(ns leiningen.idefiles
  (:require [clojure.pprint :as pp]
            [leiningen.core.classpath :as classpath]
            [leiningen.core.main      :as main]
            [leiningen.new.ide-files  :as nif])
  (:import (java.io File)
           (org.sonatype.aether.resolution DependencyResolutionException)))

(defn classpath-entries
  [project]
  (try (classpath/get-classpath project)
       (catch DependencyResolutionException e
         (main/abort (.getMessage e)))))

(defn classpath-jars
  "Return a list of maps (keys :jar-name and :jar-path), where the JAR name has
  no path/extension (but unique due to gensym) and JAR path is just the path."
  [project]
  (let [jar-file? (fn [^String x] (assert (string? x))
                    (and (let [x (.toLowerCase x)]
                           (some #(.endsWith x %) #{"jar" "zip"}))
                         (.isFile (File. x))))
        jar-name (fn [^String jar-path] (assert (string? jar-path))
                   {:jar-name (str (let [short-name (.getName (File. jar-path))]
                                        (subs short-name
                                              0 (.lastIndexOf short-name ".")))
                                   "-" (gensym))
                    :jar-path jar-path})]
    (->> (classpath-entries project)
         (filter jar-file?)
         (map jar-name)
         vec)))

(defn relative-path
  [^String base ^String path]
  (-> (File. base)
      (.toURI)
      (.relativize (.toURI (File. path)))
      (.getPath)))

(defn rel-paths
  [^String base-path paths]
  (->> paths
       (filter #(.exists (File. %)))
       (map (partial relative-path base-path))
       vec))

(defn echo
  "Print and return the argument. Use for debugging."
  [x]
  (println "---------------")
  (pp/pprint x)
  x)

(defn relativize-paths
  [project accumulator-key path-keys src-accumulator-key src-path-keys]
  (let [accumulate-all (fn [m] (merge m {accumulator-key (mapcat m path-keys)}))
        accumulate-src (fn [m] (merge m {src-accumulator-key (mapcat m src-path-keys)}))]
   (->> path-keys
        (map (fn [k] {k (rel-paths (get project :root ".")
                                  (let [path-or-paths (get project k)]
                                    (if (seq? path-or-paths)
                                      path-or-paths
                                      (list path-or-paths)))
                                  )}))
        (apply merge project)
        (accumulate-all)
        (accumulate-src))))



(defn idefiles
  "Generate IDE files for a Leiningen project"
  [project ide]
  (let [only-source-keys [:java-source-paths :source-paths :resource-paths]
        all-source-keys (concat only-source-keys [:test-paths :compile-path ])]
    (-> {:classpath-jars (classpath-jars project)}
        (merge (select-keys project (concat [:group :name] all-source-keys)))
      ;   echo
        (relativize-paths :all-source-paths all-source-keys
                          :only-source-paths only-source-keys)
       ;  echo
        (nif/ide-files ide))))

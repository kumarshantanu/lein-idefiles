(ns leiningen.new.ide-files.idea
  (:require [leiningen.new.ide-files.util :as util])
  (:use [leiningen.new.templates :only [renderer sanitize]]))


(def render (renderer "ide_files/idea"))


(defn idea
  [{:keys [group name classpath-jars only-source-paths test-paths]
    :as argmap}]
  (util/announce "IntelliJ IDEA" name)
  (let [data {:name name
              :sanitized (sanitize name)
              :classpath-jars classpath-jars
              :only-source-paths only-source-paths
              :test-paths test-paths}
  	r #(render % data)]
    (apply util/files data
      [(str name ".iml") (r "project.iml")]
      ".idea"
      [".idea/.name"          (r "dot-idea/dot-name")]
      [".idea/compiler.xml"   (r "dot-idea/compiler.xml")]
      [".idea/encodings.xml"  (r "dot-idea/encodings.xml")]
      [".idea/misc.xml"       (r "dot-idea/misc.xml")]
      [".idea/modules.xml"    (r "dot-idea/modules.xml")]
      [".idea/uiDesigner.xml" (r "dot-idea/uiDesigner.xml")]
      [".idea/vcs.xml"        (r "dot-idea/vcs.xml")]
      [".idea/workspace.xml"  (r "dot-idea/workspace.xml")]
      ".idea/copyright"
      [".idea/copyright/profiles_settings.xml" (r "dot-idea/copyright/profiles_settings.xml")]
      ".idea/inspectionProfiles"
      ".idea/libraries"
      ".idea/scopes"
      [".idea/scope_settings"                  (r "dot-idea/scopes/scope_settings.xml")]
      (->> classpath-jars
           (map (fn [{:keys [jar-name jar-path] :as each-jar}]
                  [(format ".idea/libraries/%s.xml" (sanitize jar-name))
                   (render "dot-idea/libraries/lib-template.xml"
                           (merge data each-jar))]))
           doall))))

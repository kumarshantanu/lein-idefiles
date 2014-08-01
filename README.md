# lein-idefiles

A Leiningen plugin to generate IDE files for projects. This plugin supersedes
the [ide-files](https://github.com/kumarshantanu/ide-files) Leiningen template.


## Installation

_Requires Leiningen 2.1 or better._

On Clojars: https://clojars.org/lein-idefiles

The recommended way is to install as a user-level plugin by putting
`[lein-idefiles "0.2.1"]` into the `:plugins` vector of your `:user`
profile, i.e. in `~/.lein/profiles.clj`:

```clojure
{:user {:plugins [[lein-idefiles "0.2.1"]]}}
```

You may also install as a project-level plugin by putting
`[lein-idefiles "0.2.1"]` into the `:plugins` vector of your `project.clj`:

```clojure
:plugins [[lein-idefiles "0.2.1"]]
```


## Usage

```bash
$ lein new foo-bar      # or clone a repo from version control
$ cd foo-bar
$ lein idefiles all     # creates IDE files for both Eclipse and IDEA
$ lein idefiles eclipse # creates only Eclipse files
$ lein idefiles idea    # creates only IDEA files
```


## Contributors


* Shantanu Kumar
* Michael Klishin


## License

Copyright © 2013-2014 Shantanu Kumar and contributors

Distributed under the Eclipse Public License, the same as Clojure.

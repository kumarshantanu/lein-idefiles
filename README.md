# lein-idefiles

A Leiningen plugin to generate IDE files for projects. This plugin supersedes
the [ide-files](https://github.com/kumarshantanu/ide-files) Leiningen template.


## Installation (requires Leiningen 2.1)

The recommended way is to install as a user-level plugin by putting
`[lein-idefiles "0.2.0"]` into the `:plugins` vector of your `:user`
profile, i.e. in `~/.lein/profiles.clj`:

```clojure
{:user {:plugins [[lein-idefiles "0.2.0"]]}}
```

You may also install as a project-level plugin by putting
`[lein-idefiles "0.2.0"]` into the `:plugins` vector of your `project.clj`:

```clojure
:plugins [[lein-idefiles "0.2.0"]]
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

Copyright © 2013 Shantanu Kumar and contributors

Distributed under the Eclipse Public License, the same as Clojure.

(defproject org.immutant/immutant-jobs-module "1.1.1-SNAPSHOT"
  :parent [org.immutant/immutant-modules-parent _ :relative-path "../pom.xml"]
  :plugins [[lein-modules "0.2.0"]]

  :profiles {:provided
             {:dependencies [[org.immutant/immutant-common-module _]
                             [org.immutant/immutant-core-module _]
                             [org.projectodd/polyglot-core _]
                             [org.projectodd/polyglot-jobs _]
                             [org.projectodd/polyglot-hasingleton _]
                             [org.jboss.as/jboss-as-jmx _]]}})


grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

def camelVersion = '2.11.0' // don't upgrade/downgrade camel. it seems best result is using 2.9.4 version
def activeMQVersion = '5.7.0'

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility
    repositories {
        grailsCentral()
        mavenCentral()
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
      compile ("org.apache.camel:camel-core:${camelVersion}") { excludes 'slf4j-api' }
      compile ("org.apache.camel:camel-http:${camelVersion}") { excludes 'commons-codec' }
      compile ("org.apache.camel:camel-mail:${camelVersion}")
      compile ("org.apache.camel:camel-groovy:${camelVersion}") { excludes 'groovy-all' }
      compile ("org.apache.camel:camel-spring:${camelVersion}") { excludes 'log4j', 'spring-tx', 'spring-jms','spring-context', 'spring-beans', 'spring-aop' }
      compile ("org.apache.camel:camel-jms:${camelVersion}")  { excludes 'spring-tx', 'spring-jms','spring-context', 'spring-beans', 'spring-aop', 'spring-core' }
      compile ("org.apache.camel:camel-cache:${camelVersion}") { excludes 'xercesImpl', 'xml-apis', 'slf4j-api', 'ehcache'  }
      compile ("org.apache.camel:camel-tagsoup:${camelVersion}")

      compile ("org.quartz-scheduler:quartz:2.1.6") { excludes 'slf4j-api' }

      compile ("org.apache.tika:tika-core:1.3")
      compile ("org.apache.tika:tika-parsers:1.3") { excludes "tika-core" }

      runtime("org.apache.activemq:activemq-core:${activeMQVersion}")  {
        excludes 'commons-logging',  'spring-context', 'slf4j-api' 
      }
      runtime("org.apache.activemq:activemq-camel:${activeMQVersion}")  {
        excludes 'commons-logging', 'slf4j-api', 'camel-core', 'camel-jms', 'camel-spring', 'camel-groovy'
      }
      runtime("org.apache.xbean:xbean-spring:3.8") { excludes 'commons-logging' }
      runtime("hsqldb:hsqldb:1.8.0.10") { }
    }

    plugins {
        build(":tomcat:$grailsVersion",
              ":routing:1.2.4",
              ":release:2.2.1",
              ":rest-client-builder:1.0.3") {
            export = false
        }
    }
}

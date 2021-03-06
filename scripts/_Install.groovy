//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//

println '''
*******************************************************
* You\'ve installed the GNutch grails plugin          *
*                                                     *
* Make sure you have the following plugins installed: *
*    routing (1.2.6+)                                 *
*                                                     *
* You should have the following in your Config.groovy *
* groovy.config.locations = [ GnutchConfig ]          *
*                                                     *
*******************************************************
'''

println "Copying configurations"

ant.copy(file:"${pluginBasedir}/grails-app/conf/ehcache.xml", tofile:"${basedir}/grails-app/conf/ehcache.xml")

def src = "${pluginBasedir}/grails-app/conf/GnutchConfigTemplate.groovy"
def dst = "${basedir}/grails-app/conf/GnutchConfig.groovy"
ant.copy(file: src, tofile: dst)


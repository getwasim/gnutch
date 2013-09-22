log4j = {
    error 'org.codehaus.groovy.grails',
          'org.springframework',
          'org.hibernate',
          'net.sf.ehcache.hibernate'
}
//**************************** START OF GNUTCH CONFIG ********************************
gnutch {
  // Define local directory which is used for receiving source-definition files.
  inputRoute = 'file:///tmp/gnutch-input'

  crawl {
    // Define fixed number of threads we use for crawling
    threads = {-> java.lang.Runtime.getRuntime().availableProcessors() }()
  }

  handlers {
    // post processors
    // define custom post processing closure which is called for HTML pages
    // ex.in.body contains org.w3c.dom.Document object represending XHTML page just crawled
    postXHTML = {ex ->}

    // define custom post processing closure which is called for XML document which was received after XSLT
    // ex.in.body contains org.w3c.dom.Document object represending XML result of XSL transformation
    postXML = {ex ->}

    validate = { ex ->
      def title = org.apache.xpath.XPathAPI.eval(ex.in.body, "//field[@name = 'title']").str()
      def content = org.apache.xpath.XPathAPI.eval(ex.in.body, "//field[@name = 'content']").str()

      return (title.equals("") == false) && (content.equals("") == false)
    }


    publish = {
      /** 
       // This route will save files to a directory
       from('direct:publish').
       to("file:///tmp/gnutch-output/")
      */

      // This route will commit to a solr server
      from('direct:publish').
      setHeader(org.apache.camel.Exchange.HTTP_URI, constant("http://localhost:8983/solr/collection1/update?commit=true")).
      setHeader(org.apache.camel.Exchange.HTTP_METHOD, constant('POST')).
      setHeader(org.apache.camel.Exchange.CONTENT_TYPE, constant('application/xml')).
      to("http://null")
    }       
  }

  http {
    // UserAgent string. Better if contain email address of person who is responsible
    // for crawling. That will allow source owners to contact person directly
    userAgent = 'GNutch crawler (https://github.com/softsky/gnutch/). Contact: yourname@domain.com'

    // Since we use shared threadPool for HTTP connections,
    // we define these two values

    // Maximmum number of connections per host
    defaultMaxConnectionsPerHost = 40
    // Maximmum number of total connections
    maxTotalConnections = 40
  }

  solr {
    // URL to Solr server where we send crawled data for indexing
    serverUrl = 'http://localhost:8983/solr'
  }

  activemq {
    // URL to AMQ message broker.
    // For simple configuration it runs embedded AMQ
    // More on possible connection strings here: http://activemq.apache.org/configuring-transports.html
    // brokerURL = 'tcp://0.0.0.0:61616'
    brokerURL = 'vm://localhost'

    // For more complex cases it could contain external AMQ configuration
    //conf = 'classpath:activemq.xml'
  }
}
//**************************** END OF GNUTCH CONFIG ********************************
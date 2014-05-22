package com.cleanwise.compass;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.compass.core.config.ConfigurationException;
import org.compass.core.util.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlConfigurationBuilder {
  protected Logger log = Logger.getLogger(getClass());

  private class SimpleSaxErrorHandler implements ErrorHandler {

      private final Logger log;

      /**
       * Create a new SimpleSaxErrorHandler for the given
       * Commons Logging logger instance.
       */
      public SimpleSaxErrorHandler(Logger log) {
          this.log = log;
      }

      public void warning(SAXParseException ex) throws SAXException {
          log.warn("Ignored XML validation warning [" + ex.getMessage() + "]", ex);
      }

      public void error(SAXParseException ex) throws SAXException {
          throw ex;
      }

      public void fatalError(SAXParseException ex) throws SAXException {
          throw ex;
      }

  }


  public void configure(String name, DataConfig config) throws ConfigurationException {
    URL url = null;
      try {
        url = XmlConfigurationBuilder.class.getClassLoader().getResource(name);
        configure(url.openStream(), url.toString(), config);
      } catch (IOException e) {
          throw new ConfigurationException("Could not find configuration file [" + url  + "]", e);
      }
  }

  private void configure(InputStream is, String resourceName, DataConfig config) {
      try {
        log.info("configure mapping from: " + resourceName);
          doConfigure(is, resourceName, config);
      } finally {
          try {
              is.close();
          } catch (IOException e) {
              log.warn("Failed to close input stream for [" + resourceName + "]", e);
          }
      }
  }



  protected void doConfigure(InputStream is, String resourceName, DataConfig config) throws ConfigurationException {
      InputSource inputSource = new InputSource(is);
      try {
          DocumentBuilderFactory factory = createDocumentBuilderFactory();
          DocumentBuilder builder = createDocumentBuilder(factory);
          Document doc = builder.parse(inputSource);
          doProcess(doc, config);
      } catch (ParserConfigurationException ex) {
          throw new ConfigurationException(
                  "Parser configuration exception parsing XML from [" + resourceName + "]", ex);
      }
      catch (SAXParseException ex) {
          throw new ConfigurationException(
                  "Line [" + ex.getLineNumber() + "] in XML document from [" + resourceName + "] is invalid", ex);
      }
      catch (SAXException ex) {
          throw new ConfigurationException("XML document from [" + resourceName + "] is invalid", ex);
      }
      catch (IOException ex) {
          throw new ConfigurationException("IOException parsing XML document from [" + resourceName + "]", ex);
      }

  }

  protected void doProcess(Document doc, DataConfig config) throws ConfigurationException {
      Element root = doc.getDocumentElement();
      // the root is the data-config element
      NodeList nl = root.getChildNodes();
      for (int i = 0; i < nl.getLength(); i++) {
          Node node = nl.item(i);
          if (node instanceof Element) {
              if ("devices".equals(node.getNodeName())) {
                processData((Element) node, config);
              }
          }
      }
  }

  public void processData(Element compassElement, DataConfig config) {
      NodeList nl = compassElement.getChildNodes();
      for (int i = 0; i < nl.getLength(); i++) {
          Node node = nl.item(i);
          if (node instanceof Element) {
              Element ele = (Element) node;
              String nodeName = ele.getNodeName();
              String methodName = "bind" + Character.toUpperCase(nodeName.charAt(0)) + nodeName.substring(1, nodeName.length());
              Method method;
              try {
                  method = XmlConfigurationBuilder.class.getMethod(methodName, new Class[]{Element.class, DataConfig.class});
              } catch (NoSuchMethodException e) {
                  throw new ConfigurationException("Failed to process node [" + nodeName + "], this is " +
                          "either a mailformed xml configuration (not validated against the xsd), or an internal" +
                          " bug in compass");
              }
              try {
                  method.invoke(this, new Object[]{ele, config});
              } catch (InvocationTargetException e) {
                  throw new ConfigurationException("Failed to invoke binding metod for node [" + nodeName + "]", e.getTargetException());
              } catch (IllegalAccessException e) {
                  throw new ConfigurationException("Failed to access binding metod for node [" + nodeName + "]", e);
              }
          }
      }
  }


  protected DocumentBuilderFactory createDocumentBuilderFactory()
          throws ParserConfigurationException {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(false);
      return factory;
  }

  protected DocumentBuilder createDocumentBuilder(DocumentBuilderFactory factory)
          throws ParserConfigurationException {
      DocumentBuilder docBuilder = factory.newDocumentBuilder();
      docBuilder.setErrorHandler(doGetErrorHandler());
//      docBuilder.setEntityResolver(doGetEntityResolver());
      docBuilder.setEntityResolver(null);
      return docBuilder;
  }

  protected ErrorHandler doGetErrorHandler() {
      return new SimpleSaxErrorHandler(log);
  }

  protected EntityResolver doGetEntityResolver() {
      return new EntityResolver() {

          private static final String URL = "http://www.opensymphony.com/compass/schema/";

          public InputSource resolveEntity(String publicId, String systemId) {
              if (systemId != null && systemId.startsWith(URL)) {
                  // Search for DTD
                  String location = "/org/compass/core/" + systemId.substring(URL.length());
                  InputStream is = getClass().getResourceAsStream(location);
                  if (is == null) {
                      throw new ConfigurationException("Schema system id [" + systemId + "] not found at [" + location + "], " +
                              "please check it has the correct location. Have you included compass in your class path?");
                  }
                  InputSource source = new InputSource(is);
                  source.setPublicId(publicId);
                  source.setSystemId(systemId);
                  return source;
              } else {
                  throw new ConfigurationException("Schema system id [" + systemId + "] not found, please check it has the " +
                          "correct location");
              }
          }
      };
  }

  public void bindGps(Element ele, DataConfig config) {
    DataConfig.Gps gps = new DataConfig.Gps(DomUtils.getElementAttribute(ele, "id"));
    gps.setType(DomUtils.getElementAttribute(ele, "type"));
    List props = DomUtils.getChildElementsByTagName(ele, "property");
    for (Iterator it = props.iterator(); it.hasNext(); ) {
      Element gpsPropEle = (Element) it.next();
      if ("dataSource".equals(DomUtils.getElementAttribute(gpsPropEle, "name"))) {
        gps.setDataSource(DomUtils.getTextValue(gpsPropEle));
      }
    }
    List mappings = DomUtils.getChildElementsByTagName(ele, "mappings");
    for (Iterator it = mappings.iterator(); it.hasNext(); ) {
      Element mappingsEle = (Element) it.next();
      List mapping = DomUtils.getChildElementsByTagName(mappingsEle, "mapping");
      DataConfig.Mapping mappingConfig = null;
      for (Iterator iter = mapping.iterator(); iter.hasNext(); ) {
        Element mappingEle = (Element) iter.next();
        mappingConfig = processMapping(mappingEle);
      }
      if (mappingConfig != null) {
        gps.putMapping(mappingConfig);
      }
    }
    config.putGps(gps);

  }

  public DataConfig.Mapping processMapping(Element ele) {
    DataConfig.Mapping mappingConfig = new DataConfig.Mapping(DomUtils.getElementAttribute(ele, "alias"));
    mappingConfig.setType(DomUtils.getElementAttribute(ele, "type"));
    List props = DomUtils.getChildElementsByTagName(ele, "property");
    for (Iterator it = props.iterator(); it.hasNext();) {
      Element propEle = (Element) it.next();
      if ("indexUnMappedColumns".equals(DomUtils.getElementAttribute(propEle, "name"))) {
        if ("true".equalsIgnoreCase(DomUtils.getTextValue(propEle))) {
          mappingConfig.setIndexUnMappedColumns(true);
        }
      }
      if ("selectQuery".equals(DomUtils.getElementAttribute(propEle, "name"))) {
        mappingConfig.setSelectQuery(DomUtils.getTextValue(propEle));
      }
      if ("versionQuery".equals(DomUtils.getElementAttribute(propEle, "name"))) {
        mappingConfig.setVersionQuery(DomUtils.getTextValue(propEle));
      }
      if ("idMappings".equals(DomUtils.getElementAttribute(propEle, "name")) ||
          "dataMappings".equals(DomUtils.getElementAttribute(propEle, "name")) ||
          "versionMappings".equals(DomUtils.getElementAttribute(propEle, "name"))) {
        List map = DomUtils.getChildElementsByTagName(propEle, "map");
        for (Iterator itMap = map.iterator(); itMap.hasNext();) {
          Element mapEle = (Element) itMap.next();
          List mapProp = DomUtils.getChildElementsByTagName(mapEle, "property");
          DataConfig.ColumnMapping idMap = null;
          for (Iterator itMapProp = mapProp.iterator(); itMapProp.hasNext();) {
            Element mapEleProp = (Element) itMapProp.next();
            if ("columnName".equals(DomUtils.getElementAttribute(mapEleProp, "name"))) {
              idMap = new DataConfig.ColumnMapping(DomUtils.getTextValue(mapEleProp));
            }
            if ("propertyName".equals(DomUtils.getElementAttribute(mapEleProp, "name"))) {
              idMap.setPropertyName(DomUtils.getTextValue(mapEleProp));
            }
            if ("columnNameForVersion".equals(DomUtils.getElementAttribute(mapEleProp, "name"))) {
              idMap.setColumnNameForVersion(DomUtils.getTextValue(mapEleProp));
            }
          }
          if (idMap != null) {
            if ("idMappings".equals(DomUtils.getElementAttribute(propEle, "name"))) {
              mappingConfig.putIdMapping(idMap);
            } else if ("dataMappings".equals(DomUtils.getElementAttribute(propEle, "name"))) {
              mappingConfig.putDataMapping(idMap);
            } else if ("versionMappings".equals(DomUtils.getElementAttribute(propEle, "name"))) {
              mappingConfig.putVersionMapping(idMap);
            }
          }
        }
      }

    }
    return mappingConfig;
  }

  public static void main(String[] args) throws Exception
  {
//    XmlConfigurationBuilder builder = new XmlConfigurationBuilder();
//    builder.configure(new File("C:/cvs_cleanwise/cleanwise/stjohn/tools/compass/event.map.xml"), new DataConfig());
  }

}


package com.johnsondiversey.msds.msdswebservice.jdimsds;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.johnsondiversey.msds.msdswebservice.jdimsds package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.johnsondiversey.msds.msdswebservice.jdimsds
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMSDSProductLevel }
     * 
     */
    public GetMSDSProductLevel createGetMSDSProductLevel() {
        return new GetMSDSProductLevel();
    }

    /**
     * Create an instance of {@link GetMSDSResponse }
     * 
     */
    public GetMSDSResponse createGetMSDSResponse() {
        return new GetMSDSResponse();
    }

    /**
     * Create an instance of {@link GetMSDS }
     * 
     */
    public GetMSDS createGetMSDS() {
        return new GetMSDS();
    }

    /**
     * Create an instance of {@link GetMSDSXML }
     * 
     */
    public GetMSDSXML createGetMSDSXML() {
        return new GetMSDSXML();
    }

    /**
     * Create an instance of {@link GetMSDSProductLevelResponse }
     * 
     */
    public GetMSDSProductLevelResponse createGetMSDSProductLevelResponse() {
        return new GetMSDSProductLevelResponse();
    }

    /**
     * Create an instance of {@link GetMSDSXMLResponse }
     * 
     */
    public GetMSDSXMLResponse createGetMSDSXMLResponse() {
        return new GetMSDSXMLResponse();
    }

}

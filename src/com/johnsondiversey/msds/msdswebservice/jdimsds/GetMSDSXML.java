
package com.johnsondiversey.msds.msdswebservice.jdimsds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="XMLParameters" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "xmlParameters"
})
@XmlRootElement(name = "GetMSDSXML")
public class GetMSDSXML {

    @XmlElement(name = "XMLParameters")
    protected String xmlParameters;

    /**
     * Gets the value of the xmlParameters property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXMLParameters() {
        return xmlParameters;
    }

    /**
     * Sets the value of the xmlParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXMLParameters(String value) {
        this.xmlParameters = value;
    }

}

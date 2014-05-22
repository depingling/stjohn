/**
 * PingResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Result information returned from the {@link com.avalara.avatax.services.address.AddressSvcSoap}'s
 * {@link com.avalara.avatax.services.address.AddressSvcSoap#ping} method and the {@link TaxSvcSoap}'s
 * {@link TaxSvcSoap#ping} method.
 * <p><b>Example:</b><br>
 * <pre>
 * [Java]
 *  EngineConfiguration config = new FileProvider("avatax4j.wsdd");
 *  TaxSvcLocator taxSvcLoc = new TaxSvcLocator(config);
 *
 *  TaxSvcSoap svc = taxSvcLoc.getTaxSvcSoap(new URL("http://www.avalara.com/services/"));
 *
 *  // Set the profile
 *  Profile profile = new Profile();
 *  profile.setClient("AddressSvcTest,4.0.0.0");
 *  svc.setProfile(profile);
 *
 *  // Set security
 *  Security security = new Security();
 *  security.setAccount("account");
 *  security.setLicense("license number");
 *  svc.setSecurity(security);
 *
 *  PingResult result = svc.ping("");
 *  int numMessages = (result.getMessages() == null ||
 *          result.getMessages().getMessage() == null ? 0 :
 *          result.getMessages().getMessage().length);
 *  JOptionPane.showMessageDialog(null, "Result Code: " + result.getResultCode().toString() + "\r\n" +
 *      "# Messages: " + numMessages + "\r\n" +
 *      "Service Version: " + result.getVersion(), "Ping Result",
 *          JOptionPane.INFORMATION_MESSAGE);
 *
 * </pre>
 *
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class PingResult  extends com.avalara.avatax.services.tax.BaseResult  implements java.io.Serializable {
    private java.lang.String version;

    /**
     * Initializes a new instance of the class.
     */
    public PingResult() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param version
     */
    private PingResult(
            java.lang.String version) {
        this.version = version;
    }


    /**
     * Version of the web service (e.g. 4.0.0.0) .
     *
     * @return version
     */
    public java.lang.String getVersion() {
        return version;
    }


    /**
     * Version of the web service (e.g. 4.0.0.0) .
     *
     * @param version
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }

    private java.lang.Object __equalsCalc = null;
    /**
     * Determines whether the specified Object is equal to the current Object.
     * Note: In current implementation all Java Strings members of the two
     * objects must be exactly alike, including in case, for equal to return true.
     * @param obj
     * @return true or false, indicating if the two objects are equal.
     */
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PingResult)) return false;
        PingResult other = (PingResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) &&
                ((this.version==null && other.getVersion()==null) ||
                        (this.version!=null &&
                                this.version.equals(other.getVersion())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    /**
     *  Serves as a hash function for a particular type,
     * suitable for use in hashing algorithms and data
     * structures like a hash table.
     * @return  hash code for this GetTaxRequest object
     * @see java.lang.Object#hashCode
     */
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(PingResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PingResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Version"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }


    /**
     * Return Axis type metadata object; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }


    /**
     * Get Axis Custom Serializer; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new  org.apache.axis.encoding.ser.BeanSerializer(
                        _javaType, _xmlType, typeDesc);
    }


    /**
     * Get Axis Custom Deserializer; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new  com.avalara.avatax.services.base.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}

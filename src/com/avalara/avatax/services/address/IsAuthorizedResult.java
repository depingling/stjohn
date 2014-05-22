/**
 * IsAuthorizedResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.address;

/**
 * Result information returned from the AddressSvc's
 * {@link AddressSvcSoap#isAuthorized} method and the TaxSvc's
 * {@link com.avalara.avatax.services.tax.TaxSvcSoap#isAuthorized}
 * method.
 * <p><b>Example:</b><br>
 * <pre>
 * [Java]
 *  EngineConfiguration config = new FileProvider("avatax4j.wsdd");
 *  AddressSvcLocator AddressSvc = new AddressSvcLocator(config);
 *
 *  AddressSvcSoap port = AddressSvc.getAddressSvcSoap(new URL("http://www.avalara.com/services/"));
 *
 *  // Set the profile
 *  Profile profile = new Profile();
 *  profile.setClient("AddressSvcTest,4.0.0.0");
 *  port.setProfile(profile);
 *
 *  // Set security
 *  Security security = new Security();
 *  security.setAccount("account");
 *  security.setLicense("license number");
 *  port.setSecurity(security);
 *
 *  PingResult result = port.ping("");
 *  int numMessages = (result.getMessages() == null ||
 *          result.getMessages().getMessage() == null ? 0 :
 *          result.getMessages().getMessage().length);
 *  JOptionPane.showMessageDialog(null, "Result Code: " + result.getResultCode().toString() + "\r\n" +
 *      "# Messages: " + numMessages + "\r\n" +
 *      "Service Version: " + result.getVersion(), "Ping Result",
 *          JOptionPane.INFORMATION_MESSAGE);
 *
 *
 *
 * </pre>
 *
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class IsAuthorizedResult  extends com.avalara.avatax.services.address.BaseResult  implements java.io.Serializable {
    private java.lang.String operations;
    private java.util.Calendar expires;

    /**
     * Initializes a new instance of the class.
     */
    public IsAuthorizedResult() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param operations
     * @param expires
     */
    private IsAuthorizedResult(
            java.lang.String operations,
            java.util.Calendar expires) {
        this.operations = operations;
        this.expires = expires;
    }


    /**
     * Authorized operations for the user.
     * <br><b>Example:</b> "GetTax,PostTax"
     * <br>This will contain a comma-delimited list of the the
     * requested operations the user is authorized to invoke.
     * For security, it will not return any operations other
     * than those requested.
     *
     * @return operations
     */
    public java.lang.String getOperations() {
        return operations;
    }


    /**
     * Authorized operations for the user.
     * <br><b>Example:</b> "GetTax,PostTax"
     * <br>This will contain a comma-delimited list of the the
     * requested operations the user is authorized to invoke.
     * For security, it will not return any operations other
     * than those requested.
     *
     * @param operations
     */
    public void setOperations(java.lang.String operations) {
        this.operations = operations;
    }


    /**
     * Indicates the subscription expiration date in yyyy-mm-dd format (e.g. 2005-01-29).
     *
     * @return expires
     */
    public java.util.Calendar getExpires() {
        return expires;
    }


    /**
     * Indicates the subscription expiration date in yyyy-mm-dd format (e.g. 2005-01-29).
     *
     * @param expires
     */
    public void setExpires(java.util.Calendar expires) {
        this.expires = expires;
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
        if (!(obj instanceof IsAuthorizedResult)) return false;
        IsAuthorizedResult other = (IsAuthorizedResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) &&
                ((this.operations==null && other.getOperations()==null) ||
                        (this.operations!=null &&
                                this.operations.equals(other.getOperations()))) &&
                ((this.expires==null && other.getExpires()==null) ||
                        (this.expires!=null &&
                                this.expires.equals(other.getExpires())));
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
        if (getOperations() != null) {
            _hashCode += getOperations().hashCode();
        }
        if (getExpires() != null) {
            _hashCode += getExpires().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(IsAuthorizedResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "IsAuthorizedResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operations");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Operations"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expires");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Expires"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }



    /**
     * Return Axis type metadata object; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @return Type Description
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }


    /**
     * Get Axis Custom Serializer; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @param mechType
     * @param _javaType
     * @param _xmlType
     * @return Serializer
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
     *
     * @param mechType
     * @param _javaType
     * @param _xmlType
     * @return Deserializer
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

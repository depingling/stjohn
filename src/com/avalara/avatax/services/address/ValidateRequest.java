/**
 * ValidateRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.address;

/**
 * Data to pass to {@link AddressSvcSoap#validate}.
 * <p>
 * <pre>
 * <b>Example:</b>
 * [Java]
 * EngineConfiguration config = new FileProvider("avatax4j.wsdd");
 * AddressSvcLocator addressSvcLoc = new AddressSvcLocator(config);
 *
 * AddressSvcSoap svc = addressSvcLoc.getAddressSvcSoap(new URL("http://www.avalara.com/services/"));
 *
 * // Set the profile
 * Profile profile = new Profile();
 * profile.setClient("AddressSvcTest,4.0.0.0");
 * svc.setProfile(profile);
 *
 * // Set security
 * Security security = new Security();
 * security.setAccount("account");
 * security.setLicense("license number");
 * svc.setSecurity(security);
 *
 * Address address = new Address();
 * address.setLine1("900 Winslow Way");
 * address.setCity("Bainbridge Island");
 * address.setRegion("WA");
 * address.setPostalCode("98110");
 *
 * ValidateRequest validateRequest = new ValidateRequest();
 * validateRequest.setAddress(address);
 * validateRequest.setTextCase(TextCase.Upper);
 *
 * ValidateResult result = svc.validate(validateRequest);
 * ArrayOfValidAddress arrValids = result.getValidAddresses();
 * int numAddresses = (arrValids == null ||
 *         arrValids.getValidAddress() == null ? 0 :
 *         arrValids.getValidAddress().length);
 * </pre>
 *
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class ValidateRequest  implements java.io.Serializable {

    /**
     * The address to validate.
     * <p>
     * <b>Example</b>
     * <code>
     * <br>[C#]<br>
     * <br>Address address = new Address();
     * <br>address.Line1 = "900 Winslow Way";
     * <br>address.Region = "WA";
     * <br>address.PostalCode = "98110";
     * <br>
     * <br>ValidateRequest validateRequest = new ValidateRequest();
     * <br>validateRequest.Address = address;
     * <br>validateRequest.TextCase = TextCase.Default;
     * <br>
     * <br>AddressSvc svc = new AddressSvc();
     * <br>ValidateResult validateResult = svc.Validate(validateRequest);
     * <br></code>
     */
    private com.avalara.avatax.services.address.Address address;

    /**
     * The casing to apply to the validated address(es).
     * <p>
     * <b>Example</b>
     * <code>
     * <br>[C#]<br>
     * <br>Address address = new Address();
     * <br>address.Line1 = "900 Winslow Way";
     * <br>address.Region = "WA";
     * <br>address.PostalCode = "98110";
     * <br>
     * <br>ValidateRequest validateRequest = new ValidateRequest();
     * <br>validateRequest.Address = address;
     * <br>validateRequest.TextCase = TextCase.Default;
     * <br>
     * <br>AddressSvc svc = new AddressSvc();
     * <br>ValidateResult validateResult = svc.Validate(validateRequest);
     * </code>
     */
    private com.avalara.avatax.services.address.TextCase textCase;
    private boolean coordinates;
    private boolean taxability;

    /**
     * Initializes a new instance of the class.
     */
    public ValidateRequest() {
        textCase = TextCase.Default;
        setCoordinates(false);
    }

    /**
     *  Initializes a new instance of the class.
     *
     * @param address
     * @param textCase
     */
    private ValidateRequest(
            com.avalara.avatax.services.address.Address address,
            com.avalara.avatax.services.address.TextCase textCase) {
        this.address = address;
        this.textCase = textCase;
    }


    /**
     * The address to Validate.
     * <pre>
     * <b>Example:</b>
     * [Java]
     * Address address = new Address();
     * address.setLine1("900 Winslow Way");
     * address.setCity("Bainbridge Island");
     * address.setRegion("WA");
     * address.setPostalCode("98110");
     *
     * ValidateRequest validateRequest = new ValidateRequest();
     * validateRequest.setAddress(address);
     * validateRequest.setTextCase(TextCase.Upper);
     *
     * ValidateResult result = svc.validate(validateRequest);
     * </pre>
     *
     * @return address
     */
    public com.avalara.avatax.services.address.Address getAddress() {
        return address;
    }


    /**
     * The address to Validate.
     * <pre>
     * <b>Example:</b>
     * [Java]
     * Address address = new Address();
     * address.setLine1("900 Winslow Way");
     * address.setCity("Bainbridge Island");
     * address.setRegion("WA");
     * address.setPostalCode("98110");
     *
     * ValidateRequest validateRequest = new ValidateRequest();
     * validateRequest.setAddress(address);
     * validateRequest.setTextCase(TextCase.Upper);
     *
     * ValidateResult result = svc.validate(validateRequest);
     * </pre>
     *
     * @param address
     */
    public void setAddress(com.avalara.avatax.services.address.Address address) {
        this.address = address;
    }


    /**
     * The casing to apply to the validated address(es).
     * <pre>
     * <b>Example:</b>
     * [Java]
     * Address address = new Address();
     * address.setLine1("900 Winslow Way");
     * address.setCity("Bainbridge Island");
     * address.setRegion("WA");
     * address.setPostalCode("98110");
     *
     * ValidateRequest validateRequest = new ValidateRequest();
     * validateRequest.setAddress(address);
     * validateRequest.setTextCase(TextCase.Upper);
     *
     * ValidateResult result = svc.validate(validateRequest);
     * </pre>
     *
     * @return textCase
     */
    public com.avalara.avatax.services.address.TextCase getTextCase() {
        return textCase;
    }


    /**
     * The casing to apply to the validated address(es).
     * <pre>
     * <b>Example:</b>
     * [Java]
     * Address address = new Address();
     * address.setLine1("900 Winslow Way");
     * address.setCity("Bainbridge Island");
     * address.setRegion("WA");
     * address.setPostalCode("98110");
     *
     * ValidateRequest validateRequest = new ValidateRequest();
     * validateRequest.setAddress(address);
     * validateRequest.setTextCase(TextCase.Upper);
     *
     * ValidateResult result = svc.validate(validateRequest);
     * </pre>
     *
     * @param textCase
     */
    public void setTextCase(com.avalara.avatax.services.address.TextCase textCase) {
        this.textCase = textCase;
    }
    /**
     * Gets the coordinates value for this ValidateRequest.
     *
     * @return coordinates
     */
    public boolean getCoordinates() {
        return coordinates;
    }
    /**
     * Sets the coordinates value for this ValidateRequest.
     *  <p>
     *  True will return the @see ValidAddresses#Latitude and @see ValidAddresses#Longitude values for the @see ValidAddresses
     *  Default value is <b>false</b>
     *  </p>
     * @param coordinates
     */
    public void setCoordinates(boolean coordinates) {
        this.coordinates = coordinates;
    }

     /**
     * Gets the taxability value for this ValidateRequest.
     *
     * @return taxability
     */
    public boolean isTaxability() {
        return taxability;
    }


    /**
     * Sets the taxability value for this ValidateRequest.
     *
     * @param taxability
     */
    public void setTaxability(boolean taxability) {
        this.taxability = taxability;
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
        if (!(obj instanceof ValidateRequest)) return false;
        ValidateRequest other = (ValidateRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.address==null && other.getAddress()==null) ||
                        (this.address!=null &&
                                this.address.equals(other.getAddress()))) &&
                ((this.textCase==null && other.getTextCase()==null) ||
                        (this.textCase!=null &&
                                this.textCase.equals(other.getTextCase()))) &&
                (this.coordinates == other.getCoordinates()) &&
                (this.taxability == other.isTaxability());
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
        int _hashCode = 1;
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getTextCase() != null) {
            _hashCode += getTextCase().hashCode();
        }
        _hashCode += (getCoordinates() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isTaxability() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ValidateRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidateRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("textCase");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TextCase"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TextCase"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordinates");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Coordinates"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
         elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxability");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Taxability"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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

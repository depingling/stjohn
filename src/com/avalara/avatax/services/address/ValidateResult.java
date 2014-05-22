/**
 * ValidateResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.address;

/**
 * Contains an {@link ArrayOfValidAddress} object returned by {@link AddressSvcSoap#validate}
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
 * @see ArrayOfValidAddress
 * @see ValidAddress
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class ValidateResult  extends com.avalara.avatax.services.address.BaseResult  implements java.io.Serializable {

    /**
     * The array of {@link ValidAddress} objects for this object.
     */
    private com.avalara.avatax.services.address.ArrayOfValidAddress validAddresses;
    private boolean taxable;

    /**
     * Initializes a new instance of the class.
     */
    public ValidateResult() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param validAddresses
     */
    private ValidateResult(
            com.avalara.avatax.services.address.ArrayOfValidAddress validAddresses,
            boolean taxable) {
        this.validAddresses = validAddresses;
         this.taxable = taxable;
    }


    /**
     * Provides access to the {@link ArrayOfValidAddress} wrapper object, containing an array of
     * {@link ValidAddress} objects.
     *
     * @return validAddresses
     */
    public com.avalara.avatax.services.address.ArrayOfValidAddress getValidAddresses() {
        return validAddresses;
    }


    /**
     * Provides access to the {@link ArrayOfValidAddress} wrapper object, containing an array of
     * {@link ValidAddress} objects.
     *
     * @param validAddresses
     */
    public void setValidAddresses(com.avalara.avatax.services.address.ArrayOfValidAddress validAddresses) {
        this.validAddresses = validAddresses;
    }

    /**
     * Gets the taxable value for this ValidateResult.
     *
     * @return taxable
     */
    public boolean isTaxable() {
        return taxable;
    }


    /**
     * Sets the taxable value for this ValidateResult.
     *
     * @param taxable
     */
    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
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
        if (!(obj instanceof ValidateResult)) return false;
        ValidateResult other = (ValidateResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) &&
                ((this.validAddresses==null && other.getValidAddresses()==null) ||
                        (this.validAddresses!=null &&
                                this.validAddresses.equals(other.getValidAddresses()))) &&
                  this.taxable == other.isTaxable() ;
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
        if (getValidAddresses() != null) {
            _hashCode += getValidAddresses().hashCode();
        }
        _hashCode += (isTaxable() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ValidateResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidateResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validAddresses");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidAddresses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfValidAddress"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Taxable"));
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

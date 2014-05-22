/**
 * AdjustTaxRequest.java
 *
 /**
 * Created by IntelliJ IDEA.
 * User: amit.raval
 * Date: Sep 7, 2007
 * Time: 1:51:28 PM
 * To change this template use File | Settings | File Templates.
 */


package com.avalara.avatax.services.tax;

/**
 * Data to pass to {@link TaxSvcSoap#adjustTax}.
 * @see AdjustTaxResult
 * @author amit.raval
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class AdjustTaxRequest  implements java.io.Serializable {
    private int adjustmentReason;
    private java.lang.String adjustmentDescription;
    private com.avalara.avatax.services.tax.GetTaxRequest getTaxRequest;

    public AdjustTaxRequest() {
    }

    private AdjustTaxRequest(
            int adjustmentReason,
            java.lang.String adjustmentDescription,
            com.avalara.avatax.services.tax.GetTaxRequest getTaxRequest) {
        this.adjustmentReason = adjustmentReason;
        this.adjustmentDescription = adjustmentDescription;
        this.getTaxRequest = getTaxRequest;
    }


    /**
     * Gets the adjustmentReason value for this AdjustTaxRequest.
     * @return adjustmentReason
     */
    public int getAdjustmentReason() {
        return adjustmentReason;
    }
    /**
     * Sets the adjustmentReason value for this AdjustTaxRequest.
     * <p>
     * Reason for Adjusting document.
     * Sets a valid reason for the given AdjustTax call. Adjustment Reason is a high level classification of why an Original Document is being modified..
     * Please visit Avalara's Administrative Console's transaction adjustment section for latest AdjustmentReasonList. 
     * </p> 
     * @param adjustmentReason
     */
    public void setAdjustmentReason(int adjustmentReason) {
        this.adjustmentReason = adjustmentReason;
    }


    /**
     * Gets the adjustmentDescription value for this AdjustTaxRequest.
     *
     * @return adjustmentDescription
     */
    public java.lang.String getAdjustmentDescription() {
        return adjustmentDescription;
    }


    /**
     * Sets the adjustmentDescription value for this AdjustTaxRequest.
     * <p>
     * Sets description for the given AdjustTax call. Adjustment Description is required when {@link AdjustTaxRequest#adjustmentReason} is "Other" for enhanced tractability.
     * </p>
     * @param adjustmentDescription
     */
    public void setAdjustmentDescription(java.lang.String adjustmentDescription) {
        this.adjustmentDescription = adjustmentDescription;
    }


    /**
     * Gets the getTaxRequest value for this AdjustTaxRequest.
     *
     * @return getTaxRequest
     */
    public com.avalara.avatax.services.tax.GetTaxRequest getGetTaxRequest() {
        return getTaxRequest;
    }
    /**
     * Sets the {@link GetTaxRequest} value for this AdjustTaxRequest.
     * @param getTaxRequest
     */
    public void setGetTaxRequest(com.avalara.avatax.services.tax.GetTaxRequest getTaxRequest) {
        this.getTaxRequest = getTaxRequest;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AdjustTaxRequest)) return false;
        AdjustTaxRequest other = (AdjustTaxRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                this.adjustmentReason == other.getAdjustmentReason() &&
                ((this.adjustmentDescription==null && other.getAdjustmentDescription()==null) ||
                        (this.adjustmentDescription!=null &&
                                this.adjustmentDescription.equals(other.getAdjustmentDescription()))) &&
                ((this.getTaxRequest==null && other.getGetTaxRequest()==null) ||
                        (this.getTaxRequest!=null &&
                                this.getTaxRequest.equals(other.getGetTaxRequest())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getAdjustmentReason();
        if (getAdjustmentDescription() != null) {
            _hashCode += getAdjustmentDescription().hashCode();
        }
        if (getGetTaxRequest() != null) {
            _hashCode += getGetTaxRequest().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(AdjustTaxRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AdjustTaxRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adjustmentReason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AdjustmentReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adjustmentDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AdjustmentDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getTaxRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
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
     * Get Custom Deserializer
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

package com.avalara.avatax.services.tax;

public class TaxOverride  implements java.io.Serializable {
    private com.avalara.avatax.services.tax.TaxOverrideType taxOverrideType;
    private java.math.BigDecimal taxAmount;
    private java.util.Date taxDate;
    private java.lang.String reason;

    public TaxOverride() {
    }

    public TaxOverride(
           com.avalara.avatax.services.tax.TaxOverrideType taxOverrideType,
           java.math.BigDecimal taxAmount,
           java.util.Date taxDate,
           java.lang.String reason) {
           this.taxOverrideType = taxOverrideType;
           this.taxAmount = taxAmount;
           this.taxDate = taxDate;
           this.reason = reason;
    }


    /**
     * Gets the taxOverrideType value for this TaxOverride.
     * 
     * @return taxOverrideType
     */
    public com.avalara.avatax.services.tax.TaxOverrideType getTaxOverrideType() {
        return taxOverrideType;
    }


    /**
     * Sets the taxOverrideType value for this TaxOverride.
     * <p>
     * Reason for applying TaxOverride
     * </p>
     * @param taxOverrideType
     */
    public void setTaxOverrideType(com.avalara.avatax.services.tax.TaxOverrideType taxOverrideType) {
        this.taxOverrideType = taxOverrideType;
    }


    /**
     * Gets the taxAmount value for this TaxOverride.
     * 
     * @return taxAmount
     */
    public java.math.BigDecimal getTaxAmount() {
        return taxAmount;
    }


    /**
     * Sets the taxAmount value for this TaxOverride.
     * <p>
     * The TaxAmount overrides the total tax for the document.
     * </p>
     * @param taxAmount
     */
    public void setTaxAmount(java.math.BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }


    /**
     * Gets the taxDate value for this TaxOverride.
     * 
     * @return taxDate
     */
    public java.util.Date getTaxDate() {
        return taxDate;
    }


    /**
     * Sets the taxDate value for this TaxOverride.
     * <p>
     * The TaxDate overrides the DocDate as the effective date used for tax calculation.
     * </p>
     * @param taxDate
     */
    public void setTaxDate(java.util.Date taxDate) {
        this.taxDate = taxDate;
    }


    /**
     * Gets the reason value for this TaxOverride.
     * 
     * @return reason
     */
    public java.lang.String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this TaxOverride.
     * <p>
     * Type of tax override.
     * </p>
     * Also See {@link TaxOverrideType}
     * @param reason
     */
    public void setReason(java.lang.String reason) {
        this.reason = reason;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TaxOverride)) return false;
        TaxOverride other = (TaxOverride) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.taxOverrideType==null && other.getTaxOverrideType()==null) || 
             (this.taxOverrideType!=null &&
              this.taxOverrideType.equals(other.getTaxOverrideType()))) &&
            ((this.taxAmount==null && other.getTaxAmount()==null) || 
             (this.taxAmount!=null &&
              this.taxAmount.equals(other.getTaxAmount()))) &&
            ((this.taxDate==null && other.getTaxDate()==null) || 
             (this.taxDate!=null &&
              this.taxDate.equals(other.getTaxDate()))) &&
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason())));
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
        if (getTaxOverrideType() != null) {
            _hashCode += getTaxOverrideType().hashCode();
        }
        if (getTaxAmount() != null) {
            _hashCode += getTaxAmount().hashCode();
        }
        if (getTaxDate() != null) {
            _hashCode += getTaxDate().hashCode();
        }
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TaxOverride.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxOverride"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxOverrideType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxOverrideType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxOverrideType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Reason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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

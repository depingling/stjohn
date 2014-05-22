/**
 * GetFaxStatusResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.Reports;

public class GetFaxStatusResult  implements java.io.Serializable {
    private com.protus.www.Reports.Header header;

    private com.protus.www.Reports.FaxSummary[] faxSummary;

    public GetFaxStatusResult() {
    }

    public GetFaxStatusResult(
           com.protus.www.Reports.Header header,
           com.protus.www.Reports.FaxSummary[] faxSummary) {
           this.header = header;
           this.faxSummary = faxSummary;
    }


    /**
     * Gets the header value for this GetFaxStatusResult.
     * 
     * @return header
     */
    public com.protus.www.Reports.Header getHeader() {
        return header;
    }


    /**
     * Sets the header value for this GetFaxStatusResult.
     * 
     * @param header
     */
    public void setHeader(com.protus.www.Reports.Header header) {
        this.header = header;
    }


    /**
     * Gets the faxSummary value for this GetFaxStatusResult.
     * 
     * @return faxSummary
     */
    public com.protus.www.Reports.FaxSummary[] getFaxSummary() {
        return faxSummary;
    }


    /**
     * Sets the faxSummary value for this GetFaxStatusResult.
     * 
     * @param faxSummary
     */
    public void setFaxSummary(com.protus.www.Reports.FaxSummary[] faxSummary) {
        this.faxSummary = faxSummary;
    }

    public com.protus.www.Reports.FaxSummary getFaxSummary(int i) {
        return this.faxSummary[i];
    }

    public void setFaxSummary(int i, com.protus.www.Reports.FaxSummary _value) {
        this.faxSummary[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetFaxStatusResult)) return false;
        GetFaxStatusResult other = (GetFaxStatusResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.header==null && other.getHeader()==null) || 
             (this.header!=null &&
              this.header.equals(other.getHeader()))) &&
            ((this.faxSummary==null && other.getFaxSummary()==null) || 
             (this.faxSummary!=null &&
              java.util.Arrays.equals(this.faxSummary, other.getFaxSummary())));
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
        if (getHeader() != null) {
            _hashCode += getHeader().hashCode();
        }
        if (getFaxSummary() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFaxSummary());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFaxSummary(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetFaxStatusResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", ">GetFaxStatusResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("header");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "Header"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "Header"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faxSummary");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "FaxSummary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "FaxSummary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}

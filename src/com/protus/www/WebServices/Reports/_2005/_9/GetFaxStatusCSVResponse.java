/**
 * GetFaxStatusCSVResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.WebServices.Reports._2005._9;

public class GetFaxStatusCSVResponse  implements java.io.Serializable {
    private java.lang.String getFaxStatusCSVResult;

    public GetFaxStatusCSVResponse() {
    }

    public GetFaxStatusCSVResponse(
           java.lang.String getFaxStatusCSVResult) {
           this.getFaxStatusCSVResult = getFaxStatusCSVResult;
    }


    /**
     * Gets the getFaxStatusCSVResult value for this GetFaxStatusCSVResponse.
     * 
     * @return getFaxStatusCSVResult
     */
    public java.lang.String getGetFaxStatusCSVResult() {
        return getFaxStatusCSVResult;
    }


    /**
     * Sets the getFaxStatusCSVResult value for this GetFaxStatusCSVResponse.
     * 
     * @param getFaxStatusCSVResult
     */
    public void setGetFaxStatusCSVResult(java.lang.String getFaxStatusCSVResult) {
        this.getFaxStatusCSVResult = getFaxStatusCSVResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetFaxStatusCSVResponse)) return false;
        GetFaxStatusCSVResponse other = (GetFaxStatusCSVResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getFaxStatusCSVResult==null && other.getGetFaxStatusCSVResult()==null) || 
             (this.getFaxStatusCSVResult!=null &&
              this.getFaxStatusCSVResult.equals(other.getGetFaxStatusCSVResult())));
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
        if (getGetFaxStatusCSVResult() != null) {
            _hashCode += getGetFaxStatusCSVResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetFaxStatusCSVResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", ">GetFaxStatusCSVResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getFaxStatusCSVResult");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "GetFaxStatusCSVResult"));
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
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}

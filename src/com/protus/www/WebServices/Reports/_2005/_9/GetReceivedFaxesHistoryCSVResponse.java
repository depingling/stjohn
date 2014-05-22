/**
 * GetReceivedFaxesHistoryCSVResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.WebServices.Reports._2005._9;

public class GetReceivedFaxesHistoryCSVResponse  implements java.io.Serializable {
    private java.lang.String getReceivedFaxesHistoryCSVResult;

    public GetReceivedFaxesHistoryCSVResponse() {
    }

    public GetReceivedFaxesHistoryCSVResponse(
           java.lang.String getReceivedFaxesHistoryCSVResult) {
           this.getReceivedFaxesHistoryCSVResult = getReceivedFaxesHistoryCSVResult;
    }


    /**
     * Gets the getReceivedFaxesHistoryCSVResult value for this GetReceivedFaxesHistoryCSVResponse.
     * 
     * @return getReceivedFaxesHistoryCSVResult
     */
    public java.lang.String getGetReceivedFaxesHistoryCSVResult() {
        return getReceivedFaxesHistoryCSVResult;
    }


    /**
     * Sets the getReceivedFaxesHistoryCSVResult value for this GetReceivedFaxesHistoryCSVResponse.
     * 
     * @param getReceivedFaxesHistoryCSVResult
     */
    public void setGetReceivedFaxesHistoryCSVResult(java.lang.String getReceivedFaxesHistoryCSVResult) {
        this.getReceivedFaxesHistoryCSVResult = getReceivedFaxesHistoryCSVResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetReceivedFaxesHistoryCSVResponse)) return false;
        GetReceivedFaxesHistoryCSVResponse other = (GetReceivedFaxesHistoryCSVResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getReceivedFaxesHistoryCSVResult==null && other.getGetReceivedFaxesHistoryCSVResult()==null) || 
             (this.getReceivedFaxesHistoryCSVResult!=null &&
              this.getReceivedFaxesHistoryCSVResult.equals(other.getGetReceivedFaxesHistoryCSVResult())));
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
        if (getGetReceivedFaxesHistoryCSVResult() != null) {
            _hashCode += getGetReceivedFaxesHistoryCSVResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetReceivedFaxesHistoryCSVResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", ">GetReceivedFaxesHistoryCSVResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getReceivedFaxesHistoryCSVResult");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "GetReceivedFaxesHistoryCSVResult"));
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

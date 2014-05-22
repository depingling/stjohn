/**
 * GetReceivedFaxesHistoryResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.WebServices.Reports._2005._9;

public class GetReceivedFaxesHistoryResponse  implements java.io.Serializable {
    private com.protus.www.Reports.GetReceivedFaxesHistoryResult getReceivedFaxesHistoryResult;

    public GetReceivedFaxesHistoryResponse() {
    }

    public GetReceivedFaxesHistoryResponse(
           com.protus.www.Reports.GetReceivedFaxesHistoryResult getReceivedFaxesHistoryResult) {
           this.getReceivedFaxesHistoryResult = getReceivedFaxesHistoryResult;
    }


    /**
     * Gets the getReceivedFaxesHistoryResult value for this GetReceivedFaxesHistoryResponse.
     * 
     * @return getReceivedFaxesHistoryResult
     */
    public com.protus.www.Reports.GetReceivedFaxesHistoryResult getGetReceivedFaxesHistoryResult() {
        return getReceivedFaxesHistoryResult;
    }


    /**
     * Sets the getReceivedFaxesHistoryResult value for this GetReceivedFaxesHistoryResponse.
     * 
     * @param getReceivedFaxesHistoryResult
     */
    public void setGetReceivedFaxesHistoryResult(com.protus.www.Reports.GetReceivedFaxesHistoryResult getReceivedFaxesHistoryResult) {
        this.getReceivedFaxesHistoryResult = getReceivedFaxesHistoryResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetReceivedFaxesHistoryResponse)) return false;
        GetReceivedFaxesHistoryResponse other = (GetReceivedFaxesHistoryResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getReceivedFaxesHistoryResult==null && other.getGetReceivedFaxesHistoryResult()==null) || 
             (this.getReceivedFaxesHistoryResult!=null &&
              this.getReceivedFaxesHistoryResult.equals(other.getGetReceivedFaxesHistoryResult())));
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
        if (getGetReceivedFaxesHistoryResult() != null) {
            _hashCode += getGetReceivedFaxesHistoryResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetReceivedFaxesHistoryResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", ">GetReceivedFaxesHistoryResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getReceivedFaxesHistoryResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "GetReceivedFaxesHistoryResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", ">GetReceivedFaxesHistoryResult"));
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

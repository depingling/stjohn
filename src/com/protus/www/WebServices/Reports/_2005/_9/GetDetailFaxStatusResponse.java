/**
 * GetDetailFaxStatusResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.WebServices.Reports._2005._9;

public class GetDetailFaxStatusResponse  implements java.io.Serializable {
    private com.protus.www.Reports.GetDetailFaxStatusResult getDetailFaxStatusResult;

    public GetDetailFaxStatusResponse() {
    }

    public GetDetailFaxStatusResponse(
           com.protus.www.Reports.GetDetailFaxStatusResult getDetailFaxStatusResult) {
           this.getDetailFaxStatusResult = getDetailFaxStatusResult;
    }


    /**
     * Gets the getDetailFaxStatusResult value for this GetDetailFaxStatusResponse.
     * 
     * @return getDetailFaxStatusResult
     */
    public com.protus.www.Reports.GetDetailFaxStatusResult getGetDetailFaxStatusResult() {
        return getDetailFaxStatusResult;
    }


    /**
     * Sets the getDetailFaxStatusResult value for this GetDetailFaxStatusResponse.
     * 
     * @param getDetailFaxStatusResult
     */
    public void setGetDetailFaxStatusResult(com.protus.www.Reports.GetDetailFaxStatusResult getDetailFaxStatusResult) {
        this.getDetailFaxStatusResult = getDetailFaxStatusResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetDetailFaxStatusResponse)) return false;
        GetDetailFaxStatusResponse other = (GetDetailFaxStatusResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getDetailFaxStatusResult==null && other.getGetDetailFaxStatusResult()==null) || 
             (this.getDetailFaxStatusResult!=null &&
              this.getDetailFaxStatusResult.equals(other.getGetDetailFaxStatusResult())));
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
        if (getGetDetailFaxStatusResult() != null) {
            _hashCode += getGetDetailFaxStatusResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetDetailFaxStatusResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", ">GetDetailFaxStatusResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getDetailFaxStatusResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "GetDetailFaxStatusResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", ">GetDetailFaxStatusResult"));
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

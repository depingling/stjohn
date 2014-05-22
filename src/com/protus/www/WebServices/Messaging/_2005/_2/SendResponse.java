/**
 * SendResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.WebServices.Messaging._2005._2;

public class SendResponse  implements java.io.Serializable {
    private com.protus.www.WebServices.Messaging._2005._2.WSHeader header;

    private java.lang.String transactionID;

    private int numberOfDestinations;

    public SendResponse() {
    }

    public SendResponse(
           com.protus.www.WebServices.Messaging._2005._2.WSHeader header,
           java.lang.String transactionID,
           int numberOfDestinations) {
           this.header = header;
           this.transactionID = transactionID;
           this.numberOfDestinations = numberOfDestinations;
    }


    /**
     * Gets the header value for this SendResponse.
     * 
     * @return header
     */
    public com.protus.www.WebServices.Messaging._2005._2.WSHeader getHeader() {
        return header;
    }


    /**
     * Sets the header value for this SendResponse.
     * 
     * @param header
     */
    public void setHeader(com.protus.www.WebServices.Messaging._2005._2.WSHeader header) {
        this.header = header;
    }


    /**
     * Gets the transactionID value for this SendResponse.
     * 
     * @return transactionID
     */
    public java.lang.String getTransactionID() {
        return transactionID;
    }


    /**
     * Sets the transactionID value for this SendResponse.
     * 
     * @param transactionID
     */
    public void setTransactionID(java.lang.String transactionID) {
        this.transactionID = transactionID;
    }


    /**
     * Gets the numberOfDestinations value for this SendResponse.
     * 
     * @return numberOfDestinations
     */
    public int getNumberOfDestinations() {
        return numberOfDestinations;
    }


    /**
     * Sets the numberOfDestinations value for this SendResponse.
     * 
     * @param numberOfDestinations
     */
    public void setNumberOfDestinations(int numberOfDestinations) {
        this.numberOfDestinations = numberOfDestinations;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SendResponse)) return false;
        SendResponse other = (SendResponse) obj;
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
            ((this.transactionID==null && other.getTransactionID()==null) || 
             (this.transactionID!=null &&
              this.transactionID.equals(other.getTransactionID()))) &&
            this.numberOfDestinations == other.getNumberOfDestinations();
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
        if (getTransactionID() != null) {
            _hashCode += getTransactionID().hashCode();
        }
        _hashCode += getNumberOfDestinations();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SendResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Messaging/2005/2", "SendResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("header");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Messaging/2005/2", "Header"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Messaging/2005/2", "WSHeader"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionID");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Messaging/2005/2", "TransactionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfDestinations");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Messaging/2005/2", "NumberOfDestinations"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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

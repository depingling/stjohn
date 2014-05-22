/**
 * GetVoiceStatusResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.Reports;

public class GetVoiceStatusResult  implements java.io.Serializable {
    private com.protus.www.Reports.Header header;

    private com.protus.www.Reports.VoiceSummary[] voiceSummary;

    public GetVoiceStatusResult() {
    }

    public GetVoiceStatusResult(
           com.protus.www.Reports.Header header,
           com.protus.www.Reports.VoiceSummary[] voiceSummary) {
           this.header = header;
           this.voiceSummary = voiceSummary;
    }


    /**
     * Gets the header value for this GetVoiceStatusResult.
     * 
     * @return header
     */
    public com.protus.www.Reports.Header getHeader() {
        return header;
    }


    /**
     * Sets the header value for this GetVoiceStatusResult.
     * 
     * @param header
     */
    public void setHeader(com.protus.www.Reports.Header header) {
        this.header = header;
    }


    /**
     * Gets the voiceSummary value for this GetVoiceStatusResult.
     * 
     * @return voiceSummary
     */
    public com.protus.www.Reports.VoiceSummary[] getVoiceSummary() {
        return voiceSummary;
    }


    /**
     * Sets the voiceSummary value for this GetVoiceStatusResult.
     * 
     * @param voiceSummary
     */
    public void setVoiceSummary(com.protus.www.Reports.VoiceSummary[] voiceSummary) {
        this.voiceSummary = voiceSummary;
    }

    public com.protus.www.Reports.VoiceSummary getVoiceSummary(int i) {
        return this.voiceSummary[i];
    }

    public void setVoiceSummary(int i, com.protus.www.Reports.VoiceSummary _value) {
        this.voiceSummary[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetVoiceStatusResult)) return false;
        GetVoiceStatusResult other = (GetVoiceStatusResult) obj;
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
            ((this.voiceSummary==null && other.getVoiceSummary()==null) || 
             (this.voiceSummary!=null &&
              java.util.Arrays.equals(this.voiceSummary, other.getVoiceSummary())));
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
        if (getVoiceSummary() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVoiceSummary());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVoiceSummary(), i);
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
        new org.apache.axis.description.TypeDesc(GetVoiceStatusResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", ">GetVoiceStatusResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("header");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "Header"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "Header"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("voiceSummary");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "VoiceSummary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "VoiceSummary"));
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

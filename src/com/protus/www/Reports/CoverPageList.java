/**
 * CoverPageList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.Reports;

public class CoverPageList  implements java.io.Serializable {
    private com.protus.www.Reports.Header header;

    private com.protus.www.Reports.CoverPage[] coverPage;

    public CoverPageList() {
    }

    public CoverPageList(
           com.protus.www.Reports.Header header,
           com.protus.www.Reports.CoverPage[] coverPage) {
           this.header = header;
           this.coverPage = coverPage;
    }


    /**
     * Gets the header value for this CoverPageList.
     * 
     * @return header
     */
    public com.protus.www.Reports.Header getHeader() {
        return header;
    }


    /**
     * Sets the header value for this CoverPageList.
     * 
     * @param header
     */
    public void setHeader(com.protus.www.Reports.Header header) {
        this.header = header;
    }


    /**
     * Gets the coverPage value for this CoverPageList.
     * 
     * @return coverPage
     */
    public com.protus.www.Reports.CoverPage[] getCoverPage() {
        return coverPage;
    }


    /**
     * Sets the coverPage value for this CoverPageList.
     * 
     * @param coverPage
     */
    public void setCoverPage(com.protus.www.Reports.CoverPage[] coverPage) {
        this.coverPage = coverPage;
    }

    public com.protus.www.Reports.CoverPage getCoverPage(int i) {
        return this.coverPage[i];
    }

    public void setCoverPage(int i, com.protus.www.Reports.CoverPage _value) {
        this.coverPage[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CoverPageList)) return false;
        CoverPageList other = (CoverPageList) obj;
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
            ((this.coverPage==null && other.getCoverPage()==null) || 
             (this.coverPage!=null &&
              java.util.Arrays.equals(this.coverPage, other.getCoverPage())));
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
        if (getCoverPage() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCoverPage());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCoverPage(), i);
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
        new org.apache.axis.description.TypeDesc(CoverPageList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", ">CoverPageList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("header");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "Header"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "Header"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coverPage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "CoverPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "CoverPage"));
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

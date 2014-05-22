/**
 * CoverPage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.Reports;

public class CoverPage  implements java.io.Serializable {
    private java.lang.String coverPageName;

    private java.lang.String coverPageDescription;

    private boolean isShared;

    private boolean isOwned;

    private boolean isDefault;

    private java.util.Calendar timeStamp;

    public CoverPage() {
    }

    public CoverPage(
           java.lang.String coverPageName,
           java.lang.String coverPageDescription,
           boolean isShared,
           boolean isOwned,
           boolean isDefault,
           java.util.Calendar timeStamp) {
           this.coverPageName = coverPageName;
           this.coverPageDescription = coverPageDescription;
           this.isShared = isShared;
           this.isOwned = isOwned;
           this.isDefault = isDefault;
           this.timeStamp = timeStamp;
    }


    /**
     * Gets the coverPageName value for this CoverPage.
     * 
     * @return coverPageName
     */
    public java.lang.String getCoverPageName() {
        return coverPageName;
    }


    /**
     * Sets the coverPageName value for this CoverPage.
     * 
     * @param coverPageName
     */
    public void setCoverPageName(java.lang.String coverPageName) {
        this.coverPageName = coverPageName;
    }


    /**
     * Gets the coverPageDescription value for this CoverPage.
     * 
     * @return coverPageDescription
     */
    public java.lang.String getCoverPageDescription() {
        return coverPageDescription;
    }


    /**
     * Sets the coverPageDescription value for this CoverPage.
     * 
     * @param coverPageDescription
     */
    public void setCoverPageDescription(java.lang.String coverPageDescription) {
        this.coverPageDescription = coverPageDescription;
    }


    /**
     * Gets the isShared value for this CoverPage.
     * 
     * @return isShared
     */
    public boolean isIsShared() {
        return isShared;
    }


    /**
     * Sets the isShared value for this CoverPage.
     * 
     * @param isShared
     */
    public void setIsShared(boolean isShared) {
        this.isShared = isShared;
    }


    /**
     * Gets the isOwned value for this CoverPage.
     * 
     * @return isOwned
     */
    public boolean isIsOwned() {
        return isOwned;
    }


    /**
     * Sets the isOwned value for this CoverPage.
     * 
     * @param isOwned
     */
    public void setIsOwned(boolean isOwned) {
        this.isOwned = isOwned;
    }


    /**
     * Gets the isDefault value for this CoverPage.
     * 
     * @return isDefault
     */
    public boolean isIsDefault() {
        return isDefault;
    }


    /**
     * Sets the isDefault value for this CoverPage.
     * 
     * @param isDefault
     */
    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }


    /**
     * Gets the timeStamp value for this CoverPage.
     * 
     * @return timeStamp
     */
    public java.util.Calendar getTimeStamp() {
        return timeStamp;
    }


    /**
     * Sets the timeStamp value for this CoverPage.
     * 
     * @param timeStamp
     */
    public void setTimeStamp(java.util.Calendar timeStamp) {
        this.timeStamp = timeStamp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CoverPage)) return false;
        CoverPage other = (CoverPage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.coverPageName==null && other.getCoverPageName()==null) || 
             (this.coverPageName!=null &&
              this.coverPageName.equals(other.getCoverPageName()))) &&
            ((this.coverPageDescription==null && other.getCoverPageDescription()==null) || 
             (this.coverPageDescription!=null &&
              this.coverPageDescription.equals(other.getCoverPageDescription()))) &&
            this.isShared == other.isIsShared() &&
            this.isOwned == other.isIsOwned() &&
            this.isDefault == other.isIsDefault() &&
            ((this.timeStamp==null && other.getTimeStamp()==null) || 
             (this.timeStamp!=null &&
              this.timeStamp.equals(other.getTimeStamp())));
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
        if (getCoverPageName() != null) {
            _hashCode += getCoverPageName().hashCode();
        }
        if (getCoverPageDescription() != null) {
            _hashCode += getCoverPageDescription().hashCode();
        }
        _hashCode += (isIsShared() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isIsOwned() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isIsDefault() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getTimeStamp() != null) {
            _hashCode += getTimeStamp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CoverPage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "CoverPage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coverPageName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "CoverPageName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coverPageDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "CoverPageDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isShared");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "IsShared"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isOwned");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "IsOwned"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDefault");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "IsDefault"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "TimeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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

/**
 * GetDetailVoiceStatusCSV.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.WebServices.Reports._2005._9;

public class GetDetailVoiceStatusCSV  implements java.io.Serializable {
    private int userID;

    private java.lang.String userPassword;

    private boolean allUsersFlag;

    private java.lang.String billingCodeFilter;

    private java.lang.String transactionIDListFilter;

    private java.lang.String startTimeStampFilter;

    private java.lang.String endTimeStampFilter;

    private java.lang.String voiceNumbersListFilter;

    public GetDetailVoiceStatusCSV() {
    }

    public GetDetailVoiceStatusCSV(
           int userID,
           java.lang.String userPassword,
           boolean allUsersFlag,
           java.lang.String billingCodeFilter,
           java.lang.String transactionIDListFilter,
           java.lang.String startTimeStampFilter,
           java.lang.String endTimeStampFilter,
           java.lang.String voiceNumbersListFilter) {
           this.userID = userID;
           this.userPassword = userPassword;
           this.allUsersFlag = allUsersFlag;
           this.billingCodeFilter = billingCodeFilter;
           this.transactionIDListFilter = transactionIDListFilter;
           this.startTimeStampFilter = startTimeStampFilter;
           this.endTimeStampFilter = endTimeStampFilter;
           this.voiceNumbersListFilter = voiceNumbersListFilter;
    }


    /**
     * Gets the userID value for this GetDetailVoiceStatusCSV.
     * 
     * @return userID
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this GetDetailVoiceStatusCSV.
     * 
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }


    /**
     * Gets the userPassword value for this GetDetailVoiceStatusCSV.
     * 
     * @return userPassword
     */
    public java.lang.String getUserPassword() {
        return userPassword;
    }


    /**
     * Sets the userPassword value for this GetDetailVoiceStatusCSV.
     * 
     * @param userPassword
     */
    public void setUserPassword(java.lang.String userPassword) {
        this.userPassword = userPassword;
    }


    /**
     * Gets the allUsersFlag value for this GetDetailVoiceStatusCSV.
     * 
     * @return allUsersFlag
     */
    public boolean isAllUsersFlag() {
        return allUsersFlag;
    }


    /**
     * Sets the allUsersFlag value for this GetDetailVoiceStatusCSV.
     * 
     * @param allUsersFlag
     */
    public void setAllUsersFlag(boolean allUsersFlag) {
        this.allUsersFlag = allUsersFlag;
    }


    /**
     * Gets the billingCodeFilter value for this GetDetailVoiceStatusCSV.
     * 
     * @return billingCodeFilter
     */
    public java.lang.String getBillingCodeFilter() {
        return billingCodeFilter;
    }


    /**
     * Sets the billingCodeFilter value for this GetDetailVoiceStatusCSV.
     * 
     * @param billingCodeFilter
     */
    public void setBillingCodeFilter(java.lang.String billingCodeFilter) {
        this.billingCodeFilter = billingCodeFilter;
    }


    /**
     * Gets the transactionIDListFilter value for this GetDetailVoiceStatusCSV.
     * 
     * @return transactionIDListFilter
     */
    public java.lang.String getTransactionIDListFilter() {
        return transactionIDListFilter;
    }


    /**
     * Sets the transactionIDListFilter value for this GetDetailVoiceStatusCSV.
     * 
     * @param transactionIDListFilter
     */
    public void setTransactionIDListFilter(java.lang.String transactionIDListFilter) {
        this.transactionIDListFilter = transactionIDListFilter;
    }


    /**
     * Gets the startTimeStampFilter value for this GetDetailVoiceStatusCSV.
     * 
     * @return startTimeStampFilter
     */
    public java.lang.String getStartTimeStampFilter() {
        return startTimeStampFilter;
    }


    /**
     * Sets the startTimeStampFilter value for this GetDetailVoiceStatusCSV.
     * 
     * @param startTimeStampFilter
     */
    public void setStartTimeStampFilter(java.lang.String startTimeStampFilter) {
        this.startTimeStampFilter = startTimeStampFilter;
    }


    /**
     * Gets the endTimeStampFilter value for this GetDetailVoiceStatusCSV.
     * 
     * @return endTimeStampFilter
     */
    public java.lang.String getEndTimeStampFilter() {
        return endTimeStampFilter;
    }


    /**
     * Sets the endTimeStampFilter value for this GetDetailVoiceStatusCSV.
     * 
     * @param endTimeStampFilter
     */
    public void setEndTimeStampFilter(java.lang.String endTimeStampFilter) {
        this.endTimeStampFilter = endTimeStampFilter;
    }


    /**
     * Gets the voiceNumbersListFilter value for this GetDetailVoiceStatusCSV.
     * 
     * @return voiceNumbersListFilter
     */
    public java.lang.String getVoiceNumbersListFilter() {
        return voiceNumbersListFilter;
    }


    /**
     * Sets the voiceNumbersListFilter value for this GetDetailVoiceStatusCSV.
     * 
     * @param voiceNumbersListFilter
     */
    public void setVoiceNumbersListFilter(java.lang.String voiceNumbersListFilter) {
        this.voiceNumbersListFilter = voiceNumbersListFilter;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetDetailVoiceStatusCSV)) return false;
        GetDetailVoiceStatusCSV other = (GetDetailVoiceStatusCSV) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.userID == other.getUserID() &&
            ((this.userPassword==null && other.getUserPassword()==null) || 
             (this.userPassword!=null &&
              this.userPassword.equals(other.getUserPassword()))) &&
            this.allUsersFlag == other.isAllUsersFlag() &&
            ((this.billingCodeFilter==null && other.getBillingCodeFilter()==null) || 
             (this.billingCodeFilter!=null &&
              this.billingCodeFilter.equals(other.getBillingCodeFilter()))) &&
            ((this.transactionIDListFilter==null && other.getTransactionIDListFilter()==null) || 
             (this.transactionIDListFilter!=null &&
              this.transactionIDListFilter.equals(other.getTransactionIDListFilter()))) &&
            ((this.startTimeStampFilter==null && other.getStartTimeStampFilter()==null) || 
             (this.startTimeStampFilter!=null &&
              this.startTimeStampFilter.equals(other.getStartTimeStampFilter()))) &&
            ((this.endTimeStampFilter==null && other.getEndTimeStampFilter()==null) || 
             (this.endTimeStampFilter!=null &&
              this.endTimeStampFilter.equals(other.getEndTimeStampFilter()))) &&
            ((this.voiceNumbersListFilter==null && other.getVoiceNumbersListFilter()==null) || 
             (this.voiceNumbersListFilter!=null &&
              this.voiceNumbersListFilter.equals(other.getVoiceNumbersListFilter())));
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
        _hashCode += getUserID();
        if (getUserPassword() != null) {
            _hashCode += getUserPassword().hashCode();
        }
        _hashCode += (isAllUsersFlag() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getBillingCodeFilter() != null) {
            _hashCode += getBillingCodeFilter().hashCode();
        }
        if (getTransactionIDListFilter() != null) {
            _hashCode += getTransactionIDListFilter().hashCode();
        }
        if (getStartTimeStampFilter() != null) {
            _hashCode += getStartTimeStampFilter().hashCode();
        }
        if (getEndTimeStampFilter() != null) {
            _hashCode += getEndTimeStampFilter().hashCode();
        }
        if (getVoiceNumbersListFilter() != null) {
            _hashCode += getVoiceNumbersListFilter().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetDetailVoiceStatusCSV.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", ">GetDetailVoiceStatusCSV"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userID");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "UserID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userPassword");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "UserPassword"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allUsersFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "AllUsersFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingCodeFilter");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "BillingCodeFilter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionIDListFilter");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "TransactionIDListFilter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startTimeStampFilter");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "StartTimeStampFilter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endTimeStampFilter");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "EndTimeStampFilter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("voiceNumbersListFilter");
        elemField.setXmlName(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "VoiceNumbersListFilter"));
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

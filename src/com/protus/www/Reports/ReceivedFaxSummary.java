/**
 * ReceivedFaxSummary.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.Reports;

public class ReceivedFaxSummary  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private int accountID;

    private int userID;

    private int transactionID;

    private java.lang.String userFaxNumber;

    private java.util.Calendar dateFaxReceived;

    private java.lang.String originatingFaxNumber;

    private java.lang.String originatingFaxCSID;

    private int numberOfPages;

    private int duration;

    private org.apache.axis.message.MessageElement [] _any;

    public ReceivedFaxSummary() {
    }

    public ReceivedFaxSummary(
           int accountID,
           int userID,
           int transactionID,
           java.lang.String userFaxNumber,
           java.util.Calendar dateFaxReceived,
           java.lang.String originatingFaxNumber,
           java.lang.String originatingFaxCSID,
           int numberOfPages,
           int duration,
           org.apache.axis.message.MessageElement [] _any) {
           this.accountID = accountID;
           this.userID = userID;
           this.transactionID = transactionID;
           this.userFaxNumber = userFaxNumber;
           this.dateFaxReceived = dateFaxReceived;
           this.originatingFaxNumber = originatingFaxNumber;
           this.originatingFaxCSID = originatingFaxCSID;
           this.numberOfPages = numberOfPages;
           this.duration = duration;
           this._any = _any;
    }


    /**
     * Gets the accountID value for this ReceivedFaxSummary.
     * 
     * @return accountID
     */
    public int getAccountID() {
        return accountID;
    }


    /**
     * Sets the accountID value for this ReceivedFaxSummary.
     * 
     * @param accountID
     */
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }


    /**
     * Gets the userID value for this ReceivedFaxSummary.
     * 
     * @return userID
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this ReceivedFaxSummary.
     * 
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }


    /**
     * Gets the transactionID value for this ReceivedFaxSummary.
     * 
     * @return transactionID
     */
    public int getTransactionID() {
        return transactionID;
    }


    /**
     * Sets the transactionID value for this ReceivedFaxSummary.
     * 
     * @param transactionID
     */
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }


    /**
     * Gets the userFaxNumber value for this ReceivedFaxSummary.
     * 
     * @return userFaxNumber
     */
    public java.lang.String getUserFaxNumber() {
        return userFaxNumber;
    }


    /**
     * Sets the userFaxNumber value for this ReceivedFaxSummary.
     * 
     * @param userFaxNumber
     */
    public void setUserFaxNumber(java.lang.String userFaxNumber) {
        this.userFaxNumber = userFaxNumber;
    }


    /**
     * Gets the dateFaxReceived value for this ReceivedFaxSummary.
     * 
     * @return dateFaxReceived
     */
    public java.util.Calendar getDateFaxReceived() {
        return dateFaxReceived;
    }


    /**
     * Sets the dateFaxReceived value for this ReceivedFaxSummary.
     * 
     * @param dateFaxReceived
     */
    public void setDateFaxReceived(java.util.Calendar dateFaxReceived) {
        this.dateFaxReceived = dateFaxReceived;
    }


    /**
     * Gets the originatingFaxNumber value for this ReceivedFaxSummary.
     * 
     * @return originatingFaxNumber
     */
    public java.lang.String getOriginatingFaxNumber() {
        return originatingFaxNumber;
    }


    /**
     * Sets the originatingFaxNumber value for this ReceivedFaxSummary.
     * 
     * @param originatingFaxNumber
     */
    public void setOriginatingFaxNumber(java.lang.String originatingFaxNumber) {
        this.originatingFaxNumber = originatingFaxNumber;
    }


    /**
     * Gets the originatingFaxCSID value for this ReceivedFaxSummary.
     * 
     * @return originatingFaxCSID
     */
    public java.lang.String getOriginatingFaxCSID() {
        return originatingFaxCSID;
    }


    /**
     * Sets the originatingFaxCSID value for this ReceivedFaxSummary.
     * 
     * @param originatingFaxCSID
     */
    public void setOriginatingFaxCSID(java.lang.String originatingFaxCSID) {
        this.originatingFaxCSID = originatingFaxCSID;
    }


    /**
     * Gets the numberOfPages value for this ReceivedFaxSummary.
     * 
     * @return numberOfPages
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }


    /**
     * Sets the numberOfPages value for this ReceivedFaxSummary.
     * 
     * @param numberOfPages
     */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }


    /**
     * Gets the duration value for this ReceivedFaxSummary.
     * 
     * @return duration
     */
    public int getDuration() {
        return duration;
    }


    /**
     * Sets the duration value for this ReceivedFaxSummary.
     * 
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }


    /**
     * Gets the _any value for this ReceivedFaxSummary.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this ReceivedFaxSummary.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReceivedFaxSummary)) return false;
        ReceivedFaxSummary other = (ReceivedFaxSummary) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.accountID == other.getAccountID() &&
            this.userID == other.getUserID() &&
            this.transactionID == other.getTransactionID() &&
            ((this.userFaxNumber==null && other.getUserFaxNumber()==null) || 
             (this.userFaxNumber!=null &&
              this.userFaxNumber.equals(other.getUserFaxNumber()))) &&
            ((this.dateFaxReceived==null && other.getDateFaxReceived()==null) || 
             (this.dateFaxReceived!=null &&
              this.dateFaxReceived.equals(other.getDateFaxReceived()))) &&
            ((this.originatingFaxNumber==null && other.getOriginatingFaxNumber()==null) || 
             (this.originatingFaxNumber!=null &&
              this.originatingFaxNumber.equals(other.getOriginatingFaxNumber()))) &&
            ((this.originatingFaxCSID==null && other.getOriginatingFaxCSID()==null) || 
             (this.originatingFaxCSID!=null &&
              this.originatingFaxCSID.equals(other.getOriginatingFaxCSID()))) &&
            this.numberOfPages == other.getNumberOfPages() &&
            this.duration == other.getDuration() &&
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any())));
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
        _hashCode += getAccountID();
        _hashCode += getUserID();
        _hashCode += getTransactionID();
        if (getUserFaxNumber() != null) {
            _hashCode += getUserFaxNumber().hashCode();
        }
        if (getDateFaxReceived() != null) {
            _hashCode += getDateFaxReceived().hashCode();
        }
        if (getOriginatingFaxNumber() != null) {
            _hashCode += getOriginatingFaxNumber().hashCode();
        }
        if (getOriginatingFaxCSID() != null) {
            _hashCode += getOriginatingFaxCSID().hashCode();
        }
        _hashCode += getNumberOfPages();
        _hashCode += getDuration();
        if (get_any() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_any());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_any(), i);
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
        new org.apache.axis.description.TypeDesc(ReceivedFaxSummary.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "ReceivedFaxSummary"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "AccountID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "UserID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "TransactionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userFaxNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "UserFaxNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateFaxReceived");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "DateFaxReceived"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originatingFaxNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "OriginatingFaxNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originatingFaxCSID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "OriginatingFaxCSID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfPages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "NumberOfPages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("duration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "Duration"));
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

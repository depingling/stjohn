/**
 * FaxDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.Reports;

public class FaxDetail  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private int accountID;

    private int userID;

    private java.lang.String transactionType;

    private int transactionID;

    private int subTransactionID;

    private int statusCode;

    private int errorCodeID;

    private java.lang.String referenceID;

    private java.util.Calendar receivedTimeStamp;

    private java.util.Calendar deliveredTimeStamp;

    private int numberOfPages;

    private java.lang.String toText;

    private java.lang.String destinationNumber;

    private int destinationCountryCode;

    private int duration;

    private int fileSize;

    private int retries;

    private double cost;

    private org.apache.axis.message.MessageElement [] _any;

    public FaxDetail() {
    }

    public FaxDetail(
           int accountID,
           int userID,
           java.lang.String transactionType,
           int transactionID,
           int subTransactionID,
           int statusCode,
           int errorCodeID,
           java.lang.String referenceID,
           java.util.Calendar receivedTimeStamp,
           java.util.Calendar deliveredTimeStamp,
           int numberOfPages,
           java.lang.String toText,
           java.lang.String destinationNumber,
           int destinationCountryCode,
           int duration,
           int fileSize,
           int retries,
           double cost,
           org.apache.axis.message.MessageElement [] _any) {
           this.accountID = accountID;
           this.userID = userID;
           this.transactionType = transactionType;
           this.transactionID = transactionID;
           this.subTransactionID = subTransactionID;
           this.statusCode = statusCode;
           this.errorCodeID = errorCodeID;
           this.referenceID = referenceID;
           this.receivedTimeStamp = receivedTimeStamp;
           this.deliveredTimeStamp = deliveredTimeStamp;
           this.numberOfPages = numberOfPages;
           this.toText = toText;
           this.destinationNumber = destinationNumber;
           this.destinationCountryCode = destinationCountryCode;
           this.duration = duration;
           this.fileSize = fileSize;
           this.retries = retries;
           this.cost = cost;
           this._any = _any;
    }


    /**
     * Gets the accountID value for this FaxDetail.
     * 
     * @return accountID
     */
    public int getAccountID() {
        return accountID;
    }


    /**
     * Sets the accountID value for this FaxDetail.
     * 
     * @param accountID
     */
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }


    /**
     * Gets the userID value for this FaxDetail.
     * 
     * @return userID
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this FaxDetail.
     * 
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }


    /**
     * Gets the transactionType value for this FaxDetail.
     * 
     * @return transactionType
     */
    public java.lang.String getTransactionType() {
        return transactionType;
    }


    /**
     * Sets the transactionType value for this FaxDetail.
     * 
     * @param transactionType
     */
    public void setTransactionType(java.lang.String transactionType) {
        this.transactionType = transactionType;
    }


    /**
     * Gets the transactionID value for this FaxDetail.
     * 
     * @return transactionID
     */
    public int getTransactionID() {
        return transactionID;
    }


    /**
     * Sets the transactionID value for this FaxDetail.
     * 
     * @param transactionID
     */
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }


    /**
     * Gets the subTransactionID value for this FaxDetail.
     * 
     * @return subTransactionID
     */
    public int getSubTransactionID() {
        return subTransactionID;
    }


    /**
     * Sets the subTransactionID value for this FaxDetail.
     * 
     * @param subTransactionID
     */
    public void setSubTransactionID(int subTransactionID) {
        this.subTransactionID = subTransactionID;
    }


    /**
     * Gets the statusCode value for this FaxDetail.
     * 
     * @return statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }


    /**
     * Sets the statusCode value for this FaxDetail.
     * 
     * @param statusCode
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    /**
     * Gets the errorCodeID value for this FaxDetail.
     * 
     * @return errorCodeID
     */
    public int getErrorCodeID() {
        return errorCodeID;
    }


    /**
     * Sets the errorCodeID value for this FaxDetail.
     * 
     * @param errorCodeID
     */
    public void setErrorCodeID(int errorCodeID) {
        this.errorCodeID = errorCodeID;
    }


    /**
     * Gets the referenceID value for this FaxDetail.
     * 
     * @return referenceID
     */
    public java.lang.String getReferenceID() {
        return referenceID;
    }


    /**
     * Sets the referenceID value for this FaxDetail.
     * 
     * @param referenceID
     */
    public void setReferenceID(java.lang.String referenceID) {
        this.referenceID = referenceID;
    }


    /**
     * Gets the receivedTimeStamp value for this FaxDetail.
     * 
     * @return receivedTimeStamp
     */
    public java.util.Calendar getReceivedTimeStamp() {
        return receivedTimeStamp;
    }


    /**
     * Sets the receivedTimeStamp value for this FaxDetail.
     * 
     * @param receivedTimeStamp
     */
    public void setReceivedTimeStamp(java.util.Calendar receivedTimeStamp) {
        this.receivedTimeStamp = receivedTimeStamp;
    }


    /**
     * Gets the deliveredTimeStamp value for this FaxDetail.
     * 
     * @return deliveredTimeStamp
     */
    public java.util.Calendar getDeliveredTimeStamp() {
        return deliveredTimeStamp;
    }


    /**
     * Sets the deliveredTimeStamp value for this FaxDetail.
     * 
     * @param deliveredTimeStamp
     */
    public void setDeliveredTimeStamp(java.util.Calendar deliveredTimeStamp) {
        this.deliveredTimeStamp = deliveredTimeStamp;
    }


    /**
     * Gets the numberOfPages value for this FaxDetail.
     * 
     * @return numberOfPages
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }


    /**
     * Sets the numberOfPages value for this FaxDetail.
     * 
     * @param numberOfPages
     */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }


    /**
     * Gets the toText value for this FaxDetail.
     * 
     * @return toText
     */
    public java.lang.String getToText() {
        return toText;
    }


    /**
     * Sets the toText value for this FaxDetail.
     * 
     * @param toText
     */
    public void setToText(java.lang.String toText) {
        this.toText = toText;
    }


    /**
     * Gets the destinationNumber value for this FaxDetail.
     * 
     * @return destinationNumber
     */
    public java.lang.String getDestinationNumber() {
        return destinationNumber;
    }


    /**
     * Sets the destinationNumber value for this FaxDetail.
     * 
     * @param destinationNumber
     */
    public void setDestinationNumber(java.lang.String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }


    /**
     * Gets the destinationCountryCode value for this FaxDetail.
     * 
     * @return destinationCountryCode
     */
    public int getDestinationCountryCode() {
        return destinationCountryCode;
    }


    /**
     * Sets the destinationCountryCode value for this FaxDetail.
     * 
     * @param destinationCountryCode
     */
    public void setDestinationCountryCode(int destinationCountryCode) {
        this.destinationCountryCode = destinationCountryCode;
    }


    /**
     * Gets the duration value for this FaxDetail.
     * 
     * @return duration
     */
    public int getDuration() {
        return duration;
    }


    /**
     * Sets the duration value for this FaxDetail.
     * 
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }


    /**
     * Gets the fileSize value for this FaxDetail.
     * 
     * @return fileSize
     */
    public int getFileSize() {
        return fileSize;
    }


    /**
     * Sets the fileSize value for this FaxDetail.
     * 
     * @param fileSize
     */
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }


    /**
     * Gets the retries value for this FaxDetail.
     * 
     * @return retries
     */
    public int getRetries() {
        return retries;
    }


    /**
     * Sets the retries value for this FaxDetail.
     * 
     * @param retries
     */
    public void setRetries(int retries) {
        this.retries = retries;
    }


    /**
     * Gets the cost value for this FaxDetail.
     * 
     * @return cost
     */
    public double getCost() {
        return cost;
    }


    /**
     * Sets the cost value for this FaxDetail.
     * 
     * @param cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }


    /**
     * Gets the _any value for this FaxDetail.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this FaxDetail.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FaxDetail)) return false;
        FaxDetail other = (FaxDetail) obj;
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
            ((this.transactionType==null && other.getTransactionType()==null) || 
             (this.transactionType!=null &&
              this.transactionType.equals(other.getTransactionType()))) &&
            this.transactionID == other.getTransactionID() &&
            this.subTransactionID == other.getSubTransactionID() &&
            this.statusCode == other.getStatusCode() &&
            this.errorCodeID == other.getErrorCodeID() &&
            ((this.referenceID==null && other.getReferenceID()==null) || 
             (this.referenceID!=null &&
              this.referenceID.equals(other.getReferenceID()))) &&
            ((this.receivedTimeStamp==null && other.getReceivedTimeStamp()==null) || 
             (this.receivedTimeStamp!=null &&
              this.receivedTimeStamp.equals(other.getReceivedTimeStamp()))) &&
            ((this.deliveredTimeStamp==null && other.getDeliveredTimeStamp()==null) || 
             (this.deliveredTimeStamp!=null &&
              this.deliveredTimeStamp.equals(other.getDeliveredTimeStamp()))) &&
            this.numberOfPages == other.getNumberOfPages() &&
            ((this.toText==null && other.getToText()==null) || 
             (this.toText!=null &&
              this.toText.equals(other.getToText()))) &&
            ((this.destinationNumber==null && other.getDestinationNumber()==null) || 
             (this.destinationNumber!=null &&
              this.destinationNumber.equals(other.getDestinationNumber()))) &&
            this.destinationCountryCode == other.getDestinationCountryCode() &&
            this.duration == other.getDuration() &&
            this.fileSize == other.getFileSize() &&
            this.retries == other.getRetries() &&
            this.cost == other.getCost() &&
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
        if (getTransactionType() != null) {
            _hashCode += getTransactionType().hashCode();
        }
        _hashCode += getTransactionID();
        _hashCode += getSubTransactionID();
        _hashCode += getStatusCode();
        _hashCode += getErrorCodeID();
        if (getReferenceID() != null) {
            _hashCode += getReferenceID().hashCode();
        }
        if (getReceivedTimeStamp() != null) {
            _hashCode += getReceivedTimeStamp().hashCode();
        }
        if (getDeliveredTimeStamp() != null) {
            _hashCode += getDeliveredTimeStamp().hashCode();
        }
        _hashCode += getNumberOfPages();
        if (getToText() != null) {
            _hashCode += getToText().hashCode();
        }
        if (getDestinationNumber() != null) {
            _hashCode += getDestinationNumber().hashCode();
        }
        _hashCode += getDestinationCountryCode();
        _hashCode += getDuration();
        _hashCode += getFileSize();
        _hashCode += getRetries();
        _hashCode += new Double(getCost()).hashCode();
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
        new org.apache.axis.description.TypeDesc(FaxDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "FaxDetail"));
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
        elemField.setFieldName("transactionType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "TransactionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "TransactionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subTransactionID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "SubTransactionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "StatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCodeID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "ErrorCodeID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referenceID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "ReferenceID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receivedTimeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "ReceivedTimeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveredTimeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "DeliveredTimeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfPages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "NumberOfPages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "ToText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "DestinationNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationCountryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "DestinationCountryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("duration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "Duration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "FileSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "Retries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cost");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "Cost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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

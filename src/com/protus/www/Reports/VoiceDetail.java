/**
 * VoiceDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.Reports;

public class VoiceDetail  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private int accountID;

    private int userID;

    private java.lang.String transactionType;

    private int transactionID;

    private int subTransactionID;

    private int statusCode;

    private int errorCodeID;

    private int hardwareResultCode;

    private java.lang.String referenceID;

    private java.util.Calendar receivedTimeStamp;

    private java.util.Calendar deliveredTimeStamp;

    private java.lang.String destinationNumber;

    private java.lang.String destinationNumberExtension;

    private int destinationCountryCode;

    private int callDuration;

    private int transferDuration;

    private double cost;

    private boolean confirmed;

    private boolean doNotCall;

    private java.lang.String transferedTo;

    private java.lang.String digitsPressed;

    private com.protus.www.Reports.SurveyAnswers surveyAnswers;

    private org.apache.axis.message.MessageElement [] _any;

    public VoiceDetail() {
    }

    public VoiceDetail(
           int accountID,
           int userID,
           java.lang.String transactionType,
           int transactionID,
           int subTransactionID,
           int statusCode,
           int errorCodeID,
           int hardwareResultCode,
           java.lang.String referenceID,
           java.util.Calendar receivedTimeStamp,
           java.util.Calendar deliveredTimeStamp,
           java.lang.String destinationNumber,
           java.lang.String destinationNumberExtension,
           int destinationCountryCode,
           int callDuration,
           int transferDuration,
           double cost,
           boolean confirmed,
           boolean doNotCall,
           java.lang.String transferedTo,
           java.lang.String digitsPressed,
           com.protus.www.Reports.SurveyAnswers surveyAnswers,
           org.apache.axis.message.MessageElement [] _any) {
           this.accountID = accountID;
           this.userID = userID;
           this.transactionType = transactionType;
           this.transactionID = transactionID;
           this.subTransactionID = subTransactionID;
           this.statusCode = statusCode;
           this.errorCodeID = errorCodeID;
           this.hardwareResultCode = hardwareResultCode;
           this.referenceID = referenceID;
           this.receivedTimeStamp = receivedTimeStamp;
           this.deliveredTimeStamp = deliveredTimeStamp;
           this.destinationNumber = destinationNumber;
           this.destinationNumberExtension = destinationNumberExtension;
           this.destinationCountryCode = destinationCountryCode;
           this.callDuration = callDuration;
           this.transferDuration = transferDuration;
           this.cost = cost;
           this.confirmed = confirmed;
           this.doNotCall = doNotCall;
           this.transferedTo = transferedTo;
           this.digitsPressed = digitsPressed;
           this.surveyAnswers = surveyAnswers;
           this._any = _any;
    }


    /**
     * Gets the accountID value for this VoiceDetail.
     * 
     * @return accountID
     */
    public int getAccountID() {
        return accountID;
    }


    /**
     * Sets the accountID value for this VoiceDetail.
     * 
     * @param accountID
     */
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }


    /**
     * Gets the userID value for this VoiceDetail.
     * 
     * @return userID
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this VoiceDetail.
     * 
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }


    /**
     * Gets the transactionType value for this VoiceDetail.
     * 
     * @return transactionType
     */
    public java.lang.String getTransactionType() {
        return transactionType;
    }


    /**
     * Sets the transactionType value for this VoiceDetail.
     * 
     * @param transactionType
     */
    public void setTransactionType(java.lang.String transactionType) {
        this.transactionType = transactionType;
    }


    /**
     * Gets the transactionID value for this VoiceDetail.
     * 
     * @return transactionID
     */
    public int getTransactionID() {
        return transactionID;
    }


    /**
     * Sets the transactionID value for this VoiceDetail.
     * 
     * @param transactionID
     */
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }


    /**
     * Gets the subTransactionID value for this VoiceDetail.
     * 
     * @return subTransactionID
     */
    public int getSubTransactionID() {
        return subTransactionID;
    }


    /**
     * Sets the subTransactionID value for this VoiceDetail.
     * 
     * @param subTransactionID
     */
    public void setSubTransactionID(int subTransactionID) {
        this.subTransactionID = subTransactionID;
    }


    /**
     * Gets the statusCode value for this VoiceDetail.
     * 
     * @return statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }


    /**
     * Sets the statusCode value for this VoiceDetail.
     * 
     * @param statusCode
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    /**
     * Gets the errorCodeID value for this VoiceDetail.
     * 
     * @return errorCodeID
     */
    public int getErrorCodeID() {
        return errorCodeID;
    }


    /**
     * Sets the errorCodeID value for this VoiceDetail.
     * 
     * @param errorCodeID
     */
    public void setErrorCodeID(int errorCodeID) {
        this.errorCodeID = errorCodeID;
    }


    /**
     * Gets the hardwareResultCode value for this VoiceDetail.
     * 
     * @return hardwareResultCode
     */
    public int getHardwareResultCode() {
        return hardwareResultCode;
    }


    /**
     * Sets the hardwareResultCode value for this VoiceDetail.
     * 
     * @param hardwareResultCode
     */
    public void setHardwareResultCode(int hardwareResultCode) {
        this.hardwareResultCode = hardwareResultCode;
    }


    /**
     * Gets the referenceID value for this VoiceDetail.
     * 
     * @return referenceID
     */
    public java.lang.String getReferenceID() {
        return referenceID;
    }


    /**
     * Sets the referenceID value for this VoiceDetail.
     * 
     * @param referenceID
     */
    public void setReferenceID(java.lang.String referenceID) {
        this.referenceID = referenceID;
    }


    /**
     * Gets the receivedTimeStamp value for this VoiceDetail.
     * 
     * @return receivedTimeStamp
     */
    public java.util.Calendar getReceivedTimeStamp() {
        return receivedTimeStamp;
    }


    /**
     * Sets the receivedTimeStamp value for this VoiceDetail.
     * 
     * @param receivedTimeStamp
     */
    public void setReceivedTimeStamp(java.util.Calendar receivedTimeStamp) {
        this.receivedTimeStamp = receivedTimeStamp;
    }


    /**
     * Gets the deliveredTimeStamp value for this VoiceDetail.
     * 
     * @return deliveredTimeStamp
     */
    public java.util.Calendar getDeliveredTimeStamp() {
        return deliveredTimeStamp;
    }


    /**
     * Sets the deliveredTimeStamp value for this VoiceDetail.
     * 
     * @param deliveredTimeStamp
     */
    public void setDeliveredTimeStamp(java.util.Calendar deliveredTimeStamp) {
        this.deliveredTimeStamp = deliveredTimeStamp;
    }


    /**
     * Gets the destinationNumber value for this VoiceDetail.
     * 
     * @return destinationNumber
     */
    public java.lang.String getDestinationNumber() {
        return destinationNumber;
    }


    /**
     * Sets the destinationNumber value for this VoiceDetail.
     * 
     * @param destinationNumber
     */
    public void setDestinationNumber(java.lang.String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }


    /**
     * Gets the destinationNumberExtension value for this VoiceDetail.
     * 
     * @return destinationNumberExtension
     */
    public java.lang.String getDestinationNumberExtension() {
        return destinationNumberExtension;
    }


    /**
     * Sets the destinationNumberExtension value for this VoiceDetail.
     * 
     * @param destinationNumberExtension
     */
    public void setDestinationNumberExtension(java.lang.String destinationNumberExtension) {
        this.destinationNumberExtension = destinationNumberExtension;
    }


    /**
     * Gets the destinationCountryCode value for this VoiceDetail.
     * 
     * @return destinationCountryCode
     */
    public int getDestinationCountryCode() {
        return destinationCountryCode;
    }


    /**
     * Sets the destinationCountryCode value for this VoiceDetail.
     * 
     * @param destinationCountryCode
     */
    public void setDestinationCountryCode(int destinationCountryCode) {
        this.destinationCountryCode = destinationCountryCode;
    }


    /**
     * Gets the callDuration value for this VoiceDetail.
     * 
     * @return callDuration
     */
    public int getCallDuration() {
        return callDuration;
    }


    /**
     * Sets the callDuration value for this VoiceDetail.
     * 
     * @param callDuration
     */
    public void setCallDuration(int callDuration) {
        this.callDuration = callDuration;
    }


    /**
     * Gets the transferDuration value for this VoiceDetail.
     * 
     * @return transferDuration
     */
    public int getTransferDuration() {
        return transferDuration;
    }


    /**
     * Sets the transferDuration value for this VoiceDetail.
     * 
     * @param transferDuration
     */
    public void setTransferDuration(int transferDuration) {
        this.transferDuration = transferDuration;
    }


    /**
     * Gets the cost value for this VoiceDetail.
     * 
     * @return cost
     */
    public double getCost() {
        return cost;
    }


    /**
     * Sets the cost value for this VoiceDetail.
     * 
     * @param cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }


    /**
     * Gets the confirmed value for this VoiceDetail.
     * 
     * @return confirmed
     */
    public boolean isConfirmed() {
        return confirmed;
    }


    /**
     * Sets the confirmed value for this VoiceDetail.
     * 
     * @param confirmed
     */
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }


    /**
     * Gets the doNotCall value for this VoiceDetail.
     * 
     * @return doNotCall
     */
    public boolean isDoNotCall() {
        return doNotCall;
    }


    /**
     * Sets the doNotCall value for this VoiceDetail.
     * 
     * @param doNotCall
     */
    public void setDoNotCall(boolean doNotCall) {
        this.doNotCall = doNotCall;
    }


    /**
     * Gets the transferedTo value for this VoiceDetail.
     * 
     * @return transferedTo
     */
    public java.lang.String getTransferedTo() {
        return transferedTo;
    }


    /**
     * Sets the transferedTo value for this VoiceDetail.
     * 
     * @param transferedTo
     */
    public void setTransferedTo(java.lang.String transferedTo) {
        this.transferedTo = transferedTo;
    }


    /**
     * Gets the digitsPressed value for this VoiceDetail.
     * 
     * @return digitsPressed
     */
    public java.lang.String getDigitsPressed() {
        return digitsPressed;
    }


    /**
     * Sets the digitsPressed value for this VoiceDetail.
     * 
     * @param digitsPressed
     */
    public void setDigitsPressed(java.lang.String digitsPressed) {
        this.digitsPressed = digitsPressed;
    }


    /**
     * Gets the surveyAnswers value for this VoiceDetail.
     * 
     * @return surveyAnswers
     */
    public com.protus.www.Reports.SurveyAnswers getSurveyAnswers() {
        return surveyAnswers;
    }


    /**
     * Sets the surveyAnswers value for this VoiceDetail.
     * 
     * @param surveyAnswers
     */
    public void setSurveyAnswers(com.protus.www.Reports.SurveyAnswers surveyAnswers) {
        this.surveyAnswers = surveyAnswers;
    }


    /**
     * Gets the _any value for this VoiceDetail.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this VoiceDetail.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VoiceDetail)) return false;
        VoiceDetail other = (VoiceDetail) obj;
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
            this.hardwareResultCode == other.getHardwareResultCode() &&
            ((this.referenceID==null && other.getReferenceID()==null) || 
             (this.referenceID!=null &&
              this.referenceID.equals(other.getReferenceID()))) &&
            ((this.receivedTimeStamp==null && other.getReceivedTimeStamp()==null) || 
             (this.receivedTimeStamp!=null &&
              this.receivedTimeStamp.equals(other.getReceivedTimeStamp()))) &&
            ((this.deliveredTimeStamp==null && other.getDeliveredTimeStamp()==null) || 
             (this.deliveredTimeStamp!=null &&
              this.deliveredTimeStamp.equals(other.getDeliveredTimeStamp()))) &&
            ((this.destinationNumber==null && other.getDestinationNumber()==null) || 
             (this.destinationNumber!=null &&
              this.destinationNumber.equals(other.getDestinationNumber()))) &&
            ((this.destinationNumberExtension==null && other.getDestinationNumberExtension()==null) || 
             (this.destinationNumberExtension!=null &&
              this.destinationNumberExtension.equals(other.getDestinationNumberExtension()))) &&
            this.destinationCountryCode == other.getDestinationCountryCode() &&
            this.callDuration == other.getCallDuration() &&
            this.transferDuration == other.getTransferDuration() &&
            this.cost == other.getCost() &&
            this.confirmed == other.isConfirmed() &&
            this.doNotCall == other.isDoNotCall() &&
            ((this.transferedTo==null && other.getTransferedTo()==null) || 
             (this.transferedTo!=null &&
              this.transferedTo.equals(other.getTransferedTo()))) &&
            ((this.digitsPressed==null && other.getDigitsPressed()==null) || 
             (this.digitsPressed!=null &&
              this.digitsPressed.equals(other.getDigitsPressed()))) &&
            ((this.surveyAnswers==null && other.getSurveyAnswers()==null) || 
             (this.surveyAnswers!=null &&
              this.surveyAnswers.equals(other.getSurveyAnswers()))) &&
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
        _hashCode += getHardwareResultCode();
        if (getReferenceID() != null) {
            _hashCode += getReferenceID().hashCode();
        }
        if (getReceivedTimeStamp() != null) {
            _hashCode += getReceivedTimeStamp().hashCode();
        }
        if (getDeliveredTimeStamp() != null) {
            _hashCode += getDeliveredTimeStamp().hashCode();
        }
        if (getDestinationNumber() != null) {
            _hashCode += getDestinationNumber().hashCode();
        }
        if (getDestinationNumberExtension() != null) {
            _hashCode += getDestinationNumberExtension().hashCode();
        }
        _hashCode += getDestinationCountryCode();
        _hashCode += getCallDuration();
        _hashCode += getTransferDuration();
        _hashCode += new Double(getCost()).hashCode();
        _hashCode += (isConfirmed() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isDoNotCall() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getTransferedTo() != null) {
            _hashCode += getTransferedTo().hashCode();
        }
        if (getDigitsPressed() != null) {
            _hashCode += getDigitsPressed().hashCode();
        }
        if (getSurveyAnswers() != null) {
            _hashCode += getSurveyAnswers().hashCode();
        }
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
        new org.apache.axis.description.TypeDesc(VoiceDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "VoiceDetail"));
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
        elemField.setFieldName("hardwareResultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "HardwareResultCode"));
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
        elemField.setFieldName("destinationNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "DestinationNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationNumberExtension");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "DestinationNumberExtension"));
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
        elemField.setFieldName("callDuration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "CallDuration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transferDuration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "TransferDuration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cost");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "Cost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("confirmed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "Confirmed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doNotCall");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "DoNotCall"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transferedTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "TransferedTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("digitsPressed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "DigitsPressed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surveyAnswers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "SurveyAnswers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "SurveyAnswers"));
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

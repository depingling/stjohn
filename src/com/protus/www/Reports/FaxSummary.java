/**
 * FaxSummary.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.Reports;

public class FaxSummary  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private int accountID;

    private int userID;

    private java.lang.String transactionType;

    private int transactionID;

    private int statusCode;

    private java.lang.String billingCode;

    private java.util.Calendar receivedTimeStamp;

    private java.util.Calendar releaseTimeStamp;

    private java.util.Calendar completedTimeStamp;

    private int pagesPerDestination;

    private int numberOfDestinations;

    private int numberOfSuccessful;

    private int numberOfFailed;

    private int totalDuration;

    private double totalCost;

    private org.apache.axis.message.MessageElement [] _any;

    public FaxSummary() {
    }

    public FaxSummary(
           int accountID,
           int userID,
           java.lang.String transactionType,
           int transactionID,
           int statusCode,
           java.lang.String billingCode,
           java.util.Calendar receivedTimeStamp,
           java.util.Calendar releaseTimeStamp,
           java.util.Calendar completedTimeStamp,
           int pagesPerDestination,
           int numberOfDestinations,
           int numberOfSuccessful,
           int numberOfFailed,
           int totalDuration,
           double totalCost,
           org.apache.axis.message.MessageElement [] _any) {
           this.accountID = accountID;
           this.userID = userID;
           this.transactionType = transactionType;
           this.transactionID = transactionID;
           this.statusCode = statusCode;
           this.billingCode = billingCode;
           this.receivedTimeStamp = receivedTimeStamp;
           this.releaseTimeStamp = releaseTimeStamp;
           this.completedTimeStamp = completedTimeStamp;
           this.pagesPerDestination = pagesPerDestination;
           this.numberOfDestinations = numberOfDestinations;
           this.numberOfSuccessful = numberOfSuccessful;
           this.numberOfFailed = numberOfFailed;
           this.totalDuration = totalDuration;
           this.totalCost = totalCost;
           this._any = _any;
    }


    /**
     * Gets the accountID value for this FaxSummary.
     * 
     * @return accountID
     */
    public int getAccountID() {
        return accountID;
    }


    /**
     * Sets the accountID value for this FaxSummary.
     * 
     * @param accountID
     */
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }


    /**
     * Gets the userID value for this FaxSummary.
     * 
     * @return userID
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this FaxSummary.
     * 
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }


    /**
     * Gets the transactionType value for this FaxSummary.
     * 
     * @return transactionType
     */
    public java.lang.String getTransactionType() {
        return transactionType;
    }


    /**
     * Sets the transactionType value for this FaxSummary.
     * 
     * @param transactionType
     */
    public void setTransactionType(java.lang.String transactionType) {
        this.transactionType = transactionType;
    }


    /**
     * Gets the transactionID value for this FaxSummary.
     * 
     * @return transactionID
     */
    public int getTransactionID() {
        return transactionID;
    }


    /**
     * Sets the transactionID value for this FaxSummary.
     * 
     * @param transactionID
     */
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }


    /**
     * Gets the statusCode value for this FaxSummary.
     * 
     * @return statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }


    /**
     * Sets the statusCode value for this FaxSummary.
     * 
     * @param statusCode
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    /**
     * Gets the billingCode value for this FaxSummary.
     * 
     * @return billingCode
     */
    public java.lang.String getBillingCode() {
        return billingCode;
    }


    /**
     * Sets the billingCode value for this FaxSummary.
     * 
     * @param billingCode
     */
    public void setBillingCode(java.lang.String billingCode) {
        this.billingCode = billingCode;
    }


    /**
     * Gets the receivedTimeStamp value for this FaxSummary.
     * 
     * @return receivedTimeStamp
     */
    public java.util.Calendar getReceivedTimeStamp() {
        return receivedTimeStamp;
    }


    /**
     * Sets the receivedTimeStamp value for this FaxSummary.
     * 
     * @param receivedTimeStamp
     */
    public void setReceivedTimeStamp(java.util.Calendar receivedTimeStamp) {
        this.receivedTimeStamp = receivedTimeStamp;
    }


    /**
     * Gets the releaseTimeStamp value for this FaxSummary.
     * 
     * @return releaseTimeStamp
     */
    public java.util.Calendar getReleaseTimeStamp() {
        return releaseTimeStamp;
    }


    /**
     * Sets the releaseTimeStamp value for this FaxSummary.
     * 
     * @param releaseTimeStamp
     */
    public void setReleaseTimeStamp(java.util.Calendar releaseTimeStamp) {
        this.releaseTimeStamp = releaseTimeStamp;
    }


    /**
     * Gets the completedTimeStamp value for this FaxSummary.
     * 
     * @return completedTimeStamp
     */
    public java.util.Calendar getCompletedTimeStamp() {
        return completedTimeStamp;
    }


    /**
     * Sets the completedTimeStamp value for this FaxSummary.
     * 
     * @param completedTimeStamp
     */
    public void setCompletedTimeStamp(java.util.Calendar completedTimeStamp) {
        this.completedTimeStamp = completedTimeStamp;
    }


    /**
     * Gets the pagesPerDestination value for this FaxSummary.
     * 
     * @return pagesPerDestination
     */
    public int getPagesPerDestination() {
        return pagesPerDestination;
    }


    /**
     * Sets the pagesPerDestination value for this FaxSummary.
     * 
     * @param pagesPerDestination
     */
    public void setPagesPerDestination(int pagesPerDestination) {
        this.pagesPerDestination = pagesPerDestination;
    }


    /**
     * Gets the numberOfDestinations value for this FaxSummary.
     * 
     * @return numberOfDestinations
     */
    public int getNumberOfDestinations() {
        return numberOfDestinations;
    }


    /**
     * Sets the numberOfDestinations value for this FaxSummary.
     * 
     * @param numberOfDestinations
     */
    public void setNumberOfDestinations(int numberOfDestinations) {
        this.numberOfDestinations = numberOfDestinations;
    }


    /**
     * Gets the numberOfSuccessful value for this FaxSummary.
     * 
     * @return numberOfSuccessful
     */
    public int getNumberOfSuccessful() {
        return numberOfSuccessful;
    }


    /**
     * Sets the numberOfSuccessful value for this FaxSummary.
     * 
     * @param numberOfSuccessful
     */
    public void setNumberOfSuccessful(int numberOfSuccessful) {
        this.numberOfSuccessful = numberOfSuccessful;
    }


    /**
     * Gets the numberOfFailed value for this FaxSummary.
     * 
     * @return numberOfFailed
     */
    public int getNumberOfFailed() {
        return numberOfFailed;
    }


    /**
     * Sets the numberOfFailed value for this FaxSummary.
     * 
     * @param numberOfFailed
     */
    public void setNumberOfFailed(int numberOfFailed) {
        this.numberOfFailed = numberOfFailed;
    }


    /**
     * Gets the totalDuration value for this FaxSummary.
     * 
     * @return totalDuration
     */
    public int getTotalDuration() {
        return totalDuration;
    }


    /**
     * Sets the totalDuration value for this FaxSummary.
     * 
     * @param totalDuration
     */
    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }


    /**
     * Gets the totalCost value for this FaxSummary.
     * 
     * @return totalCost
     */
    public double getTotalCost() {
        return totalCost;
    }


    /**
     * Sets the totalCost value for this FaxSummary.
     * 
     * @param totalCost
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }


    /**
     * Gets the _any value for this FaxSummary.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this FaxSummary.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FaxSummary)) return false;
        FaxSummary other = (FaxSummary) obj;
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
            this.statusCode == other.getStatusCode() &&
            ((this.billingCode==null && other.getBillingCode()==null) || 
             (this.billingCode!=null &&
              this.billingCode.equals(other.getBillingCode()))) &&
            ((this.receivedTimeStamp==null && other.getReceivedTimeStamp()==null) || 
             (this.receivedTimeStamp!=null &&
              this.receivedTimeStamp.equals(other.getReceivedTimeStamp()))) &&
            ((this.releaseTimeStamp==null && other.getReleaseTimeStamp()==null) || 
             (this.releaseTimeStamp!=null &&
              this.releaseTimeStamp.equals(other.getReleaseTimeStamp()))) &&
            ((this.completedTimeStamp==null && other.getCompletedTimeStamp()==null) || 
             (this.completedTimeStamp!=null &&
              this.completedTimeStamp.equals(other.getCompletedTimeStamp()))) &&
            this.pagesPerDestination == other.getPagesPerDestination() &&
            this.numberOfDestinations == other.getNumberOfDestinations() &&
            this.numberOfSuccessful == other.getNumberOfSuccessful() &&
            this.numberOfFailed == other.getNumberOfFailed() &&
            this.totalDuration == other.getTotalDuration() &&
            this.totalCost == other.getTotalCost() &&
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
        _hashCode += getStatusCode();
        if (getBillingCode() != null) {
            _hashCode += getBillingCode().hashCode();
        }
        if (getReceivedTimeStamp() != null) {
            _hashCode += getReceivedTimeStamp().hashCode();
        }
        if (getReleaseTimeStamp() != null) {
            _hashCode += getReleaseTimeStamp().hashCode();
        }
        if (getCompletedTimeStamp() != null) {
            _hashCode += getCompletedTimeStamp().hashCode();
        }
        _hashCode += getPagesPerDestination();
        _hashCode += getNumberOfDestinations();
        _hashCode += getNumberOfSuccessful();
        _hashCode += getNumberOfFailed();
        _hashCode += getTotalDuration();
        _hashCode += new Double(getTotalCost()).hashCode();
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
        new org.apache.axis.description.TypeDesc(FaxSummary.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "FaxSummary"));
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
        elemField.setFieldName("statusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "StatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "BillingCode"));
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
        elemField.setFieldName("releaseTimeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "ReleaseTimeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("completedTimeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "CompletedTimeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pagesPerDestination");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "PagesPerDestination"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfDestinations");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "NumberOfDestinations"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfSuccessful");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "NumberOfSuccessful"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfFailed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "NumberOfFailed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalDuration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "TotalDuration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCost");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "TotalCost"));
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

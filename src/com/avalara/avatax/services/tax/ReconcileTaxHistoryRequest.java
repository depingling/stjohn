/**
 * ReconcileTaxHistoryRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 *  Data to pass to {@link TaxSvcSoap#reconcileTaxHistory(ReconcileTaxHistoryRequest)}
 * @see ReconcileTaxHistoryResult
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class ReconcileTaxHistoryRequest  implements java.io.Serializable {

    private java.lang.String companyCode;

    private java.lang.String lastDocId;

    /**
     * Indicates the items through {@link ReconcileTaxHistoryRequest#getLastDocId} have been reconciled
     * in the client system and should be marked as reconciled in the AvaTax system.
     * <p>
     * Set this to true to indicate that records through {@link ReconcileTaxHistoryRequest#getLastDocId}
     * should be flagged as reconciled on the server.
     * </p>
     */
    private boolean reconciled;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private com.avalara.avatax.services.tax.DocStatus docStatus;

    /**
     * Initializes a new instance of the class.
     */
    public ReconcileTaxHistoryRequest() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param companyCode
     * @param lastDocId
     * @param reconciled
     */
    private ReconcileTaxHistoryRequest(
            java.lang.String companyCode,
            java.lang.String lastDocId,
            boolean reconciled,
            java.util.Date startDate,
            java.util.Date endDate,
            com.avalara.avatax.services.tax.DocStatus docStatus) {
        this.companyCode = companyCode;
        this.lastDocId = lastDocId;
        this.reconciled = reconciled;
        this.startDate = startDate;
        this.endDate = endDate;
        this.docStatus = docStatus;
    }


    /**
     * Gets the client application company reference code.
     * <p>
     * If DocId is specified, this is not needed.
     * </p>
     *
     * @return companyCode
     */
    public java.lang.String getCompanyCode() {
        return companyCode;
    }


    /**
     * Sets the client application company reference code.
     * <p>
     * If DocId is specified, this is not needed.
     * </p>
     *
     * @param companyCode
     */
    public void setCompanyCode(java.lang.String companyCode) {
        this.companyCode = companyCode;
    }


    /**
     * Used to seek for additional results in large result sets.
     * Set the value to the result of <b>LastDocId</b> from the previous {@link ReconcileTaxHistoryResult}
     * to get the next set of results.
     * <p>
     * If {@link ReconcileTaxHistoryRequest#isReconciled} is set to true, then all records up to and
     * including <b>LastDocId</b> will be flagged as reconciled prior to returning the next result set.
     * </p>
     *
     * @return lastDocId
     */
    public java.lang.String getLastDocId() {
        return lastDocId;
    }


    /**
     * Used to seek for additional results in large result sets.
     * Set the value to the result of <b>LastDocId</b> from the previous {@link ReconcileTaxHistoryResult}
     * to get the next set of results.
     * <p>
     * If {@link ReconcileTaxHistoryRequest#isReconciled} is set to true, then all records up to and
     * including <b>LastDocId</b> will be flagged as reconciled prior to returning the next result set.
     * </p>
     *
     * @param lastDocId
     */
    public void setLastDocId(java.lang.String lastDocId) {
        this.lastDocId = lastDocId;
    }


    /**
     * Indicates the items through {@link ReconcileTaxHistoryRequest#getLastDocId} have been reconciled
     * in the client system and should be marked as reconciled in the AvaTax system.
     * <p>
     * Set this to true to indicate that records through {@link ReconcileTaxHistoryRequest#getLastDocId}
     * should be flagged as reconciled on the server.
     * </p>
     *
     * @return reconciled
     */
    public boolean isReconciled() {
        return reconciled;
    }


    /**
     * Indicates the items through {@link ReconcileTaxHistoryRequest#getLastDocId} have been reconciled
     * in the client system and should be marked as reconciled in the AvaTax system.
     * <p>
     * Set this to true to indicate that records through {@link ReconcileTaxHistoryRequest#getLastDocId}
     * should be flagged as reconciled on the server.
     * </p>
     *
     * @param reconciled
     */
    public void setReconciled(boolean reconciled) {
        this.reconciled = reconciled;
    }


    /**
     * Gets the startDate value for this ReconcileTaxHistoryRequest.
     *
     * @return startDate
     */
    public java.util.Date getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value to retrieve data FROM a specific date.
     *
     * @param startDate
     */
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the endDate value for this ReconcileTaxHistoryRequest.
     *
     * @return endDate
     */
    public java.util.Date getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value to retrieve data FROM a specific date.
     *
     * @param endDate
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the docStatus value for this ReconcileTaxHistoryRequest.     
     * @return docStatus
     */
    public com.avalara.avatax.services.tax.DocStatus getDocStatus() {
        return docStatus;
    }


    /**
     * Sets the value to retrieve data with a specific DocStatus.
     *
     * @param docStatus
     */
    public void setDocStatus(com.avalara.avatax.services.tax.DocStatus docStatus) {
        this.docStatus = docStatus;
    }

    private java.lang.Object __equalsCalc = null;


    /**
     * Determines whether the specified Object is equal to the current Object.
     * Note: In current implementation all Java Strings members of the two
     * objects must be exactly alike, including in case, for equal to return true.
     * @param obj
     * @return true or false, indicating if the two objects are equal.
     */
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReconcileTaxHistoryRequest)) return false;
        ReconcileTaxHistoryRequest other = (ReconcileTaxHistoryRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.companyCode==null && other.getCompanyCode()==null) ||
                        (this.companyCode!=null &&
                                this.companyCode.equals(other.getCompanyCode()))) &&
                ((this.lastDocId==null && other.getLastDocId()==null) ||
                        (this.lastDocId!=null &&
                                this.lastDocId.equals(other.getLastDocId()))) &&
                this.reconciled == other.isReconciled() &&
                ((this.startDate==null && other.getStartDate()==null) ||
                        (this.startDate!=null &&
                                this.startDate.equals(other.getStartDate()))) &&
                ((this.endDate==null && other.getEndDate()==null) ||
                        (this.endDate!=null &&
                                this.endDate.equals(other.getEndDate()))) &&
                ((this.docStatus==null && other.getDocStatus()==null) ||
                        (this.docStatus!=null &&
                                this.docStatus.equals(other.getDocStatus())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    /**
     *  Serves as a hash function for a particular type,
     * suitable for use in hashing algorithms and data
     * structures like a hash table.
     * @return  hash code for this GetTaxRequest object
     * @see java.lang.Object#hashCode
     */
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCompanyCode() != null) {
            _hashCode += getCompanyCode().hashCode();
        }
        if (getLastDocId() != null) {
            _hashCode += getLastDocId().hashCode();
        }
        _hashCode += (isReconciled() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getDocStatus() != null) {
            _hashCode += getDocStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ReconcileTaxHistoryRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ReconcileTaxHistoryRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CompanyCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastDocId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "LastDocId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reconciled");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Reconciled"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "StartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "EndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocStatus"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return Axis type metadata object; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @return Type Description
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }


    /**
     * Get Axis Custom Serializer; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @param mechType
     * @param _javaType
     * @param _xmlType
     * @return Serializer
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
     * Get Axis Custom Deserializer; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @param mechType
     * @param _javaType
     * @param _xmlType
     * @return Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new  com.avalara.avatax.services.base.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}

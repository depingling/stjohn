/**
 * SearchTaxHistoryResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Result data returned from {@link TaxSvcSoap#reconcileTaxHistory}.
 * This class encapsulates the data and methods used by {@link ReconcileTaxHistoryResult}.
 * @see ReconcileTaxHistoryRequest
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class SearchTaxHistoryResult  extends com.avalara.avatax.services.tax.BaseResult  implements java.io.Serializable {

    private com.avalara.avatax.services.tax.ArrayOfGetTaxResult getTaxResults;

    private java.lang.String lastDocId;

    /**
     * Initializes a new instance of the class.
     */
    public SearchTaxHistoryResult() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param getTaxResults
     * @param lastDocId
     */
    private SearchTaxHistoryResult(
            com.avalara.avatax.services.tax.ArrayOfGetTaxResult getTaxResults,
            java.lang.String lastDocId) {
        this.getTaxResults = getTaxResults;
        this.lastDocId = lastDocId;
    }


    /**
     * Gets zero or more {@link GetTaxResult} summaries matching search criteria.
     * <p>
     * If <b>LastDocId</b> was not specified by the {@link ReconcileTaxHistoryRequest},
     * then this is the first set of records that need reconciliation. If <b>LastDocId</b> was specified,
     * the collection represents the next set of records after <b>LastDocId</b>. If the collection is
     * empty, then all records have been reconciled and the result's <b>LastDocId</b> will be set to the
     * last record of the last result set.
     * <br>
     * The GetTaxResults are returned in an Axis wrapper {@link ArrayOfGetTaxResult}, which has a
     * raw GetTaxResult[] array accessible via its {@link ArrayOfGetTaxResult#getGetTaxResult} method.
     * <pre>
     * <b>Example:</b>
     * [Java]
     * ReconcileTaxHistoryResult result = new taxSvc.reconcileTaxHistory(request);
     * ArrayOfGetTaxResult resultArr = result.getGetTaxResults();
     * int numTaxResults = (resultArr == null ||
     *         resultArr.getGetTaxResult() == null ? 0 :
     *         resultArr.getGetTaxResult().length);
     * for (int i = 0 ; i < numTaxResults; i++)
     * {
     *      GetTaxResult taxResult = resultArr.getGetTaxResult(i);
     *      ...
     * }
     *
     * </pre>
     * @return getTaxResults
     */
    public com.avalara.avatax.services.tax.ArrayOfGetTaxResult getGetTaxResults() {
        return getTaxResults;
    }


    /**
     * Sets zero or more {@link GetTaxResult} summaries matching search criteria.
     * <p>
     * If <b>LastDocId</b> was not specified by the {@link ReconcileTaxHistoryRequest},
     * then this is the first set of records that need reconciliation. If <b>LastDocId</b> was specified,
     * the collection represents the next set of records after <b>LastDocId</b>. If the collection is
     * empty, then all records have been reconciled and the result's <b>LastDocId</b> will be set to the
     * last record of the last result set.
     * <br>
     * The GetTaxResults are returned in an Axis wrapper {@link ArrayOfGetTaxResult}, which has a
     * raw GetTaxResult[] array accessible via its {@link ArrayOfGetTaxResult#getGetTaxResult} method.
     * <pre>
     * <b>Example:</b>
     * [Java]
     * ReconcileTaxHistoryResult result = new taxSvc.reconcileTaxHistory(request);
     * ArrayOfGetTaxResult resultArr = result.getGetTaxResults();
     * int numTaxResults = (resultArr == null ||
     *         resultArr.getGetTaxResult() == null ? 0 :
     *         resultArr.getGetTaxResult().length);
     * for (int i = 0 ; i < numTaxResults; i++)
     * {
     *      GetTaxResult taxResult = resultArr.getGetTaxResult(i);
     *      ...
     * }
     *
     * </pre>
     *
     * @param getTaxResults
     */
    public void setGetTaxResults(com.avalara.avatax.services.tax.ArrayOfGetTaxResult getTaxResults) {
        this.getTaxResults = getTaxResults;
    }


    /**
     * Indicates the last Document Code ({@link GetTaxResult#getDocId}) the results list.
     * <p>
     * If {@link #getGetTaxResults} is not empty, then this
     * <b>LastDocId</b> should be passed to the next {@link ReconcileTaxHistoryRequest}.
     * If {@link #getGetTaxResults} is empty, then this <b>LastDocId</b> can be
     * passed to {@link ReconcileTaxHistoryRequest} with the request's
     * {@link ReconcileTaxHistoryRequest#isReconciled} flag
     * set to true in order to reconcile all documents up to and including the LastDocId.
     * </p>
     *
     * @see ReconcileTaxHistoryResult
     * @return lastDocId
     */
    public java.lang.String getLastDocId() {
        return lastDocId;
    }


    /**
     * Indicates the last Document Code ({@link GetTaxResult#getDocId}) the results list.
     * <p>
     * If {@link #getGetTaxResults} is not empty, then this
     * <b>LastDocId</b> should be passed to the next {@link ReconcileTaxHistoryRequest}.
     * If {@link #getGetTaxResults} is empty, then this <b>LastDocId</b> can be
     * passed to {@link ReconcileTaxHistoryRequest} with the request's
     * {@link ReconcileTaxHistoryRequest#isReconciled} flag
     * set to true in order to reconcile all documents up to and including the LastDocId.
     * </p>
     * @see ReconcileTaxHistoryResult
     *
     * @param lastDocId
     */
    public void setLastDocId(java.lang.String lastDocId) {
        this.lastDocId = lastDocId;
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
        if (!(obj instanceof SearchTaxHistoryResult)) return false;
        SearchTaxHistoryResult other = (SearchTaxHistoryResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) &&
                ((this.getTaxResults==null && other.getGetTaxResults()==null) ||
                        (this.getTaxResults!=null &&
                                this.getTaxResults.equals(other.getGetTaxResults()))) &&
                ((this.lastDocId==null && other.getLastDocId()==null) ||
                        (this.lastDocId!=null &&
                                this.lastDocId.equals(other.getLastDocId())));
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
        int _hashCode = super.hashCode();
        if (getGetTaxResults() != null) {
            _hashCode += getGetTaxResults().hashCode();
        }
        if (getLastDocId() != null) {
            _hashCode += getLastDocId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(SearchTaxHistoryResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "SearchTaxHistoryResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getTaxResults");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxResults"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfGetTaxResult"));
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

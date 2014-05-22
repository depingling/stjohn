/**
 * TaxLine.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Taxes for a specific document {@link Line}.
 * @see ArrayOfTaxLine
 * @see GetTaxResult
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class TaxLine  implements java.io.Serializable {

    private java.lang.String no;

    private java.lang.String taxCode;

    private boolean taxability;

    private com.avalara.avatax.services.tax.BoundaryLevel boundaryLevel;

    private java.math.BigDecimal exemption;

    private java.math.BigDecimal discount;

    private java.math.BigDecimal taxable;

    private java.math.BigDecimal rate;

    private java.math.BigDecimal tax;
    private java.math.BigDecimal taxCalculated;

    private com.avalara.avatax.services.tax.ArrayOfTaxDetail taxDetails;
    private int exemptCertId;

    private java.util.Date taxDate;
    private java.util.Date reportingDate;
    private com.avalara.avatax.services.tax.AccountingMethod accountingMethod;
    private boolean taxIncluded;

    /**
     * Initializes a new instance of the class.
     */
    public TaxLine() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param no
     * @param taxCode
     * @param taxability
     * @param boundaryLevel
     * @param exemption
     * @param discount
     * @param taxable
     * @param rate
     * @param tax
     * @param taxDetails
     */
    private TaxLine(
            java.lang.String no,
            java.lang.String taxCode,
            boolean taxability,
            com.avalara.avatax.services.tax.BoundaryLevel boundaryLevel,
            java.math.BigDecimal exemption,
            java.math.BigDecimal discount,
            java.math.BigDecimal taxable,
            java.math.BigDecimal rate,
            java.math.BigDecimal tax,
            com.avalara.avatax.services.tax.ArrayOfTaxDetail taxDetails,
            int exemptCertId) {
        this.no = no;
        this.taxCode = taxCode;
        this.taxability = taxability;
        this.boundaryLevel = boundaryLevel;
        this.exemption = exemption;
        this.discount = discount;
        this.taxable = taxable;
        this.rate = rate;
        this.tax = tax;
        this.taxDetails = taxDetails;
        this.exemptCertId = exemptCertId;
    }


    /**
     *  Line Number.
     * <p>
     * <b>Line</b>s are added to a {@link GetTaxRequest} object when preparing a document for tax calculation.
     *  The line <b>No</b> unqiuely identifies a particular line item for the client.
     * </p>
     * <p>
     * <b>Example:</b>
     * <pre>
     * [Java]
     *  GetTaxRequest request = new GetTaxRequest();
     *  Line line = new Line(request);
     *  line.setNo("01");
     *  line = new Line(request);
     *  line.setNo("02");
     *
     *  </pre>
     *
     * @return no
     */
    public java.lang.String getNo() {
        return no;
    }


    /**
     *  Line Number.
     * <p>
     * <b>Line</b>s are added to a {@link GetTaxRequest} object when preparing a document for tax calculation.
     *  The line <b>No</b> unqiuely identifies a particular line item for the client.
     * </p>
     * <p>
     * <b>Example:</b>
     * <pre>
     * [Java]
     *  GetTaxRequest request = new GetTaxRequest();
     *  Line line = new Line(request);
     *  line.setNo("01");
     *  line = new Line(request);
     *  line.setNo("02");
     *
     *  </pre>
     *
     * @param no
     */
    public void setNo(java.lang.String no) {
        this.no = no;
    }


    /**
     *  System Tax Code.
     *
     * @return taxCode
     */
    public java.lang.String getTaxCode() {
        return taxCode;
    }


    /**
     *  System Tax Code.
     *
     * @param taxCode
     */
    public void setTaxCode(java.lang.String taxCode) {
        this.taxCode = taxCode;
    }


    /**
     *  Is the item taxable?
     *
     * @return taxability
     */
    public boolean isTaxability() {
        return taxability;
    }


    /**
     *  Is the item taxable?
     *
     * @param taxability
     */
    public void setTaxability(boolean taxability) {
        this.taxability = taxability;
    }


    /**
     *  The level of jurisdiction boundary precision used for the tax calculation.
     *
     * @return boundaryLevel
     */
    public com.avalara.avatax.services.tax.BoundaryLevel getBoundaryLevel() {
        return boundaryLevel;
    }


    /**
     *  The level of jurisdiction boundary precision used for the tax calculation.
     *
     * @param boundaryLevel
     */
    public void setBoundaryLevel(com.avalara.avatax.services.tax.BoundaryLevel boundaryLevel) {
        this.boundaryLevel = boundaryLevel;
    }


    /**
     * Exempt amount for this line.
     *
     * @return exemption
     * @deprecated See {@link TaxDetail#getExemption}.
     */
    public java.math.BigDecimal getExemption() {
        return exemption;
    }


    /**
     * Exempt amount for this line.
     *
     * @param exemption
     * @deprecated See {@link TaxDetail#setExemption}.
     */
    public void setExemption(java.math.BigDecimal exemption) {
        this.exemption = exemption;
    }


    /**
     * Discount amount applied to this line
     *
     * @return discount
     */
    public java.math.BigDecimal getDiscount() {
        return discount;
    }


    /**
     * Discount amount applied to this line
     *
     * @param discount
     */
    public void setDiscount(java.math.BigDecimal discount) {
        this.discount = discount;
    }


    /**
     * The tax base, i.e. the taxable amount based on the discount and tax rules
     *
     * @return taxable
     * @deprecated See {@link TaxDetail#getTaxable}.
     */
    public java.math.BigDecimal getTaxable() {
        return taxable;
    }


    /**
     * The tax base, i.e. the taxable amount based on the discount and tax rules
     *
     * @param taxable
     * @deprecated See {@link TaxDetail#setTaxable}.
     */
    public void setTaxable(java.math.BigDecimal taxable) {
        this.taxable = taxable;
    }


    /**
     * The tax rate percentage (0.0 - 1.0).
     *
     * @return rate
     * @deprecated See {@link TaxDetail#getRate}.
     */
    public java.math.BigDecimal getRate() {
        return rate;
    }


    /**
     * The tax rate percentage (0.0 - 1.0).
     *
     * @param rate
     * @deprecated See {@link TaxDetail#setRate}.
     */
    public void setRate(java.math.BigDecimal rate) {
        this.rate = rate;
    }


    /**
     *  The tax amount, i.e.  the calculated tax amount
     * ({@link TaxLine#getTaxable} * {@link TaxLine#getRate}).
     *
     * @return tax
     */
    public java.math.BigDecimal getTax() {
        return tax;
    }


    /**
     *  The tax amount, i.e.  the calculated tax amount
     * ({@link TaxLine#getTaxable} * {@link TaxLine#getRate}).
     *
     * @param tax
     */
    public void setTax(java.math.BigDecimal tax) {
        this.tax = tax;
    }

    /**
     * Gets the taxCalculated value for this TaxLine.
     *
     * @return taxCalculated
     */
    public java.math.BigDecimal getTaxCalculated() {
        return taxCalculated;
    }


    /**
     * Sets the taxCalculated value for this TaxLine.
     *
     * @param taxCalculated
     */
    public void setTaxCalculated(java.math.BigDecimal taxCalculated) {
        this.taxCalculated = taxCalculated;
    }


    /**
     *  Tax by jurisdiction.
     *
     * @return taxDetails
     */
    public com.avalara.avatax.services.tax.ArrayOfTaxDetail getTaxDetails() {
        return taxDetails;
    }


    /**
     *  Tax by jurisdiction.
     *
     * @param taxDetails
     */
    public void setTaxDetails(com.avalara.avatax.services.tax.ArrayOfTaxDetail taxDetails) {
        this.taxDetails = taxDetails;
    }



    /**
     * Gets the exemptCertId value for this TaxLine.
     *
     * @return exemptCertId
     */
    public int getExemptCertId() {
        return exemptCertId;
    }


    /**
     * Sets the exemptCertId value for this TaxLine.
     *
     * @param exemptCertId
     */
    public void setExemptCertId(int exemptCertId) {
        this.exemptCertId = exemptCertId;
    }

    /**
     * Gets the taxDate value for this TaxLine.
     *
     * @return taxDate
     */
    public java.util.Date getTaxDate() {
        return taxDate;
    }


    /**
     * Sets the taxDate value for this TaxLine.
     *
     * @param taxDate
     */
    public void setTaxDate(java.util.Date taxDate) {
        this.taxDate = taxDate;
    }


    /**
     * Gets the reportingDate value for this TaxLine.
     *
     * @return reportingDate
     */
    public java.util.Date getReportingDate() {
        return reportingDate;
    }


    /**
     * Sets the reportingDate value for this TaxLine.
     *
     * @param reportingDate
     */
    public void setReportingDate(java.util.Date reportingDate) {
        this.reportingDate = reportingDate;
    }


    /**
     * Gets the accountingMethod value for this TaxLine.
     *
     * @return accountingMethod
     */
    public com.avalara.avatax.services.tax.AccountingMethod getAccountingMethod() {
        return accountingMethod;
    }


    /**
     * Sets the accountingMethod value for this TaxLine.
     *
     * @param accountingMethod
     */
    public void setAccountingMethod(com.avalara.avatax.services.tax.AccountingMethod accountingMethod) {
        this.accountingMethod = accountingMethod;
    }

    /**
     * True if tax is included in the line.
     *
     * @return taxIncluded
     */
    public boolean isTaxIncluded() {
        return taxIncluded;
    }


    /**
     * True if tax is included in the line.
     *
     * @param taxIncluded
     */
    public void setTaxIncluded(boolean taxIncluded) {
        this.taxIncluded = taxIncluded;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TaxLine)) return false;
        TaxLine other = (TaxLine) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.no==null && other.getNo()==null) ||
                        (this.no!=null &&
                                this.no.equals(other.getNo()))) &&
                ((this.taxCode==null && other.getTaxCode()==null) ||
                        (this.taxCode!=null &&
                                this.taxCode.equals(other.getTaxCode()))) &&
                this.taxability == other.isTaxability() &&
                ((this.boundaryLevel==null && other.getBoundaryLevel()==null) ||
                        (this.boundaryLevel!=null &&
                                this.boundaryLevel.equals(other.getBoundaryLevel()))) &&
                ((this.exemption==null && other.getExemption()==null) ||
                        (this.exemption!=null &&
                                this.exemption.equals(other.getExemption()))) &&
                ((this.discount==null && other.getDiscount()==null) ||
                        (this.discount!=null &&
                                this.discount.equals(other.getDiscount()))) &&
                ((this.taxable==null && other.getTaxable()==null) ||
                        (this.taxable!=null &&
                                this.taxable.equals(other.getTaxable()))) &&
                ((this.rate==null && other.getRate()==null) ||
                        (this.rate!=null &&
                                this.rate.equals(other.getRate()))) &&
                ((this.tax==null && other.getTax()==null) ||
                        (this.tax!=null &&
                                this.tax.equals(other.getTax()))) &&
                ((this.taxCalculated==null && other.getTaxCalculated()==null) ||
                        (this.taxCalculated!=null &&
                                this.taxCalculated.equals(other.getTaxCalculated()))) &&
                ((this.taxDetails==null && other.getTaxDetails()==null) ||
                        (this.taxDetails!=null &&
                                this.taxDetails.equals(other.getTaxDetails()))) &&
                this.exemptCertId == other.getExemptCertId() &&
                ((this.taxDate==null && other.getTaxDate()==null) ||
                        (this.taxDate!=null &&
                                this.taxDate.equals(other.getTaxDate()))) &&
                ((this.reportingDate==null && other.getReportingDate()==null) ||
                        (this.reportingDate!=null &&
                                this.reportingDate.equals(other.getReportingDate()))) &&
                ((this.accountingMethod==null && other.getAccountingMethod()==null) ||
                        (this.accountingMethod!=null &&
                                this.accountingMethod.equals(other.getAccountingMethod()))) &&
                this.taxIncluded == other.isTaxIncluded();
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
        if (getNo() != null) {
            _hashCode += getNo().hashCode();
        }
        if (getTaxCode() != null) {
            _hashCode += getTaxCode().hashCode();
        }
        _hashCode += (isTaxability() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getBoundaryLevel() != null) {
            _hashCode += getBoundaryLevel().hashCode();
        }
        if (getExemption() != null) {
            _hashCode += getExemption().hashCode();
        }
        if (getDiscount() != null) {
            _hashCode += getDiscount().hashCode();
        }
        if (getTaxable() != null) {
            _hashCode += getTaxable().hashCode();
        }
        if (getRate() != null) {
            _hashCode += getRate().hashCode();
        }
        if (getTax() != null) {
            _hashCode += getTax().hashCode();
        }
        if (getTaxCalculated() != null) {
            _hashCode += getTaxCalculated().hashCode();
        }
        if (getTaxDetails() != null) {
            _hashCode += getTaxDetails().hashCode();
        }
        _hashCode += getExemptCertId();
        if (getTaxDate() != null) {
            _hashCode += getTaxDate().hashCode();
        }
        if (getReportingDate() != null) {
            _hashCode += getReportingDate().hashCode();
        }
        if (getAccountingMethod() != null) {
            _hashCode += getAccountingMethod().hashCode();
        }
        _hashCode += (isTaxIncluded() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(TaxLine.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxLine"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("no");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "No"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxability");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Taxability"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("boundaryLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "BoundaryLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "BoundaryLevel"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exemption");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Exemption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Discount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Taxable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Rate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Tax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxCalculated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxCalculated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfTaxDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exemptCertId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ExemptCertId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportingDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ReportingDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountingMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AccountingMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AccountingMethod"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxIncluded");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxIncluded"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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

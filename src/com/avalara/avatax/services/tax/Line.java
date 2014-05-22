/**
 * Line.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * A single line within a document containing data used for calculating tax.
 * @see GetTaxRequest
 * @see ArrayOfLine
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class Line  implements java.io.Serializable {

    private java.lang.String no;

    private java.lang.String originCode;

    private java.lang.String destinationCode;

    private java.lang.String itemCode;

    private java.lang.String taxCode;

    private java.math.BigDecimal qty;

    private java.math.BigDecimal amount;

    private boolean discounted;

    private java.lang.String revAcct;

    private java.lang.String ref1;

    private java.lang.String ref2;

    private java.lang.String exemptionNo;
    private java.lang.String customerUsageType;
    private java.lang.String description;
    private com.avalara.avatax.services.tax.TaxOverride taxOverride;
    private boolean taxIncluded;

    /**
     * Creates a new line object.
     *
     * <pre><b>Example:</b>
     * [Java]
     * Line line = new Line();
     * </pre>
     */
    public Line() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * Currently unused
     * @param no
     * @param itemCode
     * @param taxCode
     * @param qty
     * @param amount
     * @param discounted
     * @param revAcct
     * @param ref1
     * @param ref2
     * @param exemptionNo
     */
    private Line(
            java.lang.String no,
            java.lang.String originCode,
            java.lang.String destinationCode,
            java.lang.String itemCode,
            java.lang.String taxCode,
            java.math.BigDecimal qty,
            java.math.BigDecimal amount,
            boolean discounted,
            java.lang.String revAcct,
            java.lang.String ref1,
            java.lang.String ref2,
            java.lang.String exemptionNo,
            java.lang.String customerUsageType,
            java.lang.String description,
           com.avalara.avatax.services.tax.TaxOverride taxOverride) {
        this.no = no;
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        this.itemCode = itemCode;
        this.taxCode = taxCode;
        this.qty = qty;
        this.amount = amount;
        this.discounted = discounted;
        this.revAcct = revAcct;
        this.ref1 = ref1;
        this.ref2 = ref2;
        this.exemptionNo = exemptionNo;
        this.customerUsageType = customerUsageType;
        this.description = description;
           this.taxOverride = taxOverride;
    }


    /**
     * Line Number.
     * <p>
     * <b>Line</b>s are added to a {@link GetTaxRequest} object when preparing a document for tax calculation.
     * The line <b>No</b> unqiuely identifies a particular line item for the client.
     * </p>
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
     * Line Number.
     * <p>
     * <b>Line</b>s are added to a {@link GetTaxRequest} object when preparing a document for tax calculation.
     * The line <b>No</b> unqiuely identifies a particular line item for the client.
     * </p>
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
     * Used internally by the adapter to reference the {@link Line#getOriginAddress}.
     * Same as the <b>OriginAddress</b>'s {@link com.avalara.avatax.services.tax.BaseAddress#getAddressCode}.
     *
     * @return An internally maintained value representing the <b>OriginAddress</b>
     * @see #originCode
     */
    public java.lang.String getOriginCode() {
        return originCode;
    }


    /**
     * Used internally by the adapter to reference the {@link Line#getOriginAddress}.
     * Same as the <b>OriginAddress</b>'s {@link com.avalara.avatax.services.tax.BaseAddress#getAddressCode}.
     * Made private to avoid data corruption.
     *
     * @param originCode
     */
    public void setOriginCode(java.lang.String originCode) {
        this.originCode = originCode;
    }


    /**
     * Used internally by the adapter to reference the {@link Line#getDestinationAddress}.
     * Same as the <b>DestinationAddress</b>'s {@link BaseAddress#getAddressCode}.
     *
     * @return An internally maintained value representing the <b>DestinationAddress</b>

     */
    public java.lang.String getDestinationCode() {
        return destinationCode;
    }


    /**
     * Used internally by the adapter to reference the {@link Line#getDestinationAddress}.
     * Same as the <b>DestinationAddress</b>'s {@link BaseAddress#getAddressCode}.
     * Made private to avoid data corruption.
     *
     * @param destinationCode
     */
    public void setDestinationCode(java.lang.String destinationCode) {
        this.destinationCode = destinationCode;
    }


    /**
     * Item Code (SKU)
     *
     * @return itemCode
     */
    public java.lang.String getItemCode() {
        return itemCode;
    }


    /**
     * Item Code (SKU)
     *
     * @param itemCode
     */
    public void setItemCode(java.lang.String itemCode) {
        this.itemCode = itemCode;
    }


    /**
     * System or Custom Tax Code.
     * <p>
     * This is used only by sellers who are managing their own tax code mapping.
     * </p>
     *
     * @return taxCode
     */
    public java.lang.String getTaxCode() {
        return taxCode;
    }


    /**
     * System or Custom Tax Code.
     * <p>
     * This is used only by sellers who are managing their own tax code mapping.
     * </p>
     *
     * @param taxCode
     */
    public void setTaxCode(java.lang.String taxCode) {
        this.taxCode = taxCode;
    }


    /**
     * The quantity represented by this line.
     * <p>
     * Qty is not used in conjunction with {@link Line#getAmount} when calculating tax. <b>Amount</b> should already
     * be a product of <b>Qty</b> * UnitPrice. For example, if a line represents 2 items, each sold at $10 then,
     * <pre>
     * [Java]
     * BigDecimal qty = line.getQty();
     * BigDecimal amount = line.getAmount();
     * </pre>
     * <p>
     * @see Line#setAmount
     *
     * @return qty
     */
    public java.math.BigDecimal getQty() {
        return qty;
    }


    /**
     * The quantity represented by this line.
     * <p>
     * Qty is not used in conjunction with {@link Line#getAmount} when calculating tax. <b>Amount</b> should already
     * be a product of <b>Qty</b> * UnitPrice. For example, if a line represents 2 items, each sold at $10 then,
     * <pre>
     * [Java]
     * line.setQty(new BigDecimal("2"))
     * line.setAmount(new BigDecimal("20"))
     * </pre>
     * <p>
     * @see Line#getAmount
     *
     * @param qty
     */
    public void setQty(java.math.BigDecimal qty) {
        this.qty = qty;
    }


    /**
     *  The total amount for this line item ({@link Line#getQty} * UnitPrice).
     *
     * @return amount
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     *  The total amount for this line item ({@link Line#getQty} * UnitPrice).
     *
     * @param amount
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * True if the document discount should be applied to this line.
     *
     * @return discounted
     */
    public boolean isDiscounted() {
        return discounted;
    }


    /**
     * True if the document discount should be applied to this line.
     *
     * @param discounted
     */
    public void setDiscounted(boolean discounted) {
        this.discounted = discounted;
    }


    /**
     *  Revenue Account.
     *
     * @return revAcct
     */
    public java.lang.String getRevAcct() {
        return revAcct;
    }


    /**
     *  Revenue Account.
     *
     * @param revAcct
     */
    public void setRevAcct(java.lang.String revAcct) {
        this.revAcct = revAcct;
    }


    /**
     *  Client specific reference field.
     *
     * @return ref1
     */
    public java.lang.String getRef1() {
        return ref1;
    }


    /**
     *  Client specific reference field.
     *
     * @param ref1
     */
    public void setRef1(java.lang.String ref1) {
        this.ref1 = ref1;
    }


    /**
     *  Client specific reference field.
     *
     * @return ref2
     */
    public java.lang.String getRef2() {
        return ref2;
    }


    /**
     *  Client specific reference field.
     *
     * @param ref2
     */
    public void setRef2(java.lang.String ref2) {
        this.ref2 = ref2;
    }


    /**
     * Exemption number for this line.
     *
     * @return exemptionNo
     */
    public java.lang.String getExemptionNo() {
        return exemptionNo;
    }


    /**
     * Exemption number for this line.
     *
     * @param exemptionNo
     */
    public void setExemptionNo(java.lang.String exemptionNo) {
        this.exemptionNo = exemptionNo;
    }


    /**
     * Allows to fetch the customer or usage type at the line level.
     * <p>
     * This overrides CustomerUsageType {@link GetTaxRequest#getCustomerUsageType} at the GetTaxRequest level.
     * </p>
     * @return customerUsageType
     */
    public java.lang.String getCustomerUsageType() {
        return customerUsageType;
    }


    /**
     * Allows specifying the customer or usage type at the line level.
     * <p>
     * This overrides CustomerUsageType {@link GetTaxRequest#getCustomerUsageType} at the GetTaxRequest level.
     * </p>
     * @param customerUsageType
     */
    public void setCustomerUsageType(java.lang.String customerUsageType) {
        this.customerUsageType = customerUsageType;
    }


    /**
     * Gets the description which defines the description for the product or item.
     *
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description which defines the description for the product or item.
     *
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    /**
     * Gets the taxOverride value for this Line.
     * 
     * @return taxOverride
     */
    public com.avalara.avatax.services.tax.TaxOverride getTaxOverride() {
        return taxOverride;
    }


    /**
     * Sets the taxOverride value for this Line.
     * 
     * @param taxOverride
     */
    public void setTaxOverride(com.avalara.avatax.services.tax.TaxOverride taxOverride) {
        this.taxOverride = taxOverride;
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
        if (!(obj instanceof Line)) return false;
        Line other = (Line) obj;
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
                ((this.originCode==null && other.getOriginCode()==null) ||
                        (this.originCode!=null &&
                                this.originCode.equals(other.getOriginCode()))) &&
                ((this.destinationCode==null && other.getDestinationCode()==null) ||
                        (this.destinationCode!=null &&
                                this.destinationCode.equals(other.getDestinationCode()))) &&
                ((this.itemCode==null && other.getItemCode()==null) ||
                        (this.itemCode!=null &&
                                this.itemCode.equals(other.getItemCode()))) &&
                ((this.taxCode==null && other.getTaxCode()==null) ||
                        (this.taxCode!=null &&
                                this.taxCode.equals(other.getTaxCode()))) &&
                ((this.qty==null && other.getQty()==null) ||
                        (this.qty!=null &&
                                this.qty.equals(other.getQty()))) &&
                ((this.amount==null && other.getAmount()==null) ||
                        (this.amount!=null &&
                                this.amount.equals(other.getAmount()))) &&
                this.discounted == other.isDiscounted() &&
                ((this.revAcct==null && other.getRevAcct()==null) ||
                        (this.revAcct!=null &&
                                this.revAcct.equals(other.getRevAcct()))) &&
                ((this.ref1==null && other.getRef1()==null) ||
                        (this.ref1!=null &&
                                this.ref1.equals(other.getRef1()))) &&
                ((this.ref2==null && other.getRef2()==null) ||
                        (this.ref2!=null &&
                                this.ref2.equals(other.getRef2()))) &&
                ((this.exemptionNo==null && other.getExemptionNo()==null) ||
                        (this.exemptionNo!=null &&
                                this.exemptionNo.equals(other.getExemptionNo()))) &&
                ((this.customerUsageType==null && other.getCustomerUsageType()==null) ||
                        (this.customerUsageType!=null &&
                                this.customerUsageType.equals(other.getCustomerUsageType()))) &&
                ((this.description==null && other.getDescription()==null) ||
                        (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.taxOverride==null && other.getTaxOverride()==null) || 
             (this.taxOverride!=null &&
              this.taxOverride.equals(other.getTaxOverride()))) &&
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
        if (getOriginCode() != null) {
            _hashCode += getOriginCode().hashCode();
        }
        if (getDestinationCode() != null) {
            _hashCode += getDestinationCode().hashCode();
        }
        if (getItemCode() != null) {
            _hashCode += getItemCode().hashCode();
        }
        if (getTaxCode() != null) {
            _hashCode += getTaxCode().hashCode();
        }
        if (getQty() != null) {
            _hashCode += getQty().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        _hashCode += (isDiscounted() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getRevAcct() != null) {
            _hashCode += getRevAcct().hashCode();
        }
        if (getRef1() != null) {
            _hashCode += getRef1().hashCode();
        }
        if (getRef2() != null) {
            _hashCode += getRef2().hashCode();
        }
        if (getExemptionNo() != null) {
            _hashCode += getExemptionNo().hashCode();
        }
        if (getCustomerUsageType() != null) {
            _hashCode += getCustomerUsageType().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getTaxOverride() != null) {
            _hashCode += getTaxOverride().hashCode();
        }
        _hashCode += (isTaxIncluded() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(Line.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Line"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("no");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "No"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "OriginCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DestinationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ItemCode"));
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
        elemField.setFieldName("qty");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Qty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discounted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Discounted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("revAcct");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "RevAcct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ref1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Ref1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ref2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Ref2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exemptionNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ExemptionNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerUsageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CustomerUsageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxOverride");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxOverride"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxOverride"));
        elemField.setMinOccurs(0);
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
     * @return TypeDesc
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
     * @return  Serializer
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
     * @return  Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new  com.avalara.avatax.services.base.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

    /**
     * Sets the Address used as the "Ship From" location for a specific line item.
     * Setting the Origin Address on a Line object is not required. If no address
     * exists for either the Origin or Destination, the OriginAddress and/or
     * DestinationAddress will be defaulted from the parent GetTaxRequest object
     * immediately prior to calling the AvaTax web service in the GetTax method.
     * Duplicate addresses on either the GetTaxRequest or the child Line will be
     * consolidated into unique references. Two address objects are considered
     * to be duplicates if they share an object reference or they contain the exact
     * same field values (case-insensitive).


     * Sets the origin address for this Line item and automatically updates the
     * parent GetTaxRequest's address collection.
     * <pre>
     * <b>Example:</b>
     * [Java]
     *  BaseAddress address = new BaseAddress();
     *  address.setLine1("900 Winslow Way");
     *  address.setRegion("WA");
     *  address.setPostalCode("98110");
     *
     *  BaseAddress address2 = new BaseAddress();
     *  address.setLine1("800 Winslow Way");
     *  address.setRegion("WA");
     *  address.setPostalCode("98110");
     *
     *  GetTaxRequest request = new GetTaxRequest();
     *
     *  // numAddresses = 0 for new Request
     *  int numAddresses = (request.getAddresses() == null ||
     *      request.getAddresses().getBaseAddress() == null ? 0 :
     *      request.getAddresses().getBaseAddress().length);
     *
     *  Line line = new Line(request);
     *  line.setOriginAddress(address);
     *
     *  // numAddresses = 1 since we added an address
     *  numAddresses = (request.getAddresses() == null ||
     *      request.getAddresses().getBaseAddress() == null ? 0 :
     *      request.getAddresses().getBaseAddress().length);
     *
     *  request.setOriginAddress(address);
     *
     *  // numAddresses = 1 since we added an address that was a duplicate of
     *  // an address already stored in the request
     *  numAddresses = (request.getAddresses() == null ||
     *      request.getAddresses().getBaseAddress() == null ? 0 :
     *      request.getAddresses().getBaseAddress().length);
     *
     *  line.setOriginAddress(address2);
     *
     *  // numAddresses = 2 since we added an address not previously added to the request
     *  numAddresses = (request.getAddresses() == null ||
     *      request.getAddresses().getBaseAddress() == null ? 0 :
     *      request.getAddresses().getBaseAddress().length);
     *
     * @param address address object containing the information to use as this line item's
     * Origin Address.
     */
    private void setOriginAddress(BaseAddress address)
    {
        //setOriginCode(Integer.toString(address.hashCode()));
        //_request.addAddress(address);
    }

    /**
     * Sets the Address used as the "Ship To" location for a specific line item. If no address
     * exists for either the Origin or Destination, the OriginAddress and/or
     * DestinationAddress will be defaulted from the parent GetTaxRequest object
     * immediately prior to calling the AvaTax web service in the GetTax method.
     * Duplicate addresses on either the GetTaxRequest or the child Line will be
     * consolidated into unique references. Two address objects are considered
     * to be duplicates if they share an object reference or they contain the exact
     * same field values (case-insensitive).
     * Sets the destination address for this Line item and automatically updates the
     * parent GetTaxRequest's address collection.
     * <pre>
     * <b>Example:</b>
     * [Java]
     *  BaseAddress address = new BaseAddress();
     *  address.setLine1("900 Winslow Way");
     *  address.setRegion("WA");
     *  address.setPostalCode("98110");
     *
     *  BaseAddress address2 = new BaseAddress();
     *  address.setLine1("800 Winslow Way");
     *  address.setRegion("WA");
     *  address.setPostalCode("98110");
     *
     *  GetTaxRequest request = new GetTaxRequest();
     *
     *  // numAddresses = 0 for new Request
     *  int numAddresses = (request.getAddresses() == null ||
     *      request.getAddresses().getBaseAddress() == null ? 0 :
     *      request.getAddresses().getBaseAddress().length);
     *
     *  Line line = new Line(request);
     *  line.setDestinationAddress(address);
     *
     *  // numAddresses = 1 since we added an address
     *  numAddresses = (request.getAddresses() == null ||
     *      request.getAddresses().getBaseAddress() == null ? 0 :
     *      request.getAddresses().getBaseAddress().length);
     *
     *  request.setDestinationAddress(address);
     *
     *  // numAddresses = 1 since we added an address that was a duplicate of
     *  // an address already stored in the request
     *  numAddresses = (request.getAddresses() == null ||
     *      request.getAddresses().getBaseAddress() == null ? 0 :
     *      request.getAddresses().getBaseAddress().length);
     *
     *  line.setDestinationAddress(address2);
     *
     *  // numAddresses = 2 since we added an address not previously added to the request
     *  numAddresses = (request.getAddresses() == null ||
     *      request.getAddresses().getBaseAddress() == null ? 0 :
     *      request.getAddresses().getBaseAddress().length);
     *
     *
     * @param address address object containing the information to use as this line item's
     * Origin Address.
     */
    private void setDestinationAddress(BaseAddress address)
    {
        //setDestinationCode(Integer.toString(address.hashCode()));
        //_request.addAddress(address);
    }

    /**
     * Gets the Address used as the "Ship From" location for a specific line item.
     * Retrieves the BaseAddress object corresponding to the Line's Origin
     * Address from the parent GetTaxRequest object.
     * @return  The BaseAddress for the Origin Address
     * if found or null if no matching address is found.
     */
    private BaseAddress getOriginAddress()
    {
        return null; //_request.getAddress(getOriginCode());
    }

    /**
     * Gets the Address used as the "Ship To" location for a specific line item.
     * Retrieves the BaseAddress object corresponding to the Line's Origin
     * Address from the parent GetTaxRequest object.
     * @return  The BaseAddress object for the Destination Address
     * if found or null if no matching address is found.
     */
    private BaseAddress getDestinationAddress()
    {
        return null; //_request.getAddress(getDestinationCode());
    }
}

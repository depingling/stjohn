/**
 * TaxDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Holds calculated tax information by jurisdiction.
 * @see ArrayOfTaxDetail
 * @see TaxLine
 * @see GetTaxResult
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class TaxDetail  implements java.io.Serializable {

    private java.lang.String country;
    private java.lang.String region;
    private com.avalara.avatax.services.tax.JurisdictionType jurisType;

    private java.lang.String jurisCode;

    private com.avalara.avatax.services.tax.TaxType taxType;

    private java.math.BigDecimal base;
    private java.math.BigDecimal taxable;
    private java.math.BigDecimal rate;
    private java.lang.String rateType;
    private java.math.BigDecimal tax;
    private java.math.BigDecimal taxCalculated;
    private java.math.BigDecimal nonTaxable;
    private java.math.BigDecimal exemption;
    private java.lang.String jurisName;
    private java.lang.String taxName;
    private int taxAuthorityType;
    private java.lang.String taxGroup;
    /**
     * Initializes a new instance of the class.
     */
    public TaxDetail() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param jurisType
     * @param jurisCode
     * @param taxType
     * @param base
     * @param rate
     * @param tax
     * @param jurisName
     */
    private TaxDetail(
            com.avalara.avatax.services.tax.JurisdictionType jurisType,
            java.lang.String jurisCode,
            com.avalara.avatax.services.tax.TaxType taxType,
            java.math.BigDecimal base,
            java.math.BigDecimal taxable,
            java.math.BigDecimal rate,
            java.math.BigDecimal tax,
            java.math.BigDecimal nonTaxable,
            java.math.BigDecimal exemption,
            java.lang.String jurisName) {
        this.jurisType = jurisType;
        this.jurisCode = jurisCode;
        this.taxType = taxType;
        this.base = base;
        this.taxable = taxable;
        this.rate = rate;
        this.rateType = "";
        this.tax = tax;
        this.nonTaxable = nonTaxable;
        this.exemption = exemption;
        this.jurisName = jurisName;
        this.taxName = taxName;
        this.taxAuthorityType = taxAuthorityType;
    }


    /**
        * Gets the country value for this TaxDetail.
        *
        * @return country
        */
       public java.lang.String getCountry() {
           return country;
       }


       /**
        * Sets the country value for this TaxDetail.
        *
        * @param country
        */
       public void setCountry(java.lang.String country) {
           this.country = country;
       }


       /**
        * Gets the region value for this TaxDetail.
        *
        * @return region
        */
       public java.lang.String getRegion() {
           return region;
       }


       /**
        * Sets the region value for this TaxDetail.
        *
        * @param region
        */
       public void setRegion(java.lang.String region) {
           this.region = region;
       }



    /**
     * Jurisdiction Type.
     *
     * @return jurisType
     */
    public com.avalara.avatax.services.tax.JurisdictionType getJurisType() {
        return jurisType;
    }


    /**
     * Jurisdiction Type.
     *
     * @param jurisType
     */
    public void setJurisType(com.avalara.avatax.services.tax.JurisdictionType jurisType) {
        this.jurisType = jurisType;
    }


    /**
     * Jurisdiction Code for the taxing jurisdiction.
     *
     * @return jurisCode
     */
    public java.lang.String getJurisCode() {
        return jurisCode;
    }


    /**
     * Jurisdiction Code for the taxing jurisdiction.
     *
     * @param jurisCode
     */
    public void setJurisCode(java.lang.String jurisCode) {
        this.jurisCode = jurisCode;
    }


    /**
     * Tax Type
     * <table>
     * <tr>
     * <th>Type</th><th>Description</th>
     * </tr>
     * <tr>
     * <td>S</td><td>Sales Tax</td>
     * </tr>
     * <tr>
     * <td>U</td><td>Use Tax</td>
     * </tr>
     * <tr>
     * </table>
     *
     * @return taxType
     */
    public com.avalara.avatax.services.tax.TaxType getTaxType() {
        return taxType;
    }


    /**
     * Tax Type
     * <table>
     * <tr>
     * <th>Type</th><th>Description</th>
     * </tr>
     * <tr>
     * <td>S</td><td>Sales Tax</td>
     * </tr>
     * <tr>
     * <td>U</td><td>Use Tax</td>
     * </tr>
     * <tr>
     * </table>
     *
     * @param taxType
     */
    public void setTaxType(com.avalara.avatax.services.tax.TaxType taxType) {
        this.taxType = taxType;
    }


    /**
     *  The tax base, i.e. the taxable amount.
     *
     * @return base
     * @deprecated See {@link TaxDetail#getTaxable}.
     */
    public java.math.BigDecimal getBase() {
        return base;
    }


    /**
     *  The tax base, i.e. the taxable amount.
     *
     * @param base
     * @deprecated See {@link TaxDetail#setTaxable}.
     */
    public void setBase(java.math.BigDecimal base) {
        this.base = base;
    }


    /**
     *  The taxable amount.  Replaces getBase().
     *
     * @return taxable
     */
    public java.math.BigDecimal getTaxable() {
        return taxable;
    }


    /**
     *  The taxable amount.  Replaces setBase().
     *
     * @param taxable
     */
    public void setTaxable(java.math.BigDecimal taxable) {
        this.taxable = taxable;
    }


    /**
     * Gets the rate value for this TaxDetail.
     *
     * @return rate
     */
    public java.math.BigDecimal getRate() {
        return rate;
    }


    /**
     * The tax rate, i.e. the rate of taxation (0.0 - 1.0)
     *
     * @param rate
     */
    public void setRate(java.math.BigDecimal rate) {
        this.rate = rate;
    }


    /**
     * Gets the rate type value for this TaxDetail.
     *
     * @return rateType
     */
    public java.lang.String getRateType() {
        return rateType;
    }


    /**
     * The tax rate type
     *
     * @param rateType
     */
    public void setRateType(java.lang.String rateType) {
        this.rateType = rateType;
    }


    /**
     * The tax amount, i.e. the calculated tax ({@link TaxDetail#getBase} * {@link TaxDetail#getRate})
     *
     * @return tax
     */
    public java.math.BigDecimal getTax() {
        return tax;
    }


    /**
     * The tax amount, i.e. the calculated tax ({@link TaxDetail#getBase} * {@link TaxDetail#getRate})
     *
     * @param tax
     */
    public void setTax(java.math.BigDecimal tax) {
        this.tax = tax;
    }

     /**
     * Gets the taxCalculated value for this TaxDetail.
     *
     * @return taxCalculated
     */
    public java.math.BigDecimal getTaxCalculated() {
        return taxCalculated;
    }


    /**
     * Sets the taxCalculated value for this TaxDetail.
     *
     * @param taxCalculated
     */
    public void setTaxCalculated(java.math.BigDecimal taxCalculated) {
        this.taxCalculated = taxCalculated;
    }


    /**
     * The non-taxable amount.
     *
     * @return nonTaxable
     */
    public java.math.BigDecimal getNonTaxable() {
        return nonTaxable;
    }


    /**
     * The non-taxable amount.
     *
     * @param nonTaxable
     */
    public void setNonTaxable(java.math.BigDecimal nonTaxable) {
        this.nonTaxable = nonTaxable;
    }


    /**
     * The exempt amount.
     *
     * @return exemption
     */
    public java.math.BigDecimal getExemption() {
        return exemption;
    }


    /**
     * The exempt amount for this TaxDetail.
     *
     * @param exemption
     */
    public void setExemption(java.math.BigDecimal exemption) {
        this.exemption = exemption;
    }


    /**
     * Gets the jurisdiction name for this TaxDetail.
     *
     * @return jurisName
     */
    public java.lang.String getJurisName() {
        return jurisName;
    }


    /**
     * Sets the jurisdiction name.
     *
     * @param jurisName
     */
    public void setJurisName(java.lang.String jurisName) {
        this.jurisName = jurisName;
    }


    /**
     * Gets the taxName value.
     * <p>
     * It further defines tax and jurisdiction..
     * </p>
     * @return taxName
     */
    public java.lang.String getTaxName() {
        return taxName;
    }
    /**
     *  Sets the taxName value.
     *  <p>
     *  It further defines tax and jurisdiction.
     *  </p>
     * @param taxName
     */
    public void setTaxName(java.lang.String taxName) {
        this.taxName = taxName;
    }


    /**
     * Gets the taxAuthorityType value for this TaxDetail.
     *
     * @return taxAuthorityType
     */
    public int getTaxAuthorityType() {
        return taxAuthorityType;
    }





    /**
     * Sets the taxAuthorityType value for this TaxDetail.
     *
     * @param taxAuthorityType
     */
    public void setTaxAuthorityType(int taxAuthorityType) {
        this.taxAuthorityType = taxAuthorityType;
    }

     /**
     * Sets the taxGroup value for this TaxDetail.
     *
     * @param taxGroup
     */
    public void setTaxGroup(String taxGroup) {
        this.taxGroup = taxGroup;
    }

    /**
     * Gets the taxGroup value for this TaxDetail.
     *
     * @return taxGroup
     */
    public String getTaxGroup() {
        return taxGroup;
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
        if (!(obj instanceof TaxDetail)) return false;
        TaxDetail other = (TaxDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.country==null && other.getCountry()==null) ||
                        (this.country!=null &&
                                this.country.equals(other.getCountry()))) &&
                ((this.region==null && other.getRegion()==null) ||
                        (this.region!=null &&
                                this.region.equals(other.getRegion()))) &&
                ((this.jurisType==null && other.getJurisType()==null) ||
                        (this.jurisType!=null &&
                                this.jurisType.equals(other.getJurisType()))) &&
                ((this.jurisCode==null && other.getJurisCode()==null) ||
                        (this.jurisCode!=null &&
                                this.jurisCode.equals(other.getJurisCode()))) &&
                ((this.taxType==null && other.getTaxType()==null) ||
                        (this.taxType!=null &&
                                this.taxType.equals(other.getTaxType()))) &&
                ((this.base==null && other.getBase()==null) ||
                        (this.base!=null &&
                                this.base.equals(other.getBase()))) &&
                ((this.taxable==null && other.getTaxable()==null) ||
                        (this.taxable!=null &&
                                this.taxable.equals(other.getTaxable()))) &&
                ((this.rate==null && other.getRate()==null) ||
                        (this.rate!=null &&
                                this.rate.equals(other.getRate()))) &&
                ((this.rateType==null && other.getRateType()==null) ||
                        (this.rateType!=null &&
                                this.rateType.equals(other.getRateType()))) &&
                ((this.tax==null && other.getTax()==null) ||
                        (this.tax!=null &&
                                this.tax.equals(other.getTax()))) &&
                ((this.taxCalculated==null && other.getTaxCalculated()==null) ||
                        (this.taxCalculated!=null &&
                                this.taxCalculated.equals(other.getTaxCalculated()))) &&
                ((this.nonTaxable==null && other.getNonTaxable()==null) ||
                        (this.nonTaxable!=null &&
                                this.nonTaxable.equals(other.getNonTaxable()))) &&
                ((this.exemption==null && other.getExemption()==null) ||
                        (this.exemption!=null &&
                                this.exemption.equals(other.getExemption()))) &&
                ((this.jurisName==null && other.getJurisName()==null) ||
                        (this.jurisName!=null &&
                                this.jurisName.equals(other.getJurisName()))) &&
                ((this.taxName==null && other.getTaxName()==null) ||
                        (this.taxName!=null &&
                                this.taxName.equals(other.getTaxName()))) &&
                ((this.taxGroup==null && other.getTaxGroup()==null) ||
                        (this.taxGroup!=null &&
                                this.taxGroup.equals(other.getTaxGroup()))) &&
                this.taxAuthorityType == other.getTaxAuthorityType();
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
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getRegion() != null) {
            _hashCode += getRegion().hashCode();
        }
        if (getJurisType() != null) {
            _hashCode += getJurisType().hashCode();
        }
        if (getJurisCode() != null) {
            _hashCode += getJurisCode().hashCode();
        }
        if (getTaxType() != null) {
            _hashCode += getTaxType().hashCode();
        }
        if (getBase() != null) {
            _hashCode += getBase().hashCode();
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
        if (getNonTaxable() != null) {
            _hashCode += getNonTaxable().hashCode();
        }
        if (getExemption() != null) {
            _hashCode += getExemption().hashCode();
        }
        if (getJurisName() != null) {
            _hashCode += getJurisName().hashCode();
        }
        if (getTaxName() != null) {
            _hashCode += getTaxName().hashCode();
        }
        if (getTaxGroup() != null) {
            _hashCode += getTaxGroup().hashCode();
        }
        _hashCode += getTaxAuthorityType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(TaxDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("region");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Region"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();        
        elemField.setFieldName("jurisType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "JurisType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "JurisdictionType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jurisCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "JurisCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("base");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Base"));
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
        elemField.setFieldName("rateType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "RateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("nonTaxable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "NonTaxable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exemption");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Exemption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jurisName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "JurisName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxAuthorityType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxAuthorityType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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

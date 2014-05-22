/**
 * ValidAddress.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.address;

/**
 *  A fully validated address based on initial {@link Address}
 * data passed to {@link AddressSvcSoap#validate}.
 * <pre>
 * <b>Example:</b>
 * [Java]
 *  ValidateRequest request = new ValidateRequest();
 *  Address address = new Address();
 *  address.setLine1("900 Winslow Way");
 *  address.setLine2("Suite 130");
 *  address.setCity("Bainbridge Is");
 *  address.setRegion("WA");
 *  address.setPostalCode("98110-2450");
 *  request.setAddress(address);
 *  request.setTextCase(TextCase.Upper);
 *
 *  ValidateResult result;
 *  result = svc.validate(request);
 *
 *  if (SeverityLevel.Success.equals(result.getResultCode()))
 *  {
 *      ArrayOfValidAddress arrValids = result.getValidAddresses();
 *      if (arrValids != null && arrValids.getValidAddress() != null &&
 *              arrValids.getValidAddress().length > 0)
 *      {
 *          ValidAddress validAddress = result.getValidAddresses().getValidAddress(0);
 *      }
 *  }
 * </pre>
 *
 * @see ArrayOfValidAddress
 * @see Address
 *
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class ValidAddress  extends com.avalara.avatax.services.address.Address  implements java.io.Serializable {
    private java.lang.String line4;
    private java.lang.String county;

    private java.lang.String fipsCode;

    private java.lang.String carrierRoute;

    private java.lang.String postNet;

    private java.lang.String addressType;
    private java.lang.String latitude;
    private java.lang.String longitude;

    /**
     * Initializes a new instance of the class.
     */
    public ValidAddress() {
        this.latitude = "";
        this.longitude = "";
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param line4
     * @param county
     * @param fipsCode
     * @param carrierRoute
     * @param postNet
     * @param addressType
     */
    private ValidAddress(
            java.lang.String line4,
            java.lang.String county,
            java.lang.String fipsCode,
            java.lang.String carrierRoute,
            java.lang.String postNet,
            java.lang.String addressType) {
        this.line4 = line4;
        this.county = county;
        this.fipsCode = fipsCode;
        this.carrierRoute = carrierRoute;
        this.postNet = postNet;
        this.addressType = addressType;
    }


    /**
     * Address line 4.
     *
     * @return line4 - Address line 4
     */
    public java.lang.String getLine4() {
        return line4;
    }


    /**
     * Address line 4.
     *
     * @param line4 - Address line 4
     */
    public void setLine4(java.lang.String line4) {
        this.line4 = line4;
    }


    /**
     * County Name.
     *
     * @return county - County Name
     */
    public java.lang.String getCounty() {
        return county;
    }


    /**
     * County Name.
     *
     * @param county - County Name
     */
    public void setCounty(java.lang.String county) {
        this.county = county;
    }


    /**
     * Federal Information Processing Standards Code (USA).
     * <p> This is a unique code representing each geographic combination of state, county, and city.
     * The code is made up of the Federal Information Processing Code (FIPS) that uniquely identifies each state, county, and city in the U.S.
     * See <a href="http://www.census.gov/geo/www/fips/fips.html">Federal Information Processing Standards (FIPS) Codes</a> for more details.
     * <table>
     * <tr>
     *     <th>Digits</th>
     *     <th>Description</th>
     * </tr>
     * <tr>
     *     <td>1-2</td>
     *     <td>State code</td>
     * </tr>
     * <tr>
     *     <td>3-5</td><td>County code</td>
     * </tr>
     * <tr>
     *     <td>6-10</td><td>City code</td>
     * </tr>
     *  </table>
     *
     * @return fipsCode
     */
    public java.lang.String getFipsCode() {
        return fipsCode;
    }


    /**
     * Federal Information Processing Standards Code (USA).
     * <p> This is a unique code representing each geographic combination of state, county, and city.
     * The code is made up of the Federal Information Processing Code (FIPS) that uniquely identifies each state, county, and city in the U.S.
     * See <a href="http://www.census.gov/geo/www/fips/fips.html">Federal Information Processing Standards (FIPS) Codes</a> for more details.
     * <table>
     * <tr>
     *     <th>Digits</th>
     *     <th>Description</th>
     * </tr>
     * <tr>
     *     <td>1-2</td>
     *     <td>State code</td>
     * </tr>
     * <tr>
     *     <td>3-5</td><td>County code</td>
     * </tr>
     * <tr>
     *     <td>6-10</td><td>City code</td>
     * </tr>
     *  </table>
     *
     * @param fipsCode
     */
    public void setFipsCode(java.lang.String fipsCode) {
        this.fipsCode = fipsCode;
    }


    /**
     * The carrier route associated with the input address (USA).
     * <p>The CarrierRoute Property is a 4 character string set
     * after a successful return from the VerifyAddress Method.
     * <p>The first character of this property is always alphabetic,
     * and the last three characters are numeric. For example,
     * "R001" or "C027" would be typical carrier routes. The
     * alphabetic letter indicates the type of delivery associated
     * with this address.
     * <table>
     * <tr>
     * <th>Term</th>
     *
     * <th>Description</th>
     * </tr>
     * <tr>
     *     <td>B</td>
     *     <td>PO Box</td>
     * </tr>
     * <tr>
     *     <td>C</td>
     *     <td>City Delivery</td>
     * </tr>
     * <tr>
     *     <td>G</td>
     *     <td>General Delivery</td>
     * </tr>
     * <tr>
     *     <td>H</td>
     *     <td>Highway Contract</td>
     * </tr>
     * <tr>
     *     <td>R</td>
     *     <td>Rural Route</td>
     * </tr>
     * </table>
     *
     * @return carrierRoute
     */
    public java.lang.String getCarrierRoute() {
        return carrierRoute;
    }


    /**
     * The carrier route associated with the input address (USA).
     * <p>The CarrierRoute Property is a 4 character string set
     * after a successful return from the VerifyAddress Method.
     * <p>The first character of this property is always alphabetic,
     * and the last three characters are numeric. For example,
     * "R001" or "C027" would be typical carrier routes. The
     * alphabetic letter indicates the type of delivery associated
     * with this address.
     * <table>
     * <tr>
     * <th>Term</th>
     *
     * <th>Description</th>
     * </tr>
     * <tr>
     *     <td>B</td>
     *     <td>PO Box</td>
     * </tr>
     * <tr>
     *     <td>C</td>
     *     <td>City Delivery</td>
     * </tr>
     * <tr>
     *     <td>G</td>
     *     <td>General Delivery</td>
     * </tr>
     * <tr>
     *     <td>H</td>
     *     <td>Highway Contract</td>
     * </tr>
     * <tr>
     *     <td>R</td>
     *     <td>Rural Route</td>
     * </tr>
     * </table>
     *
     * @param carrierRoute
     */
    public void setCarrierRoute(java.lang.String carrierRoute) {
        this.carrierRoute = carrierRoute;
    }


    /**
     * A 12-digit POSTNet barcode (USA).
     * <table>
     * <tr>
     *     <th>Digits</th>
     *     <th>Description</th>
     * </tr>
     * <tr>
     *     <td>1-5<td><td>ZIP Code</td>
     * </tr>
     * <tr>
     *     <td>6-9<td><td>Plus4 code</td>
     * </tr>
     * <tr>
     *     <td>10-11<td><td>Delivery point</td>
     * </tr>
     * <tr>
     *     <td>12<td><td>Check digit</td>
     * </tr>
     * </table>
     *
     * @return postNet
     */
    public java.lang.String getPostNet() {
        return postNet;
    }


    /**
     * A 12-digit POSTNet barcode (USA).
     * <table>
     * <tr>
     *     <th>Digits</th>
     *     <th>Description</th>
     * </tr>
     * <tr>
     *     <td>1-5<td><td>ZIP Code</td>
     * </tr>
     * <tr>
     *     <td>6-9<td><td>Plus4 code</td>
     * </tr>
     * <tr>
     *     <td>10-11<td><td>Delivery point</td>
     * </tr>
     * <tr>
     *     <td>12<td><td>Check digit</td>
     * </tr>
     * </table>
     *
     * @param postNet
     */
    public void setPostNet(java.lang.String postNet) {
        this.postNet = postNet;
    }


    /**
     * Address Type - The type of address that was coded
     * (PO Box, Rural Route, and so on), using the input address.
     *
     * <table>
     * <tr>
     *     <th>Code</th>
     *     <th>Type</th>
     * </tr>
     * <tr>
     *     <td>F<td><td>Firm or company address</td>
     * </tr>
     * <tr>
     *     <td>G<td><td>General Delivery address</td>
     * </tr>
     * <tr>
     *     <td>H<td><td>High-rise or business complexs</td>
     * </tr>
     * <tr>
     *     <td>P<td><td>PO Box address</td>
     * </tr>
     * <tr>
     *     <td>R<td><td>Rural route address</td>
     * </tr>
     * <tr>
     *     <td>S<td><td>Street or residential address</td>
     /**
     * Gets the latitude value for this ValidAddress.
     *
     * @return latitude
     */
    public java.lang.String getLatitude() {
        return latitude;
    }


    /**
     * Sets the latitude value for this ValidAddress.
     *
     * @param latitude
     */
    public void setLatitude(java.lang.String latitude) {
        this.latitude = latitude;
    }


    /**
     * Gets the longitude value for this ValidAddress.
     *
     * @return longitude
     */
    public java.lang.String getLongitude() {
        return longitude;
    }


    /**
     * Sets the longitude value for this ValidAddress.
     *
     * @param longitude
     */
    public void setLongitude(java.lang.String longitude) {
        this.longitude = longitude;
    }

    public java.lang.String getAddressType() {
        return addressType;
    }


    /**
     * Address Type - The type of address that was coded
     * (PO Box, Rural Route, and so on), using the input address.
     *
     * <table>
     * <tr>
     *     <th>Code</th>
     *     <th>Type</th>
     * </tr>
     * <tr>
     *     <td>F<td><td>Firm or company address</td>
     * </tr>
     * <tr>
     *     <td>G<td><td>General Delivery address</td>
     * </tr>
     * <tr>
     *     <td>H<td><td>High-rise or business complexs</td>
     * </tr>
     * <tr>
     *     <td>P<td><td>PO Box address</td>
     * </tr>
     * <tr>
     *     <td>R<td><td>Rural route address</td>
     * </tr>
     * <tr>
     *     <td>S<td><td>Street or residential address</td>
     * </tr>
     * </table>
     * <p><b>NOTE:</b> AddressTypeString is no longer available.
     *
     * @param addressType
     */
    public void setAddressType(java.lang.String addressType) {
        this.addressType = addressType;
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
        if (!(obj instanceof ValidAddress)) return false;
        ValidAddress other = (ValidAddress) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) &&
                ((this.line4==null && other.getLine4()==null) ||
                        (this.line4!=null &&
                                this.line4.equals(other.getLine4()))) &&
                ((this.county==null && other.getCounty()==null) ||
                        (this.county!=null &&
                                this.county.equals(other.getCounty()))) &&
                ((this.fipsCode==null && other.getFipsCode()==null) ||
                        (this.fipsCode!=null &&
                                this.fipsCode.equals(other.getFipsCode()))) &&
                ((this.carrierRoute==null && other.getCarrierRoute()==null) ||
                        (this.carrierRoute!=null &&
                                this.carrierRoute.equals(other.getCarrierRoute()))) &&
                ((this.postNet==null && other.getPostNet()==null) ||
                        (this.postNet!=null &&
                                this.postNet.equals(other.getPostNet()))) &&
                ((this.addressType==null && other.getAddressType()==null) ||
                        (this.addressType!=null &&
                                this.addressType.equals(other.getAddressType()))) &&
                ((this.latitude==null && other.getLatitude()==null) ||
                        (this.latitude!=null &&
                                this.latitude.equals(other.getLatitude()))) &&
                ((this.longitude==null && other.getLongitude()==null) ||
                        (this.longitude!=null &&
                                this.longitude.equals(other.getLongitude())));
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
        if (getLine4() != null) {
            _hashCode += getLine4().hashCode();
        }
        if (getCounty() != null) {
            _hashCode += getCounty().hashCode();
        }
        if (getFipsCode() != null) {
            _hashCode += getFipsCode().hashCode();
        }
        if (getCarrierRoute() != null) {
            _hashCode += getCarrierRoute().hashCode();
        }
        if (getPostNet() != null) {
            _hashCode += getPostNet().hashCode();
        }
        if (getAddressType() != null) {
            _hashCode += getAddressType().hashCode();
        }
        if (getLatitude() != null) {
            _hashCode += getLatitude().hashCode();
        }
        if (getLongitude() != null) {
            _hashCode += getLongitude().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ValidAddress.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidAddress"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("line4");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Line4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("county");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "County"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fipsCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "FipsCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carrierRoute");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CarrierRoute"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postNet");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PostNet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AddressType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("latitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Latitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("longitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Longitude"));
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

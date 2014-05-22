/**
 * ArrayOfLine.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

import com.avalara.avatax.services.base.Utility;


/**
 * A wrapper object used by Axis to encapsulate  an array of zero or more {@link Line} objects
 * for SOAP transmission via the Web.
 * Note: The internally kept Line array is not guaranteed to be properly initialized (this is
 * generally true for Axis ArrayOfX wrapper objects); one should always test that it is not null
 * before using.
 *
 * <pre>
 * <b>Example:</b>
 * [Java]
 * ArrayOfLine lines = getTaxRequest.getLines();
 * if (lines != null)
 * {
 *     Line[] lineArr = lines.getLine();
 *     BigDecimal total = new BigDecimal("0.00");
 *     for (int i = 0; i < lineArr.length; i++)
 *     {
 *         Line line = lineArr[i];
 *         total = total.add(line.getAmount());
 *     }
 * }
 * </pre>
 *
 * A collection of zero or more {@link Line} objects.
 * @see GetTaxRequest
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class ArrayOfLine  implements java.io.Serializable {
    private com.avalara.avatax.services.tax.Line[] line;

    /**
     * Initializes a new instance of the class with an empty array of {@link Line} objects.
     */
    public ArrayOfLine() {
        line = new Line[0];
    }

    /**
     * Initializes a new instance of the class and its internal array
     * of {@link Line} objects.
     *
     * @param line
     */
    public ArrayOfLine(
            com.avalara.avatax.services.tax.Line[] line) {
        this.line = line;
    }


    /**
     * Retrieves the raw array of {@link Line} objects encapsulated in
     * this object.
     *
     * @return Line warning this may be null, depending on how the object
     * was initialized.
     */
    public com.avalara.avatax.services.tax.Line[] getLine() {
        return line;
    }


    /**
     * Allows one to programatically set the raw array of {@link Line} objects
     * encapsulated by this object.
     *
     * @param line
     */
    public void setLine(com.avalara.avatax.services.tax.Line[] line) {
        if (line != null)
        {
            this.line = line;
        }
        else
        {
            line = new Line[0];
        }
    }

    /**
     * Retrieves the ith {@link Line} object (counting from 0) from the array
     * of Lines encapsulated in this object. Should only be used if its known
     * that {@link #getLine} returns a non-null value and that i < number of Lines
     * actually in that array.
     *
     * @param i integer from 0 to (number of Lines -1)
     *
     * @return Line
     */
    public com.avalara.avatax.services.tax.Line getLine(int i) {
        return this.line[i];
    }

    /**
     * Allows one to replace the ith {@link Line} object (counting from 0) within the array
     * of Lines encapsulated in this object. Should only be used if its known
     * that {@link #getLine} returns a non-null value and that i < number of Lines
     * actually in that array.
     *
     * @param i integer from 0 to (number of Lines -1)
     * @param _value Line object to place in the indicated position of the
     * Line array
     */
    public void setLine(int i, com.avalara.avatax.services.tax.Line _value) {
        this.line[i] = _value;
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
        if (!(obj instanceof ArrayOfLine)) return false;
        ArrayOfLine other = (ArrayOfLine) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.line==null && other.getLine()==null) ||
                        (this.line!=null &&
                                java.util.Arrays.equals(this.line, other.getLine())));
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
        if (getLine() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLine());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLine(), i);
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
            new org.apache.axis.description.TypeDesc(ArrayOfLine.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfLine"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("line");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Line"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Line"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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

    // BEGIN Extensions

    /**
     * Initializes a new instance of the class and and its internal array size.
     * of {@link Line} objects.
     * @param size
     */
    public ArrayOfLine(int size)
    {
        this.line = new Line[size];
    }

    /**
     * Gets the size of the array.
     * @return size
     */
    public int size()
    {
        if (line == null)
        {
            return 0;
        }
        else
        {
            return line.length;
        }
    }

    /**
     * Adds an item to the array, resizing it as needed.
     * @param address
     * @return Array position of the added item.
     */
    public int add(Line address)
    {
        int pos = 0;
        if (line == null)
        {
            line = new Line[1];
        }
        else
        {
            while (pos < line.length && line[pos] != null)
            {
                ++pos;
            }
            if (pos == line.length)
            {
                line = (Line[])Utility.resizeArray(line, pos+1);
            }
        }
        line[pos] = address;

        return pos;
    }

    /**
     * Retrieves the {@link Line} object identified by "no" from the array
     * of Lines encapsulated in this object.
     *
     * @param no
     *
     * @return Line if found, else null
     */
    public com.avalara.avatax.services.tax.Line getLine(String no)
    {
        Line result = null;
        if (line != null)
        {
            for (int ii = 0; ii < line.length && result == null; ii++)
            {
                if (no.equals(line[ii].getNo()))
                {
                    result = line[ii];
                }
            }
        }

        return result;
    }

    // END Extensions
}

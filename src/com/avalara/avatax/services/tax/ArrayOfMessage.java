/**
 * ArrayOfMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * A wrapper object used by Axis to encapsulate  an array of zero or more {@link Message} objects
 * for SOAP transmission via the Web returned as part of a method call's result object.
 * Note: The internally kept Message array is not guaranteed to be properly initialized (this is
 * generally true for Axis ArrayOfX wrapper objects); one should always test that it is not null
 * before using.
 * <br><b>Example:</b>
 * <pre>
 * [Java]
 * ArrayOfMessage arrMessage = getTaxResult.getMessages();
 * Message[] messages = arrMessage.getMessage();
 * int numMessages = (messages == null ? 0 : messages.length);
 * for (int i = 0; i < numMessages; i++)
 * {
 *     // These are equivalent ways of accessing the messages in the
 *     // ArrayOfMessage object
 * }
 * </pre>
 *
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class ArrayOfMessage  implements java.io.Serializable {
    private com.avalara.avatax.services.tax.Message[] message;

    /**
     * Initializes a new instance of the class with an empty array
     * of {@link Message} objects.
     */
    public ArrayOfMessage() {
        message = new Message[0];
    }

    /**
     * Initializes a new instance of the class and its internal array
     * of {@link Message} objects.
     * @param message
     */
    public ArrayOfMessage(
            com.avalara.avatax.services.tax.Message[] message) {
        this.message = message;
    }


    /**
     * Retrieves the raw array of {@link Message} objects encapsulated in
     * this object.
     *
     * @return message warning this may be null, depending on how the object
     * was initialized.
     */
    public com.avalara.avatax.services.tax.Message[] getMessage() {
        return message;
    }


    /**
     * Allows one to programatically set the raw array of {@link Message} objects
     * encapsulated by this object.
     *
     * @param message
     */
    public void setMessage(com.avalara.avatax.services.tax.Message[] message) {
        if (message != null)
        {
            this.message = message;
        }
        else
        {
            this.message = new Message[0];
        }
    }

    /**
     * Retrieves the ith {@link Message} object (counting from 0) from the array
     * of Messages encapsulated in this object. Should only be used if its known
     * that {@link #getMessage} returns a non-null value and that i < number of messages
     * actually in that array.
     *
     * @param i integer from 0 to (number of messages -1)
     * @return Message
     */
    public com.avalara.avatax.services.tax.Message getMessage(int i) {
        return this.message[i];
    }

    /**
     * Allows one to replace the ith {@link Message} object (counting from 0) within the array
     * of Messages encapsulated in this object. Should only be used if its known
     * that {@link #getMessage} returns a non-null value and that i < number of messages
     * actually in that array.
     *
     * @param i integer from 0 to (number of messages -1)
     * @param _value Message object to place in the indicated position of the
     * Message array
     */
    public void setMessage(int i, com.avalara.avatax.services.tax.Message _value) {
        this.message[i] = _value;
    }

    /**
     * TODO Ammit added this      
     * @return
     */


    /**
     * Function to remove clientmetricsrequest message from the list.
     * Note: This function must be called while dealing with  clientmetricsrequest message else caller code will
     * receive clientmetricsrequest message back from server
     * @return returns True if clientmetricsrequest message is found
     */
    public boolean hasClientMetricMessage()
    {
        boolean result = false;
        if(this.message != null || this.message.length > 0)
        {
            for (int ii = 0; ii < this.message.length; ii++)
            {
                if(this.message[ii].getName().toString().equalsIgnoreCase("clientmetricsrequest"))
                {
                    result = true;
                    break;
                }
            }
            //Remove ClinetMetricRequest message from the list
            if(result)
            {
                this.message = null;
            }
        }
        return result;
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
        if (!(obj instanceof ArrayOfMessage)) return false;
        ArrayOfMessage other = (ArrayOfMessage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.message==null && other.getMessage()==null) ||
                        (this.message!=null &&
                                java.util.Arrays.equals(this.message, other.getMessage())));
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
        if (getMessage() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMessage());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMessage(), i);
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
            new org.apache.axis.description.TypeDesc(ArrayOfMessage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfMessage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Message"));
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
     * Gets the size of the array.
     * @return size
     */
    public int size()
    {
        if (message == null)
        {
            return 0;
        }
        else
        {
            return message.length;
        }
    }

    // END Extensions
}

/**
 * BaseResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.address;

/**
 * The base class for result objects that return a ResultCode and Messages collection -- Not creatable.
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class BaseResult  implements java.io.Serializable {
    private java.lang.String transactionId;
    private com.avalara.avatax.services.address.SeverityLevel resultCode;
    private com.avalara.avatax.services.address.ArrayOfMessage messages;

    /**
     * Initializes a new instance of the class.
     */
    public BaseResult() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param transactionId
     * @param resultCode
     * @param messages
     */
    private BaseResult(
            java.lang.String transactionId,
            com.avalara.avatax.services.address.SeverityLevel resultCode,
            com.avalara.avatax.services.address.ArrayOfMessage messages) {
        this.transactionId = transactionId;
        this.resultCode = resultCode;
        this.messages = messages;
    }


    /**
     * A unique Transaction ID identifying a specific request/response set.
     * This ID is useful when contacting Avalara Support with a question
     * or problem concerning a transaction or group of transactions.
     *
     * @return transactionId
     */
    public java.lang.String getTransactionId() {
        return transactionId;
    }


    /**
     * A unique Transaction ID identifying a specific request/response set.
     * This ID is useful when contacting Avalara Support with a question
     * or problem concerning a transaction or group of transactions.
     *
     * @param transactionId
     */
    public void setTransactionId(java.lang.String transactionId) {
        this.transactionId = transactionId;
    }


    /**
     * Indicates the success (or {@link SeverityLevel}) of the operation.
     * If there are one or more {@link Message} objects in the result,
     * the ResultCode is set to the most severe of the messages' result codes.
     * <pre>
     * <b>Example:</b>
     * [Java]
     * if (result.getResultCode().equals(SeverityLevel.Success))
     * {
     *     Message[] messages = getTaxResult.getMessages().getMessage();
     *     int numMessages = (messages == null ? 0 : messages.length);
     *     for (int i = 0; i < numMessages; i++)
     *     {
     *         Message message = messages[i];
     *     }
     * }
     *</pre>
     *
     * @return resultCode
     */
    public com.avalara.avatax.services.address.SeverityLevel getResultCode() {
        return resultCode;
    }


    /**
     * Indicates the success (or {@link SeverityLevel}) of the operation.
     * If there are one or more {@link Message} objects in the result,
     * the ResultCode is set to the most severe of the messages' result codes.
     * <pre>
     * <b>Example:</b>
     * [Java]
     * if (result.getResultCode().equals(SeverityLevel.Success))
     * {
     *     Message[] messages = getTaxResult.getMessages().getMessage();
     *     int numMessages = (messages == null ? 0 : messages.length);
     *     for (int i = 0; i < numMessages; i++)
     *     {
     *         Message message = messages[i];
     *     }
     * }
     *</pre>
     *
     * @param resultCode
     */
    public void setResultCode(com.avalara.avatax.services.address.SeverityLevel resultCode) {
        this.resultCode = resultCode;
    }


    /**
     * An array of {@link Message} objects returned in an
     * {@link ArrayOfMessage} wrapper object.
     * <pre>
     * <b>Example:</b>
     * [Java]
     * if (result.getResultCode().equals(SeverityLevel.Success))
     * {
     *     Message[] messages = getTaxResult.getMessages().getMessage();
     *     int numMessages = (messages == null ? 0 : messages.length);
     *     for (int i = 0; i < numMessages; i++)
     *     {
     *         Message message = messages[i];
     *     }
     * }
     *</pre>
     *
     * @see BaseResult#getResultCode
     *
     * @return messages
     */
    public com.avalara.avatax.services.address.ArrayOfMessage getMessages() {
        return messages;
    }


    /**
     * An array of {@link Message} objects returned in an
     * {@link ArrayOfMessage} wrapper object.
     * <pre>
     * <b>Example:</b>
     * [Java]
     * if (result.getResultCode().equals(SeverityLevel.Success))
     * {
     *     Message[] messages = getTaxResult.getMessages().getMessage();
     *     int numMessages = (messages == null ? 0 : messages.length);
     *     for (int i = 0; i < numMessages; i++)
     *     {
     *         Message message = messages[i];
     *     }
     * }
     *</pre>
     *
     * @see BaseResult#getResultCode
     *
     *
     * @param messages
     */
    public void setMessages(com.avalara.avatax.services.address.ArrayOfMessage messages) {
        this.messages = messages;
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
        if (!(obj instanceof BaseResult)) return false;
        BaseResult other = (BaseResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.transactionId==null && other.getTransactionId()==null) ||
                        (this.transactionId!=null &&
                                this.transactionId.equals(other.getTransactionId()))) &&
                ((this.resultCode==null && other.getResultCode()==null) ||
                        (this.resultCode!=null &&
                                this.resultCode.equals(other.getResultCode()))) &&
                ((this.messages==null && other.getMessages()==null) ||
                        (this.messages!=null &&
                                this.messages.equals(other.getMessages())));
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
        if (getTransactionId() != null) {
            _hashCode += getTransactionId().hashCode();
        }
        if (getResultCode() != null) {
            _hashCode += getResultCode().hashCode();
        }
        if (getMessages() != null) {
            _hashCode += getMessages().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(BaseResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "BaseResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TransactionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ResultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "SeverityLevel"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Messages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfMessage"));
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

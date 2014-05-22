/**
 * SurveyAnswers.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.Reports;

public class SurveyAnswers  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private int numberOfAnswers;

    private java.lang.String[] answerID;

    private org.apache.axis.message.MessageElement [] _any;

    public SurveyAnswers() {
    }

    public SurveyAnswers(
           int numberOfAnswers,
           java.lang.String[] answerID,
           org.apache.axis.message.MessageElement [] _any) {
           this.numberOfAnswers = numberOfAnswers;
           this.answerID = answerID;
           this._any = _any;
    }


    /**
     * Gets the numberOfAnswers value for this SurveyAnswers.
     * 
     * @return numberOfAnswers
     */
    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }


    /**
     * Sets the numberOfAnswers value for this SurveyAnswers.
     * 
     * @param numberOfAnswers
     */
    public void setNumberOfAnswers(int numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }


    /**
     * Gets the answerID value for this SurveyAnswers.
     * 
     * @return answerID
     */
    public java.lang.String[] getAnswerID() {
        return answerID;
    }


    /**
     * Sets the answerID value for this SurveyAnswers.
     * 
     * @param answerID
     */
    public void setAnswerID(java.lang.String[] answerID) {
        this.answerID = answerID;
    }

    public java.lang.String getAnswerID(int i) {
        return this.answerID[i];
    }

    public void setAnswerID(int i, java.lang.String _value) {
        this.answerID[i] = _value;
    }


    /**
     * Gets the _any value for this SurveyAnswers.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this SurveyAnswers.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SurveyAnswers)) return false;
        SurveyAnswers other = (SurveyAnswers) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.numberOfAnswers == other.getNumberOfAnswers() &&
            ((this.answerID==null && other.getAnswerID()==null) || 
             (this.answerID!=null &&
              java.util.Arrays.equals(this.answerID, other.getAnswerID()))) &&
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
        _hashCode += getNumberOfAnswers();
        if (getAnswerID() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAnswerID());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAnswerID(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
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
        new org.apache.axis.description.TypeDesc(SurveyAnswers.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "SurveyAnswers"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfAnswers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "NumberOfAnswers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("answerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "AnswerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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

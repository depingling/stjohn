/**
 * SurveyQuestions.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.Reports;

public class SurveyQuestions  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private int numberOfQuestions;

    private java.lang.String[] questionID;

    private org.apache.axis.message.MessageElement [] _any;

    public SurveyQuestions() {
    }

    public SurveyQuestions(
           int numberOfQuestions,
           java.lang.String[] questionID,
           org.apache.axis.message.MessageElement [] _any) {
           this.numberOfQuestions = numberOfQuestions;
           this.questionID = questionID;
           this._any = _any;
    }


    /**
     * Gets the numberOfQuestions value for this SurveyQuestions.
     * 
     * @return numberOfQuestions
     */
    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }


    /**
     * Sets the numberOfQuestions value for this SurveyQuestions.
     * 
     * @param numberOfQuestions
     */
    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }


    /**
     * Gets the questionID value for this SurveyQuestions.
     * 
     * @return questionID
     */
    public java.lang.String[] getQuestionID() {
        return questionID;
    }


    /**
     * Sets the questionID value for this SurveyQuestions.
     * 
     * @param questionID
     */
    public void setQuestionID(java.lang.String[] questionID) {
        this.questionID = questionID;
    }

    public java.lang.String getQuestionID(int i) {
        return this.questionID[i];
    }

    public void setQuestionID(int i, java.lang.String _value) {
        this.questionID[i] = _value;
    }


    /**
     * Gets the _any value for this SurveyQuestions.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this SurveyQuestions.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SurveyQuestions)) return false;
        SurveyQuestions other = (SurveyQuestions) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.numberOfQuestions == other.getNumberOfQuestions() &&
            ((this.questionID==null && other.getQuestionID()==null) || 
             (this.questionID!=null &&
              java.util.Arrays.equals(this.questionID, other.getQuestionID()))) &&
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
        _hashCode += getNumberOfQuestions();
        if (getQuestionID() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getQuestionID());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getQuestionID(), i);
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
        new org.apache.axis.description.TypeDesc(SurveyQuestions.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.protus.com/Reports", "SurveyQuestions"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfQuestions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "NumberOfQuestions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("questionID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.protus.com/Reports", "QuestionID"));
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

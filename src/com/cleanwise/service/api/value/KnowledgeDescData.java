package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  <code>KnowledgeDescData</code> is a ValueObject 
 *  describbing a knowledge .
 *
 *@author     liang
 *@created    Jan 03, 2002
 */
public class KnowledgeDescData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -3183570276284816050L;

    private KnowledgeData    mKnowledgeDetail;
    private String      mProductName = new String("");
    private String      mSkuNum    = new String("");
    private String      mDescription    = new String("");
    
    /**
     *  Constructor.
     */
    public KnowledgeDescData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter
     *@param  parm3  Description of Parameter
     */
    public KnowledgeDescData(KnowledgeData parm1, String parm2, String parm3, String parm4) {
        mKnowledgeDetail = parm1;
        mProductName = parm2;
        mSkuNum = parm3;
        mDescription = parm4;
    }


    /**
     *  Set the mKnowledgeDetail field.
     *
     *@param  v   The new Knowledge value
     */
    public void setKnowledgeDetail(KnowledgeData v) {
        mKnowledgeDetail = v;
    }



    /**
     *  Get the mKnowledgeDetail field.
     *
     *@return    KnowledgeData
     */
    public KnowledgeData getKnowledgeDetail() {
        return mKnowledgeDetail;
    }


    /**
     *  Set the mProductName field.
     *
     *@param  v   The new String value
     */
    public void setProductName(String v) {
        mProductName = v;
    }

    /**
     *  Get the mProductName field.
     *
     *@return    String
     */
    public String getProductName() {
        return mProductName;
    }


    /**
     *  Set the mSkuNum field.
     *
     *@param  v   The new String value
     */
    public void setSkuNum(String v) {
        mSkuNum = v;
    }

    /**
     *  Get the mSkuNum field.
     *
     *@return    String
     */
    public String getSkuNum() {
        return mSkuNum;
    }


    /**
     *  Set the mDescription field.
     *
     *@param  v   The new String value
     */
    public void setDescription(String v) {
        mDescription = v;
    }

    /**
     *  Get the mDescription field.
     *
     *@return    String
     */
    public String getDescription() {
        return mDescription;
    }


    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this KnowledgeDescData object
     */
    public String toString() {
        
        return "[" +  "KnowledgeDetail=" +
                mKnowledgeDetail.toString() + ", ProductName=" + mProductName + ", SkuNum=" + mSkuNum
                + ", Description=" + mDescription
                + "]";
    }


    /**
     *  Creates a new KnowledgeDescData
     *
     *@return    Newly initialized KnowledgeDescData object.
     */
    public static KnowledgeDescData createValue() {
        KnowledgeDescData valueData = new KnowledgeDescData();
        return valueData;
    }

}


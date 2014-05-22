
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProductViewDefData
 * Description:  This is a ValueObject class wrapping the database table CLW_PRODUCT_VIEW_DEF.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ProductViewDefDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ProductViewDefData</code> is a ValueObject class wrapping of the database table CLW_PRODUCT_VIEW_DEF.
 */
public class ProductViewDefData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mProductViewDefId;// SQL type:NUMBER, not null
    private String mStatusCd;// SQL type:VARCHAR2
    private int mAccountId;// SQL type:NUMBER
    private String mAttributename;// SQL type:VARCHAR2
    private int mSortOrder;// SQL type:NUMBER
    private int mWidth;// SQL type:NUMBER
    private String mStyleClass;// SQL type:VARCHAR2
    private String mProductViewCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ProductViewDefData ()
    {
        mStatusCd = "";
        mAttributename = "";
        mStyleClass = "";
        mProductViewCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ProductViewDefData(int parm1, String parm2, int parm3, String parm4, int parm5, int parm6, String parm7, String parm8, Date parm9, String parm10, Date parm11, String parm12)
    {
        mProductViewDefId = parm1;
        mStatusCd = parm2;
        mAccountId = parm3;
        mAttributename = parm4;
        mSortOrder = parm5;
        mWidth = parm6;
        mStyleClass = parm7;
        mProductViewCd = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        
    }

    /**
     * Creates a new ProductViewDefData
     *
     * @return
     *  Newly initialized ProductViewDefData object.
     */
    public static ProductViewDefData createValue ()
    {
        ProductViewDefData valueData = new ProductViewDefData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProductViewDefData object
     */
    public String toString()
    {
        return "[" + "ProductViewDefId=" + mProductViewDefId + ", StatusCd=" + mStatusCd + ", AccountId=" + mAccountId + ", Attributename=" + mAttributename + ", SortOrder=" + mSortOrder + ", Width=" + mWidth + ", StyleClass=" + mStyleClass + ", ProductViewCd=" + mProductViewCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ProductViewDef");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mProductViewDefId));

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node =  doc.createElement("Attributename");
        node.appendChild(doc.createTextNode(String.valueOf(mAttributename)));
        root.appendChild(node);

        node =  doc.createElement("SortOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mSortOrder)));
        root.appendChild(node);

        node =  doc.createElement("Width");
        node.appendChild(doc.createTextNode(String.valueOf(mWidth)));
        root.appendChild(node);

        node =  doc.createElement("StyleClass");
        node.appendChild(doc.createTextNode(String.valueOf(mStyleClass)));
        root.appendChild(node);

        node =  doc.createElement("ProductViewCd");
        node.appendChild(doc.createTextNode(String.valueOf(mProductViewCd)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ProductViewDefId field is not cloned.
    *
    * @return ProductViewDefData object
    */
    public Object clone(){
        ProductViewDefData myClone = new ProductViewDefData();
        
        myClone.mStatusCd = mStatusCd;
        
        myClone.mAccountId = mAccountId;
        
        myClone.mAttributename = mAttributename;
        
        myClone.mSortOrder = mSortOrder;
        
        myClone.mWidth = mWidth;
        
        myClone.mStyleClass = mStyleClass;
        
        myClone.mProductViewCd = mProductViewCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ProductViewDefDataAccess.PRODUCT_VIEW_DEF_ID.equals(pFieldName)) {
            return getProductViewDefId();
        } else if (ProductViewDefDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (ProductViewDefDataAccess.ACCOUNT_ID.equals(pFieldName)) {
            return getAccountId();
        } else if (ProductViewDefDataAccess.ATTRIBUTENAME.equals(pFieldName)) {
            return getAttributename();
        } else if (ProductViewDefDataAccess.SORT_ORDER.equals(pFieldName)) {
            return getSortOrder();
        } else if (ProductViewDefDataAccess.WIDTH.equals(pFieldName)) {
            return getWidth();
        } else if (ProductViewDefDataAccess.STYLE_CLASS.equals(pFieldName)) {
            return getStyleClass();
        } else if (ProductViewDefDataAccess.PRODUCT_VIEW_CD.equals(pFieldName)) {
            return getProductViewCd();
        } else if (ProductViewDefDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ProductViewDefDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ProductViewDefDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ProductViewDefDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else {
            return null;
        }

    }
    /**
    * Gets table name
    *
    * @return Table name
    */
    public String getTable() {
        return ProductViewDefDataAccess.CLW_PRODUCT_VIEW_DEF;
    }

    
    /**
     * Sets the ProductViewDefId field. This field is required to be set in the database.
     *
     * @param pProductViewDefId
     *  int to use to update the field.
     */
    public void setProductViewDefId(int pProductViewDefId){
        this.mProductViewDefId = pProductViewDefId;
        setDirty(true);
    }
    /**
     * Retrieves the ProductViewDefId field.
     *
     * @return
     *  int containing the ProductViewDefId field.
     */
    public int getProductViewDefId(){
        return mProductViewDefId;
    }

    /**
     * Sets the StatusCd field.
     *
     * @param pStatusCd
     *  String to use to update the field.
     */
    public void setStatusCd(String pStatusCd){
        this.mStatusCd = pStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the StatusCd field.
     *
     * @return
     *  String containing the StatusCd field.
     */
    public String getStatusCd(){
        return mStatusCd;
    }

    /**
     * Sets the AccountId field.
     *
     * @param pAccountId
     *  int to use to update the field.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
        setDirty(true);
    }
    /**
     * Retrieves the AccountId field.
     *
     * @return
     *  int containing the AccountId field.
     */
    public int getAccountId(){
        return mAccountId;
    }

    /**
     * Sets the Attributename field.
     *
     * @param pAttributename
     *  String to use to update the field.
     */
    public void setAttributename(String pAttributename){
        this.mAttributename = pAttributename;
        setDirty(true);
    }
    /**
     * Retrieves the Attributename field.
     *
     * @return
     *  String containing the Attributename field.
     */
    public String getAttributename(){
        return mAttributename;
    }

    /**
     * Sets the SortOrder field.
     *
     * @param pSortOrder
     *  int to use to update the field.
     */
    public void setSortOrder(int pSortOrder){
        this.mSortOrder = pSortOrder;
        setDirty(true);
    }
    /**
     * Retrieves the SortOrder field.
     *
     * @return
     *  int containing the SortOrder field.
     */
    public int getSortOrder(){
        return mSortOrder;
    }

    /**
     * Sets the Width field.
     *
     * @param pWidth
     *  int to use to update the field.
     */
    public void setWidth(int pWidth){
        this.mWidth = pWidth;
        setDirty(true);
    }
    /**
     * Retrieves the Width field.
     *
     * @return
     *  int containing the Width field.
     */
    public int getWidth(){
        return mWidth;
    }

    /**
     * Sets the StyleClass field.
     *
     * @param pStyleClass
     *  String to use to update the field.
     */
    public void setStyleClass(String pStyleClass){
        this.mStyleClass = pStyleClass;
        setDirty(true);
    }
    /**
     * Retrieves the StyleClass field.
     *
     * @return
     *  String containing the StyleClass field.
     */
    public String getStyleClass(){
        return mStyleClass;
    }

    /**
     * Sets the ProductViewCd field.
     *
     * @param pProductViewCd
     *  String to use to update the field.
     */
    public void setProductViewCd(String pProductViewCd){
        this.mProductViewCd = pProductViewCd;
        setDirty(true);
    }
    /**
     * Retrieves the ProductViewCd field.
     *
     * @return
     *  String containing the ProductViewCd field.
     */
    public String getProductViewCd(){
        return mProductViewCd;
    }

    /**
     * Sets the AddDate field.
     *
     * @param pAddDate
     *  Date to use to update the field.
     */
    public void setAddDate(Date pAddDate){
        this.mAddDate = pAddDate;
        setDirty(true);
    }
    /**
     * Retrieves the AddDate field.
     *
     * @return
     *  Date containing the AddDate field.
     */
    public Date getAddDate(){
        return mAddDate;
    }

    /**
     * Sets the AddBy field.
     *
     * @param pAddBy
     *  String to use to update the field.
     */
    public void setAddBy(String pAddBy){
        this.mAddBy = pAddBy;
        setDirty(true);
    }
    /**
     * Retrieves the AddBy field.
     *
     * @return
     *  String containing the AddBy field.
     */
    public String getAddBy(){
        return mAddBy;
    }

    /**
     * Sets the ModDate field.
     *
     * @param pModDate
     *  Date to use to update the field.
     */
    public void setModDate(Date pModDate){
        this.mModDate = pModDate;
        setDirty(true);
    }
    /**
     * Retrieves the ModDate field.
     *
     * @return
     *  Date containing the ModDate field.
     */
    public Date getModDate(){
        return mModDate;
    }

    /**
     * Sets the ModBy field.
     *
     * @param pModBy
     *  String to use to update the field.
     */
    public void setModBy(String pModBy){
        this.mModBy = pModBy;
        setDirty(true);
    }
    /**
     * Retrieves the ModBy field.
     *
     * @return
     *  String containing the ModBy field.
     */
    public String getModBy(){
        return mModBy;
    }


}

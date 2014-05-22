
package com.cleanwise.service.api.value;

/**
 * Title:        DisTributorItemDescData
 * Description:  This is a ValueObject class to descript the distributor Item.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

public class DistributorItemDescData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 2007300291523411303L;
    
    private int mDistributorId;
    private String mItemNum;
    private String mItemUom;
    private String mItemPack;

    /**
     * Constructor.
     */
    private DistributorItemDescData ()
    {
        mItemNum = "";
        mItemUom = "";
        mItemPack = "";
    }

    /**
     * Constructor. 
     */
    public DistributorItemDescData(int parm1, String parm2, String parm3, String parm4)
    {
        mDistributorId = parm1;
        mItemNum = parm2;
        mItemUom = parm3;
        mItemPack = parm4;
    }

    /**
     * Creates a new DistributorItemDescData
     *
     * @return
     *  Newly initialized DistributorItemDescData object.
     */
    public static DistributorItemDescData createValue () 
    {
        DistributorItemDescData valueData = new DistributorItemDescData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DistributorItemDescData object
     */
    public String toString()
    {
        return "[" + "DistributorId=" + mDistributorId + ", ItemNum=" + mItemNum + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack  + "]";
    }

    /**
     * Sets the DistributorId field.
     *
     * @param pDistributorId
     *  int to use to update the field.
     */
    public void setDistributorId(int pDistributorId){
        this.mDistributorId = pDistributorId;
        setDirty(true);
    }
    /**
     * Retrieves the DistributorId field.
     *
     * @return
     *  int containing the DistributorId field.
     */
    public int getDistributorId(){
        return mDistributorId;
    }

    /**
     * Sets the ItemNum field.
     *
     * @param pItemNum
     *  String to use to update the field.
     */
    public void setItemNum(String pItemNum){
        this.mItemNum = pItemNum;
        setDirty(true);
    }
    /**
     * Retrieves the ItemNum field.
     *
     * @return
     *  String containing the ItemNum field.
     */
    public String getItemNum(){
        return mItemNum;
    }

    /**
     * Sets the ItemUom field.
     *
     * @param pItemUom
     *  String to use to update the field.
     */
    public void setItemUom(String pItemUom){
        this.mItemUom = pItemUom;
        setDirty(true);
    }
    /**
     * Retrieves the ItemUom field.
     *
     * @return
     *  String containing the ItemUom field.
     */
    public String getItemUom(){
        return mItemUom;
    }

    /**
     * Sets the ItemPack field.
     *
     * @param pItemPack
     *  String to use to update the field.
     */
    public void setItemPack(String pItemPack){
        this.mItemPack = pItemPack;
        setDirty(true);
    }
    /**
     * Retrieves the ItemPack field.
     *
     * @return
     *  String containing the ItemPack field.
     */
    public String getItemPack(){
        return mItemPack;
    }


    
}

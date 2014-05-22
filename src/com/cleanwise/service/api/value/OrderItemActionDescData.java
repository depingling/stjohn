package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  <code>OrderItemActionDescData</code> is a ValueObject 
 *  describbing an item tied to a contract.
 *
 *@author     liang
 *@created    December 05, 2001
 */
public class OrderItemActionDescData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -1307261956111294153L;

    private OrderItemActionData mOrderItemAction;

    private int mItemSkuNum;
    private String mItemPack = new String("");
    private String mDistItemSkuNum = new String("");
    private String mItemShortDesc = new String("");
    private String mItemUom = new String("");
    private BigDecimal mItemDistCost = null;
    
    /**
     *  Constructor.
     */
    public OrderItemActionDescData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter
     *@param  parm3  Description of Parameter
     */
    public OrderItemActionDescData(OrderItemActionData parm1) {
        mOrderItemAction = parm1;
    }


    /**
     *  Set the mOrderItemAction field.
     *
     *@param  v   The new OrderItemAction value
     */
    public void setOrderItemAction(OrderItemActionData v) {
        mOrderItemAction = v;
    }

    
    /**
     *  Get the mOrderItemAction field.
     *
     *@return    OrderItemActionData
     */
    public OrderItemActionData getOrderItemAction() {
        return mOrderItemAction;
    }


    /**
     *  Set the ItemSkuNUm field.
     *
     *@param  v   The new ItemSkuNum value
     */
    public void setItemSkuNum(int v) {
        mItemSkuNum = v;
    }
    
    /**
     *  Get the ItemSkuNum field.
     *
     *@return    int
     */
    public int getItemSkuNum() {
        return mItemSkuNum;
    }


    /**
     *  Set the ItemPack field.
     *
     *@param  v   The new ItemPack value
     */
    public void setItemPack(String v) {
        mItemPack = v;
    }
    
    /**
     *  Get the ItemPack field.
     *
     *@return    String
     */
    public String getItemPack() {
        return mItemPack;
    }


    /**
     *  Set the DistItemSkuNum field.
     *
     *@param  v   The new DistItemSkuNum value
     */
    public void setDistItemSkuNum(String v) {
        mDistItemSkuNum = v;
    }
    
    /**
     *  Get the DistItemSkuNum field.
     *
     *@return    String
     */
    public String getDistItemSkuNum() {
        return mDistItemSkuNum;
    }

    /**
     *  Set the ItemShortDesc field.
     *
     *@param  v   The new ItemShortDesc value
     */
    public void setItemShortDesc(String v) {
        mItemShortDesc = v;
    }
    
    /**
     *  Get the ItemShortDesc field.
     *
     *@return    String
     */
    public String getItemShortDesc() {
        return mItemShortDesc;
    }

    
    /**
     *  Set the ItemUom field.
     *
     *@param  v   The new ItemUom value
     */
    public void setItemUom(String v) {
        mItemUom = v;
    }
    
    /**
     *  Get the ItemUom field.
     *
     *@return    String
     */
    public String getItemUom() {
        return mItemUom;
    }

    
    /**
     *  Set the ItemDistCost field.
     *
     *@param  v   The new ItemDistCost value
     */
    public void setItemDistCost(BigDecimal v) {
        mItemDistCost = v;
    }
    
    /**
     *  Get the ItemDistCost field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getItemDistCost() {
        return mItemDistCost;
    }

    
    
    /**
     *  Returns a String representation of the value object
     *
     *@return The String representation of this
     * OrderItemStatusDescData object
     */
    public String toString() {
        return "[" + "OrderItemAction=" + mOrderItemAction.toString() + "]";
    }


    /**
     *  Creates a new OrderItemActionDescData
     *
     *@return    Newly initialized OrderItemActionDescData object.
     */
    public static OrderItemActionDescData createValue() {
        OrderItemActionDescData valueData = new OrderItemActionDescData();
        return valueData;
    }

}


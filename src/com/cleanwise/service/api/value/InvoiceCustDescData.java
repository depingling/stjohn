package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  <code>InvoiceCustDescData</code> is a ValueObject 
 *  describbing an order status.
 *
 *@author     liang
 *@created    Feb 25, 2002
 */
public class InvoiceCustDescData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 9000014596811666491L;

    private InvoiceCustData mInvoiceCust;
    private InvoiceCustDetailDataVector mInvoiceCustDetailList;
    
    /**
     *  Constructor.
     */
    public InvoiceCustDescData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter
     *@param  parm3  Description of Parameter
     */
    public InvoiceCustDescData(InvoiceCustData parm1, InvoiceCustDetailDataVector parm2) {
        mInvoiceCust = parm1;
        mInvoiceCustDetailList = parm2;
    }


    /**
     *  Set the mInvoiceCust field.
     *
     *@param  v   The new InvoiceCust value
     */
    public void setInvoiceCust(InvoiceCustData v) {
        mInvoiceCust = v;
    }


    /**
     *  Set the mInvoiceCustDetailList field.
     *
     *@param  v   The new InvoiceCustDetailList value
     */
    public void setInvoiceCustDetailList(InvoiceCustDetailDataVector v) {
        mInvoiceCustDetailList = v;
    }



    /**
     *  Get the mInvoiceCust field.
     *
     *@return    InvoiceCustData
     */
    public InvoiceCustData getInvoiceCust() {
        return mInvoiceCust;
    }


    /**
     *  Get the mInvoiceCustDetailList field.
     *
     *@return    InvoiceCustDetailDataVector
     */
    public InvoiceCustDetailDataVector getInvoiceCustDetailList() {
        return mInvoiceCustDetailList;
    }


    
    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this OrderItemStatusDescData object
     */
    public String toString() {
        
        InvoiceCustDetailData InvoiceCustDetail = null;
        if(null != mInvoiceCustDetailList && mInvoiceCustDetailList.size() > 0) {
            InvoiceCustDetail = (InvoiceCustDetailData) mInvoiceCustDetailList.get(0);
        }
        
        return "[" +  "InvoiceCust=" +
                mInvoiceCust.toString() + ", InvoiceCustDetail=" + InvoiceCustDetail.toString() 
                 + "]";
    }


    /**
     *  Creates a new OrderItemStatusDescData
     *
     *@return    Newly initialized OrderItemStatusDescData object.
     */
    public static InvoiceCustDescData createValue() {
        InvoiceCustDescData valueData = new InvoiceCustDescData();
        return valueData;
    }

}


package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  <code>InvoiceDistDescData</code> is a ValueObject 
 *  describbing an order status.
 *
 *@author     liang
 *@created    Feb 25, 2002
 */
public class InvoiceDistDescData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -2300676079732075180L;

    private InvoiceDistData mInvoiceDist;
    private InvoiceDistDetailDataVector mInvoiceDistDetailList;
    
    /**
     *  Constructor.
     */
    public InvoiceDistDescData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter
     *@param  parm3  Description of Parameter
     */
    public InvoiceDistDescData(InvoiceDistData parm1, InvoiceDistDetailDataVector parm2) {
        mInvoiceDist = parm1;
        mInvoiceDistDetailList = parm2;
    }


    /**
     *  Set the mInvoiceDist field.
     *
     *@param  v   The new InvoiceDist value
     */
    public void setInvoiceDist(InvoiceDistData v) {
        mInvoiceDist = v;
    }


    /**
     *  Set the mInvoiceDistDetailList field.
     *
     *@param  v   The new InvoiceDistDetailList value
     */
    public void setInvoiceDistDetailList(InvoiceDistDetailDataVector v) {
        mInvoiceDistDetailList = v;
    }



    /**
     *  Get the mInvoiceDist field.
     *
     *@return    InvoiceDistData
     */
    public InvoiceDistData getInvoiceDist() {
        return mInvoiceDist;
    }


    /**
     *  Get the mInvoiceDistDetailList field.
     *
     *@return    InvoiceDistDetailDataVector
     */
    public InvoiceDistDetailDataVector getInvoiceDistDetailList() {
        return mInvoiceDistDetailList;
    }


    
    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this OrderItemStatusDescData object
     */
    public String toString() {
        
        InvoiceDistDetailData InvoiceDistDetail = null;
        if(null != mInvoiceDistDetailList && mInvoiceDistDetailList.size() > 0) {
            InvoiceDistDetail = (InvoiceDistDetailData) mInvoiceDistDetailList.get(0);
        }
        
        return "[" +  "InvoiceDist=" +
                mInvoiceDist.toString() + ", InvoiceDistDetail=" + InvoiceDistDetail.toString() 
                 + "]";
    }


    /**
     *  Creates a new OrderItemStatusDescData
     *
     *@return    Newly initialized OrderItemStatusDescData object.
     */
    public static InvoiceDistDescData createValue() {
        InvoiceDistDescData valueData = new InvoiceDistDescData();
        return valueData;
    }

}


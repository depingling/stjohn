package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;


/**
 * <code>ProcessInvoiceResponseData</code> is a ValueObject
 * class wrapping of the database table CLW_ORDER.
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public class ProcessInvoiceResponseData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -852876876246399979L;
    
    private String mInvoiceStatusCd;

    /**
     * Get the value of InvoiceStatusCd.
     * @return value of InvoiceStatusCd.
     */
    public String getInvoiceStatusCd() {
	return mInvoiceStatusCd;
    }
    
    /**
     * Set the value of InvoiceStatusCd.
     * @param v  Value to assign to InvoiceStatusCd.
     */
    public void setInvoiceStatusCd(String  v) {
	this.mInvoiceStatusCd = v;
    }
    

    private ProcessInvoiceResponseData ()
    {
	mInvoiceStatusCd = "---";
    }

    /**
     * Creates a new ProcessInvoiceResponseData
     *
     * @return
     *  Newly initialized ProcessInvoiceResponseData object.
     */
    public static ProcessInvoiceResponseData createValue () 
    {
        ProcessInvoiceResponseData valueData = 
	    new ProcessInvoiceResponseData();
        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProcessInvoiceResponseData object
     */
    public String toString()
    {
	String s = "[" + "InvoiceStatusCd=" + mInvoiceStatusCd + " \n"; 

	int n = mMsgs.size();
	for ( int i = 0; i < n; i++ ) {
	    s += "\n  [" + i + "] " + (String)mMsgs.get(i);
	}
	s += "\n ] ";
        return s;
    }

    boolean mOK = true;
    public boolean isOK() {
	return mOK;
    }
    public void setOKFlag(boolean v) {
	mOK = v;
    }

    ArrayList mMsgs = null;
    public void addResponseMsg(String pMsg) {
	if ( null == mMsgs ) {
	    mMsgs = new ArrayList(5);
	}
	mMsgs.add(pMsg);
    }

    public String [] getMessages() {
	int n = mMsgs.size();
	String [] marr = new String[n];
	for ( int i = 0; i < n; i++ ) {
	    marr[i] = (String)mMsgs.get(i);
	}

	return marr;
    }

    
}

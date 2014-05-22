package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.RefCodeNames;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.BigDecimal;


/**
 * <code>InvoiceRequestData</code> is a ValueObject class
 *  representing an order request.
 */
public class InvoiceRequestData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = -6901334914345699748L;

    private InvoiceDistData mInvoiceD;
    private InvoiceDistDetailDataVector mInvoiceDetailDV;
    private String mSkuTypeCd;
    private BigDecimal mControlTotalSum=new BigDecimal(0);
    /** Holds value of property distributorCompanyCode. */
    private String distributorCompanyCode;
    private String distributorAccountRefNum;
    private boolean checkTotal = true;
    private String matchPoNumType = RefCodeNames.MATCH_PO_NUM_TYPE_CD.DEFAULT; 
    private int parentEventId = 0;
    /**
     * Creates a new InvoiceRequestData
     *
     * @return
     *  Newly initialized InvoiceRequestData object.
     */
    public static InvoiceRequestData createValue ()
    {
        InvoiceRequestData valueData = new InvoiceRequestData();

        return valueData;
    }
    /**
     * Get the value of InvoiceD.
     * @return value of InvoiceD.
     */
    public InvoiceDistData getInvoiceD() {
	    return mInvoiceD;
    }

    /**
     * Set the value of InvoiceD.
     * @param v  Value to assign to InvoiceD.
     */
    public void setInvoiceD(InvoiceDistData v) {
	    mInvoiceD = v;
    }

    /**
     * Get the value of InvoiceDetailDV.
     * @return value of InvoiceDetailDV.
     */
    public InvoiceDistDetailDataVector getInvoiceDetailDV() {
	    return mInvoiceDetailDV;
    }

    /**
     * Set the value of InvoiceDetailDV.
     * @param v  Value to assign to InvoiceDetailDV.
     */
    public void setInvoiceDetailDV(InvoiceDistDetailDataVector  v) {
	    mInvoiceDetailDV = v;
    }

    /**
     * Get the value of SkuTypeCd.
     * @return value of SkuTypeCd.
     */
    public String getSkuTypeCd() {
	    return mSkuTypeCd;
    }

    /**
     * Set the value of SkuTypeCd.
     * @param v  Value to assign to SkuTypeCd.
     */
    public void setSkuTypeCd(String v) {
	    mSkuTypeCd = v;
    }
    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderData object
     */
    public String toString()
    {
    return "[" + "Invoice=" + mInvoiceD +", Invoice Details =" + mInvoiceDetailDV + ", Sku Type Code=" + mSkuTypeCd + "]";
    }

    /** Getter for property distributorCompanyCode.
     * @return Value of property distributorCompanyCode.
     *
     */
    public String getDistributorCompanyCode() {
        return this.distributorCompanyCode;
    }

    /** Setter for property distributorCompanyCode.
     * @param distributorCompanyCode New value of property distributorCompanyCode.
     *
     */
    public void setDistributorCompanyCode(String distributorCompanyCode) {
        this.distributorCompanyCode = distributorCompanyCode;
    }

    /** Getter for property distributorAccountRefNum.
     * @return Value of property distributorAccountRefNum.
     *
     */
    public String getDistributorAccountRefNum() {
        return this.distributorAccountRefNum;
    }

    /** Setter for property distributorAccountRefNum.
     * @param distributorCompanyCode New value of property distributorAccountRefNum.
     *
     */
    public void setDistributorAccountRefNum(String distributorAccountRefNum) {
        this.distributorAccountRefNum = distributorAccountRefNum;
    }


    /**
     * Marks this invoice as a credit invoice.  Puts it in a consistent
     * format (negative quantity, positive cost, negative subtotal, negative sales tax.
     * Leaves the freight and misc charges unchanged.  These are assumed
     * to be signed values, and a vendor may want to charge frieght on
     * a credit for example.
     */
    public void setInvoiceAsCredit(){
        //set the details appropriatly
        Iterator it = getInvoiceDetailDV().iterator();
        while(it.hasNext()){
            InvoiceDistDetailData currDetail = (InvoiceDistDetailData)it.next();
            if(currDetail.getDistItemQuantity() > 0){
                currDetail.setDistItemQuantity(-1 * currDetail.getDistItemQuantity());
            }
            currDetail.setItemReceivedCost(currDetail.getItemReceivedCost().abs());
        }
        //set the header appropriatly
        if(getInvoiceD().getSubTotal() != null){
            getInvoiceD().setSubTotal(getInvoiceD().getSubTotal().abs().negate());
        }
        //set the header appropriatly
        if(getInvoiceD().getSalesTax() != null){
            getInvoiceD().setSalesTax(getInvoiceD().getSalesTax().abs().negate());
        }
    }


    public BigDecimal getControlTotalSum() {
        return mControlTotalSum;
    }

    public void setControlTotalSum(BigDecimal controlTotalSum) {
        this.mControlTotalSum = controlTotalSum;
    }
	public void setCheckTotal(boolean checkTotal) {
		this.checkTotal = checkTotal;
	}
	public boolean isCheckTotal() {
		return checkTotal;
	}
	
	public void setMatchPoNumType(String matchPoNumType) {
		this.matchPoNumType = matchPoNumType;
	}

	public String getMatchPoNumType() {
		return matchPoNumType;
	}
	public void setParentEventId(int parentEventId) {
		this.parentEventId = parentEventId;
	}
	public int getParentEventId() {
		return parentEventId;
	}
}

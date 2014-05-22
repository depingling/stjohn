/*
 * InoviceView.java
 *
 * Created on November 14, 2005, 5:49 PM
 *
 * Copyright November 14, 2005 Cleanwise, Inc.
 */

package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.util.Date;
import java.math.BigDecimal;
/**
 *
 * @author bstevens
 */
public class InvoiceAbstractionView extends ValueObject{
    InvoiceDistData dist;
    InvoiceCustData cust;
    
    /**
     *Returns the underlying distrbutor invoice if it exists, otherwise returns null
     */
    public InvoiceDistData getInvoiceDistData(){
        return dist;
    }
    
    /**
     *Returns the underlying customer invoice if it exists, otherwise returns null
     */
    public InvoiceCustData getInvoiceCustData(){
        return cust;
    }
    
    /**
     *Returns true if the underlying object is a distributor invoice
     */
    public boolean isInvoiceDist(){
        return dist != null;
    }
    
    /**
     *Returns true if the underlying object is a customer invoice
     */
    public boolean isInvoiceCust(){
        return cust != null;
    }
    
    /** Creates a new instance of InvoiceDetailLineItem */
    public InvoiceAbstractionView(InvoiceCustData invoice) {
        cust = invoice;
    }
    
    /** Creates a new instance of InvoiceDetailLineItem */
    public InvoiceAbstractionView(InvoiceDistData invoice) {
        dist = invoice;
    }
    
    public String getBillToAddress1(){
        if(cust != null){
            return cust.getBillToAddress1();
        }
        return null;
    }
    
    public String getBillToAddress2(){
        if(cust != null){
            return cust.getBillToAddress2();
        }
        return null;
    }
    
    public String getBillToAddress3(){
        if(cust != null){
            return cust.getBillToAddress3();
        }
        return null;
    }
    
    public String getBillToAddress4(){
        if(cust != null){
            return cust.getBillToAddress4();
        }
        return null;
    }
    
    public String getBillToCity(){
        if(cust != null){
            return cust.getBillToCity();
        }
        return null;
    }
    
    public String getBillToCountry(){
        if(cust != null){
            return cust.getBillToCountry();
        }
        return null;
    }
    
    public String getBillToName(){
        if(cust != null){
            return cust.getBillToName();
        }
        return null;
    }
    
    public String getBillToPostalCode(){
        if(cust != null){
            return cust.getBillToPostalCode();
        }
        return null;
    }
    
    public String getBillToState(){
        if(cust != null){
            return cust.getBillToState();
        }
        return null;
    }
    
    public int getAccountId(){
        if(cust != null){
            return cust.getAccountId();
        }
        return 0;
    }
    
    public String getAddBy(){
        if(cust != null){
            return cust.getAddBy();
        }else if(dist != null){
            return dist.getAddBy();
        }
        return null;
    }
    
    public Date getAddDate(){
        if(cust != null){
            return cust.getAddDate();
        }else if(dist != null){
            return dist.getAddDate();
        }
        return null;
    }
    
    public int getCitAssignmentNumber(){
        if(cust != null){
            return cust.getCitAssignmentNumber();
        }
        return 0;
    }
    
    public void setCitAssignmentNumber(int val){
        if(cust != null){
            cust.setCitAssignmentNumber(val);
        }else{
            throw new NullPointerException("Trying to set CitAssignmentNumber for a non customer invoice");
        }
    }
    
    public void setCitStatusCd(String val){
        if(cust != null){
            cust.setCitStatusCd(val);
        }else{
            throw new NullPointerException("Trying to set CitStatusCd for a non customer invoice");
        }
    }
    
    public String getCitStatusCd(){
        if(cust != null){
            return cust.getCitStatusCd();
        }
        return null;
    }
    
    public Date getCitTransactionDate(){
        if(cust != null){
            return cust.getCitTransactionDate();
        }
        return null;
    }
    
    public void setCitTransactionDate(Date val){
        if(cust != null){
            cust.setCitTransactionDate(val);
        }else{
            throw new NullPointerException("Trying to set CitTransactionDate for a non customer invoice");
        }
    }
    
    public BigDecimal getCredits(){
        if(cust != null){
            return cust.getCredits();
        }else if(dist != null){
            return dist.getCredits();
        }
        return null;
    }
    
    public BigDecimal getDiscounts(){
        if(cust != null){
            return cust.getDiscounts();
        }else if(dist != null){
            return dist.getDiscounts();
        }
        return null;
    }
    
    public String getErpPoNum(){
        if(cust != null){
            return cust.getErpPoNum();
        }else if(dist != null){
            return dist.getErpPoNum();
        }
        return null;
    }
    
    public String getErpSystemCd(){
        if(cust != null){
            return cust.getErpSystemCd();
        }else if(dist != null){
            return dist.getErpSystemCd();
        }
        return null;
    }
    
    public BigDecimal getFreight(){
        if(cust != null){
            return cust.getFreight();
        }else if(dist != null){
            return dist.getFreight();
        }
        return null;
    }
    
    public int getInvoiceId(){
        if(cust != null){
            return cust.getInvoiceCustId();
        }else if(dist != null){
            return dist.getInvoiceDistId();
        }
        return 0;
    }
    /**here for compatiblity @see getInvoiceId*/
    public int getInvoiceCustId(){
        return getInvoiceId();
    }
    
    public Date getInvoiceDate(){
        if(cust != null){
            return cust.getInvoiceDate();
        }else if(dist != null){
            return dist.getInvoiceDate();
        }
        return null;
    }
    
    public String getInvoiceNum(){
        if(cust != null){
            return cust.getInvoiceNum();
        }else if(dist != null){
            return dist.getInvoiceNum();
        }
        return null;
    }
    
    public String getInvoiceStatusCd(){
        if(cust != null){
            return cust.getInvoiceStatusCd();
        }else if(dist != null){
            return dist.getInvoiceStatusCd();
        }
        return null;
    }
    
    public void setInvoiceStatusCd(String val){
        if(cust != null){
            cust.setInvoiceStatusCd(val);
        }else if(dist != null){
            dist.setInvoiceStatusCd(val);
        }
        
    }
    
    /**Contains logic to determin if distributor invoice is a credit or in @see RefCodeNames.INVOICE_TYPE_CD*/
    public String getInvoiceType(){
        if(cust != null){
            return cust.getInvoiceType();
        }else if(dist != null){
            if(dist.getSubTotal().compareTo(new BigDecimal(0))<0){
                return RefCodeNames.INVOICE_TYPE_CD.CR;
            }else{
                return RefCodeNames.INVOICE_TYPE_CD.IN;
            }
        }
        return null;
    }
    
    public BigDecimal getMiscCharges(){
        if(cust != null){
            return cust.getMiscCharges();
        }else if(dist != null){
            return dist.getMiscCharges();
        }
        return null;
    }
    
    
    public String getModBy(){
        if(cust != null){
            return cust.getModBy();
        }else if(dist != null){
            return dist.getModBy();
        }
        return null;
    }
    
    public Date getModDate(){
        if(cust != null){
            return cust.getModDate();
        }else if(dist != null){
            return dist.getModDate();
        }
        return null;
    }
    
    /**
     * @return the total amount to be paid
     */
    public BigDecimal getNetDue(){
        if(cust != null){
            return cust.getNetDue();
        }else if(dist != null){
            BigDecimal total = dist.getSubTotal();
            total = Utility.addAmt(total,dist.getMiscCharges());
            total = Utility.subtractAmt(total,absBigD(dist.getDiscounts()));
            total = Utility.subtractAmt(total,absBigD(dist.getCredits()));
            total = Utility.addAmt(total,dist.getFreight());
            total = Utility.addAmt(total,dist.getSalesTax());
            return total;
        }
        return null;
    }
    
    /**
     * @return the total amount to be paid before discounts and credits
     */
    public BigDecimal getGrossDue(){
        if(cust != null){
            BigDecimal total = cust.getSubTotal();
            total = Utility.addAmt(total,cust.getFreight());
            total = Utility.addAmt(total,cust.getMiscCharges());
            total = Utility.addAmt(total,cust.getSalesTax());
            return total;
        }else if(dist != null){
            BigDecimal total = dist.getSubTotal();
            total = Utility.addAmt(total,dist.getMiscCharges());
            total = Utility.addAmt(total,dist.getFreight());
            total = Utility.addAmt(total,dist.getSalesTax());
            return total;
        }
        return null;
    }
    
    private BigDecimal absBigD(BigDecimal amt){
        if(amt == null){
            return null;
        }
        return amt.abs();
    }
    
    public int getOrderId(){
        if(cust != null){
            return cust.getOrderId();
        }else if(dist != null){
            return dist.getOrderId();
        }
        return 0;
    }
    
    public String getOriginalInvoiceNum(){
        if(cust != null){
            return cust.getOriginalInvoiceNum();
        }else if(dist != null){
            return null;
        }
        return null;
    }
    
    public String getPaymentTermsCd(){
        if(cust != null){
            return cust.getPaymentTermsCd();
        }else if(dist != null){
            return null;
        }
        return null;
    }
    
    public BigDecimal getSalesTax(){
        if(cust != null){
            return cust.getSalesTax();
        }else if(dist != null){
            return dist.getSalesTax();
        }
        return null;
    }
    
    public String getShippingAddress1(){
        if(cust != null){
            return cust.getShippingAddress1();
        }else if(dist != null){
            return dist.getShipToAddress1();
        }
        return null;
    }
    
    public String getShippingAddress2(){
        if(cust != null){
            return cust.getShippingAddress2();
        }else if(dist != null){
            return dist.getShipToAddress2();
        }
        return null;
    }
    
    public String getShippingAddress3(){
        if(cust != null){
            return cust.getShippingAddress3();
        }else if(dist != null){
            return dist.getShipToAddress3();
        }
        return null;
    }
    
    public String getShippingAddress4(){
        if(cust != null){
            return cust.getShippingAddress4();
        }else if(dist != null){
            return dist.getShipToAddress4();
        }
        return null;
    }
    
    public String getShippingCity(){
        if(cust != null){
            return cust.getShippingCity();
        }else if(dist != null){
            return dist.getShipToCity();
        }
        return null;
    }
    
    public String getShippingCountry(){
        if(cust != null){
            return cust.getShippingCountry();
        }else if(dist != null){
            return dist.getShipToCountry();
        }
        return null;
    }
    
    public String getShippingName(){
        if(cust != null){
            return cust.getShippingName();
        }else if(dist != null){
            return dist.getShipToName();
        }
        return null;
    }
    
    public String getShippingPostalCode(){
        if(cust != null){
            return cust.getShippingPostalCode();
        }else if(dist != null){
            return dist.getShipToPostalCode();
        }
        return null;
    }
    
    public String getShippingState(){
        if(cust != null){
            return cust.getShippingState();
        }else if(dist != null){
            return dist.getShipToState();
        }
        return null;
    }
    
    public int getSiteId(){
        if(cust != null){
            return cust.getSiteId();
        }else if(dist != null){
            return 0;
        }
        return 0;
    }
    
    public int getStoreId(){
        if(cust != null){
            return cust.getStoreId();
        }else if(dist != null){
            return dist.getStoreId();
        }
        return 0;
    }
    
    public BigDecimal getSubTotal(){
        if(cust != null){
            return cust.getSubTotal();
        }else if(dist != null){
            return dist.getSubTotal();
        }
        return null;
    }
    
    public void markAcknowledged(){
        if(cust != null){
            cust.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED);
        }else if(dist != null){
            dist.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED);
        }
    }
}

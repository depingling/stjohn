/*
 * InvoiceDetailLineItem.java
 *
 * Created on November 14, 2005, 5:48 PM
 *
 * Copyright November 14, 2005 Cleanwise, Inc.
 */

package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.ValueObject;
import java.util.Date;
import java.math.BigDecimal;

/**
 *
 * @author bstevens
 */
public class InvoiceAbstractionDetailView extends ValueObject{
    InvoiceCustDetailData cust;
    InvoiceDistDetailData dist;
    
    /**
     *Returns the underlying distrbutor invoice if it exists, otherwise returns null
     */
    public InvoiceDistDetailData getInvoiceDistDetailData(){
        return dist;
    }
    
    /**
     *Returns the underlying customer invoice if it exists, otherwise returns null
     */
    public InvoiceCustDetailData getInvoiceCustDetailData(){
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
    public InvoiceAbstractionDetailView(InvoiceCustDetailData detail) {
        cust = detail;
    }
    
    /** Creates a new instance of InvoiceDetailLineItem */
    public InvoiceAbstractionDetailView(InvoiceDistDetailData detail) {
        dist = detail;
    }
    
    
    public String getERPAccountCode(){
    	if(dist != null){
    		return dist.getErpAccountCode();
    	}
    	//not implemented for customer invoice
    	return null;
    }
    
    public void setInvoiceDetailStatusCd(String val){
        if(cust != null){
            cust.setInvoiceDetailStatusCd(val);
        }
        //just ignore
    }
    
    public String getInvoiceDetailStatusCd(){
        if(cust != null){
            return cust.getInvoiceDetailStatusCd();
        }else if(dist != null){
            return null;
        }
        return null;
    }
    
    public void setShipStatusCd(String val){
        if(cust != null){
            cust.setShipStatusCd(val);
        }else{
        	dist.setShipStatusCd(val);
        }
    }
    
    public String getShipStatusCd(){
        if(cust != null){
            return cust.getShipStatusCd();
        }else if(dist != null){
            return null;
        }
        return null;
    }
    
    
    public void setRebateStatusCd(String val){
        if(cust != null){
            cust.setRebateStatusCd(val);
        }else{
            throw new NullPointerException("Trying to set RebateStatusCd for a non customer detail invoice");
        }
    }
    
    public String getRebateStatusCd(){
        if(cust != null){
            return cust.getRebateStatusCd();
        }else if(dist != null){
            return null;
        }
        return null;
    }
    
    public int getOrderItemId(){
        if(cust != null){
            return cust.getOrderItemId();
        }else if(dist != null){
            return dist.getOrderItemId();
        }
        return 0;
    }
    
    public int getLineNumber(){
        if(cust != null){
            return cust.getLineNumber();
        }else if(dist != null){
            return dist.getErpPoLineNum();
        }
        return 0;
    }
    
    public String getItemUom(){
        if(cust != null){
            return cust.getItemUom();
        }else if(dist != null){
            return dist.getItemUom();
        }
        return null;
    }
    
    public void setItemUom(String val){
        if(cust != null){
            cust.setItemUom(val);
        }else if(dist != null){
            dist.setItemUom(val);
        }
    }
    
    //XXX should be String and return the actual sku number??
    public int getItemSkuNum(){
        if(cust != null){
            return cust.getItemSkuNum();
        }else if(dist != null){
            return dist.getItemSkuNum();
        }
        return 0;
    }
    
    public String getItemShortDesc(){
        if(cust != null){
            return cust.getItemShortDesc();
        }else if(dist != null){
            return dist.getItemShortDesc();
        }
        return null;
    }
    
    public String getItemPack(){
        if(cust != null){
            return cust.getItemPack();
        }else if(dist != null){
            return dist.getItemPack();
        }
        return null;
    }
    
    
    public BigDecimal getLineTotal(){
        if(cust != null){
            return cust.getLineTotal();
        }else if(dist != null){
            return getCustContractPrice().multiply(new BigDecimal(getItemQuantity()));
        }
        return null;
    }
    
    public int getItemQuantity(){
        if(cust != null){
            return cust.getItemQuantity();
        }else if(dist != null){
            return dist.getDistItemQuantity();
        }
        return 0;
    }
    
    public BigDecimal getCustContractPrice(){
        if(cust != null){
            return cust.getCustContractPrice();
        }else if(dist != null){
            return dist.getAdjustedCost();
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
    
    public int getInvoiceDetailId(){
        if(cust != null){
            return cust.getInvoiceCustDetailId();
        }else if(dist != null){
            return dist.getInvoiceDistDetailId();
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
}

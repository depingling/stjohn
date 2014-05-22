/*
 * ShoppingForm.java
 *
 * Created on September 28, 2005, 2:54 PM
 *
 * Copyright September 28, 2005 Cleanwise, Inc.
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import com.cleanwise.service.api.util.*;

import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Extending forms are those that are used in the shopping/checkout process.
 * Here are their shared methods that are useful when identidcal information
 * @author bstevens
 */
public abstract class ShoppingForm extends ActionForm{
    HashMap taxCalcCache = new HashMap();

    /**
     *Sets a cached sales tax calculation based off the taxable amount, and site id.  It is assumed that
     *this represents a unique tax evaluation.
     */
    public void setCacheCalculatedSalesTaxAmt(int siteId, BigDecimal taxAmt, BigDecimal taxBaseAmt){
        String key = siteId+":"+taxBaseAmt;
        taxCalcCache.put(key,taxAmt);
    }

    /**
     *Sets a cached sales tax calculation based off the taxable amount, and site id.  It is assumed that
     *this represents a unique tax evaluation.
     */
    public BigDecimal getCacheCalculatedSalesTaxAmt(int siteId, BigDecimal taxBaseAmt){
        String key = siteId+":"+taxBaseAmt;
        return (BigDecimal) taxCalcCache.get(key);
    }


    /**
     *Returns the total dollar amount of the items in the shopping cart
     */
    public java.math.BigDecimal getCartItemsAmt(HttpServletRequest request) {
        return new java.math.BigDecimal(getItemsAmt(null,0, false,request));
    }

    /**
     *Returns the total dollar amount of the taxable items in the shopping cart
     */
    public java.math.BigDecimal getCartItemsAmtTaxable(HttpServletRequest request) {
        return new java.math.BigDecimal(getItemsAmt(Boolean.TRUE,0, false,request));
    }

    /**
     *Returns the total dollar amount of the items in the shopping cart for a specific cost center
     */
    public java.math.BigDecimal getCartItemsAmt(int pCostCenterId, boolean budgetedOnly,HttpServletRequest request) {
        return new java.math.BigDecimal(getItemsAmt(null,pCostCenterId,budgetedOnly,request));
    }

    /**
     *Returns the total dollar amount of the taxable items in the shopping cart for a specific cost center
     */
    public java.math.BigDecimal getCartItemsAmtTaxable(int pCostCenterId, boolean budgetedOnly,HttpServletRequest request) {
        return new java.math.BigDecimal(getItemsAmt(Boolean.TRUE,pCostCenterId,budgetedOnly,request));
    }

    /**
     *Returns the total dollar amount of the items in the shopping cart as a double
     *same as @see getCartItemsAmt but is a double value.
     */
    public double getItemsAmt(HttpServletRequest request) {
        return getItemsAmt(null,0, false,request);
    }

    public double getItemsAmt() {
    	Thread.dumpStack();
    	return getItemsAmt(null,0, false,null);
    }

    /**
     *Method to figure out the total dollar amount of items in the cart taxing into account the taxability of the items.
     *if pOptTaxable == null everything is added up.  Otherwise the total taxes the value of the pOptTaxable and compares it
     *to the item taxability.  This method also takes into account the passed in pOptionalCostCenterId, totaling only those item
     *in the supplied cost center id.  If pOptionalCostCenterId == 0 then this condition is ignored
     */
    private double getItemsAmt(Boolean pOptTaxable, int pOptionalCostCenterId, boolean budgetedOnly,HttpServletRequest request) {
        ShoppingCartItemDataVector _cartItems = getItems();

        if(_cartItems==null) return 0;
        double cost = 0;
        CostCenterDataVector ccdv;
        SiteData site = null;
        if(request == null){
        	ccdv = new CostCenterDataVector();
        }else{
        	ccdv = ShopTool.getAllCostCenters(request);
        	HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            site = appUser.getSite();            
        }

        for(int ii=0; ii<_cartItems.size(); ii++) {
            ShoppingCartItemData sciD = (ShoppingCartItemData)_cartItems.get(ii);
            if(!sciD.getDuplicateFlag()) {
                if(pOptTaxable == null || pOptTaxable.booleanValue() == sciD.isTaxable()){
                    if(pOptionalCostCenterId == 0 || pOptionalCostCenterId == sciD.getProduct().getCostCenterId()){

                    	if(!budgetedOnly ||  sciD.getProduct().getCostCenterId() != 0){
                    			if(!budgetedOnly){
                    				cost += sciD.getAmount();
                    				continue;
                    			}
                    			int ccid = sciD. getProduct().getCostCenterId();

                    			for(int i=0; i<ccdv.size();i++){
                    				CostCenterData ccd = (CostCenterData)ccdv.get(i);
                    				if(ccd.getCostCenterId() == ccid ){
                    					if(!Utility.isTrue(ccd.getNoBudget())){
                    						boolean unLimitedBudget = false;                    				        
                    						if (site != null){
                    							unLimitedBudget = site.isBudgetUnlimited(ccid);
                            				}
                            		        if (!unLimitedBudget)
                            		        	cost += sciD.getAmount();
                    					}
                    				}
                    			}

                        }

                    }
                    }
                }
            }
        return cost;
       }


    /**
     *Implementing classes should return the current items in the cart
     */
    public abstract ShoppingCartItemDataVector getItems();
    
    /**
     *Returns the total dollar amount of the PAR items in the shopping cart as a double
     *same as @see getCartPARItemsTotal but is a double value.
     */
    public double getPARItemsTotal(HttpServletRequest request) {
        return getItemsTotalAmt(null,0, false,request,true);
    }
    
    /**
     * Returns the total dollar amount of the PAR items in the shopping cart
     */
    public java.math.BigDecimal getCartPARItemsTotal(HttpServletRequest request) {
        return new java.math.BigDecimal(getItemsTotalAmt(null,0, false,request,true));
    }
    
    /**
     *Returns the total dollar amount of the Non-PAR items in the shopping cart as a double
     *same as @see getCartNonPARItemsTotalAmt but is a double value.
     */
    public double getNonPARItemsTotal(HttpServletRequest request) {
        return getItemsTotalAmt(null,0, false,request,false);
    }
    
    /**
     * Returns the total dollar amount of the PAR items in the shopping cart
     */
    public java.math.BigDecimal getCartNonPARItemsTotalAmt(HttpServletRequest request) {
    	return new java.math.BigDecimal(getItemsTotalAmt(null,0, false,request,false));
    }
    
    /**
     *Method to figure out the total dollar amount of PAR or Non-PAR items in the cart taxing into account the taxability of the items.
     *if pOptTaxable == null everything is added up.  Otherwise the total taxes the value of the pOptTaxable and compares it
     *to the item taxability.  This method also takes into account the passed in pOptionalCostCenterId, totaling only those item
     *in the supplied cost center id.  If pOptionalCostCenterId == 0 then this condition is ignored
     * If needOnlyPARItemsAmt is <code>true</code> then Inventory (PAR) Items total will be calculated.
     * If needOnlyPARItemsAmt is <code>false</code> then Non-PAR Items total will be calculated. 
     */
    private double getItemsTotalAmt(Boolean pOptTaxable, int pOptionalCostCenterId, boolean budgetedOnly,HttpServletRequest request,
    		boolean needOnlyPARItemsAmt) {
        ShoppingCartItemDataVector _cartItems = getItems();

        if(_cartItems==null) return 0;
        double cost = 0;
        CostCenterDataVector ccdv;
        if(request == null){
        	ccdv = new CostCenterDataVector();
        }else{
        	ccdv = ShopTool.getAllCostCenters(request);
        }
        
        boolean allowCalculation;
        
        for(int ii=0; ii<_cartItems.size(); ii++) {
        	allowCalculation = false;
            ShoppingCartItemData sciD = (ShoppingCartItemData)_cartItems.get(ii);
            
            if(needOnlyPARItemsAmt) {// Calculate total amount for inventory items.
            	allowCalculation = sciD.getIsaInventoryItem();
            } else { // Calculate total amount for Non-PAR items.
            	allowCalculation = !sciD.getIsaInventoryItem();
            }
            
            if(!sciD.getDuplicateFlag() && allowCalculation) {
                if(pOptTaxable == null || pOptTaxable.booleanValue() == sciD.isTaxable()){
                    if(pOptionalCostCenterId == 0 || pOptionalCostCenterId == sciD.getProduct().getCostCenterId()){

                    	if(!budgetedOnly ||  sciD.getProduct().getCostCenterId() != 0){
                    			if(!budgetedOnly){
                    				cost += sciD.getAmount();
                    				continue;
                    			}
                    			int ccid = sciD. getProduct().getCostCenterId();

                    			for(int i=0; i<ccdv.size();i++){
                    				CostCenterData ccd = (CostCenterData)ccdv.get(i);
                    				if(ccd.getCostCenterId() == ccid ){
                    					if(!Utility.isTrue(ccd.getNoBudget())){
                    						cost += sciD.getAmount();
                    					}
                    				}
                    			}

                        }

                    }
                    }
                }
            }
        return cost;
       }
}

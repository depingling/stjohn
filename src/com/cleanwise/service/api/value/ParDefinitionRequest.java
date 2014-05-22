/*
 * BudgetDefinitionRequest.java
 *
 * Created on March 3, 2005, 9:56 AM
 */

package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;

import java.util.ArrayList;


/**
 *
 * @author bstevens
 */
public class ParDefinitionRequest extends ValueObject {

    /**
     * Holds value of property entityKey.
     */
    private String entityKey;

    private String sku;
    private String skuTypeCd;
    private String continueOnError;

    /**
     * Holds value of property amountForFuturePeriods.
     */
    private Integer amountForAllPeriods;
    private ArrayList<Integer> amounts = new ArrayList<Integer>();


    /**
     * Getter for property entityKey.
     * @return Value of property entityKey.
     */
    public String getEntityKey() {
        return this.entityKey;
    }

    /**
     * Setter for property entityKey.
     * @param entityKey New value of property entityKey.
     */
    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }


    /**
     *Provides information on the contents of the bean properties and other debugging info
     */
    public String toString(){
        StringBuffer buf = new StringBuffer();
        buf.append("Sku=").append(getSku()).append(" ");
        buf.append("EntityKey=").append(getEntityKey()).append(" ");
        buf.append("amounts=").append(getAmounts());
        return buf.toString();
    }

	public Integer getAmountForAllPeriods() {
		return amountForAllPeriods;
	}

	public void setAmountForAllPeriods(Integer amountForAllPeriods) {
		this.amountForAllPeriods = amountForAllPeriods;
	}


	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSkuTypeCd() {
		return skuTypeCd;
	}

	public void setSkuTypeCd(String skuTypeCd) {
		this.skuTypeCd = skuTypeCd;
	}

	public String getContinueOnError() {
		return continueOnError;
	}

	public void setContinueOnError(String continueOnError) {
		this.continueOnError = continueOnError;
	}

    public ArrayList<Integer> getAmounts() {
        return amounts;
    }

    public void setAmount(Integer amount) {
        this.amounts.add(amount);
    }

}

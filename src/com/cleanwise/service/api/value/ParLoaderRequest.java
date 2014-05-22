/**
 * ParLoaderRequest.java
 *
 * Created on Sept 6, 2007
 */
package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Ssharma
 *
 */
public class ParLoaderRequest extends ValueObject {

	/**
     * Holds value of property entityKey.
     */
    private String entityKey;

    /**
     * Holds value of property distSku.
     */
    private String distSku;

    private String skuTypeCd;

    /**
     * Holds value of property accountId.
     */
    private int accountId;

    /**
     * Holds value of property accountName.
     */
    private String accountName;

    /**
     * Holds value of property siteId.
     */
    private int siteId;

    private Integer allPeriods;
    private Map<String,Integer> periods = new HashMap<String,Integer>();
    private String continueOnError;

     /** Creates a new instance of ParLoaderRequest */
     public ParLoaderRequest() {}

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
      * Getter for property distSku.
      * @return Value of property distSku.
      */
     public String getDistSku() {
         return this.distSku;
     }

     /**
      * Setter for property distSku.
      * @param distSku New value of property distSku.
      */
     public void setDistSku(String distSku) {
         this.distSku = distSku;
     }

     /**
      * Getter for property accountId.
      * @return Value of property accountId.
      */
     public int getAccountId() {
         return this.accountId;
     }

     /**
      * Setter for property accountId.
      * @param accountId New value of property accountId.
      */
     public void setAccountId(int accountId) {
         this.accountId = accountId;
     }

     /**
      * Getter for property accountName.
      * @return Value of property accountName.
      */
     public String getAccountName() {
         return this.accountName;
     }

     /**
      * Setter for property accountName.
      * @param accountName New value of property accountName.
      */
     public void setAccountName(String accountName) {
         this.accountName = accountName;
     }

     /**
      * Getter for property siteId.
      * @return Value of property siteId.
      */
     public int getSiteId() {
         return this.siteId;
     }

     /**
      * Setter for property siteId.
      * @param siteId New value of property siteId.
      */
     public void setSiteId(int siteId) {
         this.siteId = siteId;
     }


     /**
      *Provides information on the contents of the bean properties and other debugging info
      */
     public String toString(){
         StringBuffer buf = new StringBuffer();
         buf.append("AccountId="+getAccountId()+" ");
         buf.append("AccountName="+getAccountName()+" ");
         buf.append("SiteId="+getSiteId()+" ");
         buf.append("EntityKey="+getEntityKey()+" ");
         buf.append("DistSku="+getDistSku()+" ");
         buf.append("Periods="+getPeriods());
         return buf.toString();
     }

     public Object clone() {
 		ParLoaderRequest plr = new ParLoaderRequest();
 		plr.entityKey = this.entityKey;
 		plr.accountId = this.accountId;
 		plr.accountName = this.accountName;
 		plr.siteId = this.siteId;
 		plr.distSku = this.distSku;
 	  //  plr.periods = (ArrayList<Integer>) this.periods.clone();
 		return plr;
 	}

     public Integer getAllPeriods() {
 		return allPeriods;
 	}

 	public void setAllPeriods(Integer allPeriods) {
 		this.allPeriods = allPeriods;
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

	private boolean loadOnlyIfData;
	public boolean isLoadOnlyIfData(){
		return loadOnlyIfData;
	}
	public void setLoadOnlyIfData(boolean loadOnlyIfData){
		this.loadOnlyIfData = loadOnlyIfData;
	}

	private boolean loadOnlyForActiveItem;
	public boolean isLoadOnlyForActiveItem(){
		return loadOnlyForActiveItem;
	}
	public void setLoadOnlyForActiveItem(boolean loadOnlyForActiveItem){
		this.loadOnlyForActiveItem = loadOnlyForActiveItem;
	}

    /*public ArrayList<Integer> getPeriods() {
        return periods;
    }

    public void setPeriod(Integer period) {
        this.periods.add(period);
    }*/

	
	public Map<String,Integer> getPeriods(){
		return periods;
	}
	
	
}

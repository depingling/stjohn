/*
 * OrderGuideLoadRequestData.java
 *
 * Created on August 17, 2004, 3:23 PM
 */

package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.ValueObject;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author  bstevens
 */
public class OrderGuideLoadRequestData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -5552824404791802141L;
	private String mEntityKey;
	/**
	 * Property that holds the key to identify the bus entity to lookup.
	 * If the bus entity is known then the orderGuide.busEntityId property 
	 * may be used
	 */
	public String getEntityKey(){
		return mEntityKey;
	}
	/**
	 * Property that holds the key to identify the bus entity to lookup.
	 * If the bus entity is known then the orderGuide.busEntityId property 
	 * may be used
	 */
	public void setEntityKey(String pVal){
		mEntityKey = pVal;
	}
	
    private List mItems = new ArrayList();
    /**
     * Holds value of property orderGuide.
     */
    private OrderGuideData orderGuide;
    
    /**
     * Holds value of property skuTypeCd.
     */
    private String skuTypeCd;
    
    /**
     * Holds value of property site.
     */
    private SiteData site;
    
    /** Creates a new instance of OrderGuideLoadRequestData */
    public OrderGuideLoadRequestData() {
    }
    
    /**
     * Getter for property orderGuide.
     * @return Value of property orderGuide.
     */
    public OrderGuideData getOrderGuide() {
        return this.orderGuide;
    }
    
    /**
     * Setter for property orderGuide.
     * @param orderGuide New value of property orderGuide.
     */
    public void setOrderGuide(OrderGuideData orderGuide) {
        this.orderGuide = orderGuide;
    }
    
    /**
     * Getter for property skuTypeCd.
     * @return Value of property skuTypeCd.
     */
    public String getSkuTypeCd() {
        return this.skuTypeCd;
    }    
    
    /**
     * Setter for property skuTypeCd.
     * @param skuTypeCd New value of property skuTypeCd.
     */
    public void setSkuTypeCd(String skuTypeCd) {
        this.skuTypeCd = skuTypeCd;
    }
    
    
    public List getItems(){
        return mItems;
    }
    
    public void addItem(int qty, String sku){
        mItems.add(new OrderGuideItemLoadRequestData(qty,sku));
    }
    
    /**
     * Getter for property site.
     * @return Value of property site.
     */
    public SiteData getSite() {
        return this.site;
    }
    
    /**
     * Setter for property site.
     * @param site New value of property site.
     */
    public void setSite(SiteData site) {
        this.site = site;
    }
    
    public class OrderGuideItemLoadRequestData extends ValueObject{
        
        private OrderGuideItemLoadRequestData(int pQty, String pSku){
            if(pSku == null){
                throw new NullPointerException("sku was null");
            }
            qty = pQty;
            sku = pSku;
        }
        
        /**
         * Holds value of property qty.
         */
        private int qty;
        
        /**
         * Holds value of property sku.
         */
        private String sku;
        
        /**
         * Getter for property qty.
         * @return Value of property qty.
         */
        public int getQty() {
            return this.qty;
        }
        
        /**
         * Setter for property qty.
         * @param qty New value of property qty.
         */
        public void setQty(int qty) {
            this.qty = qty;
        }
        
        /**
         * Getter for property sku.
         * @return Value of property sku.
         */
        public String getSku() {
            return this.sku;
        }
        
        /**
         * Setter for property sku.
         * @param sku New value of property sku.
         */
        public void setSku(String sku) {
            this.sku = sku;
        }
        
    }
}

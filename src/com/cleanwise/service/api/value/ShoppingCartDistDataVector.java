/*
 * ShoppingCartDistDataVector.java
 *
 * Created on July 30, 2005, 11:33 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.cleanwise.service.api.value;

import java.util.*;
import java.math.*;
import com.cleanwise.service.api.util.Utility;

import org.apache.log4j.Category;

/**
 *
 * @author lli
 */
public class ShoppingCartDistDataVector extends java.util.ArrayList implements Comparator {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 5514434420174693094L;
  
  static final Category log = Category.getInstance(ShoppingCartDistDataVector.class);

    public static final String NO_DISTRIBUTOR_AVAILABLE = "NO_DIST";
    private BigDecimal mTotalFreightCost;
    private boolean mHasEstimatedFreight = false;
    
    private BigDecimal mTotalDiscountCost;
    private BigDecimal mDiscountCostPerDist;
    private ArrayList mDiscCostForMultDist; 
    private HashMap<Integer,BigDecimal> mDiscCostForMultDistHM; 

    /** Creates a new instance of ShoppingCartDistDataVector */
    public ShoppingCartDistDataVector(ShoppingCartItemDataVector shoppingItems, ContractData pContract) {
        /*
        // group the shopping cart items according to distributor ID
        Hashtable distHash = new Hashtable();
        if (null != shoppingItems && shoppingItems.size() > 0) {
            for (int i = 0; i < shoppingItems.size(); i++) {
                ShoppingCartItemData item = (ShoppingCartItemData)shoppingItems.get(i);
                String key = null;
                if (null != item && null != item.getProduct()
                    && null != item.getProduct().getCatalogDistributor()) {

                    key = String.valueOf(item.getProduct().getCatalogDistributor().getBusEntityId());
                }
                if (null == key || key.trim().length() == 0) {
                    key = NO_DISTRIBUTOR_AVAILABLE;
                }
                List items = (List)distHash.get(key);
                if (null == items || items.size() == 0) {
                    items = new LinkedList();
                }
                items.add(item);
                distHash.put(key, items);
            }
        }

        if (! distHash.isEmpty()) {

            Set keys = distHash.keySet();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String)iterator.next();
                List items = (List)distHash.get(key);
                if (null != items && items.size() > 0) {
                    ShoppingCartDistData distCartD = new ShoppingCartDistData(items);

                    this.add(distCartD);
                }
            }
        }

        this.sort();
         */

        this(shoppingItems, 0, pContract);
    }


    public ShoppingCartDistDataVector(ShoppingCartItemDataVector shoppingItems, int storeId, ContractData pContract) {
        // group the shopping cart items according to distributor ID
        Hashtable distHash = new Hashtable();
        if (null != shoppingItems && shoppingItems.size() > 0) {
            for (int i = 0; i < shoppingItems.size(); i++) {
                ShoppingCartItemData item = (ShoppingCartItemData)shoppingItems.get(i);
                String key = null;
                if (null != item && null != item.getProduct()
                    && null != item.getProduct().getCatalogDistributor()) {

                    key = String.valueOf(item.getProduct().getCatalogDistributor().getBusEntityId());
                }
                if (null == key || key.trim().length() == 0) {
                    key = NO_DISTRIBUTOR_AVAILABLE;
                }
                ShoppingCartItemDataVector items = (ShoppingCartItemDataVector)distHash.get(key);
                if (null == items || items.size() == 0) {
                    items = new ShoppingCartItemDataVector();
                }
                items.add(item);
                distHash.put(key, items);
            }
        }

        if (! distHash.isEmpty()) {

            Set keys = distHash.keySet();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String)iterator.next();
                ShoppingCartItemDataVector items = (ShoppingCartItemDataVector)distHash.get(key);
                if (null != items && items.size() > 0) {
                    ShoppingCartDistData distCartD = new ShoppingCartDistData(items, storeId,pContract);

                    this.add(distCartD);
                }
            }
        }

        this.sort();
    }


    public  ShoppingCartDistDataVector(ShoppingCartServiceDataVector shoppingServices, int storeId,ContractData pContract) {
        // group the shopping cart items according to distributor ID
        Hashtable distHash = new Hashtable();
        if (null != shoppingServices && shoppingServices.size() > 0) {
            for (int i = 0; i < shoppingServices.size(); i++) {
                ShoppingCartServiceData service = (ShoppingCartServiceData)shoppingServices.get(i);
               // catalogDistributor=
                String key = null;
                if (null != service && null != service.getService()
                   && null != service.getService().getCatalogDistributor()) {

                    key =String.valueOf(service.getService().getCatalogDistributor().getBusEntityId());
                }
                if (null == key || key.trim().length() == 0) {
                    key = NO_DISTRIBUTOR_AVAILABLE;
                }
                ShoppingCartServiceDataVector services = (ShoppingCartServiceDataVector)distHash.get(key);
                if (null == services || services.size() == 0) {
                    services = new ShoppingCartServiceDataVector();
                }
                services.add(service);
                distHash.put(key, services);
            }
        }

        if (! distHash.isEmpty()) {

            Set keys = distHash.keySet();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String)iterator.next();
                ShoppingCartServiceDataVector services = (ShoppingCartServiceDataVector)distHash.get(key);
                if (null != services && services.size() > 0) {
                    ShoppingCartDistData distCartD = new ShoppingCartDistData(services, storeId,pContract);
                    this.add(distCartD);
                }
            }
        }

        this.sort();
    }

    /**
     *
     * @param shoppingItems
     * @param storeId
     * @param distFreightTables
     * @param distDiscountTables
     */
    public ShoppingCartDistDataVector(ShoppingCartItemDataVector shoppingItems, 
        int storeId, TreeMap<Integer, FreightTableData> distFreightTables,
        TreeMap<Integer, FreightTableData> distDiscountTables) {

        // group the shopping cart items according to distributor ID
        Hashtable distHash = new Hashtable();
        if (null != shoppingItems && shoppingItems.size() > 0) {
            for (int i = 0; i < shoppingItems.size(); i++) {
                ShoppingCartItemData item = (ShoppingCartItemData)shoppingItems.get(i);
                String key = null;
                if (null != item && null != item.getProduct()
                    && null != item.getProduct().getCatalogDistributor()) {
                    key = String.valueOf(item.getProduct().getCatalogDistributor().getBusEntityId());
                }
                if (null == key || key.trim().length() == 0) {
                    key = NO_DISTRIBUTOR_AVAILABLE;
                }
                ShoppingCartItemDataVector items = (ShoppingCartItemDataVector)distHash.get(key);
                if (null == items || items.size() == 0) {
                    items = new ShoppingCartItemDataVector();
                }
                items.add(item);
                distHash.put(key, items);
            }
        }

        if (! distHash.isEmpty()) {
            Set keys = distHash.keySet();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String)iterator.next();
                ShoppingCartItemDataVector items = (ShoppingCartItemDataVector)distHash.get(key);
                if (null != items && items.size() > 0) {
                    FreightTableData distFreightTable = null;
                    FreightTableData distDiscountTable = null;
                    if (!NO_DISTRIBUTOR_AVAILABLE.equals(key)) {
                        Integer distIdObj = null;
                        try {
                            distIdObj = Integer.valueOf(key);
                        } catch (Exception ex) {
                            distIdObj = null;
                        }
                        if (distIdObj != null) {
                            distFreightTable = distFreightTables.get(distIdObj);
                            distDiscountTable = distDiscountTables.get(distIdObj);
                        }
                    }
                    ShoppingCartDistData distCartD = new ShoppingCartDistData(items, 
                        storeId, distFreightTable, distDiscountTable);
                    this.add(distCartD);
                }
            }
        }

        this.sort();
    }

    public void setDistFreightVendor(String[] values) {
        int distNum = this.size();
        int valueNum = values.length;
        int minNum = distNum < valueNum ? distNum : valueNum;

        for(int i = 0; i < minNum; i++) {
            ShoppingCartDistData distD = (ShoppingCartDistData)this.get(i);
            distD.setFreightVendor(values[i]);
        }
    }


    public BigDecimal getTotalFreightCost() {
        mTotalFreightCost = new BigDecimal(0.0);
        for(int i = 0; i < size(); i++) {
            ShoppingCartDistData distD = (ShoppingCartDistData)this.get(i);
            mTotalFreightCost = mTotalFreightCost.add(new BigDecimal(distD.getDistFreightCost()));
        }
        return mTotalFreightCost;
    }

    // added by SVC
    public BigDecimal getTotalDiscountCost() {
        mTotalDiscountCost = new BigDecimal(0.0);
        for(int i = 0; i < size(); i++) {
        	log.info("***SVC_AAAAAAAAAAAAAAAAA: size = " + size());
            ShoppingCartDistData distD = (ShoppingCartDistData)this.get(i);
            
            //int distrId0 = distD.getDistId();
            //log.info("SVC_YYYYYYYY: distrId0 = " + distrId0);
            //int frTblId0 = distD.getFrTblId();
            //log.info("SVC_ZZZZZZZZ: frTblId0 = " + frTblId0);
            
            log.info("***SVC_BBBBBBBBBBBBBBBBB: distD.getDistImpliedDiscountCost() = " + distD.getDistImpliedDiscountCost());
            mTotalDiscountCost = mTotalDiscountCost.add(new BigDecimal(distD.getDistImpliedDiscountCost()));
            //log.info("***SVC_CCCCCCCCCCCCCCCCC:  mTotalDiscountCost = " + mTotalDiscountCost);            
        }
        return mTotalDiscountCost;
    }
    
    // get Discount cost per Distributor(all catalog items per one distributor)
    // and put it in the List
    public HashMap getDiscountCostPerDist() {
        mDiscountCostPerDist = new BigDecimal(0.0);
        //mDiscountCostPerDist = mDiscountCostPerDist.setScale(2, BigDecimal.ROUND_HALF_UP);
        mDiscCostForMultDist = new ArrayList();
        mDiscCostForMultDistHM = new HashMap<Integer,BigDecimal>();
        for(int i = 0; i < size(); i++) {
        	log.info("***SVC_DDDDDDDDDDDDDDDD: size = " + size());
            ShoppingCartDistData distD = (ShoppingCartDistData)this.get(i);
            
            //int distrId0 = distD.getDistId();
            //log.info("SVC_YYYYYYYY: distrId0 = " + distrId0);
            //int frTblId0 = distD.getFrTblId();
            //log.info("SVC_ZZZZZZZZ: frTblId0 = " + frTblId0);
            
            log.info("***SVC_EEEEEEEEEEEEEEE: distD.getDistImpliedDiscountCost() = " + distD.getDistImpliedDiscountCost());
            //mTotalDiscountCost = mTotalDiscountCost.add(new BigDecimal(distD.getDistImpliedDiscountCost()));
            mDiscountCostPerDist = new BigDecimal(distD.getDistImpliedDiscountCost());
            // if Discount is positive in the Discount table, convert it to negative: Discount ALWAYS MUST be negative and MUST be stored negative in the Database
            if ( mDiscountCostPerDist.compareTo(new BigDecimal(0))>0 ) {    	   
            	mDiscountCostPerDist = mDiscountCostPerDist.negate();
            }
            mDiscountCostPerDist = mDiscountCostPerDist.setScale(2, BigDecimal.ROUND_HALF_UP);
            int distrId0 = distD.getDistId();
            log.info("SVC_YYYYYYYY: distrId0 = " + distrId0);
            int frTblId0 = distD.getFrTblId();
            log.info("SVC_ZZZZZZZZ: frTblId0 = " + frTblId0);
            
            log.info("***SVC_FFFFFFFFFFFFFFF:  mDiscountCostPerDist = " + mDiscountCostPerDist);
            mDiscCostForMultDist.add(mDiscountCostPerDist);
            
            //Put the pairs distributor_id,mDiscountCostPerDist in the mDiscCostForMultDistHM
            
            //String hMkey = null;
            //hMkey = String.valueOf(distrId0);
            Integer distrIdKey = new Integer(distrId0);
            mDiscCostForMultDistHM.put(distrIdKey, mDiscountCostPerDist);
        }
        //return mDiscCostForMultDist;
        return mDiscCostForMultDistHM;
    }
        
    
    public boolean hasEstimatedFreight() {
        mHasEstimatedFreight = false;
        for(int i = 0; i < size(); i++) {
            ShoppingCartDistData distD = (ShoppingCartDistData)this.get(i);
            // only for those have been selected
            // if no selected, don't show message
            if ( distD != null
                && Utility.isSet(distD.getDistSelectableFreightVendorName())
                && Utility.isSet(distD.getDistSelectableFreightVendorName())
                && Utility.isSet(distD.getDistSelectableFreightMsg())
                && Utility.isSet(distD.getDistSelectableFreightMsg())) {
                mHasEstimatedFreight = true;
                break;
            }
        }

        return mHasEstimatedFreight;
    }

    public void sort() {
        Collections.sort(this, this);
    }

    /**
     * Compares 2 objects
     */
    public int compare(Object o1, Object o2) {
        int retcode = -1;
        ShoppingCartDistData obj1 = (ShoppingCartDistData)o1;
        ShoppingCartDistData obj2 = (ShoppingCartDistData)o2;

        String i1 = obj1.getDistributorName();
        String i2 = obj2.getDistributorName();
        if (null == i1 || i1.trim().length() == 0) {
            i1 = NO_DISTRIBUTOR_AVAILABLE;
        }
        if (null == i2 || i2.trim().length() == 0) {
            i2 = NO_DISTRIBUTOR_AVAILABLE;
        }

        retcode = i1.compareTo(i2);

        if (NO_DISTRIBUTOR_AVAILABLE.equals(i1))
            retcode = 1;

        if (NO_DISTRIBUTOR_AVAILABLE.equals(i2))
            retcode = -1;

        return retcode;
    }

    public void orderByCategory() {
        if ( this.size() == 0 ) return;
        for(int i = 0; i < this.size(); i++) {
            ((ShoppingCartDistData)this.get(i)).orderByCategory();
        }
    }
    public void orderBySku() {
        if ( this.size() == 0 ) return;
        for(int i = 0; i < this.size(); i++) {
            ((ShoppingCartDistData)this.get(i)).orderBySku();
        }
    }
    public void orderByName() {
        if ( this.size() == 0 ) return;
        for(int i = 0; i < this.size(); i++) {
            ((ShoppingCartDistData)this.get(i)).orderByName();
        }
    }

}

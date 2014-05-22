/*
 * ClwComparatorFactory.java
 *
 * Created on March 17, 2005, 9:53 AM
 */

package com.cleanwise.view.utils;

import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.GenericReportView;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.AccountSearchResultView;
import com.cleanwise.service.api.value.AllStoreData;
import java.util.Comparator;

/**
 *
 * @author bstevens
 */
public class ClwComparatorFactory {
    
    /** Non instansiable class */
    private ClwComparatorFactory() {
    }
    
    /**
     *Returns a compator suitable to compare the various different bus entity
     *heavyweight objects in mixed lists.  That is if you have BusEntityDataVector
     *and a StoreDataVector this compartor can tell you if they represent the same
     *underlying entity (uses the busEntityId)
     */
    public static Comparator getBusEntityComparator(){
        return BUS_ENTITY_ID_COMPARATOR;
    }
    
    /**
     *Returns a comparator that will sort a collection of GenericReportView objects
     *by category and then by name.
     */
    public static Comparator getReportCategoryNameComparator(){
        return REPORT_CATEGORY_NAME_COMPARATOR;
    }
    
    
    /**
     *Returns a comparator that will sort a collection of GenericReportView objects
     *by category and then by name.
     */
    public static Comparator getContactUsNameComparator(){
        return CONTACT_US_NAME_COMPARATOR;
    }
    
    /**
     *Returns a comparator that will sort a collection of OrderProperty objects
     *by order property id.
     */
    public static Comparator getOrderPropertyIdComparator(){
        return ORDER_PROPERTY_ID_COMPARATOR;
    }
    
    
    /**
     *Compares ContactUsInfo object based off their name properties
     */
    private static final Comparator CONTACT_US_NAME_COMPARATOR = new Comparator() {
        public int compare(Object o1, Object o2){
              ContactUsInfo c1 = (ContactUsInfo) o1;
              ContactUsInfo c2 = (ContactUsInfo) o2;
              String s1 = c1.getNickName();
              String s2 = c2.getNickName();
              if(s1 == null){
                  s1 = "";
              }
              if(s2 == null){
                  s2 = "";
              }
              int comp= s1.compareTo(s2);
              return comp;
        }
    };
    
    /**
     *Compares 2 GenericReportView objects and will sort them based off the category first and then the report name.
     */
    private static final Comparator REPORT_CATEGORY_NAME_COMPARATOR = new Comparator() {
        public int compare(Object o1, Object o2){
            GenericReportView grVw1 = (GenericReportView) o1;
            GenericReportView grVw2 = (GenericReportView) o2;
            String cat1 = grVw1.getReportCategory();
            String cat2 = grVw2.getReportCategory();
            if(cat1==null) cat1="";
            if(cat2==null) cat2="";
            int comp = cat1.compareTo(cat2);
            if(comp!=0) {
                return comp;
            }
            String name1 = grVw1.getReportName();
            String name2 = grVw2.getReportName();
            if(name1==null) name1="";
            if(name2==null) name2="";
            comp = name1.compareTo(name2);
            return comp;
        }
    };
    
    /**
     *compares two objects of the following types:
     *AccountData, SiteData, StoreData, BusEntityData for equality.
     *equality is decided based off the busEntityId of the object.
     */
    private static final Comparator BUS_ENTITY_ID_COMPARATOR = new Comparator() {
        private int getId(Object o){
            if(o instanceof AccountData){
                return ((AccountData) o).getBusEntity().getBusEntityId();
            }
            if(o instanceof DistributorData){
                return ((DistributorData) o).getBusEntity().getBusEntityId();
            }
            if(o instanceof SiteData){
                return ((SiteData) o).getBusEntity().getBusEntityId();
            }
            if(o instanceof StoreData){
                return ((StoreData) o).getBusEntity().getBusEntityId();
            }
            if(o instanceof ManufacturerData){
                return ((ManufacturerData) o).getBusEntity().getBusEntityId();
            }
            if(o instanceof BusEntityData){
                return ((BusEntityData) o).getBusEntityId();
            }
            if(o instanceof SiteView){
                return ((SiteView)o).getId();
            }
            if(o instanceof AccountSearchResultView){
            	return ((AccountSearchResultView)o).getAccountId();
            }
            if(o instanceof AllStoreData){
            	return ((AllStoreData)o).getStoreId();
            }
            String name = o.getClass().getName();
            throw new IllegalArgumentException("Comparator invoked with wrong type of BusEntity object: "+name);
        }
        
        public int compare(Object o1, Object o2){
            int id1 = getId(o1);
            int id2 = getId(o2);
            return id1 - id2;
        }
    };
    
    /**
     *Compares an order property data vector based off of its order property id (good approx of added date)
     */
    private static final Comparator ORDER_PROPERTY_ID_COMPARATOR = new Comparator() {
        public int compare(Object o1, Object o2){
              OrderPropertyData op1 = (OrderPropertyData) o1;
              OrderPropertyData op2 = (OrderPropertyData) o2;
              
              int comp= op1.getOrderPropertyId() - op2.getOrderPropertyId();
              return comp;
        }
    };
}

/*
 * ShoppingCartDistData.java
 *
 * Created on July 30, 2005, 11:21 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import java.util.*;
import java.math.*;

import org.apache.log4j.Category;

/**
 *
 * @author lli
 */
public class ShoppingCartDistData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = -7579915753577513182L;
  
  static final Category log = Category.getInstance(ShoppingCartDistData.class);
  
    private int mDistId;
    private int mStoreId;
    private String mDistName;
    private BusEntityData mDistributor;
    private ShoppingCartItemDataVector mShoppingCartItems;
    private ShoppingCartServiceDataVector mShoppingCartServices;
    private FreightTableCriteriaDataVector mFreightCriteriaV;
    private Map<Integer, FreightTableData> mFreightTableMap;
    private List mFreightOptions;
    private List mFreightImplied;
    private String mFreightVendor;
    private double mItemAmount = 0.0;
    private double mDistImpliedFreightCost = 0.0;
    private double mDistSelectableFreightCost = 0.0;
    private double mDistFreightCost = 0.0;
    private String mDistSelectableFreightMsg = new String("To Be Determined");
    private String mDistSelectableFreightVendorName = "";
    private FreightTableCriteriaData mDistSelectedFreight = null;
    private String mPoNumber = "";
    
    /*** Added by SVC *********************************************/
    private List mDiscountImplied;
    private Map<Integer, FreightTableData> mFreightTableDiscountMap;
    private FreightTableCriteriaDataVector mDiscountCriteriaV;
    private double mDistImpliedDiscountCost = 0.0;
    private double mDiscountAmt = 0.0;
    private double val = 0.0;
    private double _mDistImpliedDiscountCost = 0.0;
    private int mFrTblId;
    /**************************************************************/

    /// Calculation of 'Handling' amount
    private double mDistImpliedHandlingCost = 0.0;
    private double mDistSelectableHandlingCost = 0.0;

    /// Calculation of 'Fuel Surcharge' amount
    private FreightTableCriteriaDataVector mFuelSurchargeCriteriaV = null;
    private Map<Integer, FreightTableData> mFuelSurchargeTableMap = null;
    private List mFuelSurchargeList = null;
    private double mDistFuelSurchargeCost = 0.0;

    /// Calculation of 'Small Order Fee' amount
    private FreightTableCriteriaDataVector mSmallOrderFeeCriteriaV = null;
    private Map<Integer, FreightTableData> mSmallOrderFeeTableMap = null;
    private List mSmallOrderFeeList = null;
    private double mDistSmallOrderFeeCost = 0.0;

    private int mOrderBy = Constants.ORDER_BY_CATEGORY;


    /** Creates a new instance of ShoppingCartDistData */
    public ShoppingCartDistData(ShoppingCartItemDataVector shoppingCartItems, ContractData contract) {
        /*
        if (null != shoppingCartItems && shoppingCartItems.size() > 0) {
            ShoppingCartItemData firstItem = (ShoppingCartItemData)shoppingCartItems.get(0);
            mDistributor = firstItem.getProduct().getCatalogDistributor();
            mDistName = firstItem.getProduct().getCatalogDistributorName();
            mDistId = mDistributor.getBusEntityId();
            String runtimeDisplayName;

            try {
                APIAccess factory = new APIAccess();
                Distributor distEjb = factory.getDistributorAPI();
                runtimeDisplayName = distEjb.getDistRuntimeDisplayName(mDistributor.getBusEntityId());
            }
            catch(Exception e) {
                runtimeDisplayName = null;
            }

            if (null != runtimeDisplayName && runtimeDisplayName.trim().length() >0 ) {
                mDistName = runtimeDisplayName;
            }

            mShoppingCartItems = shoppingCartItems;

            orderByCategory();
        }
         */
        this(shoppingCartItems, 0, contract);
    }


    public ShoppingCartDistData(ShoppingCartItemDataVector shoppingCartItems, int storeId, ContractData contract) {
        if (null != shoppingCartItems && shoppingCartItems.size() > 0) {
            ShoppingCartItemData firstItem = (ShoppingCartItemData)shoppingCartItems.get(0);
            mDistributor = firstItem.getProduct().getCatalogDistributor();
            mDistName = firstItem.getProduct().getCatalogDistributorName();
            if(mDistributor != null){
                mDistId = mDistributor.getBusEntityId();
            }else{
                mDistId = 0;
            }
            String runtimeDisplayName;

            try {
                APIAccess factory = new APIAccess();
                Distributor distEjb = factory.getDistributorAPI();
                runtimeDisplayName = distEjb.getDistRuntimeDisplayName(mDistributor.getBusEntityId());
            }
            catch(Exception e) {
                runtimeDisplayName = null;
            }

            if (null != runtimeDisplayName && runtimeDisplayName.trim().length() >0 ) {
                mDistName = runtimeDisplayName;
            }

            mShoppingCartItems = shoppingCartItems;

            orderByCategory();

            mStoreId = storeId;
            if(0 != storeId) {
                try {
                    if(mDistId > 0){
                        APIAccess factory = new APIAccess();
                        FreightTable freightEjb = factory.getFreightTableAPI();
                        int ftid = 0; //freight table id
                        int dtid = 0; //discount table id
                        if(contract != null){
                        	ftid = contract.getFreightTableId();
                        	dtid = contract.getDiscountTableId();
                        }
                        mFreightCriteriaV = freightEjb.getStoreDistributorFreightTableCriteria(mStoreId, mDistId, ftid);
                        
                        initFreightTableMap(mFreightCriteriaV, freightEjb.getAllFreightTables());
                        
                        /**************   Added by SVC for Discount  ************************************************/
                        mDiscountCriteriaV = freightEjb.getStoreDistributorFreightTableDiscountCriteria(mStoreId, mDistId, dtid);
                        
                        initFreightTableDiscountMap(mDiscountCriteriaV, freightEjb.getAllDiscountTables());
                        
                        setDistId(mDistId);
                        
                        
                        /**********************************************************************************/
                    }
                }
                catch(Exception e) {
                    mFreightCriteriaV = null;
                    mDiscountCriteriaV = null;
                }

            }
            setItemAmount(mShoppingCartItems);
        }
    }

    public ShoppingCartDistData(ShoppingCartServiceDataVector services, int storeId, ContractData contract) {
        if (null != services && services.size() > 0) {
            ShoppingCartServiceData firstItem = (ShoppingCartServiceData) services.get(0);
            mDistributor = firstItem.getService().getCatalogDistributor();

            if (mDistributor != null) {
                mDistId = mDistributor.getBusEntityId();
                 mDistName = firstItem.getService().getCatalogDistributor().getShortDesc();
            } else {
                mDistId = 0;
                mDistName ="";
            }
            String runtimeDisplayName=null;
            if( mDistId>0)
            try {
                APIAccess factory = new APIAccess();
                Distributor distEjb = factory.getDistributorAPI();
                runtimeDisplayName = distEjb.getDistRuntimeDisplayName(mDistId);
            }
            catch (Exception e) {
                runtimeDisplayName = null;
            }

            if (null != runtimeDisplayName && runtimeDisplayName.trim().length() > 0) {
                mDistName = runtimeDisplayName;
            }

            mShoppingCartServices = services;
            mStoreId = storeId;
            if (0 != storeId) {
                try {
                    if (mDistId > 0) {
                        APIAccess factory = new APIAccess();
                        FreightTable freightEjb = factory.getFreightTableAPI();
                        int ftid = 0;
                        if(contract != null){
                        	ftid = contract.getFreightTableId();
                        }
                        mFreightCriteriaV = freightEjb.getStoreDistributorFreightTableCriteria(mStoreId, mDistId,ftid);
                        
                        initFreightTableMap(mFreightCriteriaV, freightEjb.getAllFreightTables());
                        
                        /*****************   Added by SVC   ***********************************************/
                        mDiscountCriteriaV = freightEjb.getStoreDistributorFreightTableDiscountCriteria(mStoreId, mDistId, ftid);
                        initFreightTableDiscountMap(mDiscountCriteriaV, freightEjb.getAllDiscountTables());
                        /**********************************************************************************/
                    }
                }
                catch (Exception e) {
                    mFreightCriteriaV = null;
                    //mDiscountCriteriaV = null;
                }
         }

        }
    }

    public ShoppingCartDistData(ShoppingCartItemDataVector shoppingCartItems, 
        int storeId, FreightTableData freightTable, FreightTableData discountTable) {

        if (null != shoppingCartItems && shoppingCartItems.size() > 0) {
            ShoppingCartItemData firstItem = (ShoppingCartItemData)shoppingCartItems.get(0);
            mDistributor = firstItem.getProduct().getCatalogDistributor();
            mDistName = firstItem.getProduct().getCatalogDistributorName();
            if (mDistributor != null){
                mDistId = mDistributor.getBusEntityId();
            } else {
                mDistId = 0;
            }
            String runtimeDisplayName;

            try {
                APIAccess factory = new APIAccess();
                Distributor distEjb = factory.getDistributorAPI();
                runtimeDisplayName = distEjb.getDistRuntimeDisplayName(mDistributor.getBusEntityId());
            }
            catch (Exception ex) {
                runtimeDisplayName = null;
            }

            if (null != runtimeDisplayName && runtimeDisplayName.trim().length() >0 ) {
                mDistName = runtimeDisplayName;
            }

            mShoppingCartItems = shoppingCartItems;

            orderByCategory();

            mStoreId = storeId;
            if (0 != storeId) {
                try {
                    if (mDistId > 0){
                        APIAccess factory = new APIAccess();
                        FreightTable freightEjb = factory.getFreightTableAPI();
                        //freight table id
                        int freightTableId = 0;
                        //discount table id
                        int discountTableId = 0;
                        if (freightTable != null) {
                            freightTableId = freightTable.getFreightTableId();
                        }
                        if (discountTable != null) {
                            discountTableId = discountTable.getFreightTableId();
                        }
                        if (freightTableId > 0 || discountTableId > 0) {
                            /// To get all freight tables
                            FreightTableDataVector allFreightTables = freightEjb.getAllFreightTables();

                            ///
                            mFreightCriteriaV = freightEjb.getFreightTableCriteriasByChargeCd(freightTableId, null);
                            initFreightTableMap(mFreightCriteriaV, allFreightTables);

                            ///
                            mFuelSurchargeCriteriaV = freightEjb.getFreightTableCriteriasByChargeCd(freightTableId, RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);
                            initFuelSurchargeTableMap(mFuelSurchargeCriteriaV, allFreightTables);

                            ///
                            mSmallOrderFeeCriteriaV = freightEjb.getFreightTableCriteriasByChargeCd(freightTableId, RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);
                            initSmallOrderFeeTableMap(mSmallOrderFeeCriteriaV, allFreightTables);

                            ///
                            mDiscountCriteriaV = freightEjb.getFreightTableCriteriasByChargeCd(discountTableId, RefCodeNames.CHARGE_CD.DISCOUNT);
                            initFreightTableDiscountMap(mDiscountCriteriaV, freightEjb.getAllDiscountTables());
                        }
                        ///
                        setDistId(mDistId);
                    }
                }
                catch (Exception ex) {
                    mFreightCriteriaV = null;
                    mFuelSurchargeCriteriaV = null;
                    mSmallOrderFeeCriteriaV = null;
                    mDiscountCriteriaV = null;
                }
            }
            setItemAmount(mShoppingCartItems);
        }
    }

    protected void setItemAmount(List shoppingItems) {
        double amount = 0.0;
        if (null != shoppingItems && 0 < shoppingItems.size()) {
            for (int i  = 0; i < shoppingItems.size(); i++) {
                ShoppingCartItemData item = (ShoppingCartItemData)shoppingItems.get(i);
                amount += item.getAmount();
            }
        }
        mItemAmount = amount;
    }

    public String getDistributorName() {
        return mDistName;
    }

    public BusEntityData getDistributor() {
        return mDistributor;
    }

    public ShoppingCartItemDataVector getShoppingCartItems() {
        return mShoppingCartItems;
    }

    public boolean isCategoryChanged(int ind) {
        if(ind>=mShoppingCartItems.size())
            return false;
        if(ind==0)
            return true;
        ShoppingCartItemData item1 = (ShoppingCartItemData) mShoppingCartItems.get(ind-1);
        ShoppingCartItemData item2 = (ShoppingCartItemData) mShoppingCartItems.get(ind);
        if(!item1.getCategoryName().equals(item2.getCategoryName()))
            return true;
        return false;
    }

    public boolean isServiceCategoryChanged(int ind) {
        if(ind>=mShoppingCartServices.size())
            return false;
        if(ind==0)
            return true;
        ShoppingCartServiceData item1 = (ShoppingCartServiceData) mShoppingCartServices.get(ind-1);
        ShoppingCartServiceData item2 = (ShoppingCartServiceData) mShoppingCartServices.get(ind);
        if(!item1.getCategoryName().equals(item2.getCategoryName()))
            return true;
        return false;
    }

    public List getDistFreightOptions() {
        if (null == mFreightOptions || mFreightOptions.size() == 0) {
            mFreightOptions = new LinkedList();
            if(null != mFreightCriteriaV && mFreightCriteriaV.size() > 0) {
                for(int i = 0; i < mFreightCriteriaV.size(); i++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData)mFreightCriteriaV.get(i);
                    if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.SELECTABLE.equalsIgnoreCase(crit.getRuntimeTypeCd())) {
                        String tableType = mFreightTableMap.get(crit.getFreightTableId()).getFreightTableTypeCd();
                        double compareValue = mItemAmount;
                        if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.POUND
                                .equals(tableType)
                                || RefCodeNames.FREIGHT_TABLE_TYPE_CD.KILOGRAMME
                                        .equals(tableType)) {
                            compareValue = getShoppingCartWeight(mShoppingCartItems, tableType).doubleValue();
                        }
                        BigDecimal lowerAmt = crit.getLowerAmount();
                        BigDecimal higherAmt = crit.getHigherAmount();
                        if(null == lowerAmt || compareValue >= lowerAmt.doubleValue()) {
                            if(null == higherAmt || compareValue <= higherAmt.doubleValue()) {
                                mFreightOptions.add(crit);
                            }
                        }
                    }
                }
            }
        }
        
        return mFreightOptions;
    }


    public List getDistFreightImplied() {
        if (null == mFreightImplied || mFreightImplied.size() == 0) {
            mFreightImplied = new LinkedList();
            if(null != mFreightCriteriaV && mFreightCriteriaV.size() > 0) {
                for(int i = 0; i < mFreightCriteriaV.size(); i++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData)mFreightCriteriaV.get(i);
                    if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.IMPLIED.equalsIgnoreCase(crit.getRuntimeTypeCd())) {
                        String tableType = mFreightTableMap.get(crit.getFreightTableId()).getFreightTableTypeCd();
                        double compareValue = mItemAmount;
                        
                        if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.POUND
                                .equals(tableType)
                                || RefCodeNames.FREIGHT_TABLE_TYPE_CD.KILOGRAMME
                                        .equals(tableType)) {
                            compareValue = getShoppingCartWeight(mShoppingCartItems, tableType).doubleValue();
                        }
                        
                        BigDecimal lowerAmt = crit.getLowerAmount();
                        BigDecimal higherAmt = crit.getHigherAmount();
                        if(null == lowerAmt || compareValue >= lowerAmt.doubleValue()) {
                            if(null == higherAmt || compareValue <= higherAmt.doubleValue()) {
                                mFreightImplied.add(crit);
                            }
                        }
                    }
                }
            }
        }
        
        return mFreightImplied;

    }


    public List getDistDiscountImplied() {
        if (null == mDiscountImplied || mDiscountImplied.size() == 0) {
            mDiscountImplied = new LinkedList();
            if(null != mDiscountCriteriaV && mDiscountCriteriaV.size() > 0) {
                for(int i = 0; i < mDiscountCriteriaV.size(); i++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData)mDiscountCriteriaV.get(i);
                    if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.IMPLIED.equalsIgnoreCase(crit.getRuntimeTypeCd())) {
                        String tableType = mFreightTableDiscountMap.get(crit.getFreightTableId()).getFreightTableTypeCd();
                        int frTblId = mFreightTableDiscountMap.get(crit.getFreightTableId()).getFreightTableId();
                        if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS
                                .equals(tableType)
                                || RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE
                                        .equals(tableType)) {
                           double compareValue = mItemAmount;
                        
                           BigDecimal lowerAmt = crit.getLowerAmount();
                           BigDecimal higherAmt = crit.getHigherAmount();
                           if(null == lowerAmt || compareValue >= lowerAmt.doubleValue()) {
                               if(null == higherAmt || compareValue <= higherAmt.doubleValue()) {
                                   mDiscountImplied.add(crit);
                                   
                                   setFrTblId(frTblId);
                                   
                               } //if(null == higherAmt 
                           } //if(null == lowerAmt
                       } //if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS
                   } //if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE
               } //for(int i = 0
           } //if(null != mDiscountCriteriaV 
       } //if (null == mDiscountImplied
       return mDiscountImplied;

    } //public List

    public double getDistFreightCost() {
        return getDistImpliedFreightCost() + getDistSelectableFreightCost();
    }

    public double getDistHandlingCost() {
        return getDistImpliedHandlingCost() + getDistSelectableHandlingCost();
    }

    public double getDistImpliedFreightCost() {
        mDistImpliedFreightCost = 0.0;
        if(null != mFreightCriteriaV && mFreightCriteriaV.size() > 0) {
            for(int i = 0; i < mFreightCriteriaV.size(); i++) {
                FreightTableCriteriaData crit = (FreightTableCriteriaData)mFreightCriteriaV.get(i);
                if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.IMPLIED.equalsIgnoreCase(crit.getRuntimeTypeCd())) {
                    String tableType = mFreightTableMap.get(crit.getFreightTableId()).getFreightTableTypeCd();
                    double compareValue = mItemAmount;
                    if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.POUND
                            .equals(tableType)
                            || RefCodeNames.FREIGHT_TABLE_TYPE_CD.KILOGRAMME
                                    .equals(tableType)) {
                        compareValue = getShoppingCartWeight(mShoppingCartItems, tableType).doubleValue();
                    }
                    BigDecimal lowerAmt = crit.getLowerAmount();
                    BigDecimal higherAmt = crit.getHigherAmount();
                    if(null == lowerAmt || compareValue >= lowerAmt.doubleValue()) {
                        if(null == higherAmt || compareValue <= higherAmt.doubleValue()) {
                            // set implied freight cost
                            if (null != crit.getFreightAmount() && ! RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equalsIgnoreCase(crit.getFreightCriteriaTypeCd())) {
                                mDistImpliedFreightCost += crit.getFreightAmount().doubleValue();
                            }
                        }
                    }
                }
            }
        }
        return mDistImpliedFreightCost;
    }

    public double getDistImpliedHandlingCost() {
        mDistImpliedHandlingCost = 0.0;
        if(null != mFreightCriteriaV && mFreightCriteriaV.size() > 0) {
            for(int i = 0; i < mFreightCriteriaV.size(); i++) {
                FreightTableCriteriaData crit = (FreightTableCriteriaData)mFreightCriteriaV.get(i);
                if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.IMPLIED.equalsIgnoreCase(crit.getRuntimeTypeCd())) {
                    String tableType = mFreightTableMap.get(crit.getFreightTableId()).getFreightTableTypeCd();
                    double compareValue = mItemAmount;
                    if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.POUND
                            .equals(tableType)
                            || RefCodeNames.FREIGHT_TABLE_TYPE_CD.KILOGRAMME
                                    .equals(tableType)) {
                        compareValue = getShoppingCartWeight(mShoppingCartItems, tableType).doubleValue();
                    }
                    BigDecimal lowerAmt = crit.getLowerAmount();
                    BigDecimal higherAmt = crit.getHigherAmount();
                    if(null == lowerAmt || compareValue >= lowerAmt.doubleValue()) {
                        if(null == higherAmt || compareValue <= higherAmt.doubleValue()) {
                            // set implied handling cost
                            if (null != crit.getHandlingAmount() && ! RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equalsIgnoreCase(crit.getFreightCriteriaTypeCd())) {
                                mDistImpliedHandlingCost += crit.getHandlingAmount().doubleValue();
                            }
                        }
                    }
                }
            }
        }
        return mDistImpliedHandlingCost;
    }

    //SVC: new method for Discount calculation
    public double getDistImpliedDiscountCost() {
        mDistImpliedDiscountCost = 0.0;
        val = 0.0;
        if(null != mDiscountCriteriaV && mDiscountCriteriaV.size() > 0) {
            for(int i = 0; i < mDiscountCriteriaV.size(); i++) {
                FreightTableCriteriaData crit = (FreightTableCriteriaData)mDiscountCriteriaV.get(i);
                if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.IMPLIED.equalsIgnoreCase(crit.getRuntimeTypeCd())) {
                    String tableType = mFreightTableDiscountMap.get(crit.getFreightTableId()).getFreightTableTypeCd();
                    double compareValue = mItemAmount;
                    if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS
                            .equals(tableType)
                            || RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE
                                    .equals(tableType)) {
                       compareValue = mItemAmount;                    
 
                       BigDecimal lowerAmt = crit.getLowerAmount();
                       BigDecimal higherAmt = crit.getHigherAmount();
                       if(null == lowerAmt || compareValue >= lowerAmt.doubleValue()) {
                          if(null == higherAmt || compareValue <= higherAmt.doubleValue()) {
                            // set implied Discount cost
                            if (null != crit.getDiscount() && ! RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equalsIgnoreCase(crit.getFreightCriteriaTypeCd())) {
                            	val = crit.getDiscount().doubleValue();
                            	if(tableType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS)) {
                            	   mDiscountAmt = val; 
                                } else { //PERCENTAGE-DOLLARS; calculate value in percentage
                                	//val = val.movePointLeft(2);
                                	val = val/100.00;
                                    //discountAmt = pAmount.multiply(val); 
                                	mDiscountAmt = compareValue*val;
                                } // endif
                            	//log.info("***************SVC(getDistImpliedDiscountCost): crit.getDiscount().doubleValue() = " + crit.getDiscount().doubleValue());  
                            	//mDistImpliedDiscountCost += crit.getDiscount().doubleValue();
                            	mDistImpliedDiscountCost += mDiscountAmt;
                            	//mDistImpliedDiscountCost = mDistImpliedDiscountCost.add(new BigDecimal(crit.getDiscount().doubleValue()));
                            }
                          }
                       }
                   }
                }
            }
        }
        // If Discount is positive, convert it to negative: discount MUST be negative everywhere
        if ( mDistImpliedDiscountCost > 0.0 ) {    	   
        	mDistImpliedDiscountCost = -mDistImpliedDiscountCost;
        }
        storeImpliedDiscountCostPerDist(mDistImpliedDiscountCost);
        return mDistImpliedDiscountCost;
    }

    public FreightTableCriteriaData getDistSelectedFreight() {
        mDistSelectedFreight = null;
        if (null != mFreightVendor && mFreightVendor.trim().length() > 0) {
            if (null != mFreightOptions && 0 < mFreightOptions.size()) {
                for(int i = 0; i < mFreightOptions.size(); i++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData)mFreightOptions.get(i);
                    if(mFreightVendor.equalsIgnoreCase(String.valueOf(crit.getFreightTableCriteriaId()))) {
                         mDistSelectedFreight = crit;
                         break;
                    }
                }

            }
        }
        return mDistSelectedFreight;
    }

    public double getDistSelectableFreightCost() {
        mDistSelectableFreightCost = 0.0;
        if (null != mFreightVendor && mFreightVendor.trim().length() > 0) {
            if (null != mFreightOptions && 0 < mFreightOptions.size()) {
                for(int i = 0; i < mFreightOptions.size(); i++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData)mFreightOptions.get(i);
                    if(mFreightVendor.equalsIgnoreCase(String.valueOf(crit.getFreightTableCriteriaId()))) {
                        if (null != crit.getFreightAmount() && ! RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equalsIgnoreCase(crit.getFreightCriteriaTypeCd())) {
                            mDistSelectableFreightCost = crit.getFreightAmount().doubleValue();
                            break;
                        }
                    }
                }

            }
        }
        return mDistSelectableFreightCost;
    }

    public double getDistSelectableHandlingCost() {
        mDistSelectableHandlingCost = 0.0;
        if (null != mFreightVendor && mFreightVendor.trim().length() > 0) {
            if (null != mFreightOptions && 0 < mFreightOptions.size()) {
                for(int i = 0; i < mFreightOptions.size(); i++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData)mFreightOptions.get(i);
                    if(mFreightVendor.equalsIgnoreCase(String.valueOf(crit.getFreightTableCriteriaId()))) {
                        if (null != crit.getHandlingAmount() && ! RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equalsIgnoreCase(crit.getFreightCriteriaTypeCd())) {
                        	mDistSelectableHandlingCost = crit.getHandlingAmount().doubleValue();
                            break;
                        }
                    }
                }

            }
        }
        return mDistSelectableHandlingCost;
    }

    public String getDistSelectableFreightVendorName() {
        mDistSelectableFreightVendorName = "";
        if (null != mFreightVendor && mFreightVendor.trim().length() > 0) {
            if (null != mFreightOptions && 0 < mFreightOptions.size()) {
                for(int i = 0; i < mFreightOptions.size(); i++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData)mFreightOptions.get(i);
                    if(mFreightVendor.equalsIgnoreCase(String.valueOf(crit.getFreightTableCriteriaId()))) {
                        mDistSelectableFreightVendorName = crit.getShortDesc();
                        break;
                    }
                }

            }
        }
        return mDistSelectableFreightVendorName;
    }

    public String getDistSelectableFreightMsg() {
        mDistSelectableFreightMsg = "To Be Determined";
        if (null != mFreightVendor && mFreightVendor.trim().length() > 0) {
            if (null != mFreightOptions && 0 < mFreightOptions.size()) {
                for(int i = 0; i < mFreightOptions.size(); i++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData)mFreightOptions.get(i);
                    if(mFreightVendor.equalsIgnoreCase(String.valueOf(crit.getFreightTableCriteriaId()))) {
                        if (RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equalsIgnoreCase(crit.getFreightCriteriaTypeCd())) {
                            mDistSelectableFreightMsg = "To Be Determined";
                            break;
                        }
                        else {
                            mDistSelectableFreightMsg = "";
                            break;
                        }
                    }
                }

            }
        }
        return mDistSelectableFreightMsg;
    }


    public void setFreightVendor(String value) {
        mFreightVendor = value;
    }

    public String getFreightVendor() {
        return mFreightVendor;
    }


    // Shopping cart sort functions.
    static final Comparator SCART_CATCOMP = new Comparator() {
        public int compare(Object o1, Object o2) {
            String name1 = ((ShoppingCartItemData)o1).getCategoryName()
                + " " +
                ((ShoppingCartItemData)o1).getActualSkuNum();
            String name2 = ((ShoppingCartItemData)o2).getCategoryName()
                + " " +
                ((ShoppingCartItemData)o2).getActualSkuNum();
            return name1.compareTo(name2);
        }
    };

    static final Comparator SCART_NAMECOMP = new Comparator() {
        public int compare(Object o1, Object o2) {
            String name1 = ((ShoppingCartItemData)o1).getProduct().
                getCatalogProductShortDesc();
            String name2 = ((ShoppingCartItemData)o2).getProduct().
                getCatalogProductShortDesc();
            return name1.compareTo(name2);
        }
    };

    static final Comparator SCART_SKUCOMP = new Comparator() {
        public int compare(Object o1, Object o2) {
            String name1 = ((ShoppingCartItemData)o1).getActualSkuNum();
            String name2 = ((ShoppingCartItemData)o2).getActualSkuNum();
            return name1.compareTo(name2);
        }
    };

    public void orderByCategory() {
        ShoppingCartData.orderByCategory(mShoppingCartItems);
    }

    public void orderBySku() {
        if ( mShoppingCartItems == null ) return;
        Collections.sort(mShoppingCartItems, SCART_SKUCOMP);
    }
    public void orderByName() {
        if ( mShoppingCartItems == null ) return;
        Collections.sort(mShoppingCartItems, SCART_NAMECOMP);
    }

    public static void orderByCategory(List l) {
         ShoppingCartData.orderByCategory(l);
    }

    public static void orderBySku(List l) {
        if ( l == null ) return;
        Collections.sort(l, SCART_SKUCOMP);
    }
    public static void orderByName(List l) {
        if ( l == null ) return;
        Collections.sort(l, SCART_NAMECOMP);
    }

    /**
     * Holds value of property salesTax.
     */
    private BigDecimal salesTax;

    /**
     * Getter for property salesTax.
     * @return Value of property salesTax.
     */
    public BigDecimal getSalesTax() {

        return this.salesTax;
    }

    /**
     * Setter for property salesTax.
     * @param salesTax New value of property salesTax.
     */
    public void setSalesTax(BigDecimal salesTax) {

        this.salesTax = salesTax;
    }



    /**
     *Returns the total cost of the shopping cart items (i.e. subtotal).
     */
    public double getItemsCost(){
        return getShoppingCartItems().getItemsCost();
    }

    /**
     *Returns the total cost of the shopping cart items (i.e. subtotal).
     */
    public double getItemsCostNonResale(){
        return getShoppingCartItems().getItemsCostNonResale();
    }

    /**
     *Returns true if all of the items are resale items
     */
    public boolean isAllResaleItems(){
        return getShoppingCartItems().isAllResaleItems();
    }

    public ShoppingCartServiceDataVector getShoppingCartServices() {
        return mShoppingCartServices;
    }

    public void setShoppingCartServices(ShoppingCartServiceDataVector shoppingCartServices) {
        this.mShoppingCartServices = shoppingCartServices;
    }

    public void setPoNumber(String pValue) {
        mPoNumber = pValue;
    }
    public String getPoNumber() {
        return mPoNumber;
    }


    private BigDecimal getShoppingCartWeight(ShoppingCartItemDataVector cartItems,
            String pWeightUnit) {
        BigDecimal result = new BigDecimal(0);
        for (int ii = 0; cartItems != null && ii < cartItems.size(); ii++) {
            ShoppingCartItemData item = (ShoppingCartItemData) cartItems
                    .get(ii);
            int qty = item.getQuantity();
            if (qty > 0) {
                String weightS = item.getProduct().getShipWeight();
                weightS = (Utility.isSet(weightS)) ? weightS : "0";
                try {
                    BigDecimal weight = new BigDecimal(weightS);
                    weight = weight.multiply(new BigDecimal(qty));
                    result = result.add(Utility.getWeight(weight, item
                            .getProduct().getWeightUnit(), pWeightUnit));
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        }
        return result;
    }

    private void initFreightTableMap(FreightTableCriteriaDataVector pItems, FreightTableDataVector pTables) {
        mFreightTableMap = new HashMap<Integer, FreightTableData>();
        for (int i = 0; pItems != null && i < pItems.size(); i++) {
            FreightTableCriteriaData item = (FreightTableCriteriaData) pItems.get(i);
            if (mFreightTableMap.containsKey(item.getFreightTableId()) == false) {
                for (int ii = 0; pTables != null && ii < pTables.size(); ii++) {
                    FreightTableData item2 = (FreightTableData) pTables.get(ii);
                    if (item2.getFreightTableId() == item.getFreightTableId()) {
                        mFreightTableMap.put(item2.getFreightTableId(), item2);
                        break;
                    }
                }
            }
        }
    }

    private void initFuelSurchargeTableMap(FreightTableCriteriaDataVector pItems, FreightTableDataVector pTables) {
        if (pItems == null || pTables == null) {
            return;
        }
        mFuelSurchargeTableMap = new HashMap<Integer, FreightTableData>();
        for (int i = 0; pItems != null && i < pItems.size(); i++) {
            FreightTableCriteriaData item = (FreightTableCriteriaData) pItems.get(i);
            if (mFuelSurchargeTableMap.containsKey(item.getFreightTableId()) == false) {
                for (int ii = 0; pTables != null && ii < pTables.size(); ii++) {
                    FreightTableData item2 = (FreightTableData) pTables.get(ii);
                    if (item2.getFreightTableId() == item.getFreightTableId()) {
                        mFuelSurchargeTableMap.put(item2.getFreightTableId(), item2);
                        break;
                    }
                }
            }
        }
    }

    private void initSmallOrderFeeTableMap(FreightTableCriteriaDataVector pItems, FreightTableDataVector pTables) {
        if (pItems == null || pTables == null) {
            return;
        }
        mSmallOrderFeeTableMap = new HashMap<Integer, FreightTableData>();
        for (int i = 0; pItems != null && i < pItems.size(); i++) {
            FreightTableCriteriaData item = (FreightTableCriteriaData) pItems.get(i);
            if (mSmallOrderFeeTableMap.containsKey(item.getFreightTableId()) == false) {
                for (int ii = 0; pTables != null && ii < pTables.size(); ii++) {
                    FreightTableData item2 = (FreightTableData) pTables.get(ii);
                    if (item2.getFreightTableId() == item.getFreightTableId()) {
                        mSmallOrderFeeTableMap.put(item2.getFreightTableId(), item2);
                        break;
                    }
                }
            }
        }
    }

    // Added by SVC
    private void initFreightTableDiscountMap(FreightTableCriteriaDataVector pItems, FreightTableDataVector pTables) {
        mFreightTableDiscountMap = new HashMap<Integer, FreightTableData>();
        for (int i = 0; pItems != null && i < pItems.size(); i++) {
            FreightTableCriteriaData item = (FreightTableCriteriaData) pItems.get(i);
            if (mFreightTableDiscountMap.containsKey(item.getFreightTableId()) == false) {
                for (int ii = 0; pTables != null && ii < pTables.size(); ii++) {
                    FreightTableData item2 = (FreightTableData) pTables.get(ii);
                    if (item2.getFreightTableId() == item.getFreightTableId()) {
                        mFreightTableDiscountMap.put(item2.getFreightTableId(), item2);
                        break;
                    }
                }
            }
        }
        
    }
    
    // Added by SVC
    public void storeImpliedDiscountCostPerDist(double pDistImpliedDiscountCost) {
    	mDistImpliedDiscountCost = pDistImpliedDiscountCost;
    }
    
    // Added by SVC
    public double getImpliedDiscountCostPerDist() {
    	return mDistImpliedDiscountCost;
    }
    
    // Added by SVC    
    public int getDistId(){
    	return mDistId;
    }
    
    // Added by SVC
    public void setDistId(int pValue){
    	mDistId= pValue;
    	//this.mDistId = pValue;
    	//log.info("ZZZZZZZZZZZZZZZZZ_1: mDistId = " + this.mDistId);
    }
    
    // Added by SVC    
    public int getFrTblId(){
    	return mFrTblId;
    }
    
    // Added by SVC
    public void setFrTblId(int pValue) {
    	mFrTblId = pValue;
    	//this.mFrTblId = pValue; 
    	//log.info("ZZZZZZZZZZZZZZZZZ_3: mFrTblId = " + this.mFrTblId);  	
    }
    
    // Added by SVC
    public int getDistId( int frTblId ){
    	FreightTableData ftData;
        try {
            APIAccess factory = new APIAccess();
            FreightTable freightEjb = factory.getFreightTableAPI();        
    	    int distId;
    	    ftData = freightEjb.getFreightTableByFreightId(frTblId);
    	    if ( ftData != null ) {
    		   distId = ftData.getDistributorId();
    		   return distId;
    	    } else {
    		   return 0;
    	    }
        } catch (Exception e) {
           ftData = null; 
           return 0;
        }
    	
    }

    public List getDistFuelSurchargeList() {
        if (null == mFuelSurchargeList || mFuelSurchargeList.size() == 0) {
            mFuelSurchargeList = new LinkedList();
            if (null != mFuelSurchargeCriteriaV && mFuelSurchargeCriteriaV.size() > 0) {
                for (int i = 0; i < mFuelSurchargeCriteriaV.size(); i++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData)mFuelSurchargeCriteriaV.get(i);
                    if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.IMPLIED.equalsIgnoreCase(crit.getRuntimeTypeCd())) {
                        String tableType = mFuelSurchargeTableMap.get(crit.getFreightTableId()).getFreightTableTypeCd();
                        double compareValue = mItemAmount;
                        if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.POUND.equals(tableType) ||
                            RefCodeNames.FREIGHT_TABLE_TYPE_CD.KILOGRAMME.equals(tableType)) {
                            compareValue = getShoppingCartWeight(mShoppingCartItems, tableType).doubleValue();
                        }
                        BigDecimal lowerAmt = crit.getLowerAmount();
                        BigDecimal higherAmt = crit.getHigherAmount();
                        if (null == lowerAmt || compareValue >= lowerAmt.doubleValue()) {
                            if (null == higherAmt || compareValue <= higherAmt.doubleValue()) {
                                mFuelSurchargeList.add(crit);
                            }
                        }
                    }
                }
            }
        }
        return mFuelSurchargeList;
    }

    public double getDistFuelSurchargeCost() {
        mDistFuelSurchargeCost = 0.0;
        List criterias = getDistFuelSurchargeList();
        if (criterias != null) {
            for (int i = 0; i < criterias.size(); ++i) {
                FreightTableCriteriaData criteria = (FreightTableCriteriaData)criterias.get(i);
                if (criteria != null) {
                    if (null != criteria.getHandlingAmount() &&
                        !RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equalsIgnoreCase(criteria.getFreightCriteriaTypeCd())) {
                        mDistFuelSurchargeCost += criteria.getHandlingAmount().doubleValue();
                    }
                }
            }
        }
        return mDistFuelSurchargeCost;
    }

    public List getDistSmallOrderFeeList() {
        if (null == mSmallOrderFeeList || mSmallOrderFeeList.size() == 0) {
            mSmallOrderFeeList = new LinkedList();
            if (null != mSmallOrderFeeCriteriaV && mSmallOrderFeeCriteriaV.size() > 0) {
                for (int i = 0; i < mSmallOrderFeeCriteriaV.size(); i++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData)mSmallOrderFeeCriteriaV.get(i);
                    if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.IMPLIED.equalsIgnoreCase(crit.getRuntimeTypeCd())) {
                        String tableType = mSmallOrderFeeTableMap.get(crit.getFreightTableId()).getFreightTableTypeCd();
                        double compareValue = mItemAmount;
                        if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.POUND.equals(tableType) ||
                            RefCodeNames.FREIGHT_TABLE_TYPE_CD.KILOGRAMME.equals(tableType)) {
                            compareValue = getShoppingCartWeight(mShoppingCartItems, tableType).doubleValue();
                        }
                        BigDecimal lowerAmt = crit.getLowerAmount();
                        BigDecimal higherAmt = crit.getHigherAmount();
                        if (null == lowerAmt || compareValue >= lowerAmt.doubleValue()) {
                            if (null == higherAmt || compareValue <= higherAmt.doubleValue()) {
                                mSmallOrderFeeList.add(crit);
                            }
                        }
                    }
                }
            }
        }
        return mSmallOrderFeeList;
    }

    public double getDistSmallOrderFeeCost() {
        mDistSmallOrderFeeCost = 0.0;
        List criterias = getDistSmallOrderFeeList();
        if (criterias != null) {
            for (int i = 0; i < criterias.size(); ++i) {
                FreightTableCriteriaData criteria = (FreightTableCriteriaData)criterias.get(i);
                if (criteria != null) {
                    if (null != criteria.getHandlingAmount() &&
                        !RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equalsIgnoreCase(criteria.getFreightCriteriaTypeCd())) {
                        mDistSmallOrderFeeCost += criteria.getHandlingAmount().doubleValue();
                    }
                }
            }
        }
        return mDistSmallOrderFeeCost;
    }

}

package com.cleanwise.service.api.value;

/**
 * Title:        SiteData
 * Description:  Value object extension for marshalling site data.
 * Purpose:      Obtains and marshals site information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.PhysicalInventoryPeriod;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;


/**
 * <code>SiteData</code> is a value object that aggregates all the value
 * objects that make up a Site.
 */
public class SiteData extends ValueObject
{

    private static final Logger log = Logger.getLogger(SiteData.class);

    //Do not remove or modify next line. It would break object DB saving
    private static final long serialVersionUID = 2149906344587966044L;
    private static final BigDecimal ZERO = new BigDecimal(0.00);
    private BusEntityData mBusEntity;
    private BusEntityData mAccountBusEntity;
    private BusEntityAssocData mAccountAssoc;
    private AddressData mSiteAddress;
    private PhoneDataVector mPhones;
    private BudgetViewVector mBudgets;
    private BudgetSpendViewVector mSpendingInfo;
    private Integer mTargetFacilityRank;
    private PropertyData mTaxableIndicator;
    private PropertyDataVector mMiscProperties;
    private PropertyDataVector mDataFieldProperties;
    private PropertyDataVector mDataFieldPropertiesRuntime;
    //Jd begin
    private BigDecimal mWeightThreshold = null;
    private BigDecimal mPriceThreshold = null;
    private BigDecimal mContractThreshold = null;
    //Jd end
    private Hashtable mBudgetPeriods = new Hashtable();
    private PropertyData mInventoryShoppingType;
    private String mInventoryCartAccessInterval;
    private ArrayList<PhysicalInventoryPeriod> mPhysicalInventoryPeriods = null;
    private SitePriceListView mPriceLists;
    private OrderGuideDataVector mTemplateOrderGuides;
    private OrderGuideDataVector mCustomOrderGuides;
    private Date mLastUserVisitDate = null;
    
    //STJ-4384 : Add Rebill property in Site Details. 
    private PropertyData mReBill = null;
    
    /**
     *If this site is configured in such a way that frieght should be allocated to the budget
     */
    public boolean isAllocateFreightToBudget(){
    	if(mSpendingInfo == null){
    		return false;
    	}
        Iterator it = mSpendingInfo.iterator();
        while(it.hasNext()){
            BudgetSpendView bud = (BudgetSpendView) it.next();
            if(bud.getAllocateFreight()){
                return true;
            }
        }
        return false;
    }

    public void setSpendingInfo(BudgetSpendViewVector v) {
        mSpendingInfo = v;
    }
    
    public BudgetSpendViewVector getSpendingInfo() {
        return mSpendingInfo;
    }



    BudgetSpendViewVector sessionBudgetSpendViewVector;
    /**
     *
     */
    public void addSessionBudgetSpendViewVector(BudgetSpendView bsvd){
    	if(sessionBudgetSpendViewVector == null){
    		sessionBudgetSpendViewVector = new BudgetSpendViewVector();
    	}
    	sessionBudgetSpendViewVector.add(bsvd);
    }

    public BigDecimal getTotalBudgetSpent() {
        BigDecimal tspent = new BigDecimal(0);
        for ( int i = 0; mSpendingInfo != null && i < mSpendingInfo.size(); i++ ) {
            BudgetSpendView thisbudget = (BudgetSpendView)mSpendingInfo.get(i);
            if ( thisbudget.getAmountSpent() != null ) {
                tspent = tspent.add(thisbudget.getAmountSpent());
            }
        }
        for ( int i = 0; sessionBudgetSpendViewVector != null && i < sessionBudgetSpendViewVector.size(); i++ ) {
            BudgetSpendView thisbudget = (BudgetSpendView)sessionBudgetSpendViewVector.get(i);
            if ( thisbudget.getAmountSpent() != null ) {
                tspent = tspent.add(thisbudget.getAmountSpent());
            }
        }
        return tspent;
    }

    /**
     *Gets the amount this site has spent against the supplied cost center id or 0 if nothing was spent
     */
    public BigDecimal getBudgetSpent(int costCenterId) {
    	BigDecimal tspent = new BigDecimal(0);
        for ( int i = 0; mSpendingInfo != null && i < mSpendingInfo.size(); i++ ) {
            BudgetSpendView thisbudget = (BudgetSpendView)mSpendingInfo.get(i);
            if (thisbudget.getCostCenterId() == costCenterId && thisbudget.getAmountSpent() != null ) {
            	tspent = tspent.add(thisbudget.getAmountSpent());
            }
        }

        for ( int i = 0; sessionBudgetSpendViewVector != null && i < sessionBudgetSpendViewVector.size(); i++ ) {
            BudgetSpendView thisbudget = (BudgetSpendView)sessionBudgetSpendViewVector.get(i);
            if (thisbudget.getCostCenterId() == costCenterId && thisbudget.getAmountSpent() != null ) {
            	tspent = tspent.add(thisbudget.getAmountSpent());
            }
        }
        return tspent;
    }

    public BigDecimal getTotalBudgetAllocated() {
        BigDecimal talloc = new BigDecimal(0);
        for ( int i = 0; mSpendingInfo != null && i < mSpendingInfo.size(); i++ ) {
            BudgetSpendView thisbudget = (BudgetSpendView)mSpendingInfo.get(i);
            if ( thisbudget.getAmountAllocated() != null ) {
            talloc = talloc.add(thisbudget.getAmountAllocated());
            }
        }
        return talloc;
    }
    
    public BigDecimal getTotalBudgetRemaining() {
    	BigDecimal returnValue = null;
    	BigDecimal allocatedAmount = getTotalBudgetAllocated();
    	BigDecimal spentAmount = getTotalBudgetSpent();
    	if (allocatedAmount != null && spentAmount != null) {
    		returnValue = allocatedAmount.subtract(spentAmount);
    	}
    	return returnValue;
    }

    public Integer getBudgetThresholdPercent(int pCostCenterId) {
        for (int i = 0; mSpendingInfo != null && i < mSpendingInfo.size(); i++) {
            BudgetSpendView thisbudget = (BudgetSpendView) mSpendingInfo.get(i);
            if (thisbudget.getCostCenterId() == pCostCenterId) {
                return thisbudget.getBudgetThresholdPercent();
            }
        }
        return null;
    }
    public boolean isBudgetUnlimited(int costCenterId) {
             for ( int i = 0; mSpendingInfo != null && i < mSpendingInfo.size(); i++ ) {
                 BudgetSpendView thisbudget = (BudgetSpendView)mSpendingInfo.get(i);
                 if(thisbudget.getCostCenterId()==costCenterId){
                 	return thisbudget.getUnlimitedBudget();
                 }
             }
             return false;
    }

    /**
     *Gets the amount this site has allocate for the supplied cost center id or 0 if nothing was spent
     */
    public BigDecimal getBudgetAllocated(int costCenterId) {
        for ( int i = 0; mSpendingInfo != null && i < mSpendingInfo.size(); i++ ) {
            BudgetSpendView thisbudget = (BudgetSpendView)mSpendingInfo.get(i);
            if(thisbudget.getCostCenterId()==costCenterId && thisbudget.getAmountAllocated() != null){
                return thisbudget.getAmountAllocated();
            }
        }
        return ZERO;
    }

    public void setBudgetPeriods(Hashtable v) {
        mBudgetPeriods = v;
    }
    public Hashtable getBudgetPeriods() {
        return mBudgetPeriods;
    }

    int mCurrentBudgetPeriod = 1;
    public void setCurrentBudgetPeriod(int v ) {
	mCurrentBudgetPeriod = v;
    }
    public int getCurrentBudgetPeriod( ) {
	return mCurrentBudgetPeriod;
    }

    int mCurrentInventoryPeriod = 1;
    public void setCurrentInventoryPeriod(int v ) {
	mCurrentInventoryPeriod = v;
    }
    public int getCurrentInventoryPeriod( ) {
	return mCurrentInventoryPeriod;
    }

    public SiteData(BusEntityData pSiteBusEntity,
                    BusEntityData pAccountBusEntity,
		    BusEntityAssocData pAccountAssoc,
		    AddressData pSiteAddress,
		    PhoneDataVector pPhones,
		    BudgetViewVector pBudgets,
                    PropertyData pSubContractor,
                    Integer pTargetFacilityRank,
                    PropertyData pTaxableIndicator,
		    PropertyDataVector pMiscProperties,
		    PropertyDataVector pFieldProperties,
                    PropertyDataVector pFieldPropertiesRuntime,
                    BlanketPoNumData pBlanketPoNumData,
                   //Jd begin
                    BigDecimal pWeightThreshold,
                    BigDecimal pPriceThreshold,
                    BigDecimal pContractThreshold
                    //Jd end
                    ) {
	this.mBusEntity = (pSiteBusEntity != null)
	    ? pSiteBusEntity : BusEntityData.createValue();
	this.mAccountBusEntity = (pAccountBusEntity != null)
	    ? pAccountBusEntity : BusEntityData.createValue();
	this.mAccountAssoc = (pAccountAssoc != null)
	    ? pAccountAssoc : BusEntityAssocData.createValue();
	this.mSiteAddress = (pSiteAddress != null)
	    ? pSiteAddress : AddressData.createValue();
	this.mPhones = (pPhones != null)
	    ? pPhones : new PhoneDataVector();
	this.mBudgets = (pBudgets != null)
	    ? pBudgets : new BudgetViewVector();
        this.mTaxableIndicator = (pTaxableIndicator != null)
	    ? pTaxableIndicator : PropertyData.createValue();
	this.mMiscProperties = (pMiscProperties != null)
	    ? pMiscProperties : new PropertyDataVector();
	this.mDataFieldProperties = (pFieldProperties != null)
	    ? pFieldProperties : new PropertyDataVector();
        this.mDataFieldPropertiesRuntime = (pFieldPropertiesRuntime != null)
	    ? pFieldPropertiesRuntime : new PropertyDataVector();
         //Jd begin
         this.mWeightThreshold = pWeightThreshold;
         this.mPriceThreshold = pPriceThreshold;
         this.mContractThreshold =  pContractThreshold;
         //Jd end
         this.mInventoryShopping = PropertyData.createValue();
		 this.mInventoryShoppingHoldOrderUntilDeliveryDate = PropertyData.createValue();
         this.mTargetFacilityRank = pTargetFacilityRank;
         this.blanketPoNum = (pBlanketPoNumData != null)
	    ? pBlanketPoNumData : BlanketPoNumData.createValue();
    }

    /**
     *  <code>createValue</code>, instantiate a site data
     *  object where all values a initialized to some default,
     *  non-null value.
     *
     * @return a <code>SiteData</code> value
     */
    public static SiteData createValue ()
    {
	return new SiteData(null,null,null,null,null,
                            null,null,null,null,null, null,
                            null,null,null,null,null
                            );
    }

    /**
     * Describe <code>getBusEntity</code> method here.
     *
     * @return a <code>BusEntityData</code> value
     */
    public BusEntityData getBusEntity() {
	return mBusEntity;
    }

    public void  setBusEntity(BusEntityData v) {
	mBusEntity=v;
    }

    public boolean isActive() {

        if ( getBusEntity() == null ) return false;

        String status = getBusEntity().getBusEntityStatusCd();

        if ( null == status ) return false;

        return status.equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);

    }

    /**
     * Describe <code>getAccountBusEntity</code> method here.
     *
     * @return a <code>BusEntityData</code> value
     */
    public BusEntityData getAccountBusEntity() {
	return mAccountBusEntity;
    }

    /**
     * Describe <code>getAccountAssoc</code> method here.
     *
     * @return a <code>BusEntityAssocData</code> value
     */
    public BusEntityAssocData getAccountAssoc() {
	return mAccountAssoc;
    }

    /**
     * Describe <code>getSiteAddress</code> method here.
     *
     * @return an <code>AddressData</code> value
     */
    public AddressData getSiteAddress() {
        return mSiteAddress;
    }
    public void setSiteAddress(AddressData v) {
        mSiteAddress = v;
    }

    /**
     * Describe <code>getPhones</code> method here.
     *
     * @return an <code>PhoneDataVector</code> value
     */
    public PhoneDataVector getPhones() {
	return mPhones;
    }

    /**
     * Describe <code>getBudgets</code> method here.
     *
     * @return an <code>BudgetDataVector</code> value
     */
    public BudgetViewVector getBudgets() {
	return mBudgets;
    }

	// Has this site got any budgets.
	 public boolean getHasBudgets() {

		if ( mBudgets != null &&
			mBudgets.size() > 0 ) {
			return true;
		}

		return false;
	 }

	 public boolean hasBudgets() {
         return getHasBudgets();
	 }

     /**
     * Describe <code>getTargetFacilityRank</code> method here.
     *
     * @return an <code>Integer</code> value
     */
    public Integer getTargetFacilityRank() {
	return mTargetFacilityRank;
    }
    public boolean getIsaTargetFacility() {
        if ( null == mTargetFacilityRank ) {
            return false;
        }
        if (mTargetFacilityRank.intValue() >=
            Utility.TARGET_FACILITY_THRESHOLD.intValue() ) {
            return true;
        }
        return false;
    }
    public void setTargetFacilityRank(Integer pValue){
        mTargetFacilityRank = pValue;
    }

    /**
     * Describe <code>getTaxableIndicator</code> method here.
     *
     * @return an <code>PropertyData</code> value
     */
    public PropertyData getTaxableIndicator() {
	return mTaxableIndicator;
    }
    /**
     * Describe <code>getMiscProperties</code> method here.
     *
     * @return an <code>PropertyDataVector</code> value
     */
    public PropertyDataVector getMiscProperties() {
	return mMiscProperties;
    }


    public PropertyDataVector getDataFieldProperties() {
        return mDataFieldProperties;
    }

    public void setDataFieldProperties(PropertyDataVector v) {
        mDataFieldProperties = v;
    }

    public PropertyDataVector getDataFieldPropertiesRuntime() {

        return mDataFieldPropertiesRuntime;
    }

    public void setDataFieldPropertiesRuntime(PropertyDataVector v) {
        mDataFieldPropertiesRuntime = v;
    }

   //Jd begin
    public BigDecimal getWeightThreshold() {return mWeightThreshold;}
    public void setWeightThreshold(BigDecimal pValue) {mWeightThreshold = pValue;}

    public BigDecimal getPriceThreshold() {return mPriceThreshold;}
    public void setPriceThreshold(BigDecimal pValue) {mPriceThreshold = pValue;}

    public BigDecimal getContractThreshold() {return mContractThreshold;}
    public void setContractThreshold(BigDecimal pValue) {mContractThreshold = pValue;}

    public String  getPriceCode() {
      for(int ii=0; ii<mDataFieldProperties.size(); ii++) {
        PropertyData prop = (PropertyData) mDataFieldProperties.get(ii);
        if("Price Code".equalsIgnoreCase(prop.getShortDesc())){
          return prop.getValue();
        }
      }
      return "";
    }
    //Jd end



    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SiteData object
     */
    public String toString()
    {
	return "[" +
            "BusEntity=" + mBusEntity +
            ", AccountBusEntity=" + mAccountBusEntity +
            ", BusEntityAssocData=" + mAccountAssoc +
            ", SiteAddress=" + mSiteAddress +
            ", Phones=" + mPhones +
            ", Budgets=" + mBudgets +
            ", TargetFacilityRank=" + mTargetFacilityRank +
            ", TaxableIndicator=" + mTaxableIndicator +
            ", MiscProperties=" + mMiscProperties +
            ", DataFieldProperties=" + mDataFieldProperties +
            ", DataFieldPropertiesRuntime=" + mDataFieldPropertiesRuntime +
            ", WeightThreshold = " + mWeightThreshold +
            ", PriceThreshold = " + mPriceThreshold +
            ", ContractThreshold = "+ mContractThreshold +
            ", mInventoryShopping=" + mInventoryShopping +
	    ", mContractData=" + mContractData +
            "]";
    }

    public String toBasicInfo()
    {
	String t = "\nSiteData [" +
            "\n  Name     =" + mBusEntity.getShortDesc() +
            "\n  Id       =" + mBusEntity.getBusEntityId() +
            "\n  ErpNum   =" + mBusEntity.getErpNum() +
	    "\n  ModBy    =" + mBusEntity.getModBy() +
	    "\n  ModDate  =" + mBusEntity.getModDate() +
	    "\n    hasInventoryShoppingOn = " + hasInventoryShoppingOn() +
	    "\n    CurrentBudgetPeriod    = " + mCurrentBudgetPeriod +
	    "\n    CurrentInventoryPeriod = " + mCurrentInventoryPeriod ;

	if ( null != mContractData ) {
	    t +=  "\n    Contract   = " + mContractData.getShortDesc()
		+ "\n    ContractId = " + mContractData.getContractId()
		;
	}

	for ( int i = 0; null != mMiscProperties &&
		  i < mMiscProperties.size(); i++ ) {
            PropertyData pd =  (PropertyData)mMiscProperties.get(i) ;
	    t += "\n  MISC." + pd.getShortDesc() + " = " +
		pd.getValue();
	}
	for ( int i = 0; null != mDataFieldProperties &&
		  i < mDataFieldProperties.size(); i++ ) {
            PropertyData pd =  (PropertyData)mDataFieldProperties.get(i) ;
	    t += "\n  DATA." + pd.getShortDesc() + " = " +
		pd.getValue();
	}
	for ( int i = 0; null != mDataFieldPropertiesRuntime &&
		  i < mDataFieldPropertiesRuntime.size(); i++ ) {
            PropertyData pd =  (PropertyData)
		mDataFieldPropertiesRuntime.get(i) ;
	    t += "\n  RUNF." + pd.getShortDesc() + " = " +
		pd.getValue();
	}

	t += "\n]";

	return t;
    }

    /**
     *Returns true if this site can be routed via the order routing pipeline, false otherwise
     */
    public boolean isAllowOrderRouting(){
        Iterator it = mMiscProperties.iterator();
        while(it.hasNext()){
            PropertyData p = (PropertyData) it.next();
            if(RefCodeNames.PROPERTY_TYPE_CD.BYPASS_ORDER_ROUTING.equals(p.getShortDesc())){
                //return the negative
                return !Utility.isTrue(p.getValue());
            }
        }
        //if it is not setup then it can be routed
        return true;
    }


    private PropertyData mInventoryShopping;
    public void setInventoryShopping(PropertyData pV) {
        mInventoryShopping = pV;
    }
    public PropertyData getInventoryShopping() {
        return this.mInventoryShopping ;
    }

    public boolean hasInventoryShoppingOn() {
    	if (isAllowCorpSchedOrder())
    		return true;
	if ( mInventoryShopping == null ) return false;
	if ( mInventoryShopping.getValue() == null ) return false;
	return mInventoryShopping.getValue().equals("on");
    }

    private java.util.ArrayList siteInv;

    /**
     * Get the value of siteInv.
     * @return value of siteInv.
     */
    public java.util.ArrayList getSiteInventory() {
        if ( null == siteInv ) {
            siteInv = new SiteInventoryInfoViewVector();
        }
        return siteInv;
    }
    public void setSiteInventory(java.util.ArrayList v) {
        siteInv = v;
    }
    public void resetSiteInventory() {
        for ( int i = 0; siteInv != null && i < siteInv.size();
              i++ ) {
            SiteInventoryInfoView siiv =
                (SiteInventoryInfoView)siteInv.get(i);
            siiv.setQtyOnHand("");
        }
    }

    public boolean hasInventoryAutoOrderItem() {
        for ( int i = 0; siteInv != null && i < siteInv.size();
              i++ ) {
            SiteInventoryInfoView siiv =
                (SiteInventoryInfoView)siteInv.get(i);
                if (isAnInventoryAutoOrderItem(siiv))  {
                    return true;
                }
            }
            return false;
    }

    private boolean _isAnInventoryAutoOrderItem(String v) {
	    return v.equals("Y");
    }

    public boolean isAnInventoryAutoOrderItem(SiteInventoryConfigView siiv) {
        if ( siiv != null &&
            siiv.getAutoOrderItem() != null )  {
            return _isAnInventoryAutoOrderItem
		    (siiv.getAutoOrderItem() );
        }
        return false;
    }
    public boolean isAnInventoryAutoOrderItem(SiteInventoryInfoView siiv) {
        if ( siiv != null &&
            siiv.getAutoOrderItem() != null )  {
            return _isAnInventoryAutoOrderItem
		    (siiv.getAutoOrderItem() );
        }
        return false;
    }

    public boolean isAnInventoryAutoOrderItem(String pItemidString) {
        return isAnInventoryAutoOrderItem(Integer.parseInt(pItemidString));
    }
    public boolean isAnInventoryAutoOrderItem(Integer pItemidInt) {
        return isAnInventoryAutoOrderItem(pItemidInt.intValue());
    }

    public boolean isAnInventoryAutoOrderItem(int pItemid) {

        for (int i = 0; siteInv != null && i < siteInv.size(); i++) {
            SiteInventoryInfoView siiv = (SiteInventoryInfoView) siteInv.get(i);

            if (pItemid == siiv.getItemId() && isAnInventoryAutoOrderItem(siiv)) {
                return true;
            }
        }

        return false;
    }

    public boolean isAnInventoryOrderItem(int pItemid) {

        for (int i = 0; siteInv != null && i < siteInv.size(); i++) {
            SiteInventoryInfoView siiv = (SiteInventoryInfoView) siteInv.get(i);

            if (pItemid == siiv.getItemId()) {
                return true;
            }
        }

        return false;
    }


    public String getInventorySummary()
    {
        String os = "";
        os += "\n\nLocation: " + mBusEntity.getShortDesc() + "\n\n";
        for ( int i = 0; siteInv != null && i < siteInv.size();
              i++ ) {
            SiteInventoryInfoView siiv =
                (SiteInventoryInfoView)siteInv.get(i);
            if ( siiv.getParValue() > 0 )
            {
		String qty = siiv.getQtyOnHand();
		if ( null == qty ) qty = "none";
                os += "\n  SKU         = " + siiv.getItemSku();
                os += "\n  Description = " + siiv.getItemDesc();
                os += "\n  Qty On Hand = " + qty;
                os += "\n  Par Value   = " + siiv.getParValue();
                os += "\n";
            }
        }
        os += "\n";
        return os;
    }

    java.util.Date mNextDeliveryDate = null;

    public java.util.Date getNextDeliveryDate() {
        return mNextDeliveryDate;
    }

    public void setNextDeliveryDate(java.util.Date v) {
        mNextDeliveryDate = v;
    }

    java.util.Date mNextOrdercutoffDate = null;

    public java.util.Date getNextOrdercutoffDate() {
        return mNextOrdercutoffDate;
    }

    public void setNextOrdercutoffDate(java.util.Date v) {
        mNextOrdercutoffDate = v;
    }

    private Date mNextOrdercutoffTime;

    public java.util.Date getNextOrdercutoffTime() {
        return mNextOrdercutoffTime;
    }

    public void setNextOrdercutoffTime(java.util.Date v) {
        mNextOrdercutoffTime= v;
    }

    public String getSiteFieldValue(String pFieldName) {
        if ( mDataFieldProperties == null ) {
            return null;
        }
        for ( int i = 0; i < mDataFieldProperties.size(); i++ ) {
            PropertyData spd = (PropertyData)mDataFieldProperties.get(i);
            if ( spd.getShortDesc().startsWith(pFieldName) ) {
                return spd.getValue();
            }
        }
        return null;
    }

    // Currently active contract.
    private ContractData mContractData = null;
    public ContractData getContractData() {
	return mContractData;
    }
    public void setContractData(ContractData v) {
	mContractData = v;
    }

    private int mCatalogId;
    public int getSiteCatalogId() {
        return mCatalogId;
    }
    public void setSiteCatalogId(int v) {
        mCatalogId = v;
    }

    public int getSiteId() {
	if (null == mBusEntity) return 0;
	return mBusEntity.getBusEntityId();
    }
    public int getAccountId() {
	if (null == mAccountBusEntity) return 0;
	return mAccountBusEntity.getBusEntityId();
    }

    public PropertyData getComments()
    {
        return getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS);
    }

    public PropertyData getMiscProp(String pPropShortDesc) {

        if ( null == mMiscProperties ||
             mMiscProperties.size() == 0 )
        {
            return null;
        }

        for (int i = 0; i < mMiscProperties.size(); i++ )
        {
            PropertyData pd = (PropertyData)mMiscProperties.get(i);

            if ( pd.getShortDesc() == null ) {
                continue;
            }
            if(pd.getShortDesc().equals(pPropShortDesc))
            {
                return pd;
            }
        }

        for (int i = 0; i < mDataFieldProperties.size(); i++ )
        {
            PropertyData pd = (PropertyData)mDataFieldProperties.get(i);

            if ( pd.getShortDesc() == null ) {
                continue;
            }
            if(pd.getShortDesc().equals(pPropShortDesc))
            {
                return pd;
            }
        }

        for (int i = 0; i < mDataFieldPropertiesRuntime.size(); i++ )
        {
            PropertyData pd = (PropertyData)mDataFieldPropertiesRuntime.get(i);

            if ( pd.getShortDesc() == null ) {
                continue;
            }
            if(pd.getShortDesc().equals(pPropShortDesc))
            {
                return pd;
            }
        }

        return null;
    }

    private String mWorkflowMessage = null;
    public String getWorkflowMessage() { return mWorkflowMessage; }
    public void setWorkflowMessage(String v) { mWorkflowMessage = v; }

    BuildingServicesContractorView mBSC = null;


    public boolean hasBSC() {
        if (mBSC == null) return false;
        return true;
    }
    public BuildingServicesContractorView getBSC() { return mBSC; }
    public void setBSC(BuildingServicesContractorView pV)
    { mBSC = pV; }



  private ShoppingControlDataVector mShoppingControls;

    /**
     * Holds value of property blanketPoNum.
     */
    private BlanketPoNumData blanketPoNum;
  public ShoppingControlDataVector getShoppingControls() {
    if ( null == mShoppingControls )
      mShoppingControls = new ShoppingControlDataVector();
    return mShoppingControls;
  }
    public Hashtable  getShoppingControlsMap() {
	Hashtable ht = new Hashtable(1);
	ShoppingControlDataVector scdv = getShoppingControls();
	if ( null == scdv ) { return ht; }

	Iterator it = scdv.iterator();
	while ( it.hasNext()) {
	    ShoppingControlData scd = (ShoppingControlData)it.next();
	    ht.put(new Integer(scd.getItemId()), scd);
	}

	return ht;
    }


  public void setShoppingControls(ShoppingControlDataVector v) {
    mShoppingControls = v;
  }

    /**
     * Getter for property blanketPoNum.
     * @return Value of property blanketPoNum.
     */
    public BlanketPoNumData getBlanketPoNum() {

        return this.blanketPoNum;
    }

    /**
     * Setter for property blanketPoNum.
     * @param blanketPoNum New value of property blanketPoNum.
     */
    public void setBlanketPoNum(BlanketPoNumData blanketPoNum) {

        this.blanketPoNum = blanketPoNum;
    }


    public boolean isBlanketPoNumSet(){
        if(this.blanketPoNum != null && Utility.isSet(this.blanketPoNum.getPoNumber())){
            return true;
        }
        return false;
    }

    public boolean  isSetupForMonthlyOrder() {

        Iterator it = mMiscProperties.iterator();
        while(it.hasNext()){
            PropertyData p = (PropertyData) it.next();
            if(RefCodeNames.PROPERTY_TYPE_CD.DELIVERY_SCHEDULE.equals(p.getShortDesc())){
		return p.getValue().startsWith("Monthly");
            }
        }

        return false;
    }


    public boolean  isSetupForSharedOrderGuides() {

        PropertyData pd = getMiscProp
	    (RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);

	if ( pd != null &&
	     Utility.isTrue(pd.getValue())) {
	    return true;
	}
        return false;
    }

    private PropertyData mInventoryShoppingHoldOrderUntilDeliveryDate;
	public PropertyData getInventoryShoppingHoldOrderUntilDeliveryDate() {
		return mInventoryShoppingHoldOrderUntilDeliveryDate;
	}

	public void setInventoryShoppingHoldOrderUntilDeliveryDate(
			PropertyData inventoryShoppingHoldOrderUntilDeliveryDate) {
		this.mInventoryShoppingHoldOrderUntilDeliveryDate = inventoryShoppingHoldOrderUntilDeliveryDate;
	}

    public void setInventoryShoppingType(PropertyData pInventoryShoppingType) {
        this.mInventoryShoppingType = pInventoryShoppingType;
    }

    public PropertyData getInventoryShoppingType() {
       return this.mInventoryShoppingType;
    }

    public boolean hasModernInventoryShopping() {
    	if (isAllowCorpSchedOrder())
    		return true;
        if (mInventoryShoppingType == null) return false;
        if (mInventoryShoppingType.getValue() == null) return false;
        return mInventoryShoppingType.getValue().equals("on");
    }

    public String getInventoryCartAccessInterval() {
        return mInventoryCartAccessInterval;
    }

    public void setInventoryCartAccessInterval(String pValue) {
        this.mInventoryCartAccessInterval = pValue;
    }

    public boolean hasInventoryCartAccessOpen(){
        return hasInventoryCartAccessOpen(null);
    }

    public boolean hasInventoryCartAccessOpen(Date currentDate) {

        if (this.getNextOrdercutoffDate() == null ||
                this.getInventoryCartAccessInterval() == null) {
            return false;
        }

        if(!Utility.isSet(this.getInventoryCartAccessInterval())){
        	return true;
        }
        
        Date accessTime;
		try {
			accessTime = getNextScheduleAccessTime();
		} catch (Exception e) {
			return false;
		}
            
        if(currentDate==null) {
            currentDate=new Date();
        }

        GregorianCalendar upRangeCal = new  GregorianCalendar();
        upRangeCal.setTime(getNextOrdercutoffDate());

        Date cutoffTime = getNextOrdercutoffTime();
        if(cutoffTime!=null){
            GregorianCalendar cutoffTimeCal = new GregorianCalendar();
            cutoffTimeCal.setTime(cutoffTime);
            upRangeCal.set(Calendar.HOUR_OF_DAY,cutoffTimeCal.get(Calendar.HOUR_OF_DAY));
            upRangeCal.set(Calendar.MINUTE,cutoffTimeCal.get(Calendar.MINUTE));
            upRangeCal.set(Calendar.SECOND,cutoffTimeCal.get(Calendar.SECOND));
            upRangeCal.set(Calendar.MILLISECOND,cutoffTimeCal.get(Calendar.MILLISECOND));
        }

        return currentDate.after(accessTime)&&currentDate.before(upRangeCal.getTime());
      
    }
    
    public Date getNextScheduleAccessTime() throws Exception {
    	if (getNextOrdercutoffDate() == null)
    		return null;
    	if(Utility.isSet(this.getInventoryCartAccessInterval())){
	        int accessInterval = Integer.parseInt(this.getInventoryCartAccessInterval());
	        GregorianCalendar cal = new GregorianCalendar();
	        boolean allowCorpSchedOrder = isAllowCorpSchedOrder();
	        if (allowCorpSchedOrder){
	        	Date cutoffDateTime = Utility.getDateTime(this.getNextOrdercutoffDate(), getNextOrdercutoffTime());
	        	cal.setTime(cutoffDateTime);
	            cal.add(Calendar.HOUR, (-1) * accessInterval);
	        }else{
	        	cal.setTime(this.getNextOrdercutoffDate());                
	        	cal.add(Calendar.DATE, (-1) * accessInterval);
	        }
	        Date accessTime = cal.getTime();
	        return accessTime;
    	}else{
    		return getNextOrdercutoffDate();
    	}        
    }

    public boolean hasDiscretionaryCartAccessOpen(){
        return hasDiscretionaryCartAccessOpen(null);
    }

    public boolean hasDiscretionaryCartAccessOpen(Date currentDate){

        if(Utility.isSet(this.getInventoryCartAccessInterval())){
            try {
                return !hasInventoryCartAccessOpen(currentDate);
            }
            catch (NumberFormatException e) {
                return true;
            }
        } else{
            return true;
        }

    }

    public boolean isModernInventoryCartAvailable(){
        return isModernInventoryCartAvailable(null);
    }

    public boolean isModernInventoryCartAvailable(Date currentDate) {

        if (this.getContractData() == null) {
            return false;
        }

        if (this.getContractData().getCatalogId() <= 0) {
            return false;
        }

        if (this.getContractData().getContractId() <= 0) {
            return false;
        }

        if (!this.hasModernInventoryShopping()) {
            return false;
        }

        if (!this.hasInventoryShoppingOn()) {
            return false;
        }

        if (!this.hasInventoryCartAccessOpen(currentDate)) {
            return false;
        }

        return true;
    }

    public boolean isModernInventoryCartAvailableWithoutDatesChecking() {

        if (this.getContractData() == null) {
            return false;
        }

        if (this.getContractData().getCatalogId() <= 0) {
            return false;
        }

        if (this.getContractData().getContractId() <= 0) {
            return false;
        }

        if (!this.hasModernInventoryShopping()) {
            return false;
        }

        if (!this.hasInventoryShoppingOn()) {
            return false;
        }

        return true;
    }

    public ArrayList<PhysicalInventoryPeriod> getPhysicalInventoryPeriods() {
        return mPhysicalInventoryPeriods;
    }

    public void setPhysicalInventoryPeriods(ArrayList<PhysicalInventoryPeriod> periods) {
        mPhysicalInventoryPeriods = periods;
    }

    public String getProductBundle() {
        PropertyData property = getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SITE_PRODUCT_BUNDLE);
        return property != null ? property.getValue() : null;
    }

    public boolean isUseProductBundle() {
        return Utility.isSet(getProductBundle());
    }

    public void setPriceLists(SitePriceListView priceLists) {
        this.mPriceLists = priceLists;
    }

    public SitePriceListView getPriceLists() {
        return mPriceLists;
    }

    public Integer getPriceListRank1Id() {
        return getPriceLists() != null
                && getPriceLists().getPriceList1() != null
                ? getPriceLists().getPriceList1().getPriceListId()
                : null;
    }

    public Integer getPriceListRank2Id() {
        return getPriceLists() != null
                && getPriceLists().getPriceList2() != null
                ? getPriceLists().getPriceList2().getPriceListId()
                : null;
    }

    public IdVector getProprietaryPriceListIds() {
        return getPriceLists() != null
                && getPriceLists().getProprietaryLists() != null
                ? Utility.toIdVector(getPriceLists().getProprietaryLists())
                : null;
    }

    public void setTemplateOrderGuides(OrderGuideDataVector pTemplateOrderGuides) {
        this.mTemplateOrderGuides = pTemplateOrderGuides;
    }

    public OrderGuideDataVector getTemplateOrderGuides() {
        return this.mTemplateOrderGuides;
    }

    public void setCustomOrderGuides(OrderGuideDataVector pCustomOrderGuides) {
        this.mCustomOrderGuides = pCustomOrderGuides;
    }

    public OrderGuideDataVector getCustomOrderGuides() {
        return this.mCustomOrderGuides;
    }

    public List<Integer> getAvailableTemplateOrderGuideIds() {

         List<Integer> ids = new ArrayList<Integer>();

         ids.addAll(Utility.toIdVector(getTemplateOrderGuides()));
         ids.addAll(Utility.toIdVector(getCustomOrderGuides()));

         return ids;
    }

	/**
	 * @return the lastUserVisitDate
	 */
	public Date getLastUserVisitDate() {
		return mLastUserVisitDate;
	}

	/**
	 * @param lastUserVisitDate the lastUserVisitDate to set
	 */
	public void setLastUserVisitDate(Date lastUserVisitDate) {
		this.mLastUserVisitDate = lastUserVisitDate;
	}

	public PropertyData getReBill() {
		return mReBill;
	}

	public void setReBill(PropertyData pReBill) {
		mReBill = pReBill;
	}
	
	private PropertyData mAllowCorpSchedOrder;
    public void setAllowCorpSchedOrder(PropertyData pV) {
        mAllowCorpSchedOrder = pV;
    }
    public PropertyData getAllowCorpSchedOrder() {
        return this.mAllowCorpSchedOrder ;
    }
    
    public boolean isAllowCorpSchedOrder() {
        if (mAllowCorpSchedOrder == null) return false;
        return Utility.isTrue(mAllowCorpSchedOrder.getValue());
    }

}




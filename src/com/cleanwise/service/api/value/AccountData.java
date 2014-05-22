package com.cleanwise.service.api.value;

/**
 * Title:        AccountData
 * Description:  Value object extension for marshalling account data.
 * Purpose:      Obtains and marshals account information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

/**
 * <code>AccountData</code> is a value object that aggregates all the value
 * objects that make up a Store.
 */
public class AccountData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = -2944406569398461798L;

    private BusEntityData mBusEntity;
    private BusEntityAssocData mStoreAssoc;
    private AddressData mPrimaryAddress;
    private PhoneData mPrimaryPhone;
    private PhoneData mPrimaryFax;
    private PhoneData mOrderPhone;
    private PhoneData mOrderFax;
    private EmailData mPrimaryEmail;
    private EmailData mCustomerServiceEmail;
    private EmailData mDefaultEmail;
    private AddressData mBillingAddress;
    private PropertyData mAccountType;
    private PropertyData mOrderMinimum;
    private PropertyData mCreditLimit;
    private PropertyData mCreditRating;
    private PropertyData mCrcShop;
    private PropertyData mComments;
    private PropertyData mResaleAccountErpNumber;
    private boolean mAuthorizedForResale;
    private String mEdiShipToPrefix;
    private BusEntityParameterDataVector mAccountParameters;
    private CatalogData mAccountCatalog;

    private PropertyDataVector mMiscProperties;
    private ArrayList mOrderManagerEmails;

    /** Holds value of property targetMargin. */
    private BigDecimal targetMargin;

    /** Holds value of property dataFieldProperties. */
    private List dataFieldProperties;

    /** Holds value of property dataFieldPropertiesRuntime. */
    private PropertyDataVector dataFieldPropertiesRuntime;

    /**
     * Holds value of property customerSystemApprovalCd.
     */
    private String customerSystemApprovalCd;
    private String trackPunchoutOrderMessages = null;

    private List mRuntimeDisplayOrderItemActionTypes;

    private String mAccountFolder;
    private String distrPOType;

    private ProductViewDefDataVector productViewDefinitions;

    //private EmailData mWorkflowEmail;
    /**
     * Creates a new <code>AccountData</code> instance.  This should be
     * a private interface callable only from the Store session bean.
     * (Unfortunately I do not know of a way to do that)
     */

    public AccountData(BusEntityData pBusEntity,
            BusEntityAssocData pStoreAssoc,
            AddressData pPrimaryAddress,
            PhoneData pPrimaryPhone,
            PhoneData pPrimaryFax,
            PhoneData pOrderPhone,
            PhoneData pOrderFax,
            EmailData pPrimaryEmail,
            AddressData pBillingAddress,
            PropertyData pAccountType,
            PropertyData pOrderMinimum,
            PropertyData pCreditLimit,
            PropertyData pCreditRating,
            PropertyData pCrcShop,
            PropertyData pComments,
            PropertyDataVector pMiscProperties,
            CatalogData pAccountCatalog,
            ArrayList pOrderManagerEmails,
            PropertyData pAuthForResale,
            PropertyData pResaleErpAccount,
            String pEdiShipToPrefix,
            BigDecimal pTargetMargin,
            List pDataFieldProperties,
            PropertyDataVector pDataFieldPropertiesRuntime,
            String pCustomerSystemApprovalCd,
            String pTrackPunchoutOrderMessages,
            boolean pMakeShipToBillTo,
            String pFreightChargeType,
            List pRuntimeDisplayOrderItemActionTypes,
            String pPurchaseOrderAccountName,
            String pBudgetTypeCd,
            boolean pCustomerRequestPoAllowed,
            PhoneData pFaxBackConfirm,
            boolean pTaxableIndicator,
            String distrPOType,
            EmailData pCustomerServiceEmail,
            EmailData pDefaultEmail,
            String pGlTranformationType,
            String pDistributorAccountRefNum,
            ProductViewDefDataVector productViewDefinitions) {
        this.mBusEntity = (pBusEntity != null)
        ? pBusEntity : BusEntityData.createValue();
        this.mStoreAssoc = (pStoreAssoc != null)
        ? pStoreAssoc : BusEntityAssocData.createValue();
        this.mPrimaryAddress = (pPrimaryAddress != null)
        ? pPrimaryAddress : AddressData.createValue();
        this.mPrimaryPhone = (pPrimaryPhone != null)
        ? pPrimaryPhone : PhoneData.createValue();
        this.mPrimaryFax = (pPrimaryFax != null)
        ? pPrimaryFax : PhoneData.createValue();
        this.mOrderPhone = (pOrderPhone != null)
        ? pOrderPhone : PhoneData.createValue();
        this.mOrderFax = (pOrderFax != null)
        ? pOrderFax : PhoneData.createValue();
        this.mPrimaryEmail = (pPrimaryEmail != null)
        ? pPrimaryEmail : EmailData.createValue();
        this.mBillingAddress = (pBillingAddress != null)
        ? pBillingAddress : AddressData.createValue();
        this.mAccountType = (pAccountType != null)
        ? pAccountType : PropertyData.createValue();
        this.mOrderMinimum = (pOrderMinimum != null)
        ? pOrderMinimum : PropertyData.createValue();
        this.mCreditLimit = (pCreditLimit != null)
        ? pCreditLimit : PropertyData.createValue();
        this.mCreditRating = (pCreditRating != null)
        ? pCreditRating : PropertyData.createValue();
        this.mCrcShop = (pCrcShop != null)
        ? pCrcShop : PropertyData.createValue();
        this.mComments = (pComments != null)
        ? pComments : PropertyData.createValue();
        this.mMiscProperties = (pMiscProperties != null)
        ? pMiscProperties : new PropertyDataVector();
        this.mAccountCatalog = pAccountCatalog;
        this.mOrderManagerEmails = (pOrderManagerEmails != null)
        ? pOrderManagerEmails : new ArrayList();
        if(pAuthForResale == null){
            this.mAuthorizedForResale = false;
        }else{
            this.mAuthorizedForResale = com.cleanwise.service.api.util.Utility.isTrue(pAuthForResale.getValue());
        }
        this.mResaleAccountErpNumber = (pResaleErpAccount != null)
        ? pResaleErpAccount : PropertyData.createValue();
        this.mEdiShipToPrefix = pEdiShipToPrefix;
        this.targetMargin = pTargetMargin;
        this.dataFieldProperties = pDataFieldProperties;
        this.dataFieldPropertiesRuntime = pDataFieldPropertiesRuntime;
        this.customerSystemApprovalCd = pCustomerSystemApprovalCd;
        this.trackPunchoutOrderMessages = pTrackPunchoutOrderMessages;
        this.makeShipToBillTo = pMakeShipToBillTo;
        this.freightChargeType = pFreightChargeType;
        this.purchaseOrderAccountName = pPurchaseOrderAccountName;
        this.budgetTypeCd = pBudgetTypeCd;
        this.mAccountParameters = null;
        this.mRuntimeDisplayOrderItemActionTypes = (pRuntimeDisplayOrderItemActionTypes != null)
        ? pRuntimeDisplayOrderItemActionTypes : new ArrayList();
        this.customerRequestPoAllowed = pCustomerRequestPoAllowed;
        this.faxBackConfirm = (pFaxBackConfirm != null)
        ? pFaxBackConfirm : PhoneData.createValue();
        this.taxableIndicator = pTaxableIndicator;
        this.distrPOType = distrPOType;
        this.mCustomerServiceEmail = (pCustomerServiceEmail != null)
        ? pCustomerServiceEmail : EmailData.createValue();
        this.mDefaultEmail = (pDefaultEmail != null)
        ? pDefaultEmail : EmailData.createValue();
        this.glTransformationType = pGlTranformationType;
        this.distributorAccountRefNum = pDistributorAccountRefNum;
        this.productViewDefinitions = productViewDefinitions;
    }

    /**
     * Describe <code>createValue</code> method here.
     *
     * @return a <code>AccountData</code> value
     */
    public static AccountData createValue() {
        AccountData acctD = new AccountData(null,null,null,null,null, null, null,
                null,null,null,null,null, null, null, null, null, null,null,null,
                null,null,null,null,null,null, null, false, null,null,null,null,true,null,false,
                null,null,null,null,null,null);
        return acctD;
    }

    /**
     * Describe <code>getBusEntity</code> method here.
     *
     * @return a <code>BusEntityData</code> value
     */
    public BusEntityData getBusEntity() {
        return mBusEntity;
    }

    /**
     * <code>setBusEntity</code>
     */
    public void setBusEntity(BusEntityData v) {
        mBusEntity = v;
    }

    /**
     * Describe <code>getStoreAssoc</code> method here.
     *
     * @return a <code>BusEntityAs/PrimaryA
     * socData</code> value
     */
    public BusEntityAssocData getStoreAssoc() {
        return mStoreAssoc;
    }

    /**
     * Describe <code>getPrimaryAddress</code> method here.
     *
     * @return an <code>AddressData</code> value
     */
    public AddressData getPrimaryAddress() {
        return mPrimaryAddress;
    }

    /**
     * <code>setPrimaryAddress</code>
     *
     */
    public void setPrimaryAddress(AddressData v) {
        mPrimaryAddress = v;
    }

    /**
     * Describe <code>getPrimaryFax</code> method here.
     *
     * @return a <code>PhoneData</code> value
     */
    public PhoneData getPrimaryFax() {
        return mPrimaryFax;
    }

    public PhoneData getOrderFax() {
        return mOrderFax;
    }

    /**
     * Describe <code>getPrimaryPhone</code> method here.
     *
     * @return a <code>PhoneData</code> value
     */
    public PhoneData getPrimaryPhone() {
        return mPrimaryPhone;
    }

    public PhoneData getOrderPhone() {
        return mOrderPhone;
    }

    /**
     * Describe <code>getPrimaryEmail</code> method here.
     *
     * @return an <code>EmailData</code> value
     */
    public EmailData getPrimaryEmail() {
        return mPrimaryEmail;
    }

    /**
     * Describe <code>getBillingAddress</code> method here.
     *
     * @return an <code>AddressData</code> value
     */
    public AddressData getBillingAddress() {
        return mBillingAddress;

    }

    /**
     * Describe <code>setBillingAddress</code> method here.
     *
     */
    public void setBillingAddress(AddressData pValue) {
        this.mBillingAddress = pValue;

    }

    /**
     * Describe <code>getAccountType</code> method here.
     *
     * @return an <code>PropertyData</code> value
     */
    public PropertyData getAccountType() {
        return mAccountType;
    }

    /**
     * <code>setAccountType</code>
     *
     */
    public void setAccountType(PropertyData v) {
        mAccountType = v;
    }

    /**
     * Describe <code>getOrderMinimum</code> method here.
     *
     * @return an <code>PropertyData</code> value
     */
    public PropertyData getOrderMinimum() {
        return mOrderMinimum;
    }

    /**
     * Describe <code>getCreditLimit</code> method here.
     *
     * @return an <code>PropertyData</code> value
     */
    public PropertyData getCreditLimit() {
        return mCreditLimit;
    }

    /**
     * Describe <code>getCreditRating</code> method here.
     *
     * @return an <code>PropertyData</code> value
     */
    public PropertyData getCreditRating() {
        return mCreditRating;
    }

    /**
     * Describe <code>getCrcShop</code> method here.
     *
     * @return an <code>PropertyData</code> value
     */
    public PropertyData getCrcShop() {
        return mCrcShop;
    }

    /**
     * Describe <code>getComments</code> method here.
     *
     * @return an <code>PropertyData</code> value
     */
    public PropertyData getComments() {
        return mComments;
    }

    /**
     * Describe <code>getMiscProperties</code> method here.
     *
     * @return an <code>PropertyDataVector</code> value
     */
    public PropertyDataVector getMiscProperties() {
        return mMiscProperties;
    }

    /**
     * Describe <code>getOrderManagerEmails</code> method here.
     *
     * @return an <code>ArrayList</code> value
     */
    public ArrayList getOrderManagerEmails() {
        return mOrderManagerEmails;
    }

    /**
     * <code>setOrderManagerEmails</code>
     *
     */
    public void setOrderManagerEmails(ArrayList v) {
        mOrderManagerEmails = v;
    }


    /**
     *getter for property authorizedForResale
     */
    public boolean isAuthorizedForResale(){
        return mAuthorizedForResale;
    }

    /**
     *setter for property authorizedForResale
     */
    public void setAuthorizedForResale(boolean pVal){
        mAuthorizedForResale = pVal;
    }

    /**
     *getter for property EdiShipToPrefix
     */
    public String getEdiShipToPrefix(){
        return mEdiShipToPrefix;
    }

    /**
     *setter for property EdiShipToPrefix
     */
    public void setEdiShipToPrefix(String pVal){
        mEdiShipToPrefix = pVal;
    }

    /**
     *getter for property AccountParameters
     */
    public BusEntityParameterDataVector getAccountParameters(){
        return mAccountParameters;
    }

    /**
     *setter for property AccountParameters
     */
    public void setAccountParameters(BusEntityParameterDataVector pVal){
        mAccountParameters = pVal;
    }

    /**
     *setter for property AccountParameter
     */
    public void setAccountParameter(BusEntityParameterData pVal){
        //Find the parameter
        if(pVal.getEffDate()==null) {
            pVal.setEffDate(Utility.MIN_DATE);
        }
        if(mAccountParameters==null) {
            mAccountParameters = new BusEntityParameterDataVector();
        }
        if(pVal.getExpDate()==null) {
            boolean insertFl = true;
            for(Iterator iter=mAccountParameters.iterator();iter.hasNext();) {
                BusEntityParameterData bepD = (BusEntityParameterData) iter.next();
                String name = bepD.getName();
                if(name.equals(pVal.getName())) {
                    Date expDate = bepD.getExpDate();
                    if(expDate==null) {
                        String value = bepD.getValue();
                        if(value==null) value = "";
                        if(value.equals(pVal.getValue())) {
                            insertFl = false;
                            break;
                        } else {
                            bepD.setExpDate(pVal.getEffDate());
                        }
                    }
                }
            }
            if(insertFl) {
                mAccountParameters.add(pVal);
            }
        } else{ //old record correction
            Date effDate = pVal.getEffDate();
            Date expDate = pVal.getExpDate();
            for(Iterator iter=mAccountParameters.iterator();iter.hasNext();) {
                BusEntityParameterData bepD = (BusEntityParameterData) iter.next();
                String name = bepD.getName();
                if(name.equals(pVal.getName())) {
                    Date effD = bepD.getEffDate();
                    if(effD==null) effD = Utility.MIN_DATE;
                    Date expD = bepD.getExpDate();
                    if(effD.equals(effDate) &&
                            ((expDate==null && expD==null) ||
                            (expDate!=null && expD!=null && expDate.equals(expD)))) {
                        bepD.setValue(pVal.getValue());
                        bepD.setModBy(pVal.getModBy());
                        break;
                    }
                }
            }
        }
    }

    public void removeAccountParameter(int pBusEntityParameterId){
        //Find the parameter
        for(Iterator iter=mAccountParameters.iterator();iter.hasNext();) {
            BusEntityParameterData bepD = (BusEntityParameterData) iter.next();
            int id = bepD.getBusEntityParameterId();
            if(id==pBusEntityParameterId) {
                Date effDate = bepD.getEffDate();
                iter.remove();
                if(effDate!=null) {
                    for(Iterator iter1=mAccountParameters.iterator();iter1.hasNext();) {
                        BusEntityParameterData bepD1 = (BusEntityParameterData) iter1.next();
                        if(bepD1.getEffDate()==null || bepD1.getEffDate()==null) {
                            //could hapeen only by program error
                            iter1.remove();
                            continue;
                        }
                        if(bepD1.getExpDate().equals(effDate)) {
                            bepD1.setExpDate(null);
                        }
                    }
                }
                break;
            }
        }
    }

    public BusEntityParameterData getAccountParameterActual(String pName){
        BusEntityParameterData parameter = null;
        if(mAccountParameters==null) {
            return parameter;
        }
        for(Iterator iter=mAccountParameters.iterator();iter.hasNext();) {
            BusEntityParameterData bepD = (BusEntityParameterData) iter.next();
            Date expDate = bepD.getExpDate();
            if(pName.equals(bepD.getName()) && expDate==null) {
                parameter = bepD;
            }
        }
        return parameter;
    }

    public BusEntityParameterDataVector getAccountParameterHistory(String pName){
        BusEntityParameterDataVector parameterHistory =
                new BusEntityParameterDataVector();
        if(mAccountParameters==null) {
            return parameterHistory;
        }
        for(Iterator iter=mAccountParameters.iterator();iter.hasNext();) {
            BusEntityParameterData bepD = (BusEntityParameterData) iter.next();
            if(pName.equals(bepD.getName())) {
                parameterHistory.add(bepD);
            }
        }
        Date minDate = Utility.MIN_DATE;
        Object[] parameterHistoryA = parameterHistory.toArray();
        for(int ii=0; ii<parameterHistoryA.length-1; ii++ ) {
            boolean exitFl = true;
            for(int jj=0; jj<parameterHistoryA.length-1-ii; jj++ ) {
                BusEntityParameterData bepD1 = (BusEntityParameterData) parameterHistoryA[jj];
                BusEntityParameterData bepD2 = (BusEntityParameterData) parameterHistoryA[jj+1];
                Date effDate1 = bepD1.getEffDate();
                if(effDate1==null) effDate1 = minDate;
                Date effDate2 = bepD2.getEffDate();
                if(effDate2== null) effDate2 = minDate;
                int comp = effDate1.compareTo(effDate2);
                if(comp>0) {
                    parameterHistoryA[jj] = bepD2;
                    parameterHistoryA[jj+1] = bepD1;
                    exitFl = false;
                }
            }
            if(exitFl) {
                break;
            }
        }
        parameterHistory = new BusEntityParameterDataVector();
        for(int ii=0; ii<parameterHistoryA.length; ii++ ) {
            BusEntityParameterData bepD = (BusEntityParameterData) parameterHistoryA[ii];
            parameterHistory.add(bepD);
        }
        return parameterHistory;
    }


    /**
     *getter for property resaleAccountErpNumber
     */
    public PropertyData getResaleAccountErpNumber(){
        return mResaleAccountErpNumber;
    }

    /**
     *setter for property resaleAccountErpNumber
     */
    public void setResaleAccountErpNumber(PropertyData pVal){
        mResaleAccountErpNumber = pVal;
    }



    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AccountData object
     */
    public String toString() {
        return "[" + "BusEntity=" + mBusEntity +
                ", StoreAssoc=" + mStoreAssoc +
                ", PrimaryAddress=" + mPrimaryAddress +
                ", PrimaryPhone=" + mPrimaryPhone +
                ", PrimaryFax=" + mPrimaryFax +
                ", PrimaryEmail=" + mPrimaryEmail +
                ", AccountType=" + mAccountType +
                ", OrderMinimum=" + mOrderMinimum +
                ", CreditLimit=" + mCreditLimit +
                ", CreditRating=" + mCreditRating +
                ", CrcShop=" + mCrcShop +
                ", Comments=" + mComments +
                ", MiscProperties=" + mMiscProperties +
                ", OrderManagerEmails=" + mOrderManagerEmails +
                ", EdiShipToPrefixm "+mEdiShipToPrefix+
                ", TargetMargin "+targetMargin+
                ", AccountFolder "+mAccountFolder+
                ", CustomerServiceEmail="+mCustomerServiceEmail +
                ", DefaultEmail="+mDefaultEmail +
                ", GL Transformation=" + glTransformationType +
                ", Distributor Acct Ref Num=" +distributorAccountRefNum +
                "]";
    }

    /**
     *Gets on of the "Extra" or misc properties for this account
     */
    public String getPropertyValue(String pPropName) {
        for ( int i = 0; mMiscProperties != null &&
                i < mMiscProperties.size(); i++ ) {
            PropertyData prop = (PropertyData)mMiscProperties.get(i);
            if ( prop.getShortDesc() == null ) {
                continue;
            }
            if ( prop.getShortDesc().equals(pPropName)) {
                return prop.getValue();
            }
        }
        return "";
    }

    public String getRushOrderCharge() {
        return getPropertyValue
                (RefCodeNames.PROPERTY_TYPE_CD.RUSH_ORDER_CHARGE);
    }

    public String getBudgetThresholdType() {
        return getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_TYPE);
    }

    /**
     *Sets on of the "Extra" or misc properties for this account
     */
    public void setPropertyValue(String pPropName, String pValue, String pUserName){
        if(mMiscProperties == null){
            mMiscProperties = new PropertyDataVector();
        }
        Iterator it = mMiscProperties.iterator();
        while(it.hasNext()){
            PropertyData prop = (PropertyData) it.next();
            if(prop.getShortDesc().equals(pPropName)){
                prop.setValue(pValue);
                return;
            }
        }
        PropertyData newProp = PropertyData.createValue();
        newProp.setAddBy(pUserName);
        newProp.setModBy(pUserName);
        newProp.setBusEntityId(getBusEntity().getBusEntityId());
        newProp.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        newProp.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
        newProp.setShortDesc(pPropName);
        newProp.setValue(pValue);
        mMiscProperties.add(newProp);
    }

    /** Getter for property targetMargin.
     * @return Value of property targetMargin.
     *
     */
    public BigDecimal getTargetMargin() {
        return this.targetMargin;
    }

    /** Setter for property targetMargin.
     * @param targetMargin New value of property targetMargin.
     *
     */
    public void setTargetMargin(BigDecimal targetMargin) {
        this.targetMargin = targetMargin;
    }

    /** Getter for property dataFieldProperties.
     * @return Value of property dataFieldProperties.
     *
     */
    public List getDataFieldProperties() {
        return this.dataFieldProperties;
    }

    /** Setter for property dataFieldProperties.
     * @param dataFieldProperties New value of property dataFieldProperties.
     *
     */
    public void setDataFieldProperties(List dataFieldProperties) {
        this.dataFieldProperties = dataFieldProperties;
    }

    /** Getter for property dataFieldPropertiesRuntime.
     * @return Value of property dataFieldPropertiesRuntime.
     *
     */
    public PropertyDataVector getDataFieldPropertiesRuntime() {
        return this.dataFieldPropertiesRuntime;
    }

    /** Setter for property dataFieldPropertiesRuntime.
     * @param dataFieldPropertiesRuntime New value of property dataFieldPropertiesRuntime.
     *
     */
    public void setDataFieldPropertiesRuntime(PropertyDataVector dataFieldPropertiesRuntime) {
        this.dataFieldPropertiesRuntime = dataFieldPropertiesRuntime;
    }

    /**
     * Getter for property customerSystemApprovalCd.
     * @return Value of property customerSystemApprovalCd.
     */
    public String getCustomerSystemApprovalCd() {
        return this.customerSystemApprovalCd;
    }

    /**
     * Setter for property customerSystemApprovalCd.
     * @param customerSystemApprovalCd New value of property customerSystemApprovalCd.
     */
    public void setCustomerSystemApprovalCd(String customerSystemApprovalCd) {
        this.customerSystemApprovalCd = customerSystemApprovalCd;
    }

    /**
	 * @return the trackPunchoutOrderMessages
	 */
	public String getTrackPunchoutOrderMessages() {
		return trackPunchoutOrderMessages;
	}

	/**
	 * @param trackPunchoutOrderMessages the trackPunchoutOrderMessages to set
	 */
	public void setTrackPunchoutOrderMessages(String trackPunchoutOrderMessages) {
		this.trackPunchoutOrderMessages = trackPunchoutOrderMessages;
	}

	private ArrayList mInventoryItems = null;
    public void setInventoryItemsData( ArrayList v) {
        mInventoryItems = v;
    }
    public ArrayList getInventoryItemsData() {
        return mInventoryItems;
    }
    public int getNumberOfInventoryItems() {
        if ( null != mInventoryItems ) return mInventoryItems.size();
        return 0;
    }

    private boolean makeShipToBillTo;

    /**
     * Holds value of property freightChargeType.
     */
    private String freightChargeType;

    public boolean isMakeShipToBillTo(){
        return makeShipToBillTo;
    }
    public void setMakeShipToBillTo(boolean pMakeShipToBillTo){
        makeShipToBillTo = pMakeShipToBillTo;
    }

    /**
     * Getter for property freightChargeType.
     * @return Value of property freightChargeType.
     */
    public String getFreightChargeType() {
        return this.freightChargeType;
    }

    /**
     * Setter for property freightChargeType.
     * @param freightChargeType New value of property freightChargeType.
     */
    public void setFreightChargeType(String freightChargeType) {
        this.freightChargeType = freightChargeType;
    }

    private PropertyData mOrderGuideNote;
    public PropertyData getOrderGuideNote() {
        if ( null == this.mOrderGuideNote) {
            this.mOrderGuideNote = PropertyData.createValue();
        }
        return this.mOrderGuideNote;
    }
    public void  setOrderGuideNote(PropertyData pVal) {
        this.mOrderGuideNote = pVal;
    }

    private PropertyData mSkuTag;

    /**
     * Holds value of property purchaseOrderAccountName.
     */
    private String purchaseOrderAccountName;

    /**
     * Holds value of property budgetTypeCd.
     */
    private String budgetTypeCd;

    /**
     * Holds value of property glTransformtionType.
     */
    private String glTransformationType;

    /**
     * Holds value of property distributorAccountRefNum.
     */
    private String distributorAccountRefNum;


    /**
     * Holds value of property customerRequestPoAllowed.
     */
    private boolean customerRequestPoAllowed;

    /**
     * Holds value of property faxBackConfirm.
     */
    private PhoneData faxBackConfirm;
    public PropertyData getSkuTag() {
        if ( null == this.mSkuTag) {
            this.mSkuTag = PropertyData.createValue();
        }
        return this.mSkuTag;
    }
    public void  setSkuTag(PropertyData pVal) {
        this.mSkuTag = pVal;
    }

    public List getRuntimeDisplayOrderItemActionTypes() {
        return mRuntimeDisplayOrderItemActionTypes;
    }

    public void setRuntimeDisplayOrderItemActionTypes(List pRuntimeDisplayOrderItemActionTypes) {
        this.mRuntimeDisplayOrderItemActionTypes = pRuntimeDisplayOrderItemActionTypes;
    }

    /**
     * Getter for property purchaseOrderAccountName.
     * @return Value of property purchaseOrderAccountName.
     */
    public String getPurchaseOrderAccountName() {

        return this.purchaseOrderAccountName;
    }

    /**
     * Setter for property purchaseOrderAccountName.
     * @param purchaseOrderAccountName New value of property purchaseOrderAccountName.
     */
    public void setPurchaseOrderAccountName(String purchaseOrderAccountName) {

        this.purchaseOrderAccountName = purchaseOrderAccountName;
    }

    /**
     * Getter for property budgetTypeCd.
     * @return Value of property budgetTypeCd.
     */
    public String getBudgetTypeCd() {

        return this.budgetTypeCd;
    }

    /**
     * Setter for property budgetTypeCd.
     * @param budgetTypeCd New value of property budgetTypeCd.
     */
    public void setBudgetTypeCd(String budgetTypeCd) {

        this.budgetTypeCd = budgetTypeCd;
    }

    /**
     * Getter for property glTransformationType.
     * @return Value of property glTransformationType.
     */
    public String getGlTransformationType() {

        return this.glTransformationType;
    }

    /**
     * Setter for property glTransformationType.
     * @param glTransformationType New value of property glTransformationType.
     */
    public void setGlTransformationType(String glTransformationType) {

        this.glTransformationType = glTransformationType;
    }

    /**
     * Getter for property distributorAccountRefNum.
     * @return Value of property distributorAccountRefNum.
     */
    public String getDistributorAccountRefNum() {

        return this.distributorAccountRefNum;
    }

    /**
     * Setter for property distributorAccountRefNum.
     * @param distributorAccountRefNum New value of property distributorAccountRefNum.
     */
    public void setDistributorAccountRefNum(String distributorAccountRefNum) {

        this.distributorAccountRefNum = distributorAccountRefNum;
    }

    /**
     * Getter for property customerRequestPoAllowed.
     * @return Value of property customerRequestPoAllowed.
     */
    public boolean isCustomerRequestPoAllowed() {

        return this.customerRequestPoAllowed;
    }

    /**
     * Setter for property customerRequestPoAllowed.
     * @param customerRequestPoAllowed New value of property customerRequestPoAllowed.
     */
    public void setCustomerRequestPoAllowed(boolean customerRequestPoAllowed) {

        this.customerRequestPoAllowed = customerRequestPoAllowed;
    }

    /**
     * Getter for property faxBackConfirm.
     * @return Value of property faxBackConfirm.
     */
    public PhoneData getFaxBackConfirm() {

        return this.faxBackConfirm;
    }

    /**
     * Setter for property faxBackConfirm.
     * @param faxBackConfirm New value of property faxBackConfirm.
     */
    public void setFaxBackConfirm(PhoneData faxBackConfirm) {

        this.faxBackConfirm = faxBackConfirm;
    }

    /**
     * Holds value of property taxableIndicator.
     */
    private boolean taxableIndicator;

    /**
     * Getter for property taxableIndicator.
     * @return Value of property taxableIndicator.
     */
    public boolean isTaxableIndicator() {

        return this.taxableIndicator;
    }

    /**
     * Setter for property taxableIndicator.
     * @param taxableIndicator New value of property taxableIndicator.
     */
    public void setTaxableIndicator(boolean taxableIndicator) {

        this.taxableIndicator = taxableIndicator;
    }

    ShoppingOptionsDataVector mShoppingOptionsDataVector
            = new ShoppingOptionsDataVector();
    public ShoppingOptionsDataVector getCheckoutOptions() {
        return mShoppingOptionsDataVector;
    }
    public void setCheckoutOptions(ShoppingOptionsDataVector v) {
        mShoppingOptionsDataVector=v;
    }

    public ShoppingOptionsDataVector getCheckoutOptions
            (String pFname, String pFvalue) {

        ShoppingOptionsDataVector v = new ShoppingOptionsDataVector();
        for( int i = 0; i < mShoppingOptionsDataVector.size(); i++ ) {
            ShoppingOptionsData sod = (ShoppingOptionsData)
            mShoppingOptionsDataVector.get(i) ;
            if ( pFname.equals(sod.getFieldName()) &&
                    pFvalue.equals(sod.getFieldValue())) {
                v.add(sod);
            }
        }
        return v;
    }

    private ShoppingOptConfigData mShoppingOptConfigData;
    public ShoppingOptConfigData getCheckoutOptConfig() {
        return mShoppingOptConfigData;
    }
    public void setCheckoutOptConfig(ShoppingOptConfigData v) {
        mShoppingOptConfigData=v;
    }
    private ShoppingControlDataVector mShoppingControls;
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

    public int getAccountId() {
      return   mBusEntity.getBusEntityId();
    }


  private FiscalCalenderViewVector mFiscalCalenders = null;

  public FiscalCalenderViewVector getFiscalCalenders() {
    return mFiscalCalenders;
  }

  public void setFiscalCalenders(FiscalCalenderViewVector v) {
    mFiscalCalenders = v;
  }


  private int mHowToCleanTopicId = 0;
  private NoteViewVector mNoteList = null;
  private NoteJoinViewVector mNotes = null;

  public int getHowToCleanTopicId(){return mHowToCleanTopicId;}
  public void setHowToCleanTopicId(int pValue) {mHowToCleanTopicId = pValue;}

  public NoteViewVector getNoteList(){return mNoteList;}
  public void setNoteList(NoteViewVector pValue) {mNoteList = pValue;}

    public boolean hasNotes() {
  return null != mNoteList &&
      mNoteList.size() > 0;
    }

  public NoteJoinView getNote( int pNoteId ){
      NoteJoinView noteJVw = null;
      for ( int i = 0; null != mNotes && i < mNotes.size(); i++) {
    noteJVw = (NoteJoinView)mNotes.get(i);
    if ( pNoteId == noteJVw.getNote().getNoteId() ) {
        return noteJVw;
    }
      }
      return null;
  }

  public void addNote(NoteJoinView pValue) {
      if ( null == mNotes ) mNotes = new NoteJoinViewVector();
      mNotes.add(pValue);
  }

    private ArrayList mAccountBillTos = null;
    public boolean hasBillTos() {
  return (getAccountBillTos().size() > 0);
    }

    public ArrayList getAccountBillTos() {
  if ( null == mAccountBillTos ) {
      mAccountBillTos = new ArrayList();
  }
  return mAccountBillTos;
    }
    public void setAccountBillTos(ArrayList v) {
  mAccountBillTos = v;
    }


public boolean isShowSPL() {
  String showSPLs = getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SPL);
  return showSPLs != null && showSPLs.length() > 0 && "T".equalsIgnoreCase(showSPLs.substring(0, 1));
}

public boolean isHideItemMfg() {
   String hideItemfg = getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.HIDE_ITEM_MFG);
   return hideItemfg != null && hideItemfg.length() > 0 && "T".equalsIgnoreCase(hideItemfg.substring(0, 1));
}

public boolean hideShippingComments() {
  String hideShippignComments = getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.HIDE_SHIPPING_COMMENTS);
  return hideShippignComments != null && hideShippignComments.length() > 0 && "T".equalsIgnoreCase(hideShippignComments.substring(0, 1));
}

public boolean isShowReBillOrder() {
    String showReBillOrder = getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_REBILL_ORDER);
    return Utility.isTrue(showReBillOrder);
}

    public String getDistrPOType() {
        return distrPOType;
    }

    public void setDistrPOType(String distrPOType) {
        this.distrPOType = distrPOType;
    }

    /**
     * Describe <code>getWorkflowEmail</code> method here.
     *
     * @return an <code>EmailData</code> value
     */
    /*
    public EmailData getWorkflowEmail() {
        return mWorkflowEmail;
    }
    */


    public EmailData getCustomerServiceEmail() {
        if(mCustomerServiceEmail==null) {
            mCustomerServiceEmail = EmailData.createValue();
        }
        return mCustomerServiceEmail;
    }

    public void setCustomerServiceEmail(EmailData pCustomerServiceEmail) {
        mCustomerServiceEmail = pCustomerServiceEmail;
    }

    public EmailData getDefaultEmail() {
    	if (mDefaultEmail == null) {
    		mDefaultEmail = EmailData.createValue();
    	}
    	return mDefaultEmail;
    }
    
    public void setDefaultEmail(EmailData pDefaultEmail) {
    	this.mDefaultEmail = pDefaultEmail;
    }

    HashMap productViewDefinitionsViewMap;
    /**
     * Returns the product view definitions for the specified view code.
     * @see RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD
     */
	public ProductViewDefDataVector getProductViewDefinitions(String filter) {
		if(productViewDefinitionsViewMap == null){
			//lazy load...initialize the map on first use
                        productViewDefinitionsViewMap = new HashMap();
			if(productViewDefinitions == null){
				productViewDefinitionsViewMap = new HashMap();
			}else{
				Iterator it = productViewDefinitions.iterator();
				while(it.hasNext()){
					ProductViewDefData def = (ProductViewDefData) it.next();
					ProductViewDefDataVector deflist = (ProductViewDefDataVector) productViewDefinitionsViewMap.get(def.getProductViewCd());
					if(deflist == null){
						deflist = new ProductViewDefDataVector();
						productViewDefinitionsViewMap.put(def.getProductViewCd(), deflist);
					}
					deflist.add(def);
				}
			}
		}
		return (ProductViewDefDataVector) productViewDefinitionsViewMap.get(filter);
	}

	public void setProductViewDefinitions(ProductViewDefDataVector productViewDefinitions) {
		this.productViewDefinitions = productViewDefinitions;
	}

    public CatalogData getAccountCatalog() {
        return mAccountCatalog;
    }

    public void setAccountCatalog(CatalogData mAccountCatalog) {
        this.mAccountCatalog = mAccountCatalog;
    }

    public int getAccountCatalogId() {
        if (mAccountCatalog != null) {
            return mAccountCatalog.getCatalogId();
        } else {
            return 0;
        }
    }

    public boolean isSupportsBudget() {
      String supportBudget = getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SUPPORTS_BUDGET);
      return Utility.isTrue(supportBudget);
    }


}

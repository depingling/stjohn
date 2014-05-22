/**
 * Title:        CustAcctMgtReportForm
 * Description:  This is the Struts ActionForm class for 
 * order operation console page.
 * Purpose:      Strut support to search for orders.      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       liang
 */

package com.cleanwise.view.forms;

import java.util.Date;
import java.util.ArrayList;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

/**
 * Form bean for the user manager page. 
 *
 * @author Tim Besser
 */
public final class ServiceProviderMgtReportForm extends StorePortalBaseForm {

    private String mReportTypeCd = new String("");
    private String mReportTypeDesc = new String("");
    private int mAccountId;
    private CatalogDataVector mCatalogList = new CatalogDataVector();
    private ContractDataVector mContractList = new ContractDataVector();
    private String mBeginMonth = new String("1");
    private String mBeginYear;
    private String mEndMonth = new String("12");
    private String mEndYear;
    private String mBeginDateS = new String("");
    private String mEndDateS = new String("");
    private Date mBeginDate;
    private Date mEndDate;
    private String mBeginDateBeforeS = new String("");
    private String mEndDateBeforeS = new String("");
    private Date mBeginDateBefore;
    private Date mEndDateBefore;
    private ArrayList mSiteIdList;
    private ArrayList mCategoryList;
    private ArrayList mItemIdList;
    private CustomerReportResultDataVector mResultList = new CustomerReportResultDataVector();
    private String[] mSelectedSites = new String[0];
    private SiteDataVector mSelectedSiteList = new SiteDataVector();
    private CatalogCategoryDataVector mTopCategoryList = new CatalogCategoryDataVector();    
    private ArrayList mChildCategoryIdList = new ArrayList();
    private ArrayList mChildCategoryListList = new ArrayList();
    private ProductDataVector mProductList = new ProductDataVector();
    private boolean mSelectLocationFlag = false;
    private boolean mMultipleLocSelectFlag = false;
    private String mSelectedTopCategoryId = new String("");
    private String[] mSelectedItems = new String[0];

    private BigDecimal mTotalAmount = new BigDecimal(0);
    private BigDecimal mTotalAmountBefore = new BigDecimal(0);
    private BigDecimal mTotalAmountChange = new BigDecimal(0);
    private int mTotalOrderNum = 0;
    private int mTotalOrderNumBefore = 0;
    private int mTotalOrderNumChange = 0;
    private BigDecimal mAvgOrderSize = new BigDecimal(0);
    private BigDecimal mAvgOrderSizeBefore = new BigDecimal(0);
    private BigDecimal mAvgOrderSizeChange = new BigDecimal(0);
    private ArrayList mPeriodList = new ArrayList();
    
    
    private int mFinalCategoryId = 0;
    private String mCategoryTreeDesc = new String("");
    private String mTopCategoryName = new String("");
    private String mFinalCategoryName = new String("");
    
    private GenericReportControlViewVector mReportControls = new GenericReportControlViewVector();
    private GenericReportResultViewVector mReportResults = new GenericReportResultViewVector();
    private int mGenericReportPageNum = 0;
    

    private BudgetSpendViewVector SpendingData;

    private int mReportMaxRowCount;
    private String[] mSelectedPeriods=new String[0];
    
    /**
     * Get the value of SpendingData.
     * @return value of SpendingData.
     */
    public BudgetSpendViewVector getSpendingData() {
	return SpendingData;
    }
    
    /**
     * Set the value of SpendingData.
     * @param v  Value to assign to SpendingData.
     */
    public void setSpendingData(BudgetSpendViewVector  v) {
	this.SpendingData = v;
    }
    
    /**
     * Gets the value of mAccountId
     *
     * @return the value of mAccountId
     */
    public int getAccountId() {
	return this.mAccountId;
    }

    /**
     * Sets the value of mAccountId
     *
     * @param argAccountId Value to assign to this.mAccountId
     */
    public void setAccountId(int argAccountId){
	this.mAccountId = argAccountId;
    }



    /**
     * Gets the value of mCatalogList
     *
     * @return the value of mCatalogList
     */
    public CatalogDataVector getCatalogList() {
        if (null == this.mCatalogList) {
            this.mCatalogList = new CatalogDataVector();
        }
	return this.mCatalogList;
    }

    /**
     * Sets the value of mCatalogList
     *
     * @param arg Value to assign to this.mCatalogList
     */
    public void setCatalogList(CatalogDataVector arg){
	this.mCatalogList = arg;
    }

    /**
     * Gets the value of mContractList
     *
     * @return the value of mContractList
     */
    public ContractDataVector getContractList() {
        if (null == this.mContractList) {
            this.mContractList = new ContractDataVector();
        }
	return this.mContractList;
    }

    /**
     * Sets the value of mContractList
     *
     * @param arg Value to assign to this.mContractList
     */
    public void setContractList(ContractDataVector arg){
	this.mContractList = arg;
    }
    
    
    /**
     * Gets the value of mBeginMonth
     *
     * @return the value of mBeginMonth
     */
    public String getBeginMonth() {
	return this.mBeginMonth;
    }

    /**
     * Sets the value of mBeginMonth
     *
     * @param argBeginMonth Value to assign to this.mBeginMonth
     */
    public void setBeginMonth(String argBeginMonth){
	this.mBeginMonth = argBeginMonth;
    }


    /**
     * Gets the value of mBeginYear
     *
     * @return the value of mBeginYear
     */
    public String getBeginYear() {
	return this.mBeginYear;
    }

    /**
     * Sets the value of mBeginYear
     *
     * @param argBeginYear Value to assign to this.mBeginYear
     */
    public void setBeginYear(String argBeginYear){
	this.mBeginYear = argBeginYear;
    }


    /**
     * Gets the value of mEndMonth
     *
     * @return the value of mEndMonth
     */
    public String getEndMonth() {
	return this.mEndMonth;
    }

    /**
     * Sets the value of mEndMonth
     *
     * @param argEndMonth Value to assign to this.mEndMonth
     */
    public void setEndMonth(String argEndMonth){
	this.mEndMonth = argEndMonth;
    }


    /**
     * Gets the value of mEndYear
     *
     * @return the value of mEndYear
     */
    public String getEndYear() {
	return this.mEndYear;
    }

    /**
     * Sets the value of mEndYear
     *
     * @param argEndYear Value to assign to this.mEndYear
     */
    public void setEndYear(String argEndYear){
	this.mEndYear = argEndYear;
    }
    
    
    /**
     * Gets the value of mBeginDateS
     *
     * @return the value of mBeginDateS
     */
    public String getBeginDateS() {
	return this.mBeginDateS;
    }

    /**
     * Sets the value of mBeginDateS
     *
     * @param argBeginDate Value to assign to this.mBeginDateS
     */
    public void setBeginDateS(String argBeginDate){
	this.mBeginDateS = argBeginDate;
    }

    /**
     * Gets the value of mBeginDate
     *
     * @return the value of mBeginDate
     */
    public Date getBeginDate() {
	return this.mBeginDate;
    }

    /**
     * Sets the value of mBeginDate
     *
     * @param argBeginDate Value to assign to this.mBeginDate
     */
    public void setBeginDate(Date argBeginDate){
	this.mBeginDate = argBeginDate;
    }


    /**
     * Gets the value of mBeginDateBeforeS
     *
     * @return the value of mBeginDateBeforeS
     */
    public String getBeginDateBeforeS() {
	return this.mBeginDateBeforeS;
    }

    /**
     * Sets the value of mBeginDateBeforeS
     *
     * @param arg Value to assign to this.mBeginDateBeforeS
     */
    public void setBeginDateBeforeS(String arg){
	this.mBeginDateBeforeS = arg;
    }

    
    /**
     * Gets the value of mBeginDateBefore
     *
     * @return the value of mBeginDateBefore
     */
    public Date getBeginDateBefore() {
	return this.mBeginDateBefore;
    }

    /**
     * Sets the value of mBeginDateBefore
     *
     * @param arg Value to assign to this.mBeginDateBefore
     */
    public void setBeginDateBefore(Date arg){
	this.mBeginDateBefore = arg;
    }
    
    
    /**
     * Gets the value of mEndDateS
     *
     * @return the value of mEndDateS
     */
    public String getEndDateS() {
	return this.mEndDateS;
    }

    /**
     * Sets the value of mEndDateS
     *
     * @param argEndDate Value to assign to this.mEndDateS
     */
    public void setEndDateS(String argEndDate){
	this.mEndDateS = argEndDate;
    }

    /**
     * Gets the value of mEndDate
     *
     * @return the value of mEndDate
     */
    public Date getEndDate() {
	return this.mEndDate;
    }

    /**
     * Sets the value of mEndDate
     *
     * @param argEndDate Value to assign to this.mEndDate
     */
    public void setEndDate(Date argEndDate){
	this.mEndDate = argEndDate;
    }


    /**
     * Gets the value of mEndDateBeforeS
     *
     * @return the value of mEndDateBeforeS
     */
    public String getEndDateBeforeS() {
	return this.mEndDateBeforeS;
    }

    /**
     * Sets the value of mEndDateBeforeS
     *
     * @param arg Value to assign to this.mEndDateBeforeS
     */
    public void setEndDateBeforeS(String arg){
	this.mEndDateBeforeS = arg;
    }


    /**
     * Gets the value of mEndDateBefore
     *
     * @return the value of mEndDateBefore
     */
    public Date getEndDateBefore() {
	return this.mEndDateBefore;
    }

    /**
     * Sets the value of mEndDateBefore
     *
     * @param arg Value to assign to this.mEndDateBefore
     */
    public void setEndDateBefore(Date arg){
	this.mEndDateBefore = arg;
    }

    
    /**
     * Gets the value of mSiteIdList
     *
     * @return the value of mSiteIdList
     */
    public ArrayList getSiteIdList() {
        if (null == this.mSiteIdList) {
            this.mSiteIdList = new ArrayList();
        }
	return this.mSiteIdList;
    }

    /**
     * Sets the value of mSiteIdList
     *
     * @param argSiteIdList Value to assign to this.mSiteIdList
     */
    public void setSiteIdList(ArrayList argSiteIdList){
	this.mSiteIdList = argSiteIdList;
    }

    /**
     * Gets the value of mCategoryList
     *
     * @return the value of mCategoryList
     */
    public ArrayList getCategoryList() {
        if (null == this.mCategoryList) {
            this.mCategoryList = new ArrayList();
        }
	return this.mCategoryList;
    }

    /**
     * Sets the value of mCategoryList
     *
     * @param argCategoryList Value to assign to this.mCategoryList
     */
    public void setCategoryList(ArrayList argCategoryList){
	this.mCategoryList = argCategoryList;
    }

    /**
     * Gets the value of mItemIdList
     *
     * @return the value of mItemIdList
     */
    public ArrayList getItemIdList() {
        if (null == this.mItemIdList) {
            this.mItemIdList = new ArrayList();
        }
	return this.mItemIdList;
    }

    /**
     * Sets the value of mItemIdList
     *
     * @param argItemIdList Value to assign to this.mItemIdList
     */
    public void setItemIdList(ArrayList argItemIdList){
	this.mItemIdList = argItemIdList;
    }

    /**
     * Gets the value of mResultList
     *
     * @return the value of mResultList
     */
    public CustomerReportResultDataVector getResultList() {
        if (null == this.mResultList) {
            this.mResultList = new CustomerReportResultDataVector();
        }
	return this.mResultList;
    }

    /**
     * Sets the value of mResultList
     *
     * @param argResultList Value to assign to this.mResultList
     */
    public void setResultList(CustomerReportResultDataVector argResultList){
	this.mResultList = argResultList;
    }

    /**
     * Gets the value of mReportTypeCd
     *
     * @return the value of mReportTypeCd
     */
    public String getReportTypeCd() {
	return this.mReportTypeCd;
    }

    /**
     * Sets the value of mReportTypeCd
     *
     * @param argReportTypeCd Value to assign to this.mReportTypeCd
     */
    public void setReportTypeCd(String argReportTypeCd){
	this.mReportTypeCd = argReportTypeCd;
    }


    /**
     * Gets the value of mReportTypeDesc
     *
     * @return the value of mReportTypeDesc
     */
    public String getReportTypeDesc() {
	return this.mReportTypeDesc;
    }

    /**
     * Sets the value of mReportTypeDesc
     *
     * @param argReportTypeDesc Value to assign to this.mReportTypeDesc
     */
    public void setReportTypeDesc(String argReportTypeDesc){
	this.mReportTypeDesc = argReportTypeDesc;
    }


    /**
     * Gets the value of mSelectLocationFlag
     *
     * @return the value of mSelectLocationFlag
     */
    public boolean getSelectLocationFlag() {
	return this.mSelectLocationFlag;
    }

    /**
     * Sets the value of mSelectLocationFlag
     *
     * @param argSelectLocationFlag Value to assign to this.mSelectLocationFlag
     */
    public void setSelectLocationFlag(boolean argSelectLocationFlag){
	this.mSelectLocationFlag = argSelectLocationFlag;
    }

    
    /**
     * Gets the value of mSelectLocationFlag
     *
     * @return the value of mMultipleLocSelectFlag
     */
    public boolean getMultipleLocSelectFlag() {
	return this.mMultipleLocSelectFlag;
    }

    /**
     * Sets the value of mMultipleLocSelectFlag
     *
     * @param arg Value to assign to this.mMultipleLocSelectFlag
     */
    public void setMultipleLocSelectFlag(boolean arg){
	this.mMultipleLocSelectFlag = arg;
    }

    
    
    public String [] getSelectedSites() {
        return mSelectedSites;
    }

    public void setSelectedSites(String[] pSiteIds) {
        mSelectedSites = pSiteIds;
    }

    
    /**
     * Gets the value of mSelectedSiteList
     *
     * @return the value of mSelectedSiteList
     */
    public SiteDataVector getSelectedSiteList() {
        if (null == this.mSelectedSiteList) {
            this.mSelectedSiteList = new SiteDataVector();
        }
	return this.mSelectedSiteList;
    }

    /**
     * Sets the value of mSelectedSiteList
     *
     * @param arg Value to assign to this.mSelectedSiteList
     */
    public void setSelectedSiteList(SiteDataVector arg){
	this.mSelectedSiteList = arg;
    }

    /**
     * Gets the value of mTopCategoryList
     *
     * @return the value of mTopCategoryList
     */
    public CatalogCategoryDataVector getTopCategoryList() {
        if (null == this.mTopCategoryList) {
            this.mTopCategoryList = new CatalogCategoryDataVector();
        }
	return this.mTopCategoryList;
    }

    /**
     * Sets the value of mTopCategoryList
     *
     * @param arg Value to assign to this.mTopCategoryList
     */
    public void setTopCategoryList(CatalogCategoryDataVector arg){
	this.mTopCategoryList = arg;
    }


    /**
     * Gets the value of mChildCategoryIdList
     *
     * @return the value of mChildCategoryIdList
     */
    public ArrayList getChildCategoryIdList() {
        if (null == this.mChildCategoryIdList) {
            this.mChildCategoryIdList = new ArrayList();
        }
	return this.mChildCategoryIdList;
    }

    /**
     * Sets the value of mChildCategoryIdList
     *
     * @param arg Value to assign to this.mChildCategoryIdList
     */
    public void setChildCategoryIdList(ArrayList arg){
	this.mChildCategoryIdList = arg;
    }


    public String getChildCategoryId(int idx) {

        if (mChildCategoryIdList == null) {
            mChildCategoryIdList = new ArrayList();
        }
        while (idx >= mChildCategoryIdList.size()) {
            mChildCategoryIdList.add(new String(""));
        }    
        
        return (String) mChildCategoryIdList.get(idx);
    }
        
    public void setChildCategoryId(int idx, String arg) {

        if (mChildCategoryIdList == null) {
            mChildCategoryIdList = new ArrayList();
        }
        while (idx >= mChildCategoryIdList.size()) {
            mChildCategoryIdList.add(new String(""));
        }    
        
        mChildCategoryIdList.set(idx, arg);
    }
    
    /**
     * Gets the value of mChildCategoryListList
     *
     * @return the value of mChildCategoryListList
     */
    public ArrayList getChildCategoryListList() {
        if (null == this.mChildCategoryListList) {
            this.mChildCategoryListList = new ArrayList();
        }
	return this.mChildCategoryListList;
    }

    /**
     * Sets the value of mChildCategoryListList
     *
     * @param arg Value to assign to this.mChildCategoryListList
     */
    public void setChildCategoryListList(ArrayList arg){
	this.mChildCategoryListList = arg;
    }


    /**
     * Gets the value of mProductList
     *
     * @return the value of mProductList
     */
    public ProductDataVector getProductList() {
        if (null == this.mProductList) {
            this.mProductList = new ProductDataVector();
        }
	return this.mProductList;
    }

    /**
     * Sets the value of mProductList
     *
     * @param arg Value to assign to this.mProductList
     */
    public void setProductList(ProductDataVector arg){
	this.mProductList = arg;
    }

                
    /**
     * Gets the value of mSelectedTopCategoryId
     *
     * @return the value of mSelectedTopCategoryId
     */
    public String getSelectedTopCategoryId() {
	return this.mSelectedTopCategoryId;
    }

    /**
     * Sets the value of mSelectedTopCategoryId
     *
     * @param arg Value to assign to this.mSelectedTopCategoryId
     */
    public void setSelectedTopCategoryId(String arg){
	this.mSelectedTopCategoryId = arg;
    }
       

    public String[] getSelectedItems() {
        return mSelectedItems;
    }

    public void setSelectedItems(String[] pItemIds) {
        mSelectedItems = pItemIds;
    }

    
    
    /**
     *  Get the mTotalAmount field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getTotalAmount() {
        if (null == mTotalAmount) {
            mTotalAmount = new BigDecimal(0);
        }
        return mTotalAmount;
    }

    /**
     *  Set the mTotalAmount field.
     *
     *@param  v   The new TotalAmount value
     */
    public void setTotalAmount(BigDecimal v) {
        mTotalAmount = v;
    }


    
    /**
     *  Get the mTotalAmountBefore field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getTotalAmountBefore() {
        if (null == mTotalAmountBefore) {
            mTotalAmountBefore = new BigDecimal(0);
        }
        return mTotalAmountBefore;
    }

    /**
     *  Set the mTotalAmountBefore field.
     *
     *@param  v   The new TotalAmountBefore value
     */
    public void setTotalAmountBefore(BigDecimal v) {
        mTotalAmountBefore = v;
    }


    /**
     *  Get the mTotalAmountChange field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getTotalAmountChange() {
        if (null == mTotalAmountChange) {
            mTotalAmountChange = new BigDecimal(0);
        }
        return mTotalAmountChange;
    }

    /**
     *  Set the mTotalAmountChange field.
     *
     *@param  v   The new TotalAmountChange value
     */
    public void setTotalAmountChange(BigDecimal v) {
        mTotalAmountChange = v;
    }


    /**
     *  Get the mTotalOrderNum field.
     *
     *@return    int
     */
    public int getTotalOrderNum() {
        return mTotalOrderNum;
    }

    /**
     *  Set the mTotalOrderNum field.
     *
     *@param  v   The new TotalOrderNum value
     */
    public void setTotalOrderNum(int v) {
        mTotalOrderNum = v;
    }


    
    /**
     *  Get the mTotalOrderNumBefore field.
     *
     *@return    int
     */
    public int getTotalOrderNumBefore() {
        return mTotalOrderNumBefore;
    }

    /**
     *  Set the mTotalOrderNumBefore field.
     *
     *@param  v   The new TotalOrderNumBefore value
     */
    public void setTotalOrderNumBefore(int v) {
        mTotalOrderNumBefore = v;
    }


    
    /**
     *  Get the mTotalOrderNumChange field.
     *
     *@return    int
     */
    public int getTotalOrderNumChange() {
        return mTotalOrderNumChange;
    }

    /**
     *  Set the mTotalOrderNumChange field.
     *
     *@param  v   The new TotalOrderNumChange value
     */
    public void setTotalOrderNumChange(int v) {
        mTotalOrderNumChange = v;
    }



    /**
     *  Get the mAvgOrderSize field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getAvgOrderSize() {
        if (null == mAvgOrderSize) {
            mAvgOrderSize = new BigDecimal(0);
        }
        return mAvgOrderSize;
    }

    /**
     *  Set the mAvgOrderSize field.
     *
     *@param  v   The new AvgOrderSize value
     */
    public void setAvgOrderSize(BigDecimal v) {
        mAvgOrderSize = v;
    }


    
    /**
     *  Get the mAvgOrderSizeBefore field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getAvgOrderSizeBefore() {
        if (null == mAvgOrderSizeBefore) {
            mAvgOrderSizeBefore = new BigDecimal(0);
        }
        return mAvgOrderSizeBefore;
    }

    /**
     *  Set the mAvgOrderSizeBefore field.
     *
     *@param  v   The new AvgOrderSizeBefore value
     */
    public void setAvgOrderSizeBefore(BigDecimal v) {
        mAvgOrderSizeBefore = v;
    }


    /**
     *  Get the mAvgOrderSizeChange field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getAvgOrderSizeChange() {
        if (null == mAvgOrderSizeChange) {
            mAvgOrderSizeChange = new BigDecimal(0);
        }
        return mAvgOrderSizeChange;
    }

    /**
     *  Set the mAvgOrderSizeChange field.
     *
     *@param  v   The new AvgOrderSizeChange value
     */
    public void setAvgOrderSizeChange(BigDecimal v) {
        mAvgOrderSizeChange = v;
    }


    
    /**
     * Gets the value of mFinalCategoryId
     *
     * @return the value of mFinalCategoryId
     */
    public int getFinalCategoryId() {
	return this.mFinalCategoryId;
    }

    /**
     * Sets the value of mFinalCategoryId
     *
     * @param arg Value to assign to this.mFinalCategoryId
     */
    public void setFinalCategoryId(int arg){
	this.mFinalCategoryId = arg;
    }


    /**
     * Gets the value of mCategoryTreeDesc
     *
     * @return the value of mCategoryTreeDesc
     */
    public String getCategoryTreeDesc() {
	return this.mCategoryTreeDesc;
    }

    /**
     * Sets the value of mCategoryTreeDesc
     *
     * @param arg Value to assign to this.mCategoryTreeDesc
     */
    public void setCategoryTreeDesc(String arg){
	this.mCategoryTreeDesc = arg;
    }


    /**
     * Gets the value of mTopCategoryName
     *
     * @return the value of mTopCategoryName
     */
    public String getTopCategoryName() {
	return this.mTopCategoryName;
    }

    /**
     * Sets the value of mTopCategoryName
     *
     * @param arg Value to assign to this.mTopCategoryName
     */
    public void setTopCategoryName(String arg){
	this.mTopCategoryName = arg;
    }


    
    /**
     * Gets the value of mFinalCategoryName
     *
     * @return the value of mFinalCategoryName
     */
    public String getFinalCategoryName() {
	return this.mFinalCategoryName;
    }

    /**
     * Sets the value of mFinalCategoryName
     *
     * @param arg Value to assign to this.mFinalCategoryName
     */
    public void setFinalCategoryName(String arg){
	this.mFinalCategoryName = arg;
    }


    
    
    /**
     * <code>reset</code> method
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }


    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
				 HttpServletRequest request) {
        return null;
    }

    java.util.ArrayList mOrderReport;
    
    /**
     * Get the value of mXmlReport.
     * @return value of mXmlReport.
     */
    public java.util.ArrayList getOrderReport() {
	return mOrderReport;
    }
    
    /**
     * Set the value of mXmlReport.
     * @param v  Value to assign to mXmlReport.
     */
    public void setOrderReport(java.util.ArrayList  v) {
	this.mOrderReport = v;
    }

    /**
     * Get the value of mReportControls.
     * @return value of mReportControls.
     */
    public GenericReportControlViewVector getReportControls() {
	return mReportControls;
    }
    
    /**
     * Set the value of mReportControls.
     * @param v  Value to assign to mReportControls.
     */
    public void setReportControls(GenericReportControlViewVector  v) {
	this.mReportControls = v;
    }
    //Generic controls individual access
    public String getReportControlValue(int index) {
        if(mReportControls==null || index>=mReportControls.size()) {
            return null;
        }
        String value = ((GenericReportControlView)this.mReportControls.get(index)).getValue();
        return value ;
    }
    
    public void setReportControlValue(int index, String value) {
        if(mReportControls==null || index>=mReportControls.size()) {
            return;
        }
        GenericReportControlView grc = (GenericReportControlView) this.mReportControls.get(index);
        grc.setValue(value);
        
    }
        
    /**
     * Get the value of mReportResults.
     * @return value of mReportResults.
     */
    public GenericReportResultViewVector getReportResults() {
	return mReportResults;
    }
    
    /**
     * Set the value of mReportResults.
     * @param v  Value to assign to mReportResults.
     */
    public void setReportResults(GenericReportResultViewVector v) {
	this.mReportResults = v;
    }
    
    /**
     * Get the value of mGenericReportPageNum.
     * @return value of mGenericReportPageNum.
     */
    public int getGenericReportPageNum() {
	return mGenericReportPageNum;
    }
    
    /**
     * Set the value of mGenericReportPageNum.
     * @param v  Value to assign to mGenericReportPageNum.
     */
    public void setGenericReportPageNum(int v) {
	this.mGenericReportPageNum = v;
    }

    public void setReportMaxRowCount(int reportMaxRowCount) {
        this.mReportMaxRowCount = reportMaxRowCount;
    }


    public int getReportMaxRowCount() {
        return mReportMaxRowCount;
    }
    
    /**
     * Gets the value of mPeriodList
     *
     * @return the value of mPeriodList
     */
    public ArrayList getPeriodList() {
        if (null == this.mPeriodList) {
            this.mPeriodList = new ArrayList();
        }
	return this.mPeriodList;
    }

    /**
     * Sets the value of mPeriodList
     *
     * @param arg Value to assign to this.mPeriodList
     */
    public void setPeriodList(ArrayList arg){
	this.mPeriodList = arg;
    }


    public String getPeriod(int idx) {

        if (mPeriodList == null) {
            mPeriodList = new ArrayList();
        }
        while (idx >= mPeriodList.size()) {
            mPeriodList.add(new String(""));
        }    
        
        return (String) mPeriodList.get(idx);
    }
        
    public void setPeriod(int idx, String arg) {

        if (mPeriodList == null) {
            mPeriodList = new ArrayList();
        }
        while (idx >= mPeriodList.size()) {
            mPeriodList.add(new String(""));
        }    
        
        mPeriodList.set(idx, arg);
    }
    
    public void setSelectedPeriods(String[] pValue) {
    	mSelectedPeriods=pValue;
    }
    public String[] getSelectedPeriods(){
    	return mSelectedPeriods;
    }
    
}

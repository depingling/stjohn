
package com.cleanwise.service.api.value;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <code>OrderSiteSummaryData</code>
 */
public class OrderSiteSummaryData extends OrderInfoBase {
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = 6747693724082035337L;

    private int mOrderId;
    private String mOrderNum;
    private int mUserId;

    private OrderAddressData mOrderAddressData;

    private String mPoNum;
    private int mCostCenterId;
    private String mCostCenterDesc;

    //private String mAccountNum, mSiteErpNum;
    private int mAccountId, mSiteId;
    private String mOrderStatusCd;
    private String mWorkflowRoleCd;
    private java.math.BigDecimal mAmount;
    private java.math.BigDecimal mSumOfAllOrderCharges;
    private Date mOrderDate;
    private Date mOrderTime;
    private String mComments;
	private String mLocaleCd;
	
	private String mStringDiscountAmount;  //SVC
	private BigDecimal mDiscountAmount;    //SVC

    /**
     * Holds value of property placedBy.
     */
    private UserData placedBy;

    private String mProcessOnDate;
	private Date mModifStartedOn;
	private String mModifStartedBy;
  private Date orderTime;

  /**
     * Get the value of ProcessOnDate.
     * @return value of ProcessOnDate.
     */
    public String getProcessOnDate() {
        if ( null == mProcessOnDate ) {
            mProcessOnDate = "";
        }
        return mProcessOnDate;
    }
    public void setProcessOnDate(String v) { mProcessOnDate = v; }

    /**
     * Constructor.
     */
    private OrderSiteSummaryData ( )
    {
        mOrderNum = "";
        mPoNum = "";
        mOrderAddressData = null;
        mCostCenterDesc = "";
        mOrderStatusCd = "";
        mWorkflowRoleCd = "";
        mComments = "";
		mLocaleCd = "";
    }
    
    public void setOrderAddressData(OrderAddressData orderAddressData) {
    	mOrderAddressData = orderAddressData;
    }
    
    public OrderAddressData getOrderAddressData() {
    	return mOrderAddressData;
    }

    public BigDecimal getSumOfAllOrderCharges() {
        return mSumOfAllOrderCharges;
    }
    
    public void setOrderData( OrderData pOrderData ) {

	mOrderId = pOrderData.getOrderId();
	mOrderNum = pOrderData.getOrderNum();
	mUserId = pOrderData.getUserId();

	mPoNum = pOrderData.getRequestPoNum();
	mCostCenterId = pOrderData.getCostCenterId();

	//mAccountNum = pOrderData.getAccountErpNum();
        //mSiteErpNum = pOrderData.getSiteErpNum();
        mAccountId = pOrderData.getAccountId();
        mSiteId = pOrderData.getSiteId();
	mOrderStatusCd = pOrderData.getOrderStatusCd();
	mWorkflowRoleCd = pOrderData.getWorkflowStatusCd();
	mAmount = pOrderData.getOriginalAmount();
	mOrderDate = pOrderData.getOriginalOrderDate();
        mOrderTime = pOrderData.getOriginalOrderTime();
	mComments = pOrderData.getComments();
	mLocaleCd = pOrderData.getLocaleCd();
	mSumOfAllOrderCharges = OrderStatusDescData.getSumOfAllOrderCharges(pOrderData);
	
    }
    
    //Added by SVC: Begin
    public void setStringDiscountAmount(String pValue) {
    	mStringDiscountAmount = pValue;
    }
    
    public String getStringDiscountAmount() {
    	return mStringDiscountAmount;
    }
    
    public void setDiscountAmount(BigDecimal pValue) {
    	mDiscountAmount = pValue;
    }
    
    public BigDecimal getDiscountAmount() {
    	return mDiscountAmount;
    }
    

    

    
    /**
     * Get the value of CostCenterDesc.
     * @return value of CostCenterDesc.
     */
    public String getCostCenterDesc() {
	return mCostCenterDesc;
    }

    /**
     * Set the value of CostCenterDesc.
     * @param v  Value to assign to CostCenterDesc.
     */
    public void setCostCenterDesc(String  v) {
	this.mCostCenterDesc = v;
    }

    /**
     * Get the value of SitePostalCode.
     * @return value of SitePostalCode.
     */
    public String getSitePostalCode() {
    	if (getOrderAddressData() != null) {
    		return getOrderAddressData().getPostalCode();
    	}
    	else {
    		return "----";
    	}
    }

    /**
     * Get the value of SiteState.
     * @return value of SiteState.
     */
    public String getSiteState() {
    	if (getOrderAddressData() != null) {
    		return getOrderAddressData().getStateProvinceCd();
    	}
    	else {
    		return "---";
    	}
    }

    /**
     * Get the value of SiteCity.
     * @return value of SiteCity.
     */
    public String getSiteCity() {
    	if (getOrderAddressData() != null) {
    		return getOrderAddressData().getCity();
    	}
    	else {
    		return "--";
    	}
    }

    /**
     * Get the value of SiteDesc.
     * @return value of SiteDesc.
     */
    public String getSiteDesc() {
    	if (getOrderAddressData() != null) {
    		return getOrderAddressData().getShortDesc();
    	}
    	else {
    		return "-";
    	}
    }

    /**
     * Creates a new OrderSiteSummaryData
     *
     * @return
     *  Newly initialized OrderSiteSummaryData  object.
     */
    public static OrderSiteSummaryData  createValue ()
    {
        OrderSiteSummaryData valueData = new OrderSiteSummaryData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderSiteSummaryData  object
     */
    public String toString()
    {
        return "[" + "OrderId=" + mOrderId + "]";
    }

    /**
     * Sets the OrderId field. This field is required to be set in the database.
     *
     * @param pOrderId
     *  int to use to update the field.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
    }
    /**
     * Retrieves the OrderId field.
     *
     * @return
     *  int containing the OrderId field.
     */
    public int getOrderId(){
        return mOrderId;
    }

    /**
     * Sets the OrderNum field. This field is required to be set in the database.
     *
     * @param pOrderNum
     *  String to use to update the field.
     */
    public void setOrderNum(String pOrderNum){
        this.mOrderNum = pOrderNum;
    }
    /**
     * Retrieves the OrderNum field.
     *
     * @return
     *  String containing the OrderNum field.
     */
    public String getOrderNum(){
        return mOrderNum;
    }

    /**
     * Sets the UserId field. This field is required to be set in the database.
     *
     * @param pUserId
     *  int to use to update the field.
     */
    public void setUserId(int pUserId){
        this.mUserId = pUserId;
    }
    /**
     * Retrieves the UserId field.
     *
     * @return
     *  int containing the UserId field.
     */
    public int getUserId(){
        return mUserId;
    }

    /**
     * Sets the PoNum field.
     *
     * @param pPoNum
     *  String to use to update the field.
     */
    public void setPoNum(String pPoNum){
        this.mPoNum = pPoNum;
    }
    /**
     * Retrieves the PoNum field.
     *
     * @return
     *  String containing the PoNum field.
     */
    public String getPoNum(){
        return mPoNum;
    }

    /**
     * Sets the CostCenterId field.
     *
     * @param pCostCenterId
     *  int to use to update the field.
     */
    public void setCostCenterId(int pCostCenterId){
        this.mCostCenterId = pCostCenterId;
    }
    /**
     * Retrieves the CostCenterId field.
     *
     * @return
     *  int containing the CostCenterId field.
     */
    public int getCostCenterId(){
        return mCostCenterId;
    }

    /**
     * Sets the AccountNum field.
     *
     * @param pAccountNum
     *  String to use to update the field.
     */
    //public void setAccountNum(String pAccountNum){
    //    this.mAccountNum = pAccountNum;
    //}



    /**
     * Retrieves the AccountNum field.
     *
     * @return
     *  String containing the AccountNum field.
     */
    //public String getAccountNum(){
    //    return mAccountNum;
    //}

    //public String getSiteErpNum(){
    //    return mSiteErpNum;
    //}


    public int getSiteId(){
        return mSiteId;
    }
    public int getAccountId(){
        return mAccountId;
    }

    /**
     * Sets the OrderStatusCd field. This field is required to be set in the database.
     *
     * @param pOrderStatusCd
     *  String to use to update the field.
     */
    public void setOrderStatusCd(String pOrderStatusCd){
        this.mOrderStatusCd = pOrderStatusCd;
    }
    /**
     * Retrieves the OrderStatusCd field.
     *
     * @return
     *  String containing the OrderStatusCd field.
     */
    public String getOrderStatusCd(){
        return mOrderStatusCd;
    }

    /**
     * Sets the WorkflowRoleCd field. This field is required to be set in the database.
     *
     * @param pWorkflowRoleCd
     *  String to use to update the field.
     */
    public void setWorkflowRoleCd(String pWorkflowRoleCd){
        this.mWorkflowRoleCd = pWorkflowRoleCd;
    }
    /**
     * Retrieves the WorkflowRoleCd field.
     *
     * @return
     *  String containing the WorkflowRoleCd field.
     */
    public String getWorkflowRoleCd(){
        return mWorkflowRoleCd;
    }

    /**
     * Sets the Amount field.
     *
     * @param pAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setAmount(java.math.BigDecimal pAmount){
        this.mAmount = pAmount;
    }
    /**
     * Retrieves the Amount field.
     *
     * @return
     *  java.math.BigDecimal containing the Amount field.
     */
    public java.math.BigDecimal getAmount(){
        return mAmount;
    }

    /**
     * Sets the OrderDate field.
     *
     * @param pOrderDate
     *  Date to use to update the field.
     */
    public void setOrderDate(Date pOrderDate){
        this.mOrderDate = pOrderDate;
    }

    /**
    * Sets the OrderTime field.
    *
    * @param pOrderTime
    *  Date to use to update the field.
    */

    public void setOrderTime(Date orderTime) {
      this.mOrderTime = orderTime;
    }

    /**
     * Retrieves the OrderDate field.
     *
     * @return
     *  Date containing the OrderDate field.
     */
    public Date getOrderDate(){
        return mOrderDate;
    }
    /**
     * Retrieves the OrderTime field.
     *
     * @return
     *  Date containing the OrderTime field.
     */

    public Date getOrderTime() {
      return mOrderTime;
    }

    /**
     * Sets the Comments field.
     *
     * @param pComments
     *  String to use to update the field.
     */
    public void setComments(String pComments){
        this.mComments = pComments;
    }

    /**
     * Sets the LocaleCd field.
     *
     * @param pLocaleCd
     *  String to use to update the field.
     */
    public void setLocaleCd(String pLocaleCd){
        this.mLocaleCd = pLocaleCd;
    }

    /**
     * Getter for property placedBy.
     * @return Value of property placedBy.
     */
    public UserData getPlacedBy() {

        return this.placedBy;
    }

  /**
     * Setter for property placedBy.
     * @param placedBy New value of property placedBy.
     */
    public void setPlacedBy(UserData placedBy) {

        this.placedBy = placedBy;
    }

  /**
     * Retrieves the Comments field.
     *
     * @return
     *  String containing the Comments field.
     */
    public String getComments(){
        return mComments;
    }

    /**
     * Retrieves the LocaleCd field.
     *
     * @return
     *  String containing the LocaleCd field.
     */
    public String getLocaleCd(){
        return mLocaleCd;
    }


  /**
     * Sets the ModifStartedOn field.
     *
     * @param pModifStartedOn
     *  Date to use to update the field.
     */
    public void setModifStartedOn(Date pModifStartedOn){
        this.mModifStartedOn = pModifStartedOn;
    }

  /**
     * Retrieves the ModifStartedOn field.
     *
     * @return
     *  Date  containing the ModifStartedOn field.
     */
    public Date getModifStartedOn(){
        return mModifStartedOn;
    }

  /**
     * Sets the ModifStartedBy field.
     *
     * @param pModifStartedBy
     *  String to use to update the field.
     */
    public void setModifStartedBy(String pModifStartedBy){
        this.mModifStartedBy = pModifStartedBy;
    }

  /**
     * Retrieves the ModifStartedBy field.
     *
     * @return
     *  String containing the ModifStartedBy field.
     */
    public String getModifStartedBy(){
        return mModifStartedBy;
    }

}

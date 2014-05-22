package com.cleanwise.view.forms;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.WorkOrderUtil;

import javax.servlet.ServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.logic.UserWorkOrderMgrLogic;

import com.cleanwise.view.utils.WorkOrderItemizedServiceStr;
import com.cleanwise.view.utils.WorkOrderItemizedServiceStrVector;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 * Title:        UserWorkOrderDetailMgrForm
 * Description:  Form bean
 * Purpose:      Holds data for work order management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         14.10.2007
 * Time:         14:25:18
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserWorkOrderDetailMgrForm extends ActionForm {
	public static final String EMAIL = WorkOrderUtil.EMAIL;
	public static final String FAX   = WorkOrderUtil.FAX;

    public static interface CREATE_STEP_CD{
        public  String STEP1 = "STEP1";
        public  String STEP2 = "STEP2";
        public  String STEP3 = "STEP3";
    }

    public String managementSource = UserWorkOrderMgrLogic.className;

    public WorkOrderDetailView workOrderDetail;

    //workorder detail page
    private int busEntityId = 0;
    private String costCenterId = "";
    private String addBy;
    private Date   addDate;
    private String categoryCd;

    private String longDesc;
    private String modBy;
    private Date   modDate;
    private String priority;
    private String shortDesc;
    private String statusCd;
    private String typeCd;
    private String serviceProviderIdStr = "";
    private String actionCode;
    private String actualFinishDate;
    private String actualStartDate;
    private String quotedStartDate;
    private String quotedFinishDate;
    private String partsBilledService;
    private String partsWarranty;
    private String partsPMContract;
    private String partsTotal;
    private String laborBilledService;
    private String laborWarranty;
    private String laborPMContract;
    private String laborTotal;
    private String travelBilledService;
    private String travelWarranty;
    private String travelPMContract;
    private String travelTotal;
    private String totalBilledService;
    private String totalWarranty;
    private String totalPMContract;
    private String totalTotal;
    private String providerTradingType;
    private String workflowProcessing;
    private String workOrderNum;
    private String workOrderPoNum;
    private boolean allowSetWorkOrderPoNumber = false;
    private boolean workOrderPoNumberIsRequired = false;
    private boolean copyWorkOrder = false;
    private String forceStatusChange;
    private String contactInformationType = "";
    private int itemizedServiceTableMaxLineNumber;
    private int itemizedServiceTableActiveCount;
    private int itemizedServiceTableDataLineCount;

    private ServiceProviderData serviceProvider;

    private String userWorkOrderCreatorFullName = "";

    //user contact information
    private WorkOrderPropertyData userContactFirstName;
    private WorkOrderPropertyData userContactPhoneNum;
    private WorkOrderPropertyData userContactLastName;
    private WorkOrderPropertyData userContactFaxNum;
    private WorkOrderPropertyData userContactAddress1;
    private WorkOrderPropertyData userContactMobilePhone;
    private WorkOrderPropertyData userContactAddress2;
    private WorkOrderPropertyData userContactEmail;
    private WorkOrderPropertyData userContactCity;
    private WorkOrderPropertyData userContactCountry;
    private WorkOrderPropertyData userContactState;
    private WorkOrderPropertyData userContactPostalCode;
    //site contact information


    private ServiceProviderDataVector actualServiceProviders;
    private OrderScheduleDataVector schedules;

    private BudgetSpentAmountView shortBudgetSpent;
    private BudgetSpendViewVector detailBudgetSpent;
    private CostCenterDataVector displayCostCenters;


    // WorkOrderItems
    private WarrantyDataVector warrantyForActiveAsset;
    private AssetDataVector assetCategories;
    private AssetDataVector allAssets;
    private AssetDataVector assetForActiveCategory;

    WorkOrderDetailDataVector itemizedService;
    WorkOrderItemizedServiceStrVector itemizedServiceStr;
    WorkOrderItemDetailView workOrderItemDetail;

    //detail field
    private int workOrderId = 0;
    private int workOrderItemId = 0;
    private int warrantyId = 0;
    private String itemActualLabor;
    private String itemActualPart;
    private String itemQuotedPart;
    private String itemQuotedLabor;
    private String itemShortDesc;
    private String itemLongDesc;
    private String itemQuotedTotalCost;
    private String itemActualTotalCost;
    private String itemAddBy;
    private Date   itemAddDate;
    private String itemModBy;
    private Date   itemModDate;
    private String itemSequence;
    private String itemStatusCd;
    private PairViewVector assetGroups;
    private OrderDataVector itemOrders;
    private AssetData activeAsset;
    private AssetData activeAssetCategory;
    private String activeAssetIdStr = "";
    private String activeWarrantyIdStr = "";
    private String activeStep;
    private String activeCategoryIdStr = "";
    private boolean allowBuyWorkOrderParts = false;
    private String nteStr;
    private BigDecimal nte;

    private boolean displayDistributorAccountReferenceNumber;
    private boolean displayDistributorSiteReferenceNumber;

	private String distributorAccountNumber;
	private String distributorShipToLocationNumber;
  private String receivedDate;
  private String receivedTime;
  private String quotedStartTime;
  private String quotedFinishTime;
  private String actualStartTime;
  private String actualFinishTime;
  private String TCSFl;

  public boolean isDisplayDistributorAccountReferenceNumber() {
		return displayDistributorAccountReferenceNumber;
	}

	public void setDisplayDistributorAccountReferenceNumber(
			boolean displayDistributorAccountReferenceNumber) {
		this.displayDistributorAccountReferenceNumber = displayDistributorAccountReferenceNumber;
	}

	public boolean isDisplayDistributorSiteReferenceNumber() {
		return displayDistributorSiteReferenceNumber;
	}

	public void setDisplayDistributorSiteReferenceNumber(
			boolean displayDistributorSiteReferenceNumber) {
		this.displayDistributorSiteReferenceNumber = displayDistributorSiteReferenceNumber;
	}

	public String getDistributorAccountNumber() {
		return distributorAccountNumber;
	}

	public void setDistributorAccountNumber(String distributorAccountNumber) {
		this.distributorAccountNumber = distributorAccountNumber;
	}

	public String getDistributorShipToLocationNumber() {
		return distributorShipToLocationNumber;
	}

	public void setDistributorShipToLocationNumber(
			String distributorShipToLocationNumber) {
		this.distributorShipToLocationNumber = distributorShipToLocationNumber;
	}


	public BigDecimal getNte() {
		return nte;
	}

	public void setNte(BigDecimal nte) {
		this.nte = nte;
	}

	public String getNteStr() {
		return nteStr;
	}

	public void setNteStr(String nte) {
		this.nteStr = nte;
	}

	public String getUserWorkOrderCreatorFullName() {
        return userWorkOrderCreatorFullName;
    }

    public void setUserWorkOrderCreatorFullName(String userWorkOrderCreatorFullName) {
        this.userWorkOrderCreatorFullName = userWorkOrderCreatorFullName;
    }

    public String getItemActualTotalCost() {
        return itemActualTotalCost;
    }

    public void setItemActualTotalCost(String itemActualTotalCost) {
        this.itemActualTotalCost = itemActualTotalCost;
    }

    public String getItemQuotedTotalCost() {
        return itemQuotedTotalCost;
    }

    public void setItemQuotedTotalCost(String itemQuotedTotalCost) {
        this.itemQuotedTotalCost = itemQuotedTotalCost;
    }

    public String getItemActualLabor() {
        return itemActualLabor;
    }

    public void setItemActualLabor(String itemActualLabor) {
        this.itemActualLabor = itemActualLabor;
    }

    public String getItemActualPart() {
        return itemActualPart;
    }

    public void setItemActualPart(String itemActualPart) {
        this.itemActualPart = itemActualPart;
    }

    public String getItemAddBy() {
        return itemAddBy;
    }

    public void setItemAddBy(String itemAddBy) {
        this.itemAddBy = itemAddBy;
    }

    public Date getItemAddDate() {
        return itemAddDate;
    }

    public void setItemAddDate(Date itemAddDate) {
        this.itemAddDate = itemAddDate;
    }

    public String getItemLongDesc() {
        return itemLongDesc;
    }

    public void setItemLongDesc(String itemLongDesc) {
        this.itemLongDesc = itemLongDesc;
    }

    public String getItemModBy() {
        return itemModBy;
    }

    public void setItemModBy(String itemModBy) {
        this.itemModBy = itemModBy;
    }

    public Date getItemModDate() {
        return itemModDate;
    }

    public void setItemModDate(Date itemModDate) {
        this.itemModDate = itemModDate;
    }

    public String getItemQuotedLabor() {
        return itemQuotedLabor;
    }

    public void setItemQuotedLabor(String itemQuotedLabor) {
        this.itemQuotedLabor = itemQuotedLabor;
    }

    public String getItemQuotedPart() {
        return itemQuotedPart;
    }

    public void setItemQuotedPart(String itemQuotedPart) {
        this.itemQuotedPart = itemQuotedPart;
    }

    public String getItemShortDesc() {
        return itemShortDesc;
    }

    public void setItemShortDesc(String itemShortDesc) {
        this.itemShortDesc = itemShortDesc;
    }

    public String getItemStatusCd() {
        return itemStatusCd;
    }

    public void setItemStatusCd(String itemStatusCd) {
        this.itemStatusCd = itemStatusCd;
    }

    public WorkOrderDetailView getWorkOrderDetail() {
        return workOrderDetail;
    }

    public void setWorkOrderDetail(WorkOrderDetailView workOrderDetail) {
        this.workOrderDetail = workOrderDetail;
    }


    public int getBusEntityId() {
        return busEntityId;
    }

    public void setBusEntityId(int busEntityId) {
        this.busEntityId = busEntityId;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public String getCategoryCd() {
        return categoryCd;
    }

    public void setCategoryCd(String categoryCd) {
        this.categoryCd = categoryCd;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(String actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public String getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(String actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getModBy() {
        return modBy;
    }

    public void setModBy(String modBy) {
        this.modBy = modBy;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public String getServiceProviderIdStr() {
        return serviceProviderIdStr;
    }

    public void setServiceProviderIdStr(String providerIdStr) {
        this.serviceProviderIdStr = providerIdStr;
    }

    public void setActionCode(String action) {
        this.actionCode = action;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setEstimatedStartDate(String estimatedStartDate) {
        this.quotedStartDate = estimatedStartDate;
    }

    public String getEstimatedStartDate() {
        return quotedStartDate;
    }

    public void setEstimatedFinishDate(String estimatedFinishDate) {
        this.quotedFinishDate = estimatedFinishDate;
    }

    public String getEstimatedFinishDate() {
        return quotedFinishDate;
    }

    public void resetManagementSource() {
        managementSource = UserWorkOrderMgrLogic.className;
    }

    public ServiceProviderData getActiveServiceProvider(){
        return this.serviceProvider;
    }

    public void setServiceProvider(ServiceProviderData serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public WorkOrderPropertyData getUserContactFirstName() {
        return userContactFirstName;
    }

    public void setUserContactFirstName(WorkOrderPropertyData userContactFirstName) {
        this.userContactFirstName = userContactFirstName;
    }

    public WorkOrderPropertyData getUserContactPhoneNum() {
        return userContactPhoneNum;
    }

    public void setUserContactPhoneNum(WorkOrderPropertyData userContactPhoneNum) {
        this.userContactPhoneNum = userContactPhoneNum;
    }

    public WorkOrderPropertyData getUserContactLastName() {
        return userContactLastName;
    }

    public void setUserContactLastName(WorkOrderPropertyData userContactLastName) {
        this.userContactLastName = userContactLastName;
    }

    public WorkOrderPropertyData getUserContactFaxNum() {
        return userContactFaxNum;
    }

    public void setUserContactFaxNum(WorkOrderPropertyData userContactFaxNum) {
        this.userContactFaxNum = userContactFaxNum;
    }

    public WorkOrderPropertyData getUserContactAddress1() {
        return userContactAddress1;
    }

    public void setUserContactAddress1(WorkOrderPropertyData userContactAddress1) {
        this.userContactAddress1 = userContactAddress1;
    }

    public WorkOrderPropertyData getUserContactMobilePhone() {
        return userContactMobilePhone;
    }

    public void setUserContactMobilePhone(WorkOrderPropertyData userContactMobilePhone) {
        this.userContactMobilePhone = userContactMobilePhone;
    }

    public WorkOrderPropertyData getUserContactAddress2() {
        return userContactAddress2;
    }

    public void setUserContactAddress2(WorkOrderPropertyData userContactAddress2) {
        this.userContactAddress2 = userContactAddress2;
    }

    public WorkOrderPropertyData getUserContactEmail() {
        return userContactEmail;
    }

    public void setUserContactEmail(WorkOrderPropertyData userContactEmail) {
        this.userContactEmail = userContactEmail;
    }

    public WorkOrderPropertyData getUserContactCity() {
        return userContactCity;
    }

    public void setUserContactCity(WorkOrderPropertyData userContactCity) {
        this.userContactCity = userContactCity;
    }

    public WorkOrderPropertyData getUserContactCountry() {
        return userContactCountry;
    }

    public void setUserContactCountry(WorkOrderPropertyData userContactCountry) {
        this.userContactCountry = userContactCountry;
    }

    public WorkOrderPropertyData getUserContactState() {
        return userContactState;
    }

    public void setUserContactState(WorkOrderPropertyData userContactState) {
        this.userContactState = userContactState;
    }

    public WorkOrderPropertyData getUserContactPostalCode() {
        return userContactPostalCode;
    }

    public void setUserContactPostalCode(WorkOrderPropertyData userContactPostalCode) {
        this.userContactPostalCode = userContactPostalCode;
    }

    public void setActualServiceProviders(ServiceProviderDataVector actualServiceProviders) {
        this.actualServiceProviders = actualServiceProviders;
    }

    public ServiceProviderDataVector getActualServiceProviders() {
        return actualServiceProviders;
    }

    public String getProviderTradingType() {
        return providerTradingType;
    }

    public void setProviderTradingType(String providerTradingType) {
        this.providerTradingType = providerTradingType;
    }

    public void setSchedules(OrderScheduleDataVector schedules) {
        this.schedules = schedules;
    }

    public OrderScheduleDataVector getSchedules() {
        return schedules;
    }

    public String getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(String costCenterId) {
        this.costCenterId = costCenterId;
    }

    public String getManagementSource() {
        return managementSource;
    }

    public void setManagementSource(String managementSource) {
        this.managementSource = managementSource;
    }

    public void setShortBudgetSpent(BudgetSpentAmountView shortBudgetSpent) {
        this.shortBudgetSpent = shortBudgetSpent;
    }

    public void setDetailBudgetSpent(BudgetSpendViewVector detailBudgetSpent) {
        this.detailBudgetSpent = detailBudgetSpent;
    }

    public BudgetSpendViewVector getDetailBudgetSpent() {
        return detailBudgetSpent;
    }

    public BudgetSpentAmountView getShortBudgetSpent() {
        return shortBudgetSpent;
    }

    public void setDisplayCostCenters(CostCenterDataVector displayCostCenters) {
        this.displayCostCenters = displayCostCenters;
    }

    public CostCenterDataVector getDisplayCostCenters() {
        return displayCostCenters;
    }

    public String getWorkflowProcessing() {
        return workflowProcessing;
    }

    public void setWorkflowProcessing(String workflowProcessing) {
        this.workflowProcessing = workflowProcessing;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.workflowProcessing = Boolean.toString(false);
    }

    public void setAllowSetWorkOrderPoNumber(boolean allowSetWorkOrderPoNumber) {
        this.allowSetWorkOrderPoNumber = allowSetWorkOrderPoNumber;
    }

    public boolean getAllowSetWorkOrderPoNumber() {
        return this.allowSetWorkOrderPoNumber;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public void setWorkOrderPoNumberIsRequired(boolean workOrderPoNumberIsRequired) {
        this.workOrderPoNumberIsRequired = workOrderPoNumberIsRequired;
    }

    public boolean getWorkOrderPoNumberIsRequired() {
        return this.workOrderPoNumberIsRequired;
    }

    public String getWorkOrderPoNum() {
        return workOrderPoNum;
    }

    public void setWorkOrderPoNum(String workOrderPoNum) {
        this.workOrderPoNum = workOrderPoNum;
    }


    public AssetData getActiveAssetCategory() {
        return activeAssetCategory;
    }

    public void setActiveAssetCategory(AssetData activeAssetCategory) {
        this.activeAssetCategory = activeAssetCategory;
    }

    public void setActiveAsset(AssetData activeAsset) {
        this.activeAsset = activeAsset;
    }

    public AssetData getActiveAsset() {
        return activeAsset;
    }

    public AssetDataVector getAssetForActiveCategory() {
        return assetForActiveCategory;
    }

    public void setAssetForActiveCategory(AssetDataVector assetForActiveCategory) {
        this.assetForActiveCategory = assetForActiveCategory;
    }

    public void setAssetCategories(AssetDataVector assetCategories) {
        this.assetCategories = assetCategories;
    }

    public AssetDataVector getAssetCategories() {
        return assetCategories;
    }

    public String getActiveCategoryIdStr() {
        return activeCategoryIdStr;
    }

    public void setActiveCategoryIdStr(String activeCategoryIdStr) {
        this.activeCategoryIdStr = activeCategoryIdStr;
    }

    public WorkOrderDetailDataVector getItemizedService() {
        return itemizedService;
    }

    public void setItemizedService(WorkOrderDetailDataVector itemizedService) {
        this.itemizedService = itemizedService;
    }

    public WorkOrderItemDetailView getWorkOrderItemDetail() {
        return workOrderItemDetail;
    }

    public void setWorkOrderItemDetail(WorkOrderItemDetailView workOrderItemDetail) {
        this.workOrderItemDetail = workOrderItemDetail;
    }

    public int getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(int warrantyId) {
        this.warrantyId = warrantyId;
    }

    public int getWorkOrderItemId() {
        return workOrderItemId;
    }

    public void setWorkOrderItemId(int workOrderItemId) {
        this.workOrderItemId = workOrderItemId;
    }

    public String getItemSequence() {
        return itemSequence;
    }

    public void setItemSequence(String itemSequence) {
        this.itemSequence = itemSequence;
    }

    public String getActiveWarrantyIdStr() {
        return activeWarrantyIdStr;
    }

    public void setActiveWarrantyIdStr(String warrantyIdStr) {
        this.activeWarrantyIdStr = warrantyIdStr;
    }

    public String getActiveAssetIdStr() {
        return activeAssetIdStr;
    }

    public void setActiveAssetIdStr(String assetIdStr) {
        this.activeAssetIdStr = assetIdStr;
    }

    public void setWarrantyForActiveAsset(WarrantyDataVector warranties) {
        this.warrantyForActiveAsset = warranties;
    }

    public WarrantyDataVector getWarrantyForActiveAsset() {
        return warrantyForActiveAsset;
    }

    public void setAllAssets(AssetDataVector assets) {
        this.allAssets = assets;
    }

    public AssetDataVector getAllAssets() {
        return allAssets;
    }

    public String getActiveStep() {
        return activeStep;
    }

    public void setActiveStep(String activeStep) {
        this.activeStep = activeStep;
    }

    public void setAssetGroups(PairViewVector assetGroups) {
        this.assetGroups = assetGroups;
    }

    public PairViewVector getAssetGroups() {
        return assetGroups;
    }

    public void setItemOrders(OrderDataVector itemOrders) {
        this.itemOrders = itemOrders;
    }

    public OrderDataVector getItemOrders() {
        return itemOrders;
    }

    public void setAllowBuyWorkOrderParts(boolean allowBuyWorkOrderParts) {
        this.allowBuyWorkOrderParts = allowBuyWorkOrderParts;
    }

    public boolean getAllowBuyWorkOrderParts() {
        return this.allowBuyWorkOrderParts;
    }

    public String getContactInformationType() {
        return contactInformationType;
    }

    public void setContactInformationType(String contactInformationType) {
        this.contactInformationType = contactInformationType;
    }

    public String getLaborBilledService() {
        return laborBilledService;
    }

    public void setLaborBilledService(String laborBilledService) {
        this.laborBilledService = laborBilledService;
    }

    public String getLaborPMContract() {
        return laborPMContract;
    }

    public void setLaborPMContract(String laborPMContract) {
        this.laborPMContract = laborPMContract;
    }

    public String getLaborTotal() {
        return laborTotal;
    }

    public void setLaborTotal(String laborTotal) {
        this.laborTotal = laborTotal;
    }

    public String getLaborWarranty() {
        return laborWarranty;
    }

    public void setLaborWarranty(String laborWarranty) {
        this.laborWarranty = laborWarranty;
    }

    public String getPartsBilledService() {
        return partsBilledService;
    }

    public void setPartsBilledService(String partsBilledService) {
        this.partsBilledService = partsBilledService;
    }

    public String getPartsPMContract() {
        return partsPMContract;
    }

    public void setPartsPMContract(String partsPMContract) {
        this.partsPMContract = partsPMContract;
    }

    public String getPartsTotal() {
        return partsTotal;
    }

    public void setPartsTotal(String partsTotal) {
        this.partsTotal = partsTotal;
    }

    public String getPartsWarranty() {
        return partsWarranty;
    }

    public void setPartsWarranty(String partsWarranty) {
        this.partsWarranty = partsWarranty;
    }

    public String getQuotedFinishDate() {
        return quotedFinishDate;
    }

    public void setQuotedFinishDate(String quotedFinishDate) {
        this.quotedFinishDate = quotedFinishDate;
    }

    public String getQuotedStartDate() {
        return quotedStartDate;
    }

    public void setQuotedStartDate(String quotedStartDate) {
        this.quotedStartDate = quotedStartDate;
    }

    public String getTotalBilledService() {
        return totalBilledService;
    }

    public void setTotalBilledService(String totalBilledService) {
        this.totalBilledService = totalBilledService;
    }

    public String getTotalPMContract() {
        return totalPMContract;
    }

    public void setTotalPMContract(String totalPMContract) {
        this.totalPMContract = totalPMContract;
    }

    public String getTotalTotal() {
        return totalTotal;
    }

    public void setTotalTotal(String totalTotal) {
        this.totalTotal = totalTotal;
    }

    public String getTotalWarranty() {
        return totalWarranty;
    }

    public void setTotalWarranty(String totalWarranty) {
        this.totalWarranty = totalWarranty;
    }

    public String getTravelBilledService() {
        return travelBilledService;
    }

    public void setTravelBilledService(String travelBilledService) {
        this.travelBilledService = travelBilledService;
    }

    public String getTravelPMContract() {
        return travelPMContract;
    }

    public void setTravelPMContract(String travelPMContract) {
        this.travelPMContract = travelPMContract;
    }

    public String getTravelTotal() {
        return travelTotal;
    }

    public void setTravelTotal(String travelTotal) {
        this.travelTotal = travelTotal;
    }

    public String getTravelWarranty() {
        return travelWarranty;
    }

    public void setTravelWarranty(String travelWarranty) {
        this.travelWarranty = travelWarranty;
    }

    public String getForceStatusChange() {
        return forceStatusChange;
    }

    public void setForceStatusChange(String forceStatusChange) {
        this.forceStatusChange = forceStatusChange;
    }

    public int getItemizedServiceTableMaxLineNumber() {
        return itemizedServiceTableMaxLineNumber;
    }

    public void setItemizedServiceTableMaxLineNumber(int itemizedServiceTableMaxLineNumber) {
        this.itemizedServiceTableMaxLineNumber = itemizedServiceTableMaxLineNumber;
    }

    public int getItemizedServiceTableActiveCount() {
        return itemizedServiceTableActiveCount;
    }

    public void setItemizedServiceTableActiveCount(int itemizedServiceTableActiveCount) {
        this.itemizedServiceTableActiveCount = itemizedServiceTableActiveCount;
    }

    public int getItemizedServiceTableDataLineCount() {
        return itemizedServiceTableDataLineCount;
    }

    public void setItemizedServiceTableDataLineCount(int itemizedServiceTableDataLineCount) {
        this.itemizedServiceTableDataLineCount = itemizedServiceTableDataLineCount;
    }

    public WorkOrderItemizedServiceStrVector getItemizedServiceStr() {
        return itemizedServiceStr;
    }

    public void setItemizedServiceStr(WorkOrderItemizedServiceStrVector itemizedServiceStr) {
        this.itemizedServiceStr = itemizedServiceStr;
    }

    public void setPaymentTypeCd(int idx, String pVal) {
      WorkOrderDetailData woDD = (WorkOrderDetailData)itemizedService.get(idx);
      if(woDD == null) return;
      woDD.setPaymentTypeCd(pVal);
    }
    public String getPaymentTypeCd(int idx) {
      WorkOrderDetailData woDD = (WorkOrderDetailData)itemizedService.get(idx);
      if(woDD == null) return "";
      return woDD.getPaymentTypeCd();
    }

    public void setQuantity(int idx, String pVal) {
      WorkOrderItemizedServiceStr woISS = (WorkOrderItemizedServiceStr)itemizedServiceStr.get(idx);
      if(woISS == null) return;
      woISS.setQuantity(pVal);
    }
    public String getQuantity(int idx) {
      WorkOrderItemizedServiceStr woISS = (WorkOrderItemizedServiceStr)itemizedServiceStr.get(idx);
      if(woISS == null) return "";
      return woISS.getQuantity();
    }

    public void setPartNumber(int idx, String pVal) {
      WorkOrderDetailData woDD = (WorkOrderDetailData)itemizedService.get(idx);
      if(woDD == null) return;
      woDD.setPartNumber(pVal);
    }
    public String getPartNumber(int idx) {
      WorkOrderDetailData woDD = (WorkOrderDetailData)itemizedService.get(idx);
      if(woDD == null) return "";
      return woDD.getPartNumber();
    }

    public void setShortDescr(int idx, String pVal) {
      WorkOrderDetailData woDD = (WorkOrderDetailData)itemizedService.get(idx);
      if(woDD == null) return;
      woDD.setShortDesc(pVal);
    }
    public String getShortDescr(int idx) {
      WorkOrderDetailData woDD = (WorkOrderDetailData)itemizedService.get(idx);
      if(woDD == null) return "";
      return woDD.getShortDesc();
    }

    public void setComments(int idx, String pVal) {
      WorkOrderDetailData woDD = (WorkOrderDetailData)itemizedService.get(idx);
      if(woDD == null) return;
      woDD.setComments(pVal);
    }
    public String getComments(int idx) {
      WorkOrderDetailData woDD = (WorkOrderDetailData)itemizedService.get(idx);
      if(woDD == null) return "";
      return woDD.getComments();
    }

    public void setPartPrice(int idx, String pVal) {
      WorkOrderItemizedServiceStr woISS = (WorkOrderItemizedServiceStr)itemizedServiceStr.get(idx);
      if(woISS == null) return;
      woISS.setPartPrice(pVal);
    }
    public String getPartPrice(int idx) {
      WorkOrderItemizedServiceStr woISS = (WorkOrderItemizedServiceStr)itemizedServiceStr.get(idx);
      if(woISS == null) return "";
      return woISS.getPartPrice();
    }

    public void setLabor(int idx, String pVal) {
      WorkOrderItemizedServiceStr woISS = (WorkOrderItemizedServiceStr)itemizedServiceStr.get(idx);
      if(woISS == null) return;
      woISS.setLabor(pVal);
    }
    public String getLabor(int idx) {
      WorkOrderItemizedServiceStr woISS = (WorkOrderItemizedServiceStr)itemizedServiceStr.get(idx);
      if(woISS == null) return "";
      return woISS.getLabor();
    }

    public void setTravel(int idx, String pVal) {
      WorkOrderItemizedServiceStr woISS = (WorkOrderItemizedServiceStr)itemizedServiceStr.get(idx);
      if(woISS == null) return;
      woISS.setTravel(pVal);
    }
    public String getTravel(int idx) {
      WorkOrderItemizedServiceStr woISS = (WorkOrderItemizedServiceStr)itemizedServiceStr.get(idx);
      if(woISS == null) return "";
      return woISS.getTravel();
    }

    public boolean getCopyWorkOrder() {
        return copyWorkOrder;
    }

  public String getReceivedDate() {
    return receivedDate;
  }

  public String getReceivedTime() {
    return receivedTime;
  }

  public String getQuotedStartTime() {
    return quotedStartTime;
  }

  public String getQuotedFinishTime() {
    return quotedFinishTime;
  }

  public String getActualStartTime() {
    return actualStartTime;
  }

  public String getActualFinishTime() {
    return actualFinishTime;
  }

  public String getTCSFl() {
    return TCSFl;
  }

  public void setCopyWorkOrder(boolean copyWorkOrder) {
        this.copyWorkOrder = copyWorkOrder;
    }

  public void setReceivedDate(String receivedDate) {
    this.receivedDate = receivedDate;
  }

  public void setReceivedTime(String receivedTime) {
    this.receivedTime = receivedTime;
  }

  public void setQuotedStartTime(String quotedStartTime) {
    this.quotedStartTime = quotedStartTime;
  }

  public void setQuotedFinishTime(String quotedFinishTime) {
    this.quotedFinishTime = quotedFinishTime;
  }

  public void setActualStartTime(String actualStartTime) {
    this.actualStartTime = actualStartTime;
  }

  public void setActualFinishTime(String actualFinishTime) {
    this.actualFinishTime = actualFinishTime;
  }

  public void setTCSFl(String TCSFl) {
    this.TCSFl = TCSFl;
  }

  public void setDelete(int idx, String pVal) {
      WorkOrderDetailData woDD = (WorkOrderDetailData)itemizedService.get(idx);
      if(woDD == null) return;
      if (RefCodeNames.STATUS_CD.INACTIVE.equals(pVal)) {
        woDD.setStatusCd(RefCodeNames.STATUS_CD.INACTIVE);
      }
    }

    public String getDelete(int idx) {
      WorkOrderDetailData woDD = (WorkOrderDetailData)itemizedService.get(idx);
      if(woDD == null) return RefCodeNames.STATUS_CD.ACTIVE;
      if (RefCodeNames.STATUS_CD.INACTIVE.equals(woDD.getStatusCd())) {
        return RefCodeNames.STATUS_CD.INACTIVE;
      } else {
        return RefCodeNames.STATUS_CD.ACTIVE;
      }
    }
}

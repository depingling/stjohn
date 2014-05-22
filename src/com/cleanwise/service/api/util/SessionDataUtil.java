/**
 * Title: SessionDataUtil 
 * Description: This is the centralized class that holds the information about the values that are 
 * in the session.
 */
package com.cleanwise.service.api.util;

import java.util.Map;
import java.util.HashMap;

import com.cleanwise.service.api.dto.LocationBudgetChartDto;
import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.dto.OrderSearchDto;
import com.cleanwise.service.api.dto.ReportingDto;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.StoreMessageViewVector;
import com.cleanwise.view.forms.CustAcctMgtReportForm;
import com.cleanwise.view.forms.EditOrderGuideForm;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.RemoteWebClient;

public class SessionDataUtil {
	
	//Begin: variables to hold 'Change Location/Specify Location'
	private Map<String,LocationSearchDto> _locationSearchDtoMap;
	//Variable to hold the parent page location of Last selected 'Specify Location' option.
	private String _pageForSpecifyLocation;
	//End:
	
	//Begin: Variables to hold Previous action values.
	private String _previousReportingAction;	
	private String _previousDocumentationAction;	
	private String _previousShoppingAction;	
	private String _previousDashboardAction;
	//End: Variables to hold Previous action values. 
	
	//Begin: Orders
	private OrderSearchDto _orderSearchDto;
	private OrderSearchDto _futureOrderSearchDto;
	private String _previousOrdersAction;
	private String _orderId;
	private String _lastViewedOrderScheduleId;
	private String _lastViewedOrderScheduleType;
	private OrderItemDescDataVector  _orderItemDescList;
	//End: Orders
	
	private ReportingDto _ordersGlanceReportingDto;
	private ReportingDto _budgetsGlanceReportingDto;
	
	private ReportingDto _genericReportingDto;
	
	//Begin: Location Budget
	private LocationBudgetChartDto _locationBudgetChartDto;
	private String _showChart;
	//End: Location Budget
	
	//Begin: Product Catalog
	private UserShopForm _userShopForm;
	private String previousCategory;
	private Integer _allProductsCount;
	//End: Product Catalog
	
	//Begin: Shopping Cart
	private ShoppingCartForm _shoppingCartForm;
    private boolean _excludeOrderFromBudget;
	//End: Shopping Cart
	
	//Begin: Checkout
	private boolean _purchaseInProgress;
	//End: Checkout
	
	//Begin: Shopping List
	private EditOrderGuideForm _editOrderGuideForm;
	private int _rememberShoppingList;
	private int _currentShoppingListId;
	private int[] _itemIds;
	private String[] _quantity;
	private int[] _orderNumbers;
	private boolean _actionProductSearch;
	private String _productSearchField;
	private String _productSearchValue;
	private Map<Integer,Integer> _userShoppingListIds;
	private Map<Integer,Integer> _templateShoppingListIds;
	//End: Shopping List
	
	//Begin: Reporting - Standard Reports
	private boolean _initReportControls = true;
	private CustAcctMgtReportForm _customerReportingForm;
	private boolean _reportIsInProgress;
	private int _lastSelectedReportId;
	//End: Reporting - Standard Reports
	
	private boolean _configuredForCorporateOrders = false;
	
	private String _corporateOrderReleaseDate;
	private String _corporateOrderOpenDate;
	
	private String _headerLogo;
	
	//There are occasions when errors need to be persisted across redirect calls.  Saving these
	//errors in the request dowsn't work in that situation, since the redirect issues a new request
	//and thus any errors saved in the old request are lost.  This data element can be used to
	//persist errors across requests.  Note that if possible the Struts framework should be used
	//to handle errors, and this data element should only be employed in situations when the Struts 
	//framework doesn't support the required functionality.  It is declared as an object to prevent
	//tying this class to the Struts framework
	private Object _errors;
	
	private String _selectedMainTab = "";
	private String _selectedSubTab = "";
	private String _selectedSubSubTab = "";
	
	private String _previousShoppingModuleTab = "";
	
	private boolean _webUI;
	
	private String panelProductCatalog = Constants.EXPAND_PANEL;
	private String panelShoppingList = Constants.EXPAND_PANEL;
	
	//interstitial (i.e. forced read) message info
	private StoreMessageViewVector _interstitialMessages = null;
	private Integer _interstitialMessageIndex = null;
	
	//Begin: Remote Access  (Ex: For Neptune Remote Connection)
		private String _serviceTicketNumbers;
		private boolean _isRemoteAccess;
		private RemoteWebClient _remoteWebClient;
		private boolean isContentOnly;
	//End: Remote Access 
		//STJ-5261 For mobile All- Orders.
		//Initially we will be showing first 10 orders.
		//TODO:Change to constants
		private int _ordersLength = 10;
		private String _selectedMobileMainTab;
		private int _locationsLength = 10;
		private String _mobileLocationSelected;
		private SiteData _specifiedLocation;
	    
		public SiteData getSpecifiedLocation() {
			return _specifiedLocation;
		}

		public void setSpecifiedLocation(SiteData specifiedLocation) {
			_specifiedLocation = specifiedLocation;
		}

		
	    public String getMobileLocationSelected() {
			return _mobileLocationSelected;
		}

		public void setMobileLocationSelected(String mobileLocationSelected) {
			_mobileLocationSelected = mobileLocationSelected;
		}

		public int getLocationsLength() {
			return _locationsLength;
		}

		public void setLocationsLength(int locationsLength) {
			_locationsLength = locationsLength;
		}

		public String getSelectedMobileMainTab() {
			return _selectedMobileMainTab;
		}

		public void setSelectedMobileMainTab(String selectedMobileMainTab) {
			_selectedMobileMainTab = selectedMobileMainTab;
		}

		public int getOrdersLength() {
			return _ordersLength;
		}

		public void setOrdersLength(int ordersLength) {
			_ordersLength = ordersLength;
		}
	/**
	 * @return the corporateOrderReleaseDate
	 */
	public final String getCorporateOrderReleaseDate() {
		return _corporateOrderReleaseDate;
	}

	/**
	 * @param corporateOrderReleaseDate the corporateOrderReleaseDate to set
	 */
	public final void setCorporateOrderReleaseDate(
			String corporateOrderReleaseDate) {
		this._corporateOrderReleaseDate = corporateOrderReleaseDate;
	}

	/**
	 * @return the corporateOrderOpenDate
	 */
	public final String getCorporateOrderOpenDate() {
		return _corporateOrderOpenDate;
	}

	/**
	 * @param corporateOrderOpenDate the corporateOrderOpenDate to set
	 */
	public final void setCorporateOrderOpenDate(String corporateOrderOpenDate) {
		this._corporateOrderOpenDate = corporateOrderOpenDate;
	}

	public int getRememberShoppingList() {
		return _rememberShoppingList;
	}

	public void setRememberShoppingList(int rememberShoppingList) {
		_rememberShoppingList = rememberShoppingList;
	}

	public EditOrderGuideForm getEditOrderGuideForm() {
		return _editOrderGuideForm;
	}

	public void setEditOrderGuideForm(EditOrderGuideForm _editOrderGuideForm) {
		this._editOrderGuideForm = _editOrderGuideForm;
	}

	
	/**
	 * @return the shoppingCartForm
	 */
	public ShoppingCartForm getShoppingCartForm() {
		return _shoppingCartForm;
	}

	/**
	 * @param pShoppingCartForm the shoppingCartForm to set
	 */
	public void setShoppingCartForm(ShoppingCartForm pShoppingCartForm) {
		_shoppingCartForm = pShoppingCartForm;
	}

	public LocationBudgetChartDto getLocationBudgetChartDto() {
		return _locationBudgetChartDto;
	}

	public void setLocationBudgetChartDto(
			LocationBudgetChartDto _locationBudgetChartDto) {
		this._locationBudgetChartDto = _locationBudgetChartDto;
	}

	public String getShowChart() {
		return _showChart;
	}

	public void setShowChart(String _showChart) {
		this._showChart = _showChart;
	}

	/**
	 * @return the userShopForm
	 */
	public UserShopForm getUserShopForm() {
		return _userShopForm;
	}

	/**
	 * @param userShopForm the userShopForm to set
	 */
	public void setUserShopForm(UserShopForm _userShopForm) {
		this._userShopForm = _userShopForm;
	}

	/**
	 * 
	 * @return ordersSearchDto
	 */
	public OrderSearchDto getOrderSearchDto() {
		return _orderSearchDto;
	}

	/**
	 * 
	 * @param orderSearchDto - OrderSearchDto to set
	 */
	public void setOrderSearchDto(OrderSearchDto orderSearchDto) {
		_orderSearchDto = orderSearchDto;
	}
	
	/**
	 * 
	 * @return futureOrderSearchDto
	 */
	public OrderSearchDto getFutureOrderSearchDto() {
		return _futureOrderSearchDto;
	}

	/**
	 * 
	 * @param orderSearchDto - OrderSearchDto to set
	 */
	public void setFutureOrderSearchDto(OrderSearchDto futureOrderSearchDto) {
		_futureOrderSearchDto = futureOrderSearchDto;
	}
	

	/**
	 * @return the previousOrdersAction
	 */
	public String getPreviousOrdersAction() {
		return _previousOrdersAction;
	}

	/**
	 * @param pPreviousOrdersAction the previousOrdersAction to set
	 */
	public void setPreviousOrdersAction(String pPreviousOrdersAction) {
		_previousOrdersAction = pPreviousOrdersAction;
	}
	
	public String getOrderId() {
		return _orderId;
	}

	/**
	 * @param pPreviousOrdersAction the previousOrdersAction to set
	 */
	public void setOrderId(String pOrderId) {
		_orderId = pOrderId;
	}

	/**
	 * @param lastViewedOrderScheduleId the lastViewedOrderScheduleId to set
	 */
	public void setLastViewedParOrderScheduleId(String lastViewedOrderScheduleId) {
		_lastViewedOrderScheduleId = lastViewedOrderScheduleId;
		if (Utility.isSet(_lastViewedOrderScheduleId)) {
			_lastViewedOrderScheduleType = RefCodeNames.FUTURE_ORDER_TYPE.CORPORATE_ORDER_SCHEDULE;
		}
		else {
			_lastViewedOrderScheduleType = null;
		}
	}
	/**
	 * @param lastViewedOrderScheduleId the lastViewedOrderScheduleId to set
	 */
	public void setLastViewedOrderScheduleId(String lastViewedOrderScheduleId) {
		_lastViewedOrderScheduleId = lastViewedOrderScheduleId;
		if (Utility.isSet(_lastViewedOrderScheduleId)) {
			_lastViewedOrderScheduleType = RefCodeNames.FUTURE_ORDER_TYPE.SHOPPING_LIST_SCHEDULE;
		}
		else {
			_lastViewedOrderScheduleType = null;
		}
	}

	/**
	 * @return the lastViewedOrderScheduleId
	 */
	public String getLastViewedOrderScheduleId() {
		return _lastViewedOrderScheduleId;
	}

	/**
	 * @return the lastViewedOrderScheduleType
	 */
	public String getLastViewedOrderScheduleType() {
		return _lastViewedOrderScheduleType;
	}

	/**
	 * @return the previousReportingAction
	 */
	public String getPreviousReportingAction() {
		return _previousReportingAction;
	}

	/**
	 * @param previousReportingAction the previousReportingAction to set
	 */
	public void setPreviousReportingAction(String previousReportingAction) {
		_previousReportingAction = previousReportingAction;
	}

	/**
	 * @return the previousDocumentationAction
	 */
	public String getPreviousDocumentationAction() {
		return _previousDocumentationAction;
	}

	/**
	 * @param previousDocumentationAction the previousDocumentationAction to set
	 */
	public void setPreviousDocumentationAction(String previousDocumentationAction) {
		_previousDocumentationAction = previousDocumentationAction;
	}

	/**
	 * @return the previousShoppingAction
	 */
	public String getPreviousShoppingAction() {
		return _previousShoppingAction;
	}

	/**
	 * @param previousShoppingAction the previousShoppingAction to set
	 */
	public void setPreviousShoppingAction(String previousShoppingAction) {
		_previousShoppingAction = previousShoppingAction;
	}

	/**
	 * @return the previousDashboardAction
	 */
	public String getPreviousDashboardAction() {
		return _previousDashboardAction;
	}

	/**
	 * @param previousDashboardAction the previousDashboardAction to set
	 */
	public void setPreviousDashboardAction(String previousDashboardAction) {
		_previousDashboardAction = previousDashboardAction;
	}

	/**
	 * @return the _ordersGlanceReportingDto
	 */
	public final ReportingDto getOrdersGlanceReportingDto() {
		if(_ordersGlanceReportingDto==null) {
			_ordersGlanceReportingDto = new ReportingDto();
		}
		return _ordersGlanceReportingDto;
	}

	/**
	 * @param ordersGlanceReportingDto the _ordersGlanceReportingDto to set
	 */
	public final void setOrdersGlanceReportingDto(
			ReportingDto ordersGlanceReportingDto) {
		_ordersGlanceReportingDto = ordersGlanceReportingDto;
	}

	/**
	 * @return the _budgetsGlanceReportingDto
	 */
	public final ReportingDto getBudgetsGlanceReportingDto() {
		return _budgetsGlanceReportingDto;
	}

	/**
	 * @param budgetsGlanceReportingDto the _budgetsGlanceReportingDto to set
	 */
	public final void setBudgetsGlanceReportingDto(
			ReportingDto budgetsGlanceReportingDto) {
		_budgetsGlanceReportingDto = budgetsGlanceReportingDto;
	}

	/**
	 * @return the _genericReportingDto
	 */
	public final ReportingDto getGenericReportingDto() {
		return _genericReportingDto;
	}

	/**
	 * @param genericReportingDto the _genericReportingDto to set
	 */
	public final void setGenericReportingDto(ReportingDto genericReportingDto) {
		_genericReportingDto = genericReportingDto;
	}

	public String getPreviousCategory() {
		return previousCategory;
	}

	public void setPreviousCategory(String previousCategory) {
		this.previousCategory = previousCategory;
	}
	/**
	 * @return the purchaseInProgress
	 */
	public boolean isPurchaseInProgress() {
		return _purchaseInProgress;
	}

	/**
	 * @param pPurchaseInProgress the purchaseInProgress to set
	 */
	public synchronized void setPurchaseInProgress(boolean pPurchaseInProgress) {
		_purchaseInProgress = pPurchaseInProgress;
	}

	/**
	 * @return the initReportControls
	 */
	public boolean isInitReportControls() {
		return _initReportControls;
	}

	/**
	 * @param pInitReportControls the initReportControls to set
	 */
	public void setInitReportControls(boolean pInitReportControls) {
		_initReportControls = pInitReportControls;
	}

	/**
	 * @return the reportIsInProgress
	 */
	public boolean isReportIsInProgress() {
		return _reportIsInProgress;
	}

	/**
	 * @param pReportIsInProgress the reportIsInProgress to set
	 */
	public void setReportIsInProgress(boolean pReportIsInProgress) {
		_reportIsInProgress = pReportIsInProgress;
	}

	/**
	 * @return the customerReportingForm
	 */
	public CustAcctMgtReportForm getCustomerReportingForm() {
		return _customerReportingForm;
	}

	/**
	 * @param pCustomerReportingForm the customerReportingForm to set
	 */
	public void setCustomerReportingForm(
			CustAcctMgtReportForm pCustomerReportingForm) {
		_customerReportingForm = pCustomerReportingForm;
	}

	public boolean isConfiguredForCorporateOrders() {
		return _configuredForCorporateOrders;
	}

	public void setConfiguredForCorporateOrders(boolean configuredForCorporateOrders) {
		_configuredForCorporateOrders = configuredForCorporateOrders;
	}
	/**
	 * @return the allProductsCount
	 */
	public Integer getAllProductsCount() {
		return _allProductsCount;
	}

	/**
	 * @param allProductsCount the allProductsCount to set
	 */
	public void setAllProductsCount(Integer allProductsCount) {
		_allProductsCount = allProductsCount;
	}
	/**
	 * @return the currentShoppingListId
	 */
	public int getCurrentShoppingListId() {
		return _currentShoppingListId;
	}

	/**
	 * @param currentShoppingListId the currentShoppingListId to set
	 */
	public void setCurrentShoppingListId(int currentShoppingListId) {
		_currentShoppingListId = currentShoppingListId;
	}
	public boolean isActionProductSearch() {
		return _actionProductSearch;
	}

	public void setActionProductSearch(boolean actionProductSearch) {
		_actionProductSearch = actionProductSearch;
	}

	public int[] getOrderNumbers() {
		return _orderNumbers;
	}

	public void setOrderNumbers(int[] orderNumbers) {
		_orderNumbers = orderNumbers;
	}
	public int[] getItemIds() {
		return _itemIds;
	}

	public void setItemIds(int[] itemsIds) {
		_itemIds = itemsIds;
	}

	public String[] getQuantity() {
		return _quantity;
	}

	public void setQuantity(String[] quantity) {
		_quantity = quantity;
	}

	public String getHeaderLogo() {
		return _headerLogo;
	}

	public void setHeaderLogo(String headerLogo) {
		_headerLogo = headerLogo;
	}
	
	public boolean isExcludeOrderFromBudget() {
		return _excludeOrderFromBudget;
	}

	public void setExcludeOrderFromBudget(boolean pExcludeOrderFromBudget) {
		_excludeOrderFromBudget = pExcludeOrderFromBudget;
	}

	/**
	 * @return the errors
	 */
	public Object getErrors() {
		return _errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(Object errors) {
		_errors = errors;
	}
	
	/**
	 * @return the selectedMainTab
	 */
	public String getSelectedMainTab() {
		return _selectedMainTab;
	}

	/**
	 * @param selectedMainTab the selectedMainTab to set
	 */
	public void setSelectedMainTab(String selectedMainTab) {
		_selectedMainTab = selectedMainTab;
	}

	/**
	 * @return the selectedSubTab
	 */
	public String getSelectedSubTab() {
		return _selectedSubTab;
	}

	/**
	 * @param selectedSubTab the selectedSubTab to set
	 */
	public void setSelectedSubTab(String selectedSubTab) {
		_selectedSubTab = selectedSubTab;
	}

	/**
	 * @return the electedSubSubTab
	 */
	public String getSelectedSubSubTab() {
		return _selectedSubSubTab;
	}

	/**
	 * @param selectedSubSubTab the selectedSubSubTab to set
	 */
	public void setSelectedSubSubTab(String selectedSubSubTab) {
		_selectedSubSubTab = selectedSubSubTab;
	}

	/**
	 * @return the previousShoppingModuleTab
	 */
	public String getPreviousShoppingModuleTab() {
		return _previousShoppingModuleTab;
	}

	/**
	 * @param previousShoppingModuleTab the PreviousShoppingModuleTab to set
	 */
	public void setPreviousShoppingModuleTab(String previousShoppingModuleTab) {
		_previousShoppingModuleTab = previousShoppingModuleTab;
	}

	 public boolean isWebUI() {
 		return _webUI;
	 }
	 
	/**
	* @param webUI the webUI to set
	*/
	public void setWebUI(boolean webUI) {
		_webUI = webUI;
	}

	/**
	 * @return the interstitialMessages
	 */
	public StoreMessageViewVector getInterstitialMessages() {
		return _interstitialMessages;
	}

	/**
	 * @param interstitialMessages the interstitialMessages to set
	 */
	public void setInterstitialMessages(StoreMessageViewVector interstitialMessages) {
		_interstitialMessages = interstitialMessages;
	}

	/**
	 * @return the interstitialMessageIndex
	 */
	public Integer getInterstitialMessageIndex() {
		return _interstitialMessageIndex;
	}

	/**
	 * @param interstitialMessageIndex the interstitialMessageIndex to set
	 */
	public void setInterstitialMessageIndex(Integer interstitialMessageIndex) {
		_interstitialMessageIndex = interstitialMessageIndex;
	}
	
    /**
     * <code>getOrderItemDescList</code> method.
     *
     * @return a <code>OrderItemDescDataVector</code> value
     */
    public OrderItemDescDataVector getOrderItemDescList() {
        if( null == _orderItemDescList) {
            _orderItemDescList = new OrderItemDescDataVector();
        }
        return (_orderItemDescList);
    }

    /**
     * <code>setOrderItemDescList</code> method.
     *
     * @param pVal a <code>OrderItemDescDataVector</code> value
     */
    public void setOrderItemDescList(OrderItemDescDataVector pVal) {
        _orderItemDescList = pVal;
    }
    
    /**
	 * @return the serviceTicketNumbers
	 */
	public String getServiceTicketNumbers() {
		return _serviceTicketNumbers;
	}

	/**
	 * @param pServiceTicketNumbers the serviceTicketNumbers to set
	 */
	public void setServiceTicketNumbers(String pServiceTicketNumbers) {
		_serviceTicketNumbers = pServiceTicketNumbers;
	}

	/**
	 * @return the isRemoteAccess
	 */
	public boolean isRemoteAccess() {
		return _isRemoteAccess;
	}

	/**
	 * @param pIsRemoteAccess the isRemoteAccess to set
	 */
	public void setRemoteAccess(boolean pIsRemoteAccess) {
		_isRemoteAccess = pIsRemoteAccess;
	}

	/**
	 * @return the remoteWebClient
	 */
	public RemoteWebClient getRemoteWebClient() {
		return _remoteWebClient;
	}

	/**
	 * @param pRemoteWebClient the remoteWebClient to set
	 */
	public void setRemoteWebClient(RemoteWebClient pRemoteWebClient) {
		_remoteWebClient = pRemoteWebClient;
	}

	/**
	 * @return the isContentOnly
	 */
	public boolean isContentOnly() {
		return isContentOnly;
	}

	/**
	 * @param pIsContentOnly the isContentOnly to set
	 */
	public void setContentOnly(boolean pIsContentOnly) {
		isContentOnly = pIsContentOnly;
	}

	/**
	 * @return the panelProductCatalog
	 */
	public final String getPanelProductCatalog() {
		return panelProductCatalog;
	}

	/**
	 * @param panelProductCatalog the panelProductCatalog to set
	 */
	public final void setPanelProductCatalog(String panelProductCatalog) {
		this.panelProductCatalog = panelProductCatalog;
	}

	/**
	 * @return the panelShoppingList
	 */
	public final String getPanelShoppingList() {
		return panelShoppingList;
	}

	/**
	 * @param panelShoppingList the panelShoppingList to set
	 */
	public final void setPanelShoppingList(String panelShoppingList) {
		this.panelShoppingList = panelShoppingList;
	}

	/**
	 * @return the lastSelectedReportId
	 */
	public int getLastSelectedReportId() {
		return _lastSelectedReportId;
	}

	/**
	 * @param lastSelectedReportId the lastSelectedReportId to set
	 */
	public void setLastSelectedReportId(int pLastSelectedReportId) {
		_lastSelectedReportId = pLastSelectedReportId;
	}

	/**
	 * @return the locationSearchDtoMap
	 */
	public Map<String, LocationSearchDto> getLocationSearchDtoMap() {
		if(_locationSearchDtoMap == null) {
			_locationSearchDtoMap = new HashMap<String,LocationSearchDto>();
		}
		return _locationSearchDtoMap;
	}

	/**
	 * @param pLocationSearchDtoMap the locationSearchDtoMap to set
	 */
	public void setLocationSearchDtoMap(
			Map<String, LocationSearchDto> pLocationSearchDtoMap) {
		_locationSearchDtoMap = pLocationSearchDtoMap;
	}

	/**
	 * @return the pageForSpecifyLocation
	 */
	public String getPageForSpecifyLocation() {
		return _pageForSpecifyLocation;
	}

	/**
	 * @param pPageForSpecifyLocation the pageForSpecifyLocation to set
	 */
	public void setPageForSpecifyLocation(String pPageForSpecifyLocation) {
		_pageForSpecifyLocation = pPageForSpecifyLocation;
	}

	/**
	 * @return the userShoppingList
	 */
	public Map<Integer, Integer> getUserShoppingListIds() {
		if(_userShoppingListIds==null) {
			_userShoppingListIds = new HashMap<Integer,Integer>();
		}
		return _userShoppingListIds;
	}

	/**
	 * @param pUserShoppingList the userShoppingList to set
	 */
	public void setUserShoppingListIds(Map<Integer, Integer> pUserShoppingListIds) {
		_userShoppingListIds = pUserShoppingListIds;
	}

	/**
	 * @return the templateShoppingListIds
	 */
	public Map<Integer, Integer> getTemplateShoppingListIds() {
		if(_templateShoppingListIds==null) {
			_templateShoppingListIds = new HashMap<Integer,Integer>();
		}
		return _templateShoppingListIds;
	}

	/**
	 * @param pTemplateShoppingListIds the templateShoppingListIds to set
	 */
	public void setTemplateShoppingListIds(
			Map<Integer, Integer> pTemplateShoppingListIds) {
		_templateShoppingListIds = pTemplateShoppingListIds;
	}

	/**
	 * @return the productSearchField
	 */
	public String getProductSearchField() {
		return _productSearchField;
	}

	/**
	 * @param pProductSearchField the productSearchField to set
	 */
	public void setProductSearchField(String pProductSearchField) {
		_productSearchField = pProductSearchField;
	}

	/**
	 * @return the productSearchValue
	 */
	public String getProductSearchValue() {
		return _productSearchValue;
	}

	/**
	 * @param pProductSearchValue the productSearchValue to set
	 */
	public void setProductSearchValue(String pProductSearchValue) {
		_productSearchValue = pProductSearchValue;
	}
	
	
}

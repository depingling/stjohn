/**
 * Title: ShoppingForm 
 * Description: This is the Struts ActionForm class handling the ESW shopping functionality.
 *
 */

package com.espendwise.view.forms.esw;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.forms.EditOrderGuideForm;
import com.cleanwise.view.forms.QuickOrderForm;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.utils.Constants;

/**
 * Implementation of <code>ActionForm</code> that handles shopping
 * functionality.
 */
public final class ShoppingForm extends EswForm {

	private static final long serialVersionUID = 1L;
	
	private String _productSearchValue;
	private String _productSearchField;
	private List<LabelValueBean> _productSearchFieldChoices;
	
	//Begin: Product Catalog
	private UserShopForm _userShopForm;
	private String _catalogItemKey;
	private boolean _addCatalogItems;
	private String _greenCertified;
	//End: Product Catalog
	
	//Begin: Shopping Cart
	private ShoppingCartForm _shoppingCartForm;
	private String _itemIdToRemove;
    private boolean _excludeOrderFromBudget;
	//End: Shopping Cart
	
	//Begin: Par Order
	private String _orderReleaseDate;
	private String _orderClosesInDays;
	//End: Par Order

	//Begin: Product Detail
	private int itemId;
	private String quantityDetail;
	private String msdsStorageTypeCd;
	private String dedStorageTypeCd;
	private String specStorageTypeCd;
	//End: Product Detail
	
	//Begin: Shopping List
	private String[] _orderGuideNames;
	private int[] _userOrdGuideIds;
	private String[] _userOrdGuideNames;
	private List<LabelValueBean> _shoppingListOptions;
	private EditOrderGuideForm _editOrderGuideForm;
	private int _selectedShoppingList;
	//End: Shopping List
	
	//Begin: Quick Shop
		private boolean _showQuickShop;
		private QuickOrderForm _quickOrderForm;
		//End: Quick Shop
		
		//expand/collapse left menu
		private String panelProductCatalog;
		private String panelShoppingList;

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

	public int getSelectedShoppingList() {
		return _selectedShoppingList;
	}

	public void setSelectedShoppingList(int selectedShoppingList) {
		_selectedShoppingList = selectedShoppingList;
	}

	
	public EditOrderGuideForm getEditOrderGuideForm() {
		return _editOrderGuideForm;
	}

	public void setEditOrderGuideForm(EditOrderGuideForm _editOrderGuideForm) {
		this._editOrderGuideForm = _editOrderGuideForm;
	}

	/**
	 * 
	 * @return orderGuideNames
	 */
	public String[] getOrderGuideNames() {
		return _orderGuideNames;
	}
	
	/**
	 * @param orderGuideNames the orderGuideNames to set
	 */
	public void setOrderGuideNames(String[] orderGuideNames) {
		_orderGuideNames = orderGuideNames;
	}
	
	/**
	 * 
	 * @return addCatalogItems
	 */
	public boolean isAddCatalogItems() {
		return _addCatalogItems;
	}

	/**
	 * 
	 * @param addCatalogItems
	 */
	public void setAddCatalogItems(boolean addCatalogItems) {
		_addCatalogItems = addCatalogItems;
	}


	/**
	 * @return _userOrdGuideNames
	 */
	public String[] getUserOrdGuideNames() {
		return _userOrdGuideNames;
	}

	/**
	 * @param userOrdGuideNames the userOrdGuideNames to set
	 */
	public void setUserOrdGuideNames(String[] userOrdGuideNames) {
		_userOrdGuideNames = userOrdGuideNames;
	}

	/**
	 * @return userOrdGuideIds
	 */
	public int[] getUserOrdGuideIds() {
		return _userOrdGuideIds;
	}

	/**
	 * @param userOrdGuideIds the userOrdGuideIds to set
	 */
	public void setUserOrdGuideIds(int[] userOrdGuideIds) {
		_userOrdGuideIds = userOrdGuideIds;
	}
	
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getQuantityDetail() {
        return quantityDetail;
    }

    public void setQuantityDetail(String quantityDetail) {
        this.quantityDetail = quantityDetail;
    }
    
	/**
	 * @return the catalogItemKey
	 */
	public String getCatalogItemKey() {
		return _catalogItemKey;
	}

	/**
	 * @param catalogItemKey the catalogItemKey to set
	 */
	public void setCatalogItemKey(String catalogItemKey) {
		this._catalogItemKey = catalogItemKey;
	}

	/**
	 * @return the productSearchFieldChoices
	 */
	public List<LabelValueBean> getProductSearchFieldChoices() {
		return _productSearchFieldChoices;
	}

	/**
	 * @param productSearchFieldChoices the productSearchFieldChoices to set
	 */
	public void setProductSearchFieldChoices(
			List<LabelValueBean> productSearchFieldChoices) {
		_productSearchFieldChoices = productSearchFieldChoices;
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
	public void setUserShopForm(UserShopForm userShopForm) {
		this._userShopForm = userShopForm;
	}

	/**
	 * @return the productSearchValue
	 */
	public String getProductSearchValue() {
		return _productSearchValue;
	}

	/**
	 * @param productSearchValue
	 *            the productSearchValue to set
	 */
	public void setProductSearchValue(String productSearchValue) {
		_productSearchValue = productSearchValue;
	}

	/**
	 * @return the productSearchField
	 */
	public String getProductSearchField() {
		return _productSearchField;
	}

	/**
	 * @param productSearchField
	 *            the productSearchField to set
	 */
	public void setProductSearchField(String productSearchField) {
		_productSearchField = productSearchField;
	}


	/**
	 * @return the shoppingCartForm
	 */
	public ShoppingCartForm getShoppingCartForm() {
		if(_shoppingCartForm==null) {
			_shoppingCartForm = new ShoppingCartForm();
		}
		return _shoppingCartForm;
	}

	/**
	 * @param pShoppingCartForm the shoppingCartForm to set
	 */
	public void setShoppingCartForm(ShoppingCartForm pShoppingCartForm) {
		_shoppingCartForm = pShoppingCartForm;
	}

	/**
	 * @return the itemIdToRemove
	 */
	public String getItemIdToRemove() {
		return _itemIdToRemove;
	}

	/**
	 * @param pItemIdToRemove the itemIdToRemove to set
	 */
	public void setItemIdToRemove(String pItemIdToRemove) {
		_itemIdToRemove = pItemIdToRemove;
	}
	
	/**
	 * @return the orderReleaseDate
	 */
	public String getOrderReleaseDate() {
		return _orderReleaseDate;
	}

	/**
	 * @param pOrderReleaseDate the orderReleaseDate to set
	 */
	public void setOrderReleaseDate(String pOrderReleaseDate) {
		_orderReleaseDate = pOrderReleaseDate;
	}

	/**
	 * @return the orderClosesInDays
	 */
	public String getOrderClosesInDays() {
		return _orderClosesInDays;
	}

	/**
	 * @param pOrderClosesInDays the orderClosesInDays to set
	 */
	public void setOrderClosesInDays(String pOrderClosesInDays) {
		_orderClosesInDays = pOrderClosesInDays;
	}
	
	/**
	 * @return the shoppingListOptions
	 */
	public List<LabelValueBean> getShoppingListOptions() {
		return _shoppingListOptions;
	}

	/**
	 * @param pShoppingListOptions the shoppingListOptions to set
	 */
	public void setShoppingListOptions(List<LabelValueBean> pShoppingListOptions) {
		_shoppingListOptions = pShoppingListOptions;
	}
	public String getGreenCertified() {
		return _greenCertified;
	}

	public void setGreenCertified(String greenCertified) {
		_greenCertified = greenCertified;
	}
	
	
    public boolean isExcludeOrderFromBudget() {
        return _excludeOrderFromBudget;
    }


    public void setExcludeOrderFromBudget(boolean pExcludeOrderFromBudget) {
        _excludeOrderFromBudget = pExcludeOrderFromBudget;
    }
    public boolean isShowQuickShop() {
		return _showQuickShop;
	}

	public void setShowQuickShop(boolean showQuickShop) {
		_showQuickShop = showQuickShop;
	}
	public QuickOrderForm getQuickOrderForm() {
    	if(_quickOrderForm==null) {
    		_quickOrderForm = new QuickOrderForm();
		}
		return _quickOrderForm;
	}

	public void setQuickOrderForm(QuickOrderForm quickOrderForm) {
		_quickOrderForm = quickOrderForm;
	}
	/**
	 * Reset all properties to their default values.
	 * 
	 * @param mapping
	 *            The mapping used to select this instance
	 * @param request
	 *            The servlet request we are processing
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		//Get needed forms from the session.
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		//If _userShopForm the session is null, create new one and put it in session.
		_userShopForm = sessionDataUtil.getUserShopForm();
		if(_userShopForm == null) {
			_userShopForm = new UserShopForm();
			sessionDataUtil.setUserShopForm(_userShopForm);
		}
		
		//If _shoppingCartForm in the session is null, create new one and put it in session.
		_shoppingCartForm = sessionDataUtil.getShoppingCartForm();
		if(_shoppingCartForm==null) {
			_shoppingCartForm = new ShoppingCartForm();
			sessionDataUtil.setShoppingCartForm(_shoppingCartForm);
		}
		//Reset Shopping Messages.
		_shoppingCartForm.setConfirmMessage(null);
		
		_editOrderGuideForm= sessionDataUtil.getEditOrderGuideForm();
		if(_editOrderGuideForm==null) {
			_editOrderGuideForm = new EditOrderGuideForm();
			sessionDataUtil.setEditOrderGuideForm(_editOrderGuideForm);
		}
		
		//STJ-5666
		_productSearchValue = sessionDataUtil.getProductSearchValue();
		_productSearchField = sessionDataUtil.getProductSearchField();
		
		_catalogItemKey = null;
	}

	/**
	 * Validate the properties that have been set from this HTTP request, and
	 * return an <code>ActionErrors</code> object that encapsulates any
	 * validation errors that have been found. If no errors are found, return
	 * <code>null</code> or an <code>ActionErrors</code> object with no recorded
	 * error messages.
	 * 
	 * @param mapping
	 *            The mapping used to select this instance
	 * @param request
	 *            The servlet request we are processing
	 * @return Description of the Returned Value
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// no validation is performed at the form level, so return null.
		ActionErrors returnValue = null;
		return returnValue;
	}
	
	public String getMsdsStorageTypeCd() {
		return msdsStorageTypeCd;
	}
	
	public void setMsdsStorageTypeCd(String msdsStorageTypeCd) {
		this.msdsStorageTypeCd = msdsStorageTypeCd;
	}
	
	public String getDedStorageTypeCd() {
		return dedStorageTypeCd;
	}
	
	public void setDedStorageTypeCd(String dedStorageTypeCd) {
		this.dedStorageTypeCd = dedStorageTypeCd;
	}

	public String getSpecStorageTypeCd() {
		return specStorageTypeCd;
	}
	
	public void setSpecStorageTypeCd(String specStorageTypeCd) {
		this.specStorageTypeCd = specStorageTypeCd;
	}
}

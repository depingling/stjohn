/*
 * AggregateItemMgrForm.java
 *
 * Created on December 1, 2003, 5:10 PM
 */

package com.cleanwise.view.forms;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.SelectableObjects;
import com.cleanwise.service.api.value.*;
import java.util.List;

/**
 *
 * @author  bstevens
 */
public class AggregateItemMgrForm extends ActionForm {
    
    /** Holds value of property distributors. */
    private SelectableObjects distributors;
    
    /** Holds value of property accounts. */
    private SelectableObjects accounts;
    
    /** Holds value of property items. */
    private ProductDataVector items;
    
    /** Holds value of property wizardStep. */
    private String wizardStep;
    
    /** Holds value of property distSearchType. */
    private String distSearchType;
    
    /** Holds value of property distSearchField. */
    private String distSearchField;
    
    /** Holds value of property distSearchState. */
    private String distSearchState;
    
    /** Holds value of property distSearchPostalCode. */
    private String distSearchPostalCode;
    
    /** Holds value of property distSearchGroupId. */
    private String distSearchGroupId;
    
    /** Holds value of property distSearchCounty. */
    private String distSearchCounty;
    
    /** Holds value of property acctSearchField. */
    private String acctSearchField;
    
    /** Holds value of property acctSearchType. */
    private String acctSearchType;
    
    /** Holds value of property acctSearchGroupId. */
    private String acctSearchGroupId;
    
    /** Holds value of property itemSearchCategory. */
    private String itemSearchCategory;
    
    /** Holds value of property itemSearchShortDesc. */
    private String itemSearchShortDesc;
    
    /** Holds value of property itemSearchItemPropertyName. */
    private String itemSearchItemPropertyName;
    
    /** Holds value of property itemSearchItemProperty. */
    private String itemSearchItemProperty;
    
    /** Holds value of property itemSearchLongDesc. */
    private String itemSearchLongDesc;
    
    /** Holds value of property itemSearchManuId. */
    private String itemSearchManuId;
    
    /** Holds value of property itemSearchSku. */
    private String itemSearchSku;
    
    /** Holds value of property itemSearchSkuType. */
    private String itemSearchSkuType = "SystemCustomer";
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        reset();
    }
    public void reset(ActionMapping mapping, ServletRequest request) {
       reset();
    }
    private void reset(){
        if(accounts != null){
            accounts.handleStutsFormResetRequest();
        }
        if(distributors != null){
            distributors.handleStutsFormResetRequest();
        }
        if(aggregateItems != null){
            aggregateItems.handleStutsFormResetRequest();
        }
        tickItemsToCatalog = false;
        tickItemsToContract = false;
        tickItemsToOrderGuide = false;
    }
    
    
    List distributorsNewlySelected;
    public List getDistributorsNewlySelected(){
        return distributorsNewlySelected;
    }
    public void setDistributorsNewlySelected(List value){
        distributorsNewlySelected = value;
    }
    List accountsNewlySelected;
    
    /** Holds value of property aggregateItems. */
    private SelectableObjects aggregateItems;
    
    /** Holds value of property currManagingItem. */
    private ProductData currManagingItem;
    
    /** Holds value of property category. */
    private String category;
    
    /** Holds value of property categoryAction. */
    private String categoryAction;
    
    /** Holds value of property categoryCostCenter. */
    private String categoryCostCenter;
    
    /** Holds value of property tickItemsToContract. */
    private boolean tickItemsToContract;
    
    /** Holds value of property tickItemsToCatalog. */
    private boolean tickItemsToCatalog;
    
    /** Holds value of property tickItemsToOrderGuide. */
    private boolean tickItemsToOrderGuide;
    
    /** Holds value of property catalogSearchField. */
    private String catalogSearchField;
    
    /** Holds value of property catalogSearchType. */
    private String catalogSearchType;
    
    /** Holds value of property catalogs. */
    private SelectableObjects catalogs;
    
    /** Holds value of property catalogsNewlySelected. */
    private List catalogsNewlySelected;
    
    /** Holds value of property usingCatalogSearch. */
    private boolean usingCatalogSearch;

    /**
     * Holds value of property stores.
     */
    private SelectableObjects stores;

    /**
     * Holds value of property storesNewlySelected.
     */
    private List storesNewlySelected;

    /**
     * Holds value of property storeSearchGroupId.
     */
    private String storeSearchGroupId;

    /**
     * Holds value of property storeSearchType.
     */
    private String storeSearchType;

    /**
     * Holds value of property storeSearchField.
     */
    private String storeSearchField;
    
    public List getAccountsNewlySelected(){
        return accountsNewlySelected;
    }
    public void setAccountsNewlySelected(List value){
        accountsNewlySelected = value;
    }
    
    /** Getter for property distributors.
     * @return Value of property distributors.
     *
     */
    public SelectableObjects getDistributors() {
        return this.distributors;
    }
    
    /** Setter for property distributors.
     * @param distributors New value of property distributors.
     *
     */
    public void setDistributors(SelectableObjects distributors) {
        this.distributors = distributors;
    }
    
    /** Getter for property accounts.
     * @return Value of property accounts.
     *
     */
    public SelectableObjects getAccounts() {
        return this.accounts;
    }
    
    /** Setter for property accounts.
     * @param accounts New value of property accounts.
     *
     */
    public void setAccounts(SelectableObjects accounts) {
        this.accounts = accounts;
    }
    
    /** Getter for property items.
     * @return Value of property items.
     *
     */
    public ProductDataVector getItems() {
        return this.items;
    }
    
    /** Setter for property items.
     * @param items New value of property items.
     *
     */
    public void setItems(ProductDataVector items) {
        this.items = items;
    }
    
    /** Getter for property wizardStep.
     * @return Value of property wizardStep.
     *
     */
    public String getWizardStep() {
        return this.wizardStep;
    }
    
    /** Setter for property wizardStep.
     * @param wizardStep New value of property wizardStep.
     *
     */
    public void setWizardStep(String wizardStep) {
        this.wizardStep = wizardStep;
    }
    
    /** Getter for property distSearchType.
     * @return Value of property distSearchType.
     *
     */
    public String getDistSearchType() {
        return this.distSearchType;
    }
    
    /** Setter for property distSearchType.
     * @param distSearchType New value of property distSearchType.
     *
     */
    public void setDistSearchType(String distSearchType) {
        this.distSearchType = distSearchType;
    }
    
    /** Getter for property distSearchField.
     * @return Value of property distSearchField.
     *
     */
    public String getDistSearchField() {
        return this.distSearchField;
    }
    
    /** Setter for property distSearchField.
     * @param distSearchField New value of property distSearchField.
     *
     */
    public void setDistSearchField(String distSearchField) {
        this.distSearchField = distSearchField;
    }
    
    /** Getter for property distSearchState.
     * @return Value of property distSearchState.
     *
     */
    public String getDistSearchState() {
        return this.distSearchState;
    }
    
    /** Setter for property distSearchState.
     * @param distSearchState New value of property distSearchState.
     *
     */
    public void setDistSearchState(String distSearchState) {
        this.distSearchState = distSearchState;
    }
    
    /** Getter for property distSearchPostalCode.
     * @return Value of property distSearchPostalCode.
     *
     */
    public String getDistSearchPostalCode() {
        return this.distSearchPostalCode;
    }
    
    /** Setter for property distSearchPostalCode.
     * @param distSearchPostalCode New value of property distSearchPostalCode.
     *
     */
    public void setDistSearchPostalCode(String distSearchPostalCode) {
        this.distSearchPostalCode = distSearchPostalCode;
    }
    
    /** Getter for property distSearchGroupId.
     * @return Value of property distSearchGroupId.
     *
     */
    public String getDistSearchGroupId() {
        return this.distSearchGroupId;
    }
    
    /** Setter for property distSearchGroupId.
     * @param distSearchGroupId New value of property distSearchGroupId.
     *
     */
    public void setDistSearchGroupId(String distSearchGroupId) {
        this.distSearchGroupId = distSearchGroupId;
    }
    
    /** Getter for property distSearchCounty.
     * @return Value of property distSearchCounty.
     *
     */
    public String getDistSearchCounty() {
        return this.distSearchCounty;
    }
    
    /** Setter for property distSearchCounty.
     * @param distSearchCounty New value of property distSearchCounty.
     *
     */
    public void setDistSearchCounty(String distSearchCounty) {
        this.distSearchCounty = distSearchCounty;
    }
    
    /** Getter for property acctSearchField.
     * @return Value of property acctSearchField.
     *
     */
    public String getAcctSearchField() {
        return this.acctSearchField;
    }
    
    /** Setter for property acctSearchField.
     * @param acctSearchField New value of property acctSearchField.
     *
     */
    public void setAcctSearchField(String acctSearchField) {
        this.acctSearchField = acctSearchField;
    }
    
    /** Getter for property acctSearchType.
     * @return Value of property acctSearchType.
     *
     */
    public String getAcctSearchType() {
        return this.acctSearchType;
    }
    
    /** Setter for property acctSearchType.
     * @param acctSearchType New value of property acctSearchType.
     *
     */
    public void setAcctSearchType(String acctSearchType) {
        this.acctSearchType = acctSearchType;
    }
    
    /** Getter for property acctSearchGroupId.
     * @return Value of property acctSearchGroupId.
     *
     */
    public String getAcctSearchGroupId() {
        return this.acctSearchGroupId;
    }
    
    /** Setter for property acctSearchGroupId.
     * @param acctSearchGroupId New value of property acctSearchGroupId.
     *
     */
    public void setAcctSearchGroupId(String acctSearchGroupId) {
        this.acctSearchGroupId = acctSearchGroupId;
    }
    
    /** Getter for property itemSearchCategory.
     * @return Value of property itemSearchCategory.
     *
     */
    public String getItemSearchCategory() {
        return this.itemSearchCategory;
    }
    
    /** Setter for property itemSearchCategory.
     * @param itemSearchCategory New value of property itemSearchCategory.
     *
     */
    public void setItemSearchCategory(String itemSearchCategory) {
        this.itemSearchCategory = itemSearchCategory;
    }
    
    /** Getter for property itemSearchShortDesc.
     * @return Value of property itemSearchShortDesc.
     *
     */
    public String getItemSearchShortDesc() {
        return this.itemSearchShortDesc;
    }
    
    /** Setter for property itemSearchShortDesc.
     * @param itemSearchShortDesc New value of property itemSearchShortDesc.
     *
     */
    public void setItemSearchShortDesc(String itemSearchShortDesc) {
        this.itemSearchShortDesc = itemSearchShortDesc;
    }
    
    /** Getter for property itemSearchItemPropertyName.
     * @return Value of property itemSearchItemPropertyName.
     *
     */
    public String getItemSearchItemPropertyName() {
        return this.itemSearchItemPropertyName;
    }
    
    /** Setter for property itemSearchItemPropertyName.
     * @param itemSearchItemPropertyName New value of property itemSearchItemPropertyName.
     *
     */
    public void setItemSearchItemPropertyName(String itemSearchItemPropertyName) {
        this.itemSearchItemPropertyName = itemSearchItemPropertyName;
    }
    
    /** Getter for property itemSearchItemProperty.
     * @return Value of property itemSearchItemProperty.
     *
     */
    public String getItemSearchItemProperty() {
        return this.itemSearchItemProperty;
    }
    
    /** Setter for property itemSearchItemProperty.
     * @param itemSearchItemProperty New value of property itemSearchItemProperty.
     *
     */
    public void setItemSearchItemProperty(String itemSearchItemProperty) {
        this.itemSearchItemProperty = itemSearchItemProperty;
    }
    
    /** Getter for property itemSearchLongDesc.
     * @return Value of property itemSearchLongDesc.
     *
     */
    public String getItemSearchLongDesc() {
        return this.itemSearchLongDesc;
    }
    
    /** Setter for property itemSearchLongDesc.
     * @param itemSearchLongDesc New value of property itemSearchLongDesc.
     *
     */
    public void setItemSearchLongDesc(String itemSearchLongDesc) {
        this.itemSearchLongDesc = itemSearchLongDesc;
    }
    
    /** Getter for property itemSearchManuId.
     * @return Value of property itemSearchManuId.
     *
     */
    public String getItemSearchManuId() {
        return this.itemSearchManuId;
    }
    
    /** Setter for property itemSearchManuId.
     * @param itemSearchManuId New value of property itemSearchManuId.
     *
     */
    public void setItemSearchManuId(String itemSearchManuId) {
        this.itemSearchManuId = itemSearchManuId;
    }
    
    /** Getter for property itemSearchSku.
     * @return Value of property itemSearchSku.
     *
     */
    public String getItemSearchSku() {
        return this.itemSearchSku;
    }
    
    /** Setter for property itemSearchSku.
     * @param itemSearchSku New value of property itemSearchSku.
     *
     */
    public void setItemSearchSku(String itemSearchSku) {
        this.itemSearchSku = itemSearchSku;
    }
    
    /** Getter for property itemSearchSkuType.
     * @return Value of property itemSearchSkuType.
     *
     */
    public String getItemSearchSkuType() {
        return this.itemSearchSkuType;
    }
    
    /** Setter for property itemSearchSkuType.
     * @param itemSearchSkuType New value of property itemSearchSkuType.
     *
     */
    public void setItemSearchSkuType(String itemSearchSkuType) {
        this.itemSearchSkuType = itemSearchSkuType;
    }
    
    /** Getter for property aggregateItems.
     * @return Value of property aggregateItems.
     *
     */
    public SelectableObjects getAggregateItems() {
        return this.aggregateItems;
    }
    
    /** Setter for property aggregateItems.
     * @param aggregateItems New value of property aggregateItems.
     *
     */
    public void setAggregateItems(SelectableObjects aggregateItems) {
        this.aggregateItems = aggregateItems;
    }
    
    /** Getter for property currManagingItem.
     * @return Value of property currManagingItem.
     *
     */
    public ProductData getCurrManagingItem() {
        return this.currManagingItem;
    }
    
    /** Setter for property currManagingItem.
     * @param currManagingItem New value of property currManagingItem.
     *
     */
    public void setCurrManagingItem(ProductData currManagingItem) {
        this.currManagingItem = currManagingItem;
    }
    
    /** Getter for property category.
     * @return Value of property category.
     *
     */
    public String getCategory() {
        return this.category;
    }
    
    /** Setter for property category.
     * @param category New value of property category.
     *
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /** Getter for property categoryAction.
     * @return Value of property categoryAction.
     *
     */
    public String getCategoryAction() {
        return this.categoryAction;
    }
    
    /** Setter for property categoryAction.
     * @param categoryAction New value of property categoryAction.
     *
     */
    public void setCategoryAction(String categoryAction) {
        this.categoryAction = categoryAction;
    }
    
    /** Getter for property categoryCostCenter.
     * @return Value of property categoryCostCenter.
     *
     */
    public String getCategoryCostCenter() {
        return this.categoryCostCenter;
    }
    
    /** Setter for property categoryCostCenter.
     * @param categoryCostCenter New value of property categoryCostCenter.
     *
     */
    public void setCategoryCostCenter(String categoryCostCenter) {
        this.categoryCostCenter = categoryCostCenter;
    }
    
    /** Getter for property tickItemsToContract.
     * @return Value of property tickItemsToContract.
     *
     */
    public boolean isTickItemsToContract() {
        return this.tickItemsToContract;
    }
    
    /** Setter for property tickItemsToContract.
     * @param tickItemsToContract New value of property tickItemsToContract.
     *
     */
    public void setTickItemsToContract(boolean tickItemsToContract) {
        this.tickItemsToContract = tickItemsToContract;
    }
    
    /** Getter for property tickItemsToCatalog.
     * @return Value of property tickItemsToCatalog.
     *
     */
    public boolean isTickItemsToCatalog() {
        return this.tickItemsToCatalog;
    }
    
    /** Setter for property tickItemsToCatalog.
     * @param tickItemsToCatalog New value of property tickItemsToCatalog.
     *
     */
    public void setTickItemsToCatalog(boolean tickItemsToCatalog) {
        this.tickItemsToCatalog = tickItemsToCatalog;
    }
    
    /** Getter for property tickItemsToOrderGuide.
     * @return Value of property tickItemsToOrderGuide.
     *
     */
    public boolean isTickItemsToOrderGuide() {
        return this.tickItemsToOrderGuide;
    }
    
    /** Setter for property tickItemsToOrderGuide.
     * @param tickItemsToOrderGuide New value of property tickItemsToOrderGuide.
     *
     */
    public void setTickItemsToOrderGuide(boolean tickItemsToOrderGuide) {
        this.tickItemsToOrderGuide = tickItemsToOrderGuide;
    }
    
    /** Getter for property catalogSearchField.
     * @return Value of property catalogSearchField.
     *
     */
    public String getCatalogSearchField() {
        return this.catalogSearchField;
    }
    
    /** Setter for property catalogSearchField.
     * @param catalogSearchField New value of property catalogSearchField.
     *
     */
    public void setCatalogSearchField(String catalogSearchField) {
        this.catalogSearchField = catalogSearchField;
    }
    
    /** Getter for property catalogSearchType.
     * @return Value of property catalogSearchType.
     *
     */
    public String getCatalogSearchType() {
        return this.catalogSearchType;
    }
    
    /** Setter for property catalogSearchType.
     * @param catalogSearchType New value of property catalogSearchType.
     *
     */
    public void setCatalogSearchType(String catalogSearchType) {
        this.catalogSearchType = catalogSearchType;
    }
    
    /** Getter for property catalogs.
     * @return Value of property catalogs.
     *
     */
    public SelectableObjects getCatalogs() {
        return this.catalogs;
    }
    
    /** Setter for property catalogs.
     * @param catalogs New value of property catalogs.
     *
     */
    public void setCatalogs(SelectableObjects catalogs) {
        this.catalogs = catalogs;
    }
    
    /** Getter for property catalogsNewlySelected.
     * @return Value of property catalogsNewlySelected.
     *
     */
    public List getCatalogsNewlySelected() {
        return this.catalogsNewlySelected;
    }
    
    /** Setter for property catalogsNewlySelected.
     * @param catalogsNewlySelected New value of property catalogsNewlySelected.
     *
     */
    public void setCatalogsNewlySelected(List catalogsNewlySelected) {
        this.catalogsNewlySelected = catalogsNewlySelected;
    }
    
    /** Getter for property usingCatalogSearch.
     * @return Value of property usingCatalogSearch.
     *
     */
    public boolean isUsingCatalogSearch() {
        return this.usingCatalogSearch;
    }
    
    /** Setter for property usingCatalogSearch.
     * @param usingCatalogSearch New value of property usingCatalogSearch.
     *
     */
    public void setUsingCatalogSearch(boolean usingCatalogSearch) {
        this.usingCatalogSearch = usingCatalogSearch;
    }

    /**
     * Getter for property stores.
     * @return Value of property stores.
     */
    public SelectableObjects getStores() {

        return this.stores;
    }

    /**
     * Setter for property stores.
     * @param stores New value of property stores.
     */
    public void setStores(SelectableObjects stores) {

        this.stores = stores;
    }

    /**
     * Getter for property storesNewlySelected.
     * @return Value of property storesNewlySelected.
     */
    public List getStoresNewlySelected() {

        return this.storesNewlySelected;
    }

    /**
     * Setter for property storesNewlySelected.
     * @param storesNewlySelected New value of property storesNewlySelected.
     */
    public void setStoresNewlySelected(List storesNewlySelected) {

        this.storesNewlySelected = storesNewlySelected;
    }

    /**
     * Getter for property storeSearchGroupId.
     * @return Value of property storeSearchGroupId.
     */
    public String getStoreSearchGroupId() {

        return this.storeSearchGroupId;
    }

    /**
     * Setter for property storeSearchGroupId.
     * @param storeSearchGroupId New value of property storeSearchGroupId.
     */
    public void setStoreSearchGroupId(String storeSearchGroupId) {

        this.storeSearchGroupId = storeSearchGroupId;
    }

    /**
     * Getter for property storeSearchType.
     * @return Value of property storeSearchType.
     */
    public String getStoreSearchType() {

        return this.storeSearchType;
    }

    /**
     * Setter for property storeSearchType.
     * @param storeSearchType New value of property storeSearchType.
     */
    public void setStoreSearchType(String storeSearchType) {

        this.storeSearchType = storeSearchType;
    }

    /**
     * Getter for property storeSearchField.
     * @return Value of property storeSearchField.
     */
    public String getStoreSearchField() {

        return this.storeSearchField;
    }

    /**
     * Setter for property storeSearchField.
     * @param storeSearchField New value of property storeSearchField.
     */
    public void setStoreSearchField(String storeSearchField) {

        this.storeSearchField = storeSearchField;
    }
    
}

/*
 * StoreItemMgrForm.java
 *
 * Created on March 16, 2005, 3:04 PM
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.view.utils.SelectableObjects;
import org.apache.struts.upload.FormFile;

import java.util.*;


/**
 *
 * @author Ykupershmidt
 */
public class StoreItemMgrForm  extends  StorePortalBaseForm {


  // -------------------------------------------------------- Instance Variables
    private boolean _locateItemFl = false;
    private int _level = 0;


    private String _action=null;
    private String _erpSystem = "";
    private boolean _autoSkuFlag = true;
    private int _catalogId=0;
    private int _storeCatalogId=0;
    private int _itemStoreId=0;
    private CatalogCategoryDataVector _storeCategories = new CatalogCategoryDataVector();
    private ManufacturerDataVector _storeManufacturers = new ManufacturerDataVector();
    private ItemDataVector _storeMajorCategories = new ItemDataVector();
    private MultiproductViewVector _multiproducts = new MultiproductViewVector();

    private String _searchNumType = "nameBegins";
    private String _searchType="";
    private boolean _searchGreenCertifiedFl=false;
    private String _skuTempl="";
    private String _skuType=SearchCriteria.STORE_SKU_NUMBER;
    private String _shortDescTempl="";
    private String _longDescTempl="";
    private String _manuId="";
    private String _manuNameTempl="";
    private String _distId="";
    private String _distNameTempl="";
    private String _categoryTempl="";
    private String _sizeTempl="";
    private String _itemPropertyName="";
    private String _itemPropertyTempl="";
    private String _whereToSearch="this";
    private String _resultSource="";
    private String _outServiceName = "";
    private IdVector _listIds=new IdVector();
    private ItemViewVector _items = new ItemViewVector();
    private ItemViewVector _stagedItems;/*staged Master Items search result*/
    private ItemViewVector _itemMatchSearchResult;/*Items to match with. search result*/
    private DistItemViewVector _distItems = new DistItemViewVector();
    private DistItemViewVector _distItemsFullList = new DistItemViewVector();
    private ProductData _product = null;
    private String[] _selectorBox=new String[0];
    private String _nameSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
    private String _skuSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
    private String _stagedSearchType = RefCodeNames.STAGED_ASSETS_SEARCH_TYPE_CD.NOT_MATCHED;

    private String _mfg1Id = "";
    private String _mfg1Name = "";
    private String _mfg1ItemSku = "";

    private boolean _distInfoFlag = false;

    private boolean _mShowDistCostFl = false;

    //LinkedList _categoryTree;
    int _selectedCategoryId = 0;
    int _selectedManufId = 0;

    private String _skuNum="";
    private String _distributorId="";
    private int _manufacturerId=0;
    private String _manufacturerName="";
    private String _manufacturerSku="";
    private String _effDate="";
    private String _expDate="";
    private String _listPrice="";
    private String _costPrice="";
    private String _newDistributorId="";
    private String _newDistributorSku="";
    private String _newDistributorUom="";
    private String _newDistributorPack="";
    private BusEntityData _newDistributor=null;
    private String[] _distributorBox;
    private String _retAction=null;
    private String _retMapping=null;
    private int _distrListPageSize=6;
    private int _distrListOffset=0;
    private String _uom = new String ("");
    private String _tempDistributorUom = new String("");

    private FormFile _imageFile;
    private FormFile _thumbnailFile;
    private FormFile _msdsFile;
    private FormFile _dedFile;
    private FormFile _specFile;

    private String imageUrl;
    private String thumbnailUrl;
    private String msdsUrl;
    private String dedUrl;
    private String specUrl;

    private boolean useUrls;

    //Major category
    private String _majorCategoryName = "";
    private int _majorCategoryId = 0;

    //Store category to edit
    private String _editCategoryName = "";
    private int _editCategoryId = 0;
    private boolean _canEditFl = true;
    private String _adminCategoryName = "";

    //Muti product to edit
    private String _editMultiproductName = "";
    private int _editMultiproductId = 0;

    //Import items from another store
    private boolean _allowImporFl = false;
    private BusEntityDataVector _userStoreEntities = null;
    private int _importStoreId = 0;
    private ItemViewVector _itemsToImport = null;

    // the managed item properties
    private boolean managedItemFlag = false;

    // the linked product items for ENTERPRISE store
    private StoreProductViewVector _linkedProductItems = new StoreProductViewVector();
    private Map orgProductPropertyMap = null;
    private boolean productUpdated = false;

    // the property for managed item of non-ENTERPRISE store
    private boolean warnedForManagedItem = false;
    private StoreProductViewVector _linkedProductItemsBetweenStores = new StoreProductViewVector();
    private int _unlinkedItemIdBetweenStores = 0;
    private boolean _managedItemBetweenStoresFlag = false;
    private boolean _warnedForManagedItemBetweenStores = false;
    private boolean _openItemLinkManagedBetweenStoresFl = false;

    // for item import, if we want to create the managed item link, we have to know the
    // following infomation
    private boolean fromImport = false;
    private int parentProductId = 0;

    // for Find Items to Link for managed item, we need the following
    private ItemViewVector itemsToLink = null;

    // for Price Editing
    // accounts
    private BusEntityDataVector accounts = new BusEntityDataVector();
    // contracts
    private ArrayList contracts = new ArrayList();
    // prices
    private ArrayList prices = new ArrayList();
    // multiple account for catalog flags
    private boolean[] multipleAccount;
    private boolean showPricing;
    //
    private boolean _showInactiveFl=false;
    //open   ItemLinkManaged dialog
    private boolean _openItemLinkManagedFl=false;
    //control changes only if STORE_TYPE_CD equals ENTERPRISE
    private Boolean _changesOnItemEditPageFl=Boolean.FALSE;
    private int _unlinkedItemId=0;
    private SelectableObjects mSelectCertCompanies;

    private int _selectedId = 0;

    private ItemMetaDataVector dataFieldProperties;
    private BusEntityDataVector _linkedStores;
    private boolean _showLinkedStores=false;
    private ItemView _stagedItemMatch;
    private boolean _matchSearch=false;
    private boolean _isEditable=true;
    
    //Johnson Diversey MSDS URL, received via Web Service 
    private String wsMsdsUrl = "";   
    
    //E3 Storage: Storage Type Code (Database or E3)
    private String imageStorageTypeCd;
    private String thumbnailStorageTypeCd;
    private String msdsStorageTypeCd;
    private String dedStorageTypeCd;
    private String specStorageTypeCd;
    private boolean allowMixedCategoryAndItemUnderSameParent;
// ---------------------------------------------------------------- Properties


    //-----------
    public void setAction(String pValue) {_action=pValue;}
    public String getAction(){return _action;}

    public void setErpSystem(String pValue) {_erpSystem=pValue;}
    public String getErpSystem(){return _erpSystem;}

    public void setAutoSkuFlag(boolean pValue) {_autoSkuFlag=pValue;}
    public boolean getAutoSkuFlag(){return _autoSkuFlag;}

    public void setStoreCategories(CatalogCategoryDataVector pValue) {_storeCategories=pValue;}
    public CatalogCategoryDataVector getStoreCategories() {return _storeCategories;}

    public void setStoreManufacturers(ManufacturerDataVector pValue) {_storeManufacturers=pValue;}
    public ManufacturerDataVector getStoreManufacturers() {return _storeManufacturers;}

    public void setStoreMajorCategories(ItemDataVector pValue) {_storeMajorCategories=pValue;}
    public ItemDataVector getStoreMajorCategories() {return _storeMajorCategories;}


    public void setSelectedCategoryId(int pValue) {_selectedCategoryId=pValue;}
    public int getSelectedCategoryId() {return _selectedCategoryId;}

    public void setSelectedManufId(int pValue) {_selectedManufId=pValue;}
    public int getSelectedManufId() {return _selectedManufId;}

    //public void setCategoryTree(LinkedList pValue) {_categoryTree=pValue;}
    //public LinkedList getCategoryTree() {return _categoryTree;}

    public void setCatalogId(int pValue) {_catalogId=pValue;}
    public int getCatalogId() {return _catalogId;}

    public void setStoreCatalogId(int pValue) {_storeCatalogId=pValue;}
    public int getStoreCatalogId() {return _storeCatalogId;}

    public void setSearchType(String pValue) {_searchType=pValue;}
    public String getSearchType(){return _searchType;}

    public void setSearchNumType(String pValue) {_searchNumType=pValue;}
    public String getSearchNumType(){return _searchNumType;}

    public void setSkuTempl(String pValue) {_skuTempl=pValue;}
    public String getSkuTempl(){return _skuTempl;}

    public void setSkuType(String pValue) {_skuType=pValue;}
    public String getSkuType(){return _skuType;}

    public void setShortDescTempl(String pValue) {_shortDescTempl=pValue;}
    public String getShortDescTempl(){return _shortDescTempl;}

    public void setLongDescTempl(String pValue) {_longDescTempl=pValue;}
    public String getLongDescTempl(){return _longDescTempl;}

    public void setCategoryTempl(String pValue) {_categoryTempl=pValue;}
    public String getCategoryTempl(){return _categoryTempl;}

    public void setSizeTempl(String pValue) {_sizeTempl=pValue;}
    public String getSizeTempl(){return _sizeTempl;}

    public void setItemPropertyName(String pValue) {_itemPropertyName=pValue;}
    public String getItemPropertyName(){return _itemPropertyName;}

    public void setItemPropertyTempl(String pValue) {_itemPropertyTempl=pValue;}
    public String getItemPropertyTempl(){return _itemPropertyTempl;}

    public void setManuId(String pValue) {
      _manuId = pValue;
      if(pValue==null || pValue.trim().length()==0) {
        _manuNameTempl = "";
      }
    }
    public String getManuId(){return _manuId;}

    public void setManuNameTempl(String pValue) {_manuNameTempl = pValue;}
    public String getManuNameTempl(){return _manuNameTempl;}

    public void setDistId(String pValue) {
      _distId=pValue;
      if(pValue==null || pValue.trim().length()==0) {
        _distNameTempl="";
      }
    }
    public String getDistId(){return _distId;}

    public void setDistNameTempl(String pValue) {_distNameTempl = pValue;}
    public String getDistNameTempl(){return _distNameTempl;}

    public void setSelectorBox(String[] pValue) {_selectorBox=pValue;}
    public String[] getSelectorBox(){return _selectorBox;}


    public int getListCount() {
      return (_items.size());
    }

    public void setListIds(IdVector pValue) {_listIds=pValue;}
    public IdVector getListIds(){return _listIds;}

    public int getListSize(){return _items.size();}

    public void setItems(ItemViewVector pValue) {_items=pValue;}
    public ItemViewVector getItems(){return _items;}

    public void setDistItems(DistItemViewVector pValue) {_distItems=pValue;}
    public DistItemViewVector getDistItems(){return _distItems;}

    public void setDistItemsFullList(DistItemViewVector pValue) {_distItemsFullList=pValue;}
    public DistItemViewVector getDistItemsFullList(){return _distItemsFullList;}

    public void setWhereToSearch(String pValue) {_whereToSearch=pValue;}
    public String getWhereToSearch(){return _whereToSearch;}

    public void setResultSource(String pValue) {_resultSource=pValue;}
    public String getResultSource(){return _resultSource;}

    public void setOutServiceName(String pValue) {_outServiceName=pValue;}
    public String getOutServiceName(){return _outServiceName;}

    public void setDistInfoFlag(boolean pValue) {_distInfoFlag=pValue;}
    public boolean getDistInfoFlag(){return _distInfoFlag;}

    public void setMfg1Id(String pValue) {_mfg1Id=pValue;}
    public String getMfg1Id(){return _mfg1Id;}
/*
    public void setMfg1IdEl(int index, String pValue)
    {
      if(_mfg1Ids==null || index<0 || index>=_mfg1Ids.length) return;
      _mfg1Ids[index] = pValue;
    }
    public String getMfg1IdEl(int index){
      if(_mfg1Ids==null || index<0 || index>=_mfg1Ids.length) return "";
      return _mfg1Ids[index];
    }
*/
    public void setMfg1Name(String pValue) {_mfg1Name=pValue;}
    public String getMfg1Name(){return _mfg1Name;}
/*
    public void setMfg1NameEl(int index, String pValue)
    {
      if(_mfg1Names==null || index<0 || index>=_mfg1Names.length) return;
      _mfg1Names[index] = pValue;
    }
    public String getMfg1NameEl(int index){
      if(_mfg1Names==null || index<0 || index>=_mfg1Names.length) return "";
      return _mfg1Names[index];
    }
*/
    public void setMfg1ItemSku(String pValue) {_mfg1ItemSku=pValue;}
    public String getMfg1ItemSku(){return _mfg1ItemSku;}

    public void setProduct(ProductData pValue) {_product=pValue;}
    public ProductData getProduct(){return _product;}


    /*
    public void setMfg1SkuEl(int index, String pValue)
    {
      if(_mfg1Skus==null || index<0 || index>=_mfg1Skus.length) return;
      _mfg1Names[index] = pValue;
    }
    public String getMfg1SkuEl(int index){
      if(_mfg1Skus==null || index<0 || index>=_mfg1Skus.length) return "";
      return _mfg1Names[index];
    }
*/
    public int getCategoryListSize() {
      CatalogCategoryDataVector catalogCategoryDV=_product.getCatalogCategories();
      int size=(catalogCategoryDV==null)?0:catalogCategoryDV.size();
      return size;
    }

    public void setSkuNum(String pValue) {_skuNum=pValue;}
    public String getSkuNum() {return _skuNum;}

    public void setEffDate(String pValue) {_effDate=pValue;}
    public String getEffDate(){return _effDate;}

    public void setExpDate(String pValue) {_expDate=pValue;}
    public String getExpDate(){return _expDate;}

    public void setListPrice(String pValue) {_listPrice=pValue;}
    public String getListPrice(){return _listPrice;}

    public void setCostPrice(String pValue) {_costPrice=pValue;}
    public String getCostPrice(){return _costPrice;}


    public void setManufacturerId(int pValue) {_manufacturerId=pValue;}
    public int getManufacturerId(){return _manufacturerId;}

    public void setManufacturerName(String pValue) {_manufacturerName=pValue;}
    public String getManufacturerName(){return _manufacturerName;}

    public void setManufacturerSku(String pValue) {_manufacturerSku=pValue;}
    public String getManufacturerSku(){return _manufacturerSku;}

    public void setNewDistributorId(String pValue) {_newDistributorId=pValue;}
    public String getNewDistributorId(){return _newDistributorId;}

    public void setNewDistributorSku(String pValue) {_newDistributorSku=pValue;}
    public String getNewDistributorSku(){return _newDistributorSku;}

    public void setNewDistributorUom(String pValue) {_newDistributorUom=pValue;}
    public String getNewDistributorUom(){return _newDistributorUom;}

    public void setNewDistributorPack(String pValue) {_newDistributorPack=pValue;}
    public String getNewDistributorPack(){return _newDistributorPack;}

    public void setNewDistributor(BusEntityData pValue) {_newDistributor=pValue;}
    public BusEntityData getNewDistributor(){return _newDistributor;}
    public String getNewDistributorName() {
     if(_newDistributor==null) return "";
     return _newDistributor.getShortDesc();
    }

    public void setDistributorBox(String[] pValue) {_distributorBox=pValue;}
    public String[] getDistributorBox(){return _distributorBox;}

    public void setRetAction(String pValue) {_retAction=pValue;}
    public String getRetAction(){return _retAction;}

    public void setRetMapping(String pValue) {_retMapping=pValue;}
    public String getRetMapping(){return _retMapping;}

    public void setDistrListOffset(int pValue) {_distrListOffset=pValue;}
    public int getDistrListOffset(){return _distrListOffset;}

    public void setDistrListPageSize(int pValue) {_distrListPageSize=pValue;}
    public int getDistrListPageSize(){return _distrListPageSize;}

    public LinkedList getDistrListPages() {
  LinkedList pages = new LinkedList();
  BusEntityDataVector distrV = _product.getMappedDistributors();
  if(distrV!=null) {
      for(int jj=0,ii=0; ii<distrV.size(); ii+=_distrListPageSize, jj++) {
    pages.add(new Integer(jj));
      }
  }
  return pages;
    }

    public void setImageFile(FormFile pFile) {
     _imageFile=pFile;
    }
    public FormFile getImageFile(){return _imageFile;}

    public void setThumbnailFile(FormFile pFile) {_thumbnailFile = pFile;}
    public FormFile getThumbnailFile() { return _thumbnailFile;}

    public void setMsdsFile(FormFile pFile) {_msdsFile=pFile;}
    public FormFile getMsdsFile(){return _msdsFile;}

    public void setDedFile(FormFile pFile) {_dedFile=pFile;}
    public FormFile getDedFile(){return _dedFile;}

    public void setSpecFile(FormFile pFile) {_specFile=pFile;}
    public FormFile getSpecFile(){return _specFile;}

    public Date getCurrentDate() {
     GregorianCalendar cal = new GregorianCalendar();
     cal.setTime(new Date(System.currentTimeMillis()));
     cal = new GregorianCalendar
      (cal.get(GregorianCalendar.YEAR),
       cal.get(GregorianCalendar.MONTH),
       cal.get(GregorianCalendar.DAY_OF_MONTH));
       return cal.getTime();
    }

    public int getCurrentDistrListPage() {
      int currentPage = _distrListOffset/_distrListPageSize;
      return currentPage;
    }

    public int getDistrListCount() {
       int size = _product.getMappedDistributors().size();
       return size;
    }

    public void setHazmat(String Hazmat) {
       _product.setHazmat(Hazmat);
    }

    public String getHazmat() {
     return _product.getHazmat();
    }

    public void setUom(String pValue) {
        _uom = pValue;

    }

    public String getUom(){
        return _uom;
    }


    public void setTempDistributorUom(String pValue) {_tempDistributorUom=pValue;}
    public String getTempDistributorUom(){return new String("");}

    //Major Category
    public void setMajorCategoryName(String pValue) {_majorCategoryName=pValue;}
    public String getMajorCategoryName(){return _majorCategoryName;}

    public void setMajorCategoryId(int pValue) {_majorCategoryId=pValue;}
    public int getMajorCategoryId(){return _majorCategoryId;}

    public void setEditCategoryName(String pValue) {_editCategoryName=pValue;}
    public String getEditCategoryName(){return _editCategoryName;}

    public void setAdminCategoryName(String pValue) {_adminCategoryName=pValue;}
    public String getAdminCategoryName(){return _adminCategoryName;}

    public void setEditCategoryId(int pValue) {_editCategoryId=pValue;}
    public int getEditCategoryId(){return _editCategoryId;}

    public void setCanEditFl(boolean pValue) {_canEditFl=pValue;}
    public boolean getCanEditFl(){return _canEditFl;}
	
     //Import items from another store
    public void setAllowImporFl(boolean pValue) {_allowImporFl=pValue;}
    public boolean getAllowImporFl(){return _allowImporFl;}

    public void setUserStoreEntities(BusEntityDataVector pValue) {_userStoreEntities=pValue;}
    public BusEntityDataVector getUserStoreEntities(){return _userStoreEntities;}

    public void setImportStoreId(int pValue) {_importStoreId=pValue;}
    public int getImportStoreId(){return _importStoreId;}

    // for managed item
    public void setManagedItemFlag(boolean value) { managedItemFlag = value; }
    public boolean isManagedItemFlag() {return managedItemFlag; }

    public void setItemsToImport(ItemViewVector pValue) {_itemsToImport=pValue;}
    public ItemViewVector getItemsToImport(){return _itemsToImport;}

    public void setLinkedProductItems(StoreProductViewVector pValue) {_linkedProductItems=pValue;}
    public StoreProductViewVector getLinkedProductItems(){return _linkedProductItems;}

    public void setLinkedItem(int index, StoreProductView pValue) {
        _linkedProductItems.set(index, pValue);
    }
    public StoreProductView getLinkedItem(int index) {
        for(int i = _linkedProductItems.size(); i < index; i++) {
            _linkedProductItems.add(new StoreProductView());
        }
        return (StoreProductView)_linkedProductItems.get(index);
    }

    public void setLinkedProductItemsBetweenStores(StoreProductViewVector pValue) {_linkedProductItemsBetweenStores=pValue;}
    public StoreProductViewVector getLinkedProductItemsBetweenStores(){return _linkedProductItemsBetweenStores;}

    public void setLinkedItemBetweenStores(int index, StoreProductView pValue) {
    	_linkedProductItemsBetweenStores.set(index, pValue);
    }

    public StoreProductView getLinkedItemBetweenStores(int index) {
        for(int i = _linkedProductItemsBetweenStores.size(); i < index; i++) {
        	_linkedProductItemsBetweenStores.add(new StoreProductView());
        }
        return (StoreProductView)_linkedProductItemsBetweenStores.get(index);
    }

    public int getUnlinkedItemIdBetweenStores() {
        return _unlinkedItemIdBetweenStores;
    }

    public void setUnlinkedItemIdBetweenStores(int unlinkedItemId) {
        _unlinkedItemIdBetweenStores = unlinkedItemId;
    }

    public boolean isManagedItemBetweenStoresFlag() {
    	return _managedItemBetweenStoresFlag;
    }

    public void setManagedItemBetweenStoresFlag(boolean value) {
    	_managedItemBetweenStoresFlag = value;
    }

    public boolean isWarnedForManagedItemBetweenStores() {
    	return _warnedForManagedItemBetweenStores;
    }

    public void setWarnedForManagedItemBetweenStores(boolean value) {
    	_warnedForManagedItemBetweenStores = value;
    }

    public boolean getOpenItemLinkManagedBetweenStoresFl() {
        return _openItemLinkManagedBetweenStoresFl;
    }

    public void setOpenItemLinkManagedBetweenStoresFl(boolean openItemLinkManagedFl) {
    	_openItemLinkManagedBetweenStoresFl = openItemLinkManagedFl;
    }

    public void setDistItem(int index, DistItemView pValue) {
        _distItems.set(index, pValue);
    }
    public DistItemView getDistItem(int index) {
        for(int i = _distItems.size(); i < index; i++) {
            _distItems.add(DistItemView.createValue());
        }
        return (DistItemView) _distItems.get(index);
    }

    public DistributorData getDistItemByEntityId(int busEntityId) {
      for (int i=0; i<_distItemsFullList.size(); i++){
        DistributorData d = (DistributorData)_distItemsFullList.get(i);
        if (d.getBusEntity().getBusEntityId() == busEntityId) {
          return d;
        }
      }
      return null;
    }

    public void setOrgProductPropertyMap(Map pValue) {orgProductPropertyMap=pValue;}
    public Map getOrgProductPropertyMap(){return orgProductPropertyMap;}

    public void setProductUpdated(boolean value) { productUpdated = value; }
    public boolean isProductUpdated() {return productUpdated; }

    public void setWarnedForManagedItem(boolean value) { warnedForManagedItem = value; }
    public boolean isWarnedForManagedItem() {return warnedForManagedItem; }

    // for item import, if we want to create the managed item link, we have to know the
    // following infomation
    public void setFromImport(boolean value) { fromImport = value; }
    public boolean isFromImport() {return fromImport; }

    public void setParentProductId(int value) { parentProductId = value; }
    public int getParentProductId() {return parentProductId; }

    // for Find Items To Link for managed item
    public void setItemsToLink(ItemViewVector pValue) {itemsToLink=pValue;}
    public ItemViewVector getItemsToLink(){return itemsToLink;}


    // for Price Editing
    public boolean getShowPricing() { return showPricing; }
    public void setShowPricing(boolean val) { showPricing = val; }
    // accounts
    public void setAccounts(BusEntityDataVector val) {
      accounts = val;
      if (val != null && val.size() > 0) {
        multipleAccount = new boolean[val.size()];
      }
    }
    public BusEntityDataVector getAccounts() { return accounts; }
    // contracts
    public void setContracts(ArrayList val) { contracts = val; }
    public ArrayList getContracts() { return contracts; }
    public ContractDataVector getAccountContractList(int accountId) {
      ContractDataVector result = new ContractDataVector();
      for (int i=0; i<accounts.size(); i++) {
        BusEntityData account = (BusEntityData)accounts.get(i);
        if (account.getBusEntityId() == accountId) {
          result = (ContractDataVector)contracts.get(i);
          break;
        }
      }
      return result;
    }

    //prices
    public void setPrices(ArrayList val) { prices = val; }
    public ArrayList getPrices() { return prices; }
    public ContractItemPriceViewVector getItemPriceList(int index) {
      if (prices == null || prices.size() < index) {
        return new ContractItemPriceViewVector();
      } else {
        return (ContractItemPriceViewVector)prices.get(index);
      }
    }
    public ContractItemPriceViewVector getAccountItemPriceList(int accountId) {
      for (int i=0; i<accounts.size(); i++) {
        BusEntityData account = (BusEntityData)accounts.get(i);
        if (account.getBusEntityId() == accountId) {
          return getItemPriceList(i);
        }
      }
      return new ContractItemPriceViewVector();
    }
    // multiple account
    public boolean[] getMultipleAccount() { return multipleAccount; }
    public void setMultipleAccount(boolean[] val) { multipleAccount = val; }

    public boolean getShowInactiveFl() {
        return _showInactiveFl;
    }
    public void setShowInactiveFl(boolean showInactiveFl) {
        this._showInactiveFl = showInactiveFl;
    }

    public boolean getOpenItemLinkManagedFl() {
        return _openItemLinkManagedFl;
    }

    public void setOpenItemLinkManagedFl(boolean openItemLinkManagedFl) {
        this._openItemLinkManagedFl = openItemLinkManagedFl;
    }

    public Boolean getChangesOnItemEditPageFl() {
        return _changesOnItemEditPageFl;
    }

    public void setChangesOnItemEditPageFl(Boolean changesOnItemEditPageFl) {
        this._changesOnItemEditPageFl = changesOnItemEditPageFl;
    }

    public void setUnlinkedItemId(int unlinkedItemId) {
        this._unlinkedItemId = unlinkedItemId;
    }

    public int getUnlinkedItemId() {
        return _unlinkedItemId;
    }

    /**
   * Reset all properties to their default values.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        // create a new detail object and convert nulls to empty strings
        _distInfoFlag = false;
        _showInactiveFl=false;
        int size = _linkedProductItems.size();
        for(int i=0;i<size;i++)
        {
            StoreProductView linkedProduct = (StoreProductView) _linkedProductItems.get(i);
            linkedProduct.setUpdateShortDesc(false);
            linkedProduct.setUpdateOtherDesc(false);
            linkedProduct.setUpdateLongDesc(false);
            linkedProduct.setUpdateImage(false);
            linkedProduct.setUpdateManufacturer(false);
            linkedProduct.setUpdateManufacturerSku(false);
            linkedProduct.setUpdateUpc(false);
            linkedProduct.setUpdatePkgUpc(false);
            linkedProduct.setUpdateMsds(false);
            linkedProduct.setUpdateUnspscCd(false);
            linkedProduct.setUpdateColor(false);
            linkedProduct.setUpdateSize(false);
            linkedProduct.setUpdateScent(false);
            linkedProduct.setUpdateDed(false);
            linkedProduct.setUpdateShipWeight(false);
            linkedProduct.setUpdateCubeSize(false);
            linkedProduct.setUpdateListPrice(false);
            linkedProduct.setUpdatePsn(false);
            linkedProduct.setUpdateSpec(false);
            linkedProduct.setUpdateNsn(false);
            linkedProduct.setUpdateUom(false);
            linkedProduct.setUpdatePackProblemSku(false);
            linkedProduct.setUpdatePack(false);
            linkedProduct.setUpdateCostPrice(false);
            linkedProduct.setUpdateHazmat(false);
            linkedProduct.setUpdateCertifiedCompanies(false);
        }
        if (_linkedProductItemsBetweenStores != null) {
        	int sizeAllLinks = _linkedProductItemsBetweenStores.size();
        	for (int i = 0; i < sizeAllLinks; i++) {
                StoreProductView linkedProduct = (StoreProductView) _linkedProductItemsBetweenStores.get(i);
                linkedProduct.setUpdateShortDesc(false);
                linkedProduct.setUpdateOtherDesc(false);
                linkedProduct.setUpdateLongDesc(false);
                linkedProduct.setUpdateImage(false);
                linkedProduct.setUpdateManufacturer(false);
                linkedProduct.setUpdateManufacturerSku(false);
                linkedProduct.setUpdateUpc(false);
                linkedProduct.setUpdatePkgUpc(false);
                linkedProduct.setUpdateMsds(false);
                linkedProduct.setUpdateUnspscCd(false);
                linkedProduct.setUpdateColor(false);
                linkedProduct.setUpdateSize(false);
                linkedProduct.setUpdateScent(false);
                linkedProduct.setUpdateDed(false);
                linkedProduct.setUpdateShipWeight(false);
                linkedProduct.setUpdateCubeSize(false);
                linkedProduct.setUpdateListPrice(false);
                linkedProduct.setUpdatePsn(false);
                linkedProduct.setUpdateSpec(false);
                linkedProduct.setUpdateNsn(false);
                linkedProduct.setUpdateUom(false);
                linkedProduct.setUpdatePackProblemSku(false);
                linkedProduct.setUpdatePack(false);
                linkedProduct.setUpdateCostPrice(false);
                linkedProduct.setUpdateHazmat(false);
                linkedProduct.setUpdateCertifiedCompanies(false);
            }
        }

        if (mSelectCertCompanies != null) {
            mSelectCertCompanies.handleStutsFormResetRequest();
        }
        setUseUrls(false);
        _matchSearch = false;
        _skuTempl = "";
        _shortDescTempl = "";
    }

  /**
   * So far nothing to validate
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request)
  {

    return null;

  }

    public void setSelectCertCompanies(SelectableObjects selectCertCompanies) {
        this.mSelectCertCompanies = selectCertCompanies;
    }

    public SelectableObjects getSelectCertCompanies() {
        return mSelectCertCompanies;
    }


    public boolean getSearchGreenCertifiedFl() {
        return _searchGreenCertifiedFl;
    }

    public void setSearchGreenCertifiedFl(boolean searchGreenCertifiedFl) {
        this._searchGreenCertifiedFl = searchGreenCertifiedFl;
    }

    public String getDedUrl() {
        return dedUrl;
    }
    public void setDedUrl(String dedUrl) {
        this.dedUrl = dedUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getMsdsUrl() {
        return msdsUrl;
    }
    public void setMsdsUrl(String msdsUrl) {
        this.msdsUrl = msdsUrl;
    }
    public String getSpecUrl() {
        return specUrl;
    }
    public void setSpecUrl(String specUrl) {
        this.specUrl = specUrl;
    }
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
    public boolean isUseUrls() {
        return useUrls;
    }
    public void setUseUrls(boolean useUrls) {
        this.useUrls = useUrls;
    }
	/**
	 * @return Returns the _selectedId.
	 */
	public int getSelectedId() {
		return _selectedId;
	}
	/**
	 * @param id The _selectedId to set.
	 */
	public void setSelectedId(int id) {
		_selectedId = id;
	}
    /**
     * @return Returns the _multiproducts.
     */
    public MultiproductViewVector getMultiproducts() {
        return _multiproducts;
    }
    /**
     * @param _multiproducts The _multiproducts to set.
     */
    public void setMultiproducts(MultiproductViewVector _multiproducts) {
        this._multiproducts = _multiproducts;
    }
    /**
     * @return Returns the _editMultiproductName.
     */
    public String getEditMultiproductName() {
        return _editMultiproductName;
    }
    /**
     * @param multiproductName The _editMultiproductName to set.
     */
    public void setEditMultiproductName(String multiproductName) {
        _editMultiproductName = multiproductName;
    }
    /**
     * @return Returns the _editMultiproductId.
     */
    public int getEditMultiproductId() {
        return _editMultiproductId;
    }
    /**
     * @param multiproductId The _editMultiproductId to set.
     */
    public void setEditMultiproductId(int multiproductId) {
        _editMultiproductId = multiproductId;
    }
    
    public boolean getShowDistCostFl() {return _mShowDistCostFl;} //SVC
    public void setShowDistCostFl(boolean pVal) {_mShowDistCostFl = pVal;} //SVC



    public ItemMetaDataVector getDataFieldProperties() {
        return this.dataFieldProperties;
    }

    public void setDataFieldProperties(ItemMetaDataVector dataFieldProperties) {
        this.dataFieldProperties = dataFieldProperties;
    }

    public void setDataFieldProperty(int indx, ItemMetaData dataField) {
        int len = dataFieldProperties.size();
        while(len <= indx){
            dataFieldProperties.add(null);
        }
        dataFieldProperties.add(indx,dataField);
    }

    public ItemMetaData getDataFieldProperty(int indx) {
        if(indx > dataFieldProperties.size()){
            return null;
        }else{
            return (ItemMetaData) dataFieldProperties.get(indx);
        }
    }

    public void setStagedItems(ItemViewVector stagedItems) {
        this._stagedItems = stagedItems;
    }

    public ItemViewVector getStagedItems() {
        return _stagedItems;
    }

    public BusEntityDataVector getLinkedStores() {
        return _linkedStores;
    }

    public void setLinkedStores(BusEntityDataVector linkedStores) {
        this._linkedStores = linkedStores;
    }

    public String getStagedSearchType() {
        return _stagedSearchType;
    }

    public void setStagedSearchType(String stagedSearchType) {
        this._stagedSearchType = stagedSearchType;
    }

    public String getNameSearchType() {
        return _nameSearchType;
    }

    public void setNameSearchType(String nameSearchType) {
        this._nameSearchType = nameSearchType;
    }

    public String getSkuSearchType() {
        return _skuSearchType;
    }

    public void setSkuSearchType(String skuSearchType) {
        this._skuSearchType = skuSearchType;
    }

    public ItemView getStagedItemMatch() {
        return _stagedItemMatch;
    }

    public void setStagedItemMatch(ItemView stagedItemMatch) {
        this._stagedItemMatch = stagedItemMatch;
    }

    public ItemViewVector getItemMatchSearchResult() {
        return _itemMatchSearchResult;
    }

    public void setItemMatchSearchResult(ItemViewVector itemMatchSearchResult) {
        this._itemMatchSearchResult = itemMatchSearchResult;
    }

    public boolean getMatchSearch() {
        return _matchSearch;
    }

    public void setMatchSearch(boolean matchSearch) {
        this._matchSearch = matchSearch;
    }

    public boolean getIsEditable() {
        return _isEditable;
    }

    public void setIsEditable(boolean isEditable) {
        this._isEditable = isEditable;
    }

    public boolean getShowLinkedStores() {
        return _showLinkedStores;
    }

    public void setShowLinkedStores(boolean showLinkedStores) {
        this._showLinkedStores = showLinkedStores;
    }
    
    public String getWsMsdsUrl() {
        return wsMsdsUrl;
    }
    
    public void setWsMsdsUrl(String pWsMsdsUrl){
    	this.wsMsdsUrl = pWsMsdsUrl;
    }
    
    // Methods for E3 Storage vs. Database (for images, docs)
	
    public String getImageStorageTypeCd() {
		return imageStorageTypeCd;
	}
	
	public void setImageStorageTypeCd(String imageStorageTypeCd) {
		this.imageStorageTypeCd = imageStorageTypeCd;
	}
	
	public String getThumbnailStorageTypeCd() {
		return thumbnailStorageTypeCd;
	}
	
	public void setThumbnailStorageTypeCd(String thumbnailStorageTypeCd) {
		this.thumbnailStorageTypeCd = thumbnailStorageTypeCd;
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
	
	public void setAllowMixedCategoryAndItemUnderSameParent(boolean pAllowMixedCategoryAndItemUnderSameParent) {
        this.allowMixedCategoryAndItemUnderSameParent = pAllowMixedCategoryAndItemUnderSameParent;
    }

    public boolean getAllowMixedCategoryAndItemUnderSameParent() {
        return allowMixedCategoryAndItemUnderSameParent;
    }

    public int getItemStoreId() {
        return _itemStoreId;
    }

    public void setItemStoreId(int itemStoreId) {
        this._itemStoreId = itemStoreId;
    }
}

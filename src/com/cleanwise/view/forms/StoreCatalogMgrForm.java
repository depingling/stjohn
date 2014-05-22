 
/**
 * Title:        StoreCatalogMgrForm
 * Description:  This is the Struts ActionForm class for 
 * store catalog/contract management page.
 * Purpose:      Strut support to search for users.      
 * Copyright:    Copyright (c) 2005
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.Constants;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpSession;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.session.Store;


/**
 * Form bean for the user manager page. 
 *
 * @author durval
 */
public final class StoreCatalogMgrForm 
      extends StorePortalBaseForm
      implements UpdaterAfterStoreChange {

  //Embadded forms
  private LocateStoreAccountForm _locateStoreAccountForm = null;
  int _level = 0;
  
  //Action
  private String _action=null;
  private boolean _catalogProcessFl = false;
  private boolean _locateAccountFl = false;
  private boolean _cleanwiseFl = false;
  private String _copyContractFl = null;
 
 //Search properties
  private AccountDataVector _filterAccounts = null;
  private String _searchField = "";
  private String _searchType = "catalogNameStarts";
  private String _catalogType = "";
  private boolean _containsFlag = false;
  private boolean _showInactiveFlag = false;
  private List _resultList = new LinkedList();
  private int[] _selectedCatalogIds = new int[0];
  private CatalogDataVector _selectedCatalogs = null;
  
  
  //Detail properties
  private CatalogData _catalogDetail = CatalogData.createValue();
  private int _storeId = 0;
  private String _storeName = "";
  private int _masterCatalogId;
  private boolean _mayDelete=false;
  private int _foundCatalogId=0;
  private String _foundCatalogName;
  private boolean _populateCatalogFl = false;
//  private boolean updatePriceFromLoader;
  private String updatePriceFromLoader;
  //???????????
  private ProductDataVector _products=new ProductDataVector();
  private ProductDataVector _missingData=new ProductDataVector();

  //validation information
  private int _numItems;
  private int _activeContracts;
  private int _numDistributors;
  
  //Contract data  & Order Guide
  private ContractData    _contractDetail = ContractData.createValue();
  private String          _freightTableId = new String("");
  private String          _freightTableName = new String("");
  private FreightTableData                    _currentFreightTable = null;
  private FreightTableCriteriaDescDataVector  _currentFreightTableCriteria = null;
  private String _discountTableId = new String("");
  private OrderGuideDescDataVector _orderGuides = null;
  
  //private String _orderGuideName = "";

  //Price enter
  private String[] _distCostS = new String[0];
  private String[] _distBaseCostS = new String[0];
  private String[] _amountS = new String[0];
  private String[] _inputIds = new String[0];


  // Items to be removed.
  private String[]        _selectItemsCollection = { " " };
  private ArrayList       _itemsDetailCollection;
  private ArrayList       _catalogItemsCollection;
  private ContractItemDescDataVector _nonCatalogItems;

  //Cost Centers   
  private String mBudgetPeriod;
  private String mFiscalCalenderEffDate;

  private String mBudgetTotal;
  private String mBudgetStartDate;
  private String mCity;
  private String mState;
  //private String mSearchField;
  //private String mSearchType;
  private ArrayList mSiteBudgetList;
  private CatalogFiscalPeriodViewVector mCatalogFiscalPeriods = null; 
  private CostCenterData mCostCenter = null;
  private FiscalCalenderView mFiscalCalenderView = null;
  private BusEntityDataVector mBudgetAccounts = new BusEntityDataVector();
  private BusEntityData mBudgetAccount = null;
  private int mBudgetAccountIdInp = 0;
  private String costCenterTypeCd;
  private BudgetView budget;
  private HashMap<Integer,String> budgetAmounts;

  //-- config data 
    private CatalogDataVector mConfCatalogFilter = null;
    private String mConfSearchField = "";
    private String mConfSearchType = "nameBegins";
    private String mConfType = "";
    private String mConfCity = "";
    private String mConfCounty = "";
    private String mConfState = "";
    private String mConfZipcode = "";
    private boolean mConfShowInactiveFl = false;
    private boolean mConfShowConfguredOnlyFl = false;
    private boolean mConfMoveSitesFl = false;
    //private int mOverwriteDistId = 0;

    //private String _distributorId;
    private String[] mAssocSiteIds = null;
    private String[] mSelectIds = null;
    private String[] mDisplayIds = null;
    private String mSelectedMainDistributorId="";
    private String[] mBudgetThreshold;
    private boolean displayBudgetThreshold;

    // ------------------------------------------------------------ Public Methods

  public String getCopyContractFl() { return _copyContractFl;}
  public void setCopyContractFl(String pCopyContractFl) {_copyContractFl = pCopyContractFl;}

  public LocateStoreAccountForm getLocateStoreAccountForm() { return this._locateStoreAccountForm;}
  public void setLocateStoreAccountForm(LocateStoreAccountForm pValue) {this._locateStoreAccountForm = pValue;}
  
  public void setEmbeddedForm(Object pVal) {
    if(pVal instanceof LocateStoreAccountForm) {
      _locateStoreAccountForm = (LocateStoreAccountForm) pVal;
    }
  } 
  
  public int getLevel() {return _level;}
  public void setLevel(int pVal) {_level = pVal;}
  

  
  public CatalogData getCatalogDetail() { return this._catalogDetail;}
  public void setCatalogDetail(CatalogData catalogDetail) {this._catalogDetail = catalogDetail;}

  public boolean getCatalogProcessFl() { return _catalogProcessFl;}
  public void setCatalogProcessFl(boolean pVal) {_catalogProcessFl = pVal;}
  
  public boolean getLocateAccountFl() { return _locateAccountFl;}
  public void setLocateAccountFl(boolean pLocateAccountFl) {_locateAccountFl = pLocateAccountFl;}

  public int getStoreId() { return _storeId;}
  public void setStoreId(int pStoreId) {_storeId = pStoreId;}

  public String getStoreName() {return _storeName;}
  public void setStoreName(String pStoreName) {_storeName=pStoreName; }

  public int getMasterCatalogId() {return _masterCatalogId;}
  public void setMasterCatalogId(int pMasterCatalogId) {_masterCatalogId = pMasterCatalogId; }

  public String getFoundCatalogName() {return _foundCatalogName;}
  public void setFoundCatalogName(String pVal) {_foundCatalogName = pVal;}

  public boolean getMayDelete() {return _mayDelete;}
  public void setMayDelete(boolean pMayDelete) {_mayDelete=pMayDelete;}

  public String getEffDate() {
    Date effDate =  this._contractDetail.getEffDate();
    String dateString = new String("");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    if(null != effDate) {
      dateString = simpleDateFormat.format(effDate);
    }
    return dateString;
  }


  public void setEffDate(String dateString) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date effDate =  new Date();
    try {
        effDate  = simpleDateFormat.parse(dateString);
    }
    catch (Exception e) {
    }
    this._contractDetail.setEffDate(effDate);
  }


  public String getExpDate() {
    Date expDate =  this._contractDetail.getExpDate();
    String dateString = new String("");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    if (null != expDate) {
        dateString = simpleDateFormat.format(expDate);
    }
    return dateString;
  }


  public void setExpDate(String dateString) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date expDate = new Date();
    try {
        expDate  = simpleDateFormat.parse(dateString);
    }
    catch (Exception e) {
    }
    this._contractDetail.setExpDate(expDate);
  }

  public void setFoundCatalogId(int pValue) {_foundCatalogId=pValue;}
  public int getFoundCatalogId() {return _foundCatalogId;}

  public void setNumItems(int pValue) {_numItems=pValue;}
  public int getNumItems() {return _numItems;}

  public void setActiveContracts(int pValue) {_activeContracts=pValue;}
  public int getActiveContracts() {return _activeContracts;}

  public void setNumDistributors(int pValue) {_numDistributors=pValue;}
  public int getNumDistributors() {return _numDistributors;}

  public void setPopulateCatalogFl(boolean pValue) {_populateCatalogFl=pValue;}
  public boolean getPopulateCatalogFl() {return _populateCatalogFl;}
  
  
  public void setBadDist(ProductDataVector pValue) {_products=pValue;}
  public ProductDataVector getBadDist(){return _products;}  
  public int getBadDistSize(){return _products.size();}


  public void setMissingData(ProductDataVector pValue) {_missingData=pValue;}
  public ProductDataVector getMissingData(){return _missingData;}  
  public int getMissingDataSize(){return _missingData.size();}

  // ---------------------------------------------------------------- Properties
  public void setAction(String pValue) {_action=pValue;}
  public String getAction(){return _action;}

  public void setCleanwiseFl(boolean pValue) {_cleanwiseFl=pValue;}
  public boolean getCleanwiseFl(){return _cleanwiseFl;}
  
  //Search properties
  public AccountDataVector getFilterAccounts() {return (this._filterAccounts); }
  public void setFilterAccounts(AccountDataVector pVal) { this._filterAccounts = pVal; }
  
  public String getSearchField() {return (this._searchField); }
  public void setSearchField(String pVal) { this._searchField = pVal; }

  public String getSearchType() {return (this._searchType);}
  public void setSearchType(String pVal) {this._searchType = pVal; }

  public String getCatalogType() {return (this._catalogType);}
  public void setCatalogType(String pVal) {this._catalogType = pVal; }

  public boolean isContainsFlag() {	return (this._containsFlag); }
  public void setContainsFlag(boolean pVal) {this._containsFlag = pVal; }

  public boolean isShowInactiveFlag() {	return (this._showInactiveFlag); }
  public boolean getShowInactiveFlag() {	return (this._showInactiveFlag); }
  public void setShowInactiveFlag(boolean pVal) {this._showInactiveFlag = pVal; }

  public List getResultList() {return (this._resultList);}
  public void setResultList(List pVal) {this._resultList = pVal;}

  public int[] getSelectedCatalogIds() {return (this._selectedCatalogIds);}
  public void setSelectedCatalogIds(int[] pVal) {this._selectedCatalogIds = pVal;}

  public CatalogDataVector getSelectedCatalogs() {return (this._selectedCatalogs);}
  public void setSelectedCatalogs(CatalogDataVector pVal) {this._selectedCatalogs = pVal;}

  
  
  public int getListCount() { return (this._resultList.size());}

  //----- contract methods
    public ContractData getContractDetail() {return (this._contractDetail); }
    public void setContractDetail(ContractData detail) {this._contractDetail = detail;}

    public String getFreightTableId() {return this._freightTableId;}
    public void setFreightTableId(String pFreightTableId) { this._freightTableId = pFreightTableId;}
    

    public String getFreightTableName() {return this._freightTableName; }
    public void setFreightTableName(String pFreightTableName) { this._freightTableName = pFreightTableName;}
    
    public FreightTableData getCurrentFreightTable() {return this._currentFreightTable;}
    public void setCurrentFreightTable(FreightTableData pFreightTable) {this._currentFreightTable = pFreightTable;}

    public String getOrderGuideName(int ii) {
      if(_orderGuides==null || _orderGuides.size()<ii) {
        return null;
      }
      OrderGuideDescData ogdD = (OrderGuideDescData) _orderGuides.get(ii);
      return ogdD.getOrderGuideName();
    }
    public void setOrderGuideName(int ii, String pOrderGuideName) {
      if(_orderGuides==null || _orderGuides.size()<ii) {
        return;
      }
      OrderGuideDescData ogdD = (OrderGuideDescData) _orderGuides.get(ii);
      ogdD.setOrderGuideName(pOrderGuideName);
      return;
    }
    
    public OrderGuideDescDataVector getOrderGuides() {return this._orderGuides;}
    public void setOrderGuides(OrderGuideDescDataVector pOrderGuides) { this._orderGuides = pOrderGuides;}
            
   
    public FreightTableCriteriaDescDataVector getCurrentFreightTableCriteria() {return this._currentFreightTableCriteria;}
    public void setCurrentFreightTableCriteria(FreightTableCriteriaDescDataVector pFreightTableCriteria) {
        this._currentFreightTableCriteria = pFreightTableCriteria; }

    public ArrayList getItemsDetailCollection() {
       if(null == _itemsDetailCollection) {
             _itemsDetailCollection = new ArrayList();
       }
        return _itemsDetailCollection;
    }

    public void setItemsDetailCollection(ArrayList pCol) {
        _itemsDetailCollection = pCol;
    }    

    public ContractItemDescDataVector getNonCatalogItems() {
        if(null == _nonCatalogItems) {
             _nonCatalogItems = new ContractItemDescDataVector();
        }
        return _nonCatalogItems;
    }

    public void setNonCatalogItems(ContractItemDescDataVector pCol) {
        _nonCatalogItems = pCol;
    }    
    
    public ArrayList getCatalogItemsCollection() {
        if(null == _catalogItemsCollection) {
             _catalogItemsCollection = new ArrayList();
        }
        return _catalogItemsCollection;
    }

    public void setCatalogItemsCollection(ArrayList pCol) {
        _catalogItemsCollection = pCol;
    }    

    public ContractItemDescData getItemDesc(int idx) {

        if (_itemsDetailCollection == null) {
            _itemsDetailCollection = new ArrayList();
        }
        while (idx >= _itemsDetailCollection.size()) {
            _itemsDetailCollection.add(new ContractItemDescData());
        }            
        return (ContractItemDescData) _itemsDetailCollection.get(idx);
    }
    

    
    public String [] getSelectItems() {return _selectItemsCollection;}
    public void setSelectItems(String[] pItemIds) {_selectItemsCollection = pItemIds;}

    //price enter fields
    public void setInputIds(String[] pValue) {_inputIds = pValue;}
    public String[] getInputIds(){return _inputIds;}
    public void setInputId(int ind, String inputId) {
      if(_inputIds.length>ind) {
        _inputIds[ind]=inputId;
      }
    }
    public String getInputId(int ind) {
      String ret = "";
      if(_inputIds.length>ind) {
        ret = _inputIds[ind];
      }
      return ret;
    }

    public void setDistCosts(String[] pValue) {_distCostS = pValue; }
    public String[] getDistCosts() {return _distCostS;}
    public void setDistCost(int ind, String pDistCostS) {
      if(_distCostS.length>ind) {
        _distCostS[ind]=pDistCostS;
      }
    }
    public String getDistCost(int ind) {
      String ret = "";
      if(_distCostS.length>ind) {
        ret = _distCostS[ind];
      }
      return ret;
    }
    
    public void setDistBaseCosts(String[] pValue) {_distBaseCostS = pValue; }
    public String[] getDistBaseCosts() {return _distBaseCostS;}
    public void setDistBaseCost(int ind, String pDistBaseCostS) {
      if(_distBaseCostS.length>ind) {
        _distBaseCostS[ind]=pDistBaseCostS;
      }
    }
    public String getDistBaseCost(int ind) {
      String ret = "";
      if(_distBaseCostS.length>ind) {
        ret = _distBaseCostS[ind];
      }
      return ret;
    }
    

    public void setAmounts(String[] pValue) {_amountS = pValue; }
    public String[] getAmounts() {return _amountS;}
    public void setAmount(int ind, String pAmountS) {
      if(_amountS.length>ind) {
        _amountS[ind]=pAmountS;
      }
    }
    public String getAmount(int ind) {
      String ret = "";
      if(_amountS.length>ind) {
        ret = _amountS[ind];
      }
      return ret;
    }

    //Costs Centers
    public void setBudgetPeriod(String pVal) {mBudgetPeriod = pVal;}
    public String getBudgetPeriod() {
      if (null == mBudgetPeriod) return "";
      return mBudgetPeriod;
    }
    

     public void setFiscalCalenderEffDate(java.util.Date v) {
        if ( v == null ) v = new java.util.Date();
        SimpleDateFormat df = new  SimpleDateFormat("M/dd/yyyy");
        this.mFiscalCalenderEffDate = df.format(v);
    }
    public void setFiscalCalenderEffDate(String v) {this.mFiscalCalenderEffDate = v;}
    public String getFiscalCalenderEffDate() {return this.mFiscalCalenderEffDate;}

    public void setBudgetTotal(String pVal) {mBudgetTotal = pVal;}
    public String getBudgetTotal() {return mBudgetTotal;}

    public void setBudgetStartDate(String pVal) {mBudgetStartDate = pVal;}
    public String getBudgetStartDate() {return mBudgetStartDate; }

    public void setCity(String pVal) {mCity = pVal;}
    public String getCity() {return mCity;}

    public void setState(String pVal) {mState = pVal;}
    public String getState() {return mState;}

    public void setSiteBudgetList(ArrayList pVal) {mSiteBudgetList = pVal;}
    public ArrayList getSiteBudgetList() {return mSiteBudgetList;}
    public SiteBudget getSiteBudget(int index) {
      return (SiteBudget)mSiteBudgetList.get(index);
    }


    public void setCatalogFiscalPeriods(CatalogFiscalPeriodViewVector v) {mCatalogFiscalPeriods = v;}
    public CatalogFiscalPeriodViewVector getCatalogFiscalPeriods() {return mCatalogFiscalPeriods ;}

    public void setCostCenter(CostCenterData v) {mCostCenter = v;}
    public CostCenterData getCostCenter() {return mCostCenter;}
    
    public void setFiscalCalenderView(FiscalCalenderView v) {mFiscalCalenderView = v;}
    public FiscalCalenderView getFiscalCalenderView() {return mFiscalCalenderView;}

    public void setBudgetAccounts(BusEntityDataVector v) {mBudgetAccounts = v;}
    public BusEntityDataVector getBudgetAccounts() {return mBudgetAccounts;}

    public void setBudgetAccount(BusEntityData v) {mBudgetAccount = v;}
    public BusEntityData getBudgetAccount() {return mBudgetAccount;}

    public void setBudgetAccountIdInp(int v) {mBudgetAccountIdInp = v;}
    public int getBudgetAccountIdInp() {return mBudgetAccountIdInp;}



    public String getCostCenterTypeCd() {return costCenterTypeCd;}
    public void setCostCenterTypeCd(String pVal) {costCenterTypeCd = pVal;}

    public BudgetView getBudget() {return budget;}
    public void setBudget(BudgetView pVal) {budget = pVal;}
  
  
  
    // --  Configuration
    public CatalogDataVector getConfCatalogFilter() {return mConfCatalogFilter;}
    public void setConfCatalogFilter(CatalogDataVector pVal) {mConfCatalogFilter = pVal;}

    public String getConfSearchField() {return mConfSearchField;}
    public void setConfSearchField(String pVal) {mConfSearchField = pVal;}

    public String getConfSearchType() {return mConfSearchType;}
    public void setConfSearchType(String pVal) {mConfSearchType = pVal;}

    public String getConfType() {return mConfType;}
    public void setConfType(String pVal) {mConfType = pVal;}

    public String getConfCity() {return mConfCity;}
    public void setConfCity(String pVal) {mConfCity = pVal;}

    public String getConfCounty() {return mConfCounty;}
    public void setConfCounty(String pVal) {mConfCounty = pVal;}

    public String getConfState() {return mConfState;}
    public void setConfState(String pVal) {mConfState = pVal;}

    public String getConfZipcode() {return mConfZipcode;}
    public void setConfZipcode(String pVal) {mConfZipcode = pVal;}

    public boolean getConfShowInactiveFl() {return mConfShowInactiveFl;}
    public boolean isConfShowInactiveFl() {return mConfShowInactiveFl;}
    public void setConfShowInactiveFl(boolean pVal) {mConfShowInactiveFl = pVal;}

    public boolean getConfShowConfguredOnlyFl() {return mConfShowConfguredOnlyFl;}
    public boolean isConfShowConfguredOnlyFl() {return mConfShowConfguredOnlyFl;}
    public void setConfShowConfguredOnlyFl(boolean pVal) {mConfShowConfguredOnlyFl = pVal;}

    public boolean getConfMoveSitesFl() {return mConfMoveSitesFl;}
    public boolean isConfMoveSitesFl() {return mConfMoveSitesFl;}
    public void setConfMoveSitesFl(boolean pVal) {mConfMoveSitesFl = pVal;}

    public String[] getAssocSiteIds() {return mAssocSiteIds;}
    public void setAssocSiteIds(String[] pVal) {mAssocSiteIds = pVal;}

    public String[] getSelectIds() {return mSelectIds;}
    public void setSelectIds(String[] pVal) {mSelectIds = pVal;}

    public String[] getDisplayIds() {return mDisplayIds;}
    public void setDisplayIds(String[] pVal) {mDisplayIds = pVal;}

    

  /**
   * <code>reset</code> method, set the search fiels to null.
   *
   * @param mapping an <code>ActionMapping</code> value
   * @param request a <code>HttpServletRequest</code> value
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    reset();
  }


  public void reset() {
      this._searchField = "";
      this._containsFlag = false;
      this._showInactiveFlag = false;
      this._selectItemsCollection = new String[0];
      this._populateCatalogFl = false;
      this._copyContractFl = "";
      this.mSelectIds = new String[0];
      this.mDisplayIds = new String[0];
      mConfShowInactiveFl = false;
      mConfShowConfguredOnlyFl = false;
      mConfMoveSitesFl = false;
      this.mSelectedMainDistributorId="";
      this._catalogType = "";
      this.updatePriceFromLoader = null;
      this._searchType = "catalogNameStarts";
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

    return super.validate(mapping, request);

  }

    /**
     * Holds value of property freightCostCenterId.
     */
    private String freightCostCenterId;

    /**
     * Getter for property freightCostCenterId.
     * @return Value of property freightCostCenterId.
     */
    public String getFreightCostCenterId() {

        return this.freightCostCenterId;
    }

    /**
     * Setter for property freightCostCenterId.
     * @param freightCostCenterId New value of property freightCostCenterId.
     */
    public void setFreightCostCenterId(String freightCostCenterId) {

        this.freightCostCenterId = freightCostCenterId;
    }

    /**
     * Holds value of property costCentersFound.
     */
    private CostCenterDataVector costCentersFound;

    /**
     * Getter for property costCentersFound.
     * @return Value of property costCentersFound.
     */
    public CostCenterDataVector getCostCentersFound() {

        return this.costCentersFound;
    }

    /**
     * Setter for property costCentersFound.
     * @param costCentersFound New value of property costCentersFound.
     */
    public void setCostCentersFound(CostCenterDataVector costCentersFound) {

        this.costCentersFound = costCentersFound;
    }

    public String getSelectedMainDistributorId() {
        return mSelectedMainDistributorId;
    }

    public void setSelectedMainDistributorId(String mainDistributorId) {
        this.mSelectedMainDistributorId = mainDistributorId;
    }
	/**
	 * @return Returns the updatePriceFromLoader.
	 */
//	public boolean isUpdatePriceFromLoader() {
//		return updatePriceFromLoader;
//	}
//	/**
//	 * @param updatePriceFromLoader The updatePriceFromLoader to set.
//	 */
//	public void setUpdatePriceFromLoader(boolean updatePriceFromLoader) {
//		this.updatePriceFromLoader = updatePriceFromLoader;
//	}
	/**
	 * @return Returns the updatePriceFromLoader.
	 */
	public String getUpdatePriceFromLoader() {
		return updatePriceFromLoader;
	}
	/**
	 * @param updatePriceFromLoader The updatePriceFromLoader to set.
	 */
	public void setUpdatePriceFromLoader(String updatePriceFromLoader) {
		this.updatePriceFromLoader = updatePriceFromLoader;
	}

    public String getBudgetAmounts(int pPeriod) {
        return Utility.strNN(budgetAmounts.get(pPeriod));
    }

    public void setBudgetAmounts(int pPeriod, String pValue) {
        budgetAmounts.put(pPeriod,pValue);
    }

    public HashMap<Integer, String> getBudgetAmounts() {
        return budgetAmounts;
    }

    public void setBudgetAmounts(HashMap<Integer, String> budgetAmounts) {
        this.budgetAmounts = budgetAmounts;
    }

    public String getDiscountTableId() {
        return _discountTableId;
    }

    public void setDiscountTableId(String pTableId) {
        _discountTableId = pTableId;
    }
    public String[] getBudgetThreshold() {
        return mBudgetThreshold;
    }
    public void setBudgetThreshold(String[] budgetThreshold) {
        this.mBudgetThreshold = budgetThreshold;
    }

    public void setDisplayBudgetThreshold(boolean pVal) {
        this.displayBudgetThreshold = pVal;
    }

    public boolean getDisplayBudgetThreshold() {
        return displayBudgetThreshold;
    }

    /// 'UpdaterAfterStoreChange' interface
    public void updateDataAfterStoreChange() {
        _resultList = new LinkedList();
        _filterAccounts = null;
        reset();
    }


}

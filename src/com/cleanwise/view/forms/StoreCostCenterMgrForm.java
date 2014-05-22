/*
 * StoreCostCenterMgrForm.java
 *
 * Created on June 12, 2006, 4:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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
 *
 * @author Ykupershmidt
 */
public class StoreCostCenterMgrForm 
      extends StorePortalBaseForm  {
  
  
 
   public AccountDataVector mAccountFilter;
   public SiteViewVector mSiteFilter;

  //Embadded forms
  private LocateStoreAccountForm _locateStoreAccountForm = null;
  int _level = 0;
  
  //Action
  private String _action=null;
 
 //Search properties
  private CatalogDataVector _filterCatalogs = null;
  private String _searchField = "";
  private String _searchType = "costCenterNameStarts";
  //private String _costCenterType = "";
  //private boolean _containsFlag = false;
  private boolean _showInactiveFlag = false;
  //private List _resultList = new LinkedList();
  private int[] _selectedCostCenterIds = new int[0];
  private CostCenterDataVector _selectedCostCenters = null;
  
  
  //Detail properties
  private CostCenterData _costCenterDetail = null;
  //private int _storeId = 0;
  //private String _storeName = "";
  private boolean _mayDelete=false;
  
  //Cost Centers   
  private String mBudgetPeriod;
  private String mFiscalCalenderEffDate;

  private String mBudgetTotal;
  private String mBudgetStartDate;
  private String mCity;
  private String mState;
  private ArrayList mSiteBudgetList;
  private CatalogFiscalPeriodViewVector mCatalogFiscalPeriods = null; 
  private CostCenterData mCostCenter = null;
  private FiscalCalenderView mFiscalCalenderView = null;
  private BusEntityDataVector mBudgetAccounts = new BusEntityDataVector();
  private BusEntityData mBudgetAccount = null;
  private int mBudgetAccountIdInp = 0;
  private int mBudgetSiteIdInp = 0;
  private String costCenterTypeCd;
  private HashMap<Integer, String> budgetAmounts;
  private String budgetAmount13;
  private BudgetView budget;

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

    private int[] mAssocIds = null;

    



  // ------------------------------------------------------------ Public Methods


  public LocateStoreAccountForm getLocateStoreAccountForm() { return this._locateStoreAccountForm;}
  public void setLocateStoreAccountForm(LocateStoreAccountForm pValue) {this._locateStoreAccountForm = pValue;}
  
  public void setEmbeddedForm(Object pVal) {
    if(pVal instanceof LocateStoreAccountForm) {
      _locateStoreAccountForm = (LocateStoreAccountForm) pVal;
    }
  } 
  
  public int getLevel() {return _level;}
  public void setLevel(int pVal) {_level = pVal;}
  

  
  public CostCenterData getCostCenterDetail() { return this._costCenterDetail;}
  public void setCostCenterDetail(CostCenterData costCenterDetail) {this._costCenterDetail = costCenterDetail;}

  //public int getStoreId() { return _storeId;}
  //public void setStoreId(int pStoreId) {_storeId = pStoreId;}

  //public String getStoreName() {return _storeName;}
  //public void setStoreName(String pStoreName) {_storeName=pStoreName; }

  public boolean getMayDelete() {return _mayDelete;}
  public void setMayDelete(boolean pMayDelete) {_mayDelete=pMayDelete;}

  
  // ---------------------------------------------------------------- Properties
  public void setAction(String pValue) {_action=pValue;}
  public String getAction(){return _action;}

  //Search properties
  public CatalogDataVector getFilterCatalogs() {return (this._filterCatalogs); }
  public void setFilterCatalogs(CatalogDataVector pVal) { this._filterCatalogs = pVal; }
  
  public String getSearchField() {return (this._searchField); }
  public void setSearchField(String pVal) { this._searchField = pVal; }

  public String getSearchType() {return (this._searchType);}
  public void setSearchType(String pVal) {this._searchType = pVal; }

  //public String getCostCenterType() {return (this._costCenterType);}
  //public void setCostCenterType(String pVal) {this._costCenterType = pVal; }

  //public boolean isContainsFlag() {	return (this._containsFlag); }
  //public void setContainsFlag(boolean pVal) {this._containsFlag = pVal; }

  public boolean isShowInactiveFlag() {	return (this._showInactiveFlag); }
  public boolean getShowInactiveFlag() {	return (this._showInactiveFlag); }
  public void setShowInactiveFlag(boolean pVal) {this._showInactiveFlag = pVal; }

  //public List getResultList() {return (this._resultList);}
  //public void setResultList(List pVal) {this._resultList = pVal;}

  public int[] getSelectedCostCenterIds() {return (this._selectedCostCenterIds);}
  public void setSelectedCostCenterIds(int[] pVal) {this._selectedCostCenterIds = pVal;}

  public CostCenterDataVector getSelectedCostCenters() {return (this._selectedCostCenters);}
  public void setSelectedCostCenters(CostCenterDataVector pVal) {this._selectedCostCenters = pVal;}

  
  
  public int getListCount() { 
    return (_selectedCostCenters == null)?0:_selectedCostCenters.size(); 
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

    public int[] getAssocIds() {return mAssocIds;}
    public void setAssocIds(int[] pVal) {mAssocIds = pVal;}

    //public String[] getSelectIds() {return mSelectIds;}
    //public void setSelectIds(String[] pVal) {mSelectIds = pVal;}

    //public String[] getDisplayIds() {return mDisplayIds;}
    //public void setDisplayIds(String[] pVal) {mDisplayIds = pVal;}

    

  /**
   * <code>reset</code> method, set the search fiels to null.
   *
   * @param mapping an <code>ActionMapping</code> value
   * @param request a <code>HttpServletRequest</code> value
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    //_containsFlag = false;
    _showInactiveFlag = false;
    //this.mSelectIds = new String[0];
    //this.mDisplayIds = new String[0];
    mAssocIds = new int[0];
    mConfShowInactiveFl = false;
    mConfShowConfguredOnlyFl = false;
    mConfMoveSitesFl = false;
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
    public String getFreightCostCenterId() {return this.freightCostCenterId; }
    public void setFreightCostCenterId(String freightCostCenterId) {this.freightCostCenterId = freightCostCenterId;}

    /**
     * Holds value of property costCentersFound.
     */
    private CostCenterDataVector costCentersFound;
    public CostCenterDataVector getCostCentersFound() {return this.costCentersFound;}
    public void setCostCentersFound(CostCenterDataVector costCentersFound) {this.costCentersFound = costCentersFound;}

    public int getBudgetSiteIdInp() {
        return mBudgetSiteIdInp;
    }

    public void setBudgetSiteIdInp(int budgetSiteIdInp) {
        this.mBudgetSiteIdInp = budgetSiteIdInp;
    }

    public AccountDataVector getAccountFilter() {
        return mAccountFilter;
    }

    public void setAccountFilter(AccountDataVector accountFilter) {
        this.mAccountFilter = accountFilter;
    }

    public SiteViewVector getSiteFilter() {
        return mSiteFilter;
    }

    public void setSiteFilter(SiteViewVector siteFilter) {
        this.mSiteFilter = siteFilter;
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

}


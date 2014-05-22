/*
 * StoreSiteMgrForm.java
 *
 * Created on March 20, 2006, 12:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cleanwise.view.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.FiscalPeriodView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.view.utils.ProfileViewContainer;


/**
 *
 * @author Ykupershmidt
 */
public class StoreSiteMgrForm  extends StorePortalBaseForm {

    private String _searchField = "";
    private String _searchRefNum = "";
    private String _searchType = "nameBegins";
    private String _searchRefNumType = "nameBegins";
    private String _searchCity = "";
    private String _searchCounty = "";
    private String _searchState = "";
    private String _searchPostalCode = "";
    private boolean _showInactiveFl = false;
    private AccountDataVector mAccountFilter;

    //private String _mId = "";
    private String _mName = "";
    private String _mCity = "";
    private String _mCounty = "";
    private String _mState = "";
    private String _mAccountIdList = "";
    private String mPostalCode;
    private String mRefValue;

    private String mAccountId;
    private String mAccountName;
    private String mStoreId;
    private String mStoreName;
    private ArrayList mSiteBudgetList;
    private String mSubContractor;
    private String mTaxableIndicator="N";
    private boolean mInventoryShopping;
    
    //this field is removed from the UI (site detail page) as per bug 3690.
    private boolean inventoryShoppingHoldOrderUntilDeliveryDate;
    private String mSiteNumber;
    private String mOldSiteNumber;
    private String mShippingMessage;
    private String mEffDate;
    private String mExpDate;
    private IdVector mAvailableShipto = null;
    private boolean ERPEnabled;

    private SiteData SiteData;

    private boolean shareBuyerOrderGuides;

    //Configuration

    private String _configSearchField = "";
    private String _configSearchType = "nameBegins";
    private String _configType = "";
    private boolean _configuredOnly = false;
    private String[] _selectIds = null;
    private String[] _displayIds = null;
    private String _catalogId = null;
    private String _oldCatalogId = null;
    private HashMap map = new HashMap();
    private String mSelectedWorkflowId="";
    private String mAssignedWorkflowId="";
    /* As per bug 3690, modified UI(Site Detail page), so that a single button
     * 'Enable Inventory Shopping' will modify both mInventoryShopping and 
     * mInventoryShoppingType
     */
    private boolean mInventoryShoppingType;
    private String mWorkflowTypeCd;
    private FiscalPeriodView fiscalInfo;
    private boolean allowBudgetThreshold;
    private String budgetThresholdType;
    private String productBundle;
    
    private int selectedFiscalYear; // Selected Fiscal Year
    private List<Integer> fiscalYearsList; // List of fiscal year
    private boolean enableCopyBudgetsButton; // This is for Site budget page.
    
    //STJ-4384: Add Rebill in Site Detail.
    private boolean _reBill;
    
    private boolean _allowCorpSchedOrder;

    public String getConfigSearchField() {return (this._configSearchField); }
    public void setConfigSearchField(String pVal) {this._configSearchField = pVal;}

    public String getConfigSearchType() {return (this._configSearchType);}
    public void setConfigSearchType(String pVal) {this._configSearchType = pVal;}

    public String getConfigType() {return (this._configType);}
    public void setConfigType(String pVal) {this._configType = pVal;}

    public boolean getConfiguredOnly() {return _configuredOnly;}
    public void setConfiguredOnly(boolean pVal) {_configuredOnly = pVal;}

    public void setSearchCity(String pVal) {this._searchCity = pVal;}
    public String getSearchCity() {return (this._searchCity);}

    public void setSearchCounty(String pVal) {this._searchCounty = pVal;}
    public String getSearchCounty() {return (this._searchCounty);}

    public void setSearchState(String pVal) {this._searchState = pVal;}
    public String getSearchState() {return (this._searchState);}

    public void setSearchPostalCode(String pVal) {this._searchPostalCode = pVal;}
    public String getSearchPostalCode() {return (this._searchPostalCode);}

    public String getCatalogId() {return (this._catalogId);}
    public void setCatalogId(String pVal) {this._catalogId = pVal;}

    public String getOldCatalogId() {return (this._oldCatalogId);}
    public void setOldCatalogId(String pVal) {this._oldCatalogId = pVal;}


    public void setSelectIds (String[] pValue) {_selectIds = pValue; }
    public String[] getSelectIds() {return _selectIds;}

    public void setDisplayIds (String[] pValue) {_displayIds = pValue;}
    public String[] getDisplayIds() {return _displayIds;}

    // General purpose hash map for config items.
    public Object getGeneralPropertyVal(String key) {return map.get(key);}
    public void setGeneralPropertyVal(String key, Object value) {map.put(key, value);}
    public HashMap getGeneralPropertyMap() {return map; }
////////////////////////

    public String getPostalCode() {return mPostalCode;}
    public void setPostalCode(String  v) {this.mPostalCode = v;}

    public void setSearchField(String pVal) {this._searchField = pVal;}
    public String getSearchField() {return (this._searchField);}

    public void setSearchRefNum(String pVal) {this._searchRefNum = pVal;}
    public String getSearchRefNum() {return (this._searchRefNum);}

    public void setSearchType(String pVal) {this._searchType = pVal;}
    public String getSearchType() {return (this._searchType);}

    public void setSearchRefNumType(String pVal) {this._searchRefNumType = pVal;}
    public String getSearchRefNumType() {return (this._searchRefNumType);}

    public void setShowInactiveFl(boolean pVal) {_showInactiveFl = pVal;}
    public boolean getShowInactiveFl() {return _showInactiveFl;}

    public void setAccountFilter(AccountDataVector pVal) {mAccountFilter = pVal;}
    public AccountDataVector getAccountFilter() {return mAccountFilter;}

    //public void setId(String pVal) {this._mId = pVal;}
    //public String getId() {return (this._mId);}

    public void setName(String pVal) {this._mName = pVal;}
    public String getName() {return (this._mName);}

    public void setCity(String pVal) {this._mCity = pVal;}
    public String getCity() {return (this._mCity);}

    public void setCounty(String pVal) {this._mCounty = pVal;}
    public String getCounty() {return (this._mCounty);}

    public void setState(String pVal) {this._mState = pVal;}
    public String getState() {return (this._mState);}

    public String getRefValue() {return mRefValue;}
    public void setRefValue(String  v) {this.mRefValue = v;}

    public String getAccountIdList() {return _mAccountIdList;}
    public void setAccountIdList(String  v) {_mAccountIdList = v;}



    /**
     * Get the value of ShippingMessage.
     * @return value of ShippingMessage.
     */
    public String getShippingMessage() {
        return mShippingMessage;
    }

    /**
     * Set the value of ShippingMessage.
     * @param v  Value to assign to ShippingMessage.
     */
    public void setShippingMessage(String  v) {
        this.mShippingMessage = v;
    }

    // Values for the 3 fields that an account
    // can collect from a site.
    private String mF1Value;
    private String mF2Value;
    private String mF3Value;
    private String mF4Value;
    private String mF5Value;
    private String mF6Value;
    private String mF7Value;
    private String mF8Value;
    private String mF9Value;
    private String mF10Value;
    private String siteReferenceNumber;
    private String distSiteReferenceNumber;
    private String targetFacilityRank;
    private String lineLevelCode;
    private ProfileViewContainer profile;

    private boolean authorizedReSaleAccount;
    private boolean bypassOrderRouting;
    private boolean makeShiptoBillto;
    private boolean consolidatedOrderWarehouse;
    private Integer blanketPoNumId;


    public String getStoreName() {return mStoreName;}
    public void setStoreName(String mStoreName) {this.mStoreName = mStoreName;}

    public String getAccountId() {return mAccountId;}
    public void setAccountId(String mAccountId) {this.mAccountId = mAccountId;}

    public String getAccountName() {return mAccountName;}
    public void setAccountName(String mAccountName) {this.mAccountName = mAccountName;}

    public String getStoreId() {return mStoreId;}
    public void setStoreId(String mStoreId) {this.mStoreId = mStoreId;}

    public SiteBudget getSiteBudget(int index) {return (SiteBudget) mSiteBudgetList.get(index);}

    public ArrayList getSiteBudgetList() {return mSiteBudgetList;}
    public void setSiteBudgetList(ArrayList pSiteBudgetList) {this.mSiteBudgetList = pSiteBudgetList;}

    public String getSubContractor() {return mSubContractor;}
    public void setSubContractor(String pSubContractor) {this.mSubContractor = pSubContractor;}

    public String getTaxableIndicator() {return mTaxableIndicator;}
    public void setTaxableIndicator(String pTaxableIndicator) {this.mTaxableIndicator = pTaxableIndicator;}

    public String getSiteNumber() {return mSiteNumber;}
    public void setSiteNumber(String pSiteNumber) {this.mSiteNumber = pSiteNumber;}

    public String getOldSiteNumber() {return mOldSiteNumber;}
    public void setOldSiteNumber(String pOldSiteNumber) {this.mOldSiteNumber = pOldSiteNumber;}

    public String getF1Value() {return mF1Value;}
    public void setF1Value(String v) {this.mF1Value = v;}

    public String getF2Value() {return mF2Value;}
    public void setF2Value(String v) {this.mF2Value = v;}

    public String getF3Value() {return mF3Value;}
    public void setF3Value(String v) {this.mF3Value = v;}

    public String getF4Value() {return mF4Value;}
    public void setF4Value(String v) {this.mF4Value = v;}

    public String getF5Value() {return mF5Value;}
    public void setF5Value(String v) {this.mF5Value = v;}

    public String getF6Value() {return mF6Value;}
    public void setF6Value(String v) {this.mF6Value = v;}

    public String getF7Value() {return mF7Value;}
    public void setF7Value(String v) {this.mF7Value = v;}

    public String getF8Value() {return mF8Value;}
    public void setF8Value(String v) {this.mF8Value = v;}

    public String getF9Value() {return mF9Value;}
    public void setF9Value(String v) {this.mF9Value = v;}

    public String getF10Value() {return mF10Value;}
    public void setF10Value(String v) {this.mF10Value = v;}

    public String getEffDate() {return mEffDate;}
    public void setEffDate(String v) {this.mEffDate = v;}

    public String getExpDate() {return mExpDate;}
    public void setExpDate(String v) {this.mExpDate = v;}


    public void setAvailableShipto(IdVector v) {this.mAvailableShipto = v;}
    public IdVector getAvailableShipto() {return mAvailableShipto;}

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        _searchField = "";
        _searchRefNum = "";
        _searchCity = "";
        _searchCounty = "";
        _searchState = "";
        _searchPostalCode = "";
        setBypassOrderRouting(false);
        setConsolidatedOrderWarehouse(false);
        setInventoryShopping(false);
        setInventoryShoppingType(false);
        setInventoryShoppingHoldOrderUntilDeliveryDate(false);
        setReBill(false);
        setAllowCorpSchedOrder(false);
        _configSearchField = "";
        _configuredOnly = false;
        _selectIds = new String[0];
        _displayIds = new String[0];
        _showInactiveFl = false;
        mSelectedWorkflowId="";
        return;
    }

    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        // No validation necessary.
        return null;
    }


    public String getSiteReferenceNumber() {return this.siteReferenceNumber; }
    public void setSiteReferenceNumber(String siteReferenceNumber) {
      this.siteReferenceNumber = siteReferenceNumber;
    }

    public String getDistSiteReferenceNumber() {return this.distSiteReferenceNumber; }
    public void setDistSiteReferenceNumber(String distSiteReferenceNumber) {
      this.distSiteReferenceNumber = distSiteReferenceNumber;
    }

    public boolean getInventoryShopping() {
    	return this.mInventoryShopping;
    	}
    public void setInventoryShopping(boolean v) {
    	this.mInventoryShopping = v;
    	}
    public void setInventoryShoppingStr(String v) {
      if ( v == null && v.length() == 0 ) {
        this.mInventoryShopping = false;
      }
      else {
          this.mInventoryShopping = v.equals("on");
      }
    }
    /*public void setInventoryShoppingHoldOrderUntilDeliveryDateStr(String v) {
        if ( v == null && v.length() == 0 ) {
          this.inventoryShoppingHoldOrderUntilDeliveryDate = false;
        }
        else {
            this.inventoryShoppingHoldOrderUntilDeliveryDate = v.equals("on");
        }
      }*/

    public String getTargetFacilityRank() {return this.targetFacilityRank;}
    public void setTargetFacilityRank(String targetFacilityRank) {
        this.targetFacilityRank = targetFacilityRank;
    }

    public String getLineLevelCode() {return this.lineLevelCode;}
    public void setLineLevelCode(String lineLevelCode) {
        this.lineLevelCode = lineLevelCode;
    }

    public ProfileViewContainer getProfile() {return this.profile;}
    public void setProfile(ProfileViewContainer profile) {this.profile = profile;}
    public boolean isAuthorizedReSaleAccount() {return this.authorizedReSaleAccount;}
    public void setAuthorizedReSaleAccount(boolean authorizedReSaleAccount) {
        this.authorizedReSaleAccount = authorizedReSaleAccount;
    }

    public boolean isBypassOrderRouting() {return this.bypassOrderRouting;}
    public void setBypassOrderRouting(boolean bypassOrderRouting) {
        this.bypassOrderRouting = bypassOrderRouting;
    }

    public boolean isMakeShiptoBillto() {return this.makeShiptoBillto;}
    public void setMakeShiptoBillto(boolean makeShiptoBillto) {
        this.makeShiptoBillto = makeShiptoBillto;
    }

    public boolean isConsolidatedOrderWarehouse() {return this.consolidatedOrderWarehouse;}
    public boolean getConsolidatedOrderWarehouse() {return this.consolidatedOrderWarehouse;}
    public void setConsolidatedOrderWarehouse(boolean consolidatedOrderWarehouse) {
        this.consolidatedOrderWarehouse = consolidatedOrderWarehouse;
    }

    public Integer getBlanketPoNumId() {return this.blanketPoNumId;}
    public void setBlanketPoNumId(Integer blanketPoNumId) {
        this.blanketPoNumId = blanketPoNumId;
    }

    public boolean isERPEnabled() {return this.ERPEnabled;}
    public void setERPEnabled(boolean ERPEnabled) {
        this.ERPEnabled = ERPEnabled;
    }


    public SiteData getSiteData() {return SiteData;}
    public void setSiteData(SiteData newSiteData) {this.SiteData = newSiteData;}

    public PropertyData getSiteProp(int propIdx) {
      PropertyDataVector v = getSiteData().getDataFieldProperties();
       if ( null == v ) v = new PropertyDataVector();
       while ( propIdx >= v.size() ) {
        v.add(PropertyData.createValue());
      }
      return (PropertyData)v.get(propIdx);
    }

    public void setSelectedWorkflowId(String selectedWorkflowId) {
        this.mSelectedWorkflowId=selectedWorkflowId;
    }
    public String getSelectedWorkflowId() {
        return mSelectedWorkflowId;
    }

    public String getAssignedWorkflowId() {
        return this.mAssignedWorkflowId;
    }
   public void setAssignedWorkflowId(String assignWId) {
        this.mAssignedWorkflowId = assignWId;
    }
public boolean isInventoryShoppingHoldOrderUntilDeliveryDate() {
  return inventoryShoppingHoldOrderUntilDeliveryDate;
}
public void setInventoryShoppingHoldOrderUntilDeliveryDate(
    boolean inventoryShoppingHoldOrderUntilDeliveryDate) {
  this.inventoryShoppingHoldOrderUntilDeliveryDate = inventoryShoppingHoldOrderUntilDeliveryDate;
}


public void setShareBuyerOrderGuides(boolean val) {
  this.shareBuyerOrderGuides = val;
}

public boolean getShareBuyerOrderGuides() {
  return this.shareBuyerOrderGuides;
}


    public boolean getInventoryShoppingType() {
        return mInventoryShoppingType;
    }

    public void setInventoryShoppingType(boolean pInventoryShoppingType) {
        this.mInventoryShoppingType = pInventoryShoppingType;
    }

    public void setInventoryShoppingTypeStr(String pInventoryShoppingTypeStr) {
        this.mInventoryShoppingType = !(pInventoryShoppingTypeStr == null
                                    || pInventoryShoppingTypeStr.length() == 0)
                                    && pInventoryShoppingTypeStr.equals("on");
    }

    public void setWorkflowTypeCd(String workflowTypeCd) {
        this.mWorkflowTypeCd = workflowTypeCd;
    }

    public String getWorkflowTypeCd() {
        return mWorkflowTypeCd;
    }

    public void setFiscalInfo(FiscalPeriodView fiscalInfo) {
        this.fiscalInfo = fiscalInfo;
    }

    public FiscalPeriodView getFiscalInfo() {
        return fiscalInfo;
    }

    public void setAllowBudgetThreshold(boolean pAllowBudgetThreshold) {
        this.allowBudgetThreshold = pAllowBudgetThreshold;
    }

    public boolean getAllowBudgetThreshold() {
        return allowBudgetThreshold;
    }

    public void setBudgetThresholdType(String budgetThresholdType) {
        this.budgetThresholdType = budgetThresholdType;
    }

    public String getBudgetThresholdType() {
        return budgetThresholdType;
    }

    public void setProductBundle(String productBundle) {
        this.productBundle = productBundle;
    }

    public String getProductBundle() {
        return productBundle;
    }
    
    public int getSelectedFiscalYear() {
		return selectedFiscalYear;
	}
	public void setSelectedFiscalYear(int selectedFiscalYear) {
		this.selectedFiscalYear = selectedFiscalYear;
	}
	public List<Integer> getFiscalYearsList() {
		return fiscalYearsList;
	}
	public void setFiscalYearsList(List<Integer> fiscalYearsList) {
		this.fiscalYearsList = fiscalYearsList;
	}
	public boolean getEnableCopyBudgetsButton() {
		return enableCopyBudgetsButton;
	}
	public void setEnableCopyBudgetsButton(boolean enableCopyBudgetsButton) {
		this.enableCopyBudgetsButton = enableCopyBudgetsButton;
	}
	public boolean isReBill() {
		return _reBill;
	}
	public void setReBill(boolean pReBill) {
		_reBill = pReBill;
	}
	public void setAllowCorpSchedOrder(boolean _allowCorpSchedOrder) {
		this._allowCorpSchedOrder = _allowCorpSchedOrder;
	}
	public boolean isAllowCorpSchedOrder() {
		return _allowCorpSchedOrder;
	}
	  
}


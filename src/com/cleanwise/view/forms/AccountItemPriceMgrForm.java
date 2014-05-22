/**
 * Title:        ItemSubstMgrForm
 * Description:  This is the Struts ActionForm class for the contract item substitution page.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;
import javax.servlet.http.HttpSession;


/**
 * Form bean for the add/remove contract item substitutions page.
 */
public final class AccountItemPriceMgrForm extends ActionForm {
    //general fields
    private int _catalogId = 0;
    private ContractDataVector _contracts = null;

    //Search fields
    private String _searchType = "";
    private String _skuTempl = "";
    private String _skuType = "SystemCustomer";
    private String _shortDescTempl = "";
    private String _longDescTempl = "";
    private String _manuId = "";
    private String _manuName = "";
    private String _distributorId = "";
    private String _distributorName = "";
    private String _categoryTempl = "";
    private String _sizeTempl = "";
    //Result fields
    private String _priceMulty = "";
    private String _costMulty = "";
    private ContractItemPriceViewVector _priceItems = null;
    private String[] _distCostS = new String[0];
    private String[] _priceS = new String[0];
    private String[] _inputIds = new String[0];
    private String[] _custSkuS = new String[0];
    private String[] _custDescS = new String[0];

    private String[] _selectorBox=new String[0];
    private String _sortField = null;
    String NewCustSku;
    
    /**
     * Get the value of NewCustSku.
     * @return value of NewCustSku.
     */
    public String getNewCustSku() {
	return NewCustSku;
    }
    
    /**
     * Set the value of NewCustSku.
     * @param v  Value to assign to NewCustSku.
     */
    public void setNewCustSku(String  v) {
	this.NewCustSku = v;
    }
    String NewCustDesc;
    
    /**
     * Get the value of NewCustDesc.
     * @return value of NewCustDesc.
     */
    public String getNewCustDesc() {
	return NewCustDesc;
    }
    
    /**
     * Set the value of NewCustDesc.
     * @param v  Value to assign to NewCustDesc.
     */
    public void setNewCustDesc(String  v) {
	this.NewCustDesc = v;
    }
    
    // --------- Access methods
    // General fields
    public void setCatalogId(int pValue) {_catalogId=pValue;}
    public int getCatalogId() {return _catalogId;}

    public void setContracts(ContractDataVector pValue) {_contracts=pValue;}
    public ContractDataVector getContracts() {return _contracts;}

    //Search fields
    public void setSearchType(String pValue) {_searchType=pValue;}
    public String getSearchType(){return _searchType;}

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

    public void setManuId(String pValue) {
      _manuId = pValue;
      if(pValue==null || pValue.trim().length()==0) {
        _manuName = "";
      }
    }
    public String getManuId(){return _manuId;}

    public void setManuName(String pValue) {_manuName = pValue;}
    public String getManuName(){return _manuName;}

    public void setDistributorId(String pValue) {
      _distributorId=pValue;
      if(pValue==null || pValue.trim().length()==0) {
        _distributorName="";
      }
    }
    public String getDistributorId(){return _distributorId;}

    public void setDistributorName(String pValue) {_distributorName = pValue;}
    public String getDistributorName(){return _distributorName;}

    //Result fields
    public boolean isOnlyOneItem() {
      if(_priceItems==null) {
        return false;
      }
      ContractItemPriceView cipVw = (ContractItemPriceView) _priceItems.get(0);
      int itemId = cipVw.getItemId();
      for(int ii=1; ii<_priceItems.size(); ii++) {
        cipVw = (ContractItemPriceView) _priceItems.get(ii);
        if(itemId!=cipVw.getItemId()) {
          return false;
        }
      }
      return true;
    }
    public void setPriceMulty(String pValue) {_priceMulty=pValue;}
    public String getPriceMulty(){return _priceMulty;}

    public void setCostMulty(String pValue) {_costMulty=pValue;}
    public String getCostMulty(){return _costMulty;}

    public void setPriceItems(ContractItemPriceViewVector pValue) {_priceItems=pValue;}
    public ContractItemPriceViewVector getPriceItems(){return _priceItems;}

    public void setSelectorBox(String[] pValue) {_selectorBox=pValue;}
    public String[] getSelectorBox(){return _selectorBox;}

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

    public void setPrices(String[] pValue) {_priceS = pValue; }
    public String[] getPrices() {return _priceS;}
    public void setPrice(int ind, String pPriceS) {
      if(_priceS.length>ind) {
        _priceS[ind]=pPriceS;
      }
    }
    public String getPrice(int ind) {
      String ret = "";
      if(_priceS.length>ind) {
        ret = _priceS[ind];
      }
      return ret;
    }

    public void setCustSku(String[] pValue) {_custSkuS = pValue; }
    public String[] getCustSku() {return _custSkuS;}
    public void setCustSku(int ind, String pVal) {
      if(_custSkuS.length>ind) {
        _custSkuS[ind]=pVal;
      }
    }
    public String getCustSku(int ind) {
      String ret = "";
      if(_custSkuS.length>ind) {
        ret = _custSkuS[ind];
      }
      return ret;
    }

    public void setCustDesc(String[] pValue) {_custDescS = pValue; }
    public String[] getCustDesc() {return _custDescS;}
    public void setCustDesc(int ind, String pVal) {
      if(_custDescS.length>ind) {
        _custDescS[ind]=pVal;
      }
    }
    public String getCustDesc(int ind) {
      String ret = "";
      if(_custDescS.length>ind) {
        ret = _custDescS[ind];
      }
      return ret;
    }

//    public void setOutServiceName(String pValue) {_outServiceName=pValue;}
//    public String getOutServiceName(){return _outServiceName;}

    public void setSortField(String pValue) {_sortField=pValue;}
    public String getSortField(){return _sortField;}



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

    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
      _sortField = null;
      _selectorBox=new String[0];
    }

}

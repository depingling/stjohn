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
public final class ItemSubstMgrForm extends ActionForm {
    //general fields
    private int _catalogId = 0;
    private int _contractId = 0;
    private int _storeCatalogId = 0;

    //Search fields
    private String _searchType = "";
    private String _skuTempl = "";
    private String _skuType = "System";
    private String _shortDescTempl = "";
    private String _longDescTempl = "";
    private String _manuId = "";
    private String _manuName = "";
    private String _distributorId = "";
    private String _distributorName = "";
    private String _categoryTempl = "";
    private String _sizeTempl = "";
    private int _itemOrSubst = 0;
    private boolean _substOnlyFlag = false;
    //To substitute
    private ProductDataVector _substItems = null;
    //Result fields
    private ContractItemSubstViewVector _substitutions = null;
    private String[] _selectorBox=new String[0];
    private IdVector _selectedSubstIds=new IdVector();
    private int _selectedItemId;
    //
    private String _outServiceName = "";

    // --------- Access methods
    // General fields
    public void setCatalogId(int pValue) {_catalogId=pValue;}
    public int getCatalogId() {return _catalogId;}

    public void setContractId(int pValue) {_contractId=pValue;}
    public int getContractId() {return _contractId;}

    public void setStoreCatalogId(int pValue) {_storeCatalogId=pValue;}
    public int getStoreCatalogId() {return _storeCatalogId;}
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

    public void setItemOrSubst(int pValue) {_itemOrSubst=pValue;}
    public int getItemOrSubst(){return _itemOrSubst;}

    public void setSubstOnlyFlag(boolean pValue) {
        _substOnlyFlag=pValue;
    }
    public boolean isSubstOnlyFlag(){
        return _substOnlyFlag;
    }

  //To substitute
    public void setSubstItems(ProductDataVector pValue) {_substItems=pValue;}
    public ProductDataVector getSubstItems(){return _substItems;}

    //Result fields
    public void setSubstitutions(ContractItemSubstViewVector pValue) {_substitutions=pValue;}
    public ContractItemSubstViewVector getSubstitutions(){return _substitutions;}

    public void setSelectedSubstIds(IdVector pValue) {_selectedSubstIds=pValue;}
    public IdVector getSelectedSubstIds(){return _selectedSubstIds;}

    public void setSelectorBox(String[] pValue) {_selectorBox=pValue;}
    public String[] getSelectorBox(){return _selectorBox;}

    //
    public void setOutServiceName(String pValue) {_outServiceName=pValue;}
    public String getOutServiceName(){return _outServiceName;}




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
      _substOnlyFlag = false;
    }

}

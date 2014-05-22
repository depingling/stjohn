/**
 * Title:        QuickOrderForm
 * Description:  This is the Struts ActionForm class for the shopping by janitor closet page.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.service.api.value.*;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import com.cleanwise.view.utils.Constants;
import java.math.BigDecimal;


/**
 * Form bean for the shopping page.  This form has the following fields,
 */

public final class QuickOrderForm extends ActionForm {


  public static int DEFAULT_SKU_TYPE = 1;
  public static int MFG_SKU_TYPE = 2;
  private static int INIT_PAGE_SIZE = 10;
  // -------------------------------------------------------- Instance Variables
  private int _shopMethod = -1;
  //order guide
  //---------------------


  private int _pageSize=INIT_PAGE_SIZE;

  private String[] _itemQtys = new String[_pageSize];
  private String[] _itemSkus = new String[_pageSize];
  private int _skuType = DEFAULT_SKU_TYPE;
  private int[] _itemIds = new int[_pageSize];
  private ArrayList[] _mfgNames = new ArrayList[_pageSize];
  private ArrayList[] _shortDesc = new ArrayList[_pageSize];
  private ArrayList[] _itemIdLists = new ArrayList[_pageSize];
  private boolean _duplicationFlag = false;
  private int _incPageSize = 0;
  private String confirmMessage = null;
  private List warningMessage = null;
  // ---------------------------------------------------------------- Properties
/*
  public String getRequestNumber(){return _requestNumber;}
  public void setRequestNumber(String pValue) {_requestNumber=pValue;}
*/
  public void init() {
    _itemQtys = new String[_pageSize];
    _itemSkus = new String[_pageSize];
    _skuType = DEFAULT_SKU_TYPE;
    _itemIds = new int[_pageSize];
    _mfgNames = new ArrayList[_pageSize];
    _shortDesc = new ArrayList[_pageSize];
    _itemIdLists = new ArrayList[_pageSize];
    _duplicationFlag = false;
  }

  public void clear() {
    _itemQtys = new String[_pageSize];
    _itemSkus = new String[_pageSize];
//    _skuType = DEFAULT_SKU_TYPE;
    _itemIds = new int[_pageSize];
    _mfgNames = new ArrayList[_pageSize];
    _shortDesc = new ArrayList[_pageSize];
    _itemIdLists = new ArrayList[_pageSize];
    _duplicationFlag = false;
    _incPageSize = 0;
  }

  public int getShopMethod() {return _shopMethod;}
  public void setShopMethod(int pValue) {_shopMethod = pValue;}

  public int getPageSize() {return _pageSize;};
  public void setPageSize(int v) {_pageSize = v;};

  public int getIncPageSize() {return _incPageSize;};
  public void setIncPageSize(int v) {_incPageSize = v;};

  public int getSkuType() {return _skuType;}
  public void setSkuType(int pValue) {_skuType = pValue;}

  public void setItemQtys(String[] pValue){_itemQtys=pValue;}
  public String[] getItemQtys() { return _itemQtys;}

  public void setItemQtysElement(int pIndex, String pValue){
    if(pIndex<0) return;
    if(pIndex<_itemQtys.length) {
      _itemQtys[pIndex]=pValue;
    }
  }

  public String getItemQtysElement(int pIndex) {
    if(pIndex<_itemQtys.length &&pIndex>=0) {
      return _itemQtys[pIndex];
    } else {
      return "";
    }
  }

  public void setItemSkus(String[] pValue) {_itemSkus = pValue;}
  public String[] getItemSkus() {return _itemSkus;}

  public void setItemSkusElement(int pIndex, String pValue){
    if(pIndex<0) return;
    if(pIndex<_itemSkus.length) {
      _itemSkus[pIndex]=pValue;
    }
  }

  public String getItemSkusElement(int pIndex) {
    if(pIndex<_itemSkus.length &&pIndex>=0) {
      return _itemSkus[pIndex];
    } else {
      return "";
    }
  }

  public int[] getItemIds(){return _itemIds;}
  public void setItemIds(int[] pValue) {_itemIds = pValue;}
  public void setItemIdsElement(int pIndex, int pValue){
    if(pIndex<0) return;
    if(pIndex<_itemIds.length) {
      _itemIds[pIndex]=pValue;
    }
  }
  public int getItemIdsElement(int pIndex) {
    if(pIndex<_itemIds.length &&pIndex>=0) {
      return _itemIds[pIndex];
    } else {
      return 0;
    }
  }



  public ArrayList[] getMfgNames(){return _mfgNames;}
  public void setMfgNames(ArrayList[] pValue) {_mfgNames = pValue;}

  public void setMfgNamesElement(int pIndex, ArrayList pValue){
    if(pIndex<0) return;
    if(pIndex<_mfgNames.length) {
      _mfgNames[pIndex]=pValue;
    }
  }
  public ArrayList getMfgNamesElement(int pIndex) {
    if(pIndex<_mfgNames.length &&pIndex>=0) {
      return _mfgNames[pIndex];
    } else {
      return new ArrayList();
    }
  }

  public ArrayList[] getShortDesc(){return _shortDesc;}
  public void setShortDesc(ArrayList[] pValue) {_shortDesc = pValue;}

  public void setShortDescElement(int pIndex, ArrayList pValue){
    if(pIndex<0) return;
    if(pIndex<_shortDesc.length) {
      _shortDesc[pIndex]=pValue;
    }
  }
  public ArrayList getShortDescElement(int pIndex) {
    if(pIndex<_shortDesc.length &&pIndex>=0) {
      return _shortDesc[pIndex];
    } else {
      return new ArrayList();
    }
  }



  public ArrayList[] getItemIdLists(){return _itemIdLists;}
  public void setItemIdLists(ArrayList[] pValue) {_itemIdLists = pValue;}

  public void setItemIdListsElement(int pIndex, ArrayList pValue){
    if(pIndex<0) return;
    if(pIndex<_itemIdLists.length) {
      _itemIdLists[pIndex]=pValue;
    }
  }
  public ArrayList getItemIdListsElement(int pIndex) {
    if(pIndex<_itemIdLists.length &&pIndex>=0) {
      return _itemIdLists[pIndex];
    } else {
      return new ArrayList();
    }
  }

  public boolean getDuplicationFlag(){return _duplicationFlag;}
  public void setDuplicationFlag(boolean pValue) {_duplicationFlag=pValue;}

  // ------------------------------------------------------------ Public Methods

  public void setConfirmMessage(String confirmMessage) {
      this.confirmMessage = confirmMessage;
  }

  public String getConfirmMessage() {
	  return confirmMessage;
  }

  public void setWarningMessage(List warningMessage) {
	  this.warningMessage = warningMessage;
  }

  public List getWarningMessage() {
	  return warningMessage;
  }

  /**
   * Reset all properties to their default values.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
  }


  /**
   * Validate the properties that have been set from this HTTP request,
   * and return an <code>ActionErrors</code> object that encapsulates any
   * validation errors that have been found.  If no errors are found, return
   * <code>null</code> or an <code>ActionErrors</code> object with no
   * recorded error messages.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {

    ActionErrors errors = new ActionErrors();
    return errors;
  }
}

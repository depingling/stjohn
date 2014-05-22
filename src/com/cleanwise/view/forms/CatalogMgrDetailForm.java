/**
 * Title:        ContractMgrDetail
 * Description:  This is the Struts ActionForm class for the contract detail page.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       
 */

package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import javax.servlet.http.HttpSession;
import com.cleanwise.service.api.*;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Store;
import java.rmi.RemoteException;
import com.cleanwise.service.api.util.DataNotFoundException;


/**
 * Form bean for the add/edit contract page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>detail</b> - A BusEntityData object
 * </ul>
 */
public final class CatalogMgrDetailForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables
    private CatalogData     _detail = CatalogData.createValue();
    private int _storeId = 0;
    private String _storeName = "";
    private int _masterCatalogId;
    private boolean _mayDelete=false;
    private int _foundCatalogId=0;
    private String _foundCatalogName;
    //validation information
    private int _numItems;
    private int _activeContracts;
    private int _numDistributors;
    
    private ProductDataVector _products=new ProductDataVector();
    private ProductDataVector _missingData=new ProductDataVector();
  // --------------------------------------------------------- Properties


  /**
   * Return the contract detail object
   */
  public CatalogData getDetail() {
    return (this._detail);
  }

  /**
   * Set the contract detail object
   */
  public void setDetail(CatalogData detail) {
    this._detail = detail;
  }

  public int getStoreId() {
    return _storeId;
  }

  public void setStoreId(int pStoreId) {
    _storeId = pStoreId;
  }

  public String getStoreName() {
    return _storeName;
  }
  public void setStoreName(String pStoreName) {
    _storeName=pStoreName;
  }

  public int getMasterCatalogId() {
    return _masterCatalogId;
  }

  public String getFoundCatalogName() {
    return _foundCatalogName;
  }

  public void setMasterCatalogId(int pMasterCatalogId) {
    _masterCatalogId = pMasterCatalogId;
  }

  public void setFoundCatalogName(String pVal) {
    _foundCatalogName = pVal;
  }

  public boolean getMayDelete() {
    return _mayDelete;
  }
  public void setMayDelete(boolean pMayDelete) {
    _mayDelete=pMayDelete;
  }



  public String getEffDate() {
    Date effDate =  this._detail.getEffDate();
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
    this._detail.setEffDate(effDate);
  }


  public String getExpDate() {
    Date expDate =  this._detail.getExpDate();
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
    this._detail.setExpDate(expDate);
  }

  public void setFoundCatalogId(int pValue) {_foundCatalogId=pValue;}
  public int getFoundCatalogId() {return _foundCatalogId;}

  public void setNumItems(int pValue) {_numItems=pValue;}
  public int getNumItems() {return _numItems;}

  public void setActiveContracts(int pValue) {_activeContracts=pValue;}
  public int getActiveContracts() {return _activeContracts;}

  public void setNumDistributors(int pValue) {_numDistributors=pValue;}
  public int getNumDistributors() {return _numDistributors;}

  public int getBadDistSize(){return _products.size();}

  public void setBadDist(ProductDataVector pValue) {_products=pValue;}
  public ProductDataVector getBadDist(){return _products;}  

  public int getMissingDataSize(){return _missingData.size();}

  public void setMissingData(ProductDataVector pValue) {_missingData=pValue;}
  public ProductDataVector getMissingData(){return _missingData;}  


  // ------------------------------------------------------------ Public Methods


  /**
   * Reset all properties to their default values.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {

    // create a new detail object and convert nulls to empty strings
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
                               HttpServletRequest request)
  {

    // Retrieve the action. We only want to validate on save.
    String action = request.getParameter("action");
    if (action == null) action = "";
    if (!action.equals("create")&&
        !action.equals("Create from existing catalog") &&
        !action.equals("Create from scracth") &&
        !action.equals("Save Catalog") ){
      return null;
    }
    // Perform the save validation.
    ActionErrors errors = new ActionErrors();
    if(_detail.getShortDesc().trim().length()==0) {
      errors.add("error1", new ActionError("catalog.new.no_name"));
    }
    if(_detail.getCatalogTypeCd().trim().length()==0) {
      errors.add("error2", new ActionError("catalog.new.no_type"));
    }
    if(_detail.getCatalogStatusCd().trim().length()==0) {
      errors.add("error3", new ActionError("catalog.new.no_status"));
    }
    if(_storeId==0) {
      errors.add("error4", new ActionError("catalog.new.no_store"));
    } else {
      HttpSession session = request.getSession();
      APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
        errors.add("error5", new ActionError("catalog.no_api_access"));
      } else {
        try {
          Store storeEjb = factory.getStoreAPI();
          StoreData storeD = storeEjb.getStore(_storeId);
        } catch (DataNotFoundException exc) {
          errors.add("error5", new ActionError("catalog.new.wrong_store"));
        } catch (APIServiceAccessException exc){
          errors.add("error5", new ActionError("catalog.no_api_access"));
        } catch (RemoteException exc) {
          errors.add("error6", new ActionError("catalog.remote_exception"));
        }
      }

    }
    return errors;

  }


}

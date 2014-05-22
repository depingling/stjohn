/*
 * LocateStoreCatalogForm.java
 *
 * Created on May 5, 2005, 2:33 PM
 */

package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.Collection;
import java.util.LinkedList;
import java.util.GregorianCalendar;
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
import org.apache.struts.upload.FormFile;


/**
 *
 * @author Ykupershmidt
 */
public class LocateStoreCatalogForm   extends LocateStoreBaseForm {
  
    private boolean _locateAccountFl = false;
    private AccountDataVector _accountFilter = null;
    private String _searchCatalogField = "";
    private String _searchCatalogType = "catalogNameStarts";
    private String _catalogTypeFilter = "";
    private boolean _containsFlag = false;
    private boolean _showInactiveCatalogFl = false;
    int[] _selectedCatalogIds = new int[0];
    private CatalogDataVector _catalogsSelected = null;
    private CatalogDataVector _catalogsToReturn = null;
    
    public boolean getLocateAccountFl() {return _locateAccountFl;}    
    public void setLocateAccountFl(boolean pVal) {_locateAccountFl = pVal;}

    public AccountDataVector getAccountFilter() {return _accountFilter;}    
    public void setAccountFilter(AccountDataVector pVal) {_accountFilter = pVal;}
 
    public void setSearchCatalogField(String pVal) {this._searchCatalogField = pVal;}
    public String getSearchCatalogField() {return (this._searchCatalogField);}

    public void setSearchCatalogType(String pVal) {this._searchCatalogType = pVal;}
    public String getSearchCatalogType() {return (this._searchCatalogType);}

    public void setCatalogTypeFilter(String pVal) {this._catalogTypeFilter = pVal;}
    public String getCatalogTypeFilter() {return (this._catalogTypeFilter);}

    public boolean isContainsFlag() {	return (this._containsFlag); }
    public void setContainsFlag(boolean pVal) {this._containsFlag = pVal; }

    
    public int[] getSelectedCatalogIds() {return _selectedCatalogIds;}    
    public void setSelectedCatalogIds(int[] pVal) {_selectedCatalogIds = pVal;}

    public boolean getShowInactiveCatalogFl() {return _showInactiveCatalogFl;}    
    public void setShowInactiveCatalogFl(boolean pVal) {_showInactiveCatalogFl = pVal;}
   
    public CatalogDataVector getCatalogsSelected() {return _catalogsSelected;}    
    public void setCatalogsSelected(CatalogDataVector pVal) {_catalogsSelected = pVal;}
    public int getListCount() {
      if(_catalogsSelected==null) return 0;
      return _catalogsSelected.size();
    }
 
    public CatalogDataVector getCatalogsToReturn() {return _catalogsToReturn;}    
    public void setCatalogsToReturn(CatalogDataVector pVal) {_catalogsToReturn = pVal;}

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    _selectedCatalogIds = new int[0];  
    _showInactiveCatalogFl = false;
  }
  
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request)
  {
    return null;
  }
}

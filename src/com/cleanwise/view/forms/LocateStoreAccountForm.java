/*
 * LocateStoreAccountForm.java
 *
 * Created on April 28, 2005, 12:01 PM
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
public class LocateStoreAccountForm extends LocateStoreBaseForm
{ 
  
    private String _searchField = "";
    private String _searchType = "";
    private boolean _showInactiveFl = false;
    int[] _selected = new int[0];
    private GroupDataVector _accountGroups = null;
    private int searchGroupId;    
    private AccountDataVector _accounts = null;
    private AccountDataVector _accountsToReturn = null;
    private AccountSearchResultViewVector _accountSearchResult = null;

 
    public void setSearchField(String pVal) {this._searchField = pVal;}
    public String getSearchField() {return (this._searchField);}

    public void setSearchType(String pVal) {this._searchType = pVal;}
    public String getSearchType() {return (this._searchType);}


    public int getSearchGroupId() {return this.searchGroupId;}    
    public void setSearchGroupId(int pValue) {this.searchGroupId = pValue;}
    
    public GroupDataVector getAccountGroups() {return _accountGroups;}    
    public void setAccountGroups(GroupDataVector pVal) {_accountGroups = pVal;}

    public int[] getSelected() {return _selected;}    
    public void setSelected(int[] pVal) {_selected = pVal;}

    public boolean getShowInactiveFl() {return _showInactiveFl;}    
    public void setShowInactiveFl(boolean pVal) {_showInactiveFl = pVal;}

    public AccountDataVector getAccounts() {return _accounts;}    
    public void setAccounts(AccountDataVector pVal) {_accounts = pVal;}
   
    public AccountDataVector getAccountsToReturn() {return _accountsToReturn;}    
    public void setAccountsToReturn(AccountDataVector pVal) {_accountsToReturn = pVal;}
    
    public AccountSearchResultViewVector getAccountSearchResult() {return _accountSearchResult;}    
    public void setAccountSearchResult(AccountSearchResultViewVector pVal) {_accountSearchResult = pVal;}


  public void reset(ActionMapping mapping, HttpServletRequest request) {
    _selected = new int[0];  
    _showInactiveFl = false;
  }
  
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request)
  {
    return null;
  }
   
}

package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.GroupDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Alexander Chikin
 * Date: 17.08.2006
 * Time: 1:32:25
 *
 */
public class LocateStoreUserForm extends LocateStoreBaseForm {
    private String _searchField = "";
    private String _searchType = "";
    private RefCdDataVector   _userTypeList;
    private String _userType="";
    private String _firstName="";
    private String _lastName="";

    private boolean _showInactiveFl = false;
    int[] _selected = new int[0];
    UserDataVector  _users=null;
    UserDataVector _usersToReturn=null;
    private int _searchStoreId=-1;
  private String userTypesAutorized;

  public String getSearchField() {
        return _searchField;
    }

    public void setSearchField(String _searchField) {
        this._searchField = _searchField;
    }

    public String getSearchType() {
        return _searchType;
    }

    public void setSearchType(String _searchType) {
        this._searchType = _searchType;
    }

    public boolean getShowInactiveFl() {
        return _showInactiveFl;
    }

    public void setShowInactiveFl(boolean _showInactiveFl) {
        this._showInactiveFl = _showInactiveFl;
    }

    public int[] getSelected() {
        return _selected;
    }

    public void setSelected(int[] _selected) {
        this._selected = _selected;
    }

    public UserDataVector getUsers() {
        return _users;
    }

    public void setUsers(UserDataVector _users) {
        this._users = _users;
    }

    public UserDataVector getUsersToReturn() {
        return _usersToReturn;
    }

    public void setUsersToReturn(UserDataVector _usersToReturn) {
        this._usersToReturn = _usersToReturn;
    }


    public void reset(ActionMapping mapping, HttpServletRequest request) {
    _selected = new int[0];
    _showInactiveFl = false;
  }

  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request)
  {
    return null;
  }


    public void setSearchStoreId(int searchStoreId) {
        this._searchStoreId = searchStoreId;
    }
       public int getSearchStoreId() {
        return _searchStoreId;
    }

    public String getUserType() {
        return _userType;
    }

    public void setUserType(String _userType) {
        this._userType = _userType;
    }

    public String get_searchField() {
        return _searchField;
    }

  public String getUserTypesAutorized() {
    return userTypesAutorized;
  }

  public void set_searchField(String _searchField) {
        this._searchField = _searchField;
    }

  public void setUserTypesAutorized(String userTypesAutorized) {
    this.userTypesAutorized = userTypesAutorized;
  }

  public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        this._firstName = firstName;
    }

    public String getLastName() {
        return _lastName;
    }


    public void setLastName(String lastName) {
        this._lastName = lastName;


    }

    public RefCdDataVector getUserTypeList() {
        return _userTypeList;
    }

    public void setUserTypeList(RefCdDataVector userTypeList) {
        this._userTypeList = userTypeList;
    }
}

/*
 * LocateStoreDistForm.java
 *
 * Created on May 24, 2005, 3:54 PM
 */

package com.cleanwise.view.forms;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cleanwise.service.api.value.*;


/**
 *
 * @author Ykupershmidt
 */
public class LocateStoreDistForm  extends LocateStoreBaseForm
{

    private String _searchField = "";
    private String _searchType = "";

    private String _searchCity = "";
    private String _searchState = "";
    private String _searchCounty = "";
    private String _searchPostalCode = "";

    private boolean _showInactiveFl = false;
    int[] _selected = new int[0];
    private GroupDataVector _distGroups = null;
    private int searchGroupId;
    private DistributorDataVector _distributors = null;
    private DistributorDataVector _distributorsToReturn = null;
  private String dataSourceType;

  public void setSearchField(String pVal) {this._searchField = pVal;}
    public String getSearchField() {return (this._searchField);}

    public void setSearchType(String pVal) {this._searchType = pVal;}
    public String getSearchType() {return (this._searchType);}

    public void setSearchCity(String pVal) {this._searchCity = pVal;}
    public String getSearchCity() {return (this._searchCity);}

    public void setSearchState(String pVal) {this._searchState = pVal;}
    public String getSearchState() {return (this._searchState);}

    public void setSearchCounty(String pVal) {this._searchCounty = pVal;}
    public String getSearchCounty() {return (this._searchCounty);}

    public void setSearchPostalCode(String pVal) {this._searchPostalCode = pVal;}
    public String getSearchPostalCode() {return (this._searchPostalCode);}

    public int getSearchGroupId() {return this.searchGroupId;}

  public String getDataSourceType() {
    return dataSourceType;
  }

  public void setSearchGroupId(int pValue) {this.searchGroupId = pValue;}

  public void setDataSourceType(String dataSourceType) {
    this.dataSourceType = dataSourceType;
  }

  public GroupDataVector getDistGroups() {return _distGroups;}
    public void setDistGroups(GroupDataVector pVal) {_distGroups = pVal;}

    public int[] getSelected() {return _selected;}
    public void setSelected(int[] pVal) {_selected = pVal;}

    public boolean getShowInactiveFl() {return _showInactiveFl;}
    public void setShowInactiveFl(boolean pVal) {_showInactiveFl = pVal;}

    public DistributorDataVector getDistributors() {return _distributors;}
    public void setDistributors(DistributorDataVector pVal) {_distributors = pVal;}

    public DistributorDataVector getDistributorsToReturn() {return _distributorsToReturn;}
    public void setDistributorsToReturn(DistributorDataVector pVal) {_distributorsToReturn = pVal;}

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

/*
 * LocateStoreManufForm.java
 *
 * Created on Feb 21, 2006, 12:05 PM
 */

package com.cleanwise.view.forms;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cleanwise.service.api.value.*;


/**
 *
 * @author Ykupershmidt
 */
public class LocateStoreManufForm  extends LocateStoreBaseForm
{

    private String _searchField = "";
    private String _searchType = "";

    private boolean _showInactiveFl = false;
    int[] _selected = new int[0];
    private ManufacturerDataVector _manufacturers = null;
    private ManufacturerDataVector _manufacturersToReturn = null;
  private String dataSourceType;

  public void setSearchField(String pVal) {this._searchField = pVal;}
    public String getSearchField() {return (this._searchField);}

    public void setSearchType(String pVal) {this._searchType = pVal;}
    public String getSearchType() {return (this._searchType);}


    public int[] getSelected() {return _selected;}
    public void setSelected(int[] pVal) {_selected = pVal;}

    public boolean getShowInactiveFl() {return _showInactiveFl;}
    public void setShowInactiveFl(boolean pVal) {_showInactiveFl = pVal;}

    public ManufacturerDataVector getManufacturers() {return _manufacturers;}
    public void setManufacturers(ManufacturerDataVector pVal) {_manufacturers = pVal;}

    public ManufacturerDataVector getManufacturersToReturn() {return _manufacturersToReturn;}
    public void setManufacturersToReturn(ManufacturerDataVector pVal) {_manufacturersToReturn = pVal;}

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    _selected = new int[0];
    _showInactiveFl = false;
  }

  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request)
  {
    return null;
  }

  public void setDataSourceType(String dataSourceType) {
    this.dataSourceType = dataSourceType;
  }

  public String getDataSourceType() {
    return dataSourceType;
  }

}


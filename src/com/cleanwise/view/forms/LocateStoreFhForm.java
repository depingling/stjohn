package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.FreightTableDataVector;
import com.cleanwise.service.api.value.FreightHandlerViewVector;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Alexander Chikin
 * Date: 22.08.2006
 * Time: 15:17:00
 */
public class LocateStoreFhForm  extends LocateStoreBaseForm{
    private String _searchField = "";
    private String _searchType = "nameBegins";
    private boolean _showInactiveFl = false;
    int[] _selected = new int[0];
    FreightHandlerViewVector  _freightHandler=null;
    FreightHandlerViewVector _freightHandlerToReturn=null;
    private int searchStoreId=-1;


    public FreightHandlerViewVector getFreightHandler() {
        return _freightHandler;
    }

    public void setFreightHandler(FreightHandlerViewVector freighthandler) {
        this._freightHandler = freighthandler;
    }

    public String getSearchField() {
        return _searchField;
    }

    public void setSearchField(String searchField) {
        this._searchField = searchField;
    }

    public int getSearchStoreId() {
        return searchStoreId;
    }

    public void setSearchStoreId(int searchStoreId) {
        this.searchStoreId = searchStoreId;
    }

    public String getSearchType() {
        return _searchType;
    }

    public void setSearchType(String searchType) {
        this._searchType = searchType;
    }

    public int[] getSelected() {
        return _selected;
    }

    public void setSelected(int[] selected) {
        this._selected = selected;
    }

    public boolean getShowInactiveFl() {
        return _showInactiveFl;
    }

    public void setShowInactiveFl(boolean showInactiveFl) {
        this._showInactiveFl = showInactiveFl;
    }

    public FreightHandlerViewVector getFreightHandlerToReturn() {
        return _freightHandlerToReturn;
    }

    public void setFreightHandlerToReturn(FreightHandlerViewVector freightHandlerToReturn) {
        this._freightHandlerToReturn = freightHandlerToReturn;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest servletRequest) {
         _selected = new int[0];
        _showInactiveFl=false;
    }

}

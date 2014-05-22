/*
 * LocateReportItemForm.java
 *
 * Created on April, 2010
 */

package com.cleanwise.view.forms;

import java.util.Collection;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.view.utils.*;


public class LocateReportItemForm extends LocateStoreBaseForm {

    private String _searchField = "";
    private String _searchType = "";
    //private boolean _showInactiveFl = false;
    int[] _selected = new int[0];
    private int _searchGroupId;
    private ItemViewVector _items = null;
    private ItemViewVector _itemsToReturn = null;
	private String _dataSourceType = "";

    private String _categoryTempl = "";
    private String _categoryTemplType = "nameBegins";

    private String _shortDescTempl = "";
    private String _shortDescTemplType = "nameBegins";

    private String _manufTempl = "";
    private String _manufTemplType = "nameBegins";

    private String _skuTempl = "";
    private String _skuTemplType = SearchCriteria.DISTRIBUTOR_SKU_NUMBER;


    public String getSearchField() {
        return _searchField;
    }

    public void setSearchField(String searchField) {
        _searchField = searchField;
    }

    public String getSearchType() {
        return _searchType;
    }

    public void setSearchType(String searchType) {
        _searchType = searchType;
    }


    public String getCategoryTempl() {
        return _categoryTempl;
    }

    public void setCategoryTempl(String v) {
        _categoryTempl = v;
    }

    public void setShortDescTempl(String v) {
        _shortDescTempl = v;
    }

    public String getShortDescTempl() {
        return _shortDescTempl;
    }


    public void setManufTempl(String v) {
        _manufTempl = v;
    }

    public String getManufTempl() {
        return _manufTempl;
    }

    public void setSkuTempl(String v) {
        _skuTempl = v;
    }

    public String getSkuTempl() {
        return _skuTempl;
    }


    public String getCategoryTemplType() {
        return _categoryTemplType;
    }

    public void setCategoryTemplType(String v) {
        _categoryTemplType = v;
    }

    public void setShortDescTemplType(String v) {
        _shortDescTemplType = v;
    }

    public String getShortDescTemplType() {
        return _shortDescTemplType;
    }


    public void setManufTemplType(String v) {
        _manufTemplType = v;
    }

    public String getManufTemplType() {
        return _manufTemplType;
    }

    public void setSkuTemplType(String v) {
        _skuTemplType = v;
    }

    public String getSkuTemplType() {
        return _skuTemplType;
    }







    public int getSearchGroupId() {
        return _searchGroupId;
    }

    public void setSearchGroupId(int searchGroupId) {
        _searchGroupId = searchGroupId;
    }

    public int[] getSelected() {
        return _selected;
    }

    public void setSelected(int[] selected) {
        _selected = selected;
    }

    /*public boolean getShowInactiveFl() {
        return _showInactiveFl;
    }

    public void setShowInactiveFl(boolean showInactiveFl) {
        _showInactiveFl = showInactiveFl;
    } */

    public ItemViewVector getItems() {
        return _items;
    }

    public void setItems(ItemViewVector v) {
        _items = v;
    }

    public ItemViewVector getItemsToReturn() {
        return _itemsToReturn;
    }

    public void setItemsToReturn(ItemViewVector itemsToReturn) {
        _itemsToReturn = itemsToReturn;
    }
	
	
    public String getDataSourceType() {
        return _dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        _dataSourceType = dataSourceType;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        _selected = new int[0];
        //_showInactiveFl = false;
/*        _categoryTemplType = "nameBegins";
        _shortDescTempl = "";
        _shortDescTemplType = "nameBegins";
        _manufTempl = "";
        _manufTemplType = "nameBegins";
        _skuTempl = "";
        _skuTemplType = SearchCriteria.DISTRIBUTOR_SKU_NUMBER;*/
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        return null;
    }



}

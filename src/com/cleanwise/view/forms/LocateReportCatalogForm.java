package com.cleanwise.view.forms;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.value.CatalogDataVector;


public class LocateReportCatalogForm extends LocateStoreBaseForm {

	private String _searchField = "";
	private String _searchType = "catalogNameStarts";
	private CatalogDataVector _resultList = new CatalogDataVector();
	private int[] _selected;

	public String getSearchField() {
		return (this._searchField);
	}

	public void setSearchField(String pVal) {
		this._searchField = pVal;
	}

	public String getSearchType() {
		return (this._searchType);
	}

	public void setSearchType(String pVal) {
		this._searchType = pVal;
	}

	public CatalogDataVector getResultList() {
		return (this._resultList);
	}

	public void setResultList(CatalogDataVector pVal) {
		this._resultList = pVal;
	}

	public void setSelected(int[] _selected) {
		this._selected = _selected;
	}

	public int[] getSelected() {
		return _selected;
	}

	public int getListCount() {
		return (this._resultList.size());
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		_selected = new int[0];
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// No validation necessary.
		return null;
	}




}

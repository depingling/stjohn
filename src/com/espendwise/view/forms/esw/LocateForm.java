/**
 * Title: LocateForm
 
 * Description: This is the Struts ActionForm class handling the ESW locate functionality.
 *
 */

package com.espendwise.view.forms.esw;

import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.view.forms.LocateReportAccountForm;
import com.cleanwise.view.utils.Constants;

/**
 * Implementation of <code>ActionForm</code> that handles reporting
 * functionality.
 */
public final class LocateForm extends EswForm {
    private static final long serialVersionUID = 1L;
    private LocationSearchDto _locationSearchInfo;
    private LocateReportAccountForm _locateReportAccountForm;
    private String _reportResultsSubTab;
    private long _reportEncodedId;
    private int[] _selected;
    private boolean _showInactiveFl;
    private int _randId;
    private String _sortField;
    private String _sortOrder;
    private String _hiddenName;
    private String _pageForSpecifyLocation;

    /**
     * @return the reportResultsSubTab
     */
    public String getReportResultsSubTab() {
        return _reportResultsSubTab;
    }

    /**
     * @param pReportResultsSubTab
     *            the reportResultsSubTab to set
     */
    public void setReportResultsSubTab(String pReportResultsSubTab) {
        _reportResultsSubTab = pReportResultsSubTab;
    }

    public long getReportEncodedId() {
        return _reportEncodedId;
    }

    public void setReportEncodedId(long pReportEncodedId) {
        _reportEncodedId = pReportEncodedId;
    }

    public LocateReportAccountForm getLocateReportAccountForm() {
        if (_locateReportAccountForm == null) {
            _locateReportAccountForm = new LocateReportAccountForm();
            _locateReportAccountForm.setSearchType(Constants.NAME_BEGINS);
        }
        return _locateReportAccountForm;
    }

    public void setLocateReportAccountForm(
            LocateReportAccountForm locateReportAccountForm) {
        this._locateReportAccountForm = locateReportAccountForm;
    }

    public LocationSearchDto getLocationSearchInfo() {
        if (_locationSearchInfo == null) {
            _locationSearchInfo = new LocationSearchDto();
        }
        return _locationSearchInfo;
    }

    public void setLocationSearchInfo(LocationSearchDto locationSearchInfo) {
        _locationSearchInfo = locationSearchInfo;
    }

    public int[] getSelected() {
        return _selected;
    }

    public void setSelected(int[] selected) {
        _selected = selected;
    }

    public boolean isShowInactiveFl() {
        return _showInactiveFl;
    }

    public void setShowInactiveFl(boolean showInactiveFl) {
        _showInactiveFl = showInactiveFl;
    }

    public int getRandId() {
        return _randId;
    }

    public void setRandId(int randId) {
        this._randId = randId;
    }

    public String getSortField() {
        return _sortField;
    }

    public void setSortField(String sortField) {
        this._sortField = sortField;
    }

    public String getSortOrder() {
        return _sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this._sortOrder = sortOrder;
    }

    public String getHiddenName() {
        return _hiddenName;
    }

    public void setHiddenName(String hiddenName) {
        this._hiddenName = hiddenName;
    }

	public String getPageForSpecifyLocation() {
		return _pageForSpecifyLocation;
	}

	public void setPageForSpecifyLocation(String pPageForSpecifyLocation) {
		_pageForSpecifyLocation = pPageForSpecifyLocation;
	}
    
}
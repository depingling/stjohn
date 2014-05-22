/*
 * LocateStoreOrderGuideForm.java
 *
 * Created on September, 2008
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.OrderGuideDescData;
import com.cleanwise.service.api.value.OrderGuideDescDataVector;


public class LocateStoreOrderGuideForm extends LocateStoreBaseForm {

    private String _searchObjectField = "";
    private String _searchObjectType = "";
    private String _objectTypeFilter = "";
    private boolean _containsFlag = false;
    private boolean _showInactiveObjectFl = false;
    private OrderGuideDescDataVector _objectsSelected = null;
    private OrderGuideDescDataVector _objectsToReturn = null;
    int[] _selectedObjectIds = new int[0];

    public String getSearchObjectField() {
        return _searchObjectField;
    }

    public void setSearchObjectField(String searchObjectField) {
        _searchObjectField = searchObjectField;
    }

    public String getSearchObjectType() {
        return _searchObjectType;
    }

    public void setSearchObjectType(String searchObjectType) {
        _searchObjectType = searchObjectType;
    }

    public String getObjectTypeFilter() {
        return _objectTypeFilter;
    }

    public void setObjectTypeFilter(String objectTypeFilter) {
        _objectTypeFilter = objectTypeFilter;
    }

    public boolean getContainsFlag() {
        return _containsFlag;
    }    

    public void setContainsFlag(boolean containsFlag) {
        _containsFlag = containsFlag;
    }

    public boolean getShowInactiveObjectFl() {
        return _showInactiveObjectFl;
    }    

    public void setShowInactiveObjectFl(boolean showInactiveObjectFl) {
        _showInactiveObjectFl = showInactiveObjectFl;
    }

    public OrderGuideDescDataVector getObjectsSelected() {
        return _objectsSelected;
    }    

    public void setObjectsSelected(OrderGuideDescDataVector objectsSelected) {
        _objectsSelected = objectsSelected;
    }

    public OrderGuideDescDataVector getObjectsToReturn() {
        return _objectsToReturn;
    }    

    public void setObjectsToReturn(OrderGuideDescDataVector objectsToReturn) {
        _objectsToReturn = objectsToReturn;
    }

    public int[] getSelectedObjectIds() {
        return _selectedObjectIds;
    }    

    public void setSelectedObjectIds(int[] selectedObjectIds) {
        _selectedObjectIds = selectedObjectIds;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        _selectedObjectIds = new int[0];  
        _showInactiveObjectFl = false;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        return null;
    }
}

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.service.api.value.GroupDataVector;


public class LocateStoreGroupForm extends LocateStoreBaseForm {

    private String _groupName;
    private String _groupType;
    private boolean _showInactiveFl;
    private int[] _selected;
    private GroupDataVector _groups;
    private GroupDataVector _groupsToReturn;
    private int _searchStoreId;

    public LocateStoreGroupForm() {
            _groupName = "";
            _groupType = "";
            _showInactiveFl = false;
            _selected = new int[0];
            _groups = null;
            _groupsToReturn = null;
            _searchStoreId = 0;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        return new ActionErrors();
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        _selected = new int[0];
        _showInactiveFl = false;
    }

    public String getGroupName() {
        return _groupName;
    }

    public void setGroupName(String groupName) {
        _groupName = groupName;
    }

    public String getGroupType() {
        return _groupType;
    }

    public void setGroupType(String groupType) {
        _groupType = groupType;
    }

    public GroupDataVector getGroups() {
        return _groups;
    }

    public void setGroups(GroupDataVector groups) {
        _groups = groups;
    }

    public GroupDataVector getGroupsToReturn() {
        return _groupsToReturn;
    }

    public void setGroupsToReturn(GroupDataVector groupsToReturn) {
        _groupsToReturn = groupsToReturn;
    }

    public int getSearchStoreId() {
        return _searchStoreId;
    }

    public void setSearchStoreId(int searchStoreId) {
        _searchStoreId = searchStoreId;
    }

    public int[] getSelected() {
        return _selected;
    }

    public void setSelected(int[] selected) {
        _selected = selected;
    }

    public boolean getShowInactiveFl() {
        return _showInactiveFl;
    }

    public void setShowInactiveFl(boolean showInactiveFl) {
        _showInactiveFl = showInactiveFl;
    }

}

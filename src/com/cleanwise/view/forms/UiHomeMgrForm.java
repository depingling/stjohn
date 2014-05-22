package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.*;

import java.util.Iterator;


public class UiHomeMgrForm extends ActionForm {
    private static int ASSOC_STR_LENGTH = 200;

    private UiGroupDataViewVector mManagedGroups;
    private String assocName;

    private UiGroupDataView currentGroup;



    public void setManagedGroups(UiGroupDataViewVector pManagedGroups) {
        this.mManagedGroups = pManagedGroups;
    }

    public UiGroupDataViewVector getManagedGroups() {
        return mManagedGroups;
    }

    public void setAssocName(String v) {
        assocName = v;
    }

    public String getAssocName() {
        return assocName;
    }

    public String getAssocNamesShortStr(UiGroupDataView groupView) {
        StringBuffer result = new StringBuffer();
        BusEntityDataVector associations = groupView.getGroupAssociations();

        for (int i=0; i<associations.size(); i++) {
            BusEntityData assoc = (BusEntityData)associations.get(i);
            if (i > 0) {
                result.append(", ");
            }
            result.append(assoc.getShortDesc());
            if (result.length() > ASSOC_STR_LENGTH) {
                if (i < associations.size()-1) {
                    result.append(", ...");
                }
                break;
            }
        }
        return result.toString();

    }

    public void setCurrentGroup(UiGroupDataView v) {
        currentGroup = v;
    }

    public UiGroupDataView getCurrentGroup() {
        return currentGroup;
    }
}

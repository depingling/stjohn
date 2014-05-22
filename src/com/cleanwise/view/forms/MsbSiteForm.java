package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.MenuItemView;

import java.util.ArrayList;

/**
 */
public class MsbSiteForm extends ActionForm {

    private MenuItemView trackOrderPopup;
    private MenuItemView maintenancePopup;
    private MenuItemView profilePopup;

    public void setTrackOrderPopup(MenuItemView trackOrderPopup) {
        this.trackOrderPopup = trackOrderPopup;
    }

    public MenuItemView getTrackOrderPopup() {
        return trackOrderPopup;
    }

    public void setMaintenancePopup(MenuItemView maintenancePopup) {
        this.maintenancePopup = maintenancePopup;
    }

    public MenuItemView getMaintenancePopup() {
        return maintenancePopup;
    }

    public MenuItemView getProfilePopup() {
        return profilePopup;
    }

    public void setProfilePopup(MenuItemView v) {
        this.profilePopup = v;
    }
}

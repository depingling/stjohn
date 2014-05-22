package com.cleanwise.view.utils;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;

/**
 * Title:        Admin2UiUtillity
 * Description:  set of static utility functions
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         09.10.2009
 * Time:         14:39:46
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class Admin2UiUtillity {

    /**
     * set acctive status to all pages and controls which contains ui.
     *
     * @param pUi ui
     */
    public static void rebuild4SysAdmin(UiView pUi) {
        if (pUi != null) {
            UiPageViewVector uipages = pUi.getUiPages();
            if (uipages != null && !uipages.isEmpty()) {
                for (Object oUiPage : uipages) {
                    UiPageView uiPage = (UiPageView) oUiPage;
                    uiPage.getUiPage().setStatusCd(RefCodeNames.UI_PAGE_STATUS_CD.ACTIVE);
                    UiControlViewVector uicontrols = uiPage.getUiControls();
                    if (uicontrols != null && !uicontrols.isEmpty()) {
                        for (Object oUiControl : uicontrols) {
                            UiControlView uiControl = (UiControlView) oUiControl;
                            uiControl.getUiControlData().setStatusCd(RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE);
                        }
                    }
                }
            }
        }
    }

}

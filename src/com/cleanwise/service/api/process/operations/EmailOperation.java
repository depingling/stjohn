package com.cleanwise.service.api.process.operations;

import java.io.File;

import com.cleanwise.service.api.value.EventEmailData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.apps.ApplicationsEmailTool;


public class EmailOperation {

    /*
     * This method just passes through to the ApplicationsEmailTool.  It is here as a reminder that there
     * are rows in the CLW_TASK table that make reference to this class, so we cannot change this method
     * without updating the templates.xml file used to populate the CLW_TASK table.
     */
    public void createEvent(EventEmailDataView emailData, Integer orderId, String processName) throws Exception {
        new ApplicationsEmailTool().createEvent(emailData, orderId, processName, "EmailOperation");
    }

    /*
     * This method just passes through to the ApplicationsEmailTool.  It is here as a reminder that there
     * are rows in the CLW_TASK table that make reference to this class, so we cannot change this method
     * without updating the templates.xml file used to populate the CLW_TASK table.
     */
    public EventEmailData prepareEventEmailData(String toAddress, String ccAddress, String subject, String message, File[]  attachments, String addBy) {
        return new ApplicationsEmailTool().prepareEventEmailData(toAddress, ccAddress, subject, message, attachments, addBy);
    }

}

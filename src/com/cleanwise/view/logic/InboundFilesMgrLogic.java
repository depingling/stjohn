package com.cleanwise.view.logic;

import java.io.OutputStream;
import java.util.Date;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.InboundFiles;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.InboundData;
import com.cleanwise.service.api.value.InboundDataVector;
import com.cleanwise.view.forms.InboundFilesMgrForm;


public class InboundFilesMgrLogic {

    public static final String className = "InboundFilesMgrLogic";
    public final static String DATE_FORMAT = "MM/dd/yyyy";
    public final static String INBOUND_FILES_MGR_FORM = "INBOUND_FILES_MGR_FORM";
    public final static String PARAMETER_INBOUND_ID = "id";
    public final static String PARAMETER_INBOUND_FILE_NAME = "filename";

    public static ActionErrors init(HttpServletResponse response, 
        HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors errors = new ActionErrors();
        InboundFilesMgrForm inboundForm = (InboundFilesMgrForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        InboundFiles inboundFilesEjb = factory.getInboundFilesAPI();
        inboundForm.setInboundFilesCount(inboundFilesEjb.getInboundFileCount());
        session.setAttribute(INBOUND_FILES_MGR_FORM, inboundForm);

        return errors;
    }

    public static ActionErrors search(HttpServletResponse response, HttpServletRequest request, ActionForm form)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        InboundFilesMgrForm inboundForm = (InboundFilesMgrForm)form;
        InboundDataVector inboundVector = new InboundDataVector();

        try {
            ///
            APIAccess factory = new APIAccess();
            InboundFiles inboundFilesEjb = factory.getInboundFilesAPI();
            ///
            do {
                int inboundId = 0;
                /// 'Indound Id' was selected?
                /// 'Indound Id' have value greater than 0 if 'Indound Id' was selected.
                if (!isEmptyString(inboundForm.getSearchInboundId())) {
                    try {
                        inboundId = Integer.parseInt(inboundForm.getSearchInboundId());
                    } catch (NumberFormatException ex) {
                        errors.add("Checking parameters", new ActionMessage("error.invalidNumber", "Inbound Id"));
                        break;
                    }
                }
                /// Searching of 'Infound File' by id.
                if (inboundId > 0) {
                    InboundData inboundData = inboundFilesEjb.getInboundFileById(inboundId);
                    if (inboundData != null) {
                        inboundVector.add(inboundData);
                    }
                    break;
                }
                /// 'Indound Id' was not selected.
                /// Searching of 'Infound Files' by the others parameters.
                /// In this case the 'Date From' parameter should be specified.
                Date dateFrom = null;
                Date dateTo = null;
                if (isEmptyString(inboundForm.getSearchDateFrom())) {
                    errors.add("Checking parameters", new ActionMessage("variable.empty.error", "From Date"));
                    break;
                }
                dateFrom = Utility.parseDate(inboundForm.getSearchDateFrom(), DATE_FORMAT, false);
                if (dateFrom == null) {
                    errors.add("Checking parameters", new ActionMessage("error.badDateFormat", "From Date"));
                    break;
                }
                if (!isEmptyString(inboundForm.getSearchDateTo())) {
                    dateTo = Utility.parseDate(inboundForm.getSearchDateTo(), DATE_FORMAT, false);
                    if (dateTo == null) {
                        errors.add("Checking parameters", new ActionMessage("error.badDateFormat", "To Date"));
                        break;
                    }
                }
                ///
                inboundVector = inboundFilesEjb.getInboundFilesByCriteria(dateFrom, 
                    dateTo, inboundForm.getSearchFileName(), 
                    inboundForm.getSearchPartnerKey(), 
                    inboundForm.getSearchUrl());
            } while (false);
        } catch(RemoteException ex) {
            String msg = "An error occurred at searching. " + ex.getMessage();
            errors.add("Error while searching", new ActionMessage("error.simpleGenericError", msg));
        } catch(Exception ex) {
            String msg = "An error occurred at searching. " + ex.getMessage();
            errors.add("Error while searching", new ActionMessage("error.simpleGenericError", msg));
        }
        inboundForm.setInboundFiles(inboundVector);
        session.setAttribute(INBOUND_FILES_MGR_FORM, inboundForm);
        return errors;
    }

    public static ActionErrors downladInboundFile(HttpServletResponse response, 
        HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        InboundFilesMgrForm inboundForm = (InboundFilesMgrForm)form;
        try {
            APIAccess factory = new APIAccess();
            InboundFiles inboundFilesEjb = factory.getInboundFilesAPI();
            ///
            do {
                int inboundId = 0;
                String fileName = null;
                if (isEmptyString(request.getParameter(PARAMETER_INBOUND_ID))) {
                    errors.add("Error download file", new ActionMessage("variable.empty.error", "Inbound Id"));
                    break;
                }
                if (isEmptyString(request.getParameter(PARAMETER_INBOUND_FILE_NAME))) {
                    errors.add("Error download file", new ActionMessage("variable.empty.error", "File Name"));
                    break;
                }
                try {
                    inboundId = Integer.parseInt(request.getParameter(PARAMETER_INBOUND_ID));
                } catch (NumberFormatException ex) {
                    errors.add("Checking parameters", new ActionMessage("error.invalidNumber", "Inbound Id"));
                    break;
                }
                fileName = request.getParameter(PARAMETER_INBOUND_FILE_NAME);
                InboundData inboundData = inboundFilesEjb.getInboundFileById(inboundId);
                if (inboundData == null) {
                    errors.add("Error download file", new ActionMessage("error.simpleGenericError", "Inbound file is not found"));
                    break;
                }
                byte[] data = inboundData.getDecryptBinaryData();
                if (data == null) {
                    errors.add("Loading binary data from file", new ActionMessage("error.simpleGenericError", "File to download is empty"));
                } else {
                    try {
                        response.setContentType("application/octet-stream");
                        response.setHeader("Cache-Control", "no-cache");
                        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
                        OutputStream outputStream = response.getOutputStream();
                        outputStream.write(data);
                        outputStream.flush();
                        outputStream.close();
                    } catch(Exception ex){
                        String msg = "An error occurred at writing of file into stream. " + ex.getMessage();
                        errors.add("Loading binary data from file", new ActionMessage("error.simpleGenericError", msg));
                    }
                }
            } while (false);
        } catch(RemoteException ex) {
            String msg = "An error occurred at loading of file. " + ex.getMessage();
            errors.add("Error download file", new ActionMessage("error.simpleGenericError", msg));
        } catch(Exception ex) {
            String msg = "An error occurred at loading of file. " + ex.getMessage();
            errors.add("Error download file", new ActionMessage("error.simpleGenericError", msg));
        }
        session.setAttribute(INBOUND_FILES_MGR_FORM, inboundForm);
        return errors;
    }

    private static boolean isEmptyString(String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().length() == 0) {
            return true;
        }
        return false;
    }

}

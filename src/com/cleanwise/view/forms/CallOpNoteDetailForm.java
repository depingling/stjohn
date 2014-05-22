/**
 * Title:        CallOpDetailForm
 * Description:  This is the Struts ActionForm class for the callOpDetail page.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       
 */

package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CurrencyFormat;
import javax.servlet.http.HttpSession;


/**
 * Form bean for the add/edit CallDetail page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>callDetail</b> - A CallData object
 * </ul>
 */
public final class CallOpNoteDetailForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private String _mCallId   = new String("");    
    private CallPropertyData    _mNoteDetail = CallPropertyData.createValue();
    
    
  // ---------------------------------------------------------------- Properties

    /**
     * Return the CallId object
     */
    public String getCallId() {
        return (this._mCallId);
    }


    /**
     * Set the CallId object
     */
    public void setCallId(String pVal) {
        this._mCallId = pVal;
    }


    /**
     * Return the NoteDetail object
     */
    public CallPropertyData getNoteDetail() {
        return (this._mNoteDetail);
    }

    /**
     * Set the NoteDetail object
     */
    public void setNoteDetail(CallPropertyData pVal) {
        this._mNoteDetail = pVal;
    }
    
    
    
  // ------------------------------------------------------------ Public Methods


    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }


    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *
     * Required fields are:
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {

        // Retrieve the action. We only want to validate on save.
        String action = request.getParameter("action");
        if (action == null) action = "";
        if (!action.equals("Save")) {
            return null;
        }
        
        String change = request.getParameter("change");
        if(null != change && "type".equals(change)) {
            return null;
        }
        
        // Is there a currently logged on user?
        HttpSession currentSession = request.getSession();
        if ((currentSession == null) || (currentSession.getAttribute(Constants.APIACCESS) == null) 
            || (currentSession.getAttribute(Constants.APP_USER) == null)) {
            return null;
        }        
        

        // Perform the save validation.
        return null;
    }


}

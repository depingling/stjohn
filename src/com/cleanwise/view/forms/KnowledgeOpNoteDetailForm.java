/**
 * Title:        KnowledgeOpDetailForm
 * Description:  This is the Struts ActionForm class for the knowledgeOpDetail page.
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
 * Form bean for the add/edit KnowledgeDetail page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>knowledgeDetail</b> - A KnowledgeData object
 * </ul>
 */
public final class KnowledgeOpNoteDetailForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private String _mKnowledgeId   = new String("");    
    private KnowledgePropertyData    _mNoteDetail = KnowledgePropertyData.createValue();
    
    
  // ---------------------------------------------------------------- Properties

    /**
     * Return the KnowledgeId object
     */
    public String getKnowledgeId() {
        return (this._mKnowledgeId);
    }


    /**
     * Set the KnowledgeId object
     */
    public void setKnowledgeId(String pVal) {
        this._mKnowledgeId = pVal;
    }


    /**
     * Return the NoteDetail object
     */
    public KnowledgePropertyData getNoteDetail() {
        return (this._mNoteDetail);
    }

    /**
     * Set the NoteDetail object
     */
    public void setNoteDetail(KnowledgePropertyData pVal) {
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

        // Perform the save validation.
        return null;
    }


}

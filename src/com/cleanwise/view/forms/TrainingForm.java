/**
 * Title:        TrainingForm
 * Description:  This is the Struts ActionForm class for the training page.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Date;
import com.cleanwise.view.utils.*;
import java.math.BigDecimal;


/**
 * Form bean for the training page.  This form has the following fields,
 */

public final class TrainingForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables
  private int mHowToCleanTopicId = 0;
  private NoteViewVector mNoteList = null;
  private NoteJoinView mNote = null;

  public int getHowToCleanTopicId(){return mHowToCleanTopicId;}
  public void setHowToCleanTopicId(int pValue) {mHowToCleanTopicId = pValue;}

  public NoteViewVector getNoteList(){return mNoteList;}
  public void setNoteList(NoteViewVector pValue) {mNoteList = pValue;}
  
  public NoteJoinView getNote(){return mNote;}
  public void setNote(NoteJoinView pValue) {mNote = pValue;}
  
  
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
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {

    ActionErrors errors = new ActionErrors();
    return errors;
  }


    
}

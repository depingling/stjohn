/**
 * Title:        ShareReportForm
 * Description:  This is the Struts ActionForm class for 
 * sharing preapred reports
 * Purpose:      Share prepared reports
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;

public final class ShareReportForm extends ActionForm {
    //
    private String mUserToAdd = "";
    private String mGroupToAdd = "";
    
    private String mSharingType = RefCodeNames.REPORT_RESULT_ASSOC_CD.GROUP;
    private boolean mAssignedFl = true;
    private UserDataVector mUsers = new UserDataVector();
    private GroupDataVector mGroups = new GroupDataVector();
    private IdVector mReportResultAssocIds = new IdVector();
    private String[] mGroupSelected = new String[0];
    private String[] mUserSelected = new String[0];
    
    private PreparedReportView mReport = PreparedReportView.createValue();
    
    private int mReportResultId = 0;
    
//-----------------------------------------------------------------------------
    public String getUserToAdd(){return mUserToAdd;}
    public void setUserToAdd (String pUserToAdd) {mUserToAdd = pUserToAdd;}

    public String getGroupToAdd(){return mGroupToAdd;}
    public void setGroupToAdd (String pGroupToAdd) {mGroupToAdd = pGroupToAdd;}

   // private String mSharingType = RefCodeNames.REPORT_RESULT_ASSOC_CD.GROUP;
    public String getSharingType(){return mSharingType;}
    public void setSharingType (String pSharingType) {mSharingType = pSharingType;}

   // private boolean mAssignedFl = true;
    public boolean getAssignedFl(){return mAssignedFl;}
    public void setAssignedFl (boolean pAssignedFl) {mAssignedFl = pAssignedFl;}

   // private UserDataVector mUsers = new UserDataVector();
    public UserDataVector getUsers(){return mUsers;}
    public void setUsers (UserDataVector pUsers) {mUsers = pUsers;}

   // private GroupDataVector mGroups = new GroupDataVector();
    public GroupDataVector getGroups(){return mGroups;}
    public void setGroups (GroupDataVector pGroups) {mGroups = pGroups;}

    public IdVector getReportResultAssocIds(){return mReportResultAssocIds;}
    public void setReportResultAssocIds (IdVector pReportResultAssocIds) 
                                {mReportResultAssocIds = pReportResultAssocIds;}

   // private String[] mGroupSelected = new String[0];
    public String[] getGroupSelected(){return mGroupSelected;}
    public void setGroupSelected (String[] pGroupSelected) {mGroupSelected = pGroupSelected;}

   // private String[] mUserSelected = new String[0];
    public String[] getUserSelected(){return mUserSelected;}
    public void setUserSelected (String[] pUserSelected) {mUserSelected = pUserSelected;}

   // private PreparedReportView mReport = PreparedReportView.createValue();
    public PreparedReportView getReport(){return mReport;}
    public void setReport (PreparedReportView pReport) {mReport = pReport;}

   // private int mReportResultId = 0;
    public int getReportResultId(){return mReportResultId;}
    public void setReportResultId (int pReportResultId) {mReportResultId = pReportResultId;}

    /*
    public ** get..(){return m..;}
    public void set.. (** p..) {m.. = p..;}
    */
    
    
    /**
     * <code>reset</code> method
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
      mGroupSelected = new String[0];
      mUserSelected = new String[0];
    }
    
    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
    HttpServletRequest request) {
        
        // Perform the run validation.
        ActionErrors errors = new ActionErrors();
        return errors;
    }
    
    
    
    
}


package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.logic.CrcSiteNoteLogic;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;

import java.util.Enumeration;
/**
 * Implementation of <strong>Action</strong> that saves a new
 * catalog detail or updates an existing catalog detail.
 */
public final class CrcSiteNoteAction extends ActionSuper {


    public ActionForward performSub(
				 ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws IOException, ServletException {


	String action  = request.getParameter("action");
	HttpSession session = request.getSession();

	// Is there a currently logged on user?
	SessionTool st = new SessionTool(request);
	if ( st.checkSession() == false ) {
	    return mapping.findForward("/userportal/logon");
	}   
    
    session.setAttribute("LAST_ACTION",action);
	NoteMgrForm sForm = (NoteMgrForm) form;
    sForm.setErrorFl(false);
	if(action==null) { action = "init"; }
	try {
	   if(action.equals("init") ) {
 	       ActionErrors ae = CrcSiteNoteLogic.init(request,sForm);
             if (ae.size() > 0) {
               sForm.setErrorFl(true);
               saveErrors(request, ae);
               return (mapping.findForward("failure"));
             }
           }
       else if(action.equals("Add Note") ) {
   	     ActionErrors ae = CrcSiteNoteLogic.addNote(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }       
       else if(action.equals("editNote") ) {
   	     ActionErrors ae = CrcSiteNoteLogic.editNote(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }       
       else if(action.equals("Save Note") ) {
   	     ActionErrors ae = CrcSiteNoteLogic.saveNote(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if(action.equals("Delete Notes") ) {
   	     ActionErrors ae = CrcSiteNoteLogic.delNotes(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if(action.equals("readNote") ) {
   	     ActionErrors ae = CrcSiteNoteLogic.readNote(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if(action.equals("Delete Selected") ) {
   	     ActionErrors ae = CrcSiteNoteLogic.delParagraphs(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
   	     ae = CrcSiteNoteLogic.delSelectedAttachments(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if (action.equals("sortNotes")) {
   	     ActionErrors ae = CrcSiteNoteLogic.sortNotes(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
      }
       else if (action.equals("attachment")) {
   	     ActionErrors ae = CrcSiteNoteLogic.downloadAttachment(request,response,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	    }
       
       
	} catch (Exception e) {
	    request.setAttribute("errorobject", e);
	    e.printStackTrace();
            throw new ServletException(e.getMessage());
	    //return (mapping.findForward("failure"));
	}

	return (mapping.findForward("success"));
    }
    
}

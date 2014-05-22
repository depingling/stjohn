
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
import com.cleanwise.view.logic.SiteMgrNoteLogic;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;

import java.util.Enumeration;
/**
 * Implementation of <strong>Action</strong> that saves a new
 * catalog detail or updates an existing catalog detail.
 */
public final class SiteMgrNoteAction extends ActionSuper {


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
 	       ActionErrors ae = SiteMgrNoteLogic.init(request,sForm);
             if (ae.size() > 0) {
               sForm.setErrorFl(true);
               saveErrors(request, ae);
               return (mapping.findForward("failure"));
             }
           }
       else if(action.equals("Add Topic") ) {
 	   ActionErrors ae = SiteMgrNoteLogic.addTopic(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
          }
	   }           
       else if(action.equals("editTopic") ) {
 	   ActionErrors ae = SiteMgrNoteLogic.editTopic(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
          }
	   }           
      else if(action.equals("Update Topic") ) {
 	   ActionErrors ae = SiteMgrNoteLogic.updateTopic(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
          }
	   }           
       else if(action.equals("selectTopic") ) {
         ActionErrors ae = SiteMgrNoteLogic.moveToSelectTopic(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if(action.equals("deleteTopic") ) {
   	     ActionErrors ae = SiteMgrNoteLogic.deleteTopic(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if(action.equals("loadTopic") ) {
   	     ActionErrors ae = SiteMgrNoteLogic.loadTopic(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if(action.equals("Add Note") ) {
   	     ActionErrors ae = SiteMgrNoteLogic.addNote(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }       
       else if(action.equals("editNote") ) {
   	     ActionErrors ae = SiteMgrNoteLogic.editNote(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }       
       else if(action.equals("Save Note") ) {
   	     ActionErrors ae = SiteMgrNoteLogic.saveNote(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if(action.equals("Delete Notes") ) {
   	     ActionErrors ae = SiteMgrNoteLogic.delNotes(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if(action.equals("readNote") ) {
   	     ActionErrors ae = SiteMgrNoteLogic.readNote(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if(action.equals("Search") ) {
   	     ActionErrors ae = SiteMgrNoteLogic.searchNotes(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if(action.equals("View All") ) {
   	     ActionErrors ae = SiteMgrNoteLogic.allNotes(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if(action.equals("Delete Selected") ) {
   	     ActionErrors ae = SiteMgrNoteLogic.delParagraphs(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
   	     ae = SiteMgrNoteLogic.delSelectedAttachments(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
	   }
       else if (action.equals("sortNotes")) {
   	     ActionErrors ae = SiteMgrNoteLogic.sortNotes(request,sForm);
         if (ae.size() > 0) {
           sForm.setErrorFl(true);
           saveErrors(request, ae);
           return (mapping.findForward("failure"));
         }
      }
       else if (action.equals("attachment")) {
   	     ActionErrors ae = SiteMgrNoteLogic.downloadAttachment(request,response,sForm);
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

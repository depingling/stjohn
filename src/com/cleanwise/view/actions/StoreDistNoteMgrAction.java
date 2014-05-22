
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.StoreDistNoteMgrLogic;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;


public final class StoreDistNoteMgrAction extends ActionSuper {


public ActionForward performSub(
         ActionMapping mapping,
         ActionForm form,
         HttpServletRequest request,
         HttpServletResponse response)
  throws IOException, ServletException {

  String action  = request.getParameter("action");

  // Is there a currently logged on user?
  SessionTool st = new SessionTool(request);
  if ( st.checkSession() == false ) {
    return mapping.findForward("/userportal/logon");
  }

  StoreDistNoteMgrForm sForm = (StoreDistNoteMgrForm) form;
  sForm.setErrorFl(false);
  if(action==null) { action = "init"; }
  try {
    if(action.equals("init") ) {
      ActionErrors ae = StoreDistNoteMgrLogic.init(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
    }
    else if(action.equals("selectTopic") ) {
      ActionErrors ae = StoreDistNoteMgrLogic.moveToSelectTopic(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
    }
    else if(action.equals("loadTopic") ) {
      ActionErrors ae = StoreDistNoteMgrLogic.loadTopic(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
    }
    else if(action.equals("Add Note") ) {
      ActionErrors ae = StoreDistNoteMgrLogic.addNote(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
    }
    else if(action.equals("editNote") ) {
      ActionErrors ae = StoreDistNoteMgrLogic.editNote(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
    }
    else if(action.equals("Save Note") ) {
      ActionErrors ae = StoreDistNoteMgrLogic.saveNote(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
    }
    else if(action.equals("Delete Notes") ) {
      ActionErrors ae = StoreDistNoteMgrLogic.delNotes(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
    }
    else if(action.equals("readNote") ) {
      ActionErrors ae = StoreDistNoteMgrLogic.readNote(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
    }
    else if(action.equals("Search") ) {
      ActionErrors ae = StoreDistNoteMgrLogic.searchNotes(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
    }
    else if(action.equals("View All") ) {
      ActionErrors ae = StoreDistNoteMgrLogic.allNotes(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
    }
    else if(action.equals("Delete Selected") ) {
      ActionErrors ae = StoreDistNoteMgrLogic.delParagraphs(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
       ae = StoreDistNoteMgrLogic.delSelectedAttachments(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
    }
    else if (action.equals("sortNotes")) {
      ActionErrors ae = StoreDistNoteMgrLogic.sortNotes(request,sForm);
      if (ae.size() > 0) {
        sForm.setErrorFl(true);
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
      }
    }
    else if (action.equals("attachment")) {
      ActionErrors ae = StoreDistNoteMgrLogic.downloadAttachment(request,response,sForm);
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
  }
  return (mapping.findForward("success"));
}

}

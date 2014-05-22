
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

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.Constants;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.forms.EditOrderGuideForm;
import com.cleanwise.view.logic.EditOrderGuideLogic;
import java.util.Iterator;
import java.util.Enumeration;
import com.cleanwise.view.utils.*;


/**
 * Implementation of <strong>Action</strong> that processes the
 * user shopping functions.
 *
 * @author durval
 */
public final class EditOrderGuideAction extends ActionSuper
{

  // ---------------------------------------------------- Public Methods


  /**
   * Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or <code>null</code> if the response has
   * already been completed.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param form an <code>ActionForm</code> value
   * @param request The HTTP request we are processing
   * @param response The HTTP response we are creating
   *
   * @return an <code>ActionForward</code> value
   * @exception IOException if an input/output error occurs
   * @exception ServletException if a servlet exception occurs
   */
  public ActionForward performSub(
			       ActionMapping mapping,
			       ActionForm form,
			       HttpServletRequest request,
			       HttpServletResponse response)
    throws IOException, ServletException
  {

    String action = request.getParameter("action");
    String command = request.getParameter("command");
    EditOrderGuideForm theForm = (EditOrderGuideForm) form;
    HttpSession session = request.getSession();

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }   


    if (action==null || action.trim().length()==0) {
	action = "editOrderGuideInit";
    }

    try {
      if ( action.equals("editOrderGuideInit") ) {
        ActionErrors ae = EditOrderGuideLogic.init(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return mapping.findForward("userHome");
        }
        return mapping.findForward("display");
	  }
      else if (action.equals("editOrderGuideSubmit")) {
        if("select".equals(command) ||
           request.getParameter("select.x")!=null) {
          ActionErrors ae = EditOrderGuideLogic.select(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if("addFromCart".equals(command) ||
                request.getParameter("addFromCart.x")!=null) {
          ActionErrors ae = EditOrderGuideLogic.addFromCart(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if("save".equals(command) ||
                request.getParameter("save.x")!=null) {
          ActionErrors ae = EditOrderGuideLogic.save(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if("findItemsToAdd".equals(command) ||
                request.getParameter("findItemsToAdd.x")!=null) {
          ActionErrors ae = EditOrderGuideLogic.findItemsToAdd(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if("addSelectedItems".equals(command) ||
                request.getParameter("addSelectedItems.x")!=null) {
          ActionErrors ae = EditOrderGuideLogic.addItemsSelected(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          } else {
	      // refresh the order guide information.
	      ae = EditOrderGuideLogic.select(request,theForm);
	      if(ae.size()>0) {
		  saveErrors(request,ae);
	      }
	  }
        }
        else if("sort".equals(command) ||
                request.getParameter("sort.x")!=null) {
          ActionErrors ae = EditOrderGuideLogic.sort(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if("removeSelected".equals(command) ||
                request.getParameter("removeSelected.x")!=null) {
          ActionErrors ae = EditOrderGuideLogic.removeSelected(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if("recalc".equals(command) ||
                request.getParameter("recalc.x")!=null) {
          ActionErrors ae = EditOrderGuideLogic.recalc(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if("restoreQuant".equals(command) ||
                request.getParameter("restoreQuant.x")!=null) {
          ActionErrors ae = EditOrderGuideLogic.restoreQuant(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if("merge".equals(command) ||
                request.getParameter("merge.x")!=null) {
          ActionErrors ae = EditOrderGuideLogic.merge(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if("removeAll".equals(command) ||
                request.getParameter("removeAll.x")!=null) {
          ActionErrors ae = EditOrderGuideLogic.removeAll(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if("deleteOrderGuide".equals(command) ||
                request.getParameter("deleteOrderGuide.x")!=null) {
          ActionErrors ae = EditOrderGuideLogic.deleteOrderGuide(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else {
          ActionErrors ae = EditOrderGuideLogic.select(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        return mapping.findForward("display");
	  }
      else if (action.equals("editOrderGuideUndoRemove")) {
        ActionErrors ae = EditOrderGuideLogic.undoRemove(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
        return mapping.findForward("display");
      }
      else{
        ActionErrors ae = new ActionErrors();
        ae.add("error",new ActionError("error.systemError","Unknown action: ["+Utility.encodeForHTML(action)+"]"));
        saveErrors(request,ae);
        return mapping.findForward("display");
	  }
    }
    catch ( Exception e ) {
      e.printStackTrace();
      request.setAttribute(Constants.EXCEPTION_OBJECT, e);
      return mapping.findForward("error");
    }
  }


}

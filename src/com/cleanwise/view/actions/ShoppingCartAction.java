
package com.cleanwise.view.actions;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

import java.io.IOException;
import com.cleanwise.view.i18n.ClwI18nUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.Constants;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.logic.ShoppingCartLogic;
import com.cleanwise.view.utils.*;


/**
 * Implementation of <strong>Action</strong> that processes the
 * user shopping functions.
 *
 * @author durval
 */
public final class ShoppingCartAction extends ActionSuper
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

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }

    HttpSession session = request.getSession();
    ShoppingCartForm theForm = (ShoppingCartForm) form;
    String newRequestId = null;
    theForm.setConfirmMessage(null);

    if (action==null || action.trim().length()==0) action = "init";

    //String buttonOrderNotAffectBudgetStr = getMessage(getResources(request),request,"shop.budgetInfo.label.setNotBudgetedOrder");
    //String buttonOrderDoesAffectBudgetStr = getMessage(getResources(request),request,"shop.budgetInfo.label.setBudgetedOrder");


    try {
        if ("shop.budgetInfo.label.setNotBudgetedOrder".equals(command) || action.equals("shop.budgetInfo.label.setNotBudgetedOrder")){
         ActionErrors ae = ShoppingCartLogic.setOrderBudgetTypeCd(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE,request,theForm);
         if(ae.size()>0) {
           saveErrors(request,ae);
         }
         return mapping.findForward("display");
       }else if ("shop.budgetInfo.label.setBudgetedOrder".equals(command) || action.equals("shop.budgetInfo.label.setBudgetedOrder")){
         ActionErrors ae = ShoppingCartLogic.setOrderBudgetTypeCd(null,request,theForm);
         if(ae.size()>0) {
           saveErrors(request,ae);
         }
         return mapping.findForward("display");
       }
      if ( action.equals("init") ) {

        ActionErrors ae = ShoppingCartLogic.init(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
         // return mapping.findForward("userHome");

          //return mapping.findForward("/userportal/logon");
        }
        return mapping.findForward("display");
      }
      else if (action.equals("submitQty")) {

        if(request.getParameter("recalc.x")!=null) {
          ActionErrors ae = ShoppingCartLogic.recalc(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if(request.getParameter("removeSelected.x")!=null) {
          ActionErrors ae = ShoppingCartLogic.removeSelected(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if(request.getParameter("restoreQuant.x")!=null) {
          ActionErrors ae = ShoppingCartLogic.restoreQuant(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if(request.getParameter("merge.x")!=null) {
          ActionErrors ae = ShoppingCartLogic.merge(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if(request.getParameter("removeAll.x")!=null) {
          ActionErrors ae = ShoppingCartLogic.removeAll(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }
        else if(request.getParameter("saveOrderGuide.x")!=null) {
          ActionErrors ae = ShoppingCartLogic.saveOrderGuide(request,theForm);
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
        }

        else {
          ActionErrors ae = new ActionErrors();
          ae.add("error",new ActionError("error.systemError","Unknown submit command"));
          saveErrors(request,ae);
        }
        return mapping.findForward("display");
  	  }

      else if(action.equals("saveOrderGuide")) {
        ActionErrors ae = ShoppingCartLogic.saveOrderGuide(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
        request.setAttribute("gotoAnchor","true");
        return mapping.findForward("display");
      }
      else if(action.equals("updateOrderGuide")) {
        ActionErrors ae = ShoppingCartLogic.updateOrderGuide(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
        request.setAttribute("gotoAnchor","true");
        return mapping.findForward("display");
        //return mapping.findForward("viewButtonSection");
      }

      else if(action.equals("recalc")) {
        ActionErrors ae = ShoppingCartLogic.recalc(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
        return mapping.findForward("display");
      }
      else if(action.equals("removeSelected")) {
        ActionErrors ae = ShoppingCartLogic.removeSelected(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
        request.setAttribute("gotoAnchor","true");
        return mapping.findForward("display");
        //return mapping.findForward("viewButtonSection");
      }
      else if(action.equals("updateCart")) {
        ActionErrors ae = ShoppingCartLogic.recalc(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }

        /*long[] toDelete = theForm.getSelectBox();
        if (toDelete != null && (toDelete.length > 0)) {
          ae = ShoppingCartLogic.removeSelected(request, theForm);
        }
        if(ae.size()>0) {
          //this.addErrors(request,ae);
          saveErrors(request,ae);
        }*/
        //return mapping.findForward("viewButtonSection");
        request.setAttribute("gotoAnchor","true");
        return mapping.findForward("display");
      }

      else if(action.equals("removeAll")) {
        ActionErrors ae = ShoppingCartLogic.removeAll(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
        return mapping.findForward("display");
      }
      else if(action.equals("restoreQuant")) {
        ActionErrors ae = ShoppingCartLogic.restoreQuant(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
        return mapping.findForward("display");
      }

      else if (action.equals("sort")) {
        ActionErrors ae = ShoppingCartLogic.sort(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
        return mapping.findForward("display");
      }
      else if (action.equals("undoRemove")) {
        ActionErrors ae = ShoppingCartLogic.undoRemove(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
        return mapping.findForward("display");
      }else{
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

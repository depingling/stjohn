
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
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.forms.OrderSchedulerForm;
import com.cleanwise.view.logic.OrderSchedulerLogic;
import java.util.Iterator;
import java.util.Enumeration;
import com.cleanwise.view.utils.*;


/**
 * Implementation of <strong>Action</strong> that processes the
 * user shopping functions.
 *
 * @author durval
 */
public final class OrderSchedulerAction extends ActionSuper
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

      String action = request.getParameter("action"),
	  command = request.getParameter("command"),
	  scrollFwd = request.getParameter("calendarForward.x"),
	  scrollBwd = request.getParameter("calendarBackward.x")
	  ;

      if ( scrollFwd != null && scrollFwd.length() > 0 ) {
	  command = "calendarForward";
      }
      if ( scrollBwd != null && scrollBwd.length() > 0 ) {
	  command = "calendarBackward";
      }

      OrderSchedulerForm theForm = (OrderSchedulerForm) form;
      HttpSession session = request.getSession();

      // Is there a currently logged on user?
      SessionTool st = new SessionTool(request);
      if ( st.checkSession() == false ) {
	  return mapping.findForward("/userportal/logon");
      }   

      logm("1 action=" + action + " command=" + command );
      String scheduleTypeChange = request.getParameter("scheduleTypeChange");
      if(scheduleTypeChange!=null && scheduleTypeChange.trim().length()>0) {
	  return (mapping.findForward("display"));
      }

      if (action==null || action.trim().length()==0) {
	  action = "orderSchedulerInit"; 
      }

      try {
	  if ( action.equals("orderSchedulerInit") ) {
	      ActionErrors ae = OrderSchedulerLogic.init(request,theForm, true);
	      if(ae.size()>0) {
		  saveErrors(request,ae);
		  return mapping.findForward("userHome");
	      }
	      return mapping.findForward("display");
	  }
	  else if("calendarForward".equals(command)){
	      ActionErrors ae = OrderSchedulerLogic.showCalDates(request,theForm, "forward");
	      if(ae.size()>0) {
		  saveErrors(request,ae);
	      }
	  }
	  else if("calendarBackward".equals(command) ) {
	      ActionErrors ae = OrderSchedulerLogic.showCalDates(request,theForm, "backward");
	      if(ae.size()>0) {
		  saveErrors(request,ae);
	      }
	  }
	  else if("saveSchedule".equals(command) ||
		  request.getParameter("saveSchedule.x")!=null) {
	      ActionErrors ae = OrderSchedulerLogic.saveSchedule(request,theForm, true);
	      if(ae.size()>0) {
		  saveErrors(request,ae);
	      }
	  }
	  else if("deleteSchedule".equals(command) ||
		  request.getParameter("deleteSchedule.x")!=null) {
	      ActionErrors ae = OrderSchedulerLogic.deleteSchedule(request,theForm);
	      if(ae.size()>0) {
		  saveErrors(request,ae);
	      }
	  }
	  else if (action.equals("orderSchedulerSubmit") &&
		  request.getParameter("orderScheduleId")!=null) {
	      ActionErrors ae = OrderSchedulerLogic.prepareSchedule(request,theForm);
	      if(ae.size()>0) {
		  saveErrors(request,ae);
	      }
	  }
	  else if (action.equals("orderSchedulerSubmit")) {
	      ActionErrors ae = OrderSchedulerLogic.select(request,theForm);
	      if(ae.size()>0) {
		  saveErrors(request,ae);
	      }
	  }
	  else {
	      ActionErrors ae = OrderSchedulerLogic.prepareSchedule(request,theForm);
	      if(ae.size()>0) {
		  saveErrors(request,ae);
	      }
	  }
	  return mapping.findForward("display");
	      
      }
      catch ( Exception e ) {
	  logm("101 action=" + action );
	  e.printStackTrace();
	  request.setAttribute(Constants.EXCEPTION_OBJECT, e);
	  return mapping.findForward("error");
      }
      finally {
	  logm("100 action=" + action );
      }
  }


}

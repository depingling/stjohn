
package com.cleanwise.view.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.AutoOrderMgrForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.RefCodeNames;
import java.rmi.RemoteException;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * <code>AutoOrderMgrLogic</code> implements the logic needed to
 * place orders scheduled for automatic processing.
 *
 *@author Yuriy Kupershmidt
 */
public class AutoOrderMgrLogic {

    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors init(HttpServletRequest request,
			    AutoOrderMgrForm pForm)
    {
    ActionErrors ae = new ActionErrors();
  	return ae;
    }
   //***********************************************************************
    public static ActionErrors runBatch(HttpServletRequest request,
			    AutoOrderMgrForm pForm)
    {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    AutoOrder autoOrderEjb = null;
    try {
      autoOrderEjb = factory.getAutoOrderAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No auto order Ejb pointer"));
      return ae;
    }
    GregorianCalendar batchDateGC = Constants.createCalendar(pForm.getBatchDate());
    if(batchDateGC==null) {
      ae.add("error",new ActionError("error.simpleGenericError","Wrong batch date format: "+pForm.getBatchDate()));
      return ae;
    }
    Date batchDate = batchDateGC.getTime();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
      ae.add("error",new ActionError("error.systemError","No session user information found"));
      return ae;
    }
    IdVector scheduleIds = null;
    try {
      scheduleIds = autoOrderEjb.getOrderSchedules(batchDate);
    } catch(RemoteException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    for(int ii=0; ii<scheduleIds.size(); ii++) {
      Integer scheduleIdI = (Integer) scheduleIds.get(ii);
      int scheduleId = scheduleIdI.intValue();
      try {
        autoOrderEjb.placeAutoOrder(scheduleId, batchDate, appUser.getUser().getUserName());
      } catch(RemoteException exc) {
        exc.printStackTrace();
        ae.add("error",new ActionError("error.systemError",exc.getMessage()));
        return ae;
      }
    }
    OrderBatchLogDataVector orderBatchLogDV = null;
    try {
      orderBatchLogDV = autoOrderEjb.getOrderBatchLog(batchDate);
    } catch(RemoteException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    pForm.setOrderBatchLog(orderBatchLogDV);
  	return ae;
    }

}






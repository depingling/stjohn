
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
import com.cleanwise.view.forms.SpendingEstimatorForm;
import com.cleanwise.view.logic.SpendingEstimatorLogic;
import java.util.Iterator;
import java.util.Enumeration;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.RefCodeNames;

/**
 * Implementation of <strong>Action</strong> that processes the
 * user shopping functions.
 *
 * @author durval
 */
public final class SpendingEstimatorAction extends ActionSuper {
    
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
    throws IOException, ServletException {
        
        String mappingAddress = "success";
        try {
            
            // Is there a currently logged on user?
            SessionTool st = new SessionTool(request);
            if ( st.checkSession() == false ) {
                return mapping.findForward("/userportal/logon");
            }
            
            String action = (String) request.getParameter("action");
            SpendingEstimatorForm theForm = (SpendingEstimatorForm) form;
            if (action==null || action.trim().length()==0) action = "init";
            request.setAttribute("action", action);
            
            SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
            String selectedPageS = request.getParameter("selectedPage");
            if(selectedPageS!=null) {
              try {
                int selectedPage = Integer.parseInt(selectedPageS);
                pForm.setSelectedPage(selectedPage);
              } catch(Exception exc){pForm.setSelectedPage(0);}
            }
            
            if ( action.equals("init") ) {
                SpendingEstimatorLogic.init(request,theForm);
            }
            else if (action.equals("New Model")) {
              pForm.setSelectedPage(7);
            }
            else if (action.equals("initNewModel")) {
              ActionErrors ae = SpendingEstimatorLogic.initNewModel(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }            
            else if (action.equals("loadModel")) {
              ActionErrors ae = SpendingEstimatorLogic.loadModel(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }
            else if (action.equals("delModel")) {
              ActionErrors ae = SpendingEstimatorLogic.delModel(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }            
            else if (action.equals("copyModel")) {
              ActionErrors ae = SpendingEstimatorLogic.copyModel(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }
            else if (action.equals("Profile")) {
              ActionErrors ae = SpendingEstimatorLogic.prepareProfilePage(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }            
            else if (action.equals("Save Profile")) {
              ActionErrors ae = SpendingEstimatorLogic.saveProfile(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }  
            else if (action.equals("Create Model")) {
              ActionErrors ae = SpendingEstimatorLogic.createModelFromTemplate(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }  
            else if (action.equals("PaperPlus")) {
              ActionErrors ae = SpendingEstimatorLogic.loadPaperPlus(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }
            else if (action.equals("Save Assumptions")) {
              ActionErrors ae = SpendingEstimatorLogic.saveAssumptions(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }
            else if (action.equals("Save Products")) {
              ActionErrors ae = SpendingEstimatorLogic.saveProducts(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }
            else if (action.equals("Save Paper, Soap, Liner Products")) {
              ActionErrors ae = SpendingEstimatorLogic.savePaperPlusProducts(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }
            else if (action.equals("Modify Products")) {
              ActionErrors ae = SpendingEstimatorLogic.modifyProducts(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }
            else if (action.equals("ProductFilter")) {
              ActionErrors ae = SpendingEstimatorLogic.prepareProductFilter(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }
            /*
            else if (action.equals("Calculate Results")) {
              ActionErrors ae = SpendingEstimatorLogic.calculateResults(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            } 
             */           
            else if (action.equals("Modify Assumptions")) {
              ActionErrors ae = SpendingEstimatorLogic.modifyAssumptions(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }            
            else if (action.equals("AllocatedCategory")) {
              ActionErrors ae = SpendingEstimatorLogic.loadAllocatedCategories(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }
            else if (action.equals("Save Allocation Percent")) {
              ActionErrors ae = SpendingEstimatorLogic.saveAllocationPercent(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }
            else if (action.equals("Modify Allocation Percent")) {
              ActionErrors ae = SpendingEstimatorLogic.modifyAllocationPercent(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }   
            
            else if (action.equals("reports")) {
              ActionErrors ae = SpendingEstimatorLogic.initReports(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }   
            
            else if (action.equals("reportRequest")) {
              ActionErrors ae = SpendingEstimatorLogic.reportRequest(request,theForm);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }   
            
            else if (action.equals("Run Report")) {
              ActionErrors ae = SpendingEstimatorLogic.runReport(request,response, theForm);
              if(ae.size()>0) {
                makeMessageString(request,ae);
              }
            }   
            else if (action.equals("Management")) {
              mappingAddress = "management";
            }
        }
        catch ( Exception e ) {
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            e.printStackTrace();
            mappingAddress = "error";
        }
        
        
        return mapping.findForward(mappingAddress);
    }

    String makeMessageString(HttpServletRequest request, ActionErrors ae) {
      org.apache.struts.util.MessageResources mr = getResources(request);
      String errorMessage = "";
      java.util.Iterator iter = ae.get();
      while (iter.hasNext()) {
        ActionError err = (ActionError) iter.next();
        String mess = getMessage(mr,request,err.getKey(),err.getValues());
        if(errorMessage.length()>0) errorMessage += "   "; //Character.LINE_SEPARATOR;        
        errorMessage += mess;
      }
      request.setAttribute("errorMessage", errorMessage);
      return errorMessage;
    }
    
}

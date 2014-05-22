
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
import com.cleanwise.view.forms.EstimatorMgrForm;
import com.cleanwise.view.logic.EstimatorMgrLogic;
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
public final class EstimatorMgrAction extends ActionSuper {
    
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
            EstimatorMgrForm theForm = (EstimatorMgrForm) form;
            String itemIdAddS = request.getParameter("itemIdAdd");
            String scheduleTypeFilter = request.getParameter("scheduleTypeFilter");
            String cleaningProcIdS = request.getParameter("cleanigProcSelected");
            int itemIdAdd = 0;
            if(itemIdAddS!=null) {
              try {
                itemIdAdd = Integer.parseInt(itemIdAddS);
              } catch(Exception exc) {}
            }
            if (action==null || action.trim().length()==0) {
              if(itemIdAdd!=0) {
                 action = "addItem";
              } else if(scheduleTypeFilter!=null) {
                 action = "loadSchedules";
              } else if(cleaningProcIdS!=null) {
                 action = "loadProcedureProducts";
              } else {
                 action = "init";
              }
            }
            request.setAttribute("action", action);
            
            String selectedPageS = request.getParameter("selectedPage");
            int selectedPage = 0;
            if(selectedPageS!=null) {
              try {
                selectedPage = Integer.parseInt(selectedPageS);                
                theForm.setSelectedPage(selectedPage);
              } catch(Exception exc){}
            } else {
              selectedPage = theForm.getSelectedPage();
              if(selectedPage==0) theForm.setSelectedPage(101);
            }
            
            if ( action.equals("init") ) {
                EstimatorMgrLogic.init(request,theForm);
            }
            else if (action.equals("Products")) {
              ActionErrors ae = EstimatorMgrLogic.initProducts(request,form);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }
            else if (action.equals("Search")) {
              ActionErrors ae = EstimatorMgrLogic.setProductFilter(request,form);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }            
            else if (action.equals("Save Products")) {
              ActionErrors ae = EstimatorMgrLogic.updateProducts(request,form);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }            
            else if (action.equals("addItem")) {
              ActionErrors ae = EstimatorMgrLogic.addItem(request,form);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }   
            else if (action.equals("Remove Selected")) {
              ActionErrors ae = EstimatorMgrLogic.removeItems(request,form);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }               
            else if (action.equals("Save")) {
              if(selectedPage==111) {
                ActionErrors ae = EstimatorMgrLogic.saveProcedureProducts(request,form);
                if(ae.size()>0) {
                  saveErrors(request,ae);
                  mappingAddress = "failure";
                }
              } else if(selectedPage==121) {
                ActionErrors ae = EstimatorMgrLogic.saveSchedules(request,form);
                if(ae.size()>0) {
                  saveErrors(request,ae);
                  mappingAddress = "failure";
                }
              }
            }               
            else if (action.equals("loadSchedules")) {
              ActionErrors ae = EstimatorMgrLogic.loadSchedules(request,form);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }   
            else if (action.equals("CleaningProcedures")) {
              ActionErrors ae = EstimatorMgrLogic.initCleaningProcedures(request,form);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }   
            else if (action.equals("loadProcedureProducts")) {
              ActionErrors ae = EstimatorMgrLogic.loadProcedureProducts(request,form);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }   
            else if (action.equals("Assign To Actions")) {
              ActionErrors ae = EstimatorMgrLogic.saveProductToBuffer(request,form);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }   
            else if (action.equals("ClearBuffer")) {
              ActionErrors ae = EstimatorMgrLogic.clearBuffer(request,form);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }               
            else if (action.equals("addProcProduct")) {
              ActionErrors ae = EstimatorMgrLogic.addProcProduct(request,form);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }               
            else if (action.equals("Delete Selected")) {
              ActionErrors ae = EstimatorMgrLogic.deleteProcProducts(request,form);
              if(ae.size()>0) {
                saveErrors(request,ae);
                mappingAddress = "failure";
              }
            }   
        }
        catch ( Exception e ) {
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            e.printStackTrace();
            mappingAddress = "error";
        }
        
        
        return mapping.findForward(mappingAddress);
    }
    
}

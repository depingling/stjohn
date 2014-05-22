/*
 * StoreBlanketPoNumAction.java
 *
 * Created on November 12, 2008, 15:30 PM
 */

package com.cleanwise.view.actions;

import com.cleanwise.view.forms.StoreBlanketPoNumSearchForm;
import com.cleanwise.view.logic.StoreBlanketPoNumLogic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
/**
 *
 * @author sergei v. cher (svc)
 */
public class StoreBlanketPoNumAction extends ActionBase{
   

    public ActionForward performAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	// Determine action to be performed
    	String action = request.getParameter("action");
        if(action == null){
            action = "init"; // I changed it to " action = "init" "; and applied some changes to the appropriate Logic class,
                             // initially it was " action = ""; "
        }
        
       	ActionErrors ae = null;
        String forward = "display";

        MessageResources mr = getResources(request);
        
        // Get the form buttons specified in the "properties" file
        //String viewAllStr = getMessage(mr,request,"admin.button.viewall"); //svc
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String createStr = getMessage(mr,request,"admin.button.create");
        
    	if (action.equals("init")) {
    		StoreBlanketPoNumLogic.init(request, form);
    	}
        if(searchStr.equals(action)){
            //ae = BlanketPoNumLogic.search(request, form); //svc
        	ae = StoreBlanketPoNumLogic.search(request, form);
        //}else if(viewAllStr.equals(action)){
            //ae = StoreBlanketPoNumLogic.viewAll(request, form); //svc       
        }else if(createStr.equals(action)){
            forward = "create";
        }
        if(ae!=null && !(ae.size() == 0)) {
            saveErrors(request,ae);
        }
        return (mapping.findForward(forward));
    }
    
}

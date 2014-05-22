/*
 * BlanketPoNumAction.java
 *
 * Created on February 9, 2005, 11:44 AM
 */

package com.cleanwise.view.actions;

import com.cleanwise.view.logic.BlanketPoNumLogic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
/**
 *
 * @author bstevens
 */
public class BlanketPoNumAction extends ActionBase{
   

    public ActionForward performAction(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        ActionErrors ae = null;
        String forward = "display";
        MessageResources mr = getResources(request);
        String viewAllStr = getMessage(mr,request,"admin.button.viewall");
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String createStr = getMessage(mr,request,"admin.button.create");
        if(searchStr.equals(action)){
            ae = BlanketPoNumLogic.search(request, form);
        }else if(viewAllStr.equals(action)){
            ae = BlanketPoNumLogic.viewAll(request, form);
        }else if(createStr.equals(action)){
            forward = "create";
        }
        if(ae!=null && !(ae.size() == 0)) {
            saveErrors(request,ae);
        }
        return (mapping.findForward(forward));
    }
    
}

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
public class BlanketPoNumDetailAction extends ActionBase{
   

    public ActionForward performAction(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        ActionErrors ae = null;
        String forward = "display";
        MessageResources mr = getResources(request);
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String createStr = "create";
        String detailStr = "detail";
        BlanketPoNumLogic.initConstants(request);
        if(createStr.equals(action)){
            ae = BlanketPoNumLogic.initNew(request, form);
        }else if(saveStr.equals(action)){
            ae = BlanketPoNumLogic.save(request, form);
        }else if(detailStr.equals(action)){
            ae = BlanketPoNumLogic.getDetail(request, form);
        }
        if(ae!=null && !(ae.size() == 0)) {
            saveErrors(request,ae);
        }
        return (mapping.findForward(forward));
    }
    
}

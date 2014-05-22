/*
 * StoreReasources.java
 *
 * Created on January 12, 2005, 4:50 PM
 */

package com.cleanwise.view.actions;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.logic.StoreMgrLogic;
/**
 *
 * @author bstevens
 */
public class StoreResourcesAction extends ActionBase{
    
    /**
     *Overidding classes should not worry about checking wheather the user is logged in or not and
     *should not catch any un-caught exception (Logic class problem).  They should however set the
     *failForward property if it should be anything other than <i>failure</i>.
     */
    public ActionForward performAction(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        // Determine the account manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }
        // Get the form buttons as specified in the properties file.
        MessageResources mr = getResources(request);
        String saveStr = getMessage(mr,request,"global.action.label.save");
        
        
        
        String mappingVal = "success";
        if (action.equals("init")) {
            StoreMgrLogic.initReasourceConfig(request, form, mr);
        }else if (action.equals(saveStr)) {
            StoreMgrLogic.saveReasourceConfig(request,form);
            StoreMgrLogic.initReasourceConfig(request, form, mr);
        }
        
        return (mapping.findForward(mappingVal));
    }
    
}

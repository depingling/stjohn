/*
 * StoreHomeAction.java
 *
 * Created on July 30, 2005, 7:04 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.cleanwise.view.actions;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.view.logic.LogOnLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.UserAcessTokenViewData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import java.text.MessageFormat;

/**
 *
 * @author bstevens
 */
public class StoreHomeAction extends ActionBase{

	private static final String MAPPING_STORE_HOME_GO_MANTA="gomantaadmin";
	private static final String MAPPING_STORE_HOME_GO_ORCA="goorcaadmin";
	private static final String MAPPING_STORE_CHANGE_STORE="changeStore";
	
	private static final String PROPERTY_ORCA_ENTRY_POINT="orca.entry.point";
	private static final String PROPERTY_MANTA_ENTRY_POINT="manta.entry.point";
	private static final String PROPERTY_ORCA_URL="orca.url";
	private static final String PROPERTY_MANTA_URL="manta.url";
	
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
        ActionMessages ae = null;
        String action = request.getParameter("action");
        String forward = "success";
        if(MAPPING_STORE_CHANGE_STORE.equals(action)){
            int storeId = 0;
            try{
                storeId = Integer.parseInt(request.getParameter("id"));
            }catch(Exception e){
                e.printStackTrace();
                ae = new ActionMessages();
                ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.badRequest2"));
            }
            if(ae == null){
                ae = LogOnLogic.switchUserStore(storeId,request);
            }
        }else if (MAPPING_STORE_HOME_GO_MANTA.equals(action) || MAPPING_STORE_HOME_GO_ORCA.equals(action)){
        	ae = new ActionMessages();
        	HttpSession session = request.getSession();
        	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        	APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        	UserAcessTokenViewData accessTok = LogOnLogic.createAccessToken(factory,user);
        	//get system property based off action
        	String sysPropertyEntry = (MAPPING_STORE_HOME_GO_MANTA.equals(action)) ? PROPERTY_MANTA_ENTRY_POINT :  PROPERTY_ORCA_ENTRY_POINT;
        	String sysPropertyHost = (MAPPING_STORE_HOME_GO_MANTA.equals(action)) ? PROPERTY_MANTA_URL :  PROPERTY_ORCA_URL;
        	String redirectEntry = System.getProperty(sysPropertyEntry);
        	String redirectUrl = System.getProperty(sysPropertyHost);
        	if(!Utility.isSet(redirectEntry)){
        		ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.simpleMessage","Configuration error.  System property "+sysPropertyEntry+" not set."));
        	}else if(!Utility.isSet(redirectUrl)){
        		ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.simpleMessage","Configuration error.  System property "+sysPropertyHost+" not set."));
        	}else{
        		
        		// http://orcadev01:8080/defman/j_spring_security_check?accessToken=1234
        		response.sendRedirect(MessageFormat.format(redirectEntry,redirectUrl,accessTok.getAccessToken()));
        		forward = null;  //set forward to null as we control the redirect
        	}
        }
        
        if(ae!=null && !(ae.size() == 0)) {
            saveErrors(request,ae);
        }
        return (mapping.findForward(forward));
    }
}

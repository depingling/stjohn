/**
 * 
 */
package com.cleanwise.view.actions;
import org.apache.struts.action.*;

import javax.servlet.http.*;
import com.cleanwise.view.logic.*;
/**
 * @author ssharma
 *
 */
public class InboundTranslatorSecureAction extends ActionBase {
    protected boolean getIsPrivatePage(){
        return false;
    }
    
    public ActionForward performAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	InboundTranslatorLogic.preformTranslation(request, response);
        return null;
    }

    protected boolean isRequiresConfidentialConnection(){
        return true;
    } 
}

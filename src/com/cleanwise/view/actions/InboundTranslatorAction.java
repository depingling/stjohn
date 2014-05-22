/*
 * InboundTranslator.java
 *
 * Created on June 30, 2004, 3:40 PM
 */

package com.cleanwise.view.actions;
import org.apache.struts.action.*;
import javax.servlet.http.*;
import com.cleanwise.view.logic.*;
/**
 *
 * @author  bstevens
 */
public class InboundTranslatorAction extends ActionBase {
    protected boolean getIsPrivatePage(){
        return false;
    }
    
    public ActionForward performAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        InboundTranslatorLogic.preformTranslation(request, response);
        return null;
    }
    
}

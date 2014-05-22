/*
 * OutboundTranslator.java
 *
 * Created on September 8, 2006
 */

package com.cleanwise.view.actions;

import org.apache.struts.action.*;
import javax.servlet.http.*;
import com.cleanwise.view.logic.*;

/**
 *
 * @author  vdenega
 */
public class OutboundTranslatorAction extends ActionBase {
  protected boolean getIsPrivatePage() {
    return false;
  }

  public ActionForward performAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
    Exception {
    OutboundTranslatorLogic.performTranslation(request, response);
    return null;
  }

}

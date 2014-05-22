package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class OrcaAccessTag extends TagSupport {

    public int doStartTag() throws JspException {
        if (pageContext == null || pageContext.getSession() == null) {
            return SKIP_BODY;
        }

        CleanwiseUser appUser = (CleanwiseUser) pageContext.getSession().getAttribute(Constants.APP_USER);
        if (appUser == null) {
            return SKIP_BODY;
        }

        SessionDataUtil sessionData = Utility.getSessionDataUtil(pageContext.getSession());
        if(sessionData.isRemoteAccess()){
            return EVAL_BODY_INCLUDE;
        }  else {
            return SKIP_BODY;
        }
    }

}

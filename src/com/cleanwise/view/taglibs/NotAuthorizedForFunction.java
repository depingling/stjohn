package com.cleanwise.view.taglibs;

import javax.servlet.jsp.JspException;

import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

public class NotAuthorizedForFunction extends AuthorizedForFunction {

	/**
	 * Checks if the currently logged in user is authorized for the provided
	 * function. If there is no user logged in the body will not be rendered.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	public int doStartTag() throws JspException {
		if (getName() == null || pageContext == null
				|| pageContext.getSession() == null) {
			return SKIP_BODY;
		}
		CleanwiseUser appUser = (CleanwiseUser) pageContext.getSession()
				.getAttribute(Constants.APP_USER);
		if (appUser == null) {
			return SKIP_BODY;
		}
		if (!appUser.isAuthorizedForFunction(getName())) {
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

}

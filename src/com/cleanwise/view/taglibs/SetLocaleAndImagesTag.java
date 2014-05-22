/**
 * Title:        SetLocaleAndImagesTag
 * Description:  This is the tag class to set the locale and the image path.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li
 */

package com.cleanwise.view.taglibs;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.action.Action;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.utils.Constants;

import org.apache.struts.Globals;

/**
 * Gets the Locale object from the request object and sets it in the
 * session object under the known name Action.LOCALE_KEY.
 * It also gets the images path from the properties file and saves it
 * in the session object under the known name Action.IMAGES_PATH.
 * This tag writes nothing to the JSP page.
 */
public final class SetLocaleAndImagesTag extends TagSupport {


  // ------------------------------------------------------------ Public Methods


  /**
   * Defer our processing until the end of this tag is encountered.
   *
   * @exception JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {

        return (SKIP_BODY);

  }


  /**
   * Gets the Locale object from the request object and
   * sets it in the session object using Action.LOCALE_KEY.
   *
   * @exception JspException if a JSP exception has occurred
   */
  public int doEndTag() throws JspException {

    // Get the user's locale from the http request.
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    Locale locale = request.getLocale();

    // Save the locale in the session under the name Action.LOCALE_KEY.
    // This makes it available to the struts engine.
    HttpSession session = pageContext.getSession();
    session.setAttribute(Globals.LOCALE_KEY, locale);

    // Acquire the resources object containing our messages
    MessageResources resources = (MessageResources) pageContext.getAttribute(Globals.MESSAGES_KEY, PageContext.APPLICATION_SCOPE);
    if (resources == null) {
    // The message resources for this package.
     MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.LocalStrings");
     throw new JspException(messages.getMessage("messageTag.resources", Globals.MESSAGES_KEY));
    }

    // Retrieve the message string we are looking for
    String key = new String("images.path");
    String message = resources.getMessage(locale, key);

    if (message == null) {
     MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.LocalStrings");
     throw new JspException(messages.getMessage("messageTag.message", key));
    }

    // Save the images path in the session under the key Constants.IMAGES_PATH
    // This makes it available to the struts engine.
    session.setAttribute(Constants.IMAGES_PATH, message);

    return (EVAL_PAGE);
  }


  /**
   * Release all allocated resources.
   */
  public void release() {

    super.release();

  }


}

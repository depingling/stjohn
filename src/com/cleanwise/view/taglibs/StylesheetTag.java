
package com.cleanwise.view.taglibs;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.util.MessageResources;


/**
 * Builds the HTML for a link to a stylesheet. The stylesheet used is
 * determined by the user's browser type.  Supported browser types are
 * MSIE (Microsoft), GECKO (Netscape 6), and MOZILLA (Netscape 4).
 *
 * The code first checks the user's session for Browser and Stylesheet
 * entries. If one or both are missing, it uses the request header
 * 'user-agent' value to define the user's browser type and stylesheet.
 * This information is stored in the user's session so that this
 * definition will not have to be done again.
 */
public final class StylesheetTag extends TagSupport {


  // -------------------------------------------------------- Instance Variables


  /**
   * The message resources for this package.
   */
  protected static MessageResources _messages =
     MessageResources.getMessageResources("com.cleanwise.view.taglibs.TagResources");


  // ------------------------------------------------------------ Public Methods


  /**
   * Builds the html for a stylesheet link.  It first looks for browser and
   * stylesheet data in the user's session. If not found, it determines the
   * browser type and stylesheet based on the request's 'user-agent' header,
   * then stores this data in the user's session.
   *
   * @exception JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {

    HttpSession session = pageContext.getSession();
    String browser = (String) session.getAttribute("Browser");
    String stylesheet = (String) session.getAttribute("Stylesheet");

    if (browser == null    || browser.equals("") ||
        stylesheet == null || stylesheet.equals("")) {

      HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
      String userAgent = request.getHeader("user-agent").toUpperCase();

      if (userAgent.indexOf("MSIE") >= 0) {
        browser = "MICROSOFT";
        stylesheet = "cleanwise.css";
      }

      else if (userAgent.indexOf("GECKO") >= 0) {
        browser = "NETSCAPE6";
        stylesheet = "cleanwise.css";
      }

      else if (userAgent.indexOf("MOZILLA") >= 0) {
        browser = "NETSCAPE4";
        stylesheet = "cleanwise.css";
      }

      else {
        browser = "MICROSOFT";
        stylesheet = "cleanwise.css";
      }

      session.setAttribute("Browser", browser);
      session.setAttribute("Stylesheet", stylesheet);

    }

    // Build the stylesheet output string.
    StringBuffer results = new StringBuffer("<link rel='stylesheet' href='");
    results.append(stylesheet);
    results.append("' type='text/css'>");

    // Print this string to the output writer.
    JspWriter writer = pageContext.getOut();
    try {
      writer.print(results.toString());
    } catch (IOException e) {
      throw new JspException(
          _messages.getMessage("taglibs.ioerror", e.toString()));
    }

    return (EVAL_BODY_INCLUDE);

  }


  /**
   * Release all allocated resources.
   */
  public void release() {

    super.release();

  }


}

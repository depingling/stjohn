/**
 *  Title: DebugTag Description: This is the tag class which provides debug
 *  information for a page. Purpose: Copyright: Copyright (c) 2001 Company:
 *  CleanWise, Inc.
 *
 *@author     durval
 */

package com.cleanwise.view.taglibs;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.action.Action;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.utils.Constants;

/**
 *  This tag provides debug information for a page.
 *
 *@author     durval
 *@created    September 12, 2001
 */
public final class DebugTag extends TagSupport {
	
    private String applicationInfoPage = "/adminportal/applicationInfo.jsp";

    /**
     *  Defer our checking until the end of this tag is encountered.
     *
     *@return                   an <code>int</code> value
     *@exception  JspException  if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        return (SKIP_BODY);
    }


    /**
     *  The <code>doEndTag</code> method performs the work needed to display
     *  request, session scoped, page scoped , and application scoped
     *  information.
     *
     *@return                   an <code>int</code> value
     *@exception  JspException  if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {

        JspWriter writer = pageContext.getOut();
        try {
            writer.print("<table frame=box border=1>");
            writer.print("<tr><td align=left><u>Debug Information</u>");
            writer.print("<br>");
            //include the application information.  This page is seperated out so it
            //can be updated with appropriate information at application build time.
            pageContext.include(applicationInfoPage);
            writer.print("<br><b>Request information</b>");
            writer.print(getReqInfo());
            writer.print("<br><b>Session scoped attributes</b>");
            writer.print(getAttr(javax.servlet.jsp.PageContext.SESSION_SCOPE));
            writer.print("<br><b>Page scoped attributes</b>");
            writer.print(getAttr(javax.servlet.jsp.PageContext.PAGE_SCOPE));
            writer.print("<br><b>Request scoped attributes</b>");
            writer.print(getAttr(javax.servlet.jsp.PageContext.REQUEST_SCOPE));
            writer.print("<br><b>Application scoped attributes</b>");
            writer.print(getAttr
                         (javax.servlet.jsp.PageContext.APPLICATION_SCOPE));
            writer.print("<br><b>System attributes</b>");
            writer.print(getSysProperties());
            writer.print("</td></tr></table>");
        }
        catch (Exception e) {

        }

        return (EVAL_PAGE);
    }

    private String getSysProperties() {
        java.util.Properties sysProps = System.getProperties();
	java.util.ArrayList l =
	    java.util.Collections.list(sysProps.propertyNames());
	java.util.Collections.sort(l);
	java.util.Enumeration en = java.util.Collections.enumeration(l);

        String rets = "<ul>";
        while (en.hasMoreElements()) {
            String s = (String) en.nextElement();
            rets += "<br><b>" + s + " =</b> " +
                    System.getProperty(s);
        }
        rets += "</ul>";
        return rets;
    }

    /**
     *  Gets the ReqInfo attribute of the DebugTag object
     *
     *@return    The ReqInfo value
     */
    private String getReqInfo() {
        javax.servlet.http.HttpServletRequest request =
                (javax.servlet.http.HttpServletRequest)
                pageContext.getRequest();

        String rets = "<ul>JSP Request Method: ";
        rets += request.getMethod();
        rets += "<br>Request URI: ";
        rets += request.getRequestURI();
        rets += "<br>Request Protocol: ";
        rets += request.getProtocol();
        rets += "<br>Servlet path: ";
        rets += request.getServletPath();
        rets += "<br>Path info: ";
        rets += request.getPathInfo();
        rets += "<br>Path translated: ";
        rets += request.getPathTranslated();
        rets += "<br>Query string: ";
        rets += request.getQueryString();
        rets += "<br>Content length: ";
        rets += request.getContentLength();
        rets += "<br>Content type: ";
        rets += request.getContentType();
        rets += "<br>Server name: ";
        rets += request.getServerName();
        rets += "<br>Server port: ";
        rets += request.getServerPort();
        rets += "<br>Remote address: ";
        rets += request.getRemoteAddr();
        rets += "<br>Remote host: ";
        rets += request.getRemoteHost();
        rets += "<br>Authorization scheme: ";
        rets += request.getAuthType();
        rets += "<br>Locale: ";
        rets += request.getLocale();
        rets += "<br>Browser used: ";
        rets += request.getHeader("User-Agent");
        rets += "</ul>";
        return rets;
    }


    /**
     *  Gets the Attr attribute of the DebugTag object
     *
     *@param  pScope  Description of Parameter
     *@return         The Attr value
     */
    private String getAttr(int pScope) {

	java.util.ArrayList l =
	    java.util.Collections.list
	    (pageContext.getAttributeNamesInScope(pScope));
	java.util.Collections.sort(l);
	java.util.Enumeration en = java.util.Collections.enumeration(l);

        String rets = "<ul>";
        while (en.hasMoreElements()) {
            String s = (String) en.nextElement();
            if ( null != s) {
                rets += "\n<br><b>" + s + " =</b> ";
                try {
                    rets += pageContext.getAttribute(s, pScope);
                } 
                catch (Exception e) {
                    rets += "Error: got exception: " +
                        e.getMessage();
                }
            }
        }
        rets += "</ul>";
        return rets;
    }
}



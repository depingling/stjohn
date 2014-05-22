package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.BreadCrumbNavigator;
import com.cleanwise.view.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 */
public class BreadCrumbBar extends TagSupport {

    private static String className = "BreadCrumbBar";

    private BreadCrumbNavigator navigator;

    public BreadCrumbNavigator getNavigator() {
        return navigator;
    }

    public void setNavigator(BreadCrumbNavigator navigator) {
        this.navigator = navigator;
    }


    public int doStartTag() throws JspException {

        try {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            BreadCrumbNavigator navigator = (BreadCrumbNavigator) request.getSession().getAttribute(Constants.BREAD_CRUMB_NAVIGATOR);
            if (navigator == null) {
                navigator = new BreadCrumbNavigator();
                request.getSession().setAttribute(Constants.BREAD_CRUMB_NAVIGATOR, navigator);
            }
            setNavigator(navigator);
        } catch (Exception e) {
            error(e.getMessage(), e);
        }

        return BodyTagSupport.EVAL_BODY_BUFFERED;

    }

    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();
        //log("doEndTag => begin");
        try {
            BreadCrumbNavigator navigator = getNavigator();

            out.print("<table" +
                    " border=\"0\"" +
                    " cellpadding=\"0\" cellspacing=\"0\" HEIGHT=\"30\" >");


            Iterator it = navigator.getContainer().getItems().iterator();
            BreadCrumbNavigator.BreadCrumbItem cursorItem = navigator.getItem(navigator.getCursor());
            while (it.hasNext()) {
                BreadCrumbNavigator.BreadCrumbItem item = (BreadCrumbNavigator.BreadCrumbItem) it.next();
                printItemsBar(out, item, cursorItem);
            }

            out.print("</table>");
        } catch (Exception e) {
            error(e.getMessage(), e);
        }
        //log("doEndTag => end");
        return EVAL_PAGE;
    }

    private void printItemsBar(JspWriter out, BreadCrumbNavigator.BreadCrumbItem item, BreadCrumbNavigator.BreadCrumbItem cursorItem) throws IOException {
        if (Utility.isSet(item.getName())
                && Utility.isSet(item.getMessage()) && Utility.isSet(item.getKey()) &&
                cursorItem != null) {
            out.print("<tr><td>");
            //log("printItemsBar => href: "+item.getHref());
            if (item.getKey().equals(cursorItem.getKey())) {
                out.print("<span style=\"color:#FFFF6A;\" class=\"breadcrumb\">"
                        + item.getMessage() + "</span>" +
                        (item.getChilds().size() > 0 ? "&nbsp;<span style=\"color:#FFFFFF;\" class=\"breadcrumb\">>></span>&nbsp;" : ""));
            } else if (Utility.isSet(item.getHref())) {
                out.print("<a style=\"color:#FFFFFF;\" class=\"breadcrumb\" href=\"" + item.getHref() + "\">"
                        + item.getMessage() + "</a>" +
                        (item.getChilds().size() > 0 ? "&nbsp;<span style=\"color:#FFFFFF;\" class=\"breadcrumb\">>></span>&nbsp;" : ""));
            } else {
                out.print("<span style=\"color:#FFFFFF;\" class=\"breadcrumb\">"
                        + item.getMessage() + "</span>" +
                        (item.getChilds().size() > 0 ? "&nbsp;<span style=\"color:#FFFFFF;\" class=\"breadcrumb\">>></span>&nbsp;" : ""));

            }
            out.print("</td>");
            if (!item.getChilds().isEmpty()) {
                Iterator it = item.getChilds().iterator();
                out.print("<td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
                while (it.hasNext()) {
                    out.print("<tr><td>");
                    printItemsBar(out, (BreadCrumbNavigator.BreadCrumbItem) it.next(), cursorItem);
                    out.print("</td></tr>");
                }
                out.print("</table></td>");

            }
            out.print("</tr>");
        }

    }


    public void release() {
        super.release();
    }


    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

    }
}

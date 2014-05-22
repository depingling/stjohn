package com.cleanwise.view.taglibs;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.cleanwise.service.api.util.*;
import com.cleanwise.view.i18n.*;
import com.cleanwise.view.utils.*;

/**
 */
public class ClwPagerIndexTag extends BodyTagSupport {

    private static final String className = "ClwPagerIndexTag";

    private static final String PAGE_URL = "PAGEURL";

    public static final String CLW = "CLW";
    public static final String NEW_XPEDX = "NEWXPEDX";

    private int index;
    private int maxItems;
    private int maxPages;
    private int mountedListSize;
    private String pageUrl;
    private String style;
  private int indexSize;
  public void setIndex(int index) {
        this.index = index;
    }

    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public void setMountedListSize(int mountedListSize) {
        this.mountedListSize = mountedListSize;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }


    public static String getClassName() {
        return className;
    }

    public int getIndex() {
        return index;
    }

    public int getMaxItems() {
        return maxItems;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public int getMountedListSize() {
        return mountedListSize;
    }

    public String getPageUrl() {
        return pageUrl;
    }


    public String getStyle() {
        return style;
    }

  public int getIndexSize() {
    return indexSize;
  }

  public void setStyle(String style) {
        this.style = style;
    }

  public void setIndexSize(int indexSize) {
    this.indexSize = indexSize;
  }

  public int doStartTag() throws JspException {

        ClwPagerTag ancestorTag = (ClwPagerTag) findAncestorWithClass(this, ClwPagerTag.class);
        if (ancestorTag == null) {
            String message = "A query without a ClwPagerItemTag attribute must be nested within a ClwPagerTag tag.";
            error(message, new Exception(message));
            return (SKIP_PAGE);
        }


        setIndex(Integer.parseInt(ancestorTag.getPageIndex()));
        setPageUrl(ancestorTag.getPageUrl());
        setMaxItems(ancestorTag.getPager().getMaxItems());
        setMaxPages(ancestorTag.getPager().getMaxPages());
        if (ancestorTag.getPager().getMountedList() != null) {
            setMountedListSize(ancestorTag.getPager().getMountedList().size());
        } else {
            setMountedListSize(0);
        }

        return BodyTagSupport.EVAL_BODY_BUFFERED;

    }


    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String style = getStyle();
        try {
            if (!Utility.isSet(style) || CLW.equalsIgnoreCase(style)) {
                printClwIndexStyle(out, request);
            } else if (NEW_XPEDX.equals(style)) {
                printNewXpedxIndexStyle(out,request);
            } else {
                throw new Exception("Unknown index style => " + style);
            }
        } catch (Exception e) {
            error(e.getMessage(), e);
            return SKIP_BODY;
        }

        return EVAL_PAGE;
    }

    private void printNewXpedxIndexStyle(JspWriter out, HttpServletRequest request) throws IOException {
        int maxPages = getMaxPages();
        int numPages = getMountedListSize() / getMaxItems() + (getMountedListSize() % getMaxItems() != 0 ? 1 : 0);
        int index = getIndex();
        int bPos = 0;
        int ePos = bPos + maxPages;

        int indexSize = getIndexSize();
        int kInd= numPages/indexSize;

        if ( kInd > 0) {
          bPos = (index < kInd*indexSize) ? (index/indexSize)*indexSize : (numPages -indexSize ) ;
          ePos = (index < kInd*indexSize) ? ((index/indexSize)+ 1)*indexSize : numPages  ;
        }
        if (numPages > 1 && index == -1) {
            out.print("<table class='pagerTable' cellpadding='0' cellspacing=6>");
            out.print("<tr>");

            out.print("<td class=pagerLink>");
            out.print("<a class=pagerLink href=\"" + getHref(0) + "\">");
            out.print("&nbsp;");
            out.print(ClwI18nUtil.getMessage(request, "global.label.pageView", null));
            out.print("&nbsp;");
            out.print("</a>");
            out.print("</td>");


            out.print("</tr>");
            out.print("</table>");
        }
        if (numPages > 1 && index > -1) {
            out.print("<table class='pagerTable' cellpadding='0' cellspacing=6>");
            out.print("<tr>");
            if (index > 0) {
                printNewXpedxArrow(out, request, "goToFirst.gif", 0);
                printNewXpedxArrow(out, request, "prevArrow.gif", index-1);
            }
            for (int i = bPos; i < ePos && i < numPages; i++) {
                if (index == i) {
                    out.print("<td class=pagerLinkSelected>");
                    out.print("&nbsp;");
                    out.print(i + 1);
                    out.print("&nbsp;");
                } else {
                    out.print("<td class=pagerLink>");
                    out.print("<a class=pagerLink href=\"" + getHref(i) + "\">");
                    out.print("&nbsp;");
                    out.print(i + 1);
                    out.print("&nbsp;");
                    out.print("</a>");
                }
                out.print("</td>");
            }
            if (index < numPages - 1) {
                printNewXpedxArrow(out, request, "nextArrow.gif", index+1);
                printNewXpedxArrow(out, request, "goToLast.gif", numPages-1);
            }
            out.print("<td class=pagerLink>");
            out.print("<a class=pagerLink href=\"" + getHref(-1) + "\">");
            out.print("&nbsp;");
            out.print(ClwI18nUtil.getMessage(request, "global.label.viewAll", null));
            out.print("&nbsp;");
            out.print("</a>");
            out.print("</td>");


            out.print("</tr>");
            out.print("</table>");
        }
    }

    private void printNewXpedxArrow(JspWriter out, HttpServletRequest request, String imgName, int index)  throws IOException {
        String href = getHref(index);
        String src = ClwCustomizer.getSIP(request,imgName);
        out.print("<td>");
        out.print("<a href='" + href + "'>");
        out.print("<img width=20 height=17 alt='' border=0 src='" + src + "'>");
        out.print("</a>");
        out.print("</td>");
    }


    private void printClwIndexStyle(JspWriter out, HttpServletRequest request) throws IOException {

        int maxPages = getMaxPages();
        int numPages = getMountedListSize() / getMaxItems() + (getMountedListSize() % getMaxItems() != 0 ? 1 : 0);
        int index = getIndex();
        int bPos = 0;
        int ePos = bPos + maxPages;

        out.print("<table>");
        out.print("<tr>");

        out.print("<td>");
        if (getIndex() > 0) {

            out.print("<a href=\"" + getHref(getIndex() - 1) + "\">");
            out.print("<< " + ClwI18nUtil.getMessage(request, "global.action.label.previous", null));
            out.print("</a>");

        }
        out.print("</td>");

        out.print("<td>");
        for (int i = bPos; i < ePos && i < numPages; i++) {

            out.print("<a href=\"" + getHref(i) + "\">");
            out.print("&nbsp;");

            out.print(i + 1);

            out.print("&nbsp;");
            out.print("</a>");

        }
        out.print("</td>");

        out.print("<td>");
        if (getIndex() + 1 < numPages) {

            out.print("<a href=\"" + getHref(getIndex() + 1) + "\">");
            out.print(ClwI18nUtil.getMessage(request, "global.action.label.next", null) + ">>");
            out.print("</a>");

        }
        out.print("</td>");

        out.print("</tr>");
        out.print("</table>");

    }

    private String getHref(int i) {
        String pageUrl = Utility.strNN(getPageUrl());
        String index = (i == -1) ? "all" : String.valueOf(i);
        return pageUrl + (pageUrl.indexOf("?") > 0 ? "&" : "?") + "pageIndex=" + index;
    }

    public void release() {

        this.index = 0;
        this.maxItems = 0;
        this.maxPages = 0;
        this.mountedListSize = 0;
        this.pageUrl = "";
        this.style = "";

    }

    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {

        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);

    }

}


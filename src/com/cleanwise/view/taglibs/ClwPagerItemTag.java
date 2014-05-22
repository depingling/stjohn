package com.cleanwise.view.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;

/**
 */
public class ClwPagerItemTag extends BodyTagSupport {

    private static final String className = "ClwPagerItemTag";

    protected Iterator iterator;
    protected String id;
    protected String type;

    public Iterator getIterator() {
        return iterator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIterator(Iterator iterator) {
        this.iterator = iterator;
    }

    public int doStartTag() throws JspException {

        ClwPagerTag ancestorTag = (ClwPagerTag) findAncestorWithClass(this, ClwPagerTag.class);
        if (ancestorTag == null) {
            String message = "A query without a ClwPagerItemTag attribute must be nested within a ClwPagerTag tag.";
            error(message, new Exception(message));
            return (SKIP_PAGE);
        }

        setIterator(ancestorTag.getIterator());

        Iterator it = getIterator();

        if (it!=null && it.hasNext()) {
            Object element = it.next();
            pageContext.setAttribute(getId(), element);
            return (EVAL_BODY_AGAIN);
        } else {
            return (SKIP_BODY);
        }
    }


    public int doAfterBody() throws JspException {

        BodyContent bodyContent = getBodyContent();
        if (bodyContent != null) {
            try {
                JspWriter out = getPreviousOut();
                out.print(bodyContent.getString());
                bodyContent.clearBody();
            } catch (IOException e) {
                error(e.getMessage(), e);
                return (SKIP_BODY);
            }
        }

        Iterator it = getIterator();
        if (it!=null && it.hasNext()) {
            Object element = it.next();
            pageContext.setAttribute(getId(), element);
            return (EVAL_BODY_AGAIN);
        } else {
            return (SKIP_BODY);
        }
    }

    public void release() {

        this.iterator = null;
        this.id = null;
        this.type = null;

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

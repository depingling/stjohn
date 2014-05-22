package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.Pager;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;

/**
 */
public class ClwPagerTag extends BodyTagSupport {

    private static final String className = "ClwPagerTag";

    protected Iterator iterator = null;
    protected Object collection = null;
    protected String name = null;
    protected String property = null;
    protected String pageIndex = null;
    protected Pager pager = null;
    private String pageUrl = null;

    public String getPageUrl() {
        return pageUrl;
    }


    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public Iterator getIterator() {
        return iterator;
    }

    public void setIterator(Iterator iterator) {
        this.iterator = iterator;
    }

    public Object getCollection() {
        return collection;
    }

    public void setCollection(Object collection) {
        this.collection = collection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Pager getPager() {
        return this.pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int doStartTag() throws JspException {
        Object pager;

        Object bean = pageContext.findAttribute(Utility.strNN(getName()));
        if (bean == null) {
            String message = "Bean not found";
            error(message, new Exception(message));
            return SKIP_BODY;
        }
        String property = getProperty();
        if (property == null) {
            pager = bean;
        } else {
            try {
                pager = PropertyUtils.getProperty(bean, property);
            } catch (Exception e) {
                error(e.getMessage(), e);
                return SKIP_BODY;
            }
        }

        if (pager == null) {
            String message = "Bean not found";
            error(message, new Exception(message));
            return SKIP_BODY;
        }


        if (!(pager instanceof Pager)) {
            String message = "Class " + pager.getClass() + " must implement Pager interface";
            error(message, new Exception(message));
            return SKIP_BODY;
        }
        setPager((Pager) pager);
        int index;
        try {
            index = Integer.parseInt(getPageIndex());
            setCollection(((Pager) pager).getPageItemList(index));
        } catch (Exception e) {
            error(e.getMessage(), e);
            setCollection(null);
        }

        Object collection = getCollection();

        if (collection instanceof Collection) {
            Iterator it = ((Collection) collection).iterator();
            setIterator(it);
        } else if (collection instanceof Iterator) {
            Iterator it = (Iterator) collection;
            setIterator(it);
        }

        return BodyTagSupport.EVAL_BODY_BUFFERED;

    }


    public int doEndTag() throws JspException {

        JspWriter out = pageContext.getOut();
        try {
            out.print(bodyContent.getString());
        } catch (Exception e) {
            error(e.getMessage(), e);
            return SKIP_BODY;
        }

        return EVAL_PAGE;
    }


    public void release() {
        this.iterator = null;
        this.collection = null;
        this.name = null;
        this.pager = null;
        this.property = null;
        this.pageIndex = null;
        this.pageUrl = null;
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




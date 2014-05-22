package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.UiControlView;
import com.cleanwise.service.api.value.UiControlViewVector;
import com.cleanwise.service.api.wrapper.UiPageViewWrapper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.HashMap;
import java.util.Map;

public class UiPageConfigTag extends BodyTagSupport {

 private static final Logger log = Logger.getLogger(UiPageConfigTag.class);

    protected String name;
    protected String bean;
    protected String property;
    protected String type;
    private UiPageViewWrapper pageWrapper;
    private String configMode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int doStartTag() throws JspException {

        log.info("doStartTag() => BEGIN");

        Object beanObj = pageContext.findAttribute(Utility.strNN(this.bean));
        if (beanObj == null) {
            String message = "Bean " + this.bean + " not found.";
            log.error(message, new Exception(message));
            throw new JspException();
        }

        if (Utility.isSet(this.property)) {
            try {
                beanObj = PropertyUtils.getProperty(beanObj, property);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new JspException(e.getMessage());
            }
        }
            
        if (beanObj == null) {
            String message = "Page '" + this.name + "' not found within request scope";
            log.error(message, new Exception(message));
            return SKIP_BODY;
        }

        setPageWrapper((UiPageViewWrapper) beanObj);

        log.info("doStartTag() => END");

        return BodyTagSupport.EVAL_BODY_BUFFERED;

    }

    public int doEndTag() throws JspException {

        JspWriter out = pageContext.getOut();
        try {
            out.println(bodyContent.getString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SKIP_BODY;
        } finally {
            this.release();
        }
        return EVAL_PAGE;
    }

    public void release() {
        super.release();
        this.name = null;
        this.pageWrapper = null;
        this.bean = null;
        this.property = null;
        this.type = null;
        this.configMode = null;
    }

    public void setPageWrapper(UiPageViewWrapper pageWrapper) {
        this.pageWrapper = pageWrapper;
    }

    public UiPageViewWrapper getPageWrapper() {
        return pageWrapper;
    }

    public String getBean() {
        return bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isConfigMode() {
        return Utility.isTrue(configMode);
    }

    public String getConfigMode() {
        return configMode;
    }

    public void setConfigMode(String configMode) {
        this.configMode = configMode;
    }

     public void setConfigMode(boolean configMode) {
        this.configMode = String.valueOf(configMode);
    }
}

package com.cleanwise.view.taglibs;

import org.apache.log4j.Logger;


import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.cleanwise.service.api.value.UiControlElementData;


public class UiLabelTag extends BodyTagSupport {

    private static final Logger log = Logger.getLogger(UiLabelTag.class);

    private String property;
    private String value;
    private UiControlElementData controlElement;

    public void setProperty(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setControlElement(UiControlElementData controlElement) {
        this.controlElement = controlElement;
    }

    public UiControlElementData getControlElement() {
        return controlElement;
    }

    public int doStartTag() throws JspException {

        UiControlElementConfigTag ancestorTag = (UiControlElementConfigTag) findAncestorWithClass(this, UiControlElementConfigTag.class);
        if (ancestorTag == null) {
            String message = "UiLabelTag tag must be nested within a UiControlElementConfigTag tag.";
            throw new JspException(message);
        }

        if(ancestorTag.getAncestor().getAncestor().isConfigMode()){
            setProperty(ancestorTag.getProperty()+".value");
            setControlElement(ancestorTag.getControlElement());
        }

        return BodyTagSupport.EVAL_BODY_BUFFERED;

    }


    public int doEndTag() throws JspException {
        try {

            JspWriter out = pageContext.getOut();

            String property;
            String value;

            property = getProperty();
            value = getValue()==null?getBodyContent().getString():getValue();
            if (property != null) {
                out.println("<input type=\"hidden\"  name=\"" + property + "\" value=\"" + value + "\"/>");
            }

            out.println(getBodyContent().getString());

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
        property=null;
        value=null;
        controlElement= null;
    }

}

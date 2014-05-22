package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.value.UiControlView;
import com.cleanwise.service.api.value.UiControlElementData;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;

import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.StringWriter;
import java.io.PrintWriter;

import org.apache.commons.beanutils.PropertyUtils;


public class UiControlElementTag extends BodyTagSupport {

    private static final String className = "UiControlElementTag";

    private UiControlTag ancestor;
    private String name;
    private String bean;
    private String property;
    private UiControlElementData controlElement;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public UiControlElementData getControlElement() {
        return controlElement;
    }

    public void setControlElement(UiControlElementData controlElement) {
        this.controlElement = controlElement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UiControlTag getAncestor() {
        return ancestor;
    }

    public void setAncestor(UiControlTag ancestor) {
        this.ancestor = ancestor;
    }

    public int doStartTag() throws JspException {

        UiControlTag ancestorTag = (UiControlTag) findAncestorWithClass(this, UiControlTag.class);
        if (ancestorTag == null) {
            String message = "UiControlElement tag must be nested within a UiControlTag tag.";
            error(message, new Exception(message));
            throw new JspException(message);
        }

        UiControlView control = ancestorTag.getControl();

        UiControlElementData cElObj = null;
        if (Utility.isSet(this.name)) {
            cElObj = Utility.getUiControlElement(control.getUiControlElements(), this.name);
            if (cElObj == null) {
                String message = "Control '" + control.getUiControlData().getShortDesc() + "' does not contain element with name " + this.name;
                error(message, new Exception(message));
                return SKIP_BODY;
            }
        }

        Object beanObj;

        if (Utility.isSet(this.bean)) {

            beanObj = pageContext.findAttribute(Utility.strNN(this.bean));
            if (beanObj == null) {
                String message = "Bean " + this.bean + "not found.";
                error(message, new Exception(message));
                throw new JspException();
            }

            if (getValue() == null) {
                try {
                    Object value = PropertyUtils.getProperty(beanObj, property);
                    setValue(getAsString(value));
                } catch (Exception e) {
                    error(e.getMessage(), e);
                    throw new JspException(e.getMessage());
                }
            }
        }

        setAncestor(ancestorTag);
        setControlElement(cElObj);

        return BodyTagSupport.EVAL_BODY_BUFFERED;

    }

    private String getAsString(Object value) throws Exception {
        if (value == null) {
            return null;
        } else {
            return String.valueOf(value);
        }
    }


    public int doEndTag() throws JspException {
        try {

            UiControlView control = ancestor.getControl();

            if (RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE.equals(control.getUiControlData().getStatusCd())) {
                JspWriter out = pageContext.getOut();
                out.print(getBodyContent().getString());
            } else if (RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE.equals(control.getUiControlData().getStatusCd())) {
                String body = "<input type=\"hidden\" name=\"" + this.property + "\"" + (this.value != null ? " value=\"" + this.value + "\"" : "") + "/>";
                ancestor.addElementBody(controlElement, body);
            } else if (RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT.equals(control.getUiControlData().getStatusCd())) {
                String elementValue = controlElement != null ? controlElement.getValue() : null;
                String body = "<input type=\"hidden\" name=\"" + this.property + "\" value=\"" + (isSet(this.value) ? String.valueOf(this.value) : Utility.strNN(elementValue)) + "\"/>";
                ancestor.addElementBody(controlElement, body);
            }

        } catch (Exception e) {
            error(e.getMessage(), e);
            throw new JspException(e.getMessage());
        } finally {
            this.release();
        }

        return EVAL_PAGE;
    }

    public boolean isSet(String pVal){
        return Utility.isSet(pVal);
    }

    public void release() {
        super.release();
        this.value = null;
        this.name = null;
        this.ancestor = null;
        this.controlElement = null;
        this.bean = null;
        this.property = null;

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


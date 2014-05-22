package com.cleanwise.view.taglibs;

import org.apache.struts.taglib.html.RadioTag;
import org.apache.log4j.Logger;

import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.jsp.JspException;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.UiControlView;


public class UiRadioTag extends RadioTag {

    private static final Logger log = Logger.getLogger(UiRadioTag.class);
    UiControlElementConfigTag ancestor;

    public int doStartTag() throws JspException {

        UiControlElementConfigTag ancestorTag = (UiControlElementConfigTag) findAncestorWithClass(this, UiControlElementConfigTag.class);
        if (ancestorTag == null) {
            String message = "UiRadioboxTag tag must be nested within a UiControlElementConfigTag tag.";
            throw new JspException(message);
        }

        if (ancestorTag.getAncestor().getAncestor().isConfigMode()) {
            setName(ancestorTag.getBean());
            setProperty(ancestorTag.getProperty() + ".value");
            if (this.value == null && Utility.isSet(ancestorTag.getControlElement().getValue())) {
                setValue(ancestorTag.getControlElement().getValue());
            }
        }
        setAncestor(ancestorTag);

        return super.doStartTag();

    }

    public int doEndTag() throws JspException {
        try {
            UiControlView control = ancestor.getAncestor().getControl();
            if (RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE.equals(control.getUiControlData().getStatusCd()) || ancestor.getAncestor().getAncestor().isConfigMode()) {
                return super.doEndTag();
            } else if (RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE.equals(control.getUiControlData().getStatusCd())) {
                Object value = PropertyUtils.getProperty(pageContext.findAttribute(Utility.strNN(this.name)), this.property);
                if (this.value.equals(value)) {
                    String body = "<input type=\"hidden\" name=\"" + this.property + "\"" + (this.value != null ? " value=\"" + this.value + "\"" : "") + "/>";
                    ancestor.getAncestor().addElementBody(ancestor.getControlElement(), body);
                }
            } else if (RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT.equals(control.getUiControlData().getStatusCd())) {
                Object value = PropertyUtils.getProperty(pageContext.findAttribute(Utility.strNN(this.name)), this.property);
                if (value != null) {
                    if (this.value.equals(value)) {
                        String body = "<input type=\"hidden\" name=\"" + this.property + "\" value=\"" + this.value + "\"/>";
                        ancestor.getAncestor().addElementBody(ancestor.getControlElement(), body);
                    }
                } else {
                    String elementValue = ancestor.getControlElement() != null ? ancestor.getControlElement().getValue() : null;
                    if (this.value.equals(elementValue)) {
                        String body = "<input type=\"hidden\" name=\"" + this.property + "\" value=\"" + this.value + "\"/>";
                        ancestor.getAncestor().addElementBody(ancestor.getControlElement(), body);
                    }
                }
            }
            return EVAL_PAGE;
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        } finally {
            this.release();
        }
    }

    public void release() {
        super.release();
        ancestor = null;
    }

    public UiControlElementConfigTag getAncestor() {
        return ancestor;
    }

    public void setAncestor(UiControlElementConfigTag ancestor) {
        this.ancestor = ancestor;
    }
}

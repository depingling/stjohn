package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.UiControlView;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import org.apache.struts.taglib.html.TextTag;

import javax.servlet.jsp.JspException;


public class UiTextTag extends TextTag {

    private static final Logger log = Logger.getLogger(UiTextTag.class);

    UiControlElementConfigTag ancestor;

    public int doStartTag() throws JspException {

        UiControlElementConfigTag ancestorTag = (UiControlElementConfigTag) findAncestorWithClass(this, UiControlElementConfigTag.class);
        if (ancestorTag == null) {
            String message = "UiTextTag tag must be nested within a UiControlElementConfigTag tag.";
            throw new JspException(message);
        }

        if (ancestorTag.getAncestor().getAncestor().isConfigMode()) {
            setName(ancestorTag.getBean());
            setProperty(ancestorTag.getProperty() + ".value");
            if (this.value == null) {
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
                this.value = value != null ? String.valueOf(value) : null;
                String body = "<input type=\"hidden\" name=\"" + this.property + "\"" + (this.value != null ? " value=\"" + this.value + "\"" : "") + "/>";
                ancestor.getAncestor().addElementBody(ancestor.getControlElement(), body);
            } else if (RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT.equals(control.getUiControlData().getStatusCd())) {
                Object value = PropertyUtils.getProperty(pageContext.findAttribute(Utility.strNN(this.name)), this.property);
                this.value = value != null ? String.valueOf(value) : null;
                String elementValue = ancestor.getControlElement() != null ? ancestor.getControlElement().getValue() : null;
                String body = "<input type=\"hidden\" name=\"" + this.property + "\" value=\"" + (Utility.isSet(this.value) ? String.valueOf(this.value) : Utility.strNN(elementValue)) + "\"/>";
                ancestor.getAncestor().addElementBody(ancestor.getControlElement(), body);
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


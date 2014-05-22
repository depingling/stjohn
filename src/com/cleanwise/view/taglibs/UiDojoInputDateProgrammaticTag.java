package com.cleanwise.view.taglibs;

import org.apache.log4j.Logger;


import javax.servlet.jsp.JspException;

import com.cleanwise.service.api.value.UiControlView;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;


public class UiDojoInputDateProgrammaticTag extends  DojoInputDateProgrammaticTag{

    private static final Logger log = Logger.getLogger(UiDojoInputDateProgrammaticTag.class);

       UiControlElementConfigTag ancestor;

    public int doStartTag() throws JspException {

        log.info("doStartTag => BEGIN");

        UiControlElementConfigTag ancestorTag = (UiControlElementConfigTag) findAncestorWithClass(this, UiControlElementConfigTag.class);
        if (ancestorTag == null) {
            String message = "UiDojoInputDateProgrammaticTag tag must be nested within a UiControlElementConfigTag tag.";
            throw new JspException(message);
        }

        if(ancestorTag.getAncestor().getAncestor().isConfigMode()){
            setName(ancestorTag.getBean());
            setProperty(ancestorTag.getProperty()+".value");
        }
        setAncestor(ancestorTag);
        log.info("doStartTag => END.");

        return super.doStartTag();

    }

    public int doEndTag() throws JspException {
        try {
            UiControlView control = ancestor.getAncestor().getControl();
            log.info(getProperty());
            if (ancestor.getAncestor().getAncestor().isConfigMode() || RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE.equals(control.getUiControlData().getStatusCd())) {
                return super.doEndTag();
            } else if (RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE.equals(control.getUiControlData().getStatusCd())) {
                String body = "<input type=\"hidden\" name=\"" + this.property + "\"" + (getDateValue() != null ? " value=\"" + getDateValue() + "\"" : "") + "/>";
                ancestor.getAncestor().addElementBody(ancestor.getControlElement(), body);
            } else if (RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT.equals(control.getUiControlData().getStatusCd())) {
                String elementValue = ancestor.getControlElement() != null ? ancestor.getControlElement().getValue() : null;
                String body = "<input type=\"hidden\" name=\"" + this.property + "\" value=\"" + (Utility.isSet(getDateValue()) ? String.valueOf(getDateValue()) : Utility.strNN(elementValue)) + "\"/>";
                ancestor.getAncestor().addElementBody(ancestor.getControlElement(), body);
            }
            return EVAL_PAGE;
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

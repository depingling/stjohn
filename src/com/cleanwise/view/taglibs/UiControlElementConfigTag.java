package com.cleanwise.view.taglibs;

import org.apache.log4j.Logger;


import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.UiControlElementData;
import com.cleanwise.service.api.value.UiControlView;

public class UiControlElementConfigTag extends BodyTagSupport {

    private static final Logger log = Logger.getLogger(UiControlElementConfigTag.class);


    protected String name;
    protected String template;
    protected UiControlElementData controlElement;
    private UiControlConfigTag ancestor;
    private String bean;
    private String property;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public UiControlElementData getControlElement() {
        return controlElement;
    }

    public void setControlElement(UiControlElementData controlElement) {
        this.controlElement = controlElement;
    }

    public UiControlConfigTag getAncestor() {
        return ancestor;
    }

    public void setAncestor(UiControlConfigTag ancestor) {
        this.ancestor = ancestor;
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

    public int doStartTag() throws JspException {

        UiControlConfigTag ancestorTag = (UiControlConfigTag) findAncestorWithClass(this, UiControlConfigTag.class);
        if (ancestorTag == null) {
            String message = "UiControlElement tag must be nested within a UiControlTag tag.";
            log.error(message, new Exception(message));
            throw new JspException(message);
        }

        UiControlView control = ancestorTag.getControl();
        UiControlElementData cElObj;
        cElObj = Utility.getUiControlElement(control.getUiControlElements(), this.name);
        if (cElObj == null && !ancestorTag.getAncestor().isConfigMode()) {
            String message = "Control '" + control.getUiControlData().getShortDesc() + "' does not contain element with name " + this.name;
            log.error(message, new Exception(message));
            return SKIP_BODY;
        } else if (cElObj == null){
            cElObj = UiControlElementData.createValue();
            cElObj.setShortDesc(this.name);
            cElObj.setTypeCd(this.type);
            control.getUiControlElements().add(cElObj);
        }

        String baseProperty = "uiPage.uiControlWrapper(" + control.getUiControlData().getShortDesc() + ").uiControlElementWrapper(" + this.name + ")";

        setBean(ancestorTag.getAncestor().getBean());
        setProperty(baseProperty);

        setAncestor(ancestorTag);
        setControlElement(cElObj);

        return BodyTagSupport.EVAL_BODY_BUFFERED;

    }


    public int doEndTag() throws JspException {
        if(!getAncestor().getAncestor().isConfigMode()){
            return doView();
        } else{
           return doConfig();
        }
    }

      public int doConfig() throws JspException {
        try {

            JspWriter out = pageContext.getOut();

            String property;

            property = getProperty() + ".shortDesc";
            out.println("<input type=\"hidden\"  name=\"" + property + "\" value=\"" + getControlElement().getShortDesc() + "\"/>");

            property = getProperty() + ".typeCd";
            out.println("<input type=\"hidden\"  name=\"" + property + "\" value=\"" + getControlElement().getTypeCd() + "\"/>");

            out.println(getBodyContent().getString());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SKIP_BODY;
        } finally {
            this.release();
        }

        return EVAL_PAGE;
    }

    public int doView() throws JspException {
        try {

          JspWriter out = pageContext.getOut();
          out.print(getBodyContent().getString());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
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
        this.name = null;
        this.ancestor = null;
        this.controlElement = null;
        this.bean = null;
        this.property = null;

    }


}

package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.Utility;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;


public class DojoAutoSuggestTextBoxProgrammaticTag extends TagSupport {

    private static final Logger log = Logger.getLogger(DojoAutoSuggestTextBoxProgrammaticTag.class);

    private static String className = "DojoAutoSuggestTextBoxProgrammaticTag";

    private static final String OBJECT_SUFF = "Obj";

    public static final String ONLOAD = "ONLOAD";
    public static final String TABLE_MODEL = "TABLE_MODEL";
    public static final String LIST_MODEL  = "LIST_MODEL";

    private static final String _defaultModule = "dijit";

    private String name            = null;
    private String property        = null;
    private String id              = null;
    private String startupEvent    = null;
    private String module          = null;
    private Object dataObject      = null;
    private String value           = null;
    private String action          = null;
    private String formId          = null;
    private String searchAttr      = null;
    private String displayAttr     = null;
    private String model           = null;
    private String onEnterKeyPress = null;

    public int doStartTag() throws JspException {

        Object dataObj = null;

        if (this.value == null) {

            Object bean = pageContext.findAttribute(Utility.strNN(getName()));
            log.info("doStartTag => bean: " + bean);
            if (bean == null) {
                String message = "Bean not found";
                error(message, new Exception(message));
            }
            String property = getProperty();
            log.info("doStartTag => property: " + property);
            if (property == null) {
                dataObj = bean;
            } else if (bean != null) {
                try {
                    dataObj = PropertyUtils.getProperty(bean, property);
                } catch (Exception e) {
                    error(e.getMessage(), e);
                }
            }

            setDataObject( dataObj);
        }

        if (!starupEventSupport(getStartupEvent())) {
            String message = "Startup Event " + getStartupEvent() + " not supported.";
            error(message, new Exception(message));
            return SKIP_PAGE;
        }

        return BodyTagSupport.EVAL_PAGE;
    }

    private boolean starupEventSupport(String startupEvent) {
        return !Utility.isSet(startupEvent) ||
                (Utility.isSet(startupEvent) &&
                        (ONLOAD.equals(startupEvent)));
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int doEndTag() throws JspException {

        JspWriter out = pageContext.getOut();
        try {
            writeTo(out);
        } catch (Exception e) {
            e.printStackTrace();
            error(e.getMessage(), e);
            return SKIP_BODY;
        }

        return EVAL_PAGE;
    }

    private void writeTo(JspWriter out) throws Exception {

        try {

            StringBuffer buffer = new StringBuffer();
            StringBuffer djBuffer = new StringBuffer();

            String value = getValue();
            Object dataObject = getDataObject();
            if (value == null && dataObject != null) {
                value = (String) dataObject;
                setValue(value);
            }

            createInputDataObjVar(buffer);
            creteContainer(buffer);
            createJSO(djBuffer, getValue());
            buffer.append(createJSFunction(djBuffer));

            //log("buffer=> " + buffer.toString());
            out.write(buffer.toString());

        } finally {
            setValue(null);
            setDataObject(null);
            setDisplayAttr(null);
            setModel(null);
        }
    }

    private void createJSO(StringBuffer buffer, String value) {
        buffer.append("new ");
        if (Utility.isSet(getModule())) {
            buffer.append(getModule());
        } else {
            buffer.append(_defaultModule);
        }
        buffer.append(".form.AutoSuggestTextBox({store:new dojo.data.ItemFileReadStore({data:{items:[]}}),viewModel:");
        buffer.append(getJSViewModel());
        buffer.append(", templateNode:dojo.byId(\"");
        buffer.append(getId());
        buffer.append("\"),searchAttr:\"");
        buffer.append(searchAttr);
        buffer.append("\"");
        if (Utility.isSet(displayAttr)) {
            buffer.append(",displayAttr:");
            buffer.append(displayAttr);
        }
        buffer.append(",value:\"");
        buffer.append(Utility.strNN(value));
        buffer.append("\",name:\"");
        buffer.append(getFormId());
        buffer.append("\",iName:\"");
        buffer.append(getProperty());
        buffer.append("\",action:\"");
        buffer.append(getAction());
        buffer.append("\",autoComplete:false,searchDelay:500");
        if (Utility.isSet(onEnterKeyPress)) {
            buffer.append(",onEnterKeyPress:function(){ ");
            buffer.append(this.onEnterKeyPress);
            buffer.append("}");
        }

        buffer.append("},\"");

        buffer.append(getId());
        buffer.append("\");");
    }

    private String getJSViewModel() {

        String module;

        if (Utility.isSet(getModule())) {
            module = getModule();
        } else {
            module = _defaultModule;
        }

        if (TABLE_MODEL.equals(this.model)) {
            return "new " + module + ".form.TableModel()";
        } else {
            return "new " + module + ".form.ListModel()";
        }
    }

    private Object createJSFunction(StringBuffer buffer) {
        buffer.insert(0, "<script language=\"JavaScript1.2\"> function create" + getInputDateObject() + "() {");
        buffer.insert(buffer.length(), "}");

        String startupEvent = getStartupEvent();
        if (!Utility.isSet(startupEvent) || ONLOAD.equals(startupEvent)) {
            buffer.insert(buffer.length(), "dojo.addOnLoad(function (){ create" + getInputDateObject() + "();});");
        }

        buffer.insert(buffer.length(), "</script>");

        return buffer;
    }

    private void creteContainer(StringBuffer buffer) {
        String el = "<input id=\"" + getId() + "\" type=\"text\" name=\"" + getProperty() + "\"  autocomplete=\"off\" dojoAttachEvent=\"onkeypress:_onKeyPress, onfocus:_onMouse, compositionend,onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\" dojoAttachPoint=\"textbox,focusNode,comboNode\" waiRole=\"textbox\" waiState=\"haspopup-true,autocomplete-list\" value=\"" + Utility.strNN(getValue()) + "\"/>";
        buffer.append(el);
    }

    private void createInputDataObjVar(StringBuffer buffer) {
        buffer.append("");
    }

    private String getInputDateObject() {
        return getId() + OBJECT_SUFF;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDataObject(Object dataObject) {
        this.dataObject = dataObject;
    }

    public Object getDataObject() {
        return dataObject;
    }

    public String getStartupEvent() {
        return startupEvent;
    }

    public void setStartupEvent(String startupEvent) {
        this.startupEvent = startupEvent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getSearchAttr() {
        return searchAttr;
    }

    public void setSearchAttr(String searchAttr) {
        this.searchAttr = searchAttr;
    }

    public String getDisplayAttr() {
        return displayAttr;
    }

    public void setDisplayAttr(String displayAttr) {
        this.displayAttr = displayAttr;
    }

    public String getOnEnterKeyPress() {
        return onEnterKeyPress;
    }

    public void setOnEnterKeyPress(String onEnterKeyPress) {
        this.onEnterKeyPress = onEnterKeyPress;
    }

    public void release() {
        super.release();
        this.dataObject = null;
        this.name = null;
        this.id = null;
        this.property = null;
        this.module = null;
        this.startupEvent = null;
        this.value = null;
        this.action = null;
        this.formId = null;
        this.searchAttr = null;
        this.displayAttr = null;
        this.model = null;
        this.onEnterKeyPress = null;
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

package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.i18n.ClwI18nUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class DojoSelectorProgrammaticTag extends TagSupport {

    private static String className = "DojoSelectorProgrammaticTag";

    private static final String TARGET_SUFF = "Target";
    private static final String OBJECT_SUFF = "Obj";

    public static final String ONMOUSEOVER = "ONMOUSEOVER";
    public static final String ONLOAD = "ONLOAD";

    String id;
    String key;
    String module;
    private String targetWidth;
    private String targetTabStyle;
    private String startupEvent;
    private String onClick;

    public int doStartTag() throws JspException {

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
                        (ONLOAD.equals(startupEvent) || ONMOUSEOVER.equals(startupEvent)));
    }


    public int doEndTag() throws JspException {

        JspWriter out = pageContext.getOut();
        try {
            writeTo(out);
        } catch (Exception e) {
            error(e.getMessage(), e);
            return SKIP_PAGE;
        }

        return EVAL_PAGE;
    }

    private void writeTo(JspWriter out) throws IOException {
        StringBuffer buffer = new StringBuffer();
        StringBuffer djBuffer = new StringBuffer();
        String selectorObj = getSelectorObj();
        createSelectorObjVar(buffer,selectorObj);
        creteTarget(buffer,selectorObj);
        createMenuSelector(djBuffer);
        buffer.append(createJSFunction(djBuffer,selectorObj));
        //log("buffer=> " + buffer.toString());
        out.write(buffer.toString());
    }

    private void createMenuSelector(StringBuffer buffer) {
        String obj = getSelectorObj();
        buffer.append(obj);
        buffer.append("= new ");
        buffer.append(getModule());
        buffer.append(".MenuSelector({targetTabStyle:\"");
        buffer.append(getTargetTabStyle());
        buffer.append("\",target:\"");
        buffer.append(getTarget());
        buffer.append("\"})");

    }

    private StringBuffer createJSFunction(StringBuffer buffer, String obj) {

        buffer.insert(0, "<script language=\"JavaScript1.2\"> function create" + obj + "() {");
        buffer.insert(buffer.length(), "}");

        String startupEvent = getStartupEvent();
        if (!Utility.isSet(startupEvent) || ONLOAD.equals(startupEvent)) {
            buffer.insert(buffer.length(), "dojo.addOnLoad(function (){  create" + obj + "()});");
        }

        buffer.insert(buffer.length(), "</script>");

        return buffer;
    }


    private void createSelectorObjVar(StringBuffer buffer,String  obj) {

        buffer.append("<script language=\"JavaScript1.2\"> " + "var ");
        buffer.append(obj);
        buffer.append(";</script>");
    }

    private String getSelectorObj() {
        return getId()+OBJECT_SUFF;
    }

    private void creteTarget(StringBuffer buffer,String obj) {

        buffer.append("<td id=\"");
        buffer.append(getTarget());
        buffer.append("\" class=\"");
        buffer.append(getTargetTabStyle());

        if(Utility.isSet(getOnClick())){
            buffer.append("\" " + " onclick=\"");
            buffer.append(getOnClick());
            buffer.append("\"");
        }

        if(Utility.isSet(getTargetWidth())){
            buffer.append("width=\"");
            buffer.append(getTargetWidth());
            buffer.append("\"");
        }

        String startupEvent = getStartupEvent();
        if(ONMOUSEOVER.equals(startupEvent)){
            buffer.append(" onmouseover=\"if(dojo && ((dojo.isIE && ready && !");
            buffer.append(obj);
            buffer.append(") || (!dojo.isIE && !");
            buffer.append(obj);
            buffer.append("))");
            buffer.append("){ create");
            buffer.append(obj);
            buffer.append("(); ");
            buffer.append(obj);
            buffer.append(".select(this);}\"");
        }

        buffer.append("')\">");
        buffer.append(ClwI18nUtil.getMessage((HttpServletRequest) pageContext.getRequest(), getKey(), null));
        buffer.append("</td>");
    }

    private String getTarget() {
        return getId()+TARGET_SUFF;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTargetWidth() {
        return targetWidth;
    }

    public void setTargetWidth(String targetWidth) {
        this.targetWidth = targetWidth;
    }

    public String getTargetTabStyle() {
        return targetTabStyle;
    }

    public void setTargetTabStyle(String targetTabStyle) {
        this.targetTabStyle = targetTabStyle;
    }

    public String getStartupEvent() {
        return startupEvent;
    }

    public void setStartupEvent(String startupEvent) {
        this.startupEvent = startupEvent;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public void release() {
        this.id = null;
        this.targetWidth = null;
        this.targetTabStyle = null;
        this.key = null;
        this.module = null;
        this.onClick = null;
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

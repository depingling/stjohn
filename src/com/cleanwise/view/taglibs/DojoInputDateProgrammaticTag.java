package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.i18n.ClwI18nUtil;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;


public class DojoInputDateProgrammaticTag extends TagSupport {

    private static String className = " DojoInputDateProgrammaticTag";

    private static final String TARGET_SUFF = "Target";
    private static final String OBJECT_SUFF = "Obj";
    private static final String STARTUP_TARGET_SUFF ="StartupObj";

    public static final String ONCLICK = "ONCLICK";
    public static final String ONLOAD = "ONLOAD";

    private static final char space = ' ';
    private static final char quote = '\"';

    private static final String _defaultModule = "dijit";
    private static final String _defaultStartupTargetImgSrc = "../externals/images/showCalendar.gif";

    String name;
    String property;
    String id;
    String startupEvent;
    String module;
    Object dateObject;
    String startupTargetImgSrc;
    private String dateValue;

    public int doStartTag() throws JspException {

        Object dataObj = null;

        Object bean = pageContext.findAttribute(Utility.strNN(getName()));
        if (bean == null) {
            String message = "Bean not found";
            error(message, new Exception(message));
        }
        String property = getProperty();
        if (property == null) {
            dataObj = bean;
        } else if (bean != null) {
            try {
                dataObj = PropertyUtils.getProperty(bean, property);
            } catch (Exception e) {
                error(e.getMessage(), e);
            }
        }

        setDateObject(dataObj);

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
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        try {
            writeTo(request, out, getDateObject());
        } catch (Exception e) {
            error(e.getMessage(), e);
            return SKIP_BODY;
        }

        return EVAL_PAGE;
    }

    private void writeTo(HttpServletRequest request, JspWriter out, Object dateObject) throws IOException {

        StringBuffer buffer = new StringBuffer();
        StringBuffer djBuffer = new StringBuffer();

        GregorianCalendar calendar = null;
        if (dateObject instanceof String) {
            try {
                if (Utility.isSet((String) dateObject)) {
                    Date date = ClwI18nUtil.parseDateTimeInp(request, (String) dateObject, null, null);
                    calendar = new GregorianCalendar();
                    calendar.setTime(date);
                }
            } catch (ParseException e) {
            }
        } else if (dateObject instanceof Date) {
            calendar = new GregorianCalendar();
            calendar.setTime((Date) dateObject);
        } else if (dateObject instanceof GregorianCalendar) {
            calendar = (GregorianCalendar) dateObject;
        }

        setDateValue(calendar != null ? ClwI18nUtil.formatDateInp(request, calendar.getTime()) : (String) dateObject);

        String inputDateObj = getInputDateObject();

        createInputDateObjVar(buffer,inputDateObj);
        creteTarget(buffer);
        createMenuSelector(request, getDateValue(), djBuffer);
        buffer.append(createJSFunction(djBuffer,inputDateObj));
        //log("buffer=> " + buffer.toString());
        out.write(buffer.toString());
    }

    private Object createJSFunction(StringBuffer buffer, String obj) {
        buffer.insert(0, "<script language=\"JavaScript1.2\"> function create" + obj + "() {");
        buffer.insert(buffer.length(), "}");

        String startupEvent = getStartupEvent();
        if (!Utility.isSet(startupEvent) || ONLOAD.equals(startupEvent)) {
            buffer.insert(buffer.length(), "dojo.addOnLoad(function (){ create" + obj + "()});");
        }

        buffer.insert(buffer.length(), "</script>");

        return buffer;
    }

    private void creteTarget(StringBuffer buffer) {

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        buffer.append("<input id=");
        buffer.append(quote);
        buffer.append(getTarget());
        buffer.append(quote);

        if (Utility.isSet(getProperty())) {
            buffer.append(space);
            buffer.append("name=");
            buffer.append(quote);
            buffer.append(getProperty());
            buffer.append(quote);
        }

        buffer.append("/>");

        String startupTargeetImgSrc = getStartupTargetImgSrc() != null ? getStartupTargetImgSrc() : _defaultStartupTargetImgSrc;
        buffer.append("<img id=");

        buffer.append(quote);
        buffer.append(getStartupTarget());
        buffer.append(quote);

        buffer.append(space);

        buffer.append("src=");
        buffer.append(quote);
        buffer.append(startupTargeetImgSrc);
        buffer.append(quote);

        buffer.append(space);

        buffer.append("width=");
        buffer.append(quote);
        buffer.append("19px");
        buffer.append(quote);

        buffer.append(space);

        buffer.append("height=");
        buffer.append(quote);
        buffer.append("19px");
        buffer.append(quote);

        buffer.append(space);
        buffer.append("border=0");
        buffer.append(space);

        buffer.append("onmouseover=");
        buffer.append(quote);
        buffer.append("window.status='");
        buffer.append(ClwI18nUtil.getMessage(request, "global.label.chooseDate", null));
        buffer.append("';return true;");
        buffer.append(quote);

        buffer.append(space);

        buffer.append("onmouseout=");
        buffer.append(quote);
        buffer.append("window.status='';return true;");
        buffer.append(quote);

        buffer.append("/>");
    }

    private void createInputDateObjVar(StringBuffer buffer, String inputDateObj) {
        buffer.append("<script language=\"JavaScript1.2\"> " + "var ");
        buffer.append(inputDateObj);
        buffer.append(";</script>");
    }


    public void createMenuSelector(HttpServletRequest request, String dateValue, StringBuffer buffer) {

        buffer.append(getInputDateObject());
        buffer.append(" = new  ");
        if (Utility.isSet(getModule())) {
            buffer.append(getModule()).append(".form.DateTextBox");
        } else {
            buffer.append(_defaultModule).append(".form.DateTextBox");
        }
        buffer.append("({id:");
        buffer.append(quote);
        buffer.append(getTarget());
        buffer.append(quote);
        buffer.append(",appPatern:");
        buffer.append(quote);
        buffer.append(Utility.strNN(ClwI18nUtil.getCountryDateFormat(request)));
        buffer.append(quote);

        if (Utility.isSet(dateValue)) {
            buffer.append(",appValue:");
            buffer.append(quote);
            buffer.append(dateValue);
            buffer.append(quote);
        }

        if (Utility.isSet(getStartupTarget())) {
            buffer.append(",targetNodeIds:");
            buffer.append("['");
            buffer.append(getStartupTarget());
            buffer.append("']");
        }

        if (Utility.isSet(getProperty())) {
            buffer.append(space);
            buffer.append(",name:");
            buffer.append(quote);
            buffer.append(getProperty());
            buffer.append(quote);
        }

        buffer.append("})");

    }

    private String getInputDateObject() {
        return getId() + OBJECT_SUFF;
    }


    public String getStartupTarget() {
        return getId() + STARTUP_TARGET_SUFF;
    }


    private String getTarget() {
        return getId() + TARGET_SUFF;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDateObject(Object dateObject) {
        this.dateObject = dateObject;
    }

    public Object getDateObject() {
        return dateObject;
    }

    public String getStartupEvent() {
        return startupEvent;
    }

    public void setStartupEvent(String startupEvent) {
        this.startupEvent = startupEvent;
    }

    public String getStartupTargetImgSrc() {
        return startupTargetImgSrc;
    }

    public void setStartupImgSrc(String imgSrc) {
        this.startupTargetImgSrc = imgSrc;
    }

    public void release() {
        this.dateObject = null;
        this.name = null;
        this.id = null;
        this.property = null;
        this.module = null;
        this.startupEvent = null;
        this.startupTargetImgSrc = null;
        this.dateValue = null;
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

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }

    public String getDateValue() {
        return dateValue;
    }
}

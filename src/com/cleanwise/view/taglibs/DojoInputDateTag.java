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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


public class DojoInputDateTag extends TagSupport {

    private static String className = "DojoInputDateTag";

    private static final char space = ' ';
    private static final char quote = '\"';

    private static final String _defaultModule = "dijit";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    String name;
    String property;
    String id;
    String targets;
    String module;
    Object dateObject;

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

        return BodyTagSupport.EVAL_PAGE;
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
        GregorianCalendar calendar = null;

        if (dateObject instanceof String) {
            try {
                if (Utility.isSet((String) dateObject)) {
                    Date date = ClwI18nUtil.parseDateTimeInp(request, (String) dateObject,null,null);
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

        String  dateValue = calendar != null?ClwI18nUtil.formatDateInp(request,calendar.getTime()):(String) dateObject;

        buildDojoInputStr(request, dateValue, buffer);

        out.write(buffer.toString());
    }


    public void buildDojoInputStr(HttpServletRequest request,String dateValue, StringBuffer buffer) {


        buffer.append("<");
        buffer.append("input");

        if (Utility.isSet(getId())) {
            buffer.append(space);
            buffer.append("id=");
            buffer.append(quote);
            buffer.append(getId());
            buffer.append(quote);
        }

        if (Utility.isSet(getProperty())) {
            buffer.append(space);
            buffer.append("name=");
            buffer.append(quote);
            buffer.append(getProperty());
            buffer.append(quote);
        }

        buffer.append(space);
        buffer.append("dojoType=");
        buffer.append(quote);
        if (Utility.isSet(getModule())) {
            buffer.append(getModule()).append(".form.DateTextBox");
        } else {
            buffer.append(_defaultModule).append(".form.DateTextBox");
        }
        buffer.append(quote);

        buffer.append(space);
        buffer.append("appPattern=");
        buffer.append(quote);
        buffer.append(Utility.strNN(ClwI18nUtil.getCountryDateFormat(request)));
        buffer.append(quote);

        if (Utility.isSet(dateValue)) {
            buffer.append(space);
            buffer.append("appValue=");
            buffer.append(quote);
            buffer.append(dateValue);
            buffer.append(quote);
        }

        if (Utility.isSet(getTargets())) {
            buffer.append(space);
            buffer.append("targetNodeIds=");
            buffer.append(quote);
            buffer.append(getTargets());
            buffer.append(quote);
        }

        buffer.append("/>");

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

    public String getTargets() {
        return targets;
    }

    public void setTargets(String targets) {
        this.targets = targets;
    }

    public void setDateObject(Object dateObject) {
        this.dateObject = dateObject;
    }

    public Object getDateObject() {
        return dateObject;
    }

    public void release() {
        this.targets = null;
        this.dateObject = null;
        this.name = null;
        this.id = null;
        this.property = null;
        this.module = null;
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

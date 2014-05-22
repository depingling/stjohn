package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.UiControlElementData;
import com.cleanwise.service.api.value.UiControlView;
import com.cleanwise.service.api.value.UiPageView;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


public class UiControlTag extends BodyTagSupport {

    private static final String className = "UiControlTag";

    protected String name;
    protected String template;
    protected UiPageView page;
    protected UiControlView control;
    protected List<PairView> elementsBody;


    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<PairView> getElementsBody() {
        return elementsBody;
    }

    public void setElementsBody(List<PairView> elementsBody) {
        this.elementsBody = elementsBody;
    }

    public void setPage(UiPageView page) {
        this.page = page;
    }

    public void setControl(UiControlView control) {
        this.control = control;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UiPageView getPage() {
        return page;
    }

    public UiControlView getControl() {
        return control;
    }

    public int doStartTag() throws JspException {

        UiPageTag ancestorTag = (UiPageTag) findAncestorWithClass(this, UiPageTag.class);
        if (ancestorTag == null) {
            String message = "UiControl tag must be nested within a UiPageTag tag.";
            error(message, new Exception(message));
            throw new JspException(message);
        }

        UiPageView pageObj = ancestorTag .getPage();
        UiControlView controlObj = Utility.getUiControl(pageObj.getUiControls() , this.name);
        if (controlObj == null) {
            String message = "Page '"+pageObj.getUiPage().getShortDesc() +"' page does not contain control with name " + this.name;
            error(message, new Exception(message));
            return SKIP_BODY;
        }


        setPage(pageObj);
        setControl(controlObj);
        setElementsBody(new ArrayList<PairView>());

        return BodyTagSupport.EVAL_BODY_BUFFERED;

    }

    public int doEndTag() throws JspException {
        try {

            JspWriter out = pageContext.getOut();
            if (RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE.equals(control.getUiControlData().getStatusCd())) {
                out.print(getBodyContent().getString());
            } else {

                List<PairView> elsBody = getElementsBody();
                for (PairView elBody : elsBody) {
                    String elementField = (String) elBody.getObject2();
                    out.print("\r\n");
                    out.print(elementField);
                }

                if (Utility.isSet(getTemplate())) {
                    out.print(getTemplate());
                }

            }

        } catch (Exception e) {
            error(e.getMessage(), e);
            return SKIP_BODY;
        } finally {
            this.release();
        }

        return EVAL_PAGE;
    }

    public void addElementBody(UiControlElementData controlElement, String body) {
        this.elementsBody.add(new PairView(controlElement,body));
    }

    public void release() {
        super.release();
        name = null;
        page = null;
        control = null;
        elementsBody = null;
        template = null;
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

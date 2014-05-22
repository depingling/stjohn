package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.value.UiPageView;
import com.cleanwise.service.api.value.UiView;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ShopTool;

import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpSession;
import java.io.StringWriter;
import java.io.PrintWriter;

public class UiPageTag extends BodyTagSupport {

    private static final String className = "UiControlTag";

    protected String name;
    private UiPageView page;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UiPageView getPage() {
        return page;
    }

    public void setPage(UiPageView page) {
        this.page = page;
    }

    public int doStartTag() throws JspException {

        HttpSession session = pageContext.getSession();
        CleanwiseUser appUser = ShopTool.getCurrentUser(session);

        if (appUser == null) {
            throw new JspException("Invalid user object");
        }

        UiView ui = appUser.getUi();
        if (ui == null) {
            String message = "User interface is not set for user => " + appUser.getUserName();
            error(message, new Exception(message));
            return SKIP_BODY;
        }

        UiPageView pageObj = Utility.getUiPage(ui.getUiPages(), this.name);
        if (pageObj == null) {
            String message = "Page '" + this.name + "' has not been configured for ui => " + ui.getUiData().getUiId();
            error(message, new Exception(message));
            return SKIP_BODY;
        }

        setPage(pageObj);

        return BodyTagSupport.EVAL_BODY_BUFFERED;

    }

     public int doEndTag() throws JspException {

        JspWriter out = pageContext.getOut();
        try {
            out.print(bodyContent.getString());
        } catch (Exception e) {
            error(e.getMessage(), e);
            return SKIP_BODY;
        } finally {
            this.release();
        }
        return EVAL_PAGE;
    }


    public void release() {
        super.release();
        this.name = null;
        this.page = null;
    }

    /* Error logging
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

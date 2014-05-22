package com.espendwise.view.taglibs.esw;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import com.cleanwise.service.api.dto.ReportingDto;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.i18n.ClwMessageResourcesImpl;
import com.cleanwise.view.utils.Constants;

public class SpecifyAccountsTag extends TagSupport {
    private final static String LAYOUT_H = "H";
    private static long randIdCounter;

    private String layout;
    private String hiddenName;

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getHiddenName() {
        return hiddenName;
    }

    public void setHiddenName(String hiddenName) {
        this.hiddenName = hiddenName;
    }

    public int doStartTag() throws JspException {
        return BodyTagSupport.EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
        try {
            Writer out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            SessionDataUtil sessionDataUtil = Utility
                    .getSessionDataUtil(request);
            ReportingDto reportingInfo = sessionDataUtil.getGenericReportingDto();
            if (Utility.isSet(layout) == false) {
                layout = LAYOUT_H;
            }
            int[] accountId = null;
            if (reportingInfo != null) {
                accountId = reportingInfo.getAccountId();
            }
            String specifiedText = ClwI18nUtil.getMessage(request,
                    "locate.specifyAccounts.label.specifiedAccounts",
                    new Object[] { accountId == null ? 0 : accountId.length });
            StringBuilder hiddenText = new StringBuilder();
            StringBuilder idString = new StringBuilder();
            for (int i = 0; accountId != null && i < accountId.length; i++) {
                hiddenText.append("<input type='hidden' name='" + hiddenName
                        + "' value='" + accountId[i] + "'/>");
                if (idString.length() > 0) {
                    idString.append(',');
                }
                idString.append(accountId[i]);
            }
            String label = ClwMessageResourcesImpl.getMessage(request,
                    "global.label.account");
            final long randId = ++randIdCounter;
            final String specifyId = Constants.PARAMETER_OPERATION_SPECIFY_ACCOUNTS
                    + randIdCounter;
            String linkLabel = ClwMessageResourcesImpl.getMessage(request,
                    "locate." + Constants.PARAMETER_OPERATION_SPECIFY_ACCOUNTS
                            + ".link.label");
            String linkTitle = ClwMessageResourcesImpl.getMessage(request,
                    "locate." + Constants.PARAMETER_OPERATION_SPECIFY_ACCOUNTS
                            + ".link.title");
            String linkHref = request.getContextPath()
                    + "/userportal/esw/locate.do?operation="
                    + Constants.PARAMETER_OPERATION_SPECIFY_ACCOUNTS
                    + "&randId=" + randId + "&hiddenName=" + hiddenName;
            if (idString.length() > 0) {
                linkHref += "&ids=" + idString.toString();
            }
            StringBuilder htmlButton = new StringBuilder();
            htmlButton.append("<p id='");
            htmlButton.append(specifyId);
            htmlButton.append("Info'>");
            if (accountId != null && accountId.length > 0) {
                htmlButton.append(specifiedText);
            }
            htmlButton.append(hiddenText);
            htmlButton.append("</p>");
            htmlButton.append("<p class='buttonRow clearfix'><a id='");
            htmlButton.append(specifyId);
            htmlButton.append("Link' class='blueBtnMed popUpWide' title='");
            htmlButton.append(linkTitle + "'");
            htmlButton.append(" href='" + linkHref + "'");
            htmlButton.append("><span>" + linkLabel + "</span></a></p>");

            StringBuilder html = new StringBuilder();
            if (layout.toUpperCase().startsWith(LAYOUT_H)) {
                html.append("<tr><td>");
                html.append(label);
                html.append("</td><td>");
                html.append(htmlButton.toString());
                html.append("</td></tr>");
            } else {
                html.append("<p>");
                html.append(label);
                html.append("<br />");
                html.append(htmlButton.toString());
                html.append("</p>");
            }
            out.write(html.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }
        return BodyTagSupport.EVAL_PAGE;
    }
}
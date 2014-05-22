<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
	StringBuilder cssStyles = new StringBuilder(50);
	cssStyles.append("clearfix");
    String additionalCssStyles = request.getParameter(Constants.PARAMETER_ADDITIONAL_CSS_STYLES);
    if (Utility.isSet(additionalCssStyles)) {
    	cssStyles.append(" ");
    	cssStyles.append(additionalCssStyles);
    }
%>

        <div id="footerWrapper" class="<%=cssStyles%>">
            <div id="footer">
                <p>
                	<app:custom pageElement="pages.footer.msg"/>
                </p>
            </div>
        </div>
<%
	String duration = (String)request.getAttribute(Constants.REQUEST_ATTRIBUTE_PAGE_GENERATION_TIME);
	if (Utility.isSet(duration)) {
%>
		<div id="pageGenerationTime" style="text-align: center;">
	    	<p>
	    		<span style="font-size: 10px;">
	    			<app:storeMessage key="global.label.pageGenerationTime" arg0="<%=duration%>"/>
	    		</span>
			</p>
	    </div>
<%
	}
%>
        
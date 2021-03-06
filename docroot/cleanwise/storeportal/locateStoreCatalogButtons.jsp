<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.LocatePropertyNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<%
String jspFormName = request.getParameter("jspFormName");
String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
%>
<logic:present name="<%=jspFormName%>" property="<%=jspReturnFilterProperty%>">
    <logic:greaterThan name="<%=jspFormName%>" property="<%=jspReturnFilterProperty%>" value="0">
        <logic:iterate name="<%=jspFormName%>" property="<%=jspReturnFilterProperty%>" id="val">
            &lt;<bean:write name="val" property="shortDesc"/>&gt;
        </logic:iterate>
        <html:submit property="action" value="<%=LocatePropertyNames.CLEAR_STORE_CATALOG_FILTER_ACTION%>" styleClass='text'/>
    </logic:greaterThan>
</logic:present>
<html:submit property="action" value="<%=LocatePropertyNames.LOCATE_STORE_CATALOG_ACTION%>" styleClass='text'/>

<%@ page language="java" %>

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
    <logic:notEmpty name="<%=jspFormName%>" property="<%=jspReturnFilterProperty%>">
        <logic:iterate name="<%=jspFormName%>" property="<%=jspReturnFilterProperty%>" id="val">
            &lt;<bean:write name="val" property="busEntity.shortDesc"/>&gt;
        </logic:iterate>
        <html:submit property="action" value="Clear Distributor Filter" styleClass='text'/>
    </logic:notEmpty>
</logic:present>
<html:submit property="action" value="Locate Distributor" styleClass='text'/>
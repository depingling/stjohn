<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<html>
<body>

<%
String jspFormName = request.getParameter("jspFormName");
String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
%>
    
    <table align="left" cellspacing="0" cellpadding="0">
    <logic:present name="<%=jspFormName%>" property="<%=jspReturnFilterProperty%>">
        <bean:size id="returnFilterSize" name="<%=jspFormName%>" property="<%=jspReturnFilterProperty%>"/>
        <logic:greaterThan name="returnFilterSize" value="0">
            <tr align="left">
                <td colspan="2">&nbsp;</td>
                <td><b><app:storeMessage key="userlocate.site.text.selectedSites"/>:</b>&nbsp;<%=returnFilterSize%></td>
            </tr>
            <logic:iterate name="<%=jspFormName%>" property="<%=jspReturnFilterProperty%>" id="val">
            <tr align="left">
                <td colspan="2">&nbsp;</td>
                <td>&quot;<bean:write name="val" property="name"/>&quot;</td>
            </tr>
            </logic:iterate>
            <tr><td>&nbsp;</td></tr>
        </logic:greaterThan>
    </logic:present>
    <tr>
        <td>
            <html:button property="action" styleClass="store_fb" onclick="actionSubmit('11111','Locate Site');">
                <app:storeMessage key="userlocate.site.text.locateSite"/>
            </html:button>
        </td>
        <td>&nbsp;</td>
        <td>
            <logic:present name="<%=jspFormName%>" property="<%=jspReturnFilterProperty%>">
                <bean:size id="returnFilterSize" name="<%=jspFormName%>" property="<%=jspReturnFilterProperty%>"/>
                <logic:greaterThan name="returnFilterSize" value="0">
                    <html:button property="action" styleClass="store_fb" onclick="actionSubmit('11111','Clear Site Filter');">
                        <app:storeMessage key="userlocate.site.text.clearSiteFilter"/>
                    </html:button>
                </logic:greaterThan>
            </logic:present>
        </td>
    </tr>    
    </table>
<body>
<html>

<%@ page language="java" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:setLocaleAndImages/>

<%
String origIp=null;
String currIp=null;
Cookie[] cc = request.getCookies();
for(int i=0;i<cc.length;i++){
	Cookie c = cc[i];
	if("ORIG_IP".equalsIgnoreCase(c.getName())){
		origIp = c.getValue();
	}
}
currIp = (String) request.getHeader("X-Forwarded-For");
if(currIp == null){
	currIp = request.getRemoteAddr();
}
%>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">
<table>
		<tr> <td><html:errors/></td> </tr>
		<tr>
<td align="center">
<b>app:storeMessage key="login.errors.externalSystemFailed" /></b></td>
		</tr>
</table>
	
<!-- Current IP: <%=currIp%> Original IP: <%=origIp%> -->

</div>

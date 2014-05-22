<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="STORE_ADMIN_USER_FORM" type="com.cleanwise.view.forms.StoreUserMgrForm"/>
<% 
    int userId = theForm.getDetail().getUserData().getUserId();
%>
<table ID=1432 width="<%=Constants.TABLEWIDTH%>">
  <tr bgcolor="#000000">
    <% if(userId>0) { %>
    <app:renderStatefulButton link="userdet.do?action=returnToDetailPage" name="Detail" tabClassOff="tbar" 
				tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="userdet"/>
        
		<app:renderStatefulButton link="userconfig.do?initconfig=true" name="Configuration" tabClassOff="tbar" 
				tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="userconfig"/>
		<% }else{ %>
             <td class="tbartext">&nbsp;</td>
	    <% } %>	
        
		
	</tr>
</table>



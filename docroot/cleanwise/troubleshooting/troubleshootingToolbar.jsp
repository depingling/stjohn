

<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>



<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/> 




<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <tr>    
    <td class="troubleshooterrulecolor" colspan="2">
	  <img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="27">
	</td>    
  </tr>
  <tr>      
    <td class="troubleshooterdk" colspan="2" valign="top">
	  <img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooter.gif" WIDTH="179" HEIGHT="30">
	</td>
  </tr>
  <tr>      
    <td class="troubleshooterrulecolor" colspan="2">
	  <img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" WIDTH="1" HEIGHT="3">
	</td>
  </tr>
</table>
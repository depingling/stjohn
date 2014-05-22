<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.dao.*" %>
<%@ page import="com.cleanwise.view.forms.*" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
 <!--
  function f_check_search()
  {
  var x=document.getElementsByName("searchType");
  for (var i = 0; i < x.length ; i++)
  {
  if (x[i].value == "nameBegins")
  {
  x[i].checked = true;
  }
  }                   
  }

  function f_check_boxes()
  {
  f_set_vals(1);
  }

  function f_uncheck_boxes()
  {
  f_set_vals(0);
  }

  function f_set_vals(pVal)
  {
  var x=document.getElementsByName("confSelectIds");
  //alert('x.length='+x.length + ' pVal=' + pVal);
  for (var i = 0; i < x.length ; i++)
  {
  if (x[i].name == "confSelectIds")
  {
  if ( pVal == 1 )
  {
  x[i].checked = true;
  }
  else
  {
  x[i].checked = false;
  }
  }
  }

  }
  function submitForm() {
  document.forms[0].submit();
  }
  -->
</script>

<bean:define id="theForm" name="CORPORATE_SCHEDULE_FORM" type="com.cleanwise.view.forms.CorporateScheduleMgrForm"/>
<%
	int scheduleId = theForm.getScheduleData().getScheduleId();
  	String confFunc = theForm.getConfFunction();
%>
<div class="text">
<table ID=220 border="0" cellpadding="0" cellspacing="0" width="769" class="mainbody">
	<tr><td height="5"></td></tr>
    <tr>
        <td><b>Schedule Id:</b>
            <bean:write name="CORPORATE_SCHEDULE_FORM" property="scheduleData.scheduleId"/></td>
        <td colspan="2">&nbsp;</td>
        <td><b>Schedule Name:</b>
            <bean:write name="CORPORATE_SCHEDULE_FORM" property="scheduleData.shortDesc"/></td>
        <td colspan="2">&nbsp;</td>
        <td><b>Schedule Status:</b>
            <bean:write name="CORPORATE_SCHEDULE_FORM" property="scheduleData.scheduleStatusCd"/></td>
        <td colspan="2">&nbsp;</td>
    </tr>
    <tr><td height="5"></td></tr>
</table>

<table ID=1397>
  <html:form styleId="1398" action="/storeportal/corporateScheduleConfig.do">
    <html:hidden property="currentPage" value="config"/>                
    <tr> <td><b>Configure</b></td>
      <td>  
        <html:select name="CORPORATE_SCHEDULE_FORM" property="confFunction" onchange="submitForm()">          
          <html:option value="acctConfig">Accounts</html:option>
          <html:option value="siteConfig">Sites</html:option>
        </html:select>
      </td>
      <td>
    </tr>
  </html:form>
</table>

<table ID=1399 class="results"><tr><td>

  <% 
    String f1sub = "/storeportal/corporateScheduleConfig.do?scheduleId=" +
            scheduleId + "&tab=" + confFunc;

    String f2sub = "/storeportal/corporateScheduleConfig.do?goto=corporateScheduleConfig&scheduleId=" +
            scheduleId + "&tab=" + confFunc;
  %>


<% if (confFunc.equals("siteConfig")) { %>
       <%@ include file="corporateScheduleMgrSiteConfig.jsp" %>
  <% }else{ %>
       <%@ include file="corporateScheduleMgrAcctConfig.jsp" %>
  <% } %>

  











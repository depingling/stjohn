<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>

<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="account" type="java.lang.String" toScope="session"/>
<%
	String isMSIE = (String)session.getAttribute("IsMSIE");
	if (null == isMSIE) isMSIE = "";
%>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Accounts</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<% if ("Y".equals(isMSIE)) { %>
<script language="JavaScript" src="../externals/calendar.js"></script>
<% } else {  %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% }  %>

<body>

<bean:define id="pcd" name="Account.fiscalInfo"
  property="fiscalCalenderData.periodCd" type="java.lang.String"/>
<% 
  int dateFieldCount = 1;

        if ( pcd == null )
        {
            dateFieldCount = 13;
        }
	else 
	{

        if ( RefCodeNames.BUDGET_PERIOD_CD.ANNUALLY.equals(pcd))
        {
            dateFieldCount =1;
        }
        else if ( RefCodeNames.BUDGET_PERIOD_CD.SEMIANNUALLY.equals(pcd))
        {
            dateFieldCount =2;
        }
        else if ( RefCodeNames.BUDGET_PERIOD_CD.QUARTERLY.equals(pcd))
        {
            dateFieldCount =4;
        }
        else if ( RefCodeNames.BUDGET_PERIOD_CD.MONTHLY.equals(pcd))
        {
            dateFieldCount =13;
        }

	}
	request.setAttribute("dateFieldCount", new Integer(dateFieldCount));
%>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admAccountToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<% if ("Y".equals(isMSIE)) { %>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
    marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% }  %>

<div class="text">
<center>
<font color=red>
<!-- errors list 0 -->
<html:errors/>
<!-- errors list 1 -->
</font>
</center>



<table width="769"  class="mainbody">
<html:form name="COST_CENTER_DETAIL_FORM" action="adminportal/costcentermgrDetail"
scope="session" type="com.cleanwise.view.forms.CostCenterMgrDetailForm">

  <tr>
  <td><b>Store&nbsp;Id:</b></td><td><bean:write name="Store.id"/></td>
  <td><b>Store&nbsp;Name:</b></td><td><bean:write name="Store.name"/></td>
  </tr>
  <tr>
  <td><b>Account&nbsp;Id:</b></td><td><bean:write name="Account.id"/></td>
  <td><b>Account&nbsp;Name:</b></td><td><bean:write name="Account.name"/></td>
  </tr>

<tr>
<td><b>Cost Center&nbsp;Id:</b></td>
<td>
<logic:notEqual name="COST_CENTER_DETAIL_FORM" property="id" value="0">
<bean:write name="COST_CENTER_DETAIL_FORM" property="id"/>
</logic:notEqual>
<html:hidden property="id"/>
</td>

<td><b>Name:</b></td>
<td>
<html:text name="COST_CENTER_DETAIL_FORM" property="name" maxlength= "30" size="30"/>
<span class="reqind">*</span>
</td>
</tr>

<tr>
<td><b>Budget:</b></td>
<td>
<html:text name="COST_CENTER_DETAIL_FORM" property="budgetAmount"/>
<span class="reqind">*</span>
</td>
<td><b>Status:</b></td>
<td>
<html:select name="COST_CENTER_DETAIL_FORM" property="statusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Account.status.vector" property="value" />
</html:select>
</td>
</tr>

<tr>
<td><b>Cost Center Type:</b></td>
<td>
<html:select name="COST_CENTER_DETAIL_FORM" property="costCenterTypeCd" onchange="document.forms[0].submit();">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Budget.type.cd" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>

<tr>
<td colspan=4 align=center>

<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>
<logic:notEqual name="COST_CENTER_DETAIL_FORM" property="id" value="0">
<html:submit property="action">
<app:storeMessage  key="global.action.label.delete"/>
</html:submit>
</logic:notEqual>

</td>
</tr>
</table>

<logic:equal name="COST_CENTER_DETAIL_FORM" property="costCenterTypeCd" value="<%=RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET%>">
	<!-- begin site search form -->
	<jsp:include flush='true' page='costcentermgrDetailSite.jsp'/>
</logic:equal>
<logic:equal name="COST_CENTER_DETAIL_FORM" property="costCenterTypeCd" value="<%=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET%>">
	<table>
	<tr>
		<td><b>Amounts By Period:</b></td>
	<% if ( dateFieldCount == 13 ) {  %>
		<td>1</td><td><html:text size="5" property='budgetAmount1'/>
		<td>2</td><td><html:text size="5" property='budgetAmount2'/>
		<td>3</td><td><html:text size="5" property='budgetAmount3'/>
		<td>4</td><td><html:text size="5" property='budgetAmount4'/>
		<td>5</td><td><html:text size="5" property='budgetAmount5'/>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>6</td><td><html:text size="5" property='budgetAmount6'/>
		<td>7</td><td><html:text size="5" property='budgetAmount7'/>
		<td>8</td><td><html:text size="5" property='budgetAmount8'/>
		<td>9</td><td><html:text size="5" property='budgetAmount9'/>
		<td>10</td><td><html:text size="5" property='budgetAmount10'/>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>11</td><td><html:text size="5" property='budgetAmount11'/>
		<td>12</td><td><html:text size="5" property='budgetAmount12'/>
		<td>13</td><td><html:text size="5" property='budgetAmount13'/>
	<% } else if ( dateFieldCount == 4 ) {  %>
		<td>1st Quarter:</td><td><html:text size="5" property='budgetAmount1'/>
		<td>2nd Quarter:</td><td><html:text size="5" property='budgetAmount2'/>
		<td>3rd Quarter:</td><td><html:text size="5" property='budgetAmount3'/>
		<td>4th Quarter:</td><td><html:text size="5" property='budgetAmount4'/>
	<% } else if ( dateFieldCount == 2 ) {  %>
		<td>1st Half:</td><td><html:text size="5" property='budgetAmount1'/>
		<td>2nd Half:</td><td><html:text size="5" property='budgetAmount2'/>
	<% } else {  %>
		<td><html:text size="5" property='budgetAmount1'/>
	<% }  %>
	
	</tr>
	</table>
</logic:equal>

<html:submit property="action">
<app:storeMessage  key="costcenter.button.savebudgets"/>
</html:submit>
</logic:present>
</logic:notEqual>

<!-- End Display Sites -->


</html:form>

</div>

<% if ("Y".equals(isMSIE)) { %>
<script for=document event="onclick()">
<!--
document.all.CalFrame.style.display="none";
//-->
</script>
<% }  %>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>







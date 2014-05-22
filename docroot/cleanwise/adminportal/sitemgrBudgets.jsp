<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<style>
.bb { background-color: #cccccc; font-weight: bold; 
padding-left: 1em; padding-right: 1em;
}
</style>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Sites</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admSiteToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<% java.math.BigDecimal siteTotalBudget = new java.math.BigDecimal(0); %>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table width="769"  class="mainbody">
<html:form name="SITE_DETAIL_FORM" scope="session"
action="adminportal/sitemgrBudgets.do"
scope="session" type="com.cleanwise.view.forms.SiteMgrDetailForm">

<tr>
<td><b>Account&nbsp;Id:</b></td>
<td>
  <html:hidden name="SITE_DETAIL_FORM" property="accountId"/>
  <bean:write name="SITE_DETAIL_FORM" property="accountId"/>
</td>

<td><b>Account&nbsp;Name:</b></td>
<td>
  <bean:write name="SITE_DETAIL_FORM" property="accountName"/>
  <html:hidden name="SITE_DETAIL_FORM" property="accountName"/>
</td>

<tr>
<td><b>Store&nbsp;Id:</b></td>
<td>
  <bean:write name="SITE_DETAIL_FORM" property="storeId"/>
</td>
<td><b>Store&nbsp;Name:</b></td>
<td>
  <bean:write name="SITE_DETAIL_FORM" property="storeName"/>
</td>
</tr>

<tr>
<td><b>Site&nbsp;Id:</b> </td>
<td>
  <bean:write name="SITE_DETAIL_FORM" property="id"/>
</td>
<td><b>Name:</b> </td>
<td>
  <bean:write name="SITE_DETAIL_FORM" property="name"/>
</td>
</tr>

<table width="769"  class="results">
<tr align=left>
<td class="tableheader">Cost Center Info</td>
<td class="tableheader">Budget(s) Assigned</td>
<td class="tableheader">Fiscal Period</td>
<td class="tableheader">Fiscal Year</td>
<td class="tableheader">Budget Remaining<br></td>
</tr>

<logic:iterate id="budgetsele" name="SITE_DETAIL_FORM" 
    indexId="i" property="siteBudgetList"
   type="com.cleanwise.view.forms.SiteBudget">

<tr>
<td>

<table>
<tr><td class="bb">Name</td>
<bean:define id="lsid" name="SITE_DETAIL_FORM" property="id" type="String"/>
<bean:define id="eleid" name="budgetsele" property="budgetData.costCenterId"/>
<td>
<a href="costcentermgr.do?action=costcenterdetail&searchType=id&searchField=<%=eleid%>&siteId=<%=lsid%>">
<bean:write name="budgetsele" property="name"/>
</a>
</td>
<tr><td class="bb">Type</td><td><bean:write name="budgetsele" 
  property="costCenterData.costCenterTypeCd"/> </td></tr>
<tr><td class="bb">Id</td><td><bean:write name="budgetsele" 
  property="costCenterData.costCenterId"/> </td></tr>
<tr><td class="bb">Period</td><td>
<bean:write name="budgetsele" property="costCenterData.budgetPeriodCd"/>
 </td></tr>
</table>

</td>

<td>
<logic:equal name="budgetsele" property="costCenterData.budgetPeriodCd"
  value="<%=RefCodeNames.BUDGET_PERIOD_CD.WEEKLY%>">
<table border=1 bgcolor="white"><tr><td>
<bean:write name="budgetsele" property="budget1"/>
</TD></TR></TABLE>
</logic:equal>

<% String s = ""; %>

<logic:equal name="budgetsele" property="costCenterData.budgetPeriodCd"
  value="<%=RefCodeNames.BUDGET_PERIOD_CD.MONTHLY%>">
<table bgcolor="white" cellpadding=2>
<% s = "siteBudgetList[" + i + "].id";%>
<html:hidden name="SITE_DETAIL_FORM" property="<%=s%>" value="<%=lsid%>"/>
<tr>
<td class="bb">1</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget1";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>

<td class="bb">2</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget2";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>

<td class="bb">3</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget3";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>

<td class="bb">4</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget4";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>

<td class="bb">5</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget5";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>

<td class="bb">6</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget6";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>



</tr>
<tr>

<td class="bb">7</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget7";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>

<td class="bb">8</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget8";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>

<td class="bb">9</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget9";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>

<td class="bb">10</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget10";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>

<td class="bb">11</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget11";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>

<td class="bb">12</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget12";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>


</tr>
<tr>

<td class="bb">13</td><td align="right">
<% s = "siteBudgetList[" + i + "].budget13";%>
<html:text name="SITE_DETAIL_FORM" property="<%=s%>" size="5"/>
</td>


</tr>
</TABLE>

<bean:define id="b1" type="java.lang.String" name="budgetsele" 
 property="budget1"/>
<% siteTotalBudget = siteTotalBudget.add(new java.math.BigDecimal(b1)); %>
<bean:define id="b2" type="java.lang.String" name="budgetsele" 
 property="budget2"/>
<% siteTotalBudget = siteTotalBudget.add(new java.math.BigDecimal(b2)); %>
<bean:define id="b3" type="java.lang.String" name="budgetsele" 
 property="budget3"/>
<% siteTotalBudget = siteTotalBudget.add(new java.math.BigDecimal(b3)); %>
<bean:define id="b4" type="java.lang.String" name="budgetsele" 
 property="budget4"/>
<% siteTotalBudget = siteTotalBudget.add(new java.math.BigDecimal(b4)); %>
<bean:define id="b5" type="java.lang.String" name="budgetsele" 
 property="budget5"/>
<% siteTotalBudget = siteTotalBudget.add(new java.math.BigDecimal(b5)); %>
<bean:define id="b6" type="java.lang.String" name="budgetsele" 
 property="budget6"/>
<% siteTotalBudget = siteTotalBudget.add(new java.math.BigDecimal(b6)); %>
<bean:define id="b7" type="java.lang.String" name="budgetsele" 
 property="budget7"/>
<% siteTotalBudget = siteTotalBudget.add(new java.math.BigDecimal(b7)); %>
<bean:define id="b8" type="java.lang.String" name="budgetsele" 
 property="budget8"/>
<% siteTotalBudget = siteTotalBudget.add(new java.math.BigDecimal(b8)); %>
<bean:define id="b9" type="java.lang.String" name="budgetsele" 
 property="budget9"/>
<% siteTotalBudget = siteTotalBudget.add(new java.math.BigDecimal(b9)); %>
<bean:define id="b10" type="java.lang.String" name="budgetsele" 
 property="budget10"/>
<% siteTotalBudget = siteTotalBudget.add(new java.math.BigDecimal(b10)); %>
<bean:define id="b11" type="java.lang.String" name="budgetsele" 
 property="budget11"/>
<% siteTotalBudget = siteTotalBudget.add(new java.math.BigDecimal(b11)); %>
<bean:define id="b12" type="java.lang.String" name="budgetsele" 
 property="budget12"/>
<% siteTotalBudget = siteTotalBudget.add(new java.math.BigDecimal(b12)); %>

</logic:equal>


<logic:equal name="budgetsele" property="costCenterData.budgetPeriodCd"
  value="<%=RefCodeNames.BUDGET_PERIOD_CD.QUARTERLY%>">
<table border=1 bgcolor="white">
<tr><td>1st Quarter</TD><td><bean:write name="budgetsele" property="budget1"/></TD></TR>
<tr><td>2nd Quarter</TD><td><bean:write name="budgetsele" property="budget2"/></TD></TR>
<tr><td>3rd Quarter</TD><td><bean:write name="budgetsele" property="budget3"/></TD></TR>
<tr><td>4th Quarter</TD><td><bean:write name="budgetsele" property="budget4"/></TD></TR>
</table>
</logic:equal>

<logic:equal name="budgetsele" property="costCenterData.budgetPeriodCd"
  value="<%=RefCodeNames.BUDGET_PERIOD_CD.SEMIANNUALLY%>">
<table border=1 bgcolor="white">
<tr><td>1st Half</TD><td><bean:write name="budgetsele" property="budget1"/></TD></TR>
<tr><td>2nd Half</TD><td><bean:write name="budgetsele" property="budget2"/></TD></TR>
</table>
</logic:equal>

<logic:equal name="budgetsele" property="costCenterData.budgetPeriodCd"
  value="<%=RefCodeNames.BUDGET_PERIOD_CD.ANNUALLY%>">
<table border=1 bgcolor="white"><tr><td>
<bean:write name="budgetsele" property="budget1"/>
</TD></TR></TABLE>
</logic:equal>

</td>
<td><bean:write name="budgetsele" property="currentBudgetPeriod"/></td>
<td><%=budgetsele.getCurrentBudgetYear()%></td>


</td>
<bean:define id="remainingBudget" type="java.math.BigDecimal"
  name="budgetsele" property="siteBudgetRemaining"/>
<td><i18n:formatCurrency value="<%= remainingBudget %>"
  locale="<%= Locale.US %>" /></td>
</tr>
</logic:iterate>

<tr><td colspan=5 align="right">
<html:submit property="action">
Set budgets
</html:submit>
<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>
</td></tr>


</table>

</html:form>
</table>

<table width="769"  class="results">
<tr align=left>
<td align=right colspan=5 class="tableheader">

Total Budget</td><td> <%=siteTotalBudget%></td>
</tr></table>
</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>







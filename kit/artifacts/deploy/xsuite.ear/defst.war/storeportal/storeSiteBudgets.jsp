<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.FiscalCalendarUtility" %>
<%@ page import="com.cleanwise.view.forms.SiteBudget" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.cleanwise.service.api.util.BudgetUtil" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<style>
.bb { background-color: #cccccc; font-weight: bold;
padding-left: 1em; padding-right: 1em;
}
</style>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</head>

<body>

<script>
function f_setAllBudgetValue(pForm, index, numperiods) {
  var newValue = pForm.elements["setAllBudgetList["+index+"]"].value;
  var pattern = "siteBudgetList[" + index + "].siteBudgetAmount";
  for (var i=1; i<=numperiods; i++) {
    var name = pattern + "["+i+"]";
    pForm.elements[name].value = newValue;
  }
  return false;
}

function updateBudget() {
	document.getElementById("siteBudgetsForRequiredYear").value = "siteBudgetsForSelectedYear";
	document.getElementById("1188").value="sitebudgets.do";
	document.getElementById("1188").submit();
}
</script>

<bean:define id="theForm" name="STORE_ADMIN_SITE_FORM" type="com.cleanwise.view.forms.StoreSiteMgrForm"/>
<% String property;%>
<% java.math.BigDecimal siteTotalBudget = new java.math.BigDecimal(0); %>
<% boolean showUnlimitedTotal = true; 
   boolean isBudgetDefined = false;
%>
<% FiscalPeriodView fiscalInfo = theForm.getFiscalInfo(); %>

<div class="text">

<table ID=1187 width="769"  class="mainbody">
<html:form styleId="1188" name="STORE_ADMIN_SITE_FORM" scope="session"
               action="storeportal/sitebudgets.do"
               scope="session" type="com.cleanwise.view.forms.StoreSiteMgrForm">

<tr>
<td><b>Account&nbsp;Id:</b></td>
<td>
  <html:hidden name="STORE_ADMIN_SITE_FORM" property="accountId"/>
  <bean:write name="STORE_ADMIN_SITE_FORM" property="accountId"/>
</td>

<td><b>Account&nbsp;Name:</b></td>
<td>
  <bean:write name="STORE_ADMIN_SITE_FORM" property="accountName"/>
  <html:hidden name="STORE_ADMIN_SITE_FORM" property="accountName"/>
</td>

<tr>
<td><b>Site&nbsp;Id:</b> </td>
<td>
  <bean:write name="STORE_ADMIN_SITE_FORM" property="id"/>
</td>
<td><b>Name:</b> </td>
<td>
  <bean:write name="STORE_ADMIN_SITE_FORM" property="name"/>
</td>
</tr>

<table width="769" >
	<tr>
		<td width="35%">&nbsp;</td>
		<td width="35%">&nbsp;</td>
		
		<td align="right" >Fiscal Year :</td>
		<td align="left">
			<!-- bug # 4601 : Added to display dropdown with current fiscal year and next year -->
			<html:hidden property="siteBudgetsForSelectedYear" styleId="siteBudgetsForRequiredYear" value=""/>
			<html:select property="selectedFiscalYear" onchange="updateBudget()" >
			<logic:iterate id="fiscalYear" name="STORE_ADMIN_SITE_FORM" property="fiscalYearsList">
			<% if(new Integer(theForm.getSelectedFiscalYear()).equals(fiscalYear)) { %>
				<option selected value="<bean:write name='fiscalYear' />" ><bean:write name="fiscalYear" /></option>
			<% } else { %>
				<option value="<bean:write name='fiscalYear' />" ><bean:write name="fiscalYear" /></option>
			<% } %>
			</logic:iterate>
			</html:select>
		</td>
	</tr>
</table>

<table ID=1189 width="769"  class="results">
<tr align=left>
<td class="tableheader">Cost Center Info</td>
<td class="tableheader">Budget(s) Assigned</td>
<td class="tableheader">Fiscal Period</td>
<td class="tableheader">Fiscal Year</td>
<td class="tableheader">Budget Remaining<br></td>
</tr>
<% boolean siteBudgetFl = false; %>
<logic:iterate id="budgetsele" name="STORE_ADMIN_SITE_FORM"
    indexId="i" property="siteBudgetList"
   type="com.cleanwise.view.forms.SiteBudget">
<%
 BudgetView budgetV = budgetsele.getBudgetView();
 if(budgetV.getDetails()!=null && budgetV.getDetails().size()>0){
	isBudgetDefined = true;
 }
 String CostCntrTypeCD = budgetsele.getCostCenterData().getCostCenterTypeCd();
 String budgetType = budgetV.getBudgetData().getBudgetTypeCd();
 boolean ccBudgetReadOnlyFl = true;
 if(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(budgetType)) {
   ccBudgetReadOnlyFl = false;
   siteBudgetFl = true;
 }
 boolean showTextBox = false;
 if(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(CostCntrTypeCD)){
	 showTextBox = true;
 }
%>
<tr>
<td>
<table ID=1190>
<tr><td class="bb">Name</td>
<bean:define id="lsid" name="STORE_ADMIN_SITE_FORM" property="id" type="String"/>
<bean:define id="eleid" name="budgetsele" property="budgetView.budgetData.costCenterId"/>
<td><bean:write name="budgetsele" property="name"/></td></tr>
<tr><td class="bb">Type</td><td><bean:write name="budgetsele"
  property="costCenterData.costCenterTypeCd"/> </td></tr>
<tr><td class="bb">Id</td><td><bean:write name="budgetsele"
  property="costCenterData.costCenterId"/> </td></tr>
</table>
</td>

<td>

<% String s = ""; %>

<table ID=1191 bgcolor="white" cellpadding=2>
<%
if(!showTextBox){
%>
<% s = "siteBudgetList[" + i + "].id";%>
<html:hidden name="STORE_ADMIN_SITE_FORM" property="<%=s%>" value="<%=lsid%>"/>
<tr>

    <%for (int j = 0, p = 1; p <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalInfo.getFiscalCalenderView()); p++, j++) {%>
                                <%if (j % 7 == 0) {%></tr><tr><%}%>
                                <% if (Utility.isSet(FiscalCalendarUtility.getMmdd(fiscalInfo.getFiscalCalenderView().getFiscalCalenderDetails(), p))) {%>
                                    <td class="bb"><%=p%></td><td align="left" width="68">
                                    <%property = "budgetAmount["+p+"]";%>
                                    <td><bean:write name="budgetsele" property="<%=property%>"/>
                                    </td>
                                <% } %>
                                <% } %>

</tr>

<%}else { %>
<tr>
<% s = "siteBudgetList[" + i + "].id";%>
<html:hidden name="STORE_ADMIN_SITE_FORM" property="<%=s%>" value="<%=lsid%>"/>

     <%int j = 0;%>
    <%for (int p = 1; p <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalInfo.getFiscalCalenderView()); p++, j++) {%>
    <%if (j % 7 == 0) {%></tr><tr><%}%>
    <% if (Utility.isSet(FiscalCalendarUtility.getMmdd(fiscalInfo.getFiscalCalenderView().getFiscalCalenderDetails(), p))) {%>
    <td class="bb"><%=p%></td><td align="left" width="68">
    <% s = "siteBudgetList[" + i + "].siteBudgetAmount["+p+"]";%>
        <html:text name="STORE_ADMIN_SITE_FORM" property="<%=s%>" size="5" readonly="<%=ccBudgetReadOnlyFl%>"/>
    </td>
    <% } %>
    <% } %>

</tr>

<tr>                

<td align="left" colspan="7">
    <% if (BudgetUtil.isUsedSiteBudgetThreshold(theForm.getAllowBudgetThreshold(), theForm.getBudgetThresholdType())) { %>
    <table border="0" cellpadding="1" cellspacing="0" bgcolor="#cccccc" >
        <tr>
            <td>
                <% s = "siteBudgetList[" + i + "].budgetThreshold";%>
                <b>Budget Threshold(%)</b>: <html:text name="STORE_ADMIN_SITE_FORM" property="<%=s%>" size="4"/>
            </td>
        </tr>
    </table>
    <%}%>
</td>

<td align="right" colspan="7">
<table ID=1192 border="0" cellpadding="1" cellspacing="1" bgcolor="#cccccc" >
  <tr>
    <td><input type="text" name="setAllBudgetList[<%=i%>]" size="4"/></td>
    <td> <a ID=1193 href="#" onClick="return f_setAllBudgetValue(document.forms['STORE_ADMIN_SITE_FORM'], '<%=i%>', <%=FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalInfo.getFiscalCalenderView())%>)"><b>Set All</b></a> </td>
  </tr>
</table>
</td>
</tr>
<%} %>
</TABLE>
</td>

<%for (int j = 0, p = 1; p <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalInfo.getFiscalCalenderView()); p++, j++) {%>
<% if (Utility.isSet(FiscalCalendarUtility.getMmdd(fiscalInfo.getFiscalCalenderView().getFiscalCalenderDetails(), p))) {
    String amount = ((SiteBudget) budgetsele).getBudgetAmount(p);
    if (Utility.isSet(amount)) {
		if(amount!=null && amount.equalsIgnoreCase(Constants.UNLIMITED)) {
			amount = "0";
		} else {
			showUnlimitedTotal = false;
		}
        BigDecimal bAmount;
        try {
            bAmount = new BigDecimal(amount);
        } catch (Exception e) {
            bAmount = new BigDecimal(0);
        }
        siteTotalBudget = Utility.addAmt(siteTotalBudget, bAmount);
    }
}
}%>


<td><bean:write name="budgetsele" property="currentBudgetPeriod"/></td>
<td><%=budgetsele.getCurrentBudgetYear()%></td>



<td>
<% 
	if(budgetsele.getRemainingBudgetForSite()!=null ) { %>
		<bean:define id="remainingBudget" type="java.math.BigDecimal" name="budgetsele" property="remainingBudgetForSite"/>
		<i18n:formatCurrency value="<%= remainingBudget %>" locale="<%= Locale.US %>" />
	<% } else {
		if(!showTextBox) {
			BigDecimal bg = new BigDecimal(0);
			%>
				<i18n:formatCurrency value="<%= bg %>" locale="<%= Locale.US %>" />
			<%
		} else {
		%>
			<%=Constants.UNLIMITED %>
	<%	}
	}%>

  </td>
</tr>
</logic:iterate>
<tr>
<td align="right" colspan="5">
<table><tr>
	<%	
	if(siteBudgetFl) { %>
		<td colspan=5 align="right">
		<html:submit property="action">Set All Unlimited</html:submit>
		</td>
		<td colspan=5 align="right">
		<html:submit property="action">Set budgets</html:submit>
		</td>
	<% } %>
	<% boolean enabled = !(theForm.getEnableCopyBudgetsButton()); %>

	<td colspan=5 align="right">
	<html:submit disabled="<%=enabled %>" property="copyBudgetsFromPreviousYear">Copy Budgets From Previous Year</html:submit>
	</td>
</tr>
</table>
</td>

</tr>

</table>

</html:form>
</table>

<table ID=1194 width="769"  class="results" >
<tr align=left>
<td align=right colspan=5 class="tableheader">

Total Budget</td>
<td width="10%"> 
	<%if(showUnlimitedTotal && isBudgetDefined) {%>
		<%=Constants.UNLIMITED %>
	<%}else {%>
		<%=siteTotalBudget%>
	<%}%>
</td>
</tr></table>
</div>

</body>

</html:html>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">

  <html:form name="SITE_BUDGET_SEARCH_FORM" action="adminportal/sitebudgetmgr.do"
    scope="session" type="com.cleanwise.view.forms.SiteBudgetMgrSearchForm">

  <table width="769"  style="background-color: #cccccc;">

  <tr>
  <td>Store&nbsp;Id:</td><td><bean:write name="Store.id"/></td>
  <td>Store&nbsp;Name:</td><td><bean:write name="Store.name"/></td>
  </tr>
  <tr>
  <td>Account&nbsp;Id:</td><td><bean:write name="Account.id"/></td>
  <td>Account&nbsp;Name:</td><td><bean:write name="Account.name"/></td>
  </tr>
  <tr class="results"> <td><b>Find Site Budget:</b></td>
       <td colspan="3">
			<html:text name="SITE_BUDGET_SEARCH_FORM" property="searchField"/>
       </td>
  </tr>
  <tr class="results"> <td></td>
       <td colspan="3">
         <html:radio name="SITE_BUDGET_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="SITE_BUDGET_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="SITE_BUDGET_SEARCH_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.create"/>
	</html:submit>
     </html:form>
    </td>
  </tr>
</table>

<logic:present name="SiteBudget.found.vector">
<bean:size id="rescount"  name="SiteBudget.found.vector"/>
Search result count:  <bean:write name="rescount" />
<table width="769"  style="background-color: #ccff66;">
<tr align=left>
<td>Site Budget Id </td><td>Name </td><td>Cost Center Name </td><td>Cost Center Budget <td>Site Budget </td>
</tr>

<logic:iterate id="arrele" name="SiteBudget.found.vector">
<tr>
<td><bean:write name="arrele" property="budgetId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="budgetId"/>
<bean:define id="ccname" name="arrele" property="costCenterName"/>
<a href="sitebudgetmgr.do?action=sitebudgetdetail&searchType=id&searchField=<%=eleid%>&ccname=<%=ccname%>">
<bean:write name="arrele" property="budgetName"/>
</a>
</td>
<td>
<bean:define id="ccid" name="arrele" property="costCenterId"/>
<a href="costcentermgr.do?action=costcenterdetail&searchType=id&searchField=<%=ccid%>">
<bean:write name="arrele" property="costCenterName"/>
</a>
</td>
<bean:define id="ccamt" name="arrele" property="costCenterBudgetAmount"/>
<% java.math.BigDecimal ccbudget = new java.math.BigDecimal((String)ccamt); %>
<td><i18n:formatCurrency value="<%=ccbudget%>" locale="<%=Locale.US%>"/></td>
<bean:define id="amt" name="arrele" property="budgetAmount"/>
<% java.math.BigDecimal budget = new java.math.BigDecimal((String)amt); %>
<td><i18n:formatCurrency value="<%=budget%>" locale="<%=Locale.US%>"/></td>
</tr>

</logic:iterate>
</table>
</logic:present>

</div>

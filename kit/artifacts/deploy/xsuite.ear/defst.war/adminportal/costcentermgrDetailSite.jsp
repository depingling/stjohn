<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


  
<%int dateFieldCount = ((Integer)request.getAttribute("dateFieldCount")).intValue();%>

<logic:notEqual name="COST_CENTER_DETAIL_FORM" property="id" value="0">
<div class="text">
<br>
  <table width="769" cellspacing="0" border="0" class="mainbody">
<tr>
<td class="largeheader" rowspan=3>Site<br> Budgets</td>
<td align="right"><b>Find Site</b></td>
<td>
<html:text name="COST_CENTER_DETAIL_FORM" property="searchField"/>
<html:radio name="COST_CENTER_DETAIL_FORM" property="searchType" 
    value="id" />  ID
<html:radio name="COST_CENTER_DETAIL_FORM" property="searchType" 
    value="nameBegins" />  Name(starts with)
<html:radio name="COST_CENTER_DETAIL_FORM" property="searchType" 
    value="nameContains" />  Name(contains)
</td>
</tr>

<tr> 

<td align="right"><b>City</b></td>
<td><html:text name="COST_CENTER_DETAIL_FORM" property="city"/> </td>
</tr>

<tr> 

<td align="right"><b>State</b></td>
<td><html:text name="COST_CENTER_DETAIL_FORM" property="state"/> 
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
    </td>
  </tr>
</table>

</div>
</logic:notEqual>
<!-- Display Sites -->

<logic:notEqual name="COST_CENTER_DETAIL_FORM" property="id" value="0">
<logic:present name="COST_CENTER_DETAIL_FORM" property="siteBudgetList">
<bean:size id="sitesfound" name="COST_CENTER_DETAIL_FORM" property="siteBudgetList"/>
Search result count: <bean:write name="sitesfound"/>

<table width="769"  class="results">
<tr align=left>
<td>Site Id</td><td>Site Name</td><td>City</td><td>State</td>
<td>Budget</td>
</tr>
<logic:iterate id="budgetsele" name="COST_CENTER_DETAIL_FORM" indexId="i" property="siteBudgetList">
<% if (( i.intValue() % 2 ) == 0 ) { %>
<tr class="rowa">
<% } else { %>
<tr class="rowb">
<% } %>
<td><bean:write name="budgetsele" property="id"/></td>
<td><bean:write name="budgetsele" property="name"/></td>
<td><bean:write name="budgetsele" property="city"/></td>
<td><bean:write name="budgetsele" property="state"/></td>

<% if ( dateFieldCount == 13 ) {  %>
<td>
<table><tr>
<td>1</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget1" %>'/>
<td>2</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget2" %>'/>
<td>3</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget3" %>'/>
<td>4</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget4" %>'/>
<td>5</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget5" %>'/>
<td>6</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget6" %>'/>
</tr><tr>
<td>7</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget7" %>'/>
<td>8</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget8" %>'/>
<td>9</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget9" %>'/>
<td>10</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget10" %>'/>
<td>11</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget11" %>'/>
<td>12</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget12" %>'/>
</tr><tr>
<td>13</td><td><html:text size="5" property='<%= "siteBudget[" + i + "].budget13" %>'/>
</tr></table>
</td>
<% } else if ( dateFieldCount == 4 ) {  %>
<td>
<table><tr>
<td> 1st Quarter: <html:text size="5" property='<%= "siteBudget[" + i + "].budget1" %>'/></td>
<td> 2nd Quarter: <html:text size="5" property='<%= "siteBudget[" + i + "].budget2" %>'/></td>
</tr><tr>
<td> 3rd Quarter: <html:text size="5" property='<%= "siteBudget[" + i + "].budget3" %>'/></td>
<td> 4th Quarter: <html:text size="5" property='<%= "siteBudget[" + i + "].budget4" %>'/></td>
</tr></table>
</td>
<% } else if ( dateFieldCount == 2 ) {  %>
<td>
 1st half: <html:text size="5" property='<%= "siteBudget[" + i + "].budget1" %>'/>
 2nd half: <html:text size="5" property='<%= "siteBudget[" + i + "].budget2" %>'/>
</td>
<% } else {  %>
<td>
<html:text size="5" property='<%= "siteBudget[" + i + "].budget1" %>'/>
</td>
<% }  %>

</tr>
</logic:iterate>
</table>
</logic:present>
</logic:notEqual>

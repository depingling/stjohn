<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<jsp:include flush='true' page="ui/accountCtx.jsp"/>

<logic:present name="Account.fiscalInfo">
 <logic:present name="Account.fiscalInfo"
  property="fiscalCalenderData">

<html:form name="COST_CENTER_SEARCH_FORM" 
  action="adminportal/costcentermgr.do"    
  scope="session" 
  type="com.cleanwise.view.forms.CostCenterMgrSearchForm">

<bean:define id="accountFiscalCalender"
  name="Account.fiscalInfo"
  property="fiscalCalenderData"
  type="FiscalCalenderData"/>
<bean:define id="theForm"
  name="COST_CENTER_SEARCH_FORM"
  type="com.cleanwise.view.forms.CostCenterMgrSearchForm"/>

<table bgcolor=lightgreen width=769>

<tr>
<td rowspan=4>

<table>
<tr>
<td>
<b> Fiscal Year:</b>
</td><td>
<bean:write name="accountFiscalCalender"
  property="fiscalYear"/>
</td>
</tr>
<tr>
<td>
<b> Current Period:</b>
</td><td>
<bean:write name="Account.fiscalInfo"
  property="currentFiscalPeriod"/>
</td>
</tr>
<tr>
<% theForm.setBudgetPeriod(accountFiscalCalender.getPeriodCd() ); %>
<% theForm.setFiscalCalenderEffDate(accountFiscalCalender.getEffDate() ); %>
<td>
<b> Fiscal Calender:</b>
</td>
<td>
<bean:write name="COST_CENTER_SEARCH_FORM" property="budgetPeriod"/>
</td>
</tr>
<tr>
<td>
<b>Effective Date:</b>
</td>
<td>
<bean:write name="accountFiscalCalender"
  property="effDate"/>
</td>
</tr>
</table>


</td>
</tr>

<tr><td colspan=6 align=center><b> Start Dates (mm/dd) </b></td></tr>
<tr>
<td align=right><b>1</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd1"/></td>
<td align=right><b>2</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd2"/></td>
<td align=right><b>3</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd3"/></td>
<td align=right><b>4</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd4"/></td>
<td align=right><b>5</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd5"/></td>
<td align=right><b>6</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd6"/></td>
</tr>
<tr>
<td align=right><b>7</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd7"/></td>
<td align=right><b>8</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd8"/></td>
<td align=right><b>9</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd9"/></td>
<td align=right><b>10</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd10"/></td>
<td align=right><b>11</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd11"/></td>
<td align=right><b>12</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd12"/></td>
<td align=right><b>13</b></td><td bgcolor=white><bean:write name="accountFiscalCalender" property="mmdd13"/></td>
</tr>


<!-- enable to set the fiscal calender from the ui 
<td colspan=5>
 <html:submit property="action">	
  <app:storeMessage  key="admin.button.setCalender"/></html:submit>
  </td>
  -->

  
</tr>
</html:form>

</table>
 </logic:present>
</logic:present>

<table width="769" class="results">

<tr><td>
<html:form name="COST_CENTER_SEARCH_FORM" action="adminportal/costcentermgr.do"    scope="session" type="com.cleanwise.view.forms.CostCenterMgrSearchForm">
  <b> Cost Centers </b><br> 
 <html:submit property="action">	
  <app:storeMessage  key="admin.button.create"/></html:submit>
</html:form>
</td>
<td>
<logic:present name="CostCenter.found.vector">

<table class="results">
<tr align=left>
<td><a class="tableheader" href="costcentermgr.do?action=sort&sortField=id">Cost Center&nbsp;Id</a></td>
<td><a class="tableheader" href="costcentermgr.do?action=sort&sortField=name">Name</a></td>
<td><a class="tableheader" href="costcentermgr.do?action=sort&sortField=amount">Budget Amount</a></td>
<td><a class="tableheader" href="costcentermgr.do?action=sort&sortField=status">Status</a></td>
</tr>

<logic:iterate id="arrele" name="CostCenter.found.vector">
<tr>
<td><bean:write name="arrele" property="costCenterId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="costCenterId"/>
<a href="costcentermgr.do?action=costcenterdetail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="shortDesc"/>
</a>
</td>
<bean:define id="amt" name="arrele" property="budgetAmount"/>
<td><i18n:formatCurrency value="<%=amt%>" locale="<%=Locale.US%>"/></td>
<td><bean:write name="arrele" property="costCenterStatusCd"/></td>
</tr>
</logic:iterate>
</table>

</logic:present>
</td>
</tr>
</table>
<p>
<logic:present name="CategoryToCostCenter.found.vector">
<table width="769" class="results">
<tr>
<td><b>Category</b></td>
<td><b>Current Cost Center</b></td>
<td><b>New Cost Center</b></td>
</tr>


<logic:iterate id="cctoc" name="CategoryToCostCenter.found.vector" indexId="mainidx" >
<tr <% if (( mainidx.intValue() % 2 ) == 0 ) { %>class="rowa"<% } else { %>class="rowb"<% } %> >
<td><bean:write name="cctoc" property="categoryName"/></td>
<bean:define id="currentccid" name="cctoc" property="costCenterId"
  type="java.lang.Integer" />
<td>
<% String displayStr = "--None--"; %>
<logic:iterate id="ccopt" name="CostCenter.found.vector">
<bean:define id="thisccid" name="ccopt" property="costCenterId" type="java.lang.Integer" />
<% if ( thisccid.intValue() == currentccid.intValue() ) {  %> 
<bean:write name="ccopt" property="shortDesc"/>
<% 
displayStr = ""; } 
%> 
</logic:iterate>

<%= displayStr %>
</td>

<td><form name="FFF<%= mainidx.intValue() %>" action="costcentermgr.do">
<select name="newCostCenter" onchange="javascript: { document.FFF<%= mainidx.intValue() %>.submit();}">
<option value="0" <% if ( currentccid.intValue() == 0 ) { %> selected="y" <% } %> >--None--</option>
<logic:iterate id="ccopt" name="CostCenter.found.vector">
  <bean:define id="thisccid" name="ccopt" property="costCenterId" type="java.lang.Integer" />
  <bean:define id="thisccstatus" name="ccopt" property="costCenterStatusCd" type="java.lang.String" />
  <% if("INACTIVE".equals(thisccstatus)) { continue; } %>
  <option <% if ( thisccid.intValue() == currentccid.intValue() ) {  %> selected="y" <% } %> value="<%= thisccid %>" >
    <bean:write name="ccopt" property="shortDesc"/> 
   </option>
</logic:iterate>
</select>
<bean:define id="catname" name="cctoc" property="categoryName"/>
<input type="hidden" name="categoryName" value="<%= catname %>" >
<input type="hidden" name="action" value="setCategoryCostCenter" >
</form></td>
</tr>
</logic:iterate>

</table>
</logic:present>

<% boolean hdrdone = false; %>
<logic:present name="ItemToCostCenter.found.vector">
<p><ul>List of items without cost centers.
<bean:size id="totf" name="ItemToCostCenter.found.vector"/>
 Total found: <%=totf%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="costcentermgr.do?action=reset_item_cost_centers">
[Reset Item Cost Centers.]</a>
</ul></p>
<table width="769" class="results">


<logic:iterate id="cctoi" 
  name="ItemToCostCenter.found.vector" indexId="mainidx" 
  type="com.cleanwise.service.api.dao.UniversalDAO.dbrow" >

<% if ( !hdrdone ) { 
  hdrdone = true;
%> 
<%=cctoi.toHtmlTableHeader()%> 
<% } %>
<%=cctoi.toHtmlTableRow()%>
</logic:iterate>


</table>
</logic:present>

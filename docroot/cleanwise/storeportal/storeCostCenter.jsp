<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.FiscalCalendarUtility" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm"
  name="STORE_ADMIN_CATALOG_FORM"
  type="com.cleanwise.view.forms.StoreCatalogMgrForm"/>
<table ID=675 border="0"cellpadding="2" cellspacing="1" width="769"  bgcolor="#cccccc">
  <tr>
    <td><b>Catalog ID:</b>
      &nbsp;<bean:write name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogId" />
    </td>
    <td><b>Catalog Name:</b>
      &nbsp;<bean:write name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.shortDesc" />
    </td>
    <td><b>Catalog Type:</b>
      <bean:write name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogTypeCd"/>
    </td>
    <td><b>Catalog Status:</b>
      <bean:write name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogStatusCd"/>
    </td>
  </tr>
</table>
<%
   CatalogFiscalPeriodViewVector cfpVwV = theForm.getCatalogFiscalPeriods();
   if(cfpVwV!=null && cfpVwV.size()>0) {
%>
<table ID=676 border="0"cellpadding="2" cellspacing="1" width="769"  bgcolor="#cccccc">
  <tr><td class='tableheader' colspan='16'>Budget Periods</td>
  </tr>
  <tr>
  <td class='tableheader'>Account(s)</td>
  <td class='tableheader'>Effective Date</td>
  <td class='tableheader' align='center'>Year</td>
  <td class='tableheader' align='center'>Type</td>
  <td class='tableheader' colspan='13' align='center'>Period Start Dates</td>
  </tr>

<%
  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
  for(int ii=0; ii<cfpVwV.size(); ii++) {
    CatalogFiscalPeriodView cfpVw = (CatalogFiscalPeriodView) cfpVwV.get(ii);
    FiscalCalenderView fcV = cfpVw.getFiscalCalenderView();
    BusEntityDataVector beDV = cfpVw.getAccounts();
    Date effDate = fcV.getFiscalCalender().getEffDate();
    String effDateS = (effDate!=null)? sdf.format(effDate):"";
    int year =fcV.getFiscalCalender().getFiscalYear();
    String yearS = (year!=0)?""+year:"";
    String accountStr = null;
    for(int jj=0; jj<beDV.size(); jj++) {
      if(jj>4) {
        accountStr += " ... ("+beDV.size()+" accounts)";
        break;
      }
      BusEntityData beD = (BusEntityData) beDV.get(jj);
      if(accountStr==null) accountStr = beD.getShortDesc();
      else accountStr += ", "+beD.getShortDesc();
    }
%>
    <tr>
        <td><%=accountStr%> </td>
        <td><%=effDateS%></td>
        <td><%=yearS%></td>
        <td><%=fcV.getFiscalCalender().getPeriodCd()%></td>
        <%for (int j = 0, i = 1; i <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fcV); i++, j++) {%>
        <td><%=FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), i)%></td>
        <%}%>
    </tr>

<% } %>
  </table>


<table ID=677 width="769" class="results">

<tr><td>
  <b> Cost Centers </b><br>
</td>
<td>
<logic:present name="STORE_ADMIN_CATALOG_FORM" property="costCentersFound">

<html:form styleId="678" action="/storeportal/storecostcenter.do">
<input type="hidden" name="action" value="setFreightCostCenter" >
<table ID=679 class="results" cellpadding="3">
<tr align=left>
<td class="tableheader" align='center'>Cost Center&nbsp;Id</td>
<td class="tableheader" align='center'>Name</td>
<td class="tableheader" align='center'>Status</td>
<td class="tableheader" align='center'>Accept Freight</td>
<td class="tableheader" align='center'>Sales Tax Allocation</td>
<logic:equal name="STORE_ADMIN_CATALOG_FORM" property="displayBudgetThreshold" value="true">
    <td class="tableheader" align='center'>Budget Threshold (%)</td>
</logic:equal>
</tr>


<tr><td colspan="3">&nbsp;</td><td></td>
<logic:iterate indexId="idx" id="arrele" name="STORE_ADMIN_CATALOG_FORM" property="costCentersFound">
<tr>
<td align='center'><bean:write name="arrele" property="costCenterId"/></td>
<td align='center'>
<bean:define id="eleid" name="arrele" property="costCenterId"/>
<a ID=680 href="storecostcentdet.do?action=costcenterdetail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="shortDesc"/>
</a>
</td>
<td align='center'><bean:write name="arrele" property="costCenterStatusCd"/></td>
<td align="center"><html:radio property="freightCostCenterId" onclick ="document.forms['STORE_ADMIN_CATALOG_FORM'].submit()" value="<%=eleid.toString()%>"/></td>
<td align="center">
	<%String prop = "costCentersFound["+idx+"].costCenterTaxType";%>
	<html:select  property="<%=prop%>" onchange="document.forms['STORE_ADMIN_CATALOG_FORM'].submit()">
		<html:option value="<%=RefCodeNames.COST_CENTER_TAX_TYPE.DONT_ALLOCATE_SALES_TAX%>">Don't allocate sales tax</html:option>
		<html:option value="<%=RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER%>">Master sales tax cost center</html:option>
		<html:option value="<%=RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX%>">Allocate Product Sales Tax</html:option>
	</html:select>
</td>
<logic:equal name="STORE_ADMIN_CATALOG_FORM" property="displayBudgetThreshold" value="true">
    <%prop = "budgetThreshold[" + idx + "]"; %>
    <td align="center"><html:text property="<%=prop%>" /></td>
</logic:equal>
</tr>
</logic:iterate>
<tr>
<td align='center'>&nbsp;</td>
<td align='center'>None</td>
<td align='center'>&nbsp;</td>
<td align="center"><html:radio property="freightCostCenterId" onclick="document.forms['STORE_ADMIN_CATALOG_FORM'].submit()" value="0"/></td>
<td align="center">&nbsp;</td>
<logic:equal name="STORE_ADMIN_CATALOG_FORM" property="displayBudgetThreshold" value="true">
    <td align="center">
        <html:submit onclick="document.forms['STORE_ADMIN_CATALOG_FORM'].elements['action'].value = 'updateBudgetThreshold';">
        Update Budget Threshold
        </html:submit>
   </td>
 </logic:equal>
</tr>

</table>
</html:form>
</logic:present>
</td>
</tr>
</table>
<p>
<logic:present name="CategoryToCostCenter.found.vector">
<table ID=681 width="769" class="results">
<tr>
<td><b>Category</b></td>
<td><b>Current Cost Center</b></td>
<td><b>New Cost Center</b></td>
</tr>


<logic:iterate id="cctoc" name="CategoryToCostCenter.found.vector" indexId="mainidx" >
<tr <% if (( mainidx.intValue() % 2 ) == 0 ) { %>class="rowa"<% } else { %>class="rowb"<% } %> >
<form ID=682 name="FFF<%= mainidx.intValue() %>" action="storecostcenter.do" method=POST>
<td><bean:write name="cctoc" property="categoryName"/></td>
<bean:define id="currentccid" name="cctoc" property="costCenterId"
  type="java.lang.Integer" />
<td>
<% String displayStr = "--None--"; 

%>

<logic:iterate id="ccopt" name="STORE_ADMIN_CATALOG_FORM" property="costCentersFound">
<bean:define id="thisccid" name="ccopt" property="costCenterId" type="java.lang.Integer" />
<% if ( thisccid.intValue() == currentccid.intValue() ) {  %> 
<bean:write name="ccopt" property="shortDesc"/>
<% 
displayStr = ""; } 
%> 
</logic:iterate>

<%= displayStr %>
</td>

<td>
<select name="newCostCenter" onchange="javascript: { document.FFF<%= mainidx.intValue() %>.submit();}">
<option value="0" <% if ( currentccid.intValue() == 0 ) { %> selected="y" <% } %> >--None--</option>
<logic:iterate id="ccopt" name="STORE_ADMIN_CATALOG_FORM" property="costCentersFound">
  <bean:define id="thisccid" name="ccopt" property="costCenterId" type="java.lang.Integer" />
  <bean:define id="thisccstatus" name="ccopt" property="costCenterStatusCd" type="java.lang.String" />
  <% if(!"INACTIVE".equals(thisccstatus)) {%> 
  <option <% if ( thisccid.intValue() == currentccid.intValue() ) {  %> selected="y" <% } %> value="<%= thisccid %>" >
		<bean:write name="ccopt" property="shortDesc"/> 
  </option>
  <% } %>         
</logic:iterate>
</select>
<logic:present name="cctoc" property="categoryName">
<bean:define id="catname" name="cctoc" property="categoryName"/>
<input type="hidden" name="categoryName" value="<%= catname %>" >
<input type="hidden" name="action" value="setCategoryCostCenter" >
</logic:present>
</td>
</form>
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
<a ID=683 href="storecostcenter.do?action=reset_item_cost_centers">
[Reset Item Cost Centers.]</a>
</ul></p>
<table ID=684 width="769" class="results">


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
<% } else { %>
<table ID=685 border="0"cellpadding="2" cellspacing="1" width="769"  bgcolor="#cccccc">
  <tr><td class='tableheader'>No Budget Periods Exist</td>
  </tr>
</table>
<% } %>

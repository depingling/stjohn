<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.util.FiscalCalendarUtility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript1.2">
<!--
function Submit (val) {
 dml=document.forms; 
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action' && 
        dml[i].elements[j].value=='BBBBBBB') {
      //alert('action.value='+dml[i].elements[j].value+' -> '+val);
      dml[i].elements[j].value=val;
      dml[i].submit();
    }
  }
 }
}
-->
</script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="account" type="java.lang.String" toScope="session"/>
<bean:define id="theForm"
  name="STORE_ADMIN_CATALOG_FORM"
  type="com.cleanwise.view.forms.StoreCatalogMgrForm"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>


<body>

<% 
  FiscalCalenderView fcV = theForm.getFiscalCalenderView();
%>


<div class="text">



<table ID=668 width="769"  class="mainbody">
<html:form styleId="669" name="STORE_ADMIN_CATALOG_FORM" action="storeportal/storecostcentdet"
scope="session" type="com.cleanwise.view.forms.StoreCatalogMgrForm">

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

<tr>
<tr>
<td colspan='4'><b>Account:</b>
&nbsp;
<html:select name="STORE_ADMIN_CATALOG_FORM" property="budgetAccountIdInp" onchange="Submit('changeBudgetAccount');">
<%
  BusEntityDataVector accounts = theForm.getBudgetAccounts();
  for(Iterator iter= accounts.iterator(); iter.hasNext(); ) {
    BusEntityData beD = (BusEntityData) iter.next();
    String accountIdS = ""+beD.getBusEntityId();
    String shortDesc = beD.getShortDesc();
%>
  <html:option value="<%=accountIdS%>"><%=shortDesc%></html:option>
<% } %>
<html:option value="0">&nbsp;</html:option>
</html:select>
</td>
</tr>
<% 
   if(fcV!=null) {
     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
     Date effDate = fcV.getFiscalCalender().getEffDate();
     String effDateS = (effDate!=null)? sdf.format(effDate):"";
     int year =fcV.getFiscalCalender().getFiscalYear();
     String yearS = (year!=0)?""+year:"";
%>
<tr><td colspan="4">
<table ID=670 border="0"cellpadding="2" cellspacing="1" width="769"  bgcolor="#cccccc">
  <tr><td class='tableheader' colspan='5' align='center'>Budget Periods</td>
  </tr>
  <tr>
  <td class='tableheader'>Effective Date</td>
  <td class='tableheader' align='center'>Year</td>
  <td class='tableheader' align='center'>Type</td>
  <td class='tableheader' align='center'>Period Start Dates</td>
  </tr>

  <tr>
   <td><%=effDateS%></td>
  <td><%=yearS%></td>
  <td><%=fcV.getFiscalCalender().getPeriodCd()%></td>
  <td><table><tr>
          <%for (int j = 0, i = 1; i <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fcV); i++, j++) {%>
          <td><%=FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), i)%></td>
          <%}%>
  </tr></table>
  </td>
  </tr>
  </table>
</td></tr>
<% } %>

<tr>
<td colspan='4'><b>Cost Center&nbsp;Id:</b>&nbsp;
<bean:write name="STORE_ADMIN_CATALOG_FORM" property="costCenter.costCenterId"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<b>Name:</b>&nbsp;
<html:text name="STORE_ADMIN_CATALOG_FORM" property="costCenter.shortDesc" maxlength= "30" size="30"/>
<span class="reqind">*</span>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<b>Status:</b>&nbsp;
<html:select name="STORE_ADMIN_CATALOG_FORM" property="costCenter.costCenterStatusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Account.status.vector" property="value" />
</html:select>
</td>
</tr>


<tr>
<td><b>Cost Center Type:</b></td>
<td>
<html:select name="STORE_ADMIN_CATALOG_FORM" property="costCenter.costCenterTypeCd" onchange="Submit('changeBudgetType');">
    <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
    <html:option value="<%=RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET%>"><%=RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET%></html:option>
    <html:option value="<%=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET%>"><%=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET%></html:option>
</html:select>
<span class="reqind">*</span>
</td>
<td colspan='2'>&nbsp;</td>
</tr>

<tr>
<td colspan=4 align=center>

<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
<input type="button" onclick="document.location.href='<%=request.getContextPath()%>/storeportal/storecostcentdet.do?action=costcenterdetail&searchType=id&searchField=<%=theForm.getCostCenter().getCostCenterId()%>';" value="<app:storeMessage  key="admin.button.reset"/>"/>
<logic:notEqual name="STORE_ADMIN_CATALOG_FORM" property="costCenter.costCenterId" value="0">
<html:submit property="action">
<app:storeMessage  key="global.action.label.delete"/>
</html:submit>
</logic:notEqual>

</td>
</tr>
</table>

<logic:equal name="STORE_ADMIN_CATALOG_FORM" property="costCenter.costCenterTypeCd" value="<%=RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET%>">
	<!--///////////////  begin site search form /////////////-->

<logic:notEqual name="STORE_ADMIN_CATALOG_FORM" property="costCenter.costCenterId" value="0">
<div class="text">
<br>
  <table ID=671 width="769" cellspacing="0" border="0" class="mainbody">
<tr>
<td class="largeheader" rowspan=3>Site<br> Budgets</td>
<td align="right"><b>Find Site</b></td>
<td>
<html:text name="STORE_ADMIN_CATALOG_FORM" property="searchField"/>
<html:radio name="STORE_ADMIN_CATALOG_FORM" property="searchType" 
    value="id" />  ID
<html:radio name="STORE_ADMIN_CATALOG_FORM" property="searchType" 
    value="nameBegins" />  Name(starts with)
<html:radio name="STORE_ADMIN_CATALOG_FORM" property="searchType" 
    value="nameContains" />  Name(contains)
</td>
</tr>

<tr> 

<td align="right"><b>City</b></td>
<td><html:text name="STORE_ADMIN_CATALOG_FORM" property="city"/> </td>
</tr>

<tr> 

<td align="right"><b>State</b></td>
<td><html:text name="STORE_ADMIN_CATALOG_FORM" property="state"/> 
	<html:button property="action"  onclick="Submit('searchSites');">
		<app:storeMessage  key="global.action.label.search"/>
	</html:button>
	<html:button property="action" onclick="Submit('allSites');">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:button>
    </td>
  </tr>
</table>

</div>
<!-- Display Sites -->

<logic:present name="STORE_ADMIN_CATALOG_FORM" property="siteBudgetList">
<bean:size id="sitesfound" name="STORE_ADMIN_CATALOG_FORM" property="siteBudgetList"/>
Search result count: <bean:write name="sitesfound"/>

<table ID=672 width="769"  class="results">
<tr align=left>
<td>Site Id</td><td>Site Name</td><td>City</td><td>State</td>
<td>Budget</td>
</tr>
<logic:iterate id="budgetsele" name="STORE_ADMIN_CATALOG_FORM" indexId="i" property="siteBudgetList">
<% if (( i.intValue() % 2 ) == 0 ) { %>
<tr class="rowa">
<% } else { %>
<tr class="rowb">
<% } %>
<td><bean:write name="budgetsele" property="id"/></td>
<td><bean:write name="budgetsele" property="name"/></td>
<td><bean:write name="budgetsele" property="city"/></td>
<td><bean:write name="budgetsele" property="state"/></td>
<!-- ///////////////////////////// -->
<td>
	<table ID=673>
	<tr>
        <td><table>
            <tr>
                <%for (int j = 0, p = 1; p <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fcV); p++, j++) {%>
                <%if (j % 7 == 0) {%></tr><tr><%}%>
                <% if (Utility.isSet(FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), p))) {%>
                <td><%=FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), p)%>
                    <html:text size="5" property='<%= "siteBudget[" + i + "].budgetAmount["+p+"]"%>'/>
                </td>
                <% } %>
                <% } %>
            </tr>
        </table>
       </td>
	</tr>
	</table>
	</td>
<!-- ///////////////////////////// -->

</tr>
</logic:iterate>
</table>
</logic:present>
</logic:notEqual>
</logic:equal>
<!-- ///////// -->
<logic:equal name="STORE_ADMIN_CATALOG_FORM"
             property="costCenter.costCenterTypeCd"
            value="<%=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET%>">

    <table ID=674>
        <tr>
            <td valign="top"><b>Amounts By Period:</b></td>
            <td><table> <tr><%for (int j = 0, i = 1; i <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fcV); i++, j++) {%>
                <%if (j % 7 == 0){%></tr><tr><%}%>
                <% if (Utility.isSet(FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), i))) {%>
                <td><%=FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), i)%><html:text size="5" property='<%= "budgetAmounts["+i+"]"%>'/></td>
                <% } %>
                <% } %>
    </tr>
    </table></td></tr>
	</table>
</logic:equal>

<html:submit property="action">
<app:storeMessage  key="costcenter.button.savebudgets"/>
</html:submit>


<!-- End Display Sites -->
  <html:hidden  property="action" value="BBBBBBB"/>
</html:form>
</div>

</body>

</html:html>
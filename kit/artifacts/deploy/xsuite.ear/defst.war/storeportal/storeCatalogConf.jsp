<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="theForm" name="STORE_ADMIN_CATALOG_FORM" type="com.cleanwise.view.forms.StoreCatalogMgrForm"/>
<%
  CatalogData catalogD = theForm.getCatalogDetail();
  int catalogId = catalogD.getCatalogId();
  String action = theForm.getAction();
%>
<bean:define id="masterCatalogId" name="STORE_ADMIN_CATALOG_FORM" property="masterCatalogId" />
<bean:define id="Location" value="catalog" type="java.lang.String" toScope="session"/>

<%
com.cleanwise.view.forms.StoreCatalogMgrForm catForm =
  (com.cleanwise.view.forms.StoreCatalogMgrForm)
    session.getAttribute("STORE_ADMIN_CATALOG_FORM");
int thisCatalogId = catForm.getCatalogDetail().getCatalogId();
%>

<html:hidden name="STORE_ADMIN_CATALOG_FORM" property="catalogId"
  value="<%= String.valueOf(thisCatalogId) %>" />

<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.STORE_ADMIN_CATALOG_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectIds') {
     dml.elements[i].checked=val;
   }
 }
}
function clearRadioButton() {
 dml=document.STORE_ADMIN_CATALOG_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectedMainDistributorId') {
     dml.elements[i].checked=0;
   }
 }
}
//-->

</script>

<div  bgcolor="#cccccc">

<jsp:include flush='true' page="locateStoreCatalog.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/storecatalogconf.do" />
   <jsp:param name="jspFormName" 	value="STORE_ADMIN_CATALOG_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="confCatalogFilter"/>
</jsp:include>

<table ID=548 border="0"cellpadding="2" cellspacing="1" width="769"  bgcolor="#cccccc">
  <tr>
                <td><b>Catalog ID:</b>
                </td>
                <td><%=catalogId%></td>
                <td> <b>Catalog Name:</b> </td>
                <td>
                  <bean:write name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.shortDesc" />
                  </td>
                  <td colspan='2'>&nbsp;</td>
              </tr>
              <tr>
                <td> <b>Catalog Type:</b> </td>
                <td>
                  <bean:write name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogTypeCd"/>
                </td>
                <td><b>Catalog Status: </b> </td>
                <td>
                  <bean:write name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogStatusCd" filter='true'/>
                </td>

                <td> <b>Master Catalog Id: </b> </td>
                <td> <bean:write name="STORE_ADMIN_CATALOG_FORM" property="masterCatalogId" filter="true"/>
                </td>
                <td> <b>&nbsp;</b> </td>
                <td>&nbsp;
                </td>
                </tr>



</table>
</div>
<!-- //////////////////////// -->
<div class="text">

<html:form styleId="549" name="STORE_ADMIN_CATALOG_FORM" action="/storeportal/storecatalogconf.do"
scope="session" type="com.cleanwise.view.forms.StoreCatalogMgrForm">

<table ID=550>
  <tr> <td><b>Find</b></td>
       <td>
          <html:select name="STORE_ADMIN_CATALOG_FORM" property="confType" onchange="document.forms[0].submit();">
             <html:option value="Accounts">Accounts</html:option>
             <html:option value="Distributors">Distributors</html:option>
             <!-- html:option value="Items" Item Distributors /html:option -->
             <html:option value="Sites">Sites</html:option>
             <html:option value="Users">Users</html:option>
          </html:select>
       </td>
  </tr>
<% if( !Utility.isSet(theForm.getConfType()) ||
       "Accounts".equals(theForm.getConfType()) ||
       "Distributors".equals(theForm.getConfType())) { %>
      <tr>
      <td>&nbsp;</td>
       <td colspan="4"> <b>Filter Catalog:&nbsp;</b>
<%
       boolean oneCatalogFl = true;
       CatalogData filterCatalogD = null;
       CatalogDataVector filterCatalogDV = theForm.getConfCatalogFilter();
       if(filterCatalogDV!=null && filterCatalogDV.size()>0) {
         filterCatalogD = (CatalogData) filterCatalogDV.get(0);
%>
      <%=filterCatalogD.getShortDesc()%>
  <html:submit property="action" value="Clear Catalog Filter" styleClass='text'/>
       <% } %>
  <html:submit property="action" value="Locate Catalog" styleClass='text'/>
  </td></tr>

  <tr>
      <td>&nbsp;</td>
       <td colspan="4">
          <html:text name="STORE_ADMIN_CATALOG_FORM" property="confSearchField"/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <html:radio name="STORE_ADMIN_CATALOG_FORM" property="confSearchType" value="nameBegins" />
          Name(starts with)
          &nbsp;&nbsp;
          <html:radio name="STORE_ADMIN_CATALOG_FORM" property="confSearchType" value="nameContains" />
          Name(contains)
         </td>
  </tr>

  <tr> <td colspan="1">&nbsp;</td>
       <td colspan="4">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Show Configured Only<html:checkbox name="STORE_ADMIN_CATALOG_FORM"  property="confShowConfguredOnlyFl"/>

        <logic:equal name="STORE_ADMIN_CATALOG_FORM" property="confType" value="Distributors">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Show Inactive<html:checkbox name="STORE_ADMIN_CATALOG_FORM" property="confShowInactiveFl"/>
        </logic:equal>
        <!-- html:submit property="action"
                bean:message key="admin.button.viewall"/
        /html:submit -->
     </td>
  </tr>
<% } %>


<logic:equal name="STORE_ADMIN_CATALOG_FORM" property="confType" value="Sites">
      <tr>
      <td>&nbsp;</td>
       <td colspan="4"> <b>Filter Catalog:&nbsp;</b>
<%
       boolean oneCatalogFl = true;
       CatalogData filterCatalogD = null;
       CatalogDataVector filterCatalogDV = theForm.getConfCatalogFilter();
       if(filterCatalogDV!=null && filterCatalogDV.size()>0) {
         filterCatalogD = (CatalogData) filterCatalogDV.get(0);
%>
      <%=filterCatalogD.getShortDesc()%>
  <html:submit property="action" value="Clear Catalog Filter" styleClass='text'/>
       <% } %>
  <html:submit property="action" value="Locate Catalog" styleClass='text'/>
  </td></tr>

<tr>
<td align="right"><b>Find Site</b></td>
<td>
<html:text name="STORE_ADMIN_CATALOG_FORM" property="confSearchField"/>
<html:radio name="STORE_ADMIN_CATALOG_FORM" property="confSearchType"
    value="id" />  ID
<html:radio name="STORE_ADMIN_CATALOG_FORM" property="confSearchType"
    value="nameBegins" />  Name(starts with)
<html:radio name="STORE_ADMIN_CATALOG_FORM" property="confSearchType"
    value="nameContains" />  Name(contains)
</td>
</tr>

<tr>
<td align="right"><b>City</b></td>
<td colspan="3"><html:text name="STORE_ADMIN_CATALOG_FORM" property="confCity"/>
&nbsp;&nbsp;&nbsp;&nbsp;<b>County</b>&nbsp;&nbsp;
<html:text name="STORE_ADMIN_CATALOG_FORM" property="confCounty"/>
</td>

</tr>

<tr>
<td align="right"><b>State</b></td>
<td colspan="3"><html:text name="STORE_ADMIN_CATALOG_FORM" property="confState"/>
&nbsp;&nbsp;&nbsp;&nbsp;<b>Zip Code</b>
<html:text name="STORE_ADMIN_CATALOG_FORM" property="confZipcode"/>
</td>
</tr>

<tr>
       <td colspan="4">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Show Configured Only<html:checkbox name="STORE_ADMIN_CATALOG_FORM"  property="confShowConfguredOnlyFl"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Move Sites When Assign<html:checkbox name="STORE_ADMIN_CATALOG_FORM"  property="confMoveSitesFl"/>

        <!-- html:submit property="action"
                bean:message key="admin.button.viewall"/
        /html:submit -->
     </td>
  </tr>
</logic:equal>


  <tr><td colspan="4">&nbsp;</td>
  </tr>

</table>

<logic:present name="catalog.distributors.vector">
<bean:size id="rescount"  name="catalog.distributors.vector"/>
Search result count:  <bean:write name="rescount" />

<logic:greaterThan name="rescount" value="0">

<div style="width: 769; text-align: right;">
<table ID=551>

<tr><td><a ID=552 href="javascript:clearRadioButton()">[&nbsp;Clear]</a></td>
<td><a ID=553 href="javascript:SetChecked(1)">[Check&nbsp;All]</a><a ID=554 href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br></td></tr>
</tr>
 </table>
</div>

<table ID=555 width="769" border="0" class="results">
<tr align=left>
<td><a ID=556 class="tableheader" href="storecatalogconf.do?action=sort&configType=Distributors&sortField=id">Distributor Id</td>
<td><a ID=557 class="tableheader" href="storecatalogconf.do?action=sort&configType=Distributors&sortField=name">Name</td>
<td><a ID=558 class="tableheader" href="storecatalogconf.do?action=sort&configType=Distributors&sortField=address">Address</td>
<td><a ID=559 class="tableheader" href="storecatalogconf.do?action=sort&configType=Distributors&sortField=city">City</td>
<td><a ID=560 class="tableheader" href="storecatalogconf.do?action=sort&configType=Distributors&sortField=state">State</td>
<td><a ID=561 class="tableheader" href="storecatalogconf.do?action=sort&configType=Distributors&sortField=zipcode">Zip Code</td>
<td><a ID=562 class="tableheader" href="storecatalogconf.do?action=sort&configType=Distributors&sortField=status">Status</td>
<td class="tableheader"> Main Distributor</td>
<td class="tableheader">Select</td>
</tr>

<logic:iterate id="arrele" name="catalog.distributors.vector">
  <tr>
    <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
    <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
    <td><bean:write name="arrele" property="busEntity.shortDesc"/></td>
    <td><bean:write name="arrele" property="primaryAddress.address1"/></td>
    <td><bean:write name="arrele" property="primaryAddress.city"/></td>
    <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
    <td><bean:write name="arrele" property="primaryAddress.postalCode"/></td>
    <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
    <td align="center"><html:radio name="STORE_ADMIN_CATALOG_FORM" property="selectedMainDistributorId" value="<%=String.valueOf(eleid)%>"/></td>
<td><html:multibox name="STORE_ADMIN_CATALOG_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
<html:hidden name="STORE_ADMIN_CATALOG_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />
  </tr>
</logic:iterate>
<td colspan="9" align="right" >

<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
</td>
</table>
</logic:greaterThan>
</logic:present>

<logic:present name="Account.search.result">
<bean:size id="rescount"  name="Account.search.result"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<div style="width: 769; text-align: right;">
<a ID=563 href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a ID=564 href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
</div>
<table ID=565 width="769" border="0" class="results">
<tr align=left>
<td><a ID=566 class="tableheader" href="storecatalogconf.do?action=sort&configType=Accounts&sortField=id">Account Id</td>
<td><a ID=567 class="tableheader" href="storecatalogconf.do?action=sort&configType=Accounts&sortField=name">Name</td>
<td><a ID=568 class="tableheader" href="storecatalogconf.do?action=sort&configType=Accounts&sortField=address">Address</td>
<td><a ID=569 class="tableheader" href="storecatalogconf.do?action=sort&configType=Accounts&sortField=city">City</td>
<td><a ID=570 class="tableheader" href="storecatalogconf.do?action=sort&configType=Accounts&sortField=state">State</td>
<td><a ID=571 class="tableheader" href="storecatalogconf.do?action=sort&configType=Accounts&sortField=zipcode">Zip Code</td>
<td><a ID=572 class="tableheader" href="storecatalogconf.do?action=sort&configType=Accounts&sortField=status">Status</td>
<td class="tableheader">Select</td>
</tr>

<logic:iterate id="arrele" name="Account.search.result">
  <bean:define id="eleid" name="arrele" property="accountId"/>
  <tr>
    <td><bean:write name="arrele" property="accountId"/></td>
    <td><bean:write name="arrele" property="shortDesc"/></td>
    <td><bean:write name="arrele" property="address1"/></td>
    <td><bean:write name="arrele" property="city"/></td>
    <td><bean:write name="arrele" property="stateProvinceCd"/></td>
    <td><bean:write name="arrele" property="postalCode"/></td>
    <td><bean:write name="arrele" property="busEntityStatusCd"/></td>
<td><html:multibox name="STORE_ADMIN_CATALOG_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
<html:hidden name="STORE_ADMIN_CATALOG_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />
  </tr>

</logic:iterate>
<td colspan="7"></td>
<td>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
</td>
</table>
</logic:greaterThan>
</logic:present>

<logic:present name="catalog.sites.vector">
<bean:size id="rescount"  name="catalog.sites.vector"/>
Search result count:  <bean:write name="rescount" />
<% if ( rescount.intValue() >= Constants.MAX_SITES_TO_RETURN ){ %> &nbsp;(request limit) <%}%>
<logic:greaterThan name="rescount" value="0">
<div style="width: 769; text-align: right;">
<a ID=573 href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a ID=574 href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
</div>

<table ID=575 width="769" border="0" class="results">
<tr align=left>

<td><a ID=576 class="tableheader" href="storecatalogconf.do?action=sort&configType=Sites&sortField=id">Site Id</td>
<td><a ID=577 class="tableheader" href="storecatalogconf.do?action=sort&configType=Sites&sortField=account">Account Name</td>
<td><a ID=578 class="tableheader" href="storecatalogconf.do?action=sort&configType=Sites&sortField=name">Site Name</td>
<td><a ID=579 class="tableheader" href="storecatalogconf.do?action=sort&configType=Sites&sortField=address">Street Address</td>
<td><a ID=580 class="tableheader" href="storecatalogconf.do?action=sort&configType=Sites&sortField=city">City</td>
<td><a ID=581 class="tableheader" href="storecatalogconf.do?action=sort&configType=Sites&sortField=state">State</td>
<td><a ID=582 class="tableheader" href="storecatalogconf.do?action=sort&configType=Sites&sortField=county">County</td>
<td><a ID=583 class="tableheader" href="storecatalogconf.do?action=sort&configType=Sites&sortField=zipcode">Zip Code</td>
<td><a ID=584 class="tableheader" href="storecatalogconf.do?action=sort&configType=Sites&sortField=status">Status</td>
<td class="tableheader">Select</td>
</tr>

<% int sidx = 0; %>
<logic:iterate id="arrele" name="catalog.sites.vector">
<% if (( sidx % 2 ) == 0 ) { %> <tr class="rowa">
<% } else { %> <tr class="rowb"> <% } sidx++; %>

    <bean:define id="eleid" name="arrele" property="id"/>
    <td><bean:write name="arrele" property="id"/></td>
    <td><bean:write name="arrele" property="accountName"/></td>
    <td><bean:write name="arrele" property="name"/></td>
    <td><bean:write name="arrele" property="address"/></td>
    <td><bean:write name="arrele" property="city"/></td>
    <td><bean:write name="arrele" property="state"/></td>
    <td><bean:write name="arrele" property="county"/></td>
    <td><bean:write name="arrele" property="postalCode"/></td>
    <td><bean:write name="arrele" property="status"/></td>
<td><html:multibox name="STORE_ADMIN_CATALOG_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
<html:hidden name="STORE_ADMIN_CATALOG_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />
  </tr>

</logic:iterate>
<td colspan="7"></td>
<td>

<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>

</td>
</table>
</logic:greaterThan>
</logic:present>

<logic:present name="Related.users.vector">

<bean:size id="rescount"  name="Related.users.vector"/>
Search result count:  <bean:write name="rescount" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>--- View Only ---</b>
<logic:greaterThan name="rescount" value="0">
<table ID=585 width="769" border="0" class="results">
<tr align=left>
<td><a ID=586 class="tableheader" href="storecatalogconf.do?action=sort&configType=Users&name=Related.users.vector&sortField=id">User&nbsp;Id </a></td>
<td><a ID=587 class="tableheader" href="storecatalogconf.do?action=sort&configType=Users&name=Related.users.vector&sortField=name">Login Name </a></td>
<td><a ID=588 class="tableheader" href="storecatalogconf.do?action=sort&configType=Users&name=Related.users.vector&sortField=firstName">First Name </a></td>
<td><a ID=589 class="tableheader" href="storecatalogconf.do?action=sort&configType=Users&name=Related.users.vector&sortField=lastName">Last Name </a></td>
<td><a ID=590 class="tableheader" href="storecatalogconf.do?action=sort&configType=Users&name=Related.users.vector&sortField=type">User Type Code </a></td>
<td><a ID=591 class="tableheader" href="storecatalogconf.do?action=sort&configType=Users&name=Related.users.vector&sortField=status">Status Code </a></td>
</tr>

<logic:iterate id="arrele" name="Related.users.vector">
<tr>
<td><bean:write name="arrele" property="userId"/></td>
<td><bean:write name="arrele" property="userName"/></td>
<td><bean:write name="arrele" property="firstName"/></td>
<td><bean:write name="arrele" property="lastName"/></td>
<td><bean:write name="arrele" property="userTypeCd"/></td>
<td><bean:write name="arrele" property="userStatusCd"/></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>

</html:form>

</div>

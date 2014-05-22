<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.CATALOG_CONFIG_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectIds') {
     dml.elements[i].checked=val;
   }
 }
}

//-->
</script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="catalogid" name="CATALOG_DETAIL_FORM" property="detail.catalogId" />

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Catalog Configuration</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/catalogToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/catalogInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<html:form name="CATALOG_CONFIG_FORM" action="adminportal/catalogConfig.do"
 scope="session" type="com.cleanwise.view.forms.CatalogMgrConfigForm">

<table>
<logic:notEqual name="CATALOG_CONFIG_FORM" property="configType" value="Sites">
  <tr> <td><b>Find</b></td>
       <td>
          <html:select name="CATALOG_CONFIG_FORM" property="configType" onchange="document.forms[0].submit();">
             <html:option value="Accounts">Accounts</html:option>
             <html:option value="Distributors">Distributors</html:option>
             <html:option value="Items">Item Distributors</html:option>
             <html:option value="Sites">Sites</html:option>
          </html:select>
       </td>
       <td colspan="2">
          <html:text name="CATALOG_CONFIG_FORM" property="searchField"/>
       </td>
  </tr>

  <tr> <td colspan="2">&nbsp;</td>
       <td colspan="3">
         <html:radio name="CATALOG_CONFIG_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="CATALOG_CONFIG_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>

  <tr> <td colspan="2">&nbsp;</td>
       <td colspan="3">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.viewall"/>
        </html:submit>
     </td>
  </tr>
</logic:notEqual>


<logic:equal name="CATALOG_CONFIG_FORM" property="configType" value="Sites">
<tr>
  <td>
          <html:select name="CATALOG_CONFIG_FORM" property="configType" onchange="document.forms[0].submit();">
             <html:option value="Accounts">Accounts</html:option>
             <html:option value="Distributors">Distributors</html:option>
             <html:option value="Items">Item Distributors</html:option>
             <html:option value="Sites">Sites</html:option>
          </html:select>
       </td>
</tr>
<tr>
<td align="right"><b>Find Site</b></td>
<td>
<html:text name="CATALOG_CONFIG_FORM" property="searchField"/>
<html:radio name="CATALOG_CONFIG_FORM" property="searchType"
    value="id" />  ID
<html:radio name="CATALOG_CONFIG_FORM" property="searchType"
    value="nameBegins" />  Name(starts with)
<html:radio name="CATALOG_CONFIG_FORM" property="searchType"
    value="nameContains" />  Name(contains)
</td>
</tr>

<tr>
<td align="right"><b>City</b></td>
<td colspan="3"><html:text name="CATALOG_CONFIG_FORM" property="city"/>
&nbsp;&nbsp;&nbsp;&nbsp;<b>County</b>&nbsp;&nbsp;
<html:text name="CATALOG_CONFIG_FORM" property="county"/>
</td>

</tr>

<tr>
<td align="right"><b>State</b></td>
<td colspan="3"><html:text name="CATALOG_CONFIG_FORM" property="state"/>
&nbsp;&nbsp;&nbsp;&nbsp;<b>Zip Code</b>
<html:text name="CATALOG_CONFIG_FORM" property="zipcode"/>
</td>
</tr>

<tr>
       <td colspan="3">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.viewall"/>
        </html:submit>
     </td>
  </tr>
</logic:equal>


  <tr><td colspan="4">&nbsp;</td>
  </tr>

</table>

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>

<logic:present name="catalog.item.distributors.vector">
<bean:size id="rescount"  name="catalog.item.distributors.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Items&sortField=id">Distributor Id</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Items&sortField=name">Name</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Items&sortField=address">Address</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Items&sortField=city">City</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Items&sortField=state">State</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Items&sortField=zipcode">Zip Code</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Items&sortField=status">Status</td>
<td class="tableheader">Select</td>
</tr>

<logic:iterate id="arrele" name="catalog.item.distributors.vector">
  <tr>
    <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
    <td>
      <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
      <a href="distmgr.do?action=distdetail&searchType=id&searchField=<%=eleid%>">
      <bean:write name="arrele" property="busEntity.shortDesc"/>
      </a>
    </td>
    <td><bean:write name="arrele" property="primaryAddress.address1"/></td>
    <td><bean:write name="arrele" property="primaryAddress.city"/></td>
    <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
    <td><bean:write name="arrele" property="primaryAddress.postalCode"/></td>
    <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
    <td>
      <html:radio name="CATALOG_CONFIG_FORM" property="distributorId" value="<%=String.valueOf(eleid)%>"/>
    </td>
  </tr>

</logic:iterate>
<td colspan="8" align="right">
Replace Distributor:
<html:select name="CATALOG_CONFIG_FORM" property="overwriteDistId">
      <html:option value="0">--- Unassigned Items Only ---</html:option>
      <html:option value="-1">--- All Distributors ---</html:option>
<logic:present name="catalog.item.alldistributors.vector">
<logic:iterate id="arrele" name="catalog.item.alldistributors.vector">
   <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
   <html:option value="<%=\"\"+eleid%>"><bean:write name="arrele" property="busEntity.shortDesc"/></html:option>
</logic:iterate>
</logic:present>
</html:select>
<html:submit property="action">
<app:storeMessage  key="admin.button.assigndistributor"/>
</html:submit>
</td>
</tr>

<!-- items could not be moved to new distributor, because the distributor does not carry them -->
<logic:present name="catalog.item.distributor.error">
<bean:size id="itemErrCount" name="catalog.item.distributor.error" />
<tr>
<td colspan="8" align="right">
<table>
<tr><td colspan="3"><b>There are <bean:write name="itemErrCount"/> exception items, which the distributor does not carry</B></TD></TR>
<TR>
<td><b>Sku Num</B></TD>
<td><b>Item Name</B></TD>
<td><b>Item Id</B></TD>
</TR>
<logic:iterate id="arrele" name="catalog.item.distributor.error">
<TR>
<td><bean:write name="arrele" property="skuNum"/></TD>
<td><bean:write name="arrele" property="shortDesc"/></TD>
<td><bean:write name="arrele" property="itemId"/></TD>
</TR>
</logic:iterate>
</table>
</td>
</tr>
</logic:present>

</table>
</logic:greaterThan>
</logic:present>



<logic:present name="catalog.distributors.vector">
<bean:size id="rescount"  name="catalog.distributors.vector"/>
Search result count:  <bean:write name="rescount" />

<logic:greaterThan name="rescount" value="0">

<div style="width: 769; text-align: right;">
<a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
</div>

<table width="769" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Distributors&sortField=id">Distributor Id</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Distributors&sortField=name">Name</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Distributors&sortField=address">Address</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Distributors&sortField=city">City</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Distributors&sortField=state">State</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Distributors&sortField=zipcode">Zip Code</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Distributors&sortField=status">Status</td>
<td class="tableheader">Select</td>
</tr>

<logic:iterate id="arrele" name="catalog.distributors.vector">
  <tr>
    <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
    <td>
      <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
      <a href="distmgr.do?action=distdetail&searchType=id&searchField=<%=eleid%>">
      <bean:write name="arrele" property="busEntity.shortDesc"/>
      </a>
    </td>
    <td><bean:write name="arrele" property="primaryAddress.address1"/></td>
    <td><bean:write name="arrele" property="primaryAddress.city"/></td>
    <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
    <td><bean:write name="arrele" property="primaryAddress.postalCode"/></td>
    <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
<td><html:multibox name="CATALOG_CONFIG_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
<html:hidden name="CATALOG_CONFIG_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />
  </tr>

</logic:iterate>
<td colspan="6"></td>
<td>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
</td>
</table>
</logic:greaterThan>
</logic:present>

<logic:present name="catalog.accounts.vector">
<bean:size id="rescount"  name="catalog.accounts.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Accounts&sortField=id">Account Id</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Accounts&sortField=name">Name</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Accounts&sortField=address">Address</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Accounts&sortField=city">City</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Accounts&sortField=state">State</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Accounts&sortField=zipcode">Zip Code</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Accounts&sortField=type">Type</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Accounts&sortField=status">Status</td>
<td class="tableheader">Select</td>
</tr>

<logic:iterate id="arrele" name="catalog.accounts.vector">
  <tr>
    <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
    <td>
      <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
      <a href="accountmgr.do?action=accountdetail&searchType=id&searchField=<%=eleid%>">
      <bean:write name="arrele" property="busEntity.shortDesc"/>
      </a>
    </td>
    <td><bean:write name="arrele" property="primaryAddress.address1"/></td>
    <td><bean:write name="arrele" property="primaryAddress.city"/></td>
    <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
    <td><bean:write name="arrele" property="primaryAddress.postalCode"/></td>
    <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
    <td><bean:write name="arrele" property="accountType.value"/></td>
<td><html:multibox name="CATALOG_CONFIG_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
<html:hidden name="CATALOG_CONFIG_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />
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

<logic:greaterThan name="rescount" value="0">
<div style="width: 769; text-align: right;">
<a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
</div>

<table width="769" border="0" class="results">
<tr align=left>

<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Sites&sortField=id">Site Id</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Sites&sortField=account">Account Name</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Sites&sortField=name">Site Name</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Sites&sortField=address">Street Address</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Sites&sortField=city">City</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Sites&sortField=state">State</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Sites&sortField=county">County</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Sites&sortField=zipcode">Zip Code</td>
<td><a class="tableheader" href="catalogConfig.do?action=sort&configType=Sites&sortField=status">Status</td>
<td class="tableheader">Select</td>
</tr>

<% int sidx = 0; %>
<logic:iterate id="arrele" name="catalog.sites.vector">
<% if (( sidx % 2 ) == 0 ) { %> <tr class="rowa">
<% } else { %> <tr class="rowb"> <% } sidx++; %>

    <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
    <td><bean:write name="arrele" property="accountBusEntity.shortDesc"/></td>
    <td>
      <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
      <a href="sitemgr.do?action=sitedetail&searchType=id&searchField=<%=eleid%>">
      <bean:write name="arrele" property="busEntity.shortDesc"/>
      </a>
    </td>
    <td><bean:write name="arrele" property="siteAddress.address1"/></td>
    <td><bean:write name="arrele" property="siteAddress.city"/></td>
    <td><bean:write name="arrele" property="siteAddress.stateProvinceCd"/></td>
    <td><bean:write name="arrele" property="siteAddress.countyCd"/></td>
    <td><bean:write name="arrele" property="siteAddress.postalCode"/></td>
    <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
<td><html:multibox name="CATALOG_CONFIG_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
<html:hidden name="CATALOG_CONFIG_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />
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

  <pg:index>
    Result Pages:
    <pg:prev>&nbsp;<a href="<%= pageUrl %>">[<< Prev]</a></pg:prev>
    <pg:pages><%
      if (pageNumber.intValue() < 10) {
        %>&nbsp;<%
      }
      if (pageNumber == pagerPageNumber) {
        %><b><%= pageNumber %></b><%
      } else {
        %><a href="<%= pageUrl %>"><%= pageNumber %></a><%
      }
    %>
    </pg:pages>
    <pg:next>&nbsp;<a href="<%= pageUrl %>">[Next >>]</a></pg:next>
    <br></font>
  </pg:index>
</pg:pager>

</html:form>

</div>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</html:form>
</body>
</html:html>







<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Account Configuration</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
 <div class="text">
<body>

 <jsp:include flush='true' page="storeAccountCtx.jsp"/>

<table ID=471> <tr>
<html:form styleId="472" name="STORE_ACCOUNT_CONFIGURATION_FORM" action="storeportal/storeAccountConfiguration.do"  method="POST" scope="session" type="com.cleanwise.view.forms.StoreAccountConfigurationForm">

<logic:equal name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchForType" value="users">
<tr>
  <td>
    <html:select name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchForType" onchange="document.forms[0].submit();">
      <html:options collection="Account.configuration.options.vector" property="label" labelProperty="value" />
    </html:select>
  </td>
</tr>
<tr>
  <td align="right">
    <b>Search Users:</b>
  </td>
  <td>
    <html:text name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchForName"/>

     <html:radio name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="id" />  ID
     <html:radio name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="nameBegins" />  Name(starts with)
     <html:radio name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="nameContains" />  Name(contains)
  </td>
</tr>

<tr>
  <td align="right"><b>First Name:</b></td>
  <td><html:text name="STORE_ACCOUNT_CONFIGURATION_FORM" property="firstName"/> </td>
</tr>

<tr>
  <td align="right"><b>Last Name:</b></td>
  <td><html:text name="STORE_ACCOUNT_CONFIGURATION_FORM" property="lastName"/> </td>
</tr>

<tr>
  <td align="right"><b>Type:</b></td>
  <td>
    <html:select name="STORE_ACCOUNT_CONFIGURATION_FORM" property="userType" >
      <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
      <html:options  collection="Users.types.vector" property="value" />
    </html:select>
  </td>
</tr>

</logic:equal>

<logic:equal name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchForType" value="sites">
<tr>
  <td>
    <html:select name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchForType" onchange="document.forms[0].submit();">
      <html:options collection="Account.configuration.options.vector" property="label" labelProperty="value" />
    </html:select>
  </td>
</tr>
<tr>
  <td align="right"><b>Find Site</b></td>
<td>
<html:text name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchForName"/>
<html:radio name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchType"
    value="id" />  ID
<html:radio name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchType"
    value="nameBegins" />  Name(starts with)
<html:radio name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchType"
    value="nameContains" />  Name(contains)
</td>
</tr>

<tr>
<td align="right"><b>City</b></td>
<td><html:text name="STORE_ACCOUNT_CONFIGURATION_FORM" property="city"/> </td>
</tr>

<tr>
<td align="right"><b>State</b></td>
<td><html:text name="STORE_ACCOUNT_CONFIGURATION_FORM" property="state"/> </td>
</tr>



</logic:equal>

<logic:notEqual name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchForType" value="users">
<logic:notEqual name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchForType" value="sites">
<td><b>Find Relationships:</b></td>
<td>
<html:text name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchForName" size="10"/>
<html:select name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchForType" onchange="document.forms[0].submit();">
<html:options collection="Account.configuration.options.vector"
property="label"
labelProperty="value"
/>
</html:select>
</td></tr>
  <tr> <td><b>Search By:</b></td>
       <td colspan="3">
         <html:radio name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="STORE_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>
</logic:notEqual>
</logic:notEqual>


<tr>
<td></td>
<td colspan="3">
 <html:submit styleId="search"  property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
  Show Inactive <html:checkbox property="showInactiveFl" value="true" />
</tr>

</html:form>

</table>


<logic:present name="Account.configuration.sites.vector">
<bean:size id="siteres"  name="Account.configuration.sites.vector"/>
Search result count:  <bean:write name="siteres" />
<logic:greaterThan name="siteres" value="0">
<% boolean authorizedStoreMgrTabSite = false;%>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_SITE%>">
<% authorizedStoreMgrTabSite = true;%>
</app:authorizedForFunction>
<table width="<%=Constants.TABLEWIDTH%>"  class="stpTable_sortable" id="ts1">
<thead>
<tr align=left class=stpTH>
<th class="stpTH">Site Id</th>
<th class="stpTH">Name</th>
<th class="stpTH">Street Address</th>
<th class="stpTH">City</th>
<th class="stpTH">State<br>Province</th>
<th class="stpTH">Zip</th>
<th class="stpTH">County</th>
<th class="stpTH">Status</th>
</tr>
 </thead>
<tbody>


    <logic:iterate id="arrele" name="Account.configuration.sites.vector">
        <tr class=stpTD>
            <td class=stpTD><bean:write name="arrele" property="id"/></td>
            <td class=stpTD>
                <bean:define id="eleid" name="arrele" property="id"/>
                <%if(authorizedStoreMgrTabSite){%>
                <a href="sitedet.do?action=sitedetail&searchType=id&searchField=<%=eleid%>">
                    <bean:write name="arrele" property="name"/>
                </a>
                <%}else{%>
                <bean:write name="arrele" property="name"/>
                <%}%>
            </td>
            <td class=stpTD>
                <bean:write name="arrele" property="address"/>
            </td>
            <td class=stpTD>
                <bean:write name="arrele" property="city"/>
            </td>
            <td class=stpTD>
                <bean:write name="arrele" property="state"/>
            </td>
            <td class=stpTD><bean:write name="arrele" property="postalCode"/></td>
            <td class=stpTD><bean:write name="arrele" property="county"/></td>

            <td class=stpTD><bean:write name="arrele" property="status"/></td>
        </tr>

    </logic:iterate>




<tbody>
</table>
</logic:greaterThan>
</logic:present>

<logic:present name="Account.configuration.catalogs.vector">
<bean:size id="catres"  name="Account.configuration.catalogs.vector"/>
Search result count:  <bean:write name="catres" />
<logic:greaterThan name="catres" value="0">
<% boolean authorizedStoreMgrTabCat = false;%>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_CATALOG%>">
<% authorizedStoreMgrTabCat = true;%>
</app:authorizedForFunction>
<table width="<%=Constants.TABLEWIDTH%>"  class="stpTable_sortable" id="ts2">
<thead>
<tr align=left class=stpTH>
<th class="stpTH">Catalog Id</th>
<th class="stpTH">Name</th>
<th class="stpTH">Type</th>
<th class="stpTH">Status</th>
</tr>
</thead>
<tbody>
<logic:iterate id="arrele" name="Account.configuration.catalogs.vector">
<tr class=stpTD>
<td class=stpTD><bean:write name="arrele" property="catalogId"/></td>
<td class=stpTD>
<bean:define id="eleid" name="arrele" property="catalogId"/>
<%if(authorizedStoreMgrTabCat){%>
<a ID=474 href="storecatalogdet.do?action=edit&id=<%=eleid%>">
<bean:write name="arrele" property="shortDesc"/>
</a>
<%}else{%>
    <bean:write name="arrele" property="shortDesc"/>
    <%}%>
</td>
<td class=stpTD><bean:write name="arrele" property="catalogTypeCd"/></td>
<td class=stpTD><bean:write name="arrele" property="catalogStatusCd"/></td>
</tr>
</logic:iterate>
</tbody>
</table>
</logic:greaterThan>
</logic:present>


<logic:present name="Account.configuration.users.vector">
<bean:size id="userres"  name="Account.configuration.users.vector"/>
Search result count:  <bean:write name="userres" />
<logic:greaterThan name="userres" value="0">
<% boolean authorizedStoreMgrTabUser = false;%>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_USER%>">
<% authorizedStoreMgrTabUser = true;%>
</app:authorizedForFunction>
<table width="<%=Constants.TABLEWIDTH%>"  class="stpTable_sortable" id="ts3">
<thead>
<tr class=stpTH align=left>
<th class="stpTH">User Id</th>
<th class="stpTH">Login Name</th>
<th class="stpTH">First Name</th>
<th class="stpTH">Last Name</th>
<th class="stpTH">Type</th>
<th class="stpTH">Status</th>
</tr>
</thead>
<tbody>
<logic:iterate id="arrele" name="Account.configuration.users.vector">
<tr class=stpTD >
<td class=stpTD><bean:write name="arrele" property="userId"/></td>
<td class=stpTD>
<bean:define id="eleid" name="arrele" property="userId"/>
 <bean:define id="eletype" name="arrele" property="userTypeCd"/>
 <%if(authorizedStoreMgrTabUser){%>
<a ID=475 href="userdet.do?action=userdetail&userId=<%=eleid%>&type=<%=eletype%>">
<bean:write name="arrele" property="userName"/>
</a>
<%}else{%>
    <bean:write name="arrele" property="userName"/>
    <%}%>
</td>
<td class=stpTD><bean:write name="arrele" property="firstName"/></td>
<td class=stpTD><bean:write name="arrele" property="lastName"/></td>
<td class=stpTD><bean:write name="arrele" property="userTypeCd"/></td>
<td class=stpTD><bean:write name="arrele" property="userStatusCd"/></td>
</tr>
</logic:iterate>
 </tbody>
</table>
</logic:greaterThan>
</logic:present>





<logic:present name="Account.configuration.guides.vector">
<bean:size id="ogres"  name="Account.configuration.guides.vector"/>
Search result count:  <bean:write name="ogres" />
<logic:greaterThan name="ogres" value="0">
<table width="<%=Constants.TABLEWIDTH%>"  class="stpTable_sortable" id="ts4">
<thead>
<tr class=stpTH>
<th class="stpTH">Order Guide Id</th>
<th class="stpTH">Name</th>
<th class="stpTH">Catalog Name</th>
<th class="stpTH">Status</th>
</tr>
</thead>
<tbody>
<logic:iterate id="ogele" name="Account.configuration.guides.vector">
<tr class=stpTD>
<td class=stpTD><bean:write name="ogele" property="orderGuideId"/></td>
<td class=stpTD>
<bean:define id="ogid" name="ogele" property="orderGuideId"/>
<bean:write name="ogele" property="orderGuideName"/>

</td>
<td class=stpTD><bean:write name="ogele" property="catalogName"/></td>
<td class=stpTD><bean:write name="ogele" property="status"/></td>
</tr>
</logic:iterate>
</tbody>
</table>
</logic:greaterThan>
</logic:present>

</body>
</div>
</html:html>





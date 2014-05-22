<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>


<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: User Distributors</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<html:form action="/adminportal/userDistributorConfig.do">

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admUserToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/userInfo.jsp"/>

<div class="text">
<font color=red>
<html:errors/>
</font>
</center>

<html:form name="USER_DISTRIBUTOR_CONFIG_FORM" action="adminportal/usermgr.do"
 scope="session" type="com.cleanwise.view.forms.UserMgrDistributorConfigForm">

<table>
  <tr> <td align="right"><b>Find Distributor</b></td>
    <td>
      <html:text name="USER_DISTRIBUTOR_CONFIG_FORM" property="searchField"/>
      <html:radio name="USER_DISTRIBUTOR_CONFIG_FORM" property="searchType" value="id" />  ID
      <html:radio name="USER_DISTRIBUTOR_CONFIG_FORM" property="searchType" value="nameBegins" />  Name(starts with)
      <html:radio name="USER_DISTRIBUTOR_CONFIG_FORM" property="searchType" value="nameContains" />  Name(contains)
    </td>
  </tr>


  <tr> <td>&nbsp;</td>
       <td colspan="3">
        <html:hidden property="distributorconfig" value="true"/>
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.viewall"/>
        </html:submit>
     </td>
  </tr>

  <tr><td colspan="4">&nbsp;</td>
  </tr>

</table>

<logic:present name="user.distributor.vector">
<bean:size id="rescount"  name="user.distributor.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">



<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="userDistributorConfig.do?action=sort&sortField=id&distributorconfig=true">Distributor&nbsp;Id</td>
<td><a class="tableheader" href="userDistributorConfig.do?action=sort&sortField=erpNum&distributorconfig=true">ERP&nbsp;Num</td>
<td><a class="tableheader" href="userDistributorConfig.do?action=sort&sortField=name&distributorconfig=true">Name</td>
<td><a class="tableheader" href="userDistributorConfig.do?action=sort&sortField=address&distributorconfig=true">Address 1</td>
<td><a class="tableheader" href="userDistributorConfig.do?action=sort&sortField=city&distributorconfig=true">City</td>
<td><a class="tableheader" href="userDistributorConfig.do?action=sort&sortField=state&distributorconfig=true">State</td>
<td><a class="tableheader" href="userDistributorConfig.do?action=sort&sortField=status&distributorconfig=true">Status</td>
<td class="tableheader">Select</td>
</tr>
<logic:iterate id="arrele" name="user.distributor.vector">
<tr>

<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td><bean:write name="arrele" property="busEntity.erpNum"/></td>
<td>
  <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
  <a href="distmgr.do?action=distdetail&searchType=id&searchField=<%=eleid%>">
  <bean:write name="arrele" property="busEntity.shortDesc"/></a>
</td>
<td><bean:write name="arrele" property="primaryAddress.address1"/></td>
<td><bean:write name="arrele" property="primaryAddress.city"/></td>
<td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>


<td><html:multibox name="USER_DISTRIBUTOR_CONFIG_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
<html:hidden name="USER_DISTRIBUTOR_CONFIG_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />

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

</html:form>

</div>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</html:form>
</body>

</html:html>







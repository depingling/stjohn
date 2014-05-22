<%!private String prop; %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.TaskPropertyData" %>
<%@ page import="com.cleanwise.service.api.value.TaskData" %>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="dist" type="java.lang.String" 
  toScope="session"/>
  

<html:html>

<head>
    <link rel="stylesheet" href="../externals/styles.css">
    <title>Domain Administrator</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<table border=0 width="<%=Constants.TABLEWIDTH%>" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <jsp:include flush='true' page="ui/systemToolbar.jsp"/>
        </td>
    </tr>
    <tr>
        <td>
            <jsp:include flush='true' page="ui/loginInfo.jsp"/>
        </td>
    </tr>
    <tr>
        <td>
            <jsp:include flush='true' page="ui/admDomainToolbar.jsp"/>
        </td>
    </tr>
</table>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH%>"  class="mainbody">

<html:form action="adminportal/domainAdmDetail.do">

<tr>
<td><b>Domain Id:</b></td>
<td>
<bean:write name="DOMAIN_ADM_DETAIL_FORM" property="id" />
<html:hidden property="id"/>
</td>
 <td><b>Name:</b></td>
<td>
<html:text tabindex="1" property="name" 
  size="30" maxlength="30" />
<span class="reqind">*</span>
</td>
  </tr>
<tr>
<td><b>Status:</b></td>
<td>
<html:select tabindex="3" property="statusCd" >
<html:option value="" ><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Domain.status.cds" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
<td><b>Long name:</b></td>
<td>

<html:textarea property="longDescription" cols="80" rows="3"/>

</td>

</tr>
<tr>
<td><b>SSL name:</b></td>
<td>
<html:text tabindex="4" property="SSLName" 
  size="30" maxlength="30" />
</td>

</tr>

<tr>
    <td colspan=4 align=center>
      <html:hidden property="action" value="saveDomain"/>
      <html:submit>
        <app:storeMessage  key="global.action.label.save"/>
      </html:submit>
    </td>
</tr>

</html:form>

</table>


</div>


<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>

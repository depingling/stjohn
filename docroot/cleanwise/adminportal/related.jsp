<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<%! String lAction = "init"; %>
<% lAction = request.getParameter("action"); %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Locate </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admRelatedToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<%if (lAction.equals("catalog") ) { %>
<jsp:include flush='true' page="ui/catalogInfo.jsp"/>
<% }  else if(lAction.equals("contract") ) { %>
<jsp:include flush='true' page="ui/contractInfo.jsp"/>
<% }  else if(lAction.equals("user") ) { %>
<jsp:include flush='true' page="ui/userInfo.jsp"/>
<% }  else if(lAction.equals("store") ) { %>
<jsp:include flush='true' page="ui/storeInfo.jsp"/>
<% }  else if(lAction.equals("account") ) { %>
<jsp:include flush='true' page="ui/accountInfo.jsp"/>
<% }  else if(lAction.equals("site") ) { %>
<jsp:include flush='true' page="ui/siteInfo.jsp"/>
<% } %>


<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>
<table> <tr>
<html:form name="RELATED_FORM" action="adminportal/related.do"
    method="POST"
    scope="session" type="com.cleanwise.view.forms.RelatedForm">
<td><b>Find Relationships:</b></td>
<td>
<html:text name="RELATED_FORM" property="searchForName" size="10"/>	
<html:select name="RELATED_FORM" property="searchForType" onchange="document.forms[0].submit();">
<html:options collection="Related.options.vector" 
property="label"
labelProperty="value" 
/>
</html:select>
</td>
</tr>
  <tr> <td><b>Search By:</b></td>
       <td colspan="3">
         <html:radio name="RELATED_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="RELATED_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>

<bean:define id="actionBean" 
  value="<%= request.getParameter(\"action\") %>" scope="request"/>
<html:hidden property="action" value="<%= actionBean %>"/>

<tr>
<td></td>
<td colspan="3">
<html:submit>
<app:storeMessage  key="global.action.label.search"/>
</html:submit>
<html:submit property="viewAll">
<app:storeMessage  key="admin.button.viewall"/>
</html:submit>
</td>
</tr>

</html:form>

</table>
<jsp:include flush='true' page="ui/admFooter.jsp"/>

</div>
</body>
</html:html>






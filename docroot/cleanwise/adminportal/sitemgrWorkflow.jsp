<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.ArrayList" %>

<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="account" type="java.lang.String" 
 toScope="session"/>
<%
	String isMSIE = (String)session.getAttribute("IsMSIE");
	if (null == isMSIE) isMSIE = "";
%>

<html:html>

<head>

<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Site Workflow</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admSiteToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table width="769"  class="mainbody"> <%-- Main table. --%>
  <tr>
  <td><b>Store&nbsp;Id:</b></td><td>
<bean:write name="SITE_DETAIL_FORM" property="storeId"/>
  </td>
  <td><b>Store&nbsp;Name:</b></td><td>
<bean:write name="SITE_DETAIL_FORM" property="storeName"/>
</td>
  </tr>
  <tr>
  <td><b>Account&nbsp;Id:</b></td><td><bean:write 
  name="SITE_DETAIL_FORM" property="accountId"/>
  </td>
  <td><b>Account&nbsp;Name:</b></td><td>
  <bean:write name="SITE_DETAIL_FORM" property="accountName"/>
  </td>
  </tr>
<logic:present name="Site.workflow">
<tr>
<td><b>Workflow Id:</b></td>
<td>
<bean:write name="Site.workflow" property="workflowId"/>
<bean:define id="wid" type="java.lang.Integer"
   name="Site.workflow" property="workflowId"/>
</td>
<td><b>Name:</b></td>
<td>
<a href="accountWorkflow.do?action=detail&searchType=id&searchField=<%=wid%>">
<bean:write name="Site.workflow" property="shortDesc"/>
</a>
</td>
</tr>

<tr>
<td><b>Type:</b></td>
<td>
<bean:write name="Site.workflow" property="workflowTypeCd"/>
</td>
<td><b>Status:</b></td>
<td>
<bean:write name="Site.workflow" property="workflowStatusCd"/>
</td>
</tr>
</logic:present>
</table>
<br><br>
<table width="769" cellspacing=0 >
<logic:notPresent name="Site.workflow">
<tr>
<th>No Workflow Defined Yet</th>
</tr>
</logic:notPresent>

</table> <%-- Main table --%>
<br><br>
<%-- Rules for the workflow. --%>
<table width="769" cellspacing=0 >
<logic:present name="Site.workflow.rules.vector">
<bean:size id="rescount"  name="Site.workflow.rules.vector"/>
  <tr>
    <th>Rule</th>
    <th colspan=3>Expression</td>
    <th>Action</th>
  </tr>
<logic:greaterThan name="rescount" value="0">
<define:bean id="idx" type="java.lang.Integer" value="0"/>

<logic:iterate id="arrele" indexId="idx" name="Site.workflow.rules.vector">
<bean:define id="wrid" type="java.lang.Integer" 
  name="arrele" property="workflowRuleId"/>

  <% if ( (idx.intValue()%2) == 0 ) { %>  
  <tr class="rowa" align=center>
  <% } else { %>
  <tr class="rowb" align=center>
  <% } %>
  
    <td>                    
<%-- 
Show the order or evaluation, this may not be the
same as the sequence number stored in the database.
--%>                         
    <%= idx.intValue() + 1 %>
</td>
    <td><bean:write name="arrele" property="ruleTypeCd"/></td>
    <td><bean:write name="arrele" property="ruleExp"/></td>
    <td><bean:write name="arrele" property="ruleExpValue"/></td>

    <td>
      <bean:write name="arrele" property="ruleAction"/>
      <logic:match name="arrele" property="ruleAction" 
        value="Forward" >
        <br>(<bean:write name="arrele" property="nextActionCd"/>)
      </logic:match>
      <logic:match name="arrele" property="ruleAction" 
        value="Send" >
        <br>(<bean:write name="arrele" property="nextActionCd"/>)
      </logic:match>
    </td>
  </tr>
</logic:iterate>
</logic:greaterThan>
</logic:present>

</table><%-- Rules for the workflow. --%>


</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>







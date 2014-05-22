<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Locate </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admAccountToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<table width="769" border="0" class="mainbody">
  <tr>
  <td><b>Store&nbsp;Id:</b></td><td><bean:write name="Store.id"/></td>
  <td><b>Store&nbsp;Name:</b></td><td><bean:write name="Store.name"/></td>
  </tr>
  <tr>
  <td><b>Account&nbsp;Id:</b></td><td><bean:write name="Account.id"/></td>
  <td><b>Account&nbsp;Name:</b></td><td><bean:write name="Account.name"/></td>
  </tr>
</table>

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>

<logic:present name="Account.config.catalog.vector">
<bean:size id="rescount"  name="Account.config.catalog.vector"/>
 Search Account Configuration Results:  <bean:write name="rescount" />
  
<table width="769" border="0" class="results">
<html:form action="adminportal/accountConfig.do">
  <tr align=left>
    <td><a class="tableheader" href="accountConfig.do?action=sort&field=id">Catalog Id</td>
    <td><a class="tableheader" href="accountConfig.do?action=sort&field=name">Name</td>
    <td><a class="tableheader" href="accountConfig.do?action=sort&field=status">Status</td>
    <td class="tableheader">Select</td>
  </tr>

  <logic:iterate id="arrele" name="Account.config.catalog.vector">
  <pg:item>
  <tr>
    <td><bean:write name="arrele" property="catalogId"/></td>
    <td>
      <bean:define id="eleid" name="arrele" property="catalogId"/>
      <a href="catalogdetail.do?action=edit&id=<%=eleid%>">
        <bean:write name="arrele" property="shortDesc"/>
      </a>
    </td>
    <td><bean:write name="arrele" property="catalogStatusCd"/></td>
    <td>
      <html:radio name="ACCOUNT_CONFIG_FORM" property="catalogId" value="<%=String.valueOf(eleid)%>"/>
    </td>
  </tr>
  </pg:item>
  </logic:iterate>
  <tr>
    <td></td>
    <td></td>
    <td></td>
    <td>
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.save"/>
      </html:submit>
    </td>
  </tr>
</html:form>
</table>
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




<jsp:include flush='true' page="ui/admFooter.jsp"/>

</div>
</body>
</html:html>






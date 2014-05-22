<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>



<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Operations Console: Troubleshooting</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/orderOpToolbar.jsp"/>

<div class="text">

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">

<html:form name="ORDER_TROUBLESHOOTING_FORM" action="/console/orderTroubleshooting.do" focus="accountId"
    scope="session" type="com.cleanwise.view.forms.OrderTroubleshootingForm">

<%
  String location = (String) session.getAttribute("loc");
%>
  <tr> <td><b><%= location %>:</b></td>
       
  </tr>

</html:form>  
</table>

Search results count:&nbsp;<bean:write name="ORDER_TROUBLESHOOTING_FORM" property="listCount" filter="true"/>

<logic:greaterThan name="ORDER_TROUBLESHOOTING_FORM" property="listCount" value="0">

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a href="orderTroubleshooting.do?action=sort&sortField=acctname"><b>Acct Name</b></a></td>
<td class="resultscolumna"><a href="orderTroubleshooting.do?action=sort&sortField=erpponum"><b>Outbound PO&nbsp;#</b></a></td>
<td><a href="orderTroubleshooting.do?action=sort&sortField=webordernum"><b>Web Order&nbsp;#</b></a></td>
<td class="resultscolumna"><a href="orderTroubleshooting.do?action=sort&sortField=orderdate"><b>Date Ordered</b></a></td>
<td><a href="orderTroubleshooting.do?action=sort&sortField=custponum"><b>Cust PO&nbsp;#</b></a></td>
<td class="resultscolumna"><a href="orderTroubleshooting.do?action=sort&sortField=sitename"><b>Site Name</b></a></td>
<td><b>Site Address</b></td>
<td class="resultscolumna"><b>City</b></td>
<td><b>State</b></td>
<td class="resultscolumna"><a href="orderTroubleshooting.do?action=sort&sortField=zip"><b>Zip</b></a></td>
<td><a href="orderTroubleshooting.do?action=sort&sortField=method"><b>Method</b></a></td>
</tr>

 <bean:define id="pagesize" name="ORDER_TROUBLESHOOTING_FORM" property="listCount"/>
	  
<logic:iterate id="order" name="ORDER_TROUBLESHOOTING_FORM" property="resultList"
     offset="0" length="<%=pagesize.toString()%>" type="com.cleanwise.service.api.value.OrderHistoryData"> 
 <pg:item>
 <bean:define id="key"  name="order" property="orderStatus.orderStatusId"/>
 <bean:define id="orderDate"  name="order" property="orderStatus.dateOrdered"/>
 <% String linkHref = new String("orderOpDetail.do?action=view&id=" + key);%>
	
	
  <tr>
  <td class="resultscolumna"><bean:write name="order" property="orderStatus.customerAcct"/></td>
  <td class="resultscolumna"><bean:write name="order" property="erpPo.erpPoNumber"/></td>  
  <td class="resultscolumna"><a href="<%=linkHref%>"><bean:write name="order" property="orderStatus.frontEndOrderNumber"/></a></td>
  <td class="resultscolumna"><i18n:formatDate value="<%=orderDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/></td>
  <td class="resultscolumna"><bean:write name="order" property="orderStatus.customerPoNumber"/></td>
  <td class="resultscolumna"><bean:write name="order" property="orderStatus.locationName"/></td>
  <td class="resultscolumna"><bean:write name="order" property="orderStatus.shippingAddress1"/></td>
  <td class="resultscolumna"><bean:write name="order" property="orderStatus.city"/></td>
  <td class="resultscolumna"><bean:write name="order" property="orderStatus.state"/></td>
  <td class="resultscolumna"><bean:write name="order" property="orderStatus.zip"/></td>  
  <td class="resultscolumna"><bean:write name="order" property="shippingHistory.shipMethod"/></td>
 </tr>
	
	
 </pg:item>
 </logic:iterate>
	  
</table>
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
</logic:greaterThan>

</div>


<jsp:include flush='true' page="ui/consoleFooter.jsp"/>
</body>

</html:html>



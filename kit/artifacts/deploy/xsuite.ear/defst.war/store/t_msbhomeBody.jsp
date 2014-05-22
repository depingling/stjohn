<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<div class="text"><font color=red><html:errors/></font></div>

<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">

<bean:define id="toolBarTab" type="java.lang.String" value="default"
  toScope="request"/>
<jsp:include flush='true' page="f_msbToolbar.jsp"/>

</table>

<table CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>" align=center>
<tr>
<td  bgcolor="black" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td><table CELLSPACING=0 CELLPADDING=0 width="750" align=center>
<tr>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbhome.do?action=sort_order_guides&sortField=id">Order Guide Id</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbhome.do?action=sort_order_guides&sortField=name">Name</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbhome.do?action=sort_order_guides&sortField=catalog">Catalog Name</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbhome.do?action=sort_order_guides&sortField=orderGuideTypeCd">Type</div></td>
</tr>

<logic:present name="msb.orderGuide.vector" scope="session">
<bean:size id="ogres"  name="msb.orderGuide.vector"/>

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>

<logic:iterate id="ogele" indexId="ogIndex" name="msb.orderGuide.vector">
<pg:item>

<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)ogIndex)%>">

<td><bean:write name="ogele" property="orderGuideId"/></td>
<td>
<bean:define id="ogid" name="ogele" property="orderGuideId"/>
<a href="msbshop_order_guide.do?action=shop_order_guide&orderGuideId=<%=ogid%>&idx=<%=ogIndex%>">
<bean:write name="ogele" property="orderGuideName"/>
</a>
</td>
<td><bean:write name="ogele" property="catalogName"/></td>
<td>
<logic:equal name="ogele" property="orderGuideTypeCd" value="BUYER_ORDER_GUIDE">
Site
</logic:equal>
<logic:equal name="ogele" property="orderGuideTypeCd" value="ORDER_GUIDE_TEMPLATE">
Template
</logic:equal>

</td>
</tr>

</pg:item>
</logic:iterate>

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

</logic:present>

</table>
<br> <% /* Add some white space before the footer. */ %>
</td>

<td bgcolor="black" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</table>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<tr>
<td>
 <img src="<%=IMGPath%>/cw_left_footer_shop.gif" ALIGN="top">
</td>
<td>
 <img src="<%=IMGPath%>/cw_middle_footer_shop.gif" ALIGN="top"
   width="<%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>" height="8">
</td>
<td>
 <img src="<%=IMGPath%>/cw_right_footer_shop.gif" ALIGN="top">
</td>
</tr>
</table>





<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" type="java.lang.String" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">
<tr>
<td class="top3dk"  width="<%=Constants.TABLEWIDTH%>"><img src="../<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
<bean:define id="toolBarTab" type="java.lang.String" value="sites"
toScope="request"/>
<jsp:include flush='true' page="msbToolbar.jsp"/> 

<tr>
<td>
<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">
<tr>
<td  bgcolor="black" width="1"><img src="../<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
<td>

<table align=center CELLSPACING=0 CELLPADDING=0>
<tr><td>

<logic:present name="msb.site.vector">
<bean:size id="rescount"  name="msb.site.vector"/>

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>

<table width="750"  class="results" cellspacing=0 cellpadding=0>
<tr align=left>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbsites.do?action=sort_sites&sortField=id">Site&nbsp;Id</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbsites.do?action=sort_sites&sortField=name">Site Name</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbsites.do?action=sort_sites&sortField=address">Street Address</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbsites.do?action=sort_sites&sortField=city">City</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbsites.do?action=sort_sites&sortField=state">State/Province</div></td>
</tr>

<logic:iterate id="arrele" name="msb.site.vector" indexId="i">
<pg:item>
<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a href="msbsites.do?action=shop_site&siteId=<%=eleid%>">
<bean:write name="arrele" property="busEntity.shortDesc"/>
</a>
</td>
<td>
<bean:write name="arrele" property="siteAddress.address1"/>
</td>
<td>
<bean:write name="arrele" property="siteAddress.city"/>
</td>
<td>
<bean:write name="arrele" property="siteAddress.stateProvinceCd"/>
</td>
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
</logic:present>

</table>
<br>
</td>
<td bgcolor="black" width="1"><img src="../<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
</table>

</td>
</tr>

</table>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<tr>
<td>
 <img src="/<%=storeDir%>/<%=ip%>images/cw_left_footer_shop.gif" ALIGN="top">
</td>
<td>
 <img src="/<%=storeDir%>/<%=ip%>images/cw_middle_footer_shop.gif" ALIGN="top" width="<%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>" height="8">
</td>
<td>
 <img src="/<%=storeDir%>/<%=ip%>images/cw_right_footer_shop.gif" ALIGN="top">
</td>
</tr>

</table>


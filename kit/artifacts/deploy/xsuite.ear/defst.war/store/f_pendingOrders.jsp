<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<logic:present name="pending.orders.vector">
<bean:size id="rescount"  name="pending.orders.vector"/>

<tr><td>
<table align=center width=750 cellpadding=3 cellspacing=0>

<tr>
<td width="10%" class="shopcharthead">

<div class="fivemargin">Order Number</div></td>
<td class="shopcharthead"><div class="fivemargin">
Date
</div></td>
<td class="shopcharthead"><div class="fivemargin">
Site
</div></td>
<td class="shopcharthead"><div class="fivemargin">
Site City
</div></td>
<td class="shopcharthead"><div class="fivemargin">
Site State
</div></td>
<td class="shopcharthead"><div class="fivemargin">
Amount
</div></td>
<td class="shopcharthead">
<div class="fivemargin">
Comments
</div></td>
</tr>

<logic:iterate id="arrele" name="pending.orders.vector" indexId="i">

<% 
  ClwI18nUtil i18n = ClwI18nUtil.getInstance(request,arrele.getLocaleCd(),-1);
%>

<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
<td><bean:write name="arrele" property="orderNum"/></td>
<td><bean:write name="arrele" property="orderDate" /></td>
<td><bean:write name="arrele" property="siteDesc" /></td>
<td><bean:write name="arrele" property="siteCity" /></td>
<td><bean:write name="arrele" property="siteState" /></td>
<td>
  <bean:define id="amount"  name="arrele" property="amount"/>
  <%=i18n.priceFormat(amount,"&nbsp;")%>
  <i18n:formatCurrency value="<%=amount%>" locale="<%=Locale.US%>"/>
</td>
<td>
<bean:write name="arrele" property="comments" /></td>
</tr>
</logic:iterate>

</logic:present>

</td></tr>
</table>





<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.*"%>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<% CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER); %>

<div class="text"><font color=red><html:errors/></font></div>

<logic:present name="pending.orders.vector">
<bean:size id="rescount"  name="pending.orders.vector"/>


<table align=center CELLSPACING=0 CELLPADDING=0
  width="<%=Constants.TABLEWIDTH%>">
<tr>
<td font-weight: bold;">&nbsp;</td>
</tr>
</table>

<table align=center CELLSPACING=0 CELLPADDING=0
  width="<%=Constants.TABLEWIDTH%>">
<tr align=left>
<td class="shopcharthead"

>
<div class="fivemargin">
<a href="pendingOrders.do?action=sort&sortField=orderNum">
   <app:storeMessage key="shop.orderStatus.text.order#"/></a>
</div></td>
<td class="shopcharthead" align=center><div class="fivemargin">
<a href="pendingOrders.do?action=sort&sortField=orderDate">
 <app:storeMessage key="shop.orderStatus.text.date"/></a>
</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a href="pendingOrders.do?action=sort&sortField=siteDesc">
  <app:storeMessage key="shop.orderStatus.text.siteName"/></a>
</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a href="pendingOrders.do?action=sort&sortField=siteCity">
 <app:storeMessage key="shop.orderStatus.text.city"/></a>
</div></td>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<td class="shopcharthead"><div class="fivemargin">
<a href="pendingOrders.do?action=sort&sortField=siteState">
 <app:storeMessage key="shop.orderStatus.text.state"/></a>
</div></td>
<%} %>
<td class="shopcharthead"><div class="fivemargin">
 <app:storeMessage key="shop.orderStatus.text.status"/>
</div></td>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<td class="shopcharthead" align=center><div class="fivemargin">
<a href="pendingOrders.do?action=sort&sortField=amount">
  <app:storeMessage key="shop.orderStatus.text.amount"/></a>
</div></td>
</logic:equal>
<td class="shopcharthead"

>
<div class="fivemargin">
<a href="pendingOrders.do?action=sort&sortField=comments">
  <app:storeMessage key="shop.orderStatus.text.comments"/></a>
</div></td>
</tr>

<logic:iterate id="arrele" name="pending.orders.vector" indexId="i"
type="com.cleanwise.service.api.value.OrderSiteSummaryData">

<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
<td >
<bean:define id="oid" name="arrele" property="orderId"/>
<bean:define id="orderLocale" name="arrele" property="localeCd"/>
<%
String viewOrderUrl = "handleOrder.do?action=view&amp;" +
 "orderId=" + oid;
%>


 <%
 // All items were added by the system, this indicates and auto order
 // placed by the inventory logic.  durval, 3/16/2006
 %>
 <logic:equal name="arrele" property="allOrderItemsAddedBySystemFlag"
   value="true">
 <span class="inv_item">
 &nbsp;A&nbsp;
 </span>
 </logic:equal>

<a href="<%=viewOrderUrl%>">
<bean:write name="arrele" property="orderNum"/>
</a>
</td>
<td width=80 align=center><bean:write name="arrele" property="orderDate" /></td>
<td><bean:write name="arrele" property="siteDesc" /></td>
<td><bean:write name="arrele" property="siteCity" /></td>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<td><bean:write name="arrele" property="siteState" /></td>
<%} %>
<td>
<%
if(arrele.getModifStartedOn() == null) {
%>
<bean:write name="arrele" property="orderStatusCd" />
<logic:equal name="arrele" property="orderStatusCd"
 value="<%=RefCodeNames.ORDER_STATUS_CD.PENDING_DATE%>">
<%
 String processOnDateS = ((OrderSiteSummaryData) arrele).getProcessOnDate();
 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
 Date processOnDate = null;
 if(processOnDateS!=null && processOnDateS.trim().length()>0) {
   processOnDate = sdf.parse(processOnDateS);
%>
<br><%=(new SimpleDateFormat("yyyy-MM-dd")).format(processOnDate)%>
<% } %>
</logic:equal>
<% } else { // Order is under modification %>
 <app:storeMessage key="shop.orderStatus.text.orderModificationStarted"/><br>
<%=(new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(arrele.getModifStartedOn())%>
<% } %>
</td>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<td align=right style="padding-right: 5pt;">
  <bean:define id="amount"  name="arrele" property="sumOfAllOrderCharges"/>
  <%=ClwI18nUtil.priceFormat(request, amount, (String) orderLocale,"&nbsp;")%>
</td>
</logic:equal>
<td >
&nbsp;
<bean:write name="arrele" property="comments" />
</td>

</tr>

</logic:iterate>
</table>

<table align=center CELLSPACING=0 CELLPADDING=0
  width="<%=Constants.TABLEWIDTH%>">
<tr>
<td>

</logic:present>

<br>
</td>
</tr>

</table>


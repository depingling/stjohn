
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.session.Site"%>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<bean:define id="IMGPath" type="java.lang.String"
  name="pages.store.images"/>

<div class="text">



<table cellSpacing=0 cellPadding=0 border=0> <!-- inner -->

<td>
<%String f_siteProps_jsp = ClwCustomizer.getStoreFilePath(request, "f_siteProps.jsp");%>
<jsp:include flush='true' page="<%=f_siteProps_jsp%>"/>
<br><br>
<div class="bar_a"><app:storeMessage key="shop.site.text.siteParValues"/></div>

<html:form
  name="SITE_INVENTORY_FORM"
  action="userportal/siteInventoryEdit.do?action=update_site_inventory"
  type="com.cleanwise.view.forms.InventoryForm">

<bean:define id="thisSite" name="ApplicationUser" property="site"  type="SiteData" />
<bean:define id="budgetPeriods" name="ApplicationUser" property="site.budgetPeriods" type="java.util.Hashtable" />

<%Calendar cal = java.util.Calendar.getInstance(); %>
<%int numPeriods = ((java.util.Hashtable)budgetPeriods).size();%>

<table cellspacing=0 cellpadding=2>
<tr class="changeshippinglt">
<td class="cityhead"><app:storeMessage key="shoppingItems.text.sku"/></td>
<td class="cityhead" colspan=6><app:storeMessage key="shoppingItems.text.productName"/></td>
<td class="cityhead"><app:storeMessage key="shoppingItems.text.uom"/></td>
<td class="cityhead" colspan=4><app:storeMessage key="shoppingItems.text.pack"/></td>
</tr>
<tr class="changeshippinglt">
<td class="cityhead" colspan=<%=numPeriods%>><app:storeMessage key="shop.site.text.parValues"/></td>
</tr>
<tr>

    <% for (int bp = 1; bp <= numPeriods; bp++) {

        String drange = "";
        if (budgetPeriods.containsKey(bp)) {

            FiscalCalendarInfo.BudgetPeriodInfo bi = (FiscalCalendarInfo.BudgetPeriodInfo) budgetPeriods.get(bp);

            cal.setTime(bi.getStartDate());
            drange = "" + (cal.get(cal.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + " - ";

            cal.setTime(bi.getEndDate());
            drange += "" + (cal.get(cal.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH);

        } %>

    <% if ( drange != null && drange.length() > 0 ) { %>
    <td valign=top class="cityhead"><%=bp%><br><%=drange%></td>
    <% } %>

    <% } %>

</tr>

  <logic:iterate id="siteInv"
     indexId="i"
  name="SITE_INVENTORY_FORM"
  property="inventoryItems"
     type="com.cleanwise.service.api.value.SiteInventoryConfigView">

<% if (siteInv.getSumOfAllParValues() > 0 ) { %>
  <tr>
<% } else { %>
  <tr bgcolor="#c6ddef">
<% } %>

<td>
<% if (thisSite.isAnInventoryAutoOrderItem(siteInv)) { %>
<span class="inv_item">a</span>
<% } %>
&nbsp;&nbsp;<bean:write name="siteInv" property="actualSku"/></td>
<td colspan=<%=numPeriods - 6%>><bean:write name="siteInv" property="itemDesc"/></td>
<td><bean:write name="siteInv" property="itemUom"/></td>
<td><bean:write name="siteInv" property="itemPack"/></td>
<td><bean:write name="siteInv" property="modBy"/></td>
<td colspan=2>
<logic:present name="siteInv" property="modDate">
<bean:define id="mDate" name="siteInv" property="modDate" type="java.util.Date"
/>
<i18n:formatDate value="<%=mDate%>"
  pattern="yyyy-M-d"  locale="<%=Locale.US%>"/>
</logic:present>
</td>

</tr>

<% if (siteInv.getSumOfAllParValues() > 0 ) { %>
  <tr>
<% } else { %>
  <tr bgcolor="#c6ddef">
<% } %>

<html:hidden name="SITE_INVENTORY_FORM"
  property='<%= "inventoryItem[" + i + "].itemId" %>'
  value="<%=String.valueOf(siteInv.getItemId())%>" />

<html:hidden name="SITE_INVENTORY_FORM"
  property='<%= "inventoryItem[" + i + "].siteId" %>'
  value="<%=String.valueOf(siteInv.getSiteId())%>" />

<% for ( int bp2 = 1; bp2 <= numPeriods; bp2++ ) { %>
<td class="rtext2">
  <html:text size="3" name="SITE_INVENTORY_FORM"
  property='<%= "inventoryItemWrapper[" + i + "].parValue[" + bp2 + "]" %>'/>
</td>
<% } %>

</tr>

  </logic:iterate>

<tr >
<td ></td>
<td ></td>
<td ></td>
<td ></td>
<td >
<html:link href="javascript:{document.SITE_INVENTORY_FORM.submit();}">
[<app:storeMessage key="global.action.label.submit"/>]</html:link>
</td>
<td></td>
<!-- 118 -->

</tr>

</table>
</html:form>
<br><br>


 </td>

</tr></table> <!-- inner -->


</div>
<!-- 119 -->

<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<style>
.s1 {
  background-color: #cccccc;
  border-bottom: solid 1px black;
  border-right: solid 1px black;
}
.s2 {
  background-color: #cccccc;
  font-weight: bold;
}
.r1 {
  background-color: #cccccc;
}
</style>

<logic:present name="order">

<table align=center valign=top
  CELLSPACING=3 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">
  <tr>
    <td>
      <img src='<app:custom  pageElement="pages.logo1.image" addImagePath="true" encodeForHTML="true"/>' border="0"  >
    </td>
  </tr>

  <tr>
    <td class="s1">Order Number</td>
    <td><bean:write name="order" property="orderNum"/></td>
  </tr>
  <tr>
    <td class="s1">Order Date</td>
    <td><bean:write name="order" property="orderDate"/></td>
  </tr>
  <tr>
    <td class="s1">PO Number</td>
    <td><bean:write name="order" property="poNum"/></td>
  </tr>
  <tr>
    <td class="s1">Comments</td>
    <td><bean:write name="order" property="comments"/></td>
  </tr>

<%-- Order Contact fields ------------------------ --%>

<%
CleanwiseUser appUser = (CleanwiseUser)
  session.getAttribute(Constants.APP_USER);
boolean crcManagerFl = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CRC_MANAGER);  
String utype = appUser.getUser().getUserTypeCd();
if ( utype.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
     utype.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER) ||
	 crcManagerFl
   ) {
%>

<tr><td class="s1">Contact Name</td>
    <td><bean:write name="order" property="orderData.orderContactName"/></td>
</tr>

<tr><td class="s1">Contact Phone #</td>
    <td><bean:write name="order" property="orderData.orderContactPhoneNum"/></td>
</tr>

<tr><td class="s1">Contact Email</td>
    <td><bean:write name="order" property="orderData.orderContactEmail"/></td>
</tr>

<tr><td class="s1">Method</td>
    <td><bean:write name="order" property="orderData.orderSourceCd"/></td>
</tr>

<% } %>

<logic:iterate id="siteField" name="CHECKOUT_FORM"
  property="site.dataFieldPropertiesRuntime"
  type="com.cleanwise.service.api.value.PropertyData">

  <tr>
    <td class="s1">
      <bean:write name="siteField" property="shortDesc"/>
    </td>
    <td>
      <bean:write name="siteField" property="value" />
    </td>
  </tr>
</logic:iterate>

<tr>
  <td colspan=3>
    <table align=center width=750> <% /* Start - List the items for the order. */ %>

      <tr class="s2">
        <td>Qty</td>
        <td>Our&nbsp;Sku&nbsp;#</td>
        <td>Product&nbsp;Name</td>
        <td>Size</td>
        <td>Pack</td>
        <td>Uom</td>
        <td>Color</td>
        <td>Manufacturer</td>
        <td>Mfg.Sku #</td>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
          <td>Price</td>
          <td>Amount</td>
        </logic:equal>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
          <td colspan="2">&nbsp;</td>
        </logic:equal>
      </tr>

  <logic:iterate id="item" name="order" property="orderJoinItems"
   offset="0" indexId="oidx"
   type="com.cleanwise.service.api.value.OrderItemJoinData">

<% if ((oidx.intValue() % 2) > 0 ) { %>
 <tr class="r1">
<% } else { %>
 <tr>
<% } %>
  <td><bean:write name="item" property="qty"/></td>
  <td><%=ShopTool.getRuntimeSku(item,request)%></td>
  <td><bean:write name="item" property="shortDesc"/></td>
  <td><bean:write name="item" property="size"/></td>
  <td><bean:write name="item" property="pack"/></td>
  <td><bean:write name="item" property="uom"/></td>
  <td><bean:write name="item" property="color"/></td>
  <td><bean:write name="item" property="mfgName"/></td>
  <td><bean:write name="item" property="mfgSkuNum"/></td>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
          <bean:define id="amount"  name="item" property="amount"/>
      <td><i18n:formatCurrency value="<%=amount%>" locale="<%=Locale.US%>"/></td>

          <%
             BigDecimal  amt = item.getAmount();
             double number = item.getQty();
             BigDecimal num = new BigDecimal(number);
             BigDecimal total = amt.multiply(num);
          %>
          <td><i18n:formatCurrency value="<%=total%>" locale="<%=Locale.US%>"/></td>
        </logic:equal>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
          <td colspan="2">&nbsp;</td>
        </logic:equal>

      </tr>
      </logic:iterate>
  </td>
</table> <% /* End   - List the items for the order. */ %>

</td>
</tr>


<tr class="s2">
<td >Order Summary</td>
<td >Ship to</td>
<td >Bill to</td>
</tr>

<tr valign=top>

<td>

<table> <% /* Order summary data. */ %>
<bean:define id="theForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>
            <bean:define id="cartAmt" name="CHECKOUT_FORM" property="cartAmt"/>
            <logic:greaterThan name="CHECKOUT_FORM" property="cartAmt" value="0">
<tr> <td class="s1"> Subtotal: </td>
<td><i18n:formatCurrency value="<%=cartAmt%>" locale="<%=Locale.US%>"/> </td>
</tr>
            </logic:greaterThan>

            <bean:define id="freightAmt" name="CHECKOUT_FORM" property="freightAmt"/>
            <logic:greaterThan name="CHECKOUT_FORM" property="freightAmt" value="0">
<tr> <td class="s1"> Freight: </td>
<td><i18n:formatCurrency value="<%=freightAmt%>" locale="<%=Locale.US%>"/></td>
</tr>
            </logic:greaterThan>

            <bean:define id="handlingAmt" name="CHECKOUT_FORM" property="handlingAmt"/>
            <logic:greaterThan name="CHECKOUT_FORM" property="handlingAmt" value="0">
<tr> <td class="s1"> Handling: </td>
<td><i18n:formatCurrency value="<%=handlingAmt%>" locale="<%=Locale.US%>"/></td>
</tr>
            </logic:greaterThan>

            <bean:define id="miscAmt"  name="CHECKOUT_FORM" property="miscAmt"/>
            <logic:greaterThan name="CHECKOUT_FORM" property="miscAmt" value="0">
<tr> <td class="s1">Misc. Charges: </td>
<td><i18n:formatCurrency value="<%=miscAmt%>" locale="<%=Locale.US%>"/></td>
</tr>

            </logic:greaterThan>

            <% BigDecimal zero = new BigDecimal(0);
              zero = zero.setScale(2,BigDecimal.ROUND_HALF_UP);
            %>
            <logic:present name="<%=zero.toString()%>">
<tr> <td class="s1">Tax: </td>
<td>$<%=zero.toString()%></td>
</tr>
            </logic:present>
<tr> <td class="s1">Total: </td>
<% BigDecimal lTotal = theForm.getCartAmt().add(theForm.getFreightAmt());
   lTotal = lTotal.add(theForm.getHandlingAmt());

%>
<td><i18n:formatCurrency value="<%=lTotal%>" locale="<%=Locale.US%>"/></td>

</tr>

</table> <% /* Order summary data. */ %>

</td>

<td>

<table> <% /* Ship to data. */ %>
<tr valign=top>
<td  class="s1">
Contact
</td>

<td>
<bean:write name="order" property="order.orderContactName"/>
</td>

</tr>

<tr valign=top>
<td class="s1">
Address
</td>
<td>
<bean:write name="order" property="shipShortDesc"/>
<br>
<bean:write name="order" property="shipAddress1"/>
<br>
<bean:write name="order" property="shipAddress2"/>
<br>
<bean:write name="order" property="shipAddress3"/>
<br>
<bean:write name="order" property="shipAddress4"/>
<br>
<bean:write name="order" property="shipCity"/>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<br>
<bean:write name="order" property="shipStateProvinceCd"/>
<%} %>
<br>
<bean:write name="order" property="shipPostalCode"/>
<br>
<bean:write name="order" property="shipCountryCd"/>
</td>
</tr>
</table> <% /* Ship to data. */ %>

</td>

<td >
<table> <% /* Account bill to information. */ %>
<tr valign=top>
<td class="s1">Account number</td>
<td><bean:write name="order" property="accountNum"/></td>
</tr>
<tr valign=top>
<td class="s1">Address</td>
<td>
<bean:write name="order" property="billShortDesc"/>
<br>
<bean:write name="order" property="billAddress1"/>
<br>
<bean:write name="order" property="billAddress2"/>
<br>
<bean:write name="order" property="billAddress3"/>
<br>
<bean:write name="order" property="billAddress4"/>
<br>
<bean:write name="order" property="billCity"/>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<br>
<bean:write name="order" property="billStateProvinceCd"/>
<%} %>
<br>
<bean:write name="order" property="billPostalCode"/>
<br>
<bean:write name="order" property="billCountryCd"/>
</td>
</tr>
</table>


</td>

</tr>

</table>

</logic:present>

<logic:notPresent name="order">

<table align=center CELLSPACING=0 CELLPADDING=0
  width="<%=Constants.TABLEWIDTH%>">
<tr align=center>
<td style="border-right: solid 1px black;
  border-left: solid 1px black;
  border-bottom: solid 1px black;
  font-weight: bold;">
<br><br><br><br>
No order data available.
<br><br><br><br>
</td></tr></table>

</logic:notPresent>




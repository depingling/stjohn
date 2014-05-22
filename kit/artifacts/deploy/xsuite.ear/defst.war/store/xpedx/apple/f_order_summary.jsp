
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<%
 ShoppingCartData shoppingCart = ShopTool.getCurrentShoppingCart(session);
 /*
 BigDecimal prevRushCharge = shoppingCart.getPrevRushCharge();
 String rushOrderCharge = "0";
 if ( prevRushCharge != null ) {
   // Carry foward the previous rush charge.
   rushOrderCharge = prevRushCharge.toString();
 } else if (shoppingCart.getPrevOrderData() == null ) {
   // set the rush amount to the amount defined
   // at the account.
   rushOrderCharge = ShopTool.getRushOrderCharge(request);
 }

*/

try {
%>



<table    cellspacing="0"   cellpadding="1"
  class="tbstd"
   width="<%=Constants.TABLEWIDTH%>"> <!-- summary table -->

<tr valign=top><td>

<table border="0" align="center" cellspacing="0" cellpadding="0"
  width="90%"><tr valign=top>
  <td valign=top>      <!-- Table order summary beg -->
     <table border="0" cellspacing="0" cellpadding="0" align="center">
            <bean:define id="cartAmt" name="CHECKOUT_FORM" property="cartAmt"/>
        <tr>
<td colspan=3 class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
  <app:storeMessage key="shop.checkout.header.summary"/></td>
</tr>
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.checkout.text.subtotal"/></b></td>
<td align="right"> <%=ClwI18nUtil.getPriceShopping(request,cartAmt,"&nbsp;")%> </td>
<td> &nbsp;&nbsp;</td>
                </tr>

<bean:define id="freightAmt" name="CHECKOUT_FORM" property="freightAmt"/>

<% if (appUser.isaCustServiceRep()) { %>
  <tr><td class="smalltext">  <b><app:storeMessage key="shop.checkout.text.freight"/></b></td>
  <td align="right"> <html:text name="CHECKOUT_FORM" property="freightAmtString" size="8"/></td>
<td> &nbsp;&nbsp;</td>
  </tr>
  <%--<tr><td class="smalltext">  <b><app:storeMessage key="shop.checkout.text.rushOrderCharge"/></b></td>
  <td align="right"> <html:text name="CHECKOUT_FORM" property="rushChargeAmtString" size="8"
  value="<%=rushOrderCharge%>" /></td>
<td> &nbsp;&nbsp;</td>
  </tr>--%>
<% } else { %>
<html:hidden name="CHECKOUT_FORM" property="freightAmtString" value="<%=theForm.getFreightAmtString()%>" />
<logic:greaterThan name="CHECKOUT_FORM" property="freightAmt" value="0">
  <tr><td class="smalltext" >  <b><app:storeMessage key="shop.checkout.text.freight"/></b> </td>
  <td align="right"><%=ClwI18nUtil.getPriceShopping(request,freightAmt,"&nbsp;")%></td>
<td> &nbsp;&nbsp;</td>
  </tr>
</logic:greaterThan>
<%--
  <% if ( null != rushOrderCharge && rushOrderCharge.length() > 0 ) { %>
  <html:hidden name="CHECKOUT_FORM" property="rushChargeAmtString"
  value="<%=rushOrderCharge%>"/>
  <tr><td class="smalltext">  <b><app:storeMessage key="shop.checkout.text.rushOrderCharge"/></b></td>
  <td align="right">
  <%=ClwI18nUtil.getPriceShopping(request,new BigDecimal(rushOrderCharge),"&nbsp;")%>
  </td>
<td> &nbsp;&nbsp;</td>
  </tr>
  <% } /* rush order charge */ %> --%>
<% } /* Not a crc shopper */ %>

<bean:define id="handlingAmt" name="CHECKOUT_FORM"
  property="handlingAmt" type="java.math.BigDecimal"/>

<%

OrderData prevOrder = shoppingCart.getPrevOrderData();

if ( prevOrder != null && prevOrder.getTotalMiscCost() != null &&
  prevOrder.getTotalMiscCost().intValue() > 0 ) {
  if ( handlingAmt == null || handlingAmt.intValue() <= 0 ) {
  handlingAmt = prevOrder.getTotalMiscCost();
  theForm.setHandlingAmt(handlingAmt);
  }
}
%>

<%
String applyHandlingCharge = appUser.getSite().getSiteFieldValue
  ("Apply contract handling charges");

if (applyHandlingCharge != null &&
  applyHandlingCharge.toLowerCase().equals("n")) {
  handlingAmt = new java.math.BigDecimal("0");
  theForm.setHandlingAmt(handlingAmt);
  theForm.setHandlingAmtString(String.valueOf(handlingAmt));
}
%>

<% if (appUser.isaCustServiceRep()) { %>
  <tr><td class="smalltext">  <b><app:storeMessage key="shop.checkout.text.handling"/></b></td>
  <td align="right"> <html:text name="CHECKOUT_FORM" property="handlingAmtString" size="8"/></td>
<td> &nbsp;&nbsp;</td>
  </tr>
<% } else { %>
<html:hidden name="CHECKOUT_FORM" property="handlingAmtString" value="<%=theForm.getHandlingAmtString()%>" />
            <logic:greaterThan name="CHECKOUT_FORM" property="handlingAmt" value="0">
  <tr><td class="smalltext" >  <b><app:storeMessage key="shop.checkout.text.handling"/></b> </td>
  <td align="right"><%=ClwI18nUtil.getPriceShopping(request,handlingAmt,"&nbsp;")%></td>
<td> &nbsp;&nbsp;</td>
  </tr>
            </logic:greaterThan>
<% } %>

            <bean:define id="miscAmt"  name="CHECKOUT_FORM" property="miscAmt"/>
            <logic:greaterThan name="CHECKOUT_FORM" property="miscAmt" value="0">
<tr><td class="smalltext">   <b><app:storeMessage key="shop.checkout.text.miscCharges"/></b></td>
<td align="right"> <%=ClwI18nUtil.getPriceShopping(request,miscAmt,"&nbsp;")%></td>
<td> &nbsp;&nbsp;</td>
</tr>
            </logic:greaterThan>

<%
// Taxes are always zero.  Display this to the user.
%>
<tr><td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.tax"/></b> </td>
<% BigDecimal noTax = new BigDecimal(0); %>
<td align="right"> <%=ClwI18nUtil.getPriceShopping(request,noTax,"&nbsp;")%></td>
<td> &nbsp;&nbsp;</td>
</tr>

<tr><td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.total"/></b></td>
<%
BigDecimal lTotal = theForm.getCartAmt().add(theForm.getFreightAmt());
   lTotal = lTotal.add(handlingAmt);
//if ( null != rushOrderCharge && rushOrderCharge.length() > 0 ) {
//   lTotal = lTotal.add(new BigDecimal(rushOrderCharge));
//}
%>

 <td align="right">
   <%=ClwI18nUtil.getPriceShopping(request,lTotal,"&nbsp;")%>
 </td>
<td> &nbsp;&nbsp;</td>
</tr>

     </table><!--order totals summary end --></td>

</td>

  <td valign=top>     <!-- Table billto beg -->
     <table border="0" cellspacing="0" cellpadding="0" align="center">
       <tr>
     <td class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
  <app:storeMessage key="shop.checkout.header.billingInformation"/></td>
  </tr><tr>
        <td class="smalltext"><div class="fivemargin">
        <b><app:storeMessage key="shop.checkout.text.accountName"/></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="account.busEntity.shortDesc"/><br>
        <b><app:storeMessage key="shop.checkout.text.address1"/></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="account.billingAddress.address1"/><br>
        <% String address2 = theForm.getAccount().getBillingAddress().getAddress2(); %>
        <% if(address2!=null && address2.trim().length()>0) { %>
          <b><app:storeMessage key="shop.checkout.text.address2"/></b>&nbsp;<%=address2%><br>
        <% } %>
        <% String address3 = theForm.getAccount().getBillingAddress().getAddress3(); %>
        <% if(address3!=null && address3.trim().length()>0) { %>
          <b><app:storeMessage key="shop.checkout.text.address3"/></b>&nbsp;<%=address3%><br>
        <% } %>
        <% String address4 = theForm.getAccount().getBillingAddress().getAddress4(); %>
        <% if(address4!=null && address4.trim().length()>0) { %>
          <b><app:storeMessage key="shop.checkout.text.address4"/></b>&nbsp;<%=address4%><br>
        <% }
         if (appUser.getUserStore().isStateProvinceRequired()) {
        %>
        <b><app:storeMessage key="shop.checkout.text.cityStateZip:"/></b>&nbsp;
          <bean:write name="CHECKOUT_FORM" property="account.billingAddress.city"/>,
          <bean:write name="CHECKOUT_FORM" property="account.billingAddress.stateProvinceCd"/>
          <bean:write name="CHECKOUT_FORM" property="account.billingAddress.postalCode"/>
        <%} else { %>
          <b><app:storeMessage key="shop.checkout.text.cityZip:"/></b>&nbsp;
          <bean:write name="CHECKOUT_FORM" property="account.billingAddress.city"/>,
          <bean:write name="CHECKOUT_FORM" property="account.billingAddress.postalCode"/>
        <%}%>
        <br>
        <b><app:storeMessage key="shop.checkout.text.country:"/></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="account.billingAddress.countryCd"/></td>
        </div></td>
        </tr>
     </table><!--Table billto end --></td>
     <td><!-- Table shipto -->
     <table border="0" cellspacing="0" cellpadding="0" align="center">
       <tr>
     <td class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
  <app:storeMessage key="shop.checkout.header.shippingInformation"/></td>
  </tr><tr>
        <td class="smalltext"><div class="fivemargin">
        <b><app:storeMessage key="shop.checkout.text.name:"/></b>&nbsp;
        <bean:write name="CHECKOUT_FORM" property="site.siteAddress.name1"/>&nbsp;
        <bean:write name="CHECKOUT_FORM" property="site.siteAddress.name2"/>
        <br>
        <b><app:storeMessage key="shop.checkout.text.siteName"/></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="site.busEntity.shortDesc"/><br>
        <b><app:storeMessage key="shop.checkout.text.address1"/></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="site.siteAddress.address1"/><br>
        <% String siteAddress2 = theForm.getSite().getSiteAddress().getAddress2(); %>
        <% if(siteAddress2!=null && siteAddress2.trim().length()>0) { %>
          <b><app:storeMessage key="shop.checkout.text.address2"/></b>&nbsp;<%=siteAddress2%><br>
        <% } %>
        <% String siteAddress3 = theForm.getSite().getSiteAddress().getAddress3(); %>
        <% if(siteAddress3!=null && siteAddress3.trim().length()>0) { %>
          <b><app:storeMessage key="shop.checkout.text.address3"/></b>&nbsp;<%=siteAddress3%><br>
        <% } %>
        <% String siteAddress4 = theForm.getSite().getSiteAddress().getAddress4(); %>
        <% if(siteAddress4!=null && siteAddress4.trim().length()>0) { %>
          <b><app:storeMessage key="shop.checkout.text.address4"/></b>&nbsp;<%=siteAddress4%><br>
        <% }
         if (appUser.getUserStore().isStateProvinceRequired()) {
        %>
        <b><app:storeMessage key="shop.checkout.text.cityStateZip:"/></b>&nbsp;
          <bean:write name="CHECKOUT_FORM" property="site.siteAddress.city"/>,
          <bean:write name="CHECKOUT_FORM" property="site.siteAddress.stateProvinceCd"/>
          <bean:write name="CHECKOUT_FORM" property="site.siteAddress.postalCode"/>
        <%} else { %>
          <b><app:storeMessage key="shop.checkout.text.cityZip:"/></b>&nbsp;
          <bean:write name="CHECKOUT_FORM" property="site.siteAddress.city"/>,
          <bean:write name="CHECKOUT_FORM" property="site.siteAddress.postalCode"/>
        <%}%>
        <br>
        <b><app:storeMessage key="shop.checkout.text.country:"/></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="site.siteAddress.countryCd"/></td>
        </div></td>
        </tr>
     </table><!--Table shipto end --></td>
</tr>

</table>  <!-- table 90 pct -->

</td></tr>
</table>  <!-- summary table -->


<%
} catch (Exception e) {
 out.print(" <tr><td> End of stack trace"
  + "<br> message=" + e.getMessage()
  );
}
%>



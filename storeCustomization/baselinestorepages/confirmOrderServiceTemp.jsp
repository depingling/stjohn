<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>

<%

 ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
 CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
 boolean crcManagerFl = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CRC_MANAGER);
 String utype = appUser.getUser().getUserTypeCd();
 %>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>




<script language="JavaScript1.2">
<!--
function viewPrinterFriendly(oid) {
  var loc = "printerFriendlyOrder.do?action=printPdf&orderId=" + oid;
  prtwin = window.open(loc,"printer_friendly",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}
//-->

</script>

<style>
.checkoutSubHeader   {
        color: #FFFFFF;
        background-color: #669999;
        font-size: 14px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;

}

.checkoutSummaryHeader   {
        color: #336666;
        background-color: #99cccc;
        font-size: 12px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
        border-width: 1px;
        border-style: solid;
        border-color: #336666;

}
</style>

<!-- Table. level1 Begin -->
<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <!-- Title -->
  <% if(request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
  <tr>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
    <td class="genericerror" align="center"><html:errors/></td>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  </tr>
  <% } %>
  <tr>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
      <td>
      <table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
       <tr>
        <td class="checkoutSubHeader" valign="top">
        <app:storeMessage key="shop.checkout.text.serviceShoppingCart"/>
         </td>
       </tr>
      </table>
      </td>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  </tr>

  <!-- No items in the shopping cart -->
  <logic:equal name="CHECKOUT_FORM" property="servicesSize" value="0">
  <% if(request.getAttribute("org.apache.struts.action.ERROR")==null) { %>
  <tr bgcolor="#F0FFFF">
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
    <td class="text" align="center">
      <br><b><app:storeMessage key="shop.checkout.text.shoppiningCartIsEmpty"/></b><br>&nbsp;
    </td>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  </tr>
  <tr>
    <td colspan="3" class="tableoutline"><img src="/images/cw_spacer.gif" height="3"></td>
  </tr>
  <tr>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
    <td class="text">&nbsp;

    </td>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  </tr>
  <%}%>
  </logic:equal>


<!-- There are items in the shopping cart -->
<logic:greaterThan name="CHECKOUT_FORM" property="servicesSize" value="0">

<%
  String confSubmit ="/store/checkout.do?action=confOrderService";

 %>

  <html:form action="<%=confSubmit%>">
    <tr>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
    <td>

      <!-- Table Level2 Beg -->
      <table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td colspan="2">
      <b><bean:define id="orderRes" name="CHECKOUT_FORM" property="orderResult" type="ProcessOrderResultData"/>
            <%

            CustomerOrderRequestData orderReq = theForm.getOrderRequest();
            String otype = "";
            if ( orderReq != null &&
            orderReq.getOrderType() != null ) {
              otype = orderReq.getOrderType();
            }

            while(orderRes != null){
              pageContext.setAttribute("orderRes",orderRes);
              String sts = orderRes.getOrderStatusCd();
                %>
                <app:storeMessage key="shop.checkout.text.yourOrderHasBeenPlaced"/>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <app:storeMessage key="shop.checkout.text.confirmationNumber"/>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <span style="font-size: 150%;">
                  <bean:write name="orderRes" property="orderNum"/>
                </span>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <app:storeMessage key="shop.checkout.text.orderDate"/>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <%=ClwI18nUtil.formatDate(request,orderRes.getOrderDate(),DateFormat.FULL)%>
                <%orderRes = orderRes.getNext();%>
                <%if(orderRes != null){%>
                   <br>
                <%}%>
             <%}%> </b></td>
    </tr>
  <tr>
          <td  class="text">

          <app:storeMessage  key="shop.confirm.text.additional"/>
         </td>
       </tr>
       <tr><td class="text">
         <table cellspacing=0 cellpadding=0 valign=top>
           <tr>
             <td valign=top>
               <%-- -------   ----   PO and comments block --%>
               <table valign=top width="365" border="0" cellpadding="1" cellspacing="0">
                 <tr>
                   <td class="text" align="left">
                     <%if(appUser.getUserAccount().isCustomerRequestPoAllowed()){%>
                     <b><app:storeMessage key="shop.checkout.text.poNumber"/></b>
                     <%if(theForm.getSite().getBlanketPoNum() != null && theForm.getSite().getBlanketPoNum().getBlanketPoNumId() != 0){%>
                     <bean:write name="CHECKOUT_FORM" property="site.blanketPoNum.poNumber"/>
                     <%}%>
                     <bean:write name="CHECKOUT_FORM" property="poNumber"/>
                     <%}else{%>
                     &nbsp;
                     <%}%>
                   </td>
                   <td class="text" align="left">
                     <% if(theForm.getProcessOrderDate()!=null && theForm.getProcessOrderDate().trim().length()>0) {%>
                     <b><app:storeMessage key="shop.checkout.text.processOrderOn"/></b>
                     <bean:write name="CHECKOUT_FORM" property="processOrderDate"/>
                     <% } else {%>
                     &nbsp;
                     <% } %>
                   </td>
                 </tr>
                 <% if (!appUser.getUserAccount().hideShippingComments()) { %>
                 <tr>
                   <td class="text" valign="top" colspan='2' align="left">
                     <b><app:storeMessage key="shop.checkout.text.shippingComments"/></b>
                     <bean:write name="CHECKOUT_FORM" property="comments"/></td>
                   </tr>
                <%} %>
                 </table>
               </td>  <%-- ------  --------- PO and comments block --%>
               <td valign=top>
                 <%-- Site fields ---------------   --%>
                 <table valign=top>
                   <logic:iterate id="siteField"
                     name="CHECKOUT_FORM" property="site.dataFieldPropertiesRuntime"
                     type="com.cleanwise.service.api.value.PropertyData">

                     <tr>
                       <td class="text"><b><bean:write name="siteField" property="shortDesc"/> </b></td>
                       <td><bean:write name="siteField" property="value" />     </td>
                     </tr>

                   </logic:iterate>

                   <bean:define id="shipmsg" type="java.lang.String"
                     name="CHECKOUT_FORM" property="shippingMessage" />
                     <% if (shipmsg.length() > 0) { %>
                     <tr>  <td colspan=2 class="text">
                       <b><app:storeMessage key="shop.checkout.text.shippingInformation"/></b> <%= shipmsg %>
                     </td></tr>
                     <% } %>
                   </table>
                   <%-- Site fields ------------------------ --%>

                   <%-- Order Contact fields ------------------------ --%>
                   <%
                   if ( utype.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
                   utype.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER) ||
				   crcManagerFl
                   ) {
                     %>

                     <table>
                       <tr><td class="text"><b> <app:storeMessage key="shop.checkout.text.contactName"/></b></td>
                         <td> <bean:write name="CHECKOUT_FORM" property="orderContactName"/>
                         </td></tr>

                         <tr><td class="text"><b> <app:storeMessage key="shop.checkout.text.contactPhoneNum"/></b></td>
                           <td> <bean:write name="CHECKOUT_FORM" property="orderContactPhoneNum"/>
                           </td></tr>

                           <tr><td class="text"><b> <app:storeMessage key="shop.checkout.text.contactEmail"/></b></td>
                             <td> <bean:write name="CHECKOUT_FORM" property="orderContactEmail"/>
                             </td></tr>

                             <tr><td class="text"><b> <app:storeMessage key="shop.checkout.text.method"/></b></td>
                               <td>  <bean:write name="CHECKOUT_FORM" property="orderOriginationMethod"/>
                               </td></tr>

                             </table>
                             <%-- Order Contact fields ------------------------ --%>
                    <% } %> <br>
                         <%

                          String b_print = IMGPath + "/b_print.gif"; %>
                     <a href="#" class="linkButton"
                        onclick="viewPrinterFriendly('<%=theForm.getOrderResult().getOrderId()%>');"
                             ><img src="<%=b_print%>" border="0"/>
                         <app:storeMessage key="global.label.printerFriendly"/>
                     </a>
                    </tr>
          </table>

</td></tr>
</table><!-- Table Level2 end -->
 <jsp:include flush='true' page="f_shoppingServices.jsp">
       <jsp:param name="formId" value="CHECKOUT_FORM"/>
</jsp:include>
  </td>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  </tr>
  <!----------- Total Information -------------->
  <tr>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
    <td>
  <!-- Table level2 beg -->
  <table border="0" align="center" cellspacing="0" cellpadding="0" width="90%">
     <tr>
     <td valign="top">
         <table border="0" cellspacing="0" cellpadding="0" align="center">
        <tr>
        <td colspan="2" class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
        <app:storeMessage key="shop.checkout.header.summary"/>
        </td>
        </tr>
        <bean:define id="cartServiceAmt" name="CHECKOUT_FORM" property="cartServiceAmt"/>
        <logic:greaterThan name="CHECKOUT_FORM" property="cartServiceAmt" value="0">
        <tr><td class="smalltext"><b><app:storeMessage key="shop.checkout.text.subtotal"/></b></td>
          <!-- cartAmt -->
          <td align="right"> <%=ClwI18nUtil.getPriceShopping(request,cartServiceAmt,"&nbsp;")%> &nbsp;&nbsp;</td>
         </tr>
         </logic:greaterThan>
         <% boolean freightHandlFl = false; %>
        <bean:define id="freightAmt" name="CHECKOUT_FORM" property="freightAmt"/>
        <logic:greaterThan name="CHECKOUT_FORM" property="freightAmt" value="0">
        <tr> <td class="smalltext"><b><app:storeMessage key="shop.checkout.text.freight"/></b></td>
        <td align="right">
         <%freightHandlFl = true; %>
         <%=ClwI18nUtil.getPriceShopping(request,freightAmt,"&nbsp;")%><bean:write name="CHECKOUT_FORM" property="estimateFreightString" />
         </td>
         </tr>
         </logic:greaterThan>
<%--
        <bean:define id="rushChargeAmt" name="CHECKOUT_FORM" property="rushChargeAmtString" type="java.lang.String" />
        <% BigDecimal rushCharge = new BigDecimal(0); %>
        <% if ( null != rushChargeAmt && rushChargeAmt.length() > 0 ) {
        rushCharge = new BigDecimal(rushChargeAmt);
        %>
        <tr> <td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.rushOrderCharge"/></b> </td>
         <td align="right">
         <%=ClwI18nUtil.getPriceShopping(request,rushCharge,"&nbsp;")%>
         </td>
         </tr>
        <% } %>
--%>
        <bean:define id="handlingAmt" name="CHECKOUT_FORM" property="handlingAmt"/>
        <logic:greaterThan name="CHECKOUT_FORM" property="handlingAmt" value="0">
         <tr> <td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.handling"/></b> </td>
         <td align="right">
         <%freightHandlFl = true; %>
        <%=ClwI18nUtil.getPriceShopping(request,handlingAmt,"&nbsp;")%>

         </td>
         </tr>
        </logic:greaterThan>

        <logic:present name="CHECKOUT_FORM" property="salesTax">
        <bean:define id="tax" name="CHECKOUT_FORM" property="salesTax"/>
        <logic:greaterThan name="CHECKOUT_FORM" property="salesTax" value="0">
         <tr> <td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.tax"/></b> </td>
         <td align="right">
         <%=ClwI18nUtil.getPriceShopping(request,tax,"&nbsp;")%>
         &nbsp;&nbsp;</td>
         </tr>
        </logic:greaterThan>
        </logic:present>


        <tr> <td class="smalltext"><b><app:storeMessage key="shop.checkout.text.total"/></b></td>
        <% BigDecimal lTotal = theForm.getCartServiceAmt().add(theForm.getFreightAmt());
           lTotal = lTotal.add(theForm.getHandlingAmt());
//           lTotal = lTotal.add(rushCharge);
         if(theForm.getSalesTax() != null){
          lTotal = lTotal.add(theForm.getSalesTax());
         }
        %>
       <td align="right">
        <%=ClwI18nUtil.getPriceShopping(request,lTotal,"&nbsp;")%>
       &nbsp;&nbsp;</td>
       </tr>
      </table>
     </td>

     <!-- Table level3 beg account-->
     <td valign="top">
     <table border="0" cellspacing="0" cellpadding="0" align="center">
       <tr><td colspan="1" class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
       <app:storeMessage key="shop.checkout.header.billingInformation"/></td>
       </tr>
        <tr>
        <td class="smalltext"><div>
        <b><app:storeMessage key="shop.checkout.text.accountName"/></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="accountShortDesc"/><br>
        <b><app:storeMessage key="shop.checkout.text.address1"/></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="confirmBillToAddress.address1"/><br>
      <% String address2 = theForm.getConfirmBillToAddress().getAddress2(); %>
      <% if(address2!=null && address2.trim().length()>0) { %>
          <b><app:storeMessage key="shop.checkout.text.address2"/></b>&nbsp;<%=address2%><br>
      <% } %>
      <% String address3 = theForm.getConfirmBillToAddress().getAddress3(); %>
      <% if(address3!=null && address3.trim().length()>0) { %>
        <b><app:storeMessage key="shop.checkout.text.address3"/></b>&nbsp;<%=address3%><br>
      <% } %>
      <% String address4 = theForm.getConfirmBillToAddress().getAddress4(); %>
      <% if(address4!=null && address4.trim().length()>0) { %>
        <b><app:storeMessage key="shop.checkout.text.address4"/></b>&nbsp;<%=address4%><br>
      <% }
         if (appUser.getUserStore().isStateProvinceRequired()) {
      %>
      <b><app:storeMessage key="shop.checkout.text.cityStateZip:"/></b>&nbsp;
      <bean:write name="CHECKOUT_FORM" property="confirmBillToAddress.city"/>,
      <bean:write name="CHECKOUT_FORM" property="confirmBillToAddress.stateProvinceCd"/>
      <bean:write name="CHECKOUT_FORM" property="confirmBillToAddress.postalCode"/>
      <%} else { %>
      <b><app:storeMessage key="shop.checkout.text.cityZip:"/></b>&nbsp;
      <bean:write name="CHECKOUT_FORM" property="confirmBillToAddress.city"/>,
      <bean:write name="CHECKOUT_FORM" property="confirmBillToAddress.postalCode"/>
      <%}%>
      <br>
      <b><app:storeMessage key="shop.checkout.text.country:"/></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="confirmBillToAddress.countryCd"/>
     </div></td>
     </tr>
     </table><!--Table level3 account end -->
     </td>
     <td valign="top">
     <!-- Table level3 ship to beg -->
     <table border="0" cellspacing="0" cellpadding="0" align="center">
       <tr>
       <td class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
       <app:storeMessage key="shop.checkout.header.shippingInformation"/>
       </td>
       </tr>
        <tr>
        <td class="smalltext"><div>
        <b><app:storeMessage key="shop.checkout.text.name:"/></b>&nbsp;
        <bean:write name="CHECKOUT_FORM" property="siteName1"/>&nbsp;
        <bean:write name="CHECKOUT_FORM" property="siteName2"/>
        <br>
        <b><app:storeMessage key="shop.checkout.text.siteName"/></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="siteShortDesc"/><br>
        <b><app:storeMessage key="shop.checkout.text.address1"/></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="siteAddress1"/><br>
        <% String siteAddress2 = theForm.getSiteAddress2(); %>
        <% if(siteAddress2!=null && siteAddress2.trim().length()>0) { %>
          <b><app:storeMessage key="shop.checkout.text.address2"/></b>&nbsp;<%=siteAddress2%><br>
        <% } %>
        <% String siteAddress3 = theForm.getSiteAddress3(); %>
        <% if(siteAddress3!=null && siteAddress3.trim().length()>0) { %>
          <b><app:storeMessage key="shop.checkout.text.address3"/></b>&nbsp;<%=siteAddress3%><br>
        <% } %>
        <% String siteAddress4 = theForm.getSiteAddress4(); %>
        <% if(siteAddress4!=null && siteAddress4.trim().length()>0) { %>
          <b><app:storeMessage key="shop.checkout.text.address4"/></b>&nbsp;<%=siteAddress4%><br>
        <% }
         if (appUser.getUserStore().isStateProvinceRequired()) {
      %>
      <b><app:storeMessage key="shop.checkout.text.cityStateZip:"/></b>&nbsp;
          <bean:write name="CHECKOUT_FORM" property="siteCity"/>,
          <bean:write name="CHECKOUT_FORM" property="siteStateProvinceCd"/>
          <bean:write name="CHECKOUT_FORM" property="sitePostalCode"/>
      <%} else { %>
      <b><app:storeMessage key="shop.checkout.text.cityZip:"/></b>&nbsp;
          <bean:write name="CHECKOUT_FORM" property="siteCity"/>,
          <bean:write name="CHECKOUT_FORM" property="sitePostalCode"/>
      <%}%>
        <br>
        <b><app:storeMessage key="shop.checkout.text.country:"/></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="siteCountryCd"/>
        </div></td>
        </tr>
     </table><!--Table level3 ship to end -->
     </td>
 </tr>
</table>


<table>

   </table><!--Table level2 end -->

   </td>
   <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
   </tr>

  </html:form>
 </logic:greaterThan>
</table> <!-- Table. level1 End -->

<!-- Footer -->
<!-- Table. level1 Begin -->
<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<tr>
<td><img src="<%=IMGPath%>/cw_left_footer_shop.gif" ALIGN="top"></td>
<td><img src="<%=IMGPath%>/cw_middle_footer_shop.gif" ALIGN="top" width="<%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>" height="8"></td>
<td><img src="<%=IMGPath%>/cw_right_footer_shop.gif" ALIGN="top"></td>
</tr>
</table><!-- Table. level1 End -->

<!-- CRC INFO -->

<%
  String userType = appUser.getUser().getUserTypeCd();

  String accountName = "";
  String accountNumber = "";
  String siteErp = "";
  String contractName = "";
  String siteName = "";

  if (null != appUser && null != appUser.getSite()){

      siteName = appUser.getSite().getBusEntity().getShortDesc();
      accountName = appUser.getUserAccount().getBusEntity().getShortDesc();
      accountNumber = appUser.getUserAccount().getBusEntity().getErpNum();
      siteErp = appUser.getSite().getBusEntity().getErpNum();
      contractName = (String)session.getAttribute("ContractName");

  }

  if(userType.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
     userType.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
     userType.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER) ||
	 crcManagerFl){
%>

<table align="center" border="0" cellspacing="0" cellpadding="0" width="Constants.TABLEWIDTH" class="textreadonly">
<tr><td>
<b><app:storeMessage key="shop.checkout.text.accountName"/></b>  <%=accountName%> </td></tr>
<tr><td>
<b><app:storeMessage key="shop.checkout.text.accountErp"/></b> <%=accountNumber%>  </td></tr>
<tr><td><b><app:storeMessage key="shop.checkout.text.siteName"/></b> <%=siteName %> </td></tr>
<tr><td><b><app:storeMessage key="shop.checkout.text.siteErp"/></b><%=siteErp%> </td></tr>
<tr><td><b><app:storeMessage key="shop.checkout.text.contractName"/></b> <%=contractName %> </td></tr>
</table>
<% } %>


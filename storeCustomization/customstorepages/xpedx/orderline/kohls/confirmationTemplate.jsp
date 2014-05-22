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
 String requestNumber =  (String) session.getAttribute(Constants.REQUEST_NUM);
 ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
 CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
 String utype = appUser.getUser().getUserTypeCd();
 ReplacedOrderViewVector replacedOrders = theForm.getReplacedOrders();
 boolean consolidatedOrderFl = (replacedOrders!=null && replacedOrders.size()>0)? true:false;
 boolean usedCreditCard;
 String ccNumber = theForm.getCcNumber();
 if(ccNumber!=null && ccNumber.trim().length()>0) {
   usedCreditCard = true;
  }else{
   usedCreditCard = false;
  }
%>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>




<script language="JavaScript1.2">
<!--
function viewPrinterFriendly(oid) {
  var loc = "printerFriendlyOrder.do?action=printPdf&orderId=" + oid + "&showMfg=n&showMfgSku=n&showPar=n";
  prtwin = window.open(loc,"printer_friendly",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}
//-->

</script>

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
         <% if(consolidatedOrderFl) {%>
          <app:storeMessage key="shop.checkout.text.consolidatedShoppingCart"/>
         <% } else { %>
          <app:storeMessage key="shop.checkout.text.shoppingCart"/>
         <% } %>
        </td>
        <td class="shoponsiteservelt" valign="bottom" align="right">
           <%
/*
The reorder button is not shown for those sites
configured for inventory shopping.
*/
           if (!consolidatedOrderFl &&
    (
    utype.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
                utype.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER) ||
                utype.equals(RefCodeNames.USER_TYPE_CD.MSB) ||
                utype.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER)
    )
           ) {
           %>
 <%--      <a href="../userportal/msbsites.do?action=reorder" class="reorder">&nbsp;<app:storeMessage key="global.action.label.reorder"/></a> --%>
           <% } else { %>
             &nbsp;
           <% } %>

        </td>
       </tr>
      </table>
      </td>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  </tr>

  <!-- No items in the shopping cart -->
  <logic:equal name="CHECKOUT_FORM" property="itemsSize" value="0">
  <% if(request.getAttribute("org.apache.struts.action.ERROR")==null) { %>
  <tr class="orderInfoHeader">
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
    <td class="text">
      &nbsp;
    </td>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  </tr>
  <%}%>
  </logic:equal>


<!-- There are items in the shopping cart -->
<logic:greaterThan name="CHECKOUT_FORM" property="itemsSize" value="0">

<%
  String confSubmit ="/store/checkout.do?action=confSubmit";
  int colCount = 12;
  if (appUser.getUserAccount().isShowSPL()) {
   colCount++;
 }
 %>
  <html:form action="<%=confSubmit%>">
  <html:hidden name="CHECKOUT_FORM" property="orderResult.orderId"
  value="<%=\"\"+theForm.getOrderResult().getOrderId()%>" />
  <tr>
    <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
    <td>

      <!-- Table Level2 Beg -->
      <table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td colspan="<%=colCount%>" class="text">
          <b>
            <bean:define id="orderRes" name="CHECKOUT_FORM" property="orderResult" type="ProcessOrderResultData"/>
            <%
            boolean pendingConsolidationFl = false;

            CustomerOrderRequestData orderReq = theForm.getOrderRequest();
            String otype = "";
            if ( orderReq != null &&
            orderReq.getOrderType() != null ) {
              otype = orderReq.getOrderType();
            }
            if(RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(otype)) {
              pendingConsolidationFl = true;
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
             <%}/*end while(orderRes != null)*/%>
           </b>
           <app:storeMessage  key="shop.confirm.text.additional"/>
         </td>
       </tr>
       <tr><td colspan="<%=colCount%>" class="text">
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
                 <tr>
                   <td class="text" valign="top" colspan='2' align="left">
                     <b><app:storeMessage key="shop.checkout.text.shippingComments"/></b>
                     <bean:write name="CHECKOUT_FORM" property="comments"/></td>
                   </tr>
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
                   utype.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)
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
                    <% } %>

                    <br>
                      <% String b_print = IMGPath + "/b_print.gif"; %>
                      <a href="#" class="linkButton" onclick="viewPrinterFriendly('<%=theForm.getOrderResult().getOrderId()%>');"
                        ><img src="<%=b_print%>" border="0"/><app:storeMessage key="global.label.printerFriendly"/></a>

                      </td>
                    </tr>
          </table>

</td></tr>

<tr><td colspan="<%=colCount%>">&nbsp;</td></tr>

<tr><td colspan="<%=colCount%>">
      <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_confirmShoppingItems.jsp")%>'>
        <jsp:param name="allowEdits" value="false"/>
        <jsp:param name="showSelectCol" value="true"/>
        <jsp:param name="view" value="checkout"/>
        <jsp:param name="orderBy" value="<%=theForm.getSortBy()%>"/>
        <jsp:param name="formId" value="CHECKOUT_FORM"/>
      </jsp:include>
</td></tr>

  <tr>
  <td colspan="<%=colCount%>" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
  </tr>


  <tr>
  <td colspan="<%=(colCount-3)%>">&nbsp;</td>

  <td class="text" align="right"><div><b><app:storeMessage key="shoppingItems.text.productTotal:"/><b></div></td>
  <td class="text"><div>
      <bean:define id="itemsAmt"  name="CHECKOUT_FORM" property="itemsAmt"/>
      <%=ClwI18nUtil.getPriceShopping(request,itemsAmt,"&nbsp;")%>
    </div>
  </td>
  </tr>
</table><!-- Table Level2 end -->

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
      <!-- Table level3 beg -->
     <table border="0" cellspacing="0" cellpadding="0" align="center">
        <tr>
        <td colspan="2" class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
        <app:storeMessage key="shop.checkout.header.summary"/>
        </td>
        </tr>
        <bean:define id="cartAmt" name="CHECKOUT_FORM" property="cartAmt"/>
        <logic:greaterThan name="CHECKOUT_FORM" property="cartAmt" value="0">
        <tr><td class="smalltext"><b><app:storeMessage key="shop.checkout.text.subtotal"/></b></td>
          <!-- cartAmt -->
          <td align="right"> <%=ClwI18nUtil.getPriceShopping(request,cartAmt,"&nbsp;")%> &nbsp;&nbsp;</td>
         </tr>
         </logic:greaterThan>
         <% boolean freightHandlFl = false; %>
        <bean:define id="freightAmt" name="CHECKOUT_FORM" property="freightAmt"/>
        <logic:greaterThan name="CHECKOUT_FORM" property="freightAmt" value="0">
        <tr> <td class="smalltext"><b><app:storeMessage key="shop.checkout.text.freight"/></b></td>
        <td align="right">
         <%
         freightHandlFl = true;
         if(!pendingConsolidationFl) {
         %>
         <!-- freightAmt -->
         <%=ClwI18nUtil.getPriceShopping(request,freightAmt,"&nbsp;")%><bean:write name="CHECKOUT_FORM" property="estimateFreightString" />
         <% } else { %>
         *
         <% } %>
         &nbsp;&nbsp;</td>
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
         <%
         if(!pendingConsolidationFl) {
         %>
         <%=ClwI18nUtil.getPriceShopping(request,rushCharge,"&nbsp;")%>
         <% } else { %>
         *
         <% } %>
         &nbsp;&nbsp;</td>
         </tr>
        <% } %>
--%>
        <bean:define id="handlingAmt" name="CHECKOUT_FORM" property="handlingAmt"/>
        <logic:greaterThan name="CHECKOUT_FORM" property="handlingAmt" value="0">
         <tr> <td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.handling"/></b> </td>
         <td align="right">
         <%
         freightHandlFl = true;
         if(!pendingConsolidationFl) {
         %>
         <%=ClwI18nUtil.getPriceShopping(request,handlingAmt,"&nbsp;")%>
         <% } else { %>
         *
         <% } %>
         &nbsp;&nbsp;</td>
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

        <%
        if(!pendingConsolidationFl || !freightHandlFl) {
        %>
        <tr> <td class="smalltext"><b><app:storeMessage key="shop.checkout.text.total"/></b></td>
        <% BigDecimal lTotal = theForm.getCartAmt().add(theForm.getFreightAmt());
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
       <% if(theForm.getEstimateFreightFlag()) { %>
       <tr><td colspan="2" align="right">
       <app:storeMessage key="shop.checkout.text.estimate"/>
       </td>
       </tr>
       <% } %>
       <% } %>
      </table><!--Table level3 end -->
      </td>
     <!-- Table level3 beg account-->
     <td valign="top">
     <table border="0" cellspacing="0" cellpadding="0" align="center">
       <tr><td colspan="1" class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
       <app:storeMessage key="shop.checkout.header.billingInformation"/></td>
       </tr>
        <tr>
        <td class="smalltext"><div>
  <%if(!usedCreditCard){%><%-- find me on account bill to --%>
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
      <b><app:storeMessage key="shop.checkout.text.country:"
      /></b>&nbsp;<bean:write name="CHECKOUT_FORM" property="confirmBillToAddress.countryCd"/>
  <%}else{%><%-- END on account bill to --%>
  <!-- Credit Card -->
      <table  align="center" border="0" cellpadding="0" cellspacing="0" >
      <tr>
          <td class="text">
      <b><app:storeMessage key="shop.checkout.text.cardType:"/></b>
      </td>
      <td>
      <%
        String ccType = theForm.getCcType();
        String ccTypeView = "";
        if(ccType==null) ccType = "";
        if(ccType.equals(RefCodeNames.PAYMENT_TYPE_CD.VISA)) {
          ccTypeView = "Visa";
        }
        else if(ccType.equals(RefCodeNames.PAYMENT_TYPE_CD.MASTER_CARD)) {
          ccTypeView = "MasterCard";
        }
        else if(ccType.equals(RefCodeNames.PAYMENT_TYPE_CD.AMERICAN_EXPRESS)) {
          ccTypeView = "American Express";
        }
      %>
      <%=ccTypeView%>
      </td>
      </tr>
      <tr>
      <td class="text" align="left">
      <b><app:storeMessage key="shop.checkout.text.cardNumber:"/></b>
          </td>
      <td class="text">****-<bean:write name="CHECKOUT_FORM" property="ccNumberForDisplay"/>
      </td>
      </tr>
      <tr>
      <td class="text"><b><app:storeMessage key="shop.checkout.text.expDate:"/></b></td>
      <td class="text">
      <%
        String ccExpMonthS = "";
        switch(theForm.getCcExpMonth()) {
          case GregorianCalendar.JANUARY:
            ccExpMonthS = ClwI18nUtil.getMessage(request,"global.text.month.jan",null);
            break;
          case GregorianCalendar.FEBRUARY:
            ccExpMonthS = ClwI18nUtil.getMessage(request,"global.text.month.feb",null);
            break;
          case GregorianCalendar.MARCH:
            ccExpMonthS = ClwI18nUtil.getMessage(request,"global.text.month.mar",null);
            break;
          case GregorianCalendar.APRIL:
            ccExpMonthS = ClwI18nUtil.getMessage(request,"global.text.month.apr",null);
            break;
          case GregorianCalendar.MAY:
            ccExpMonthS = ClwI18nUtil.getMessage(request,"global.text.month.may",null);
            break;
          case GregorianCalendar.JUNE:
            ccExpMonthS = ClwI18nUtil.getMessage(request,"global.text.month.jun",null);
            break;
          case GregorianCalendar.JULY:
            ccExpMonthS = ClwI18nUtil.getMessage(request,"global.text.month.jul",null);
            break;
          case GregorianCalendar.AUGUST:
            ccExpMonthS = ClwI18nUtil.getMessage(request,"global.text.month.aug",null);
            break;
          case GregorianCalendar.SEPTEMBER:
            ccExpMonthS = ClwI18nUtil.getMessage(request,"global.text.month.sep",null);
            break;
          case GregorianCalendar.OCTOBER:
            ccExpMonthS = ClwI18nUtil.getMessage(request,"global.text.month.oct",null);
            break;
          case GregorianCalendar.NOVEMBER:
            ccExpMonthS = ClwI18nUtil.getMessage(request,"global.text.month.nov",null);
            break;
          case GregorianCalendar.DECEMBER:
            ccExpMonthS = ClwI18nUtil.getMessage(request,"global.text.month.dec",null);
            break;
        }
      %>
      <%=ccExpMonthS%>
      <bean:write  name="CHECKOUT_FORM" property="ccExpYear"/>
      </td>
      </tr>
      <tr>
      <td class="text"><b><app:storeMessage key="shop.checkout.text.ccPersonName:"/></b></td>
      <td class="text">
      <bean:write name="CHECKOUT_FORM" property="ccPersonName"/>
      </td>
      </tr>
          <tr>
      <td class="text"><b><app:storeMessage key="shop.checkout.text.street1:"/></b></td>
      <td>
      <bean:write name="CHECKOUT_FORM" property="ccStreet1"/>
      </td>
      </tr>
      <tr>
      <td class="text"><b><app:storeMessage key="shop.checkout.text.street2:"/></b></td>
      <td>
      <bean:write name="CHECKOUT_FORM" property="ccStreet2"/>
      </td>
      </tr>
      <tr>
      <td class="text"><b><app:storeMessage key="shop.checkout.text.city:"/></b></td>
      <td>
      <bean:write name="CHECKOUT_FORM" property="ccCity"/>
      </td>
      </tr>
      <% if (appUser.getUserStore().isStateProvinceRequired()) { %>
      <tr>
      <td class="text"><b><app:storeMessage key="shop.checkout.text.state:"/></b></td>
      <td><bean:write name="CHECKOUT_FORM" property="ccState"/></td>
      </tr>
      <%} %>
      <tr>
      <td class="text"><b><app:storeMessage key="shop.checkout.text.zipCode:"/></b></td>
      <td><bean:write name="CHECKOUT_FORM" property="ccZipCode"/></td>
      </tr>
      </table><!--Table level3 end -->
   <% } %><!-- end of Credit Card -->

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
<%  if(pendingConsolidationFl && freightHandlFl) { %>
    <tr>
    <td colspan='5'>
    * <app:storeMessage key="shop.checkout.text.actualHandlingFreightWillBeCalculatedAtConsolidatedOrderTime"/>
    </td>
    </tr>
<% } %>
   </table><!--Table level2 end -->

   </td>
   <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
   </tr>

  </html:form>
  <% if(consolidatedOrderFl) { %>
  <tr>
  <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  <td>
  <table align="center" border="0" cellpadding="0" cellspacing="0" >
  <tr><td>
  <%@ include file="replacedOrders.jsp" %>
  </td></tr>
  </table>
  </td>
  <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  </tr>
  <% } %>
</logic:greaterThan>



</table> <!-- Table. level1 End -->

<!-- Footer -->
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>

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
     userType.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)){
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


<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript1.2">
<!--

function setAndSubmit(fid, vv, value) {
 var aaa = document.forms[fid].elements[vv];
 aaa.value=value;
 aaa.form.submit();
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

<bean:define id="theForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>

<%
 ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
 CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
 boolean crcManagerFl = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CRC_MANAGER);
 AccountData account = appUser.getUserAccount();

String userType = appUser.getUser().getUserTypeCd();

boolean showPlaceOrderFields = false,
        showPlaceOrderButton = true,
        showOrderSelected = false;

java.util.ArrayList pmtopts = new java.util.ArrayList();
//
// Doug requested that the CC option be listed
// first (default) if the user is set up for CC purchases.
// durval 2006-9-13
//

if (appUser.getOnAccount()) {
  pmtopts.add("PO");
}

String reqpmt = "";
boolean f_showPO = false, f_showCC = false, f_showOther = false;
if ( appUser.canMakePurchases() ) {
 showPlaceOrderFields = true;
 showOrderSelected = true;
 reqpmt = theForm.getReqPmt();
 if (null == reqpmt || "0".equals(reqpmt) ) {
    reqpmt = "PO";
 }
 if (reqpmt.equals("PO")) {
   f_showPO = true;
 }
}


boolean allowPoEntry = true, allowShippingCommentsEntry = true, allowAccountComments = true;
String siteName = appUser.getSite().getBusEntity().getShortDesc();
String accountName = account.getBusEntity().getShortDesc();
String accountNumber = account.getBusEntity().getErpNum();
String siteErp = appUser.getSite().getBusEntity().getErpNum();
String contractName = (String)session.getAttribute("ContractName");
if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.
                                  equals(account.getCustomerSystemApprovalCd())){
   allowPoEntry = false;
   allowShippingCommentsEntry = false;
   allowAccountComments = false;
}
%>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="clwWorkflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>


<html:form action="/store/checkout.do?action=submit"  name="CHECKOUT_FORM" method="POST" >

<table class="tbstd" width="<%=Constants.TABLEWIDTH%>"  cellpadding=0 cellspacing=0>
<% if(request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
<tr><td class="genericerror" align="center"><html:errors/></td></tr>
<% } %>
<tr >
<td class="checkoutSubHeader" valign="top">

<app:storeMessage key="shop.checkout.text.serviceShoppingCart"/>

</td>
</tr>
</table>

<table class="tbstd" width="<%=Constants.TABLEWIDTH%>"  cellpadding=0 cellspacing=0>
<logic:equal name="CHECKOUT_FORM" property="servicesSize" value="0">
<!-- No services in the shopping cart -->
<% if(request.getAttribute("org.apache.struts.action.ERROR")==null) { %>
<tr bgcolor="#F0FFFF">
<td class="text" align="center">
  <br><b><app:storeMessage key="shop.checkout.text.shoppiningCartIsEmpty"/></b><br>&nbsp;
</td>
</tr>
<tr>



<td class="tableoutline">
<img src="<%=IMGPath%>/cw_spacer.gif" height="3">
</td>

</tr>
<%}%>
</logic:equal>

<logic:greaterThan name="CHECKOUT_FORM" property="servicesSize" value="0">
<html:hidden name="CHECKOUT_FORM" property="siteId" value="<%=\"\"+theForm.getSite().getBusEntity().getBusEntityId()%>" />
<html:hidden name="CHECKOUT_FORM" property="userId" value="<%=\"\"+appUser.getUser().getUserId()%>" />
<%    if ( showPlaceOrderFields ) {   %>
  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_placeOrderFields.jsp")%>'>
     <jsp:param name="showPlaceOrderFields" value='<%=String.valueOf(showPlaceOrderFields)%>'/>
     <jsp:param name="showPlaceOrderButton" value='<%=String.valueOf(showPlaceOrderButton)%>'/>
     <jsp:param name="f_showCC" value='<%=String.valueOf(f_showCC)%>'/>
     <jsp:param name="f_showOther" value='<%=String.valueOf(f_showOther)%>'/>
     <jsp:param name="f_showPO" value='<%=String.valueOf(f_showPO)%>'/>
     <jsp:param name="allowPoEntry" value='<%=String.valueOf(allowPoEntry)%>'/>
     <jsp:param name="allowShippingCommentsEntry" value='<%=String.valueOf(allowShippingCommentsEntry)%>'/>
     <jsp:param name="allowAccountComments" value='<%=String.valueOf(allowAccountComments)%>'/>
     <jsp:param name="allowOrderConsolidationFl" value='<%=String.valueOf(Boolean.FALSE)%>'/>
     <jsp:param name="formId" value="CHECKOUT_FORM"/>
     <jsp:param name="orderServiceFl" value="<%=String.valueOf(Boolean.TRUE)%>"/>
  </jsp:include>
<% } /* End of the place order fields. */ %>

<table class="tbstd" width="<%=Constants.TABLEWIDTH%>"
  cellpadding=0 cellspacing=0>
<tr>
<td>

  <jsp:include flush='true' page="f_shoppingServices.jsp">
       <jsp:param name="formId" value="CHECKOUT_FORM"/>
  </jsp:include>
 </td>
</tr>
</table>



<table class="tbstd" width="<%=Constants.TABLEWIDTH%>" cellpadding=0 cellspacing=0 >
     <tr valign=top>
     <td align="center">
<table border="0" align="center" cellspacing="0" cellpadding="0">
<tr valign=top>
        <td valign=top>      <!-- Table order summary beg -->
     <table border="0" cellspacing="0" cellpadding="0" align="center">
<bean:define id="cartServiceAmt" name="CHECKOUT_FORM" property="cartServiceAmt"/>
        <tr>
<td colspan=3 class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
  <app:storeMessage key="shop.checkout.header.summary"/></td>
</tr>
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.checkout.text.subtotal"/></b></td>
<!-- cartServiceAmt -->
<td align="right"><%=ClwI18nUtil.getPriceShopping(request,cartServiceAmt,"&nbsp;")%></td>
<td> &nbsp;&nbsp;</td>
                </tr>



  <bean:define id="freightAmt" name="CHECKOUT_FORM" property="freightAmt"/>
  <% if (appUser.isaCustServiceRep() || crcManagerFl) { %>
    <tr><td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.freight"/></b></td>
      <td align="right"> <html:text name="CHECKOUT_FORM" property="freightAmtString" size="8"/></td>
    <td> &nbsp;&nbsp;</td>
    </tr>
  <% } else { %>
  <html:hidden name="CHECKOUT_FORM" property="freightAmtString" value="<%=theForm.getFreightAmtString()%>" />
  <logic:greaterThan name="CHECKOUT_FORM" property="freightAmt" value="0">
    <tr><td class="smalltext" > <b><app:storeMessage key="shop.checkout.text.freight"/></b> </td>
      <!-- freightAmt -->
      <td align="right"><%=ClwI18nUtil.getPriceShopping(request,freightAmt,"&nbsp;")%><bean:write name="CHECKOUT_FORM" property="estimateFreightString" /></td>
    <td> &nbsp;&nbsp;</td>
    </tr>
    </logic:greaterThan>
  <% } %>



<bean:define id="handlingAmt" name="CHECKOUT_FORM" property="handlingAmt" type="java.math.BigDecimal"/>
<%
String applyHandlingCharge = null;
try{
applyHandlingCharge = theForm.getSite().getSiteFieldValue("Apply contract handling charges");
  }catch(Exception e){e.printStackTrace();}

if (applyHandlingCharge != null &&
  applyHandlingCharge.toLowerCase().equals("n")) {
  handlingAmt = new java.math.BigDecimal("0");
  theForm.setHandlingAmt(handlingAmt);
}
%>

<% if (appUser.isaCustServiceRep() || crcManagerFl) { %>
  <tr><td class="smalltext">  <b><app:storeMessage key="shop.checkout.text.handling"/></b></td>
  <td align="right"> <html:text name="CHECKOUT_FORM" property="handlingAmtString" size="8"/></td>
<td> &nbsp;&nbsp;</td>
  </tr>
<% } else { %>
<html:hidden name="CHECKOUT_FORM" property="handlingAmtString" value="<%=theForm.getHandlingAmtString()%>" />
            <logic:greaterThan name="CHECKOUT_FORM" property="handlingAmt" value="0">
  <tr><td class="smalltext" >  <b><app:storeMessage key="shop.checkout.text.handling"/></b> </td>
  <!-- handlingAmt -->
  <td align="right"><%=ClwI18nUtil.getPriceShopping(request,handlingAmt,"&nbsp;")%></td>
<td> &nbsp;&nbsp;</td>
  </tr>
            </logic:greaterThan>
<% } %>
            <bean:define id="miscAmt"  name="CHECKOUT_FORM" property="miscAmt"/>
            <logic:greaterThan name="CHECKOUT_FORM" property="miscAmt" value="0">
<tr><td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.miscCharges"/></b></td>
<!-- miscAmt -->
<td align="right"><%=ClwI18nUtil.getPriceShopping(request,miscAmt,"&nbsp;")%></td>
<td> &nbsp;&nbsp;</td>
</tr>
            </logic:greaterThan>



<% if ( null != theForm.getSalesTax()&& theForm.getSalesTax().doubleValue()>0) { %>
<tr valign=top><td class="smalltext" > <b><app:storeMessage key="shop.checkout.text.tax"/></b></td>
<td align="right">

<!-- theForm.getSalesTax() -->
<%=ClwI18nUtil.getPriceShopping(request,theForm.getSalesTax(),"&nbsp;")%></td>
</tr>
<% } %>

<tr><td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.total"/></b></td>
<%	BigDecimal lTotal = theForm.getCartServiceAmt();
    lTotal = lTotal.add(theForm.getFreightAmt());
    lTotal = lTotal.add(handlingAmt);
    lTotal = lTotal.add(theForm.getSalesTax()); %>
 <td align="right">
   <!-- lTotal -->
   <%=ClwI18nUtil.getPriceShopping(request,lTotal,"&nbsp;")%><bean:write name="CHECKOUT_FORM" property="estimateFreightString" />
 </td>
<td> &nbsp;&nbsp;</td>
</tr>

     </table><!--Table order summary end -->
</td>
     <td align="center">
<table border="0" align="center" cellspacing="0" cellpadding="0">
<tr valign=top>
<td valign=top>     <!-- Table billto beg -->
     <table border="0" cellspacing="0" cellpadding="0" align="center">
        <tr>
        <td  class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
       <app:storeMessage key="shop.checkout.header.billingInformation"/>
     </td>
</tr><tr>
        <td class="smalltext">
            <div class="fivemargin">
        <b><app:storeMessage key="shop.checkout.text.accountName"/></b>&nbsp;<%=accountName%><br>
        </div>
</td>
</tr>

<tr><td>
<% if (appUser.canEditBillTo()) { %>
    <table>
<tr><td>  <b><app:storeMessage key="shop.checkout.text.address1"/></b></td>
    <td>  <html:text name="CHECKOUT_FORM"
          property="requestedBillToAddress.address1"/>
    </td>
</tr>
<tr><td>  <b><app:storeMessage key="shop.checkout.text.address2"/></b></td>
    <td>  <html:text name="CHECKOUT_FORM"
          property="requestedBillToAddress.address2"/>
    </td>
</tr>
<tr><td>  <b><app:storeMessage key="shop.checkout.text.address3"/></b></td>
    <td>  <html:text name="CHECKOUT_FORM"
          property="requestedBillToAddress.address3"/>
    </td>
</tr>

<tr><td> <b><app:storeMessage key="shop.checkout.text.city:"/></b></td>
    <td> <html:text name="CHECKOUT_FORM"
          property="requestedBillToAddress.city"/>
    </td>
</tr>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<tr><td> <b><app:storeMessage key="shop.checkout.text.state:"/></b></td>
    <td> <html:text name="CHECKOUT_FORM" size="2" maxlength="2"
          property="requestedBillToAddress.stateProvinceCd"/>
    </td>
</tr>
<%} %>
<tr><td> <b><app:storeMessage key="shop.checkout.text.zip:"/></b></td>
    <td> <html:text name="CHECKOUT_FORM"
          property="requestedBillToAddress.postalCode"/>
    </td>
</tr>
<tr><td> <b><app:storeMessage key="shop.checkout.text.country:"/></b></td>
    <td> <html:text name="CHECKOUT_FORM"
          property="requestedBillToAddress.countryCd"/>
    </td>
</tr>

    </table>

<% } else { %>

    <table>
<tr><td> <b><app:storeMessage key="shop.checkout.text.address1"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.address1"/></td>
</tr>
        <% String address2 = theForm.getAccount().getBillingAddress().getAddress2(); %>
        <% if(address2!=null && address2.trim().length()>0) { %>
<tr><td> <b><app:storeMessage key="shop.checkout.text.address2"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.address2"/></td>
</tr>
        <% } %>
        <% String address3 = theForm.getAccount().getBillingAddress().getAddress3(); %>
        <% if(address3!=null && address3.trim().length()>0) { %>
<tr><td> <b><app:storeMessage key="shop.checkout.text.address3"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.address3"/></td>
</tr>
        <% } %>
        <% String address4 = theForm.getAccount().getBillingAddress().getAddress4(); %>
        <% if(address4!=null && address4.trim().length()>0) { %>
<tr><td> <b><app:storeMessage key="shop.checkout.text.address4"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.address4"/></td>
</tr>
        <% } %>

<tr><td> <b><app:storeMessage key="shop.checkout.text.city:"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.city"/></td>
</tr>

<%
if (appUser.getUserStore().isStateProvinceRequired()) {
%>
<tr><td> <b><app:storeMessage key="shop.checkout.text.state:"/></b></td>
  <td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.stateProvinceCd"/></td>
</tr>
<% } %>

<tr><td> <b><app:storeMessage key="shop.checkout.text.zip:"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.postalCode"/></td>
</tr>
<tr><td> <b><app:storeMessage key="shop.checkout.text.country:"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.countryCd"/></td>
</tr>
</table>

<% } %>
</td></tr>

     </table>
     </td>
     <td  valign=top>
     <table border="0" cellspacing="0" cellpadding="0" align="center">
        <tr>
  <td colspan="2"  class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
   <app:storeMessage key="shop.checkout.header.shippingInformation"/>
  </td>
</tr>


<tr valign=top><td>

<table>
<tr><td>  <b><app:storeMessage key="shop.checkout.text.address1"/></b></td>
    <td>  <bean:write name="CHECKOUT_FORM"
          property="site.siteAddress.address1"/>
    </td>
</tr>
<tr><td>  <b><app:storeMessage key="shop.checkout.text.address2"/></b></td>
    <td>  <bean:write name="CHECKOUT_FORM"
          property="site.siteAddress.address2"/>
    </td>
</tr>
<tr><td>  <b><app:storeMessage key="shop.checkout.text.address3"/></b></td>
    <td>  <bean:write name="CHECKOUT_FORM"
          property="site.siteAddress.address3"/>
    </td>
</tr>

<tr><td> <b><app:storeMessage key="shop.checkout.text.city:"/></b></td>
    <td> <bean:write name="CHECKOUT_FORM"
          property="site.siteAddress.city"/>
    </td>
</tr>
<% if (appUser.getUserStore().isStateProvinceRequired()) {%>
<tr><td> <b><app:storeMessage key="shop.checkout.text.state:"/></b></td>
  <td> <bean:write name="CHECKOUT_FORM"
    property="site.siteAddress.stateProvinceCd"/>
  </td>
</tr>
<% } %>
<tr><td> <b><app:storeMessage key="shop.checkout.text.zip:"/></b></td>
    <td> <bean:write name="CHECKOUT_FORM"
          property="site.siteAddress.postalCode"/>
    </td>
</tr>
<tr><td> <b><app:storeMessage key="shop.checkout.text.country:"/></b></td>
    <td> <bean:write name="CHECKOUT_FORM"
          property="site.siteAddress.countryCd"/>
    </td>
</tr>

          </table>


</td></tr>


     </table><!--Table shipto end -->
     </td>

</tr>
</table>

<table align=right>
<tr>

<% if ( showOrderSelected ) {   %>
<td>
    <a href="#" class="linkButton" onclick="setAndSubmit('CHECKOUT_FORM','command','orderService');">
        <img src='<%=IMGPath + "/b_placeorder.gif"%>' border="0"/><app:storeMessage key="global.label.placeOrder"/></a>
</td>
<%
  } %>
</tr></table>

</logic:greaterThan>

</td></tr>
</table>
  </td>
  </tr>
 </table>
<%@ include file="f_table_bottom.jsp" %>

  <html:hidden property="action" value="submit"/>
  <html:hidden property="command" value="selectedOrderService"/>

</html:form>



<!-- CRC INFO -->
<%
  if(userType.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
     userType.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
     userType.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER) ||
	 crcManagerFl){
%>

<table align="center" border="0" cellspacing="0" cellpadding="0" class="textreadonly">
<tr><td>
<b><app:storeMessage key="shop.checkout.text.accountName"/></b>  <%=accountName%> </td></tr>
<tr><td>
<b><app:storeMessage key="shop.checkout.text.accountErp"/></b> <%=accountNumber%>  </td></tr>
<tr><td><b><app:storeMessage key="shop.checkout.text.siteName"/></b> <%=siteName %> </td></tr>
<tr><td><b><app:storeMessage key="shop.checkout.text.siteErp"/></b><%=siteErp%> </td></tr>
<tr><td><b><app:storeMessage key="shop.checkout.text.contractName"/></b> <%=contractName %> </td></tr>
</table>

<% } %>




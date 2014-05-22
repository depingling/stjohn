<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<%  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER); %>
<script language="JavaScript1.2">
<!--
function viewPrinterFriendly(oid) {
  var loc = "printerFriendlyOrder.do?action=printPdf&orderId=" + oid;
  <% if (appUser.getUserAccount().isHideItemMfg()) { %>
    loc = loc + "&showMfg=n&showMfgSku=n";
  <% } %>
  prtwin = window.open(loc,"printer_friendly",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}
//-->
function f_hideBox(boxid) {
  if (document.getElementById(boxid) != null){
    document.getElementById(boxid).style.visibility = 'hidden';
  }
}
function f_showBox(boxid) {
  if (document.getElementById(boxid) != null){
    document.getElementById(boxid).style.visibility = 'visible';
  }
}

function f_changeVisibility(boxid) {
  if (document.getElementById(boxid) != null){
    var state = document.getElementById(boxid).style.visibility;
    if (state.match('hidden')){
      f_showBox(boxid);
    }else {
      f_hideBox(boxid);
    }
  }
}



</script>


<bean:define id="theForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>

<%
  boolean isHideForNewXpdex = true;
 ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
 //CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
 AccountData account = appUser.getUserAccount();
 String allowOrderConsolidationS =
   account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION);
 boolean consolidatedOrderFl =(shoppingCart instanceof ConsolidatedCartView)? true:false;

 boolean allowOrderConsolidationFl =
   (allowOrderConsolidationS != null && allowOrderConsolidationS.length() > 0 &&
      "T".equalsIgnoreCase(allowOrderConsolidationS.substring(0, 1)))? true:false;
 if(consolidatedOrderFl) allowOrderConsolidationFl = false;

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
if (appUser.getCreditCardFlag()) {
  pmtopts.add("CC");
}
if (appUser.getOnAccount()) {
  pmtopts.add("PO");
}
if (appUser.getOtherPaymentFlag()) {
  pmtopts.add("Other");
}
String reqpmt = "";
boolean f_showPO = false, f_showCC = false, f_showOther = false;
if ( appUser.canMakePurchases() ) {
 showPlaceOrderFields = true;
 reqpmt = theForm.getReqPmt();
 if (null == reqpmt || "0".equals(reqpmt) ) {
   if ( appUser.getCreditCardFlag() )
   { reqpmt = "CC";}
   else { reqpmt = "PO"; }
 }
 if (reqpmt.equals("PO")) {
   f_showPO = true;
 }
 if (reqpmt.equals("CC")) {
   f_showCC = true;
   if(appUser.getUserStore().getStoreId() == 173243){f_showPO = true;}
 }

 if (reqpmt.equals("Other")) {
   f_showOther = true;
 }

 if (      appUser.getCreditCardFlag()
  && appUser.getOtherPaymentFlag() == false
  && appUser.getOnAccount() == false	) {
  // The user is allowed to use credit cards and no other option has
  // been choosen.  By default, we show the PO.
  f_showCC = true;
  f_showPO = false;
  if(appUser.getUserStore().getStoreId() == 173243){f_showPO = true;}
 }

}

boolean allowShippingCommentsEntry = true;
boolean allowAccountComments = true;
boolean allowPoEntry = true;

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
//if(appUser.getUserStore().getStoreId() == 173243){allowPoEntry = true;}

boolean allowPoByVender = false;
if (f_showPO && allowPoEntry){
	allowPoByVender = Utility.isTrue(PropertyUtil.getPropertyValue(appUser.getUserStore().getMiscProperties(),
    		 		RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER));
}
BigDecimal rushOrderCharge = new BigDecimal(0);
OrderData prevOrder = shoppingCart.getPrevOrderData();
%>
<%
    /* -- -------   ----   PO  block -- */

    String newpoval = "",
           currPoval = theForm.getPoNumber();
    if ( currPoval == null || currPoval.length() == 0 ) {
      currPoval = shoppingCart.getSavedPoNumber();
    }
    newpoval = currPoval;

    if ( prevOrder != null
            && ( currPoval == null || currPoval.length() == 0)
            ) {

      if ( prevOrder.getRefOrderNum() != null ) {
        newpoval = prevOrder.getRefOrderNum();
      }
    }

    if(!appUser.getUserAccount().isCustomerRequestPoAllowed()){
      allowPoEntry=false;
    }

    boolean usingBlanketPo = false;
    if(theForm.getSite().getBlanketPoNum() != null && theForm.getSite().getBlanketPoNum().getBlanketPoNumId() != 0){
      usingBlanketPo=true;
      newpoval = "";
      allowPoEntry=false;
    }

    boolean showPoField = false;
    if((allowPoEntry && f_showPO) || (usingBlanketPo)){
      showPoField = true;
    }
  %>
    <% /* -- -------   ----    comments block -- */
    String newcomments = "",
            currComments = theForm.getComments();
    if ( currComments == null || currComments.length() == 0 ) {
      currComments = shoppingCart.getSavedComments();
    }

    if ( currComments == null ) currComments  = "";

    newcomments = currComments;

    if ( prevOrder != null && currComments.length() == 0) {

      if ( prevOrder.getRequestPoNum() != null ) {
        newcomments = prevOrder.getRequestPoNum() ;
      }

      if ( prevOrder.getRefOrderNum() != null ) {
        if (newcomments.length() > 0 ) {
          newcomments += "/";
        }
        newcomments += prevOrder.getRefOrderNum();
      }

      if ( prevOrder.getComments() != null ) {
        if (newcomments.length() > 0 ) {
          newcomments += "/";
        }
        newcomments += prevOrder.getComments();
      }


    }

    String prevOrderContactName = theForm.getOrderContactName();
    String prevOrderContactPhoneNum = theForm.getOrderContactPhoneNum();
    String prevOrderContactEmail = theForm.getOrderContactEmail();
    String prevOrderMethod = theForm.getOrderOriginationMethod();


    if ( appUser.isaCustServiceRep()) {

      if ( prevOrder != null ) {
        // Copy in the values from the previous order.
        prevOrderContactName = prevOrder.getOrderContactName();
        if ( prevOrderContactName == null ) prevOrderContactName = "";

        prevOrderContactPhoneNum = prevOrder.getOrderContactPhoneNum();
        if ( prevOrderContactPhoneNum == null ) prevOrderContactPhoneNum = "";

        prevOrderContactEmail = prevOrder.getOrderContactEmail();
        if ( prevOrderContactEmail == null ) prevOrderContactEmail = "";

        prevOrderMethod = prevOrder.getOrderSourceCd();
        if ( prevOrderMethod == null ) prevOrderMethod = "";
        /* Do not allow EDI as an option for web orders. */
        if ( prevOrderMethod.equals("EDI")) prevOrderMethod = "Other";
      }

    }
  %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="clwWorkflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>

<html:form action="/store/checkout.do?action=submit"  name="CHECKOUT_FORM" method="POST" >

<%--------------------------bread crumb---------------------------------%>
<table class="breadcrumb" align="left">
<tr class="breadcrumb">
  <td class="breadcrumb">
    <a class="breadcrumb" href="../userportal/msbsites.do">
      <app:storeMessage key="global.label.Home"/></a>
  </td>
  <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
  <%--
  <td class="breadcrumb">
    <a class="breadcrumb" href="../store/shoppingcart.do"><app:storeMessage key="shoppingCart.text.viewCart"/></a>
  </td>
  <td> class="breadcrumb"<span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
  <td class="breadcrumb">
    <a class="breadcrumb" href="../store/checkout.do"><app:storeMessage key="global.action.label.checkout"/></a>
  </td>
  <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
  --%>
  <td class="breadcrumb">
    <%--<a class="breadcrumb" href="#"><app:storeMessage key="shoppingCart.text.confirmation"/></a>--%>    
    <div class="breadcrumbCurrent"><app:storeMessage key="shoppingCart.text.confirmation"/></div>
  </td>

</tr>
</table>
<br>
<%--------------------------Business rule applicable Warnings----------------------------------%>
<table  width="<%=Constants.TABLEWIDTH800%>"  cellpadding=0 cellspacing=0>
  <% if(request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
  <tr><td class="genericerror" align="left" style="color:#FF0000; text-transform:uppercase"><html:errors/></td></tr>
  <% } %>
</table>
<br>
<%--------------------------Cart Is Empty View----------------------------------%>
<table width="<%=Constants.TABLEWIDTH800%>"  cellpadding=0 cellspacing=0>
<logic:equal name="CHECKOUT_FORM" property="itemsSize" value="0">
<!-- No items in the shopping cart -->
<% if(request.getAttribute("org.apache.struts.action.ERROR")==null) { %>
<tr class="orderInfoHeader">
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
</table>

<%--------------------------review Items Note----------------------------------%>
<table width="<%=Constants.TABLEWIDTH800%>"  cellpadding=0 cellspacing=0  border="0" >
  <% String confNumberMessage = theForm.getConfirmMessage();
  if (confNumberMessage == null){
    confNumberMessage = "";//"Order Number '{0}' has been successfully submitted." ;
  }
  Object[] param = new Object[2];
  //String link = "<a class='text' href='#' style='text-decoration:none;'><app:storeMessage key='shop.checkout.text.hereLink'/></a>"
  String linkMess = ClwI18nUtil.getMessage(request, "shop.checkout.text.hereLink", null );
  param[0] = theForm.getOrderResult().getOrderNum().toString();
 // param[1] = "<a class=\"text\" style=\"TEXT-DECORATION: underline;background-color: #FFFFFF;color:#736F6E; font-weight:bold;\" href=\"#\" onclick=\"f_changeVisibility('INFO')\">"+linkMess+ "</a>";
	
  param[1] = ":::::::";
  String link = "<a class=\"hereLink\" href=\"#\" onclick=\"f_changeVisibility('INFO')\">"+linkMess+ "</a>";
	
  String confMessage = ClwI18nUtil.getMessage(request, "shop.checkout.text.actionMessage.confirmationMessXpedx", param );
  confMessage = confMessage.replace(param[1].toString(), link);
  %>
  <% 
  if(confMessage != null && confMessage.length() > 0){
  %>
  <tr>
  	<td align="center" class="text" style = "font-size =12pt;">
  		<b>
  			<font color="#003399"><%=confMessage%></font></b>
  	</td> 
  </tr>
  <tr>
  	<td align="center">&nbsp;</td> 
  </tr>
  <% }else{ %>
  <tr>
  	<td align="center" class="text" style = "font-size =12pt;">
  		<b>
    		<font color="#003399"><%=confNumberMessage %></font>
  		</b>
  	</td>
  </tr>
  <%
  }
   %>
</table>
<br>

<div id="INFO"  style="visibility:hidden">
<table  width="<%=Constants.TABLEWIDTH800%>"  cellpadding=0 cellspacing=0>
  <tr>  <td colspan="2" class="tableoutline"><img src="images/spacer.gif" height="1"></td>  </tr>

  <tr>
<%------------------Shipping Information------------------%>
 <td  valign=top>
     <table border="0" cellspacing="0" cellpadding="0" align="left">

        <tr>
          <td colspan="2"  >
            <b><app:storeMessage key="shop.checkout.header.shippingInfo"/></b>
          </td>
        </tr>

        <tr valign=top>
          <td>
          <%if ( !ShopTool.isAnOCISession(request.getSession()) ) {
                if (theForm.getAllowAlternateShipTo() && 
                        (Utility.isSet(theForm.getAlternateAddress1()) ||
                         Utility.isSet(theForm.getAlternateAddress2()) ||
                         Utility.isSet(theForm.getAlternateAddress3()) ||
                         Utility.isSet(theForm.getAlternateCity()) ||
                         Utility.isSet(theForm.getAlternateStateProvince()) ||
                         Utility.isSet(theForm.getAlternatePostalCode()) ||
                         Utility.isSet(theForm.getAlternateCountry()))
                        ) { 
                %>
                    <table>
                        <tr>
                            <td align="right">Location:</td>
                            <td>&nbsp;<bean:write name="CHECKOUT_FORM" property="site.busEntity.shortDesc"/></td>
                        </tr>
                        <tr>
                            <td align="right">
                                <app:storeMessage key="shop.checkout.text.phone:"/>
                            </td>
                            <td>
                                &nbsp;<bean:write name="CHECKOUT_FORM" property="orderContactPhoneNum"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                <app:storeMessage key="shop.checkout.text.address1"/>
                            </td>
                            <td>
                                &nbsp;<bean:write name="CHECKOUT_FORM" property="alternateAddress1"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                <app:storeMessage key="shop.checkout.text.address2"/>
                            </td>
                            <td>
                                &nbsp;<bean:write name="CHECKOUT_FORM" property="alternateAddress2"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                <app:storeMessage key="shop.checkout.text.address3"/>
                            </td>
                            <td>
                                &nbsp;<bean:write name="CHECKOUT_FORM" property="alternateAddress3"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                <app:storeMessage key="shop.checkout.text.city:"/>
                            </td>
                            <td>
                                &nbsp;<bean:write name="CHECKOUT_FORM" property="alternateCity"/>
                            </td>
                        </tr>
                        <% if (appUser.getUserStore().isStateProvinceRequired()) {%>
                        <tr>
                            <td align="right">
                                <app:storeMessage key="shop.checkout.text.state:"/>
                            </td>
                            <td>
                                &nbsp;<bean:write name="CHECKOUT_FORM" property="alternateStateProvince"/>
                            </td>
                        </tr>
                        <% } %>
                        <tr>
                            <td align="right">
                                <app:storeMessage key="shop.checkout.text.zip:"/>
                            </td>
                            <td>
                                &nbsp;<bean:write name="CHECKOUT_FORM" property="alternatePostalCode"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                <app:storeMessage key="shop.checkout.text.country:"/>
                            </td>
                            <td>
                                &nbsp;<bean:write name="CHECKOUT_FORM" property="alternateCountry"/>
                            </td>
                        </tr>
                    </table>
              <%} else {%>
                    <table>
        			<tr>
        				<td align="right">Location:</td>
        				<td>&nbsp;<bean:write name="CHECKOUT_FORM" property="site.busEntity.shortDesc"/></td>
        			</tr>
                    <tr>
                        <td align="right">  <app:storeMessage key="shop.checkout.text.address1"/></td>
                        <td>
                          &nbsp;<bean:write name="CHECKOUT_FORM"  property="site.siteAddress.address1"/>
                       </td>
                    </tr>
                    <tr><td align="right">  <app:storeMessage key="shop.checkout.text.address2"/></td>
                        <td>&nbsp;<bean:write name="CHECKOUT_FORM" property="site.siteAddress.address2"/>
                        </td>
                     </tr>
                     <tr><td align="right"> <app:storeMessage key="shop.checkout.text.address3"/></td>
                       <td>&nbsp;<bean:write name="CHECKOUT_FORM" property="site.siteAddress.address3"/>
                       </td>
                     </tr>

                     <tr><td align="right"><app:storeMessage key="shop.checkout.text.city:"/></td>
                       <td>&nbsp;<bean:write name="CHECKOUT_FORM" property="site.siteAddress.city"/>
                       </td>
                    </tr>
                    <% if (appUser.getUserStore().isStateProvinceRequired()) {%>
                    <tr><td align="right"> <app:storeMessage key="shop.checkout.text.state:"/></td>
                      <td> <bean:write name="CHECKOUT_FORM"
                      property="site.siteAddress.stateProvinceCd"/>
                      </td>
                    </tr>
                   <% } %>
                   <tr><td align="right"> <app:storeMessage key="shop.checkout.text.zip:"/></td>
                     <td> <bean:write name="CHECKOUT_FORM"
                     property="site.siteAddress.postalCode"/>
                     </td>
                   </tr>
                   <tr><td align="right"> <app:storeMessage key="shop.checkout.text.country:"/></td>
                     <td> <bean:write name="CHECKOUT_FORM"
                     property="site.siteAddress.countryCd"/>
                     </td>
                   </tr>
                  </table>
           <%   } 
            }
            %>
     </td></tr>
     </table><!--Table shipto end -->
  </td>
  <td align="right" valign="top" style ="visibility:hidden;">
    <% String b_print = IMGPath + "/b_print.gif"; %>
    <a href="#" class="linkButton" onclick="viewPrinterFriendly('<%=theForm.getOrderResult().getOrderId()%>');"
      ><img src="<%=b_print%>" border="0"/><app:storeMessage key="global.label.printerFriendly"/></a>
  </td>
<%------------end---Shipping Information------------------%>
</tr></table>
<%--------------------------Shopping Items----------------------------------%>
<table  width="<%=Constants.TABLEWIDTH800%>"  cellpadding=0 cellspacing=0>
<tr>
<td>

      <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_confirmShoppingItems.jsp")%>'>
        <jsp:param name="allowEdits" value="false"/>
        <jsp:param name="showSelectCol" value="true"/>
        <jsp:param name="view" value="checkout"/>
        <jsp:param name="orderBy" value="<%=theForm.getSortBy()%>"/>
        <jsp:param name="formId" value="CHECKOUT_FORM"/>
      </jsp:include>

</td>
</tr>

</table>
<%-------------------summary/-Billing Info/Shoppin Info-----------------%>
<table width="<%=Constants.TABLEWIDTH800%>"   cellpadding=0 cellspacing=0  border="0">
<tr>  <td class="tableoutline" colspan="2"><img src="images/spacer.gif" height="1"></td>  </tr>

<tr valign=top>
      <%-------Shipping Comments && PO #-------%>
      <td valign=top width="66%">
          <table border="0" cellspacing="0" cellpadding="0" align="left">
          <tr>
          <%if (allowAccountComments && appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_CUSTOMER_ORDER_NOTES)){%>
            <td class="text">
           <%---------internal Comments-----------%>
             <b><app:storeMessage key="shop.checkout.text.customerComment" arg0="<%=accountName%>" />:</b>
             <bean:write name="CHECKOUT_FORM" property="customerComment"/>
            </td>
          <%}else{%>
            <td >&nbsp;</td>
          <%}%>
          </tr>
           <%-------Shipping Comments -------%>
           <tr>
             <%if(allowShippingCommentsEntry && !appUser.getUserAccount().hideShippingComments()
							&& appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_SHIPPING_ORDER_COMMENTS)){%>
             <td class="text" valign="top" align="left"  >
               <b><app:storeMessage key="shop.checkout.text.shippingComments"/></b>
               <%--  <html:text name="CHECKOUT_FORM" property="comments" size="40" maxlength="254" value="<%=newcomments%>" /><br>--%>
             <bean:write name="CHECKOUT_FORM" property="comments"/>
             </td>
             <%}else{%>
             <td  >&nbsp;</td>
             <%}%>
             <%--is authorized for ADD_CUSTOMER_ORDER_NOTES check--%>
           </tr>
            <%-------PO #-------%>
           <tr>
               <td align="left"  nowrap="nowrap" class="text"  valign="top">
                 <%if(!allowPoByVender && (allowPoEntry && f_showPO)){%>
                     <b><app:storeMessage key="shop.checkout.text.poNum"/>  </b>
                     <bean:write name="CHECKOUT_FORM" property="poNumber"/>
                     <br>
                  <%}else if(usingBlanketPo){%>
                       <b><app:storeMessage key="shop.checkout.text.poNumber"/>  </b>
                       <bean:write name="CHECKOUT_FORM" property="site.blanketPoNum.poNumber"/>
                       <br>
                  <%}%>
               </td>
           </tr>
          </table>
     </td>
      <td valign=top width="33%">
 <%------------------ Table Summary beg --------------------------------%>
          <table border="0" cellspacing="0" cellpadding="0" align="right">
    <%------------------Summary :: SubTotal------------------%>
            <%BigDecimal cartAmt = (theForm.getCartAmt(request)); %>
            <tr>
              <td  colspan="2"  align="right" class="smalltext"><b><app:storeMessage key="shop.checkout.text.sTotal"/></b></td>
              <!-- cartAmt -->
              <td align="right"><%=ClwI18nUtil.getPriceShopping(request,cartAmt,"&nbsp;")%></td>
            </tr>

	<%------------------Service Fee Code --------------------%>
	<%
		//Get account property
		String addServiceFee = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
		if(addServiceFee.equals("true")){
		    BigDecimal serviceFeeAmt = (theForm.getServiceFeeAmt()); 
	%>
		<tr>
			<td  colspan="2"  align="right" class="smalltext"><b>Service Fee:</b></td>
			<td align="right" class="checkoutInformation"><%=ClwI18nUtil.getPriceShopping(request,serviceFeeAmt,"&nbsp;")%></td>
		</tr>
	<% } %>			
			
    <%------------------Summary :: Handling------------------%>
          <bean:define id="handlingAmt" name="CHECKOUT_FORM"  property="handlingAmt" type="java.math.BigDecimal"/>
          <%
          if ( prevOrder != null && prevOrder.getTotalMiscCost() != null &&
            prevOrder.getTotalMiscCost().intValue() > 0 ) {
              if ( handlingAmt == null || handlingAmt.intValue() <= 0 ) {
                String p = prevOrder.getOrderSourceCd();
                if ( null == p ) p = "";
                if ( ! p.equals("Inventory") ) {
                  // Don't carry over freight charges on inventory orders.
                  // Let the system re-compute them.
                  handlingAmt = prevOrder.getTotalMiscCost();
                  theForm.setHandlingAmt(handlingAmt);
                }
              }
            }
            String applyHandlingCharge = null;
            try{
              applyHandlingCharge = theForm.getSite().getSiteFieldValue
              ("Apply contract handling charges");
            }catch(Exception e){e.printStackTrace();}

            if (applyHandlingCharge != null &&
            applyHandlingCharge.toLowerCase().equals("n")) {
              handlingAmt = new java.math.BigDecimal("0");
              theForm.setHandlingAmt(handlingAmt);
            }
            %>
            <% if (appUser.isaCustServiceRep()) { %>
            <tr><td  colspan="2"  align="right" class="smalltext">  <b><app:storeMessage key="shop.checkout.text.estimatedHandling"/></b></td>
              <td align="right"> <html:text name="CHECKOUT_FORM" property="handlingAmtString" size="8"/></td>
              <td> &nbsp;&nbsp;</td>
            </tr>
            <% } else { %>
            <html:hidden name="CHECKOUT_FORM" property="handlingAmtString" value="<%=theForm.getHandlingAmtString()%>" />
             <%-- <logic:greaterThan name="CHECKOUT_FORM" property="handlingAmt" value="0">--%>
                <tr><td  colspan="2"  align="right" class="smalltext" >  <b><app:storeMessage key="shop.checkout.text.estimatedHandling"/></b> </td>
                  <!-- handlingAmt -->
                  <td align="right"><%=ClwI18nUtil.getPriceShopping(request,handlingAmt,"&nbsp;")%></td>

                </tr>
             <%-- </logic:greaterThan>--%>
            <% } %>
    <%------------------end of  Handling------------------%>
    <%-------------------Summary :: Freight-Dropdown----------------%>
    <bean:define id="SCartDistV" name="CHECKOUT_FORM" property="cartDistributors" type="ShoppingCartDistDataVector"/>

    <logic:iterate id="scdistD" name="SCartDistV"  offset="0" indexId="DISTIDX"  type="com.cleanwise.service.api.value.ShoppingCartDistData">
    <%
    ShoppingCartDistData ddd =  (ShoppingCartDistData)(theForm.getCartDistributors().get(DISTIDX));

    if (null != scdistD.getDistFreightOptions() && 0 < scdistD.getDistFreightOptions().size()) {
    %>
    <bean:define id="freightOptions" name="scdistD" property="distFreightOptions"  type="java.util.List"/>
    <bean:define id="freightImplied" name="scdistD" property="distFreightImplied"  type="java.util.List"/>

    <tr>

      <td  nowrap="nowrap"><b><app:storeMessage key="shop.checkout.text.EstimatedFreight"/></b></td>
      <td  align="right" > (<bean:write name="scdistD" property="distSelectableFreightVendorName" />)<b>:</b> </td>
      <td align="right">
        <bean:define id="selectedFrMsg" name="scdistD" property="distSelectableFreightMsg" type="String"/>
        <bean:define id="selectedFrAmt" name="scdistD" property="distSelectableFreightCost" />
    <%		if (null != selectedFrMsg && selectedFrMsg.trim().length() > 0) {  %>
        <bean:write name="scdistD" property="distSelectableFreightMsg" />
    <%		} else {  %>
        <%=ClwI18nUtil.getPriceShopping(request,selectedFrAmt,"&nbsp;")%>
    <%		}  %>
      </td>
    </tr>
    <% 	} else {  %>
      <html:hidden name="CHECKOUT_FORM" property="distFreightVendor" value=""/>
    <%	}  %>
   <%--	}  --%>
   </logic:iterate>
<%if (SCartDistV != null) {
boolean showF = false;
	for(int i=0; i<SCartDistV.size(); i++){
		ShoppingCartDistData scdd = (ShoppingCartDistData)SCartDistV.get(i);
		if(scdd.getDistFreightOptions().size() > 0){
			showF = true;
		}
	}
	
	if(showF){
%>
<logic:iterate id="impliedD" name="freightImplied"
         offset="0" indexId="impliedIDX"
         type="com.cleanwise.service.api.value.FreightTableCriteriaData">
     <tr><td colspan="2" nowrap="nowrap" align="right"><b><%=impliedD.getShortDesc()%>:</td>
<td align="right">
<logic:equal name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                <%--should be i18n, but this is hard coded in the ShoppingCartDistData object as well.  Do both at same time to be consistent--%>
                        To Be Determined
                </logic:equal>
                <logic:notEqual name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                <logic:present name="impliedD" property="freightAmount">
                <bean:define id="frAmt" name="impliedD" property="freightAmount" />
                <%=ClwI18nUtil.getPriceShopping(request,frAmt,"&nbsp;")%>
                </logic:present>
                <logic:notPresent name="impliedD" property="freightAmount">
                <%=ClwI18nUtil.getPriceShopping(request,new java.math.BigDecimal(0),"&nbsp;")%>
                </logic:notPresent>
                </logic:notEqual>
</td>
</tr>
    </logic:iterate>
<%}
}%>

              <% if (!isHideForNewXpdex) { %>
              <bean:define id="miscAmt"  name="CHECKOUT_FORM" property="miscAmt"/>
              <logic:greaterThan name="CHECKOUT_FORM" property="miscAmt" value="0">
                <tr><td  colspan="2"  align="right" class="smalltext"> <b><app:storeMessage key="shop.checkout.text.miscCharges"/></b></td>
                  <!-- miscAmt -->
                  <td align="right"><%=ClwI18nUtil.getPriceShopping(request,miscAmt,"&nbsp;")%></td>

                </tr>
              </logic:greaterThan>
              <%} %>
              <%------------------Summary ::  Tax------------------%>
              <% if ( null != theForm.getSalesTax()) { %>
              <tr valign=top><td  colspan="2"  align="right" class="smalltext" > <b><app:storeMessage key="shop.checkout.text.estimatedTax"/></b></td>
                <td align="right">
                  <!-- theForm.getSalesTax() -->
                  <%=ClwI18nUtil.getPriceShopping(request,theForm.getSalesTax(),"&nbsp;")%>
                </td>
              </tr>
              <% } %>
              <%------------------Summary ::  Grand Total------------------%>
              <tr><td  colspan="2"  align="right" class="smalltext"> <b><app:storeMessage key="shop.checkout.text.grandTotal"/></b></td>
                <%	BigDecimal lTotal = theForm.getCartAmt(request);
                lTotal = lTotal.add(theForm.getFreightAmt());
                lTotal = lTotal.add(handlingAmt);
                lTotal = lTotal.add(rushOrderCharge);
                lTotal = lTotal.add(theForm.getSalesTax()); 
				lTotal = lTotal.add(theForm.getServiceFeeAmt());%>
                <td align="right">
                  <!-- lTotal -->
                  <%=ClwI18nUtil.getPriceShopping(request,lTotal,"&nbsp;")%><bean:write name="CHECKOUT_FORM" property="estimateFreightString" />
                </td>

               </tr>
          </table><!--Table order summary end -->
       </td>
<%-------------end-----Summary ------------------%>
<%--    place of shipping --%>
</tr>
</table>


<%--</logic:greaterThan>   -- not balanced! NG

</td></tr>
</table>--%>

<% if(consolidatedOrderFl) { %>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"assocOrder.jsp")%>'/>
<% } %>

</html:form>
<%-- } --%>


<!-- CRC INFO -->
<%
  if(userType.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
     userType.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
     userType.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)){
%>

<table align="center" border=0" cellspacing="0"
  cellpadding="0" class="textreadonly">
<tr><td>
<b><app:storeMessage key="shop.checkout.text.accountName"/></b>  <%=accountName%> </td></tr>
<tr><td>
<b><app:storeMessage key="shop.checkout.text.accountErp"/></b> <%=accountNumber%>  </td></tr>
<tr><td><b><app:storeMessage key="shop.checkout.text.siteName"/></b> <%=siteName %> </td></tr>
<tr><td><b><app:storeMessage key="shop.checkout.text.siteErp"/></b><%=siteErp%> </td></tr>
<tr><td><b><app:storeMessage key="shop.checkout.text.contractName"/></b> <%=contractName %> </td></tr>
</table>

<% } %>

</div>




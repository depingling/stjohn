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

<script language="JavaScript1.2">
<!--

function setAndSubmit(fid, vv, value) {
 var aaa = document.forms[fid].elements[vv];
 aaa.value=value;
 aaa.form.submit();
 return false;
 }

 function limitText(limitField, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	}
}


//-->

</script>

<bean:define id="theForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>


<%
  boolean isHideForNewXpdex = true;
 ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
 CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
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

//
// Doug requested that the CC option be listed
// first (default) if the user is set up for CC purchases.
// durval 2006-9-13
//

String reqpmt = "";
boolean f_showPO = false;
boolean f_showCC = false;

if ( appUser.canMakePurchases() ) {
    showPlaceOrderFields = true;
    reqpmt = theForm.getReqPmt();
    if (null == reqpmt || "0".equals(reqpmt) ) {
        if ( appUser.getCreditCardFlag() ) {
            reqpmt = "CC";
        } else {
            reqpmt = "PO";
        }
    }
    if (reqpmt.equals("PO")) {
        f_showPO = true;
    }
    if (reqpmt.equals("CC")) {
        f_showCC = true;
        if(appUser.getUserStore().getStoreId() == 173243){
            f_showPO = true;
        }
    }
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

boolean isShowCreditCardSection = Utility.isTrue(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CC_PAYMENT));
boolean isCCPmt = (theForm.getReqPmt() != null && theForm.getReqPmt().equals("CC"));



boolean allowShippingComments = true;
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
   allowAccountComments = false;
   allowShippingComments = false;
}
//if(appUser.getUserStore().getStoreId() == 173243){allowPoEntry = true;}

boolean allowPoByVender = false;
if (f_showPO && allowPoEntry){
	allowPoByVender = Utility.isTrue(PropertyUtil.getPropertyValue(appUser.getUserStore().getMiscProperties(),
    		 		RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER));
}
//BigDecimal rushOrderCharge = new BigDecimal(0);
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
  <td class="breadcrumb">
    <a class="breadcrumb" href="../store/shoppingcart.do"><app:storeMessage key="shoppingCart.text.viewCart"/></a>
  </td>
  <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
  <td class="breadcrumb">
    <%--<a class="breadcrumb" href="#"><app:storeMessage key="global.action.label.checkout"/></a>--%>
    <div class="breadcrumbCurrent"><app:storeMessage key="global.action.label.checkout"/></div>
  </td>
</tr>
</table>
<br><br>
<%--------------------------Business rule applicable Warnings----------------------------------%>
<table  width="<%=Constants.TABLEWIDTH800%>"  cellpadding=0 cellspacing=0>
  <% if(request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
  <tr><td class="genericerror" align="left" style="color:#FF0000; text-transform:uppercase"><html:errors/></td></tr>
  <% } %>
</table>
<%
        java.util.List warnings = theForm.getWarningMessages();
        theForm.setWarningMessages(null);
        if(warnings != null){
        for (int widx = 0; widx < warnings.size(); widx++) {
            String warningMsg = (String) warnings.get(widx);
%>

<table cellpadding="0" cellspacing="0" align="center"
   width="<%=Constants.TABLEWIDTH800%>" >
<tr>
<td class="genericerror" align="left"  style="color:#FF0000; text-transform:uppercase"><%= warningMsg %></td>
</tr>
</table>

<% }} /* end of loop and if null check on warnings */ %>

<%--------------------------Cart Is Empty View----------------------------------%>
<table width="<%=Constants.TABLEWIDTH800%>"  cellpadding=0 cellspacing=0>
<logic:equal name="CHECKOUT_FORM" property="itemsSize" value="0">
<!-- No items in the shopping cart -->
<% if(request.getAttribute("org.apache.struts.action.ERROR")==null) { %>
<tr class="orderInfoHeader">
  <td class="text" align="center">
    <br><b><app:storeMessage key="shop.checkout.text.cartIsEmpty"/></b><br>&nbsp;
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
<table width="<%=Constants.TABLEWIDTH800%>"  cellpadding=0 cellspacing=0  border="0">
<logic:greaterThan name="CHECKOUT_FORM" property="itemsSize" value="0">
  <tr> <td align="left">
 <%-- <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ADD_RE_SALE_ITEMS%>">--%>
   <%String hrefStr = "../store/shoppingcart.do"; %>
   <b><app:storeMessage key="shop.checkout.text.checkYourOrder"/>
    <a class="hereLink" href="<%=hrefStr%>" >'<app:storeMessage key="shop.checkout.text.hereLink"/>'</a></b>
 <%--     </app:authorizedForFunction> --%>

   </td>  </tr>
   <tr>  <td class="tableoutline"><img src="images/spacer.gif" height="1"></td>  </tr>
</logic:greaterThan>
</table>
<br>
<%--------------------------input for inventory shopping---------------------------------->
<%if (!isHideForNewXpdex) { %>

  <table  width="<%=Constants.TABLEWIDTH800%>"  cellpadding=0 cellspacing=0>

  <logic:greaterThan name="CHECKOUT_FORM" property="itemsSize" value="0">
    <html:hidden name="CHECKOUT_FORM" property="siteId" value="<%=\"\"+theForm.getSite().getBusEntity().getBusEntityId()%>" />
      <html:hidden name="CHECKOUT_FORM" property="userId" value="<%=\"\"+appUser.getUser().getUserId()%>" />
        <html:hidden name="CHECKOUT_FORM" property="cartAmtString" value="<%=theForm.getCartAmtString()%>" />
        <%

        if ( ShopTool.isInventoryShoppingOn(request) == true ) {

          showPlaceOrderFields = true;
          if ( appUser.canMakePurchases() ) {
            showOrderSelected = true;
          }
          if (ShopTool.isModernInventoryShopping(request)) {
            showPlaceOrderButton = true;
            showPlaceOrderFields = true;
            if(showOrderSelected) showOrderSelected = false;
          } else {
            showPlaceOrderButton = false;
          }
        }

        OrderData prevOrder = shoppingCart.getPrevOrderData();
        if ( prevOrder != null ) { %>
        <tr><td rowspan=4><app:storeMessage key="shop.checkout.text.thisOrderWasCreatedFrom"/> </td></tr>
        <tr><td><app:storeMessage key="shop.checkout.text.orderNumber"/></td>
          <td> <%= prevOrder.getOrderNum() %></td>
   </tr>
   <tr><td><app:storeMessage key="shop.checkout.text.orderedBy"/></td>
     <td> <%= prevOrder.getUserFirstName() %> <%= prevOrder.getUserLastName() %> ( <%= prevOrder.getAddBy() %> )</td>
  </tr>
  <tr><td><app:storeMessage key="shop.checkout.text.originalOrderDate"/></td>
    <td> <%= prevOrder.getOriginalOrderDate() %></td>
  </tr>
  <% } %>
  </table>
<%--------------------------nonSPLItemsWarning---------------------------------->
  <% if (account.isShowSPL()) {
    boolean isNonSPLExists = false;
    java.util.List items = ShopTool.getCurrentShoppingCart(request).getItems();
    if (items != null) {
      for (int i=0; i<items.size(); i++) {
        ShoppingCartItemData item = (ShoppingCartItemData)items.get(i);
        ItemMappingData catDistMapping = item.getProduct().getCatalogDistrMapping();
        if (catDistMapping==null ||
        !com.cleanwise.service.api.util.Utility.isTrue(catDistMapping.getStandardProductList(),false)) {
          isNonSPLExists = true;
          break;
        }
      }
    }
    if (isNonSPLExists) {
  %>
  <table  width="<%=Constants.TABLEWIDTH800%>" cellpadding=0 cellspacing=0>
    <tr><td align="center" style="padding-top: 2em; padding-bottom: 2em;"><b><app:storeMessage key="shop.checkout.text.nonSPLItemsWarning"/></b></td></tr>
  </table>
  <%}} %>
  <%--------------------------??????---------------------------------->
  <%
  String showDistInventory = ShopTool.getShowDistInventoryCode(request);
  if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory) ||
  RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
    int noInventory = 0;
    java.util.List items = ShopTool.getCurrentShoppingCart(request).getItems();
    if (items != null) {
      for (int i=0; i<items.size(); i++) {
        ShoppingCartItemData item = (ShoppingCartItemData)items.get(i);
        int invQty = item.getDistInventoryQty();
        if(invQty==0) {
          noInventory = 2;
          break;
        }
        if(invQty<0) noInventory = 1;
      }
    }
    if (noInventory>0) {
      %>

      <table  width="<%=Constants.TABLEWIDTH800%>" cellpadding=0 cellspacing=0>
        <tr><td align="center" style="padding-top: 2em; padding-bottom: 2em;">
          <% if(noInventory==1) { %>
          <b><font color='red'><app:storeMessage key="shop.checkout.text.noInvInfoWarning"/></font></b>
          <% } else { %>
          <b><font color='red'><app:storeMessage key="shop.checkout.text.noInventoryWarning"/></font></b>
          <% } %>
        </td></tr>
      </table>
      <%}} %>
 <% } %>
<%-------------------------------------------------------------%>

<table  width="<%=Constants.TABLEWIDTH800%>"  cellpadding=0 cellspacing=0>
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
          <% if ( !ShopTool.isAnOCISession(request.getSession()) ) { %>
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
                <td>&nbsp;  <bean:write name="CHECKOUT_FORM" property="site.siteAddress.address2"/>
                </td>
             </tr>
             <tr><td align="right"> <app:storeMessage key="shop.checkout.text.address3"/></td>
               <td>&nbsp;  <bean:write name="CHECKOUT_FORM" property="site.siteAddress.address3"/>
               </td>
             </tr>

             <tr><td align="right"><app:storeMessage key="shop.checkout.text.city:"/></td>
               <td>&nbsp; <bean:write name="CHECKOUT_FORM"
               property="site.siteAddress.city"/>
               </td>
            </tr>
            <% if (appUser.getUserStore().isStateProvinceRequired()) {%>
            <tr><td align="right"> <app:storeMessage key="shop.checkout.text.state:"/></td>
              <td>&nbsp; <bean:write name="CHECKOUT_FORM"
              property="site.siteAddress.stateProvinceCd"/>
              </td>
            </tr>
           <% } %>
           <tr><td align="right"> <app:storeMessage key="shop.checkout.text.zip:"/></td>
             <td>&nbsp; <bean:write name="CHECKOUT_FORM"
             property="site.siteAddress.postalCode"/>
             </td>
           </tr>
           <tr><td align="right"> <app:storeMessage key="shop.checkout.text.country:"/></td>
             <td>&nbsp; <bean:write name="CHECKOUT_FORM"
             property="site.siteAddress.countryCd"/>
             </td>
           </tr>
          </table>
        <% } %>
     </td></tr>
     </table><!--Table shipto end -->
  </td>
<%------------end---Shipping Information------------------%>
</tr></table>
<%--------------------------Shopping Items----------------------------------%>
<table  width="<%=Constants.TABLEWIDTH800%>"  cellpadding=0 cellspacing=0>
<tr>
<td>

  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shoppingItems.jsp")%>'>
        <jsp:param name="allowEdits" value="false"/>
        <jsp:param name="showSelectCol" value="true"/>
        <jsp:param name="view" value="checkout"/>
        <jsp:param name="orderBy" value="<%=theForm.getSortBy()%>"/>
        <jsp:param name="formId" value="CHECKOUT_FORM"/>
        <jsp:param name="allowPoByVender" value='<%=String.valueOf(allowPoByVender)%>'/>
  </jsp:include>

</td>
</tr>
<tr>  <td class="tableoutline"><img src="images/spacer.gif" height="1"></td>  </tr>

</table>
<%-------------------summary/-Billing Info/Shoppin Info-----------------%>
<table width="<%=Constants.TABLEWIDTH800%>"   cellpadding=0 cellspacing=0  border="0">

<tr valign=top>
  <%--
     <td align="center">

        <%/* begin of the place order fields. */ %>
        <%if(appUser.getUserStore().getStoreId() == 173243){f_showPO=true;}%>
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
        <jsp:param name="allowOrderConsolidationFl" value='<%=String.valueOf(allowOrderConsolidationFl)%>'/>
        <jsp:param name="formId" value="CHECKOUT_FORM"/>
        <jsp:param name="allowPoByVender" value='<%=String.valueOf(allowPoByVender)%>'/>
        </jsp:include>
        <% } /* End of the place order fields. */ %>
      </td>  --%>
     <td valign=top width="22%">
          <table border="0" cellspacing="2" cellpadding="0" align="left">
          <tr>
            <%if (allowAccountComments && appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_CUSTOMER_ORDER_NOTES)){ %>
            <td class="text">
     <%---------internal Comments-----------%>
             <b><app:storeMessage key="shop.checkout.text.internalComment" /></b><br>
              <html:textarea name="CHECKOUT_FORM" property="customerComment" rows="3" cols="20" onkeydown="limitText(this.form.customerComment,2000);"/>
            </td>
          <%}else{%>
            <td >&nbsp;</td>
          <%}%>
          </tr>
         </table>
     </td>
     <%-------Shipping Comments && PO #-------%>

     <td valign=top width="22%">
         <table border="0" cellspacing="2" cellpadding="0" align="center">
           <%-------Shipping Comments -------%>
           <tr>
            <%if (allowShippingComments && appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_SHIPPING_ORDER_COMMENTS)){%>
             <td class="text" valign="top" align="left"  >
               <b><app:storeMessage key="shop.checkout.text.shippingComments"/></b><br>
               <%--  <html:text name="CHECKOUT_FORM" property="comments" size="40" maxlength="254" value="<%=newcomments%>" /><br>--%>
               <html:textarea name="CHECKOUT_FORM" property="comments" rows="3" cols="20" onkeydown="limitText(this.form.comments,254);" /><br>

                   <app:storeMessage  key="shopportal.message.help.shipping.comments"/>
             </td>
             <%}else{%>
             <td  >&nbsp;</td>
             <%}%>
                            <%--is authorized for ADD_CUSTOMER_ORDER_NOTES check--%>
           </tr>
           <%-------PO #-------%>
           <tr>
               <td align="right"  nowrap="nowrap" class="text"  valign="top">
                 <%if(!allowPoByVender && (allowPoEntry && f_showPO)){%>
                     <br>
                     <b><app:storeMessage key="shop.checkout.text.poNum"/>  </b>
                     <html:text name="CHECKOUT_FORM" property="poNumber" size="20" maxlength="22" value="<%=newpoval%>" />
                     <%if(appUser.getPoNumRequired()){%><font color="red">*</font><%}%>
                  <%}else if(usingBlanketPo){%>
                       <b><app:storeMessage key="shop.checkout.text.poNumber"/>  </b>
                       <bean:write name="CHECKOUT_FORM" property="site.blanketPoNum.poNumber"/>
                       <html:hidden name="CHECKOUT_FORM" property="poNumber" value="<%=newpoval%>" />
                  <%}else{%>
                       <html:hidden name="CHECKOUT_FORM" property="poNumber" value="<%=newpoval%>" />
                  <%}%>
               </td>
           </tr>
         </table>
     </td>
     
     <%-------Alternate ShipTo Address #-------%>
     <td valign=top width="22%">
        <table border="0" cellspacing="0" cellpadding="0" align="left">
        <%
        if (theForm.getAllowAlternateShipTo()) {
        %>
        <tr>
            <td colspan="2" align="center">
                <b><app:storeMessage key="shop.checkout.header.alternateShippingAddress"/></b>
            </td>
        </tr>

        <tr>
        <td>
                <% if ( !ShopTool.isAnOCISession(request.getSession())) { %>
                        <table>
                            <tr>
                                <td align="right">
                                    <app:storeMessage key="shop.checkout.text.attn"/>
                                </td>
                                <td>
                                    <html:text name="CHECKOUT_FORM" property="alternateAddress1" size="20" maxlength="73"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <app:storeMessage key="shop.checkout.text.phone:"/>
                                </td>
                                <td>
                                    <html:text name="CHECKOUT_FORM" property="alternatePhoneNum" size="20" maxlength="20"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <app:storeMessage key="shop.checkout.text.address1"/>
                                </td>
                                <td>
                                    <html:text name="CHECKOUT_FORM" property="alternateAddress2" size="20" maxlength="79"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <app:storeMessage key="shop.checkout.text.address2"/>
                                </td>
                                <td>
                                    <html:text name="CHECKOUT_FORM" property="alternateAddress3" size="20" maxlength="79"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <app:storeMessage key="shop.checkout.text.city:"/>
                                </td>
                                <td>
                                    <html:text name="CHECKOUT_FORM" property="alternateCity" size="20" maxlength="39"/>
                                </td>
                            </tr>
                            <% if (appUser.getUserStore().isStateProvinceRequired()) {%>
                            <tr>
                                <td align="right">
                                    <app:storeMessage key="shop.checkout.text.state:"/>
                                </td>
                                <td>
                                    <html:text name="CHECKOUT_FORM" property="alternateStateProvince" size="20" maxlength="29"/>
                                </td>
                            </tr>
                            <% } %>
                            <tr>
                                <td align="right">
                                    <app:storeMessage key="shop.checkout.text.zip:"/>
                                </td>
                                <td>
                                    <html:text name="CHECKOUT_FORM" property="alternatePostalCode" size="20" maxlength="14"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <app:storeMessage key="shop.checkout.text.country:"/>
                                </td>
                                <td>
                                    <html:text name="CHECKOUT_FORM" property="alternateCountry" size="20" maxlength="29"/>
                                </td>
                            </tr>
                        </table>
                <% } %>
            </td>
            </tr>
        <%} else {%>
            <tr>
                <td colspan="2">
                    &nbsp;
                </td>
            </tr>
        <%}%>
        </table>
    </td>

     <td valign=top width="34%">
 <%------------------ Table Summary beg --------------------------------%>
          <table border="0" class="checkoutInformation" align="right">
    <%------------------Summary :: SubTotal------------------%>
            <%BigDecimal cartAmt = (theForm.getCartAmt(request)); %>
            <tr>
              <td  colspan="2"  align="right" class="smalltext"><b><app:storeMessage key="shop.checkout.text.sTotal"/></b></td>
              <!-- cartAmt -->

              <td align="right" class="checkoutInformation"><%=ClwI18nUtil.getPriceShopping(request,cartAmt,"&nbsp;")%></td>
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
                  <td align="right" class="checkoutInformation"><%=ClwI18nUtil.getPriceShopping(request,handlingAmt,"&nbsp;")%></td>

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
    <bean:define id="freightImplied" name="scdistD" property="distFreightImplied" type="java.util.List"/>

    <tr>

      <td  nowrap="nowrap"><b><app:storeMessage key="shop.checkout.text.EstimatedFreight"/>:&nbsp;</b></td>
      <td  align="right" class="checkoutInformation">
        <html:select name="CHECKOUT_FORM" property="distFreightVendor" onchange="setAndSubmit('CHECKOUT_FORM','command','recalc');">
          <html:options collection="freightOptions" property="freightTableCriteriaId" labelProperty="shortDesc"  />
        </html:select>
      </td>
      <td align="right" class="checkoutInformation">
        <bean:define id="selectedFrMsg" name="scdistD" property="distSelectableFreightMsg" type="String"/>
        <bean:define id="selectedFrAmt" name="scdistD" property="distSelectableFreightCost" />
    <%		if (null != selectedFrMsg && selectedFrMsg.trim().length() > 0) {  %>
        <bean:write name="scdistD" property="distSelectableFreightMsg" />
    <%		} else {  %>
        <%=ClwI18nUtil.getPriceShopping(request,selectedFrAmt,"&nbsp;")%>
    <%		}  %>
      </td>
    </tr>

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

    <% 	} else {  %>
      <html:hidden name="CHECKOUT_FORM" property="distFreightVendor" value=""/>
    <%	}  %>
   <%--	}  --%>
   </logic:iterate>

    <%------------------Summary :: Freight------------------ %>
    <bean:define id="freightAmt" name="CHECKOUT_FORM" property="freightAmt"/>
    <% if (appUser.isaCustServiceRep()) { %>
          <tr><td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.freight"/></b></td>
           <td align="right"> <html:text name="CHECKOUT_FORM" property="freightAmtString" size="8"/></td>
           <td> &nbsp;&nbsp;</td>
         </tr>
    <% } else { %>
         <html:hidden name="CHECKOUT_FORM" property="freightAmtString" value="<%=theForm.getFreightAmtString()%>" />
         <logic:greaterThan name="CHECKOUT_FORM" property="freightAmt" value="0">
           <tr>
             <!-- freightAmt -->
             <td align="right"><%=ClwI18nUtil.getPriceShopping(request,freightAmt,"&nbsp;")%><bean:write name="CHECKOUT_FORM" property="estimateFreightString" /></td>
             <td> &nbsp;&nbsp;</td>
           </tr>
         </logic:greaterThan>
    <% } %>
    --%>
      <%--
          <%if (!isHideForNewXpdex) { %>
          <!-- Rush order charge (begin) -->
          <% if(Utility.isSet(ShopTool.getRushOrderCharge(request))) {
          String rushOrderChargeS = theForm.getRushChargeAmtString();
          if(rushOrderChargeS==null) rushOrderChargeS = "";
          try {
            rushOrderCharge = new BigDecimal(rushOrderChargeS);
          } catch (Exception exc) {}
          %>
          <% if (appUser.isaCustServiceRep()) { %>
          <tr><td class="smalltext">  <b><app:storeMessage key="shop.checkout.text.rushOrderCharge"/></b></td>
            <td align="right"> <html:text name="CHECKOUT_FORM" property="rushChargeAmtString" size="8"/></td>
            <td> &nbsp;&nbsp;</td>
          </tr>
         <% } else { %>
         <html:hidden name="CHECKOUT_FORM" property="rushChargeAmtString" value="<%=rushOrderChargeS%>" />
           <% if(rushOrderCharge.doubleValue()>0.01) { %>
           <tr><td class="smalltext" >  <b><app:storeMessage key="shop.checkout.text.rushOrderCharge"/></b> </td>
             <td align="right"><%=ClwI18nUtil.getPriceShopping(request,rushOrderCharge,"&nbsp;")%></td>
             <td> &nbsp;&nbsp;</td>
          </tr>
          <% } %>
          <% } %>
          <% } %>
          <% } %>
          <%--Rush order charge (end) --%>

              <% if (!isHideForNewXpdex) { %>
              <bean:define id="miscAmt"  name="CHECKOUT_FORM" property="miscAmt"/>
              <logic:greaterThan name="CHECKOUT_FORM" property="miscAmt" value="0">
                <tr><td  colspan="2"  align="right" class="smalltext"> <b><app:storeMessage key="shop.checkout.text.miscCharges"/></b></td>
                  <!-- miscAmt -->
                  <td align="right" class="checkoutInformation"><%=ClwI18nUtil.getPriceShopping(request,miscAmt,"&nbsp;")%></td>

                </tr>
              </logic:greaterThan>
              <%} %>
              <%------------------Summary ::  Tax------------------%>
              <% if ( null != theForm.getSalesTax()) { %>
              <tr valign=top><td  colspan="2"  align="right" class="smalltext" > <b><app:storeMessage key="shop.checkout.text.estimatedTax"/></b></td>
                <td align="right" class="checkoutInformation">
                  <!-- theForm.getSalesTax() -->
                  <%=ClwI18nUtil.getPriceShopping(request,theForm.getSalesTax(),"&nbsp;")%>
                </td>
              </tr>
              <% } %>
              <%------------------Summary ::  Grand Total------------------%>
              <tr><td  colspan="2"  align="right" class="smalltext"> <b><app:storeMessage key="shop.checkout.text.grandTotal"/></b></td>
                <%	BigDecimal lTotal = theForm.getCartAmt(request);
                //lTotal = lTotal.add(theForm.getFreightAmt());
				lTotal = lTotal.add(theForm.getDistFreightAmt());
                lTotal = lTotal.add(handlingAmt);
//                lTotal = lTotal.add(rushOrderCharge);
                lTotal = lTotal.add(theForm.getSalesTax());
				lTotal = lTotal.add(theForm.getServiceFeeAmt());
			%>
                <td align="right" class="checkoutInformation">
                  <!-- lTotal -->
                  <%=ClwI18nUtil.getPriceShopping(request,lTotal,"&nbsp;")%><bean:write name="CHECKOUT_FORM" property="estimateFreightString" />
                </td>

               </tr>

          </table><!--Table order summary end -->
       </td>
<%-------------end-----Summary ------------------%>
<%--    place of shipping --%>
</tr>
<%-- Credit Cart section --%>
<% if (isShowCreditCardSection) { %>
<style>
    #ccInfo { display:<%=(isCCPmt?"block":"none")%> }
</style>
<script type="text/javascript">
function showCC(f) {
    if (f.reqPmtCheckBox.checked) {
        // show CC section
        f.reqPmt.value = "CC";
        document.all.ccInfo.style.display = "block";
    } else {
        // hide CC section
        f.reqPmt.value = "Other";
        document.all.ccInfo.style.display = "none";
    }
}
</script>

<tr >
<td>&nbsp;</td>
<td valign="top" colspan="2">
    <div id="ccInfo">
    <table width="100%">
    <tr>
        <td width=50% valign="top">
            <b><app:storeMessage key="shop.checkout.text.creditCardInfo"/></b><br>
            <table width="100%">
			<tr>
                    <td align="right" class="smalltext">
                    <app:storeMessage key="shop.checkout.text.cardName:"/>
					</td>
                    <td>&nbsp;</td>
                    <td>
					<html:text name="CHECKOUT_FORM" property="ccPersonName" size="25"/>
                    </td>
                <tr>
                    <td align="right" class="smalltext">
                    <app:storeMessage key="shop.checkout.text.cardType:"/>
                  </td>
                    <td>&nbsp;</td>
                    <td>
                    <html:select  name="CHECKOUT_FORM" property="ccType">
                      <html:option value="<%=RefCodeNames.PAYMENT_TYPE_CD.VISA%>"><%=RefCodeNames.PAYMENT_TYPE_CD.VISA%></html:option>
                      <html:option value="<%=RefCodeNames.PAYMENT_TYPE_CD.MASTER_CARD%>"><%=RefCodeNames.PAYMENT_TYPE_CD.MASTER_CARD%></html:option>
                    </html:select>
                  </td>
                </tr>
                <tr>
                  <td align="right" class="smalltext">
                    <app:storeMessage key="shop.checkout.text.cardNumber:"/>
                  </td>
                    <td>&nbsp;</td>
                  <td>
                    <html:text name="CHECKOUT_FORM" property="ccNumber" size="16" maxlength="16"/>
                  </td>
                </tr>
                <tr>
                  <td align="right" class="smalltext"><app:storeMessage key="shop.checkout.text.expDate:"/></td>
                    <td>&nbsp;</td>
                  <td>
                      <html:select  name="CHECKOUT_FORM" property="ccExpMonth">
                        <html:option value="<%=\"\"+GregorianCalendar.JANUARY%>"><app:storeMessage key="global.text.month.jan"/></html:option>
                        <html:option value="<%=\"\"+GregorianCalendar.FEBRUARY%>"><app:storeMessage key="global.text.month.feb"/></html:option>
                        <html:option value="<%=\"\"+GregorianCalendar.MARCH%>"><app:storeMessage key="global.text.month.mar"/></html:option>
                        <html:option value="<%=\"\"+GregorianCalendar.APRIL%>"><app:storeMessage key="global.text.month.apr"/></html:option>
                        <html:option value="<%=\"\"+GregorianCalendar.MAY%>"><app:storeMessage key="global.text.month.may"/></html:option>
                        <html:option value="<%=\"\"+GregorianCalendar.JUNE%>"><app:storeMessage key="global.text.month.jun"/></html:option>
                        <html:option value="<%=\"\"+GregorianCalendar.JULY%>"><app:storeMessage key="global.text.month.jul"/></html:option>
                        <html:option value="<%=\"\"+GregorianCalendar.AUGUST%>"><app:storeMessage key="global.text.month.aug"/></html:option>
                        <html:option value="<%=\"\"+GregorianCalendar.SEPTEMBER%>"><app:storeMessage key="global.text.month.sep"/></html:option>
                        <html:option value="<%=\"\"+GregorianCalendar.OCTOBER%>"><app:storeMessage key="global.text.month.oct"/></html:option>
                        <html:option value="<%=\"\"+GregorianCalendar.NOVEMBER%>"><app:storeMessage key="global.text.month.nov"/></html:option>
                        <html:option value="<%=\"\"+GregorianCalendar.DECEMBER%>"><app:storeMessage key="global.text.month.dec"/></html:option>
                      </html:select>&nbsp;&nbsp;
                      <%
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTime(Constants.getCurrentDate());
                      %>
                      <html:select  name="CHECKOUT_FORM" property="ccExpYear">
                        <html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
                        <% cal.add(GregorianCalendar.YEAR,1);%>
                        <html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
                        <% cal.add(GregorianCalendar.YEAR,1);%>
                        <html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
                        <% cal.add(GregorianCalendar.YEAR,1);%>
                        <html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
                        <% cal.add(GregorianCalendar.YEAR,1);%>
                        <html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
                        <% cal.add(GregorianCalendar.YEAR,1);%>
                        <html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
                        <% cal.add(GregorianCalendar.YEAR,1);%>
                        <html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
                        <% cal.add(GregorianCalendar.YEAR,1);%>
                        <html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
                        <% cal.add(GregorianCalendar.YEAR,1);%>
                        <html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
                      </html:select>
                  </td>
              </tr>
            </table>
        </td>
            <td valign="top">
                <b><app:storeMessage key="shop.checkout.text.creditCardBillingAddress"/></b><br>
                <table width="100%">
                    <tr>
                      <td align="right" class="smalltext"><app:storeMessage key="shop.checkout.text.address1:"/></td>
                      <td>
                        <html:text name="CHECKOUT_FORM" property="ccStreet1" size="25"/>
                      </td>
                    </tr>
                    <tr>
                      <td align="right" class="smalltext"><app:storeMessage key="shop.checkout.text.address2:"/></td>
                      <td>
                        <html:text name="CHECKOUT_FORM" property="ccStreet2" size="25"/>
                      </td>
                    </tr>
                    <tr>
                      <td align="right" class="smalltext"><app:storeMessage key="shop.checkout.text.city:"/></td>
                      <td>
                        <html:text name="CHECKOUT_FORM" property="ccCity" size="25"/>
                      </td>
                    </tr>
                    <tr>
                    <% if (appUser.getUserStore().isStateProvinceRequired()) { %>
                      <td align="right" class="smalltext"><app:storeMessage key="shop.checkout.text.state:"/></td>
                      <td><html:text name="CHECKOUT_FORM" property="ccState" size="25" maxlength="2"/></td>
                    </tr>
                    <%}%>
                    <tr>
                      <td align="right" class="smalltext"><app:storeMessage key="shop.checkout.text.zip:"/></td>
                      <td><html:text name="CHECKOUT_FORM" property="ccZipCode" size="25"/></td>
                    </tr>
                </table>
            </td>
          </tr>
        </table>
    </div>
</td>
</tr>
<%}%>
<%-- end of Credit Cart section --%>
<tr>
   <td  colspan="4" align="right">
    <table  border="0" cellspacing="0" cellpadding="0">
         <%-- 1 Buttons section--%>
         <tr>
           <% if (isShowCreditCardSection) { %>
           <td align="right">
               <app:storeMessage key="shop.checkout.text.payWithCreditCard?"/>&nbsp;&nbsp;
               <input type="checkbox" name="reqPmtCheckBox" onClick="showCC(this.form);" <%=isCCPmt?"checked":""%>/>
               <html:hidden property="reqPmt"/>
           </td>
           <td width="20">&nbsp;</td>
           <%}%>
           <td align="right">
            <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_saveOrderGuideButton.jsp")%>'>
               <jsp:param name="isDeleteVisible" value="<%=Boolean.toString(false)%>"/>
               <jsp:param name="isUpdateVisible" value="<%=Boolean.toString(false)%>"/>
               <jsp:param name="isCheckoutVisible" value="<%=Boolean.toString(false)%>"/>
               <jsp:param name="isSubmitVisible" value="<%=Boolean.toString(true)%>"/>
               <jsp:param name="id" value=""/>
            </jsp:include>
           </td>
         </tr>
     </table>
 </td>
</tr>


<tr>
  <%-- Button: Update Cart  --%>
  <%-- %> <a href="#" class="linkButton" onclick="setAndSubmit('CHECKOUT_FORM','command','updateCart');"--%>
    <td colspan="3" align="right">
     </td>
</tr>

</table>


<%--</logic:greaterThan>   -- not balanced! NG

</td></tr>
</table>--%>

<% if(consolidatedOrderFl) { %>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"assocOrder.jsp")%>'/>
<% } %>

<%--<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>--%>

  <html:hidden property="action" value="submit"/>
  <html:hidden property="command" value="placeOrder"/>

</html:form>
<%-- } --%>


<!-- CRC INFO -->
<%
  if(userType.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
     userType.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
     userType.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)){
%>

<table align="center" border="0" cellspacing="0"
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




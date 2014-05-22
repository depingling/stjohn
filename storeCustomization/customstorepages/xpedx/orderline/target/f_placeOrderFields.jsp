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

<bean:define id="theForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="clwWorkflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>

<%
 CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
 boolean isStoreAdmin = appUser.isaStoreAdmin();
 String formId = request.getParameter("formId");
 ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
 OrderData prevOrder = shoppingCart.getPrevOrderData();
 AccountData account = appUser.getUserAccount();
 String accountName = account.getBusEntity().getShortDesc();
 String userType = appUser.getUser().getUserTypeCd();
 boolean showPlaceOrderFields = ("true".equals(request.getParameter("showPlaceOrderFields")))?true:false;
 boolean showPlaceOrderButton = ("true".equals(request.getParameter("showPlaceOrderButton")))?true:false;
 boolean f_showCC = ("true".equals(request.getParameter("f_showCC")))?true:false;
 boolean f_showOther = ("true".equals(request.getParameter("f_showOther")))?true:false;
 boolean allowShippingCommentsEntry = ("true".equals(request.getParameter("allowShippingCommentsEntry")))?true:false;
 boolean allowAccountComments = ("true".equals(request.getParameter("allowAccountComments")))?true:false;
 boolean allowOrderConsolidationFl = ("true".equals(request.getParameter("allowOrderConsolidationFl")))?true:false;
 boolean isOrderService=("true".equals(request.getParameter("orderServiceFl")))?true:false;
%>
<table class="tbstd" width="<%=Constants.TABLEWIDTH%>" cellpadding=0 cellspacing=5>
<%if(isOrderService) {%>
<% if (showPlaceOrderFields && showPlaceOrderButton){%>
          <tr>
      <td colspan="6">
      <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.PLACE_ORDER_REQUEST_SHIP_DATE%>">
        <b><app:storeMessage key="shop.checkout.text.requestedDelivery"/></b>
        (<%=ClwI18nUtil.getUIDateFormat(request)%>)<br>
        <html:text name="CHECKOUT_FORM" property="requestedShipDate" size="10" maxlength="10" />
        <br>
      </app:authorizedForFunction>

    </td>
  </tr>    <% } %>
<%} else {%>
<tr>
    <td colspan="3">
      <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_budgetInfoInc.jsp")%>'>
        <jsp:param name="jspFormName" value="CHECKOUT_FORM" />
        <jsp:param name="formId" value="<%=formId%>"/>
      </jsp:include>
    </td>
  </tr>

  <tr>
    <td style="padding: 2em;" align="left" colspan="2">
      <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_review_items_note.jsp")%>'/>
    </td>
    <td>
      <% if (showPlaceOrderFields && showPlaceOrderButton){%>
      <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.PLACE_ORDER_REQUEST_SHIP_DATE%>">
        <b><app:storeMessage key="shop.checkout.text.requestedDelivery"/></b>
        (<%=ClwI18nUtil.getUIDateFormat(request)%>)<br>
        <html:text name="CHECKOUT_FORM" property="requestedShipDate" size="10" maxlength="10" />
        <br>
      </app:authorizedForFunction>
      <% } %>
    </td>
  </tr>
<%}%>
  <%

    /* -- -------   ----   PO and comments block -- */

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

  %>

  <%
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


    if ( appUser.isaCustServiceRep() || isStoreAdmin ) {

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
  <logic:present name="<%=Constants.SHOPPING_CART%>" property="customerComments">
    <bean:size id="custCommentCount" name="<%=Constants.SHOPPING_CART%>" property="customerComments"/>
    <logic:greaterThan name="custCommentCount" value="0">
      <tr colspan="3">
        <td><b><%=accountName%>
          <app:storeMessage key="shop.checkout.text.comments"/></b>
        </td>
      </tr>
      <logic:iterate id="custComment" name="<%=Constants.SHOPPING_CART%>"
        property="customerComments" type="com.cleanwise.service.api.value.ShoppingInfoData">
        <tr>

          <td class="text" width="150"
            style="vertical-align:top; padding-left:20; white-space: nowrap;">

            <bean:define id="commentDate" name="custComment" property="addDate"
            type="java.util.Date" />
            <b>
              <!-- i18n:formatDate value="%=commentDate%"  pattern="yyyy-M-d k:mm"  locale="%=Locale.US%"/! -->
              <%=ClwI18nUtil.formatDate(request,commentDate,DateFormat.FULL)%>
              <bean:write name="custComment" property="addBy"/>
            </b>
          </td>
          <td style="text-align:left;">
            <app:writeHTML name="custComment" property="value"/>
          </td>
        </tr>
      </logic:iterate>

    </logic:greaterThan>
  </logic:present>

  <tr>
    <td width="66%" valign="top" colspan="2">
      <% if (showPlaceOrderFields && showPlaceOrderButton){%>
      <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.PLACE_ORDER_PROCESS_ORDER_ON%>">
        <b><app:storeMessage key="shop.checkout.text.processOrderOn"/></b>
        (<%=ClwI18nUtil.getUIDateFormat(request)%>)<br>
        <html:text name="CHECKOUT_FORM" property="processOrderDate" size="15"/>
        <br>
      </app:authorizedForFunction>
      <% if (allowOrderConsolidationFl) { %><br><% } %>
      <% } %>
      <% if (allowOrderConsolidationFl) { %>
      <html:checkbox name="CHECKOUT_FORM" property="pendingConsolidation"/>
      <b><app:storeMessage key="shop.checkout.text.holdOrderForConsolidation"/></b>
      <% } %>
      &nbsp;
    </td>
    <td width="33%"><!-- crc area -->
      <table cellpadding="0" cellspacing="0">
        <%
          if ( appUser.isaCustServiceRep() || isStoreAdmin ) {
        %>
        <%-- Order Contact fields ------------------------ --%>

        <tr><td class="text"><b><app:storeMessage key="shop.checkout.text.contactName"/></b></td>
        <td> <html:text name="CHECKOUT_FORM" property="orderContactName" size="30"
          value="<%=prevOrderContactName%>" />
          <font color="red">*</font>
        </td></tr>

        <tr><td class="text"><b> <app:storeMessage key="shop.checkout.text.contactPhoneNum"/></b></td>
        <td> <html:text name="CHECKOUT_FORM"
          property="orderContactPhoneNum" size="20"
          value="<%=prevOrderContactPhoneNum%>" />
          <font color="red">*</font>
        </td></tr>

        <tr><td class="text"><b> <app:storeMessage key="shop.checkout.text.contactEmail"/></b></td>
        <td> <html:text name="CHECKOUT_FORM"
          property="orderContactEmail" size="20"
          value="<%=prevOrderContactEmail%>" />
        </td></tr>

        <tr>
          <td class="text"><b> <app:storeMessage key="shop.checkout.text.method"/></b></td>
          <td>
            <html:select name="CHECKOUT_FORM"  property="orderOriginationMethod">

              <% if (prevOrderMethod.length() > 0) { %>
              <html:option value="<%=prevOrderMethod%>"><%=prevOrderMethod%></html:option>
              <% } %>

              <% if (!prevOrderMethod.equals("Fax")) { %>
              <html:option value="Fax"><app:storeMessage key="shop.checkout.text.fax"/></html:option>
              <% } %>

              <% if (!prevOrderMethod.equals("Mail")) { %>
              <html:option value="Mail"><app:storeMessage key="shop.checkout.text.mail"/></html:option>
              <% } %>

              <% if (!prevOrderMethod.equals("Email")) { %>
              <html:option value="Email"><app:storeMessage key="shop.checkout.text.email"/></html:option>
              <% } %>

              <% if (!prevOrderMethod.equals("Telephone")) { %>
              <html:option value="Telephone"><app:storeMessage key="shop.checkout.text.telephone"/></html:option>
              <% } %>

              <% if (!prevOrderMethod.equals("Other")) { %>
              <html:option value="Other"><app:storeMessage key="shop.checkout.text.other"/></html:option>
              <% } %>

            </html:select>
            <font color="red">*</font>
          </td>
        </tr>

        <%
          if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals(clwWorkflowRole)||
                        userType.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)){
        %>
        <tr>
          <td class="text" colspan=2>
            <html:checkbox name="CHECKOUT_FORM" property="bypassOrderRouting"/>
            <b><app:storeMessage key="shop.checkout.text.bypasSmallOrderRouting"/></b>
            <br>
            <html:checkbox name="CHECKOUT_FORM" property="bypassCustomerWorkflow"/>
            <b><app:storeMessage key="shop.checkout.text.bypasCustomerWorkflow"/></b>

          </td>
        </tr>
        <% } %>

        <tr>
          <td class="text" colspan=2>
            <b><app:storeMessage key="shop.checkout.text.customerRequestedReshipmentOrderNumber"/></b><br>
            <html:text name="CHECKOUT_FORM" property="customerRequestedReshipOrderNum"/>
          </td>
        </tr>
        <% } %><%-- End isACustomerServiceRep ------------------------ --%>

        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.PLACE_CONFIRMATION_ONLY_ORDER%>">
          <tr>
            <td class="text" colspan=2>
              <html:checkbox name="CHECKOUT_FORM" property="billingOrder"/>
              <b><app:storeMessage key="shop.checkout.text.confirmationOnlyOrder"/></b>
              <br>
              <b><app:storeMessage key="shop.checkout.text.originalPurchaseOrderNumber"/></b><br>
              <html:text name="CHECKOUT_FORM" property="billingOriginalPurchaseOrder"/>
              <br>
              <b><app:storeMessage key="shop.checkout.text.distributorInvoiceNumber"/></b><br>
              <html:text name="CHECKOUT_FORM" property="billingDistributorInvoice"/>
            </td>
          </tr>
        </app:authorizedForFunction>

         <% if (appUser.getUserAccount().isShowReBillOrder()) { %>
        <tr>
            <td class="text" colspan=2>
                <html:checkbox name="CHECKOUT_FORM" property="rebillOrder"/>
                <b><app:storeMessage key="shop.checkout.text.rebillOrder"/></b>
            </td>
        </tr>
        <% } %>

    </table>
    </td><!-- crc area -->
  </tr>



  <%if ( appUser.isaCustServiceRep() || isStoreAdmin ) {    %>
  <tr>
    <td colspan="2" class="text"><b> <app:storeMessage key="shop.checkout.text.oderNoteInternal"/> </b><br>
    <html:textarea name="CHECKOUT_FORM" property="orderNote" rows="5"
    cols="45"/></td>
  </tr>
  <%}%> <%--appUser.isaCustServiceRep--%>




  <%-- ------  --------- PO and comments block --%>


  <% /* Start other payment section. */
    if(appUser.getOtherPaymentFlag() && f_showOther) { %>
  <tr>
    <td class="text" width="25%">
      <b><app:storeMessage key="shop.checkout.text.recordOfCall"/></b><br>
      <html:text name="CHECKOUT_FORM" property="otherPaymentInfo" size="16"/>
      <font color="red">*</font>
    </td>
  </tr>
  <% }/* End other payment section. */%>

  <% if(appUser.getCreditCardFlag() &&    f_showCC ) {  %>
  <tr>
    <td class="text" colspan="3"><b><app:storeMessage key="shop.checkout.text.purchasingWithCC"/></b>
    <app:storeMessage key="shop.checkout.text.fillAppropriateInformation"/></td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td class="text"><app:storeMessage key="shop.checkout.text.enterCreditCardInformationBelow"/></td>
  </tr>
  <tr>
    <td colspan="2">
      <!--Table level3 begin -->
      <table  align="center" border="0" cellpadding="0" cellspacing="0" >
        <tr>
          <td class="text" width="25%">
            <app:storeMessage key="shop.checkout.text.cardType"/>
          </td>
          <td width="75%">
            <html:select  name="CHECKOUT_FORM" property="ccType">
              <html:option value="<%=RefCodeNames.PAYMENT_TYPE_CD.VISA%>"><%=RefCodeNames.PAYMENT_TYPE_CD.VISA%></html:option>
              <html:option value="<%=RefCodeNames.PAYMENT_TYPE_CD.MASTER_CARD%>"><%=RefCodeNames.PAYMENT_TYPE_CD.MASTER_CARD%></html:option>
            </html:select>
          </td>
        </tr>
        <tr>
          <td class="text" width="25%">
            <app:storeMessage key="shop.checkout.text.cardNumberNoSpacesOrDashes"/>
          </td>
          <td width="75%">
            <html:text name="CHECKOUT_FORM" property="ccNumber" size="16" maxlength="16"/>
          </td>
        </tr>
        <tr>
          <td class="text" width="25%"><app:storeMessage key="shop.checkout.text.expDate"/></td>
          <td width="75%">

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
            </html:select>
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
      </table><!-- Table Level3 end -->
    </td>
    <td align="left">
      <!--Table level3 begin -->
      <table border="0">
        <tr>
          <td class="text" colspan="2"><app:storeMessage key="shop.checkout.text.nameOnCreditCard"/></td>
        </tr>
        <tr>
          <td class="text" colspan="2">
            <html:text name="CHECKOUT_FORM" property="ccPersonName" size="45"/>
          </td>
        </tr>
        <tr>      <td>&nbsp;</td>
        <td class="text"><app:storeMessage key="shop.checkout.text.creditCardBillingAddress"/></td></tr>
        <tr>
          <td class="text"><app:storeMessage key="shop.checkout.text.street1"/></td>
          <td>
            <html:text name="CHECKOUT_FORM" property="ccStreet1" size="30"/>
          </td>
        </tr>
        <tr>
          <td class="text"><app:storeMessage key="shop.checkout.text.street2"/></td>
          <td>
            <html:text name="CHECKOUT_FORM" property="ccStreet2" size="30"/>
          </td>
        </tr>
        <tr>
          <td class="text"><app:storeMessage key="shop.checkout.text.city"/></td>
          <td>
            <html:text name="CHECKOUT_FORM" property="ccCity" size="30"/>
          </td>
        </tr>
        <tr>
        <% if (appUser.getUserStore().isStateProvinceRequired()) { %>
          <td class="text"><app:storeMessage key="shop.checkout.text.state"/></td>
          <td>
            <html:text name="CHECKOUT_FORM" property="ccState" size="2" maxlength="2"/>
            <app:storeMessage key="shop.checkout.text.zipCode"/>
            <html:text name="CHECKOUT_FORM" property="ccZipCode" size="10"/>
          </td>
       <%} else { %>
          <td class="text"><app:storeMessage key="shop.checkout.text.zipCode"/></td>
          <td><html:text name="CHECKOUT_FORM" property="ccZipCode" size="10"/></td>
       <%}%>
        </tr>
      </table><!--Table level3 end -->
    </td>
  </tr>
  <tr>

    <td colspan="3" class="tableoutline">
      <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1">
    </td>

  </tr>


  <% } %><!-- end of Credit Card -->

  <tr>
    <%if(allowShippingCommentsEntry){%>
    <td class="text" valign="top" align="left" colspan=2>
      <b><app:storeMessage key="shop.checkout.text.shippingComments"/></b><br>
      <html:text name="CHECKOUT_FORM" property="comments" size="65" maxlength="65" value="<%=newcomments%>" /><br>
      <app:storeMessage  key="shopportal.message.help.shipping.comments"/>
    </td>
    <%}else{%>
    <td colspan=2>&nbsp;</td>
    <%}%>
    <%if (allowAccountComments && appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_CUSTOMER_ORDER_NOTES)){%>
    <td class="text"><b><app:storeMessage key="shop.checkout.text.customerComment" arg0="<%=accountName%>" /></b><br>
    <html:textarea name="CHECKOUT_FORM" property="customerComment" rows="5" cols="35"/></td>
    <%}%> <%--is authorized for ADD_CUSTOMER_ORDER_NOTES check--%>

  </tr>
  <%-- Site fields ---------------   --%>
  <logic:iterate id="siteField"
    name="CHECKOUT_FORM" property="site.dataFieldPropertiesRuntime"
    type="com.cleanwise.service.api.value.PropertyData">

    <tr>
      <td class="text"><b>
        <bean:write name="siteField" property="shortDesc"/>
      </b></td>
      <td><bean:write name="siteField" property="value" />
      </td>
    </tr>




    <%-- START- List options for this field if there are any. --%>

    <bean:define id="fname"  type="String" name="siteField" property="shortDesc"/>
    <bean:define id="fvalue" type="String" name="siteField" property="value" />

    <% String optFieldTag = "checkoutOption("+ fname + ")"; %>
    <html:hidden  name="CHECKOUT_FORM"   property="<%=optFieldTag%>"  value="<%=fvalue%>"/>

    <%
      if (  null != appUser.getUserAccount() &&
              null != appUser.getUserAccount().getCheckoutOptConfig() ) {

        ShoppingOptConfigData soConfigData =
                appUser.getUserAccount().getCheckoutOptConfig();

        String thisFormTag = "checkoutOption("+ soConfigData.getFieldName() + ")";

        ShoppingOptionsDataVector sodv =
                appUser.getUserAccount().getCheckoutOptions( fname, fvalue);
        if ( null != sodv && sodv.size() > 0 ) { %>

    <tr><td colspan=2 align="left">
      <%=soConfigData.getFieldPrompt()%>
    </td><tr>
    <tr><td colspan=4>
      <html:select name="CHECKOUT_FORM" property="<%=thisFormTag%>"
      style="font-family : monospace; font-size : 8pt"  >
      <html:option value="<%=Constants.CwUi.SELECT%>">
        <app:storeMessage key="shop.checkout.text.selectOption"/>
      </html:option>

      <% for ( int i3 = 0; i3 < sodv.size(); i3++ ) {
          ShoppingOptionsData soData = (ShoppingOptionsData)sodv.get(i3);
      %>
      <html:option value="<%=soData.getOptionValue()%>" />
      <% } %>
    </td><tr>

    </html:select>


    <% }
        } %>

    <%-- END- List options for this field if there are any. --%>


  </logic:iterate>




  <bean:define id="shipmsg" type="java.lang.String"
  name="CHECKOUT_FORM" property="shippingMessage" />
  <% if (shipmsg.length() > 0) { %>
  <tr>  <td colspan=2 class="text" align="left">
    <b><app:storeMessage key="shop.checkout.text.shippingInformation"/></b> <%= shipmsg %>
  </td></tr>
  <% } %>

  <%-- Site fields ------------------------ --%>

  <%-- Comments from the Site or Account ------------------------ --%>
  <%
    String ogcomments = "";

    if (appUser.getSite().getComments() != null) {
      ogcomments = appUser.getSite().getComments().getValue();
    }

    if ( null == ogcomments || ogcomments.length() == 0 ) {
      ogcomments = account.getComments().getValue();
    }
    if ( null != ogcomments && ogcomments.length() >= 0 &&!isOrderService) {
    %>
    <tr>    <td colspan=2 class="text" valign="top"><br>
      <%=ogcomments%>
  </td></tr>
  <%}%>

</table>

<%-- End Comments from the Site or Account  --%>
<%if(!isOrderService) {%>
<table class="tbstd" width="<%=Constants.TABLEWIDTH%>"
  cellpadding=0 cellspacing=0>
  <tr>
    <% if ( ShopTool.isInventoryShoppingOn(request) == true ) { %>

    <td align=left>
      <!-- html:image property="updateCart" border="0"/ -->
      <a href="#" class="linkButton" onclick="setAndSubmit('<%=formId%>','command','updateCart');"
      ><img src='<%=IMGPath + "/b_updatecart.gif"%>' border="0"/><app:storeMessage key="global.label.updateCart"/></a>
    </td>
    <% } %>

    <% if ( ShopTool.isAnOCISession(request.getSession()) ) {
      // In this case, always have a submit button.
    %>
    <td align=left>
      <!-- html:image  src="/images/cw_placeorder.gif\"%>" property="placeOrder" border="0"/ -->
      <a href="#" class="linkButton" onclick="setAndSubmit('<%=formId%>','command','placeOrderForAll');"
      ><img src='<%=IMGPath + "/b_placeOrder.gif"%>' border="0"/><app:storeMessage key="global.label.placeOrder"/></a>
    </td>
    <%
    }
    else  if (showPlaceOrderButton)
    {
        %>
        <td align=left>
        <!--html:image   property="placeOrder" border="0"/-->
    <a href="#" class="linkButton" onclick="setAndSubmit('<%=formId%>','command','placeOrderForAll');"
      ><img src='<%=IMGPath + "/b_placeorder.gif"%>' border="0"/><app:storeMessage key="global.label.placeOrder"/></a>

      <!--html:image   property="recalc" border="0"/-->
      <a href="#" class="linkButton" onclick="setAndSubmit('<%=formId%>','command','recalc');"
      ><img src='<%=IMGPath + "/b_recalculate.gif"%>' border="0"/><app:storeMessage key="global.label.recalculate"/></a>
    </td>
    <%
      }
    %>
  </tr>
</table>
<%}%>


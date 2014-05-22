<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

</tr>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<!-- bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/ -->


<%
StringBuilder uiSelectionLink = new StringBuilder(50);
uiSelectionLink.append("/userportal/esw/changeUI.do?");
uiSelectionLink.append(Constants.PARAMETER_OPERATION);
uiSelectionLink.append("=");
uiSelectionLink.append(Constants.PARAMETER_OPERATION_VALUE_USE_NEW_UI);

CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
String shippingAddress = "";
String addr2 = "";
String customerName = "";
String accountName = "";

if ( null != appUser && null != appUser.getSite() &&
  ShopTool.isAnOCISession(request.getSession()) == false
  ) {
  AddressData address = appUser.getSite().getSiteAddress();

  if ( null != address ) {
    shippingAddress = address.getAddress1()+" ";
    addr2 = address.getAddress2();

    if(addr2!=null && addr2.trim().length()>0 ) shippingAddress+=addr2+" ";
    String addr3 = address.getAddress3();
    if(addr3!=null && addr3.trim().length()>0 ) shippingAddress+=addr3+" ";
    String addr4 = address.getAddress4();
    if(addr4!=null && addr4.trim().length()>0 ) shippingAddress+=addr4+" ";
    shippingAddress+=address.getCity()+", ";
    if (appUser.getUserStore().isStateProvinceRequired()) {
      shippingAddress+= address.getStateProvinceCd() != null ? address.getStateProvinceCd()+" " : "";
    }
    shippingAddress+=address.getPostalCode()+" ";
    shippingAddress+=Constants.getCountryName(address.getCountryCd());
    customerName = appUser.getUser().getFirstName()+" "+appUser.getUser().getLastName()+" ";
    accountName = appUser.getSite().getBusEntity().getShortDesc();
  }

}
%>
<% SessionTool st = new SessionTool(request);%>
<script>
    var djConfig = {parseOnLoad: true, isDebug: false, usePlainJson: true},locale
    djConfig.locale = '<%=st.getUserLocaleCode(request).toString().replace('_','-').toLowerCase()%>';

</script>

<script type="text/javascript" src="../externals/dojo_1.1.0/dojo/dojo.js"></script>

<script language="JavaScript" type="text/javascript">

var xmodule = "clw.CLW";

    (function() {
        var d = dojo;
        var theme;

        theme = 'CLW';
        var themeCss = d.moduleUrl(xmodule+".themes", theme + "/" + theme + ".css");


        if (dojo.config.parseOnLoad) {
            dojo.config.parseOnLoad = false;
            dojo.config._deferParsing = true;
        }

        if (!d.hasClass(d.body(), theme)) {
            d.addClass(d.body(), theme);
        }

        d.addOnLoad(function() {
            if (dojo.config._deferParsing) {
                d.parser.parse(d.body());
            }
        });

    })();

</script>

<!--PAGESRCID=t_cwHeader.jsp-->
<script language="JavaScript" src="/<%=storeDir%>/externals/shopMenu.js">
</script>


<script language="JavaScript" src="/<%=storeDir%>/externals/shopHelp.js">
</script>
<IFRAME style="display:none; position:absolute" id="orderGuideHelp"
    height="100" width="650" noresize  scrolling="no"
    frameborder="no"
    src="/<%=storeDir%>/externals/orderGuideHelp.html">
</IFRAME>
<IFRAME style="display:none; position:absolute" id="lastOrderHelp"
    height="100" width="650" noresize  scrolling="no"
    frameborder="no"
    src="/<%=storeDir%>/externals/lastOrderHelp.html">
</IFRAME>
<IFRAME style="display:none; position:absolute" id="janitorClosetHelp"
    height="100" width="650" noresize  scrolling="no"
    frameborder="no"
    src="/<%=storeDir%>/externals/janitorClosetHelp.html">
</IFRAME>

<%
String tbarStyle = (String)session.getAttribute("pages.toolbar.style");
if (tbarStyle == null) tbarStyle = "textOnly";
%>

<%
  // Preload the images only if they are needed.
  if (! tbarStyle.equals("textOnly") ) {

%>
<script language="javascript" type="text/javascript" src="/<%=storeDir%>/externals/lib.js"></script>
<% } %>

<table id="TopHeaderTable" align="center" border="0" cellpadding="0"
 cellspacing="0" width="<%=Constants.TABLEWIDTH%>"
 style="{
  border-left:  solid black 1px;
  border-right: solid black 1px;
  border-bottom: solid black 1px;
  }"
>

<tr class="fivesidemargin shop_header_top_address">

<td>

    <% if (  null != appUser )  { %>
    <span class="address shop_header_top_address">
    <% if ( shippingAddress.length() > 0 ) { %>
    <%=accountName%>&nbsp;<app:storeMessage key="global.text.at"/>&nbsp;<%=shippingAddress%>
    <% } else { %>
    <%=accountName%>
    <% } %>
    <% if ( appUser.isaAdmin() ||
            appUser.isaCustServiceRep() ) { %>
    <br>&raquo;<a href="../storeportal/sitesearch.do">Return to Store Portal</a>
    <% } %>
    <% if ( appUser.getSiteNumber()>1 && (appUser.isaMSB() || appUser.isaCustomer() ) ) {%>
    <br>
    <% if ( appUser.getSite() != null &&
        appUser.getSite().getSiteAddress() != null &&
        appUser.getSite().getSiteAddress().getAddressTypeCd() != null &&
        appUser.getSite().getSiteAddress().getAddressTypeCd().equals
        (RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING) ) { %>
    &raquo;<a class="shop_header_top_address" href="../userportal/addshipto.do?action=edit"><app:storeMessage key="global.action.label.edit"/></a>
<%        } %>
    &raquo;<a class="shop_header_top_address" href="../userportal/customerhome.do"><app:storeMessage key="shop.header.text.choseAddress"/></a>
    <% } %>
    </span>
    <% } %>

</td>

<td width="<%=Constants.HEADER_LAST_LOGIN_WIDTH%>" align="right">
  	<%
	//if the user is logged in as somebody else, then display a "logged in as" label.  Note that this
	//should not happen, since this functionality is currently limited to the new ui, but this code
	//is included here for completeness in case we implement this functionality in the old UI.
	if (appUser.getOriginalUser() != null) {
		StringBuilder labelText = new StringBuilder(50);
		String firstName = appUser.getUser().getFirstName();
		String lastName = appUser.getUser().getLastName();
		String userName = appUser.getUser().getUserName();
		if (Utility.isSet(firstName) && Utility.isSet(lastName)) {
			labelText.append(firstName);
			labelText.append("&nbsp;");
			labelText.append(lastName);
			labelText.append("(");
		}
		labelText.append(userName);
		if (Utility.isSet(firstName) && Utility.isSet(lastName)) {
			labelText.append(")");
		}
%>
    	<span class="address shop_header_top_address">
    	<br/>
        	<app:storeMessage key="header.label.loggedInAs" />:&nbsp;<%=labelText %>
        </span>
<%
	}
	//if the user is not logged in as somebody else and has the switch ui privilege, provide
	//the link to do that
	else if (appUser != null && RefCodeNames.USER_TYPE_CD.MSB.equalsIgnoreCase(appUser.getUser().getUserTypeCd()) &&
       			appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.NEW_UI_ACCESS)) {
	%>
    	<span class="address shop_header_top_address">
    	<br/>
           	<html:link action="<%=uiSelectionLink.toString()%>">
           		<app:storeMessage key="header.label.useNewUserInterface" />
           	</html:link>
        </span>
	<%
		}
       	else {
    %>
    	&nbsp;
    <%
       	}
	%>
</td>

<td width="<%=Constants.HEADER_LAST_LOGIN_WIDTH%>" align="right">
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_siteProps.jsp")%>'/>
<span class="shop_header_top_address address">
<app:storeMessage key="shop.header.text.lastLogin"/><b>
<bean:define id="vLoginDate" name="LoginDate"/><br>
<%=ClwI18nUtil.formatDate(request,(Date)vLoginDate,DateFormat.MEDIUM)%>
 </b></span>


</td>

</tr>

<%
if ( appUser != null &&
     appUser.getSite() != null &&
     appUser.getSite().getWorkflowMessage() != null ) {
%>
<tr>

<td colspan=3 align="center">
<b><%=appUser.getSite().getWorkflowMessage()%></b>
</td>

</tr>
<% } %>

<%
SiteData siteData = ShopTool.getCurrentSite(request);
 if ( siteData != null &&
      ShopTool.showScheduledDelivery(request) &&
      siteData.getNextDeliveryDate() != null ) { %>
    <tr>

<td colspan=3 align="center">
<%
if (siteData.hasInventoryShoppingOn()) {%>   
    <app:storeMessage key="shop.header.text.inventoryShopping"/>,
<% } %>

<% if ( siteData.getNextOrdercutoffDate() != null ) { %>
<b><app:storeMessage key="shop.header.text.orderCutOff"/></b>
<!-- i18n:formatDate value="%=siteData.getNextOrdercutoffDate()%"  locale="%=appUser.getPrefLocale()%" style="medium"/ -->
<%=ClwI18nUtil.formatDate(request,siteData.getNextOrdercutoffDate(),DateFormat.MEDIUM)%>
<% } %>
<% if ( siteData.getNextDeliveryDate() != null ) { %>
&nbsp;&nbsp;<b><app:storeMessage key="shop.header.text.schedDeliveryDate"/></b>
<!-- i18n:formatDate value="%=siteData.getNextDeliveryDate()%" locale="%=appUser.getPrefLocale()%" style="medium"/ -->
<%=ClwI18nUtil.formatDate(request,siteData.getNextDeliveryDate(),DateFormat.MEDIUM)%>
<% } %>


</td>
    </tr>
<% } %>

</table>


<table id="HeaderTable" align="center" border="0" cellpadding="0"
 cellspacing="0" width="<%=Constants.TABLEWIDTH%>"
 style="border-collapse: collapse;">
<tr valign="top">

<td align="left" rowspan="2" valign="middle">
<img src='<app:custom  pageElement="pages.logo1.image" addImagePath="true" encodeForHTML="true"/>' border="0">

</td>

<%--The navigation bar--%>
<td align="right" colspan="4">
    <table cellpadding="0" cellspacing="0" class="headernavtable" style="border-collapse: collapse;">
        <td align="right">
            <a class="headernavlinkstart" href="../userportal/customerhome.do?hp_action=init">
                <app:custom pageElement="pages.home.button"/></a>
        </td>
<%
    if(appUser.isHasNotes()){
%>
        <td align="right">
            <a class="headernavlinkstart"
               href="../userportal/msbnotes.do">
                <app:storeMessage key="shop.header.text.notes"/>
            </a>
        </td>
<%
    }
%>
        <td align="right">
            <a class="headernavlinkstart"
               href="../userportal/templator.do?display=t_contactUsBody.jsp&tabs=contactUsToolBar">
                <app:storeMessage key="shop.header.text.contactUs"/></a>
        </td>
        <%
            if (session.getAttribute(Constants.CW_LOGOUT_ENABLED) == null) {
                session.setAttribute(Constants.CW_LOGOUT_ENABLED, "false");
            }
        %>

        <logic:equal name="<%=Constants.CW_LOGOUT_ENABLED%>" value="true">
            <%
                if (null != appUser && appUser.readyToShop()) {
            %>
            <td align="right">
                <a class="headernavlinkstart" href="../store/shop.do?action=catalog">
                    <app:storeMessage key="shop.header.text.search"/></a>
            </td>
            <%
                }
            %>
        </logic:equal>

        <logic:equal name="<%=Constants.CW_LOGOUT_ENABLED%>" value="true">
            <td align="right">
                <a class="headernavlink" href="../userportal/logoff.do">
                    <app:storeMessage key="shop.header.text.logOut"/></a>
            </td>
        </logic:equal>

        <logic:equal name="<%=Constants.CW_LOGOUT_ENABLED%>" value="false">
            <td align="right">
                <a class="headernavlink" href="../store/shop.do?action=catalog">
                    <app:storeMessage key="shop.header.text.search"/></a>
            </td>
        </logic:equal>

    </table>

</td>
<%--END The navigation bar--%>
</tr>

<%
   ShoppingCartData lSc = (ShoppingCartData) session.getAttribute("shoppingCart");
   if(lSc==null) {
     lSc = new com.cleanwise.service.api.value.ShoppingCartData();
     session.setAttribute("shoppingCart", lSc);
   }
%>

<% if (null != appUser && appUser.readyToShop()) { %>
<logic:present name="ApplicationUser">
    <tr>
        <td align="right">

            <logic:equal name="ApplicationUser" property="allowPurchase" value="true">
            <table align="right" border="0" cellpadding="2" cellspacing="0">
                <tr>
                   <td align="left">
                        <a href="../store/shoppingcart.do" class="linkButton">
                            <img src='<%=ClwCustomizer.getSIP(request,"b_viewcart.gif")%>' border="0"/>
                            <app:storeMessage key="global.label.shoppingCart"/>
                        </a>
                    </td>

                    <td align="left" class="text">
                        <div class="fivemargin">
                                <%=ClwI18nUtil.getShoppingItemsString(request,lSc)%>
                    </td>

                    <td align="left">
                        <a href="../store/checkout.do" class="linkButton"
                                ><img src='<%=ClwCustomizer.getSIP(request,"b_checkout.gif")%>'
                                      border=0>
                            <app:storeMessage key="global.action.label.checkout"/>
                        </a>
                    </td>

                </tr>
                <% if (null != appUser
                        && appUser.readyToShop()
                        && appUser.getSite() != null
                        && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null
                        && ShopTool.isModernInventoryCartAvailable(request)) { %>
                <tr>

                    <td align="left">
                        <a class="linkButton" href="../store/scheduledCart.do?action=showScheduledCart">
                            <img src='<%=ClwCustomizer.getSIP(request,"b_viewcart.gif")%>' border="0">
                            <app:storeMessage key="global.label.inventoryShoppingCart"/>
                        </a>
                    </td>

                    <td align="left" class="text">
                        <div class="fivemargin">
                                <%=ClwI18nUtil.getShoppingItemsString(request,(ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART))%>
                    </td>

                    <td align="left">
                        &nbsp;
                    </td>

                </tr>
                <%}%>


            </table>
            </logic:equal>


            <logic:equal name="ApplicationUser" property="allowPurchase" value="false">
                <table align="right" border="0" cellpadding="2" cellspacing="0">
                    <tr>
                        <td colspan="3">&nbsp</td>
                    </tr>
                </table>
            </logic:equal>

        </td>
    </tr>
</logic:present>

<% // application user is present %>
<% } // user is in a ready to shop state %>


</table>

<%
/*
Determine which image is on, by the current location.
*/
String lShopUrl = "../store/shop.do";

/*
This code will determine if the "i" button should be displayed
in the toolbar or not, and if so, what information should be displayed
when clicked, based on what section the user is in
*/
String shopLoc = "";
String imageFile = "";
%>
<logic:present name="shopLoc" scope="request">
<logic:match name="shopLoc" value="orderGuide">
 <%
   shopLoc="OrderGuide";
 %>
</logic:match>
<logic:match name="shopLoc" value="lastOrder">
  <%
    shopLoc="LastOrder";
  %>
</logic:match>
<logic:match name="shopLoc" value="janitorCloset">
  <%
    shopLoc="JanitorCloset";
  %>
</logic:match>
</logic:present>


<%if ( tbarStyle.equals("textOnly")) {%>
    <jsp:include flush="true" page='<%=ClwCustomizer.getStoreFilePath(request,"f_cwHeaderNavTextOnly.jsp")%>'>
        <jsp:param name="displayHelpSection" value="<%=shopLoc%>" />
        <jsp:param name="enableShop" value="<%=Boolean.toString(appUser != null && appUser.readyToShop())%>" />
        <jsp:param name="allowAssetManagement" value="<%=Boolean.toString(appUser != null && appUser.getUserStore().isAllowAssetManagement())%>" />
        <jsp:param name="shopUrl" value="<%=lShopUrl%>" />
    </jsp:include>
<%}else{%>
    <jsp:include flush="true" page='<%=ClwCustomizer.getStoreFilePath(request,"f_cwHeaderNavDefault.jsp")%>'>
        <jsp:param name="displayHelpSection" value="<%=shopLoc%>" />
        <jsp:param name="enableShop" value="<%=Boolean.toString(appUser != null && appUser.readyToShop())%>" />
        <jsp:param name="allowAssetManagement" value="<%=Boolean.toString(appUser != null && appUser.getUserStore().isAllowAssetManagement())%>" />
        <jsp:param name="shopUrl" value="<%=lShopUrl%>" />
    </jsp:include>

<%}%>





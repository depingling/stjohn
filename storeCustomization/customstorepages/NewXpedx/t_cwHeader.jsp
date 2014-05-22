<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.service.api.util.Utility"%>

<%@ page import="com.cleanwise.view.forms.UserShopForm"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<!--PAGESRCID=t_cwHeader.jsp-->

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<!-- bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/ -->

<%
StringBuilder uiSelectionLink = new StringBuilder(50);
uiSelectionLink.append("/userportal/esw/changeUI.do?");
uiSelectionLink.append(Constants.PARAMETER_OPERATION);
uiSelectionLink.append("=");
uiSelectionLink.append(Constants.PARAMETER_OPERATION_VALUE_USE_NEW_UI);

CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
String shippingAddress1 = "";
String shippingAddress2 = "";
String addr2 = "";
String customerName = "";
String accountName = "";
boolean approverFl = false;
boolean showInvCartTotal = false;
if ( null != appUser && null != appUser.getSite() &&
  ShopTool.isAnOCISession(request.getSession()) == false
  ) {
  AddressData address = appUser.getSite().getSiteAddress();

  if ( null != address ) {
    shippingAddress1 = address.getAddress1()+" ";
    addr2 = address.getAddress2();

    if(addr2!=null && addr2.trim().length()>0 ) shippingAddress1+=addr2+" ";
    String addr3 = address.getAddress3();
    if(addr3!=null && addr3.trim().length()>0 ) shippingAddress1+=addr3+" ";
    String addr4 = address.getAddress4();
    if(addr4!=null && addr4.trim().length()>0 ) shippingAddress1+=addr4+" ";
    //shippingAddress+=address.getCity()+", ";
	shippingAddress2 = address.getCity()+", ";
    if (appUser.getUserStore().isStateProvinceRequired()) {
      shippingAddress2+= address.getStateProvinceCd() != null ? address.getStateProvinceCd()+" " : "";
    }
    shippingAddress2+=address.getPostalCode()+" ";
    shippingAddress2+=Constants.getCountryName(address.getCountryCd());
    customerName = appUser.getUser().getFirstName()+" "+appUser.getUser().getLastName()+" ";
    accountName = appUser.getSite().getBusEntity().getShortDesc();

  }

  AccountData accD = appUser.getUserAccount();
  String isShowInvCartT = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_INV_CART_TOTAL);
  if(isShowInvCartT.equals("true")){
	showInvCartTotal=true;
  }
}


//looking for Approve permissions
if ( null != appUser && null != appUser.getUser()) {
  String workflowRole = appUser.getUser().getWorkflowRoleCd();
  approverFl = (workflowRole.indexOf("APPROVER") >= 0)?true:false;
}
String searchStr = "";
UserShopForm shopForm = (UserShopForm) session.getAttribute("SHOP_FORM");
if(shopForm!=null && shopForm.getSearchName()!=null) {
  searchStr = shopForm.getSearchName();
}
%>

<%
String tbarStyle = (String)session.getAttribute("pages.toolbar.style");
if (tbarStyle == null) tbarStyle = "textOnly";
%>

<%--NOT SUPPORTED
<%
  // Preload the images only if they are needed.
  if (! tbarStyle.equals("textOnly") ) {

%>
<script language="javascript" type="text/javascript" src="/<%=storeDir%>/externals/lib.js"></script>
<% } %>
--%>
<script language="JavaScript1.2">

function searchIfEnterPressed(e) {
   var unicode=e.keyCode? e.keyCode : e.charCode;
   if(unicode==13){
      search();
   }
}

function search() {
    var objA = document.getElementById('searchField');
	val = objA.value;
   	var url = "../store/shop.do?action=integratedSearch&searchName="+val;
    document.location.href=url;
	return false;
}

</script>
<% SessionTool st = new SessionTool(request);%>

<script>
    var djConfig = {parseOnLoad: true, isDebug: false, usePlainJson: true},locale
    djConfig.locale = '<%=st.getUserLocaleCode(request).toString().replace('_','-').toLowerCase()%>';

</script>

<script type="text/javascript" src="../externals/dojo_1.1.0/dojo/dojo.js"></script>

<script language="JavaScript" type="text/javascript">

var xmodule = "clw.NewXpedx";

    (function() {
        var d = dojo;
        var theme;

        theme = 'NewXpedx';
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
<style type="text/css">
.xpdexGradientButton {
        background:
        url( '<%=ClwCustomizer.getSIP(request,"buttonMiddle.png")%>' ) repeat-x top;
}
.xpdexGradientButton:hover {
        color:#FFFFFF;
        background:
        url( '<%=ClwCustomizer.getSIP(request,"buttonMiddle.png")%>' ) repeat-x top;

}
a.xpdexGradientButton:link, a.xpdexGradientButton:visited, a.xpdexGradientButton:active {
        background:
        url( '<%=ClwCustomizer.getSIP(request,"buttonMiddle.png")%>' ) repeat-x top;
}
a.xpdexGradientButton:hover {
        color:#FFFFFF;
        background:
        url( '<%=ClwCustomizer.getSIP(request,"buttonMiddle.png")%>' ) repeat-x top;
}

.shoppingCartGreyBorder {
        border-left: 1px solid #dedede;
        border-collapse: collapse;
}

.xpedxMainMenuTabHover {
    text-decoration: none;
    text-transform: capitalize;
    border: 0;
    font-weight: bold;
    font-size: 12px;
    padding:0;
    margin:0;
    color: white;
    cursor: pointer;
    cursor: hand;
    background: url( '<%=ClwCustomizer.getSIP(request,"mainmenubg.gif")%>' );
}

</style>

<bean:define id="shoppingCartForm" name="SHOPPING_CART_FORM" type="com.cleanwise.view.forms.ShoppingCartForm"/>

<table id="TopHeaderTable" align="center" border="0" cellpadding="0"
 cellspacing="0" width="<%=Constants.TABLEWIDTH800%>">
<%
	//logs to track changes to session variable "pages.css. Logs are
	//added as part of bug "2485 - Orderline reverting back to old style"
	String val = (String) session.getAttribute("pages.css");
	//end of logs for bug 2485
%>
<tr class="fivesidemargin shop_header_top_address">
  <%-- Logo & Address --%>
  <td valign="middle" class="text" width="30%" >
  <%--    <img src="<%=ClwCustomizer.getSIP(request,"logo.png")%>" border="0" hspace="4">--%>
    <img src='<app:custom  pageElement="pages.logo1.image" addImagePath="true" encodeForHTML="true"/>' border="0">
    <br>&nbsp;<br><b>
        <% if (  null != appUser )  { %>
        <span class="text" style="font-weight: bold; font-size: 8pt">
        <% if ( shippingAddress1.length() > 0 ) { %>
        <%=accountName%>&nbsp;<app:storeMessage key="global.text.at"/>&nbsp;<%=shippingAddress1%>
        <br>
        <%=shippingAddress2%><br>
        <% } else { %>
        <%=accountName%>
        <% } %>
        </span>
         <% } %>
    </b>
  </td>
  <%-- Budget table --%>
  <td valign="middle" class="budget" width="35%" align="center">
    <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_budgetInfoInc.jsp")%>'>
      <jsp:param name="jspFormName"             value="SHOPPING_CART_FORM" />
    </jsp:include>
  </td>
  <%-- Navigation Buttons --%>
  <td valign="top" width="35%" align="right">
    <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
    <tr>
    <td>

     <table align = "right" border="0" cellpadding="0" cellspacing="0" height="10">

     <tr>
<%--
   <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.APPROVE_ORDERS_ACCESS)) { %>
   --%>
   <% if(approverFl){ %>
<%String pendAppUrl="../store/orderstatus.do?action=initXPEDXListOrders&bkdte=true&orderStatus="+RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL;%>
     <app:xpedxButton label='shop.header.text.approveOrder'
	     url="<%=pendAppUrl%>"
	 />
     <td>&nbsp;</td>
    <% } %>

   <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CHANGE_LOCATION_ACCESS) && appUser.getSiteNumber() > 1) { %>
     <app:xpedxButton label='shop.header.text.changeLocation'
                      url='../userportal/msbsites.do?action=initLocationCriteria'/>

     <td>&nbsp;</td>
	<% } %>
     <%
        if(session.getAttribute(Constants.CW_LOGOUT_ENABLED)==null) {
            session.setAttribute(Constants.CW_LOGOUT_ENABLED,"false");
        }
     %>
     <logic:equal name="<%=Constants.CW_LOGOUT_ENABLED%>" value="true">
     <app:xpedxButton label='shop.header.text.logOut'
	     url='../userportal/logoff.do'
	 />
     </logic:equal>
     </tr>
     </table>


    </td>
    </tr>

    <tr>
    <td align="right" height="28" valign=middle>
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
    </tr>


    <tr>
    <td align="right">
    <br>
     <%
     boolean isUsedPhysicalInventoryAlgorithm = ShopTool.isUsedPhysicalInventoryAlgorithm(request);
     boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);
     ShoppingCartData lSc = (ShoppingCartData) session.getAttribute("shoppingCart");
      if(lSc==null) {
        lSc = new com.cleanwise.service.api.value.ShoppingCartData();
        session.setAttribute("shoppingCart", lSc);
      }
      if (appUser.getSite() != null
          && ShopTool.isModernInventoryShopping(request)
          && !ShopTool.hasDiscretionaryCartAccessOpen(request)
          && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) {
          lSc = (ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART);
        }

        String cartLocaleCd = lSc.getLocaleCd();
        int qty = lSc.getItemsQty();
        Double cost;
        if (appUser.getSite() != null && ShopTool.isModernInventoryShopping(request)
            && !ShopTool.hasDiscretionaryCartAccessOpen(request)
            && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null
            && !showInvCartTotal) {

                ShoppingCartItemDataVector allItems = lSc.getItems();
               double discTotal = 0;

                for(int i=0; i<allItems.size(); i++){
                    ShoppingCartItemData thisItem = (ShoppingCartItemData) allItems.get(i);
                    if(!thisItem.getIsaInventoryItem()){
                        discTotal+=thisItem.getAmount();
                    }
                }
                cost = new Double(discTotal);
        }else{
               cost = new Double(lSc.getItemsCost());
        }

        String localeCost =  ClwI18nUtil.getPriceShopping(request,cost,cartLocaleCd,null," ");

        String itemString = ClwI18nUtil.getMessage(request, "shoppingCart.text.itemsString",null);
        String itemsS = qty + "&nbsp;" + itemString + "&nbsp;&nbsp;" + localeCost;
     %>

     <%  if (null != appUser && appUser.readyToShop()) { %>
     <logic:present name="ApplicationUser">
     <logic:equal name="ApplicationUser" property="allowPurchase" value="true">


     <table cellpadding="0" cellspacing="0" border="0">

<%
    String hrefStr = "../store/shoppingcart.do";
    if (appUser.getSite() != null
      && ShopTool.isModernInventoryShopping(request)
      && !ShopTool.hasDiscretionaryCartAccessOpen(request)
      && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) {
        hrefStr = "../store/scheduledCart.do?action=showScheduledCart";
    }
%>
     <table cellpadding="0" cellspacing="0" border="0">
       <tr><td>

      <div class="itemGroupShortInfo"  style="background-color:#fff">

      <b class=rtopborder style="background-color:#fff">
           <b class=r1border style="background-color:#b5b5b5"></b>
           <b class=r2border style="background-color:#fff"></b>
           <b class=r3border style="background-color:#fff"></b>
           <b class=r4border style="background-color:#fff"></b>
      </b>

      <div class="borderContent" style="border-left:1px solid #B5B5B5;border-right:1px solid #B5B5B5;background-color:white">

	   <table cellpadding="0" cellspacing="0">
       <tr>

        <%
        if (isUsedPhysicalInventoryAlgorithm &&
            isPhysicalCartAvailable) {
        %>
            <td>
                <a href="../store/physicalCart.do?action=showPhysicalCart">
                    <img src="<%=ClwCustomizer.getSIP(request,"physical_cart_48x48.png")%>" alt="" align="left" border="0">
                </a>
            </td>
            <td>

                <a href="../store/physicalCart.do?action=showPhysicalCart" style="color:#FF0000;" class="shoppingCartButton">
                <app:storeMessage key="shoppingCart.text.physicalCart"/>&nbsp;

                </a>
            </td>
            <td class="shoppingCartGreyBorder" style="color:#000000;">
                <a href="../store/physicalCart.do?action=showPhysicalCart" class="shoppingCartButton" style="color:#000000;font-weight: normal;"><%=itemsS%></a>
            </td>
        <%

        } else {
            if (appUser.getSite() != null
                && ShopTool.isModernInventoryShopping(request)
                && !ShopTool.hasDiscretionaryCartAccessOpen(request)
                && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) {
        %>
            <td>
                <a href="../store/scheduledCart.do?action=showScheduledCart">
                    <img src="<%=ClwCustomizer.getSIP(request,"cart_48x48.png")%>" alt="" align="left" border="0">
                </a>
            </td>
            <td>
                <a href="../store/scheduledCart.do?action=showScheduledCart" class="shoppingCartButton">
                <app:storeMessage key="shoppingCart.text.scheduledCart"/>&nbsp;</a>
            </td>
            <td class="shoppingCartGreyBorder" style="color:#000000;">
                <a href="../store/scheduledCart.do?action=showScheduledCart" class="shoppingCartButton" style="color:#000000;font-weight: normal;"><%=itemsS%></a>
            </td>
        <%
            } else {
        %>
            <td>
                <a href="../store/shoppingcart.do" class="shoppingCartButton">
                    <img src="<%=ClwCustomizer.getSIP(request,"cart_48x48.png")%>" alt="" align="left" border="0">
                </a>
            </td>
            <td>
                <a href="../store/shoppingcart.do" class="shoppingCartButton"><app:storeMessage key="shoppingCart.text.shoppingCart"/>&nbsp;</a>
            </td>
            <td class="shoppingCartGreyBorder" style="color:#000000;">
                <a href="../store/shoppingcart.do" class="shoppingCartButton" style="color:#000000;font-weight: normal;"><%=itemsS%></a>
            </td>
        <%
            }
        }
        %>
       </tr>
     </table>

     </div>
          <b class=rbottomborder style="background-color:#fff">
              <B class=r4border style="background-color:#fff"></b>
              <b class=r3border style="background-color:#fff"></b>
              <b class=r2border style="background-color:#fff"></b>
              <b class=r1border style="background-color:#b5b5b5"></b>
          </b>
     </div>

    </td></tr>
    </table>

<%--
       </div>
   <b class="r1"></b><b class="r1"></b><b class="r3"></b>
   </div>
--%>

     </logic:equal>
     </logic:present><% // application user is present %>
     <br>

    </td>
    </tr>



    <tr>
    <td align="right" valign="top">
     <% } // user is in a ready to shop state %>

     <table border="0" cellpadding="0" cellspacing="0" >
     <tr>
	     <td valign="middle">
		   <a href="javascript:search();" class="shoppingCartButton"
		   ><app:storeMessage key="global.action.label.search"/>&nbsp;</a></td
		   ><td><a href="javascript:search();" class="shoppingCartButton"
		   ><img src="<%=ClwCustomizer.getSIP(request,"arrow.gif")%>" border="0" ></a></td
	     ><td align="left"><input id="searchField" type="text" value="<%=searchStr%>"
		 class="inputText" size="20" onkeypress="searchIfEnterPressed(event)"></td
		 ><td align="left"><a href="javascript:search();" class="shoppingCartButton"
		 ><img src="<%=ClwCustomizer.getSIP(request,"search.gif")%>" border="0" align="left"></a></td>
     </tr>
     </table>

<%-- </td> --%>

  </td>
  </tr>
  </table>

</tr>

</table>
<% // end of header %>

<%
/*
Determine which image is on, by the current location.
*/
String lShopUrl = "../store/shop.do";
%>


<%if ( tbarStyle.equals("textOnly")) {%>
<%--NOT SUPPORTED
    <jsp:include flush="true" page='<%=ClwCustomizer.getStoreFilePath(request,"f_cwHeaderNavTextOnly.jsp")%>'>
        <jsp:param name="displayHelpSection" value="<%=shopLoc%>" />
        <jsp:param name="enableShop" value="<%=Boolean.toString(appUser != null && appUser.readyToShop())%>" />
        <jsp:param name="allowAssetManagement" value="<%=Boolean.toString(appUser != null && appUser.getUserStore().isAllowAssetManagement())%>" />
        <jsp:param name="shopUrl" value="<%=lShopUrl%>" />
    </jsp:include>
	--%>
<%}else{ %>
    <jsp:include flush="true" page='<%=ClwCustomizer.getStoreFilePath(request,"f_cwHeaderNavDefault.jsp")%>'>
        <jsp:param name="enableShop" value="<%=Boolean.toString(appUser != null && appUser.readyToShop())%>" />
        <jsp:param name="allowAssetManagement" value="<%=Boolean.toString(appUser != null && appUser.getUserStore().isAllowAssetManagement())%>" />
        <jsp:param name="shopUrl" value="<%=lShopUrl%>" />
    </jsp:include>
<%}%>



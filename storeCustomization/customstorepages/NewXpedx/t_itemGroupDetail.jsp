<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartItemData" %>
<%@ page import="com.cleanwise.service.api.value.AccountData" %>
<%@ page import="com.cleanwise.view.forms.UserShopForm" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.OrderGuideData" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!--PAGESRCID=t_itemGroupDetail.jsp-->
<script language="JavaScript1.2">
    <!--

    function actionMultiSubmit(actionDef, action) {

        var actionElements = document.getElementsByName('action');
        if(actionElements.length){
            for ( var i = actionElements.length-1; i >=0; i-- ) {

                var element = actionElements[i];
                if(actionDef == element.value){
                    element.value = action;
                    document.forms[0].submit();

                    break;
                }
            }
        }  else if(actionElements){
            actionElements.value = action;
            document.forms[0].submit();
        }

        return false;
    }

    function setCommandAndSubmit(value) {
        var aaa = document.forms[0].elements['command'];
        aaa.value=value;
        aaa.form.submit();
        return false;

    }
    //-->
</script>
<script type="text/javascript">
// <![CDATA[
function changeOrderGuideOp(obj,id1) {

  txt = obj.options[obj.selectedIndex].value;
  f_hideBox("IGROUP_DETAIL_BOX_messageText");
  if ( txt.match('-2') ) {
    f_showBox("IGROUP_DETAIL_BOX_createNewList");
    f_hideBox("IGROUP_DETAIL_BOX_saveToList");
  } else if ( txt.match('-1') ) {
    f_hideBox("IGROUP_DETAIL_BOX_createNewList");
    f_hideBox("IGROUP_DETAIL_BOX_saveToList");
  } else {
    f_hideBox("IGROUP_DETAIL_BOX_createNewList");
    f_showBox("IGROUP_DETAIL_BOX_saveToList");
  }

}

function f_hideBox(boxid) {
//  document.getElementById(boxid).style.display = 'none';
  if (document.getElementById(boxid) != null){
    document.getElementById(boxid).style.display = 'none';
  }
}

function f_hideAll() {
  f_hideBox("IGROUP_DETAIL_BOX_selectList");
  f_hideBox("IGROUP_DETAIL_BOX_createNewList");
  f_hideBox("IGROUP_DETAIL_BOX_messageText");
}

function f_showBox(boxid) {
  if (document.getElementById(boxid) != null){
    document.getElementById(boxid).style.display = 'block';
    f_hideBox("IGROUP_DETAIL_BOX_messageText");
  }
}


// ]]>
</script>
<%
    String storeDir=ClwCustomizer.getStoreDir();
%>


<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>

<%
    int itemId = theForm.getItemId();
%>
<logic:present name="SHOP_FORM" property="itemDetail">
<bean:define id="size" name="SHOP_FORM" property="cartItemsSize"/>
<logic:greaterThan name ="size" value="0">
<html:form name="SHOP_FORM" action="/store/shop.do?source=t_itemGroupDetail.jsp">
<html:hidden name="SHOP_FORM" property="itemId" value="<%=\"\"+itemId%>"/>
<html:hidden name="SHOP_FORM" property="groupedItemView" value="true"/>
<bean:define id="itemGroup" name="SHOP_FORM" property="itemDetail" type="com.cleanwise.service.api.value.ShoppingCartItemData"/>

<!-- item picture and long description -->
<%ShoppingCartItemData item = ((UserShopForm) theForm).getItemDetail();%>
<table border="0" cellpadding="0" cellspacing="0" width="100%>">

<tr>
    <td align="center" valign="top">
   
        <table width="50%">
            <tr>
                <td>
                    <% String image=item.getProduct().getImage();
                        if(image!=null && image.trim().length()>0) {             %>
                    <img width="180" height="180" src="/<%=storeDir%>/<%=item.getProduct().getImage()%>">
                    <% } else { %>
                    <img width="180" height="180" src="/<%=storeDir%>/<%="/en/images/noManXpedxImg.gif"%>"/>
                    <% } %>
                </td>
            </tr>
        </table>
    </td>
    <td valign="top">
        <table width="250">
            <tr><td class="itemDetailShortDesc">
                <% String skuDesc = itemGroup.getProduct().getCustomerProductShortDesc();
                    if(skuDesc==null || skuDesc.trim().length()==0) {
                        skuDesc = itemGroup.getProduct().getShortDesc();
                    }
                %>  <b><%=Utility.strNN(skuDesc)%></b>
            </td>

            </tr>
            <tr><td><%=Utility.strNN(item.getProduct().getLongDesc())%></td></tr>
        </table>
    </td>
</tr>

<tr>
    <td colspan="2">
        &nbsp;
    </td>
</tr>

<tr>
    <td colspan="8">
        <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"t_itemGroupDetailInfo.jsp")%>'>
            <jsp:param name="formName" value="SHOP_FORM"/>
        </jsp:include>
    </td>
</tr>

<tr>
<td colspan="2" align="center">


 <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <table align = "center" border="0" cellpadding="0" cellspacing="0" >
    <tr>
    <td>
    <table border="0" cellpadding="0" cellspacing="0" >
    <tr> <td align="center">
        <table border="0" cellpadding="0" cellspacing="0" >
		<tr >
		<td colspan="4"><img src="images/spacer.gif" height="6"></td>
		</tr>
            <tr>
                <% if (!ShopTool.isPhysicalCartAvailable(request)) { %>
                    <td> <%----- clearQuantities --%>
        <table cellpadding="0" cellspacing="0"  border="0">
            <tr>
					<app:xpedxButton label='global.label.clearQuantities' 
					url="#"
					onClick='actionMultiSubmit("hiddenAction", "clear");'
					/>	 

            </tr>
        </table>
    </td>
                    <%----- addToCart --%>

    <td>
        <table cellpadding="0" cellspacing="0"  border="0">
            <tr>
					<app:xpedxButton label='global.label.addToCart' 
					url="#"
					onClick='actionMultiSubmit("hiddenAction", "addToActiveXpedxCart");'
					/>	 
            </tr>
        </table>
    </td>

    <td><%----- checkout --%>
        <table cellpadding="0" cellspacing="0"  border="0">
            <tr>
					<app:xpedxButton label='global.action.label.checkout' 
					url="#"
					onClick='actionMultiSubmit("hiddenAction", "addToCartViewcart");'
					/>	 
            </tr>
        </table>
    </td>
    <% } else {%>
    
    <td>
               	<span style="color:#FF0000;" class="shoppingCartButton">
          <app:storeMessage key="shoppingCart.text.physicalCart"/>
        </span>
               </td>
    
    <%} %>
            </tr>    </table>
    </td>
    </tr>
	<tr >
	<td colspan="4"><img src="images/spacer.gif" height="6"></td>
	</tr>
	<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		AccountData accD = appUser.getUserAccount();
		String showMyShoppingLists = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_MY_SHOPPING_LISTS);
		if (showMyShoppingLists.equals("true")) { %>
    <tr>
        <td align="center">

            <table border="0" cellpadding="0" cellspacing="0">
                <tr>

					<app:xpedxButton label='shoppingCart.text.addCart' 
					url="#"
					onClick='f_showBox("IGROUP_DETAIL_BOX_selectList");'
					/>	 

                </tr>
            </table>
        </td>
    </tr>
	<%}%>

       <%--Confirmation/Errors/Warnings message Area--%>
    <%
        String messCd = "";
        String messStyle = "color:#FFFFFF;";
        String mess = "&nbsp;";
        if (ShopTool.requestContainsErrors(request)) {
            messCd = "errors";
            messStyle = "color:#FF0000; white-space: normal; ";
        } else if ((ShopTool.cartContainsWarnings(request))) {
            messCd = "warnings";
            messStyle = "color:#FF0000; white-space: normal;";
        } else if (Utility.isSet(((UserShopForm)theForm).getConfirmMessage())) {
            messCd = "confirmation";
            messStyle = "color:#003399; ";
            mess = ((UserShopForm) theForm).getConfirmMessage();
        }

    %>
    <tr>
        <td align="center">
            <div id="IGROUP_DETAIL_BOX_messageText" style="visibility:visible">
                <table  border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td align="center" style="<%=messStyle%>">
                            <%if (messCd.equals("errors")) {%>
                            <html:errors/>
                            <%} else if (messCd.equals("warnings")) {%>
                            <jsp:include flush='true'
                                         page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'/>
                            <%} else {%>
                            <b><%=mess%></b>
                            <%} %>
                        </td>
                        <td style="padding-right:40px">&nbsp;</td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
        <%-------------------------------------%>
    <tr>
        <td align="center">
            <div id="IGROUP_DETAIL_BOX_selectList" style="display:none">

                <table width="100%" border="0"  align="center" cellpadding="0" cellspacing="0">
                    <tr align="center">
                        <td>
                            <table>
                                <tr>
                                    <td>
                                        <!-- Order guide select section -->

                                        <html:select name="SHOP_FORM" property="selectedShoppingListId"
                                                     onchange="changeOrderGuideOp(this,'-2')">
                                            <html:option value="-1">
                                                <app:storeMessage key="shoppingCart.text.selectList"/>
                                            </html:option>
                                            <html:option value="-2">
                                                <app:storeMessage key="shoppingCart.text.createNewList"/>
                                            </html:option>
                                            <logic:present name="SHOP_FORM" property="userOrderGuides">
                                                <logic:iterate id="og" name="SHOP_FORM" property="userOrderGuides"
                                                               type="com.cleanwise.service.api.value.OrderGuideData">
                                                    <html:option
                                                            value="<%=String.valueOf(((OrderGuideData)og).getOrderGuideId())%>"><%=
                                                        ((OrderGuideData) og).getShortDesc()%>
                                                    </html:option>
                                                </logic:iterate>
                                            </logic:present>
                                        </html:select>
                                    </td>
                                    <td>
                                        <div id="IGROUP_DETAIL_BOX_saveToList" style="display:none">
                                            <table  border="0" cellpadding="0"
                                                    cellspacing="0">
                                                <tr>
                                                    <td align="right" valign="middle"><img
                                                            src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>'
                                                            border="0"></td>
                                                    <td align="center" valign="middle" nowrap
                                                        class="xpdexGradientButton">
                                                        <a class="xpdexGradientButton" href="#"
                                                           onclick="actionMultiSubmit('hiddenAction', 'updateUserOrderGuide');">
                                                            <app:storeMessage key="global.action.label.save"/>
                                                        </a>
                                                    </td>
                                                    <td align="left" valign="middle"><img
                                                            src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>'
                                                            border="0"></td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </table>

                        </td>

                    </tr>
                    <tr>
                        <td>
                            <div id="IGROUP_DETAIL_BOX_createNewList" style="display:none">
                                <table align="center" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td style="padding-left:40px">

                                            <html:text name="SHOP_FORM" property="orderGuideName" maxlength="30"/>
                                        </td>
                                        <td>&nbsp;</td>
                                        <td>
                                            <table align="right" border="0" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td align="right" valign="middle"><img
                                                            src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>'
                                                            border="0"></td>
                                                    <td align="center" valign="middle" nowrap class="xpdexGradientButton">
                                                        <a class="xpdexGradientButton" href="#"
                                                           onclick="actionMultiSubmit('hiddenAction', 'saveUserOrderGuide');">
                                                            <app:storeMessage key="global.action.label.save"/>
                                                        </a>
                                                    </td>
                                                    <td align="left" valign="middle"><img
                                                            src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>'
                                                            border="0"></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

        </td>
    </tr>
    </table>
    </td>
    </tr>
    </table>
    </logic:equal>


</td>
</tr>
</table>
<% if (RefCodeNames.SHOP_UI_TYPE.B2B.equals(ShopTool.getShopUIType(request, RefCodeNames.SHOP_UI_TYPE.B2C))) {%>
</table>
<%}%>
<html:hidden property="action" value="hiddenAction"/>
<html:hidden property="source" value="t_itemGroupDetail.jsp"/>
</html:form>

</logic:greaterThan>
</logic:present>
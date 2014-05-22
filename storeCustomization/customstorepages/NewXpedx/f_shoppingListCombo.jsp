<%@ page language="java" %>

<%@ page import="java.util.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.session.*" %>

<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script type="text/javascript">
// <![CDATA[
function display(obj,id1) {

  txt = obj.options[obj.selectedIndex].value;
  f_hideBox("BOX_messageText");
  if ( txt.match('-2') ) {
    f_showBox("BOX_createNewList");
    f_hideBox("BOX_saveToList");
  } else if ( txt.match('-1') ) {
    f_hideBox("BOX_createNewList");
    f_hideBox("BOX_saveToList");
  } else {
    f_hideBox("BOX_createNewList");
    f_showBox("BOX_saveToList");
  }

}

function f_hideBox(boxid) {
//  document.getElementById(boxid).style.display = 'none';
  if (document.getElementById(boxid) != null){
    document.getElementById(boxid).style.visibility = 'hidden';
  }
}

function f_hideAll() {
  f_hideBox("BOX_selectList");
  f_hideBox("BOX_createNewList");
  f_hideBox("BOX_messageText");
}

function f_showBox(boxid) {
  //document.getElementById(boxid).style.display = 'block';
  if (document.getElementById(boxid) != null){
    document.getElementById(boxid).style.visibility = 'visible';
    f_hideBox("BOX_messageText");
  }
}


// ]]>
</script>

<%String confirmation = request.getParameter("confirmMessage");%>
<%String errors = request.getParameter("errorMessage");%>
<%String warnings = request.getParameter("warningMessage");%>

<%-- String IPTH = (String) session.getAttribute("pages.store.images"); --%>
<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>
<%
  OrderGuideDataVector userList = null;
  OrderGuideDataVector templateList = null;
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
  APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
  ShoppingServices shoppingServBeen = factory.getShoppingServicesAPI();

  int userId = appUser.getUserId();
  int siteId = appUser.getSite().getBusEntity().getBusEntityId();
  int catalogId = 0;
  Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
  if (catalogIdI != null) {
    catalogId = catalogIdI.intValue();
  }

  userList = shoppingServBeen.getUserOrderGuides( userId,catalogId,siteId);

  ArrayList userIds = new ArrayList();
  ArrayList userNames = new ArrayList();
  for(int ii=0; ii<userList.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) userList.get(ii);
      userIds.add(""+ogD.getOrderGuideId());
      userNames.add(ogD.getShortDesc());
  }
%>

<table ID=164 width="100%" align = "center" border="0" cellpadding="0" cellspacing="0"  >
<%-- <html:form styleId="165"  action="/store/shop.do?action=orderGuideSelect">--%>

<tr> <td>
   <table align = "right" border="0" cellpadding="0" cellspacing="0" >
<tr><td><img src="/defst/store//images/spacer.gif" border="0" height="4"></td></tr>
     <tr>

	 <a name="buttonSection"></a>
      <% //if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.APPROVE_ORDERS_ACCESS)) { %>
       <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
       <td align="center" valign="middle" nowrap class="xpdexGradientButton">
         <a class="xpdexGradientButton" href="#buttonSection"  onclick="f_showBox('BOX_selectList')"><app:storeMessage key="shoppingCart.text.addCart"/></a>
       </td>
       <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
       <td style="padding-right:40px">&nbsp;</td>
      <%// } %>
     </tr>
   </table>
 </td> </tr>

<%--Confirmation/Errors/Warnings message Area--%>
<%
String mess="&nbsp;";
String messStyle = "color:#FFFFFF;";

if (errors!=null && errors.trim().length() > 0) {
  mess = errors;
  messStyle = "color:#FF0000; white-space: normal; ";
} else if ((errors.trim().length() == 0) && (warnings!=null && warnings.trim().length() > 0)){
  mess = warnings;
  messStyle = "color:#FF0000; white-space: normal;";
} else if ((warnings.trim().length() == 0) && (errors.trim().length() == 0) && (confirmation!=null && confirmation.trim().length() > 0)) {
  mess= confirmation;
  messStyle = "color:#003399; ";
}
%>
<tr> <td>
<div id="BOX_messageText" style="visibility:visible" >
  <table   align = "center" border="0" cellpadding="0" cellspacing="0" >
    <tr>
      <td   align="center" style="<%=messStyle%>">
      <%if (mess.equals("errors")) {%>
        <html:errors/>
      <%} else if (mess.equals("warnings")) {%>
        <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'/>
      <%} else {%>
        <b><%=mess%></b>
      <%} %>
      </td>
      <td style="padding-right:40px">&nbsp;</td>
    </tr>
  </table>
</div>
</td></tr>
<%-------------------------------------%>
<tr> <td>
<div id="BOX_selectList" style="visibility:hidden">

<table align = "right" border="0" cellpadding="0" cellspacing="0" >
  <tr>
  <td>
<!-- Order guide select section -->

      <html:select name="SHOPPING_CART_FORM" property="selectedShoppingListId"
      onchange="display(this,'-2')" >
      <%--      onchange="javascript:  document.forms[0].submit();"  >   --%>
      <html:option value="-1"><app:storeMessage key="shoppingCart.text.selectList"/></html:option>
      <html:option value="-2"><app:storeMessage key="shoppingCart.text.createNewList"/></html:option>
      <% if(userIds.size()>0 ) {%>
        <% for(int ii=0; ii<userIds.size(); ii++) { %>
           <html:option value="<%=(String)userIds.get(ii)%>"><%=(String)userNames.get(ii)%></html:option>
        <% } %>
      <% } %>
      </html:select>

    </td>
    <td>&nbsp;</td>
    <td>
    <div id="BOX_saveToList" style="visibility:hidden">

      <table align = "right" border="0" cellpadding="0" cellspacing="0" >
        <tr>
          <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
          <td align="center" valign="middle" nowrap class="xpdexGradientButton">
            <a class="xpdexGradientButton" href="#buttonSection" onclick="actionMultiSubmit('submitQty','updateOrderGuide');"><app:storeMessage key="global.action.label.save"/></a>
          </td>
          <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
        </tr>
      </table>
    </div>

    </td>

    </tr>
    </table>
 </div>

</td></tr>
<%-- 3 --%>
<tr><td>
<div id="BOX_createNewList" style="visibility:hidden">
  <table align = "right" border="0" cellpadding="0" cellspacing="0" >
    <tr>
      <td><html:text name="SHOPPING_CART_FORM" property="userOrderGuideName" maxlength="30"/></td>
      <td>&nbsp;</td>
      <td>
        <table align = "right" border="0" cellpadding="0" cellspacing="0" >
          <tr>
            <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
            <td align="center" valign="middle" nowrap class="xpdexGradientButton">
              <a class="xpdexGradientButton" href="#buttonSection" onclick="actionMultiSubmit('submitQty','saveOrderGuide');"><app:storeMessage key="global.action.label.save"/></a>
            </td>
            <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</td></tr>

</table>

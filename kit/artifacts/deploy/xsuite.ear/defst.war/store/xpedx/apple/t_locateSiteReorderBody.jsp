<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript">
<!--
function f_tcb() {
  var tsf = document.forms[0].searchField.value;
  if ( "" == tsf ) {
    document.forms[0].searchType[0].checked='false';
    document.forms[0].searchType[1].checked='false';
  } else {

    if (document.forms[0].searchType[0].checked =='true' ) {
      document.forms[0].searchType[0].checked='true';
      document.forms[0].searchType[1].checked='false';
    } else {
      document.forms[0].searchType[0].checked='false';
      document.forms[0].searchType[1].checked='true';
    }
  }
}

function actionMultiSubmit(actionDef, action) {
  var aaal = document.getElementsByName('action');
  for ( var i = 0; i < 1; i++ ) {
    var aaa = aaal[i];
    if(aaa.value==actionDef) {
      aaa.value = action;
      aaa.form.submit();
      break;
    }
  }
 return false;
}
-->
</script>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER); %>

<div class="text"><font color=red><html:errors/></font></div>

<bean:define id="toolBarTab" type="java.lang.String" value="sites"
toScope="request"/>
<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_msbToolbar.jsp")%>'/>
</table>

<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">
<tr>
<td bgcolor="black" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td align="center">
  <table width="700" >
  <html:form name="SITE_SEARCH_FORM" action="/userportal/msbsites.do"
    scope="session" type="com.cleanwise.view.forms.SiteMgrSearchForm"
    focus="searchField"
   >

  <tr>
  <td align="right"><b><app:storeMessage key="msbSites.text.findSite"/></b></td>
  <td>
  <html:text name="SITE_SEARCH_FORM" property="searchField"
    onchange="javascript: f_tcb();"
    onblur="javascript: f_tcb();"
    onfocus="javascript: f_tcb();"
/>

  <html:radio name="SITE_SEARCH_FORM" property="searchType"
                    value="nameBegins" /> <app:storeMessage key="msbSites.text.nameStartsWith"/>
  <html:radio name="SITE_SEARCH_FORM" property="searchType"
                   value="nameContains" /> <app:storeMessage key="msbSites.text.nameContains"/>
  </td>
  </tr>

  <tr>
   <td align="right"><b><app:storeMessage key="msbSites.text.city"/></b></td>
   <td><html:text name="SITE_SEARCH_FORM" property="city" />
  </td>
  </tr>
  <tr>
  <% if (appUser.getUserStore().isStateProvinceRequired()) { %>
    <td align="right"><b><app:storeMessage key="msbSites.text.state"/></b></td>
    <td><html:text name="SITE_SEARCH_FORM" property="state" />
      &nbsp;&nbsp;&nbsp;&nbsp;
  <%} else {%>
    <td>&nbsp;</td>
    <td>
  <%} %>
  <html:submit property="action" styleClass="store_fb"
      onclick="actionMultiSubmit('BBBBBBB', 'Search To Reorder');" >
      <app:storeMessage key="msbSites.text.searchToReorder"/>
  </html:submit>
    </td>
    </tr>
     <html:hidden property="action" value="BBBBBBB"/>
     <html:hidden property="action" value="CCCCCCC"/>
    </html:form>
    </table>

<logic:present name="msb.site.vector">
<bean:size id="rescount"  name="msb.site.vector"/>

<table width="750" cellspacing=0 cellpadding=0>
<tr align=left>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="msbsites.do?action=sort_sites_reorder&sortField=name"><app:storeMessage key="msbSites.text.siteName"/></a></div></td>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="msbsites.do?action=sort_sites_reorder&sortField=address"><app:storeMessage key="msbSites.text.streetAddress"/></a></div></td>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="msbsites.do?action=sort_sites_reorder&sortField=city"><app:storeMessage key="msbSites.text.city"/></a></div></td>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="msbsites.do?action=sort_sites_reorder&sortField=state"><app:storeMessage key="msbSites.text.stateProvince"/></a></div></td>
<%} %>
</tr>


<logic:iterate id="arrele" name="msb.site.vector" indexId="i">
 <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
<td>
<bean:define id="eleid" name="arrele" property="id"/>
<a href="msbsites.do?action=shop_site_reorder&siteId=<%=eleid%>">
<bean:write name="arrele" property="name"/>
</a>
</td>
<td>
<bean:write name="arrele" property="address"/>
</td>
<td>
<bean:write name="arrele" property="city"/>
</td>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<td>
<bean:write name="arrele" property="state"/>
</td>
<%} %>
</tr>

</logic:iterate>
</table>

</logic:present>
</td>
<td bgcolor="black" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</table>
<!--//////////////////////////////////////////////////////////////////////////////// -->
<bean:define id="checkoutForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>
<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">
<tr>
<td bgcolor="black" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td align="center">
  <table border="0" align="center" cellspacing="0" cellpadding="0" width="90%">
  <tr>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.qty"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.ourSku#"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.productName"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.pack"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.uom"/></div></td>
  </tr>

  <logic:iterate id="item" name="CHECKOUT_FORM" property="items"
   offset="0" indexId="itemsIdx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">
     <bean:define id="orderNumber" name="item" property="orderNumber"/>
     <bean:define id="itemId" name="item" property="product.productId"/>
     <bean:define id="itemIdQtyElement" value="<%=\"itemIdQtyElement[\"+itemsIdx+\"]\"%>"/>

     <% if(checkoutForm.getSortBy()==Constants.ORDER_BY_CATEGORY) { %>
     <% if(checkoutForm.isCategoryChanged(((Integer)itemsIdx).intValue())) { %>
       <tr>
       <td class="shopcategory" colspan="12"><bean:write name="item" property="categoryPath"/></td>
       </tr>
     <% } %>
     <% } %>
     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)itemsIdx)%>">
     <td class="text"><div class="fivemargin">
     <bean:write name="item" property="quantity"/>
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="item" property="actualSkuNum"/>&nbsp;
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="item" property="product.catalogProductShortDesc"/>&nbsp;
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="item" property="product.pack"/>&nbsp;
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="item" property="product.uom"/>&nbsp;
     </div></td>
     </tr>
   </logic:iterate>
  <tr>
  <td colspan="10" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
  </tr>
  </table><!-- Table Level2 end -->
  </td>
  <td bgcolor="black" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  </tr>

  <!-- Table level2 beg -->
  <tr>
  <td bgcolor="black" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  <td align='center'>
  <table border="0" align="center" cellspacing="0" cellpadding="0" width="90%">
    <tr>
    <td class="tableheader"><app:storeMessage key="shop.orderStatus.text.lastOrderBilling:"/></td>
    <td>&nbsp;</td>
    <td class="tableheader"><app:storeMessage key="shop.orderStatus.text.lastOrderShipping:"/></td>
    </tr>
     <tr>
     <td>
     <!-- Table level3 beg -->
     <table border="0" cellspacing="0" cellpadding="0" align="center">
        <tr>
        <td class="smalltext"><div class="fivemargin">
        <b><app:storeMessage key="shop.orderStatus.text.accountName:"/></b>&nbsp;
           <bean:write name="CHECKOUT_FORM" property="accountShortDesc"/><br>
        <b><app:storeMessage key="shop.orderStatus.text.address1:"/></b>&nbsp;
           <bean:write name="CHECKOUT_FORM" property="accountAddress1"/><br>
        <% String address2 = checkoutForm.getAccountAddress2(); %>
        <% if(address2!=null && address2.trim().length()>0) { %>
          <b><app:storeMessage key="shop.orderStatus.text.address2:"/></b>&nbsp;<%=address2%><br>
        <% } %>
        <% String address3 = checkoutForm.getAccountAddress3(); %>
        <% if(address3!=null && address3.trim().length()>0) { %>
          <b><app:storeMessage key="shop.orderStatus.text.address3:"/></b>&nbsp;<%=address3%><br>
        <% } %>
        <% String address4 = checkoutForm.getAccountAddress4(); %>
        <% if(address4!=null && address4.trim().length()>0) { %>
          <b><app:storeMessage key="shop.orderStatus.text.address4:"/></b>&nbsp;<%=address4%><br>
        <% }
         if (appUser.getUserStore().isStateProvinceRequired()) {
        %>
        <b><app:storeMessage key="shop.orderStatus.text.sityStateZip:"/></b>&nbsp;
          <bean:write name="CHECKOUT_FORM" property="accountCity"/>,
          <bean:write name="CHECKOUT_FORM" property="accountStateProvinceCd"/>
          <bean:write name="CHECKOUT_FORM" property="accountPostalCode"/>
      <%} else { %>
        <b><app:storeMessage key="shop.orderStatus.text.sityZip:"/></b>&nbsp;
          <bean:write name="CHECKOUT_FORM" property="accountCity"/>,
          <bean:write name="CHECKOUT_FORM" property="accountPostalCode"/>
      <%}%>
        <br>
        <b><app:storeMessage key="shop.orderStatus.text.country:"/></b>&nbsp;
          <bean:write name="CHECKOUT_FORM" property="accountCountryCd"/>
        </div></td>
        </tr>
     </table><!--Table level3 end -->
     </td>
     <td>&nbsp</td>
     <td>
     <!-- Table level3 beg -->
     <table border="0" cellspacing="0" cellpadding="0" align="center">
        <tr>
        <td class="smalltext"><div class="fivemargin">
        <b><app:storeMessage key="shop.orderStatus.text.name:"/></b>&nbsp;
        <bean:write name="CHECKOUT_FORM" property="siteName1"/>&nbsp;
        <bean:write name="CHECKOUT_FORM" property="siteName2"/>
        <br>
        <b><app:storeMessage key="shop.orderStatus.text.siteName:"/></b>&nbsp;
           <bean:write name="CHECKOUT_FORM" property="siteShortDesc"/><br>
        <b><app:storeMessage key="shop.orderStatus.text.address1:"/></b>&nbsp;
           <bean:write name="CHECKOUT_FORM" property="siteAddress1"/><br>
        <% String siteAddress2 = checkoutForm.getSiteAddress2(); %>
        <% if(siteAddress2!=null && siteAddress2.trim().length()>0) { %>
          <b><app:storeMessage key="shop.orderStatus.text.address2:"/></b>&nbsp;<%=siteAddress2%><br>
        <% } %>
        <% String siteAddress3 = checkoutForm.getSiteAddress3(); %>
        <% if(siteAddress3!=null && siteAddress3.trim().length()>0) { %>
          <b><app:storeMessage key="shop.orderStatus.text.address3:"/></b>&nbsp;<%=siteAddress3%><br>
        <% } %>
        <% String siteAddress4 = checkoutForm.getSiteAddress4(); %>
        <% if(siteAddress4!=null && siteAddress4.trim().length()>0) { %>
          <b><app:storeMessage key="shop.orderStatus.text.address4:"/></b>&nbsp;<%=siteAddress4%><br>
        <% }
         if (appUser.getUserStore().isStateProvinceRequired()) {
        %>
        <b><app:storeMessage key="shop.orderStatus.text.sityStateZip:"/></b>&nbsp;
          <bean:write name="CHECKOUT_FORM" property="siteCity"/>,
          <bean:write name="CHECKOUT_FORM" property="siteStateProvinceCd"/>
          <bean:write name="CHECKOUT_FORM" property="sitePostalCode"/>
      <%} else { %>
        <b><app:storeMessage key="shop.orderStatus.text.sityZip:"/></b>&nbsp;
          <bean:write name="CHECKOUT_FORM" property="accountCity"/>,
          <bean:write name="CHECKOUT_FORM" property="accountPostalCode"/>
      <%}%>
        <br>
        <b><app:storeMessage key="shop.orderStatus.text.country:"/></b>&nbsp;
           <bean:write name="CHECKOUT_FORM" property="siteCountryCd"/>
        </div></td>
        </tr>
     </table><!--Table level3 end -->
     </td>
     </tr>
     <tr>
     <td colspan="3" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
     </tr>
   </table><!--Table level2 end -->
   </td>
 <td bgcolor="black" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
 </tr>
 </table>
<!--////////////////////////////////////////////////////////////////////////////////-->
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>




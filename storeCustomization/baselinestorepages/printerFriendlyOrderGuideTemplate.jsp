
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.Locale" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% String requestNumber =  (String) session.getAttribute(Constants.REQUEST_NUM);%>
<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>


<table align="center" border="0" cellpadding="0" cellspacing="0" width="690">
  <tr>
    <td></td>
    <td>  </td>
    <td></td>
  </tr>

  <!-- content, shopping catalog -->
  <logic:equal name="SHOP_FORM" property="userOrderGuideNumber" value="0">
  <logic:equal name="SHOP_FORM" property="templateOrderGuideNumber" value="0">
  <tr>
    <td></td>
    <td align="center">
    <b>No order guides available</b>
    </td>
    <td></td>
  </tr>
  </logic:equal>
  </logic:equal>
  <!-- Order guide select section -->
  <% if(theForm.getUserOrderGuideNumber()>0 || theForm.getTemplateOrderGuideNumber()>0) {%>
  <tr>
    <td></td>
    <td class="smalltext">
    </td>
    <td></td>
  </tr>
  <% } %>

  <!-- List of items -->
  <logic:greaterThan name="SHOP_FORM" property="orderGuideId" value="0">
  <logic:equal name="SHOP_FORM" property="cartItemsSize" value="0">
  <tr>
    <td></td>
    <td class="text" align="center">
    <b>No Items In The Order Guide</b>
    </td>
    <td></td>
  </logic:equal>
  <logic:greaterThan name="SHOP_FORM" property="cartItemsSize" value="0">

  <%String addToCartUrl1 ="/store/shop.do?action=addToCart&"+Constants.REQUEST_NUM+"="+requestNumber;%>
  <%String addToCartUrl ="/store/shop.do?action=orderGuideInput&"+Constants.REQUEST_NUM+"="+requestNumber;%>
  <tr height=50>
    <td></td>
  </tr>
  <tr>
    <td align="center">
    <table border="0" cellpadding="0" cellspacing="0" width="650" align="CENTER">
      <tr>
        <td align="left" colspan="2">
          <H3><%=theForm.getAppUser().getUserAccount().getBusEntity().getShortDesc()%></H3>
        </td>
      </tr>
      <tr>
        <td align="left" width="40%">
          <img src='/<%=storeDir%>/en/images/cw_logo.gif'>
        </td>
        <td width="10%" rowspan="2">
          &nbsp;
        </td>
        <td rowspan="2" align="right" width="50%">
        <div style="background-color: white; border: solid 1px black; width: 325px; padding: 10px;">
          <table border="0" cellpadding="0" cellspacing="0" width="300" align="CENTER">
            <tr>
              <td align="left">
                <b>Order Online:</b>&nbsp;<%=theForm.getAppUser().getUserStore().getStorePrimaryWebAddress().getValue()%>
              </td>
            </tr>
            <%if (theForm.getAppUser().getUserAccount().getOrderFax().getPhoneNum() != null &&
               theForm.getAppUser().getUserAccount().getOrderFax().getPhoneNum().length() > 0) { %>
            <tr>
              <td align="left">
                <b>Fax Order:</b>&nbsp;<%=theForm.getAppUser().getUserAccount().getOrderFax().getPhoneNum()%>
              </td>
            </tr>
            <% } %>
            <tr>
              <td align="left">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td align="left">
                <b>Contact Us</b>
              </td>
            </tr>
            <%if (theForm.getAppUser().getUserAccount().getOrderPhone().getPhoneNum() != null &&
               theForm.getAppUser().getUserAccount().getOrderPhone().getPhoneNum().length() > 0) { %>
            <tr>
              <td align="left">
                Phone: <%=theForm.getAppUser().getUserAccount().getOrderPhone().getPhoneNum()%> 8:30am-6:30pm EST
              </td>
            </tr>
            <% } %>
            <tr>
              <td align="left">
                <b>Email:</b> <%=theForm.getAppUser().getUserStore().getContactUsEmail().getEmailAddress()%>
              </td>
            </tr>
          </table>
        </div>
        </td>
      </tr>
      <tr>
        <td align="left" width="40%">
          <H3>Order Guide</H3>
        </td>
      </tr>
      <tr>
        <td align="left" colspan="3">
          <%=theForm.getAppUser().getUserAccount().getComments().getValue()%>
        </td>
      </tr>
      <tr height="40">
        <td colspan="3">&nbsp;</td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td align="center">
    <div style="background-color: white; border: solid 1px black; width: 650px">
    <table border="0" cellpadding="0" cellspacing="0" width="620" align="CENTER">
      <tr height=25>
        <td class="text" width="15%" align="left">Date:
        </td>
        <td class="text" width="45%" align="left" style="border-bottom: solid 1px black;">&nbsp;
        </td>
        <td class="text" width="15%" align="left" colspan=2># Of Pages Faxed:
        </td>
        <td class="text" width="25%" align="left" style="border-bottom: solid 1px black;" colspan=3>&nbsp;
        </td>
      </tr>
      <tr height=25>
        <td class="text" align="left" colspan=1>Submitted By:</td>
        <td class="text" colspan=6 width="85%" align="left" style="border-bottom: solid 1px black;">&nbsp;
        </td>
      </tr>
      <tr height=1>
        <td class="text" align="left" colspan=7><b>&nbsp;</b></td>
      </tr>
      <tr height=25>
        <td class="shopcharthead" align="left" colspan=7><b>SHIP TO:</b></td>
      </tr>
      <tr height=25>
        <td class="text" width="15%" align="left">Company Name:
        </td>
        <td class="text" width="45%" align="left" style="border-bottom: solid 1px black;">&nbsp;
        </td>
        <td class="text" width="6%" align="left" colspan=1>Site #:
        </td>
        <td class="text" colspan=4 width="34%" align="left" style="border-bottom: solid 1px black;">&nbsp;
        </td>
      </tr>
      <tr height=25>
        <td class="text" align="left" colspan=1>Address 1:</td>
        <td class="text" colspan=6 width="85%" align="left" style="border-bottom: solid 1px black;">&nbsp;
        </td>
      </tr>
      <tr height=25>
        <td class="text" align="left" colspan=1>Address 2:</td>
        <td class="text" colspan=6 width="85%" align="left" style="border-bottom: solid 1px black;">&nbsp;
        </td>
      </tr>
      <tr height=25>
        <td class="text" width="15%" align="left">City:
        </td>
        <td class="text" width="45%" align="left" style="border-bottom: solid 1px black;">&nbsp;
        </td>
        <td class="text" width="6%" align="left">State:
        </td>
        <td class="text" colspan=1 width="9%" align="left" style="border-bottom: solid 1px black;">&nbsp;
        </td>
        <td class="text" width="11%" align="left">Postal Code:
        </td>
        <td class="text" colspan=2 width="14%" align="left" style="border-bottom: solid 1px black;">&nbsp;
        </td>
      </tr>
      <tr height=25>
        <td class="text" width="15%" align="left">Phone #: 
        </td>
        <td class="text" width="45%" align="left" style="border-bottom: solid 1px black;">(&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)
        </td>
        <td class="text" width="15%" align="left" colspan=2>Order Placed By:
        </td>
        <td class="text" colspan=3 width="25%" align="left" style="border-bottom: solid 1px black;">&nbsp;
        </td>
      </tr>
      <tr height=1>
        <td class="text" align="left" colspan=6><b>&nbsp;</b></td>
      </tr>
    </table>
    </div>
    </td>
  </tr>
  <tr height=25>
    <td></td>
  </tr>
  <tr align="right">
     <td>Sorted By:&nbsp;<b><%switch(theForm.getOrderBy()){
        case Constants.ORDER_BY_CATEGORY:
           out.println("Category");
           break;
        case Constants.ORDER_BY_CUST_SKU:
           out.println("Our Sku #");
           break;
        case Constants.ORDER_BY_NAME:
           out.println("Product Name");
           break;
        default:
           break;
     }%></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  </tr>
  <tr height=25>
    <td></td>
  </tr>
  <tr>
    <td>
    <table width="650" align="CENTER" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
      <!-- page contral bar -->
      <html:form action="<%=addToCartUrl%>">
      <html:hidden name="SHOP_FORM" property="requestNumber" value="<%=(String)requestNumber%>"/>
      <tr>
        <td class="shopcharthead" width=30px ><div class="fivemargin">
        <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
        Qty
        </logic:equal>
        <logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
        &nbsp;
        </logic:equal>
        </div></td>
        <td  class="shopcharthead" align="center" width=35px>Our Sku #</td>
        <td  class="shopcharthead" align="center">Product Name</td>
        <td  class="shopcharthead" align="center">Size</td>
        <td  class="shopcharthead" align="center">Pack</td>
        <td  class="shopcharthead" align="center">UOM</td>
        <td  class="shopcharthead" align="center">Mfg.</td>
        <td  class="shopcharthead" align="center">Mfg.Sku #</td>
        <logic:equal name="SHOP_FORM" property="showPrice" value="true">
        <td  class="shopcharthead" align="center">Price</td>
        <td  class="shopcharthead" align="center">Extended Price</td>
        </logic:equal>
        <logic:equal name="SHOP_FORM" property="showPrice" value="false">
        <td  colspan="2" class="shopcharthead" align="center">&nbsp;</td>
        </logic:equal>
      </tr>
      <!--tr>
        <td  colspan="10" class="tableoutline"></td>  </tr>
      </tr-->
      <bean:define id="offset" name="SHOP_FORM" property="offset"/>
      <bean:define id="pagesize" name="SHOP_FORM" property="pageSize"/>

      <logic:iterate id="item" name="SHOP_FORM" property="cartItems"
       offset="0" indexId="kkk"
       type="com.cleanwise.service.api.value.ShoppingCartItemData">
      <bean:define id="itemId" name="item" property="product.productId"/>
      <bean:define id="quantityEl" value="<%=\"quantityElement[\"+kkk+\"]\"%>"/>
      <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+kkk+\"]\"%>"/>
      <html:hidden name="SHOP_FORM" property="<%=itemIdsEl%>" value="<%=\"\"+itemId%>"/>
      <% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) { %>
      <% if(theForm.isCategoryChanged(((Integer)kkk).intValue())) { %>
      <tr>
        <td  class="shopcategory" colspan = "10"><bean:write name="item" property="categoryPath"/></td>
      </tr>
      <% } %>
      <% } %>
      <tr>
        <td  class="text" align="center" bgcolor="white">
        &nbsp;
        </td>
        <td  class="text" align="center" bgcolor="white">
        <bean:write name="item" property="actualSkuNum"/>&nbsp;
        </td>
        <td  class="text" align="center" bgcolor="white">
        <bean:write name="item" property="product.catalogProductShortDesc"/>&nbsp;
        </td>
        <td  class="text" align="center" bgcolor="white">
        <bean:write name="item" property="product.size"/>&nbsp;
        </td>
        <td  class="text" align="center" bgcolor="white">
        <bean:write name="item" property="product.pack"/>&nbsp;
        </td>
        <td  class="text" align="center" bgcolor="white">
        <bean:write name="item" property="product.uom"/>&nbsp;
        </td>
        <td  class="text" align="center" bgcolor="white">
         <bean:write name="item" property="product.manufacturerName"/>&nbsp;
        </td>
        <td  class="text" align="center" bgcolor="white">
        <bean:write name="item" property="product.manufacturerSku"/>&nbsp;
        </td>
        <logic:equal name="SHOP_FORM" property="showPrice" value="true">
        <td  class="text" bgcolor="white"><div class="fivemargin">
          <bean:define id="price"  name="item" property="price"/>
           <i18n:formatCurrency value="<%=price%>" locale="<%=Locale.US%>"/>
        <logic:equal name="item" property="contractFlag" value="true">
        <logic:equal name="SHOP_FORM" property="showWholeCatalog" value="true">
        *
        </logic:equal>
        </logic:equal>
        </td>
        <td  class="text" align="center" bgcolor="white">
          &nbsp;          
        </td>
        </logic:equal>
        <logic:equal name="SHOP_FORM" property="showPrice" value="false">
        <td  class="text" align="center" bgcolor="white">
        &nbsp;
        </td>
        <td  class="text" align="center" bgcolor="white">
        &nbsp;
        </td>
        </logic:equal>
      </tr>
      </logic:iterate>
      </html:form>
    </table>
    </td>
    <td></td>
  </tr>
  </logic:greaterThan>
  </logic:greaterThan>

</table>







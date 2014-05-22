<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.PairView" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<script language="JavaScript1.2">
<!--
function f_SetChecked(val,prop) {
 dml=document.CHECKOUT_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name==prop && dml.elements[i].disabled!=true) {
     dml.elements[i].checked=val;
   }
 }
}

//-->
</script>

<%
String showSelectBoxParam =  request.getParameter("showSelectBox");
boolean showSelectBox=false;
if( showSelectBoxParam!=null)
 showSelectBox=showSelectBoxParam.equals(Boolean.TRUE);
%>

<table cellpadding="0" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH_m2%>" >

 <tr>

  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="userAssets.text.assetName"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="userAssets.text.assetNumber"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="userAssets.text.assetSerial"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingServices.text.ourServiceName"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingServices.text.baseAmount"/></div></td>

     <%if (showSelectBox) {%>
     <td class="shopcharthead">
         <div class="fivemargin">
             <a href="javascript:f_SetChecked(1,'orderServiceSelectBox')">
                 <app:storeMessage key="shoppingItems.text.checkAll"/>
             </a><br>
             <a href="javascript:f_SetChecked(0,'orderServiceSelectBox')">
                 <app:storeMessage key="shoppingItems.text.clear"/>
             </a>
     </td>
        <%}%>
</tr>

<tr>
    <td colspan="6" height="1"  class="tableoutline"><img src="/images/cw_spacer.gif" height="1"/></td>
</tr>

<bean:define id="cartServ" name="CHECKOUT_FORM" property="services" type="java.util.List"/>

<logic:iterate id="sciD" name="cartServ"  offset="0" indexId="idx"  type="com.cleanwise.service.api.value.ShoppingCartServiceData">
     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)idx)%>" >
     <bean:define id="servId" name="sciD" property="itemData.itemId" />
     <bean:define id="assetId" name="sciD" property="assetData.assetId" />
     <td>
             <logic:present name="sciD" property="assetData">
                 <bean:write name="sciD" property="assetData.shortDesc"/>
             </logic:present>
     </td>
     <td>
             <logic:present name="sciD" property="assetData">
                 <bean:write name="sciD" property="assetData.assetNum"/>
             </logic:present>
     </td>
      <td>
             <logic:present name="sciD" property="assetData">
                 <bean:write name="sciD" property="assetData.serialNum"/>
             </logic:present>
     </td>
      <td><bean:write  name="sciD" property="itemData.shortDesc"  /></td>
      <td>
           <bean:define id="amount"  name="sciD" property="amount"  />
            <%=ClwI18nUtil.getPriceShopping(request, amount, "&nbsp;")%>
         </td>
          <%if (showSelectBox) {%>
         <td>  <html:multibox name="CHECKOUT_FORM" property="orderServiceSelectBox" 
		      value='<%=(servId.toString()+","+assetId.toString())%>'/></td>
          <%}%>
 </tr>
 </logic:iterate>
<tr>
    <td colspan="6" height="1"  class="tableoutline"><img src="/images/cw_spacer.gif" height="1"/></td>
</tr>
</table>





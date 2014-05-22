<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% 
String jspSubmitActionName     = request.getParameter("jspSubmitActionName");
String jspSubmitFormName       = request.getParameter("jspSubmitFormName");
String jspSubmitCollectionName = request.getParameter("jspSubmitCollectionName");

if (jspSubmitActionName == null) {
	jspSubmitActionName = "";
}
if (jspSubmitFormName == null) {
	jspSubmitFormName = "";
}
if (jspSubmitCollectionName == null) {
	jspSubmitCollectionName = "";
}
%>

<bean:define id="theForm" name="STORE_ADMIN_ITEM_FORM" type="com.cleanwise.view.forms.StoreItemMgrForm"/>
<%
boolean linkedItemFl = false;
int licount = 0;
%>
<logic:present name="STORE_ADMIN_ITEM_FORM" property="linkedProductItemsBetweenStores">
    <bean:size id="linkedItemCount"  name="STORE_ADMIN_ITEM_FORM" property="linkedProductItemsBetweenStores"/>
    <logic:greaterThan name="linkedItemCount" value="0">
        <%
        linkedItemFl=true;
        licount = ((java.lang.Integer)linkedItemCount).intValue();
        %>
    </logic:greaterThan>
</logic:present>
<table bgcolor="white" cellpadding="3" cellspacing="0" border="0" width="100%">
    <thead>
        <tr><td>Linked Items</td></tr>
    </thead>
</table>
<table ID=1322 bgcolor="white" cellpadding="3" cellspacing="0" border="1" width="100%">
    <thead>
        <tr>
            <td><b>Store</b></td>
            <td><b>Item Id</b></td>
            <td><b>Item Name</b></td>
            <td><b>Sku</b></td>
            <td><b>UOM</b></td>
            <td><b>Pack</b></td>
            <td><b>Manufacturer</b></td>
            <td><b>Manufacture Sku</b></td>
        </tr>
    </thead>
    <tbody>
    <%
    if (linkedItemFl) {
    %>
    <logic:iterate id="linkedItemBetweenStores" name="STORE_ADMIN_ITEM_FORM" property="linkedProductItemsBetweenStores"
        type="com.cleanwise.service.api.value.StoreProductView" indexId="kkk">
        <tr>
            <td>
                <bean:write name="linkedItemBetweenStores" property="store.busEntity.shortDesc"/>
                <br>
                <br>
                <% 
                String function = "unlinkItem('" + jspSubmitFormName + "','" + jspSubmitActionName + "','" + jspSubmitCollectionName + "'," + linkedItemBetweenStores.getProduct().getProductId() + ");";
                %>
                <html:button styleClass='text' property="action" value ="Break Link" onclick='<%=function%>'/>
            </td>
            <td><bean:write name="linkedItemBetweenStores" property="product.productId"/></td>
            <td><bean:write name="linkedItemBetweenStores" property="product.shortDesc"/></td>
            <td><bean:write name="linkedItemBetweenStores" property="product.skuNum"/></td>
            <td><bean:write name="linkedItemBetweenStores" property="product.uom"/></td>
            <td><bean:write name="linkedItemBetweenStores" property="product.pack"/></td>
            <td><bean:write name="linkedItemBetweenStores" property="product.manufacturerName"/></td>
            <td><bean:write name="linkedItemBetweenStores" property="product.manufacturerSku"/></td>
        </tr>
    </logic:iterate>
    <%
    }
    %>
    </tbody>
</table>
<html:hidden  name="STORE_ADMIN_ITEM_FORM"  property="unlinkedItemIdBetweenStores" value="0"/>


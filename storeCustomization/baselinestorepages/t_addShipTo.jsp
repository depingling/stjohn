<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.forms.*" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="addrForm" name="CUST_ADDRESS_DETAIL_FORM"
  type="CustomerAddressForm"/>
<%
String action = request.getParameter("action");
SiteData sd = ShopTool.getCurrentSite(request);
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
if ( action != null && action.equals("edit") ) {
    if ( null != sd && null != sd.getSiteAddress() ) {
        addrForm.setAddress(sd.getSiteAddress());
    }
} else if ( action != null && action.equals("addShipTo") ) {
    if ( null != sd && null != sd.getSiteAddress() ) {
        AddressData ad = AddressData.createValue();
        addrForm.setAddress(ad);
    }
}
%>

<table class="tbstd" width="<%=Constants.TABLEWIDTH%>">
<tr><td>
<logic:equal name="CUST_ADDRESS_DETAIL_FORM" property="address.addressId"
  value="0">
Please specify shipping information.<br>
</logic:equal>
<logic:greaterThan name="CUST_ADDRESS_DETAIL_FORM" property="address.addressId"
  value="0">
Update shipping information.<br>
</logic:greaterThan>

<font color=red> <html:errors/> </font>
<html:form action="/userportal/addshipto.do">
    <table>

<tr>
<td rowspan=8">&nbsp;&nbsp;&nbsp;</td>
<td>  <b>Address1:</b></td>
    <td>  <html:text name="CUST_ADDRESS_DETAIL_FORM"
          property="address.address1"/>
    </td>
</tr>
<tr><td>  <b>Address2:</b></td>
    <td>  <html:text name="CUST_ADDRESS_DETAIL_FORM"
          property="address.address2"/>
    </td>
</tr>
<tr><td>  <b>Address3:</b></td>
    <td>  <html:text name="CUST_ADDRESS_DETAIL_FORM"
          property="address.address3"/>
    </td>
</tr>

<tr><td> <b>City:</b></td>
    <td> <html:text name="CUST_ADDRESS_DETAIL_FORM"
          property="address.city"/>
    </td>
</tr>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<tr><td> <b>State:</b></td>
    <td> <html:text name="CUST_ADDRESS_DETAIL_FORM" size="2" maxlength="2"
          property="address.stateProvinceCd"/>
    </td>
</tr>
<%} %>
<tr><td> <b>Zip:</b></td>
    <td> <html:text name="CUST_ADDRESS_DETAIL_FORM"
          property="address.postalCode"/>
    </td>
</tr>
<tr><td> <b>Country:</b></td>
    <td>
    <html:select name="CUST_ADDRESS_DETAIL_FORM" property="address.countryCd">
    <html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
    <html:options  collection="country.vector" labelProperty="uiName" property="shortDesc" />
    </html:select>


<logic:equal name="CUST_ADDRESS_DETAIL_FORM" property="address.addressId"
  value="0">
   <html:submit property="action" styleClass="store_fb">
    <app:storeMessage  key="button.addCustomerShipto"/>
   </html:submit>
</logic:equal>
<logic:greaterThan name="CUST_ADDRESS_DETAIL_FORM" property="address.addressId"
  value="0">
   <html:submit property="action" styleClass="store_fb">
    <app:storeMessage  key="button.updateCustomerShipto"/>
   </html:submit>
   <html:submit property="action" styleClass="store_fb">
    <app:storeMessage  key="button.removeCustomerShipto"/>
   </html:submit>
   <html:button styleClass="store_fb" property="action"
  value="Shop for this location"
  onclick="javascript: window.location='../store/shop.do'" />

</logic:greaterThan>
    </td>
</tr>

    </table>
</html:form>

        </td></tr></table>

        <%@ include file="f_table_bottom.jsp" %>

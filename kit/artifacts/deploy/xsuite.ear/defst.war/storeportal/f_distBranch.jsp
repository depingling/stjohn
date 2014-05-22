<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
  int tabIndex = 0;
  Integer tabIndexInteger = (Integer)request.getAttribute("tabIndex");
  if (tabIndexInteger != null) {
	  tabIndex = tabIndexInteger.intValue();
  }
%>

<bean:define id="distId" name="STORE_DIST_DETAIL_FORM" property="id" />
<html:hidden name="STORE_DIST_DETAIL_FORM" property="wrkAddress.BusEntityId" value="<%=distId.toString()%>"/>

<table ID=276 cellspacing="0" border="0" class="mainbody" align=right>
  <tr>
    <td rowspan=8>&nbsp;</td>
    <td><b>Address id: </b></td>
    <td> <bean:write name="STORE_DIST_DETAIL_FORM" property="wrkAddress.addressId"/></td>
  </tr>
  <tr>
    <td><b>Address1: </b></td>
    <td>  <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkAddress.address1" /></td>
  </tr>
  <tr>
    <td><b>Address2: </b></td>
    <td>  <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkAddress.address2" /></td>
  </tr>
  <tr>
    <td><b>Address3: </b></td>
    <td>  <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkAddress.address3" /></td>
  </tr>
  <tr>
    <td><b>City: </b></td>
    <td>  <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkAddress.city" /></td>
  </tr>
  <tr>
    <td><b>Country: </b></td>
    <td>
      <html:select tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkAddress.countryCd">
        <html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
        <html:options  collection="countries.vector" labelProperty="uiName" property="shortDesc" />
      </html:select>
    </td>
  </tr>
  <tr>
    <td><b>State/Province:</b></td>
    <td><html:text tabindex='<%=tabIndex++ + ""%>' maxlength="80" name="STORE_DIST_DETAIL_FORM"  property="wrkAddress.stateProvinceCd" /></td>
  </tr>
  <tr>
    <td><b>Zip/Postal Code:</b></td>
    <td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkAddress.postalCode" maxlength="15" /></td>
  </tr>
  <tr>
    <td><html:submit tabindex='<%=tabIndex++ + ""%>' property="action">Save Branch Address</html:submit></td>
      <bean:define id="thisAddrId" name="STORE_DIST_DETAIL_FORM" property="wrkAddress.addressId"/>
      <% String delAddrHref= "distdet.do?action=Delete Branch Address&branchAddrId=" +thisAddrId; %>
    <td>
      <% if(!thisAddrId.toString().equals("0")) { %>
        <a tabindex='<%=tabIndex++ + ""%>' ID=277 href="<%=delAddrHref%>">[Delete]</a>
      <%} else {%>
        &nbsp;
      <%}%>
    </td>
  </tr>
</table>
<%
	//add the tabIndex to the request so subsequent pages can make use of it
	tabIndexInteger = new Integer(tabIndex);
	request.setAttribute("tabIndex", tabIndexInteger);
%>

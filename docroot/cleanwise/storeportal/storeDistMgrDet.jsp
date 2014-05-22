<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js"
  CHARSET="ISO-8859-1"></SCRIPT>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc + "&amp;submitFl=true";
locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();

return false;
}

//-->
</script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_DIST_DETAIL_FORM" type="com.cleanwise.view.forms.StoreDistMgrDetailForm"/>
<bean:define id="Location" value="dist" type="java.lang.String" toScope="session"/>
<% String action = request.getParameter("action");%>

<link rel="stylesheet" href="../externals/styles.css">

<%
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
StoreData theStore = appUser.getUserStore();
boolean readOnlyFl = true;
boolean disableDistrNumber = readOnlyFl && (theForm.getId().equals("0"));
int tabIndex = 1;
%>


<div class="text">

<table ID=734 cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH%>"  class="mainbody">
<html:form styleId="735" name="STORE_DIST_DETAIL_FORM" scope="session"
        action="storeportal/distdet.do"
        type="com.cleanwise.view.forms.StoreDistMgrDetailForm">

<tr>
<td class="largeheader" colspan="4">&nbsp;</td>
</tr>
<tr>
  <td><b>Distributor&nbsp;Id:</b></td>
  <td>
    <bean:write name="STORE_DIST_DETAIL_FORM" property="id" />
    <html:hidden property="id"/>
  </td>
  <td colspan="2">&nbsp;</td>
</tr>
<tr>
  <td><b>Name:</b></td>
  <td>
    <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="name" size="25" maxlength="30" /><span class="reqind">*</span>
  </td>
  <td><b>Runtime Display Name:</b></td><td>
    <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="runtimeDisplayName"
    size="25" maxlength="30" />
  </td>
</tr>
<tr>
<td><b>Distributor&nbsp;Type:</b></td>
<td>
<html:select tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="typeDesc" >
<html:option value="" ><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Dist.type.vector" property="value" />
</html:select>
</td>
<td><b>Status:</b></td>
<td>
<html:select tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="statusCd" >
<html:option value="" ><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Dist.status.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
  <% if (!disableDistrNumber) { %>
  <td><b>Distributor&nbsp;Number:</b></td>
  <td>
    <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="distNumber"
      size="30" maxlength="30" readonly='<%=readOnlyFl%>' disabled='<%=readOnlyFl%>' />
    </td>
   <% } else { %>
  <% } %>
  <td><b>Call Center Hours:</b></td><td>
       <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="callHours"
       size="30" maxlength="30" />
  </td>
     
</tr>
<tr>
  <td><b>Distributor's Company Code:</b></td>
     <td>
        <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="distributorsCompanyCode"
           size="30" maxlength="30" />
     </td>
  <td><b>Customer Reference Code:</b></td><td>
		<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="customerReferenceCode"
		size="25" maxlength="30" />
	</td>
</tr>
<tr>
	<td><b>Distributor Locale:</b></td>
  	<td>
    	<html:select tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="distLocale">
 				<html:option value='<%=""%>'>Default</html:option><%
				for(java.util.Iterator iter=ClwI18nUtil.getAllLocales().iterator(); iter.hasNext();) {
  					String localeCd = (String) iter.next();
				%>
				    <html:option value="<%=localeCd%>"><%=localeCd%></html:option>
    <% } %>
			</html:select>
    </td>
    <td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr>
<td>
<b>Minimum Order:</b><br> (Monetary Amount)</TD>
<td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM"
property="minimumOrderAmount"
size="5" maxlength="30" /></td>

<td><b>Small Order Handling Fee:</b><br>
(Charge applied to orders smaller than the minimum order amount.)</TD>
<td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM"
  property="smallOrderHandlingFee"
size="5" maxlength="7" />
</td>
</tr>
<tr>
    <td>
		<b>Allowed Freight Surcharge:</b> (Information Only)
	</td>
    <td>
        <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="allowedFreightSurchargeAmount"/>
    </td>
    <td>&nbsp;</td><td>&nbsp;</td>
</tr>
<%--
<tr>
<td colspan="4" class="largeheader"><br>Inventory Exchange Info</td>
</tr>
<tr>
<td><b>Company Code:</b></td>
<td>
<html:text tabindex="5" name="STORE_DIST_DETAIL_FORM" property="exchangeCompanyCode"
   maxlength="30"/></td> --%>
<tr>
<td><b>Exchange URL:</b></td>
<td colspan="3"><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="exchangeInventoryURL"
   maxlength="100" style="width:100%;"/></td>
</tr>
<tr>
<td><b>Exchange User Name:</b></td>
<td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="exchangeUser" maxlength="50"  />
</td>
<td><b>Exchange Password:</b></td>
<td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="exchangePassword" maxlength="50" />
</td>
</tr>


<tr>
<td colspan="4" class="largeheader"><br>Contact and Address</td>
</td>
<tr>
<td><b>First Name:</b></td>
<td>
<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="name1" maxlength="30" />
</td>
<td><b>Phone:</b></td>
<td>

<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="phone" maxlength="40" />
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Last Name:</b></td>
<td>

<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="name2" maxlength="30"  />
</td>
<td><b>Fax:</b></td>
<td>

<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="fax" maxlength="30" />
</td>

</tr>
<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="streetAddr1" maxlength="80" />
<span class="reqind">*</span>
</td>
<td><b>Email:</b></td>
<td>
<html:text tabindex='<%=tabIndex++ + ""%>'  name="STORE_DIST_DETAIL_FORM" property="emailAddress" maxlength="80" />
</td>
</tr>
<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="streetAddr2" maxlength="80" />
</td>
<td><b>Country:</b></td>
<td>
<html:select tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="country">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" labelProperty="uiName" property="shortDesc" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="streetAddr3" maxlength="80" />
</td>

<td><b>State/Province:</b></td>
<td>

<html:text tabindex='<%=tabIndex++ + ""%>' size="20" maxlength="80" name="STORE_DIST_DETAIL_FORM"
  property="stateOrProv" />
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td>
<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="city" maxlength="40" />
<span class="reqind">*</span>
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="postalCode" maxlength="15" />
<span class="reqind">*</span>
</td>

</tr>

<tr>
<td colspan="4" class="largeheader"><br>Billing Address</td>
</td>
<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="billingAddress.address1"
  maxlength="80" />
</td>
<td colspan='2'><b>&nbsp</b></td>
</tr>
<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="billingAddress.address2"
 maxlength="80" />
</td>
<td><b>Country:</b></td>
<td>
<html:select tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="billingAddress.countryCd">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" labelProperty="uiName" property="shortDesc" />
</html:select>


</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="billingAddress.address3"
 maxlength="80" />
</td>

<td><b>State/Province:</b></td>
<td>

<html:text tabindex='<%=tabIndex++ + ""%>' size="20" maxlength="80" name="STORE_DIST_DETAIL_FORM"
  property="billingAddress.stateProvinceCd" />
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td>
<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="billingAddress.city" maxlength="40" />
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="billingAddress.postalCode"
  maxlength="15" />
</td>
</tr>

<tr>
<td><b>Distributor Web Info:</b></TD>
<td colspan=3><html:text tabindex='<%=tabIndex++ + ""%>'
  name="STORE_DIST_DETAIL_FORM" property="webInfo"
  size="60" />
</td>
</tr>

<tr>
<td><b>Distributor CW Account Number(s):</b></TD>
<td colspan=3><html:text tabindex='<%=tabIndex++ + ""%>'
  name="STORE_DIST_DETAIL_FORM" property="accountNumbers"
  size="60" />
</td>
</tr>

<tr>
    <td colspan=4 align=center>
      <html:submit tabindex='<%=tabIndex++ + ""%>' property="action">
        <app:storeMessage  key="global.action.label.save"/>
      </html:submit>
      <html:reset tabindex='<%=tabIndex++ + ""%>'>
        <app:storeMessage  key="admin.button.reset"/>
      </html:reset>
    <logic:notEqual name="STORE_DIST_DETAIL_FORM" property="id" value="0">
      <html:submit tabindex='<%=tabIndex++ + ""%>' property="action">
        <app:storeMessage  key="global.action.label.delete"/>
      </html:submit>
    </logic:notEqual>
    </td>
</tr>

<tr><td>&nbsp;</td></tr>

<%
	//add the tabIndex to the request so subsequent pages can make use of it
	Integer tabIndexInteger = new Integer(tabIndex);
	request.setAttribute("tabIndex", tabIndexInteger);
%>
<tr><td colspan='4'>
<jsp:include flush='true' page="f_distContacts.jsp"/>
</td></tr>

<tr><td colspan='4'>
<jsp:include flush='true' page="f_distBranches.jsp"/>
</td></tr>

<tr>
<td colspan="2" class="largeheader"><b>Served States</b></td>
<td colspan="2" class="largeheader"><b>Served Accounts</b></td>
</tr>
<%
java.util.ArrayList servedStates = theForm.getServedStates();
BusEntityDataVector servedAccounts = theForm.getServedAccounts();
%>
<tr><td valign='top' colspan='2'>
<%
if(servedStates!=null && servedStates.size()>0) {
for(int ii=0; ii<servedStates.size(); ii++) {
  String ss = (String) servedStates.get(ii);
%>
  <%=ss%><br>
<%}%>
<% } else { %>
  None
<%}%>
</td>
<td colspan='2' valign='top'>
<%
if(servedAccounts!=null && servedAccounts.size()>0) {
for(int ii=0; ii<servedAccounts.size(); ii++) {
  BusEntityData beD = (BusEntityData) servedAccounts.get(ii);
%>
  <%=beD.getShortDesc()%><br>
<%}%>
<% } else { %>
  None
<%}%>

</td>
</tr>

</table>

</div>

<jsp:include flush='true' page="f_distShipFromList.jsp" />

</html:form>

</div> <% /* End - Ship from locations. */ %>
<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms["STORE_DIST_DETAIL_FORM"].elements["name"];

  if (focusControl.type != "hidden" && !focusControl.disabled) {
     focusControl.focus();
  }
  // -->
</script>


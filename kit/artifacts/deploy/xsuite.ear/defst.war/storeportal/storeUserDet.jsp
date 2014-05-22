<%@ page language="java" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.forms.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="v_userData" name="STORE_ADMIN_USER_FORM" property="detail.userData"  type="UserData"/>
<bean:define id="userId" name="STORE_ADMIN_USER_FORM" property="detail.userData.userId" type="java.lang.Integer"/>
<bean:define id="userType" name="STORE_ADMIN_USER_FORM" property="detail.userData.userTypeCd" />
<bean:define id="Location" value="user" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="STORE_ADMIN_USER_FORM" type="com.cleanwise.view.forms.StoreUserMgrForm"/>

<script language="JavaScript" src="../externals/calendarNS.js"></script>
<table ID=1409 cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<html:form styleId="1410" name="STORE_ADMIN_USER_FORM" action="storeportal/userdet.do"
scope="session" type="com.cleanwise.view.forms.StoreUserMgrForm">
<tr>
<td colspan=4 class="largeheader">User Detail</td>
</tr>

<tr>
<td><b>User&nbsp;Id:</b></td>
<td>
<bean:write name="STORE_ADMIN_USER_FORM" property="detail.userData.userId" scope="session"/>
<html:hidden property="detail.userData.userId"/>
<html:hidden property="isEditableForUserFl"   />

</td>

<%
if( null == userId || 0 == userId.intValue() ) {
%>
<td><b>Password:</b><span class="reqind">*</span></td>
<td>
<html:password name="STORE_ADMIN_USER_FORM" property="password" maxlength="30" />
</td>
<%  } else {  %>
<td><b>New Password:</b></td>
<td>
<html:password name="STORE_ADMIN_USER_FORM" property="password" maxlength="30" />
</td>
<%  }  %>
</tr>

<tr>
<td><b>User Type:</b><span class="reqind">*</span></td>
<%
if( null == userId || 0 == userId.intValue() ) {
%>
<td>
<html:hidden property="change" value="" />
<html:select name="STORE_ADMIN_USER_FORM" property="detail.userData.userTypeCd" onchange="document.forms[0].change.value='type'; document.forms[0].submit();">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Users.types.vector" property="value" />
</html:select>
</td>
<%  } else {  %>
<td>
<bean:write name="STORE_ADMIN_USER_FORM" property="detail.userData.userTypeCd" filter="true" />
</td>
<%  }  %>

<%
if( null == userId || 0 == userId.intValue() ) {
%>
<td><b>Confirm Password:</b><span class="reqind">*</span></td>
<td>
<html:password name="STORE_ADMIN_USER_FORM" property="confirmPassword" />
</td>
<%  } else {  %>
<td><b>Confirm New Password:</b></td>
<td>
<html:password name="STORE_ADMIN_USER_FORM" property="confirmPassword" />
</td>
<%  }  %>

<td rowspan=3><b>Preferred Language:</b><span class="reqind">*</span>
<html:select name="STORE_ADMIN_USER_FORM" property="detail.languageData.shortDesc">
  <html:option value=""><app:storeMessage  key="admin.select.language"/></html:option>
  <html:options  collection="languages.vector" labelProperty="uiName" property="shortDesc" />
</html:select>
</td>

</tr>
<tr>
<td><b>Login Name:</b><span class="reqind">*</span></td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="detail.userData.userName" maxlength="255" />
</td>
<td><b>Status:</b><span class="reqind">*</span></td>
<td>
<html:select name="STORE_ADMIN_USER_FORM" property="detail.userData.userStatusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Users.status.vector" property="value" />
</html:select>
</td>
</tr>

<tr>
<td><b>User Active Date:</b><span class="reqind">*</span>
</td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="effDate" maxlength="10" />
<a ID=1411 href="javascript:show_calendar('forms[0].effDate');" onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;" title="Choose Date"><img ID=1412 src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
<br><span class="smalltext">(mm/dd/yyyy)</span>
</td>
<td><b>User Inactive Date:</b></td>
<td>
    <html:text name="STORE_ADMIN_USER_FORM" property="expDate" maxlength="10"/>
                <a ID=1413 href="javascript:show_calendar('forms[0].expDate');" onmouseover="window.status='Date Picker';return true;" onmouseout="window.status='';return true;"><img ID=1414 src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
    <br><span class="smalltext">(mm/dd/yyyy)</span>
</td>
</tr>

<tr>
    <td><b>User Code:</b></td>
    <td><html:text name="STORE_ADMIN_USER_FORM" property="userIDCode" size="20" maxlength="255"/></td>

	<td><b>Corporate user</b>
	<html:checkbox name="STORE_ADMIN_USER_FORM" property="isCorporateUser"/></td>

	<td colspan="2"><b>Receive Inventory Missing Email</b>
	<html:checkbox name="STORE_ADMIN_USER_FORM" property="receiveInvMissingEmail"/></td>

</tr>

<% if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userType)) {%>
<tr>
        <td><b>Label Height:</b></td>
        <td><html:text name="STORE_ADMIN_USER_FORM" property="manifestLabelHeight" /></td>
        <td><b>Label Width:</b></td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="manifestLabelWidth" />
</td>
</tr>
<tr>
        <td><b>Label Type:</b></td>
        <td>
<html:select name="STORE_ADMIN_USER_FORM" property="manifestLabelType">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="MANIFEST_LABEL_TYPE_CD" property="value" />
</html:select>
        </td>
        <td><b>Label Print Mode:</b></td>
        <td>
<html:select name="STORE_ADMIN_USER_FORM" property="manifestLabelPrintMode">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="MANIFEST_LABEL_MODE_CD" property="value" />
</html:select>
        </td>
</tr>
<% } %>
<% if ("yes".equals(System.getProperty("multi.store.db"))) { //multiple Database schemas %>
	      <%
	         java.util.List allStoresMainDb = theForm.getAllStoresMainDb().getValues();
	         if(allStoresMainDb.size()>1) {
	             for(int jj=0; jj<allStoresMainDb.size(); jj++) {
	            	AllStoreData allStoreD = (AllStoreData) allStoresMainDb.get(jj);
	                String selectedStr0 = "allStoresMainDb.selected["+jj+"]";
	      %>
	                <tr>
                        <td><%=allStoreD.getStoreName()%></td>
                        <td><html:multibox name="STORE_ADMIN_USER_FORM" property="<%=selectedStr0%>" value="true"/></td>
                    </tr>	
              <% } %>  
          <% } else {  //allStoresMainDb.size() <= 1 %>
                 <html:hidden name="STORE_ADMIN_USER_FORM" property="allStoresMainDb.selected[0]" value="true"/>
          <% }  %>       
<% } else { // One Database schema %>
       <% java.util.List stores = theForm.getStores().getValues();
       if(stores.size()>1) {
          for(int ii=0; ii<stores.size(); ii++) {
             BusEntityData storeD = (BusEntityData) stores.get(ii);
             String selectedStr = "stores.selected["+ii+"]";
%>
    <tr>
    <td><%=storeD.getShortDesc()%></td>
    <td><html:multibox name="STORE_ADMIN_USER_FORM" property="<%=selectedStr%>" value="true"/></td>
    </tr>
       <% } %>
   <% } else {  %>
          <html:hidden name="STORE_ADMIN_USER_FORM" property="stores.selected[0]" value="true"/>
   <% }  %>
<% } %>



<%
 if (RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userType) ||
     RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType)) {
%>

<tr>
<td><b>Customer Service Role:</b></td>
<td>
<html:select name="STORE_ADMIN_USER_FORM" property="customerServiceRoleCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="CustomerService.role.vector" property="value" />
</html:select>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<% }  %>

<%
 if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)) {
%>

<tr>
<td><b>Total Field Readonly:</b></td>
<td><html:checkbox name="STORE_ADMIN_USER_FORM" property="totalReadOnly"/></td>
<td colspan="2">&nbsp;</td>
</tr>

<% }  %>


</table>
<table ID=1415 cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<tr>
<td colspan="4" class="largeheader"><br>Contact Information</td>
</tr>

<tr>

<td colspan="2">

<table ID=1416 border="0" cellpadding="0" cellspacing="1" width="100%">
  <tr>
<td><b>First Name:</b><span class="reqind">*</span></td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="detail.userData.firstName" maxlength="40" size="40" />
</td>
  </tr>
  <tr>
<tr>
<td><b>Last Name:</b><span class="reqind">*</span></td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="detail.userData.lastName" maxlength="40" size="40" />
</td>
  </tr>
  <tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="detail.addressData.address1" maxlength="80" size="40" />
</td>
  </tr>
  <tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="detail.addressData.address2" maxlength="80" size="40" />
</td>
  </tr>
  <tr>
<td><b>City:</b></td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="detail.addressData.city" maxlength="40"/>
</td>
  </tr>
  <tr>
<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80" name="STORE_ADMIN_USER_FORM" property="detail.addressData.stateProvinceCd" />
</td>
  </tr>
  <tr>
<td><b>Zip/Postal Code:</b></td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="detail.addressData.postalCode" maxlength="15" />
</td>
  </tr>
  <tr>
<td><b>Country:</b><span class="reqind">*</span></td>
<td>
<html:select name="STORE_ADMIN_USER_FORM" property="detail.countryData.countryCode">
  <html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
  <html:options  collection="countries.vector" labelProperty="uiName" property="countryCode" />
</html:select>
</td>
  </tr>
</table>
</td>

<td colspan="2" valign="top">
  <table ID=1417 border="0" cellpadding="0" cellspacing="1" width="100%">
    <tr>
<td><b>Phone:</b></td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="detail.phone.phoneNum" maxlength="30" />
</td>
</tr>
<tr>
<td><b>Fax:</b></td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="detail.fax.phoneNum" maxlength="30" />
</td>
</tr>
<tr>
<td><b>Mobile:</b></td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="detail.mobile.phoneNum" maxlength="30"/>
</td>
</tr>
<tr>
<td><b>Email:</b></td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="detail.emailData.emailAddress" maxlength="255" size="40" />
</td>
</tr>
</table>
</td>
</tr>


  <!-- Default User Rights -->
<tr>
<td colspan="4" align="center">
    <b>Default User Rights</b>
</td>
</tr>
<tr>
<td colspan="4" align="center">
    <% {
       Integer key = new Integer(0);
    %>
       <%@ include file="storeUserRightsConfig.jsp" %>
    <% } %>
</td>
</tr>
<tr>
<td colspan="4" align="center">
<html:submit property="action">
<app:storeMessage  key="admin.button.saveUserDetail"/>
</html:submit>
<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>&nbsp;
</td>
</tr>

</table>

</html:form>
<script type="text/javascript">
  var editable = 'true';
  var forms = document.forms;
  for (var i=0;i<forms.length;i++) {
    var form = forms[i];
    var elts = form.elements;
//    alert("elements=" + elts.length);
    for (var j=0;j<elts.length;j++) {
      var el = elts[j];
      if (el.name == "isEditableForUserFl"){
        editable=el.value;
      }
    }
//    alert ( "editable=" +editable);
    if (editable != 'true'){
      for (var j=0;j<elts.length;j++) {
        var el = elts[j];
        el.disabled = true;
      }
    }
  }


</script>

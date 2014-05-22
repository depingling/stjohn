<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<script language="JavaScript1.2">
 
function toggleSelect(id1, id2){

   if (document.getElementById(id2).checked == true){
       document.getElementById(id1).checked = true;
   }

}

</script>

<app:checkLogon/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>My Profile Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<jsp:include  flush ='true' page="storeStoreCtx.jsp" />
<html:form styleId="storeProfileForm" name="STORE_STORE_DETAIL_FORM" action="/storeportal/storeStoreMyProfile.do"
  scope="session" type="com.cleanwise.view.forms.StoreStoreMgrDetailForm">

<div class="text">
 
<table ID="profileTable" width="769" class="results" border="0">

 <tr>
   <td>
       <b>My Profile Fields</b>
  </td>
   <td>
       <b>Display</b>
  </td>
   <td>
       <b>Editable</b>
  </td>
 </tr>
 
 <tr>
  <td>Profile Name</td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.profileNameDisplay" styleId="pNameD" onclick="toggleSelect('pNameD', 'pNameE')"/>
  </td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.profileNameEdit" styleId="pNameE" onclick="toggleSelect('pNameD', 'pNameE')"/>
  </td>
</tr>
 
 <tr>
  <td>Language</td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.languageDisplay" styleId="langD" onclick="toggleSelect('langD', 'langE')"/>
  </td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.languageEdit" styleId="langE" onclick="toggleSelect('langD', 'langE')"/>
  </td>
</tr>
 
 <tr>
  <td>Country</td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.countryDisplay" styleId="countryD" onclick="toggleSelect('countryD', 'countryE')"/>
  </td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.countryEdit" styleId="countryE" onclick="toggleSelect('countryD', 'countryE')"/>
  </td>
</tr>
 
 <tr>
  <td>Contact Address</td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.contactAddressDisplay"  styleId="addD" onclick="toggleSelect('addD', 'addE')"/>
  </td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.contactAddressEdit" styleId="addE" onclick="toggleSelect('addD', 'addE')"/>
  </td>
</tr>
 
 <tr>
  <td>Phone</td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.phoneDisplay" styleId="phoneD" onclick="toggleSelect('phoneD', 'phoneE')"/>
  </td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.phoneEdit" styleId="phoneE" onclick="toggleSelect('phoneD', 'phoneE')"/>
  </td>
</tr>
 
 <tr>
  <td>Mobile</td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.mobileDisplay" styleId="mobD" onclick="toggleSelect('mobD', 'mobE')"/>
  </td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.mobileEdit" styleId="mobE" onclick="toggleSelect('mobD', 'mobE')"/>
  </td>
</tr>
 
 <tr>
  <td>Fax</td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.faxDisplay" styleId="faxD" onclick="toggleSelect('faxD', 'faxE')"/>
  </td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.faxEdit" styleId="faxE" onclick="toggleSelect('faxD', 'faxE')"/>
  </td>
</tr>
 
 <tr>
  <td>Email</td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.emailDisplay" styleId="emailD" onclick="toggleSelect('emailD', 'emailE')"/>
  </td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.emailEdit" styleId="emailE" onclick="toggleSelect('emailD', 'emailE')"/>
  </td>
</tr>
 
 <tr>
  <td>Change Password</td>
  <td>
    <html:checkbox name="STORE_STORE_DETAIL_FORM" property="storeProfile.changePassword" styleId="pwd"/>
  </td>
</tr>
 
 <tr>
   <td>
       <b>Languages</b>
  </td>
  
  <td>
      <html:select name="STORE_STORE_DETAIL_FORM" property="storeProfile.storeLanguages" 
         multiple="true" size="10">
      <html:option value=""><app:storeMessage  key="admin.none"/></html:option>
      <html:options collection="store.languages.options" labelProperty="label" property="value"/>
      </html:select>
  </td>
 </tr>
 
 
 
 <tr>
  <td colspan=4 align=center>
     
     <input type="submit" value="Save"/></td>
  </td>
 </tr> 

</table>
 <html:hidden  property="action" value="saveStoreProfile"/>
</div>

</html:form>
</body>
</html:html>






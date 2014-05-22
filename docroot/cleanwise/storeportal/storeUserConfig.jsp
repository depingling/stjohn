<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.dao.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.*" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
 <!--
function sortSubmit(val) {
 var actions;
 actions=document.all["action"];
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='BBBBBBB') {
     actions[ii].value="sort";
     break;
   }
 }
 sortField=document.all["sortField"];
 sortField.value = val;

 document.forms[1].submit();
 return false;
 }

  function f_check_search()
  {
  var x=document.getElementsByName("searchType");
  for (var i = 0; i < x.length ; i++)
  {
  if (x[i].value == "nameBegins")
  {
  x[i].checked = true;
  }
  }                   
  }

  function f_check_boxes()
  {
  f_set_vals(1);
  }

  function f_uncheck_boxes()
  {
  f_set_vals(0);
  }

  function f_set_vals(pVal)
  {
  var x=document.getElementsByName("confSelectIds");
  //alert('x.length='+x.length + ' pVal=' + pVal);
  for (var i = 0; i < x.length ; i++)
  {
  if (x[i].name == "confSelectIds")
  {
  if ( pVal == 1 )
  {
  x[i].checked = true;
  }
  else
  {
  x[i].checked = false;
  }
  }
  }

  }
  function submitForm() {
  document.forms[0].submit();
  }
  -->
</script>

<style>
  .cfg { 
  border-top: solid 1px black;
  border-right: solid 1px black;
  background-color: #cccccc;
  font-weight: bold;
  text-align: center;

  }
  .cfg_on { 
  border-top: solid 1px black;
  border-right: solid 1px black;
  background-color: white;
  font-weight: bold;
  text-align: center;
  }

</style>

<bean:define id="theForm" name="STORE_ADMIN_USER_FORM" type="com.cleanwise.view.forms.StoreUserMgrForm"/>
<%
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
  String appUserType = appUser.getUser().getUserTypeCd();
  UserData v_userData = theForm.getDetail().getUserData();
  String userTypeCd = v_userData.getUserTypeCd();
  String confFunc = theForm.getConfFunction();
%>
<table ID=1396 border="0" cellpadding="0" cellspacing="0" width="769" bgcolor="#cccccc">
  <tr><td><b>User ID:</b></td>
    <td><bean:write name="STORE_ADMIN_USER_FORM" property="detail.userData.userId" filter="true"/>
    </td>
    <td> <b>User Name:</b> </td>
    <td> <bean:write name="STORE_ADMIN_USER_FORM" property="detail.userData.userName" filter="true"/>
    </td>
    <td><b>Mod By:</b></td>
    <td><bean:write name="STORE_ADMIN_USER_FORM" property="detail.userData.modBy"/></td>
  </tr>
  <tr>
    <td><b>User Type:</b></td>
    <td><bean:write name="STORE_ADMIN_USER_FORM" property="detail.userData.userTypeCd" filter="true"/></td>
    <td><b>User Status:</b></td>
    <td><bean:write name="STORE_ADMIN_USER_FORM" property="detail.userData.userStatusCd" filter="true"/>
    </td>
    <td><b>Mod Date:</b></td>
    <td><bean:write name="STORE_ADMIN_USER_FORM" property="detail.userData.modDate"/>
    </td>
  </tr>  
</table>

<table ID=1397>
  <html:form styleId="1398" action="/storeportal/userconfig.do">
    <html:hidden property="currentPage" value="config"/>                
    <tr> <td><b>Configure</b></td>
      <td>
       <% if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd)) {%>
          <b>Groups:</b>
       <% }else{ %>     
        <html:select name="STORE_ADMIN_USER_FORM" property="confFunction" onchange="submitForm()">
          <% if(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userTypeCd)) { %>
          <html:option value="distConfig">Distributors</html:option>
          <% } else if (RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER.equals(userTypeCd)) { %>
          <html:option value="spConfig">Service Providers</html:option>
          <html:option value="groupConfig">Groups</html:option>                    
          <% } else { %>
          <html:option value="acctConfig">Accounts</html:option>
          <html:option value="siteConfig">Sites</html:option>
          <html:option value="permConfig">Permissions</html:option>
          <% if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(appUserType) ||
                RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUserType) ||
                RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(appUserType)) {
          %>
          <html:option value="groupConfig">Groups</html:option>
          <% } %>
          <html:option value="catalogConfig">Catalogs (Info)</html:option>
          <html:option value="ogConfig">Order Guides (Info)</html:option>
          <% } %>
        </html:select>
       <% } %>
      </td>
      <td>
    </tr>
  </html:form>
</table>

<table ID=1399 class="results"><tr><td>

  <% 
    String f1sub = "/storeportal/userconfig.do?userid=" +
            v_userData.getUserId() + "&tab=" + confFunc;

    String f2sub = "/storeportal/userconfig.do?goto=userconfig&userid=" +
            v_userData.getUserId() + "&tab=" + confFunc;
  %>


  <% if (confFunc.equals("acctConfig")) { %>
       <%@ include file="storeUserAcctConfig.jsp" %>
  <% } %>

  <% if (confFunc.equals("siteConfig")) { %>
       <%@ include file="storeUserSiteConfig.jsp" %>
  <% } %>

<% if (confFunc.equals("permConfig")) { %>
       <%@ include file="storeUserPermConfig.jsp"%>
<% } %>

<% if (confFunc.equals("groupConfig")) { %>
       <%@ include file="storeUserGroupConfig.jsp" %>
<% } %>
<% if (confFunc.equals("distConfig")) { %>
       <%@ include file="storeUserDistConfig.jsp" %>
<% } %>
<% if (confFunc.equals("spConfig")) { %>
       <%@ include file="storeUserServiceProviderConfig.jsp" %>
<% } %>
<% if (confFunc.equals("catalogConfig")) { %>
       <%@ include file="storeUserCatalogConfig.jsp" %>
<% } %>
<% if (confFunc.equals("ogConfig")) { %>
<div id="distConfig">
    <html:form styleId="1400" name="STORE_ADMIN_USER_FORM"  action="<%=f1sub%>" scope="session" 
                           type="com.cleanwise.view.forms.StoreUserMgrForm"> 
    <html:hidden property="ogconfig" value="true"/>
      <table ID=1401 width="769" class="results">
        <tr>
         <td>&nbsp;</td>
         <td> 
          <html:text name="STORE_ADMIN_USER_FORM" property="confSearchField" 
          onfocus="javascript:f_check_search();" />
          <html:radio name="STORE_ADMIN_USER_FORM" property="confSearchType" 
          value="id" />  ID
          <html:radio name="STORE_ADMIN_USER_FORM" property="confSearchType" 
          value="nameBegins" />  Name(starts with)
          <html:radio name="STORE_ADMIN_USER_FORM" property="confSearchType" 
          value="nameContains" />  Name(contains)
        </td>
        </tr>
        <tr> <td>&nbsp;</td>
          <td>
            <html:submit property="action">
              <app:storeMessage  key="global.action.label.search"/>
            </html:submit>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This Page Shows Only Related Order Guides:
        </tr>

      </table>

      
      <logic:present name="user.og.vector">
      <bean:size id="rescount"  name="user.og.vector"/>
      Search result count:  <bean:write name="rescount" />
      <logic:greaterThan name="rescount" value="0">
    <table ID=1402 width="769"  class="results">
      <tr align=left>
      <td><a ID=1403 class="tableheader" href="#pgsort" onclick="sortSubmit('id');">Order Guide Id</td>
      <td><a ID=1404 class="tableheader" href="#pgsort" onclick="sortSubmit('name');">Order Guide Name</td>
      <td><a ID=1405 class="tableheader" href="#pgsort" onclick="sortSubmit('id');">Oder Guide Type</td>
      <td><a ID=1406 class="tableheader" href="#pgsort" onclick="sortSubmit('catalogId');">Catalog id</td>
      <td><a ID=1407 class="tableheader" href="#pgsort" onclick="sortSubmit('catalog');">Catalog Name</td>
      <td><a ID=1408 class="tableheader" href="#pgsort" onclick="sortSubmit('status');">Catalog Status</td>
      </tr>
     <logic:iterate id="arrele" name="user.og.vector">
     <tr>
     <td><bean:write name="arrele" property="orderGuideId"/></td>
     <td><bean:write name="arrele" property="orderGuideName"/></td>
      <td><bean:write name="arrele" property="orderGuideTypeCd"/></td>

     <td><bean:write name="arrele" property="catalogId"/></td>
     <td><bean:write name="arrele" property="catalogName"/></td>
      <td><bean:write name="arrele" property="status"/></td>
       </tr>
       </logic:iterate>
      </table>
      </logic:greaterThan>
      </logic:present>
  
  <html:hidden  property="action" value="BBBBBBB"/>
  <html:hidden  property="sortField" value="BBBBBBB"/>

</html:form>
</div>

<% } %>








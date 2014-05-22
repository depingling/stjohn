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

<bean:define id="theForm" name="STORE_SVCPROV_DETAIL_FORM" type="com.cleanwise.view.forms.StoreServiceProviderMgrDetailForm"/>
<%
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
  String appUserType = appUser.getUser().getUserTypeCd();
  UserData v_userData = null; //theForm.getDetail().getUserData();
  ServiceProviderData v_sProvData = theForm.getDetail();
  String userTypeCd = null; //v_userData.getUserTypeCd();
  //String sProviderTypeId = v_sProvData.getServiceProviderTypeId();
  String confFunc = theForm.getConfFunction();
%>
<table border="0" cellpadding="0" cellspacing="0" width="769" bgcolor="#cccccc">
  <tr><td><b>Service Provider ID:</b></td>
    <td><bean:write name="STORE_SVCPROV_DETAIL_FORM" property="detail.busEntity.busEntityId" filter="true"/>
    </td>
    <td> <b>Service Provider Name:</b> </td>
    <td> <bean:write name="STORE_SVCPROV_DETAIL_FORM" property="detail.busEntity.shortDesc" filter="true"/>
    </td>
    <td><b>Mod By:</b></td>
    <td><bean:write name="STORE_SVCPROV_DETAIL_FORM" property="detail.busEntity.modBy"/></td>
  </tr>
  <tr>
    <td><b>Service Provider Type:</b></td>
    <td>&nbsp; <% /*bean:write name="STORE_SVCPROV_DETAIL_FORM" property="serviceProviderType" filter="true"/  */%></td>
    <td><b>Service Provider Status:</b></td>
    <td><bean:write name="STORE_SVCPROV_DETAIL_FORM" property="detail.busEntity.busEntityStatusCd" filter="true"/>
    </td>
    <td><b>Mod Date:</b></td>
    <td><bean:write name="STORE_SVCPROV_DETAIL_FORM" property="detail.busEntity.modDate"/>
    </td>
  </tr>  
</table>

<table>
  <html:form action="/storeportal/sprconfig.do">
    <html:hidden property="currentPage" value="config"/>                
    <tr> <td><b>Configure</b></td>
      <td> 
        <html:select name="STORE_SVCPROV_DETAIL_FORM" property="confFunction" onchange="submitForm()">
          <html:option value="acctConfig">Accounts</html:option>
          <html:option value="siteConfig">Sites</html:option>
        </html:select>
      </td>
      <td>
    </tr>
  </html:form>
</table>

<table class="results"><tr><td>

  <% 
    String f1sub = "/storeportal/sprconfig.do?sprid=" +
        v_sProvData.getBusEntity().getBusEntityId() + 
		"&tab=" + confFunc;

    String f2sub = "/storeportal/sprconfig.do?goto=sprconfig&sprid=" +
        v_sProvData.getBusEntity().getBusEntityId() + 
		"&tab=" + confFunc;
  %>


  <% if (confFunc.equals("acctConfig")) { %>
       <%@ include file="storeServiceProviderAcctConfig.jsp" %>
  <% } %>
  
  <% if (confFunc.equals("siteConfig")) { %>
       <%@ include file="storeServiceProviderSiteConfig.jsp" %>
  <% } %>

<% if (confFunc.equals("ogConfig")) { %>
<div id="distConfig">
    <html:form name="STORE_SVCPROV_DETAIL_FORM"  action="<%=f1sub%>" scope="session" 
                           type="com.cleanwise.view.forms.StoreServiceProviderMgrDetailForm"> 
    <html:hidden property="ogconfig" value="true"/>
      <table width="769" class="results">
        <tr>
         <td>&nbsp;</td>
         <td> 
          <html:text name="STORE_SVCPROV_DETAIL_FORM" property="confSearchField" 
          onfocus="javascript:f_check_search();" />
          <html:radio name="STORE_SVCPROV_DETAIL_FORM" property="confSearchType" 
          value="id" />  ID
          <html:radio name="STORE_SVCPROV_DETAIL_FORM" property="confSearchType" 
          value="nameBegins" />  Name(starts with)
          <html:radio name="STORE_SVCPROV_DETAIL_FORM" property="confSearchType" 
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
    <table width="769"  class="results">
      <tr align=left>
      <td><a class="tableheader" href="#pgsort" onclick="sortSubmit('id');">Order Guide Id</td>
      <td><a class="tableheader" href="#pgsort" onclick="sortSubmit('name');">Order Guide Name</td>
      <td><a class="tableheader" href="#pgsort" onclick="sortSubmit('id');">Oder Guide Type</td>
      <td><a class="tableheader" href="#pgsort" onclick="sortSubmit('catalogId');">Catalog id</td>
      <td><a class="tableheader" href="#pgsort" onclick="sortSubmit('catalog');">Catalog Name</td>
      <td><a class="tableheader" href="#pgsort" onclick="sortSubmit('status');">Catalog Status</td>
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








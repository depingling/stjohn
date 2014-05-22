<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.forms.StoreSiteMgrForm" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<% 
  StoreSiteMgrForm theForm = (StoreSiteMgrForm) session.getAttribute("STORE_ADMIN_SITE_FORM"); 
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
%>
<bean:define id="siteid" name="STORE_ADMIN_SITE_FORM" property="id"/>

<html:html>
<body>


<div class="text">
<table ID=1195 width="<%=Constants.TABLEWIDTH%>"  class="mainbody">

<tr>
<td><b>Account&nbsp;Id:</b></td>
<td>  <bean:write name="STORE_ADMIN_SITE_FORM" property="accountId" scope="session"/></td>
<td><b>Account&nbsp;Name:</b></td>
<td><bean:write name="STORE_ADMIN_SITE_FORM" property="accountName" scope="session"/></td>
</tr>
<tr>
<td><b>Site&nbsp;Id:</b> </td>
<td><bean:write name="STORE_ADMIN_SITE_FORM" property="id" scope="session"/></td>
<td><b>Site&nbsp;Name:</b> </td>
<td><bean:write name="STORE_ADMIN_SITE_FORM" property="name" scope="session"/></td>
</tr>
</table>

<html:form styleId="1196" name="STORE_ADMIN_SITE_FORM" action="storeportal/siteconfig.do"
 scope="session" type="com.cleanwise.view.forms.StoreSiteMgrForm">

<table ID=1197>
  <tr> <td><b>Find</b></td>
       <td> 
          <html:select name="STORE_ADMIN_SITE_FORM" property="configType" onchange="setConfiguredOnly()">
             <html:option value="Catalog">Catalog</html:option>
             <html:option value="Users">Users</html:option>
             <html:option value="Distributor Schedule">Distributor Schedule</html:option>
             <html:option value="Corporate Schedule">Corporate Schedule</html:option>
          </html:select>
       </td>
       <td colspan="2"> 
	  <html:text name="STORE_ADMIN_SITE_FORM" property="configSearchField"/>	
       </td>
  </tr>
  
  <tr> <td colspan="2">&nbsp;</td>
       <td colspan="3">
         <html:radio name="STORE_ADMIN_SITE_FORM" property="configSearchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="STORE_ADMIN_SITE_FORM" property="configSearchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>  
    
  <tr> <td colspan="2">&nbsp;</td>
       <td colspan="3">
        <html:hidden property="fig" value="true"/>
	<html:submit property="action"><app:storeMessage  key="global.action.label.search"/></html:submit><div id='configuredOnlyDiv'>
        Show Configured Only <html:checkbox property="configuredOnly" value="true" />
        </div>

      <html:hidden property="siteconfig" value="true"/>
    		
      
     </td>
  </tr>

  <tr><td colspan="4">&nbsp;</td>
  </tr>
  
</table>


<logic:present name="site.users.vector">
<bean:size id="rescount"  name="site.users.vector"/>
Search result count:  <bean:write name="rescount" />
<table ID=1198  width="<%=Constants.TABLEWIDTH%>"  ><tr align="right"><td><a ID=1199 href="javascript:SetCheckedUser(1)">[Check&nbsp;All]</a><br>
        <a ID=1200 href="javascript:SetCheckedUser(0)">[&nbsp;Clear]</a></td></tr></table>
<logic:greaterThan name="rescount" value="0">

<table width="<%=Constants.TABLEWIDTH%>"  class="stpTable_sortable" id="ts1">
<thead>
    <tr align=left>
        <th class="stpTH">User Id</th>
        <th class="stpTH">Login Name</th>
        <th class="stpTH">First Name</th>
        <th class="stpTH">Last Name</th>
        <th class="stpTH">User Type</th>
        <th class="stpTH">Status</th>
        <th class="stpTH">Select</th>
    </tr>
</thead>
<logic:iterate id="arrele" name="site.users.vector">
<tr>
<td><bean:write name="arrele" property="userId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="userId"/>
<bean:define id="eletype" name="arrele" property="userTypeCd"/>
<% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_USER)) {%>
<a ID=1201 href="userdet.do?action=userdetail&userId=<%=eleid%>&type=<%=eletype%>">
<bean:write name="arrele" property="userName"/>
</a>
<% } else {%>
<bean:write name="arrele" property="userName"/>
<% } %>
</td>
<td>
<bean:write name="arrele" property="firstName"/>
</td>
<td>
<bean:write name="arrele" property="lastName"/>
</td>
<td>
<bean:write name="arrele" property="userTypeCd"/>
</td>
<td><bean:write name="arrele" property="userStatusCd"/></td>
<td><html:multibox name="STORE_ADMIN_SITE_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
<html:hidden name="STORE_ADMIN_SITE_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />
</tr>
</logic:iterate>
</table>
<table ID=1202>
<tr>
<td>
<html:submit property="action"><app:storeMessage  key="global.action.label.save"/></html:submit>
<html:hidden property="siteconfig" value="true"/>
</td>
</tr>
</table>


</logic:greaterThan>
</logic:present>

<logic:present name="site.catalogs.vector">
<bean:size id="rescount"  name="site.catalogs.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="<%=Constants.TABLEWIDTH%>"   class="stpTable_sortable" id="ts2">
<thead>
    <tr align=left>
        <th class="stpTH">Catalog Id</th>
        <th class="stpTH">Name</th>
        <th class="stpTH">Status</th>
        <th class="stpTH">Type</th>
        <th class="stpTH">Select</th>
    </tr>
</thead>

<logic:iterate id="arrele" name="site.catalogs.vector">
<tr>
<td><bean:write name="arrele" property="catalogId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="catalogId"/>
<a ID=1203 href="storecatalogdet.do?action=edit&id=<%=eleid%>">
<bean:write name="arrele" property="shortDesc"/>
</a>
</td>
<td><bean:write name="arrele" property="catalogStatusCd"/></td>
<td><bean:write name="arrele" property="catalogTypeCd"/></td>
<td>
  <html:radio name="STORE_ADMIN_SITE_FORM" property="catalogId" value="<%=String.valueOf(eleid)%>"/>
</td>
</tr>

</logic:iterate>
</table>
<table ID=1204>
<tr>
<td>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
</td>
</tr>
</table>
</logic:greaterThan>
</logic:present>


<logic:present name="Related.site.distschedules.vector">
<bean:size id="dsres"  name="Related.site.distschedules.vector"/>
Search result count:  <bean:write name="dsres" />
<logic:greaterThan name="dsres" value="0">
<table width="<%=Constants.TABLEWIDTH%>"  class="stpTable_sortable" id="ts3">
<thead>
    <tr>
        <th class="stpTH">Schedule Id</th>
        <th class="stpTH">Distributor Name</th>
        <th class="stpTH">Dist. Erp#</th>
        <th class="stpTH">Schedule Name</th>
        <th class="stpTH">Status</th>
        <th class="stpTH">Info</th>
        <th class="stpTH">Cutoff</th>
        <th class="stpTH">Next Delivery</th>
    </tr>
</thead>
<logic:iterate id="arrele" name="Related.site.distschedules.vector"
  type="com.cleanwise.service.api.value.DeliveryScheduleView"
  indexId="idx">
  <bean:define id="eleid" name="arrele" property="scheduleId"/>
    <td><bean:write name="arrele" property="scheduleId"/></td>
    <td><bean:write name="arrele" property="busEntityShortDesc"/></td>
    <td><bean:write name="arrele" property="busEntityErpNum"/></td>
    <td><bean:write name="arrele" property="scheduleName"/></td>
    <td><bean:write name="arrele" property="scheduleStatus"/></td>
    <td><bean:write name="arrele" property="scheduleInfo"/></td>
    <td><bean:write name="arrele" property="cutoffInfo"/></td>
    <% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEE, MM/dd/yyyy");
     String nextDel;
     if(arrele.getNextDelivery() == null){
       nextDel = "Error";
     }else{
       nextDel = sdf.format(arrele.getNextDelivery());
     }
    %>
    <td><%=nextDel%></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>

<logic:present name="Related.site.corpschedules.vector">
<bean:size id="dsres"  name="Related.site.corpschedules.vector"/>
Search result count:  <bean:write name="dsres" />
<logic:greaterThan name="dsres" value="0">
<table width="<%=Constants.TABLEWIDTH%>"  class="stpTable_sortable" id="ts3">
<thead>
    <tr>
        <th class="stpTH">Schedule Id</th>
        <th class="stpTH">Corporate Schedule Name</th>
        <th class="stpTH">Status</th>
        <th class="stpTH">Info</th>
        <th class="stpTH">Cutoff Time</th>
        <th class="stpTH">Next Date</th>
    </tr>
</thead>
<logic:iterate id="arrele" name="Related.site.corpschedules.vector"
  type="com.cleanwise.service.api.value.DeliveryScheduleView"
  indexId="idx">
  <bean:define id="eleid" name="arrele" property="scheduleId"/>
    <td><bean:write name="arrele" property="scheduleId"/></td>
    <td><bean:write name="arrele" property="scheduleName"/></td>
    <td><bean:write name="arrele" property="scheduleStatus"/></td>
    <td><bean:write name="arrele" property="scheduleInfo"/></td>
    <td><bean:write name="arrele" property="cutoffInfo"/></td>
    <% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
     String nextDel;
     if(arrele.getNextDelivery() == null){
       nextDel = "&nbsp;";
     }else{
       nextDel = sdf.format(arrele.getNextDelivery());
     }
    %>
    <td><%=nextDel%></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>
</div>




<html:hidden property='action' value='BBBBBB'/> 
</html:form>


</body>

</html:html>
<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms[0]['configSearchField'];
  if('undefined' != typeof focusControl) {
     focusControl.focus();
  }

  function setConfiguredOnly(){
  var configTypeObj = document.forms[0]['configType'];
  if('undefined' != typeof configTypeObj) {
    for(ii=0; ii<configTypeObj.length; ii++) {
      if(configTypeObj[ii].selected) {
        var configuredOnlyObj = document.all['configuredOnlyDiv'];
        if('undefined' != typeof configuredOnlyObj) {
          if(configTypeObj[ii].value=='Distributor Schedule') {
            configuredOnlyObj.style.display = 'none';
          } else {
            configuredOnlyObj.style.display = 'block';
          }
        }
      }
    }  
  }
}

function SetCheckedUser(val) {
var el=document.forms['STORE_ADMIN_SITE_FORM']['selectIds'];
if('undefined' != typeof el) {
var ellen = el.length;
  for(j=0; j<ellen; j++) {
      el[j].checked=val;

    }
}
 }

function kH(e) {
  var keyCode = window.event.keyCode;
  if(keyCode==13) {
    var actionButton = document.forms[0]['action'];
    if('undefined' != typeof actionButton) {
      var len = actionButton.length;
      for(ii=0; ii<len; ii++) {
        if('BBBBBB' == actionButton[ii].value) {        
          actionButton[ii].value='Search';
          document.forms[0].submit();
          break;
        }      
      }
    }
  }
}
document.onkeypress = kH;

setConfiguredOnly();
  // -->
</script>
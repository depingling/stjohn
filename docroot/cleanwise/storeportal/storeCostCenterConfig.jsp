<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.forms.StoreCostCenterMgrForm" %>

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

 document.forms[0].submit();
 return false;
 }
 
 function f_check_boxes(){
   f_set_vals(1);
 }

 function f_uncheck_boxes(){
   f_set_vals(0);
 }

 function f_set_vals(pVal) {
   var x=document.getElementsByName("assocIds");
   for (var i = 0; i < x.length ; i++) {
     if (x[i].name == "assocIds") {
       if ( pVal == 1 ) {
         x[i].checked = true;
       }  else  {
         x[i].checked = false;
       }
     }
   }
 }
 -->
</script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<% 
  StoreCostCenterMgrForm theForm = (StoreCostCenterMgrForm) session.getAttribute("STORE_ADMIN_COST_CENTER_FORM"); 
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
%>

<body>
<div class="text">
<table ID=686 width="769"  class="mainbody">
<tr>
<td><b>Cost Center&nbsp;Id:</b></td>
<td><bean:write name="STORE_ADMIN_COST_CENTER_FORM" property="costCenterDetail.costCenterId"/></td>
<td><b>Cost&nbsp;Center&nbsp;Name:</b></td>
<td><bean:write name="STORE_ADMIN_COST_CENTER_FORM" property="costCenterDetail.shortDesc"/></td>
</tr>

<html:form styleId="687" name="STORE_ADMIN_COST_CENTER_FORM" action="storeportal/costcenterconfig.do"
                             scope="session" type="com.cleanwise.view.forms.StoreCostCenterMgrForm">

<table ID=688>
  <tr> <td><b>Find</b></td>
       <td colspan="3"> 
          <html:select name="STORE_ADMIN_COST_CENTER_FORM" property="confType" onchange="setConfiguredOnly()">
             <html:option value="Catalog">Catalog</html:option>
          </html:select>
       </td>
  </tr>
  <tr><td>&nbsp;</td>
    <td colspan="3"> 
	  <html:text name="STORE_ADMIN_COST_CENTER_FORM" property="confSearchField"/>	
    &nbsp;&nbsp;&nbsp;&nbsp;
    <html:radio name="STORE_ADMIN_COST_CENTER_FORM" property="confSearchType" value="nameBegins" />
    Name(starts with)
    <html:radio name="STORE_ADMIN_COST_CENTER_FORM" property="confSearchType" value="nameContains" />
    Name(contains)
    </td>
  </tr>
  
    
  <tr> <td>&nbsp;</td>
       <td colspan="3">
	      <html:submit property="action"><app:storeMessage  key="global.action.label.search"/></html:submit>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Show Configured Only <html:checkbox property="confShowConfguredOnlyFl" value="true" />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Show Inactive Catalogs <html:checkbox property="confShowInactiveFl" value="true" />
        
      <html:hidden property="costCenterConfig" value="true"/>
     </td>
  </tr>
  <tr><td colspan="4">&nbsp;</td>
  </tr>
  
</table>

<logic:present name="costCenter.catalogs.vector">
<bean:size id="rescount"  name="costCenter.catalogs.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table ID=689 width="769" border="0"  class="stpTable">
<tr align=left>
<td class="stpTH"><a ID=690 href="#pgsort" onclick="sortSubmit('id');">Catalog Id</a></td>
<td class="stpTH"><a ID=691 href="#pgsort" onclick="sortSubmit('name');">Name</a></td>
<td class="stpTH"><a ID=692 href="#pgsort" onclick="sortSubmit('status');">Status</a></td>
<td class="stpTH"><a ID=693 href="#pgsort" onclick="sortSubmit('type');">Type</a></td>
<td class="stpTH">
  <a ID=694 href="javascript:f_check_boxes();">[ Select All ]<br></a>
  <a ID=695 href="javascript:f_uncheck_boxes();">[ Clear All ]</a>
</td>
</tr>
<logic:iterate id="arrele" name="costCenter.catalogs.vector">
<bean:define id="catalogId" name="arrele" property="catalogId"/>
<tr>
<td class="stpTD"><bean:write name="arrele" property="catalogId"/></td>
<td class="stpTD"><bean:write name="arrele" property="shortDesc"/></td>
<td class="stpTD"><bean:write name="arrele" property="catalogStatusCd"/></td>
<td class="stpTD"><bean:write name="arrele" property="catalogTypeCd"/></td>
<td class="stpTD">
  <html:multibox name="STORE_ADMIN_COST_CENTER_FORM" property="assocIds" value="<%=String.valueOf(catalogId)%>"/>
</td>
</tr>
</logic:iterate>

<tr>
<td colspan="4"></td>
<td>
<html:submit property="action"><app:storeMessage  key="global.action.label.save"/></html:submit>
</td>
</tr>
</table>
</logic:greaterThan>
</logic:present>

</table>

</div>

  <html:hidden  property="action" value="BBBBBBB"/>
  <html:hidden  property="sortField" value="BBBBBBB"/>
</html:form>

</body>

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






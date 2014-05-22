<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.forms.LocateStoreCatalogForm" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<% 
String jspFormName = request.getParameter("jspFormName");
  if(jspFormName == null){
	throw new RuntimeException("jspFormName cannot be null");
  }
  
  LocateStoreCatalogForm theForm = null;
  if(session.getAttribute(jspFormName) != null){
    Object tmpForm = session.getAttribute(jspFormName);
	if(tmpForm instanceof StorePortalForm){
		theForm  = ((StorePortalForm) session.getAttribute(jspFormName)).getLocateStoreCatalogForm();
	}else{
		throw new RuntimeException("Bean "+jspFormName+" must be of type StorePortalForm");
	}
  }else{
	throw new RuntimeException("Could not find bean "+jspFormName+" in the session");
  }
  if(theForm != null && theForm.getLocateCatalogFl()){
  String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
  if(jspFormName == null){
	throw new RuntimeException("jspReturnFilterProperty cannot be null");
  }
  String jspFormAction = request.getParameter("jspFormAction");
  String jspSubmitIdent = request.getParameter("jspSubmitIdent");
  jspSubmitIdent += "#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_CATALOG_FORM;
  String blockSelectAccount=request.getParameter("blockSelectAccount");
  String defaultFilter=null;
  boolean block=false;
  if(blockSelectAccount!=null)
      if(blockSelectAccount.equals("block"))
      {
      defaultFilter=request.getParameter("defaultFilter");
       if(defaultFilter!=null) block=true;
      }
%>


<html:html>
<script language="JavaScript1.2">
<!--
function SetCatChecked(val) {
 dml=document.forms; 
 for(i=0; i<dml.length; i++) {
  found = false;
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    //alert(dml[i].elements[j].name);
    if (dml[i].elements[j].name=='locateStoreCatalogForm.selectedCatalogIds') {
      dml[i].elements[j].checked=val;
      found = true;
    }
  }
  if(found) break;
 }
}

function SetAndSubmit (name, val) {
  var dml=document.forms[0]; 
  var ellen = dml[name].length;
  if(ellen>0) {
  for(j=0; j<ellen; j++) {
    if(dml[name][j].value==val) {
      found = true;      
      dml[name][j].checked=1;
    } else {
      dml[name][j].checked=0;
    }
  }
  } else {
    dml[name].checked=1;
  }
  var iiLast = dml['action'].length-1;
  dml['action'][iiLast].value='Return Selected';
  dml.submit();
}
//-->
</script>



<jsp:include flush='true' page="locateStoreAccount.jsp">
   <jsp:param name="jspFormAction" 	value="<%=jspFormAction%>" /> 
   <jsp:param name="jspFormName" 	value="<%=jspFormName%>" /> 
   <jsp:param name="jspSubmitIdent" 	value="<%=jspSubmitIdent%>" /> 
   <jsp:param name="jspReturnFilterProperty" 	value="locateStoreCatalogForm.accountFilter" /> 
   <jsp:param name="jspFormNestProperty" value="locateStoreCatalogForm" />
</jsp:include>


<div class="rptmid">
Find Catalogs
  <table ID=336 cellspacing="0" border="0" width="750" class="mainbody">
  <html:form styleId="337" action="<%=jspFormAction%>"  scope="session">
  <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
  <html:hidden property="locateStoreCatalogForm.property" value="<%=jspReturnFilterProperty%>"/> 
  <html:hidden property="locateStoreCatalogForm.name" value="<%=jspFormName%>"/>
   <%if(block){%> <html:hidden property="defaultFilter" value="<%=defaultFilter%>"/><%}%>
      <tr> <td colspan='4'>
  <%  if(!block){
   AccountDataVector acctDV = theForm.getAccountFilter();
   if(acctDV!=null && acctDV.size()>0) { 
  %>
  <b>Accounts:</b>
  <%
     for(int ii=0; ii<acctDV.size(); ii++) {
        AccountData acctD = (AccountData) acctDV.get(ii); 
  %>
   &lt;<%=acctD.getBusEntity().getShortDesc()%>&gt;
   <% } %>
  <html:submit property="action" value="Clear Account Filter" styleClass='text'/>
   <% } %>
  <html:submit property="action" value="Locate Account" styleClass='text'/>
  <%}%></td></tr>

  <tr> <td><b>Find Catalog:</b></td>
     <td colspan="3">
 	  <html:text  property="locateStoreCatalogForm.searchCatalogField"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <html:radio  property="locateStoreCatalogForm.searchCatalogType" value="catalogId" />
         ID
         <html:radio property="locateStoreCatalogForm.searchCatalogType" value="catalogNameStarts" />
         Name(starts with)
         <html:radio property="locateStoreCatalogForm.searchCatalogType" value="catalogNameContains" />
         Name(contains)
         
         </td>
  </tr>
  <tr> <td><b>Catalog Type:</b></td>
     <td colspan="3">
 	  <html:select property="locateStoreCatalogForm.catalogTypeFilter">
        <html:option value="">&nbsp;</html:option>
        <html:option value="<%=RefCodeNames.CATALOG_TYPE_CD.ACCOUNT%>">
        <%=RefCodeNames.CATALOG_TYPE_CD.ACCOUNT%></html:option>
        <html:option value="<%=RefCodeNames.CATALOG_TYPE_CD.SHOPPING%>">
        <%=RefCodeNames.CATALOG_TYPE_CD.SHOPPING%></html:option>
        <html:option value="<%=RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING%>">
        <%=RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING%></html:option>
        <html:option value="<%=RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR%>">
        <%=RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR%></html:option>
    </html:select>    
         </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
       <html:submit property="action" value="Search"/>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Show Inactive Catalogs
       <html:checkbox property="locateStoreCatalogForm.showInactiveCatalogFl"/>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <html:submit property="action" value="Cancel"/>
       <html:submit property="action" value="Return Selected"/>
    </td>
  </tr>
  <script type="text/javascript" language="JavaScript">
  <!--
     dml=document.forms; 
     for(i=0; i<dml.length; i++) {
      ellen = dml[i].elements.length;
      //alert('next_form='+ellen);
      for(j=0; j<ellen; j++) {
        //alert(dml[i].elements[j].name);
        if (dml[i].elements[j].name=='locateStoreCatalogForm.searchCatalogField') {
          dml[i].elements[j].focus();
          break;
        }
      }
     } 
  // -->
  </script>  
  </table>

<table ID=338 cellspacing="0" border="0" width="750" class="results">
<tr>
<td colspan=4>Search Results Count: 
<bean:write name='<%=jspFormName%>' property="locateStoreCatalogForm.listCount"/> 
</td>
</tr>
<logic:greaterThan name='<%=jspFormName%>' property="locateStoreCatalogForm.listCount" value="0">
<tr align=left>
<td class="tableheader">Id </td>
<td class="tableheader">Name</td>
<td>
<a ID=339 href="javascript:SetCatChecked(1)">[Check&nbsp;All]</a><br>
<a ID=340 href="javascript:SetCatChecked(0)">[&nbsp;Clear]</a>
</td>
<td class="tableheader>">Type </td>
<td class="tableheader>">Status </td>

</tr>
   <logic:iterate id="catalog"  name='<%=jspFormName%>' property="locateStoreCatalogForm.catalogsSelected"
                             type="com.cleanwise.service.api.value.CatalogData">
    <bean:define id="key"  name="catalog" property="catalogId"/>
    <bean:define id="name" name="catalog" property="shortDesc"/>
    <% String linkHref = "javascript: SetAndSubmit ('locateStoreCatalogForm.selectedCatalogIds',"+key+");";%>
    <tr>
  <td><bean:write name="catalog" property="catalogId"/></td>
  <td><a ID=341 href="<%=linkHref%>"><bean:write name="catalog" property="shortDesc"/></a></td>
  <td><html:multibox property="locateStoreCatalogForm.selectedCatalogIds" value="<%=key.toString()%>"/></td>
  <td><bean:write name="catalog" property="catalogTypeCd"/></td>
  <td><bean:write name="catalog" property="catalogStatusCd"/></td>
 </tr>

 </logic:iterate>

</logic:greaterThan>

</table>
  <html:hidden  property="action" value="Search"/>
  </html:form>
</div>
</html:html>
<%}//main if for the page%>

<script type="text/javascript" language="JavaScript">
  <!--
  //var focusControl = document.forms[0]['Search'];
  //if('undefined' != typeof focusControl) {
  //   focusControl.focus();
  //}
  
function kH(e) {
  var keyCode = window.event.keyCode;
  if(keyCode==13) {
    var actionButton = document.forms[0]['action'];
    if('undefined' != typeof actionButton) {
      var len = actionButton.length;
      for(ii=0; ii<len; ii++) {
        if('Search' == actionButton[ii].value) {
          actionButton[ii].select();
          actionButton[ii].click();
          break;
        }      
      }
    }
  }
}
document.onkeypress = kH;
  // -->
</script>


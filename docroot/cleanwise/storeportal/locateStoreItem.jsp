<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>

<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.forms.LocateStoreItemForm" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page contentType="text/html"%>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%

// Customization for use this control for Reporting
CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
String userType = appUser.getUser().getUserTypeCd();

String storeDir=ClwCustomizer.getStoreDir();
//hide or show CheckBox <Show Inactive>
String checkBoxShowInactiveStatus=request.getParameter("checkBoxShowInactive");
boolean checkBoxShowInactiveFl=true;  //if true show
if(checkBoxShowInactiveStatus!=null)
if(checkBoxShowInactiveStatus.equalsIgnoreCase("hide"))
   checkBoxShowInactiveFl=false ; //hide

String jspFormName = request.getParameter("jspFormName");

String jspCatalogListProperty = request.getParameter("jspCatalogListProperty");
String jspRestrictCatalog = request.getParameter("jspRestrictCatalog");

boolean showCatalogCheckBox = false;
boolean restrictCatalogList = false;
String selectedCatalogList = "";
if (jspCatalogListProperty != null) {
  CatalogDataVector catalogDV = (CatalogDataVector)session.getAttribute(jspCatalogListProperty);
  if (catalogDV != null) {
    showCatalogCheckBox = catalogDV.size() > 0;
    for (int i=0; i<catalogDV.size(); i++) {
      if (i>0) selectedCatalogList += ",";
      selectedCatalogList += ((CatalogData)catalogDV.get(i)).getCatalogId();
    }
  }
  if (jspRestrictCatalog != null) {
    if (jspRestrictCatalog.equalsIgnoreCase("yes")) {
      restrictCatalogList = true;
    }
  }
}

  if(jspFormName == null){
  throw new RuntimeException("jspFormName cannot be null");
  }
  LocateStoreItemForm theForm = null;
  if(session.getAttribute(jspFormName) != null){
    Object tmpForm = session.getAttribute(jspFormName);
  if(tmpForm instanceof StorePortalForm){
    theForm  = ((StorePortalForm) session.getAttribute(jspFormName)).getLocateStoreItemForm();
  }else{
    throw new RuntimeException("Bean "+jspFormName+" must be of type StorePortalForm");
  }
  }else{
  throw new RuntimeException("Could not find bean "+jspFormName+" in the session");
  }
  if(theForm != null && theForm.getLocateItemFl()){
  String jspSubmitIdent = request.getParameter("jspSubmitIdent");
  String jspFormAction = request.getParameter("jspFormAction");
  String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
  if(jspFormName == null){
  throw new RuntimeException("jspReturnFilterProperty cannot be null");
  }
  jspSubmitIdent += "#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ITEM_FORM;
  if (theForm != null && restrictCatalogList) {
    theForm.setSearchInSelectedCatalogs(true);
  }
  //To be Useed for Data Werehouse
  String jspDataSourceType = request.getParameter("jspDataSourceType");
  if (jspDataSourceType == null) {
    jspDataSourceType = "";
  }
  boolean showReportingStatus = false;
  if (userType.equals(RefCodeNames.USER_TYPE_CD.REPORTING_USER) || jspDataSourceType.equals("DW")){
    showReportingStatus = true;
  }
	String visibility = "hidden";

%>


<html:html>
<script language="JavaScript1.2">
<!--
function SetChecked(name, val) {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  found = false;
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name==name) {
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

function setObjVisibility(id, value) {
    var panel = document.getElementById(id);
    if (panel) {
        panel.style.visibility = value;
    }
}

function showSearchNumType(val) {

	if(val=='hidden'){
		setObjVisibility('panel1', 'hidden');
	}else{
		setObjVisibility('panel1', 'visible');
	}
}
//-->
</script>



<body>

<script src="../externals/lib.js" language="javascript"></script>

<div class="rptmid">
<app:storeMessage key="locateItem.label.findItems"/>
<%
theForm.setDataSourceType(jspDataSourceType);

StoreData loginStore = theForm.getLoginStore();
StoreData store = theForm.getStore();
String storeType = "";
if(store!=null && loginStore !=null) {
  if(store.getBusEntity().getBusEntityId() != loginStore.getBusEntity().getBusEntityId()) {
      storeType = store.getStoreType().getValue();
%>
<br>Store: <%=store.getBusEntity().getShortDesc()%>
<%}}%>


<table ID=365 width="750" border="0"  class="mainbody">
  <html:form styleId="366" action="<%=jspFormAction%>"  scope="session">
  <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
  <html:hidden property="locateStoreItemForm.property" value="<%=jspReturnFilterProperty%>"/>
  <html:hidden property="locateStoreItemForm.name" value="<%=jspFormName%>"/>

  <tr>
    <td colspan="3">&nbsp; <html:hidden  property="locateStoreItemForm.whereToSearch" value="this"/></td>
  </tr>

  <tr>
    <td><b><app:storeMessage key="locateItem.label.category"/>:</b></td>
    <td><html:text  property="locateStoreItemForm.categoryTempl"/> </td>
    <td>
      <% if (showCatalogCheckBox) { %>
      <html:hidden property="locateStoreItemForm.selectedCatalogList" value="<%=selectedCatalogList%>"/>
      <b><app:storeMessage key="locateItem.label.searchWithinSelectedCatalogs"/></b>&nbsp;<html:checkbox property="locateStoreItemForm.searchInSelectedCatalogs" disabled="<%=restrictCatalogList%>" />
      <%}%>
      &nbsp;
    </td>
  </tr>

  <tr>
    <td><b><app:storeMessage key="locateItem.label.shortDesc"/>:<b></td>
    <td><html:text  property="locateStoreItemForm.shortDescTempl"/></td>
    <% if (!showReportingStatus){ %>
    <td><b><app:storeMessage key="locateItem.label.itemProp"/>:</b>
      <html:select  property="locateStoreItemForm.itemPropertyName" >
        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
        <html:option value="SIZE"><app:storeMessage key="locateItem.itemProp.text.size"/></html:option>
        <html:option value="COLOR"><app:storeMessage key="locateItem.itemProp.text.color"/></html:option>
        <html:option value="UOM"><app:storeMessage key="locateItem.itemProp.text.uom"/></html:option>
        <html:option value="HAZMAT"><app:storeMessage key="locateItem.itemProp.text.hazmat"/></html:option>
        <html:option value="OTHER_DESC"><app:storeMessage key="locateItem.itemProp.text.otherDesc"/></html:option>
        <html:option value="PACK"><app:storeMessage key="locateItem.itemProp.text.pack"/></html:option>
        <html:option value="PKG_UPC_NUM"><app:storeMessage key="locateItem.itemProp.text.pkgUpcNumber"/></html:option>
        <html:option value="PSN"><app:storeMessage key="locateItem.itemProp.text.psn"/></html:option>
        <html:option value="SCENT"><app:storeMessage key="locateItem.itemProp.text.scent"/></html:option>
        <html:option value="SHIP_WEIGHT"><app:storeMessage key="locateItem.itemProp.text.shipWeight"/></html:option>
        <html:option value="UNSPSC_CD"><app:storeMessage key="locateItem.itemProp.text.unspsc"/></html:option>
        <html:option value="UPC_NUM"><app:storeMessage key="locateItem.itemProp.text.upcNumber"/></html:option>
        <html:option value="PACK_PROBLEM_SKU"><app:storeMessage key="locateItem.itemProp.text.packProblem"/></html:option>
      </html:select>
      <html:text  property="locateStoreItemForm.itemPropertyTempl"/>
    </td>
    <% } %>
  </tr>
<% if (!showReportingStatus){ %>
  <tr>
    <td><b><app:storeMessage key="locateItem.label.longDesc"/>:</b></td>
    <td colspan="2"><html:text  property="locateStoreItemForm.longDescTempl" size="80"/></td>
  </tr>
<% } %>
  <tr><td><b><app:storeMessage key="locateItem.label.manuf"/>:</b></td>
      <td colspan='2'><html:text  property="locateStoreItemForm.manuNameTempl" size="20"/></td>
  </tr>
  <% if (!showReportingStatus && ! RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)) {  %>
  <tr>
    <td><b><app:storeMessage key="locateItem.label.distr"/>:</b><br></td>
    <td colspan='2'><html:text  property="locateStoreItemForm.distNameTempl" size="20"/></td>
  </tr>
  <% }  %>

  <%--
  <tr>
    <td><b>Sku:</b></td>
    <td colspan="3">
       <html:text  property="locateStoreItemForm.skuTempl"/>
<% if (!showReportingStatus )  {  %>
       <html:radio  property="locateStoreItemForm.skuType" value="<%=SearchCriteria.STORE_SKU_NUMBER%>"/>
       Store
<% } %>
       <html:radio  property="locateStoreItemForm.skuType" value="<%=SearchCriteria.MANUFACTURER_SKU_NUMBER%>"/>
       Manufacturer
<% if (! RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)) {  %>
       <html:radio  property="locateStoreItemForm.skuType" value="<%=SearchCriteria.DISTRIBUTOR_SKU_NUMBER%>"/>
       Distributor
<% }  %>
<% if (!showReportingStatus )  {  %>
       <html:radio  property="locateStoreItemForm.skuType" value="<%=SearchCriteria.CUST_SKU_NUMBER%>"/>
       Store+Customer
<% } %>
     </td>
   </tr>
--%>
	<tr>
		<td><b><app:storeMessage key="locateItem.label.sku"/>:</b></td>
		<td colspan="3">
		<table>
	   <tr>
	   <td>
			<html:text property="locateStoreItemForm.skuTempl"/>
			<% if (!showReportingStatus )  {  %>
				<html:radio property="locateStoreItemForm.skuType" value="<%=SearchCriteria.STORE_SKU_NUMBER%>"
				onclick="return showSearchNumType('hidden');"/>
				<app:storeMessage key="locateItem.radio.text.store"/>
			<% } %>
			<html:radio property="locateStoreItemForm.skuType" value="<%=SearchCriteria.MANUFACTURER_SKU_NUMBER%>"
			onclick="return showSearchNumType('visible');"/>
			<app:storeMessage key="locateItem.radio.text.manuf"/>
			<% if (! RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)) {  %>
				<html:radio property="locateStoreItemForm.skuType" value="<%=SearchCriteria.DISTRIBUTOR_SKU_NUMBER%>"
				onclick="return showSearchNumType('visible');"/>
				<app:storeMessage key="locateItem.radio.text.distr"/>
			<% }  %>
			<% if (!showReportingStatus )  {  %>
				<html:radio  property="locateStoreItemForm.skuType" value="<%=SearchCriteria.CUST_SKU_NUMBER%>"
				onclick="return showSearchNumType('visible');"/>
				<app:storeMessage key="locateItem.radio.text.storeCust"/>
			<% } %>
		</td>
		</tr>
		<tr>
		<td align="center" id="panel1" style="visibility:<%=visibility%>;">
			<html:radio property="locateStoreItemForm.searchNumType" value="nameBegins"/>
			<app:storeMessage key="locateItem.radio.text.startsWith"/>
			<html:radio property="locateStoreItemForm.searchNumType" value="nameContains"/>
			<app:storeMessage key="locateItem.radio.text.contains"/>
		</td>

	   </tr>
	   </table>

	 </tr>
  <tr class="results">
    <td colspan="3"></td>
  </tr>

  <tr>
    <td colspan="3"></td>
  </tr>

  <tr>
    <td colspan="3">
     <html:submit property="action">
       <app:storeMessage  key="global.action.label.search"/>
     </html:submit>&nbsp;&nbsp;
   <%String prop="locateStoreItemForm.showInactiveFl";
  if(!showReportingStatus && checkBoxShowInactiveFl){%>
    <app:storeMessage key="locateItem.label.showInactive"/>: <html:checkbox property="<%=prop%>"/>
   <%}%>
    <% if (! RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)) {  %>
    <app:storeMessage key="locateItem.label.showDistrInfo"/><html:checkbox  property="locateStoreItemForm.distInfoRequest"/>
    <% }  %>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.cancel"/>
      </html:submit>

       <html:submit property="action">
          <app:storeMessage  key="admin.button.returnSelected"/>
        </html:submit>
      </td>
    </td>
  </tr>
</table>


<app:storeMessage key="locateItem.label.searchResultCount"/>: <bean:write name="<%=jspFormName%>" property="locateStoreItemForm.listCount"/>

<logic:greaterThan name="<%=jspFormName%>" property="locateStoreItemForm.listCount" value="0">
<bean:define id="distInfoFlag"  name="<%=jspFormName%>" property="locateStoreItemForm.distInfoFlag"/>
<table ID=367 width="100%" class="results">
<tr align=center>
<%if(!showReportingStatus) {%>
  <td class="tableheader"><b><app:storeMessage key="locateItem.text.id"/></b></a> </td>
<% } %>
<td class="tableheader"><b><app:storeMessage key="locateItem.text.sku"/></b></a> </td>
<td>
<a ID=368 href="javascript:SetChecked('locateStoreItemForm.selectedItemIds',1)"><app:storeMessage key="admin2.label.selectCheckAll"/></a><br>
<a ID=369 href="javascript:SetChecked('locateStoreItemForm.selectedItemIds',0)"><app:storeMessage key="admin2.label.clear"/></a>
</td>
<td class="tableheader"><b><app:storeMessage key="locateItem.text.name"/></b> </td>
<%if(!showReportingStatus) {%>
  <td class="tableheader"><b><app:storeMessage key="locateItem.text.size"/></b> </td>
<% } %>
<td class="tableheader"><b><app:storeMessage key="locateItem.text.pack"/></b> </td>
<td class="tableheader"><b><app:storeMessage key="locateItem.text.uom"/></b> </td>
<%if(!showReportingStatus) {%>
  <td class="tableheader"><b><app:storeMessage key="locateItem.text.color"/></b> </td>
<% } %>
<td class="tableheader"><b><app:storeMessage key="locateItem.text.mfg"/></b> </td>
<%if(!showReportingStatus) {%>
  <td class="tableheader"><b><app:storeMessage key="locateItem.text.mfgSku"/></b> </td>
<% } %>
<td class="tableheader"><b><app:storeMessage key="locateItem.text.category"/></b></td>
<% if(theForm.getDistInfoFlag() && ! RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)) {  %>
<td class="tableheader"><b><app:storeMessage key="locateItem.text.distributor"/></b> </td>
<td class="tableheader"><b><app:storeMessage key="locateItem.text.distSku"/></b> </td>
<% } %>
<%if(!showReportingStatus) {%>
<td class="tablehader"><b><app:storeMessage key="locateItem.text.status"/></b></td>
<% } %>
</tr>

   <logic:iterate id="item" name="<%=jspFormName%>" property="locateStoreItemForm.itemsSelected"
    indexId="indId">
    <bean:define id="key"  name="item" property="itemId"/>
    <bean:define id="sku" name="item" property="sku"/>
    <bean:define id="name" name="item" property="name"/>
    <bean:define id="size" name="item" property="size"/>
    <bean:define id="pack" name="item" property="pack"/>
    <bean:define id="uom" name="item" property="uom"/>
    <bean:define id="color" name="item" property="color"/>
    <bean:define id="manuName" name="item" property="manufName"/>
    <bean:define id="manuSku" name="item" property="manufSku"/>
    <bean:define id="category" name="item" property="category"/>
<% if(theForm.getDistInfoFlag() && !RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)) {  %>
    <bean:define id="distName" name="item" property="distName"/>
    <bean:define id="distSku" name="item" property="distSku"/>
<% } %>
     <bean:define id="statusCd" name="item" property="statusCd"/>

    <% String linkHref = "javascript: SetAndSubmit ('locateStoreItemForm.selectedItemIds',"+key+");";%>

<% if (( indId.intValue() % 2 ) == 0 ) { %> <tr class="rowa">
<% } else { %> <tr class="rowb"> <% } %>
<% if (!showReportingStatus){ %>
<td><bean:write name="key"/></td>
<% } %>
    <td><bean:write name="sku"/></td>
    <td>
      <html:multibox styleClass="smalltext"
      property="locateStoreItemForm.selectedItemIds" value="<%=key.toString()%>"/>
    </td>
    <td><a ID=370 href="<%=linkHref%>"><bean:write name="name"/></a></td>
<% if (!showReportingStatus){ %>
<td><bean:write name="size"/></td>
<% } %>
    <td><bean:write name="pack"/></td>
    <td><bean:write name="uom"/></td>
<% if (!showReportingStatus){ %>
<td><bean:write name="color"/></td>
<% } %>
    <td><bean:write name="manuName"/></td>
<% if (!showReportingStatus){ %>
    <td><bean:write name="manuSku"/></td>
<% } %>
    <td><bean:write name="category"/></td>
<% if(theForm.getDistInfoFlag() && !RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)) {  %>
    <td><bean:write name="distName"/></td>
    <td><bean:write name="distSku"/></td>
<% } %>
<% if (!showReportingStatus){ %>
   <td><bean:write name="statusCd"/></td>
<% } %>

  <td>
 </tr>
 </logic:iterate>

<tr align=center><td colspan="11">&nbsp;</td></tr>
</table>

</logic:greaterThan>

   <html:hidden  property="action" value="Search"/>

</html:form>

</div>
</body>

</html:html>
<%}//if(theForm.getLocateItemFl())%>

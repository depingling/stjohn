<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
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
<bean:define id="storeItemForm" scope="session" name="STORE_ADMIN_ITEM_FORM" type="com.cleanwise.view.forms.StoreItemMgrForm"/>

<% 
String storeDir=ClwCustomizer.getStoreDir();
String jspUseItemsLinksForAllStores = request.getParameter("useItemsLinksForAllStores");
if (jspUseItemsLinksForAllStores == null) {
    jspUseItemsLinksForAllStores = "";
}

boolean hideThisScreen = false;
if (request.getAttribute("hideLocateStoreItemScreen") != null) {
    String hideLocateStoreItemScreen = (String)request.getAttribute("hideLocateStoreItemScreen");
    if (hideLocateStoreItemScreen != null) {
        if (hideLocateStoreItemScreen.equalsIgnoreCase("true")) {
            hideThisScreen = true;
        }
    }
}

String jspFormName = request.getParameter("jspFormName");
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
  //if(theForm != null && theForm.getLocateItemFl()){
  if(theForm != null && !hideThisScreen){
      String jspSubmitIdent = request.getParameter("jspSubmitIdent");
      String jspFormAction = request.getParameter("jspFormAction");
      String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
      if(jspFormName == null){
        throw new RuntimeException("jspReturnFilterProperty cannot be null");
      }
      jspSubmitIdent += "#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ITEM_FORM;
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
 dml=document.forms; 
 for(i=0; i<dml.length; i++) {
  found = false;
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action') {
      //alert('action.value='+dml[i].elements[j].value);
    }
    //alert('name='+dml[i].elements[j].name);
    if (dml[i].elements[j].name==name) {
      if(dml[i].elements[j].value==val) {
        found = true;      
        dml[i].elements[j].checked=1;
      } else {
        dml[i].elements[j].checked=0;
      }
    }
  }
  //alert(found);
  if(found) {
   dml[i].submit();
  }
 }
 
}

//-->
</script>



<body>

<script src="../externals/lib.js" language="javascript"></script>

<div class="rptmid">
Find Items
<% 
StoreData loginStore = theForm.getLoginStore();
StoreData store = theForm.getStore();
String storeType = "";
if(store!=null && loginStore !=null) {
  if(store.getBusEntity().getBusEntityId() != loginStore.getBusEntity().getBusEntityId()) {
      storeType = store.getStoreType().getValue();
%>  
<br>Store: <%=store.getBusEntity().getShortDesc()%>
<%}}%>

<table ID=318 width="750" border="0"  class="mainbody">
  <html:form styleId="319" action="<%=jspFormAction%>"  scope="session">
  <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
  <html:hidden property="locateStoreItemForm.property" value="<%=jspReturnFilterProperty%>"/>
  <html:hidden property="locateStoreItemForm.name" value="<%=jspFormName%>"/>
  <html:hidden property="useItemsLinksForAllStores" value="<%=jspUseItemsLinksForAllStores%>"/>
  
  <tr><td>&nbsp;</td>
  <td colspan="3">
    &nbsp; <html:hidden  property="locateStoreItemForm.whereToSearch" value="this"/>
  </td>
  </tr>
  <tr><td><b>Store:</b></td>
    <td colspan='3'>
        <html:select property="locateStoreItemForm.storeId" >
            <html:option value="">&nbsp;</html:option>
   <% 
      BusEntityDataVector beDV = storeItemForm.getUserStoreEntities();
      if(beDV!=null) {
            for(int ii=0; ii<beDV.size(); ii++) {
                BusEntityData beD = (BusEntityData) beDV.get(ii); 
   %>
            <html:option value='<%=""+beD.getBusEntityId()%>'><%=beD.getShortDesc()%></html:option>
   <%       }
      } %>
   </html:select>    
    </td>
  </tr>
  <tr><td><b>Category:</b></td>
  <td colspan='3'><html:text  property="locateStoreItemForm.categoryTempl"/> </td>
  </tr>
  <tr>
  <td><b>Short Description:<b></td>
  <td><html:text  property="locateStoreItemForm.shortDescTempl"/></td>
   <td colspan=4><b>Item Property:</b>
   <html:select  property="locateStoreItemForm.itemPropertyName" >
   <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
   <html:option value="SIZE">Size</html:option>
   <html:option value="COLOR">Color</html:option>
   <html:option value="UOM">Uom</html:option>
   <html:option value="HAZMAT">Hazmat (true)</html:option>
   <html:option value="OTHER_DESC">Other Desc</html:option>
   <html:option value="PACK">Pack</html:option>
   <html:option value="PKG_UPC_NUM">Pkg UPC Number</html:option>
   <html:option value="PSN">PSN</html:option>
   <html:option value="SCENT">Scent</html:option>
   <html:option value="SHIP_WEIGHT">Ship Weight</html:option>
   <html:option value="UNSPSC_CD">UNSPSC</html:option>
   <html:option value="UPC_NUM">UPC Number</html:option>
   <html:option value="PACK_PROBLEM_SKU">Pack Problem (true)</html:option>
   </html:select>
   <html:text  property="locateStoreItemForm.itemPropertyTempl"/>
  </td>
  </tr>
  <tr><td><b>Long Description:</b></td>
  <td colspan="3"><html:text  property="locateStoreItemForm.longDescTempl" size="80"/>
  </td>
  </tr>
  <tr><td><b>Manufacturer:</b></td>
      <td colspan='3'><html:text  property="locateStoreItemForm.manuNameTempl" size="20"/>
      </td>
  </tr>    
<% if (! RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)) {  %>  
  <tr> <td><b>Distributor:</b><br></td>
      <td colspan='3'><html:text  property="locateStoreItemForm.distNameTempl" size="20"/>
      </td>
  </tr>
<% }  %>  
  <tr><td><b>Sku:</b></td>
       <td colspan="3">
       <html:text  property="locateStoreItemForm.skuTempl"/>
       <html:radio  property="locateStoreItemForm.skuType" value="<%=SearchCriteria.STORE_SKU_NUMBER%>"/>
       Store
       <html:radio  property="locateStoreItemForm.skuType" value="<%=SearchCriteria.MANUFACTURER_SKU_NUMBER%>"/>
       Manufacturer
<% if (! RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)) {  %>  
       <html:radio  property="locateStoreItemForm.skuType" value="<%=SearchCriteria.DISTRIBUTOR_SKU_NUMBER%>"/>
       Distributor
<% }  %>       
       <html:radio  property="locateStoreItemForm.skuType" value="<%=SearchCriteria.CUST_SKU_NUMBER%>"/>
       Store+Customer
       </td>
  </tr>
  <tr class="results"> <td></td>
       <td colspan="3">
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
   </td>
  </tr>
  <tr><td colspan="4">
       <html:submit property="action" value="Search Items"/>&nbsp;&nbsp;
<% if (! RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)) {  %>  
    Show Distributor Info<html:checkbox  property="locateStoreItemForm.distInfoRequest"/>
<% }  %>    
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <html:submit property="action" value="Cancel"/>
	     <html:submit property="action" value="Return Selected"/></td>

    </td>
  </tr>
</table>
Search result count: <bean:write name="<%=jspFormName%>" property="locateStoreItemForm.listCount"/>
<logic:greaterThan name="<%=jspFormName%>" property="locateStoreItemForm.listCount" value="0">
<bean:define id="distInfoFlag"  name="<%=jspFormName%>" property="locateStoreItemForm.distInfoFlag"/>
<table ID=320 width="100%" class="results">
<tr align=center>
<td class="tableheader"><b>Store</b></td>
<td class="tableheader"><b>Id</b></a> </td>
<td class="tableheader"><b>Sku</b></a> </td>
<td>
<a ID=321 href="javascript:SetChecked('locateStoreItemForm.selectedItemIds',1)">[Check&nbsp;All]</a><br>
<a ID=322 href="javascript:SetChecked('locateStoreItemForm.selectedItemIds',0)">[&nbsp;Clear]</a>
</td>
<td class="tableheader"><b>Name</b> </td>
<td class="tableheader"><b>Size</b> </td>
<td class="tableheader"><b>Pack</b> </td>
<td class="tableheader"><b>UOM</b> </td>
<td class="tableheader"><b>Color</b> </td>
<td class="tableheader"><b>Mfg.</b> </td>
<td class="tableheader"><b>Mfg.&nbsp;Sku</b> </td>
<td class="tableheader"><b>Category</b></td>
<% if(theForm.getDistInfoFlag() && ! RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)) {  %>  
<td class="tableheader"><b>Distributor</b> </td>
<td class="tableheader"><b>Dist.&nbsp;Sku</b> </td>
<% } %>
</tr>

   <logic:iterate id="item" name="<%=jspFormName%>" property="locateStoreItemForm.itemsSelected" 
    indexId="indId">
    <bean:define id="storeName"  name="item" property="storeName"/>
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

    <% String linkHref = "javascript: SetAndSubmit ('locateStoreItemForm.selectedItemIds',"+key+");";%>

<% if (( indId.intValue() % 2 ) == 0 ) { %> <tr class="rowa">
<% } else { %> <tr class="rowb"> <% } %>

    <td><bean:write name="storeName"/></td>
    <td><bean:write name="key"/></td>
    <td><bean:write name="sku"/></td>
    <td>
      <html:multibox styleClass="smalltext"
      property="locateStoreItemForm.selectedItemIds" value="<%=key.toString()%>"/>
    </td>
    <td><a ID=323 href="<%=linkHref%>"><bean:write name="name"/></a></td>
    <td><bean:write name="size"/></td>
    <td><bean:write name="pack"/></td>
    <td><bean:write name="uom"/></td>
    <td><bean:write name="color"/></td>
    <td><bean:write name="manuName"/></td>
    <td><bean:write name="manuSku"/></td>
    <td><bean:write name="category"/></td>
<% if(theForm.getDistInfoFlag() && !RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)) {  %>  
    <td><bean:write name="distName"/></td>
    <td><bean:write name="distSku"/></td>
<% } %>


  <td>
 </tr>
 </logic:iterate>

<tr align=center><td colspan="11">&nbsp;</td></tr>
</table>

</logic:greaterThan>


  
  <html:hidden  property="action" value="Return Selected"/>

</html:form>
  
</div>
</body>

</html:html>
<%}//if(theForm.getLocateItemFl())%>

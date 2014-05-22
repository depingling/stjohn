<%@ page contentType="text/html"%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="theForm" name="STORE_ADMIN_ITEM_FORM" type="com.cleanwise.view.forms.StoreItemMgrForm"/>

<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.STORE_ADMIN_ITEM_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectorBox') {
     dml.elements[i].checked=val;
   }
 }
}

function popLocate(name) {
  var loc = "catalogitemadd.do?feedField=" + name;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

function popManufLocate(name,name1) {
  var loc = "manuflocate.do?feedField=" + name+"&feedDesc="+name1;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

function popDistLocate(name,name1) {
  var loc = "distlocate.do?feedField=" + name+"&feedDesc="+name1;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
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

<% String catalogIdS = ""+theForm.getCatalogId(); 
   String visibility = "visible";
%>
<logic:equal name="STORE_ADMIN_ITEM_FORM" property="skuType" value="<%=SearchCriteria.STORE_SKU_NUMBER%>">
<%visibility = "hidden";%>
</logic:equal>


<html:hidden name="STORE_ADMIN_ITEM_FORM" property="catalogId" value="catalogIdS"/>

  <table ID=992 border="0" width="769" class="mainbody">
<html:form styleId="993" action="storeportal/items-submit.do" focus="skuTempl">

  <tr><td class="largeheader">Search Item</td>
  <td colspan="3">
    &nbsp; <html:hidden name="STORE_ADMIN_ITEM_FORM" property="whereToSearch" value="this"/>
  </td>
  </tr>
  <tr><td><b>Category:</b></td>
  <td colspan='3'><html:text name="STORE_ADMIN_ITEM_FORM" property="categoryTempl"/> </td>
  </tr>
  <tr>
  <td><b>Short Description:<b></td>
  <td><html:text name="STORE_ADMIN_ITEM_FORM" property="shortDescTempl"/></td>
   <td colspan=2><b>Item Property:</b>
   <html:select name="STORE_ADMIN_ITEM_FORM" property="itemPropertyName" >
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
   <html:text name="STORE_ADMIN_ITEM_FORM" property="itemPropertyTempl"/>
  </td>
  </tr>
  <tr><td><b>Long Description:</b></td>
  <td colspan="3"><html:text name="STORE_ADMIN_ITEM_FORM" property="longDescTempl" size="80"/>
  </td>
  </tr>
  <tr><td><b>Manufacturer:</b></td>
      <td colspan='3'><html:text name="STORE_ADMIN_ITEM_FORM" property="manuNameTempl" size="20"/>
      </td>
  </tr>    
<logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">  
  <tr> <td><b>Distributor:</b><br></td>
      <td colspan='3'><html:text name="STORE_ADMIN_ITEM_FORM" property="distNameTempl" size="20"/>
      </td>
  </tr>
</logic:notEqual>  

  <tr><td><b>Sku:</b></td>
       <td colspan="3">  
	   <table>
	   <tr>
	   <td>
			<html:text name="STORE_ADMIN_ITEM_FORM" property="skuTempl"/>
			<html:radio name="STORE_ADMIN_ITEM_FORM" property="skuType" value="<%=SearchCriteria.STORE_SKU_NUMBER%>"
			onclick="return showSearchNumType('hidden');"/>
			Store
			<html:radio name="STORE_ADMIN_ITEM_FORM" property="skuType" value="<%=SearchCriteria.MANUFACTURER_SKU_NUMBER%>" 
			onclick="return showSearchNumType('visible');"/>
			Manufacturer
			<logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">       
				<html:radio name="STORE_ADMIN_ITEM_FORM" property="skuType" value="<%=SearchCriteria.DISTRIBUTOR_SKU_NUMBER%>"
				onclick="return showSearchNumType('visible');"/>
				Distributor
			</logic:notEqual> 
		</td>

		<td align="left" id="panel1" style="visibility:<%=visibility%>;">     
			<html:radio name="STORE_ADMIN_ITEM_FORM" property="searchNumType" value="nameBegins"/>
			(starts with)
			<html:radio name="STORE_ADMIN_ITEM_FORM" property="searchNumType" value="nameContains"/>
			(contains)
		</td>
	   
	   </tr>
	   </table>
	   </td>
	  
  </tr>

    <tr>
          <td><b>"Green" Certified : </b> </td>
          <td colspan="3">
          <html:radio name="STORE_ADMIN_ITEM_FORM" property="searchGreenCertifiedFl" value="true"/>Yes
          <html:radio name="STORE_ADMIN_ITEM_FORM" property="searchGreenCertifiedFl" value="false"/>No
          </td>
      </tr>
  <tr class="results"> <td></td>
       <td colspan="3">
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
       <html:submit property="action" value="Search"/>
       <html:button property="action" value="Create New" onclick="document.location='item-edit.do?action=Create New';"/>
       <html:submit property="action" value="Load"/>



        Show Inactive: <html:checkbox property="showInactiveFl"/>
         <logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">
	     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         Show Distributor Info<html:checkbox name="STORE_ADMIN_ITEM_FORM" property="distInfoFlag"/>
        </logic:notEqual>
    </td>
  </tr>
</table>
Search result count: <bean:write name="STORE_ADMIN_ITEM_FORM" property="listSize"/>
<logic:greaterThan name="STORE_ADMIN_ITEM_FORM" property="listSize" value="0">
<div>

<bean:define id="distInfoFlag"  name="STORE_ADMIN_ITEM_FORM" property="distInfoFlag"/>
<table ID=994 width="100%" class="results">
<tr align=center>
<td><a ID=995 class="tableheader" href="searchItems.do?action=sort&sortField=id&distInfoFlag=<%=distInfoFlag%>"><b>Id</b></a> </td>
<td><a ID=996 class="tableheader" href="searchItems.do?action=sort&sortField=sku&distInfoFlag=<%=distInfoFlag%>"><b>Sku</b></a> </td>
<td><a ID=997 class="tableheader" href="searchItems.do?action=sort&sortField=name&distInfoFlag=<%=distInfoFlag%>"><b>Name</b></a> </td>
<td><a ID=998 class="tableheader" href="searchItems.do?action=sort&sortField=size&distInfoFlag=<%=distInfoFlag%>"><b>Size</b></a> </td>
<td><a ID=999 class="tableheader" href="searchItems.do?action=sort&sortField=pack&distInfoFlag=<%=distInfoFlag%>"><b>Pack</b></a> </td>
<td><a ID=1000 class="tableheader" href="searchItems.do?action=sort&sortField=uom&distInfoFlag=<%=distInfoFlag%>"><b>UOM</b> </td>
<td><a ID=1001 class="tableheader" href="searchItems.do?action=sort&sortField=color&distInfoFlag=<%=distInfoFlag%>"><b>Color</b> </td>
<td><a ID=1002 class="tableheader" href="searchItems.do?action=sort&sortField=manufacturer&distInfoFlag=<%=distInfoFlag%>"><b>Mfg.</b></a> </td>
<td><a ID=1003 class="tableheader" href="searchItems.do?action=sort&sortField=msku&distInfoFlag=<%=distInfoFlag%>"><b>Mfg.&nbsp;Sku</b></a> </td>
<td><a ID=1004 class="tableheader" href="searchItems.do?action=sort&sortField=category&distInfoFlag=<%=distInfoFlag%>"><b>Category</b></a> </td>

<logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">
<% if(theForm.getDistInfoFlag()) { %>
<td><a ID=1005 class="tableheader" href="searchItems.do?action=sort&sortField=dist&distInfoFlag=<%=distInfoFlag%>"><b>Distributor</b></a> </td>
<td><a ID=1006 class="tableheader" href="searchItems.do?action=sort&sortField=dsku&distInfoFlag=<%=distInfoFlag%>"><b>Dist.&nbsp;Sku</b></a> </td>
<% } %>
 </logic:notEqual>
<td><a ID=1007 class="tableheader" href="searchItems.do?action=sort&sortField=statusCd&distInfoFlag=<%=distInfoFlag%>"><b>Status</b></td>


<td width="4%" class="tableheader">Select</td>
</tr>
   <logic:iterate id="item" name="STORE_ADMIN_ITEM_FORM" property="items" 
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
    <bean:define id="statusCd" name="item" property="statusCd"/>
       
<logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">
<% if(theForm.getDistInfoFlag()) { %>
    <bean:define id="distName" name="item" property="distName"/>
    <bean:define id="distSku" name="item" property="distSku"/>
<% } %>
</logic:notEqual>

    <% String linkHref = new String("item-edit.do?action=edit&retaction=finditem&itemId=" + key);%>

<% if (( indId.intValue() % 2 ) == 0 ) { %> <tr class="rowa">
<% } else { %> <tr class="rowb"> <% } %>

    <td><bean:write name="key"/></td>
    <td><bean:write name="sku"/></td>
    <td><html:link href="<%=linkHref%>"><bean:write name="name"/></html:link></td>
    <td><bean:write name="size"/></td>
    <td><bean:write name="pack"/></td>
    <td><bean:write name="uom"/></td>
    <td><bean:write name="color"/></td>
    <td><bean:write name="manuName"/></td>
    <td><bean:write name="manuSku"/></td>
    <td><bean:write name="category"/></td>

<logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">    
<% if(theForm.getDistInfoFlag()) { %>
    <td><bean:write name="distName"/></td>
    <td><bean:write name="distSku"/></td>
<% } %>
</logic:notEqual>
    <td><bean:write name="statusCd"/></td>

    <td>
      <!--html:multibox name="STORE_ADMIN_ITEM_FORM" styleClass="smalltext"
      property="selectorBox" value="<%=indId.toString()%>"/-->
       <% String linkClone = new String("item-edit.do?action=clone&retaction=itemsearch&itemId=" + key);%>
       <html:link href="<%=linkClone%>">Clone</html:link>
    </td>

  <td>
 </tr>
 </logic:iterate>
<tr align=center><td colspan="11">&nbsp;</td></tr>
</table>

<div style="text-align: right; margin-right: 2em; background-color: #cccccc; ">

<% /*
<a ID=1008 href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a ID=1009 href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
<b>Categories Available: </b>
<bean:define id="catsAvailable" scope="session"
  name="STORE_ADMIN_ITEM_FORM" property="categoryTree" 
  type="java.util.LinkedList" />
<html:select name="STORE_ADMIN_ITEM_FORM" property="selectedCategoryId">
<html:options collection="catsAvailable" 
  labelProperty="labelDesc" 
  property="catalogCategoryId"/>

</html:select>

<html:submit property="action" value="Add category to item(s).">
add_to_category
</html:submit>
<br><html:submit property="action" value="Reset Item(s) to Distributor 0"/>
*/ %>

</div>

</logic:greaterThan>


</html:form>


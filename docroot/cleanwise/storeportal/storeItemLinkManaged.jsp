<%@ page contentType="text/html"%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

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

//-->
</script>

<jsp:include flush='true' page="locateItem.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/linkManagedItems.do" /> 
   <jsp:param name="jspFormName" 	value="STORE_ADMIN_ITEM_FORM" /> 
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" 	value="itemsToLink" />
   <jsp:param name="checkBoxShowInactive" value="hide" />
</jsp:include>


<% String catalogIdS = ""+theForm.getCatalogId(); %>

<html:hidden name="STORE_ADMIN_ITEM_FORM" property="catalogId" value="catalogIdS"/>

  <table ID=978 border="0" width="769" class="mainbody">
<html:form styleId="979" action="storeportal/linkManagedItems.do">

  <tr><td colspan='4' class="largeheader">Find Items To Link</td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
       <html:submit property="action" value="Link Items"/>
    </td>
  </tr>
</table>
<% ItemViewVector itemVwV = theForm.getItemsToLink(); 
   if(itemVwV!=null) {
%>
Search result count: <%=itemVwV.size()%>
<% if(itemVwV.size()>0) { %>
<div>

<bean:define id="distInfoFlag"  name="STORE_ADMIN_ITEM_FORM" property="distInfoFlag"/>
<table ID=980 width="100%" class="results">
<tr align=center>
<td class="tableheader"><b>Store</b> </td>
<td><a ID=981 class="tableheader" href="items.do?action=sort&sortField=id"><b>Id</b></a> </td>
<td><a ID=982 class="tableheader" href="items.do?action=sort&sortField=sku"><b>Sku</b></a> </td>
<td><a ID=983 class="tableheader" href="items.do?action=sort&sortField=name"><b>Name</b></a> </td>
<td><a ID=984 class="tableheader" href="items.do?action=sort&sortField=size"><b>Size</b></a> </td>
<td><a ID=985 class="tableheader" href="items.do?action=sort&sortField=pack"><b>Pack</b></a> </td>
<td><a ID=986 class="tableheader" href="items.do?action=sort&sortField=uom"><b>UOM</b> </td>
<td><a ID=987 class="tableheader" href="items.do?action=sort&sortField=color"><b>Color</b> </td>
<td><a ID=988 class="tableheader" href="items.do?action=sort&sortField=manufacturer"><b>Mfg.</b></a> </td>
<td><a ID=989 class="tableheader" href="items.do?action=sort&sortField=msku"><b>Mfg.&nbsp;Sku</b></a> </td>
<td><a ID=990 class="tableheader" href="items.do?action=sort&sortField=category"><b>Category</b></a> </td>
</tr>
   <logic:iterate id="item" name="STORE_ADMIN_ITEM_FORM" property="itemsToLink" 
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

    <% String linkHref = new String("linkManagedItems.do?action=link&itemId=" + key);%>

<% if (( indId.intValue() % 2 ) == 0 ) { %> <tr class="rowa">
<% } else { %> <tr class="rowb"> <% } %>

    <td><bean:write name="storeName"/></td>
    <td><bean:write name="key"/></td>
    <td><bean:write name="sku"/></td>
    <td><bean:write name="name"/></td>
    <td><bean:write name="size"/></td>
    <td><bean:write name="pack"/></td>
    <td><bean:write name="uom"/></td>
    <td><bean:write name="color"/></td>
    <td><bean:write name="manuName"/></td>
    <td><bean:write name="manuSku"/></td>
    <td><bean:write name="category"/></td>

  <td>
 </tr>
 </logic:iterate>
</table>

<% }} %>


</html:form>


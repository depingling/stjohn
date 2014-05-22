<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Enumeration" %>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="STORE_ADMIN_ITEM_FORM" type="com.cleanwise.view.forms.StoreItemMgrForm"/>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js" CHARSET="ISO-8859-1"></SCRIPT>


<style>
th {font-size: 9px; font-weight: normal; 
	background-color: white;
	color: black; }

#UOMCD  { position: relative;}
#DISTUOMCD  { position: relative;}
</style>



<body bgcolor="#cccccc">

<div class = "text">


<table ID=658 border="0"cellpadding="0" cellspacing="1" width="769">
<html:form styleId="659" action="/storeportal/itemMultiproducts.do" enctype="multipart/form-data">
 <% String action = theForm.getAction();
    Object  errorMessageObj =  request.getAttribute("org.apache.struts.action.ERROR");
    if( (errorMessageObj==null && "editMultiproduct".equals(action)) ||
      (errorMessageObj!=null && "Delete Multi product".equals(action)) ||      
      "New Multi product".equals(action) ||
      "Save Multi product".equals(action)) 
       { %>
 <tr>
  <td colspan='4'>
    <html:text size='50' name="STORE_ADMIN_ITEM_FORM" property="editMultiproductName" />
    <html:submit styleClass='text' property="action" value="Save Multi product"/>
    <% if(theForm.getEditMultiproductId()>0) { %>
      <html:submit styleClass='text' property="action" value="Delete Multi product"/>
    <% } %>
  </td>
 </tr>
    <tr> <td colspan='4' bgcolor="#cccccc" class="tableHeader">&nbsp;</td> </tr>
<% } %>
<tr>
 <td colspan='4'>
   <html:submit styleClass='text' property="action" value="New Multi product"/>
 </td>
</tr>
</html:form>

<!-- Store Multiproducts -->
    <tr>
       <td colspan='4' bgcolor="#cccccc" class="tableHeader">Store Multi products</td>
     </tr>
    <tr>
       <td class="tableHeader">Multi product Id</td>
       <td class="tableHeader">Multi product</td>
     </tr>
    <logic:iterate id="multiproduct" name="STORE_ADMIN_ITEM_FORM" property="multiproducts"
        type="com.cleanwise.service.api.value.MultiproductView"
        indexId="indId">
    <% if (( indId.intValue() % 2 ) == 0 ) { %> <tr class="rowa">
    <% } else { %> <tr class="rowb"> <% } %>
       <bean:define id="multiproductId" name="multiproduct" property="itemData.itemId"/>    
    <% String multiproductHref = new String("itemMultiproducts.do?action=editMultiproduct&multiproductId=" + multiproductId);%>
       <td><bean:write name="multiproduct" property="itemData.itemId"/></td>
       <td><html:link href="<%=multiproductHref%>"><bean:write name="multiproduct" property="itemData.shortDesc"/></html:link></td>
     </tr>
     </logic:iterate>
</table>
        




        

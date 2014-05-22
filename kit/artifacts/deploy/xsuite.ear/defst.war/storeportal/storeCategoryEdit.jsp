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
<script type="text/javascript" src="../externals/jquery/1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="../externals/jqueryui/1.8.9/jquery-ui.min.js"></script>
<script type="text/javascript" src="../externals/jqueryui/1.8.14/i18n/jquery-ui-i18n.min.js"></script>

<bean:define id="theForm" name="STORE_ADMIN_ITEM_FORM" type="com.cleanwise.view.forms.StoreItemMgrForm"/>
<%
   boolean canEditFl = theForm.getCanEditFl();
%>


<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js" CHARSET="ISO-8859-1"></SCRIPT>
<script language="JavaScript1.2">
    <!--
    
    function moveCategory(action) {
    	var radioButtons = document.getElementsByName("selectedId");
    	var selectedId;
        for (var x = 0; x < radioButtons.length; x ++) {
	        if (radioButtons[x].checked) {
	            selectedId = radioButtons[x].value;
	        }
        }
 
    	$.post("<%=request.getContextPath()%>/storeportal/itemCategories.do?action="+action+"&command=Confirm Move Category"+"&selectedId="+selectedId,
			$("#repParams").serialize(),
		    function(response, ioArgs) { 
	        try {
	        	var root = response.getElementsByTagName("response")[0];
				if (root != null && 'undefined' != typeof root) {
					var errorMessage = root.getAttribute("errorMessage");
					var warningMessage = root.getAttribute("warningMessage");
		            if (errorMessage)
		            	alert(errorMessage);
		            else if (warningMessage){
		            	if (confirm(warningMessage)){
		            		window.location="itemCategories.do?action="+action+"&command=";
		            	}
		            }else{
		            	window.location="itemCategories.do?action="+action+"&command=";
		            }
		        }
		        return false;
	        } catch(e) {
	        }
		}, 'xml');
    }
	-->
</script>
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


<html:form styleId="1444" action="/storeportal/itemCategories.do"
  scope="session" type="com.cleanwise.view.forms.StoreItemMgrForm" enctype="multipart/form-data">
 <% String action = theForm.getAction();
    Object  errorMessageObj =  request.getAttribute("org.apache.struts.action.ERROR");
    if( (errorMessageObj==null && "editCategory".equals(action)) ||
      (errorMessageObj!=null && "Delete Category".equals(action)) ||      
      "New Category".equals(action) ||
      "Save Category".equals(action)) 
       { %>
<% if(canEditFl) { %>
 <tr>
 	<td class="tableHeader">Category Name:</td>
 	<td colspan='3'>
    	<html:text size='50' name="STORE_ADMIN_ITEM_FORM" property="editCategoryName" />
	</td>
</tr>
<tr>
    <td class="tableHeader">Admin Category Name:</td>
    <td colspan='3'>
        <html:text size='50' name="STORE_ADMIN_ITEM_FORM" property="adminCategoryName"/>
    </td>
</tr>
<tr>
    <td colspan='4'>
        <html:submit styleClass='text' property="action" value="Save Category"/>
        <% if(theForm.getEditCategoryId()>0) { %>
        <html:submit styleClass='text' property="action" value="Delete Category"/>
        <!-- <html:submit styleClass='text' property="action" value="Move To" onclick="moveCategory('Move To');" /> -->
        
        <html:button property="action" onclick="moveCategory('Move To');" value="Move To"/>         
        <html:submit styleClass='text' property="action" value="To Top"/>
        <% } %>
    </td>
</tr>
<% } else { %>
 <tr>
 	<td class="tableHeader">Category Name:</td>
 	<td colspan='3'>
    	<html:text size='50' name="STORE_ADMIN_ITEM_FORM" property="editCategoryName" disabled="true"/>
	</td>
</tr>
<tr>
    <td class="tableHeader">Admin Category Name:</td>
    <td colspan='3'>
        <html:text size='50' name="STORE_ADMIN_ITEM_FORM" property="adminCategoryName"  disabled="true"/>
    </td>
</tr>

<% } %>
<tr><td colspan='4' bgcolor="#cccccc" class="tableHeader">&nbsp;</td></tr>
<% } %>
<tr>
 <td colspan='4'>
   <html:submit styleClass='text' property="action" value="New Category"/>
 </td>
</tr>

<!-- Store categories -->
    <tr>
       <td colspan='4' bgcolor="#cccccc" class="tableHeader">Store Categories</td>
     </tr>
    <tr>
       <td class="tableHeader">Category Id</td>
       <td class="tableHeader" colspan="3">Category</td>
     </tr>
    <logic:iterate id="category" name="STORE_ADMIN_ITEM_FORM" property="storeCategories"
    type="com.cleanwise.service.api.value.CatalogCategoryData"
    indexId="indId">
    <% if (( indId.intValue() % 2 ) == 0 ) { %> <tr class="rowa">
    <% } else { %> <tr class="rowb"> <% } %>
       <bean:define id="categoryId" name="category" property="itemData.itemId"/> 
       <bean:define id="lev" name="category" property="treeLevel"/>
       
    <% String categHref = new String("itemCategories.do?action=editCategory&categoryId=" + categoryId);
        %>
            <td><bean:write name="category" property="itemData.itemId"/></td>
            <td colspan="3">
                <% if (Integer.parseInt(lev.toString()) == 2) { %>  &nbsp; &nbsp; <% } %>
                <% if (Integer.parseInt(lev.toString()) == 3) { %>  &nbsp; &nbsp; &nbsp; &nbsp; <% } %>
                <% if (Integer.parseInt(lev.toString()) == 4) { %>  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <% } %>
                <html:radio name="STORE_ADMIN_ITEM_FORM" property="selectedId" value="<%=\"\"+categoryId%>"/>
                <html:link href="<%=categHref%>">
                    <bean:write name="category" property="itemData.shortDesc"/>
                    <logic:present name="category" property="itemData.longDesc">
                        (<bean:write name="category" property="itemData.longDesc"/>)
                    </logic:present>
                </html:link>
            </td>
        </tr>     
    </logic:iterate>
</html:form>
</table>
        




        

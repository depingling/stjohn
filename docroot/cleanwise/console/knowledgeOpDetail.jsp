<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.StringUtils" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.StringTokenizer" %>

<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="knowledgeId" name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeId" type="java.lang.Integer"/>
<bean:define id="categorycd" name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeCategoryCd" />

<%
	String action = (String)request.getParameter("action");
	if ( null == action ) {
		action = (String)request.getParameter("orgaction");
	}
	if (null == action) action = "add";
%>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Operation Console Home: Knowledge Base</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();

return false;
}

function popLocateSite(pLoc, name, pDesc) {
var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc + "&amp;accountid=" + document.forms[0].accountId.value;
locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();

return false;
}

function popOrder(pLoc) {
var loc = pLoc;
orderwin = window.open(loc,"Order", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500,width=780,left=50,top=50");
orderwin.focus();

return false;
}
//-->
</script>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/knowledgeOpToolbar.jsp"/>


<div class="text">
<font color=red>
<html:errors/>
</font>

<% 
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
  String role = appUser.getUser().getUserRoleCd(); 
  String crcRole = ""; 
  
  StringTokenizer st = new StringTokenizer(role, "^", false);
  while (st.hasMoreTokens()) {
    String permission = st.nextToken();

    if(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.APPROVER.equals(permission)  ||            
       RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.PUBLISHER.equals(permission) ||  
       RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.VIEWER.equals(permission)) {

      crcRole = permission;
    }    
  }
%>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<html:form name="KNOWLEDGE_OP_DETAIL_FORM" action="console/knowledgeOpDetail.do"
scope="session" type="com.cleanwise.view.forms.KnowledgeOpDetailForm">

<tr> 
	<td colspan="6" class="largeheader">Knowledge Base Detail
		<html:hidden property="change" value="" />
		<html:hidden property="orgaction" value="<%=action%>" />
	</td>	
</tr>	

<logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd" value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
<tr>
<td><b>Store&nbsp;Id:</b></td>
<td colspan="5">
    <html:text property="storeId" size="5"/>
    <span class="reqind">*</span>
    <html:button property="action" onclick="popLocate('../adminportal/storelocate', 'storeId');" value="Locate Store"/>
</td>
</tr>
</logic:equal>

<% if ("".equals(categorycd) && 0 == knowledgeId.intValue()) { %>
<tr>
	<td><b>Category</b></td>
	<td colspan="5">
		<html:select name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeCategoryCd"	
			onchange="document.forms[0].change.value='category'; document.forms[0].submit();">
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options  collection="Knowledge.category.vector" property="value" />				
		</html:select>
		<span class="reqind">*</span>	
	</td>
</tr>	

<% } else {  %>

<tr> 
	<td colspan="6"><hr></td>	
</tr>	

<%	if ( !"view".equals(action) ) {  %>

<tr>
	<td><b>Knowledge&nbsp;Id:</b></td>
	<td>
		<bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeId" scope="session"/>
		<html:hidden property="knowledgeDetail.knowledgeId"/>
	</td>
<%if(crcRole.equals(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.APPROVER)){%>
	<td><b>Status:</b></td>
	<td>
		<html:select name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeStatusCd">
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options  collection="Knowledge.status.vector" property="value" />				
		</html:select>
		<span class="reqind">*</span>
	</td>
<% }else { %>
        <td colspan="2">&nbsp;</td>
<% }  %>
	<td><b>Opened By:</b></td>
	<% if ("edit".equals(action) ) {  %>	
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.addBy" scope="session"/></td>	
	<% } else { %>	
	<td><%=session.getAttribute(Constants.USER_NAME)%></td>
	<% }  %>	
</tr>

<tr>
	<td><b>Category:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeCategoryCd" scope="session"/></td>
	<td colspan="4">&nbsp;</td>
</tr>

<tr> 
	<td colspan="6"><hr></td>	
</tr>	

	
	<% if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.PRODUCT_FEATURES_BENEFITS.equals(categorycd) ) {  %>

<tr>
	<td><b>Item Id:</b></td>
	<td colspan="2">
		<html:text name="KNOWLEDGE_OP_DETAIL_FORM" property="itemId" size="8" maxlength="30" />
		<html:button property="action" 
   			onclick="popLocate('../adminportal/itemlocate', 'itemId', '');"
   			value="Locate Item" styleClass="smallbutton"/>
	</td>
	<td>&nbsp;</td>
	<td><b>Product Name:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="productName" scope="session"/></td>	
</tr>	
	
<tr>
	<td><b>CW SKU:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemCwSku" scope="session"/></td>	
	<td><b>Manufacturer SKU:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemManufSku" scope="session"/></td>	
	<td><b>Manufacturer Name:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemManufName" scope="session"/></td>		
</tr>	

<tr>
	<td><b>Size:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemSize" scope="session"/></td>	
	<td><b>Pack:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemPack" scope="session"/></td>	
	<td><b>UOM:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemUom" scope="session"/></td>		
</tr>	

	<% } else if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.DISTRIBUTORS.equals(categorycd) ) {  %>
	
<tr>
	<td><b>Distributor Id:</b></td>
	<td colspan="2">
		<html:text name="KNOWLEDGE_OP_DETAIL_FORM" property="busEntityId" size="8" maxlength="30" />
		<span class="reqind" valign="top">*</span>
		<html:button property="action" 
   			onclick="popLocate('../adminportal/distlocate', 'busEntityId', '');"
   			value="Locate Distributor" styleClass="smallbutton"/>		
	</td>
	<td>&nbsp;</td>
	<td><b>Distributor Name:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.busEntity.shortDesc" scope="session"/></td>	
</tr>	
	
<tr>
	<td><b>Address 1:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.address1" scope="session"/></td>	
	<td><b>City:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.city" scope="session"/></td>	
	<td><b>Country:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.countryCd" scope="session"/></td>		
</tr>		

<tr>
	<td><b>Address 2:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.address2" scope="session"/></td>	
	<td><b>State/Province:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.stateProvinceCd" scope="session"/></td>	
	<td><b>Phone:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryPhone.phoneNum" scope="session"/></td>		
</tr>		
	
<tr>
	<td><b>Address 3:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.address3" scope="session"/></td>	
	<td><b>Zip/Postal Code:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.postalCode" scope="session"/></td>	
	<td><b>Fax:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryFax.phoneNum" scope="session"/></td>		
</tr>		

	<% } else if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.MANUFACTURERS.equals(categorycd) ) {  %>

<tr>
	<td><b>Manufacturer Id:</b></td>
	<td colspan="2">
		<html:text name="KNOWLEDGE_OP_DETAIL_FORM" property="busEntityId" size="8" maxlength="30" />
		<span class="reqind" valign="top">*</span>
		<html:button property="action" 
   			onclick="popLocate('../adminportal/manuflocate', 'busEntityId', '');"
   			value="Locate Manufacturer" styleClass="smallbutton"/>
	</td>
	<td>&nbsp;</td>
	<td><b>Manufacturer Name:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.busEntity.shortDesc" scope="session"/></td>	
</tr>	
	
<tr>
	<td><b>Address 1:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.address1" scope="session"/></td>	
	<td><b>City:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.city" scope="session"/></td>	
	<td><b>Country:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.countryCd" scope="session"/></td>		
</tr>		

<tr>
	<td><b>Address 2:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.address2" scope="session"/></td>	
	<td><b>State/Province:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.stateProvinceCd" scope="session"/></td>	
	<td><b>Phone:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryPhone.phoneNum" scope="session"/></td>		
</tr>		
	
<tr>
	<td><b>Address 3:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.address3" scope="session"/></td>	
	<td><b>Zip/Postal Code:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.postalCode" scope="session"/></td>	
	<td><b>Fax:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryFax.phoneNum" scope="session"/></td>		
</tr>			

	<% } // end of section according to the category Cd %>

<tr>
	<td><b>Description:</b></td>
	<td colspan="5">
		<html:text name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.longDesc" size="62" maxlength="255" />
		<span class="reqind">*</span>
	</td>		
</tr>	

<tr>
	<td><b>Date Created:</b></td>
	<% if ("edit".equals(action) ) {  %>	
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.addDate" scope="session"/></td>	
	<% } else { %>
	<td>
	<% Date currd = new Date(); %>
	<%= currd.toString() %>
	</td>
	<% }  %>	
	<td colspan="4">&nbsp;</td>
</tr>	

<tr><td colspan="6">&nbsp;</td></tr>

<tr>
	<td colspan="6"><b>COMMENTS</b></td>
</tr>
<tr>
	<td colspan="6">
		<html:textarea name="KNOWLEDGE_OP_DETAIL_FORM" cols="60" property="knowledgeDetail.comments"/>
		<span class="reqind" valign="top">*</span>		
	</td>
</tr>


<%  } else if ("view".equals(action)) {  %>

<tr>
	<td><b>Knowledge&nbsp;Id:</b></td>
	<td>
		<bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeId" scope="session"/>
		<html:hidden name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeId"/>
	</td>

<% if(crcRole.equals(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.APPROVER)){ %>
	<td><b>Status:</b></td>
	<td>
		<html:select name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeStatusCd">
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options  collection="Knowledge.status.vector" property="value" />				
		</html:select>		
	</td>
<% }else { %>
        <td><b>Status:</b></td>
        <td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeStatusCd" /></td>
<% }  %>
	<td><b>Created By:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.addBy" /></td>
</tr>

<tr>
	<td><b>Category:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeCategoryCd" scope="session"/></td>
	<td colspan="4">&nbsp;</td>
</tr>

<tr> 
	<td colspan="6"><hr></td>	
</tr>	
	
	<% if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.PRODUCT_FEATURES_BENEFITS.equals(categorycd) ) {  %>

<tr>
	<td><b>Item Id:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemId" /></td>	
	<td><b>Product Name:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="productName" scope="session"/></td>	
	<td colspan="2">&nbsp;</td>	
</tr>	
	
<tr>
	<td><b>CW SKU:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemCwSku" scope="session"/></td>	
	<td><b>Manufacturer SKU:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemManufSku" scope="session"/></td>	
	<td><b>Manufacturer Name:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemManufName" scope="session"/></td>		
</tr>	

<tr>
	<td><b>Size:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemSize" scope="session"/></td>	
	<td><b>Pack:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemPack" scope="session"/></td>	
	<td><b>UOM:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="itemUom" scope="session"/></td>		
</tr>	

	<% } else if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.DISTRIBUTORS.equals(categorycd) ) {  %>
	
<tr>
	<td><b>Distributor Id:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="busEntityId" /></td>
	<td><b>Distributor Name:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.busEntity.shortDesc" scope="session"/></td>	
	<td colspan="2">&nbsp;</td>	
</tr>	
	
<tr>
	<td><b>Address 1:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.address1" scope="session"/></td>	
	<td><b>City:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.city" scope="session"/></td>	
	<td><b>Country:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.countryCd" scope="session"/></td>		
</tr>		

<tr>
	<td><b>Address 2:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.address2" scope="session"/></td>	
	<td><b>State/Province:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.stateProvinceCd" scope="session"/></td>	
	<td><b>Phone:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryPhone.phoneNum" scope="session"/></td>		
</tr>		
	
<tr>
	<td><b>Address 3:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.address3" scope="session"/></td>	
	<td><b>Zip/Postal Code:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryAddress.postalCode" scope="session"/></td>	
	<td><b>Fax:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="distributorDetail.primaryFax.phoneNum" scope="session"/></td>		
</tr>		

	<% } else if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.MANUFACTURERS.equals(categorycd) ) {  %>

<tr>
	<td><b>Manufacturer Id:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="busEntityId" /></td>
	<td><b>Manufacturer Name:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.busEntity.shortDesc" scope="session"/></td>	
	<td colspan="2">&nbsp;</td>	
</tr>	
	
<tr>
	<td><b>Address 1:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.address1" scope="session"/></td>	
	<td><b>City:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.city" scope="session"/></td>	
	<td><b>Country:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.countryCd" scope="session"/></td>		
</tr>		

<tr>
	<td><b>Address 2:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.address2" scope="session"/></td>	
	<td><b>State/Province:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.stateProvinceCd" scope="session"/></td>	
	<td><b>Phone:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryPhone.phoneNum" scope="session"/></td>		
</tr>		
	
<tr>
	<td><b>Address 3:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.address3" scope="session"/></td>	
	<td><b>Zip/Postal Code:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryAddress.postalCode" scope="session"/></td>	
	<td><b>Fax:</b></td>
	<td><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="manufacturerDetail.primaryFax.phoneNum" scope="session"/></td>		
</tr>			

	<% } // end of section according to the category Cd %>


<tr><td colspan="6"><hr></td></tr>

<tr>
	<td><b>Description:</b></td>
	<td colspan="5"><bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.longDesc" /></td>
</tr>	

<tr>
	<td><b>Opened Date:</b></td>
	<td>
		<bean:define id="knowledgeAddDate" name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.addDate" />
		<bean:define id="knowledgeAddTime" name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.addTime" />
		<i18n:formatDate value="<%=knowledgeAddDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
		&nbsp;<i18n:formatDate value="<%=knowledgeAddTime%>" pattern="h:mm a" locale="<%=Locale.US%>"/>		
	</td>	
	<td colspan="4">&nbsp;</td>
</tr>	

<tr><td colspan="6">&nbsp;</td></tr>

<tr>
	<td colspan="6"><b>COMMENTS</b></td>
</tr>
<tr>
	<td colspan="6">
		<bean:define id="comments" name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.comments" type="java.lang.String"/>
		<% 	String newComments = new String("");
			if (null != comments && ! "".equals(comments)) {
				newComments = StringUtils.replaceString(comments, "\n", "<br>");	
			}
		%>	
		<%=newComments%>
	</td>
</tr>

<%  }  // end of if action ==  "view" %>

<tr><td colspan="6"><hr></td></tr>

<tr>
	<td colspan="6"><b>NOTES</b></td>
</tr>

<tr>
	<td colspan="2"><b>Note Date</b></td>
	<td colspan="3"><b>Note Description</b></td>
	<td><b>Note By</b></td>
</tr>

<bean:size id="noteNumber" name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeNotesList" />
<logic:iterate id="knowledgeNote" name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeNotesList"
     offset="0" length="<%=noteNumber.toString()%>" type="com.cleanwise.service.api.value.KnowledgePropertyData"> 
 <bean:define id="noteId"  name="knowledgeNote" property="knowledgePropertyId"/>
 <bean:define id="addDate" name="knowledgeNote" property="addDate"/>
 <bean:define id="addTime" name="knowledgeNote" property="addTime"/>
 <% String linkHref = new String("knowledgeOpNoteDetail.do?action=view&id=" + noteId + "&knowledgeid=" + knowledgeId);%>

 <tr>
 	<td colspan="2">
		<i18n:formatDate value="<%=addDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
		&nbsp;<i18n:formatDate value="<%=addTime%>" pattern="h:mm a" locale="<%=Locale.US%>"/>
	</td>
	<td colspan="3"><a href="<%=linkHref%>"><bean:write name="knowledgeNote" property="shortDesc"/></a></td> 
	<td><bean:write name="knowledgeNote" property="addBy"/></td>
 </tr>
 </logic:iterate>	 

<tr>
	<td colspan="5">&nbsp;</td>
	<td>
		<html:submit property="action">
			<app:storeMessage  key="admin.button.addNote"/>
		</html:submit>		
	</td>
</tr>

 <tr><td colspan="6"><hr></td></tr>

<tr>
	<td colspan="6"><b>CONTENT</b></td>
</tr>

<tr>
	<td colspan="2"><b>Content Date</b></td>
	<td colspan="3"><b>Content Description</b></td>
	<td><b>Content By</b></td>
</tr>

<bean:size id="contentNumber" name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeContentsList" />
<logic:iterate id="knowledgeContent" name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeContentsList"
     offset="0" length="<%=contentNumber.toString()%>" type="com.cleanwise.service.api.value.KnowledgeContentData"> 
 <bean:define id="contentId"  name="knowledgeContent" property="knowledgeContentId"/>
 <bean:define id="addDate" name="knowledgeContent" property="addDate"/>
 <bean:define id="addTime" name="knowledgeContent" property="addTime"/>
 <% String linkHref = new String("knowledgeOpContentDetail.do?action=view&id=" + contentId + "&knowledgeid=" + knowledgeId);%>

 <tr>
 	<td colspan="2">
		<i18n:formatDate value="<%=addDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
		&nbsp;<i18n:formatDate value="<%=addTime%>" pattern="h:mm a" locale="<%=Locale.US%>"/>
	</td>
	<td colspan="3"><a href="<%=linkHref%>"><bean:write name="knowledgeContent" property="longDesc"/></a></td> 
	<td><bean:write name="knowledgeContent" property="addBy"/></td>
 </tr>
 </logic:iterate>	 

<tr>
	<td colspan="5">&nbsp;</td>
	<td>
		<html:submit property="action">
			<app:storeMessage  key="admin.button.addContent"/>
		</html:submit>		
	</td>
</tr>

<tr><td colspan="6"><hr></td></tr>
 
<tr>
<td colspan="6" align="center">

<html:submit property="action">
	<app:storeMessage  key="global.action.label.save"/>
</html:submit>
<html:reset>
	<app:storeMessage  key="admin.button.reset"/>
</html:reset>

<html:submit property="action">
	<app:storeMessage  key="admin.button.email"/>
</html:submit>
<html:submit property="action">
	<app:storeMessage  key="admin.button.fax"/>
</html:submit>

<html:submit property="action">
	<app:storeMessage  key="global.action.label.print"/>
</html:submit>

<html:submit property="action">
	<app:storeMessage  key="admin.button.back"/>
</html:submit>
</td>
</tr>

<% } // end of if the category Cd is selected  %>

</html:form>

</table>
</div>

<jsp:include flush='true' page="ui/consoleFooter.jsp"/>
</body>

</html:html>







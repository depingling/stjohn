<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.StringUtils" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>

<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="knowledgeId" name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeId" type="java.lang.Integer"/>
<bean:define id="categorycd" name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeCategoryCd" />

<%
	String action = (String)request.getParameter("action");
	
%>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Operation Console Home: Knowledge Base</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script language="JavaScript1.2">
<!--
function popContent(pLoc) {
var loc = "/<%=storeDir%>/" + pLoc;
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

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<html:form name="KNOWLEDGE_OP_CONTENT_DETAIL_FORM" action="console/knowledgeOpContentDetail.do"
enctype="multipart/form-data" scope="session" type="com.cleanwise.view.forms.KnowledgeOpDetailForm">

<tr> 
	<td colspan="6" class="largeheader">Knowledge Content Detail</td>	
</tr>	

<tr><td colspan="6"><hr></td></tr>

<tr>
	<td><b>Knowledge&nbsp;Id:</b></td>
	<td>
		<bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeId" scope="session"/>
		<html:hidden name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeId"/>
	</td>
	<td><b>Status:</b></td>
	<td>
		<bean:write name="KNOWLEDGE_OP_DETAIL_FORM" property="knowledgeDetail.knowledgeStatusCd" />
	</td>
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

<tr><td colspan="6"><hr></td></tr>

<tr>
	<td><b>Content Description:</b></td>

<% if (! "view".equals(action) ) {  %>	
	<td colspan="5">
		<html:text name="KNOWLEDGE_OP_CONTENT_DETAIL_FORM" property="contentDetail.longDesc" size="60" maxlength="255" />
		<span class="reqind">*</span>
	</td>
<%  } else if ("view".equals(action)) {  %>
	<td colspan="5"><bean:write name="KNOWLEDGE_OP_CONTENT_DETAIL_FORM" property="contentDetail.longDesc" /></td>
<% }  %>	
</tr>	

<tr>
	<td><b>Content Date:</b></td>
<% if ( "edit".equals(action) ||  "view".equals(action)) {  %>	
	<td colspan="2">
		<bean:define id="addDate" name="KNOWLEDGE_OP_CONTENT_DETAIL_FORM" property="contentDetail.addDate" />
		<bean:define id="addTime" name="KNOWLEDGE_OP_CONTENT_DETAIL_FORM" property="contentDetail.addTime" />
		<i18n:formatDate value="<%=addDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
		&nbsp;<i18n:formatDate value="<%=addTime%>" pattern="h:mm a" locale="<%=Locale.US%>"/>		
	</td>	
<% } else { %>
	<td>
	<% Date currd = new Date(); %>
	<%= currd.toString() %>
	</td>
<% }  %>	
	<td><b>Content By:</b></td>
<% if ("edit".equals(action) ) {  %>	
	<td><bean:write name="KNOWLEDGE_OP_CONTENT_DETAIL_FORM" property="contentDetail.addBy" scope="session"/></td>	
<% } else { %>	
	<td><%=session.getAttribute(Constants.USER_NAME)%></td>
<% }  %>	
	<td>&nbsp;</td>
</tr>	

<tr>
	<td><b>Path URL:</b></td>
	<td colspan="5">
   	     <html:file name="KNOWLEDGE_OP_CONTENT_DETAIL_FORM" property="contentFile"
	      accept="application/pdf,application/html"/>
	</td>		  
</tr>

<logic:present name="KNOWLEDGE_OP_CONTENT_DETAIL_FORM" property="contentDetail.contentUrl" >
<bean:define id="contentUrl" name="KNOWLEDGE_OP_CONTENT_DETAIL_FORM" property="contentDetail.contentUrl"/>
<% if ("edit".equals(action) || "view".equals(action)) {  %>		
<tr>
	<td>&nbsp;</td>
	<td colspan="5">
		<input type="button"  value="Display" onclick="return popContent('<%=contentUrl%>');" >
	</td>
</tr>
<% }  %>
</logic:present>

<tr><td colspan="6"><hr></td></tr>

<tr>
<td colspan="6" align="center">

<html:submit property="action">
	<app:storeMessage  key="global.action.label.save"/>
</html:submit>
<% if ( ! "view".equals(action)  ) {  %>	
<html:reset>
	<app:storeMessage  key="admin.button.reset"/>
</html:reset>
<% }  %>
<html:submit property="action">
	<app:storeMessage  key="admin.button.back"/>
</html:submit>
</td>
</tr>
</html:form>

</table>
</div>

<jsp:include flush='true' page="ui/consoleFooter.jsp"/>
</body>

</html:html>







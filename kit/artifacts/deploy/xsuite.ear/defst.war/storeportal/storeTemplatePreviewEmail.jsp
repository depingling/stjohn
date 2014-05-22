<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.util.Map" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<app:checkLogon/>
<% 
	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
%>

<bean:define id="myForm" name="STORE_TEMPLATE_FORM" type="com.cleanwise.view.forms.StoreTemplateForm"/>

<script type="text/javascript" language="JavaScript">
<!--
	function closeWindow() {
		window.open('','_self','');
		window.close();
		return true;
	}
//-->
</script>

<html:html>

<head>
	<title>
		<tiles:getAsString name="title"/>
	</title>
  	<!-- meta http-equiv="Content-Type" content="text/html; charset=UTF-8" -->
  	<meta http-equiv="Pragma" content="no-cache">
  	<meta http-equiv="Expires" content="-1">
  <script src="../externals/lib.js" language="javascript"></script>
  <script src="../externals/table-sort.js" language="javascript"></script>
  <logic:present name="pages.css">
      <logic:equal name="pages.css" value="styles.css">
        <link rel="stylesheet" href='../externals/<app:custom pageElement="pages.css"/>'>
      </logic:equal>
    <logic:notEqual name="pages.css" value="styles.css">
      <link rel="stylesheet" href='../externals/styles.css'>
      <link rel="stylesheet" href='../externals/<app:custom pageElement="pages.css"/>'>
    </logic:notEqual>
  </logic:present>
  <logic:notPresent name="pages.css">
    <link rel="stylesheet" href='../externals/styles.css'>
  </logic:notPresent>
</head>

<body>
<%--render the image--%>
	<table ID=523 border="0" cellpadding="0" cellspacing="0" width="100%" style="border-collapse: collapse;">
  		<tr valign="top">
    		<td align="left" valign="middle">
 				<img ID=524 src='<app:custom pageElement="pages.store.logo1.image" addImagePath="true" encodeForHTML="true"/>'
 					 border="0">
 				<BR>
				<%=appUser.getUserStore().getBusEntity().getShortDesc()%>
			</td>
    		<td align="right" colspan="4">
      			<% java.util.Date currd = new java.util.Date(); %>
      			<%= currd.toString() %>
      			<br>
      			Server: <%=java.net.InetAddress.getLocalHost()%>
    		</td>
		</tr>
	</table>

<%--display any errors if present--%>
	<table ID=526 width="<%=Constants.TABLEWIDTH%>">
  		<tr>
    		<td align="center">
      			<div id="adminLayoutErrorSection" class="text">
      				<font color=red>
      					<html:errors/>
      				</font>
      			</div>
    		</td>
  		</tr>
	</table>

<html:form styleId="1036" action="storeportal/storeTemplate.do">
	<div class="text">
		<html:hidden property="templateData.type"/>
		<% 
			StringBuilder property = new StringBuilder(100);
			property.append("templateData.property(");
			property.append(Constants.TEMPLATE_EMAIL_PROPERTY_SUBJECT);
			property.append(").value");
		%>
		<html:hidden property="<%=property.toString()%>"/>
		<html:hidden property="templateData.content"/>
		<table ID=1035 cellspacing="0" border="0" width="100%" class="mainbody">
			<tr>
				<td class="largeheader" align="center" colspan="4">Template Preview</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td>
					<b>Order&nbsp;Number&nbsp;or&nbsp;Id:&nbsp;</b><span class="reqind">*</span>&nbsp;
				</td>
				<td align="left" colspan="3">
					<html:text property="id"/>
				</td>
			</tr>
			<tr>
				<td>
					<b>Recipient&nbsp;Locale:&nbsp;</b><span class="reqind">*</span>&nbsp;
				</td>
				<td align="left" colspan="3">
				<% 
					property = new StringBuilder(100);
					property.append("templateData.property(");
					property.append(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE);
					property.append(").value");
				%>
					<html:select property="<%=property.toString()%>">
						<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
						<html:options collection="<%=Constants.ATTRIBUTE_TEMPLATE_FULL_LOCALES_VECTOR%>" property="value"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
    			<td colspan="4" align="center">
      				<html:submit property="userAction">
        				<app:storeMessage  key="button.template.showOutput"/>
      				</html:submit>
					<html:button property="cancel" onclick="closeWindow();">
						<app:storeMessage  key="global.action.label.cancel"/>
					</html:button>
    			</td>
			</tr>
			<%
				String subject = myForm.getTemplateOutputStringValue(Constants.TEMPLATE_OUTPUT_EMAIL_SUBJECT);
				String message = myForm.getTemplateOutputStringValue(Constants.TEMPLATE_OUTPUT_EMAIL_BODY);
				boolean showOutput = (Utility.isSet(subject) || Utility.isSet(message));
				if (showOutput) {
			%>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class="largeheader" align="center" colspan="4">Template Output</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td align="left" colspan="4">
					<b>Email&nbsp;Subject:&nbsp;</b>
				</td>
			</tr>
			<tr>
				<td align="left" colspan="4">
				<%
					//because the struts tags didn't handle all special characters (i.e. the Euro symbol)
					//use regular html markup language here
				%>
					<input type="text" size="100" readonly="true" value="<%=Utility.encodeForHTMLAttribute(subject)%>">
				</td>
			</tr>
			<tr>
				<td class="largeheader" colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td align="left" colspan="4">
					<b>Email&nbsp;Body:&nbsp;</b>
				</td>
			</tr>
			<tr>
				<td align="left" colspan="4">
				<%
					//because the struts tags didn't handle all special characters (i.e. the Euro symbol)
					//use regular html markup language here
				%>
					<textarea cols="80" rows="20" readonly="true"><%=Utility.encodeForHTML(message)%></textarea>
				</td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
</html:form>
</body>
</html:html>

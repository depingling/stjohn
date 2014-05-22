<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="myForm" name="STORE_TEMPLATE_FORM" type="com.cleanwise.view.forms.StoreTemplateForm"/>
<% 
	StringBuilder property = new StringBuilder(100);
	property.append("templateData.property(");
	property.append(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE);
	property.append(").value");
	String localeProperty = property.toString();
	property = new StringBuilder(100);
	property.append("templateData.property(");
	property.append(Constants.TEMPLATE_EMAIL_PROPERTY_SUBJECT);
	property.append(").value");
	String subjectProperty = property.toString();
%>

<script type="text/javascript" language="JavaScript">
<!--
	var windowName = null;
	var validationPassed = true;

	//function to trim a string
	function trim(stringToTrim) {
		return stringToTrim.replace(/^\s+|\s+$/g,"");
	}

	//function to determine the destination of the form submission
	function determineTargetWindow() {
		var returnValue = true;
		if (windowName != null) {
			if (validationPassed) {
		    	document.forms[0].target = windowName;
		    	var win = window.open("", windowName, "width=700, height=650, resizable=1");
		    	win.focus();
			}
			returnValue = validationPassed;
		    windowName = null;
		}
		else {
		    document.forms[0].target = "_self";
		}
		return returnValue;
	}

	//Function to handle click of Preview button.
	//Any data validation must be done here, because we open a new window to show the
	//results.  If data validation is done on the server any errors will appear in the
	//new window, not the main window.
	function onPreviewButtonClick() {
		windowName='previewWindow';
		validationPassed = true;
		//template must have a body specified
		var content = document.forms[0].elements['templateData.content'].value;
		if (content == null || trim(content) == "") {
			alert('Email body must be specified before previewing.');
			validationPassed = false;
		} 
		//template must have a locale specified
		var locale = document.forms[0].elements['<%=localeProperty%>'].value;
		if (locale == null || trim(locale) == "") {
			alert('Locale/language must be specified before previewing.');
			validationPassed = false;
		} 
	}

	//function to insert sample template text into the template text area
	function addSampleText() {
		var newContent = document.forms[0].elements['templateData.content'].value;
		<%
			String sampleContent = myForm.getSampleContent();
			if (sampleContent != null && sampleContent.length() > 0) {
				String[] lines = sampleContent.split("\r?\n|\r");
				for (int i = 0; i<lines.length; i++) {
					
		%>
		newContent = newContent + '<%=lines[i]%>' + '\r\n';
		<%		
				}
			}
		%>
		document.forms[0].elements['templateData.content'].value = newContent;
		return false;
	}
//-->
</script>
<html:form styleId="1036" action="storeportal/storeTemplate.do" onsubmit="return determineTargetWindow();">
	<div class="text">
		<html:hidden property="templateData.type"/>
		<table ID=1035 cellspacing="0" border="0" width="769"  class="mainbody">
			<tr>
				<td class="largeheader" colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td>
					<b>Store&nbsp;Id:&nbsp;</b>
				</td>
				<td>
					<bean:write name="STORE_TEMPLATE_FORM" property="templateData.busEntityId"/>
					<html:hidden property="templateData.busEntityId"/>
				</td>
				<td align="right">
					<b>Store&nbsp;Name:&nbsp;</b>
				</td>
				<td>
					<bean:write name="STORE_TEMPLATE_FORM" property="templateData.busEntityName"/>
				</td>
			</tr>
			<tr>
				<td>
					<b>Template&nbsp;Id:&nbsp;</b>
				</td>
				<td colspan="3">
					<bean:write name="STORE_TEMPLATE_FORM" property="templateData.templateId"/>
					<html:hidden property="templateData.templateId"/>
				</td>
			</tr>
			<tr>
				<td>
					<b>Template&nbsp;Name:&nbsp;</b><span class="reqind">*</span>&nbsp;
				</td>
				<td colspan="3">
					<html:text property="templateData.name" 
						size="<%=new Integer(Constants.TEMPLATE_MAX_SIZE_NAME).toString()%>" 
						maxlength="<%=new Integer(Constants.TEMPLATE_MAX_SIZE_NAME).toString() %>"/>
				</td>
			</tr>
			<tr>
				<td>
					<b>Locale/Language:&nbsp;</b><span class="reqind">*</span>&nbsp;
				</td>
				<td colspan="3">
					<html:select property="<%=localeProperty%>">
						<html:option value="">Select</html:option>
						<html:options collection="<%=Constants.ATTRIBUTE_TEMPLATE_LOCALES_VECTOR%>" property="value"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>
					<b>Email&nbsp;Subject:&nbsp;</b>
				</td>
				<td colspan="3">
					<html:text property="<%=subjectProperty%>" 
						size="100" 
						maxlength="<%=new Integer(Constants.TEMPLATE_MAX_SIZE_EMAIL_SUBJECT).toString()%>"/>
				</td>
			</tr>
			<tr>
				<td>
					<b>Email&nbsp;Body:&nbsp;</b><span class="reqind">*</span>&nbsp;
				</td>
				<td colspan="3">
					<html:textarea property="templateData.content" 
						cols="80" rows="20"/>
				</td>
			</tr>
			<tr>
    			<td colspan="4" align="center">
					<html:button property="insertText" onclick="addSampleText();">
						<app:storeMessage  key="button.template.insertSampleText"/>
					</html:button>
      				<html:submit property="userAction">
        				<app:storeMessage  key="global.action.label.save"/>
      				</html:submit>
	      			<html:reset>
    	    			<app:storeMessage  key="admin.button.reset"/>
      				</html:reset>
    				<logic:notEqual name="STORE_TEMPLATE_FORM" property="templateData.templateId" value="0">
      					<html:submit property="userAction">
        					<app:storeMessage  key="global.action.label.delete"/>
      					</html:submit>
	      				<html:submit property="userAction">
    	    				<app:storeMessage  key="admin.button.createClone"/>
      					</html:submit>
    				</logic:notEqual>
	      			<html:submit property="userAction" onclick="onPreviewButtonClick();">
    	    			<app:storeMessage  key="admin.button.preview"/>
      				</html:submit>
    			</td>
			</tr>
		</table>
	</div>
</html:form>
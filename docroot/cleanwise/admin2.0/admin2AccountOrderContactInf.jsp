<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/app-interface-config.tld" prefix="ui" %>

<%String configParam = request.getParameter("configMode");%>
<%String formName = request.getParameter("formName");%>

<ui:page name="<%=RefCodeNames.UI_PAGE_CD.ACCOUNT_DETAIL%>" 
		 type="<%=RefCodeNames.UI_PAGE_TYPE_CD.ACCOUNT%>" 
		 bean="<%=formName%>" 
		 property="uiPage" 
		 configMode="<%=configParam%>">

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ORDER_CONTACT_INF %>" 
			targets="ACCOUNT_ACCOUNT_ORDER_CONTACT_INFO">

<tr>
    <td colspan=4 class="largeheader"><app:storeMessage key="admin2.account.detail.text.orderContactInformation"/></td>
</tr>
			
<tr>
<td colspan="4" id="ACCOUNT_ACCOUNT_ORDER_CONTACT_INFO">
		<table border="0" cellpadding="0" cellspacing="2" width="100%">
			<tr>
			<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_PHONE_LABEL%>" width="150px">
				<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_PHONE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
			    <b><ui:label><app:storeMessage key="admin2.account.detail.label.orderPhone"/>:</ui:label></b><span class="reqind">*</span>
	    		</ui:element>
            </td>
             <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_PHONE_VALUE%>" align="left" >
				<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_PHONE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
					<ui:text name="<%=formName%>" property="orderPhone" maxlength="15"/>
		        </ui:element>
			</td>
			</tr>
			<tr>
			<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_FAX_LABEL%>" width="150px">
				<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_FAX_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
			            <b><ui:label><app:storeMessage key="admin2.account.detail.label.orderFax"/>:</ui:label></b><span class="reqind">*</span>
			    </ui:element>
            </td>
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_FAX_VALUE%>" align="left">
				<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_FAX_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
						<ui:text name="<%=formName%>" property="orderFax" maxlength="15"/>
		        </ui:element>
			</td>
			</tr>
		</table>
	</td>
</tr>
</ui:control>

</ui:page>
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

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_BILLING_ADDRESS_INF %>" 
			targets="ACCOUNT_BILLING_ADDRESS_INFO">

<tr>
    <td colspan="4" class="largeheader"><br>
        <app:storeMessage key="admin2.account.detail.text.billingAddress"/>
    </td>
</tr>

<td colspan="4" id="ACCOUNT_BILLING_ADDRESS_INFO">
	<table border="0" cellpadding="0" cellspacing="1" width="100%">

    <tr height="0px">
        <td width="150px"></td>
        <td width="250px"></td>
        <td width="150px"></td>
        <td></td>
    </tr>
        
        <tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ADDRESS1_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ADDRESS1_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.billingAddress1"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ADDRESS1_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ADDRESS1_VALUE%>">
                    <ui:text name="<%=formName%>" property="billingAddress1" maxlength="80" size="40"/>
            </td>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_COUNTRY_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.account.detail.label.billingCountry"/>:</ui:label></b><span class="reqind">*</span></td>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_COUNTRY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
                <td>
                    <ui:select name="<%=formName%>" property="billingCountry">
                        <html:option value="">
                            <app:storeMessage  key="admin2.select.country"/>
                        </html:option>
                        <html:options collection="admin2.country.vector" labelProperty="uiName" property="shortDesc"/>
                    </ui:select>
                </td>
            </ui:element>
</tr>
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ADDRESS2_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ADDRESS2_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.billingAddress2"/>:</ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ADDRESS2_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ADDRESS2_VALUE%>">
                    <ui:text name="<%=formName%>" property="billingAddress2" maxlength="80" size="40"/>
            </td>
        </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_STATE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STATE_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.billingState"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_STATE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_STATE_VALUE%>">
                    <ui:text name="<%=formName%>" property="billingState" size="20" maxlength="80"/>
            </td>
        </ui:element>
</tr>
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ADDRESS3_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ADDRESS3_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.billingAddress3"/>:</ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ADDRESS3_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ADDRESS3_VALUE%>">
                    <ui:text name="<%=formName%>" property="billingAddress3" maxlength="80" size="40"/>
            </td>
        </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ZIP_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ZIP_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.billingPostalCode"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ZIP_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BILLING_ZIP_VALUE%>">
                    <ui:text name="<%=formName%>" property="billingPostalCode" maxlength="15"/>
            </td>
        </ui:element>
</tr>
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CITY_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CITY_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.billingCity"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CITY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CITY_VALUE%>">
                    <ui:text name="<%=formName%>" property="billingCity" maxlength="40"/>
            </td>
        </ui:element>
</tr>
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PURCHASE_ORDER_NAME_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PURCHASE_ORDER_NAME_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.purchaseOrderAccountName"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
	    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PURCHASE_ORDER_NAME_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PURCHASE_ORDER_NAME_VALUE%>">
                    <ui:text name="<%=formName%>" property="purchaseOrderAccountName" maxlength="40"/>
            </td>
        </ui:element>
</tr>
		
</table>
</td>



</ui:control>

</ui:page>
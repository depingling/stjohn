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

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_PRIMARY_CONTACT_INF %>" 
			targets="ACCOUNT_PRIMARY_CONTACT_INFO">

<tr>
    <td colspan=4 class="largeheader"><br><app:storeMessage key="admin2.account.detail.text.primaryContact"/></td>
</tr>
			
<tr>
<td colspan="4" id="ACCOUNT_PRIMARY_CONTACT_INFO">
<table border="0" cellpadding="0" cellspacing="1" width="100%">

<tr height="0px">
    <td width="150px"></td>
    <td width="250px"></td>
    <td width="150px"></td>
    <td></td>
</tr>

    <tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FIRST_NAME_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FIRST_NAME_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.name1"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FIRST_NAME_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FIRST_NAME_VALUE%>">
                    <ui:text name="<%=formName%>" property="name1" maxlength="40" size="40"/>
            </td>
        </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PHONE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PHONE_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.phone"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PHONE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PHONE_VALUE%>">
                 <ui:text name="<%=formName%>" property="phone" maxlength="15"/>
            </td>
        </ui:element>
</tr>
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_LAST_NAME_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_LAST_NAME_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.name2"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_LAST_NAME_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_LAST_NAME_VALUE%>">
				<ui:text name="<%=formName%>" property="name2" maxlength="40" size="40"/>
            </td>
        </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FAX_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FAX_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.fax"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FAX_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FAX_VALUE%>">
                 <ui:text name="<%=formName%>" property="fax" maxlength="15"/>
            </td>
        </ui:element>
</tr>
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FAX_BACK_CONFIRM_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FAX_BACK_CONFIRM_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.faxBackConfirm"/>:</ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FAX_BACK_CONFIRM_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FAX_BACK_CONFIRM_VALUE%>">
                    <ui:text name="<%=formName%>" property="faxBackConfirm" maxlength="15"/>
            </td>
        </ui:element>
</tr>
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STREET_ADDRESS1_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STREET_ADDRESS1_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.streetAddr1"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STREET_ADDRESS1_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STREET_ADDRESS1_VALUE%>">
                    <ui:text name="<%=formName%>" property="streetAddr1" maxlength="80" size="40"/>
            </td>
        </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_EMAIL_ADDRESS_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_EMAIL_ADDRESS_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.emailAddress"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_EMAIL_ADDRESS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_EMAIL_ADDRESS_VALUE%>">
                    <ui:text name="<%=formName%>" property="emailAddress" maxlength="40"/>
            </td>
        </ui:element>
</tr>
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STREET_ADDRESS2_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STREET_ADDRESS2_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.streetAddr2"/>:</ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STREET_ADDRESS2_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STREET_ADDRESS2_VALUE%>">
                    <ui:text name="<%=formName%>" property="streetAddr2" maxlength="80" size="40"/>
            </td>
        </ui:element>
	    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_COUNTRY_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.account.detail.label.country"/>:</ui:label></b><span class="reqind">*</span></td>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_COUNTRY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
			<td>
				<ui:select name="<%=formName%>" property="country">
					<html:option value=""><app:storeMessage  key="admin2.select.country"/></html:option>
					<html:options collection="admin2.country.vector" labelProperty="uiName" property="shortDesc"/>
				</ui:select>
			</td>
		</ui:element>
</tr>
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STREET_ADDRESS3_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STREET_ADDRESS3_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.streetAddr3"/>:</ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STREET_ADDRESS3_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STREET_ADDRESS3_VALUE%>">
                    <ui:text name="<%=formName%>" property="streetAddr3" maxlength="80" size="40"/>
            </td>
        </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STATE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STATE_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.stateOrProv"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STATE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_STATE_VALUE%>">
                    <ui:text name="<%=formName%>" property="stateOrProv" size="20" maxlength="80"/>
            </td>
        </ui:element>
</tr>
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CITY_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CITY_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.city"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CITY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CITY_VALUE%>">
                <ui:text name="<%=formName%>" property="city" maxlength="40"/>
            </td>
        </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ZIP_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ZIP_VALUE%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.zip"/>:</ui:label></b><span class="reqind">*</span>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ZIP_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ZIP_VALUE%>">
                    <ui:text name="<%=formName%>" property="postalCode" maxlength="15"/>
            </td>
        </ui:element>
</tr>
</table>
</td>
</tr>
</ui:control>

</ui:page>
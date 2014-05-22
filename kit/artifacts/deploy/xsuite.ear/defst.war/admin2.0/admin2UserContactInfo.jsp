<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/app-interface-config.tld" prefix="ui" %>

<%String formName = Utility.strNN(request.getParameter("formName"));%>
<%String configParam = request.getParameter("configMode");%>

<ui:page name="<%=RefCodeNames.UI_PAGE_CD.USER_DETAIL%>" type="<%=RefCodeNames.UI_PAGE_TYPE_CD.USER%>" bean="<%=formName%>" property="uiPage" configMode="<%=configParam%>">

<ui:control name="<%=RefCodeNames.UI_CONTROL.USER_CONTACT_INFO%>" targets="USER_CONTACT_INFO_PART1,USER_CONTACT_INFO_PART2">
<tr>
    <td colspan="4" class="largeheader"><br>
        <app:storeMessage key="admin2.user.detail.text.contactInformation"/>
    </td>
</tr>
<tr>
<td colspan="2" id="USER_CONTACT_INFO_PART1">

    <table border="0" cellpadding="0" cellspacing="1" width="100%">
        <tr>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_FIRST_NAME_LABEL%>"  type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.userFirstName"/>:</ui:label></b><span class="reqind">*</span></td>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_FIRST_NAME_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <td><ui:text name="<%=formName%>" property="detail.userData.firstName" maxlength="40" size="40"/></td>
            </ui:element>
        </tr>
        <tr>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_LAST_NAME_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.userLastName"/>:</ui:label></b><span class="reqind">*</span></td>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_LAST_NAME_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <td><ui:text name="<%=formName%>" property="detail.userData.lastName" maxlength="40" size="40"/></td>
            </ui:element>
        </tr>
        <tr>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS1_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.userStreetAddress1"/>:</ui:label>
                </b><span class="reqind">*</span></td>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS1_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <td><ui:text name="<%=formName%>" property="detail.addressData.address1" maxlength="80" size="40"/></td>
            </ui:element>
        </tr>
        <tr>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS2_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.userStreetAddress2"/>:</ui:label></b></td>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS2_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <td><ui:text name="<%=formName%>" property="detail.addressData.address2" maxlength="80" size="40"/></td>
            </ui:element>
        </tr>
        <tr>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_CITY_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.userCity"/>:</ui:label>
                </b><span class="reqind">*</span></td>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_CITY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <td><ui:text name="<%=formName%>" property="detail.addressData.city" maxlength="40"/></td>
            </ui:element>
        </tr>
        <tr>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STATE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.userState"/>:</ui:label>
                </b><span class="reqind">*</span></td>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STATE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <td><ui:text name="<%=formName%>" property="detail.addressData.stateProvinceCd" size="20" maxlength="80"/></td>
            </ui:element>
        </tr>
        <tr>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_ZIP_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.userZip"/>:</ui:label>
                </b><span class="reqind">*</span></td>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_ZIP_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <td><ui:text name="<%=formName%>" property="detail.addressData.postalCode" maxlength="15"/></td>
            </ui:element>
        </tr>
        <tr>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_COUNTRY_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.userCountry"/>:</ui:label></b><span class="reqind">*</span></td>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_COUNTRY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
                <td>
                    <ui:select name="<%=formName%>" property="detail.addressData.countryCd">
                        <html:option value="">
                            <app:storeMessage  key="admin2.select.country"/>
                        </html:option>
                        <html:options collection="admin2.country.vector" labelProperty="uiName" property="shortDesc"/>
                    </ui:select>
                </td>
            </ui:element>
        </tr>

    </table>


</td>

<td colspan="2" valign="top" id="USER_CONTACT_INFO_PART2">

    <table border="0" cellpadding="0" cellspacing="1" width="100%">
        <tr>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.PHONE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.userPhone"/>:</ui:label>
                </b><span class="reqind">*</span></td>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.PHONE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <td><ui:text name="<%=formName%>" property="detail.phone.phoneNum" maxlength="30"/></td>
            </ui:element>
        </tr>
        <tr>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.FAX_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.userFax"/>:</ui:label></b></td>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.FAX_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <td><ui:text name="<%=formName%>" property="detail.fax.phoneNum" maxlength="30"/></td>
            </ui:element>
        </tr>
        <tr>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.MOBILE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.userMobile"/>:</ui:label></b></td>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.MOBILE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <td><ui:text name="<%=formName%>" property="detail.mobile.phoneNum" maxlength="30"/></td>
            </ui:element>
        </tr>
        <tr>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.EMAIL_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.userEmail"/>:</ui:label></b></td>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.EMAIL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <td><ui:text name="<%=formName%>" property="detail.emailData.emailAddress" maxlength="255" size="40"/></td>
            </ui:element>
        </tr>
    </table>

</td>
</tr>
</ui:control>
</ui:page>
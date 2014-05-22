<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.value.BusEntityData" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.Admin2Tool" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/app-interface-config.tld" prefix="ui" %>

<%String configParam = request.getParameter("configMode");%>
<%String formName = request.getParameter("formName");%>
<%String messageKey;%>

<ui:page name="<%=RefCodeNames.UI_PAGE_CD.ACCOUNT_DETAIL%>" 
		 type="<%=RefCodeNames.UI_PAGE_TYPE_CD.ACCOUNT%>" 
		 bean="<%=formName%>" 
		 property="uiPage" 
		 configMode="<%=configParam%>">

<tr>
    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_REMINDER_EMAIL_TITLE_LABEL%>" colspan="4">
        <b><app:storeMessage key="admin2.account.detail.label.invReminderEmailTitle"/></b>
    </td>
</tr>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_INV_REMINDER_EMAIL_SUB%>">
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_REMINDER_EMAIL_SUB_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_REMINDER_EMAIL_SUB_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.invReminderEmailSub"/></ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_REMINDER_EMAIL_SUB_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXTAREA%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_REMINDER_EMAIL_SUB_VALUE%>" colspan="3">
               <ui:textarea name="<%=formName%>" property="invReminderEmailSub" cols="60"/>
            </td>
        </ui:element>
</tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_INV_REMINDER_EMAIL_MSG%>">
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_REMINDER_EMAIL_MSG_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_REMINDER_EMAIL_MSG_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.invReminderEmailMsg"/></ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_REMINDER_EMAIL_MSG_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXTAREA%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_REMINDER_EMAIL_MSG_VALUE%>" colspan="3">
                <ui:textarea name="<%=formName%>" property="invReminderEmailMsg" rows="4" cols="60"/>
            </td>
        </ui:element>
</tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_CONFIRM_ORDER_EMAIL_GENERATOR%>">
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONFIRM_ORDER_EMAIL_GENERATOR_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONFIRM_ORDER_EMAIL_GENERATOR_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.confirmOrderEmailGenerator"/>:</ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONFIRM_ORDER_EMAIL_GENERATOR_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONFIRM_ORDER_EMAIL_GENERATOR_VALUE%>" colspan="3">
               <ui:text name="<%=formName%>" property="confirmOrderEmailGenerator" size="100"/>
            </td>
        </ui:element>
</tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_NOTIFY_ORDER_EMAIL_GENERATOR%>">
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NOTIFY_ORDER_EMAIL_GENERATOR_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NOTIFY_ORDER_EMAIL_GENERATOR_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.notifyOrderEmailGenerator"/>:</ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NOTIFY_ORDER_EMAIL_GENERATOR_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NOTIFY_ORDER_EMAIL_GENERATOR_VALUE%>" colspan="3">
                <ui:text name="<%=formName%>" property="notifyOrderEmailGenerator" size="100"/>
            </td>
        </ui:element>
</tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_REJECT_ORDER_EMAIL_GENERATOR%>">
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_REJECT_ORDER_EMAIL_GENERATOR_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_REJECT_ORDER_EMAIL_GENERATOR_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.rejectOrderEmailGenerator"/>:</ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_REJECT_ORDER_EMAIL_GENERATOR_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_REJECT_ORDER_EMAIL_GENERATOR_VALUE%>" colspan="3">
               <ui:text name="<%=formName%>" property="rejectOrderEmailGenerator" size="100"/>
            </td>
        </ui:element>
</tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_PENDING_APPROVE_MAIL_GENERATOR%>">
<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PENDING_APPROVE_MAIL_GENERATOR_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PENDING_APPROVE_MAIL_GENERATOR_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.pendingApprovEmailGenerator"/>:</ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PENDING_APPROVE_MAIL_GENERATOR_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PENDING_APPROVE_MAIL_GENERATOR_VALUE%>" colspan="3">
                <ui:text name="<%=formName%>" property="pendingApprovEmailGenerator" size="100"/>
            </td>
        </ui:element>
</tr>
</ui:control>

<tr>
    <td colspan=4 class="largeheader"><app:storeMessage key="admin2.account.detail.text.inventoryProperties"/></td>
</tr>

<tr>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_INV_LEDGER_SWITCH%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_LEDGER_SWITCH_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_LEDGER_SWITCH_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_LEDGER_SWITCH_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="invLedgerSwitch"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_LEDGER_SWITCH_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.invLedgerSwitch"/></ui:label></b>
        </ui:element>
       </td>
    </ui:control>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_INV_PO_SUFFIX%>" template="<td colspan=2>&nbsp;</td>" >
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_PO_SUFFIX_LABEL%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_PO_SUFFIX_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.invPOSuffix"/></ui:label></b>
            </ui:element>
        </td>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_PO_SUFFIX_VALUE%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_PO_SUFFIX_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <ui:text  name="<%=formName%>" property="invPOSuffix" size="3" />
            </ui:element>
        </td>
    </ui:control>

</tr>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_INV_OG_LIST_UI%>">
    <tr>
        <td colspan="2">&nbsp;</td>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_OG_LIST_UI_LABEL%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_OG_LIST_UI_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.invOGListUI"/>:</ui:label></b>
            </ui:element>
        </td>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_OG_LIST_UI_VALUE%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_OG_LIST_UI_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
                <ui:select name="<%=formName%>" property="invOGListUI">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.INVENTORY_OG_LIST_UI%>">
                        <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.INVENTORY_OG_LIST_UI%>" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.INVENTORY_OG_LIST_UI." + ((String) refCd.getValue()).toUpperCase());%>
                            <html:option value="<%=refCd.getValue()%>">
                                <% if (messageKey != null) {%>
                                <%=messageKey%>
                                <%} else {%>
                                <%=refCd.getValue()%>
                                <%}%>
                            </html:option>
                        </logic:iterate>
                    </logic:present>
                </ui:select>
            </ui:element>
        </td>
    </tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_INV_MISSING_NOTIFICATION%>">
    <tr>
        <td colspan="2">&nbsp;</td>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_MISSING_NOTIFICATION_LABEL%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_MISSING_NOTIFICATION_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.invMissingNotification"/>:</ui:label></b>
            </ui:element>
        </td>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_MISSING_NOTIFICATION_VALUE%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_MISSING_NOTIFICATION_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <ui:text name="<%=formName%>" property="invMissingNotification" size="3"/>
            </ui:element>
        </td>
    </tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_INV_CHECK_PLACED_ORDER%>">
    <tr>
        <td colspan="2">&nbsp;</td>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_CHECK_PLACED_ORDER_LABEL%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_CHECK_PLACED_ORDER_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.invCheckPlacedOrder"/>:</ui:label></b>
            </ui:element>
        </td>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_CHECK_PLACED_ORDER_VALUE%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_INV_CHECK_PLACED_ORDER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <ui:text name="<%=formName%>" property="invCheckPlacedOrder" size="3"/>
            </ui:element>
        </td>
    </tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_DISTR_PO_TYPE%>">
    <tr>
        <td colspan="2">&nbsp;</td>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DISTR_PO_TYPE_LABEL%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DISTR_PO_TYPE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.distrPoType"/>:</ui:label></b>
            </ui:element>
        </td>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DISTR_PO_TYPE_VALUE%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DISTR_PO_TYPE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
                <ui:select name="<%=formName%>" property="distrPoType">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.DISTR_PO_TYPE%>">
                        <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.DISTR_PO_TYPE%>" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.DISTR_PO_TYPE." + ((String) refCd.getValue()).toUpperCase());%>
                            <html:option value="<%=refCd.getValue()%>">
                                <% if (messageKey != null) {%>
                                <%=messageKey%>
                                <%} else {%>
                                <%=refCd.getValue()%>
                                <%}%>
                            </html:option>
                        </logic:iterate>
                    </logic:present>
                </ui:select>
            </ui:element>
        </td>
    </tr>
</ui:control>

<tr>
    <td colspan=4 class="largeheader"><app:storeMessage key="admin2.account.detail.text.assetManagement"/></td>
</tr>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ALLOW_SET_WORK_ORDER_PO_NUMBER%>"targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_SET_WORK_ORDER_PO_NUMBER_LABEL%>">
    <tr>

        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_SET_WORK_ORDER_PO_NUMBER_LABEL%>" colspan="2">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_SET_WORK_ORDER_PO_NUMBER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                <ui:checkbox  name="<%=formName%>" property="allowSetWorkOrderPoNumber"/>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_SET_WORK_ORDER_PO_NUMBER_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.allowSetWorkOrderPoNumber"/></ui:label></b>
            </ui:element>
        </td>
        <td colspan="2">&nbsp;</td>
    </tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_WORK_ORDER_PO_NUMBER_IS_REQUIRED%>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_WORK_ORDER_PO_NUMBER_IS_REQUIRED_LABEL%>">
    <tr>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_WORK_ORDER_PO_NUMBER_IS_REQUIRED_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_WORK_ORDER_PO_NUMBER_IS_REQUIRED_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="workOrderPoNumberIsRequired"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_WORK_ORDER_PO_NUMBER_IS_REQUIRED_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.workOrderPoNumberIsRequired"/></ui:label></b>
        </ui:element>
       </td>
        <td colspan="2">&nbsp;</td>
    </tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_USER_ASSIGNED_AS_SET_NUMBER%>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_USER_ASSIGNED_AS_SET_NUMBER_LABEL%>">
    <tr>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_USER_ASSIGNED_AS_SET_NUMBER_LABEL%>" colspan="2">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_USER_ASSIGNED_AS_SET_NUMBER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                <ui:checkbox  name="<%=formName%>" property="userAssignedAssetNumber"/>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_USER_ASSIGNED_AS_SET_NUMBER_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.userAssignedAssetNumber"/></ui:label></b>
            </ui:element>
        </td>
        <td colspan="2">&nbsp;</td>
    </tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ALLOW_BUY_WORK_ORDER_PARTS%>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_BUY_WORK_ORDER_PARTS_LABEL%>">
    <tr>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_BUY_WORK_ORDER_PARTS_LABEL%>" colspan="2">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_BUY_WORK_ORDER_PARTS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                <ui:checkbox  name="<%=formName%>" property="allowBuyWorkOrderParts"/>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_BUY_WORK_ORDER_PARTS_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.allowBuyWorkOrderParts"/></ui:label></b>
            </ui:element>
        </td>
        <td colspan="2">&nbsp;</td>
    </tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_CONTACT_INFORMATION_TYPE%>" targets="ACCOUNT_CONTACT_INFORMATION_TYPE_LABEL">
<tr>
	<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONTACT_INFORMATION_TYPE_LABEL%>" align="left" colspan="4">
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONTACT_INFORMATION_TYPE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
			<b><ui:label><app:storeMessage key="admin2.account.detail.label.contactInformationType"/></ui:label></b>
		</ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONTACT_INFORMATION_TYPE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.RADIO%>">
			<ui:radio property="contactInformationType" value="user"></ui:radio>&nbsp;<b>User</b>
			<ui:radio property="contactInformationType" value="site"></ui:radio>&nbsp;<b>Site</b>
			<ui:radio property="contactInformationType" value="none"></ui:radio>&nbsp;<b>None</b>
		</ui:element>
	</td>
</tr>
</ui:control>


</ui:page>
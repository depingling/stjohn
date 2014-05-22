
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ taglib uri="/WEB-INF/app-interface-config.tld" prefix="ui" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%String formName = Utility.strNN(request.getParameter("formName"));%>
<%String configParam = Utility.strNN(request.getParameter("configMode"));%>

<ui:page name="<%=RefCodeNames.UI_PAGE_CD.USER_DETAIL%>" type="<%=RefCodeNames.UI_PAGE_TYPE_CD.USER%>" bean="<%=formName%>" property="uiPage" configMode="<%=configParam%>">
<%String prop;%>
<table class="a_tab1" cellspacing="0" width='<%=Constants.TABLEWIDTH800%>'>
    <tr>
    <td class="a_td1">
        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_ON_ACCOUNT%>" targets="USER_RIGHT_ON_ACCOUNT_TARGET">
            <div id="USER_RIGHT_ON_ACCOUNT_TARGET">
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ON_ACCOUNT_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                    <%prop = "baseUserForm.onAccount";%>
                    <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                </ui:element>
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ON_ACCOUNT_LABEL%>"
                            type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsOnAccoubt"/></ui:label></b>
                </ui:element>
            </div>
        </ui:control>
        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_CREDIT_CARD%>" targets="USER_RIGHT_CREDIT_CARD_TARGET">
            <div id="USER_RIGHT_CREDIT_CARD_TARGET">
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CREDIT_CARD_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                    <%prop = "baseUserForm.creditCard";%>
                    <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                </ui:element>
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CREDIT_CARD_LABEL%>"
                            type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsCreditCard"/></ui:label></b>
                </ui:element>
            </div>
        </ui:control>

        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_OTHER_PAYMENT%>" targets="USER_RIGHT_OTHER_PAYMENT_TARGET">
            <div id="USER_RIGHT_OTHER_PAYMENT_TARGET">
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_OTHER_PAYMENT_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                    <%prop = "baseUserForm.otherPayment";%>
                    <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                </ui:element>
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_OTHER_PAYMENT_LABEL%>"
                            type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsOtherPayment"/></ui:label></b>
                </ui:element>
            </div>
        </ui:control>
        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_PO_NUM_REQ%>" targets="USER_RIGHT_PO_NUM_REQ_TARGET">
            <div id="USER_RIGHT_PO_NUM_REQ_TARGET">
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_PO_NUM_REQ_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                    <%prop = "baseUserForm.poNumRequired";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                </ui:element>
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_PO_NUM_REQ_LABEL%>"
                            type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsPONumRequired"/></ui:label></b>
                </ui:element>
            </div>
        </ui:control>

        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_SHOW_PRICE%>" targets="USER_RIGHT_SHOW_PRICE_TARGET">
            <div id="USER_RIGHT_SHOW_PRICE_TARGET">
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_SHOW_PRICE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                    <%prop = "baseUserForm.showPrice";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                </ui:element>
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_SHOW_PRICE_LABEL%>"
                            type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsShowPrice"/></ui:label></b>
                </ui:element>
            </div>
        </ui:control>

        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_CONTRACT_ITEMS_ONLY%>" targets="USER_RIGHT_CONTRACT_ITEMS_ONLY_TARGET">
            <div id="USER_RIGHT_CONTRACT_ITEMS_ONLY_TARGET">
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CONTRACT_ITEMS_ONLY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                    <%prop = "baseUserForm.contractItemsOnly";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                </ui:element>
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CONTRACT_ITEMS_ONLY_LABEL%>"
                            type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsContractItemsOnly"/></ui:label></b>
                </ui:element>
            </div>
        </ui:control>
    </td>

    <td class="a_td1">

        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_BROWSE_ONLY%>" targets="USER_RIGHT_BROWSE_ONLY_TARGET">
            <div id="USER_RIGHT_BROWSE_ONLY_TARGET">
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_BROWSE_ONLY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                    <%prop = "baseUserForm.browseOnly";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                </ui:element>
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_BROWSE_ONLY_LABEL%>"
                            type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsBrowseOnly"/></ui:label></b>
                </ui:element>
            </div>
        </ui:control>

        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_SALES_PRESENT_ONLY%>" targets="USER_RIGHT_SALES_PRESENT_ONLY_TARGET">
            <div id="USER_RIGHT_SALES_PRESENT_ONLY_TARGET">
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_SALES_PRESENT_ONLY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                    <%prop = "baseUserForm.salesPresentationOnly";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                </ui:element>
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_SALES_PRESENT_ONLY_LABEL%>"
                            type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsSalesPresentationOnly"/></ui:label></b>
                </ui:element>
            </div>
        </ui:control>

        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_NO_REPORTING%>" targets="USER_RIGHT_NO_REPORTING_TARGET">
            <div id="USER_RIGHT_NO_REPORTING_TARGET">
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_NO_REPORTING_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                    <%prop = "baseUserForm.noReporting";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                </ui:element>
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_NO_REPORTING_LABEL%>"
                            type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsNoReporting"/></ui:label></b>
                </ui:element>
            </div>
        </ui:control>
        <logic:equal parameter="configMode" value="false">
            <logic:equal name="<%=formName%>" property="detail.userData.userTypeCd" value="REPORTING_USER">
                <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_REPORTING_MANAGER%>" targets="USER_RIGHT_REPORTING_MANAGER_TARGET">
                    <div id="USER_RIGHT_REPORTING_MANAGER_TARGET">
                        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_REPORTING_MANAGER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                            <%prop = "baseUserForm.reportingManager";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                        </ui:element>
                        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_REPORTING_MANAGER_LABEL%>"
                                    type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsReportingManager"/></ui:label></b>
                        </ui:element>
                    </div>
                </ui:control>
                <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_REP_ASSIGN_ALL_ACCTS%>" targets="USER_RIGHT_REP_ASSIGN_ALL_ACCTS_TARGET">
                    <div id="USER_RIGHT_REP_ASSIGN_ALL_ACCTS_TARGET">
                        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_REP_ASSIGN_ALL_ACCTS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                            <%prop = "baseUserForm.reportingAssignAllAccts";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                        </ui:element>
                        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_REP_ASSIGN_ALL_ACCTS_LABEL%>"
                                    type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsRepAssignAllAccts"/></ui:label></b>
                        </ui:element>
                    </div>
                </ui:control>
            </logic:equal>
        </logic:equal>
        <logic:equal parameter="configMode" value="true">
            <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_REPORTING_MANAGER%>" targets="USER_RIGHT_REPORTING_MANAGER_TARGET">
                <div id="USER_RIGHT_REPORTING_MANAGER_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_REPORTING_MANAGER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.reportingManager";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_REPORTING_MANAGER_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsReportingManager"/></ui:label></b>
                    </ui:element>
                </div>
            </ui:control>
            <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_REP_ASSIGN_ALL_ACCTS%>" targets="USER_RIGHT_REP_ASSIGN_ALL_ACCTS_TARGET">
                <div id="USER_RIGHT_REP_ASSIGN_ALL_ACCTS_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_REP_ASSIGN_ALL_ACCTS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.reportingAssignAllAccts";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_REP_ASSIGN_ALL_ACCTS_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsRepAssignAllAccts"/></ui:label></b>
                    </ui:element>
                </div>
            </ui:control>
        </logic:equal>

    </td>

        <td class="a_td1">
        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_CAN_APPROVE_ORDERS%>" targets="USER_RIGHT_CAN_APPROVE_ORDERS_TARGET">
            <div id="USER_RIGHT_CAN_APPROVE_ORDERS_TARGET">
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CAN_APPROVE_ORDERS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                    <%prop = "baseUserForm.canApproveOrders";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>" />
                </ui:element>
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CAN_APPROVE_ORDERS_LABEL%>"
                            type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsCanApproveOrders"/></ui:label></b>
                </ui:element>
            </div>
        </ui:control>
          <br>
          <b><app:storeMessage key="admin2.user.detail.text.orderNotificationEmail"/></b>
          <br>
          <app:storeMessage key="admin2.user.detail.text.orderNotificationHelpMsg"/>:
          <br>
          <br>

          <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_ORDER_DETAIL_NOTIFICATION_EMAIL%>" targets="USER_RIGHT_OORDER_DET_NOTIF_EMAIL_TARGET">
                <div id="USER_RIGHT_OORDER_DET_NOTIF_EMAIL_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_DETAIL_NOTIFICATION_EMAIL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.orderDetailNotificationEmail";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_DETAIL_NOTIFICATION_EMAIL_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsOrderDetailNotification"/></ui:label>
                    </ui:element>
                  </div>
            </ui:control>

         <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_ORDER_NOTIFICATION_SHIPPED_EMAIL%>" targets="SER_RIGHT_ORDER_NOTIF_SHIPPED_EMAIL_TARGET">
                <div id="SER_RIGHT_ORDER_NOTIF_SHIPPED_EMAIL_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_NOTIFICATION_SHIPPED_EMAIL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.orderNotificationShippedEmail";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_NOTIFICATION_SHIPPED_EMAIL_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsShippingNotification"/></ui:label>
                    </ui:element>
                  </div>
            </ui:control>

         <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_NEEDS_APPROVAL%>" targets="USER_RIGHT_NEEDS_APPROVAL_TARGET">
                <div id="USER_RIGHT_NEEDS_APPROVAL_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_NEEDS_APPROVAL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.orderNotificationNeedsApprovalEmail";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_NEEDS_APPROVAL_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsOrderNeedsApproval"/></ui:label>
                    </ui:element>
                  </div>
            </ui:control>

            <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_ORDER_WAS_APPROVED%>" targets="USER_RIGHT_ORDER_WAS_APPROVED_TARGET">
                <span id="USER_RIGHT_ORDER_WAS_APPROVED_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_WAS_APPROVED_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.orderNotificationApprovedEmail";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_WAS_APPROVED_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <ui:label><app:storeMessage key="admin2.user.detail.label.userRightsOrderWasApproved"/></ui:label>
                    </ui:element>
                  </span>
            </ui:control>

            <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_ORDER_WAS_REJECTED%>" targets="USER_RIGHT_ORDER_WAS_REJECTED_TARGET">
                <span id="USER_RIGHT_ORDER_WAS_REJECTED_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_WAS_REJECTED_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.orderNotificationRejectedEmail";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_WAS_REJECTED_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsOrderWasRejected"/></ui:label>
                    </ui:element>
                  </span>
            </ui:control>

             <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_ORDER_WAS_MODIFIED%>" targets="USER_RIGHT_ORDER_WAS_MODIFIED_TARGET">
                <span id="USER_RIGHT_ORDER_WAS_MODIFIED_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_WAS_MODIFIED_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.orderNotificationModifiedEmail";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_WAS_MODIFIED_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsOrderWasModified"/></ui:label>
                    </ui:element>
                  </span>
            </ui:control>

        <br><br>
        <b><app:storeMessage key="admin2.user.detail.text.workOrderNotification"/></b><br> <br>

        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_WO_COMPLETED%>" targets="USER_RIGHT_WO_COMPLETED_TARGET">
                <div id="USER_RIGHT_WO_COMPLETED_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_WO_COMPLETED_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.workOrderCompletedNotification";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_WO_COMPLETED_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsWorkOrderCompletedNotification"/></ui:label>
                    </ui:element>
                  </div>
            </ui:control>

            <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_WO_ACCEPTED_BY_PROVIDER%>" targets="USER_RIGHT_WO_ACCEPTED_BY_PROVIDER_TARGET">
                <div id="USER_RIGHT_WO_ACCEPTED_BY_PROVIDER_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_WO_ACCEPTED_BY_PROVIDER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.workOrderAcceptedByProviderNotification";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_WO_ACCEPTED_BY_PROVIDER_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsWorkOrderAcceptedByProviderNotification"/></ui:label>
                    </ui:element>
                  </div>
            </ui:control>
             <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_WO_REJECTED_BY_PROVIDER%>" targets="USER_RIGHT_WO_REJECTED_BY_PROVIDER_TARGET">
                <div id="USER_RIGHT_WO_REJECTED_BY_PROVIDER_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_WO_REJECTED_BY_PROVIDER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.workOrderRejectedByProviderNotification";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_WO_REJECTED_BY_PROVIDER_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsWorkOrderRejectedByProviderNotification"/></ui:label>
                    </ui:element>
                  </div>
            </ui:control>
        </td>
		<td class="a_td1">

           <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_CAN_EDIT_SHIP_TO%>" targets="USER_RIGHT_CAN_EDIT_SHIP_TO_TARGET">
                <div id="USER_RIGHT_CAN_EDIT_SHIP_TO_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CAN_EDIT_SHIP_TO_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.canEditShipTo";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CAN_EDIT_SHIP_TO_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsCanEditShipTo"/></ui:label>
                    </ui:element>
                  </div>
            </ui:control>
             <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_CAN_EDIT_BILL_TO%>" targets="USER_RIGHT_CAN_EDIT_BILL_TO_TARGET">
                <div id="USER_RIGHT_CAN_EDIT_BILL_TO_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CAN_EDIT_BILL_TO_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <%prop = "baseUserForm.canEditBillTo";%> <ui:checkbox name="<%=formName%>" property="<%=prop%>"/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CAN_EDIT_BILL_TO_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label><app:storeMessage key="admin2.user.detail.label.userRightsCanEditBillTo"/></ui:label>
                    </ui:element>
                  </div>
            </ui:control>
    </tr>

</table>
</ui:page>

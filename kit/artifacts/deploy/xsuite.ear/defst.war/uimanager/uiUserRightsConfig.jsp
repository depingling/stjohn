<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ taglib uri="/WEB-INF/app-interface-config.tld" prefix="ui" %>
<!-- 000 start of user rights -->
<table class="a_tab1" cellspacing="0" width='<%=Constants.TABLEWIDTH800%>'>
    <tr>
        <td class="a_td1">
            <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_ON_ACCOUNT%>" targets="USER_RIGHT_ON_ACCOUNT_TARGET">
                <div id="USER_RIGHT_ON_ACCOUNT_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ON_ACCOUNT_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ON_ACCOUNT_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label>On Account</ui:label></b>
                    </ui:element>
                </div>
            </ui:control>
              <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_CREDIT_CARD%>" targets="USER_RIGHT_CREDIT_CARD_TARGET">
                <div id="USER_RIGHT_CREDIT_CARD_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CREDIT_CARD_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CREDIT_CARD_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label>Credit Card</ui:label></b>
                    </ui:element>
                </div>
            </ui:control>
            
              <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_OTHER_PAYMENT%>" targets="USER_RIGHT_OTHER_PAYMENT_TARGET">
                <div id="USER_RIGHT_OTHER_PAYMENT_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_OTHER_PAYMENT_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_OTHER_PAYMENT_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label>Other Payment</ui:label></b>
                    </ui:element>
                </div>
            </ui:control>
              <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_PO_NUM_REQ%>" targets="USER_RIGHT_PO_NUM_REQ_TARGET">
                <div id="USER_RIGHT_PO_NUM_REQ_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_PO_NUM_REQ_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_PO_NUM_REQ_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label>PO# Required</ui:label></b>
                    </ui:element>
                </div>
            </ui:control>

              <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_SHOW_PRICE%>" targets="USER_RIGHT_SHOW_PRICE_TARGET">
                <div id="USER_RIGHT_SHOW_PRICE_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_SHOW_PRICE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_SHOW_PRICE_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label>Show Price</ui:label></b>
                    </ui:element>
                </div>
            </ui:control>

             <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_CONTRACT_ITEMS_ONLY%>" targets="USER_RIGHT_CONTRACT_ITEMS_ONLY_TARGET">
                <div id="USER_RIGHT_CONTRACT_ITEMS_ONLY_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CONTRACT_ITEMS_ONLY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CONTRACT_ITEMS_ONLY_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label>Contract Items Only</ui:label></b>
                    </ui:element>
                </div>
            </ui:control>
        </td>

         <td class="a_td1">

             <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_BROWSE_ONLY%>" targets="USER_RIGHT_BROWSE_ONLY_TARGET">
                <div id="USER_RIGHT_BROWSE_ONLY_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_BROWSE_ONLY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_BROWSE_ONLY_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label>Browse Only</ui:label></b>
                    </ui:element>
                </div>
            </ui:control>

             <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_SALES_PRESENT_ONLY%>" targets="USER_RIGHT_SALES_PRESENT_ONLY_TARGET">
                <div id="USER_RIGHT_SALES_PRESENT_ONLY_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_SALES_PRESENT_ONLY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_SALES_PRESENT_ONLY_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label>Sales Presentation Only</ui:label></b>
                    </ui:element>
                </div>
            </ui:control>

             <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_NO_REPORTING%>" targets="USER_RIGHT_NO_REPORTING_TARGET">
                <div id="USER_RIGHT_NO_REPORTING_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_NO_REPORTING_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_NO_REPORTING_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label>No Reporting</ui:label></b>
                    </ui:element>
                </div>
            </ui:control>

            <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_REPORTING_MANAGER%>" targets="USER_RIGHT_REPORTING_MANAGER_TARGET">
                <div id="USER_RIGHT_REPORTING_MANAGER_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_REPORTING_MANAGER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_REPORTING_MANAGER_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label>Reporting Manager</ui:label></b>
                    </ui:element>
                </div>
            </ui:control>

            <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_REP_ASSIGN_ALL_ACCTS%>" targets="USER_RIGHT_REP_ASSIGN_ALL_ACCTS_TARGET">
                <div id="USER_RIGHT_REP_ASSIGN_ALL_ACCTS_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_REP_ASSIGN_ALL_ACCTS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_REP_ASSIGN_ALL_ACCTS_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label>Reporting - Assign All Accts</ui:label></b>
                    </ui:element>
                </div>
            </ui:control>
		</td>

        <td class="a_td1">

		  <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_CAN_APPROVE_ORDERS%>" targets="USER_RIGHT_CAN_APPROVE_ORDERS_TARGET">
                <div id="USER_RIGHT_CAN_APPROVE_ORDERS_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CAN_APPROVE_ORDERS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CAN_APPROVE_ORDERS_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <b><ui:label>Can Approve Orders</ui:label></b>
                    </ui:element>
                </div>
            </ui:control>

          <br>
          <b>Order Notification Email</b>
          <br>
          Send an email when an order belonging to a site for this user:

            <br>
             <br>

          <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_ORDER_DETAIL_NOTIFICATION_EMAIL%>" targets="USER_RIGHT_OORDER_DET_NOTIF_EMAIL_TARGET">
                <div id="USER_RIGHT_OORDER_DET_NOTIF_EMAIL_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_DETAIL_NOTIFICATION_EMAIL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_DETAIL_NOTIFICATION_EMAIL_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label>Order Detail Notification</ui:label>
                    </ui:element>
                  </div>
            </ui:control>

         <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_ORDER_NOTIFICATION_SHIPPED_EMAIL%>" targets="SER_RIGHT_ORDER_NOTIF_SHIPPED_EMAIL_TARGET">
                <div id="SER_RIGHT_ORDER_NOTIF_SHIPPED_EMAIL_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_NOTIFICATION_SHIPPED_EMAIL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_NOTIFICATION_SHIPPED_EMAIL_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label>Shipping Notification</ui:label>
                    </ui:element>
                  </div>
            </ui:control>

         <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_NEEDS_APPROVAL%>" targets="USER_RIGHT_NEEDS_APPROVAL_TARGET">
                <div id="USER_RIGHT_NEEDS_APPROVAL_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_NEEDS_APPROVAL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_NEEDS_APPROVAL_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label>Needs Approval</ui:label>
                    </ui:element>
                  </div>
            </ui:control>

        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_ORDER_WAS_APPROVED%>" targets="USER_RIGHT_ORDER_WAS_APPROVED_TARGET">
                <span id="USER_RIGHT_ORDER_WAS_APPROVED_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_WAS_APPROVED_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_WAS_APPROVED_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"> <ui:label>Was Approved</ui:label>
                    </ui:element>
                  </span>
            </ui:control>
         <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_ORDER_WAS_REJECTED%>" targets="USER_RIGHT_ORDER_WAS_REJECTED_TARGET">
                <span id="USER_RIGHT_ORDER_WAS_REJECTED_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_WAS_REJECTED_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_WAS_REJECTED_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label>Was Rejected</ui:label>
                    </ui:element>
                  </span>
            </ui:control>


             <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_ORDER_WAS_MODIFIED%>" targets="USER_RIGHT_ORDER_WAS_MODIFIED_TARGET">
                <span id="USER_RIGHT_ORDER_WAS_MODIFIED_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_WAS_MODIFIED_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_ORDER_WAS_MODIFIED_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label>Was Modified</ui:label>
                    </ui:element>
                  </span>
            </ui:control>

        <br><br>
        <b>Work Order Notification</b><br> <br>

        <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_WO_COMPLETED%>" targets="USER_RIGHT_WO_COMPLETED_TARGET">
                <div id="USER_RIGHT_WO_COMPLETED_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_WO_COMPLETED_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_WO_COMPLETED_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label>Completed Notification</ui:label>
                    </ui:element>
                  </div>
            </ui:control>

            <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_WO_ACCEPTED_BY_PROVIDER%>" targets="USER_RIGHT_WO_ACCEPTED_BY_PROVIDER_TARGET">
                <div id="USER_RIGHT_WO_ACCEPTED_BY_PROVIDER_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_WO_ACCEPTED_BY_PROVIDER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_WO_ACCEPTED_BY_PROVIDER_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label>Accepted By Provider Notification</ui:label>
                    </ui:element>
                  </div>
            </ui:control>
             <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_WO_REJECTED_BY_PROVIDER%>" targets="USER_RIGHT_WO_REJECTED_BY_PROVIDER_TARGET">
                <div id="USER_RIGHT_WO_REJECTED_BY_PROVIDER_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_WO_REJECTED_BY_PROVIDER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_WO_REJECTED_BY_PROVIDER_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label>Rejected By Provider Notification</ui:label>
                    </ui:element>
                  </div>
            </ui:control>
        </td>
		<td class="a_td1">

           <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_CAN_EDIT_SHIP_TO%>" targets="USER_RIGHT_CAN_EDIT_SHIP_TO_TARGET">
                <div id="USER_RIGHT_CAN_EDIT_SHIP_TO_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CAN_EDIT_SHIP_TO_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CAN_EDIT_SHIP_TO_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label>Can edit ship to</ui:label>
                    </ui:element>
                  </div>
            </ui:control>
             <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_RIGHT_CAN_EDIT_BILL_TO%>" targets="USER_RIGHT_CAN_EDIT_BILL_TO_TARGET">
                <div id="USER_RIGHT_CAN_EDIT_BILL_TO_TARGET">
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CAN_EDIT_BILL_TO_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                        <ui:checkbox/>
                    </ui:element>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_RIGHT_CAN_EDIT_BILL_TO_LABEL%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"><ui:label>Can edit bill to</ui:label>
                    </ui:element>
                  </div>
            </ui:control>           
    </tr>

</table>


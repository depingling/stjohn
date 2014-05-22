<%--
  Date: 17.10.2007
  Time: 10:27:36
--%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM">
    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderDetail.workOrder">
        <table>
            <tr>
                <td>
                           <span class="shopassetdetailbigtxt">
                      <b>
                          <app:storeMessage key="userWorkOrder.text.long.workOrderNumber"/>
                      </b>:
                           </span>
                </td>

                <td class="shopassetdetailtxt" valign="middle">
                    <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM"
                                property="workOrderDetail.workOrder.workOrderId"/>
                </td>
                <td>&nbsp</td>
                <td><span class="shopassetdetailbigtxt"><b>
                    <app:storeMessage key="userWorkOrder.text.workOrderName"/>
                </b>:</span>
                </td>

                <td class="shopassetdetailtxt" valign="middle">
                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM"
                                   property="workOrderDetail.workOrder.shortDesc">
                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM"
                                    property="workOrderDetail.workOrder.shortDesc"/>
                    </logic:present>
                </td>
                <td>&nbsp</td>
                <td><span class="shopassetdetailbigtxt"><b>
                    <app:storeMessage key="userWorkOrder.text.priority"/>
                </b>:</span></td>
                <td class="shopassetdetailtxt" valign="middle">
                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM"
                                   property="workOrderDetail.workOrder.priority">
                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM"
                                    property="workOrderDetail.workOrder.priority"/>
                    </logic:present>
                </td>
            </tr>
        </table>
    </logic:present>
</logic:present>
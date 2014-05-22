<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.StoreGroupForm" %>
<%@ page import="com.cleanwise.service.api.value.GroupData" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table ID=819 border="0" cellpadding="0" cellspacing="0" width="769">
    <logic:present name="STORE_GROUP_FORM"  property="groupData">
        <logic:greaterThan   name="STORE_GROUP_FORM"  property="groupData.groupId" value="0">
        <tr bgcolor="#000000">
            <td class="tbartext">
                <app:renderStatefulButton link="storeGroupDetail.do?action=viewDetail"
                                          name="Group Detail" tabClassOff="tbar" tabClassOn="tbarOn"
                                          linkClassOff="tbar" linkClassOn="tbarOn"
                                          contains="storeGroupDetail"/>

                <app:renderStatefulButton link="storeGroupMgrConfig.do?action=initConfig"
                                          name="Group Config" tabClassOff="tbar" tabClassOn="tbarOn"
                                          linkClassOff="tbar" linkClassOn="tbarOn"
                                          contains="storeGroupMgrConfig"/>

                    <%--If the group type is a user group display the link to do additional user group configuration--%>
                <%
                    if (session.getAttribute("STORE_GROUP_FORM") != null) {
                        StoreGroupForm grForm = (StoreGroupForm) session.getAttribute("STORE_GROUP_FORM");
                        GroupData groupData = grForm.getGroupData();

                        if(groupData!=null && RefCodeNames.GROUP_TYPE_CD.USER.equals(groupData.getGroupTypeCd())){
                %>

                <app:renderStatefulButton link="storeGroupMgrUserReports.do?action=initReports"
                                          name="Reports" tabClassOff="tbar" tabClassOn="tbarOn"
                                          linkClassOff="tbar" linkClassOn="tbarOn"
                                          contains="storeGroupMgrUserReports"/>

                <app:renderStatefulButton link="storeGroupMgrUserFunctions.do?action=initFunctions"
                                          name="Functions" tabClassOff="tbar" tabClassOn="tbarOn"
                                          linkClassOff="tbar" linkClassOn="tbarOn"
                                          contains="storeGroupMgrUserFunctions"/>
                <%
                        }
                    }
                %>
            </td>
        </tr>
        </logic:greaterThan>
    </logic:present>
</table>

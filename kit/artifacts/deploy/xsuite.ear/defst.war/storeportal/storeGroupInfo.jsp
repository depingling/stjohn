<%!
    private String groupStatusCd;
    private String groupTypeCd;
    private String shortDesc;
    private int groupId;
%>

<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.value.GroupData" %>
<%@ page import="com.cleanwise.view.forms.StoreGroupForm" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<%
    if (session.getAttribute("STORE_GROUP_FORM") != null) {

        StoreGroupForm grForm = (StoreGroupForm) session.getAttribute("STORE_GROUP_FORM");
        GroupData groupData = grForm.getGroupData();

        if(groupData!=null){
            groupId = groupData.getGroupId();
            shortDesc = groupData.getShortDesc();
            groupTypeCd = groupData.getGroupTypeCd();
            groupStatusCd = groupData.getGroupStatusCd();
        }
    }
%>

<table ID=818 border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
    <tr>
        <td><b>Group Id:</b></td>
        <td>
            <%=groupId%>
        </td>
        <td><b>Group Name:</b></td>
        <td>
            <%=shortDesc%>
        </td>
        <td><b>Group Type:</b></td>
        <td>
            <%=groupTypeCd%>
        </td>
        <td><b>Group Status:</b></td>
        <td>
            <%=groupStatusCd%>
        </td>
    </tr>
</table>

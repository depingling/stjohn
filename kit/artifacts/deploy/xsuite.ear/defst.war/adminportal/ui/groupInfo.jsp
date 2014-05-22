<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<app:checkLogon/>
<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
        <tr>
                <td><b>Group Id:</b></td>
                <td>
                        <bean:write name="GROUP_FORM" property="groupData.groupId"/>
                </td>
                <td><b>Group Name:</b></td>
                <td>
                        <bean:write name="GROUP_FORM" property="groupData.shortDesc"/>
                </td>
                <td><b>Group Type:</b></td>
                <td>
                        <bean:write name="GROUP_FORM" property="groupData.groupTypeCd"/>
                </td>
                <td><b>Group Status:</b></td>
                <td>
                        <bean:write name="GROUP_FORM" property="groupData.groupStatusCd"/>
                </td>
        </tr>
</table>

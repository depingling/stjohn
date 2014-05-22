<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

    <div class="text">

    <jsp:include flush='true' page="storeGroupInfo.jsp"/>

    <html:form styleId="833" name="STORE_GROUP_FORM" action="storeportal/storeGroupMgrUserReports.do"
               type="com.cleanwise.view.forms.StoreGroupForm">

        <table ID=834>
            <tr>
                <td colspan="2"><b>Note:</b> Changes do not take effect until the user logs out and logs back in.
            </tr>

            <tr class="stpTH">
                <td class="stpTH" colspan="2" align="left"><b>Reports Of This Group</b></td>
            </tr>

            <tr>
                <td>
                    <table class="stpTable_sortable" id="ts1">

                        <thead>
                            <title><b>Reports Of This Group</b></title>
                            <tr class="stpTH">
                                <th class="stpTH">Names</th>
                                <th class="stpTH">Select</th>
                            </tr>
                        </thead>

                        <tbody>
                            <logic:present name="Generic.Report.name.vector" >
                                <logic:present name="STORE_GROUP_FORM" property="reports">
                                    <logic:iterate id="reportsCollectionNames" name="Generic.Report.name.vector" type="java.lang.String">
                                        <tr class="stpTD">
                                            <td class="stpTD">
                                                <bean:write name="reportsCollectionNames"/>
                                            </td>
                                            <td>
                                                <html:multibox name="STORE_GROUP_FORM" property="reports" value="<%=reportsCollectionNames%>"/>
                                            </td>
                                        </tr>
                                    </logic:iterate>
                                </logic:present>
                            </logic:present>
                        </tbody>
                        
                    </table>
                </td>
            </tr>

            <tr align="right">
                <td colspan="2"  align="right">
                    <html:hidden property="action" value="report.user.update"/>
                    <html:submit property="action">
                        <app:storeMessage  key="admin.button.submitUpdates"/>
                    </html:submit>
                </td>
            </tr>

        </table>

    </html:form>
</html:html>

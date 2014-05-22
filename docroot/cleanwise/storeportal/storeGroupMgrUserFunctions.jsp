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

    <html:form styleId="835" name="STORE_GROUP_FORM" action="storeportal/storeGroupMgrUserFunctions.do"
               type="com.cleanwise.view.forms.StoreGroupForm">

        <table ID=836>
            <tr>
                <td colspan="2"><b>Note:</b> Changes do not take effect until the user logs out and logs back in.
            </tr>

            <tr class=stpTH>
                <td class=stpTH colspan="2" align="left"><b>Functions Of This Group</b></td>
            </tr>

            <tr>
                <td>
                    <table class="stpTable_sortable" id="ts2">

                        <thead>
                            <title><b>Functions Of This Group</b></title>
                            <tr class=stpTH>
                                <th class="stpTH">Names</th>
                                <th class="stpTH">Select</th>
                            </tr>
                        </thead>

                        <tbody>
                            <logic:present name="Application.Functions.name.vector"  >
                                <logic:present name="STORE_GROUP_FORM" property="applicationFunctions" >
                                    <logic:iterate id="appFunCollectionNames" name="Application.Functions.name.vector" >
                                        <bean:define id="appFunctionName" name="appFunCollectionNames"  property="value" type="java.lang.String" />
                                        <tr class=stpTD>
                                            <td class=stpTD>
                                                <bean:write name="appFunctionName"/>
                                            </td>
                                            <td class=stpTD>
                                                <html:multibox name="STORE_GROUP_FORM" property="applicationFunctions" value="<%=appFunctionName%>"/>
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
                    <html:hidden property="action" value="function.user.update"/>
                    <html:submit property="action">
                        <app:storeMessage  key="admin.button.submitUpdates"/>
                    </html:submit>
                </td>
            </tr>

        </table>

    </html:form>
</html:html>

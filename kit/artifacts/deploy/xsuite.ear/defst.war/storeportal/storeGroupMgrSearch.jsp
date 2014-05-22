<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%--
THIS PAGE IS USED IN MULTIPLE PLACES!
It is used (at the time of writting) in the locate popup and in the group management screen.  This functionality is determined
based off the url and is controlled at the page level using the mode variable
--%>

<%
    String currentURI = request.getServletPath();

    String mode = "manage";
    if (currentURI.indexOf("locate") > 0) {
        mode = "locate";
    }

    String feedField = request.getParameter("feedField");
    if (null == feedField) {
        feedField = "";
    }
    String feedDesc = request.getParameter("feedDesc");
    if (null == feedDesc) {
        feedDesc = "";
    }
    String locateFilter = request.getParameter("locateFilter");
    if (null == locateFilter) {
        locateFilter = "";
    }

    String submitFl = request.getParameter("submitFl");
    if (null == submitFl) {
        submitFl = "false";
    }
%>

<app:checkLogon/>

<%
    if (mode.equals("locate")) {
%>

<table ID=827>
<%

        }else{

    %>
<table ID=828  border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
    <%
        }
    %>
    <html:form styleId="829" name="STORE_GROUP_FORM" action="storeportal/storeGroupMgr.do" type="com.cleanwise.view.forms.StoreGroupForm">

        <input type="hidden" name="feedField" value="<%=feedField%>">
        <input type="hidden" name="feedDesc" value="<%=feedDesc%>">
        <input type="hidden" name="locateFilter" value="<%=locateFilter%>">
        <input type="hidden" name="submitFl" value="<%=submitFl%>">

        <tr>
            <td><b>Search Groups:</b></td>
            <td>
                <html:text name="STORE_GROUP_FORM" property="groupName" size="30"/>
            </td>
        </tr>

        <tr>
            <td><b>Group Type:</b></td>
            <td>
                <html:select name="STORE_GROUP_FORM" property="groupType">
                    <html:option value="">
                        <app:storeMessage  key="admin.select"/>
                    </html:option>
                    <html:options collection="group.type.vector" property="value"/>
                </html:select>
            </td>
        </tr>

        <tr>
            <td></td>
            <td><html:checkbox name="STORE_GROUP_FORM" property="showGroupInactiveFl"/>Show Inactive</td>
        </tr>

        <tr>
            <td align="center">
                <html:submit property="action">
                    <app:storeMessage  key="global.action.label.search"/>
                </html:submit>
                &nbsp;
                <%
                    if (mode.equals("manage")) {
                %>
                &nbsp;
                <html:submit property="action">
                    <app:storeMessage  key="admin.button.create"/>
                </html:submit>
                <%
                    }
                %>
            </td>
        </tr>
    </html:form>
</table>


<table ID=830>
    <tr class=stpTH>
        <td>Search result count:
            <logic:present name="Groups.found.vector">
            <bean:size id="rescount" name="Groups.found.vector"/>
            <bean:write name="rescount"/>
            </logic:present>
        </td>
    </tr>
    <tr><td>
        <table class="stpTable_sortable" id="ts1">
            <logic:present name="Groups.found.vector">
            <thead>
                 <tr class=stpTH>
                    <td class="stpTH">Group Id</td>
                    <td class="stpTH">Group Name</td>
                    <td class="stpTH">Group Type</td>

                    <logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd"
                                 value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
                        <td class="stpTH">Store id</td>
                        <td class="stpTH">Short desc</td>
                    </logic:equal>

                    <td class="stpTH">Status</td>
                </tr>

            </thead>
            <tbody>
                <logic:iterate id="grp" name="Groups.found.vector">
                    <bean:define id="key" name="grp" property="groupId"/>
                    <bean:define id="name" name="grp" property="shortDesc"/>
                    <tr class=stpTD>
                        <td class="stpTD">
                            <bean:write name="grp" property="groupId"/>
                        </td>
                        <td class="stpTD">
                            <%
                                if (mode.equals("locate")) {
                                    String onClick = "return passIdAndName('" + key + "','" + name + "');";
                            %>
                            <a ID=831 href="javascript:void(0);" onclick="<%=onClick%>">
                                <bean:write name="grp" property="shortDesc"/>
                            </a>
                            <%
                            } else {
                                String linkHref = "storeGroupDetail.do?action=viewDetail&groupId=" + key;
                            %>
                            <a ID=832 href="<%=linkHref%>">
                                <bean:write name="grp" property="shortDesc"/>
                            </a>
                            <%
                                }
                            %>
                        </td>
                        <td class="stpTD">
                            <bean:write name="grp" property="groupTypeCd"/>
                        </td>

                        <logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd"
                                     value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
                            <logic:present name="STORE_GROUP_FORM" property="storeInfoOfGroup">
                                <bean:define id="htStoreInfo"  name="STORE_GROUP_FORM" property="storeInfoOfGroup" type="java.util.Hashtable"/>
                                <%
                                    PairView storeInfo=(PairView)htStoreInfo.get(key);
                                    if(storeInfo!=null)
                                    {
                                %>
                                <td class="stpTD">
                                    <%=storeInfo.getObject1()%>
                                </td>
                                <td class="stpTD">
                                    <%=storeInfo.getObject2()%>
                                </td>
                                <%
                                } else {
                                %>
                                <td class="stpTD">&nbsp;</td>
                                <td class="stpTD">&nbsp;</td>
                                <%
                                    }
                                %>
                            </logic:present>
                            <logic:notPresent name="STORE_GROUP_FORM" property="storeInfoOfGroup">
                                <td  class="stpTD">N/A</td>
                                <td  class="stpTD">N/A</td>
                            </logic:notPresent>
                        </logic:equal>

                        <td class="stpTD">
                            <bean:write name="grp" property="groupStatusCd"/>
                        </td>
                    </tr>

                </logic:iterate>
                </logic:present>
            </tbody>
        </table>
    </td></tr>
</table>

<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.AccountDataVector" %>
<%@ page import="com.cleanwise.service.api.value.AccountData" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
    <!--
    function SetCheckedUsers(val) {
        var dml = document.forms[0];
        var ellen = dml.elements.length;
        for (j = 0; j < ellen; j++) {
            if (dml.elements[j].name.match('configResults.selected') && !dml.elements[j].disabled) {
                dml.elements[j].checked = val;
            }
        }
    }
    //-->
</script>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<div class="text">

<jsp:include flush='true' page="/storeportal/storeGroupInfo.jsp"/>

<bean:define id="theForm" name="STORE_GROUP_FORM" type="com.cleanwise.view.forms.StoreGroupForm"/>
<html:form styleId="820" name="STORE_GROUP_FORM" action="/storeportal/storeGroupMgrConfig.do" type="com.cleanwise.view.forms.StoreGroupForm">
    
<table ID=821>
<tr>
    <td>
        <table ID=822 border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
            <tr>
                <td><b>Find:</b></td>
                <td>
                    <logic:equal name="STORE_GROUP_FORM" property="groupData.groupTypeCd"
                                 value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>">
                        <html:select name="STORE_GROUP_FORM" property="configType"  onchange="this.form.submit();">
                            <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>"/>
			    <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.ACCOUNT%>"/>
                        </html:select>
                    </logic:equal>
                    <logic:equal name="STORE_GROUP_FORM" property="groupData.groupTypeCd"
                                 value="<%=RefCodeNames.GROUP_TYPE_CD.ACCOUNT%>">
                        <html:select name="STORE_GROUP_FORM" property="configType" onchange="this.form.submit();">
                            <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.ACCOUNT%>"/>
			    <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>"/>
                        </html:select>
                    </logic:equal>
                    <%--<logic:equal name="STORE_GROUP_FORM" property="groupData.groupTypeCd"
                                 value="<%=RefCodeNames.GROUP_TYPE_CD.SITE%>">
                        <html:select name="STORE_GROUP_FORM" property="configType">
                            <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.SITE%>"/>
                        </html:select>
                    </logic:equal>--%>
                    <logic:equal name="STORE_GROUP_FORM" property="groupData.groupTypeCd"
                                 value="<%=RefCodeNames.GROUP_TYPE_CD.DISTRIBUTOR%>">
                        <html:select name="STORE_GROUP_FORM" property="configType">
                            <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.DISTRIBUTOR%>"/>
                        </html:select>
                    </logic:equal>
                    <logic:equal name="STORE_GROUP_FORM" property="groupData.groupTypeCd"
                                 value="<%=RefCodeNames.GROUP_TYPE_CD.MANUFACTURER%>">
                        <html:select name="STORE_GROUP_FORM" property="configType">
                            <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.MANUFACTURER%>"/>
                        </html:select>
                    </logic:equal>
                    <logic:equal name="STORE_GROUP_FORM" property="groupData.groupTypeCd"
                                 value="<%=RefCodeNames.GROUP_TYPE_CD.STORE%>">
                        <html:select name="STORE_GROUP_FORM" property="configType">
                            <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.STORE%>"/>
                        </html:select>
                    </logic:equal>
                    <logic:equal name="STORE_GROUP_FORM" property="groupData.groupTypeCd"
                                 value="<%=RefCodeNames.GROUP_TYPE_CD.ACCOUNT_UI%>">
                        <html:select name="STORE_GROUP_FORM" property="configType">
                            <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.ACCOUNT%>"/>
                        </html:select>
                    </logic:equal>
                    <logic:equal name="STORE_GROUP_FORM" property="groupData.groupTypeCd"
                                 value="<%=RefCodeNames.GROUP_TYPE_CD.STORE_UI%>">
                        <html:select name="STORE_GROUP_FORM" property="configType">
                            <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.STORE%>"/>
                        </html:select>
                    </logic:equal>   <logic:equal name="STORE_GROUP_FORM" property="groupData.groupTypeCd"
                                 value="<%=RefCodeNames.GROUP_TYPE_CD.USER_UI%>">
                        <html:select name="STORE_GROUP_FORM" property="configType">
                            <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.USER_UI%>"/>
                        </html:select>
                    </logic:equal>
                </td>
                <td>

                </td>
            </tr>

            <logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd"
                         value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
            <tr>
                <td><b>Store&nbsp;Id:</b></td>
                <td>
                    <html:text name="STORE_GROUP_FORM" property="searchStoreId" size="5"/>
                    <html:button property="action"
                                 onclick="popLocateGlobal('../adminportal/storelocate', 'searchStoreId', 'storeName');"
                                 value="Locate Store"/>
                </td>
			</logic:equal>
            <!--<logic:equal name="STORE_GROUP_FORM" property="configType" value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>">-->
	    <% if(theForm.getGroupType().equals(RefCodeNames.GROUP_TYPE_CD.USER) ||
		    theForm.getConfigType().equals(RefCodeNames.GROUP_TYPE_CD.USER)){ %>
                <tr>
                    <td colspan="4">
                        <table name="AccountFilterTable" border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td>
                                    <jsp:include flush='true' page="locateStoreAccount.jsp">
                                        <jsp:param name="jspFormAction"           value="/storeportal/storeGroupMgrConfig.do"/>
                                        <jsp:param name="jspFormName"             value="STORE_GROUP_FORM"/>
                                        <jsp:param name="jspSubmitIdent"          value=""/>
                                        <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
                                    </jsp:include>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <logic:notEqual name="STORE_GROUP_FORM" property="configType"
                                 value="<%=RefCodeNames.GROUP_TYPE_CD.ACCOUNT%>">
                <tr>
		    
                    <td><b>Account(s)</b></td>
                    <td colspan="3">
                        <logic:present name="STORE_GROUP_FORM" property="accountFilter">
                            <bean:define id="accountDataVector" name="STORE_GROUP_FORM" property="accountFilter" type="AccountDataVector"/>
                            <%
                            if (accountDataVector != null && accountDataVector.size() > 0) {
                                for (int i = 0; i < accountDataVector.size(); i++) {
                                    AccountData accountD = (AccountData) accountDataVector.get(i);
                            %>
                            &lt;<%=accountD.getBusEntity().getShortDesc()%>&gt;
                            <%
                                }
                            %>
                            <html:submit property="action" value="Clear Account Filter" styleClass='text'/>
                            <%
                            }
                            %>
                            <html:submit property="action" value="Locate Account" styleClass='text'/>
                        </logic:present>
                        <logic:notPresent name="STORE_GROUP_FORM" property="accountFilter">
                            <html:submit property="action" value="Locate Account" styleClass='text'/>
                        </logic:notPresent>
                    </td>
                </tr>
                <tr>
                    <td><b>First Name:</b></td>
                    <td colspan="3"><html:text name="STORE_GROUP_FORM" property="firstUserName"/></td>
                </tr>
                <tr>
                    <td><b>Last Name:</b></td>
                    <td colspan="3"><html:text name="STORE_GROUP_FORM" property="lastUserName"/></td>
                </tr>
                </logic:notEqual>
		<% } %>
               <!-- </logic:equal>-->
                <tr>
                    <td><b>Search By:</b></td>
                    <td colspan="3">
                        <html:text name="STORE_GROUP_FORM" property="configSearchField"/>
                        <html:radio name="STORE_GROUP_FORM" property="configSearchType" value="nameBegins"/>
                        Name(starts with)
                        <html:radio name="STORE_GROUP_FORM" property="configSearchType" value="nameContains"/>
                        Name(contains)
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="3">
                        <html:checkbox name="STORE_GROUP_FORM" property="showConfiguredOnlyFl"/>
                        Show configured only
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="3">
                        <html:submit property="action">
                            <app:storeMessage  key="global.action.label.search"/>
                        </html:submit>
                        <html:submit property="action">
                            <app:storeMessage  key="admin.button.submitUpdates"/>
                        </html:submit>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">&nbsp</td>
                </tr>
		
        </table>
    </td>
</tr>
<logic:present name="STORE_GROUP_FORM" property="configResults">
<tr>
    <td align="right" colspan="8">
        <a ID=823 href="javascript:SetCheckedUsers(1)">[Check&nbsp;All]</a>
        <br>
        <a ID=824 href="javascript:SetCheckedUsers(0)">[&nbsp;Clear]</a>
    </td>
</tr>
<tr>
<td>
<table class="stpTable_sortable" id="ts1">
<thead>
    <tr class=stpTH>
        <%String configResultsType = theForm.getConfigResultsType();%>
            <%--Render headers based off type we are configuring--%>
       <%if (RefCodeNames.GROUP_TYPE_CD.USER.equals(configResultsType) || RefCodeNames.GROUP_TYPE_CD.USER_UI.equals(configResultsType)) {%>

            <th class="stpTH">User Id</th>
            <th class="stpTH">Login Name</th>
            <th class="stpTH">First Name</th>
            <th class="stpTH">Last Name</th>
            <th class="stpTH">User Type</th>
            <th class="stpTH">Status</th>

        <%} else {%>

            <%--Only the user has a custom header, the rest we will just use the same heading--%>
            <th class="stpTH">Id</th>
            <th class="stpTH">Name</th>
            <th class="stpTH">Address</th>
            <th class="stpTH">City</th>
            <th class="stpTH">State</th>
            <th class="stpTH">Zip Code</th>
            <th class="stpTH">Status</th>

         <%}%>

        <th class="stpTH">Select</th>
    </tr>
</thead>
<tbody>
<logic:iterate id="arrele" name="STORE_GROUP_FORM" property="configResults.values" indexId="i">

<tr class=stpTD>
    <%--Render detail rows based off type we are configuring--%>

<logic:equal name="STORE_GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>">
    <td class="stpTD">
        <bean:write name="arrele" property="userId"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="userName"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="firstName"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="lastName"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="userTypeCd"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="userStatusCd"/>
    </td>
	</logic:equal>


<logic:equal name="STORE_GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.USER_UI%>">
        <td class="stpTD">
            <bean:write name="arrele" property="userId"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="userName"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="firstName"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="lastName"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="userTypeCd"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="userStatusCd"/>
        </td>
    </logic:equal>

<logic:equal name="STORE_GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.ACCOUNT_UI%>">
        <td class="stpTD">
            <bean:write name="arrele" property="busEntity.busEntityId"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="busEntity.shortDesc"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="billingAddress.address1"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="billingAddress.city"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="billingAddress.stateProvinceCd"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="billingAddress.postalCode"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="busEntity.busEntityStatusCd"/>
        </td>
    </logic:equal>

<logic:equal name="STORE_GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.STORE_UI%>">
        <td class="stpTD">
            <bean:write name="arrele" property="busEntity.busEntityId"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="busEntity.shortDesc"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="primaryAddress.address1"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="primaryAddress.city"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="primaryAddress.stateProvinceCd"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="primaryAddress.postalCode"/>
        </td>
        <td class="stpTD">
            <bean:write name="arrele" property="busEntity.busEntityStatusCd"/>
        </td>
    </logic:equal>

<logic:equal name="STORE_GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.ACCOUNT%>">
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.busEntityId"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.shortDesc"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="billingAddress.address1"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="billingAddress.city"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="billingAddress.stateProvinceCd"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="billingAddress.postalCode"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.busEntityStatusCd"/>
    </td>
</logic:equal>

<logic:equal name="STORE_GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.DISTRIBUTOR%>">
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.busEntityId"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.shortDesc"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="primaryAddress.address1"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="primaryAddress.city"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="primaryAddress.stateProvinceCd"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="primaryAddress.postalCode"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.busEntityStatusCd"/>
    </td>
</logic:equal>

<%--<logic:equal name="STORE_GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.SITE%>">
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.busEntityId"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.shortDesc"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="siteAddress.address1"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="siteAddress.city"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="siteAddress.stateProvinceCd"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="siteAddress.postalCode"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.busEntityStatusCd"/>
    </td>
</logic:equal>--%>

<logic:equal name="STORE_GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.STORE%>">
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.busEntityId"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.shortDesc"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="primaryAddress.address1"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="primaryAddress.city"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="primaryAddress.stateProvinceCd"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="primaryAddress.postalCode"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.busEntityStatusCd"/>
    </td>
</logic:equal>

<logic:equal name="STORE_GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.MANUFACTURER%>">
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.busEntityId"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.shortDesc"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="primaryAddress.address1"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="primaryAddress.city"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="primaryAddress.stateProvinceCd"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="primaryAddress.postalCode"/>
    </td>
    <td class="stpTD">
        <bean:write name="arrele" property="busEntity.busEntityStatusCd"/>
    </td>
</logic:equal>

<%
    String selectedStr = "configResults.selected[" + i + "]";
%>
<td class="stpTD">
	<bean:define id="configType" name="STORE_GROUP_FORM" property="configType"/>
    <bean:define id="groupTypeCd" name="STORE_GROUP_FORM" property="groupData.groupTypeCd"/>
    <%
        if ((RefCodeNames.GROUP_TYPE_CD.USER.equals(groupTypeCd) &&
        	 RefCodeNames.GROUP_TYPE_CD.USER.equals(configType)) 
        		|| 
        	RefCodeNames.GROUP_TYPE_CD.USER_UI.equals(groupTypeCd)) {
    %>
    <bean:define id="userTypeCd" name="arrele" property="userTypeCd"/>
    <bean:define id="groupShortDesc" name="STORE_GROUP_FORM" property="groupData.shortDesc"/>
    <%	
        if (userTypeCd.equals(groupShortDesc)) {
    %>
    <html:multibox disabled="true" name="STORE_GROUP_FORM" property="<%=selectedStr%>" value="true"/>
    <%
    } else {
    %>
    <html:multibox name="STORE_GROUP_FORM" property="<%=selectedStr%>" value="true"/>
    <%
        }
    } else {
    %>
    <html:multibox name="STORE_GROUP_FORM" property="<%=selectedStr%>" value="true"/>
    <%
        }
    %>
</td>
</tr>
</logic:iterate>
</tbody>
</table>
</td>
</tr>
</logic:present>
</table>
</html:form>

</div>


</html:html>

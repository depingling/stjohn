<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.logic.Admin2AccountMgrLogic" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/app-interface-config.tld" prefix="ui" %>

<bean:define id="Location" value="account" type="java.lang.String" toScope="session"/>
<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function enableCheckBox(self, boxName) {
    if (self.checked) {
        self.form.elements[boxName].disabled = false;
    } else {
        self.form.elements[boxName].checked = false;
        self.form.elements[boxName].disabled = true;
    }
}

function checkDependencies(formName) {
    var boxToCheck = document.forms[formName].elements["allowSetWorkOrderPoNumber"];
    var boxToSet = document.forms[formName].elements["workOrderPoNumberIsRequired"];

    if (boxToCheck.checked) {
        boxToSet.disabled = false;
    } else {
        boxToSet.disabled = true;
    }
}

//-->
</script>

<body class="admin2" onLoad = "javascript: checkDependencies('ADMIN2_ACCOUNT_DETAIL_MGR_FORM');">
<table ID=481 class="stpTable">

<logic:equal name="ADMIN2_ACCOUNT_DETAIL_MGR_FORM" property="id" value="0">
<logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd" value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">

<tr>
<td class=stpLabel><app:storeMessage key="admin2.account.detail.label.storeId"/>:</td>
<td>
    <html:text name="ADMIN2_ACCOUNT_DETAIL_MGR_FORM" property="storeId" size="5"/>
    <span class="reqind">*</span>
    <html:button property="action" onclick="popLocate('storelocate', 'storeId', 'storeName');" value="Locate Store"/>
</td>
<td class=stpLabel><app:storeMessage key="admin2.account.detail.label.storeName"/>:</td>
<td>
    <html:text name="ADMIN2_ACCOUNT_DETAIL_MGR_FORM" property="storeName" size="30" readonly="true" styleClass="mainbodylocatename"/>
</td>
</tr>

</logic:equal>
</logic:equal>

<logic:notEqual name="ADMIN2_ACCOUNT_DETAIL_MGR_FORM" property="id" value="0">
<tr>
<td class=stpLabel><app:storeMessage key="admin2.account.detail.label.storeId"/>:</td>
<td>
<bean:write name="ADMIN2_ACCOUNT_DETAIL_MGR_FORM" property="storeId"/>
<html:hidden name="ADMIN2_ACCOUNT_DETAIL_MGR_FORM" property="storeId"/>
</td>
<td class=stpLabel><app:storeMessage key="admin2.account.detail.label.storeName"/>:</td>
<td>
<bean:write name="ADMIN2_ACCOUNT_DETAIL_MGR_FORM" property="storeName"/>
<html:hidden name="ADMIN2_ACCOUNT_DETAIL_MGR_FORM" property="storeName"/>
</td>
</tr>
</logic:notEqual>

</table>




<div class="text">
<table cellpadding="2" cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH800%>" class="mainbody">

<html:form styleId="<%=RefCodeNames.UI_PAGE_CD.ACCOUNT_DETAIL%>"
           name="ADMIN2_ACCOUNT_DETAIL_MGR_FORM"
           action="/admin2.0/admin2AccountDetail.do"
           scope="session">

<tr height="0px">
    <td width="150px"></td>
    <td width="250px"></td>
    <td width="150px"></td>
    <td></td>
</tr>

<ui:page name="<%=RefCodeNames.UI_PAGE_CD.ACCOUNT_DETAIL%>"
		 type="<%=RefCodeNames.UI_PAGE_TYPE_CD.ACCOUNT%>"
		 bean="ADMIN2_ACCOUNT_DETAIL_MGR_FORM"
		 property="uiPage"
		 configMode="false">

<jsp:include flush='true' page="../admin2.0/admin2AccountDetailBody1.jsp">
        <jsp:param name="configMode" value="false"/>
        <jsp:param name="formAction" value="/admin2.0/admin2AccountDetail.do"/>
        <jsp:param name="formName" value="ADMIN2_ACCOUNT_DETAIL_MGR_FORM"/>
</jsp:include>
<jsp:include flush='true' page="../admin2.0/admin2AccountDetailBody2.jsp">
        <jsp:param name="configMode" value="false"/>
        <jsp:param name="formAction" value="/admin2.0/admin2AccountDetail.do"/>
        <jsp:param name="formName" value="ADMIN2_ACCOUNT_DETAIL_MGR_FORM"/>
</jsp:include>
<jsp:include flush='true' page="../admin2.0/admin2AccountDetailBody3.jsp">
        <jsp:param name="configMode" value="false"/>
        <jsp:param name="formAction" value="/admin2.0/admin2AccountDetail.do"/>
        <jsp:param name="formName" value="ADMIN2_ACCOUNT_DETAIL_MGR_FORM"/>
</jsp:include>

<tr>
    <td colspan=4>
        <table cellpadding="2" cellspacing="0" border="0" width="100%" class="mainbody">
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <html:submit property="action">
                        <app:storeMessage  key="global.action.label.save"/>
                    </html:submit>
                    <logic:equal name="<%=Constants.APP_USER%>" property="aAdmin" value="true">
                        <logic:notEqual name="<%=Constants.APP_USER%>" property="aAccountAdmin" value="true">
                            <logic:greaterThan name="<%=Admin2AccountMgrLogic.ADMIN2_ACCOUNT_DETAIL_MGR_FORM%>" property="accountData.accountId" value="0">
                                <html:submit property="action">
                                    <app:storeMessage  key="admin2.button.createClone"/>
                                </html:submit>
                            </logic:greaterThan>
                        </logic:notEqual>
                    </logic:equal> <html:reset>
                    <app:storeMessage  key="admin2.button.resetFields"/>
                </html:reset>
                </td>
            </tr>
        </table>
    </td>
</tr>

</ui:page>

</html:form>
</table>
</div>
</body>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
<!--
document.onkeypress = freeKeyHit;

function freeKeyHit(evt) {
    var evt = (evt) ? evt : event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;

    if (charCode == 13) {
        actionSubmit(0, 'SearchAsset');
    }
}

function actionSubmit(formNum, action) {
    var actions;
    actions=document.forms[formNum]["action"];
    //alert(actions.length);
    for (ii=actions.length-1; ii>=0; ii--) {
        if (actions[ii].value=='hiddenAction') {
            actions[ii].value=action;
            document.forms[formNum].submit();
        break;
        }
    }
return false;
}

-->
</script>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="USER_ASSET_PROFILE_MGR_FORM" type="com.cleanwise.view.forms.UserAssetProfileMgrForm"/>

<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String messageKey = "";
%>

<div class="text">

<table ID="1588" width="769" cellspacing="0" border="0" class="mainbody">
<html:form styleId="1589" name="USER_ASSET_PROFILE_MGR_FORM"
                          action="/storeportal/masterAssetCategoryProfile.do"
                          scope="session"
                          type="com.cleanwise.view.forms.UserAssetProfileMgrForm">
    <tr>
        <td colspan="3">
            &nbsp;
            <span style="font-size: 14px; font-weight: bold; line-height: 200%;">
                <app:storeMessage key="userAssets.text.category"/>
            </span>
        </td>
    </tr>
    <tr>
        <td width="20%" align="right">
            <b><app:storeMessage key="userAssets.text.assetCategoryName"/>:</b>
        </td>
        <td width="2%">&nbsp;</td>
        <td>
            <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
            <html:text size="60" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc" onkeypress="javascript: freeKeyHit(event)"/>
            <span class="reqind">*</span>
            </logic:equal>
            <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc"/>
           </logic:notEqual>
        </td>
    <tr>
    <tr>
        <td colspan="3" style="font-size: 3pt">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
            <html:button property="action" onclick="actionSubmit(0,'saveAssetCategory');">
                <app:storeMessage key="global.label.save"/>
            </html:button>
 <%/* TBD (TCS Bug 4059 will be deferred)
            <logic:greaterThan name="USER_ASSET_PROFILE_MGR_FORM" property="assetId" value="0">
              <html:button property="action" onclick="actionSubmit(0,'deleteAssetCategory');">
                <app:storeMessage key="global.action.label.delete"/>
              </html:button>
            </logic:greaterThan>
*/%>
            </logic:equal>
        </td>
    </tr>
    <tr>
        <td colspan="3" style="font-size: 3pt">&nbsp;</td>
    </tr>
<html:hidden property="assetData.statusCd" value="<%=RefCodeNames.ASSET_STATUS_CD.ACTIVE%>"/>
<html:hidden property="action" value="hiddenAction"/>
</html:form>
</table>

</div>






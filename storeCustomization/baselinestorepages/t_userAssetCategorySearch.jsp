<%--

  Title:        t_userAssetSearch.jsp
  Description:  search page
  Copyright:    Copyright (c) 2006
  Company:      CleanWise, Inc.
  Date:         28.12.2006
  Time:         22:27:26
  author        Alexander Chickin, TrinitySoft, Inc.

--%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.AssetView" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.cleanwise.view.forms.UserAssetMgrForm" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Locale" %>

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


<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String selectStr = ClwI18nUtil.getMessageOrNull(request, "global.action.label.select");
    if (selectStr != null) {
        selectStr = "-" + selectStr + "-";
    } else {
        selectStr = "-Select-";
    }
    String messageKey;
%>

<table align=center CELLSPACING=0 CELLPADDING=5 width="100%">
<tr>
<td>
<table>
<html:form name="USER_ASSET_MGR_FORM" action="/userportal/userAssets.do" scope="session"
           type="com.cleanwise.view.forms.UserAssetMgrForm">
    <logic:present name="<%=Constants.APP_USER%>">
        <logic:greaterThan name="<%=Constants.APP_USER%>" property="siteNumber" value="0">
            <tr>
                <td colspan="3">
                    <span style="font-size: 14px; font-weight: bold; line-height: 200%;">
                        <app:storeMessage key="global.action.label.search"/>&nbsp;<app:storeMessage key="userAssets.text.assetCategory"/>
                    </span>
                </td>
            </tr>
            
            <tr>
                <td width="25%">
                    <b><app:storeMessage key="userAssets.text.assetCategory"/>:</b>
                </td>
                <td>
                    <html:text styleId="searchField" name="USER_ASSET_MGR_FORM" property="searchField"/>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td colspan="3">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'SearchAsset');">
                        <app:storeMessage key="global.action.label.search"/>
                    </html:button>
                    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                        <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'CategoryCreate');">
                            <app:storeMessage key="global.action.label.create"/>
                        </html:button>
                    </app:authorizedForFunction>
                </td>
                <td></td>
            </tr>
            </table>
        </logic:greaterThan>
    </logic:present>
    <html:hidden property="assetType" value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>"/>
    <html:hidden property="action" value="hiddenAction"/>
    <html:hidden property="tabs" value="f_userAssetToolbar"/>
    <html:hidden property="display" value="t_userAssetCategorySearch"/>
</html:form>

</table>
<logic:present name="USER_ASSET_MGR_FORM" property="categoryAssetSearchResult">
<bean:size id="rescount" name="USER_ASSET_MGR_FORM" property="categoryAssetSearchResult"/>

<table align="center" width="100%">
<tr align="left">
    <td class="shopcharthead">
        <div style="margin-top: 5px;margin-left: 90px;margin-right: 2px;margin-bottom: 5px;">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetname&display=t_userAssetCategorySearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.text.assetCategoryName"/>
            </a>
        </div>
    </td>
</tr>


<logic:iterate id="arrele" name="USER_ASSET_MGR_FORM" property="categoryAssetSearchResult" indexId="i">
    <logic:present name="arrele" property="siteInfo">
        <bean:define id="siteInfo" name="arrele" property="siteInfo" type="java.util.HashMap"/>
        <bean:define id="eleid" name="arrele" property="assetId"/>
        <%
            ArrayList siteIds = (ArrayList) ((HashMap) siteInfo).get(AssetView.SITE_INFO.SITE_IDS);
            ArrayList siteNames = (ArrayList) ((HashMap) siteInfo).get(AssetView.SITE_INFO.SITE_NAMES);

            if (siteIds != null && siteIds.size() > 0) {
                for (int idx = 0; idx < siteIds.size(); idx++) { %>

        <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">

            <% if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(((AssetView) (arrele)).getAssetTypeCd())) { %>
            <td align="left">
                <bean:define id="eleid" name="arrele" property="assetId"/>
                <div style="margin-top: 5px;margin-left: 90px;">
                    <a href="../userportal/userAssetProfile.do?action=assetCategoryDetail&assetId=<%=eleid%>">
                        <bean:write name="arrele" property="assetName"/>
                    </a>
                </div>
            </td>

            <% } %>
        <% } %>
        <%} else {%>

        <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
            <%if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(((AssetView) (arrele)).getAssetTypeCd())) {%>
            
            <td align="left">
                <bean:define id="eleid" name="arrele" property="assetId"/>
                <div style="margin-top: 5px;margin-left: 90px;">
                    <a href="../userportal/userAssetProfile.do?action=assetCategoryDetail&assetId=<%=eleid%>">
                        <bean:write name="arrele" property="assetName"/>
                    </a>
                </div>
            </td>

            <% } %>
        </tr>
        <% } %>
    </logic:present>
</logic:iterate>
</table>

</logic:present>


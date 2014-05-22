<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.AssetView" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

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


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String selectStr = ClwI18nUtil.getMessageOrNull(request, "global.action.label.select");
    if (selectStr != null) {
        selectStr = "-" + selectStr + "-";
    } else {
        selectStr = "-Select-";
    }
%>

<div class="text">
    <table ID=1582 width="769" cellspacing="0" border="0" class="mainbody">
        <html:form styleId="1583" name="USER_ASSET_MGR_FORM" action="/storeportal/searchMasterAssetCategory.do"
                scope="session" type="com.cleanwise.view.forms.UserAssetMgrForm">
    <tr>
        <td colspan="4">
            <span style="font-size: 14px; font-weight: bold; line-height: 200%;">
                <app:storeMessage key="global.action.label.search"/>&nbsp;<app:storeMessage key="userAssets.text.assetCategory"/>
            </span>
        </td>
    </tr>

    <tr>
        <td width="20%" align="right">
            <b><app:storeMessage key="userAssets.text.assetCategoryName"/>:</b>
        </td>
        <td width="2%"></td>
        <td>
            <html:text styleId="searchField" size="25" name="USER_ASSET_MGR_FORM" property="searchField"/>
        </td>
        <td align="left" nowrap="nowrap">
            <html:radio name="USER_ASSET_MGR_FORM" property="nameSearchType"
                        value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
            <app:storeMessage key="admin2.search.criteria.label.startsWith"/>
            <html:radio name="USER_ASSET_MGR_FORM" property="nameSearchType"
                        value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
            <app:storeMessage key="admin2.search.criteria.label.contains"/>
        </td>
    </tr>
    <tr>
        <td colspan="3">&nbsp;</td>
        <td width="45%" align="left">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2">&nbsp;</td>
        <td>
            <html:button property="action" onclick="actionSubmit(0,'SearchStoreAsset');">
                <app:storeMessage key="global.action.label.search"/>
            </html:button>
            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <html:button property="action" onclick="actionSubmit(0,'CategoryCreate');">
                    <app:storeMessage key="admin.button.create"/>
                </html:button>
            </app:authorizedForFunction>
        </td>
        <td>
            <%//<html:checkbox name="USER_ASSET_MGR_FORM" property="showInactive"/><app:storeMessage key="global.label.showInactive"/>%>
        </td>
    </tr>
    <tr>
        <td colspan="4">&nbsp;</td>
    </tr>
    <tr class="results">
        <td colspan="4"></td>
    </tr>
    <tr>
        <td colspan="4" style="font-size: 3pt">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="4" align="left">
            <logic:present name="USER_ASSET_MGR_FORM" property="categoryAssetSearchResult">
                <bean:size id="rescount" name="USER_ASSET_MGR_FORM" property="categoryAssetSearchResult"/>
                &nbsp;&nbsp;Count:&nbsp;<%=rescount%>
            </logic:present>
            <logic:notPresent name="USER_ASSET_MGR_FORM" property="categoryAssetSearchResult">
                &nbsp;&nbsp;Count:&nbsp;0
            </logic:notPresent>
        </td>
    </tr>
    <tr>
        <td colspan="4" style="font-size: 3pt">&nbsp;</td>
    </tr>
    <html:hidden property="assetType" value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>"/>
    <html:hidden property="action" value="hiddenAction"/>
</table>

<logic:present name="USER_ASSET_MGR_FORM" property="categoryAssetSearchResult">
<logic:greaterThan name="rescount" value="0">
<div style="width: <%=Constants.TABLEWIDTH%>; overflow-x:auto; overflow-y:hidden;">
<table width="<%=Constants.TABLEWIDTH%>">
    <tr align="left">
        <td>
            <div class="fivemargin">
                <a class="storeassetsearchlinks" href="searchMasterAssetCategory.do?action=sort_assets&sortField=assetname">
                    <app:storeMessage key="userAssets.text.assetCategoryName"/>
                </a>
            </div>
        </td>
    </tr>

    <logic:iterate id="arrele" name="USER_ASSET_MGR_FORM" property="categoryAssetSearchResult" indexId="i">
        <bean:define id="eleid" name="arrele" property="assetId"/>
        <%
        String bgcolor = "";
        if (i % 2 == 0 ) {
            bgcolor = "rowa";
        } else {
            bgcolor = "rowb";
        }
        %>
        <tr class='<%=bgcolor%>'>
            <% if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(((AssetView) (arrele)).getAssetTypeCd())) { %>
                <td align="left">
                    <bean:define id="eleid" name="arrele" property="assetId"/>
                    <a href="../storeportal/masterAssetCategoryProfile.do?action=storeMasterAssetCategoryDetail&assetId=<%=eleid%>">
                        <bean:write name="arrele" property="assetName"/>
                    </a>
                </td>
            <% } %>
        </tr>
    </logic:iterate>
</table>
</div>
 </logic:greaterThan>
</logic:present>
</div>

</html:form>






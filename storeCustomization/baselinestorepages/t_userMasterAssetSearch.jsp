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
    String actionPath = "";
    String servletPath = request.getServletPath();
    if(servletPath.startsWith("/storeportal/"))
    {
    	actionPath = "/storeportal/searchMasterAssets.do";
    }
    else
    {
    	actionPath = "/userportal/userAssets.do"; 
    }
%>

<table>
<tr>
<td>
<table align="center" cellspacing="0" cellpadding="0" width="100%">
<html:form name="USER_ASSET_MGR_FORM" action="<%=actionPath%>" scope="session"
           type="com.cleanwise.view.forms.UserAssetMgrForm">
    <logic:present name="<%=Constants.APP_USER%>">
        <logic:greaterThan name="<%=Constants.APP_USER%>" property="siteNumber" value="0">
            <tr>
                <td colspan="3">
                    <span style="font-size: 14px; font-weight: bold; line-height: 200%;">
                        <app:storeMessage key="global.action.label.search"/>&nbsp;<app:storeMessage key="userAssets.text.assetMasterAsset"/>
                    </span>
                </td>
            </tr>
            
            <tr>
                <td width="25%">
                    <b><app:storeMessage key="userAssets.text.assetCategory"/>:</b>
                </td>
                <td>
                    <html:select name="USER_ASSET_MGR_FORM" property="searchCategoryId">
                        <html:option value="0">
                            <%=selectStr%>
                        </html:option>
                        <logic:present name="Store.category.vector">
                            <logic:iterate id="category" name="Store.category.vector"
                                           type="com.cleanwise.service.api.value.PairView">
                                <bean:define id="categoryId" name="category" property="object1" type="java.lang.Integer"/>
                                <html:option value="<%=categoryId.toString()%>">
                                    <bean:write name="category" property="object2"/>
                                </html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td width="25%">
                    <b><app:storeMessage key="userAssets.text.assetName"/>:</b>
                </td>
                <td>
                    <html:text styleId="searchField" name="USER_ASSET_MGR_FORM" property="searchField"/>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td width="25%">
                    <b><app:storeMessage key="userAssets.shop.text.param.modelNumber"/>:</b>
                </td>
                <td>
                    <html:text styleId="searchModel" name="USER_ASSET_MGR_FORM" property="searchModel"/>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td width="25%">
                    <b><app:storeMessage key="userAssets.shop.text.param.manufacturer"/>:</b>
                </td>
                <td>
                    <html:select name="USER_ASSET_MGR_FORM" property="searchManufacturerId">
                        <html:option value="0">
                            <%=selectStr%>
                        </html:option>
                        <logic:present name="Store.manufacturer.vector">
                            <logic:iterate id="manufacturer" name="Store.manufacturer.vector"
                                           type="com.cleanwise.service.api.value.PairView">
                                <bean:define id="manufacturerId" name="manufacturer" property="object1" type="java.lang.Integer"/>
                                <html:option value="<%=manufacturerId.toString()%>">
                                    <bean:write name="manufacturer" property="object2"/>
                                </html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td width="25%">
                    <b><app:storeMessage key="userAssets.shop.text.param.manufacturerSku"/>:</b>
                </td>
                <td>
                    <html:text styleId="searchManufSku" name="USER_ASSET_MGR_FORM" property="searchManufSku"/>
                </td>
                <td>&nbsp;</td>
            </tr>    
            <tr>
                <td width="25%">
                    <b><app:storeMessage key="userAssets.text.assetMasterAssetID"/>:</b>
                </td>
                <td>
                    <html:text styleId="searchNumber" name="USER_ASSET_MGR_FORM" property="searchNumber"/>
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
                        <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'MasterAssetCreate');">
                            <app:storeMessage key="global.action.label.create"/>
                        </html:button>
                    </app:authorizedForFunction>
                </td>
                <td>
                    <html:checkbox name="USER_ASSET_MGR_FORM" property="showInactive"/><app:storeMessage key="global.label.showInactive"/>
                </td>
            </tr>
            </table>
        </logic:greaterThan>
    </logic:present>
    <html:hidden property="assetType" value="<%=RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET%>"/>
    <html:hidden property="action" value="hiddenAction"/>
    <html:hidden property="tabs" value="f_userAssetToolbar"/>
    <html:hidden property="display" value="t_userMasterAssetSearch"/>


</table>
<logic:present name="USER_ASSET_MGR_FORM" property="masterAssetSearchResult">
<bean:size id="rescount" name="USER_ASSET_MGR_FORM" property="masterAssetSearchResult"/>

<table width="100%"><tr><td>
<table align=left width="<%=Constants.TABLEWIDTH%>">
<tr align=left>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetnumber&display=t_userMasterAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.text.assetMasterAssetID"/>
            </a></div>
    </td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetcategory&display=t_userMasterAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.text.assetCategory"/>
            </a></div>
    </td>
    <td  class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetname&display=t_userMasterAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.text.assetName"/>
            </a></div>
    </td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetmodel&display=t_userMasterAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.text.modelNum"/>
            </a>
        </div>
    </td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetmanufacturer&display=t_userMasterAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.shop.text.param.manufacturer"/>
            </a>
        </div>
    </td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetmanufacturersku&display=t_userMasterAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.shop.text.param.manufacturerSku"/>
            </a>
        </div>
    </td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetstatus&display=t_userMasterAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.shop.text.param.status"/>
            </a>
        </div>
    </td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks">
                <app:storeMessage key="userWorkOrder.text.actionCd"/>
            </a>
        </div>
    </td>
</tr>

<logic:iterate id="arrele" name="USER_ASSET_MGR_FORM" property="masterAssetSearchResult" indexId="i">
    <bean:define id="eleid" name="arrele" property="assetId"/>
    <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
        <td>
            <bean:write name="arrele" property="assetNumber"/>
        </td>
        <td>
            <bean:write name="arrele" property="assetCategory"/>
        </td>
        <td>
            <a href="../userportal/userAssetProfile.do?action=masterAssetDetail&assetId=<%=eleid%>">
                <bean:write name="arrele" property="assetName"/>
            </a>
        </td>
        <td>
            <bean:write name="arrele" property="modelNumber"/>
        </td>
        <td>
            <bean:write name="arrele" property="manufName"/>
        </td>
        <td>
            <bean:write name="arrele" property="manufSku"/>
        </td>
        <td>
            <bean:write name="arrele" property="status"/>
        </td>
        <td>
            <logic:equal name="arrele" property="status" value="<%=RefCodeNames.ASSET_STATUS_CD.ACTIVE%>">
                <a href="../userportal/userAssets.do?action=ManagedAssetCreate&masterAssetId=<%=eleid%>">
                    <app:storeMessage key="userAssets.text.createAsset"/>
                </a>
            </logic:equal>
            <logic:notEqual name="arrele" property="status" value="<%=RefCodeNames.ASSET_STATUS_CD.ACTIVE%>">
                &nbsp;
            </logic:notEqual>
        </td>
    </tr>
</logic:iterate>
 
</table>
</td></tr></table>

</logic:present>
</html:form>

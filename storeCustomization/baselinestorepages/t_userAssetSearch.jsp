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
//window.onload = function() {
//    document.getElementById('searchField').focus();
    //alert("onLoad");
//}

document.onkeypress = freeKeyHit;

function freeKeyHit(evt) {
    var evt = (evt) ? evt : event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;

    if (charCode == 13) {
        actionSubmit('11111', 'SearchAsset');
    }
}

function actionSubmit(form_id, action) {
    var form = document.getElementById(form_id);
    var actions = form["action"];
    //alert(actions.length);
    for(ii = actions.length-1; ii >= 0; ii--) {
        if(actions[ii].value == 'hiddenAction') {
            actions[ii].value = action;
            form.submit();
        break;
        }
    }
return false;
}

function enterKey (evt, form_id, action) {
    var evt = (evt) ? evt : event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;

    if (charCode == 13) {
        actionSubmit(form_id, action);
    }
}

function buttonLink(link) {
    window.location.href = link;
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

<table align="center" cellspacing="0" cellpadding="5" width="100%">
<tr>
    <td>
        <table align="center" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td colspan="3">
                <span style="font-size: 14px; font-weight: bold; line-height: 200%;">
                    <app:storeMessage key="global.action.label.search"/>&nbsp;<app:storeMessage key="userAssets.text.asset"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <jsp:include flush='true' page="t_userLocateStoreAccount.jsp">
                   <jsp:param name="jspFormAction" value="/userportal/userAssets.do" /> 
                   <jsp:param name="jspFormName" value="USER_ASSET_MGR_FORM" /> 
                   <jsp:param name="jspSubmitIdent" value="" /> 
                   <jsp:param name="jspReturnFilterProperty" value="filterAccounts" /> 
                </jsp:include>
            </td>
        </tr>

        </table>
    </td>
</tr>
<tr>
    <td>
        <html:form name="USER_ASSET_MGR_FORM" action="/userportal/userAssets.do" scope="session" styleId="11111"
                               type="com.cleanwise.view.forms.UserAssetMgrForm">
        <logic:present name="<%=Constants.APP_USER%>">
            <logic:greaterThan name="<%=Constants.APP_USER%>" property="siteNumber" value="0">
        <table align="center" cellspacing="0" cellpadding="0" width="100%">       
            <tr>
                <td>&nbsp;</td>
                <td colspan="2">
                    <jsp:include flush='true' page="t_userLocateStoreAccountButtons.jsp">
                        <jsp:param name="jspFormName" value="USER_ASSET_MGR_FORM" /> 
                        <jsp:param name="jspReturnFilterProperty" value="filterAccounts" /> 
                    </jsp:include>
                </td>
            </tr>
                        
            <tr>
                <td colspan="3">&nbsp;</td>
            </tr>
            
            <tr>
                <td width="15%">
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
                <td width="15%">
                    <b><app:storeMessage key="userAssets.text.assetName"/>:</b>
                </td>
                <td>
                    <html:text styleId="searchField" name="USER_ASSET_MGR_FORM" property="searchField" onkeypress="javascript:enterKey(event,'11111','SearchAsset');"/>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td width="15%">
                    <b><app:storeMessage key="userAssets.shop.text.param.modelNumber"/>:</b>
                </td>
                <td>
                    <html:text styleId="searchModel" name="USER_ASSET_MGR_FORM" property="searchModel" onkeypress="javascript:enterKey(event,'11111','SearchAsset');"/>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td width="15%">
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
                <td width="15%">
                    <b><app:storeMessage key="userAssets.shop.text.param.manufacturerSku"/>:</b>
                </td>
                <td>
                    <html:text styleId="searchManufSku" name="USER_ASSET_MGR_FORM" property="searchManufSku" onkeypress="javascript:enterKey(event,'11111','SearchAsset');"/>
                </td>
                <td>&nbsp;</td>
            </tr>    
            <tr>
                <td width="15%">
                    <b><app:storeMessage key="userAssets.shop.text.param.assetnumber"/>:</b>
                </td>
                <td>
                    <html:text styleId="searchNumber" name="USER_ASSET_MGR_FORM" property="searchNumber" onkeypress="javascript:enterKey(event,'11111','SearchAsset');"/>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td width="15%">
                    <b><app:storeMessage key="userAssets.shop.text.param.serialnumber"/>:</b>
                </td>
                <td>
                    <html:text styleId="searchSerial" name="USER_ASSET_MGR_FORM" property="searchSerial" onkeypress="javascript:enterKey(event,'11111','SearchAsset');"/>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td colspan="3">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <html:button property="action" styleClass="store_fb" onclick="actionSubmit('11111','SearchAsset');">
                        <app:storeMessage key="global.action.label.search"/>
                    </html:button>
                    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                        <html:button property="action" styleClass="store_fb" onclick="buttonLink('../userportal/userAssets.do?action=AssetCreate');">
                            <app:storeMessage key="global.action.label.create"/>
                        </html:button>
                    </app:authorizedForFunction>
                </td>
                <td width="50%" align="left">
                    <html:checkbox name="USER_ASSET_MGR_FORM" property="showInactive"/><app:storeMessage key="global.label.showInactive"/>
                </td>
            </tr>
        </table>
            </logic:greaterThan>
        </logic:present>
    <html:hidden property="assetType" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>"/>
    <html:hidden property="action" value="hiddenAction"/>
    <html:hidden property="tabs" value="f_userAssetToolbar"/>
    <html:hidden property="display" value="t_userAssetSearch"/>
    </html:form>
    </td>
</tr>
</table>

<logic:present name="USER_ASSET_MGR_FORM" property="assetSearchResult">
<bean:size id="rescount" name="USER_ASSET_MGR_FORM" property="assetSearchResult"/>

<table align=center width="100%">
<tr align=left>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetname&display=t_userAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.text.assetName"/>
            </a></div>
    </td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetnumber&display=t_userAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.text.assetNumber"/>
            </a></div>
    </td>
    <td  class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetserial&display=t_userAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.text.assetSerial"/>
            </a></div>
    </td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetcategory&display=t_userAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.text.assetCategory"/>
            </a>
        </div>
    </td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetdateinservice&display=t_userAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="userAssets.text.dateInService"/>
            </a>
        </div>
    </td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="assetsearchlinks" href="userAssets.do?action=sort_assets&sortField=assetsitename&display=t_userAssetSearch&tabs=f_userAssetToolbar">
                <app:storeMessage key="msbSites.text.siteName"/>
            </a>
        </div>
    </td>
</tr>


<logic:iterate id="arrele" name="USER_ASSET_MGR_FORM" property="assetSearchResult" indexId="i">
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

            <td colspan="6" align="center">
                <bean:define id="eleid" name="arrele" property="assetId"/>
                <a href="../userportal/userAssetProfile.do?action=assetdetail&assetId=<%=eleid%>">
                    <bean:write name="arrele" property="assetName"/>
                </a>
            </td>

            <%} else {%>

            <td>

                <a href="../userportal/userAssetProfile.do?action=assetdetail&assetId=<%=eleid%>&siteId=<%=((Integer)siteIds.get(idx)).toString()%>">
                    <bean:write name="arrele" property="assetName"/>
                </a>
            </td>
            <td>
                <bean:write name="arrele" property="assetNumber"/>
            </td>
            <td>
                <bean:write name="arrele" property="serialNumber"/>
            </td>
            <td>
                <bean:write name="arrele" property="assetCategory"/>
            </td>
            <td>
                <logic:present name="arrele" property="dateInService">
                    <bean:define id="dateInService" name="arrele" property="dateInService" type="java.lang.String"/>
                    <%if (Utility.isSet(dateInService)) { %>
                    <%=ClwI18nUtil.formatDate(request,
                            ClwI18nUtil.convertDateToRequest(Locale.US, request, dateInService),
                            DateFormat.DEFAULT)%>
                    <%}%>
                </logic:present>
            </td>
            <td>
                <%=siteNames.get(idx).equals("") ? "-" : (String) siteNames.get(idx)%>
            </td>
        </tr>

        <% } %>
        <% } %>
        <%} else {%>

        <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
            <%if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(((AssetView) (arrele)).getAssetTypeCd())) {%>

            <td colspan="6" align="center">
                <bean:define id="eleid" name="arrele" property="assetId"/>
                <a href="../userportal/userAssetProfile.do?action=assetdetail&assetId=<%=eleid%>">
                    <bean:write name="arrele" property="assetName"/>
                </a>
            </td>

            <%} else if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(((AssetView) (arrele)).getAssetTypeCd())) { %>

            <td>
                <bean:define id="eleid" name="arrele" property="assetId"/>
                <a href="../userportal/userAssetProfile.do?action=masterAssetDetail&assetId=<%=eleid%>">
                    <bean:write name="arrele" property="assetName"/>
                </a>
            </td>
            <td>&nbsp;</td>
            <td>
                <bean:write name="arrele" property="serialNumber"/>
            </td>
            <td>
                <bean:write name="arrele" property="assetCategory"/>
            </td>
            <td colspan="2">&nbsp;</td>

            <%} else {%>

            <td>
                <bean:define id="eleid" name="arrele" property="assetId"/>
                <a href="../userportal/userAssetProfile.do?action=assetdetail&assetId=<%=eleid%>">
                    <bean:write name="arrele" property="assetName"/>
                </a>
            </td>
            <td>
                <bean:write name="arrele" property="assetNumber"/>
            </td>
            <td>
                <bean:write name="arrele" property="serialNumber"/>
            </td>
            <td>
                <bean:write name="arrele" property="assetCategory"/>
            </td>
            <td>
                <logic:present name="arrele" property="dateInService">
                    <bean:define id="dateInService" name="arrele" property="dateInService" type="java.lang.String"/>
                    <%if (Utility.isSet(dateInService)) { %>
                    <%=ClwI18nUtil.formatDate(request,
                            ClwI18nUtil.convertDateToRequest(Locale.US, request, dateInService),
                            DateFormat.DEFAULT)%>
                    <%}%>
                </logic:present>
            </td>
            <td>
                -
            </td>
            <%}%>
        </tr>
        <%

            }

        %>
    </logic:present>
</logic:iterate>
</table>

</logic:present>

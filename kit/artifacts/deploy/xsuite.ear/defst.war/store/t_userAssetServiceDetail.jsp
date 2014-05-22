<%--
 Title:        t_userAssetServiceDetail
 Description:  services shopping detail.
 Purpose:      views services shopping detail of the asset.
 Copyright:    Copyright (c) 2007
 Company:      CleanWise, Inc.
 Date:         19.01.2007
 Time:         00:29:11
 @author       Alexander Chickin, TrinitySoft, Inc.

--%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.AssetPropertyData" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.SiteData" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<style>
    .bb {
        font-family: Tahoma, Verdana, sans-serif;
        color: #e76931;
        font-weight: heavy;
        font-size: 12px;
        border: solid 1px black;
        width: 120;
    }

    a.linkNotLine {
        color: #000000;
        font-weight: normal;
        text-decoration: none
    }

</style>

<script language="JavaScript1.2">
    <!--

    function setAndSubmit(fid, value) {
        var dml = document.forms[0];
        dml['action'].value = value;
        dml.submit();
    }
    function viewPrinterFriendly(loc) {
        var prtwin = window.open(loc,"view_docs",
                "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
        prtwin.focus();
        return false;
    }
    //-->

</script>
<div class="text"><font color=red>
    <html:errors/>
</font></div>
<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">
    <% String f_serviceShopToolbar = ClwCustomizer.getStoreFilePath(request, "f_serviceShopToolbar.jsp"); %>
    <jsp:include flush='true' page="<%=f_serviceShopToolbar%>"/>
</table>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<bean:define id="theForm" name="USER_SERVICE_SHOP_FORM" type="com.cleanwise.view.forms.UserAssetProfileMgrForm"/>

<bean:define id="assetProperties" name="USER_SERVICE_SHOP_FORM" property="assetPropertiesMap"
             type="java.util.HashMap"/>


<%
    String storeDir = ClwCustomizer.getStoreDir();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    AssetPropertyData acquisitionDate = (AssetPropertyData) ((HashMap) assetProperties).get(RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_DATE);
    AssetPropertyData acquisitionCost = (AssetPropertyData) ((HashMap) assetProperties).get(RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_COST);
    AssetPropertyData image = (AssetPropertyData) ((HashMap) assetProperties).get(RefCodeNames.ASSET_PROPERTY_TYPE_CD.IMAGE);
    AssetPropertyData longDesc = (AssetPropertyData) ((HashMap) assetProperties).get(RefCodeNames.ASSET_PROPERTY_TYPE_CD.LONG_DESC);
    AssetPropertyData inactiveReason = (AssetPropertyData) ((HashMap) assetProperties).get(RefCodeNames.ASSET_PROPERTY_TYPE_CD.INACTIVE_REASON);
    AssetPropertyData dateInService = (AssetPropertyData) ((HashMap) assetProperties).get(RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_IN_SERVICE);
    AssetPropertyData lastHMR = (AssetPropertyData) ((HashMap) assetProperties).get(RefCodeNames.ASSET_PROPERTY_TYPE_CD.LAST_HOUR_METER_READING);
    AssetPropertyData dateLastHMR = (AssetPropertyData) ((HashMap) assetProperties).get(RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_LAST_HOUR_METER_READING);
%>
<table align = "center" border = "0" cellpadding = "0" cellspacing = "0" width = "<%=Constants.TABLEWIDTH%>">

<html:form action="/store/servicesShop.do" name="USER_SERVICE_SHOP_FORM"        type="com.cleanwise.view.forms.UserAssetProfileMgrForm">


<!-- item picture and long description -->

<tr>
<td class="tableoutline" width="1">
    <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1">
</td>
<td class="text" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
<tr>
    <td colspan="2">&nbsp;</td>
</tr>
<tr>
    <td>
        <%
            if (image != null && image.getValue().trim().length() > 0) {
        %>
        <img src="/<%=storeDir%>/<%=image.getValue()%>">
        <% } else { %>
        &nbsp;
        <% } %>
    </td>
    <td align="left" valign="top">
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td width="3%">&nbsp;</td>
                <td class="customerltbkground" valign="top">
                    <div align="center" class="itemheadmargin">
                    <span class="shopitemhead">
                        <bean:write name="USER_SERVICE_SHOP_FORM" property="assetData.shortDesc"/>
                    </span>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="text">
                    <div class="itemheadmargin">
                        <%
                            if (longDesc != null && longDesc.getValue().trim().length() > 0) {
                        %>
                        <%=longDesc.getValue()%>
                        <% } else { %>
                        &nbsp;
                        <% } %>
                    </div>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
<td colspan="2">
<table width="100%">
<tr>
    <td colspan="4"><span class="shopassetdetailtxt"> <strong>
        <app:storeMessage key="userAssets.shop.text.param.manufacturer"/>
        : </strong> </span>
        <logic:present name="USER_SERVICE_SHOP_FORM" property="assetData.manufName">
                  <span class="shopassetdetailtxt">
                      <bean:write name="USER_SERVICE_SHOP_FORM" property="assetData.manufName"/>
                  </span>
        </logic:present>
    </td>
</tr>
<tr>
    <td colspan="2" width="40%"><span class="shopassetdetailtxt"><strong>
        <app:storeMessage key="userAssets.shop.text.param.manufacturerSku"/>
        : </strong> </span>
        <logic:present name="USER_SERVICE_SHOP_FORM" property="assetData.manufSku">
                  <span class="shopassetdetailtxt">
                      <bean:write name="USER_SERVICE_SHOP_FORM" property="assetData.manufSku"/>
                  </span>
        </logic:present>
    </td>
    <td colspan="2">&nbsp;</td>
</tr>
<tr>
    <td colspan="2"><span class="shopassetdetailtxt"><strong>
        <app:storeMessage key="userAssets.shop.text.param.assetnumber"/>
        : </strong></span>
        <logic:present name="USER_SERVICE_SHOP_FORM" property="assetData.assetNum">
				  <span class="shopassetdetailtxt">
				  <bean:write name="USER_SERVICE_SHOP_FORM" property="assetData.assetNum"/>
                  </span>
        </logic:present>
    </td>
    <td colspan="2">
           <span class="shopassetdetailtxt"><strong>
               <app:storeMessage key="userAssets.shop.text.param.dateInService"/>
               : </strong></span>
        <%if (dateInService != null) {%>
				  <span class="shopassetdetailtxt">
            <%= ClwI18nUtil.formatDate(request, dateInService.getValue(), DateFormat.DEFAULT)%>
        </span>
        <%}%>
    </td>
</tr>
<tr>
    <td colspan="2"><span class="shopassetdetailtxt"> <strong>
        <app:storeMessage key="userAssets.shop.text.param.serialnumber"/>
        : </strong> </span>
        <logic:present name="USER_SERVICE_SHOP_FORM" property="assetData.serialNum">
				  <span class="shopassetdetailtxt">
				  <bean:write name="USER_SERVICE_SHOP_FORM" property="assetData.serialNum"/>
                  </span>
        </logic:present>
    </td>
    <td colspan="2"><span class="shopassetdetailtxt"> <strong>
        <app:storeMessage key="userAssets.shop.text.param.modelNumber"/>
        : </strong> </span>
        <logic:present name="USER_SERVICE_SHOP_FORM" property="assetData.modelNumber">
				  <span class="shopassetdetailtxt">
				  <bean:write name="USER_SERVICE_SHOP_FORM" property="assetData.modelNumber"/>
                  </span>
        </logic:present>
    </td>
</tr>
<tr>
    <td colspan="2"><span class="shopassetdetailtxt"><strong>
        <app:storeMessage key="userAssets.shop.text.param.acquisitiondate"/>
        : </strong></span>
        <%
            if (acquisitionDate != null && acquisitionDate.getValue().trim().length() > 0) {
        %>
        <span class="shopassetdetailtxt">
            <%= ClwI18nUtil.formatDate(request, acquisitionDate.getValue(), DateFormat.DEFAULT)%>
        </span> <%}%>
    </td>
    <td colspan="2">
        <span class="shopassetdetailtxt">
        <strong><app:storeMessage key="userAssets.shop.text.param.acquisitioncost"/> : </strong>
        </span>
        <% if (acquisitionCost != null && acquisitionCost.getValue().trim().length() > 0) { %>
        <span class="shopassetdetailtxt"><%=
            ClwI18nUtil.getPriceShopping(request, acquisitionCost.getValue(), "&nbsp;")%>
        </span> <%}%>
    </td>
</tr>
<tr>
    <td colspan="2"><span class="shopassetdetailtxt"><strong>
        <app:storeMessage key="userAssets.shop.text.param.lastHMR"/>
        : </strong></span>
        <%
            if (lastHMR != null && lastHMR.getValue().trim().length() > 0) {
        %>
        <span class="shopassetdetailtxt">
            <%= lastHMR.getValue()%>
        </span> <%}%>
    </td>
    <td colspan="2">
        <span class="shopassetdetailtxt">
        <strong><app:storeMessage key="userAssets.shop.text.param.dateLastHMR"/> : </strong>
        </span>
        <% if (dateLastHMR != null && dateLastHMR.getValue().trim().length() > 0) { %>
        <span class="shopassetdetailtxt">
            <%= ClwI18nUtil.formatDate(request, dateLastHMR.getValue(), DateFormat.DEFAULT)%>

        </span> <%}%>
    </td>
</tr>
<tr>
    <td colspan="4"><span class="shopassetdetailtxt"><strong>
        <app:storeMessage key="userAssets.shop.text.param.status"/>
        : </strong></span>
        <bean:write name="USER_SERVICE_SHOP_FORM" property="assetData.statusCd"/>
        <%
            if (inactiveReason != null) { %><%="(" + inactiveReason.getValue() + ")"%>
        <%}%>
    </td>
</tr>
<tr><td colspan="4">
    <logic:present name="USER_SERVICE_SHOP_FORM" property="assetWarrantyAssoc">
        <bean:size id="rescount" name="USER_SERVICE_SHOP_FORM" property="assetWarrantyAssoc"/>
        <table width="100%" CELLSPACING=0 CELLPADDING=0>
            <tr>
                <td colspan="3" align="center">
                        <span class="shopassetdetailtxt"><strong>
                            <app:storeMessage key="userWarranty.text.toolbar.warranty"/>
                        </strong></span>
                </td>
            </tr>
            <tr class="tableheaderinfo">
                <td width="33%" class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.warrantyNumber"/>
                    </div>
                </td>

                <td width="33%" class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.warrantyType"/>
                    </div>
                </td>
                <td width="33%" class="shopcharthead">
                    <div class="fivemargin"><app:storeMessage key="userWarranty.text.warrantyDuration"/>
                    </div>
                </td>
            </tr>
            <logic:iterate id="arrele" name="USER_SERVICE_SHOP_FORM" property="assetWarrantyAssoc" indexId="i">
                <tr>
                    <td align="center">
                        <bean:define id="eleid" name="arrele" property="warrantyId"/>
                        <a href="../userportal/userWarranty.do?action=detail&warrantyId=<%=eleid%>&display=t_userWarrantyDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar">
                            <bean:write name="arrele" property="warrantyNumber"/>
                        </a>
                    </td>
                    <td align="center">
                        <bean:write name="arrele" property="typeCd"/>
                    </td>
                    <td align="center">
                        <bean:write name="arrele" property="duration"/>
                    </td>
                </tr>
            </logic:iterate>
        </table>
    </logic:present>
</td></tr>
<tr><td colspan="4">&nbsp;</td></tr>
<tr><td colspan="4">
    <logic:present name="USER_SERVICE_SHOP_FORM" property="assetWorkOrderAssoc">
        <bean:size id="rescount" name="USER_SERVICE_SHOP_FORM" property="assetWorkOrderAssoc"/>
        <table width="100%"  CELLSPACING=0 CELLPADDING=0>
             <tr>
                <td colspan="3" align="center">
                        <span class="shopassetdetailtxt"><strong>
                            <app:storeMessage key="userWorkOrder.text.toolbar"/>
                        </strong></span>
                </td></tr>
            <tr class="tableheaderinfo">
                <td width="33%" class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="userWorkOrder.text.workOrderNumber"/>
                     </div>
                </td>

                <td width="33%" class="shopcharthead">
                    <div class="fivemargin">
                      <app:storeMessage key="userWorkOrder.text.workOrderName"/>
                  </div>
                </td>

                <td width="33%" class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="userWorkOrder.text.type"/>
                    </div>
                </td>
                </tr>

            <logic:iterate id="arrele" name="USER_SERVICE_SHOP_FORM" property="assetWorkOrderAssoc" indexId="i">
                <bean:define id="eleid" name="arrele" property="workOrderId"/>
                <tr>
                    <td align="center">
                        <bean:write name="arrele" property="workOrderId"/>
                    </td>
                    <td align="center">
                        <logic:present name="arrele" property="shortDesc">
                            <a href="../userportal/userWorkOrderDetail.do?action=detail&workOrderId=<%=eleid%>&display=t_userWorkOrderDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                <bean:write name="arrele" property="shortDesc"/>
                            </a>
                        </logic:present>
                    </td>
                    <td align="center">
                        <logic:present name="arrele" property="typeCd">
                            <bean:write name="arrele" property="typeCd"/>
                        </logic:present>
                    </td>
                </tr>
            </logic:iterate>
        </table>
    </logic:present>

</td></tr>
<tr><td colspan="4">&nbsp;</td></tr>
<tr><td colspan="4">
    <table width="100%" border="0" cellspacing="0">
        <tr>
            <td colspan="4" align="center"><span class="shopassetdetailtxt"><b>
                <app:storeMessage key="userWarranty.text.assocDocs"/>
            </b></span></td>
        </tr>

        <tr>
            <td class="shopcharthead">
                <div class="fivemargin">
                    <app:storeMessage key="userWarranty.text.assocDocs.description"/>
                </div>
            </td>
            <td class="shopcharthead">
                <div class="fivemargin">
                    <app:storeMessage key="userWarranty.text.assocDocs.fileName"/>
                </div>
            </td>

            <td class="shopcharthead">
                <div class="fivemargin">
                    <app:storeMessage key="userWarranty.text.assocDocs.addDate"/>
                </div>
            </td>

            <td class="shopcharthead">
                <div class="fivemargin">
                    <app:storeMessage key="userWarranty.text.assocDocs.addBy"/>
                </div>
            </td>
        </tr>
        <logic:present name="USER_SERVICE_SHOP_FORM" property="contents">
            <logic:iterate id="contentV" name="USER_SERVICE_SHOP_FORM" property="contents"
                           type="com.cleanwise.service.api.value.AssetContentView" indexId="j">
                <logic:present name="contentV" property="content">
                    <logic:present name="contentV" property="assetContentData">
                        <bean:define id="acId" name="contentV" property="assetContentData.assetContentId"/>
                        <tr id="docs<%=((Integer) j).intValue()%>">
                        <td>
                            <logic:present name="contentV" property="content.shortDesc">
                                <bean:write name="contentV" property="content.shortDesc"/>
                            </logic:present>

                        </td>
                        <td><%
                            String fileName = "";
                            if (contentV.getContent().getPath() != null) {
                                fileName = contentV.getContent().getPath();
                                if (fileName.indexOf("/") > -1) {
                                    fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                                }
                            }
                        %>
                            <%String loc = "../store/servicesShop.do?action=readDocs&assetContentId=" + acId;%>
                            <a href="#" onclick="viewPrinterFriendly('<%=loc%>');"><%=fileName%>
                            </a>
                        </td>
                        <td>
                            <logic:present name="contentV" property="content.addDate">
                                <%=ClwI18nUtil.formatDate(request, contentV.getContent().getAddDate(), DateFormat.DEFAULT)%>
                            </logic:present>
                        </td>
                        <td>
                            <logic:present name="contentV" property="content.addBy">
                                <bean:write name="contentV" property="content.addBy"/>
                            </logic:present>
                        </td>
                    </logic:present>
                </logic:present>
                </tr>
            </logic:iterate>
        </logic:present>
    </table>
</td></tr>
<tr><td colspan="4">&nbsp;</td></tr>
<tr><td colspan="4">&nbsp;</td></tr>
<tr>
    <td width="10%">&nbsp;</td>
    <td></td>
    <td>&nbsp;</td>
    <td align="right">
        <logic:present name="USER_SERVICE_SHOP_FORM" property="assetLocationData">
            <table>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt"><strong>
                            <app:storeMessage key="userAssets.shop.text.paramheader.assetlocation"/>
                        </strong></span>
                    </td>
                </tr>
                <tr>
                    <td><span class="shopassetdetailtxt"><bean:write name="USER_SERVICE_SHOP_FORM"
                                                                     property="assetLocationData.address1"/></span>
                    </td>
                </tr>
                <tr>
                    <td><span class="shopassetdetailtxt">
                    <bean:write name="USER_SERVICE_SHOP_FORM" property="assetLocationData.city"/>
                    ,<bean:write name="USER_SERVICE_SHOP_FORM" property="assetLocationData.stateProvinceCd"/>
                    &nbsp;<bean:write name="USER_SERVICE_SHOP_FORM" property="assetLocationData.postalCode"/></span>
                    </td>
                </tr>
            </table>
        </logic:present>
    </td>
</tr>
</table>
</td>
</tr>
</table>
</td>
<td class="tableoutline" width="1">
    <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1">
</td>
</tr>
<html:hidden property="action" value=""/>
</html:form>
</table>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
    <tr>
        <td>
            <img src="<%=IMGPath%>/cw_left_footer_shop.gif" ALIGN="top">
        </td>
        <td>
            <img src="<%=IMGPath%>/cw_middle_footer_shop.gif" ALIGN="top"
                 width="<%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>" height="8">
        </td>
        <td>
            <img src="<%=IMGPath%>/cw_right_footer_shop.gif" ALIGN="top">
        </td>
    </tr>
</table>

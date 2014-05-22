<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.cleanwise.service.api.value.AssetView" %>
<%@ page import="com.cleanwise.service.api.value.IdVector" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<app:checkLogon/>

<%
    String feedField = (String) request.getParameter("feedField");
    if (null == feedField) {
        feedField = "";
    }
    String siteId = (String) request.getParameter("siteId");
    if (null == siteId) {
        siteId = "";
    }
    String feedDesc = (String) request.getParameter("feedDesc");
    if (null == feedDesc) {
        feedDesc = "";
    }
    String catalogId = (String) request.getParameter("catalogid");
    if (null == catalogId) {
        catalogId = "";
    }
    String contractId = (String) request.getParameter("contractid");
    if (null == contractId) {
        contractId = "";
    }

%>

<script type="text/javascript" language="JavaScript1.2">
    <!--
    function submitenter(myfield, e)
    {
        var keycode;
        if (window.event) keycode = window.event.keyCode;
        else if (e) keycode = e.which;
        else return true;

        if (keycode == 13)
        {
            var s = document.forms[0].elements['search'];
            if (s != null) s.click();
            return false;
        }
        else
            return true;
    }

    var focusControl = document.getElementById("mainSearchField");
    if (focusControl != undefined && focusControl.type != "hidden" && !focusControl.disabled) {
        focusControl.focus();
    }

    function actionSubmit(formNum, action) {

        var actions;
        actions = document.forms[formNum]["action"];
        for (ii = actions.length - 1; ii >= 0; ii--) {
            if (actions[ii].value == 'hiddenAction') {
                actions[ii].value = action;
                document.forms[formNum].submit();
                break;
            }
        }
        return false;
    }

    function passIdAndName(item_id, name, desc) {

        var feedBackFieldName = document.STORE_ASSET_MGR_FORM.feedField.value;
        var feedDesc = document.STORE_ASSET_MGR_FORM.feedDesc.value;

        if (feedBackFieldName && "" != feedBackFieldName) {
            window.opener.document.forms[0].elements[feedBackFieldName].value = item_id;
        }

        if (feedDesc && "" != feedDesc) {
            window.opener.document.forms[0].elements[feedDesc].value = unescape(desc.replace(/\+/g, ' '));
        }

        var fbele = window.opener.document.getElementById(feedBackFieldName);
        if (fbele) {
            fbele.value = item_id;
        }

        self.close();
    }

    // -->
</script>

<html:html>

<head>
    <title>Search Asset</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link rel="stylesheet" href="../externals//styles.css">
</head>

<body class="results">
<div class="text">
<table width="<%=Constants.TABLEWIDTH%>" border="0" class="results">
<tr>
    <td>
        <bean:define id="theForm" name="STORE_ASSET_MGR_FORM" type="com.cleanwise.view.forms.StoreAssetMgrForm"/>

        <html:form name="STORE_ASSET_MGR_FORM" action="/adminportal/assetLocate" scope="session">

            <input type="hidden" name="feedField" value="<%=feedField%>">
            <input type="hidden" name="siteId" value="<%=siteId%>">
            <input type="hidden" name="feedDesc" value="<%=feedDesc%>">
            <input type="hidden" name="catalogid" value="<%=catalogId%>">
            <input type="hidden" name="contractid" value="<%=contractId%>">
            <table width="<%=Constants.TABLEWIDTH%>" border="0" bgcolor="#cccccc">
                <tr>
                    <td width="93" class="largeheader"><b>Find Asset</b></td>
                    <td>
                        <html:text styleId="mainSearchField" property="searchField" onkeypress="return submitenter(this,event)"/>
                        <html:radio property="searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.ID%>"/>Id
                        <html:radio property="searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>Name(starts with)
                        <html:radio property="searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>Name(contains)
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <html:button property="action" value="Search" onclick="actionSubmit(0,'locateSearch');"/>
                    </td>
                </tr>
            </table>
            <html:hidden property="action" value="hiddenAction"/>

        </html:form>
    </td>
</tr>
<tr>
<div>
<td>

<logic:present name="STORE_ASSET_MGR_FORM" property="mainSearchResult">

<bean:size id="rescount" name="STORE_ASSET_MGR_FORM" property="mainSearchResult"/>
Count: <bean:write name="rescount"/>

<% if (rescount.intValue() >= Constants.MAX_ASSETS_TO_RETURN) { %>
(request limit)
<%}%>

<logic:greaterThan name="rescount" value="0">

    <table width="100%" class="results">
        <tr align=center>
            <td><b>Asset Id</b></td>
            <td><b>Asset Name</b></td>
            <td><b>Asset Type</b></td>
            <td><b>Asset#</b></td>
            <td><b>Serial#</b></td>
            <td><b>Site id</b></td>
            <td><b>Site Name</b></td>
            <td><b>Status</b></td>
        </tr>

        <logic:iterate id="arrele" name="STORE_ASSET_MGR_FORM" property="mainSearchResult"
                       type="com.cleanwise.service.api.value.AssetView">

            <tr>

                <td>
                    <bean:define id="key" name="arrele" property="assetId"/>
                    <bean:write name="arrele" property="assetId"/>
                </td>

                <td>
                    <bean:define id="name" name="arrele" property="assetName"/>
                    <% String onClick = new String("return passIdAndName('" + key + "', '" + java.net.URLEncoder.encode(name.toString()) + "' , '" + java.net.URLEncoder.encode(name.toString()) + "')");%>
                    <a href="javascript:void(0);" onclick="<%=onClick%>"><bean:write name="arrele" property="assetName"/></a>
                </td>

                <td>
                    <bean:write name="arrele" property="assetTypeCd"/>
                </td>

                <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                    <td>
                        <bean:write name="arrele" property="assetNumber"/>
                    </td>

                    <td>
                        <bean:write name="arrele" property="serialNumber"/>
                    </td>
                </logic:equal>

                <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>">
                    <td>
                        <%="N/A"%>
                    </td>

                    <td>
                        <%="N/A"%>
                    </td>
                </logic:equal>
                <%
                    String siteIdsStr = "error";
                    String siteNamesStr = "error";
                    int i = 0;
                %>
                <logic:present name="arrele" property="siteInfo">

                    <bean:define id="siteInfo" name="arrele" property="siteInfo" type="java.util.HashMap"/>
                    <%
                        ArrayList siteIds = (ArrayList) ((HashMap) siteInfo).get(AssetView.SITE_INFO.SITE_IDS);
                        ArrayList siteNames = (ArrayList) ((HashMap) siteInfo).get(AssetView.SITE_INFO.SITE_NAMES);
                        siteIdsStr = "";
                        siteNamesStr = "";
                        if (siteIds != null) {
                            Iterator iterator = siteIds.iterator();
                            while (iterator.hasNext()) {
                                if (i != 0) {
                                    siteIdsStr = siteIdsStr.concat("<br>");
                                    siteNamesStr = siteNamesStr.concat("<br>");
                                }
                                siteIdsStr = siteIdsStr.concat(String.valueOf((Integer) iterator.next()));
                                siteNamesStr = siteNamesStr.concat((String) siteNames.get(i));
                                i++;

                            }
                        } else {
                            siteIdsStr = "-";
                            siteNamesStr = "-";
                        }
                    %>
                </logic:present>

                <td>
                    <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                        <%=siteIdsStr%>
                    </logic:equal>
                    <logic:equal name="arrele" property="assetTypeCd"
                                 value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>">
                        <%="N/A"%>
                    </logic:equal>
                </td>

                <td>
                    <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                        <%=siteNamesStr%>
                    </logic:equal>
                    <logic:equal name="arrele" property="assetTypeCd"
                                 value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>">
                        <%="N/A"%>
                    </logic:equal>
                </td>

                <td>
                    <bean:write name="arrele" property="status"/>
                </td>

            </tr>
        </logic:iterate>

    </table>
</logic:greaterThan>
</logic:present>
</td>
</div>
</tr>
</table>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</div>
</body>
</html:html>
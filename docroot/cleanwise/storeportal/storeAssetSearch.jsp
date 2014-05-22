<%--

  Title:        storeAssetSearch.jsp
  Description:  search page
  Purpose:      views of  the result  of search
  Copyright:    Copyright (c) 2006
  Company:      CleanWise, Inc.
  Date:         20.12.2006
  Time:         19:53:26
  author        Alexander Chickin, TrinitySoft, Inc.

--%>
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

<SCRIPT TYPE="text/javascript">
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
    // -->
</SCRIPT>
<body>
<jsp:include flush='true' page="locateStoreSite.jsp">
    <jsp:param name="jspFormAction" 	value="/storeportal/storeAssetSearch.do" />
    <jsp:param name="jspFormName" 	value="STORE_ASSET_MGR_FORM" />
    <jsp:param name="jspSubmitIdent" 	        value="" />
    <jsp:param name="jspReturnFilterProperty" 	value="siteFilter" />
</jsp:include>
<table ID=544 width="<%=Constants.TABLEWIDTH%>" border="0">
<tr>
    <td>

        <bean:define id="theForm" name="STORE_ASSET_MGR_FORM" type="com.cleanwise.view.forms.StoreAssetMgrForm"/>
        <html:form styleId="545" name="STORE_ASSET_MGR_FORM" action="/storeportal/storeAssetSearch" scope="session">
            <table ID=546 width="<%=Constants.TABLEWIDTH%>" border="0" class="mainbody">
                <tr>
                    <td align="right"><b>Find Asset</b></td>
                    <td>
                        <html:text styleId="mainSearchField" property="searchField"
                                   onkeypress="return submitenter(this,event)"/>
                        <html:radio property="searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.ID%>"/>
                        Id
                        <html:radio property="searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
                        Name(starts with)
                        <html:radio property="searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
                        Name(contains)
                    </td>
                </tr>
                <tr>
                    <td align="right"><b>Site</b>
                    <td>
                        <logic:present name="STORE_ASSET_MGR_FORM" property="locateStoreSiteForm">

                            <bean:define id="filteredSiteIds" name="STORE_ASSET_MGR_FORM"
                                         property="locateStoreSiteForm.selectedIdsAsString" type="java.lang.String"/>

                            <html:text styleId="siteSearchField" property="siteSearchField" value="<%=filteredSiteIds%>"
                                       onkeypress="return submitenter(this,event)"/>
                            <logic:notEqual name="filteredSiteIds" value="">
                                <html:submit property="action" value="Clear Site Filter" styleClass='text'/>
                            </logic:notEqual>
                        </logic:present>
                        <logic:notPresent name="STORE_ASSET_MGR_FORM" property="locateStoreSiteForm">

                            <html:text styleId="siteSearchField" property="siteSearchField"
                                       onkeypress="return submitenter(this,event)"/>
                        </logic:notPresent>

                        <html:submit property="action" value="Locate Site" styleClass='text'/>
                    </td>
                </tr>
                <tr>
                    <logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd"
                                 value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
                        <td align="right"><b>Store</b>
                        <td>
                            <html:text styleId="storeSearchField" property="storeSearchField"
                                       onkeypress="return submitenter(this,event)"/>

                        </td>
                    </logic:equal>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td colspan="3">
                        <html:submit styleId="search" property="action">
                            <app:storeMessage  key="global.action.label.search"/>
                        </html:submit>
                        <html:submit property="action">
                            <app:storeMessage  key="admin.button.create"/>
                        </html:submit>
                        Show Inactive
                        <html:checkbox property="showInactiveFl" value="true"/>
                    </td>
                </tr>
            </table>
        </html:form>
    </td>
</tr>
<tr>
    <td>
        <logic:present name="STORE_ASSET_MGR_FORM" property="mainSearchResult">
            <bean:size id="rescount" name="STORE_ASSET_MGR_FORM" property="mainSearchResult"/>
            Count:
            <bean:write name="rescount"/>
            <% if (rescount.intValue() >= Constants.MAX_ASSETS_TO_RETURN) { %>
            (request limit)
            <%}%>

            <logic:greaterThan name="rescount" value="0">

                <table class="stpTable_sortable" id="ts1">
                    <thead>
                        <tr class=stpTH>
                            <th class="stpTH">Asset Id</th>
                            <th class="stpTH">Asset Name</th>
                            <th class="stpTH">Asset Type</th>
                            <th class="stpTH">Asset#</th>
                            <th class="stpTH">Serial#</th>
                            <th class="stpTH">Site id</th>
                            <th class="stpTH">Site Name</th>
                            <th class="stpTH">Status</th>
                        </tr>
                    </thead>

                    <tbody>
                        <logic:iterate id="arrele" name="STORE_ASSET_MGR_FORM" property="mainSearchResult"
                                       type="com.cleanwise.service.api.value.AssetView">

                            <tr class=stpTD>
                                <td class=stpTD>
                                    <bean:write name="arrele" property="assetId"/>
                                </td>
                                <td class=stpTD>
                                    <a ID=547 href="storeAssetDetail.do?action=assetdetail&assetId=<%=arrele.getAssetId()%>">
                                        <bean:write name="arrele" property="assetName"/>
                                    </a>
                                </td>
                                 <td class=stpTD>
                                    <bean:write name="arrele" property="assetTypeCd"/>
                                </td>
                                <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                                <td class=stpTD>
                                    <bean:write name="arrele" property="assetNumber"/>
                                </td>
                                <td class=stpTD>
                                    <bean:write name="arrele" property="serialNumber"/>
                                </td>
                                </logic:equal>
                                <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>">
                                <td class=stpTD>
                                    <%="N/A"%>
                                </td>
                                <td class=stpTD>
                                   <%="N/A"%>
                                </td>
                                </logic:equal>
                                <%String siteIdsStr="error";
                                  String siteNamesStr="error";
                                int i=0;%>
                                <logic:present  name="arrele" property="siteInfo">
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
                               <td class=stpTD>
                              <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                                 <%=siteIdsStr%>
                              </logic:equal>
                                   <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>">
                                   <%="N/A"%>
                                 </logic:equal>
                                </td>
                                <td class=stpTD>
                                  <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                                   <%=siteNamesStr%>
                                 </logic:equal>
                                 <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>">
                                   <%="N/A"%>
                                 </logic:equal>
                               </td>
                               <td class=stpTD>
                                    <bean:write name="arrele" property="status"/>
                                </td>
                            </tr>

                        </logic:iterate>
                    </tbody>
                </table>
            </logic:greaterThan>
        </logic:present>
    </td>
</tr>
</table>
</body>
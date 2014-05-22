<%--

  Title:        storeAssetConfig.jsp
  Description:  configuration page of asset
  Purpose:      views of the current association
  Copyright:    Copyright (c) 2006
  Company:      CleanWise, Inc.
  Date:         20.12.2006
  Time:         16:23:22
  author        Alexander Chickin, TrinitySoft, Inc.

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

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
<jsp:include flush='true' page="storeAssetCtx.jsp"/>
<jsp:include flush='true' page="locateStoreSite.jsp">
    <jsp:param name="jspFormAction" 	value="/storeportal/storeAssetConfig.do" />
    <jsp:param name="jspFormName" 	value="STORE_ASSET_CONFIG_FORM" />
    <jsp:param name="jspSubmitIdent" 	        value="" />
    <jsp:param name="jspReturnFilterProperty" 	value="siteFilter" />
</jsp:include>
<bean:define id="theForm" name="STORE_ASSET_CONFIG_FORM" type="com.cleanwise.view.forms.StoreAssetConfigForm"/>
<html:form styleId="528" name="STORE_ASSET_CONFIG_FORM" action="/storeportal/storeAssetConfig" scope="session">

<table ID=529 width="<%=Constants.TABLEWIDTH%>" border="0">
<tr>
    <td>
         <table ID=530 width="<%=Constants.TABLEWIDTH%>" border="0" class="mainbody">
                <tr>
                    <td align="right"><b>Find :</b></td>
                    <td><html:select  name="STORE_ASSET_CONFIG_FORM" property="assocType">
                        <bean:define    id ="assocTypes" name="Asset.assoc.type.vector"/>
                        <logic:present    name="assocTypes">
                            <logic:iterate id="type" name="assocTypes">
                        <html:option value="<%=(String)type%>"></html:option>
                        </logic:iterate>
                        </logic:present>
                        </html:select>
                    </td>
                   </tr>
                <tr>
                    <td align="right"><b>Site : </b>
                     <td>
                    <logic:present name="STORE_ASSET_CONFIG_FORM" property="locateStoreSiteForm">

                        <bean:define id="filteredSiteIds" name="STORE_ASSET_CONFIG_FORM"
                                     property="locateStoreSiteForm.selectedIdsAsString" type="java.lang.String"/>

                        <html:text styleId="siteSearchField" name="STORE_ASSET_CONFIG_FORM" property="siteSearchField"
                                               value="<%=filteredSiteIds%>" onkeypress="return submitenter(this,event)"/>

                        <logic:notEqual name="filteredSiteIds" value="">
                            <html:submit property="action" value="Clear Site Filter" styleClass='text'/>
                        </logic:notEqual>

                    </logic:present>

                    <logic:notPresent name="STORE_ASSET_CONFIG_FORM" property="locateStoreSiteForm">
                        <html:text styleId="siteSearchField" name="STORE_ASSET_CONFIG_FORM" property="siteSearchField" onkeypress="return submitenter(this,event)"/>
                    </logic:notPresent>
                    <html:submit property="action" value="Locate Site" styleClass='text'/>

                    </td>
                <tr>
                    <td>
                     &nbsp;
                    </td>
                    <td><html:checkbox name="STORE_ASSET_CONFIG_FORM" property="showConfiguredOnlyFl"/>Show configured only</td>
                </tr>
                    <tr>
                        <td>&nbsp</td>
                        <td> <html:submit styleId="search" property="action">
                                <app:storeMessage  key="global.action.label.search"/>
                             </html:submit>
                      <html:submit property="action" value="Configuration Update"/>
                        </td>
                    </tr>
            </table>
    </td>
</tr>
    <tr>
        <td>
                <logic:present name="STORE_ASSET_CONFIG_FORM" property="configResults.values">
                    <table class="stpTable_sortable" id="ts1">
                        <thead>
                            <tr class=stpTH>
                                    <%--Render headers based off type we are configuring--%>
                                <th class="stpTH">Site Id</th>
                                <th class="stpTH">Site Name</th>
                                <th class="stpTH">Site Status</th>
                                <th align="center" class="stpTH">Select</th>
                            </tr>
                        </thead>
                        <tbody>
                            <logic:iterate id="arrele" name="STORE_ASSET_CONFIG_FORM" property="configResults.values"
                                           indexId="i">
                                <bean:define id="key" name="arrele" property="busEntityId"/>
                                <tr>
                                    <td class="stpTD">
                                        <bean:write name="arrele" property="busEntityId"/>
                                    </td>
                                    <td class="stpTD">
                                        <bean:write name="arrele" property="shortDesc"/>
                                    </td>
                                    <td class="stpTD">
                                        <bean:write name="arrele" property="busEntityStatusCd"/>
                                    </td>
                                    <td class="stpTD">
                                        <html:radio name="STORE_ASSET_CONFIG_FORM" property="assetLocation" value="<%=String.valueOf(key)%>"/>
                                    </td>
                                </tr>
                            </logic:iterate>
                        </tbody>
                        </logic:present>
                    </table>
        </td>
    </tr>
</table>
        </html:form>
</body>
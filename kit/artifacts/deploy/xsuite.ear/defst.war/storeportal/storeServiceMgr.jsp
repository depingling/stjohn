<%--

  Title:        storeServiceMgr.jsp
  Description:  page of the service management
  Purpose:      configuration management
  Copyright:    Copyright (c) 2007
  Company:      CleanWise, Inc.
  Date:         09.01.2007
  Time:         13:28:33
  author        Alexander Chickin, TrinitySoft, Inc.

--%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

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
<bean:define id="theForm" name="STORE_SERVICE_MGR_FORM" type="com.cleanwise.view.forms.StoreServiceMgrForm"/>
<html:form styleId="1180" name="STORE_SERVICE_MGR_FORM" action="/storeportal/storeServiceMgr" scope="session">

<table ID=1181 width="<%=Constants.TABLEWIDTH%>" border="0">
<tr>
    <td>
         <table ID=1182 width="<%=Constants.TABLEWIDTH%>" border="0" class="mainbody">
                <tr>
                    <td align="right"><b>Service :</b></td>
                    <td><logic:equal name="STORE_SERVICE_MGR_FORM"  property="editServiceFl" value="true">
                        <html:text name="STORE_SERVICE_MGR_FORM"  property="service.shortDesc"/>
                        <html:submit property="action"><app:storeMessage  key="global.action.label.save"/></html:submit>
                        </logic:equal>
                    <logic:notEqual name="STORE_SERVICE_MGR_FORM" property="editServiceFl" value="true">
                        <html:select name="STORE_SERVICE_MGR_FORM" property="currentServiceId">
                            <logic:present name="STORE_SERVICE_MGR_FORM" property="servicesCollection">
                                <logic:iterate id="service" name="STORE_SERVICE_MGR_FORM" property="servicesCollection"
                                                            type="com.cleanwise.service.api.value.ItemData">
                                    <bean:define id="sId" name="service" property="itemId"/>
                                    <html:option value="<%=((Integer)sId).toString()%>">
                                        <bean:write name="service" property="shortDesc"/>
                                    </html:option>
                                </logic:iterate>
                            </logic:present>
                        </html:select>
                        <html:submit property="action" value="Create Service" styleClass='text'/>
                        <html:submit property="action" value="Delete Service" styleClass='text'/>
                        <html:submit property="action" value="Edit Service" styleClass='text'/>
                  </logic:notEqual>
                    </td>
                   </tr>
               <logic:notEqual name="STORE_SERVICE_MGR_FORM" property="editServiceFl" value="true">
             <tr>
                    <td align="right"><b>Asset : </b>
                     <td>
                        <html:text styleId="mainSearchField" property="assetSearchField"
                                   onkeypress="return submitenter(this,event)"/>
                        <html:radio property="assetSearchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.ID%>"/>
                        Id
                        <html:radio property="assetSearchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
                        Name(starts with)
                        <html:radio property="assetSearchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
                        Name(contains)
                    </td>
                </tr>
                <tr>
                    <td>
                     &nbsp;
                    </td>
                    <td><html:checkbox name="STORE_SERVICE_MGR_FORM" property="showConfiguredOnlyFl"/>Show configured only</td>
                </tr>

                    <tr>
                        <td>&nbsp</td>
                        <td> <html:submit styleId="search" property="action">
                                <app:storeMessage  key="global.action.label.search"/>
                             </html:submit>
                            <html:submit property="action" value="Configuration Update"/>
                        </td>
                    </tr>
             </logic:notEqual>
            </table>
    </td>
</tr>
    <tr>
        <td>
                <logic:present name="STORE_SERVICE_MGR_FORM" property="configResults.values">
                    <table class="stpTable_sortable" id="ts1">
                        <thead>
                            <tr class=stpTH>
                                    <%--Render headers based off type we are configuring--%>
                                <th class="stpTH">Asset Id</th>
                                <th class="stpTH">Asset Name</th>
                                <th class="stpTH">Asset Type</th>
                                <th class="stpTH">Asset Status</th>
                                <th class="stpTH">Select</th>

                            </tr>
                        </thead>
                        <tbody>
                            <logic:iterate id="arrele" name="STORE_SERVICE_MGR_FORM" property="configResults.values"
                                           indexId="i">
                                <tr>
                                    <td class="stpTD">
                                        <bean:write name="arrele" property="assetId"/>
                                    </td>
                                    <td class="stpTD">
                                        <bean:write name="arrele" property="shortDesc"/>
                                    </td>
                                    <td class="stpTD">
                                        <bean:write name="arrele" property="assetTypeCd"/>
                                    </td>
                                    <td class="stpTD">
                                        <bean:write name="arrele" property="statusCd"/>
                                    </td>
                                    <%
                                        String selectedStr = "configResults.selected[" + i + "]";
                                    %>
                                    <td class="stpTD">
                                        <html:multibox name="STORE_SERVICE_MGR_FORM" property="<%=selectedStr%>"
                                                       value="true"/>
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

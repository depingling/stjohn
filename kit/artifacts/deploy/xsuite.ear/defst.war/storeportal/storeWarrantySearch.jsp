<%--

  Title:        storeWarrantySearch.jsp
  Description:  search page
  Purpose:      views of  the result  of search
  Copyright:    Copyright (c) 2006
  Company:      CleanWise, Inc.
  Date:         25.09.2007
  Time:         17:54:11
  author        Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.WarrantyView" %>
<%@ page import="com.cleanwise.view.forms.StoreWarrantyMgrForm" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.text.DateFormat" %>

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
    var focusControl = document.getElementById("searchField");
    if (focusControl != undefined && focusControl.type != "hidden" && !focusControl.disabled) {
        focusControl.focus();
    }
    // -->
</SCRIPT>
<body>

<table ID=1529 width="<%=Constants.TABLEWIDTH%>" border="0">
<tr>
    <td>

        <bean:define id="theForm" name="STORE_WARRANTY_MGR_FORM" type="com.cleanwise.view.forms.StoreWarrantyMgrForm"/>
        <html:form styleId="1530" name="STORE_WARRANTY_MGR_FORM" action="/storeportal/storeWarrantySearch" scope="session">
            <table ID=1531 width="<%=Constants.TABLEWIDTH%>" border="0" class="mainbody">
                <tr>
                    <td align="right"><b>
                        <app:storeMessage key="storeWarranty.text.findWarranty"/>
                    </b></td>
                    <td>
                        <html:text styleId="searchField" property="searchField"
                                   onkeypress="return submitenter(this,event)"/>
                    </td>
                    <td>
                        <html:radio property="searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
                        <app:storeMessage key="userWarranty.text.nameStartsWith"/>
                        <html:radio property="searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
                        <app:storeMessage key="userWarranty.text.nameContains"/>
                    </td>
                </tr>
                <tr>
                    <td  colspan="2"></td>
                    <td>
                        <html:radio property="searchFieldType" value="<%=StoreWarrantyMgrForm.WARRANTY_ID%>"/>
                        <app:storeMessage key="storeWarranty.text.warrantyId"/>
                        <html:radio property="searchFieldType" value="<%=StoreWarrantyMgrForm.WARRANTY_NAME%>"/>
                        <app:storeMessage key="storeWarranty.text.warrantyName"/>
                        <html:radio property="searchFieldType" value="<%=StoreWarrantyMgrForm.WARRANTY_PROVIDER%>"/>
                        <app:storeMessage key="storeWarranty.text.warrantyProviderName"/>
                    </td>
                </tr>
                <tr>
                    <td  colspan="2"></td>
                    <td>
                        <html:radio property="searchFieldType" value="<%=StoreWarrantyMgrForm.SERVICE_PROVIDER%>"/>
                        <app:storeMessage key="storeWarranty.text.serviceProviderName"/>
                        <html:radio property="searchFieldType" value="<%=StoreWarrantyMgrForm.ASSET_NAME%>"/>
                        <app:storeMessage key="storeWarranty.text.assetName"/>
                        <html:radio property="searchFieldType" value="<%=StoreWarrantyMgrForm.ASSET_NUMBER%>"/>
                        <app:storeMessage key="storeWarranty.text.assetNumber"/></td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td colspan="2">
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
        <logic:present name="STORE_WARRANTY_MGR_FORM" property="searchResult">
            <bean:size id="rescount" name="STORE_WARRANTY_MGR_FORM" property="searchResult"/>
            <table class="stpTable_sortable" id="ts1">
                <thead>
                    <tr class=stpTH>
                        <th class=stpTH>
                            <app:storeMessage key="storeWarranty.text.warrantyNumber"/>
                        </th>
                        <th class=stpTH>
                            <app:storeMessage key="storeWarranty.text.assetCategories"/>
                        </th>
                        <th class=stpTH>
                            <app:storeMessage key="storeWarranty.text.warrantyType"/>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <logic:iterate id="arrele" name="STORE_WARRANTY_MGR_FORM" property="searchResult" indexId="i">
                        <tr class=stpTD>
                            <td class=stpTD>
                                <bean:define id="eleid" name="arrele" property="warrantyId"/>
                                <a ID=1532 href="../storeportal/storeWarrantyDetail.do?action=detail&warrantyId=<%=eleid%>">
                                    <bean:write name="arrele" property="warrantyNumber"/>
                                </a>
                            </td>
                            <logic:present name="arrele" property="assets">
                            <bean:size id="assetcount" name="arrele" property="assets"/>

                            <td class=stpTD>
                                    <logic:iterate id="asset" name="arrele" property="assets" indexId="j">
                                          <logic:greaterThan name="j" value="0">
                                            <br/>
                                         </logic:greaterThan>
                                        <bean:write name="asset" property="shortDesc"/>
                                    </logic:iterate>
                            </td>

                           </logic:present>
                             <logic:notPresent name="arrele" property="assets">
                                 <td class=stpTD>&nbsp; </td>
                             </logic:notPresent>
                            <td class=stpTD>
                                <bean:write name="arrele" property="type"/>
                            </td>
                        </tr>
                    </logic:iterate>
                </tbody>
            </table>
        </logic:present>
    </td>
</tr>
</table>
</body>
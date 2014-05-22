<%--
  Date: 30.09.2007
  Time: 21:09:29
  @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.SelectableObjects" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<SCRIPT TYPE="text/javascript">
    <!--
    function actionSubmit(formNum, action) {
        var actions;
        actions=document.forms[formNum]["action"];
        //alert(actions.length);
        for(ii=actions.length-1; ii>=0; ii--) {
            if(actions[ii].value=='hiddenAction') {
                actions[ii].value=action;
                document.forms[formNum].submit();
                break;
            }
        }
        return false;
    }

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
<body><div class="text">
    <jsp:include flush='true' page="storeWarrantyCtx.jsp"/>
    <table ID=1485  width="<%=Constants.TABLEWIDTH%>" border="0" cellpadding="0" cellspacing="0" >
        <tr>
            <td>
                <table ID=1486 width="100%" border="0" cellpadding="0" cellspacing="0"  class="mainbody" >
                    <tr><td colspan="2">&nbsp;</td></tr> <bean:define id="theForm" name="STORE_WARRANTY_CONFIG_FORM" type="com.cleanwise.view.forms.StoreWarrantyConfigForm"/>
                    <html:form styleId="1487" name="STORE_WARRANTY_CONFIG_FORM" action="/storeportal/storeWarrantyConfig" scope="session">

                    <tr>
                        <td width="20%"><b>Asset Category</b>:</td>
                        <td align="left"><html:select name="STORE_WARRANTY_CONFIG_FORM" property="activeAssetCategoryId"
                                                      onchange="actionSubmit('0','changeActiveAssetCategory')">
                            <html:option value="-1">
                                <app:storeMessage  key="admin.select"/>
                            </html:option>
                            <logic:present name="Warranty.asset.category.vector">
                                <logic:iterate id="cat" name="Warranty.asset.category.vector" type="com.cleanwise.service.api.value.AssetData">
                                    <bean:define id="catId" name="cat" property="assetId"/>
                                    <html:option value="<%=String.valueOf(catId)%>">
                                        <bean:write name="cat" property="shortDesc"/>
                                    </html:option>
                                </logic:iterate>
                            </logic:present>
                            <html:option value="0">
                                -All-
                            </html:option>
                        </html:select>
                        </td>
                    </tr>
                    <tr><td colspan="2">&nbsp;</td></tr>

                </table></td></tr>
        <tr>
            <td>
                <logic:present name="STORE_WARRANTY_CONFIG_FORM" property="configResults.values">
                    <table class="stpTable_sortable" id="ts1">
                        <thead>
                            <tr class=stpTH>
                                    <%--Render headers based off type we are configuring--%>
                                <th class="stpTH">Asset Id</th>
                                <th class="stpTH">Asset Name</th>
                                <th class="stpTH">Asset Number</th>
                                <th class="stpTH">Asset Type</th>
                                <th class="stpTH">Select</th>
                            </tr>
                        </thead>
                        <tbody>
                            <logic:iterate id="arrele" name="STORE_WARRANTY_CONFIG_FORM" property="configResults.values" indexId="i">

                                <bean:define id="key" name="arrele" property="assetId"/>
                                <tr>
                                    <td class="stpTD">
                                        <bean:write name="arrele" property="assetId"/>
                                    </td>
                                    <td class="stpTD">
                                        <%if (((SelectableObjects)theForm.getConfigResults()).getSelected(((Integer) i).intValue())) {%>
                                        <a ID=1488 href="storeWarrantyConfig.do?action=assetWarrantyConfigDetails&assetId=<%=key%>"><bean:write name="arrele" property="assetName"/></a>
                                        <%} else
                                        {%>
                                        <bean:write name="arrele" property="assetName"/>
                                        <%}%>
                                    </td>
                                    <td class="stpTD">
                                        <bean:write name="arrele" property="assetNumber"/>
                                    </td>
                                    <td class="stpTD">
                                        <bean:write name="arrele" property="assetTypeCd"/>
                                    </td>
                                    <%
                                        String selectedStr = "configResults.selected[" + i + "]";
                                    %>
                                    <td class="stpTD">
                                        <html:multibox name="STORE_WARRANTY_CONFIG_FORM" property="<%=selectedStr%>" value="true"/>
                                    </td>
                                </tr>

                            </logic:iterate>
                        </tbody> </table>
                </logic:present>
            </td></tr><tr><td> <tr><td colspan="5"> <html:submit property="action">
        <app:storeMessage  key="global.action.label.save"/>
    </html:submit>
    </td></tr> </td></tr></table>
    <html:hidden property="action" value="hiddenAction"/>
    </html:form>    </div>
</body>
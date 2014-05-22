<%--
  Date: 03.10.2007
  Time: 22:27:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.SelectableObjects" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.value.AssetView" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<SCRIPT TYPE="text/javascript">
    <!--
    function actionSubmit(formNum, action) {
        var actions;
        actions = document.forms[formNum]["action"];
        if('undefined' !=typeof actions.length ){
        for (var ii = actions.length - 1; ii >= 0; ii--) {
            if (actions[ii].value == 'hiddenAction') {
                actions[ii].value = action;
                document.forms[formNum].submit();
                break;
            }
        }
        } else if('undefined' !=typeof actions ){
          actions.value = action;
                document.forms[formNum].submit();
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

<table width="100%" cellpadding="0" cellspacing="0">
<tr>
    <td colspan="2">&nbsp;</td>
</tr>
<bean:define id="theForm" name="USER_WARRANTY_CONFIG_MGR_FORM"
             type="com.cleanwise.view.forms.UserWarrantyConfigMgrForm"/>
<html:form name="USER_WARRANTY_CONFIG_MGR_FORM" action="/userportal/userWarrantyConfig" scope="session">
<tr>
    <td width="20%"><b>Asset Category</b>:</td>
    <td align="left">
        <html:select name="USER_WARRANTY_CONFIG_MGR_FORM" property="activeAssetCategoryId"
                     onchange="actionSubmit('0','changeActiveAssetCategory')">
            <html:option value="-1">
                <app:storeMessage  key="admin.select"/>
            </html:option>
            <logic:present name="Warranty.asset.category.vector">
                <logic:iterate id="cat" name="Warranty.asset.category.vector"
                               type="com.cleanwise.service.api.value.AssetData">
                    <bean:define id="catId" name="cat" property="assetId"/>
                    <html:option value="<%=String.valueOf(catId)%>">
                        <bean:write name="cat" property="shortDesc"/>
                    </html:option>
                </logic:iterate>
            </logic:present>
        </html:select>
    </td>
</tr>

<tr>
    <td colspan="2">&nbsp;</td>
</tr>


<tr>
    <td colspan="2">
        <logic:present name="USER_WARRANTY_CONFIG_MGR_FORM" property="configResults.values">

            <table width="100%">

                <tr>

                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userAssets.text.assetName"/>
                        </div>
                    </td>    <td class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="userAssets.text.assetNumber"/>
                    </div>
                </td>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userAssets.text.serialNum"/>
                        </div>
                    </td>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userAssets.text.modelNum"/>
                        </div>
                    </td>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userAssets.text.dateInService"/>
                        </div>
                    </td>
                    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">

                    <td class="shopcharthead">
                        <div class="fivemargin">
                        </div>
                    </td> </app:authorizedForFunction>
                </tr>
                <logic:iterate id="assetWarranty" name="USER_WARRANTY_CONFIG_MGR_FORM" property="configResults.values" indexId="i">
                    <bean:define id="key" name="assetWarranty" property="assetId"/>
                    <%boolean selected = ((SelectableObjects) theForm.getConfigResults()).getSelected(((Integer) i).intValue());%>
                    <%if (!(RefCodeNames.ASSET_STATUS_CD.INACTIVE.equals(((AssetView) assetWarranty).getStatus()) && !selected)){%>
                    <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
                        <td>
                            <%if (selected) {%>
                            <a href="userWarrantyConfig.do?action=assetWarrantyConfigDetails&assetId=<%=key%>&display=t_userAssetWarrantyDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar"><logic:present name="assetWarranty" property="assetName">
                                <bean:write name="assetWarranty" property="assetName"/>
                            </logic:present></a>
                            <%} else
                            {%>
                            <logic:present name="assetWarranty" property="assetName">
                                <bean:write name="assetWarranty" property="assetName"/>
                            </logic:present>

                            <%}%>
                        </td>
                        <td>
                            <logic:present name="assetWarranty" property="assetNumber">
                                <bean:write name="assetWarranty" property="assetNumber"/>
                            </logic:present>

                        </td>
                        <td>                      <logic:present name="assetWarranty" property="serialNumber">
                            <bean:write name="assetWarranty" property="serialNumber"/>
                        </logic:present>

                        </td>
                        <td>
                            <logic:present name="assetWarranty" property="modelNumber">
                                <bean:write name="assetWarranty" property="modelNumber"/>
                            </logic:present>

                        </td>
                        <td>
                            <logic:present name="assetWarranty" property="dateInService">
                                <bean:define id="dateInService" name="assetWarranty" property="dateInService" type="java.lang.String"/>
                                <%if (Utility.isSet(dateInService)) { %>
                                <%=ClwI18nUtil.formatDate(request, ClwI18nUtil.convertDateToRequest(Locale.US,request,dateInService),DateFormat.DEFAULT)%>
                                <%}%>
                            </logic:present>
                        </td>

                        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                            <%
                                String selectedStr = "configResults.selected[" + i + "]";
                            %>
                            <td>
                                <html:multibox name="USER_WARRANTY_CONFIG_MGR_FORM" property="<%=selectedStr%>" value="true"/>
                            </td>  </app:authorizedForFunction>

                    </tr>
                    <%}%>

                </logic:iterate>

            </table>
        </logic:present>
    </td></tr>

<tr>
    <td colspan="2" align="center">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
              <html:submit property="action" styleClass="store_fb">
            <app:storeMessage  key="global.action.label.save"/>
        </html:submit>
   </app:authorizedForFunction>
    </td>
</tr>
<html:hidden property="action" value="hiddenAction"/>
<html:hidden property="tabs" value="f_userAssetToolbar"/>
<html:hidden property="display" value="t_userAssetWarranty"/>
<html:hidden property="secondaryToolbar" value="f_userSecondaryToolbar"/>
</html:form>
</table>





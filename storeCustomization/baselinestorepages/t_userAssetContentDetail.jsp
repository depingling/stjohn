<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<script language="JavaScript1.2">
    <!--
    function viewPrinterFriendly(loc) {
        var prtwin = window.open(loc, "view_docs",
                "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
        prtwin.focus();
        return false;
    }

    function actionSubmitTb(formNum, action, fwdPage, sbar) {
        var actions;
        var disp
        var secBar;
        actions = document.forms[formNum]["action"];
        disp = document.forms[formNum]["display"];
        secBar = document.forms[formNum]["secondaryToolbar"];

        for (ii = actions.length - 1; ii >= 0; ii--) {
            if (actions[ii].value == 'hiddenAction') {
                disp.value = fwdPage;
                secBar.value = sbar;
                actions[ii].value = action;
                document.forms[formNum].submit();
                break;
            }
        }
        return false;
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
    //-->
</script>



<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<td>
<logic:present name="USER_ASSET_CONTENT_MGR_FORM">
<bean:define id="theForm" name="USER_ASSET_CONTENT_MGR_FORM" type="com.cleanwise.view.forms.UserAssetContentMgrForm"/>
<html:form name="USER_ASSET_CONTENT_MGR_FORM" action="/userportal/userAssetContent.do" scope="session"
           enctype="multipart/form-data">
<bean:define id="assetcId" name="USER_ASSET_CONTENT_MGR_FORM" property="assetContentId"/>
<bean:define id="assetId" name="USER_ASSET_CONTENT_MGR_FORM" property="assetId"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0">

<tr>
<td width="3%">&nbsp;</td>
<td width="94%">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
    <td width="10%">&nbsp;</td>
    <td width="18%">&nbsp;</td>
    <td width="24%">&nbsp;</td>
    <td width="14%">&nbsp;</td>
    <td width="36%">&nbsp;</td>
    <td width="10%">&nbsp;</td>

</tr>

<tr>
    <td></td>
    <td>
        <span class="shopassetdetailtxt">
            <b>
                <app:storeMessage key="userAssets.text.assocDocs.description"/>
            </b>:
        </span>
    </td>
    <td colspan="3">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:text size="55" name="USER_ASSET_CONTENT_MGR_FORM" property="shortDesc"/>
            <span class="reqind">*</span>
        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <bean:write  name="USER_ASSET_CONTENT_MGR_FORM" property="shortDesc"/>
        </app:notAuthorizedForFunction>
    </td>
    <td></td>

</tr>


<tr>
    <td></td>
    <td style="padding-top: .7em;padding-bottom: .7em">
        <span class="shopassetdetailtxt">
            <b>
                <app:storeMessage key="userAssets.text.assocDocs.fileName"/>
            </b>:
        </span>
    </td>
    <td colspan="3">
        <logic:present name="USER_ASSET_CONTENT_MGR_FORM" property="path">
            <%String loc = "../userportal/userAssetContent.do?action=readDocs&assetContentId=" + assetcId + "&display=t_userAssetContentDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar";%>
            <a href="#" onclick="viewPrinterFriendly('<%=loc%>');">
                <bean:write name="USER_ASSET_CONTENT_MGR_FORM" property="path"/>
            </a>
        </logic:present>
    </td>
    <td>&nbsp;</td>
</tr>
<tr>
    <td colspan="2">
        &nbsp;
    </td>
    <td colspan="3">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:file name="USER_ASSET_CONTENT_MGR_FORM" property="uploadNewFile"/>
        </app:authorizedForFunction>
    </td>
    <td>&nbsp;</td>
</tr>
<tr>
    <td colspan="6"> &nbsp; </td>
</tr>
<tr>
    <td colspan="6" width="100%">
        <table align="center" width="100%" cellpadding="1" cellspacing="0">
            <tr class="customerltbkground" valign="top">
                <div align="center" class="itemheadmargin">
                    <span class="shopitemhead">
                         <td width="3%">&nbsp;</td>
                         <td align="center">
                             <b>
                                 <app:storeMessage key="userAssets.text.assocDocs.longDesc"/>
                             </b>
                         </td>
                         <td width="3%">&nbsp;</td>
                    </span>
                </div>
            </tr>

            <tr>
                <td width="3%">&nbsp;</td>
                <td align="center">
                    <b>
                        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                            <html:textarea rows="7" cols="76" name="USER_ASSET_CONTENT_MGR_FORM"
                                           property="longDesc"/>
                        </app:authorizedForFunction>
                        <app:notAuthorizedForFunction
                                name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                            <bean:write name="USER_ASSET_CONTENT_MGR_FORM" property="longDesc"/>
                        </app:notAuthorizedForFunction>
                    </b>
                </td>
                <td width="3%">&nbsp;</td>
            </tr>
        </table>
    </td>
</tr>

<tr>
    <td align="center" colspan="6">&nbsp;</td>
</tr>
<tr>
    <td align="center" colspan="6">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:submit property="action" styleClass="store_fb">
                <app:storeMessage key="global.action.label.save"/>
            </html:submit>
            <logic:greaterThan name="assetcId" value="0">
                <html:submit property="action" styleClass="store_fb">
                    <app:storeMessage key="global.action.label.delete"/>
                </html:submit>
            </logic:greaterThan>
        </app:authorizedForFunction>
    </td>
</tr>
</table>
</td>
<td width="3%">&nbsp;</td>
</tr>

</table>
<html:hidden property="action" value="hiddenAction"/>
<html:hidden property="tabs" value="f_userAssetToolbar"/>
<html:hidden property="display" value="t_userAssetContentDetail"/>
<html:hidden property="secondaryToolbar" value="f_userSecondaryToolbar"/>
</html:form>
</logic:present>
</td>
</tr>
</table>

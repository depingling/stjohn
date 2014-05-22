<%--
 Title:        t_userAssetServiceDetail
 Description:  master asset profile.
 Purpose:      master asset profile management.
 Copyright:    Copyright (c) 2009
 Company:      CleanWise, Inc.
 Date:         20.11.2007
 Time:         14:02:44

--%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.PairView" %>
<%@ page import="com.cleanwise.view.forms.UserAssetProfileMgrForm" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.logic.UserAssetMgrLogic" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.cleanwise.service.api.value.AssetData" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" href="../externals/assetutil.css">

<style type="text/css">
    .bugel {
        visibility: visible;
        display: block;
    }
</style>

<%
    String messageKey;

    String jspFormName = UserAssetMgrLogic.USER_ASSET_PROFILE_MGR_FORM;
    String jspFormAction = "userAssetProfile.do";

    String evenRowColor = ClwCustomizer.getEvenRowColor(request.getSession());
    String oddRowColor = ClwCustomizer.getOddRowColor(request.getSession());
    
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    boolean isAssetAdministrator = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR);
%>

<script language="JavaScript" src="../externals/ajaxutil.js"></script>
<script language="JavaScript" src="../externals/assetutil.js"></script>

<script language="JavaScript1.2">
<!--
var inactiveReason = {
    active:'<%=RefCodeNames.ASSET_STATUS_CD.ACTIVE%>',
    inactive:'<%=RefCodeNames.ASSET_STATUS_CD.INACTIVE%>'
}

function actionSubmit(formNum, action) {
    var actions;
    actions=document.forms[formNum]["action"];
    if('undefined'!= typeof actions.length){
        for(ii=actions.length-1; ii>=0; ii--) {
            if(actions[ii].value=='hiddenAction') {
                actions[ii].value=action;
                document.forms[formNum].submit();
                break;
            }
        }
    } else {
        document.forms[formNum]["action"].value=action;
        document.forms[formNum].submit();
    }
}

function freeKeyHit(evt) {
    var evt = (evt) ? evt : event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;

    if (charCode == 13) {
        actionSubmit(0, 'saveAssetCategory');
    }
}
-->
</script>

<%
    String browser = (String) request.getHeader("User-Agent");
    String isMSIE = "";
    if (browser != null && browser.indexOf("MSIE") >= 0) {
        isMSIE = "Y";
%>
<script language="JavaScript" src="../externals/calendar.js"></script>
    <iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
        marginheight="0" marginwidth="0" noresize frameborder="0" scrolling="no" src="../externals/calendar.html">
    </iframe>
<% } else { %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% } %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="USER_ASSET_PROFILE_MGR_FORM" type="com.cleanwise.view.forms.UserAssetProfileMgrForm"/>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">

<!-- item picture and long description -->
<tr>

    <td>
        <table width="100%">
        <tr>
            <td width="1%">&nbsp;</td>
            <td width="20%">&nbsp;</td>
            <td width="36%">&nbsp;</td>
            <td width="1%">&nbsp;</td>
            <td width="26%">&nbsp;</td>
            <td width="15%">&nbsp;</td>
            <td width="1%">&nbsp;</td>
        </tr>

        <html:form action="/userportal/userAssetProfile.do"
                   name="USER_ASSET_PROFILE_MGR_FORM"
                   type="com.cleanwise.view.forms.UserAssetProfileMgrForm">
                   
                   
        <tr>
            <td colspan="7">&nbsp;</td>
        </tr>
        
        <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetTypeCd"
                     value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>">
        <tr>
            <td>&nbsp;</td>
            <td>
              <span class="shopassetdetailtxt">
                <strong>
                    <app:storeMessage key="userAssets.text.assetCategoryName"/>
                </strong>:
               <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                   <span class="reqind">*</span>
               </app:authorizedForFunction>
              </span>
            </td>
            <td colspan="3">
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <html:text size="74" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc" onkeypress="javascript: freeKeyHit(event)"/>
                </app:authorizedForFunction>
                <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc">
                        <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc"/>
                    </logic:present>
                </app:notAuthorizedForFunction>
            </td>
            <td colspan="2">&nbsp;</td>
        </tr>
        </logic:equal>
        
        <tr>
            <td colspan="7">&nbsp;</td>
        </tr>
        
        <tr>
            <td colspan="7" align="center">
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'saveAssetCategory');">
                        <app:storeMessage key="global.action.label.save"/>
                    </html:button>
                </app:authorizedForFunction>
            </td>
        </tr>
        <html:hidden property="assetData.statusCd" value="<%=RefCodeNames.ASSET_STATUS_CD.ACTIVE%>"/>
        <html:hidden property="tabs" value="f_userAssetToolbar"/>
        <html:hidden property="display" value="t_userMasterAssetSearch"/>
        <html:hidden property="secondaryToolbar" value="f_userSecondaryToolbar"/>
        <html:hidden property="action" value="hiddenAction"/>
        
        </html:form>
        </table>
    </td>

</tr>

</table>


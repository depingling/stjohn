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
                   type="com.cleanwise.view.forms.UserAssetProfileMgrForm" enctype="multipart/form-data">
        <tr>
            <td colspan="7">&nbsp;</td>
        </tr>
        
        <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetTypeCd"
                     value="<%=RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET%>">
        
        <tr>
            <td>&nbsp;</td>
            <td>
              <span class="shopassetdetailtxt">
                <strong>
                    <app:storeMessage key="userAssets.text.assetName"/>
                </strong>:
               <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
               <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                   <span class="reqind">*</span>
               </logic:equal>
               </app:authorizedForFunction>
              </span>
            </td>
            <td colspan="3">
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <html:text size="74" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc"/>
                    </logic:equal>
                    <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                        <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc"/>
                    </logic:notEqual>
                </app:authorizedForFunction>
                <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc">
                        <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc"/>
                    </logic:present>
                </app:notAuthorizedForFunction>
            </td>
            <td colspan="2">&nbsp;</td>
        </tr>
        
        <tr>
            <td>&nbsp;</td>
            <td>
                <span class="shopassetdetailtxt">
                    <strong>
                        <app:storeMessage key="userAssets.text.assetCategory"/>
                    </strong>:
                </span>
            </td>
            <td>
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <html:select styleClass="bugel" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.parentId">
                        <html:option value="">
                            <app:storeMessage  key="admin.select"/>
                        </html:option>
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetCategories">
                            <logic:iterate id="category" name="USER_ASSET_PROFILE_MGR_FORM" property="assetCategories"
                                           type="com.cleanwise.service.api.value.AssetData">
                                <bean:define id="categoryId" name="category" property="assetId" type="java.lang.Integer"/>
                               <logic:equal name="category" property="statusCd" value="<%=RefCodeNames.ASSET_STATUS_CD.ACTIVE%>">
                                <html:option value="<%=categoryId.toString()%>">
                                    <bean:write name="category" property="shortDesc"/>
                                </html:option>
                                </logic:equal>
                            </logic:iterate>
                        </logic:present>
                    </html:select>
                </logic:equal>
                <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetCategories">
                        <%
                            String categoryName = "";
                            Iterator it = ((UserAssetProfileMgrForm) theForm).getAssetCategories().iterator();
                            while (it.hasNext()) {
                                AssetData cat = (AssetData) it.next();
                                if (cat.getAssetId() == ((UserAssetProfileMgrForm) theForm).getAssetData().getParentId()) {
                                    categoryName = cat.getShortDesc();
                                    break;
                                }
                            }
                            if (Utility.isSet(categoryName)) {
                        %>
                        <%=categoryName%>
                        <% } %>
                    </logic:present>
                </logic:notEqual>
                </app:authorizedForFunction>
                <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetCategories">
                        <%
                            String categoryName = "";
                            Iterator it = ((UserAssetProfileMgrForm) theForm).getAssetCategories().iterator();
                            while (it.hasNext()) {
                                AssetData cat = (AssetData) it.next();
                                if (cat.getAssetId() == ((UserAssetProfileMgrForm) theForm).getAssetData().getParentId()) {
                                    categoryName = cat.getShortDesc();
                                    break;
                                }
                            }
                            if (Utility.isSet(categoryName)) {
                        %>
                        <%=categoryName%>
                        <% }%>
                    </logic:present>
                </app:notAuthorizedForFunction>
            </td>
            <td>&nbsp;</td>
            <td colspan="3">
                <span class="shopassetdetailtxt">
                    <strong>
                        <app:storeMessage key="userAssets.shop.text.param.longDescription"/>
                    </strong>:
                    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                        <span class="reqind">*</span>
                    </logic:equal>
                    </app:authorizedForFunction>
                </span>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td colspan="2" rowspan="2">
                <% String img = ClwCustomizer.getImgRelativePath() + "noMan.gif";%>
                <logic:present name="USER_ASSET_PROFILE_MGR_FORM"
                               property="mainAssetImageName">
                    <bean:define id="imageFileName" name="USER_ASSET_PROFILE_MGR_FORM"
                                 property="mainAssetImageName" type="java.lang.String"/>
                    <% if (Utility.isSet(imageFileName)) {
                        img = ClwCustomizer.getTemplateImgRelativePath() + "/" + imageFileName;
                    }%>
                </logic:present>
                <div style="width: 360; overflow-x:auto; overflow-y:hidden;">
                    <html:img border="1" src="<%=img%>"/>
                </div>
            </td>
            <td>&nbsp;</td>            
            <td colspan="3" valign="top">
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <html:textarea rows="15" cols="35" name="USER_ASSET_PROFILE_MGR_FORM"
                                   property="longDesc.value"/>
                </logic:equal>
                <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="longDesc.value">
                        <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="longDesc.value"/>
                    </logic:present>
                </logic:notEqual>
                </app:authorizedForFunction>
                <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="longDesc.value">
                        <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="longDesc.value"/>
                    </logic:present>
                </app:notAuthorizedForFunction>
            </td>
        </tr>
        
        <tr>
            <td colspan="7">&nbsp;</td>
        </tr>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <tr>
                <td>&nbsp;</td>
                <td colspan="2">
                    <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <html:file name="USER_ASSET_PROFILE_MGR_FORM" property="imageFile" size="35"/>
                    </logic:equal>
                </td>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="7">&nbsp;</td>
            </tr>
        </app:authorizedForFunction>

        <tr>
            <td>&nbsp;</td>
            <td>
                <span class="shopassetdetailtxt">
                    <strong>
                        <app:storeMessage key="userAssets.shop.text.param.manufacturer"/>
                    </strong>:
                </span>
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <span class="reqind">*</span>
                </logic:equal>
                </app:authorizedForFunction>
            </td>
            <td>
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <html:select styleClass="bugel" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufId">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="manufIdNamePairs">
                            <logic:iterate id="manuf" name="USER_ASSET_PROFILE_MGR_FORM" property="manufIdNamePairs"
                                           type="com.cleanwise.service.api.value.PairView">
                                <bean:define id="manufId" name="manuf" property="object1" type="java.lang.Integer"/>
                                <html:option value="<%=manufId.toString()%>">
                                    <bean:write name="manuf" property="object2"/>
                                </html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select>
                </logic:equal>
                <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufName">
                          <span class="shopassetdetailtxt">
                              <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufName"/>
                          </span>
                    </logic:present>
                </logic:notEqual>
                </app:authorizedForFunction>
                <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufName">
                          <span class="shopassetdetailtxt">
                              <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufName"/>
                          </span>
                    </logic:present>
                </app:notAuthorizedForFunction>
            </td>
            <td colspan="4">&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>
                <span class="shopassetdetailtxt">
                    <strong>
                        <app:storeMessage key="userAssets.shop.text.param.manufacturerSku"/>
                    </strong>:
                </span>
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <span class="reqind">*</span>
                </logic:equal>
                </app:authorizedForFunction>
            </td>
            <td>
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <span class="shopassetdetailtxt">
                        <html:text name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufSku"/>
                    </span>
                </logic:equal>
                <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufSku">
                        <span class="shopassetdetailtxt">
                            <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufSku"/>
                        </span>
                    </logic:present>
                </logic:notEqual>
                </app:authorizedForFunction>
                <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufSku">
                        <span class="shopassetdetailtxt">
                            <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufSku"/>
                        </span>
                    </logic:present>
                </app:notAuthorizedForFunction>
            </td>
            <td colspan="4">&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>
                <span class="shopassetdetailtxt">
                    <strong>
                        <app:storeMessage key="userAssets.shop.text.param.modelNumber"/>
                    </strong>:
                </span>
            </td>
            <td colspan="3">
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <html:text  size="74" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.modelNumber"/>
                </logic:equal>
                <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.modelNumber">
                         <span class="shopassetdetailtxt">
                            <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.modelNumber"/>
                         </span></logic:present>
                </logic:notEqual>
                </app:authorizedForFunction>
                <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.modelNumber">
                         <span class="shopassetdetailtxt">
        				  <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.modelNumber"/>
                         </span>
                    </logic:present>
                </app:notAuthorizedForFunction>
            </td>
            <td colspan="2">&nbsp;</td>
        </tr>

        <tr>
            <td>&nbsp;</td>
            <td>
                <span class="shopassetdetailtxt">
                    <strong>
                        <app:storeMessage key="userAssets.shop.text.param.status"/>
                    </strong>:
                </span>
            </td>
            <td>
                <bean:define id="statusCd" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.statusCd"
                             type="java.lang.String"/>
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <html:select name="USER_ASSET_PROFILE_MGR_FORM"
                                 property="assetData.statusCd">
                        <logic:present name="Asset.status.vector">
                            <logic:iterate id="statusRefCd" name="Asset.status.vector" type="com.cleanwise.service.api.value.RefCdData">
                                <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.status." + ((String)statusRefCd.getValue()).toUpperCase());%>
                                <html:option value="<%=statusRefCd.getValue()%>">
                                    <% if (messageKey != null) {%>

                                    <%=messageKey%>
                                    <%} else {%>
                                    <%=statusRefCd.getValue()%>
                                    <%}%>
                                </html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select>
                    <span class="reqind">*</span>
                </logic:equal>
                <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.statusCd">
                        <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.status." + statusCd.toUpperCase());
                            if (messageKey != null) {%>
                        <%=messageKey%>
                        <%} else{%>
                        <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.statusCd"/>
                        <%}%>
                    </logic:present>
                </logic:notEqual>
                </app:authorizedForFunction>

                <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.statusCd">
                        <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.status." + statusCd.toUpperCase());
                            if (messageKey != null) {%>
                        <%=messageKey%>
                        <%} else{%>
                        <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.statusCd"/>
                        <%}%>
                    </logic:present>
                </app:notAuthorizedForFunction>
            </td>
            <td colspan="4">&nbsp;</td>
        </tr>
        
        <tr>
            <td colspan="7">&nbsp;</td>
        </tr>
        
        <tr>
            <td colspan="7" align="center">
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'saveMasterAsset');">
                        <app:storeMessage key="global.action.label.save"/>
                    </html:button>
                </logic:equal>
                </app:authorizedForFunction>
            </td>
        </tr>
        
        </logic:equal>
        
        <html:hidden property="tabs" value="f_userAssetToolbar"/>
        <html:hidden property="display" value="t_userMasterAssetSearch"/>
        <html:hidden property="secondaryToolbar" value="f_userSecondaryToolbar"/>
        <html:hidden property="action" value="hiddenAction"/>
        
        </html:form>
        </table>
    </td>

</tr>

</table>


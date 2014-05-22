<%--
 Title:        t_userAssetWarrantiesConfig
 Description:  asset warranty config.
 Purpose:      asset warranty relationship configuration.
 Copyright:    Copyright (c) 2009
 Company:      CleanWise, Inc.
 Date:         20.01.2009
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

<script language="JavaScript1.2">
<!--
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
    String messageKey;

    String jspFormName = UserAssetMgrLogic.USER_ASSET_PROFILE_MGR_FORM;
    String jspFormNestProperty = "locateStoreSiteForm";
    String jspFormAction = "";
    String jspSubmitIdent = "";
    String jspReturnFilterProperty = "";

    String returnFilterPropertyName = jspFormNestProperty + ".property";
    String formNamePropertyName = jspFormNestProperty + ".name";
    String searchFieldPropertyName = jspFormNestProperty + ".searchField";
    String searchFieldTypePropertyName = jspFormNestProperty + ".searchType";
    String searchFieldCityPropertyName = jspFormNestProperty + ".city";
    String searchFieldStatePropertyName = jspFormNestProperty + ".state";
    String selectPropertyName = jspFormNestProperty + ".selected";
    String evenRowColor = ClwCustomizer.getEvenRowColor(request.getSession());
    String oddRowColor = ClwCustomizer.getOddRowColor(request.getSession());
    
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    boolean isAssetAdministrator = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR);
    
    boolean initlocateEngine = false;
%>
<logic:present name="<%=jspFormName%>" property="<%=jspFormNestProperty%>">
    <bean:define id="theLocateForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>"
                 type="com.cleanwise.view.forms.LocateStoreSiteForm"/>

    <%
        jspFormAction = "userAssetProfile.do";
        jspSubmitIdent = "#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_SITE_FORM;
        jspReturnFilterProperty = "siteFilter";
        initlocateEngine = true;

    %>

</logic:present>

<script language="JavaScript" src="../externals/ajaxutil.js"></script>
<script language="JavaScript" src="../externals/assetutil.js"></script>

<%
    String browser = (String) request.getHeader("User-Agent");
    String isMSIE = "";
    if (browser != null && browser.indexOf("MSIE") >= 0) {
        isMSIE = "Y";
%>
<script language="JavaScript" src="../externals/calendar.js"></script>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
        marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% } else { %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% } %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="USER_ASSET_PROFILE_MGR_FORM" type="com.cleanwise.view.forms.UserAssetProfileMgrForm"/>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">

<tr>

<td>
<table width="100%">

<html:form action="/userportal/userAssetProfile.do"
           name="USER_ASSET_PROFILE_MGR_FORM"
           type="com.cleanwise.view.forms.UserAssetProfileMgrForm">
<tr>
    <td colspan="7">&nbsp;</td>
</tr>

<% if (isAssetAdministrator) { %>
<tr>
    <td></td>
    <td colspan="5">
        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetWarrantyAssoc">
            <bean:size id="rescount" name="USER_ASSET_PROFILE_MGR_FORM" property="assetWarrantyAssoc"/>
            <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr>
                    <td colspan="3" align="left">
                        <% String img = ClwCustomizer.getImgRelativePath() + "noMan.gif";%>
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM"
                                       property="mainAssetImageName">
                            <bean:define id="imageFileName" name="USER_ASSET_PROFILE_MGR_FORM"
                                         property="mainAssetImageName"
                                         type="java.lang.String"/>
                            <% if (Utility.isSet(imageFileName)) {
                                img = ClwCustomizer.getTemplateImgRelativePath() + "/" + imageFileName;
                            }%>
                        </logic:present>
                        <div style="width: 740; overflow-x:auto; overflow-y:hidden;">
                            <html:img border="1" src="<%=img%>"/>
                        </div>
                    </td>
                </tr>
                <tr><td colspan="3">&nbsp;</td></tr>
                <tr>
                    <td width="30%">
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.text.assetName"/>:</b>
                        </span>
                    </td>
                    <td align="left">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc">
                            <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc"/>
                        </logic:present>
                    </td>
                    
                </tr>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.text.assetCategory"/>:</b>
                        </span>
                    </td>
                    <td align="left">
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
                                if (Utility.isSet(categoryName)) { %>
                                    <%=categoryName%>
                            <%  } else { %>
                            -
                            <% } %>
                        </logic:present>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.shop.text.param.longDescription"/>:</b>
                        </span>
                    </td>
                    <td align="left">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="longDesc.value">
                            <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="longDesc.value"/>
                        </logic:present>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.shop.text.param.manufacturer"/>:</b>
                        </span>
                    </td>
                    <td align="left">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufName">
                            <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufName"/>
                        </logic:present>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.shop.text.param.assetnumber"/>:</b>
                        </span>
                    </td>
                    <td align="left">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetNum">
                            <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetNum"/>
                        </logic:present>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.shop.text.param.serialnumber"/>:</b>
                        </span>
                    </td>
                    <td align="left">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.serialNum">
                            <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.serialNum"/>
                        </logic:present>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.shop.text.param.modelNumber"/>:</b>
                        </span>
                    </td>
                    <td align="left">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.modelNumber">
                            <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.modelNumber"/>
                        </logic:present>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.shop.text.param.acquisitiondate"/>:</b>
                        </span>
                    </td>
                    <td align="left">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="acquisitionDate.value">
                            <bean:define id="acquisitionDate" name="USER_ASSET_PROFILE_MGR_FORM"
                                         property="acquisitionDate.value"
                                         type="java.lang.String"/>
                            <%if (Utility.isSet(acquisitionDate)) { %>
                                <%=ClwI18nUtil.formatDate(request, acquisitionDate, DateFormat.DEFAULT)%>
                            <% } else { %>
                            -
                            <% } %>
                        </logic:present>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.shop.text.param.acquisitioncost"/>:</b>
                        </span>
                    </td>
                    <td align="left">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="acquisitionCost.value">
                            <bean:define id="acquisitionCost" name="USER_ASSET_PROFILE_MGR_FORM"
                                         property="acquisitionCost.value"
                                         type="java.lang.String"/>
                        <%if (Utility.isSet(acquisitionCost)) { %>
                            <%=ClwI18nUtil.getPriceShopping(request, acquisitionCost, "&nbsp;")%>
                        <% } else { %>
                        -
                        <% } %>
                        </logic:present>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.shop.text.param.dateLastHMR"/>:</b>
                        </span>
                    </td>
                    <td align="left">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="dateLastHMR.value">
                            <bean:define id="dateLastHMR" name="USER_ASSET_PROFILE_MGR_FORM"
                                         property="dateLastHMR.value"
                                         type="java.lang.String"/>
                            <%if (Utility.isSet(dateLastHMR)) { %>
                                <%=ClwI18nUtil.formatDate(request, dateLastHMR, DateFormat.DEFAULT)%>
                            <% } else { %>
                            -
                            <% } %>
                        </logic:present>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.shop.text.param.lastHMR"/>:</b>
                        </span>
                    </td>
                    <td align="left">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="lastHMR.value">
                            <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="lastHMR.value"/>
                        </logic:present>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.shop.text.param.dateInService"/>:</b>
                        </span>
                    </td>
                    <td align="left">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="dateInService.value">
                            <bean:define id="dateInService" name="USER_ASSET_PROFILE_MGR_FORM"
                                         property="dateInService.value"
                                         type="java.lang.String"/>
                            <%if (Utility.isSet(dateInService)) { %>
                                <%=ClwI18nUtil.formatDate(request, dateInService, DateFormat.DEFAULT)%>
                            <% } else { %>
                            -
                            <% } %>
                        </logic:present>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="userAssets.shop.text.param.status"/>:</b>
                        </span>
                    </td>
                    <td align="left">
                        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.statusCd">
                            <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.statusCd"/>
                        </logic:present>
                    </td>
                </tr>
                <tr><td colspan="3">&nbsp;</td></tr>
                <tr>
                    <td align="left">
                        <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'createNewWarranty');">
                            <app:storeMessage key="userWarranty.text.createWarranty"/>
                        </html:button>
                    </td>
                    <td colspan="2" align="right">
                        <app:storeMessage key="global.label.viewAll"/>:
                        <html:checkbox name="USER_ASSET_PROFILE_MGR_FORM" property="showConfiguredWarrantiesOnly" value='true'/>
                        &nbsp;
                        <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'configAssetWarranty');">
                            <app:storeMessage key="global.action.label.search"/>
                        </html:button>
                    </td>
                </tr>
                <tr><td colspan="3">&nbsp;</td></tr>
            </table>
            <table width="100%" cellpadding="1" cellspacing="1" border="0" style="border:#D3D3D3 1px solid">
                <tr class="tableheaderinfo">
                    <td class="shopcharthead">&nbsp;</td>
                    <td width="20%" class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.warrantyNumber"/>
                        </div>
                    </td>
                    <td width="20%" class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.warrantyType"/>
                        </div>
                    </td>
                    <td width="20%" class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.warrantyDuration"/>
                        </div>
                    </td>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.expDate"/>
                        </div>
                    </td>
                    <td class="shopcharthead">
                        <div class="fivemargin"></div>
                    </td>
                </tr>
                <%
                    String currentPropertyToIterate;
                    if (!theForm.getShowConfiguredWarrantiesOnly()) {
                        currentPropertyToIterate = "assetWarrantyAssoc";
                    } else {
                        currentPropertyToIterate = "storeWarranties";
                    }
                %>
                
                <logic:iterate id="arrele" name="USER_ASSET_PROFILE_MGR_FORM" property="<%=currentPropertyToIterate%>" indexId="i" type="com.cleanwise.service.api.value.WarrantyData">
                    <%  
                        String bgColor;
                        if ((i % 2) == 0) {
                            bgColor = evenRowColor;
                        } else {
                            bgColor = oddRowColor;
                        }
                        String warrantyId = String.valueOf(arrele.getWarrantyId());
                    %>               
                    <tr style="background-color: <%=bgColor%>">
                        
                        <td align="center">
                            <html:multibox name="USER_ASSET_PROFILE_MGR_FORM" property="assetWarrantyIds" value="<%=warrantyId%>" />
                        </td>
                        <td align="center">
                            <bean:define id="eleid" name="arrele" property="warrantyId"/>
                            <a href="../userportal/userWarrantyDetail.do?action=detail&warrantyId=<%=eleid%>&display=t_userWarrantyDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                <bean:write name="arrele" property="warrantyNumber"/>
                            </a>
                        </td>
                        <td align="center">
                            <bean:write name="arrele" property="typeCd"/>
                        </td>
                        <td align="center">
                            <bean:write name="arrele" property="duration"/>
                            &nbsp;
                            <logic:present name="arrele" property="durationTypeCd">
                                <bean:define id="durationTypeCd" name="arrele" property="durationTypeCd"
                                             type="java.lang.String"/>
                                <%String durationTypeKey = "userWarranty.text.warrantyDurationTypeCd." + durationTypeCd.toUpperCase();%>
                                <app:storeMessage key="<%=durationTypeKey%>"/>
                            </logic:present>
                        </td>
                        <%GregorianCalendar dateInServCal=null;%>
                        <td align="center">
                            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="dateInService">
                                <logic:present  name="arrele" property="duration">
                                    <logic:present  name="arrele" property="durationTypeCd">
                                        <bean:define id="durationInt"  name="arrele" property="duration"  type="java.lang.Integer"/>
                                        <bean:define id="durationType"   name="arrele" property="durationTypeCd"  type="java.lang.String"/>
                                        <bean:define id="dateInService" name="USER_ASSET_PROFILE_MGR_FORM" property="dateInService.value"  type="java.lang.String"/>
                                        <%if (Utility.isSet(dateInService) && Utility.isSet(durationType)) {
                                            Date date = null;
                                            try {
                                                date = ClwI18nUtil.parseDateInp(request, dateInService);
                                                dateInServCal = new GregorianCalendar();
                                                dateInServCal.setTime(date);
                                                if (RefCodeNames.WARRANTY_DURATION_TYPE_CD.MONTHS.equals(durationType)) {
                                                    dateInServCal.add(GregorianCalendar.MONTH, ((Integer) durationInt).intValue());
                                                } else
                                                if (RefCodeNames.WARRANTY_DURATION_TYPE_CD.YEARS.equals(durationType)) {
                                                    dateInServCal.add(GregorianCalendar.YEAR, ((Integer) durationInt).intValue());
                                                }
                                        %>
                                        <%=ClwI18nUtil.formatDate(request,dateInServCal.getTime(),DateFormat.DEFAULT)%>
                                        <%} catch (ParseException e) {} %>
                                        <%}%>
                                    </logic:present>
                                </logic:present>
                            </logic:present>
                        </td>
                        <td align="center">  
                            <% if(dateInServCal!=null && new Date().after(dateInServCal.getTime())) { %>
                                <%String expAlertImg=IMGPath+"/expAlert.gif";%>
                                <html:img border="0" src="<%=expAlertImg%>"/>
                            <% } %>
                        </td>
                    </tr>
                </logic:iterate>
            </table>
        </logic:present>
    </td>
    <td></td>
</tr>
<% } else { %>
    <tr>
        <td align="center" colspan="7" class="shopassetdetailtxt">
            <font color="red">
                <b><app:storeMessage key="userAssets.text.needAssetAdministrator"/></b>
            </font>
        </td>
    </tr>
<% } %>
<tr>
    <td colspan="7">&nbsp;</td>
</tr>

<tr>
    <td colspan="7" align="center">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'saveAssetWarrantyConfig');">
                <app:storeMessage key="global.action.label.save"/>
            </html:button>
        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'backAssetWarrantyConfig');">
                <app:storeMessage key="admin.button.back"/>
            </html:button>
        </app:notAuthorizedForFunction>
    </td>
</tr>

<html:hidden property="tabs" value="f_userAssetToolbar"/>
<html:hidden property="display" value="t_userAssetWarrantiesConfig"/>
<html:hidden property="secondaryToolbar" value="f_userSecondaryToolbar"/>
<html:hidden property="action" value="hiddenAction"/>
</html:form>
</table>
</td>

</tr>

</table>


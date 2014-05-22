<%--
 Title:        t_userAssetServiceDetail
 Description:  asset profile.
 Purpose:      asset profile management.
 Copyright:    Copyright (c) 2007
 Company:      CleanWise, Inc.
 Date:         20.11.2007
 Time:         14:02:44
 @author       Alexander Chickin, TrinitySoft, Inc.

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

<bean:define id="theForm" name="USER_ASSET_PROFILE_MGR_FORM"
 type="com.cleanwise.view.forms.UserAssetProfileMgrForm"/>

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
    boolean isManagedAsset = (theForm.getAssetData().getMasterAssetId() > 0);
    boolean isStateProvinceRequired = appUser.getUserStore().isStateProvinceRequired();

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
    return false;
}

function viewPrinterFriendly(loc) {
    var prtwin = window.open(loc, "view_docs", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
    prtwin.focus();
    return false;
}

function changeAssetStatus(status) {
    var inReasonDiv = document.getElementById("inactiveReasonTR");
    var inReasonVal = document.getElementById("inactiveReasonVal");
    if (status.value == inactiveReason.active) {
        inReasonVal.value = "";
        inReasonDiv.style.visibility = 'hidden';
    } else if (status.value == inactiveReason.inactive) {
        inReasonDiv.style.visibility = 'visible';
    }
}

var locateBox = {
    displayfrequency: ["chance", "1"],
    defineheader: '<div>' +
                  '<div align="left">Find Sites</div><table width="100%"  border="0"  class="mainbody">' +
                  '<input type="hidden" id="jspSubmitIdent" name="jspSubmitIdent" value="<%=jspSubmitIdent%>">' +
                  '<input type="hidden" id="<%=returnFilterPropertyName%>" name="<%=returnFilterPropertyName%>" value="<%=jspReturnFilterProperty%>">' +
                  '<input type="hidden" id="<%=formNamePropertyName%>" name="<%=formNamePropertyName%>" value="<%=jspFormName%>">' +
                  '<tr>' +
                  '<td><b>Search Site:</b></td>' +
                  '<td><input type="text" id="<%=searchFieldPropertyName%>" name="<%=searchFieldPropertyName%>" value=""  id="mainField">' +
                  '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
                  '<input type="radio" id="<%=searchFieldTypePropertyName%>1" name="<%=searchFieldTypePropertyName%>" value="id"> ID' +
                  '<input type="radio" id="<%=searchFieldTypePropertyName%>2" name="<%=searchFieldTypePropertyName%>" value="nameBegins" checked="checked"> Name(starts with)' +
                  '<input type="radio" id="<%=searchFieldTypePropertyName%>3" name="<%=searchFieldTypePropertyName%>" value="nameContains">Name(contains)' +
                  '</td>' +
                  '</tr>' +

                  '<tr>' +
                  '<td><b>City:</b></td>' +
                  '<td><input type="text" id="<%=searchFieldCityPropertyName%>" name="<%=searchFieldCityPropertyName%>" value=""> </td>' +
                  '</tr>' +
                  '<tr>' +
                  '<td><b>State:</b></td>' +
                  '<td><input type="text" id="<%=searchFieldStatePropertyName%>" name="<%=searchFieldStatePropertyName%>" value=""> </td>' +
                  '</tr>' +

                  '<tr>' +
                  '<td>&nbsp;' +
                  '</td>' +
                  '<td colspan="2">' +
                  '<input type="button" name="action" value="Search" onclick="searchsite()">' +
                  '<input type="button" name="action" value="Cancel" onclick="locateBox.closeit()">' +
                  '</td>' +
                  '<td>&nbsp;' +
                  '</td>' +
                  '</tr>' +

                  '</table>' +
                  '</div>',
    tableHeader:'<thead>' +
                '<tr align="left">' +
                '<td class="shopcharthead"><div class="fivemargin"><%=ClwI18nUtil.getMessageOrNull(request,"userlocate.site.text.siteName")%></div></td>' +
                '<td class="shopcharthead"><div class="fivemargin"><%=ClwI18nUtil.getMessageOrNull(request,"userlocate.site.text.rank")%></div></td>' +
                '<td class="shopcharthead"><div class="fivemargin"><%=ClwI18nUtil.getMessageOrNull(request,"userlocate.site.text.accountName")%></div></td>' +
                '<td class="shopcharthead"><div class="fivemargin"><%=ClwI18nUtil.getMessageOrNull(request,"userlocate.site.text.streetAddress")%></div></td>' +
                '<td class="shopcharthead"><div class="fivemargin"><%=ClwI18nUtil.getMessageOrNull(request,"userlocate.site.text.city")%></div></td>' +
                '<td class="shopcharthead"><div class="fivemargin"><%=ClwI18nUtil.getMessageOrNull(request,"userlocate.site.text.state")%></div></td>' +
                '<td class="shopcharthead"><div class="fivemargin"><%=ClwI18nUtil.getMessageOrNull(request,"userlocate.site.text.status")%></div></td>' +
                '</tr></thead>',
    tableBody:"",
    fix:false,
    ajaxbustcache: true,
    disablescrollbars: false,
    autohidetimer: 0,
    ie7: window.XMLHttpRequest && document.all && !window.opera,
    ie7offline: this.ie7 && window.location.href.indexOf("http") == -1, //check for IE7 and offline
    launch:false,
    init:false,
    scrollbarwidth: 16,

    loadpage:function(page_request, thediv) {


        locateBox.hidescrollbar();
        locateBox.getscrollbarwidth();
        locateBox.showcontainer();


    },

    hidebugcontrol:function() {
        if (this.bugelements != "undefined") {
            for (var i = 0; i < this.bugelements.length; i++) {
                this.bugelements[i].style.visibility = 'hidden';
            }
        }
    },

    restorebugcontrol:function() {

        if (this.bugelements != "undefined") {
            for (var i = 0; i < this.bugelements.length; i++) {
                this.bugelements[i].style.visibility = 'visible';
            }
        }
    },

    createcontainer:function() {
        document.write('<div id="interContainer">' + this.defineheader + '<div id="interContent"></div></div><div id="interVeil"></div>')
        this.interContainer = document.getElementById("interContainer") //reference interstitial container
        this.interContent = document.getElementById("interContent") //reference interstitial content
        this.interVeil = document.getElementById("interVeil") //reference veil
        this.standardbody = (document.compatMode == "CSS1Compat")? document.documentElement : document.body //create reference to common "body" across doctypes
        this.bugelements = document.getElementsByTagName("select");
    }
    ,

    showcontainer:function() {

        if (!this.launch && !this.init) return //if interstitial box has already closed, just exit (window.onresize event triggers function)

        this.interContent.style.display   = "block";
        this.interContainer.style.display = "block";
        this.interVeil.style.display      = "block";


        this.hidebugcontrol();

        var ie = document.all && !window.opera
        var dom = document.getElementById
        var docwidth = (ie)? this.standardbody.clientWidth : window.innerWidth - this.scrollbarwidth
        var docheight = (ie)? this.standardbody.clientHeight: window.innerHeight

        this.interContent.style.height = Math.floor(parseInt(0.4 * docheight));
        this.interContent.style.overflow = 'auto';

        var scroll_top = (ie)? this.standardbody.scrollTop : window.pageYOffset
        var scroll_left = (ie)? this.standardbody.scrollLeft : window.pageXOffset
        var docheightcomplete = (this.standardbody.offsetHeight > this.standardbody.scrollHeight)? this.standardbody.offsetHeight : this.standardbody.scrollHeight
        var objwidth = this.interContainer.offsetWidth
        var objheight = this.interContainer.offsetHeight
        this.interVeil.style.width = docwidth + "px" //set up veil over page
        this.interVeil.style.height = docheightcomplete + "px" //set up veil over page
        this.interVeil.style.left = 0 //Position veil over page
        this.interVeil.style.top = 0 //Position veil over page
        this.interVeil.style.visibility = "visible" //Show veil over page
        this.interContainer.style.left = docwidth / 2 - objwidth / 2 + "px" //Position interstitial box
        var topposition = (docheight > objheight)? scroll_top + docheight / 2 - objheight / 2 + "px" : scroll_top + 5 + "px" //Position interstitial box
        this.interContainer.style.top = Math.floor(parseInt(topposition)) + "px"
        this.interContainer.style.visibility = "visible" //Show interstitial box
        if (this.autohidetimer && parseInt(this.autohidetimer) > 0 && typeof this.timervar == "undefined")
            this.timervar = setTimeout("locateBox.closeit()", this.autohidetimer * 1000)
    }
    ,


    closeit:function() {
        this.interVeil.style.display = "none"
        this.interContainer.style.display = "none"
        this.interContent.style.display = "none"
        if (this.disablescrollbars && window.XMLHttpRequest) //if disablescrollbars enabled and modern browsers- IE7, Firefox, Safari, Opera 8+ etc
            this.standardbody.style.overflow = "auto"
        if (typeof this.timervar != "undefined") clearTimeout(this.timervar)
        this.restorebugcontrol();
        locateBox.launch = false;
    }
    ,

    getscrollbarwidth:function() {
        var scrollbarwidth = window.innerWidth - (this.interVeil.offsetLeft + this.interVeil.offsetWidth) //http://www.howtocreate.co.uk/emails/BrynDyment.html
        this.scrollbarwidth = (typeof scrollbarwidth == "number")? scrollbarwidth : this.scrollbarwidth
    }
    ,

    hidescrollbar:function() {
        if (this.disablescrollbars) { //if disablescrollbars enabled
            if (window.XMLHttpRequest) //if modern browsers- IE7, Firefox, Safari, Opera 8+ etc
                this.standardbody.style.overflow = "hidden"
            else //if IE6 and below, just scroll to top of page to ensure interstitial is in focus
                window.scrollTo(0, 0)
        }
    }
    ,

    dotask:function(target, functionref, tasktype) { //assign a function to execute to an event handler (ie: onunload)
        tasktype = (window.addEventListener)? tasktype : "on" + tasktype
        if (target.addEventListener)
            target.addEventListener(tasktype, functionref, false)
        else if (target.attachEvent)
            target.attachEvent(tasktype, functionref)
    }
    ,

    initialize:function() {
        this.createcontainer()
        this.init = true;
    }
}
<%if(initlocateEngine){%>
if (Math.floor(Math.random() * locateBox.displayfrequency[1]) == 0) {
    locateBox.initialize();
}
<%}%>

function launchlocatebox() {
    if (locateBox.init) {
        locateBox.launch = true;
        locateBox.showcontainer();
    }
}

function searchsite() {
    var jspSubmitIdent = document.getElementById("jspSubmitIdent");
    var retFilter = document.getElementById("<%=returnFilterPropertyName%>");
    var formName = document.getElementById("<%=formNamePropertyName%>");
    var searchField = document.getElementById("<%=searchFieldPropertyName%>");
    var searchType = document.getElementsByName("<%=searchFieldTypePropertyName%>");
    var city = document.getElementById("<%=searchFieldCityPropertyName%>");
    var state = document.getElementById("<%=searchFieldStatePropertyName%>");
    var searchTypeVal = "";
    if ('undefined' != typeof  searchType) {
        for (var i = 0; i < searchType.length; i++) {
            if (searchType[i].checked) {
                searchTypeVal = searchType[i].value;
                break;
            }
        }
    }
    var cityVal = "";
    if ('undefined' != typeof  city) {
        cityVal = city.value;
    }

    var stateVal = "";
    if ('undefined' != typeof  state) {
        stateVal = state.value;
    }

    var ajaxParamStr = "action=Search&requestType=async&jspSubmitIdent=" + jspSubmitIdent.value +
                       "&<%=returnFilterPropertyName%>=" + retFilter.value +
                       "&<%=formNamePropertyName%>=" + formName.value +
                       "&<%=searchFieldPropertyName%>=" + searchField.value +
                       "&<%=searchFieldTypePropertyName%>=" + searchTypeVal +
                       "&<%=searchFieldCityPropertyName%>=" + cityVal +
                       "&<%=searchFieldStatePropertyName%>=" + stateVal;

    ajaxconnect('<%=jspFormAction%>', ajaxParamStr, 'interContent', responseXml)
}

function returnselected() {

    var jspSubmitIdent = document.getElementById("jspSubmitIdent");
    var retFilter = document.getElementById("<%=returnFilterPropertyName%>");
    var formName = document.getElementById("<%=formNamePropertyName%>");
    var selected = document.getElementsByName('<%=selectPropertyName%>');
    var selectedVal = "";
    var selectedName = "";
    if ('undefined' != typeof  selected) {
        for (var i = 0; i < selected.length; i++) {
            if (selected[i].checked) {
                selectedVal = selected[i].value;
                selectedName = selected[i].name;
                break;
            }
        }
    }

    var ajaxParamStr = "action=Return Selected&requestType=async&jspSubmitIdent=" + jspSubmitIdent.value +
                       "&<%=returnFilterPropertyName%>=" + retFilter.value +
                       "&<%=formNamePropertyName%>=" + formName.value +
                       "&<%=selectPropertyName%>=" + selectedVal;

    ajaxconnect('<%=jspFormAction%>', ajaxParamStr, null, locationDynamicBox.populateAndReDraw)

    locateBox.closeit();
}

function returnclicked (id) {

    var jspSubmitIdent = document.getElementById("jspSubmitIdent");
    var retFilter = document.getElementById("<%=returnFilterPropertyName%>");
    var formName = document.getElementById("<%=formNamePropertyName%>");
    var selected = document.getElementsByName('<%=selectPropertyName%>');

    var ajaxParamStr = "action=Return Selected&requestType=async&jspSubmitIdent=" + jspSubmitIdent.value +
                       "&<%=returnFilterPropertyName%>=" + retFilter.value +
                       "&<%=formNamePropertyName%>=" + formName.value +
                       "&<%=selectPropertyName%>=" + id;

    ajaxconnect('<%=jspFormAction%>', ajaxParamStr, null, locationDynamicBox.populateAndReDraw)

    locateBox.closeit();
}

function responseXml(xml, thediv) {

    if (thediv == null) {
        locateBox.closeit();
        return;
    }

    if (xml != null) {

        var str = "<table width='100%'>";
        str += locateBox.tableHeader;
        this.tableBody = "<tbody>";
        var root = xml.getElementsByTagName("AssetViewVector")[0];
        if (root != null && 'undefined' != typeof root) {
            var size = root.getAttribute("size");
            if ('undefined' != typeof size && size != "") {
                if (size > 0) {
                    var sites = root.getElementsByTagName("Site");

                    for (var i = 0; i < sites.length; i++) {
                        var bgcolor;
                        if (i % 2 == 0) {
                            bgcolor = "<%=evenRowColor%>";
                        } else {
                            bgcolor = "<%=oddRowColor%>";
                        }
                        this.tableBody += "<tr  bgcolor='" + bgcolor + "'>";
                        var id = sites.item(i).getAttribute("Id");
                        var siteName = sites.item(i).getElementsByTagName("Name")[0].firstChild.nodeValue;
                        var rank = sites.item(i).getElementsByTagName("Rank")[0].firstChild.nodeValue;
                        var accountName = sites.item(i).getElementsByTagName("AccountName")[0].firstChild.nodeValue;
                        var address = sites.item(i).getElementsByTagName("StreetAddress")[0].firstChild.nodeValue;
                        var city = sites.item(i).getElementsByTagName("City")[0].firstChild.nodeValue;
                        var state = sites.item(i).getElementsByTagName("State")[0].firstChild.nodeValue;
                        var status = sites.item(i).getElementsByTagName("Status")[0].firstChild.nodeValue;

                        this.tableBody += "<td><a href='javascript:returnclicked(" + id + ")'>" + siteName + "</a></td>";
                        this.tableBody += "<td>" + rank + "</td>";
                        this.tableBody += "<td>" + accountName + "</td>";
                        this.tableBody += "<td>" + address + "</td>";
                        this.tableBody += "<td>" + city + "</td>";
                        this.tableBody += "<td>" + state + "</td>";
                        this.tableBody += "<td>" + status + "</td>";
                        this.tableBody += "</tr>";
                    }
                }
            }
        }
        this.tableBody += "</tbody>";
        str += this.tableBody;
        str += "</table>";
        document.getElementById(thediv).innerHTML = str;

    }
}

function buttonLink(link) {
    window.location.href = link;
}
//-->


</script>
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


<!-- item picture and long description -->

<tr>

<td>
<table width="100%">
<tr>
    <td width="1%">&nbsp;</td>
    <td width="28%">&nbsp;</td>
    <td width="25%">&nbsp;</td>
    <td width="1%">&nbsp;</td>
    <td width="20%">&nbsp;</td>
    <td width="24%">&nbsp;</td>
    <td width="1%">&nbsp;</td>
</tr>
<html:form action="/userportal/userAssetProfile.do"
           name="USER_ASSET_PROFILE_MGR_FORM"
           type="com.cleanwise.view.forms.UserAssetProfileMgrForm" enctype="multipart/form-data">
<tr>
    <td></td>
    <td>

      <span class="shopassetdetailtxt">
        <strong>
            <app:storeMessage key="userAssets.text.assetName"/>
        </strong>:
        <% if (isAssetAdministrator && !isManagedAsset) { %>
           <span class="reqind">*</span>
        <% } %>
      </span>
    </td>
    <td colspan="4">
        <% if (isAssetAdministrator && !isManagedAsset) { %>
            <html:text size="74" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc"/>
        <% } else { %>
            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc">
                <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc"/>
            </logic:present>
        <% } %>
    </td>

    <td></td>
</tr>
<logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetTypeCd"
             value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">

<tr>
    <td></td>
      <td> <span class="shopassetdetailtxt">
        <strong>
            <app:storeMessage key="userAssets.text.assetCategory"/>
        </strong>:
      </span></td>
        <td>
            <% if (isAssetAdministrator && !isManagedAsset) { %>
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
            <% } else { %>
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
            <% } %>
        </td>
    <td></td>
    <td colspan="2">
       <span class="shopassetdetailtxt">
        <strong>
            <app:storeMessage key="userAssets.shop.text.param.longDescription"/>
        </strong>:
       <% if (isAssetAdministrator && !isManagedAsset) { %>
           <span class="reqind">*</span>
       <% } %>
      </span>
    </td>
    <td></td>
</tr>
<tr>
    <td></td>
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
    <td></td>
    <td colspan="2" valign="top">
        <% if (isAssetAdministrator && !isManagedAsset) { %>
            <html:textarea rows="15" cols="35" name="USER_ASSET_PROFILE_MGR_FORM"
                           property="longDesc.value"/>
        <% } else { %>
            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="longDesc.value">
                <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="longDesc.value"/>
            </logic:present>
        <% } %>
    </td>
    <td></td>
</tr>
<tr>
    <td></td>
    <td></td>
    <td colspan="2" valign="bottom">
        <logic:greaterThan name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetId" value="0">
        <html:button property="action" styleClass="store_fb"
                     onclick="actionSubmit(0,'createNewWorkOrder');">
            <app:storeMessage key="userAssets.shop.button.createWorkOrder"/>
        </html:button>  </logic:greaterThan>
    </td>
    <td></td>
</tr>
<% if (isAssetAdministrator && !isManagedAsset) { %>
    <tr>
        <td></td>
        <td colspan="2">
            <html:file name="USER_ASSET_PROFILE_MGR_FORM" property="imageFile" size="35"/>
        </td>
        <td>&nbsp;</td>
        <td colspan="2">&nbsp;</td>
        <td></td>
    </tr>
<% } %>
<tr>
    <td></td>
    <td colspan="2">&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2" align="left">
        <span class="shopassetdetailtxt">
            <strong>
                <app:storeMessage key="userAssets.shop.text.param.customDescription"/>
            </strong>:
        </span>
    </td>
    <td></td>
</tr>
<tr>
    <td></td>
    <td colspan="2">&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2" rowspan="4" align="left">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:textarea rows="6" cols="35" name="USER_ASSET_PROFILE_MGR_FORM"
                           property="customDesc.value"/>
        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="customDesc.value">
                <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="customDesc.value"/>
            </logic:present>
        </app:notAuthorizedForFunction>
    </td>
    <td></td>
</tr>
<tr>
    <td></td>
    <td>
        <span class="shopassetdetailtxt">
            <strong>
                <app:storeMessage key="userAssets.shop.text.param.manufacturer"/>:
            </strong>
        </span>
        <% if (isAssetAdministrator && !isManagedAsset) { %>
            <span class="reqind">*</span>
        <% } %>
    </td>
    <td>
        <% if (isAssetAdministrator && !isManagedAsset) { %>
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
        <% } else { %>
            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufName">
                  <span class="shopassetdetailtxt">
                      <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufName"/>
                  </span>
            </logic:present>
        <% } %>
    </td>
    <td colspan="3">&nbsp;</td>
    <td></td>
</tr>
<tr>
    <td></td>
    <td>
    <span class="shopassetdetailtxt"> <strong>
        <app:storeMessage key="userAssets.shop.text.param.manufacturerSku"/>
        : </strong> </span>
        <% if (isAssetAdministrator && !isManagedAsset) { %>
            <span class="reqind">*</span>
        <% } %>
    </td>
    <td>
        <% if (isAssetAdministrator && !isManagedAsset) { %>
           <span class="shopassetdetailtxt">
                      <html:text name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufSku"/>
                  </span>
        <% } else { %>
            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufSku">
                  <span class="shopassetdetailtxt">
                      <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufSku"/>
                  </span>
            </logic:present>
        <% } %>
    </td>
    <td colspan="2">&nbsp;</td>
    <td></td>
</tr>
<tr>
    <td></td>
    <td>
        <span class="shopassetdetailtxt">
            <strong>
                <app:storeMessage key="userAssets.shop.text.param.assetnumber"/> :
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetNum">
                        <logic:equal name="theForm" property="userAssignedAssetNumber" value="true">
                            <span class="reqind">*</span>
                        </logic:equal>
                    </logic:present>
                </app:authorizedForFunction>
            </strong>
        </span>
    </td>
    <td>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:equal name="theForm" property="userAssignedAssetNumber" value="true">
                <span class="shopassetdetailtxt">
                    <html:text name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetNum"/>
                </span>
            </logic:equal>
            <logic:notEqual name="theForm" property="userAssignedAssetNumber" value="true">
                <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetNum">
                    <span class="shopassetdetailtxt">
                        <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetNum"/>
                    </span>
                </logic:present>
            </logic:notEqual>
        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetNum">
                <span class="shopassetdetailtxt">
                    <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetNum"/>
                </span>
            </logic:present>
        </app:notAuthorizedForFunction>
    </td>
    <td colspan="2">&nbsp;</td>
    <td></td>
</tr>
<tr>
    <td></td>
    <td><span class="shopassetdetailtxt"><strong>
        <app:storeMessage key="userAssets.shop.text.param.serialnumber"/>
        : </strong></span>
    </td>
    <td colspan="4">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
				  <span class="shopassetdetailtxt">
				  <html:text  size="74" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.serialNum"/>
                  </span>
        </app:authorizedForFunction>

        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.serialNum">
                  <span class="shopassetdetailtxt">
				  <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.serialNum"/>
                  </span></logic:present>

        </app:notAuthorizedForFunction>

    </td>
    <td></td>
</tr>
<tr>
    <td></td>
    <td>
           <span class="shopassetdetailtxt"> <strong>
               <app:storeMessage key="userAssets.shop.text.param.modelNumber"/>
               : </strong> </span></td>
    <td colspan="4">
        <% if (isAssetAdministrator && !isManagedAsset) { %>
            <html:text  size="74" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.modelNumber"/>
        <% } else { %>
            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.modelNumber">
                 <span class="shopassetdetailtxt">
				  <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.modelNumber"/>
                  </span></logic:present>
        <% } %>
    </td>
    <td></td>
</tr>
<tr>
    <td></td>
    <td>
        <span class="shopassetdetailtxt">
            <strong>
                <app:storeMessage key="userAssets.shop.text.param.acquisitiondate"/>:
            </strong><br>
            (<%=ClwI18nUtil.getUIDateFormat(request)%>)
        </span>
    </td>
    <td>
        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="acquisitionDate">
            <bean:define id="acquisitionDate" name="USER_ASSET_PROFILE_MGR_FORM" property="acquisitionDate.value"
                         type="java.lang.String"/>
            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <nobr>
                    <html:text styleId="acquisitionDate" name="USER_ASSET_PROFILE_MGR_FORM" property="acquisitionDate.value"/>
                    <% if ("Y".equals(isMSIE)) { %>
                        <a href="#"
                           onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ACQUISITION_DATE, document.forms[0].acquisitionDate, null, -7300, 7300,null,-1);"
                           title="Choose Date"><img name="ACQUISITION_DATE" src="../externals/images/showCalendar.gif" width=19 height=19
                                                    border=0
                                                    style="position:relative"
                                                    onmouseover="window.status='Choose Date';return true"
                                                    onmouseout="window.status='';return true">
                        </a>
                    <% } else { %>
                        <a href="javascript:show_calendar('forms[0].acquisitionDate');"
                           onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;"
                           title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0>
                        </a>
                    <% } %>
                </nobr>
            </app:authorizedForFunction>
            <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">

                <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="acquisitionDate.value">
                    <bean:define id="acquisitionDate" name="USER_ASSET_PROFILE_MGR_FORM"
                                 property="acquisitionDate.value"
                                 type="java.lang.String"/>
                    <%if (Utility.isSet(acquisitionDate)) {%>
                    <span class="shopassetdetailtxt"> <%=
                        ClwI18nUtil.formatDate(request, acquisitionDate, DateFormat.DEFAULT)%>  </span>
                    <%}%>
                </logic:present>

            </app:notAuthorizedForFunction>
        </logic:present>
    </td>

    <td></td>
    <td>
           <span class="shopassetdetailtxt"><strong>
               <app:storeMessage key="userAssets.shop.text.param.acquisitionprice"/>
               : </strong></span></td>
    <td>
        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="acquisitionCost">

            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <html:text name="USER_ASSET_PROFILE_MGR_FORM" property="acquisitionCost.value"/>
            </app:authorizedForFunction>
            <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="acquisitionCost.value">
                    <bean:define id="acquisitionCost" name="USER_ASSET_PROFILE_MGR_FORM"
                                 property="acquisitionCost.value"
                                 type="java.lang.String"/>
                    <%if (Utility.isSet(acquisitionCost)) {%>
               <span class="shopassetdetailtxt">
              <%=ClwI18nUtil.getPriceShopping(request, acquisitionCost, "&nbsp;")%>
        </span> <%}%>
                </logic:present>
            </app:notAuthorizedForFunction>
        </logic:present>
    </td>
    <td></td>
</tr>
<tr>
    <td></td>
    <td><span class="shopassetdetailtxt"><strong>
        <app:storeMessage key="userAssets.shop.text.param.lastHMR"/>&nbsp;: </strong></span>
    </td>
    <td>
        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="lastHMR">
            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <html:text name="USER_ASSET_PROFILE_MGR_FORM" property="lastHMR.value"/>
            </app:authorizedForFunction>
            <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="lastHMR.value">
                    <span class="shopassetdetailtxt">
               <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="lastHMR.value"/>

        </span></logic:present>
            </app:notAuthorizedForFunction>
        </logic:present>
    </td>
    <td></td>

    <td>
        <span class="shopassetdetailtxt">
            <strong>
                <app:storeMessage key="userAssets.shop.text.param.dateLastHMR"/>:
            </strong><br>
            (<%=ClwI18nUtil.getUIDateFormat(request)%>)
        </span>
    </td>

    <td>
        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="dateLastHMR">
            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <nobr>
                    <html:text styleId="dateLastHMR" name="USER_ASSET_PROFILE_MGR_FORM" property="dateLastHMR.value"/>
                    <% if ("Y".equals(isMSIE)) { %>
                        <a href="#"
                           onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DATE_LHMR, document.forms[0].dateLastHMR, null, -7300, 7300,null,-1);"
                           title="Choose Date"><img name="DATE_LHMR" src="../externals/images/showCalendar.gif" width=19 height=19
                                                    border=0
                                                    style="position:relative"
                                                    onmouseover="window.status='Choose Date';return true"
                                                    onmouseout="window.status='';return true">
                        </a>
                    <% } else { %>
                        <a href="javascript:show_calendar('forms[0].dateLastHMR');"
                           onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;"
                           title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0>
                        </a>
                    <% } %>
                </nobr>
            </app:authorizedForFunction>
            <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="dateLastHMR.value">
                    <bean:define id="dateLastHMR" name="USER_ASSET_PROFILE_MGR_FORM" property="dateLastHMR.value"
                                 type="java.lang.String"/>
                    <%if (Utility.isSet(dateLastHMR)) {%><span class="shopassetdetailtxt">

               <%= ClwI18nUtil.formatDate(request, dateLastHMR, DateFormat.DEFAULT)%>

        </span> <%}%></logic:present>
            </app:notAuthorizedForFunction>
        </logic:present>
    </td>
    <td></td>
</tr>
<tr>
    <td></td>
    <td>
        <span class="shopassetdetailtxt">
            <strong>
                <app:storeMessage key="userAssets.shop.text.param.dateInService"/>:
            </strong><br>
            (<%=ClwI18nUtil.getUIDateFormat(request)%>)
        </span>
    </td>
    <td>
        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="dateInService">
            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <nobr>
                    <html:text styleId="dateInService" name="USER_ASSET_PROFILE_MGR_FORM" property="dateInService.value"/>
                    <% if ("Y".equals(isMSIE)) { %>
                        <a href="#"
                           onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DATE_IM_SERVICE, document.forms[0].dateInService, null, -7300, 7300,null,-1);"
                           title="Choose Date"><img name="DATE_IM_SERVICE" src="../externals/images/showCalendar.gif" width=19 height=19
                                                    border=0
                                                    style="position:relative"
                                                    onmouseover="window.status='Choose Date';return true"
                                                    onmouseout="window.status='';return true">
                        </a>
                    <% } else { %>
                        <a href="javascript:show_calendar('forms[0].dateInService');"
                           onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;"
                           title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0>
                        </a>
                    <% } %>
                </nobr>
            </app:authorizedForFunction>
            <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="dateInService.value">
                    <bean:define id="dateInService" name="USER_ASSET_PROFILE_MGR_FORM" property="dateInService.value"
                                 type="java.lang.String"/>
                    <%if (Utility.isSet(dateInService)) {%>
           <span class="shopassetdetailtxt">
            <%= ClwI18nUtil.formatDate(request, dateInService, DateFormat.DEFAULT)%>
        </span> <%}%></logic:present>
            </app:notAuthorizedForFunction>
        </logic:present>
    </td>
    <td></td>
    <td><span class="shopassetdetailtxt"><strong>
        <app:storeMessage key="userAssets.shop.text.param.status"/>
        : </strong></span>
    </td>
    <td>
        <bean:define id="statusCd" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.statusCd"
                     type="java.lang.String"/>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:select onchange="changeAssetStatus(this)" name="USER_ASSET_PROFILE_MGR_FORM"
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
    <td></td>
</tr>

<%
    String visibleInactReasonStatus = "hidden";
    if (RefCodeNames.ASSET_STATUS_CD.INACTIVE.equals(statusCd)) {
        visibleInactReasonStatus = "visible";
    }
%>
<tr id="inactiveReasonTR" style="visibility:<%=visibleInactReasonStatus%>">
    <td colspan="4"></td>
    <td>
        <span class="shopassetdetailtxt">
            <strong>
                <app:storeMessage key="userAssets.shop.text.param.inactiveStatusReason"/> :
            </strong>
        </span>
    </td>
    <td>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:text styleId="inactiveReasonVal" name="USER_ASSET_PROFILE_MGR_FORM" property="inactiveReason.value"/>
        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="inactiveReason.value">
                <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="inactiveReason.value"/>
            </logic:present>
        </app:notAuthorizedForFunction>
    </td>
    <td></td>
</tr>

<logic:greaterThan name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetId" value="0">

<tr><td colspan="4"></td>
    <td colspan="2">
        <span class="shopassetdetailtxt">
            <strong>
              <app:storeMessage key="userAssets.shop.text.param.woTotalCost"/>&nbsp;:
            </strong>
        </span>
         <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="workOrderTotalCost">
           <%=ClwI18nUtil.getPriceShopping(request,((UserAssetProfileMgrForm)theForm).getWorkOrderTotalCost())%>
         </logic:present>
      </td>
    <td></td>
</tr>

<tr><td colspan="7"></td></tr>
<tr>
    <td></td>
    <td colspan="5">


        <div id="locationMainBody">
            <tr align="center">


                    <%

                        String streetAddress = "";
                        String city          = "";
                        String postalCd      = "";
                        String state         = "";

                        String streetAddressMess = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userAssets.shop.text.param.streetAddress"));
                        String cityMess          = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userAssets.shop.text.param.city"));
                        String stateMess         = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userAssets.shop.text.param.stateProvince"));
                        String postalCdMess      = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userAssets.shop.text.param.postalCode"));
                        String headerMessage     =  Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"global.label.location"));

                       String controlFunction ="launchlocatebox()";
                       String controlActionVal= Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"global.label.location"));
                       Boolean controlRender = Boolean.FALSE;


                    %>

                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetTypeCd"
                             value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                    <%controlRender = Boolean.TRUE; %>
                </logic:equal>
                </app:authorizedForFunction>

                <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetLocationData">
                    <%

                        streetAddress = ((UserAssetProfileMgrForm) (theForm)).getAssetLocationData().getAddress1();
                        city          = ((UserAssetProfileMgrForm) (theForm)).getAssetLocationData().getCity();
                        postalCd      = ((UserAssetProfileMgrForm) (theForm)).getAssetLocationData().getPostalCode();
                        if (isStateProvinceRequired) {
                            state         = ((UserAssetProfileMgrForm) (theForm)).getAssetLocationData().getStateProvinceCd();
                        } else {
                            state         = ((UserAssetProfileMgrForm) (theForm)).getAssetLocationData().getCountyCd();
                        }

                    %>
                </logic:present>
                <script language="JavaScript1.2"><!--

                function locationInit(headerMessage,streetAdressMess,cityMess,stateMess,postalCdMess,
                                      streetAdress,city,state,postalCd,
                                      controlValue,controlFunction,contrlRender) {

                  locationDynamicBox.init(headerMessage,streetAdressMess,cityMess,stateMess, postalCdMess,
                          streetAdress,city,state,postalCd,
                          controlValue,controlFunction,contrlRender);

                    locationDynamicBox.initWriter('locationMainBody');
                    locationDynamicBox.write();
                }

                locationInit('<%=headerMessage%>','<%=streetAddressMess%>', '<%=cityMess%>', '<%=stateMess%>', '<%=postalCdMess%>',
                        '<%=streetAddress%>', '<%=city%>', '<%=state%>', '<%=postalCd%>',
                         '<%=controlActionVal%>', '<%=controlFunction%>','<%=controlRender.toString()%>');

                //-->
                </script>


    </td>
</tr>

<tr>
    <td colspan="7">&nbsp;</td>
</tr>
<tr>
    <td></td>
    <td colspan="5">
        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetWarrantyAssoc">
            <bean:size id="rescount" name="USER_ASSET_PROFILE_MGR_FORM" property="assetWarrantyAssoc"/>
            <TABLE width="100%" border="0" cellpadding="2" cellspacing=1>
                <TR><TD class=customerltbkground vAlign=top align=middle colspan="6"><SPAN class=shopassetdetailtxt>
              <B> <app:storeMessage key="userWarranty.text.toolbar.warranty"/></B> </SPAN>
                    <logic:greaterThan name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetId" value="0">
                    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetTypeCd"
                                 value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                        <html:button property="action" styleClass="store_fb" onclick="buttonLink('../userportal/userAssetProfile.do?action=configAssetWarranty');">
                            <app:storeMessage key="userAssets.text.assetConfigureWarranties"/>
                        </html:button>
                    </logic:equal>
                    </app:authorizedForFunction>
                    </logic:greaterThan>
                </TR>
                <tr class="tableheaderinfo">
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
                <logic:iterate id="arrele" name="USER_ASSET_PROFILE_MGR_FORM" property="assetWarrantyAssoc" indexId="i" type="com.cleanwise.service.api.value.WarrantyData">
                    <tr>
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
                                <html:img border="0" src="<%=IMGPath+\"/expAlert.jpg\"%>"/>
                            <% } %>
                        </td>
                    </tr>
                </logic:iterate>
            </table>
        </logic:present>
    </td>
    <td></td>
</tr>
<tr>
    <td colspan="7">&nbsp;</td>
</tr>
<tr>
    <td></td>
    <td colspan="5">
        <TABLE width="100%" border="0" cellpadding="2" cellspacing=1>
            <TR><TD class=customerltbkground vAlign=top align=middle colspan="4"><SPAN class=shopassetdetailtxt>
              <B><app:storeMessage key="userWarranty.text.assocDocs"/></B></SPAN>
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetTypeCd"
                             value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                <html:submit property="action" styleClass="store_fb">
                    <app:storeMessage key="global.label.addContent"/>
                </html:submit>
                </logic:equal>
                </app:authorizedForFunction>
            </TR>

            <tr>
                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.assocDocs.description"/>
                        </div>
                    </td>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.assocDocs.fileName"/>
                        </div>
                    </td>
                </app:authorizedForFunction>
                <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.assocDocs.description"/>
                        </div>
                    </td>
                </app:notAuthorizedForFunction>
                <td class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.assocDocs.addDate"/>
                    </div>
                </td>

                <td class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.assocDocs.addBy"/>
                    </div>
                </td>
            </tr>
            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="contents">
                <logic:iterate id="contentV" name="USER_ASSET_PROFILE_MGR_FORM" property="contents"
                               type="com.cleanwise.service.api.value.AssetContentView" indexId="j">
                    <logic:present name="contentV" property="content">
                        <logic:present name="contentV" property="assetContentData">
                            <bean:define id="acId" name="contentV" property="assetContentData.assetContentId"/>
                            <%String loc = "";%>
                            <tr id="docs<%=((Integer) j).intValue()%>">
                            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                                <td>
                                    <logic:present name="contentV" property="content.shortDesc">
                                        <a href="../userportal/userAssetContent.do?action=detail&assetContentId=<%=acId%>&display=t_userAssetContentDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                            <bean:write name="contentV" property="content.shortDesc"/>
                                        </a>
                                    </logic:present>

                                </td>
                                <td><%
                                    String fileName = "";
                                    if (contentV.getContent().getPath() != null) {
                                        fileName = contentV.getContent().getPath();
                                        if (fileName.indexOf("/") > -1) {
                                            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                                        }
                                    }
                                   %>
                                    <% loc = "../userportal/userAssetContent.do?action=readDocs&assetContentId=" + acId+"&display=t_userAssetContentDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar";%>
                                    <a href="#" onclick="viewPrinterFriendly('<%=loc%>');"><%=fileName%>
                                    </a>
                                </td>
                            </app:authorizedForFunction>
                            <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                                <td>
                                <%loc = "../userportal/userAssetContent.do?action=readDocs&assetContentId=" + acId+"&display=t_userAssetContentDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar";%>
                                    <a href="#" onclick="viewPrinterFriendly('<%=loc%>');"><bean:write name="contentV" property="content.shortDesc"/></a>
                                </td>
                            </app:notAuthorizedForFunction>
                            <td>
                                <logic:present name="contentV" property="content.addDate">
                                    <%= ClwI18nUtil.formatDate(request, contentV.getContent().getAddDate(), DateFormat.DEFAULT)%>
                                </logic:present>
                            </td>
                            <td>
                                <logic:present name="contentV" property="content.addBy">
                                    <bean:write name="contentV" property="content.addBy"/>
                                </logic:present>
                            </td>
                        </logic:present>
                    </logic:present>
                    </tr>
                </logic:iterate>
            </logic:present>
        </table>
    </td>
    <td></td>
</tr>
<tr>
    <td colspan="7">&nbsp;</td>
</tr>
<tr>
    <td></td>
    <td colspan="5">
        <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetWorkOrderAssoc">
            <bean:size id="rescount" name="USER_ASSET_PROFILE_MGR_FORM" property="assetWorkOrderAssoc"/>
            <TABLE width="100%" border="0" cellpadding="2" cellspacing=1>
                <TR>
                    <TD class=customerltbkground vAlign=top align=middle colspan="5">

                        <SPAN class=shopassetdetailtxt>
                            <B><app:storeMessage key="userWorkOrder.text.toolbar"/></B>
                        </SPAN>

                        <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetTypeCd"
                                     value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                        <html:button property="action" styleClass="store_fb"
                                     onclick="actionSubmit(0,'createNewWorkOrder');">
                            <app:storeMessage key="userAssets.shop.button.createWorkOrder"/>
                        </html:button>
                        </logic:equal>
                </TR>

                <tr class="tableheaderinfo">
                    <td width="20%" class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWorkOrder.text.workOrderNumber"/>
                        </div>
                    </td>

                    <td width="20%" class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWorkOrder.text.workOrderName"/>
                        </div>
                    </td>
                    <td width="20%" class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWorkOrder.text.type"/>
                        </div>
                    </td>
                    <td width="20%" class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userAssets.shop.text.param.wo.completionDate"/>
                        </div>
                    </td>
                    <td width="20%" class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userAssets.shop.text.param.wo.totalCost"/>
                        </div>
                    </td>

                </tr>

                <logic:iterate id="arrele" name="USER_ASSET_PROFILE_MGR_FORM" property="assetWorkOrderAssoc"
                               indexId="i">
                    <bean:define id="eleid" name="arrele" property="workOrderId" type="java.lang.Integer"/>
                    <tr>
                        <td align="center">
                            <bean:write name="arrele" property="workOrderId"/>
                        </td>
                        <td align="center">
                            <logic:present name="arrele" property="shortDesc">
                                <a href="../userportal/userWorkOrderDetail.do?action=detail&workOrderId=<%=eleid%>&display=t_userWorkOrderDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                    <bean:write name="arrele" property="shortDesc"/>
                                </a>
                            </logic:present>
                        </td>
                        <td align="center">
                            <logic:present name="arrele" property="typeCd">
                                <bean:write name="arrele" property="typeCd"/>
                            </logic:present>
                        </td>
                        <td align="center">
                            <logic:present name="arrele" property="actualFinishDate">
                                <bean:define id="actualFinishDate" name="arrele" property="actualFinishDate" type="java.util.Date"/>
                                <%=ClwI18nUtil.formatDate(request,actualFinishDate,DateFormat.DEFAULT)%>
                            </logic:present>
                        </td>
                        <td align="center">
                            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="workOrderActualCostMap">
                                <bean:define id="costMap" name="USER_ASSET_PROFILE_MGR_FORM" property="workOrderActualCostMap" type="java.util.HashMap"/>
                                <%BigDecimal cost = (BigDecimal)costMap.get(eleid);
                                    if(cost!=null){%>
                                <%=ClwI18nUtil.getPriceShopping(request,cost)%>
                                <%} else {%>-<%}%>
                            </logic:present>
                        </td>
                    </tr>
                </logic:iterate>
            </table>
        </logic:present>

    </td>
    <td></td>
</tr>
</logic:greaterThan>
<tr>
    <td colspan="7">&nbsp;</td>
</tr>
<tr>
    <td colspan="7">&nbsp;</td>
</tr>

</logic:equal>


<tr>
    <td colspan="7" align="center">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:submit property="action" styleClass="store_fb">
                <app:storeMessage key="global.action.label.save"/>
            </html:submit>
        </app:authorizedForFunction>
    </td>
</tr>
<html:hidden property="tabs" value="f_userAssetToolbar"/>
<html:hidden property="display" value="t_userAssetProfile"/>
<html:hidden property="secondaryToolbar" value="f_userSecondaryToolbar"/>
<html:hidden property="action" value="hiddenAction"/>
</html:form>
</table>
</td>

</tr>

</table>


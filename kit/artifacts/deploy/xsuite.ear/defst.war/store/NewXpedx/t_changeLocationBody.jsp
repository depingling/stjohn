<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.forms.SiteMgrSearchForm" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<style type="text/css">
    .siteSearchHead {
        color: #000000;
		font-weight: bold;
		background-color: #000000;
    }

	A.siteSearchHeadlink:link, A.siteSearchHeadlink:visited, A.siteSearchHeadlink:active {
		color: #ffffff;
		font-weight: bold;
		background-color: #000000;
	}

	A.siteSearchHeadlink:hover {
		background-color: #ffffff;
		font-weight: bold;
		color: #000000;
	}


</style>
<script language="JavaScript" src="../externals/ajaxutil.js"></script>
<script language="JavaScript" src="../externals/jsutil/changeLocationUtil.js"></script>

<script language="JavaScript1.2">
 <!--
 function actionMultiSubmit(actionDef, action) {

        var actionElements = document.getElementsByName('action');
        if(actionElements.length){
            for ( var i = actionElements.length-1; i >=0; i-- ) {

                var element = actionElements[i];
                if(actionDef == element.value){
                    element.value = action;
                    document.forms[0].submit();

                    break;
                }
            }
        }  else if(actionElements){
            actionElements.value = action;
            document.forms[0].submit();
        }

        return false;
    }

-->
</script>

<bean:define id="theForm" name="SITE_SEARCH_FORM" type="com.cleanwise.view.forms.SiteMgrSearchForm"/>
<%

String siteSelectObjectId      ="clb_site";
String stateSelectObjectId   ="clb_state";
String countrySelectObjectId ="clb_contry";

CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
StoreData userStore = appUser.getUserStore();
%>
<script language="JavaScript1.2"><!--

var siteArray = new Array();
var siteIdx = 0;

<%
if(((SiteMgrSearchForm)theForm).getSiteValuePairs()!=null
    && !(((SiteMgrSearchForm)theForm).getSiteValuePairs()).isEmpty()) {
  %>
siteArray = new Array();
<% for(int i=0;i<((SiteMgrSearchForm)theForm).getSiteValuePairs().size();i++){%>
siteArray[siteIdx] = new Array();
siteArray[siteIdx][0] = '<%=((PairView)(((SiteMgrSearchForm)theForm).getSiteValuePairs()).get(i)).getObject1().toString()%>';
siteArray[siteIdx][1] = '<%=Utility.replaceString(((PairView)(((SiteMgrSearchForm)theForm).getSiteValuePairs()).get(i)).getObject2().toString(),"'","\\'")%>';
siteIdx++;
<%}%>
<%
}
%>

function siteInit(array, currentValue, siteEmptyMessage, selectElementName) {
    clSiteListDynamicBox.init(array, currentValue, siteEmptyMessage, selectElementName);
}

//-->
</script>

<script language="JavaScript1.2"><!--

var countryArray;
var countryIdx = 0;

<%if(((SiteMgrSearchForm)theForm).getCountryValueList()!=null
&& !(((SiteMgrSearchForm)theForm).getCountryValueList()).isEmpty()) {  %>
countryArray = new Array();
<% for(int i=0;i<((SiteMgrSearchForm)theForm).getCountryValueList().size();i++){%>
countryArray[countryIdx] = new Array();

        <%
        String countryStr = (String)(((SiteMgrSearchForm)theForm).getCountryValueList()).get(i);
        String countryStrTrunc;
        if(countryStr.indexOf("'")>0){ 		//escape single quotes
        	countryStr = countryStr.replace("'", "\\'");
        }
        countryStrTrunc = countryStr;
        if(countryStr.length() > 25){
        	if(countryStr.indexOf(",") > 0){
        		countryStrTrunc = countryStr.substring(0,countryStr.indexOf(","));
        	}
        }
        %>
countryArray[countryIdx][0] = '<%=countryStr%>';
countryArray[countryIdx][1] = '<%=countryStrTrunc%>';
countryIdx++;
<%}%>
<%}%>

function countryInit(array, currentValue, countryEmptyMessage, selectElementName) {
    clCountryListDynamicBox.init(array, currentValue, countryEmptyMessage, selectElementName);
}

//-->
</script>

<% if (appUser.getUserStore().isStateProvinceRequired()) { %>

<script language="JavaScript1.2"><!--

var stateArray;
var stateIdx = 0;

<%if(((SiteMgrSearchForm)theForm).getStateValueList()!=null
&& !(((SiteMgrSearchForm)theForm).getStateValueList()).isEmpty()) {  %>
stateArray = new Array();
<% for(int i=0;i<((SiteMgrSearchForm)theForm).getStateValueList().size();i++){%>
stateArray[stateIdx] = new Array();
stateArray[stateIdx][0] = '<%=(String)(((SiteMgrSearchForm)theForm).getStateValueList()).get(i)%>';
stateArray[stateIdx][1] = '<%=(String)(((SiteMgrSearchForm)theForm).getStateValueList()).get(i)%>';
stateIdx++;
<%}%>
<%}%>

function stateInit(array, currentValue, stateEmptyMessage, selectElementName) {
    clStateListDynamicBox.init(array, currentValue, stateEmptyMessage, selectElementName);
}

//-->
</script>

<%}%>

<script language="JavaScript1.2"><!--
addLoadEvent(stateInitChangeLoc);

function stateInitChangeLoc() {
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
    stateInit(stateArray, "<%=Utility.strNN(((SiteMgrSearchForm)theForm).getState())%>", "<%=ClwI18nUtil.getMessage(request,"shop.orderStatus.label.selectaState",null)%>", "<%=stateSelectObjectId%>");
	siteInit(siteArray, "<%=Utility.strNN(((SiteMgrSearchForm)theForm).getSiteId())%>", "<%=ClwI18nUtil.getMessage(request,"shop.orderStatus.label.selectaStore",null)%>", "<%=siteSelectObjectId%>");
<%}%>
    countryInit(countryArray, "<%=Utility.strNN(((SiteMgrSearchForm)theForm).getCountry())%>", "<%=ClwI18nUtil.getMessage(request,"shop.orderStatus.label.selectaCountry",null)%>", "<%=countrySelectObjectId%>");
    siteInit(siteArray, "<%=Utility.strNN(((SiteMgrSearchForm)theForm).getSiteId())%>", "<%=ClwI18nUtil.getMessage(request,"shop.orderStatus.label.selectaStore",null)%>", "<%=siteSelectObjectId%>");
}
//--></script>



<%--bread crumb --%>
<table class="breadcrumb">
<tr class="breadcrumb">
<td class="breadcrumb">
	<a class="breadcrumb" href="../userportal/msbsites.do">
	<app:storeMessage key="global.label.Home"/></a>
</td>
<td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
<td class="breadcrumb">
    <%--<a class="breadcrumb" href="#"><app:storeMessage key="shop.header.text.changeLocation"/></a>--%>
	<div class="breadcrumbCurrent"><app:storeMessage key="shop.header.text.changeLocation"/></div>
</td>
</tr>
</table>
<br>



<table border="0" cellpadding="0" cellspacing="0" align="center" width="<%=Constants.TABLEWIDTH800%>">
<tr>
    <td>&nbsp;<b><app:storeMessage key="global.action.label.search"/></b></td>
    <td>&nbsp;<b><app:storeMessage key="global.label.quickSelect"/></b></td>
</tr>
<tr>
<html:form name="SITE_SEARCH_FORM"
           action="/userportal/msbsites.do"
           scope="session"
           type="com.cleanwise.view.forms.SiteMgrSearchForm">
<td>
    <table height="100%" border="0" cellspacing="0" cellpadding="0">

        <tr  bgcolor="#ECECEC">
            <td><img src='<%=ClwCustomizer.getSIP(request,"topL.png")%>'></td>
            <td style="border:1px solid #B5B5B5;border-bottom:0;border-left:0;border-right:0;">&nbsp;</td>
            <td><img src='<%=ClwCustomizer.getSIP(request,"topR.png")%>'></td>
        </tr>



        <tr bgcolor="#ECECEC">
            <td style="border:1px solid #B5B5B5;border-bottom:0;border-top:0;border-right:0;">&nbsp;</td>

            <td>
                    <%--<% if ( appUser != null && appUser.getSiteNumber() > 1 ) {  %>--%>

                <table  height="100%" bgcolor="#ECECEC" cellspacing="4" cellpadding="2">

                    <tr>

                        <td align="right"><app:storeMessage key="msbSites.text.siteName"/>:</td>
                        <td>
                            <html:text name="SITE_SEARCH_FORM" property="searchField" />
                        </td>

                        <td align="right"><app:storeMessage key="msbSites.text.country"/>:</td>
                        <td>
                            <html:text name="SITE_SEARCH_FORM" property="country" />
                        </td>

                    </tr>


                    <tr>
                        <% if (userStore.isStateProvinceRequired())  { %>
                        <td align="right"><app:storeMessage key="msbSites.text.stateProvince"/>:</td>
                        <td><html:text name="SITE_SEARCH_FORM" property="state" /></td>
                        <% } else { %>
                        <td colspan="2">&nbsp;</td>
                        <% } %>

                        <td align="right"><app:storeMessage key="msbSites.text.city"/>:</td>
                        <td><html:text name="SITE_SEARCH_FORM" property="city" /></td>
                        <td></td>

                    </tr>

                    <tr>
                        <td align="right"><app:storeMessage key="msbSites.text.zip"/>:</td>
                        <td><html:text name="SITE_SEARCH_FORM" property="postalCode" /></td>
                        <td></td>
                        <td align="right">
                            <table  border="0" cellspacing="0" cellpadding="0"><tr><td>
                                <app:xpedxButton label='global.action.label.search' url="#" onClick='actionMultiSubmit("hiddenAction", "only_site_search");'/>
                            </td></tr></table>
                         </td>
                    </tr>
                </table>
            </td>

            <td style="border:1px solid #B5B5B5;border-bottom:0;border-left:0;border-top:0;">&nbsp;</td>

        </tr>

            <%--	<% } %> --%>


        <tr  bgcolor="#ECECEC">
            <td><img src='<%=ClwCustomizer.getSIP(request,"botL.png")%>'></td>
            <td style="border:1px solid #B5B5B5;border-top:0;border-left:0;border-right:0;">&nbsp;</td>
            <td><img src='<%=ClwCustomizer.getSIP(request,"botR.png")%>'></td>
        </tr>


    </table>
</td>

<td height="100%">
    <table border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#ECECEC">

        <tr  bgcolor="#ECECEC">
            <td><img src='<%=ClwCustomizer.getSIP(request,"topL.png")%>'></td>
            <td style="border:1px solid #B5B5B5;border-bottom:0;border-left:0;border-right:0;">&nbsp;</td>
            <td><img src='<%=ClwCustomizer.getSIP(request,"topR.png")%>'></td>
        </tr>

        <tr valign="top">
            <td  height="100%" style="border:1px solid #B5B5B5;border-bottom:0;border-top:0;border-right:0;">&nbsp;</td>

            <td>
                <table  cellspacing="4" cellpadding="2" bgcolor="#ECECEC" width="212" height="100%">
                    <tr><td><select id ="<%=countrySelectObjectId%>" name="country" onchange="dojo.xhrGet({load:locationCriteria.update,url:'msbsites.do?action=changeCountry&newCountry='+this.value,handleAs:'json'});"></select></td></tr>
                    <% if (appUser.getUserStore().isStateProvinceRequired()) { %>
                    <tr><td><select id ="<%=stateSelectObjectId%>" name="state" onchange="dojo.xhrGet({load:locationCriteria.update,url:'msbsites.do?action=changeState&newState='+this.value,handleAs:'json'});"></select></td></tr>
                    <%}%>
                    <tr><td><select id ="<%=siteSelectObjectId%>" name="siteId" onchange="actionMultiSubmit('hiddenAction', 'changeSite');"></select></td></tr>
                </table>
            </td>

            <td  height="100%" style="border:1px solid #B5B5B5;border-bottom:0;border-left:0;border-top:0;">&nbsp;</td>

        </tr>

            <%--	<% } %> --%>

        <tr valign="top"  bgcolor="#ECECEC">
            <td valign="bottom"><img src='<%=ClwCustomizer.getSIP(request,"botL.png")%>'></td>
            <td valign="bottom" style="border:1px solid #B5B5B5;border-top:0;border-left:0;border-right:0;">&nbsp;</td>
            <td valign="bottom"><img src='<%=ClwCustomizer.getSIP(request,"botR.png")%>'></td>
        </tr>


    </table>
</td>

<html:hidden property="action" value="hiddenAction"/>
<html:hidden property="source" value="t_changeLocationBody.jsp"/>
</html:form>
</tr></table>
<br><br>

<logic:present name="msb.site.vector">
<bean:size id="rescount"  name="msb.site.vector"/>

<% SiteViewVector svv = (SiteViewVector)session.getAttribute("msb.site.vector");

if(svv.size() == 0){%>
<table border="0" align=center CELLSPACING=0 CELLPADDING=5
  width="100%">
<tr><td align="center">
<font color=#003399>
<b><app:storeMessage key="msbSites.text.noResult"/></b></font>
</td></tr></table>
<% } else { %>

<table border="0" align=center CELLSPACING=0 CELLPADDING=5
  width="100%"
  style="{border-bottom: black 2px solid;}" >
<tr align=left >
<td class="itemRowFirst siteSearchHead"><div class="fivemargin">
<a class="siteSearchHeadlink" href="msbsites.do?action=x_sort_sites&sortField=name"><app:storeMessage key="msbSites.text.location"/></a></div></td>
<td class="siteSearchHead"><div class="fivemargin">
<a class="siteSearchHeadlink" href="msbsites.do?action=x_sort_sites&sortField=address"><app:storeMessage key="msbSites.text.streetAddress"/></a></div></td>
<td class="siteSearchHead"><div class="fivemargin">
<a class="siteSearchHeadlink" href="msbsites.do?action=x_sort_sites&sortField=city"><app:storeMessage key="msbSites.text.city"/></a></div></td>
<% if (userStore.isStateProvinceRequired())  { %>
<td class="siteSearchHead"><div class="fivemargin">
<a class="siteSearchHeadlink" href="msbsites.do?action=x_sort_sites&sortField=state"><app:storeMessage key="msbSites.text.stateProvince"/></a></div></td>
<td class="siteSearchHead"><div class="fivemargin">
<a class="siteSearchHeadlink" href="msbsites.do?action=x_sort_sites&sortField=zipcode"><app:storeMessage key="msbSites.text.zip"/></a></div></td>
<%} %>
<td class="siteSearchHead"></td>

</tr>


<logic:iterate id="arrele" name="msb.site.vector" indexId="i">
<%String styleClass = (((i++) %2 )==0) ?  "evenRowColor" : "oddRowColor";%>
<tr class="<%=styleClass%>">
<td class="itemRowFirst itemRow">
<bean:define id="eleid" name="arrele" property="id"/>
<bean:write name="arrele" property="name"/>
</td>
<td class="itemRow">
<bean:write name="arrele" property="address"/>
</td>
<td class="itemRow">
<bean:write name="arrele" property="city"/>
</td>
<% if (userStore.isStateProvinceRequired())  { %>
<td class="itemRow">
  <bean:write name="arrele" property="state"/>
</td>
<td class="itemRow">
  <bean:write name="arrele" property="postalCode"/>
</td>

<%}%>
<td  class="itemRow">
    <table border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
                <%String link = "msbsites.do?action=shop_site2&siteId="+eleid;%>
                <app:xpedxButton label='global.action.label.select' url='<%=link%>'/>
            </td>
        </tr>
    </table>
</td>

</tr>

</logic:iterate>
</table>
<% } %>
</logic:present>



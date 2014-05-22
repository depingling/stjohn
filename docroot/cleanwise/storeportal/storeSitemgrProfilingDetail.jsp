<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.logic.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.taglibs.ProfilingSurveyTag" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="store" type="java.lang.String" toScope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Profiling</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admSiteToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>


<script src="../externals/surveyProfiling.js" language="javascript"></script>


<div class="text">
<font color=red>
<html:errors/>
</font>

<jsp:include flush='true' page="ui/siteInfo.jsp"/>

<table width="769" cellspacing="0" border="0"  class="mainbody">
<html:form name="SITE_DETAIL_FORM" action="storeportal/storeSitemgrProfilingDetail.do" type="com.cleanwise.view.forms.ProfilingMgrSurveyForm">
<tr><td colspan="6" align="center">&nbsp;</td></tr>
<tr>
        <td><b>Survey Id:</b></td>
        <td><bean:write name="SITE_DETAIL_FORM" property="profile.profileView.profileData.profileId"/></td>
        <td><b>Survey Name:</b></td>
        <td colspan="2"><bean:write name="SITE_DETAIL_FORM" property="profile.profileView.profileData.shortDesc"/></td>
        <td><b>Help Text:</b></td>
        <td colspan="2"><bean:write name="SITE_DETAIL_FORM" property="profile.profileView.profileData.helpText"/></td>
</tr>
<tr>
        <td><b>Last Mod By</td>
        <td>
                <logic:present name="SITE_DETAIL_FORM" property="profile.lastModifiedDetailData">
                        <bean:write name="SITE_DETAIL_FORM" property="profile.lastModifiedDetailData.modBy"/>
                </logic:present>
        </td>
        <td><b>Last Mod Date</td>
        <td>
          <logic:present name="SITE_DETAIL_FORM" property="profile.lastModifiedDetailData">
            <logic:present name="SITE_DETAIL_FORM" property="profile.lastModifiedDetailData.modDate">
                <bean:define id="theDate" name="SITE_DETAIL_FORM" property="profile.lastModifiedDetailData.modDate"/>
                <i18n:formatDate value="<%=theDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
            </logic:present>
          </logic:present>
        </td>
        <td>&nbsp;</td>
</tr>
<tr>
        <td colspan="6" align="center">
                <html:submit property="action">
                        <app:storeMessage  key="admin.button.submitUpdates"/>
                </html:submit>
        </td>
</tr>
<tr>
        <td colspan="6">
           <table>
                <tbody id="questions">
                <%
                String childPrefix = "<td colspan='10'><div><b>Q "+ProfilingSurveyTag.TOKEN_NUMBER_LOOP_VALUE +":</b>";
                String allPostfix = "</div></td></tr>";
                String helpTextPrefix = " <a href='javascript:void(0);' onClick='javaScript:alert(\"";
                String helpTextPostFix="\")'><b>(Help)</b></a>";

                String metaHelpPrefix = "<a href='javascript:void(0);' onClick='javaScript:popLocateGlobal(\"../general/textEdit\",\""+ProfilingSurveyTag.TOKEN_PATH_TO_PROFILE_META+"value\")'>(e)</a> <b>Help:</b>";
                String altChildElementPrefix = "<tr id='"+ProfilingSurveyTag.TOKEN_NUMBER_LOOP_VALUE +"' name='"+ProfilingSurveyTag.TOKEN_PATH_TO_PROFILE+"' style='display:none'>";
                String elementPrefix =         "<tr id='"+ProfilingSurveyTag.TOKEN_NUMBER_LOOP_VALUE +"' name='"+ProfilingSurveyTag.TOKEN_PATH_TO_PROFILE+"' style='display:'>";
                String formSelectElementParentOnlyJavascript = "onChange=\"showMyImediateChildren(this,Array("+ProfilingSurveyTag.TOKEN_CORRECT_VALUES+"),Array("+ProfilingSurveyTag.TOKEN_OTHER_VALUES+"))\"";
                //String elementPrefix = "<tr>";
                %>
                <app:ProfilingSurvey form="SITE_DETAIL_FORM" name="profile" renderHiddenNumberDependants="10"
                        tokenize="true" skipFirst="true"  elementPostfix="<%=allPostfix%>" elementPrefix="<%=elementPrefix%>"
                        altChildElementPrefix="<%=altChildElementPrefix%>"
                        childSeperator="<td>&nbsp;</td>" childPrefix="<%=childPrefix%>" childPostfix=""
                        profileOrderPrefix=" <b>Order:</b>" formElementParentOnlyJavascript="onKeyUp=showMyImediateChildren(this)"
                        formSelectElementParentOnlyJavascript="<%=formSelectElementParentOnlyJavascript%>"
                        helpTextPrefix="<%=helpTextPrefix%>" helpTextPostFix="<%=helpTextPostFix%>"
                        profileStatusCdPrefix=" <b>Status:</b>" readOnlyPrefix="<b> Read Only:</b>"
                        profileMetaPrefix="<br><b>Answer:</b>" metaHelpPrefix="<%=metaHelpPrefix%>"
                        errorMessgePrefix="<font color=red>" errorMessgePostfix="</font>"
                        otherStyle="display:none" useClone="false"
                        admin="false" readOnly="false"
                        />
                </tbody>
           </table>
        </td>
</tr>
<tr>
        <td colspan="8" align="center">
                <html:submit property="action">
                        <app:storeMessage  key="admin.button.submitUpdates"/>
                </html:submit>
        </td>
</tr>
</html:form>
</table>

</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>

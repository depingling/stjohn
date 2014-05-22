
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.taglibs.ProfilingSurveyTag" %>
<%@ page import="com.cleanwise.view.utils.*"%>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script src="../externals/surveyProfiling.js" language="javascript"></script>

<%
    String browser = request.getHeader("User-Agent");
    String isMSIE = "";
    if (browser != null && browser.indexOf("MSIE") >= 0) {
        isMSIE = "Y";
%>
    <script language="JavaScript" src="../externals/calendar.js"></script>
    <iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
       marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html">
    </iframe>
<%
} else {
%>
    <script language="JavaScript" src="../externals/calendarNS.js"></script>
<%
    }
%>


<bean:define id="IMGPath" type="java.lang.String"
  name="pages.store.images"/>

<div class="text" >

<font color=red>
<!-- <html:errors/> -->
</font>

</div>

<html:form name="CUSTOMER_PROFILING_FORM" action="userportal/customerAccountManagementSurveyDetail.do" type="com.cleanwise.view.forms.CustomerProfilingForm" >

<table cellSpacing=5 cellPadding=2 align=center border=0>

<tr>
        <td><b>Survey Name:</b></td>
        <td>
                <bean:write name="CUSTOMER_PROFILING_FORM" property="profile.profileView.profileData.shortDesc"/>
        </td>
        <td><b>Last Mod By:</b></td>
        <td>
                <logic:present name="CUSTOMER_PROFILING_FORM" property="profile.lastModifiedDetailData">
                        <bean:write name="CUSTOMER_PROFILING_FORM" property="profile.lastModifiedDetailData.modBy"/>
                </logic:present>
        </td>
        <td><b>Last Mod Date:</b></td>
        <td>
          <logic:present name="CUSTOMER_PROFILING_FORM" property="profile.lastModifiedDetailData">
            <logic:present name="CUSTOMER_PROFILING_FORM" property="profile.lastModifiedDetailData.modDate">
                <bean:define id="theDate" name="CUSTOMER_PROFILING_FORM" property="profile.lastModifiedDetailData.modDate"/>
                <i18n:formatDate value="<%=theDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
            </logic:present>
          </logic:present>
        </td>
</tr>
<tr>

<%String readOnly = "true";%>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.EDIT_PROFILING%>">
        <%readOnly = "false";%>
</app:authorizedForFunction>

<td colspan="6">



<table>
<tbody id="questions">
<%
String childPrefix = "<td colspan='10'><div><b>Q "
  +ProfilingSurveyTag.TOKEN_QUESTION_IDX + "."
  +ProfilingSurveyTag.TOKEN_NUMBER_LOOP_VALUE
  +":</b>";
String allPostfix = "</div></td></tr>";
String helpTextPrefix = " <a href='javascript:void(0);' onClick='javaScript:alert(\"";
String helpTextPostFix="\")'><b>(Help)</b></a>";

String metaHelpPrefix = "<a href='javascript:void(0);' onClick='javaScript:popLocateGlobal(\"../general/textEdit\",\""+ProfilingSurveyTag.TOKEN_PATH_TO_PROFILE_META+"value\")'>(e)</a> <b>Help:</b>";
String altChildElementPrefix = "<tr id='"+ProfilingSurveyTag.TOKEN_NUMBER_LOOP_VALUE +"' name='"+ProfilingSurveyTag.TOKEN_PATH_TO_PROFILE+"' style='display:none'>";
String elementPrefix =         "<tr id='"+ProfilingSurveyTag.TOKEN_NUMBER_LOOP_VALUE +"' name='"+ProfilingSurveyTag.TOKEN_PATH_TO_PROFILE+"' style='display:'>";
String formSelectElementParentOnlyJavascript = "onChange=\"showMyImediateChildren(this,Array("+ProfilingSurveyTag.TOKEN_CORRECT_VALUES+"),Array("+ProfilingSurveyTag.TOKEN_OTHER_VALUES+"))\"";
//String elementPrefix = "<tr>";

String datePromptText = "&nbsp;(" + ClwI18nUtil.getUIDateFormat(request) + ")";
Locale userLocale = null;
CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
if ( null != appUser ) {
   userLocale = appUser.getPrefLocale();
}
if (userLocale != null && userLocale.equals(Locale.US)) {
  datePromptText += "&nbsp;<a href='#' onClick='event.cancelBubble=true; return ShowCalendar(document.CUSTOMER_PROFILING_FORM.DEFSADD1, document.getElementById(\"formElementId\"), null, -7300, 7300, null, 0);' title='Choose Date'>" +
  "<img name='DEFSADD1' src='../externals/images/showCalendar.gif' width='19' height='19' border='0' align='middle' style='position:relative' onmouseover='window.status=\"Choose Date\";return true' onmouseout='window.status=\"\";return true'>" +
  "</a>";
}

%>



<app:ProfilingSurvey form="CUSTOMER_PROFILING_FORM" name="profile"
  renderHiddenNumberDependants="20"
  tokenize="true"
  skipFirst="true"
  elementPostfix="<%=allPostfix%>"
  elementPrefix="<%=elementPrefix%>"
  altChildElementPrefix="<%=altChildElementPrefix%>"
  childSeperator="<td>&nbsp;</td>"
  childPrefix="<%=childPrefix%>"
  childPostfix=""
  profileOrderPrefix=" <b>Order:</b>"
  formElementParentOnlyJavascript="onKeyUp=showMyImediateChildren(this)"
  formSelectElementParentOnlyJavascript="<%=formSelectElementParentOnlyJavascript%>"
  helpTextPrefix="<%=helpTextPrefix%>" helpTextPostFix="<%=helpTextPostFix%>"
  profileStatusCdPrefix=" <b>Status:</b>" readOnlyPrefix="<b> Read Only:</b>"
  profileMetaPrefix="<br><b>Answer:</b>" metaHelpPrefix="<%=metaHelpPrefix%>"
  errorMessgePrefix="<font color=red>" errorMessgePostfix="</font>"
  otherStyle="display:none" useClone="false"
  admin="false" readOnly="<%=readOnly%>" debug="false"
  datePromptText="<%=datePromptText%>"
/>


                </tbody>

           </table>

</td>
</tr>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.EDIT_PROFILING%>">
<tr>
        <td colspan="8" align="center">
                <html:submit property="action">
                        <app:storeMessage  key="admin.button.submitUpdates"/>
                </html:submit>
        </td>
</tr>
</app:authorizedForFunction>
</table>

</html:form>

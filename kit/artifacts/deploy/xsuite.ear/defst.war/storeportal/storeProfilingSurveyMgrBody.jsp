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

<script src="../externals/surveyProfiling.js" language="javascript"></script>

<div class="text">
<font color=red>
<html:errors/>
</font>

<table cellspacing="0" border="0"  class="mainbody">
<html:form name="STORE_PROFILING_MGR_FORM" action="storeportal/storeProfilingSurveyMgr.do" type="com.cleanwise.view.forms.StoreProfilingMgrSurveyForm" enctype="multipart/form-data">
<tr>
        <td><b>Survey Id:</b></td>
        <td><bean:write name="STORE_PROFILING_MGR_FORM" property="profile.profileView.profileData.profileId"/></td>
        <td><b>Survey Name:</b></td>
        <td colspan="2"><html:text name="STORE_PROFILING_MGR_FORM" property="profile.profileView.profileData.shortDesc" size="30"/></td>
        <td><b>Help Text:</b></td>
        <td colspan="2"><html:text name="STORE_PROFILING_MGR_FORM" property="profile.profileView.profileData.helpText" size="30"/></td>
</tr>
<tr>
        <td><b>Add By:</b></td><td><bean:write name="STORE_PROFILING_MGR_FORM" property="profile.profileView.profileData.addBy"/></td>
        <td><b>Add Date:</b></td>
        <td>
            <logic:present name="STORE_PROFILING_MGR_FORM" property="profile.profileView.profileData.addDate">
                <bean:define id="addDate" name="STORE_PROFILING_MGR_FORM" property="profile.profileView.profileData.addDate"/>
                <i18n:formatDate value="<%=addDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
            </logic:present>
        </td>
        <td><b>Mod By:</b></td><td><bean:write name="STORE_PROFILING_MGR_FORM" property="profile.profileView.profileData.modBy"/></td>
        <td><b>Mod Date:</b></td>
        <td>
            <logic:present name="STORE_PROFILING_MGR_FORM" property="profile.profileView.profileData.modDate">
                <bean:define id="modDate" name="STORE_PROFILING_MGR_FORM" property="profile.profileView.profileData.modDate"/>
                <i18n:formatDate value="<%=modDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
            </logic:present>
        </td>
</tr>
<tr>
        <td colspan="8" align="center">
                <html:submit property="action">
                        <app:storeMessage  key="admin.button.submitUpdates"/>
                </html:submit>
                <html:button property="action"
                onclick="popWindow('storeProfilingSurveyMgrPreview.do?action=preview','profiling');"
                value="Preview" />
        </td>
</tr>
<tr>
        <td colspan="8">
                *Note: use the text "TOK_NUM_LOOP_VAL" in any question to indicate the current iteration for numbered question is being rendered.
        </td>
</tr>
<tr>
        <td colspan="8">
           <table border=1>
                <tbody id="questions">
                <tr><td colspan="10"><a href='javascript:void(0);' onClick='javascript:addSubEntity(this,1)'>Add Child Question</a></td></tr>
                <%
                String childPrefix = "<td valign=top colspan='10'><br><div><b>Q:</b>";
                String allPostfix = "<br><a href='javascript:void(0);' name='"+ProfilingSurveyTag.TOKEN_PATH_TO_PROFILE+"' onClick='javascript:addSubEntity(this,1)'>Add Child Question</a>" +
                        "&nbsp;<a href='javascript:void(0);' name='"+ProfilingSurveyTag.TOKEN_PATH_TO_PROFILE+"' onClick='javascript:addMetaData(this,1)'>Add Answer</a></div></td></tr>";
                String helpTextPrefix = "<br><b>Help:</b>";
                String profileOrderPrefix = "<br><a href='javascript:void(0);' onClick='javaScript:popLocateGlobal(\"../general/textEdit\",\""+ProfilingSurveyTag.TOKEN_PATH_TO_PROFILE+"profileView.profileData.shortDesc\")'>(e)</a><br> <b>Order:</b>";
                String profileQuestionTypeCdPrefix = "<a href='javascript:void(0);' onClick='javaScript:popLocateGlobal(\"../general/textEdit\",\""+ProfilingSurveyTag.TOKEN_PATH_TO_PROFILE+"profileData.helpText\")'>(e)</a> <br><b>Type:</b>";

                String metaHelpPrefix = "<a href='javascript:void(0);' onClick='javaScript:popLocateGlobal(\"../general/textEdit\",\""+ProfilingSurveyTag.TOKEN_PATH_TO_PROFILE_META+"value\")'>(e)</a> <b>Help:</b>";
                String metaTypeCdPrefix = "<a href='javascript:void(0);' onClick='javaScript:popLocateGlobal(\"../general/textEdit\",\""+ProfilingSurveyTag.TOKEN_PATH_TO_PROFILE_META+"helpText\")'>(e)</a> <b>Type:</b>";
                %>
               <app:ProfilingSurvey form="STORE_PROFILING_MGR_FORM" name="profile"
                        tokenize="true" skipFirst="true"  elementPostfix="<%=allPostfix%>" elementPrefix="<tr>"
                        childSeperator="<td>&nbsp;</td>" childPrefix="<%=childPrefix%>" childPostfix=""
                        profileOrderPrefix="<%=profileOrderPrefix%>"
                        helpTextPrefix="<%=helpTextPrefix%>" profileQuestionTypeCdPrefix="<%=profileQuestionTypeCdPrefix%>"
                        profileStatusCdPrefix="<br><b>Status:</b>" readOnlyPrefix="<b> Read Only:</b>"
        profileMetaPrefix="<hr><span style=\"font-weight:bold; padding-left: 10;\">Answer:</span>" 
	metaHelpPrefix="<%=metaHelpPrefix%>" metaTypeCdPrefix="<%=metaTypeCdPrefix%>"
                        profileMetaStatusCdPrefix="<b>Status:</b>"
                        admin="true" readOnly="false"
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

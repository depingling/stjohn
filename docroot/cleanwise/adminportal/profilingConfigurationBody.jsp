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


<div class="text">
<font color=red>
<html:errors/>
</font>

<table width="769" cellspacing="0" border="0"  class="mainbody">
<html:form name="PROFILING_MGR_FORM" action="adminportal/profilingConfiguration.do" type="com.cleanwise.view.forms.ProfilingMgrSurveyForm">

<%--name="PROFILING_MGR_FORM" property="searchType"/>--%>
<tr> <td><b>Find</b></td>
       <td>
          <html:select name="PROFILING_MGR_FORM" property="searchType">
                <html:options collection="profiling.config.type.vector" property="value" labelProperty="label"/>
          </html:select>
       </td>
       <td colspan="2">
          <html:text name="PROFILING_MGR_FORM" property="searchField"/>
       </td>
  </tr>

  <tr>
        <td colspan="2">&nbsp;</td>
        <td colspan="3">
            <html:submit property="action"><app:storeMessage  key="global.action.label.search"/></html:submit>
        </td>
  </tr>
</html:form>
</table>


<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<html:form name="PROFILING_MGR_FORM" action="adminportal/profilingConfiguration.do" type="com.cleanwise.view.forms.ProfilingMgrSurveyForm">
<logic:present name="PROFILING_MGR_FORM" property="selectableAccountResults">
<tr>
        <td>Account Id</td>
        <td>Name</td>
        <td>City</td>
        <td>State/Province</td>
        <td>Type</td>
        <td>Status</td>
        <td>Select</td>
</tr>
<logic:iterate id="arrele" name="PROFILING_MGR_FORM" property="selectableAccountResults.values" indexId="i" type="com.cleanwise.service.api.value.AccountSearchResultView">
        <bean:define id="eleid" name="arrele" property="accountId"/>
        <tr>
                <td><bean:write name="arrele" property="accountId"/></td>
                <td>
                        <a href="accountmgr.do?action=accountdetail&searchType=id&searchField=<%=eleid%>">
                        <bean:write name="arrele" property="shortDesc"/>
                        </a>
                </td>
                <td><bean:write name="arrele" property="city"/></td>
                <td><bean:write name="arrele" property="stateProvinceCd"/></td>
                <td><bean:write name="arrele" property="value"/></td>
                <td><bean:write name="arrele" property="busEntityStatusCd"/></td>
                <%String selectedStr = "selectableAccountResults.selected["+i+"]";%>
                <%--<%=eleid.toString()%>--%>
                <td><html:multibox name="PROFILING_MGR_FORM" property="<%=selectedStr%>" value="true"/></td>
        </tr>
</logic:iterate>
<tr>
        <td colspan="2">&nbsp;</td>
        <td colspan="3">
        <logic:present name="PROFILING_MGR_FORM" property="searchType">
                <bean:define id="theUpdateType" name="PROFILING_MGR_FORM" property="searchType" type="java.lang.String"/>
                <html:hidden name="PROFILING_MGR_FORM" property="updateType" value="<%=theUpdateType%>" />
                <%--<html:hidden name="PROFILING_MGR_FORM" property="updateType" value="Account" />--%>
        </logic:present>
            <html:submit property="action"><app:storeMessage  key="admin.button.submitUpdates"/></html:submit>
        </td>
</tr>
</logic:present>
</html:form>
</table>


</div>

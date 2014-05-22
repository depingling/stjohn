<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>
<script language="JavaScript1.2">
    <!--
    function popLocate(pLoc, name, pDesc) {
        var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc + "&amp;submitFl=true";
        locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
        locatewin.focus();

        return false;
    }

    //-->
</script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="REPORT_SCHEDULE_FORM" type="com.cleanwise.view.forms.ReportScheduleForm"/>

<table cellpadding="2" cellspacing="0" border="0" width="750">
    <tbody>
        <tr><td align="center"><font color=red><html:errors/></font></td></tr>
    </tbody>
</table>

<table cellpadding="0" cellspacing="0" border="0" width="750">
<html:form name="REPORT_SCHEDULE_FORM" action="reporting/reportSchedule"
           scope="session" type="com.cleanwise.view.forms.ReportScheduleForm">
    <tbody>
        <!-- -->
        <tr>
            <td colspan="2" class="subheader"><b>Filters</b></td>
        </tr>
        <tr>
            <td><b>Category:</b>
                <html:text name="REPORT_SCHEDULE_FORM" property="categoryFilter"/></td>
            <td><b>Report Name:</b>
                <html:text name="REPORT_SCHEDULE_FORM" property="reportFilter"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <html:hidden property="setFilter" value="" />
                <button onclick="document.forms[0].setFilter.value='setScheduleFilter'; document.forms[0].submit();">Search</button>
                <button onclick="document.forms[0].setFilter.value='clearScheduleFilter'; document.forms[0].submit();">View All</button>
            </td>
        </tr>

    </tbody>
    </table>
    <table cellpadding="3" cellspacing="0" border="0" width="750" class="results">
        <%
            ReportScheduleViewVector reportSchedules = theForm.getReportSchedules();
            int qty = 0;
            if(reportSchedules!=null) qty = reportSchedules.size();
            if(qty>0) {
        %>
        <tr>
            <td  class="rptmid_blue"><a class="rptmid_blue" href="reportSchedule.do?action=sort&field=id"><b>Id</b></a></td>
            <td class="rptmid_blue"><a class="rptmid_blue" href="reportSchedule.do?action=sort&field=category"><b>Category</b></a></td>
            <td class="rptmid_blue"><a class="rptmid_blue" href="reportSchedule.do?action=sort&field=report"><b>Report Name</b></a></td>
            <td class="rptmid_blue"><a class="rptmid_blue" href="reportSchedule.do?action=sort&field=schedule"><b>Schedule Name</b></a></td>
            <td class="rptmid_blue"><a class="rptmid_blue"><b>Parameters</b></a></td>
            <td class="rptmid_blue"><a class="rptmid_blue"><b>Schedule</b></a></td>
            <td class="rptmid_blue"><a class="rptmid_blue"><b>Last Run</b></a></td>
            <td class="rptmid_blue"><a class="rptmid_blue"><b>Select</b></a></td>
        </tr>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            for(int ii=0; ii<qty; ii++) {
                ReportScheduleView rsVw = (ReportScheduleView) reportSchedules.get(ii);
                String reportScheduleIdS = ""+rsVw.getReportScheduleId();
                String href = "reportSchedule.do?action=detail&id="+reportScheduleIdS;
                Date lastRun = rsVw.getLastRunDate();
                String lastRunS = (lastRun==null)?"":sdf.format(lastRun);
        %>
<% if ( ( ii % 2 ) == 0 ) { %>
<tr >
<% } else { %>
<tr class="rowb">
<% } %>

            <td><%=reportScheduleIdS%></td>
            <td><%=rsVw.getReportCategory()%></td>
            <td><%=rsVw.getReportName()%></td>
            <td><A href="<%=href%>"><%=rsVw.getScheduleName()%></A></td>
            <td><%=rsVw.getReportParameters()%></td>
            <td><%=rsVw.getScheduleInfo()%></td>
            <td><%=lastRunS%></td>
            <td><html:multibox name="REPORT_SCHEDULE_FORM" property="scheduleSelected" value="<%=reportScheduleIdS%>" /></td>
        </tr>
        <%
                }
            }
        %>
   </table>

<div class="text" align="right">
                <html:hidden name="REPORT_SCHEDULE_FORM" property="newScheduleReportId" value="0"/>
                <% if(qty>0){ %>
                <html:submit property="action" value="Delete Selected Schedules"/>
                <%}%>
</div>
</html:form>




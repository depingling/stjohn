
<app:checkLogon/>


<script language="JavaScript1.2">
    <!--
    function popLocate(pLoc, name, pDesc) {
        var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
        locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
        locatewin.focus();
        return false;
    }

    //-->
</script>

<%/*
    ReportScheduleJoinView reportJoin = theForm.getReportScheduleJoin();
    GenericReportData report = reportJoin.getReport();
    ReportScheduleData schedule = reportJoin.getSchedule();
    ReportScheduleDetailDataVector scheduleDetails = reportJoin.getScheduleDetails();
    GenericReportControlViewVector controls = reportJoin.getReportControls();
    theFormA.setReport(report);
    theFormA.setReportId(report.getGenericReportId());
    theFormA.setGenericControls(controls);
*/%>
<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
    <tr>
        <td>&nbsp;</td>
        <td>Report Id:&nbsp;<b><%=report.getGenericReportId()%></b></td>
        <td>Report Schedule Id:&nbsp;<b><%=schedule.getReportScheduleId()%></b></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>Category:&nbsp;<b><%=report.getCategory()%></b></td>
        <td>Report Name:&nbsp;<b><%=report.getName()%></b></td>
    </tr>
</table>

<table cellpadding="0" cellspacing="0" border="0" width="769" class="mainbody">
  <tr><td>
  <jsp:include flush='true' page="analyticRepRequest.jsp">
    <jsp:param name="runFromSchedule"  value="true" />
  </jsp:include>
  </td>
  </tr>
</table>





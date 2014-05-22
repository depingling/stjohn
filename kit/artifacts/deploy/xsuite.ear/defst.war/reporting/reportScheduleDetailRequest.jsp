<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<html:form styleId= "REPORT_SCHEDULE_FORM_ID" name="REPORT_SCHEDULE_FORM" action="reporting/reportSchedule"
    scope="session" type="com.cleanwise.view.forms.ReportScheduleForm">

  <tr class="rptmid_blue">
  <td>
    &nbsp;
  </td>
  </tr>
  <tr><td>
  <jsp:include flush='true' page="reportScheduleCalendar.jsp"/>
  </td>
  </tr>

  <%
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
  String userType = appUser.getUser().getUserTypeCd();
  if( !RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userType) ) {  %>
  <tr class="rptmid_blue">
    <td>
    &nbsp;
    </td>
  </tr>
  <tr><td>
    <jsp:include flush='true' page="reportScheduleShare.jsp"/>
  </td> </tr>
 <%    } else { %>
  <html:hidden styleId="hiddenAction" name="REPORT_SCHEDULE_FORM" property="action" value="BBBBBB"/>

 <%}%>


  <tr class="rptmid_blue"><td>
 <%/*>   <html:hidden property="action" value="Save Schedule"/> */%>
    <button styleClass="store_fb" onclick="setAndSubmitA('ANALYTIC_REPORT_FORM_ID','REPORT_SCHEDULE_FORM_ID','Save Schedule');">Save Schedule</button>
 <%/*>   <html:submit property="action" value="Save & Run Report"/> */%>
    <button styleClass="store_fb" onclick="setAndSubmitA('ANALYTIC_REPORT_FORM_ID','REPORT_SCHEDULE_FORM_ID','Save & Run Report');">Save & Run Report</button>

</td>
  </tr>

  <%
  for (int ii = 0; ii < repControls.size(); ii++) {
  String controlEl = "genericControlValue["+ii+"]";
  %>
  <html:hidden name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" value="" />
    <%   } %>

<bean:define id="theFormA" name="ANALYTIC_REPORT_FORM" type="com.cleanwise.view.forms.AnalyticReportForm"/>
 <%
 // int iiMax = (theFormA.getRunForAccounts() != null) ? theFormA.getRunForAccounts().length : 20 ;
 // for (int ii = 0; ii < iiMax; ii++) {
  String runForAccountsEl = "runForAccounts";
  %>
  <html:hidden name="REPORT_SCHEDULE_FORM" property="<%=runForAccountsEl%>" value="" />
    <%//   } %>

  <html:hidden property="command" value="CCCCCCC"/>

</html:form>







</html>
</html>
</html>

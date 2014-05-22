<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cleanwise.service.api.value.*"%>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="theForm" name="REPORT_SCHEDULE_FORM" type="com.cleanwise.view.forms.ReportScheduleForm"/>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">
  <font color=red><html:errors/></font>
  <table width="769" cellspacing="0" border="0" class="mainbody">
  <tr>
  <td class="smalltext">
    <!-- Schdule -->
    <table class="smalltext" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
    <td colspan="2"><b>Schedule Name:</b>
    <html:text name="REPORT_SCHEDULE_FORM" property="reportScheduleJoin.schedule.shortDesc" size="30" maxlength="30"/>
    <font color="red">*</font>
    </td>
    <td><b>&nbsp;</b></td>
    <td><b>&nbsp;</b></td>
    </tr>
  <tr>
  <td class="smalltext">
   &nbsp;&nbsp;<b>Schedule Type:</b>
   <html:hidden property="scheduleRuleChange" value="" />
   <html:select name="REPORT_SCHEDULE_FORM" property="reportScheduleJoin.schedule.reportScheduleRuleCd" onchange="document.forms[1].scheduleRuleChange.value='true'; document.forms[1].submit()">
   <html:option value="<%=RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK%>">Week</html:option>
   <html:option value="<%=RefCodeNames.REPORT_SCHEDULE_RULE_CD.DAY_MONTH%>">Month 1</html:option>
   <html:option value="<%=RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK_MONTH%>">Month 2</html:option>
  </html:select>
  </td>
    <td><b>Status:</b>
   <html:select name="REPORT_SCHEDULE_FORM" property="reportScheduleJoin.schedule.reportScheduleStatusCd">
   <html:option value="<%=RefCodeNames.REPORT_SCHEDULE_STATUS_CD.ACTIVE%>">
                       <%=RefCodeNames.REPORT_SCHEDULE_STATUS_CD.ACTIVE%></html:option>
   <html:option value="<%=RefCodeNames.REPORT_SCHEDULE_STATUS_CD.INACTIVE%>">
                       <%=RefCodeNames.REPORT_SCHEDULE_STATUS_CD.INACTIVE%></html:option>
   </html:select>
    </td>
    <td><b>&nbsp;</b></td>
    <td>
      <b>Suppress Email if Report Result is Empty: </b><html:checkbox  name="REPORT_SCHEDULE_FORM" property="emailFlag" value = "Yes"/>&nbsp;
    </td>
<%//  <td><b>&nbsp;</b></td> %>
    </tr>
    </table>
  </td>
  </tr>
  <% String scheduleType = theForm.getReportScheduleJoin().getSchedule().getReportScheduleRuleCd();
  %>
  <!-- Monthly Day Schedule -->
  <% if(RefCodeNames.REPORT_SCHEDULE_RULE_CD.DAY_MONTH.equals(scheduleType)) { %>
  <tr>
  <td>
  <table class="smalltext" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
  <td><b>&nbsp;Every</b><html:text name="REPORT_SCHEDULE_FORM" property="monthDayCycle" size="1" styleClass="smalltext"/><b>month(s):</b></td>
  <% for(int ii=1; ii<29; ii++) { %>
  <td align="center"><%=ii%></td>
  <% } %>
  <td>Last Day</td>
  </tr>
  <tr>
  <td>&nbsp;</td>
  <% for(int ii=1; ii<29; ii++) { %>
  <td><html:multibox name="REPORT_SCHEDULE_FORM" property="monthDays" value="<%=\"\"+ii%>" /></td>
  <% } %>
  <td><html:multibox name="REPORT_SCHEDULE_FORM" property="monthDays" value="32" /></td>
  </tr>
   </table>
  </td>
  </tr>
  <!-- Monthly Week Schedule -->
  <% } else if(RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK_MONTH.equals(scheduleType)) { %>
  <tr>
  <td>
  <table class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td><b>&nbsp;Every</b><html:text name="REPORT_SCHEDULE_FORM" property="monthWeekCycle" size="1" styleClass="smalltext"/><b>month(s):</b></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>First<html:multibox name="REPORT_SCHEDULE_FORM" property="monthWeeks" value="1" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Second<html:multibox name="REPORT_SCHEDULE_FORM" property="monthWeeks" value="2" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Third<html:multibox name="REPORT_SCHEDULE_FORM" property="monthWeeks" value="3" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Forth<html:multibox name="REPORT_SCHEDULE_FORM" property="monthWeeks" value="4" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Last<html:multibox name="REPORT_SCHEDULE_FORM" property="monthWeeks" value="5" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td><html:select name="REPORT_SCHEDULE_FORM" property="monthWeekDay" styleClass="smalltext">
      <html:option value="1">Sunday</html:option>
      <html:option value="2">Monday</html:option>
      <html:option value="3">Tuesday</html:option>
      <html:option value="4">Wednesday</html:option>
      <html:option value="5">Thursday</html:option>
      <html:option value="6">Friday</html:option>
      <html:option value="7">Saturday</html:option>
      </html:select>
  </td>
  </tr>
   </table>
  </td>
  </tr>
  <!-- Weekly Schedule -->
  <% } else  { %>
  <tr>
  <td>
  <table class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td><b>&nbsp;Every</b><html:text name="REPORT_SCHEDULE_FORM" property="weekCycle" size="1" styleClass="smalltext"/><b>week(s):</b></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Sunday<html:multibox name="REPORT_SCHEDULE_FORM" property="weekDays" value="1" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Monday<html:multibox name="REPORT_SCHEDULE_FORM" property="weekDays" value="2" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Tuesday<html:multibox name="REPORT_SCHEDULE_FORM" property="weekDays" value="3" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Wednesday<html:multibox name="REPORT_SCHEDULE_FORM" property="weekDays" value="4" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Thursday<html:multibox name="REPORT_SCHEDULE_FORM" property="weekDays" value="5" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Friday<html:multibox name="REPORT_SCHEDULE_FORM" property="weekDays" value="6" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Saturday<html:multibox name="REPORT_SCHEDULE_FORM" property="weekDays" value="7" /></td>
  </tr>
   </table>
  </td>
  </tr>
  <% } %>
  <!-- Also Dates -->
  <tr><td>&nbsp;</td>
  </tr>
  <tr>
  <td>
  <table class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td><b>&nbsp;Also Dates:</b><br>&nbsp;&nbsp;&nbsp;(mm/dd/yy, ...)</td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td><html:text name="REPORT_SCHEDULE_FORM" property="alsoDates" size="80" />
</td>
  </tr>
   </table>
  </td>
  </tr>
  <!-- Except Dates -->
  <tr>
  <td>
  <table class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td><b>&nbsp;Except Dates:</b><br>&nbsp;&nbsp;&nbsp;(mm/dd/yy, ...)</td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td><html:text name="REPORT_SCHEDULE_FORM" property="excludeDates" size="80" />
  </td>
  </tr>
   </table>
  </td>
  </tr>
  <!-- Calendar -->
  <tr>
  <td>
  <table class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <%for(int mm=0; mm<6; mm++) { %>
  <td>&nbsp;&nbsp;</td>
  <td>
  <table class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
  <tr><td colspan="7" align="center"><b><%=theForm.getMonth(mm)%></b></td>
  </tr>
  <tr>
  <td align="center"><b>S</b></td><td align="center"><b>M</b></td><td align="center"><b>T</b></td><td align="center"><b>W</b></td>
  <td align="center"><b>T</b></td><td align="center"><b>F</b></td><td align="center"><b>S</b></td>
  </tr>
  <% for(int ii=0; ii<6; ii++) { %>
  <tr>
  <%
  for(int jj=1; jj<8; jj++) {
    int kk = theForm.getDay(mm,ii,jj);
  %>
  <td align="right">
    <% if(kk>=100){ %>
      <font color="blue"><b><%=(kk/100)%></b>&nbsp;</font>
    <% } else if(kk>0) { %>
      <%=kk%>&nbsp;
    <% } else { %>
      &nbsp;
    <% } %>
  </td>
  <% } %>
  </tr>
  <% } %>
  </table>
  </td>
  <% } %>
  <td>&nbsp;</td>
  </tr>
  </table>
  </td>
  </tr>
</table>
</div>







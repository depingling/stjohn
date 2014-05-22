<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cleanwise.service.api.value.*"%>


<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<% String requestNumber =  (String) session.getAttribute(Constants.REQUEST_NUM);%>
<bean:define id="theForm" name="ORDER_SCHEDULE_MGR_FORM" type="com.cleanwise.view.forms.OrderScheduleMgrForm"/>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
  String browser = (String)  request.getHeader("User-Agent");
	String isMSIE = "";
  if(browser!=null && browser.indexOf("MSIE")>=0) {
	   isMSIE = "Y";
%>
    <script language="JavaScript" src="../externals/calendar.js"></script>
    <iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
       marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% } else {  %>
    <script language="JavaScript" src="../externals/calendarNS.js"></script>
<% }  %>

<div class="text">
  <table width="769" cellspacing="0" border="0" class="mainbody">

<!-- content -->
<logic:equal name="ORDER_SCHEDULE_MGR_FORM" property="userOrderGuideNumber" value="0">
<logic:equal name="ORDER_SCHEDULE_MGR_FORM" property="templateOrderGuideNumber" value="0">
<tr>
<td align="center">
<b>No order guides available</b>
</td>
</tr>
</logic:equal>
</logic:equal>
<!-- Order guide select section -->
<% if(theForm.getUserOrderGuideNumber()>0 || theForm.getTemplateOrderGuideNumber()>0) {%>
<html:form action="/adminportal/orderschedulemgr.do?">
<tr>
<td class="smalltext">
  <table class="smalltext" align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
  <td>
    <table class="smalltext" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr height="25">
    <td class="subheaders" width="15%"><b>&nbsp;Account Id:</b>
    </td>
    <td class="subheaders" align="left" width="35%">
    <bean:write  name="ORDER_SCHEDULE_MGR_FORM" property="orderSchedule.accountId"/>
    </td>
    <td class="subheaders" width="20%">
    <b>Account Name:</b>
    </td>
    <td class="subheaders" width="30%">
    <bean:write  name="ORDER_SCHEDULE_MGR_FORM" property="orderSchedule.accountName"/>
    </td>
    </tr>
    <tr height="25">
    <td class="subheaders" width="15%"><b>&nbsp;Site Id:</b>
    </td>
    <td class="subheaders" width="35%">
    <bean:write  name="ORDER_SCHEDULE_MGR_FORM" property="orderSchedule.siteId"/>
    </td>
    <td class="subheaders" width="20%"><b>Site Name:</b>
    </td>
    <td class="subheaders" width="30%">
    <bean:write  name="ORDER_SCHEDULE_MGR_FORM" property="orderSchedule.siteName"/>
    </td>
    </tr>
    <tr height="25">
    <td class="subheaders" width="15%"><b>&nbsp;User Id:</b>
    </td>
    <td class="subheaders" width="35%">
    <bean:write  name="ORDER_SCHEDULE_MGR_FORM" property="orderSchedule.userId"/>
    </td>
    <td class="subheaders" width="20%">
    <b>User Name: </b>
    </td>
    <td class="subheaders" width="30%">
    <html:hidden property="userChange" value="" />
    <%
    UserDataVector users = theForm.getUsers();
    for(int ii=0; ii<users.size(); ii++) {
    UserData user = (UserData) users.get(ii);
    if( user.getUserId() != theForm.getOrderSchedule().getUserId() )
       continue;
    %>
    <%=user.getLastName()%>
    <% } %>
    </td>
    </tr>
    </table>
    <table class="smalltext" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
    <td>
    &nbsp;&nbsp;
    <html:select name="ORDER_SCHEDULE_MGR_FORM" property="orderSchedule.orderGuideId">
    <html:options  name="ORDER_SCHEDULE_MGR_FORM" property="templateOrderGuideIds" labelName="ORDER_SCHEDULE_MGR_FORM" labelProperty="templateOrderGuideNames"/>
    <html:options  name="ORDER_SCHEDULE_MGR_FORM" property="userOrderGuideIds" labelName="ORDER_SCHEDULE_MGR_FORM" labelProperty="userOrderGuideNames"/>
    </html:select>
    </td>
    <td>Start Date: <html:text name="ORDER_SCHEDULE_MGR_FORM" property="startDate"/>
    <% if ("Y".equals(isMSIE)) { %>
			<a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].STARTDATE, document.forms[0].startDate, null, -7300, 7300);" title="Choose Date"
     	><img name="STARTDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
    <% } else {  %>
	  	<a href="javascript:show_calendar('forms[0].startDate');" onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;" title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
    <% }  %>
    </td>
    <td>End Date: <html:text name="ORDER_SCHEDULE_MGR_FORM" property="endDate"/>
    <% if ("Y".equals(isMSIE)) { %>
			<a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ENDDATE, document.forms[0].endDate, null, -7300, 7300);" title="Choose Date"
     	><img name="ENDDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
    <% } else {  %>
			<a href="javascript:show_calendar('forms[0].endDate');" onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;" title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
    <% }  %>
    </td>
    </tr>
   </table>
  </td>
  </tr>
  <tr>
  <td class="smalltext">
  <html:radio name="ORDER_SCHEDULE_MGR_FORM" property="orderSchedule.orderScheduleCd" value="<%=RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY%>"/>Remind to place order
  <html:radio name="ORDER_SCHEDULE_MGR_FORM" property="orderSchedule.orderScheduleCd" value="<%=RefCodeNames.ORDER_SCHEDULE_CD.PLACE_ORDER%>"/>Automatically place order
  <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   Cc eMail address (oprtional):</b><html:text name="ORDER_SCHEDULE_MGR_FORM" property="ccEmail" size="35"/>
  </td>
  </tr>
  <!-- subheader Schedule -->
  <tr>
  <td class="subheaders">
   &nbsp;&nbsp;<b>Schedule</b>
   <html:hidden property="scheduleTypeChange" value="" />
   <html:select name="ORDER_SCHEDULE_MGR_FORM" property="orderSchedule.orderScheduleRuleCd" onchange="document.forms[0].scheduleTypeChange.value='true'; document.forms[0].submit()">
   <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK%>">Week</html:option>
   <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH%>">Month 1</html:option>
   <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH%>">Month 2</html:option>
  </html:select>
  </td>
  </tr>
  <% String scheduleType = theForm.getOrderSchedule().getOrderScheduleRuleCd(); %>
  <!-- Monthly Day Schedule -->
  <% if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(scheduleType)) { %>
  <tr>
  <td>
  <table class="smalltext" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
  <td><b>&nbsp;Every</b><html:text name="ORDER_SCHEDULE_MGR_FORM" property="monthDayCycle" size="1" styleClass="smalltext"/><b>month(s):</b></td>
  <% for(int ii=1; ii<29; ii++) { %>
  <td align="center"><%=ii%></td>
  <% } %>
  <td>Last Day</td>
  </tr>
  <tr>
  <td>&nbsp;</td>
  <% for(int ii=1; ii<29; ii++) { %>
  <td><html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="monthDays" value="<%=\"\"+ii%>" /></td>
  <% } %>
  <td><html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="monthDays" value="32" /></td>
  </tr>
   </table>
  </td>
  </tr>
  <!-- Monthly Week Schedule -->
  <% } else if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(scheduleType)) { %>
  <tr>
  <td>
  <table class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td><b>&nbsp;Every</b><html:text name="ORDER_SCHEDULE_MGR_FORM" property="monthWeekCycle" size="1" styleClass="smalltext"/><b>month(s):</b></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>First<html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="monthWeeks" value="1" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Second<html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="monthWeeks" value="2" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Third<html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="monthWeeks" value="3" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Forth<html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="monthWeeks" value="4" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Last<html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="monthWeeks" value="5" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td><html:select name="ORDER_SCHEDULE_MGR_FORM" property="monthWeekDay" styleClass="smalltext">
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
  <td><b>&nbsp;Every</b><html:text name="ORDER_SCHEDULE_MGR_FORM" property="weekCycle" size="1" styleClass="smalltext"/><b>week(s):</b></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Sunday<html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="weekDays" value="1" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Monday<html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="weekDays" value="2" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Tuesday<html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="weekDays" value="3" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Wednesday<html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="weekDays" value="4" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Thursday<html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="weekDays" value="5" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Friday<html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="weekDays" value="6" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Saturday<html:multibox name="ORDER_SCHEDULE_MGR_FORM" property="weekDays" value="7" /></td>
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
  <td><b>&nbsp;Also place order on:</b><br>&nbsp;&nbsp;&nbsp;(mm/dd/yy, ...)</td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td><html:text name="ORDER_SCHEDULE_MGR_FORM" property="alsoDates" size="80" /></td>
  </tr>
   </table>
  </td>
  </tr>
  <!-- Except Dates -->
  <tr>
  <td>
  <table class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td><b>&nbsp;Do not place order on:</b><br>&nbsp;&nbsp;&nbsp;(mm/dd/yy, ...)</td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td><html:text name="ORDER_SCHEDULE_MGR_FORM" property="excludeDates" size="80" /></td>
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
    <% } else { %>&nbsp;<% } %>
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
    <!--  --->
  <tr>
  <td align="center"  class="smalltext">
   <html:submit property="action" value="Save"/>
   <% if(theForm.getOrderSchedule().getOrderScheduleId()>0) {  %>
     <html:submit property="action" value="Delete"/>
   <% } %>
  </td>
  </tr>
  </table>
</tr>
</html:form>
<% } %>
</table>
</div>
<!-- reset checkboxes -->
<%
   theForm.setWeekDays(new int[0]);
   theForm.setMonthDays(new int[0]);
   theForm.setMonthWeeks(new int[0]);
%>






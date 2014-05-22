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

<bean:define id="theForm" name="DELIVERY_SCHEDULE_FORM" type="com.cleanwise.view.forms.DeliveryScheduleMgrForm"/>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
  String portal = request.getParameter("portal");
  boolean adminPortalFl = ("adminportal".equalsIgnoreCase(portal))?true:false;
  String actionStr = (adminPortalFl)?"/adminportal/deliveryScheduleMgr.do":"/console/crcDeliverySchedule.do";  
  String thisLink = (adminPortalFl)?"deliveryScheduleMgr.do":"crcDeliverySchedule.do";
  boolean readOnlyFl = (adminPortalFl)?false:true;
  
  String disabledStr = (readOnlyFl)?"disabled='true'":"";

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
  <font color=red><html:errors/></font>
  <table width="769" cellspacing="0" border="0" class="mainbody">

  <html:form action="<%=actionStr%>">
  <html:hidden name="DELIVERY_SCHEDULE_FORM" property="contentPage"  value="deliveryScheduleMgrDetail.jsp"/>
  <tr>
  <td class="smalltext">
    <!-- Distributor info -->
    <table class="smalltext" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
    <td><b>Distributor&nbsp;Id:</b>
    <bean:write name="DIST_DETAIL_FORM" property="intId"/></td>
    <td><b>Name:</b>
    <bean:write name="DIST_DETAIL_FORM" property="name"/></td>
    <td><b>Type:</b>
    <bean:write name="DIST_DETAIL_FORM" property="typeDesc"/></td>
    </tr>
    <tr>
    <td><b>Schedule Id:</b>
    <bean:write name="DELIVERY_SCHEDULE_FORM" property="scheduleData.scheduleId"/></td>
    <td colspan="2">&nbsp;</td>
    </tr>
    </table>
    <!-- Schdule -->
    <table class="smalltext" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
    <td colspan="2"><b>Schedule Name:</b> <html:text name="DELIVERY_SCHEDULE_FORM" property="scheduleData.shortDesc" size="30" readonly="<%=readOnlyFl%>"/>
    <font color="red">*</font>
    </td>
    <td><b>Effective Date:</b> <html:text name="DELIVERY_SCHEDULE_FORM" property="effDate" size="10" readonly="<%=readOnlyFl%>"/>
    <% if(!readOnlyFl) { %>
    <% if ("Y".equals(isMSIE)) { %>
      <a href="#" onClick="return ShowCalendar(document.forms[0].STARTDATE, document.forms[0].effDate, null, -7300, 7300, null);" title="Choose Date"
     	><img name="STARTDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
    <% } else {  %>
	 <a href="javascript:show_calendar('forms[0].effDate');" onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;" title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
    <% }  %><% } %><font color="red">*</font>
    </td>
    <td><b>Expiration Date:</b> <html:text name="DELIVERY_SCHEDULE_FORM" property="expDate" size="10" readonly="<%=readOnlyFl%>"/>
    <% if(!readOnlyFl) {%>
    <% if ("Y".equals(isMSIE)) { %>
			<a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ENDDATE, document.forms[0].expDate, null, -7300, 7300);" title="Choose Date"
     	><img name="ENDDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
    <% } else {  %>
			<a href="javascript:show_calendar('forms[0].expDate');" onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;" title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
    <% }  %>
    <% } %>
    </td>
    </tr>
  <tr>
  <td class="smalltext">
   &nbsp;&nbsp;<b>Schedule Type:</b>
   <html:hidden property="scheduleRuleChange" value="" />
   <html:select name="DELIVERY_SCHEDULE_FORM" property="scheduleData.scheduleRuleCd" 
      onchange="document.forms[0].scheduleRuleChange.value='true'; document.forms[0].submit()"
      disabled="<%=readOnlyFl%>" >
   <html:option value="<%=RefCodeNames.SCHEDULE_RULE_CD.WEEK%>">Week</html:option>
   <html:option value="<%=RefCodeNames.SCHEDULE_RULE_CD.DAY_MONTH%>">Month 1</html:option>
   <html:option value="<%=RefCodeNames.SCHEDULE_RULE_CD.WEEK_MONTH%>">Month 2</html:option>
  </html:select>
  </td>
    <td><b>Status:</b>
   <html:select name="DELIVERY_SCHEDULE_FORM" property="scheduleData.scheduleStatusCd"
      disabled="<%=readOnlyFl%>" >
   <html:option value="<%=RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE%>"><%=RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE%></html:option>
   <html:option value="<%=RefCodeNames.SCHEDULE_STATUS_CD.INACTIVE%>"><%=RefCodeNames.SCHEDULE_STATUS_CD.INACTIVE%></html:option>
   <html:option value="<%=RefCodeNames.SCHEDULE_STATUS_CD.LIMITED%>"><%=RefCodeNames.SCHEDULE_STATUS_CD.LIMITED%></html:option>
   </html:select>
    </td>
    <td colspan="2"><b>Cutoff Day:</b><html:text name="DELIVERY_SCHEDULE_FORM" property="cutoffDay" size="1" readonly="<%=readOnlyFl%>"/>
     <font color="red">*</font>
    <b>Cutoff Time</b><%if(!readOnlyFl) {%>(12:00 pm)<%}%><html:text name="DELIVERY_SCHEDULE_FORM" property="cutoffTime" size="8" readonly="<%=readOnlyFl%>"/>
     <font color="red">*</font>
    </TD>
    </tr>
    </table>
  </td>
  </tr>
  <% String scheduleType = theForm.getScheduleData().getScheduleRuleCd(); %>
  <!-- Monthly Day Schedule -->
  <% if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(scheduleType)) { %>
  <tr>
  <td>
  <table class="smalltext" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
  <td><b>&nbsp;Every</b><html:text name="DELIVERY_SCHEDULE_FORM" property="monthDayCycle" size="1" 
      styleClass="smalltext" readonly="<%=readOnlyFl%>"/><b>month(s):</b></td>
  <% for(int ii=1; ii<29; ii++) { %>
  <td align="center"> <% if(ii<10){%>&nbsp;<% } %><%=ii%></td>
  <% } %>
  <td>Last Day</td>
  </tr>
  <tr>
  <td>&nbsp;</td>
  <% if(!readOnlyFl) {%>    
  <% for(int ii=1; ii<29; ii++) { %>
  <td><html:multibox name="DELIVERY_SCHEDULE_FORM" property="monthDays" value="<%=\"\"+ii%>" /></td>
  <% } %>
  <td><html:multibox name="DELIVERY_SCHEDULE_FORM" property="monthDays" value="32" /></td>
  <% } else { %>
  <% 
    int[] monthDays = theForm.getMonthDays();
    int dayNum = 0;
    for(int ii=1,jj=0; ii<=29; ii++) {
      boolean deliveryFl = false;
      for(;jj<monthDays.length;jj++) { 
        if(monthDays[jj]==ii || monthDays[jj]>=32) {
          dayNum = monthDays[jj];
          deliveryFl = true;
          jj++;
          break;
        }
        if(monthDays[jj]>ii) {
          break;
        }
      }
  %>
  <td align='center'><b>
  <% if(deliveryFl) {%><html:hidden name="DELIVERY_SCHEDULE_FORM" property="monthDays" value='<%=""+dayNum%>'/>
   +
  <% } else { %>-<% } %>
  </b>
  </td>
  <% } %>
  <% } %>
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
  <td><b>&nbsp;Every</b><html:text name="DELIVERY_SCHEDULE_FORM" property="monthWeekCycle" size="1" 
         styleClass="smalltext" readonly="<%=readOnlyFl%>"/><b>month(s):</b></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <% if(!readOnlyFl) { %>
  <td>First<html:multibox name="DELIVERY_SCHEDULE_FORM" property="monthWeeks" value="1" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Second<html:multibox name="DELIVERY_SCHEDULE_FORM" property="monthWeeks" value="2" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Third<html:multibox name="DELIVERY_SCHEDULE_FORM" property="monthWeeks" value="3" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Forth<html:multibox name="DELIVERY_SCHEDULE_FORM" property="monthWeeks" value="4" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Last<html:multibox name="DELIVERY_SCHEDULE_FORM" property="monthWeeks" value="5" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <% } else { %>
    <td>&nbsp;<b>on</b>&nbsp;</td>
  <% 
    int[] monthWeeks = theForm.getMonthWeeks();
    String monthWeeksListStr = "";
    for(int ii=0; ii<monthWeeks.length; ii++) { 
      if(ii!=0) monthWeeksListStr = ", ";
      if(monthWeeks[ii]==1) monthWeeksListStr += "First";
      if(monthWeeks[ii]==2) monthWeeksListStr += "Second";
      if(monthWeeks[ii]==3) monthWeeksListStr += "Third";
      if(monthWeeks[ii]==4) monthWeeksListStr += "Fourth";
      if(monthWeeks[ii]==5) monthWeeksListStr += "Last";
    
    if(monthWeeksListStr.length()>0) {
  %>
  <html:hidden name="DELIVERY_SCHEDULE_FORM" property="monthWeeks" value='<%=""+monthWeeks[ii]%>'/>
  <td><b><%=monthWeeksListStr%></b></td>
  <% }} %>
   <td>&nbsp;&nbsp;</td>
  <% } %>
  <td><html:select name="DELIVERY_SCHEDULE_FORM" property="monthWeekDay" styleClass="smalltext"
      disabled="<%=readOnlyFl%>" >
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
  <td><b>&nbsp;Every</b><html:text name="DELIVERY_SCHEDULE_FORM" property="weekCycle" size="1" 
      styleClass="smalltext" readonly="<%=readOnlyFl%>"/><b>week(s):</b></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <% if(!readOnlyFl) { %>
  <td>Sunday<html:multibox name="DELIVERY_SCHEDULE_FORM" property="weekDays" value="1" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Monday<html:multibox name="DELIVERY_SCHEDULE_FORM" property="weekDays" value="2" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Tuesday<html:multibox name="DELIVERY_SCHEDULE_FORM" property="weekDays" value="3" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Wednesday<html:multibox name="DELIVERY_SCHEDULE_FORM" property="weekDays" value="4" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Thursday<html:multibox name="DELIVERY_SCHEDULE_FORM" property="weekDays" value="5" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Friday<html:multibox name="DELIVERY_SCHEDULE_FORM" property="weekDays" value="6" /></td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>Saturday<html:multibox name="DELIVERY_SCHEDULE_FORM" property="weekDays" value="7" /></td>
  <% } else  { %>
  <% 
    int[] weekDays = theForm.getWeekDays();
    String weekDaysListStr = "";
    for(int ii=0; ii<weekDays.length; ii++) { 
      if(ii!=0) weekDaysListStr = ", ";
      if(weekDays[ii]==1) weekDaysListStr += "Sunday";
      if(weekDays[ii]==2) weekDaysListStr += "Monday";
      if(weekDays[ii]==3) weekDaysListStr += "Tuesday";
      if(weekDays[ii]==4) weekDaysListStr += "Wednesday";
      if(weekDays[ii]==5) weekDaysListStr += "Thursday";
      if(weekDays[ii]==6) weekDaysListStr += "Friday";
      if(weekDays[ii]==7) weekDaysListStr += "Saturday";
      if(weekDaysListStr.length()>0) {
  %>
  <html:hidden name="DELIVERY_SCHEDULE_FORM" property="weekDays" value='<%=""+weekDays[ii]%>'/>
  <td><b><%=weekDaysListStr%></b></td>
  <% }}} %>
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
  <td><b>&nbsp;Also delivery on:</b><br>&nbsp;&nbsp;&nbsp;(mm/dd/yy, ...)</td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td><html:text name="DELIVERY_SCHEDULE_FORM" property="alsoDates" size="80" readonly="<%=readOnlyFl%>"/>
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
  <td><b>&nbsp;No delivery on:</b><br>&nbsp;&nbsp;&nbsp;(mm/dd/yy, ...)</td>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td><html:text name="DELIVERY_SCHEDULE_FORM" property="excludeDates" size="80" readonly="<%=readOnlyFl%>"/>
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
  <% if(!readOnlyFl) {%>
  <tr>
  <td align="center"  class="smalltext">
   <html:submit property="action" value="Save"/>
   <% if(theForm.getScheduleData().getScheduleId()>0) {  %>
     <html:submit property="action" value="Delete"/>
   <% } %>
  </td>
  </tr>
  <% } %>
</table>
<!-- ///////////////////////////////////////////////////////////////////////////////// -->
<% if(theForm.getScheduleData().getScheduleId()>0) { %>
<table cellspacing="0" border="0" width="769"  class="mainbody">
<%
  String actConfType = theForm.getActConfigType();
%>
<html:hidden name="DELIVERY_SCHEDULE_FORM" property="actConfigType" value="<%=actConfType%>"/>
<tr><td><b>Config Type:<b></td>
      <td colspan="3">
      <html:select name="DELIVERY_SCHEDULE_FORM" property="configType">
      <html:option value="County">County</html:option>
      <html:option value="Zip Code">Zip Code</html:option>
      </html:select>
  </tr>
  <tr><td><b>State:</b></td>
  <td><html:text name="DELIVERY_SCHEDULE_FORM" property="searchState" size="10"/>
  </td>
  <td><b>County:</b></td>
  <td><html:text name="DELIVERY_SCHEDULE_FORM" property="searchCounty" size="30"/>
  </td>
  <tr><td><b>Zip Code:</b></td>
  <td><html:text name="DELIVERY_SCHEDULE_FORM" property="searchZipCode" size="6"/>
  </td>
  <td><b>Serviced Only:</b></td>
  <td><html:checkbox name="DELIVERY_SCHEDULE_FORM" property="servicedOnly" value="true"/>
  </td>
  </tr>
  <tr> <td>&nbsp;</td>
  <td colspan="3">
  <html:submit property="action" value="Search"/>
  </td>
  </tr>
</table>
<% if("County".equals(theForm.getActConfigType())) {%>
<% int size = theForm.getCounties().size(); %>
Search result count: <%=size %>
<% if(size>0) { %>
<table cellspacing="0" border="0" width="769"  class="results">
<tr align=center>
<td><a class="tableheader" href="<%=thisLink%>?action=sortTerr&sortField=CountyCd"><b>County</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sortTerr&sortField=StateProvinceCd"><b>State Cd</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sortTerr&sortField=StateProvinceName"><b>State Name</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sortTerr&sortField=CountryCd"><b>Country</b></a> </td>
<td class="tableheader">&nbsp;</td>
</tr>
<logic:iterate id="confCounty" name="DELIVERY_SCHEDULE_FORM" property="counties"
  type="com.cleanwise.service.api.value.BusEntityTerrView"
  indexId="idx"
>
  <bean:define id="lCounty"  name="confCounty" property="countyCd"/>
  <bean:define id="lStateCd"  name="confCounty" property="stateProvinceCd"/>
  <bean:define id="lStateName"  name="confCounty" property="stateProvinceName"/>
  <bean:define id="lCountry"  name="confCounty" property="countryCd"/>
  <bean:define id="lDistId" name="confCounty" property="busEntityId"/>
  <bean:define id="lNoModifiyFl" name="confCounty" property="noModifiyFl"/>
<% if ( idx.intValue()%2==0 ) { %>
    <tr class="rowa">
<% } else { %>
    <tr class="rowb">
<% } %>
    <td><bean:write name="lCounty"/></td>
    <td><bean:write name="lStateCd"/></td>
    <td><bean:write name="lStateName"/></td>
    <td><bean:write name="lCountry"/></td>
    <% String countySelectFl = lCounty+"^"+lStateCd+"^"+lCountry;
    %>
    <td>
    <% if(!readOnlyFl) { %>
     <html:multibox name="DELIVERY_SCHEDULE_FORM" property="selected"
       value="<%=countySelectFl%>" disabled="<%=((Boolean)lNoModifiyFl).booleanValue()%>"/>
    <% } else { %>
    &nbsp;
    <% if(confCounty.getCheckedFl()) { %>+<% } %><% if(confCounty.getNoModifiyFl()) { %>-<% } %>      
    <% } %>
    </td>
    </tr>
 </logic:iterate>
<% if(!readOnlyFl) {%>
  <tr>
  <td colspan="5">
  <html:submit property="action" value="Save Configuration"/>
  <html:submit property="action" value="Select All"/>
  <html:submit property="action" value="Clear Selection"/>
  </td>
  </tr>
<% } %>
</table><br><br>
<% } %>
<% } %>
<% if("Zip Code".equals(theForm.getActConfigType())) {%>
<% int size = theForm.getPostalCodes().size(); %>
Search result count: <%=size %>
<% if(size>0) { %>
<table cellspacing="0" border="0" width="769"  class="results">
<tr align=center>
<td><a class="tableheader" href="<%=thisLink%>?action=sortTerr&sortField=PostalCode"><b>Postal Code</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sortTerr&sortField=CountyCd"><b>County</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sortTerr&sortField=StateProvinceCd"><b>State Cd</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sortTerr&sortField=StateProvinceName"><b>State Name</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sortTerr&sortField=CountryCd"><b>Country</b></a> </td>
<td class="tableheader">&nbsp;</td>
</tr>
<logic:iterate id="confPostalCode" name="DELIVERY_SCHEDULE_FORM" property="postalCodes"
  type="com.cleanwise.service.api.value.BusEntityTerrView"
  indexId="idx"
>
  <bean:define id="lPostalCodeId"  name="confPostalCode" property="postalCodeId"/>
  <bean:define id="lPostalCode"  name="confPostalCode" property="postalCode"/>
  <bean:define id="lCounty"  name="confPostalCode" property="countyCd"/>
  <bean:define id="lStateCd"  name="confPostalCode" property="stateProvinceCd"/>
  <bean:define id="lStateName"  name="confPostalCode" property="stateProvinceName"/>
  <bean:define id="lCountry"  name="confPostalCode" property="countryCd"/>
  <bean:define id="lDistId" name="confPostalCode" property="busEntityId"/>
<% if ( idx.intValue()%2==0 ) { %>
    <tr class="rowa">
<% } else { %>
    <tr class="rowb">
<% } %>
    <td><bean:write name="lPostalCode"/></td>
    <td><bean:write name="lCounty"/></td>
    <td><bean:write name="lStateCd"/></td>
    <td><bean:write name="lStateName"/></td>
    <td><bean:write name="lCountry"/></td>
    <% String postalCodeSelectFl = (String)lPostalCode+"^"+(String)lCountry;
    %>
    <td>
    <% if(!readOnlyFl) { %>
    <html:multibox name="DELIVERY_SCHEDULE_FORM" property="selected"
       value="<%=postalCodeSelectFl%>"/>
    
    <% } else { %>
    &nbsp;
    <% if(confPostalCode.getCheckedFl()) { %>+<% } %><% if(confPostalCode.getNoModifiyFl()) { %>-<% } %>      
    <% } %>
    </td>
    </tr>
 </logic:iterate>
<% if(!readOnlyFl) {%>
  <tr>
  <td colspan="5">
  <html:submit property="action" value="Save Configuration"/>
  <html:submit property="action" value="Select All"/>
  <html:submit property="action" value="Clear Selection"/>
  </td>
  </tr>
  <% } %>
</table><br><br>
<% } %>
<% } %>
<% } %>
</html:form>
<!-- ///////////////////////////////////////////////////////////////////////////////// -->
</div>

<%
   theForm.setWeekDays(new int[0]);
   theForm.setMonthDays(new int[0]);
   theForm.setMonthWeeks(new int[0]);
%>






<%@ page language="java" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.EstimatorMgrForm" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% EstimatorMgrForm theForm = (EstimatorMgrForm) session.getAttribute("ESTIMATOR_MGR_FORM"); %>


<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.ESTIMATOR_MGR_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='productSelector') {
     dml.elements[i].checked=val;
   }
 }
}

function popLocate() {
  var loc = "estimatorMgrProductAdd.do";
//alert(loc);
  locatewin = window.open(loc,"tickersearch", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

//-->
</script>

<html:html>
<head>
<title>Cleaning Procedures</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals//styles.css">
</head>

<body bgcolor="#cccccc">
<div class = "text">
<html:form action="/adminportal/estimatorMgr.do" >

  <table border="0" width="750" class="results">
  <tr><td colspan='5' align='center'><b>Select Procedures:</b>
  </tr>
  <tr>
  <td><b>Paper, Soap, Liner Supply</b></td>
  <td><b>Floor Cleaning<br>Hight Traffic</b></td>
  <td><b>Floor Cleaning<br>Medium Traffic</b></td>
  <td><b>Floor Cleaning<br>Low Traffic</b></td>
  <td><b>Restroom Cleaning</td>
  </tr>
<tr>
<td>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.HAND_SOAP_SUPPLY%>'/>Hand Soap<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.PAPER_TOWEL_SUPPLY%>'/>Paper Towels<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.TOILET_TISSUE_SUPPLY%>'/>Toilet Tissues<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.SEAT_COVER_SUPPLY%>'/>Seat Covers<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.OFFICE_LINER_SUPPLY%>'/>Office Liners<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.COMMONON_AREA_LINER_SUPPLY%>'/>Common Area Liners<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.BATHROOM_LINER_SUPPLY%>'/>Bathroom Liners<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.CONTAINER_LINER_SUPPLY%>'/>Container Liners
</td>
<td>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.HT_WOOD_FLOOR_CLEANING%>'/>Wood<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.HT_VCT_FLOOR_CLEANING%>'/>VCT Tyle<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.HT_TERRAZZO_FLOOR_CLEANING%>'/>Terrazzo<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.HT_CONCRETE_FLOOR_CLEANING%>'/>Concrete<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.HT_CERAMIC_FLOOR_CLEANING%>'/>Ceramic<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.HT_CARPET_FLOOR_CLEANING%>'/>Carpet
</td>
<td>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.MT_WOOD_FLOOR_CLEANING%>'/>Wood<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.MT_VCT_FLOOR_CLEANING%>'/>VCT Tyle<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.MT_TERRAZZO_FLOOR_CLEANING%>'/>Terrazzo<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.MT_CONCRETE_FLOOR_CLEANING%>'/>Concrete<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.MT_CERAMIC_FLOOR_CLEANING%>'/>Ceramic<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.MT_CARPET_FLOOR_CLEANING%>'/>Carpet<br>
</td>
<td>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.LT_WOOD_FLOOR_CLEANING%>'/>Wood<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.LT_VCT_FLOOR_CLEANING%>'/>VCT Tyle<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.LT_TERRAZZO_FLOOR_CLEANING%>'/>Terrazzo<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.LT_CONCRETE_FLOOR_CLEANING%>'/>Concrete<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.LT_CERAMIC_FLOOR_CLEANING%>'/>Ceramic<br>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.LT_CARPET_FLOOR_CLEANING%>'/>Carpet<br>
</td>
<td>
<html:radio name='ESTIMATOR_MGR_FORM' property='scheduleTypeFilter' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=RefCodeNames.CLEANING_SCHEDULE_CD.RESTROOM_CLEANING%>'/>Restroom
   </td><tr>
</html:form>
  </table>

<% 
   CleaningScheduleJoinViewVector csjVwV = theForm.getCleaningSchedules();
   if(csjVwV!=null && csjVwV.size()>0) {
     boolean floorFl = false;
     String scheduleTypeFilter = theForm.getScheduleTypeFilter();
     if( RefCodeNames.CLEANING_SCHEDULE_CD.HT_WOOD_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.HT_VCT_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.HT_TERRAZZO_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.HT_CONCRETE_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.HT_CERAMIC_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        //RefCodeNames.CLEANING_SCHEDULE_CD.HT_CARPET_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.MT_WOOD_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.MT_VCT_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.MT_TERRAZZO_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.MT_CONCRETE_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.MT_CERAMIC_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        //RefCodeNames.CLEANING_SCHEDULE_CD.MT_CARPET_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.LT_WOOD_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.LT_VCT_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.LT_TERRAZZO_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.LT_CONCRETE_FLOOR_CLEANING.equals(scheduleTypeFilter) ||
        RefCodeNames.CLEANING_SCHEDULE_CD.LT_CERAMIC_FLOOR_CLEANING.equals(scheduleTypeFilter) //||
        //RefCodeNames.CLEANING_SCHEDULE_CD.LT_CARPET_FLOOR_CLEANING.equals(scheduleTypeFilter)
      ) floorFl = true;      
%>
<table width='100%' border='2'>
<tr bgcolor='#eeeeee'>
 <td><b>Name</b></td>
 <td><b>Frequency</b></td>
 <% if(floorFl) { %>
 <td><b>Floor Machine</b></td>
 <% } %> 
 <td><b>Actions</b></td>
</tr>
<html:form action="/adminportal/estimatorMgr.do" >
<%
   int ind = -1;
   for(Iterator iter = csjVwV.iterator(); iter.hasNext();) {
     ind ++;
     CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
     String procFrequencyInp = "cleaningProcFrequency["+ind+"]";     
     String procTimePeriodCdInp = "cleaningProcTimePeriodCd["+ind+"]";     
     String procFloorMachineInp = "cleaningProcFloorMachine["+ind+"]";
     CleaningScheduleData csD = csjVw.getSchedule();
     String cleaningScheduleCd = csD.getCleaningScheduleCd();

     BigDecimal procFrequency = csD.getFrequency();
     String procTimePeriodCd = csD.getTimePeriodCd();
     CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
     String cleaningTitle = csD.getName();
     %>
<tr>
<td><%=csD.getName()%></td>
<td>
  <html:text name='ESTIMATOR_MGR_FORM' property='<%=procFrequencyInp%>' size='3'/> 
  time(s) per
  <html:select name='ESTIMATOR_MGR_FORM' property='<%=procTimePeriodCdInp%>' >
  <html:option value='<%=RefCodeNames.TIME_PERIOD_CD.YEAR%>'><%=RefCodeNames.TIME_PERIOD_CD.YEAR%></html:option>
  <html:option value='<%=RefCodeNames.TIME_PERIOD_CD.MONTH%>'><%=RefCodeNames.TIME_PERIOD_CD.MONTH%></html:option>
  <html:option value='<%=RefCodeNames.TIME_PERIOD_CD.WEEK%>'><%=RefCodeNames.TIME_PERIOD_CD.WEEK%></html:option>
  <html:option value='<%=RefCodeNames.TIME_PERIOD_CD.WORKING_DAY%>'><%=RefCodeNames.TIME_PERIOD_CD.WORKING_DAY%></html:option>
  </html:select>&nbsp;
</td>
 <% if(floorFl) { %>
<td>
  <html:select name='ESTIMATOR_MGR_FORM' property='<%=procFloorMachineInp%>' >
  <html:option value=''>&nbsp;</html:option>
  <html:option value='Rotary'>Rotary</html:option>
  <html:option value='Auto'>Auto</html:option>
  </html:select>&nbsp;
</td>
 <% } %>
<td>
  <%
   int ind1 = -1;
   for(Iterator iter1 = cssjVwV.iterator(); iter1.hasNext();) {
     ind1 ++;
     CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) iter1.next();
     CleaningSchedStructData cssD = cssjVw.getScheduleStep();
     CleaningProcData cpD = cssjVw.getProcedure();
     int cleaningSchedStructId = cssD.getCleaningSchedStructId();
  %>
<%=cpD.getShortDesc()%><br>
<% } %>
&nbsp;</td>
</tr>
<% } %>
  <tr>
  <td colspan='3'>
  <html:submit property="action" value="Save"/>
  </td>
</tr>
</html:form>
</table>
<% } %>


</body>

</html:html>



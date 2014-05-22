<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.logic.ReportScheduleLogic" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>
<bean:define id="theForm" name="REPORT_SCHEDULE_FORM" type="com.cleanwise.view.forms.ReportScheduleForm"/>
<bean:define id="theFormA" name="ANALYTIC_REPORT_FORM" type="com.cleanwise.view.forms.AnalyticReportForm"/>
<%
   String action = (String) request.getAttribute("action");
   String actionParam =  (String) request.getParameter("action");

    ReportScheduleJoinView reportJoin = theForm.getReportScheduleJoin();
    ReportScheduleData schedule = reportJoin.getSchedule();
    ReportScheduleDetailDataVector scheduleDetails = reportJoin.getScheduleDetails();

    GenericReportData report = reportJoin.getReport();
    GenericReportControlViewVector repControls = reportJoin.getReportControls();
//    GenericReportControlViewVector aControls ;
    if (report != null){
      theFormA.setReport(report);
      theFormA.setReportId(report.getGenericReportId());
    }
   if (repControls != null){
      if (schedule.getReportScheduleId()==0){  // if we Create Schedule

        theForm.setAccountFilter(theFormA.getAccountFilter());
        theForm.setSiteFilter(theFormA.getSiteFilter());
        theForm.setItemFilter(theFormA.getItemFilter());
        theForm.setManufFilter(theFormA.getManufFilter());
        theForm.setDistFilter(theFormA.getDistFilter());
        theForm.setRunForAccounts(theFormA.getRunForAccounts());

      }  else {                                // if we Update Schedule
         // prepare values for Locate buttons
         if ((action==null && actionParam != null && !actionParam.equals("Search")) ||
             (action!=null && (action.equals("detail") || action.startsWith("Save"))) ){             // schedule action
            GenericReportControlViewVector aControls = new GenericReportControlViewVector();
            aControls = new GenericReportControlViewVector();
            for (int kk = 0; kk < repControls.size(); kk++) {
              GenericReportControlView repControl = (GenericReportControlView)repControls.get(kk);
              aControls.add(repControl.copy());
            }
            theFormA.setGenericControls(aControls);
            theFormA.setAccountFilter(theForm.getAccountFilter());
            theFormA.setSiteFilter(theForm.getSiteFilter());
            theFormA.setItemFilter(theForm.getItemFilter());
            theFormA.setManufFilter(theForm.getManufFilter());
            theFormA.setDistFilter(theForm.getDistFilter());
            theFormA.setRunForAccounts(theForm.getRunForAccounts());
          } else{
            theForm.setAccountFilter(theFormA.getAccountFilter());
            theForm.setSiteFilter(theFormA.getSiteFilter());
            theForm.setItemFilter(theFormA.getItemFilter());
            theForm.setManufFilter(theFormA.getManufFilter());
            theForm.setDistFilter(theFormA.getDistFilter());
            theForm.setRunForAccounts(theFormA.getRunForAccounts());
            repControls = theFormA.getGenericControls();
          }
       }
       ReportScheduleLogic.scatterSchedule(request, theForm);
       ReportScheduleLogic.showDates(request, theForm);

    } else {
      repControls = theFormA.getGenericControls();
    }
%>


<script language="JavaScript1.2">

function setAndSubmitA(fid1,fid2, value) {
   var elsFrom = document.forms[fid1].elements;
   if ('undefined' != typeof elsFrom ) {
      var elsTo = document.forms[fid2].elements;
      var k= 0;
      var prevFromName = '';
      var prevElTo = '';
      if ('undefined' != typeof elsFrom.length ) {
        var fromArray = new Array();
        for(i=0; i<elsFrom.length; i++){
//          alert ('elsFrom[i].name =' +  elsFrom[i].name);
          var fromName = elsFrom[i].name;
          var fromValue = elsFrom[i].value;
          if (elsFrom[i].type=='checkbox'){
            if (fromName.indexOf("runForAccounts") >=0){
                if (elsFrom[i].checked){
//                alert ('elsFrom[i].name =' +  elsFrom[i].name + '=' + fromValue);
                  fromArray[k] = fromValue;
                  k++;
                }
                prevFromName = fromName;
                continue;
            }else{
                fromValue = (elsFrom[i].checked) ? 'Yes' : 'No';
            }
          }
          if (prevFromName!= '' && prevFromName.indexOf("runForAccounts") >=0 ) {
              elTo = elsTo[prevFromName];
//              alert ('prevFromName =' +  prevFromName +'->'+ elTo + '=' + fromArray);
              if ('undefined' != typeof elTo) {
                if ('undefined' != typeof elTo.value ) {
                    elTo.value = fromArray;
                    prevFromName = '';
//                    alert ('toName =' +  elTo.name+'(' +elTo.value +')');
                }
              }
          }
          if ('undefined' != typeof  fromName ) {
            if (fromName.indexOf("genericControlValue") >=0 ) {
              elTo = elsTo[fromName];
//              alert ('fromName =' +  fromName +'->'+ elTo + '=' + fromValue);
              if ('undefined' != typeof elTo) {
                if ('undefined' != typeof elTo.value ) {
                    elTo.value = fromValue;
//                    alert ('toName2 =' +  elTo.name+'(' +elTo.value +')');
                }
              }
            }
          }
        }
      }
    }

    setAndSubmitB(fid2, 'command', value);
    return false;
}

function setAndSubmitB(fid, vv, value) {
    var actionElements = document.forms[fid].elements;
    for(i=0; i<actionElements.length; i++){
      var element = actionElements[i];
//alert (element.name + "==" + vv) ;
      if (element.name == vv){
        element.value = value;
        break;
      }
    }
    document.forms[fid].submit();
//    var actionElements = document.getElementsByName(vv);
//      var element = actionElements[0];
//alert (element.value) ;
//    element.value = value;
//    element.form.submit();

    return false;
}


</script>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% String onKeyPress="return submitenter(this,event,'Submit');"; %>
<% String repUserTypes = (report.getUserTypes() != null) ? report.getUserTypes() : "";%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<jsp:include flush='true' page="/storeportal/locateStoreUser.jsp">
        <jsp:param name="jspFormAction" value="/reporting/reportSchedule.do" />
        <jsp:param name="jspFormName" 	value="REPORT_SCHEDULE_FORM" />
	<jsp:param name="jspSubmitIdent" 	        value="" />
        <jsp:param name="isSingleSelect" 	value="true" />
        <jsp:param name="jspReturnFilterPropertyAlt" value="userDummyConvert"/>
        <jsp:param name="jspReturnFilterProperty" value="userFilter"/>
        <jsp:param name="jspUserTypesAutorized" value="<%=repUserTypes%>"/>
</jsp:include>

<jsp:include flush='true' page="/storeportal/locateStoreGroup.jsp">
    <jsp:param name="jspFormAction"           value="/reporting/reportSchedule.do" />
    <jsp:param name="jspFormName"             value="REPORT_SCHEDULE_FORM" />
    <jsp:param name="jspSubmitIdent"          value="" />
    <jsp:param name="isSingleSelect"          value="true" />
    <jsp:param name="jspReturnFilterProperty" value="groupFilter"/>
</jsp:include>



<table cellpadding="0" cellspacing="0" border="0" width="769">
 <tbody>

  <tr><td>
  <%@ include file="reportScheduleParamRequest.jsp"  %>
<% /*
  <jsp:include flush='true' page="analyticRepRequest.jsp">
    <jsp:param name="runFromSchedule"  value="true" />
  </jsp:include>
*/%>
  </td>
  </tr>

<%@ include file="reportScheduleDetailRequest.jsp"  %>

 </tbody>

</table>






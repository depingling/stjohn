<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.RequestPropertyNames" %>
<%@ page import="com.cleanwise.view.forms.LocateReportAccountForm" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script src="../externals/ajaxutil.js" language="JavaScript"></script>


<style type="text/css">

.genericControlRowA {
    background: #B5B5B5;
}
.genericControlRowB {
    background: Grey;
}

</style>
<app:checkLogon/>
<%
    boolean scheduleFl = false;
    boolean hasDateControls = false;
    String formName  = "ANALYTIC_REPORT_FORM";
    String formClass = "";
    String runFromSchedule = request.getParameter("runFromSchedule");
    if(runFromSchedule != null && runFromSchedule.equals("true")){
	scheduleFl = true;
    }
   String dateConst =(!scheduleFl)? "" :
       Constants.REP_DATE_CONST.LAST_MONTH_YEAR_BEGIN + ",  " +
       Constants.REP_DATE_CONST.THIS_YEAR_BEGIN +",  " +
       Constants.REP_DATE_CONST.LAST_MONTH_END + ",  " +
       Constants.REP_DATE_CONST.LAST_WEEKDAY + ",  " +
       Constants.REP_DATE_CONST.TODAY;

   String dateBegConst =(true)? "" : "(Allowed : " +Constants.REP_DATE_CONST.LAST_MONTH_YEAR_BEGIN + ", " + Constants.REP_DATE_CONST.THIS_YEAR_BEGIN+")";
   String dateEndConst = (true)? "" : "(Allowed : " +Constants.REP_DATE_CONST.LAST_MONTH_END + ", " + Constants.REP_DATE_CONST.TODAY+")";


    String storeDir=ClwCustomizer.getStoreDir();
    String onKeyPress="return submitenter(this,event,'Submit');";
    CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String userType = user.getUser().getUserTypeCd();
    String siteIdS = "0";
    String accountIdS = "0";
    String userIdS = ""+user.getUser().getUserId();
    boolean adminFl = false, fReportUser = false;
    if(user.isaAdmin() || user.isaCustServiceRep() || user.isaStoreAdmin()){
        adminFl = true;
    } else if ( user.getSite() != null ) {
        siteIdS = ""+user.getSite().getSiteId();
        //accountIdS = ""+user.getSite().getAccountAssoc().getBusEntity2Id();
    }

    String storeIdS;
    if(user.getUserStore() != null){
        storeIdS = Integer.toString(user.getUserStore().getStoreId());
    }else if(user.getStores() != null && !user.getStores().isEmpty()){
        storeIdS = Integer.toString(((BusEntityData)user.getStores().get(0)).getBusEntityId());
    }else{
        storeIdS = "0";
    }
	if(storeIdS.equals("128584")) storeIdS = "196842";

    boolean multiStores;
    if(user.getStores() != null && user.getStores().size() > 1){
        multiStores=true;
    }else{
        multiStores=false;
    }
    //   Reporting user Flag
    boolean isReportingUser = false;
    if (user.getUser().getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.REPORTING_USER)){
      isReportingUser = true;
    }
    // Added by SVC 12/30/2009 
    ArrayList invoiceStatusCodeDV = new ArrayList();
    invoiceStatusCodeDV.add("-Select-");
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.CANCELLED);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_PROCESSED);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_RELEASED);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED_FAILED);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.DUPLICATE);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED_ERROR);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.ERP_REJECTED);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.INVOICE_HISTORY);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.PENDING);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.PROCESS_ERP);
    invoiceStatusCodeDV.add(RefCodeNames.INVOICE_STATUS_CD.REJECTED);
    /*** end of new piece of code by SVC ***/
%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="ANALYTIC_REPORT_FORM" type="com.cleanwise.view.forms.AnalyticReportForm"/>

<%
  String requestToken =  ""+theForm.getRequestToken();
  theForm.setScheduleFl(scheduleFl);
  BusEntityDataVector acctBusEntDV = theForm.getUserAccounts();
  if (acctBusEntDV == null) {
     acctBusEntDV=new BusEntityDataVector();
  }
%>

<script language="JavaScript1.2">

var applyEvent = true;

function submitWithAction(action) {
    document.forms[0].action1.value = action;
//    document.forms[0].submit();
    doSubmit(0);
}

function actionSubmit(formId, action, value) {
    var actions;
    actions = document.forms[formId][action];
    var frms = document.forms;
    if ('undefined' != typeof actions.length) {
        for (ii = actions.length - 1; ii >= 0; ii--) {
          if (actions[ii].id == 'hiddenAction') {

            actions[ii].value = value;
            doSubmit(formId);
//           document.forms[formId].submit();

            break;
          }
        }
      } else {
 //        alert (action);
        document.forms[formId]["action"].value = action;
        doSubmit(formId);
//        document.forms[formId].submit();
      }
    return false;
}

function setAndSubmit(fid, vv, value) {
    var aaa = document.forms[fid].elements[vv];
    aaa.value = value;
 //   disableButtons(fid);
   doSubmit(fid);
   return false;
}


function downloadReport(formId, formatValue) {
    var cmd = document.forms[formId].elements['command'];
    cmd.value = 'Download Report';

    var format = document.forms[formId].elements['format'];
    format.value = formatValue;

    //disableButtons(formId);
    doSubmit(formId);
    return false;
}

function doSubmit(formId){
    var mess = checkReport.isLocked('analyticRep.do','action=checkReportState');  //  ajax request
//    var state = isLocked();  //  ajax request
    var key ='@in progress@';
    if (mess.indexOf(key) >= 0){
      alert (mess.replace(key, ''));
    } else {
      document.forms[formId].submit();
    }
}

function disableButtons(formId){
    var download = document.forms[formId].elements['download'];
    var view = document.forms[formId].elements['view'];
    var print = document.forms[formId].elements['print'];
    if (download.disabled || view.disabled || print.disabled ) {
      download.disabled = true;
      view.disabled = true;
      print.disabled = true;
    }
}

function popLocate(pLoc, name, pDesc) {
	var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  	locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  	locatewin.focus();
  	return false;
}

function popLocateUser(pLoc, name, pDesc, pShowStoreMSBOnly) {
	var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc + "&amp;showStoreMSBOnly=" + pShowStoreMSBOnly;
  	locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  	locatewin.focus();
  	return false;
}

<%//submits the form when there are multiple buttons present (locate buttons)%>
function submitRunReport(){
   document.forms[0].action.value="Run Report";
//   document.forms[0].submit();
   doSubmit(0);
}

function submitViewReport(){
   document.forms[0].action.value="View Report";
//   document.forms[0].submit();
   doSubmit(0);
}

function keyDown(e)
{
    var keycode = document.all ? event.keyCode : e.which;
    var realkey = String.fromCharCode(keycode)
    if ((""+keycode)=="13" && applyEvent)
    {
         submitRunReport();
    }
}

document.onkeydown = keyDown;
if ( document.layers )
{
  document.captureEvents(Event.KEYDOWN);
}

</script>
<%@include file="/externals/calendar/calendar.jsp"%>
<body>
<div class="text">
<!--
<table cellpadding="2" cellspacing="0" border="0" width="750" class="mainbody">
 <tbody>
  <tr> <td align="center"><font color=red><html:errors/></font></td></tr>
 </tbody>
</table>
-->

<%
String reportType = theForm.getReport().getGenericReportType();

boolean useLocateAccountControl = false;
boolean useDW=false;
boolean useLocateCatalogControl = false;
{
    GenericReportControlViewVector reportControls = theForm.getGenericControls();
    if (reportControls != null && reportControls.size()>0) {
        for (int i = 0; i < reportControls.size(); ++i) {
            GenericReportControlView reportControl = (GenericReportControlView) reportControls.get(i);
            if ("LOCATE_ACCOUNT_MULTI".equalsIgnoreCase(reportControl.getName()) ||
		"LOCATE_ACCOUNT_MULTI_OPT".equalsIgnoreCase(reportControl.getName())) {
                useLocateAccountControl = true;
               // break;
            }
            if (reportControl.getName().toUpperCase().startsWith("DW_LOCATE")) {
                useLocateAccountControl = true;
                useDW = true;
               // break;
            }
            if (reportControl.getName().toUpperCase().contains("DATE")) {
                hasDateControls = true;
            }
            if ("LOCATE_CATALOG_MULTI".equalsIgnoreCase(reportControl.getName()) ||
            		"LOCATE_CATALOG_MULTI_OPT".equalsIgnoreCase(reportControl.getName())) {
                            useLocateCatalogControl = true;
            }
        }
    }
}
%>
<table cellpadding="2" cellspacing="0" border="0" width="750" class="mainbody">
<tbody>
<%/*
    <tr>
        <td colspan="3" align="left" class='mainbody'>
            <jsp:include flush='true' page="f_reportingMsg.jsp"/>
        </td>
    </tr>
*/%>
    <tr>
        <td colspan="3" align="left" class='mainbody'>
		<!-- analyticRepLocateStoreSite.jsp-->
            <jsp:include flush='true' page="/storeportal/locateStoreSite.jsp">
                <jsp:param name="jspFormAction"     value="/reporting/analyticRep.do" />
                <jsp:param name="jspFormName"   value="ANALYTIC_REPORT_FORM" />
                <jsp:param name="jspSubmitIdent"    value="" />
                <jsp:param name="isSingleSelect"    value="true" />
                <jsp:param name="jspReturnFilterPropertyAlt" value="siteDummyConvert"/>
                <jsp:param name="jspReturnFilterProperty" value="siteFilter"/>
            </jsp:include>
        </td>
    </tr>
    <tr>
        <td colspan="3" align="left" class='mainbody'>
            <jsp:include flush='true' page="/reporting/analyticRepLocateStoreSite.jsp">
                <jsp:param name="jspFormAction"     value="/reporting/analyticRep.do" />
                <jsp:param name="jspFormName"   value="ANALYTIC_REPORT_FORM" />
                <jsp:param name="jspSubmitIdent"    value="" />
                <jsp:param name="isSingleSelect"    value="true" />
                <jsp:param name="jspReturnFilterPropertyAlt" value="siteDummyConvert"/>
                <jsp:param name="jspReturnFilterProperty" value="siteFilter"/>

                <jsp:param name="jspDataSourceType" value="<%=(useDW) ? \"DW\":\"\"%>"/>
            </jsp:include>
        </td>
    </tr>
    <tr>
        <td colspan="3" align="left" class='mainbody'>
            <%
            if (useLocateAccountControl) {
            %>
            <jsp:include flush='true' page="locateReportAccount.jsp">
                <jsp:param name="jspFormAction"            value="/reporting/analyticRep.do"/>
                <jsp:param name="jspFormName"              value="ANALYTIC_REPORT_FORM"/>
                <jsp:param name="jspSubmitIdent"           value=""/>
                <jsp:param name="jspReturnFilterProperty"  value="accountFilter"/>
                <jsp:param name="checkBoxShowInactive"     value="hide"/>
                <jsp:param name="jspHtmlFormId"            value="LOCATE_ACCOUNT_FORM_ID"/>
                <jsp:param name="jspReportIdName"          value="id"/>
                <jsp:param name="jspReportIdValue"         value="<%=Integer.toString(theForm.getReportId())%>"/>
                <jsp:param name="jspDataSourceType"        value="<%=(useDW) ? \"DW\":\"\"%>"/>

            </jsp:include>
            <%
            }
            %>
        </td>
    </tr>
    <tr>
        <td colspan="3" align="left" class='mainbody'>
          <jsp:include flush='true' page="locateReportItem.jsp">
          <jsp:param name="jspFormAction" 	value="/reporting/analyticRep.do" />
          <jsp:param name="jspFormName" 	value="ANALYTIC_REPORT_FORM" />
          <jsp:param name="jspSubmitIdent" 	value="" />
          <jsp:param name="jspReturnFilterProperty" value="itemFilter"/>
          <jsp:param name="jspCatalogListProperty" value="catalogListSelectedCatItems"/>
          <jsp:param name="jspDataSourceType" value="<%=(useDW) ? \"DW\":\"\"%>"/>
          </jsp:include>
        </td>
    </tr>
    <tr>
        <td colspan="3" align="left" class='mainbody'>
          <jsp:include flush='true' page="/storeportal/locateStoreDistributor.jsp">
          <jsp:param name="jspFormAction" 	value="/reporting/analyticRep.do"/>
          <jsp:param name="jspFormName" 	value="ANALYTIC_REPORT_FORM" />
          <jsp:param name="jspSubmitIdent" 	value="" />
          <jsp:param name="jspReturnFilterPropertyAlt" value="distDummyConvert"/>
          <jsp:param name="jspReturnFilterProperty" value="distFilter"/>
          <jsp:param name="jspDataSourceType" value="<%=(useDW) ? \"DW\":\"\"%>"/>
          </jsp:include>
        </td>
    </tr>
    <tr>
        <td colspan="3" align="left" class='mainbody'>
          <jsp:include flush='true' page="/storeportal/locateStoreManufacturer.jsp">
          <jsp:param name="jspFormAction" 	value="/reporting/analyticRep.do" />
          <jsp:param name="jspFormName" 	value="ANALYTIC_REPORT_FORM" />
          <jsp:param name="jspSubmitIdent" 	value="" />
          <jsp:param name="jspReturnFilterPropertyAlt" value="manufDummyConvert"/>
          <jsp:param name="jspReturnFilterProperty" value="manufFilter"/>
          <jsp:param name="jspDataSourceType" value="<%=(useDW) ? \"DW\":\"\"%>"/>
          </jsp:include>
        </td>
    </tr>
    <tr>
    	<td colspan="3" align="left" class='mainbody'>
    	<% if (useLocateCatalogControl) { %>        
          <jsp:include flush='true' page="locateReportCatalog.jsp">
          <jsp:param name="jspFormAction" 	value="/reporting/analyticRep.do" />
          <jsp:param name="jspFormName" 	value="ANALYTIC_REPORT_FORM" />
          <jsp:param name="jspSubmitIdent" 	value="" />
          <jsp:param name="jspReturnFilterProperty" value="catalogFilter"/>
          </jsp:include>
        <% } %>
        </td>
		
<html:form styleId="ANALYTIC_REPORT_FORM_ID" name="ANALYTIC_REPORT_FORM" action="reporting/analyticRep.do" scope="session" type="com.cleanwise.view.forms.AnalyticReportForm">
<html:hidden property="id" value="<%=Integer.toString(theForm.getReportId())%>"/>
  <tr>
       <td colspan="3" align="left" class='rptmid_blue'>
       <%=theForm.getReport().getName()%>
       </td>
  </tr>
    <% String longDesc = theForm.getReport().getLongDesc();
       if(longDesc!=null) {
    %>
    <tr>
       <td colspan="3" align="left">
       <%=longDesc%>
       </td>
    </tr>
  <% } %>

 <!-- Parameter Control  -->
  <%
      String controls = "";
      GenericReportControlViewVector grcVV = theForm.getGenericControls();

      if(grcVV!=null && grcVV.size()>0) {

          for(int ii=0; ii<grcVV.size(); ii++) {
              GenericReportControlView grc = (GenericReportControlView) grcVV.get(ii);
              String controlEl = "genericControlValue["+ii+"]";
              
              String name = grc.getName();
              String label = grc.getLabel();
              if (grc.getInvisible() != null && grc.getInvisible().booleanValue()){
              %>
            	  <html:hidden property="<%=controlEl%>" value="<%=grc.getValue()%>"/>
              <% continue;}
              if(label!=null && label.length()==0) label=null;
              boolean mandatoryFl=true;
              String mf = grc.getMandatoryFl();

              if(mf!=null){
                  mf = mf.trim().toUpperCase();
              }

              if("N".equals(mf) || "NO".equals(mf) ||"0".equals(mf) ||"F".equals(mf) ||"FALSE".equals(mf)){
                  mandatoryFl = false;
              }

              if(name.endsWith("_OPT")){
                  mandatoryFl = false;
              }
              String rowClass = "genericControlRowB";
              if ( ( ii % 2 ) == 0 ) {
                rowClass = "genericControlRowA";
              }
if("BEG_DATE".equalsIgnoreCase(name)||"BEG_DATE_OPT".equalsIgnoreCase(name)){
  %>

 <tr > <td>&nbsp;</td>
     <%
         mandatoryFl=true;
         if(name.endsWith("_OPT")){
             mandatoryFl=false;
         }
     %>
       <td><b><%=(label==null)?"Begin Date":label%>
       (
       <%=ClwI18nUtil.getUIDateFormat(request)%>
       ):
       </b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
           <%
               if(mandatoryFl) {
           %>
           <span class="reqind">*</span><b>&nbsp;&nbsp;<%=dateBegConst%> </b>
           <%
               }
           %>
       </td>
  </tr>

  <%
      for(int jj=0; jj<grcVV.size(); jj++) {
          GenericReportControlView grc1 = (GenericReportControlView) grcVV.get(jj);
          String name1 = grc1.getName();
          if("END_DATE".equalsIgnoreCase(name1) || "END_DATE_OPT".equalsIgnoreCase(name1)) {
              String label1 = grc1.getLabel();
              if(label1!=null && label1.length()==0){
                  label1=null;
              }
              boolean mandatoryFl1=true;
              if(name1.endsWith("OPT")){
                  mandatoryFl1=false;
              }
              String mf1 = grc1.getMandatoryFl();
              if(mf!=null){
                  mf1 = mf1.trim().toUpperCase();
              }
              if("N".equals(mf1) || "NO".equals(mf1) ||"0".equals(mf1) ||"F".equals(mf1) ||"FALSE".equals(mf1)) mandatoryFl1 = false;
              String controlEl1 = "genericControlValue["+jj+"]";
  %>
 <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"End Date":label1%>
       (
       <%=ClwI18nUtil.getUIDateFormat(request)%>
       ):
       </b></td>
       </b></td><td>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl1%>" />
         <%if(mandatoryFl1) { %>
         <span class="reqind">*</span><b>&nbsp;&nbsp;<%=dateEndConst%> </b> <%}%></td></tr>


         <%
                 break;
             }
         }
     } else if("END_DATE".equalsIgnoreCase(name)) {
     } else if("END_DATE_OPT".equalsIgnoreCase(name)) {

} else if("STORE".equalsIgnoreCase(name) || "STORE_OPT".equalsIgnoreCase(name)) { %>
<% if(adminFl) {                                       %>
<%    if ("STORE".equalsIgnoreCase(name) ){ %>
         <html:hidden  name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=storeIdS%>" />
     <%} else  { %>
<!-- Store -->
  <tr> <td>&nbsp;</td>
      <td><b><%=(label==null)?"Store:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/storelocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
       <html:button property="locateStore"
                    onclick="<%=onClick%>"
                    value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Store\"%>"/>
        </td>
  </tr>
  <%  } %>
  <% } else {  %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=storeIdS%>" />
  <%} %>
 <% } else if("STORE_MULTI".equalsIgnoreCase(name)||"STORE_MULTI_OPT".equalsIgnoreCase(name)) { %>
<% if(adminFl) {       %>
<%    if ("STORE_MULTI".equalsIgnoreCase(name) ){ %>
           <html:hidden  name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=storeIdS%>" />
       <%} else  {%>
<!-- Store -->
      <tr> <td>&nbsp;</td>
           <td><b><%=(label==null)?"Store(s):":label%></b></td>
           <td>
           <% String onClick = "popLocate('../adminportal/storeLocateMulti', '"+controlEl+"', '');";%>
           <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
           <html:button property="locateStore"
                        onclick="<%=onClick%>"
                        value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Store(s)\"%>"/>

           </td>
      </tr>
  <%  } %>
<% } else { %>
     <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>"  value="<%=storeIdS%>" />
<% } %>
<% } else if("ALLOW_UPDATES".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
  <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="false" />
<% } %>
<% } else if("GROUP_OPT".equalsIgnoreCase(name) || "GROUP".equalsIgnoreCase(name)) {
   if(adminFl && ! user.isaStoreAdmin()) {
%>
<!-- Group -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Group:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/grouplocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
       <html:button property="locateGroup"
                    onclick="<%=onClick%>"
                    value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Group\"%>"/>
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<% } else if("ACCOUNT".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Account -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Account:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/accountlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
       <html:button property="locateAccount"
                    onclick="<%=onClick%>"
                    value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Account\"%>"/>
        </td>
  </tr>
<% } else {
if(acctBusEntDV != null && acctBusEntDV.size()==1) {

    BusEntityData beD = (BusEntityData) acctBusEntDV.get(0);
    accountIdS = String.valueOf(beD.getBusEntityId());
%>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>"
   value="<%=accountIdS%>"/>
<%
} else {
%>
  <tr> <td>&nbsp;</td>
     <td><b><%=(label==null)?"Account:":label%></b></td>
     <td>
     <html:select name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>">
       <% for(int kk=0; kk<acctBusEntDV.size(); kk++) {
         BusEntityData beD = (BusEntityData) acctBusEntDV.get(kk);
         String shortDesc = beD.getShortDesc();
         String acctIdS = ""+beD.getBusEntityId();
       %>
      <html:option value="<%=acctIdS%>"><%=shortDesc%></html:option>
      <% } %>
    </html:select>
    </td>
  </tr>
<% } %>
<% } %>
<% } else if("STORE_SELECT".equalsIgnoreCase(name)) { %>
<!-- Store_Select -->
	<%if (multiStores) { %>
		<tr>
			<td>&nbsp;</td>
			<td>
				<b><%=(label==null)?"Select Store:":label%></b>
			</td>
			<td>
<!-- store dropdown list-->
                          <select name="<%=controlEl%>" onchange="setAndSubmit('ANALYTIC_REPORT_FORM_ID','command','saveSelectedStore');">
                          <%  HashMap controlFiltersMap = (HashMap) request.getSession().getAttribute("REPORT_CONTROL_FILTER_MAP");
                                  Integer selectedStoreIdInt = null;
                                  if (controlFiltersMap != null) {
                                     Object elValue = controlFiltersMap.get("STORE_SELECT");
                                     if (elValue != null){
                                       if (elValue instanceof Integer) {
                                         selectedStoreIdInt = (Integer)elValue;
                                       }else{
                                         selectedStoreIdInt = Integer.valueOf(elValue.toString());
                                       }
                                     }

                                  }
                                  String selectedStoreId = (selectedStoreIdInt != null) ? selectedStoreIdInt.toString() : "0";

                                  BusEntityDataVector userStoresDV = user.getStores();
                                  for(int j = 0; j < userStoresDV.size(); j++) {
                                          BusEntityData beD = (BusEntityData) userStoresDV.get(j);
                                          String shortDesc = beD.getShortDesc();
                                          String storeIdStr = "" + beD.getBusEntityId();

                                          if (selectedStoreId.equals(storeIdStr)) { %>
                                                  <option value="<%=storeIdStr%>" selected="true"><%=shortDesc%></option>
                                          <% } else {%>
                                                  <option value="<%=storeIdStr%>"><%=shortDesc%></option>
                                          <% }
                                  } %>
                          </select>
                        <%if(mandatoryFl) { %> &nbsp;<span class="reqind">*</span> <% } %>
                        </td>
		</tr>
	<%  } else { %>
			<html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=storeIdS%>"/>
	<%  } %>
<% } else if("INVOICE_STATUS_SELECT_OPT".equalsIgnoreCase(name) || "INVOICE_STATUS_SELECT".equalsIgnoreCase(name)) { %>
<!-- Invoice_Status_Select -->
		<tr>
			<td>&nbsp;</td>
			<td>
				<b><%=(label==null)?"Invoice Status:":label%></b>
			</td>
			<td>
<!-- Invoice Status Code dropdown list-->
            <html:select property="invoiceStatus" value="">
                          <%  HashMap controlFiltersMap = (HashMap) request.getSession().getAttribute("REPORT_CONTROL_FILTER_MAP");
                                  String selectedInvoiceStatusString = null;
                                  if (controlFiltersMap != null) {
                                     Object elValue = controlFiltersMap.get("INVOICE_STATUS_SELECT_OPT");
                                     if (elValue != null){
                                       if (elValue instanceof String) {
                                    	   selectedInvoiceStatusString = (String)elValue;
                                       }else{
                                         //selectedStoreIdInt = Integer.valueOf(elValue.toString());
                                    	   selectedInvoiceStatusString = elValue.toString();
                                       }
                                     }
                                  }
                                  if (selectedInvoiceStatusString == null) {
                                	  selectedInvoiceStatusString = "";
                                  }

                                  for(int j = 0; j < invoiceStatusCodeDV.size(); j++) {
                                          String invoiceStatusCode = (String) invoiceStatusCodeDV.get(j);

                           %>     
                                          <html:option value="<%=invoiceStatusCode%>"/>
                                         
                                  <% } %>
            </html:select>
             </td>
		</tr>
<% } else if("DW_STORE_SELECT".equalsIgnoreCase(name)) { %>
<!-- Store_Select from Data Werehouse -->
	<%if (multiStores) { %>
       <tr  >
        <%@ include file="f_dwStoreSelectControl.jsp" %>
       </tr>
 	<%  } else { %>
           <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=storeIdS%>"/>
	<%  } %>

<% } else if("DW_USER_SELECT".equalsIgnoreCase(name)) { %>
<% } else if("DW_BEGIN_DATE".equalsIgnoreCase(name) || "DW_BEGIN_DATE_OPT".equalsIgnoreCase(name) ) { %>
<!-- Begin/End Date For Data Werehouse -->
      <tr >
          <%@ include file="f_dwDateControl.jsp"  %>
       </tr>
<% } else if("ACCOUNT_OPT".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Account Optional-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Account:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/accountlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
       <html:button property="locateAccount"
                    onclick="<%=onClick%>"
                    value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Account\"%>"/>
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=accountIdS%>"/>
<% } %>
<% } else if("ACCOUNT_MULTI_OPT".equalsIgnoreCase(name) || "ACCOUNT_MULTI".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Account Optional-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Account(s):":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/accountLocateMulti', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" size='60' />
       <%if(mandatoryFl) { %>&nbsp;<span class="reqind">*</span> <%}%>
       <html:button property="locateAccount"
                    onclick="<%=onClick%>"
                    value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Account(s)\"%>"/>
        </td>
  </tr>
<% } else if ( user.isaReportingUser()) { %>

<%
if(acctBusEntDV.size()==1) {

    BusEntityData beD = (BusEntityData) acctBusEntDV.get(0);
    accountIdS = String.valueOf(beD.getBusEntityId());
%>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>"
   value="<%=accountIdS%>"/>
<%
} else {
%>
  <tr><td>&nbsp;</td><td>&nbsp;</td><td>
<a href="javascript:SetCheckedGlobal(1,'runForAccounts')">[Check&nbsp;All]</a><br>
<a href="javascript:SetCheckedGlobal(0,'runForAccounts')">[&nbsp;Clear]</a>
  </td>
  <tr> <td>&nbsp;</td>
     <td><b><%=(label==null)?"Account:":label%></b></td>
     <td>
<%
for(int kk=0; kk<acctBusEntDV.size(); kk++) {
         BusEntityData beD = (BusEntityData) acctBusEntDV.get(kk);
         String shortDesc = beD.getShortDesc();
         String acctIdS = ""+beD.getBusEntityId();
%>
<br><html:multibox name="ANALYTIC_REPORT_FORM"   property="runForAccounts"
  value="<%=acctIdS%>"/><%=shortDesc%>

<% } %>
    </td>
</tr>
<% } %>


<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=accountIdS%>"/>
<% } %>
<%
  } else if("LOCATE_ACCOUNT_MULTI".equalsIgnoreCase(name) ||
	    "LOCATE_ACCOUNT_MULTI_OPT".equalsIgnoreCase(name) ||
            "DW_LOCATE_ACCOUNT_MULTI".equalsIgnoreCase(name) ||
	    "DW_LOCATE_ACCOUNT_MULTI_OPT".equalsIgnoreCase(name)) {

        AccountUIViewVector accountDataVector = theForm.getAccountFilter();
%>
<html:hidden property="<%=RequestPropertyNames.PROPERTY_NAME_LOCATE_ACCOUNT_TYPE%>" value="<%=RequestPropertyNames.PROPERTY_VALUE_LOCATE_ACCOUNT_REPORT%>"/>
    <tr class="<%=rowClass%>" >
	    <td>&nbsp;</td>
        <td><b><%=(label==null)?"Account Filter":label%></b></td>
		<td>
            <%
            if (accountDataVector != null && accountDataVector.size() > 0) {
                for (int i = 0; i < accountDataVector.size(); ++i) {
                    AccountUIView accountData = (AccountUIView)accountDataVector.get(i);
            %>
            &lt;<%=accountData.getBusEntity().getShortDesc()%>&gt;
            <%
                }
            %>
            <html:button styleClass='text' property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Locate Account')">
                <%=Utility.isSet(grc.getAdditionalLabel1()) ? grc.getAdditionalLabel1() : "Locate Account(s)"%>
            </html:button>&nbsp;
            <html:button styleClass='text' property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Clear Account Filter')">
                <%=Utility.isSet(grc.getAdditionalLabel2()) ? grc.getAdditionalLabel2() : "Clear Account Filter"%>
            </html:button>
            <%
            } else {
            %>
            <html:button styleClass='text' property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Locate Account')">
                <%=Utility.isSet(grc.getAdditionalLabel1()) ? grc.getAdditionalLabel1() : "Locate Account(s)"%>
            </html:button>
            <% } %>
			<%if(mandatoryFl) { %>&nbsp;<span class="reqind">*</span> <%}%>
        </td>
    </tr>
<% } else if("DISTRIBUTOR".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Distributor -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Distributor:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/distlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
             <html:button property="locateDistributor"
                          onclick="<%=onClick%>"
                          value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Distributor\"%>"/>
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<% } else if("DISTRIBUTOR_OPT".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Distributor Opt-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Distributor:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/distlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
             <html:button property="locateDistributor"
                          onclick="<%=onClick%>"
                          value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Distributor\"%>"/>
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<% } else if("DISTRIBUTOR_MULTI_OPT".equalsIgnoreCase(name) ||
             "LOCATE_DISTRIBUTOR_MULTI_OPT".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Distributor Multi Opt-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Distributor(s):":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/distLocateMulti', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
             <html:button property="locateDistributor(s)"
                          onclick="<%=onClick%>"
                          value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Distributor(s)\"%>"/>
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<% } else if("MANUFACTURER".equalsIgnoreCase(name) || "MANUFACTURER_OPT".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Manufacturer -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Manufacturer:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/manuflocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
             <html:button property="locateManufacturer"
                          onclick="<%=onClick%>"
                          value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Manufacturer\"%>"/>
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<% } else if("MANUF_MULTI_OPT".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Manufacturer Multi Opt-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Manufacturer(s):":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/manufLocateMulti', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
             <html:button property="locateManufacturer(s)"
                          onclick="<%=onClick%>"
                          value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Manufacturer(s)\"%>"/>
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<% } else if("ITEM".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Item -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Item:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/itemlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
             <html:button property="locateItem"
                          onclick="<%=onClick%>"
                          value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Item\"%>"/><br>
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<% } else if("ITEM_OPT".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Item Opt-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Item:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/itemlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
             <html:button property="locateItem"
                          onclick="<%=onClick%>"
                          value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Item\"%>"/><br>
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<!--////////////////////////////-->
<% } else if("SKU_MULTI_OPT".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Sku Multi Opt-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Sku(s):":label%></b></td>
       <td>
       <html:text name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" size='60' />
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<!--////////////////////////////-->
<% } else if("CONTRACT".equalsIgnoreCase(name)||"CONTRACT_OPT".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Contract -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Contract:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/contractlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
         <html:button property="locateContract"
                          onclick="<%=onClick%>"
                          value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Contract\"%>"/><br>
 </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<% } else if("CATALOG".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- Catalog -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Catalog:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/cataloglocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
             <html:button property="locateCatalog"
                          onclick="<%=onClick%>"
                          value="<%=Utility.isSet(grc.getAdditionalLabel1())?grc.getAdditionalLabel1():\"Locate Catalog\"%>"/><br>
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<% } else if("SITE".equalsIgnoreCase(name)) {

%>
<!-- Site -->
  <tr> <td>&nbsp;</td>
  <td><b><%=(label==null)?"Site:":label%></b></td>
  <td>

  <% String selectedSiteId="";
      String selectedSiteName="";
      SiteViewVector siteFilter=theForm.getSiteFilter();

      if(adminFl){
       if(siteFilter!=null && siteFilter.size()>0)
       selectedSiteId=String.valueOf(((SiteView)siteFilter.get(0)).getId());%>
      <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=selectedSiteId%>" />
      <%} else {
      if(siteFilter!=null && siteFilter.size()>0)
      {
     selectedSiteName=String.valueOf(((SiteView)siteFilter.get(0)).getName());
     selectedSiteId=String.valueOf(((SiteView)siteFilter.get(0)).getId());
      }  %>
      <%=selectedSiteName%>
    <html:hidden  name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=selectedSiteId%>"/>
   <%}%>
   <%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
      <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Locate Site')">
          <%=Utility.isSet(grc.getAdditionalLabel1()) ? grc.getAdditionalLabel1() : "Locate Site"%>
      </html:button>
  <%if(siteFilter!=null&&siteFilter.size()>0){%>
      <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Clear Site Filter')">
          <%=Utility.isSet(grc.getAdditionalLabel2()) ? grc.getAdditionalLabel2() : "Clear Site Filter"%>
      </html:button>
  <%}%> <br>

 </td>
     </tr>

  <%
  }  else if ("SITE_MULTI".equalsIgnoreCase(name)){
  %>

  <!-- Site_Opt -->
  <tr> <td>&nbsp;</td>
      <td><b><%=(label==null)?"Site(s):":label%></b></td>
      <td>
          <%
              SiteViewVector siteFilter=theForm.getSiteFilter();
              String selectedSitesId="";
              String selectedSitesName="";

              if(siteFilter!=null&&siteFilter.size()>0){
                  for(int i=0; i<siteFilter.size(); i++){

                      if(i!=0)
                      {
                          selectedSitesId=selectedSitesId.concat(",");
                          selectedSitesName=selectedSitesName.concat(", ");
                      }

                      selectedSitesId=selectedSitesId.concat(String.valueOf(((SiteView)siteFilter.get(i)).getId()));
                      selectedSitesName=selectedSitesName.concat(String.valueOf(((SiteView)siteFilter.get(i)).getName()));
                  }

              }
              if(adminFl){
          %>
          <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=selectedSitesId%>"/>
          <%
          } else {
          %>
          <%=selectedSitesName%>
          <html:hidden  name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=selectedSitesId%>"/>
          <%
              }
              if(mandatoryFl) {
          %>
          <span class="reqind">*</span>
          <%
              }
          %>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Locate Site')">
              <%=Utility.isSet(grc.getAdditionalLabel1()) ? grc.getAdditionalLabel1() : "Locate Site"%>
          </html:button>
          <%if(siteFilter!=null&&siteFilter.size()>0){%>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Clear Site Filter')">
              <%=Utility.isSet(grc.getAdditionalLabel2()) ? grc.getAdditionalLabel2() : "Clear Site Filter"%>
          </html:button>
          <%}%><br>

      </td>
  </tr>

  <%
  } else if("SITE_OPT".equalsIgnoreCase(name)) {
  %>
  <!-- Site_Opt -->
  <tr> <td>&nbsp;</td>
      <td><b><%=(label==null)?"Site:":label%></b></td>
      <td>

          <%
              String selectedSiteId="";
              String selectedSiteName="";
              SiteViewVector siteFilter=theForm.getSiteFilter();

              if(adminFl){
                  if(siteFilter!=null && siteFilter.size()>0)
                      selectedSiteId=String.valueOf(((SiteView)siteFilter.get(0)).getId());
          %>
          <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=selectedSiteId%>" />
          <%
          } else {
              if(siteFilter!=null && siteFilter.size()>0)
              {
                  selectedSiteName=String.valueOf(((SiteView)siteFilter.get(0)).getName());
                  selectedSiteId=String.valueOf(((SiteView)siteFilter.get(0)).getId());
              }
          %>
          <%=selectedSiteName%>
          <html:hidden  name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=selectedSiteId%>"/>
          <%
              }
          %>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Locate Site')">
              <%=Utility.isSet(grc.getAdditionalLabel1()) ? grc.getAdditionalLabel1() : "Locate Site"%>
          </html:button>
          <%
              if(siteFilter!=null&&siteFilter.size()>0){
          %>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Clear Site Filter')">
              <%=Utility.isSet(grc.getAdditionalLabel2()) ? grc.getAdditionalLabel2() : "Clear Site Filter"%>
          </html:button>
          <%
              }
          %>
          <br>
      </td>
  </tr>

  <%
  }else if ("SITE_MULTI_OPT".equalsIgnoreCase(name)){
  %>

  <!-- Site_Multi_Opt -->
  <tr> <td>&nbsp;</td>
      <td><b><%=(label==null)?"Site(s):":label%></b></td>
      <td>
          <%
              String selectedSitesId="";
              String selectedSitesName="";
              SiteViewVector siteFilter=theForm.getSiteFilter();

              if(siteFilter!=null&&siteFilter.size()>0){

                  for(int i=0;i<siteFilter.size();i++){

                      if(i!=0)
                      {
                          selectedSitesId=selectedSitesId.concat(",");
                          selectedSitesName=selectedSitesName.concat(", ");
                      }

                      selectedSitesId=selectedSitesId.concat(String.valueOf(((SiteView)siteFilter.get(i)).getId()));
                      selectedSitesName=selectedSitesName.concat(String.valueOf(((SiteView)siteFilter.get(i)).getName()));
                  }

              }
              if(adminFl){
          %>
          <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=selectedSitesId%>"/>
          <%
          } else {
          %>
          <%=selectedSitesName%>
          <html:hidden  name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=selectedSitesId%>"/>
          <%
              }
          %>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Locate Site')">
              <%=Utility.isSet(grc.getAdditionalLabel1()) ? grc.getAdditionalLabel1() : "Locate Site"%>
          </html:button>
          <%
              if(siteFilter!=null&&siteFilter.size()>0){
          %>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Clear Site Filter')">
              <%=Utility.isSet(grc.getAdditionalLabel2()) ? grc.getAdditionalLabel2() : "Clear Site Filter"%>
          </html:button>
          <%
              }
          %>
          <br>
      </td>
  </tr>
  <%
  }else if ("LOCATE_SITE_MULTI_OPT".equalsIgnoreCase(name) ||
            "DW_LOCATE_SITE_MULTI".equalsIgnoreCase(name) ||
	    "DW_LOCATE_SITE_MULTI_OPT".equalsIgnoreCase(name)) {

 %>
  <html:hidden property="<%=RequestPropertyNames.PROPERTY_NAME_LOCATE_SITE_TYPE%>" value="<%=RequestPropertyNames.PROPERTY_VALUE_LOCATE_SITE_REPORT%>"/>

  <!-- Site_Multi_Opt -->
  <tr class="<%=rowClass%>" > <td>&nbsp;</td>
      <td><b><%=(label==null)?"Site:":label%></b></td>
      <td>
          <%
              String selectedSitesId="";
              String selectedSitesName="";
              SiteViewVector siteFilter=theForm.getSiteFilter();

              if(siteFilter!=null&&siteFilter.size()>0){

                  for(int i=0;i<siteFilter.size();i++){

                      if(i!=0)
                      {
                          selectedSitesId=selectedSitesId.concat(",");
                          selectedSitesName=selectedSitesName.concat(", ");
                      }

                      selectedSitesId=selectedSitesId.concat(String.valueOf(((SiteView)siteFilter.get(i)).getId()));
                      selectedSitesName=selectedSitesName.concat(String.valueOf(((SiteView)siteFilter.get(i)).getName()));
                  }

              }
          %>
          <%=selectedSitesName%>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Locate Site')">
              <%=Utility.isSet(grc.getAdditionalLabel1()) ? grc.getAdditionalLabel1() : "Locate Site"%>
          </html:button>&nbsp;
          <%
              if(siteFilter!=null&&siteFilter.size()>0){
          %>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Clear Site Filter')">
              <%=Utility.isSet(grc.getAdditionalLabel2()) ? grc.getAdditionalLabel2() : "Clear Site Filter"%>
          </html:button>
          <%
              }
          %>
          <br>
      </td>
  </tr>
  <%
  }else if ("LOCATE_ITEM_OPT".equalsIgnoreCase(name) ||
            "DW_LOCATE_ITEM".equalsIgnoreCase(name) ||
            "DW_LOCATE_ITEM_OPT".equalsIgnoreCase(name)){
  %>
  <html:hidden property="<%=RequestPropertyNames.PROPERTY_NAME_LOCATE_ITEM_TYPE%>" value="<%=RequestPropertyNames.PROPERTY_VALUE_LOCATE_ITEM_REPORT%>"/>

  <!-- Item_Multi_Opt -->
  <tr class="<%=rowClass%>" > <td>&nbsp;</td>
      <td><b><%=(label==null)?"Item:":label%></b></td>
      <td>
          <%
              String selectedItemsId="";
              String selectedItemsName="";
              ItemViewVector itemFilter=theForm.getItemFilter();

              if(itemFilter!=null&&itemFilter.size()>0){

                  for(int i=0;i<itemFilter.size();i++){

                      if(i!=0)
                      {
                          selectedItemsId=selectedItemsId.concat(",");
                          selectedItemsName=selectedItemsName.concat(", ");
                      }

                      selectedItemsId=selectedItemsId.concat(String.valueOf(((ItemView)itemFilter.get(i)).getItemId()));
                      String itemName = String.valueOf(((ItemView)itemFilter.get(i)).getSku()) + " " +
                                        String.valueOf(((ItemView)itemFilter.get(i)).getName());
                      selectedItemsName=selectedItemsName.concat(itemName);
                  }

              }
          %>
          <%=selectedItemsName%>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Locate Item')">
              <%=Utility.isSet(grc.getAdditionalLabel1()) ? grc.getAdditionalLabel1() : "Locate Item"%>
          </html:button>&nbsp;
          <%
              if(itemFilter!=null&&itemFilter.size()>0){
          %>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Clear Item Filter')">
              <%=Utility.isSet(grc.getAdditionalLabel2())?grc.getAdditionalLabel2():"Clear Item Filter"%>
          </html:button>
          <%
              }
          %>
          <br>
      </td>
  </tr>
<%
  }else if ("DW_LOCATE_DISTRIBUTOR".equalsIgnoreCase(name) ||
            "DW_LOCATE_DISTRIBUTOR_OPT".equalsIgnoreCase(name)){
  %>
  <html:hidden property="<%=RequestPropertyNames.PROPERTY_NAME_LOCATE_DISTRIBUTOR_TYPE%>" value="<%=RequestPropertyNames.PROPERTY_VALUE_LOCATE_DISTRIBUTOR_REPORT%>"/>

  <!-- DISTRIBUTOR_Multi_Opt -->
  <tr class="<%=rowClass%>" > <td>&nbsp;</td>
      <td><b><%=(label==null)?"Distributor:":label%></b></td>
      <td>
          <%
              String selectedDistId="";
              String selectedDistName="";
              DistributorDataVector distFilter=theForm.getDistFilter();

              if(distFilter!=null&&distFilter.size()>0){

                  for(int i=0;i<distFilter.size();i++){

                      if(i!=0)
                      {
                          selectedDistId=selectedDistId.concat(",");
                          selectedDistName=selectedDistName.concat(", ");
                      }

                      selectedDistId=selectedDistId.concat(String.valueOf(((DistributorData)distFilter.get(i)).getBusEntity().getBusEntityId()));
                      selectedDistName=selectedDistName.concat(String.valueOf(((DistributorData)distFilter.get(i)).getBusEntity().getShortDesc()));
                  }

              }
          %>
          <%=selectedDistName%>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Locate Distributor')">
              <%=Utility.isSet(grc.getAdditionalLabel1()) ? grc.getAdditionalLabel1() : "Locate Distributor"%>
          </html:button>&nbsp;
          <%
              if(distFilter!=null&&distFilter.size()>0){
          %>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Clear Distributor Filter')">
              <%=Utility.isSet(grc.getAdditionalLabel2())?grc.getAdditionalLabel2():"Clear Distributor Filter"%>
          </html:button>
          <%
              }
          %>
          <br>
      </td>
  </tr>
<%
  }else if ("LOCATE_MANUFACTURER_OPT".equalsIgnoreCase(name) ||
            "DW_LOCATE_MANUFACTURER".equalsIgnoreCase(name) ||
            "DW_LOCATE_MANUFACTURER_OPT".equalsIgnoreCase(name)){
  %>
  <html:hidden property="<%=RequestPropertyNames.PROPERTY_NAME_LOCATE_MANUF_TYPE%>" value="<%=RequestPropertyNames.PROPERTY_VALUE_LOCATE_MANUF_REPORT%>"/>

  <!-- Locate_Manufacturer_Opt -->
  <tr class="<%=rowClass%>" > <td>&nbsp;</td>
      <td><b><%=(label==null)?"Manufacturer:":label%></b></td>
      <td>
          <%
              String selectedManufId="";
              String selectedManufName="";
              ManufacturerDataVector manufFilter=theForm.getManufFilter();

              if(manufFilter!=null&&manufFilter.size()>0){

                  for(int i=0;i<manufFilter.size();i++){

                      if(i!=0)
                      {
                          selectedManufId=selectedManufId.concat(",");
                          selectedManufName=selectedManufName.concat(", ");
                      }

                      selectedManufId=selectedManufId.concat(String.valueOf(((ManufacturerData)manufFilter.get(i)).getBusEntity().getBusEntityId()));
                      selectedManufName=selectedManufName.concat(String.valueOf(((ManufacturerData)manufFilter.get(i)).getBusEntity().getShortDesc()));
                  }

              }
          %>
          <%=selectedManufName%>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Locate Manufacturer')">
              <%=Utility.isSet(grc.getAdditionalLabel1()) ? grc.getAdditionalLabel1() : "Locate Manufacturer"%>
          </html:button>&nbsp;
          <%
              if(manufFilter!=null&&manufFilter.size()>0){
          %>
          <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Clear Manufacturer Filter')">
              <%=Utility.isSet(grc.getAdditionalLabel2())?grc.getAdditionalLabel2():"Clear Manufacturer Filter"%>
          </html:button>
          <%
              }
          %>
          <br>
      </td>
  </tr>
<% }else if ("LOCATE_CATALOG_MULTI_OPT".equalsIgnoreCase(name)){
%>
<html:hidden property="<%=RequestPropertyNames.PROPERTY_NAME_LOCATE_CATALOG_TYPE%>" value="<%=RequestPropertyNames.PROPERTY_VALUE_LOCATE_CATALOG_REPORT%>"/>

<!-- Catalog_Multi_Opt -->
<tr class="<%=rowClass%>" > <td>&nbsp;</td>
  <td><b><%=(label==null)?"Catalog:":label%></b></td>
  <td>
      <%
          String selectedCatalogsId="";
          String selectedCatalogsName="";
          CatalogDataVector catalogFilter=theForm.getCatalogFilter();

          if(catalogFilter!=null&&catalogFilter.size()>0){

              for(int i=0;i<catalogFilter.size();i++){

                  if(i!=0)
                  {
                      selectedCatalogsId=selectedCatalogsId.concat(",");
                      selectedCatalogsName=selectedCatalogsName.concat(", ");
                  }

                  selectedCatalogsId=selectedCatalogsId.concat(String.valueOf(((CatalogData)catalogFilter.get(i)).getCatalogId()));
                  String catalogName = ((CatalogData)catalogFilter.get(i)).getShortDesc();
                  selectedCatalogsName=selectedCatalogsName.concat(catalogName);
              }

          }
      %>
      <%=selectedCatalogsName%>
      <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Locate Catalog')">
          <%=Utility.isSet(grc.getAdditionalLabel1()) ? grc.getAdditionalLabel1() : "Locate Catalog"%>
      </html:button>&nbsp;
      <%
          if(catalogFilter!=null&&catalogFilter.size()>0){
      %>
      <html:button property="action" onclick="actionSubmit('ANALYTIC_REPORT_FORM_ID','action','Clear Catalog Filter')">
          <%=Utility.isSet(grc.getAdditionalLabel2())?grc.getAdditionalLabel2():"Clear Catalog Filter"%>
      </html:button>
      <%
          }
      %>
      <br>
  </td>
</tr>
<% } else if("CATEGORY_OPT".equalsIgnoreCase(name) ||
             "DW_CATEGORY_OPT".equalsIgnoreCase(name)) { %>
<!-- CategoryOpt -->
  <tr > <td>&nbsp;</td>
       <td><b><%=(label==null)?"Category:":label%></b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
        </td>
  </tr>
<% } else if("DW_DSR_OPT".equalsIgnoreCase(name)) { %>
<!-- Distributor_Sales_Rep_Opt -->
  <tr  > <td>&nbsp;</td>
       <td><b><%=(label==null)?" Distributor Sales Rep:":label%></b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
        </td>
  </tr>
<% } else if("DW_VERTICAL_OPT".equalsIgnoreCase(name)) { %>
<!-- Vertical Market Opt -->
  <tr  > <td>&nbsp;</td>
       <td><b><%=(label==null)?"Vertical Market:":label%></b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
        </td>
  </tr>
<% } else if("DW_REGION_OPT".equalsIgnoreCase(name)) { %>
<!-- Region_Opt -->
 <tr  > <td>&nbsp;</td>
       <td><b><%=(label==null)?" Region:":label%></b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
        </td>
  </tr>
<% } else if("DW_CONNECT_CUST_OPT".equalsIgnoreCase(name)) { %>
<!-- Connection_Customer_Opt -->
 <tr  > <td>&nbsp;</td>
       <td><b><%=(label==null)?" Connection Customer:":label%></b></td>
       <td>
        <html:checkbox  name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value = "Yes"/>&nbsp;

        </td>
  </tr>

<% } else if("DW_REGION_AUTOSUGGEST_OPT".equalsIgnoreCase(name) || "DW_DSR_AUTOSUGGEST_OPT".equalsIgnoreCase(name) || "DW_CATEGORY_AUTOSUGGEST_OPT".equalsIgnoreCase(name)) { %>
<!-- Region_Opt -->
  <script language="JavaScript1.2">
      if(typeof dojo == "undefined"){
          var djConfig = {parseOnLoad: true,isDebug: false, usePlainJson: true}
          document.write("<script type=\"text/javascript\" src=\"<%=request.getContextPath()%>/externals/dojo_1.1.0/dojo/dojo.js\"><"+"/script>")
      }
  </script>

  <script language="JavaScript1.2">

    dojo.requireLocalization("clw.JD.form", "AutoSuggestTextBox", null, "ROOT", "", "");
    dojo.require("dojo.data.ItemFileReadStore");

    var d = dojo;
    var theme;
    applyEvent = false;

    if (!theme) {
        theme = 'JD';
    }

    var themeCss = d.moduleUrl("clw.JD.themes", theme + "/" + theme + ".css");
    document.write('<link rel="stylesheet" type="text/css" href="' + themeCss + '"/>');

    d.addOnLoad(function() {
        if (!d.hasClass(d.body(), theme)) {
            d.addClass(d.body(), theme);
        }
    });
</script>
<%if("DW_REGION_AUTOSUGGEST_OPT".equalsIgnoreCase(name)) { %>
  <tr> <td>&nbsp;</td>
      <td><b><%=(label==null)?" Region:":label%></b></td>
      <td>
          <app:autoSuggestTextBox id="AutoSuggestRegion"
                                  name="ANALYTIC_REPORT_FORM"
                                  formId="ANALYTIC_REPORT_FORM_ID"
                                  property="<%=controlEl%>"
                                  module="clw.JD"
                                  action="autosuggestRegion"
                                  searchAttr="name"/>
      </td>
  </tr>
<% } else if("DW_DSR_AUTOSUGGEST_OPT".equalsIgnoreCase(name)) { %>
  <tr> <td>&nbsp;</td>
      <td><b><%=(label==null)?"Distributor Sales Rep:":label%></b></td>
      <td>
          <app:autoSuggestTextBox id="AutoSuggestDSR"
                                  name="ANALYTIC_REPORT_FORM"
                                  formId="ANALYTIC_REPORT_FORM_ID"
                                  property="<%=controlEl%>"
                                  module="clw.JD"
                                  action="autosuggestDSR"
                                  searchAttr="name"/>
      </td>
  </tr>
<% } else if("DW_CATEGORY_AUTOSUGGEST_OPT".equalsIgnoreCase(name)) { %>
  <tr> <td>&nbsp;</td>
      <td><b><%=(label==null)?"Category:":label%></b></td>
      <td>
          <app:autoSuggestTextBox id="AutoSuggestDWCategory"
                                  name="ANALYTIC_REPORT_FORM"
                                  formId="ANALYTIC_REPORT_FORM_ID"
                                  property="<%=controlEl%>"
                                  module="clw.JD"
                                  action="autosuggestDWCategory"
                                  searchAttr="name"/>
      </td>
  </tr>
<%}%>

<% } else if("COUNTY_OPT".equalsIgnoreCase(name)) {
      if(adminFl) {
%>
<!-- County Opt -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"County:":label%></b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<% } else if("COUNTY_OPT".equalsIgnoreCase(name)) {
      if(adminFl) {
%>
<!-- County Opt -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"County:":label%></b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
        </td>
  </tr>
<% } else { %>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="0"/>
<% } %>
<% } else if("STATE_OPT".equalsIgnoreCase(name)) {
   if(adminFl) {
%>
<!-- State Opt -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"State:":label%></b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" />
        </td>
  </tr>
<% } %>
<% } else if("CUSTOMER".equalsIgnoreCase(name) || "CUSTOMER_OPT".equalsIgnoreCase(name) ||
		"USER".equalsIgnoreCase(name) || "USER_OPT".equalsIgnoreCase(name)) {
/*Disable the user button for store admins as the security implications have not been thought out yet, 
they could spoof
themselves as being from anouther store to look at data they should not see, bypassing any security 
checks in the reports*/
   //if(adminFl && !user.isaStoreAdmin()) {
  if(adminFl || user.isaAccountAdmin()) {
%>
<!-- User to run report as -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"User:":label%></b></td>
       <td>
     <% 
       String showStoreMSBOnly = "no";
       if(user.isaStoreAdmin()){
    	   showStoreMSBOnly = "yes";
       }
       if (Utility.isSet(theForm.getReport().getUserTypes())){
         showStoreMSBOnly = theForm.getReport().getUserTypes();
       }
       String onClick = "popLocateUser('../adminportal/usermgrLocate', '"+controlEl+"', '','"+  showStoreMSBOnly +"');";
     %>
       
       <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>"/><%if(mandatoryFl ||
       (("CUSTOMER".equalsIgnoreCase(name) || "CUSTOMER_OPT".equalsIgnoreCase(name)) && Utility.isLocateUserRequired(userType, theForm.getReport().getUserTypes()))) {%><span class="reqind">*</span><%}%>
       <html:button property="locateUser"
                    onclick="<%=onClick%>"
                    value="<%=Utility.isSet(grc.getAdditionalLabel1()) ? grc.getAdditionalLabel1() : \"Locate User\"%>"/>
        </td>
  </tr>
<%
} else {
%>
 <html:hidden name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>" value="<%=userIdS%>"/>
<%
    }
} else { %>
  <!-- Generic Opt -->

  <%if (RefCodeNames.CONTROL_TYPE_CD.DROP_DOWN.equals(grc.getControlTypeCd())) { %>
  <%
      String defVal = grc.getDefault();
      PairViewVector values = grc.getChoiceValues();
  %>

  <tr>
      <td>&nbsp;</td>
      <td><b><%=(label == null) ? name : label%>
      </b></td>
      <td>
          <html:select onkeypress="<%=onKeyPress%>" property="<%=controlEl%>" value="<%=defVal%>">
              <%
                  for (Iterator iter = values.iterator(); iter.hasNext();) {
                      PairView valPair = (PairView) iter.next();
                      String val = (String) valPair.getObject1();
                      String elLabel = (String) valPair.getObject2();
              %>
              <html:option value="<%=val%>">
                  <%=elLabel%>
              </html:option>
              <% } %>
          </html:select>
          <%if (mandatoryFl) { %>
          <span class="reqind">*</span>
          <%}%>

      </td>
  </tr>
  <%} else if (RefCodeNames.CONTROL_TYPE_CD.RADIO.equals(grc.getControlTypeCd())) { %>
  <%
      String defVal = grc.getDefault();
      PairViewVector values = grc.getChoiceValues();
  %>
  <tr>
      <td>&nbsp;</td>
      <td><b><%=(label == null) ? name : label%>
      </b></td>
      <td>
          <%
              for (Iterator iter = values.iterator(); iter.hasNext();) {
                  PairView valPair = (PairView) iter.next();
                  String val = (String) valPair.getObject1();
                  String elLabel = (String) valPair.getObject2();
          %>
          <html:radio styleId="<%=controlEl%>" property="<%=controlEl%>" value="<%=val%>"/>
          <%=elLabel%>
          <% } %>

          <%if(defVal!=null){%>
          <script language="JavaScript1.2"> <!--
          var el= document.getElementById('<%=controlEl%>');
          if('undefined' != typeof el){
              if(el.value == '<%=defVal%>'){
                  el.checked = 1;
              }
          }
          //-->
          </script>
          <%}%>
      </td>
  </tr>
  <%} else {%>

  <tr>
      <td>&nbsp;</td>
      <td><b><%=(label == null) ? name : label%>
      </b></td>
      <td>
          <html:text onkeypress="<%=onKeyPress%>" name="ANALYTIC_REPORT_FORM" property="<%=controlEl%>"/>
          <%if (mandatoryFl) { %><span class="reqind">*</span> <%}%>
      </td>
  </tr>
  <% }
  }
        }
} //End of generic report controls
%>
<%   if(scheduleFl && hasDateControls) {  %>
<tr>
<td>&nbsp;</td>
<td colspan="2" align="left">
  <b>Possible Date Constants:&nbsp;</b><%=dateConst%>
</td></tr>
<% } %>

<html:hidden name="ANALYTIC_REPORT_FORM" property="requestedControls" value="<%=controls%>" />
   <tr>
     <% if(!scheduleFl && !theForm.isSpecialFl()) {  %>
       <td colspan="3" align="center">
          <b>Select Report Output Option:</b>
       </td>
    <% } %>
   </tr>
   <%//boolean disabledFl = theForm.isLockedFl();%>
   <tr>
       <td colspan="3" align="center">
        <html:hidden styleId="hiddenAction" name="ANALYTIC_REPORT_FORM" property="action" value="BBBBBB"/>
        <html:hidden styleId="format" name="ANALYTIC_REPORT_FORM" property="format" value=""/>
     <% if(!scheduleFl) {  %>
       <% if(theForm.isSpecialFl()) {  %>
        <button name="download" styleClass="store_fb" onclick="downloadReport('ANALYTIC_REPORT_FORM_ID','');">Download Report</button>
       <% } else {  %>
        <% if (!theForm.isShowOnlyDownloadButtonFl()) { %>
        <input type="button" name="action" value="View Report" onclick="setAndSubmit('ANALYTIC_REPORT_FORM_ID','command','View Report')">
        <% } %>
        <input type="button" name="action" value="Download Report" onclick="downloadReport('ANALYTIC_REPORT_FORM_ID','<%=Constants.REPORT_FORMAT.EXCEL%>')">
        <% if (!theForm.isShowOnlyDownloadButtonFl()) { %>
        <input type="button" name="action" value="Printer Friendly" onclick="downloadReport('ANALYTIC_REPORT_FORM_ID','<%=Constants.REPORT_FORMAT.PDF%>')">
        <% } %>
        <%String href = "reportSchedule.do?action=detail&reportId="+theForm.getReportId(); %>
        <a href="<%=href%>">Create Schedule</a>
        <% if(adminFl) {  %>
        <html:checkbox name="ANALYTIC_REPORT_FORM" property="savePrevVersion"/>
        Save the previous version of the report.
        <% } %>
       <% } %>

      <%-- </td>  --%>
    <% } %>
    </td>
  </tr>

<html:hidden  property="requestTokenSubmit" value="<%=requestToken%>"/>
<html:hidden property="command" value="CCCCCCC"/>
<html:hidden property="genericReportPageNum"/>
</html:form>
</tbody>
</table>



</div>
</body>

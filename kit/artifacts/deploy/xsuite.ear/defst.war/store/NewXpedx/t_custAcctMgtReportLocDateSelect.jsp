<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.*" %>
<%@ page import="com.cleanwise.service.api.reporting.ReportingUtils" %>
<%@page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.logic.CustAcctMgtReportLogic" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script type="text/javascript" src="../externals/jquery/1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="../externals/jqueryui/1.8.9/jquery-ui.min.js"></script>
<script type="text/javascript" src="../externals/jqueryui/1.8.14/i18n/jquery-ui-i18n.min.js"></script>
<script language="JavaScript1.2">
    <!--
    dojo.require("dojox.fx.easing");
    dojo.require("dojox.fx.scroll");
    dojo.require("clw.NewXpedx.form.DateTextBox");
    var loadingMessageId = 0;
    var count = 0;
    

    function setAndSubmit(fid, vv, value) {
        var aaa = document.forms[fid].elements[vv];
        aaa.value = value;
        if(dojo.isIE <= 6){
           //document.forms[fid].target = "pdf";
        }
        aaa.form.submit();
        return false;
    }
    
    function resultInNewWindow() {
        //  document.CUSTOMER_REPORT_FORM.submit();

        var loc = "custAcctMgtReportResult.do?action=get_delivery_sched"
                + "&beginMonth="
                + document.CUSTOMER_REPORT_FORM.beginMonth.value
                + "&beginYear="
                + document.CUSTOMER_REPORT_FORM.beginYear.value
                + "&endMonth="
                + document.CUSTOMER_REPORT_FORM.endMonth.value
                + "&endYear="
                + document.CUSTOMER_REPORT_FORM.endYear.value
                ;
        var prtwin = window.open(loc, "printer_friendly",
                "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
        prtwin.focus();
        window.location.href = "customerAccountManagement.do?action=done_with_report"
        return false;
    }

    function writemessage(id, message) {
        try {
            document.getElementById(id).innerHTML = message;
        } catch(e) {
        }
    }

    function gotoId(id) {
        try {
            var node = dojo.byId(id);
            var anim = dojox.fx.smoothScroll({
                node: node,
                win:window,
                duration:300,
                easing:dojox.fx.easing.easeOut}).play();
        } catch(e) {
        }
    }

    function generateReport(command) {
        writemessage("wmessage","");
        writemessage("emessage","");
        writemessage("loadingMessage","Loading");
            	
        $.post("<%=request.getContextPath()%>/userportal/custAcctMgtReportLocDateSelect.do?action=generateReport&command="+command,
			$("#repParams").serialize(),
		    function(response, ioArgs) {         
  			writemessage("loadingMessage","");
	    	clearInterval ( loadingMessageId );
	        var errorMsgs = "";
	        try {
	
	            var root = response.getElementsByTagName("response")[0];
	            var body = root.getElementsByTagName("body")[0];
	            var errors = body.getElementsByTagName("errors")[0];
	
	            for (var i = 0; i < errors.childNodes.length; i++) {
	                var error = errors.childNodes[i];
	                for (var j = 0; j < error.childNodes.length; j++) {
	                    var errorString = error.childNodes[j]
	                    if (errorString.firstChild){
	                    	var mess = errorString.firstChild.nodeValue
	                    	errorMsgs += (i > 0?"<br>":"") + mess;
	                    }
	                }
	            }
	
	            var emessage = document.getElementById("emessage");
	            if (emessage) {
	                if (errorMsgs != "") {
	                    emessage.innerHTML = "<b>" + errorMsgs + "</b>";
	                    gotoId("emessage");
	                } else {
	                	var command = root.getAttribute("command");
	                	if (command == "") 
	                		command = "ViewReport";
	                	if (command == "ViewReport"){
		                    var rCount = body.getElementsByTagName("reportmaxrowcount")[0];
		                    var rCountValue = rCount.firstChild.nodeValue;
		                    if (rCountValue > <%=CustAcctMgtReportLogic.MAX_HTML_ROWS%>) {
		                        writemessage("wmessage", "<%=Utility.strNN(ClwI18nUtil.getMessage(request,"shop.warnings.reportHasBeenSentToExcel",new Object[]{CustAcctMgtReportLogic.MAX_HTML_ROWS},true))%>");
		                        gotoId("wmessage");
		                        window.location.href = "custAcctMgtReportResult.do?action=Download to Excel";
		                    } else {
		                        setAndSubmit('repParams', 'command', 'goToResult')
		                    }
		                } else if (command == "DownloadToExcel"){
		                	window.location.href = "custAcctMgtReportResult.do?action=Download to Excel";
		                } else if (command == "DownloadToPDF"){
		                	window.location.href = "custAcctMgtReportResult.do?action=Download to PDF";
		                }
	                }
	            }
	        } catch(e) {
	        }
		}, 'xml');
		count = 0;
		loadingMessageId = setInterval ("showLoadingMessage()",2000); // update the Loading... message every 2 seconds
    }
    
    function showLoadingMessage(){
    	if (count == 5){ // make request to keep session alive every 10 seconds
    		count = 0;
    		dojo.xhrGet({url: "../keepAlive.do?action=updateLoadingMessage&loadingMessage=Loading",handleAs:'xml',sync:false});
    	}else{
    		count += 1;
    	}
    	var loadingMessage = document.getElementById("loadingMessage").innerHTML
    	if (loadingMessage.length==10)
    		loadingMessage = "Loading";
    	else
    		loadingMessage += ".";
    	
 		writemessage("loadingMessage",loadingMessage);
	}

    //-->
</script>


<%
    String browser = request.getHeader("User-Agent");
    String isMSIE = "";
	boolean isRequired = false;
    if (browser != null && browser.indexOf("MSIE") >= 0) {
        isMSIE = "Y";
%>

    <script language="JavaScript" src="../externals/calendar.js"></script>
    <iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
       marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html">
    </iframe>

<%
} else {
%>
    <script language="JavaScript" src="../externals/calendarNS.js"></script>

<%
    }
%>

<%
    boolean dateSearchStart = false, dateSearchEnd = false;
%>

<bean:define id="IMGPath" type="java.lang.String"
             name="pages.store.images"/>

<bean:define id="theForm" name="CUSTOMER_REPORT_FORM" type="com.cleanwise.view.forms.CustAcctMgtReportForm"/>

<jsp:include flush='true' page="/storeportal/locateStoreSite.jsp">
    <jsp:param name="jspFormAction" value="/userportal/custAcctMgtReportLocDateSelect.do"/>
    <jsp:param name="jspFormName" value="CUSTOMER_REPORT_FORM"/>
    <jsp:param name="jspSubmitIdent" value=""/>
    <jsp:param name="isSingleSelect" value="true"/>
    <jsp:param name="jspReturnFilterPropertyAlt" value="siteDummyConvert"/>
    <jsp:param name="jspReturnFilterProperty" value="siteFilter"/>
	<jsp:param name="jspMaxSites" value="2000"/>
</jsp:include>

<jsp:include flush='true' page="/reporting/locateReportAccount.jsp">
    <jsp:param name="jspFormAction"            value="/userportal/custAcctMgtReportLocDateSelect.do"/>
    <jsp:param name="jspFormName"              value="CUSTOMER_REPORT_FORM"/>
    <jsp:param name="jspSubmitIdent"           value=""/>
    <jsp:param name="jspReturnFilterProperty"  value="accountFilter"/>
    <jsp:param name="checkBoxShowInactive"     value="hide"/>
    <jsp:param name="jspHtmlFormId"            value="LOCATE_ACCOUNT_FORM_ID"/>
    <jsp:param name="jspReportIdName"          value="id"/>
    <jsp:param name="jspReportIdValue"         value="1"/>
    <jsp:param name="jspDataSourceType"        value=""/>
</jsp:include>

<jsp:include flush='true' page="/reporting/locateReportDistributor.jsp">
    <jsp:param name="jspFormAction"            value="/userportal/custAcctMgtReportLocDateSelect.do"/>
    <jsp:param name="jspFormName"              value="CUSTOMER_REPORT_FORM"/>
    <jsp:param name="jspSubmitIdent"           value=""/>
    <jsp:param name="jspReturnFilterProperty"  value="distributorFilter"/>
    <jsp:param name="checkBoxShowInactive"     value="hide"/>
    <jsp:param name="jspHtmlFormId"            value="LOCATE_DISTRIBUTOR_FORM_ID"/>
    <jsp:param name="jspReportIdName"          value="id"/>
    <jsp:param name="jspReportIdValue"         value="1"/>
    <jsp:param name="jspDataSourceType"        value=""/>

</jsp:include>

<html:form name="CUSTOMER_REPORT_FORM" action="/userportal/custAcctMgtReportLocDateSelect.do"
           styleId="repParams" type="com.cleanwise.view.forms.CustAcctMgtReportForm">

<bean:define id="l_rpttype" name="CUSTOMER_REPORT_FORM" property="reportTypeCd" type="java.lang.String"/>
<%
    String xlateReportcat = ClwI18nUtil.getMessageOrNull(request, ReportingUtils.makeReportNameKey(l_rpttype));

    if (xlateReportcat == null) {
        xlateReportcat = l_rpttype;
    }
%>

<TABLE cellSpacing=0 cellPadding=0 width=769 align=center border=0>
<TBODY>
<tr><td>
    <table class="breadcrumb"><tr class="breadcrumb">
        <td class="breadcrumb"><a class="breadcrumb" href="../userportal/msbsites.do?action=goHome"><app:storeMessage key="breadcrumb.label.home"/></a></td>
        <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
        <td class="breadcrumb"><a class="breadcrumb" href="customerAccountManagement.do"><app:storeMessage key="shop.menu.main.reports"/></a></td>
        <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
        <td class="breadcrumb">
		<%--<a class="breadcrumb" href="customerAccountManagement.do?action=next&reportTypeCd=<%=xlateReportcat%>"><%=xlateReportcat%></a>--%>
		<div class="breadcrumbCurrent"><%=xlateReportcat%></div>
		</td>
    </tr></table>
</td></tr>
<tr><td>
    <div id="loadingMessage" class="genericerror" align="center"  style="color:#FF0000"></div>
</td></tr>
<tr><td>
<br>
<TABLE cellSpacing="0" cellPadding="4" border="0">
<TBODY>

<TR>
<td width="169" class="shopcharthead"><app:storeMessage key="report.text.typeOfReport"/></TD>
<TD class="shopcharthead">&nbsp;</TD>
<logic:equal name="CUSTOMER_REPORT_FORM" property="selectLocationFlag" value="true">
<TD width=335 class="shopcharthead">&nbsp;</TD>
</logic:equal>
<TD colspan=25 class="shopcharthead">
   <app:storeMessage key="report.text.SelectParameters" />
</TD>
</TR>

<html:hidden name="CUSTOMER_REPORT_FORM" property="reportTypeCd" value="<%=l_rpttype%>"/>

<TR>
<TD class="text" valign="top" width="169">
    <DIV class="fivemargin">
        <DIV class="subheadergeneric">
            <%=xlateReportcat%>
        </DIV>
        <BR/>
        <BR/>
        <%
            String xlateReportdesc = ClwI18nUtil.getMessageOrNull(request,
                    ReportingUtils.makeReportDescriptionKey(l_rpttype));

            if (xlateReportdesc == null) {
                xlateReportdesc = "";
            }
        %>
        <%=xlateReportdesc%>
        <BR/>
    </DIV>
</TD>

<TD width="1">
    <IMG height="1" src="<%=IMGPath%>/cw_spacer.gif" width="1">
</TD>

<logic:equal name="CUSTOMER_REPORT_FORM" property="selectLocationFlag" value="true">
    <TD class=text valign="top">
        <DIV class=fivemargin>
            <bean:define id="userSites" name="CUSTOMER_REPORT_FORM" property="siteIdList"/>
            <logic:equal name="CUSTOMER_REPORT_FORM" property="multipleLocSelectFlag" value="true">
                <app:storeMessage key="report.text.chooseGroupLocations"/>:<BR><BR>
                <html:select name="CUSTOMER_REPORT_FORM" property="selectedSites"
                             multiple="true" size="5">
                    <html:options collection="userSites"
                                  property="label" labelProperty="value"/>
                </html:select>
            </logic:equal>
            <logic:equal name="CUSTOMER_REPORT_FORM" property="multipleLocSelectFlag" value="false">
                <app:storeMessage key="report.text.chooseLocation"/>:<BR><BR>
                <html:select name="CUSTOMER_REPORT_FORM" property="selectedSites">
                    <html:options collection="userSites"
                                  property="label" labelProperty="value"/>
                </html:select>
            </logic:equal>
        </DIV>
    </TD>
    <TD width=1>
        <IMG height=1 src="<%=IMGPath%>/cw_spacer.gif" width=1>
    </TD>
</logic:equal>

<TD class=text valign=top width=230>
<DIV class=fivemargin>


        <%
        Boolean l_flag11 = Boolean.FALSE;

        if (l_rpttype.indexOf("Inventory Ordering Activity") >= 0 ||
            l_rpttype.indexOf("Order Information") >= 0) {
        %>
            <app:storeMessage key="report.text.selectBudgetPeriod"/>:<BR><BR>

            <table>
                <logic:iterate id="budgetPeriod"
                               name="Report.budget.periods"
                               type="com.cleanwise.service.api.dao.UniversalDAO.dbrow">
                    <tr>
                    <%if(budgetPeriod != null && budgetPeriod.getColumnCount() > 3){%>
                        <td>
                            <%=budgetPeriod.getStringValue(0)%>
                        </td>
                        <td align="center">
                            <a href="custAcctMgtReportResult.do?action=init&year=<%=budgetPeriod.getStringValue(0)%>&budget_period=<%=budgetPeriod.getStringValue(1)%>&start_date=<%=budgetPeriod.getStringValue(2)%>&end_date=<%=budgetPeriod.getStringValue(3)%>">
                                <%=budgetPeriod.getStringValue(2)%> -
                                <%=budgetPeriod.getStringValue(3)%>
                            </a>
                        </td>
                    <%}%>
                    </tr>
                </logic:iterate>
            </table>
        <% } else {

            if (l_rpttype.indexOf("Budget") >= 0 ) {
                l_flag11 = Boolean.TRUE;
            } else {
                l_flag11 = Boolean.FALSE;
            }
        }

        if (l_rpttype.equals(RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE)) { %>

            <table>
                <tr>
                    <td class=text><b><app:storeMessage key="report.text.from"/>:</b></td>
                    <td>
                        <html:select name="CUSTOMER_REPORT_FORM"
                                     property="beginMonth" disabled="<%=l_flag11.booleanValue()%>">
                            <html:options collection="Report.month.vector"
                                          property="label" labelProperty="value"/>
                        </html:select>
                        <html:select name="CUSTOMER_REPORT_FORM" property="beginYear">
                            <html:options collection="Report.year.vector"
                                          property="label" labelProperty="value"/>
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td class=text><b><app:storeMessage key="report.text.to"/>:</b></td>
                    <td>
                        <html:select name="CUSTOMER_REPORT_FORM" property="endMonth" disabled="<%=l_flag11.booleanValue()%>">

                            <html:options collection="Report.month.vector"
                                          property="label" labelProperty="value"/>
                        </html:select>

                        <html:select name="CUSTOMER_REPORT_FORM" property="endYear">
                            <html:options collection="Report.year.vector" property="label" labelProperty="value"/>
                        </html:select>
                    </td>
                </tr>
            </table>
            <P>
            <%
                String leftButtonURL = ClwCustomizer.getSIP(request,"buttonLeft.png");
                String rightButtonURL = ClwCustomizer.getSIP(request,"buttonRight.png");
            %>
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
                    <td align="center" valign="middle" nowrap class="xpdexGradientButton">
                        <a href="#" class="xpdexGradientButton"  onclick="resultInNewWindow();"><app:storeMessage key="global.action.label.exportExcelSheet"/></a>
                    </td>
                    <td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>
                </tr>
            </table>

            <div class="text"><font color=red><html:errors/></font></div>
            <%request.setAttribute("renderedErrors","true");%>
            </P>

    <% } else {  %>
<!-- Beg Generic Report Controls -->
    <%

    GenericReportControlViewVector grcVV = theForm.getReportControls();
       GenericReportControlView endMonthOpt = null;
       GenericReportControlView endYearOpt = null;
       int endMonthOptNum = 0, endYearOptNum = 0;
       Date today = new Date();

       if(grcVV!=null && grcVV.size()>0) {

       for(int ii=0; ii<grcVV.size(); ii++) {

       GenericReportControlView grc = (GenericReportControlView) grcVV.get(ii);
       boolean rendered = false;
       String controlEl = "reportControlValue["+ii+"]";
       if (grc.getInvisible() != null && grc.getInvisible().booleanValue()){
       %>
     	  <html:hidden property="<%=controlEl%>" value="<%=grc.getValue()%>"/>
       <% continue;}
           
       String name = grc.getName();

       boolean mandatoryFl=true;
       dateSearchStart = false;
       dateSearchEnd = false;

       String mf = grc.getMandatoryFl();
       if(mf!=null){
        mf = mf.trim().toUpperCase();
       }

       if("N".equals(mf) || "NO".equals(mf) ||"0".equals(mf) ||"F".equals(mf) ||"FALSE".equals(mf)){
        mandatoryFl = false;
       }       
	          
	   if (grc.getIgnore()!=null && grc.getIgnore()==true) 
	   {
             continue;
	   }
	
       if(
          "ACCOUNT".equals(name) ||
          "ACCOUNT_MULTI_OPT".equals(name) ||
          "STORE".equals(name) ||
          "STORE_OPT".equals(name) ||
          "STORE_SELECT".equals(name) ||
          "SITE".equals(name) ||
          "DATE_FMT".equals(name) ||
          "CUSTOMER".equals(name)) {
             // nothing to render for these controls
             // when run through the customer reporting pages
             continue;
       }
       
       //STJ-4110 - removing the distributor selection for the
       //xpedx Distributor summary sales report
       if ("LOCATE_DISTRIBUTOR_MULTI_OPT".equalsIgnoreCase(name) ||
           "LOCATE_DISTRIBUTOR_MULTI".equalsIgnoreCase(name) ||
           "DISTRIBUTOR_MULTI".equalsIgnoreCase(name) ||
           "DISTRIBUTOR_MULTI_OPT".equalsIgnoreCase(name)){
    	   continue;
       }

       String label = grc.getLabel();
       if("BEG_DATE".equals(name) || "BEG_DATE_OPT".equals(name)){
            dateSearchStart = true;
            label=ClwI18nUtil.getMessage(request,"report.control.beginDate:",null);
       }else if("END_DATE".equals(name) || "END_DATE_OPT".equals(name)){
            dateSearchEnd = true;
            label=ClwI18nUtil.getMessage(request,"report.control.endDate:",null);

	   }else if("endMonth_OPT".equals(name)){
          endMonthOpt = grc;
          endMonthOptNum = ii;
          theForm.setReportControlValue(ii, ""+(today.getMonth()+1));
          mandatoryFl=false;
		  rendered=true;
	   }else if("endYear_OPT".equals(name)){
	      endYearOpt = grc;
	      endYearOptNum = ii;
	      theForm.setReportControlValue(ii, ""+(today.getYear()+1900));
		  mandatoryFl=false;
		  rendered=true;
       }else if ("BUDGET_YEAR".equals(name)) {

    //START RENDERING "BUDGET_YEAR"
    String yrs = "";
    rendered = true;

    String xlateyr = ClwI18nUtil.getMessageOrNull(request, "report.text.BudgetYear");
    if ( null == xlateyr ){
     xlateyr = "Year";
    }
    %>
    <%=xlateyr%>
    <%
    if(mandatoryFl) {
    %>
<span class="reqind">*</span>
<%isRequired=true;%>
    <%
    }
    %>
<logic:iterate id="budgetPeriod" name="Report.budget.periods" type="com.cleanwise.service.api.dao.UniversalDAO.dbrow">
    <%
    if ( yrs.indexOf(budgetPeriod.getStringValue(0)) == -1 ) {
      yrs += budgetPeriod.getStringValue(0) + " : ";
    %>
    <%--name="CUSTOMER_REPORT_FORM"--%>
<br/>
<html:radio property="<%=controlEl%>" value="<%=budgetPeriod.getStringValue(0)%>">
    <%=budgetPeriod.getStringValue(0)%>
</html:radio>

    <%
    }
    %>

</logic:iterate>
    <%--END RENDERING "BUDGET_YEAR"--%>
    <%
    } else if ("BUDGET_PERIOD_INFO".equals(name)) {

    //--START RENDERING "BUDGET_PERIOD_INFO"--
    rendered = true;

    String xlateyr = ClwI18nUtil.getMessageOrNull(request, "report.text.selectBudgetPeriod");
    if ( null == xlateyr ){
     xlateyr = "Please select a Budget Period";
    }
    %>
    <%=xlateyr%>

    <% if (mandatoryFl) { %>
        <span class="reqind">*</span>
        <% isRequired = true; %>
    <% } %>

    <BR>
    <BR>
    <table>
        <logic:iterate  id="budgetPeriod"
                        name="Report.budget.periods"
                        type="com.cleanwise.service.api.dao.UniversalDAO.dbrow">
        <tr>
            <% if (budgetPeriod != null && budgetPeriod.getColumnCount() > 3) { %>
            <td align="center">
                <%
                String val =    "year=" + budgetPeriod.getStringValue(0) +
                                "&budget_period=" + budgetPeriod.getStringValue(1) +
                                "&start_date=" + budgetPeriod.getStringValue(2) +
                                "&end_date=" + budgetPeriod.getStringValue(3);
                %>
                <html:radio property="<%=controlEl%>" value="<%=val%>"/>
            </td>
            <td align="left">
                &nbsp;<%=budgetPeriod.getStringValue(0)%>&nbsp;<%=budgetPeriod.getStringValue(2)%>&nbsp;-&nbsp;<%=budgetPeriod.getStringValue(3)%>
            </td>
            <% } %>
        </tr>
        </logic:iterate>
    </table>
    <%--END RENDERING "BUDGET_PERIOD_INFO"--%>


	<%--START RENDERING "SINGLE_DAYS_BACK_7"--%>
	<%

    } else if (name.startsWith("SINGLE_DAYS_BACK_")  ) {
		rendered=true;

    %>

	<app:storeMessage key="report.text.selectDate"/>:<BR><BR>

    <table>
    <%
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdfTo   = new SimpleDateFormat("MM/dd/yyyy");
		Calendar curr = Calendar.getInstance();
		java.util.Date current = new java.util.Date(System.currentTimeMillis());
		curr.setTime(current);

		for(int i=0; i<7; i++){
			if(i>0){
				curr.add(Calendar.DAY_OF_YEAR, -1);
			}
			String thisDate = sdf.format(curr.getTime());
			String thisDateFormatted = sdfTo.format(sdf.parse(thisDate));
		%>
			<tr>
            <%if(thisDate != null){%>
                <td align="center">
				<a href="javascript:document.getElementById('SINGLEDAYSBACK').value='<%=thisDateFormatted%>';generateReport('ViewReport');"><%=thisDate%></a>
				</td>
            <%}%>

            </tr>

		<%}%>
		<input type="hidden" id="SINGLEDAYSBACK" name="<%=controlEl%>" value=""/>
    </table>


	<%--END RENDERING "SINGLE_DAYS_BACK_7"--%>
	
	<%--START RENDERING "ACCOUNTS"--%>
    <%
    }else if ("LOCATE_ACCOUNT_MULTI".equalsIgnoreCase(name) ||
              "LOCATE_ACCOUNT_MULTI_OPT".equalsIgnoreCase(name)) {
    rendered = true;
    AccountUIViewVector accVwV = (AccountUIViewVector) session.getAttribute("Report.parameter.accounts");
    String controlLabel = name.startsWith("LOCATE_ACCOUNT_MULTI") ? "userlocate.account.text.locateAccount" :"";
    %>
<br/>
<html:hidden property="<%=RequestPropertyNames.PROPERTY_NAME_LOCATE_ACCOUNT_TYPE%>" value="<%=RequestPropertyNames.PROPERTY_VALUE_LOCATE_ACCOUNT_REPORT%>"/>

    <%
    if(accVwV!=null && accVwV.size()>0) {
	int accCount = accVwV.size();
    %>
	<b><%=accCount%> accounts selected:</b>
<div class="subheadergeneric">
    <%
        for (Iterator iter = accVwV.iterator(); iter.hasNext();) {
            AccountUIView accVw = (AccountUIView) iter.next();
    %>
    &quot;<%=accVw.getBusEntity().getShortDesc()%>&quot;<br/>
        <html:hidden name="CUSTOMER_REPORT_FORM" property="<%=controlEl%>" value="<%=Integer.toString(accVw.getBusEntity().getBusEntityId())%>"/>
    <%
        }
    %>
    <nobr>
      <input type="submit" property="action" class="text"
           onclick="setAndSubmit('repParams','command','Clear Accounts')"
           value="<app:storeMessage key="report.control.clearAccounts" />"/>&nbsp;
      <input type="submit" property="action" class="text"
           onclick="setAndSubmit('repParams','command','Locate Account')"
           value="<app:storeMessage key="<%=controlLabel%>" />"/>
     </nobr>
    <%
        } else {
    %>
        <input type="submit" property="action" class="text"
        onclick="setAndSubmit('repParams','command','Locate Account')"
        value="<app:storeMessage key="<%=controlLabel%>" />"/>
     <% }%>
    <% if (mandatoryFl) { %>
        <span class="reqind">*</span>
        <% isRequired = true; %>
    <% } %>
</div>
<br/>
    <%--END RENDERING "ACCOUNTS"--%>

    <%
    } else if ("SITES".equals(name) || "SITES_OPT".equals(name) ||
               "SITE_MULTI".equals(name) || "SITE_MULTI_OPT".equals(name) ||
               "LOCATE_SITE_MULTI".equals(name) || "LOCATE_SITE_MULTI_OPT".equals(name)) {
    rendered = true;
    SiteViewVector siteVwV = (SiteViewVector) session.getAttribute("Report.parameter.sites");
    %>

    <%


    if(siteVwV!=null && siteVwV.size()>0) {

	int siteCount = siteVwV.size();
    %>
	<b><%=siteCount%> sites selected:</b>
<div class="subheadergeneric">

	<%
        for (Iterator iter = siteVwV.iterator(); iter.hasNext();) {
            SiteView siteVw = (SiteView) iter.next();
    %>
    &quot;<%=siteVw.getName()%>&quot;<br/>
    <%
        }
    %>

    <input type="submit" property="action" class="text"
           onclick="setAndSubmit('repParams','command','Clear Sites')"
           value="<app:storeMessage key="report.control.clearSites" />"/>
    <%
        }
    %>
    <input type="submit" property="action" class="text"
           onclick="setAndSubmit('repParams','command','Locate Site')"
           value="<app:storeMessage key="report.control.selectSites" />"/>
</div>
<br/>
    <%--END RENDERING "SITES"--%>
    <%
    }else if ("MANUFACTURER".equals(name) || "MANUFACTURER_OPT".equals(name)) {
  //START RENDERING "MANUFACTURER"
  rendered = true;
    %>
<div class="subheadergeneric">
    <%
        String mfgselect = ClwI18nUtil.getMessageOrNull(request, "report.control.selectManufacturer");
        if (null == mfgselect) {
            mfgselect = "Select Mfg:";
        }
    %>
    <b><%=mfgselect%></b>
    <html:select property="<%=controlEl%>">
        <html:option value="">
            <app:storeMessage  key="admin.select"/>
        </html:option>
        <html:options collection="Report.all.manufacturer.for.store" property="busEntityId" labelProperty="shortDesc"/>
    </html:select>
</div>
<br>

    <%--END RENDERING "MANUFACTURER"--%>
    <%
    }else if ("DISTRIBUTOR".equals(name) || "DISTRIBUTOR_OPT".equals(name)) {
  //START RENDERING "DISTRIBUTOR"
  rendered = true;

    %>
<div class="subheadergeneric">
    <b><app:storeMessage  key="report.text.selectDistributor"/>:</b>
    <html:select property="<%=controlEl%>">
        <html:option value="">
            <app:storeMessage  key="admin.select"/>
        </html:option>
        <html:options collection="Report.distributor.vector" property="busEntityId" labelProperty="shortDesc"/>
    </html:select>
</div>
<br>
    <%--END RENDERING "DISTRIBUTOR"--%>

         <%--START RENDERING "LOCATE_DISTRIBUTOR_OPT"--%>
        <%
         }else if ("LOCATE_DISTRIBUTOR_MULTI_OPT".equalsIgnoreCase(name) ||
                   "LOCATE_DISTRIBUTOR_MULTI".equalsIgnoreCase(name) ||
                   "DISTRIBUTOR_MULTI".equalsIgnoreCase(name) ||
                   "DISTRIBUTOR_MULTI_OPT".equalsIgnoreCase(name)) {
         rendered = true;
         DistributorDataVector distDV = (DistributorDataVector)
            session.getAttribute("Report.parameter.distributors");
         String controlLabel = "userlocate.distributor.text.locateDistributor";

         %>
   <html:hidden property="<%=RequestPropertyNames.PROPERTY_NAME_LOCATE_DISTRIBUTOR_TYPE%>"
                value="<%=RequestPropertyNames.PROPERTY_VALUE_LOCATE_DISTRIBUTOR_REPORT%>"/>

     <br/>
         <%


         if(distDV!=null && distDV.size()>0) {

         int distCount = distDV.size();
         %>
         <b><%=distCount%> distributors selected:</b>
     <div class="subheadergeneric">

         <%
             for (Iterator iter = distDV.iterator(); iter.hasNext();) {
                 DistributorData distD = (DistributorData) iter.next();
         %>
         &quot;<%=distD.getBusEntity().getShortDesc()%>&quot;<br/>
        <html:hidden name="CUSTOMER_REPORT_FORM" property="<%=controlEl%>" value="<%=Integer.toString(distD.getBusEntity().getBusEntityId())%>"/>
         <%
             }
         %>
         <nobr>
         <input type="submit" property="action" class="text"

                onclick="setAndSubmit('repParams','command','Clear Distributors')"
                value="<app:storeMessage key="report.control.clearDistributors" />"/>
           <input type="submit" property="action" class="text"
                onclick="setAndSubmit('repParams','command','Locate Distributor')"
                value="<app:storeMessage key="<%=controlLabel%>" />"/>
         </nobr>
         <%
             } else{
         %>
         <input type="submit" property="action" class="text"
                onclick="setAndSubmit('repParams','command','Locate Distributor')"
                value="<app:storeMessage key="<%=controlLabel%>" />"/>
           <% }%>
     </div>
     <br/>
         <%--END RENDERING "LOCATE_DISTRIBUTOR_OPT"--%>
    <% } %>


    <%--START RENDERING "INVOICE_TYPE_2"--%>
    <%
    if ("INVOICE_TYPE_2".equals(name) || "INVOICE_TYPE_2_OPT".equals(name)) {
  rendered = true;

    %>
<div class=subheadergeneric>
    <b><app:storeMessage  key="report.text.selectInvoiceType"/>:</b><br>
    <html:select property="<%=controlEl%>">
        <html:option value="">
            <app:storeMessage  key="admin.select"/>
        </html:option>
        <html:option value="<%=RefCodeNames.ITEM_SALE_TYPE_CD.END_USE%>">
            <app:storeMessage  key="report.text.invoice.type.enduse"/>
        </html:option>
        <html:option value="<%=RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE%>">
            <app:storeMessage  key="report.text.invoice.type.resale"/>
        </html:option>
    </html:select>
    <%
        if ("INVOICE_TYPE_2".equals(name) && mandatoryFl) {
    %><span class="reqind">*</span>
	<%isRequired=true;%>
    <%
        }
    %>
</div>
<br>
    <%
    }
    %>
    <%--END RENDERING "INVOICE_TYPE_2"--%>

    <%
if(label!=null && label.length()==0){
 label=null;
}

if(!rendered){

if ( dateSearchEnd ) {

    %>
<BR/>

<DIV class="subheadergeneric">
    <b>
        <app:storeMessage key="report.text.EndDate"/>
        (
        <%=ClwI18nUtil.getUIDateFormat(request)%>
        )
    </b>
	<br/>
    <app:dojoInputDate id="controlEndDate"
                       name="CUSTOMER_REPORT_FORM"
                       property="<%=controlEl%>"
                       module="clw.NewXpedx"
                       targets="EndDate"/>


     <img id="EndDate" src="../externals/images/showCalendar.gif"
             width=19  height=19 border=0
             onmouseover="window.status='Choose Date';return true"
             onmouseout="window.status='';return true">

    <!-- date format is
    <%=ClwI18nUtil.getUIDateFormat(request)%>
    -->
</DIV>
    <%
    } else if (dateSearchStart) {
    %>
<br/>
<br/>
<BR/>

<DIV class="subheadergeneric">
    <b>
        <app:storeMessage key="report.text.BeginDate"/>
        (
        <%=ClwI18nUtil.getUIDateFormat(request)%>
        )
    </b>
     <app:dojoInputDate id="controlStartDate"
                       name="CUSTOMER_REPORT_FORM"
                       property="<%=controlEl%>"
                       module="clw.NewXpedx"
                       targets="StartDate"/>


   <img id="StartDate" src="../externals/images/showCalendar.gif"
             width=19  height=19 border=0
             onmouseover="window.status='Choose Date';return true"
             onmouseout="window.status='';return true">

    <%
    } else {
        String controlType = grc.getControlTypeCd();
        if (controlType == null
                || controlType.trim().length() == 0
                || controlType.equals(RefCodeNames.CONTROL_TYPE_CD.TEXT)) {
    %>

    <!-- Generic Opt -->
    <BR/>

    <DIV class="subheadergeneric">
        <b><%=(label == null) ? name : label%>
        </b><br>
        <html:text name="CUSTOMER_REPORT_FORM" property="<%=controlEl%>"/>
        <%
            if (mandatoryFl) {
        %>
        <span class="reqind">*</span>
		<%isRequired=true;%>
        <%
            }
        %>
    </DIV>
    <!-- DROP-DOWN control -->
    <%
    } else if (controlType.equals(RefCodeNames.CONTROL_TYPE_CD.DROP_DOWN)) {
        String defVal = grc.getDefault();
        PairViewVector values = grc.getChoiceValues();
    %>
    <div class="subheadergeneric">
        <b><%=((label == null) ? "&nbsp;" : label)%>
        </b>
        <br/>
        <%
            if (values != null && values.size() > 0) {
                if (defVal == null) {
                    PairView valPair = (PairView) values.get(0);
                    defVal = (String) valPair.getObject1();
                }
        %>
        <html:select property="<%=controlEl%>" value="<%=defVal%>">
            <%
                for (Iterator iter = values.iterator(); iter.hasNext();) {
                    PairView valPair = (PairView) iter.next();
                    String val = (String) valPair.getObject1();
                    String uiVal = (String) valPair.getObject2();
            %>
            <html:option value="<%=val%>"><%=uiVal%>
            </html:option>
            <%
                }
            %>
        </html:select>

        <%
            if (mandatoryFl) {
        %>
        <span class="reqind">*</span>
		<%isRequired=true;%>
        <%
            }
        %>
        <%
            }
        %>
    </div>
    <br> <!-- End DROP-DOWN control -->
   <% } else if (RefCodeNames.CONTROL_TYPE_CD.RADIO.equals(grc.getControlTypeCd())) { %>
    <%
        String defVal = grc.getDefault();
        PairViewVector values = grc.getChoiceValues();
    %>
    <div class="subheadergeneric">
        <b><%=(label == null) ? name : label%>
        </b>
        <br/>
            <%
                for (Iterator iter = values.iterator(); iter.hasNext();) {
                    PairView valPair = (PairView) iter.next();
                    String val = (String) valPair.getObject1();
                    String elLabel = (String) valPair.getObject2();
            %>
            <html:radio styleId="<%=controlEl%>" property="<%=controlEl%>" value="<%=val%>"/>
            <%=elLabel%>
            <% } %>

            <%if (defVal != null) {%>
            <script language="JavaScript1.2"> <!--
            var el = document.getElementById('<%=controlEl%>');
            if ('undefined' != typeof el) {
                if (el.value == '<%=defVal%>') {
                    el.checked = 1;
                }
            }
            //-->
            </script>
            <%}%>
    <%}%>
    <%
                }
            }
        } //End of generic report controls
    %>

        <% if (endMonthOpt != null) {
              String controlEl = "reportControlValue["+endMonthOptNum+"]";
        %>

                    <br>OR<br><br>
                    <b><app:storeMessage key="report.text.selectEndMonth"/>:</b>
                    <html:select name="CUSTOMER_REPORT_FORM" property="<%=controlEl%>" >
                    <html:options collection="Report.month.vector" property="label" labelProperty="value"/>
                    </html:select>

        <% } %>
        <% if (endYearOpt != null) {
              String controlEl = "reportControlValue["+endYearOptNum+"]";
        %>

                    <br><br>
                    <b><app:storeMessage key="report.text.selectEndYear"/>:</b>
                    <html:select name="CUSTOMER_REPORT_FORM" property="<%=controlEl%>">
                    <html:options collection="Report.year.vector" property="label" labelProperty="value"/>
                    </html:select>

        <% } %>

    <!-- End Generic Report Controls -->
        <%
            String leftButtonURL = ClwCustomizer.getSIP(request,"buttonLeft.png");
            String rightButtonURL = ClwCustomizer.getSIP(request,"buttonRight.png");
        %>
<p>

<%
	boolean correctPdf = true;
	String dnwlcmd = "DownloadToExcel";
	String dnwlcmd2 = "DownloadToPDF";
	String reportName = xlateReportcat;
	if(!reportName.equals("Daily Back Order Report")){
%>
<table cellpadding="0" cellspacing="0" border="0">
<tr>
<td colspan="9"><app:storeMessage key="report.text.SelectOption"/></td>
</tr>
<tr>
<td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
<td align="center" valign="middle" nowrap class="xpdexGradientButton"><a href="#"
    class="xpdexGradientButton"
    onclick="generateReport('ViewReport')"><app:storeMessage key="report.text.ViewReport"/></a></td>
<td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>

<td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
<td align="center" valign="middle" nowrap class="xpdexGradientButton"><a href="#"
    class="xpdexGradientButton"
	onclick="generateReport('<%=dnwlcmd2%>');"><app:storeMessage key="report.text.PrintReport"/></a></td>
<td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>
<%

if( "Budget Report".equals(reportName) ||
    "Account Budget Report".equals(reportName) ||
    "Forecast Order History report".equals(reportName) ||
	"Spend vs Budget by Category / Subcategory".equals(reportName) ||
    "Forecast Order Summary by Item Report".equals(reportName) ||
    "Forecast Order Summary by Location Report".equals(reportName) ||
    "Budgets Exceeded Report".equals(reportName) ||
    "Order Totals".equals(reportName) ||
	"Manufacturer Summary Sales Report".equals(reportName) ||
	"Inventory Par Values".equals(reportName) ||
	"Forecasted Items Summary Zero and Blank Entries".equals(reportName) ||
    "xpedx Distributor Summary Sales Report".equals(reportName) ||
    "Order Approval Report".equals(reportName)){
%>
<td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
<td align="center" valign="middle" nowrap class="xpdexGradientButton"><a href="#"
    class="xpdexGradientButton"
	onclick="generateReport('<%=dnwlcmd%>');"><app:storeMessage key="global.action.label.exportExcelSheet"/></a></td>
<td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>
<%}else{%>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<%}%>
</tr>
</table>
<%}%>
<% if(isRequired){%>
<div class="text"><font color=red><app:storeMessage key="newxpdex.label.indicatesRequiredField"/></font></div>
<%}%>
<div class="text"><font color=red id="emessage"><html:errors/></font></div>
<div id="wmessage" class="genericerror" align="left"  style="color:#FF0000; text-transform:uppercase"></div>
</p>
    <%
            }

        }
    %>
</DIV>
</TD>

</TR>
</TBODY>
</TABLE>
</td></tr>
</tbody>
</table>

<%
    if ("Y".equals(isMSIE)) {
%>
<script for=document event="onclick()">
<!--
document.all.CalFrame.style.display="none";
//-->
</script>
<% }  %>

<html:hidden property="command" value="CCCCCCC"/>
</html:form>




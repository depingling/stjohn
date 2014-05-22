<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.*" %>
<%@ page import="com.cleanwise.service.api.reporting.ReportingUtils" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.logic.ServiceProviderMgtReportLogic" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%@include file="/externals/calendar/calendar.jsp"%>

<script type="text/javascript" src="../externals/dojo_1.1.0/dojo/dojo.js"></script>

<script language="JavaScript1.2">
    <!--

	dojo.require("dojox.fx.easing");
    dojo.require("dojox.fx.scroll");
    dojo.require("clw.NewXpedx.form.DateTextBox");

    function setAndSubmit(fid, vv, value) {
        var aaa = document.forms[fid].elements[vv];
        aaa.value = value;
        aaa.form.submit();
        return false;
    }

    function resultInNewWindow() {
        //  document.SERVICE_PROVIDER_REPORT_FORM.submit();

        var loc = "serviceProviderMgtReportResult.do?action=get_delivery_sched"
                + "&beginMonth="
                + document.SERVICE_PROVIDER_REPORT_FORM.beginMonth.value
                + "&beginYear="
                + document.SERVICE_PROVIDER_REPORT_FORM.beginYear.value
                + "&endMonth="
                + document.SERVICE_PROVIDER_REPORT_FORM.endMonth.value
                + "&endYear="
                + document.SERVICE_PROVIDER_REPORT_FORM.endYear.value
                ;
        var prtwin = window.open(loc, "printer_friendly",
                "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
        prtwin.focus();
        window.location.href = "serviceProviderReporting.do?action=done_with_report"
        return false;
    }

	function parseResult(var1, var2, var3) {
        var errorMsgs = "";
        try {

            var root = var1.getElementsByTagName("response")[0];
            var body = root.getElementsByTagName("body")[0];

            var errors = body.getElementsByTagName("errors")[0];
            for (var i = 0; i < errors.childNodes.length; i++) {
                var error = errors.childNodes[i];
                for (var j = 0; j < error.childNodes.length; j++) {
                    var errorString = error.childNodes[j]
                    var mess = errorString.firstChild.nodeValue
                    errorMsgs += (i > 0?"<br>":"") + mess;
                }
            }

            var emessage = document.getElementById("emessage");
            if (emessage) {
                if (errorMsgs != "") {
                    emessage.innerHTML = "<b>" + errorMsgs + "</b>";
                    gotoId("emessage");
                } else {
                    var rCount = body.getElementsByTagName("reportmaxrowcount")[0];
                    var rCountValue = rCount.firstChild.nodeValue;
                    if (rCountValue > <%=ServiceProviderMgtReportLogic.MAX_HTML_ROWS%>) {
                        writemessage("wmessage", "<%=Utility.strNN(ClwI18nUtil.getMessage(request,"shop.warnings.reportHasBeenSentToExcel",new Object[]{ServiceProviderMgtReportLogic.MAX_HTML_ROWS},true))%>");
                        gotoId("wmessage");
                        window.location.href = "serviceProviderMgtReportResult.do?action=Download to Excel";
                    } else {
                        setAndSubmit('repParams', 'command', 'goToResult')
                    }
                }
            }
        } catch(e) {
        }
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

	function generateReport() {
        writemessage("wmessage","");
        writemessage("emessage","");
        dojo.xhrPost({load:parseResult,form:'repParams',handleAs:'xml',content:{action:'generateReport',requestType:'async',command:'next'}})
	};
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
    boolean dateSearchActualBegin = false;
    boolean dateSearchActualEnd = false;
    boolean dateSearchEstimateBegin = false;
    boolean dateSearchEstimateEnd = false;
%>

<bean:define id="IMGPath" type="java.lang.String"
             name="pages.store.images"/>

<bean:define id="theForm" name="SERVICE_PROVIDER_REPORT_FORM" type="com.cleanwise.view.forms.ServiceProviderMgtReportForm"/>

<jsp:include flush='true' page="/storeportal/locateStoreSite.jsp">
    <jsp:param name="jspFormAction" value="/serviceproviderportal/serviceProviderMgtReportLocDateSelect.do"/>
    <jsp:param name="jspFormName" value="SERVICE_PROVIDER_REPORT_FORM"/>
    <jsp:param name="jspSubmitIdent" value=""/>
    <jsp:param name="isSingleSelect" value="true"/>
    <jsp:param name="jspReturnFilterPropertyAlt" value="siteDummyConvert"/>
    <jsp:param name="jspReturnFilterProperty" value="siteFilter"/>
</jsp:include>


<html:form name="SERVICE_PROVIDER_REPORT_FORM" action="/serviceproviderportal/serviceProviderMgtReportLocDateSelect.do"
           styleId="repParams" type="com.cleanwise.view.forms.ServiceProviderMgtReportForm">


<TABLE cellSpacing="0" cellPadding="0" border="0">
<TBODY>
<TR>
    <TD rowspan="3">&nbsp;</TD>
</TR>

<TR class="reportbold">
    <TD width=169>Type of Report</TD>
    <TD>&nbsp;</TD>
    <logic:equal name="SERVICE_PROVIDER_REPORT_FORM" property="selectLocationFlag" value="true">
    	<TD width=335>&nbsp;</TD>
    </logic:equal>
    <TD colspan=25>
    <app:storeMessage key="report.text.SelectParameters" />
    </TD>
</TR>

<bean:define id="l_rpttype" name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" type="java.lang.String"/>

<html:hidden name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" value="<%=l_rpttype%>"/>

<%
    String xlateReportcat = ClwI18nUtil.getMessageOrNull(request, ReportingUtils.makeReportNameKey(l_rpttype));

    if (xlateReportcat == null) {
        xlateReportcat = l_rpttype;
    }
%>
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

<TD width="1" background="<%=IMGPath%>/cw_account-dot.gif">
    <IMG height="1" src="<%=IMGPath%>/cw_spacer.gif" width="1">
</TD>

<logic:equal name="SERVICE_PROVIDER_REPORT_FORM" property="selectLocationFlag" value="true">
    <TD class=text valign="top">
        <DIV class=fivemargin>
            <bean:define id="userSites" name="SERVICE_PROVIDER_REPORT_FORM" property="siteIdList"/>
            <logic:equal name="SERVICE_PROVIDER_REPORT_FORM" property="multipleLocSelectFlag" value="true">
                Please Choose a Location or Group of Locations:<BR><BR>
                <html:select name="SERVICE_PROVIDER_REPORT_FORM" property="selectedSites"
                             multiple="true" size="5">
                    <html:options collection="userSites"
                                  property="label" labelProperty="value"/>
                </html:select>
            </logic:equal>
            <logic:equal name="SERVICE_PROVIDER_REPORT_FORM" property="multipleLocSelectFlag" value="false">
                Please Choose a Location:<BR><BR>
                <html:select name="SERVICE_PROVIDER_REPORT_FORM" property="selectedSites">
                    <html:options collection="userSites"
                                  property="label" labelProperty="value"/>
                </html:select>
            </logic:equal>
        </DIV>
    </TD>
    <TD width=1 background="<%=IMGPath%>/cw_account-dot.gif">
        <IMG height=1 src="<%=IMGPath%>/cw_spacer.gif" width=1>
    </TD>
</logic:equal>

<TD class=text valign=top width=230>
<DIV class=fivemargin>

    <%
	Boolean l_flag11 = Boolean.FALSE;

		if (l_rpttype.indexOf("Order Information") >= 0
			|| l_rpttype.indexOf("Inventory Ordering Activity") >= 0 ) {
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
					<a href="serviceProviderMgtReportResult.do?action=init&year=<%=budgetPeriod.getStringValue(0)%>&budget_period=<%=budgetPeriod.getStringValue(1)%>&start_date=<%=budgetPeriod.getStringValue(2)%>&end_date=<%=budgetPeriod.getStringValue(3)%>">
						<%=budgetPeriod.getStringValue(2)%> -
						<%=budgetPeriod.getStringValue(3)%>
					</a>
				</td>
			<%}%>
			</tr>
		</logic:iterate>
    </table>
    <%} else {

            if (l_rpttype.indexOf("Budget") >= 0 ) {
                l_flag11 = Boolean.TRUE;
            } else {
                l_flag11 = Boolean.FALSE;
            }
        }


    if (l_rpttype.equals(RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE)) { %>

	<IMG height=10 src="<%=IMGPath%>/cw_from.gif" width=45>
		<html:select	name="SERVICE_PROVIDER_REPORT_FORM"
						property="beginMonth"
						disabled="<%=l_flag11.booleanValue()%>">
			<html:options	collection="Report.month.vector"
							property="label" labelProperty="value"/>
		</html:select>

		<html:select name="SERVICE_PROVIDER_REPORT_FORM" property="beginYear">
			<html:options	collection="Report.year.vector"
							property="label" labelProperty="value"/>
		</html:select>

		<BR><BR>

	<IMG height=9 src="<%=IMGPath%>/cw_to.gif" width=45>

		<html:select	name="SERVICE_PROVIDER_REPORT_FORM"
						property="endMonth"
						disabled="<%=l_flag11.booleanValue()%>">

			<html:options	collection="Report.month.vector"
							property="label" labelProperty="value"/>
		</html:select>

		<html:select name="SERVICE_PROVIDER_REPORT_FORM" property="endYear">
			<html:options collection="Report.year.vector" property="label" labelProperty="value"/>
		</html:select>

	<P>
		<A href="javascript:resultInNewWindow();"><IMG height=18 src="<%=IMGPath%>/cw_submit.gif" width=58 border=0></A>
	</P>

    <% } else { %>
<!-- Beg Generic Report Controls -->
    <%


    GenericReportControlViewVector grcVV = theForm.getReportControls();
       if(grcVV!=null && grcVV.size()>0) {

       for(int ii=0; ii<grcVV.size(); ii++) {

       GenericReportControlView grc = (GenericReportControlView) grcVV.get(ii);
       boolean rendered = false;
       String name = grc.getName();

       boolean mandatoryFl=true;
       dateSearchStart = false;
       dateSearchEnd = false;
       dateSearchActualBegin = false;
       dateSearchActualEnd = false;
       dateSearchEstimateBegin = false;
       dateSearchEstimateEnd = false;

       String mf = grc.getMandatoryFl();
       if(mf!=null){
        mf = mf.trim().toUpperCase();
       }

       if("N".equals(mf) || "NO".equals(mf) ||"0".equals(mf) ||"F".equals(mf) ||"FALSE".equals(mf)){
        mandatoryFl = false;
       }

       String controlEl = "reportControlValue["+ii+"]";

       if(
          "ACCOUNT".equals(name) ||
          "ACCOUNT_MULTI_OPT".equals(name) ||
          "STORE".equals(name) ||
          "STORE_OPT".equals(name) ||
          "SITE".equals(name) ||
          "DATE_FMT".equals(name) ||
          "CUSTOMER".equals(name)) {
             // nothing to render for these controls
             // when run through the customer reporting pages
             continue;
       }

       String label = grc.getLabel();
       if("BEG_DATE".equals(name) || "BEG_DATE_OPT".equals(name)){
            dateSearchStart = true;
            label="Begin Date (mm/dd/yyyy):";
       }else if("END_DATE".equals(name) || "END_DATE_OPT".equals(name)){
            dateSearchEnd = true;
            label="End Date (mm/dd/yyyy):";

	   } else if ("BEG_DATE_ACTUAL".equals(name) || "BEG_DATE_ACTUAL_OPT".equals(name)) {
            dateSearchActualBegin = true;
       } else if ("END_DATE_ACTUAL".equals(name) || "END_DATE_ACTUAL_OPT".equals(name)) {
            dateSearchActualEnd = true;
       } else if ("BEG_DATE_ESTIMATE".equals(name) || "BEG_DATE_ESTIMATE_OPT".equals(name)) {
            dateSearchEstimateBegin = true;
       }else if ("END_DATE_ESTIMATE".equals(name) || "END_DATE_ESTIMATE_OPT".equals(name)) {
            dateSearchEstimateEnd = true;
       } else if("endYear_OPT".equals(name)){

	%>
				<br>OR<br><br>
				<b>Select End Year:</b>
				<html:select name="SERVICE_PROVIDER_REPORT_FORM" property="<%=controlEl%>">
				<html:options collection="Report.year.vector" property="label" labelProperty="value"/>
				</html:select>

	<%

		mandatoryFl=false;
		rendered=true;
	   }else if("endMonth_OPT".equals(name)){

	%>
				<br><br>
				<b>Select End Month:</b>
				<html:select name="SERVICE_PROVIDER_REPORT_FORM" property="<%=controlEl%>" >
				<html:options collection="Report.month.vector" property="label" labelProperty="value"/>
				</html:select>

	<%

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
    <%--name="SERVICE_PROVIDER_REPORT_FORM"--%>
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
    } else if ("BUDGET_PERIODS_INFO".equals(name)) {

    //--START RENDERING "BUDGET_PERIOD_INFO"--
    rendered = true;

    String xlateyr = ClwI18nUtil.getMessageOrNull(request, "report.text.selectBudgetPeriods");
    if ( null == xlateyr ){
     xlateyr = "Please select Budget Periods";
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
        <logic:iterate  id="budgetPeriod" indexId="i"
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
                String period = "period["+i+"]";
                %>
                <html:multibox property="selectedPeriods" value="<%=val%>"/>
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
				<a href="javascript:document.getElementById('SINGLEDAYSBACK').value='<%=thisDateFormatted%>';generateReport();"><%=thisDate%></a>
				</td>
            <%}%>

            </tr>

		<%}%>
		<input type="hidden" id="SINGLEDAYSBACK" name="<%=controlEl%>" value=""/>
    </table>


	<%--END RENDERING "SINGLE_DAYS_BACK_7"--%>
    <%
    }else if ("SITES".equals(name) || "SITES_OPT".equals(name) || "SITE_MULTI_OPT".equals(name) || "SITE_MULTI".equals(name) ) {
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
           value='<app:storeMessage key="report.control.clearSites" />'/>
    <%
        }
    %>
    <input type="submit" property="action" class="text"
           onclick="setAndSubmit('repParams','command','Locate Site')"
           value='<app:storeMessage key="report.control.selectSites" />'/>
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
    <b>Select Distributor:</b>
    <html:select property="<%=controlEl%>">
        <html:option value="">
            <app:storeMessage  key="admin.select"/>
        </html:option>
        <html:options collection="Report.distributor.vector" property="busEntityId" labelProperty="shortDesc"/>
    </html:select>
</div>
<br>
    <%
    }
    %>
    <%--END RENDERING "DISTRIBUTOR"--%>


    <%--START RENDERING "INVOICE_TYPE_2"--%>
    <%
    if ("INVOICE_TYPE_2".equals(name) || "INVOICE_TYPE_2_OPT".equals(name)) {
  rendered = true;

    %>
<div class=subheadergeneric>
    <b>Select Invoice Type:</b><br>
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
    <input TYPE="TEXT" name="<%=controlEl%>" ID="enddate"/>
   <%
     Locale loc=ClwI18nUtil.getUserLocale(request);

     if((loc.getCountry()).equals("US")) {
         if ("Y".equals(isMSIE)) {
    %>
     <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.SERVICE_PROVIDER_REPORT_FORM.DEFSADD1, document.getElementById('enddate'), null, -7300, 7300);" title="Choose Date">
        <img name="DEFSADD1" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="middle" style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true">
     </a>
    <% } else { %>
          <a href="#" onClick="showCalendar('enddate', event); return false;"
             title="Choose Date">
            <img name="DEFSADD1" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                 onmouseover="window.status='Choose Date';return true"
                 onmouseout="window.status='';return true">
          </a>
    	<% } %>
    <% } %>
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
    <input TYPE="TEXT" name="<%=controlEl%>" ID="startdate"/>
	<%
	Locale loc=ClwI18nUtil.getUserLocale(request);

	if((loc.getCountry()).equals("US")){
        if ("Y".equals(isMSIE)) {
    %>

	  <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.SERVICE_PROVIDER_REPORT_FORM.STARTDATE, document.getElementById('startdate'), null, -7300, 7300);" title="Choose Date">
              <img name="STARTDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true">
          </a>
	<% } else { %>
          <a href="#" onClick="showCalendar('startdate', event); return false;"
             title="Choose Date">
            <img name="STARTDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                 onmouseover="window.status='Choose Date';return true"
                 onmouseout="window.status='';return true">
          </a>
    	<% } %>
    <% }
    } else if (dateSearchActualBegin) {
    %>
        <br/>
        <br/>
    <DIV class="subheadergeneric">
        <b>
            <nobr>
                <app:storeMessage key="userWorkOrder.text.dateType.actual"/>&nbsp;
                <app:storeMessage key="report.text.BeginDate"/>
                (<%=ClwI18nUtil.getUIDateFormat(request)%>)
            </nobr>
        </b><br>
        <input TYPE="TEXT" name="<%=controlEl%>" ID="actualBeginDate"/>
    	<%
    	Locale loc=ClwI18nUtil.getUserLocale(request);

    	if((loc.getCountry()).equals("US")) {
            if ("Y".equals(isMSIE)) {
    	%>
    	  <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.SERVICE_PROVIDER_REPORT_FORM.ACTUALBEGINDATE, document.getElementById('actualBeginDate'), null, -7300, 7300);" title="Choose Date">
                  <img name="ACTUALBEGINDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true">
              </a>
         <% } else {%>
          <a href="#" onClick="showCalendar('actualBeginDate', event); return false;"
             title="Choose Date">
            <img name="ACTUALBEGINDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                 onmouseover="window.status='Choose Date';return true"
                 onmouseout="window.status='';return true">
          </a>
    	<% } %>
    <% }
    } else if (dateSearchActualEnd) {
    %>
        <br/>
        <br/>
    <DIV class="subheadergeneric">
        <b>
            <nobr>
                <app:storeMessage key="userWorkOrder.text.dateType.actual"/>&nbsp;
                <app:storeMessage key="report.text.EndDate"/>
                (<%=ClwI18nUtil.getUIDateFormat(request)%>)
            </nobr>
        </b><br>
        <input TYPE="TEXT" name="<%=controlEl%>" ID="actualEndDate"/>
    	<%
    	Locale loc=ClwI18nUtil.getUserLocale(request);

    	if((loc.getCountry()).equals("US")) {
            if ("Y".equals(isMSIE)) {
    	%>
    	  <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.SERVICE_PROVIDER_REPORT_FORM.ACTUALENDDATE, document.getElementById('actualEndDate'), null, -7300, 7300);" title="Choose Date">
                  <img name="ACTUALENDDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true">
              </a>
    	<% } else {%>
          <a href="#" onClick="showCalendar('actualEndDate', event); return false;"
             title="Choose Date">
            <img name="ACTUALENDDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                 onmouseover="window.status='Choose Date';return true"
                 onmouseout="window.status='';return true">
          </a>
    	<% } %>
    <% }
    } else if (dateSearchEstimateBegin) {
    %>
        <br/>
        <br/>
    <DIV class="subheadergeneric">
        <b>
            <nobr>
                <app:storeMessage key="userWorkOrder.text.dateType.estimate"/>&nbsp;
                <app:storeMessage key="report.text.BeginDate"/>
                (<%=ClwI18nUtil.getUIDateFormat(request)%>)
            </nobr>
        </b><br>
        <input TYPE="TEXT" name="<%=controlEl%>" ID="estimateBeginDate"/>
    	<%
    	Locale loc=ClwI18nUtil.getUserLocale(request);

    	if((loc.getCountry()).equals("US")) {
            if ("Y".equals(isMSIE)) {
    	%>
    	  <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.SERVICE_PROVIDER_REPORT_FORM.ESTIMATEBEGINDATE, document.getElementById('estimateBeginDate'), null, -7300, 7300);" title="Choose Date">
                  <img name="ESTIMATEBEGINDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true">
              </a>
    	<% } else {%>
          <a href="#" onClick="showCalendar('estimateBeginDate', event); return false;"
             title="Choose Date">
            <img name="ESTIMATEBEGINDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                 onmouseover="window.status='Choose Date';return true"
                 onmouseout="window.status='';return true">
          </a>
    	<% } %>
    <% }
    } else if (dateSearchEstimateEnd) {
    %>
        <br/>
        <br/>
    <DIV class="subheadergeneric">
        <b>
            <nobr>
                <app:storeMessage key="userWorkOrder.text.dateType.estimate"/>&nbsp;
                <app:storeMessage key="report.text.EndDate"/>
                (<%=ClwI18nUtil.getUIDateFormat(request)%>)
            </nobr>
        </b><br>
        <input TYPE="TEXT" name="<%=controlEl%>" ID="estimateEndDate"/>
    	<%
    	Locale loc=ClwI18nUtil.getUserLocale(request);

    	if((loc.getCountry()).equals("US")) {
            if ("Y".equals(isMSIE)) {
    	%>
    	  <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.SERVICE_PROVIDER_REPORT_FORM.ESTIMATEENDDATE, document.getElementById('estimateEndDate'), null, -7300, 7300);" title="Choose Date">
                  <img name="ESTIMATEENDDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true">
              </a>
    	<% } else { %>
          <a href="#" onClick="showCalendar('estimateEndDate', event); return false;"
             title="Choose Date">
            <img name="ESTIMATEENDDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                 onmouseover="window.status='Choose Date';return true"
                 onmouseout="window.status='';return true">
          </a>
    	<% } %>
    <% }
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
        <html:text name="SERVICE_PROVIDER_REPORT_FORM" property="<%=controlEl%>"/>
        <%
            if (mandatoryFl) {
        %>
        <span class="reqind">*</span>
		<%isRequired=true;%>
        <%
            }
        %>
    </DIV>
    <br>
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
            <nobr>
                <html:radio styleId="<%=controlEl%>" property="<%=controlEl%>" value="<%=val%>"/>
                <%=elLabel%>
            </nobr>
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
        <br>
    <%}%>
    <%
                }
            }
        } //End of generic report controls
		}
    %>
	<%String reportName = xlateReportcat;
	if(!reportName.equals("Daily Back Order Report")){%>
    <!-- End Generic Report Controls -->
    <P>
        <input type="submit" property="action" class="reportsubmit"
               onclick="setAndSubmit('repParams','command','next')"
               value='<app:storeMessage key="global.action.label.submit" />'/>

    </P>
	<%}%>
<% if(isRequired) { %>
   <div class="text"><font color=red><app:storeMessage key="newxpdex.label.indicatesRequiredField"/></font></div>
<% } %>
<div class="text"><font color=red id="emessage"><html:errors/></font></div>
<div id="wmessage" class="genericerror" align="left"  style="color:#FF0000; text-transform:uppercase"></div>
</p>
    <%
           // }

        }
    %>
</DIV>
</TD>

</TR>
</TBODY>
</TABLE>

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




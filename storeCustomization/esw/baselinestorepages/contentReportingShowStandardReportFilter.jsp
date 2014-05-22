<%@page import="com.cleanwise.service.api.value.PairView"%>
<%@page import="com.cleanwise.service.api.value.PairViewVector"%>
<%@page import="com.cleanwise.service.api.value.SiteViewVector"%>
<%@page import="com.cleanwise.view.utils.RequestPropertyNames"%>
<%@page import="com.cleanwise.service.api.value.AccountUIViewVector"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.cleanwise.service.api.value.GenericReportControlView"%>
<%@page import="com.cleanwise.service.api.value.GenericReportControlViewVector"%>
<%@page import="com.cleanwise.service.api.reporting.ReportingUtils"%>
<%@page import="com.cleanwise.view.forms.CustAcctMgtReportForm"%>
<%@page import="com.espendwise.view.forms.esw.ReportingForm"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="com.cleanwise.service.api.dto.OrderSearchDto"%>
<%@page import="com.cleanwise.service.api.dto.LocationBudgetChartDto"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<bean:define id="reportingForm" name="esw.ReportingForm"  type="com.espendwise.view.forms.esw.ReportingForm"/>
<script type="text/javascript" src="../../externals/dojo_1.1.0/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false">
</script>
<%
	String reportingLink = "userportal/esw/reporting.do";
	String newrepl = reportingLink+"?operation=filterGenericReportCategories";
	CustAcctMgtReportForm  customerReportForm = reportingForm.getCustomerReportingForm();
	
	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	
	//Report Name
	String reportName = customerReportForm.getReportTypeCd();
 	String reportName4mMR = ClwI18nUtil.getMessageOrNull(request, ReportingUtils.makeReportNameKey(reportName));
 	String reportDesc4mMR = ClwI18nUtil.getMessageOrNull(request, ReportingUtils.makeReportDescriptionKey(reportName));
    if (Utility.isSet(reportName4mMR)) {
    	reportName = reportName4mMR;
    }
    
    //Report Description
    String reportDesc = customerReportForm.getReportTypeDesc();
    if (Utility.isSet(reportDesc4mMR)) {
    	reportDesc = reportDesc4mMR;
    }
	if(!Utility.isSet(reportDesc)) {
		reportDesc = "";
	}

	String allCategories = ClwMessageResourcesImpl.getMessage(request, "reporting.filter.allCategories");
    
%>

<script type="text/javascript">
	var loadingMessageId = 0;
	var count = 0;
	 $(document).ready(function(){	
	 	writemessage("loadingMessage","");
		initializeCategoryLevels();		
	});
	
	function submitReport(year,budget_period,start_date,end_date){
		document.getElementById('yearId').value=year;
		document.getElementById('budget_periodId').value=budget_period;
		document.getElementById('start_dateId').value=start_date;
		document.getElementById('end_dateId').value=end_date;
		document.getElementById('operationId').value='<%=Constants.PARAMETER_OPERATION_VALUE_SEARCH_STANDARD_REPORT %>';
		submitForm('reportStandardFilterId');
	}
	
	function filterCategories(level) {
		
		document.getElementById('operationId').value='<%=Constants.PARAMETER_OPERATION_VALUE_FILTER_GENERIC_REPORT_CATEGORIES %>';
		
		$.post("<%=request.getContextPath()%>/<%=reportingLink%>",
			$("#reportingStandardId").serialize(),
		       function(response, ioArgs) {
		    
			if(level=='level1'){
				$('#cat2').children().remove();
				$('#cat3').children().remove();
				$('#cat4').children().remove();
			
				if(response.catOptions2!=null){
		
			
					for (var i = 0; i< response.catOptions2.length; i++) {
					    
					    var name = response.catOptions2[i].name;
					    var val = response.catOptions2[i].id;
					    var option = new Option(name, val);
					    var dropdownList = $("#cat2")[0];
			
					    //$('#cat2').append(new Option(name, val));
					    if ($.browser.msie) {
						dropdownList.add(option);
					    }else {
						dropdownList.add(option, null);
					    }
					}
				
				}
				
			}else if(level=='level2'){
				$('#cat3').children().remove();
				$('#cat4').children().remove();
				
				if(response.catOptions3!=null){
		
					$('#cat3').children().remove();
					$('#cat4').children().remove();
					for (var i = 0; i< response.catOptions3.length; i++) {
					    var name = response.catOptions3[i].name;
					    var val = response.catOptions3[i].id;
					    var option = new Option(name, val);
					    var dropdownList = $("#cat3")[0];
					    //$('#cat3').append(new Option(name, val));
					    if ($.browser.msie) {
						dropdownList.add(option);
					    }else {
						dropdownList.add(option, null);
					    }
					}
			
				}
				
			}else if(level=='level3'){
				$('#cat4').children().remove();
				
				if(response.catOptions4!=null){
					$('#cat4').children().remove();
					for (var i = 0; i< response.catOptions4.length; i++) {
					    var name = response.catOptions4[i].name;
					    var val = response.catOptions4[i].id;
					    var option = new Option(name, val);
					    var dropdownList = $("#cat4")[0];
					    //$('#cat4').append(new Option(name, val));
					    if ($.browser.msie) {
						dropdownList.add(option);
					    }else {
						dropdownList.add(option, null);
					    }
					}
			
				}
				
			}
			
		    initializeCategoryLevels();
		    
		    for (var i = 0; i< response.categoriesSelected.length; i++) {
			dojo.byId("cat2").value = response.categoriesSelected[i].cat2Selected;
			dojo.byId("cat3").value = response.categoriesSelected[i].cat3Selected;
			dojo.byId("cat4").value = response.categoriesSelected[i].cat4Selected;
			
		    }
		}
		);
	
	}
	
	function initializeCategoryLevels(){

	  if ($('#cat1').find(":selected").text() == '<%=allCategories%>') {
		
		$('p.level2').addClass('hide');			
		$('p.level3').addClass('hide');
		$('p.level4').addClass('hide');
	   }else{
		
		if($('#cat2').find(":selected").text().length != 0 && $("#cat2 option").length >1){
			$('p.level2').removeClass('hide');
		}else{
			$('p.level2').addClass('hide');
		}
	
		if ($('#cat2').find(":selected").text() == '<%=allCategories%>') {
		
			$('p.level3').addClass('hide');
			$('p.level4').addClass('hide');
		}else{
		
			if($('#cat3').find(":selected").text().length != 0 && $("#cat3 option").length >1){
				$('p.level3').removeClass('hide');
			}else{
				$('p.level3').addClass('hide');
			}
			
			if ($('#cat3').find(":selected").text() == '<%=allCategories%>') {
		
			    $('p.level4').addClass('hide');
			}else{
		
			    if($('#cat4').find(":selected").text().length != 0 && $("#cat4 option").length >1){
				    $('p.level4').removeClass('hide');
			    }else{
				    $('p.level4').addClass('hide');
			    }
			}
		 }    
	   }    
	}
	
	function generateReport() {
		var messageDiv = dojo.byId('errorsAndMessagesPageHold');
		if (messageDiv != null)
			messageDiv.style.display = "none";

		writemessage("loadingMessage",'<%=Utility.encodeForJavaScript(
		        ClwMessageResourcesImpl.getMessage(request, "global.label.loading"))%>');
		
        document.getElementById('operationId').value='searchStandardReport';
        $.post("<%=request.getContextPath()%>/<%=reportingLink%>",
			$("#reportingStandardId").serialize(),
		       function(response, ioArgs) { 
  			writemessage("loadingMessage","");
	    	clearInterval ( loadingMessageId );
	    	var errorMsgs = "";
	        try {
				var root = response.getElementsByTagName("response")[0];
			 	if (root != null && 'undefined' != typeof root) {
					var forwardAction = root.getAttribute("forwardAction");
					setFieldsAndSubmitForm('reportingStandardId','operationId',forwardAction);
	         	}
	        } catch(e) {
	        }
		}, 'xml');
        loadingMessageId = setInterval ("showLoadingMessage()",2000); 
    };
    function showLoadingMessage(){
    	if (count == 5){ // make request to keep session alive every 10 seconds
    		count = 0;    		
    		dojo.xhrGet({url: "../../keepAlive.do?action=updateLoadingMessage&loadingMessage=Loading",handleAs:'xml',sync:false})
    	}else{
    		count += 1;
    	}
    	
    	var loadingMessage = document.getElementById("loadingMessageHidden").value;
    	if (loadingMessage.length==10)
    		loadingMessage = "Loading";
    	else
    		loadingMessage += ".";
    	
 		writemessage("loadingMessage",loadingMessage);
    	
	}
	
	function writemessage(id, message) {
		var messageDiv = dojo.byId('loadingMessage');
		if (message == ""){
			messageDiv.style.display = "none"; //to hide it
		}else{
			messageDiv.style.display = "block"; //to show it 
			dojo.byId(id).innerHTML = "<p>" + message + "<br/></p>";
			document.getElementById("loadingMessageHidden").value = message;
		}
    }
    
</script>

<html:form styleId="reportStandardFilterId" action="<%=reportingLink %>">
     <html:hidden property="<%=Constants.PARAMETER_OPERATION%>" 
             value="<%=Constants.PARAMETER_OPERATION_VALUE_SEARCH_STANDARD_REPORT%>"/>
			 <input type="hidden" name="year" value="" id="yearId" />
			 <input type="hidden" name="budget_period" value="" id="budget_periodId" />
			 <input type="hidden" name="start_date" value="" id="start_dateId" />
			 <input type="hidden" name="end_date" value="" id="end_dateId" />
</html:form>

<app:setLocaleAndImages/>
<div class="singleColumn clearfix" id="contentWrapper">
<div id="content">	
				                   
				<!-- Begin: Error Message -->
				<%
					String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
				%>
				<div id="errorsAndMessagesPageHold">
				<jsp:include page="<%=errorsAndMessagesPage %>"/>
				</div>
				<div id="loadingMessage" class="infoMessage"></div>
				<input type="hidden" id="loadingMessageHidden" name="loadingMessageHidden" value=""/>
			<!-- End: Error Message -->					
               
                <!-- Start Box -->
                <div class="boxWrapper topMargin smallMargin squareBottom">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">
                            <h1 class="main">
                            	<%=Utility.encodeForHTML(reportName) %>
                            </h1>						
							
							<%
							//STJ-4565
							GenericReportControlViewVector grcVV = customerReportForm.getReportControls();
							boolean showSelectParams = false;
							boolean showRequired = false;
							if(customerReportForm.getSelectLocationFlag()  
									|| reportName.indexOf(Constants.REPORT_NAME_ORDER_INFORMATION) >= 0
									|| reportName.indexOf(Constants.REPORT_NAME_INV_ORDERING_ACTIVITY) >= 0
									|| reportName.equals(RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE)
									) {
								
								showSelectParams = true;
							} 
							if (Utility.isSet(grcVV)) {
								int reportControlCount = 0;
								boolean mandatoryFl=true;
								for(int ii=0; ii<grcVV.size(); ii++) {
									GenericReportControlView grc = (GenericReportControlView) grcVV.get(ii);
									mandatoryFl = true;
									mandatoryFl = Utility.isFalse(grc.getMandatoryFl());

									if (grc.getIgnore()!=null && grc.getIgnore()==true) {
										reportControlCount ++;
										continue;
								    }

								    String  name = grc.getName();
								    if(Constants.REPORT_CONTROL_ACCOUNT.equals(name) ||
									  Constants.REPORT_CONTROL_ACCOUNT_MULTI.equals(name) ||
									  Constants.REPORT_CONTROL_ACCOUNT_MULTI_OPT.equals(name) ||
									//Constants.REPORT_CONTROL_LOCATE_ACCOUNT_MULTI_OPT.equals(name) ||
									  Constants.REPORT_CONTROL_STORE.equals(name) ||
									  Constants.REPORT_CONTROL_STORE_OPT.equals(name) ||
									  Constants.REPORT_CONTROL_STORE_SELECT.equals(name) ||
									  Constants.REPORT_CONTROL_SITE.equals(name) ||
									  Constants.REPORT_CONTROL_DATE_FMT.equals(name) ||
									  Constants.REPORT_CONTROL_CUSTOMER.equals(name)) {
										 // nothing to render for these controls
										 // when run through the customer reporting pages
									  reportControlCount++;
									 
								    } else if(Constants.REPORT_CONTROL_END_YEAR_OPT.equals(name) || 
										Constants.REPORT_CONTROL_END_MONTH_OPT.equals(name)){
										mandatoryFl = false;
									} 

									if(mandatoryFl) {
										showRequired = true;
									}
								}
								if(reportControlCount!=grcVV.size()) {
									showSelectParams = true;
								}
							}
								String reportType = ClwI18nUtil.getMessageOrNull(request, ReportingUtils.makeReportNameKey(reportName));
								if (reportType == null) {
									reportType = reportName;
							    }
								if(!reportType.equals(Constants.REPORT_NAME_DAILY_BACK_ORDER_REPORT)){
							%>
							
								<html:link href="javascript:generateReport()" styleClass="blueBtnLargeExt">
					                  <span><app:storeMessage  key="global.action.label.submit" /></span>
								 </html:link>
							<%} %>
							<div class="twoColBox">
                                <div class="column width80">
									<p>
									 	<%=Utility.encodeForHTML(reportDesc) %> 
									</p>
								</div>
							</div>
							<hr>
						<%
						if(showSelectParams) {
						%>
							<h2><app:storeMessage key="report.text.SelectParameters" /></h2>
							<% if(showRequired) { %>
                            <p class="required right">* <app:storeMessage key="global.text.required" /></p>     
                       <%	}
						} %>                     
         <html:form styleId="reportingStandardId" action="<%=reportingLink %>">
     		<html:hidden property="<%=Constants.PARAMETER_OPERATION%>" styleId="operationId"
             value=""/>
			 

                            <div class="twoColBox">
                                <div class="column width80">
                                    <table>
                                        <colgroup>
                                            <col>
                                            <col>
											<col>
                                        </colgroup>
                                        
										<tbody>
	<!-- Being: Location Selection -->									
											<logic:equal name="esw.ReportingForm" property="customerReportingForm.selectLocationFlag" value="true">
											<tr>
												<logic:equal name="esw.ReportingForm" property="customerReportingForm.multipleLocSelectFlag" value="true">
												<td>
                                                	 <app:storeMessage key="report.text.chooseGroupLocations" />
                                            	</td>
                                            	<td>
									                <html:select name="esw.ReportingForm" property="customerReportingForm.selectedSites" multiple="true" size="5">
									                    <html:options collection="userSites" property="label" labelProperty="value"/>
									                </html:select>
                                            	</td>
                                            	</logic:equal>
                                            	<logic:equal name="esw.ReportingForm" property="customerReportingForm.multipleLocSelectFlag" value="false">
												<td>
                                                	 <app:storeMessage key="report.text.chooseLocation" />
                                            	</td>
                                            	<td>
									                <html:select name="esw.ReportingForm" property="customerReportingForm.selectedSites">
											             <html:options collection="userSites" property="label" labelProperty="value"/>
											        </html:select>
                                            	</td>
                                            	</logic:equal>
										   </tr>
										</logic:equal>
	<!-- End: Location Selection -->
	<!-- Begin: Budget Periods (ORDER INFORMATION) -->
										<%
										Boolean reportTypeFlag = Boolean.FALSE;
										if (reportName.indexOf(Constants.REPORT_NAME_ORDER_INFORMATION) >= 0
												|| reportName.indexOf(Constants.REPORT_NAME_INV_ORDERING_ACTIVITY) >= 0 ) {
										%>
										
										<tr>
											<td><app:storeMessage key="report.text.selectBudgetPeriod" /></td>
											<td>
											
												<logic:iterate id="budgetPeriod" name="Report.budget.periods"
															   type="com.cleanwise.service.api.dao.UniversalDAO.dbrow">
													<p class="inputRow">
													<label>
													<%if(budgetPeriod != null && budgetPeriod.getColumnCount() > 3){%>
														
															<%=budgetPeriod.getStringValue(0)%>
		
																<a href="#" onclick="javascript:submitReport('<%=budgetPeriod.getStringValue(0) %>','<%=budgetPeriod.getStringValue(1) %>','<%=budgetPeriod.getStringValue(2) %>','<%=budgetPeriod.getStringValue(3) %>')" >

															   	<%=budgetPeriod.getStringValue(2)%> -
															  	<%=budgetPeriod.getStringValue(3)%>
															   </a>
														
													<%}%>
										             </label> </p>
													
												</logic:iterate>
										    
											</td>
										</tr>
	<!-- End: Budget Periods (ORDER INFORMATION)-->		
										<%}else {
								            if (reportName.indexOf(Constants.REPORT_NAME_BUDGET) >= 0 ) {
								            	reportTypeFlag = Boolean.TRUE;
								            } else {
								            	reportTypeFlag = Boolean.FALSE;
								            }
								      	}
										
										if (reportName.equals(RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE)) {
										%>
	<!-- Begin: Delivery Schedule Report -->			
										<tr>
											<td><app:storeMessage key="userportal.esw.standardReports.beginMonth" /></td>
											<td>
												<html:select name="esw.ReportingForm"
																property="customerReportingForm.beginMonth"
																disabled="<%=reportTypeFlag.booleanValue()%>">
													<html:options	collection="Report.month.vector"
																	property="label" labelProperty="value"/>
												</html:select>
											</td>
										</tr>
										<tr>
											<td><app:storeMessage key="userportal.esw.standardReports.beginYear" /></td>
											<td>
												<html:select name="esw.ReportingForm" property="customerReportingForm.beginYear">
													<html:options	collection="Report.year.vector"
																	property="label" labelProperty="value"/>
												</html:select>
											</td>
										</tr>
										<tr>
											<td><app:storeMessage key="userportal.esw.standardReports.endMonth" /></td>
											<td>
												<html:select name="esw.ReportingForm"
																property="customerReportingForm.endMonth"
																disabled="<%=reportTypeFlag.booleanValue()%>">
										
													<html:options collection="Report.month.vector"
																	property="label" labelProperty="value"/>
												</html:select>
											</td>
										</tr>
										<tr>
											<td><app:storeMessage key="userportal.esw.standardReports.endYear" /></td>
											<td>
											<html:select name="esw.ReportingForm" property="customerReportingForm.endYear">
												<html:options collection="Report.year.vector" property="label" labelProperty="value"/>
											</html:select>
											</td>
										</tr>
	<!-- End: Delivery Schedule Report  -->		
										<%} else {%>
	<!-- Begin: Generic Report Controls -->
	<!-- ============================================================================================================================ -->										
											
											 <%
											 
											 	boolean dateSearchStart = false, dateSearchEnd = false;
											    boolean dateSearchActualBegin = false;
											    boolean dateSearchActualEnd = false;
											    boolean dateSearchEstimateBegin = false;
											    boolean dateSearchEstimateEnd = false;
											    //boolean isRequired = false;

											    List<String> invoiceStatusCodeDV = new ArrayList<String>();
											    String select = ClwMessageResourcesImpl.getMessage(request, "admin2.select");
											    invoiceStatusCodeDV.add(select);
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
											    
												String defaultDateFormat = ClwI18nUtil.getUIDateFormat(request);
												if(Utility.isSet(defaultDateFormat)) {
													defaultDateFormat = defaultDateFormat.toLowerCase();
												}

												if(grcVV!=null && grcVV.size()>0) {
												
												       for(int ii=0; ii<grcVV.size(); ii++) {
												
												       GenericReportControlView grc = (GenericReportControlView) grcVV.get(ii);
												       String controlEl = "customerReportingForm.reportControlValue["+ii+"]";
												       if (grc.getInvisible() != null && grc.getInvisible().booleanValue()){
											           %>
											           	   <html:hidden property="<%=controlEl%>" value="<%=grc.getValue()%>"/>
											           <% continue;}
												       boolean rendered = false;
												       String  name = grc.getName();
												       boolean mandatoryFl=true;
												       dateSearchStart = false;
												       dateSearchEnd = false;
												       dateSearchActualBegin = false;
												       dateSearchActualEnd = false;
												       dateSearchEstimateBegin = false;
												       dateSearchEstimateEnd = false;
												       boolean dateSearchReceivedFrom = false, dateSearchReceivedTo = false;
												
												       String mf = grc.getMandatoryFl();
												       mandatoryFl = Utility.isFalse(mf);
												

													   if (grc.getIgnore()!=null && grc.getIgnore()==true) {
												             continue;
													   }
													   
												       if(Constants.REPORT_CONTROL_ACCOUNT.equals(name) ||
												          Constants.REPORT_CONTROL_ACCOUNT_MULTI.equals(name) ||
												          Constants.REPORT_CONTROL_ACCOUNT_MULTI_OPT.equals(name) ||
														//Constants.REPORT_CONTROL_LOCATE_ACCOUNT_MULTI_OPT.equals(name) ||
												          Constants.REPORT_CONTROL_STORE.equals(name) ||
												          Constants.REPORT_CONTROL_STORE_OPT.equals(name) ||
												          Constants.REPORT_CONTROL_STORE_SELECT.equals(name) ||
												          Constants.REPORT_CONTROL_SITE.equals(name) ||
												          Constants.REPORT_CONTROL_DATE_FMT.equals(name) ||
												          Constants.REPORT_CONTROL_CUSTOMER.equals(name)) {
												             // nothing to render for these controls
												             // when run through the customer reporting pages
												             continue;
												       }
												
												       String label = grc.getLabel();
												       if(Constants.REPORT_CONTROL_BEG_DATE.equals(name) || Constants.REPORT_CONTROL_BEG_DATE_OPT.equals(name)){
												            dateSearchStart = true;
												            label=ClwMessageResourcesImpl.getMessage(request, "report.text.BeginDate");
												       }else if(Constants.REPORT_CONTROL_END_DATE.equals(name) || Constants.REPORT_CONTROL_END_DATE_OPT.equals(name)){
												            dateSearchEnd = true;
												            label=ClwMessageResourcesImpl.getMessage(request, "report.text.EndDate");
												
													   } else if (Constants.REPORT_CONTROL_BEG_DATE_ACTUAL.equals(name) || Constants.REPORT_CONTROL_BEG_DATE_ACTUAL_OPT.equals(name)) {
												            dateSearchActualBegin = true;
												       } else if (Constants.REPORT_CONTROL_END_DATE_ACTUAL.equals(name) || Constants.REPORT_CONTROL_END_DATE_ACTUAL_OPT.equals(name)) {
												            dateSearchActualEnd = true;
												       } else if (Constants.REPORT_CONTROL_BEG_DATE_ESTIMATE.equals(name) || Constants.REPORT_CONTROL_BEG_DATE_ESTIMATE_OPT.equals(name)) {
												            dateSearchEstimateBegin = true;
												       }else if (Constants.REPORT_CONTROL_END_DATE_ESTIMATE.equals(name) || Constants.REPORT_CONTROL_END_DATE_ESTIMATE_OPT.equals(name)) {
												            dateSearchEstimateEnd = true;
												       }else if (Constants.REPORT_CONTROL_RECEIVED_DATE_FROM.equals(name) || Constants.REPORT_CONTROL_RECEIVED_DATE_FROM_OPT.equals(name)) {
												            dateSearchReceivedFrom = true;
												       }else if (Constants.REPORT_CONTROL_RECEIVED_DATE_TO.equals(name) || Constants.REPORT_CONTROL_RECEIVED_DATE_TO_OPT.equals(name)) {
												            dateSearchReceivedTo = true;
												       }
												       	
												       else if(Constants.REPORT_CONTROL_CATEGORIES_OPT.equals(name)){
														mandatoryFl=false;
														rendered=true;
												       %>
												      
												       <tr>
														<td>
															<app:storeMessage  key="reporting.label.productCategory" />
														</td>
														<td>
														<p class="level1"><app:storeMessage  key="reporting.label.level1" /><br />
															<html:select styleId="cat1" name="esw.ReportingForm" property="customerReportingForm.genericReportingInfo.cat1Selected" onchange="filterCategories('level1')">
															<html:optionsCollection name="esw.ReportingForm" property="customerReportingForm.genericReportingInfo.category1" label="label" value="value"/>
															</html:select>
														</p>
														</td>
														
												       </tr>
												       <tr>	<td> </td>
														<td>
														<p class="level2 hide"><app:storeMessage  key="reporting.label.level2" /><br />
															<html:select styleId="cat2" name="esw.ReportingForm" property="customerReportingForm.genericReportingInfo.cat2Selected" onchange="filterCategories('level2')">
															<html:optionsCollection name="esw.ReportingForm" property="customerReportingForm.genericReportingInfo.category2" label="label" value="value"/>
															</html:select>
														</p>
														</td>
												       </tr>
												       <tr>	<td> </td>
														<td>
														<p class="level3 hide"><app:storeMessage  key="reporting.label.level3" /><br />
															<html:select styleId="cat3" name="esw.ReportingForm" property="customerReportingForm.genericReportingInfo.cat3Selected" onchange="filterCategories('level3')">
															<html:optionsCollection name="esw.ReportingForm" property="customerReportingForm.genericReportingInfo.category3" label="label" value="value"/>
															</html:select>
														</p>
														</td>
												       </tr>
												       
												       <tr>	<td> </td>
														<td>
														<p class="level4 hide"><app:storeMessage  key="reporting.label.level4" /><br />
															<html:select styleId="cat4" name="esw.ReportingForm" property="customerReportingForm.genericReportingInfo.cat4Selected" onchange="filterCategories('level4')">
															<html:optionsCollection name="esw.ReportingForm" property="customerReportingForm.genericReportingInfo.category4" label="label" value="value"/>
															</html:select>
														</p>
														</td>
												       </tr>
												       
												       <%
												       }
														
												       else if(Constants.REPORT_CONTROL_END_YEAR_OPT.equals(name)){
												
													%>
	<!-- Begin: END_YEAR_OPT  -->												
																<tr>
																	<td>
																		<app:storeMessage key="userportal.esw.standardReports.selectEndYear" />
																	</td>																					
																	<td>
																		<html:select name="esw.ReportingForm" property="<%=controlEl%>">
																			<html:options collection="Report.year.vector" property="label" labelProperty="value"/>
																		</html:select>
																	</td>                                            
																</tr>
	<!-- End: END_YEAR_OPT  -->											
													<%
												
														mandatoryFl=false;
														rendered=true;
													   } else if(Constants.REPORT_CONTROL_END_MONTH_OPT.equals(name)){
												
													%>
	<!-- Begin: END_MONTH_OPT  -->															
																<tr>
																	<td>
																		<app:storeMessage key="userportal.esw.standardReports.selectEndMonth" />
																	</td>																					
																	<td>
																		<html:select name="esw.ReportingForm" property="<%=controlEl%>" >
																			<html:options collection="Report.month.vector" property="label" labelProperty="value"/>
																		</html:select>
																	</td>                                            
																</tr>
	<!-- End: END_MONTH_OPT  -->											
													<%
												
														mandatoryFl=false;
														rendered=true;
												       } else if (Constants.REPORT_CONTROL_BUDGET_YEAR.equals(name)) {
												
												    //START RENDERING "BUDGET_YEAR"
												    String yrs = "";
												    rendered = true;
												
												    String budgetYear = ClwI18nUtil.getMessageOrNull(request, "report.text.BudgetYear");
												    if ( null == budgetYear ){
												    	budgetYear = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.year");;
												    }
												    %>
	<!-- Begin: BUDGET_YEAR  -->											    
												    <tr>
												    	<td>
														    <%=budgetYear%>
														    <%
														    if(mandatoryFl) {
														    %>
																<span class="required">*</span>
																<% //isRequired=true; %>
														    <%
														    }
														    %>
												    	</td>
												    	<td>

												    		
															<logic:iterate id="budgetPeriod" name="Report.budget.periods" type="com.cleanwise.service.api.dao.UniversalDAO.dbrow">

															    <%
															    if ( yrs.indexOf(budgetPeriod.getStringValue(0)) == -1 ) {
																	      yrs += budgetPeriod.getStringValue(0) + " : ";
																	    %>
																	    <%--name="esw.ReportingForm"--%>
																	<p class="inputRow">
																	<label>
																	<html:radio property="<%=controlEl%>"  styleClass= "chkBox" value="<%=budgetPeriod.getStringValue(0)%>">
																	    <%=budgetPeriod.getStringValue(0)%>
																	</html:radio>
																	</label>
																	</p>
															    <%
															    }
															    %>
															
															</logic:iterate>
															
														</td>
													</tr>
	<!-- End: BUDGET_YEAR  -->											    
												    <%--END RENDERING "BUDGET_YEAR"--%>
												    <%
												    } else if (Constants.REPORT_CONTROL_BUDGET_PERIOD_INFO.equals(name)) {
												
												    //--START RENDERING "BUDGET_PERIOD_INFO"--
												    rendered = true;
												
												    String xlateyr = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.budgetPeriod");
												    if(!Utility.isSet(xlateyr)){
												    	xlateyr = Constants.REPORT_STANDARD_FILTER_BUDGET_PERIOD_LABEL;
												    }
												    %>
	<!-- Begin: BUDGET_PERIOD  -->												
													<tr>
														<td>
															 <%=xlateyr%>
														    <% if (mandatoryFl) { %>
														        <span class="required">*</span>
														        <% //isRequired = true; %>
														    <% } %>
														</td>
												   		<td>
													    
													        <logic:iterate  id="budgetPeriod"
													                        name="Report.budget.periods"
													                        type="com.cleanwise.service.api.dao.UniversalDAO.dbrow">
															<p class="inputRow">
													        <label>
													            <% if (budgetPeriod != null && budgetPeriod.getColumnCount() > 3) { %>
													            
													                <%
	
													                String val =    "year=" + budgetPeriod.getStringValue(0) +
													                                "&budget_period=" + budgetPeriod.getStringValue(1) +
													                                "&start_date=" + budgetPeriod.getStringValue(2) +
													                                "&end_date=" + budgetPeriod.getStringValue(3);
													               
													                %>
													                <html:radio styleClass= "chkBox" property="<%=controlEl%>" value="<%=val %>"/>
													            
													           
													                &nbsp;<%=budgetPeriod.getStringValue(0)%>&nbsp;<%=budgetPeriod.getStringValue(2)%>&nbsp;-&nbsp;<%=budgetPeriod.getStringValue(3)%>
													            
													            <% } %>
													        </label>
															</p>
													        </logic:iterate>
													    
													    </td>
												    </tr>
	<!-- End: BUDGET_PERIOD  -->											    
												    <%--END RENDERING "BUDGET_PERIOD_INFO"--%>
												    <%--START RENDERING "SINGLE_DAYS_BACK_7"--%>
													<%
												    } else if (Constants.REPORT_CONTROL_BUDGET_PERIODS_INFO.equals(name)) {
												
												    //--START RENDERING "BUDGET_PERIOD_INFO"--
												    rendered = true;
												
												    String xlateyr = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.budgetPeriods");
												    if ( null == xlateyr ){
												     	xlateyr = Constants.REPORT_STANDARD_FILTER_BUDGET_PERIODS_LABEL;
												    }
												    %>
	<!-- Begin: BUDGET_PERIODS  -->											    
												    <tr>
													    <td>
														    <%=xlateyr%>
														
														    <% if (mandatoryFl) { %>
														         <span class="required">*</span>
														        <% //isRequired = true; %>
														    <% } %>
														</td>
													<td>
													<td>
													    
													        <logic:iterate  id="budgetPeriod" indexId="i"
													                        name="Report.budget.periods"
													                        type="com.cleanwise.service.api.dao.UniversalDAO.dbrow">
															<p class="inputRow">
													        <label>
													            <% if (budgetPeriod != null && budgetPeriod.getColumnCount() > 3) { %>
													           
													                <%
													                String val =    "year=" + budgetPeriod.getStringValue(0) +
													                                "&budget_period=" + budgetPeriod.getStringValue(1) +
													                                "&start_date=" + budgetPeriod.getStringValue(2) +
													                                "&end_date=" + budgetPeriod.getStringValue(3);
													                String period = "period["+i+"]";
													                %>
													                <html:multibox property="customerReportingForm.selectedPeriods" value="<%=val %>"/>
													            
													                &nbsp;<%=budgetPeriod.getStringValue(0)%>&nbsp;<%=budgetPeriod.getStringValue(2)%>&nbsp;-&nbsp;<%=budgetPeriod.getStringValue(3)%>
													           
													            <% } %>
													        </label>
															 </p>
													        </logic:iterate>
													   
												    </td>
												    </tr>
	<!-- End: BUDGET_PERIODS  -->											    
												    <%
												
												    } else if (name.startsWith(Constants.REPORT_CONTROL_SINGLE_DAYS_BACK)  ) {
														rendered=true;
												
												    %>
	<!-- Begin: SINGLE_DAYS_BACK_7  -->												
													<tr>
													<td>
														<%
														 	String selectADate = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.selectDate");
														    if ( null == selectADate ){
														    	selectADate = Constants.REPORT_STANDARD_FILTER_SELECT_A_DATE;
														    }
													    %>
													    <%=selectADate %>
													</td>
														<td>
													   
													    <%
													    	String datePattern = ClwI18nUtil.getDatePattern(request);
															SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
															SimpleDateFormat sdfTo   = new SimpleDateFormat(datePattern);
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
															
															<%if(thisDate != null){%>
															   
															    <a href="#" onclick="javascript:setFieldsAndSubmitForm('reportingStandardId','selectDateId','<%=thisDateFormatted %>')">
			                                                    		<%=thisDate%>
			                                                    </a>
															    
															<% } %>
															
														  <%}%>
															<input type="hidden" id="selectDateId" name="<%=controlEl%>" value=""/>
													   		
													   
														</td>
													</tr>
	<!-- End: SINGLE_DAYS_BACK_7  -->											
												    <%
												    }
			//TODO - Report Control ("LOCATE_ACCOUNT_MULTI_OPT") would be implemented in Phase-II.
			
												    else if (Constants.REPORT_CONTROL_LOCATE_ACCOUNT_MULTI_OPT.equalsIgnoreCase(name) ) {
			// Refer /baselinestorepages/t_custAcctMgtReportLocDateSelect.jsp for implementation.
												   %>
												
	<!-- Begin: LOCATE_ACCOUNT_MULTI_OPT  -->
	<!-- End: LOCATE_ACCOUNT_MULTI_OPT  -->											
												   <%
												    }else if ("LOCATE_SITE_MULTI_OPT".equalsIgnoreCase(name)
												    		||"SITES".equals(name) 
												    		|| "SITES_OPT".equals(name) 
												    		|| "SITE_MULTI_OPT".equals(name) 
												    		|| "SITE_MULTI".equals(name) ) {
												   
												    
		  //TODO - Report Control ("LOCATE_ACCOUNT_MULTI_OPT")  would be implemented in Phase-II.
		  
		  // Refer /baselinestorepages/t_custAcctMgtReportLocDateSelect.jsp for implementation.
												    %>
	<!-- Begin: LOCATE_SITE_MULTI_OPT  -->
	<!-- End: LOCATE_SITE_MULTI_OPT  -->											
												    <%
												    }else if (Constants.REPORT_CONTROL_MANUFACTURER.equals(name) || Constants.REPORT_CONTROL_MANUFACTURER_OPT.equals(name)) {
													  rendered = true;
												    %>
												    <%
												        String mfgselect = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.manufacturer");
												        if (null == mfgselect) {
												            mfgselect = Constants.REPORT_STANDARD_FILTER_MANUFACTURER_LABEL;
												        }
												    %>
	<!-- Begin: MANUFACTURER  -->											   
												    <tr>
												    	<td><%=mfgselect%>
												    		<%
														        if (Constants.REPORT_CONTROL_MANUFACTURER.equals(name) && mandatoryFl) {
														    %>  <span class="required">*</span>
															<%
														        }
														    %>
												    	</td>
												    	<td>
														    <html:select property="<%=controlEl%>">
														        <html:option value="">
														            <app:storeMessage  key="admin.select"/>
														        </html:option>
														        <html:options collection="Report.all.manufacturer.for.store" property="busEntityId" labelProperty="shortDesc"/>
														    </html:select>
														</td>
													</tr>
	<!-- End: MANUFACTURER  -->											
												    <%
												    }else if (Constants.REPORT_CONTROL_DISTRIBUTOR.equals(name) 
												    		|| Constants.REPORT_CONTROL_DISTRIBUTOR_OPT.equals(name)) {
														  //START RENDERING "DISTRIBUTOR"
														  rendered = true;
												   
												        String distributor = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.distributor");
												        if (null == distributor) {
												        	distributor = Constants.REPORT_STANDARD_FILTER_DISTRIBUTOR_LABEL;
												        }
												    %>
	<!-- Begin: DISTRIBUTOR  -->												
													<tr>
														<td>
												    		<%=distributor %>
												    		<%
														        if (Constants.REPORT_CONTROL_DISTRIBUTOR.equals(name) && mandatoryFl) {
														    %>  <span class="required">*</span>
															<%
														        }
														    %>
												    	</td>
												    	<td>
														    <html:select property="<%=controlEl%>">
														        <html:option value="">
														            <app:storeMessage  key="admin.select"/>
														        </html:option>
														        <html:options collection="Report.distributor.vector" property="busEntityId" labelProperty="shortDesc"/>
														    </html:select>
														</td>
													</tr>
	<!-- End: DISTRIBUTOR  -->												
												    <%
												    }
												    %>
												   
												    <%
												    if (Constants.REPORT_CONTROL_INVOICE_TYPE_2.equals(name) 
												    		|| Constants.REPORT_CONTROL_INVOICE_TYPE_2_OPT.equals(name)) {
												  		rendered = true;
												
												    %>
		<!--Begin: INVOICE_TYPE_2 -->												   
												   <tr>
												   		<td>
												   			 <%
														        String invoiceType = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.invoiceType");
														        if (null == invoiceType) {
														        	invoiceType = Constants.REPORT_STANDARD_FILTER_INVOICE_TYPE_LABEL;
														        }
														    %>
														    <%=invoiceType %>
														     <%
														        if (Constants.REPORT_CONTROL_INVOICE_TYPE_2_OPT.equals(name) && mandatoryFl) {
														     %>  <span class="required">*</span>
																<%//isRequired=true;
														        }
														    %>
												   		</td>
												   		<td>
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
													    </td>
												    </tr>
	<!--End: INVOICE_TYPE_2-->												   
												    <%
												    }
												    %>
												   
												
												    <%--START RENDERING "INVOICE_STATUS_SELECT"--%>
												    <%
												    if (Constants.REPORT_CONTROL_INVOICE_STATUS_SELECT.equals(name) 
												    		|| Constants.REPORT_CONTROL_INVOICE_STATUS_SELECT_OPT.equals(name)) {
												    	rendered = true;
												    %>
												        <!-- Invoice_Status_Select -->
												        
												        <%
														        String invoiceType = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.invoiceStatus");
														        if (null == invoiceType) {
														        	invoiceType = Constants.REPORT_STANDARD_FILTER_INVOICE_STATUS_LABEL;
														        }
														%>
	<!--Begin: INVOICE_STATUS_SELECT-->															
															<tr>
																<td>
																<%=invoiceType %>
																<%
															        if (Constants.REPORT_CONTROL_INVOICE_STATUS_SELECT.equals(name) && mandatoryFl) {
															    %>
															           <span class="required">*</span>
																       <%//isRequired=true;%>
															     <%
															        }
															     %>
																</td>
																<td>
													            <!-- Invoice Status Code drop-down list-->
														            <html:select property="invoiceStatus" value="">
														                          <%
														                                  for(int j = 0; j < invoiceStatusCodeDV.size(); j++) {
														                                      String invoiceStatusCode = (String) invoiceStatusCodeDV.get(j);
														                          %>
														                                      <html:option value="<%=invoiceStatusCode%>"/>
														                               <% } %>
														            </html:select>
																</td>
															</tr>
	<!--End: INVOICE_STATUS_SELECT-->													
												 <% } // end if ("INVOICE_STATUS_SELECT"...%>
												 
												    <%--START RENDERING "APPROVED_FLAG"--%>
												    <%												    
												    if ("APPROVED_FLAG".equals(name) 
												    		|| "APPROVED_FLAG_OPT".equals(name)) {
												    	rendered = true;
												    	String defVal = grc.getDefault();
												    %>														
															<tr>
																<td>
																<app:storeMessage  key="report.text.invoice.status.approvedOnly"/>:
																<%
															        if ("APPROVED_FLAG".equals(name) && mandatoryFl) {
															    %>
															           <span class="required">*</span>
																       <%//isRequired=true;%>
															     <%
															        }
															     %>
																</td>
																<td>
																	<html:select property="<%=controlEl%>" value="<%=defVal%>">
																        <html:option value="No">
																            <app:storeMessage  key="global.text.no"/>
																        </html:option>
																        <html:option value="Yes">
																            <app:storeMessage  key="global.text.yes"/>
																        </html:option>
																    </html:select>
																</td>
															</tr>											
												 <% } // end if ("APPROVED_FLAG"...%>
												 
												 <%--START RENDERING "APPROVED_FLAG"--%>
												    <%												    
												    if ("SUPPRESS_NOTES".equals(name) 
												    		|| "SUPPRESS_NOTES_OPT".equals(name)) {
												    	rendered = true;
												    	String defVal = grc.getDefault();
												    %>														
															<tr>
																<td>
																<app:storeMessage  key="report.text.invoice.status.suppressNotes"/>:
																<%
															        if ("SUPPRESS_NOTES".equals(name) && mandatoryFl) {
															    %>
															           <span class="required">*</span>
																       <%//isRequired=true;%>
															     <%
															        }
															     %>
																</td>
																<td>
																	<html:select property="<%=controlEl%>" value="<%=defVal%>">
																        <html:option value="No">
																            <app:storeMessage  key="global.text.no"/>
																        </html:option>
																        <html:option value="Yes">
																            <app:storeMessage  key="global.text.yes"/>
																        </html:option>
																    </html:select>
																</td>
															</tr>											
												 <% } // end if ("SUPPRESS_NOTES"...%>
												 
												 <%
												if(label!=null && label.length()==0){
												 label=null;
												}
												
												if(!rendered){
												String dateTypeValue = defaultDateFormat;
												if(dateSearchEnd || dateSearchReceivedFrom || dateSearchReceivedTo || dateSearchStart
														|| dateSearchActualBegin || dateSearchActualEnd || dateSearchEstimateBegin 
														|| dateSearchEstimateEnd ) {
														try{
															if(Utility.isSet(reportingForm.getCustomerReportingForm().getReportControlValue(ii))){
																dateTypeValue = reportingForm.getCustomerReportingForm().getReportControlValue(ii);
															}
														}finally{}
												}
												String dateTypeClass = defaultDateFormat.equals(dateTypeValue) ? "default-value " : "";
												dateTypeClass += "standardCal";
												if ( dateSearchEnd ) {
												
												    %>
	<!--Begin: End Date-->											
												    <tr>
			                                            <td>
			                                                  <%
														        String endDate = ClwI18nUtil.getMessageOrNull(request, "report.text.EndDate");
														        if (null == endDate) {
														        	endDate = Constants.REPORT_STANDARD_FILTER_END_DATE_LABEL;
														        }
															  %>
															  <%=endDate %>
															   <%
														        if (mandatoryFl) {
														     %>  <span class="required">*</span>
																<%//isRequired=true;
														        }
														    %>
			                                            </td>
			                                            <td>
			                                                <html:text styleClass="<%=dateTypeClass%>" value="<%=dateTypeValue %>"    property="<%=controlEl%>" />
			                                            </td>
			                                        </tr>
	<!--End: End Date-->											   
												<%} else if (dateSearchReceivedFrom) {%>
	<!--Begin: Received Date From -->											 	
												 	<tr>
			                                            <td>
												     		<%
														        String receivedDateFrom = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.receivedDateFrom");
														        if (null == receivedDateFrom) {
														        	receivedDateFrom = Constants.REPORT_STANDARD_FILTER_RECEIVED_DATE_FROM_LABEL;
														        }
															%>
															 <%=receivedDateFrom %>
														</td>
			                                            <td>
			                                                <html:text   styleClass="<%=dateTypeClass%>" value="<%=dateTypeValue %>"    property="<%=controlEl%>" />
			                                            </td>
			                                        </tr>
	<!--End: Received Date From -->											    
												<%} else if (dateSearchReceivedTo) {%>
	<!--Begin: Received Date To -->											
												<tr>
			                                            <td>
												     		<%
														        String receivedDateTo = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.receivedDateTo");
														        if (null == receivedDateTo) {
														        	receivedDateTo = Constants.REPORT_STANDARD_FILTER_RECEIVED_DATE_TO_LABEL;
														        }
															%>
															 <%=receivedDateTo %>
														</td>
			                                            <td>
			                                                <html:text   styleClass="<%=dateTypeClass%>" value="<%=dateTypeValue %>"    property="<%=controlEl%>" />
			                                            </td>
			                                      </tr>
	<!--End: Received Date To -->											
												    <%
												    } else if (dateSearchStart) {
												    %>
	<!--Begin: Begin Date -->												
													<tr>
			                                            <td>
												     		<%
														        String beginDate = ClwI18nUtil.getMessageOrNull(request, "report.text.BeginDate");
														        if (null == beginDate) {
														        	beginDate = Constants.REPORT_STANDARD_FILTER_BEGIN_DATE_LABEL;
														        }
															%>
															 <%=beginDate %>
															 <%
														        if (mandatoryFl) {
														     %>  <span class="required">*</span>
																<%//isRequired=true;
														        }
														    %>
														</td>
			                                            <td>
			                                                <html:text   styleClass="<%=dateTypeClass%>" value="<%=dateTypeValue %>"    property="<%=controlEl%>" />
			                                            </td>
			                                      </tr>
	<!--End: Begin Date -->											
												 <%   
												 } else if (dateSearchActualBegin) {
												 %>
	<!--Begin: Actual Start Date -->											        
												      <tr>
			                                            <td>
												     		<%
														        String actualStartDate = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.actualStartDate");
														        if (null == actualStartDate) {
														        	actualStartDate = Constants.REPORT_STANDARD_FILTER_ACTUAL_START_DATE_LABEL;
														        }
															%>
															 <%=actualStartDate %>
														</td>
			                                            <td>
			                                                <html:text   styleClass="<%=dateTypeClass%>" value="<%=dateTypeValue %>"    property="<%=controlEl%>" />
			                                            </td>
			                                      </tr>
	<!--End: Actual Start Date -->		                                      
												  <%  } else if (dateSearchActualEnd) {
												    %>
	<!--Begin: Actual End Date -->											    
												    <tr>
			                                            <td>
												     		<%
														        String actualEndDate = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.actualEndDate");
														        if (null == actualEndDate) {
														        	actualEndDate = Constants.REPORT_STANDARD_FILTER_ACTUAL_END_DATE_LABEL;
														        }
															%>
															 <%=actualEndDate %>
														</td>
			                                            <td>
			                                                <html:text   styleClass="<%=dateTypeClass%>" value="<%=dateTypeValue %>"    property="<%=controlEl%>" />
			                                            </td>
			                                      </tr>
	<!--End: Actual End Date -->												    
												 <%  } else if (dateSearchEstimateBegin) {
												    %>
	<!--Begin: Estimated Start Date -->												    
												    <tr>
			                                            <td>
												     		<%
														        String estimatedStartDate = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.estimatedStartDate");
														        if (null == estimatedStartDate) {
														        	estimatedStartDate = Constants.REPORT_STANDARD_FILTER_ESTIMATED_START_DATE_LABEL;
														        }
															%>
															 <%=estimatedStartDate %>
														</td>
			                                            <td>
			                                                <html:text   styleClass="<%=dateTypeClass%>" value="<%=dateTypeValue %>"    property="<%=controlEl%>" />
			                                            </td>
			                                      </tr>
	<!--End: Estimated Start Date -->											        
												       
												 <%  } else if (dateSearchEstimateEnd) {
												    %>
	<!--Begin: Estimated End Date -->											       
												        <tr>
				                                            <td>
													     		<%
															        String estimatedEndDate = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.standardReports.estimatedEndDate");
															        if (null == estimatedEndDate) {
															        	estimatedEndDate = Constants.REPORT_STANDARD_FILTER_ESTIMATED_END_DATE_LABEL;
															        }
																%>
																 <%=estimatedEndDate %>
															</td>
				                                            <td>
				                                                <html:text   styleClass="<%=dateTypeClass%>" value="<%=dateTypeValue %>"    property="<%=controlEl%>" />
				                                            </td>
				                                      </tr>
	<!--End: Estimated End Date -->
												   <% } else if ("LOCATE_SITE_MULTI".equals(name) ||
												           "LOCATE_SITE_MULTI_OPT".equals(name) ||
												           "SITE_MULTI".equals(name) || 
												           "SITE_MULTI_OPT".equals(name)) {
													   
											SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
											String idString = new String();
											int siteId[] = sessionDataUtil.getGenericReportingDto().getSiteId();
											
											for (int i = 0; siteId != null && i < siteId.length; i++) {
											    if(Utility.isSet(idString)){
												    idString  = idString + "," + Integer.toString(siteId[i]);
											    }else{
												    idString = Integer.toString(siteId[i]);
											    }
											    
											}
											
											String locationSelected = sessionDataUtil.getGenericReportingDto().getLocationSelected();
											%>
                                                        <app:specifyLocations hiddenName="customerReportingForm.genericReportingInfo.siteId" locationIds="<%=idString%>" locationSelected="<%=locationSelected%>"
							    selectName="customerReportingForm.genericReportingInfo.locationSelected"
							    pageForSpecifyLocation="<%=Constants.SPECIFY_LOCATIONS_STANDARD_REPORTS %>"/>
												   <% } else if ("LOCATE_ACCOUNT_MULTI".equals(name) ||
												           "LOCATE_ACCOUNT_MULTI_OPT".equals(name)) {%>
                                                        <app:specifyAccounts hiddenName="customerReportingForm.genericReportingInfo.accountId" />
												   <% } else {
												        String controlType = grc.getControlTypeCd();
												        if (controlType == null
												                || controlType.trim().length() == 0
												                || controlType.equals(RefCodeNames.CONTROL_TYPE_CD.TEXT)) {
												    %>
	<!--Begin: Generic Options  -->
												    <!-- Generic Opt -->
												    
												    	<tr>
				                                            <td>
																 <%=(label == null) ? name : label%>
																  <%
															         if (mandatoryFl) {
															      %>
																 	<span class="required">*</span>
																	<%//isRequired=true;%>
															      <%
															            }
															      %>
															</td>
				                                            <td>
				                                                <html:text name="esw.ReportingForm" property="<%=controlEl%>"/>
				                                            </td>
				                                      </tr>
												    
	<!-- Begin: DROP-DOWN control -->
												    <%
												    } else if (controlType.equals(RefCodeNames.CONTROL_TYPE_CD.DROP_DOWN)) {
												        String defVal = grc.getDefault();
												        PairViewVector values = grc.getChoiceValues();
												    %>
												    
												    <tr>
				                                            <td>
																 <%=((label == null) ? "&nbsp;" : label)%>
																  <%
															         if (mandatoryFl) {
															      %>
																 	<span class="required">*</span>
																	<%//isRequired=true;%>
															      <%
															            }
															      %>
															</td>
				                                            <td>
												    
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
												            }
												        %>
												        </td>
				                                      </tr>
		 <!-- End DROP-DOWN control -->
												   <% } else if (RefCodeNames.CONTROL_TYPE_CD.RADIO.equals(grc.getControlTypeCd())) { %>
												    <%
												        String defVal = grc.getDefault();
												        PairViewVector values = grc.getChoiceValues();
												    %>
												    
												    <tr>
				                                            <td>
																 <%=(label == null) ? name : label%>
																  
															</td>
				                                            <td>
				                                            
				                                      		
														            <%
														                for (Iterator iter = values.iterator(); iter.hasNext();) {
														                    PairView valPair = (PairView) iter.next();
														                    String val = (String) valPair.getObject1();
														                    String elLabel = (String) valPair.getObject2();
														            %>
																		<p class="inputRow">
															            <label>
															                <html:radio styleClass= "chkBox" styleId="<%=controlEl%>" property="<%=controlEl%>" value="<%=val%>"/>
															                <%=elLabel%>
															            </label>
																		</p>
														            <% } %>
												            	
															</td>
				                                      </tr>
												    <%}%>
												    <%
												                }
												            }
												        } //End of generic report controls
													}
											    %>
											
	<!--End: Generic Options  -->											
											
	<!-- ============================================================================================================================= -->									
	<!-- End: Generic Report Controls -->
										<%} %>
										
                                    </tbody></table>			
                                </div>
							</div>	
						
	</html:form>
						</div>
					</div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
                <!-- End Box -->    				
				
            </div>

</div>
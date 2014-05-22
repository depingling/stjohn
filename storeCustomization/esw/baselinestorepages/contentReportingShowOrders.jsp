<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="com.cleanwise.service.api.dto.OrdersAtAGlanceDto"%>
<%@page import="com.cleanwise.service.api.dto.ReportingDto"%>
<%@page import="com.cleanwise.service.api.value.SiteData"%>
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
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<bean:define id="myForm" name="esw.ReportingForm"  type="com.espendwise.view.forms.esw.ReportingForm"/>

<script type="text/javascript" src="../../externals/dojo_1.1.0/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false">
</script>
<%
    CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String reportingURL =  "userportal/esw/reporting.do";
    StringBuilder showReportURL = new StringBuilder(50);
    showReportURL.append(reportingURL);
    
    StringBuilder filterCategoriesURL = new StringBuilder(50);
    filterCategoriesURL.append(reportingURL);
    filterCategoriesURL.append(Constants.PARAMETER_OPERATION);
    filterCategoriesURL.append("=");
    filterCategoriesURL.append(Constants.PARAMETER_OPERATION_VALUE_FILTER_ORDERS_REPORT_CATEGORIES);
		
    //String formBeanName = request.getParameter(Constants.PARAMETER_FORM_BEAN_NAME);
	
	boolean hideMfg = false;
	if(user.getUserAccount().isHideItemMfg()){
		hideMfg = true;
	}
	SiteData location = user.getSite();
	
	ReportingDto rDto = myForm.getOrdersGlanceReportingInfo();
	String breadcrumb = rDto.getBreadcrumb();
	if(!Utility.isSet(breadcrumb)){
	    breadcrumb = ClwMessageResourcesImpl.getMessage(request,"reporting.label.allProducts");
	}
	
	//STJ-4637
	String fromDate = rDto.getFrom();
	String toDate = rDto.getTo();
	String datePattern = ClwI18nUtil.getDatePattern(request);
   	if (Utility.isSet(fromDate) && fromDate.equals(datePattern)) {
   		rDto.setFrom(fromDate.toLowerCase());
   	}
   	if (Utility.isSet(toDate) && toDate.equals(datePattern)) {
   		rDto.setTo(toDate.toLowerCase());
   	}
	
	String allCategories = ClwMessageResourcesImpl.getMessage(request, "reporting.filter.allCategories");
    
    
%>

<script type="text/javascript">
	$(document).ready(function(){	
		initializeCategoryLevels();		
	});
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

function generateReport(){
    dojo.byId("reportingFilterFormOperation").value = '<%=Constants.PARAMETER_OPERATION_VALUE_GENERATE_EXCEL_REPORT%>';
    dojo.byId("reportingFilterFormReportDBName").value = '<%=RefCodeNames.CUSTOMER_REPORT_TYPE_CD.ORDERS_AT_A_GLANCE%>';
    submitForm('reportingFilterForm');
}

function filterReport(){
    dojo.byId("reportingFilterFormOperation").value = '<%=Constants.PARAMETER_OPERATION_VALUE_FILTER_ORDERS_REPORT%>';
    dojo.byId("reportingFilterFormCategorySelected").value = "";
    submitForm('reportingFilterForm');
}

function setLevelAndSubmitForm(categoryName){
    
	//Send the category value to server with form submit and set the filter values while displaying pie chart.
	dojo.byId("reportingFilterFormOperation").value = '<%=Constants.PARAMETER_OPERATION_VALUE_FILTER_ORDERS_REPORT%>';
	dojo.byId("reportingFilterFormCategorySelected").value = categoryName;
	submitForm('reportingFilterForm');

 }

 function filterCategories(level) {
 
	dojo.byId("reportingFilterFormOperation").value  = 'filterOrdersReportCategories';

	//alert($("#reportingFilterForm").serialize());
	$.post("<%=request.getContextPath()%>/<%=showReportURL%>",
	        $("#reportingFilterForm").serialize(),
	       //{ operation: "filterOrdersReportCategories"},
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

</script>

<app:setLocaleAndImages/>

<div id="contentWrapper" class="singleColumn clearfix">
    <div id="content">
	<%
	    String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
	%>
	<jsp:include page="<%=errorsAndMessagesPage %>"/>
	<!-- Start Box -->
        <div class="boxWrapper">
            <%
		String tabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "reportingTabs.jsp");
	    %>
	    <jsp:include page="<%=tabPage%>"/>
	    
	    <div class="content">
                <div class="left budgets clearfix">
		    
		    <%
                        //if no site is selected then do not render anything (there will be an
                        //error displayed above telling the user they must select a site)
                        //if (user.getSite() != null) {//STJ-5135 Even if user do not have a current location selected,
    												   //Orders at-a-glance / Budget at-a-glance - Should be available
	
		    %>
		    
		    <html:form styleId="reportingFilterForm" action="<%=showReportURL.toString() %>">
		    <html:hidden styleId="reportingFilterFormOperation" property="<%=Constants.PARAMETER_OPERATION%>"/>
		    <html:hidden styleId="reportingFilterFormCategorySelected" property="ordersGlanceReportingInfo.chartCatSelected" />
		    <html:hidden styleId="reportingFilterFormReportDBName" property="customerReportingForm.reportDbName" />
	
		    <div class="rightColumn">
                        <div class="rightColumnIndent">
                            <h2><app:storeMessage key="reporting.label.ordersAtAGlance" /></h2>
			    <% Object errors =request.getAttribute("org.apache.struts.action.ERROR");
				if(errors==null){
			    %>
                            <a onclick="javascript:generateReport()" class="blueBtnLarge right"><span><app:storeMessage key="global.action.label.exportExcelSheet" /></span></a>
			    <% } %>
			    <hr />
			    
			    <h3 class="left"><%=breadcrumb%></h3>
			    
			    <div class="graphArea leftMargin topMargin" id="container">

				<% 
				OrdersAtAGlanceDto dto = myForm.getOrdersGlanceReportingInfo().getOrdersReportChart();
				if(dto != null){
				%>
				
				<app:ordersAtAGlance pieChartDto="<%= dto%>" pieChartContainer="container" />
				<% } %>
			    </div>
			    
                        </div>
                    </div>
		   
		    <div class="leftColumn">
			
			
			<h3><app:storeMessage key="reporting.label.filters" /></h3>
			<hr />
<%--						
			<p><%=ClwMessageResourcesImpl.getMessage(request,"reporting.label.location")%><br />
				<html:select name="esw.ReportingForm" property="ordersGlanceReportingInfo.locationSelected" styleId="locationSelected">
				<html:optionsCollection name="esw.ReportingForm" property="locationFilterChoices" label="label" value="value"/>
				</html:select>
			</p> --%>
			<%
							    SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
							    String idString = new String();
							    int siteId[] = sessionDataUtil.getOrdersGlanceReportingDto().getSiteId();
							    
							    for (int i = 0; siteId != null && i < siteId.length; i++) {
								if(Utility.isSet(idString)){
									idString  = idString + "," + Integer.toString(siteId[i]);
								}else{
									idString = Integer.toString(siteId[i]);
								}
								
							    }
							    
							    String locationSelected = sessionDataUtil.getOrdersGlanceReportingDto().getLocationSelected();
			%>
            
	    <app:specifyLocations hiddenName="ordersGlanceReportingInfo.siteId" locationIds="<%=idString%>" locationSelected="<%=locationSelected%>"
							    selectName="ordersGlanceReportingInfo.locationSelected" useSelect="true" layout="V" 
							    pageForSpecifyLocation="<%=Constants.SPECIFY_LOCATIONS_ORDERS_AT_A_GLANCE %>"/>
			<hr />
				
			<p><app:storeMessage  key="orders.filterPane.label.dateRange" /><br>
			     <html:select name="esw.ReportingForm" property="ordersGlanceReportingInfo.dateRange" styleClass="dateRange" >
			      <html:optionsCollection name="esw.ReportingForm" property="dateRangeFieldChoices" label="label" value="value"/>
			      </html:select>
			</p>
			<%
									String defaultDateFormat = ClwI18nUtil.getDatePattern(request);
									if(Utility.isSet(defaultDateFormat)) {
										defaultDateFormat = defaultDateFormat.toLowerCase();
									}
									String toDateValue = defaultDateFormat;
									String fromDateValue = defaultDateFormat;
									if(Utility.isSet(myForm.getOrdersGlanceReportingInfo().getTo())) {
										toDateValue = myForm.getOrdersGlanceReportingInfo().getTo();
									}
									if(Utility.isSet(myForm.getOrdersGlanceReportingInfo().getFrom())) {
										fromDateValue = myForm.getOrdersGlanceReportingInfo().getFrom();
									}
			%>
			
			<table class="calendar hide">
			   <tbody>
				<tr>
				    <td><app:storeMessage  key="orders.filterPane.label.from" /></td>
				    <td>
				     <html:text name="esw.ReportingForm" styleClass="default-value standardCal" value="<%=fromDateValue %>" property="ordersGlanceReportingInfo.from"/>
				     </td>
				</tr>
				<tr>
				    <td><app:storeMessage  key="orders.filterPane.label.to" /></td>
				    <td>
				    <html:text name="esw.ReportingForm" styleClass="default-value standardCal" value="<%=toDateValue %>" property="ordersGlanceReportingInfo.to"/>
				    </td>
				</tr>
			    </tbody>
			</table>
			
			<hr />
			
			<p><app:storeMessage  key="reporting.label.manufacturer" /><br>
			     <html:select name="esw.ReportingForm" property="ordersGlanceReportingInfo.mfgSelected" >
			     <html:optionsCollection name="esw.ReportingForm" property="ordersGlanceReportingInfo.mfgList" label="label" value="value"/>
			     </html:select>
			</p>
			<hr />
				
			<p><app:storeMessage  key="reporting.label.productCategory" /><br>
			   
			     <p class="level1"><app:storeMessage  key="reporting.label.level1" /><br />
                                <html:select styleId="cat1" name="esw.ReportingForm" property="ordersGlanceReportingInfo.cat1Selected" onchange="filterCategories('level1')">
				<html:optionsCollection name="esw.ReportingForm" property="ordersGlanceReportingInfo.category1" label="label" value="value"/>
				</html:select>
			     </p>

                            <p class="level2 hide"><app:storeMessage  key="reporting.label.level2" /><br />
                                <html:select styleId="cat2" name="esw.ReportingForm" property="ordersGlanceReportingInfo.cat2Selected" onchange="filterCategories('level2')">
				<html:optionsCollection name="esw.ReportingForm" property="ordersGlanceReportingInfo.category2" label="label" value="value"/>
				</html:select>
			    </p>

                            <p class="level3 hide"><app:storeMessage  key="reporting.label.level3" /><br />
                                <html:select styleId="cat3" name="esw.ReportingForm" property="ordersGlanceReportingInfo.cat3Selected" onchange="filterCategories('level3')">
				<html:optionsCollection name="esw.ReportingForm" property="ordersGlanceReportingInfo.category3" label="label" value="value"/>
				</html:select>
			    </p>

                            <p class="level4 hide"><app:storeMessage  key="reporting.label.level4" /><br />
                                <html:select styleId="cat4" name="esw.ReportingForm" property="ordersGlanceReportingInfo.cat4Selected" >
				<html:optionsCollection name="esw.ReportingForm" property="ordersGlanceReportingInfo.category4" label="label" value="value"/>
				</html:select>			   
			    </p>
			    <hr />
				
			</p>
			
			<a onclick="javascript:filterReport()" class="blueBtnLarge"><span><app:storeMessage key="global.action.label.filter" /></span></a>
						
			
		    </div>
		</html:form>
		    <% //} %>
		</div>
	    </div>
	    <div class="bottom clearfix">
                        <span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span>
            </div>
	    
	</div>

    </div>
</div>


        
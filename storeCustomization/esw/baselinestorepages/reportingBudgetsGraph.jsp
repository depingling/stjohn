<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.service.api.dto.ReportingDto"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.service.api.dto.LocationBudgetChartDto"%>
<%@page import="com.espendwise.view.forms.esw.DashboardForm"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/locationBudget.tld" prefix="charts" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="myForm" name="esw.ReportingForm"  type="com.espendwise.view.forms.esw.ReportingForm"/>

<script type="text/javascript" src="../../externals/dojo_1.1.0/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false">
</script>
<script type="text/javascript">

function saveState(state)
{
	var graphTotalBudget = document.getElementById('graphTotalBudget');
	var graphTotalUnbudgeted = document.getElementById('graphTotalUnbudgeted');
	var graphTotalCombined = document.getElementById('graphTotalCombined');
	var graphBudgetedCostCenter = document.getElementById('graphBudgetedCostCenter');
	var graphUnbudgetedCostCenter = document.getElementById('graphUnbudgetedCostCenter');
	var graphCombinedCostCenter = document.getElementById('graphCombinedCostCenter');
	var showCCLink = document.getElementById('showCCLink');
    var hideCCLink = document.getElementById('hideCCLink');
	//var label = document.getElementById('label');
	dojo.xhrGet( { // 
        url: "<%= request.getContextPath()%>/userportal/esw/dashboard.do?time="+new Date()+"&operation=showChart&state="+state, 
        handleAs: "text",
        timeout: 10000, // Time in milliseconds
        // The LOAD function will be called on a successful response.
        load: function(response, ioArgs) { //
            if(state=='hideCC')
            {
            	if(document.getElementById('combinedCCId').checked) {
            		graphTotalBudget.style.display = 'none';
                    graphBudgetedCostCenter.style.display = 'none';
                    graphTotalUnbudgeted.style.display = 'none';
                    graphTotalCombined.style.display = 'block';
                    graphCombinedCostCenter.style.display = 'none';
                    graphUnbudgetedCostCenter.style.display = 'none';		
                    document.getElementById('totalCombinedId').checked=true;
            	} else if(document.getElementById('budgetedCCId').checked) {
            		graphTotalBudget.style.display = 'block';
                    graphBudgetedCostCenter.style.display = 'none';
                    graphTotalUnbudgeted.style.display = 'none';
                    graphTotalCombined.style.display = 'none';
                    graphCombinedCostCenter.style.display = 'none';
                    graphUnbudgetedCostCenter.style.display = 'none';
                    document.getElementById('totalBudgetedId').checked=true;
            	} else {
            		graphTotalBudget.style.display = 'none';
                    graphBudgetedCostCenter.style.display = 'none';
                    graphTotalUnbudgeted.style.display = 'block';
                    graphTotalCombined.style.display = 'none';
                    graphCombinedCostCenter.style.display = 'none';
                    graphUnbudgetedCostCenter.style.display = 'none';
                    document.getElementById('totalNonbudgetedId').checked=true;
            	}
             
             hideCCLink.style.display = 'block';
			 showCCLink.style.display = 'none';
			// label.style.display='block';
			 
			 document.getElementById('totalButtons').style.display = 'block';
			 document.getElementById('ccButtons').style.display = 'none';
			// document.getElementById('totalBudgetedId').checked=true;
            }
            else if(state=='showCC') {
            	if(document.getElementById('totalCombinedId').checked) {
            		graphTotalBudget.style.display = 'none';
                    graphBudgetedCostCenter.style.display = 'none';
                    graphTotalUnbudgeted.style.display = 'none';
                    graphTotalCombined.style.display = 'none';
                    graphCombinedCostCenter.style.display = 'block';
                    graphUnbudgetedCostCenter.style.display = 'none';		
                    document.getElementById('combinedCCId').checked=true;
            	} else if(document.getElementById('totalBudgetedId').checked) {
            		graphTotalBudget.style.display = 'none';
                    graphBudgetedCostCenter.style.display = 'block';
                    graphTotalUnbudgeted.style.display = 'none';
                    graphTotalCombined.style.display = 'none';
                    graphCombinedCostCenter.style.display = 'none';
                    graphUnbudgetedCostCenter.style.display = 'none';
                    document.getElementById('budgetedCCId').checked=true;
            	} else {
            		graphTotalBudget.style.display = 'none';
                    graphBudgetedCostCenter.style.display = 'none';
                    graphTotalUnbudgeted.style.display = 'none';
                    graphTotalCombined.style.display = 'none';
                    graphCombinedCostCenter.style.display = 'none';
                    graphUnbudgetedCostCenter.style.display = 'block';
                    document.getElementById('nonBudgetedCCId').checked=true;
            	}
             hideCCLink.style.display = 'none';
   			 showCCLink.style.display = 'block';
			 //label.style.display='none';
			 document.getElementById('totalButtons').style.display = 'none';
			 document.getElementById('ccButtons').style.display = 'block';
			 //document.getElementById('budgetedCCId').checked=true;
            }
          return response; // 
        },
        // The ERROR function will be called in an error case.
        error: function(response, ioArgs) { // 
          console.error("HTTP status code: ", ioArgs.xhr.status); //
          dojo.byId("replace").innerHTML = 'Loading the ressource from the server did not work'; //  
          return response; // 
          }
        });
}
function showGraph(containerId) {
    var graphTotalBudget = document.getElementById('graphTotalBudget');
    var graphTotalUnbudgeted = document.getElementById('graphTotalUnbudgeted');
    var graphTotalCombined = document.getElementById('graphTotalCombined');
    var graphBudgetedCostCenter = document.getElementById('graphBudgetedCostCenter');
    var graphUnbudgetedCostCenter = document.getElementById('graphUnbudgetedCostCenter');
    var graphCombinedCostCenter = document.getElementById('graphCombinedCostCenter');
    if(containerId == 'graphBudgetedCostCenter') {
    	graphBudgetedCostCenter.style.display = 'block';
    	graphUnbudgetedCostCenter.style.display = 'none';
    	graphCombinedCostCenter.style.display = 'none';
    	graphTotalBudget.style.display = 'none';
    	graphTotalUnbudgeted.style.display = 'none';
    	graphTotalCombined.style.display = 'none';
    } else if(containerId == 'graphTotalUnbudgeted') {
    	graphTotalBudget.style.display = 'none';
    	graphTotalUnbudgeted.style.display = 'block';
    	graphTotalCombined.style.display = 'none';
    	graphBudgetedCostCenter.style.display = 'none';
    	graphUnbudgetedCostCenter.style.display = 'none';
    	graphCombinedCostCenter.style.display = 'none';
    } else if(containerId == 'graphTotalCombined') {
    	graphTotalBudget.style.display = 'none';
    	graphTotalUnbudgeted.style.display = 'none';
    	graphTotalCombined.style.display = 'block';
    	graphBudgetedCostCenter.style.display = 'none';
    	graphUnbudgetedCostCenter.style.display = 'none';
    	graphCombinedCostCenter.style.display = 'none';
    } else if(containerId == 'graphUnbudgetedCostCenter') {
    	graphBudgetedCostCenter.style.display = 'none';
    	graphUnbudgetedCostCenter.style.display = 'block';
    	graphCombinedCostCenter.style.display = 'none';
    	graphTotalBudget.style.display = 'none';
    	graphTotalUnbudgeted.style.display = 'none';
    	graphTotalCombined.style.display = 'none';
    }  
    else if(containerId == 'graphCombinedCostCenter') {
    	graphBudgetedCostCenter.style.display = 'none';
    	graphUnbudgetedCostCenter.style.display = 'none';
    	graphCombinedCostCenter.style.display = 'block';
    	graphTotalBudget.style.display = 'none';
    	graphTotalUnbudgeted.style.display = 'none';
    	graphTotalCombined.style.display = 'none';
    }  
    else if(containerId == 'graphTotalBudget') {
    	graphTotalBudget.style.display = 'block';
    	graphTotalUnbudgeted.style.display = 'none';
    	graphTotalCombined.style.display = 'none';
    	graphBudgetedCostCenter.style.display = 'none';
    	graphUnbudgetedCostCenter.style.display = 'none';
    	graphCombinedCostCenter.style.display = 'none';
    }  
	
}
</script>			
				   <% SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
							String showChart = sessionDataUtil.getShowChart();
							LocationBudgetChartDto chartDto= myForm.getBudgetsGlanceReportingInfo().getBudgetChart();
							CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);

							String periodStr = "";
							ReportingDto reportingInfo = myForm.getBudgetsGlanceReportingInfo();
							if(reportingInfo != null && reportingInfo.getFiscalPeriodSelected()!=null){
			    				periodStr = "("+ClwMessageResourcesImpl.getMessage(request,"reporting.label.fiscalPeriod")+ " " + reportingInfo.getFiscalPeriodSelected() +")";
							}
					%>
					<div>
						<h2><app:storeMessage key="reporting.label.budgetsAtAGlance" />
								    <%=periodStr%>
						</h2>
					    <!--<a href="#" class="blueBtnMed topMargin right"><span><app:storeMessage key="reporting.label.customReport" /></span></a>-->
					    <hr />
              		    <%
              		    	if(chartDto!=null && chartDto.isAccountHasBudget() && user.getShowPrice()){//STJ-4379.
	       						if(Constants.LOCATION_BUDGET_SHOW_COST_CENTER.equalsIgnoreCase(showChart)){
       								%> 	      		                    			
       								<div id="showCCLink" style="display:block"> 	
       								 	<a href="javascript:saveState('hideCC')" class="btn" 
       								 		title="<app:storeMessage key="global.action.label.showBudgetTotal" />">
                                    		<span>
                                    			<app:storeMessage key="global.action.label.showBudgetTotal" />
                                    		</span>
                                    	</a>
                                    	<h3>&nbsp;</h3>
                                    </div>
                                    <% if (user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.DISPLAY_COST_CENTER_DETAIL)){ %>
                                    <div id="hideCCLink" style="display:none"> 	
   										<a href="javascript:saveState('showCC')" class="btn" 
   										title="<app:storeMessage key="global.action.label.showCostCenters" />">
                                    		<span>
                                    			<app:storeMessage key="global.action.label.showCostCenters" />
                                    		</span>
                                    	</a>
                                    	<h3>&nbsp;</h3>
                                  	</div>
                                  	<% } %>
                                    <%  } else {  %>

            						<% if (user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.DISPLAY_COST_CENTER_DETAIL)){ %>
            						<div id="hideCCLink" style="display:block"> 	
   										<a href="javascript:saveState('showCC')" class="btn" 
   										title="<app:storeMessage key="global.action.label.showCostCenters" />">
                                    		<span>
                                    			<app:storeMessage key="global.action.label.showCostCenters" />
                                    		</span>
                                    	</a>
                                    	<h3>&nbsp;</h3>
                                  	</div>
                                  	<% } %>
                                  	<div id="showCCLink" style="display:none"> 	
       								 	<a href="javascript:saveState('hideCC')" class="btn" 
       								 	title="<app:storeMessage key="global.action.label.showBudgetTotal" />">
                                    		<span>
                                    			<app:storeMessage key="global.action.label.showBudgetTotal" />
                                    		</span>
                                    	</a>
                                     	<h3>&nbsp;</h3>
                                    </div>
                                  	<%  }  %>
                                    <% 
           								if(Constants.LOCATION_BUDGET_HIDE_COST_CENTER.equalsIgnoreCase(showChart) || showChart == null){
                                    %>
                                    <div id="ccButtons" style="display:none">
                                    <p class="inputRow">
                                    <label><app:storeMessage key="location.budget.label.displayByCostCenterType" /></label>
                                    	<label><input id="combinedCCId" type="radio" name="costCenterType" value="Combined" onclick="showGraph('graphCombinedCostCenter')"><app:storeMessage key="location.budget.label.combined" /></label>
	                                    <label><input id="budgetedCCId" checked type="radio" name="costCenterType" value="Budgeted" onclick="showGraph('graphBudgetedCostCenter')"/><app:storeMessage key="location.budget.label.budgeted" /></label>
	                                    <label><input id="nonBudgetedCCId" type="radio" name="costCenterType" value="Non-Budgeted" onclick="showGraph('graphUnbudgetedCostCenter')"/><app:storeMessage key="location.budget.label.nonBudgeted" /></label>
                                    </p>
                                    </div>
                                    
                                    <div id="totalButtons" style="display:block">
                                     <p class="inputRow">
                                   <label><app:storeMessage key="location.budget.label.displayByCostCenterType" /></label>
	                                    <label><input id="totalCombinedId" type="radio" name="costCenterType" value="Combined" onclick="showGraph('graphTotalCombined')"><app:storeMessage key="location.budget.label.combined" /></label>
	                                    <label><input id="totalBudgetedId" checked type="radio" name="costCenterType" value="Budgeted" onclick="showGraph('graphTotalBudget')"/><app:storeMessage key="location.budget.label.budgeted" /></label> 
	                                    <label><input id="totalNonbudgetedId" type="radio" name="costCenterType" value="Non-Budgeted" onclick="showGraph('graphTotalUnbudgeted')"/><app:storeMessage key="location.budget.label.nonBudgeted" /></label>
                                    </p>
                                    </div>
									
									<div id="graphTotalBudget" style="display:block">
										<charts:locationBudget chartDto="<%= chartDto%>" totalBgtContainer="graphTotalBudget" 
									                       isReportingChart="true" graph="<%= Constants.TOTAL_BUDGETED %>" theme="themes" />
									</div>
									<div id="graphTotalUnbudgeted" style="display:none">
										<charts:locationBudget chartDto="<%= chartDto%>" totalUnBgtContainer="graphTotalUnbudgeted" 
									                      isReportingChart="true" graph="<%= Constants.TOTAL_UNBUDGETED %>" theme="themes" />
									</div>
									<div id="graphTotalCombined" style="display:none">
										<charts:locationBudget chartDto="<%= chartDto%>" totalCombinedContainer="graphTotalCombined" 
									                      isReportingChart="true" graph="<%= Constants.TOTAL_COMBINED %>" theme="themes" />
									</div>
									<div id="graphBudgetedCostCenter"  style="display:none" >
										<charts:locationBudget chartDto="<%= chartDto%>" costCenterContainer="graphBudgetedCostCenter" 
														  isReportingChart="true" graph="<%= Constants.BUDGETED_COST_CENTER %>" theme="themes" />
									</div>
									<div id="graphUnbudgetedCostCenter"  style="display:none" >
										<charts:locationBudget chartDto="<%= chartDto%>" unbudgetedCostCenterContainer="graphUnbudgetedCostCenter" 
														  isReportingChart="true" graph="<%= Constants.UNBUDGETED_COST_CENTER %>" theme="themes" />
									</div>
									<div id="graphCombinedCostCenter"  style="display:none" >
										<charts:locationBudget chartDto="<%= chartDto%>" combinedCostCenterContainer="graphCombinedCostCenter" 
														  isReportingChart="true" graph="<%= Constants.COMBINED_COST_CENTER %>" theme="themes" />
									</div>
									<% } else {%>
									<div id="totalButtons" style="display:none">
                                    <p class="inputRow">
                                     <label><app:storeMessage key="location.budget.label.displayByCostCenterType" /></label>
	                                    <label><input id="totalCombinedId" type="radio" name="costCenterType" value="Combined" onclick="showGraph('graphTotalCombined')"><app:storeMessage key="location.budget.label.combined" /></label>
	                                    <label><input id="totalBudgetedId" checked type="radio" name="costCenterType" value="Budgeted" onclick="showGraph('graphTotalBudget')"/><app:storeMessage key="location.budget.label.budgeted" /></label> 
	                                    <label><input id="totalNonbudgetedId" type="radio" name="costCenterType" value="Non-Budgeted" onclick="showGraph('graphTotalUnbudgeted')"/><app:storeMessage key="location.budget.label.nonBudgeted" /></label>
                                    </p>
                                    </div>
									<div id="ccButtons" style="display:block">
									<p class="inputRow">
									<label><app:storeMessage key="location.budget.label.displayByCostCenterType" /></label>
										<label><input id="combinedCCId" type="radio" name="costCenterType" value="Combined" onclick="showGraph('graphCombinedCostCenter')"><app:storeMessage key="location.budget.label.combined" /></label>
	                                    <label><input id="budgetedCCId" checked type="radio" name="costCenterType" value="Budgeted" onclick="showGraph('graphBudgetedCostCenter')"/><app:storeMessage key="location.budget.label.budgeted" /></label>
	                                    <label><input id="nonBudgetedCCId" type="radio" name="costCenterType" value="Non-Budgeted" onclick="showGraph('graphUnbudgetedCostCenter')"/><app:storeMessage key="location.budget.label.nonBudgeted" /></label>
                                    </p>
                                    </div>
                                    
									<div id="graphBudgetedCostCenter"  style="display:block" >
										<charts:locationBudget chartDto="<%= chartDto%>" costCenterContainer="graphBudgetedCostCenter" 
														  isReportingChart="true" graph="<%= Constants.BUDGETED_COST_CENTER %>" theme="themes" />
									</div>
									<div id="graphUnbudgetedCostCenter"  style="display:none" >
										<charts:locationBudget chartDto="<%= chartDto%>" unbudgetedCostCenterContainer="graphUnbudgetedCostCenter" 
														  isReportingChart="true" graph="<%= Constants.UNBUDGETED_COST_CENTER %>" theme="themes" />
									</div>
									<div id="graphCombinedCostCenter"  style="display:none" >
										<charts:locationBudget chartDto="<%= chartDto%>" combinedCostCenterContainer="graphCombinedCostCenter" 
														  isReportingChart="true" graph="<%= Constants.COMBINED_COST_CENTER %>" theme="themes" />
									</div>
									<div id="graphTotalBudget" style="display:none">
										<charts:locationBudget chartDto="<%= chartDto%>" totalBgtContainer="graphTotalBudget" 
														  isReportingChart="true" graph="<%= Constants.TOTAL_BUDGETED %>" theme="themes" />
									</div>
									<div id="graphTotalUnbudgeted" style="display:none">
										<charts:locationBudget chartDto="<%= chartDto%>" totalUnBgtContainer="graphTotalUnbudgeted" 
									                      isReportingChart="true" graph="<%= Constants.TOTAL_UNBUDGETED %>" theme="themes" />
									</div>
									<div id="graphTotalCombined" style="display:none">
										<charts:locationBudget chartDto="<%= chartDto%>" totalCombinedContainer="graphTotalCombined" 
									                      isReportingChart="true" graph="<%= Constants.TOTAL_COMBINED %>" theme="themes" />
									</div>
									<%  }  %>
                    <% }   %>
                        <!-- End Box -->
		</div>

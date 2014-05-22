/**
 * Title: ReportingAction 
 * Description: This is the Struts Action class handling the ESW reporting functionality.
 */

package com.espendwise.view.actions.esw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.dto.ReportingDto;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ReportRequest;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.ReportingForm;
import com.espendwise.view.logic.esw.ReportingLogic;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class ReportingAction extends EswAction {
    private static final Logger log = Logger.getLogger(ReportingAction.class);
    
  //constants to hold the various action mappings that can be returned by this action class.
    private static final String MAPPING_ORDERS_REPORTS = "reportingShowOrders";
    private static final String MAPPING_BUDGETS_REPORTS = "reportingShowBudgets";
    private static final String MAPPING_MISC_REPORTS = "reportingShowMiscReports";
    private static final String MAPPING_STANDARD_REPORTS_FILTER = "reportingShowStandardFilter";
    private static final String MAPPING_STANDARD_REPORTS_SEARCH_RESULTS = "reportingShowStandardSearchResults";
    private static final String MAPPING_STANDARD_REPORTS_DOWNLOAD_RESULTS = "reportingDownloadResults";

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     * @param  mapping      the ActionMapping used to select this instance.
     * @param  form         the ActionForm containing the data.
     * @param  request      the HTTP request we are processing.
     * @param  response     the HTTP response we are creating.
     * @return              an ActionForward describing the component that should receive control.
     */
    public ActionForward performAction(ActionMapping mapping, ActionForm form,
            						HttpServletRequest request, HttpServletResponse response) {

        //If there isn't a currently logged on user then go to the login page
        if (!new SessionTool(request).checkSession()) {
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
        }
        
    	ActionForward returnValue = null;
        ReportingForm theForm = (ReportingForm)form;
        
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    	//make sure the user has the ability to run reports - if not return an error
    	HttpSession session = request.getSession();
    	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);        
    	if (user.isNoReporting()) {
	        ActionErrors errors = new ActionErrors();
	        String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.unauthorized", null);
	        errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	        saveErrors(request, errors);
	    	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
    	}
    	//determine what action to perform
        //If an operation has been specified use it.
    	String operation = theForm.getOperation();
        //If no operation was specified but there is a previously executed operation use it.
    	if (!Utility.isSet(operation)) {
    		operation = sessionDataUtil.getPreviousReportingAction();
    	}
    	//If no operation was specified and there is no previous operation, default to show
    	//standard reports
    	boolean doesAnyAccountSupportsBudgets = ShopTool.doesAnyAccountSupportsBudgets(request);
    	if (!Utility.isSet(operation)) {
    	    if (doesAnyAccountSupportsBudgets) {
    		    operation = Constants.PARAMETER_OPERATION_VALUE_SHOW_BUDGETS_REPORTS;
            } else {
                operation = Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDERS_REPORTS;
            }
    	}
    	//trim whitespace if an operation has been specified
    	if (Utility.isSet(operation)) {
    		operation = operation.trim();
    	}
    	
    	//now that we've determined what action to take, take it
    	boolean rememberOperation = true;
    	
    	if (Constants.PARAMETER_OPERATION_VALUE_SHOW_BUDGETS_REPORTS.equalsIgnoreCase(operation)) {
        	returnValue = handleShowBudgetsReportsRequest(request, response, theForm, mapping);
            //set the active sub tab to be budgets.
    		Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.TAB_BUDGETS_REPORTS);
        }else if(Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDERS_REPORTS.equalsIgnoreCase(operation)) {
        	returnValue = handleShowOrdersReportsRequest(request, response, theForm, mapping);
            //set the active sub tab to be orders.
    		Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.TAB_ORDERS_REPORTS);
        }
    	
        //Begin: Standard(Misc) Reports
        else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_MISC_REPORTS.equalsIgnoreCase(operation)){
        	returnValue = handleShowMiscReportsRequest(request, response, theForm, mapping);
            //set the active sub tab to be misc.
            Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.TAB_MISC_REPORTS);
        }else if(Constants.PARAMETER_OPERATION_VALUE_CHANGE_STANDARD_FILTER_REPORTS.equalsIgnoreCase(operation)) {
            rememberOperation = false;
            returnValue = handleShowStandardReportsFilterRequest(request, response, theForm, mapping, true);
        }else if(Constants.PARAMETER_OPERATION_VALUE_SHOW_STANDARD_FILTER_REPORTS.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleShowStandardReportsFilterRequest(request, response, theForm, mapping, false);
        }else if(Constants.PARAMETER_OPERATION_VALUE_SEARCH_STANDARD_REPORT.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
			returnValue = handleSearchStandardReportsRequest(request, response, theForm,mapping);
        }else if (Constants.PARAMETER_OPERATION_VALUE_REPORT_REULSTS_SUB_TAB.equalsIgnoreCase(operation)){ 
    		rememberOperation = false;
    		returnValue = handleSelectReportResultSubTabRequest(request, response, theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_DOWNLOAD_REPORT.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	handleDownloadReportRequest(request, response, theForm, mapping);
        }
        //End: Standard(Misc) Reports
    	
        else if (Constants.PARAMETER_OPERATION_VALUE_FILTER_BUDGETS_REPORT.equalsIgnoreCase(operation)){
        	//rememberOperation = false;
        	returnValue = handleFilterBudgetsReportRequest(request, response, theForm, mapping);
        }else if (Constants.PARAMETER_OPERATION_VALUE_FILTER_ORDERS_REPORT.equalsIgnoreCase(operation)){
        	rememberOperation = false;
        	returnValue = handleFilterOrdersReportRequest(request, response, theForm, mapping);
        }else if (Constants.PARAMETER_OPERATION_VALUE_FILTER_ORDERS_REPORT_CATEGORIES.equalsIgnoreCase(operation)){
        	rememberOperation = false;
        	returnValue = handleFilterOrdersReportCategoriesRequest(request, response, theForm, mapping);
        }else if (Constants.PARAMETER_OPERATION_VALUE_FILTER_GENERIC_REPORT_CATEGORIES.equalsIgnoreCase(operation)){
        	rememberOperation = false;
        	returnValue = handleFilterGenericReportCategoriesRequest(request, response, theForm, mapping);
        }else if(Constants.PARAMETER_OPERATION_VALUE_GENERATE_EXCEL_REPORT.equalsIgnoreCase(operation)){
        	rememberOperation = false;
        	returnValue = handleExcelGeneratorRequest(request, response, theForm, mapping);
        }else if(MAPPING_STANDARD_REPORTS_SEARCH_RESULTS.equalsIgnoreCase(operation) ||
        		MAPPING_STANDARD_REPORTS_FILTER.equalsIgnoreCase(operation)){
        	//if there are any errors in the session (which will be the case if there were validation errors),
            //retrieve them and save them as errors in the request.  This is something of a hack, but these
            //errors needed to be persisted across a redirect.
            Object errors = Utility.getSessionDataUtil(request).getErrors();
            if (errors != null && errors instanceof ActionErrors) {
          	  saveErrors(request, (ActionErrors)errors);
          	  //now that the errors are processed, blank them out
          	  Utility.getSessionDataUtil(request).setErrors(null);
            }
        	rememberOperation = false;
        	returnValue = mapping.findForward(operation);
        }else if(MAPPING_STANDARD_REPORTS_DOWNLOAD_RESULTS.equalsIgnoreCase(operation)){
        	rememberOperation = false;
        	ReportingLogic.downLoadCustomerFile(request, response, theForm);
        }else{
        	rememberOperation = false;
			returnValue = handleUnknownOperation(request, response, theForm,mapping);
		}
    	
    	
    	if(rememberOperation) {
    		sessionDataUtil.setPreviousReportingAction(operation);
    	}
        
    	return returnValue;    
    }
    
    /*
     * Private method to determine what action forward should be returned after a show budgets reports request.
     */
	private ActionForward handleShowBudgetsReportsRequest(HttpServletRequest request,
			HttpServletResponse response, ReportingForm theForm,
			ActionMapping mapping) {
		
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		ReportingDto reportingInfo = sessionDataUtil.getBudgetsGlanceReportingDto();

		if(reportingInfo!=null) {
			theForm.setBudgetsGlanceReportingInfo(reportingInfo);
		}

		return handleFilterBudgetsReportRequest(request,response,theForm,mapping);
		
	}
	
	private ActionForward handleFilterBudgetsReportRequest(HttpServletRequest request,
			HttpServletResponse response, ReportingForm theForm,ActionMapping mapping) {
		ActionErrors errors = new ActionErrors();
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		ReportingDto reportingInfo = theForm.getBudgetsGlanceReportingInfo();
		ReportingLogic.initReportingCriteria(request, reportingInfo);
		
		populateCommonData(request,theForm);
		SiteData currentLocation = ShopTool.getCurrentSite(request);
    	//if the user has not yet selected a location, return an error
    	/*if (currentLocation == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.noLocationSelected", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));  		
    	}else{
    		//Get search results
    		errors = ReportingLogic.showBudgetsReport(request, reportingInfo,currentLocation,false);
    	}*/
		//STJ-5135
		errors = ReportingLogic.showBudgetsReport(request, reportingInfo,currentLocation,false, null, null);
		
		if (errors == null || errors.isEmpty()) {
            sessionDataUtil.setBudgetsGlanceReportingDto(reportingInfo);
        }
		
		if(errors != null && !errors.isEmpty()) {
			saveErrors(request, errors);
		}else {
			ActionMessages messages = null;
			if(reportingInfo.getBudgetChart() != null && reportingInfo.getBudgetChart().getSiteWithNoDataList().size()>0){
				messages = new ActionMessages();
	        	Object[] param = new Object[1];
	        	ArrayList<Integer> siteIdList = new ArrayList<Integer>(reportingInfo.getBudgetChart().getSiteWithNoDataList());
	        	ArrayList<String> siteNames = ReportingLogic.getSiteNameFromId(siteIdList);
	        	param[0] = Utility.toCommaSting(siteNames);
	            String noLastPeriodmessage = ClwI18nUtil.getMessage(request,"budget.errors.noLastPeriodData",param);
	            messages.add("message", new ActionMessage("message.simpleMessage", noLastPeriodmessage));
	            saveMessages(request, messages);
			}
	        
			if(reportingInfo.getBudgetChart() != null && reportingInfo.getBudgetChart().getNoLastFiscalYearList().size()>0){
	        	
	            if(messages == null)
	            	messages = new ActionMessages();
		        Object[] param = new Object[1];
		        ArrayList<String> siteNames = ReportingLogic.getSiteNameFromId(reportingInfo.getBudgetChart().getNoLastFiscalYearList());
		        param[0] = Utility.toCommaSting(siteNames);
		        String noLastPeriodmessage = ClwI18nUtil.getMessage(request,"budget.errors.noLastYearOrPeriodData",param);
		        messages.add("message", new ActionMessage("message.simpleMessage", noLastPeriodmessage));
		        saveMessages(request, messages);
	        }
	       
			if (messages == null && (errors == null || (reportingInfo.getBudgetChart()==null
				|| reportingInfo.getBudgetChart().getAllocatedBudget().size()==0))) {
				if(messages == null)
	            	messages = new ActionMessages();
	        	String message = ClwI18nUtil.getMessage(request,"reporting.search.noResults", null);
	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	        	saveMessages(request, messages);	
			}
        	
		}
		
    	return mapping.findForward(MAPPING_BUDGETS_REPORTS);
	}
	
	
	
	/*
     * Private method to determine what action forward should be returned after a show orders reports request.
     */
	private ActionForward handleShowOrdersReportsRequest(HttpServletRequest request,
			HttpServletResponse response, ReportingForm theForm,
			ActionMapping mapping) {
		
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		ReportingDto reportingInfo = sessionDataUtil.getOrdersGlanceReportingDto();

		if(reportingInfo!=null) {
			theForm.setOrdersGlanceReportingInfo(reportingInfo);
		}

		return handleFilterOrdersReportRequest(request,response,theForm,mapping);
	}
	
	private ActionForward handleFilterOrdersReportRequest(HttpServletRequest request,
			HttpServletResponse response, ReportingForm theForm,ActionMapping mapping) {
		ActionErrors errors = new ActionErrors();
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		ReportingDto reportingInfo = theForm.getOrdersGlanceReportingInfo();
		ReportingLogic.initReportingCriteria(request, reportingInfo);
		
		populateCommonData(request, theForm);
		
		SiteData currentLocation = ShopTool.getCurrentSite(request);
    	//if the user has not yet selected a location, return an error
    	/*if (currentLocation == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.noLocationSelected", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));  		
    	}else{
    		//Get search results
    		errors = ReportingLogic.showOrdersReport(request, reportingInfo);
    	}	*/
		//STJ-5135
		errors = ReportingLogic.showOrdersReport(request, reportingInfo);

		if (errors == null || errors.isEmpty()) {
			theForm.setOrdersGlanceReportingInfo(reportingInfo);
            sessionDataUtil.setOrdersGlanceReportingDto(reportingInfo);
            
            handleFilterOrdersReportCategoriesRequest(request,response,theForm, mapping);
      
        }else{
        	//preserving filter values
    		ReportingDto sessionDto = sessionDataUtil.getOrdersGlanceReportingDto();
    		if(sessionDto!=null){
       		reportingInfo.setMfgList(sessionDto.getMfgList());
    		reportingInfo.setCategory1(sessionDto.getCategory1());
    		reportingInfo.setCategory2(sessionDto.getCategory2());
    		reportingInfo.setCategory3(sessionDto.getCategory3());
    		reportingInfo.setCategory4(sessionDto.getCategory4());
    		
    		handleFilterOrdersReportCategoriesRequest(request,response,theForm, mapping);
    		}
    		theForm.setOrdersGlanceReportingInfo(reportingInfo);
        }
		
		if (errors.isEmpty() && (reportingInfo.getOrdersReportChart()==null || 
				reportingInfo.getOrdersReportChart().getCategoryAmountMap()==null || 
				reportingInfo.getOrdersReportChart().getCategoryAmountMap().size()==0) 
				) {
        	ActionMessages messages = new ActionMessages();
        	String message = ClwI18nUtil.getMessage(request,"reporting.search.noResults", null);
        	messages.add("message", new ActionMessage("message.simpleMessage", message));
        	saveMessages(request, messages);
		}
	
		if(errors != null && !errors.isEmpty()) {
			saveErrors(request, errors);
			
		}

		
		return mapping.findForward(MAPPING_ORDERS_REPORTS);
	}
	
	private ActionForward handleExcelGeneratorRequest(HttpServletRequest request,
			HttpServletResponse response, ReportingForm theForm,ActionMapping mapping) {
		ActionErrors errors = new ActionErrors();
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		ReportingDto reportingInfo = theForm.getOrdersGlanceReportingInfo();
		ReportingLogic.initReportingCriteria(request, reportingInfo);
		populateCommonData(request, theForm);
		SiteData currentLocation = ShopTool.getCurrentSite(request);
    	//if the user has not yet selected a location, return an error
    	if (currentLocation == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.noLocationSelected", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));  		
    	}else{
    		//Get search results
    		errors = ReportingLogic.showOrdersReport(request, reportingInfo);
    	}	
		if(errors != null && !errors.isEmpty()) {
			saveErrors(request, errors);
		    return mapping.findForward(MAPPING_ORDERS_REPORTS);
		}
		
		if (errors.isEmpty() && (
		        reportingInfo == null ||
		        reportingInfo.getOrdersReportChart() == null ||
		        reportingInfo.getOrdersReportChart().getCategoryAmountMap()==null ||
		        reportingInfo.getOrdersReportChart().getCategoryAmountMap().size()==0)) {
        	ActionMessages messages = new ActionMessages();
        	String message = ClwI18nUtil.getMessage(request,"reporting.search.noResults", null);
        	messages.add("message", new ActionMessage("message.simpleMessage", message));
        	saveMessages(request, messages);

        	
		}
		
		if (errors == null || errors.isEmpty()) {
			theForm.setOrdersGlanceReportingInfo(reportingInfo);
            sessionDataUtil.setOrdersGlanceReportingDto(reportingInfo);
            
            ReportingDto sessionReportingInfo = sessionDataUtil.getOrdersGlanceReportingDto();
    		sessionReportingInfo = populateCategoryFilters(request,sessionReportingInfo, reportingInfo,true);
    		//populate form with report result DV
    		ReportRequest reportRequest = getReportRequest(request);
    		errors = ReportingLogic.generateReport(request, response, theForm, reportRequest);

            if(errors != null && !errors.isEmpty()) {
                saveErrors(request, errors);
                return mapping.findForward(MAPPING_ORDERS_REPORTS);
            }

    		//create xls file
    		handleDownloadReportRequest(request, response,theForm,mapping);
        }
         
		return null;
		
	}
	
	private ActionForward handleFilterOrdersReportCategoriesRequest(HttpServletRequest request,
			HttpServletResponse response, ReportingForm theForm,ActionMapping mapping) {
		
		ActionForward returnValue = null;
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		ReportingDto sessionReportingInfo = sessionDataUtil.getOrdersGlanceReportingDto();
		ReportingDto reportingInfo = theForm.getOrdersGlanceReportingInfo();
		
		sessionReportingInfo = populateCategoryFilters(request,sessionReportingInfo, reportingInfo,true);

		StringBuffer responseJson = createResponse(sessionReportingInfo);
		
		sessionDataUtil.setOrdersGlanceReportingDto(sessionReportingInfo);
		theForm.setOrdersGlanceReportingInfo(sessionReportingInfo);
    	try {
    		response.setContentType(Constants.CONTENT_TYPE_JSON);
    		response.setHeader("Cache-Control", "no-cache");
    		response.setCharacterEncoding(Constants.UTF_8);
    		response.getWriter().write(responseJson.toString());
    	}
    	catch (Exception e) {
            ActionErrors errors = new ActionErrors();
            String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            saveErrors(request, errors);
        	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);    
    	}
    	return returnValue;
	}

	private ReportingDto populateCategoryFilters(HttpServletRequest request,ReportingDto sessionDto, ReportingDto formDto,
			boolean showBreadCrumb){
		
		String allCategories = ClwI18nUtil.getMessage(request, "reporting.filter.allCategories", null);
		if(sessionDto.getCat1Selected()!=null && 
				!(sessionDto.getCat1Selected().equals(formDto.getCat1Selected()))){
			sessionDto.setCat1Selected(formDto.getCat1Selected());
			sessionDto.setCat2Selected(allCategories);
			sessionDto.setCat3Selected(allCategories);
			sessionDto.setCat4Selected(allCategories);
		}else if(sessionDto.getCat2Selected()!=null && 
				!(sessionDto.getCat2Selected().equals(formDto.getCat2Selected()))){
			sessionDto.setCat2Selected(formDto.getCat2Selected());
			sessionDto.setCat3Selected(allCategories);
			sessionDto.setCat4Selected(allCategories);
		}else if(sessionDto.getCat3Selected()!=null && 
				!(sessionDto.getCat3Selected().equals(formDto.getCat3Selected()))){
			sessionDto.setCat3Selected(formDto.getCat3Selected());
			sessionDto.setCat4Selected(allCategories);
		}else if(sessionDto.getCat4Selected()!=null && 
				!(sessionDto.getCat4Selected().equals(formDto.getCat4Selected()))){
			sessionDto.setCat4Selected(formDto.getCat4Selected());
		}
		
		sessionDto = ReportingLogic.populateCategoryFilters(request,sessionDto,showBreadCrumb);
		
		return sessionDto;
	}
	
	private StringBuffer createResponse(ReportingDto rDto){
		
		StringBuffer responseJson = new StringBuffer();
		boolean includeComma = false;
		
		List<LabelValueBean> cat2OptionsList = rDto.getCategory2();
		List<LabelValueBean> cat3OptionsList = rDto.getCategory3();
		List<LabelValueBean> cat4OptionsList = rDto.getCategory4();
		
		responseJson.append("{");
		if(cat2OptionsList!=null && cat2OptionsList.size() > 1){
			
			responseJson.append("\"catOptions2\": [");
	        
	        Iterator options2Iterator = cat2OptionsList.iterator();
	        while (options2Iterator.hasNext()) {
	        	if (includeComma) {
	        		responseJson.append(", ");
	        	}
	        	includeComma = true;
	        	LabelValueBean option = (LabelValueBean)options2Iterator.next();
	        	responseJson.append("{\"name\": \"");
	        	responseJson.append(option.getLabel());
	        	responseJson.append("\",\"id\": \"");
	        	responseJson.append(option.getValue());
	        	responseJson.append("\"}");
	        }
	        responseJson.append("]");
			
		}
		
		if(cat3OptionsList!=null && cat3OptionsList.size() > 1){
			if (includeComma) {
        		responseJson.append(", ");
        	}
			responseJson.append("\"catOptions3\": [");
	        includeComma = false;
	        Iterator options3Iterator = cat3OptionsList.iterator();
	        while (options3Iterator.hasNext()) {
	        	if (includeComma) {
	        		responseJson.append(", ");
	        	}
	        	includeComma = true;
	        	LabelValueBean option = (LabelValueBean)options3Iterator.next();
	        	responseJson.append("{\"name\": \"");
	        	responseJson.append(option.getLabel());
	        	responseJson.append("\",\"id\": \"");
	        	responseJson.append(option.getValue());
	        	responseJson.append("\"}");
	        }
	        responseJson.append("]");
		
		}
        
		if(cat4OptionsList!=null && cat4OptionsList.size() > 1){
			if (includeComma) {
        		responseJson.append(", ");
        	}
			responseJson.append("\"catOptions4\": [");
	        includeComma = false;
	        Iterator options4Iterator = cat4OptionsList.iterator();
	        while (options4Iterator.hasNext()) {
	        	if (includeComma) {
	        		responseJson.append(", ");
	        	}
	        	includeComma = true;
	        	LabelValueBean option = (LabelValueBean)options4Iterator.next();
	        	responseJson.append("{\"name\": \"");
	        	responseJson.append(option.getLabel());
	        	responseJson.append("\",\"id\": \"");
	        	responseJson.append(option.getValue());
	        	responseJson.append("\"}");
	        }
	        responseJson.append("]");
			
		}
		
		if (includeComma) {
    		responseJson.append(", ");
    	}
		responseJson.append("\"categoriesSelected\": [");
		responseJson.append("{\"cat2Selected\": \"");
    	responseJson.append(rDto.getCat2Selected());
    	responseJson.append("\", ");
    	responseJson.append("\"cat3Selected\": \"");
    	responseJson.append(rDto.getCat3Selected());
    	responseJson.append("\", ");
    	responseJson.append("\"cat4Selected\": \"");
    	responseJson.append(rDto.getCat4Selected());
    	responseJson.append("\"}");
		responseJson.append("]");
        
		responseJson.append("}");
		
		
		return responseJson;
	}
	
	private ActionForward handleFilterGenericReportCategoriesRequest(HttpServletRequest request,
			HttpServletResponse response, ReportingForm theForm,ActionMapping mapping) {
		
		ActionForward returnValue = null;
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		ReportingDto sessionReportingInfo = sessionDataUtil.getGenericReportingDto();
		ReportingDto reportingInfo = theForm.getCustomerReportingForm().getGenericReportingInfo();
		
		sessionReportingInfo = populateCategoryFilters(request,sessionReportingInfo, reportingInfo,false);

		StringBuffer responseJson = createResponse(sessionReportingInfo);
	
		sessionDataUtil.setGenericReportingDto(sessionReportingInfo);
		theForm.getCustomerReportingForm().setGenericReportingInfo(sessionReportingInfo);
    	try {
    		response.setContentType(Constants.CONTENT_TYPE_JSON);
    		response.setHeader("Cache-Control", "no-cache");
    		response.setCharacterEncoding(Constants.UTF_8);
    		response.getWriter().write(responseJson.toString());
    	}
    	catch (Exception e) {
            ActionErrors errors = new ActionErrors();
            String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            saveErrors(request, errors);
        	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);    
    	}
    	return returnValue;
	}
	
	/*
     * Private method to determine what action forward should be returned after a show misc reports request.
     */
	private ActionForward handleShowMiscReportsRequest(HttpServletRequest request,
			HttpServletResponse response, ReportingForm theForm,
			ActionMapping mapping) {
        ActionErrors errors = new ActionErrors();
        populateCommonData(request, theForm);
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        ReportingDto reportingInfo = theForm.getCustomerReportingForm().getGenericReportingInfo();
        errors = ReportingLogic.showMiscReports(request, reportingInfo);
        if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);
        }
        if (errors == null || errors.isEmpty()) {
            sessionDataUtil.setGenericReportingDto(reportingInfo);
        }
        return mapping.findForward(MAPPING_MISC_REPORTS);
	}
	
	/*
     * Private method to determine what action forward should be returned after a show standard reports filter request.
     */
	private ActionForward handleShowStandardReportsFilterRequest(HttpServletRequest request,
			HttpServletResponse response, ReportingForm theForm,
			ActionMapping mapping, boolean setPreviousValues) {
		ActionForward forwardName =  mapping.findForward(MAPPING_MISC_REPORTS);
        ActionErrors errors = null;
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        try {
			errors = ReportingLogic.showStandardReportFilter(request, theForm, setPreviousValues);
		} catch (Exception e) {
			  if(errors==null) {
				  errors = new ActionErrors();
			  }
	          String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
	          errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		}
		
		if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);
            // Set Reporting Info from the session if there are any errors.        
            theForm.getCustomerReportingForm().setGenericReportingInfo(sessionDataUtil.getGenericReportingDto());
            sessionDataUtil.setSelectedSubTab(Constants.TAB_MISC_REPORTS);
        } else {
        	theForm.getCustomerReportingForm().setGenericReportingInfo(sessionDataUtil.getGenericReportingDto());
			handleFilterGenericReportCategoriesRequest(request,response,theForm, mapping);
        	forwardName =  mapping.findForward(MAPPING_STANDARD_REPORTS_FILTER);
        }
        
        return forwardName;
	}
	
	/*
     * Private method to determine what action forward should be returned after a search standard reports request.
     */
	private ActionForward handleSearchStandardReportsRequest(HttpServletRequest request,
			HttpServletResponse response, ReportingForm theForm,
			ActionMapping mapping) {
		String forwardName =  MAPPING_STANDARD_REPORTS_FILTER;
        ActionErrors errors = new ActionErrors();
        
        SessionDataUtil sessionDataUtils = Utility.getSessionDataUtil(request);

        try {
        	//If Report Generation is in Process, return an error message
        	if(sessionDataUtils.isReportIsInProgress()) {
        		 Object[] params = new Object[1]; 
        		 params[0] = theForm.getCustomerReportingForm().getReportTypeCd();
        		 String errorMess = ClwI18nUtil.getMessage(request, "report.errors.reportIsInProgress", params);
   	          	 errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        	} else {
        		sessionDataUtils.setReportIsInProgress(true);
                ReportRequest reportRequest = getReportRequest(request);

                String lockedParams = "'" +  theForm.getCustomerReportingForm().getReportTypeCd() + "'";
                if(lockAction(request, Constants.lockName , lockedParams, errors, Constants.lockMessage)) {
                	try {
                		errors = ReportingLogic.searchCustomerReport(request, response, theForm,reportRequest);
                	} finally {
                		unLockAction(request, Constants.lockName);
                	}
                }

        	}
			
		} catch (Exception e) {
	          String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
	          errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		} finally {
			sessionDataUtils.setReportIsInProgress(false);
		}
		
		//Save Errors if there are any
		if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);
            Utility.getSessionDataUtil(request).setErrors(errors);
        } else {
        	forwardName = MAPPING_STANDARD_REPORTS_SEARCH_RESULTS;
        	GenericReportResultView reportResult = (GenericReportResultView) theForm.getCustomerReportingForm().getReportResults().get(0);
        	if (reportResult.getName().equals(RefCodeNames.CUSTOMER_REPORT_TYPE_CD.INVOICE_LISTING)){        	
        		forwardName = MAPPING_STANDARD_REPORTS_DOWNLOAD_RESULTS;
        	}
        }
		
		response.setContentType("application/xml");
        response.setHeader("Cache-Control", "no-cache");
                
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
            docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.getDOMImplementation().createDocument("", "response", null);
            Element root =  doc.getDocumentElement();
            
            root.setAttribute("forwardAction", forwardName);
            OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
            XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
            serializer.serialize(root);
        } catch (Exception ex) { 
        }
        
        return null;
	}
	
	/*
     * Private method to determine what action forward should be returned after a Select Report Reulst's Sub Tab request.
     */
	private ActionForward handleSelectReportResultSubTabRequest(HttpServletRequest request,
			HttpServletResponse response, ReportingForm theForm,
			ActionMapping mapping) {
		
		ActionForward forwardName =  mapping.findForward(MAPPING_STANDARD_REPORTS_SEARCH_RESULTS);
        ActionErrors errors = null;
        
        try {
			 errors = ReportingLogic.selectReportResultsTab(request,theForm);
		} catch (Exception e) {
			  errors = new ActionErrors();
	          String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
	          errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		}
		
		//Save Errors if there are any
		if (errors != null && !errors.isEmpty()) 
            saveErrors(request, errors);
		
        return forwardName;
	}
	
	/*
     * Private method to download report result data. 
     */
	private void handleDownloadReportRequest(HttpServletRequest request,
			HttpServletResponse response, ReportingForm theForm,
			ActionMapping mapping) {
        
        try {
        	 ReportRequest reportRequest = getReportRequest(request);
			 ReportingLogic.downLoadCustomerReport(request,response,theForm,reportRequest);
		} catch (Exception e) {
			  log.error("An Exception has occured while downloading the Excel Report");
		}
	}
	
	private void populateCommonData(HttpServletRequest request, ReportingForm form) {
		
		form.setFiscalPeriodFilterChoices(ClwI18nUtil.getFiscalPeriodFilterChoices(request));
		SiteData currentLocation = ShopTool.getCurrentSite(request);

		List<LabelValueBean> locationChoices = null;
		
		if(currentLocation!=null) {
			locationChoices = ClwI18nUtil.getLocationChoices(request);
		} else {
			locationChoices = new ArrayList<LabelValueBean>(); 
		}
    	
    	try {
    		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    		//STJ-5677
	        //LocationSearchDto locationSearchDto = sessionDataUtil.getLocationSearchDto();
    		LocationSearchDto locationSearchDto = sessionDataUtil.getLocationSearchDtoMap().get(Constants.SPECIFY_LOCATIONS_STANDARD_REPORTS);
    		if(locationSearchDto!=null && Utility.isSet(locationSearchDto.getMatchingLocations()) && locationSearchDto.getMatchingLocations().size()>1) {
    			ReportingDto ordersGlanceReportDto = sessionDataUtil.getOrdersGlanceReportingDto();
    			if(ordersGlanceReportDto==null) {
    				ordersGlanceReportDto = form.getOrdersGlanceReportingInfo();
    			}
        		//Need to add All Location(Number of Locations) as the first element in the list. If there are matching locations.
            	locationChoices.add(0,new LabelValueBean(ordersGlanceReportDto.getLocations(), Constants.ORDERS_ALL_LOCATIONS));
            	
            	ReportingDto budgetsGlanceReportDto = sessionDataUtil.getBudgetsGlanceReportingDto();
    			if(budgetsGlanceReportDto==null) {
    				budgetsGlanceReportDto = form.getBudgetsGlanceReportingInfo();
    			}
        		//Need to add All Location(Number of Locations) as the first element in the list. If there are matching locations.
            	locationChoices.add(0,new LabelValueBean(budgetsGlanceReportDto.getLocations(), Constants.ORDERS_ALL_LOCATIONS));
            	
            	//generic reports
            	ReportingDto genericReportDto = sessionDataUtil.getGenericReportingDto();
    			if(genericReportDto==null) {
    				if(form.getCustomerReportingForm()!=null){
    					genericReportDto = form.getCustomerReportingForm().getGenericReportingInfo();
    				}
    			}
        		//Need to add All Location(Number of Locations) as the first element in the list. If there are matching locations.
            	if(genericReportDto!=null){
            		locationChoices.add(0,new LabelValueBean(genericReportDto.getLocations(), Constants.ORDERS_ALL_LOCATIONS));
            	}
    			
        	}
    	}finally{    		
    	}
    	form.setLocationFilterChoices(locationChoices);
    	
    	//Date Range
    	form.setDateRangeFieldChoices(ClwI18nUtil.getReportingDateRangeFieldChoices(request));
    	
	}
	
	private ReportRequest getReportRequest(HttpServletRequest request) {
		//Below logic is copied from OLD Action/Logic classes.
		SessionTool st = new SessionTool(request);
        MessageResources mr = getResources(request);
        String dateFmt = ClwI18nUtil.getDatePattern(request);
        ReportRequest reportRequest = new ReportRequest(st.getUserData(),mr,dateFmt);
        
        return reportRequest;
	}
}



/**
 * 
 */
package com.espendwise.view.logic.esw;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.Collator;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dto.CategoryStructureDto;
import com.cleanwise.service.api.dto.LocationBudgetChartDto;
import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.dto.OrdersAtAGlanceDto;
import com.cleanwise.service.api.dto.ReportingDto;
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Budget;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.FreightTable;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.CategoryItemMfgKey;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.AccountUIView;
import com.cleanwise.service.api.value.AccountUIViewVector;
import com.cleanwise.service.api.value.BudgetDetailData;
import com.cleanwise.service.api.value.BudgetDetailDataVector;
import com.cleanwise.service.api.value.BudgetSpendView;
import com.cleanwise.service.api.value.BudgetView;
import com.cleanwise.service.api.value.BudgetViewVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.FiscalCalendarInfo;
import com.cleanwise.service.api.value.FiscalCalendarInfo.BudgetPeriodInfo;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.FiscalCalenderDetailDataVector;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.FiscalCalenderViewVector;
import com.cleanwise.service.api.value.FreightTableCriteriaData;
import com.cleanwise.service.api.value.FreightTableData;
import com.cleanwise.service.api.value.GenericReportControlView;
import com.cleanwise.service.api.value.GenericReportControlViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportView;
import com.cleanwise.service.api.value.GenericReportViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderFreightData;
import com.cleanwise.service.api.value.OrderFreightDataVector;
import com.cleanwise.service.api.value.OrderHandlingItemView;
import com.cleanwise.service.api.value.OrderHandlingItemViewVector;
import com.cleanwise.service.api.value.OrderHandlingView;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartDistData;
import com.cleanwise.service.api.value.ShoppingCartDistDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.forms.CustAcctMgtReportForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.i18n.ClwMessageResourcesImpl;
import com.cleanwise.view.logic.AnalyticReportLogic;
import com.cleanwise.view.logic.CustAcctMgtReportLogic;
import com.cleanwise.view.logic.ProfilingMgrLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ReportRequest;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.ReportingForm;
/**
 * @author ssharma
 *
 */
public class ReportingLogic {

	private static final Logger log = Logger.getLogger(ReportingLogic.class);
	/**
	 * Initializes Reporting Criteria with default values if they are not set.
	 * @param request
	 * @param orderSearchDto
	 */
	public static void initReportingCriteria(HttpServletRequest request, ReportingDto reportingDto) {
		
		SiteData currentLocation = ShopTool.getCurrentSite(request);
		
		if(!Utility.isSet(reportingDto.getLocationSelected())) {
			if(currentLocation != null){
				reportingDto.setLocationSelected(Constants.ORDERS_CURRENT_LOCATION);
			}else{
				reportingDto.setLocationSelected(Constants.ORDERS_SPECIFIED_LOCATIONS);//STJ-5135
			}
			
		}
		
		if(!Utility.isSet(reportingDto.getLocations())) {
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			//STJ-5677
	        //LocationSearchDto locationSearchDto = sessionDataUtil.getLocationSearchDto();
	        LocationSearchDto locationSearchDto = sessionDataUtil.getLocationSearchDtoMap().get(Constants.SPECIFY_LOCATIONS_STANDARD_REPORTS);
	        Object[] params = new Object[1];
 	        params[0] = 0;
	        if(locationSearchDto==null) {
	        	reportingDto.setLocationSelected(Constants.ORDERS_CURRENT_LOCATION);
	        } else {
	 	        params[0] = locationSearchDto.getMatchingLocations().size();
	        }
	        reportingDto.setLocations(ClwI18nUtil.getMessage(request, "orders.filterPane.label.allLocations", params));
		}
		
		if(!Utility.isSet(reportingDto.getFiscalPeriod())) {
			
	    	if (currentLocation != null && Utility.isSet(currentLocation.getBudgetPeriods())) {
	    		reportingDto.setFiscalPeriod(Constants.REPORTING_FILTER_THIS_PERIOD);   		
	    	}else{
	    		reportingDto.setFiscalPeriod(Constants.REPORTING_FILTER_THIS_FISCAL_YEAR);    		
	    	}
		}
		
		//Default value for Order Date 
        if(!Utility.isSet(reportingDto.getDateRange())) {
        	reportingDto.setDateRange(Constants.DATE_RANGE_30_DAYS);
        }
        
        //If Custom Date range is not set then Initialize From & To date values with 
        //Logged in User's locale date format.
        if(!reportingDto.getDateRange().trim().equals(Constants.DATE_RANGE_CUSTOM_RANGE)) {
        	String userLocaleDateFormat = ClwI18nUtil.getUIDateFormat(request);
        	reportingDto.setFrom(userLocaleDateFormat);
        	reportingDto.setTo(userLocaleDateFormat);
        }
   
        String allMfgs = ClwI18nUtil.getMessage(request, "reporting.filter.allManufacturers", null);
        
        if(!Utility.isSet(reportingDto.getMfgSelected())){
        	reportingDto.setMfgSelected(allMfgs);
        }
        if(reportingDto.getMfgList()==null){
        	List<LabelValueBean> mfgList = new ArrayList<LabelValueBean>();
            mfgList.add(0,new LabelValueBean(allMfgs, allMfgs));
            reportingDto.setMfgList(mfgList);   
        }
 
        initCategoriesCriteria(request,reportingDto);
	}
	
	public static void initCategoriesCriteria(HttpServletRequest request, ReportingDto reportingDto) {
		String allCategories = ClwI18nUtil.getMessage(request, "reporting.filter.allCategories", null);
		if(!Utility.isSet(reportingDto.getCat1Selected())){
        	reportingDto.setCat1Selected(allCategories);
        }
        if(!Utility.isSet(reportingDto.getCat2Selected())){
        	reportingDto.setCat2Selected(allCategories);
        }
        if(!Utility.isSet(reportingDto.getCat3Selected())){
        	reportingDto.setCat3Selected(allCategories);
        }
        if(!Utility.isSet(reportingDto.getCat4Selected())){
        	reportingDto.setCat4Selected(allCategories);
        }
        if(!Utility.isSet(reportingDto.getChartCatSelected())){
        	reportingDto.setChartCatSelected(StringUtils.EMPTY);
        }
        List<LabelValueBean> level1 = new ArrayList<LabelValueBean>();
        List<LabelValueBean> level2 = new ArrayList<LabelValueBean>();
        List<LabelValueBean> level3 = new ArrayList<LabelValueBean>();
        List<LabelValueBean> level4 = new ArrayList<LabelValueBean>();
        
        level1.add(0,new LabelValueBean(allCategories, allCategories));
        level2.add(0,new LabelValueBean(allCategories, allCategories));
        level3.add(0,new LabelValueBean(allCategories, allCategories));
        level4.add(0,new LabelValueBean(allCategories, allCategories));
        
        reportingDto.setCategory1(level1);
        reportingDto.setCategory2(level2);
        reportingDto.setCategory3(level3);
        reportingDto.setCategory4(level4);
	}
	
	private static ActionErrors validateDateRangeCriteria(HttpServletRequest request, ReportingDto reportingDto) {
    	ActionErrors errors = new ActionErrors();
    	if (reportingDto == null) {
        	log.error("Missing filter search criteria encountered.");
            String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.unableToRetrieveReport", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    	}
    	else {
   
    		// Validate date fields.
	        if(reportingDto.getDateRange().trim().equals(Constants.DATE_RANGE_CUSTOM_RANGE)) {
	        	String fromDate = reportingDto.getFrom();
	        	String toDate = reportingDto.getTo();
	        	Date from = null;
	        	Date to = null;
	        	
	        	String userLocaleDateFormat = ClwI18nUtil.getUIDateFormat(request);
	        	if(fromDate==null || fromDate.equals(StringUtils.EMPTY) || fromDate.toLowerCase().equals(userLocaleDateFormat.toLowerCase())) {
	        		reportingDto.setFrom(userLocaleDateFormat);
	        		log.error("Missing begin date for custom date range.");
	                String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.noBeginDate", null);
	                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	        	} else {
	        		try {
	        			//STJ-4662
	        			from = ClwI18nUtil.parseDateInp(request, fromDate);
					} catch (ParseException e) {
						log.error("Invalid begin date entered.");
		                String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.invalidBeginDate", null);
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
					} catch (Exception e) {
						log.error("Invalid begin date entered.");
						String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.invalidBeginDate", null);
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
					}
	        	}
	        	if(toDate==null  || toDate.equals(StringUtils.EMPTY) || toDate.toLowerCase().equals(userLocaleDateFormat.toLowerCase())) {
	        		reportingDto.setTo(userLocaleDateFormat);
	        		log.error("Missing end date for custom date range.");
	                String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.noEndDate", null);
	                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	        	} else {
	        		try {
	        			//STJ-4662
	        			to = ClwI18nUtil.parseDateInp(request, toDate);
					} catch (ParseException e) {
						log.error("Invalid end date entered.");
		                String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.invalidEndDate", null);
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
					} catch (Exception e) {
						log.error("Invalid end date entered.");
						String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.invalidEndDate", null);
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
					}
	        	}
	        	if(from!=null && to!=null && (from.compareTo(to)>0)) {
	        		log.error("Invalid date range, end date must be before or equals to the beginning date.");
	                String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.invalidDateRange", null);
	                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	        	}
	        	if(from!=null && to!=null && (from.compareTo(new Date())>0)) {
	        		log.error("Invalid date range, future date should not be selected as begin date.");
	                String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.invalidBeginDateRange", null);
	                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	        	}
	        	
	        }
        	
    	}
    	return errors;
	}
	
	public static ActionErrors generateReport(HttpServletRequest request, HttpServletResponse response,
    		ReportingForm reportingForm,ReportRequest pRepRequest){
		
		ActionErrors errors = new ActionErrors();
		ReportingDto reportingDto = reportingForm.getOrdersGlanceReportingInfo();
		//validate date range
		errors = validateDateRangeCriteria(request,reportingDto);
		if(errors.size()>0) {
			return errors;
		}
		
		try{
			HttpSession session = request.getSession();
			SiteData currentLocation = ShopTool.getCurrentSite(request);
			APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
			Site siteEjb = factory.getSiteAPI();
			CustAcctMgtReportForm sForm = reportingForm.getCustomerReportingForm();
			//set report controls
			
			GenericReportControlViewVector controls = new GenericReportControlViewVector();
			GenericReportControlView cv = new GenericReportControlView();
			cv.setName("CUSTOMER");
			controls.add(cv);
			
			//Date range
			errors = setDateRange(request,reportingDto);
	        if(errors.size()>0) {
				return errors;
			}
	        String orderFromDate = reportingDto.getFrom();
	        String orderToDate = reportingDto.getTo();
	        
	        //String orderFromDate = reportingDto.getFrom();     
	        cv = new GenericReportControlView();
	        cv.setName("BEG_DATE");
	        cv.setValue(orderFromDate);
	        controls.add(cv);
	        
	        //String orderToDate = reportingDto.getTo();        
	        cv = new GenericReportControlView();
	        cv.setName("END_DATE");
	        cv.setValue(orderToDate);
	        controls.add(cv);
			
	      //If Custom Date range is not set then Initialize From & To date values with 
	        //Logged in User's locale date format.
	        if(!reportingDto.getDateRange().trim().equals(Constants.DATE_RANGE_CUSTOM_RANGE)) {
	        	String userLocaleDateFormat = ClwI18nUtil.getUIDateFormat(request);
	        	reportingDto.setFrom(userLocaleDateFormat);
	        	reportingDto.setTo(userLocaleDateFormat);
	        }
			//sites
	        IdVector sites = new IdVector();
	        if(reportingDto.getLocationSelected().trim().startsWith(Constants.ORDERS_CURRENT_LOCATION)) {
	        	Integer siteId = new Integer(currentLocation.getSiteId());
	        	sites.add(siteId);
	        }else if (reportingDto.getLocationSelected().trim().startsWith(Constants.ORDERS_SPECIFIED_LOCATIONS)) {
	            int siteId[]= reportingDto.getSiteId();
	            for (int i = 0; siteId != null && i < siteId.length; i++) {
	                sites.add(siteId[i]);
	            }
	        }

	        if(sites!=null && sites.size()>0){
		        cv = new GenericReportControlView();
				cv.setName("LOCATE_SITE_MULTI_OPT");
				QueryRequest req = new QueryRequest();
				req.filterBySiteIdList(sites);
				SiteViewVector siteVV = siteEjb.getSiteCollection(req);
				session.setAttribute("Report.parameter.sites", siteVV);
				controls.add(cv);
	        } else {
	            // STJ-6099 Get empty excel result when no specified location selected
                if (reportingDto.getLocationSelected().trim().startsWith(Constants.ORDERS_SPECIFIED_LOCATIONS)) {
                    cv = new GenericReportControlView();
                    cv.setName("LOCATE_SITE_MULTI_OPT");
                    SiteViewVector siteVV = new SiteViewVector();
                    siteVV.add(SiteView.createValue());
                    session.setAttribute("Report.parameter.sites", siteVV);
                    controls.add(cv);
                }
            }


			//mfg

	        String allMfgs = ClwI18nUtil.getMessage(request, "reporting.filter.allManufacturers", null);
	        if(Utility.isSet(reportingDto.getMfgSelected()) && 
	        		!reportingDto.getMfgSelected().equals(allMfgs)){
	        	cv = new GenericReportControlView();
	        	cv.setName("MANUFACTURER_OPT");
	        	cv.setValue(reportingDto.getMfgSelected());
	        	controls.add(cv);
	        }
	        sForm.setReportControls(controls);
			//categories
	        setCategoriesControl(request, reportingForm,null);	  
			errors = CustAcctMgtReportLogic.getCustomerReport(request, response, sForm, pRepRequest);

			reportingForm.setCustomerReportingForm(sForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return errors;
	}

	/**
	 * Gets category hierarchy from store catalog, and filters by categories present in account catalogs for user's accounts.
	 * @param request
	 * @param user
	 * @param reportingInfo
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<CategoryStructureDto> getCategoryStructures(HttpServletRequest request,CleanwiseUser user,
			ReportingDto reportingInfo) throws Exception{
		ArrayList<CategoryStructureDto> categoryStructure = new ArrayList<CategoryStructureDto>();
		
		/*SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		if(sessionDataUtil.getReportingDto()!=null && (sessionDataUtil.getReportingDto().getCategoryTree()!=null
				&& sessionDataUtil.getReportingDto().getCategoryTree().size()>0)){
			return sessionDataUtil.getReportingDto().getCategoryTree();
		}
		*/
		try{
			HttpSession session = request.getSession();
	        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
	        CatalogInformation catInfoEjb = factory.getCatalogInformationAPI();
	        
	        StoreData storeD = (StoreData)user.getUserStore();
	        int storeId = storeD.getStoreId();
	        int storeCatalogId = catInfoEjb.getStoreCatalogId(storeId);        
	        
	        ArrayList catsLevel1 = catInfoEjb.getLevel1Categories(storeCatalogId);
	        Iterator it = catsLevel1.iterator();
	        while(it.hasNext()){  	
	        	//int l1CatId = ((Integer)it.next()).intValue();
	        	String l1Cat = (String)it.next();
	        	//int l1CatId = Integer.parseInt(l1Cat.split("-")[0]);
	        	//String level1Name = l1Cat.split("-")[1];
	        	String l1CatIdAndName[] = l1Cat.split("-",2);
	        	int l1CatId = Integer.parseInt(l1CatIdAndName[0]);
	        	String level1Name = l1CatIdAndName[1];
	        	
	        	ArrayList catsLevel2 = catInfoEjb.getNextLevelCategories(l1CatId,storeCatalogId);
	        	
	        	if(catsLevel2!=null && catsLevel2.size()>0){
		        	Iterator it2 = catsLevel2.iterator();
		        	while(it2.hasNext()){
		        		//int l2CatId = ((Integer)it2.next()).intValue();
		        		String l2Cat = (String)it2.next();
		        		String l2CatIdAndName[] = l2Cat.split("-",2);
			        	int l2CatId = Integer.parseInt(l2CatIdAndName[0]);
			        	String level2Name = l2CatIdAndName[1];
		        		
		        		ArrayList catsLevel3 = catInfoEjb.getNextLevelCategories(l2CatId,storeCatalogId);
		        		
		        		if(catsLevel3!=null && catsLevel3.size()>0){
			        		Iterator it3 = catsLevel3.iterator();
			        		while(it3.hasNext()){
			        			//int l3CatId = ((Integer)it3.next()).intValue();
			        			String l3Cat = (String)it3.next();
			    	        	//int l3CatId = Integer.parseInt(l3Cat.split("-")[0]);
			    	        	//String level3Name = l3Cat.split("-")[1];
			        			String l3CatIdAndName[] = l3Cat.split("-",2);
			    	        	int l3CatId = Integer.parseInt(l3CatIdAndName[0]);
			    	        	String level3Name = l3CatIdAndName[1];
			        			
			        			ArrayList catsLevel4 = catInfoEjb.getNextLevelCategories(l3CatId,storeCatalogId);
			        			
			        			if(catsLevel4 != null && catsLevel4.size() > 0){
				        			Iterator it4 = catsLevel4.iterator();
				        			while(it4.hasNext()){
				        				//int l4CatId = ((Integer)it4.next()).intValue();
				        				String l4Cat = (String)it4.next();
				        	        	//int l4CatId = Integer.parseInt(l4Cat.split("-")[0]);
				        	        	//String level4Name = l4Cat.split("-")[1];
				        				String l4CatIdAndName[] = l4Cat.split("-",2);
					    	        	int l4CatId = Integer.parseInt(l4CatIdAndName[0]);
					    	        	String level4Name = l4CatIdAndName[1];
				        				
				        				CategoryStructureDto catDto = new CategoryStructureDto();
					        			catDto.setLevel4(l4CatId);
					        			catDto.setLevel3(l3CatId);
					        			catDto.setLevel2(l2CatId);
					        			catDto.setLevel1(l1CatId);
					        			catDto.setLowestLevel(l4CatId);
					        			
					        			catDto.setLevel1Name(level1Name);
					        			catDto.setLevel2Name(level2Name);
					        			catDto.setLevel3Name(level3Name);
					        			catDto.setLevel4Name(level4Name);
					        			catDto.setLowestLevelName(level4Name);
					        			
					        			categoryStructure.add(catDto);
				        			}
			        			}else{
			        				CategoryStructureDto catDto = new CategoryStructureDto();
				        			catDto.setLevel3(l3CatId);
				        			catDto.setLevel2(l2CatId);
				        			catDto.setLevel1(l1CatId);
				        			catDto.setLowestLevel(l3CatId);
				        			
				        			catDto.setLevel1Name(level1Name);
				        			catDto.setLevel2Name(level2Name);
				        			catDto.setLevel3Name(level3Name);
				        			catDto.setLowestLevelName(level3Name);
				        			
				        			categoryStructure.add(catDto);
			        			}
			        			
			        		}
		        		}else{
		        			CategoryStructureDto catDto = new CategoryStructureDto();
		        			catDto.setLevel2(l2CatId);
		        			catDto.setLevel1(l1CatId);
		        			catDto.setLowestLevel(l2CatId);
		        			
		        			catDto.setLevel1Name(level1Name);
		        			catDto.setLevel2Name(level2Name);
		        			catDto.setLowestLevelName(level2Name);
		        			
		        			categoryStructure.add(catDto);
		        		}
		        	}
	        	}else{
	        		CategoryStructureDto catDto = new CategoryStructureDto();
        			catDto.setLevel1(l1CatId);
        			catDto.setLowestLevel(l1CatId);
        			
        			catDto.setLevel1Name(level1Name);
        			catDto.setLowestLevelName(level1Name);
        			
        			categoryStructure.add(catDto);
	        	}
	        	
	        }
	        
	        //Filter categories which do not belong to account catalogs
	        
	        List acctCatIds = catInfoEjb.getAcctCatalogCategoriesForUser(user.getUserId());
	        
	        Iterator iter = categoryStructure.iterator();
	        while(iter.hasNext()){
	        	CategoryStructureDto catStruct = (CategoryStructureDto)iter.next();
	        	
	        	int l1 = catStruct.getLevel1();
	        	if(!acctCatIds.contains(l1)){
	        		iter.remove();
	        	}
	        	int l2 = catStruct.getLevel2();
	        	int l3 = catStruct.getLevel3();
	        	int l4 = catStruct.getLevel4();
	        	
	        	if(!acctCatIds.contains(l4)){
	        		catStruct.setLevel4(0);
	        		catStruct.setLevel4Name(null);
	        	}
	        	if(!acctCatIds.contains(l3)){
	        		catStruct.setLevel4(0);
	        		catStruct.setLevel4Name(null);
	        		catStruct.setLevel3(0);
	        		catStruct.setLevel3Name(null);
	        	}
	        	if(!acctCatIds.contains(l2)){
	        		catStruct.setLevel4(0);
	        		catStruct.setLevel4Name(null);
	        		catStruct.setLevel3(0);
	        		catStruct.setLevel3Name(null);
	        		catStruct.setLevel2(0);
	        		catStruct.setLevel2Name(null);
	        	}
	        	    	
	        }
	        
		}catch (Exception e){
			e.printStackTrace();
        	throw new Exception(e.getMessage());
		}
		
		return categoryStructure;
	}
	
	public static ActionErrors setDateRange(HttpServletRequest request, ReportingDto reportingDto){
		ActionErrors errors = new ActionErrors();
		
		SiteData currentLocation = ShopTool.getCurrentSite(request);
        Date toDate = new Date();
        Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate);
		Date fromDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(ClwI18nUtil.getDatePattern(request));
        
        if(reportingDto.getDateRange().trim().equals(Constants.REPORTING_FILTER_LAST_PERIOD)) {
        	int currentPeriod = currentLocation.getCurrentBudgetPeriod();
    		if (currentPeriod > 1) {
    			int lastPeriod = currentPeriod - 1;
	    		FiscalCalendarInfo.BudgetPeriodInfo budgetPeriod = (BudgetPeriodInfo) currentLocation.getBudgetPeriods().get(new Integer(lastPeriod));
	    		fromDate = budgetPeriod.getStartDate();
	    		toDate = budgetPeriod.getEndDate();
    		}
    		//if we're in the first period of the current fiscal year, we need to get the last period
    		//of the previous fiscal year.
    		else {
    			try {
    				Account accountBean = APIAccess.getAPIAccess().getAccountAPI();
					FiscalCalenderData currentFiscalCalendar = accountBean.getCurrentFiscalCalender(currentLocation.getAccountId());
					int currentFiscalYear = currentFiscalCalendar.getFiscalYear();
					int previousFiscalYear = currentFiscalYear - 1;
					FiscalCalenderView fcv = accountBean.getFiscalCalenderVForYear(currentLocation.getAccountId(), previousFiscalYear);
					//if there is no fiscal calendar for the previous year, return an error
					if (fcv == null) {
	    	            String errorMess = ClwI18nUtil.getMessage(request, "previousOrder.search.error.noLastPeriod", null);
	    	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	    	            return errors;						
					}
					FiscalCalendarInfo fci = new FiscalCalendarInfo(fcv);
					FiscalCalendarInfo.BudgetPeriodInfo budgetPeriod = fci.getBudgetPeriod(fci.getNumberOfBudgetPeriods());
		    		fromDate = budgetPeriod.getStartDate();
		    		toDate = budgetPeriod.getEndDate();
    			}
    			catch (Exception e) {
    				e.printStackTrace();
    	        	String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.unableToRetrieveReport", null);
    	        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));  
    	        	return errors;
    			}
    		}
        }
        else if(reportingDto.getDateRange().trim().equals(Constants.REPORTING_FILTER_THIS_PERIOD)) {
        	int currentPeriod = currentLocation.getCurrentBudgetPeriod();
    		FiscalCalendarInfo.BudgetPeriodInfo budgetPeriod = (BudgetPeriodInfo) currentLocation.getBudgetPeriods().get(new Integer(currentPeriod));
    		fromDate = budgetPeriod.getStartDate();
    		toDate = budgetPeriod.getEndDate();
        }
        else {
        	if (reportingDto.getDateRange().trim().equals(Constants.DATE_RANGE_30_DAYS)) {     		
        		calendar.add(Calendar.DATE, -30);
        		fromDate = new Date(calendar.getTime().getTime());
	        }
	        else if (reportingDto.getDateRange().trim().equals(Constants.DATE_RANGE_60_DAYS)) {
	        	calendar.add(Calendar.DATE, -60);
        		fromDate = new Date(calendar.getTime().getTime());
	        }
	        else if (reportingDto.getDateRange().trim().equals(Constants.DATE_RANGE_90_DAYS)) {
	        	calendar.add(Calendar.DATE, -90);
        		fromDate = new Date(calendar.getTime().getTime());
	        }
	        else if (reportingDto.getDateRange().trim().equals(Constants.DATE_RANGE_CUSTOM_RANGE)) {
	        	
	        	try {
					fromDate = sdf.parse(reportingDto.getFrom());
					toDate = sdf.parse(reportingDto.getTo());
				} catch (ParseException e) {
					e.printStackTrace();
    	        	String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.unableToRetrieveReport", null);
    	        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));  
    	        	return errors;
				}
	        	
	        }
        }
        try{
	        //String orderFromDate = Utility.convertDateToDBString(fromDate, false);
	        //String orderToDate = Utility.convertDateToDBString(toDate, false);
	        
        	String orderFromDate = sdf.format(fromDate);
        	String orderToDate = sdf.format(toDate);
        	
	        reportingDto.setFrom(orderFromDate);
	        reportingDto.setTo(orderToDate);

		} catch (Exception e) {
			e.printStackTrace();
        	String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.unableToRetrieveReport", null);
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));  
        	return errors;
		}
        
		return errors;
	}
	
	public static ActionErrors showOrdersReport(HttpServletRequest request, ReportingDto reportingDto){
		ActionErrors errors = new ActionErrors();	
		//validate date range
		errors = validateDateRangeCriteria(request,reportingDto);
		if(errors.size()>0) {
			return errors;
		}
		try{
			HttpSession session = request.getSession();
	        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
	        CatalogInformation catInfoEjb = factory.getCatalogInformationAPI();
	        SiteData currentLocation = ShopTool.getCurrentSite(request);
	        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

	        errors = setDateRange(request,reportingDto);
	        if(errors.size()>0) {
				return errors;
			}
	        //String orderFromDate = reportingDto.getFrom();
	        //String orderToDate = reportingDto.getTo();
	        SimpleDateFormat sdf = new SimpleDateFormat(ClwI18nUtil.getDatePattern(request));
	        Date fromd = sdf.parse(reportingDto.getFrom());
	        Date tod = sdf.parse(reportingDto.getTo());
	        String orderFromDate = Utility.convertDateToDBString(fromd, false);
	        String orderToDate = Utility.convertDateToDBString(tod, false);
	        
	        //If Custom Date range is not set then Initialize From & To date values with 
	        //Logged in User's locale date format.
	        if(!reportingDto.getDateRange().trim().equals(Constants.DATE_RANGE_CUSTOM_RANGE)) {
	        	String userLocaleDateFormat = ClwI18nUtil.getUIDateFormat(request);
	        	reportingDto.setFrom(userLocaleDateFormat);
	        	reportingDto.setTo(userLocaleDateFormat);
	        }
	        
	        String cat1Selected = reportingDto.getCat1Selected();
	        String cat2Selected = reportingDto.getCat2Selected();
	        String cat3Selected = reportingDto.getCat3Selected();
	        String cat4Selected = reportingDto.getCat4Selected();
	        String chartCatSelected = reportingDto.getChartCatSelected();
	        
	        String allCategories = ClwI18nUtil.getMessage(request, "reporting.filter.allCategories", null);
	        if(cat1Selected.equals(allCategories)){
	        	cat2Selected = allCategories;
	        	cat3Selected = allCategories;
	        	cat4Selected = allCategories;
	        }

			IdVector sites = new IdVector();
	        IdVector mfgs = new IdVector();
	        if(reportingDto.getLocationSelected().trim().startsWith(Constants.ORDERS_CURRENT_LOCATION)) {
	        	Integer siteId = new Integer(currentLocation.getSiteId());
	        	sites.add(siteId);
	        } else if (reportingDto.getLocationSelected().trim().startsWith(Constants.ORDERS_SPECIFIED_LOCATIONS)) {
	            int siteId[]= reportingDto.getSiteId();
	            for (int i = 0; siteId != null && i < siteId.length; i++) {
	                sites.add(siteId[i]);
	            }
	        }
	        
	        //STJ-5135
	        if(!reportingDto.getLocationSelected().trim().startsWith(Constants.ORDERS_ALL_LOCATIONS)
	        		&& sites != null && sites.size() == 0) {
	        	return errors;
	        }
	        
	        
	        String allMfgs = ClwI18nUtil.getMessage(request, "reporting.filter.allManufacturers", null);
	        if(Utility.isSet(reportingDto.getMfgSelected()) && 
	        		!reportingDto.getMfgSelected().equals(allMfgs)){
	        	mfgs.add(new Integer(reportingDto.getMfgSelected()));
	        }
	        
	        ArrayList<String> pOrderStatus = new ArrayList<String>();
			pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.ORDERED);
			pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);
			pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
			
	        //Verify order locales to check that all orders have same currency
	        String multipleLocales = catInfoEjb.verifyOrderLocales(appUser.getUserId(), orderFromDate, orderToDate, pOrderStatus, sites);
	        if(multipleLocales != null){
	        	String errorMess = ClwI18nUtil.getMessage(request, multipleLocales, null);
	        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));    
	        	return errors;
	        }
	        
	        ArrayList<CategoryStructureDto> categoryStructure = getCategoryStructures(request, appUser,reportingDto);
        
	        HashMap categoryItemsTotal = catInfoEjb.getCategoryItemsTotal(appUser.getUserId(), 
	        		orderFromDate, orderToDate, pOrderStatus, sites, mfgs);
	        HashMap updatedCategoryItemsTotal = new HashMap();
	        	       
	        Iterator it = categoryItemsTotal.keySet().iterator();
	        while(it.hasNext()){
	        	
	        	Integer catId = (Integer)it.next();
	        	
	        	ArrayList itemTotals = (ArrayList)categoryItemsTotal.get(catId);
	        	ArrayList newItemTotals = new ArrayList();
	        	
	        	for(int i=0; i<itemTotals.size(); i++){
	        		HashMap iMap = (HashMap)itemTotals.get(i);
	        		HashMap newIMap = new HashMap();
	        		
	        		Iterator it2 = iMap.keySet().iterator();
	        		while(it2.hasNext()){
	        			CategoryItemMfgKey key = (CategoryItemMfgKey)it2.next();
	        			BigDecimal val = (BigDecimal)iMap.get(key);
	        			
	        			String itemId = (new Integer(key.getItemId())).toString();
	        			String itemName = key.getItemName();
	        			String mfgId = (new Integer(key.getMfgId())).toString();
	        			String mfgName = key.getMfgName();
	        			
	        			newIMap.put(itemId+"-"+itemName, val);
	        			newItemTotals.add(newIMap);
	        			
	        			/* This logic was used to get manuf. list from orders placed. 
	        			 * It has since been changed to get the manuf. list from the account catalog.
	        			 * Leaving the code here for possible future use.
	        			 if(!mfgMap.containsKey(mfgName)){
	        				mfgMap.put(mfgName, mfgId);
	        			}*/
	        			
	        		}
	        	}
	        	
	        	updatedCategoryItemsTotal.put(catId, newItemTotals);
	        }
	        
	        //Manufacturers from account catalogs
	        HashMap mfgMap = catInfoEjb.getMfgsFromAccountCatalogs(sites, appUser.getUserId());
	        
	        List<LabelValueBean> mfgList = sortMfgs(mfgMap, appUser.getPrefLocale());
	        reportingDto.getMfgList().addAll(mfgList);
	        
	        //Filtering out categories for which orders were placed has been removed as part of STJ-5099/STJ-5100
	        /*ArrayList<CategoryStructureDto> updatedCategoryStructure = new ArrayList<CategoryStructureDto>();
	        for(int i=0; i<categoryStructure.size(); i++){
	        	CategoryStructureDto cDto = categoryStructure.get(i);
	        	int lowestLevel = cDto.getLowestLevel();
	        	
	        	if(updatedCategoryItemsTotal.containsKey(lowestLevel)){
	        		updatedCategoryStructure.add(cDto);
	        	}
	        }*/
	        
	        reportingDto.setCategoryTree(categoryStructure);
			
	        int catSelected = 0;
	        int level = 0;
	        
	        if(Utility.isSet(chartCatSelected)){
	        	//category was picked by clicking on chart
	        	
	        	catSelected = new Integer(chartCatSelected.split("-",2)[0]).intValue();
	        	
	        	for(int i=0; i<categoryStructure.size(); i++){
					CategoryStructureDto cDto = categoryStructure.get(i);
					if(cDto.getLevel1()==catSelected){
						level = 1;
						reportingDto.setCat1Selected(Integer.toString(catSelected));
						reportingDto.setCat2Selected(allCategories);
						reportingDto.setCat3Selected(allCategories);
						reportingDto.setCat4Selected(allCategories);					
						break;
					}else if(cDto.getLevel2()==catSelected){
						level = 2;
						reportingDto.setCat1Selected(Integer.toString(cDto.getLevel1()));
						reportingDto.setCat2Selected(Integer.toString(catSelected));
						reportingDto.setCat3Selected(allCategories);
						reportingDto.setCat4Selected(allCategories);
						break;
					}else if(cDto.getLevel3()==catSelected){
						level = 3;
						reportingDto.setCat1Selected(Integer.toString(cDto.getLevel1()));
						reportingDto.setCat2Selected(Integer.toString(cDto.getLevel2()));
						reportingDto.setCat3Selected(Integer.toString(catSelected));
						reportingDto.setCat4Selected(allCategories);
						break;
					}else if(cDto.getLevel4()==catSelected){
						level = 4;
						reportingDto.setCat1Selected(Integer.toString(cDto.getLevel1()));
						reportingDto.setCat2Selected(Integer.toString(cDto.getLevel2()));
						reportingDto.setCat3Selected(Integer.toString(cDto.getLevel3()));
						reportingDto.setCat4Selected(Integer.toString(catSelected));
						break;
					}
				}	       
	        	
	        	if(level == 0){ // item level was selected, show parent category values for this item
	        		if(Utility.isSet(cat4Selected) && !cat4Selected.equals(allCategories)){
	        			catSelected = new Integer(cat4Selected).intValue();
	        			level=4;
	        		}else if(Utility.isSet(cat3Selected) && !cat3Selected.equals(allCategories)){
	        			catSelected = new Integer(cat3Selected).intValue();
	        			level=3;
	        		}else if(Utility.isSet(cat2Selected) && !cat2Selected.equals(allCategories)){
	        			catSelected = new Integer(cat2Selected).intValue();
	        			level=2;
	        		}else if(Utility.isSet(cat1Selected) && !cat1Selected.equals(allCategories)){
	        			catSelected = new Integer(cat1Selected).intValue();
	        			level=1;
	        		}
	        	}
	        	
	        }else{
	        	//category was picked from left hand filter
	        
		        if(Utility.isSet(cat1Selected) && !cat1Selected.equals(allCategories)){	
			        catSelected = new Integer(cat1Selected.split("-",2)[0]).intValue();
			        level =1;
			        
			        if(Utility.isSet(cat2Selected) && !cat2Selected.equals(allCategories)){	
				        catSelected = new Integer(cat2Selected.split("-",2)[0]).intValue();
				        level =2;
				        
				        if(Utility.isSet(cat3Selected) && !cat3Selected.equals(allCategories)){	
					        catSelected = new Integer(cat3Selected.split("-",2)[0]).intValue();
					        level =3;
					        
					        if(Utility.isSet(cat4Selected) && !cat4Selected.equals(allCategories)){	
						        catSelected = new Integer(cat4Selected.split("-",2)[0]).intValue();
						        level =4;
					        }
				        }
			        }			        
		        }		       
	        }
	        
	        HashMap<String, BigDecimal> finalMap = getFinalMap(categoryStructure, updatedCategoryItemsTotal, catSelected, level);
	        OrdersAtAGlanceDto dto = new OrdersAtAGlanceDto();
	        finalMap = getSortedCategoryAmountMap(finalMap);
	        dto.setCategoryAmountMap(finalMap);
        	dto.setShowCurrency(false);
        	reportingDto.setOrdersReportChart(dto);
	        
		}catch (Exception e){
			e.printStackTrace();
        	String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.unableToRetrieveReport", null);
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));    	
		}
		
		return errors;
	}
	
	public static ReportingDto populateCategoryFilters(HttpServletRequest request,ReportingDto reportingInfo,
			boolean showBreadCrumb){

        List<LabelValueBean> level1 = new ArrayList<LabelValueBean>();
        List<LabelValueBean> level2 = new ArrayList<LabelValueBean>();
        List<LabelValueBean> level3 = new ArrayList<LabelValueBean>();
        List<LabelValueBean> level4 = new ArrayList<LabelValueBean>();
        
        String allCategories = ClwI18nUtil.getMessage(request, "reporting.filter.allCategories", null);
        level1.add(0,new LabelValueBean(allCategories, allCategories));
        level2.add(0,new LabelValueBean(allCategories, allCategories));
        level3.add(0,new LabelValueBean(allCategories, allCategories));
        level4.add(0,new LabelValueBean(allCategories, allCategories));
        
        ArrayList<CategoryStructureDto> catStructList = reportingInfo.getCategoryTree();
        if(Utility.isSet(catStructList)) {
        	Iterator it = catStructList.iterator();
            
            while(it.hasNext()){
            	CategoryStructureDto cDto = (CategoryStructureDto)it.next();
            	
            	LabelValueBean l1 = new LabelValueBean(cDto.getLevel1Name(), Integer.toString(cDto.getLevel1()));
            	
            	if(Utility.isSet(reportingInfo.getCat1Selected()) && !reportingInfo.getCat1Selected().equals(allCategories)){

            		if(level1!=null && !level1.contains(l1)){
                		level1.add(l1);
                	}
            		
            		if(Integer.parseInt(reportingInfo.getCat1Selected())==cDto.getLevel1()){
            			
            			if(cDto.getLevel2()>0){
    	        			LabelValueBean l2 = new LabelValueBean(cDto.getLevel2Name(), Integer.toString(cDto.getLevel2()));
    						if(level2!=null && !level2.contains(l2)){
    							level2.add(l2);
    						}
            			}
            			
            			if(Utility.isSet(reportingInfo.getCat2Selected()) && !reportingInfo.getCat2Selected().equals(allCategories)){
                    		
            				if(Integer.parseInt(reportingInfo.getCat2Selected())==cDto.getLevel2()){
            					
            					if(cDto.getLevel3()>0){
    	        					LabelValueBean l3 = new LabelValueBean(cDto.getLevel3Name(), Integer.toString(cDto.getLevel3()));
    	        					if(level3!=null && !level3.contains(l3)){
    	        						level3.add(l3);
    	        					}
            					}
            					
            					if(Utility.isSet(reportingInfo.getCat3Selected()) && !reportingInfo.getCat3Selected().equals(allCategories)){
                            		
            						if(Integer.parseInt(reportingInfo.getCat3Selected())==cDto.getLevel3()){
            							
            							if(cDto.getLevel4()>0){
    	        							LabelValueBean l4 = new LabelValueBean(cDto.getLevel4Name(), Integer.toString(cDto.getLevel4()));
    	        							if(level4!=null && !level4.contains(l4)){
    	        								level4.add(l4);
    	        							}
            							}
            						}
            					
            					}
            				}
            			}
            		}
            		
            	}else{
            		//All categories
            		if(reportingInfo.getCat1Selected().equals(allCategories)){
            			reportingInfo.setCat2Selected(allCategories);
            			reportingInfo.setCat3Selected(allCategories);
            			reportingInfo.setCat4Selected(allCategories);
            			reportingInfo.setChartCatSelected(null);
            		}
            		if(level1!=null && !level1.contains(l1)){
                		level1.add(l1);
                	}
            	}
            }
        }
        
        reportingInfo.setCategory1(level1);
        reportingInfo.setCategory2(level2);
        reportingInfo.setCategory3(level3);
        reportingInfo.setCategory4(level4);
        
        //Set breadcrumb 
        // STJ - 4580
        if(showBreadCrumb){
        	StringBuilder reportingOrdersLink = new StringBuilder(50);
            reportingOrdersLink.append("reporting.do?");
            reportingOrdersLink.append(Constants.PARAMETER_OPERATION);
            reportingOrdersLink.append("=");
            reportingOrdersLink.append(Constants.PARAMETER_OPERATION_VALUE_FILTER_ORDERS_REPORT);
            
            reportingOrdersLink.append("&");
            reportingOrdersLink.append("ordersGlanceReportingInfo.locationSelected");
            reportingOrdersLink.append("=");
            reportingOrdersLink.append(reportingInfo.getLocationSelected());
            
            reportingOrdersLink.append("&");
            reportingOrdersLink.append("ordersGlanceReportingInfo.dateRange");
            reportingOrdersLink.append("=");
            reportingOrdersLink.append(reportingInfo.getDateRange());
            
            reportingOrdersLink.append("&");
            reportingOrdersLink.append("ordersGlanceReportingInfo.from");
            reportingOrdersLink.append("=");
            reportingOrdersLink.append(reportingInfo.getFrom());
            
            reportingOrdersLink.append("&");
            reportingOrdersLink.append("ordersGlanceReportingInfo.to");
            reportingOrdersLink.append("=");
            reportingOrdersLink.append(reportingInfo.getTo());
            
            reportingOrdersLink.append("&");
            reportingOrdersLink.append("ordersGlanceReportingInfo.mfgSelected");
            reportingOrdersLink.append("=");
            reportingOrdersLink.append(reportingInfo.getMfgSelected());
            
            String breadcrumb = "<a href=\""+reportingOrdersLink.toString()+"\">"
        	+ ClwI18nUtil.getMessage(request, "reporting.label.allProducts", null) + "</a>";
        

            reportingOrdersLink.append("&");
            reportingOrdersLink.append("ordersGlanceReportingInfo.cat1Selected");
            reportingOrdersLink.append("=");
            reportingOrdersLink.append(reportingInfo.getCat1Selected());
            reportingOrdersLink.append("&");
            reportingOrdersLink.append("ordersGlanceReportingInfo.cat2Selected");
            reportingOrdersLink.append("=");
            reportingOrdersLink.append(reportingInfo.getCat2Selected());
            reportingOrdersLink.append("&");
            reportingOrdersLink.append("ordersGlanceReportingInfo.cat3Selected");
            reportingOrdersLink.append("=");
            reportingOrdersLink.append(reportingInfo.getCat3Selected());
            reportingOrdersLink.append("&");
            reportingOrdersLink.append("ordersGlanceReportingInfo.cat4Selected");
            reportingOrdersLink.append("=");
            reportingOrdersLink.append(reportingInfo.getCat4Selected());
            
            reportingOrdersLink.append("&");
            reportingOrdersLink.append("ordersGlanceReportingInfo.chartCatSelected");
            reportingOrdersLink.append("=");
    		
    		if(Utility.isSet(reportingInfo.getCat1Selected()) && !reportingInfo.getCat1Selected().equals(allCategories)){
    		
    			if(reportingInfo.getCategory1()!=null){
    			    for(int i=0; i< reportingInfo.getCategory1().size(); i++){
    			    	LabelValueBean lvb = (LabelValueBean) reportingInfo.getCategory1().get(i);
    			    	if(lvb.getValue().equals(reportingInfo.getCat1Selected())){
    			    		String catKey_catName = lvb.getValue()+"-"+lvb.getLabel();
    			    		breadcrumb += " > <a href=\""+reportingOrdersLink.toString()+""+catKey_catName+"\">" + lvb.getLabel()+"</a>";
    			    		break;
    			    	}
    			    }
    			}
    		}
    		if(Utility.isSet(reportingInfo.getCat2Selected()) && !reportingInfo.getCat2Selected().equals(allCategories)){
    			
    			if(reportingInfo.getCategory2()!=null){
    			    for(int i=0; i< reportingInfo.getCategory2().size(); i++){
    			    	LabelValueBean lvb = (LabelValueBean) reportingInfo.getCategory2().get(i);
    			    	if(lvb.getValue().equals(reportingInfo.getCat2Selected())){
    			    		String catKey_catName = lvb.getValue()+"-"+lvb.getLabel();
    			    		breadcrumb += " > <a href=\""+reportingOrdersLink.toString()+""+catKey_catName+"\">" + lvb.getLabel()+"</a>";
    			    		break;
    			    	}
    			    }
    			}
    		}
    		if(Utility.isSet(reportingInfo.getCat3Selected()) && !reportingInfo.getCat3Selected().equals(allCategories)){
    			
    			if(reportingInfo.getCategory3()!=null){
    			    for(int i=0; i< reportingInfo.getCategory3().size(); i++){
    			    	LabelValueBean lvb = (LabelValueBean) reportingInfo.getCategory3().get(i);
    			    	if(lvb.getValue().equals(reportingInfo.getCat3Selected())){
    			    		String catKey_catName = lvb.getValue()+"-"+lvb.getLabel();
    			    		breadcrumb += " > <a href=\""+reportingOrdersLink.toString()+""+catKey_catName+"\">" + lvb.getLabel()+"</a>";
    			    		break;
    			    	}
    			    } 
    			}
    		}
    		if(Utility.isSet(reportingInfo.getCat4Selected()) && !reportingInfo.getCat4Selected().equals(allCategories)){
    			
    			if(reportingInfo.getCategory4()!=null){
    			    for(int i=0; i< reportingInfo.getCategory4().size(); i++){
    			    	LabelValueBean lvb = (LabelValueBean) reportingInfo.getCategory4().get(i);
    			    	if(lvb.getValue().equals(reportingInfo.getCat4Selected())){
    			    		String catKey_catName = lvb.getValue()+"-"+lvb.getLabel();
    			    		breadcrumb += " > <a href=\""+reportingOrdersLink.toString()+""+catKey_catName+"\">" + lvb.getLabel()+"</a>";
    			    		break;
    			    	}
    			    }
    			}
    		}
            
            reportingInfo.setBreadcrumb(breadcrumb);
        }
        
		
        return reportingInfo;
	}
	
	public static HashMap<String, BigDecimal> getFinalMap(ArrayList<CategoryStructureDto> catStructList, HashMap catItems, 
			int catSelected, int level){
		HashMap<String, BigDecimal> finalMap = new HashMap<String, BigDecimal>();
		
		for(int j=0; j<catStructList.size(); j++){
       	 
        	CategoryStructureDto cDto = catStructList.get(j);
        	
        	String key = null;
        	
        	if(level == 0){
        		if(catSelected ==0){ //get top level
            		key = cDto.getLevel1()+"-"+cDto.getLevel1Name();
            	}
        	}  	
        	
        	if(catSelected > 0 && level >0){
	        	if(level ==1 && cDto.getLevel1()==catSelected){

	        		if(cDto.getLevel2()!=0){
	        			//get next level categories as key
	        			key = cDto.getLevel2()+"-"+cDto.getLevel2Name();
	        		}
	        		
	        	}else if(level ==2 && cDto.getLevel2()==catSelected){
	        		
	        		if(cDto.getLevel3()!=0){
	        			key = cDto.getLevel3()+"-"+cDto.getLevel3Name();
	        		}
	        		
	        	}else if(level ==3 && cDto.getLevel3()==catSelected){
	        		
	        		if(cDto.getLevel4()!=0){
	        			key = cDto.getLevel4()+"-"+cDto.getLevel4Name();
	        		}
	        		
	        	}else if(level ==4 && cDto.getLevel4()==catSelected){
	        		//this is the lowest level
	        		
	        	}else{
	        		continue;
	        	}
        	}
        	
        	
        	int lowestLevel = cDto.getLowestLevel();
        	
        	BigDecimal total = new BigDecimal(0.0);
        	
        	if(Utility.isSet(key)){
        		//category level map
        		if(finalMap.containsKey(key)){	        		
            		total = finalMap.get(key);      		
            	}

        		if(catItems.containsKey(lowestLevel)){
        			ArrayList itemTotList = (ArrayList) catItems.get(lowestLevel);
        			
        			for(int i=0; i<itemTotList.size(); i++){
        				HashMap iMap = (HashMap)itemTotList.get(i);
        				
        				Iterator it=iMap.values().iterator();
        				while(it.hasNext()){
        					total = total.add((BigDecimal)it.next());
        				}
        			}		
        		}
        		if(total.compareTo(new BigDecimal(0.0))>0){
        			finalMap.put(key, total);
        		}
        	}else{
        		//item level map
        		//lowest level should be equal to category selected to get here
        		ArrayList itemTotList = (ArrayList) catItems.get(lowestLevel);
        		
        		if(itemTotList!=null && itemTotList.size()>0){
	        		for(int i=0; i<itemTotList.size(); i++){
	        			HashMap iMap = (HashMap) itemTotList.get(i);
	        			
	        			Set entries = iMap.entrySet();     			
	        			Iterator iter = entries.iterator();
	        			while (iter.hasNext()) {
	                    
	                        Map.Entry entry = (Map.Entry) iter.next();
	                        
	                        String itemName = (String)entry.getKey();
	                        BigDecimal amount = (BigDecimal)entry.getValue();
	                        
	                        if(!finalMap.containsKey(itemName)){
	                        	finalMap.put(itemName, amount);
	                        }else{
	                        	BigDecimal totalAmount = (BigDecimal)finalMap.get(itemName).add(amount);
	                        	finalMap.put(itemName, totalAmount);
	                        }
	        			}
	        		}
        		}
        	}
        	
        	
        }
		return finalMap;
	}
	
	public static ActionErrors showBudgetsReport(HttpServletRequest request, ReportingDto reportingDto, SiteData currentLocation, 
			boolean locationBudget, CheckoutForm checkoutForm, String orderFee){
		ActionErrors errors = new ActionErrors();	
		try{
			HttpSession session = request.getSession();
	        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
	        Site siteEjb = factory.getSiteAPI();
	        User userEjb = factory.getUserAPI();
	        Budget budgetEjb = factory.getBudgetAPI();
	        
	        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	        
	        ArrayList<Integer> siteIds = new ArrayList<Integer>();
	        LocationBudgetChartDto budgetChartDto = null;
	        String locationSelected = reportingDto.getLocationSelected();
	        //STJ-5135 Even if user do not have a current location selected,
    		//Orders at-a-glance / Budget at-a-glance - Should be available
	        //Location filter
	        if(locationSelected != null) {
	        	if(locationSelected.equals(Constants.ORDERS_ALL_LOCATIONS)) {
					//all sites associated with the user
			        
		        	SiteDataVector sites = userEjb.getSiteDescCollection(appUser.getUserId(), null);
		        	
		        	if(sites.size() > Constants.MAX_SITES_TO_RETURN){
		        		//Need to add in logic if more than 1000 sites
		        		//Pass user id as parameter and join with clw_user_assoc to get sites
		        		String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.maxSites", null);
		            	errors.add("error", new ActionError("error.simpleGenericError", errorMess));    	
		        	}
		        	
		        	if(errors==null || errors.size()==0){
			        	Iterator it = sites.iterator();
			        	while(it.hasNext()){
			        		SiteData site = (SiteData)it.next();
			        		
			        		siteIds.add(site.getSiteId());
			        	}        	
		        	}
	        	} else if(reportingDto.getLocationSelected().trim().startsWith(Constants.ORDERS_SPECIFIED_LOCATIONS)) {
	                //specified sites
	                //ArrayList<Integer> siteIds = new ArrayList<Integer>();
	                int siteId[] = reportingDto.getSiteId();
	                //STJ-5135
	                if(siteId == null) {
	                	return errors;
	                }
	                for (int i = 0; siteId != null && i < siteId.length; i++) {
	                    siteIds.add(siteId[i]);
	                }
	                if (siteIds.size() > Constants.MAX_SITES_TO_RETURN) {
	                    // Need to add in logic if more than 1000 sites
	                    // Pass user id as parameter and join with clw_user_assoc to
	                    // get sites
	                    String errorMess = ClwI18nUtil.getMessage(request,
	                            "reporting.error.maxSites", null);
	                    errors.add("error", new ActionError(
	                            "error.simpleGenericError", errorMess));
	                }
		        } else if (reportingDto.getLocationSelected().trim().startsWith(Constants.ORDERS_CURRENT_LOCATION)) {
		        		siteIds.add(currentLocation.getSiteId());		        	
		        }
	        } 
	        
	        if(errors != null && !errors.isEmpty()) {
	        	return errors;
	        }
		
	        int siteId = 0;
	        if(siteIds != null && siteIds.size() > 0) {
	        	siteId = siteIds.get(0);
	        }
	        
			//SiteData currentLocation = ShopTool.getCurrentSite(request);
			BudgetSpendView budgetSpendView = siteEjb.getBudgetSpendView(siteId);
			
			String filterPeriod = reportingDto.getFiscalPeriod();
			
			int budgetPeriod = budgetSpendView.getCurrentBudgetPeriod();
			int budgetYear = budgetSpendView.getCurrentBudgetYear();
			boolean isLastPeriodOrYear = false;
			boolean isLastFiscalYear = false;
			
			
			
			//budget period filter
			if(Constants.REPORTING_FILTER_LAST_PERIOD.equalsIgnoreCase(filterPeriod)) {    		
	    		if(budgetPeriod >1){
	    			budgetPeriod = budgetPeriod -1;
	    		}
	    		else{
	    			isLastPeriodOrYear = true; //STJ - 4742 4743 5083
	    		}
	    	}else if(Constants.REPORTING_FILTER_LAST_FISCAL_YEAR.equalsIgnoreCase(filterPeriod)) {
	    		budgetPeriod=0;
	    		budgetYear = budgetSpendView.getCurrentBudgetYear() -1;
	    		isLastFiscalYear = true;
	    	}else if(Constants.REPORTING_FILTER_THIS_FISCAL_YEAR.equalsIgnoreCase(filterPeriod)) {
	    		budgetPeriod=0;
	    	}
			
			if(budgetPeriod > 0){
				reportingDto.setFiscalPeriodSelected(Integer.toString(budgetPeriod));
			}
	        
	        factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
			Site siteBean = factory.getSiteAPI();
		    Budget budgetBean = factory.getBudgetAPI();
		 // STJ - 4790
		    ArrayList<Integer> catalogCCs = null;
		    Account account = APIAccess.getAPIAccess().getAccountAPI();
			AccountData accountData = null;
			BudgetViewVector budgetViewVector = new BudgetViewVector();
			ArrayList<BudgetView> budgets = new ArrayList<BudgetView>();
			ArrayList<Integer> doesContainCC = new ArrayList();
			ArrayList siteIdsList = null;
			ArrayList<Integer> accountHasNoLastPeriodList = new ArrayList<Integer>();
			Set<Integer> siteWithNoDataList = new HashSet<Integer>();
			ArrayList<Integer> noLastYearList = new ArrayList<Integer>();
		    for( int index = 0; siteIds != null && index < siteIds.size(); index++ ) {
		    	accountData = account.getAccountForSite(siteIds.get(index));
		    	//STJ-STJ-5382
		    	AccountDataVector accounts = new AccountDataVector();
				accounts.add(accountData);
				if(!ShopTool.doesSiteSupportsBudgets(request, accounts)) {
					continue;
				}
		    	// STJ - 4742 4743 5083
		    	FiscalCalenderViewVector fscvv = accountData.getFiscalCalenders();
		    	if(!locationBudget && isLastPeriodOrYear || isLastFiscalYear){
		    		if(fscvv != null && fscvv.size() > 0){
			    		FiscalCalenderView fscv = (FiscalCalenderView)fscvv.get(fscvv.size()-1);
			    		if(fscv.getFiscalCalender().getFiscalYear() == 0){
			    			FiscalCalenderDetailDataVector fcddv =  fscv.getFiscalCalenderDetails();
			    			if(!isLastFiscalYear)
			    				budgetPeriod = (fcddv != null && fcddv.size() < 13) ? fcddv.size() : 12;
			    		}
			    		else{
					    	if(fscvv.size() > 0){
					    		int indx = 0;
					    		for(; indx < fscvv.size();indx++){
					    		fscv = (FiscalCalenderView)fscvv.get(indx);
					    		if(fscv.getFiscalCalender().getFiscalYear() == budgetYear){
					    			FiscalCalenderDetailDataVector fcddv =  fscv.getFiscalCalenderDetails();
					    			if(!isLastFiscalYear)
					    				budgetPeriod = (fcddv != null && fcddv.size() < 13) ? fcddv.size() : 12;
					    			break;
					    		}
					    	}
					    		if(indx >= fscvv.size()){
					    			noLastYearList.add(siteIds.get(index));
					    			continue;
					    		}
					    	}
					    	else{
					    		noLastYearList.add(siteIds.get(index));
					    		continue;
					    	}
			    		}
		    		}else{
		    			noLastYearList.add(siteIds.get(index));
			    		continue;
		    		}
		    	}
		    	siteIdsList= new ArrayList();
		    	siteIdsList.add(siteIds.get(index));
		    	catalogCCs = siteBean.getCostCentersForSites(siteIdsList);
		    	
		    	// STJ - 4742 4743 5083
		    	if(!locationBudget && !Constants.REPORTING_FILTER_THIS_FISCAL_YEAR.equalsIgnoreCase(filterPeriod) &&
    					!Constants.REPORTING_FILTER_THIS_PERIOD.equalsIgnoreCase(filterPeriod)) {
		    		boolean isSpent = false;
			    	for( int i = 0; catalogCCs != null && i < catalogCCs.size(); i++ ) {
	 	               	isSpent = isSpentForLastYearOrPeriod(siteIds.get(index),budgetPeriod,budgetYear,catalogCCs.get(i));
	 	               	if(isSpent){
	 	               		break;
	 	               	}
			    	}
			    	if(!isSpent){
			    		siteWithNoDataList.add(siteIds.get(index));
			    		continue;
			    	}
		    	}
		    	
		    	for( int i = 0; catalogCCs != null && i < catalogCCs.size(); i++ ) {
		    		CostCenterData ccData = budgetBean.getCostCenterData(catalogCCs.get(i));
		    		if(ccData.getCostCenterTypeCd().equalsIgnoreCase(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET)) {
		    			if(doesContainCC.contains(catalogCCs.get(i))){
		    				continue;
		    			}
		    			doesContainCC.add(catalogCCs.get(i));
		    			budgetViewVector = budgetBean.getBudgets(accountData.getAccountId(), catalogCCs.get(i), budgetYear);
		    		}
		    		else {
		    			budgetViewVector = budgetBean.getBudgets(siteIds.get(index), catalogCCs.get(i), budgetYear);
		    		}
				    for( int j = 0; budgetViewVector != null && j < budgetViewVector.size(); j++ ) {
				    	budgets.add((BudgetView)budgetViewVector.get(j));
				    }
				    
		    	}
		    }
		    
		    if (errors == null || errors.size() == 0 && budgets.size() > 0) {
	        	budgetChartDto = getBudgetDtoForSitesList(request,budgets,siteIds, budgetPeriod, budgetYear,locationBudget, checkoutForm, orderFee);
	        }
		    if(budgetChartDto != null){
	        	budgetChartDto.setSiteWithNoDataList(siteWithNoDataList);
	        	budgetChartDto.setNoLastFiscalYearList(noLastYearList);
	        	reportingDto.setBudgetChart(budgetChartDto);
	        }
		    if(budgets.size() == 0){
		    	budgetChartDto = new LocationBudgetChartDto();
	        	budgetChartDto.setSiteWithNoDataList(siteWithNoDataList);
	        	budgetChartDto.setNoLastFiscalYearList(noLastYearList);
	        	reportingDto.setBudgetChart(budgetChartDto);
	        }
		    
	       
    
		}catch (Exception e){
			e.printStackTrace();
			
			if(e instanceof RemoteException){
				String errorMess = e.getMessage();
				if(errorMess.equals("reporting.error.multipleCurrencies")){
					errorMess = ClwI18nUtil.getMessage(request, errorMess, null);
				}
				errors.add("error", new ActionError("error.simpleGenericError", errorMess));  
				
			}else{
	        	String errorMess = ClwI18nUtil.getMessage(request, "reporting.error.unableToRetrieveReport", null);
	        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));  
			}
		}
		
		return errors;
	}
	
	
	public static LocationBudgetChartDto getBudgetDtoForSitesList(
			HttpServletRequest request, ArrayList<BudgetView> budgets,
			ArrayList<Integer> siteIds, int budgetPeriod, int budgetYear,
			boolean locationBudget, CheckoutForm checkoutForm, String orderFee)
			throws Exception, RemoteException {
		
		LocationBudgetChartDto budgetDto = new LocationBudgetChartDto();
		
		HttpSession session = request.getSession();
		APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
		Site siteBean = factory.getSiteAPI();
		ArrayList<Integer> catalogCCs = null;
		Budget budgetBean = factory.getBudgetAPI();
		Account accountBean = factory.getAccountAPI();

	    catalogCCs = siteBean.getCostCentersForSites(siteIds);
	    
	    //Total allocated per cc
		TreeMap<String, BigDecimal> allocatedCCMap = new TreeMap<String, BigDecimal>();
		TreeMap<String, BigDecimal> unbudgetedAllocatedCCMap = new TreeMap<String, BigDecimal>();
		HashMap<String,String> ccBudgetMap = new HashMap<String,String>();
		ArrayList<String> pOrderStatus = null;
		HashMap<String, BigDecimal> spentCCMap = new HashMap<String, BigDecimal>();
		HashMap<String, BigDecimal> pendingCCMap = new HashMap<String, BigDecimal>();
		HashMap<String,BigDecimal> limitedUnlimitedTotalMap = new HashMap<String,BigDecimal>();
		HashMap<String,BigDecimal> paddingCCMap = new HashMap<String,BigDecimal>();
		
		HashMap<String, BigDecimal> unlimSpentCCMap = new HashMap<String, BigDecimal>();
		HashMap<String, BigDecimal> unlimPendingCCMap = new HashMap<String, BigDecimal>();
		
		//BudgetViewVector budgets = budgetBean.getAllBudgetsForSites(siteIds, budgetYear);
		boolean isLimitedBudget = false;
		boolean isUnlimitedBudget = false;

		Iterator it = budgets.iterator();
		while(it.hasNext()){
			ArrayList<Integer>accountSiteIds = new ArrayList<Integer>();
			BudgetView bv = (BudgetView) it.next();
			
			int ccId = bv.getBudgetData().getCostCenterId();
			CostCenterData ccData = budgetBean.getCostCenterData(ccId);
			
			String key = ccData.getShortDesc();
			int accountId = bv.getBudgetData().getBusEntityId();
            if(ccData.getCostCenterTypeCd().equals(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET)){
                QueryRequest qr = new QueryRequest();
                qr.filterByAccountId(accountId);
                Site site = APIAccess.getAPIAccess().getSiteAPI();
                SiteViewVector siteViewV = site.getSiteCollection(qr);
                Iterator iterator = siteViewV.iterator();
                while(iterator.hasNext()){
	               	SiteView siteView = (SiteView)iterator.next();
	               	int siteIdd = siteView.getId();
	               	accountSiteIds.add(siteIdd);
                }
            }else {
            	accountSiteIds.add(accountId);//If budget type is Site budget, accountId holds siteId.
            }
            
			
			if(ccData.getCostCenterStatusCd().equals(RefCodeNames.COST_CENTER_STATUS_CD.ACTIVE) && catalogCCs.contains(ccId)){
				if(!Utility.isTrue(ccData.getNoBudget())){ //No Budget is false
					
					BigDecimal total = getBudgetAmount(bv.getDetails(), budgetPeriod);
					 
					 if(total != null){ // Not Unlimited
						 isLimitedBudget = true; // this is used to know for mixed budgets
	                     ccBudgetMap.put(key, Constants.LIMITED);
	                    
	                     if(allocatedCCMap.containsKey(key)){
                            BigDecimal ccTotal = allocatedCCMap.get(key);
                            allocatedCCMap.put(key, ccTotal.add(total));
                            
	                     }else{
                            allocatedCCMap.put(key, total);
	                     }
                        
                        // spent for budgeted cost centers.
                        for(int i = 0;i < accountSiteIds.size();i++) {
	                        int siteId = accountSiteIds.get(i);
	                        pOrderStatus = new ArrayList<String>();
	                        pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.ORDERED);
	                		pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);
	                        pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
	                        BigDecimal ccAmount = budgetBean.getAllBudgetSpentForSiteCostCenter
	                        (siteId, bv.getBudgetData().getCostCenterId(), budgetPeriod, budgetYear, pOrderStatus);
	                    
	                        if(spentCCMap.containsKey(key)){
	                            BigDecimal ccTotal = spentCCMap.get(key);
	                            spentCCMap.put(key, ccTotal.add(ccAmount));
	                            
	                        }else{
	                            spentCCMap.put(key, ccAmount);
	                        }		
	                        
	                        //pending for budget cost centers.
	                        pOrderStatus = new ArrayList<String>();
	                        pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
	
	                        ccAmount = budgetBean.getAllBudgetSpentForSiteCostCenter
	                        (siteId, bv.getBudgetData().getCostCenterId(), budgetPeriod, budgetYear, pOrderStatus);
	                        
	                        if(pendingCCMap.containsKey(key)){
	                            BigDecimal ccTotal = pendingCCMap.get(key);
	                            pendingCCMap.put(key, ccTotal.add(ccAmount));
	                            
	                        }else{
	                            pendingCCMap.put(key, ccAmount);
	                        }
                        }
					 }
	                 else { // Here we handle unlimited budget scenarios.
	                	 
	                	 isUnlimitedBudget = true;// this is used to know for mixed budgets
	                	 if(!ccBudgetMap.containsKey(key)){
                            ccBudgetMap.put(key, Constants.UNLIMITED);
	                	 }
                        
	                	 //allocated.
                         unbudgetedAllocatedCCMap.put(key, new BigDecimal(0));
                        for(int i = 0;i < accountSiteIds.size();i++) {
	                        int siteId = accountSiteIds.get(i);
	                        //spent for unlimited cost centers.
	                        pOrderStatus = new ArrayList<String>();
	                        pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.ORDERED);
	                		pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);
	                        pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
	                        BigDecimal ccAmount = budgetBean.getAllBudgetSpentForSiteCostCenter
	                        (siteId, bv.getBudgetData().getCostCenterId(), budgetPeriod, budgetYear, pOrderStatus);
	
	                        if(unlimSpentCCMap.containsKey(key)){
	                            BigDecimal ccTotal = unlimSpentCCMap.get(key);
	                            unlimSpentCCMap.put(key, ccTotal.add(ccAmount));
	                            
	                        }else{
	                            // spentCCMap.put(key, total);
	                            unlimSpentCCMap.put(key, ccAmount);
	                        }
	                           
	                        //pending for unlimited cost centers.
	                        pOrderStatus = new ArrayList<String>();
	                        pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
	                        ccAmount = budgetBean.getAllBudgetSpentForSiteCostCenter
	                         (siteId, bv.getBudgetData().getCostCenterId(), budgetPeriod, budgetYear, pOrderStatus);
	
	                        if(unlimPendingCCMap.containsKey(key)){
	                            BigDecimal ccTotal = unlimPendingCCMap.get(key);
	                            unlimPendingCCMap.put(key, ccTotal.add(ccAmount));
	                            
	                        }else{
	                        	unlimPendingCCMap.put(key, ccAmount);
	                        }
                        }
                   }
				}
			}
			
		}
		
		// Cart amount
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		HashMap<String,BigDecimal> cartCCMap = new HashMap<String,BigDecimal>();
		HashMap<String,BigDecimal> unlimCartCCMap = new HashMap<String,BigDecimal>();
		if(locationBudget && !sessionDataUtil.isExcludeOrderFromBudget()) {
			CleanwiseUser user = ShopTool.getCurrentUser(request);
	        it = budgets.iterator();
	        BigDecimal totalPrice = null;
	        ShoppingCartItemDataVector scItemDV = null;
	        ShoppingCartData shoppingCartD = null;
	       
	        if(checkoutForm == null) {
	        	shoppingCartD = ShopTool.getCurrentShoppingCart(request);
	        }
	        boolean isFreightIncluded = false;// freight and handling should be applied for only one cost center.
	        boolean isDiscountIncluded = false;// discount should be applied for only one cost center.
	        boolean isTaxIncluded = false;
	        while(it.hasNext()){
				BudgetView bv = (BudgetView) it.next();
				int ccId = bv.getBudgetData().getCostCenterId();
				CostCenterData ccData = budgetBean.getCostCenterData(ccId);
				totalPrice = new BigDecimal(0);
				if(checkoutForm != null) { //if called from checkout.
					//STJ-5637
			        scItemDV = checkoutForm.getItems();
			    	HashMap<Integer,BigDecimal> costCenterCartData = new HashMap<Integer,BigDecimal>();
			    	if(scItemDV != null){
			        	ShoppingCartItemDataVector ccItemsOnly = scItemDV.getCostCenterItemsOnly(ccId);
			      		totalPrice = new BigDecimal(ccItemsOnly.getItemsCost());
			      		if(!isFreightIncluded) {
				      		if(Utility.isSet(orderFee) && Utility.isTrue(ccData.getAllocateFreight()) && totalPrice.doubleValue() > 0 ) {
				      			HashMap<String, BigDecimal> orderFeeMap = new HashMap<String, BigDecimal>();
				      			calculateOrderFee(request, checkoutForm, ccItemsOnly, orderFeeMap, orderFee);
				      			totalPrice = totalPrice.add(orderFeeMap.get("selectedOrderFee"));
				      			isFreightIncluded = true;
				      		} else {
					      		if(Utility.isTrue(ccData.getAllocateFreight()) && totalPrice.doubleValue() > 0 ){
					      			HashMap<String, BigDecimal> orderFeeMap = new HashMap<String, BigDecimal>();
					      			calculateOrderFee(request, checkoutForm, ccItemsOnly, orderFeeMap, null);
					      			totalPrice = totalPrice.add(orderFeeMap.get(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT));
					      			totalPrice = totalPrice.add(orderFeeMap.get("Handling"));
					      			isFreightIncluded = true;
					      		}
				      		}	
			      		}
			      		if(!isDiscountIncluded) {
				      		if(Utility.isTrue(ccData.getAllocateDiscount())&& totalPrice.doubleValue() > 0 ){
				      			HashMap<String, BigDecimal> orderFeeMap = new HashMap<String, BigDecimal>();
				      			calculateOrderFee(request, checkoutForm, ccItemsOnly, orderFeeMap, null);
				      			totalPrice = totalPrice.add(orderFeeMap.get(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT));
				      			isDiscountIncluded = true;
				      		}
			      		}
						if (!RefCodeNames.COST_CENTER_TAX_TYPE.DONT_ALLOCATE_SALES_TAX
								.equals(ccData.getCostCenterTaxType())
								&& totalPrice.doubleValue() > 0) {
								if(RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX.equals(ccData.getCostCenterTaxType())) {
									factory = APIAccess.getAPIAccess();
					      	        Site siteEjb   = factory.getSiteAPI();
					      	        Order orderEjb = factory.getOrderAPI();
					      	        StoreData store = user.getUserStore();
					      	        SiteData site = user.getSite();
					      	        Account account = APIAccess.getAPIAccess().getAccountAPI();
					      	        AccountData accountData = account.getAccountForSite(site.getSiteId());
					      	        Distributor distributorEjb = factory.getDistributorAPI();
					      	        Catalog catalogEjb = factory.getCatalogAPI();
					      	        totalPrice = totalPrice.add(ShopTool.getSalesTaxAvalara(orderEjb, siteEjb, distributorEjb, store, accountData, site, ccItemsOnly, catalogEjb));
					      	        //isProductSalesTaxIncluded = true;
								} else if(!isTaxIncluded){
									factory = APIAccess.getAPIAccess();
					      	        Site siteEjb   = factory.getSiteAPI();
					      	        Order orderEjb = factory.getOrderAPI();
					      	        StoreData store = user.getUserStore();
					      	        SiteData site = user.getSite();
					      	        Account account = APIAccess.getAPIAccess().getAccountAPI();
					      	        AccountData accountData = account.getAccountForSite(site.getSiteId());
					      	        Distributor distributorEjb = factory.getDistributorAPI();
					      	        Catalog catalogEjb = factory.getCatalogAPI();
					      	        totalPrice = totalPrice.add(ShopTool.getSalesTaxAvalara(orderEjb, siteEjb, distributorEjb, store, accountData, site, ccItemsOnly, catalogEjb));
					      	        isTaxIncluded = true;
				      	        }
			             } 
			    	}
				} else {
					scItemDV = shoppingCartD.getItems();
					ShoppingCartItemDataVector ccItemsOnly = scItemDV.getCostCenterItemsOnly(ccId);
					totalPrice = new BigDecimal(ccItemsOnly.getItemsCost());
				}
				//STJ-5637
				String costCenterName = ccData.getShortDesc();
	      		if(ccBudgetMap.containsKey(costCenterName) && ccBudgetMap.get(costCenterName).equals(Constants.LIMITED)) {
	      			cartCCMap.put(costCenterName,totalPrice);
	      		}else{
	      			unlimCartCCMap.put(costCenterName,totalPrice);
	      		}
			}
		}
		//Total 
		//Vikranth TODO - create a DTO object and set all the below arguments and pass the created DTO object 
		// to the following call instead of passing all these.
        budgetDto = getTotalBudget(request,allocatedCCMap,unbudgetedAllocatedCCMap,spentCCMap,pendingCCMap,cartCCMap,unlimCartCCMap,
                unlimSpentCCMap,unlimPendingCCMap,locationBudget);
		return budgetDto;
	}
	
	public static LocationBudgetChartDto getTotalBudget(HttpServletRequest request,
			TreeMap<String, BigDecimal> allocatedMap, TreeMap<String, BigDecimal> unbudgetedAllocatedMap,
            HashMap<String, BigDecimal> spentMap,HashMap<String, BigDecimal> pendingMap, 
            HashMap<String, BigDecimal> cartMap,HashMap<String, BigDecimal> unlimCartMap,
            HashMap<String, BigDecimal> unlimSpentMap,HashMap<String, BigDecimal> unlimPendingMap,
            boolean locationBudget) throws Exception{
        
    	LocationBudgetChartDto budgetDto = new LocationBudgetChartDto();
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        
        ArrayList<BigDecimal> allocatedList = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> overBudgetList = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> unusedBudgetList = new ArrayList<BigDecimal>();
        ArrayList<String> budgetedCostCentersList = new ArrayList<String>();
        
        BigDecimal totalBudget = new BigDecimal(0);
        BigDecimal totalSpent = new BigDecimal(0);
        BigDecimal totalPending = new BigDecimal(0);
        BigDecimal totalCart = new BigDecimal(0);
        BigDecimal totalOverBudget = new BigDecimal(0);
        BigDecimal totalUnusedBudget = new BigDecimal(0);
        
        //STJ-5218
        ArrayList<String> combinedCostCentersList = new ArrayList<String>();
        
        //spent related objects.
        ArrayList<BigDecimal> budgetedSpentList = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> unbudgetedSpentList = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> combinedSpentList = new ArrayList<BigDecimal>();
        BigDecimal totalBudgetedSpent = new BigDecimal(0);
        BigDecimal totalUnbudgetedSpent = new BigDecimal(0);
        BigDecimal totalCombinedSpent = new BigDecimal(0);
        
        //pending related objects.
        ArrayList<BigDecimal> budgetedPendingList = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> unbudgetedPendingList = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> combinedPendingList = new ArrayList<BigDecimal>();
        BigDecimal totalBudgetedPending = new BigDecimal(0);
        BigDecimal totalUnbudgetedPending = new BigDecimal(0);
        BigDecimal totalCombinedPending = new BigDecimal(0);
        
        //cart related objects.
        ArrayList<BigDecimal> budgetedCartList = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> unbudgetedCartList = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> combinedCartList = new ArrayList<BigDecimal>();
        BigDecimal totalBudgetedCart = new BigDecimal(0);
        BigDecimal totalUnbudgetedCart = new BigDecimal(0);
        BigDecimal totalCombinedCart = new BigDecimal(0);
        //STJ-5218
        Iterator<String> it = allocatedMap.keySet().iterator();
        while(it.hasNext()){
            String costCenter = it.next();
            // cost center names
            budgetedCostCentersList.add(costCenter);
            // allocated amount
            allocatedList.add(allocatedMap.get(costCenter));                           
            totalBudget = totalBudget.add(allocatedMap.get(costCenter));
            
            //spent amount:Here we calculate spent amounts for budgeted cost centers.
            BigDecimal spentAmt = new BigDecimal(0);
            if(spentMap.containsKey(costCenter)){
            	spentAmt = spentMap.get(costCenter);
            }
            budgetedSpentList.add(spentAmt);// cost center specific budgeted spent.
            //pending amount:Here we calculate pending amounts for budgeted cost centers.
            BigDecimal pendingAmt = new BigDecimal(0);
            if(pendingMap.containsKey(costCenter)){
            	pendingAmt = pendingMap.get(costCenter);
            }
            budgetedPendingList.add(pendingAmt);
            //cart amount for location budget, we will not be displaying cart amount in budgets-at-a-glance.
            BigDecimal cartAmt = new BigDecimal(0);
            if(locationBudget && !sessionDataUtil.isExcludeOrderFromBudget() ) {
            	
            	 //cart amount:Here we calculate cart amounts for budgeted cost centers.
                if(cartMap.containsKey(costCenter)){
                	cartAmt = cartMap.get(costCenter);
                }
                budgetedCartList.add(cartAmt);
            }
            
            //over/unused budget
            BigDecimal allocatedBudget  = allocatedMap.get(costCenter);
            BigDecimal spentBudget = new BigDecimal(0);
            if(spentMap.containsKey(costCenter)){
                spentBudget = spentBudget.add(spentMap.get(costCenter));
            }
            if(pendingMap.containsKey(costCenter)){
                spentBudget = spentBudget.add(pendingMap.get(costCenter));
            }
            if(locationBudget && !sessionDataUtil.isExcludeOrderFromBudget() ) {
                if(cartMap.containsKey(costCenter)){
                    spentBudget = spentBudget.add(cartMap.get(costCenter));
                }    
            }
           
        	if(allocatedBudget.compareTo(spentBudget) > 0){
                BigDecimal diff = allocatedBudget.subtract(spentBudget);
                
                unusedBudgetList.add(diff);
                overBudgetList.add(new BigDecimal(0));
        	}else if(spentBudget.compareTo(allocatedBudget) > 0){
                BigDecimal diff = spentBudget.subtract(allocatedBudget);
                overBudgetList.add(diff);
                unusedBudgetList.add(new BigDecimal(0));
        	}else{
        		unusedBudgetList.add(new BigDecimal(0));
        		overBudgetList.add(new BigDecimal(0));
        	}
        	
        	//Here we calculate combined amounts(budgeted and non-budgeted cost centers)
	            if(allocatedMap.containsKey(costCenter) && unbudgetedAllocatedMap.containsKey(costCenter)) {
	            	combinedCostCentersList.add(costCenter);
		            
	            	combinedSpentList.add(spentMap.get(costCenter).add(unlimSpentMap.get(costCenter)));
	            	totalBudgetedSpent = totalBudgetedSpent.add(spentMap.get(costCenter));
		            
	            	combinedPendingList.add(pendingMap.get(costCenter).add(unlimPendingMap.get(costCenter)));
	            	totalBudgetedPending = totalBudgetedPending.add(pendingMap.get(costCenter));
	            } else {
	            	combinedSpentList.add(spentMap.get(costCenter));
	            	combinedCostCentersList.add(costCenter);
	            	combinedPendingList.add(pendingMap.get(costCenter));
	            	totalBudgetedSpent = totalBudgetedSpent.add(spentAmt);
	            	totalBudgetedPending = totalBudgetedPending.add(pendingAmt);
	            	if(locationBudget) {
	            		combinedCartList.add(cartAmt);
	            		totalBudgetedCart = totalBudgetedCart.add(cartAmt);
	            	}
	            }
        }
        
        ArrayList<String> unbudgetedCostCentersList = new ArrayList<String>();
        it = unbudgetedAllocatedMap.keySet().iterator();
        while(it.hasNext()){
            String costCenter = it.next();
            // cost center names
            unbudgetedCostCentersList.add(costCenter);
           
            BigDecimal spentAmt = new BigDecimal(0);
            //spent amount:Here we calculate spent amounts for non-budgeted cost centers.
            spentAmt = new BigDecimal(0);
            if(unlimSpentMap.containsKey(costCenter)) {
            	spentAmt = unlimSpentMap.get(costCenter);
            }
            unbudgetedSpentList.add(spentAmt);
           
            BigDecimal pendingAmt = new BigDecimal(0);
            //pending amount:Here we calculate pending amounts for non-budgeted cost centers.
            pendingAmt = new BigDecimal(0);
            if(unlimPendingMap.containsKey(costCenter)) {
            	pendingAmt = unlimPendingMap.get(costCenter);
            }
            unbudgetedPendingList.add(pendingAmt);
            
            //cart amount for location budget
            if(locationBudget && !sessionDataUtil.isExcludeOrderFromBudget() ) {
            	
            	 //cart amount:Here we calculate cart amounts for non-budgeted cost centers.
                BigDecimal cartAmt = new BigDecimal(0);
                if(unlimCartMap.containsKey(costCenter)) {
                	cartAmt = unlimCartMap.get(costCenter);
                }
                unbudgetedCartList.add(cartAmt);
            }
            
           //Here we calculate combined amounts(budgeted and non-budgeted cost centers)
            if(!allocatedMap.containsKey(costCenter) && unbudgetedAllocatedMap.containsKey(costCenter)) {
            	combinedCostCentersList.add(costCenter);
            	combinedSpentList.add(unlimSpentMap.get(costCenter));
	            combinedPendingList.add(unlimPendingMap.get(costCenter));
	            if(locationBudget && !sessionDataUtil.isExcludeOrderFromBudget()) {
	            	combinedCartList.add(unlimCartMap.get(costCenter));
	            }
            }
            
            totalUnbudgetedSpent = totalUnbudgetedSpent.add(unlimSpentMap.get(costCenter));
            totalUnbudgetedPending = totalUnbudgetedPending.add(unlimPendingMap.get(costCenter));
            if(locationBudget && !sessionDataUtil.isExcludeOrderFromBudget()) {
            	totalUnbudgetedCart = totalUnbudgetedCart.add(unlimCartMap.get(costCenter));
            }
        }
        
        //total over and unused
        BigDecimal totalSpentBudget = totalBudgetedSpent.add(totalBudgetedPending);
        if(locationBudget && !sessionDataUtil.isExcludeOrderFromBudget() ) {
            totalSpentBudget = totalSpentBudget.add(totalBudgetedCart);
        }
        	
		totalUnusedBudget = new BigDecimal(0);
		totalOverBudget = new BigDecimal(0);
    	
    	if(totalBudget.compareTo(totalSpentBudget)>0) {
            totalUnusedBudget = totalBudget.subtract(totalSpentBudget);
            
        }else if(totalSpentBudget.compareTo(totalBudget)>0) {
            totalOverBudget = totalSpentBudget.subtract(totalBudget);
            
        }
        	
        if(budgetedCostCentersList.size() == 0 && unbudgetedCostCentersList.size() == 0 && combinedCostCentersList.size() == 0){
            budgetDto.setAccountHasBudget(false);
            return budgetDto;
        }
       
        NumberFormat formatter = ClwI18nUtil.getDecimalFormatForHC(request);
        
        //Here we set amounts for budgeted cost centers.
        if(budgetedCostCentersList.size() > 0) {
        	budgetDto.setExistsBudgetedAmounts(true);
        
	        budgetDto.setAllocatedBudget(allocatedList);
	        budgetDto.setSpentAmount(budgetedSpentList);
	        budgetDto.setPendingAppr(budgetedPendingList);
	        budgetDto.setShoppingCart(budgetedCartList);
	        budgetDto.setOverBudget(overBudgetList);
	        budgetDto.setUnusedBudget(unusedBudgetList);
	        budgetDto.setCostCenterNames(budgetedCostCentersList);
	        
	        //Here we set total amounts for budgeted cost centers.
	        budgetDto.setTotalBD(formatter.format(totalBudget));
	        budgetDto.setTotalSpent(formatter.format(totalBudgetedSpent));
	        budgetDto.setTotalPending(formatter.format(totalBudgetedPending));
	        budgetDto.setTotalCart(formatter.format(totalBudgetedCart));
	        budgetDto.setTotalOver(formatter.format(totalOverBudget));
	        budgetDto.setTotalUnused(formatter.format(totalUnusedBudget));	
        }

        //Here we set amounts for non-budgeted cost centers.
        if(unbudgetedCostCentersList.size() > 0) {
        	budgetDto.setExistsNonBudgetedAmounts(true);
	        budgetDto.setUnbudgetedSpentList(unbudgetedSpentList);
	        budgetDto.setUnbudgetedPendingList(unbudgetedPendingList);
	        budgetDto.setUnbudgetedCartList(unbudgetedCartList);
	        budgetDto.setUnbudgetedCostCenterNamesList(unbudgetedCostCentersList);
	        
	        //Here we set total amounts for non-budgeted cost centers.
	        budgetDto.setUnbudgetedTotalSpent(formatter.format(totalUnbudgetedSpent));
	        budgetDto.setUnbudgetedTotalPending(formatter.format(totalUnbudgetedPending));
	        budgetDto.setUnbudgetedTotalCart(formatter.format(totalUnbudgetedCart));
        }
        
        //Here we calculate and set total amounts for combined(budgeted & non-budgeted cost centers)
        if(combinedCostCentersList.size() > 0) {
        	budgetDto.setExistsCombinedAmounts(true);
	        budgetDto.setCombinedTotalSpent(formatter.format(totalBudgetedSpent.add(totalUnbudgetedSpent)));
	    	budgetDto.setCombinedTotalPending(formatter.format(totalBudgetedPending.add(totalUnbudgetedPending)));
	    	budgetDto.setCombinedTotalCart(formatter.format(totalBudgetedCart.add(totalUnbudgetedCart)));
	        
	        //Here we set amounts for combined(budgeted & non-budgeted cost centers)
	        budgetDto.setCombinedSpentList(combinedSpentList);
	        budgetDto.setCombinedPendingList(combinedPendingList);
	        budgetDto.setCombinedCartList(combinedCartList);
	        budgetDto.setCombinedCostCenterNamesList(combinedCostCentersList);
        }
        budgetDto.setAccountHasBudget(true);
        return budgetDto;
    }

	
	public static BigDecimal getBudgetAmount(BudgetDetailDataVector details, int period){
		
		BigDecimal total = null;
	
		for(int i=0; i<details.size(); i++){
			BudgetDetailData det = (BudgetDetailData)details.get(i);
			if(det.getPeriod() == period){
				return det.getAmount();				
			}else{
				if(det.getAmount()!=null){
					if(total==null){
						total = new BigDecimal(0);
					}
					total = total.add(det.getAmount());
				}
			}
		}
		return total;
	}
	
	

    public static ActionErrors showMiscReports(HttpServletRequest request,
            ReportingDto reportingDto) {
        ActionErrors errors = new ActionErrors();
        try {
            final String genericCategory = ClwMessageResourcesImpl.getMessage(
                    request, "reporting.label.generic");
            CleanwiseUser user = (CleanwiseUser) request.getSession()
                    .getAttribute(Constants.APP_USER);
            GenericReportViewVector reports = (GenericReportViewVector) user
                    .getAuthorizedRuntimeReports().clone();
            
            ReportingUtils.restrictNotSupportedReports(reports);
            // Remove Delivery Schedule report since no customer need it
            /*
            // Site schedule
            SiteData currentSiteD = user.getSite();
            if (null != currentSiteD
                    && null != currentSiteD.getNextDeliveryDate()) {
                GenericReportView grd = GenericReportView.createValue();
                grd.setReportName(RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE);
                grd.setReportCategory(Constants.CUSTOMER_REPORT_CATEGORY);
                grd.setLongDesc(ClwMessageResourcesImpl.getMessage(request,
                        "reporting.name.displaysOrderCut-off"));
                reports.add(grd);
            }*/
            // Budget (JCI) reports
            /*if (user.hasAccountReports() == true) {
                APIAccess factory = (APIAccess) request.getSession()
                        .getAttribute(Constants.APIACCESS);
                RefCdDataVector typev = factory.getListServiceAPI()
                        .getRefCodesCollection("ACCOUNT_REPORT_TYPE_CD");
                if (typev != null) {
                    Iterator it = typev.iterator();
                    while (it.hasNext()) {
                        RefCdData ref = (RefCdData) it.next();
                        GenericReportView grd = GenericReportView.createValue();
                        grd.setReportName(ref.getValue());
                        grd.setReportCategory(Constants.CUSTOMER_REPORT_CATEGORY
                                        + " "
                                        + ClwMessageResourcesImpl
                                                .getMessage(request,
                                                        "reporting.name.accountReports"));
                        if (RefCodeNames.ACCOUNT_REPORT_TYPE_CD.ORDER_INFORMATION_REPORT
                                .equals(ref.getValue())) {
                            grd.setLongDesc(ClwMessageResourcesImpl.getMessage(
                                    request,
                                    "reporting.name.displayOrderingActivity"));
                        }
                        reports.add(grd);
                    }
                }
            }*/
            for (int i = 0; i < reports.size(); i++) {
                GenericReportView report = (GenericReportView) reports.get(i);
                if (Constants.CUSTOMER_REPORT_CATEGORY.equals(report
                        .getReportCategory())
                        || genericCategory.equals(report.getReportCategory())) {
                    report.setReportCategory(null);
                }
            }
            AnalyticReportLogic.sortByCategoryName(reports);
            for (int i = 0; i < reports.size(); i++) {
                GenericReportView report = (GenericReportView) reports.get(i);
                if (report.getReportCategory() == null) {
                    report.setReportCategory(genericCategory);
                }
            }
            reportingDto.setMatchedReports(reports);
            if (errors.size() > 0) {
                return errors;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMess = ClwI18nUtil.getMessage(request,
                    "reporting.error.unableToRetrieveReport", null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return errors;
    }

    public static ActionErrors showStandardReportFilter(HttpServletRequest request,ReportingForm reportingForm, boolean setPreviousValues) throws Exception{
    	ActionErrors errors = null;
    	ReportingDto reportingInfo = reportingForm.getCustomerReportingForm().getGenericReportingInfo();
		initCategoriesCriteria(request, reportingInfo);
		
		HttpSession session = request.getSession();
		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	//if the user has not yet selected a location, return an error
        if (currentLocation == null) {
        	errors = new ActionErrors();
            String errorMess = ClwI18nUtil.getMessage(request,"error.noLocationSelected", null);
            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
        } else {
        	SessionDataUtil sessionDataUtils = Utility.getSessionDataUtil(request);
        	//Initialize Report controls related stuff only once per a site.    	
        	//Reset the flag if current location has been changed.
        	if(sessionDataUtils.isInitReportControls()) {
        		CustAcctMgtReportLogic.initConstantList(request);
                ProfilingMgrLogic.getProfilesForSite(request);
                sessionDataUtils.setInitReportControls(false);
        	}
        	//set report name and desc
        	setReportTypeDetails(request,reportingForm);
        	
        	CustAcctMgtReportForm customerReportingForm = reportingForm.getCustomerReportingForm();
        	GenericReportControlViewVector prevControls = customerReportingForm.getReportControls();
        	if(Utility.isSet(customerReportingForm.getReportTypeCd())){ 
        		String reportTypeDesc = customerReportingForm.getReportTypeDesc();
        		errors = CustAcctMgtReportLogic.initLocDateSelect(request, customerReportingForm);
        		//STJ-4562
        		if(!Utility.isSet(customerReportingForm.getReportTypeDesc())) {
        			customerReportingForm.setReportTypeDesc(reportTypeDesc);
        		}
        	} else {
        		errors = new ActionErrors();
        		String errorMess = ClwI18nUtil.getMessage(request,"reporting.error.unableToRetrieveReport", null);
                errors.add("error", new ActionError("error.simpleGenericError",errorMess));
        	}
        	
        	//Populate categories filter
        	try{
	        	String cat1Selected = reportingInfo.getCat1Selected();
		        String cat2Selected = reportingInfo.getCat2Selected();
		        String cat3Selected = reportingInfo.getCat3Selected();
		        String cat4Selected = reportingInfo.getCat4Selected();
		        
		        String allCategories = ClwI18nUtil.getMessage(request, "reporting.filter.allCategories", null);
		        if(cat1Selected.equals(allCategories)){
		        	cat2Selected = allCategories;
		        	cat3Selected = allCategories;
		        	cat4Selected = allCategories;
		        }
		        
		        //get category structure
		        ArrayList<CategoryStructureDto> categoryStructure = getCategoryStructures(request, appUser,reportingInfo);
		        
		        reportingInfo.setCategoryTree(categoryStructure);
	        }catch (Exception e){
				e.printStackTrace();
	        	throw new Exception(e.getMessage());
			}
            if (setPreviousValues) {
                for (int i = 0; prevControls != null && i < prevControls.size()
                        && customerReportingForm.getReportControls() != null
                        && i < customerReportingForm.getReportControls().size(); i++) {
                    GenericReportControlView prevControl = (GenericReportControlView) prevControls
                            .get(i);
                    GenericReportControlView curControl = (GenericReportControlView) customerReportingForm
                            .getReportControls().get(i);
                    curControl.setValue(prevControl.getValue());
                }
            }
        	sessionDataUtils.setGenericReportingDto(reportingInfo);
        }
    	
    	return errors;
    }
    
    public static ActionErrors searchCustomerReport(HttpServletRequest request, HttpServletResponse response,
    		ReportingForm reportingForm,ReportRequest pRepRequest) throws Exception {
    	ActionErrors errors = null;
    	CustAcctMgtReportForm customerReportingForm = reportingForm.getCustomerReportingForm();
    	String[] selectedSites = customerReportingForm.getSelectedSites();
        if (0 == selectedSites.length) {
        	errors = new ActionErrors();
        	String errorMess = ClwI18nUtil.getMessage(request,"userportal.esw.standardReports.noLocationSelected", null);
        	errors.add("error", new ActionError("error.simpleGenericError",errorMess));
        } 
        else {
        	//Validate Date fields
        	errors = validateDateFields(request, reportingForm, pRepRequest);
        	if (errors!=null && errors.isEmpty()) {
        	    prepareLocateValues(request, reportingForm);
        	    setCategoriesControl(request, reportingForm,reportingForm.getCustomerReportingForm().getGenericReportingInfo());
            	errors = CustAcctMgtReportLogic.getCustomerReport(request, response, customerReportingForm, pRepRequest);
        	}
        }
    	return errors;
    }
    
    private static void setCategoriesControl(HttpServletRequest request,ReportingForm reportingForm, 
    		ReportingDto genericReportingDto){
  	
    	ReportingDto reportingDto = null;
    	if(genericReportingDto != null){
    		reportingDto = genericReportingDto;
    	}else{
    		reportingDto = reportingForm.getOrdersGlanceReportingInfo();
    	}

    	ArrayList<CategoryStructureDto> categoryTree = reportingDto.getCategoryTree();
    	
    	GenericReportControlViewVector controls = reportingForm.getCustomerReportingForm().getReportControls();
		
        List catIds = new ArrayList();
        String cat1Selected = reportingDto.getCat1Selected();
        String cat2Selected = reportingDto.getCat2Selected();
        String cat3Selected = reportingDto.getCat3Selected();
        String cat4Selected = reportingDto.getCat4Selected();
        
        String allCategories = ClwI18nUtil.getMessage(request, "reporting.filter.allCategories", null);
        if(!cat1Selected.equals(allCategories)){
        	Iterator it = categoryTree.iterator();
        	
        	if(cat2Selected.equals(allCategories)){
        		//add all levels below cat 1
    			Integer catSelected = new Integer(cat1Selected.split("-",2)[0]).intValue();
        		
				while(it.hasNext()){
					CategoryStructureDto catStruct = (CategoryStructureDto)it.next();
					
					if(catStruct.getLevel1() == catSelected
							&& catStruct.getLowestLevel()>0){
						if(!catIds.contains(catStruct.getLowestLevel())){
							catIds.add(catStruct.getLowestLevel());
						}
					}
				}
				
        	}else{
        		
        		if(cat3Selected.equals(allCategories)){
        			//add all levels below cat 2
        			Integer catSelected = new Integer(cat2Selected.split("-",2)[0]).intValue();
    				while(it.hasNext()){
    					CategoryStructureDto catStruct = (CategoryStructureDto)it.next();
    					
    					if(catStruct.getLevel2() == catSelected
    							&& catStruct.getLowestLevel()>0){
    						if(!catIds.contains(catStruct.getLowestLevel())){
    							catIds.add(catStruct.getLowestLevel());
    						}
    					}
    				}
    				
        		}else{
        			
        			if(cat4Selected.equals(allCategories)){
        				//add all levels below cat 3
        				Integer catSelected = new Integer(cat3Selected.split("-",2)[0]).intValue();
        				while(it.hasNext()){
        					CategoryStructureDto catStruct = (CategoryStructureDto)it.next();
        					
        					if(catStruct.getLevel3() == catSelected
        							&& catStruct.getLowestLevel()>0){
        						if(!catIds.contains(catStruct.getLowestLevel())){
        							catIds.add(catStruct.getLowestLevel());
        						}
        					}
        				}
        			}else{
        				//add level 4
        				Integer catSelected = new Integer(cat4Selected.split("-",2)[0]).intValue();
        				if(!catIds.contains(catSelected)){
							catIds.add(catSelected);
						}
        			}
        		}
        	}
        	
        	String categories = null;
        	Iterator it2 = catIds.iterator();
        	while(it2.hasNext()){
        		if(categories==null){
        			categories = ((Integer)it2.next()).toString();
        		}else{
        			categories += "," + ((Integer)it2.next()).toString();
        		}
        		
        	}
        	GenericReportControlView cv = new GenericReportControlView();
        	cv.setName("CATEGORIES_OPT");
        	cv.setValue(categories);
        	controls.add(cv);
        }
    }
    
    private static void prepareLocateValues(HttpServletRequest request,
            ReportingForm reportingForm) {
        HttpSession session = request.getSession(true);
        ReportingDto reportingInfo = reportingForm.getCustomerReportingForm().getGenericReportingInfo(); 
        int accountIds[] = reportingInfo.getAccountId();
        int siteIds[] = reportingInfo.getSiteId();
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        sessionDataUtil.getGenericReportingDto().setSiteId(siteIds);
        sessionDataUtil.getGenericReportingDto().setAccountId(accountIds);
        AccountUIViewVector accounts = new AccountUIViewVector();
        for (int i = 0; accountIds != null && i < accountIds.length; i++) {
            int accountId = accountIds[i];
            if (accountId > 0) {
                BusEntityData beD = BusEntityData.createValue();
                beD.setBusEntityId(accountId);
                AccountUIView item = AccountUIView.createValue();
                item.setBusEntity(beD);
                accounts.add(item);
            }
        }
        session.setAttribute("Report.parameter.accounts", accounts);
        SiteViewVector sites = new SiteViewVector();
        for (int i = 0; siteIds != null && i < siteIds.length; i++) {
            int siteId = siteIds[i];
            if (siteId > 0) {
                SiteView item  = SiteView.createValue();
                item.setId(siteId);
                sites.add(item);
            }
        }
        session.setAttribute("Report.parameter.sites", sites);
    }

    public static ActionErrors selectReportResultsTab(HttpServletRequest request, ReportingForm reportingForm) throws Exception {
    	ActionErrors errors = null;
    	
    	String pageName = reportingForm.getReportResultsSubTab();
    	if(Utility.isSet(pageName)){
    		//Get Report Results from the session.
    		CustAcctMgtReportForm customerReportingForm = reportingForm.getCustomerReportingForm();
    		
    		if(Utility.isSet(customerReportingForm.getReportResults())) {
    			errors = CustAcctMgtReportLogic.getGenericReportPage(request,customerReportingForm,pageName);
    		}
    		
    	} else {
    		errors = new ActionErrors();
        	String errorMess = ClwI18nUtil.getMessage(request,"userportal.esw.standardReports.invalidReportResultsTab", null);
        	errors.add("error", new ActionError("error.simpleGenericError",errorMess));
    	}
    	
    	return errors;
    }
    
    public static void downLoadCustomerReport(HttpServletRequest request, HttpServletResponse response,
    		ReportingForm reportingForm,ReportRequest pRepRequest) throws Exception {
    	
    	CustAcctMgtReportForm customerReportingForm = reportingForm.getCustomerReportingForm();
    	CustAcctMgtReportLogic.downloadGenericReport(request,response,customerReportingForm, pRepRequest);
    }
    
    private static void setReportTypeDetails(HttpServletRequest request, ReportingForm reportingForm) {
    	String reportTypeCd="";
    	
    	long reportEncodedId = reportingForm.getReportEncodedId();
    	if(reportEncodedId>0) {
    		//Decode reportId
    		reportEncodedId = (reportEncodedId - Constants.MULTIPLICATION_FACTOR)/Constants.MULTIPLICATION_FACTOR;
    		int reportId = (int)reportEncodedId;
    		
    		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    		//STJ-5195: Preserved locations should be cleared if the user navigates to a different report. 
    		//They should remain same if the same report selected again.
    		int lastSelectedReportId = sessionDataUtil.getLastSelectedReportId();
    		if(reportId!=lastSelectedReportId) {
    			//Clear last selected location/account ids for 'Specify Location/Accounts' options
    			sessionDataUtil.getGenericReportingDto().setSiteId(new int[0]);
    	        sessionDataUtil.getGenericReportingDto().setAccountId(new int[0]);
    	        //STJ-5677
    	        try {
    	        	Map<String,LocationSearchDto> locationSearchDtoMap = sessionDataUtil.getLocationSearchDtoMap();
	    	        LocationSearchDto locateSearchDto = new LocationSearchDto(); 
	    	        CleanwiseUser user = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
	    	        locateSearchDto.setUserId(Integer.toString(user.getUserId()));
	    	        locateSearchDto.setSortField(Constants.LOCATION_SORT_FIELD_LAST_VISIT);
	    	        locateSearchDto.setSortOrder(Constants.LOCATION_SORT_ORDER_DESCENDING);
	                
	    	        ActionErrors errors = DashboardLogic.performLocationSearch(request,locateSearchDto);
	                if (errors == null || errors.isEmpty()) {
	                	locationSearchDtoMap.put(Constants.SPECIFY_LOCATIONS_STANDARD_REPORTS, locateSearchDto);
	                } 
    	        } catch (Exception e) {
    	        	
    	        }
    		}
    		sessionDataUtil.setLastSelectedReportId(reportId);
    		
    		ReportingDto reportingDto = sessionDataUtil.getGenericReportingDto();
    		Collection reportList = reportingDto.getMatchedReports();
    		if(Utility.isSet(reportList)) {
    			CustAcctMgtReportForm customerReportingForm = reportingForm.getCustomerReportingForm();
    			Iterator reports = reportList.iterator();
    			while(reports.hasNext()){
    				GenericReportView report = (GenericReportView) reports.next();
    				 if (report.getGenericReportId() == reportId) {
                     	customerReportingForm.setReportTypeCd(report.getReportName());
                     	customerReportingForm.setReportTypeDesc(report.getLongDesc());
                     	customerReportingForm.setReportDbName(report.getDBName());
                     	break;
                     }
    			}
    		} //End of If
    		
    	}
    }
    
    private static ActionErrors validateDateFields(HttpServletRequest request, ReportingForm reportingForm, ReportRequest repRequest){
    	ActionErrors errors = new ActionErrors();
    	GenericReportControlViewVector controls = reportingForm.getCustomerReportingForm().getReportControls();
    	if (Utility.isSet(controls)) {
    		 String defaultDateFormat = null;
    		 if (Utility.isSet(repRequest.mDateFormat)) {
    			 defaultDateFormat = repRequest.mDateFormat;
    		 }
    		 else {
    			 defaultDateFormat = ClwI18nUtil.getUIDateFormat(request);
    		 }
    		 Date actualStartDate = null;
    		 Date actualEndDate = null;
    		 Date quotedStartDate = null;
    		 Date quotedEndDate = null;
    		 Date receivedDateFrom = null;
    		 Date receivedDateTo = null;
    		 Date beginDate = null;
    		 Date endDate = null; 
    		 
    		 for (int ii = 0; ii < controls.size(); ii++) {
		        GenericReportControlView control = (GenericReportControlView) controls.get(ii);
		        //reset control value, if it is default to user date format.
		        if (Utility.isSet(control.getValue()) && defaultDateFormat.equalsIgnoreCase(control.getValue())) {
	        		control.setValue("");
	        	}
		        String name = control.getName();
		        if(Utility.isSet(name)){
		        	name = name.trim();
		        }
		        if (Constants.REPORT_CONTROL_BEG_DATE_ACTUAL_OPT.equals(name)) {
		        	if (Utility.isSet(control.getValue())){
		        		try {
		        			actualStartDate = ClwI18nUtil.parseDateInp(request, control.getValue());
						} 
		        		catch (ParseException e) {
							log.error("Invalid Actual Start Date entered.");
			                String fieldName = ClwI18nUtil.getMessage(request, "userportal.esw.standardReports.actualStartDate");
			                if (!Utility.isSet(fieldName)) {
			                	fieldName = Constants.REPORT_STANDARD_FILTER_ACTUAL_START_DATE_LABEL;
			                }
			                String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDate", new Object[]{fieldName, control.getValue()});
			                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
						}
		        	}
		        } 
		        else if (Constants.REPORT_CONTROL_END_DATE_ACTUAL_OPT.equals(name)) {
		        	if (Utility.isSet(control.getValue())){
		        		try {
		        			actualEndDate = ClwI18nUtil.parseDateInp(request, control.getValue());
						} 
		        		catch (ParseException e) {
							log.error("Invalid Actual End Date entered.");
			                String fieldName = ClwI18nUtil.getMessage(request, "userportal.esw.standardReports.actualEndDate");
			                if (!Utility.isSet(fieldName)) {
			                	fieldName = Constants.REPORT_STANDARD_FILTER_ACTUAL_END_DATE_LABEL;
			                }
			                String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDate", new Object[]{fieldName, control.getValue()});
			                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
						}
		        	}
		        } 
		        else if (Constants.REPORT_CONTROL_BEG_DATE_ESTIMATE_OPT.equals(name)) {
		        	if (Utility.isSet(control.getValue())){
		        		try {
		        			quotedStartDate = ClwI18nUtil.parseDateInp(request, control.getValue());
						} 
		        		catch (ParseException e) {
							log.error("Invalid Quoted Start Date entered.");
			                String fieldName = ClwI18nUtil.getMessage(request, "userportal.esw.standardReports.estimatedStartDate");
			                if (!Utility.isSet(fieldName)) {
			                	fieldName = Constants.REPORT_STANDARD_FILTER_ESTIMATED_START_DATE_LABEL;
			                }
			                String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDate", new Object[]{fieldName, control.getValue()});
			                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
						}
		        	}
		        } 
		        else if(Constants.REPORT_CONTROL_END_DATE_ESTIMATE_OPT.equals(name) ) {
		        	if (Utility.isSet(control.getValue())){
		        		try {
		        			quotedEndDate = ClwI18nUtil.parseDateInp(request, control.getValue());
						} 
		        		catch (ParseException e) {
							log.error("Invalid Quoted End Date entered.");
			                String fieldName = ClwI18nUtil.getMessage(request, "userportal.esw.standardReports.estimatedEndDate");
			                if (!Utility.isSet(fieldName)) {
			                	fieldName = Constants.REPORT_STANDARD_FILTER_ESTIMATED_END_DATE_LABEL;
			                }
			                String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDate", new Object[]{fieldName, control.getValue()});
			                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
						}
		        	}
		        } 
		        else if (Constants.REPORT_CONTROL_RECEIVED_DATE_FROM_OPT.equals(name)) {
		        	if (Utility.isSet(control.getValue())){
		        		try {
		        			receivedDateFrom = ClwI18nUtil.parseDateInp(request, control.getValue());
						} 
		        		catch (ParseException e) {
							log.error("Invalid Recieved Date From entered.");
			                String fieldName = ClwI18nUtil.getMessage(request, "userportal.esw.standardReports.receivedDateFrom");
			                if (!Utility.isSet(fieldName)) {
			                	fieldName = Constants.REPORT_STANDARD_FILTER_RECEIVED_DATE_FROM_LABEL;
			                }
			                String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDate", new Object[]{fieldName, control.getValue()});
			                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
						}
		        	}
		        } 
		        else if (Constants.REPORT_CONTROL_RECEIVED_DATE_TO_OPT.equals(name)){
		        	if (Utility.isSet(control.getValue())){
		        		try {
		        			receivedDateTo = ClwI18nUtil.parseDateInp(request, control.getValue());
						} 
		        		catch (ParseException e) {
							log.error("Invalid Recieved Date To entered.");
			                String fieldName = ClwI18nUtil.getMessage(request, "userportal.esw.standardReports.receivedDateTo");
			                if (!Utility.isSet(fieldName)) {
			                	fieldName = Constants.REPORT_STANDARD_FILTER_RECEIVED_DATE_TO_LABEL;
			                }
			                String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDate", new Object[]{fieldName, control.getValue()});
			                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
						}
		        	}
		        } 
		        else if (Constants.REPORT_CONTROL_BEG_DATE.equals(name)){
		        	if (Utility.isSet(control.getValue())){
		        		try {
		        			if (Utility.isSet(repRequest.mDateFormat)) {
		        				beginDate = ClwI18nUtil.parseDateInp(repRequest.mDateFormat, control.getValue());
		        				control.setValue(ClwI18nUtil.formatDate(repRequest.mDateFormat, beginDate));
		        			}
		        			else {
		        				beginDate = ClwI18nUtil.parseDateInp(request, control.getValue());
		        				control.setValue(ClwI18nUtil.formatDate(request, beginDate));
		        			}
						} 
		        		catch (ParseException e) {
							log.error("Invalid Begin Date entered.");
			                String fieldName = ClwI18nUtil.getMessage(request, "report.text.BeginDate");
			                if (!Utility.isSet(fieldName)) {
			                	fieldName = Constants.REPORT_STANDARD_FILTER_BEGIN_DATE_LABEL;
			                }
			                String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDate", new Object[]{fieldName, control.getValue()});
			                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
						}
		        	} 
		        	else { 
		        		log.error("Begin Date is required field.");
		                String fieldName = ClwI18nUtil.getMessage(request, "report.text.BeginDate");
		                if (!Utility.isSet(fieldName)) {
		                	fieldName = Constants.REPORT_STANDARD_FILTER_BEGIN_DATE_LABEL;
		                }
		                String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.requiredField", new Object[]{fieldName});
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
			        }
		        } 
		        else if(Constants.REPORT_CONTROL_END_DATE.equals(name)){
		        	if (Utility.isSet(control.getValue())){
		        		try {
		        			if (Utility.isSet(repRequest.mDateFormat)) {
		        				endDate = ClwI18nUtil.parseDateInp(repRequest.mDateFormat, control.getValue());
		        				control.setValue(ClwI18nUtil.formatDate(repRequest.mDateFormat, endDate));
		        			}
		        			else {
		        				endDate = ClwI18nUtil.parseDateInp(request, control.getValue());
		        				control.setValue(ClwI18nUtil.formatDate(request, endDate));
		        			}
						} 
		        		catch (ParseException e) {
							log.error("Invalid End Date entered.");
			                String fieldName = ClwI18nUtil.getMessage(request, "report.text.EndDate");
			                if (!Utility.isSet(fieldName)) {
			                	fieldName = Constants.REPORT_STANDARD_FILTER_END_DATE_LABEL;
			                }
			                String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDate", new Object[]{fieldName, control.getValue()});
			                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
						}
		        	} 
		        	else { 
		        		log.error("End Date is required field.");
		                String fieldName = ClwI18nUtil.getMessage(request, "report.text.EndDate");
		                if (!Utility.isSet(fieldName)) {
		                	fieldName = Constants.REPORT_STANDARD_FILTER_BEGIN_DATE_LABEL;
		                }
		                String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.requiredField", new Object[]{fieldName});
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
			        }
		        }
    		 } // End of for loop

    		 if (beginDate != null && endDate != null) {
    			 if (endDate.compareTo(beginDate) < 0) {
    				 log.error("Invalid date range, 'Begin Date' must be before or equals to the 'End Date'.");
    			 	 String endDateStr = ClwI18nUtil.getMessage(request, "report.text.EndDate");
    			 	 String beginDateStr = ClwI18nUtil.getMessage(request, "report.text.BeginDate");
    			 	 if(!Utility.isSet(endDateStr)) {
    			 		endDateStr = Constants.REPORT_STANDARD_FILTER_END_DATE_LABEL;
	                 }
    			 	 if(!Utility.isSet(beginDateStr)) {
    			 		beginDateStr = Constants.REPORT_STANDARD_FILTER_BEGIN_DATE_LABEL;
	                 }
    			 	 String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDateRange", new Object[]{ beginDateStr,endDateStr});
    			 	 errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    			 }
    		 }
    		 if (actualStartDate != null && actualEndDate != null) {
    			 if (actualEndDate.compareTo(actualStartDate) < 0) {
    				 log.error("Invalid date range, 'Actual Begin Date' must be before or equals to the 'Actual End Date'.");
    			 	 String endDateStr = ClwI18nUtil.getMessage(request, "userportal.esw.standardReports.actualEndDate");
    			 	 String beginDateStr = ClwI18nUtil.getMessage(request, "userportal.esw.standardReports.actualStartDate");
    			 	 if (!Utility.isSet(endDateStr)) {
    			 		endDateStr = Constants.REPORT_STANDARD_FILTER_ACTUAL_END_DATE_LABEL;
	                 }
    			 	 if (!Utility.isSet(beginDateStr)) {
    			 		beginDateStr = Constants.REPORT_STANDARD_FILTER_ACTUAL_START_DATE_LABEL;
	                 }
    			 	 String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDateRange", new Object[]{ beginDateStr,endDateStr});
    			 	 errors.add("error", new ActionError("error.simpleGenericError", errorMess)); 
    			 }
    		 } 
    		 if (quotedStartDate != null && quotedEndDate != null) {
    			 if (quotedEndDate.compareTo(quotedStartDate) < 0) {
    				 log.error("Invalid date range, 'Quoted Begin Date' must be before or equals to the 'Quoted End Date'.");
    			 	 String endDateStr = ClwI18nUtil.getMessage(request, "userportal.esw.standardReports.estimatedEndDate");
    			 	 String beginDateStr = ClwI18nUtil.getMessage(request, "userportal.esw.standardReports.estimatedStartDate");
    			 	 if (!Utility.isSet(endDateStr)) {
    			 		endDateStr = Constants.REPORT_STANDARD_FILTER_ESTIMATED_END_DATE_LABEL;
	                 }
    			 	 if (!Utility.isSet(beginDateStr)) {
    			 		beginDateStr = Constants.REPORT_STANDARD_FILTER_ESTIMATED_START_DATE_LABEL;
	                 }
    			 	 String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDateRange", new Object[]{ beginDateStr,endDateStr});
    			 	 errors.add("error", new ActionError("error.simpleGenericError", errorMess)); 
    			 }
    		 }
    		 if (receivedDateFrom != null && receivedDateTo != null) {
    			 if (receivedDateTo.compareTo(receivedDateFrom) < 0) {
    				 log.error("Invalid date range, 'Received Date From' must be before or equals to the 'Received Date To'.");
    			 	 String endDateStr = ClwI18nUtil.getMessage(request, "userportal.esw.standardReports.receivedDateTo");
    			 	 String beginDateStr = ClwI18nUtil.getMessage(request, "userportal.esw.standardReports.receivedDateFrom");
    			 	 if (!Utility.isSet(endDateStr)) {
    			 		endDateStr = Constants.REPORT_STANDARD_FILTER_RECEIVED_DATE_TO_LABEL;
	                 }
    			 	 if (!Utility.isSet(beginDateStr)) {
    			 		beginDateStr = Constants.REPORT_STANDARD_FILTER_RECEIVED_DATE_FROM_LABEL;
	                 }
    			 	 String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDateRange", new Object[]{ beginDateStr,endDateStr});
    			 	 errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    			 }
    		 }
    	} // End of if
    	return errors;
    }
    
    public static List sortMfgs(HashMap pMfgMap, Locale pLocale){
    	List<LabelValueBean> mfgList = new ArrayList<LabelValueBean>();
    	Collator localeCollator = Collator.getInstance(pLocale);
    	
    	List<String> mfgNames = new ArrayList<String>(pMfgMap.keySet());
    	Collections.sort(mfgNames, localeCollator);

        Iterator mfgIt = mfgNames.iterator();
        while(mfgIt.hasNext()){
        	String mfgName = (String)mfgIt.next();
        	String mfgId = ((Integer)pMfgMap.get(mfgName)).toString();
        	LabelValueBean mfg = new LabelValueBean(mfgName, mfgId);
        	mfgList.add(mfg);
        }
       
        return mfgList;
    }
    private static HashMap<String,BigDecimal> getSortedCategoryAmountMap(HashMap<String,BigDecimal> categories){
		//HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		ValueComparator bvc = new ValueComparator(categories);
		TreeMap<String, BigDecimal> sorted_map = new TreeMap(bvc);
		sorted_map.putAll(categories);
		
		ArrayList  keys = new ArrayList(sorted_map.keySet());
		ArrayList values = new ArrayList(sorted_map.values());
		int maxCategories = Constants.MAX_CATEGORY_ITEM_PIE_PIECES;
		if(keys.size() > maxCategories) {
		BigDecimal otherAmount = new BigDecimal(0);
		
			for(int i = maxCategories-1; i < keys.size(); i++) {
				otherAmount = otherAmount.add((BigDecimal)sorted_map.get(keys.get(i)));
			}
		
		HashMap<String,BigDecimal> cats = new HashMap<String,BigDecimal>();
		for(int i = 0 ;i < maxCategories-1 ;i++) {
			cats.put((String)keys.get(i),(BigDecimal)values.get(i));
		}
		cats.put("0-Other", otherAmount);
    	return cats;
		}
		else{
			return categories;
		}
    }
    /*
     * method to retrieve site names from ids.
     */
    public static ArrayList<String> getSiteNameFromId(ArrayList siteIds){
    	ArrayList<String> siteNames = new ArrayList<String>();
    	IdVector siteIdVector = new IdVector();
    	SiteDataVector siteVector = null;
    	Iterator iterator = siteIds.iterator();
    	while(iterator.hasNext()){
    		siteIdVector.add((Integer)iterator.next());
    	}
    	try{
    		siteVector = APIAccess.getAPIAccess().getSiteAPI().getSiteCollection(siteIdVector);
    		iterator = siteVector.iterator();
        	while(iterator.hasNext()){
        		siteNames.add(((SiteData)iterator.next()).getBusEntity().getShortDesc());
        	}
    	}
    	catch(Exception e){
    		return siteNames;
    	}
    	return siteNames;
    }
  
    //STJ-4742
    /*
     * private method to find whether any site has spent amount in last period or year.
     */
    private static boolean isSpentForLastYearOrPeriod(int pSiteId,int pBudgetPeriod,int pBudgetYear,int pCcId) throws Exception{
   	 ArrayList<String> pOrderStatus = new ArrayList<String>();
        pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.ORDERED);
        pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);
        pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
        boolean hasSpent = false;
        Budget budgetBean = APIAccess.getAPIAccess().getBudgetAPI();
        BigDecimal ccAmount = budgetBean.getAllBudgetSpentForSiteCostCenter(pSiteId,pCcId,pBudgetPeriod, pBudgetYear, pOrderStatus);
		 if(ccAmount.compareTo(BigDecimal.ZERO) > 0){
			 return true;
		 }
        if(!hasSpent){
       	 pOrderStatus = new ArrayList<String>();
            pOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
            ccAmount = budgetBean.getAllBudgetSpentForSiteCostCenter(pSiteId,pCcId,pBudgetPeriod, pBudgetYear, pOrderStatus);
       	 if(ccAmount.compareTo(BigDecimal.ZERO) > 0){
           	return true;
       	 }
        }
        return false;
    }
    
    public static void downLoadCustomerFile(HttpServletRequest request, HttpServletResponse response,
    		ReportingForm reportingForm) {
    	CustAcctMgtReportForm customerReportingForm = reportingForm.getCustomerReportingForm();
    	GenericReportResultView reportResult = (GenericReportResultView) customerReportingForm.getReportResults().get(0);
    	if (reportResult.getName().equals(RefCodeNames.CUSTOMER_REPORT_TYPE_CD.INVOICE_LISTING)){ 
    		try {        	
        		CustAcctMgtReportLogic.downloadInvoicePDF(request,response,customerReportingForm.getReportResults(), customerReportingForm.getReportControls());
	    	} catch (Exception e){
	    		log.error("An Exception has occured while downloading the Invoice Listing Report");
	    	}
    	}
    }
    
    //STJ-5637
    /**
     * Calculates freight and handling amounts.
     * @param request
     * @param pForm
     * @param cartItems
     * @param orderFeeMap
     * @param orderFee
     * @return ActionErrors
     */
    public static ActionErrors calculateOrderFee(HttpServletRequest request, CheckoutForm pForm,
    		ShoppingCartItemDataVector cartItems, HashMap<String, BigDecimal> orderFeeMap, String orderFee){

    	ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
          ae.add("error", new ActionError("error.systemError", "No Ejb access"));
          return ae;
        }
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        
        ShoppingServices shoppingServEjb = null;
        try {
          shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
          ae.add("error", new ActionError("error.systemError", "No shopping services API Access"));
          return ae;
        }

        FreightTable freightTableEjb = null;
        try {
            freightTableEjb = factory.getFreightTableAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No freight table API Access"));
            return ae;
        }

        ShoppingCartData shoppingCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);

        Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
        BigDecimal freightAmt = new BigDecimal(0);
        BigDecimal handlingAmt = new BigDecimal(0);
        BigDecimal discountAmt = new BigDecimal(0);

        BigDecimal cartAmt = new BigDecimal(shoppingCartD.getItemsCost());

        cartAmt = cartAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        //pForm.setCartAmt(cartAmt);
        int contractId = 0;
        if (contractIdI != null) {
          contractId = contractIdI.intValue();
        }

        //Freight
        OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();

        for (int ii = 0; ii < cartItems.size(); ii++) {
          ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(ii);
          OrderHandlingItemView frItem = OrderHandlingItemView.createValue();
          frItem.setItemId(cartItem.getProduct().getProductId());
          BigDecimal priceBD = new BigDecimal(cartItem.getPrice());
          priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);
          frItem.setPrice(priceBD);
          frItem.setQty(cartItem.getQuantity());
          BigDecimal weight = null;
          String weightS = cartItem.getProduct().getShipWeight();
          try {
            weight = new BigDecimal(weightS);
          } catch (Exception exc) {}
          frItem.setWeight(weight);
          frItems.add(frItem);
        }
        OrderHandlingView frOrder = OrderHandlingView.createValue();
        frOrder.setTotalHandling(new BigDecimal(0));
        frOrder.setTotalFreight(new BigDecimal(0));
        frOrder.setContractId(contractId);
        int accountId = appUser.getSite().getAccountBusEntity().getBusEntityId();
        frOrder.setAccountId(accountId);
        frOrder.setSiteId(shoppingCartD.getSite().getBusEntity().getBusEntityId());
        //frOrder.setAmount(cartAmt);
        frOrder.setWeight(new BigDecimal(0));
        frOrder.setItems(frItems);

    	log.info("**********: frOrder = " + frOrder); 

        /// Create map to catch the proper 'Freight Table' for every distributor
        TreeMap<Integer, FreightTableData> distFreightTables =
            new TreeMap<Integer, FreightTableData>();
        /// Create map to catch the proper 'Discount Table' for every distributor
        TreeMap<Integer, FreightTableData> distDiscountTables =
            new TreeMap<Integer, FreightTableData>();

        /// Filling of the maps 'distFreightTables' and 'distDiscountTables' by distributors id
        if (cartItems != null && cartItems.size() > 0) {
            for (int i = 0; i < cartItems.size(); ++i) {
                ShoppingCartItemData item = (ShoppingCartItemData)cartItems.get(i);
                if (item == null) {
                    continue;
                }
                if (item.getProduct() == null) {
                    continue;
                }
                if (item.getProduct().getCatalogDistributor() == null) {
                    continue;
                }
                Integer distributorId =
                    new Integer(item.getProduct().getCatalogDistributor().getBusEntityId());
                distFreightTables.put(distributorId, null);
                distDiscountTables.put(distributorId, null);
            }
        }

        /// Calculation of Catalog Id
        int storeId = appUser.getUserStore().getStoreId();
        ContractData contractData = appUser.getSite().getContractData();
        int catalogId = 0;
        if (contractData != null) {
            catalogId = contractData.getCatalogId();
        }

        log.info("### [CheckoutLogic.init] storeId: " + storeId + ", catalogId: " + catalogId);

        /// Filling of the maps 'distFreightTables' and 'distDiscountTables' by 'Freight Tables' and by 'Discount Tables'
        try {
            calculateFreightTablesForDistributors(freightTableEjb, storeId, catalogId, distFreightTables);
            calculateDiscountTablesForDistributors(freightTableEjb, storeId, catalogId, distDiscountTables);
        } catch (Exception ex) {
            ae.add("error", new ActionError("error.systemError", ex.getMessage()));
            ex.printStackTrace();
            return ae;
        }

        /// Search any not empty 'Freight Table' in the map 'distFreightTables'
        boolean existsFreightTableForDistributors = false;
        if (distFreightTables.size() > 0) {
            Collection<FreightTableData> values = distFreightTables.values();
            Iterator<FreightTableData> iterator = values.iterator();
            while (iterator.hasNext()) {
                FreightTableData table = (FreightTableData)iterator.next();
                if (table != null) {
                    existsFreightTableForDistributors = true;
                    break;
                }
            }
        }
        /// Search any not empty 'Discount Table' in the map 'distDiscountTables'
        boolean existsDiscountTableForDistributors = false;
        if (distDiscountTables.size() > 0) {
            Collection<FreightTableData> values = distDiscountTables.values();
            Iterator<FreightTableData> iterator = values.iterator();
            while (iterator.hasNext()) {
                FreightTableData table = (FreightTableData)iterator.next();
                if (table != null) {
                    existsDiscountTableForDistributors = true;
                    break;
                }
            }
        }

        boolean useCatalogForCharges = true;
        if (existsFreightTableForDistributors || existsDiscountTableForDistributors) {
            useCatalogForCharges = false;
        }


        if (useCatalogForCharges) {
            try {
                frOrder = shoppingServEjb.calcTotalFreightAndHandlingAmount(frOrder);
                freightAmt = frOrder.getTotalFreight();
                handlingAmt = frOrder.getTotalHandling();
                discountAmt = shoppingServEjb.getDiscountAmt(contractId, cartAmt, frOrder); 
            } catch (RemoteException exc) {
                ae.add("error", new ActionError("error.systemError", exc.getMessage()));
                exc.printStackTrace();
                return ae;
            }
            discountAmt = discountAmt.setScale(2, BigDecimal.ROUND_HALF_UP); 
            log.info("***************: discountAmt from the DB = " + discountAmt);
            BigDecimal zeroValue = new BigDecimal(0);

            if ( discountAmt.compareTo(zeroValue)>0 ) {
                discountAmt = discountAmt.negate();
            }
            
            log.info("***************: discountAmt screen = " + discountAmt);

            freightAmt = freightAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
            log.info("***************: freightAmt = " + freightAmt);

            handlingAmt = handlingAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
            log.info("***************: handlingAmt screen = " + handlingAmt);
            
            orderFeeMap.put(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT, freightAmt);
            orderFeeMap.put("Handling", handlingAmt);
            orderFeeMap.put(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT, discountAmt);

           }
        ShoppingCartDistDataVector cartDistributorV = new ShoppingCartDistDataVector(
        		cartItems, storeId, distFreightTables, distDiscountTables);
        
        if(orderFee != null) {
        	BigDecimal selectedOrderFee = calculateSelectedOrderFee(cartDistributorV, orderFee);
        	orderFeeMap.put("selectedOrderFee", selectedOrderFee);
        	return ae;
        }
         
       if (!useCatalogForCharges) {
            if (cartDistributorV != null) {
                for (int i = 0; i < cartDistributorV.size(); ++i) {
                    ShoppingCartDistData cartDistData = (ShoppingCartDistData)cartDistributorV.get(i);
                    if (cartDistData != null) {
                        freightAmt = freightAmt.add(new BigDecimal(cartDistData.getDistFreightCost()));
                        handlingAmt = handlingAmt.add(new BigDecimal(cartDistData.getDistHandlingCost()));
                        double discount = cartDistData.getDistImpliedDiscountCost();
                        if (discount > 0.0) {
                            discountAmt = discountAmt.add(new BigDecimal(-discount));
                        } else {
                            discountAmt = discountAmt.add(new BigDecimal(discount));
                        }
                    }
                }
            }

            discountAmt = discountAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
            freightAmt = freightAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
            handlingAmt = handlingAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
            
            orderFeeMap.put(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT, freightAmt);
            orderFeeMap.put("Handling", handlingAmt);
            orderFeeMap.put(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT, discountAmt);
        }
        return ae;
    }
    
    /*
     * calculates selectable Freight/Handling.
     */
    //STJ-5637 In checkout page, we need to include order fee(freight, handling, discount, and tax)
    private static BigDecimal calculateSelectedOrderFee(ShoppingCartDistDataVector cartDistributors, String orderFee) {
    	
    	BigDecimal totalFreight = new BigDecimal(0);
        BigDecimal totalHandling = new BigDecimal(0);
        
        OrderFreightDataVector orderFreightList = null;
        for (int i = 0; cartDistributors != null && i < cartDistributors.size(); i++) {
    	    ShoppingCartDistData cartDistributor = (ShoppingCartDistData) cartDistributors.get(i);
    	    if(Utility.isSet(cartDistributor.getDistFreightOptions()) && cartDistributor.getDistFreightOptions().size() > 0) {
    	        break;
    	    }
    	}
        cartDistributors.setDistFreightVendor(new String[]{orderFee});
		if (null != cartDistributors && 0 < cartDistributors.size()) {
			orderFreightList = new OrderFreightDataVector();
			for (int i = 0; i < cartDistributors.size(); i++) {
				ShoppingCartDistData distD = (ShoppingCartDistData) cartDistributors
						.get(i);
				List impliedFreight = null;
				FreightTableCriteriaData selectedFreight = null;
				if (null != distD) {
					impliedFreight = distD.getDistFreightImplied();
					selectedFreight = distD.getDistSelectedFreight();
				}
				if (null != impliedFreight && 0 < impliedFreight.size()) {
					for (int j = 0; j < impliedFreight.size(); j++) {
						FreightTableCriteriaData crit = (FreightTableCriteriaData) impliedFreight
								.get(j);
						OrderFreightData orderFreightD = OrderFreightData
								.createValue();
						orderFreightD.setBusEntityId(distD.getDistributor()
								.getBusEntityId());
						orderFreightD.setFreightTypeCd(crit
								.getFreightCriteriaTypeCd());
						orderFreightD.setShortDesc(crit.getShortDesc());
						crit.getFreightCriteriaTypeCd();
						orderFreightD
								.setAmount(crit.getFreightAmount() != null ? crit
										.getFreightAmount() : crit
										.getHandlingAmount());
						orderFreightD.setFreightHandlerId(crit
								.getFreightHandlerId());
						orderFreightList.add(orderFreightD);
					}
				}
				if (null != selectedFreight) {
					OrderFreightData orderFreightD = OrderFreightData
							.createValue();
					orderFreightD.setBusEntityId(distD.getDistributor()
							.getBusEntityId());
					orderFreightD.setFreightTypeCd(selectedFreight
							.getFreightCriteriaTypeCd());
					orderFreightD.setShortDesc(selectedFreight.getShortDesc());
					orderFreightD
							.setAmount(selectedFreight.getFreightAmount() != null ? selectedFreight
									.getFreightAmount() : selectedFreight
									.getHandlingAmount());
					orderFreightD.setFreightHandlerId(selectedFreight
							.getFreightHandlerId());
					orderFreightList.add(orderFreightD);

					if (selectedFreight.getFreightAmount() != null) {
						totalFreight = Utility.addAmt(totalFreight,
								selectedFreight.getFreightAmount());
					}
					if (selectedFreight.getHandlingAmount() != null) {
						totalHandling = Utility.addAmt(totalHandling,
								selectedFreight.getHandlingAmount());
					}
				}
			}
		}
		BigDecimal orderFees = new BigDecimal(0);
		if(totalFreight != null) {
			orderFees = Utility.addAmt(orderFees, totalFreight);
		}
		if(totalHandling != null) {
			orderFees = Utility.addAmt(orderFees, totalHandling);
		}
		
		return orderFees;
    }
    
    /*
     * gets freight table by distributor and catalog
     */
    private static void calculateFreightTablesForDistributors(FreightTable freightTableEjb,
            int storeId, int catalogId, TreeMap<Integer, FreightTableData> distFreightTables) throws RemoteException {
            if (distFreightTables == null) {
                return;
            }
            if (distFreightTables.isEmpty()) {
                return;
            }
            Set<Integer> keys = distFreightTables.keySet();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                Integer key = (Integer)iterator.next();
                FreightTableData freightTable = freightTableEjb.getFreightTableByDistributorAndCatalog(storeId, catalogId, key.intValue());
                distFreightTables.put(key, freightTable);
            }
        }

    /*
     * gets discount table by distributor and catalog
     */
    private static void calculateDiscountTablesForDistributors(FreightTable freightTableEjb,
        int storeId, int catalogId, TreeMap<Integer, FreightTableData> distDiscountTables) throws Exception {
        if (distDiscountTables == null) {
            return;
        }
        if (distDiscountTables.isEmpty()) {
            return;
        }
        Set<Integer> keys = distDiscountTables.keySet();
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            Integer key = (Integer)iterator.next();
            FreightTableData freightTable = freightTableEjb.getDiscountTableByDistributorAndCatalog(storeId, catalogId, key.intValue());
            distDiscountTables.put(key, freightTable);
        }
    }
}
/**
 * Implementation of logic that sorts category key- amount map based on amount.
 */
    class ValueComparator implements Comparator {
    	Map base;

    	public ValueComparator(Map base) {
    		this.base = base;
    	}

    	public int compare(Object a, Object b) {
    		if (((BigDecimal) base.get(a)).compareTo((BigDecimal) base.get(b)) > 0) {
    			return -1;
    		} else if (((BigDecimal) base.get(a)).compareTo((BigDecimal) base.get(b)) < 0) {
    			return 1;
    		} else {
    			return 0;
    		}
    	}
    }


package com.cleanwise.view.logic.esw;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dto.LocationBudgetChartDto;
import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.dto.ReportingDto;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Budget;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.BudgetSpendView;
import com.cleanwise.service.api.value.BudgetSpendViewVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.FiscalCalendarInfo;
import com.cleanwise.service.api.value.FiscalCalendarInfo.BudgetPeriodInfo;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.SiteMgrLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.DashboardForm;
import com.espendwise.view.logic.esw.ReportingLogic;

public class SiteLocationBudgetLogic extends SiteMgrLogic{
	private static final Logger log = Logger.getLogger(SiteLocationBudgetLogic.class);
	 /**
     * <code>prepareLocationBudgetTotal</code> prepares all the data needed to prepare location budget chart, 
     * for example ,allocated budget,spent amount,pending approval amount, 
     * shopping cart amount,over budget amount,unused amount
     * @param request
     * @param form
     * return  ActionErrors
     */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public static ActionErrors prepareLocationBudgetTotal(HttpServletRequest request,ActionForm form)
   {
		DashboardForm dashboardForm = (DashboardForm) form;
		CleanwiseUser appUser = (CleanwiseUser) request.getSession()
				.getAttribute(Constants.APP_USER);
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		/*String showChart = sessionDataUtil.getShowChart();
		
		if(showChart==null) // for the first time we will be showing total budget bar 
		{
			sessionDataUtil.setShowChart(Constants.LOCATION_BUDGET_HIDE_COST_CENTER);
		}*/
		SiteData site = appUser.getSite();
		if(site==null)//if there is no default site ,and if there is only one site use it 
		{
			//LocationSearchDto locationSearchDto = sessionDataUtil.getLocationSearchDto();
			LocationSearchDto locationSearchDto = sessionDataUtil.getLocationSearchDtoMap().get(Constants.CHANGE_LOCATION);
			 if (locationSearchDto != null) {
				 if (Utility.isSet(locationSearchDto.getMatchingLocations())) {
					 if (locationSearchDto.getMatchingLocations().size() == 1) {
						 site =(SiteData)locationSearchDto.getMatchingLocations().get(0);
					 }
				 }
			 }
		}
		ActionErrors errors = getLocationBudgetDtoForSite(request, dashboardForm, site, appUser);
		if (errors != null) {
			sessionDataUtil.setLocationBudgetChartDto(dashboardForm.getLocationBudgetChartDto());
		}
		return errors;
	}
	
	public static ActionErrors getLocationBudgetDtoForSite(HttpServletRequest request, DashboardForm dashboardForm, 
			SiteData site, CleanwiseUser appUser) {
		ActionErrors errors = updateLocationBudgetChart(request,site,appUser);
		//Get LocationBudgetChart from the session and set it in the Dashboard form.
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		dashboardForm.setLocationBudgetChartDto(sessionDataUtil.getLocationBudgetChartDto());
		
		return errors;
	}
	
	/**
	 * Updates Location Budget Chart Info and put into the session.
	 * @param request - the <code>HttpServletRequest</code> currently being handled.
	 * @param site - needed <SiteData> object.
	 * @param appUser - logged in <CleanwiseUser> object.
	 * @return - An <code>ActionErrors</code> object containing any errors.
	 */
	public static ActionErrors updateLocationBudgetChart(HttpServletRequest request,SiteData site, CleanwiseUser appUser) {
		return updateLocationBudgetChart(request, site, appUser, null, null);
	}
	
	/**
	 * Updates Location Budget Chart Info and put into the session.
	 * @param request - the <code>HttpServletRequest</code> currently being handled.
	 * @param site - needed <SiteData> object.
	 * @param appUser - logged in <CleanwiseUser> object.
	 * @return - An <code>ActionErrors</code> object containing any errors.
	 */
	public static ActionErrors updateLocationBudgetChart(HttpServletRequest request,SiteData site, CleanwiseUser appUser, 
			CheckoutForm checkoutForm, String orderFee) {
		ActionErrors errors = new ActionErrors();
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		String showChart = sessionDataUtil.getShowChart();
		if(showChart==null) // for the first time we will be showing total budget bar 
		{
			sessionDataUtil.setShowChart(Constants.LOCATION_BUDGET_HIDE_COST_CENTER);
		}
		String startPeriodMmDD = "";
		String endPeriodMmDD = "";
		BudgetPeriodInfo startPeriod = null;
		BudgetPeriodInfo endPeriod = null;
		BudgetPeriodInfo currentPeriod = null;
		
		try {
			if(site != null && ShopTool.doesAccountSiteSupportsBudgets(request)){
			int accountId = site.getAccountId();
			Account account = APIAccess.getAPIAccess().getAccountAPI();
			FiscalCalenderView fiscalCalenderView = account.getCurrentFiscalCalenderV(accountId);
			FiscalCalendarInfo fiscalCalendarInfo = new FiscalCalendarInfo(fiscalCalenderView);
			HashMap budgetPeriodDatesMap = fiscalCalendarInfo.getBudgetPeriodsAsHashMap();
			int currentBudgetPeriod = site.getCurrentBudgetPeriod();
			//AccountData accountData = account.getAccountForSite(site.getSiteId());
			AccountData accountData =  appUser.getUserAccount();
			String budgetType = accountData.getBudgetTypeCd();
			ReportingDto reportingDto = new ReportingDto();
			reportingDto.setLocationSelected(Constants.ORDERS_CURRENT_LOCATION);
			if (budgetType != null
					&& budgetType.equalsIgnoreCase(RefCodeNames.BUDGET_ACCRUAL_TYPE_CD.BY_FISCAL_YEAR)) {
				reportingDto.setFiscalPeriod(Constants.REPORTING_FILTER_THIS_FISCAL_YEAR);// if account's budget type is fiscal year.
			} else {
				reportingDto.setFiscalPeriod(""); // if account's budget type is BY-PERIOD.
			}
			errors = ReportingLogic.showBudgetsReport(request, reportingDto, site, true, checkoutForm, orderFee);
			if(errors != null && errors.size() > 0){
				log.error("Unexpected exception in ReportingLogic: ");
				String errorMess = ClwI18nUtil.getMessage(request,"location.budget.errors.noChartData", null);
				errors.add("error", new ActionError("error.simpleGenericError",errorMess));
				LocationBudgetChartDto locationBudgetDto = new LocationBudgetChartDto();
				locationBudgetDto.setAccountHasBudget(false);
				sessionDataUtil.setLocationBudgetChartDto(locationBudgetDto);
				return errors;
			}
			reportingDto.getBudgetChart().setSiteName(site.getBusEntity().getShortDesc());
			if (budgetPeriodDatesMap != null) {
				if (budgetType != null
						&& budgetType.equalsIgnoreCase(RefCodeNames.BUDGET_ACCRUAL_TYPE_CD.BY_FISCAL_YEAR)) {
					startPeriod = (BudgetPeriodInfo) budgetPeriodDatesMap.get(1);
					startPeriodMmDD = ClwI18nUtil.formatDateInp(request,startPeriod.getStartDate());
					endPeriod = (BudgetPeriodInfo) budgetPeriodDatesMap.get(currentBudgetPeriod);
					endPeriodMmDD = ClwI18nUtil.formatDateInp(request,endPeriod.getEndDate());
					reportingDto.getBudgetChart().setBudgetPeriod(startPeriodMmDD + " - " + endPeriodMmDD);
				} else {
					currentPeriod = (BudgetPeriodInfo) budgetPeriodDatesMap.get(currentBudgetPeriod);
					startPeriodMmDD = ClwI18nUtil.formatDateInp(request,currentPeriod.getStartDate());
					endPeriodMmDD = ClwI18nUtil.formatDateInp(request,currentPeriod.getEndDate());
					reportingDto.getBudgetChart().setBudgetPeriod(startPeriodMmDD + " - " + endPeriodMmDD);
				}
			}
			sessionDataUtil.setLocationBudgetChartDto(reportingDto.getBudgetChart());
			}
			else{
				LocationBudgetChartDto locationBudgetDto = new LocationBudgetChartDto();
				locationBudgetDto.setAccountHasBudget(false);
				sessionDataUtil.setLocationBudgetChartDto(locationBudgetDto);
			}
		} catch (Exception e) {
			log.error("Unexpected exception in SiteLocationBudgetLogic: ", e);
			String errorMess = ClwI18nUtil.getMessage(request,"location.budget.errors.noChartData", null);
			errors.add("error", new ActionError("error.simpleGenericError",errorMess));
		}

		return errors;
	}
}

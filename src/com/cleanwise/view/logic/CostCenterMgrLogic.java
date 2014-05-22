/*
package com.cleanwise.view.logic;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;

import com.cleanwise.view.forms.AccountMgrDetailForm;
import com.cleanwise.view.forms.CostCenterMgrDetailForm;
import com.cleanwise.view.forms.CostCenterMgrSearchForm;
import com.cleanwise.view.forms.SiteBudget;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.view.utils.DisplayListSort;

import java.math.BigDecimal;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


*/
/**
 *  <code>CostCenterMgrLogic</code> implements the logic needed to manipulate
 *  purchasing cost center records.
 *
 *@author     tbesser
 *@created    August 27, 2001
 */
/*
public class CostCenterMgrLogic {
    private static void getSiteBudgets(int accountId, int costCenterId,
                                       CostCenterMgrDetailForm sForm,
                                       String pOptionalSiteId, HttpServletRequest pRequest)
                                throws Exception {

        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        SiteDataVector sd = null;

        if (null != pOptionalSiteId) {

            SiteData thisSite = siteBean.getSite(Integer.parseInt(
                                                         pOptionalSiteId),
                                                 accountId);
            sd = new SiteDataVector();
            sd.add(thisSite);
        } else {

            sd = new SiteDataVector();

            // first, get the site view vector from the site mgr logic
            SiteViewVector dv = SiteMgrLogic.search(pRequest, sForm.getSearchField(), sForm.getSearchType(), Integer.toString(accountId),
                    sForm.getCity(), sForm.getCounty(), sForm.getState(), sForm.getPostalCode());

            //now that we have SiteViewVector dv, fill sd
            for (int ii = 0; ii < dv.size(); ii++) {

                if (((SiteView)dv.get(ii)).getAccountId() == accountId)
                    sd.add(siteBean.getSite(((SiteView)dv.get(ii)).getId()));
            }
        }

        BigDecimal budgetTotal = new BigDecimal(0);
        ArrayList budgets = new ArrayList();
        Iterator siteIter = sd.iterator();

        while (siteIter.hasNext()) {

            SiteBudget budgetInfo = new SiteBudget();
            SiteData site = (SiteData)siteIter.next();
            budgetInfo.setId(String.valueOf(site.getBusEntity().getBusEntityId()));
            budgetInfo.setName(site.getBusEntity().getShortDesc());

            AddressData address = site.getSiteAddress();
            budgetInfo.setCity(address.getCity());
            budgetInfo.setState(address.getStateProvinceCd());

            BudgetData budgetData = null;
            BudgetDataVector budgetv = site.getBudgets();
            Iterator budgetIter = budgetv.iterator();

            while (budgetIter.hasNext()) {

                BudgetData budget = (BudgetData)budgetIter.next();

                if (budget.getCostCenterId() == costCenterId) {

                    // Calculate the remaining budget amount.
                    BudgetSpendView spent = siteBean.getBudgetSpent
			(site.getBusEntity().getBusEntityId(),
			 costCenterId);
                    budgetData = budget;
                    budgetInfo.setSiteBudgetRemaining(spent.getAmountSpent());

                    break;
                }
            }

            // if it doesn't yet have an explicit budget, it's zero
            if (budgetData == null) {
                budgetData = BudgetData.createValue();
                budgetData.setBusEntityId(site.getBusEntity().getBusEntityId());
                budgetData.setCostCenterId(costCenterId);

                BigDecimal zero = new BigDecimal(0);
                budgetInfo.setSiteBudgetRemaining(zero);
            }

            budgetInfo.setBudgetData(budgetData);
            budgets.add(budgetInfo);
        }

        sForm.setSiteBudgetList(budgets);
        sForm.setBudgetTotal(CurrencyFormat.format(budgetTotal));
    }

    private static void getSiteBudgets(int accountId, int costCenterId,
                                       CostCenterMgrDetailForm sForm,
                                       String pOptionalSiteId)
                                throws Exception {

        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        SiteDataVector sd = null;

        if (null != pOptionalSiteId) {

            SiteData thisSite = siteBean.getSite
                (Integer.parseInt(pOptionalSiteId),accountId);
            sd = new SiteDataVector();
            sd.add(thisSite);
        } else {
            sd = siteBean.getAllSites(accountId, Site.ORDER_BY_NAME);
        }

        BigDecimal budgetTotal = new BigDecimal(0);
        ArrayList budgets = new ArrayList();
        Iterator siteIter = sd.iterator();

        while (siteIter.hasNext()) {

            SiteBudget budgetInfo = new SiteBudget();
            SiteData site = (SiteData)siteIter.next();
            budgetInfo.setId(String.valueOf(site.getBusEntity().getBusEntityId()));
            budgetInfo.setName(site.getBusEntity().getShortDesc());

            AddressData address = site.getSiteAddress();
            budgetInfo.setCity(address.getCity());
            budgetInfo.setState(address.getStateProvinceCd());

            BudgetData budgetData = null;
            BudgetDataVector budgetv = site.getBudgets();
            Iterator budgetIter = budgetv.iterator();

            while (budgetIter.hasNext()) {

                BudgetData budget = (BudgetData)budgetIter.next();

                if (budget.getCostCenterId() == costCenterId) {

                    BudgetSpendView spent = siteBean.getBudgetSpent
			(site.getBusEntity().getBusEntityId(),
			 costCenterId);
                    String budgetStr = spent.getAmountAllocated().toString();

                    budgetData = budget;
                    budgetInfo.setSiteBudgetRemaining(spent.getAmountSpent());

                    break;
                }
            }

            // if it doesn't yet have an explicit budget, it's zero
            if (budgetData == null) {
                budgetData = BudgetData.createValue();
                budgetData.setBusEntityId(site.getBusEntity().getBusEntityId());
                budgetData.setCostCenterId(costCenterId);

                BigDecimal zero = new BigDecimal(0);
                budgetInfo.setSiteBudgetRemaining(zero);
            }

            budgetInfo.setBudgetData(budgetData);
            budgets.add(budgetInfo);
        }

        sForm.setSiteBudgetList(budgets);
        sForm.setBudgetTotal(CurrencyFormat.format(budgetTotal));
    }

    */
/**
     *  <code>getAll</code> cost centers, note that the CostCenter list
     *  returned will be limited to a certain amount of records. It is
     *  up to the jsp page to detect this and to issued a subsequent call
     *  to get more records.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
/*
    public static void getAll(HttpServletRequest request, ActionForm form)
                       throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        initFormVectors(request);

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        String id = (String)session.getAttribute("Account.id");
        int accountId = Integer.parseInt(id);
        CostCenterDataVector dv = accountBean.getAllCostCenters
            (accountId,
             Account.ORDER_BY_NAME);
        session.setAttribute("CostCenter.found.vector", dv);
        session.setAttribute("CostCenter.found.total",
                             String.valueOf(dv.size()));

        CategoryToCostCenterViewVector ctoccv = new CategoryToCostCenterViewVector();
        session.setAttribute("CategoryToCostCenter.found.vector", ctoccv);
        ctoccv = accountBean.getAllCategoryToCostCenters(accountId);
        session.setAttribute("CategoryToCostCenter.found.vector", ctoccv);
        ArrayList itoccv = accountBean.getAllItemToCostCenters(accountId);
        session.setAttribute("ItemToCostCenter.found.vector", itoccv);


	FiscalPeriodView fpv = null;
	try {
	    fpv = accountBean.getFiscalInfo(accountId);
	}
	catch (Exception e) {
	    fpv = FiscalPeriodView.createValue();
	    if ( null == fpv.getFiscalCalenderData() ) {
		fpv.setFiscalCalenderData(FiscalCalenderData.createValue());
		fpv.getFiscalCalenderData().setPeriodCd("unset");
	    }
	}
	session.setAttribute("Account.fiscalInfo", fpv);

    }

    public static void resetCostCenters(HttpServletRequest request,
                                        ActionForm form)
        throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        initFormVectors(request);

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        String id = (String)session.getAttribute("Account.id");
        int accountId = Integer.parseInt(id);
        accountBean.resetCostCenters(accountId);
        getAll(request, form);
    }

    */
/**
     *  <code>getAll</code> Sites
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
/*
    public static void getAllSites(HttpServletRequest request, ActionForm form)
                            throws Exception {

        HttpSession session = request.getSession();
        initFormVectors(request);

        CostCenterMgrDetailForm sForm = (CostCenterMgrDetailForm)session.getAttribute(
                                                "COST_CENTER_DETAIL_FORM");

        if (sForm == null) {
            sForm = new CostCenterMgrDetailForm();
            session.setAttribute("COST_CENTER_DETAIL_FORM", sForm);
        }

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        String fieldValue = sForm.getId();

        if (null == fieldValue) {
            fieldValue = "0";
        }

        String siteidValue = request.getParameter("siteId");
        int costcenterid = Integer.parseInt(fieldValue);
        int accountid = Integer.parseInt(
                                (String)session.getAttribute("Account.id"));
        CostCenterData dd = accountBean.getCostCenter(costcenterid, accountid);
        FiscalPeriodView fpv =
            accountBean.getFiscalInfo(accountid);
        FiscalCalenderData fc = fpv.getFiscalCalenderData();

        if (null != dd) {

            // Set the form values.
            sForm.setName(dd.getShortDesc());
            sForm.setStatusCd(dd.getCostCenterStatusCd());
            sForm.setId(String.valueOf(dd.getCostCenterId()));
            //sForm.setBudgetAmount(CurrencyFormat.format(dd.getBudgetAmount()));
            if ( fc != null )
            {
                sForm.setBudgetPeriod(fc.getPeriodCd());
                sForm.setBudgetMMDD1(fc.getMmdd1());
                sForm.setBudgetMMDD2(fc.getMmdd2());
                sForm.setBudgetMMDD3(fc.getMmdd3());
                sForm.setBudgetMMDD4(fc.getMmdd4());
                sForm.setBudgetMMDD5(fc.getMmdd5());
                sForm.setBudgetMMDD6(fc.getMmdd6());
                sForm.setBudgetMMDD7(fc.getMmdd7());
                sForm.setBudgetMMDD8(fc.getMmdd8());
                sForm.setBudgetMMDD9(fc.getMmdd9());
                sForm.setBudgetMMDD10(fc.getMmdd10());
                sForm.setBudgetMMDD11(fc.getMmdd11());
                sForm.setBudgetMMDD12(fc.getMmdd12());
                sForm.setBudgetMMDD13(fc.getMmdd13());
            }
            getSiteBudgets(accountid, costcenterid, sForm, siteidValue);
        }
    }

    */
/**
     *formats the big decimal for displaying in the budgeting screen
     */
/*
    private static String formatBigDecimal(BigDecimal pBigDecimal){
        if(pBigDecimal == null){
            return "";
        }
        return pBigDecimal.toString();
    }

    */
/**
     *  Describe <code>getDetail</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
/*
    public static void getDetail(HttpServletRequest request, ActionForm form)
                          throws Exception {

        HttpSession session = request.getSession();
        initFormVectors(request);

        CostCenterMgrDetailForm sForm = (CostCenterMgrDetailForm)session.getAttribute(
                                                "COST_CENTER_DETAIL_FORM");

        if (sForm == null) {
            sForm = new CostCenterMgrDetailForm();
            session.setAttribute("COST_CENTER_DETAIL_FORM", sForm);
        }

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        Budget budBean = factory.getBudgetAPI();
        String fieldValue = request.getParameter("searchField");

        if (null == fieldValue) {
            fieldValue = "0";
        }

        String siteidValue = request.getParameter("siteId");
        int costcenterid = Integer.parseInt(fieldValue);
        int accountid = Integer.parseInt(
                                (String)session.getAttribute("Account.id"));
        CostCenterData dd = accountBean.getCostCenter(costcenterid, accountid);
        FiscalPeriodView fpv = accountBean.getFiscalInfo(accountid);
        FiscalCalenderData fc = fpv.getFiscalCalenderData();

        if (null != dd) {

            // Set the form values.
            sForm.setName(dd.getShortDesc());
            sForm.setStatusCd(dd.getCostCenterStatusCd());
            sForm.setId(String.valueOf(dd.getCostCenterId()));
            //sForm.setBudgetAmount(CurrencyFormat.format(dd.getBudgetAmount()));
            sForm.setCostCenterTypeCd(dd.getCostCenterTypeCd());

            //fetch the budget information for this account
            if(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET.equals(dd.getCostCenterTypeCd())){
                BudgetData theBudget = budBean.fetchBusEntityBudget(accountid, dd.getCostCenterId());
                sForm.setBudget(theBudget);
                if(theBudget != null){
                    //setup the form
                    sForm.setBudgetAmount1(formatBigDecimal(theBudget.getAmount1()));
                    sForm.setBudgetAmount2(formatBigDecimal(theBudget.getAmount2()));
                    sForm.setBudgetAmount3(formatBigDecimal(theBudget.getAmount3()));
                    sForm.setBudgetAmount4(formatBigDecimal(theBudget.getAmount4()));
                    sForm.setBudgetAmount5(formatBigDecimal(theBudget.getAmount5()));
                    sForm.setBudgetAmount6(formatBigDecimal(theBudget.getAmount6()));
                    sForm.setBudgetAmount7(formatBigDecimal(theBudget.getAmount7()));
                    sForm.setBudgetAmount8(formatBigDecimal(theBudget.getAmount8()));
                    sForm.setBudgetAmount9(formatBigDecimal(theBudget.getAmount9()));
                    sForm.setBudgetAmount10(formatBigDecimal(theBudget.getAmount10()));
                    sForm.setBudgetAmount11(formatBigDecimal(theBudget.getAmount11()));
                    sForm.setBudgetAmount12(formatBigDecimal(theBudget.getAmount12()));
                    sForm.setBudgetAmount13(formatBigDecimal(theBudget.getAmount13()));
                }
            }

            if ( fc != null )
            {
                sForm.setBudgetPeriod(fc.getPeriodCd());
                sForm.setBudgetMMDD1(fc.getMmdd1());
                sForm.setBudgetMMDD2(fc.getMmdd2());
                sForm.setBudgetMMDD3(fc.getMmdd3());
                sForm.setBudgetMMDD4(fc.getMmdd4());
                sForm.setBudgetMMDD5(fc.getMmdd5());
                sForm.setBudgetMMDD6(fc.getMmdd6());
                sForm.setBudgetMMDD7(fc.getMmdd7());
                sForm.setBudgetMMDD8(fc.getMmdd8());
                sForm.setBudgetMMDD9(fc.getMmdd9());
                sForm.setBudgetMMDD10(fc.getMmdd10());
                sForm.setBudgetMMDD11(fc.getMmdd11());
                sForm.setBudgetMMDD12(fc.getMmdd12());
                sForm.setBudgetMMDD13(fc.getMmdd13());
            }

        }
    }

    */
/**
     *  <code>init</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
/*
    public static void init(HttpServletRequest request, ActionForm form)
                     throws Exception {
        initFormVectors(request);

        // Get all the cost centers for an account.
        getAll(request, form);

        return;
    }

    public static void initFormVectors(HttpServletRequest request)
                                throws Exception {

        HttpSession session = request.getSession();

        // Cache the lists needed for CostCenters.
        APIAccess factory = new APIAccess();
        ListService lsvc = factory.getListServiceAPI();

        // Set up the site status list.
        if (null == session.getAttribute("Account.status.vector")) {

            RefCdDataVector statusv = lsvc.getRefCodesCollection(
                                              "BUS_ENTITY_STATUS_CD");
            session.setAttribute("Account.status.vector", statusv);
        }

        // Set up the budget period list.
        if (null == session.getAttribute("CostCenter.period.vector")) {

            RefCdDataVector periodv = lsvc.getRefCodesCollection(
                                              "BUDGET_PERIOD_CD");
            session.setAttribute("CostCenter.period.vector", periodv);
        }

        if (null == session.getAttribute("Budget.type.cd")) {

            RefCdDataVector periodv = lsvc.getRefCodesCollection(
                                              "BUDGET_TYPE_CD");
            session.setAttribute("Budget.type.cd", periodv);
        }

    }

    */
/**
     *  <code>search</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
/*
    public static void search(HttpServletRequest request, ActionForm form)
                       throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        CostCenterMgrSearchForm sForm = (CostCenterMgrSearchForm)form;
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();

        // Reset the session variables.
        CostCenterDataVector dv = new CostCenterDataVector();
        session.setAttribute("CostCenter.found.vector", dv);
        session.setAttribute("CostCenter.found.total", "0");

        String fieldValue = sForm.getSearchField();
        String fieldSearchType = sForm.getSearchType();
        int accountid = Integer.parseInt(
                                (String)session.getAttribute("Account.id"));

        if (fieldSearchType.equals("id")) {

            Integer id = new Integer(fieldValue);
            CostCenterData dd = accountBean.getCostCenter(id.intValue(),
                                                          accountid);

            if (null != dd) {
                dv.add(dd);
            }
        } else if (fieldSearchType.equals("nameBegins")) {
            dv = accountBean.getCostCenterByName(fieldValue, accountid,
                                                 Account.BEGINS_WITH_IGNORE_CASE,
                                                 Account.ORDER_BY_NAME);
        } else if (fieldSearchType.equals("nameContains")) {
            dv = accountBean.getCostCenterByName(fieldValue, accountid,
                                                 Account.CONTAINS_IGNORE_CASE,
                                                 Account.ORDER_BY_NAME);
        } else {
            dv = accountBean.getAllCostCenters(accountid,
                                               Account.ORDER_BY_NAME);
        }

        session.setAttribute("CostCenter.found.vector", dv);
        session.setAttribute("CostCenter.found.total",
                             String.valueOf(dv.size()));
    }

    */
/**
     *  <code>search</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
/*
    public static void searchSites(HttpServletRequest request, ActionForm form)
                            throws Exception {

        HttpSession session = request.getSession();
        initFormVectors(request);

        CostCenterMgrDetailForm sForm = (CostCenterMgrDetailForm)session.getAttribute(
                                                "COST_CENTER_DETAIL_FORM");

        if (sForm == null) {
            sForm = new CostCenterMgrDetailForm();
            session.setAttribute("COST_CENTER_DETAIL_FORM", sForm);
        }

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        String fieldValue = sForm.getId();

        if (null == fieldValue) {
            fieldValue = "0";
        }

        String siteidValue = request.getParameter("siteId");
        int costcenterid = Integer.parseInt(fieldValue);
        int accountid = Integer.parseInt(
                                (String)session.getAttribute("Account.id"));
        CostCenterData dd = accountBean.getCostCenter(costcenterid, accountid);

        if (null != dd) {

            // Set the form values.
            sForm.setName(dd.getShortDesc());
            sForm.setStatusCd(dd.getCostCenterStatusCd());
            sForm.setId(String.valueOf(dd.getCostCenterId()));
            //sForm.setBudgetAmount(CurrencyFormat.format(dd.getBudgetAmount()));
            //sForm.setBudgetPeriod(dd.getBudgetPeriodCd());
            getSiteBudgets(accountid, costcenterid, sForm, siteidValue, request);
        }
    }

    */
/**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
/*
    public static void sort(HttpServletRequest request, ActionForm form)
                     throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        CostCenterDataVector costcenters = (CostCenterDataVector)session.getAttribute(
                                                   "CostCenter.found.vector");

        if (costcenters == null) {

            return;
        }

        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(costcenters, sortField);
    }

    */
/**
     *  Describe <code>addCostCenter</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
/*
    public static void addCostCenter(HttpServletRequest request,
            ActionForm form)
            throws Exception {

        HttpSession session = request.getSession();
        CostCenterMgrDetailForm costCenterForm = new CostCenterMgrDetailForm();
        session.setAttribute("COST_CENTER_DETAIL_FORM", costCenterForm);
        FiscalPeriodView fpv = null;
        fpv = (FiscalPeriodView)request.getSession().getAttribute("Account.fiscalInfo");
        if ( fpv == null ) {

            fpv = FiscalPeriodView.createValue();
            session.setAttribute("Account.fiscalInfo", fpv);
        }

        if ( fpv.getFiscalCalenderData() == null ) {
            fpv.setFiscalCalenderData(FiscalCalenderData.createValue());
            fpv.getFiscalCalenderData().setPeriodCd("unset");
        }

        if ( null == fpv.getFiscalCalenderData().getPeriodCd() ) {
            fpv.getFiscalCalenderData().setPeriodCd("unset");
        }


    }


    private static boolean verifyBudgetDate(String pmmdd) {

        if (pmmdd == null)

            return false;

        if (pmmdd.length() < 3)

            return false;

        StringTokenizer st = new StringTokenizer(pmmdd, "/");
        int[] ra = new int[2];

        for (int i = 0; st.hasMoreTokens() && i < 2; i++) {
            ra[i] = Integer.parseInt(st.nextToken());
        }

        if (ra[0] <= 0 || ra[0] > 31)

            return false;

        if (ra[1] <= 0 || ra[1] > 31)

            return false;

        return true;
    }

    */
/**
     *  Describe <code>updateCostCenter</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@return                an <code>ActionErrors</code> value
     *@exception  Exception  if an error occurs
     */
/*
    public static ActionErrors updateCostCenter(HttpServletRequest request,
                                                ActionForm form)
                                         throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        CostCenterMgrDetailForm sForm = (CostCenterMgrDetailForm)form;

        if (sForm != null) {

            // Verify the form information submitted.
            if (sForm.getName().length() == 0) {
                lUpdateErrors.add("name",
                                  new ActionError("variable.empty.error",
                                                  "Name"));
            }

            if (sForm.getStatusCd().length() == 0) {
                lUpdateErrors.add("statusCd",
                                  new ActionError("variable.empty.error",
                                                  "Status"));
            }

            if (sForm.getCostCenterTypeCd().length() == 0) {
                lUpdateErrors.add("costCenterTypeCd",
                                  new ActionError("variable.empty.error",
                                                  "Cost Center Type"));
            }

            if (sForm.getBudgetAmount().length() == 0) {
                lUpdateErrors.add("budgetAmount",
                                  new ActionError("variable.empty.error",
                                                  "Budget"));
            } else {

                // check that it is a valid currency amt
                try {

                    BigDecimal budgetAmt = CurrencyFormat.parse(sForm.getBudgetAmount());
                } catch (ParseException pe) {
                    lUpdateErrors.add("budgetAmount",
                                      new ActionError("error.invalidAmount",
                                                      "Budget"));
                }
            }

        }

        if (lUpdateErrors.size() > 0) {

            // Report the errors to allow for edits.
            return lUpdateErrors;
        }

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        int costcenterid = 0;

        if (!sForm.getId().equals("")) {
            costcenterid = Integer.parseInt(sForm.getId());
        }

        // Get the current information for this Account.
        CostCenterData dd;
        int accountid = Integer.parseInt(
					 (String)session.getAttribute("Account.id"));

        if (costcenterid > 0) {
            dd = accountBean.getCostCenter(costcenterid, accountid);
        } else {
            dd = CostCenterData.createValue();
        }

        String cuser = (String)session.getAttribute(Constants.USER_NAME);

        FiscalPeriodView fpv = (FiscalPeriodView)
            session.getAttribute("Account.fiscalInfo");
	if ( null == fpv ||
	     null == fpv.getFiscalCalenderData() ) {
	    fpv = mkFiscalCal(request, accountid);

	}
	if ( null == fpv.getFiscalCalenderData().getPeriodCd() ||
             fpv.getFiscalCalenderData().getPeriodCd().length() <= 0   ) {
            fpv.getFiscalCalenderData().setPeriodCd(RefCodeNames.BUDGET_PERIOD_CD.MONTHLY);
        }

        // Now update with the data from the form.
        dd.setShortDesc(sForm.getName());
        dd.setCostCenterStatusCd(sForm.getStatusCd());
        dd.setShortDesc(sForm.getName());
        dd.setCostCenterStatusCd(sForm.getStatusCd());
        //dd.setBudgetPeriodCd(fpv.getFiscalCalenderData().getPeriodCd());
        dd.setCostCenterTypeCd(sForm.getCostCenterTypeCd());
        dd.setCostCenterTaxType(RefCodeNames.COST_CENTER_TAX_TYPE.DONT_ALLOCATE_SALES_TAX);
        */
/*
        if ( dd.getCatalogId() <= 0 ) {
            CatalogData cd = accountBean.getAccountCatalog(accountid );
            if ( null != cd ) {
                dd.setCatalogId(cd.getCatalogId());
            }
        }
        */
/*
        dd.setModBy(cuser);

        //BigDecimal budgetAmt = CurrencyFormat.parse(sForm.getBudgetAmount());
        //dd.setBudgetAmount(budgetAmt);

        if (costcenterid == 0) {
            dd.setAddBy(cuser);
            dd.setModBy(cuser);

            try {
                //dd = accountBean.addCostCenter(dd, accountid);
                dd = accountBean.updateCostCenter(dd);
            } catch (DuplicateNameException ne) {
                lUpdateErrors.add("name",
                                  new ActionError("error.field.notUnique",
                                                  "Name"));

                return lUpdateErrors;
            }
             catch (Exception e) {
                throw e;
            }

            costcenterid = dd.getCostCenterId();
            sForm.setId(String.valueOf(costcenterid));
        } else {

            // Now update this Cost Center
            dd.setModBy(cuser);

            try {
                accountBean.updateCostCenter(dd);
            } catch (DuplicateNameException ne) {
                lUpdateErrors.add("name",
                                  new ActionError("error.field.notUnique",
                                                  "Name"));

                return lUpdateErrors;
            }
             catch (Exception e) {
                throw e;
            }
        }

        // reformat the cost center budget amount
        //sForm.setBudgetAmount(CurrencyFormat.format(budgetAmt));

        return lUpdateErrors;
    }

    public static FiscalPeriodView mkFiscalCal(HttpServletRequest request,
            int pAccountId)
            throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        return accountBean.createDefaultFiscalInfo(pAccountId);
    }

    */
/**
     *  Describe <code>updateBudgets</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@return                an <code>ActionErrors</code> value
     *@exception  Exception  if an error occurs
     */
/*
    public static ActionErrors updateBudgets(HttpServletRequest request,
            ActionForm form)
            throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        CostCenterMgrDetailForm sForm = (CostCenterMgrDetailForm)form;

        if (sForm != null) {

            // Verify the form information submitted.
            if (sForm.getName().length() == 0) {
                lUpdateErrors.add("name",
                        new ActionError("variable.empty.error",
                        "Name"));
            }
        }

        if (lUpdateErrors.size() > 0) {

            // Report the errors to allow for edits.
            return lUpdateErrors;
        }

        String cuser = (String)session.getAttribute(Constants.USER_NAME);
        APIAccess factory = new APIAccess();

        //handle sites
        if(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(sForm.getCostCenterTypeCd())){
            Budget budBean = factory.getBudgetAPI();

            // Iterate thru the site budgets looking for any that have changed
            ArrayList budgets = sForm.getSiteBudgetList();
            if ( null == budgets ) {
                return lUpdateErrors;
            }

            Iterator iter = budgets.iterator();

            while (iter.hasNext()) {

                SiteBudget siteBudget = (SiteBudget)iter.next();
                BudgetData budgetData = siteBudget.getBudgetData();

                if (budgetData.getBudgetId() == 0) {
                    budgetData.setAddBy(cuser);
                }
                budgetData.setBudgetTypeCd(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET);
                budgetData = budBean.updateBudget(budgetData,cuser);
                siteBudget.setBudgetData(budgetData);
            }
        }

        //handle accounts
        if(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET.equals(sForm.getCostCenterTypeCd())){


            BudgetData acctBudget = sForm.getBudget();
            if(acctBudget == null){
                acctBudget = BudgetData.createValue();
            }
            acctBudget.setAmount1(null);
            acctBudget.setAmount2(null);
            acctBudget.setAmount3(null);
            acctBudget.setAmount4(null);
            acctBudget.setAmount5(null);
            acctBudget.setAmount6(null);
            acctBudget.setAmount7(null);
            acctBudget.setAmount8(null);
            acctBudget.setAmount9(null);
            acctBudget.setAmount10(null);
            acctBudget.setAmount11(null);
            acctBudget.setAmount12(null);
            acctBudget.setAmount13(null);

            try{
                if(sForm.getBudgetAmount1().length() > 0){
                    acctBudget.setAmount1(new BigDecimal(sForm.getBudgetAmount1()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount1",new ActionError("error.invalidAmount","budgetAmount1"));
            }
            try{
                if(sForm.getBudgetAmount2().length() > 0){
                    acctBudget.setAmount2(new BigDecimal(sForm.getBudgetAmount2()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount2",new ActionError("error.invalidAmount","budgetAmount2"));
            }
            try{
                if(sForm.getBudgetAmount3().length() > 0){
                    acctBudget.setAmount3(new BigDecimal(sForm.getBudgetAmount3()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount3",new ActionError("error.invalidAmount","budgetAmount3"));
            }
            try{
                if(sForm.getBudgetAmount4().length() > 0){
                    acctBudget.setAmount4(new BigDecimal(sForm.getBudgetAmount4()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount4",new ActionError("error.invalidAmount","budgetAmount4"));
            }
            try{
                if(sForm.getBudgetAmount5().length() > 0){
                    acctBudget.setAmount5(new BigDecimal(sForm.getBudgetAmount5()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount5",new ActionError("error.invalidAmount","budgetAmount5"));
            }
            try{
                if(sForm.getBudgetAmount6().length() > 0){
                    acctBudget.setAmount6(new BigDecimal(sForm.getBudgetAmount6()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount6",new ActionError("error.invalidAmount","budgetAmount6"));
            }
            try{
                if(sForm.getBudgetAmount7().length() > 0){
                    acctBudget.setAmount7(new BigDecimal(sForm.getBudgetAmount7()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount7",new ActionError("error.invalidAmount","budgetAmount7"));
            }
            try{
                if(sForm.getBudgetAmount8().length() > 0){
                    acctBudget.setAmount8(new BigDecimal(sForm.getBudgetAmount8()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount8",new ActionError("error.invalidAmount","budgetAmount8"));
            }
            try{
                if(sForm.getBudgetAmount9().length() > 0){
                    acctBudget.setAmount9(new BigDecimal(sForm.getBudgetAmount9()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount9",new ActionError("error.invalidAmount","budgetAmount9"));
            }
            try{
                if(sForm.getBudgetAmount10().length() > 0){
                    acctBudget.setAmount10(new BigDecimal(sForm.getBudgetAmount10()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount10",new ActionError("error.invalidAmount","budgetAmount10"));
            }
            try{
                if(sForm.getBudgetAmount11().length() > 0){
                    acctBudget.setAmount11(new BigDecimal(sForm.getBudgetAmount11()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount11",new ActionError("error.invalidAmount","budgetAmount11"));
            }
            try{
                if(sForm.getBudgetAmount12().length() > 0){
                    acctBudget.setAmount12(new BigDecimal(sForm.getBudgetAmount12()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount12",new ActionError("error.invalidAmount","budgetAmount12"));
            }
            try{
                if(sForm.getBudgetAmount13().length() > 0){
                    acctBudget.setAmount13(new BigDecimal(sForm.getBudgetAmount13()));
                }
            }catch(NumberFormatException e){
                lUpdateErrors.add("budgetAmount13",new ActionError("error.invalidAmount","budgetAmount13"));
            }
            if(lUpdateErrors.size() > 0){
                return lUpdateErrors;
            }
            acctBudget.setBudgetTypeCd(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET);
            acctBudget.setBusEntityId(Integer.parseInt((String)session.getAttribute("Account.id")));
            acctBudget.setCostCenterId(sForm.getIntId());
            Budget budBean = factory.getBudgetAPI();
            sForm.setBudget(budBean.updateBudget(acctBudget, cuser));


        }
        return lUpdateErrors;
    }

    */
/**
     *  The <code>delete</code> method removes the database entries defining
     *  this account if no other database entry is dependent on it.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     *@see                   com.cleanwise.service.api.session.Account
     */
/*
    public static ActionErrors delete(HttpServletRequest request,
                                      ActionForm form)
                               throws Exception {

        ActionErrors deleteErrors = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        String strid = request.getParameter("id");

        if (strid == null || strid.length() == 0) {
            deleteErrors.add("id", new ActionError("error.badRequest", "id"));

            return deleteErrors;
        }

        Integer id = new Integer(strid);
        int accountid = Integer.parseInt(
                                (String)session.getAttribute("Account.id"));
        CostCenterData dd = accountBean.getCostCenter(id.intValue(), accountid);

        if (null != dd) {

            try {
                accountBean.removeCostCenter(dd);
            } catch (Exception e) {
                deleteErrors.add("id",
                                 new ActionError("error.deleteFailed",
                                                 "Cost Center"));

                return deleteErrors;
            }

            session.removeAttribute("CostCenter.found.vector");
        }

        return deleteErrors;
    }

    public static ActionErrors updateCategoryCostCenter(HttpServletRequest request,
                                                        ActionForm form)
                                                 throws Exception {

        ActionErrors ae = new ActionErrors();
        String newcostcenter = request.getParameter("newCostCenter");
        String forCategoryName = request.getParameter("categoryName");

        if (newcostcenter == null || newcostcenter.trim().length() == 0) {
            ae.add("error",
                   new ActionError("error.badRequest", "Cost Center Info"));

            return ae;
        }

        int newcostcenterId = Integer.parseInt(newcostcenter.trim());

        if (forCategoryName == null ||
            forCategoryName.trim().length() == 0) {
            ae.add("error",
                   new ActionError("error.badRequest", "Category Name"));

            return ae;
        }

        try {

            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Account accountBean = factory.getAccountAPI();
            int accountid = Integer.parseInt(
                                    (String)session.getAttribute("Account.id"));
            accountBean.setCategoryCostCenter(accountid, forCategoryName,
                                              newcostcenterId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ae;
    }
}
*/

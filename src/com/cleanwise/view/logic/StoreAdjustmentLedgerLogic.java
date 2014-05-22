package com.cleanwise.view.logic;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.view.utils.StringUtils;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.forms.StoreAdjustmentLedgerForm;

import java.rmi.RemoteException;
import java.util.*;
import java.math.BigDecimal;
import com.cleanwise.view.utils.SessionTool;

/**
 * @author Alexander Chikin
 *         Date: 06.10.2006
 *         Time: 9:53:53
 */
public class StoreAdjustmentLedgerLogic {

    private static final Logger log = Logger.getLogger(StoreAdjustmentLedgerLogic.class);

    public static ActionErrors init(HttpServletRequest request, ActionForm form) throws APIServiceAccessException, NamingException {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        String siteId = (String) session.getAttribute("Site.id");
        String accountId = (String) session.getAttribute("Site.account.id");

        if (siteId == null) {
            ae.add("error", new ActionError("simpleGenericError", "Site Id not found"));
        }

        if (accountId == null) {
            ae.add("error", new ActionError("simpleGenericError", "Account Id not found"));
        }

        if (ae.size() > 0) {
            return ae;
        }

        int accountIdInt;
        int siteIdInt;
        try {
            accountIdInt = Integer.parseInt(accountId);
            siteIdInt = Integer.parseInt(siteId);
        } catch (NumberFormatException e) {
            ae.add("error",
                    new ActionError("simpleGenericError",
                            "Invalid <siteId or accountId>:<siteId - " + siteId + "> , <accountId - " + accountId + ">"));
            return ae;
        }

        APIAccess factory = new APIAccess();
        Account accountEJB = factory.getAccountAPI();
        Site siteEJB = factory.getSiteAPI();

        StoreAdjustmentLedgerForm pForm = new StoreAdjustmentLedgerForm();
        session.setAttribute("STORE_ADJUSTMENT_LEDGER_FORM", pForm);
        session.setAttribute("Site.budget.year.vector", new ArrayList());
        session.setAttribute("Site.budget.period.type", "");
        session.setAttribute("Site.budget.period.vector", new ArrayList());
        session.setAttribute("Site.budget.costcenter.vector", new ArrayList());
        session.setAttribute("Site.budget.info", new Hashtable());
        session.setAttribute("Site.budget.infoWithSpecifedAdjustment", new Hashtable());

        try {
            //get fiscal calendar collection for selected accountId
            FiscalCalenderViewVector fiscalCalendarVV = accountEJB.getFiscalCalCollection(accountIdInt);
            if (fiscalCalendarVV == null) {
                ae.add("error", new ActionError("error.simpleGenericError", "Account does not have fiscal calendar assigned"));
                return ae;
            }
            //get  budget years list which contains  collection
            ArrayList budgetYears = getArrayBudgetYears(fiscalCalendarVV);
            if (budgetYears.size() == 0) {
                ae.add("error", new ActionError("error.simpleGenericError", "Can't build budget years list"));
                return ae;
            }
            //get cost centers collection.
            CostCenterDataVector costCentersDV = accountEJB.getAllCostCenters(accountIdInt, Account.ORDER_BY_NAME);
            //get cost centers list which contains  collection
            ArrayList<String> costCentersShortDesc = getArrayCostCenter(costCentersDV);
            if (costCentersShortDesc.size() == 0) {
                ae.add("error", new ActionError("error.simpleGenericError", "Can't build cost centers list"));
                return ae;
            }
            //////////////////////////////////////////////////
            SiteData siteData = siteEJB.getSite(siteIdInt, 0, true, SessionTool.getCategoryToCostCenterView(session, siteIdInt));
            BudgetViewVector budgetVV = siteData.getBudgets();
            //select budget data from collection  for first cost center
            BudgetView budget = getBudget(budgetVV, ((CostCenterData) costCentersDV.get(0)).getCostCenterId());
            if (budget == null) {
                ae.add("error", new ActionError("error.simpleGenericError", "Site does not have budget assigned"));
                return ae;
            }
            int curNumBudgetPeriod = siteData.getCurrentBudgetPeriod();
            String budgetTypeCd = budget.getBudgetData().getBudgetTypeCd();
            //get Currently fiscal calendar
            FiscalCalenderView curFiscalCalendar = accountEJB.getFiscalCalenderV(accountIdInt, new Date());
            String curBudgetPeriodType = "";
            String curBudgetPeriod = "";
            String curBudgetYear = "";

            if (curFiscalCalendar != null) {
                curBudgetPeriodType = curFiscalCalendar.getFiscalCalender().getPeriodCd();
                curBudgetPeriod = getCurrentlyBudgetPeriod(curFiscalCalendar.getFiscalCalenderDetails(), curNumBudgetPeriod);
                curBudgetYear = String.valueOf(curFiscalCalendar.getFiscalCalender().getFiscalYear());
            }

            //get currently cost center data
            CostCenterData curCostCenter = accountEJB.getCostCenter(budget.getBudgetData().getCostCenterId(), accountIdInt);
            //get array start date  budget periods
            HashMap<Integer, String> periods = getArrayMmdd(curFiscalCalendar);
            //contains period adjustments
            //key is year_costCenterId,object is ArrayList
            //Size of array is nimber of budget periods
            //0 is an adjustment for the first budget period.
            //1 -  an adjustment for the second budget period..
            Hashtable<String, HashMap<Integer, BigDecimal>> htAdjustments = new Hashtable<String, HashMap<Integer, BigDecimal>>();
            htAdjustments.put(curBudgetYear + "_" + curCostCenter.getCostCenterId(), initArrayAdjust(curFiscalCalendar));

            Hashtable<String, HashMap<Integer, Boolean>> htChangesAdjustmentFl = new Hashtable<String, HashMap<Integer, Boolean>>();
            htChangesAdjustmentFl.put(curBudgetYear + "_" + curCostCenter.getCostCenterId(), initArrayChangesAdjustmentFl(curFiscalCalendar));

            Hashtable<String, HashMap<Integer, String>> htComments = new Hashtable<String, HashMap<Integer, String>>();
            htComments.put(curBudgetYear + "_" + curCostCenter.getCostCenterId(), initArrayComments(curFiscalCalendar));

            pForm.setAccountId(accountId);
            pForm.setSiteId(siteId);
            pForm.setSiteName(siteData.getBusEntity().getShortDesc());
            pForm.setAccountName(siteData.getAccountBusEntity().getShortDesc());
            pForm.setSelectedCostCenter(curCostCenter.getShortDesc());
            pForm.setSelectedBudgetYear(curBudgetYear);
            pForm.setSelectedBudgetPeriod(curBudgetPeriod);
            pForm.setSelectedBudgetPeriodType(curBudgetPeriodType);
            pForm.setBudget(budget);
            pForm.setFiscalCalendarCollection(fiscalCalendarVV);
            pForm.setCostCentersCollection(costCentersDV);
            pForm.setSiteBudgets(budgetVV);
            pForm.setCurrentBudgetYear(Integer.parseInt(curBudgetYear));
            pForm.setBudgetPeriods(periods);
            pForm.setHtAdjustments(htAdjustments);
            pForm.setHtChangesAdjustmentsFl(htChangesAdjustmentFl);
            pForm.setHtComments(htComments);
            pForm.setBudgetTypeCd(budgetTypeCd);

            SiteLedgerDataVector siteLedgerDV = new SiteLedgerDataVector();
            if (budget.getBudgetData().getBudgetTypeCd().equals(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET)) {
                ///////////////////////////////////////////////////
                ////////BudgetTypeCd is ACCOUNT BUDGET/////////////
                //get ledger collection for all sites of account//
                ///////////////////////////////////////////////////
                IdVector siteIdV = siteEJB.getSiteIdsOnlyForAccount(budget.getBudgetData().getBusEntityId());
                Iterator iter = siteIdV.iterator();
                SiteLedgerDataVector tempLedgerDV;
                while (iter.hasNext()) {
                    Integer id = (Integer) iter.next();
                    tempLedgerDV = siteEJB.getValidSiteLedgerCollection(id.intValue(), budget.getBudgetData().getCostCenterId());
                    if (tempLedgerDV != null && tempLedgerDV.size() > 0) {
                        siteLedgerDV.addAll(tempLedgerDV);
                    }
                }
            } else {
                //get ledger collection for site
                siteLedgerDV = siteEJB.getValidSiteLedgerCollection(Integer.parseInt(siteId), budget.getBudgetData().getCostCenterId());
            }
            //get comment of ledger collection for selected budget year,period,cost center..
            String comment = getCommentOfLedgerCollection(siteLedgerDV,
                    curFiscalCalendar.getFiscalCalender().getFiscalYear(),
                    curNumBudgetPeriod,
                    curCostCenter.getCostCenterId());

            pForm.setComments(comment);

            //contains report of budget  for selected budget year,period,cost center
            //key is budget period shortDesc and  string "Total" which contains  object sum,object is String[] param
            //Size of array is 4
            //param[0] is Allocated (budget amount of clw_budget)
            //param[1] is Spent (sum all amount of clw_site_ledger exclude amount where entry_type_cd ="Adjustment"  )
            //param[2] is Adjustment
            //param[3] is Difference(Allocated-(Spent+Adjustment))
            Hashtable<String, String[]> budgetInfo = new Hashtable<String, String[]>();
            //budgetinfo with specified adjustment
            Hashtable<String, String[]> budgetInfoWithSpecAdj = new Hashtable<String, String[]>();

            buildBudgetInfo(curFiscalCalendar, budget, curCostCenter.getCostCenterId(),
                    siteLedgerDV, htAdjustments, htChangesAdjustmentFl,
                    budgetInfo, budgetInfoWithSpecAdj);

            if (budgetInfoWithSpecAdj.get(curBudgetPeriod) != null) {
                pForm.setBudgetAdjustment((budgetInfoWithSpecAdj.get(curBudgetPeriod))[2]);
            }

            ArrayList<Integer> periodNums = new ArrayList<Integer>();
            periodNums.addAll(periods.keySet());
            Collections.sort(periodNums);

            session.setAttribute("Site.budget.year.vector", budgetYears);
            session.setAttribute("Site.budget.period.type", pForm.getSelectedBudgetPeriodType());
            session.setAttribute("Site.budget.period.vector", periodNums);
            session.setAttribute("Site.budget.costcenter.vector", costCentersShortDesc);
            session.setAttribute("Site.budget.info", budgetInfo);
            session.setAttribute("Site.budget.infoWithSpecifedAdjustment", budgetInfoWithSpecAdj);
        }
        catch (DataNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        } /*catch (DataNotFoundException e) {

        }  */
        return ae;
    }

    /**
     * get comment of ledger collection
     * for selected budget year,period,cost center..
     *
     * @param siteLedgerDV collection
     * @param budgetYear   budget year
     * @param budgetPeriod budget period
     * @param costCenterId cost center Id
     * @return comment
     */
    private static String getCommentOfLedgerCollection(SiteLedgerDataVector siteLedgerDV, int budgetYear, int budgetPeriod, int costCenterId) {
        if (siteLedgerDV != null) {
            for (Object oSiteLedgerDV : siteLedgerDV) {
                SiteLedgerData siteLD = (SiteLedgerData) oSiteLedgerDV;
                if (siteLD.getBudgetYear() == budgetYear
                        && siteLD.getBudgetPeriod() == budgetPeriod
                        && siteLD.getCostCenterId() == costCenterId
                        && siteLD.getEntryTypeCd().equals(RefCodeNames.LEDGER_ENTRY_TYPE_CD.ADJUSTMENT)) {
                    return siteLD.getComments();
                }
            }
        }
        return "";
    }


    private static HashMap<Integer, String> initArrayComments(FiscalCalenderView fiscalCalendar) {
        HashMap<Integer, String> arrayComments = new HashMap<Integer, String>();
        for (int i = 1; i <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalCalendar); i++) {
            arrayComments.put(i, "");
        }
        return arrayComments;
    }

    private static HashMap<Integer, Boolean> initArrayChangesAdjustmentFl(FiscalCalenderView fiscalCalendar) {
        HashMap<Integer, Boolean> changesAdjustment = new HashMap<Integer, Boolean>();
        for (int i = 1; i <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalCalendar); i++) {
            changesAdjustment.put(i, Boolean.FALSE);
        }
        return changesAdjustment;
    }

    private static HashMap<Integer, BigDecimal> initArrayAdjust(FiscalCalenderView fiscalCalendar) {
        HashMap<Integer, BigDecimal> adjust = new HashMap<Integer, BigDecimal>();
        for (int i = 1; i <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalCalendar); i++) {
            adjust.put(i, new BigDecimal(0));
        }
        return adjust;
    }

    /*
     * build report of budget data for selected budget year,period,cost center
     */
    private static void buildBudgetInfo(FiscalCalenderView fiscalCalendar,
                                        BudgetView budget,
                                        int costCenterId,
                                        SiteLedgerDataVector siteLedgerDV,
                                        Hashtable<String, HashMap<Integer, BigDecimal>> htAdjustments,
                                        Hashtable<String, HashMap<Integer, Boolean>> htChangesAdjustmentFl,
                                        Hashtable<String, String[]> resultTable,
                                        Hashtable<String, String[]> resultTableWithSpecAdj) {

        log.info("buildBudgetInfo => BEGIN");

        HashMap<Integer, String> periods = getArrayMmdd(fiscalCalendar);
        HashMap<Integer, BigDecimal> amounts = getArrayAmounts(fiscalCalendar, budget);

        log.info("buildBudgetInfo => amounts: "+amounts);

        String[] totalParam = new String[4];
        String[] totalParamWithSpecAdj = new String[4];
        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal totalLedgerAmount = new BigDecimal(0);
        BigDecimal totalAdjustmentLedgerAmount = new BigDecimal(0);
        BigDecimal totalResultAmount = new BigDecimal(0);

        BigDecimal totalAmountWithSpecAdj = new BigDecimal(0);
        BigDecimal totalLedgerAmountWithSpecAdj = new BigDecimal(0);
        BigDecimal totalAdjustmentLedgerAmountWithSpecAdj = new BigDecimal(0);
        BigDecimal totalResultAmountWithSpecAdj = new BigDecimal(0);

        HashMap<Integer, BigDecimal> arraySpecifedAdjustment = htAdjustments.get(String.valueOf(fiscalCalendar.getFiscalCalender().getFiscalYear()) + "_" + costCenterId);
        HashMap<Integer, Boolean> arrayChangesAdjustmentFl = htChangesAdjustmentFl.get(String.valueOf(fiscalCalendar.getFiscalCalender().getFiscalYear()) + "_" + costCenterId);

        for (int i = 1; i <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalCalendar); i++) {
            String[] param = new String[4];
            String[] paramWithSpecAdj = new String[4];
            BigDecimal amount = amounts.get(i);

            log.info("buildBudgetInfo => period: "+i+" ,amount: "+amount);

            BigDecimal ledgerAmount = getAllLedgerAmountWithOutAdjustment(fiscalCalendar.getFiscalCalender().getFiscalYear(), i, siteLedgerDV);
            BigDecimal adjustmentLedgerAmount = getAdjustmentLedgerAmount(fiscalCalendar.getFiscalCalender().getFiscalYear(), i, siteLedgerDV);
            BigDecimal restAmount;
            if (amount == null) {
                restAmount = (ledgerAmount).subtract(adjustmentLedgerAmount);
            } else {
                restAmount = amount.subtract(ledgerAmount).subtract(adjustmentLedgerAmount);
            }

            BigDecimal amountWithSpecAdj;
            BigDecimal ledgerAmountWithSpecAdj;
            BigDecimal adjustmentLedgerAmountWithSpecAdj;
            BigDecimal restAmountWithSpecAdj;

            if (arraySpecifedAdjustment != null && arrayChangesAdjustmentFl != null && arrayChangesAdjustmentFl.get(i)) {
                amountWithSpecAdj = Utility.bdNN(amounts.get(i));
                adjustmentLedgerAmountWithSpecAdj = (arraySpecifedAdjustment.get(i));
                ledgerAmountWithSpecAdj = getAllLedgerAmountWithOutAdjustment(fiscalCalendar.getFiscalCalender().getFiscalYear(), i, siteLedgerDV).add(adjustmentLedgerAmountWithSpecAdj);
                restAmountWithSpecAdj = amountWithSpecAdj.subtract(ledgerAmountWithSpecAdj);
            } else {
                amountWithSpecAdj = Utility.bdNN(amount);
                ledgerAmountWithSpecAdj = ledgerAmount;
                adjustmentLedgerAmountWithSpecAdj = adjustmentLedgerAmount;
                restAmountWithSpecAdj = restAmount;
            }

            totalAmountWithSpecAdj = totalAmountWithSpecAdj.add(amountWithSpecAdj);
            totalLedgerAmountWithSpecAdj = totalLedgerAmountWithSpecAdj.add(ledgerAmountWithSpecAdj);
            totalAdjustmentLedgerAmountWithSpecAdj = totalAdjustmentLedgerAmountWithSpecAdj.add(adjustmentLedgerAmountWithSpecAdj);
            totalResultAmountWithSpecAdj = totalResultAmountWithSpecAdj.add(restAmountWithSpecAdj);

            if (amount != null) {
                totalAmount = totalAmount.add(amount);
            }

            totalLedgerAmount = totalLedgerAmount.add(ledgerAmount);
            totalAdjustmentLedgerAmount = totalAdjustmentLedgerAmount.add(adjustmentLedgerAmount);
            totalResultAmount = totalResultAmount.add(restAmount);

            if (amount == null) {
                param[0] = "";
            } else {
                param[0] = amount.toString();
            }

            param[1] = ledgerAmount.toString();
            param[2] = adjustmentLedgerAmount.toString();
            param[3] = restAmount.toString();

            paramWithSpecAdj[0] = amountWithSpecAdj.toString();
            paramWithSpecAdj[1] = ledgerAmountWithSpecAdj.toString();
            paramWithSpecAdj[2] = adjustmentLedgerAmountWithSpecAdj.toString();
            paramWithSpecAdj[3] = restAmountWithSpecAdj.toString();

            resultTable.put(String.valueOf(i), param);
            resultTableWithSpecAdj.put(String.valueOf(i), paramWithSpecAdj);

        }

        totalParam[0] = totalAmount.toString();
        totalParam[1] = totalLedgerAmount.toString();
        totalParam[2] = totalAdjustmentLedgerAmount.toString();
        totalParam[3] = totalResultAmount.toString();

        totalParamWithSpecAdj[0] = totalAmountWithSpecAdj.toString();
        totalParamWithSpecAdj[1] = totalLedgerAmountWithSpecAdj.toString();
        totalParamWithSpecAdj[2] = totalAdjustmentLedgerAmountWithSpecAdj.toString();
        totalParamWithSpecAdj[3] = totalResultAmountWithSpecAdj.toString();

        resultTable.put("Total", totalParam);
        resultTableWithSpecAdj.put("Total", totalParamWithSpecAdj);

        log.info("buildBudgetInfo => END.");

    }

    private static BigDecimal getAdjustmentLedgerAmount(int year, int period, SiteLedgerDataVector siteLedgerDV) {
        BigDecimal result = new BigDecimal(0);
        if (siteLedgerDV != null) {
            for (Object oSiteLedgerDV : siteLedgerDV) {
                SiteLedgerData siteLedgerD = (SiteLedgerData) oSiteLedgerDV;
                if (period == siteLedgerD.getBudgetPeriod() && year == siteLedgerD.getBudgetYear()
                        && siteLedgerD.getEntryTypeCd().equals(RefCodeNames.LEDGER_ENTRY_TYPE_CD.ADJUSTMENT))
                    result = result.add(siteLedgerD.getAmount());
            }
        }
        return result;
    }

    private static BigDecimal getOrderLedgerAmount(int year, int period, SiteLedgerDataVector siteLedgerDV) {
        BigDecimal result = new BigDecimal(0);
        if (siteLedgerDV != null) {
            for (Object oSiteLedgerDV : siteLedgerDV) {
                SiteLedgerData siteLedgerD = (SiteLedgerData) oSiteLedgerDV;
                if (period == siteLedgerD.getBudgetPeriod() && year == siteLedgerD.getBudgetYear()
                        && siteLedgerD.getEntryTypeCd().equals(RefCodeNames.LEDGER_ENTRY_TYPE_CD.ORDER))
                    result = result.add(siteLedgerD.getAmount());
            }
        }
        return result;
    }

    private static BigDecimal getInvoiceLedgerAmount(int year, int period, SiteLedgerDataVector siteLedgerDV) {
        BigDecimal result = new BigDecimal(0);
        if (siteLedgerDV != null) {
            for (Object oSiteLedgerDV : siteLedgerDV) {
                SiteLedgerData siteLedgerD = (SiteLedgerData) oSiteLedgerDV;
                if (period == siteLedgerD.getBudgetPeriod() && year == siteLedgerD.getBudgetYear()
                        && siteLedgerD.getEntryTypeCd().equals(RefCodeNames.LEDGER_ENTRY_TYPE_CD.INVOICE_DIST_ACTUAL))
                    result = result.add(siteLedgerD.getAmount());
            }
        }
        return result;
    }

    private static BigDecimal getPriorLedgerAmount(int year, int period, SiteLedgerDataVector siteLedgerDV) {
        BigDecimal result = new BigDecimal(0);
        if (siteLedgerDV != null) {
            for (Object oSiteLedgerDV : siteLedgerDV) {
                SiteLedgerData siteLedgerD = (SiteLedgerData) oSiteLedgerDV;
                if (period == siteLedgerD.getBudgetPeriod() && year == siteLedgerD.getBudgetYear()
                        && siteLedgerD.getEntryTypeCd().equals(RefCodeNames.LEDGER_ENTRY_TYPE_CD.PRIOR_PERIOD_BUDGET_ACTUAL))
                    result = result.add(siteLedgerD.getAmount());
            }
        }
        return result;
    }

    private static BigDecimal getAllLedgerAmount(int year, int period, SiteLedgerDataVector siteLedgerDV) {
        BigDecimal result = new BigDecimal(0);
        if (siteLedgerDV != null) {
            for (Object oSiteLedgerDV : siteLedgerDV) {
                SiteLedgerData siteLedgerD = (SiteLedgerData) oSiteLedgerDV;
                if (period == siteLedgerD.getBudgetPeriod() && year == siteLedgerD.getBudgetYear()) {
                    result = result.add(siteLedgerD.getAmount());
                }
            }
        }
        return result;
    }

    private static BigDecimal getAllLedgerAmountWithOutOrder(int year, int period, SiteLedgerDataVector siteLedgerDV) {
        BigDecimal result = new BigDecimal(0);
        if (siteLedgerDV != null) {
            for (Object oSiteLedgerDV : siteLedgerDV) {
                SiteLedgerData siteLedgerD = (SiteLedgerData) oSiteLedgerDV;
                if (period == siteLedgerD.getBudgetPeriod() && year == siteLedgerD.getBudgetYear()
                        && !siteLedgerD.getEntryTypeCd().equals(RefCodeNames.LEDGER_ENTRY_TYPE_CD.ORDER)) {
                    result = result.add(siteLedgerD.getAmount());
                }
            }
        }
        return result;
    }

    private static BigDecimal getAllLedgerAmountWithOutAdjustment(int year, int period, SiteLedgerDataVector siteLedgerDV) {
        BigDecimal result = new BigDecimal(0);
        if (siteLedgerDV != null) {
            Iterator iter = siteLedgerDV.iterator();
            for (Object oSiteLedgerDV : siteLedgerDV) {
                SiteLedgerData siteLedgerD = (SiteLedgerData) oSiteLedgerDV;
                if (period == siteLedgerD.getBudgetPeriod() && year == siteLedgerD.getBudgetYear()
                        && !siteLedgerD.getEntryTypeCd().equals(RefCodeNames.LEDGER_ENTRY_TYPE_CD.ADJUSTMENT)) {
                    result = result.add(siteLedgerD.getAmount());
                }
            }
        }
        return result;
    }

    private static HashMap<Integer, String> getArrayMmdd(FiscalCalenderView fiscalCal) {
        HashMap<Integer, String> periods = new HashMap<Integer, String>();
        if (fiscalCal != null) {
            for (int i = 1; i <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalCal); i++) {
                String mmdd = FiscalCalendarUtility.getMmdd(fiscalCal.getFiscalCalenderDetails(), i);
                periods.put(i, mmdd != null ? mmdd : "N/A");
            }
        }
        return periods;
    }

    private static HashMap<Integer, BigDecimal> getArrayAmounts(FiscalCalenderView fiscalCal, BudgetView budget) {
        HashMap<Integer, BigDecimal> amounts = new HashMap<Integer, BigDecimal>();
        if (budget != null) {
            for (int i = 1; i <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalCal); i++) {
                amounts.put(i, BudgetUtil.getAmount(budget.getDetails(), i));
            }
        }
        return amounts;
    }

    private static String getCurrentlyBudgetPeriod(FiscalCalenderDetailDataVector pFiscalCalDetails, int pNumBudgetPeriod) throws Exception {
        for (Object oFiscalDetail : pFiscalCalDetails) {
            FiscalCalenderDetailData fiscalDetailData = (FiscalCalenderDetailData) oFiscalDetail;
            if (fiscalDetailData.getPeriod() == pNumBudgetPeriod) {
                return fiscalDetailData.getMmdd();
            }
        }
        throw new Exception("Unknown budget period");
    }

    public static ActionErrors changeBudgetYears(HttpServletRequest request, ActionForm form) throws APIServiceAccessException, NamingException {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        String siteId = (String) session.getAttribute("Site.id");
        String accountId = (String) session.getAttribute("Site.account.id");

        if (siteId == null) {
            ae.add("error", new ActionError("simpleGenericError", "Site Id not found"));
        }

        if (accountId == null) {
            ae.add("error", new ActionError("simpleGenericError", "Account Id not found"));
        }

        if (ae.size() > 0) {
            return ae;
        }

        StoreAdjustmentLedgerForm pForm = (StoreAdjustmentLedgerForm) form;
        HashMap<Integer, String> periods = new HashMap<Integer, String>();
        ///////reset variable//////////////////////////////////
        pForm.setSelectedCostCenter("");
        pForm.setSelectedBudgetPeriod("");
        pForm.setBudgetAdjustment("0");
        pForm.setBudget(null);
        pForm.setComments("");
        pForm.setSelectedBudgetPeriodType("-");
        session.setAttribute("Site.budget.period.vector", new ArrayList());
        session.setAttribute("Site.budget.info", new Hashtable());

        FiscalCalenderViewVector fiscalCalendarVV = pForm.getFiscalCalendarCollection();
        if (fiscalCalendarVV == null || (fiscalCalendarVV.size() == 0)) {
            ae.add("error", new ActionError("error.simpleGenericError", "Account does not have fiscal calendar assigned"));
            return ae;
        }

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        int currentYear = gc.get(Calendar.YEAR);

        Iterator<FiscalCalenderView> iter = fiscalCalendarVV.iterator();
        FiscalCalenderView fiscalCalV = null;
        while (iter.hasNext()) {
            fiscalCalV = iter.next();
            if (fiscalCalV.getFiscalCalender().getFiscalYear() == Integer.parseInt(pForm.getSelectedBudgetYear()) ||
                    ((Integer.parseInt(pForm.getSelectedBudgetYear()) == currentYear) &&  fiscalCalV.getFiscalCalender().getFiscalYear() == 0)) {
                periods = getArrayMmdd(fiscalCalV);
                break;
            }
        }

        if (fiscalCalV != null) {
            pForm.setSelectedBudgetPeriodType(fiscalCalV.getFiscalCalender().getPeriodCd());
        } else {
            pForm.setSelectedBudgetPeriodType("");
        }

        pForm.setBudgetPeriods(periods);
        ArrayList<Integer> periodNums = new ArrayList<Integer>();
        periodNums.addAll(periods.keySet());
        Collections.sort(periodNums);

        session.setAttribute("Site.budget.period.vector",periodNums );

        return ae;
    }


    public static ArrayList<String> getArrayBudgetYears(FiscalCalenderViewVector fiscalCalendarVV) {

        ArrayList<String> budgetYears = new ArrayList<String>();
        if (fiscalCalendarVV != null) {
            for (Object oFiscalCalendarV : fiscalCalendarVV) {
                FiscalCalenderView fiscalCalendarV = (FiscalCalenderView) oFiscalCalendarV;
                if (!budgetYears.contains(String.valueOf(fiscalCalendarV.getFiscalCalender().getFiscalYear())))
                    if (fiscalCalendarV.getFiscalCalender().getFiscalYear() == 0) {
                        GregorianCalendar gc = new GregorianCalendar();
                        gc.setTime(new Date());
                        budgetYears.add(String.valueOf(gc.get(Calendar.YEAR)));
                    } else {
                        budgetYears.add(String.valueOf(fiscalCalendarV.getFiscalCalender().getFiscalYear()));
                    }
            }
            if (budgetYears.size() != 0) {
                Collections.sort(budgetYears);
            }
        }
        return budgetYears;
    }

    public static ArrayList<String> getArrayCostCenter(CostCenterDataVector costCentersDV) {

        ArrayList<String> costCentersShortDesc = new ArrayList<String>();
        if (costCentersDV != null) {
            Iterator iterator = costCentersDV.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                CostCenterData costCenterData = (CostCenterData) iterator.next();
                if (!costCentersShortDesc.contains(costCenterData.getShortDesc()))
                    costCentersShortDesc.add(i++, costCenterData.getShortDesc());
            }
        }
        return costCentersShortDesc;
    }

    /**
     * get budget  by cost center id from collection
     *
     * @param budgetVV     collection
     * @param costCenterId cost center id
     * @return budget  for cost center id
     */
    public static BudgetView getBudget(BudgetViewVector budgetVV, int costCenterId) {

        BudgetView budget = null;
        if (budgetVV != null) {
            for (Object oBudget : budgetVV) {
                budget = (BudgetView) oBudget;
                if (budget.getBudgetData().getCostCenterId() == costCenterId) {
                    break;
                } else {
                    budget = null;
                }
            }
        }
        return budget;
    }

    public static ActionErrors getDetail(HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        String siteId = (String) session.getAttribute("Site.id");
        String accountId = (String) session.getAttribute("Site.account.id");

        if (siteId == null) {
            ae.add("error", new ActionError("simpleGenericError", "Site Id not found"));
        }

        if (accountId == null) {
            ae.add("error", new ActionError("simpleGenericError", "Account Id not found"));
        }
        if (ae.size() > 0)
            return ae;

        int accountIdInt;

        try {
            accountIdInt = Integer.parseInt(accountId);
            Integer.parseInt(siteId);
        } catch (NumberFormatException e) {
            ae.add("error", new ActionError("simpleGenericError",
                    "Invalid <siteId or accountId>:<siteId - " + siteId + "> , <accountId - " + accountId + ">"));
            return ae;
        }

        APIAccess factory = new APIAccess();
        Site siteEJB = factory.getSiteAPI();
        StoreAdjustmentLedgerForm pForm = (StoreAdjustmentLedgerForm) form;

        if (pForm == null) throw new Exception("StoreAdjustmentLedgerForm is null");
        ////////reset data///////////////
        pForm.setBudgetAdjustment("0");
        pForm.setBudget(null);
        pForm.setComments("");
        session.setAttribute("Site.budget.info", new Hashtable());
        session.setAttribute("Site.budget.infoWithSpecifedAdjustment", new Hashtable());
        if (Utility.isSet(pForm.getSelectedBudgetPeriod()) && Utility.isSet(pForm.getSelectedCostCenter())) {
            try {
                FiscalCalenderViewVector fiscalCalendarVV = pForm.getFiscalCalendarCollection();
                if (fiscalCalendarVV == null) {
                    ae.add("error", new ActionError("error.simpleGenericError", "Fiscal calendar collection not initialized"));
                    return ae;
                }

                CostCenterDataVector costCentersDV = pForm.getCostCentersCollection();
                if (fiscalCalendarVV == null) {
                    ae.add("error", new ActionError("error.simpleGenericError", "Cost centers collection not initialized"));
                    return ae;
                }
                ////////////////////////////////////////////
                ////search data for selected cost center////
                ////////////////////////////////////////////
                Iterator iterator = costCentersDV.iterator();
                CostCenterData costCenterData = null;
                while (iterator.hasNext()) {
                    costCenterData = (CostCenterData) iterator.next();
                    if (costCenterData.getShortDesc().equals(pForm.getSelectedCostCenter())) {
                        break;
                    } else {
                        costCenterData = null;
                    }
                }

                if (costCenterData == null) {
                    ae.add("error", new ActionError("error.simpleGenericError", "Cost center data not found for selected cost center "));
                    return ae;
                }
                //////////////////////////////////////////////////
                log.info("getDetail => Selected Budget Year: "+ pForm.getSelectedBudgetYear());
                log.info("getDetail => Current Budget Year: "+ pForm.getCurrentBudgetYear());
                BudgetView budget = null;
                if (Integer.parseInt(pForm.getSelectedBudgetYear()) == pForm.getCurrentBudgetYear()) {
                    BudgetViewVector budgetVV = pForm.getSiteBudgets();
                    //search budget data  for selected cost center from collection////
                    budget = getBudget(budgetVV, costCenterData.getCostCenterId());
                    if (budget == null) {
                        ae.add("error", new ActionError("error.simpleGenericError", "Budget Data not found for selected cost center"));
                        return ae;
                    }
                }

                log.info("getDetail => budget: "+ budget);

                //////////////////////////////////////////////////////////
                ////search FiscalCalenderData for selected budget year////
                //////////////////////////////////////////////////////////
                Iterator iter = fiscalCalendarVV.iterator();
                FiscalCalenderView fiscalCalV = null;
                while (iter.hasNext()) {
                    fiscalCalV = (FiscalCalenderView) iter.next();
                    if (fiscalCalV.getFiscalCalender().getFiscalYear() == Integer.parseInt(pForm.getSelectedBudgetYear())) {
                        break;
                    }
                }

                //contains period adjustments
                //key is year_costCenterId,object is ArrayList
                //Size of array is number of budget periods
                //0 is an adjustment for the first budget period.
                //1 -  an adjustment for the second budget period..
                Hashtable<String, HashMap<Integer,BigDecimal>> htAdjustments = pForm.getHtAdjustments();
                Hashtable<String, HashMap<Integer,Boolean>> htChangesAdjustmentsFl = pForm.getHtChangesAdjustmentsFl();
                Hashtable<String, HashMap<Integer,String>> htComments = pForm.getHtComments();

                int costCenterIdInt = getCostCenterIdByNameFromCollection(costCentersDV, pForm.getSelectedCostCenter());
                if (htAdjustments.get(pForm.getSelectedBudgetYear() + "_" + String.valueOf(costCenterIdInt)) == null) {
                    htAdjustments.put(pForm.getSelectedBudgetYear() + "_" + String.valueOf(costCenterIdInt), initArrayAdjust(fiscalCalV));
                    htChangesAdjustmentsFl.put(pForm.getSelectedBudgetYear() + "_" + String.valueOf(costCenterIdInt), initArrayChangesAdjustmentFl(fiscalCalV));
                    htComments.put(pForm.getSelectedBudgetYear() + "_" + String.valueOf(costCenterIdInt), initArrayComments(fiscalCalV));


                }

                pForm.setBudget(budget);
                pForm.setSelectedBudgetPeriodType(fiscalCalV.getFiscalCalender().getPeriodCd());

                SiteLedgerDataVector siteLedgerDV = new SiteLedgerDataVector();
                if (pForm.getBudgetTypeCd() != null && pForm.getBudgetTypeCd().equals(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET)) {
                    ///////////////////////////////////////////////////
                    ////////BudgetTypeCd is ACCOUNT BUDGET/////////////
                    //get ledger collection for all sites of account//
                    ///////////////////////////////////////////////////
                    IdVector siteIdV = siteEJB.getSiteIdsOnlyForAccount(accountIdInt);
                    iter = siteIdV.iterator();
                    SiteLedgerDataVector tempLedgerDV;
                    while (iter.hasNext()) {

                        Integer id = (Integer) iter.next();
                        tempLedgerDV = siteEJB.getValidSiteLedgerCollection(id, costCenterIdInt);
                        if (tempLedgerDV != null && tempLedgerDV.size() > 0) {
                            siteLedgerDV.addAll(tempLedgerDV);
                        }
                    }
                } else {
                    //get ledger collection for site
                    siteLedgerDV = siteEJB.getValidSiteLedgerCollection(Integer.parseInt(siteId), costCenterIdInt);
                }
                //contains report of budget  for selected budget year,period,cost center
                //key is budget period shortDesc and  string "Total" which contains  object sum,object is String[] param
                //Size of array is 4
                //param[0] is Allocated (budget amount of clw_budget)
                //param[1] is Spent (sum all amount of clw_site_ledger exclude amount where entry_type_cd ="Adjustment")
                //param[2] is Adjustment
                //param[3] is Difference(Allocated-(Spent+Adjustment))
                Hashtable<String, String[]> budgetInfo = new Hashtable<String, String[]>();
                //budgetinfo with specified adjustment
                Hashtable<String, String[]> budgetInfoWithSpecAdj = new Hashtable<String, String[]>();
                 buildBudgetInfo(fiscalCalV, budget, costCenterIdInt, siteLedgerDV,
                        pForm.getHtAdjustments(), pForm.getHtChangesAdjustmentsFl(),
                        budgetInfo, budgetInfoWithSpecAdj);
                String[] params = budgetInfoWithSpecAdj.get(pForm.getSelectedBudgetPeriod());
                if (params != null) {
                    pForm.setBudgetAdjustment(params[2]);
                } else {
                    pForm.setBudgetAdjustment("");
                }
                String comment;
                int numBudgetPeriod = getNumBudgetPeriodByShortDesc(fiscalCalV, pForm.getSelectedBudgetPeriod());
                HashMap<Integer,Boolean> arrayChangesAdjFl = htChangesAdjustmentsFl.get(pForm.getSelectedBudgetYear() + "_" + String.valueOf(costCenterIdInt));
                if (!arrayChangesAdjFl.get(numBudgetPeriod))

                    comment = getCommentOfLedgerCollection(siteLedgerDV,
                            fiscalCalV.getFiscalCalender().getFiscalYear(),
                            numBudgetPeriod,
                            costCenterIdInt);

                else {

                    HashMap<Integer,String> arrayList = pForm.getHtComments().get(pForm.getSelectedBudgetYear() + "_" + String.valueOf(costCenterIdInt));
                    comment = (arrayList).get(numBudgetPeriod);

                }

                pForm.setComments(comment);
                session.setAttribute("Site.budget.info", budgetInfo);
                session.setAttribute("Site.budget.infoWithSpecifedAdjustment", budgetInfoWithSpecAdj);
            }
            catch (RemoteException e) {
                try {
                    ae = StringUtils.getUiErrorMess(e);
                } catch (Exception e1) {
                    ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
                }
            } catch (DataNotFoundException e) {
                e.printStackTrace();
            } /*catch (DataNotFoundException e) {

        }  */
        }
        return ae;
    }

    public static ActionErrors addAdjustment(HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        String siteId = (String) session.getAttribute("Site.id");
        String accountId = (String) session.getAttribute("Site.account.id");

        if (siteId == null) {
            ae.add("error", new ActionError("simpleGenericError", "Site Id not found"));
        }

        if (accountId == null) {
            ae.add("error", new ActionError("simpleGenericError", "Account Id not found"));
        }

        if (ae.size() > 0) {
            return ae;
        }

        StoreAdjustmentLedgerForm pForm = (StoreAdjustmentLedgerForm) form;

        if (!Utility.isSet(pForm.getSelectedBudgetPeriod()))
            ae.add("Period", new ActionError("variable.empty.error", "Period"));
        if (!Utility.isSet(pForm.getSelectedCostCenter()))
            ae.add("Cost Center", new ActionError("variable.empty.error", "Cost Center"));
        if (!Utility.isSet(pForm.getSelectedBudgetYear()))
            ae.add("Budget Year", new ActionError("variable.empty.error", "Budget Year"));
        if (!Utility.isSet(pForm.getComments()))
            ae.add("Comments", new ActionError("variable.empty.error", "Comments"));

        if (ae.size() > 0) {
            return ae;
        }

        String adjustment = pForm.getBudgetAdjustment();
        double adjustmentDouble;
        BigDecimal adjustmentBD;
        try {
            adjustmentDouble = Double.parseDouble(adjustment);
            adjustmentBD = new BigDecimal(adjustmentDouble).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            ae.add("error", new ActionError("error.simpleGenericError", "Invalid adjustment:<" + adjustment + ">"));
            return ae;
        }

        Hashtable<String, HashMap<Integer, BigDecimal>> htAdjustments = pForm.getHtAdjustments();
        Hashtable<String, HashMap<Integer, Boolean>> htChangesAdjustmentsFl = pForm.getHtChangesAdjustmentsFl();
        Hashtable<String, HashMap<Integer, String>> htComments = pForm.getHtComments();

        String selectedBudgetPeriod = pForm.getSelectedBudgetPeriod();
        HashMap<Integer, String> periods = pForm.getBudgetPeriods();
        int period = 1;
        for (; period <= periods.size(); period++) {
            if (periods.get(period).equals(selectedBudgetPeriod)) {
                break;
            }

        }

        if (period > periods.size()) {
            ae.add("error", new ActionError("error.simpleGenericError", "Can't set adjustment for this period"));
        }

        String comment = pForm.getComments();

        if (ae.size() > 0) {
            return ae;
        }

        int costCenterId = getCostCenterIdByNameFromCollection(pForm.getCostCentersCollection(), pForm.getSelectedCostCenter());
        String key = pForm.getSelectedBudgetYear() + "_" + costCenterId;
        HashMap<Integer, BigDecimal> arrayAdjustmentByPeriod = htAdjustments.get(key);
        HashMap<Integer, Boolean> arrayChangesAdjustmentByPeriodFl = htChangesAdjustmentsFl.get(key);
        HashMap<Integer, String> arrayCommentsByPeriod = htComments.get(key);

        if (arrayAdjustmentByPeriod == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "Can't set adjustment"));
            return ae;
        }

        if (period <= periods.size()) {
            arrayAdjustmentByPeriod.put(period, adjustmentBD);
            arrayChangesAdjustmentByPeriodFl.put(period, Boolean.TRUE);
            arrayCommentsByPeriod.put(period, comment);
        }

        ae = getDetail(request, form);

        return ae;

    }

    public static int getCostCenterIdByNameFromCollection(CostCenterDataVector costCentersDV, String name) {
        if (costCentersDV != null) {
            Iterator iterator = costCentersDV.iterator();
            CostCenterData costCenterData;
            while (iterator.hasNext()) {
                costCenterData = (CostCenterData) iterator.next();
                if (costCenterData.getShortDesc().equals(name))
                    return costCenterData.getCostCenterId();

            }
        }
        return 0;
    }

    public static ActionErrors saveAdjustments(HttpServletRequest request, ActionForm form) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String siteId = (String) session.getAttribute("Site.id");
        String accountId = (String) session.getAttribute("Site.account.id");
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null || (appUser != null && appUser.getUser() == null)) {
            ae.add("error", new ActionError("simpleGenericError", "Can't save.Unknown user data"));
            return ae;
        }
        if (siteId == null) {
            ae.add("error", new ActionError("simpleGenericError", "Site Id not found"));
        }
        if (accountId == null) {
            ae.add("error", new ActionError("simpleGenericError", "Account Id not found"));
        }
        if (ae.size() > 0) {
            return ae;
        }


        int siteIdInt;

        try {
            Integer.parseInt(accountId);
            siteIdInt = Integer.parseInt(siteId);
        } catch (NumberFormatException e) {
            ae.add("error", new ActionError("simpleGenericError",
                    "Invalid <siteId or accountId>:<siteId - " + siteId + "> , <accountId - " + accountId + ">"));
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            Site siteEJB = factory.getSiteAPI();

            SiteLedgerDataVector siteLDV = new SiteLedgerDataVector();
            StoreAdjustmentLedgerForm pForm = (StoreAdjustmentLedgerForm) form;
            Hashtable<String, HashMap<Integer,BigDecimal>> htAdjustments = pForm.getHtAdjustments();
            Hashtable<String, HashMap<Integer,Boolean>> htChangesAdjustmentsFl = pForm.getHtChangesAdjustmentsFl();
            Hashtable<String, HashMap<Integer,String>> htComments = pForm.getHtComments();
            Enumeration<String> keys = htAdjustments.keys();

            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                HashMap<Integer,BigDecimal> arrayAdjustmentsByPeriod = htAdjustments.get(key);
                HashMap<Integer,Boolean> arrayChangesAdjustmentsByPeriodFl = htChangesAdjustmentsFl.get(key);
                HashMap<Integer,String> arrayCommentsByPeriod = htComments.get(key);
                if (arrayAdjustmentsByPeriod != null) {
                    int index;
                    index = key.indexOf("_");
                    if (index == -1) {
                        ae.add("error", new ActionError("error.simoleGenericErrror", "Can't parse adjustment key:<" + key + ">"));
                        break;
                    } else {
                        String year = key.substring(0, index);
                        String costCenterId = key.substring(index + 1);
                        int yearInt;
                        int costCenterIdInt;
                        try {
                            yearInt = Integer.parseInt(year);
                            costCenterIdInt = Integer.parseInt(costCenterId);
                        } catch (NumberFormatException e) {
                            //ae.add("error",new ActionError("error.simpleGenericError","Invalid <key adjustment>:<"+key+">"));
                            e.printStackTrace();
                            break;
                        }

                        for (Integer period : arrayAdjustmentsByPeriod.keySet()) {

                            BigDecimal adjust = arrayAdjustmentsByPeriod.get(period);

                            if (arrayChangesAdjustmentsByPeriodFl.get(period)) {
                                SiteLedgerData siteLD = SiteLedgerData.createValue();
                                siteLD.setBudgetYear(yearInt);
                                siteLD.setCostCenterId(costCenterIdInt);
                                siteLD.setAmount(adjust);
                                siteLD.setOrderId(0);
                                siteLD.setComments(arrayCommentsByPeriod.get(period));
                                siteLD.setBudgetPeriod(period);
                                siteLD.setSiteId(siteIdInt);
                                siteLD.setEntryTypeCd(RefCodeNames.LEDGER_ENTRY_TYPE_CD.ADJUSTMENT);
                                siteLD.setFiscalCalenderId(getFiscalCalendarIdByYearFromCollection(pForm.getFiscalCalendarCollection(), yearInt));
                                siteLD.setAddBy(appUser.getUser().getUserName());
                                siteLD.setModBy(appUser.getUser().getUserName());
                                siteLDV.add(siteLD);
                            }

                        }
                    }
                }
            }
            if (ae.size() == 0) {
                try {
                    siteEJB.adjustSiteLedgerCollection(siteLDV);
                    init(request, form);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (APIServiceAccessException e) {
            e.printStackTrace();
        }
        return ae;
    }

    private static int getFiscalCalendarIdByYearFromCollection(FiscalCalenderViewVector fiscalCalendarVV, int yearInt) {
        for (Object oFiscalCalendarView : fiscalCalendarVV) {
            if (oFiscalCalendarView != null && ((FiscalCalenderView) oFiscalCalendarView).getFiscalCalender().getFiscalYear() == yearInt)
                return ((FiscalCalenderView) oFiscalCalendarView).getFiscalCalender().getFiscalCalenderId();

        }
        return 0;
    }

    public static int getNumBudgetPeriodByShortDesc(FiscalCalenderView pFiscalCal, String pShortDesc) {
        HashMap<Integer, String> mm_dd = getArrayMmdd(pFiscalCal);
        for (int i = 1; i <= mm_dd.size(); i++) {
            if ((mm_dd.get(i)).equals(pShortDesc))
                return (i);
        }
        return -1;
    }
}

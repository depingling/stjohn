package com.cleanwise.service.api.process.workflowrules;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.workflow.RuleRequestData;
import com.cleanwise.service.api.process.workflow.ProcessWorkflow;
import com.cleanwise.service.api.process.workflow.RuleResponseData;
import com.cleanwise.service.api.process.workflow.WORuleResponseData;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import org.apache.log4j.Logger;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Iterator;

/**
 */
public class WorkOrderBudgetSpendingForCCWorkflow implements ProcessWorkflow {
    protected Logger log = Logger.getLogger(this.getClass());
    
    private static String className = "WorkOrderBudgetSpendingForCCWorkflow";

    /**
     * Processes a Budget Data object
     *
     * @param workflowRuleRequest contains work order detail
     * @return succes flag
     * @throws Exception errors
     */
    public RuleResponseData process(RuleRequestData workflowRuleRequest) throws Exception {

        RuleResponseData ruleResponseData = new WORuleResponseData(RuleResponseData.STATUS_STOPED);
        WorkOrderDetailView workOrderDetail = (WorkOrderDetailView) workflowRuleRequest.getObjectForProcessing();

        Budget budgetEjb = APIAccess.getAPIAccess().getBudgetAPI();
        Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
        Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();
        WorkOrder workOrderEjb = APIAccess.getAPIAccess().getWorkOrderAPI();
        
        Locale locale = Locale.US;
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
        NumberFormat numFormatter = NumberFormat.getInstance(locale);
        numFormatter.setMinimumFractionDigits(2);

        WorkOrderData workOrderData = workOrderDetail.getWorkOrder();
        WorkOrderDetailDataVector woDetailDV = workOrderEjb.getWorkOrderDetails(workOrderDetail.getWorkOrder().getWorkOrderId());
        
        CostCenterData costCenter = null;
        FiscalCalenderView fiscalCal = null;
        BudgetData budget = null;
        
        if (workOrderData.getCostCenterId() > 0) {
            try {
                costCenter = orderEjb.getCostCenter(workOrderData.getCostCenterId());
            } catch (DataNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (costCenter == null) {
            log.error("Insufficient data for process. CostCenter for WorkOrder: " + workOrderData.getWorkOrderId() + " not found");
            ruleResponseData.setStatus(RuleResponseData.STATUS_STOPED);
            return ruleResponseData;
        }
        
        int accountId = accountEjb.getAccountIdForSite(workOrderData.getBusEntityId());
        if (accountId <= 0) {
            log.error("No Account for Site found. SiteId => " + workOrderData.getBusEntityId());
            throw new Exception("No Account for Site found. SiteId => " + workOrderData.getBusEntityId());
        }
        AccountData accountD = accountEjb.getAccountForSite(workOrderData.getBusEntityId());
        
        //Date workOrderDate = WorkOrderUtil.getActualWorkOrderDate(workOrderDetail.getWorkOrder());
        Date workOrderDate = workOrderDetail.getWorkOrder().getAddDate();
        
        int budgetBusEntityId = 0;
        if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET.equals(costCenter.getCostCenterTypeCd())) {
            budgetBusEntityId = accountId;
        } else if (RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET.equals(costCenter.getCostCenterTypeCd())) {
            budgetBusEntityId = workOrderData.getBusEntityId();
        } else {
            //unknown budget type
            log.error("Unknown Cost Center Type. CostCenterId => " + costCenter.getCostCenterId() + ", workOrderDate => " + workOrderDate);
            throw new Exception("Unknown Cost Center Type. CostCenterId => " + costCenter.getCostCenterId() + ", workOrderDate => " + workOrderDate);
        }
        
        fiscalCal = accountEjb.getFiscalCalenderV(accountId, workOrderDate);
        if (fiscalCal == null) {
            log.error("No Fiscal Calendar found. For AccountId => " + accountId + ", workOrderDate => " + workOrderDate);
            throw new Exception("No Fiscal Calendar found. For AccountId => " + accountId + ", workOrderDate => " + workOrderDate);
        }

        budget = budgetEjb.getWorkOrderBudgetData(budgetBusEntityId,
                                                  workOrderData.getCostCenterId(),
                                                  fiscalCal.getFiscalCalender().getFiscalYear());
        
        if (budget == null) {
            log.error("No budget found for process");
            throw new Exception("No budget found. For BusEntityId => " + budgetBusEntityId + ", workOrderDate => " + workOrderDate);
        }

        int budgetPeriod = budgetEjb.getAccountBudgetPeriod(accountId, workOrderData.getBusEntityId(), workOrderDate);
        if (budgetPeriod == 0){
            log.error("No Budget Period. BudgetId => " + budget.getBudgetId() + ", workOrderDate => " + workOrderDate);
            throw new Exception("No Budget Period. BudgetId => " + budget.getBudgetId() + ", workOrderDate => " + workOrderDate);
        }

        BudgetSpentCriteria crit = new BudgetSpentCriteria();
        
        crit.setBudgetTypeCd(costCenter.getCostCenterTypeCd());
        crit.setCostCenters(Utility.toIdVector(workOrderData.getCostCenterId()));
        crit.setBudgetYear(fiscalCal.getFiscalCalender().getFiscalYear());
        crit.setNumberOfBudgetPeriods(FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalCal));
        crit.setBusEntityId(budgetBusEntityId);
        BudgetSpentShortViewVector budgetSpentInfo = budgetEjb.getWorkOrderBudgetSpendInfo(crit);
        
        boolean stop = false;
        String ruleExp;
        String ruleExpValueStr;
        double ruleExpValue;
        StringBuffer report = new StringBuffer();

        WorkflowRuleData ruleData = workflowRuleRequest.getRuleData();

        ruleExp = ruleData.getRuleExp();
        ruleExpValueStr = ruleData.getRuleExpValue();
        try {
            ruleExpValue = Double.parseDouble(ruleExpValueStr);
        } catch (NumberFormatException e) {
            log.error("WorkOrderBudgetSpendingForCCRule. Error parsing 'ruleExpValue'. RuleId = " + ruleData.getWorkflowRuleId() +
                    ", workOrderDate => " + workOrderDate);
            throw new Exception("WorkOrderBudgetSpendingForCCRule. Error parsing 'ruleExpValue'. RuleId = " + ruleData.getWorkflowRuleId() +
                    ", workOrderDate => " + workOrderDate);
        }
        
        FiscalPeriodView fiscalInfo = accountEjb.getFiscalInfo(budgetBusEntityId);
        WorkOrderBudgetUtil woBudgetUtil = new WorkOrderBudgetUtil(budgetSpentInfo);
        
        BigDecimal allocated = new BigDecimal(0);
        BigDecimal spent = new BigDecimal(0);
        
        if (RefCodeNames.BUDGET_ACCRUAL_TYPE_CD.BY_FISCAL_YEAR.equals(accountD.getBudgetTypeCd())) {
            BudgetSpentShortViewVector budgetShortCollection = woBudgetUtil.getPeriodInfo(budgetBusEntityId,
                                                           fiscalCal.getFiscalCalender().getFiscalYear(),
                                                           1,
                                                           fiscalInfo.getCurrentFiscalPeriod());
            
            Iterator it = budgetShortCollection.iterator();
            BudgetSpentShortView sv;
            while (it.hasNext()) {
                sv = (BudgetSpentShortView) it.next();
                allocated = Utility.addAmt(sv.getAmountAllocated(), allocated);
                spent = Utility.addAmt(sv.getAmountSpent(), spent);
            }
        } else if (RefCodeNames.BUDGET_ACCRUAL_TYPE_CD.BY_PERIOD.equals(accountD.getBudgetTypeCd())) {
            //Only for the current Period
            allocated = woBudgetUtil.getAmountAllocated(budgetBusEntityId, fiscalCal.getFiscalCalender().getFiscalYear(), fiscalInfo.getCurrentFiscalPeriod());
            spent = woBudgetUtil.getAmountSpent(budgetBusEntityId, fiscalCal.getFiscalCalender().getFiscalYear(), fiscalInfo.getCurrentFiscalPeriod());
        } else {
            log.error("WorkOrderBudgetSpendingForCCRule. Error: Unknown BUDGET_ACCRUAL_TYPE_CD. AccountId => " + accountId + " workOrderDate => " + workOrderDate);
            throw new Exception("WorkOrderBudgetSpendingForCCRule. Error: Unknown BUDGET_ACCRUAL_TYPE_CD. AccountId => " + accountId + " workOrderDate => " + workOrderDate);
        }
        
        BigDecimal thisOrderAmount = WorkOrderUtil.getWorkOrderTotalCostSum(woDetailDV);
        BigDecimal siteBudgetWithThisOrder = Utility.addAmt(spent, thisOrderAmount);
        BigDecimal diff = Utility.subtractAmt(allocated, siteBudgetWithThisOrder);


        log.info("Money spent: " + spent);
        log.info("ThisOrderAmount: " + thisOrderAmount);
        log.info("Money allocated: " + allocated);
        log.info("Difference: " + diff);

        if (isTrueExp(allocated, siteBudgetWithThisOrder, ruleExp, ruleExpValue)) {
            report.append("Work Order has been checked with Workflow Rule: WorkOrderBudgetSpendingForCCWorkflow.");
            report.append("Allocated budget - ");
            report.append(getPrice(allocated, numFormatter));
            report.append(",");
            report.append("spent - ");
            report.append(getPrice(spent, numFormatter));
            report.append(",");
            report.append("this work order amount - ");
            report.append(getPrice(thisOrderAmount, numFormatter));
            report.append(".Process Date:");
            report.append(getDate(new Date(System.currentTimeMillis()), dateFormatter, timeFormatter));
            stop = true;
        }

        if(stop){
            ruleResponseData.setStatus(RuleResponseData.STATUS_STOPED);
        }else{
            ruleResponseData.setStatus(RuleResponseData.STATUS_PASSED);
        }

        return ruleResponseData;
    }

    private String getPrice(BigDecimal value, NumberFormat numFormatter) {
        if (value == null) {
            return "";
        } else {
            return numFormatter.format(value);
        }
    }

    private String getDate(Date date, DateFormat dateFormatter, DateFormat timeFormatter) {
        if (date == null) {
            return "";
        } else {
            return dateFormatter.format(date)+" "+timeFormatter.format(date);
        }
    }

    private void stop(String message) throws Exception {
        throw new Exception(message);
    }

    private boolean isTrueExp(BigDecimal allocated, BigDecimal siteBudgetWithThisOrder, String ruleExp, double ruleExpValue) {

        double remainingValue = allocated.doubleValue() - siteBudgetWithThisOrder.doubleValue();
                
        if (ruleExp.equals("<")) {
            return remainingValue < ruleExpValue;
        } else if (ruleExp.equals("<=")) {
            return remainingValue <= ruleExpValue;
        } else if (ruleExp.equals("==")) {
            return remainingValue == ruleExpValue;
        } else if (ruleExp.equals(">")) {
            return remainingValue > ruleExpValue;
        } else if (ruleExp.equals(">=")) {
            return remainingValue >= ruleExpValue;
        }

        return false;
    }

}

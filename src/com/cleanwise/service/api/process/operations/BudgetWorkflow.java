package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.NumberFormat;

/**
 */
public class BudgetWorkflow {

    private static String className = "BudgetWorkflow";
    public static String BY_PERIOD = "BY_PERIOD";
    public static String BY_FISCAL_YEAR = "BY_FISCAL_YEAR";

    /**
     * Processes a Budget Data object
     *
     * @param workOrderDetail work order detail
     * @return succes flag
     * @throws Exception errors
     */
    public Boolean processBudget(WorkOrderDetailView workOrderDetail) throws Exception {

        Budget budgetEjb = APIAccess.getAPIAccess().getBudgetAPI();
        Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
        Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();
        Workflow workflowEjb = APIAccess.getAPIAccess().getWorkflowAPI();

        Locale locale = Locale.US;
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
        NumberFormat numFormatter = NumberFormat.getInstance(locale);
        numFormatter.setMinimumFractionDigits(2);

        WorkOrderData workOrderData = workOrderDetail.getWorkOrder();

        BudgetData budget = null;
        CostCenterData costCenter = null;
        WorkflowData workflow = null;
        WorkflowRuleDataVector workflowRules = new WorkflowRuleDataVector();
        if (workOrderData.getCostCenterId() > 0) {
            try {
                workflow = workflowEjb.fetchWorkflowForSite(workOrderData.getBusEntityId(), RefCodeNames.WORKFLOW_TYPE_CD.WORK_ORDER_WORKFLOW);
                workflowRules = workflowEjb.getWorkflowRulesCollection(workflow.getWorkflowId());
                costCenter = orderEjb.getCostCenter(workOrderData.getCostCenterId());
            } catch (DataNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (workflow == null || workflowRules.isEmpty() || costCenter == null) {
            return true;
        }

        int accountId = accountEjb.getAccountIdForSite(workOrderData.getBusEntityId());
        if (accountId <= 0) {
            throw new Exception("No account found.SiteId => " + workOrderData.getBusEntityId());
        }

        //Date orderDate = WorkOrderUtil.getActualWorkOrderDate(workOrderDetail.getWorkOrder());
        Date orderDate = workOrderDetail.getWorkOrder().getAddDate();

        FiscalCalenderView fiscalCal = accountEjb.getFiscalCalenderV(accountId, orderDate);
        if (fiscalCal == null) {
            throw new Exception("No fiscal calendar found.AccountId => " + accountId+", orderDate => "+orderDate);
        }

        budget = budgetEjb.getWorkOrderBudgetData(workOrderData.getBusEntityId(), workOrderData.getCostCenterId(), fiscalCal.getFiscalCalender().getFiscalYear());
        if(budget == null){
            return true;
        }

        int budgetPeriod = budgetEjb.getAccountBudgetPeriod(accountId, workOrderData.getBusEntityId(), orderDate);
        if(budgetPeriod==0){
            throw new Exception();
        }

        BudgetSpentCriteria crit = new BudgetSpentCriteria();
        if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET.equals(costCenter.getCostCenterTypeCd())) {
            crit.setBudgetTypeCd(costCenter.getCostCenterTypeCd());
            crit.setBusEntityId(accountId);
            crit.setCostCenters(Utility.toIdVector(workOrderData.getCostCenterId()));
            crit.setBudgetYear(fiscalCal.getFiscalCalender().getFiscalYear());
            crit.setNumberOfBudgetPeriods(FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalCal));
        } else if (RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET.equals(costCenter.getCostCenterTypeCd())) {
            crit.setBudgetTypeCd(costCenter.getCostCenterTypeCd());
            crit.setBusEntityId(workOrderData.getBusEntityId());
            crit.setCostCenters(Utility.toIdVector(workOrderData.getCostCenterId()));
            crit.setBudgetYear(fiscalCal.getFiscalCalender().getFiscalYear());
            crit.setNumberOfBudgetPeriods(FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalCal));
        } else {
            throw new Exception();
        }

        BudgetSpentShortViewVector budgetSpentInfo = budgetEjb.getWorkOrderBudgetSpendInfo(crit);

        WorkOrderBudgetUtil woBudgetUtil = new WorkOrderBudgetUtil(budgetSpentInfo);

        boolean stop = false;
        String ruleExp;
        String ruleExpValue;
        String action;
        StringBuffer report = new StringBuffer();
        Iterator it = workflowRules.iterator();
        while (it.hasNext()) {
            WorkflowRuleData rule = (WorkflowRuleData) it.next();
            ruleExp = rule.getRuleExp();
            ruleExpValue = rule.getRuleExpValue();
            action = rule.getRuleAction();
            if (BY_PERIOD.equals(ruleExpValue)) {

                BigDecimal spent = woBudgetUtil.getAmountSpent(workOrderData.getBusEntityId(),fiscalCal.getFiscalCalender().getFiscalYear(),budgetPeriod);
                BigDecimal allocated = woBudgetUtil.getAmountAllocated(workOrderData.getBusEntityId(),fiscalCal.getFiscalCalender().getFiscalYear(),budgetPeriod);
                BigDecimal thisOrderAmmount = WorkOrderUtil.getWorkOrderAmount(workOrderDetail.getWorkOrderItems());
                BigDecimal siteBudgetWithThisOrder = Utility.addAmt(spent,thisOrderAmmount);
                BigDecimal diff = Utility.subtractAmt(allocated, siteBudgetWithThisOrder);

                if (isTrueExp(allocated, siteBudgetWithThisOrder, ruleExp, ruleExpValue)) {

                    report.append("Work order status changed to Pending Approval by Workflow Rule WorkFlowBudget.");
                    report.append("Allocated budget - ");
                    report.append(getPrice(allocated,numFormatter));
                    report.append(",");
                    report.append("spent - ");
                    report.append(getPrice(spent,numFormatter));
                    report.append(",");
                    report.append("this work order amount - ");
                    report.append(getPrice(thisOrderAmmount,numFormatter));
                    report.append(".Process Date: ");
                    report.append(getDate(new Date(System.currentTimeMillis()),dateFormatter,timeFormatter));
                    performRuleAction(workOrderData.getWorkOrderId(),report.toString(), action);
                    stop = true;
                }
            } else if (BY_FISCAL_YEAR.equals(ruleExpValue)) {
                BigDecimal spent = woBudgetUtil.getAmountSpent(workOrderData.getBusEntityId(),fiscalCal.getFiscalCalender().getFiscalYear());
                BigDecimal allocated = woBudgetUtil.getAmountAllocated(workOrderData.getBusEntityId(),fiscalCal.getFiscalCalender().getFiscalYear());
                BigDecimal thisOrderAmmount = WorkOrderUtil.getWorkOrderAmount(workOrderDetail.getWorkOrderItems());
                BigDecimal siteBudgetWithThisOrder = Utility.addAmt(spent,thisOrderAmmount);
                BigDecimal diff = Utility.subtractAmt(allocated, siteBudgetWithThisOrder);

                if (isTrueExp(allocated, siteBudgetWithThisOrder, ruleExp, ruleExpValue)) {

                    report.append("Work order status changed to Pending Approval by Workflow Rule WorkFlowBudget.");
                    report.append("Allocated budget - ");
                    report.append(getPrice(allocated,numFormatter));
                    report.append(",");
                    report.append("spent - ");
                    report.append(getPrice(spent,numFormatter));
                    report.append(",");
                    report.append("this work order amount - ");
                    report.append(getPrice(thisOrderAmmount,numFormatter));
                    report.append(".Process Date:");
                    report.append(getDate(new Date(System.currentTimeMillis()),dateFormatter,timeFormatter));
                    performRuleAction(workOrderData.getWorkOrderId(),report.toString(), action);
                    stop = true;
                }
            }
        }
        return Boolean.valueOf(stop);
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

    private boolean isTrueExp(BigDecimal allocated, BigDecimal siteBudgetWithThisOrder, String ruleExp, String ruleExpValue) {
        if (ruleExp.equals("<")) {
            return siteBudgetWithThisOrder.doubleValue() < allocated.doubleValue();
        } else if (ruleExp.equals("<=")) {
            return siteBudgetWithThisOrder.doubleValue() <= allocated.doubleValue();
        } else if (ruleExp.equals("==")) {
            return siteBudgetWithThisOrder.doubleValue() == allocated.doubleValue();
        } else if (ruleExp.equals(">")) {
            return siteBudgetWithThisOrder.doubleValue() > allocated.doubleValue();
        } else if (ruleExp.equals(">=")) {
            return siteBudgetWithThisOrder.doubleValue() >= allocated.doubleValue();
        }
        return false;
    }

    private void performRuleAction(int workOrderId, String report,String action) throws Exception {

        WorkOrder wrkEjb = APIAccess.getAPIAccess().getWorkOrderAPI();
        WorkOrderNoteData note = WorkOrderNoteData.createValue();
        note.setWorkOrderId(workOrderId);
        note.setShortDesc("Process note");
        note.setTypeCd(RefCodeNames.WORK_ORDER_NOTE_TYPE_CD.PROCESS_NOTE);

        note.setNote(report);

        wrkEjb.updateWorkOrderNoteData(note, className);

        if ("Forward for approval".equals(action)) {
            wrkEjb.updateStatus(workOrderId, RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL, className);
        }
    }

}

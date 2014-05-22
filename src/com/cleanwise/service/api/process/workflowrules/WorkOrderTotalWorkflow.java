package com.cleanwise.service.api.process.workflowrules;

import com.cleanwise.service.api.process.workflow.ProcessWorkflow;
import com.cleanwise.service.api.process.workflow.RuleResponseData;
import com.cleanwise.service.api.process.workflow.RuleRequestData;
import com.cleanwise.service.api.process.workflow.WORuleResponseData;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.*;

import java.util.Locale;
import java.util.Date;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.math.BigDecimal;
import org.apache.log4j.Logger;


/**
 */
public class WorkOrderTotalWorkflow implements ProcessWorkflow {
    protected Logger log = Logger.getLogger(this.getClass());

    private static String className = "WorkOrderTotalWorkflow";

    /**
     * Processes a Work Order Total Amount
     *
     * @param workflowRuleRequest contains work order detail  and rule data
     * @return succes flag
     * @throws Exception errors
     */
    public RuleResponseData process(RuleRequestData workflowRuleRequest) throws Exception {

        RuleResponseData ruleResponseData = new WORuleResponseData(RuleResponseData.STATUS_STOPED);
        WorkOrderDetailView workOrderDetail = (WorkOrderDetailView) workflowRuleRequest.getObjectForProcessing();
        WorkflowRuleData rule = workflowRuleRequest.getRuleData();
        ruleResponseData.setRuleData(rule);

        WorkOrder workOrderEjb = APIAccess.getAPIAccess().getWorkOrderAPI();
        WorkOrderDetailDataVector woDetailDV = workOrderEjb.getWorkOrderDetails(workOrderDetail.getWorkOrder().getWorkOrderId());
        
        String ruleExp = rule.getRuleExp();
        String ruleExpValue = rule.getRuleExpValue();

        BigDecimal workOrderTotal = WorkOrderUtil.getWorkOrderTotalCostSum(woDetailDV);

        if (isTrueExp(workOrderTotal, ruleExp, ruleExpValue)) {
            ruleResponseData.setStatus(RuleResponseData.STATUS_STOPED);
        } else {
            ruleResponseData.setStatus(RuleResponseData.STATUS_PASSED);
        }

        return ruleResponseData;
    }

    private boolean isTrueExp(BigDecimal wrkTotal, String ruleExp, String ruleExpValue) {

        BigDecimal expVal = new BigDecimal(ruleExpValue);
        if (ruleExp.equals("<")) {
            return wrkTotal.doubleValue() < expVal.doubleValue();
        } else if (ruleExp.equals("<=")) {
            return wrkTotal.doubleValue() <= expVal.doubleValue();
        } else if (ruleExp.equals("==")) {
            return wrkTotal.doubleValue() == expVal.doubleValue();
        } else if (ruleExp.equals(">")) {
            return wrkTotal.doubleValue() > expVal.doubleValue();
        } else if (ruleExp.equals(">=")) {
            return wrkTotal.doubleValue() >= expVal.doubleValue();
        }

        return false;
    }

}

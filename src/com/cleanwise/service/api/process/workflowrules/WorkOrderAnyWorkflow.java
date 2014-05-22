package com.cleanwise.service.api.process.workflowrules;

import com.cleanwise.service.api.process.workflow.ProcessWorkflow;
import com.cleanwise.service.api.process.workflow.RuleResponseData;
import com.cleanwise.service.api.process.workflow.RuleRequestData;
import com.cleanwise.service.api.process.workflow.WORuleResponseData;
import com.cleanwise.service.api.value.*;
import org.apache.log4j.Logger;


/**
 */
public class WorkOrderAnyWorkflow implements ProcessWorkflow {
    protected Logger log = Logger.getLogger(this.getClass());
    
    private static String className = "WorkOrderAnyWorkflow";

    /**
     * Processes a Work Order Any workflow rule
     *
     * @param workflowRuleRequest contains work order detail  and rule data
     * @return succes flag
     * @throws Exception errors
     */
    public RuleResponseData process(RuleRequestData workflowRuleRequest) throws Exception {

        RuleResponseData ruleResponseData = new WORuleResponseData(RuleResponseData.STATUS_STOPED);
        WorkflowRuleData rule = workflowRuleRequest.getRuleData();
        ruleResponseData.setRuleData(rule);
        
        ruleResponseData.setStatus(RuleResponseData.STATUS_STOPED);
        
        return ruleResponseData;
    }

}

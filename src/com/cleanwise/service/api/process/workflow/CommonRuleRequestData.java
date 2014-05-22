package com.cleanwise.service.api.process.workflow;

import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.value.WorkflowRuleData;

public class CommonRuleRequestData implements RuleRequestData{
    protected Object objectForProcessing;
    protected WorkflowRuleData ruleData;

    public CommonRuleRequestData(WorkflowRuleData ruleData,Object objectForProcessing) {
        this.objectForProcessing = objectForProcessing;
        this.ruleData = ruleData;
    }

    public Object getObjectForProcessing() {
        return this.objectForProcessing;
    }

    public void setObjectForProcessing(Object objectForProcessing) throws PipelineException {
        this.objectForProcessing = objectForProcessing;
    }

    public WorkflowRuleData getRuleData() {
        return this.ruleData;
    }

}

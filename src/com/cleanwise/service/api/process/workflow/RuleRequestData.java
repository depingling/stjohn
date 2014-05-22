package com.cleanwise.service.api.process.workflow;

import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.value.WorkflowRuleData;

public interface RuleRequestData {
    public final static String INCORRECT_OBJECT_TYPE = "INCORRECT_OBJECT_TYPE";

    public Object getObjectForProcessing();
    public void setObjectForProcessing(Object object) throws PipelineException;
    public WorkflowRuleData getRuleData();

}

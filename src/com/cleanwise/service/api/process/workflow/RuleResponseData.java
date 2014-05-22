package com.cleanwise.service.api.process.workflow;

import com.cleanwise.service.api.value.WorkflowRuleData;


public interface RuleResponseData {

    public static final String STATUS_PASSED = "STATUS_PASSED";
    public static final String STATUS_STOPED = "STATUS_STOPED";

    public String getStatus();
    public void setStatus(String status);

    public void setRuleData(WorkflowRuleData rule);
    public WorkflowRuleData getRuleData();
}

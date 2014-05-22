package com.cleanwise.service.api.process.workflow;

public interface ProcessWorkflow {
    public RuleResponseData process(RuleRequestData ruleRequestData) throws Exception;
}

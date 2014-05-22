package com.cleanwise.service.api.process.workflow;
import com.cleanwise.service.api.value.WorkflowRuleData;

public class WORuleResponseData implements RuleResponseData{

    private String status;
	private WorkflowRuleData rule;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WORuleResponseData(String status) {
        this.status = status;
    }
    public void setRuleData(WorkflowRuleData rule) {
		this.rule = rule;
	}
	
    public WorkflowRuleData getRuleData() {
		return this.rule;
	}
	
}

package com.cleanwise.service.api.process.workflow;


public class CommonProcessingData implements WorkfolowProcessingData{

    private String whatNext;
    private String reason;
    private static final long serialVersionUID = 192081812468828071L;

    public CommonProcessingData() {
        this.whatNext = GO_FIRST_STEP;
    }

    public String getWhatNext() {
        return this.whatNext;
    }

    public void setWhatNext(String whatNext) {
        this.whatNext = whatNext;
    }

    public void processRuleResult(RuleResponseData ruleResponseData) {
        if(RuleResponseData.STATUS_PASSED.equals(ruleResponseData.getStatus())){
            this.whatNext = WorkfolowProcessingData.GO_NEXT;
        } else {
            this.whatNext = WorkfolowProcessingData.FORWARD_FOR_APPROVE;
        }
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}

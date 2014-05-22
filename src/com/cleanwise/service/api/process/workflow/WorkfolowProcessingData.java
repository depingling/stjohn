package com.cleanwise.service.api.process.workflow;

import java.io.Serializable;

public interface WorkfolowProcessingData extends Serializable{
    

    //Return codedes
    public static final String STOP_AND_RETURN     = "STOP_AND_RETURN";
    public static final String GO_NEXT             = "GO_NEXT";
    public static final String REPEAT              = "REPEAT";
    public static final String GO_FIRST_STEP       = "GO_FIRST_STEP";
    public static final String GO_BREAK_POINT      = "GO_BREAK_POINT";
    public static final String CHECK_ERROR         = "CHECK_ERROR";
    public static final String CHECK_SYSTEM_ERROR  = "CHECK_SYSTEM_ERROR";
    public static final String FORWARD_FOR_APPROVE = "FORWARD_FOR_APPROVE";
    public static final String STATUS_PASSED       =  "STATUS_PASSED";   

    public String getWhatNext();

    public void processRuleResult(RuleResponseData ruleResponseData);
}

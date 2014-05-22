package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.util.PipelineUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.sql.Connection;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         24.04.2007
 * Time:         1:10:39
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public abstract class PipelineBaton implements Serializable {

    //Return codedes
    public static final String STOP = "STOP_AND_RETURN";
    public static final String GO_NEXT = "GO_NEXT";
    public static final String REPEAT = "REPEAT";
    public static final String GO_FIRST_STEP = "GO_FIRST_STEP";
    public static final String GO_BREAK_POINT = "GO_BREAK_POINT";

    private HashMap errors = new HashMap();
    private String localeCd = null;
    private String mWhatNext = null;


    public class OrderError implements Serializable {
        public String error;
        public String shortDesc;
        public String text;
        public String orderStatus;
        public int lineNum;
        public int workflowRuleId;
        // veronika
        public String messageKey;
        public String arg0;
        public String arg0TypeCd;
        public String arg1;
        public String arg1TypeCd;
        public String arg2;
        public String arg2TypeCd;
        public String arg3;
        public String arg3TypeCd;

        public OrderError(String pError, String pShortDesc,
                          String pOrderStatus, int pLineNum, int pWorkflowRuleId,
                          String pMessageKey,
                          Object pArg0, String pArg0TypeCd,
                          Object pArg1, String pArg1TypeCd,
                          Object pArg2, String pArg2TypeCd,
                          Object pArg3, String pArg3TypeCd) {
            shortDesc = pShortDesc;
            error = pError;
            orderStatus = pOrderStatus;
            lineNum = pLineNum;
            workflowRuleId = pWorkflowRuleId;
            messageKey = pMessageKey;
            arg0 = PipelineUtil.catStr(pArg0, PipelineUtil.ARG_LENGTH);
            arg0TypeCd = pArg0TypeCd;
            arg1 = PipelineUtil.catStr(pArg1, PipelineUtil.ARG_LENGTH);
            arg1TypeCd = pArg1TypeCd;
            arg2 = PipelineUtil.catStr(pArg2, PipelineUtil.ARG_LENGTH);
            arg2TypeCd = pArg2TypeCd;
            arg3 = PipelineUtil.catStr(pArg3, PipelineUtil.ARG_LENGTH);
            arg3TypeCd = pArg3TypeCd;
        }
    }

    public void setErrors(HashMap pErrors) {
        errors = pErrors;
    }

    public HashMap getErrors() {
        return errors;
    }

    public String addError(Connection pCon, String pError,
                           String pOrderStatus, int pLineNum, int pWorkflowRuleId) {
        return addError(pCon, pError, null, pOrderStatus, pLineNum, pWorkflowRuleId);
    }

    public String addError(Connection pCon, String pError, String pShortDesc,
                           String pOrderStatus, int pLineNum, int pWorkflowRuleId) {
        return addError(pCon, pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                null, null, null, null, null, null, null, null, null);
    }

    public String addError(Connection pCon, String pError, String pShortDesc,
                           String pOrderStatus, int pLineNum, int pWorkflowRuleId,
                           String pMessageKey) {
        return addError(pCon, pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                pMessageKey, null, null, null, null, null, null, null, null);
    }

    public String addError(Connection pCon, String pError,
                           String pOrderStatus, int pLineNum, int pWorkflowRuleId,
                           String pMessageKey) {
        return addError(pCon, pError, null, pOrderStatus, pLineNum, pWorkflowRuleId,
                pMessageKey, null, null, null, null, null, null, null, null);
    }

    public String addError(Connection pCon, String pError, String pShortDesc,
                           String pOrderStatus, int pLineNum, int pWorkflowRuleId,
                           String pMessageKey, String pArg0, String pArg0TypeCd) {
        return addError(pCon, pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                pMessageKey, pArg0, pArg0TypeCd, null, null, null, null, null, null);
    }

    public String addError(Connection pCon, String pError, String pShortDesc,
                           String pOrderStatus, int pLineNum, int pWorkflowRuleId,
                           String pMessageKey,
                           String pArg0, String pArg0TypeCd,
                           String pArg1, String pArg1TypeCd) {
        return addError(pCon, pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                pMessageKey, pArg0, pArg0TypeCd, pArg1, pArg1TypeCd, null, null, null, null);
    }

    public String addError(Connection pCon, String pError, String pShortDesc,
                           String pOrderStatus, int pLineNum, int pWorkflowRuleId,
                           String pMessageKey,
                           String pArg0, String pArg0TypeCd,
                           String pArg1, String pArg1TypeCd,
                           String pArg2, String pArg2TypeCd
    ) {
        return addError(pCon, pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                pMessageKey, pArg0, pArg0TypeCd, pArg1, pArg1TypeCd, pArg2, pArg2TypeCd, null, null);
    }

    public String addError(Connection pCon, String pError, String pShortDesc,
                           String pOrderStatus, int pLineNum, int pWorkflowRuleId,
                           String pMessageKey,
                           Object[] pArgs,
                           String[] pArgsTypes
    ) {
        Object[] args = new Object[4];
        String[] types = new String[4];
        if (pArgs != null) {
            for (int i = 0; i < pArgs.length; i++) {
                args[i] = pArgs[i];
            }
            for (int i = pArgs.length; i < args.length; i++) {
                args[i] = null;
            }
        }
        if (pArgsTypes != null) {
            for (int i = 0; i < pArgsTypes.length; i++) {
                types[i] = pArgsTypes[i];
            }
            for (int i = pArgsTypes.length; i < types.length; i++) {
                types[i] = null;
            }
        }

        return addError(pCon, pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                pMessageKey, args[0], types[0], args[1], types[1], args[2], types[2], args[3], types[3]);
    }


    public String addError(Connection pCon, String pError, String pShortDesc,
                           String pOrderStatus, int pLineNum, int pWorkflowRuleId,
                           String pMessageKey,
                           Object pArg0, String pArg0TypeCd,
                           Object pArg1, String pArg1TypeCd,
                           Object pArg2, String pArg2TypeCd,
                           Object pArg3, String pArg3TypeCd
    ) {

        if (errors == null) errors = new HashMap();
        OrderError oe = new OrderError(pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                pMessageKey, pArg0, pArg0TypeCd, pArg1, pArg1TypeCd, pArg2, pArg2TypeCd, pArg3, pArg3TypeCd);
      //STJ-5604
        oe.text = PipelineUtil.translateMessage(pMessageKey,
                localeCd,
                pArg0, pArg0TypeCd, pArg1, pArg1TypeCd, pArg2, pArg2TypeCd,
                pArg3, pArg3TypeCd);
        ArrayList errorAL = (ArrayList) errors.get(pError);
        if (errorAL == null) {
            errorAL = new ArrayList();
            errorAL.add(oe);
            errors.put(pError, errorAL);
        } else {
            errorAL.add(oe);
        }
        return null;
    }


    public boolean hasError(String pError) {
        OrderError oe = (OrderError) errors.get(pError);
        if (oe == null) return false;
        return true;
    }

    public ArrayList getError(String pError) {
        ArrayList oe = (ArrayList) errors.get(pError);
        return oe;
    }


    public String getLocaleCd() {
        return localeCd;
    }

    public void setLocaleCd(String localeCd) {
        this.localeCd = localeCd;
    }

    public String getWhatNext() {
        return mWhatNext;
    }

    public void setWhatNext(String pValue) {
        this.mWhatNext = pValue;
    }
}

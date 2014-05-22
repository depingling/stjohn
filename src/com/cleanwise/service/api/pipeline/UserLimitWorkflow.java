package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.UniversalDAO;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.BudgetUtil;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;
import org.apache.log4j.Logger;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Title:        UserLimitWorkflow
 * Description:  Workflow Rule
 * Purpose:      ISS UK rule,keeps track of the order totals per site
 * per user within the number of days configured to the workflow rule
 * Functional Specs: http://bugzilla.espendwise.com/bugzilla/show_bug.cgi?id=2382
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         03.11.2009
 * Time:         16:50:11
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class UserLimitWorkflow implements OrderPipeline {

    private static final Logger log = Logger.getLogger(UserLimitWorkflow.class);

    public static final String STRING = "STRING";

    /**
     * Process this pipeline.
     *
     * @param pBaton the order request object to act upon
     * @param pCon   a active database connection
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
                                      OrderPipelineActor pActor,
                                      Connection pCon,
                                      APIAccess pFactory) throws PipelineException {
        try {

            log.info("process()=> BEGIN");

            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            if (!shouldPipelineRun(pBaton)) {
                return pBaton;
            }

            boolean evalNextRule = true;
            //Iterate through the rules for the site.
            log.info("process()=> Iterate through the rules for the site.");
            WorkflowRuleDataVector wfrv = pBaton.getWorkflowRuleDataVector(RefCodeNames.WORKFLOW_RULE_TYPE_CD.USER_LIMIT);
            for (int ruleidx = 0; evalNextRule && ruleidx < wfrv.size(); ruleidx++) {

                WorkflowRuleData rd = (WorkflowRuleData) wfrv.get(ruleidx);

                log.info("process()=> rule: " + rd);
                try {

                    BigDecimal userLimit = new BigDecimal(rd.getRuleExpValue());
                    int numberofDays = Utility.parseInt(rd.getRuleExp());

                    log.info("process()=> userLimit: " + rd.getRuleExpValue());
                    log.info("process()=> numberofDays: " + numberofDays);

                    BigDecimal ordersTotal;
                    if (numberofDays > 0) {

                        Date dateRangeBegin = getDateRangeBegin(numberofDays);
                        Date dateRangeEnd = getDateRangeEnd();

                        log.info("process()=> dateRange: " + dateRangeBegin + " - " + dateRangeEnd);

                        BigDecimal prevOrdersAmt = getOrdersTotalWithinPeriod(pCon,
                                pBaton.getOrderData().getSiteId(),
                                dateRangeBegin,
                                dateRangeEnd,
                                Utility.toIdVector(pBaton.getOrderData().getOrderId()));


                        BigDecimal currOrderAmt = BudgetUtil.calculateOrderTotal(pBaton.getOrderData(), pBaton.getOrderMetaDataVector());

                        log.info("process()=> prevOrdersAmt: " + prevOrdersAmt);
                        log.info("process()=> currOrderAmt: " + currOrderAmt);

                        ordersTotal = Utility.addAmt(prevOrdersAmt, currOrderAmt);

                    } else {

                        // if number of days equals zero (0) the system would allow an unlimited number
                        // of orders under the User Order Total
                        //  amount and only forward an order
                        // IF the single order amount exceeds the limit
                        ordersTotal = BudgetUtil.calculateOrderTotal(pBaton.getOrderData(), pBaton.getOrderMetaDataVector());
                    }

                    ordersTotal = ordersTotal.setScale(BigDecimal.ROUND_HALF_UP, 2);
                    userLimit = userLimit.setScale(BigDecimal.ROUND_HALF_UP, 2);

                    log.info("process()=> ordersTotal: " + ordersTotal.doubleValue());
                    log.info("process()=> userLimit: " + userLimit.doubleValue());

                    if (ordersTotal.doubleValue() >= userLimit.doubleValue()) {
                        evalNextRule = performRuleAction(pCon, pBaton, rd);
                    }

                } catch (Exception exc) {
                    log.error("process() =>  Exception: " + exc.getMessage());
                    exc.printStackTrace();
                    pBaton.addError(
                            pCon,
                            OrderPipelineBaton.WORKFLOW_RULE_WARNING,
                            RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,
                            0,
                            rd.getWorkflowRuleId(),
                            "pipeline.message.workflowRuleWarning");
                }
            }

            log.info("process()=> END");

            return pBaton;

        }
        catch (Exception exc) {
            throw new PipelineException(exc.getMessage());
        }

    }

    private BigDecimal getOrdersTotalWithinPeriod(Connection pCon,
                                         int pSiteId,
                                         Date pDateRangeBegin,
                                         Date pDateRangeEnd,
                                         IdVector pExcludedOrderIds) throws Exception {

        log.info("getOrdersTotalAmt()=> BEGIN");

        BigDecimal ordersTotal = null;

        IdVector orderIds = OrderDAO.getOrderIdsWithinPeriod(pCon,
                pSiteId,
                pDateRangeBegin,
                pDateRangeEnd,
                getStatusList());

        orderIds.removeAll(pExcludedOrderIds);
        log.info("getOrdersTotalAmt()=> orderIds size : "+orderIds.size());

        List<IdVector> orderIdsPacks = Utility.createPackages(orderIds, 1000);
        for (IdVector pack : orderIdsPacks) {
            String sql = OrderDAO.getOrdersTotalAmtSQL(IdVector.toCommaString(pack));
            ArrayList dataList = UniversalDAO.getData(pCon, sql);
            for (Object o : dataList) {
                BigDecimal packsum = Utility.parseBigDecimal(((UniversalDAO.dbrow) o).getStringValue(0));
                ordersTotal = Utility.addAmt(ordersTotal, packsum);
            }
        }

        log.info("getOrdersTotalAmt()=> END.");

        return Utility.bdNN(ordersTotal);

    }

    private boolean shouldPipelineRun(OrderPipelineBaton pBaton) {

        OrderData orderD = pBaton.getOrderData();

        int siteId = orderD.getSiteId();
        if (siteId <= 0) {
            log.info("shouldPipelineRun()=> siteId <= 0");
            return false;
        }

        if (pBaton.hasErrors()) {
            log.info("shouldPipelineRun()=> pBaton.hasErrors()");
            return false;
        }

        String wfrcd = pBaton.getUserWorkflowRoleCd();
        if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals(wfrcd)) {
            // No need to check the rules since this user
            // has the authority to overide them.
            return false;
        }

        WorkflowRuleDataVector wfrv;
        wfrv = pBaton.getWorkflowRuleDataVector(RefCodeNames.WORKFLOW_RULE_TYPE_CD.USER_LIMIT);
        log.info("shouldPipelineRun()=> size of rule:" + wfrv.size());
        if (wfrv.size() <= 0) {
            log.info("shouldPipelineRun()=> no workflow rules");
            return false;
        }

        return true;
    }

    private boolean performRuleAction(Connection pCon,
                                      OrderPipelineBaton pBaton,
                                      WorkflowRuleData pWorkflowRule) {
        int nd = Utility.parseInt(pWorkflowRule.getRuleExp());
        if (nd > 0) {
            return OrderPipelineActor.performRuleAction(pCon,
                    pBaton,
                    pWorkflowRule,
                    "pipeline.message.orderExceededHisLimitWithinDays",
                    new Object[]{pWorkflowRule.getRuleExpValue(), pWorkflowRule.getRuleExp()},
                    new String[]{STRING, STRING},
                    null,
                    pBaton.getBypassWkflRuleActionCd());
        } else {
            return OrderPipelineActor.performRuleAction(pCon,
                    pBaton,
                    pWorkflowRule,
                    "pipeline.message.orderExceededHisLimit",
                    new Object[]{pWorkflowRule.getRuleExpValue()},
                    new String[]{STRING},
                    null,
                    pBaton.getBypassWkflRuleActionCd());
        }
    }

    private Date getDateRangeBegin(int pValue) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -1 * pValue);
        return calendar.getTime();
    }

    private Date getDateRangeEnd() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        return calendar.getTime();
    }

    private List getStatusList() {
        return Utility.getAsList(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED,
                RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL,
                RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW,
                RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
    }
}


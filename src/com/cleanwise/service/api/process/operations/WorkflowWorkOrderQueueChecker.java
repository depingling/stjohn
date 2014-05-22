package com.cleanwise.service.api.process.operations;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import java.sql.*;
import javax.naming.*;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.WorkOrderData;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class WorkflowWorkOrderQueueChecker extends ApplicationServicesAPI {
    public static final String USER_NAME = "WorkflowWorkOrderQueueChecker";

    private static final Logger log = Logger.getLogger(WorkflowWorkOrderQueueChecker.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    public void startQueueCheck(String command) throws Exception {
            Connection conn = null;
            try {
                    conn = getConnection();
                    Date runForDate = new Date();
                    runForDate = sdf.parse(sdf.format(runForDate));

                    WorkflowWoQueueDataVector workflowRules = getWorkflowRules(conn,
                               RefCodeNames.WORKFLOW_RULE_ACTION.PENDING_REVIEW);
                    
                    if (!workflowRules.isEmpty()) {
                        if ("OnHand".equalsIgnoreCase(command)) {
                            processWorkflowRules(conn, workflowRules);
                        }
                    }
            } catch (Exception e) {
                    throw processException(e);
            } finally {
                    closeConnection(conn);
            }
    }
    
    private WorkflowWoQueueDataVector getWorkflowRules(Connection conn, String entryType)
                                                       throws Exception {
            // search for active workflow rules in queue table
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(WorkflowWoQueueDataAccess.SHORT_DESC, entryType);
            crit.addEqualTo(WorkflowWoQueueDataAccess.STATUS_CD, RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE);
            crit.addOrderBy(WorkflowWoQueueDataAccess.WORKFLOW_ID);

            return WorkflowWoQueueDataAccess.select(conn, crit);
    }

    private void processWorkflowRules(Connection conn,
                                      WorkflowWoQueueDataVector workflowRules) 
                                      throws Exception {
        GregorianCalendar tmpCalendar = new GregorianCalendar();
        Date currentDate = new Date();
        GregorianCalendar currCalendar = new GregorianCalendar();
        currCalendar.setTime(currentDate);

        ArrayList checkAction = new ArrayList();
        Iterator it = workflowRules.iterator();
        int currWoId = 0;
        WorkflowWoQueueData qD;
        while (it.hasNext()) {
            qD = (WorkflowWoQueueData)it.next();
            if (currWoId != qD.getWorkOrderId()) { // process collected checkAction list
                if (!checkAction.isEmpty()) {
                    Collections.sort(checkAction, new WorkflowWoQueueDataOnDateComparator());
                    checkWORule(conn,
                                currWoId,
                                checkAction,
                                currCalendar.get(Calendar.YEAR),
                                currCalendar.get(Calendar.DAY_OF_YEAR));
                    checkAction.clear();
                }
                currWoId = qD.getWorkOrderId(); // next woId
            } // collect checkAction list for the current WoId
            tmpCalendar.setTime(qD.getAddDate());
            tmpCalendar.add(Calendar.DATE, qD.getActionDays());
            qD.setModDate(tmpCalendar.getTime()); // make ModDate field dirty to store the nextAction date for the Rule
            checkAction.add(qD);
        }
    }
    
    private void checkWORule (Connection conn,
                              int currWoId,
                              ArrayList rules,
                              int currYear,
                              int currDayOfYear) throws Exception {
        GregorianCalendar tmpCalendar = new GregorianCalendar();
        WorkOrderData woData;
        WorkflowWoQueueData tmpWOD;
        for (int i = 0; i < rules.size(); i++) {
            tmpWOD = (WorkflowWoQueueData)rules.get(i);
            tmpCalendar.setTime(tmpWOD.getModDate());

            if ( (tmpCalendar.get(Calendar.YEAR) < currYear) ||
                 (tmpCalendar.get(Calendar.YEAR) == currYear &&
                  tmpCalendar.get(Calendar.DAY_OF_YEAR) < currDayOfYear) ) { // past
                //delete the old rule
                tmpWOD.setStatusCd(RefCodeNames.WORKFLOW_STATUS_CD.INACTIVE);
                WorkflowWoQueueDataAccess.update(conn, tmpWOD);
            } else if (tmpCalendar.get(Calendar.YEAR) == currYear &&
                       tmpCalendar.get(Calendar.DAY_OF_YEAR) == currDayOfYear) { // today
                if (RefCodeNames.WORKFLOW_RULE_ACTION.APPROVE_ORDER.equals(tmpWOD.getActionType())) {
                    // look for another rules
                    boolean approve = true;
                    GregorianCalendar checkCalendar = new GregorianCalendar();
                    for (int j = i + 1; j < rules.size(); j++) {
                        tmpWOD = (WorkflowWoQueueData)rules.get(j);
                        checkCalendar.setTime(tmpWOD.getModDate());
                        if (RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER.equals(tmpWOD.getActionType()) &&
                                checkCalendar.get(Calendar.YEAR) == currYear &&
                                checkCalendar.get(Calendar.DAY_OF_YEAR) == currDayOfYear) { //today
                            // reject the work order
                            woData = WorkOrderDataAccess.select(conn, currWoId);
                            woData.setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED);
                            WorkOrderDataAccess.update(conn, woData);
                            // delete the all aready irrelevant rules
                            makeRulesInactive(conn, rules, i);
                            approve = false;
                            break;
                        } else if (RefCodeNames.WORKFLOW_RULE_ACTION.APPROVE_ORDER.equals(tmpWOD.getActionType()) &&
                                   (checkCalendar.get(Calendar.YEAR) != currYear ||
                                    checkCalendar.get(Calendar.DAY_OF_YEAR) != currDayOfYear) // not today
                                ) {
                            approve = false;
                            break;
                        }
                    }
                    if (approve) {// approve the work order
                        woData = WorkOrderDataAccess.select(conn, currWoId);
                        woData.setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER);
                        WorkOrderDataAccess.update(conn, woData);
                        // delete the all aready irrelevant rules
                        makeRulesInactive(conn, rules, i);
                        
                        //send notification to provider
                        sentNotificationToServiceProvider(currWoId);
                        WOOperations woop = new WOOperations();
                        //woop.sendPdfToProvider(workOrderDetailView, localeCd, databaseName, ORACLE);
                    }
                    break;
                } else if (RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER.equals(tmpWOD.getActionType())) {
                    // reject the work order
                    woData = WorkOrderDataAccess.select(conn, currWoId);
                    woData.setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED);
                    WorkOrderDataAccess.update(conn, woData);
                    // delete the all aready irrelevant rules
                    makeRulesInactive(conn, rules, i);
                    break;
                }
            } else if ( (tmpCalendar.get(Calendar.YEAR) > currYear) ||
                        (tmpCalendar.get(Calendar.YEAR) == currYear &&
                         tmpCalendar.get(Calendar.DAY_OF_YEAR) > currDayOfYear) ) { // future
                break;
            }
        }
    }
    
    private void makeRulesInactive (Connection conn, ArrayList rules, int startindex) throws SQLException {
        WorkflowWoQueueData tmp;
        for (int i = startindex; i < rules.size(); i++) {
            tmp = (WorkflowWoQueueData)rules.get(i);
            tmp.setStatusCd(RefCodeNames.WORKFLOW_STATUS_CD.INACTIVE);
            WorkflowWoQueueDataAccess.update(conn, tmp);
        }
    }
    
    private void sentNotificationToServiceProvider(int currWoId) throws Exception {
        APIAccess factory  = new APIAccess();
        WorkOrder woEjb = factory.getWorkOrderAPI();
        WorkOrderDetailView woDetail = woEjb.getWorkOrderDetailView(currWoId);
        
        ServiceProviderData provider = woDetail.getServiceProvider();
        if (provider != null ) {
            if (provider.getPrimaryEmail() != null) {
                String emailAddress = provider.getPrimaryEmail().getEmailAddress();
                if (Utility.isSet(emailAddress)) {
                    WOOperations.sendPdfToProvider(woDetail, Locale.US, WorkOrderUtil.EMAIL, emailAddress);
                } else {
                    log.error("No email-address for providerId: " + provider.getBusEntity() + ", Work OrderId: " + currWoId);
                }
            }
        }
    }
    
    private class WorkflowWoQueueDataOnDateComparator implements Comparator {
     
        public final int compare ( Object a, Object b ) {
            WorkflowWoQueueData first = (WorkflowWoQueueData)a;
            WorkflowWoQueueData second = (WorkflowWoQueueData)b;
            
            return first.getModDate().compareTo( second.getModDate() );
        }
    }
    
}

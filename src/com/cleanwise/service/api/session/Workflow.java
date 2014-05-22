package com.cleanwise.service.api.session;

/**
 * Title:        Workflow
 * Description:  Remote Interface for Workflow Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving Workflow information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import java.sql.Connection;
import java.util.HashSet;

/**
 * Describe interface <code>Workflow</code> here.
 *
 * @author <a href="mailto:dvieira@cleanwise.com"></a>
 */
public interface Workflow extends javax.ejb.EJBObject
{


    /**
     * Describe constant <code>BEGINS_WITH_IGNORE_CASE</code> here.
     *
     */
    public final static int BEGINS_WITH_IGNORE_CASE = 10004;
    /**
     * Describe constant <code>CONTAINS_IGNORE_CASE</code> here.
     *
     */
    public final static int CONTAINS_IGNORE_CASE = 10005;

    /**
     * Describe constant <code>MOVE_RULE_UP</code> here.
     *
     */
    public final static int MOVE_RULE_UP = 10;
    /**
     * Describe constant <code>MOVE_RULE_DOWN</code> here.
     *
     */
    public final static int MOVE_RULE_DOWN = 20;
    /**
     * Describe constant <code>MOVE_RULE_LAST</code> here.
     *
     */
    public final static int MOVE_RULE_LAST = 30;


    /**
     * Adds the workflow routing information values to be used by the request.
     * @param pWorkflowRouting  the workflow routing data.
     * @param request  the workflow routing request data.
     * @return new WorkflowRoutingRequestData()
     * @throws            RemoteException Required by EJB 1.0
     */
    public WorkflowRoutingRequestData addWorkflowRouting
        (WorkflowRoutingData pWorkflowRouting,
         WorkflowRoutingRequestData request)
        throws RemoteException;

    /**
     * Adds the queue information values to be used by the request.
     * @param pQueue  the queue data.
     * @param request  the queue request data.
     * @return new QueueRequestData()
     * @throws            RemoteException Required by EJB 1.0
     */
    public QueueRequestData addQueue(QueueData pQueue,
                                     QueueRequestData request)
        throws RemoteException;

    /**
     * Updates the workflow information values.
     * @param pUpdateWorkflowData  the workflow data.
     * @exception            RemoteException Required by EJB 1.0
     */
    public void updateWorkflow(WorkflowData pUpdateWorkflowData)
        throws RemoteException;

    /**
     * Updates the workflow routing information values to be used by the request.
     * @param pUpdateWorkflowRoutingData  the workflow routing data.
     * @param pBusEntityId the business entity identifier.
     * @param pWorkflowId the workflow identifier.
     * @param pWorkflowItemId the workflow item identifier.
     * @exception            RemoteException Required by EJB 1.0
     */
    public void updateWorkflowRouting(WorkflowRoutingData pUpdateWorkflowRoutingData, int pBusEntityId, int pWorkflowId, int pWorkflowItemId)
        throws RemoteException;

    /**
     * Updates the queue routing information values to be used by the request.
     * @param pUpdateQueueData  the queue data.
     * @param pBusEntityId the business entity identifier.
     * @param pWorkflowId the workflow identifier.
     * @param pWorkflowItemId the workflow item identifier.
     * @param pQueueId the queue identifier.
     * @exception            RemoteException Required by EJB 1.0
     */
    public void updateQueue(QueueData pUpdateQueueData,
                            int pBusEntityId, int pWorkflowId, int pWorkflowItemId, int pQueueId)
        throws RemoteException;

    /**
     * Gets the workflow vector information values to be used by the request.
     * @param pBusEntityId the business entity identifier.
     * @return WorkflowDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public WorkflowDataVector getWorkflowCollectionByEntity(int pBusEntityId)
        throws RemoteException;

    public WorkflowDataVector getWorkflowCollectionByEntity(int pBusEntityId, boolean matchBusEntity)
        throws RemoteException;

    public WorkflowDataVector getWorkflowCollectionByEntity(int pBusEntityId,  String pStatusCd)
        throws RemoteException ;

    /**
     *  Gets the workflow desc vector for the business entity.
     * if the busEntityId is 0, return all
     *
     *@param  pBusEntityId      the BusEntity identifier.
     *@return                   WorkflowDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowDescDataVector getWorkflowDescCollectionByEntity
    (          int pBusEntityId, boolean matchBusEntity )
    throws RemoteException;

    /**
     * Gets the workflow vector information values to be used by the request.
     * @param pWorkflowTypeCd the workflow type code.
     * @return WorkflowDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public WorkflowDataVector getWorkflowCollectionByType(String pWorkflowTypeCd)
        throws RemoteException;

    /**
     * Gets the workflow vector information values to be used by the request.
     * @param pName a <code>String</code> value
     * @param pBusEntityId an <code>int</code> value
     * @param pMatchType an <code>int</code> value
     * @return WorkflowDataVector
     * @exception            RemoteException Required by EJB 1.0
     */
    public WorkflowDataVector getWorkflowCollectionByName(String pName,
                                                          int pBusEntityId, int pMatchType)
        throws RemoteException;


    public WorkflowDataVector getWorkflowCollectionByName(String pName,
                                                          int pBusEntityId, int pMatchType,
                                                          boolean matchBusEntity)
        throws RemoteException;

    /**
     *  Gets the workflow desc vector information values to be used by the request.
     *
     *@param  pName                a <code>String</code> value
     *@param  pBusEntityId         an <code>int</code> value
     *@param  pMatchType           an <code>int</code> value
     *@return                      WorkflowDataVector
     *@exception  RemoteException  Required by EJB 1.0
     */
    public WorkflowDescDataVector getWorkflowDescCollectionByName
    (          String pName,
               int pBusEntityId, int pMatchType, boolean matchBusEntity )
    throws RemoteException;

    /**
     * Gets the workflow routing vector information values to be used by the request.
     * @param pWorkflowId the workflow identifier.
     * @return WorkflowRoutingDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public WorkflowRoutingDataVector getWorkflowRoutingCollectionByWorkflow(int pWorkflowId)
        throws RemoteException;

    /**
     * Gets the queue vector information values to be used by the request.
     * @param pQueueStatusCd the queue status code.
     * @return QueueDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public QueueDataVector getQueueCollectionByStatus(String pQueueStatusCd)
        throws RemoteException;

    /**
     * Gets the queue vector information values to be used by the request.
     * @param pWorkflowId the workflow identifier.
     * @return QueueDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public QueueDataVector getQueueCollectionByWorkflow(int pWorkflowId)
        throws RemoteException;

    /**
     * Gets the queue vector information values to be used by the request.
     * @param pWorkflowItemId the workflow item identifier.
     * @return QueueDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public QueueDataVector getQueueCollectionByWorkflowItem(int pWorkflowItemId)
        throws RemoteException;

    /**
     * Gets the queue vector information values to be used by the request.
     * @param pBusEntityId the business entity identifier.
     * @return QueueDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public QueueDataVector getQueueCollectionByBusEntity(int pBusEntityId)
        throws RemoteException;

    /**
     * Gets the workflow.
     * @param pWorkflowId the workflow identifier.
     * @return WorkflowData
     * @throws            RemoteException Required by EJB 1.0
     */
    public WorkflowData getWorkflow(int pWorkflowId)
        throws RemoteException;

    /**
     *  Gets the workflow desc.
     *
     *@param  pWorkflowId       the workflow identifier.
     *@return                   WorkflowData
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowDescData getWorkflowDesc(          int pWorkflowId )
    throws RemoteException;

    /**
     * Gets the workflow routing information values to be used by the request.
     * @param pBusEntityId the business entity identifier.
     * @param pWorkflowId the workflow identifier.
     * @param pWorkflowItemId the workflow item identifier.
     * @return WorkflowRoutingData
     * @throws            RemoteException Required by EJB 1.0
     */
    public WorkflowRoutingData getWorkflowRouting(int pBusEntityId, int pWorkflowId,
                                                  int pWorkflowItemId)
        throws RemoteException;

    /**
     * Gets the queue information values to be used by the request.
     * @param pQueueId the queue identifier.
     * @return QueueData
     * @throws            RemoteException Required by EJB 1.0
     */
    public QueueData getQueue(int pQueueId)
        throws RemoteException;

    /**
     * Creates the workflow information values to be used by the request.
     * @param pData the workflow data
     * @return WorkflowData
     * @throws            RemoteException Required by EJB 1.0
     */
    public WorkflowData createWorkflow(WorkflowData pData)
        throws RemoteException;

    /**
     *  Saves removes existing rule (if rule sequence >0) and adds new one
     *
     *@param pRuleSeqOrig rule sequence wich the rules had befor editing
     *@param  pRuleRecords a vector of <code>WorkflowRuleData</code> objects
     *@return a vector of <code>WorkflowRuleData</code> objects
     *@exception  RemoteException  if an error occurs
     */
    public WorkflowRuleDataVector saveWorkflowRule(int pRuleSeqOrig, WorkflowRuleDataVector pRuleRecords)
                                        throws RemoteException;

    /**
     *  Saves removes existing rule (if rule sequence >0) and adds new one
     *
     *@param pRuleSeqOrig rule sequence wich the rules had befor editing
     *@param  pRuleRecords a vector of <code>WorkflowRuleData</code> objects
     *@param pWorkflowAssocVector a set of user group assoccitions to apply / skip the rule
     *@return a vector of <code>WorkflowRuleData</code> objects
     *@exception  RemoteException  if an error occurs
     */
    public WorkflowRuleDataVector saveWorkflowRule(int pRuleSeqOrig,
            WorkflowRuleDataVector pRuleRecords, WorkflowAssocDataVector pWorkflowAssocVector)
                                        throws RemoteException;
    /**
     * Creates the workflow information values to be used by the request.
     * @param pWorkflowId the workflow identifier
     * @param pBusEntityId the business entity identifier
     * @return WorkflowData
     * @throws            RemoteException Required by EJB 1.0
     */
    public WorkflowData createWorkflow(int pWorkflowId, int pBusEntityId)
        throws RemoteException;

    /**
     * Describe <code>assignWorkflow</code> method here.
     *
     * @param pWorkflowId an <code>int</code> value
     * @param pSiteId an <code>int</code> value
     * @param pUserName a <code>String</code> value
     * @exception RemoteException if an error occurs
     */
    public void assignWorkflow(int pWorkflowId, int pSiteId,
                               String pUserName)
        throws RemoteException;

    /**
     * Describe <code>assignWorkflowToAccountSites</code> method here.
     *
     * @param pWorkflowId an <code>int</code> value
     * @param pAccountId an <code>int</code> value
     * @param pUserName a <code>String</code> value
     * @exception RemoteException if an error occurs
     */
    public void assignWorkflowToAccountSites
        (int pWorkflowId, int pAccountId, String pUserName)
        throws RemoteException;

    /**
     * Describe <code>unassignWorkflow</code> method here.
     *
     * @param pWorkflowId an <code>int</code> value
     * @param pSiteId an <code>int</code> value
     * @exception RemoteException if an error occurs
     */
    public void unassignWorkflow(int pWorkflowId, int pSiteId)
        throws RemoteException;

    /**
     * Describe <code>getWorkflowSiteIds</code> method here.
     *
     * @param pWorkflowId an <code>int</code> value
     * @return an <code>IdVector</code> value
     * @exception RemoteException if an error occurs
     */
    public IdVector getWorkflowSiteIds(int pWorkflowId)
        throws RemoteException;

    /**
     *  Describe <code>getWorkflowRulesJoinVector</code> method here.
     *
     *@param  pWorkflowId          an <code>int</code> value
     *@return                      a <code>WorkflowRuleDataVector</code> value
     *@exception  RemoteException  if an error occurs
     */
    public WorkflowRuleJoinViewVector getWorkflowRules(int pWorkflowId)
    throws RemoteException;

    /**
     * Describe <code>getWorkflowRulesCollection</code> method here.
     *
     * @param pWorkflowId an <code>int</code> value
     * @return a <code>WorkflowRuleDataVector</code> value
     * @exception RemoteException if an error occurs
     */
    public WorkflowRuleDataVector getWorkflowRulesCollection
        ( int pWorkflowId ) throws RemoteException;

    /**
     * Describe <code>createWorkflowRule</code> method here.
     *
     * @param pRule a <code>WorkflowRuleData</code> value
     * @return a <code>WorkflowRuleData</code> value
     * @exception RemoteException if an error occurs
     */
    public WorkflowRuleData createWorkflowRule( WorkflowRuleData pRule )
        throws RemoteException;

    /**
     * Describe <code>deleteWorkflowRule</code> method here.
     *
     * @param pWorkflowRuleId an <code>int</code> value
     * @exception RemoteException if an error occurs
     */
    public void deleteWorkflowRule( int pWorkflowRuleId )
        throws RemoteException;

    public void deleteWorkflowRule( int pWorkflowRuleId, boolean pDeleteChildQueue )
        throws RemoteException;
    /**
     * Describe <code>fetchWorkflowForSite</code> method here.
     *
     * @param pSiteId an <code>int</code> value
     * @param pTypeCd workflow type cd
     * @return a <code>WorkflowData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public WorkflowData fetchWorkflowForSite(int pSiteId, String pTypeCd)
            throws RemoteException, DataNotFoundException;

    public WorkflowData fetchWorkflowForSite(int pSiteId)
            throws RemoteException, DataNotFoundException;
    /**
     * Describe <code>updateWorkflowRuleSeq</code> method here.
     *
     * @param pWorkflowRuleId an <code>int</code> value
     * @param pMoveCmd an <code>int</code> value
     * @exception RemoteException if an error occurs
     */
    public void updateWorkflowRuleSeq(int pWorkflowRuleId, int pMoveCmd)
        throws RemoteException;

    /**
     * Describe <code>applySiteWorkflow</code> method here.
     *
     * @param pSiteId an <code>int</code> value
     * @param pAccountId an <code>int</code> value
     * @param pOrderData an <code>Order</code> value
     * @return an <code>int</code> value, @see Workflow.OK
     * and Workflow.FAIL for a description of the values.
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public WorkflowRuleResult applySiteWorkflow(int pSiteId,
                                                int pAccountId,
                                                OrderData pOrderData,
                                                OrderItemDataVector pOrderItems) throws RemoteException, DataNotFoundException;

    /**
     * Describe <code>applyStoreWorkflow</code> method here.
     *
     */
    public boolean applyStoreWorkflow
	( int pStoreId, int pOrderId, OrderItemDataVector pOidv )
        throws RemoteException;

    /**
     * Describe <code>deleteWorkflow</code> method here.
     *
     * @param pWorkflowId an <code>int</code> value
     * @exception RemoteException if an error occurs
     */
    public void deleteWorkflow(          int pWorkflowId )
        throws RemoteException;

    /**
     * Describe <code>createQueueEntry</code> method here.
     *
     * @param pSiteId an <code>int</code> value
     * @param pOrderData an <code>OrderData</code> value
     * @param pRuleResult a <code>WorkflowRuleResult</code> value
     * @return a <code>WorkflowQueueData</code> value
     * @exception RemoteException if an error occurs
     */
    public WorkflowQueueData createQueueEntry
	    ( int pSiteId, OrderData pOrderData,
	    WorkflowRuleResult pRuleResult )
        throws RemoteException;


    /**
     * <code>processQueueEntries</code> - process the actions in the
     * clw_workflow_queue table.
     * Return a string describbing the work done.
     *
     * @exception RemoteException if an error occurs
     */

    public String processQueueEntries()
	throws RemoteException;

    /**
     * <code>processQueueEntries</code> - process the actions in the
     * clw_workflow_queue table.
     * @param pEntryType type of Workflow Queue record
     * Return number of processed transactions
     *
     * @exception RemoteException if an error occurs
     */
    public int processQueueEntries(String pEntryType)
	throws RemoteException;


    public OrderSiteSummaryDataVector listPendingOrders
    (int pUserId)
    throws RemoteException ;
    public IdVector getOrdersSinceLastCheck()
    throws RemoteException ;
    public void updateLastCheckStamp( java.util.Date pDateStamp, int pOrderId)
    throws RemoteException ;
    public void sendOrderEmails(int pOrderId)
    throws Exception, RemoteException ;

    // Create a message to be shown to buyers informing them of any workflow
    // message you we might need.
    public String getSiteWorkflowMessage(int pSiteId, String pTypeCd)
    throws RemoteException,    DataNotFoundException ;

    public OrderPropertyData  sendPendingApprovalEmail(Connection con, OrderData pOrder, OrderAddressData pOrderAddress)
    //public void  sendPendingApprovalEmail(Connection con, OrderData pOrder, OrderAddressData pOrderAddress)
    throws Exception, RemoteException;
    
    public void  sendPendingApprovalEmail(OrderData pOrder, OrderAddressData pOrderAddress, HashSet pSendToUsers)
    throws Exception, RemoteException;

    public IdVector getSiteWorkflowIds(int pSiteId) throws RemoteException;
    
    public WorkflowWoQueueData createWorkflowWoQueueEntry(WorkflowWoQueueData workflowWOD) throws RemoteException;
    public WorkflowWoQueueDataVector getWorkflowWoQueueEntries(DBCriteria crit) throws RemoteException;

    public WorkflowQueueDataVector getWorkflowQueueEntries(int pRuleId) throws RemoteException;
    public OrderDataVector getOrdersWorkflowQueueV(int pRuleId) throws RemoteException;
    
    public void deleteWorkflowQueueEntryByOrderId(int orderId) throws RemoteException;
    
    public void delWorkflowQueueEntryByOrderIdAndShortDesc(int orderId,  String shortDesc) throws RemoteException;

}

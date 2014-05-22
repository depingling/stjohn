package com.cleanwise.service.api.session;

import java.io.File;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.CreateException;

import org.apache.log4j.Category;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BudgetDAO;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.CostCenterDataAccess;
import com.cleanwise.service.api.dao.GroupAssocDataAccess;
import com.cleanwise.service.api.dao.JoinDataAccess;
import com.cleanwise.service.api.dao.OrderAssocDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import com.cleanwise.service.api.dao.SiteWorkflowDataAccess;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.dao.WorkflowAssocDataAccess;
import com.cleanwise.service.api.dao.WorkflowDataAccess;
import com.cleanwise.service.api.dao.WorkflowQueueDataAccess;
import com.cleanwise.service.api.dao.WorkflowRuleDataAccess;
import com.cleanwise.service.api.dao.WorkflowWoQueueDataAccess;
import com.cleanwise.service.api.dto.template.OrderDto;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.framework.WorkflowServicesAPI;
import com.cleanwise.service.api.process.operations.OrderNotificationGeneratorBase;
import com.cleanwise.service.api.util.CacheManager;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.TemplateUtilities;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BudgetDataVector;
import com.cleanwise.service.api.value.BudgetSpendView;
import com.cleanwise.service.api.value.BudgetSpendViewVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.GroupData;
import com.cleanwise.service.api.value.GroupDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderAssocData;
import com.cleanwise.service.api.value.OrderAssocDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OrderInfoDataView;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderSiteSummaryDataVector;
import com.cleanwise.service.api.value.QueueData;
import com.cleanwise.service.api.value.QueueDataVector;
import com.cleanwise.service.api.value.QueueRequestData;
import com.cleanwise.service.api.value.SiteWorkflowData;
import com.cleanwise.service.api.value.SiteWorkflowDataVector;
import com.cleanwise.service.api.value.TemplateDataExtended;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.service.api.value.WorkflowAssocData;
import com.cleanwise.service.api.value.WorkflowAssocDataVector;
import com.cleanwise.service.api.value.WorkflowData;
import com.cleanwise.service.api.value.WorkflowDataVector;
import com.cleanwise.service.api.value.WorkflowDescData;
import com.cleanwise.service.api.value.WorkflowDescDataVector;
import com.cleanwise.service.api.value.WorkflowQueueData;
import com.cleanwise.service.api.value.WorkflowQueueDataVector;
import com.cleanwise.service.api.value.WorkflowRoutingData;
import com.cleanwise.service.api.value.WorkflowRoutingDataVector;
import com.cleanwise.service.api.value.WorkflowRoutingRequestData;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;
import com.cleanwise.service.api.value.WorkflowRuleJoinView;
import com.cleanwise.service.api.value.WorkflowRuleJoinViewVector;
import com.cleanwise.service.api.value.WorkflowRuleResult;
import com.cleanwise.service.api.value.WorkflowWoQueueData;
import com.cleanwise.service.api.value.WorkflowWoQueueDataVector;
import com.cleanwise.view.utils.Constants;
import org.apache.log4j.Logger;


/**
 *  class <code>WorkflowBean</code> implements the Workflow interface,
 *
 *@author     <a href="mailto:dvieira@cleanwise.com"></a>
 *@created    December 20, 2001
 *@see        com.cleanwise.service.api.session.Workflow
 */
public class WorkflowBean extends WorkflowServicesAPI {

	private static final long serialVersionUID = -7393438624980632239L;
	private static final Logger log = Logger.getLogger(WorkflowBean.class);
    private static String className = "WorkflowBean";

    /**
     */
    public WorkflowBean() {
    }

    /**
     *  Gets the workflow vector for the business entity.
     *
     *@param  pBusEntityId      the BusEntity identifier.
     *@return                   WorkflowDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowDataVector getWorkflowCollectionByEntity(int pBusEntityId)
        throws RemoteException {
      return getWorkflowCollectionByEntity(pBusEntityId, null);
    }
    public WorkflowDataVector getWorkflowCollectionByEntity(int pBusEntityId,  String pStatusCd)
        throws RemoteException {

        WorkflowDataVector v = new WorkflowDataVector();
        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(WorkflowDataAccess.BUS_ENTITY_ID, pBusEntityId);
            if (pStatusCd != null) {
              crit.addEqualTo(WorkflowDataAccess.WORKFLOW_STATUS_CD, pStatusCd );
            }
            crit.addOrderBy(WorkflowDataAccess.WORKFLOW_ID);
            v = WorkflowDataAccess.select(conn, crit);
        } catch (Exception e) {

            String m = "Workflow.getWorkflowCollectionByEntity: " + e;
            logError(m);
            throw new RemoteException(m);
        } finally {

            try {

                if (null != conn) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }

        return v;
    }

    /**
     *  Gets the workflow vector for the business entity.
     * if the busEntityId is 0, return all
     *
     *@param  pBusEntityId      the BusEntity identifier.
     *@return                   WorkflowDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowDataVector getWorkflowCollectionByEntity(int pBusEntityId,
                                                            boolean matchBusEntity)
                                                     throws RemoteException {

        WorkflowDataVector v = new WorkflowDataVector();
        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            if (0 != pBusEntityId || true == matchBusEntity) {
                crit.addEqualTo(WorkflowDataAccess.BUS_ENTITY_ID, pBusEntityId);
                crit.addOrderBy(WorkflowDataAccess.WORKFLOW_ID);
            }

            v = WorkflowDataAccess.select(conn, crit);
        } catch (Exception e) {

            String m = "Workflow.getWorkflowCollectionByEntity: " + e;
            logError(m);
            throw new RemoteException(m);
        } finally {

            try {

                if (null != conn) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }

        return v;
    }

    /**
     *  Gets the workflow desc vector for the business entity.
     * if the busEntityId is 0, return all
     *
     *@param  pBusEntityId      the BusEntity identifier.
     *@return                   WorkflowDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowDescDataVector getWorkflowDescCollectionByEntity(int pBusEntityId,
                                                                    boolean matchBusEntity)
        throws RemoteException {

        WorkflowDescDataVector v = new WorkflowDescDataVector();
        Connection conn = null;

        try {

            WorkflowDataVector workflowV = getWorkflowCollectionByEntity(
                                                   pBusEntityId,
                                                   matchBusEntity);

            if (null != workflowV && workflowV.size() > 0) {

                IdVector busEntityIdV = new IdVector();

                for (int i = 0; i < workflowV.size(); i++) {

                    WorkflowData workflowD = (WorkflowData)workflowV.get(i);

                    if (null != workflowD) {
                        busEntityIdV.add(new Integer(workflowD.getBusEntityId()));
                    }
                }

                BusEntityDataVector busEntityV = new BusEntityDataVector();
                conn = getConnection();

                DBCriteria crit = new DBCriteria();
                crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, busEntityIdV);
                busEntityV = BusEntityDataAccess.select(conn, crit);

                for (int i = 0; i < workflowV.size(); i++) {

                    WorkflowData workflowD = (WorkflowData)workflowV.get(i);
                    WorkflowDescData workflowDescD = WorkflowDescData.createValue();
                    workflowDescD.setWorkflow(workflowD);

                    if (null != busEntityV && 0 < busEntityV.size()) {

                        for (int j = 0; j < busEntityV.size(); j++) {

                            BusEntityData busEntityD = (BusEntityData)busEntityV.get(
                                                               j);

                            if (workflowD.getBusEntityId() == busEntityD.getBusEntityId()) {
                                workflowDescD.setBusEntityShortDesc(busEntityD.getShortDesc());
                                workflowDescD.setBusEntityTypeCd(busEntityD.getBusEntityTypeCd());

                                break;
                            }
                        }
                    }

                    v.add(workflowDescD);
                }
            }
        } catch (Exception e) {

            String m = "Workflow.getWorkflowDescCollectionByEntity: " + e;
            logError(m);
            throw new RemoteException(m);
        } finally {

            try {

                if (null != conn) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }

        return v;
    }

    /**
     *
     *  Gets the workflow vector information values to be used by the
     *  request.
     *
     *@param  pWorkflowTypeCd   the workflow type code.
     *@return                   WorkflowDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowDataVector getWorkflowCollectionByType(String pWorkflowTypeCd)
                                                   throws RemoteException {

        return new WorkflowDataVector();
    }

    /**
     *  Gets the workflow vector information values to be used by the request.
     *
     *@param  pName                a <code>String</code> value
     *@param  pBusEntityId         an <code>int</code> value
     *@param  pMatchType           an <code>int</code> value
     *@return                      WorkflowDataVector
     *@exception  RemoteException  Required by EJB 1.0
     */
    public WorkflowDataVector getWorkflowCollectionByName(String pName,
                                                          int pBusEntityId,
                                                          int pMatchType)
                                                   throws RemoteException {

        WorkflowDataVector wv = new WorkflowDataVector();
        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            switch (pMatchType) {

                case Workflow.BEGINS_WITH_IGNORE_CASE:
                    crit.addLikeIgnoreCase(WorkflowDataAccess.SHORT_DESC,
                                           pName + "%");

                    break;

                case Workflow.CONTAINS_IGNORE_CASE:
                    crit.addLikeIgnoreCase(WorkflowDataAccess.SHORT_DESC,
                                           "%" + pName + "%");

                    break;

                default:
                    throw new RemoteException("Workflow.getWorkflowCollectionByName: Bad match specification");
            }

            crit.addEqualTo(WorkflowDataAccess.BUS_ENTITY_ID, pBusEntityId);
            crit.addOrderBy(WorkflowDataAccess.WORKFLOW_ID, true);
            wv = WorkflowDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException("Workflow.getWorkflowCollectionByName: " +
                                      e.getMessage());
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }

        return wv;
    }

    /**
     *  Gets the workflow vector information values to be used by the request.
     *
     *@param  pName                a <code>String</code> value
     *@param  pBusEntityId         an <code>int</code> value
     *@param  pMatchType           an <code>int</code> value
     *@return                      WorkflowDataVector
     *@exception  RemoteException  Required by EJB 1.0
     */
    public WorkflowDataVector getWorkflowCollectionByName(String pName,
                                                          int pBusEntityId,
                                                          int pMatchType,
                                                          boolean matchBusEntity)
                                                   throws RemoteException {

        WorkflowDataVector wv = new WorkflowDataVector();
        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            switch (pMatchType) {

                case Workflow.BEGINS_WITH_IGNORE_CASE:
                    crit.addLikeIgnoreCase(WorkflowDataAccess.SHORT_DESC,
                                           pName + "%");

                    break;

                case Workflow.CONTAINS_IGNORE_CASE:
                    crit.addLikeIgnoreCase(WorkflowDataAccess.SHORT_DESC,
                                           "%" + pName + "%");

                    break;

                default:
                    throw new RemoteException("Workflow.getWorkflowCollectionByName: Bad match specification");
            }

            if (0 != pBusEntityId || true == matchBusEntity) {
                crit.addEqualTo(WorkflowDataAccess.BUS_ENTITY_ID, pBusEntityId);
            }

            crit.addOrderBy(WorkflowDataAccess.WORKFLOW_ID, true);
            wv = WorkflowDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException("Workflow.getWorkflowCollectionByName: " +
                                      e.getMessage());
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }

        return wv;
    }

    /**
     *  Gets the workflow desc vector information values to be used by the request.
     *
     *@param  pName                a <code>String</code> value
     *@param  pBusEntityId         an <code>int</code> value
     *@param  pMatchType           an <code>int</code> value
     *@return                      WorkflowDataVector
     *@exception  RemoteException  Required by EJB 1.0
     */
    public WorkflowDescDataVector getWorkflowDescCollectionByName(String pName,
                                                                  int pBusEntityId,
                                                                  int pMatchType,
                                                                  boolean matchBusEntity)
        throws RemoteException {

        WorkflowDescDataVector v = new WorkflowDescDataVector();
        Connection conn = null;

        try {

            WorkflowDataVector workflowV = getWorkflowCollectionByName(pName,
                                                                       pBusEntityId,
                                                                       pMatchType,
                                                                       matchBusEntity);

            if (null != workflowV && workflowV.size() > 0) {

                IdVector busEntityIdV = new IdVector();

                for (int i = 0; i < workflowV.size(); i++) {

                    WorkflowData workflowD = (WorkflowData)workflowV.get(i);

                    if (null != workflowD) {
                        busEntityIdV.add(new Integer(workflowD.getBusEntityId()));
                    }
                }

                BusEntityDataVector busEntityV = new BusEntityDataVector();
                conn = getConnection();

                DBCriteria crit = new DBCriteria();
                crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, busEntityIdV);
                busEntityV = BusEntityDataAccess.select(conn, crit);

                for (int i = 0; i < workflowV.size(); i++) {

                    WorkflowData workflowD = (WorkflowData)workflowV.get(i);
                    WorkflowDescData workflowDescD = WorkflowDescData.createValue();
                    workflowDescD.setWorkflow(workflowD);

                    if (null != busEntityV && 0 < busEntityV.size()) {

                        for (int j = 0; j < busEntityV.size(); j++) {

                            BusEntityData busEntityD = (BusEntityData)busEntityV.get(
                                                               j);

                            if (workflowD.getBusEntityId() == busEntityD.getBusEntityId()) {
                                workflowDescD.setBusEntityShortDesc(busEntityD.getShortDesc());
                                workflowDescD.setBusEntityTypeCd(busEntityD.getBusEntityTypeCd());

                                break;
                            }
                        }
                    }

                    v.add(workflowDescD);
                }
            }
        } catch (Exception e) {
            throw new RemoteException("Workflow.getWorkflowDescCollectionByName: " +
                                      e.getMessage());
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }

        return v;
    }

    /**
     *  Gets the workflow routing vector information values to be used
     *  by the request.
     *
     *@param  pWorkflowId       the workflow identifier.
     *@return                   WorkflowRoutingDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowRoutingDataVector getWorkflowRoutingCollectionByWorkflow(int pWorkflowId)
        throws RemoteException {

        return new WorkflowRoutingDataVector();
    }

    /**
     *  Gets the queue vector information values to be used by the request.
     *
     *@param  pQueueStatusCd    the queue status code.
     *@return                   QueueDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public QueueDataVector getQueueCollectionByStatus(String pQueueStatusCd)
                                               throws RemoteException {

        return new QueueDataVector();
    }

    /**
     *  Gets the queue vector information values to be used by the request.
     *
     *@param  pWorkflowId       the workflow identifier.
     *@return                   QueueDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public QueueDataVector getQueueCollectionByWorkflow(int pWorkflowId)
                                                 throws RemoteException {

        return new QueueDataVector();
    }

    /**
     *  Gets the queue vector information values to be used by the request.
     *
     *@param  pWorkflowItemId   the workflow item identifier.
     *@return                   QueueDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public QueueDataVector getQueueCollectionByWorkflowItem(int pWorkflowItemId)
                                                     throws RemoteException {

        return new QueueDataVector();
    }

    /**
     *  Gets the queue vector information values to be used by the request.
     *
     *@param  pBusEntityId      the business entity identifier.
     *@return                   QueueDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public QueueDataVector getQueueCollectionByBusEntity(int pBusEntityId)
                                                  throws RemoteException {

        return new QueueDataVector();
    }

    /**
     *  Gets the workflow.
     *
     *@param  pWorkflowId       the workflow identifier.
     *@return                   WorkflowData
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowData getWorkflow(int pWorkflowId)
                             throws RemoteException {

        WorkflowData w = null;
        Connection conn = null;

        try {
            conn = getConnection();
            w = WorkflowDataAccess.select(conn, pWorkflowId);
        } catch (Exception e) {

            String m = "Workflow.getWorkflow: id: " + pWorkflowId +
                       " error: " + e;
            logError(m);
            throw new RemoteException(m);
        } finally {
            closeConnection(conn);
        }

        return w;
    }

    /**
     *  Gets the workflow desc.
     *
     *@param  pWorkflowId       the workflow identifier.
     *@return                   WorkflowData
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowDescData getWorkflowDesc(int pWorkflowId)
                                     throws RemoteException {

        WorkflowDescData wd = null;
        Connection conn = null;

        try {
            conn = getConnection();

            WorkflowData w = WorkflowDataAccess.select(conn, pWorkflowId);

            if (null != w) {
                wd = WorkflowDescData.createValue();
                wd.setWorkflow(w);

                BusEntityData b = BusEntityDataAccess.select(conn,
                                                             w.getBusEntityId());

                if (null != b) {
                    wd.setBusEntityShortDesc(b.getShortDesc());
                    wd.setBusEntityTypeCd(b.getBusEntityTypeCd());
                }
            }
        } catch (Exception e) {

            String m = "Workflow.getWorkflowDesc: id: " + pWorkflowId +
                       " error: " + e;
            logError(m);
            throw new RemoteException(m);
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }

        return wd;
    }

    /**
     *  Gets the workflow routing information values to be used by the request.
     *
     *@param  pBusEntityId      the BusEntity identifier.
     *@param  pWorkflowId       the workflow identifier.
     *@param  pWorkflowItemId   the workflow item identifier.
     *@return                   WorkflowRoutingData
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowRoutingData getWorkflowRouting(int pBusEntityId,
                                                  int pWorkflowId,
                                                  int pWorkflowItemId)
                                           throws RemoteException {

        return new WorkflowRoutingData();
    }

    /**
     *  Gets the queue information values to be used by the request.
     *
     *@param  pQueueId          the queue identifier.
     *@return                   QueueData
     *@throws  RemoteException  Required by EJB 1.0
     */
    public QueueData getQueue(int pQueueId)
                       throws RemoteException {

        return QueueData.createValue();
    }

    /**
     *  Describe <code>getWorkflowSiteIds</code> method here.
     *
     *@param  pWorkflowId          an <code>int</code> value
     *@return                      an <code>IdVector</code> value
     *@exception  RemoteException  if an error occurs
     */
    public IdVector getWorkflowSiteIds(int pWorkflowId)
                                throws RemoteException {

        Connection conn = null;
        IdVector idv = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(SiteWorkflowDataAccess.WORKFLOW_ID, pWorkflowId);
            idv = SiteWorkflowDataAccess.selectIdOnly(conn,
                                                      SiteWorkflowDataAccess.SITE_ID,
                                                      crit);
        } catch (Exception e) {

            String msg = "Workflow.getWorkflowSiteIds error: " +
                         e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }

        return idv;
    }

    public IdVector getSiteWorkflowIds(int pSiteId) throws RemoteException {

        Connection conn = null;

        try {

            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(SiteWorkflowDataAccess.SITE_ID, pSiteId);

            String widsReq = SiteWorkflowDataAccess.getSqlSelectIdOnly(SiteWorkflowDataAccess.WORKFLOW_ID, crit);

            DBCriteria workflowCriteria;
            workflowCriteria = new DBCriteria();
            workflowCriteria.addOneOf(WorkflowDataAccess.WORKFLOW_ID, widsReq);
            workflowCriteria.addEqualTo(WorkflowDataAccess.WORKFLOW_STATUS_CD, RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE);

            return WorkflowDataAccess.selectIdOnly(conn, workflowCriteria);

        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }
    /**
     *  Describe <code>getWorkflowRulesJoinVector</code> method here.
     *
     *@param  pWorkflowId          an <code>int</code> value
     *@return                      a <code>WorkflowRuleDataVector</code> value
     *@exception  RemoteException  if an error occurs
     */
    public WorkflowRuleJoinViewVector getWorkflowRules(int pWorkflowId)
                                                      throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(WorkflowRuleDataAccess.WORKFLOW_ID, pWorkflowId);
            crit.addOrderBy(WorkflowRuleDataAccess.RULE_SEQ);

            WorkflowRuleDataVector workflowRuleDV =
                    WorkflowRuleDataAccess.select(conn, crit);

            crit = new DBCriteria();
            crit.addEqualTo(WorkflowAssocDataAccess.WORKFLOW_ID,pWorkflowId);
            WorkflowAssocDataVector workflowAssocDV =
                    WorkflowAssocDataAccess.select(conn, crit);

            HashMap workflowAssocHM = new HashMap();
            for(Iterator iter=workflowAssocDV.iterator(); iter.hasNext();) {
              WorkflowAssocData waD = (WorkflowAssocData) iter.next();
              int wrId = waD.getWorkflowRuleId();
              Integer wrIdI = new Integer(wrId);
              WorkflowAssocDataVector waDV = (WorkflowAssocDataVector) workflowAssocHM.get(wrIdI);
              if(waDV==null)  {
                waDV = new WorkflowAssocDataVector();
                workflowAssocHM.put(wrIdI,waDV);
              }
              waDV.add(waD);
            }

            WorkflowRuleJoinViewVector workflowRuleJoinVwV = new WorkflowRuleJoinViewVector();
            WorkflowRuleDataVector wrDV = null;
            WorkflowAssocDataVector waDV = null;
            int prevSeq = 0;
            for(Iterator iter = workflowRuleDV.iterator(); iter.hasNext();) {
              WorkflowRuleData wrD = (WorkflowRuleData) iter.next();
              if(wrDV==null || prevSeq != wrD.getRuleSeq()) {
                prevSeq = wrD.getRuleSeq();
                wrDV = new WorkflowRuleDataVector();
                waDV = new WorkflowAssocDataVector();
                WorkflowRuleJoinView wrjVw = WorkflowRuleJoinView.createValue();
                workflowRuleJoinVwV.add(wrjVw);
                wrjVw.setWorkflowId(wrD.getWorkflowId());
                wrjVw.setRuleSeq(wrD.getRuleSeq());
                wrjVw.setRuleTypeCd(wrD.getRuleTypeCd());
                wrjVw.setRules(wrDV);
                wrjVw.setAssociations(waDV);
              }
              wrDV.add(wrD);
              Integer wrIdI = new Integer(wrD.getWorkflowRuleId());
              WorkflowAssocDataVector wrkWaDV = (WorkflowAssocDataVector) workflowAssocHM.get(wrIdI);
              if(wrkWaDV!=null)  {
                waDV.addAll(wrkWaDV);
              }

            }

            return workflowRuleJoinVwV;
        } catch (Exception e) {

            String msg = "Workflow.getWorkflowRulesCollection error: " +
                         e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    /**
     *  Describe <code>getWorkflowRulesCollection</code> method here.
     *
     *@param  pWorkflowId          an <code>int</code> value
     *@return                      a <code>WorkflowRuleDataVector</code> value
     *@exception  RemoteException  if an error occurs
     */
    public WorkflowRuleDataVector getWorkflowRulesCollection(int pWorkflowId)
                                                      throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(WorkflowRuleDataAccess.WORKFLOW_ID, pWorkflowId);
            crit.addOrderBy(WorkflowRuleDataAccess.RULE_SEQ);

            return WorkflowRuleDataAccess.select(conn, crit);
        } catch (Exception e) {

            String msg = "Workflow.getWorkflowRulesCollection error: " +
                         e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    /**
     *@exception  CreateException  Description of Exception
     *@exception  RemoteException  Description of Exception
     */
    public void ejbCreate()
                   throws CreateException, RemoteException {
    }

    /**
     *  Adds the workflow routing information values to be used by the request.
     *
     *@param  pWorkflowRouting  the workflow routing data.
     *@param  request           the workflow routing request data.
     *@return                   new WorkflowRoutingRequestData()
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowRoutingRequestData addWorkflowRouting(WorkflowRoutingData pWorkflowRouting,
                                                         WorkflowRoutingRequestData request)
                                                  throws RemoteException {

        return new WorkflowRoutingRequestData();
    }

    /**
     *  Adds the queue information values to be used by the request.
     *
     *@param  pQueue            the queue data.
     *@param  request           the queue request data.
     *@return                   new QueueRequestData()
     *@throws  RemoteException  Required by EJB 1.0
     */
    public QueueRequestData addQueue(QueueData pQueue,
                                     QueueRequestData request)
                              throws RemoteException {

        return new QueueRequestData();
    }

    /**
     *  Updates the workflow information values.
     *
     *@param  pUpdateWorkflowData  the workflow data.
     *@exception  RemoteException  Required by EJB 1.0
     */
    public void updateWorkflow(WorkflowData pUpdateWorkflowData)
                        throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();
            WorkflowDataAccess.update(conn, pUpdateWorkflowData);
        } catch (Exception e) {

            String m = "Workflow.updateWorkflow: " + pUpdateWorkflowData +
                       " error: " + e;
            logError(m);
            throw new RemoteException(m);
        } finally {

            try {

                if (null != conn) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }

        return;
    }

    /**
     *  Updates the workflow routing information values to be used by the
     *  request.
     *
     *@param  pUpdateWorkflowRoutingData  the workflow routing data.
     *@param  pBusEntityId                the BusEntity identifier.
     *@param  pWorkflowId                 the workflow identifier.
     *@param  pWorkflowItemId             the workflow item identifier.
     *@exception  RemoteException         Required by EJB 1.0
     */
    public void updateWorkflowRouting(WorkflowRoutingData pUpdateWorkflowRoutingData,
                                      int pBusEntityId, int pWorkflowId,
                                      int pWorkflowItemId)
                               throws RemoteException {
    }

    /**
     *  Updates the queue routing information values to be used by the request.
     *
     *@param  pUpdateQueueData     the queue data.
     *@param  pBusEntityId         the BusEntity identifier.
     *@param  pWorkflowId          the workflow identifier.
     *@param  pWorkflowItemId      the workflow item identifier.
     *@param  pQueueId             the queue identifier.
     *@exception  RemoteException  Required by EJB 1.0
     */
    public void updateQueue(QueueData pUpdateQueueData, int pBusEntityId,
                            int pWorkflowId, int pWorkflowItemId, int pQueueId)
                     throws RemoteException {
    }

    /**
     *  Creates the workflow information values to be used by the request.
     *
     *@param  pData             the workflow data
     *@return                   WorkflowData
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowData createWorkflow(WorkflowData pData)
                                throws RemoteException {

        // Validate.
        if (pData == null) {
            throw new RemoteException("Workflow.createWorkflow error: " +
                                      " empty object.");
        }

        if (pData.getShortDesc().length() == 0) {
            throw new RemoteException("Workflow.createWorkflow error: " +
                                      " name required.");
        }

        Connection conn = null;
        WorkflowData w;

        try {
            conn = getConnection();
            w = WorkflowDataAccess.insert(conn, pData);
        } catch (Exception e) {

            String msg = "Workflow.createWorkflow error: " + e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }

        return w;
    }

    /**
     *  Creates the workflow information values to be used by the request.
     *
     *@param  pWorkflowId       the workflow identifier
     *@param  pBusEntityId      the business entity identifier
     *@return                   WorkflowData
     *@throws  RemoteException  Required by EJB 1.0
     */
    public WorkflowData createWorkflow(int pWorkflowId, int pBusEntityId)
                                throws RemoteException {

        return WorkflowData.createValue();
    }

    /**
     * Describe <code>assignWorkflow</code> method here.
     *
     * @param pWorkflowId an <code>int</code> value
     * @param pSiteId     an <code>int</code> value
     * @param pUserName   a <code>String</code> value
     * @throws RemoteException if an error occurs
     */
    public void assignWorkflow(int pWorkflowId, int pSiteId, String pUserName) throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(SiteWorkflowDataAccess.SITE_ID, pSiteId);

            SiteWorkflowDataVector swdv = SiteWorkflowDataAccess.select(conn, crit);

            if (swdv.size() > 0) {

                // Update the first entry.
                SiteWorkflowData swd = (SiteWorkflowData) swdv.get(0);
                swd.setWorkflowId(pWorkflowId);
                swd.setModBy(pUserName);
                SiteWorkflowDataAccess.update(conn, swd);
            } else {

                // Create a new entry.
                SiteWorkflowData swd = SiteWorkflowData.createValue();
                swd.setWorkflowId(pWorkflowId);
                swd.setSiteId(pSiteId);
                swd.setAddBy(pUserName);
                swd.setModBy(pUserName);
                SiteWorkflowDataAccess.insert(conn, swd);
            }
        } catch (Exception e) {
            String msg = "Workflow.unassignWorkflow error: " + e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            closeConnection(conn);
        }
    }

    public void assignWorkflowToAccountSites(int pWorkflowId, int pAccountId,
                                             String pUserName)
                                      throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,
                            pAccountId);
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                            RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

            IdVector ids = BusEntityAssocDataAccess.selectIdOnly(conn,
                                                                 BusEntityAssocDataAccess.BUS_ENTITY1_ID,
                                                                 crit);

            if (ids.size() == 0) {

                // no sites for this account
                return;
            }

            for (int idx = 0; idx < ids.size(); idx++) {
                assignWorkflow(pWorkflowId, ((Integer)ids.get(idx)).intValue(),
                               pUserName);
            }
        } catch (Exception e) {

            String msg = "Workflow.assignWorkflowToAccountSites error: " +
                         e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }

        return;
    }

    /**
     *  Describe <code>unassignWorkflow</code> method here.
     *
     *@param  pWorkflowId          an <code>int</code> value
     *@param  pSiteId              an <code>int</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void unassignWorkflow(int pWorkflowId, int pSiteId)
                          throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(SiteWorkflowDataAccess.SITE_ID, pSiteId);
            crit.addEqualTo(SiteWorkflowDataAccess.WORKFLOW_ID, pWorkflowId);
            SiteWorkflowDataAccess.remove(conn, crit);
        } catch (Exception e) {

            String msg = "Workflow.unassignWorkflow error: " +
                         e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }

        return;
    }

    /**
     *  Describe <code>createWorkflowRule</code> method here.
     *
     *@param  pRule <code>WorkflowRuleData</code> value
     *@returna <code>WorkflowRuleData</code> value
     *@exception  RemoteException  if an error occurs
     */
    public WorkflowRuleData createWorkflowRule(WorkflowRuleData pRule)
                                        throws RemoteException {

        // Validate.
        if (pRule == null) {
            throw new RemoteException("Workflow.createWorkflowRule error: " +
                                      " empty object.");
        }

        Connection conn = null;
        WorkflowRuleData w;

        try {
            conn = getConnection();
            w = WorkflowRuleDataAccess.insert(conn, pRule);

            // Update the sequence.
            WorkflowRuleDataVector wrDV = new WorkflowRuleDataVector();
            wrDV.add(w);
            updateRuleSeq(conn, wrDV, Workflow.MOVE_RULE_LAST);
        } catch (Exception e) {

            String msg = "Workflow.createWorkflowRule error: " +
                         e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }

        return w;
    }

    /**
     *  Saves removes existing rule (if rule sequence >0) and adds new one
     *
     *@param pRuleSeqOrig rule sequence wich the rules had befor editing
     *@param  pRuleRecords a vector of <code>WorkflowRuleData</code> objects
     *@return a vector of <code>WorkflowRuleData</code> objects
     *@exception  RemoteException  if an error occurs
     */
    public WorkflowRuleDataVector saveWorkflowRule(int pRuleSeqOrig, WorkflowRuleDataVector pRuleRecords)
                                        throws RemoteException {
        WorkflowRuleDataVector wrDV =
                saveWorkflowRule(pRuleSeqOrig, pRuleRecords, null);
        return wrDV;

    }

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
                                        throws RemoteException {
        if (pRuleRecords == null || pRuleRecords.size()==0) {
            throw new RemoteException("Workflow.createWorkflowRule error: " +
                                      " empty object.");
        }

        WorkflowRuleData wfrD = (WorkflowRuleData) pRuleRecords.get(0);
        int workflowId = wfrD.getWorkflowId();
        if(workflowId<=0) {
            throw new RemoteException("Workflow.createWorkflowRule error: " +
                                      " no workflow id assigned.");
        }
        int ruleSeq = wfrD.getRuleSeq();
        String userName = wfrD.getModBy();
        for(int ii=1; ii<pRuleRecords.size(); ii++) {
          WorkflowRuleData wD = (WorkflowRuleData) pRuleRecords.get(ii);
          int rs = wD.getRuleSeq();
          if(ruleSeq!=rs) {
            throw new RemoteException("Workflow.createWorkflowRule error: " +
                                      " no workflow sequence consitence.");
          }
        }

        Connection conn = null;
        DBCriteria dbc = new DBCriteria();
        try {
          conn = getConnection();
          WorkflowRuleDataVector roulesToChangeSequence = null;
          if(ruleSeq>0 && ruleSeq!=pRuleSeqOrig) {
            dbc.addEqualTo(WorkflowRuleDataAccess.WORKFLOW_ID,workflowId);
            dbc.addGreaterOrEqual(WorkflowRuleDataAccess.RULE_SEQ,ruleSeq);
            dbc.addNotEqualTo(WorkflowRuleDataAccess.RULE_SEQ,pRuleSeqOrig);
            dbc.addOrderBy(WorkflowRuleDataAccess.RULE_SEQ);
            roulesToChangeSequence = WorkflowRuleDataAccess.select(conn,dbc);
          }
          WorkflowRuleDataVector dbRules = new WorkflowRuleDataVector();
          if(pRuleSeqOrig>0) {
            //get old records
            dbc = new DBCriteria();
            dbc.addEqualTo(WorkflowRuleDataAccess.WORKFLOW_ID,workflowId);
            dbc.addEqualTo(WorkflowRuleDataAccess.RULE_SEQ, pRuleSeqOrig);
            dbc.addOrderBy(WorkflowRuleDataAccess.WORKFLOW_RULE_ID);
            dbRules = WorkflowRuleDataAccess.select(conn,dbc);
          }
          int index = 0;
          int minRuleId = 0;
          IdVector dbRuleIdV = new IdVector();
          for(;index<pRuleRecords.size() && index<dbRules.size() ; index++) {
            WorkflowRuleData wD = (WorkflowRuleData) pRuleRecords.get(index);
            WorkflowRuleData dbWD = (WorkflowRuleData) dbRules.get(index);

            int ruleId = dbWD.getWorkflowRuleId();

            if (!Utility.isEqual(wD.getRuleAction(), dbWD.getRuleAction())) {
                // check if rule in the queue
                // and order is not approve or reject manually
                OrderDataVector wqOrders = getOrdersWorkflowQueueV(ruleId);
                if (wqOrders.size() > 0) {
                    throw new RemoteException(
                        "Error. There are unprocessed workflow actions related to the rule.");
                }
            }
            dbRuleIdV.add(new Integer(ruleId));
            if(ruleId < minRuleId || minRuleId ==0) minRuleId = ruleId;

            wD.setWorkflowRuleId(dbWD.getWorkflowRuleId());
            wD.setAddDate(dbWD.getAddDate());
            wD.setAddBy(dbWD.getAddBy());

            WorkflowRuleDataAccess.update(conn, wD);
          }
          for(;index<dbRules.size();index++) {
            WorkflowRuleData dbWD = (WorkflowRuleData) dbRules.get(index);
            dbRuleIdV.add(new Integer(dbWD.getWorkflowRuleId()));

            int ruleId = dbWD.getWorkflowRuleId();
            WorkflowQueueDataVector queueV = getWorkflowQueueEntries(ruleId);
            if (queueV.size() > 0) {
                throw new RemoteException(
                                      "Error. There are unprocessed workflow actions related to the rule.");
            }

            WorkflowRuleDataAccess.remove(conn,dbWD.getWorkflowRuleId());
          }
          for(;index<pRuleRecords.size(); index++) {
            WorkflowRuleData wD = (WorkflowRuleData) pRuleRecords.get(index);
            wD = WorkflowRuleDataAccess.insert(conn, wD);
            if(minRuleId==0) minRuleId = wD.getWorkflowRuleId();
          }
          if(roulesToChangeSequence!=null && roulesToChangeSequence.size()>0) {
            WorkflowRuleData wrD = (WorkflowRuleData) roulesToChangeSequence.get(0);
            if(wrD.getRuleSeq()==ruleSeq) {
              for(Iterator iter=roulesToChangeSequence.iterator(); iter.hasNext();) {
                wrD = (WorkflowRuleData) iter.next();
                wrD.setRuleSeq(wrD.getRuleSeq()+1);
                wrD.setModBy(userName);
                WorkflowRuleDataAccess.update(conn,wrD);
              }
            }
          }
          if(ruleSeq<=0) {
            // Update the sequence.
            updateRuleSeq(conn, pRuleRecords, Workflow.MOVE_RULE_LAST);
          }

          //Update  Associations
          WorkflowAssocDataVector dbWorkflowAssocDV = new WorkflowAssocDataVector();
          if(dbRuleIdV.size()>0) {
            dbc = new DBCriteria();
            dbc.addOneOf(WorkflowAssocDataAccess.WORKFLOW_RULE_ID, dbRuleIdV);
            dbc.addNotEqualTo(WorkflowAssocDataAccess.WORKFLOW_RULE_ID,minRuleId);
            WorkflowAssocDataAccess.remove(conn,dbc);

          }

          dbc = new DBCriteria();
          dbc.addEqualTo(WorkflowAssocDataAccess.WORKFLOW_RULE_ID,minRuleId);
          dbWorkflowAssocDV = WorkflowAssocDataAccess.select(conn,dbc);

          index = 0;
          if(pWorkflowAssocVector==null) {
            pWorkflowAssocVector = new WorkflowAssocDataVector();
          }
          for(;index<pWorkflowAssocVector.size() && index<dbWorkflowAssocDV.size() ; index++) {
            WorkflowAssocData waD = (WorkflowAssocData) pWorkflowAssocVector.get(index);
            WorkflowAssocData dbWaD = (WorkflowAssocData) dbWorkflowAssocDV.get(index);
            waD.setWorkflowId(workflowId);
            waD.setWorkflowRuleId(minRuleId);
            waD.setAddBy(dbWaD.getAddBy());
            waD.setAddDate(dbWaD.getAddDate());
            waD.setWorkflowAssocId(dbWaD.getWorkflowAssocId());
            WorkflowAssocDataAccess.update(conn, waD);
          }
          for(;index<dbWorkflowAssocDV.size();index++) {
            WorkflowAssocData dbWaD = (WorkflowAssocData) dbWorkflowAssocDV.get(index);
            WorkflowAssocDataAccess.remove(conn,dbWaD.getWorkflowAssocId());
          }
          for(;index<pWorkflowAssocVector.size(); index++) {
            WorkflowAssocData waD = (WorkflowAssocData) pWorkflowAssocVector.get(index);
            waD.setWorkflowId(workflowId);
            waD.setWorkflowRuleId(minRuleId);
            waD = WorkflowAssocDataAccess.insert(conn, waD);
          }
          return pRuleRecords;
        } catch (Exception e) {
            String msg = e.getMessage();
            logError(msg);
            e.printStackTrace();
            throw new RemoteException(msg);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
              throw new RemoteException(ex.getMessage());
            }
        }
    }

    /**
     *  <code>deleteWorkflowRule</code> method
     *
     *@param  pWorkflowRuleId      an <code>int</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void deleteWorkflowRule(int pWorkflowRuleId, boolean pDeleteChildQueue)
                            throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();
            WorkflowRuleData wrD = WorkflowRuleDataAccess.select(conn,pWorkflowRuleId);
            int workflowId = wrD.getWorkflowId();
            int ruleSeq = wrD.getRuleSeq();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(WorkflowRuleDataAccess.WORKFLOW_ID,workflowId);
            dbc.addEqualTo(WorkflowRuleDataAccess.RULE_SEQ,ruleSeq);
            IdVector workflowRuleIdV = WorkflowRuleDataAccess.selectIdOnly(conn,
                    WorkflowRuleDataAccess.WORKFLOW_RULE_ID,dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(WorkflowAssocDataAccess.WORKFLOW_RULE_ID,workflowRuleIdV);
            WorkflowAssocDataAccess.remove(conn, dbc);

            if (pDeleteChildQueue) {
                dbc = new DBCriteria();
                dbc.addEqualTo(WorkflowQueueDataAccess.WORKFLOW_RULE_ID, pWorkflowRuleId);
                WorkflowQueueDataAccess.remove(conn, dbc);
            }

            dbc = new DBCriteria();
            dbc.addOneOf(WorkflowRuleDataAccess.WORKFLOW_RULE_ID,workflowRuleIdV);
            WorkflowRuleDataAccess.remove(conn, dbc);


        } catch (Exception e) {

            String msg = "deleteWorkflowRule error: " + e;
            logError(msg);
            throw new RemoteException(msg);
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    public void deleteWorkflowRule(int pWorkflowRuleId)
                            throws RemoteException {

        deleteWorkflowRule(pWorkflowRuleId, false);
    }

    public void deleteWorkflow(int pWorkflowId)
                        throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(WorkflowAssocDataAccess.WORKFLOW_ID, pWorkflowId);
            WorkflowAssocDataAccess.remove(conn,dbc);

            dbc = new DBCriteria();
            dbc.addEqualTo(WorkflowRuleDataAccess.WORKFLOW_ID, pWorkflowId);
            WorkflowRuleDataAccess.remove(conn,dbc);


            WorkflowDataAccess.remove(conn, pWorkflowId);
        } catch (Exception e) {

            String msg = "Workflow.deleteWorkflow error: " + e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    public WorkflowData fetchWorkflowForSite(int pSiteId) throws RemoteException, DataNotFoundException {
        return fetchWorkflowForSite(pSiteId, RefCodeNames.WORKFLOW_TYPE_CD.ORDER_WORKFLOW);
    }

    /**
     * Description of the Method
     *
     * @param pSiteId Description of Parameter
     * @param pTypeCd workflow type cd
     * @return Description of the Returned Value
     * @throws RemoteException       Description of Exception
     * @throws DataNotFoundException Description of Exception
     */
    public WorkflowData fetchWorkflowForSite(int pSiteId, String pTypeCd) throws RemoteException, DataNotFoundException {

        WorkflowDataVector wfdv = null;
        Connection conn = null;
        String widsReq;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(SiteWorkflowDataAccess.SITE_ID, pSiteId);

            widsReq = SiteWorkflowDataAccess.getSqlSelectIdOnly(SiteWorkflowDataAccess.WORKFLOW_ID, crit);

            DBCriteria workflowCriteria;
            workflowCriteria = new DBCriteria();
            workflowCriteria.addOneOf(WorkflowDataAccess.WORKFLOW_ID, widsReq);
            workflowCriteria.addEqualTo(WorkflowDataAccess.WORKFLOW_TYPE_CD, pTypeCd);
            workflowCriteria.addEqualTo(WorkflowDataAccess.WORKFLOW_STATUS_CD, RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE);
            wfdv = WorkflowDataAccess.select(conn, workflowCriteria);

            if (wfdv.isEmpty()) {
                throw new DataNotFoundException("Workflow not found.Site Id => " + pSiteId + ",type cd => " + pTypeCd);
            } else if (wfdv.size() > 1) {
                throw new Exception("Multiple Workflow found.Site Id => " + pSiteId + ",type cd => " + pTypeCd);
            }

            return (WorkflowData) wfdv.get(0);

        } catch (DataNotFoundException e) {
            String msg = "Workflow.fetchWorkflowForSite error: " + e.getMessage();
            logError(msg);
            throw new DataNotFoundException(msg);
        } catch (Exception e) {
            String msg = "Workflow.fetchWorkflowForSite error: " + e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     *  Description of the Method
     *
     *@param  pWorkflowRuleId      Description of Parameter
     *@param  pMoveCmd             Description of Parameter
     *@exception  RemoteException  Description of Exception
     */
    public void updateWorkflowRuleSeq(int pWorkflowRuleId, int pMoveCmd)
                               throws RemoteException {

        Connection conn = null;
        WorkflowRuleData w = null;

        try {
            conn = getConnection();
            w = WorkflowRuleDataAccess.select(conn, pWorkflowRuleId);
            int workflowId = w.getWorkflowId();
            int ruleSeq = w.getRuleSeq();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(WorkflowRuleDataAccess.WORKFLOW_ID,workflowId);
            dbc.addEqualTo(WorkflowRuleDataAccess.RULE_SEQ,ruleSeq);
            WorkflowRuleDataVector wrDV =
                                       WorkflowRuleDataAccess.select(conn,dbc);

            updateRuleSeq(conn, wrDV, pMoveCmd);
        } catch (Exception e) {

            String msg = "Workflow.updateWorkflowRuleSeq error: " +
                         e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }

        return;
    }

    /**
     *  Description of the Method
     *
     *@param  conn               Description of Parameter
     *@param  pWorkflowRuleSeq  Set of WorkflowRuleDataObjects
     *@param  pMv                Description of Parameter
     *@exception  Exception      Description of Exception
     */
    private void updateRuleSeq(Connection conn,
                               WorkflowRuleDataVector pWorkflowRuleSeq, int pMv)
                        throws Exception {

        Statement stmt = conn.createStatement();
        String query = "";
        String idList = "";
        int workflowId = 0;
        int ruleSeq = 0;
        for(int ii=0; ii<pWorkflowRuleSeq.size(); ii++) {
          WorkflowRuleData wfrD = (WorkflowRuleData) pWorkflowRuleSeq.get(ii);
          if(idList.length()==0) {
            workflowId = wfrD.getWorkflowId();
            ruleSeq = wfrD.getRuleSeq();
            idList += "(";
          } else {
            idList += ",";
          }
          idList += wfrD.getWorkflowRuleId();
        }
        idList += ")";
        switch (pMv) {
            case Workflow.MOVE_RULE_LAST:

                // Make this the last rule.
                query = "update " +
                        WorkflowRuleDataAccess.CLW_WORKFLOW_RULE + " set " +
                        WorkflowRuleDataAccess.RULE_SEQ +
                        " = ( 1 + (select max(" +
                        WorkflowRuleDataAccess.RULE_SEQ + ") from " +
                        WorkflowRuleDataAccess.CLW_WORKFLOW_RULE +
                        " where " + WorkflowRuleDataAccess.WORKFLOW_ID +
                        " = " + workflowId + ") )" +
                        " where " + WorkflowRuleDataAccess.WORKFLOW_RULE_ID +
                        " in " + idList;

                stmt.execute(query);
                break;

            case Workflow.MOVE_RULE_UP:
                if (ruleSeq <= 0) {
                    // Can't move up from the 0th entry.
                    return;
                }

                query = "select max(" +WorkflowRuleDataAccess.RULE_SEQ +
                       ") as rule_seq from " +
                        WorkflowRuleDataAccess.CLW_WORKFLOW_RULE +
                        " where " + WorkflowRuleDataAccess.RULE_SEQ + " < " +
                        ruleSeq + " and " +
                        WorkflowRuleDataAccess.WORKFLOW_ID + " = " + workflowId;

                ResultSet rs = stmt.executeQuery(query);
                int prevSeq = 0;

                if(rs.next()) {
                    prevSeq = rs.getInt(1);
                }
                rs.close();
                if (prevSeq == 0) {
                    return;
                }

                query = "update " +
                        WorkflowRuleDataAccess.CLW_WORKFLOW_RULE + " set " +
                        WorkflowRuleDataAccess.RULE_SEQ + " = " + ruleSeq +
                        " where " + WorkflowRuleDataAccess.WORKFLOW_ID +
                        " = " + workflowId +
                        " and "+ WorkflowRuleDataAccess.RULE_SEQ +
                        " = " + prevSeq;

                stmt.execute(query);
                query = "update " +
                        WorkflowRuleDataAccess.CLW_WORKFLOW_RULE + " set " +
                        WorkflowRuleDataAccess.RULE_SEQ + " = " +prevSeq+
                        " where " +
                        WorkflowRuleDataAccess.WORKFLOW_RULE_ID + " IN " + idList;
                stmt.execute(query);
                break;

            case Workflow.MOVE_RULE_DOWN:
                if (ruleSeq <= 0) {
                    return;
                }

                query = "select min(" +WorkflowRuleDataAccess.RULE_SEQ +
                       ") as rule_seq from " +
                        WorkflowRuleDataAccess.CLW_WORKFLOW_RULE +
                        " where " + WorkflowRuleDataAccess.RULE_SEQ + " > " +
                        ruleSeq + " and " +
                        WorkflowRuleDataAccess.WORKFLOW_ID + " = " + workflowId;

                ResultSet rs2 = stmt.executeQuery(query);
                int nextSeq = 0;

                if(rs2.next()) {
                    nextSeq = rs2.getInt(1);
                }
                rs2.close();
                if (nextSeq == 0) {
                    return;
                }
                query = "update " +
                        WorkflowRuleDataAccess.CLW_WORKFLOW_RULE + " set " +
                        WorkflowRuleDataAccess.RULE_SEQ + " = " + ruleSeq +
                        " where " + WorkflowRuleDataAccess.WORKFLOW_ID +
                        " = " + workflowId +
                        " and "+ WorkflowRuleDataAccess.RULE_SEQ +
                        " = " + nextSeq;

                stmt.execute(query);
                query = "update " +
                        WorkflowRuleDataAccess.CLW_WORKFLOW_RULE + " set " +
                        WorkflowRuleDataAccess.RULE_SEQ + " = " +nextSeq+
                        " where " +
                        WorkflowRuleDataAccess.WORKFLOW_RULE_ID + " IN " + idList;
                stmt.execute(query);
                break;


            default:
                throw new Exception("WorkflowBean.updateRuleSeq: " +
                                    "Error: invalid command:" + pMv);
        }
        stmt.close();
   }


    private WorkflowRuleResult performRuleAction
        (WorkflowRuleData pRule,
         CostCenterData pOptionalCostCenterData,
         String pBypassWkflRuleActionCd) {

        String msg = "  Workflow rule triggered: \n" + pRule;
        WorkflowRuleResult rr = new WorkflowRuleResult();
        rr.setRule(pRule);
        rr.setStatus(WorkflowRuleResult.OK);
        if (pBypassWkflRuleActionCd != null &&
            pBypassWkflRuleActionCd.length() > 0 &&
            pBypassWkflRuleActionCd.equals(pRule.getRuleAction()) ) {
            logInfo("WKFL 0.2: pBypassWkflRuleActionCd=" +
                    pBypassWkflRuleActionCd );
            rr.setStatus(WorkflowRuleResult.GOTO_NEXT_RULE);
            return rr;
        }

        if (pOptionalCostCenterData != null) {
            rr.setCostCenterId(pOptionalCostCenterData.getCostCenterId());
            rr.setCostCenterName(pOptionalCostCenterData.getShortDesc());
            msg += " \n for cost center : " + pOptionalCostCenterData + "  == \n";
        }

        logInfo("WKFL 0.3:" + msg);

        if (pRule.getRuleAction().equals(
                    RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER)) {
            logInfo("WKFL 1: Order rejected");

            String rtype = pRule.getRuleTypeCd();

            if (rtype.equals(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL))
            {
                rr.setStatus(WorkflowRuleResult.ORDER_TOTAL_RULE_FAIL);
            }
            else if (rtype.equals(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU))
            {
                rr.setStatus(WorkflowRuleResult.ORDER_SKU_RULE_FAIL);
            }
            else if (rtype.equals(RefCodeNames.
                                  WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY))
            {
                rr.setStatus(WorkflowRuleResult.ORDER_VELOCITY_RULE_FAIL);
            }
            else {
                rr.setStatus(WorkflowRuleResult.BUDGET_RULE_FAIL);
            }

        } else if (pRule.getRuleAction().equals(
                           RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL)) {
            logInfo("WKFL 2: Order pending approval");
            rr.setStatus(WorkflowRuleResult.PENDING_APPROVAL);
        } else if (pRule.getRuleAction().equals(
                           RefCodeNames.WORKFLOW_RULE_ACTION.STOP_ORDER)) {
            logInfo("WKFL 3: Stop Order for review");
            rr.setStatus(WorkflowRuleResult.PENDING_ORDER_REVIEW);
        } else if (pRule.getRuleAction().equals(
                           RefCodeNames.WORKFLOW_RULE_ACTION.SEND_EMAIL)) {
            logInfo("WKFL 4: Approve the order and send email.");
            rr.setStatus(WorkflowRuleResult.OK);
        }

        // The remaining action is to approve the order.
        return rr;
    }

    public String getSiteWorkflowMessage(int pSiteId,String pTypeCd)
    throws RemoteException,    DataNotFoundException
    {
        // Get the workflow for the site.
        WorkflowData wfd = null;

        try {
            wfd = fetchWorkflowForSite(pSiteId,pTypeCd);
        } catch (DataNotFoundException e) {

            // This is an acceptable state.  A workflow
            // is not required for a site to make purchases.
            logDebug("WKFL 100: no workflow found for site: " + pSiteId);
            return null;
        }

        if (wfd.getWorkflowStatusCd().equals("INACTIVE")) {
            logDebug("WKFL 111: inactive workflow found for site: " + pSiteId);
            return null;
        }

        WorkflowRuleDataVector wfrv = getWorkflowRulesCollection(wfd.getWorkflowId());

        if (wfrv == null || wfrv.size() <= 0) {
            logInfo("WKFL 112: No workflow rules defined for site: " +
                    pSiteId);
            return null;
        }

        // Are any of the rules order velocity rules?

        Connection con = null;
        try
        {
            con = getConnection();
            for ( int idx =0; idx < wfrv.size(); idx++)
            {
                WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(idx);
                String rtype = rd.getRuleTypeCd();
                if (rtype.equals
                    (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY)
                    &&
                    rd.getRuleAction().equals(
                    RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER)
                )
                {
                    String dateCond = OrderDataAccess.ORIGINAL_ORDER_DATE
                    +" > (sysdate - " + rd.getRuleExpValue() + ") ";
                    String statusCond = OrderDataAccess.ORDER_STATUS_CD + " in " +
                    OrderDAO.kGoodOrderStatusSqlList;
                    DBCriteria dbc = new DBCriteria();
                    dbc.addEqualTo(OrderDataAccess.SITE_ID, pSiteId);
                    dbc.addCondition(dateCond);
                    dbc.addCondition(statusCond);
                    dbc.addOrderBy(OrderDataAccess.ORDER_ID, true);

                    OrderDataVector ov = OrderDataAccess.select(con, dbc);
                    if ( ov == null || ov.size() == 0 ) {
                        return null;
                    }
                    OrderData lastOrderData = (OrderData)ov.get(0);

                    String nextOrderDateAllowedSql = " select "
                    + " to_char("
                    + OrderDataAccess.ORIGINAL_ORDER_DATE
                    + ", 'Mon DD, YYYY')   ,  "
                    + " to_char("
                    + OrderDataAccess.ORIGINAL_ORDER_DATE + " + "
                    + rd.getRuleExpValue()
                    + ", 'Mon DD, YYYY') "
                    + " from " + OrderDataAccess.CLW_ORDER
                    + " where " + OrderDataAccess.ORDER_ID
                    + " = " + lastOrderData.getOrderId();

                    Statement stmt = con.createStatement();
                    logDebug("SQL:" + nextOrderDateAllowedSql);
                    ResultSet rs = stmt.executeQuery(nextOrderDateAllowedSql);
                    String res = "";
                    if ( rs.next() )
                    {
                        res = "Last order: "   + rs.getString(1)
                        + " Next order date: " + rs.getString(2);
                    }
                    stmt.close();
                    return res;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logError(e.getMessage());
        }
        finally
        {
            closeConnection(con);
        }
        return null;
    }

    public WorkflowRuleResult applySiteWorkflow(int pSiteId,
                                                int pAccountId,
                                                OrderData pOrderData,
                                                OrderItemDataVector pOrderItems) throws RemoteException, DataNotFoundException {

        Connection con = null;
        try {
            con = getConnection();
            return applySiteWorkflow1(con, pSiteId, pAccountId, pOrderData, pOrderItems, "");
        } catch (Exception e) {
            logError("applySiteWorkflow Error 2: " + e);
            WorkflowRuleResult wkflrr = new WorkflowRuleResult();
            wkflrr.setStatus(WorkflowRuleResult.FAIL);
            return wkflrr;
        } finally {
            closeConnection(con);
        }
    }

    public WorkflowRuleResult applySiteWorkflow1(Connection con,
                                                 int pSiteId,
                                                 int pAccountId,
                                                 OrderData pOrderData,
                                                 OrderItemDataVector pOrderItems,
                                                 String pBypassWkflRuleActionCd) throws DataNotFoundException, Exception {


        WorkflowRuleResult wkflrr = new WorkflowRuleResult();

        // Check the workflow role.  Workflow's apply only to
        // customers which are not APPROVERs.
        String wfrcd = pOrderData.getWorkflowStatusCd();

        if ((wfrcd != null) &&
            wfrcd.equals(RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER)) {

            // No need to check the rules since this user
            // has the authority to overide them.
            wkflrr.setStatus(WorkflowRuleResult.OK);

            return wkflrr;
        }

        // Get the workflow for the site.
        WorkflowData wfd = null;

        try {
            wfd = fetchWorkflowForSite(pSiteId, RefCodeNames.WORKFLOW_TYPE_CD.ORDER_WORKFLOW);
        } catch (DataNotFoundException e) {

            // This is an acceptable state.  A workflow
            // is not required for a site to make purchases.
            logDebug("WKFL 10: no workflow found for site: " + pSiteId);
            wkflrr.setStatus(WorkflowRuleResult.OK);

            return wkflrr;
        }

        if (wfd.getWorkflowStatusCd().equals("INACTIVE")) {

            // Obviously, we don't enforce inactive rules
            logDebug("WKFL 11: inactive workflow found for site: " + pSiteId);
            wkflrr.setStatus(WorkflowRuleResult.OK);

            return wkflrr;
        }

        WorkflowRuleDataVector wfrv =
            getWorkflowRulesCollection(wfd.getWorkflowId());

        if (wfrv.size() <= 0) {
            logInfo("WKFL 12: No workflow rules defined for site: " +
                    pOrderData.getSiteId());
            wkflrr.setStatus(WorkflowRuleResult.OK);

            return wkflrr;
        }

        // Iterate through the rules for the site.
        BigDecimal orderTotal = pOrderData.getOriginalAmount();

        if (orderTotal == null) {

            String msg = "applySiteWorkflow error: " + "null order amount.";
            logError(msg);
            orderTotal = new BigDecimal(0);
        }

        if (orderTotal.floatValue() <= 0) {
            logInfo("Zero value order accepted.  Order info: " + pOrderData);
            wkflrr.setStatus(WorkflowRuleResult.OK);

            return wkflrr;
        }

        BudgetSpendView moneySpent = null;
        BigDecimal siteBudgetWithThisOrder = null;
        Integer budgetYear = BudgetDAO.getCurrentBudgetYear(con, pAccountId);
        BigDecimal ruleExpValue = null;
        String ruleExp = null;
        boolean evalNextRule = true;
        wkflrr.setStatus(WorkflowRuleResult.OK);

        for (int ruleidx = 0; evalNextRule &&
                 ruleidx < wfrv.size(); ruleidx++) {

            WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);
            wkflrr.setRule(rd);
            wkflrr.setStatus(WorkflowRuleResult.GOTO_NEXT_RULE);
            logDebug("evaluating workflow rule: " + rd);

            // Get the rule type.
            String rtype = rd.getRuleTypeCd();
            ruleExp = rd.getRuleExp();

            ruleExpValue = null;
	    try {
		ruleExpValue = new BigDecimal(rd.getRuleExpValue());
	    }
	    catch (Exception e ) {
		e.printStackTrace();
		ruleExpValue = new BigDecimal(0);
	    }

            wkflrr.setRuleIsActive(false);

            CostCenterData ccd = null;

            if (rtype.equals(
                        RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC)) {

                // Get the cost centers and amounts spent in this order.
                String query = " select cost_center_id, " +
                    " sum(cust_contract_price * total_quantity_ordered ) " +
                    " from clw_order_item where order_id = " +
                    pOrderData.getOrderId() +
                    " and cost_center_id > 0 group by cost_center_id ";
                Statement stmt = null;
                ResultSet rs = null;
                logDebug("WKFL SQL: " + query);

                try {
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(query);
                    logDebug(" ++++ 1 Order id: " + pOrderData.getOrderId());

                    int oct = 0;
                    wkflrr.setStatus(WorkflowRuleResult.OK);

                    while (rs.next()) {

                        int thisCostCenterId = rs.getInt(1);
                        ccd = CostCenterDataAccess.select(con,
                                                          thisCostCenterId);

                        double thisCostCenterSum = rs.getDouble(2);
                        logDebug(" ++++ " + oct++ + " Order id: " +
                                 pOrderData.getOrderId() +
                                 " cost center id: " + thisCostCenterId +
                                 " sum for this cost center: " +
                                 thisCostCenterSum);

                        Site siteBean = getAPIAccess().getSiteAPI();
                        BudgetDataVector siteBudget = null;
                        if (budgetYear != null) {
                            siteBudget = BudgetDAO.getBudgetDataVector(con,
                                    pSiteId,
                                    thisCostCenterId,
                                    budgetYear,
                                    Utility.getAsList(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET));
                        }
                        if (null == siteBudget || siteBudget.isEmpty()) {
                            logError("applySiteWorkflow Error, " +
                                     " no site budget available.");

                            continue;
                        }

                        moneySpent = siteBean.getBudgetSpent(pSiteId,
                                                              thisCostCenterId);
                        siteBudgetWithThisOrder = new BigDecimal
                            (
                             moneySpent.getAmountAllocated()
                             .doubleValue() -
                             (moneySpent.getAmountSpent()
                              .doubleValue() + thisCostCenterSum));

                        // Check the remaining budget for the site.
                        if (ruleExp.equals("<")) {

                            if (siteBudgetWithThisOrder.doubleValue() <
                                ruleExpValue.doubleValue()) {

                                wkflrr =  performRuleAction
                                    (rd, ccd,  pBypassWkflRuleActionCd);
                                wkflrr.setRuleIsActive(true);

                            }
                        } else if (ruleExp.equals("<=")) {

                            if (siteBudgetWithThisOrder.doubleValue() <=
                                ruleExpValue.doubleValue()) {

                                wkflrr =  performRuleAction
                                    (rd, ccd, pBypassWkflRuleActionCd);
                                wkflrr.setRuleIsActive(true);
                            }
                        } else if (ruleExp.equals("==")) {

                            if (siteBudgetWithThisOrder.doubleValue() ==
                                ruleExpValue.doubleValue()) {

                                wkflrr =  performRuleAction
                                    (rd, ccd, pBypassWkflRuleActionCd);
                                wkflrr.setRuleIsActive(true);

                            }
                        }
                    }
                } catch (Exception e) {
                    logError("applySiteWorkflow Error 2: " + e);
                    wkflrr.setStatus(WorkflowRuleResult.FAIL);

                } finally {

                    try {

                        if (rs != null)
                            rs.close();

                        if (stmt != null)
                            stmt.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (rtype.equals(
                               RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD)) {

                String query = " select " +
                    " sum(cust_contract_price * total_quantity_ordered ) " +
                    " from clw_order_item where order_id = " +
                    pOrderData.getOrderId();
                Statement stmt = null;
                ResultSet rs = null;
                logDebug(getClass().getName() + " SQL 2: " + query);

                try {
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(query);
                    logDebug(" ++++ 11 Order id: " +
                             pOrderData.getOrderId());

                    wkflrr.setStatus(WorkflowRuleResult.OK);

                    if (rs.next()) {

                        double thisOrderSum = rs.getDouble(1);
                        Site siteBean = getAPIAccess().getSiteAPI();

                        BudgetDataVector siteBudgets = null;
                        if (budgetYear != null) {
                            siteBudgets = BudgetDAO.getBudgetDataVector(con,
                                    pSiteId,
                                    0,
                                    budgetYear,
                                    Utility.getAsList(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET));
                        }

                        if (null == siteBudgets || siteBudgets.isEmpty()) {
                            logError("applySiteWorkflow Error 2, " +
                                     " no site budget available.");
                        }

                        moneySpent = siteBean.getBudgetSpendView(pSiteId);
                        siteBudgetWithThisOrder = new BigDecimal
                            (moneySpent.getAmountAllocated()
                             .doubleValue() -
                             (moneySpent.getAmountSpent()
                              .doubleValue() + thisOrderSum));

                        // Check the remaining budget for the site.
                        if (ruleExp.equals("<")) {
                            if (siteBudgetWithThisOrder.doubleValue()
                                < ruleExpValue.doubleValue()) {

                                wkflrr =  performRuleAction
                                    (rd, ccd,
                                     pBypassWkflRuleActionCd);
                                wkflrr.setRuleIsActive(true);

                            }
                        } else if (ruleExp.equals("<=")) {

                            if (siteBudgetWithThisOrder.doubleValue()
                                <= ruleExpValue.doubleValue()) {

                                wkflrr =  performRuleAction
                                    (rd, ccd,
                                     pBypassWkflRuleActionCd);
                                wkflrr.setRuleIsActive(true);

                            }
                        } else if (ruleExp.equals("==")) {

                            if (siteBudgetWithThisOrder.doubleValue() ==
                                ruleExpValue.doubleValue()) {

                                wkflrr =  performRuleAction
                                    (rd, ccd, pBypassWkflRuleActionCd);
                                wkflrr.setRuleIsActive(true);
                            }
                        }
                    }
                } catch (Exception e) {
                    logError("applySiteWorkflow Error 21: " + e);
                    wkflrr.setStatus(WorkflowRuleResult.FAIL);
                } finally {

                    try {

                        if (rs != null)
                            rs.close();

                        if (stmt != null)
                            stmt.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (rtype.equals
                       (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL)) {


                if (ruleExp.equals("<")) {

                    if (orderTotal.doubleValue() < ruleExpValue.doubleValue()) {

                        wkflrr =  performRuleAction
                            (rd, ccd,
                             pBypassWkflRuleActionCd);
                        wkflrr.setRuleIsActive(true);
                    }
                } else if (ruleExp.equals("<=")) {

                    if (orderTotal.doubleValue() <= ruleExpValue.doubleValue()) {

                        wkflrr =  performRuleAction
                            (rd, ccd, pBypassWkflRuleActionCd);
                        wkflrr.setRuleIsActive(true);
                    }
                } else if (ruleExp.equals(">")) {

                    if (orderTotal.doubleValue() > ruleExpValue.doubleValue()) {

                        wkflrr =  performRuleAction
                            (rd, ccd, pBypassWkflRuleActionCd);
                        wkflrr.setRuleIsActive(true);
                    }
                } else if (ruleExp.equals(">=")) {

                    if (orderTotal.doubleValue() >= ruleExpValue.doubleValue()) {

                        wkflrr =  performRuleAction
                            (rd, ccd, pBypassWkflRuleActionCd);
                        wkflrr.setRuleIsActive(true);
                    }
                } else if (ruleExp.equals("==")) {

                    if (orderTotal.doubleValue() == ruleExpValue.doubleValue()) {

                        wkflrr =  performRuleAction
                            (rd, ccd, pBypassWkflRuleActionCd);
                        wkflrr.setRuleIsActive(true);
                    }
                }
            }
            else if (rtype.equals
                     (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU))
            {

                // Iterate through the items in the order.
                for ( int oi_idx = 0; pOrderItems != null
                          && oi_idx < pOrderItems.size(); oi_idx++ )
                {
                    OrderItemData oid = (OrderItemData)
                        pOrderItems.get(oi_idx);
                    String oisku1 = oid.getCustItemSkuNum(),
                        ruleSku = rd.getRuleExpValue();
                    int oisku2 = oid.getItemSkuNum();
                    logDebug(" Check Order for watched sku=" + ruleSku
                                + " cust sku=" + oisku1
                                + " cw sku=" + oisku2);

                    if ( oisku1.equals(ruleSku) ||
                         oisku2 == ruleExpValue.intValue() )
                    {
                        logDebug(" Order HAS watched sku=" + ruleSku
                                + " cust sku=" + oisku1
                                + " cw sku=" + oisku2);

                        wkflrr =  performRuleAction(rd, ccd,
                                                    pBypassWkflRuleActionCd);
                        logDebug(" wkflrr=" + wkflrr);
                        wkflrr.setRuleIsActive(true);
                        break;
                    }
                    else
                    {
                        wkflrr.setRuleIsActive(false);
                        wkflrr.setStatus(WorkflowRuleResult.GOTO_NEXT_RULE);
                    }
                }
            }
            else if (rtype.equals
                     (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY))
            {
                wkflrr.setStatus(WorkflowRuleResult.OK);
                if ( siteOrderVelocityExceeded
                     ( con, pOrderData,ruleExpValue.intValue() ))
                {
                    // Check against the last order date for this site.
                    wkflrr =  performRuleAction(rd, ccd,
                                                pBypassWkflRuleActionCd);
                    wkflrr.setRuleIsActive(true);
                }
                else
                {
                    wkflrr.setRuleIsActive(false);
                    wkflrr.setStatus(WorkflowRuleResult.GOTO_NEXT_RULE);
                }
            }
            else {
                logError("Unknown workflow rule type " + rtype);
            }
            if ( wkflrr != null ) {
                evalNextRule = wkflrr.isGotoNextRule();
            }
        }

        if ( wkflrr.isGotoNextRule() ) {
            wkflrr.setStatus(WorkflowRuleResult.OK);
        }

        logDebug("WKFL 99: returning workflow result: " + wkflrr);

        return wkflrr;
    }

    private boolean siteOrderVelocityExceeded
        ( Connection pConn, OrderData pOrderData,
          int pDaysBetweenOrders )
    {
        try {
            String dateCond = OrderDataAccess.ORIGINAL_ORDER_DATE
            +" > (sysdate - " + pDaysBetweenOrders + ") ";
            String statusCond = OrderDataAccess.ORDER_STATUS_CD + " in " +
                         OrderDAO.kGoodOrderStatusSqlList;
            DBCriteria dbc = new DBCriteria();
            dbc.addNotEqualTo(OrderDataAccess.ORDER_ID, pOrderData.getOrderId());
            dbc.addEqualTo(OrderDataAccess.SITE_ID, pOrderData.getSiteId());
            dbc.addCondition(dateCond);
            dbc.addCondition(statusCond);

            OrderDataVector v = OrderDataAccess.select(pConn, dbc);
            if ( v != null && v.size() > 0 ) {
                return true;
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            logError("siteOrderVelocityExceeded, error, " + exc +
                     " pOrderId=" + pOrderData.getOrderId());
        }

        return false;
    }

    public WorkflowQueueData createQueueEntry(int pSiteId,
                                              OrderData pOrderData,
                                              WorkflowRuleResult pRuleResult)
        throws RemoteException {

        String wkflrole = pOrderData.getWorkflowStatusCd();

        if (null == wkflrole || wkflrole.length() == 0) {
            wkflrole = RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN;
        }

        return createQueueEntry(pOrderData.getOrderId(), pSiteId,
                                pRuleResult.getRule().getRuleAction(),
                                wkflrole,
                                pRuleResult.getRule().getWorkflowId(),
                                pRuleResult.getRule().getWorkflowRuleId(),
                                pOrderData.getAddBy());
    }

    private WorkflowQueueData createQueueEntry(int pOrderId, int pBusEntityId,
                                               String pRuleAction,
                                               String pWorkflowRoleCd,
                                               int pWorkflowId,
                                               int pWorkflowRuleId,
                                               String pUser)
                                        throws RemoteException {

        WorkflowQueueData q = WorkflowQueueData.createValue();
        Connection conn = null;

        try {
            conn = getConnection();
            q.setOrderId(pOrderId);
            q.setBusEntityId(pBusEntityId);
            q.setShortDesc(pRuleAction);
            q.setWorkflowRoleCd(pWorkflowRoleCd);
            q.setWorkflowId(pWorkflowId);

            if (pWorkflowRuleId > 0) {
                q.setWorkflowRuleId(pWorkflowRuleId);
            }

            q.setAddBy(pUser);
            q = WorkflowQueueDataAccess.insert(conn, q);
        } catch (Exception e) {
            logError("createQueueEntry error: " + e);
        } finally {
            closeConnection(conn);
        }

        return q;
    }

    private void pq_sendOrderEmail(HashMap pEmailMessHM, Connection pConn,
                                   WorkflowQueueData pwfqd,
                                   WorkflowRuleData pwfrd) {
      Email eMail = null;
      OrderData od = null;
      WorkflowMailReqByGenerator mreq = null;
      String originalSubject = null;
      boolean useTemplate = false;
      FileAttach[] fileAttach = null;
   	  try {
        od = OrderDataAccess.select(pConn,pwfqd.getOrderId());

        OrderAddressData ad = OrderDAO.getShippingAddress
            (pConn,pwfqd.getOrderId());
        String subj = "", msgtype = "send_email", skuval = null;

        if (pwfrd.getRuleTypeCd().equals
            (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU))
        {
            subj = " -- Order has SKU:"
            + pwfrd.getRuleExpValue()
            + " Location: " + od.getOrderSiteName()
            + ", Order Number: " + od.getOrderNum()
            + " -- ";
            msgtype = "sku_ordered_email";
            skuval = pwfrd.getRuleExpValue();
        }
        else
        {
            subj = " -- Order notification."
            + " Location: " + od.getOrderSiteName()
            + ", Order Number: " + od.getOrderNum()
            + " -- ";

        }
        
        //save the subject in case of a template generation exception
        originalSubject = subj;

          mreq = new WorkflowMailReqByGenerator(pConn,  od, ad, msgtype, skuval );
          //if the message generator is the default (i.e. no class has been specified) and the
          //message type is "send_email" (i.e. has not been changed to "sku_ordered_email") then
          //the system should use a template to generate the email subject and message for each of the
          //recipients.
          useTemplate = mreq.getIsDefaultGenerator() && msgtype.equals("send_email");

          fileAttach = mreq.getAttachment();
          String osummary = mreq.getMessage();
          if (mreq.getSubject()!=null){
           subj = mreq.getSubject();
           originalSubject = subj;
          }
          if (!Utility.isSet(osummary)){
            String msg = "WorkflowBeen :: pq_sendOrderEmail()---<probably Order ERROR>---No Email Message content was generated!";
            logError(msg);
          }

        eMail = new Email();
        eMail.subject = subj;
        eMail.message = osummary;

        eMail.attachments=fileAttach;
        // Now remove the send email queue
        // entry since we are now in a pending review state.
        deleteWorkflowQueueEntry(pwfqd.getWorkflowQueueId());

    } catch (Exception e) {
        e.printStackTrace();
        logError("pq_sendOrderEmail, error: " + e);
        return;
    }

    // Get the user id to be notified.
    Integer userIdI = null;
    try {
      String cfg = pwfrd.getNextActionCd();
      int idx = cfg.lastIndexOf("Id:");
      if (idx != -1) {
         String idstr = "";
         idx += 3; // Skip over Id:
         while (cfg.charAt(idx) != '.') {
           idstr += cfg.charAt(idx);
           idx++;
           if (idx >= cfg.length()) {
              break;
           }
         }
         userIdI = new Integer(idstr.trim());
       }
    } catch (Exception exc) {
       exc.printStackTrace();
    }
    if(userIdI!=null && userIdI.intValue()>0) {
    	//if a template should be used to generate the email subject and message, do so
    	//now and replace the existing subject and message
    	if (useTemplate) {
	        String templateType = RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE;
    		Map<String, String> emailData = generateEmailData(templateType, od.getStoreId(), 
    				od.getAccountId(), od.getOrderId(), userIdI.intValue(),
    				null, null, originalSubject, mreq.getMessage());
        	eMail = new Email();
          	eMail.subject = emailData.get(Constants.TEMPLATE_OUTPUT_EMAIL_SUBJECT);
          	eMail.message = emailData.get(Constants.TEMPLATE_OUTPUT_EMAIL_BODY);
            eMail.attachments=fileAttach;
    	}
		addEmailMessage(pEmailMessHM, userIdI, eMail);
    }

    try {
      int userGroupId = pwfrd.getEmailGroupId();
      if(userGroupId>0) {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(GroupAssocDataAccess.GROUP_ID,userGroupId);
        if(userIdI!=null && userIdI.intValue()>0) {
          dbc.addNotEqualTo(GroupAssocDataAccess.USER_ID,userIdI.intValue());
        }
        dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                       RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
        IdVector groupUserIdV =
           GroupAssocDataAccess.selectIdOnly(pConn,GroupAssocDataAccess.USER_ID,dbc);
        Map<String, TemplateDataExtended> localeToTemplateMap = new HashMap();
	    Map templateObjects = TemplateUtilities.getDefaultOrderEmailObjectMap(od.getOrderId());
        for(Iterator iter=groupUserIdV.iterator(); iter.hasNext();) {
          Integer groupUserIdI = (Integer) iter.next();
	      	//if a template should be used to generate the email subject and message, do so
	      	//now and replace the existing subject and message
	      	if (useTemplate) {
    	        String templateType = RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE;
        		Map<String, String> emailData = generateEmailData(templateType, od.getStoreId(), 
        				od.getAccountId(), od.getOrderId(), groupUserIdI.intValue(),
        				localeToTemplateMap, templateObjects, originalSubject, mreq.getMessage());
            	eMail = new Email();
              	eMail.subject = emailData.get(Constants.TEMPLATE_OUTPUT_EMAIL_SUBJECT);
              	eMail.message = emailData.get(Constants.TEMPLATE_OUTPUT_EMAIL_BODY);
                eMail.attachments=fileAttach;
	      	}
      		addEmailMessage(pEmailMessHM, groupUserIdI, eMail);
        }
      }
    } catch (Exception exc) {
      exc.printStackTrace();
    }
    }

    private void addEmailMessage(HashMap pEmailHM, Integer pUserIdI, Email pEmail) {
      String mess = pEmail.message;
      if(mess==null) mess = "";
      LinkedList emails = (LinkedList) pEmailHM.get(pUserIdI);
      if(emails==null) {
        emails = new LinkedList();
        pEmailHM.put(pUserIdI, emails);
        emails.add(pEmail);
      } else {
        for(Iterator iter=emails.iterator(); iter.hasNext();) {
          Email em = (Email) iter.next();
          if(!mess.equals(em.message)) {
            emails.add(pEmail);
          }
        }
      }
    }

    private void pq_sendOrderEmail(HashMap pEmailMessHM,
            Connection pConn,
            WorkflowQueueDataVector pWorkflowQueueDV)
    {
      log("================== pq_sendOrderEmail=========Begin");
      if(pWorkflowQueueDV==null) return;
       WorkflowQueueData priorityWqD = null;
       WorkflowRuleData priorityWrD = null;
       for(int ii=0; ii<pWorkflowQueueDV.size(); ii++) {
         WorkflowQueueData wqD = (WorkflowQueueData) pWorkflowQueueDV.get(ii);
         int ruleId = 0;
         int minRuleSeq=-1;
         boolean minRuleSeqFl = false;
         WorkflowRuleData wrD = null;
         try {
          ruleId = wqD.getWorkflowRuleId();
          wrD = WorkflowRuleDataAccess.select(pConn,ruleId);
         }catch(Exception exc) {
           logError("Can't find workflow rule. Rule id="+ruleId+
                          ". Workflow queue id="+wqD.getWorkflowQueueId());
           continue;
         }
         int ruleSeq = wrD.getRuleSeq();
         if(!minRuleSeqFl || minRuleSeq>ruleSeq) {
           minRuleSeq = ruleSeq;
           priorityWqD = wqD;
           priorityWrD = wrD;
           minRuleSeqFl = true;
         }
       }
       if(priorityWrD!=null) {
         pq_sendOrderEmail(pEmailMessHM, pConn,priorityWqD, priorityWrD);
       }
    }

    private void pq_forwardOrder(HashMap pEmailMessHM,
                                 Connection pConn,
                                 WorkflowQueueDataVector pWorkflowQueueDV) {

        try {
          if(pWorkflowQueueDV==null || pWorkflowQueueDV.size()==0) return;

          WorkflowQueueData wqD0 = (WorkflowQueueData) pWorkflowQueueDV.get(0);
          int busEntityId = wqD0.getBusEntityId();
          int orderId = wqD0.getOrderId();

          for(Iterator iter=pWorkflowQueueDV.iterator(); iter.hasNext();) {
            WorkflowQueueData wqD = (WorkflowQueueData) iter.next();

              createQueueEntry(orderId, busEntityId,
                             RefCodeNames.WORKFLOW_RULE_ACTION.PENDING_REVIEW,
                             RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER,
                             wqD.getWorkflowId(), wqD.getWorkflowRuleId(),
                             wqD.getAddBy());
              // Now remove the forward for approval queue
              // entry since we are now in a pending review state.
              deleteWorkflowQueueEntry(wqD.getWorkflowQueueId());
          }
        } catch (Exception e) {
            e.printStackTrace();
            logError("pq_forwardOrder, error: " + e);
        }
    }

    private void pq_updatePendingOrder(Connection pConn, int pOrderId,
                                       boolean pApproveTheOrder)
                                throws Exception {
    	
        log.info("pq_updatePendingOrder: order_id = " + pOrderId);
        
        OrderData orderD = null;
        try {
          orderD = OrderDataAccess.select(pConn,pOrderId);
        }catch(DataNotFoundException exc) {
           logError("Workflow. Order not found. Order Id: "+pOrderId);
           exc.printStackTrace();
           return;
        }

        if(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.
                                     equals(orderD.getOrderStatusCd())) {
        	
           log.info("Order with order_id = " + pOrderId + " has " + RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL + " status.");
	       
           String pMsg = "Workflow, status changed to: ",
		   newstatus = "";

           if (pApproveTheOrder) {
              newstatus =
              (RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.
              equals(orderD.getOrderTypeCd()))?
              RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION:
	          RefCodeNames.ORDER_STATUS_CD.ORDERED;
	       } else {
	          newstatus = RefCodeNames.ORDER_STATUS_CD.REJECTED;
	       }
	       orderD.setOrderStatusCd(newstatus);
	       orderD.setModBy("System");
	       OrderDataAccess.update(pConn, orderD);

	       pMsg += newstatus + " " + new Date();
	       addWorkflowOrderNote2(pConn, pMsg,pOrderId, null);

       } //endif
    } // end of pq_updatePendingOrder() method

    private class WorkflowMailReq {
        public WorkflowMailReq( String pMsgType,
        OrderData pOd, OrderAddressData pOad ) {
            mMsgType = pMsgType;
            mOrderData = pOd;
            mOrderAddressData = pOad;
            mOptSiteBudgets = null;
            mSkuVal = null;
            mWorkflowQueueDV = null;
        }

        public OrderData mOrderData;
        public OrderAddressData mOrderAddressData;
        public String mMsgType = "", mSkuVal = "";
        public BudgetSpendViewVector mOptSiteBudgets;
        public WorkflowQueueDataVector mWorkflowQueueDV;

    }

    private  String makeOrderSummary(Connection pConn, WorkflowMailReq pWorkflowMailReq) {
        return makeOrderSummary(pConn, pWorkflowMailReq, false);
    }

    private  String makeOrderSummary(Connection pConn, WorkflowMailReq pWorkflowMailReq, boolean pUseSiteName) {

        //String url = System.getProperty("com.cleanwise.baseurl");

        //if (url == null) {
        //    url = "com.cleanwise.baseurl";
        //}

        // This url is meaningless since it forces the user
        // to login when they try to access it.  Once logged in
        // the user is directed to their home area and not the
        // intended order information.  The logon logic should
        // direct the user to this url after a successful login.
        // url += "/cleanwise/store/handleOrder.do?action=view&orderId=" + pOrderData.getOrderId();
        //url += "/";


        String msg = "\n\n";
        msg += getProperty("mail.msg.header", pWorkflowMailReq.mOrderData.getStoreId(), pConn);

        String totalPrice;
        Locale loc = new Locale("en_US");
        if (null != pWorkflowMailReq.mOrderData.getLocaleCd()) {
            loc = Utility.parseLocaleCode(pWorkflowMailReq.mOrderData.getLocaleCd());
        }
        NumberFormat currencyFmt = NumberFormat.getCurrencyInstance(loc);
        BigDecimal amt = pWorkflowMailReq.mOrderData.getOriginalAmount();
          BigDecimal totPrice = calculateOrderTotal(pConn,  pWorkflowMailReq.mOrderData);

       if (null == amt) amt = new BigDecimal(0);
        if (null == totPrice) totPrice = new BigDecimal(0);

        totalPrice = currencyFmt.format(totPrice);

        String accountName = fetchAccountName(pConn, pWorkflowMailReq.mOrderData);


        if (pWorkflowMailReq.mMsgType.equals("send_email")) {

            String[] args1 = {accountName};
            msg += MessageFormat.format(
                    getProperty("mail.body.inform", pWorkflowMailReq.mOrderData.getStoreId(), pConn),
                    args1);

        } else if (pWorkflowMailReq.mMsgType.equals("sku_ordered_email")) {

            String[] args1 = {accountName, pWorkflowMailReq.mSkuVal};
            msg += MessageFormat.format(
                    getProperty("mail.body.sku_inform", pWorkflowMailReq.mOrderData.getStoreId(), pConn),
                    args1);

        } else if (pWorkflowMailReq.mMsgType.equals("send_email_revised")) {

            String[] args1 = {accountName};
            msg += MessageFormat.format(
                    getProperty("mail.body.inform.revised", pWorkflowMailReq.mOrderData.getStoreId(), pConn),
                    args1);

        } else if (pWorkflowMailReq.mMsgType.equals("reject_order")) {

            String[] args2 = {
                    pWorkflowMailReq.mOrderData.getOrderNum(),
                    pWorkflowMailReq.mOrderData.getOriginalOrderDate().toString()
            };

            msg += MessageFormat.format(
                    getProperty("mail.body.orderrejection", pWorkflowMailReq.mOrderData.getStoreId(), pConn),
                    args2);

        } else if (pWorkflowMailReq.mMsgType.equals("forward_for_approval")) {

            String[] args2 = new String[2];
            if (pUseSiteName) {
                args2[0] = fetchSiteName(pConn, pWorkflowMailReq.mOrderData);
            } else {
                args2[0] = accountName;
            }

            args2[1] = "";
            LinkedList messages = new LinkedList();

            if (pWorkflowMailReq.mWorkflowQueueDV != null) {
                for (int ii = 0; ii < pWorkflowMailReq.mWorkflowQueueDV.size(); ii++) {
                    WorkflowQueueData wqD =
                            (WorkflowQueueData) pWorkflowMailReq.mWorkflowQueueDV.get(ii);
                    if (Utility.isSet(wqD.getMessage())) {
                        messages.add(wqD.getMessage());
                    }
                }
            }

            msg += MessageFormat.format(
                    getProperty("mail.body.approve", pWorkflowMailReq.mOrderData.getStoreId(), pConn),
                    args2);

            if (messages.size() == 1) {
                msg += " " + messages.get(0) + ".";
            }

            if (messages.size() > 1) {
                msg += " Problems found: ";
                for (int ii = 0; ii < messages.size(); ii++) {
                    int ln = ii + 1;
                    msg += "\n" + ln + ". " + messages.get(ii) + ".";
                }
            }

            msg += "\n\n";

        } else {
            msg += "This Email is to inform you that the approval " +
                    " period for order ";
            msg += pWorkflowMailReq.mOrderData.getOrderNum() + " ";
            msg += "has expired and that order ";
            msg += pWorkflowMailReq.mOrderData.getOrderNum() + " ";

            String[] args3 = {
                    pWorkflowMailReq.mOrderData.getOrderNum(),
                    pWorkflowMailReq.mOrderData.getOrderNum()
            };

            if (pWorkflowMailReq.mMsgType.equals("agedApp")) {
                msg += MessageFormat.format(getProperty("mail.body.elapsed1", pWorkflowMailReq.mOrderData.getStoreId(), pConn),
                        args3);
            } else {
                msg += MessageFormat.format(getProperty("mail.body.elapsed2", pWorkflowMailReq.mOrderData.getStoreId(), pConn),
                        args3);
            }
        }

        String[] bodyArgs = new String[10];
        bodyArgs[0] = pWorkflowMailReq.mOrderData.getOrderNum();
        bodyArgs[1] = pWorkflowMailReq.mOrderData.getOriginalOrderDate().toString();

        if (pWorkflowMailReq.mOrderData.getRequestPoNum() == null) {
            bodyArgs[2] = " N/A ";
        } else {
            bodyArgs[2] = pWorkflowMailReq.mOrderData.getRequestPoNum();
        }

        bodyArgs[3] = pWorkflowMailReq.mOrderData.getOrderSiteName();
        bodyArgs[4] = pWorkflowMailReq.mOrderAddressData.getAddress1();
        bodyArgs[5] = pWorkflowMailReq.mOrderAddressData.getCity();
        bodyArgs[6] = pWorkflowMailReq.mOrderAddressData.getStateProvinceCd();
        bodyArgs[7] = pWorkflowMailReq.mOrderAddressData.getPostalCode();

        String fullname = null;

        if (pWorkflowMailReq.mOrderData.getOrderContactName() != null) {
            fullname = pWorkflowMailReq.mOrderData.getOrderContactName();
        } else {

            if (pWorkflowMailReq.mOrderData.getUserFirstName() != null) {
                fullname = pWorkflowMailReq.mOrderData.getUserFirstName();
            }

            fullname += " ";

            if (pWorkflowMailReq.mOrderData.getUserLastName() != null) {
                fullname += pWorkflowMailReq.mOrderData.getUserLastName();
            }
        }

        bodyArgs[8] = fullname;
        bodyArgs[9] = totalPrice;

        if (pWorkflowMailReq.mOrderData.getRefOrderNum() != null &&
                pWorkflowMailReq.mOrderData.getRefOrderNum().length() > 0) {
            msg += "\n Original Order Number: "
                    + pWorkflowMailReq.mOrderData.getRefOrderNum() + " \n";
        }

        String fmtstr;

        if (!pWorkflowMailReq.mMsgType.equals("send_email_revised")) {
            fmtstr = getProperty("mail.body.ordersummary", pWorkflowMailReq.mOrderData.getStoreId(), pConn);
        }else{
            bodyArgs[10] = pWorkflowMailReq.mOrderData.getRefOrderNum();
            fmtstr = getProperty("mail.body.inform.revised", pWorkflowMailReq.mOrderData.getStoreId(), pConn);
        }
        msg += MessageFormat.format(fmtstr, bodyArgs);

        if (pWorkflowMailReq.mOrderData.getComments() != null) {
            msg += pWorkflowMailReq.mOrderData.getComments();
        }

        // Now add the current site budgets.
        if (null != pWorkflowMailReq.mOptSiteBudgets &&
                pWorkflowMailReq.mOptSiteBudgets.size() > 0) {

            msg += " \n\n---   Budget Spending Information \n";

            for (int idx = 0; idx < pWorkflowMailReq.mOptSiteBudgets.size(); idx++) {

                BudgetSpendView b = (BudgetSpendView) pWorkflowMailReq.mOptSiteBudgets.get(idx);

                // Format the money amounts.
                String aalloc = currencyFmt.format(b.getAmountAllocated());
                String aspent = currencyFmt.format(b.getAmountSpent());
                String adiff = currencyFmt.format(b.getAmountAllocated().subtract(b.getAmountSpent()));
                msg += "\n  Cost Center Name  : " + b.getCostCenterName();
                msg += "\n    Budget Allocated: " + aalloc;
                msg += "\n    Budget Spent    : " + aspent;
                msg += "\n    Difference      : " + adiff;
            }

            msg += " \n---   \n";
        }

        msg += " \n\n";
        msg += getProperty("mail.msg.footer", pWorkflowMailReq.mOrderData.getStoreId(), pConn);

        return msg;
    }

    private void pq_checkPendingOrder(HashMap pEmailMessHM,
                                      Connection pConn,
                                      WorkflowQueueData pwfqd,
                                      WorkflowRuleData pwfrd) {

    	log.info("pq_checkPendingOrder. Processing workflow_queue entry: "+pwfqd.getWorkflowQueueId()+" for orderId "+pwfqd.getOrderId());
        // Get the action to perform.
        String cfg = pwfrd.getNextActionCd();

        if (cfg.length() <= 6) {
            logError("pq_checkPendingOrder()=> invalid next action: " + cfg + " in rule: " + pwfrd);
        } else {

            String daysstr = "";
            int didx = 6; // Skip over " After" string and end

            // at the start of "days".
            while (cfg.charAt(didx) != 'd') {
                daysstr += cfg.charAt(didx);
                didx++;

                if (didx >= cfg.length()) {
                    break;
                }
            }

            Integer days = new Integer(daysstr.trim());
            log.info("*****AAAA: days = " + days);

            // Compare the current date to the date this queue
            // entry was made.
            // If this entry >= the days configured, then perform
            // the action specified (approve of reject the order).
            Date qdate = pwfqd.getAddDate();
            Date now = new Date();

            log.info("*****BBBB: today = " + now + " queue entry date = " + qdate);
            
            if (now.compareTo(qdate) > 0) {

                long daysAllowed = days.longValue();
                long businessDays = Utility.calculateBusinessDays(now, qdate);
                
                log.info("*****CCCC: businessDays = " + businessDays);

                if (businessDays < daysAllowed) {
                    log("pq_checkPendingOrder()=> This rule has not yet expired.");
                    return;
                }
            }

            // The order should now be rejected or approved.
            boolean approveTheOrder = true;

            if (cfg.indexOf(RefCodeNames.WORKFLOW_RULE_ACTION.APPROVE_ORDER) != -1) {
                approveTheOrder = true;
                log.info("*****DDDD: Approve the order");
            } else if (cfg.indexOf(RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER) != -1) {
                approveTheOrder = false;
                log.info("*****EEEE: Reject the order");
            } else {
                logError("pq_checkPendingOrder()=> invalid next action: " + cfg + " in rule: " + pwfrd);
                return;
            }

            try {
                pq_updatePendingOrder(pConn, pwfqd.getOrderId(), approveTheOrder);

                log.info("*****FFFF: pq_updatePendingOrder() method finished its work.");
                
                OrderData od = OrderDataAccess.select(pConn, pwfqd.getOrderId());
                OrderAddressData ad = OrderDAO.getShippingAddress(pConn, pwfqd.getOrderId());
                int userId = od.getUserId();
                String ostatuscd = od.getOrderStatusCd();

                if ( ostatuscd != null && ostatuscd.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)) {
                	String osummary;
                    String ty = "";

                    if (approveTheOrder) {
                        ty = "agedApp";
                    } else {
                        ty = "agedDen";
                    }

                    WorkflowMailReq mreq = new WorkflowMailReq(ty, od, ad);
                    osummary = makeOrderSummary (pConn, mreq);

                    User userBean = getAPIAccess().getUserAPI();

                    // Notify the buyer.
                    IdVector userIdV = new IdVector();
                    userIdV.add(new Integer(userId));

                    // Now notify the approvers of the
                    // manner in which the order was dispatched.

                    int userGroupId = pwfrd.getEmailGroupId();
                    if(userGroupId>0) {
                        DBCriteria dbc = new DBCriteria();
                        dbc.addEqualTo(GroupAssocDataAccess.GROUP_ID,userGroupId);
                        dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                                RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
                        dbc.addNotEqualTo(GroupAssocDataAccess.USER_ID,userId);
                        userIdV = GroupAssocDataAccess.selectIdOnly(pConn,GroupAssocDataAccess.USER_ID,dbc);

                    } else { //Approver list is not configured
                        UserDataVector uv = userBean.getUsersCollectionByBusEntity
                                (pwfqd.getBusEntityId(), RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER);

                        for(Iterator iter=uv.iterator(); iter.hasNext();) {
                            UserData uD = (UserData) iter.next();
                            userIdV.add(new Integer(uD.getUserId()));
                        }
                    }

                    String subj;
                    if (approveTheOrder) {
                        subj = " -- Aged Order approval notification. ";
                    } else {
                        subj = " -- Aged Order cancelled notification. ";
                    }

                    subj += " Location: " + od.getOrderSiteName();
                    subj += ", Order Number: " + od.getOrderNum();
                    subj += ", Order Date: " + od.getOriginalOrderDate();
                    subj += " -- ";

                    for (Iterator iter=userIdV.iterator(); iter.hasNext();) {
                        Integer userIdI = (Integer) iter.next();
                        Email eMail = new Email();
                        eMail.subject = subj;
                        eMail.message = osummary;
                        addEmailMessage(pEmailMessHM, userIdI, eMail);
                    }

                }

                // Now remove the pending review queue entry.
                deleteWorkflowQueueEntryByOrderId(pwfqd.getOrderId());

             } catch (Exception e) {
                logError("pq_checkPendingOrder, error: " + e);
            }
        }
    }

    private void pq_checkPendingOrder(HashMap pEmailMessHM,
                                      Connection pConn,
                                      WorkflowQueueDataVector pWorkflowQueueDV)
    {
        log("pq_checkPendingOrder()=> Begin");

        if(pWorkflowQueueDV==null){
            return;
        }
    
        log.info("pq_checkPendingOrder :: pWorkflowQueueDV.size() = " + pWorkflowQueueDV.size());
    
        int orderId = -1;
        WorkflowQueueDataVector orderWqDV = new WorkflowQueueDataVector();
        int idx = 0;
        for (; idx < pWorkflowQueueDV.size(); idx++) {
          WorkflowQueueData wqD = (WorkflowQueueData) pWorkflowQueueDV.get(idx);
          if(orderId!=wqD.getOrderId()) {
            if(idx!=0) {
              pq_processOrderRules(pEmailMessHM, pConn, orderWqDV);	
              orderWqDV = new WorkflowQueueDataVector();
            }
            orderId = wqD.getOrderId();
          } 
          orderWqDV.add(wqD);
        }          
        if(idx>0) { 
          pq_processOrderRules(pEmailMessHM, pConn, orderWqDV);	
        }
        
    log("pq_checkPendingOrder()=> End");
    
    } //private void pq_checkPendingOrder()

    private void pq_processOrderRules(HashMap pEmailMessHM,
                                       Connection pConn,
                                       WorkflowQueueDataVector pWfQdV) {
    	
        log("pq_processOrederRules => Begin");

        if(pWfQdV==null){
            return;
        }
        log.info("pWfQdV.size() = " + pWfQdV.size());
        
        WorkflowQueueData priorityWqD = null;
        WorkflowRuleData priorityWrD = null;
        int minRuleSeq=-1;        
        
        for(int ii=0; ii<pWfQdV.size(); ii++) {
            WorkflowQueueData wqD = (WorkflowQueueData) pWfQdV.get(ii);
            int ruleId = 0;
            WorkflowRuleData wrD = null;

            try {
                ruleId = wqD.getWorkflowRuleId();
                wrD = WorkflowRuleDataAccess.select(pConn,ruleId);
            }catch(Exception exc) {
                logError("Can't find workflow rule. Rule id="+ruleId+
                        ". Workflow queue id="+wqD.getWorkflowQueueId());
                continue;
            }

            int ruleSeq = wrD.getRuleSeq();

            if (minRuleSeq == -1 || minRuleSeq > ruleSeq) {
                minRuleSeq = ruleSeq;
                priorityWqD = wqD;
                priorityWrD = wrD;
            }
        } // end for
        
        if(priorityWrD!=null) {
                log.info("priorityWqD = " + priorityWqD);
            	log.info("priorityWrD = " + priorityWrD);
            	log.info("Before pq_checkPendingOrder(pEmailMessHM, pConn,priorityWqD, priorityWrD) call.");
                pq_checkPendingOrder(pEmailMessHM, pConn,priorityWqD, priorityWrD);
            	log.info("After pq_checkPendingOrder(pEmailMessHM, pConn,priorityWqD, priorityWrD) call.");
        }       
        
        log("pq_processOrederRules => End");
        
    }
    
    private void pq_stopOrder(HashMap pEmailMessHM,
                                      Connection pConn,
                                      WorkflowQueueDataVector pWorkflowQueueDV)
    {
       if(pWorkflowQueueDV==null || pWorkflowQueueDV.size()==0) return;
       WorkflowQueueData wqD0 = (WorkflowQueueData) pWorkflowQueueDV.get(0);
       OrderData oD = null;
       try {
         oD = OrderDataAccess.select(pConn, wqD0.getOrderId());
       } catch(Exception exc) {
         exc.printStackTrace();
         return;
       }
       String subj = "Order "+oD.getOrderNum()+" has been stopped";
       String message = subj;
       for(int ii=0; ii<pWorkflowQueueDV.size(); ii++) {
         WorkflowQueueData wqD = (WorkflowQueueData) pWorkflowQueueDV.get(ii);
         int ruleId = wqD.getWorkflowRuleId();
         if(ruleId<=0) {
           continue;
         }
         WorkflowRuleData wrD = null;
         try {
           wrD = WorkflowRuleDataAccess.select(pConn,ruleId);
           message += " by wokflwo rule "+wrD.getRuleTypeCd();

         } catch(Exception exc) {
           exc.printStackTrace();
           continue;
         }
         int userGroupId = wrD.getEmailGroupId();
         if(userGroupId<=0) {
           continue;
         }
         IdVector userIdV = new IdVector();
         DBCriteria dbc = new DBCriteria();
         dbc.addEqualTo(GroupAssocDataAccess.GROUP_ID,userGroupId);
         dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                       RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
         try {
           userIdV =
               GroupAssocDataAccess.selectIdOnly(pConn,GroupAssocDataAccess.USER_ID,dbc);
         }catch (Exception exc) {
           exc.printStackTrace();
           return;
         }

          for (Iterator iter=userIdV.iterator(); iter.hasNext();) {
            Integer userIdI = (Integer) iter.next();
            Email eMail = new Email();
            eMail.subject = subj;
            eMail.message = message;
            addEmailMessage(pEmailMessHM, userIdI, eMail);
          }
       }
    }
    //
    private void processOrderQueueEntries(String pEntryType, WorkflowQueueDataVector pWorkflowQueueDV, Connection pCon) {
        log("processOrderQueueEntries()=> Begin pEntryType=" + pEntryType);

        HashMap eMailMessageHM = new HashMap();

        if(pWorkflowQueueDV==null || pWorkflowQueueDV.size()==0){
            log("processOrderQueueEntries()=> End. pWorkflowQueueDV==null || pWorkflowQueueDV.size()==0");
            return;
        }

        WorkflowQueueData wqD0 = (WorkflowQueueData) pWorkflowQueueDV.get(0);
        OrderData oD;

        try {
            oD = OrderDataAccess.select(pCon, wqD0.getOrderId());
        } catch(Exception exc) {
            exc.printStackTrace();
            return;
        }

        if (RefCodeNames.WORKFLOW_RULE_ACTION.SEND_EMAIL.equals(pEntryType)) {
            pq_sendOrderEmail(eMailMessageHM, pCon, pWorkflowQueueDV);
        } else if (RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL.equals(pEntryType)) {
            pq_forwardOrder(eMailMessageHM, pCon, pWorkflowQueueDV);
        } else if (RefCodeNames.WORKFLOW_RULE_ACTION.STOP_ORDER.equals(pEntryType)) {
            pq_stopOrder(eMailMessageHM, pCon, pWorkflowQueueDV);
            logDebug("WORKFLOW result: " + pEntryType);
        } else if (RefCodeNames.WORKFLOW_RULE_ACTION.PENDING_REVIEW.equals(pEntryType)) {
        	log.info("Before calling pq_checkPendingOrder() method.");
            pq_checkPendingOrder(eMailMessageHM, pCon, pWorkflowQueueDV);
        	log.info("pq_checkPendingOrder() method was executed.");
        } else {
            logError("processQueueEntry, unknown queue entry " + pEntryType);
        }

        sendMessages(eMailMessageHM, pCon, oD);

        log("processOrderQueueEntries()=> End");
    }

    private void sendMessages(HashMap pEmailMessHM, Connection pCon, OrderData pOrder) {
      Set users = pEmailMessHM.keySet();
      User userBean = null;
      try {
        userBean = getAPIAccess().getUserAPI();
      } catch(Exception exc) {
        exc.printStackTrace();
        return;
      }
      HashSet sentMessHS = new HashSet();
      HashMap notSentMessHM = new HashMap();
      for(Iterator iter=users.iterator();iter.hasNext();) {
        Integer userIdI = (Integer) iter.next();
        String errorMess = null;
        String destEmail = null;
        if(userIdI.intValue()<=0) {
          errorMess = "No user configured";
        } else {
          UserData uD = null;
          try {
            UserInfoData uiD = getCachedUser(userBean, userIdI.intValue());
            uD = uiD.getUserData();
            destEmail = uiD.getEmailData().getEmailAddress();
          }catch(Exception exc) {
            exc.printStackTrace();
            continue;
          }
          if(!RefCodeNames.USER_STATUS_CD.ACTIVE.equals(uD.getUserStatusCd())) {
            errorMess = "User "+uD.getUserId()+" has "+uD.getUserStatusCd()+" status";
          }
          if(errorMess==null && (destEmail==null || destEmail.trim().length()<3)) {
            //errorMess = "User "+uD.getUserId()+" has no email address";
            // STJ-3928 users without email address should be silently ignored
              continue;
          }
        }
        LinkedList userMessages = (LinkedList) pEmailMessHM.get(userIdI);
        if(errorMess!=null) {
          for(Iterator iter1=userMessages.iterator(); iter1.hasNext();) {
            Email eMail = (Email) iter1.next();
            String message = eMail.message;
            LinkedList errors = (LinkedList) notSentMessHM.get(message);
            if(errors==null) {
              errors = new LinkedList();
              notSentMessHM.put(message,errors);
            }
            errors.add(errorMess);
          }
        } else {
          String subj = null;
          String complexMessage = null;
          FileAttach[] attachments = null;
          if(userMessages.size()==1) {
            Email eMail0 = (Email) userMessages.get(0);
            subj = eMail0.subject;
            attachments = eMail0.attachments;
          } else {
            subj = "Workflow message. Order Number: " + pOrder.getOrderNum()+
             " Location: " + pOrder.getOrderSiteName();
          }
          for(Iterator iter1=userMessages.iterator(); iter1.hasNext();) {
            Email eMail = (Email) iter1.next();
            String message = eMail.message;
            if(!sentMessHS.contains(message)) {
              sentMessHS.add(message);
            }
            if(complexMessage==null) {
              complexMessage = message;
            } else {
              complexMessage += "\n\n"+message;
            }
          }
          sendEmail(pCon, pOrder.getOrderId(), pOrder.getAccountId(), pOrder.getStoreId(), destEmail, subj,
                    complexMessage, userIdI.intValue(), attachments);

        }

      }
      //check not sent messages;
      String adminMess = null;
      if(!notSentMessHM.isEmpty()) {
        Set messages = notSentMessHM.keySet();
        for(Iterator iter=messages.iterator(); iter.hasNext();) {
          String message = (String) iter.next();
          if(sentMessHS.contains(message)) {
            continue;
          }
          if(adminMess==null) {
            adminMess = message;
          } else {
            adminMess += "\n\n"+message;
          }
          LinkedList errorMess = (LinkedList) notSentMessHM.get(message);
          adminMess+="\n\t\t - "+errorMess;
        }
        if(adminMess!=null) {
          String subj = "Not sent order messages. Order: "+pOrder.getOrderNum();
          sendAdminEmail(pCon,pOrder.getOrderId(),pOrder.getAccountId(),pOrder.getStoreId(),subj,adminMess);
        }
      }
    }

    public int processQueueEntries(String pEntryType)
    throws RemoteException
        {
        log("01262010_1: processQueueEntries(String)=> Begin pEntryType=" + pEntryType);

        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(WorkflowQueueDataAccess.SHORT_DESC, pEntryType);
            crit.addOrderBy(WorkflowQueueDataAccess.ORDER_ID);

            WorkflowQueueDataVector orderWqDV = null;
            if ( RefCodeNames.WORKFLOW_RULE_ACTION.PENDING_REVIEW.equals(pEntryType) ) {
                orderWqDV = WorkflowQueueDataAccess.select(conn, crit);
            } else {
                WorkflowQueueDataVector wqDV
                    = WorkflowQueueDataAccess.select(conn, crit);
                int orderId = -1;
                orderWqDV = new WorkflowQueueDataVector();
                for (int idx=0; idx < wqDV.size(); idx++) {
                    WorkflowQueueData wqD = (WorkflowQueueData) wqDV.get(idx);
                    if(orderId!=wqD.getOrderId() && idx!=0) {
                        break;
                    }
                    orderWqDV.add(wqD);
                }
            }
            int qtyToProc = orderWqDV.size();
            log("processQueueEntries => Found " + qtyToProc + " entries of type (" + pEntryType + ")");

            if(qtyToProc>0) {
                processOrderQueueEntries(pEntryType, orderWqDV, conn);

            }

            log("processQueueEntries => Processed " + qtyToProc + " entries of type (" + pEntryType + ")");
            return qtyToProc;

        } catch (Exception e) {
            logError("processQueueEntries: error: " + e);
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            if(conn!=null) {
                closeConnection(conn);
            }
        }
    }

    private void cleanQueue() {

        // Delete those queue entries which are no longer
        // needed because the order status has migrated or the order
        // has been in the system for more than one year.
        String cleanPendingSql = "delete FROM " +
            WorkflowQueueDataAccess.CLW_WORKFLOW_QUEUE +
            " WHERE " +
            WorkflowQueueDataAccess.ORDER_ID +
            " in ( select " + OrderDataAccess.ORDER_ID +
            " from " + OrderDataAccess.CLW_ORDER +
            " where ( ( " +
            OrderDataAccess.ORDER_STATUS_CD + " in ( '" +
             RefCodeNames.ORDER_STATUS_CD.ORDERED +
            "','" + RefCodeNames.ORDER_STATUS_CD.CANCELLED +
            "','" + RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED +
            "','" + RefCodeNames.ORDER_STATUS_CD.INVOICED +
            "','" + RefCodeNames.ORDER_STATUS_CD.REJECTED +
            "','" + RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION +
            "','" + RefCodeNames.ORDER_STATUS_CD.PENDING_DATE +
            "') and " + OrderDataAccess.ADD_DATE + " < sysdate - 1 ) " +
            " or (" + OrderDataAccess.ADD_DATE + " < sysdate - 365 ) ) and "
            + OrderDataAccess.CLW_ORDER + "." +
            OrderDataAccess.ORDER_ID + " = " +
            WorkflowQueueDataAccess.CLW_WORKFLOW_QUEUE +
            "." + WorkflowQueueDataAccess.ORDER_ID + " ) ";

        Connection conn = null;

        try {
            conn = getConnection();

            Statement stmt = conn.createStatement();
            log("cleanQueue()=> sql=" +  cleanPendingSql);
            stmt.executeQuery(cleanPendingSql);
            stmt.close();
        } catch (Exception e) {
            logError("cleanQueue: error: " + e);
        } finally {
            closeConnection(conn);
        }

        return;
    }

    int queueCt = 0, loopCt = 0;

    public String processQueueEntries()
    throws RemoteException
    {

        log("processQueueEntries()=> Begin");

        String retMsg = "";
        retMsg = "processQueueEntries, start";
        // Clean up orders which have been reviewed.
        cleanQueue();

        loopCt = 0;

        do {
            queueCt = 0;

            log("LS  1 processQueueEntries loopCt=" + loopCt + " queueCt=" + queueCt );
            // Check for orders pending approval which
            // have aged past the time allowed.
            int qty = processQueueEntries(RefCodeNames.WORKFLOW_RULE_ACTION.PENDING_REVIEW);
            retMsg += "\n " + loopCt + " "+
                RefCodeNames.WORKFLOW_RULE_ACTION.PENDING_REVIEW +" count=" + qty;
            queueCt += qty;

            //****************************************************************************************
            log("LS  2 processQueueEntries loopCt=" + loopCt + " queueCt=" + queueCt + " " + retMsg );

            // Forward orders.
            qty = processQueueEntries(RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL);
            retMsg += "\n " + RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL + " count=" + qty;
            queueCt += qty;

            //****************************************************************************************
            log("LS  3 processQueueEntries loopCt=" + loopCt +
                    " queueCt=" + queueCt + " " + retMsg );

            qty = processQueueEntries(RefCodeNames.WORKFLOW_RULE_ACTION.STOP_ORDER);
            retMsg += "\n " + RefCodeNames.WORKFLOW_RULE_ACTION.STOP_ORDER + " count=" + qty;
            queueCt += qty;
            //****************************************************************************************
            log("LS  4 processQueueEntries loopCt=" + loopCt + " queueCt=" + queueCt + " " + retMsg );

            // Send email notifications.
            qty = processQueueEntries(RefCodeNames.WORKFLOW_RULE_ACTION.SEND_EMAIL);
            retMsg += "\n " + RefCodeNames.WORKFLOW_RULE_ACTION.SEND_EMAIL + " count=" + qty;
            queueCt += qty;
            //****************************************************************************************
            log("LS  5 processQueueEntries loopCt=" + loopCt + " queueCt=" + queueCt + " " + retMsg );

            loopCt++;

        } while (queueCt > 0 && loopCt < 10);

        retMsg += "\n    processQueueEntries, done, processed " + queueCt ;

        log("processQueueEntries()=> LE loopCt=" + loopCt + " queueCt=" + queueCt );
        log("processQueueEntries()=> End");

        return retMsg;

    }

    /**
     *
     * @return IdVector
     * @throws RemoteException
     */
    public IdVector getOrdersSinceLastCheck() throws RemoteException {

        String lastmoddate = "";
        String lastoid = "";

        log("getOrdersSinceLastCheck() => Begin");

        try {
            PropertyService propBean = getAPIAccess().getPropertyServiceAPI();
            lastmoddate = propBean.getProperty(mLastUpdateModDate);
        } catch (Exception e) {
            lastmoddate = "";
        }

        try {
            PropertyService propBean = getAPIAccess().getPropertyServiceAPI();
            lastoid = propBean.getProperty(mLastUpdateOid);
        } catch (Exception e) {
            lastoid = "";
        }

        Connection con = null;

        try {
            logDebug("getOrdersSinceLastCheck 0");
            con = getConnection();

            DBCriteria dbc = new DBCriteria();
            String q = " select o1.order_id from clw_order o1 where ( ";

            if (lastmoddate == null || lastmoddate.length() == 0) {
                q += " o1." + OrderDataAccess.MOD_DATE + " >= sysdate - 1 ";
            } else {
                q += " o1." + OrderDataAccess.MOD_DATE + " >=  to_date('" +
                        lastmoddate + "', 'MM-dd-yyyy HH24:mi') ";
            }

            if (lastoid == null || lastoid.length() == 0) {
                q += " or o1.order_id > (select max(order_id) - 10 from clw_order) ";
            } else {
                q += "or o1." + OrderDataAccess.ORDER_ID + " >=  " + lastoid;
            }
/*  Excluded because sending Pending Approval Email moved to pipeline process : UpdateOrderState as SYNCH-ASYNCH type..
            q += " or o1." + OrderDataAccess.ORDER_STATUS_CD
                    + " = '"
                    + RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL
                    + "' ";
*/
            q += " ) and o1." + OrderDataAccess.ADD_DATE +
                    " >= sysdate - 10  ";

            logDebug(" getOrdersSinceLastCheck, q=" + q);
            dbc.addOneOf(OrderDataAccess.ORDER_ID, q);
            dbc.addOrderBy(OrderDataAccess.ORDER_ID);

            log("getOrdersSinceLastCheck() => End");

            return OrderDataAccess.selectIdOnly(con, dbc);

        } catch (Exception e) {
            logError(e.getMessage());
        } finally {
            closeConnection(con);
        }

        return null;
    }

    private String mLastUpdateModDate = "Workflow.email.last_run";
    private String mLastUpdateOid = "Workflow.email.last_oid";

    public void updateLastCheckStamp(java.util.Date pDateStamp, int pOrderId)
                              throws RemoteException {

        try {

            java.text.SimpleDateFormat datefmt = new java.text.SimpleDateFormat(
                                                         "MM-dd-yyyy H:mm");
            String starttime = datefmt.format(pDateStamp);
            PropertyService propBean = getAPIAccess().getPropertyServiceAPI();
            propBean.setProperty(mLastUpdateModDate, starttime);

            if (pOrderId > 0) {
                propBean.setProperty(mLastUpdateOid, String.valueOf(pOrderId));
            }

            logDebug("updateLastCheckStamp 1");
        } catch (Exception e) {
            logError(e.getMessage());
            e.printStackTrace();
        }
    }

    private CacheManager mCacheManager = new CacheManager(this.getClass().getName());
    private UserInfoData getCachedUser(User userBean, int pUserId)
        throws Exception {
	return mCacheManager.getCachedUser(userBean, pUserId);
    }

    public void sendOrderEmails(int pOrderId)
            throws Exception, RemoteException {

        User userBean = getAPIAccess().getUserAPI();
        Connection con = getConnection();
        String originalOrderNumber = null;

        logDebug("sendOrderEmails()=> Begin, pOrderId=" + pOrderId);

        try {
            OrderData od = OrderDataAccess.select(con, pOrderId);

            if (od.getAccountId() <= 0 || od.getSiteId() <= 0) {
                logError(" account or site missing for order: " + od);
                return;
            }

            OrderAddressData ad = OrderDAO.getShippingAddress(con, pOrderId);

            //determine if this order was a modification of a previous order
            boolean isModifiedOrder = false;
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderAssocDataAccess.ORDER2_ID, pOrderId);
            dbc.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD, RefCodeNames.ORDER_ASSOC_CD.REPLACED);
            dbc.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_STATUS_CD, RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
            OrderAssocDataVector oadv = OrderAssocDataAccess.select(con, dbc);
            if (oadv != null && oadv.size() > 0) {
            	OrderAssocData oad = (OrderAssocData) oadv.get(0);
            	int originalOrderId = oad.getOrder1Id();
                dbc = new DBCriteria();
                dbc.addEqualTo(OrderDataAccess.ORDER_ID,  originalOrderId);
                dbc.addEqualTo(OrderDataAccess.ACCOUNT_ID, od.getAccountId());
                dbc.addEqualTo(OrderDataAccess.SITE_ID,    od.getSiteId());

                OrderDataVector odv = OrderDataAccess.select(con, dbc);

                if (odv != null && odv.size() > 0) {
                	OrderData originalOrder = (OrderData)odv.get(0);
                    String originalOrderStatus = originalOrder.getOrderStatusCd();
                    isModifiedOrder = RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(originalOrderStatus);
                    if (isModifiedOrder) {
                    	originalOrderNumber = originalOrder.getOrderNum();
                    }
                }
            }

            // Determine the type of mail to send out based on order status.
            String ostatus = od.getOrderStatusCd();
            String subj = "--none--";
            String emailaction = "";
            String usub = "--none--";
            String templateType = null;

            if (ostatus.equals(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED) && isModifiedOrder) {

                // This order was modified.
                subj = " -- Order Modified. ";
                subj += " Location: " + od.getOrderSiteName();
                subj += ", Order Number: " + od.getOrderNum();
                subj += " -- ";
                emailaction = "send_email_revised";
                usub = UserInfoData.USER_GETS_EMAIL_ORDER_WAS_MODIFIED;
                templateType = RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE;

            } else if (ostatus.equals(RefCodeNames.ORDER_STATUS_CD.REJECTED)) {

                // Check for reject notification.
                subj = " -- Order Cancelled. ";
                subj += " Location: " + od.getOrderSiteName();
                subj += ", Order Number: " + od.getOrderNum();
                subj += " -- ";
                emailaction = "reject_order";
                usub = UserInfoData.USER_GETS_EMAIL_ORDER_WAS_REJECTED;
                templateType = RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE;

            } else if (ostatus.equals(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED)) {

                // Check for approved notification.
                subj = " -- Order Approved. ";
                subj += " Location: " + od.getOrderSiteName();
                subj += ", Order Number: " + od.getOrderNum();
                subj += " -- ";
                emailaction = "send_email";
                usub = UserInfoData.USER_GETS_EMAIL_ORDER_WAS_APPROVED;
                templateType = RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE;

            } else if (ostatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)) {
                return;

            } else {
                // nothing to do for this order.
                return;
            }
            
            //save the subject in case of a template generation exception
            String originalSubject = subj;

            logDebug("sendOrderEmails()=> Action=" + emailaction);

            //--------------------------------------------------------------------
            WorkflowMailReqByGenerator mreq = new WorkflowMailReqByGenerator(con, od,ad, emailaction, null);

            FileAttach[] fileAttach = mreq.getAttachment();
            String message = mreq.getMessage();
            if (mreq.getSubject()!=null){
              subj = mreq.getSubject();
              originalSubject = subj;
            }
            if (!Utility.isSet(message)){
              String error = "WorkflowBeen :: sendOrderEmails()---<probably Order ERROR>---No Email Message content was generated!";
              logError(error);
            }
           //-------------------------------------------------------------------

            Map<String, TemplateDataExtended> localeToTemplateMap = new HashMap();
        	Map templateObjects = null; 
        	//set the original order number if this is a modified order.
        	if (isModifiedOrder && Utility.isSet(originalOrderNumber)) {
        		templateObjects = TemplateUtilities.getDefaultOrderEmailObjectMap(od.getOrderId(), originalOrderNumber);
        	}
        	else {
        		templateObjects = TemplateUtilities.getDefaultOrderEmailObjectMap(od.getOrderId());
        	}
            UserDataVector udv = userBean.getAllActiveUsers(od.getSiteId(), User.ORDER_BY_ID);
            if(udv.size()>0){
                for (int uidx = 0; udv != null && uidx < udv.size(); uidx++) {
                    UserData ud = (UserData)udv.get(uidx);
                    UserInfoData u = getCachedUser(userBean, ud.getUserId());
                    if (u.subscribesTo(usub, od.getAccountId()) == false) {
                        continue;
                    }
                    String userEmailAddress = u.getEmailData().getEmailAddress();
                    if (!Utility.isSet(userEmailAddress)) {
                        continue;
                    }
                    logDebug(" User : " + u.getUserData() + " subscribes to:" + usub);

                    //if the default email generator is what is being used to generate the email (i.e. an actual
                    //class name for this email event has not been specified), and this is an email event for which
                    //templates are applicable (i.e. templateType has a non-empty value), generate the data for 
                    //the email message.
                    if (mreq.getIsDefaultGenerator() && Utility.isSet(templateType)) {
                		Map<String, String> emailData = TemplateUtilities.generateEmailData(templateType, od.getStoreId(), 
                				od.getAccountId(), od.getOrderId(), u.getUserData().getPrefLocaleCd(),
                				localeToTemplateMap, templateObjects, originalSubject, mreq.getMessage());
                		subj = emailData.get(Constants.TEMPLATE_OUTPUT_EMAIL_SUBJECT);
                		message = emailData.get(Constants.TEMPLATE_OUTPUT_EMAIL_BODY);
                    }
                    
                	sendEmail(
                        con,
                        pOrderId,
                        od.getAccountId(),
                        od.getStoreId(),
                        userEmailAddress,
                        subj,
                        message,
                        ud.getUserId(),
                        fileAttach);
                }
            } else {
                String error = "Site "+od.getSiteId()+" does not have active users to send email.\n\n"+
                        " Original message: \n"+message;
                logInfo (subj + "\n" + error);
            }
        } finally {
            closeConnection(con);
            logDebug("sendOrderEmails()=> End");
        }
    }

   public OrderPropertyData  sendPendingApprovalEmail(Connection con, OrderData pOrder, OrderAddressData pOrderAddress)
//   public void  sendPendingApprovalEmail(Connection con, OrderData pOrder, OrderAddressData pOrderAddress)
   throws Exception, RemoteException
   {
	   log.info("sendPendingApprovalEmail() --> Begin. ");
	   OrderPropertyData noEmailUserNote = null ;
	   
	   User userBean = getAPIAccess().getUserAPI();
       int orderId = pOrder.getOrderId();
       DBCriteria dbc = new DBCriteria();
       dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID,orderId);
       dbc.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
               RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
       dbc.addEqualTo(OrderPropertyDataAccess.SHORT_DESC,"Workflow Note");
       dbc.addIsNull(OrderPropertyDataAccess.APPROVE_DATE);
       dbc.addOrderBy(OrderPropertyDataAccess.ORDER_PROPERTY_ID);

       OrderPropertyDataVector orderPropertyDV =
       OrderPropertyDataAccess.select(con,dbc);
       if(orderPropertyDV.size()==0) {
           return noEmailUserNote;
       }
       Date ruleDate = null;
       IdVector ruleIdV = new IdVector();
       for(Iterator iter=orderPropertyDV.iterator(); iter.hasNext();) {
           OrderPropertyData opD = (OrderPropertyData) iter.next();
           if(ruleDate==null) ruleDate = opD.getAddDate();
           int ruleId = opD.getWorkflowRuleId();
           ruleIdV.add(new Integer(ruleId));
       }
       String ruleDateS = "";
       SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
       if(ruleDate!=null) ruleDateS = sdf.format(ruleDate);

       String subj = " -- Order approval notification. "+
        " Location: " + pOrder.getOrderSiteName() +
        ", Order Number: " + pOrder.getOrderNum() +
        " ("+ruleDateS+") -- ";
       String emailaction = "forward_for_approval";
       String usub = UserInfoData.USER_GETS_EMAIL_ORDER_NEEDS_APPROVAL;
       
       //save the subject in case of a template generation exception
       String originalSubject = subj;
       
        WorkflowMailReqByGenerator mreq = new WorkflowMailReqByGenerator(con, pOrder, pOrderAddress, emailaction, null);

        FileAttach[] fileAttach = mreq.getAttachment();
        String message = mreq.getMessage();
        if (mreq.getSubject()!=null){
          subj = mreq.getSubject();
          originalSubject = subj;
        }
        if (!Utility.isSet(message)){
          String msg = "WorkflowBeen :: sendPendingApprovalEmail()---<probably Order ERROR>---No Email Message content was generated!";
          logError(msg);
        }
       dbc = new DBCriteria();
       dbc.addOneOf(WorkflowRuleDataAccess.WORKFLOW_RULE_ID,ruleIdV);
       WorkflowRuleDataVector workflowRuleDV =
       WorkflowRuleDataAccess.select(con,dbc);
       IdVector emailGroupIdV = new IdVector();
       IdVector workflowRuleIdV = new IdVector();
       boolean defaultEmailFl = false;
       for(Iterator iter=workflowRuleDV.iterator(); iter.hasNext();) {
           WorkflowRuleData wrD = (WorkflowRuleData) iter.next();
           int emailGroupId = wrD.getEmailGroupId();
           if(emailGroupId>0) {
               emailGroupIdV.add(new Integer(emailGroupId));
               workflowRuleIdV.add(new Integer(wrD.getWorkflowRuleId()));
           } else {
               defaultEmailFl = true;
           }
       }
       IdVector userIdV = new IdVector();
       Map<String, TemplateDataExtended> localeToTemplateMap = new HashMap();
       //get the objects needed to generate the output
       Map templateObjects = TemplateUtilities.getDefaultOrderEmailObjectMap(pOrder.getOrderId());
       //add additional information to the order required by the templates.
       //first set the rule date on the order
       OrderDto orderDto = (OrderDto)templateObjects.get(Constants.TEMPLATE_EMAIL_MAP_KEY_ORDER);
       orderDto.setRuleDate(ruleDate);
       if(defaultEmailFl) {
           UserDataVector userDataV =
                   userBean.getAllActiveUsers(pOrder.getSiteId(), User.ORDER_BY_ID);
           for(Iterator iter=userDataV.iterator(); iter.hasNext();) {
               UserData uD = (UserData) iter.next();
               UserInfoData uiD = getCachedUser(userBean, uD.getUserId());
               String userEmailAddress = uiD.getEmailData().getEmailAddress();
               if (!Utility.isSet(userEmailAddress)) {
                   continue;
               }
               boolean subscrFl = uiD.subscribesTo(usub, pOrder.getAccountId());
               if (subscrFl) {
					//if the default email generator is what is being used to generate the email (i.e.
					//an actual class name for this email event has not been specified), then generate 
					//the data for the email message.
					if (mreq.getIsDefaultGenerator()) {
		                String templateType = RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE;
						Map<String, String> emailData = TemplateUtilities.generateEmailData(templateType, pOrder.getStoreId(), 
								pOrder.getAccountId(), pOrder.getOrderId(), uiD.getUserData().getPrefLocaleCd(),
								localeToTemplateMap, templateObjects, originalSubject, mreq.getMessage());
						subj = emailData.get(Constants.TEMPLATE_OUTPUT_EMAIL_SUBJECT);
						message = emailData.get(Constants.TEMPLATE_OUTPUT_EMAIL_BODY);
					}
					sendEmail(con, orderId, pOrder.getAccountId(), pOrder.getStoreId(),
                        userEmailAddress, subj,
                        message, uD.getUserId(), fileAttach,mreq.getIsStoreFromEmail());
					userIdV.add(new Integer(uD.getUserId()));
               }
           }
       }
	   log.info("sendPendingApprovalEmail() --> emailGroupIdV= "+ emailGroupIdV);
	   //STJ-6003: If the system cannot find a user in the Email Group for the user limit workflow rule,
	   //then the order will be put in a Pending Review status and no email notifications will be sent.
	   //The system will also log a order note  
	   if(emailGroupIdV.size()>0) {
    	   boolean isUserToSend = false;

/*           dbc = new DBCriteria();
           dbc.addOneOf(GroupAssocDataAccess.GROUP_ID, emailGroupIdV);
           dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                   RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
           String userIdReq =
                   GroupAssocDataAccess.getSqlSelectIdOnly(GroupAssocDataAccess.USER_ID, dbc);
           dbc = new DBCriteria();
           dbc.addOneOf(UserDataAccess.USER_ID,userIdReq);
           dbc.addEqualTo(UserDataAccess.USER_STATUS_CD,RefCodeNames.USER_STATUS_CD.ACTIVE);
           if(userIdV.size()>0) {
               dbc.addNotOneOf(UserDataAccess.USER_ID,userIdV);
           }
           userIdV = UserDataAccess.selectIdOnly(con,UserDataAccess.USER_ID,dbc);
*/
    	   IdVector groupUserIdV = userBean.getUsersForGroupSiteRights(pOrder.getSiteId(), emailGroupIdV, null);
    	   groupUserIdV.removeAll(userIdV); // remove users already processed for sending emails
    	   for(Iterator iter = groupUserIdV.iterator(); iter.hasNext();) {
               Integer userIdI = (Integer) iter.next();
               int userId = userIdI.intValue();
               UserInfoData uiD = getCachedUser(userBean, userId);
               String userEmailAddress = uiD.getEmailData().getEmailAddress();
               if (!Utility.isSet(userEmailAddress)) {
                   continue;
               }
				//if the default email generator is what is being used to generate the email (i.e.
				//an actual class name for this email event has not been specified), then generate 
				//the data for the email message.
				if (mreq.getIsDefaultGenerator()) {
				    String templateType = RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE;
					Map<String, String> emailData = TemplateUtilities.generateEmailData(templateType, pOrder.getStoreId(), 
							pOrder.getAccountId(), pOrder.getOrderId(), uiD.getUserData().getPrefLocaleCd(),
							localeToTemplateMap, templateObjects, originalSubject, mreq.getMessage());
					subj = emailData.get(Constants.TEMPLATE_OUTPUT_EMAIL_SUBJECT);
					message = emailData.get(Constants.TEMPLATE_OUTPUT_EMAIL_BODY);
				}
				sendEmail(con, orderId, pOrder.getAccountId(), pOrder.getStoreId(),
                    userEmailAddress, subj,
                    message, userId,fileAttach,mreq.getIsStoreFromEmail());
				isUserToSend = true;
    	   }
    	   if (!isUserToSend){
    		   noEmailUserNote = addNote(pOrder, getAPIAccess().getGroupAPI().getGroupsByIds(emailGroupIdV), workflowRuleIdV);
    	   }	
       }
	   log.info("sendPendingApprovalEmail() --> noEmailUserNote-- Begin. ");
       if (noEmailUserNote != null) {
    	   getAPIAccess().getOrderAPI().addNote(noEmailUserNote);
       }
       return noEmailUserNote;
   }

   public void  sendPendingApprovalEmail( OrderData pOrder, OrderAddressData pOrderAddress, HashSet pSendToUsers)
   throws Exception, RemoteException
   {
	   log.info("sendPendingApprovalEmail() --> Begin. pSendToUsers=" + pSendToUsers);
	   Connection con = null;
       try {
         con = getConnection();
         User userBean = getAPIAccess().getUserAPI();
         int orderId = pOrder.getOrderId();
         DBCriteria dbc = new DBCriteria();
         dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID, orderId);
         dbc.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                        RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
         dbc.addEqualTo(OrderPropertyDataAccess.SHORT_DESC, "Workflow Note");
         dbc.addIsNull(OrderPropertyDataAccess.APPROVE_DATE);
         dbc.addOrderBy(OrderPropertyDataAccess.ORDER_PROPERTY_ID);

         OrderPropertyDataVector orderPropertyDV = OrderPropertyDataAccess.select(con,dbc);
         if (orderPropertyDV.size() == 0) {
           return;
         }

         Date ruleDate = null;
         for(Iterator iter=orderPropertyDV.iterator(); iter.hasNext();) {
           OrderPropertyData opD = (OrderPropertyData) iter.next();
           if(ruleDate==null) ruleDate = opD.getAddDate();
         }
         String ruleDateS = "";
         SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
         if(ruleDate!=null) ruleDateS = sdf.format(ruleDate);

         String subj = " -- Order approval notification. "+
             " Location: " + pOrder.getOrderSiteName() +
             ", Order Number: " + pOrder.getOrderNum() +
             " ("+ruleDateS+") -- ";
         String emailaction = "forward_for_approval";
         
         //save the subject in case of a template generation exception
         String originalSubject = subj;
         
        WorkflowMailReqByGenerator mreq = new WorkflowMailReqByGenerator(con, pOrder, pOrderAddress, emailaction, null);

        FileAttach[] fileAttach = mreq.getAttachment();
        String message = mreq.getMessage();
        if (mreq.getSubject()!=null){
          subj = mreq.getSubject();
        }
        if (!Utility.isSet(message)){
          String msg = "WorkflowBeen :: sendPendingApprovalEmail()---<probably Order ERROR>---No Email Message content was generated!";
          logError(msg);
        }
       Map<String, TemplateDataExtended> localeToTemplateMap = new HashMap();
       Map templateObjects = TemplateUtilities.getDefaultOrderEmailObjectMap(pOrder.getOrderId());
       //add additional information to the order required by the templates.
       //first set the rule date on the order
       OrderDto orderDto = (OrderDto)templateObjects.get(Constants.TEMPLATE_EMAIL_MAP_KEY_ORDER);
       orderDto.setRuleDate(ruleDate);
       if(pSendToUsers!=null && !pSendToUsers.isEmpty()) { // send email to default users
          for (Iterator iter = pSendToUsers.iterator(); iter.hasNext(); ) {
           Integer userIdI = (Integer) iter.next();
           int userId = userIdI.intValue();
           UserInfoData uiD = getCachedUser(userBean, userId);
           String userEmailAddress = uiD.getEmailData().getEmailAddress();
           if (!Utility.isSet(userEmailAddress)) {
               continue;
           }
           	//if the default email generator is what is being used to generate the email (i.e.
			//an actual class name for this email event has not been specified), then generate 
			//the data for the email message.
			if (mreq.getIsDefaultGenerator()) {
			    String templateType = RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE;
				Map<String, String> emailData = TemplateUtilities.generateEmailData(templateType, pOrder.getStoreId(), 
						pOrder.getAccountId(), pOrder.getOrderId(), uiD.getUserData().getPrefLocaleCd(),
						localeToTemplateMap, templateObjects, originalSubject, mreq.getMessage());
				subj = emailData.get(Constants.TEMPLATE_OUTPUT_EMAIL_SUBJECT);
				message = emailData.get(Constants.TEMPLATE_OUTPUT_EMAIL_BODY);
			}
			sendEmail(con, orderId, pOrder.getAccountId(), pOrder.getStoreId(),
                 userEmailAddress, subj,
                 message, userId, fileAttach,mreq.getIsStoreFromEmail());
         }
       }
     }
     catch (Exception e) {
            String msg = "[WorkflowBean] sendPendingApprovalEmail error: " + e;
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            closeConnection(con);
     }

   }

   private void deleteWorkflowQueueEntry(int pWorkflowQueueId)
                                   throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();
            WorkflowQueueDataAccess.remove(conn, pWorkflowQueueId);
        } catch (Exception e) {

            String msg = "deleteWorkflowQueueEntry error: " + e;
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            closeConnection(conn);
        }
    }


    public void deleteWorkflowQueueEntryByOrderId(int orderId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addEqualTo(WorkflowQueueDataAccess.ORDER_ID, orderId);
            WorkflowQueueDataAccess.remove(conn, dbCrit);
        } catch (Exception e) {
            String msg = "deleteWorkflowQueueEntryByOrderId error: " + e;
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            closeConnection(conn);
        }
    }
    
    
    public void delWorkflowQueueEntryByOrderIdAndShortDesc(int orderId,  String shortDesc) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addEqualTo(WorkflowQueueDataAccess.ORDER_ID, orderId);
            dbCrit.addEqualTo(WorkflowQueueDataAccess.SHORT_DESC, shortDesc);
            WorkflowQueueDataAccess.remove(conn, dbCrit);
        } catch (Exception e) {
            String msg = "delWorkflowQueueEntryByOrderIdAndShortDesc error: " + e;
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            closeConnection(conn);
        }
    }

    
    public OrderSiteSummaryDataVector listPendingOrders2(int pAccountId,
                                                         int pUserId)
                                                  throws RemoteException {

        Connection conn = null;
        OrderSiteSummaryDataVector ossdv = new OrderSiteSummaryDataVector();

        try {
            conn = getConnection();

            // Find the orders pending in the queue.
            DBCriteria crit = new DBCriteria();
            UserData ud = UserDataAccess.select(conn, pUserId);
            String idt = "";

            if (ud != null) {
                idt = ud.getUserTypeCd();
            }

            String usql = WorkflowQueueDataAccess.BUS_ENTITY_ID + " in ( ";

            if (idt.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER) ||
                idt.equals(RefCodeNames.USER_TYPE_CD.MSB)) {
                usql += " select bus_entity_id from " + " clw_user_assoc where user_id = " + pUserId;
            } else {
                usql += " select bus_entity1_id from " + " clw_bus_entity_assoc where bus_entity2_id = " + pAccountId;
            }

            usql += " ) ";

            String osql = WorkflowQueueDataAccess.ORDER_ID +
                          " IN ( SELECT " + OrderDataAccess.ORDER_ID +
                          " from " + OrderDataAccess.CLW_ORDER + " where " +
                          OrderDataAccess.ORDER_STATUS_CD + " IN ("+
                          "'" + RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL + "',"+
                          "'" + RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION + "',"+
                          "'" + RefCodeNames.ORDER_STATUS_CD.PENDING_DATE + "'"+
                          ")"+
                          " and " + OrderDataAccess.CLW_ORDER + "." +
                          OrderDataAccess.ORDER_ID + " = " +
                          WorkflowQueueDataAccess.CLW_WORKFLOW_QUEUE + "." +
                          WorkflowQueueDataAccess.ORDER_ID + " ) ";
            crit.addCondition(osql);
            crit.addCondition(usql);

            IdVector orderids = WorkflowQueueDataAccess.selectIdOnly(conn,
                                                                     WorkflowQueueDataAccess.ORDER_ID,
                                                                     crit);

            // Get the Order information.
            crit = new DBCriteria();
            crit.addOneOf(OrderDataAccess.ORDER_ID, orderids);
            crit.addOrderBy(OrderDataAccess.ORDER_ID);

            OrderDataVector odv = OrderDataAccess.select(conn, crit);

            for (int idx = 0; idx < odv.size(); idx++) {
                OrderData o1 = (OrderData)odv.get(idx);
                ossdv.add(OrderDAO.populateOrderSiteSummryData(conn,o1));
            }
        } catch (Exception e) {

            String msg = "listPendingOrders error: " + e;
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            closeConnection(conn);
        }

        return ossdv;
    }

    public OrderSiteSummaryDataVector listPendingOrders(int pUserId)
                                                 throws RemoteException
    {

        Connection conn = null;
        OrderSiteSummaryDataVector ossdv = new OrderSiteSummaryDataVector();

        try {
            conn = getConnection();

            // Find the orders pending in the queue.
            DBCriteria crit = new DBCriteria();

            LinkedList orderStatusList = new LinkedList();
            orderStatusList.add(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
            orderStatusList.add(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION);
            orderStatusList.add(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE);
            // Get the Order information.
            crit = new DBCriteria();
            crit.addJoinTableOneOf(OrderDataAccess.CLW_ORDER,OrderDataAccess.ORDER_STATUS_CD, orderStatusList);
            crit.addJoinTableEqualTo(UserAssocDataAccess.CLW_USER_ASSOC,UserAssocDataAccess.USER_ASSOC_CD,RefCodeNames.USER_ASSOC_CD.SITE);
            crit.addJoinTableEqualTo(UserAssocDataAccess.CLW_USER_ASSOC,UserAssocDataAccess.USER_ID,pUserId);
            crit.addJoinCondition(UserAssocDataAccess.CLW_USER_ASSOC,UserAssocDataAccess.BUS_ENTITY_ID,OrderDataAccess.CLW_ORDER, OrderDataAccess.SITE_ID);
            crit.addJoinTableOrderBy(OrderDataAccess.CLW_ORDER,OrderDataAccess.ORDER_SITE_NAME);
            crit.addDataAccessForJoin(new OrderDataAccess());
            List jodv = JoinDataAccess.select(conn, crit);

            for (int idx = 0; idx < jodv.size(); idx++) {
                OrderData o1 = (OrderData)((List)jodv.get(idx)).get(0);
                ossdv.add(OrderDAO.populateOrderSiteSummryData(conn, o1));
            }


        } catch (Exception e) {

            String msg = "listPendingOrders2 error: " + e;
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            closeConnection(conn);
        }

        // Return all the orders found for this account.
        return ossdv;
    }




    public boolean applyStoreWorkflow(int pStoreId, int pOrderId,
                                      OrderItemDataVector oiv)
                               throws RemoteException {

        // Look up the CWSKU workflow,  This is currently the only
        // workflow defined at the store level.
        WorkflowDataVector wdv = getWorkflowCollectionByEntity(pStoreId);
        WorkflowData wd = null;

        for (int idx = 0; idx < wdv.size(); idx++) {
            wd = (WorkflowData)wdv.get(idx);

            String v = wd.getWorkflowTypeCd();

            if (v.equals(RefCodeNames.WORKFLOW_TYPE_CD.CWSKU)) {
                v = wd.getWorkflowStatusCd();

                if (v.equals(RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE)) {

                    break;
                }
            }
        }

        if (null == wd) {

            // No active workflow.
            logDebug("applyStoreWorkflow: no workflow found for store: " +
                     pStoreId);

            return true;
        }

        logDebug("applyStoreWorkflow: " + wd);

        Connection pConn = null;

        try {
            pConn = getConnection();

            // If any order item contains SKUs
            // meeting the workflow criteria
            // then return a failure (false).
            boolean retf = true;
            logDebug("applyStoreWorkflow: oiv.size=" + oiv.size());

            for (int idx = 0; idx < oiv.size(); idx++) {

                OrderItemData oid = (OrderItemData)oiv.get(idx);
                String distsku = oid.getDistItemSkuNum();
                int oursku = oid.getItemSkuNum();
                logDebug("1 distsku=" + distsku);

                if (distsku == null || distsku.trim().length() == 0) {

                    // No distributor SKU set.
                    // Set this order to a pending approval state.
                    retf = false;

                    String msg = "Missing Distributor SKU for cw.sku: " +
                                 oursku;
                    addWorkflowOrderNote2(pConn, msg, pOrderId, oid);
                } else {
                    distsku = distsku.toLowerCase(Locale.US);
                    logDebug("2 distsku=" + distsku);

                    if (distsku.trim().indexOf("cw") == 0) {
                        logDebug("3 distsku=" + distsku);

                        // This is a temporary Cleanwise SKU.
                        // Set this order to a pending approval state.
                        retf = false;

                        String msg = "Temporary Distributor SKU for cw.sku: " +
                                     oursku;
                        addWorkflowOrderNote2(pConn, msg, pOrderId, oid);
                    }
                }
            }

            if (retf == false) {
                createQueueEntry(pOrderId, pStoreId,
                                 RefCodeNames.WORKFLOW_TYPE_CD.CWSKU,
                                 RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER,
                                 wd.getWorkflowId(), 0, "StoreWorkflow");
            }

            return retf;
        } catch (Exception e) {
            throw new RemoteException("applyStoreWorkflow: " +
                                      e.getMessage());
        } finally {
            closeConnection(pConn);
        }
    }

    private void addWorkflowOrderNote2(Connection pConn, String pMsg,
                                       int pOrderId,
                                       OrderItemData pOrderItemData) {
        log.info(pMsg + " on order: " + pOrderId);

        OrderPropertyData dbOrderProp = OrderPropertyData.createValue();
        dbOrderProp.setOrderPropertyTypeCd(
                RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        dbOrderProp.setShortDesc("CW Workflow Note");
        dbOrderProp.setValue(pMsg);
        dbOrderProp.setOrderPropertyStatusCd(
                RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        dbOrderProp.setOrderId(pOrderId);
	if ( pOrderItemData != null ) {
	    dbOrderProp.setOrderItemId(pOrderItemData.getOrderItemId());
	}
        dbOrderProp.setAddBy("CW Workflow");

        try {

            OrderPropertyDataAccess.insert(pConn, dbOrderProp);
        } catch (Exception e) {
            logError("addWorkflowOrderNote2: " + e);
        }
    }

    private  String fetchAccountName(Connection pConn, OrderData pOrderData) {

        try {
            return BusEntityDataAccess.select(pConn,pOrderData.getAccountId()).getShortDesc();
        } catch (Exception e) {
            logError("fetchAccountName: " + e);

            return "-- Acount Id:" + pOrderData.getAccountId();
        }
    }

    private  String fetchSiteName(Connection pConn, OrderData pOrderData) {

        try {
            return BusEntityDataAccess.select(pConn,pOrderData.getSiteId()).getShortDesc();
        } catch (Exception e) {
            logError("fetchSiteName: " + e);

            return "-- Site Id:" + pOrderData.getSiteId();
        }
    }

    //-----------------------------------------------------------------------------
    private void sendEmail(Connection pConn, int pOrderId, int pAccountId, int pStoreId,
                           String pToEmailAddress, String pSubject,String pMsg, int pUserId) {

      sendEmail(pConn,  pOrderId, pAccountId,  pStoreId, pToEmailAddress,  pSubject,
                           pMsg, pUserId, null);

    }

    private void sendEmail(Connection pConn, int pOrderId, int pAccountId, int pStoreId,
                           String pToEmailAddress, String pSubject,
                           String pMsg, int pUserId, FileAttach[] pAttachments) {

      sendEmail(pConn,  pOrderId, pAccountId,  pStoreId, pToEmailAddress,  pSubject,
                           pMsg, pUserId, pAttachments, false);
    }


    private void sendEmail(Connection pConn, int pOrderId, int pAccountId, int pStoreId,
                           String pToEmailAddress, String pSubject,
                           String pMsg, int pUserId, FileAttach[] pAttachments, boolean isStoreFromEmail) {

        APIAccess factory = null;
        EmailClient emailClientEjb = null;
        Account accountEjb = null;
        Store storeEjb = null;

        try {
            factory = APIAccess.getAPIAccess();
            emailClientEjb = factory.getEmailClientAPI();
            accountEjb = factory.getAccountAPI();
            storeEjb = factory.getStoreAPI();
        } catch (Exception exc) {

            String mess = "WorkflowBean.sendEmail. No API access";
            logError(mess);

            return;
        }

        try {

            boolean isSent = emailClientEjb.wasThisEmailSent(pSubject, pToEmailAddress);
            if (isSent == true) {
                logDebug("emailClientEjb.wasThisEmailSent TRUE for "
                        + " pSubject=" + pSubject
                        + " pToEmailAddress=" + pToEmailAddress);
                return;
            }


            String from = "";
            if (isStoreFromEmail) {
                from = storeEjb.getDefaultEmailForStoreUser(pUserId);
            } else {
                from = accountEjb.getDefaultEmail(pAccountId, pStoreId);
            }

			emailClientEjb.send(pToEmailAddress, from, null, pSubject, pMsg,
                                null, pOrderId,
                                RefCodeNames.EMAIL_TRACKING_CD.WORKFLOW,
                                0, pUserId,
                                "UNKNOWN", pAttachments);
        } catch (Exception exc) {

            String mess = "WorkflowBean.sendEmail: " + exc.getMessage();
            logError(mess);

            return;
        }

        OrderPropertyData note = OrderPropertyData.createValue();
        note.setOrderId(pOrderId);
        note.setOrderPropertyTypeCd(
                RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        note.setOrderPropertyStatusCd(
                RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);

        String shortDesc =
            "EmailId:" + pToEmailAddress
            + " Subject:" + pSubject;

        if (shortDesc.length() > 255)
            shortDesc = shortDesc.substring(0, 255);

        note.setShortDesc(shortDesc);


        if (pMsg!=null && pMsg.length() > 2000)
            pMsg = pMsg.substring(0, 2000);

        note.setValue(pMsg);
        note.setAddBy("Wkfl.100");

        try {
            OrderPropertyDataAccess.insert(pConn, note);
        } catch (Exception exc) {

            String mess =
                "WorkflowBean.sendEmail. Can't save email note. Error: " +
                 exc.getMessage();
            logError(mess);

            return;
        }
    }
    private void sendAdminEmail(Connection pConn, int pOrderId, int pAccountId, int pStoreId, String pSubject,
                           String pMsg)
    {
       try {
         APIAccess factory = APIAccess.getAPIAccess();
         EmailClient emailClientEjb = factory.getEmailClientAPI();
         String defEmailAddr = emailClientEjb.getDefaultEmailAddress();
         sendEmail(pConn,pOrderId,pAccountId, pStoreId,defEmailAddr,pSubject,pMsg,0);
       } catch (Exception exc) {
          exc.printStackTrace();
          return;
       }
       return;
    }


    private String getProperty(String pName, int storeId,Connection pConn) {
            PropertyUtil pru = new PropertyUtil(pConn);
            String retVal;
            try{
                 retVal = pru.fetchValueIgnoreMissing(0,storeId, pName);
                 if(retVal != null){
                     StringBuffer retValBuf = new StringBuffer(retVal);
                     Utility.replaceString(retValBuf, "\\n","\n");
                     retVal = retValBuf.toString();
                 }
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
            if(retVal == null){
                retVal = "";
            }
            return retVal;


    }

    protected BigDecimal calculateOrderTotal(Connection conn, OrderData orderData){
      OrderMetaDataVector orderMeta = OrderDAO.getOrderMetaDV(conn, orderData.getOrderId());
      BigDecimal orderDiscount = null;
      BigDecimal orderSmallOrderFee = null;
      BigDecimal orderFuelsurcharge = null;
      BigDecimal freightAmt = orderData.getTotalFreightCost();
      BigDecimal handlingAmt = orderData.getTotalMiscCost();
      BigDecimal rushOrderCharge = orderData.getTotalRushCharge();
      BigDecimal salesTax = orderData.getTotalTaxCost();
      BigDecimal subTotal = orderData.getTotalPrice();

      OrderMetaData mDiscount = getMetaObject(orderData, orderMeta, RefCodeNames.CHARGE_CD.DISCOUNT);
     if (mDiscount != null && Utility.isSet(mDiscount.getValue())) {
        orderDiscount = new BigDecimal(mDiscount.getValue());
      }
      OrderMetaData mSmallOrderFee = getMetaObject(orderData, orderMeta, RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);
      if (mSmallOrderFee != null && Utility.isSet(mSmallOrderFee.getValue())) {
          orderSmallOrderFee = new BigDecimal(mSmallOrderFee.getValue());
      }
      OrderMetaData mFuelsurcharge = getMetaObject(orderData, orderMeta, RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);
      if (mFuelsurcharge != null && Utility.isSet(mFuelsurcharge.getValue())) {
          orderFuelsurcharge = new BigDecimal(mFuelsurcharge.getValue());
      }

      BigDecimal orderTotal = (subTotal != null ) ? subTotal : new BigDecimal(0);

      orderTotal = orderTotal.add(orderDiscount!= null ? orderDiscount : new BigDecimal(0));
      orderTotal = orderTotal.add(orderFuelsurcharge!=null ? orderFuelsurcharge : new BigDecimal(0));
      orderTotal = orderTotal.add(orderSmallOrderFee!=null ? orderSmallOrderFee : new BigDecimal(0));
      orderTotal = orderTotal.add(freightAmt != null ? freightAmt : new BigDecimal(0));
      orderTotal = orderTotal.add(handlingAmt!= null ? handlingAmt : new BigDecimal(0));
      orderTotal = orderTotal.add(rushOrderCharge!= null ? rushOrderCharge : new BigDecimal(0));
      orderTotal = orderTotal.add(salesTax!= null ? salesTax : new BigDecimal(0));
      return orderTotal;
    }

    public OrderMetaData getMetaObject(OrderData orderData, OrderMetaDataVector orderMeta, String pName) {
         if (orderMeta == null || pName == null) return null;
         Date modDate = null;
         OrderMetaData retObject = null;
         for (int ii = 0; ii < orderMeta.size(); ii++) {
             OrderMetaData omD = (OrderMetaData) orderMeta.get(ii);
             String name = omD.getName();
             if (pName.equals(name)) {
                 if (modDate == null) {
                     modDate = orderData.getModDate();
                     retObject = omD;
                 } else {
                     Date mD = orderData.getModDate();
                     if (mD == null) continue;
                     if (modDate.before(mD)) {
                         modDate = mD;
                         retObject = omD;
                     }
                 }
             }
         }
         return retObject;
     }

    private class Email {
      public String subject = null;
      public String message = null;
      public  FileAttach[]  attachments =null;
    }

    private void log(String message) {
        logInfo(className + " :: " + message);
    }

    private class WorkflowMailReqByGenerator{
      private String osummary =null;
      private FileAttach[] fileAttach= null;
      private String subj=null;
      private boolean isStoreFromEmail = false;
      private boolean isDefaultGenerator = false;

      public WorkflowMailReqByGenerator( Connection pConn, OrderData pOrder, OrderAddressData pOrderAddress, String pEmailaction , String pSkuVal){
        try {
          init(pConn,  pOrder,  pOrderAddress,  pEmailaction ,  pSkuVal);
        }catch (Exception ex ){
          ex.getStackTrace();
        }
      }
      public boolean getIsDefaultGenerator() {
		return isDefaultGenerator;
	}
	public String getMessage(){
        return osummary;
      }
      public String getSubject(){
        return subj;
      }
      public FileAttach[] getAttachment(){
        return fileAttach;
      }
      public boolean getIsStoreFromEmail() {
          return isStoreFromEmail;
      }

      public void init(Connection pConn, OrderData pOrder, OrderAddressData pOrderAddress, String pEmailaction , String pSkuVal) throws Exception{
//for Kohls and Pollock need to use generator===============================
        APIAccess factory = new APIAccess();
        PropertyService propEjb = factory.getPropertyServiceAPI();
        String className = null;

        String propName = "";
        String orderStatus = pOrder.getOrderStatusCd();
        if (orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.REJECTED)){
          propName = RefCodeNames.PROPERTY_TYPE_CD.REJECT_ORDER_EMAIL_GENERATOR;
        } else if (orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)){
          propName = RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROV_EMAIL_GENERATOR;
        } else if (orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED)){
          propName = RefCodeNames.PROPERTY_TYPE_CD.NOTIFY_ORDER_EMAIL_GENERATOR;
        } else if (pEmailaction.equals("send_email") ){
          propName = RefCodeNames.PROPERTY_TYPE_CD.CONFIRM_ORDER_EMAIL_GENERATOR;
        }
        try {
          className = propEjb.getBusEntityProperty(pOrder.getAccountId(), propName );
        }
        catch (DataNotFoundException ex) {
          className = null;
        }
        catch (RemoteException ex) {
          ex.getStackTrace();
        }


        OrderNotificationGeneratorBase generator = (OrderNotificationGeneratorBase)Utility.getFileGenerator(className);
        if (generator != null) {
          //getting Order Item Info
          DBCriteria crit = new DBCriteria();
          crit.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrder.getOrderId());

          Order orderBean = getAPIAccess().getOrderAPI();
          OrderInfoDataView oidv = orderBean.getOrderInfoData(pOrder.getOrderId());

          crit = new DBCriteria();
          crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID, pOrder.getOrderId());
          crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);
          OrderPropertyDataVector opdV = OrderPropertyDataAccess.select(pConn, crit);
          if(opdV != null){
                  oidv.setInternalComments(opdV);
          }

          File tmp = Utility.createTempFileAttachment(generator.getFileExtension());
          generator.generate(oidv, tmp);
          fileAttach = Utility.getFileAttachment(tmp);
          osummary = generator.getTextMessage();
          subj = generator.getSubject(oidv);
          isStoreFromEmail = generator.isStoreFromEmail();
        }
        else {
        	isDefaultGenerator = true;
          WorkflowMailReq mreq = new WorkflowMailReq(pEmailaction, pOrder, pOrderAddress);
          mreq.mSkuVal=pSkuVal;
          osummary = makeOrderSummary(pConn, mreq);

        }

      }
    }
    
    public WorkflowWoQueueData createWorkflowWoQueueEntry(WorkflowWoQueueData workflowWOD)
                                                          throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return createWorkflowWoQueueEntry(conn, workflowWOD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private WorkflowWoQueueData createWorkflowWoQueueEntry(Connection conn,
                                                           WorkflowWoQueueData workflowWOD)
                                                           throws SQLException {
        WorkflowWoQueueData qD;
        qD = WorkflowWoQueueDataAccess.insert(conn, workflowWOD);
        
        return qD;
    }
    
    public WorkflowWoQueueDataVector getWorkflowWoQueueEntries(DBCriteria crit)
                                                               throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getWorkflowWoQueueEntries(conn, crit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private WorkflowWoQueueDataVector getWorkflowWoQueueEntries(Connection conn,
                                                                DBCriteria crit)
                                                                throws SQLException {
        WorkflowWoQueueDataVector qDV;
        qDV = WorkflowWoQueueDataAccess.select(conn, crit);
        
        return qDV;
    }


    public WorkflowQueueDataVector getWorkflowQueueEntries(int pRuleId) throws RemoteException {
        Connection conn = null;
        WorkflowQueueDataVector resV = new WorkflowQueueDataVector();
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(WorkflowQueueDataAccess.WORKFLOW_RULE_ID, pRuleId);
            resV = WorkflowQueueDataAccess.select(conn, dbc);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return resV;
    }

    public OrderDataVector getOrdersWorkflowQueueV(int pRuleId) throws RemoteException {
        Connection conn = null;
        OrderDataVector resV = new OrderDataVector();
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(WorkflowQueueDataAccess.WORKFLOW_RULE_ID, pRuleId);
            IdVector wqOrderIdV = WorkflowQueueDataAccess.selectIdOnly(conn, WorkflowQueueDataAccess.ORDER_ID, dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(OrderDataAccess.ORDER_ID, wqOrderIdV);
            List orderStatuses = new ArrayList();
            
            //check 4 criterias below: was here before Bug # 4849 fix.
            orderStatuses.add(RefCodeNames.ORDER_STATUS_CD.REJECTED);
            orderStatuses.add(RefCodeNames.ORDER_STATUS_CD.ORDERED); // is it correct (it is an itermediate Order state) ???
            orderStatuses.add(RefCodeNames.ORDER_STATUS_CD.SENT_TO_CUST_SYSTEM); // is it correct (it is an itermediate Order state) ???
            orderStatuses.add(RefCodeNames.ORDER_STATUS_CD.SHIPPED); // is it correct (it is an itermediate Order state) ???
            
            // Bug # 4849: additional RefCodeNames, which represent the "End State' of the Order: Begin
            orderStatuses.add(RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED);
            orderStatuses.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
            orderStatuses.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);
            orderStatuses.add(RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED);
            orderStatuses.add(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
            // additional RefCodeNames, which represent the "End State' of the Order: End

            dbc.addNotOneOf(OrderDataAccess.ORDER_STATUS_CD, orderStatuses);

            resV = OrderDataAccess.select(conn, dbc);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return resV;
    }

    private Map<String, String> generateEmailData(String templateType, 
    		int storeId, int accountId,	int orderId, int recipientId,
    		Map<String, TemplateDataExtended> localeToTemplateMap, Map templateObjects,
    		String defaultSubject, String defaultMessage) {
    	Map<String, String> returnValue = null;
    	try {
    		String recipientLocale = getAPIAccess().getUserAPI().getUser(recipientId).getPrefLocaleCd();
    		returnValue = TemplateUtilities.generateEmailData(templateType, storeId, accountId, orderId, recipientLocale, 
        		localeToTemplateMap, templateObjects, defaultSubject, defaultMessage);
    	}
    	catch (Exception e) {
	        log.error("Unable to retrieve all info for template usage in WorkflowBean.generateEmailData: " + e.getMessage());
    	}
    	return returnValue;
    }
    
   private OrderPropertyData  addNote(OrderData pOrderD, GroupDataVector pGroups, IdVector pWorkflowRuleIds) {
	   log.info("addNote() ===> BEGIN.");
	   OrderPropertyData opD = OrderPropertyData.createValue();
       int wfrId = ((Integer)pWorkflowRuleIds.get(0)).intValue();
       opD.setOrderId(pOrderD.getOrderId());
       opD.setWorkflowRuleId(wfrId);
       opD.setAddBy("System (user: SYNCH_ASYNCH)");
       opD.setModBy("System");
       opD.setAddDate(new Date());
       opD.setModDate(new Date());
       //opD.setApproveDate(new Date());
       opD.setShortDesc("Workflow Note");
       String messKey = "pipeline.message.noUserInEmailGroup";
       String groupName = (pGroups != null && pGroups.size() >0) ? ((GroupData)pGroups.get(0)).getShortDesc() : "";
       String mess = PipelineUtil.translateMessage(messKey, pOrderD.getLocaleCd(),
         groupName, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
         null, null, null, null, null, null);

       opD.setValue(mess);
       opD.setOrderPropertyStatusCd("ACTIVE");
       opD.setOrderPropertyTypeCd("Notes");
	   log.info("addNote() ===> END. orderId = "+ pOrderD.getOrderId() + ", mess=" + mess);
       return opD;
   }
}

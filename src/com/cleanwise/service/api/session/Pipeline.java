package com.cleanwise.service.api.session;

/**
 * Title:        Pipeline
 * Description:  Remote Interface for Pipeline Stateless Session Bean
 * Purpose:      Provides access to methods for managing and accessing the application pipeline.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */


import java.rmi.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Hashtable;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;

public interface Pipeline extends javax.ejb.EJBObject
{

  /**
   * Adds the pipeline information values to be used by the request.
   * @param pPipeline  the pipeline data.
   * @param request  the pipeline request data.
   * @return new PipelineRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public PipelineRequestData addPipeline(PipelineData pPipeline,
                PipelineRequestData request)
      throws RemoteException;

  /**
   * Updates the pipeline information values to be used by the request.
   * @param pUpdatePipelineData  the pipeline data.
   * @param pBusEntityId the business entity identifier.
   * @param pWorkflowId the workflow identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updatePipeline(PipelineData pUpdatePipelineData,
                int pBusEntityId, int pWorkflowId)
      throws RemoteException;

  /**
   * Adds the Pipeline routing information values to be used by the request.
   * @param pPipelineRouting  the Pipeline routing data.
   * @param request  the Pipeline routing request data.
   * @return new PipelineRoutingRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public PipelineRoutingRequestData addPipelineRouting(PipelineRoutingData pPipelineRouting,
                PipelineRoutingRequestData request)
      throws RemoteException;

  /**
   * Updates the pipeline routing information values to be used by the request.
   * @param pUpdatePipelineRoutingData  the pipeline routing data.
   * @param pBusEntityId the business entity identifier.
   * @param pWorkflowId the Workflow identifier.
   * @param pWorkflowItemId the Workflow item identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updatePipelineRouting(PipelineRoutingData pUpdatePipelineRoutingData,
                int pBusEntityId, int pWorkflowId, int pWorkflowItemId)
      throws RemoteException;

  /**
   * Adds the pipeline rule information values to be used by the request.
   * @param pPipelineRule  the pipeline Rule data.
   * @param request  the pipeline Rule request data.
   * @return new PipelineRuleRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public PipelineRuleRequestData addPipelineRule(PipelineRuleData pPipelineRule,
                PipelineRuleRequestData request)
      throws RemoteException;

  /**
   * Updates the pipeline Rule information values to be used by the request.
   * @param pUpdatePipelineData  the pipeline data.
   * @param pPipelineId the pipeline identifier.
   * @param pPipelineRuleId the pipeline rule identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updatePipelineRule(PipelineRuleData pUpdatePipelineRuleData,
                int pPipelineId, int pPipeLineRuleId)
      throws RemoteException;

  /**
   * Adds the pipeline item information values to be used by the request.
   * @param pPipelineItem  the pipeline item data.
   * @param request  the pipeline item request data.
   * @return new PipelineItemRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public PipelineItemRequestData addPipelineItem(PipelineItemData pPipelineItem,
                PipelineItemRequestData request)
      throws RemoteException;

  /**
   * Creates the pipeline information values to be used by the request.
   * @param pData  the pipeline data.
   * @return PipelineData
   * @throws            RemoteException Required by EJB 1.0
   */
  public PipelineData createPipeline(PipelineData pData)
      throws RemoteException;

  /**
   * Clears the shopping cart information values to be used by the request.
   * @param pCart  the shopping cart data.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void clearCart(ShoppingCartData pCart)
      throws RemoteException;

  /**
   * Gets the pipeline vector information values to be used by the request.
   * @param pBusEntityId the business entity identifier.
   * @return PipelineDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public PipelineDataVector getPipelinesCollection(int pBusEntityId)
      throws RemoteException;

  /**
     *  Gets the pipeline vector information values to be used by the request.
     *
     *@param  pPipelineTypeCd   pipeline type code.
     *@return                   PipelineDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public PipelineDataVector getPipelinesCollection(String pPipelineTypeCd)
    throws RemoteException;

  /**
   * Gets the pipeline information values to be used by the request.
   * @param pPipelineId the pipeline identifier.
   * @param pBusEntityId the business entity identifier.
   * @return PipelineData
   * @throws            RemoteException Required by EJB 1.0
   */
  public PipelineData getPipeline(int pPipelineId, int pBusEntityId)
      throws RemoteException;

  /**
   * Gets the pipeline routing vector information values to be used by the request.
   * @param pPipeLineId the pipeline identifier.
   * @return PipelineRoutingDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public PipelineRoutingDataVector getPipelineRoutingsCollection(int pPipelineId)
      throws RemoteException;

  /**
   * Gets the pipeline routing information values to be used by the request.
   * @param pPipeLineId the pipeline identifier.
   * @param pPipeLineRoutingId the pipeline routing identifier.
   * @return PipelineRoutingData
   * @throws            RemoteException Required by EJB 1.0
   */
  public PipelineRoutingData getPipelineRouting(int pPipelineId,
            int pPipelineRoutingId)
      throws RemoteException;

  /**
   * Gets the pipeline rule vector information values to be used by the request.
   * @param pPipeLineId the pipeline identifier.
   * @return PipelineRuleDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public PipelineRuleDataVector getPipelineRulesCollection(int pPipelineId)
      throws RemoteException;

  /**
   * Gets the pipeline rule information values to be used by the request.
   * @param pPipeLineId the pipeline identifier.
   * @param pPipeLineRuleId the pipeline rule identifier.
   * @return PipelineRuleData
   * @throws            RemoteException Required by EJB 1.0
   */
  public PipelineRuleData getPipelineRule(int pPipelineId,
            int pPipelineRuleId)
      throws RemoteException;
  
  /**
   * Removes the pipeline item information values to be used by the request.
   * @param pPipeLine the pipeline data.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void removePipeline(PipelineData pPipeline)
      throws RemoteException;
  
  /**
   * Clears the pipeline information values to be used by the request.
   * @param pPipeLine the pipeline data.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void clearPipeline(PipelineData pPipeline)
      throws RemoteException;
  
  /**
   * Saves the pipeline information values to be used by the request.
   * @param pPipeLine the pipeline data.
   * @param pPipeLineShortDesc the pipeline short description.
   * @return the updated PipelineData
   * @throws            RemoteException Required by EJB 1.0
   */
  public PipelineData savePipeline(PipelineData pPipeline,
            String pUserName)
      throws RemoteException;
    
    /**
     *Processes orders defined by order status  through the  piepline defined by it's type
     * @parameter pOrderType 
     * @parameter pPipelineType
     *@returns the number of orders remainging to be processed.  This method will only process a portion of the
     *  orders given the time it takes to process orders timeouts would happen otherwise
     *@throws RemoteException
     */
    public int processPipeline(String pOrderType, String pPipelineType)
    throws RemoteException;
    
    /**
     *Returns the order routing data for the supplied zip code and account id.
     *
     *@param pPostalCode the postal code
     *@param pAccountId the account id
     *@returns the most specific matching OrderRoutingData
     *@throws RemoteException if an error occurs
     *@throws DataNotFoundException if there were no matches
     */
    public OrderRoutingDescView getOrderRoutingDescForPostalCode(String pPostalCode,int pAccountId)
    throws RemoteException,DataNotFoundException;
    
    
    /*
     * Processes pipeline for the order
     * @param pOrderData order to be processed
     * @param pPipelineType type of the pipeline
     * @Throws RemoteException
     */
    public void
         processPipeline(OrderData pOrderData, String pPipelineType)
    throws RemoteException;

    /*
     * Processes unfinished pipeline for the order
     * @param pOrderData order to be processed
     * @Throws RemoteException
     */
    public void resumePipeline(OrderData pOrderData)
    throws RemoteException;

      
    /**
     *Process the distributor invoice pipeline.  This is currently a very rudimentary implementation of a pipline.
     */
    public void processDistInvoicePipeline()throws RemoteException;

    public String processDistInvoicePipeline(int pInvoiceId) throws RemoteException;
    /**
     *Process the distributor invoice pipeline.  This is currently a very rudimentary implementation of a pipline.
     */
    public void processSelfStoreDistInvPipeline()throws RemoteException;
     /*
     * Processes pipeline for the multi order
     * @param pParametrs parameters of start
     * @param pPipelineType type of the pipeline
     * @Throws RemoteException
     */
    public void processPipeline(Hashtable pParametrs, String pPipelineType) throws RemoteException;


    /**
     * Processes pipeline for the request of invoice
     * @throws RemoteException if an errors
     */
     public void  processInvoiceRequest(InvoiceRequestData pInvoiceReq,int pTradinPartnerId) throws RemoteException;

     public List processCheckoutRequest(OrderData pOrderData, OrderItemDataVector oiDV, String userRoleCd, String freightType)
     		throws RemoteException;

}

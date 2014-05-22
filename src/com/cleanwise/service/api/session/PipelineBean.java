package com.cleanwise.service.api.session;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.pipeline.*;
import com.cleanwise.service.api.process.operations.AppCmdOperations;

import java.rmi.*;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;


import javax.ejb.*;


/**
 *  Description of the Class
 *
 *@author     dvieira
 *@created    May 27, 2003
 */
public class PipelineBean extends UtilityServicesAPI {

    private static final String className="PipelineBean";

    /**
     */
    public PipelineBean() { }


    /**
     *@exception  CreateException  Description of the Exception
     *@exception  RemoteException  Description of the Exception
     */
    public void ejbCreate()
    throws CreateException, RemoteException {
    }


    /**
     *  Adds the pipeline information values to be used by the request.
     *
     *@param  pPipeline         the pipeline data.
     *@param  request           the pipeline request data.
     *@return                   new PipelineRequestData()
     *@throws  RemoteException  Required by EJB 1.0
     */
    public PipelineRequestData addPipeline(PipelineData pPipeline,
    PipelineRequestData request)
    throws RemoteException {

        return new PipelineRequestData();
    }


    /**
     *  Updates the pipeline information values to be used by the request.
     *
     *@param  pUpdatePipelineData  the pipeline data.
     *@param  pBusEntityId         the business entity identifier.
     *@param  pWorkflowId          the workflow identifier.
     *@throws  RemoteException     Required by EJB 1.0
     */
    public void updatePipeline(PipelineData pUpdatePipelineData,
    int pBusEntityId, int pWorkflowId)
    throws RemoteException {
    }


    /**
     *  Adds the Pipeline routing information values to be used by the request.
     *
     *@param  pPipelineRouting  the Pipeline routing data.
     *@param  request           the Pipeline routing request data.
     *@return                   new PipelineRoutingRequestData()
     *@throws  RemoteException  Required by EJB 1.0
     */
    public PipelineRoutingRequestData addPipelineRouting(PipelineRoutingData pPipelineRouting,
    PipelineRoutingRequestData request)
    throws RemoteException {

        return new PipelineRoutingRequestData();
    }


    /**
     *  Updates the pipeline routing information values to be used by the
     *  request.
     *
     *@param  pUpdatePipelineRoutingData  the pipeline routing data.
     *@param  pBusEntityId                the business entity identifier.
     *@param  pWorkflowId                 the Workflow identifier.
     *@param  pWorkflowItemId             the Workflow item identifier.
     *@throws  RemoteException            Required by EJB 1.0
     */
    public void updatePipelineRouting(PipelineRoutingData pUpdatePipelineRoutingData,
    int pBusEntityId, int pWorkflowId,
    int pWorkflowItemId)
    throws RemoteException {
    }


    /**
     *  Adds the pipeline rule information values to be used by the request.
     *
     *@param  pPipelineRule     the pipeline Rule data.
     *@param  request           the pipeline Rule request data.
     *@return                   new PipelineRuleRequestData()
     *@throws  RemoteException  Required by EJB 1.0
     */
    public PipelineRuleRequestData addPipelineRule(PipelineRuleData pPipelineRule,
    PipelineRuleRequestData request)
    throws RemoteException {

        return new PipelineRuleRequestData();
    }


    /**
     *  Updates the pipeline Rule information values to be used by the request.
     *
     *@param  pPipelineId              the pipeline identifier.
     *@param  pUpdatePipelineRuleData  Description of the Parameter
     *@param  pPipeLineRuleId          Description of the Parameter
     *@throws  RemoteException         Required by EJB 1.0
     */
    public void updatePipelineRule(PipelineRuleData pUpdatePipelineRuleData,
    int pPipelineId, int pPipeLineRuleId)
    throws RemoteException {
    }


    /**
     *  Adds the pipeline item information values to be used by the request.
     *
     *@param  pPipelineItem     the pipeline item data.
     *@param  request           the pipeline item request data.
     *@return                   new PipelineItemRequestData()
     *@throws  RemoteException  Required by EJB 1.0
     */
    public PipelineItemRequestData addPipelineItem(PipelineItemData pPipelineItem,
    PipelineItemRequestData request)
    throws RemoteException {

        return new PipelineItemRequestData();
    }


    /**
     *  Creates the pipeline information values to be used by the request.
     *
     *@param  pData             the pipeline data.
     *@return                   PipelineData
     *@throws  RemoteException  Required by EJB 1.0
     */
    public PipelineData createPipeline(PipelineData pData)
    throws RemoteException {

        return PipelineData.createValue();
    }


    /**
     *  Clears the shopping cart information values to be used by the request.
     *
     *@param  pCart             the shopping cart data.
     *@throws  RemoteException  Required by EJB 1.0
     */
    public void clearCart(ShoppingCartData pCart)
    throws RemoteException {
    }


    /**
     *  Gets the pipeline vector information values to be used by the request.
     *
     *@param  pBusEntityId      the business entity identifier.
     *@return                   PipelineDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public PipelineDataVector getPipelinesCollection(int pBusEntityId)
    throws RemoteException {

        Connection con = null;

        try {
            con = getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(PipelineDataAccess.BUS_ENTITY_ID, pBusEntityId);

            return PipelineDataAccess.select(con, dbc);
        } catch (Exception e) {
            logError("getPipelinesCollection error: " + e);
        } finally {
            closeConnection(con);
        }

        return new PipelineDataVector();
    }

    /**
     *  Gets the pipeline vector information values to be used by the request.
     *
     *@param  pPipelineTypeCd   pipeline type code.
     *@return                   PipelineDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public PipelineDataVector getPipelinesCollection(String pPipelineTypeCd)
    throws RemoteException {

        Connection con = null;
        PipelineDataVector pipelineDV = null;
        try {
          con = getConnection();
          DBCriteria dbc = new DBCriteria();
          if(Utility.isSet(pPipelineTypeCd)) {
            dbc.addEqualTo(PipelineDataAccess.PIPELINE_TYPE_CD,pPipelineTypeCd);
          } else {
            dbc.addCondition("1=1");
          }
          dbc.addOrderBy(PipelineDataAccess.PIPELINE_TYPE_CD);
          dbc.addOrderBy(PipelineDataAccess.PIPELINE_ORDER);
          pipelineDV = PipelineDataAccess.select(con, dbc);
          return pipelineDV;

        } catch (Exception e) {
           e.printStackTrace();
           logError("getPipelinesCollection error: " + e);
           throw new RemoteException(e.getMessage());
        } finally {
          closeConnection(con);
        }
    }

    /**
     *  Gets the pipeline information values to be used by the request.
     *
     *@param  pPipelineId       the pipeline identifier.
     *@param  pBusEntityId      the business entity identifier.
     *@return                   PipelineData
     *@throws  RemoteException  Required by EJB 1.0
     */
    public PipelineData getPipeline(int pPipelineId, int pBusEntityId)
    throws RemoteException {

        return PipelineData.createValue();
    }


    /**
     *  Gets the pipeline routing vector information values to be used by the
     *  request.
     *
     *@param  pPipelineId       Description of the Parameter
     *@return                   PipelineRoutingDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public PipelineRoutingDataVector getPipelineRoutingsCollection(int pPipelineId)
    throws RemoteException {

        return new PipelineRoutingDataVector();
    }


    /**
     *  Gets the pipeline routing information values to be used by the request.
     *
     *@param  pPipelineId         Description of the Parameter
     *@param  pPipelineRoutingId  Description of the Parameter
     *@return                     PipelineRoutingData
     *@throws  RemoteException    Required by EJB 1.0
     */
    public PipelineRoutingData getPipelineRouting(int pPipelineId,
    int pPipelineRoutingId)
    throws RemoteException {

        return PipelineRoutingData.createValue();
    }


    /**
     *  Gets the pipeline rule vector information values to be used by the
     *  request.
     *
     *@param  pPipelineId       Description of the Parameter
     *@return                   PipelineRuleDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public PipelineRuleDataVector getPipelineRulesCollection(int pPipelineId)
    throws RemoteException {

        return new PipelineRuleDataVector();
    }


    /**
     *  Gets the pipeline rule information values to be used by the request.
     *
     *@param  pPipelineId       Description of the Parameter
     *@param  pPipelineRuleId   Description of the Parameter
     *@return                   PipelineRuleData
     *@throws  RemoteException  Required by EJB 1.0
     */
    public PipelineRuleData getPipelineRule(int pPipelineId,
    int pPipelineRuleId)
    throws RemoteException {

        return new PipelineRuleData();
    }


    /**
     *  Removes the pipeline item information values to be used by the request.
     *
     *@param  pPipeline         Description of the Parameter
     *@throws  RemoteException  Required by EJB 1.0
     */
    public void removePipeline(PipelineData pPipeline)
    throws RemoteException {
      Connection conn = null;
      try {
        conn = getConnection();
        int pipelineId = pPipeline.getPipelineId();
        if(pipelineId!=0) {
          PipelineDataAccess.remove(conn,pipelineId);
        }
      } catch (Exception exc) {
           exc.printStackTrace();
           throw new RemoteException(exc.getMessage());
      }finally {
         if(conn!=null) {
           closeConnection(conn);
         }
      }
    }


    /**
     *  Clears the pipeline information values to be used by the request.
     *
     *@param  pPipeline         Description of the Parameter
     *@throws  RemoteException  Required by EJB 1.0
     */
    public void clearPipeline(PipelineData pPipeline)
    throws RemoteException {
    }


    /**
     *  Saves the pipeline information values to be used by the request.
     *
     *@param  pPipeline           Description of the Parameter
     *@param  pUserName           Description of the Parameter
     *@return                     the updated PipelineData
     *@throws  RemoteException    Required by EJB 1.0
     */
    public PipelineData savePipeline(PipelineData pPipeline, String pUserName)
    throws RemoteException {

        Connection con = null;

        try {
            con = getConnection();
            pPipeline.setModBy(pUserName);

            if (pPipeline.getPipelineId() == 0) {
                pPipeline = PipelineDataAccess.insert(con, pPipeline);
            } else {
                PipelineDataAccess.update(con, pPipeline);
            }
        } catch (Exception e) {
            logError("savePipeline error: " + e);
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(con);
        }
        return pPipeline;
    }




    /**
     *  Returns the order routing data for the supplied zip code and account id.
     *
     *@param  pPostalCode             the postal code
     *@param  pAccountId              the account id
     *@return                         The orderRoutingDescForPostalCode value
     *@returns                        the most specific matching
     *      OrderRoutingData
     *@throws  RemoteException        if an error occurs
     *@throws  DataNotFoundException  if there were no matches
     */
    public OrderRoutingDescView getOrderRoutingDescForPostalCode(String pPostalCode, int pAccountId)
    throws RemoteException, DataNotFoundException {
        Connection con = null;
        try {
            con = getConnection();
            com.cleanwise.service.api.pipeline.OrderRouting or = new com.cleanwise.service.api.pipeline.OrderRouting();
            OrderRoutingData ord = or.getOrderRoutingDataForPostalCode(pPostalCode, pAccountId, con);
            if (ord == null) {
                throw new DataNotFoundException("Could not find order routing data for account: " + pAccountId + " and postal code: " + pPostalCode);
            }
            OrderRoutingDescView ordVw = OrderRoutingDescView.createValue();
            ordVw.setOrderRoutingData(ord);
            try {
                ordVw.setAccount(BusEntityDataAccess.select(con, ord.getAccountId()));
            } catch (DataNotFoundException e) {}
            try {
                ordVw.setDistributor(BusEntityDataAccess.select(con, ord.getDistributorId()));
            } catch (DataNotFoundException e) {}
            try {
                ordVw.setFreightHandler(BusEntityDataAccess.select(con, ord.getFreightHandlerId()));
            } catch (DataNotFoundException e) {}
            try {
                ordVw.setFinalDistributor(BusEntityDataAccess.select(con, ord.getFinalDistributorId()));
            } catch (DataNotFoundException e) {}
            try {
                ordVw.setContract(ContractDataAccess.select(con, ord.getContractId()));
            } catch (DataNotFoundException e) {}
            try {
                ordVw.setFinalContract(ContractDataAccess.select(con, ord.getFinalContractId()));
            } catch (DataNotFoundException e) {}
            try{
                if(ordVw.getContract()!=null)
                ordVw.setCatalog(CatalogDataAccess.select(con,ordVw.getContract().getCatalogId()));
            } catch (DataNotFoundException e){}
            try{
                if(ordVw.getFinalContract()!=null)
                ordVw.setFinalCatalog(CatalogDataAccess.select(con,ordVw.getFinalContract().getCatalogId()));
            } catch (DataNotFoundException e){}
            try {
                ordVw.setFinalFreightHandler(BusEntityDataAccess.select(con, ord.getFinalFreightHandlerId()));
            } catch (DataNotFoundException e) {}
            try {
                ordVw.setLtlFreightHandler(BusEntityDataAccess.select(con, ord.getLtlFreightHandlerId()));
            } catch (DataNotFoundException e) {}
            return ordVw;
        } catch (javax.naming.NamingException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(con);
        }
    }



    String globalOp;

    /**
     *Processes all orders through the PostOrderCapturePipeline piepline.  This consists of all orders
     *that are aproved and have yet to be processed.
     *@returns the number of orders remainging to be processed.  This method will only process a portion of the
     *  orders given the time it takes to process orders timeouts would happen otherwise
     *@throws RemoteException
     */
    /*
    public int processPostOrderCapturePipeline()
    throws RemoteException {
        logInfo("###########################################");
        logInfo((new Date(System.currentTimeMillis()))+" - Batch. Order processing");
        globalOp = OP_ORDER_OUT;
        int nbOrders = 0;
        int nbErrors = 0;

        Connection con = null;
        try {
            con = getConnection();
            String lstat = getAppLock(con, globalOp);
            logDebug("lock status is: " + lstat);
            if (! lstat.equals("UNLOCKED") ) {
                logInfo("This operation1, " + globalOp +" is " + lstat );
                return 0;
            }
            OrderDataVector orders = getAPIAccess().getOrderAPI().getApprovedOrders();
            if(orders.size()==0) {
                logInfo("No unprocessed approved orders found");
            } else {
                String[] orderToResults = new String[orders.size()];
                OrderDataVector processedOrders = new OrderDataVector();
                int ii=-1;
                Iterator it = orders.iterator();
                while(it.hasNext()) {
                    ii++;
                    OrderData oD = (OrderData) it.next();
                    DBCriteria crit = new DBCriteria();
                    crit.addEqualTo(OrderItemDataAccess.ORDER_ID,oD.getOrderId());
                    OrderItemDataVector oID = OrderItemDataAccess.select(con,crit);
                    try {
                        //processPostOrderCapturePipeline(oD, oID, oD.getAccountId(),con);
                        processPostOrderCapturePipeline(con,oD);
                    } catch (Exception exc) {
                        exc.printStackTrace();
                        nbErrors++;
                    }
                    it.remove();
                    nbOrders++;
                    if (nbOrders > 9) {
                        break;
                    }
                }
                return orders.size();
            }
            return 0;
        }catch (Exception exc) {
            throw processException(exc);
        } finally {
            try{
                releaseAppLock(con, globalOp);
            }catch(Exception e){
                throw processException(e);
            }finally{
                closeConnection(con);
            }

            logInfo("--------------------------------------------------");
            logInfo((new Date(System.currentTimeMillis()))+
            " - Batch finished. Orders processed: "+
            nbOrders+" Errors: "+nbErrors);
            logInfo("###################################################");
        }
    }
   */
    /**
     *Processes orders defined by order status  through the  piepline defined by it's type
     * @parameter pOrderType
     * @parameter pPipelineType
     *@returns the number of orders remainging to be processed.  This method will only process a portion of the
     *  orders given the time it takes to process orders timeouts would happen otherwise
     *@throws RemoteException
     */
    public int processPipeline(String pOrderType, String pPipelineType)
    throws RemoteException {
        logInfo("###########################################");
        logInfo((new Date(System.currentTimeMillis()))+
               " - Batch. Pipeline order processing. Order type: "+pOrderType+
                " Pipeline type: "+pPipelineType);
        globalOp = OP_ORDER_OUT;
        int nbOrders = 0;
        int nbErrors = 0;

        Connection con = null;
        try {
            con = getConnection();
            String lstat = getAppLock(con, globalOp);
            logDebug("lock status is: " + lstat);
            if (! lstat.equals("UNLOCKED") ) {
                logInfo("This operation1, " + globalOp +" is " + lstat );
                return 0;
            }
            //Interrupted orders first
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderMetaDataAccess.NAME,
                    RefCodeNames.ORDER_PROPERTY_TYPE_CD.PIPELINE_STEP);
            dbc.addEqualTo(OrderMetaDataAccess.CLW_VALUE,pPipelineType);
            dbc.addGreaterThan(OrderMetaDataAccess.VALUE_NUM,0);
            String orderReq = OrderMetaDataAccess.getSqlSelectIdOnly(
                    OrderMetaDataAccess.ORDER_ID, dbc);

            dbc = new DBCriteria();
            dbc.addEqualTo(OrderDataAccess.WORKFLOW_IND,
                                RefCodeNames.WORKFLOW_IND_CD.TO_RESUME);
            dbc.addOneOf(OrderDataAccess.ORDER_ID,orderReq);
            OrderDataVector orders =
                    OrderDataAccess.select(con,dbc);

            //Add new orders to process
            orders.addAll(getAPIAccess().getOrderAPI().getOrdersByType(pOrderType));

            if(orders.size()>0) {
                String[] orderToResults = new String[orders.size()];
                OrderDataVector processedOrders = new OrderDataVector();
                int ii=-1;
                Iterator it = orders.iterator();
                while(it.hasNext()) {
                    ii++;
                    OrderData oD = (OrderData) it.next();
                    if(RefCodeNames.WORKFLOW_IND_CD.INTERRUPTED.
                                           equals(oD.getWorkflowStatusCd())) {
                      continue; //interrupted pipeline
                    }
                    DBCriteria crit = new DBCriteria();
                    try {
                        processPipeline(con,oD,pPipelineType);
                    } catch (Exception exc) {
                        exc.printStackTrace();
                        if(exc instanceof RemoteException){
                          throw exc;
                        }
                        nbErrors++;
                    }
                    it.remove();
                    nbOrders++;
                    if (nbOrders > 9) {
                        break;
                    }
                }
                return orders.size();
            }
            return 0;
        }catch (Exception exc) {
            throw processException(exc);
        } finally {
            try{
                releaseAppLock(con, globalOp);
            }catch(Exception e){
                throw processException(e);
            }finally{
                closeConnection(con);
            }

            logInfo("--------------------------------------------------");
            logInfo((new Date(System.currentTimeMillis()))+
            " - Batch finished. Orders processed: "+
            nbOrders+" Errors: "+nbErrors);
            logInfo("###################################################");
        }
    }



    /**
     *Process a distributor invoice.  This is currently a very rudimentary implementation of a pipline.
     */
    public String processDistInvoicePipeline( int pInvoiceId) throws RemoteException{
        try {
	        Connection conn = getConnection();
	        processDistInvoicePipeline(conn, pInvoiceId) ;
	        InvoiceDistData invoice = InvoiceDistDataAccess.select(conn, pInvoiceId);
	        return "Status: "+invoice.getInvoiceStatusCd();
	   	 } catch (SQLException se){
			 se.printStackTrace();
			 throw new RemoteException(se.getMessage());
		 } catch (javax.naming.NamingException ne){
			 ne.printStackTrace();
			 throw new RemoteException(ne.getMessage());
		 } catch ( com.cleanwise.service.api.util.DataNotFoundException nfe){
			 nfe.printStackTrace();
			 throw new RemoteException(nfe.getMessage());
		 }
    }

    private void processDistInvoicePipeline(Connection con, int pInvoiceId) throws RemoteException,SQLException,javax.naming.NamingException{
        logInfo("###################################################");
        logInfo("Start Processing dist invoice id: "+pInvoiceId);
        //get the invoice dist detail data vector

        //get the factory
        APIAccess factory = getAPIAccess();
        //do the steps.  This is currently hardcoded.
        try{
            InvoiceDistPipelineBaton baton = new InvoiceDistPipelineBaton();
            baton.populateBaton(con, pInvoiceId);

            LinkedList pipelineComponents = new LinkedList();
            pipelineComponents.add(new InvoiceDistStopZeroItemInvoices());
            pipelineComponents.add(new InvoiceDistPriceAnalysis());
            pipelineComponents.add(new InvoiceDistHoldInvoice());
            pipelineComponents.add(new InvoiceDistItemMatching());
            pipelineComponents.add(new InvoiceDistReceivingSystem());
            pipelineComponents.add(new InvoiceDistDateAnalysis());
            pipelineComponents.add(new InvoiceDistFreightAnalysis());
            pipelineComponents.add(new InvoiceDistTaxAnalysis());
            pipelineComponents.add(new InvoiceDistCancelBackorders());
            pipelineComponents.add(new InvoiceDistERPProcessor());
            pipelineComponents.add(new InvoiceDistAddItemActions());
            pipelineComponents.add(new InvoiceDistGCAGeneralLedgerLogic());
            pipelineComponents.add(new InvoiceDistWatchDog());
            pipelineComponents.add(new InvoiceDistUpdateObjects());
            Iterator it = pipelineComponents.iterator();
            int totalIteratorCt = 0;
            while(it.hasNext()){
            	long timer = System.currentTimeMillis();
                InvoiceDistPipeline pipeline = (InvoiceDistPipeline) it.next();
                pipeline.process(baton,con,factory);
                int repeateStep = 0;
                while(baton.REPEAT.equals(baton.getWhatNext())){
                    pipeline.process(baton,con,factory);
                    repeateStep++;
                    if(repeateStep > 50){
                        throw new PipelineException("Maximum number of repeates reached.  Pipeline componenet has been executted 50 times with no change in state");
                    }
                }
                if(baton.GO_FIRST_STEP.equals(baton.getWhatNext())){
                    it = pipelineComponents.iterator();
                    totalIteratorCt ++;
                }else if(baton.REPEAT.equals(baton.getWhatNext())){
                    throw new IllegalStateException("Baton cannot be in a Repeat state");
                }else if(baton.STOP.equals(baton.getWhatNext())){
                    break;
                }else if(baton.GO_NEXT.equals(baton.getWhatNext())){
                    //normal state continue with loop
                }else{
                    throw new IllegalStateException("Unknown Baton state: "+baton.getWhatNext());
                }
                if(repeateStep > 50){
                    throw new PipelineException("Maximum number of totalIteratorCt reached.  Pipeline has been executted 50 times with no change in state");
                }
            }

        }catch(PipelineException e){
            try{
                InvoiceDistData invoice = InvoiceDistDataAccess.select(con,pInvoiceId);
                e.printStackTrace();
                OrderDAO.enterOrderProperty(con,RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,"Invoice Error","Error Code PB001",
                         invoice.getOrderId(),0,pInvoiceId,0,0,0,0,"System");
                invoice.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
                InvoiceDistDataAccess.update(con,invoice);
            }catch(Exception e2){
                throw processException(e2);
            }
        }
        logInfo("END Processing dist invoice id: "+pInvoiceId);
        logInfo("###################################################");
    }

    /**
     *Process the distributor invoice pipeline.  This is currently a very rudimentary implementation of a pipline.
     */
    public void processDistInvoicePipeline()throws RemoteException{
        String semaphore = "INVOICE_DIST_PROCESSING";
        logInfo("###########################INVOICE PROCESSING BATCH STARTED "+new Date()+" ##################");
        Connection conn = null;
        try {
            conn = getConnection();
            String lstat = getAppLock(conn, semaphore);
            logDebug("lock status is: " + lstat);
            if (! lstat.equals("UNLOCKED") ) {
              logInfo("This operation1, "+semaphore+" is " + lstat );
              return;
            }
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(InvoiceDistDataAccess.INVOICE_STATUS_CD, RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED);
            IdVector toProcess = InvoiceDistDataAccess.selectIdOnly(conn,crit);
            logInfo("Found "+toProcess.size()+" invoices to process");
            Iterator it = toProcess.iterator();
            int ct = 0;
            while(it.hasNext()){
                processDistInvoicePipeline(conn, ((Integer) it.next()).intValue());
                ct++;
                if(ct >= 50){
                        closeConnection(conn);
                	conn = getConnection();
                        ct = 0;
                }
            }
        }catch(Exception e){
            throw processException(e);
        } finally {
           try{
             if(semaphore!=null) {
                releaseAppLock(conn, semaphore);
             }
            }catch(Exception e){
                throw new RemoteException(e.getMessage());
            }finally{
              if(conn!=null) {
                closeConnection(conn);
              }
            }
          }
    }

    /**
     *Process the distributor invoice pipeline.  This is currently a very rudimentary implementation of a pipline.
     */
    public void processSelfStoreDistInvPipeline()throws RemoteException{
        String semaphore = "INVOICE_DIST_PROCESSING";
        logInfo("########################### SELF STORE INVOICE PROCESSING BATCH STARTED "+new Date()+" ##################");
        Connection conn = null;
        try {
            conn = getConnection();
            String lstat = getAppLock(conn, semaphore);
            logDebug("lock status is: " + lstat);
            if (! lstat.equals("UNLOCKED") ) {
              logInfo("This operation1, "+semaphore+" is " + lstat );
              return;
            }
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(InvoiceDistDataAccess.INVOICE_STATUS_CD, RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED);
            crit.addNotEqualTo(InvoiceDistDataAccess.STORE_ID, 1);
            IdVector toProcess = InvoiceDistDataAccess.selectIdOnly(conn,crit);
            logInfo("Found "+toProcess.size()+"sef store invoices to process");
            Iterator it = toProcess.iterator();
            int ct = 0;
            while(it.hasNext()){
                processDistInvoicePipeline(conn, ((Integer) it.next()).intValue());
                ct++;
                if(ct >= 1000){
                	logInfo("Processed 1000 invoices, exiting.");
                    break;
                }
            }
        }catch(Exception e){
            throw processException(e);
        } finally {
           try{
             if(semaphore!=null) {
                releaseAppLock(conn, semaphore);
             }
            }catch(Exception e){
                throw new RemoteException(e.getMessage());
            }finally{
              if(conn!=null) {
                closeConnection(conn);
              }
            }
          }
    }

    public void  processInvoiceRequest(InvoiceRequestData pInvoiceReq,int pTradinPartnerId) throws RemoteException
    {
        //String semaphore = "PROCESS_INVOICE_REQUEST";
        log("PROCESS_INVOICE_REQUEST PROCESSING BATCH STARTED "+new Date());
        Connection conn = null;
        try {
            conn = getConnection();
            /*String lstat = getAppLock(conn, semaphore);
            log("lock status is: " + lstat);
            if (!lstat.equals("UNLOCKED") ) {
              logInfo("This operation , "+semaphore+" is " + lstat );
              return;
            }*/
                processInvoiceRequest(conn,pInvoiceReq,pTradinPartnerId);

        }catch(Exception e){
            throw processException(e);
        } finally {
           try{
             //if(semaphore!=null) {
               // releaseAppLock(conn, semaphore);
             //}
            }catch(Exception e){
                throw new RemoteException(e.getMessage());
            }finally{
              if(conn!=null) {
                closeConnection(conn);
              }
            }
          }
    }

    private void processInvoiceRequest(Connection conn, InvoiceRequestData pInvoiceReq, int pTradingPartnerId) throws Exception {
        log("Start processInvoiceRequest");
        //get the factory
        APIAccess factory = getAPIAccess();
        //do the steps.  This is currently hardcoded.
        InvoiceRequestPipelineBaton baton = new InvoiceRequestPipelineBaton();
        try{
            baton.populatePipelineBaton(factory, pInvoiceReq,new Integer(pTradingPartnerId));

            LinkedList pipelineComponents = new LinkedList();
            pipelineComponents.add(new InvoiceRequestValidateShipToAddress());
            pipelineComponents.add(new InvoiceRequestUpdatePO());
            pipelineComponents.add(new InvoiceRequestAnalysis());
            pipelineComponents.add(new InvoiceRequestAddShipFromAddress());
            pipelineComponents.add(new InvoiceRequestItemProcessor());
            pipelineComponents.add(new InvoiceRequestEndProcess());

            Iterator it = pipelineComponents.iterator();
            int totalIteratorCt = 0;
            while(it.hasNext()){
            	long timer = System.currentTimeMillis();
                InvoiceRequestPipeline pipeline = (InvoiceRequestPipeline) it.next();
                log("pipeline.process : "+pipeline.getClass());
                pipeline.process(baton,conn,factory);
                int repeateStep = 0;
                while(PipelineBaton.REPEAT.equals(baton.getWhatNext())){
                    pipeline.process(baton,conn,factory);
                    repeateStep++;
                    if(repeateStep > 50){
                        throw new PipelineException("Maximum number of repeates reached.  Pipeline componenet has been executted 50 times with no change in state");
                    }
                }
                log("Invoice Dist Pipeline****"+pipeline.getClass().getName()+" completed in "+(System.currentTimeMillis() - timer)+" milliseconds");
                if(PipelineBaton.GO_FIRST_STEP.equals(baton.getWhatNext())){
                    it = pipelineComponents.iterator();
                    totalIteratorCt ++;
                }else if(PipelineBaton.REPEAT.equals(baton.getWhatNext())){
                    throw new IllegalStateException("Baton cannot be in a Repeat state");
                }else if(PipelineBaton.STOP.equals(baton.getWhatNext())){
                    break;
                }else if(PipelineBaton.GO_NEXT.equals(baton.getWhatNext())){
                    //normal state continue with loop
                }else{
                    throw new IllegalStateException("Unknown Baton state: "+baton.getWhatNext());
                }
                if(repeateStep > 50){
                    throw new PipelineException("Maximum number of totalIteratorCt reached.  Pipeline has been executted 50 times with no change in state");
                }
            }

        }catch(PipelineException e){
            try{
                log("Error Code PB001");
               }catch(Exception e2){
                throw processException(e2);
            }
        }finally{
            log("notes : "+baton.getNotesToLog());
            log("itemNotes : "+baton.getItemNotesToLog());
            log("miscNotes : "+baton.getMiscInvoiceNotes());
        }
        log("END Processing request invoice");
    }

    private void log(String message){
        logInfo(className + "::"+ message);
    }


    /*
     * Processes pipeline for the order
     * @param pOrderData order to be processed
     * @param pPipelineType type of the pipeline
     * @Throws RemoteException
     */
    public void
         processPipeline(OrderData pOrderData, String pPipelineType)
    throws RemoteException {
      String semaphore = null;
      Exception exception = null;
      if(RefCodeNames.PIPELINE_CD.ASYNCH.equals(pPipelineType) ||
         RefCodeNames.PIPELINE_CD.ASYNCH_PRE_PROCESSED.equals(pPipelineType) ||
         RefCodeNames.PIPELINE_CD.SYNCH_ASYNCH.equals(pPipelineType)) {
         semaphore = OP_ORDER_OUT;
      }
      Connection conn = null;
      try {
        conn = getConnection();
        if(semaphore!=null) {
          String lstat = getAppLock(conn, semaphore);
          logDebug("lock status is: " + lstat);
          if (! lstat.equals("UNLOCKED") ) {
            logInfo("This operation1, " + semaphore +" is " + lstat );
            return;
          }
        }
        processPipeline(conn, pOrderData, pPipelineType);

       }catch (Exception exc) {
           exc.printStackTrace();
           exception = exc;           
       } finally {
           try{
             if(semaphore!=null) {
                releaseAppLock(conn, semaphore);
             }
            }catch(Exception e){
            	e.printStackTrace();
            	logError(e.getMessage());
            	if (exception == null)
            		throw new RemoteException("Exception in PipelineBean.processPipeline: ", exception);
            }finally{
              if(conn!=null) {
                closeConnection(conn);
              }
              if (exception != null){// throw original exception
            	  throw new RemoteException(exception.getMessage());
              }
            }
          }
    }


    /*
     * Processes unfinished pipeline for the order
     * @param pOrderData order to be processed
     * @Throws RemoteException
     */
    public void resumePipeline(OrderData pOrderData)
    throws RemoteException {
      Connection conn = null;
      try {
        conn = getConnection();
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID,pOrderData.getOrderId());
        dbc.addEqualTo(OrderMetaDataAccess.NAME,
                RefCodeNames.ORDER_PROPERTY_TYPE_CD.PIPELINE_STEP);
		dbc.addOrderBy(OrderMetaDataAccess.ORDER_META_ID, false);
        OrderMetaDataVector omDV = OrderMetaDataAccess.select(conn,dbc);
        if(omDV.size()==0) {
          String errorMess = "Order inconsistence. " +
                  "Interrapted order doesn't have pipeline step meta property. " +
                  "Order id: "+pOrderData.getOrderId();
          throw new Exception(errorMess);
        }
		/*
        if(omDV.size()>1) {
          String errorMess = "Order inconsistence. " +
                  "Intrrapted order has multiple pipeline step meta properties. " +
                  "Order id: "+pOrderData.getOrderId();
          throw new Exception(errorMess);
        }
		*/
        OrderMetaData omD = (OrderMetaData) omDV.get(0);
        int pipelineId = omD.getValueNum();
        if(pipelineId<=0) {
          String errorMess = "Wrong pipeline step id (OrderMeta table). " +
                  "Order id: "+pOrderData.getOrderId();
          throw new Exception(errorMess);
        }
        PipelineData pD = PipelineDataAccess.select(conn,pipelineId);
        if(!pD.getPipelineTypeCd().equals(omD.getValue())) {
          String errorMess = "Wrong pipeline step(OrderMeta table). " +
                  " Pipeline id doesn't correspond to pipeline type " +
                  "Order id: "+pOrderData.getOrderId();
          throw new Exception(errorMess);
        }
        processPipeline(pOrderData, pD.getPipelineTypeCd());

       }catch (Exception exc) {
           exc.printStackTrace();
           throw new RemoteException(exc.getMessage());
       } finally {
              if(conn!=null) {
                closeConnection(conn);
              }
       }
    }


     private void processPipeline(Connection pCon, OrderData pOrderData, String pPipelineType) throws Exception {
         PipelineUtil pip = new PipelineUtil();
         pip.processPipeline(pCon, getAPIAccess(), pOrderData, pPipelineType);
     }

    /**
       * Processes pipeline for the order collection
       * @param  param
       * @param pPipelineType type of the pipeline
       * @throws RemoteException
       */
    public void processPipeline(Hashtable param, String pPipelineType)
            throws RemoteException {

        String semaphore = null;
        if(RefCodeNames.PIPELINE_CD.ASYNCH.equals(pPipelineType)
                || RefCodeNames.PIPELINE_CD.ASYNCH_PRE_PROCESSED.equals(pPipelineType)
                || RefCodeNames.PIPELINE_CD.SYNCH_ASYNCH.equals(pPipelineType)) {
            semaphore = OP_ORDER_OUT;
        }
        Connection conn = null;
        try {
            conn = getConnection();
            if(semaphore!=null) {
                String lstat = getAppLock(conn, semaphore);
                logDebug("lock status is: " + lstat);
                if (! lstat.equals("UNLOCKED") ) {
                    logInfo("This operation1, " + semaphore +" is " + lstat );
                    return;
                }
            }
            processPipeline(conn, param, pPipelineType);

        }catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try{
                if(semaphore!=null) {
                    releaseAppLock(conn, semaphore);
                }
            }catch(Exception e){
                throw new RemoteException(e.getMessage());
            }finally{
                if(conn!=null) {
                    closeConnection(conn);
                }
            }
        }
    }

    /**
     *
     * @param pCon   Connection
     * @param  params
     * @param pPipelineType    pipeline type
     * @throws Exception
     */
     private void processPipeline(Connection pCon,Hashtable params,String pPipelineType) throws Exception {
        PipelineUtil pip = new PipelineUtil();
        pip.processPipeline(pCon, getAPIAccess(),params, pPipelineType);

     }

     public List processCheckoutRequest(OrderData pOrderData, OrderItemDataVector oiDV, String userRoleCd, String freightType)
     throws RemoteException{
    	 Connection conn = null;

    	 try {

    		 APIAccess factory = getAPIAccess();
    		 conn = getConnection();

    		 OrderPipelineActor pipelineActor = new OrderPipelineActor();
    		 OrderPipelineBaton baton = new OrderPipelineBaton();
    		 baton.setStepNum(0);
    		 baton.setPipelineTypeCd(RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE);
    		 baton.setWhatNext(OrderPipelineBaton.GO_NEXT);
    		 baton.setOrderData(pOrderData);
    		 baton.setOrderItemDataVector(oiDV);
    		 baton.setFreightType(freightType);
    		 baton.setOrderRequestData(null); //no order request necessary
    		 baton.setCurrentDate(new Date());
    		 baton.setUserWorkflowRoleCd(userRoleCd);
    		 baton.setUserName(RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE);
    		 baton.setOrderMetaDataVector(new OrderMetaDataVector());

    		 OrderPipelineBaton[] pipelineResult =
    			 pipelineActor.processPipeline(
    					 baton,
    					 RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE,
    					 conn,
    					 factory);

    		 return baton.getResultMessages();

    	 }catch (Exception exc) {
    		 exc.printStackTrace();
    		 throw new RemoteException(exc.getMessage());
    	 } finally {
    		 closeConnection(conn);
    	 }
     }

}


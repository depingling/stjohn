package com.cleanwise.service.api.session;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;


import com.cleanwise.service.api.eventsys.EventData;
import com.cleanwise.service.apps.*;


import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Iterator;
import javax.naming.NamingException;
import java.lang.reflect.*;

import java.io.*;
import java.text.SimpleDateFormat;
import com.cleanwise.service.apps.ClientServicesAPI;

import org.quartz.*;

/**
 * Title:        SystemAgentBean
 * Description:  Bean implementation for SystemAgent Stateless Session Bean
 * Purpose:      Provides access to the services for managing the SystemAgent information, properties and relationships.
 * Copyright:    Copyright (c) 2002
 * Company:      CleanWise, Inc.
 */
import javax.ejb.*;


/**
 * <code>SystemAgent</code> stateless session bean.
 */
public class SystemAgentBean extends UtilityServicesAPI {
    //package that the dao classes are located in
    static final String daoPackage = "com.cleanwise.service.api.dao.";
    
    /**
     * Creates a new <code>SystemAgentBean</code> instance.
     */
    public SystemAgentBean() {
    }
    
    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @exception CreateException if an error occurs
     * @exception RemoteException if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }
    
    private OrderDataVector getOrdersInState
    (Connection pConn,ArrayList pBadOrderStatus,ArrayList pBadOrderItemStatus)
    throws SQLException{
        //first get all the orders with the bad status
        DBCriteria crit = new DBCriteria();
        crit.addOneOf(OrderDataAccess.ORDER_STATUS_CD,pBadOrderStatus);
        DBCriteria orCrit = new DBCriteria();
        //now add to the criteria any bad items
        DBCriteria itemCrit = new DBCriteria();
        itemCrit.addOneOf(OrderItemDataAccess.ORDER_ITEM_STATUS_CD,pBadOrderItemStatus);
        orCrit.addOneOf(OrderDataAccess.ORDER_ID,OrderItemDataAccess.getSqlSelectIdOnly(OrderItemDataAccess.ORDER_ID,itemCrit));
        crit.addOrCriteria(orCrit);
        logDebug(crit.getWhereClause());
        return OrderDataAccess.select(pConn,crit);
    }
    
    /**
     * Returns an orderDataVector of all of the order data value objects
     * that have any orders with a pending status or has items that are in
     * a pending status.
     * @return a <code>OrderDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public OrderDataVector getPendingOrders() throws RemoteException {
        Connection conn = null;
        try{
            logInfo("Getting Pending Orders");
            conn = getConnection();
            ArrayList badOrderStatus = new ArrayList();
            badOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE);
            badOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION);
            badOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
            badOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW);
            badOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
            
            ArrayList badItemStatus = new ArrayList();
            badItemStatus.add(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_REVIEW);
            badItemStatus.add(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT);
            
            OrderDataVector ret = getOrdersInState(conn,badOrderStatus,badItemStatus);
            logInfo("Done Getting Pending Orders");
            return ret;
        }catch (NamingException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     * Returns an orderDataVector of all of the order data value objects
     * that have any orders with an error status or has items that are in
     * an error status.
     * @return a <code>OrderDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public OrderDataVector getFailedOrders() throws RemoteException {
        Connection conn = null;
        try{
            logInfo("Getting Failed Orders");
            conn = getConnection();
            ArrayList pendOrderStatus = new ArrayList();
            pendOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED);
            pendOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED_PO_ERROR);
            pendOrderStatus.add(RefCodeNames.ORDER_STATUS_CD.SENDING_TO_ERP);
            ArrayList pendItemStatus = new ArrayList();
            pendItemStatus.add(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED);
            OrderDataVector ret = getOrdersInState(conn,pendOrderStatus,pendItemStatus);
            logInfo("Done Getting Failed Orders");
            return ret;
        }catch (NamingException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     * Returns an OrderBatchLogDataVector of all of the order batch log
     * entries with a failed status.
     * @return a <code>OrderBatchLogDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public OrderBatchLogDataVector getFailedOrderBatchEntries() throws RemoteException {
        Connection conn = null;
        try{
            logInfo("Getting Failed Orders Batch Entries");
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            ArrayList status = new ArrayList();
            status.add(RefCodeNames.ORDER_BATCH_STATUS_CD.FAILURE);
            status.add(RefCodeNames.ORDER_BATCH_STATUS_CD.SUCCESS_NO_EMAIL);
            crit.addOneOf(OrderBatchLogDataAccess.ORDER_BATCH_STATUS_CD,status);
            OrderBatchLogDataVector ret = OrderBatchLogDataAccess.select(conn,crit);
            logInfo("Done Getting Failed Orders Batch Entries");
            return ret;
        }catch (NamingException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     * Returns an PurchaseOrderDataVector of all of the purchase orders that
     * are in a failed state
     * @return a <code>PurchaseOrderDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public PurchaseOrderDataVector getFailedPurchaseOrders() throws RemoteException {
        Connection conn = null;
        try{
            logInfo("Getting Failed Purchase Orders");
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            ArrayList status = new ArrayList();
            status.add(RefCodeNames.PURCHASE_ORDER_STATUS_CD.PENDING_FULFILLMENT);
            status.add(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED);
            crit.addEqualToIgnoreCase(
            PurchaseOrderDataAccess.PURCHASE_ORDER_STATUS_CD,RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED);
            PurchaseOrderDataVector podv = PurchaseOrderDataAccess.select(conn,crit);
            
            //now get everything that was modified n days ago or earlier in a PENDING_FULFILLMENT state
            crit = new DBCriteria();
            crit.addEqualToIgnoreCase(
            PurchaseOrderDataAccess.PURCHASE_ORDER_STATUS_CD,RefCodeNames.PURCHASE_ORDER_STATUS_CD.PENDING_FULFILLMENT);
            
            Calendar yesterday = Calendar.getInstance();
            yesterday.set(Calendar.DAY_OF_YEAR,yesterday.get(Calendar.DAY_OF_YEAR) - 1);
            crit.addLessOrEqual(PurchaseOrderDataAccess.MOD_DATE,yesterday.getTime());
            podv.addAll(PurchaseOrderDataAccess.select(conn,crit));
            logInfo("Done Getting Failed Purchase Orders");
            return podv;
        }catch (NamingException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     * Returns a InvoiceDistDataVector of all of the distributor invoices that
     * are in a failed state
     * @return a <code>InvoiceDistDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public InvoiceDistDataVector getFailedDistributorInvoices() throws RemoteException {
        Connection conn = null;
        try{
            logInfo("Getting Failed Distributor Invoices");
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            ArrayList status = new ArrayList();
            status.add(RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED_ERROR);
            status.add(RefCodeNames.INVOICE_STATUS_CD.ERP_REJECTED);
            status.add(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR);
            //logically this is actually an error
            status.add(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
            crit.addOneOf(InvoiceDistDataAccess.INVOICE_STATUS_CD,status);
            InvoiceDistDataVector ret = InvoiceDistDataAccess.select(conn,crit);
            logInfo("Done Getting Failed Distributor Invoices");
            return ret;
        }catch (NamingException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     * Returns a InvoiceCustDataVector of all of the customer invoices that
     * are in a failed state
     * @return a <code>InvoiceCustDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public InvoiceCustDataVector getFailedCustomerInvoices() throws RemoteException {
        Connection conn = null;
        try{
            logInfo("Getting Failed Cusomter Invoices");
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            ArrayList status = new ArrayList();
            status.add(RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED_ERROR);
            status.add(RefCodeNames.INVOICE_STATUS_CD.ERP_REJECTED);
            status.add(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR);
            status.add(RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED_FAILED);
            //logically this is actually an error
            status.add(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
            crit.addOneOf(InvoiceCustDataAccess.INVOICE_STATUS_CD,status);
            
            DBCriteria itemCrit = new DBCriteria();
            ArrayList detStatus = new ArrayList();
            //logically this is actually an error
            detStatus.add(RefCodeNames.INVOICE_DETAIL_STATUS_CD.PENDING_REVIEW);
            itemCrit.addOneOf(InvoiceCustDetailDataAccess.INVOICE_DETAIL_STATUS_CD,detStatus);
            DBCriteria orCrit = new DBCriteria();
            orCrit.addOneOf(InvoiceCustDataAccess.INVOICE_CUST_ID,InvoiceCustDetailDataAccess.getSqlSelectIdOnly(InvoiceCustDetailDataAccess.INVOICE_CUST_ID,itemCrit));
            crit.addOrCriteria(orCrit);
            InvoiceCustDataVector ret = InvoiceCustDataAccess.select(conn,crit);
            logInfo("Done Getting Failed Cusomter Invoices");
            return ret;
        }catch (NamingException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    
    /**
     * Returns a InvoiceCustDataVector of all of the customer invoices that
     * failed to be sent to CIT.  This is a little complicated as it depends
     * on the customer and wheather they are EDI enabled, however we will ignore
     * this and treat anything that is more than a n days old as a problem.
     * Essentially this is correct, it may just be related to a different problem.
     * @return a <code>InvoiceCustDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public InvoiceCustDataVector getFailedCITCustomerInvoices() throws RemoteException {
        Connection conn = null;
        try{
            logInfo("Getting Failed Cit Customer Invoices");
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualToIgnoreCase(InvoiceCustDataAccess.CIT_STATUS_CD,RefCodeNames.CIT_STATUS_CD.FAILED);
            
            DBCriteria orCrit = new DBCriteria();
            orCrit.addEqualToIgnoreCase(InvoiceCustDataAccess.CIT_STATUS_CD,RefCodeNames.CIT_STATUS_CD.PENDING);
            Calendar yesterday = Calendar.getInstance();
            yesterday.set(Calendar.DAY_OF_YEAR,yesterday.get(Calendar.DAY_OF_YEAR) - 2);
            orCrit.addLessOrEqual(InvoiceCustDataAccess.MOD_DATE,yesterday.getTime());
            ArrayList status = new ArrayList();
            status.add(RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED_ERROR);
            status.add(RefCodeNames.INVOICE_STATUS_CD.ERP_REJECTED);
            status.add(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR);
            status.add(RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED_FAILED);
            orCrit.addNotOneOf(InvoiceCustDataAccess.INVOICE_STATUS_CD,status);
            crit.addOrCriteria(orCrit);
            InvoiceCustDataVector ret = InvoiceCustDataAccess.select(conn,crit);
            logInfo("Done Getting Failed Cit Customer Invoices");
            return ret;
        }catch (NamingException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     * Returns a RemittanceDataVector of all of the remittances that
     * are in a failed state
     * @return a <code>RemittanceDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public RemittanceDataVector getFailedRemittanceData() throws RemoteException {
        Connection conn = null;
        try{
            logInfo("Getting Failed Remittance Data");
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            ArrayList status = new ArrayList();
            status.add(RefCodeNames.REMITTANCE_STATUS_CD.PROCESS_ERP_ERROR);
            status.add(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED_ERROR);
            status.add(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY);
            crit.addOneOf(RemittanceDataAccess.REMITTANCE_STATUS_CD,status);
            RemittanceDataVector ret = RemittanceDataAccess.select(conn,crit);
            logInfo("Done Getting Failed Remittance Data");
            return ret;
        }catch (NamingException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    
    /**
     * Returns a RemittanceDetailDataVector of all of the remittances detail (invoices)
     * that are in a failed state
     * @return a <code>RemittanceDetailDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public RemittanceDetailDataVector getFailedRemittanceDetailData() throws RemoteException {
        Connection conn = null;
        try{
            logInfo("Getting Failed Remittance Detail Data");
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            ArrayList status = new ArrayList();
            status.add(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.PROCESS_ERP_ERROR);
            status.add(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED_ERROR);
            status.add(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.INFORMATION_ONLY);
            crit.addOneOf(RemittanceDetailDataAccess.REMITTANCE_DETAIL_STATUS_CD,status);
            RemittanceDetailDataVector ret = RemittanceDetailDataAccess.select(conn,crit);
            logInfo("Done Getting Failed Remittance Detail Data");
            return ret;
        }catch (NamingException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     *Returns a list of all of the RemittancePropertyData where the
     *remittanceDetailId = 0, remittanceId = 0 and the type is an error.
     *This indicates a line that was not able to be processed by the loader.
     *@throws RemoteException on any error
     */
    public RemittancePropertyDataVector getUnparsableRemittanceData()
    throws RemoteException{
        Remittance remitEjb;
        try{
            logInfo("Getting Unparsable Remittance Data");
            remitEjb = getAPIAccess().getRemittanceAPI();
            logInfo("Done Getting Unparsable Remittance Data");
            return remitEjb.getUnparsableRemittanceData();
        }catch (Exception e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }
    
    /**
     *Verifies the dao objects based off the tables that are in the database.
     *Selects all tables out of the database that match the naming convention used by the 
     *code generation, then uses reflection to invoke the "select" method.  This may cause
     *a DataNotFound Exception to be generated, which is ignored, but if any other exception
     *is thrown it will be added to the list that is returned.
     *@returns List a list of errors
     *@throws RemoteException on any error
     */
    public List verifyDaoLayer() throws RemoteException{
        logInfo("Attempting to Verify DAO Layer");
        Connection conn = null;
        boolean success = true;
        List errors = new ArrayList();
        try{
            conn = getConnection();
            java.sql.DatabaseMetaData meta = conn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = meta.getTables(null, meta.getUserName(), "CLW_%", types);
            List tableNames = new ArrayList();
            while (rs.next()){
                String tabName = rs.getString("TABLE_NAME");
                tableNames.add(tabName);
                logDebug(tabName);
            }
            rs.close();
            //parameter types used when calling the "select" method on the dao class
            //final Class[] paramsTypesForSelect = {conn.getClass(),Integer.TYPE};
            final Class[] paramsTypesForSelect = {Class.forName("java.sql.Connection"),Integer.TYPE};
            
            Iterator it = tableNames.iterator();
            while(it.hasNext()){
                String tabName = (String) it.next();
                
                //****Create the dao class name*****
                //start with a lower case table name
                StringBuffer tabNameBuf = new StringBuffer(tabName.toLowerCase());
                //get rid of the CLW
                tabNameBuf.delete(0,3);
                for(int i=0;i<tabNameBuf.length();i++){
                    char c = tabNameBuf.charAt(i);
                    //upper case the next char and delete this one
                    if (c == '_'){
                        if(i+1<=tabNameBuf.length()){
                            char nextChr = tabNameBuf.charAt(i+1);
                            nextChr = Character.toUpperCase(nextChr);
                            tabNameBuf.setCharAt(i+1, nextChr);
                        }
                        tabNameBuf.deleteCharAt(i);
                    }
                }
                String className=tabNameBuf+"DataAccess";
                logDebug("Testing: " + className);
                //****Now that we have created the name try and select some data*****
                try{
                    Class clazz = Class.forName(daoPackage+className);
                    java.lang.reflect.Method selectMeth = clazz.getDeclaredMethod("select", paramsTypesForSelect);
                    Object[] args = {conn,new Integer(1)};
                    selectMeth.invoke(null, args);
                    logDebug("method invoked");
                    
                    //******Make sure all of the fields match the database*****
                    //retrieve all the columns fom the database
                    List columns = new ArrayList();
                    rs = meta.getColumns(null, meta.getUserName(), tabName, null);
                    while (rs.next()){
                        columns.add(rs.getString("COLUMN_NAME"));
                    }
                    rs.close();
                    //match the db against the dao class's fields
                    Field[] fields = clazz.getDeclaredFields();
                    for(int i=0;i<fields.length;i++){
                        Field field = fields[i];
                        String fieldName = field.getName();
                        if(Modifier.isPublic(field.getModifiers())){
                            if((!fieldName.equals(tabName))){
                                String fieldValue = (String) field.get(null);
                                if(fieldValue.equals(fieldName)){
                                    columns.remove(fieldName);
                                }else{
                                    errors.add("Field "+fieldName+" does not match value: "+fieldValue+" in class: "+className);
                                }
                            }
                        }
                    }
                    Iterator colit = columns.iterator();
                    while(colit.hasNext()){
                        String column = (String) colit.next();
                        errors.add("Table: "+tabName+" has extra column: "+column+" not in dao class: " +className);
                    }
                }catch (java.lang.reflect.InvocationTargetException e){
                    //when move to jdk1.4 use e.getCause(), not e.getTargetException()
                    Throwable root = e.getTargetException();
                    //Data Not Found exception just means no id of 1 in database,  which is fine
                    if(!(root instanceof DataNotFoundException)){
                        errors.add("Caught Exception::Class: "+className+"::"+root.getClass().getName()+"::"+root.getMessage());
                        root.printStackTrace();
                        logDebug("Exception Message: "+root.getMessage());
                        logDebug("Exception Type: "+root.getClass().getName());
                    }
                }catch (Exception e){
                    errors.add("Caught Exception::Using Class: "+className+"::Exception Type::"+e.getClass().getName()+"::Message::"+e.getMessage());
                }
            }
        }catch (Exception e){
            success = false;
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
        return errors;
    }
}

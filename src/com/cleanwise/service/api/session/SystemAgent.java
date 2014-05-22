package com.cleanwise.service.api.session;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import java.util.List;
import javax.ejb.*;
import java.rmi.*;
import org.quartz.*;
import java.sql.SQLException;
import javax.naming.NamingException;




/**
 * Remote interface for the <code>SystemAgent</code> stateless session bean.
 * @author  bstevens
 */
public interface SystemAgent extends javax.ejb.EJBObject {

    
    /**
     * Returns an orderDataVector of all of the order data value objects
     * that have any orders with a pending status or has items that are in 
     * a pending status.
     * @return a <code>OrderDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public OrderDataVector getFailedOrders() throws RemoteException;
    
    /**
     * Returns an orderDataVector of all of the order data value objects
     * that have any orders with an error status or has items that are in 
     * an error status.
     * @return a <code>OrderDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public OrderDataVector getPendingOrders() throws RemoteException;
    
    /**
     * Returns an OrderBatchLogDataVector of all of the order batch log
     * entries with a failed status.
     * @return a <code>OrderBatchLogDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public OrderBatchLogDataVector getFailedOrderBatchEntries() throws RemoteException;
    
    /**
     * Returns an PurchaseOrderDataVector of all of the purchase orders that
     * are in a failed state
     * @return a <code>PurchaseOrderDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public PurchaseOrderDataVector getFailedPurchaseOrders() throws RemoteException;
    
    /**
     * Returns a InvoiceDistDataVector of all of the distributor invoices that
     * are in a failed state
     * @return a <code>InvoiceDistDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public InvoiceDistDataVector getFailedDistributorInvoices() throws RemoteException;
    
    /**
     * Returns a InvoiceCustDataVector of all of the customer invoices that
     * are in a failed state
     * @return a <code>InvoiceCustDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public InvoiceCustDataVector getFailedCustomerInvoices() throws RemoteException;
    
    /**
     * Returns a InvoiceCustDataVector of all of the customer invoices that
     * failed to be sent to CIT.  This is a little complicated as it depends
     * on the customer and wheather they are EDI enabled, however we will ignore
     * this and treat anything that is more than a n days old as a problem.  
     * Essentially this is correct, it may just be related to a different problem.
     * @return a <code>InvoiceCustDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public InvoiceCustDataVector getFailedCITCustomerInvoices() throws RemoteException;
    
    /**
     * Returns a RemittanceDataVector of all of the remittances that
     * are in a failed state
     * @return a <code>RemittanceDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public RemittanceDataVector getFailedRemittanceData() throws RemoteException;
    
    
    /**
     * Returns a RemittanceDetailDataVector of all of the remittances detail (invoices)
     * that are in a failed state
     * @return a <code>RemittanceDetailDataVector</code> vector
     * @exception RemoteException if an error occurs
     */
    public RemittanceDetailDataVector getFailedRemittanceDetailData() throws RemoteException;
    
    /**
     *Returns a list of all of the RemittancePropertyData where the 
     *remittanceDetailId = 0, remittanceId = 0 and the type is an error.
     *This indicates a line that was not able to be processed by the loader.
     *@throws RemoteException on any error
     */
    public RemittancePropertyDataVector getUnparsableRemittanceData()
    throws RemoteException;
    
    /**
     *Verifies the dao objects based off the tables that are in the database.
     *Selects all tables out of the database that match the naming convention used by the 
     *code generation, then uses reflection to invoke the "select" method.  This may cause
     *a DataNotFound Exception to be generated, which is ignored, but if any other exception
     *is thrown it will be added to the list that is returned.
     *@returns List a list of errors
     *@throws RemoteException on any error
     */
    public List verifyDaoLayer() throws RemoteException;
  
}

/**
 * TaxSvcSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Proxy interface for the Avalara Tax Web Service. Requires a
 * Web Service Deployment Descriptor (WSDD) configuration file at creation time named
 * (for example the sample file avatax4j.wsdd) in the same directory as the
 * project is running. The values in the file will be loaded as the default
 * configuration information.
 * <p>
 * <pre>
 * <b>Example:</b>
 * [Java]
 * EngineConfiguration config = new FileProvider("avatax4j.wsdd");
 * TaxSvcLocator taxSvcLoc = new TaxSvcLocator(config);
 *
 * TaxSvcSoap svc = taxSvcLoc.getTaxSvcSoap(new URL("http://www.avalara.com/services/"));
 *
 * // Set the profile
 * Profile profile = new Profile();
 * profile.setClient("TaxSvcTest,4.0.0.0");
 * svc.setProfile(profile);
 *
 * // Set security
 * Security security = new Security();
 * security.setAccount("account");
 * security.setLicense("license number");
 * svc.setSecurity(security);
 * </pre>
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public interface TaxSvcSoap extends com.avalara.avatax.services.base.BaseSvcSoap {

    /**
     * Calculates taxes on a document such as a sales order, sales invoice, purchase order, purchase invoice, or credit memo.
     * <br>The tax data is saved Sales Invoice and Purchase Invoice document types {@link GetTaxRequest#getDocType}.
     *
     * @param getTaxRequest  -- Tax calculation request
     *
     * @return   {@link GetTaxResult} object
     * @throws java.rmi.RemoteException
     */
    public com.avalara.avatax.services.tax.GetTaxResult getTax(com.avalara.avatax.services.tax.GetTaxRequest getTaxRequest) throws java.rmi.RemoteException;

    /**
     * Retrieves a previously calculated tax document.
     * <p>
     * This is only available for saved tax documents (Sales Invoices, Purchase Invoices).
     * </p>
     * <p>
     * A document can be indicated solely by the {@link PostTaxRequest#getDocId} if it is known.
     * Otherwise the request must specify all of {@link PostTaxRequest#getCompanyCode}, see {@link PostTaxRequest#getDocCode}
     * and {@link PostTaxRequest#getDocType} in order to uniquely identify the document.
     * </p>
     *
     * @param getTaxHistoryRequest a {@link GetTaxHistoryRequest} object indicating the document for which history should be retrieved.
     * @return a {@link GetTaxHistoryResult} object
     * @throws java.rmi.RemoteException
     */
    public com.avalara.avatax.services.tax.GetTaxHistoryResult getTaxHistory(com.avalara.avatax.services.tax.GetTaxHistoryRequest getTaxHistoryRequest) throws java.rmi.RemoteException;

    /**
     * Posts a previously calculated tax
     * <p>
     * This is only available for saved tax documents (Sales Invoices, Purchase Invoices).
     * </p>
     * <p>
     * A document can be indicated solely by the {@link PostTaxRequest#getDocId} if it is known.
     * Otherwise the request must specify all of {@link PostTaxRequest#getCompanyCode}, {@link PostTaxRequest#getDocCode}, and
     * {@link PostTaxRequest#getDocType} in order to uniquely identify the document.
     * </p>
     *
     * @param postTaxRequest a {@link PostTaxRequest} object indicating the document that should be posted.
     * @return a {@link PostTaxResult} object
     * @throws java.rmi.RemoteException
     */
    public com.avalara.avatax.services.tax.PostTaxResult postTax(com.avalara.avatax.services.tax.PostTaxRequest postTaxRequest) throws java.rmi.RemoteException;

    /**
     * Commits a previously posted tax.
     * <p>
     * This is only available for posted tax documents (Sales Invoices, Purchase Invoices). Committed documents cannot
     * be changed or deleted.
     * </p>
     * <p>
     * A document can be indicated solely by the {@link CommitTaxRequest#getDocId} if it is known. Otherwise the
     * request must specify all of {@link CommitTaxRequest#getCompanyCode}, {@link CommitTaxRequest#getDocCode}, and
     * {@link CommitTaxRequest#getDocType} in order to uniquely identify the document.
     * </p>
     *
     * @param commitTaxRequest a {@link CommitTaxRequest} object indicating the document that should be committed.
     * @return a {@link CommitTaxResult} object
     * @throws java.rmi.RemoteException
     */
    public com.avalara.avatax.services.tax.CommitTaxResult commitTax(com.avalara.avatax.services.tax.CommitTaxRequest commitTaxRequest) throws java.rmi.RemoteException;

    /**
     * Cancels a previously calculated tax;  This is for use as a
     * compensating action when posting on the client fails to complete.
     * <p>
     * This is only available for saved tax document types (Sales Invoices, Purchase Invoices). A document that is saved
     * but not posted will be deleted if canceled. A document that has been posted will revert to a saved state if canceled
     * (in this case <b>CancelTax</b> should be called with a {@link CancelTaxRequest#getCancelCode} of
     * <i>PostFailed</i>). A document that has been committed cannot be reverted to a posted state or deleted. In the case
     * that a document on the client side no longer exists, a committed document can be virtually removed by calling
     * <b>CancelTax</b> with a <b>CancelCode</b> of <i>DocDeleted</i>. The record will be retained in history but removed
     * from all reports.
     * </p>
     * <p>
     * A document can be indicated solely by the {@link CancelTaxRequest#getDocId} if it is known. Otherwise the request
     * must specify all of {@link CancelTaxRequest#getCompanyCode}, {@link CancelTaxRequest#getDocCode}, and
     * {@link CancelTaxRequest#getDocType} in order to uniquely identify the document.
     *
     * @param cancelTaxRequest a {@link CancelTaxRequest} object indicating the document that should be canceled.
     * @return   a {@link CancelTaxResult} object
     * @throws java.rmi.RemoteException
     */
    public com.avalara.avatax.services.tax.CancelTaxResult cancelTax(com.avalara.avatax.services.tax.CancelTaxRequest cancelTaxRequest) throws java.rmi.RemoteException;

    /**
     * Reconciles tax history to ensure the client data matches the
     * AvaTax history.
     * <p>The Reconcile operation allows reconciliation of the AvaTax history with the client accounting system.
     * It must be used periodically according to your service contract.
     * </p>
     * <p>
     * Because there may be a large number of documents to reconcile, it is designed to be called repetitively
     * until all documents have been reconciled.  It should be called until no more documents are returned.
     * Each subsequent call should pass the previous results {@link ReconcileTaxHistoryRequest#getLastDocId}.
     * </p>
     * <p>
     * When all results have been reconciled, Reconcile should be called once more with
     * {@link ReconcileTaxHistoryRequest#getLastDocId}
     * equal to the last document code processed and {@link ReconcileTaxHistoryRequest#isReconciled} set to true to indicate
     * that all items have been reconciled.  If desired, this may be done incrementally with each result set.  Just send
     * Reconciled as true when requesting the next result set and the prior results will be marked as reconciled.
     * </p>
     * <p>
     * The {@link #postTax}, {@link #commitTax}, and {@link #cancelTax} operations can be used to correct any differences.
     * {@link #getTax} should be called if any committed documents are out of balance
     * ({@link GetTaxResult#getTotalAmount} or {@link GetTaxResult#getTotalTax}
     * don't match the accounting system records).  This is to make sure the correct tax is reported.
     * </p>
     *
     * @param reconcileTaxHistoryRequest  a Reconciliation request
     * @return A collection of documents that have been posted or committed since the last reconciliation.
     * @throws java.rmi.RemoteException
     */
    public com.avalara.avatax.services.tax.ReconcileTaxHistoryResult reconcileTaxHistory(com.avalara.avatax.services.tax.ReconcileTaxHistoryRequest reconcileTaxHistoryRequest) throws java.rmi.RemoteException;

    /**
     * Verifies connectivity to the web service and returns version information about the service.
     * <p>This replaces TestConnection and is available on every service.</p>
     *
     * @param message For future use
     * @return  a {@link PingResult} object
     * @throws java.rmi.RemoteException
     */
    public com.avalara.avatax.services.tax.PingResult ping(java.lang.String message) throws java.rmi.RemoteException;

    /**
     * Checks authentication of and authorization to one or more operations on the service.
     * <p>
     * This operation allows pre-authorization checking of any or all operations.
     * It will return a comma delimited set of operation names which will be all or a subset
     * of the requested operation names.  For security, it will never return operation names
     * other than those requested, i.e. protects against phishing.
     * </p>
     * <p>
     * <b>Example:</b> <code>isAuthorized("GetTax,PostTax")</code>
     * </p>
     *
     * @param operations a comma-delimited list of operation names.
     * @return a {@link IsAuthorizedResult} object
     * @throws java.rmi.RemoteException
     */
    public com.avalara.avatax.services.tax.IsAuthorizedResult isAuthorized(java.lang.String operations) throws java.rmi.RemoteException;

    /**
     * * <p>
     * The AdjustTax operation allows user to Adjust document on the AvaTax system.
     * Adjustment is allowed for Commited documents. If Document status is {@link GetTaxResult#locked}  then system will not process any AdjustTax call. A valid {@link AdjustTaxRequest#adjustmentReason} is required for Adjusting a document.
     */
    public com.avalara.avatax.services.tax.AdjustTaxResult adjustTax(com.avalara.avatax.services.tax.AdjustTaxRequest adjustTaxRequest) throws java.rmi.RemoteException;

    /**
     * This method is used to apply a payment to a document for cash basis accounting. Applies a payment date to an existing invoice
     * <p>
     * It sets the document PaymentDate and changes the reporting date from the DocDate default. It may be called before or after a document is committed. It should not be used for accrual basis accounting
     * </p>
     * @param applyPaymentRequest
     * @return a {@link ApplyPaymentResult} object
     * @throws java.rmi.RemoteException
     */
    public com.avalara.avatax.services.tax.ApplyPaymentResult applyPayment(com.avalara.avatax.services.tax.ApplyPaymentRequest applyPaymentRequest) throws java.rmi.RemoteException;


}

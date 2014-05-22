/*
 * OrderCreditCardDescData.java
 *
 * Created on November 2, 2004, 11:05 AM
 */

package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.ValueObject;
import java.math.BigDecimal;
/**
 *
 * @author  bstevens
 */
public class OrderCreditCardDescData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 769210569596080842L;
    
    /**
     * Holds value of property orderCreditCardData.
     */
    private OrderCreditCardData orderCreditCardData;
    
    /**
     * Holds value of property orderData.
     */
    private OrderData orderData;
    
    /**
     * Holds value of property invoiceCustData.
     */
    private InvoiceCustData invoiceCustData;
    
    /**
     * Holds value of property approvalTransactionData.
     */
    private CreditCardTransData approvalTransactionData;
    
    /**
     * Holds value of property invoiceTransactionData.
     */
    private CreditCardTransData invoiceTransactionData;
    
    /**
     * Holds value of property transactionHistory.
     */
    private CreditCardTransDataVector transactionHistory;
    
    /** Creates a new instance of OrderCreditCardDescData */
    public OrderCreditCardDescData() {
    }
    
    /**
     * Getter for property orderCreditCardData.
     * @return Value of property orderCreditCardData.
     */
    public OrderCreditCardData getOrderCreditCardData() {
        return this.orderCreditCardData;
    }
    
    /**
     * Setter for property orderCreditCardData.
     * @param orderCreditCardData New value of property orderCreditCardData.
     */
    public void setOrderCreditCardData(OrderCreditCardData orderCreditCardData) {
        this.orderCreditCardData = orderCreditCardData;
    }
    
    /**
     * Getter for property orderData.
     * @return Value of property orderData.
     */
    public OrderData getOrderData() {
        return this.orderData;
    }
    
    /**
     * Setter for property orderData.
     * @param orderData New value of property orderData.
     */
    public void setOrderData(OrderData orderData) {
        this.orderData = orderData;
    }
    
    /**
     * Getter for property invoiceCustData.
     * @return Value of property invoiceCustData.
     */
    public InvoiceCustData getInvoiceCustData() {
        return this.invoiceCustData;
    }
    
    /**
     * Setter for property invoiceCustData.
     * @param invoiceCustData New value of property invoiceCustData.
     */
    public void setInvoiceCustData(InvoiceCustData invoiceCustData) {
        this.invoiceCustData = invoiceCustData;
    }
    
    /**
     * Getter for property authTransactionData.
     * @return Value of property authTransactionData.
     */
    public CreditCardTransData getApprovalTransactionData() {
        return this.approvalTransactionData;
    }
    
    /**
     * Setter for property authTransactionData.
     * @param authTransactionData New value of property authTransactionData.
     */
    public void setApprovalTransactionData(CreditCardTransData approvalTransactionData) {
        this.approvalTransactionData = approvalTransactionData;
    }
    
    /**
     * Getter for property invoiceTransactionData.
     * @return Value of property invoiceTransactionData.
     */
    public CreditCardTransData getInvoiceTransactionData() {
        return this.invoiceTransactionData;
    }
    
    /**
     * Setter for property invoiceTransactionData.
     * @param invoiceTransactionData New value of property invoiceTransactionData.
     */
    public void setInvoiceTransactionData(CreditCardTransData invoiceTransactionData) {
        this.invoiceTransactionData = invoiceTransactionData;
    }
    
    /**
     * Getter for property transactionHistory.
     * @return Value of property transactionHistory.
     */
    public CreditCardTransDataVector getTransactionHistory() {
        return this.transactionHistory;
    }
    
    /**
     * Setter for property transactionHistory.
     * @param transactionHistory New value of property transactionHistory.
     */
    public void setTransactionHistory(CreditCardTransDataVector transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
    
}

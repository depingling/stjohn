/*
 * invoiceCustOpSearchForm.java
 *
 * Created on July 25, 2003, 10:01 PM
 */

package com.cleanwise.view.forms;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

/**
 *
 * @author  bstevens
 */
public class InvoiceCustOpSearchForm extends ActionForm {
    
    /** Holds value of property invoiceDateRangeBegin. */
    private String invoiceDateRangeBegin;
    
    /** Holds value of property accountId. */
    private String accountId;
    
    /** Holds value of property invoiceDateRangeEnd. */
    private String invoiceDateRangeEnd;
    
    /** Holds value of property resultList. */
    private InvoiceCustViewVector resultList;
    
    /** Holds value of property invoiceNumRangeBegin. */
    private String invoiceNumRangeBegin;
    
    /** Holds value of property invoiceNumRangeEnd. */
    private String invoiceNumRangeEnd;
    
    /** Creates a new instance of invoiceCustOpSearchForm */
    public InvoiceCustOpSearchForm() {
    }
    
    /** Getter for property invoiceDateRangeBegin.
     * @return Value of property invoiceDateRangeBegin.
     *
     */
    public String getInvoiceDateRangeBegin() {
        return this.invoiceDateRangeBegin;
    }
    
    /** Setter for property invoiceDateRangeBegin.
     * @param invoiceDateRangeBegin New value of property invoiceDateRangeBegin.
     *
     */
    public void setInvoiceDateRangeBegin(String invoiceDateRangeBegin) {
        this.invoiceDateRangeBegin = invoiceDateRangeBegin;
    }
    
    /** Getter for property accountId.
     * @return Value of property accountId.
     *
     */
    public String getAccountId() {
        return this.accountId;
    }
    
    /** Setter for property accountId.
     * @param accountId New value of property accountId.
     *
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    
    /** Getter for property invoiceoDateRangeEnd.
     * @return Value of property invoiceoDateRangeEnd.
     *
     */
    public String getInvoiceDateRangeEnd() {
        return this.invoiceDateRangeEnd;
    }
    
    /** Setter for property invoiceoDateRangeEnd.
     * @param invoiceoDateRangeEnd New value of property invoiceoDateRangeEnd.
     *
     */
    public void setInvoiceDateRangeEnd(String invoiceDateRangeEnd) {
        this.invoiceDateRangeEnd = invoiceDateRangeEnd;
    }
    
    /** Getter for property resultList.
     * @return Value of property resultList.
     *
     */
    public InvoiceCustViewVector getResultList() {
        return this.resultList;
    }
    
    /** Setter for property resultList.
     * @param resultList New value of property resultList.
     *
     */
    public void setResultList(InvoiceCustViewVector resultList) {
        this.resultList = resultList;
    }
    
    /** Getter for property invoicNumStart.
     * @return Value of property invoicNumStart.
     *
     */
    public String getInvoiceNumRangeBegin() {
        return this.invoiceNumRangeBegin;
    }
    
    /** Setter for property invoicNumStart.
     * @param invoicNumStart New value of property invoicNumStart.
     *
     */
    public void setInvoiceNumRangeBegin(String invoiceNumRangeBegin) {
        this.invoiceNumRangeBegin = invoiceNumRangeBegin;
    }
    
    /** Getter for property invoiceNumEnd.
     * @return Value of property invoiceNumEnd.
     *
     */
    public String getInvoiceNumRangeEnd() {
        return this.invoiceNumRangeEnd;
    }
    
    /** Setter for property invoiceNumEnd.
     * @param invoiceNumEnd New value of property invoiceNumEnd.
     *
     */
    public void setInvoiceNumRangeEnd(String invoiceNumRangeEnd) {
        this.invoiceNumRangeEnd = invoiceNumRangeEnd;
    }

    private int mWebOrderNum;
    public int getWebOrderNum() {
        return (this.mWebOrderNum);
    }

    public void setWebOrderNum(int pVal) {
        this.mWebOrderNum = pVal;
    }

    
}

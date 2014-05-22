/*
 * InvoiceCustOpDetailForm.java
 *
 * Created on July 27, 2003, 1:27 AM
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
public class InvoiceCustOpDetailForm extends ActionForm {
    
    /** Holds value of property invoiceCustView. */
    private InvoiceCustView invoiceCustView;
    
    /** Creates a new instance of InvoiceCustOpDetailForm */
    public InvoiceCustOpDetailForm() {
    }
    
    /** Getter for property invoiceCustView.
     * @return Value of property invoiceCustView.
     *
     */
    public InvoiceCustView getInvoiceCustView() {
        return this.invoiceCustView;
    }
    
    /** Setter for property invoiceCustView.
     * @param invoiceCustView New value of property invoiceCustView.
     *
     */
    public void setInvoiceCustView(InvoiceCustView invoiceCustView) {
        this.invoiceCustView = invoiceCustView;
    }
    
}

package com.cleanwise.service.api.reporting;

/**
 * Simple subclass of JciCustomerInvoiceReport to tell it that this is the detail version.
 * @author  bstevens
 */
public class JciCustomerInvoiceDetailReport extends JciCustomerInvoiceReport{
    
    /** Creates a new instance of JciCustomerInvoiceDetailReport */
    public JciCustomerInvoiceDetailReport() {
        reportDetailLevel = DETAIL_LEVEL;
    }
    
}

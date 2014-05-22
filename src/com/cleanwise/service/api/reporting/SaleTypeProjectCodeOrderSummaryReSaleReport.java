/*
 * SaleTypeProjectCodeOrderSummaryReSaleReport.java
 *
 * Created on November 5, 2003, 3:28 PM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.util.RefCodeNames;
/**
 *
 * @author  bstevens
 */
public class SaleTypeProjectCodeOrderSummaryReSaleReport extends SaleTypeProjectCodeOrderSummaryReportSuper{
    
    /** Creates a new instance of SaleTypeProjectCodeOrderSummaryReSaleReport */
    public SaleTypeProjectCodeOrderSummaryReSaleReport() {
    }
    
    String getSaleType(java.util.Map pParams) {
        return RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE;
    }
    
}


/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        InvoiceCustDetailDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_INVOICE_CUST_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.InvoiceCustDetailDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>InvoiceCustDetailDataMeta</code> is a meta class describing the database table object CLW_INVOICE_CUST_DETAIL.
 */
public class InvoiceCustDetailDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public InvoiceCustDetailDataMeta(TableObject pData) {
        
        super(InvoiceCustDetailDataAccess.CLW_INVOICE_CUST_DETAIL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(InvoiceCustDetailDataAccess.INVOICE_CUST_DETAIL_ID);
            fm.setValue(pData.getFieldValue(InvoiceCustDetailDataAccess.INVOICE_CUST_DETAIL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public InvoiceCustDetailDataMeta(TableField... fields) {
        
        super(InvoiceCustDetailDataAccess.CLW_INVOICE_CUST_DETAIL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

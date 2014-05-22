
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        InvoiceCustReengDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_INVOICE_CUST_REENG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.InvoiceCustReengDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>InvoiceCustReengDataMeta</code> is a meta class describing the database table object CLW_INVOICE_CUST_REENG.
 */
public class InvoiceCustReengDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public InvoiceCustReengDataMeta(TableObject pData) {
        
        super(InvoiceCustReengDataAccess.CLW_INVOICE_CUST_REENG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(InvoiceCustReengDataAccess.INVOICE_CUST_REENG_ID);
            fm.setValue(pData.getFieldValue(InvoiceCustReengDataAccess.INVOICE_CUST_REENG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public InvoiceCustReengDataMeta(TableField... fields) {
        
        super(InvoiceCustReengDataAccess.CLW_INVOICE_CUST_REENG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

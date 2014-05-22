
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        InvoiceCustDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_INVOICE_CUST.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.InvoiceCustDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>InvoiceCustDataMeta</code> is a meta class describing the database table object CLW_INVOICE_CUST.
 */
public class InvoiceCustDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public InvoiceCustDataMeta(TableObject pData) {
        
        super(InvoiceCustDataAccess.CLW_INVOICE_CUST);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(InvoiceCustDataAccess.INVOICE_CUST_ID);
            fm.setValue(pData.getFieldValue(InvoiceCustDataAccess.INVOICE_CUST_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public InvoiceCustDataMeta(TableField... fields) {
        
        super(InvoiceCustDataAccess.CLW_INVOICE_CUST);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

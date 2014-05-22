
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        InvoiceDistDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_INVOICE_DIST.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>InvoiceDistDataMeta</code> is a meta class describing the database table object CLW_INVOICE_DIST.
 */
public class InvoiceDistDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public InvoiceDistDataMeta(TableObject pData) {
        
        super(InvoiceDistDataAccess.CLW_INVOICE_DIST);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(InvoiceDistDataAccess.INVOICE_DIST_ID);
            fm.setValue(pData.getFieldValue(InvoiceDistDataAccess.INVOICE_DIST_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public InvoiceDistDataMeta(TableField... fields) {
        
        super(InvoiceDistDataAccess.CLW_INVOICE_DIST);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

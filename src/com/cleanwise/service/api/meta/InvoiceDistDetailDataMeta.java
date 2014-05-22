
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        InvoiceDistDetailDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_INVOICE_DIST_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.InvoiceDistDetailDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>InvoiceDistDetailDataMeta</code> is a meta class describing the database table object CLW_INVOICE_DIST_DETAIL.
 */
public class InvoiceDistDetailDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public InvoiceDistDetailDataMeta(TableObject pData) {
        
        super(InvoiceDistDetailDataAccess.CLW_INVOICE_DIST_DETAIL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(InvoiceDistDetailDataAccess.INVOICE_DIST_DETAIL_ID);
            fm.setValue(pData.getFieldValue(InvoiceDistDetailDataAccess.INVOICE_DIST_DETAIL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public InvoiceDistDetailDataMeta(TableField... fields) {
        
        super(InvoiceDistDetailDataAccess.CLW_INVOICE_DIST_DETAIL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

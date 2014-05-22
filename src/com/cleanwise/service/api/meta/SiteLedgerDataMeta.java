
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        SiteLedgerDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_SITE_LEDGER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.SiteLedgerDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>SiteLedgerDataMeta</code> is a meta class describing the database table object CLW_SITE_LEDGER.
 */
public class SiteLedgerDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public SiteLedgerDataMeta(TableObject pData) {
        
        super(SiteLedgerDataAccess.CLW_SITE_LEDGER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(SiteLedgerDataAccess.SITE_LEDGER_ID);
            fm.setValue(pData.getFieldValue(SiteLedgerDataAccess.SITE_LEDGER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public SiteLedgerDataMeta(TableField... fields) {
        
        super(SiteLedgerDataAccess.CLW_SITE_LEDGER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

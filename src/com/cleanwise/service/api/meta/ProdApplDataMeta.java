
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ProdApplDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PROD_APPL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ProdApplDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ProdApplDataMeta</code> is a meta class describing the database table object CLW_PROD_APPL.
 */
public class ProdApplDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ProdApplDataMeta(TableObject pData) {
        
        super(ProdApplDataAccess.CLW_PROD_APPL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ProdApplDataAccess.PROD_APPL_ID);
            fm.setValue(pData.getFieldValue(ProdApplDataAccess.PROD_APPL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ProdApplDataMeta(TableField... fields) {
        
        super(ProdApplDataAccess.CLW_PROD_APPL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

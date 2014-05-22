
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        BlanketPoNumDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_BLANKET_PO_NUM.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.BlanketPoNumDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>BlanketPoNumDataMeta</code> is a meta class describing the database table object CLW_BLANKET_PO_NUM.
 */
public class BlanketPoNumDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public BlanketPoNumDataMeta(TableObject pData) {
        
        super(BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(BlanketPoNumDataAccess.BLANKET_PO_NUM_ID);
            fm.setValue(pData.getFieldValue(BlanketPoNumDataAccess.BLANKET_PO_NUM_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public BlanketPoNumDataMeta(TableField... fields) {
        
        super(BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

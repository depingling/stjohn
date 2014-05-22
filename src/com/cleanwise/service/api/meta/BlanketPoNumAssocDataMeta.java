
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        BlanketPoNumAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_BLANKET_PO_NUM_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.BlanketPoNumAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>BlanketPoNumAssocDataMeta</code> is a meta class describing the database table object CLW_BLANKET_PO_NUM_ASSOC.
 */
public class BlanketPoNumAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public BlanketPoNumAssocDataMeta(TableObject pData) {
        
        super(BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(BlanketPoNumAssocDataAccess.BLANKET_PO_NUM_ASSOC_ID);
            fm.setValue(pData.getFieldValue(BlanketPoNumAssocDataAccess.BLANKET_PO_NUM_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public BlanketPoNumAssocDataMeta(TableField... fields) {
        
        super(BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    


/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        BusEntityAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_BUS_ENTITY_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>BusEntityAssocDataMeta</code> is a meta class describing the database table object CLW_BUS_ENTITY_ASSOC.
 */
public class BusEntityAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public BusEntityAssocDataMeta(TableObject pData) {
        
        super(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_ID);
            fm.setValue(pData.getFieldValue(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public BusEntityAssocDataMeta(TableField... fields) {
        
        super(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

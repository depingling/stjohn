
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        BusEntityDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_BUS_ENTITY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>BusEntityDataMeta</code> is a meta class describing the database table object CLW_BUS_ENTITY.
 */
public class BusEntityDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public BusEntityDataMeta(TableObject pData) {
        
        super(BusEntityDataAccess.CLW_BUS_ENTITY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(BusEntityDataAccess.BUS_ENTITY_ID);
            fm.setValue(pData.getFieldValue(BusEntityDataAccess.BUS_ENTITY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public BusEntityDataMeta(TableField... fields) {
        
        super(BusEntityDataAccess.CLW_BUS_ENTITY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    


/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        BusEntityGroupDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_BUS_ENTITY_GROUP.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.BusEntityGroupDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>BusEntityGroupDataMeta</code> is a meta class describing the database table object CLW_BUS_ENTITY_GROUP.
 */
public class BusEntityGroupDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public BusEntityGroupDataMeta(TableObject pData) {
        
        super(BusEntityGroupDataAccess.CLW_BUS_ENTITY_GROUP);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(BusEntityGroupDataAccess.BUS_ENTITY_GROUP_ID);
            fm.setValue(pData.getFieldValue(BusEntityGroupDataAccess.BUS_ENTITY_GROUP_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public BusEntityGroupDataMeta(TableField... fields) {
        
        super(BusEntityGroupDataAccess.CLW_BUS_ENTITY_GROUP);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

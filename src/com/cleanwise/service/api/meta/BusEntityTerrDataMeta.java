
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        BusEntityTerrDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_BUS_ENTITY_TERR.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.BusEntityTerrDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>BusEntityTerrDataMeta</code> is a meta class describing the database table object CLW_BUS_ENTITY_TERR.
 */
public class BusEntityTerrDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public BusEntityTerrDataMeta(TableObject pData) {
        
        super(BusEntityTerrDataAccess.CLW_BUS_ENTITY_TERR);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(BusEntityTerrDataAccess.BUS_ENTITY_TERR_ID);
            fm.setValue(pData.getFieldValue(BusEntityTerrDataAccess.BUS_ENTITY_TERR_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public BusEntityTerrDataMeta(TableField... fields) {
        
        super(BusEntityTerrDataAccess.CLW_BUS_ENTITY_TERR);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

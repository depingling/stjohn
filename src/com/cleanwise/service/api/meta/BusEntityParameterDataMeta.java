
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        BusEntityParameterDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_BUS_ENTITY_PARAMETER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.BusEntityParameterDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>BusEntityParameterDataMeta</code> is a meta class describing the database table object CLW_BUS_ENTITY_PARAMETER.
 */
public class BusEntityParameterDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public BusEntityParameterDataMeta(TableObject pData) {
        
        super(BusEntityParameterDataAccess.CLW_BUS_ENTITY_PARAMETER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(BusEntityParameterDataAccess.BUS_ENTITY_PARAMETER_ID);
            fm.setValue(pData.getFieldValue(BusEntityParameterDataAccess.BUS_ENTITY_PARAMETER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public BusEntityParameterDataMeta(TableField... fields) {
        
        super(BusEntityParameterDataAccess.CLW_BUS_ENTITY_PARAMETER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

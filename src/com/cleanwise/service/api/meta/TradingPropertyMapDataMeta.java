
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        TradingPropertyMapDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_TRADING_PROPERTY_MAP.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.TradingPropertyMapDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>TradingPropertyMapDataMeta</code> is a meta class describing the database table object CLW_TRADING_PROPERTY_MAP.
 */
public class TradingPropertyMapDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public TradingPropertyMapDataMeta(TableObject pData) {
        
        super(TradingPropertyMapDataAccess.CLW_TRADING_PROPERTY_MAP);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(TradingPropertyMapDataAccess.TRADING_PROPERTY_MAP_ID);
            fm.setValue(pData.getFieldValue(TradingPropertyMapDataAccess.TRADING_PROPERTY_MAP_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public TradingPropertyMapDataMeta(TableField... fields) {
        
        super(TradingPropertyMapDataAccess.CLW_TRADING_PROPERTY_MAP);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

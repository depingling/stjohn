
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        TradingProfileConfigDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_TRADING_PROFILE_CONFIG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.TradingProfileConfigDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>TradingProfileConfigDataMeta</code> is a meta class describing the database table object CLW_TRADING_PROFILE_CONFIG.
 */
public class TradingProfileConfigDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public TradingProfileConfigDataMeta(TableObject pData) {
        
        super(TradingProfileConfigDataAccess.CLW_TRADING_PROFILE_CONFIG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(TradingProfileConfigDataAccess.TRADING_PROFILE_CONFIG_ID);
            fm.setValue(pData.getFieldValue(TradingProfileConfigDataAccess.TRADING_PROFILE_CONFIG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public TradingProfileConfigDataMeta(TableField... fields) {
        
        super(TradingProfileConfigDataAccess.CLW_TRADING_PROFILE_CONFIG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

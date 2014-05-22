
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        TradingProfileDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_TRADING_PROFILE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.TradingProfileDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>TradingProfileDataMeta</code> is a meta class describing the database table object CLW_TRADING_PROFILE.
 */
public class TradingProfileDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public TradingProfileDataMeta(TableObject pData) {
        
        super(TradingProfileDataAccess.CLW_TRADING_PROFILE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(TradingProfileDataAccess.TRADING_PROFILE_ID);
            fm.setValue(pData.getFieldValue(TradingProfileDataAccess.TRADING_PROFILE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public TradingProfileDataMeta(TableField... fields) {
        
        super(TradingProfileDataAccess.CLW_TRADING_PROFILE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

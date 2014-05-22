
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        TradingPartnerDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_TRADING_PARTNER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.TradingPartnerDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>TradingPartnerDataMeta</code> is a meta class describing the database table object CLW_TRADING_PARTNER.
 */
public class TradingPartnerDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public TradingPartnerDataMeta(TableObject pData) {
        
        super(TradingPartnerDataAccess.CLW_TRADING_PARTNER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(TradingPartnerDataAccess.TRADING_PARTNER_ID);
            fm.setValue(pData.getFieldValue(TradingPartnerDataAccess.TRADING_PARTNER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public TradingPartnerDataMeta(TableField... fields) {
        
        super(TradingPartnerDataAccess.CLW_TRADING_PARTNER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

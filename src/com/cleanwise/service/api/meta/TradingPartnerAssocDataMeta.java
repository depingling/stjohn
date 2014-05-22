
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        TradingPartnerAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_TRADING_PARTNER_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.TradingPartnerAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>TradingPartnerAssocDataMeta</code> is a meta class describing the database table object CLW_TRADING_PARTNER_ASSOC.
 */
public class TradingPartnerAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public TradingPartnerAssocDataMeta(TableObject pData) {
        
        super(TradingPartnerAssocDataAccess.CLW_TRADING_PARTNER_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_ID);
            fm.setValue(pData.getFieldValue(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public TradingPartnerAssocDataMeta(TableField... fields) {
        
        super(TradingPartnerAssocDataAccess.CLW_TRADING_PARTNER_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

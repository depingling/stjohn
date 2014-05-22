
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CurrencyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CURRENCY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CurrencyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CurrencyDataMeta</code> is a meta class describing the database table object CLW_CURRENCY.
 */
public class CurrencyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CurrencyDataMeta(TableObject pData) {
        
        super(CurrencyDataAccess.CLW_CURRENCY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CurrencyDataAccess.CURRENCY_ID);
            fm.setValue(pData.getFieldValue(CurrencyDataAccess.CURRENCY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CurrencyDataMeta(TableField... fields) {
        
        super(CurrencyDataAccess.CLW_CURRENCY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

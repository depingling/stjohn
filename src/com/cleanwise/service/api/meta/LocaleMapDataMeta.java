
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        LocaleMapDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_LOCALE_MAP.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.LocaleMapDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>LocaleMapDataMeta</code> is a meta class describing the database table object CLW_LOCALE_MAP.
 */
public class LocaleMapDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public LocaleMapDataMeta(TableObject pData) {
        
        super(LocaleMapDataAccess.CLW_LOCALE_MAP);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(LocaleMapDataAccess.LOCALE_MAP_ID);
            fm.setValue(pData.getFieldValue(LocaleMapDataAccess.LOCALE_MAP_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public LocaleMapDataMeta(TableField... fields) {
        
        super(LocaleMapDataAccess.CLW_LOCALE_MAP);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

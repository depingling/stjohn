
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        LocaleDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_LOCALE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.LocaleDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>LocaleDataMeta</code> is a meta class describing the database table object CLW_LOCALE.
 */
public class LocaleDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public LocaleDataMeta(TableObject pData) {
        
        super(LocaleDataAccess.CLW_LOCALE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(LocaleDataAccess.LOCALE_ID);
            fm.setValue(pData.getFieldValue(LocaleDataAccess.LOCALE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public LocaleDataMeta(TableField... fields) {
        
        super(LocaleDataAccess.CLW_LOCALE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

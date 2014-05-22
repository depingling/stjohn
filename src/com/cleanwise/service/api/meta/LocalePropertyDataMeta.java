
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        LocalePropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_LOCALE_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.LocalePropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>LocalePropertyDataMeta</code> is a meta class describing the database table object CLW_LOCALE_PROPERTY.
 */
public class LocalePropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public LocalePropertyDataMeta(TableObject pData) {
        
        super(LocalePropertyDataAccess.CLW_LOCALE_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(LocalePropertyDataAccess.LOCALE_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(LocalePropertyDataAccess.LOCALE_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public LocalePropertyDataMeta(TableField... fields) {
        
        super(LocalePropertyDataAccess.CLW_LOCALE_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

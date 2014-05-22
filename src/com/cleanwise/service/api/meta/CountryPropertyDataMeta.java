
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CountryPropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_COUNTRY_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CountryPropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CountryPropertyDataMeta</code> is a meta class describing the database table object CLW_COUNTRY_PROPERTY.
 */
public class CountryPropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CountryPropertyDataMeta(TableObject pData) {
        
        super(CountryPropertyDataAccess.CLW_COUNTRY_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CountryPropertyDataAccess.COUNTRY_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(CountryPropertyDataAccess.COUNTRY_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CountryPropertyDataMeta(TableField... fields) {
        
        super(CountryPropertyDataAccess.CLW_COUNTRY_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

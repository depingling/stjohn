
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CountryDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_COUNTRY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CountryDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CountryDataMeta</code> is a meta class describing the database table object CLW_COUNTRY.
 */
public class CountryDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CountryDataMeta(TableObject pData) {
        
        super(CountryDataAccess.CLW_COUNTRY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CountryDataAccess.COUNTRY_ID);
            fm.setValue(pData.getFieldValue(CountryDataAccess.COUNTRY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CountryDataMeta(TableField... fields) {
        
        super(CountryDataAccess.CLW_COUNTRY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

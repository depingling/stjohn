
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CityPostalCodeDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CITY_POSTAL_CODE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CityPostalCodeDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CityPostalCodeDataMeta</code> is a meta class describing the database table object CLW_CITY_POSTAL_CODE.
 */
public class CityPostalCodeDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CityPostalCodeDataMeta(TableObject pData) {
        
        super(CityPostalCodeDataAccess.CLW_CITY_POSTAL_CODE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CityPostalCodeDataAccess.CITY_POSTAL_CODE_ID);
            fm.setValue(pData.getFieldValue(CityPostalCodeDataAccess.CITY_POSTAL_CODE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CityPostalCodeDataMeta(TableField... fields) {
        
        super(CityPostalCodeDataAccess.CLW_CITY_POSTAL_CODE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

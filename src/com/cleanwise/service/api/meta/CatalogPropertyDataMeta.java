
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CatalogPropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CATALOG_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CatalogPropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CatalogPropertyDataMeta</code> is a meta class describing the database table object CLW_CATALOG_PROPERTY.
 */
public class CatalogPropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CatalogPropertyDataMeta(TableObject pData) {
        
        super(CatalogPropertyDataAccess.CLW_CATALOG_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CatalogPropertyDataAccess.CATALOG_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(CatalogPropertyDataAccess.CATALOG_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CatalogPropertyDataMeta(TableField... fields) {
        
        super(CatalogPropertyDataAccess.CLW_CATALOG_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

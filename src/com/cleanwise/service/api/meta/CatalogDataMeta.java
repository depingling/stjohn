
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CatalogDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CATALOG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CatalogDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CatalogDataMeta</code> is a meta class describing the database table object CLW_CATALOG.
 */
public class CatalogDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CatalogDataMeta(TableObject pData) {
        
        super(CatalogDataAccess.CLW_CATALOG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CatalogDataAccess.CATALOG_ID);
            fm.setValue(pData.getFieldValue(CatalogDataAccess.CATALOG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CatalogDataMeta(TableField... fields) {
        
        super(CatalogDataAccess.CLW_CATALOG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

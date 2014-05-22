
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CatalogAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CATALOG_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CatalogAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CatalogAssocDataMeta</code> is a meta class describing the database table object CLW_CATALOG_ASSOC.
 */
public class CatalogAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CatalogAssocDataMeta(TableObject pData) {
        
        super(CatalogAssocDataAccess.CLW_CATALOG_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CatalogAssocDataAccess.CATALOG_ASSOC_ID);
            fm.setValue(pData.getFieldValue(CatalogAssocDataAccess.CATALOG_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CatalogAssocDataMeta(TableField... fields) {
        
        super(CatalogAssocDataAccess.CLW_CATALOG_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

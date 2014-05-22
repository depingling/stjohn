
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CatalogStructureDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CATALOG_STRUCTURE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CatalogStructureDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CatalogStructureDataMeta</code> is a meta class describing the database table object CLW_CATALOG_STRUCTURE.
 */
public class CatalogStructureDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CatalogStructureDataMeta(TableObject pData) {
        
        super(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CatalogStructureDataAccess.CATALOG_STRUCTURE_ID);
            fm.setValue(pData.getFieldValue(CatalogStructureDataAccess.CATALOG_STRUCTURE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CatalogStructureDataMeta(TableField... fields) {
        
        super(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

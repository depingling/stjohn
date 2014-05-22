
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ProductUomPackDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PRODUCT_UOM_PACK.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ProductUomPackDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ProductUomPackDataMeta</code> is a meta class describing the database table object CLW_PRODUCT_UOM_PACK.
 */
public class ProductUomPackDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ProductUomPackDataMeta(TableObject pData) {
        
        super(ProductUomPackDataAccess.CLW_PRODUCT_UOM_PACK);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ProductUomPackDataAccess.PRODUCT_UOM_PACK_ID);
            fm.setValue(pData.getFieldValue(ProductUomPackDataAccess.PRODUCT_UOM_PACK_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ProductUomPackDataMeta(TableField... fields) {
        
        super(ProductUomPackDataAccess.CLW_PRODUCT_UOM_PACK);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

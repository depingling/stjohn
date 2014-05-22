
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        AllocatedCategoryDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ALLOCATED_CATEGORY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.AllocatedCategoryDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>AllocatedCategoryDataMeta</code> is a meta class describing the database table object CLW_ALLOCATED_CATEGORY.
 */
public class AllocatedCategoryDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public AllocatedCategoryDataMeta(TableObject pData) {
        
        super(AllocatedCategoryDataAccess.CLW_ALLOCATED_CATEGORY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(AllocatedCategoryDataAccess.ALLOCATED_CATEGORY_ID);
            fm.setValue(pData.getFieldValue(AllocatedCategoryDataAccess.ALLOCATED_CATEGORY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public AllocatedCategoryDataMeta(TableField... fields) {
        
        super(AllocatedCategoryDataAccess.CLW_ALLOCATED_CATEGORY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

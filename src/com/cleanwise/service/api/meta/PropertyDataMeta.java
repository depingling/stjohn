
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PropertyDataMeta</code> is a meta class describing the database table object CLW_PROPERTY.
 */
public class PropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PropertyDataMeta(TableObject pData) {
        
        super(PropertyDataAccess.CLW_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PropertyDataAccess.PROPERTY_ID);
            fm.setValue(pData.getFieldValue(PropertyDataAccess.PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PropertyDataMeta(TableField... fields) {
        
        super(PropertyDataAccess.CLW_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

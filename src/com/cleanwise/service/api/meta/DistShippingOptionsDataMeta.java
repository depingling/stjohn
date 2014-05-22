
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        DistShippingOptionsDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_DIST_SHIPPING_OPTIONS.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.DistShippingOptionsDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>DistShippingOptionsDataMeta</code> is a meta class describing the database table object CLW_DIST_SHIPPING_OPTIONS.
 */
public class DistShippingOptionsDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public DistShippingOptionsDataMeta(TableObject pData) {
        
        super(DistShippingOptionsDataAccess.CLW_DIST_SHIPPING_OPTIONS);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(DistShippingOptionsDataAccess.DIST_SHIPPING_OPTIONS_ID);
            fm.setValue(pData.getFieldValue(DistShippingOptionsDataAccess.DIST_SHIPPING_OPTIONS_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public DistShippingOptionsDataMeta(TableField... fields) {
        
        super(DistShippingOptionsDataAccess.CLW_DIST_SHIPPING_OPTIONS);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

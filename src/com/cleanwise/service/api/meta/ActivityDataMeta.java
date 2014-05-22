
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ActivityDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ACTIVITY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ActivityDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ActivityDataMeta</code> is a meta class describing the database table object CLW_ACTIVITY.
 */
public class ActivityDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ActivityDataMeta(TableObject pData) {
        
        super(ActivityDataAccess.CLW_ACTIVITY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ActivityDataAccess.ACTIVITY_ID);
            fm.setValue(pData.getFieldValue(ActivityDataAccess.ACTIVITY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ActivityDataMeta(TableField... fields) {
        
        super(ActivityDataAccess.CLW_ACTIVITY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

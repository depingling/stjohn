
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        EventPropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_EVENT_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.EventPropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>EventPropertyDataMeta</code> is a meta class describing the database table object CLW_EVENT_PROPERTY.
 */
public class EventPropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public EventPropertyDataMeta(TableObject pData) {
        
        super(EventPropertyDataAccess.CLW_EVENT_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(EventPropertyDataAccess.EVENT_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(EventPropertyDataAccess.EVENT_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public EventPropertyDataMeta(TableField... fields) {
        
        super(EventPropertyDataAccess.CLW_EVENT_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

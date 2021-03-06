
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        EventDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_EVENT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.EventDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>EventDataMeta</code> is a meta class describing the database table object CLW_EVENT.
 */
public class EventDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public EventDataMeta(TableObject pData) {
        
        super(EventDataAccess.CLW_EVENT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(EventDataAccess.EVENT_ID);
            fm.setValue(pData.getFieldValue(EventDataAccess.EVENT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public EventDataMeta(TableField... fields) {
        
        super(EventDataAccess.CLW_EVENT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

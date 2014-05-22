
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        EventEmailDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_EVENT_EMAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.EventEmailDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>EventEmailDataMeta</code> is a meta class describing the database table object CLW_EVENT_EMAIL.
 */
public class EventEmailDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public EventEmailDataMeta(TableObject pData) {
        
        super(EventEmailDataAccess.CLW_EVENT_EMAIL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(EventEmailDataAccess.EVENT_EMAIL_ID);
            fm.setValue(pData.getFieldValue(EventEmailDataAccess.EVENT_EMAIL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public EventEmailDataMeta(TableField... fields) {
        
        super(EventEmailDataAccess.CLW_EVENT_EMAIL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

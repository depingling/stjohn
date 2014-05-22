
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        MessageResourceDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_MESSAGE_RESOURCE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.MessageResourceDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>MessageResourceDataMeta</code> is a meta class describing the database table object CLW_MESSAGE_RESOURCE.
 */
public class MessageResourceDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public MessageResourceDataMeta(TableObject pData) {
        
        super(MessageResourceDataAccess.CLW_MESSAGE_RESOURCE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(MessageResourceDataAccess.MESSAGE_RESOURCE_ID);
            fm.setValue(pData.getFieldValue(MessageResourceDataAccess.MESSAGE_RESOURCE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public MessageResourceDataMeta(TableField... fields) {
        
        super(MessageResourceDataAccess.CLW_MESSAGE_RESOURCE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

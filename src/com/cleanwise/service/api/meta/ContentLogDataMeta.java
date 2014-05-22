
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ContentLogDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CONTENT_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ContentLogDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ContentLogDataMeta</code> is a meta class describing the database table object CLW_CONTENT_LOG.
 */
public class ContentLogDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ContentLogDataMeta(TableObject pData) {
        
        super(ContentLogDataAccess.CLW_CONTENT_LOG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ContentLogDataAccess.CONTENT_LOG_ID);
            fm.setValue(pData.getFieldValue(ContentLogDataAccess.CONTENT_LOG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ContentLogDataMeta(TableField... fields) {
        
        super(ContentLogDataAccess.CLW_CONTENT_LOG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

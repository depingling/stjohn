
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ContentDetailLogDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CONTENT_DETAIL_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ContentDetailLogDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ContentDetailLogDataMeta</code> is a meta class describing the database table object CLW_CONTENT_DETAIL_LOG.
 */
public class ContentDetailLogDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ContentDetailLogDataMeta(TableObject pData) {
        
        super(ContentDetailLogDataAccess.CLW_CONTENT_DETAIL_LOG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ContentDetailLogDataAccess.CONTENT_DETAIL_LOG_ID);
            fm.setValue(pData.getFieldValue(ContentDetailLogDataAccess.CONTENT_DETAIL_LOG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ContentDetailLogDataMeta(TableField... fields) {
        
        super(ContentDetailLogDataAccess.CLW_CONTENT_DETAIL_LOG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

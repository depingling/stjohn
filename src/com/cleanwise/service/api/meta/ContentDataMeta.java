
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ContentDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CONTENT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ContentDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ContentDataMeta</code> is a meta class describing the database table object CLW_CONTENT.
 */
public class ContentDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ContentDataMeta(TableObject pData) {
        
        super(ContentDataAccess.CLW_CONTENT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ContentDataAccess.CONTENT_ID);
            fm.setValue(pData.getFieldValue(ContentDataAccess.CONTENT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ContentDataMeta(TableField... fields) {
        
        super(ContentDataAccess.CLW_CONTENT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

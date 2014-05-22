
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ContentDetailDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CONTENT_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ContentDetailDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ContentDetailDataMeta</code> is a meta class describing the database table object CLW_CONTENT_DETAIL.
 */
public class ContentDetailDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ContentDetailDataMeta(TableObject pData) {
        
        super(ContentDetailDataAccess.CLW_CONTENT_DETAIL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ContentDetailDataAccess.CONTENT_DETAIL_ID);
            fm.setValue(pData.getFieldValue(ContentDetailDataAccess.CONTENT_DETAIL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ContentDetailDataMeta(TableField... fields) {
        
        super(ContentDetailDataAccess.CLW_CONTENT_DETAIL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    


/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        KeywordDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_KEYWORD.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.KeywordDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>KeywordDataMeta</code> is a meta class describing the database table object CLW_KEYWORD.
 */
public class KeywordDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public KeywordDataMeta(TableObject pData) {
        
        super(KeywordDataAccess.CLW_KEYWORD);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(KeywordDataAccess.KEYWORD_ID);
            fm.setValue(pData.getFieldValue(KeywordDataAccess.KEYWORD_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public KeywordDataMeta(TableField... fields) {
        
        super(KeywordDataAccess.CLW_KEYWORD);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

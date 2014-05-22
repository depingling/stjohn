
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        KnowledgeContentDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_KNOWLEDGE_CONTENT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.KnowledgeContentDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>KnowledgeContentDataMeta</code> is a meta class describing the database table object CLW_KNOWLEDGE_CONTENT.
 */
public class KnowledgeContentDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public KnowledgeContentDataMeta(TableObject pData) {
        
        super(KnowledgeContentDataAccess.CLW_KNOWLEDGE_CONTENT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(KnowledgeContentDataAccess.KNOWLEDGE_CONTENT_ID);
            fm.setValue(pData.getFieldValue(KnowledgeContentDataAccess.KNOWLEDGE_CONTENT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public KnowledgeContentDataMeta(TableField... fields) {
        
        super(KnowledgeContentDataAccess.CLW_KNOWLEDGE_CONTENT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

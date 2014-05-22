
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        KnowledgeMetaDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_KNOWLEDGE_META.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.KnowledgeMetaDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>KnowledgeMetaDataMeta</code> is a meta class describing the database table object CLW_KNOWLEDGE_META.
 */
public class KnowledgeMetaDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public KnowledgeMetaDataMeta(TableObject pData) {
        
        super(KnowledgeMetaDataAccess.CLW_KNOWLEDGE_META);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(KnowledgeMetaDataAccess.KNOWLEDGE_META_ID);
            fm.setValue(pData.getFieldValue(KnowledgeMetaDataAccess.KNOWLEDGE_META_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public KnowledgeMetaDataMeta(TableField... fields) {
        
        super(KnowledgeMetaDataAccess.CLW_KNOWLEDGE_META);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

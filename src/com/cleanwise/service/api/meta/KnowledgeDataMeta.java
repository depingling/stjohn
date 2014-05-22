
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        KnowledgeDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_KNOWLEDGE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.KnowledgeDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>KnowledgeDataMeta</code> is a meta class describing the database table object CLW_KNOWLEDGE.
 */
public class KnowledgeDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public KnowledgeDataMeta(TableObject pData) {
        
        super(KnowledgeDataAccess.CLW_KNOWLEDGE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(KnowledgeDataAccess.KNOWLEDGE_ID);
            fm.setValue(pData.getFieldValue(KnowledgeDataAccess.KNOWLEDGE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public KnowledgeDataMeta(TableField... fields) {
        
        super(KnowledgeDataAccess.CLW_KNOWLEDGE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

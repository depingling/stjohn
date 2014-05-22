
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        KnowledgePropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_KNOWLEDGE_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.KnowledgePropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>KnowledgePropertyDataMeta</code> is a meta class describing the database table object CLW_KNOWLEDGE_PROPERTY.
 */
public class KnowledgePropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public KnowledgePropertyDataMeta(TableObject pData) {
        
        super(KnowledgePropertyDataAccess.CLW_KNOWLEDGE_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(KnowledgePropertyDataAccess.KNOWLEDGE_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(KnowledgePropertyDataAccess.KNOWLEDGE_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public KnowledgePropertyDataMeta(TableField... fields) {
        
        super(KnowledgePropertyDataAccess.CLW_KNOWLEDGE_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

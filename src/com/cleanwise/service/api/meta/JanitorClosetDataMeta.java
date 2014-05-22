
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        JanitorClosetDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_JANITOR_CLOSET.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.JanitorClosetDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>JanitorClosetDataMeta</code> is a meta class describing the database table object CLW_JANITOR_CLOSET.
 */
public class JanitorClosetDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public JanitorClosetDataMeta(TableObject pData) {
        
        super(JanitorClosetDataAccess.CLW_JANITOR_CLOSET);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(JanitorClosetDataAccess.JANITOR_CLOSET_ID);
            fm.setValue(pData.getFieldValue(JanitorClosetDataAccess.JANITOR_CLOSET_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public JanitorClosetDataMeta(TableField... fields) {
        
        super(JanitorClosetDataAccess.CLW_JANITOR_CLOSET);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    


/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        InboundDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_INBOUND.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.InboundDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>InboundDataMeta</code> is a meta class describing the database table object CLW_INBOUND.
 */
public class InboundDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public InboundDataMeta(TableObject pData) {
        
        super(InboundDataAccess.CLW_INBOUND);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(InboundDataAccess.INBOUND_ID);
            fm.setValue(pData.getFieldValue(InboundDataAccess.INBOUND_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public InboundDataMeta(TableField... fields) {
        
        super(InboundDataAccess.CLW_INBOUND);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    


/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        FreightTableDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_FREIGHT_TABLE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.FreightTableDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>FreightTableDataMeta</code> is a meta class describing the database table object CLW_FREIGHT_TABLE.
 */
public class FreightTableDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public FreightTableDataMeta(TableObject pData) {
        
        super(FreightTableDataAccess.CLW_FREIGHT_TABLE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(FreightTableDataAccess.FREIGHT_TABLE_ID);
            fm.setValue(pData.getFieldValue(FreightTableDataAccess.FREIGHT_TABLE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public FreightTableDataMeta(TableField... fields) {
        
        super(FreightTableDataAccess.CLW_FREIGHT_TABLE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

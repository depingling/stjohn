
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        InterchangeDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_INTERCHANGE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.InterchangeDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>InterchangeDataMeta</code> is a meta class describing the database table object CLW_INTERCHANGE.
 */
public class InterchangeDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public InterchangeDataMeta(TableObject pData) {
        
        super(InterchangeDataAccess.CLW_INTERCHANGE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(InterchangeDataAccess.INTERCHANGE_ID);
            fm.setValue(pData.getFieldValue(InterchangeDataAccess.INTERCHANGE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public InterchangeDataMeta(TableField... fields) {
        
        super(InterchangeDataAccess.CLW_INTERCHANGE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

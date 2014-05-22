
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        LoggingDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_LOGGING.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.LoggingDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>LoggingDataMeta</code> is a meta class describing the database table object CLW_LOGGING.
 */
public class LoggingDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public LoggingDataMeta(TableObject pData) {
        
        super(LoggingDataAccess.CLW_LOGGING);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(LoggingDataAccess.LOGGING_ID);
            fm.setValue(pData.getFieldValue(LoggingDataAccess.LOGGING_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public LoggingDataMeta(TableField... fields) {
        
        super(LoggingDataAccess.CLW_LOGGING);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

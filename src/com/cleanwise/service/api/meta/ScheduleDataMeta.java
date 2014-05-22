
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ScheduleDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_SCHEDULE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ScheduleDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ScheduleDataMeta</code> is a meta class describing the database table object CLW_SCHEDULE.
 */
public class ScheduleDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ScheduleDataMeta(TableObject pData) {
        
        super(ScheduleDataAccess.CLW_SCHEDULE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ScheduleDataAccess.SCHEDULE_ID);
            fm.setValue(pData.getFieldValue(ScheduleDataAccess.SCHEDULE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ScheduleDataMeta(TableField... fields) {
        
        super(ScheduleDataAccess.CLW_SCHEDULE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

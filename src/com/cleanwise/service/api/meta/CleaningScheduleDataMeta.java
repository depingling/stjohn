
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CleaningScheduleDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CLEANING_SCHEDULE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CleaningScheduleDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CleaningScheduleDataMeta</code> is a meta class describing the database table object CLW_CLEANING_SCHEDULE.
 */
public class CleaningScheduleDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CleaningScheduleDataMeta(TableObject pData) {
        
        super(CleaningScheduleDataAccess.CLW_CLEANING_SCHEDULE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CleaningScheduleDataAccess.CLEANING_SCHEDULE_ID);
            fm.setValue(pData.getFieldValue(CleaningScheduleDataAccess.CLEANING_SCHEDULE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CleaningScheduleDataMeta(TableField... fields) {
        
        super(CleaningScheduleDataAccess.CLW_CLEANING_SCHEDULE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

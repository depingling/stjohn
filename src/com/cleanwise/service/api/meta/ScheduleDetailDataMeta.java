
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ScheduleDetailDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_SCHEDULE_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ScheduleDetailDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ScheduleDetailDataMeta</code> is a meta class describing the database table object CLW_SCHEDULE_DETAIL.
 */
public class ScheduleDetailDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ScheduleDetailDataMeta(TableObject pData) {
        
        super(ScheduleDetailDataAccess.CLW_SCHEDULE_DETAIL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ScheduleDetailDataAccess.SCHEDULE_DETAIL_ID);
            fm.setValue(pData.getFieldValue(ScheduleDetailDataAccess.SCHEDULE_DETAIL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ScheduleDetailDataMeta(TableField... fields) {
        
        super(ScheduleDetailDataAccess.CLW_SCHEDULE_DETAIL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

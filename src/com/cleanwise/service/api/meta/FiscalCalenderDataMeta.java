
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        FiscalCalenderDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_FISCAL_CALENDER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.FiscalCalenderDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>FiscalCalenderDataMeta</code> is a meta class describing the database table object CLW_FISCAL_CALENDER.
 */
public class FiscalCalenderDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public FiscalCalenderDataMeta(TableObject pData) {
        
        super(FiscalCalenderDataAccess.CLW_FISCAL_CALENDER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(FiscalCalenderDataAccess.FISCAL_CALENDER_ID);
            fm.setValue(pData.getFieldValue(FiscalCalenderDataAccess.FISCAL_CALENDER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public FiscalCalenderDataMeta(TableField... fields) {
        
        super(FiscalCalenderDataAccess.CLW_FISCAL_CALENDER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

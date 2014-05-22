
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        GenericReportDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_GENERIC_REPORT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.GenericReportDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>GenericReportDataMeta</code> is a meta class describing the database table object CLW_GENERIC_REPORT.
 */
public class GenericReportDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public GenericReportDataMeta(TableObject pData) {
        
        super(GenericReportDataAccess.CLW_GENERIC_REPORT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(GenericReportDataAccess.GENERIC_REPORT_ID);
            fm.setValue(pData.getFieldValue(GenericReportDataAccess.GENERIC_REPORT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public GenericReportDataMeta(TableField... fields) {
        
        super(GenericReportDataAccess.CLW_GENERIC_REPORT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

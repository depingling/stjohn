
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ReportExchangeLogDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_REPORT_EXCHANGE_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ReportExchangeLogDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ReportExchangeLogDataMeta</code> is a meta class describing the database table object CLW_REPORT_EXCHANGE_LOG.
 */
public class ReportExchangeLogDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ReportExchangeLogDataMeta(TableObject pData) {
        
        super(ReportExchangeLogDataAccess.CLW_REPORT_EXCHANGE_LOG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ReportExchangeLogDataAccess.REPORT_EXCHANGE_LOG_ID);
            fm.setValue(pData.getFieldValue(ReportExchangeLogDataAccess.REPORT_EXCHANGE_LOG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ReportExchangeLogDataMeta(TableField... fields) {
        
        super(ReportExchangeLogDataAccess.CLW_REPORT_EXCHANGE_LOG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

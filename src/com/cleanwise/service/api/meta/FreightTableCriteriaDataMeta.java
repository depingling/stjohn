
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        FreightTableCriteriaDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_FREIGHT_TABLE_CRITERIA.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.FreightTableCriteriaDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>FreightTableCriteriaDataMeta</code> is a meta class describing the database table object CLW_FREIGHT_TABLE_CRITERIA.
 */
public class FreightTableCriteriaDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public FreightTableCriteriaDataMeta(TableObject pData) {
        
        super(FreightTableCriteriaDataAccess.CLW_FREIGHT_TABLE_CRITERIA);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(FreightTableCriteriaDataAccess.FREIGHT_TABLE_CRITERIA_ID);
            fm.setValue(pData.getFieldValue(FreightTableCriteriaDataAccess.FREIGHT_TABLE_CRITERIA_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public FreightTableCriteriaDataMeta(TableField... fields) {
        
        super(FreightTableCriteriaDataAccess.CLW_FREIGHT_TABLE_CRITERIA);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

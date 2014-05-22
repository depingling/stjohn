
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        FacilityPropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_FACILITY_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.FacilityPropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>FacilityPropertyDataMeta</code> is a meta class describing the database table object CLW_FACILITY_PROPERTY.
 */
public class FacilityPropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public FacilityPropertyDataMeta(TableObject pData) {
        
        super(FacilityPropertyDataAccess.CLW_FACILITY_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(FacilityPropertyDataAccess.FACILITY_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(FacilityPropertyDataAccess.FACILITY_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public FacilityPropertyDataMeta(TableField... fields) {
        
        super(FacilityPropertyDataAccess.CLW_FACILITY_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

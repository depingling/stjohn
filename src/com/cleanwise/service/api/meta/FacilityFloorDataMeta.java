
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        FacilityFloorDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_FACILITY_FLOOR.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.FacilityFloorDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>FacilityFloorDataMeta</code> is a meta class describing the database table object CLW_FACILITY_FLOOR.
 */
public class FacilityFloorDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public FacilityFloorDataMeta(TableObject pData) {
        
        super(FacilityFloorDataAccess.CLW_FACILITY_FLOOR);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(FacilityFloorDataAccess.FACILITY_FLOOR_ID);
            fm.setValue(pData.getFieldValue(FacilityFloorDataAccess.FACILITY_FLOOR_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public FacilityFloorDataMeta(TableField... fields) {
        
        super(FacilityFloorDataAccess.CLW_FACILITY_FLOOR);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

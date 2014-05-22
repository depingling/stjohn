
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        EstimatorFacilityDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ESTIMATOR_FACILITY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.EstimatorFacilityDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>EstimatorFacilityDataMeta</code> is a meta class describing the database table object CLW_ESTIMATOR_FACILITY.
 */
public class EstimatorFacilityDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public EstimatorFacilityDataMeta(TableObject pData) {
        
        super(EstimatorFacilityDataAccess.CLW_ESTIMATOR_FACILITY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(EstimatorFacilityDataAccess.ESTIMATOR_FACILITY_ID);
            fm.setValue(pData.getFieldValue(EstimatorFacilityDataAccess.ESTIMATOR_FACILITY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public EstimatorFacilityDataMeta(TableField... fields) {
        
        super(EstimatorFacilityDataAccess.CLW_ESTIMATOR_FACILITY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    


/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        SiteDeliveryDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_SITE_DELIVERY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.SiteDeliveryDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>SiteDeliveryDataMeta</code> is a meta class describing the database table object CLW_SITE_DELIVERY.
 */
public class SiteDeliveryDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public SiteDeliveryDataMeta(TableObject pData) {
        
        super(SiteDeliveryDataAccess.CLW_SITE_DELIVERY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(SiteDeliveryDataAccess.SITE_DELIVERY_ID);
            fm.setValue(pData.getFieldValue(SiteDeliveryDataAccess.SITE_DELIVERY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public SiteDeliveryDataMeta(TableField... fields) {
        
        super(SiteDeliveryDataAccess.CLW_SITE_DELIVERY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

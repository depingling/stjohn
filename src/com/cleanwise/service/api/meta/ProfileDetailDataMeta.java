
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ProfileDetailDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PROFILE_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ProfileDetailDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ProfileDetailDataMeta</code> is a meta class describing the database table object CLW_PROFILE_DETAIL.
 */
public class ProfileDetailDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ProfileDetailDataMeta(TableObject pData) {
        
        super(ProfileDetailDataAccess.CLW_PROFILE_DETAIL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ProfileDetailDataAccess.PROFILE_DETAIL_ID);
            fm.setValue(pData.getFieldValue(ProfileDetailDataAccess.PROFILE_DETAIL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ProfileDetailDataMeta(TableField... fields) {
        
        super(ProfileDetailDataAccess.CLW_PROFILE_DETAIL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

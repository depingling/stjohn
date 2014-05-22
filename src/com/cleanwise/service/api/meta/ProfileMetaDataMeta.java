
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ProfileMetaDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PROFILE_META.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ProfileMetaDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ProfileMetaDataMeta</code> is a meta class describing the database table object CLW_PROFILE_META.
 */
public class ProfileMetaDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ProfileMetaDataMeta(TableObject pData) {
        
        super(ProfileMetaDataAccess.CLW_PROFILE_META);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ProfileMetaDataAccess.PROFILE_META_ID);
            fm.setValue(pData.getFieldValue(ProfileMetaDataAccess.PROFILE_META_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ProfileMetaDataMeta(TableField... fields) {
        
        super(ProfileMetaDataAccess.CLW_PROFILE_META);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

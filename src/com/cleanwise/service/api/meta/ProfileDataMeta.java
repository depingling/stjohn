
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ProfileDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PROFILE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ProfileDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ProfileDataMeta</code> is a meta class describing the database table object CLW_PROFILE.
 */
public class ProfileDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ProfileDataMeta(TableObject pData) {
        
        super(ProfileDataAccess.CLW_PROFILE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ProfileDataAccess.PROFILE_ID);
            fm.setValue(pData.getFieldValue(ProfileDataAccess.PROFILE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ProfileDataMeta(TableField... fields) {
        
        super(ProfileDataAccess.CLW_PROFILE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

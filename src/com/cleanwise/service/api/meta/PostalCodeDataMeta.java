
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PostalCodeDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_POSTAL_CODE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PostalCodeDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PostalCodeDataMeta</code> is a meta class describing the database table object CLW_POSTAL_CODE.
 */
public class PostalCodeDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PostalCodeDataMeta(TableObject pData) {
        
        super(PostalCodeDataAccess.CLW_POSTAL_CODE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PostalCodeDataAccess.POSTAL_CODE_ID);
            fm.setValue(pData.getFieldValue(PostalCodeDataAccess.POSTAL_CODE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PostalCodeDataMeta(TableField... fields) {
        
        super(PostalCodeDataAccess.CLW_POSTAL_CODE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

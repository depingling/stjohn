
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        RefCdDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_REF_CD.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.RefCdDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>RefCdDataMeta</code> is a meta class describing the database table object CLW_REF_CD.
 */
public class RefCdDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public RefCdDataMeta(TableObject pData) {
        
        super(RefCdDataAccess.CLW_REF_CD);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(RefCdDataAccess.REF_CD_ID);
            fm.setValue(pData.getFieldValue(RefCdDataAccess.REF_CD_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public RefCdDataMeta(TableField... fields) {
        
        super(RefCdDataAccess.CLW_REF_CD);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

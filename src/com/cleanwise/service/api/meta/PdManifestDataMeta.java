
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PdManifestDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PD_MANIFEST.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PdManifestDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PdManifestDataMeta</code> is a meta class describing the database table object CLW_PD_MANIFEST.
 */
public class PdManifestDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PdManifestDataMeta(TableObject pData) {
        
        super(PdManifestDataAccess.CLW_PD_MANIFEST);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PdManifestDataAccess.PD_MANIFEST_ID);
            fm.setValue(pData.getFieldValue(PdManifestDataAccess.PD_MANIFEST_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PdManifestDataMeta(TableField... fields) {
        
        super(PdManifestDataAccess.CLW_PD_MANIFEST);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    


/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        Fedstrip058DataMeta
 * Description:  This is a meta class describing the object of database table  CLW_FEDSTRIP_058.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.Fedstrip058DataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>Fedstrip058DataMeta</code> is a meta class describing the database table object CLW_FEDSTRIP_058.
 */
public class Fedstrip058DataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public Fedstrip058DataMeta(TableObject pData) {
        
        super(Fedstrip058DataAccess.CLW_FEDSTRIP_058);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(Fedstrip058DataAccess.FEDSTRIP_058_ID);
            fm.setValue(pData.getFieldValue(Fedstrip058DataAccess.FEDSTRIP_058_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public Fedstrip058DataMeta(TableField... fields) {
        
        super(Fedstrip058DataAccess.CLW_FEDSTRIP_058);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    


/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        Edi997DataMeta
 * Description:  This is a meta class describing the object of database table  CLW_EDI_997.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.Edi997DataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>Edi997DataMeta</code> is a meta class describing the database table object CLW_EDI_997.
 */
public class Edi997DataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public Edi997DataMeta(TableObject pData) {
        
        super(Edi997DataAccess.CLW_EDI_997);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(Edi997DataAccess.EDI_997_ID);
            fm.setValue(pData.getFieldValue(Edi997DataAccess.EDI_997_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public Edi997DataMeta(TableField... fields) {
        
        super(Edi997DataAccess.CLW_EDI_997);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

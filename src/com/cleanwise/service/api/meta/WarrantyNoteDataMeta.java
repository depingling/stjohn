
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WarrantyNoteDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WARRANTY_NOTE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WarrantyNoteDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WarrantyNoteDataMeta</code> is a meta class describing the database table object CLW_WARRANTY_NOTE.
 */
public class WarrantyNoteDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WarrantyNoteDataMeta(TableObject pData) {
        
        super(WarrantyNoteDataAccess.CLW_WARRANTY_NOTE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WarrantyNoteDataAccess.WARRANTY_NOTE_ID);
            fm.setValue(pData.getFieldValue(WarrantyNoteDataAccess.WARRANTY_NOTE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WarrantyNoteDataMeta(TableField... fields) {
        
        super(WarrantyNoteDataAccess.CLW_WARRANTY_NOTE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

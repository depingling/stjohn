
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        NoteDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_NOTE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.NoteDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>NoteDataMeta</code> is a meta class describing the database table object CLW_NOTE.
 */
public class NoteDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public NoteDataMeta(TableObject pData) {
        
        super(NoteDataAccess.CLW_NOTE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(NoteDataAccess.NOTE_ID);
            fm.setValue(pData.getFieldValue(NoteDataAccess.NOTE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public NoteDataMeta(TableField... fields) {
        
        super(NoteDataAccess.CLW_NOTE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

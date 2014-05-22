
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        NoteTextDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_NOTE_TEXT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.NoteTextDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>NoteTextDataMeta</code> is a meta class describing the database table object CLW_NOTE_TEXT.
 */
public class NoteTextDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public NoteTextDataMeta(TableObject pData) {
        
        super(NoteTextDataAccess.CLW_NOTE_TEXT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(NoteTextDataAccess.NOTE_TEXT_ID);
            fm.setValue(pData.getFieldValue(NoteTextDataAccess.NOTE_TEXT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public NoteTextDataMeta(TableField... fields) {
        
        super(NoteTextDataAccess.CLW_NOTE_TEXT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

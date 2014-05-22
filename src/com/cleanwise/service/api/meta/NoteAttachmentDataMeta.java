
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        NoteAttachmentDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_NOTE_ATTACHMENT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.NoteAttachmentDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>NoteAttachmentDataMeta</code> is a meta class describing the database table object CLW_NOTE_ATTACHMENT.
 */
public class NoteAttachmentDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public NoteAttachmentDataMeta(TableObject pData) {
        
        super(NoteAttachmentDataAccess.CLW_NOTE_ATTACHMENT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(NoteAttachmentDataAccess.NOTE_ATTACHMENT_ID);
            fm.setValue(pData.getFieldValue(NoteAttachmentDataAccess.NOTE_ATTACHMENT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public NoteAttachmentDataMeta(TableField... fields) {
        
        super(NoteAttachmentDataAccess.CLW_NOTE_ATTACHMENT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    


/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        UiFrameSlotDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_UI_FRAME_SLOT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.UiFrameSlotDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>UiFrameSlotDataMeta</code> is a meta class describing the database table object CLW_UI_FRAME_SLOT.
 */
public class UiFrameSlotDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public UiFrameSlotDataMeta(TableObject pData) {
        
        super(UiFrameSlotDataAccess.CLW_UI_FRAME_SLOT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(UiFrameSlotDataAccess.UI_FRAME_SLOT_ID);
            fm.setValue(pData.getFieldValue(UiFrameSlotDataAccess.UI_FRAME_SLOT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public UiFrameSlotDataMeta(TableField... fields) {
        
        super(UiFrameSlotDataAccess.CLW_UI_FRAME_SLOT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

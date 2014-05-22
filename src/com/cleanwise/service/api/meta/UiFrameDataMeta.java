
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        UiFrameDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_UI_FRAME.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.UiFrameDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>UiFrameDataMeta</code> is a meta class describing the database table object CLW_UI_FRAME.
 */
public class UiFrameDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public UiFrameDataMeta(TableObject pData) {
        
        super(UiFrameDataAccess.CLW_UI_FRAME);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(UiFrameDataAccess.UI_FRAME_ID);
            fm.setValue(pData.getFieldValue(UiFrameDataAccess.UI_FRAME_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public UiFrameDataMeta(TableField... fields) {
        
        super(UiFrameDataAccess.CLW_UI_FRAME);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

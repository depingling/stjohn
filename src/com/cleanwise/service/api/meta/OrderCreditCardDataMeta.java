
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderCreditCardDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_CREDIT_CARD.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderCreditCardDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderCreditCardDataMeta</code> is a meta class describing the database table object CLW_ORDER_CREDIT_CARD.
 */
public class OrderCreditCardDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderCreditCardDataMeta(TableObject pData) {
        
        super(OrderCreditCardDataAccess.CLW_ORDER_CREDIT_CARD);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderCreditCardDataAccess.ORDER_CREDIT_CARD_ID);
            fm.setValue(pData.getFieldValue(OrderCreditCardDataAccess.ORDER_CREDIT_CARD_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderCreditCardDataMeta(TableField... fields) {
        
        super(OrderCreditCardDataAccess.CLW_ORDER_CREDIT_CARD);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    

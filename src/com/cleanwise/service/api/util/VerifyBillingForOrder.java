package com.cleanwise.service.api.util;

/**
 */

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import java.sql.*;
import java.util.*;

public class VerifyBillingForOrder {
    
    OrderData mOrder = null;
    Connection mCon = null;
    public VerifyBillingForOrder( Connection pCon, OrderData pOrder) {
        mOrder = pOrder;
        mCon   = pCon;
    }

    public boolean hasError() {
        if ( null != mError && mError.length() > 0 ) {
            return true;
        }
        return false;
    }
    private String mError = null;
    public String getError() {
        return mError;
    }
  
    OrderAddressData mBillingAddress;
    public boolean readyForPO() throws Exception {
        
        // If this is a credit card purchase, make sure the
        // authorization has been performed.
        int orderId = mOrder.getOrderId();
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderCreditCardDataAccess.ORDER_ID,orderId);
        
        OrderCreditCardDataVector v =
        OrderCreditCardDataAccess.select(mCon, dbc);
        if ( v == null || v.size() == 0 ) {
            // This is not a credit card trans.
            return true;

        }
        
        
        Iterator it = v.iterator();
        while ( it.hasNext() ) {
            OrderCreditCardData ocd = (OrderCreditCardData)it.next();
            if ( ocd.getAuthStatusCd() != null &&
                ocd.getAuthStatusCd().equals(RefCodeNames.CREDIT_CARD_AUTH_STATUS.AUTH_SUCCESS) )
            {
                return true;
            }
        }
        mError = "VBFO4: No " +
        RefCodeNames.CREDIT_CARD_AUTH_STATUS.AUTH_SUCCESS +
        " entry found for orderId=" + orderId;
        return false;
    }

    public OrderAddressData getBillingAddress() {
        //billing address
        if (null != mBillingAddress ) {
            return mBillingAddress;
        }
        
        try {
            int orderId = mOrder.getOrderId();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderAddressDataAccess.ORDER_ID,orderId);
            dbc.addEqualTo(OrderAddressDataAccess.ADDRESS_TYPE_CD,
            RefCodeNames.ADDRESS_TYPE_CD.BILLING);
            OrderAddressDataVector billingAddresses = 
            OrderAddressDataAccess.select(mCon,dbc);
            if(billingAddresses.size()==0) {
                mError = "VBFO1. No billing address found. Order id: "+orderId;
                return null;
            }
            if(billingAddresses.size()>1) {
                mError = "VBFO2. The order has more than one billing address. Order id: "+orderId;
                return null;
            }
            mBillingAddress = (OrderAddressData) billingAddresses.get(0);
            return mBillingAddress;
        }
        catch (Exception e) {
            if ( null == mError ) { mError = "" ; }
            mError += "VBFO3 getBillingAddress error, " 
            + e.getMessage();
        }
        return null;
    }
}

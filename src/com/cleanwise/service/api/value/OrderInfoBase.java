package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * Describe class <code>OrderInfoBase</code> here.
 *
 * @author <a href="mailto:"></a>
 */
public class OrderInfoBase extends ValueObject {

    public OrderInfoBase() { mItemShoppingInfoList = null ; }
    public boolean getAllOrderItemsAddedBySystemFlag() {

	List l = this.getShoppingHistory();
	if ( null == l || l.size() == 0 ) {
	    return false;
	}
	
	for ( int i = 0; null != l && i < l.size(); i++ ) {
	    ShoppingInfoData sid = (ShoppingInfoData)l.get(i);
	    if ( sid.getAddBy() != null &&
		 ! sid.getAddBy().equals("inv_auto_order") ) {
		return false;
	    }
	}
	
	// All items were added by the inventory application.
	return true;
    }
    

    private List mItemShoppingInfoList = null;
    public List getShoppingHistory()    {
        if ( null == mItemShoppingInfoList ) {
            mItemShoppingInfoList = new ArrayList();
        }
        return mItemShoppingInfoList;
    }
    
    public void setShoppingHistory(List v)     {
	mItemShoppingInfoList = v;
    }

}

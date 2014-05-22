package com.cleanwise.view.forms;

/**
 *
 *@author     durval
 */

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.*;
import java.util.*;

public class ShoppingControlForm extends ActionForm {

    private HashMap map = new HashMap();
    private HashMap map2 = new HashMap();

    public Iterator getShoppingControlItemIds() {

    	return map.keySet().iterator();
    }

    public Iterator getShoppingControlItemIdsWithRestrictionDays(){

    	return map2.keySet().iterator();
    }

    public Object getItemIdMaxAllowed(String key) {
        return map.get(key);
    }

    public void setItemIdMaxAllowed(String key, Object value) {
        map.put(key, value);
    }

    public Object getItemIdRestrictionDays(String key) {
        return map2.get(key);
    }

    public void setItemIdRestrictionDays(String key, Object value) {
        map2.put(key, value);
    }
         public HashMap getMap() {
        return map;
    }

    public void setMap(HashMap map) {
        this.map = map;
    }

    public HashMap getMap2() {
        return map2;
    }

    public void setMap2(HashMap map2) {
        this.map2 = map2;
    }
    
    public ShoppingControlForm() { }

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
	map = new HashMap();
	map2 = new HashMap();
    }


    /**
     *  <code>validate</code> method is a stub.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     *@return          an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        // Validation happens in the logic bean.
        return null;
    }

}


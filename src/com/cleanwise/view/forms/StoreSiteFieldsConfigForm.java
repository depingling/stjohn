package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.BusEntityFieldsData;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Alexander Chikin
 * Date: 15.08.2006
 * Time: 13:30:04
 *
 */
public class StoreSiteFieldsConfigForm extends ActionForm {

    private BusEntityFieldsData mConfig = new BusEntityFieldsData();
    private int mBusEntityId;
    private Hashtable mCheckBoxValue=new Hashtable();

    /**
     *  Sets the BusEntityId attribute of the StoreUIConfigForm object
     *
     *@param  v  The new BusEntityId value
     */
    public void setBusEntityId(int v) {
        mBusEntityId = v;
    }
    /**
     *  Gets the BusEntityId attribute of the StoreUIConfigForm object
     *
     *@return    The BusEntityId value
     */
    public int getBusEntityId() {
        return mBusEntityId;
    }

    public  BusEntityFieldsData getConfig() {
        return mConfig;
    }

    public void setConfig(BusEntityFieldsData v) {
        mConfig = v;
    }



}

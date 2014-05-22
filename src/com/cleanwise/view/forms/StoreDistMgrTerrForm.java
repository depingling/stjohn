/**
 *  Title: StoreDistMgrTerrForm Description: This is the Struts ActionForm
 *  class for distributor config store page.
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     YKupershmidt
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

/**
 *  Form bean for the catalog manager configuration page.
 *
 *@author     tbesser
 *@created    October 14, 2001
 */
public final class StoreDistMgrTerrForm extends ActionForm {
    //Search fields
    private String _configType = "County";
    private String _city = "";
    private String _state = "";
    private String _postalCode = "";
    private String _county = "";
    private String _servicedOnly = "false";
    private String _servicedOnlyMem = "false";
    //Wrk fields
    private int _distributorId;
    private String _actConfigType = "";
    private BusEntityTerrViewVector _counties = new BusEntityTerrViewVector();
    private BusEntityTerrViewVector _postalCodes = new BusEntityTerrViewVector();
    private BusEntityTerrViewVector _cities = new BusEntityTerrViewVector();
    private String[] _selected = null;
    private String[] _busEntityTerrFreightCd = null;


    public void setConfigType(String pVal) {_configType = pVal;}
    public String getConfigType() {return _configType;}

    public void setState(String pVal) {_state = pVal;}
    public String getState() {return _state;}

    public void setCity(String pVal) {_city = pVal;}
    public String getCity() {return _city;}

    public void setPostalCode(String pVal) {_postalCode = pVal;}
    public String getPostalCode() {return _postalCode;}

    public void setCounty(String pVal) {_county = pVal;}
    public String getCounty() {return _county;}

    public void setServicedOnly(String pVal) {_servicedOnly = pVal;}
    public String getServicedOnly() {return _servicedOnly;}

    public void setServicedOnlyMem(String pVal) {_servicedOnlyMem = pVal;}
    public String getServicedOnlyMem() {return _servicedOnlyMem;}

    public void setDistributorId(int pVal) {_distributorId = pVal;}
    public int getDistributorId() {return _distributorId;}

    public void setActConfigType(String pVal) {_actConfigType = pVal;}
    public String getActConfigType() {return _actConfigType;}

    public void setSelected(String[] pVal) {_selected = pVal;}
    public String[] getSelected() {return _selected;}

    public void setBusEntityTerrFreightCd(String[] pVal) {_busEntityTerrFreightCd = pVal;}
    public String[] getBusEntityTerrFreightCd() {return _busEntityTerrFreightCd;}

    public void setCounties (BusEntityTerrViewVector pVal) {_counties = pVal;}
    public BusEntityTerrViewVector getCounties() {return _counties;}

    public void setPostalCodes (BusEntityTerrViewVector pVal) {_postalCodes = pVal;}
    public BusEntityTerrViewVector getPostalCodes() {return _postalCodes;}

    public void setCities (BusEntityTerrViewVector pVal) {_cities = pVal;}
    public BusEntityTerrViewVector getCities() {return _cities;}

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
       _selected = new String[0];
       _busEntityTerrFreightCd = new String[0];
       _servicedOnly = "false";
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
        // No validation necessary.
        return null;
    }

}


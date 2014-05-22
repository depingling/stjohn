
package com.cleanwise.view.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;

/**
 * <code>DistMgrConfigLogic</code>
 *
 * @author     YKupershmidt
 * @created    October 3, 2002
 */
public class DistMgrConfigLogic {

    /**
     *  <code>init</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void init(HttpServletRequest request,
			    ActionForm form)
	throws Exception {

	// reset all the saved data
  	DistMgrConfigForm sForm = (DistMgrConfigForm)form;
    sForm.setPostalCodes(new BusEntityTerrViewVector());
    sForm.setCounties(new BusEntityTerrViewVector());
    return;
    }

    /**
     *  <code>search</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors search(HttpServletRequest request,
			      ActionForm form)
	throws Exception {

	// new search, reset saved data
    ActionErrors ae = new ActionErrors();
	init(request, form);

    HttpSession session = request.getSession();
  	DistMgrConfigForm sForm = (DistMgrConfigForm)form;
  	String configType = sForm.getConfigType();
    String county = sForm.getCounty();
    String postalCode = sForm.getPostalCode();
    String state = sForm.getState();
    String servicedOnly = sForm.getServicedOnly();
    Hashtable conditions = new Hashtable();
    sForm.setServicedOnlyMem(sForm.getServicedOnly());
    if(county!=null && county.trim().length()>0) {
      conditions.put("county",county.toUpperCase());
    }
    if(postalCode!=null && postalCode.trim().length()>0) {
      conditions.put("postalCode",postalCode.toUpperCase());
    }
    if(state!=null && state.trim().length()>0) {
      conditions.put("state",state.toUpperCase());
    }
    if("true".equalsIgnoreCase(servicedOnly)) {
      conditions.put("servicedOnly",servicedOnly.toUpperCase());
    }

  	if ("County".equals(configType)) {
      ae = searchCounty(request,form,conditions);
      if(ae.size()>0) return ae;
  	}else if ("Zip Code".equals(configType)) {
      if(conditions.isEmpty()){
        ae.add("error",new ActionError("error.simpleGenericError","Search condition is too broad"));
        return ae;
      }
      ae = searchZipCode(request,form,conditions);
      if(ae.size()>0) return ae;
    }
    sForm.setActConfigType(configType);

    return ae;
    }

    /**
     *  <code>searchCounty</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors searchCounty(HttpServletRequest request,
				      ActionForm form, Hashtable pConditions)
	throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

	APIAccess factory = new APIAccess();
	Distributor distBean = factory.getDistributorAPI();
  	DistMgrConfigForm sForm = (DistMgrConfigForm)form;
    try {
      BusEntityTerrViewVector betVV =
         distBean.getDistributorCounties(sForm.getDistributorId(),pConditions);
      sForm.setCounties(betVV);
      setSelectedCounties(sForm);
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if(mess.indexOf("Too broad")>0) {
        ae.add("error",new ActionError("error.simpleGenericError","Too broad condition"));
        return ae;
      } else {
        throw exc;
      }
    }
    return ae;
    }

    /**
     *  <code>searchZipCode</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors searchZipCode(HttpServletRequest request,
				      ActionForm form, Hashtable pConditions)
	throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

	APIAccess factory = new APIAccess();
	Distributor distBean = factory.getDistributorAPI();
  	DistMgrConfigForm sForm = (DistMgrConfigForm)form;
    try {
      BusEntityTerrViewVector betVV =
         distBean.getDistributorZipCodes(sForm.getDistributorId(),pConditions);
      sForm.setPostalCodes(betVV);
      setSelectedZipCodes(sForm);
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if(mess.indexOf("Too broad")>0) {
        ae.add("error",new ActionError("error.simpleGenericError","Too broad condition"));
        return ae;
      } else {
        throw exc;
      }
    }
    return ae;
    }
    /**
     *  <code>save</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors save(HttpServletRequest request,
			      ActionForm form)
	throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
  	DistMgrConfigForm sForm = (DistMgrConfigForm)form;
    if("County".equalsIgnoreCase(sForm.getActConfigType())) {
      ae = saveCounty(request,form);
      if(ae.size()>0) return ae;
    }
    if("Zip Code".equalsIgnoreCase(sForm.getActConfigType())) {
      ae = saveZipCode(request,form);
      if(ae.size()>0) return ae;
    }
    ae = search(request,form);
    return ae;
    }
    /**
     *  <code>saveCounty</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors saveCounty(HttpServletRequest request,
			      ActionForm form)
	throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
  	DistMgrConfigForm sForm = (DistMgrConfigForm)form;
    int distId = sForm.getDistributorId();
    BusEntityTerrViewVector betVV = sForm.getCounties();
    BusEntityTerrViewVector betVVUpdate = new BusEntityTerrViewVector();
    String[] selected = sForm.getSelected();
    String[] busEntityTerrFreightCd = sForm.getBusEntityTerrFreightCd();
    int count=0;
    for(int ii=0; ii<betVV.size(); ii++) {
      BusEntityTerrView betV = (BusEntityTerrView) betVV.get(ii);
      int distId1 = betV.getBusEntityId();
      if(distId1!=distId) {
        ae.add("error",new ActionError("error.simpleGenericError",
           "Current distributor and request distribut do not match. Probably old page was used"));
        return ae;
      }
      //is checked?
      String countyCd = betV.getCountyCd()+"^"+betV.getStateProvinceCd()+"^"+betV.getCountryCd()+"^";
      int jj=0;
      for(;jj<selected.length; jj++) {
        String countyCd1 = selected[jj].substring(1);
        if(countyCd1.equals(countyCd)) {
          count++;
          if("0".equals(selected[jj].substring(0,1))) {
            break;
          }else{
            betV.setCheckedFl(true);
            betVVUpdate.add(betV);
            break;
          }
        }
      }
      if(jj==selected.length) {
        if(!betV.getNoModifiyFl() && betV.getCheckedFl()) {
          betV.setCheckedFl(false);
          betVVUpdate.add(betV);
        }
      }
    }
    
    //Match up the bus entity terr freight code list.  None of the above error checking is
    //preseant here as it is assumed that the above code has validated the data
    for(int ii=0; ii<betVVUpdate.size(); ii++) {
      BusEntityTerrView betV = (BusEntityTerrView) betVVUpdate.get(ii);
      //is checked?
      String countyCd = betV.getCountyCd()+"^"+betV.getStateProvinceCd()+"^"+betV.getCountryCd()+"^";
      int jj=0;
      for(;jj<busEntityTerrFreightCd.length; jj++) {
        String countyCd1 = busEntityTerrFreightCd[jj].substring(1);
        if(countyCd1.startsWith(countyCd)) {
            countyCd1 = countyCd1.substring(countyCd.length());
            betV.setBusEntityTerrFreightCd(countyCd1);
            break;
        }
      }
    }
    //************
    
    if(count!=selected.length) {
      ae.add("error",new ActionError("error.simpleGenericError",
         "Current and requested counties do not match. Probably old page was used"));
      return ae;
        }

	String user = (String) session.getAttribute("LoginUserName");
	APIAccess factory = new APIAccess();
	Distributor distBean = factory.getDistributorAPI();
        distBean.updateDistributorCounties(betVVUpdate,user);
    return ae;
    }

    /**
     *  <code>saveZipCode</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors saveZipCode(HttpServletRequest request,
			      ActionForm form)
	throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
  	DistMgrConfigForm sForm = (DistMgrConfigForm)form;
    int distId = sForm.getDistributorId();
    BusEntityTerrViewVector betVV = sForm.getPostalCodes();
    BusEntityTerrViewVector betVVUpdate = new BusEntityTerrViewVector();
    String[] selected = sForm.getSelected();
    
    HashSet processedZips = new HashSet();
    HashSet expectedProcessedZips = new HashSet();
    for(int i=0;i<selected.length;i++){
        expectedProcessedZips.add(selected[i]);
    }
    
    for(int ii=0; ii<betVV.size(); ii++) {
      BusEntityTerrView betV = (BusEntityTerrView) betVV.get(ii);
      int distId1 = betV.getBusEntityId();
      if(distId1!=distId) {
        ae.add("error",new ActionError("error.simpleGenericError",
           "Current distributor and request distribut do not match. Probably old page was used"));
        return ae;
      }
      //is checked?
      String postalCode = betV.getPostalCode()+"^";
      int jj=0;
      for(;jj<selected.length; jj++) {
        String postalCode1 = selected[jj];
        if(postalCode1.equals(postalCode)) {
          processedZips.add(postalCode);
          if(!betV.getCheckedFl()) {
            betV.setCheckedFl(true);
          }
          betVVUpdate.add(betV);
          break;
        }
      }
      if(jj==selected.length) {
        if(betV.getCheckedFl()) {
          betV.setCheckedFl(false);
          betVVUpdate.add(betV);
        }
      }
    }
    
    //Match up the bus entity terr freight code list.  None of the above error checking is
    //present here as it is assumed that the above code has validated the data
    String[] busEntityTerrFreightCd = sForm.getBusEntityTerrFreightCd();
    for(int ii=0; ii<betVVUpdate.size(); ii++) {
      BusEntityTerrView betV = (BusEntityTerrView) betVVUpdate.get(ii);
      //is checked?
      String postalCode = betV.getPostalCode()+"^";
      int jj=0;
      for(;jj<busEntityTerrFreightCd.length; jj++) {
        String postalCode1 = busEntityTerrFreightCd[jj];
        if(postalCode1.startsWith(postalCode)) {
            postalCode1 = postalCode1.substring(postalCode.length());
            betV.setBusEntityTerrFreightCd(postalCode1);
        }
      }
    }
    //************
    if(processedZips.size()!=expectedProcessedZips.size()) {
      ae.add("error",new ActionError("error.simpleGenericError",
         "Current and requested postal codes do not match. Probably old page was used"));
      return ae;
    }

	String user = (String) session.getAttribute("LoginUserName");
	APIAccess factory = new APIAccess();
	Distributor distBean = factory.getDistributorAPI();
    distBean.updateDistributorZipCodes(betVVUpdate,user);
    return ae;
    }

    /**
     *  <code>selectAll</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors selectAll(HttpServletRequest request,
				    ActionForm form)
	throws Exception {
    ActionErrors ae = new ActionErrors();
    DistMgrConfigForm sForm = (DistMgrConfigForm)form;
    if("Zip Code".equalsIgnoreCase(sForm.getActConfigType())) {
      BusEntityTerrViewVector zipCodes = sForm.getPostalCodes();
      String[] selected = new String[zipCodes.size()];
      for(int ii=0; ii<zipCodes.size(); ii++) {
        BusEntityTerrView betV = (BusEntityTerrView) zipCodes.get(ii);
        selected[ii] = ""+betV.getPostalCode();
      }
      sForm.setSelected(selected);
    }
    if("County".equalsIgnoreCase(sForm.getActConfigType())) {
      BusEntityTerrViewVector counties = sForm.getCounties();
      String[] selected = new String[counties.size()];
      for(int ii=0; ii<counties.size(); ii++) {
        BusEntityTerrView betV = (BusEntityTerrView) counties.get(ii);
        String ss = (betV.getNoModifiyFl())?"0":"1";
        ss += betV.getCountyCd()+"^"+betV.getStateProvinceCd()+"^"+betV.getCountryCd()+"^";
        selected[ii] = ss;
      }
      sForm.setSelected(selected);
    }
    return ae;
    }

    /**
     *  <code>clearSelection</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors clearSelection(HttpServletRequest request,
				    ActionForm form)
	throws Exception {
    ActionErrors ae = new ActionErrors();
    DistMgrConfigForm sForm = (DistMgrConfigForm)form;
    if("Zip Code".equalsIgnoreCase(sForm.getActConfigType())) {
      sForm.setSelected(new String[0]);
    }
    if("County".equalsIgnoreCase(sForm.getActConfigType())) {
      BusEntityTerrViewVector counties = sForm.getCounties();
      ArrayList selectedAL = new ArrayList();
      for(int ii=0; ii<counties.size(); ii++) {
        BusEntityTerrView betV = (BusEntityTerrView) counties.get(ii);
        if(betV.getNoModifiyFl()) {
          String ss = (betV.getNoModifiyFl())?"0":"1";
          ss += betV.getCountyCd()+"^"+betV.getStateProvinceCd()+"^"+betV.getCountryCd()+"^";
          selectedAL.add(ss);
        }
      }
      String[] selected = new String[selectedAL.size()];
      for(int ii=0; ii<selected.length; ii++) {
        selected[ii] = (String) selectedAL.get(ii);
      }
      sForm.setSelected(selected);
    }
    return ae;
    }

    /**
     *  <code>sort</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors sort(HttpServletRequest request,
				    ActionForm form)
	throws Exception {
    ActionErrors ae = new ActionErrors();
    DistMgrConfigForm sForm = (DistMgrConfigForm)form;
    if("County".equalsIgnoreCase(sForm.getActConfigType())) {
      ae = sortCounty(request,form);
      if(ae.size()>0) return ae;
    }
    if("Zip Code".equalsIgnoreCase(sForm.getActConfigType())) {
      ae = sortZipCode(request,form);
      if(ae.size()>0) return ae;
    }
    sForm.setServicedOnly(sForm.getServicedOnlyMem());
    return ae;
    }

    /**
     *  <code>sortCounty</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors sortCounty(HttpServletRequest request,
				    ActionForm form)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
      DistMgrConfigForm sForm = (DistMgrConfigForm)form;
  	  String sortField = request.getParameter("sortField");
      BusEntityTerrViewVector counties = sForm.getCounties();
      if("County".equalsIgnoreCase(sortField)) {
        counties.sort("CountyCd");
      }
      if("State Cd".equalsIgnoreCase(sortField)) {
        counties.sort("StateProvinceCd");
      }
      if("State Name".equalsIgnoreCase(sortField)) {
        counties.sort("StateProvinceName");
      }
      if("Country".equalsIgnoreCase(sortField)) {
        counties.sort("CountryCd");
      }
      setSelectedCounties(sForm);
      return ae;
    }

    /**
     *  <code>sortZipCode</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors sortZipCode(HttpServletRequest request,
				    ActionForm form)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
      DistMgrConfigForm sForm = (DistMgrConfigForm)form;
  	  String sortField = request.getParameter("sortField");
      BusEntityTerrViewVector zipCodes = sForm.getPostalCodes();
      if("County".equalsIgnoreCase(sortField)) {
        zipCodes.sort("CountyCd");
      }
      if("State Cd".equalsIgnoreCase(sortField)) {
        zipCodes.sort("StateProvinceCd");
      }
      if("State Name".equalsIgnoreCase(sortField)) {
        zipCodes.sort("StateProvinceName");
      }
      if("Country".equalsIgnoreCase(sortField)) {
        zipCodes.sort("CountryCd");
      }
      if("Postal Code".equalsIgnoreCase(sortField)) {
        zipCodes.sort("PostalCode");
      }
      setSelectedZipCodes(sForm);
      return ae;
    }

    private static void setSelectedCounties(DistMgrConfigForm sForm) {
      BusEntityTerrViewVector counties = sForm.getCounties();
      ArrayList selectedV = new ArrayList();
      ArrayList frtTypCdV = new ArrayList();
      for(int ii=0; ii<counties.size(); ii++) {
        BusEntityTerrView betV = (BusEntityTerrView) counties.get(ii);
        if(betV.getCheckedFl()) {
          String ss = (betV.getNoModifiyFl())?"0":"1";
          ss += betV.getCountyCd()+"^"+betV.getStateProvinceCd()+"^"+betV.getCountryCd()+"^";
          selectedV.add(ss);
          frtTypCdV.add((betV.getBusEntityTerrFreightCd()==null)?"":ss+betV.getBusEntityTerrFreightCd());
        }
      }
      String[] selected = new String[0];
      String[] frtTypCd = new String[0];
      selected = (String[]) selectedV.toArray(selected);
      frtTypCd = (String[]) frtTypCdV.toArray(frtTypCd);
      sForm.setSelected(selected);
      sForm.setBusEntityTerrFreightCd(frtTypCd);
    }

    private static void setSelectedZipCodes(DistMgrConfigForm sForm) {
      BusEntityTerrViewVector zipCodes = sForm.getPostalCodes();
      ArrayList selectedV = new ArrayList();
      ArrayList frtTypCdV = new ArrayList();
      for(int ii=0; ii<zipCodes.size(); ii++) {
        BusEntityTerrView betV = (BusEntityTerrView) zipCodes.get(ii);
        if(betV.getCheckedFl()) {
          String ss = betV.getPostalCode()+"^";
          selectedV.add(ss);
          frtTypCdV.add((betV.getBusEntityTerrFreightCd()==null)?"":ss+betV.getBusEntityTerrFreightCd());
        }
      }
      String[] selected = new String[0];
      String[] frtTypCd = new String[0];
      selected = (String[]) selectedV.toArray(selected);
      frtTypCd = (String[]) frtTypCdV.toArray(frtTypCd);
      sForm.setSelected(selected);
      sForm.setBusEntityTerrFreightCd(frtTypCd);
    }

}








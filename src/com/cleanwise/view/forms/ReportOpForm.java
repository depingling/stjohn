/**
 * Title:        ReportOpForm
 * Description:  This is the Struts ActionForm class for
 * order operation console page.
 * Purpose:      Strut support to search for orders.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       durval
 */

package com.cleanwise.view.forms;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import com.cleanwise.view.logic.ReportOpLogic;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.service.api.value.*;

/**
 * Form bean for the user manager page.
 *
 * @author Tim Besser
 */
public final class ReportOpForm extends ActionForm {
    
    private String rname;
    private String rtype;
    private String accountId;
    private String beginDate;
    private String endDate;
    private String interval;
    private String minAmt;
    private String maxAmt;
    private String inclusive;
    private String manufacturerId;
    private String vendorId;
    private String dateType;
    private String shipment;
    private boolean groupOnOrder;
    private String[] siteList;
    private String itemId;
    private boolean isBadDistSku;
    private String stateCd;
    private String countyCd;
    private String catalogId;
    private String requestedControls;
    private GenericReportControlViewVector genericControls;
    /** Holds value of property contractId. */
    private String contractId;
    
    
    /**
     * Gets the value of accountId
     *
     * @return the value of accountId
     */
    public String getAccountId() {
        return this.accountId;
    }
    
    
    /**
     * Sets the value of accountId
     *
     * @param argAccountId Value to assign to this.accountId
     */
    public void setAccountId(String argAccountId){
        this.accountId = argAccountId;
    }
    
    /**
     * Gets the value of beginDate
     *
     * @return the value of beginDate
     */
    public String getBeginDate() {
        return this.beginDate;
    }
    
    /**
     * Sets the value of beginDate
     *
     * @param argBeginDate Value to assign to this.beginDate
     */
    public void setBeginDate(String argBeginDate){
        this.beginDate = argBeginDate;
    }
    
    /**
     * Gets the value of endDate
     *
     * @return the value of endDate
     */
    public String getEndDate() {
        return this.endDate;
    }
    
    /**
     * Sets the value of endDate
     *
     * @param argEndDate Value to assign to this.endDate
     */
    public void setEndDate(String argEndDate){
        this.endDate = argEndDate;
    }
    
    /**
     * Gets the value of interval
     *
     * @return the value of interval
     */
    public String getInterval() {
        return this.interval;
    }
    
    /**
     * Sets the value of interval
     *
     * @param argInterval Value to assign to this.interval
     */
    public void setInterval(String argInterval){
        this.interval = argInterval;
    }
    
    /**
     * Gets the value of minAmt
     *
     * @return the value of minAmt
     */
    public String getMinAmt() {
        return this.minAmt;
    }
    
    /**
     * Sets the value of minAmt
     *
     * @param argMinAmt Value to assign to this.minAmt
     */
    public void setMinAmt(String argMinAmt){
        this.minAmt = argMinAmt;
    }
    
    /**
     * Gets the value of maxAmt
     *
     * @return the value of maxAmt
     */
    public String getMaxAmt() {
        return this.maxAmt;
    }
    
    /**
     * Sets the value of maxAmt
     *
     * @param argMaxAmt Value to assign to this.maxAmt
     */
    public void setMaxAmt(String argMaxAmt){
        this.maxAmt = argMaxAmt;
    }
    
    /**
     * Gets the value of inclusive
     *
     * @return the value of inclusive
     */
    public String getInclusive() {
        return this.inclusive;
    }
    
    /**
     * Sets the value of inclusive
     *
     * @param argInclusive Value to assign to this.inclusive
     */
    public void setInclusive(String argInclusive){
        this.inclusive = argInclusive;
    }
    
    /**
     * Gets the value of rname
     *
     * @return the value of rname
     */
    public String getRname() {
        return this.rname;
    }
    
    /**
     * Sets the value of rname
     *
     * @param argRname Value to assign to this.rname
     */
    public void setRname(String argRname){
        this.rname = argRname;
    }
    
    /**
     * Gets the value of manufacturerId
     *
     * @return the value of manufacturerId
     */
    public String getManufacturerId() {
        return this.manufacturerId;
    }
    
    /**
     * Sets the value of manufacturerId
     *
     * @param argManufacturerId Value to assign to this.manufacturerId
     */
    public void setManufacturerId(String argManufacturerId){
        this.manufacturerId = argManufacturerId;
    }
    
    /**
     * Gets the value of vendorId
     *
     * @return the value of vendorId
     */
    public String getVendorId() {
        return this.vendorId;
    }
    
    /**
     * Sets the value of vendorId
     *
     * @param argVendorId Value to assign to this.vendorId
     */
    public void setVendorId(String argVendorId){
        this.vendorId = argVendorId;
    }
    
    /**
     * Gets the value of dateType
     *
     * @return the value of dateType
     */
    public String getDateType() {
        return this.dateType;
    }
    
    /**
     * Sets the value of dateType
     *
     * @param argDateType Value to assign to this.dateType
     */
    public void setDateType(String argDateType){
        this.dateType = argDateType;
    }
    
    /**
     * Gets the value of shipment
     *
     * @return the value of shipment
     */
    public String getShipment() {
        return this.shipment;
    }
    
    /**
     * Sets the value of shipment
     *
     * @param argShipment Value to assign to this.shipment
     */
    public void setShipment(String argShipment){
        this.shipment = argShipment;
    }
    
    
    /**
     * Gets the value of groupOnOrder
     *
     * @return the value of groupOnOrder
     */
    public boolean getGroupOnOrder() {
        return this.groupOnOrder;
    }
    
    /**
     * Sets the value of groupOnOrder
     *
     * @param argGroupOnOrder Value to assign to this.groupOnOrder
     */
    public void setGroupOnOrder(boolean argGroupOnOrder){
        this.groupOnOrder = argGroupOnOrder;
    }
    
    /**
     * Gets the value of siteList
     *
     * @return the value of siteList
     */
    public String[] getSiteList() {
        return this.siteList;
    }
    
    /**
     * Sets the value of siteList
     *
     * @param argSiteList Value to assign to this.siteList
     */
    public void setSiteList(String[] argSiteList){
        this.siteList = argSiteList;
    }
    
    /** Getter for property itemId.
     * @return Value of property itemId.
     */
    public String getItemId() {
        return this.itemId;
    }
    
    /** Setter for property itemId.
     * @param itemId New value of property itemId.
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    
    
    /** Getter for property stateCd.
     * @return Value of property stateCd.
     */
    public String getStateCd() {
        return this.stateCd;
    }
    
    /** Setter for property stateCd.
     * @param stateCd New value of property stateCd.
     */
    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }
    
    /** Getter for property countyCd.
     * @return Value of property countyCd.
     */
    public String getCountyCd() {
        return this.countyCd;
    }
    
    /** Setter for property countyCd.
     * @param stateCd New value of property countyId.
     */
    public void setCountyCd(String countyCd) {
        this.countyCd = countyCd;
    }
    
    /** Getter for property catalogId.
     * @return Value of property catalogId.
     */
    public String getCatalogId() {
        return this.catalogId;
    }
    
    /** Setter for property catalogId.
     * @param stateCd New value of property catalogId.
     */
    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
    
    /** Getter for property RequestedControls.
     * @return Value of property requestedControls.
     */
    public String getRequestedControls() {
        return this.requestedControls;
    }
    
    /** Setter for property RequestedControls.
     * @param stateCd New value of property requestedControls.
     */
    public void setRequestedControls(String requestedControls) {
        this.requestedControls = requestedControls;
    }
    
    //private GenericReportControlViewVector genericControls;
    /** Getter for property GenericControls.
     * @return Value of property genericControls.
     */
    public GenericReportControlViewVector getGenericControls() {
        return this.genericControls;
    }
    
    /** Setter for property GenericControls.
     * @param genericControls New value of property genericControls.
     */
    public void setGenericControls(GenericReportControlViewVector genericControls) {
        this.genericControls = genericControls;
    }
    
    //Generic controls individual access
    public String getGenericControlValue(int index) {
        if(genericControls==null || index>=genericControls.size()) {
            return null;
        }
        String value = ((GenericReportControlView)this.genericControls.get(index)).getValue();
        return value ;
    }
    
    public void setGenericControlValue(int index, String value) {
        if(genericControls==null || index>=genericControls.size()) {
            return;
        }
        GenericReportControlView grc = (GenericReportControlView) this.genericControls.get(index);
        grc.setValue(value);
        
    }
    
    /**
     * <code>reset</code> method
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        rname = "";
        rtype = "";
        isBadDistSku = false;
    }
    
    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
    HttpServletRequest request) {
        
        // Perform the run validation.
        ActionErrors errors = new ActionErrors();
        
        String f_action = request.getParameter("f_action");
        
        if (f_action == null) {
            return errors;
        }
        if (rname.equals("") && rtype.equals("")) {
            return errors;
        }
        
        
        String change = request.getParameter("change");
        String changeType = request.getParameter("changeType");
        if (change != null && change.equals("rname")) {
            return errors;
        }
        if (changeType != null && changeType.equals("rtype")) {
            return errors;
        }
        
        
        //---------- Default checking
        
        StringTokenizer st = new StringTokenizer(requestedControls,",");
        while (st.hasMoreTokens()) {
            String control = st.nextToken();
            if(control.equals("DATE")){
                String bd = beginDate;
                if(bd==null || bd.trim().length()==0) {
                    errors.add("beginDate", new ActionError("variable.empty.error","Begin Date"));
                } else {
                    if(!isDate(bd)) {
                        errors.add("beginDate", new ActionError("variable.date.format.error", "Begin Date"));
                    }
                }
                String ed = endDate;
                if(ed==null || ed.trim().length()==0) {
                    errors.add("endDate", new ActionError("variable.empty.error","End Date"));
                } else {
                    if(!isDate(ed)) {
                        errors.add("endDate", new ActionError("variable.date.format.error", "Begin Date"));
                    }
                }
            } else if(control.equals("BEG_DATE")){
                String bd = beginDate;
                if(bd==null || bd.trim().length()==0) {
                    errors.add("beginDate", new ActionError("variable.empty.error","Begin Date"));
                } else {
                    if(!isDate(bd)) {
                        errors.add("beginDate", new ActionError("variable.date.format.error", "Begin Date"));
                    }
                }
            } else if(control.equals("END_DATE")){
                String ed = endDate;
                if(ed==null || ed.trim().length()==0) {
                    errors.add("endDate", new ActionError("variable.empty.error","End Date"));
                } else {
                    if(!isDate(ed)) {
                        errors.add("endDate", new ActionError("variable.date.format.error", "Begin Date"));
                    }
                }
            } else if(control.equals("ACCOUNT")){
                if ((accountId == null) || (accountId.trim().length() < 1)) {
                    errors.add("account",new ActionError("variable.empty.error","Account"));
                } else if(!isInt(accountId)) {
                    errors.add("account", new ActionError("variable.integer.format.error", "Account"));
                }
            } else if(control.equals("ACCOUNT_OPT")){
                if (accountId != null && accountId.trim().length()>0 && !isInt(accountId)) {
                    errors.add("account", new ActionError("variable.integer.format.error", "Account"));
                }
            } else if(control.equals("AMOUNT")){
            } else if(control.equals("ACCOUNT_LIST")){
            } else if(control.equals("CONTRACT")){
                if ((contractId == null) || (contractId.trim().length() < 1)) {
                    errors.add("contract",new ActionError("variable.empty.error","Contract"));
                } else if(!isInt(contractId)) {
                    errors.add("contract", new ActionError("variable.integer.format.error", "Contract"));
                }
            } else if(control.equals("DISTRIBUTOR")){
                if ((vendorId == null) || (vendorId.trim().length() < 1)) {
                    errors.add("distributor",new ActionError("variable.empty.error","Distributor"));
                } else if(!isInt(vendorId)) {
                    errors.add("distributor", new ActionError("variable.integer.format.error", "Distributor"));
                }
            } else if(control.equals("SITE")){
            } else if(control.equals("SITE_LIST")){
            } else if(control.equals("MANUFACTURER")){
                if ((manufacturerId == null) || (manufacturerId.trim().length() < 1)) {
                    errors.add("manufacturer",new ActionError("variable.empty.error","Manufacturer"));
                } else if(!isInt(manufacturerId)) {
                    errors.add("manufacturer", new ActionError("variable.integer.format.error", "Manufacturer"));
                }
            } else if(control.equals("MANUFACTURER_LIST")){
            } else if(control.equals("ITEM")){
                if ((itemId == null) || (itemId.trim().length() < 1)) {
                    errors.add("item",new ActionError("variable.empty.error","Item"));
                } else if(!isInt(itemId)) {
                    errors.add("item", new ActionError("variable.integer.format.error", "Item"));
                }
            } else if(control.equals("ITEM_LIST")){
            } else if(control.equals("INTERVAL")){
                if ((interval == null) || (interval.trim().length() < 1)) {
                    errors.add("interval",new ActionError("variable.empty.error","Interval"));
                }
            } else if(control.equals("DATE_TYPE_GROUPING")){
            } else if(control.equals("SHIPMENT")){
            } else if(control.equals("INCLUSIVE")){
                if ((inclusive == null) || (inclusive.trim().length() < 1)) {
                    errors.add("inclusive",new ActionError("variable.empty.error","Range"));
                }
            } else if(control.equals("STATE_OPT")){
            } else if(control.equals("COUNTY_OPT")){
            } else if(control.equals("DISTRIBUTOR_OPT")){
                if (vendorId != null && vendorId.trim().length()>0 && !isInt(vendorId)) {
                    errors.add("vendor", new ActionError("variable.integer.format.error", "Vendor"));
                }
            } else if(control.equals("CATALOG")){
                if ((catalogId == null) || (catalogId.trim().length() < 1)) {
                    errors.add("catalog",new ActionError("variable.empty.error","Catalog"));
                } else if(!isInt(catalogId)) {
                    errors.add("catalog", new ActionError("variable.integer.format.error", "Catalog"));
                }
            }
            
            return errors;
        }
        return errors;
    }
    
    /*
     *
     */
    boolean isDate(String pDate) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            df.parse(pDate);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /*
     *
     */
    boolean isInt(String pInt) {
        try {
            Integer.parseInt(pInt);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /** Getter for property isBadDistSku.
     * @return Value of property isBadDistSku.
     */
    public boolean isIsBadDistSku() {
        return this.isBadDistSku;
    }
    
    /** Setter for property isBadDistSku.
     * @param isBadDistSku New value of property isBadDistSku.
     */
    public void setIsBadDistSku(boolean isBadDistSku) {
        this.isBadDistSku = isBadDistSku;
    }
    
    /** Getter for property rtype.
     * @return Value of property rtype.
     */
    public String getRtype() {
        return this.rtype;
    }
    
    /** Setter for property rtype.
     * @param rtype New value of property rtype.
     */
    public void setRtype(String rtype) {
        this.rtype = rtype;
    }
    
    /** Getter for property contractId.
     * @return Value of property contractId.
     */
    public String getContractId() {
        return this.contractId;
    }
    
    /** Setter for property contractId.
     * @param contractId New value of property contractId.
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
    
    
}

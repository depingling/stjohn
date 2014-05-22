/**
 * Title:        KnowledgeOpDetailForm
 * Description:  This is the Struts ActionForm class for the knowledgeOpDetail page.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       
 */

package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CurrencyFormat;
import javax.servlet.http.HttpSession;


/**
 * Form bean for the add/edit KnowledgeDetail page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>knowledgeDetail</b> - A KnowledgeData object
 * </ul>
 */
public final class KnowledgeOpDetailForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private KnowledgeData         _mKnowledgeDetail = KnowledgeData.createValue();
    private KnowledgePropertyDataVector _mKnowledgeNotesList = new KnowledgePropertyDataVector();
    private KnowledgeContentDataVector _mKnowledgeContentsList = new KnowledgeContentDataVector();
    
    private DistributorData     _mDistributorDetail     = DistributorData.createValue();
    private ManufacturerData    _mManufacturerDetail    = ManufacturerData.createValue();
    private ProductData         _mProductDetail     = new ProductData();
    
    private String _mBusEntityId  = new String("");
    private String _mItemId      = new String("");
    
    private String _mProductName    = new String("");
    private String _mItemSize    = new String("");
    private String _mItemPack   = new String("");
    private String _mItemUom     = new String("");
    private String _mItemManufName = new String("");
    private String _mItemCwSku = new String("");
    private String _mItemManufSku = new String("");

    /**
     * Holds value of property storeId.
     */
    private String storeId;
    
        
  // ---------------------------------------------------------------- Properties


      
    /**
     * Return the knowledgeDetail object
     */
    public KnowledgeData getKnowledgeDetail() {
        return (this._mKnowledgeDetail);
    }

    /**
     * Set the KnowledgeDetail object
     */
    public void setKnowledgeDetail(KnowledgeData detail) {
        this._mKnowledgeDetail = detail;
    }


    /**
     * Return the KnowledgeNotesList object
     */
    public KnowledgePropertyDataVector getKnowledgeNotesList() {
        return (this._mKnowledgeNotesList);
    }

    /**
     * Set the KnowledgeNotesList object
     */
    public void setKnowledgeNotesList(KnowledgePropertyDataVector pVal) {
        this._mKnowledgeNotesList = pVal;
    }
    

    /**
     * Return the KnowledgeContentsList object
     */
    public KnowledgeContentDataVector getKnowledgeContentsList() {
        return (this._mKnowledgeContentsList);
    }

    /**
     * Set the KnowledgeContentsList object
     */
    public void setKnowledgeContentsList(KnowledgeContentDataVector pVal) {
        this._mKnowledgeContentsList = pVal;
    }
    
    
    /**
     * Return the DistributorDetail object
     */
    public DistributorData getDistributorDetail() {
        return (this._mDistributorDetail);
    }

    /**
     * Set the DistributorDetail object
     */
    public void setDistributorDetail(DistributorData detail) {
        this._mDistributorDetail = detail;
    }


    /**
     * Return the ManufacturerDetail object
     */
    public ManufacturerData getManufacturerDetail() {
        return (this._mManufacturerDetail);
    }

    /**
     * Set the ManufacturerDetail object
     */
    public void setManufacturerDetail(ManufacturerData detail) {
        this._mManufacturerDetail = detail;
    }


    /**
     * Return the ProductDetail object
     */
    public ProductData getProductDetail() {
        return (this._mProductDetail);
    }

    /**
     * Set the ProductDetail object
     */
    public void setProductDetail(ProductData detail) {
        this._mProductDetail = detail;
    }
    
    
    /**
     * Return the busEntityId object
     */
    public String getBusEntityId() {
        return (this._mBusEntityId);
    }


    /**
     * Set the BusEntityId object
     */
    public void setBusEntityId(String pVal) {
        this._mBusEntityId = pVal;
    }


    /**
     * Return the ItemId object
     */
    public String getItemId() {
        return (this._mItemId);
    }


    /**
     * Set the ItemId object
     */
    public void setItemId(String pVal) {
        this._mItemId = pVal;
    }


    /**
     * Return the ProductName object
     */
    public String getProductName() {
        return (this._mProductName);
    }


    /**
     * Set the ProductName object
     */
    public void setProductName(String pVal) {
        this._mProductName = pVal;
    }

    /**
     * Return the ItemSize object
     */
    public String getItemSize() {
        return (this._mItemSize);
    }


    /**
     * Set the ItemSize object
     */
    public void setItemSize(String pVal) {
        this._mItemSize = pVal;
    }

    /**
     * Return the ItemPack object
     */
    public String getItemPack() {
        return (this._mItemPack);
    }


    /**
     * Set the ItemPack object
     */
    public void setItemPack(String pVal) {
        this._mItemPack = pVal;
    }

    /**
     * Return the ItemUom object
     */
    public String getItemUom() {
        return (this._mItemUom);
    }


    /**
     * Set the ItemUom object
     */
    public void setItemUom(String pVal) {
        this._mItemUom = pVal;
    }

       
    /**
     * Return the ItemManufName object
     */
    public String getItemManufName() {
        return (this._mItemManufName);
    }


    /**
     * Set the ItemManufName object
     */
    public void setItemManufName(String pVal) {
        this._mItemManufName = pVal;
    }


    /**
     * Return the ItemCwSku object
     */
    public String getItemCwSku() {
        return (this._mItemCwSku);
    }


    /**
     * Set the ItemCwSku object
     */
    public void setItemCwSku(String pVal) {
        this._mItemCwSku = pVal;
    }


    /**
     * Return the ItemManufSku object
     */
    public String getItemManufSku() {
        return (this._mItemManufSku);
    }


    /**
     * Set the ItemManufSku object
     */
    public void setItemManufSku(String pVal) {
        this._mItemManufSku = pVal;
    }

    
    
  // ------------------------------------------------------------ Public Methods


    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }


    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *
     * Required fields are:
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {

        // Retrieve the action. We only want to validate on save.
        String action = request.getParameter("action");
        if (action == null) action = "";
        if (!action.equals("Save")) {
            return null;
        }
        
        String change = request.getParameter("change");
        if(null != change && "type".equals(change)) {
            return null;
        }
        
        // Is there a currently logged on user?
        HttpSession currentSession = request.getSession();
        if ((currentSession == null) || (currentSession.getAttribute(Constants.APIACCESS) == null) 
            || (currentSession.getAttribute(Constants.APP_USER) == null)) {
            return null;
        }        
        

        // Perform the save validation.
        return null;
    }

    /**
     * Getter for property storeId.
     * @return Value of property storeId.
     */
    public String getStoreId() {

        return this.storeId;
    }

    /**
     * Setter for property storeId.
     * @param storeId New value of property storeId.
     */
    public void setStoreId(String storeId) {

        this.storeId = storeId;
    }


}

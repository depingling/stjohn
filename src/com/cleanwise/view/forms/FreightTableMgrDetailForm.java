/**
 * Title:        FreightTableMgrDetail
 * Description:  This is the Struts ActionForm class for the freightTable detail page.
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
 * Form bean for the add/edit freightTable page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>detail</b> - A BusEntityData object
 * </ul>
 */
public final class FreightTableMgrDetailForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private FreightTableData    _detail = FreightTableData.createValue();
    private FreightTableCriteriaDescDataVector  _criteriaList = new FreightTableCriteriaDescDataVector();
    private FreightTableCriteriaDescDataVector  _orgCriteriaList = new FreightTableCriteriaDescDataVector();
    
    private int _criteriaLength = 10;
    
  // ---------------------------------------------------------------- Properties


    /**
     * Return the freightTable detail object
     */
    public FreightTableData getDetail() {
        return (this._detail);
    }

    /**
     * Set the freightTable detail object
     */
    public void setDetail(FreightTableData detail) {
        this._detail = detail;
    }

  
    /**
     * <code>getCriteriaList</code> method.
     *
     * @return a <code>FreightTableCriteriaDescDataVector</code> value
     */
    public FreightTableCriteriaDescDataVector getCriteriaList() {
        if( null == this._criteriaList) {
            this._criteriaList = new FreightTableCriteriaDescDataVector();
        }
        return (this._criteriaList);
    }

    /**
     * <code>setCriteriaList</code> method.
     *
     * @param pVal a <code>FreightTableCriteriaDescDataVector</code> value
     */
    public void setCriteriaList(FreightTableCriteriaDescDataVector pVal) {
        this._criteriaList = pVal;
    }


    /**
     * <code>getOrgCriteriaList</code> method.
     *
     * @return a <code>FreightTableCriteriaDescDataVector</code> value
     */
    public FreightTableCriteriaDescDataVector getOrgCriteriaList() {
        if( null == this._orgCriteriaList) {
            this._orgCriteriaList = new FreightTableCriteriaDescDataVector();
        }
        return (this._orgCriteriaList);
    }

    /**
     * <code>setOrgCriteriaList</code> method.
     *
     * @param pVal a <code>FreightTableCriteriaDescDataVector</code> value
     */
    public void setOrgCriteriaList(FreightTableCriteriaDescDataVector pVal) {
        this._orgCriteriaList = pVal;
    }


    
    public FreightTableCriteriaDescData getCriteria(int idx) {

        if (_criteriaList == null) {
            _criteriaList = new FreightTableCriteriaDescDataVector();
        }
        while (idx >= _criteriaList.size()) {
            _criteriaList.add(FreightTableCriteriaDescData.createValue());
        }    
        
        return (FreightTableCriteriaDescData) _criteriaList.get(idx);
    }
    

    /**
     * Return the criteria length
     */
    public int getCriteriaLength() {
        return (this._criteriaLength);
    }

    /**
     * Set the criteria length
     */
    public void setCriteriaLength(int pVal) {
        this._criteriaLength = pVal;
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
     *   freightTable name
     *   tax ID
     *   contact first and last names
     *   contact email address
     *
     * Additionally, a freightTable's name is checked for uniqueness.
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
        ActionErrors errors = new ActionErrors();

        if ((_detail.getShortDesc() == null) || (_detail.getShortDesc().trim().length() < 1))
            errors.add("freightTablename", new ActionError("variable.empty.error", "FreightTable Name"));

        if ((_detail.getFreightTableTypeCd() == null) || (_detail.getFreightTableTypeCd().trim().length() < 1))
            errors.add("freightTablename", new ActionError("variable.empty.error", "FreightTable Type"));

        if ((_detail.getFreightTableStatusCd() == null) || (_detail.getFreightTableStatusCd().trim().length() < 1))
            errors.add("freightTablename", new ActionError("variable.empty.error", "FreightTable Status"));

        
        for(int i = 0; i < _criteriaList.size(); i++) {
            FreightTableCriteriaDescData criteria = (FreightTableCriteriaDescData)_criteriaList.get(i); 
            boolean hasError = false; 
            if(criteria.getLowerAmount().trim().length() > 0) {
                // check that it is a valid amount format
                try {
                    BigDecimal lowerAmt = 
                        CurrencyFormat.parse(criteria.getLowerAmount());
                    criteria.setLowerAmount(CurrencyFormat.formatAsNumber(lowerAmt));
                } catch (ParseException pe) {
                    errors.add("criteriaAmount", 
				      new ActionError("error.invalidNumberAmount",
						      "Criteria [" + (i+1) + "]'s Begin Amount"));
                    hasError = true;
                }
            }
                
            if(criteria.getHigherAmount().trim().length() > 0) {
                // check that it is a valid amount format
                try {
                    BigDecimal higherAmt = 
                        CurrencyFormat.parse(criteria.getHigherAmount());
                    criteria.setHigherAmount(CurrencyFormat.formatAsNumber(higherAmt));
                } catch (ParseException pe) {
                    errors.add("criteriaAmount", 
				      new ActionError("error.invalidNumberAmount",
						      "Criteria [" + (i+1) + "]'s End Amount"));
                    hasError = true;
                }
            }
                
            if(criteria.getFreightAmount().trim().length() > 0) {
                // check that it is a valid amount format
                try {
                    BigDecimal freightAmt = 
                    CurrencyFormat.parse(criteria.getFreightAmount());
                    criteria.setFreightAmount(CurrencyFormat.formatAsNumber(freightAmt));
                } catch (ParseException pe) {
                    errors.add("criteriaAmount", 
				      new ActionError("error.invalidNumberAmount",
						      "Criteria [" + (i+1) + "]'s Freight Amount"));
                    hasError = true;
                }
            }

            if(criteria.getHandlingAmount().trim().length() > 0) {
                // check that it is a valid amount format
                try {
                    BigDecimal handlingAmt = 
                    CurrencyFormat.parse(criteria.getHandlingAmount());
                    criteria.setHandlingAmount(CurrencyFormat.formatAsNumber(handlingAmt));
                } catch (ParseException pe) {
                    errors.add("criteriaAmount", 
				      new ActionError("error.invalidNumberAmount",
						      "Criteria [" + (i+1) + "]'s Handling Amount"));
                    hasError = true;
                }
            }
            
            if( true != hasError ) {
                _criteriaList.set(i, criteria);
            }
                        
        }
        
        return errors;

    }


}

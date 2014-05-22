/**
 * Title:        CallOpDetailForm
 * Description:  This is the Struts ActionForm class for the callOpDetail page.
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
 * Form bean for the add/edit CallDetail page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>callDetail</b> - A CallData object
 * </ul>
 */
public final class CallOpDetailForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private CallData         _mCallDetail = CallData.createValue();
    private CallPropertyDataVector _mCallNotesList = new CallPropertyDataVector();
    private UserDataVector _mCustomerServiceUserList = new UserDataVector();    
    private OrderStatusDescDataVector _mOrderStatusDescList = new OrderStatusDescDataVector();
    
    private String _mAccountId   = new String("");
    private String _mAccountName = new String("");
    private String _mSiteId      = new String("");
    private String _mSiteName    = new String("");
    private String _mSiteCity    = new String("");
    private String _mSiteState   = new String("");
    private String _mSiteZip     = new String("");
    
    private String _mAssignedToId = new String("");
    private String _mAssignedToName = new String("");
    private String _mEmailMessage = new String("");
    private String _mStatusCd     = new String("");
    private String _mSeverityCd     = new String("");
    
    private Date   _mClosedDate  = null;
    private String _mClosedDateS = new String("");

    // for orderstatus  info
    private String _mErpOrderNum    = new String("");
    private String _mWebOrderNum    = new String("");
    private String _mCustPoNum      = new String("");
    private String _mErpPoNum       = new String("");
        
  // ---------------------------------------------------------------- Properties


      
    /**
     * Return the callDetail object
     */
    public CallData getCallDetail() {
        return (this._mCallDetail);
    }

    /**
     * Set the CallDetail object
     */
    public void setCallDetail(CallData detail) {
        this._mCallDetail = detail;
    }


    /**
     * Return the CallNotesList object
     */
    public CallPropertyDataVector getCallNotesList() {
        return (this._mCallNotesList);
    }

    /**
     * Set the CallNotesList object
     */
    public void setCallNotesList(CallPropertyDataVector pVal) {
        this._mCallNotesList = pVal;
    }
    
    
    /**
     * <code>getCustomerServiceUserList</code> method.
     *
     * @return a <code>UserDataVector</code> value
     */
    public UserDataVector getCustomerServiceUserList() {
        return (this._mCustomerServiceUserList);
    }

    /**
     * <code>setCustomerServiceUserList</code> method.
     *
     * @param pVal a <code>UserDataVector</code> value
     */
    public void setCustomerServiceUserList(UserDataVector pVal) {
        this._mCustomerServiceUserList = pVal;
    }


    /**
     * Return the OrderStatusDescList object
     */
    public OrderStatusDescDataVector getOrderStatusDescList() {
        return (this._mOrderStatusDescList);
    }

    /**
     * Set the OrderStatusDescList object
     */
    public void setOrderStatusDescList(OrderStatusDescDataVector pVal) {
        this._mOrderStatusDescList = pVal;
    }
    
    
    
    /**
     * Return the accountId object
     */
    public String getAccountId() {
        return (this._mAccountId);
    }


    /**
     * Set the AccountId object
     */
    public void setAccountId(String pVal) {
        this._mAccountId = pVal;
    }


    /**
     * Return the AccountName object
     */
    public String getAccountName() {
        return (this._mAccountName);
    }


    /**
     * Set the AccountName object
     */
    public void setAccountName(String pVal) {
        this._mAccountName = pVal;
    }


    /**
     * Return the SiteId object
     */
    public String getSiteId() {
        return (this._mSiteId);
    }


    /**
     * Set the SiteId object
     */
    public void setSiteId(String pVal) {
        this._mSiteId = pVal;
    }


    /**
     * Return the SiteName object
     */
    public String getSiteName() {
        return (this._mSiteName);
    }


    /**
     * Set the SiteName object
     */
    public void setSiteName(String pVal) {
        this._mSiteName = pVal;
    }

    /**
     * Return the SiteCity object
     */
    public String getSiteCity() {
        return (this._mSiteCity);
    }


    /**
     * Set the SiteCity object
     */
    public void setSiteCity(String pVal) {
        this._mSiteCity = pVal;
    }

    /**
     * Return the SiteState object
     */
    public String getSiteState() {
        return (this._mSiteState);
    }


    /**
     * Set the SiteState object
     */
    public void setSiteState(String pVal) {
        this._mSiteState = pVal;
    }

    /**
     * Return the SiteZip object
     */
    public String getSiteZip() {
        return (this._mSiteZip);
    }


    /**
     * Set the SiteZip object
     */
    public void setSiteZip(String pVal) {
        this._mSiteZip = pVal;
    }

       
    /**
     * Return the ErpOrderNum object
     */
    public String getErpOrderNum() {
        return (this._mErpOrderNum);
    }

    /**
     * Set the ErpOrderNum object
     */
    public void setErpOrderNum(String pVal) {
        this._mErpOrderNum = pVal;
    }
    

    /**
     * Return the WebOrderNum object
     */
    public String getWebOrderNum() {
        return (this._mWebOrderNum);
    }

    /**
     * Set the WebOrderNum object
     */
    public void setWebOrderNum(String pVal) {
        this._mWebOrderNum = pVal;
    }
    
    /**
     * Return the CustPoNum object
     */
    public String getCustPoNum() {
        return (this._mCustPoNum);
    }

    /**
     * Set the CustPoNum object
     */
    public void setCustPoNum(String pVal) {
        this._mCustPoNum = pVal;
    }
    
    /**
     * Return the ErpPoNum object
     */
    public String getErpPoNum() {
        return (this._mErpPoNum);
    }

    /**
     * Set the ErpPoNum object
     */
    public void setErpPoNum(String pVal) {
        this._mErpPoNum = pVal;
    }
    

    /**
     * Return the AssignedToId object
     */
    public String getAssignedToId() {
        return (this._mAssignedToId);
    }


    /**
     * Set the AssignedToId object
     */
    public void setAssignedToId(String pVal) {
        this._mAssignedToId = pVal;
    }


    /**
     * Return the AssignedToName object
     */
    public String getAssignedToName() {
        return (this._mAssignedToName);
    }


    /**
     * Set the AssignedToName object
     */
    public void setAssignedToName(String pVal) {
        this._mAssignedToName = pVal;
    }


    /**
     * Return the EmailMessage object
     */
    public String getEmailMessage() {
        return (this._mEmailMessage);
    }


    /**
     * Set the EmailMessage object
     */
    public void setEmailMessage(String pVal) {
        this._mEmailMessage = pVal;
    }

    
    /**
     * Return the StatusCd object
     */
    public String getStatusCd() {
        return (this._mStatusCd);
    }


    /**
     * Set the StatusCd object
     */
    public void setStatusCd(String pVal) {
        this._mStatusCd = pVal;
    }


    /**
     * Return the SeverityCd object
     */
    public String getSeverityCd() {
        return (this._mSeverityCd);
    }


    /**
     * Set the SeverityCd object
     */
    public void setSeverityCd(String pVal) {
        this._mSeverityCd = pVal;
    }

    
    
    
    /**
     * Return the ClosedDate object
     */
    public Date getClosedDate() {
        return (this._mClosedDate);
    }

    /**
     * Set the ClosedDate object
     */
    public void setClosedDate(Date pVal) {
        this._mClosedDate = pVal;
    }
        
    
    public String getClosedDateS() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        if(null == _mClosedDate) {
            _mClosedDateS = new String("");            
        }
        else {
            this._mClosedDateS = simpleDateFormat.format(_mClosedDate);  
        }
        return this._mClosedDateS;  
    }
  
  
    public void setClosedDateS(String dateString) {    
        this._mClosedDateS = dateString;
        if(null != dateString && ! "".equals(dateString)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date =  new Date();
            try {
                date  = simpleDateFormat.parse(dateString);  
            }
            catch (Exception e) {
                date = null;
            }    
            this._mClosedDate = date;
        }
        else {
            this._mClosedDate = null;
        }
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


}

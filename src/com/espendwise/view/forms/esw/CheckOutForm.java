/**
 * Title: CheckOutEswForm 
 * Description: This is the Struts ActionForm class handling the ESW CheckOut functionality.
 *
 */

package com.espendwise.view.forms.esw;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.utils.ShopTool;

/**
 * Implementation of <code>ActionForm</code> that handles CheckOut functionality.
 */
public final class CheckOutForm extends EswForm {

	private static final long serialVersionUID = 1L;
	
	//Begin: CheckOut functionality
	private CheckoutForm _checkOutForm;
	private ShoppingCartData _shoppingCartData;
	private List<LabelValueBean> _paymentTypeChoices;
	//End: CheckOut functionality
	
	//Begin: Remote Access
	private boolean _isRemoteAccess;
	//End: Remote Access
	
	//STJ-5637 
	private String _orderFee;
	
	
	/**
	 * @return the shoppingCartData
	 */
	public ShoppingCartData getShoppingCartData() {
		return _shoppingCartData;
	}

	/**
	 * @return the paymentTypeChoices
	 */
	public List<LabelValueBean> getPaymentTypeChoices() {
		return _paymentTypeChoices;
	}

	/**
	 * @param pPaymentTypeChoices the paymentTypeChoices to set
	 */
	public void setPaymentTypeChoices(List<LabelValueBean> pPaymentTypeChoices) {
		_paymentTypeChoices = pPaymentTypeChoices;
	}

	/**
	 * @param pShoppingCartData the shoppingCartData to set
	 */
	public void setShoppingCartData(ShoppingCartData pShoppingCartData) {
		_shoppingCartData = pShoppingCartData;
	}

	/**
	 * @return the checkOutForm
	 */
	public CheckoutForm getCheckOutForm() {
		if(_checkOutForm==null){
			_checkOutForm = new CheckoutForm();
		}
		return _checkOutForm;
	}

	/**
	 * @param pCheckOutForm the checkOutForm to set
	 */
	public void setCheckOutForm(CheckoutForm pCheckOutForm) {
		_checkOutForm = pCheckOutForm;
	}
	
	/**
	 * @return the isRemoteAccess
	 */
	public boolean isRemoteAccess() {
		return _isRemoteAccess;
	}

	/**
	 * @param pIsRemoteAccess the isRemoteAccess to set
	 */
	public void setRemoteAccess(boolean pIsRemoteAccess) {
		_isRemoteAccess = pIsRemoteAccess;
	}

	/**
	 * @return the orderFee
	 */
	public String getOrderFee() {
		return _orderFee;
	}


	/**
	 * @param orderFee the orderFee to set
	 */
	public void setOrderFee(String orderFee) {
		_orderFee = orderFee;
	}

	
	/**
     * Reset all properties to their default values.
     * @param  mapping  The mapping used to select this instance
     * @param  request  The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	super.reset(mapping, request);
    	
    	//reset the CheckoutForm
    	CheckoutForm oldCheckOutForm = getCheckOutForm();
    	oldCheckOutForm.reset(mapping,request);
    	
    	//STJ-4749
    	if(!oldCheckOutForm.isBillingOrder()){
    		oldCheckOutForm.setBillingOriginalPurchaseOrder("");
    		oldCheckOutForm.setBillingDistributorInvoice("");
    	}
    	
    	SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
    	oldCheckOutForm.setBypassBudget(sessionData.isExcludeOrderFromBudget());
    	
    	//STJ-5203
    	setRemoteAccess(sessionData.isRemoteAccess());
    	
    	//Set ShoppingCartData from the session.
    	if(getShoppingCartData() == null) {
    		setShoppingCartData(ShopTool.getCurrentShoppingCart(request));
    	}
    }

    /**
     * Validate the properties that have been set from this HTTP request, and
     * return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found. If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *@param  mapping  The mapping used to select this instance
     *@param  request  The servlet request we are processing
     *@return          Description of the Returned Value
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    	//no validation is performed at the form level, so return null.
    	ActionErrors returnValue = null;
        return returnValue;
    }
    
}


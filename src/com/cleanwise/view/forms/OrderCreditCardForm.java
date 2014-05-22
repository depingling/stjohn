/*
 * OrderCreditCardForm.java
 *
 * Created on October 29, 2004, 4:17 PM
 */

package com.cleanwise.view.forms;
import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.*;
/**
 *
 * @author  bstevens
 */
public class OrderCreditCardForm extends ActionForm{
    private OrderCreditCardDescData mOrderCreditCardDescData;
    private String newCreditCardNumber;
    private InvoiceCreditCardTransViewVector invCCTransList = null;
    private int invCustIDToProcess;
    
    public OrderCreditCardDescData getOrderCreditCardDescData(){
        return mOrderCreditCardDescData;
    }
    public void setOrderCreditCardDescData(OrderCreditCardDescData pOrderCreditCardDescData){
        mOrderCreditCardDescData = pOrderCreditCardDescData;
    }
    
    
    
    /**
     * Getter for property newCreditCardNumber.
     * @return Value of property newCreditCardNumber.
     */
    public String getNewCreditCardNumber() {
        return this.newCreditCardNumber;
    }
    
    /**
     * Setter for property newCreditCardNumber.
     * @param newCreditCardNumber New value of property newCreditCardNumber.
     */
    public void setNewCreditCardNumber(String newCreditCardNumber) {
        this.newCreditCardNumber = newCreditCardNumber;
    }
    
	public InvoiceCreditCardTransViewVector getInvCCTransList() {
		return invCCTransList;
	}
	
	public void setInvCCTransList(InvoiceCreditCardTransViewVector invCCTransList) {
		this.invCCTransList = invCCTransList;
	}
	public int getInvCustIDToProcess() {
		return invCustIDToProcess;
	}
	public void setInvCustIDToProcess(int invCustIDToProcess) {
		this.invCustIDToProcess = invCustIDToProcess;
	}
}

package com.cleanwise.service.api.value;

import java.math.BigDecimal;

import com.cleanwise.service.api.framework.ValueObject;


/**
 * This value object provides methods of accessing data from the underlying budget data object.
 * It differes from the BudgetView class as that is designed for the UI to interact with it,
 * this class is more for bridging the gap between the data model and a more indexed accesable
 * object.  @see com.cleanwise.service.api.util.BudgetUtil for more useful methods 
 * and @see com.cleanwise.view.forms.BudgetView for interacting with the UI 
 * @author bstevens
 */
public class BudgetInfoView extends ValueObject{
	BudgetData mBudget;
	
	/**
	 * Constructs a new BudgetInfoView object with using the supplied BudgetData
	 * object as the underlying budget object to operate off of
	 */
	public BudgetInfoView(BudgetData pBudget){
		mBudget=pBudget;
	}
	
	/**
	 * Gets the underlying budget object.
	 */
	public BudgetData getBudgetData(){
		return mBudget;
	}

}

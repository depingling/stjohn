/**
 * ISSReSaleOrderReport.java
 */
package com.cleanwise.service.api.reporting;

/**
 * @author ssharma
 *
 */
public class ISSReSaleOrderReport extends ReSaleOrderReport{

	/**
	 * Custom report for ISS. Extra columns - Primary contact name, dist cost, cust price
	 */
	protected boolean isShowPrimaryContact(){
    	return true;
    }
    protected boolean isShowCost(){
    	return true;
    }
    
    protected boolean isUseMultiAcctsFlag(){
    	return false;
    }

}

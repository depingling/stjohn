package com.cleanwise.service.api.reporting;

/**
 * @author aaugustine
 */
public class ISSUKReSaleOrderReport extends ReSaleOrderReport {
	/**
	 * Custom report for ISS UK. Extra columns - Delivery Note Number
	 */
	protected boolean isShowDeliveryNoteNum() {
		return true;
	}

}

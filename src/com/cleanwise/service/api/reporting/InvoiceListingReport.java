package com.cleanwise.service.api.reporting;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.apache.struts.action.ActionError;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PurchaseOrder;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.ClwComparatorFactory;

/**
 * 
 */
public class InvoiceListingReport  implements GenericReport {

	/** Creates a new instance of InvoiceListingReport */
	public InvoiceListingReport() {
	}

	@Override
	public GenericReportResultView process(ConnectionContainer cons,
			GenericReportData reportData, Map pParams) throws Exception {
		APIAccess factory = new APIAccess();
		User userEjb = factory.getUserAPI();
		PurchaseOrderStatusCriteriaData searchCriteria = PurchaseOrderStatusCriteriaData.createValue();
		searchCriteria.setInvoiceSearch(true);
		SimpleDateFormat sdf = new SimpleDateFormat((String) pParams.get("DATE_FMT"));
		String userIdStr = (String) pParams.get("CUSTOMER");	    
		String storeIdStr = (String) pParams.get("STORE");
		int userId = Integer.parseInt(userIdStr);
		int storeId = Integer.parseInt(storeIdStr);
		String value = (String) ReportingUtils.getParam(pParams, "BEG_DATE");
		if (Utility.isSet(value)){
			Date begDateD = sdf.parse(value);
			searchCriteria.setInvoiceDistDateRangeBegin(begDateD);
		}

		value = (String)ReportingUtils.getParam(pParams, "END_DATE");
		if (Utility.isSet(value)){
			Date endDateD = sdf.parse(value); 
			searchCriteria.setInvoiceDistDateRangeEnd(endDateD);
		}

		value = (String)ReportingUtils.getParam(pParams, "INVOICE_TYPE_2");
		if (Utility.isSet(value)){
			searchCriteria.setSaleTypeCd(value);
		}

		value = (String)ReportingUtils.getParam(pParams, "DISTRIBUTOR");
		if (Utility.isSet(value)){
			Integer distId = new Integer(value);
			IdVector distIds = new IdVector();
			distIds.add(distId);
			searchCriteria.setDistributorIds(distIds);
		}

		value = (String)ReportingUtils.getParam(pParams, "SITES");
		if (!Utility.isSet(value)){
			value = (String)ReportingUtils.getParam(pParams, "SITE_MULTI");
		}
		if (Utility.isSet(value)){
			searchCriteria.setSiteIdVector(Utility.parseIdStringToVector(value, ","));
		}

		value = (String)ReportingUtils.getParam(pParams, "LOCATE_DISTRIBUTOR_MULTI");
		if (!Utility.isSet(value)){
			value = (String)ReportingUtils.getParam(pParams, "DISTRIBUTOR_MULTI");
		}
		if (!Utility.isSet(value)){
			value = (String)ReportingUtils.getParam(pParams, "DISTRIBUTOR");
		}

		if (Utility.isSet(value)){
			searchCriteria.setDistributorIds(Utility.parseIdStringToVector(value, ","));
		}

		value = (String)ReportingUtils.getParam(pParams, "APPROVED_FLAG");
		if (Utility.isSet(value)){
			if ("Yes".equals(value)) {
				LinkedList invoiceStatusLL = new LinkedList();
				invoiceStatusLL.add(RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_PROCESSED);
				invoiceStatusLL.add(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED);
				searchCriteria.setInvoiceDistStatusList(invoiceStatusLL);
			}
		}

		value = (String)ReportingUtils.getParam(pParams, "INVOICE_NUM");
		if (Utility.isSet(value)){
			searchCriteria.setInvoiceDistNum(value);
		}
		value = (String)ReportingUtils.getParam(pParams, "WEB_ORDER_NUMBER");
		if (Utility.isSet(value)){
			searchCriteria.setWebOrderConfirmationNum(value);
		}
		value = (String)ReportingUtils.getParam(pParams, "PURCHASE_ORDER_NUMBER");
		if (Utility.isSet(value)){
			searchCriteria.setOutboundPoNum(value);
			searchCriteria.setErpPONumMatchType(PurchaseOrderStatusCriteriaData.EXACT_MATCH_AND_OTHER_COL);
		}

		searchCriteria.setAccountIdVector(userEjb.getUserAccountIds(userId, storeId, false));
		IdVector stories = new IdVector();
		stories.add(storeId);

		searchCriteria.setStoreIdVector(stories);;

		//filter out the bad status codes
		if (searchCriteria.getInvoiceDistStatusList() == null || searchCriteria.getInvoiceDistStatusList().size() == 0) {
			ArrayList excludeStatusList = new ArrayList();
			excludeStatusList.add(RefCodeNames.INVOICE_STATUS_CD.CANCELLED);
			excludeStatusList.add(RefCodeNames.INVOICE_STATUS_CD.DUPLICATE);
			excludeStatusList.add(RefCodeNames.INVOICE_STATUS_CD.PENDING);
			excludeStatusList.add(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
			excludeStatusList.add(RefCodeNames.INVOICE_STATUS_CD.REJECTED);
			searchCriteria.setInvoiceDistExcludeStatusList(excludeStatusList);
		}

		if (searchCriteria.getSiteIdVector() != null && !searchCriteria.getSiteIdVector().isEmpty()) {
			//Need to validate sites belong to user??
			//factory.getUserAPI().isSiteOfUser()
		} else {
			IdVector userSiteIds = Utility.toIdVector(factory.getUserAPI().getSiteCollection(userId));
			searchCriteria.setSiteIdVector(userSiteIds);
		}

		PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
		PurchaseOrderStatusDescDataViewVector poStatusDescData =
			purchaseOrderEjb.getPurchaseOrderStatusDescCollection(searchCriteria);
		if (poStatusDescData.isEmpty()){
			throw new Exception("^clwKey^report.no.invoices.found^clwKey^");
		}else if(poStatusDescData.size() > PurchaseOrderStatusCriteriaData.MAX_SEARCH_RESULTS){
			throw new Exception("^clwKey^report.max.invoices.found^clwKey^");
		}

		ArrayList invoiceToRender = new ArrayList();
		Iterator it = poStatusDescData.iterator();
		while (it.hasNext()) {
			PurchaseOrderStatusDescDataView po = (PurchaseOrderStatusDescDataView) it.next();
			invoiceToRender.add(po);
		}
		GenericReportResultView result = GenericReportResultView.createValue();
		result.setName(RefCodeNames.CUSTOMER_REPORT_TYPE_CD.INVOICE_LISTING);
		result.setTable(invoiceToRender);
		return result;
	}

	/**
	 * Gets appropriate notes for display for the invoice display screen
	 */
	public static OrderPropertyDataVector getInvoiceDistNotesForDisplay(int invoiceId, int orderId, Order orderEjb) throws
	RemoteException, DataNotFoundException {
		//set the order property notes
		OrderPropertyDataVector opdv = orderEjb.getOrderPropertyCollectionByInvoiceDist(invoiceId,
				RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
		if (opdv == null) {
			opdv = new OrderPropertyDataVector();
		}
		if (orderId > 0) {
			opdv.addAll(orderEjb.getOrderPropertyCollection(orderId, RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS));
		}
		sortAndRemoveDupsFromOrderPropertyDataVector(opdv);
		return opdv;
	}

	private static void sortAndRemoveDupsFromOrderPropertyDataVector(OrderPropertyDataVector dv) {
		Collections.sort(dv, ClwComparatorFactory.getOrderPropertyIdComparator());
		Iterator it = dv.iterator();
		HashSet found = new HashSet();
		while (it.hasNext()) {
			OrderPropertyData opd = (OrderPropertyData) it.next();
			Integer id = new Integer(opd.getOrderPropertyId());
			if (found.contains(id)) {
				it.remove();
			} else {
				found.add(id);
			}
		}
	}
}

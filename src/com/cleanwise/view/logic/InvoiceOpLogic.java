
package com.cleanwise.view.logic;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.math.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.dao.ItemSubstitutionDataAccess;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.utils.pdf.PdfInvoice;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

/**
 * <code>InvoiceOpLogic</code> implements the logic needed to
 * manipulate order records.
 *
 * @author durval
 */
public class InvoiceOpLogic {

    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request,
			    ActionForm form)
	throws Exception {

        initConstantList(request);
	//searchAll(request, form);    
	return;
    }
  
  
    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void search(HttpServletRequest request,
			      ActionForm form)
	throws Exception {

	HttpSession session = request.getSession();
	OrderOpSearchForm sForm = (OrderOpSearchForm)form;
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
  
       
	Order orderEjb   = factory.getOrderAPI();
        
        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
        IdVector storeIds = new IdVector();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            storeIds = appUser.getUserStoreAsIdVector();
        }
        if( sForm.getAccountId().trim().length() > 0 ) {
            searchCriteria.setAccountId(sForm.getAccountId().trim());
        }
        if( sForm.getDistributorId().trim().length() > 0 ) {
            searchCriteria.setDistributorId(sForm.getDistributorId().trim());
        }
        if( sForm.getErpOrderNum().trim().length() > 0 ) {
            searchCriteria.setErpOrderNum(sForm.getErpOrderNum().trim());
        }
        if( sForm.getErpPONum().trim().length() > 0 ) {
            searchCriteria.setErpPONum(sForm.getErpPONum().trim());
        }
        if( sForm.getWebOrderConfirmationNum().trim().length() > 0 ) {
            searchCriteria.setWebOrderConfirmationNum(sForm.getWebOrderConfirmationNum().trim());
        }
        if( sForm.getOrderDateRangeBegin().trim().length() > 0 ) {
            searchCriteria.setOrderDateRangeBegin(sForm.getOrderDateRangeBegin().trim());
        }
        if( sForm.getOrderDateRangeEnd().trim().length() > 0 ) {
            searchCriteria.setOrderDateRangeEnd(sForm.getOrderDateRangeEnd().trim());
        }
        if( sForm.getCustPONum().trim().length() > 0 ) {
            searchCriteria.setCustPONum(sForm.getCustPONum().trim());
        }
        if( sForm.getRefOrderNum().trim().length() > 0 ) {
            searchCriteria.setRefOrderNum(sForm.getRefOrderNum().trim());
        }
        if( sForm.getSiteId().trim().length() > 0 ) {
            searchCriteria.setSiteId(sForm.getSiteId().trim());
        }
        if( sForm.getSiteZipCode().trim().length() > 0 ) {
            searchCriteria.setSiteZipCode(sForm.getSiteZipCode().trim());
        }
        if( ! "".equals(sForm.getOrderStatus()) ) {
            searchCriteria.setOrderStatus(sForm.getOrderStatus());
        }
        if( sForm.getReferenceCode().trim().length() > 0 ) {
            searchCriteria.setReferenceCode(sForm.getReferenceCode().trim());
        }
        if( sForm.getShipFromId().trim().length() > 0 ) {
            searchCriteria.setShipFromId(sForm.getShipFromId().trim());
        }
        if( sForm.getPlacedBy().trim().length() > 0 ) {
            searchCriteria.setPlacedBy(sForm.getPlacedBy().trim());
        }
        if( ! "".equals(sForm.getMethod()) ) {
            searchCriteria.setMethod(sForm.getMethod());
        }
        if( sForm.getInvoiceDistNum().trim().length() > 0 ) {
            searchCriteria.setInvoiceDistNum(sForm.getInvoiceDistNum().trim());
        }        
        
        OrderStatusDescDataVector orderStatus = new OrderStatusDescDataVector();
        orderStatus = orderEjb.getOrderStatusDescCollection(searchCriteria, storeIds);
            
	sForm.setResultList(orderStatus);
    }

    /**
     *refreshes the data in the form based off the po_number that is supplied.
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors lookupByPurchaseOrder(HttpServletRequest request,
    ActionForm form) throws Exception {
        ActionErrors lErrors = new ActionErrors();
        HttpSession session = request.getSession();
	InvoiceOpDetailForm sForm = (InvoiceOpDetailForm)form;
        if(!Utility.isSet(sForm.getNewErpPoNum())){
            lErrors.add("newErpPoNum",new ActionError("variable.empty.error","newErpPoNum"));
            return lErrors;
        }
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
        PurchaseOrderStatusCriteriaData crit = new PurchaseOrderStatusCriteriaData();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            crit.setStoreIdVector(appUser.getUserStoreAsIdVector());
        }
        crit.setErpPONum(sForm.getNewErpPoNum());
        PurchaseOrderDataVector pos = factory.getPurchaseOrderAPI().getPurchaseOrderCollection(crit);
        if(pos.size() == 0){
            lErrors.add("newErpPoNum",new ActionError("invoice.couldNotFindPo",sForm.getNewErpPoNum()));
            return lErrors;
        }else if(pos.size() > 1){
            lErrors.add("newErpPoNum",new ActionError("invoice.invoice.foundMultiplePos",sForm.getNewErpPoNum()));
            return lErrors;
        }else{
            PurchaseOrderData po = (PurchaseOrderData) pos.get(0);
            getDetail(request, form, Integer.toString(po.getPurchaseOrderId()), true);
        }
        return lErrors;
    }
    
    /**
     * <code>searchAll</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void searchAll(HttpServletRequest request,
				 ActionForm form)
	throws Exception {

	HttpSession session = request.getSession();
	OrderOpSearchForm sForm = (OrderOpSearchForm)form;
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
    
        /*
	OrderOp orderEjb   = factory.getOrderOpAPI();

	OrderOpDataVector orders = new OrderOpDataVector();
	orders  = orderEjb.getAllOrderOps();
        
	sForm.setResultList(orders);
         */
    }
  
    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request,
			    ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
	OrderOpSearchForm sForm = (OrderOpSearchForm)form;
	if (sForm == null) {
	    return;
	}
	OrderStatusDescDataVector orders = 
	    (OrderStatusDescDataVector)sForm.getResultList();
	if (orders == null) {
	    return;
	}

	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(orders, sortField);
    }


    /**
     *  <code>sortItems</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sortItems(HttpServletRequest request,
				 ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();
	
        OrderOpDetailForm sForm =
            (OrderOpDetailForm)
            session.getAttribute("ORDER_OP_DETAIL_FORM");
	if (sForm == null) {
	    // not expecting this, but nothing to do if it is
	    return;
	}

	OrderItemDescDataVector orderItemDescList = 
	   (OrderItemDescDataVector)sForm.getOrderItemDescList();

	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(orderItemDescList, sortField);
    }


    /**
     *  <code>sortItemDetails</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sortItemDetails(HttpServletRequest request,
				 ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();
	
        OrderOpItemDetailForm sForm =
            (OrderOpItemDetailForm)
            session.getAttribute("ORDER_OP_ITEM_DETAIL_FORM");
	if (sForm == null) {
	    // not expecting this, but nothing to do if it is
	    return;
	}
    }

      
    
    /**
     *  <code>initConstantList</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@exception  Exception  if an error occurs
     */    
    public static void initConstantList(HttpServletRequest request)
	throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }    

        ListService listServiceEjb = factory.getListServiceAPI();

        if (null == session.getAttribute("Order.status.vector")) {
	    RefCdDataVector statusv =
		listServiceEjb.getRefCodesCollection("ORDER_STATUS_CD");
	    session.setAttribute("Order.status.vector", statusv);
	}
        
        if (null == session.getAttribute("Method.type.vector")) {
	    RefCdDataVector typev =
		listServiceEjb.getRefCodesCollection("METHOD_TYPE_CD");
	    session.setAttribute("Method.type.vector", typev);
	}

        if (null == session.getAttribute("ItemDetail.action.vector")) {
	    RefCdDataVector actionv =
		listServiceEjb.getRefCodesCollection("ORDER_ITEM_DETAIL_ACTION_CD");
	    session.setAttribute("ItemDetail.action.vector", actionv);
	}        
        
        if (null == session.getAttribute("Shipping.carrier.vector")) {
	    RefCdDataVector carrierv =
		listServiceEjb.getRefCodesCollection("SHIPPING_CARRIER_CD");
	    session.setAttribute("Shipping.carrier.vector", carrierv);
	}        
        
        if (null == session.getAttribute("Shipping.trackingType.vector")) {
	    RefCdDataVector typev =
		listServiceEjb.getRefCodesCollection("SHIPPING_TRACKING_TYPE_CD");
	    session.setAttribute("Shipping.trackingType.vector", typev);
	}        
        
    }
    
    /**
     * <code>getOrderStatusDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param orderId a <code>String</code> value
     * @exception Exception if an error occurs
     */  
    public static void getDetail(
				    HttpServletRequest request,
				    ActionForm form,
				    String purchaseOrderStatusId,
                                    boolean resetForm)
	throws Exception {
        
        InvoiceOpDetailForm detailForm = (InvoiceOpDetailForm) form;
        if (resetForm){
            detailForm.init();
        }

	HttpSession session = request.getSession();
        //if (!Utility.isSet(detailForm.getBatchNumS())){
        ///    if (Utility.isSet((String)session.getAttribute("lawson.apbatch"))){
        //        detailForm.setBatchNumS((String)session.getAttribute("lawson.apbatch"));
        //    }
        //}
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}
    
        
	Order orderEjb = factory.getOrderAPI();
        PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
        Distributor distEjb = factory.getDistributorAPI();
        //Lawson lawsonEjb = factory.getLawsonAPI();
	if( null == purchaseOrderStatusId || "".equals(purchaseOrderStatusId)) {
	    purchaseOrderStatusId = (String)session.getAttribute("purchaseOrderStatus.id");
	}
        else {
            session.setAttribute("purchaseOrderStatusId", purchaseOrderStatusId);
        }
        PurchaseOrderStatusDescDataView po = purchaseOrderEjb.getPurchaseOrderStatusDesc(Integer.parseInt(purchaseOrderStatusId));
        session.setAttribute("orderStatusId", Integer.toString(po.getOrderData().getOrderId()));
	//AddressDataVector remits = lawsonEjb.getRemitAddresses(po.getDistributorBusEntityData().getErpNum());
        //detailForm.setRemitToAddresses(remits);
        //if there is only one for this distributor set it to start with, otherwise leave it
        //blank and the user will have to choose
        DistShipFromAddressViewVector shipFroms = distEjb.getShipFromAddressCollection(po.getDistributorBusEntityData().getBusEntityId(),RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
        if(shipFroms.size() == 1){
            DistShipFromAddressView shipFrom = (DistShipFromAddressView) shipFroms.get(0);
            detailForm.setShipFromId(Integer.toString(shipFrom.getShipFromAddressId()));
        }
        
        
        /*Get only the notes from the vector for the selected order
	  getOrderPropertyVec takes care of making sure we only get notes, 
	  and only the notes for the proper order
	*/
	OrderPropertyDataVector orderPropertyDetailVec = orderEjb.getOrderPropertyVec(po.getOrderData().getOrderId());    
        OrderPropertyData orderPropertyDetail = OrderPropertyData.createValue();		
        if ( null != orderPropertyDetailVec && 0 < orderPropertyDetailVec.size()) {            
	
            //We want to see the notes in order of their being written
            DisplayListSort.sort(orderPropertyDetailVec, "propertyId");
		
            //set the OrderPropertyDetail to the latest object
            int lastItem = orderPropertyDetailVec.size()-1;	  
            orderPropertyDetail = (OrderPropertyData)orderPropertyDetailVec.get(lastItem);	
        }	
	
	OrderItemDescDataVector itemStatusDescV = 
          orderEjb.getOrderItemDescCollection(po.getOrderData().getOrderId(), 
                                              po.getPurchaseOrderData().getErpPoNum(),
                                              po.getPurchaseOrderData().getPurchaseOrderId());
        DisplayListSort.sort(itemStatusDescV,"erpPoLineNum");
        
	
        
        //detailForm.setOrderStatusDetail(orderStatusDetail);
        detailForm.setPurchaseOrderStatusDesc(po);
	detailForm.setOrderPropertyList(orderPropertyDetailVec);
	detailForm.setOrderPropertyDetail(orderPropertyDetail);
        detailForm.setOrderItemDescList(itemStatusDescV);        
        if(po.getDistributorBusEntityData() != null){
            try{
                DistributorData dist = distEjb.getDistributor(po.getDistributorBusEntityData().getBusEntityId());
                detailForm.setDistributorData(dist);
            }catch(DataNotFoundException e){
                detailForm.setDistributorData(DistributorData.createValue());
            }
        }
        
    
        CumulativeSummary sum = new CumulativeSummary(itemStatusDescV);
        detailForm.setAcceptedNum(sum.getAcceptedNum());
        detailForm.setBackorderedNum(sum.getBackorderedNum());
        detailForm.setInvoicedNum(sum.getInvoicedNum());
        detailForm.setLastDate(sum.getLastDate());
        detailForm.setOrderedNum(sum.getOrderedNum());
        detailForm.setReturnedNum(sum.getReturnedNum());
        detailForm.setShippedNum(sum.getShippedNum());
        detailForm.setSubstitutedNum(sum.getSubstitutedNum());
        
	//initialize the comstants lists for states and contries
	initConstantList(request);
        
        session.setAttribute("INVOICE_OP_DETAIL_FORM", detailForm);
        detailForm.setErpSystemCd(po.getPurchaseOrderData().getErpSystemCd());
    }
    
    
    /**
     * <codeupdateDistInvoice</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */  
    public static ActionErrors updateDistInvoice(
				    HttpServletRequest request,
				    ActionForm form)
	throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();
            
	InvoiceOpDetailForm detailForm = (InvoiceOpDetailForm) form;

	int orderId = detailForm.getPurchaseOrderStatusDesc().getOrderData().getOrderId();
        int storeId = detailForm.getPurchaseOrderStatusDesc().getOrderData().getStoreId();
        String erpSystemCd = detailForm.getPurchaseOrderStatusDesc().getOrderData().getErpSystemCd();
        int batchNum = 0;
        
	// Get a reference to the admin facade.
	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
    
	Order orderEjb = factory.getOrderAPI();
        PurchaseOrder poEjb = factory.getPurchaseOrderAPI();
        //Lawson lawsonEjb = factory.getLawsonAPI();

	InvoiceDistData invoiceDist = detailForm.getInvoiceDist();

        OrderStatusDescData orderStatusDescD = OrderStatusDescData.createValue();
        try {
            orderStatusDescD = orderEjb.getOrderStatusDesc(orderId);
        }
        catch (Exception e) {
        }
        OrderItemDescDataVector orderItemDescList = detailForm.getOrderItemDescList();
        InvoiceDistDetailDataVector invoiceDistDetailList = detailForm.getInvoiceDistDetailList();        

        AddressData shipFromAddress = AddressData.createValue();
        BigDecimal totalInvoicedAmount = new BigDecimal(0D);
        BigDecimal invoicedSubTotal = new BigDecimal(0D);
        Date optInvoiceDueDate = null;
        
        // validate the input fields
        if ((detailForm.getTotalAmountS() == null) || (detailForm.getTotalAmountS().trim().length() < 1)) {
            lUpdateErrors.add("invoicedistdetail", new ActionError("variable.empty.error", "Invoice's Total Amount"));
        }
        else {
            // check that it is a valid amount format
            try {
                BigDecimal totalAmount = 
                    CurrencyFormat.parse(detailForm.getTotalAmountS());
                totalInvoicedAmount = totalAmount;
                invoicedSubTotal = totalInvoicedAmount;
            } catch (ParseException pe) {
                lUpdateErrors.add("invocedistdetail", 
                        new ActionError("error.invalidNumberAmount",
						      "Invoice's Total Amount"));
            }
        }
        
        if( null != detailForm.getTotalFreightCostS() && detailForm.getTotalFreightCostS().trim().length() > 0) {
            // check that it is a valid amount format
            try {
                BigDecimal freight = 
                    CurrencyFormat.parse(detailForm.getTotalFreightCostS());
                invoiceDist.setFreight(freight);
                invoicedSubTotal = invoicedSubTotal.subtract(freight);
            } catch (ParseException pe) {
                lUpdateErrors.add("invocedistdetail", 
                        new ActionError("error.invalidNumberAmount",
						      "Invoice's Freight"));
            }
        }else {
            invoiceDist.setFreight(new BigDecimal(0));
        }

        if(null != detailForm.getTotalTaxCostS() && detailForm.getTotalTaxCostS().trim().length() > 0) {
            // check that it is a valid amount format
            try {
                BigDecimal tax = 
                    CurrencyFormat.parse(detailForm.getTotalTaxCostS());
                invoiceDist.setSalesTax(tax);
                invoicedSubTotal = invoicedSubTotal.subtract(tax);
            } catch (ParseException pe) {
                lUpdateErrors.add("invocedistdetail", 
                        new ActionError("error.invalidNumberAmount",
						      "Invoice's Tax"));
            }
        }else {
            invoiceDist.setSalesTax(new BigDecimal(0));
        }
        
        if(null != detailForm.getTotalMiscCostS() && detailForm.getTotalMiscCostS().trim().length() > 0) {
            // check that it is a valid amount format
            try {
                BigDecimal misc = 
                    CurrencyFormat.parse(detailForm.getTotalMiscCostS());
                invoiceDist.setMiscCharges(misc);
                invoicedSubTotal = invoicedSubTotal.subtract(misc);
            } catch (ParseException pe) {
                lUpdateErrors.add("invocedistdetail", 
                        new ActionError("error.invalidNumberAmount",
						      "Invoice's Misc Charges"));
            }
        }else {
            invoiceDist.setMiscCharges(new BigDecimal(0));
        }
        
        if(null != detailForm.getDiscountS() && detailForm.getDiscountS().trim().length() > 0) {
            // check that it is a valid amount format
            try {
                BigDecimal discount = 
                    CurrencyFormat.parse(detailForm.getDiscountS());
                invoiceDist.setDiscounts(discount);
                invoicedSubTotal = invoicedSubTotal.add(discount);
            } catch (ParseException pe) {
                lUpdateErrors.add("invocedistdetail", 
                        new ActionError("error.invalidNumberAmount",
						      "Invoice's Discounts"));
            }
        }else {
            invoiceDist.setDiscounts(new BigDecimal(0));
        }
        
        
        invoiceDist.setSubTotal(invoicedSubTotal);
        
        if ((detailForm.getInvoiceDateS() == null) || (detailForm.getInvoiceDateS().trim().length() < 1)) {
            lUpdateErrors.add("invoicedistdetail", new ActionError("variable.empty.error", "Invoice Date"));
        }
        else {
            // check that it is a valid Date format
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date invoiceDate =  null;
            try {
                invoiceDate  = simpleDateFormat.parse(detailForm.getInvoiceDateS());  
                invoiceDist.setInvoiceDate(invoiceDate);
            }
            catch (Exception e) {
                lUpdateErrors.add("invocedistdetail", 
                        new ActionError("error.invalidNumberAmount",
						      "Invoice Date"));
            }    
        }
        
        if (!((detailForm.getInvoiceDueDateS() == null) || (detailForm.getInvoiceDueDateS().trim().length() < 1))) {
            // check that it is a valid Date format
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date invoiceDate =  null;
            try {
                optInvoiceDueDate  = simpleDateFormat.parse(detailForm.getInvoiceDueDateS());  
            }
            catch (Exception e) {
                lUpdateErrors.add("Invoice Due Date", 
                        new ActionError("error.invalidNumberAmount","Invoice Due Date"));
            }    
        }
        if((detailForm.getDistErpNumCheck() == null) || (detailForm.getDistErpNumCheck().trim().length() < 1)) {
            lUpdateErrors.add("invoicedistdetail", new ActionError("variable.empty.error", "Vendor Erp Number"));
        }else if(!(detailForm.getDistErpNumCheck().trim().equals(detailForm.getDistributorData().getBusEntity().getErpNum()))){
            lUpdateErrors.add("invoicedistdetail", new ActionError("invoice.distributorErpNumMismatch", "Distributor Erp Number"));
        }
        if ((invoiceDist.getInvoiceNum() == null) || (invoiceDist.getInvoiceNum().trim().length() < 1)) {
            lUpdateErrors.add("invoicedistdetail", new ActionError("variable.empty.error", "Invoice / Shipment Number"));
        }
        invoiceDist.setCarrier(RefCodeNames.SHIPPING_CARRIER_CD.COMMON_CARRIER);
        invoiceDist.setTrackingType(RefCodeNames.SHIPPING_TRACKING_TYPE_CD.TRACKING_NUM);
        
        
        /*if ((invoiceDist.getCarrier() == null) || (invoiceDist.getCarrier().trim().length() < 1)) {
            lUpdateErrors.add("invoicedistdetail", new ActionError("variable.empty.error", "Carrier"));
        }
        if ((invoiceDist.getTrackingType() == null) || (invoiceDist.getTrackingType().trim().length() < 1)) {
            lUpdateErrors.add("invoicedistdetail", new ActionError("variable.empty.error", "Tracking Type"));
        }*/

        if ((detailForm.getShipFromId() == null) || (detailForm.getShipFromId().trim().length() < 1)) {
            lUpdateErrors.add("invoicedistdetail", new ActionError("variable.empty.error", "Dist Ship From ID"));
        } else {
            shipFromAddress = orderEjb.getAddress(Integer.parseInt(detailForm.getShipFromId().trim()), 
                                                    RefCodeNames.ADDRESS_TYPE_CD.DIST_SHIP_FROM); 
        }
        if(erpSystemCd==null) {
          if((detailForm.getBatchNumS()  == null) || (detailForm.getBatchNumS().trim().length() < 1)){
            lUpdateErrors.add("invoicedistdetail", new ActionError("variable.empty.error", "Batch Number"));
          }else{
            try{
                String batchS = detailForm.getBatchNumS();
                batchNum = Integer.parseInt(batchS);
                //session.setAttribute("lawson.apbatch",batchS);
            }catch (NumberFormatException e){
                lUpdateErrors.add("invoicedistdetail",new ActionError("error.invalidNumber","Batch Number"));
            }
          }
        }
        //validate item data
        boolean itemsSet = false;
        for(int i=0,len=detailForm.getOrderItemDescList().size();i<len;i++){
            OrderItemDescData itm = (OrderItemDescData) detailForm.getOrderItemDescList().get(i);
            if(Utility.isSet(itm.getItemQuantityS())){
                int matchQty = 0;
                try{
                    matchQty = Integer.parseInt(itm.getItemQuantityS());
                }catch (NumberFormatException e){
                    lUpdateErrors.add("invoicedistdetail",new ActionError("error.invalidNumber",itm.getItemSkuNumS()));
                    break;
                }
                itemsSet = true;
                int qtyInv = 0;
                for(int j=0,len2=itm.getInvoiceDistDetailList().size();j<len2;j++){
                    InvoiceDistDetailData iddd = (InvoiceDistDetailData) itm.getInvoiceDistDetailList().get(j);
                    qtyInv += iddd.getDistItemQuantity();
                }
                if (qtyInv + matchQty > itm.getOrderItem().getTotalQuantityOrdered()){
                    lUpdateErrors.add("invoicedistdetail",
                        new ActionError("invoice.invalidquantitymatched",new Integer(itm.getOrderItem().getErpPoLineNum())));
                }
            }
        }
        if(!itemsSet){
            lUpdateErrors.add("updateDistInvoice",new ActionError("invoice.noitems"));
        }
        
	if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }
        
        String userName = (String)session.getAttribute(Constants.USER_NAME);                
        
        // set up the other invoiceDist fields
        invoiceDist.setOrderId(orderId);
        invoiceDist.setErpPoNum("");
        invoiceDist.setStoreId(storeId);
        invoiceDist.setErpSystemCd(erpSystemCd);
        
        // set the ship from info according to the shipFromId
        if (null != shipFromAddress) {
            invoiceDist.setShipFromName(shipFromAddress.getName1());
            invoiceDist.setShipFromAddress1(shipFromAddress.getAddress1());
            invoiceDist.setShipFromAddress2(shipFromAddress.getAddress2());
            invoiceDist.setShipFromAddress3(shipFromAddress.getAddress3());
            invoiceDist.setShipFromAddress4(shipFromAddress.getAddress4());
            invoiceDist.setShipFromCity(shipFromAddress.getCity());
            invoiceDist.setShipFromState(shipFromAddress.getStateProvinceCd());
            invoiceDist.setShipFromPostalCode(shipFromAddress.getPostalCode());
            invoiceDist.setShipFromCountry(shipFromAddress.getCountryCd());
        }
        if (null != orderStatusDescD ) {
            OrderAddressData shipToAddress = orderStatusDescD.getShipTo();
            if (null != shipToAddress) {
                invoiceDist.setShipToName(shipToAddress.getShortDesc());
                invoiceDist.setShipToAddress1(shipToAddress.getAddress1());
                invoiceDist.setShipToAddress2(shipToAddress.getAddress2());
                invoiceDist.setShipToAddress3(shipToAddress.getAddress3());
                invoiceDist.setShipToAddress4(shipToAddress.getAddress4());
                invoiceDist.setShipToCity(shipToAddress.getCity());
                invoiceDist.setShipToState(shipToAddress.getStateProvinceCd());
                invoiceDist.setShipToPostalCode(shipToAddress.getPostalCode());
                invoiceDist.setShipToCountry(shipToAddress.getCountryCd());
            }
        }
        
        // if the dist order # is empty, enter invoice # automatically
        if (null == invoiceDist.getDistOrderNum() || invoiceDist.getDistOrderNum().trim().length() < 1 ) {
            invoiceDist.setDistOrderNum(invoiceDist.getInvoiceNum());
        }
        // if the dist tracking # is empty, enter invoice # automatically
        if (null == invoiceDist.getTrackingNum() || invoiceDist.getTrackingNum().trim().length() < 1 ) {
            invoiceDist.setTrackingNum(invoiceDist.getInvoiceNum());
        }
        invoiceDist.setAddBy(userName);
        invoiceDist.setModBy(userName);
        
        // if the order has several ERP_PO_NUMBER, we need several invoiceDist
        ArrayList newInvoiceDistList = new ArrayList();
        
        //holds all of the actions that we are going to add
        OrderItemActionDataVector orderItemActionDataVector = new OrderItemActionDataVector();
        
        for (int i = 0; i < orderItemDescList.size(); i ++) {
            OrderItemData orderItem = ((OrderItemDescData) orderItemDescList.get(i)).getOrderItem();
            String itemErpPoNum = orderItem.getErpPoNum();
            if (null == itemErpPoNum ) {
                itemErpPoNum = new String("");
            }

            boolean foundFlag = false;
            
            for (int j = 0 ; j < newInvoiceDistList.size(); j++) {
                InvoiceDistData tempInvoiceDist = ((InvoiceDistStruct)newInvoiceDistList.get(j)).getInvoiceDistData();
                if (itemErpPoNum.equals(tempInvoiceDist.getErpPoNum())) {
                    foundFlag = true;
                    break;
                }
            }
            if (false == foundFlag) {
                InvoiceDistData newInvoiceDist = InvoiceDistData.createValue();
                // copy the info into the new InvoiceDist
                newInvoiceDist.setBusEntityId(invoiceDist.getBusEntityId());
                newInvoiceDist.setOrderId(invoiceDist.getOrderId());                
                newInvoiceDist.setErpPoNum(itemErpPoNum);
                newInvoiceDist.setDistOrderNum(invoiceDist.getDistOrderNum());
                newInvoiceDist.setInvoiceNum(invoiceDist.getInvoiceNum().toUpperCase());
                newInvoiceDist.setInvoiceDate(invoiceDist.getInvoiceDate());
                newInvoiceDist.setDistShipmentNum(invoiceDist.getDistShipmentNum());
                newInvoiceDist.setSubTotal(invoiceDist.getSubTotal());
                newInvoiceDist.setFreight(invoiceDist.getFreight());
                newInvoiceDist.setSalesTax(invoiceDist.getSalesTax());
                newInvoiceDist.setDiscounts(invoiceDist.getDiscounts());
                newInvoiceDist.setMiscCharges(invoiceDist.getMiscCharges());
                newInvoiceDist.setAddBy(invoiceDist.getAddBy());
                newInvoiceDist.setModBy(invoiceDist.getModBy());
                newInvoiceDist.setCarrier(invoiceDist.getCarrier());
                newInvoiceDist.setTrackingType(invoiceDist.getTrackingType());
                newInvoiceDist.setTrackingNum(invoiceDist.getTrackingNum());
                newInvoiceDist.setShipFromName(invoiceDist.getShipFromName());
                newInvoiceDist.setShipFromAddress1(invoiceDist.getShipFromAddress1());
                newInvoiceDist.setShipFromAddress2(invoiceDist.getShipFromAddress2());
                newInvoiceDist.setShipFromAddress3(invoiceDist.getShipFromAddress3());
                newInvoiceDist.setShipFromAddress4(invoiceDist.getShipFromAddress4());
                newInvoiceDist.setShipFromCity(invoiceDist.getShipFromCity());
                newInvoiceDist.setShipFromState(invoiceDist.getShipFromState());
                newInvoiceDist.setShipFromPostalCode(invoiceDist.getShipFromPostalCode());
                newInvoiceDist.setShipFromCountry(invoiceDist.getShipFromCountry());
                newInvoiceDist.setShipToName(invoiceDist.getShipToName());
                newInvoiceDist.setShipToAddress1(invoiceDist.getShipToAddress1());
                newInvoiceDist.setShipToAddress2(invoiceDist.getShipToAddress2());
                newInvoiceDist.setShipToAddress3(invoiceDist.getShipToAddress3());
                newInvoiceDist.setShipToAddress4(invoiceDist.getShipToAddress4());
                newInvoiceDist.setShipToCity(invoiceDist.getShipToCity());
                newInvoiceDist.setShipToState(invoiceDist.getShipToState());
                newInvoiceDist.setShipToPostalCode(invoiceDist.getShipToPostalCode());
                newInvoiceDist.setShipToCountry(invoiceDist.getShipToCountry());
                newInvoiceDist.setInvoiceDistSourceCd(RefCodeNames.INVOICE_DIST_SOURCE_CD.WEB);
                newInvoiceDist.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED);
                newInvoiceDist.setStoreId(invoiceDist.getStoreId());
                newInvoiceDist.setErpSystemCd(invoiceDist.getErpSystemCd());
                //newInvoiceDist.setBusEntityId(detailForm.getPurchaseOrderStatusDesc().getDistributorBusEntityData().getBusEntityId());
                newInvoiceDistList.add(new InvoiceDistStruct(newInvoiceDist));
            }            
        }
        
        // calc the sum of line total to see if it is match the inputed invoicedSubTotal
        double lineTotalSum = 0D;
        for (int i = 0; i < orderItemDescList.size(); i++) {
            OrderItemDescData orderItemDesc = (OrderItemDescData)orderItemDescList.get(i);
            // newInvoiceDistDetail is the InvoiceDistDetailData with inputed info
            InvoiceDistDetailData newInvoiceDistDetail = orderItemDesc.getNewInvoiceDistDetail();
            
            // add new invoiceDistDetail only if the quantity is filled in
            if ( 0 < orderItemDesc.getItemQuantityS().trim().length() ) {
                int quantity = 0;    
                BigDecimal price = new BigDecimal(0D);
                quantity = Integer.parseInt(orderItemDesc.getItemQuantityS().trim());
                if(0 < orderItemDesc.getCwCostS().trim().length()) {                            
                    try {
                        BigDecimal itemCost = 
                                CurrencyFormat.parse(orderItemDesc.getCwCostS());
                        price = itemCost;        
                    } catch (ParseException pe) {
                        lUpdateErrors.add("invocedistdetail", 
                                    new ActionError("error.invalidNumberAmount",
                                                            "Item #" + orderItemDesc.getOrderItem().getOrderLineNum() +"'s CW Cost"));
                        return lUpdateErrors;
                    }
                }
                else {
                    price = orderItemDesc.getOrderItem().getDistItemCost();
                }

                // calc the line total for this item
                if (null != price) {
                    lineTotalSum += price.doubleValue() * quantity;
                }                                
            }
        }
        if ( 0.01 <= java.lang.Math.abs(lineTotalSum - invoiceDist.getSubTotal().doubleValue())) {
            lUpdateErrors.add("invocedistdetail", 
                           new ActionError("error.notMatch","Invoiced Sub-Total","the sum of line totals."));
            return lUpdateErrors;            
        }        

        // now adding the invoiceDistDetail along with invoiceDist
        // into database
        boolean addDetailFlag = false;
        for (int i = 0; i < orderItemDescList.size(); i++) {
            OrderItemDescData orderItemDesc = (OrderItemDescData)orderItemDescList.get(i);
            // newInvoiceDistDetail is the InvoiceDistDetailData with inputed info
            InvoiceDistDetailData newInvoiceDistDetail = orderItemDesc.getNewInvoiceDistDetail();
            // orgInvoiceDistDetail is the InvoiceDistDetailData with OrderItemData info, 
            // according to IntegrationServices, we save this one into CLW_INVOICE_DIST_DETAIL
            InvoiceDistDetailData orgInvoiceDistDetail = InvoiceDistDetailData.createValue();
            
            // add new invoiceDistDetail only any of the following fields is filled
            if ( 0 < orderItemDesc.getItemQuantityS().trim().length() ) { 
                newInvoiceDistDetail.setItemQuantity(Integer.parseInt(orderItemDesc.getItemQuantityS().trim()));

                if(0 < orderItemDesc.getCwCostS().trim().length()) {
                    try {
                        BigDecimal itemCost = CurrencyFormat.parse(orderItemDesc.getCwCostS());
                        if(itemCost.compareTo(orderItemDesc.getOrderItem().getDistItemCost())!=0){
                            newInvoiceDistDetail.setAdjustedCost(itemCost);
                        }
                        newInvoiceDistDetail.setItemCost(itemCost);
                    } catch (ParseException pe) {
                        lUpdateErrors.add("invocedistdetail", 
                                    new ActionError("error.invalidNumberAmount",
                                                            "Item #" + orderItemDesc.getOrderItem().getOrderLineNum() +"'s CW Cost"));
                        return lUpdateErrors;
                    }
                }
                else {
                    newInvoiceDistDetail.setItemCost(orderItemDesc.getOrderItem().getDistItemCost());
                }

                // calc the line total for this item
                if (null != newInvoiceDistDetail.getItemCost()) {
                    newInvoiceDistDetail.setLineTotal(new BigDecimal(newInvoiceDistDetail.getItemCost().doubleValue() * newInvoiceDistDetail.getItemQuantity()));
                }
                
                
                if (0 < orderItemDesc.getDistLineNumS().trim().length()) {
                    newInvoiceDistDetail.setDistLineNumber(Integer.parseInt(orderItemDesc.getDistLineNumS().trim()));
                }
                else {
                    newInvoiceDistDetail.setDistLineNumber(orderItemDesc.getOrderItem().getOrderLineNum());
                }
                                    
                if (0 < orderItemDesc.getItemSkuNumS().trim().length()) {
                    newInvoiceDistDetail.setItemSkuNum(Integer.parseInt(orderItemDesc.getItemSkuNumS().trim()));
                }
                else {
                    newInvoiceDistDetail.setItemSkuNum(orderItemDesc.getOrderItem().getItemSkuNum());
                }
                
                if ( null == newInvoiceDistDetail.getDistItemSkuNum() || 1 > newInvoiceDistDetail.getDistItemSkuNum().trim().length()) {
                    newInvoiceDistDetail.setDistItemSkuNum(orderItemDesc.getOrderItem().getDistItemSkuNum());
                }

                if ( null == newInvoiceDistDetail.getItemShortDesc() || 1 > newInvoiceDistDetail.getItemShortDesc().trim().length()) {
                    newInvoiceDistDetail.setItemShortDesc(orderItemDesc.getOrderItem().getItemShortDesc());
                }

                if ( null == newInvoiceDistDetail.getItemUom() || 1 > newInvoiceDistDetail.getItemUom().trim().length()) {
                    newInvoiceDistDetail.setItemUom(orderItemDesc.getOrderItem().getItemUom());
                }

                if ( null == newInvoiceDistDetail.getItemPack() || 1 > newInvoiceDistDetail.getItemPack().trim().length()) {
                    newInvoiceDistDetail.setItemPack(orderItemDesc.getOrderItem().getItemPack());
                }
                
                newInvoiceDistDetail.setOrderItemId(orderItemDesc.getOrderItem().getOrderItemId());
                newInvoiceDistDetail.setAddBy(userName);
                newInvoiceDistDetail.setModBy(userName);

                // set up the InvoiceDistDetailData to be inserted into CLW_INVOICE_DIST_DETAIL    
                orgInvoiceDistDetail.setItemQuantity(newInvoiceDistDetail.getItemQuantity());
                orgInvoiceDistDetail.setDistItemQuantity(newInvoiceDistDetail.getItemQuantity());
                orgInvoiceDistDetail.setItemCost(orderItemDesc.getOrderItem().getDistItemCost());
                orgInvoiceDistDetail.setLineTotal(newInvoiceDistDetail.getLineTotal());
                orgInvoiceDistDetail.setDistLineNumber(newInvoiceDistDetail.getDistLineNumber());
                orgInvoiceDistDetail.setOrderItemId(orderItemDesc.getOrderItem().getOrderItemId());
                orgInvoiceDistDetail.setErpPoLineNum(orderItemDesc.getOrderItem().getErpPoLineNum());
                orgInvoiceDistDetail.setDistItemSkuNum(orderItemDesc.getOrderItem().getDistItemSkuNum());
                orgInvoiceDistDetail.setDistItemShortDesc(orderItemDesc.getOrderItem().getDistItemShortDesc());
                orgInvoiceDistDetail.setDistItemUom(orderItemDesc.getOrderItem().getDistItemUom());
                orgInvoiceDistDetail.setDistItemPack(orderItemDesc.getOrderItem().getDistItemPack());
                orgInvoiceDistDetail.setItemSkuNum(orderItemDesc.getOrderItem().getItemSkuNum());
                orgInvoiceDistDetail.setItemShortDesc(orderItemDesc.getOrderItem().getItemShortDesc());
                orgInvoiceDistDetail.setItemUom(orderItemDesc.getOrderItem().getItemUom());
                orgInvoiceDistDetail.setItemPack(orderItemDesc.getOrderItem().getItemPack());
                orgInvoiceDistDetail.setAdjustedCost(newInvoiceDistDetail.getAdjustedCost());
                orgInvoiceDistDetail.setAddBy(userName);
                orgInvoiceDistDetail.setModBy(userName);
                
                
                String itemErpPoNum = orderItemDesc.getOrderItem().getErpPoNum();
                if (null == itemErpPoNum ) {
                    itemErpPoNum = new String("");
                }

                InvoiceDistStruct currentInvoiceDist = null;
                for(int j = 0 ; j < newInvoiceDistList.size(); j++) {
                    
                    if (itemErpPoNum.equals(((InvoiceDistStruct) newInvoiceDistList.get(j)).getInvoiceDistData().getErpPoNum())) {
                        currentInvoiceDist = (InvoiceDistStruct) newInvoiceDistList.get(j);
                        currentInvoiceDist.addInvoiceDetailData(orgInvoiceDistDetail);
                        currentInvoiceDist.addOrderItemDescData(orderItemDesc);
                        if (0 != currentInvoiceDist.getInvoiceDistData().getInvoiceDistId()) {
                            //not sure this is possible
                            newInvoiceDistDetail.setInvoiceDistId(currentInvoiceDist.getInvoiceDistData().getInvoiceDistId());
                            orgInvoiceDistDetail.setInvoiceDistId(newInvoiceDistDetail.getInvoiceDistId());
                        }
                        break;
                    }
                }
                
                
                /*try{
                    orderEjb.addInvoiceDistDetail(orgInvoiceDistDetail);
                }catch (Exception e){
                    lUpdateErrors.add("updateInvoiceDist",new ActionError("error.genericError",e.getMessage()));
                    return lUpdateErrors;
                }*/
                addDetailFlag = true;
                
                // if the inputed itemSkuNum is not equal to the orderItem's we add a substitution
                boolean substitutedFlag = false;
                int substitutionId = 0;
                if(newInvoiceDistDetail.getItemSkuNum() != orderItemDesc.getOrderItem().getItemSkuNum()) {
                    substitutedFlag = true;
                    ItemSubstitutionData substitutionD = ItemSubstitutionData.createValue();
                    substitutionD.setOrderId(orderItemDesc.getOrderItem().getOrderId());
                    substitutionD.setOrderItemId(orderItemDesc.getOrderItem().getOrderItemId());
                    substitutionD.setItemSkuNum(newInvoiceDistDetail.getItemSkuNum());
                    substitutionD.setDistItemSkuNum(newInvoiceDistDetail.getDistItemSkuNum());
                    substitutionD.setItemShortDesc(newInvoiceDistDetail.getItemShortDesc());
                    substitutionD.setItemUom(newInvoiceDistDetail.getItemUom());
                    substitutionD.setItemPack(newInvoiceDistDetail.getItemPack());
                    substitutionD.setItemQuantity(newInvoiceDistDetail.getItemQuantity());
                    substitutionD.setItemDistCost(newInvoiceDistDetail.getItemCost());
                    substitutionD.setAddBy(newInvoiceDistDetail.getAddBy());
                    substitutionD.setModBy(newInvoiceDistDetail.getModBy());
                    
                    ItemSubstitutionData newSubstitutionD = orderEjb.addItemSubstitution(substitutionD);
                    substitutionId = newSubstitutionD.getItemSubstitutionId();
                }
                
                // add the substituted action
                if(true == substitutedFlag) {
                    Date currentDate = new Date();
                    OrderItemActionData substitutedAction = OrderItemActionData.createValue();
                    substitutedAction.setOrderId(orderItemDesc.getOrderItem().getOrderId());
                    substitutedAction.setOrderItemId(orderItemDesc.getOrderItem().getOrderItemId());
                    substitutedAction.setAffectedSku(String.valueOf(newInvoiceDistDetail.getItemSkuNum()));
                    substitutedAction.setAffectedTable(ItemSubstitutionDataAccess.CLW_ITEM_SUBSTITUTION);
                    substitutedAction.setAffectedId(substitutionId);
                    substitutedAction.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SUBSTITUTED);
                    substitutedAction.setQuantity(newInvoiceDistDetail.getItemQuantity());
                    substitutedAction.setActionDate(invoiceDist.getInvoiceDate());
                    substitutedAction.setActionTime(invoiceDist.getInvoiceDate());
                    substitutedAction.setAddBy(newInvoiceDistDetail.getAddBy());
                    substitutedAction.setModBy(newInvoiceDistDetail.getModBy());
                    
                    orderEjb.addOrderItemAction(substitutedAction);
                }
                
                // add the shipped action
                Date currentDate = new Date();
                OrderItemActionData shippedAction = OrderItemActionData.createValue();
                shippedAction.setOrderId(orderItemDesc.getOrderItem().getOrderId());
                shippedAction.setOrderItemId(orderItemDesc.getOrderItem().getOrderItemId());
                shippedAction.setAffectedSku(String.valueOf(newInvoiceDistDetail.getItemSkuNum()));
                shippedAction.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED);
                shippedAction.setQuantity(newInvoiceDistDetail.getItemQuantity());
                shippedAction.setActionDate(invoiceDist.getInvoiceDate());
                shippedAction.setActionTime(invoiceDist.getInvoiceDate());
                shippedAction.setAddBy("invoice op");
                shippedAction.setModBy(newInvoiceDistDetail.getModBy());
                
                orderItemActionDataVector.add(shippedAction);
                
                // add the invoiced action
                OrderItemActionData invoicedAction = OrderItemActionData.createValue();
                invoicedAction.setOrderId(orderItemDesc.getOrderItem().getOrderId());
                invoicedAction.setOrderItemId(orderItemDesc.getOrderItem().getOrderItemId());
                invoicedAction.setAffectedSku(String.valueOf(newInvoiceDistDetail.getItemSkuNum()));
                invoicedAction.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_INVOICED);
                invoicedAction.setQuantity(newInvoiceDistDetail.getItemQuantity());
                invoicedAction.setActionDate(invoiceDist.getInvoiceDate());
                invoicedAction.setActionTime(invoiceDist.getInvoiceDate());
                invoicedAction.setAddBy(newInvoiceDistDetail.getAddBy());
                invoicedAction.setModBy(newInvoiceDistDetail.getModBy());
                
                orderItemActionDataVector.add(invoicedAction);                
                
            } // end of if the user input something.
                
        } // end of the loop for orderItemDescList

            
            try{
                for(int j=0;j<newInvoiceDistList.size();j++){
                    InvoiceDistStruct invoice = (InvoiceDistStruct) newInvoiceDistList.get(j);
                    
                    java.util.Hashtable terrCond = new java.util.Hashtable();
                    terrCond.put("postalCode", invoice.getInvoiceDistData().getShipToPostalCode());
                    BusEntityTerrViewVector distTerr = factory.getDistributorAPI().getDistributorZipCodes(detailForm.getDistributorData().getBusEntity().getBusEntityId(),terrCond);
                    
                    String distErpNum = detailForm.getDistributorData().getBusEntity().getErpNum();
                    //Boolean errorOnOverChargeFrt = detailForm.getDistributorData().getExceptionOnOverchargedFreight();
                    BigDecimal poItemTotals = detailForm.getPurchaseOrderStatusDesc().getPurchaseOrderData().getLineItemTotal();
                    String remitTo = detailForm.getRemitTo();
                    invoice.getInvoiceDistData().setRemitTo(remitTo); //copy it from throws form
                    String erpPoNum = detailForm.getPurchaseOrderStatusDesc().getPurchaseOrderData().getErpPoNum();
                    InvoiceDistDataVector existingIvoices = orderEjb.getInvoiceDistCollection(null,null,null,erpPoNum,null,null,null);
                    OrderItemDataVector orderItems = Utility.toOrderItemDataVector(invoice.getOrderItemDescDataVector());
		    FreightHandlerView fhv = StoreVendorInvoiceLogic.getFreightHandler(orderItems);
                    DistributorInvoiceFreightTool frtTool = 
                        new DistributorInvoiceFreightTool(detailForm.getPurchaseOrderStatusDesc().getPurchaseOrderData().getLineItemTotal(),
                        orderItems,invoice.getInvoiceDistData(),detailForm.getDistributorData(),fhv,existingIvoices,distTerr,null);
                    if(!frtTool.isFreightHandlerFreightAllowed() && detailForm.getOrderRoutedWarningCount() == 0){
                        detailForm.setOrderRoutedWarningCount(detailForm.getOrderRoutedWarningCount() + 1);
                        lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("invoice.shippedViaFreightHandlerWithFrieght"));
                        return lUpdateErrors;
                    }else if(frtTool.isOverMinimumOrderWithFrieght() && detailForm.getMinimumOrderWarningCount() == 0){
                        detailForm.setMinimumOrderWarningCount(detailForm.getMinimumOrderWarningCount() + 1);
                        BigDecimal minimumOrderAmount = detailForm.getDistributorData().getMinimumOrderAmount();
                        lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("invoice.overMinimumOrderWithFrieght",detailForm.getPurchaseOrderStatusDesc().getPurchaseOrderData().getPurchaseOrderTotal(),minimumOrderAmount));
                        return lUpdateErrors;
                    }else if(frtTool.isBackOrderWithFreight() && detailForm.getFrieghtOnBackorderedWarningCount() == 0){
                        detailForm.setFrieghtOnBackorderedWarningCount(detailForm.getFrieghtOnBackorderedWarningCount() + 1);
                        lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("invoice.backOrderWithFreight"));
                        return lUpdateErrors;
                    }else if(frtTool.isFreeTerritoryWithFreight() && detailForm.getFrieghtOnFreeTerritoryWarningCount() == 0){
                        detailForm.setFrieghtOnFreeTerritoryWarningCount(detailForm.getFrieghtOnFreeTerritoryWarningCount() + 1);
                        lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("invoice.freeTerritoryWithFreight"));
                        return lUpdateErrors;
                    }else{
                        String voucherNum = null;  
                        invoice.setVoucher(voucherNum);
                        invoice.getInvoiceDistData().setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PROCESS_ERP);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                lUpdateErrors.add("updateDistInvoice",new ActionError("error.genericError","Error: "+e.getMessage()));
                return lUpdateErrors;
            }
            try{
                for(int j=0;j<newInvoiceDistList.size();j++){
                    InvoiceDistStruct invoice = (InvoiceDistStruct) newInvoiceDistList.get(j);
                    invoice.getInvoiceDistData().setBatchNumber(batchNum);
                    invoice.getInvoiceDistData().setBusEntityId(detailForm.getPurchaseOrderStatusDesc().getDistributorBusEntityData().getBusEntityId());
                    PurchaseOrderData poD = detailForm.getPurchaseOrderStatusDesc().getPurchaseOrderData();
                    invoice.getInvoiceDistData().setErpPoNum(poD.getErpPoNum());
                    invoice.getInvoiceDistData().setStoreId(poD.getStoreId());
                    invoice.getInvoiceDistData().setErpSystemCd(poD.getErpSystemCd());
                    invoice.getInvoiceDistData().setInvoiceDistSourceCd(RefCodeNames.INVOICE_DIST_SOURCE_CD.WEB);
                    InvoiceDistData invoiceData = poEjb.addUpdateInvoiceDistData(invoice.getInvoiceDistData());
                    OrderPropertyData voucher = OrderPropertyData.createValue();
                    voucher.setAddBy(userName);
                    voucher.setModBy(userName);
                    voucher.setAddDate(new Date());
                    voucher.setModDate(new Date());
                    voucher.setInvoiceDistId(invoiceData.getInvoiceDistId());
                    voucher.setOrderId(orderId);
                    voucher.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                    voucher.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.VOUCHER_NUMBER);
                    voucher.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.VOUCHER_NUMBER);
                    voucher.setValue(invoice.getVoucher());
                    //sets the voucher number to the last voucher number, this 
                    //breaks the whole multiple invoices a little, but this functionality
                    //is irrelevant given the new searching functionality.
                    detailForm.setVoucher(invoice.getVoucher());
                    for(int i=0;i<invoice.getInvoiceDistDetailDataVector().size();i++){
                        InvoiceDistDetailData detail = (InvoiceDistDetailData)invoice.getInvoiceDistDetailDataVector().get(i);
                        detail.setInvoiceDistId(invoiceData.getInvoiceDistId());
                        detail = orderEjb.addInvoiceDistDetail(detail);
                    }
                    detailForm.setInvoiceDistDetailList(invoice.getInvoiceDistDetailDataVector());
                    detailForm.setInvoiceDist(invoice.getInvoiceDistData());
                    
                }
                for(int i=0;i<orderItemActionDataVector.size();i++){
                    orderEjb.addOrderItemAction((OrderItemActionData)orderItemActionDataVector.get(i));
                }
            }catch (Exception e){
                e.printStackTrace();
                lUpdateErrors.add("updateInvoiceDist",new ActionError("error.genericError","EJB Error, (Invoice *WAS* sent to ERP): "+e.getMessage()));
                return lUpdateErrors;
            }
        
        return lUpdateErrors;
    }


    /**
     *  <code>searchDistSku</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void searchDistSku(HttpServletRequest request,
			    ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
	InvoiceOpDetailForm detailForm = (InvoiceOpDetailForm)form;
	if (detailForm == null) {
	    return;
	}
        
	OrderItemDescDataVector orderItemDescList = detailForm.getOrderItemDescList();
	if ( null == orderItemDescList || 0 == orderItemDescList.size()) {
	    return;
	}
        
        String distSku = detailForm.getDistSku();
        if (null == distSku || 1 > distSku.trim().length()) {
            return;
        }
        
        distSku = distSku.toUpperCase();
        OrderItemDescDataVector newOrderItemDescList = new OrderItemDescDataVector();
        for(int i = 0; i < orderItemDescList.size(); i++) {
            OrderItemDescData orderItemDesc = (OrderItemDescData)orderItemDescList.get(i);
            String distItemSkuNum = orderItemDesc.getOrderItem().getDistItemSkuNum();
            if (null == distItemSkuNum) {
                distItemSkuNum = new String("");
            }
            if ( -1 != distItemSkuNum.toUpperCase().indexOf(distSku)) {
                newOrderItemDescList.add(orderItemDesc);
                orderItemDescList.remove(i);
                i--;
            }
        }
        
        for(int i = 0; i < orderItemDescList.size(); i++) {
            OrderItemDescData orderItemDesc = (OrderItemDescData)orderItemDescList.get(i);
            newOrderItemDescList.add(orderItemDesc);
        }
        
        detailForm.setOrderItemDescList(newOrderItemDescList);
    }


    
    
    /**
     * <code>getOrderItemDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param OrderItemId a <code>String</code> value
     * @exception Exception if an error occurs
     */  
    public static void getOrderItemDetail(
				    HttpServletRequest request,
				    ActionForm form,
				    String OrderItemId)
	throws Exception {

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
    
        
	Order orderEjb = factory.getOrderAPI();
	if( null == OrderItemId || "".equals(OrderItemId)) {
	    OrderItemId = (String)session.getAttribute("OrderItem.id");
	}
        
        OrderItemDescData orderItemDescD = orderEjb.getOrderItemDescByItem(Integer.parseInt(OrderItemId));
	//OrderItemData OrderItemDetail = orderEjb.getOrderItem(Integer.parseInt(OrderItemId));
        //OrderItemDetailDataVector itemDetailV = orderEjb.getOrderItemDetailCollection(Integer.parseInt(OrderItemId), 0);
                
	OrderOpItemDetailForm detailForm = (OrderOpItemDetailForm) form;
	
        detailForm.setOrderItemDesc(orderItemDescD);
        //detailForm.setOrderItemDetail(OrderItemDetail);
        //detailForm.setOrderItemDetailList(itemDetailV);        
            
    }
    

    /**
     * <code>getOrderStatusDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param orderId a <code>String</code> value
     * @exception Exception if an error occurs
     */  
    public static void getOrderNotes(
				    HttpServletRequest request,
				    ActionForm form,
				    String orderId,
                                    String orderItemId,
                                    String noteId)
	throws Exception {

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
    
        
	Order orderEjb = factory.getOrderAPI();
	if( null == orderId || "".equals(orderId)) {
            OrderOpDetailForm orderDetailForm = (OrderOpDetailForm)session.getAttribute("ORDER_OP_DETAIL_FORM");
            if (null != orderDetailForm ) {
                orderId = String.valueOf(orderDetailForm.getOrderStatusDetail().getOrderDetail().getOrderId());
            }
            else {
                orderId = (String)session.getAttribute("OrderStatus.id");
            }
	}
        
        OrderPropertyDataVector orderNotes = new OrderPropertyDataVector();
        if (null != orderItemId && ! "".equals(orderItemId)) {
            orderNotes = orderEjb.getOrderItemPropertyCollection(Integer.parseInt(orderItemId),
                            RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        }
        else if (null != orderId && ! "".equals(orderId)) {
            orderNotes = orderEjb.getOrderPropertyCollection(Integer.parseInt(orderId),
                            RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        }
        
        OrderPropertyData note = OrderPropertyData.createValue();
        if (null != noteId && ! "".equals(noteId)) {
            note = orderEjb.getOrderProperty(Integer.parseInt(noteId), RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        }
        
        OrderOpNoteForm orderNoteForm = (OrderOpNoteForm)form;
        orderNoteForm.setOrderPropertyDetail(note);
        orderNoteForm.setOrderPropertyList(orderNotes);
    }
    
    
    /**
     * <code>AddOrderNote</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */        
    public static void addOrderNote(
				   HttpServletRequest request,
				   ActionForm form,
                                   String orderId,
                                   String orderItemId,
                                   String noteType)
	throws Exception {
        
	OrderOpNoteForm dForm = (OrderOpNoteForm) form;
        HttpSession session = request.getSession();
        
	dForm = new OrderOpNoteForm();

        if (null == noteType || "".equals(noteType)) {
            noteType = new String("order");            
        }
        dForm.setOrderNoteType(noteType);
        
        if ("order".equals(noteType)) {
            OrderOpDetailForm orderDetailForm = (OrderOpDetailForm)session.getAttribute("ORDER_OP_DETAIL_FORM");
            if (null != orderDetailForm ) {
                orderId = String.valueOf(orderDetailForm.getOrderStatusDetail().getOrderDetail().getOrderId());
            }
            else {
                orderId = (String)session.getAttribute("OrderStatus.id");
            }
            dForm.setOrderId(orderId);
        }
        else if ("item".equals(noteType)) {
            dForm.setOrderItemId(orderItemId);            
        }

        session.setAttribute("ORDER_OP_NOTE_FORM", dForm);
    
    }

    
    /**
     * <code>saveNote</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param orderId a <code>String</code> value
     * @exception Exception if an error occurs
     */  
    public static ActionErrors saveNote(HttpServletRequest request,
                                        ActionForm form)
	throws Exception {
	
        ActionErrors lUpdateErrors = new ActionErrors();
         
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
		  		 
        OrderOpNoteForm sForm = (OrderOpNoteForm) form;
	session.setAttribute("ORDER_OP_NOTE_FORM", sForm);			
		 
        if(sForm != null){
            if (sForm.getOrderPropertyDetail().getValue().length() == 0) {
                lUpdateErrors.add("notes", 
                                new ActionError("variable.empty.error",
						  "Notes"));
            }		 
	}
		 
	if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }
        String noteType = sForm.getOrderNoteType();
        if ( null == noteType || "".equals(noteType)) {
            noteType = new String("order");
        }

	Order orderEjb = factory.getOrderAPI();	
	OrderPropertyData OPD = OrderPropertyData.createValue();				
        
        if ("order".equals(noteType)) {
            String orderId = sForm.getOrderId();			 		 				
            OPD.setOrderId(Integer.parseInt(orderId));
        }
        else if ("item".equals(noteType)) {
            String orderItemId = sForm.getOrderItemId();	
            OPD.setOrderItemId(Integer.parseInt(orderItemId));            
        }
	OPD.setShortDesc(sForm.getOrderPropertyDetail().getValue());
	OPD.setValue(sForm.getOrderPropertyDetail().getValue());
	OPD.setOrderPropertyStatusCd("ACTIVE");
	OPD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
	OPD.setAddBy(sForm.getOrderPropertyDetail().getAddBy());
	OPD.setModBy(sForm.getOrderPropertyDetail().getAddBy());
	//OPD.setAddDate();
	//OPD.setModDate();
		
	orderEjb.addNote(OPD);
		
	return lUpdateErrors;
				
    }
           
    
    /**
     * <code>getOrderStatusDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param orderId a <code>String</code> value
     * @exception Exception if an error occurs
     */  
    public static ActionErrors updateNote(
				    HttpServletRequest request,
				    ActionForm form)
	throws Exception {
	
	     ActionErrors lUpdateErrors = new ActionErrors();
         
         HttpSession session = request.getSession();
	     APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	     if (null == factory) {
	         throw new Exception("Without APIAccess.");
	     }    
		  
		 
		 OrderOpDetailForm sForm = (OrderOpDetailForm) form;
	 	 session.setAttribute("ORDER_OP_DETAIL_FORM", sForm);	
		
		 
		 if(sForm != null){
		    if (sForm.getOrderPropertyDetail().getValue().length() == 0) {
                lUpdateErrors.add("notes", 
				  new ActionError("variable.empty.error",
						  "Notes"));
            }		 
		 }
		 
		 if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
         }
		 int orderStatusId = sForm.getOrderStatusDetail().getOrderDetail().getOrderId();			 
		 		
		Order orderEjb = factory.getOrderAPI();	
	    OrderData orderStatusDetail = orderEjb.getOrderStatus(orderStatusId);
		
		
		OrderPropertyData OPD = OrderPropertyData.createValue();				
		
                OPD.setOrderId(orderStatusId);
		OPD.setShortDesc(sForm.getOrderPropertyDetail().getValue());
		OPD.setValue(sForm.getOrderPropertyDetail().getValue());
		OPD.setOrderPropertyStatusCd("ACTIVE");
		OPD.setOrderPropertyTypeCd("ORDER_NOTE");
		OPD.setAddBy(sForm.getOrderPropertyDetail().getAddBy());
		OPD.setModBy(sForm.getOrderPropertyDetail().getAddBy());
		//OPD.setAddDate();
		//OPD.setModDate();
		
		orderEjb.addNote(OPD);
		return lUpdateErrors;
    }
    
    /**
     *populates the result set in the supplied form based off the criteria in the supplied form
     */
    public static ActionErrors searchCustomerInvoices(HttpServletRequest request,ActionForm form)
    throws Exception {
        
        ActionErrors lUpdateErrors = new ActionErrors();
        
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        InvoiceCustOpSearchForm sForm = (InvoiceCustOpSearchForm) form;
        
        SimpleDateFormat dteFrmt = new SimpleDateFormat("MM/dd/yyyy");
        //verify input data and set the criteria
        InvoiceCustCritView crit = InvoiceCustCritView.createValue();
        boolean critSet = false;
        if(Utility.isSet(sForm.getAccountId())){
            try{
                crit.setAccountId(Integer.parseInt(sForm.getAccountId()));
                critSet = true;
            }catch(NumberFormatException e){
                lUpdateErrors.add("accountId",new ActionError("error.invalidNumber","Account Id"));
            }
        }

        if(sForm.getWebOrderNum()>0){
          crit.setWebOrderNum(sForm.getWebOrderNum());
          critSet = true;
        }

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        crit.setStoreId(appUser.getUserStore().getStoreId());
        
        if(Utility.isSet(sForm.getInvoiceDateRangeBegin())){
            try{
                crit.setInvoiceDateRangeBegin(dteFrmt.parse(sForm.getInvoiceDateRangeBegin()));
                critSet = true;
            }catch(NumberFormatException e){
                lUpdateErrors.add("invoiceDateRangeBegin",new ActionError("error.badDateFormat","Invoice Date Range Begin"));
            }
        }
        if(Utility.isSet(sForm.getInvoiceDateRangeEnd())){
            try{
                crit.setInvoiceDateRangeEnd(dteFrmt.parse(sForm.getInvoiceDateRangeEnd()));
                critSet = true;
            }catch(NumberFormatException e){
                lUpdateErrors.add("invoiceDateRangeEnd",new ActionError("error.badDateFormat","Invoice Date Range End"));
            }
        }
        if(Utility.isSet(sForm.getInvoiceNumRangeBegin())){
            crit.setInvoiceNumRangeBegin(sForm.getInvoiceNumRangeBegin());
            critSet = true;
        }
        if(Utility.isSet(sForm.getInvoiceNumRangeEnd())){
            crit.setInvoiceNumRangeEnd(sForm.getInvoiceNumRangeEnd());
            critSet = true;
        }
        //if nothing was specified treat as error
        if(critSet == false){
            lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidSearchCritera"));
        }
        //return any errors
        if(lUpdateErrors.size() > 0){
            return lUpdateErrors;
        }
        
        //continue with search
        sForm.setResultList(factory.getOrderAPI().getInvoiceCustViewCollection(crit,false));
        
        return lUpdateErrors;
    }
    
    
     /**
     *Generates a pdf of many invoices to the response output stream
     */
    public static ActionErrors printAllCustomerInvoice(HttpServletRequest request,
    HttpServletResponse response,ActionForm form)throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        
        PdfInvoice pdf = new PdfInvoice();
        InvoiceCustOpSearchForm sForm = (InvoiceCustOpSearchForm) form;
        InvoiceCustViewVector icv = sForm.getResultList();
        java.util.Iterator it = icv.iterator();
        StoreData lStore = null;
        HashSet stores = new HashSet();
        while (it.hasNext()){
            InvoiceCustView invVw = (InvoiceCustView) it.next();
            int storeId = invVw.getInvoiceCustData().getStoreId();
            if(storeId == 0){
                storeId = 1;
            }
            Integer storeKey = new Integer(storeId);
            stores.add(storeKey);
        }
        if(stores.size() > 1){
            lUpdateErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("invoice.error.multiple.stores.print"));
        }else if (stores.size() == 0){
            lUpdateErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.nothing.to.print"));
        }else{
            Integer storeId = (Integer) stores.iterator().next();
            lStore = factory.getStoreAPI().getStore(storeId.intValue());
        }
        
        //get in the necessary data
        InvoiceCustViewVector populatedInvoices = new InvoiceCustViewVector();
        it = icv.iterator();
        while (it.hasNext()){
            InvoiceCustView invVw = (InvoiceCustView) it.next();
            //only fetch it if it is not populated, some version of acrobat will make a 2nd request once
            //they realize the content is pdf, so we will run through this 2x times
            if(invVw.getInvoiceCustDetailDataVector() == null || invVw.getOrderData() == null){
                invVw = fetchCustomerInvoice(invVw.getInvoiceCustData().getInvoiceCustId(), lUpdateErrors, request);
            }
            populatedInvoices.add(invVw);
        }
        
        if(lUpdateErrors.size() > 0){
            return lUpdateErrors;
        }
        
        sForm.setResultList(populatedInvoices);
        ByteArrayOutputStream pdfout = new ByteArrayOutputStream();
        pdf.constructPdfInvoice(populatedInvoices,lStore,pdfout,ClwCustomizer.getLogoPathForPrinterDisplay(lStore));
        response.setContentType("application/pdf");
        
		String browser = (String)  request.getHeader("User-Agent");
        boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
        if (!isMSIE6){
        	response.setHeader("extension", "pdf");
      	  	response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");
        }
        response.setContentLength(pdfout.size());   
        pdfout.writeTo(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();
        
        return null;
    }
    
    /**
     *Generates a pdf of one invoice to the response output stream
     */
    public static ActionErrors printCustomerInvoice(HttpServletRequest request,
    HttpServletResponse response,ActionForm form)throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        
        PdfInvoice pdf = new PdfInvoice();
        InvoiceCustOpDetailForm sForm = (InvoiceCustOpDetailForm) form;
        InvoiceCustView invVw = sForm.getInvoiceCustView();
        int storeId = invVw.getOrderData().getStoreId();
        if(storeId == 0){
            storeId = 1;
        }
        StoreData store = factory.getStoreAPI().getStore(storeId);
        
        ByteArrayOutputStream pdfout = new ByteArrayOutputStream();
        pdf.constructPdfInvoice(invVw.getOrderData(),invVw.getInvoiceCustData(), 
            invVw.getInvoiceCustDetailDataVector(),store,pdfout,ClwCustomizer.getLogoPathForPrinterDisplay(store));
        response.setHeader("extension", "pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");
        response.setContentType("application/pdf");
        response.setContentLength(pdfout.size());   
        pdfout.writeTo(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();
        
        
        
        return null;
    }
    
    
    /**
     *populates the form based off the id in the request
     */
    public static ActionErrors fetchCustomerInvoice(HttpServletRequest request,ActionForm form)
    throws Exception {
        
        ActionErrors lUpdateErrors = new ActionErrors();
        InvoiceCustOpDetailForm sForm = (InvoiceCustOpDetailForm) form;
        
        //parse out the id
        String idS = request.getParameter("id");
        int id;
        try{
            id = Integer.parseInt(idS);
        }catch(Exception e){
            lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.badRequest","id"));
            return null;
        }
        
        InvoiceCustView inv = fetchCustomerInvoice(id, lUpdateErrors, request);
        if (lUpdateErrors.size() > 0){
            return lUpdateErrors;
        }
        sForm.setInvoiceCustView(inv);
        return lUpdateErrors;
    }
    
    private static InvoiceCustView fetchCustomerInvoice(int id,ActionErrors lUpdateErrors,
    HttpServletRequest request) throws Exception{
        
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        
        
        InvoiceCustCritView crit = InvoiceCustCritView.createValue();
        crit.setInvoiceCustId(id);
        InvoiceCustViewVector icvVec = factory.getOrderAPI().getInvoiceCustViewCollection(crit, true);
        InvoiceCustView inv;
        if(icvVec.size() == 1){
            inv = (InvoiceCustView) icvVec.get(0);
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            int storeId = 0;
            if(appUser != null) {
                storeId = appUser.getUserStore().getStoreId();
            }
            if (storeId  != inv.getInvoiceCustData().getStoreId()){
            	lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.unauthorized"));
            }
        }else{
            throw new IllegalStateException("id does not refer to 1 cust invoice, referes to: " + icvVec.size());
        }
        return inv;
    }
    
    //private inner class to help with distributor invoice matching
    private static class InvoiceDistStruct{
        private InvoiceDistStruct(InvoiceDistData pInvoiceDistData){
            invoiceDistData = pInvoiceDistData;
        }
        
        private void addInvoiceDetailData(InvoiceDistDetailData idd){
            if (invoiceDistDetailDataVector == null){
                invoiceDistDetailDataVector = new InvoiceDistDetailDataVector();
            }
            invoiceDistDetailDataVector.add(idd);
        }
        
         private void addOrderItemDescData(OrderItemDescData odd){
            if (orderItemDescDataVector == null){
                orderItemDescDataVector = new OrderItemDescDataVector();
            }
            orderItemDescDataVector.add(odd);
        }
        
        /** Holds value of property invoiceDistData. */
        private InvoiceDistData invoiceDistData;
        
        /** Holds value of property invoiceDistDetailDataVector. */
        private InvoiceDistDetailDataVector invoiceDistDetailDataVector;
        
        /** Holds value of property voucher. */
        private String voucher;
        
        /** Holds value of property orderDescItemDataVector. */
        private OrderItemDescDataVector orderItemDescDataVector;
        
        /** Getter for property invoiceDistData.
         * @return Value of property invoiceDistData.
         *
         */
        public InvoiceDistData getInvoiceDistData() {
            return this.invoiceDistData;
        }
        
        /** Setter for property invoiceDistData.
         * @param invoiceDistData New value of property invoiceDistData.
         *
         */
        public void setInvoiceDistData(InvoiceDistData invoiceDistData) {
            this.invoiceDistData = invoiceDistData;
        }
        
        /** Getter for property invoiceDistDetailDataVector.
         * @return Value of property invoiceDistDetailDataVector.
         *
         */
        public InvoiceDistDetailDataVector getInvoiceDistDetailDataVector() {
            return this.invoiceDistDetailDataVector;
        }
        
        /** Setter for property invoiceDistDetailDataVector.
         * @param invoiceDistDetailDataVector New value of property invoiceDistDetailDataVector.
         *
         */
        public void setInvoiceDistDetailDataVector(InvoiceDistDetailDataVector invoiceDistDetailDataVector) {
            this.invoiceDistDetailDataVector = invoiceDistDetailDataVector;
        }
        
        /** Getter for property voucher.
         * @return Value of property voucher.
         *
         */
        public String getVoucher() {
            return this.voucher;
        }
        
        /** Setter for property voucher.
         * @param voucher New value of property voucher.
         *
         */
        public void setVoucher(String voucher) {
            this.voucher = voucher;
        }
        
        /** Getter for property orderItemDescDataVector.
         * @return Value of property orderItemDescDataVector.
         *
         */
        public OrderItemDescDataVector getOrderItemDescDataVector() {
            return this.orderItemDescDataVector;
        }
        
    }

 

}

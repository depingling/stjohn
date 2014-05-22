
package com.cleanwise.view.logic;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.PurchaseOrder;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AssetData;
import com.cleanwise.service.api.value.AssetDataVector;
import com.cleanwise.service.api.value.AssetSearchCriteria;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.ERPPurchaseOrderLineDescView;
import com.cleanwise.service.api.value.ERPPurchaseOrderLineDescViewVector;
import com.cleanwise.service.api.value.FreightHandlerView;
import com.cleanwise.service.api.value.GenericReportColumnView;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ManifestItemView;
import com.cleanwise.service.api.value.ManifestItemViewVector;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.PurchaseOrderDataVector;
import com.cleanwise.service.api.value.PurchaseOrderStatusCriteriaData;
import com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView;
import com.cleanwise.service.api.value.PurchaseOrderStatusDescDataViewVector;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.ReturnRequestData;
import com.cleanwise.service.api.value.ReturnRequestDescDataView;
import com.cleanwise.service.api.value.ReturnRequestDetailData;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.forms.InvoiceOpDetailForm;
import com.cleanwise.view.forms.LocateStoreAccountForm;
import com.cleanwise.view.forms.LocateStoreDistForm;
import com.cleanwise.view.forms.LocateStoreSiteForm;
import com.cleanwise.view.forms.PurchaseOrderOpDetailForm;
import com.cleanwise.view.forms.PurchaseOrderOpSearchForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.ReportWritter;
import com.cleanwise.view.utils.pdf.PdfGenericReturn;
import com.cleanwise.view.utils.pdf.PdfManifestLabel;
import com.cleanwise.view.utils.pdf.PdfPurchaseOrder;


/**
 * <code>PoOpLogic</code> implements the logic needed to
 * manipulate po records.
 *
 * @author bstevens
 */
public class PurchaseOrderOpLogic {
    private static final List PURCHASE_ORDER_STATUS_DIST_ALLOW_LIST;
    static{
        PURCHASE_ORDER_STATUS_DIST_ALLOW_LIST = new ArrayList();
        PURCHASE_ORDER_STATUS_DIST_ALLOW_LIST.add(RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED);
        PURCHASE_ORDER_STATUS_DIST_ALLOW_LIST.add(RefCodeNames.PURCHASE_ORDER_STATUS_CD.DIST_ACKD_PURCH_ORDER);
        PURCHASE_ORDER_STATUS_DIST_ALLOW_LIST.add(RefCodeNames.PURCHASE_ORDER_STATUS_CD.PENDING_FULFILLMENT);
        PURCHASE_ORDER_STATUS_DIST_ALLOW_LIST.add(RefCodeNames.PURCHASE_ORDER_STATUS_CD.PENDING_FULFILLMENT_PROCESSING);
        PURCHASE_ORDER_STATUS_DIST_ALLOW_LIST.add(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR);
    }

    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @throws Exception if an error occurs
     */
    public static void init(HttpServletRequest request,ActionForm form)
    throws Exception {
        initConstantList(request);
        return;
    }


    /**
     *Creates a GenericReportColumnView from the suplied input.
     */
    private static GenericReportColumnView createGenericReportColumnView(
    String pColumnClass, String pColumnName, int pColumnPrecision, int pColumnScale,String pColumnType){
        GenericReportColumnView vw = GenericReportColumnView.createValue();
        vw.setColumnClass(pColumnClass);
        vw.setColumnName(pColumnName);
        vw.setColumnPrecision(pColumnPrecision);
        vw.setColumnScale(pColumnScale);
        vw.setColumnType(pColumnType);
        return vw;
    }

    //Creates the header for the Purchase Order Tracker Report
    private static GenericReportColumnViewVector getPurchaseOrderTrackerReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(createGenericReportColumnView("java.lang.String","Dist Name",0,255,"VARCHAR2"));
        header.add(createGenericReportColumnView("java.lang.String","Acct Name",0,255,"VARCHAR2"));
        header.add(createGenericReportColumnView("java.lang.String","Ship City",0,255,"VARCHAR2"));
        header.add(createGenericReportColumnView("java.lang.String","Ship State",0,255,"VARCHAR2"));
        header.add(createGenericReportColumnView("java.lang.String","Ship Postal Code",0,255,"VARCHAR2"));
        header.add(createGenericReportColumnView("java.lang.String","Outbound PO Num",0,255,"VARCHAR2"));
        header.add(createGenericReportColumnView("java.lang.String","ERP Order",0,255,"VARCHAR2"));
        header.add(createGenericReportColumnView("java.sql.Timestamp","PO Date",0,0, "DATE"));
        header.add(createGenericReportColumnView("java.lang.String","PO Status",0,255,"VARCHAR2"));
        header.add(createGenericReportColumnView("java.sql.Timestamp","Target Shipping Date",0,0, "DATE"));
        header.add(createGenericReportColumnView("java.lang.String","Open Line Status",0,255,"VARCHAR2"));
        header.add(createGenericReportColumnView("java.lang.String","Sku",0,255,"VARCHAR2"));
        header.add(createGenericReportColumnView("java.math.BigDecimal","Unit Cost$",0,32,"NUMBER"));
        header.add(createGenericReportColumnView("java.lang.Integer","Ordered Qty",0,32,"NUMBER"));
        header.add(createGenericReportColumnView("java.lang.Integer","Open Qty",0,32,"NUMBER"));
        header.add(createGenericReportColumnView("java.math.BigDecimal","Open Amount$",0,32,"NUMBER"));
        header.add(createGenericReportColumnView("java.lang.String","Item Description",0,255,"VARCHAR2"));
        return header;
    }

    /**
     * <code>printPurchaseOrderTracker</code> spools an excel file to the HttpServletResponse output
     * stream.  The stream is flushed and closed at the end of this method if no errors were encountered.
     * (ActionErrors.size() == 0)
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @param form an <code>ActionForm</code> value
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors printPurchaseOrderTracker(HttpServletRequest request,HttpServletResponse response,ActionForm form)
    throws Exception {
        GenericReportResultView report = GenericReportResultView.createValue();
        HttpSession session = request.getSession();
        ActionErrors lUpdateErrors = new ActionErrors();
        PurchaseOrderOpSearchForm sForm = (PurchaseOrderOpSearchForm) form;
        if(sForm.getTrackingResultList() == null || sForm.getTrackingResultList().size() == 0){
            lUpdateErrors.add("printPurchaseOrderTracker",new ActionError("error.emptyReport"));
        }
        if(lUpdateErrors.size() > 0){
            return lUpdateErrors;
        }

        report.setHeader(getPurchaseOrderTrackerReportHeader());
        report.setColumnCount(report.getHeader().size());
        ArrayList reportdata = new ArrayList();
        report.setTable(reportdata);

        for (int i=0,len=sForm.getTrackingResultList().size();i<len;i++){
            ArrayList row = new ArrayList();
            ERPPurchaseOrderLineDescView line = (ERPPurchaseOrderLineDescView) sForm.getTrackingResultList().get(i);
            row.add(line.getDistributorName());
            row.add(line.getAccountName());
            row.add(line.getShipCity());
            row.add(line.getShipState());
            row.add(line.getShipPostalCode());
            row.add(line.getErpPoNum());
            row.add(line.getErpOrderNum());
            row.add(line.getErpPoDate());
            if(line.getPurchaseOrderData() != null){
                row.add(line.getPurchaseOrderData().getPurchaseOrderStatusCd());
            }else{
                row.add(null);
            }
            if(line.getOrderItemData() != null){
                row.add(line.getOrderItemData().getTargetShipDate());
            }else{
                row.add(null);
            }
            row.add(line.getOpenLineStatusCd());
            row.add(line.getErpSkuNum());
            row.add(line.getErpUnitCost());
            row.add(new Integer(line.getErpOrderedQty()));
            row.add(new Integer(line.getErpOpenQty()));
            row.add(line.getErpOpenAmount());
            row.add(line.getErpItemDescription());
            reportdata.add(row);
        }

        String fileName = "purchaseOrderTracker.xls";
        response.setContentType("application/x-excel");
        String browser = (String)  request.getHeader("User-Agent");
        boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
        if (!isMSIE6){
        	response.setHeader("extension", "xls");
        }
        if(isMSIE6){
    		response.setHeader("Pragma", "public");
    		response.setHeader("Expires", "0");
    		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
    		response.setHeader("Cache-Control", "public"); 
    	
    	}
    	response.setHeader("Content-disposition", "attachment; filename="+fileName);
        GenericReportResultViewVector reportResults = new GenericReportResultViewVector();
        reportResults.add(report);
        ReportWritter.writeExcelReportMulti(reportResults, response.getOutputStream());
        response.flushBuffer();
        return lUpdateErrors;
    }


    /**
     * <code>savePurchaseOrderTrackerData</code> saves any changes that was made in the purchase order
     * tracker screens.  Sends updates to the EJB Layer, where they are actually stored.  Only po lines
     * that are represented in St John can be saved, those that are only in the ERP system cannot
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors savePurchaseOrderTrackerData(HttpServletRequest request,ActionForm form)
    throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        try{
            HttpSession session = request.getSession();
            PurchaseOrderOpSearchForm sForm = (PurchaseOrderOpSearchForm) form;
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            if (null == factory) {
                throw new Exception("Without APIAccess.");
            }
            PurchaseOrder poEjb = factory.getPurchaseOrderAPI();
            List openLines = sForm.getTrackingResultList();
            String[] updates = sForm.getOpenLineStatusUpdates();
            ERPPurchaseOrderLineDescViewVector toUpdate = new ERPPurchaseOrderLineDescViewVector();
            for(int i=0;i<openLines.size();i++){
                ERPPurchaseOrderLineDescView openLine = (ERPPurchaseOrderLineDescView) openLines.get(i);
                if(openLine.getOpenLineStatusCd() != null){
                    if(updates[i] != null){
                        if(! updates[i].equals(openLine.getOpenLineStatusCd()) ){
                            openLine.setOpenLineStatusCd(updates[i]);
                            toUpdate.add(openLine);
                        }
                    }
                }

            }

            CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);

            poEjb.saveERPPurchaseOrderLineCollection(toUpdate, appUser.getUserName());
        }catch(Exception e){
            lUpdateErrors.add("PurchaseOrderLogic", new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return lUpdateErrors;
    }

    /**
     * <code>searchPurchaseOrderTracker</code> searches for open lines to display in the
     * purchase order tracker.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors searchPurchaseOrderTracker(HttpServletRequest request,ActionForm form)
    throws Exception {
        HttpSession session = request.getSession();
        ActionErrors lUpdateErrors = new ActionErrors();
        PurchaseOrderOpSearchForm sForm = (PurchaseOrderOpSearchForm) form;
        initConstantList(request);
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        PurchaseOrderStatusCriteriaData searchCriteria = PurchaseOrderStatusCriteriaData.createValue();

        populateSearchCriteria(sForm, lUpdateErrors, searchCriteria, null, request, null);

        if(lUpdateErrors.size() > 0){
            return lUpdateErrors;
        }

        return lUpdateErrors;
    }


    /**
     * <code>sortPurchaseOrderTracker</code> sorts Purchase Order Tracker acording to the supplied
     *  field (sortField) which should be part of the request.  If this parameter is not part of the
     *  request the order  remains unchanged
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @throws Exception if an error occurs
     */
    public static void sortPurchaseOrderTracker(HttpServletRequest request,ActionForm form)
    throws Exception {
        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        PurchaseOrderOpSearchForm sForm = (PurchaseOrderOpSearchForm)form;
        if (sForm == null) {
            return;
        }
        ERPPurchaseOrderLineDescViewVector pos = (ERPPurchaseOrderLineDescViewVector)sForm.getTrackingResultList();
        if (pos == null) {
            return;
        }
        String sortField = request.getParameter("sortField");
        if(Utility.isSet(sortField)){
            pos.sort(sortField);
        }
    }





    /**
     * <code>printDetail</code> spools a printer friendly po to the request output stream
     * the stream is flushed and closed at the end of this method if no errors were encountered.
     * (ActionErrors.size() == 0)
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @param form an <code>ActionForm</code> value
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors printDetail(
    HttpServletResponse response,
    HttpServletRequest request,
    ActionForm form) throws Exception{
        HttpSession session = request.getSession();

        PurchaseOrderOpDetailForm sPoForm = null;
        InvoiceOpDetailForm sInvForm = null;
        if (form instanceof PurchaseOrderOpDetailForm){
            sPoForm = (PurchaseOrderOpDetailForm)form;
        }else if (form instanceof InvoiceOpDetailForm){
            sInvForm = (InvoiceOpDetailForm)form;
        }
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        Store storeEjb   = factory.getStoreAPI();
        PropertyService propEjb   = factory.getPropertyServiceAPI();
        Asset assetEjb=factory.getAssetAPI();
        ItemInformation itemInfoEjb=factory.getItemInformationAPI();
        PdfPurchaseOrder pdf = new PdfPurchaseOrder();


        //sets the content type so the browser knows this is a pdf
        response.setContentType("application/pdf");
        String browser = (String)  request.getHeader("User-Agent");
        boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
        if (!isMSIE6){
      	  	response.setHeader("extension", "pdf");
      	  	response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");
        }
        
        //gets the references to the objects our po needs
        OrderData ord = null;
        OrderMetaDataVector ormdl = null;
        PurchaseOrderData po = null;
        OrderAddressData shipAddr = null;
        OrderAddressData billAddr = null;
        FreightHandlerView shipVia = null;
        OrderItemDescDataVector itmsDesc = null;
        DistributorData dist = null;
        String accountName = null;
        HashMap assetInfo=null;
        if (sPoForm != null){
            ord = sPoForm.getPurchaseOrderStatusDescDataView().getOrderData();
            po = sPoForm.getPurchaseOrderStatusDescDataView().getPurchaseOrderData();
            shipAddr = sPoForm.getPurchaseOrderStatusDescDataView().getShipToAddress();
            billAddr = sPoForm.getPurchaseOrderStatusDescDataView().getBillToAddress();
            shipVia = sPoForm.getPurchaseOrderStatusDescDataView().getShipVia();
            itmsDesc = sPoForm.getPurchaseOrderItemDescList();
            dist = sPoForm.getDistributorData();
            ormdl = sPoForm.getOrderMetaList();
            accountName = sPoForm.getPurchaseOrderStatusDescDataView().getPoAccountName();
        }else if (sInvForm != null){
            ord = sInvForm.getPurchaseOrderStatusDesc().getOrderData();
            po = sInvForm.getPurchaseOrderStatusDesc().getPurchaseOrderData();
            shipAddr = sInvForm.getPurchaseOrderStatusDesc().getShipToAddress();
            billAddr = sInvForm.getPurchaseOrderStatusDesc().getBillToAddress();
            shipVia = sInvForm.getPurchaseOrderStatusDesc().getShipVia();
            itmsDesc = sInvForm.getOrderItemDescList();
            dist = sInvForm.getDistributorData();
            ormdl = null;
            accountName = sInvForm.getPurchaseOrderStatusDesc().getPoAccountName();
        }
        int storeId = ord.getStoreId();
        if(storeId==0) storeId = 1;
        StoreData storeD = storeEjb.getStore(storeId);
        String siteShippingMessage = null;
        try{
            siteShippingMessage = propEjb.getBusEntityProperty(ord.getSiteId(), RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
        }catch(Exception e){}

        // Call the clw customizer logic to get the path
        String imgPath = ClwCustomizer.getLogoPathForPrinterDisplay(storeD);

        //we need to convert the orderItemDescData vector to
        //a plain orderItem vector
        //so that the pdf interface can understand it.
        OrderItemDataVector itms = new OrderItemDataVector();
        for(int i=0,len=itmsDesc.size();i<len;i++){
            itms.add(((OrderItemDescData)itmsDesc.get(i)).getOrderItem());
        }
        IdVector itemIds=new IdVector();
        IdVector assetIds=new IdVector();
        for(int i=0,len=itms.size();i<len;i++)
        {
         itemIds.add(new Integer(((OrderItemData)itms.get(i)).getItemId()));
         assetIds.add(new Integer(((OrderItemData)itms.get(i)).getAssetId()));
        }

        ItemDataVector itemDV=itemInfoEjb.getItemCollection(itemIds);
        boolean isSimpleServiceOrder = PipelineUtil.isSimpleServiceOrder(itemDV);
        if(PipelineUtil.isSimpleServiceOrder(itemDV))
        {
          assetInfo=getAssetInfo(assetEjb,assetIds);
        }
        ByteArrayOutputStream pdfout = new ByteArrayOutputStream();
        pdf.constructPdfPO(dist,ord, ormdl, po,itms,assetInfo,shipAddr,billAddr,
        accountName,shipVia,
        siteShippingMessage,storeD,pdfout,imgPath,isSimpleServiceOrder);

        response.setContentLength(pdfout.size());
        pdfout.writeTo(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();

        return new ActionErrors();
    }


    /**
     * <code>printReturnDetail</code> spools a printer friendly return form to the request output stream
     * the stream is flushed and closed at the end of this method if no errors were encountered.
     * (ActionErrors.size() == 0)
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @param form an <code>ActionForm</code> value
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors printReturnDetail(
    HttpServletResponse response,
    HttpServletRequest request,
    ActionForm form) throws Exception{
        HttpSession session = request.getSession();
        PurchaseOrderOpDetailForm sPoForm = (PurchaseOrderOpDetailForm) form;
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        Store storeEjb   = factory.getStoreAPI();
        PropertyService propEjb   = factory.getPropertyServiceAPI();

        int storeId = sPoForm.getPurchaseOrderStatusDescDataView().getOrderData().getStoreId();
        if(storeId == 0){
            storeId = 1;
        }
        StoreData storeD = storeEjb.getStore(storeId);

        String imgPath = ClwCustomizer.getCustomizeImgElement(request,"pages.store.logo1.image");

        //sets the content type so the browser knows this is a pdf
        response.setContentType("application/pdf");
		response.setHeader("extension", "pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");

        //gets the references to the objects our po needs


        ByteArrayOutputStream pdfout = new ByteArrayOutputStream();
        PdfGenericReturn pdf = new PdfGenericReturn();
        pdf.constructPdf(sPoForm.getDistributorData(),
        sPoForm.getPurchaseOrderStatusDescDataView().getPurchaseOrderData(),
        sPoForm.getReturnRequestDescData(),storeD,pdfout,imgPath);

        response.setContentLength(pdfout.size());
        pdfout.writeTo(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();

        return new ActionErrors();
    }

    /**
     *Populates a PurchaseOrderStatusCriteriaData object based off the supplied PurchaseOrderOpSearchForm
     *any validation errors are added to the ActionErrors.
     *@returns true if something was populated, false if nosearch criteria was set
     */
    static boolean populateSearchCriteria(
    PurchaseOrderOpSearchForm sForm,
    ActionMessages updateErrors,
    PurchaseOrderStatusCriteriaData searchCriteria,
    IdVector authorizedDistributorIds, HttpServletRequest request,IdVector authorizedAccountIds){
        boolean criteriaSet = false;
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        String userTypeCd = appUser.getUser().getUserTypeCd();
        if(!RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userTypeCd) && 
           !RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userTypeCd)){
            searchCriteria.setStoreIdVector(appUser.getUserStoreAsIdVector());
        }

        if(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userTypeCd)){
            searchCriteria.setUserId(appUser.getUserId());
        }

        if( Utility.isSet(sForm.getTargetFacilityRank()) ) {
            try{
                searchCriteria.setTargetFacilityRank(new Integer(sForm.getTargetFacilityRank().trim()));
                criteriaSet = true;
            }catch(NumberFormatException e){
                updateErrors.add("targetFacilityRank", new ActionError("error.invalidNumber","Target Facility Rank"));
            }
        }

        if( Utility.isSet(sForm.getItemStatus())) {
            criteriaSet = true;
            searchCriteria.setItemStatus(sForm.getItemStatus().trim());
        }

        if( Utility.isSet(sForm.getDistributorId())) {
            try{
                Integer distId = new Integer(sForm.getDistributorId().trim());
                criteriaSet = true;
                IdVector searchDistIds = new IdVector();
                if(authorizedDistributorIds != null){
                    if (authorizedDistributorIds.contains(distId)){
                        searchDistIds.add(distId);
                    }else{
                        updateErrors.add("distributorId", new ActionError("error.unauthorized"));
                        //set this in case some misbehaving code continues the search despite the action errors
                        searchCriteria.setDistributorIds(authorizedDistributorIds);
                    }
                }else{
                    searchDistIds.add(distId);
                }
                searchCriteria.setDistributorIds(searchDistIds);
            }catch(RuntimeException e){
                //set this in case some misbehaving code continues the search despite the action errors
                searchCriteria.setDistributorIds(authorizedDistributorIds);
                updateErrors.add("distributorId", new ActionError("error.invalidNumber","distributorId"));
            }

        }else{
            searchCriteria.setDistributorIds(authorizedDistributorIds);
        }

	if ( null != authorizedAccountIds && authorizedAccountIds.size() > 0 ) {
	    searchCriteria.setAccountIdVector(authorizedAccountIds);
	}

        if( Utility.isSet(sForm.getErpOrderNum())) {
            criteriaSet = true;
            searchCriteria.setErpOrderNum(sForm.getErpOrderNum().trim());
        }

	    if( Utility.isSet(sForm.getErpPORefNum())) {
            criteriaSet = true;
            searchCriteria.setErpPORefNum(sForm.getErpPORefNum().trim());
        }

        if( Utility.isSet(sForm.getOutboundPoNum())) {
            criteriaSet = true;
            searchCriteria.setOutboundPoNum(sForm.getOutboundPoNum().trim());
        }

        if( Utility.isSet(sForm.getErpPONum())) {
            criteriaSet = true;
            searchCriteria.setErpPONum(sForm.getErpPONum().trim());
        }

        if( Utility.isSet(sForm.getWebOrderConfirmationNum())) {
            criteriaSet = true;
            searchCriteria.setWebOrderConfirmationNum(sForm.getWebOrderConfirmationNum().trim());
        }

        if( Utility.isSet(sForm.getDistributorReturnRequestNum())) {
            criteriaSet = true;
            searchCriteria.setDistributorReturnRequestNum(sForm.getDistributorReturnRequestNum().trim());
        }

        if( Utility.isSet(sForm.getReturnRequestRefNum())) {
            criteriaSet = true;
            searchCriteria.setReturnRequestRefNum(sForm.getReturnRequestRefNum().trim());
        }

        if( Utility.isSet(sForm.getPoDateRangeBegin())) {
            criteriaSet = true;
            Date begDate =  new Date();
            try {
            	begDate = ClwI18nUtil.parseDateInp(request, sForm.getPoDateRangeBegin().trim()); 
            } catch (Exception e) {
                begDate = null;
                updateErrors.add("posearch", new ActionError("variable.date.format.error","Po Begin Date Range"));
            }
            searchCriteria.setPoDateRangeBegin(begDate);
        }
        if( Utility.isSet(sForm.getPoDateRangeEnd())) {
            criteriaSet = true;
            Date endDate =  new Date();
            try {
            	endDate  = ClwI18nUtil.parseDateInp(request, sForm.getPoDateRangeEnd().trim());
            }
            catch (Exception e) {
                updateErrors.add("posearch", new ActionError("variable.date.format.error","Po End Date Range"));
                endDate = null;
            }
            searchCriteria.setPoDateRangeEnd(endDate);
        }
        if( Utility.isSet(sForm.getInvoiceDistDateRangeBegin())) {
            criteriaSet = true;
            Date begDate =  new Date();
            try {
            	begDate  = ClwI18nUtil.parseDateInp(request, sForm.getInvoiceDistDateRangeBegin().trim());
            }
            catch (Exception e) {
                begDate = null;
                updateErrors.add("posearch", new ActionError("variable.date.format.error","Dist Invoice Begin Date Range"));
            }
            searchCriteria.setInvoiceDistDateRangeBegin(begDate);
        }
        if( Utility.isSet(sForm.getInvoiceDistDateRangeEnd())) {
            criteriaSet = true;
            Date endDate =  new Date();
            try {
            	endDate  = ClwI18nUtil.parseDateInp(request, sForm.getInvoiceDistDateRangeEnd().trim());
            }
            catch (Exception e) {
                updateErrors.add("posearch", new ActionError("variable.date.format.error","Dist Invoice End Date Range"));
                endDate = null;
            }
            searchCriteria.setInvoiceDistDateRangeEnd(endDate);
        }

        if( Utility.isSet(sForm.getInvoiceDistAddDateRangeBegin())) {
            criteriaSet = true;
            Date begDate =  new Date();
            try {
            	begDate  = ClwI18nUtil.parseDateInp(request, sForm.getInvoiceDistAddDateRangeBegin().trim());
            	begDate.setHours(0);
            	begDate.setMinutes(0);
            	begDate.setSeconds(0);
            }
            catch (Exception e) {
                begDate = null;
                updateErrors.add("posearch", new ActionError("variable.date.format.error","Dist Invoice Received Begin Date Range"));
            }
            searchCriteria.setInvoiceDistAddDateRangeBegin(begDate);
        }
        if( Utility.isSet(sForm.getInvoiceDistAddDateRangeEnd())) {
            criteriaSet = true;
            Date endDate =  new Date();
            try {
            	endDate = ClwI18nUtil.parseDateInp(request, sForm.getInvoiceDistAddDateRangeEnd().trim());
            	// STJ-3129 'End Date' value should be until the end of the day
            	endDate.setHours(23);
            	endDate.setMinutes(59);
            	endDate.setSeconds(59);
            }
            catch (Exception e) {
                updateErrors.add("posearch", new ActionError("variable.date.format.error","Dist Invoice Received End Date Range"));
                endDate = null;
            }
            searchCriteria.setInvoiceDistAddDateRangeEnd(endDate);
        }

        if(sForm.isExceptionDistInvoiceOnly()){
            criteriaSet = true;
            if(searchCriteria.getInvoiceDistExcludeStatusList() == null){
                searchCriteria.setInvoiceDistExcludeStatusList(new ArrayList());
            }
            searchCriteria.getInvoiceDistExcludeStatusList().add(RefCodeNames.INVOICE_STATUS_CD.CANCELLED);
            searchCriteria.getInvoiceDistExcludeStatusList().add(RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_PROCESSED);
            searchCriteria.getInvoiceDistExcludeStatusList().add(RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED);
            searchCriteria.getInvoiceDistExcludeStatusList().add(RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED_FAILED);
            searchCriteria.getInvoiceDistExcludeStatusList().add(RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED);
            searchCriteria.getInvoiceDistExcludeStatusList().add(RefCodeNames.INVOICE_STATUS_CD.DUPLICATE);
            searchCriteria.getInvoiceDistExcludeStatusList().add(RefCodeNames.INVOICE_STATUS_CD.REJECTED);
            searchCriteria.getInvoiceDistExcludeStatusList().add(RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED);
            searchCriteria.getInvoiceDistExcludeStatusList().add(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED);
            searchCriteria.getInvoiceDistExcludeStatusList().add(RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE);
            searchCriteria.getInvoiceDistExcludeStatusList().add(RefCodeNames.INVOICE_STATUS_CD.PENDING);
            searchCriteria.getInvoiceDistExcludeStatusList().add(RefCodeNames.INVOICE_STATUS_CD.PROCESS_ERP);
        }
        if( Utility.isSet(sForm.getSiteId())) {
            criteriaSet = true;
            searchCriteria.setSiteId(sForm.getSiteId().trim());
        }

        if( Utility.isSet(sForm.getAccountId())) {
            criteriaSet = true;
            searchCriteria.setAccountId(sForm.getAccountId().trim());
        }

        String accountIdList = Utility.strNN(sForm.getAccountIdList()).trim();
        if( Utility.isSet(accountIdList)) {

          IdVector accountIdV = new IdVector();
          StringTokenizer tok = new StringTokenizer(accountIdList,",");

          while(tok.hasMoreTokens()){
            String aIdS =tok.nextToken().trim();
            try{
              int accountId = Integer.parseInt(aIdS);
              accountIdV.add(new Integer(accountId));
            } catch(Exception exc) {}
          }
          if(accountIdV.size()>0) {
            criteriaSet = true;
            searchCriteria.setAccountIdVector(accountIdV);
          }
        }
        if( Utility.isSet(sForm.getInvoiceDistNum())) {
            criteriaSet = true;
            searchCriteria.setInvoiceDistNum(sForm.getInvoiceDistNum().trim());
        }
        if( Utility.isSet(sForm.getPurchaseOrderStatus())) {
            criteriaSet = true;
            if(searchCriteria.getPurchaseOrderStatus() == null){
                searchCriteria.setPurchaseOrderStatus(new ArrayList());
            }
            searchCriteria.getPurchaseOrderStatus().add(sForm.getPurchaseOrderStatus().trim());
        }
        if( Utility.isSet(sForm.getSiteData())){
            criteriaSet = true;
            searchCriteria.setSiteData(sForm.getSiteData().trim());
        }
        if( Utility.isSet(sForm.getItemTargetShipDateBeginString())) {
            criteriaSet = true;
            Date dte =  new Date();
            try {
            	dte = ClwI18nUtil.parseDateInp(request, sForm.getItemTargetShipDateBeginString().trim());
            }
            catch (Exception e) {
                updateErrors.add("posearch", new ActionError("variable.date.format.error","Item Target Ship Date"));
                dte = null;
            }
            searchCriteria.setItemTargetShipDateBegin(dte);
        }
        if( Utility.isSet(sForm.getItemTargetShipDateEndString())) {
            criteriaSet = true;
            Date dte =  new Date();
            try {
            	dte = ClwI18nUtil.parseDateInp(request, sForm.getItemTargetShipDateEndString().trim());
            }
            catch (Exception e) {
                updateErrors.add("posearch", new ActionError("variable.date.format.error","Item Target Ship Date"));
                dte = null;
            }
            searchCriteria.setItemTargetShipDateEnd(dte);
        }
        if( Utility.isSet(sForm.getOrderRequestPoNum())){
            criteriaSet = true;
            searchCriteria.setOrderRequestPoNum(sForm.getOrderRequestPoNum().trim());
        }

        return criteriaSet;
    }

    //adds the various status that indicate po is in fully baked non-exception state
    private static void addUnrestrictedStatusToSearchCriteria(PurchaseOrderOpSearchForm pForm, PurchaseOrderStatusCriteriaData searchCriteria){
        //reset the search status list
        if(searchCriteria.getPurchaseOrderStatus() == null){
            searchCriteria.setPurchaseOrderStatus(new ArrayList());
        }else{
            searchCriteria.getPurchaseOrderStatus().clear();
        }
        //if the user requested a given status make sure it it allowed, and just add it to the request
        //otherwise add all the allowed po status list.
        if(Utility.isSet(pForm.getPurchaseOrderStatus())){
            if(PURCHASE_ORDER_STATUS_DIST_ALLOW_LIST.contains(pForm.getPurchaseOrderStatus())){
                searchCriteria.getPurchaseOrderStatus().add(pForm.getPurchaseOrderStatus());
            }else{
                throw new IllegalArgumentException("Purchase Order Status requested is not allowed");
            }
        }else{
            searchCriteria.getPurchaseOrderStatus().addAll(PURCHASE_ORDER_STATUS_DIST_ALLOW_LIST);
        }
    }

    /**
     *Retrieves all of the purchase orders that are pending a manifest
     *and restricted by the distributor that this user is related to.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors fetchAllPOsPendingManifestDistributorRestricted(HttpServletRequest request,ActionForm form)
    throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        IdVector authorizedDistIds = getAuthorizedDistIds(session);
        if(authorizedDistIds == null || authorizedDistIds.size() == 0){
            ae.add(ActionErrors.GLOBAL_ERROR,
            new ActionError("error.systemError","No Distributors Configured For User"));
            return ae;
        }

        PurchaseOrderOpSearchForm sForm = (PurchaseOrderOpSearchForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
        PurchaseOrderStatusCriteriaData searchCriteria = PurchaseOrderStatusCriteriaData.createValue();
        //we are going to ignore the form and just grab everything, but this is not an inherent limitation
        searchCriteria.setPendingManifestOnly(true);
        searchCriteria.setRoutedOnly(true);
        searchCriteria.setDistributorIds(authorizedDistIds);

        //add the status we want to let a restricted user see
        addUnrestrictedStatusToSearchCriteria(sForm,searchCriteria);
        PurchaseOrderStatusDescDataViewVector poStatusDescData = purchaseOrderEjb.getPurchaseOrderStatusDescCollection(searchCriteria);
        sForm.setResultList(poStatusDescData);

        return ae;
    }

    /**
     *Searches the purchase orders but is restricted by the distributors that this user is related to.
     * <code>searchPurchaseOrdersDistributorRestricted</code> queries the purchaseOrder EJB
     * for matching purchase orders to the supplied criteria supplied in the form object.
     * the results are restricted by the distributor that this user is related to.
     * The resultList property of the form will be populated with all matching pos.
     * Validation is preformed on the input and action errors will be returned if there is any
     * problem with it (invalid dates etc.)
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors searchPurchaseOrdersDistributorRestricted(HttpServletRequest request,ActionForm form)
    throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        IdVector authorizedDistIds = getAuthorizedDistIds(session);
        if(authorizedDistIds == null || authorizedDistIds.size() == 0){
            ae.add(ActionErrors.GLOBAL_ERROR,
            new ActionError("error.systemError","No Distributors Configured For User"));
            return ae;
        }
        IdVector authorizedAccountIds = getAuthorizedAccountIds(session);
        return search(request, form,authorizedDistIds,true,
		      authorizedAccountIds);
    }

    /**
     *Retrieves the authorized distributor ids from the session
     */
    private static IdVector getAuthorizedDistIds(HttpSession session){
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        BusEntityDataVector distributors = appUser.getDistributors();

        IdVector authorizedDistIds = new IdVector();
        java.util.Iterator it = distributors.iterator();
        while(it.hasNext()){
            BusEntityData dist = (BusEntityData) it.next();
            authorizedDistIds.add(new Integer(dist.getBusEntityId()));
        }
        return authorizedDistIds;
    }
    private static IdVector getAuthorizedAccountIds(HttpSession session){
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        BusEntityDataVector accts = appUser.getUserAccountsCollection();

        IdVector authorizedIds = new IdVector();
        java.util.Iterator it = accts.iterator();
        while(it.hasNext()){
            BusEntityData acct = (BusEntityData) it.next();
            authorizedIds.add(new Integer(acct.getBusEntityId()));
        }

	    return authorizedIds;
    }

    /**
     * <code>search</code> queries the purchaseOrder EJB for matching purchase orders to the
     * supplied criteria supplied in the form object.  The resultList property of the form
     * will be populated with all matching pos.  Validation is preformed on the input and
     * action errors will be returned if there is any problem with it (invalid dates etc.)
     *
     * This is a convinience method and is the same thing as calling search,request,form,null)
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors search(HttpServletRequest request,ActionForm form)
    throws Exception {
        return search(request, form,null,false);
    }


    /**
     * <code>search</code> queries the purchaseOrder EJB for matching purchase orders to the
     * supplied criteria supplied in the form object.  The resultList property of the form
     * will be populated with all matching pos.  Validation is preformed on the input and
     * action errors will be returned if there is any problem with it (invalid dates etc.)
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param authorizedDistributorIds the distributor ids to limit this search to.
     *      May be null to indicate any distributor.
     * @param restrictPurchaseOrderStatus restrict the purchase order status to only fully backed non
     *      exception status.
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors search
	(HttpServletRequest request,ActionForm form,
	 IdVector authorizedDistributorIds,
	 boolean restrictPurchaseOrderStatus) throws Exception{
	return search( request, form,
		       authorizedDistributorIds,
		       restrictPurchaseOrderStatus, null);

    }

    public static ActionErrors search
	(HttpServletRequest request,ActionForm form,
	 IdVector authorizedDistributorIds,boolean restrictPurchaseOrderStatus,
	 IdVector authorizedAccountIds)
	throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        PurchaseOrderOpSearchForm sForm = (PurchaseOrderOpSearchForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        PurchaseOrder purchaseOrderEjb   = factory.getPurchaseOrderAPI();
        PurchaseOrderStatusCriteriaData searchCriteria = PurchaseOrderStatusCriteriaData.createValue();

        boolean criteriaSet;
        criteriaSet = populateSearchCriteria(sForm, lUpdateErrors, searchCriteria, authorizedDistributorIds, request, authorizedAccountIds );

        if(restrictPurchaseOrderStatus){
            //if we are going to restrict this data clear out whatever we just added, just in case
            //it is not in the list.  The UI should also not allow this.
            if (searchCriteria.getPurchaseOrderStatus() != null){
                searchCriteria.getPurchaseOrderStatus().clear();
            }
            addUnrestrictedStatusToSearchCriteria(sForm, searchCriteria);
        }

        //if something was specified search for it.
        if (!criteriaSet){
            lUpdateErrors.add("posearch", new ActionError("error.invalidSearchCritera"));
        }

        if (lUpdateErrors.size() > 0){
            return lUpdateErrors;
        }


        PurchaseOrderStatusDescDataViewVector poStatusDescData;
        poStatusDescData = purchaseOrderEjb.getPurchaseOrderStatusDescCollection(searchCriteria);

        sForm.setResultList(poStatusDescData);

        return lUpdateErrors;
    }


    /**
     *  <code>sortItems</code> sorts pos acording to the supplied field (sortField) which should
     *  be part of the request.  If this parameter is not part of the request the pos order
     *  remains unchanged
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@throws  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request,
    ActionForm form)
    throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        PurchaseOrderOpSearchForm sForm = (PurchaseOrderOpSearchForm)form;
        if (sForm == null) {
            return;
        }
        PurchaseOrderStatusDescDataViewVector pos = (PurchaseOrderStatusDescDataViewVector)sForm.getResultList();
        if (pos == null) {
            return;
        }
        String sortField = request.getParameter("sortField");
        if(Utility.isSet(sortField)){
            DisplayListSort.sort(pos, sortField);
        }
    }

    /**
     *<code>updateItemsShippingStatus</code> updates the shipping status of all the items for this form.
     *No updates are sent to the EJB, this is for display to the user.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@returns  a populated action errors object, size will > 0 if an error occured
     *@throws  Exception  if an error occurs
     */
    public static ActionErrors updateItemsShippingStatus(HttpServletRequest request,ActionForm form) throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        PurchaseOrderOpDetailForm sForm = (PurchaseOrderOpDetailForm)form;
        String shippingStatus = shippingStatus=sForm.getShippingStatus();
        OrderItemDescDataVector itms=sForm.getPurchaseOrderItemDescList();
        for(int i=0,len=itms.size();i<len;i++){
            OrderItemDescData itm=(OrderItemDescData)itms.get(i);
            itm.setShipStatus(shippingStatus);
        }
        return lUpdateErrors;
    }

    /**
     *<code>updateItemsQtyForAction</code> updates the quantity for the item action
     *to the quantities order for each line.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@returns  a populated action errors object, size will > 0 if an error occured
     *@throws  Exception  if an error occurs
     */
    public static ActionErrors updateItemsQtyForAction(HttpServletRequest request,ActionForm form) throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        PurchaseOrderOpDetailForm sForm = (PurchaseOrderOpDetailForm)form;
        OrderItemDescDataVector itms=sForm.getPurchaseOrderItemDescList();
        for(int i=0,len=itms.size();i<len;i++){
            OrderItemDescData itm=(OrderItemDescData)itms.get(i);
            itm.setNewOrderItemActionQtyS(Integer.toString(itm.getOrderItem().getTotalQuantityOrdered()));
        }
        return lUpdateErrors;
    }


    /**
     *<code>updateItemsOpenPoLineStatus</code> updates the OpenPoLineStatus of all the items for this form.
     *No updates are sent to the EJB, this is for display to the user.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@returns  a populated action errors object, size will > 0 if an error occured
     *@throws  Exception  if an error occurs
     */
    public static ActionErrors updateItemsOpenPoLineStatus(HttpServletRequest request,ActionForm form) throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        PurchaseOrderOpDetailForm sForm = (PurchaseOrderOpDetailForm)form;
        //if (Utility.isSet(sForm.getOpenLineStatusCd())){
            String openLineStatusCd=sForm.getOpenLineStatusCd();
            OrderItemDescDataVector itms=sForm.getPurchaseOrderItemDescList();
            for(int i=0,len=itms.size();i<len;i++){
                OrderItemDescData itm=(OrderItemDescData)itms.get(i);
                itm.setOpenLineStatusCd(openLineStatusCd);
            }
        //}else{
            //lUpdateErrors.add("updateItemsOpenPoLineStatus", new ActionError("variable.empty.error","Open Line Status Cd"));
        //}
        return lUpdateErrors;
    }

    /**
     *<code>updateItemsTargetShipDate</code> updates the target ship date of all the items for this form.
     *No updates are sent to the EJB, this is for display to the user.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@returns  a populated action errors object, size will > 0 if an error occured
     *@throws  Exception  if an error occurs
     */
    public static ActionErrors updateItemsTargetShipDate(HttpServletRequest request,ActionForm form) throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        PurchaseOrderOpDetailForm sForm = (PurchaseOrderOpDetailForm)form;
        String targetShipDateString = sForm.getTargetShipDate();
        if (Utility.isSet(targetShipDateString)){
            try{
                //make sure it is a valid date
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                simpleDateFormat.parse(targetShipDateString);
            }catch (ParseException e){
                lUpdateErrors.add("updateItemsTargetShipDate", new ActionError("variable.date.format.error","Target Ship Date"));
                return lUpdateErrors;
            }
        }
        OrderItemDescDataVector itms=sForm.getPurchaseOrderItemDescList();
        for(int i=0,len=itms.size();i<len;i++){
            OrderItemDescData itm=(OrderItemDescData)itms.get(i);
            itm.setTargetShipDateString(targetShipDateString);
        }
        //}else{
            //lUpdateErrors.add("updateItemsTargetShipDate", new ActionError("variable.empty.error","Target Ship Date"));
        //}
        return lUpdateErrors;
    }

    /**
     *<code>updateStatus</code> updates the status of a po to
     *@see RefCodeNames.PURCHASE_ORDER_STATUS_CD.DIST_ACKD_PURCH_ORDER and sends the update to the
     *PurchaseOrder EJB
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@returns  a populated action errors object, size will > 0 if an error occured
     *@throws  Exception  if an error occurs
     */
    public static ActionErrors updateStatus(HttpServletRequest request, ActionForm form) throws Exception {
        try{
            ActionErrors lUpdateErrors = new ActionErrors();

            HttpSession session = request.getSession();

            PurchaseOrderOpDetailForm sForm = (PurchaseOrderOpDetailForm)form;
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            if (null == factory) {
                throw new Exception("Without APIAccess.");
            }
            PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
            String newStatus = RefCodeNames.PURCHASE_ORDER_STATUS_CD.DIST_ACKD_PURCH_ORDER;

            PurchaseOrderData po = sForm.getPurchaseOrderStatusDescDataView().getPurchaseOrderData();
            po.setPurchaseOrderStatusCd(newStatus);
            purchaseOrderEjb.updatePurchaseOrder(po);
            sForm.setPurchaseOrderStatus(newStatus);
            return lUpdateErrors;
        }catch(Exception e){
            e.printStackTrace();
            throw (e);
        }
    }

    /**
     *<code>deleteOrderItemActions</code> deletes the selected order item actions.  This
     *will remove them from the distributor and the customer display and certain reports
     *and add them as a historical status that admins and customer service users can view.
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@returns  a populated action errors object, size will > 0 if an error occured
     *@throws  Exception  if an error occurs
     */
    public static ActionErrors deleteOrderItemActions(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        PurchaseOrderOpDetailForm sForm = (PurchaseOrderOpDetailForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        //filter out the null items
        IdVector toDelete = new IdVector();
        Iterator it = sForm.getOrderItemActionSelectionList().iterator();
        while(it.hasNext()){
            Integer id = (Integer) it.next();
            if(id != null){
                toDelete.add(id);
            }
        }

        if(toDelete.isEmpty()){
            lUpdateErrors.add("orderItemActionSelection",new ActionError("error.nothing.selected"));
        }

        if(!(lUpdateErrors.size() == 0)){
            return lUpdateErrors;
        }
        //send to EJB for updates
        Order orderEjb = factory.getOrderAPI();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        orderEjb.deleteOrderItemActions(toDelete,appUser.getUserName());
        //refresh the data
        getPurchaseOrderStatusDetail(request,form,Integer.toString(sForm.getPurchaseOrderStatusDescDataView().getPurchaseOrderData().getPurchaseOrderId()));
        return lUpdateErrors;
    }

    /**
     *<code>updatePoItems</code> updates a purchase order's item target ship date,
     *ship status, etc. and sends the update to the PurchaseOrder EJB.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@returns  a populated action errors object, size will > 0 if an error occured
     *@throws  Exception  if an error occurs
     */
    public static ActionErrors updatePoItems(HttpServletRequest request,ActionForm form) throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        PurchaseOrderOpDetailForm sForm = (PurchaseOrderOpDetailForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        OrderItemDescDataVector items = sForm.getPurchaseOrderItemDescList();
        for (int i=0,len=items.size();i<len;i++){
            OrderItemDescData item = (OrderItemDescData) items.get(i);

            boolean qtyReq = false;

            if ((Utility.isSet(item.getShipStatus()))){
                qtyReq = true;
            }
            try{
                if (Utility.isSet(item.getTargetShipDateString())){
                    qtyReq = true;
                    if(!RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SCHEDULED.equals(item.getShipStatus()) &&
                        !RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.BACKORDERED.equals(item.getShipStatus())){
                         lUpdateErrors.add("savePoItems", new ActionError("variable.must.be.empty.error","Target Ship Date"));
                    }
                    Date targetShipDate = simpleDateFormat.parse(item.getTargetShipDateString());
                    targetShipDate = Utility.normalizeDate(targetShipDate);
                    item.getOrderItem().setTargetShipDate(targetShipDate);
                    item.setTargetShipDate(targetShipDate);
                }else{
                    if(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SCHEDULED.equals(item.getShipStatus())){
                        lUpdateErrors.add("savePoItems", new ActionError("variable.empty.dependency.error","Target Ship Date","Ship Status",RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SCHEDULED));
                    }
                    item.getOrderItem().setTargetShipDate(null);
                }
            }catch (ParseException e){
                lUpdateErrors.add("savePoItems", new ActionError("variable.date.format.error","Target Ship Date"));
            }

            try{
                if (Utility.isSet(item.getNewOrderItemActionQtyS())){
                    int qty = Integer.parseInt(item.getNewOrderItemActionQtyS());
                    item.setNewOrderItemActionQty(qty);
                }else{
                    if(qtyReq){
                        lUpdateErrors.add("NewOrderItemActionQtyS", new ActionError("variable.empty.error","Quantity"));
                    }
                    //if (Utility.isSet(item.getShipStatus())&&!(item.getShipStatus().trim().equalsIgnoreCase(RefCodeNames.ORDER_PROPERTY_SHIP_STATUS.BACKORDERED))){
                        //lUpdateErrors.add("savePoItems", new ActionError("variable.empty.error","Target Ship Date"));
                    //}
                }
            }catch (NumberFormatException e){
                lUpdateErrors.add("savePoItems", new ActionError("variable.date.format.error","Target Ship Date"));
            }

        }



        if (lUpdateErrors.size() > 0){
            return lUpdateErrors;
        }
        //send to EJB for updates
        Order orderEjb = factory.getOrderAPI();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        sForm.setPurchaseOrderItemDescList(orderEjb.updateOrderItemDescCollection(items,appUser.getUserName()));
        //refresh the data
        getPurchaseOrderStatusDetail(request,form,Integer.toString(sForm.getPurchaseOrderStatusDescDataView().getPurchaseOrderData().getPurchaseOrderId()));
        return lUpdateErrors;
    }

    /**
     *<code>updatePoStatus</code> updates the status of all pos that are "checked" (in the sForm.getSelectorBox())
     *array to @see RefCodeNames.PURCHASE_ORDER_STATUS_CD.DIST_ACKD_PURCH_ORDER and sends the update to the
     *PurchaseOrder EJB
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@returns  a populated action errors object, size will > 0 if an error occured
     *@throws  Exception  if an error occurs
     */
    public static ActionErrors updatePoStatus(HttpServletRequest request,ActionForm form) throws Exception {
        try{
            ActionErrors lUpdateErrors = new ActionErrors();

            HttpSession session = request.getSession();

            PurchaseOrderOpSearchForm sForm = (PurchaseOrderOpSearchForm)form;
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            if (null == factory) {
                throw new Exception("Without APIAccess.");
            }
            PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
            //pPurchaseOrderStatusCriteriaData searchCriteria = PurchaseOrderStatusCriteriaData.createValue();

            String[] updateList = sForm.getSelectorBox();
            PurchaseOrderDataVector pos = new PurchaseOrderDataVector();
            PurchaseOrderStatusDescDataViewVector poDescList = (PurchaseOrderStatusDescDataViewVector) sForm.getResultList();
            for (int i=0;i<updateList.length;i++){
                //now match the update list to the pos we have in memory
                for(int j=0,len=poDescList.size();j<len;j++){
                    PurchaseOrderStatusDescDataView poDesc = (PurchaseOrderStatusDescDataView) poDescList.get(j);
                    if (poDesc.getPurchaseOrderData().getPurchaseOrderId()==Integer.parseInt(updateList[i])){
                        //XXX only update if the po is in an acceptable state?
                        poDesc.getPurchaseOrderData().setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.DIST_ACKD_PURCH_ORDER);
                        purchaseOrderEjb.updatePurchaseOrder(poDesc.getPurchaseOrderData());
                    }
                }
            }
            //reset the update list
            sForm.setSelectorBox(new String[0]);
            return lUpdateErrors;
        }catch(Exception e){
            e.printStackTrace();
            throw (e);
        }
    }

    /**
     *  <code>sortItems</code> sorts items acording to the supplied field (sortField) which should
     *  be part of the request.  If this parameter is not part of the request the items order
     *  remains unchanged
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@returns  a populated action errors object, size will > 0 if an error occured
     *@throws  Exception  if an error occurs
     */
    public static ActionErrors sortItems(HttpServletRequest request,
    ActionForm form)
    throws Exception {
        ActionErrors lErrors = new ActionErrors();
        HttpSession session = request.getSession();
        PurchaseOrderOpDetailForm sForm =
        (PurchaseOrderOpDetailForm)session.getAttribute("PO_OP_DETAIL_FORM");
        if (sForm == null) {
            // not expecting this, but nothing to do if it is
            return new ActionErrors();
        }
        OrderItemDescDataVector orderItemDescList =
        (OrderItemDescDataVector)sForm.getPurchaseOrderItemDescList();
        String sortField = request.getParameter("sortField");
        if(Utility.isSet(sortField)){
            DisplayListSort.sort(orderItemDescList, sortField);
        }
        return lErrors;
    }


    /**
     *  <code>initConstantList</code> initializes a set of display variables that are common across
     *  po functions of the site.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@throws  Exception  if an error occurs
     */
    public static void initConstantList(HttpServletRequest request)
    throws Exception {
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        ListService listServiceEjb = factory.getListServiceAPI();

        if (session.getAttribute("PurchaseOrder.status.vector") == null) {
            RefCdDataVector statusv =
            listServiceEjb.getRefCodesCollection("PURCHASE_ORDER_STATUS_CD");
            session.setAttribute("PurchaseOrder.status.vector", statusv);
        }

        if (session.getAttribute("Open.line.status.vector") == null) {
            RefCdDataVector statusv =
            listServiceEjb.getRefCodesCollection("OPEN_LINE_STATUS_CD");
            session.setAttribute("Open.line.status.vector", statusv);
        }

        if (session.getAttribute("OrderProperty.ShipStatus.vector") == null){
        	CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);

            RefCdDataVector statusv =
            listServiceEjb.getRefCodesCollection("ORDER_PROPERTY_SHIP_STATUS");

            if(appUser.getUser().getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR)
            		&& appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.MODIFY_ORDER_ITEM_QTY)){

            	RefCdData newFunc = RefCdData.createValue();
            	newFunc.setRefCd("ORDER_PROPERTY_SHIP_STATUS");
            	newFunc.setShortDesc(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.QUANTITY_CHANGE);
            	newFunc.setValue(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.QUANTITY_CHANGE);

            	statusv.add(newFunc);
            }
            session.setAttribute("OrderProperty.ShipStatus.vector", statusv);
        }

    }

    /**
     *Initializes the Return Request object belonging to the supplied form with some default
     *information from the session.  Any exceptions generated are not propogated out as they
     *will be delt with elsewhere, and if they are not serious and the rest of the management
     *can continue this method is convinence and not critical to the managment of returns.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static void initEmptyReturnRequestDetail(
    HttpServletRequest request,
    ActionForm form){
        try{
            HttpSession session = request.getSession();
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
            UserInfoData uidata = factory.getUserAPI().getUserContact(appUser.getUser().getUserId());
            PurchaseOrderOpDetailForm sForm = (PurchaseOrderOpDetailForm) form;
            sForm.setReturnRequestDescData(new ReturnRequestDescDataView(ReturnRequestData.createValue(),new OrderItemDescDataVector(),OrderAddressData.createValue()));

            ReturnRequestData data = sForm.getReturnRequestDescData().getReturnRequestData();
            //contact info based off user logged in
            data.setSenderContactName(appUser.getUser().getFirstName() + " " + appUser.getUser().getLastName());
            data.setSenderContactPhone(uidata.getPhone().getPhoneNum());
            //reciever info based off order
            data.setPickupContactName(sForm.getPurchaseOrderStatusDescDataView().getOrderData().getOrderContactName());

            //set date recieved to today
            SimpleDateFormat dteFor = new SimpleDateFormat("MM/dd/yyyy",request.getLocale());
            sForm.setReturnRequestDateRecievedString(dteFor.format(new Date()));
            //set up the address to pickup to be the order shipto data by default
            OrderAddressData shipto = sForm.getPurchaseOrderStatusDescDataView().getShipToAddress();
            OrderAddressData pickup = sForm.getReturnRequestDescData().getPickupAddress();
            pickup.setShortDesc(shipto.getShortDesc());
            pickup.setAddress1(shipto.getAddress1());
            pickup.setAddress2(shipto.getAddress2());
            pickup.setAddress3(shipto.getAddress3());
            pickup.setAddress4(shipto.getAddress4());
            pickup.setCity(shipto.getCity());
            pickup.setCountryCd(shipto.getCountryCd());
            pickup.setCountyCd(shipto.getCountyCd());
            pickup.setPostalCode(shipto.getPostalCode());
            pickup.setStateProvinceCd(shipto.getStateProvinceCd());
            pickup.setFaxPhoneNum(sForm.getPurchaseOrderStatusDescDataView().getOrderData().getOrderContactFaxNum());
            pickup.setPhoneNum(sForm.getPurchaseOrderStatusDescDataView().getOrderData().getOrderContactPhoneNum());
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * <code>getReturnRequestDetail</code> fetches the Return detail from the PurchaseOrder EJB
     * (for the supplied returnRequestId) and populates the form object.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param returnRequestId a <code>String</code> value
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors getReturnRequestDetail(
    HttpServletRequest request,
    ActionForm form,
    String returnRequestId)
    throws Exception {
        ActionErrors lErrors = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        PurchaseOrder poEjb = factory.getPurchaseOrderAPI();
        int returnRequestIdI=0;
        try{
            returnRequestIdI = Integer.parseInt(returnRequestId);
        }catch(NumberFormatException e){
            lErrors.add("getReturnRequestDetail",new ActionError("error.invalidNumber","Return Request Id"));
        }
        if (lErrors.size() > 0){
            return lErrors;
        }
        PurchaseOrderOpDetailForm sForm = (PurchaseOrderOpDetailForm) form;
        sForm.setReturnRequestDescData(poEjb.getReturnRequestDetail(returnRequestIdI));


        //set the date string for editing
        SimpleDateFormat dteFor = new SimpleDateFormat("MM/dd/yyyy",request.getLocale());
        if(sForm.getReturnRequestDescData().getReturnRequestData().getDateRequestRecieved() != null){
            sForm.setReturnRequestDateRecievedString(dteFor.format(sForm.getReturnRequestDescData().getReturnRequestData().getDateRequestRecieved()));
        }else{
            sForm.setReturnRequestDateRecievedString("");
        }
        lErrors = getPurchaseOrderStatusDetail(request, form,Integer.toString(sForm.getReturnRequestDescData().getReturnRequestData().getPurchaseOrderId()));

        //set the return amount string for editing
        //and map the Order Items returned in the po to the return data.
        OrderItemDescDataVector ordItms = sForm.getPurchaseOrderItemDescList();
        OrderItemDescDataVector returnItms = sForm.getReturnRequestDescData().getReturnRequestOrderItemDescDataVector();
        for(int i=0;i<ordItms.size();i++){
            OrderItemDescData ordItm = (OrderItemDescData) ordItms.get(i);
            boolean found = false;
            for(int j=0,len2=returnItms.size();j<len2;j++){
                OrderItemDescData returnItm=(OrderItemDescData)returnItms.get(j);
                if(returnItm.getReturnRequestDetailData().getOrderItemId()==ordItm.getOrderItem().getOrderItemId()){
                    found = true;
                    ordItm.setReturnRequestDetailData(returnItm.getReturnRequestDetailData());
                    ordItm.setQtyReturnedString(Integer.toString(ordItm.getReturnRequestDetailData().getQuantityReturned()));
                    break;
                }
            }
            if(!found){
                ordItms.remove(i);
                i--;
            }
        }
        sForm.setPurchaseOrderItemDescList(ordItms);
        sForm.getReturnRequestDescData().setReturnRequestOrderItemDescDataVector(ordItms);
        return lErrors;
    }

    /**
     * <code>saveReturnRequest</code> sends the return request stored in the form to the PurchaseOrder
     * EJB for saving.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @params doingAdd
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors saveReturnRequest(
    HttpServletRequest request,
    ActionForm form, boolean doingAdd)
    throws Exception {
        ActionErrors lErrors = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        PurchaseOrder poEjb = factory.getPurchaseOrderAPI();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);

        PurchaseOrderOpDetailForm sForm = (PurchaseOrderOpDetailForm) form;
        ReturnRequestDescDataView view = sForm.getReturnRequestDescData();
        //set the purchase order id from the po
        view.getReturnRequestData().setPurchaseOrderId(
        sForm.getPurchaseOrderStatusDescDataView().getPurchaseOrderData().getPurchaseOrderId());

        //validate the data
        if(Utility.isSet(sForm.getReturnRequestDateRecievedString())){
            SimpleDateFormat dteFor = new SimpleDateFormat("MM/dd/yyyy",request.getLocale());
            try{
                view.getReturnRequestData().setDateRequestRecieved(dteFor.parse(sForm.getReturnRequestDateRecievedString()));
            }catch(java.text.ParseException e){
                lErrors.add("saveReturnRequest",new ActionError("error.badDateFormat",sForm.getReturnRequestDateRecievedString()));
            }
        }else{
            lErrors.add("saveReturnRequest",new ActionError("variable.empty.error","Date Return Request Recieved"));
        }
        if(!Utility.isSet(view.getReturnRequestData().getReason())){
            lErrors.add("saveReturnRequest",new ActionError("variable.empty.error","Reason"));
        }
        if(!Utility.isSet(view.getReturnRequestData().getSenderContactName())){
            lErrors.add("saveReturnRequest",new ActionError("variable.empty.error","Sender Contact Name"));
        }
        if(!Utility.isSet(view.getReturnRequestData().getSenderContactPhone())){
            lErrors.add("saveReturnRequest",new ActionError("variable.empty.error","Sender Contact Phone"));
        }
        if(!Utility.isSet(view.getReturnRequestData().getPickupContactName())){
            lErrors.add("saveReturnRequest",new ActionError("variable.empty.error","Pickup Contact"));
        }
        if(!Utility.isSet(view.getPickupAddress().getPhoneNum())){
            lErrors.add("saveReturnRequest",new ActionError("variable.empty.error","Pickup Contact Phone"));
        }
        //if(!Utility.isSet(view.getPickupAddress().getFaxPhoneNum())){
        //    lErrors.add("saveReturnRequest",new ActionError("variable.empty.error","Fax Contact Phone"));
        //}
        if(!Utility.isSet(view.getPickupAddress().getShortDesc())){
            lErrors.add("saveReturnRequest",new ActionError("variable.empty.error","Pickup Address Name"));
        }
        if(!Utility.isSet(view.getPickupAddress().getAddress1())){
            lErrors.add("saveReturnRequest",new ActionError("variable.empty.error","Pickup Address Line 1"));
        }
        if(!Utility.isSet(view.getPickupAddress().getCity())){
            lErrors.add("saveReturnRequest",new ActionError("variable.empty.error","Pickup Address City"));
        }
        if(!Utility.isSet(view.getPickupAddress().getStateProvinceCd())){
            lErrors.add("saveReturnRequest",new ActionError("variable.empty.error","Pickup Address State"));
        }
        if(!Utility.isSet(view.getPickupAddress().getPostalCode())){
            lErrors.add("saveReturnRequest",new ActionError("variable.empty.error","Pickup Address Zip Code"));
        }
        //parse out the qty filled in on the form into the return request detail objects
        OrderItemDescDataVector itms = sForm.getPurchaseOrderItemDescList();
        boolean somethingReturned = false;
        for(int i=0;i<itms.size();i++){
            OrderItemDescData itm = (OrderItemDescData) itms.get(i);
            if(Utility.isSet(itm.getQtyReturnedString())){
                somethingReturned = true;

                if(itm.getReturnRequestDetailData() == null){
                    itm.setReturnRequestDetailData(ReturnRequestDetailData.createValue());
                }
                itm.getReturnRequestDetailData().setOrderItemId(itm.getOrderItem().getOrderItemId());
                try{
                    itm.getReturnRequestDetailData().setQuantityReturned(Integer.parseInt(itm.getQtyReturnedString()));
                }catch(NumberFormatException e){
                    lErrors.add("saveReturnRequest",new ActionError("error.invalidNumber","quantity returned ("+itm.getQtyReturnedString()+")"));
                }
            }else{
                if(itm.getReturnRequestDetailData() != null){
                    itm.getReturnRequestDetailData().setQuantityReturned(0);
                }
            }
        }
        //nothing was filled in.
        if(!somethingReturned){
            if(doingAdd){
                lErrors.add("saveReturnRequest",new ActionError("returns.noitems"));
            }
        }
        //XXX this is sort of a hack, just ignores item updates
        //if(doingAdd){
        view.setReturnRequestOrderItemDescDataVector(sForm.getPurchaseOrderItemDescList());
        //}
        if (lErrors.size() > 0){
            return lErrors;
        }
        view = poEjb.addUpdateReturnRequest(view,appUser.getUserName());
        session.setAttribute("returnRequest.id",Integer.toString(view.getReturnRequestData().getReturnRequestId()));
        return getReturnRequestDetail(request, form, Integer.toString(view.getReturnRequestData().getReturnRequestId()));
    }





    /**
     *Verifies that the authorized distributor config is setup correctly.  If not generates the apropriate action error.
     *Will add to the passed in action errors appropriate error messages.
     *@param ActionErrors to add errors to
     *@param HttpServletRequest request that should be authorized
     *@param Integer the id of the distributor to check for authorization
     *@returns true if the user is authorized, false if not.
     */
    private static boolean isAuthorizedForDistributor(ActionErrors pErrors, HttpServletRequest request, Integer distId){
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);

        String type = appUser.getUser().getUserTypeCd();
        if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(type) ||
                RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(type) ||
                RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(type) ||
                RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(type) ||
                RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(type)){
            return true;
        }
        IdVector authorizedDistIds = getAuthorizedDistIds(session);
        if(authorizedDistIds == null || authorizedDistIds.size() == 0){
            pErrors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.systemError","No Distributors Configured For User"));
        }else if(authorizedDistIds.contains(distId)){
            return true;
        }
        pErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.unauthorized"));
        return false;
    }



    /**
     * <code>getPurchaseOrderStatusDetail</code> fetches the purchase order detail from the EJB layer
     * and populates the passed in form.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param purchaseOrderStatusId a <code>String</code> value
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors getPurchaseOrderStatusDetail(
    HttpServletRequest request,
    ActionForm form,
    String purchaseOrderStatusId)
    throws Exception {
        ActionErrors lErrors = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        PurchaseOrder poEjb = factory.getPurchaseOrderAPI();
        Order orderEjb = factory.getOrderAPI();
        Distributor distEjb = factory.getDistributorAPI();
        if( null == purchaseOrderStatusId || "".equals(purchaseOrderStatusId)) {
            purchaseOrderStatusId = (String)session.getAttribute("PurchaseOrderStatus.id");
        }
        session.setAttribute("PurchaseOrderStatus.id", purchaseOrderStatusId);

        PurchaseOrderStatusDescDataView purchaseOrderStatusDetail =
        poEjb.getPurchaseOrderStatusDesc
        (Integer.parseInt(purchaseOrderStatusId));

        //check authorization
        if(purchaseOrderStatusDetail.getDistributorBusEntityData() == null){
            lErrors.add("distributorId",
            new ActionError("error.simpleGenericError","No distributor for this purchase order"));
        }else{
            Integer distId = new Integer(purchaseOrderStatusDetail.getDistributorBusEntityData().getBusEntityId());
            if(!isAuthorizedForDistributor(lErrors,request,distId)){
                return lErrors;
            }
        }

        if(lErrors.size() > 0){
            return lErrors;
        }

        //the adding of order notes uses this property
        session.setAttribute("orderid",Integer.toString(purchaseOrderStatusDetail.getOrderData().getOrderId()));
        PurchaseOrderOpDetailForm detailForm = (PurchaseOrderOpDetailForm) form;
        detailForm.setTargetShipDate("");
        detailForm.setShippingStatus("");

        detailForm.setPurchaseOrderStatus(purchaseOrderStatusDetail.getPurchaseOrderData().getPurchaseOrderStatusCd());

        int thisOrderId = 0;
        int distId = purchaseOrderStatusDetail.getDistributorBusEntityData().getBusEntityId();
        if(purchaseOrderStatusDetail.getDistributorBusEntityData() != null){

            try{
                DistributorData dist = distEjb.getDistributor(distId);
                detailForm.setDistributorData(dist);
            }catch(DataNotFoundException e){
                detailForm.setDistributorData(DistributorData.createValue());
            }
        }

        //get delivery date
        Date orderDate = purchaseOrderStatusDetail.getOrderData().getOriginalOrderDate();
        Date orderTime = purchaseOrderStatusDetail.getOrderData().getOriginalOrderTime();
        String zipCode = purchaseOrderStatusDetail.getShipToAddress().getPostalCode();
        int siteId = purchaseOrderStatusDetail.getOrderData().getSiteId();
        Date deliveryDate = null;
        if(siteId!=0) {
            deliveryDate = distEjb.getDeliveryDate(distId, null, siteId,
            orderDate, orderTime, zipCode);
        }
        purchaseOrderStatusDetail.setDeliveryDate(deliveryDate);

        //Get only the notes from the vector for the selected order
        //getOrderPropertyVec takes care of making sure we only get notes,
        //and only the notes for the proper order
        OrderStatusDescData osdd = orderEjb.getOrderStatusDesc(purchaseOrderStatusDetail.getOrderData().getOrderId());
        detailForm.setOrderStatusDescData(osdd);
        OrderPropertyDataVector orderPropertyDetailVec = orderEjb.getOrderPropertyVec(purchaseOrderStatusDetail.getOrderData().getOrderId());
        String reqshipdate = osdd.getRequestedShipDate();
        OrderPropertyData orderPropertyDetail;
        if ( null != orderPropertyDetailVec && 0 < orderPropertyDetailVec.size()) {
            //We want to see the notes in the order they were written
            DisplayListSort.sort(orderPropertyDetailVec, "propertyId");

            //set the OrderPropertyDetail to the latest object
            int lastItem = orderPropertyDetailVec.size()-1;
            orderPropertyDetail = (OrderPropertyData)orderPropertyDetailVec.get(lastItem);
        }else{
            orderPropertyDetail = OrderPropertyData.createValue();
        }
        if (null != purchaseOrderStatusDetail.getOrderData()) {
            thisOrderId =  purchaseOrderStatusDetail.getOrderData().getOrderId();
        }

        OrderItemDescDataVector itemStatusDescV =
        orderEjb.getOrderItemDescCollection
        (thisOrderId, null, Integer.parseInt(purchaseOrderStatusId));

        OrderMetaDataVector orderMetaDVec = osdd.getOrderMetaData();
        detailForm.setOrderMetaList(orderMetaDVec);

        //sets the po and order info
        detailForm.setPurchaseOrderStatusDescDataView(purchaseOrderStatusDetail);

        //set order notes, pos don't have notes
        detailForm.setOrderPropertyList(orderPropertyDetailVec);
        detailForm.setOrderPropertyDetail(orderPropertyDetail);
        //sets the items
        detailForm.setPurchaseOrderItemDescList(itemStatusDescV);
        // set the total order amount
        BigDecimal totalOrderAmount = new BigDecimal(0);
        if (null != purchaseOrderStatusDetail.getOrderData()) {

            OrderData ord = purchaseOrderStatusDetail.getOrderData();
            if (null != ord.getTotalPrice()) {
                totalOrderAmount = totalOrderAmount.add(ord.getTotalPrice());
            }
            if (null != ord.getTotalFreightCost()) {
                totalOrderAmount = totalOrderAmount.add(ord.getTotalFreightCost());
            }
            if (null != ord.getTotalTaxCost()) {
                totalOrderAmount = totalOrderAmount.add(ord.getTotalTaxCost());
            }
            if (null != ord.getTotalMiscCost()) {
                totalOrderAmount = totalOrderAmount.add(ord.getTotalMiscCost());
            }
        }
        detailForm.setOrderTotalAmount(totalOrderAmount);

        //set the purchase order amount
        BigDecimal miscCharges = new BigDecimal(0);
        BigDecimal taxTotal = new BigDecimal(0);        
        if(purchaseOrderStatusDetail.getPurchaseOrderData() != null){
            PurchaseOrderData po = purchaseOrderStatusDetail.getPurchaseOrderData();
            if (po.getPurchaseOrderTotal() != null){
                miscCharges = miscCharges.add(po.getPurchaseOrderTotal()); // Sales Tax IS INCLUDED in "Purchase Order Total"
            }
            if (po.getLineItemTotal() != null){
                miscCharges = miscCharges.subtract(po.getLineItemTotal()); // Sales Tax IS NOT INCLUDED in "Line Item Total"
            }
            //Now Misc. Charges = Sales Tax (if applicable) + Misc. Charges
            
            // Purchase Order Items can be taxable; if they are taxable - recalculate Misc. Charges for this Purchase Order
            if (po.getTaxTotal() != null){
                miscCharges = miscCharges.subtract(po.getTaxTotal());
            }
            
        }
        detailForm.setMiscellaneousAmount(miscCharges);
        
        /*** we do not need to store Sales Tax in the form - it is stored in the DB table clw_purchase_order ***/
        /***
        if (po.getTaxTotal() != null) {
        	taxTotal = po.getTaxTotal();
        }  
        detailForm.setTaxTotalAmount(taxTotal);
        ***/

        int orderedNum = 0;
        int acceptedNum = 0;
        int backorderedNum = 0;
        int substitutedNum = 0;
        int shippedNum = 0;
        int invoicedNum = 0;
        //Date lastDate = null;
        if (null != itemStatusDescV && 0 < itemStatusDescV.size() ) {
            SimpleDateFormat dteFor = new SimpleDateFormat("MM/dd/yyyy",request.getLocale());

            for(int i = 0; i < itemStatusDescV.size(); i++) {
                OrderItemDescData orderItemDescD =
                (OrderItemDescData)itemStatusDescV.get(i);

                //setup the targetDate string based off the items target ship date value
                if (orderItemDescD.getOrderItem().getTargetShipDate() != null){
                    String dteS = dteFor.format(orderItemDescD.getOrderItem().getTargetShipDate());
                    orderItemDescD.setTargetShipDateString(dteS);
                }else{
                    orderItemDescD.setTargetShipDateString("");
                }

                // add the itemQuantity to orderedNum
                orderedNum += orderItemDescD.getOrderItem().getTotalQuantityOrdered();

                // get the quantity for different action
                OrderItemActionDataVector itemActions = orderItemDescD.getOrderItemActionList();
                if( null != itemActions ) {
                    for( int j = 0; j < itemActions.size(); j++) {
                        OrderItemActionData itemActionD = (OrderItemActionData)itemActions.get(j);
                        /*if (null == lastDate) {
                            lastDate = itemActionD.getActionDate();
                        }
                        else {
                            if(lastDate.before(itemActionD.getActionDate()) ) {
                                lastDate = itemActionD.getActionDate();
                            }
                        }*/

                        String actionCd = itemActionD.getActionCd();
                        if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACCEPTED.equals(actionCd)) {
                            acceptedNum += itemActionD.getQuantity();
                        } else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SUBSTITUTED.equals(actionCd)) {
                            substitutedNum += itemActionD.getQuantity();
                        } else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_INVOICED.equals(actionCd)) {
                            invoicedNum += itemActionD.getQuantity();
                        } else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED.equals(actionCd)) {
                            shippedNum += itemActionD.getQuantity();
                        }

                    }   // end of loop of itemActions
                }   //end of if null != itemActions

            } // end of the loop for itemStatusDescV
            if (0 == shippedNum ) {
                backorderedNum = 0;
            }
            else {
                backorderedNum = orderedNum - shippedNum;
            }

        }   // end of if null != itemStatusDescV

        //detailForm.setLastDate(lastDate);
        detailForm.setOrderedNum(orderedNum);
        detailForm.setAcceptedNum(acceptedNum);
        detailForm.setBackorderedNum(backorderedNum);
        detailForm.setSubstitutedNum(substitutedNum);
        detailForm.setShippedNum(shippedNum);
        detailForm.setInvoicedNum(invoicedNum);
        detailForm.setTargetShipDate(reqshipdate);
        //initialize the constants lists for states and countries
        initConstantList(request);

        return lErrors;
    }

    /**
     * <code>note</code> prepares for distributor po note editing
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @returns  a populated action errors object, size will > 0 if an error occured
     * @throws Exception if an error occurs
     */
    public static ActionErrors note(
    HttpServletRequest request,
    ActionForm form)
    throws Exception {
        ActionErrors lErrors = new ActionErrors();
        HttpSession session = request.getSession();
        String indexS = request.getParameter("index");
        int index = 0;
        try {
          index = Integer.parseInt(indexS);
        } catch (Exception exc) {
          String errorMess = "Illegal line number";
          lErrors.add("lineNumber",
          new ActionError("error.simpleGenericError",errorMess));
          return lErrors;
        }

        PurchaseOrderOpDetailForm pForm = (PurchaseOrderOpDetailForm) form;
        OrderItemDescData orderItemDescD = pForm.getPurchaseOrderItemDesc(index);
        if(orderItemDescD==null) {
          String errorMess = "Wrong line number";
          lErrors.add("lineNumber",
          new ActionError("error.simpleGenericError",errorMess));
          return lErrors;
        }

        OrderPropertyData note = orderItemDescD.getDistPoNote();
        if(note==null) {
          note = OrderPropertyData.createValue();
          OrderItemData orderItemD = orderItemDescD.getOrderItem();
          note.setOrderId(orderItemD.getOrderId());
          note.setOrderItemId(orderItemD.getOrderItemId());
          note.setShortDesc("Distributor Po Note");
          note.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
          note.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.DISTRIBUTOR_PO_NOTE);
          orderItemDescD.setDistPoNote(note);
        }
        pForm.setDistNoteLine(index);
        return lErrors;
    }

    /**
     *Initializes the request for entering data for these manifest entries (weight, size, etc).
     *
     *@param HttpServletRequest the request
     *@param ActionForm the form to be validate
     *@returns  a populated action errors object, size will > 0 if an error occured
     */
    public static ActionErrors initManifestDataEntry(HttpServletRequest request,ActionForm form){
        ActionErrors lErrors = new ActionErrors();
        int numToManifest = 0;
        PurchaseOrderStatusDescDataViewVector newPoList = new PurchaseOrderStatusDescDataViewVector();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        PurchaseOrderOpSearchForm sForm = (PurchaseOrderOpSearchForm) form;
        List pos = sForm.getResultList();
        String[] qtbArray  = sForm.getQuantityBarcode();
        for(int i=0;i<qtbArray.length;i++){
            String qtbS = qtbArray[i];
            if(Utility.isSet(qtbS)){
                PurchaseOrderStatusDescDataView po = (PurchaseOrderStatusDescDataView) pos.get(i);
                int qtb;
                try{
                    qtb = Integer.parseInt(qtbS);
                    if(qtb < 0){
                        throw new NumberFormatException("qty to barcode must be postive");
                    }
                    if(qtb > 0){
                        if(po.getManifestItems() == null){
                            po.setManifestItems(new ManifestItemViewVector());
                        }else{
                            po.getManifestItems().clear();
                        }
                        for(int j=0;j<qtb;j++){
                            ManifestItemView aPackage = ManifestItemView.createValue();
                            aPackage.setCubicSizeString("");
                            aPackage.setWeightString("");
                            aPackage.getManifestItem().setAddBy(appUser.getUserName());
                            aPackage.getManifestItem().setModBy(appUser.getUserName());
                            aPackage.getManifestItem().setPurchaseOrderId(po.getPurchaseOrderData().getPurchaseOrderId());
                            po.getManifestItems().add(aPackage);
                            numToManifest++;
                        }
                        newPoList.add(po);
                    }
                }catch(NumberFormatException e){
                    lErrors.add("resultList", new ActionError("distributor.error.qty.barcode.invalidNumber",po.getPurchaseOrderData().getErpPoNum()));
                }
            }
        }

        if(numToManifest == 0 && lErrors.size() == 0){
            lErrors.add("resultList", new ActionError("distributor.error.qty.barcode.emptyList"));
        }
        sForm.setToManifestList(newPoList);
        return lErrors;
    }


    /**
     *processes a request from the user to complete this manifest.  Manifest will then be sent to
     *freight handler by some other program.
     *
     *@param HttpServletRequest the request
     *@returns  a populated action errors object, size will > 0 if an error occured
     */
    public static ActionErrors processManifestCompleteRequest(HttpServletRequest request){
        ActionErrors lErrors = new ActionErrors();
        HttpSession session = request.getSession();
        try{
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            PurchaseOrder poEjb = factory.getPurchaseOrderAPI();
            CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
            String distribCtr = appUser.getDistributionCenterId();
            if(!Utility.isSet(distribCtr)){
                lErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("distributor.error.no.distribution.center"));
            }
            if(lErrors.size() > 0){
                return lErrors;
            }
            BusEntityDataVector dists = appUser.getDistributors();
            IdVector distIds = Utility.toIdVector(dists);
            poEjb.processManifestComplete(distribCtr,distIds);
        }catch(Exception e){
            e.printStackTrace();
            lErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.systemError",e.getMessage()));
        }
        return lErrors;
    }


    /**
     *Prints the Manifest Labels to the request stream so they may be printed
     *
     *@param HttpServletRequest the request
     *@param HttpServletResponse the response to stream the printable labels to
     *@param ActionForm the form to be validate
     *@returns  a populated action errors object, size will > 0 if an error occured
     */
    public static ActionErrors printManifestLabels(HttpServletRequest request,HttpServletResponse response ,ActionForm form){
        ActionErrors lErrors = new ActionErrors();
        PurchaseOrder poEjb = null;
        PropertyService propEjb = null;
        EmailClient emailEjb = null;
        Distributor distributorEjb = null;
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        try{
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            poEjb = factory.getPurchaseOrderAPI();
            propEjb = factory.getPropertyServiceAPI();
            emailEjb = factory.getEmailClientAPI();
            distributorEjb = factory.getDistributorAPI();
        }catch(Exception e){
            lErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.systemError", e.getMessage()));
            return lErrors;
        }

	    AddressData returnAddress = appUser.getContact().getAddressData();
        if(returnAddress == null){
            lErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.systemError", "No contact address setup for user"));
        }
        String distributionCenterId = appUser.getDistributionCenterId();
        if(!Utility.isSet(distributionCenterId)){
            lErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.systemError", "No distribution center setup for user"));
        }
        if(lErrors.size() > 0){
            return lErrors;
        }

        PurchaseOrderOpSearchForm sForm = (PurchaseOrderOpSearchForm) form;
        PurchaseOrderStatusDescDataViewVector pos = sForm.getToManifestList();
        Iterator it = pos.iterator();
        //HashMap distMap = new HashMap();
        //loop through and validate the data and get the associated distributors
        while(it.hasNext()){
            PurchaseOrderStatusDescDataView po = (PurchaseOrderStatusDescDataView) it.next();
            Integer distId = new Integer(po.getDistributorBusEntityData().getBusEntityId());
            po.getPurchaseOrderData().setPurchOrdManifestStatusCd(RefCodeNames.PURCH_ORD_MANIFEST_STATUS_CD.MANIFESTED);

            //validate the manifests and assign our session info to the constructed objects
            Iterator manIt = po.getManifestItems().iterator();
            int manifestCounter = 0;
            while(manIt.hasNext()){
                ManifestItemView manifest = (ManifestItemView) manIt.next();
                //cubic size
                if(!Utility.isSet(manifest.getCubicSizeString())){
                    //lErrors.add("toManifest", new ActionError("distributor.error.invalidManifestPackageField","Cubic Size",po.getPurchaseOrderData().getErpPoNum(),new Integer(manifestCounter)));
                }else{
                    try{
                        manifest.getManifestItem().setCubicSize(new BigDecimal(manifest.getCubicSizeString()));
                    }catch(RuntimeException e){
                        lErrors.add("toManifest", new ActionError("distributor.error.invalidManifestPackageField","Cubic Size",po.getPurchaseOrderData().getErpPoNum(),new Integer(manifestCounter)));
                    }
                }
                //weight
                if(!Utility.isSet(manifest.getWeightString())){
                    //lErrors.add("toManifest", new ActionError("distributor.error.invalidManifestPackageField","Weight",po.getPurchaseOrderData().getErpPoNum(),new Integer(manifestCounter)));
                }else{
                    try{
                        manifest.getManifestItem().setWeight(new BigDecimal(manifest.getWeightString()));
                    }catch(RuntimeException e){
                        lErrors.add("toManifest", new ActionError("distributor.error.invalidManifestPackageField","Weight",po.getPurchaseOrderData().getErpPoNum(),new Integer(manifestCounter)));
                    }
                }

                manifest.setReturnAddress(Utility.toOrderAddress(returnAddress));
                manifest.getManifestItem().setDistributionCenterId(distributionCenterId);

                manifestCounter++;
            }
        }
        //if any errors occured during validation return them now
        if(lErrors.size() > 0){
            return lErrors;
        }



        //assign the manifest package ids
        try{
            pos = poEjb.assignManifestPackageIds(pos);
        }catch(Exception e){
            e.printStackTrace();
            lErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.systemError","Error Code POLOGIC 2"));
        }

        //if any errors occured during validation return them now
        if(lErrors.size() > 0){
            return lErrors;
        }

        //get the user setup configured for viewing the pdf (size, mode etc)
        Integer lblH = null;
        Integer lblW = null;
        String lblMode = null;
        String lblType = null;
        try{
            lblH = (Integer) session.getAttribute(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_HEIGHT);
            lblW = (Integer) session.getAttribute(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_WIDTH);
            lblMode = (String) session.getAttribute(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_MODE);
            lblType = (String) session.getAttribute(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_TYPE);
            int userId = appUser.getUser().getUserId();

            if(lblH == null){
                try{
                    String lblHS = propEjb.getUserProperty(userId, RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_HEIGHT);
                    lblH = new Integer(lblHS);
                    //commented out for debugging with vendor only
                    //session.setAttribute(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_HEIGHT, lblH);
                }catch(DataNotFoundException e){}
            }
            if(lblW == null){
                try{
                    String lblWS = propEjb.getUserProperty(userId, RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_WIDTH);
                    lblW = new Integer(lblWS);
                    //commented out for debugging with vendor only
                    //session.setAttribute(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_WIDTH, lblW);
                }catch(DataNotFoundException e){}
            }
            if(lblMode == null){
                try{
                    lblMode = propEjb.getUserProperty(userId, RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_MODE);
                    session.setAttribute(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_MODE,lblMode);
                }catch(DataNotFoundException e){}
            }
            if(lblType == null){
                try{
                    lblType = propEjb.getUserProperty(userId, RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_TYPE);
                }catch(DataNotFoundException e){}
                if(!Utility.isSet(lblType)){
                    lblType = RefCodeNames.MANIFEST_LABEL_TYPE_CD.PLAIN;
                }
                session.setAttribute(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_TYPE,lblType);
            }

        }catch(Exception e){
            e.printStackTrace();
            lErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.systemError","Error Code POLOGIC 3"));
            return lErrors;
        }

        Iterator poIt = pos.iterator();
        while(poIt.hasNext()){
                PurchaseOrderStatusDescDataView po = (PurchaseOrderStatusDescDataView) poIt.next();
                Iterator manIt = po.getManifestItems().iterator();
                while(manIt.hasNext()){
                    ManifestItemView manifest = (ManifestItemView) manIt.next();
                    manifest.getManifestItem().setManifestLabelTypeCd(lblType);
                }
        }

        //stream the pdf to the response stream
        try{
            //sets the content type so the browser knows this is a pdf
            response.setContentType("application/pdf");
			response.setHeader("extension", "pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");


            //create a temporary storage buffer so we can correctly set
            //the content length of the response
            ByteArrayOutputStream pdfout = new ByteArrayOutputStream();
            PdfManifestLabel pdf = new PdfManifestLabel();

            if(lblH != null){
                pdf.setPageHeight(lblH.intValue());
            }

            if(lblW != null){
                pdf.setPageWidth(lblW.intValue());
            }
            pdf.setPrintMode(lblMode);
            pdf.setLabelType(lblType);

            pdf.constructPdfManifestLabel(pdfout,pos);

            response.setContentLength(pdfout.size());
            pdfout.writeTo(response.getOutputStream());
            response.flushBuffer();
            response.getOutputStream().close();

        }catch(Exception e){
            e.printStackTrace();
            lErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.systemError", "Error generating pdf: " + e.getMessage()));
            return lErrors;
        }

        //update po_status
        try{
            pos = poEjb.updateManifestPackagesAndPurchaseOrderData(pos);
            sForm.setToManifestList(pos); //so refreshing does not add them again to the database
        }catch(Exception e){
            e.printStackTrace();
            try{
                StringBuffer message = new StringBuffer(e.getMessage());
                message.append("::"+appUser+"::"+pos);
                emailEjb.send(emailEjb.getDefaultEmailAddress(),
			      emailEjb.getDefaultEmailAddress(),
			      "DIST MANIFEST FAILED UPDATE",
                e.getMessage(),Constants.EMAIL_FORMAT_PLAIN_TEXT,0,0);
            }catch(Exception e2){
                //big trouble...not much we can do
                e2.printStackTrace();
            }
        }
        return lErrors;
    }

    private static HashMap getAssetInfo(Asset assetEjb, IdVector assetIds) {
        AssetSearchCriteria criteria = new AssetSearchCriteria();
        HashMap assetInfo = null;
        criteria.setAssetIds(assetIds);
        try {
            AssetDataVector assetDV = assetEjb.getAssetDataByCriteria(criteria);
            assetInfo = new HashMap();
            Iterator it = assetDV.iterator();
            while (it.hasNext()) {
                AssetData assetData = (AssetData) it.next();
                if (!assetInfo.containsKey(String.valueOf(assetData.getAssetId()))) {
                    assetInfo.put(String.valueOf(assetData.getAssetId()), assetData);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return assetInfo;
    }

    public static ActionErrors fillPurchaseOrderDistributorField(ActionForm form) throws Exception {
      ActionErrors ae = new ActionErrors();
      if (form instanceof PurchaseOrderOpSearchForm) {
          PurchaseOrderOpSearchForm poForm = (PurchaseOrderOpSearchForm)form;
          LocateStoreDistForm locateDistForm = poForm.getLocateStoreDistForm();
          if (locateDistForm != null) {
              DistributorDataVector distriutors = locateDistForm.getDistributorsToReturn();
              if (distriutors != null) {
                  if (distriutors.size() > 1) {
                      ae.add("distributorId", new ActionError("error.simpleGenericError", 
                          "Only one distributor can be selected"));
                      DistributorData distriutor = (DistributorData)distriutors.get(0);
                      poForm.setDistributorId(String.valueOf(distriutor.getBusEntity().getBusEntityId()));
                  } else if (distriutors.size() == 1) {
                      DistributorData distriutor = (DistributorData)distriutors.get(0);
                      poForm.setDistributorId(String.valueOf(distriutor.getBusEntity().getBusEntityId()));
                  }
              } 
          }
      }
      return ae;
    }

    public static ActionErrors fillPurchaseOrderAccountsField(ActionForm form) throws Exception {
      ActionErrors ae = new ActionErrors();
      if (form instanceof PurchaseOrderOpSearchForm) {
          PurchaseOrderOpSearchForm poForm = (PurchaseOrderOpSearchForm)form;
          LocateStoreAccountForm locateAccountForm = poForm.getLocateStoreAccountForm();
          if (locateAccountForm != null) {
              AccountDataVector accounts = locateAccountForm.getAccountsToReturn();
              if (accounts != null && accounts.size() > 0) {
                  StringBuilder buffer = new StringBuilder();
                  for (int i = 0; i < accounts.size(); ++i) {
                      AccountData account = (AccountData)accounts.get(i);
                      if (i > 0) {
                          buffer.append(",");
                      }
                      buffer.append(String.valueOf(account.getAccountId()));
                  }
                  poForm.setAccountIdList(buffer.toString());
              } 
          }
      }
      return ae;
    }

    public static ActionErrors fillPurchaseOrderSiteField(ActionForm form) throws Exception {
      ActionErrors ae = new ActionErrors();
      if (form instanceof PurchaseOrderOpSearchForm) {
          PurchaseOrderOpSearchForm poForm = (PurchaseOrderOpSearchForm)form;
          LocateStoreSiteForm locateSiteForm = poForm.getLocateStoreSiteForm();
          if (locateSiteForm != null) {
              SiteViewVector sites = locateSiteForm.getSitesToReturn();
              if (sites != null) {
                  if (sites.size() > 1) {
                      ae.add("siteId", new ActionError("error.simpleGenericError", 
                          "Only one site can be selected"));
                      SiteView site = (SiteView)sites.get(0);
                      poForm.setSiteId(String.valueOf(site.getId()));
                  } else if (sites.size() == 1) {
                      SiteView site = (SiteView)sites.get(0);
                      poForm.setSiteId(String.valueOf(site.getId()));
                  }
              } 
          }
      }
      return ae;
    }

}


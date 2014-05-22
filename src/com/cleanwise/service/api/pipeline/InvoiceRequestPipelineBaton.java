package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.APIAccess;
import org.apache.log4j.Category;

import java.util.*;
import java.math.BigDecimal;
/**
 * Title:        InvoiceRequestPipelineBaton
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         23.04.2007
 * Time:         13:27:32
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class InvoiceRequestPipelineBaton extends PipelineBaton  {
	private static final Category log = Category.getInstance(InvoiceRequestPipelineBaton.class);
  private static final String className = "InvoiceRequestPipelineBaton";

    private String mProcessName;
    private int mTradingPartnerId;
    private InvoiceRequestData mInvoiceRequest;
    private OrderItemDataVector mPOItems;
    private ArrayList mNotesToLog = new ArrayList();
    private PurchaseOrderData mPurchaseOrder;
    private DistributorData mDistributor;
    private OrderData mOrder;
    private TradingPartnerInfo  mTradingPartnerInfo;
    private HashMap mParamMap=new HashMap();
    private HashMap mMiscInvoiceNotes= new HashMap();
    private List mItemNotesToLog=new ArrayList(0);
    private int mCountInsertedInvoiceItems=0;
    private InvoiceDistDataVector mInsertedInvoices=new InvoiceDistDataVector();
    private BigDecimal mRecivedTotal=new BigDecimal(0);
    InvoiceDistDetailDescDataVector mToInsertItems=new InvoiceDistDetailDescDataVector();
    private HashMap mTradPartnerAssoc=new HashMap();
    private int mExceptionCount=0;

    //Temporarily  hold variable for each item in InvoiceRequestItemProcessor
    private Boolean mCurrInsInvDetailFlag;
    InvoiceDistDetailData mCurrInoiceDetailData;

    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";

    public InvoiceRequestPipelineBaton populatePipelineBaton(InvoiceRequestData pInvoiceRequest, Integer pTradingPartnerId,String processName) throws Exception {
        this.mProcessName=processName;
        return populatePipelineBaton(pInvoiceRequest,pTradingPartnerId);
    }

    public void populatePipelineBaton(APIAccess factory,InvoiceRequestData pInvoiceRequest, Integer pTradingPartnerId) throws Exception {

    	log.info("populatePipelineBaton started");
        TradingPartner tradingEjb=factory.getTradingPartnerAPI();
        Order orderEjb = factory.getOrderAPI();
        Distributor distEjb =factory.getDistributorAPI();

        this.mInvoiceRequest    = pInvoiceRequest;
        this.mInvoiceRequest.getInvoiceD().setAddBy(mProcessName);
        this.mInvoiceRequest.getInvoiceD().setModBy(mProcessName);

        log.info("76");
        this.mTradPartnerAssoc  = tradingEjb.getMapTradingPartnerAssocIds(pTradingPartnerId.intValue());

        IdVector storeIds       = (IdVector) this.mTradPartnerAssoc.get(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);

        log.info("83");
        
        if (mInvoiceRequest.getInvoiceD().getStoreId() == 0 && storeIds != null && storeIds.size() == 1) {
            mInvoiceRequest.getInvoiceD().setStoreId(((Integer) storeIds.get(0)).intValue());
        }
        log.info("88");

        String distAccRef = pInvoiceRequest.getDistributorAccountRefNum();
        log.info("############## distAccRef="+distAccRef);
        
        IntegrationServices integrationServices = factory.getIntegrationServicesAPI();
        if(mInvoiceRequest.getMatchPoNumType() == null){
        	mInvoiceRequest.setMatchPoNumType(RefCodeNames.MATCH_PO_NUM_TYPE_CD.DEFAULT);
        }
        String poNum = mInvoiceRequest.getMatchPoNumType().equals(RefCodeNames.MATCH_PO_NUM_TYPE_CD.VENDOR_ORDER_NUM) 
        	? mInvoiceRequest.getInvoiceD().getDistOrderNum() : mInvoiceRequest.getInvoiceD().getErpPoNum();
        log.info("############## poNum="+poNum + ", matchPoNumType:" + mInvoiceRequest.getMatchPoNumType());
        PurchaseOrderData po = integrationServices.getPurchaseOrderByPoNum(poNum, pTradingPartnerId.intValue(), null, distAccRef, mInvoiceRequest.getMatchPoNumType());
        
        log.info("113");

        this.mPurchaseOrder     = po;
        this.mPOItems           = getOriginalOrderRecords(orderEjb,po,mInvoiceRequest.getInvoiceD());

        log.info("118");
        if(mPurchaseOrder!=null&&mPOItems!=null&&mPOItems.size()>0) {

        this.mTradingPartnerInfo= getTradingPartnerInfo(tradingEjb,mPurchaseOrder.getDistErpNum());
        this.mDistributor=getDistributorData(distEjb,0,mPurchaseOrder.getDistErpNum());
        mOrder=orderEjb.getOrder(po.getOrderId());

        log.info("125");
        this.mInvoiceRequest=reSettingStoreId(this.mInvoiceRequest,storeIds,this.mNotesToLog);

        log.info("Adding invoice to database");
        addDataToInvoiceData(mOrder, this.mPurchaseOrder,
                              this.mInvoiceRequest.getInvoiceD(),
                              this.mDistributor,
                              this.mNotesToLog);

        }

        //------------end populating of the store id vector


    }

    public InvoiceRequestPipelineBaton populatePipelineBaton(InvoiceRequestData pInvoiceRequest, Integer pTradingPartnerId) throws Exception {
      populatePipelineBaton(APIAccess.getAPIAccess(),pInvoiceRequest,pTradingPartnerId);
      return this;
    }

    private void addDataToInvoiceData(OrderData pOrder, PurchaseOrderData pPurchaseOrder, InvoiceDistData pInvoiceD,DistributorData dist, ArrayList notesToLog) {

        //1
        String newPOnum = pPurchaseOrder.getErpPoNum();
        if (Utility.isSet(newPOnum) && !pInvoiceD.getErpPoNum().equals(newPOnum)) {
            notesToLog.add("Recvd po = " + pInvoiceD.getErpPoNum());
        }
        pInvoiceD.setErpPoNum(newPOnum); //in case it was modified to get at the right result...

        //2
        String erpPoRefNum = pPurchaseOrder.getErpPoRefNum();
        pInvoiceD.setErpPoRefNum(erpPoRefNum);

        //3
        if(dist != null && dist.getBusEntity() != null){
                    pInvoiceD.setBusEntityId(dist.getBusEntity().getBusEntityId());
         }

        //4
        pInvoiceD.setOrderId(pOrder.getOrderId());
        pInvoiceD.setStoreId(pOrder.getStoreId());        
        pInvoiceD.setAccountId(pOrder.getAccountId());
        pInvoiceD.setSiteId(pOrder.getSiteId());

    }

    private DistributorData getDistributorData(Distributor distEjb, int pBusEntityId, String pErpNum)
            throws Exception {

        DistributorData d = null;
        if (pBusEntityId != 0) {


            d = distEjb.getDistributor(pBusEntityId);

        } else if (Utility.isSet(pErpNum)) {

            d = distEjb.getDistributorByErpNum(pErpNum);

        }
        return d;
    }

    private InvoiceRequestData reSettingStoreId(InvoiceRequestData invoiceRequest, IdVector storeIds, ArrayList notesToLog) {
        InvoiceDistData  invoiceD = invoiceRequest.getInvoiceD();
        int defaultStoreId;
        if (storeIds != null && storeIds.size() == 1) {
            defaultStoreId = ((Integer) storeIds.get(0)).intValue();
            if (invoiceD.getStoreId() == 0) {
                invoiceD.setStoreId(defaultStoreId);
            }
        }

        Integer StoreIdInt = new Integer(invoiceD.getStoreId());

        if (invoiceD.getStoreId() != 0 && !storeIds.isEmpty() && !storeIds.contains(StoreIdInt)) {
            invoiceD.setStoreId(0);

        } else if (invoiceD.getStoreId() != 0 && (storeIds.isEmpty() || !storeIds.contains(StoreIdInt))) {
            String note = "The reference from trading partner to store is not found. Check please the order, " +
                    "probably the order is configured not correct." +
                    "store_id of the order: " + invoiceD.getStoreId();
            notesToLog.add(note);
        }
        if(invoiceD.getStoreId()==0) {
            //try order if found
            OrderData orderD = this.getOrder();
            if(orderD!=null && orderD.getStoreId()>0) {
                invoiceD.setStoreId(orderD.getStoreId());
                invoiceD.setErpSystemCd(orderD.getErpSystemCd());
            }

        }
        return invoiceRequest;
    }


    public OrderItemDataVector getOriginalOrderRecords(Order orderEjb, PurchaseOrderData thePo, InvoiceDistData invoiceD) throws Exception {


        OrderItemDataVector poItems;
        if (thePo == null) {
            return null;
        } else {

            //aka adding a # in front of it. or system is using customer po number
            // get the order items related to this invoice
            poItems = orderEjb.getOrderItemsByPoIdOrderId(thePo.getOrderId(), thePo.getPurchaseOrderId());
        }
        return poItems;
    }

   public static void addListValueToMap(Map map,Object key, Object value){
        List l = (List) map.get(key);
        if(l == null){
            l = new ArrayList();
            map.put(key,l);
        }
        l.add(value);
    }


    private TradingPartnerInfo getTradingPartnerInfo(TradingPartner tradingEjb, String distErp) throws Exception {

        int partnerId = tradingEjb.getPartnerId(distErp, 0,
                                                RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR,
                                                false, false, RefCodeNames.EDI_TYPE_CD.T810);
        if (partnerId != 0) {
            return tradingEjb.getTradingPartnerInfo(partnerId);
        }
        return null;
    }


    public int getTradingPartnerId() {
        return mTradingPartnerId;
    }

    public void setTradingPartnerId(int pTradingPartnerId) {
        this.mTradingPartnerId = pTradingPartnerId;
    }

    public InvoiceRequestData getInvoiceRequest() {
        return mInvoiceRequest;
    }

    public void setInvoiceRequest(InvoiceRequestData pInvoiceRequest) {
        this.mInvoiceRequest = pInvoiceRequest;
    }

    public OrderItemDataVector getPOItems() {
        return mPOItems;
    }

    public void setPOItems(OrderItemDataVector pPOItems) {
        this.mPOItems = pPOItems;
    }

    public ArrayList getNotesToLog() {
        return mNotesToLog;
    }

    public void setNotesToLog(ArrayList pNotesToLog) {
        this.mNotesToLog = pNotesToLog;
    }

    public PurchaseOrderData getPurchaseOrder() {
        return mPurchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderData pPurchaseOrder) {
        this.mPurchaseOrder = pPurchaseOrder;
    }

    public DistributorData getDistributor() {
        return mDistributor;
    }

    public void setDistributor(DistributorData pDistributor) {
        this.mDistributor = pDistributor;
    }

    public OrderData getOrder() {
        return mOrder;
    }

    public void setOrder(OrderData pOrder) {
        this.mOrder = pOrder;
    }

    public TradingPartnerInfo getTradingPartnerInfo() {
        return mTradingPartnerInfo;
    }

    public void setTradingPartnerInfo(TradingPartnerInfo pTradingPartnerInfo) {
        this.mTradingPartnerInfo = pTradingPartnerInfo;
    }

    public HashMap getParamMap() {
        return mParamMap;
    }

    public void setParamMap(HashMap pParamMap) {
        this.mParamMap = pParamMap;
    }


    public HashMap getMiscInvoiceNotes() {
        return mMiscInvoiceNotes;
    }

    public void setMiscInvoiceNotes(HashMap pMiscInvoiceNotes) {
        this.mMiscInvoiceNotes = pMiscInvoiceNotes;
    }

    public List getItemNotesToLog() {
        return mItemNotesToLog;
    }

    public int getCountInsertedInvoiceItems() {
        return mCountInsertedInvoiceItems;
    }

    public void setCountInsertedInvoiceItems(int pInsertedInvoiceItems) {
        this.mCountInsertedInvoiceItems = pInsertedInvoiceItems;
    }

    public void clearCountInsertedInvoiceItems() {
         this.mCountInsertedInvoiceItems=0;
    }


    public InvoiceDistDataVector getInsertedInvoices() {
        return mInsertedInvoices;
    }

    public void setInsertedInvoices(InvoiceDistDataVector pInsertedInvoices) {
        this.mInsertedInvoices = pInsertedInvoices;
    }

    public void clearInsertedInvoices() {
           this.mInsertedInvoices.clear();
    }

    public BigDecimal getRecivedTotal() {
        return mRecivedTotal;
    }

    public void setRecivedTotal(BigDecimal pRecivedTotal) {
        this.mRecivedTotal = pRecivedTotal;
    }

    public InvoiceDistDetailDescDataVector getToInsertItems() {
        return mToInsertItems;
    }

    public void setToInsertItems(InvoiceDistDetailDescDataVector pToInsertItems) {
        this.mToInsertItems = pToInsertItems;
    }

    public HashMap getTradPartnerAssoc() {
        return mTradPartnerAssoc;
    }

    public void setTradPartnerAssoc(HashMap pTradPartnerAssoc) {
        this.mTradPartnerAssoc = pTradPartnerAssoc;
    }

    public void incExceptionCount() {
        mExceptionCount++;
    }

    public int getExceptionCount() {
        return mExceptionCount;
    }

    public void addExceptionCount(int ExceptionCount) {
         mExceptionCount+=ExceptionCount;
    }

    public Boolean getCurrInsInvDetailFlag() {
        return mCurrInsInvDetailFlag;
    }

    public void setCurrInsInvDetailFlag(Boolean pCurrInsInvDetailFlag) {
        this.mCurrInsInvDetailFlag = pCurrInsInvDetailFlag;
    }


    public InvoiceDistDetailData getCurrInoiceDetailData() {
        return mCurrInoiceDetailData;
    }

    public void setCurrInoiceDetailData(InvoiceDistDetailData pCurrInoiceDetailData) {
        this.mCurrInoiceDetailData = pCurrInoiceDetailData;
    }

    public String getProcessName() {
        return mProcessName;
    }

    public void setProcessName(String processName) {
        this.mProcessName = processName;
    }
}

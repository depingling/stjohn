package com.cleanwise.service.apps.dataexchange;

import com.americancoders.edi.OBOEException;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import java.util.Iterator;

import org.apache.log4j.Logger;


public class Outbound850Clw implements OutboundEventTransaction {
	protected Logger log = Logger.getLogger(this.getClass());
    private static String className = "Outbound850Clw";

    private static final String EVENT = "event";

    private OutboundEDIRequestDataVector transactionsToProcess;
    private ProcessOutboundResultView processOutboundResultView;
    private StringBuffer transactionReport;
    private String distErpNum;
    private TradingPartnerDescView tradingPartnerDescView;


    public Outbound850Clw() {
        this.processOutboundResultView = ProcessOutboundResultView.createValue();
        this.transactionReport = new StringBuffer();
    }

    public void setOutboundTransactionsToProcess(OutboundEDIRequestDataVector transactionsToProcess) {
        this.transactionsToProcess = transactionsToProcess;
    }

    public OutboundEDIRequestDataVector getTransactionsToProcess() {
        return transactionsToProcess;
    }


    public TradingPartnerDescView getTradingPartnerDescView() {
        return tradingPartnerDescView;
    }

    public void build() throws Exception {
        log("build => begin");
        if (!instanceValid()) {
            throw new Exception("outbound event transaction data is not valid");
        }
        OrderRequestDataVector object = createOrderRequestCollection(getTransactionsToProcess());
        IntegrationRequestsVector intgObj = createIntegrationObject(getTransactionsToProcess());
        buildResultData(object, intgObj, getTradingPartnerDescView(), getDistErpNum());
        log("build => end");
    }


    // list object that need to be updated
    private IntegrationRequestsVector createIntegrationObject(OutboundEDIRequestDataVector transactionsToProcess) {
         IntegrationRequestsVector integrationRequests = new IntegrationRequestsVector();

        for (int i = 0; i < transactionsToProcess.size(); i++) {
            OutboundEDIRequestData outEdi = (OutboundEDIRequestData) transactionsToProcess.get(i);
            for (int j = 0; j < outEdi.getOrderItemDV().size(); j++) {
                OrderItemData itemD = (OrderItemData) outEdi.getOrderItemDV().get(j);
                itemD.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
                integrationRequests.add(itemD);
            }
            outEdi.getPurchaseOrderD().setPurchaseOrderStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
            integrationRequests.add(outEdi.getPurchaseOrderD());
        }
        return integrationRequests;
    }

    private void buildResultData(OrderRequestDataVector result,
                                 IntegrationRequestsVector intObj,
                                 TradingPartnerDescView tradingPartnerDescView,
                                 String distrErpNum) {

        processOutboundResultView.setProcessResult(result);
        processOutboundResultView.setIntegrationRequests(intObj);
        processOutboundResultView.setTradingPartnerDescView(tradingPartnerDescView);
        processOutboundResultView.setDistErpNum(distrErpNum);
        processOutboundResultView.setOutboundEDIRequests(getTransactionsToProcess());

    }

    private StoreData getStore(int storeId) throws Exception {
        Store storeEjb = APIAccess.getAPIAccess().getStoreAPI();
        return storeEjb.getStore(storeId);
    }

    private DistributorData getDitributor() throws Exception {
        Distributor distEjb = APIAccess.getAPIAccess().getDistributorAPI();
        return distEjb.getDistributorByErpNum(getDistErpNum());
    }
/*
    public TradingPartnerDescView getTradingPartnerForReference() throws Exception {
        TradingPartner partner = APIAccess.getAPIAccess().getTradingPartnerAPI();
		String partnerKey = getClwPartnerKey();
        TradingPartnerDescView tpDescVw = partner.getTradingPartnerInfoByPattern(partnerKey, EVENT);
        if(tpDescVw==null) {
            String errorMess = "Trading partner not found for partner key: "+partnerKey+" and pattern: "+EVENT;
            throw new Exception (errorMess);
        }
        TradingPartnerData tpD = tpDescVw.getTradingPartnerData();
        if(RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER.equals(tpD.getTradingPartnerTypeCd())) {
            String errorMess = "Trading partner not found for partner key: "+partnerKey+" and pattern: "+EVENT +
                    " is not a customer";
            throw new Exception (errorMess);
        }
        return tpDescVw;
    }
*/

    private String getCustomerPoNum(OrderPropertyDataVector orderPropertyDV) throws Exception {
        return getPropertyValue(orderPropertyDV, RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_PO_NUM);
    }

    private String getCustomerOrderDate(OrderPropertyDataVector orderPropertyDV) throws Exception {
        return getPropertyValue(orderPropertyDV, RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_ORDER_DATE);
    }

    private String getPropertyValue(OrderPropertyDataVector orderPropertyDV, String propertyTypeCd) throws Exception {
        String propertyVal = "";
        Iterator it = orderPropertyDV.iterator();
        while (it.hasNext()) {
            OrderPropertyData property = (OrderPropertyData) it.next();
            if (propertyTypeCd.equals(property.getOrderPropertyTypeCd())) {
                if (!Utility.isSet(propertyVal)) {
                    propertyVal = property.getValue();
                } else {
                    throw new Exception("Multiple order property.ORDER_PROPERTY_TYPE_CD => " + property.getOrderPropertyTypeCd());
                }

            }
        }
        return propertyVal;
    }

    private OrderRequestDataVector createOrderRequestCollection(OutboundEDIRequestDataVector transactionsToProcess)
    throws Exception {

        //TradingPartnerDescView refPartner = getTradingPartnerForReference();
        TradingPartner partner = APIAccess.getAPIAccess().getTradingPartnerAPI();
		String partnerKey = getClwPartnerKey();
        TradingPartnerDescView tpDescVw = partner.getTradingPartnerInfoByPattern(partnerKey, EVENT);
        if(tpDescVw==null) {
            String errorMess = "Trading partner not found for partner key: "+partnerKey+" and pattern: "+EVENT;
            throw new Exception (errorMess);
        }
        TradingPartnerData tpD = tpDescVw.getTradingPartnerData();
        if(!RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER.equals(tpD.getTradingPartnerTypeCd())) {
            String errorMess = "Trading partner found for partner key: "+partnerKey+" and pattern: "+EVENT +
                    " is not a customer";
            throw new Exception (errorMess);
        }

        //Get ref store
        int tpId = tpD.getTradingPartnerId();
        TradingPartnerAssocDataVector tpAssocDV =
               partner.getTradingPartnerAssocDataVectorForPartnerForTrading(tpId);
        IdVector accountIdV = new IdVector();
        for(Iterator iter = tpAssocDV.iterator(); iter.hasNext();) {
            TradingPartnerAssocData tpaD = (TradingPartnerAssocData) iter.next();
            if(RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT.equals(tpaD.getTradingPartnerAssocCd()))  {
               accountIdV.add(new Integer(tpaD.getBusEntityId()));
            }
        }
        if(accountIdV.isEmpty()) {
            String errorMess = "Trading partner is not associated with any account. " +
                    "Trading partner id: "+tpId;
            throw new Exception (errorMess);
        }
        Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();

        OrderRequestDataVector orderRequests = new OrderRequestDataVector();

        for (int i = 0; i < transactionsToProcess.size(); i++) {

            OutboundEDIRequestData outboundEdiReq = (OutboundEDIRequestData) transactionsToProcess.get(i);
            PropertyDataVector sitePropDV = outboundEdiReq.getSiteProperties();
            String siteRefNum = "";
            for(Iterator iter=sitePropDV.iterator(); iter.hasNext();) {
                PropertyData prD = (PropertyData) iter.next();
                if(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER.equals(prD.getShortDesc())) {
                    if(prD.getValue()!=null){
                    	siteRefNum = prD.getValue().trim();
                    }
                }
            }
            if(siteRefNum.length()==0) {
                String errorMess = "Site doesn't have budget reference num. Site id: " +
                    +outboundEdiReq.getOrderD().getSiteId();
                throw new Exception (errorMess);

            }
            int accountId = 0;
            int siteId = 0;
            int siteFoundQty = 0;
            for(Iterator iter = accountIdV.iterator(); iter.hasNext();) {
                int aId = ((Integer) iter.next()).intValue();
                IdVector sIdV =
                  siteEjb.getActiveSiteIdsByProperty(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER,siteRefNum,aId);
                if(sIdV.size()>0 && siteId==0) {
                    accountId = aId;
                    siteId = ((Integer) sIdV.get(0)).intValue();
                }
                siteFoundQty += sIdV.size();
            }
            if(siteFoundQty==0) {
                String errorMess = "Can't find site with budget reference number " +siteRefNum+
                        " in the accounts. Account ids = " + IdVector.toCommaString(accountIdV);
                throw new Exception (errorMess);
            }
            if(siteFoundQty>1) {
                String errorMess = "Found multiple sites with budget reference number " +siteRefNum+
                        " in the account. Account id = "+IdVector.toCommaString(accountIdV);
                throw new Exception (errorMess);
            }
            SiteData siteD = siteEjb.getSite(siteId);
            
            AddressData siteAddressD = siteD.getSiteAddress();
            OrderAddressData orderAddressD = new OrderAddressData();
            orderAddressD.setAddress1(outboundEdiReq.getOrderD().getOrderSiteName());
            orderAddressD.setAddress2(siteAddressD.getAddress1());
            orderAddressD.setAddress3(siteAddressD.getAddress2());
            orderAddressD.setAddress4(siteAddressD.getAddress3());
            orderAddressD.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING);
            orderAddressD.setCity(siteAddressD.getCity());
            orderAddressD.setPostalCode(siteAddressD.getPostalCode());
            orderAddressD.setStateProvinceCd(siteAddressD.getStateProvinceCd());
            orderAddressD.setCountryCd(siteAddressD.getCountryCd());
            orderAddressD.setCountyCd(siteAddressD.getCountyCd());
            

            OrderRequestData orderReq = OrderRequestData.createValue();
            orderReq.setOrderContactName(outboundEdiReq.getOrderD().getOrderContactName());
            orderReq.setOrderShipAddress(orderAddressD);
            orderReq.setSiteId(siteId);
            orderReq.setAccountId(accountId);
            orderReq.setSiteName(outboundEdiReq.getOrderD().getOrderSiteName());
            orderReq.setTradingPartnerId(tpD.getTradingPartnerId());
            orderReq.setIncomingProfileId(tpDescVw.getTradingProfileData().getTradingProfileId());
            orderReq.setCustomerPoNumber(outboundEdiReq.getPurchaseOrderD().getOutboundPoNum());
            orderReq.setCustomerOrderDate(getCustomerOrderDate(outboundEdiReq.getOrderPropertyDV()));
            orderReq.setOrderTelephoneNumber(outboundEdiReq.getOrderD().getOrderContactPhoneNum());
            orderReq.setCustomerComments(getCustomerComments(outboundEdiReq));
            orderReq.setOrderEmail(outboundEdiReq.getOrderD().getOrderContactEmail());
            orderReq.setRefOrderId(outboundEdiReq.getOrderD().getOrderId());
            orderReq.addOrderNote("from order id: "+outboundEdiReq.getOrderD().getOrderId());
            orderReq.setSkuTypeCd(RefCodeNames.SKU_TYPE_CD.CLW);                            // ???
            /*
            int distId = outboundEdiReq.getDistributorId();
            if (distId > 0) {                                                         // no distributor found
                orderReq.setOrderStatusCdOveride(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
            }
            */

            OrderItemDataVector items = outboundEdiReq.getOrderItemDV();
            for (Iterator it = items.iterator(); it.hasNext();) {
                OrderItemData item = (OrderItemData) it.next();
                orderReq.addItemEntry(item.getErpPoLineNum(), item.getDistItemSkuNum(),
                        item.getDistItemQuantity(), item.getDistItemCost().doubleValue(),
                        item.getDistItemUom(), item.getDistItemShortDesc(), item.getDistItemPack());
            }
            orderRequests.add(orderReq);
        }
        return orderRequests;
    }

    private boolean instanceValid() {
        return transactionsToProcess != null
                && processOutboundResultView != null
                && transactionReport != null
                && tradingPartnerDescView != null
                && distErpNum != null;
    }

    public ProcessOutboundResultView getProcessOutboundResultData() {
        return this.processOutboundResultView;
    }

    public StringBuffer getTranslationReport() {
        return this.transactionReport;
    }

    public void setTradingPartnerDescView(TradingPartnerDescView partnerDescView) {
        this.tradingPartnerDescView = partnerDescView;
    }

    public String getDistErpNum() {
        return distErpNum;
    }

    public void setDistErpNum(String distErpNum) {
        this.distErpNum = distErpNum;
    }

    /**
     * Logging
     *
     * @param message - message which will be pasted to log
     */
    protected void log(String message) {
        log.info(message);
    }


    public String getCustomerComments(OutboundEDIRequestData outboundReq) throws OBOEException {

        String msg = outboundReq.getOrderD().getComments();
        String mReqshipdate = OrderStatusDescData.getRequestedShipDate(outboundReq.getOrderMetaDV());

        if (!RefCodeNames.STORE_TYPE_CD.MLA.equals(outboundReq.getStoreType()) && Utility.isSet(mReqshipdate)) {
            if (!Utility.isSetForDisplay(msg)) {
            }

            msg = "Deliver before: " + mReqshipdate;
        }

        PropertyData pd = Utility.getProperty(outboundReq.getSiteProperties(), RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);

        String siteComments = null;
        if (pd != null) {
            siteComments = pd.getValue();
        }

        if (!Utility.isSet(msg)) {
            msg = siteComments;
        } else {
            if (Utility.isSet(siteComments)) {
                msg = msg + ". " + siteComments;
            }
        }

        return msg;
    }

    public String getClwPartnerKey() {
        Iterator it = getTradingPartnerDescView().getTradingPropertyMapDataVector().iterator();
        while (it.hasNext()) {
            TradingPropertyMapData data = (TradingPropertyMapData) it.next();
            if (RefCodeNames.ENTITY_PROPERTY_TYPE.CLW_TRADING_PARTNER_KEY.equals(data.getPropertyTypeCd())) {
                return data.getHardValue();
            }
        }
        return null;
    }
}

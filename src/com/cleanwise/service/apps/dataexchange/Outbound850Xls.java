package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.operations.Outbound850XLSBuilder;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import java.io.*;

import org.apache.log4j.Logger;



public class Outbound850Xls implements OutboundEventTransaction {
	protected Logger log = Logger.getLogger(this.getClass());
    private static String className="Outbound850Xls";

    private OutboundEDIRequestDataVector transactionsToProcess;
    private ProcessOutboundResultView processOutboundResultView;
    private StringBuffer transactionReport;
    private String distErpNum;
    private TradingPartnerDescView tradingPartnerDescView;



    public Outbound850Xls() {
        this.processOutboundResultView =ProcessOutboundResultView.createValue();
        this.transactionReport=new StringBuffer();
    }

    public void setOutboundTransactionsToProcess(OutboundEDIRequestDataVector transactionsToProcess) {
        this.transactionsToProcess=transactionsToProcess;
    }

    public OutboundEDIRequestDataVector getTransactionsToProcess() {
        return transactionsToProcess;
    }


    public TradingPartnerDescView getTradingPartnerDescView() {
        return tradingPartnerDescView;
    }

    public void build() throws Exception {
        log("build => begin");
        if(!instanceValid()){
            throw new Exception("outbound event transaction data is not valid");
        }
        DistributorData distributor = getDitributor();
        StoreData store = getStore(distributor.getStoreId());
        String logo = getLogoImg(store.getStoreId());
        File file = constructOutboundPurchaseOrder(distributor, getTransactionsToProcess(), store,logo);
        IntegrationRequestsVector intgObj = createIntegrationObject(getTransactionsToProcess());
        buildResultData( file,intgObj,getTradingPartnerDescView(),getDistErpNum());
        log("build => end");
    }

    // list object that need to be updated
    private IntegrationRequestsVector createIntegrationObject(OutboundEDIRequestDataVector transactionsToProcess) {
        log("createIntegrationObject => begin");
        IntegrationRequestsVector integrationRequests = new IntegrationRequestsVector();

        for (int i = 0; i < transactionsToProcess.size(); i++) {
            OutboundEDIRequestData outEdi = (OutboundEDIRequestData) transactionsToProcess.get(i);
            for (int j = 0; j < outEdi.getOrderItemDV().size(); j++){
            	OrderItemData itemD = (OrderItemData)outEdi.getOrderItemDV().get(j);
            	itemD.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
            	integrationRequests.add(itemD);            	
            }
            outEdi.getPurchaseOrderD().setPurchaseOrderStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
            integrationRequests.add(outEdi.getPurchaseOrderD());
        }
        log("createIntegrationObject => end");
        return integrationRequests;
    }

    private void buildResultData(File file,
                                 IntegrationRequestsVector intObj,
                                 TradingPartnerDescView tradingPartnerDescView,
                                 String distrErpNum) {

        processOutboundResultView.setProcessResult(file);
        processOutboundResultView.setIntegrationRequests(intObj);
        processOutboundResultView.setTradingPartnerDescView(tradingPartnerDescView);
        processOutboundResultView.setDistErpNum(distrErpNum);
        processOutboundResultView.setOutboundEDIRequests(getTransactionsToProcess());

    }

    private StoreData getStore(int storeId) throws Exception  {
        Store storeEjb = APIAccess.getAPIAccess().getStoreAPI();
        return storeEjb.getStore(storeId);
    }

    private DistributorData getDitributor() throws Exception{
        Distributor distEjb = APIAccess.getAPIAccess().getDistributorAPI();
        return distEjb.getDistributorByErpNum(getDistErpNum());
    }

    private String getLogoImg(int storeId) throws Exception{

        PropertyService propEjb = APIAccess.getAPIAccess().getPropertyServiceAPI();
        UIConfigData uiconfig = propEjb.fetchUIConfigData(storeId);
        return uiconfig.getLogo1();

    }

    public File constructOutboundPurchaseOrder(DistributorData pDist,
                                               OutboundEDIRequestDataVector p850s,
                                               StoreData pStore, String logo) throws Exception {
        log("constructOutboundPurchaseOrder => begin");
        File tmp;
        BufferedOutputStream out;
        tmp = File.createTempFile("Attachment", ".xls");
        out = new BufferedOutputStream(new FileOutputStream(tmp));
        Outbound850XLSBuilder xls = new Outbound850XLSBuilder();
        xls.constructXlsPO(pDist,p850s,pStore,out);
        log("constructOutboundPurchaseOrder => end");

        return tmp;

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
        this.tradingPartnerDescView=partnerDescView;
    }

    public String getDistErpNum() {
        return distErpNum;
    }

    public void setDistErpNum(String distErpNum) {
        this.distErpNum = distErpNum;
    }

    /**
     * Logging
     * @param message - message which will be pasted to log
     */
    protected void log(String message){
        log.info(message);
    }
}

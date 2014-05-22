package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.operations.Outbound850PDFBuilder;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.ClwApiCustomizer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;



public class Outbound850Pdf implements OutboundEventTransaction {
	protected Logger log = Logger.getLogger(this.getClass());
    private OutboundEDIRequestDataVector transactionsToProcess;
    private ProcessOutboundResultView processOutboundResultView;
    private StringBuffer transactionReport;
    private String distErpNum;
    private TradingPartnerDescView tradingPartnerDescView;


    public Outbound850Pdf() {

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

        if(!instanceValid()){
            throw new Exception("outbound event transaction data is not valid");
        }

        DistributorData distributor = getDitributor();
        StoreData store = getStore(distributor.getStoreId());
        String logo = getLogoImg(store.getStoreId());
        File file = constructOutboundPurchaseOrder(distributor, getTransactionsToProcess(), store,logo);
        IntegrationRequestsVector intgObj = createIntegrationObject(getTransactionsToProcess());
        buildResultData( file,intgObj,getTradingPartnerDescView(),getDistErpNum());
    }

    private IntegrationRequestsVector createIntegrationObject(OutboundEDIRequestDataVector transactionsToProcess) {

        IntegrationRequestsVector integrationRequests = new IntegrationRequestsVector();

        for (int i = 0; i < transactionsToProcess.size(); i++) {
            OutboundEDIRequestData outEdi = (OutboundEDIRequestData) transactionsToProcess.get(i);
            if (!outEdi.getOrderD().getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED)) {
                outEdi.getOrderD().setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
                integrationRequests.add(outEdi.getOrderD());                
            }
            
            for (int j = 0, len = outEdi.getOrderItemDV().size(); j < len; j++) {
                OrderItemData currItem = (OrderItemData) outEdi.getOrderItemDV().get(j);
                //set state to processed
                currItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
                integrationRequests.add(currItem); // for update the status
            }
            
            outEdi.getPurchaseOrderD().setPurchaseOrderStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
            integrationRequests.add(outEdi.getPurchaseOrderD());
        }
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

        File tmp;
        String lImageName = ClwApiCustomizer.getCustomizeImgElement(logo);
        BufferedOutputStream out;
        tmp = File.createTempFile("Attachment", ".pdf");
        out = new BufferedOutputStream(new FileOutputStream(tmp));
        Outbound850PDFBuilder pdf = new Outbound850PDFBuilder();
        pdf.constructPdfPO(pDist,p850s,pStore,out,lImageName);
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

}

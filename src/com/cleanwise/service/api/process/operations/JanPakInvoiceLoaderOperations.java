package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.variables.InboundContent;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.JanPakInvoiceLoader;
import com.cleanwise.service.api.value.IdVector;
import org.apache.log4j.Logger;



public class JanPakInvoiceLoaderOperations {

    private static final Logger log = Logger.getLogger(JanPakInvoiceLoaderOperations.class);

    public Integer getDistributorId(Integer pStoreId) throws Exception {

        Distributor distrEjb = APIAccess.getAPIAccess().getDistributorAPI();

        IdVector distrIds = distrEjb.getDistributorIdsForStore(pStoreId);
        if (distrIds.size() == 0) {
            throw new Exception("Distributor for store not found.Store Id => " + pStoreId);
        }

        if (distrIds.size() > 1) {
            throw new Exception("Multiple Distributor for store.Store Id => " + pStoreId);
        }

        return (Integer) distrIds.get(0);
    }

    public void prepare(Integer pStoreId, Integer pDistributorId, String pTable, Object pDataContent, Boolean pReload) throws Exception {
        JanPakInvoiceLoader loader = APIAccess.getAPIAccess().getJanPakInvoiceLoaderAPI();
        if (pReload != null && pReload) {
            if (pDataContent instanceof InboundContent) {
                pDataContent = ((InboundContent) pDataContent).getDecContent();
            }
            loader.accept((byte[]) pDataContent, pTable, null, null, "JAN_PACK_INVOICE_RELOADER");
        }
        loader.prepare(pStoreId, pDistributorId, pTable);
    }

    public void match(Integer pStoreId, Integer pDistributorId, String pTable) throws Exception {

        JanPakInvoiceLoader loader = APIAccess.getAPIAccess().getJanPakInvoiceLoaderAPI();
        loader.match(pStoreId, pDistributorId, pTable);

    }


    public void insert(Integer pStoreId,
                       Integer pDistributorId,
                       String pTable,
                       String pUser) throws Exception {

        JanPakInvoiceLoader loader = APIAccess.getAPIAccess().getJanPakInvoiceLoaderAPI();
        loader.insert(pStoreId, pDistributorId, pTable, pUser);

    }

    public void dropWorkTables(String pTable) throws Exception {

        JanPakInvoiceLoader loader = APIAccess.getAPIAccess().getJanPakInvoiceLoaderAPI();
        loader.dropWorkTables(pTable);

    }

    public void report(String pTable) throws Exception {
        JanPakInvoiceLoader loader = APIAccess.getAPIAccess().getJanPakInvoiceLoaderAPI();
        StringBuffer report = loader.report(pTable);
        log.info(report.toString());
    }

}

package com.cleanwise.service.api.process.operations;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.session.JanPakItemLoader;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.variables.InboundContent;
import com.cleanwise.service.api.value.IdVector;


public class JanPakItemLoaderOperations {
    private static final Logger log = Logger.getLogger(JanPakItemLoaderOperations.class);

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

    public Integer getStoreCatalogId(Integer pStoreId) throws Exception {

        CatalogInformation catInfEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
        return catInfEjb.getStoreCatalogId(pStoreId);

    }

    public void prepare(String pTable, String pUser, Object pDataContent, Boolean pReload, String pFilterTableName) throws Exception {
        JanPakItemLoader loader = APIAccess.getAPIAccess().getJanPakItemLoaderAPI();
        if (pReload != null && pReload) {
            if (pDataContent instanceof InboundContent) {
                pDataContent = ((InboundContent) pDataContent).getDecContent();
            }
            loader.accept((byte[]) pDataContent, pTable, null, null, "JAN_PACK_ITEM_RELOADER");
        }
        loader.prepare(pTable, pFilterTableName, pUser);
    }

    public void match(Integer pStoreId,
                      Integer pDistributorId,
                      Integer pStoreCatalogId,
                      String pTable,
                      String pUser) throws Exception {

        JanPakItemLoader loader = APIAccess.getAPIAccess().getJanPakItemLoaderAPI();
        loader.match(pStoreId, pDistributorId, pStoreCatalogId, pTable, pUser);

    }

    public void update(Integer pDistributorId,
                       Integer pStoreCatalogId,
                       String pTable,
                       String pUser) throws Exception {

        JanPakItemLoader loader = APIAccess.getAPIAccess().getJanPakItemLoaderAPI();
        loader.update(pDistributorId, pStoreCatalogId, pTable, pUser);

    }


    public void insert(Integer pStoreId,
                       Integer pDistributorId,
                       Integer pStoreCatalogId,
                       String pTable,
                       String pUser) throws Exception {

        JanPakItemLoader loader = APIAccess.getAPIAccess().getJanPakItemLoaderAPI();
        loader.insert(pStoreId, pDistributorId, pStoreCatalogId, pTable, pUser);

    }

    public void delete(Integer pStoreCatalogId, String pTable, String pUser) throws Exception {

        JanPakItemLoader loader = APIAccess.getAPIAccess().getJanPakItemLoaderAPI();
        loader.delete(pStoreCatalogId, pTable, pUser);

    }

    public void dropWorkTables(String pTable) throws Exception {

        JanPakItemLoader loader = APIAccess.getAPIAccess().getJanPakItemLoaderAPI();
        loader.dropWorkTables(pTable);
        
    }

    /* public void report(String pTable) throws Exception {
        JanPakItemLoader loader = APIAccess.getAPIAccess().getJanPakItemLoaderAPI();
        StringBuffer report = loader.report(pTable);
        log.info(report.toString());
    }*/
}

package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.variables.InboundContent;
import com.cleanwise.service.api.session.JanPakSiteLoader;
import org.apache.log4j.Logger;


public class JanPakSiteLoaderOperations {

    private static final Logger log = Logger.getLogger(JanPakSiteLoaderOperations.class);

    public void prepare(String pTable, String pUser, Object pDataContent, Boolean pReload) throws Exception {
        JanPakSiteLoader loader = APIAccess.getAPIAccess().getJanPakSiteLoaderAPI();
        if (pReload != null && pReload) {
            if (pDataContent instanceof InboundContent) {
                pDataContent = ((InboundContent) pDataContent).getDecContent();
            }
            loader.accept((byte[]) pDataContent, pTable, null, null, "JAN_PACK_SITE_RELOADER");
        }
        loader.prepare(pTable, pUser);
    }

    public void match(Integer pStoreId, String pTable, String pUser) throws Exception {
        JanPakSiteLoader loader = APIAccess.getAPIAccess().getJanPakSiteLoaderAPI();
        loader.match(pStoreId, pTable, pUser);
    }

    public void update(String pTable, String pUser) throws Exception {
        JanPakSiteLoader loader = APIAccess.getAPIAccess().getJanPakSiteLoaderAPI();
        loader.update(pTable, pUser);
    }

    public void insert(Integer pStoreId, String pTable, String pUser) throws Exception {
        JanPakSiteLoader loader = APIAccess.getAPIAccess().getJanPakSiteLoaderAPI();
        loader.insert(pStoreId, pTable, pUser);
    }

    public void delete(Integer pStoreId, String pTable, String pUser) throws Exception {
        JanPakSiteLoader loader = APIAccess.getAPIAccess().getJanPakSiteLoaderAPI();
        loader.delete(pStoreId, pTable, pUser);
    }

    public void report(String pTable) throws Exception {
        JanPakSiteLoader loader = APIAccess.getAPIAccess().getJanPakSiteLoaderAPI();
        StringBuffer report = loader.report(pTable);
        log.info(report.toString());
    }
}

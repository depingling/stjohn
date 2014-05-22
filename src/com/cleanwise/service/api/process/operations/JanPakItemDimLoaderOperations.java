package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.DataWarehouse;
import org.apache.log4j.Logger;




public class JanPakItemDimLoaderOperations {
    private static final Logger log = Logger.getLogger(JanPakItemDimLoaderOperations.class);

    public void processItemDimLoadRequest (String storeSchemaName, Integer store1Id, Integer store2Id, String dwSchemaName) throws Exception {
        
        DataWarehouse dwEjb = APIAccess.getAPIAccess().getDataWarehouseAPI();
        dwEjb.fillDwItemDimension(storeSchemaName, store1Id, store2Id, dwSchemaName);
        
        return;
    }
}

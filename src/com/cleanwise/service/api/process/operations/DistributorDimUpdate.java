package com.cleanwise.service.api.process.operations;

import java.util.List;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.DataWarehouse;

public class DistributorDimUpdate {
    public void updateDimDist(String dbSchemaGeneral, String dbSchemaDim,
            List<Integer> storeIds, Integer jdStoreId) throws Exception {
        DataWarehouse dataWarehouse = APIAccess.getAPIAccess()
                .getDataWarehouseAPI();
        dataWarehouse.updateDimDist(dbSchemaGeneral, dbSchemaDim, storeIds,
                jdStoreId);
    }
}

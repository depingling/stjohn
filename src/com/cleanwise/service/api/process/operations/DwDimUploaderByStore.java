package com.cleanwise.service.api.process.operations;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.DataWarehouse;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.ProcessUtil;
import com.cleanwise.service.api.util.MultipleDataException;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.util.Utility;
import java.rmi.RemoteException;
/**
 * This class fill the table DW_ITEM_DISTRIBUTOR in the 'Data Warehouse'.
 * Use the parameter 'dbTablespace' to define the schema for 'CLW'-tables with data.
 * Use the parameter 'dwTablespace' to define the schema for the tables from 'Data Warehouse'.
 */
public class DwDimUploaderByStore {

     public static final String DEFAULT_USER_NAME_TO_UPDATE = "EventBean";

     private static final Logger log = Logger.getLogger(DwDimUploaderByStore.class);

     public void loadAccounts(Integer storeId, Integer linkedStoreId, String dbTablespace, String dwTablespace, String dbLink, Integer pProcessActiveId) throws Exception {
        load(storeId.intValue(), linkedStoreId.intValue(), DataWarehouse.TBL_DW_ACCOUNT_DIM, dbTablespace, dwTablespace, dbLink, pProcessActiveId.intValue());
     }

     public void loadSites(Integer storeId,  String dbTablespace, String dwTablespace, String dbLink, Integer pProcessActiveId) throws Exception {
         load(storeId.intValue(), 0, DataWarehouse.TBL_DW_SITE_DIM, dbTablespace, dwTablespace, dbLink, pProcessActiveId.intValue());
     }

     public void loadCategories(Integer storeId, Integer linkedStoreId, String dbTablespace, String dwTablespace, String dbLink, Integer pProcessActiveId) throws Exception {
        load(storeId.intValue(), linkedStoreId.intValue(), DataWarehouse.TBL_DW_CATEGORY_DIM, dbTablespace, dwTablespace, dbLink, pProcessActiveId.intValue());
     }

     public void loadItems(Integer storeId, Integer linkedStoreId, String dbTablespace, String dwTablespace, String dbLink, Integer pProcessActiveId) throws Exception {
         load(storeId.intValue(), linkedStoreId.intValue(), DataWarehouse.TBL_DW_ITEM_DIM, dbTablespace, dwTablespace, dbLink, pProcessActiveId.intValue());
     }

     public void loadManuf(Integer storeId, Integer linkedStoreId, String dbTablespace, String dwTablespace, String dbLink, Integer pProcessActiveId) throws Exception {
         load(storeId.intValue(), linkedStoreId.intValue(), DataWarehouse.TBL_DW_MANUFACTURER_DIM, dbTablespace, dwTablespace, dbLink, pProcessActiveId.intValue());
     }

     public void loadDistr(Integer storeId, Integer linkedStoreId, String dbTablespace, String dwTablespace, String dbLink, Integer pProcessActiveId) throws Exception {
         load(storeId.intValue(), linkedStoreId.intValue(), DataWarehouse.TBL_DW_DISTRIBUTOR_DIM, dbTablespace, dwTablespace, dbLink, pProcessActiveId.intValue());
     }

     private void load(int storeId, int linkedStoreId, String dimName, String dbTablespace, String dwTablespace, String dbLink, int pProcessId) throws Exception {

          try {
            ProcessUtil.verifySimilarProcessesRunning(pProcessId);
          }
          catch (Exception ex) {
            log.info(ex.getMessage());
            throw ex;
          }

          APIAccess factory = new APIAccess();
          DataWarehouse dataWarehouseBean = factory.getDataWarehouseAPI();

          StringBuffer report = null;
          try {
            report = dataWarehouseBean.fillDwAnyDimByStore(storeId,
                linkedStoreId, dimName, dbTablespace, dwTablespace, dbLink,
                pProcessId);
          }
          catch (Exception ex) {
            log.info(Utility.getUiErrorMess(ex.getMessage()));
            if (ex instanceof RemoteException ){
              throw new RemoteException(ex.getMessage());
            } else {
              throw new Exception(ex.getMessage());
            }
          }
          if (report != null) {
            log.info(report.toString());
          }
      }

}

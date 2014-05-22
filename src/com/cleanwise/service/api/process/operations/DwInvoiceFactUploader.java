package com.cleanwise.service.api.process.operations;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.DataWarehouse;
import com.cleanwise.service.api.util.ProcessUtil;
import com.cleanwise.service.api.util.Utility;
import java.rmi.RemoteException;

/**
 * This class fill the table DW_ITEM_DISTRIBUTOR in the 'Data Warehouse'.
 * Use the parameter 'dbTablespace' to define the schema for 'CLW'-tables with data.
 * Use the parameter 'dwTablespace' to define the schema for the tables from 'Data Warehouse'.
 */
public class DwInvoiceFactUploader {

    public static final String DEFAULT_USER_NAME_TO_UPDATE = "EventBean";

    private static final Logger log = Logger.getLogger(DwInvoiceFactUploader.class);

    public void load(Integer storeId, String dbTablespace, String dwTablespace, String dbLink, String isJanPakStore, Integer percentOfErrors, Integer pProcessActiveId) throws Exception {

        log.info(
            "[DwInvoiceFactUploader.loadTable] Start of the updating the table DW_INVOICE_FACT in the 'Data Warehouse'. \r\n" +
            "Parameters: \r\n" +
            "         dbLink = " + dbLink + ",\r\n" +
            "         storeId = " + storeId + ",\r\n" +
            "         isJanPakStore = " + isJanPakStore + ",\r\n" +
            "         percentOfErrors = " + percentOfErrors + ",\r\n" +
            "    dbTablespace = " + dbTablespace + ",\r\n" +
            "    dwTablespace = " + dwTablespace + ",\r\n" +
            "    pProcessActiveId = " + pProcessActiveId);

        try {
          ProcessUtil.verifySimilarProcessesRunning(pProcessActiveId.intValue());
        }
        catch (Exception ex) {
          log.error(ex.getMessage());
          throw new Exception(ex.getMessage());
        }
        APIAccess factory = new APIAccess();
        DataWarehouse dataWarehouseBean = factory.getDataWarehouseAPI();
//        String userName = DEFAULT_USER_NAME_TO_UPDATE;
        StringBuffer report = null;
        try {
          report = dataWarehouseBean.fillDwInvoiceFactTable(storeId.intValue(),
              dbTablespace, dwTablespace, dbLink, isJanPakStore,
              percentOfErrors, pProcessActiveId.intValue());
        }
        catch (Exception ex) {
          log.info(Utility.getUiErrorMess(ex.getMessage()));
          if (ex instanceof RemoteException ){
             throw new RemoteException(ex.getMessage());
           } else {
             throw new Exception(ex.getMessage());
           }
        }
        log.info(report.toString());

    }
}

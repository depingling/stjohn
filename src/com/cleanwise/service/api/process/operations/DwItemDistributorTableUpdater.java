package com.cleanwise.service.api.process.operations;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.DataWarehouse;

/**
 * This class fill the table DW_ITEM_DISTRIBUTOR in the 'Data Warehouse'.
 * Use the parameter 'dbTablespace' to define the schema for 'CLW'-tables with data.  
 * Use the parameter 'dwTablespace' to define the schema for the tables from 'Data Warehouse'.
 */
public class DwItemDistributorTableUpdater {

    public static final String DEFAULT_USER_NAME_TO_UPDATE = "EventBean";
    
    private static final Logger log = Logger.getLogger(DwCustomerDimUpdater.class);

    public void updateTable(String dbTablespace, String dwTablespace) throws Exception {

        log.info(
            "[DwItemDistributorTableUpdater.updateTable] Start of the updating the table DW_ITEM_DISTRIBUTOR in the 'Data Warehouse'. \r\n" + 
            "Parameters: \r\n" + 
            "    dbTablespace = " + dbTablespace + ",\r\n" + 
            "    dwTablespace = " + dwTablespace);

        APIAccess factory = new APIAccess();
        DataWarehouse dataWarehouseBean = factory.getDataWarehouseAPI();

        String userName = DEFAULT_USER_NAME_TO_UPDATE;
        dataWarehouseBean.fillDwItemDistributorTable(userName, dbTablespace, dwTablespace);
    }
}

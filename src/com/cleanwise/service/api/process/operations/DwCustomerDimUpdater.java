package com.cleanwise.service.api.process.operations;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.DataWarehouse;

/**
 * This class fill the table DW_CUSTOMER_DIM in the 'Data Warehouse'.
 * Use the parameter 'dbTablespace' to define the schema for 'CLW'-tables with data.  
 * Use the parameter 'dwTablespace' to define the schema for the tables from 'Data Warehouse'.
 * The parameter 'storeId' - is the identifier of store (for example 'JanPak') to explore.
 * The parameter 'jdStoreId' - is the identifier of 'JohnsonDiversey' store.
 */
public class DwCustomerDimUpdater {

	public static final String DEFAULT_USER_NAME_TO_UPDATE = "EventBean";
	
	private static final Logger log = Logger.getLogger(DwCustomerDimUpdater.class);

    public void updateDimension(String dbTablespace, String dwTablespace, 
        Integer storeId, Integer jdStoreId) throws Exception {

        log.info(
            "[DwCustomerDimUpdater.updateDimension] Start of the updating the table DW_CUSTOMER_DIM in the 'Data Warehouse'. \r\n" + 
            "Parameters: \r\n" + 
            "    dbTablespace = " + dbTablespace + ",\r\n" + 
            "    dwTablespace = " + dwTablespace + ",\r\n" +
            "         storeId = " + storeId + ",\r\n" +
            "       jdStoreId = " + jdStoreId);

        if (storeId == null || jdStoreId == null) {
            String errorMessage = "[DwCustomerDimUpdater.updateDimension] ";
            if (storeId == null) {
                errorMessage += "The parameter 'storeId' is not defined. ";
            }
            if (jdStoreId == null) {
                errorMessage += "The parameter 'jdStoreId' is not defined. ";
            }
            throw new Exception(errorMessage);
        }

        APIAccess factory = new APIAccess();
        DataWarehouse dataWarehouseBean = factory.getDataWarehouseAPI();

        String userName = DEFAULT_USER_NAME_TO_UPDATE;

        dataWarehouseBean.fillDwCustomerDimension(userName, dbTablespace, 
            dwTablespace, storeId.intValue(), jdStoreId.intValue());
    }
}

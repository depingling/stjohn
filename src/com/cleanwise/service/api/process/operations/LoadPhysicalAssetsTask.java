package com.cleanwise.service.api.process.operations;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.DataUploaderHelper;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.util.Iterator;
import java.util.Date;
import java.text.SimpleDateFormat;


public class LoadPhysicalAssetsTask {

	public static final String DEFAULT_USER_NAME_TO_REPORT = "TCS Physical Asset Loader";

	private static final Logger log = Logger.getLogger(LoadPhysicalAssetsTask.class);

    public void loadPhysicalAssets(String table, String storeId, 
	                               Integer pProcessActiveId, String database_schema, String ftpFileName) 
	throws Exception {

        log.info(
            "[LoadPhysicalAssetsTask.loadPhysicalAssets]  Load Physical Assets. \r\n" +
            "Parameter: \r\n" +
            "    table = " + table + "\r\n" +
			"    storeId = " + storeId + "\r\n" +
			"    pProcessActiveId = " + pProcessActiveId + "\r\n" +
			"    database_schema = " + database_schema + "\r\n" +
			"	 ftpFileName = " + ftpFileName);

		if(ftpFileName ==null || ftpFileName.trim().length()==0) {
			return; //No file found. Empty run.
		}
        if (table == null || table.trim().length()==0) {
            throw new Exception("[LoadPhysicalAssetsTask.loadPhysicalAssets] The parameter 'table' is not defined.");
        }
        if (pProcessActiveId <= 0) {
            throw new Exception("[LoadPhysicalAssetsTask.loadPhysicalAssets] Invalid parameter 'pProcessActiveId' = "+pProcessActiveId);
        }
		int storeIdInt = 0;
		try {
			storeIdInt = Integer.parseInt(storeId);
		} catch (Exception e) {}
	
        if (storeIdInt <= 0) {
            throw new Exception("[LoadPhysicalAssetsTask.loadPhysicalAssets] Invalid parameter 'storeId' = "+storeId);
        }

        APIAccess factory = new APIAccess();
        Store storeEjb = factory.getStoreAPI();
		String wrkTableName = table+pProcessActiveId;

        DataUploaderHelper dataUploaderHelper = factory.getDataUploaderHelperAPI();
        log.info("[LoadPhysicalAssetsTask.loadPhysicalAssets] Check the existence of table: " + wrkTableName);
        boolean isExistsTable = dataUploaderHelper.isExistsDatabaseTable(wrkTableName, database_schema);
        if (!isExistsTable) {
            log.info("[LoadPhysicalAssetsTask.loadPhysicalAssets] Table " + wrkTableName + " is not found");
               return;
        }
		
		storeEjb.loadPhysicalAsset(wrkTableName, storeIdInt, DEFAULT_USER_NAME_TO_REPORT);

    }

 
}

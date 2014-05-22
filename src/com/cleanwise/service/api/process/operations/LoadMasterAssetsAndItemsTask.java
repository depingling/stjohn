package com.cleanwise.service.api.process.operations;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.ICleanwiseUser;
import java.util.Iterator;
import java.util.Date;
import java.text.SimpleDateFormat;


public class LoadMasterAssetsAndItemsTask {

	public static final String DEFAULT_USER_NAME_TO_REPORT = "TCS Master Asset Loader";

	private static final Logger log = Logger.getLogger(LoadMasterAssetsAndItemsTask.class);

    public void loadMasterAssets(String table, String storeId, Integer pProcessActiveId, String ftpFileName) 
	throws Exception {

        log.info(
            "[LoadMasterAssetsTask.loadMasterAssets]  Load Master Assets. \r\n" +
            "Parameter: \r\n" +
            "    table = " + table + "\r\n" +
			"    storeId = " + storeId + "\r\n" +
			"    pProcessActiveId = " + pProcessActiveId + "\r\n" +
			"    ftpFileName = " + ftpFileName);
        if(ftpFileName ==null || ftpFileName.trim().length()==0) {
			return; //No file found. Empty run.
		}
        if (table == null || table.trim().length()==0) {
            throw new Exception("[LoadMasterAssetsTask.loadMasterAssets] The parameter 'table' is not defined.");
        }
        if (pProcessActiveId <= 0) {
            throw new Exception("[LoadMasterAssetsTask.loadMasterAssets] Invalid parameter 'pProcessActiveId' = "+pProcessActiveId);
        }
		int storeIdInt = 0;
		try {
			storeIdInt = Integer.parseInt(storeId);
		} catch (Exception e) {}
	
        if (storeIdInt <= 0) {
            throw new Exception("[LoadMasterAssetsTask.loadMasterAssets] Invalid parameter 'storeId' = "+storeId);
        }

        APIAccess factory = new APIAccess();
        Store storeEjb = factory.getStoreAPI();
		String wrkTableName = table+pProcessActiveId+"A";
		storeEjb.loadMasterAsset(wrkTableName, storeIdInt, DEFAULT_USER_NAME_TO_REPORT);
		StoreData storeD = storeEjb.getStore(storeIdInt);
        String isParentStore = Utility.getPropertyValue(storeD.getMiscProperties(),
                                RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE);
        if(Utility.isTrue(isParentStore, false)) {
			storeEjb.synchronizeMasterAsset(storeIdInt, 0, (ICleanwiseUser) null);
		}

    }	

 
    public void loadMasterItems(String table, String storeId, Integer pProcessActiveId, String ftpFileName) 
	throws Exception {

        log.info(
            "[LoadMasterAssetsTask.loadMasterItems]  Load Master Items. \r\n" +
            "Parameter: \r\n" +
            "    table = " + table + "\r\n" +
			"    storeId = " + storeId + "\r\n" +
			"    pProcessActiveId = " + pProcessActiveId + "\r\n" +
			"    ftpFileName = " + ftpFileName);
        if(ftpFileName ==null || ftpFileName.trim().length()==0) {
			return; //No file found. Empty run.
		}
        if (table == null || table.trim().length()==0) {
            throw new Exception("[LoadMasterAssetsTask.loadMasterAssets] The parameter 'table' is not defined.");
        }
        if (pProcessActiveId <= 0) {
            throw new Exception("[LoadMasterAssetsTask.loadMasterAssets] Invalid parameter 'pProcessActiveId' = "+pProcessActiveId);
        }
		int storeIdInt = 0;
		try {
			storeIdInt = Integer.parseInt(storeId);
		} catch (Exception e) {}
	
        if (storeIdInt <= 0) {
            throw new Exception("[LoadMasterAssetsTask.loadMasterAssets] Invalid parameter 'storeId' = "+storeId);
        }

        APIAccess factory = new APIAccess();
        Store storeEjb = factory.getStoreAPI();
		String wrkTableName = table+pProcessActiveId+"I";
		storeEjb.loadMasterItem (wrkTableName, storeIdInt, DEFAULT_USER_NAME_TO_REPORT);
		StoreData storeD = storeEjb.getStore(storeIdInt);
        String isParentStore = Utility.getPropertyValue(storeD.getMiscProperties(),
                                RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE);
        if(Utility.isTrue(isParentStore, false)) {
			storeEjb.synchronizeParentChildStoreItems(storeIdInt, 0, 0, (ICleanwiseUser) null);
		}
    }
	

    public void splitInputTable(String table, String storeId, Integer pProcessActiveId, String ftpFileName) throws Exception {

        log.info(
            "[LoadMasterAssetsTask.loadMasterAssets]  Load Master Assets. \r\n" +
            "Parameter: \r\n" +
            "    table = " + table + "\r\n" +
			"    storeId = " + storeId + "\r\n" +
			"    pProcessActiveId = " + pProcessActiveId + "\r\n" +
			"    ftpFileName = " + ftpFileName);
        if(ftpFileName ==null || ftpFileName.trim().length()==0) {
			return; //No file found. Empty run.
		}
        if (table == null || table.trim().length()==0) {
            throw new Exception("[LoadMasterAssetsTask.loadMasterAssets] The parameter 'table' is not defined.");
        }
        if (pProcessActiveId <= 0) {
            throw new Exception("[LoadMasterAssetsTask.loadMasterAssets] Invalid parameter 'pProcessActiveId' = "+pProcessActiveId);
        }
		int storeIdInt = 0;
		try {
			storeIdInt = Integer.parseInt(storeId);
		} catch (Exception e) {}
	
        if (storeIdInt <= 0) {
            throw new Exception("[LoadMasterAssetsTask.loadMasterAssets] Invalid parameter 'storeId' = "+storeId);
        }

        APIAccess factory = new APIAccess();
        Store storeEjb = factory.getStoreAPI();
		String wrkTableName = table+pProcessActiveId;
		storeEjb.splitAssetItemTable(wrkTableName);

    }
 
}

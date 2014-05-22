package com.cleanwise.service.api.util.synchronizer;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;

public class StoreItemSynchronizer {

	private int parentStoreId;
	private int childStoreId;
	private int itemId;
	private String userName;

	public StoreItemSynchronizer(int pParentStoreId, int pChildStoreId,
			int pItemId, String pUserName) {
		super();
		this.parentStoreId = pParentStoreId;
		this.childStoreId = pChildStoreId;
		this.itemId = pItemId;
		this.userName = pUserName;
	}

	public void synchronize(Connection connection) throws Exception {
        String sqlName = "/com/cleanwise/service/api/util/synchronizer/sql/SyncrhonizeItems.sql";
		String sql = gatherSQL(sqlName);
		HashMap params = new HashMap();
		params.put("#PARENT_STORE_ID#",String.valueOf(parentStoreId));
		params.put("#CHILD_STORE_ID#",(childStoreId>0)?String.valueOf(childStoreId):"null");
		params.put("#PARENT_ITEM_ID#",(itemId>0)?String.valueOf(itemId):"null");
		params.put("#USER#",userName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String timeSt = sdf.format(new Date());
		sql = ParametersSubstitutor.resolveParameters(sql, params, null, timeSt);

		SQLBatchExecutor.executeBatch(connection, sql);		
	}

	public void synchronizeCategories(Connection connection) throws Exception {
        String sqlName = "/com/cleanwise/service/api/util/synchronizer/sql/StoreCategorySynchronizer.sql";
		String sql = gatherSQL(sqlName);
        HashMap params = new HashMap();
		params.put("#PARENT_STORE_ID#",String.valueOf(parentStoreId));
		params.put("#CHILD_STORE_ID#",(childStoreId>0)?String.valueOf(childStoreId):"null");
		//params.put("#PARENT_ITEM_ID#",(itemId>0)?String.valueOf(itemId):"null");
		params.put("#USER#",userName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String timeSt = sdf.format(new Date());
		sql = ParametersSubstitutor.resolveParameters(sql, params, null, timeSt);

		SQLBatchExecutor.executeBatch(connection, sql);		
	}

	private String gatherSQL(String sqlName) throws IOException {

		ResourceLoaderUtil resourceLoader = new ResourceLoaderUtil();
		String sql = "";
		sql = sql + resourceLoader.loadText(sqlName);
		return sql;
	}

}

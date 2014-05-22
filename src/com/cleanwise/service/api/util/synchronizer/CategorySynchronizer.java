package com.cleanwise.service.api.util.synchronizer;

import java.io.IOException;
import java.sql.Connection;

public class CategorySynchronizer {
	int parentStoreId;
	int assetId;
	String userName;

	public CategorySynchronizer(int parentStoreId, int assetId, String userName) {
		super();
		this.parentStoreId = parentStoreId;
		this.assetId = assetId;
		this.userName = userName;
	}
	public void synchronize(Connection connection) throws Exception {

		String sql = gatherSQL();
		ParametersSubstitutor substitutor = new ParametersSubstitutor(
				parentStoreId, 0, assetId, userName);
		substitutor.addParameter("#ASSET_ID#", this.assetId);
		sql = substitutor.resolveParameters(sql);

		SQLBatchExecutor.executeBatch(connection, sql);		
	}

	private String gatherSQL() throws IOException {

		final String sql_CategorySynchronizer = "/com/cleanwise/service/api/util/synchronizer/sql/CategorySynchronizer.sql";

		ResourceLoaderUtil resourceLoader = new ResourceLoaderUtil();
		String sql = resourceLoader.loadText(sql_CategorySynchronizer);
		return sql;
	}
	

}

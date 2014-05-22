package com.cleanwise.service.api.util.synchronizer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ParentChildStoresSynchronizer {

	private int parentStoreId;
	private int childStoreId;
	private int masterAssetId;
	private String userName;

	public ParentChildStoresSynchronizer(int parentStoreId, int childStoreId,
			int masterAssetId, String userName) {
		super();
		this.parentStoreId = parentStoreId;
		this.childStoreId = childStoreId;
		this.masterAssetId = masterAssetId;
		this.userName = userName;
	}

	public void synchronize(Connection connection) throws Exception {

		SQLBatchExecutor.executeBatch(connection, getSqlPackage(connection));		
	}

	public String getSqlPackage(Connection connection) throws SQLException,
			IOException {
		String mnfAliasesScript = ManufAliasesCreateTableScriptGenerator.generateForSynchronization(connection, parentStoreId);
		String sql = gatherSQL(mnfAliasesScript);
		ParametersSubstitutor substitutor = new ParametersSubstitutor(
				parentStoreId, childStoreId, masterAssetId, userName);
		sql = substitutor.resolveParameters(sql);
		return sql;
	}

	private String gatherSQL(String mnfAliasesScript) throws IOException {

		final String sql_CreateChildStoreTable = "/com/cleanwise/service/api/util/synchronizer/sql/CreateChildStoreTable.sql";
		final String sql_CreateChildStoreTable4One = "/com/cleanwise/service/api/util/synchronizer/sql/CreateChildStoreTable4One.sql";
		final String sql_ParentChildStoresSynchronizer = "/com/cleanwise/service/api/util/synchronizer/sql/ParentChildStoresSynchronizer.sql";

		ResourceLoaderUtil resourceLoader = new ResourceLoaderUtil();
		String sql = "";
		if (this.childStoreId == 0) {
			sql = sql + resourceLoader.loadText(sql_CreateChildStoreTable);
		} else {
			sql = sql + resourceLoader.loadText(sql_CreateChildStoreTable4One);
		}
		sql = sql + "\n" + mnfAliasesScript +"\n";
		sql = sql + resourceLoader.loadText(sql_ParentChildStoresSynchronizer);
		return sql;
	}

}

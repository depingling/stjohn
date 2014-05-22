package com.cleanwise.service.api.util.synchronizer;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.lang.StringBuffer;

public class ExistingMasterObjectsValidator extends Validator {
	String loadingTableName;
	ParametersSubstitutor substitutor;
	String resourcePathName;

	public ExistingMasterObjectsValidator(String loadingTableName,
			ParametersSubstitutor substitutor, String resourcePathName) {
		super();
		this.loadingTableName = loadingTableName;
		this.substitutor = substitutor;
		this.resourcePathName = resourcePathName;
	}

	public String validate(Connection connection) throws Exception {

		long transactionTime = new Date().getTime();
		String sql = ManufAliasesCreateTableScriptGenerator
				.generateForExistingObjectValidator(connection,
						loadingTableName);
		sql = substitutor.resolveParameters(sql, transactionTime);
		SQLBatchExecutor.executeBatch(connection, sql);

		ResourceLoaderUtil resourceLoader = new ResourceLoaderUtil();
		sql = resourceLoader.loadText(resourcePathName);
		sql = substitutor.resolveParameters(sql, transactionTime);

		String errors =
			validateQuery(
					connection,
					sql,
					"There are existing masters in the parent store of current record store.",
					this.getClass().getName());

		sql = "DROP TABLE tmp_mnf_alias PURGE;";
		sql = substitutor.resolveParameters(sql, transactionTime);
		SQLBatchExecutor.executeBatch(connection, sql);
		
		return errors;
	}

}

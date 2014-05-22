package com.cleanwise.service.api.util.synchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CertifiedCompaniesValidator extends Validator{

	private String certifiedCompaniesScript;
	private ParametersSubstitutor substitutor;
	private String loadingTableName;
	
	public CertifiedCompaniesValidator(String certifiedCompaniesScript, String loadingTableName) {
		super();
		this.certifiedCompaniesScript = certifiedCompaniesScript;
		this.loadingTableName = loadingTableName; 
		substitutor = new ParametersSubstitutor(loadingTableName, "temp");
	}

	public String validate(Connection connection) throws Exception{

		long transactionTime = new Date().getTime();

		String query = certifiedCompaniesScript;
		query = substitutor.resolveParameters(query, transactionTime);
		SQLBatchExecutor.executeBatch(connection, query);
		
		query = 
			"SELECT   company_name, '{' || lines_no || '}' as lines, '[' || certified_companies || ']' as certified_companies_column " +
			  "FROM   tmp_crt_comp " +
			 "WHERE   bus_entity_id IS NULL " +
			 "ORDER BY lines_no";
		query = substitutor.resolveParameters(query, transactionTime);
		String errors =
			validateQuery(
					connection,
					query,
					"Can not find certified company.",
					this.getClass().getName());

		query = "DROP TABLE tmp_crt_comp PURGE;";
		query = substitutor.resolveParameters(query, transactionTime);
		SQLBatchExecutor.executeBatch(connection, query);
		
		return errors;
	}

}

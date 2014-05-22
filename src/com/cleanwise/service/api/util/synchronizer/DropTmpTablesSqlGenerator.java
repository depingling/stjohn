package com.cleanwise.service.api.util.synchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DropTmpTablesSqlGenerator {

	public static String generate(Connection connection, String tableNamePrefix) throws Exception {
		String sql = 
			"SELECT table_name " + 
			  "FROM all_all_tables " + 
			 "WHERE table_name LIKE '" + tableNamePrefix + "%'";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		String dropTablesScript = "";
		while (rs.next()) {
			dropTablesScript = dropTablesScript +
				"DROP TABLE " + rs.getString(1) + " PURGE;\n";
		}
		rs.close();
		stmt.close();
		return dropTablesScript;
	}

}

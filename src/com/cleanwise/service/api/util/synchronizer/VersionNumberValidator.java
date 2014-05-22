package com.cleanwise.service.api.util.synchronizer;

import java.sql.Connection;

public class VersionNumberValidator extends Validator {

	private String loadingTableName;
	public VersionNumberValidator(String loadingTableName) {
		super();
		this.loadingTableName = loadingTableName;
	}
	@Override
	public String validate(Connection connection) throws Exception {
		String query = 
			"SELECT line_number, version_number " + 
			"FROM " +  this.loadingTableName + " " +
			"WHERE version_number <> 1";

		return validateQuery(connection, query,
				"Version Number should be equal 1.", this.getClass().getName());
	}

}

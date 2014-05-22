package com.cleanwise.service.api.util.synchronizer;

import java.sql.Connection;

public class RequiredFieldValidator extends Validator {
	private String loadingTableName;
	private ParametersSubstitutor substitutor;
	private String[] requiredFields;

	public RequiredFieldValidator(String loadingTableName,
			ParametersSubstitutor substitutor, String[] requiredFields) {
		super();
		this.loadingTableName = loadingTableName;
		this.substitutor = substitutor;
		this.requiredFields = requiredFields;
	}

	@Override
	public String validate(Connection connection) throws Exception {
		String selectList = "";
		String whereClause = "";
		for (int i = 0; i < this.requiredFields.length; i++) {
			selectList = selectList + 
				", NVL(TRIM(" + this.requiredFields[i] + "), '<" +this.requiredFields[i] + " IS EMPTY>') AS " + 
				this.requiredFields[i];
			whereClause = whereClause + 
				"OR TRIM(" + this.requiredFields[i] + ") IS NULL ";
		}
		whereClause = whereClause.substring(3); 
		
		String query = "SELECT line_number" + selectList + " " + 
		                 "FROM " +  this.loadingTableName + " " +
				        "WHERE " + whereClause;

		return validateQuery(connection, query,
				"Values of required columns must not be empty.", this.getClass()
						.getName());

	}

}

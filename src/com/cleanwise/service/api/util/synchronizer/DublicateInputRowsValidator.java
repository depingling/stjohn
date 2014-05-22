package com.cleanwise.service.api.util.synchronizer;

import java.sql.Connection;
import java.util.Date;

public class DublicateInputRowsValidator extends Validator {
	String loadingTableName;
	ParametersSubstitutor substitutor;
	String[] uniqueFields;

	public DublicateInputRowsValidator(String loadingTableName,
			ParametersSubstitutor substitutor, String[] uniqueFields) {
		super();
		this.loadingTableName = loadingTableName;
		this.substitutor = substitutor;
		this.uniqueFields = uniqueFields;
	}

	@Override
	public String validate(Connection connection) throws Exception {

		String uniqueColUmnList = "";
		for (int i = 0; i < this.uniqueFields.length; i++) {
			uniqueColUmnList = uniqueColUmnList + ", " + this.uniqueFields[i];
		}
		uniqueColUmnList = uniqueColUmnList.substring(2);
		String query = 
				  "SELECT   '{' || wm_concat(line_number) ||  '}' as lines, " + uniqueColUmnList + " "
				+ "  FROM   " + this.loadingTableName + " GROUP BY " + uniqueColUmnList + " "
				+ "HAVING   (COUNT( * ) > 1) ";
		return validateQuery(connection, query, 
				"Can not create item. Duplicated " + uniqueColUmnList + " found.", 
				this.getClass().getName());

	}
}

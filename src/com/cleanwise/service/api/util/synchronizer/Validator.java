package com.cleanwise.service.api.util.synchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Validator {

	public static String validateQuery(Connection connection, String query,
			String titleMessage, String validatorName)
			throws SQLException, Exception {
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		int maxCount = 100;
		int count = 0;
		StringBuffer buffer = new StringBuffer();
		while (rs.next()) {
			if (count == maxCount) {
				buffer.append("and more..\n");
				break;
			}

			String row = "";
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				row = row + "," + rs.getObject(i + 1);
			}
			row = row.substring(1) + "\n";
			buffer.append(row);
			count++;
		}

		if (count > 0) {
			String columnNames = "";

			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				columnNames = columnNames + ","
						+ rs.getMetaData().getColumnLabel(i + 1);
			}
			columnNames = columnNames.substring(1);

			StringBuffer resultBuffer = new StringBuffer();
			resultBuffer.append("\n" + titleMessage + "\n");
			resultBuffer.append("\n\n" + columnNames + "\n");
			resultBuffer.append(buffer);
			resultBuffer.append("\n\n");

			rs.close();
			stmt.close();

			return validatorName + ":\n"
					+ resultBuffer.toString();
		} else {
			rs.close();
			stmt.close();
			return "";
		}
	}

	public abstract String validate(Connection connection) throws Exception;

	public Validator() {
		super();
	}

}

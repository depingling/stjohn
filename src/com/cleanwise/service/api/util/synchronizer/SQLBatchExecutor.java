package com.cleanwise.service.api.util.synchronizer;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;

public class SQLBatchExecutor {
   private static final Logger log = Logger.getLogger(SQLBatchExecutor.class);
	public static void executeBatch(Connection connection, String sqlPackage)
			throws SQLException {

		if (sqlPackage.length() == 0) {
			return;
		}

		SimpleDateFormat format = new SimpleDateFormat();
		format.applyLocalizedPattern("mm:ss:SSS");
		Date totalStart = new Date();

		// Statement statement = connection.createStatement();
		int stmtQty = 0;
		int nextSepPos = sqlPackage.indexOf(';');
		ArrayList statementArray = new ArrayList();
		while (nextSepPos >= 0) {
			Date startStepDate = new Date();
			String sql = sqlPackage.substring(0, nextSepPos).trim();
			sqlPackage = sqlPackage.substring(nextSepPos + 1);

			printTrace();
			printTrace("SQLBatchExecutor, Sql:");
			printTrace(sql);
			PreparedStatement statement = connection.prepareStatement(sql);
			int retCode = 0;
			stmtQty++;
			try {
				retCode = statement.executeUpdate();
			} catch (SQLException e) {
				printTrace("SQLBatchExecutor, Exeption in previous(" + stmtQty
						+ ") sql.");
				e.printStackTrace();
				printTrace("SQLBatchExecutor, Exeption END.");
				throw e;
			}

			printTrace("SQLBatchExecutor, RetCode: " + retCode);
			Date stepTime = new Date(new Date().getTime()
					- startStepDate.getTime());
			printTrace("SQLBatchExecutor, Step Time: "
					+ format.format(stepTime));
			statement.close();

			nextSepPos = sqlPackage.indexOf(';');
		}
		Date totalTime = new Date(new Date().getTime() - totalStart.getTime());
		printTrace("\n\n"
				+ "-- ---------------------------------------------\n"
				+ "--                S U C S E S S                 \n"
				+ "-- ---------------------------------------------\n"
				+ "-- Statements: " + stmtQty + "                  \n"
				+ "-- ---------------------------------------------\n"
				+ "-- T O T A L  T I M E: " + format.format(totalTime) + "\n"
				+ "-- ---------------------------------------------\n");
	}

	static void printTrace(String s) {
		log.info(s);
	}

	static void printTrace() {
		printTrace("");
	}

}

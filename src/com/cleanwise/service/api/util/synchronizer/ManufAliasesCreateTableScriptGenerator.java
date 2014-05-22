package com.cleanwise.service.api.util.synchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ManufAliasesCreateTableScriptGenerator {
	private static final Logger log = Logger.getLogger(ManufAliasesCreateTableScriptGenerator.class);
	
	public static String generateForLoader(Connection connection,
			String loadingTableName) throws SQLException {
		return generate(connection, "AND EXISTS ( " + "SELECT * FROM "
				+ loadingTableName
				+ " WHERE store_id = clw_bus_entity_assoc.bus_entity2_id)");
	}

	public static String generateForExistingObjectValidator(
			Connection connection, String loadingTableName) throws SQLException {
		log.info("ManufAliasesCreateTableScriptGenerator MACTSGMACTSG loadingTableName: "+loadingTableName);
		return generate(connection, 
				"AND clw_bus_entity_assoc.bus_entity2_id IN ( " +
				"SELECT   clw_property.clw_value " +
				  "FROM   "+loadingTableName+" load, clw_property " +
				 "WHERE   (load.store_id = clw_property.bus_entity_id) " +
				         "AND (clw_property.short_desc = 'PARENT_STORE_ID'))");
	}

	public static String generateForSynchronization(Connection connection,
			int parentStoreId) throws SQLException {
		String parentStoreFilter = "";
		if (parentStoreId > 0) {
			parentStoreFilter = "AND (clw_bus_entity_assoc.bus_entity2_id = "
					+ parentStoreId + ")";
		}
		return generate(connection, parentStoreFilter);
	}

	private static String generate(Connection connection,
			String parentStoreFilter) throws SQLException {
		String sql;
		sql = "CREATE TABLE tmp_mnf_alias \n" + "( \n"
				+ "manuf_id NUMBER (38) NOT NULL, \n"
				+ "alias    VARCHAR2 (255) \n" + ");\n";

		String query = "SELECT  clw_bus_entity.bus_entity_id, "
				+ "clw_bus_entity.short_desc, "
				+ "clw_property.clw_value "
				+ "FROM   clw_property, clw_bus_entity, clw_bus_entity_assoc "
				+ "WHERE   (clw_bus_entity.bus_entity_id = clw_property.bus_entity_id(+)) "
				+ "AND (clw_property.short_desc(+) = 'OTHER_NAMES') "
				+ "AND (clw_bus_entity.bus_entity_id = "
				+ "clw_bus_entity_assoc.bus_entity1_id) "
				+ "AND (clw_bus_entity.bus_entity_type_cd = 'MANUFACTURER') "
				+ "AND (clw_bus_entity_assoc.bus_entity_assoc_cd = "
				+ "'MANUFACTURER OF STORE') " + parentStoreFilter;

		log.info(query);
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int busEntityId = rs.getInt(1);
			String mainName = rs.getString(2);
			String otherNames = rs.getString(3);
			sql = sql
					+ ManufAliasesCreateTableScriptGenerator.getInsertSql(
							busEntityId, mainName);

			if (otherNames != null && otherNames.trim().length() > 0) {
				String[] names = otherNames.split("\n");
				for (int i = 0; i < names.length; i++) {
					if (names[i] != null && names[i].trim().length() > 0) {
						sql = sql
								+ ManufAliasesCreateTableScriptGenerator
										.getInsertSql(busEntityId, names[i]);
					}
				}
			}

		}
		rs.close();
		stmt.close();
		return sql;
	}

	static String getInsertSql(int busEntityId, final String alias) {
		return "INSERT INTO tmp_mnf_alias VALUES(" + busEntityId + ", " + "'"
				+ alias.trim().replace("'", "''") + "');\n";
	}

}

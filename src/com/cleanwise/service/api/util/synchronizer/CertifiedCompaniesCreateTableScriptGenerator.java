package com.cleanwise.service.api.util.synchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class CertifiedCompaniesCreateTableScriptGenerator {
	
	private static final Logger log = Logger.getLogger(CertifiedCompaniesCreateTableScriptGenerator.class);

	public static String generateForLoader(Connection connection,
			String loadingTableName) throws Exception{
		String sql;
		sql = 
			"CREATE TABLE tmp_crt_comp " +
			"( " +
			   "certified_companies   VARCHAR2(255), " +
			   "lines_no               VARCHAR2(255), " +
			   "company_name          VARCHAR2(255), " +
			   "bus_entity_id         NUMBER(38) " +
			");";			

		String query = 
			  "SELECT   certified_companies, wm_concat(line_number) as lines_no " +
			    "FROM   " + loadingTableName + " " +
			   "WHERE   (certified_companies IS NOT NULL) " +
			"GROUP BY   certified_companies ";
		
		log.info(query);
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			String certifiedCompanies = rs.getString(1);
			String lines_no = rs.getString(2);
			if (certifiedCompanies.trim().length() > 0) {
				String[] names = certifiedCompanies.split(",");
				for (int i = 0; i < names.length; i++) {
					if (names[i] != null && names[i].trim().length() > 0) {
						sql = sql + getInsertSql(certifiedCompanies, lines_no, names[i]);
					}
				}
			}
		}
		rs.close();
		stmt.close();
		
		sql = sql +
			"MERGE INTO   tmp_crt_comp target " +
			     "USING   (SELECT   input.certified_companies, " +
			                       "input.company_name, " +
			                       "clw_bus_entity.bus_entity_id " +
			                "FROM   tmp_crt_comp input, clw_bus_entity " +
			               "WHERE   (input.company_name = clw_bus_entity.short_desc(+)) " +
			                       "AND(clw_bus_entity.bus_entity_type_cd(+) = " +
			                              "'CERTIFIED_COMPANY')) source " +
			        "ON   (source.certified_companies = target.certified_companies " +
			              "AND source.company_name = target.company_name) " +
			"WHEN MATCHED " +
			"THEN " +
			   "UPDATE SET target.bus_entity_id = source.bus_entity_id; ";		
		
		return sql;
		
	}
	static String getInsertSql(String certifiedCompanies, String lines_no, final String companyName) {
		return 
		"INSERT INTO tmp_crt_comp ( " +
			"CERTIFIED_COMPANIES, LINES_NO, COMPANY_NAME) " + 
			"VALUES ('" + certifiedCompanies.replace("'", "''") + "', " + 
			"'" + lines_no + "', " + 
			"'" + companyName.trim().replace("'", "''") + "');"		
		;
		
	}

}

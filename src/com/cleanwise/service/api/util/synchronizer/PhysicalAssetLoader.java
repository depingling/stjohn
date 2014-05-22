package com.cleanwise.service.api.util.synchronizer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;


public class PhysicalAssetLoader {
    
	private String loadingTableName;
	String userName;
	
        private static final Logger log = Logger.getLogger(PhysicalAssetLoader.class);
        private static String className = PhysicalAssetLoader.class.getName();
        
	public PhysicalAssetLoader(String loadingTableName, String userName) {
		super();
		this.loadingTableName = loadingTableName;
		this.userName = userName;
	}

	public void load(Connection connection) throws Exception {
               
            boolean isOk = true;
            StringBuffer errorMessages = new StringBuffer();
            do {
                ParametersSubstitutor substitutor = new ParametersSubstitutor(loadingTableName,
                                                                              userName);
                ResourceLoaderUtil resourceLoader = new ResourceLoaderUtil();
                if (!validateInput(connection,
                                   resourceLoader,
                                   substitutor,
                                   errorMessages)) {
                    isOk = false;
                    log.info("[" + className + ".load()] \r\n" + errorMessages.toString());
                    log.error("[" + className + ".load()] Invalid input data. Process will be terminated.");
                    break;
                }

                if (!loadData(connection,
                              resourceLoader,
                              substitutor,
                              errorMessages)) {
                    isOk = false;
                    log.info("[" + className + ".load()] \r\n" + errorMessages.toString());
                    log.error("[" + className + ".load()] Error during data loading. Process will be terminated.");
                    break;
                }
            } while (false);
            if (!isOk) {
                throw new Exception("[" + className + ".load()] ERROR OCCURED. Process is terminated.");
            }
	}

	private String gatherSQL(ResourceLoaderUtil resourceLoader) throws IOException {

		final String sql_CatAndManuf                = "/com/cleanwise/service/api/util/synchronizer/sql/physicalloader/LoadPA1_CatAndManuf.sql";
		
		final String sql_UpdateMatchedAssets        = "/com/cleanwise/service/api/util/synchronizer/sql/physicalloader/LoadPA2.1_UpdateMatchedAssets.sql";     
		final String sql_UpdateMatchedProperties    = "/com/cleanwise/service/api/util/synchronizer/sql/physicalloader/LoadPA2.2_UpdateMatchedProperties.sql"; 
		final String sql_UpdateMatchedImage         = "/com/cleanwise/service/api/util/synchronizer/sql/physicalloader/LoadPA2.3_UpdateMatchedImage.sql";      
		final String sql_UpdateMatchedSpec          = "/com/cleanwise/service/api/util/synchronizer/sql/physicalloader/LoadPA2.4_UpdateMatchedSpec.sql";      
		
		final String sql_SelectUnmatched            = "/com/cleanwise/service/api/util/synchronizer/sql/physicalloader/LoadPA3_SelectUnmatched.sql";    
		final String sql_UnmatchedToClwAsset        = "/com/cleanwise/service/api/util/synchronizer/sql/physicalloader/LoadPA4_UnmatchedToClwAsset.sql";  

		String sql = "";
		sql = sql + loadSqlFile(sql_CatAndManuf, resourceLoader);

		sql = sql + loadSqlFile(sql_UpdateMatchedAssets, resourceLoader);
		sql = sql + loadSqlFile(sql_UpdateMatchedProperties, resourceLoader);
		sql = sql + loadSqlFile(sql_UpdateMatchedImage, resourceLoader);
		sql = sql + loadSqlFile(sql_UpdateMatchedSpec, resourceLoader);
		
		sql = sql + loadSqlFile(sql_SelectUnmatched, resourceLoader);

		sql = sql + loadSqlFile(sql_UnmatchedToClwAsset, resourceLoader);

		return sql;
	}
        
        private boolean validateInput(Connection connection,
                                      ResourceLoaderUtil resourceLoader,
                                      ParametersSubstitutor substitutor,
                                      StringBuffer errorMessages) throws Exception {
            boolean isOk = true;
            String sql = "";
            //final String validateMandatoryFields = "/com/cleanwise/service/api/util/synchronizer/sql/physicalloader/LoadPA_ValidateMandatoryFields.sql";
            final String validateUniqueKey = "/com/cleanwise/service/api/util/synchronizer/sql/physicalloader/LoadPA_ValidateUniqueKey.sql";
            final String validateStoreAccountSite = "/com/cleanwise/service/api/util/synchronizer/sql/physicalloader/LoadPA_ValidateStoreAccountSite.sql";
            final String validateVersionNumber = "/com/cleanwise/service/api/util/synchronizer/sql/physicalloader/LoadPA_ValidateVersionNumber.sql";
            
            try {
                
                sql = resourceLoader.loadText(validateVersionNumber);
                sql = substitutor.resolveParameters(sql);

                PreparedStatement statement = connection.prepareStatement(sql);
                log.info(sql);
                ResultSet rs = statement.executeQuery(sql);
                
                StringBuffer row = new StringBuffer();
                StringBuffer message = new StringBuffer();
                while (rs.next()) {
                    row.setLength(0);
                    row.append("Line: [");
                    row.append(rs.getInt(1));
                    row.append("]  Version_Number: '");
                    row.append(rs.getInt(2));
                    row.append("'  Store_Id: '");
                    row.append(rs.getInt(3));
                    row.append("'  Account_Ref_Num: '");
                    row.append(rs.getString(4));
                    row.append("'  Asset_Number: '");
                    row.append(rs.getString(5));
                    row.append("'");
                    row.append("\r\n");
                    message.append(row.toString());
                }
                if (message.length() > 0) {
                    errorMessages.append("\r\nERROR: Version Number is other than 1!\r\n");
                    errorMessages.append(message.toString());
                    isOk = false;
                }

                sql = resourceLoader.loadText(validateUniqueKey);
                sql = substitutor.resolveParameters(sql);

                statement = connection.prepareStatement(sql);
                log.info(sql);
                rs = statement.executeQuery(sql);
                
                row = new StringBuffer();
                message = new StringBuffer();
                while (rs.next()) {
                    row.setLength(0);
                    row.append("Line: [");
                    row.append(rs.getInt(1));
                    row.append("]  Store_Id: '");
                    row.append(rs.getInt(2));
                    row.append("'  Account_Ref_Num: '");
                    row.append(rs.getString(3));
                    row.append("'  Asset_Number: '");
                    row.append(rs.getString(4));
                    row.append("'");
                    row.append("\r\n");
                    message.append(row.toString());
                }
                if (message.length() > 0) {
                    errorMessages.append("\r\nERROR: Duplicate unique keys!\r\n");
                    errorMessages.append(message.toString());
                    isOk = false;
                }
                
                sql = resourceLoader.loadText(validateStoreAccountSite);
                sql = substitutor.resolveParameters(sql);

                statement = connection.prepareStatement(sql);
                log.info(sql);
                rs = statement.executeQuery(sql);
                
                row = new StringBuffer();
                message = new StringBuffer();
                while (rs.next()) {
                    row.setLength(0);
                    row.append("Line: [");
                    row.append(rs.getInt(1));
                    row.append("]  Store_Id: '");
                    row.append(rs.getInt(2));
                    row.append("'  Account_Ref_Num: '");
                    row.append(rs.getString(3));
                    row.append("'  Site_Ref_Num: '");
                    row.append(rs.getString(4));
                    row.append("'  Asset_Number: '");
                    row.append(rs.getString(5));
                    row.append("'");
                    row.append("\r\n");
                    message.append(row.toString());
                }
                if (message.length() > 0) {
                    errorMessages.append("\r\nERROR: Wrong Store-Account-Sites for an Asset!\r\n");
                    errorMessages.append(message.toString());
                    isOk = false;
                }
            } catch (Exception e) {
                isOk = false;
                e.printStackTrace();
                throw e;
            }
            return isOk;
        }
        
        private boolean loadData (Connection connection,
                                  ResourceLoaderUtil resourceLoader,
                                  ParametersSubstitutor substitutor,
                                  StringBuffer errorMessages) throws Exception {
            boolean isOk = true;
            String sql = "";
            try {
                sql = gatherSQL(resourceLoader);
                sql = substitutor.resolveParameters(sql);
                SQLBatchExecutor.executeBatch(connection, sql);
            } catch (SQLException e) {
                isOk = false;
                e.printStackTrace();
                throw e;
            }
            return isOk;
        }
        
	private String loadSqlFile(final String sql_fileName,
			ResourceLoaderUtil resourceLoader) throws IOException {
		return 
			"-- " + sql_fileName + " begin \n" +  
			resourceLoader.loadText(sql_fileName) +
			"-- " + sql_fileName + " end";  
	}

	
}

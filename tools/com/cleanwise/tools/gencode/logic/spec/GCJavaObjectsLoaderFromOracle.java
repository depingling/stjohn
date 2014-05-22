/**
 * Title:        GCJavaObjectsLoaderFromOracle
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */
package com.cleanwise.tools.gencode.logic.spec;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.cleanwise.tools.gencode.GCCodeNames;
import com.cleanwise.tools.gencode.GCException;
import com.cleanwise.tools.gencode.GCJavaObjects;
import com.cleanwise.tools.gencode.items.GCField;
import com.cleanwise.tools.gencode.items.GCForeignKey;
import com.cleanwise.tools.gencode.items.GCForeignKeyColumnRef;
import com.cleanwise.tools.gencode.items.GCIndex;
import com.cleanwise.tools.gencode.items.GCIndexColumnRef;
import com.cleanwise.tools.gencode.items.GCPrimaryKey;
import com.cleanwise.tools.gencode.items.GCPrimaryKeyColumnRef;
import com.cleanwise.tools.gencode.items.GCSequence;
import com.cleanwise.tools.gencode.items.GCTable;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsLoader;
import com.cleanwise.tools.gencode.utils.GCUtils;

public class GCJavaObjectsLoaderFromOracle implements GCJavaObjectsLoader {

    public GCJavaObjectsLoaderFromOracle() {
        _javaObjects = null;
    }

    public GCJavaObjects getJavaObjects() {
        return _javaObjects;
    }
    
    public ArrayList<GCTable> getLogTables(){
    	return _logTables;
    }

    public boolean loadData(Properties settings) throws GCException {
        String tag = "";
        String dbDriver = "";
        String dbUrl = "";
        String dbLogin = "";
        String dbPassword = "";
        String tableOwner = "";
        //String tablePrefix1 = "";
        List<String> tablePrefixList = null;
        String tableSpace = "";
        String schemaPattern = "";
        //String tableNamePattern1 = "";
        List<String> tableNamePatternList = null;
        String classSerialUidProperties = "";
        String useClassSerialUidProperties = "";
        int sizeOfPrefixes = 0;

        if (settings != null) {
            tag = settings.getProperty(GCCodeNames.Property.TAG);
            dbDriver = settings.getProperty(GCCodeNames.Property.DATABASE_DRIVER);
            dbUrl = settings.getProperty(GCCodeNames.Property.DATABASE_URL);
            dbLogin = settings.getProperty(GCCodeNames.Property.DATABASE_LOGIN);
            dbPassword = settings.getProperty(GCCodeNames.Property.DATABASE_PASSWORD);
            tableOwner = settings.getProperty(GCCodeNames.Property.DATABASE_TABLE_OWNER);
            //bug # 4535: Modified to support versioning for the temporary tables.
            String noOfPrefixes = settings.getProperty(GCCodeNames.Property.NO_OF_DATABASE_TABLE_PREFIXES);
            sizeOfPrefixes = Integer.parseInt(noOfPrefixes.trim());
            tablePrefixList = new ArrayList<String>(sizeOfPrefixes);
            tableNamePatternList = new ArrayList<String>(sizeOfPrefixes);
            for(int index=0; index<sizeOfPrefixes;index++) {
            	tablePrefixList.add(settings.getProperty(GCCodeNames.Property.DATABASE_TABLE_PREFIX+"_"+index));
            	tableNamePatternList.add(settings.getProperty(GCCodeNames.Property.DATABASE_TABLE_NAME_PATTERN+"_"+index));
            }
            
            //tablePrefix = settings.getProperty(GCCodeNames.Property.DATABASE_TABLE_PREFIX);
            tableSpace = settings.getProperty(GCCodeNames.Property.DATABASE_TABLESPACE);
            schemaPattern = settings.getProperty(GCCodeNames.Property.DATABASE_SCHEMA_PATTERN);
            //tableNamePattern = settings.getProperty(GCCodeNames.Property.DATABASE_TABLE_NAME_PATTERN);
            classSerialUidProperties = settings.getProperty(GCCodeNames.Property.CLASS_SERIAL_UID_PROPERTIES);
            useClassSerialUidProperties = settings.getProperty(GCCodeNames.Property.USE_CLASS_SERIAL_UID_PROPERTIES);
        }

        if (GCUtils.IsEmptyString(dbDriver)) {
            throw new GCException("Database driver is not defined", "GCJavaObjectsLoaderFromOracle.loadData");
        }
        if (GCUtils.IsEmptyString(dbUrl)) {
            throw new GCException("Database url is not defined", "GCJavaObjectsLoaderFromOracle.loadData");
        }
        if (GCUtils.IsEmptyString(dbLogin)) {
            throw new GCException("Database login is not defined", "GCJavaObjectsLoaderFromOracle.loadData");
        }
        if (GCUtils.IsEmptyString(dbPassword)) {
            throw new GCException("Database password is not defined", "GCJavaObjectsLoaderFromOracle.loadData");
        }
        if (!GCUtils.IsEmptyString(useClassSerialUidProperties)) {
            if (GCUtils.IsEmptyString(classSerialUidProperties)) {
                throw new GCException("A file with serial identifiers for classes (defined by attribute '" + GCCodeNames.Property.CLASS_SERIAL_UID_PROPERTIES + "') is not defined", "GCJavaObjectsLoaderFromOracle.loadData");
            } else {
                boolean fileExists = false;
                try {
                    fileExists = GCUtils.FileExists(classSerialUidProperties);
                } catch (Exception ex) {
                }
                if (!fileExists) {
                    throw new GCException("Can not find the file '" + classSerialUidProperties + "' with serial identifiers for classes (defined by attribute '" + GCCodeNames.Property.CLASS_SERIAL_UID_PROPERTIES + "')", "GCJavaObjectsLoaderFromOracle.loadData");
                }
            }
        }

        System.out.println("#########################################################");
        System.out.println("# [GCJavaObjectsLoaderFromOracle] PROPERTIES");
        System.out.println("# " + GCCodeNames.Property.DATABASE_DRIVER + ": " + dbDriver);
        System.out.println("# " + GCCodeNames.Property.DATABASE_URL + ": " + dbUrl);
        System.out.println("# " + GCCodeNames.Property.DATABASE_LOGIN + ": " + dbLogin);
        System.out.println("# " + GCCodeNames.Property.DATABASE_TABLE_OWNER + ": " + tableOwner);
        for(int index=0; index<sizeOfPrefixes;index++) {
        	System.out.println("# " + GCCodeNames.Property.DATABASE_TABLE_PREFIX+"_"+index+ ": " + tablePrefixList.get(index));
        	System.out.println("# " + GCCodeNames.Property.DATABASE_TABLE_NAME_PATTERN+"_"+index+ ": " + tableNamePatternList.get(index));
        }
        //System.out.println("# " + GCCodeNames.Property.DATABASE_TABLE_PREFIX + ": " + tablePrefix);
        System.out.println("# " + GCCodeNames.Property.DATABASE_SCHEMA_PATTERN + ": " + schemaPattern);
        System.out.println("# " + GCCodeNames.Property.DATABASE_TABLESPACE + ": " + tableSpace);
        //System.out.println("# " + GCCodeNames.Property.DATABASE_TABLE_NAME_PATTERN + ": " + tableNamePattern);
        System.out.println("# " + GCCodeNames.Property.CLASS_SERIAL_UID_PROPERTIES + ": " + classSerialUidProperties);
        System.out.println("# " + GCCodeNames.Property.USE_CLASS_SERIAL_UID_PROPERTIES + ": " + useClassSerialUidProperties);
        System.out.println("# " + GCCodeNames.Property.TAG + ": " + tag);
        System.out.println("#########################################################");

        _javaObjects = new GCJavaObjects();
        _javaObjects.setTables(new ArrayList<GCTable>());
        _javaObjects.setForeignKeys(new ArrayList<GCForeignKey>());
        _javaObjects.setIndexes(new ArrayList<GCIndex>());
        _javaObjects.setSequences(new ArrayList<GCSequence>());
        _logTables = new ArrayList<GCTable>();

        loadObjects(dbDriver, dbUrl, dbLogin, dbPassword, tablePrefixList,
            schemaPattern, tableNamePatternList, tableOwner);

        if (!GCUtils.IsEmptyString(useClassSerialUidProperties)) {
    		setClassSerialUidForTables(_javaObjects.getTables(),
    				tablePrefixList, classSerialUidProperties);
        }
        return true;
    }

    private void loadSequences(Connection connection, String tablePrefix,
        String schemaPattern) throws GCException {
        try {
            String sqlWhere = "";
            if (!GCUtils.IsEmptyString(tablePrefix) || !GCUtils.IsEmptyString(schemaPattern)) {
                if (!GCUtils.IsEmptyString(tablePrefix)) {
                    if (!GCUtils.IsEmptyString(sqlWhere)) {
                        sqlWhere += "AND ";
                    }
                    sqlWhere += "tblSeq.SEQUENCE_NAME LIKE '" + tablePrefix + "%' ";
                }
                if (!GCUtils.IsEmptyString(schemaPattern)) {
                    if (!GCUtils.IsEmptyString(sqlWhere)) {
                        sqlWhere += "AND ";
                    }
                    sqlWhere += "tblSeq.SEQUENCE_OWNER = '" + schemaPattern + "' ";
                }
                sqlWhere = "WHERE " + sqlWhere;
            }
            String sql =
                "SELECT " +
                "tblSeq.SEQUENCE_OWNER, " +
                "tblSeq.SEQUENCE_NAME, " +
                "tblSeq.MAX_VALUE, " +
                "tblSeq.MIN_VALUE " +
                "FROM " +
                "ALL_SEQUENCES tblSeq " +
                sqlWhere +
                "ORDER BY " +
                "tblSeq.SEQUENCE_NAME";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet sequenceSet = stmt.executeQuery();
            if (sequenceSet != null) {
                while (sequenceSet.next()) {
                    String currSequenceName = sequenceSet.getString("SEQUENCE_NAME");
                    if (GCUtils.IsEmptyString(currSequenceName)) {
                        continue;
                    }
                    GCSequence sequence = new GCSequence();
                    sequence.setName(currSequenceName);
                    _javaObjects.getSequences().add(sequence);
                }
                sequenceSet.close();
            }
            stmt.close();
        } catch (Exception ex) {
            throw new GCException("Exception: " + ex.getMessage(),
                "GCJavaObjectsLoaderFromOracle.loadSequences");
        }
    }

    private void loadIndexes(Connection connection, String tablePrefix,
        String schemaPattern) throws GCException {
        try {
            String sqlWhere = "WHERE tblInd.UNIQUENESS = 'NONUNIQUE' ";
            if (!GCUtils.IsEmptyString(tablePrefix) || !GCUtils.IsEmptyString(schemaPattern)) {
                if (!GCUtils.IsEmptyString(tablePrefix)) {
                    sqlWhere += "AND tblInd.TABLE_NAME LIKE '" + tablePrefix + "%' ";
                }
                if (!GCUtils.IsEmptyString(schemaPattern)) {
                    sqlWhere += "AND tblInd.TABLE_OWNER = '" + schemaPattern + "' ";
                }
            }
            String sql =
                "SELECT " +
                "tblInd.TABLE_OWNER, " +
                "tblInd.TABLE_NAME, " +
                "tblInd.INDEX_NAME, " +
                "tblInd.UNIQUENESS, " +
                "tblCol.COLUMN_NAME, " +
                "tblCol.COLUMN_POSITION, " +
                "tblCol.DESCEND " +
                "FROM " +
                "ALL_INDEXES tblInd JOIN ALL_IND_COLUMNS tblCol " +
                "ON " +
                "tblInd.INDEX_NAME = tblCol.INDEX_NAME AND " +
                "tblInd.TABLE_OWNER = tblCol.TABLE_OWNER " +
                sqlWhere +
                "ORDER BY " +
                "tblInd.TABLE_NAME, " +
                "tblInd.INDEX_NAME, " +
                "tblCol.COLUMN_POSITION";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet indexSet = stmt.executeQuery();
            if (indexSet != null) {
                GCIndex index = null;
                String currTableName = "";
                String currIndexName = "";
                String prevTableName = "";
                String prevIndexName = "";
                while (indexSet.next()) {
                    currTableName = indexSet.getString("TABLE_NAME");
                    currIndexName = indexSet.getString("INDEX_NAME");
                    if (GCUtils.IsEmptyString(currTableName) || GCUtils.IsEmptyString(currIndexName)) {
                        continue;
                    }
                    if (!currIndexName.equalsIgnoreCase(prevIndexName)) {
                        if (prevTableName.length() > 0 && prevIndexName.length() > 0) {
                            _javaObjects.getIndexes().add(index);
                        }
                        index = new GCIndex();
                        index.setTable(currTableName);
                        index.setName(currIndexName);
                        index.setIsUnique(false);
                        if (indexSet.getString("UNIQUENESS") != null) {
                            if ("UNIQUE".equalsIgnoreCase(indexSet.getString("UNIQUENESS"))) {
                                index.setIsUnique(true);
                            }
                        }
                        index.setType(0);
                        prevTableName = currTableName;
                        prevIndexName = currIndexName;
                    }
                    index.addColumnRef(new GCIndexColumnRef(
                        indexSet.getString("COLUMN_NAME"),
                        indexSet.getInt("COLUMN_POSITION")));
                }
                if (prevTableName.length() > 0 && prevIndexName.length() > 0) {
                    _javaObjects.getIndexes().add(index);
                }
                indexSet.close();
            }
            stmt.close();
        } catch (Exception ex) {
            throw new GCException("Exception: " + ex.getMessage(),
                "GCJavaObjectsLoaderFromOracle.loadIndexes");
        }
    }

    private void loadObjects(String dbDriver, String dbUrl,
        String dbLogin, String dbPassword, List<String> tablePrefixList,
        String schemaPattern, List<String> tableNamePatternList, String tableOwner)
        throws GCException {

        Connection connection = null;
        DatabaseMetaData dbMetaData = null;
        ResultSet tablesSet = null;
		HashMap<String,LinkedList<GCField>> columnHM = null;
        ResultSet primaryKeysSet = null;
        ResultSet exportedKeysSet = null;
        String tableName = null;

        try {
            connection = createConnection(dbDriver, dbUrl, dbLogin, dbPassword);
            columnHM = new HashMap<String,LinkedList<GCField>>();
            for(String tablePrefix:tablePrefixList) {
            	 /// Loading of indexes
            	loadIndexes(connection, tablePrefix, schemaPattern);
            	/// Loading of sequences
                loadSequences(connection, tablePrefix, schemaPattern);
               /// Load columns info
    			columnHM.putAll(getTabColumns(connection, tablePrefix)) ;
            }

            /// Loading of tables
            dbMetaData = connection.getMetaData();
            for(String tableNamePattern:tableNamePatternList) {
                tablesSet = dbMetaData.getTables(null, schemaPattern,tableNamePattern, new String[]{"TABLE"});
	            if (tablesSet != null) {
	                while (tablesSet.next()) {
	                    tableName = tablesSet.getString("TABLE_NAME");
	
	                    /// Definition of table
	                    GCTable table = new GCTable();
	                    table.setName(tableName);
	
	                    System.out.println("[GCJavaObjectsLoaderFromOracle] Processing of table: " + tableName);
	
	                    /// Definition of primary keys
	                    primaryKeysSet = dbMetaData.getPrimaryKeys(null, schemaPattern, tableName);
	                    if (primaryKeysSet != null) {
	                        GCPrimaryKey primaryKey = new GCPrimaryKey();
	                        while (primaryKeysSet.next()) {
	                            primaryKey.addColumnRef(new GCPrimaryKeyColumnRef(
	                                primaryKeysSet.getString("COLUMN_NAME"),
	                                primaryKeysSet.getInt("KEY_SEQ")));
	                            if (GCUtils.IsEmptyString(primaryKey.getName())) {
	                                primaryKey.setName(primaryKeysSet.getString("PK_NAME"));
	                            }
	                        }
	                        table.setPrimaryKey(primaryKey);
	                        primaryKeysSet.close();
	                    }
	
	                    /// Definition of columns
						LinkedList<GCField> tabColumns = columnHM.get(tableName);
	                    for(GCField field : tabColumns) {
							field.setIsPrimaryKey(table.isColumnPrimaryKey(field.getName()));
							table.addField(field);
	                    }
	
	                    ///
	                    _javaObjects.getTables().add(table);
	
	                    /// Definition of foreign keys
	                    exportedKeysSet = dbMetaData.getExportedKeys(null, schemaPattern, tableName);
	                    if (exportedKeysSet != null) {
	                        GCForeignKey foreignKey = null;
	                        String currFkTableName = "";
	                        String prevFkTableName = "";
	                        String currFkName = "";
	                        String prevFkName = "";
	                        while (exportedKeysSet.next()) {
	                            currFkTableName = exportedKeysSet.getString("FKTABLE_NAME");
	                            currFkName = exportedKeysSet.getString("FK_NAME");
	                            if (GCUtils.IsEmptyString(currFkTableName) || GCUtils.IsEmptyString(currFkName)) {
	                                continue;
	                            }
	                            if (!currFkTableName.equalsIgnoreCase(prevFkTableName) || !currFkName.equalsIgnoreCase(prevFkName)) {
	                                if (prevFkTableName.length() > 0 && prevFkName.length() > 0) {
	                                    _javaObjects.getForeignKeys().add(foreignKey);
	                                }
	                                foreignKey = new GCForeignKey();
	                                foreignKey.setName(currFkName);
	                                foreignKey.setPkName(exportedKeysSet.getString("PK_NAME"));
	                                foreignKey.setFkTable(currFkTableName);
	                                foreignKey.setPkTable(exportedKeysSet.getString("PKTABLE_NAME"));
	                                foreignKey.setUpdateRule(exportedKeysSet.getShort("UPDATE_RULE"));
	                                foreignKey.setDeleteRule(exportedKeysSet.getShort("DELETE_RULE"));
	                                prevFkTableName = currFkTableName;
	                                prevFkName = currFkName;
	                            }
	                            foreignKey.addColumnRef(new GCForeignKeyColumnRef(
	                                exportedKeysSet.getString("FKCOLUMN_NAME"),
	                                exportedKeysSet.getString("PKCOLUMN_NAME"),
	                                exportedKeysSet.getShort("KEY_SEQ")));
	                        }
	                        if (prevFkTableName.length() > 0 && prevFkName.length() > 0) {
	                            _javaObjects.getForeignKeys().add(foreignKey);
	                        }
	                        exportedKeysSet.close();
	                    }
	                }
	            }
            }
        } catch (GCException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new GCException("Exception: " + ex.getMessage(),
                "GCJavaObjectsLoaderFromOracle.loadObjects");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) {
                }
            }
        }
    }

	private HashMap<String, LinkedList<GCField>> getTabColumns(Connection conn, String tablePrefix) 
	throws java.sql.SQLException
	{
		HashMap<String,LinkedList<GCField>> hm = new HashMap<String,LinkedList<GCField>>();
		String sql = "SELECT "+
			"TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, "+
			"DATA_SCALE, NULLABLE, COLUMN_ID, "+
			"CHARACTER_SET_NAME, CHAR_LENGTH, CHAR_USED "+
            "FROM user_tab_columns "+
			"WHERE table_name like Upper('"+tablePrefix+"%')";

		PreparedStatement tabColumnsPS = conn.prepareStatement(sql);
        ResultSet columnsSet = tabColumnsPS.executeQuery();
        while (columnsSet.next()) {
            GCField field = new GCField();
            String columnName = columnsSet.getString("COLUMN_NAME");
            field.setName(columnName);
			String dataType = columnsSet.getString("DATA_TYPE");
            field.setSqlType(dataType);
            field.setType(dataType);
            field.setIsNullable("Y".equalsIgnoreCase(columnsSet.getString("NULLABLE")));
			int dataLength = columnsSet.getInt("DATA_LENGTH");
			if ("VARCHAR2".equals(dataType)) {
				dataLength = columnsSet.getInt("CHAR_LENGTH");
			} else if("NUMBER".equals(dataType)) { 
				dataLength = columnsSet.getInt("DATA_PRECISION");
			}
            field.setSize(dataLength);
            field.setDigits(columnsSet.getInt("DATA_SCALE"));
            field.setCharUsed(columnsSet.getString("CHAR_USED"));
			String tableName = columnsSet.getString("TABLE_NAME");
			LinkedList<GCField> ll = hm.get(tableName);
			if(ll == null) {
				ll = new LinkedList<GCField>();
				hm.put(tableName,ll);
			}
			ll.add(field);
        }
        columnsSet.close();
		return hm;
	}

    private void setClassSerialUidForTables(ArrayList<GCTable> tables,
        List<String> tablePrefixList, String classSerialUidProperties) throws GCException {
        if (tables == null || classSerialUidProperties == null) {
            return;
        }
        try {
            Properties properties = new Properties();
            FileInputStream inputStream = new FileInputStream(classSerialUidProperties);
            properties.load(inputStream);
            GCTable table = null;
            String tableJavaName = null;
            String fullDataJavaName = null;
            String fullVectorJavaName = null;
            String versionDataUid = null;
            String versionVectorUid = null;
            for (int i = 0; i < tables.size(); ++i) {
                table = tables.get(i);
                //tableJavaName = GCUtils.GetJavaNameByDbName(table.getName(), prefix);
                tableJavaName = GCUtils.getJavaNameByDbTableName(table.getName(), tablePrefixList);
                fullDataJavaName = GCUtils.getFullDataJavaName(tableJavaName);
                fullVectorJavaName = GCUtils.getFullVectorJavaName(tableJavaName);
                versionDataUid = properties.getProperty(fullDataJavaName);
                if (GCUtils.IsEmptyString(versionDataUid)) {
                    throw new GCException("No version UID for java class: " + fullDataJavaName,
                        "GCJavaObjectsLoaderFromOracle.setClassSerialUidForTables");
                }
                versionVectorUid = properties.getProperty(fullVectorJavaName);
                if (GCUtils.IsEmptyString(versionVectorUid)) {
                    throw new GCException("No version UID for java class: " + fullVectorJavaName,
                        "GCJavaObjectsLoaderFromOracle.setClassSerialUidForTables");
                }
                table.setSerialVersionDataUID(versionDataUid);
                table.setSerialVersionVectorUID(versionVectorUid);
            }
        } catch (GCException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new GCException("Exception: " + ex.getMessage(),
                "GCJavaObjectsLoaderFromOracle.setClassSerialUidForTables");
        }
    }

    private Connection createConnection(String dbDriver, String dbUrl,
        String dbLogin, String dbPassword) throws GCException {
        Connection connection = null;
        try {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(dbUrl, dbLogin, dbPassword);
        } catch (SQLException ex) {
            throw new GCException("SQLException. " + ex.getMessage(),
                "GCJavaObjectsLoaderFromOracle.createConnection");
        } catch (ClassNotFoundException ex) {
            throw new GCException("ClassNotFoundException: " + ex.getMessage(),
                "GCJavaObjectsLoaderFromOracle.createConnection");
        } catch (Exception ex) {
            throw new GCException("Exception: " + ex.getMessage(),
                "GCJavaObjectsLoaderFromOracle.createConnection");
        }
        return connection;
    }
    private GCJavaObjects _javaObjects;
    private ArrayList<GCTable> _logTables;
}

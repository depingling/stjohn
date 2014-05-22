/**
 * Title:        GCJavaObjectsLoaderFromEnterpriseDB
 */
package com.cleanwise.tools.gencode.logic.spec;

import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.LinkedList;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.HashSet;

import com.cleanwise.tools.gencode.GCException;
import com.cleanwise.tools.gencode.GCCodeNames;
import com.cleanwise.tools.gencode.GCJavaObjects;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsLoader;
import com.cleanwise.tools.gencode.items.GCIndexColumnRef;
import com.cleanwise.tools.gencode.items.GCPrimaryKeyColumnRef;
import com.cleanwise.tools.gencode.items.GCForeignKeyColumnRef;
import com.cleanwise.tools.gencode.items.GCField;
import com.cleanwise.tools.gencode.items.GCForeignKey;
import com.cleanwise.tools.gencode.items.GCTable;
import com.cleanwise.tools.gencode.items.GCIndex;
import com.cleanwise.tools.gencode.items.GCPrimaryKey;
import com.cleanwise.tools.gencode.items.GCSequence;
import com.cleanwise.tools.gencode.utils.GCUtils;

public class GCJavaObjectsLoaderFromEnterpriseDB implements GCJavaObjectsLoader {

    public GCJavaObjectsLoaderFromEnterpriseDB() {
        _javaObjects = null;
    }

    public GCJavaObjects getJavaObjects() {
        return _javaObjects;
    }

    public boolean loadData(Properties settings) throws GCException {
        String tag = "";
        String dbDriver = "";
        String dbUrl = "";
        String dbLogin = "";
        String dbPassword = "";
        String tableOwner = "";
        String tablePrefix = "";
        String tableSpace = "";
        String schemaPattern = "";
        String tableNamePattern = "";
        String classSerialUidProperties = "";
        String useClassSerialUidProperties = "";

        if (settings != null) {
            tag = settings.getProperty(GCCodeNames.Property.TAG);
            dbDriver = settings.getProperty(GCCodeNames.Property.DATABASE_DRIVER);
            dbUrl = settings.getProperty(GCCodeNames.Property.DATABASE_URL);
            dbLogin = settings.getProperty(GCCodeNames.Property.DATABASE_LOGIN);
            dbPassword = settings.getProperty(GCCodeNames.Property.DATABASE_PASSWORD);
            tableOwner = settings.getProperty(GCCodeNames.Property.DATABASE_TABLE_OWNER);
            tablePrefix = settings.getProperty(GCCodeNames.Property.DATABASE_TABLE_PREFIX);
            tableSpace = settings.getProperty(GCCodeNames.Property.DATABASE_TABLESPACE);
            schemaPattern = settings.getProperty(GCCodeNames.Property.DATABASE_SCHEMA_PATTERN);
            tableNamePattern = settings.getProperty(GCCodeNames.Property.DATABASE_TABLE_NAME_PATTERN);
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
        System.out.println("# [GCJavaObjectsLoaderFromEnterpriseDB] PROPERTIES:");
        System.out.println("# " + GCCodeNames.Property.DATABASE_DRIVER + ": " + dbDriver);
        System.out.println("# " + GCCodeNames.Property.DATABASE_URL + ": " + dbUrl);
        System.out.println("# " + GCCodeNames.Property.DATABASE_LOGIN + ": " + dbLogin);
        System.out.println("# " + GCCodeNames.Property.DATABASE_TABLE_OWNER + ": " + tableOwner);
        System.out.println("# " + GCCodeNames.Property.DATABASE_TABLE_PREFIX + ": " + tablePrefix);
        System.out.println("# " + GCCodeNames.Property.DATABASE_SCHEMA_PATTERN + ": " + schemaPattern);
        System.out.println("# " + GCCodeNames.Property.DATABASE_TABLESPACE + ": " + tableSpace);
        System.out.println("# " + GCCodeNames.Property.DATABASE_TABLE_NAME_PATTERN + ": " + tableNamePattern);
        System.out.println("# " + GCCodeNames.Property.CLASS_SERIAL_UID_PROPERTIES + ": " + classSerialUidProperties);
        System.out.println("# " + GCCodeNames.Property.USE_CLASS_SERIAL_UID_PROPERTIES + ": " + useClassSerialUidProperties);
        System.out.println("# " + GCCodeNames.Property.TAG + ": " + tag);
        System.out.println("#########################################################");

        _javaObjects = new GCJavaObjects();
        _javaObjects.setTables(new ArrayList<GCTable>());
        _javaObjects.setForeignKeys(new ArrayList<GCForeignKey>());
        _javaObjects.setIndexes(new ArrayList<GCIndex>());
        _javaObjects.setSequences(new ArrayList<GCSequence>());

        loadObjects(dbDriver, dbUrl, dbLogin, dbPassword, tablePrefix,
            schemaPattern, tableNamePattern, tableOwner);

        if (!GCUtils.IsEmptyString(useClassSerialUidProperties)) {
            setClassSerialUidForTables(_javaObjects.getTables(),
                tablePrefix, classSerialUidProperties);
        }
        return true;
    }

    private void loadSequences(Connection connection, String tablePrefix,
        String schemaPattern) throws GCException {
        try {
            String sqlWhere = "object_type = 'SEQUENCE'";
			
            if (!GCUtils.IsEmptyString(tablePrefix)) {
                sqlWhere += "AND ";
                String[] tablePrefixes = tablePrefix.split(",");
                sqlWhere += "(";
                for (int i = 0; tablePrefixes != null && i < tablePrefixes.length; i++) {
                    if (i > 0) {
                        sqlWhere += " OR ";
                    }
                    sqlWhere += "tblSeq.OBJECT_NAME LIKE '"
                                + tablePrefixes[i] + "%' ";
                }
                sqlWhere += ")";
            }    
            sqlWhere += "AND ";
            sqlWhere += "tblSeq.schemaname = '" + schemaPattern + "' ";

            sqlWhere = "WHERE " + sqlWhere;
            
            String sql =
                "SELECT " +
                "tblSeq.SCHEMANAME, " +
                "tblSeq.OBJECT_NAME " +
                "FROM " +
                "ALL_OBJECTS tblSeq " +
                sqlWhere +
                "ORDER BY " +
                "tblSeq.OBJECT_NAME";
//System.out.println("GGGG sql: "+sql);
			PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet sequenceSet = stmt.executeQuery();
            
			if (sequenceSet != null) {
                while (sequenceSet.next()) {
                    String currSequenceName = sequenceSet.getString("OBJECT_NAME");
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

    private void loadForeignKeys(Connection connection, String tableNamePattern,
        String schemaPattern) throws GCException {
        try {
            String[] tableNamePatternes = tableNamePattern.split(",");
            String sqlTableNameCond = "";
            for (int i = 0; tableNamePatternes != null && i < tableNamePatternes.length; i++) {
                if (i == 0) {
//                    sqlTableNameCond = sqlTableNameCond + " WHERE ";
                    if (tableNamePatternes.length > 1) {
                        sqlTableNameCond = sqlTableNameCond + "( ";
                    }
                } else {
                    sqlTableNameCond = sqlTableNameCond + " OR ";
                }
                sqlTableNameCond = sqlTableNameCond +
                                   "con.table_name LIKE '" + tableNamePatternes[i] + "%' ";

                if (i == tableNamePatternes.length - 1 && tableNamePatternes.length > 1) {
                        sqlTableNameCond = sqlTableNameCond + " )";
                }
            }
String sql = 
 "select " +
 "con.constraint_name as FK_NAME,  " +
 "con.schema_name,  " +
 "con.table_name as FKTABLE_NAME,  " +
 "conpk.constraint_name as PK_NAME,  " +
 "conpk.table_name as PKTABLE_NAME,  " +
 "con.delete_rule as DELETE_RULE  " +
 "from all_constraints con, all_constraints conpk  " +
 "where 1=1  " +
 "and con.constraint_type = 'F' " +
 "and con.r_constraint_name = conpk.constraint_name  "+
 "and con.schema_name = conpk.schema_name "+
 "and con.schema_name = '" + schemaPattern + "' and " + sqlTableNameCond;
// System.out.println("GCJavaObjectLoaderFromEnterpriseDB sql: " + sql);

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet fkSet = stmt.executeQuery();
            if (fkSet != null) {
                while (fkSet.next()) {

                    GCForeignKey foreignKey = new GCForeignKey();
                    foreignKey.setName(fkSet.getString("FK_NAME"));
                    foreignKey.setPkName(fkSet.getString("PK_NAME"));
                    foreignKey.setFkTable(fkSet.getString("FKTABLE_NAME"));
                    foreignKey.setPkTable(fkSet.getString("PKTABLE_NAME"));
//                                foreignKey.setUpdateRule(fkSet.getShort("UPDATE_RULE"));
//                                foreignKey.setDeleteRule(fkSet.getShort("DELETE_RULE"));
/*
                    foreignKey.addColumnRef(new GCForeignKeyColumnRef(
                            fkSet.getString("FKCOLUMN_NAME"),
                            fkSet.getString("PKCOLUMN_NAME"),
							
//                            fkSet.getShort("KEY_SEQ")));    ???????
                            1));
							*/
//                 System.out.println("==fk: " + foreignKey.getName() + ":" +
//                		                       foreignKey.getFkTable() + ":" +
//                		                       foreignKey.getPkName() + ":" +
//                		                       foreignKey.getPkTable());

                    _javaObjects.getForeignKeys().add(foreignKey);

                }
                fkSet.close();
            }
            stmt.close();
for(Iterator iter=_javaObjects.getForeignKeys().iterator(); iter.hasNext(); ) {
  GCForeignKey  foreignKey = (GCForeignKey) iter.next();
  String fkName = foreignKey.getName();
  String pkName = foreignKey.getPkName();
  String fkTableName = foreignKey.getFkTable();
  String pkTableName = foreignKey.getPkTable();
  String colSql = 
    " select "+ 
    "col.column_name as FKCOLUMN_NAME, "+ 
    "col.position as KEY_SEQ, "+ 
    "colpk.column_name as PKCOLUMN_NAME "+ 
    "from all_cons_columns col, all_cons_columns colpk  "+ 
    "where 1=1 "+
    "and col.schema_name = '"+schemaPattern+"' "+
    "and col.table_name = '"+fkTableName+"' "+
    "and col.constraint_name = '"+fkName+"' "+
    "and colpk.schema_name = col.schema_name "+
    "and colpk.table_name = '"+pkTableName+"' "+
    "and colpk.constraint_name = '"+pkName+"' ";
// System.out.println("colSql: " + colSql);

	        stmt = connection.prepareStatement(colSql);
            fkSet = stmt.executeQuery();
            if (fkSet != null) {
                while (fkSet.next()) {
					String fkColName = fkSet.getString("FKCOLUMN_NAME");
					String pkColName = fkSet.getString("PKCOLUMN_NAME");
// System.out.println(fkColName+" --> "+pkColName);
                    GCForeignKeyColumnRef colRef = new GCForeignKeyColumnRef(fkColName, pkColName,1);
                    foreignKey.addColumnRef(colRef);

                }
                fkSet.close();
            }
            stmt.close();
  
}			
			
        } catch (Exception ex) {
            throw new GCException("Exception: " + ex.getMessage(),
                "GCJavaObjectsLoaderFromOracle.loadForeignKeys");
        }
    }


    private void loadIndexes(Connection connection, String tablePrefix,
        String schemaPattern) throws GCException {
        try {
            String sqlWhere = "WHERE tblInd.UNIQUENESS = 'NONUNIQUE' ";
            if (!GCUtils.IsEmptyString(tablePrefix) || !GCUtils.IsEmptyString(schemaPattern)) {
                if (!GCUtils.IsEmptyString(tablePrefix)) {
                    String[] tablePrefixes = tablePrefix.split(",");
                    sqlWhere += "AND (";
                    for (int i = 0; tablePrefixes != null
                            && i < tablePrefixes.length; i++) {
                        if (i > 0) {
                            sqlWhere += " OR ";
                        }
                        sqlWhere += " tblInd.TABLE_NAME LIKE '"
                                + tablePrefixes[i] + "%' ";
                    }
                    sqlWhere += ")";
                    // sqlWhere += "AND tblInd.TABLE_NAME LIKE '" + tablePrefix
                    // + "%' ";
                }
                if (!GCUtils.IsEmptyString(schemaPattern)) {
                    sqlWhere += "AND tblInd.INDEX_SCHEMA = '" + schemaPattern + "' ";
                }
            }
            String sql =
                "SELECT " +
                "tblInd.INDEX_SCHEMA, " +
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
                "tblInd.INDEX_SCHEMA = tblCol.SCHEMA_NAME " +
                sqlWhere +
                "ORDER BY " +
                "tblInd.TABLE_NAME, " +
                "tblInd.INDEX_NAME, " +
                "tblCol.COLUMN_POSITION";
//System.out.println("GCJavaObjectsLoaderFromEnterpriseDB f				
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
		    ex.printStackTrace();
            throw new GCException("Exception: " + ex.getMessage(),
                "GCJavaObjectsLoaderFromOracle.loadIndexes");
        }
    }

    
    private void loadObjects(String dbDriver, String dbUrl,
            String dbLogin, String dbPassword, String tablePrefix,
            String schemaPattern, String tableNamePattern, String tableOwner)
            throws GCException {

            Connection connection = null;
            DatabaseMetaData dbMetaData = null;
    		HashMap<String,LinkedList> columnHM = null;
            ResultSet primaryKeysSet = null;
            ResultSet exportedKeysSet = null;
            ResultSet importedKeysSet = null;
            String tableName = null;

            try {
                connection = createConnection(dbDriver, dbUrl, dbLogin, dbPassword);
                /// Loading of indexes
                loadIndexes(connection, tablePrefix, schemaPattern);
                /// Loading of sequences
                loadSequences(connection, tablePrefix, schemaPattern);
                /// Load columns info
                columnHM = getTabColumns(connection, tablePrefix, schemaPattern);
                /// Loading of tables
                dbMetaData = connection.getMetaData();
                String databaseName = dbMetaData.getDatabaseProductName();
                String[] tableNamePatternes = tableNamePattern.split(",");
                Set<String> tableNames = new TreeSet<String>();

                String sqlTableNameCond = "";
                for (int i = 0; tableNamePatternes != null && i < tableNamePatternes.length; i++) {
                    if (i == 0) {
                        sqlTableNameCond = sqlTableNameCond + " WHERE ";
                        if (tableNamePatternes.length > 1) {
                            sqlTableNameCond = sqlTableNameCond + "( ";
                        }
                    } else {
                        sqlTableNameCond = sqlTableNameCond + " OR ";
                    }
                    sqlTableNameCond = sqlTableNameCond +
                                       "table_name LIKE '" + tableNamePatternes[i] + "%' ";

                    if (i == tableNamePatternes.length - 1 && tableNamePatternes.length > 1) {
                            sqlTableNameCond = sqlTableNameCond + " )";
                    }
                }
                if ("EnterpriseDB".equals(databaseName)) {
                    sqlTableNameCond = sqlTableNameCond + " AND schemaname = '" + schemaPattern + "'";
                }

                String sql = "SELECT TABLE_NAME FROM all_tables" +  sqlTableNameCond;

    			System.out.println("===GCJavaObjectsLoaderFromEnterpriseDB sql: " + sql);

                PreparedStatement tabPS = connection.prepareStatement(sql);
                ResultSet tabSet = tabPS.executeQuery();

                while (tabSet.next()) {
                    tableName = tabSet.getString("TABLE_NAME");
                    tableNames.add(tableName);
                }

                if (tableNames.size() > 0) {
                    Iterator<String> it = tableNames.iterator();
                    while (it.hasNext()) {
                        tableName = it.next();

                        /// Definition of table
                        GCTable table = new GCTable();
                        table.setName(tableName);

                        System.out.println("[GCJavaObjectsLoaderFromEnterpriseDB] Processing of table: " + tableName);

                        /// Definition of primary keys
                        sql = "SELECT DISTINCT col.CONSTRAINT_NAME, col.COLUMN_NAME, col.POSITION " +
                              "FROM all_cons_columns col, all_constraints con " +
                              "WHERE col.constraint_name = con.constraint_name AND " +
                              "con.schema_name = col.schema_name AND " +
                              "col.schema_name = '" + schemaPattern +
                              "' AND col.table_name = '" + tableName + "' AND con.constraint_type = 'P'";
//                         System.out.println("GCJavaObjectsLoaderFromOracle sql: " + sql);

                         PreparedStatement primaryKeyPS = connection.prepareStatement(sql);
                         primaryKeysSet = primaryKeyPS.executeQuery();

//                         primaryKeysSet = dbMetaData.getPrimaryKeys(null, schemaPattern, tableName);
                        if (primaryKeysSet != null) {
                            GCPrimaryKey primaryKey = new GCPrimaryKey();
//                            System.out.println("===: " + tableName);
                            while (primaryKeysSet.next()) {
                                primaryKey.addColumnRef(new GCPrimaryKeyColumnRef(
                                    primaryKeysSet.getString("COLUMN_NAME"),
                                    1));
//                                    primaryKeysSet.getInt("KEY_SEQ")));
//                                    primaryKeysSet.getInt("POSITION")));
//                                System.out.println("columnName: " + primaryKeysSet.getString("COLUMN_NAME") + ":" + primaryKeysSet.getInt("POSITION"));
                                if (GCUtils.IsEmptyString(primaryKey.getName())) {
//                                    primaryKey.setName(primaryKeysSet.getString("PK_NAME"));
                                    primaryKey.setName(primaryKeysSet.getString("CONSTRAINT_NAME"));
//                                    System.out.println("===CONSTRAINT_NAME: " + primaryKeysSet.getString("CONSTRAINT_NAME"));
                                }
                            }
//                            System.out.println(tableName + " - PK: " + primaryKey.getName());
//                        	for (int i = 0; i < primaryKey.getColumnRefCount(); i++) {
//                            	System.out.println("1===" + i + ": " + primaryKey.getColumnRef(i).getColumnName());
//                        	}

                            table.setPrimaryKey(primaryKey);
                            primaryKeysSet.close();
                        }

                        /// Definition of columns
//                        if ("EnterpriseDB".equals(databaseName)) {
//                            tableName = tableName.toUpperCase();
//                        }
                        

                        LinkedList<GCField> tabColumns = columnHM.get(tableName);
    					if(tabColumns!=null) {
    						for(GCField field : tabColumns) {
    							field.setIsPrimaryKey(table.isColumnPrimaryKey(field.getName()));
    							table.addField(field);
    						}
    					}

                        ///
                        _javaObjects.getTables().add(table);

                    }
                }
                loadForeignKeys(connection, tableNamePattern, schemaPattern);

            } catch (GCException ex) {
                throw ex;
            } catch (Exception ex) {
    			ex.printStackTrace();
                throw new GCException("Exception: " + ex.getMessage(),
                    "GCJavaObjectsLoaderFromEnterpriseDB.loadObjects");
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }
    
    

	private HashMap getTabColumns(Connection conn, String tablePrefix, String schemaPattern)
	throws java.sql.SQLException
	{
		HashMap<String,LinkedList> hm = new HashMap<String,LinkedList>();

		String[] prefixes = tablePrefix.split(",", 0);
		String sqlTableNameCond = "";
		for (int i = 0; i < prefixes.length; i++) {
			sqlTableNameCond = sqlTableNameCond +
				"OR table_name LIKE '"+prefixes[i]+"%' ";
		}
		sqlTableNameCond = sqlTableNameCond.substring(3);

		String sql = "SELECT "+
			"TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, "+
			"DATA_SCALE, NULLABLE, COLUMN_ID "+
            "FROM all_tab_columns "+
			"WHERE " + sqlTableNameCond + " "+
			"AND schemaname = '"+schemaPattern+"'";
 System.out.println("CCOOLL sql: " + sql);

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
                        if("NUMBER".equals(dataType)) {
                                dataLength = columnsSet.getInt("DATA_PRECISION");
                            }
                            field.setSize(dataLength);
                            field.setDigits(columnsSet.getInt("DATA_SCALE"));
            if("VARCHAR2".equals(dataType) | "CHAR".equals(dataType) | "NVARCHAR2".equals(dataType) | "NCHAR".equals(dataType)) {
                field.setCharUsed("C");
            }
            String tableName = columnsSet.getString("TABLE_NAME");
            System.out.println("@tableName: " + tableName);
            LinkedList ll = hm.get(tableName);
            if(ll == null) {
                ll = new LinkedList();
                hm.put(tableName,ll);
            }
            ll.add(field);
        }
        columnsSet.close();
//System.out.println("CCCCCCC columnHM: " + hm);		
        return hm;
	}

////////////////////////

    private void setClassSerialUidForTables(ArrayList<GCTable> tables,
        String prefix, String classSerialUidProperties) throws GCException {
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
                tableJavaName = GCUtils.GetJavaNameByDbName(table.getName(), prefix);
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
}

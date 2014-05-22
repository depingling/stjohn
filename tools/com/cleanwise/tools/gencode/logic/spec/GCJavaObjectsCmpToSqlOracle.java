/**
 * Title:        GCJavaObjectsCmpToSqlOracle
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */
package com.cleanwise.tools.gencode.logic.spec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.io.StringReader;
import java.io.StringWriter;

import com.cleanwise.tools.gencode.GCCodeNames;
import com.cleanwise.tools.gencode.GCException;
import com.cleanwise.tools.gencode.GCJavaObjects;
import com.cleanwise.tools.gencode.items.GCField;
import com.cleanwise.tools.gencode.items.GCForeignKey;
import com.cleanwise.tools.gencode.items.GCForeignKeyComparator;
import com.cleanwise.tools.gencode.items.GCIndex;
import com.cleanwise.tools.gencode.items.GCIndexComparator;
import com.cleanwise.tools.gencode.items.GCPrimaryKey;
import com.cleanwise.tools.gencode.items.GCPrimaryKeyComparator;
import com.cleanwise.tools.gencode.items.GCSequence;
import com.cleanwise.tools.gencode.items.GCTable;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsCmpToSql;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsExpToSql;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsExpToSqlFactory;
import com.cleanwise.tools.gencode.logic.spec.GCOracleUtils;
import com.cleanwise.tools.gencode.utils.GCUtils;

public class GCJavaObjectsCmpToSqlOracle implements GCJavaObjectsCmpToSql {
	
    //String[] logColumnList = {"LOG_DATE_MS","LOG_DATE","ACTION","STATUS"};
	ArrayList<String> logColumnList = new ArrayList<String>();
	String logPrefix ="";
	ArrayList<String> tablePrefixes= new ArrayList<String>();
	
    public StringReader compareAndBuildSql(Properties settings,
        GCJavaObjects javaObjects1, GCJavaObjects javaObjects2, GCJavaObjects javaLogObjects)
        throws GCException {

        /// Properties
        String schemaPattern = "";
        String tableSpace = "";
        
        if (settings != null) {
            schemaPattern = (String) settings.get(GCCodeNames.Property.DATABASE_SCHEMA_PATTERN);
            tableSpace = (String) settings.get(GCCodeNames.Property.DATABASE_TABLESPACE);
            
            if((String)settings.get(GCCodeNames.Property.LOG_TABLE_PREFIX)!=null){
            	logPrefix = (String)  settings.get(GCCodeNames.Property.LOG_TABLE_PREFIX);
            }
            
            int count = new Integer((String)settings.get(GCCodeNames.Property.NO_OF_DATABASE_TABLE_PREFIXES)).intValue();
            for(int c=0; c<count; c++){
            	String prefix = (String)settings.get(GCCodeNames.Property.DATABASE_TABLE_PREFIX+"_"+c);
            	tablePrefixes.add(prefix);
            }
        }
        
        //custom columns for log tables
        logColumnList.add("LOG_DATE_MS");
    	logColumnList.add("LOG_DATE");
    	logColumnList.add("ACTION");
    	logColumnList.add("STATUS");

        System.out.println("#########################################################");
        System.out.println("# [GCJavaObjectsCmpToSqlOracle] PROPERTIES");
        System.out.println("# " + GCCodeNames.Property.DATABASE_SCHEMA_PATTERN + ": " + schemaPattern);
        System.out.println("# " + GCCodeNames.Property.DATABASE_TABLESPACE + ": " + tableSpace);
        System.out.println("#########################################################");

        if (javaObjects1 == null && javaObjects2 == null) {
            return new StringReader("");
        }
        if (javaObjects1 == null) {
            GCJavaObjectsExpToSqlFactory factory = GCJavaObjectsExpToSqlFactory.GetInstance();
            GCJavaObjectsExpToSql exporter = factory.getJavaObjectsExpToSql(GCCodeNames.ExpToSql.TO_DB_ORACLE);
            return exporter.exportToSql(settings, javaObjects2);
        }
        if (javaObjects2 == null) {
            GCJavaObjectsExpToSqlFactory factory = GCJavaObjectsExpToSqlFactory.GetInstance();
            GCJavaObjectsExpToSql exporter = factory.getJavaObjectsExpToSql(GCCodeNames.ExpToSql.TO_DB_ORACLE);
            return exporter.exportToSql(settings, javaObjects1);
        }

        StringReader inputStream = null;
        try {
            StringWriter outputStream = new StringWriter();

            compareExistenceTables(outputStream, schemaPattern, tableSpace,
                javaObjects1.getTables(), javaObjects2.getTables());

            if(javaLogObjects!=null){
            	compareTablesByColumns(outputStream, schemaPattern,
            			javaObjects1.getTables(), javaObjects2.getTables(),javaLogObjects.getTables());
            }else{
            	compareTablesByColumns(outputStream, schemaPattern,
            			javaObjects1.getTables(), javaObjects2.getTables(),null);
            }

            compareTablesByPrimaryKeys(outputStream, schemaPattern,
                javaObjects1.getTables(), javaObjects2.getTables());

            compareArraySequences(outputStream, schemaPattern,
                javaObjects1.getSequences(), javaObjects2.getSequences());

            compareArrayIndexes(outputStream, schemaPattern,
                javaObjects1.getIndexes(), javaObjects2.getIndexes());

            compareArrayForeignKeys(outputStream, schemaPattern,
                javaObjects1.getForeignKeys(), javaObjects2.getForeignKeys());

            outputStream.flush();
            inputStream = new StringReader(outputStream.getBuffer().toString());
            outputStream.close();
        } catch (GCException ex) {
        	ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCJavaObjectsCmpToSqlOracle.compareAndBuildSql");
        }
        return inputStream;
    }

    private void compareExistenceTables(StringWriter outputStream,
        String schemaPattern, String tableSpace, ArrayList<GCTable> tables1,
        ArrayList<GCTable> tables2)
        throws GCException {
        if (tables1 == null) {
            System.out.println("[GCJavaObjectsCmpToSqlOracle] tables1 is null");
            return;
        }
        GCTable table1 = null;
        GCTable table2 = null;
        if (tables2 == null) {
            for (int i = 0; i < tables1.size(); ++i) {
                table1 = tables1.get(i);
                GCOracleUtils.WriteSqlToCreateTable(outputStream, table1, schemaPattern, tableSpace);
                //create log table
                if(table1.getLog()!=null && table1.getLog().equalsIgnoreCase("true")){
                	GCTable logTable = table1;
                	logTable.setName(logPrefix+table1.getName());
                	GCOracleUtils.WriteSqlToCreateTable(outputStream, logTable, schemaPattern, tableSpace);
                }
            }
            return;
        }
        boolean isTableToCreate = false;
        for (int i = 0; i < tables1.size(); ++i) {
            table1 = tables1.get(i);
            isTableToCreate = true;
            for (int j = 0; j < tables2.size(); ++j) {
                table2 = tables2.get(j);
                if (table1.getName().equalsIgnoreCase(table2.getName())) {
                    isTableToCreate = false;
                    break;
                }
            }
            if (isTableToCreate) {
                GCOracleUtils.WriteSqlToCreateTable(outputStream, table1, schemaPattern, tableSpace);
                
                if(table1.getLog()!=null && table1.getLog().equalsIgnoreCase("true")){
                	GCTable logTable = table1;
                	logTable.setName(logPrefix+table1.getName());
                	GCOracleUtils.WriteSqlToCreateTable(outputStream, logTable, schemaPattern, tableSpace);
                }
            }
        }
        boolean isTableToDelete = false;
        for (int i = 0; i < tables2.size(); ++i) {
            table2 = tables2.get(i);
            isTableToDelete = true;
            for (int j = 0; j < tables1.size(); ++j) {
                table1 = tables1.get(j);
                if (table2.getName().equalsIgnoreCase(table1.getName())) {
                    isTableToDelete = false;
                    break;
                }
            }
            if (isTableToDelete) {
                GCOracleUtils.WriteSqlToDeleteTable(outputStream, table2, schemaPattern);
            }
        }
    }

    private void compareTablesByColumns(StringWriter outputStream,
        String schemaPattern, ArrayList<GCTable> tables1,
        ArrayList<GCTable> tables2, ArrayList<GCTable> logTables)
        throws GCException {
        if (tables1 == null || tables2 == null) {
            return;
        }
        GCTable table1 = null;
        GCTable table2 = null;
        for (int i = 0; i < tables1.size(); ++i) {
            table1 = tables1.get(i);
            for (int j = 0; j < tables2.size(); ++j) {
                table2 = tables2.get(j);
                if (table1.getName().equalsIgnoreCase(table2.getName())) {
                    compareTablesWithIdenticalNameByColumns(outputStream, schemaPattern, table1, table2);
                    break;
                }
            }
            
            //compare with log table if exists
            if(table1.getLog()!=null && table1.getLog().equalsIgnoreCase("true")){
            	if(logTables!=null && !logTables.isEmpty()){
	            	Iterator<GCTable> it = logTables.iterator();
	            	while(it.hasNext()){
	            		GCTable lTable = (GCTable)it.next();   
	            		if((logPrefix+table1.getName()).equalsIgnoreCase(lTable.getName())){    
	            			compareTablesWithIdenticalNameByColumns(outputStream, schemaPattern, table1,lTable);
	            		}
	            	}  
            	}
            }
        }
    }

    private void compareTablesByPrimaryKeys(StringWriter outputStream,
        String schemaPattern, ArrayList<GCTable> tables1,
        ArrayList<GCTable> tables2)
        throws GCException {
        if (tables1 == null || tables2 == null) {
            return;
        }
        GCTable table1 = null;
        GCTable table2 = null;
        for (int i = 0; i < tables1.size(); ++i) {
            table1 = tables1.get(i);
            for (int j = 0; j < tables2.size(); ++j) {
                table2 = tables2.get(j);
                if (table1.getName().equalsIgnoreCase(table2.getName())) {
                    comparePrimaryKeys(outputStream, schemaPattern, table1.getName(), table1.getPrimaryKey(), table2.getPrimaryKey());
                    break;
                }
            }
        }
    }

    private void comparePrimaryKeys(StringWriter outputStream,
        String schemaPattern, String tableName, GCPrimaryKey primaryKey1,
        GCPrimaryKey primaryKey2) throws GCException {
        if (outputStream == null || tableName == null) {
            return;
        }
        if (primaryKey1 == null && primaryKey2 == null) {
            return;
        }
        if (primaryKey1 != null && primaryKey2 == null) {
            if (primaryKey1.getName() != null) {
                GCOracleUtils.WriteSqlToCreatePrimaryKey(outputStream, primaryKey1, tableName, schemaPattern);
            }
            return;
        }
        if (primaryKey1 == null && primaryKey2 != null) {
            if (primaryKey2.getName() != null) {
                GCOracleUtils.WriteSqlToDeletePrimaryKey(outputStream, primaryKey2, tableName, schemaPattern);
            }
            return;
        }
        int compareRes = 0;
        if (GCUtils.IsEmptyString(primaryKey1.getName())) {
            GCPrimaryKeyComparator comparator = new GCPrimaryKeyComparator(GCPrimaryKeyComparator.COMPARE_BY_COLUMNS);
            compareRes = comparator.compare(primaryKey1, primaryKey2);
        } else {
            GCPrimaryKeyComparator comparator = new GCPrimaryKeyComparator(GCPrimaryKeyComparator.COMPARE_BY_ALL_FIELDS);
            compareRes = comparator.compare(primaryKey1, primaryKey2);
        }
        if (compareRes != 0 ) {
          if (!GCUtils.IsEmptyString(primaryKey1.getName()) &&
              !GCUtils.IsEmptyString(primaryKey2.getName())) {
            GCOracleUtils.WriteSqlToRenamePrimaryKey(outputStream,  primaryKey2, primaryKey1, tableName, schemaPattern);
         } else {
            GCOracleUtils.WriteSqlToDeletePrimaryKey(outputStream, primaryKey2, tableName, schemaPattern);
            GCOracleUtils.WriteSqlToCreatePrimaryKey(outputStream, primaryKey1, tableName, schemaPattern);
         }
        }
    }

    private void compareArraySequences(StringWriter outputStream,
        String schemaPattern, ArrayList<GCSequence> sequences1,
        ArrayList<GCSequence> sequences2)
        throws GCException {
        if (sequences1 == null) {
            System.out.println("[compareArraySequences] sequences1 is null");
            return;
        }
        GCSequence sequence1 = null;
        GCSequence sequence2 = null;
        if (sequences2 == null) {
            for (int i = 0; i < sequences1.size(); ++i) {
                sequence1 = sequences1.get(i);
                GCOracleUtils.WriteSqlToCreateSequence(outputStream, sequence1, schemaPattern);
            }
            return;
        }
        boolean isSequenceToCreate = false;
        for (int i = 0; i < sequences1.size(); ++i) {
            sequence1 = sequences1.get(i);
            isSequenceToCreate = true;
            for (int j = 0; j < sequences2.size(); ++j) {
                sequence2 = sequences2.get(j);
                if (sequence1.getName().equalsIgnoreCase(sequence2.getName())) {
                    isSequenceToCreate = false;
                    break;
                }
            }
            if (isSequenceToCreate) {
                GCOracleUtils.WriteSqlToCreateSequence(outputStream, sequence1, schemaPattern);
            }
        }
        
        boolean isSequenceToDelete = false;
        
        
	        for (int i = 0; i < sequences2.size(); ++i) {
	            sequence2 = sequences2.get(i);
	            if(!sequence2.getName().startsWith("CLW_ORDER_SEQUENCE_")){
		            isSequenceToDelete = true;
		            for (int j = 0; j < sequences1.size(); ++j) {
		                sequence1 = sequences1.get(j);
		                if (sequence2.getName().equalsIgnoreCase(sequence1.getName())) {
		                    isSequenceToDelete = false;
		                    break;
		                }
		            }
		            if (isSequenceToDelete) {
		                GCOracleUtils.WriteSqlToDeleteSequence(outputStream, sequence2, schemaPattern);
		            }
	            }
	        }
        
    }
    
    private void compareTablesWithIdenticalNameByColumns(StringWriter outputStream,
        String schemaPattern, GCTable table1, GCTable table2) throws GCException {
        if (outputStream == null || table1 == null || table2 == null) {
            return;
        }
        
        String tableName1 = table1.getName();
        String tableName2 = table2.getName();
        
        boolean isLogTable = false;
        Iterator it = tablePrefixes.iterator();
        String token = "";
        while(it.hasNext()){
        	String prefix = (String)it.next();
        	if(tableName1.startsWith(prefix)){
        		token = prefix;
        	}
        	
        }
        if(token != null && token.trim().length()>0){
        
        	if(tableName2.startsWith((logPrefix+token))){
        		isLogTable = true;
        	}
        }
        
        if (!GCUtils.IsEqualsIgnoreCaseStrings(table1.getName(), table2.getName())
        		&& !(isLogTable)) {
            return;
        }
        

        /// Comparison of columns
        final int columnCount1 = table1.getFieldCount();
        final int columnCount2 = table2.getFieldCount();
        GCField column1 = null;
        GCField column2 = null;

        boolean isColumnToDelete = false;
        for (int i = 0; i < columnCount2; ++i) {
            column2 = table2.getField(i);
            
            if(isLogTable){
            	
	            if(logColumnList.contains(column2.getName())){	            	
	            	continue;
	            }
            }
            
            isColumnToDelete = true;
            for (int j = 0; j < columnCount1; ++j) {
                column1 = table1.getField(j);
                
                if (column2.getName().equalsIgnoreCase(column1.getName())) {
                    isColumnToDelete = false;
                    break;
                }            
            }
            if (isColumnToDelete) {
                GCOracleUtils.WriteSqlToDeleteColumn(outputStream, column2, tableName2, schemaPattern);
            }
        }

        ArrayList<GCField> columnsToCreate = new ArrayList<GCField>();
        boolean isColumnToCreate = false;
        for (int i = 0; i < columnCount1; ++i) {
            column1 = table1.getField(i);
            isColumnToCreate = true;
            for (int j = 0; j < columnCount2; ++j) {
                column2 = table2.getField(j);
                if (column1.getName().equalsIgnoreCase(column2.getName())) {
                    isColumnToCreate = false;
                    break;
                }
            }
            if (isColumnToCreate) {
                columnsToCreate.add(column1);
            }
        }
        if (columnsToCreate.size() > 0) {
        	if(table1.getLog()!=null && table1.getLog().equalsIgnoreCase("true") &&
        			isLogTable){
        		
        		GCOracleUtils.WriteSqlToCreateColumns(outputStream, columnsToCreate, tableName2, schemaPattern);
        		
        	}else{
        		GCOracleUtils.WriteSqlToCreateColumns(outputStream, columnsToCreate, tableName1, schemaPattern);
        	}
        }

        for (int i = 0; i < columnCount1; ++i) {
            column1 = table1.getField(i);
            for (int j = 0; j < columnCount2; ++j) {
                column2 = table2.getField(j);
                if (column1.getName().equalsIgnoreCase(column2.getName())) {
                	compareColumnsWithIdenticalName(outputStream, schemaPattern, tableName2, column1, column2);
                    break;
                }
            }
        }
    }

    private void compareColumnsWithIdenticalName(StringWriter outputStream,
        String schemaPattern, String tableName, GCField column1,
        GCField column2) throws GCException {
        if (outputStream == null || tableName == null ||
            column1 == null || column2 == null) {
            return;
        }
        if (!GCUtils.IsEqualsIgnoreCaseStrings(column1.getName(), column2.getName())) {
            return;
        }
        if (!column1.getType().equalsIgnoreCase(column2.getType())) {
            GCOracleUtils.WriteSqlToDeleteColumn(outputStream, column2, tableName, schemaPattern);
            GCOracleUtils.WriteSqlToCreateColumn(outputStream, column1, tableName, schemaPattern);
            return;
        }
		if(!"VARCHAR2".equalsIgnoreCase(column2.getType())) {
			if ((column1.getSize() > 0 && column1.getSize() != column2.getSize()) ||
				column1.getDigits() != column2.getDigits()) {
				GCOracleUtils.WriteSqlToUpdateColumnType(outputStream, column1, tableName, schemaPattern);
			}
		} else {
			if (column1.getSize() != column2.getSize() || !"C".equalsIgnoreCase(column2.getCharUsed())) {
				GCOracleUtils.WriteSqlToUpdateColumnType(outputStream, column1, tableName, schemaPattern);
			}			
		}
        if (column1.getIsNullable() != column2.getIsNullable()) {
            GCOracleUtils.WriteSqlToUpdateNullable(outputStream, column1, tableName, schemaPattern);
        }
    }

    private void compareArrayForeignKeys(StringWriter outputStream,
        String schemaPattern, ArrayList<GCForeignKey> foreignKeys1,
        ArrayList<GCForeignKey> foreignKeys2)
        throws GCException {
        if (foreignKeys1 == null) {
            System.out.println("[GCJavaObjectsCmpToSqlOracle] foreignKeys1 is null");
            return;
        }
        GCForeignKey foreignKey1 = null;
        GCForeignKey foreignKey2 = null;
        if (foreignKeys2 == null) {
            for (int i = 0; i < foreignKeys1.size(); ++i) {
                foreignKey1 = foreignKeys1.get(i);
                GCOracleUtils.WriteSqlToCreateForeignKey(outputStream, foreignKey1, schemaPattern);
            }
            return;
        }

        boolean isForeignKeyToCreate = false;
        for (int i = 0; i < foreignKeys1.size(); ++i) {
            foreignKey1 = foreignKeys1.get(i);
            isForeignKeyToCreate = true;
            for (int j = 0; j < foreignKeys2.size(); ++j) {
                foreignKey2 = foreignKeys2.get(j);
                if (foreignKey1.getName().equalsIgnoreCase(foreignKey2.getName())) {
                    isForeignKeyToCreate = false;
                    compareForeignKeysWithIdenticalName(outputStream, schemaPattern, foreignKey1, foreignKey2);
                    break;
                }
            }
            if (isForeignKeyToCreate) {
                GCOracleUtils.WriteSqlToCreateForeignKey(outputStream, foreignKey1, schemaPattern);
            }
        }

        boolean isForeignKeyToDelete = false;
        for (int i = 0; i < foreignKeys2.size(); ++i) {
            foreignKey2 = foreignKeys2.get(i);
            isForeignKeyToDelete = true;
            for (int j = 0; j < foreignKeys1.size(); ++j) {
                foreignKey1 = foreignKeys1.get(j);
                if (foreignKey2.getName().equalsIgnoreCase(foreignKey1.getName())) {
                    isForeignKeyToDelete = false;
                    break;
                }
            }
            if (isForeignKeyToDelete) {
                GCOracleUtils.WriteSqlToDeleteForeignKey(outputStream, foreignKey2, schemaPattern);
            }
        }
    }

    private void compareForeignKeysWithIdenticalName(StringWriter outputStream,
        String schemaPattern, GCForeignKey foreignKey1,
        GCForeignKey foreignKey2) throws GCException {
        if (outputStream == null || foreignKey1 == null || foreignKey2 == null) {
            return;
        }
        if (!GCUtils.IsEqualsIgnoreCaseStrings(foreignKey1.getName(), foreignKey2.getName())) {
            return;
        }
        GCForeignKeyComparator comparator = new GCForeignKeyComparator();
        int compareRes = comparator.compare(foreignKey1, foreignKey2);
        if (compareRes != 0) {
            GCOracleUtils.WriteSqlToDeleteForeignKey(outputStream, foreignKey2, schemaPattern);
            GCOracleUtils.WriteSqlToCreateForeignKey(outputStream, foreignKey1, schemaPattern);
        }
    }

    private void compareArrayIndexes(StringWriter outputStream,
        String schemaPattern, ArrayList<GCIndex> indexes1,
        ArrayList<GCIndex> indexes2)
        throws GCException {
        if (indexes1 == null) {
            System.out.println("[GCJavaObjectsCmpToSqlOracle] indexes1 is null");
            return;
        }
        GCIndex index1 = null;
        GCIndex index2 = null;
        if (indexes2 == null) {
            for (int i = 0; i < indexes1.size(); ++i) {
                index1 = indexes1.get(i);
                GCOracleUtils.WriteSqlToCreateIndex(outputStream, index1, schemaPattern);
            }
            return;
        }
        boolean isIndexToCreate = false;
        for (int i = 0; i < indexes1.size(); ++i) {
            index1 = indexes1.get(i);
            isIndexToCreate = true;
            for (int j = 0; j < indexes2.size(); ++j) {
                index2 = indexes2.get(j);
                if (index1.getName().equalsIgnoreCase(index2.getName())) {
                    isIndexToCreate = false;
                    compareIndexesWithIdenticalName(outputStream, schemaPattern, index1, index2);
                    break;
                }
            }
            if (isIndexToCreate) {
                GCOracleUtils.WriteSqlToCreateIndex(outputStream, index1, schemaPattern);
            }
        }
        boolean isIndexToDelete = false;
        for (int i = 0; i < indexes2.size(); ++i) {
            index2 = indexes2.get(i);
            isIndexToDelete = true;
            for (int j = 0; j < indexes1.size(); ++j) {
                index1 = indexes1.get(j);
                if (index2.getName().equalsIgnoreCase(index1.getName())) {
                    isIndexToDelete = false;
                    break;
                }
            }
            if (isIndexToDelete) {
                GCOracleUtils.WriteSqlToDeleteIndex(outputStream, index2, schemaPattern);
            }
        }
    }

    private void compareIndexesWithIdenticalName(StringWriter outputStream,
        String schemaPattern, GCIndex index1, GCIndex index2)
        throws GCException {
        if (outputStream == null || index1 == null || index2 == null) {
            return;
        }
        if (!GCUtils.IsEqualsIgnoreCaseStrings(index1.getName(), index2.getName())) {
            return;
        }
        GCIndexComparator comparator = new GCIndexComparator(GCIndexComparator.COMPARE_BY_NAME_COLUMNS);
        int compareRes = comparator.compare(index1, index2);
        if (compareRes != 0) {
            GCOracleUtils.WriteSqlToDeleteIndex(outputStream, index2, schemaPattern);
            GCOracleUtils.WriteSqlToCreateIndex(outputStream, index1, schemaPattern);
        }
    }
}

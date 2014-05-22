/**
 * Title:        GCJavaObjectsCmpToSqlEnterpriseDB
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */
package com.cleanwise.tools.gencode.logic.spec;

import java.util.ArrayList;
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
import com.cleanwise.tools.gencode.logic.GCJavaObjectsCmpToSql1;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsExpToSql;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsExpToSqlFactory;
import com.cleanwise.tools.gencode.utils.GCUtils;

public class GCJavaObjectsCmpToSqlEnterpriseDB implements GCJavaObjectsCmpToSql1 {

    public StringReader compareAndBuildSql(Properties settings,
        GCJavaObjects javaObjects1, GCJavaObjects javaObjects2)
        throws GCException {

        /// Properties
        String schemaPattern = "";
        String tableSpace = "";
        String dbDriver = "";
        if (settings != null) {
            schemaPattern = (String) settings.get(GCCodeNames.Property.DATABASE_SCHEMA_PATTERN);
            tableSpace = (String) settings.get(GCCodeNames.Property.DATABASE_TABLESPACE);
            dbDriver = (String) settings.get(GCCodeNames.Property.DATABASE_DRIVER);
        }

        System.out.println("#########################################################");
        System.out.println("# [GCJavaObjectsCmpToSqlEnterpriseDB] PROPERTIES:");
        System.out.println("# " + GCCodeNames.Property.DATABASE_SCHEMA_PATTERN + ": " + schemaPattern);
        System.out.println("# " + GCCodeNames.Property.DATABASE_TABLESPACE + ": " + tableSpace);
        System.out.println("#########################################################");

        System.out.println("javaObjects1 = " + javaObjects1);
        System.out.println("javaObjects2 = " + javaObjects2);
        
        if (javaObjects1 == null && javaObjects2 == null) {
            return new StringReader("");
        }
        if (javaObjects1 == null) {
            GCJavaObjectsExpToSqlFactory factory = GCJavaObjectsExpToSqlFactory.GetInstance();
            GCJavaObjectsExpToSql exporter = factory.getJavaObjectsExpToSql(GCCodeNames.ExpToSql.TO_DB_ENTERPRISEDB);
            return exporter.exportToSql(settings, javaObjects2);
        }
        if (javaObjects2 == null) {
            GCJavaObjectsExpToSqlFactory factory = GCJavaObjectsExpToSqlFactory.GetInstance();
            GCJavaObjectsExpToSql exporter = factory.getJavaObjectsExpToSql(GCCodeNames.ExpToSql.TO_DB_ENTERPRISEDB);
            return exporter.exportToSql(settings, javaObjects1);
        }

        StringReader inputStream = null;
        try {
            StringWriter outputStream = new StringWriter();

            System.out.println("===compareExistenceTables()");
            ArrayList<GCTable> gcObjects1 = javaObjects1.getTables();
            ArrayList<GCTable> gcObjects2 = javaObjects2.getTables();            
            for (int i1=0; i1<gcObjects1.size(); i1++) {
                GCTable gcTable1 = (GCTable) gcObjects1.get(i1); 
                System.out.println("gcTable1.getName() = " + gcTable1.getName());   
            }
            for (int i2=0; i2<gcObjects2.size(); i2++) {
                GCTable gcTable2 = (GCTable) gcObjects2.get(i2); 
                System.out.println("gcTable2.getName() = " + gcTable2.getName());   
            }
            
            compareExistenceTables(outputStream, schemaPattern, tableSpace,
                javaObjects1.getTables(), javaObjects2.getTables());

            System.out.println("===compareTablesByColumns()");
            compareTablesByColumns(outputStream, schemaPattern,
                javaObjects1.getTables(), javaObjects2.getTables());

            compareTablesByPrimaryKeys(outputStream, schemaPattern,
                javaObjects1.getTables(), javaObjects2.getTables());

            compareArraySequences(outputStream, schemaPattern,
                javaObjects1.getSequences(), javaObjects2.getSequences());

            compareArrayIndexes(outputStream, schemaPattern,
                javaObjects1.getIndexes(), javaObjects2.getIndexes(), dbDriver);

            compareArrayForeignKeys(outputStream, schemaPattern,
                javaObjects1.getForeignKeys(), javaObjects2.getForeignKeys());

            outputStream.flush();
            inputStream = new StringReader(outputStream.getBuffer().toString());
            outputStream.close();
        } catch (GCException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCJavaObjectsCmpToSqlEnterpriseDB.compareAndBuildSql");
        }
        return inputStream;
    }

    private void compareExistenceTables(StringWriter outputStream,
        String schemaPattern, String tableSpace, ArrayList<GCTable> tables1,
        ArrayList<GCTable> tables2)
        throws GCException {
        if (tables1 == null) {
            System.out.println("[GCJavaObjectsCmpToSqlEnterpriseDB] tables1 is null");
            return;
        }
        GCTable table1 = null;
        GCTable table2 = null;
        if (tables2 == null) {
            for (int i = 0; i < tables1.size(); ++i) {
                table1 = tables1.get(i);
                GCEnterpriseDBUtils.WriteSqlToCreateTable(outputStream, table1, schemaPattern, tableSpace);
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
                GCEnterpriseDBUtils.WriteSqlToCreateTable(outputStream, table1, schemaPattern, tableSpace);
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
                GCEnterpriseDBUtils.WriteSqlToDeleteTable(outputStream, table2, schemaPattern);
            }
        }
    }

    private void compareTablesByColumns(StringWriter outputStream,
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
                    compareTablesWithIdenticalNameByColumns(outputStream, schemaPattern, table1, table2);
                    break;
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
                GCEnterpriseDBUtils.WriteSqlToCreatePrimaryKey(outputStream, primaryKey1, tableName, schemaPattern);
            }
            return;
        }
        if (primaryKey1 == null && primaryKey2 != null) {
            if (primaryKey2.getName() != null) {
                GCEnterpriseDBUtils.WriteSqlToDeletePrimaryKey(outputStream, primaryKey2, tableName, schemaPattern);
            }
            return;
        }
        int compareRes = 0;
        if (GCUtils.IsEmptyString(primaryKey1.getName())) {
            GCPrimaryKeyComparator comparator = new GCPrimaryKeyComparator(GCPrimaryKeyComparator.COMPARE_BY_COLUMNS);
            compareRes = comparator.compare(primaryKey1, primaryKey2);
//            System.out.println(" - Step1");
        } else {
            GCPrimaryKeyComparator comparator = new GCPrimaryKeyComparator(GCPrimaryKeyComparator.COMPARE_BY_ALL_FIELDS);
            compareRes = comparator.compare(primaryKey1, primaryKey2);
//            System.out.println(" - Step2.1. primaryKey1: " + primaryKey1.getName() + ";compareRes: " + compareRes);
//            System.out.println(" - Step2.2. primaryKey2: " + primaryKey2.getName() + ";compareRes: " + compareRes);
        }
        if (compareRes != 0 ) {
//          if (!GCUtils.IsEmptyString(primaryKey1.getName()) &&
//              !GCUtils.IsEmptyString(primaryKey2.getName())) {
//                GCEnterpriseDBUtils.WriteSqlToRenamePrimaryKey(outputStream, primaryKey2, primaryKey1, tableName, schemaPattern);
//         } else {
            GCEnterpriseDBUtils.WriteSqlToDeletePrimaryKey(outputStream, primaryKey2, tableName, schemaPattern);
            GCEnterpriseDBUtils.WriteSqlToCreatePrimaryKey(outputStream, primaryKey1, tableName, schemaPattern);
//         }
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
                GCEnterpriseDBUtils.WriteSqlToCreateSequence(outputStream, sequence1, schemaPattern);
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
                GCEnterpriseDBUtils.WriteSqlToCreateSequence(outputStream, sequence1, schemaPattern);
            }
        }
        boolean isSequenceToDelete = false;
        for (int i = 0; i < sequences2.size(); ++i) {
            sequence2 = sequences2.get(i);
            isSequenceToDelete = true;
            for (int j = 0; j < sequences1.size(); ++j) {
                sequence1 = sequences1.get(j);
                if (sequence2.getName().equalsIgnoreCase(sequence1.getName())) {
                    isSequenceToDelete = false;
                    break;
                }
            }
            if (isSequenceToDelete) {
                GCEnterpriseDBUtils.WriteSqlToDeleteSequence(outputStream, sequence2, schemaPattern);
            }
        }
    }

    private void compareTablesWithIdenticalNameByColumns(StringWriter outputStream,
        String schemaPattern, GCTable table1, GCTable table2) throws GCException {
        if (outputStream == null || table1 == null || table2 == null) {
            return;
        }
        if (!GCUtils.IsEqualsIgnoreCaseStrings(table1.getName(), table2.getName())) {
            return;
        }
        String tableName1 = table1.getName();
        String tableName2 = table2.getName();

        /// Comparison of columns
        final int columnCount1 = table1.getFieldCount();
        final int columnCount2 = table2.getFieldCount();
        GCField column1 = null;
        GCField column2 = null;

        boolean isColumnToDelete = false;
        for (int i = 0; i < columnCount2; ++i) {
            column2 = table2.getField(i);
            isColumnToDelete = true;
            for (int j = 0; j < columnCount1; ++j) {
                column1 = table1.getField(j);
                if (column2.getName().equalsIgnoreCase(column1.getName())) {
                    isColumnToDelete = false;
                    break;
                }
            }
            if (isColumnToDelete) {
            	GCEnterpriseDBUtils.WriteSqlToDeleteColumn(outputStream, column2, tableName2, schemaPattern);
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
        	for(GCField column : columnsToCreate) {
        		GCEnterpriseDBUtils.WriteSqlToCreateColumn(outputStream, column, tableName1, schemaPattern);
        	}
        }

        for (int i = 0; i < columnCount1; ++i) {
            column1 = table1.getField(i);
            for (int j = 0; j < columnCount2; ++j) {
                column2 = table2.getField(j);
                if (column1.getName().equalsIgnoreCase(column2.getName())) {
                    compareColumnsWithIdenticalName(outputStream, schemaPattern, tableName1, column1, column2);
                    break;
                }
            }
        }
    }

    private void compareColumnsWithIdenticalName(StringWriter outputStream,
        String schemaPattern, String tableName, GCField column1,
        GCField column2) 
	throws GCException {
//        System.out.println("===type1: " + column1.getType() + " , type2: " + column2.getType());
        if (outputStream == null || tableName == null ||
            column1 == null || column2 == null) {
            return;
        }
        if (!GCUtils.IsEqualsIgnoreCaseStrings(column1.getName(), column2.getName())) {
            return;
        }
        if (!column1.getType().equalsIgnoreCase(column2.getType())) {
        	GCEnterpriseDBUtils.WriteSqlToDeleteColumn(outputStream, column2, tableName, schemaPattern);
        	GCEnterpriseDBUtils.WriteSqlToCreateColumn(outputStream, column1, tableName, schemaPattern);
            return;
        }
        if(!"VARCHAR2".equalsIgnoreCase(column2.getType())) {
            if(!"DATE".equalsIgnoreCase(column2.getType()) &&
               !"BLOB".equalsIgnoreCase(column2.getType()) &&
               !"CLOB".equalsIgnoreCase(column2.getType())) {
                if (column1.getSize() != column2.getSize() ||
                    column1.getDigits() != column2.getDigits()) {
                	GCEnterpriseDBUtils.WriteSqlToUpdateColumnType(outputStream, column1, tableName, schemaPattern);
                }
            }
        } else {
            if (column1.getSize() != column2.getSize() || !"C".equalsIgnoreCase(column2.getCharUsed())) {
            	GCEnterpriseDBUtils.WriteSqlToUpdateColumnType(outputStream, column1, tableName, schemaPattern);
            }
        }
        if (column1.getIsNullable() != column2.getIsNullable()) {
        	GCEnterpriseDBUtils.WriteSqlToUpdateNullable(outputStream, column1, tableName, schemaPattern);
        }
    }

    private void compareArrayForeignKeys(StringWriter outputStream,
        String schemaPattern, ArrayList<GCForeignKey> foreignKeys1,
        ArrayList<GCForeignKey> foreignKeys2)
        throws GCException {
    	
    	if (foreignKeys1 == null) {
            System.out.println("[GCJavaObjectsCmpToSqlEnterpriseDB] foreignKeys1 is null");
            return;
        }
        GCForeignKey foreignKey1 = null;
        GCForeignKey foreignKey2 = null;
        if (foreignKeys2 == null) {
            for (int i = 0; i < foreignKeys1.size(); ++i) {
                foreignKey1 = foreignKeys1.get(i);
                GCEnterpriseDBUtils.WriteSqlToCreateForeignKey(outputStream, foreignKey1, schemaPattern);
            }
            System.out.println("[GCJavaObjectsCmpToSqlEnterpriseDB] foreignKeys2 is null");
            return;
        }

//        System.out.println("===============================================");
//        for (int i = 0; i < foreignKeys1.size(); ++i) {
//        	GCForeignKey foreignKey = foreignKeys1.get(i);
//            System.out.println("==fk1: " + foreignKey.getName() + ":" +
//                    foreignKey.getFkTable() + ":" +
//                    foreignKey.getPkName() + ":" +
//                    foreignKey.getPkTable());
//        }
//        System.out.println("-----------------------------------------------");
//        for (int i = 0; i < foreignKeys2.size(); ++i) {
//        	GCForeignKey foreignKey = foreignKeys2.get(i);
//            System.out.println("==fk2: " + foreignKey.getName() + ":" +
//                    foreignKey.getFkTable() + ":" +
//                    foreignKey.getPkName() + ":" +
//                    foreignKey.getPkTable());
//        }
//        System.out.println("///////////////////////////////////////////////");
        
        
        
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
                GCEnterpriseDBUtils.WriteSqlToDeleteForeignKey(outputStream, foreignKey2, schemaPattern);
            }
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
                GCEnterpriseDBUtils.WriteSqlToCreateForeignKey(outputStream, foreignKey1, schemaPattern);
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
//        System.out.println("KKKKKKKKK foreignKey: " +foreignKey1.getName() + ":"+foreignKey2.getName() );
        GCForeignKeyComparator comparator = new GCForeignKeyComparator();
        int compareRes = comparator.compare(foreignKey1, foreignKey2);
        if (compareRes != 0) {
            GCEnterpriseDBUtils.WriteSqlToDeleteForeignKey(outputStream, foreignKey2, schemaPattern);
            GCEnterpriseDBUtils.WriteSqlToCreateForeignKey(outputStream, foreignKey1, schemaPattern);
        }
    }

    private void compareArrayIndexes(StringWriter outputStream,
        String schemaPattern, ArrayList<GCIndex> indexes1,
        ArrayList<GCIndex> indexes2, String dbDriver)
        throws GCException {
        if (indexes1 == null) {
            System.out.println("[GCJavaObjectsCmpToSqEnterpriseDB] indexes1 is null");
            return;
        }
        GCIndex index1 = null;
        GCIndex index2 = null;
        if (indexes2 == null) {
            for (int i = 0; i < indexes1.size(); ++i) {
                index1 = indexes1.get(i);
                GCEnterpriseDBUtils.WriteSqlToCreateIndex(outputStream, index1, schemaPattern);
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
                if ("com.edb.Driver".equals(dbDriver)) {
                    GCEnterpriseDBUtils.WriteSqlToCreateIndex(outputStream, index1, schemaPattern);
                } else {
                    GCEnterpriseDBUtils.WriteSqlToCreateIndex(outputStream, index1, schemaPattern);
                }
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
                GCEnterpriseDBUtils.WriteSqlToDeleteIndex(outputStream, index2, schemaPattern);
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
            GCEnterpriseDBUtils.WriteSqlToDeleteIndex(outputStream, index2, schemaPattern);
            GCEnterpriseDBUtils.WriteSqlToCreateIndex(outputStream, index1, schemaPattern);
        }
    }
}

/**
 * Title:        GCOracleUtils
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */
package com.cleanwise.tools.gencode.logic.spec;

import java.io.StringWriter;
import java.util.ArrayList;

import com.cleanwise.tools.gencode.GCException;
import com.cleanwise.tools.gencode.items.GCField;
import com.cleanwise.tools.gencode.items.GCForeignKey;
import com.cleanwise.tools.gencode.items.GCForeignKeyColumnRef;
import com.cleanwise.tools.gencode.items.GCForeignKeyColumnRefComparator;
import com.cleanwise.tools.gencode.items.GCIndex;
import com.cleanwise.tools.gencode.items.GCIndexColumnRefComparator;
import com.cleanwise.tools.gencode.items.GCPrimaryKey;
import com.cleanwise.tools.gencode.items.GCPrimaryKeyColumnRefComparator;
import com.cleanwise.tools.gencode.items.GCSequence;
import com.cleanwise.tools.gencode.items.GCTable;
import com.cleanwise.tools.gencode.utils.GCUtils;

public class GCOracleUtils {

    static public String GetColumnTypeName(String sqlType, String typeName,
        long size, int digits) {
        if (GCUtils.IsEmptyString(typeName)) {
            return "";
        }
        if ("NUMBER".equalsIgnoreCase(typeName)) {
            if (size > 0) {
                StringBuffer buffer = new StringBuffer();
                if (digits <= 0) {
                    buffer.append("NUMBER(");
                    buffer.append(String.valueOf(size));
                    buffer.append(")");
                } else {
                    buffer.append("NUMBER(");
                    buffer.append(String.valueOf(size));
                    buffer.append(",");
                    buffer.append(String.valueOf(digits));
                    buffer.append(")");
                }
                return buffer.toString();
            } else {
                return "NUMBER";
            }
        } else if ("VARCHAR2".equalsIgnoreCase(typeName)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("VARCHAR2(");
            buffer.append(String.valueOf(size));
            buffer.append(" CHAR)");
            return buffer.toString();
        } else if ("NVARCHAR2".equalsIgnoreCase(typeName)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("NVARCHAR2(");
            buffer.append(String.valueOf(size));
            buffer.append(")");
            return buffer.toString();
        } else if ("FLOAT".equalsIgnoreCase(typeName)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("FLOAT");
            if (size > 0) {
                buffer.append("(");
                buffer.append(String.valueOf(size));
                buffer.append(")");
            }
            return buffer.toString();
        } else if ("LONG".equalsIgnoreCase(typeName)) {
            return "LONG";
        } else if ("DATE".equalsIgnoreCase(typeName)) {
            return "DATE";
        } else if ("TIMESTAMP".equalsIgnoreCase(typeName)) {
            return "TIMESTAMP";
        } else if ("RAW".equalsIgnoreCase(typeName)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("RAW(");
            buffer.append(String.valueOf(size));
            buffer.append(")");
            return buffer.toString();
        } else if ("LONG RAW".equalsIgnoreCase(typeName)) {
            return "LONG RAW";
        } else if ("CHAR".equalsIgnoreCase(typeName)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("CHAR");
            if (size > 0) {
                buffer.append("(");
                buffer.append(String.valueOf(size));
                buffer.append(")");
            }
            return buffer.toString();
        } else if ("NCHAR".equalsIgnoreCase(typeName)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("CHAR");
            if (size > 0) {
                buffer.append("(");
                buffer.append(String.valueOf(size));
                buffer.append(")");
            }
            return buffer.toString();
        } else if ("CLOB".equalsIgnoreCase(typeName)) {
            return "CLOB";
        } else if ("NCLOB".equalsIgnoreCase(typeName)) {
            return "NCLOB";
        } else if ("BLOB".equalsIgnoreCase(typeName)) {
            return "BLOB";
        } else if ("BFILE".equalsIgnoreCase(typeName)) {
            return "BFILE";
        }
        return "";
    }

    static public String GetFullItemName(String itemName, String schemaPattern) {
        if (GCUtils.IsEmptyString(itemName)) {
            return itemName;
        }
        if (GCUtils.IsEmptyString(schemaPattern)) {
            return itemName.trim().toUpperCase();
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(schemaPattern.trim().toUpperCase());
        buffer.append(".");
        buffer.append(itemName.trim().toUpperCase());
        return buffer.toString();
    }

    static public void WriteSqlToCreateIndex(StringWriter outputStream,
        GCIndex index, String schemaPattern)
        throws GCException {
        if (outputStream == null || index == null) {
            return;
        }
        try {
            StringBuffer columnBuffer = new StringBuffer();
            index.sortColumns(new GCIndexColumnRefComparator(GCIndexColumnRefComparator.COMPARE_BY_KEYSEQ));
            for (int i = 0; i < index.getColumnRefCount(); ++i) {
                if (i > 0) {
                    columnBuffer.append(", ");
                }
                columnBuffer.append(index.getColumnRef(i).getColumnName());
            }
            outputStream.write("\r\n");
            outputStream.write("CREATE INDEX \r\n");
            outputStream.write(GCUtils.GetTabString(1));
            outputStream.write(GetFullItemName(index.getName(), schemaPattern).toUpperCase());
            outputStream.write(" \r\n");
            outputStream.write("ON \r\n");
            outputStream.write(GCUtils.GetTabString(1));
            outputStream.write(GetFullItemName(index.getTable(), schemaPattern).toUpperCase());
            outputStream.write(" (");
            outputStream.write(columnBuffer.toString());
            outputStream.write("); \r\n");
            outputStream.flush();
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToCreateIndex");
        }
    }

    static public void WriteSqlToDeleteIndex(StringWriter outputStream,
        GCIndex index, String schemaPattern) throws GCException {
        if (outputStream == null || index == null) {
            return;
        }
        try {
            outputStream.write("\r\n--DROP INDEX ");
            outputStream.write(GetFullItemName(index.getName(), schemaPattern).toUpperCase());
            outputStream.write(";\r\n");
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToDeleteIndex");
        }
    }

    static public void WriteSqlToCreateForeignKey(StringWriter outputStream,
        GCForeignKey foreignKey, String schemaPattern)
        throws GCException {
        if (outputStream == null || foreignKey == null) {
            return;
        }
        try {
            StringBuffer fkBuffer = new StringBuffer();
            StringBuffer pkBuffer = new StringBuffer();
            foreignKey.sortColumns(new GCForeignKeyColumnRefComparator(GCForeignKeyColumnRefComparator.COMPARE_BY_KEYSEQ));
            for (int i = 0; i < foreignKey.getColumnRefCount(); ++i) {
                GCForeignKeyColumnRef columnRef = foreignKey.getColumnRef(i);
                if (i > 0) {
                    fkBuffer.append(", ");
                    pkBuffer.append(", ");
                }
                fkBuffer.append(columnRef.getFkColumnName());
                pkBuffer.append(columnRef.getPkColumnName());
            }
            outputStream.write("\r\n");
            outputStream.write("ALTER TABLE \r\n");
            outputStream.write(GCUtils.GetTabString(1));
            outputStream.write(GetFullItemName(foreignKey.getFkTable(), schemaPattern).toUpperCase());
            outputStream.write(" \r\n");
            outputStream.write("ADD CONSTRAINT \r\n");
            outputStream.write(GCUtils.GetTabString(1));
            outputStream.write(foreignKey.getName().toUpperCase());
            outputStream.write(" FOREIGN KEY (");
            outputStream.write(fkBuffer.toString());
            outputStream.write(") \r\n");
            outputStream.write("REFERENCES \r\n");
            outputStream.write(GCUtils.GetTabString(1));
            outputStream.write(foreignKey.getPkTable().toUpperCase());
            outputStream.write(" (");
            outputStream.write(pkBuffer.toString());
            outputStream.write(")");
            outputStream.write(";\r\n");
            outputStream.flush();
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToCreateForeignKey");
        }
    }

    static public void WriteSqlToDeleteForeignKey(StringWriter outputStream,
        GCForeignKey foreignKey, String schemaPattern) throws GCException {
        if (outputStream == null || foreignKey == null) {
            return;
        }
        try {
            outputStream.write("\r\n--ALTER TABLE ");
            outputStream.write(GetFullItemName(foreignKey.getFkTable(), schemaPattern).toUpperCase());
            outputStream.write(" DROP CONSTRAINT ");
            outputStream.write(foreignKey.getName().toUpperCase());
            outputStream.write(";\r\n");
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToDeleteForeignKey");
        }
    }

    static private void WriteSqlForField(StringWriter outputStream, GCField field)
        throws GCException {
        if (outputStream == null || field == null) {
            return;
        }
        final int fieldNameWidth = 37;
        String fieldName = field.getName();
        String fieldSqlType = field.getSqlType();
        String fieldType = field.getType();
        long fieldSize = field.getSize();
        int fieldDigits = field.getDigits();
        try {
            outputStream.write(fieldName);
            outputStream.write(" ");
            final int restSymbols = fieldNameWidth - fieldName.length() - 1;
            if (restSymbols > 0) {
                for (int i = 0; i < restSymbols; ++i) {
                    outputStream.write(" ");
                }
            }
            outputStream.write(GCOracleUtils.GetColumnTypeName(fieldSqlType,
                fieldType, fieldSize, fieldDigits));
            if (!field.getIsNullable()) {
                outputStream.write(" NOT NULL");
            }
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlForField");
        }
    }

    static public void WriteSqlToCreateTable(StringWriter outputStream,
        GCTable table, String schemaPattern, String tableSpace) throws GCException {
        if (outputStream == null || table == null) {
            return;
        }
        try {
        	if(table.isTemporary()) {
        		 outputStream.write("\r\nCREATE GLOBAL TEMPORARY TABLE ");
        	}else {
        		outputStream.write("\r\nCREATE TABLE ");
        	}
            outputStream.write(GetFullItemName(table.getName(), schemaPattern).toUpperCase());
            outputStream.write("\r\n(");
            /// Writing of columns
            for (int i = 0; i < table.getFieldCount(); ++i) {
                GCField field = table.getField(i);
                if (field == null) {
                    continue;
                }
                outputStream.write("\r\n");
                outputStream.write(GCUtils.GetTabString(1));
                WriteSqlForField(outputStream, field);
                if (i < table.getFieldCount() - 1) {
                    outputStream.write(",");
                }
            }
            /// Writing of primary keys
            GCPrimaryKey primaryKey = table.getPrimaryKey();
            if (primaryKey != null) {
                if (primaryKey.getColumnRefCount() > 0) {
                    outputStream.write(",\r\n");
                    outputStream.write(GCUtils.GetTabString(1));
                    if (!GCUtils.IsEmptyString(primaryKey.getName())) {
                        outputStream.write("CONSTRAINT");
                        if (!GCUtils.IsEmptyString(primaryKey.getName())) {
                            outputStream.write(" ");
                            outputStream.write(primaryKey.getName());
                        }
                        outputStream.write(" ");
                    }
                    outputStream.write("PRIMARY KEY (");
                    primaryKey.sortColumns(new GCPrimaryKeyColumnRefComparator(GCPrimaryKeyColumnRefComparator.COMPARE_BY_KEYSEQ));
                    for (int i = 0; i < primaryKey.getColumnRefCount(); ++i) {
                        if (i > 0) {
                            outputStream.write(", ");
                        }
                        outputStream.write(primaryKey.getColumnRef(i).getColumnName());
                    }
                    outputStream.write(")");
                }
            }
            outputStream.write("\r\n)");
            if (!GCUtils.IsEmptyString(tableSpace)) {
                outputStream.write("\r\nTABLESPACE ");
                outputStream.write(tableSpace);
            }
            if(table.isTemporary()) {
            	outputStream.write("\r\nON COMMIT DELETE ROWS ");
            	outputStream.write("\r\nNOCACHE; \r\n");
	       	}else {
	       		outputStream.write(";\r\n");
	       	}
            outputStream.flush();
        } catch (GCException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToCreateTable");
        }
    }

    static public void WriteSqlToDeleteTable(StringWriter outputStream,
        GCTable table, String schemaPattern) throws GCException {
        if (outputStream == null || table == null) {
            return;
        }
        try {
            outputStream.write("\r\n--DROP TABLE ");
            outputStream.write(GetFullItemName(table.getName(), schemaPattern).toUpperCase());
            if(table.isTemporary()) {
            	 outputStream.write(" CASCADE CONSTRAINTS;\r\n");
            }else {
            	 outputStream.write(";\r\n");
            }
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToDeleteTable");
        }
    }

    static public void WriteSqlToCreateColumn(StringWriter outputStream,
        GCField column, String tableName, String schemaPattern)
        throws GCException {
        if (outputStream == null || column == null || tableName == null) {
            return;
        }
        try {
            outputStream.write("\r\nALTER TABLE ");
            outputStream.write(GetFullItemName(tableName, schemaPattern).toUpperCase());
            outputStream.write(" ADD ");
            outputStream.write(column.getName().toUpperCase());
            outputStream.write(" ");
            outputStream.write(GetColumnTypeName(column.getSqlType(), column.getType(), column.getSize(), column.getDigits()));
            outputStream.write(";\r\n");
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToCreateColumn");
        }
    }

    static public void WriteSqlToCreateColumns(StringWriter outputStream,
        ArrayList<GCField> columns, String tableName, String schemaPattern)
        throws GCException {
        if (outputStream == null || columns == null || tableName == null) {
            return;
        }
        if (columns.size() == 1) {
            WriteSqlToCreateColumn(outputStream, columns.get(0), tableName, schemaPattern);
            return;
        }
        try {
            outputStream.write("\r\nALTER TABLE ");
            outputStream.write(GetFullItemName(tableName, schemaPattern).toUpperCase());
            outputStream.write(" ADD (\r\n");
            for (int i = 0; i < columns.size(); ++i) {
                GCField column = columns.get(i);
                outputStream.write(GCUtils.GetTabString(1));
                outputStream.write(column.getName().toUpperCase());
                outputStream.write(" ");
                outputStream.write(GetColumnTypeName(column.getSqlType(), column.getType(), column.getSize(), column.getDigits()));
                if (i < columns.size() - 1) {
                    outputStream.write(", \r\n");
                }
            }
            outputStream.write("\r\n);\r\n");
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToCreateColumns");
        }
    }

    static public void WriteSqlToDeleteColumn(StringWriter outputStream,
        GCField column, String tableName, String schemaPattern)
        throws GCException {
        if (outputStream == null || column == null || tableName == null) {
            return;
        }
        try {
            outputStream.write("\r\n--ALTER TABLE ");
            outputStream.write(GetFullItemName(tableName, schemaPattern).toUpperCase());
            outputStream.write(" DROP (");
            outputStream.write(column.getName().toUpperCase());
            outputStream.write(");\r\n");
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToDeleteColumn");
        }
    }

    static public void WriteSqlToUpdateColumnType(StringWriter outputStream,
        GCField column, String tableName, String schemaPattern)
        throws GCException {
        if (outputStream == null || column == null || tableName == null) {
            return;
        }
        try {
            outputStream.write("\r\nALTER TABLE ");
            outputStream.write(GetFullItemName(tableName, schemaPattern).toUpperCase());
            outputStream.write(" MODIFY (");
            outputStream.write(column.getName().toUpperCase());
            outputStream.write(" ");
            outputStream.write(GetColumnTypeName(column.getSqlType(), column.getType(), column.getSize(), column.getDigits()));
            outputStream.write(");\r\n");
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToUpdateColumnType");
        }
    }

    static public void WriteSqlToUpdateNullable(StringWriter outputStream,
        GCField column, String tableName, String schemaPattern)
        throws GCException {
        if (outputStream == null || column == null || tableName == null) {
            return;
        }
        try {
            outputStream.write("\r\nALTER TABLE ");
            outputStream.write(GetFullItemName(tableName, schemaPattern).toUpperCase());
            outputStream.write(" MODIFY (");
            outputStream.write(column.getName().toUpperCase());
            outputStream.write(" ");
            outputStream.write(GetColumnTypeName(column.getSqlType(), column.getType(), column.getSize(), column.getDigits()));
            if (column.getIsNullable()) {
                outputStream.write(" NULL");
            } else {
                outputStream.write(" NOT NULL");
            }
            outputStream.write(");\r\n");
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToUpdateNullable");
        }
    }
    static public void WriteSqlToRenamePrimaryKey(StringWriter outputStream,
        GCPrimaryKey oldPrimaryKey, GCPrimaryKey newPrimaryKey, String tableName, String schemaPattern)
        throws GCException {
        if (outputStream == null || oldPrimaryKey == null || newPrimaryKey == null ||tableName == null) {
            return;
        }
        try {
            StringBuffer bufferColumns = new StringBuffer();
            outputStream.write("\r\nALTER TABLE ");
            outputStream.write(GetFullItemName(tableName, schemaPattern).toUpperCase());
            outputStream.write(" RENAME CONSTRAINT ");
            if (!GCUtils.IsEmptyString(oldPrimaryKey.getName()) &&
                !GCUtils.IsEmptyString(newPrimaryKey.getName())) {
                outputStream.write(oldPrimaryKey.getName().toUpperCase());
                outputStream.write(" TO ");
                outputStream.write(newPrimaryKey.getName().toUpperCase());
            }
             outputStream.write(";\r\n");
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToRenamePrimaryKey");
        }
    }

    static public void WriteSqlToCreatePrimaryKey(StringWriter outputStream,
        GCPrimaryKey primaryKey, String tableName, String schemaPattern)
        throws GCException {
        if (outputStream == null || primaryKey == null || tableName == null) {
            return;
        }
        try {
            StringBuffer bufferColumns = new StringBuffer();
            primaryKey.sortColumns(new GCPrimaryKeyColumnRefComparator(GCPrimaryKeyColumnRefComparator.COMPARE_BY_KEYSEQ));
            for (int i = 0; i < primaryKey.getColumnRefCount(); ++i) {
                if (i > 0) {
                    bufferColumns.append(", ");
                }
                bufferColumns.append(primaryKey.getColumnRef(i).getColumnName().toUpperCase());
            }
            outputStream.write("\r\nALTER TABLE ");
            outputStream.write(GetFullItemName(tableName, schemaPattern).toUpperCase());
            outputStream.write(" ADD CONSTRAINT");
            if (!GCUtils.IsEmptyString(primaryKey.getName())) {
                outputStream.write(" ");
                outputStream.write(primaryKey.getName().toUpperCase());
            }
            outputStream.write(" PRIMARY KEY (");
            outputStream.write(bufferColumns.toString());
            outputStream.write(");\r\n");
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToCreatePrimaryKey");
        }
    }

    static public void WriteSqlToDeletePrimaryKey(StringWriter outputStream,
        GCPrimaryKey primaryKey, String tableName, String schemaPattern)
        throws GCException {
        if (outputStream == null || primaryKey == null || tableName == null) {
            return;
        }
        try {
            outputStream.write("\r\n--ALTER TABLE ");
            outputStream.write(GetFullItemName(tableName, schemaPattern).toUpperCase());
            outputStream.write(" DROP CONSTRAINT");
            if (!GCUtils.IsEmptyString(primaryKey.getName())) {
                outputStream.write(" ");
                outputStream.write(primaryKey.getName().toUpperCase());
            }
            outputStream.write(";\r\n");
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToDeletePrimaryKey");
        }
    }

    static public void WriteSqlToCreateSequence(StringWriter outputStream,
        GCSequence sequence, String schemaPattern)
        throws GCException {
        if (outputStream == null || sequence == null) {
            return;
        }
        try {
            outputStream.write("\r\nCREATE SEQUENCE ");
            outputStream.write(GetFullItemName(sequence.getName(), schemaPattern).toUpperCase());
            outputStream.write(";\r\n");
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToCreateSequence");
        }
    }

    static public void WriteSqlToDeleteSequence(StringWriter outputStream,
        GCSequence sequence, String schemaPattern)
        throws GCException {
        if (outputStream == null || sequence == null) {
            return;
        }
        try {
            outputStream.write("\r\n--DROP SEQUENCE ");
            outputStream.write(GetFullItemName(sequence.getName(), schemaPattern).toUpperCase());
            outputStream.write(";\r\n");
        } catch (Exception ex) {
            throw new GCException("Exception occurred. " +
                ex.getMessage(), "GCOracleUtils.WriteSqlToDeleteSequence");
        }
    }
}

package com.cleanwise.service.api.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.TableColumnInfo;
import java.util.Date;



/**
 * Title:        DataUploaderHelperBean
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2010
 * Company:      eSpendwise, Inc.
 */


public class DataUploaderHelperBean extends UtilityServicesAPI {

    public void ejbCreate() throws CreateException, RemoteException {
    }

    private String getFullTableName(String table, String namespace)  {
        if (!Utility.isSet(table)) {
            return table;
        }
        String fullTableName = table.trim().toUpperCase();
        if (Utility.isSet(namespace)) {
            fullTableName = namespace.trim().toUpperCase() + "." + fullTableName;
        }
        return fullTableName;
    }

    public boolean isExistsDatabaseTable(String table, String namespace)
        throws RemoteException, SQLException {

        Connection connection = null;
        boolean isExistTable = false;
        PreparedStatement stmt = null;
        ResultSet resSet = null;

        try {
            connection = getConnection();
            String sql = null;
            if (Utility.isSet(namespace)) {
                sql =
                    "SELECT COUNT(*) FROM ALL_TABLES WHERE upper(OWNER) = ? AND upper(TABLE_NAME) = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, namespace.toUpperCase());
                stmt.setString(2, table.toUpperCase());
            } else {
                sql =
                    "SELECT COUNT(*) TABLE_COUNT FROM USER_TABLES WHERE upper(TABLE_NAME) = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, table.toUpperCase());
            }
            resSet = stmt.executeQuery();
            if (resSet.next()) {
                isExistTable = (resSet.getInt(1) > 0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                }
            }
            if (resSet != null) {
                try {
                    resSet.close();
                } catch (Exception ex) {
                }
            }
            closeConnection(connection);
        }
        return isExistTable;
    }

    public List<TableColumnInfo> getTableColumnsInfo(String table, String namespace)
        throws RemoteException, SQLException {
        if (!Utility.isSet(table)) {
            throw new RemoteException("Database table is not defined.");
        }
        List<TableColumnInfo> columns = new ArrayList<TableColumnInfo>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resSet = null;
        try {
            String sql =
                "SELECT " +
                    "OWNER, " +
                    "TABLE_NAME, " +
                    "COLUMN_NAME, " +
                    "DATA_TYPE, " +
                    "DATA_LENGTH, " +
                    "DATA_PRECISION, " +
                    "DATA_SCALE, " +
                    "NULLABLE, "+
                    "CHARACTER_SET_NAME, " +
                    "CHAR_COL_DECL_LENGTH, " +
                    "CHAR_LENGTH, " +
                    "CHAR_USED "+
                "FROM " +
                    "ALL_TAB_COLUMNS " +
                "WHERE " +
                    //(Utility.isSet(namespace) ? "LOWER(OWNER) = LOWER('" + namespace + "') AND " : "") +
                    //"LOWER(TABLE_NAME) = LOWER('" + table + "') " +
                    (Utility.isSet(namespace) ? "LOWER(OWNER) = LOWER(?) AND " : "") +
                    "LOWER(TABLE_NAME) = LOWER(?) " +
                "ORDER BY " +
                    "COLUMN_ID";
            connection = getConnection();
            stmt = connection.prepareStatement(sql);
            int i=1;
            if (Utility.isSet(namespace)) {
                stmt.setString(i++, namespace);
            }
            stmt.setString(i++, table);
            resSet = stmt.executeQuery();
            if (resSet != null) {
                while (resSet.next()) {
                    TableColumnInfo column = new TableColumnInfo();
                    column.setName(resSet.getString("COLUMN_NAME"));
                    column.setType(resSet.getString("DATA_TYPE"));
                    column.setSize(resSet.getInt("DATA_LENGTH"));
                    column.setPrecision(resSet.getInt("DATA_PRECISION"));
                    column.setScale(resSet.getInt("DATA_SCALE"));
                    column.setIsNullable( (("Y".equals(resSet.getString("NULLABLE"))) ? true : false) );
                    column.setCharSet(resSet.getString("CHARACTER_SET_NAME"));
                    column.setCharLength(resSet.getInt("CHAR_LENGTH"));
                    column.setCharUsed(resSet.getString("CHAR_USED"));
                    columns.add(column);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                }
            }
            if (resSet != null) {
                try {
                    resSet.close();
                } catch (Exception ex) {
                }
            }
            closeConnection(connection);
        }
        return columns;
    }

    public void createDatabaseTable(String userName, String table, String namespace,
        List<TableColumnInfo> columnsInfo)
        throws RemoteException, SQLException {
        if (!Utility.isSet(table)) {
            throw new RemoteException("Database table is not defined.");
        }
        if (columnsInfo == null || columnsInfo.size() == 0) {
            throw new RemoteException("Columns information for the table is not defined.");
        }
        StringBuilder buff = new StringBuilder();
        writeSqlToCreateTable(buff, table, namespace, columnsInfo);
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = getConnection();
            stmt = connection.prepareStatement(buff.toString());
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                }
            }
            closeConnection(connection);
        }
    }

    public void deleteDatabaseTable(String userName, String table, String namespace)
        throws RemoteException, SQLException {
        if (!Utility.isSet(table)) {
            throw new RemoteException("Database table is not defined.");
        }
        String tableFullName = getFullTableName(table, namespace).toUpperCase();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            String sql = "DROP TABLE " + tableFullName + " PURGE";
            connection = getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                }
            }
            closeConnection(connection);
        }
    }

    public void deleteAllRowsFromDatabaseTable(String userName, String table, String namespace)
        throws RemoteException, SQLException {
        if (!Utility.isSet(table)) {
            throw new RemoteException("Database table is not defined.");
        }
        String tableFullName = getFullTableName(table, namespace).toUpperCase();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM " + tableFullName;
            connection = getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                }
            }
            closeConnection(connection);
        }
    }

    public void insertRowIntoDatabaseTable(String userName, String table, String namespace,
        List<TableColumnInfo> columnsInfo, List<String> values,
        Map<String, String> dataFormats, int rowNum, boolean insertRowNumFl)
        throws RemoteException, SQLException {
        if (!Utility.isSet(table)) {
            throw new RemoteException("Database table is not defined.");
        }
        if (columnsInfo == null || columnsInfo.size() == 0) {
            throw new RemoteException("Columns information for the table is not defined.");
        }
        if (values == null || values.size() == 0) {
            throw new RemoteException("Values to insert into table is not defined.");
        }
        if (columnsInfo.size() != values.size()) {
            throw new RemoteException("Size of values and columns is not equal.");
        }

        final String dbDatePattren = "MM/dd/yyyy";
        String tableFullName = getFullTableName(table, namespace).toUpperCase();
        SimpleDateFormat dateFormat = null;
        Connection connection = null;
        PreparedStatement stmt = null;

        if (dataFormats != null) {
            String datePattern = dataFormats.get(DataUploaderHelper.DATE_PATTERN);
            if (Utility.isSet(datePattern)) {
                 try {
                    dateFormat = new SimpleDateFormat(datePattern);
                } catch (Exception ex) {
                    dateFormat = null;
                }
            }
        }

        try {
            StringBuilder buff = new StringBuilder();
            buff.append("INSERT INTO ");
            buff.append(tableFullName);
            buff.append(" (");
            for (int i = 0; i < columnsInfo.size(); ++i) {
                TableColumnInfo column = columnsInfo.get(i);
                buff.append(column.getName());
                if (i < columnsInfo.size() - 1) {
                    buff.append(",");
                }				
            }
			if(insertRowNumFl) {
				buff.append(",LINE_NUMBER");
			}
            buff.append(") VALUES (");
            for (int i = 0; i < values.size(); ++i) {
                String value = values.get(i);
				if(value!=null && value.indexOf("'")>=0) value = value.replaceAll("'","''"); 
                TableColumnInfo column = columnsInfo.get(i);
                if (column.getType().toUpperCase().startsWith("NUMBER") ||
                    column.getType().toUpperCase().startsWith("FLOAT") ||
                    column.getType().toUpperCase().startsWith("LONG")) {
                    buff.append((value == null || value.trim().length()==0 ) ? "NULL" : value);
                } else if (column.getType().toUpperCase().startsWith("VARCHAR") ||
                           column.getType().toUpperCase().startsWith("NVARCHAR") ||
                           column.getType().toUpperCase().startsWith("CHAR") ||
                           column.getType().toUpperCase().startsWith("NCHAR")) {
                    if (value == null || value.trim().length()==0 ) {
                        buff.append("NULL");
                    } else {
                        buff.append("'");
                        buff.append(value);
                        buff.append("'");
                    }
                } else if (column.getType().toUpperCase().startsWith("DATE")) {
                    if (dateFormat == null) {
                        throw new RemoteException("Date pattern is not defined!");
                    }
                    if (value == null || value.trim().length()==0 ) {
                        buff.append("NULL");
                    } else {
                        Date date = dateFormat.parse(value);
                        buff.append("TO_DATE('");
                        buff.append((new SimpleDateFormat(dbDatePattren)).format(date));
                        buff.append("','");
                        buff.append(dbDatePattren);
                        buff.append("')");
                    }
                } else if (column.getType().toUpperCase().startsWith("TIMESTAMP")) {
                    if (dateFormat == null) {
                        throw new RemoteException("Date pattern is not defined!");
                    }
                    if (value == null || value.trim().length()==0 ) {
                        buff.append("NULL");
                    } else {
                        Date date = dateFormat.parse(value);
                        buff.append("TO_DATE('");
                        buff.append((new SimpleDateFormat(dbDatePattren)).format(date));
                        buff.append("','");
                        buff.append(dbDatePattren);
                        buff.append("')");
                    }
                } else if (column.getType().toUpperCase().startsWith("BLOB")) {
                    buff.append("NULL");
                } else {
                    throw new RemoteException("Not supported column type: '" + column.getType() + "'");
                }
                if (i < columnsInfo.size() - 1) {
                    buff.append(",");
                }
            }
			if(insertRowNumFl) {
				buff.append(",");
				buff.append(rowNum);
			}

            buff.append(")");

            connection = getConnection();
            stmt = connection.prepareStatement(buff.toString());
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                }
            }
            closeConnection(connection);
        }
    }

    public TreeSet<String> getStringColumnValues(String table, String databaseSchema,
        String columnName) throws RemoteException, SQLException {
        TreeSet<String> values = new TreeSet<String>();
        if (!Utility.isSet(table)) {
            throw new IllegalStateException("Database table is not defined.");
        }
        if (!Utility.isSet(columnName)) {
            throw new IllegalStateException("Column name is not defined.");
        }
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resSet = null;
        try {
            String sql =
                "SELECT DISTINCT " +
                    columnName.toUpperCase() + " " +
                "FROM " +
                    getFullTableName(table, databaseSchema).toUpperCase() + " " +
                "WHERE " +
                    columnName.toUpperCase() + " IS NOT NULL";
            connection = getConnection();
            stmt = connection.prepareStatement(sql);
            resSet = stmt.executeQuery();
            if (resSet != null) {
                while (resSet.next()) {
                    String value = resSet.getString(1);
                    if (value != null) {
                        values.add(value);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                }
            }
            if (resSet != null) {
                try {
                    resSet.close();
                } catch (Exception ex) {
                }
            }
            closeConnection(connection);
        }
        return values;
    }

    public void updateBlobColumnValues(String table, String databaseSchema,
        String anchorColumnName, String anchorColumnValue, String columnName,
        byte[] bytes) throws RemoteException, SQLException {
        if (!Utility.isSet(table)) {
            throw new IllegalStateException("Database table is not defined.");
        }
        if (!Utility.isSet(anchorColumnName)) {
            throw new IllegalStateException("Anchor column name is not defined.");
        }
        if (!Utility.isSet(anchorColumnValue)) {
            throw new IllegalStateException("Anchor column value is not defined.");
        }
        if (!Utility.isSet(columnName)) {
            throw new IllegalStateException("Column name is not defined.");
        }
        if (bytes == null) {
            return;
        }
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            String sql =
                "UPDATE " + getFullTableName(table, databaseSchema).toUpperCase() + " " +
                "SET " + columnName.toUpperCase() + "=? WHERE " + anchorColumnName.toUpperCase() + "=?";


            connection = getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setBytes(1, bytes);
            stmt.setString(2, anchorColumnValue);
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                }
            }
            closeConnection(connection);
        }
    }

    private String getColumnTypeName(TableColumnInfo column) {
        if (column == null) {
            return "";
        }
        if (!Utility.isSet(column.getType())) {
            return "";
        }
        if ("NUMBER".equalsIgnoreCase(column.getType())) {
            if (column.getSize() > 0) {
                StringBuffer buffer = new StringBuffer();
                if (column.getScale() <= 0) {
                    buffer.append("NUMBER");
                    if (column.getPrecision() > 0) {
                        buffer.append("(");
                        buffer.append(String.valueOf(column.getPrecision()));
                        buffer.append(")");
                    }
                } else {
                    buffer.append("NUMBER(");
                    buffer.append(String.valueOf(column.getPrecision()));
                    buffer.append(",");
                    buffer.append(String.valueOf(column.getScale()));
                    buffer.append(")");
                }
                return buffer.toString();
            } else {
                return "NUMBER";
            }
        } else if ("VARCHAR2".equalsIgnoreCase(column.getType())) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("VARCHAR2(");
            buffer.append(String.valueOf(column.getSize()));
            buffer.append(" ");
            if (Utility.isSet(column.getCharUsed())) {
                buffer.append(("C".equalsIgnoreCase(column.getCharUsed()))?"CHAR":"BYTE");
            } else {
                buffer.append("BYTE");
            }
            buffer.append(")");
            return buffer.toString();
        } else if ("NVARCHAR2".equalsIgnoreCase(column.getType())) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("NVARCHAR2");
            if (column.getCharLength() > 0) {
                buffer.append("(");
                buffer.append(String.valueOf(column.getCharLength()));
                buffer.append(")");
            }
            return buffer.toString();
        } else if ("FLOAT".equalsIgnoreCase(column.getType())) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("FLOAT");
            if (column.getPrecision() > 0) {
                buffer.append("(");
                buffer.append(String.valueOf(column.getPrecision()));
                buffer.append(")");
            }
            return buffer.toString();
        } else if ("LONG".equalsIgnoreCase(column.getType())) {
            return "LONG";
        } else if ("DATE".equalsIgnoreCase(column.getType())) {
            return "DATE";
        } else if ("TIMESTAMP".equalsIgnoreCase(column.getType())) {
            if (column.getSize() > 0) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("TIMESTAMP(");
                buffer.append(String.valueOf(column.getSize()));
                buffer.append(")");
                return buffer.toString();
            } else {
                return "TIMESTAMP";
            }
        } else if (column.getType().toUpperCase().startsWith("TIMESTAMP(") ||
                   column.getType().toUpperCase().startsWith("TIMESTAMP (") ||
                   column.getType().toUpperCase().startsWith("TIMESTAMP  (")) {
            return column.getType();
        } else if ("RAW".equalsIgnoreCase(column.getType())) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("RAW(");
            buffer.append(String.valueOf(column.getSize()));
            buffer.append(")");
            return buffer.toString();
        } else if ("LONG RAW".equalsIgnoreCase(column.getType())) {
            return "LONG RAW";
        } else if ("CHAR".equalsIgnoreCase(column.getType())) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("CHAR");
            if (column.getSize() > 0) {
                buffer.append("(");
                buffer.append(String.valueOf(column.getSize()));
                buffer.append(" ");
                if (Utility.isSet(column.getCharUsed())) {
                    buffer.append(("C".equalsIgnoreCase(column.getCharUsed()))?"CHAR":"BYTE");
                } else {
                    buffer.append("BYTE");
                }
                buffer.append(")");
            }
            return buffer.toString();
        } else if ("NCHAR".equalsIgnoreCase(column.getType())) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("NCHAR");
            if (column.getCharLength() > 0) {
                buffer.append("(");
                buffer.append(String.valueOf(column.getCharLength()));
                buffer.append(")");
            }
            return buffer.toString();
        } else if ("CLOB".equalsIgnoreCase(column.getType())) {
            return "CLOB";
        } else if ("NCLOB".equalsIgnoreCase(column.getType())) {
            return "NCLOB";
        } else if ("BLOB".equalsIgnoreCase(column.getType())) {
            return "BLOB";
        } else if ("BFILE".equalsIgnoreCase(column.getType())) {
            return "BFILE";
        }
        return "";
    }

    private void writeSqlForColumn(StringBuilder output, TableColumnInfo column) {
        if (output == null || column == null) {
            return;
        }
        output.append(column.getName());
        output.append(" ");
        output.append(getColumnTypeName(column));
        if (!column.getIsNullable()) {
            output.append(" NOT NULL");
        }
    }

    private void writeSqlToCreateTable(StringBuilder output,
        String table, String schema, List<TableColumnInfo> columns) {
        if (output == null || table == null) {
            return;
        }
        if (columns == null || columns.size() == 0) {
            return;
        }
        output.append("\r\nCREATE TABLE ");
        output.append(getFullTableName(table, schema).toUpperCase());
        output.append("\r\n(");
        /// Writing of columns
        for (int i = 0; i < columns.size(); ++i) {
            TableColumnInfo column = columns.get(i);
            if (column == null) {
                continue;
            }
            output.append("\r\n");
            writeSqlForColumn(output, column);
            if (i < columns.size() - 1) {
                output.append(",");
            }
        }
        output.append("\r\n)");
    }

}

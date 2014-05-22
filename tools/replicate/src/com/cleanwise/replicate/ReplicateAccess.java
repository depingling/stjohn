package com.cleanwise.replicate;

import java.sql.*;
import org.apache.log4j.Logger;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReplicateAccess {
    private static final Logger log = Logger.getLogger(ReplicateAccess.class);
    Connection masterConn = null;
    Connection slaveConn = null;
    public static final String ORACLE = "Oracle";
    public static final String EDB = "EnterpriseDB";
    public ReplicateAccess() {
    }

    public Connection getMasterConnection() throws Exception{
        if (masterConn == null) {
            try {
                log.info("master driver: " + System.getProperty("masterDriver"));
                Class.forName(System.getProperty("masterDriver"));
                masterConn = DriverManager.getConnection (System.getProperty("masterUrl"),
                                                          System.getProperty("masterUser"),
                                                          System.getProperty("masterPassword"));
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e);
                throw new Exception(e);
            }
        }
        return masterConn;
    }

    public Connection getSlaveConnection() throws Exception {
        if (slaveConn == null) {
            try {
                log.info("slave driver: " + System.getProperty("slaveDriver"));
                Class.forName(System.getProperty("slaveDriver"));
                slaveConn = DriverManager.getConnection (System.getProperty("slaveUrl"),
                                                         System.getProperty("slaveUser"),
                                                         System.getProperty("slavePassword"));
            } catch (Throwable e) {
                e.printStackTrace();
                log.error(e);
                throw new Exception(e);
            }
        }
        return slaveConn;
    }

    public void testConnection() {
        try {
            Statement stmt = masterConn.createStatement();
            ResultSet rs = stmt.executeQuery("select sysdate from dual");
            while (rs.next())
                log.info(rs.getDate(1));
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPearametersString(int numberOfColumns) {
        String parametersString = "";
        for (int i = 1; i < numberOfColumns + 1; i++) {
            parametersString += "?";
            if (i < numberOfColumns) {
                parametersString += ",";
            }
        }
        return parametersString;
    }

    public static final String VARCHAR2 = "VARCHAR2";
    public static final String NUMBER = "NUMBER";
    public static final String NUMERIC = "numeric";
    public static final String DATE = "DATE";
    public static final String TIMESTAMP = "timestamp";
    public static final String BLOB = "BLOB";
    public static final String BYTEA = "bytea";
    public static final String CHAR = "CHAR";
    public static final String BPCHAR = "bpchar";

    public void copyTable(String table, Connection fromConn, Connection toConn) throws Exception{
        copyTable(table, table, fromConn, toConn);
    }

    public void copyTable(String fromTable, String toTable, Connection fromConn, Connection toConn) throws Exception{
        try {
            DatabaseMetaData fromMetaData = fromConn.getMetaData();
            String fromDbName = fromMetaData.getDatabaseProductName();
//            log.info("from Db: " + fromDbName + ", table: " + fromTable);
            DatabaseMetaData toMetaData = toConn.getMetaData();
            String toDbName = toMetaData.getDatabaseProductName();
//            log.info("to Db: " + toDbName + ", table: " + toTable);
            log.info(fromDbName + ": " +  fromTable  + " --> " + toDbName + ":" + toTable);

            Statement fromStmt = fromConn.createStatement();
            fromStmt.setFetchSize(1000);
            ResultSet fromRs = fromStmt.executeQuery("select * from " + fromTable);
            ResultSetMetaData fromRsMetaData = fromRs.getMetaData();
            int numberOfColumns = fromRsMetaData.getColumnCount();

            Statement toTestStmt = toConn.createStatement();
            toTestStmt.setFetchSize(1000);
            ResultSet toTestRs = toTestStmt.executeQuery("select * from " + toTable);
            ResultSetMetaData toRsMetaData = toTestRs.getMetaData();

            PreparedStatement toStmt = null;
            toStmt = toConn.prepareStatement(
                    "insert into " + toTable + " values (" + getPearametersString(numberOfColumns) + ")");
            int row = 0;
            while (fromRs.next()) {
                for (int i = 1; i < numberOfColumns + 1; i++) {
                    String fromTypeName = fromRsMetaData.getColumnTypeName(i);
                    String toTypeName = toRsMetaData.getColumnTypeName(i);

                    Class fromCl=Class.forName("java.sql.ResultSet");
                    Class[] fromPar=new Class[1];
                    fromPar[0]=Integer.TYPE;
                    Method fromMthd = null;
                    if (VARCHAR2.equalsIgnoreCase(fromTypeName) ||
                        CHAR.equalsIgnoreCase(fromTypeName) ||
                        BPCHAR.equalsIgnoreCase(fromTypeName)) {
                        fromMthd = fromCl.getMethod("getString", fromPar);
                    } else if (NUMBER.equalsIgnoreCase(fromTypeName) ||
                               NUMERIC.equalsIgnoreCase(fromTypeName)) {
                        fromMthd = fromCl.getMethod("getBigDecimal", fromPar);
                    } else if (DATE.equalsIgnoreCase(fromTypeName) ||
                               TIMESTAMP.equalsIgnoreCase(fromTypeName)) {
                        fromMthd = fromCl.getMethod("getTimestamp", fromPar);
                    } else if (BLOB.equalsIgnoreCase(fromTypeName) ||
                               BYTEA.equalsIgnoreCase(fromTypeName)) {
                        fromMthd = fromCl.getMethod("getBytes", fromPar);
                    } else {
                        throw new Exception ("Unknown datatype: " + fromTypeName + " in source db");
                    }
                    fromMthd.invoke(fromRs,i);

                    Class toCl=Class.forName("java.sql.PreparedStatement");
                    Class[] toPar=new Class[2];
                    toPar[0]=Integer.TYPE;
                    Method toMthd = null;
                    if (VARCHAR2.equalsIgnoreCase(toTypeName) ||
                        CHAR.equalsIgnoreCase(toTypeName) ||
                        BPCHAR.equalsIgnoreCase(toTypeName)) {
                        toPar[1] = Class.forName("java.lang.String");
                        toMthd = toCl.getMethod("setString", toPar);
                    } else if (NUMBER.equalsIgnoreCase(toTypeName) ||
                               NUMERIC.equalsIgnoreCase(toTypeName)) {
                        toPar[1] = Class.forName("java.math.BigDecimal");
                        toMthd = toCl.getMethod("setBigDecimal", toPar);
                    } else if (DATE.equalsIgnoreCase(toTypeName) ||
                               TIMESTAMP.equalsIgnoreCase(toTypeName)) {
                        toPar[1] = Class.forName("java.sql.Timestamp");
                        toMthd = toCl.getMethod("setTimestamp", toPar);
                    } else if (BLOB.equalsIgnoreCase(toTypeName) ||
                               BYTEA.equalsIgnoreCase(toTypeName)) {
                        toPar[1] = byte[].class;
                        toMthd = toCl.getMethod("setBytes", toPar);
                    } else {
                        throw new Exception ("Unknown datatype: " + toTypeName + " in target db");
                    }
                    toMthd.invoke(toStmt,i,fromMthd.invoke(fromRs,i));
                }
                toStmt.executeUpdate();
                row++;
                if (row % 10000 == 0) {
                    log.info(row + " rows");
                }
            }
            log.info(row + " rows");
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("Internal error",e);
            throw new Exception(e);
        }
    }

    public void callGetTableSnapshot(Connection conn,
                                     String proc,
                                     String tableName,
                                     String filter,
                                     java.util.Date previousDate,
                                     java.util.Date startDate,
                                     int replStatus,
                                     String workSchema,
                                     String rplSchema,
                                     int sessionId) throws Exception{
        try {
            CallableStatement cs = conn.prepareCall("{call " + proc + "}");
            cs.setObject(1,tableName);
            cs.setObject(2,filter);
            cs.setObject(3, toSQLTimestamp(previousDate));
            cs.setObject(4, toSQLTimestamp(startDate));
            cs.setObject(5,replStatus);
            cs.setObject(6,workSchema);
            cs.setObject(7,rplSchema);
            cs.setObject(8,sessionId);
            cs.executeUpdate();
            cs.close();
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("Internal error",e);
            throw new Exception(e);
        }
    }

    public void callSynchronize(Connection conn,
                                String proc,
                                String tableName,
                                int replStatus,
                                String workSchema,
                                String rplSchema,
                                int sessionId) throws Exception{
        try {
            CallableStatement cs = conn.prepareCall("{call " + proc + "}");
            cs.setObject(1,tableName);
            cs.setObject(2,replStatus);
            cs.setObject(3,workSchema);
            cs.setObject(4,rplSchema);
            cs.setObject(5,sessionId);
            cs.executeUpdate();
            cs.close();
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("Internal error",e);
            throw new Exception(e);
        }
    }

    public void testTable(String table, Connection fromConn, Connection toConn) {
        try {
            DatabaseMetaData fromMetaData = fromConn.getMetaData();
            String fromDbName = fromMetaData.getDatabaseProductName();
            DatabaseMetaData toMetaData = toConn.getMetaData();
            String toDbName = toMetaData.getDatabaseProductName();

            Statement fromStmt = fromConn.createStatement();
            fromStmt.setFetchSize(10);
            ResultSet fromRs = fromStmt.executeQuery("select * from " + table);
            ResultSetMetaData fromRsMetaData = fromRs.getMetaData();
            int numberOfColumns = fromRsMetaData.getColumnCount();

            Statement toTestStmt = toConn.createStatement();
            toTestStmt.setFetchSize(10);
            ResultSet toTestRs = toTestStmt.executeQuery("select * from " + table);
            ResultSetMetaData toRsMetaData = toTestRs.getMetaData();

            log.info("Table: " + table);
            log.info("DB: " + fromDbName);
            for (int i = 1; i < numberOfColumns + 1; i++) {
                String columnName = fromRsMetaData.getColumnName(i);
                String tableName = fromRsMetaData.getTableName(i);
                String typeName = fromRsMetaData.getColumnTypeName(i);
                log.info("column name=" + columnName +
                         " table=" + tableName +
                         " type=" + typeName +
                         "(" + fromRsMetaData.getColumnType(i) + ") " +
                         "precision=" + fromRsMetaData.getPrecision(i) +
                         " scale= " + fromRsMetaData.getScale(i));
            }

            log.info("DB: " + toDbName);
            for (int i = 1; i < numberOfColumns + 1; i++) {
                String columnName = toRsMetaData.getColumnName(i);
                String tableName = toRsMetaData.getTableName(i);
                String typeName = toRsMetaData.getColumnTypeName(i);
                log.info("column name=" + columnName +
                         " table=" + tableName +
                         " type=" + typeName +
                         "(" + toRsMetaData.getColumnType(i) + ") " +
                         "precision=" + toRsMetaData.getPrecision(i) +
                         " scale= " + toRsMetaData.getScale(i));
            }

        } catch (Throwable e) {
            e.printStackTrace();
            log.error("Internal error",e);
        }
    }

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat ("M/d/yyyy");

    public void replicate(String pSubscriber, Connection pMasterConn, Connection pSlaveConn) {
        int subscriberId = 0;
        int groupId = 0;
        int replMask = 0;
        java.util.Date previousDate = null;
        java.util.Date startDate = null;
        String masterSchema = null;
        String slaveSchema = null;
        String masterRplSchema = null;
        String slaveRplSchema = null;
        try {
            pMasterConn.setAutoCommit(false);
            pSlaveConn.setAutoCommit(false);
            DatabaseMetaData masterMetaData = pMasterConn.getMetaData();
            String masterDbName = masterMetaData.getDatabaseProductName();
            DatabaseMetaData slaveMetaData = pSlaveConn.getMetaData();
            String slaveDbName = slaveMetaData.getDatabaseProductName();

            PreparedStatement stmt = null;
            stmt = pMasterConn.prepareStatement(
                    "select repl_subscriber_id, repl_group_id, master_clw_schema, slave_clw_schema, master_rpl_schema, slave_rpl_schema, mask " +
                    "from rpl_repl_subscriber where name = ? AND status = ?");
            stmt.setString(1, pSubscriber);
            stmt.setString(2, "Active");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            subscriberId = rs.getInt(1);
            groupId = rs.getInt(2);
            masterSchema = rs.getString(3).toLowerCase();
            slaveSchema = rs.getString(4).toLowerCase();
            masterRplSchema = rs.getString(5).toLowerCase();
            slaveRplSchema = rs.getString(6).toLowerCase();
            replMask = rs.getInt(7);
            stmt.close();
            rs.close();

            stmt = pMasterConn.prepareStatement(
                    "select count(*) from rpl_repl_session where repl_subscriber_id = ?");
            stmt.setInt(1, subscriberId);
            rs = stmt.executeQuery();
            int count = 0;
            rs.next();
            count = rs.getInt(1);
            stmt.close();
            rs.close();

            if (count > 0) {
                stmt = pMasterConn.prepareStatement(
                        "select max(start_date) from rpl_repl_session where repl_subscriber_id = ?");
                stmt.setInt(1, subscriberId);
                rs = stmt.executeQuery();
                rs.next();
                previousDate = rs.getTimestamp(1);
                stmt.close();
                rs.close();
            } else {
                previousDate = dateFormatter.parse("1/1/1980");
            }

            Statement st = pMasterConn.createStatement();
            rs = st.executeQuery("select rpl_repl_session_seq.nextval from dual");
            rs.next();
            int sessionId = rs.getInt(1);
            st.close();
            rs.close();

            startDate = new java.util.Date(System.currentTimeMillis());
            stmt = pMasterConn.prepareStatement(
                    "insert into rpl_repl_session values(?,?,?,null,?,?,?,?,?)");
            stmt.setInt(1, sessionId);
            stmt.setInt(2, subscriberId);
            stmt.setTimestamp(3, toSQLTimestamp(startDate));
            stmt.setTimestamp(4, toSQLTimestamp(previousDate));
            stmt.setString(5, "Replication");
            stmt.setTimestamp(6, toSQLTimestamp(startDate));
            stmt.setString(7, "Replication");
            stmt.setTimestamp(8, toSQLTimestamp(startDate));
            stmt.executeUpdate();
            stmt.close();

            stmt = pMasterConn.prepareStatement(
                    "select table_name, filter from rpl_repl_table where repl_group_id = ? AND status = ? order by repl_order");
            stmt.setInt(1, groupId);
            stmt.setString(2, "Active");
            rs = stmt.executeQuery();
            ArrayList tables = new ArrayList();
            while(rs.next()) {
                String[] attr = new String[2];
                attr[0] = rs.getString(1); // table_name
                attr[1] = rs.getString(2); // filter
                tables.add(attr);
            }
            stmt.close();
            rs.close();

            for (int i = 0; i < tables.size(); i++) {
                String[] attr = (String[])tables.get(i);
                log.info("table: " + attr[0] + " -- start");
// create snapshot in master db
                log.info("table: " + attr[0] + " -- get snapshot " + masterDbName);
                callGetTableSnapshot(pMasterConn,
                                     "get_table_snapshot(?,?,?,?,?,?,?,?)",
                                     attr[0],
                                     attr[1],
                                     null,    // previousDate
                                     null,    // startDate
                                     replMask,
                                     masterSchema,
                                     masterRplSchema,
                                     sessionId);
// create snapshot in slave db
                log.info("table: " + attr[0] + " -- get snapshot " + slaveDbName);
                callGetTableSnapshot(pSlaveConn,
                                     "get_table_snapshot(?,?,?,?,?,?,?,?)",
                                     attr[0],
                                     attr[1],
                                     null,    // previousDate
                                     null,    // startDate
                                     replMask,
                                     slaveSchema,
                                     slaveRplSchema,
                                     sessionId);
// copy new and updated records from master db to slave db
                log.info("table: " + attr[0] + " -- copy");
                String fromTable = "rpl_d_" + attr[0].substring(4).toLowerCase();
                String toTable = "rpl_e_" + attr[0].substring(4).toLowerCase();
                copyTable(fromTable, toTable, pMasterConn, pSlaveConn);
// copy new and updated records from slave db to master db
                copyTable(fromTable, toTable, pSlaveConn, pMasterConn);

// synchronize master db
                log.info("table: " + attr[0] + " -- synchronize " + masterDbName);
                callSynchronize(pMasterConn,
                                "synchronize_table(?,?,?,?,?)",
                                attr[0],
                                replMask,
                                masterSchema,
                                masterRplSchema,
                                sessionId);


// synchronize slave db
                log.info("table: " + attr[0] + " -- synchronize " + slaveDbName);
                callSynchronize(pSlaveConn,
                                "synchronize_table(?,?,?,?,?)",
                                attr[0],
                                replMask,
                                slaveSchema,
                                slaveRplSchema,
                                sessionId);

                log.info("table: " + attr[0] + " -- end");
            }
// close session
            java.util.Date endDate = new java.util.Date(System.currentTimeMillis());
            stmt = pMasterConn.prepareStatement(
                    "update rpl_repl_session set end_date = ?, mod_by = ?, mod_date = ? where repl_session_id = ?");
            stmt.setTimestamp(1, toSQLTimestamp(endDate));
            stmt.setString(2, "Replication");
            stmt.setTimestamp(3, toSQLTimestamp(endDate));
            stmt.setInt(4, sessionId);
            stmt.executeUpdate();
            stmt.close();
//            if (1==1) throw new Exception("Test,Test,Test...");
            pMasterConn.commit();
            pSlaveConn.commit();
        } catch (Throwable e) {
            try{
                pMasterConn.rollback();
                pSlaveConn.rollback();
                e.printStackTrace();
                log.error("Internal error",e);
            } catch (Throwable th) {
                e.printStackTrace();
            }
        } finally {
            try{
             pMasterConn.close();
             pSlaveConn.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

    }


    public static void main( String args[]) {

    }

public static java.sql.Timestamp toSQLTimestamp (java.util.Date value){
    java.sql.Timestamp result = null;
    if (null != value) {
        result = new java.sql.Timestamp(value.getTime());
    }
    return result;
}

}

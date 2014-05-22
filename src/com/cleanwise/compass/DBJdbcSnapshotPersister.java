package com.cleanwise.compass;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.sql.*;

import org.apache.log4j.Logger;
import org.compass.gps.device.jdbc.*;
import org.compass.gps.device.jdbc.mapping.*;
import org.compass.gps.device.jdbc.snapshot.*;
import com.cleanwise.service.api.util.*;
import oracle.sql.*;

/**
 * A DB (Database) based snapshot persister. The persister will store and
 * load the alias snapshots to the Database.
 */
public class DBJdbcSnapshotPersister implements JdbcSnapshotPersister {

    private static final Logger log = Logger.getLogger(DBJdbcSnapshotPersister.class);

    protected DataSource dataSource;
    protected List mappings = new ArrayList();
    protected String tablePrefix = "INDEX_SNAPSHOT_";

    protected final String ORACLE = "Oracle";
    protected final String MYSQL = "MySQL";
    protected String ORACLE_USER_TABLES_START = "select * from user_tables where TABLE_NAME = '";
    protected String ORACLE_USER_TABLES_END = "'";
    protected String MYSQL_USER_TABLES_START = "select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = '";
    protected String MYSQL_USER_TABLES_END = "'";
    protected String ORACLE_CREATE_TABLE_START = "create table ";
    protected String ORACLE_CREATE_TABLE_END = " (VALUE_ BLOB, SIZE_ NUMBER, LF_ TIMESTAMP(6))";
    protected String MYSQL_CREATE_TABLE_START = "create table ";
    protected String MYSQL_CREATE_TABLE_END = " (VALUE_ LONGBLOB, SIZE_ INTEGER, LF_ DATETIME)";
    protected String SELECT_START = "select VALUE_ from ";
    protected String SELECT_UPDATE_START = "select VALUE_ from ";
    protected String SELECT_UPDATE_END = " for update";
    protected String ORACLE_INSERT_START = "insert into ";
    protected String ORACLE_INSERT_END = " values(empty_blob(), 0, null)";
    protected String MYSQL_INSERT_START = "insert into ";
    protected String MYSQL_INSERT_END = " values(null, 0, null)";
    protected String UPDATE_START = "update ";
    protected String UPDATE_END = " set VALUE_=?, SIZE_=?, LF_=?";


    public DBJdbcSnapshotPersister() {
    }

    public DBJdbcSnapshotPersister(DataSource dataSource, List mappings) {
        this.dataSource = dataSource;
        this.mappings = mappings;
    }

    public DBJdbcSnapshotPersister(DataSource dataSource, List mappings, String tablePrefix) {
        this(dataSource, mappings);
        this.tablePrefix = tablePrefix;
    }

    public JdbcSnapshot load() throws JdbcGpsDeviceException {
      log.info("load snapshot. start.");
      JdbcSnapshot snapshot = new JdbcSnapshot();
      for (Iterator it = mappings.iterator(); it.hasNext();) {
          ResultSetToResourceMapping mapping = (ResultSetToResourceMapping) it.next();
          if (mapping.supportsVersioning()) {
              JdbcAliasSnapshot aliasSnapshot = load(mapping.getAlias());
              snapshot.putAliasSnapshot(aliasSnapshot);
          }
      }
      log.info("load snapshot. end.");
      return snapshot;
    }

    public void save(JdbcSnapshot snapshot) throws JdbcGpsDeviceException {
      log.info("save snapshot. start.");
      for (Iterator it = mappings.iterator(); it.hasNext();) {
          ResultSetToResourceMapping mapping = (ResultSetToResourceMapping) it.next();
          if (mapping.supportsVersioning() && snapshot.getAliasSnapshot(mapping.getAlias()) != null) {
              save(snapshot.getAliasSnapshot(mapping.getAlias()));
          }
      }
      log.info("save snapshot. end.");
    }

    public JdbcAliasSnapshot load(String aliasName) throws JdbcGpsDeviceException {
      log.info("load snapshot " + aliasName);
      Connection conn = JdbcUtils.getConnection(dataSource);

      PreparedStatement ps = null;
      ResultSet rs = null;
      Statement stmt;
      try {
        DatabaseMetaData metaData = conn.getMetaData();
        String databaseName = metaData.getDatabaseProductName();
        stmt = conn.createStatement();
        String sql = null;
        String s = null;
        if (ORACLE.equals(databaseName)) {
          sql = ORACLE_USER_TABLES_START + tablePrefix + aliasName.toUpperCase() + ORACLE_USER_TABLES_END;
        } else if (MYSQL.equals(databaseName)) {
          sql = MYSQL_USER_TABLES_START + tablePrefix + aliasName.toUpperCase() + MYSQL_USER_TABLES_END;
        } else {
          throw new JdbcGpsDeviceException("Unknown database");
        }
        rs = stmt.executeQuery(sql);
        if (!rs.next()) {
          log.info("No table for snapshot data [" + aliasName + "] found, creating a new one");
          if (ORACLE.equals(databaseName)) {
            sql = ORACLE_CREATE_TABLE_START + tablePrefix + aliasName + ORACLE_CREATE_TABLE_END;
          } else if (MYSQL.equals(databaseName)) {
            sql = MYSQL_CREATE_TABLE_START + tablePrefix + aliasName + MYSQL_CREATE_TABLE_END;
          } else {
            throw new JdbcGpsDeviceException("Unknown database");
          }
          stmt.executeUpdate(sql);
          return new JdbcAliasSnapshot(aliasName);
        } else {
          sql = SELECT_START + tablePrefix + aliasName;
          rs = stmt.executeQuery(sql);
          if (!rs.next()) {
            log.info("No snapshot data [" + aliasName + "] found, creating a new one");
          } else {
            ObjectInputStream objStream = null;
            try {
              objStream = new ObjectInputStream(new ByteArrayInputStream(rs.getBytes(1)));
            }catch (Exception e) {
              log.error("Ops!", e);
              return new JdbcAliasSnapshot(aliasName);
            }
            return (JdbcAliasSnapshot) objStream.readObject();
          }
        }
      } catch (Exception e) {
          log.error("Failed to load jdbc snapshot [" + aliasName + "]", e);
          e.printStackTrace();
          throw new JdbcGpsDeviceException("Failed to load jdbc snapshot [" + aliasName + "]", e);
      } finally {
          JdbcUtils.closeResultSet(rs);
          JdbcUtils.closeStatement(ps);
          JdbcUtils.closeConnection(conn);
      }

      return new JdbcAliasSnapshot(aliasName);
    }

    public void save(JdbcAliasSnapshot snapshot) throws JdbcGpsDeviceException {
      log.info("save snapshot [" + snapshot.getAlias() + "]");
      Connection conn = JdbcUtils.getConnection(dataSource);
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      Statement stmt = null;
      try {
        DatabaseMetaData metaData = conn.getMetaData();
        String databaseName = metaData.getDatabaseProductName();
        stmt = conn.createStatement();
        String sql = null;
        if (ORACLE.equals(databaseName)) {
          sql = ORACLE_USER_TABLES_START + tablePrefix + snapshot.getAlias().toUpperCase() + ORACLE_USER_TABLES_END;
        } else if (MYSQL.equals(databaseName)) {
          sql = MYSQL_USER_TABLES_START + tablePrefix + snapshot.getAlias().toUpperCase() + MYSQL_USER_TABLES_END;
        } else {
          throw new JdbcGpsDeviceException("Unknown database");
        }
        rs = stmt.executeQuery(sql);
        if (!rs.next()) {
          log.info("No table for snapshot data [" + snapshot.getAlias() + "] found, creating a new one");
          if (ORACLE.equals(databaseName)) {
            sql = ORACLE_CREATE_TABLE_START + tablePrefix + snapshot.getAlias() + ORACLE_CREATE_TABLE_END;
          } else if (MYSQL.equals(databaseName)) {
            sql = MYSQL_CREATE_TABLE_START + tablePrefix + snapshot.getAlias() + MYSQL_CREATE_TABLE_END;
          } else {
            throw new JdbcGpsDeviceException("Unknown database");
          }
          stmt.executeUpdate(sql);
        }
        sql = SELECT_UPDATE_START + tablePrefix + snapshot.getAlias() + SELECT_UPDATE_END;
        rs = stmt.executeQuery(sql);
        if (!rs.next()) {
          log.info("No snapshot data [" + snapshot.getAlias() + "] found, creating a new one");
          if (ORACLE.equals(databaseName)) {
            sql = ORACLE_INSERT_START + tablePrefix + snapshot.getAlias() + ORACLE_INSERT_END;
          } else if (MYSQL.equals(databaseName)) {
            sql = MYSQL_INSERT_START + tablePrefix + snapshot.getAlias() + MYSQL_INSERT_END;
          } else {
            throw new JdbcGpsDeviceException("Unknown database");
          }
          stmt.executeUpdate(sql);
          sql = SELECT_UPDATE_START + tablePrefix + snapshot.getAlias() + SELECT_UPDATE_END;
          rs = stmt.executeQuery(sql);
          rs.next();
        }

        BLOB snapshotBlob = null;
        if (ORACLE.equals(databaseName)) {
          snapshotBlob = (BLOB) rs.getBlob(1);
        }
        rs.close();
        stmt.close();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(baos);
        objStream.writeObject(snapshot);
        objStream.flush();
        objStream.close();
        sql = UPDATE_START + tablePrefix + snapshot.getAlias() + UPDATE_END;
        pstmt = conn.prepareStatement(sql);
        if (ORACLE.equals(databaseName)) {
          setByteToOracleBlob(snapshotBlob, baos.toByteArray());
          pstmt.setBlob(1, snapshotBlob);
        } else {
          ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
          pstmt.setBinaryStream(1, is, is.available());
        }
        pstmt.setInt(2, baos.toByteArray().length);
        pstmt.setTimestamp(3, DBAccess.toSQLTimestamp(new Date(System.currentTimeMillis())));
        pstmt.executeUpdate();
        pstmt.close();
        log.info("Snapshot data saved [" + snapshot.getAlias() + "] size [" + baos.toByteArray().length + "bytes]");
      } catch (Exception e) {
        log.error("Failed to save jdbc snapshot [" + snapshot.getAlias() + "]", e);
        throw new JdbcGpsDeviceException("Failed to save jdbc snapshot [" + snapshot.getAlias() + "]", e);
      } finally {
          JdbcUtils.closeResultSet(rs);
          JdbcUtils.closeStatement(pstmt);
          JdbcUtils.closeStatement(stmt);
          JdbcUtils.closeConnection(conn);
      }
    }

    protected int setByteToOracleBlob(BLOB blob, byte[] data) throws Exception {
        OutputStream out = blob.setBinaryStream(0);
        out.write(data);
        out.flush();
        out.close();
        return data.length;
    }


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) throws Exception
    {
    }
}

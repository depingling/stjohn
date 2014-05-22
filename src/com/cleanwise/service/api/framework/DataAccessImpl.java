package com.cleanwise.service.api.framework;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import oracle.jdbc.OracleTypes;
import java.sql.Connection;
import oracle.sql.BLOB;
import java.io.OutputStream;
import java.sql.DatabaseMetaData;
import java.io.ByteArrayInputStream;

/**
 * Main class for the dataAccess objects
 * @author
 */
public abstract class DataAccessImpl implements DataAccess {
    protected static final String ORACLE = "Oracle";
    protected static final String EDB = "EnterpriseDB";
    protected static String databaseName = null;
    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@param int the offset to use which is useful when using 1 query to populate multiple objects
    *@returns a populated <xsl:value-of select="@javaname"/>Data Object.
    *@throws SQLException
    */
    public abstract ValueObject parseResultSet(ResultSet rs,int offset) throws SQLException;

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a <xsl:value-of select="@javaname"/>Data Object
    *using the parseResultSet method.
    */
    public abstract String getSelectColumns();

    /**
    *@Returns a count of the number of columns the <xsl:value-of select="@javaname"/>Data Object represents.
    */
    public abstract int getColumnCount();

    protected static String getDatabaseName(Connection conn) throws SQLException{
        if (databaseName == null) {
            DatabaseMetaData metaData = conn.getMetaData();
            databaseName = metaData.getDatabaseProductName();
        }
        return databaseName;
    }


    protected static BLOB toBlob(Connection conn, byte[] data) throws SQLException {
        oracle.sql.BLOB blob = null;
        try{
            if(data!=null) {
                blob = createTemporaryBlob(conn);
                setByteToOracleBlob(blob, data);
            }
            return blob;
        }
        catch (Exception e) {
            e.printStackTrace();
            freeTemporary(blob);
            throw new SQLException(e.getMessage());
        }
    }

    protected static void freeTemporary(BLOB  blob) throws SQLException {
        if(blob!=null){
            blob.freeTemporary();
        }
    }

    protected static BLOB createTemporaryBlob(Connection con)
            throws SQLException {
        CallableStatement cst = null;
        try {
            cst = con.prepareCall("{call dbms_lob.createTemporary(?, false, dbms_lob.SESSION)}");
            cst.registerOutParameter(1, OracleTypes.BLOB);
            cst.execute();
            return (BLOB)cst.getBlob(1);
        } finally {
            if (cst != null) { cst.close(); }
        }
    }

    protected static int setByteToOracleBlob(oracle.sql.BLOB blob, byte[] data) throws Exception {
        OutputStream out = blob.setBinaryStream(1L);
        out.write(data);
        out.flush();
        out.close();
        return data.length;
    }

}

package com.cleanwise.service.api.session;

import java.io.*;
import java.rmi.*;
import java.sql.*;
import java.util.Date;
import javax.ejb.*;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.InboundDataAccess;
import oracle.sql.*;
import org.apache.log4j.Category;

public class InterchangeBean extends ApplicationServicesAPI{
	
  private static Category log = Category.getInstance(InterchangeBean.class.getName());
  private static final String className = "InterchangeBean";

 public InterchangeBean() {
 }

 public void ejbCreate() throws CreateException, RemoteException {}

 public void saveInboundData(InboundData inbound, byte[] enc, byte[] dec) throws RemoteException {
   Connection conn = null;
   try {
     conn = getConnection();
     //Create record for blob
     Statement stmt = conn.createStatement();
     ResultSet rs = stmt.executeQuery("SELECT CLW_INBOUND_SEQ.NEXTVAL FROM DUAL");
     rs.next();
     int keyId = rs.getInt(1);
     rs.close();
     stmt.close();

     String insertSql =
             " insert into CLW_INBOUND ( " +
                " INBOUND_ID," +
                " FILE_NAME," +
                " PARTNER_KEY," +
                " URL," +
                " ENCRYPT_BINARY_DATA ," +
                " DECRYPT_BINARY_DATA," +
                " ADD_DATE," +
                " ADD_BY," +
                " MOD_DATE," +
                " MOD_BY" +
                " ) values  (?,?,?,?,?,?,?,?,?,?) ";


     PreparedStatement pstmt = conn.prepareStatement(insertSql);
     pstmt.setInt(1, keyId);
     pstmt.setString(2, inbound.getFileName());
     pstmt.setString(3, inbound.getPartnerKey());
     pstmt.setString(4, inbound.getUrl());

     if (ORACLE.equals(databaseName)) {
         pstmt.setBlob(5, BLOB.empty_lob());
         pstmt.setBlob(6, BLOB.empty_lob());
     } else if (EDB.equals(databaseName)) {
         pstmt.setBytes(5, null);
         pstmt.setBytes(6, null);
     } else {
       throw new Exception("Unknown database");
     }

     pstmt.setTimestamp(7, DBAccess.toSQLTimestamp(new Date(System.currentTimeMillis())));
     pstmt.setString(8, className);
     pstmt.setTimestamp(9, DBAccess.toSQLTimestamp(new Date(System.currentTimeMillis())));
     pstmt.setString(10, className);

     pstmt.executeUpdate();
     pstmt.close();

     updateBlobValue(conn, keyId, enc, dec);
   } catch (Exception e) {
     e.printStackTrace();
     throw new RemoteException(e.getMessage());
   } finally {
     try {
       if (conn != null) conn.close();
     } catch (Exception ex) {}
   }
 }

 private boolean updateBlobValue(Connection conn, int inboundId, byte[] enc, byte[] dec) throws Exception {

     Statement stmt = conn.createStatement();
     String sql = "select ENCRYPT_BINARY_DATA, DECRYPT_BINARY_DATA from CLW_INBOUND" +
             " where INBOUND_ID = " + inboundId +
             " for update";
     ResultSet rs = stmt.executeQuery(sql);
     rs.next();

     BLOB encBlob = null;
     BLOB decBlob = null;
     if (ORACLE.equals(databaseName)) {
         encBlob = (BLOB) rs.getBlob(1);
         decBlob = (BLOB) rs.getBlob(2);
         setByteToOracleBlob(encBlob, enc);
         setByteToOracleBlob(decBlob, dec);
     }
     rs.close();
     stmt.close();

     String updateBlobSql =
             "update CLW_INBOUND set ENCRYPT_BINARY_DATA=?," +
             " DECRYPT_BINARY_DATA=?" +
             " where INBOUND_ID = " + inboundId;

     PreparedStatement pstmt = conn.prepareStatement(updateBlobSql);
     if (ORACLE.equals(databaseName)) {
         pstmt.setBlob(1, encBlob);
         pstmt.setBlob(2, decBlob);
     } else if (EDB.equals(databaseName)) {
         ByteArrayInputStream encIs = new ByteArrayInputStream(enc);
         pstmt.setBinaryStream(1, encIs, encIs.available());
         ByteArrayInputStream decIs = new ByteArrayInputStream(dec);
         pstmt.setBinaryStream(2, decIs, decIs.available());
         log.info("DatabaseName: " + databaseName);
//         pstmt.setBytes(1, enc);
//         pstmt.setBytes(2, dec);
     } else {
       throw new Exception("Unknown database");
     }
     log.info("EXecuteUpdate");
     pstmt.executeUpdate();

     pstmt.close();

     return true;
 }

 protected int setByteToOracleBlob(BLOB blob, byte[] data) throws Exception {

     OutputStream out = blob.setBinaryStream(0);
     out.write(data);
     out.flush();
     out.close();

     return data.length;

 }


    public byte[] getInboundDecContent(int pInboundId) throws RemoteException {
        Connection conn = null;
        try {

            conn = getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(InboundDataAccess.INBOUND_ID, pInboundId);

            InboundDataVector v = InboundDataAccess.select(conn, dbc);

            if (!v.isEmpty()) {
                return ((InboundData) v.get(0)).getDecryptBinaryData();
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public void updateInboundDecContent(int pInboundId, byte[] pData, String pUser) throws RemoteException {
        Connection conn = null;
        try {

            conn = getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(InboundDataAccess.INBOUND_ID, pInboundId);

            InboundDataVector v = InboundDataAccess.select(conn, dbc);
            if (!v.isEmpty()) {
                InboundData inboundData = ((InboundData) v.get(0));
                inboundData.setDecryptBinaryData(pData);
                inboundData.setModBy(pUser);
                InboundDataAccess.update(conn, inboundData);
            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public int saveInboundData(InboundData pInbound, String pUser) throws RemoteException {
        Connection conn = null;
        try {

            conn = getConnection();

            if (pInbound.getInboundId() == 0) {
                pInbound.setAddBy(pUser);
                pInbound.setModBy(pUser);
                pInbound = InboundDataAccess.insert(conn, pInbound);
            } else {
                pInbound.setModBy(pUser);
                InboundDataAccess.update(conn, pInbound);
            }

            return pInbound.getInboundId();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }


}

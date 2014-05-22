package com.cleanwise.service.apps.dataexchange;

import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleTypes;
import oracle.sql.BLOB;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.apps.ClientServicesAPI;



public class BlobToE3FileLoader extends ClientServicesAPI {
    private static Logger log = Logger.getLogger(BlobToE3FileLoader.class);
    
    public static void main (String args[]) throws Exception {
    	String selectSql = "select content_id, binary_data from CLW_CONTENT where  binary_data is not null and content_system_ref is null";
    	String updateSql = "UPDATE CLW_CONTENT SET MOD_DATE = sysdate,MOD_BY = ?,CONTENT_SERVER = ?,CONTENT_SYSTEM_REF = ?,STORAGE_TYPE_CD = ? WHERE CONTENT_ID = ?";
    	//String updateSql = "UPDATE CLW_CONTENT SET MOD_DATE = sysdate,MOD_BY = ?,BINARY_DATA = ?,CONTENT_SERVER = ?,CONTENT_SYSTEM_REF = ?,STORAGE_TYPE_CD = ? WHERE CONTENT_ID = ?";
    	String modBy = "BlobToE3FileLoader";
    	String storageTypeCd = RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE;
    	BlobToE3FileLoader test = new BlobToE3FileLoader();
    	test.getAPIAccess();
    	String storageSystemServername = System.getProperty("storage.system.servername");
    	
    	Connection conn = null;
    	log("************** Parser: start : " + new java.util.Date() + " ****************");
    	try{
    		conn = test.getConnection();
    		conn.setAutoCommit(false);
    		Statement stmt = conn.createStatement();
    		PreparedStatement pstmt = conn.prepareStatement(updateSql);
    		
            ResultSet rs = stmt.executeQuery(selectSql);
            int cnt = 0;
            while (rs.next()){
            	cnt++;
            	int contentId = rs.getInt(1);            	
            	String contentSystemRef = test.getAPIAccess().getContentAPI().writeContentToE3StorageSystem(rs.getBytes(2), "IMAGE_");
            	int i = 1;
            	pstmt.setString(i++, modBy);
            	//pstmt.setBlob(i++, test.toBlob(conn,null));
            	pstmt.setString(i++, storageSystemServername);
            	pstmt.setString(i++, contentSystemRef);
            	pstmt.setString(i++, storageTypeCd);
            	pstmt.setInt(i++, contentId);
            	pstmt.addBatch();
            	if (cnt % 100 == 0){
            		pstmt.executeBatch();
            		conn.commit();
            		log("Processed Record: " + cnt);
            	}
            }
            	
            log("Total processed record: " + cnt);
            conn.commit();
            stmt.close();
            log("************** Parser: end : " + new java.util.Date() + " ****************");
    	}catch(Exception e){
    		conn.close();
    	}
    }
    
    private static void log(String message){
    	log.info(message);
    }
    public BLOB toBlob(Connection conn, byte[] data) throws Exception {
        oracle.sql.BLOB blob = null;
        try{
        	if (data!=null){
        		blob = createTemporaryBlob(conn);            
            	setByteToOracleBlob(blob, data);
        	}
            return blob;}
        catch (Exception e) {
            e.printStackTrace();
            freeTemporary(blob);
            throw new Exception(e.getMessage());
        }
    }

    private void freeTemporary(BLOB  blob) throws SQLException {
        if(blob!=null){
            blob.freeTemporary();
        }
    }

    public static BLOB createTemporaryBlob(Connection con)
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

    protected int setByteToOracleBlob(oracle.sql.BLOB blob, byte[] data) throws Exception {

        OutputStream out = blob.setBinaryStream(1L);
        out.write(data);
        out.flush();
        out.close();

        return data.length;

    }

}

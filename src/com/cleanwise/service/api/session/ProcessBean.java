




package com.cleanwise.service.api.session;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.process.ProcessActive;
import com.cleanwise.service.api.process.ProcessSchema;
import com.cleanwise.service.api.process.TaskActive;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.blob.storage.util.BlobStorageAccess;
import java.io.ByteArrayInputStream;
import oracle.sql.BLOB;

import javax.ejb.CreateException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import java.util.List;
import org.apache.log4j.Category;

/**
 * Title:        ProcessBean
 * Description:  Bean implementation for Process Session Bean
 * Purpose:      Ejb for scheduled process management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         05.04.2007
 * Time:         14:36:13
 *
 * @author Alexander Chickin, Evgeny Vlasov TrinitySoft, Inc.
 */

public class ProcessBean extends ApplicationServicesAPI {
    private static final String className = "ProcessBean";
    private static final Category log = Category.getInstance(ProcessBean.class);
    private BlobStorageAccess bsa = new BlobStorageAccess();
	 
    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @throws javax.ejb.CreateException if an error occurs
     * @throws java.rmi.RemoteException  if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }

    /**
     * gets process
     *
     * @param id identifier
     * @return process data
     * @throws RemoteException if an errors
     */
    public ProcessData getProcess(int id) throws RemoteException {
        Connection conn = null;

        try {
            conn = getConnection();
            return ProcessDataAccess.select(conn, id);
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public int getActiveTemplateProcessId(String name) throws RemoteException {
        Connection conn = null;
        DBCriteria dbCrit;
        int id;

        try {
            conn = getConnection();
            dbCrit = new DBCriteria();
            dbCrit.addEqualTo(ProcessDataAccess.PROCESS_NAME, name);
            dbCrit.addEqualTo(ProcessDataAccess.PROCESS_TYPE_CD,RefCodeNames.PROCESS_TYPE_CD.TEMPLATE);
            dbCrit.addEqualTo(ProcessDataAccess.PROCESS_STATUS_CD,RefCodeNames.PROCESS_STATUS_CD.ACTIVE);

            IdVector sqlRes = ProcessDataAccess.selectIdOnly(conn, dbCrit);
            if (sqlRes.size() > 1) {
                throw new Exception("Multiple process id for name: " + name);
            }
            if (sqlRes.size() == 0) {
                throw new Exception("Data not found for name: " + name);
            }

            id = ((Integer) sqlRes.get(0)).intValue();

            return id;
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }
    /**
     * initialize process variables
     *
     * @param processId process identifier
     * @throws RemoteException if an errors
     */
    public void initProcessVariable(int processId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }


    /**
     * updates clw_process table
     *
     * @param process data
     * @return updated data
     * @throws RemoteException if an errors
     */
    public ProcessData updateProcessData(ProcessData process) throws RemoteException {
        Connection conn = null;

        try {
            conn = getConnection();
            return updateProcessData(conn,process);
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
    }


    /**
     * gets task property
     *
     * @param templateProcessId process template identifier
     * @return task property data collection
     * @throws RemoteException if an errors
     */
    public TaskPropertyDataVector getTaskPropertyByTemplateProcessId(int templateProcessId) throws RemoteException {

        Connection conn = null;
        DBCriteria dbCrit;

        try {
            conn = getConnection();
            //1. gets all task for template
            dbCrit = new DBCriteria();
            dbCrit.addEqualTo(TaskDataAccess.PROCESS_ID, templateProcessId);
            String tasksSQL = TaskDataAccess.getSqlSelectIdOnly(TaskDataAccess.TASK_ID, dbCrit);
            //2.gets task property
            dbCrit = new DBCriteria();
            dbCrit.addOneOf(TaskPropertyDataAccess.TASK_ID, tasksSQL);
            TaskPropertyDataVector taskProperty = TaskPropertyDataAccess.select(conn, dbCrit);
            //3.return result
            return taskProperty;

        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }

    }

    /**
     * gets process property
     *
     * @param processId process identifier
     * @return process property data collection
     * @throws RemoteException if an errors
     */
    public ProcessPropertyDataVector getProcessProperty(int processId) throws RemoteException {

       Connection conn = null;
       DBCriteria crit;
       try {
            conn = getConnection();
            crit = new DBCriteria();
            crit.addEqualTo(ProcessPropertyDataAccess.PROCESS_ID,processId);
            //return ProcessPropertyDataAccess.select(conn,crit);
            ProcessPropertyDataVector processPropertyDV = ProcessPropertyDataAccess.select(conn, crit);
            // STJ-6037
            if (Utility.isSet(processPropertyDV)) {
                ProcessPropertyData processProperty;
                for (int i = 0; i < processPropertyDV.size(); i++) {
                    processProperty = (ProcessPropertyData) processPropertyDV.get(i);
                    
                    if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(processProperty.getStorageTypeCd()) ||
						BlobStorageAccess.STORAGE_NFS.equals(processProperty.getStorageTypeCd()) ||
                        BlobStorageAccess.STORAGE_FTP.equals(processProperty.getStorageTypeCd())) {
                        if (Utility.isSet(processProperty.getBlobValueSystemRef())) {
                            byte[] blob = bsa.readBlob(processProperty.getStorageTypeCd(),
                                                       processProperty.getBinaryDataServer(),
                                                       processProperty.getBlobValueSystemRef());
                            processProperty.setVarValue(blob);
                        }
                    } /*else {
                        if (processProperty.getVarValue() != null) {
                            throw new Exception("ERROR. Blob has been read from DB. ProcessID: " + processProperty.getProcessId() +
                                      " (" + BlobStorageAccess.FIELD_VAR_VALUE + ")");
                        }
                    }*/
                }
            }
            return processPropertyDV;
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    
    private boolean updateProcessValueVariable(Connection conn, int processPropertyId, byte data[]) throws Exception {

    	log.info("updateProcessValueVariable() method => Begin; byte data.length="+
		  (data==null?data:data.length)+ " processPropertyId="+processPropertyId);
    	
        /*
        Statement stmn = conn.createStatement();
        String sql = "select VAR_VALUE, BINARY_DATA_SERVER, BLOB_VALUE_SYSTEM_REF, STORAGE_TYPE_CD from CLW_PROCESS_PROPERTY " +
                " where PROCESS_PROPERTY_ID = " + processPropertyId +
                " for update ";

        BLOB blob = null;
        byte[] blobValueBytes = null;
        
        ResultSet rs = stmn.executeQuery(sql);
        rs.next();
        if (ORACLE.equals(databaseName)) {
            blob = (BLOB) rs.getBlob(1); // Oracle DB
        } else if (EDB.equals(databaseName)) {
            log.info("updateProcessValueVariable() method: byte data[]; Postgres DB 1");
            blobValueBytes = rs.getBytes(1); // Enterprise DB (Postgres DB)
        }
        String binaryDataServer = rs.getString(2);
        String blobValueSystemRef = rs.getString(3);
        String storageTypeCd = rs.getString(4);
        
        log.info("updateProcessValueVariable() method: byte data[]; blobValueBytes from CLW_PROCESS_PROPERTY="+blobValueBytes);
        rs.close();
        stmn.close();
        */
        
        //STJ-6037
        Statement stmn = conn.createStatement();
        String sql = "select PROCESS_ID, " +
                             "BINARY_DATA_SERVER, " +
                             "BLOB_VALUE_SYSTEM_REF, " +
                             "STORAGE_TYPE_CD, " +
                             "VAR_VALUE " +
                "from CLW_PROCESS_PROPERTY " +
                " where PROCESS_PROPERTY_ID = " + processPropertyId +
                " for update";
        
        ResultSet rs = stmn.executeQuery(sql);
        rs.next();
       
        int processId = rs.getInt(1);
        String binaryDataServer = rs.getString(2);
        String blobValueSystemRef = rs.getString(3);
        String storageTypeCd = rs.getString(4);
        
        BLOB blob = null;
        if (data != null && data.length > 0) {
            if (ORACLE.equals(databaseName)) {
                blob = (BLOB) rs.getBlob(5); // Oracle DB
            }
        }
			
        
        rs.close();
        stmn.close();

        boolean writeVarValue = false;
        if (data != null) {
            if (!Utility.isSet(blobValueSystemRef)) {
                blobValueSystemRef = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_PROCESS_PROPERTY,
                                                                       BlobStorageAccess.FIELD_VAR_VALUE,
                                                                       processId);
            }
            boolean isOK = bsa.storeBlob(data, blobValueSystemRef);
    	    log.info("Saving Process Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());
           if (isOK && bsa.getStoredToStorageType() != null &&
                bsa.getStoredToHostName() != null	) { // blob has been stored
                storageTypeCd = bsa.getStoredToStorageType();
                binaryDataServer = bsa.getStoredToHostName();
            } else {
                log.error("ERROR. Blob will be written into DB. ProcessID: " + processId + 
                              " (" + BlobStorageAccess.FIELD_VAR_VALUE + ")");
                storageTypeCd = BlobStorageAccess.STORAGE_DB;
                binaryDataServer = BlobStorageAccess.getCurrentHost();
				blobValueSystemRef = null;
                writeVarValue = true;
            }
        }
        
        String updatePropSql = "update CLW_PROCESS_PROPERTY " +
                               "set BINARY_DATA_SERVER=?, " +
                               "BLOB_VALUE_SYSTEM_REF=?, " +
                               "STORAGE_TYPE_CD=?, " +
                               "MOD_BY=?, " +
                               "MOD_DATE=? " +
                               (writeVarValue ? ", VAR_VALUE=EMPTY_BLOB()" : "") +
                               " WHERE PROCESS_PROPERTY_ID = " + processPropertyId;
        PreparedStatement pstmt = conn.prepareStatement(updatePropSql);
        
        java.util.Date currDate = new java.util.Date(System.currentTimeMillis());

        pstmt.setString(1, binaryDataServer);
        pstmt.setString(2, blobValueSystemRef);
        pstmt.setString(3, storageTypeCd);
        pstmt.setString(4, className);
        pstmt.setTimestamp(5, DBAccess.toSQLTimestamp(currDate));
        pstmt.executeUpdate();
        pstmt.close();

        if (writeVarValue) {
            String getBlobSql = "select VAR_VALUE from CLW_PROCESS_PROPERTY " +
                " where PROCESS_PROPERTY_ID = " + processPropertyId;
            Statement stmn1 = conn.createStatement();
            ResultSet rs1 = stmn1.executeQuery(getBlobSql);
            rs1.next();
            blob = (BLOB) rs1.getBlob(1); // Oracle DB
			rs1.close();
			stmn1.close();
			String updateBlobSql = "update CLW_PROCESS_PROPERTY " +
                 "set VAR_VALUE=? WHERE PROCESS_PROPERTY_ID = " + processPropertyId;
            PreparedStatement pstmt1 = conn.prepareStatement(updateBlobSql);
            if (ORACLE.equals(databaseName)) {
				 setByteToOracleBlob(blob, data);
                 pstmt1.setBlob(1, blob);
            } else if (EDB.equals(databaseName)) {
                //ByteArrayInputStream isA = new ByteArrayInputStream(data);
                //pstmt.setBinaryStream(6, isA, isA.available());
                pstmt1.setBytes(1, data);
            } else {
                throw new Exception("Unknown database");
            }
			pstmt1.executeUpdate();
			pstmt1.close();
        }

        return true;
    }

    /*
    public boolean updateProcessValueVariable(Connection conn, int processPropertyId, byte data[]) throws Exception {

    	log.info("updateProcessValueVariable() method: byte data[]=> Begin; byte data[]="+data+ " processPropertyId="+processPropertyId);
    	
        Statement stmn = conn.createStatement();
        String sql = "select VAR_VALUE from CLW_PROCESS_PROPERTY " +
                " where PROCESS_PROPERTY_ID = " + processPropertyId +
                " for update ";

        BLOB blob = null;
        byte[] blobValueBytes = null;
        
        ResultSet rs = stmn.executeQuery(sql);
        rs.next();
        if (ORACLE.equals(databaseName)) {
        	blob = (BLOB) rs.getBlob(1); // Oracle DB
        } else if (EDB.equals(databaseName)) {
        	log.info("updateProcessValueVariable() method: byte data[]; Postgres DB 1");
        	blobValueBytes = rs.getBytes(1); // Enterprise DB (Postgres DB)
        }        
        //BLOB blob = (BLOB) rs.getBlob(1); //old code
        log.info("updateProcessValueVariable() method: byte data[]; blobValueBytes from CLW_PROCESS_PROPERTY="+blobValueBytes);
        rs.close();
        stmn.close();

        if (ORACLE.equals(databaseName)) { // Oracle DB
          if (blob != null) {
            setByteToOracleBlob(blob, data);

            String updateBlobSql =
                    "update CLW_PROCESS_PROPERTY set VAR_VALUE=? WHERE PROCESS_PROPERTY_ID = " + processPropertyId;

            PreparedStatement pstmt = conn.prepareStatement(updateBlobSql);
            pstmt.setBlob(1, blob);
            pstmt.executeUpdate();
            pstmt.close();
          }
        } else { // Enterprise DB (Postgres DB)
          log.info("updateProcessValueVariable() method: byte data[]; Postgres DB 2");
          if (data != null) {
        	  String updateBlobSql =
                  "update CLW_PROCESS_PROPERTY set VAR_VALUE=? WHERE PROCESS_PROPERTY_ID = " + processPropertyId;

              PreparedStatement pstmt = conn.prepareStatement(updateBlobSql);
              //pstmt.setBlob(1, blob); //old code
              log.info("updateProcessValueVariable() method: byte data[]; data to update="+data);
              pstmt.setBytes(1, data); //new code
              pstmt.executeUpdate();
              pstmt.close(); 
          }
        }
        return true;

    }
    */


    public ProcessPropertyData createProcessEmptyValueVariable(Connection conn, ProcessPropertyData prop) throws Exception {

        //Create record for blob
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PROCESS_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        int keyId = rs.getInt(1);
        stmt.close();

        String varField = null;
        if (RefCodeNames.PROCESS_VAR_CLASS.STRING.equals(prop.getVarClass())) {
            varField = ProcessPropertyDataAccess.STRING_VAL;
        } else if (RefCodeNames.PROCESS_VAR_CLASS.INTEGER.equals(prop.getVarClass())) {
            varField = ProcessPropertyDataAccess.NUMBER_VAL;
        } else if (RefCodeNames.PROCESS_VAR_CLASS.DATE.equals(prop.getVarClass())) {
            varField = ProcessPropertyDataAccess.DATE_VAL;
        } else {
            varField = ProcessPropertyDataAccess.VAR_VALUE;
        }

        String insertSql =
                " insert into CLW_PROCESS_PROPERTY ( " +
                        " PROCESS_PROPERTY_ID," +
                        " PROCESS_ID," +
                        " TASK_VAR_NAME," +
                        " PROPERTY_TYPE_CD," +
                        varField +"," +
                        " VAR_CLASS," +
                        " ADD_BY," +
                        " ADD_DATE," +
                        " MOD_BY," +
                        " MOD_DATE " +
                        " ) values  (?,?,?,?,?,?,?,?,?,?) ";




        PreparedStatement pstmt = conn.prepareStatement(insertSql);

        pstmt.setInt(1, keyId);
        pstmt.setInt(2, prop.getProcessId());
        pstmt.setString(3, prop.getTaskVarName());
        pstmt.setString(4, prop.getPropertyTypeCd());

        if (ProcessPropertyDataAccess.NUMBER_VAL.equals(varField)) {
            pstmt.setInt(5, prop.getNumberVal());
        } else if (ProcessPropertyDataAccess.STRING_VAL.equals(varField)) {
            pstmt.setString(5, prop.getStringVal());
        } else if (ProcessPropertyDataAccess.DATE_VAL.equals(varField)) {
            pstmt.setTimestamp(5, DBAccess.toSQLTimestamp(prop.getDateVal()));
        } else {
        	if (ORACLE.equals(databaseName)) {
                pstmt.setBlob(5, BLOB.empty_lob());
            } else if (EDB.equals(databaseName)) {
                pstmt.setBytes(5, null);
            } else {
              throw new Exception("Unknown database");
            }
            //pstmt.setBlob(5, BLOB.empty_lob()); //old code
        }

        pstmt.setString(6, prop.getVarClass());
        pstmt.setString(7, prop.getAddBy());
        pstmt.setTimestamp(8, DBAccess.toSQLTimestamp(new Date(System.currentTimeMillis())));
        pstmt.setString(9, prop.getModBy());
        pstmt.setTimestamp(10, DBAccess.toSQLTimestamp(new Date(System.currentTimeMillis())));

        pstmt.executeUpdate();
        pstmt.close();
        prop.setProcessPropertyId(keyId);
        return prop;
    }

    /**
     * updates clw_process_property
     *
     * @param processVariableObj
     * @return succes flag
     * @throws RemoteException if an errors
     */
    public boolean updateProcessPropertyData(PairViewVector processVariableObj) throws RemoteException {

    	log.info("updateProcessPropertyData() method: Begin");
    	
        Connection conn = null;

        if (processVariableObj == null) {
            return false;
        }

        try {
            conn = getConnection();
            Iterator it = processVariableObj.iterator();
            while (it.hasNext()) {
                PairView pair = (PairView) it.next();
                ProcessPropertyData prop = (ProcessPropertyData) pair.getObject1();
                Object value = pair.getObject2();
                if (prop != null) {
                    if (prop.getProcessPropertyId() > 0) {
                        updateProcessValueVariable(conn, prop.getProcessPropertyId(), value);
                    } else {
                        prop = createProcessEmptyValueVariable(conn, prop);
                        updateProcessValueVariable(conn, prop.getProcessPropertyId(),value);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    protected int setByteToOracleBlob(BLOB blob, byte[] data) throws Exception {

        OutputStream out = blob.setBinaryStream(0);
        out.write(data);
        out.flush();
        out.close();

        return data.length;

    }

    protected byte[] objectToBytes(Object pObj) throws java.io.IOException {
        java.io.ByteArrayOutputStream oStream = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream os = new java.io.ObjectOutputStream(oStream);
        os.writeObject(pObj);
        os.flush();
        os.close();
        return oStream.toByteArray();

    }

    protected Object bytesToObject(byte[] pBytes) {
        Object obj = null;
        if (pBytes != null) {
           java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream(pBytes);
           try {
               java.io.ObjectInputStream is = new java.io.ObjectInputStream(iStream);
               obj = is.readObject();
               is.close();
               iStream.close();
           } catch (Exception exc) {
               exc.printStackTrace();
           }
        }
        return obj;
    }

    /**
     * gets variable  value for process
     *
     * @param processId process identifier
     * @param varName   varible name
     * @return variable value
     * @throws RemoteException if an errors
     */
    public Object getVariableValue(int processId, String varName) throws RemoteException {

        Connection conn = null;
        DBCriteria dbCrit;

        try {

            conn = getConnection();
            dbCrit = new DBCriteria();
            dbCrit.addEqualTo(ProcessPropertyDataAccess.PROCESS_ID, processId);
            dbCrit.addEqualTo(ProcessPropertyDataAccess.TASK_VAR_NAME, varName);
            ProcessPropertyDataVector data = ProcessPropertyDataAccess.select(conn, dbCrit);
            if (data != null && data.size() > 1) {
                throw new Exception("Multiple variable for process : " + processId);
            }
            if (data == null || data.size() == 0) {
                throw new Exception("variable (" + varName + ") for process : " + processId + " not found");
            }

            byte[] val = null;
            if ("java.lang.String".equals(((ProcessPropertyData) data.get(0)).getVarClass())) {
                return ((ProcessPropertyData) data.get(0)).getStringVal();
            }
            else if ("java.lang.Integer".equals(((ProcessPropertyData) data.get(0)).getVarClass())) {
                return new Integer(((ProcessPropertyData) data.get(0)).getNumberVal());
            }
            else if ("java.util.Date".equals(((ProcessPropertyData) data.get(0)).getVarClass())) {
                java.util.Date date;
                date = ((ProcessPropertyData) data.get(0)).getDateVal();
                return date;
            } else {
               // STJ-6037
               ProcessPropertyData ppD = ((ProcessPropertyData) data.get(0));

               
               if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(ppD.getStorageTypeCd()) ||
				   BlobStorageAccess.STORAGE_NFS.equals(ppD.getStorageTypeCd()) ||
                   BlobStorageAccess.STORAGE_FTP.equals(ppD.getStorageTypeCd())) {
                   if (Utility.isSet(ppD.getBlobValueSystemRef())) {
                       val = bsa.readBlob(ppD.getStorageTypeCd(),
                                           ppD.getBinaryDataServer(),
                                           ppD.getBlobValueSystemRef());
                   }
                } else {
                   if (ppD.getVarValue() != null) {
				       val = ((ProcessPropertyData) data.get(0)).getVarValue();
					   /*
                       throw new Exception("ERROR. Blob has been read from DB. ProcessID: " + ppD.getProcessId() +
                                 " (" + BlobStorageAccess.FIELD_VAR_VALUE + ")");
						*/		 
                   }
				   
               }
               //---------------------------------------------------------------
               //val = ((ProcessPropertyData) data.get(0)).getVarValue();
                return bytesToObject(val);
            }

        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }


    public boolean updateProcessPropertyDataPN(int processId, PairViewVector pairProcessVariable) throws RemoteException {
        Connection conn = null;

        if (pairProcessVariable == null) {
            return false;
        }

        try {

            conn = getConnection();
            Iterator it = pairProcessVariable.iterator();
            while (it.hasNext()) {
                PairView pair = (PairView) it.next();
                String varName = (String) pair.getObject1();
                Object value = pair.getObject2();
                if (processId > 0)
                    updateProcessValueVariable(conn, processId, varName, value);
            }

            return true;
        } catch (Exception e) {
            throw new RemoteException("processId: " + processId, e);
        } finally {
            closeConnection(conn);
        }
    }


    private boolean updateProcessValueVariable(Connection conn, int processId, String varName, Object value) throws Exception {

        try {
          if (value instanceof String) {
            return updateProcessValueVariable(conn, processId, varName, (String) value);
          } else if (value instanceof Integer) {
            return updateProcessValueVariable(conn, processId, varName, (Integer) value);
          } else if (value instanceof Date) {
            return updateProcessValueVariable(conn, processId, varName, (Date) value);
          } else {
            return updateProcessValueVariable(conn, processId, varName,
                                              objectToBytes(value));
          }
        } catch (Exception e) {
          throw new Exception("paremeter: " + varName, e);
        }
    }


    private boolean updateProcessValueVariable(Connection conn, int processPropertyId, Object value) throws Exception {
        log.info("updateProcessValueVariable() method: Begin");
    	try {
          if (value instanceof String) {
            return updateProcessValueVariable(conn, processPropertyId, (String) value);

          } else if (value instanceof Integer) {
            return updateProcessValueVariable(conn, processPropertyId, (Integer) value);

          } else if (value instanceof Date) {
            return updateProcessValueVariable(conn, processPropertyId, (Date) value);

          } else {
            return updateProcessValueVariable(conn, processPropertyId, objectToBytes(value));
          }
        } catch (Exception e) {
		  e.printStackTrace();
          throw new Exception("paremeter: " + processPropertyId + "; " + e.getMessage(), e.getCause());
        }
    }


    private boolean updateProcessValueVariable(Connection conn, int processPropertyId, String value) throws Exception {
        log.info("updateProcessValueVariable() method: String Value => Begin; value="+value);
    	String updateStringSql =
                "update CLW_PROCESS_PROPERTY set " + ProcessPropertyDataAccess.STRING_VAL + "=? where PROCESS_PROPERTY_ID = ? ";

        PreparedStatement pstmt = conn.prepareStatement(updateStringSql);
        pstmt.setString(1, value);
        pstmt.setInt(2, processPropertyId);
        pstmt.executeUpdate();
        pstmt.close();

        return true;
    }

    private boolean updateProcessValueVariable(Connection conn, int processPropertyId, Integer value) throws Exception {
    	log.info("updateProcessValueVariable() method: Integer Value => Begin; value="+value);
    	String updateStringSql =
                "update CLW_PROCESS_PROPERTY set " + ProcessPropertyDataAccess.NUMBER_VAL + "=? where PROCESS_PROPERTY_ID = ? ";

        PreparedStatement pstmt = conn.prepareStatement(updateStringSql);
        pstmt.setInt(1, value.intValue());
        pstmt.setInt(2, processPropertyId);
        pstmt.executeUpdate();
        pstmt.close();

        return true;
    }

    private boolean updateProcessValueVariable(Connection conn, int processPropertyId, Date value) throws Exception {
    	log.info("updateProcessValueVariable() method: Date Value => Begin; value="+value);
    	String updateStringSql =
                "update CLW_PROCESS_PROPERTY set " + ProcessPropertyDataAccess.DATE_VAL + "=? where PROCESS_PROPERTY_ID = ? ";

        PreparedStatement pstmt = conn.prepareStatement(updateStringSql);
        pstmt.setTimestamp(1, DBAccess.toSQLTimestamp(value));
        pstmt.setInt(2, processPropertyId);
        pstmt.executeUpdate();
        pstmt.close();

        return true;
    }

    private boolean updateProcessValueVariable(Connection conn, int processId, String varName, Integer integer) throws Exception {
        String updateBlobSql =
                "update CLW_PROCESS_PROPERTY set NUMBER_VAL=? where PROCESS_ID = ? AND TASK_VAR_NAME = ? ";

         try {
           PreparedStatement pstmt = conn.prepareStatement(updateBlobSql);
           pstmt.setInt(1, integer.intValue());
           pstmt.setInt(2, processId);
           pstmt.setString(3, varName);

           pstmt.executeUpdate();
           pstmt.close();

           return true;
         } catch (Exception e) {
           throw new Exception("value: " + integer==null?"null":integer.toString(), e);
         }
    }

    private boolean updateProcessValueVariable(Connection conn, int processId, String varName, String string) throws Exception {
        String updateBlobSql =
                "update CLW_PROCESS_PROPERTY set STRING_VAL=? where PROCESS_ID = ? AND TASK_VAR_NAME = ? ";

        try {
          PreparedStatement pstmt = conn.prepareStatement(updateBlobSql);
          pstmt.setString(1, string);
          pstmt.setInt(2, processId);
          pstmt.setString(3, varName);

          pstmt.executeUpdate();
          pstmt.close();
          return true;
        } catch (Exception e) {
          throw new Exception("value: " + string, e);
        }
    }

    private boolean updateProcessValueVariable(Connection conn, int processId, String varName, java.util.Date date) throws Exception {
        String updateBlobSql =
                "update CLW_PROCESS_PROPERTY set DATE_VAL=? where PROCESS_ID = ? AND TASK_VAR_NAME = ? ";

            try {
              PreparedStatement pstmt = conn.prepareStatement(updateBlobSql);
              pstmt.setTimestamp(1, DBAccess.toSQLTimestamp(date));
              pstmt.setInt(2, processId);
              pstmt.setString(3, varName);
              pstmt.executeUpdate();
              pstmt.close();

              return true;
            } catch (Exception e) {
              throw new Exception("value: " + date==null?"null":date.toString(), e);
            }
    }

    private boolean updateProcessValueVariable(Connection conn, int processId, String varName, byte[] data) throws Exception {

      try {
          
        // STJ-6037
        //String sql =
        //    "select VAR_VALUE from CLW_PROCESS_PROPERTY " +
        //    " where PROCESS_ID = ?  AND TASK_VAR_NAME= ? for update ";
    	log.info("updateProcessValueVariable() method: byte data[]=> Begin; byte data.length="+(
		         data==null?data:data.length)+ " processId="+processId);
		
        String sql = "select PROCESS_PROPERTY_ID, " +
                            "BINARY_DATA_SERVER, " +
                            "BLOB_VALUE_SYSTEM_REF, " +
                            "STORAGE_TYPE_CD, " +
                            "VAR_VALUE " +
                     "from CLW_PROCESS_PROPERTY " +
                     "where PROCESS_ID=? AND TASK_VAR_NAME=? for update";
        PreparedStatement stmn = conn.prepareStatement(sql);

        stmn.setInt(1, processId);
        stmn.setString(2, varName);

        ResultSet rs = stmn.executeQuery();
        rs.next();
        
        int processPropertyId = rs.getInt(1);
        String binaryDataServer = rs.getString(2);
        String blobValueSystemRef = rs.getString(3);
        String storageTypeCd = rs.getString(4);

        BLOB blob = null;
        if (data != null && data.length > 0) {
            if (ORACLE.equals(databaseName)) {
                blob = (BLOB) rs.getBlob(5);
                setByteToOracleBlob(blob, data);
            }
        }
        
        boolean writeVarValue = false;
        if (!Utility.isSet(blobValueSystemRef)) {
            blobValueSystemRef = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_PROCESS_PROPERTY,
                                                                   BlobStorageAccess.FIELD_VAR_VALUE,
                                                                   processId);
        }
        boolean isOK = bsa.storeBlob(data, blobValueSystemRef);
	    log.info("Saving Process Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());

        if (isOK && bsa.getStoredToStorageType() != null &&
            bsa.getStoredToHostName() != null	) { // blob has been stored
            storageTypeCd = bsa.getStoredToStorageType();
            binaryDataServer = bsa.getStoredToHostName();
        } else {
            log.error("ERROR. Blob will be written into DB. ProcessID: " + processId + 
                              " (" + BlobStorageAccess.FIELD_VAR_VALUE + ")");
            storageTypeCd = BlobStorageAccess.STORAGE_DB;
            binaryDataServer = BlobStorageAccess.getCurrentHost();
            writeVarValue = true;
        }

        //String updateBlobSql = "update CLW_PROCESS_PROPERTY set VAR_VALUE=? where PROCESS_ID = ? AND TASK_VAR_NAME = ? ";
        
        String updateBlobSql = "update CLW_PROCESS_PROPERTY " +
                               "set BINARY_DATA_SERVER=?, " +
                               "BLOB_VALUE_SYSTEM_REF=?, " + 
                               "STORAGE_TYPE_CD=?, " +
                               "MOD_BY=?, " +
                               "MOD_DATE=? " +
                               (writeVarValue ? ", VAR_VALUE=? " : "") +
                               "where PROCESS_PROPERTY_ID = " + processPropertyId;
        
        PreparedStatement pstmt = conn.prepareStatement(updateBlobSql);

        java.util.Date currDate = new java.util.Date(System.currentTimeMillis());

        pstmt.setString(1, binaryDataServer);
        pstmt.setString(2, blobValueSystemRef);
        pstmt.setString(3, storageTypeCd);
        pstmt.setString(4, className);
        pstmt.setTimestamp(5, DBAccess.toSQLTimestamp(currDate));
        
        /*
        if (ORACLE.equals(databaseName)) { //Oralce Database 
            pstmt.setBlob(1, blob);
        } else { //Enterprise DB (Postgres DB)
        	pstmt.setBytes(1, blobValueBytes);
        }

        pstmt.setInt(2, processId);
        pstmt.setString(3, varName);
        */

        if (writeVarValue) {
            if (ORACLE.equals(databaseName)) {
                pstmt.setBlob(6, blob);
            } else if (EDB.equals(databaseName)) {
                //ByteArrayInputStream isA = new ByteArrayInputStream(data);
                //pstmt.setBinaryStream(6, isA, isA.available());
                pstmt.setBytes(6, data);
            } else {
                throw new Exception("Unknown database");
            }
        }
        
        pstmt.executeUpdate();
        pstmt.close();

        return true;
      } catch (Exception e) {
        throw new Exception("size: " + (data==null?"null":""+data.length) , e);
      }
    }




    /**
     * Creates the Process according the Process template
     * @param templateId - Id of the Process which has to be running
     * @param  userName - user name which creates this process
     * @return ProcessActive
     * @throws java.rmi.RemoteException if an errors
     * @param schema process schema
     */
    public ProcessActive createActiveProcess(int templateId, ProcessSchema schema, String userName) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();

            //gets template
            ProcessData processTemplate = getProcess(templateId);
            
            //get actual hostname
            String hostname = System.getenv("HOSTNAME");
            if (hostname == null) {
                hostname = InetAddress.getLocalHost().getHostName();
            }
            
            //create process data
            ProcessData process = ProcessData.createValue();
            process.setProcessTemplateId(processTemplate.getProcessId());
            process.setProcessTypeCd(RefCodeNames.PROCESS_TYPE_CD.ACTIVE);
            process.setProcessStatusCd(RefCodeNames.PROCESS_STATUS_CD.READY);
            process.setProcessName(processTemplate.getProcessName());
            process.setProcessHostname(hostname);
            process.setAddBy(userName);
            process.setAddDate(new Date(System.currentTimeMillis()));             //insert

            process = updateProcessData(process);

            logInfo("Schema => " + schema);
            TaskRefDataVector activeRefs;
            if (schema == null) {
                //schema = createStandartSchema(conn,process);
                activeRefs = applyStandartSchema(process);
            } else {
                activeRefs = applySchema(schema, process);
            }

            if (activeRefs.isEmpty()) {
                throw new Exception("No task references.Process => " + process.getProcessId());
            }

            return new ProcessActive(process);

        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    private TaskRefDataVector applyStandartSchema(ProcessData process) throws Exception {
        Task taskEjb = APIAccess.getAPIAccess().getTaskAPI();
        return taskEjb.createActiveReferences(process);
    }

    private ProcessSchema createStandartSchema(Connection conn, ProcessData process) throws Exception {
        ProcessSchema schema = new ProcessSchema();
        HashMap stepMap = new HashMap();
        buildTaskStepMap(conn, process.getProcessTemplateId(), 0, 1, stepMap);
        for (int i = 0; i < stepMap.size(); i++) {
            List tasksInfo = ((List) stepMap.get(new Integer(i)));
            if (tasksInfo != null) {
                schema.addStep(tasksInfo.toArray());
            }
        }
        return schema;
    }

    public void buildTaskStepMap(Connection conn, int processId, int begTaskId, int step, HashMap stepMap) throws Exception {

        DBCriteria dbCrit = new DBCriteria();

        dbCrit.addEqualTo(TaskRefDataAccess.PROCESS_ID, processId);
        if (begTaskId == 0) {
            dbCrit.addIsNull(TaskRefDataAccess.TASK_ID1);
        } else {
            dbCrit.addEqualTo(TaskRefDataAccess.TASK_ID1, begTaskId);
        }

        IdVector tasks = TaskRefDataAccess.selectIdOnly(conn, TaskRefDataAccess.TASK_ID2, dbCrit);

        if (!tasks.isEmpty()) {
            Iterator it = tasks.iterator();
            ArrayList taskInfo = new ArrayList();
            while (it.hasNext()) {
                Integer taskId2Int = (Integer) it.next();
                if (taskId2Int != null && taskId2Int.intValue() > 0) {

                    TaskData task = TaskDataAccess.select(conn, taskId2Int.intValue());

                    DBCriteria taskPropCriteria = new DBCriteria();
                    taskPropCriteria.addEqualTo(TaskPropertyDataAccess.TASK_ID, taskId2Int.intValue());
                    TaskPropertyDataVector properties = TaskPropertyDataAccess.select(conn, taskPropCriteria);

                    TaskPropertyDataVector outParams = getTaskProperty(properties, RefCodeNames.TASK_PROPERTY_TYPE_CD.OUTPUT);
                    TaskPropertyDataVector inParams = getTaskProperty(properties, RefCodeNames.TASK_PROPERTY_TYPE_CD.INPUT);
                    inParams.addAll(getTaskProperty(properties, RefCodeNames.TASK_PROPERTY_TYPE_CD.MANDATORY));

                    sortByPosition(inParams);
                    sortByPosition(outParams);

                    Object[] inParamInfo = new Object[inParams.size()];
                    for (int i = 0; i < inParams.size(); i++) {
                        inParamInfo[i] = ((TaskPropertyData) inParams.get(i)).getVarType();
                    }

                    Object[] outParamInfo = new Object[outParams.size()];
                    for (int i = 0; i < outParams.size(); i++) {
                        outParamInfo[i] = ((TaskPropertyData) outParams.get(i)).getVarType();
                    }

                    taskInfo.add(new com.cleanwise.service.api.value.TaskView(task.getTaskName(),task.getVarClass(), task.getMethod(), inParamInfo, outParamInfo));

                    buildTaskStepMap(conn, processId, taskId2Int.intValue(), step + 1, stepMap);
                }
                stepMap.put(new Integer(step), taskInfo);
            }
        }
    }

    public TaskPropertyDataVector getTaskProperty(TaskPropertyDataVector property, String typeCd) {

        TaskPropertyDataVector result = new TaskPropertyDataVector();

        if (property != null) {

            Iterator it = property.iterator();
            while (it.hasNext()) {

                TaskPropertyData taskPropData = (TaskPropertyData) it.next();
                if (typeCd != null) {
                    if (typeCd.equals(taskPropData.getPropertyTypeCd())) {
                        result.add(taskPropData);
                    }
                }
            }
        }

        return result;
    }

    public void sortByPosition(TaskPropertyDataVector inParams) {
        Collections.sort(inParams, TaskActive.TASK_POSITION);
    }


    private TaskRefDataVector applySchema(ProcessSchema schema, ProcessData process) throws Exception {
        Task taskEjb = APIAccess.getAPIAccess().getTaskAPI();
        TaskDataVector tasks = parseSchema(taskEjb, process.getProcessTemplateId(), schema);
        return taskEjb.updateRefs(process.getProcessId(), createTableReference(tasks));
    }

    public TaskDataVector parseSchema(Task taskEjb, int templateProcessId, ProcessSchema schema) throws Exception {

        TaskDataVector processTasks = new TaskDataVector();

        int stepCount = schema.getSchema().length;
        for (int i = 0; i < stepCount; i++) {
            Object stepSchema = schema.getSchema()[i];
            if (stepSchema instanceof ProcessSchema) {
                processTasks.addAll(parseSchema(taskEjb, templateProcessId, (ProcessSchema) stepSchema));
            } else if (stepSchema instanceof Object[]) {
                processTasks.addAll(parseSchema(taskEjb, templateProcessId, (Object[]) stepSchema));
            }
        }
        return processTasks;
    }

    private TaskDataVector parseSchema(Task taskEjb, int templateProcessId, Object[] stpSchema) throws Exception {
        int count = stpSchema.length;
        TaskDataVector stepTasks = new TaskDataVector();
        for (int i = 0; i < count; i++) {
            Object stepSchema = stpSchema[i];
            if (stepSchema instanceof ProcessSchema) {
                stepTasks.addAll(parseSchema(taskEjb, templateProcessId, (ProcessSchema) stepSchema));
            } else if (stepSchema instanceof TaskView) {
                TaskSearchCriteria crit = new TaskSearchCriteria();
                crit.setProcessIds(Utility.toIdVector(templateProcessId));
                crit.setProcessTypeCd(RefCodeNames.PROCESS_TYPE_CD.TEMPLATE);
                crit.setTaskStatusCds(new String[]{RefCodeNames.TASK_STATUS_CD.ACTIVE});
                crit.setTaskTypeCd(RefCodeNames.TASK_TYPE_CD.TEMPLATE);
                crit.setTaskNames(new String[]{((com.cleanwise.service.api.value.TaskView) stepSchema).getName()});
                TaskDataVector tasks = taskEjb.getTasks(crit);
                stepTasks.addAll(tasks);
            } else if (stepSchema instanceof Object[]) {
                stepTasks.addAll(parseSchema(taskEjb, templateProcessId, (Object[]) stepSchema));
            }
        }
        return stepTasks;
    }

    private TaskRefDataVector createTableReference(TaskDataVector taskDataVector) {

        TaskRefDataVector refTable = new TaskRefDataVector();

        if (!taskDataVector.isEmpty()) {

            TaskRefData taskRefData = TaskRefData.createValue();
            taskRefData.setTaskId2(((TaskData) taskDataVector.get(0)).getTaskId());
            refTable.add(taskRefData);

            for (int i = 1; i < taskDataVector.size(); i++) {
                taskRefData = TaskRefData.createValue();

                TaskData td = (TaskData) taskDataVector.get(i);
                taskRefData.setTaskId1(((TaskData) taskDataVector.get(i - 1)).getTaskId());
                taskRefData.setTaskId2(td.getTaskId());

                taskRefData.setAddBy("ProcessBean");
                taskRefData.setModBy("ProcessBean");

                refTable.add(taskRefData);
            }

            taskRefData = TaskRefData.createValue();
            taskRefData.setTaskId1(((TaskData) taskDataVector.get(taskDataVector.size() - 1)).getTaskId());
            refTable.add(taskRefData);
        }
        return refTable;
    }

    public ProcessData setProcessStatus(ProcessData processData, String status) throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();

            if (processData != null && processData.getProcessId() > 0) {
                processData.setProcessStatusCd(status);
                processData = updateProcessData(conn, processData);
            }

        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
        return processData;
    }

    private ProcessData updateProcessData(Connection conn, ProcessData processData) throws SQLException {

        if (processData != null) {
            if (processData.getProcessId() > 0) {
                ProcessDataAccess.update(conn, processData);
            } else {
                processData = ProcessDataAccess.insert(conn, processData);
            }
        }
        return processData;
    }

     public ProcessDataVector getAllTemplateProcesses() throws RemoteException{
       Connection conn = null;
       DBCriteria crit;
       try {
            conn = getConnection();
            crit = new DBCriteria();
            crit.addEqualTo(ProcessDataAccess.PROCESS_TYPE_CD, RefCodeNames.PROCESS_TYPE_CD.TEMPLATE);
            return ProcessDataAccess.select(conn,crit);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
     }


    public TaskTemplateDetailViewVector updateTemplateProcessDetailData(TaskTemplateDetailViewVector taskTemplateDetails) throws RemoteException{

        Connection conn = null;
        try {
            conn = getConnection();
            Task taskEjb= APIAccess.getAPIAccess().getTaskAPI();

            if(taskTemplateDetails!=null && !taskTemplateDetails.getTasks().isEmpty()){
                ProcessData tProcessData = taskTemplateDetails.getProcessData();
                DBCriteria crit= new DBCriteria();
                crit.addEqualTo(ProcessDataAccess.PROCESS_NAME,tProcessData.getProcessName());
                crit.addEqualTo(ProcessDataAccess.PROCESS_TYPE_CD,RefCodeNames.PROCESS_TYPE_CD.TEMPLATE);
                ProcessDataVector oldPdv = ProcessDataAccess.select(conn,crit);
                if (!oldPdv.isEmpty()) {
                    Iterator oldPdIter = oldPdv.iterator();
                    if (oldPdIter.hasNext()){
                    	ProcessData oldPd = (ProcessData) oldPdIter.next();
                        if (Utility.isEqual(oldPd.getProcessStatusCd(), tProcessData.getProcessStatusCd()) &&
                        		oldPd.getProcessPriority() == tProcessData.getProcessPriority()){
                        	taskTemplateDetails.setProcessData(oldPd);
                        }else{
                        	oldPd.setProcessStatusCd(tProcessData.getProcessStatusCd());
                            oldPd.setProcessPriority(tProcessData.getProcessPriority());
                            oldPd.setModBy(tProcessData.getModBy());
                            oldPd.setModDate(tProcessData.getModDate());
                            // process: update active
                            taskTemplateDetails.setProcessData(updateProcessData(conn, oldPd));                            
                        }
                    }
                } else {
                    // process: insert new active
                    taskTemplateDetails.setProcessData(updateProcessData(conn, tProcessData));
                }
                
                IdVector updatedTaskIds = new IdVector();
                Iterator newTaskIter = taskTemplateDetails.getTasks().iterator();
                while(newTaskIter.hasNext()){
                    TaskTemplateDetailView taskView = (TaskTemplateDetailView) newTaskIter.next();
                    if(taskView != null){
                        taskView.getTask().getTaskData().setProcessId(taskTemplateDetails.getProcessData().getProcessId());
                        crit = new DBCriteria();
                        crit.addEqualTo(TaskDataAccess.PROCESS_ID,taskTemplateDetails.getProcessData().getProcessId());
                        crit.addEqualTo(TaskDataAccess.TASK_NAME,taskView.getTask().getTaskData().getTaskName());
                        crit.addEqualTo(TaskDataAccess.TASK_TYPE_CD,RefCodeNames.TASK_TYPE_CD.TEMPLATE);
                        if (!updatedTaskIds.isEmpty())
                        	crit.addNotOneOf(TaskDataAccess.TASK_ID, updatedTaskIds);
                        crit.addOrderBy(TaskDataAccess.TASK_STATUS_CD);
                        crit.addOrderBy(TaskDataAccess.TASK_ID);
                        TaskDataVector oldTdv = TaskDataAccess.select(conn,crit);
                        if (oldTdv.isEmpty()) {
                            crit = new DBCriteria();
                            crit.addEqualTo(TaskDataAccess.PROCESS_ID,taskTemplateDetails.getProcessData().getProcessId());
                            crit.addEqualTo(TaskDataAccess.TASK_TYPE_CD,RefCodeNames.TASK_TYPE_CD.TEMPLATE);
                            if (!updatedTaskIds.isEmpty())
                            	crit.addNotOneOf(TaskDataAccess.TASK_ID, updatedTaskIds);
                            crit.addOrderBy(TaskDataAccess.TASK_STATUS_CD);
                            crit.addOrderBy(TaskDataAccess.TASK_ID);
                            oldTdv = TaskDataAccess.select(conn,crit);
                        }
                        if (!oldTdv.isEmpty()) {
                            Iterator oldTdIter = oldTdv.iterator();
                            while (oldTdIter.hasNext()) {
                                TaskData oldTd = (TaskData) oldTdIter.next();
                                if (Utility.isEqual(oldTd.getTaskName(),taskView.getTask().getTaskData().getTaskName()) &&
                                		Utility.isEqual(oldTd.getTaskStatusCd(),taskView.getTask().getTaskData().getTaskStatusCd()) &&
                                		Utility.isEqual(oldTd.getVarClass(),taskView.getTask().getTaskData().getVarClass()) &&
                                		Utility.isEqual(oldTd.getMethod(),taskView.getTask().getTaskData().getMethod()) &&
                                		Utility.isEqual(oldTd.getOperationTypeCd(),taskView.getTask().getTaskData().getOperationTypeCd())) {
                                	taskView.getTask().setTaskData(oldTd);
                                	break;
                                }
                            }
                            if (taskView.getTask().getTaskData().getTaskId() == 0){
                            	TaskData oldTd = (TaskData) oldTdv.get(0);
                            	oldTd.setTaskName(taskView.getTask().getTaskData().getTaskName());
                                oldTd.setTaskStatusCd(taskView.getTask().getTaskData().getTaskStatusCd());
                                oldTd.setVarClass(taskView.getTask().getTaskData().getVarClass());
                                oldTd.setMethod(taskView.getTask().getTaskData().getMethod());
                                oldTd.setOperationTypeCd(taskView.getTask().getTaskData().getOperationTypeCd());
                                oldTd.setModBy(tProcessData.getModBy());
                                oldTd.setModDate(tProcessData.getModDate());
                                taskView.getTask().setTaskData(taskEjb.updateTaskData(oldTd));
                            }
                        } else {
                            taskView.getTask().setTaskData(taskEjb.updateTaskData(taskView.getTask().getTaskData()));
                        }

                        IdVector updatedTaskPropIds = new IdVector();
                        Iterator newTpIter = taskView.getTask().getTaskPropertyDV().iterator();
                        while(newTpIter.hasNext()){
                            TaskPropertyData tpd = (TaskPropertyData)newTpIter.next();
                            tpd.setTaskId(taskView.getTask().getTaskData().getTaskId());
                            crit = new DBCriteria();
                            crit.addEqualTo(TaskPropertyDataAccess.TASK_ID,taskView.getTask().getTaskData().getTaskId());
                            crit.addEqualTo(TaskPropertyDataAccess.VAR_NAME,tpd.getVarName());
                            if (!updatedTaskPropIds.isEmpty())
                            	crit.addNotOneOf(TaskPropertyDataAccess.TASK_PROPERTY_ID, updatedTaskPropIds);
                            crit.addOrderBy(TaskPropertyDataAccess.TASK_PROPERTY_STATUS_CD);
                            crit.addOrderBy(TaskPropertyDataAccess.TASK_PROPERTY_ID);
                            TaskPropertyDataVector oldTpdv = TaskPropertyDataAccess.select(conn,crit);
                            if (oldTpdv.isEmpty()) {
                                crit = new DBCriteria();
                                crit.addEqualTo(TaskPropertyDataAccess.TASK_ID,taskView.getTask().getTaskData().getTaskId());
                                if (!updatedTaskPropIds.isEmpty())
                                	crit.addNotOneOf(TaskPropertyDataAccess.TASK_PROPERTY_ID, updatedTaskPropIds);
                                crit.addOrderBy(TaskPropertyDataAccess.TASK_PROPERTY_STATUS_CD);
                                crit.addOrderBy(TaskPropertyDataAccess.TASK_PROPERTY_ID);
                                oldTpdv = TaskPropertyDataAccess.select(conn,crit);                               
                            }
                            if (!oldTpdv.isEmpty()) {
                                Iterator oldTpIter = oldTpdv.iterator();
                                while (oldTpIter.hasNext()) {
                                    TaskPropertyData oldTp = (TaskPropertyData) oldTpIter.next();
                                    if (Utility.isEqual(oldTp.getVarName(),tpd.getVarName()) &&
                                            Utility.isEqual(oldTp.getTaskPropertyStatusCd(),tpd.getTaskPropertyStatusCd()) &&
                                            Utility.isEqual(oldTp.getVarType(),tpd.getVarType()) &&
                                            Utility.isEqual(oldTp.getPropertyTypeCd(),tpd.getPropertyTypeCd()) &&
                                            oldTp.getPosition() == tpd.getPosition()) {
                                    	tpd = oldTp;
                                    	break;
                                    }
                                }
                                if (tpd.getTaskPropertyId() == 0){
                                	TaskPropertyData oldTp = (TaskPropertyData) oldTpdv.get(0);
                                	oldTp.setVarName(tpd.getVarName());
                                    oldTp.setTaskPropertyStatusCd(tpd.getTaskPropertyStatusCd());
                                    oldTp.setVarType(tpd.getVarType());
                                    oldTp.setPosition(tpd.getPosition());
                                    oldTp.setPropertyTypeCd(tpd.getPropertyTypeCd());
                                    oldTp.setModBy(tProcessData.getModBy());
                                    oldTp.setModDate(tProcessData.getModDate());
                                    TaskPropertyDataAccess.update(conn,oldTp);
                                    tpd = oldTp;
                                }
                            } else {
                                tpd = TaskPropertyDataAccess.insert(conn, tpd);
                            }
                            updatedTaskPropIds.add(tpd.getTaskPropertyId());
                        }
                        crit= new DBCriteria();
                        crit.addEqualTo(TaskPropertyDataAccess.TASK_ID,taskView.getTask().getTaskData().getTaskId());
                        crit.addEqualTo(TaskPropertyDataAccess.TASK_PROPERTY_STATUS_CD,RefCodeNames.TASK_PROPERTY_STATUS_CD.ACTIVE);
                        crit.addNotOneOf(TaskPropertyDataAccess.TASK_PROPERTY_ID, updatedTaskPropIds);
                        TaskPropertyDataVector inactiveTpv = TaskPropertyDataAccess.select(conn,crit);
                        Iterator inactiveTpIter = inactiveTpv.iterator();
                        while (inactiveTpIter.hasNext()) {
                            TaskPropertyData inactiveTp = (TaskPropertyData) inactiveTpIter.next();
                            inactiveTp.setTaskPropertyStatusCd(RefCodeNames.TASK_PROPERTY_STATUS_CD.INACTIVE);
                            inactiveTp.setModBy("ProcessLoader");
                            inactiveTp.setModDate(new Date(System.currentTimeMillis()));
                            TaskPropertyDataAccess.update(conn, inactiveTp);
                        }
                        updatedTaskIds.add(taskView.getTask().getTaskData().getTaskId());
                    }
                }
                crit= new DBCriteria();
                crit.addEqualTo(TaskDataAccess.PROCESS_ID,taskTemplateDetails.getProcessData().getProcessId());
                crit.addEqualTo(TaskDataAccess.TASK_TYPE_CD,RefCodeNames.TASK_TYPE_CD.TEMPLATE);
                crit.addEqualTo(TaskDataAccess.TASK_STATUS_CD,RefCodeNames.TASK_STATUS_CD.ACTIVE);
                crit.addNotOneOf(TaskDataAccess.TASK_ID, updatedTaskIds);
                TaskDataVector inactiveTdv = TaskDataAccess.select(conn,crit);
                Iterator inactiveTdIter = inactiveTdv.iterator();
                while (inactiveTdIter.hasNext()) {
                    TaskData inactiveTd = (TaskData) inactiveTdIter.next();
                    crit= new DBCriteria();
                    crit.addEqualTo(TaskPropertyDataAccess.TASK_ID,inactiveTd.getTaskId());
                    crit.addEqualTo(TaskPropertyDataAccess.TASK_PROPERTY_STATUS_CD,RefCodeNames.TASK_PROPERTY_STATUS_CD.ACTIVE);
                    TaskPropertyDataVector inactiveTpv = TaskPropertyDataAccess.select(conn,crit);
                    Iterator inactiveTpIter = inactiveTpv.iterator();
                    while (inactiveTpIter.hasNext()) {
                        TaskPropertyData inactiveTp = (TaskPropertyData) inactiveTpIter.next();
                        inactiveTp.setTaskPropertyStatusCd(RefCodeNames.TASK_PROPERTY_STATUS_CD.INACTIVE);
                        inactiveTp.setModBy("ProcessLoader");
                        inactiveTp.setModDate(new Date(System.currentTimeMillis()));
                        TaskPropertyDataAccess.update(conn, inactiveTp);
                    }
                    inactiveTd.setTaskStatusCd(RefCodeNames.TASK_STATUS_CD.INACTIVE);
                    inactiveTd.setModBy("ProcessLoader");
                    inactiveTd.setModDate(new Date(System.currentTimeMillis()));
                    taskEjb.updateTaskData(inactiveTd);
                }

            }
            return taskTemplateDetails;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }


    public void updateInactiveTemplate(IdVector updatedIds) throws RemoteException{

        Connection conn = null;
        try {
            conn = getConnection();
            Task taskEjb= APIAccess.getAPIAccess().getTaskAPI();

            DBCriteria crit= new DBCriteria();
            crit.addEqualTo(ProcessDataAccess.PROCESS_TYPE_CD,RefCodeNames.PROCESS_TYPE_CD.TEMPLATE);
            crit.addEqualTo(ProcessDataAccess.PROCESS_STATUS_CD,RefCodeNames.PROCESS_STATUS_CD.ACTIVE);
            crit.addNotOneOf(ProcessDataAccess.PROCESS_ID, updatedIds);
            ProcessDataVector inactivePdv = ProcessDataAccess.select(conn,crit);
            Iterator inactivePdIter = inactivePdv.iterator();
            while (inactivePdIter.hasNext()) {
                ProcessData inactivePd = (ProcessData) inactivePdIter.next();
                inactivePd.setProcessStatusCd(RefCodeNames.PROCESS_STATUS_CD.INACTIVE);
                inactivePd.setModBy("ProcessLoader");
                inactivePd.setModDate(new Date(System.currentTimeMillis()));
                updateProcessData(conn, inactivePd);
                crit= new DBCriteria();
                crit.addEqualTo(TaskDataAccess.PROCESS_ID,inactivePd.getProcessId());
                crit.addEqualTo(TaskDataAccess.TASK_TYPE_CD,RefCodeNames.TASK_TYPE_CD.TEMPLATE);
                crit.addEqualTo(TaskDataAccess.TASK_STATUS_CD,RefCodeNames.TASK_STATUS_CD.ACTIVE);
                TaskDataVector inactiveTdv = TaskDataAccess.select(conn,crit);
                Iterator inactiveTdIter = inactiveTdv.iterator();
                while (inactiveTdIter.hasNext()) {
                    TaskData inactiveTd = (TaskData) inactiveTdIter.next();
                    crit= new DBCriteria();
                    crit.addEqualTo(TaskPropertyDataAccess.TASK_ID,inactiveTd.getTaskId());
                    crit.addEqualTo(TaskPropertyDataAccess.TASK_PROPERTY_STATUS_CD,RefCodeNames.TASK_PROPERTY_STATUS_CD.ACTIVE);
                    TaskPropertyDataVector inactiveTpv = TaskPropertyDataAccess.select(conn,crit);
                    Iterator inactiveTpIter = inactiveTpv.iterator();
                    while (inactiveTpIter.hasNext()) {
                        TaskPropertyData inactiveTp = (TaskPropertyData) inactiveTpIter.next();
                        inactiveTp.setTaskPropertyStatusCd(RefCodeNames.TASK_PROPERTY_STATUS_CD.INACTIVE);
                        inactiveTp.setModBy("ProcessLoader");
                        inactiveTp.setModDate(new Date(System.currentTimeMillis()));
                        TaskPropertyDataAccess.update(conn, inactiveTp);
                    }
                    inactiveTd.setTaskStatusCd(RefCodeNames.TASK_STATUS_CD.INACTIVE);
                    inactiveTd.setModBy("ProcessLoader");
                    inactiveTd.setModDate(new Date(System.currentTimeMillis()));
                    taskEjb.updateTaskData(inactiveTd);
                }
                crit= new DBCriteria();
                crit.addEqualTo(TaskRefDataAccess.PROCESS_ID, inactivePd.getProcessId());
                crit.addEqualTo(TaskRefDataAccess.TASK_REF_STATUS_CD, RefCodeNames.TASK_REF_STATUS_CD.ACTIVE);
                TaskRefDataVector rfs = TaskRefDataAccess.select(conn,crit);
                if (!rfs.isEmpty()) {
                    Iterator iter = rfs.iterator();
                    if (iter.hasNext()) {
                        TaskRefData refData = (TaskRefData) iter.next();
                        refData.setTaskRefStatusCd(RefCodeNames.TASK_REF_STATUS_CD.INACTIVE);
                        refData.setModBy("ProcessLoader");
                        refData.setModDate(new Date(System.currentTimeMillis()));
                        TaskRefDataAccess.update(conn, refData);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    private void removeTaskLinksNotOneOf(Connection conn, int processId, IdVector ids) throws Exception {

        DBCriteria crit= new DBCriteria();
        crit.addEqualTo(TaskDataAccess.PROCESS_ID,processId);
        crit.addNotOneOf(TaskDataAccess.TASK_ID,ids);
        TaskDataAccess.remove(conn,crit);

    }

    /**
     * Get process by its name.
     *
     * @param processName
     *            The name of process.
     * @return ProcessData
     * @throws RemoteException
     */
    public ProcessData getProcessByName(String processName)
            throws RemoteException, DataNotFoundException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getProcessByName(conn, processName);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

	public ProcessData getProcessByName(Connection conn, String processName)
			throws SQLException, DataNotFoundException {
		DBCriteria cr = new DBCriteria();
		cr.addEqualTo(ProcessDataAccess.PROCESS_NAME, processName);
		cr.addEqualTo(ProcessDataAccess.PROCESS_TYPE_CD, RefCodeNames.PROCESS_TYPE_CD.TEMPLATE);
		ProcessDataVector pdv = ProcessDataAccess.select(conn, cr);
		if (pdv != null && pdv.size() > 0) {
		    return (ProcessData) pdv.get(0);
		} else {
		    throw new DataNotFoundException("Not found process for name:"
		            + processName);
		}
	}

    public Map getAllTaskProperties() throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(TaskDataAccess.TASK_TYPE_CD, RefCodeNames.TASK_TYPE_CD.TEMPLATE);
            cr.addEqualTo(TaskDataAccess.TASK_STATUS_CD, RefCodeNames.TASK_STATUS_CD.ACTIVE);
            TaskDataVector taskDV = TaskDataAccess.select(conn, cr);
            Map task2Process = new HashMap();
            IdVector taskIds = new IdVector();
            for (int i = 0; taskDV != null && i < taskDV.size(); i++) {
                TaskData item = (TaskData) taskDV.get(i);
                task2Process.put(new Integer(item.getTaskId()), new Integer(
                        item.getProcessId()));
                taskIds.add(new Integer(item.getTaskId()));
            }
            cr = new DBCriteria();
            cr.addOneOf(TaskPropertyDataAccess.TASK_ID, taskIds);
            //cr.addEqualTo(TaskPropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.TASK_PROPERTY_TYPE_CD.MANDATORY);
            cr.addNotEqualTo(TaskPropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.TASK_PROPERTY_TYPE_CD.OUTPUT);
            cr.addEqualTo(TaskPropertyDataAccess.TASK_PROPERTY_STATUS_CD, RefCodeNames.TASK_PROPERTY_STATUS_CD.ACTIVE);
            TaskPropertyDataVector taskPropDV = TaskPropertyDataAccess.select(
                    conn, cr);
            Map result = new HashMap();
            for (int i = 0; taskPropDV != null && i < taskPropDV.size(); i++) {
                TaskPropertyData item = (TaskPropertyData) taskPropDV.get(i);
                Object id = task2Process.get(new Integer(item.getTaskId()));
                List items = (List) result.get(id);
                if (items == null) {
                    items = new ArrayList();
                    result.put(id, items);
                }
                items.add(item);
            }
            for (Object propList : result.values()) {
                Collections.sort((List<TaskPropertyData>)propList, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        TaskPropertyData tp1 = (TaskPropertyData) o1;
                        TaskPropertyData tp2 = (TaskPropertyData) o2;
                        if (tp1 != null && tp2 != null) {
                            return tp1.getVarName().compareToIgnoreCase(tp2.getVarName());
                        }
                        return 0;
                    }
                });
            }
            return result;
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public ProcessDataVector getProcessesByEventId(int eventId) throws RemoteException {
//        SELECT * FROM CLW_PROCESS t WHERE t.process_id IN (
//                SELECT number_val FROM clw_event_property WHERE type = 'PROCESS_ACTIVE_ID' AND short_desc = 'process_id' AND event_id = 296232
//        )
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(EventPropertyDataAccess.TYPE, Event.PROCESS_ACTIVE_ID);
            cr.addEqualTo(EventPropertyDataAccess.SHORT_DESC, "process_id");
            cr.addEqualTo(EventPropertyDataAccess.EVENT_ID , eventId);
            IdVector ids = EventPropertyDataAccess.selectIdOnly(conn,
                    EventPropertyDataAccess.NUMBER_VAL, cr);
            cr = new DBCriteria();
            cr.addOneOf(ProcessDataAccess.PROCESS_ID, ids);
            cr.addOrderBy(ProcessDataAccess.PROCESS_ID);
            return ProcessDataAccess.select(conn, cr);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    // returns vector of  Event Ids
    public IdVector getEventsByProcessIds(IdVector processIds) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(EventPropertyDataAccess.TYPE, Event.PROCESS_ACTIVE_ID);
            cr.addEqualTo(EventPropertyDataAccess.SHORT_DESC, "process_id");
            cr.addOneOf(EventPropertyDataAccess.NUMBER_VAL, processIds);

            IdVector ids = EventPropertyDataAccess.selectIdOnly(conn,
                    EventPropertyDataAccess.EVENT_ID, cr);
            return ids;
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    // finds processes with the same PROCESS_NAME as original process and in
    // IN_PROGRESS status
     public IdVector getSimilarProcessesRunning(int pProcessId) throws RemoteException {
         Connection conn = null;
         try {
             conn = getConnection();
             HashSet excludeParams = new HashSet();
             excludeParams.add("pProcessActiveId");
             ProcessData pdAct = ProcessDataAccess.select(conn, pProcessId);
             DBCriteria cr = new DBCriteria();
             cr.addEqualTo(ProcessPropertyDataAccess.PROCESS_ID, pProcessId);
             ProcessPropertyDataVector ppdAct = ProcessPropertyDataAccess.select(conn,cr);
             PairViewVector paramsVAct = ProcessUtil.getProcessParameters(ppdAct,excludeParams);
             String paramsVActS = paramsVAct.toString();
             cr = new DBCriteria();
             cr.addEqualTo(ProcessDataAccess.PROCESS_TYPE_CD, RefCodeNames.PROCESS_TYPE_CD.ACTIVE);
             cr.addEqualTo(ProcessDataAccess.PROCESS_STATUS_CD, RefCodeNames.PROCESS_STATUS_CD.IN_PROGRESS);
             cr.addEqualTo(ProcessDataAccess.PROCESS_NAME, pdAct.getProcessName());
             cr.addNotEqualTo(ProcessDataAccess.PROCESS_ID, pProcessId);

             IdVector ids = ProcessDataAccess.selectIdOnly(conn, ProcessDataAccess.PROCESS_ID, cr);

             cr = new DBCriteria();
             cr.addOneOf(ProcessPropertyDataAccess.PROCESS_ID, ids);
             cr.addEqualTo(ProcessPropertyDataAccess.PROPERTY_TYPE_CD,RefCodeNames.PROCESS_PROPERTY_TYPE_CD.MANDATORY);
             cr.addOrderBy(ProcessPropertyDataAccess.PROCESS_ID);
 //            cr.addOrderBy(ProcessPropertyDataAccess.TASK_VAR_NAME);
             // prepare HashMap of process properties with similar names
             ProcessPropertyDataVector ppdV = ProcessPropertyDataAccess.select(conn, cr);
             
             // STJ-6037
             if (Utility.isSet(ppdV)) {
                ProcessPropertyData processProperty;
                for (int i = 0; i < ppdV.size(); i++) {
                    processProperty = (ProcessPropertyData) ppdV.get(i);

                    if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(processProperty.getStorageTypeCd()) ||
						BlobStorageAccess.STORAGE_NFS.equals(processProperty.getStorageTypeCd()) ||
                        BlobStorageAccess.STORAGE_FTP.equals(processProperty.getStorageTypeCd())) {
                        if (Utility.isSet(processProperty.getBlobValueSystemRef())) {
                            byte[] blob = bsa.readBlob(processProperty.getStorageTypeCd(),
                                                       processProperty.getBinaryDataServer(),
                                                       processProperty.getBlobValueSystemRef());
                            processProperty.setVarValue(blob);
                        }
                    } /*else {
                        if (processProperty.getVarValue() != null) {
                            throw new Exception("ERROR. Blob has been read from DB. ProcessID: " + processProperty.getProcessId() +
                                      " (" + BlobStorageAccess.FIELD_VAR_VALUE + ")");
                        }
                    }*/
                }
             }
             HashMap ppdHM = new HashMap();
             ProcessPropertyDataVector dV = null;
             int processIdPre = 0;
             for (Iterator iter = ppdV.iterator(); iter.hasNext(); ) {
               ProcessPropertyData ppd = (ProcessPropertyData) iter.next();
               if(ppd!= null && ppd.getProcessId() != processIdPre){
                  dV = new ProcessPropertyDataVector();
                  ppdHM.put(new Integer(ppd.getProcessId()), dV);
                  processIdPre = ppd.getProcessId();
               }
               dV.add(ppd);
             }
             // verify is there any process with list of parameters similar to current

             IdVector processIdsSimV = new IdVector();
             Set keys = ppdHM.keySet();
             for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
               Integer key = (Integer) iter.next();
               dV = (ProcessPropertyDataVector)ppdHM.get(key);
               PairViewVector paramsV = ProcessUtil.getProcessParameters(dV, excludeParams);

               String paramsVS = paramsV.toString();
               if (paramsVActS != null && paramsVS != null){
                 if (paramsVActS.equals(paramsVS)){
                   processIdsSimV.add(key);
                 }
               }
             }
             return processIdsSimV;
         } catch (Exception e) {
             e.printStackTrace();
             throw new RemoteException(e.getMessage());
         } finally {
             closeConnection(conn);
         }
    }

}

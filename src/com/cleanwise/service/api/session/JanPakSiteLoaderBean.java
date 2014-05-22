package com.cleanwise.service.api.session;

import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.dao.JanPakLoaderAgent;

import javax.ejb.CreateException;
import java.rmi.RemoteException;
import java.sql.*;

public class JanPakSiteLoaderBean extends UtilityServicesAPI {

    private static final int SHIP_TO_ZIP_MAX_LENGTH = 15;

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @throws javax.ejb.CreateException if an error occurs
     * @throws RemoteException           if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }

    public void accept(byte[] pData, String pTable, String pFileName, java.util.Date pModDate, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            accept(conn, pData, pTable, pFileName, pModDate, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }


    public StringBuffer report(String pTable) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return report(conn, pTable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public void prepare(String pTable, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            prepare(conn, pTable, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }


    public void match(int pStoreId, String pTable, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            match(conn, pStoreId, pTable, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public void insert(int pStoreId, String pTable, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            insert(conn, pStoreId, pTable, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public void update(String pTable, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            update(conn, pTable, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }


    public void delete(int pStoreId, String pTable, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            delete(conn, pStoreId, pTable, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    private void prepare(Connection conn, String pTable, String pUser) throws SQLException {

        billDrop(conn, pTable);
        shipDrop(conn, pTable);

        billCreate(conn, pTable);
        shipCreate(conn, pTable);

        billLoad(conn, pTable, pUser);
        shipLoad(conn, pTable, pUser);

    }

    private void match(Connection conn, int pStoreId, String pTable, String pUser) throws SQLException {

        tempDrop(conn, pTable);

        defineAccount(conn, pStoreId, pTable, pUser);
        billMatch(conn, pTable);

        defineSite(conn, pTable, pUser);
        shipMatch(conn, pTable);
    }

    private void update(Connection conn, String pTable, String pUser) throws SQLException {
        billUpdate(conn, pTable, pUser);
        shipUpdate(conn, pTable, pUser);
    }

    private void insert(Connection conn, int pStoreId, String pTable, String pUser) throws SQLException {
        billInsert(conn, pStoreId, pTable, pUser);
        shipInsert(conn, pTable, pUser);
    }

    private void delete(Connection conn, int pStoreId, String pTable, String pUser) throws SQLException {
        shipDelete(conn, pStoreId, pTable, pUser);
        billDelete(conn, pStoreId, pTable, pUser);
    }

    private void shipDelete(Connection conn, int pStoreId, String pTable, String pUser) throws SQLException {
        Statement stmt;

        {
            String sql = "UPDATE CLW_BUS_ENTITY SET BUS_ENTITY_STATUS_CD = '" + RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE + "',MOD_DATE=SYSDATE,MOD_BY='" + pUser + "'" +
                    " WHERE BUS_ENTITY_ID IN (SELECT SITE_ID FROM " + pTable + "_SHIP WHERE STATUS = 'I' AND SITE_ACTION_CD IS NULL)";

            logDebug("shipDelete = > SQL:" + sql);

            stmt = conn.createStatement();
            int n = stmt.executeUpdate(sql);
            logDebug("shipDelete = > updated row:" + n);
            stmt.close();
        }
    }

    private void billDelete(Connection conn, int pStoreId, String pTable, String pUser) throws SQLException {

        Statement stmt;

        {
        	String sql = "UPDATE "+ pTable + "_BILL SET ACCOUNT_ACTION_CD = 'D' \n" +
        		 "  WHERE NOT EXISTS ( \n" +
        		 "       SELECT bea.BUS_ENTITY1_ID AS SITE_ID \n" +
        		 "       FROM CLW_BUS_ENTITY_ASSOC BEA, \n" +
        		 "            CLW_BUS_ENTITY BA  \n" +
        		 "       WHERE bea.BUS_ENTITY2_ID      = ACCOUNT_ID \n" +
        		 "        AND  bea.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "' \n" +
        		 "        AND  ba.BUS_ENTITY_STATUS_CD = '" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "' \n" +
        		 "        AND  ba.BUS_ENTITY_ID = bea.BUS_ENTITY1_ID \n" +
        		 "      )   \n" ;

            logDebug("billDelete = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }


        {
            String sql = "UPDATE CLW_BUS_ENTITY SET BUS_ENTITY_STATUS_CD = '" + RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE + "',MOD_DATE=SYSDATE,MOD_BY='" + pUser + "'" +
                    " WHERE BUS_ENTITY_ID IN (SELECT ACCOUNT_ID FROM " + pTable + "_BILL WHERE ACCOUNT_ACTION_CD = 'D')";

            logDebug("billDelete = > SQL:" + sql);

            stmt = conn.createStatement();
            int n = stmt.executeUpdate(sql);
            logDebug("billDelete = > updated row:" + n);
            stmt.close();
        }
    }

    private void billInsert(Connection conn, int pStoreId, String pTable, String pUser) throws SQLException {
        Statement stmt;

        {
            String sql = "INSERT INTO CLW_BUS_ENTITY (BUS_ENTITY_ID,SHORT_DESC,ADD_BY,MOD_BY,BUS_ENTITY_TYPE_CD,BUS_ENTITY_STATUS_CD,\n" +
                    " WORKFLOW_ROLE_CD,LOCALE_CD,ADD_DATE,MOD_DATE) SELECT  ACCOUNT_ID,BILL_TO_NAME,'" + pUser + "','" + pUser + "','" + RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT + "','" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "',\n" +
                    " 'UNKNOWN','EN_US',SYSDATE,SYSDATE FROM " + pTable + "_BILL  WHERE ACCOUNT_ID>0  AND ACCOUNT_ACTION_CD='C'";

            logDebug("billInsert = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "INSERT INTO CLW_BUS_ENTITY_ASSOC (BUS_ENTITY_ASSOC_ID,BUS_ENTITY1_ID,BUS_ENTITY2_ID,BUS_ENTITY_ASSOC_CD,ADD_BY,\n" +
                    "\t\tMOD_BY,ADD_DATE,MOD_DATE) SELECT CLW_BUS_ENTITY_ASSOC_SEQ.NEXTVAL,ACCOUNT_ID," + pStoreId + ",'" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE + "','" + pUser + "','" + pUser + "'" +
                    ",SYSDATE,SYSDATE FROM " + pTable + "_BILL WHERE ACCOUNT_ID>0  AND ACCOUNT_ACTION_CD='C'";

            logDebug("billInsert = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }


        {
            String sql = "INSERT INTO CLW_PROPERTY (PROPERTY_ID,BUS_ENTITY_ID,SHORT_DESC,PROPERTY_TYPE_CD,PROPERTY_STATUS_CD,CLW_VALUE,\n" +
                    "\t\tADD_BY,MOD_BY,ADD_DATE,MOD_DATE) SELECT CLW_PROPERTY_SEQ.NEXTVAL,ACCOUNT_ID,'" + RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM + "','" + RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM + "',\n" +
                    "\t\t'ACTIVE',BILL_TO_NUM,'" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_BILL \n" +
                    "\t\tWHERE ACCOUNT_ID>0  AND CUS_SEL_CODE_ACTION_CD='C'";

            logDebug("billInsert = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }


        {
            String sql = "INSERT INTO CLW_PROPERTY (PROPERTY_ID,BUS_ENTITY_ID,SHORT_DESC,PROPERTY_TYPE_CD,PROPERTY_STATUS_CD,CLW_VALUE,ADD_BY,\n" +
                    "\t\tMOD_BY,ADD_DATE,MOD_DATE) SELECT CLW_PROPERTY_SEQ.NEXTVAL,ACCOUNT_ID,'ACCOUNT TYPE','" + RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_TYPE + "','" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "',\n" +
                    "\t\tCUS_SEL_CODE,'" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_BILL \n" +
                    "\t\tWHERE ACCOUNT_ID>0  AND CUS_SEL_CODE_ACTION_CD='C'";

            logDebug("billInsert = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "INSERT INTO CLW_ADDRESS (ADDRESS_ID,BUS_ENTITY_ID,NAME1,ADDRESS1,ADDRESS2,CITY,STATE_PROVINCE_CD,COUNTRY_CD,\n" +
                    "\t\tPOSTAL_CODE,ADDRESS_STATUS_CD,ADDRESS_TYPE_CD,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE) \n" +
                    "\t\tSELECT CLW_ADDRESS_SEQ.NEXTVAL,ACCOUNT_ID,BILL_TO_NAME,BILL_TO_ADD1,BILL_TO_ADD2,BILL_TO_CITY,BILL_TO_ST,'UNITED STATES',\n" +
                    "\t\tBILL_TO_ZIP,'" + RefCodeNames.ADDRESS_STATUS_CD.ACTIVE + "','" + RefCodeNames.ADDRESS_TYPE_CD.BILLING + "','" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_BILL \n" +
                    "\t\tWHERE ACCOUNT_ID>0  AND ACCOUNT_ADDRESS_ACTION_CD='C'";

            logDebug("billInsert = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    private void shipInsert(Connection conn, String pTable, String pUser) throws SQLException {
        Statement stmt;

        {
            String sql = "INSERT INTO CLW_BUS_ENTITY (BUS_ENTITY_ID,SHORT_DESC,ADD_BY,MOD_BY,BUS_ENTITY_TYPE_CD,BUS_ENTITY_STATUS_CD,WORKFLOW_ROLE_CD,\n" +
                    "\t\tLOCALE_CD,ADD_DATE,MOD_DATE) SELECT  SITE_ID,SHIP_TO_NAME,'" + pUser + "','" + pUser + "','" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "',DECODE(STATUS,\n" +
                    "                                                                              'A','"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"',\n" +
            		"																		 	   'I','"+RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE+"'),'UNKNOWN',\n" +
                    "\t\t'EN_US',SYSDATE,SYSDATE FROM " + pTable + "_SHIP  WHERE ACCOUNT_ID>0 AND SITE_ID>0 AND SITE_ACTION_CD='C'";

            logDebug("shipInsert = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "INSERT INTO CLW_BUS_ENTITY_ASSOC (BUS_ENTITY_ASSOC_ID,BUS_ENTITY1_ID,BUS_ENTITY2_ID,BUS_ENTITY_ASSOC_CD,\n" +
                    "\t\tADD_BY,MOD_BY,ADD_DATE,MOD_DATE) SELECT CLW_BUS_ENTITY_ASSOC_SEQ.NEXTVAL,SITE_ID,ACCOUNT_ID,\n" +
                    "\t\t'" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "','" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_SHIP  \n" +
                    "\t\tWHERE ACCOUNT_ID>0 AND SITE_ID>0 AND SITE_ACTION_CD='C'";

            logDebug("billInsert = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }


        {
            String sql = "INSERT INTO CLW_PROPERTY (PROPERTY_ID,BUS_ENTITY_ID,SHORT_DESC,PROPERTY_TYPE_CD,PROPERTY_STATUS_CD,CLW_VALUE,\n" +
                    "\t\tADD_BY,MOD_BY,ADD_DATE,MOD_DATE) SELECT CLW_PROPERTY_SEQ.NEXTVAL,SITE_ID,'" + RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER + "','" + RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER + "','" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "',\n" +
                    "\t\tSHIP_TO_NUM,'" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_SHIP \n" +
                    "\t\tWHERE ACCOUNT_ID>0  AND SITE_ID>0 AND SITE_ACTION_CD='C'";

            logDebug("shipInsert = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }                {            String sql = "INSERT INTO CLW_PROPERTY (PROPERTY_ID,BUS_ENTITY_ID,SHORT_DESC,PROPERTY_TYPE_CD,PROPERTY_STATUS_CD,CLW_VALUE,\n" +                    "\t\tADD_BY,MOD_BY,ADD_DATE,MOD_DATE) SELECT CLW_PROPERTY_SEQ.NEXTVAL,SITE_ID,'" + RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR + "','" + RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR + "','" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "',\n" +                    "\t\t'N','" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_SHIP \n" +                    "\t\tWHERE ACCOUNT_ID>0  AND SITE_ID>0 AND SITE_ACTION_CD='C'";            logDebug("shipInsert = > SQL:" + sql);            stmt = conn.createStatement();            stmt.executeUpdate(sql);            stmt.close();        }

        {
            String sql = " INSERT INTO CLW_ADDRESS (ADDRESS_ID,BUS_ENTITY_ID,NAME1,ADDRESS1,ADDRESS2,CITY,STATE_PROVINCE_CD,COUNTRY_CD,POSTAL_CODE,\n" +
                    "\t\tADDRESS_STATUS_CD,ADDRESS_TYPE_CD,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE) \n" +
                    "\t\tSELECT CLW_ADDRESS_SEQ.NEXTVAL,SITE_ID,SHIP_TO_NAME,SHIP_TO_ADD1,SHIP_TO_ADD2,SHIP_TO_CITY,SHIP_TO_ST,'UNITED STATES',\n" +
                    "\t\tSHIP_TO_ZIP,'" + RefCodeNames.ADDRESS_STATUS_CD.ACTIVE + "','" + RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "','" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_SHIP WHERE ACCOUNT_ID>0  \n" +
                    "\t\tAND SITE_ID>0  AND SITE_ADDRESS_ACTION_CD='C'";

            logDebug("shipInsert = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }


    private void billMatch(Connection conn, String pTable) throws SQLException {

        Statement stmt;

        {
            String sql = "UPDATE " + pTable + "_BILL SET ACCOUNT_ACTION_CD = 'U'\n" +
                    "                   WHERE EXISTS (SELECT 'U' FROM CLW_BUS_ENTITY\n" +
                    "                                 WHERE SHORT_DESC!=BILL_TO_NAME\n" +
                    "                                  AND BUS_ENTITY_ID=ACCOUNT_ID\n" +
                    "                                   AND BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT + "')";

            logDebug("billMatch = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "UPDATE " + pTable + "_BILL  SET ACCOUNT_ACTION_CD = 'U'           \n" +
                    "                   WHERE EXISTS (SELECT 'U' FROM CLW_BUS_ENTITY\n" +
                    "                                 WHERE BUS_ENTITY_STATUS_CD != '" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "'\n" +
                    "                                  AND BUS_ENTITY_ID=ACCOUNT_ID\n" +
                    "                                   AND BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT + "')";

            logDebug("billMatch = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        }

        {
            String sql = "UPDATE " + pTable + "_BILL  SET ACCOUNT_ADDRESS_ACTION_CD = 'U'\n" +
                    "                   WHERE EXISTS (SELECT 'U' FROM CLW_ADDRESS\n" +
                    "                                        WHERE ADDRESS_TYPE_CD='" + RefCodeNames.ADDRESS_TYPE_CD.BILLING + "'\n" +
                    "                                         AND (BILL_TO_NAME!=NAME1\n" +
                    "                                          OR BILL_TO_ADD1!=ADDRESS1\n" +
                    "                                           OR BILL_TO_ADD2!=ADDRESS2\n" +
                    "                                            OR BILL_TO_CITY!=CITY\n" +
                    "                                             OR BILL_TO_ST!=STATE_PROVINCE_CD\n" +
                    "                                              OR BILL_TO_ZIP!=POSTAL_CODE)\n" +
                    "                                         AND BUS_ENTITY_ID=ACCOUNT_ID)";

            logDebug("billMatch = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        }

        {
            String sql = "UPDATE " + pTable + "_BILL SET CUS_SEL_CODE_ACTION_CD = 'U'\n" +
                    "                    WHERE EXISTS (SELECT 'U' FROM CLW_PROPERTY\n" +
                    "                                        WHERE PROPERTY_TYPE_CD='" + RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_TYPE + "'\n" +
                    "                                          AND SHORT_DESC='ACCOUNT TYPE'\n" +
                    "                                            AND CLW_VALUE!=CUS_SEL_CODE\n" +
                    "                                              AND BUS_ENTITY_ID=ACCOUNT_ID)";

            logDebug("billMatch = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        }


        {
            String sql = "UPDATE " + pTable + "_BILL SET ACCOUNT_ACTION_CD = 'C',ACCOUNT_ADDRESS_ACTION_CD ='C',CUS_SEL_CODE_ACTION_CD='C',ACCOUNT_ID=CLW_BUS_ENTITY_SEQ.NEXTVAL WHERE ACCOUNT_ID IS NULL";

            logDebug("billMatch = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

    }

    private void shipMatch(Connection conn, String pTable) throws SQLException {
        Statement stmt;

        {
            String sql = " UPDATE " + pTable + "_SHIP  SET SITE_ACTION_CD = 'U'\n" +
                    "                   WHERE EXISTS (SELECT 'U'  FROM CLW_BUS_ENTITY\n" +
                    "                                    WHERE SHORT_DESC!=SHIP_TO_NAME\n" +
                    "                                     AND BUS_ENTITY_ID=SITE_ID\n" +
                    "                                      AND BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "')";

            logDebug("shipMatch = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = " UPDATE " + pTable + "_SHIP  SET SITE_ADDRESS_ACTION_CD = 'U'\n" +
                    "                   WHERE EXISTS (SELECT 'U' FROM CLW_ADDRESS\n" +
                    "                                        WHERE ADDRESS_TYPE_CD='" + RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "'\n" +
                    "                                         AND (SHIP_TO_NAME!=NAME1\n" +
                    "                                          OR SHIP_TO_ADD1!=ADDRESS1\n" +
                    "                                           OR SHIP_TO_ADD2!=ADDRESS2\n" +
                    "                                            OR SHIP_TO_CITY!=CITY\n" +
                    "                                             OR SHIP_TO_ST!=STATE_PROVINCE_CD\n" +
                    "                                              OR SHIP_TO_ZIP!=POSTAL_CODE)\n" +
                    "                                         AND BUS_ENTITY_ID=SITE_ID)";

            logDebug("shipMatch = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        }

        {
            String sql = " UPDATE " + pTable + "_SHIP SET SITE_ACTION_CD = 'C',SITE_ADDRESS_ACTION_CD ='C',SITE_ID=CLW_BUS_ENTITY_SEQ.NEXTVAL WHERE SITE_ID IS NULL";

            logDebug("shipMatch = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    private boolean billDrop(Connection conn, String pTable) throws SQLException {

        if (existTable(conn, pTable + "_BILL")) {
            dropTable(conn, pTable + "_BILL");
            return true;
        }

        return false;
    }

    private boolean tempDrop(Connection conn, String pTable) throws SQLException {

        if (existTable(conn, pTable + "_TEMP")) {
            dropTable(conn, pTable + "_TEMP");
            return true;
        }

        return false;
    }

    private boolean shipDrop(Connection conn, String pTable) throws SQLException {

        if (existTable(conn, pTable + "_SHIP")) {
            dropTable(conn, pTable + "_SHIP");
            return true;
        }

        return false;
    }

    private boolean existTable(Connection conn, String tableName) throws SQLException {

        String sql = "SELECT TABLE_NAME FROM USER_TABLES WHERE TABLE_NAME = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, tableName.toUpperCase());

        ResultSet rs = pstmt.executeQuery();

        return rs.next();
    }

    private void billCreate(Connection conn, String pTable) throws SQLException {
        Statement stmt;

        String sql = "CREATE TABLE " + pTable + "_BILL(\n" +
                "       BILL_TO_NUM  VARCHAR2(20) NULL,\n" +
                "       BILL_TO_NAME VARCHAR2(40) NULL,\n" +
                "       ACCOUNT_ID   NUMBER(38) NULL,\n" +
                "       BILL_TO_ADD1 VARCHAR2(80) NULL,\n" +
                "       BILL_TO_ADD2 VARCHAR2(80) NULL,\n" +
                "       BILL_TO_CITY VARCHAR2(40) NULL,\n" +
                "       BILL_TO_ST   VARCHAR2(30)  NULL,\n" +
                "       BILL_TO_ZIP  VARCHAR2(15)  NULL,\n" +
                "       CUS_SEL_CODE VARCHAR2(20) NULL,\n" +
                "       ACCOUNT_ACTION_CD VARCHAR2(2) NULL,\n" +
                "       ACCOUNT_ADDRESS_ACTION_CD VARCHAR(2) NULL,\n" +
                "       CUS_SEL_CODE_ACTION_CD VARCHAR(2),\n" +
                "       ADD_DATE DATE NOT NULL,\n" +
                "       ADD_BY   VARCHAR2 (255),\n" +
                "       MOD_DATE DATE NOT NULL,\n" +
                "       MOD_BY   VARCHAR2 (255)" +
                ")";

        logDebug("billCreate = > SQL:" + sql);
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();

    }

    private void shipCreate(Connection conn, String pTable) throws SQLException {
        Statement stmt;

        String sql = "CREATE TABLE " + pTable + "_SHIP(\n" +
                "        BILL_TO_NUM  VARCHAR2(19) NULL,\n" +
                "        ACCOUNT_ID NUMBER(38) NULL,\n" +
                "        SHIP_TO_NUM  VARCHAR2(19) NULL,\n" +
                "        SHIP_TO_NAME VARCHAR2(40) NULL,\n" +
                "        SITE_ID NUMBER(38) NULL,\n" +
                "        SHIP_TO_ADD1 VARCHAR2(40) NULL,\n" +
                "        SHIP_TO_ADD2 VARCHAR2(40) NULL,\n" +
                "        SHIP_TO_CITY VARCHAR2(40) NULL,\n" +
                "        SHIP_TO_ST   VARCHAR2(9)  NULL,\n" +
                "        SHIP_TO_ZIP  VARCHAR2(16)  NULL,\n" +
                "        STATUS       VARCHAR2(2)   NULL,\n" +
                "        SITE_ACTION_CD VARCHAR2(2) NULL,\n" +
                "        SITE_ADDRESS_ACTION_CD VARCHAR(2) NULL,\n" +
                "        ADD_DATE DATE NOT NULL,\n" +
                "        ADD_BY   VARCHAR2 (255),\n" +
                "        MOD_DATE DATE NOT NULL,\n" +
                "        MOD_BY   VARCHAR2 (255)\n" +
                "        )";

        logDebug("shipCreate = > SQL:" + sql);
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();

    }

    private void billLoad(Connection conn, String pTable, String pUser) throws SQLException {
        Statement stmt;

        {

            String sql = "INSERT /*+ APPEND */  INTO " + pTable + "_BILL (BILL_TO_NUM,BILL_TO_NAME,\n" +
                    "BILL_TO_ADD1,BILL_TO_ADD2,BILL_TO_CITY,BILL_TO_ST,BILL_TO_ZIP,CUS_SEL_CODE,\n" +
                    "ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) (SELECT A.BILL_TO_NUM, A.BILL_TO_NAME,A.BILL_TO_ADD1,A.BILL_TO_ADD2,\n" +
                    "A.BILL_TO_CITY,A.BILL_TO_ST,A.BILL_TO_ZIP,A.CUS_SEL_CODE,'" + pUser + "',SYSDATE,'" + pUser + "',SYSDATE FROM " + pTable + " A ,\n" +
                    "(SELECT MAX(ROWID) AS ROW_ID,BILL_TO_NUM FROM " + pTable + " \n" +
                    "   WHERE TRIM(BILL_TO_ADD1) IS NOT NULL\n" +
                    "       AND TRIM(BILL_TO_NUM)   IS NOT NULL \n" +
                    "        AND TRIM(BILL_TO_NAME)  IS NOT NULL \n" +
                    "   GROUP BY BILL_TO_NUM) B WHERE A.ROWID=B.ROW_ID)";


            stmt = conn.createStatement();
            logDebug("billLoad => SQL:" + sql);
            stmt.executeUpdate(sql);
            stmt.close();

        }

    }

    private void defineAccount(Connection conn, int pStoreId, String pTable, String pUser) throws SQLException {
        Statement stmt;
        {
            String sql = "CREATE INDEX "+pTable+"_INDEX1 ON " + pTable + "_BILL(BILL_TO_NUM)";

            logDebug("defineAccount => SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }


        {
            String sql = "CREATE INDEX "+pTable+"_INDEX2 ON " + pTable + "_BILL(ACCOUNT_ID)";

            logDebug("defineAccount = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "CREATE TABLE " + pTable + "_TEMP (\n" +
                    "        BILL_TO_NUM VARCHAR2(40),\n" +
                    "        BUS_ENTITY_ID NUMBER(38)\n" +
                    "        )";

            logDebug("defineAccount => SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "INSERT INTO " + pTable + "_TEMP (BILL_TO_NUM,BUS_ENTITY_ID) SELECT  P.CLW_VALUE,P.BUS_ENTITY_ID FROM CLW_PROPERTY P,CLW_BUS_ENTITY_ASSOC BEA\n" +
                    "               WHERE P.BUS_ENTITY_ID = BEA.BUS_ENTITY1_ID\n" +
                    "                 AND BEA.BUS_ENTITY2_ID = " + pStoreId + "\n" +
                    "                  AND P.SHORT_DESC ='" + RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM + "'\n" +
                    "                   AND BEA.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE + "'";

            logDebug("defineAccount => SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "CREATE INDEX "+pTable+"_INDEX3 ON " + pTable + "_TEMP(BILL_TO_NUM)";

            logDebug("defineAccount => SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {

            String sql = "UPDATE " + pTable + "_BILL B SET ACCOUNT_ID = \n" +
                    " (SELECT T.BUS_ENTITY_ID  FROM " + pTable + "_TEMP T WHERE T.BILL_TO_NUM=B.BILL_TO_NUM )," +
                    " MOD_BY = '" + pUser + "'," +
                    " MOD_DATE =  SYSDATE";

            logDebug("defineAccount => SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        }

        {

            String sql = "DROP TABLE  " + pTable + "_TEMP PURGE";
            logDebug("defineAccount => SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }


    private void defineSite(Connection conn, String pTable, String pUser) throws SQLException {
        Statement stmt;

        {
            String sql = "CREATE INDEX "+pTable+"_INDEX4 ON " + pTable + "_SHIP(BILL_TO_NUM)";

            logDebug("defineSite = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "UPDATE " + pTable + "_SHIP S SET ACCOUNT_ID = (SELECT B.ACCOUNT_ID FROM " + pTable + "_BILL B WHERE B.BILL_TO_NUM = S.BILL_TO_NUM)";

            logDebug("defineSite = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "CREATE TABLE " + pTable + "_TEMP(\n" +
                    "       SHIP_TO_NUM VARCHAR2(19) ,\n" +
                    "       SITE_ID NUMBER(38),\n" +
                    "       ACCOUNT_ID NUMBER(38)\n" +
                    ")";

            logDebug("defineSite = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = " CREATE INDEX "+pTable+"_INDEX5 ON " + pTable + "_SHIP(SITE_ID)";

            logDebug("defineSite = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "INSERT INTO " + pTable + "_TEMP (SHIP_TO_NUM,SITE_ID,ACCOUNT_ID) SELECT  P.CLW_VALUE,BEA.BUS_ENTITY1_ID,BEA.BUS_ENTITY2_ID\n" +
                    "FROM CLW_PROPERTY P,CLW_BUS_ENTITY_ASSOC BEA," + pTable + "_BILL B\n" +
                    "               WHERE P.BUS_ENTITY_ID = BEA.BUS_ENTITY1_ID\n" +
                    "                 AND BEA.BUS_ENTITY2_ID = B.ACCOUNT_ID\n" +
                    "                  AND BEA.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "'\n" +
                    "                   AND P.SHORT_DESC ='" + RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER + "' ";

            logDebug("defineSite = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "CREATE INDEX "+pTable+"_INDEX6 ON " + pTable + "_TEMP(SHIP_TO_NUM)";

            logDebug("defineSite = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "CREATE INDEX "+pTable+"_INDEX7 ON " + pTable + "_TEMP(ACCOUNT_ID)";

            logDebug("defineSite = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {

            String sql = "UPDATE " + pTable + "_SHIP S SET SITE_ID = (SELECT T.SITE_ID  FROM " + pTable + "_TEMP T \n" +
                    "WHERE T.SHIP_TO_NUM=S.SHIP_TO_NUM AND T.ACCOUNT_ID = S.ACCOUNT_ID)," +
                    " MOD_BY = '" + pUser + "'," +
                    " MOD_DATE =  SYSDATE";

            logDebug("defineSite = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        }

        {

            String sql = "DROP TABLE  " + pTable + "_TEMP PURGE";
            logDebug("defineSite = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

    }

    private void shipLoad(Connection conn, String pTable, String pUser) throws SQLException {
        Statement stmt;


        String sql = "INSERT INTO " + pTable + "_SHIP (BILL_TO_NUM,SHIP_TO_NUM,SHIP_TO_NAME,SHIP_TO_ADD1,SHIP_TO_ADD2,SHIP_TO_CITY,SHIP_TO_ST,SHIP_TO_ZIP,STATUS,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE)\n" +
                "        SELECT A.BILL_TO_NUM,A.SHIP_TO_NUM,A.SHIP_TO_NAME,A.SHIP_TO_ADD1,A.SHIP_TO_ADD2,A.SHIP_TO_CITY,A.SHIP_TO_ST,A.SHIP_TO_ZIP, A.STATUS," + "'" + pUser + "',SYSDATE,'" + pUser + "',SYSDATE\n" +
                "        FROM " + pTable + " A ,(SELECT MAX(ROWID) AS ROW_ID,BILL_TO_NUM,SHIP_TO_NUM FROM " + pTable +
                "        WHERE TRIM(BILL_TO_ADD1) IS NOT NULL\n" +
                "         AND TRIM(BILL_TO_NUM)   IS NOT NULL\n " +
                "         AND TRIM(BILL_TO_NAME)  IS NOT NULL\n " +
                "         AND TRIM(SHIP_TO_NUM)   IS NOT NULL\n " +
                "         AND TRIM(SHIP_TO_NAME)  IS NOT NULL\n " +
                "         AND LENGTH(SHIP_TO_ZIP) <= "+SHIP_TO_ZIP_MAX_LENGTH +
                "         GROUP BY BILL_TO_NUM,SHIP_TO_NUM) B WHERE A.ROWID=B.ROW_ID";


        logDebug("shipLoad = > SQL:" + sql);

        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();


    }


    private void shipUpdate(Connection conn, String pTable, String pUser) throws SQLException {
        Statement stmt;

        {
            String sql = " UPDATE CLW_BUS_ENTITY SET (SHORT_DESC,BUS_ENTITY_STATUS_CD) = \n" +
            		"                       (SELECT S.SHIP_TO_NAME, DECODE(S.STATUS,\n" +
                    "                               'A','"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"',\n" +
            		"						        'I','"+RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE+"'\n" +
            		"							  )\n" +
                    "                        FROM " + pTable + "_SHIP S\n" +
                    "                        WHERE S.SITE_ID = BUS_ENTITY_ID\n" +
                    "                        AND BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "'\n" +
                    "                        AND S.SITE_ACTION_CD ='U'),\n" +
                    "             MOD_BY='" + pUser + "',\n" +
                    "             MOD_DATE=SYSDATE\n" +
                    "\n" +
                    "       WHERE  bus_entity_id IN (SELECT S.SITE_ID FROM " + pTable + "_SHIP S\n" +
                    "                                WHERE S.SITE_ID = BUS_ENTITY_ID\n" +
                    "                                AND BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "'\n" +
                    "                                AND S.SITE_ACTION_CD = 'U')";

            logDebug("shipUpdate = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "UPDATE CLW_ADDRESS SET\n" +
                    "                (NAME1,ADDRESS1,ADDRESS2,CITY,STATE_PROVINCE_CD,POSTAL_CODE) = \n" +
                    "                (SELECT S.SHIP_TO_NAME,S.SHIP_TO_ADD1,S.SHIP_TO_ADD2,S.SHIP_TO_CITY,S.SHIP_TO_ST,S.SHIP_TO_ZIP FROM " + pTable + "_SHIP S\n" +
                    "                 WHERE S.SITE_ID=BUS_ENTITY_ID\n" +
                    "                 AND ADDRESS_TYPE_CD='" + RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "'\n" +
                    "                 AND S.SITE_ADDRESS_ACTION_CD='U'),\n" +
                    "              MOD_BY='" + pUser + "',\n" +
                    "              MOD_DATE=SYSDATE\n" +
                    "\n" +
                    "              WHERE BUS_ENTITY_ID IN (SELECT S.SITE_ID FROM " + pTable + "_SHIP S\n" +
                    "                                        WHERE S.SITE_ID=BUS_ENTITY_ID\n" +
                    "                                        AND ADDRESS_TYPE_CD='" + RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "'\n" +
                    "                                        AND S.SITE_ADDRESS_ACTION_CD='U')";

            logDebug("shipUpdate = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        }
    }

    private void billUpdate(Connection conn, String pTable, String pUser) throws SQLException {
        Statement stmt;


        {
            String sql = "UPDATE CLW_BUS_ENTITY SET (SHORT_DESC,BUS_ENTITY_STATUS_CD) = (SELECT B.BILL_TO_NAME,'"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"' FROM " + pTable + "_BILL B\n" +
                    "                                                                          WHERE B.ACCOUNT_ID = BUS_ENTITY_ID\n" +
                    "                                                                            AND BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT + "'\n" +
                    "                                                                              AND B.ACCOUNT_ACTION_CD = 'U'),\n" +
                    "                        MOD_BY='" + pUser + "',\n" +
                    "                        MOD_DATE=SYSDATE\n" +
                    "\n" +
                    "                        WHERE  BUS_ENTITY_ID IN (SELECT B.ACCOUNT_ID FROM " + pTable + "_BILL B\n" +
                    "                                                          WHERE B.ACCOUNT_ID = BUS_ENTITY_ID\n" +
                    "                                                             AND BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT + "'\n" +
                    "                                                              AND B.ACCOUNT_ACTION_CD = 'U')";

            logDebug("billUpdate = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        }

        {
            String sql = " UPDATE CLW_ADDRESS SET (NAME1,ADDRESS1,ADDRESS2,CITY,STATE_PROVINCE_CD,POSTAL_CODE) = (SELECT  B.BILL_TO_NAME, B.BILL_TO_ADD1, B.BILL_TO_ADD2, B.BILL_TO_CITY, B.BILL_TO_ST, B.BILL_TO_ZIP FROM " + pTable + "_BILL B\n" +
                    "                                                                                                 WHERE B.ACCOUNT_ID=BUS_ENTITY_ID\n" +
                    "                                                                                                  AND ADDRESS_TYPE_CD='" + RefCodeNames.ADDRESS_TYPE_CD.BILLING + "'\n" +
                    "                                                                                                   AND B.ACCOUNT_ADDRESS_ACTION_CD = 'U'),\n" +
                    "                   MOD_BY='" + pUser + "',\n" +
                    "                   MOD_DATE=SYSDATE\n" +
                    "\n" +
                    "                   WHERE BUS_ENTITY_ID IN (SELECT B.ACCOUNT_ID FROM " + pTable + "_BILL B\n" +
                    "                                             WHERE B.ACCOUNT_ID=BUS_ENTITY_ID\n" +
                    "                                              AND ADDRESS_TYPE_CD='" + RefCodeNames.ADDRESS_TYPE_CD.BILLING + "'\n" +
                    "                                               AND B.ACCOUNT_ADDRESS_ACTION_CD = 'U')";

            logDebug("billUpdate = > SQL:" + sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        {
            String sql = "UPDATE CLW_PROPERTY SET (CLW_VALUE) = (SELECT B.CUS_SEL_CODE FROM " + pTable + "_BILL B\n" +
                    "                                                               WHERE B.ACCOUNT_ID=BUS_ENTITY_ID\n" +
                    "                                                                AND B.CUS_SEL_CODE_ACTION_CD = 'U'\n" +
                    "                                                                 AND PROPERTY_TYPE_CD='" + RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_TYPE + "'\n" +
                    "                                                                  AND SHORT_DESC='ACCOUNT TYPE'),\n" +
                    "          MOD_BY='" + pUser + "',\n" +
                    "          MOD_DATE=SYSDATE\n" +
                    "\n" +
                    "        WHERE  BUS_ENTITY_ID IN (SELECT B.ACCOUNT_ID FROM " + pTable + "_BILL B\n" +
                    "                                         WHERE B.ACCOUNT_ID=BUS_ENTITY_ID\n" +
                    "                                           AND B.CUS_SEL_CODE_ACTION_CD = 'U'\n" +
                    "                                            AND PROPERTY_TYPE_CD='" + RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_TYPE + "'\n" +
                    "                                             AND SHORT_DESC='ACCOUNT TYPE')";

            logDebug("billUpdate = > SQL:" + sql);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        }

    }


    private void accept(Connection conn,
                        byte[] pData,
                        String pTable,
                        String pFileName,
                        java.util.Date pModDate,
                        String pUser) throws Exception {

        java.sql.Date fModDate = pModDate != null ? new java.sql.Date(pModDate.getTime()) : null;
        java.util.Date current = new java.sql.Date(System.currentTimeMillis());

        logInfo("accept => BEGIN.");

        if (existTable(conn, pTable)) {
            dropTable(conn, pTable);
        }

        createMainTable(conn, pTable);
        
        JanPakLoaderAgent jplAgent = new JanPakLoaderAgent();
        jplAgent.accept(conn, pData, pTable, pFileName, fModDate, current, pUser);

        logInfo("accept => END.");
    }

    private void createMainTable(Connection conn, String pTable) throws SQLException {

        String sql = "CREATE TABLE " + pTable + "(\n" +
                "  FILE_NAME    CHAR(28)     NULL,\n" +
                "  FILE_DATE    DATE         NULL,\n" +
                "  SHIP_TO_NUM  VARCHAR2(6)  NULL,\n" +
                "  SHIP_TO_NAME VARCHAR2(40) NULL,\n" +
                "  SHIP_TO_ADD1 VARCHAR2(40) NULL,\n" +
                "  SHIP_TO_ADD2 VARCHAR2(40) NULL,\n" +
                "  SHIP_TO_CITY VARCHAR2(40) NULL,\n" +
                "  SHIP_TO_ST   VARCHAR2(9)  NULL,\n" +
                "  SHIP_TO_ZIP  VARCHAR2(16) NULL,\n" +
                "  BILL_TO_NUM  VARCHAR2(19) NULL,\n" +
                "  BILL_TO_NAME VARCHAR2(40) NULL,\n" +
                "  BILL_TO_ADD1 VARCHAR2(40) NULL,\n" +
                "  BILL_TO_ADD2 VARCHAR2(35) NULL,\n" +
                "  BILL_TO_CITY VARCHAR2(28) NULL,\n" +
                "  BILL_TO_ST   VARCHAR2(9)  NULL,\n" +
                "  BILL_TO_ZIP  VARCHAR2(9)  NULL,\n" +
                "  CUS_SEL_CODE VARCHAR2(50) NULL,\n" +
                "  STATUS       VARCHAR2(2)  NULL,\n" +
                "  ADD_DATE DATE NOT NULL,\n" +
                "  ADD_BY   VARCHAR2 (255),\n" +
                "  MOD_DATE DATE NOT NULL,\n" +
                "  MOD_BY   VARCHAR2 (255)\n" +
                ")";

        logDebug("createMainTable = > sql:" + sql);

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();

    }

    private void dropTable(Connection conn, String pTableName) throws SQLException {
        String sql = "DROP TABLE " + pTableName + " PURGE";
        logDebug("dropTable = > sql:" + sql);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }


    public StringBuffer report(Connection conn, String pTable) throws SQLException {

        Statement stmt;
        ResultSet rs;
        StringBuffer report = new StringBuffer();

        report.append("Report(U-UPDATE,C-CREATE,D-DELETE,I-IGNORE,E-ERROR)");
        report.append("[");

        if (existTable(conn, pTable) && existTable(conn, pTable + "_BILL") && existTable(conn, pTable + "_SHIP")) {

            {
                String sql = "SELECT COUNT(BILL_TO_NUM) FROM " + pTable + "_BILL WHERE ACCOUNT_ACTION_CD = 'U'";

                report.append("ACCOUNT(U):");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }

            {
                String sql = "SELECT COUNT(BILL_TO_NUM) FROM " + pTable + "_BILL WHERE ACCOUNT_ADDRESS_ACTION_CD = 'U'";

                report.append(", ACCOUNT_ADDRESS(U):");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }

            {
                String sql = "SELECT COUNT(BILL_TO_NUM) FROM " + pTable + "_BILL WHERE CUS_SEL_CODE_ACTION_CD = 'U'";

                report.append(", CUS_SEL_CODE(U):");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }

            {
                String sql = "SELECT COUNT(SHIP_TO_NUM) FROM " + pTable + "_SHIP WHERE SITE_ACTION_CD = 'U'";

                report.append(", SITE(U):");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }


            {
                String sql = "SELECT COUNT(SHIP_TO_NUM) FROM " + pTable + "_SHIP WHERE SITE_ADDRESS_ACTION_CD = 'U'";

                report.append(", SITE_ADDRESS(U):");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }

            {
                String sql = "SELECT COUNT(BILL_TO_NUM) FROM " + pTable + "_BILL WHERE ACCOUNT_ACTION_CD = 'C'";

                report.append(", ACCOUNT(C):");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }

            {
                String sql = "SELECT COUNT(SHIP_TO_NUM) FROM  " + pTable + "_SHIP WHERE SITE_ACTION_CD='C'";

                report.append(", SITE(C):");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }

            {
                String sql = "SELECT COUNT(ACCOUNT_ID) FROM " + pTable + "_BILL WHERE ACCOUNT_ACTION_CD='D'";

                report.append(", ACCOUNT(D):");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }

            {
                String sql = "SELECT COUNT(SITE_ID) FROM " + pTable + "_SHIP WHERE SITE_ACTION_CD='D'";

                report.append(", SITE(D):");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }

            {
                String sql = "SELECT COUNT(T1.ROWID) FROM " + pTable + " T1,(SELECT MAX(ROWID) AS ROW_ID,BILL_TO_NUM FROM " + pTable + "  WHERE TRIM(BILL_TO_ADD1) IS NULL GROUP BY BILL_TO_NUM) T2 \n" +
                        "WHERE T1.ROWID=T2.ROW_ID";

                report.append(", ACCOUNT(I):");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }

            {
                String sql = "SELECT COUNT(T1.ROWID) FROM " + pTable + " T1,(SELECT MAX(ROWID) AS ROW_ID,BILL_TO_NUM FROM " + pTable + "  WHERE TRIM(BILL_TO_ADD1) IS NOT  NULL \n" +
                        "AND (TRIM(BILL_TO_NUM)   IS NULL OR TRIM(BILL_TO_NAME) IS NULL) GROUP BY BILL_TO_NUM) T2 \n" +
                        "WHERE T1.ROWID=T2.ROW_ID";

                report.append(", ACCOUNT(E):");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }

            {
                String sql = "SELECT COUNT(T1.ROWID) FROM " + pTable + " T1,(SELECT MAX(ROWID) AS ROW_ID,BILL_TO_NUM FROM " + pTable + "  WHERE TRIM(SHIP_TO_NUM) IS  NULL OR TRIM(SHIP_TO_NAME)  IS NULL OR LENGTH(SHIP_TO_ZIP)>15 GROUP BY BILL_TO_NUM,SHIP_TO_NUM ) T2 \n" +
                        "WHERE T1.ROWID=T2.ROW_ID";

                report.append(", SITE(E):");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }
        }

        report.append("]");
        return report;
    }

}

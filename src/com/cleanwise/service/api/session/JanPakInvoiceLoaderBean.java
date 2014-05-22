package com.cleanwise.service.api.session;

import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.dao.JanPakLoaderAgent;

import javax.ejb.CreateException;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;
import java.sql.*;

public class JanPakInvoiceLoaderBean extends UtilityServicesAPI {

    private static final String price_format_i = "999999999";
    private static final String price_format_f = "999999999.99";
    private static final String date_format    = "MM/dd/yyyy";

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

    public void prepare(int pStoreId, int pDistributorId, String pTable) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            prepare(conn, pStoreId, pDistributorId, pTable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public void match(int pStoreId, int pDistributorId, String pTable) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            match(conn, pStoreId, pDistributorId, pTable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public void insert(int pStoreId, int pDistributorId, String pTable, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            insert(conn, pStoreId, pDistributorId, pTable, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public void dropWorkTables(String pTable) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            dropWorkTables(conn, pTable);
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

    private StringBuffer report(Connection conn, String pTable) throws SQLException {
        Statement stmt;
        ResultSet rs;
        StringBuffer report = new StringBuffer();

        report.append("Report[");

        if (existTable(conn, pTable + "_INV")) {

            {
                String sql = "SELECT COUNT(INVOICE_INVOICE_ID) FROM " + pTable + "_INV WHERE INVOICE_DIST_ID>0 AND (INV_ACTION_CD IS NULL OR INV_ACTION_CD!='C')";

                report.append("INVOICE DEFINED:");
                logDebug("report = > SQL:" + sql);

                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();

                report.append(rs.getInt(1));

                rs.close();
                stmt.close();

            }

            {
                String sql = "SELECT COUNT(INVOICE_INVOICE_ID) FROM " + pTable + "_INV WHERE INV_ACTION_CD='C'";

                report.append(", INVOICE CREATED:");
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

    private void insert(Connection conn, int pStoreId, int pDistributorId, String pTable, String pUser) throws SQLException {
        String sql;
        {
            sql = "INSERT INTO CLW_INVOICE_DIST (INVOICE_DIST_ID,\n" +
                    "BUS_ENTITY_ID,\n" +
                    "INVOICE_NUM,\n" +
                    "INVOICE_DATE,\n" +
                    "INVOICE_STATUS_CD,\n" +
                    "SHIP_TO_NAME,\n" +
                    "SHIP_TO_ADDRESS_1,\n" +
                    "SHIP_TO_ADDRESS_2,\n" +
                    "SHIP_TO_ADDRESS_3,\n" +
                    "SHIP_TO_ADDRESS_4,\n" +
                    "SHIP_TO_CITY,\n" +
                    "SHIP_TO_STATE,\n" +
                    "SHIP_TO_POSTAL_CODE,\n" +
                    "SHIP_TO_COUNTRY,\n" +
                    "SUB_TOTAL,\n" +
                    "STORE_ID,\n" +
                    "SITE_ID,\n" +
                    "ACCOUNT_ID,\n" +
                    "ADD_BY,\n" +
                    "ADD_DATE,\n" +
                    "MOD_BY,\n" +
                    "MOD_DATE) SELECT INVOICE_DIST_ID," + pDistributorId + ",INVOICE_NUM,INVOICE_DATE,'" + RefCodeNames.INVOICE_STATUS_CD.INVOICE_HISTORY + "'," +
                    "SHIP_TO_NAME,SHIP_TO_ADDRESS1,SHIP_TO_ADDRESS2,SHIP_TO_ADDRESS3,SHIP_TO_ADDRESS4,SHIP_TO_CITY,SHIP_TO_STATE,SHIP_TO_POSTAL_CODE," +
                    "SHIP_TO_COUNTRY,SUB_TOTAL," + pStoreId + ",SITE_ID,ACCOUNT_ID,'" + pUser + "',SYSDATE,'" + pUser + "',SYSDATE FROM " + pTable + "_INV WHERE INV_ACTION_CD ='C'";
            executeUpdate(conn, sql);
        }

        {
            sql = "INSERT INTO CLW_ORDER_PROPERTY (ORDER_PROPERTY_ID,\n" +
                    "INVOICE_DIST_ID,\n" +
                    "SHORT_DESC,\n" +
                    "CLW_VALUE,\n" +
                    "ORDER_PROPERTY_STATUS_CD,\n" +
                    "ORDER_PROPERTY_TYPE_CD,\n" +
                    "ADD_DATE,\n" +
                    "ADD_BY,\n" +
                    "MOD_DATE,\n" +
                    "MOD_BY) SELECT  CLW_ORDER_PROPERTY_SEQ.NEXTVAL,INVOICE_DIST_ID,SHORT_DESC,VALUE,'" + RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE + "','" + RefCodeNames.ORDER_PROPERTY_TYPE_CD.JANPAK_INVOICE + "',SYSDATE,'" + pUser + "',SYSDATE,'" + pUser + "' FROM " + pTable + "_INV_PROPERTY WHERE INV_PROPERTY_ACTION_CD='C'";
            executeUpdate(conn, sql);
        }

        {
            sql = "INSERT INTO CLW_INVOICE_DIST_DETAIL (INVOICE_DIST_DETAIL_ID,\n" +
                    "INVOICE_DIST_ID,\n" +
                    "DIST_LINE_NUMBER,\n" +
                    "DIST_ITEM_SKU_NUM,\n" +
                    "DIST_ITEM_SHORT_DESC,\n" +
                    "DIST_ITEM_UOM,\n" +
                    "DIST_ITEM_QUANTITY,\n" +
                    "ADJUSTED_COST,\n" +
                    "ADD_DATE,\n" +
                    "ADD_BY,\n" +
                    "MOD_DATE,\n" +
                    "MOD_BY,\n" +
                    "ITEM_RECEIVED_COST,\n" +
                    "DIST_ITEM_QTY_RECEIVED,\n" +
                    "INVOICE_DIST_SKU_NUM,\n" +
                    "DIST_INTO_STOCK_COST)  (SELECT INVOICE_DIST_DETAIL_ID,INVOICE_DIST_ID,DIST_LINE_NUMBER,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC," +
                    "DIST_ITEM_UOM,DIST_ITEM_QTY_RECEIVED,ADJUSTED_COST,SYSDATE,'" + pUser + "',SYSDATE,'" + pUser + "',ITEM_RECEIVED_COST," +
                    "DIST_ITEM_QTY_RECEIVED,DIST_ITEM_SKU_NUM," +
                    "DIST_INTO_STOCK_COST FROM " + pTable + "_INV_ITEM WHERE INV_ITEM_ACTION_CD='C')";
            executeUpdate(conn, sql);
        }

        {
            sql = "INSERT INTO CLW_ORDER_PROPERTY (ORDER_PROPERTY_ID,\n" +
                    "INVOICE_DIST_ID,\n" +
                    "INVOICE_DIST_DETAIL_ID,\n" +
                    "SHORT_DESC,\n" +
                    "CLW_VALUE,\n" +
                    "ORDER_PROPERTY_STATUS_CD,\n" +
                    "ORDER_PROPERTY_TYPE_CD,\n" +
                    "ADD_DATE,\n" +
                    "ADD_BY,\n" +
                    "MOD_DATE,\n" +
                    "MOD_BY) SELECT  CLW_ORDER_PROPERTY_SEQ.NEXTVAL,NULL,INVOICE_DIST_DETAIL_ID,SHORT_DESC,VALUE,'" + RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE + "','" + RefCodeNames.ORDER_PROPERTY_TYPE_CD.JANPAK_INVOICE_ITEM + "',SYSDATE,'" + pUser + "',SYSDATE,'" + pUser + "' FROM " + pTable + "_INV_ITEM_PROPERTY " +
                    "WHERE INV_ITEM_PROPERTY_ACTION_CD='C'";
            executeUpdate(conn, sql);
        }
    }

    private void match(Connection conn, int pStoreId, int pDistributorId, String pTable) throws SQLException {
        tempDrop(conn, pTable);
        defineInvoice(conn, pStoreId, pDistributorId, pTable);
        setActionCds(conn, pTable);
    }

    private void setActionCds(Connection conn, String pTable) throws SQLException {
        String sql;

        {
            sql = "UPDATE " + pTable + "_INV SET INV_ACTION_CD='C' WHERE ACCOUNT_ID>0 AND SITE_ID>0 AND INVOICE_DIST_ID IS NULL";
            executeUpdate(conn, sql);
        }

        {
            sql = "UPDATE " + pTable + "_INV SET INVOICE_DIST_ID = CLW_INVOICE_DIST_SEQ.NEXTVAL WHERE INV_ACTION_CD ='C'";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX18 ON " + pTable + "_INV_PROPERTY(INVOICE_INVOICE_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = "UPDATE " + pTable + "_INV_PROPERTY IP SET (INVOICE_DIST_ID,INV_PROPERTY_ACTION_CD) =(SELECT II.INVOICE_DIST_ID,II.INV_ACTION_CD FROM " + pTable + "_INV II WHERE II.INVOICE_INVOICE_ID = IP.INVOICE_INVOICE_ID AND II.INV_ACTION_CD='C')";
            executeUpdate(conn, sql);
        }

        {
            sql = "UPDATE  " + pTable + "_INV_ITEM I SET (INVOICE_DIST_ID,INV_ITEM_ACTION_CD) =(SELECT II.INVOICE_DIST_ID,II.INV_ACTION_CD FROM " + pTable + "_INV II WHERE II.INVOICE_INVOICE_ID = I.INVOICE_INVOICE_ID AND II.INV_ACTION_CD='C')";
            executeUpdate(conn, sql);
        }

        {
            sql = "UPDATE  " + pTable + "_INV_ITEM I SET INVOICE_DIST_DETAIL_ID = CLW_INVOICE_DIST_DETAIL_SEQ.NEXTVAL WHERE INV_ITEM_ACTION_CD = 'C'";
            executeUpdate(conn, sql);
        }

        {
            sql = "UPDATE  " + pTable + "_INV_ITEM_PROPERTY IP SET (INVOICE_DIST_ID,INVOICE_DIST_DETAIL_ID,INV_ITEM_PROPERTY_ACTION_CD)=\n" +
                    "(SELECT I.INVOICE_DIST_ID,I.INVOICE_DIST_DETAIL_ID,I.INV_ITEM_ACTION_CD FROM " + pTable + "_INV_ITEM I\n" +
                    "  WHERE I.INVOICE_INVOICE_ITEM_ID = IP.INVOICE_INVOICE_ITEM_ID AND I.INV_ITEM_ACTION_CD='C')";
            executeUpdate(conn, sql);
        }

    }

    private void defineInvoice(Connection conn, int pStoreId, int pDistributorId, String pTable) throws SQLException {
        String sql;

        {
            sql = "CREATE TABLE  " + pTable + "_TEMP  AS SELECT II.INVOICE_INVOICE_ID,ID.INVOICE_DIST_ID FROM " + pTable + "_INV II ,CLW_INVOICE_DIST ID " +
                    "WHERE ID.STORE_ID= " + pStoreId + "\n" +
                    " AND ID.BUS_ENTITY_ID =" + pDistributorId + "\n" +
                    " AND II.ACCOUNT_ID = ID.ACCOUNT_ID\n" +
                    " AND II.SITE_ID = ID.SITE_ID\n" +
                    " AND II.INVOICE_NUM = ID.INVOICE_NUM\n" +
                    " AND II.INVOICE_DATE = ID.INVOICE_DATE";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX11 ON " + pTable + "_TEMP(INVOICE_INVOICE_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX12 ON " + pTable + "_TEMP(INVOICE_DIST_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = " CREATE TABLE " + pTable + "_TEMP1 AS SELECT II.INVOICE_INVOICE_ID,II.INVOICE_NUM,OP.SHORT_DESC,OP.VALUE FROM " + pTable + "_INV  II," + pTable + "_INV_PROPERTY  OP\n" +
                    " WHERE II.INVOICE_INVOICE_ID=OP.INVOICE_INVOICE_ID AND II.INVOICE_INVOICE_ID IN (SELECT DISTINCT INVOICE_INVOICE_ID FROM " + pTable + "_TEMP)";
            executeUpdate(conn, sql);
        }

        {
            sql = " CREATE TABLE " + pTable + "_TEMP2 AS \n" +
                    " SELECT I.*,OP.SHORT_DESC,OP.CLW_VALUE AS VALUE FROM (SELECT IT.INVOICE_INVOICE_ID,ID.INVOICE_NUM,ID.INVOICE_DIST_ID \n" +
                    " FROM CLW_INVOICE_DIST ID ," + pTable + "_TEMP IT\n" +
                    "  WHERE  ID.INVOICE_DIST_ID = IT.INVOICE_DIST_ID) I LEFT JOIN CLW_ORDER_PROPERTY OP ON I.INVOICE_DIST_ID = OP.INVOICE_DIST_ID";
            executeUpdate(conn, sql);
        }

        {

            sql = " DROP TABLE  " + pTable + "_TEMP PURGE";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX13 ON " + pTable + "_TEMP1(INVOICE_INVOICE_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX14 ON " + pTable + "_TEMP1(SHORT_DESC)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX15 ON " + pTable + "_TEMP1(VALUE)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX16 ON " + pTable + "_TEMP2(INVOICE_INVOICE_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX17 ON " + pTable + "_TEMP2(SHORT_DESC)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX18 ON " + pTable + "_TEMP2(VALUE)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE TABLE " + pTable + "_TEMP AS SELECT I1.INVOICE_INVOICE_ID,I1.INVOICE_DIST_ID FROM" +
                    " (SELECT T1.INVOICE_INVOICE_ID,T1.INVOICE_NUM,T2.INVOICE_DIST_ID,COUNT(T1.INVOICE_NUM) AS COUNT FROM " + pTable + "_TEMP1 T1," + pTable + "_TEMP2 T2\n" +
                    "  WHERE T1.INVOICE_INVOICE_ID=T2.INVOICE_INVOICE_ID\n" +
                    "   AND T1.SHORT_DESC = T2.SHORT_DESC\n" +
                    "   AND NVL(T1.VALUE,  ' ')=NVL(T2.VALUE, ' ') GROUP BY T1.INVOICE_INVOICE_ID,T2.INVOICE_DIST_ID,T1.INVOICE_NUM) I1,\n" +
                    "  (SELECT T1.INVOICE_INVOICE_ID,T1.INVOICE_NUM,COUNT(T1.INVOICE_NUM) AS COUNT  FROM " + pTable + "_TEMP1 T1 GROUP BY T1.INVOICE_INVOICE_ID,T1.INVOICE_NUM) I2,\n" +
                    "  (SELECT T2.INVOICE_DIST_ID,T2.INVOICE_NUM,COUNT(T2.INVOICE_NUM) AS COUNT FROM " + pTable + "_TEMP2 T2 GROUP BY T2.INVOICE_DIST_ID,T2.INVOICE_NUM) I3\n" +
                    "   WHERE I1.INVOICE_INVOICE_ID=I2.INVOICE_INVOICE_ID AND I1.INVOICE_NUM=I2.INVOICE_NUM\n" +
                    "    AND I1.COUNT=I2.COUNT AND I1.INVOICE_DIST_ID=I3.INVOICE_DIST_ID\n" +
                    "    AND I2.INVOICE_NUM =I3.INVOICE_NUM AND I2.COUNT=I3.COUNT";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX19 ON " + pTable + "_TEMP(INVOICE_INVOICE_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX20 ON " + pTable + "_TEMP(INVOICE_DIST_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX21 ON " + pTable + "_INV_ITEM_PROPERTY(INVOICE_INVOICE_ITEM_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = " DROP TABLE  " + pTable + "_TEMP1 PURGE";
            executeUpdate(conn, sql);
        }

        {
            sql = " DROP TABLE  " + pTable + "_TEMP2 PURGE";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE TABLE " + pTable + "_TEMP1 AS SELECT II.INVOICE_INVOICE_ID,II.INVOICE_INVOICE_ITEM_ID,II.INVOICE_NUM," +
                    "II.DIST_ITEM_SKU_NUM,II.DIST_ITEM_UOM,II.DIST_ITEM_QTY_RECEIVED,II.ITEM_RECEIVED_COST,II.DIST_LINE_NUMBER," +
                    "II.DIST_INTO_STOCK_COST,II.ADJUSTED_COST,IP.SHORT_DESC,IP.VALUE " +
                    "FROM " + pTable + "_INV_ITEM II ," + pTable + "_INV_ITEM_PROPERTY IP \n" +
                    "WHERE II.INVOICE_INVOICE_ITEM_ID = IP.INVOICE_INVOICE_ITEM_ID \n" +
                    "AND II.INVOICE_INVOICE_ID IN (SELECT DISTINCT INVOICE_INVOICE_ID FROM " + pTable + "_TEMP)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE TABLE " + pTable + "_TEMP2 AS SELECT I.*,OP.SHORT_DESC,OP.CLW_VALUE AS VALUE \n" +
                    "FROM (SELECT II.INVOICE_INVOICE_ID,II.INVOICE_NUM,II.INVOICE_DIST_ID,IDD.INVOICE_DIST_DETAIL_ID,IDD.DIST_ITEM_SKU_NUM,IDD.DIST_LINE_NUMBER,IDD.DIST_ITEM_UOM,IDD.DIST_ITEM_QTY_RECEIVED,IDD.ITEM_RECEIVED_COST,IDD.DIST_INTO_STOCK_COST,IDD.ADJUSTED_COST \n" +
                    "FROM CLW_INVOICE_DIST_DETAIL IDD,(SELECT IT.INVOICE_INVOICE_ID,ID.INVOICE_NUM,ID.INVOICE_DIST_ID\n" +
                    "FROM CLW_INVOICE_DIST ID ," + pTable + "_TEMP IT " +
                    "WHERE ID.INVOICE_DIST_ID=IT.INVOICE_DIST_ID) II " +
                    "WHERE IDD.INVOICE_DIST_ID=II.INVOICE_DIST_ID) I LEFT JOIN CLW_ORDER_PROPERTY OP ON I.INVOICE_DIST_DETAIL_ID = OP.INVOICE_DIST_DETAIL_ID ";
            executeUpdate(conn, sql);
        }

        {
            sql = " DROP TABLE  " + pTable + "_TEMP PURGE";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE TABLE " + pTable + "_TEMP AS SELECT I1.INVOICE_DIST_ID,I1.INVOICE_INVOICE_ID FROM (\n" +
                    "SELECT T1.INVOICE_INVOICE_ID,T1.INVOICE_NUM,T2.INVOICE_DIST_ID,COUNT(T1.INVOICE_NUM) AS COUNT  FROM   " + pTable + "_TEMP1 T1, " + pTable + "_TEMP2 T2 \n" +
                    "   WHERE T1.INVOICE_INVOICE_ID=T2.INVOICE_INVOICE_ID \n" +
                    "    AND T1.SHORT_DESC = T2.SHORT_DESC \n" +
                    "    AND NVL(T1.VALUE,  ' ')=NVL(T2.VALUE, ' ') \n" +
                    "    AND T1.DIST_ITEM_SKU_NUM  = T2.DIST_ITEM_SKU_NUM \n" +
                    "    AND T1.ADJUSTED_COST  = T2.ADJUSTED_COST \n" +
                    "    AND T1.DIST_INTO_STOCK_COST  = T2.DIST_INTO_STOCK_COST \n" +
                    "    AND T1.ITEM_RECEIVED_COST= T2.ITEM_RECEIVED_COST\n" +
                    "    AND T1.DIST_ITEM_QTY_RECEIVED= T2.DIST_ITEM_QTY_RECEIVED \n" +
                    "    AND  NVL(T1.DIST_ITEM_UOM,' ')=NVL(T2.DIST_ITEM_UOM,' ')" +
                    "    AND T1.DIST_LINE_NUMBER = T2.DIST_LINE_NUMBER GROUP BY T1.INVOICE_INVOICE_ID,T2.INVOICE_DIST_ID,T1.INVOICE_NUM) I1,\n" +
                    "(SELECT T1.INVOICE_INVOICE_ID,T1.INVOICE_NUM,COUNT(T1.INVOICE_NUM) AS COUNT FROM " + pTable + "_TEMP1 T1   GROUP BY T1.INVOICE_INVOICE_ID,T1.INVOICE_NUM) I2,\n" +
                    "(SELECT T2.INVOICE_DIST_ID,T2.INVOICE_NUM,COUNT(T2.INVOICE_NUM) AS COUNT FROM " + pTable + "_TEMP2 T2  GROUP BY T2.INVOICE_DIST_ID,T2.INVOICE_NUM) I3\n" +
                    "WHERE I1.INVOICE_INVOICE_ID=I2.INVOICE_INVOICE_ID AND I1.INVOICE_NUM=I2.INVOICE_NUM AND I1.COUNT=I2.COUNT AND I1.INVOICE_DIST_ID=I3.INVOICE_DIST_ID\n" +
                    "AND I2.INVOICE_NUM =I3.INVOICE_NUM AND I2.COUNT=I3.COUNT";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX22 ON " + pTable + "_TEMP(INVOICE_INVOICE_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = " UPDATE " + pTable + "_INV II SET (INV_ACTION_CD,INVOICE_DIST_ID)=(SELECT NULL,INVOICE_DIST_ID FROM " + pTable + "_TEMP T WHERE T.INVOICE_INVOICE_ID=II.INVOICE_INVOICE_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = " DROP TABLE  " + pTable + "_TEMP PURGE";
            executeUpdate(conn, sql);
        }

        {
            sql = " DROP TABLE  " + pTable + "_TEMP1 PURGE";
            executeUpdate(conn, sql);
        }

        {
            sql = " DROP TABLE  " + pTable + "_TEMP2 PURGE";
            executeUpdate(conn, sql);
        }

    }

    private void prepare(Connection conn, int pStoreId, int pDistributorId, String pTable) throws SQLException {

        dropWorkTables(conn, pTable);

        invoiceCreate(conn, pTable);
        invoiceLoad(conn, pTable);
        defineAccount(conn, pTable, pStoreId);
        defineSite(conn, pTable);

        invoicePropertyCreate(conn, pTable);
        invoicePropertyLoad(conn, pTable);

        invoiceItemCreate(conn, pTable);
        invoiceItemLoad(conn, pTable);
        setDistLineNumber(conn, pTable);
        defineItem(conn, pTable, pDistributorId);

        invoiceItemPropertyCreate(conn, pTable);
        invoiceItemPropertyLoad(conn, pTable);
    }

    private void invoiceItemPropertyLoad(Connection conn, String pTable) throws SQLException {
        {
            String sql = "INSERT INTO " + pTable + "_INV_ITEM_PROPERTY(INVOICE_INVOICE_ID,INVOICE_INVOICE_ITEM_ID,INVOICE_NUM ,SHORT_DESC ,VALUE) (SELECT II.INVOICE_INVOICE_ID,II.INVOICE_INVOICE_ITEM_ID,II.INVOICE_NUM,'GP',II.GP FROM " + pTable + "_INV_ITEM II)";
            executeUpdate(conn, sql);
        }
    }

    private void invoiceItemPropertyCreate(Connection conn, String pTable) throws SQLException {
        {
            String sql = "CREATE TABLE " + pTable + "_INV_ITEM_PROPERTY (\n" +
                    "INVOICE_INVOICE_ID  NUMBER(38),\n" +
                    "INVOICE_INVOICE_ITEM_ID  NUMBER(38),\n" +
                    "INVOICE_DIST_DETAIL_ID NUMBER(38),\n" +
                    "INVOICE_DIST_ID NUMBER(38),\n" +
                    "INVOICE_NUM  VARCHAR2(255),\n" +
                    "SHORT_DESC VARCHAR2(255),\n" +
                    "VALUE VARCHAR2(255),\n" +
                    "INV_ITEM_PROPERTY_ACTION_CD CHAR(2)\n" +
                    ")";
            executeUpdate(conn, sql);
        }
    }

    private void setDistLineNumber(Connection conn, String pTable) throws SQLException {

        {
            String sql = "CREATE INDEX " + pTable + "_INDEX6 ON " + pTable + "_INV_ITEM(INVOICE_NUM)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "CREATE INDEX " + pTable + "_INDEX7 ON " + pTable + "_INV_ITEM(INVOICE_INVOICE_ITEM_ID)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "CREATE INDEX " + pTable + "_INDEX8 ON " + pTable + "_INV_ITEM(INVOICE_INVOICE_ID)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "UPDATE " + pTable + "_INV_ITEM I1 SET (DIST_LINE_NUMBER) =  (SELECT MAX(I2.INVOICE_INVOICE_ITEM_ID) FROM " + pTable + "_INV_ITEM I2 WHERE I2.INVOICE_INVOICE_ID = I1.INVOICE_INVOICE_ID AND  DIST_LINE_NUMBER IS NULL GROUP BY INVOICE_INVOICE_ID)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "UPDATE " + pTable + "_INV_ITEM I1 SET (DIST_LINE_NUMBER) =  (SELECT I2.DIST_LINE_NUMBER-I2.INVOICE_INVOICE_ITEM_ID+1 FROM " + pTable + "_INV_ITEM I2 WHERE I2.INVOICE_INVOICE_ITEM_ID = I1.INVOICE_INVOICE_ITEM_ID)";
            executeUpdate(conn, sql);
        }

    }

    private void defineItem(Connection conn, String pTable, int pDistributorId) throws SQLException {
        {
            String sql = "CREATE  TABLE " + pTable + "_TEMP AS SELECT IM.ITEM_ID,IM.ITEM_NUM,IM.SHORT_DESC FROM CLW_ITEM_MAPPING IM WHERE IM.BUS_ENTITY_ID =  " + pDistributorId;
            executeUpdate(conn, sql);

        }

        {
            String sql = "CREATE INDEX " + pTable + "_INDEX9 ON " + pTable + "_INV_ITEM(DIST_ITEM_SKU_NUM)";
            executeUpdate(conn, sql);

        }

        {
            String sql = "CREATE INDEX " + pTable + "_INDEX10 ON " + pTable + "_TEMP(ITEM_NUM)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "UPDATE " + pTable + "_INV_ITEM I SET (ITEM_ID,DIST_ITEM_SHORT_DESC) = (SELECT T.ITEM_ID,T.SHORT_DESC FROM  " + pTable + "_TEMP T  WHERE T.ITEM_NUM = I.DIST_ITEM_SKU_NUM)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "DROP TABLE " + pTable + "_TEMP PURGE";
            executeUpdate(conn, sql);
        }

    }

    private void defineSite(Connection conn, String pTable) throws SQLException {
        {
            String sql = "CREATE TABLE " + pTable + "_TEMP AS SELECT DISTINCT S.BUS_ENTITY2_ID AS ACCOUNT_ID,S.BUS_ENTITY1_ID AS SITE_ID,S.CLW_VALUE AS SHIP_TO," +
                    "SHORT_DESC AS SITE_NAME ,A.ADDRESS1,A.ADDRESS2,A.ADDRESS3,A.ADDRESS4,A.CITY,A.STATE_PROVINCE_CD,A.POSTAL_CODE,A.COUNTRY_CD " +
                    "FROM (SELECT  P.CLW_VALUE,BEA.BUS_ENTITY1_ID,BEA.BUS_ENTITY2_ID,BE.SHORT_DESC\n" +
                    " FROM CLW_PROPERTY P,CLW_BUS_ENTITY_ASSOC BEA,CLW_BUS_ENTITY BE\n" +
                    "  WHERE P.BUS_ENTITY_ID = BE.BUS_ENTITY_ID\n" +
                    "    AND BE.BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "'\n" +
                    "    AND BE.BUS_ENTITY_ID = BEA.BUS_ENTITY1_ID\n" +
                    "    AND BEA.BUS_ENTITY2_ID  IN (SELECT DISTINCT ACCOUNT_ID FROM " + pTable + "_INV WHERE ACCOUNT_ID>0)\n" +
                    "    AND BEA.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "'\n" +
                    "    AND P.SHORT_DESC ='" + RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER + "') S LEFT JOIN CLW_ADDRESS A ON S.BUS_ENTITY1_ID=A.BUS_ENTITY_ID WHERE A .ADDRESS_TYPE_CD='" + RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "'";
            executeUpdate(conn, sql);
        }

        {
            String sql = "CREATE INDEX " + pTable + "_INDEX4 ON " + pTable + "_TEMP(SHIP_TO)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "CREATE INDEX " + pTable + "_INDEX5 ON " + pTable + "_TEMP(ACCOUNT_ID)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "UPDATE " + pTable + "_INV I SET (SITE_ID,SHIP_TO_NAME,SHIP_TO_ADDRESS1,SHIP_TO_ADDRESS2,SHIP_TO_ADDRESS3,SHIP_TO_ADDRESS4," +
                    "SHIP_TO_CITY,SHIP_TO_STATE,SHIP_TO_POSTAL_CODE,SHIP_TO_COUNTRY) = (SELECT T.SITE_ID,T.SITE_NAME,T.ADDRESS1,T.ADDRESS2,T.ADDRESS3,T.ADDRESS4," +
                    "T.CITY,T.STATE_PROVINCE_CD,POSTAL_CODE,T.COUNTRY_CD FROM " + pTable + "_TEMP T  WHERE T.SHIP_TO=I.SHIP_TO AND T.ACCOUNT_ID = I.ACCOUNT_ID)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "DROP TABLE " + pTable + "_TEMP PURGE";
            executeUpdate(conn, sql);
        }


    }

    private void defineAccount(Connection conn, String pTable, int pStoreId) throws SQLException {
        {
            String sql = "CREATE INDEX " + pTable + "_INDEX1 ON " + pTable + "_INV(BILL_TO)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "CREATE INDEX " + pTable + "_INDEX2 ON " + pTable + "_INV(INVOICE_INVOICE_ID)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "CREATE TABLE " + pTable + "_TEMP AS SELECT  P.CLW_VALUE AS DIST_REF_NUM ,P.BUS_ENTITY_ID AS ACCOUNT_ID FROM CLW_PROPERTY P,CLW_BUS_ENTITY_ASSOC BEA \n" +
                    " WHERE P.BUS_ENTITY_ID = BEA.BUS_ENTITY1_ID\n" +
                    "  AND BEA.BUS_ENTITY2_ID = " + pStoreId + "\n" +
                    "  AND P.SHORT_DESC ='" + RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM + "'\n" +
                    "  AND BEA.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE + "'";
            executeUpdate(conn, sql);
        }

        {
            String sql = "CREATE INDEX " + pTable + "_INDEX3 ON " + pTable + "_TEMP(DIST_REF_NUM)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "UPDATE " + pTable + "_INV I SET ACCOUNT_ID = (SELECT T.ACCOUNT_ID FROM " + pTable + "_TEMP T WHERE T.DIST_REF_NUM=I.BILL_TO)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "DROP TABLE " + pTable + "_TEMP PURGE";
            executeUpdate(conn, sql);
        }

    }

    private void invoiceItemLoad(Connection conn, String pTable) throws SQLException {
        {
            String sql = "INSERT INTO " + pTable + "_INV_ITEM (INVOICE_INVOICE_ITEM_ID,INVOICE_INVOICE_ID,INVOICE_NUM,DIST_ITEM_SKU_NUM,DIST_ITEM_UOM,DIST_ITEM_QTY_RECEIVED,ITEM_RECEIVED_COST,ADJUSTED_COST,DIST_INTO_STOCK_COST,GP)\n" +
                    "SELECT ROWNUM,T.* FROM (SELECT II.INVOICE_INVOICE_ID,II.INVOICE_NUM,I.ITEM_NUM,UOM,ROUND(TO_NUMBER(I.QTY),0) AS DIST_ITEM_QTY_RECEIVED,DECODE(NVL(ROUND(TO_NUMBER(I.QTY),0),0),0,0,NVL(ROUND(TO_NUMBER(I.PRICE),2),0)/ROUND(TO_NUMBER(I.QTY),0)) AS ITEM_RECEIVED_COST,ROUND(TO_NUMBER(I.PRICE),2) AS ADJUSTED_COST,ROUND(TO_NUMBER(I.COST),2) AS DIST_INTO_STOCK_COST,I.GP FROM \n" +
                    pTable + "_INV II," + pTable + " I WHERE \n" +
                    " II.BILL_TO=I.BILL_TO \n" +
                    " AND  II.SHIP_TO=I.SHIP_TO\n" +
                    "  AND  II.INVOICE_NUM=I.INVOICE_NUM \n" +
                    "  AND II. INVOICE_DATE=TO_DATE(I.SHIP_DATE,'" + date_format + "')\n" +
                    "  AND II.ACCOUNT_ID>0\n" +
                    "  AND II.SITE_ID>0  ORDER BY II.INVOICE_INVOICE_ID,II.INVOICE_NUM,I.ITEM_NUM,I.UOM,I.QTY,I.PRICE,I.COST,I.GP) T";
            executeUpdate(conn, sql);
        }

    }

    private void invoiceItemCreate(Connection conn, String pTable) throws SQLException {
        {
            String sql = "CREATE TABLE " + pTable + "_INV_ITEM (\n" +
                    "INVOICE_DIST_ID  NUMBER(38),\n" +
                    "INVOICE_INVOICE_ID  NUMBER(38),\n" +
                    "INVOICE_DIST_DETAIL_ID NUMBER(38),\n" +
                    "INVOICE_INVOICE_ITEM_ID NUMBER(38),\n" +
                    "INVOICE_ITEM_DIST_ID  NUMBER(38),\n" +
                    "INVOICE_NUM VARCHAR2(255),\n" +
                    "ITEM_ID NUMBER(38),\n" +
                    "DIST_ITEM_SHORT_DESC VARCHAR2(255),\n" +
                    "DIST_ITEM_SKU_NUM VARCHAR2(30),\n" +
                    "ITEM_SKU_NUM VARCHAR2(255),\n" +
                    "DIST_ITEM_UOM VARCHAR2(30),\n" +
                    "DIST_ITEM_QTY_RECEIVED  NUMBER(32),\n" +
                    "ITEM_RECEIVED_COST NUMBER(13,2),\n" +
                    "DIST_INTO_STOCK_COST  NUMBER(15,3),\n" +
                    "ADJUSTED_COST NUMBER(13,3),\n" +
                    "GP VARCHAR2(255),\n" +
                    "DIST_LINE_NUMBER NUMBER(38),\n" +
                    "INV_ITEM_ACTION_CD CHAR(2)\n" +
                    ")";
            executeUpdate(conn, sql);
        }
    }

    private void invoicePropertyLoad(Connection conn, String pTable) throws SQLException {

        {
            String sql = "INSERT INTO " + pTable + "_INV_PROPERTY  (INVOICE_INVOICE_ID,INVOICE_NUM, SHORT_DESC ,VALUE ) " +
                    "(SELECT II.INVOICE_INVOICE_ID,II.INVOICE_NUM,'" + RefCodeNames.ORDER_PROPERTY_TYPE_CD.BRANCH + "',II.BRANCH FROM " + pTable + "_INV II)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "INSERT INTO " + pTable + "_INV_PROPERTY  (INVOICE_INVOICE_ID,INVOICE_NUM, SHORT_DESC ,VALUE ) " +
                    "(SELECT II.INVOICE_INVOICE_ID,II.INVOICE_NUM,'" + RefCodeNames.ORDER_PROPERTY_TYPE_CD.REP_NAME + "',II.REP_NAME FROM " + pTable + "_INV II)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "INSERT INTO " + pTable + "_INV_PROPERTY  (INVOICE_INVOICE_ID,INVOICE_NUM, SHORT_DESC ,VALUE ) " +
                    "(SELECT II.INVOICE_INVOICE_ID,II.INVOICE_NUM,'" + RefCodeNames.ORDER_PROPERTY_TYPE_CD.REP_NUM + "',II.REP_NUM FROM " + pTable + "_INV II)";
            executeUpdate(conn, sql);
        }

        {
            String sql = "INSERT INTO " + pTable + "_INV_PROPERTY  (INVOICE_INVOICE_ID,INVOICE_NUM, SHORT_DESC ,VALUE ) " +
                    "(SELECT II.INVOICE_INVOICE_ID,II.INVOICE_NUM,'" + RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_PO_NUM + "',II.CUSTOMER_PO FROM " + pTable + "_INV II)";
            executeUpdate(conn, sql);
        }

    }

    private void invoicePropertyCreate(Connection conn, String pTable) throws SQLException {
        {
            String sql = "CREATE TABLE " + pTable + "_INV_PROPERTY (\n" +
                    "INVOICE_INVOICE_ID  NUMBER(38),\n" +
                    "INVOICE_DIST_ID NUMBER(38),\n" +
                    "INVOICE_NUM  VARCHAR2(255),\n" +
                    "SHORT_DESC VARCHAR2(255),\n" +
                    "VALUE VARCHAR2(255),\n" +
                    "INV_PROPERTY_ACTION_CD CHAR(2)\n" +
                    ")";
            executeUpdate(conn, sql);
        }
    }

    private void invoiceLoad(Connection conn, String pTable) throws SQLException {
        {
            String sql = "INSERT INTO " + pTable + "_INV (BILL_TO,SHIP_TO,INVOICE_NUM,INVOICE_DATE,SUB_TOTAL,BRANCH,REP_NUM,REP_NAME,CUSTOMER_PO)\n" +
                    " (SELECT BILL_TO,SHIP_TO,INVOICE_NUM,TO_DATE(SHIP_DATE,'" + date_format + "'),SUM(ROUND(TO_NUMBER (PRICE),2)),BRANCH,REP_NUM,REP_NAME,CUSTOMER_PO \n" +
                    "  FROM " + pTable + " \n" +
                    "  GROUP BY BILL_TO,SHIP_TO,INVOICE_NUM,SHIP_DATE,BRANCH,REP_NUM,REP_NAME,CUSTOMER_PO) ";
            executeUpdate(conn, sql);

        }

        {
            String sql = "UPDATE  " + pTable + "_INV  SET INVOICE_INVOICE_ID=ROWNUM";
            executeUpdate(conn, sql);
        }
    }

    private void invoiceCreate(Connection conn, String pTable) throws SQLException {

        String sql = "CREATE TABLE " + pTable + "_INV (\n" +
                "INVOICE_INVOICE_ID  NUMBER(38),\n" +
                "ACCOUNT_ID  NUMBER(38),\n" +
                "SITE_ID NUMBER(38),\n" +
                "INVOICE_DIST_ID  NUMBER(38),\n" +
                "INVOICE_NUM VARCHAR2(255),\n" +
                "INVOICE_DATE DATE,\n" +
                "BILL_TO VARCHAR2(255),\n" +
                "SHIP_TO VARCHAR2(255),\n" +
                "SHIP_TO_NAME VARCHAR2(255),\n" +
                "SHIP_TO_ADDRESS1 VARCHAR2(255),\n" +
                "SHIP_TO_ADDRESS2 VARCHAR2(255),\n" +
                "SHIP_TO_ADDRESS3 VARCHAR2(255),\n" +
                "SHIP_TO_ADDRESS4 VARCHAR2(255),\n" +
                "SHIP_TO_CITY VARCHAR2(255),\n" +
                "SHIP_TO_STATE  VARCHAR2(255),\n" +
                "SHIP_TO_POSTAL_CODE VARCHAR2(255),\n" +
                "SHIP_TO_COUNTRY  VARCHAR2(255),\n" +
                "SUB_TOTAL NUMBER(15,3),\n" +
                "BRANCH VARCHAR2(255),\n" +
                "REP_NUM VARCHAR2(255),\n" +
                "REP_NAME VARCHAR2(255),\n" +
                "CUSTOMER_PO VARCHAR2(255),\n" +
                "INV_ACTION_CD CHAR(2)\n" +
                ")";

        executeUpdate(conn, sql);

    }

    private void dropWorkTables(Connection conn, String pTable) throws SQLException {

        if (existTable(conn, pTable + "_INV")) {
            dropTable(conn, pTable + "_INV");
        }

        if (existTable(conn, pTable + "_INV_ITEM")) {
            dropTable(conn, pTable + "_INV_ITEM");
        }

        if (existTable(conn, pTable + "_INV_ITEM_PROPERTY")) {
            dropTable(conn, pTable + "_INV_ITEM_PROPERTY");
        }

        if (existTable(conn, pTable + "_INV_PROPERTY")) {
            dropTable(conn, pTable + "_INV_PROPERTY");
        }

        if (existTable(conn, pTable + "_TEMP")) {
            dropTable(conn, pTable + "_TEMP");
        }

        if (existTable(conn, pTable + "_TEMP1")) {
            dropTable(conn, pTable + "_TEMP1");
        }

        if (existTable(conn, pTable + "_TEMP2")) {
            dropTable(conn, pTable + "_TEMP2");
        }
    }

    private void executeUpdate(Connection conn, String sql) throws SQLException {
        Statement stmt;
        logInfo("executeUpdate = > SQL:" + sql);
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
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
                "  INVOICE_NUM  VARCHAR2(255)  NULL,\n" +
                "  SHIP_DATE VARCHAR2(255) NULL,\n" +
                "  BRANCH VARCHAR2(255) NULL,\n" +
                "  ITEM_NUM VARCHAR2(255) NULL,\n" +
                "  UOM VARCHAR2(255) NULL,\n" +
                "  QTY VARCHAR2(255) NULL,\n" +
                "  GP VARCHAR2(255)  NULL,\n" +
                "  PRICE VARCHAR2(255) NULL,\n" +
                "  COST VARCHAR2(255) NULL,\n" +
                "  REP_NUM VARCHAR2(255) NULL,\n" +
                "  REP_NAME VARCHAR2(255) NULL,\n" +
                "  BILL_TO VARCHAR2(255) NULL,\n" +
                "  CUSTOMER_PO VARCHAR2(255) NULL,\n" +
                "  SHIP_TO VARCHAR2(255) NULL,\n" +
                "  ADD_DATE DATE NOT NULL,\n" +
                "  ADD_BY   VARCHAR2 (255),\n" +
                "  MOD_DATE DATE NOT NULL,\n" +
                "  MOD_BY   VARCHAR2 (255)\n" +
                ")";


        executeUpdate(conn, sql);

    }

    private void tempDrop(Connection conn, String pTable) throws SQLException {

        if (existTable(conn, pTable + "_TEMP")) {
            dropTable(conn, pTable + "_TEMP");
        }

        if (existTable(conn, pTable + "_TEMP1")) {
            dropTable(conn, pTable + "_TEMP");
        }

        if (existTable(conn, pTable + "_TEMP2")) {
            dropTable(conn, pTable + "_TEMP");
        }
    }

    private void dropTable(Connection conn, String pTableName) throws SQLException {
        String sql = "DROP TABLE " + pTableName + " PURGE";
        logInfo("dropTable = > sql:" + sql);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    private boolean existTable(Connection conn, String tableName) throws SQLException {

        String sql = "SELECT TABLE_NAME FROM USER_TABLES WHERE TABLE_NAME = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, tableName.toUpperCase());

        ResultSet rs = pstmt.executeQuery();

        return rs.next();
    }

}

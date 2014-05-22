package com.cleanwise.service.api.session;

import com.cleanwise.service.api.dao.JanPakLoaderAgent;
import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

import javax.ejb.CreateException;
import java.rmi.RemoteException;
import java.sql.*;

public class JanPakItemLoaderBean extends UtilityServicesAPI {

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


    public void prepare(String pTable, String pFilterTable, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            prepare(conn, pTable, pFilterTable, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public void match(int pStoreId, int pDistributorId, int pStoreCatalogId, String pTable, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            match(conn, pStoreId, pDistributorId, pStoreCatalogId, pTable, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public void update(int pDistributorId, int pStoreCatalogId, String pTable, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            update(conn, pDistributorId, pStoreCatalogId, pTable, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public void insert(int pStoreId, int pStoreCatalogId, int pDistributorId, String pTable, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            insert(conn, pStoreId, pDistributorId, pStoreCatalogId, pTable, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public void delete(int pStoreCatalogId, String pTable, String pUser) throws RemoteException {
        Connection conn = null;
        try {

            conn = getConnection();
            delete(conn, pStoreCatalogId, pTable, pUser);
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

    private void dropWorkTables(Connection conn, String pTable) throws SQLException {
        itemDrop(conn, pTable);
        categDrop(conn, pTable);
        manufDrop(conn, pTable);
        tempDrop(conn, pTable);
    }


    private void delete(Connection conn, int pStoreCatalogId, String pTable, String pUser) throws SQLException {
        String sql;

    /*    {
            sql = "UPDATE CLW_ITEM I1 SET ITEM_STATUS_CD = '" + RefCodeNames.ITEM_STATUS_CD.INACTIVE + "'," +
                    " MOD_BY='" + pUser + "'," +
                    " MOD_DATE=SYSDATE" +
                    " WHERE I1.ITEM_ID IN (SELECT ITEM1_ID FROM (SELECT CI.ITEM_ID AS ITEM1_ID,I.ITEM_ID AS ITEM2_ID FROM" +
                    " (SELECT ITEM_ID from CLW_CATALOG_STRUCTURE WHERE CATALOG_ID = " + pStoreCatalogId + " AND CATALOG_STRUCTURE_CD='" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT + "') CI LEFT JOIN " + pTable + "_ITEM I\n" +
                    " ON I.ITEM_ID = CI.ITEM_ID) WHERE ITEM2_ID IS NULL)";
            executeUpdate(conn, sql);
        }*/
    }


    private void insert(Connection conn, int pStoreId, int pStoreCatalogId, int pDistributorId, String pTable, String pUser) throws SQLException {
        manufInsert(conn, pTable, pUser, pStoreId);
        categInsert(conn, pTable, pUser, pStoreCatalogId);
        itemInsert(conn, pTable, pUser, pStoreCatalogId, pDistributorId);
    }

    private void itemInsert(Connection conn, String pTable, String pUser, int pStoreCatalogId, int pDistributorId) throws SQLException {
        String sql;

        {
            sql = "INSERT /*APPEND*/ INTO CLW_ITEM(ITEM_ID,SHORT_DESC,SKU_NUM,LONG_DESC,EFF_DATE,EXP_DATE,ITEM_TYPE_CD,ITEM_STATUS_CD,ITEM_ORDER_NUM,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE)" +
                    " SELECT ITEM_ID,ITEM_DESC,(ITEM_ID+10000),ITEM_LONG_DESC,NULL,NULL,'" + RefCodeNames.ITEM_TYPE_CD.PRODUCT + "','" + RefCodeNames.ITEM_STATUS_CD.ACTIVE + "',NULL,'" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_ITEM" +
                    " WHERE ITEM_ID>0  AND ITEM_ACTION_CD='C'";
            executeUpdate(conn, sql);

        }

        {
            sql = "INSERT /*APPEND*/ INTO CLW_ITEM_ASSOC(ITEM_ASSOC_ID,ITEM1_ID,ITEM2_ID,CATALOG_ID,ITEM_ASSOC_CD,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE)" +
                    " SELECT CLW_ITEM_ASSOC_SEQ.NEXTVAL,ITEM_ID,CATEGORY_ID," + pStoreCatalogId + ",'" + RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY + "','" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_ITEM " +
                    "WHERE ITEM_ID>0 AND CATEGORY_ID>0 AND ITEM_CATEGORY_ACTION_CD='C'";
            executeUpdate(conn, sql);

        }

        {
            sql = "INSERT /*APPEND*/ INTO CLW_CATALOG_STRUCTURE(CATALOG_STRUCTURE_ID,CATALOG_ID,CATALOG_STRUCTURE_CD,ITEM_ID,CUSTOMER_SKU_NUM,SHORT_DESC,EXP_DATE,EFF_DATE,STATUS_CD,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE,COST_CENTER_ID,TAX_EXEMPT,ITEM_GROUP_ID) \n" +
                    "SELECT CLW_CATALOG_STRUCTURE_SEQ.NEXTVAL," + pStoreCatalogId + ",'" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT + "',ITEM_ID,(ITEM_ID+10000),NULL,NULL,NULL,'" + RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE + "','" + pUser + "','" + pUser + "',SYSDATE,SYSDATE,NULL,NULL,NULL FROM " + pTable + "_ITEM " +
                    "WHERE ITEM_ID>0  AND ITEM_CATALOG_ACTION_CD='C'";
            executeUpdate(conn, sql);

        }

        {
            sql = "INSERT /*APPEND*/ INTO CLW_ITEM_META(ITEM_META_ID,ITEM_ID,VALUE_ID,NAME_VALUE,CLW_VALUE,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE)" +
                    " SELECT CLW_ITEM_META_SEQ.NEXTVAL,ITEM_ID,0,'"+JanPakLoaderAgent.UOM+"',UOM,'" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_ITEM " +
                    "WHERE ITEM_ID>0  AND ITEM_META_UOM_ACTION_CD='C'";
            executeUpdate(conn, sql);

        }

        {
            sql = "INSERT /*APPEND*/ INTO CLW_ITEM_META(ITEM_META_ID,ITEM_ID,VALUE_ID,NAME_VALUE,CLW_VALUE,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE)" +
                    " SELECT CLW_ITEM_META_SEQ.NEXTVAL,ITEM_ID,0,'"+JanPakLoaderAgent.PACK+"',PACK,'" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_ITEM " +
                    "WHERE ITEM_ID>0  AND ITEM_META_PACK_ACTION_CD='C'";
            executeUpdate(conn, sql);

        }

        {
            sql = "INSERT /*APPEND*/ INTO CLW_ITEM_MAPPING(ITEM_MAPPING_ID,ITEM_ID,BUS_ENTITY_ID,ITEM_NUM,ITEM_UOM,ITEM_PACK,SHORT_DESC,LONG_DESC,ITEM_MAPPING_CD,EFF_DATE,EXP_DATE,STATUS_CD,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE,UOM_CONV_MULTIPLIER,STANDARD_PRODUCT_LIST) \n" +
                    "SELECT CLW_ITEM_MAPPING_SEQ.NEXTVAL,ITEM_ID," + pDistributorId + ",ITEM_NUM,UOM,PACK,NULL,NULL,'" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "',NULL,NULL,'" + RefCodeNames.SIMPLE_STATUS_CD.ACTIVE + "','" + pUser + "','" + pUser + "',SYSDATE,SYSDATE,NULL,NULL " +
                    "FROM " + pTable + "_ITEM WHERE ITEM_ID>0  " +
                    "AND ITEM_DISTRIBUTOR_ACTION_CD='C'";
            executeUpdate(conn, sql);

        }


        {
            sql = "INSERT /*APPEND*/ INTO CLW_ITEM_MAPPING(ITEM_MAPPING_ID,ITEM_ID,BUS_ENTITY_ID,ITEM_NUM,ITEM_UOM,ITEM_PACK,SHORT_DESC,LONG_DESC,ITEM_MAPPING_CD,EFF_DATE,EXP_DATE,STATUS_CD,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE,UOM_CONV_MULTIPLIER,STANDARD_PRODUCT_LIST) \n" +
                    "SELECT CLW_ITEM_MAPPING_SEQ.NEXTVAL,ITEM_ID,MANUFACTURER_ID,NVL(CATALOG_NUM,'"+JanPakLoaderAgent.DEFAULT_MFG_SKU_VALUE +"'),NULL,NULL,NULL,NULL,'" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "',NULL,NULL,'" + RefCodeNames.SIMPLE_STATUS_CD.ACTIVE + "','" + pUser + "','" + pUser + "',SYSDATE,SYSDATE,NULL,NULL" +
                    " FROM " + pTable + "_ITEM WHERE ITEM_ID>0 " +
                    "AND MANUFACTURER_ID>0" +
                    " AND ITEM_MANUFACTURER_ACTION_CD='C'";
            executeUpdate(conn, sql);

        }
    }

    private void manufInsert(Connection conn, String pTable, String pUser, int pStoreId) throws SQLException {
        String sql;

        {
            sql = "INSERT /*APPEND*/ INTO CLW_BUS_ENTITY (BUS_ENTITY_ID,SHORT_DESC,ADD_BY,MOD_BY,BUS_ENTITY_TYPE_CD," +
                    "BUS_ENTITY_STATUS_CD,WORKFLOW_ROLE_CD,LOCALE_CD,ADD_DATE,MOD_DATE) " +
                    "SELECT  MANUFACTURER_ID,MANUFACTURER,'" + pUser + "','" + pUser + "','" + RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER + "','" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "'," +
                    "'UNKNOWN','EN_US',SYSDATE,SYSDATE FROM " + pTable + "_MFG " +
                    " WHERE MANUFACTURER_ID>0 " +
                    " AND MANUFACTURER_ACTION_CD='C'";
            executeUpdate(conn, sql);

        }

        {
            sql = "INSERT /*APPEND*/ INTO CLW_BUS_ENTITY_ASSOC (BUS_ENTITY_ASSOC_ID,BUS_ENTITY1_ID," +
                    "BUS_ENTITY2_ID,BUS_ENTITY_ASSOC_CD,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE) " +
                    "SELECT CLW_BUS_ENTITY_ASSOC_SEQ.NEXTVAL,MANUFACTURER_ID," + pStoreId + "," +
                    "'" + RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE + "','" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_MFG " +
                    "WHERE MANUFACTURER_ID>0 AND MANUFACTURER_ACTION_CD='C'";
            executeUpdate(conn, sql);

        }
    }

    private void categInsert(Connection conn, String pTable, String pUser, int pStoreCatalogId) throws SQLException {
        String sql;
        {
            sql = "INSERT /*APPEND*/ INTO CLW_ITEM(ITEM_ID,SHORT_DESC,SKU_NUM,LONG_DESC,EFF_DATE,EXP_DATE,ITEM_TYPE_CD,ITEM_STATUS_CD," +
                    "ITEM_ORDER_NUM,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE) " +
                    "SELECT CATEGORY_ID,CATEGORY,0,CATEGORY,NULL,NULL,'" + RefCodeNames.ITEM_TYPE_CD.CATEGORY + "','" + RefCodeNames.ITEM_STATUS_CD.ACTIVE + "',NULL,'" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_CATEGORY " +
                    "WHERE CATEGORY_ID>0  AND CATEGORY_ACTION_CD='C'";
            executeUpdate(conn, sql);

        }

        {
            sql = "INSERT /*APPEND*/ INTO CLW_CATALOG_STRUCTURE(CATALOG_STRUCTURE_ID,CATALOG_ID,CATALOG_STRUCTURE_CD,ITEM_ID,CUSTOMER_SKU_NUM,SHORT_DESC,EXP_DATE,EFF_DATE,STATUS_CD,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE,COST_CENTER_ID,TAX_EXEMPT,ITEM_GROUP_ID) \n" +
                    "SELECT CLW_CATALOG_STRUCTURE_SEQ.NEXTVAL," + pStoreCatalogId + ",'" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "',CATEGORY_ID,NULL,NULL,NULL,NULL,'" + RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE + "','" + pUser + "','" + pUser + "',SYSDATE,SYSDATE,NULL,NULL,NULL FROM " + pTable + "_CATEGORY " +
                    "WHERE CATEGORY_ID>0  AND CATEGORY_CATALOG_ACTION_CD='C'";
            executeUpdate(conn, sql);

        }

    }

    private void update(Connection conn, int pDistributorId, int pStoreCatalogId, String pTable, String pUser) throws SQLException {

        manufUpdate(conn, pTable, pUser);
        categUpdate(conn, pTable, pUser);
        itemUpdate(conn, pDistributorId, pStoreCatalogId, pTable, pUser);
        enterpriseAttrUpdate(conn, pTable, pUser);

    }

    private void enterpriseAttrUpdate(Connection conn, String pTable, String pUser) throws SQLException {
        String sql;

        tempDrop(conn, pTable);

        {
            sql = "  CREATE TABLE " + pTable + "_TEMP AS \n" +
                    "  SELECT LI.ITEM_ID AS ITEM1_ID,EI.ITEM_ID AS ITEM2_ID FROM CLW_BUS_ENTITY_ASSOC BEA,(SELECT IM.BUS_ENTITY_ID ,IM.ITEM_NUM,IM.ITEM_ID,I.JD_UOM FROM CLW_ITEM_MAPPING IM," + pTable + "_ITEM I\n" +
                    "  WHERE IM.ITEM_ID=I.ITEM_ID \n" +
                    "  AND TRIM(I.CATALOG_NUM) IS NOT NULL \n" +
                    "  AND IM.BUS_ENTITY_ID = I.MANUFACTURER_ID\n" +
                    "  AND IM.ITEM_MAPPING_CD = '"+RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER +"') LI,   \n" +
                    "  (SELECT IM.BUS_ENTITY_ID,IM.ITEM_NUM,IM.ITEM_ID,IMETA.CLW_VALUE AS UOM FROM CLW_ITEM_MAPPING IM LEFT JOIN CLW_ITEM_META IMETA ON IM.ITEM_ID = IMETA.ITEM_ID,CLW_CATALOG_STRUCTURE CS\n" +
                    "  WHERE CS.CATALOG_ID = (SELECT C.CATALOG_ID FROM CLW_CATALOG_ASSOC CA, CLW_CATALOG C,CLW_PROPERTY P \n" +
                    " WHERE P.PROPERTY_TYPE_CD='" + RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE + "'\n" +
                    "  AND P.CLW_VALUE='" + RefCodeNames.STORE_TYPE_CD.ENTERPRISE + "' \n" +
                    "  AND  CA.BUS_ENTITY_ID = P.BUS_ENTITY_ID\n" +
                    "  AND CA.CATALOG_ASSOC_CD='" + RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE + "'\n" +
                    "  AND CA.CATALOG_ID = C.CATALOG_ID\n" +
                    "  AND C.CATALOG_TYPE_CD='" + RefCodeNames.CATALOG_TYPE_CD.STORE + "'\n" +
                    "  AND C.CATALOG_STATUS_CD='" + RefCodeNames.CATALOG_STATUS_CD.ACTIVE + "')\n" +
                    "  AND CS.CATALOG_STRUCTURE_CD='" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT + "'\t\n" +
                    "  AND IM.ITEM_ID = CS.ITEM_ID \n" +
                    "  AND IM.ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "'" +
                    "  AND IMETA.NAME_VALUE='"+JanPakLoaderAgent.UOM+"') EI  \n" +
                    "  WHERE EI.ITEM_NUM=LI.ITEM_NUM \n" +
                    "  AND NVL(EI.UOM,' ')=LI.JD_UOM"+
                    "  AND BEA.BUS_ENTITY1_ID=LI.BUS_ENTITY_ID\n" +
                    "  AND BEA.BUS_ENTITY2_ID=EI.BUS_ENTITY_ID\n" +
                    "  AND BEA.BUS_ENTITY_ASSOC_CD='"+RefCodeNames.BUS_ENTITY_ASSOC_CD.ENTERPRISE_MANUF_ASSOC+"'";

            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX15 ON " + pTable + "_TEMP(ITEM1_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX16 ON " + pTable + "_TEMP(ITEM2_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = " INSERT INTO CLW_ITEM_ASSOC (ITEM_ASSOC_ID,ITEM1_ID,ITEM2_ID,CATALOG_ID,ITEM_ASSOC_CD,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE) (SELECT CLW_ITEM_ASSOC_SEQ.NEXTVAL, IT.ITEM1_ID,IT.ITEM2_ID,NULL,'" + RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT + "','" + pUser + "','" + pUser+ "',SYSDATE,SYSDATE" +
                    " FROM (SELECT * FROM " + pTable + "_TEMP WHERE ITEM1_ID IN (SELECT ITEM1_ID FROM " + pTable + "_TEMP GROUP BY ITEM1_ID HAVING(COUNT(ITEM1_ID)=1))) IT\n" +
                    "  LEFT JOIN (SELECT ITEM_ASSOC_ID,ITEM1_ID,ITEM2_ID FROM CLW_ITEM_ASSOC\n" +
                    "   WHERE ITEM_ASSOC_CD='" + RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT + "' ) IA  ON  IA.ITEM1_ID=IT.ITEM1_ID AND IA.ITEM2_ID=IT.ITEM2_ID \n" +
                    "   WHERE IA.ITEM_ASSOC_ID IS NULL)";
            executeUpdate(conn, sql);
        }

        {
            sql = "DROP TABLE " + pTable + "_TEMP PURGE";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE TABLE  " + pTable + "_TEMP AS\n" +
                    " SELECT * FROM (SELECT IM.ITEM_META_ID AS ENTERPRISE_ITEM_META_ID,\n" +
                    " IA.ITEM1_ID AS ITEM1_ID,\n" +
                    " IM.ITEM_ID AS ENTERPRISE_ITEM1_ID,\n" +
                    "  IM.NAME_VALUE AS ENTERPRISE_NAME_VALUE,\n" +
                    "  IM.CLW_VALUE AS ENTERPRISE_CLW_VALUE FROM CLW_ITEM_META IM,\n" +
                    "  " + pTable + "_ITEM I,\n" +
                    "  CLW_ITEM_ASSOC IA\n" +
                    "WHERE \n" +
                    "  IM.ITEM_ID = IA.ITEM2_ID    AND\n" +
                    "  IA.ITEM1_ID = I.ITEM_ID     AND\n" +
                    "  IA.ITEM_ASSOC_CD='" + RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT + "' ) I1\n" +
                    "\n" +
                    "   FULL JOIN \n" +
                    "   \n" +
                    "( SELECT IA.ITEM2_ID AS ENTERPRISE_ITEM2_ID,IM.ITEM_META_ID AS ITEM_META_ID,\n" +
                    "  IM.ITEM_ID AS ITEM2_ID,\n" +
                    "  IM.NAME_VALUE AS NAME_VALUE,\n" +
                    "  IM.CLW_VALUE AS CLW_VALUE FROM CLW_ITEM_META IM,\n" +
                    "  " + pTable + "_ITEM I,\n" +
                    "  CLW_ITEM_ASSOC IA\n" +
                    "WHERE \n" +
                    "  IM.ITEM_ID = IA.ITEM1_ID    AND\n" +
                    "  IA.ITEM1_ID = I.ITEM_ID     AND\n" +
                    "  IA.ITEM_ASSOC_CD='" + RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT + "') I2 ON I2.NAME_VALUE = I1.ENTERPRISE_NAME_VALUE AND I2.ITEM2_ID = I1.ITEM1_ID AND I2.ENTERPRISE_ITEM2_ID=I1.ENTERPRISE_ITEM1_ID ";

            executeUpdate(conn, sql);

        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX12 ON " + pTable + "_TEMP(ITEM_META_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX13 ON " + pTable + "_TEMP(ENTERPRISE_ITEM_META_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = "  UPDATE CLW_ITEM_META I SET (CLW_VALUE) = (SELECT ENTERPRISE_CLW_VALUE  FROM " + pTable + "_TEMP T WHERE T.ITEM_META_ID =I.ITEM_META_ID),\n" +
                    "  MOD_BY='" + pUser + "',\n" +
                    "  MOD_DATE=SYSDATE\n" +
                    "  WHERE ITEM_META_ID IN(SELECT T.ITEM_META_ID  FROM " + pTable + "_TEMP T\n" +
                    "   WHERE T.ENTERPRISE_ITEM1_ID>0 \n" +
                    "    AND T.ITEM_META_ID>0\n" +
                    "    AND T.ENTERPRISE_NAME_VALUE = T.NAME_VALUE " +
                    "    AND T.ENTERPRISE_CLW_VALUE!=T.CLW_VALUE )";

            executeUpdate(conn, sql);

        }

        {
            sql = "INSERT INTO CLW_ITEM_META (ITEM_META_ID,ITEM_ID,VALUE_ID,NAME_VALUE, CLW_VALUE,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE) \n" +
                    "   (SELECT CLW_ITEM_META_SEQ.NEXTVAL,T.ITEM1_ID,0,T.ENTERPRISE_NAME_VALUE,T.ENTERPRISE_CLW_VALUE ,'" + pUser + "','" + pUser + "',SYSDATE,SYSDATE FROM " + pTable + "_TEMP T \n" +
                    "    WHERE T.ITEM_META_ID IS NULL \n" +
                    "     AND T.ITEM1_ID>0 \n" +
                    "     AND TRIM(T.ENTERPRISE_NAME_VALUE) IS NOT NULL)";

            executeUpdate(conn, sql);

        }

        {
            sql="  UPDATE CLW_ITEM_MAPPING IMAP SET (ITEM_UOM) = (SELECT T.ENTERPRISE_CLW_VALUE  FROM "+pTable+"_TEMP T WHERE T.ITEM1_ID = IMAP.ITEM_ID AND T.ENTERPRISE_ITEM_META_ID IS NOT NULL AND T.ENTERPRISE_NAME_VALUE = '"+JanPakLoaderAgent.UOM+"' AND IMAP.ITEM_MAPPING_CD='"+RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR+"'),\n"+
                    " MOD_BY='"+pUser+"',\n" +
                    " MOD_DATE=SYSDATE\n" +
                    " WHERE IMAP.ITEM_MAPPING_ID IN(SELECT IMAP_1.ITEM_MAPPING_ID  FROM "+pTable+"_TEMP T,CLW_ITEM_MAPPING IMAP_1\n" +
                    "  WHERE T.ENTERPRISE_ITEM_META_ID IS NOT NULL \n" +
                    "   AND T.ITEM1_ID=IMAP_1.ITEM_ID\n" +
                    "   AND IMAP_1.ITEM_MAPPING_CD='"+RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR+"'\n" +
                    "   AND T.ENTERPRISE_NAME_VALUE = '"+JanPakLoaderAgent.UOM+"'\n" +
                    "   AND IMAP_1.ITEM_UOM!=T.ENTERPRISE_CLW_VALUE)";
            executeUpdate(conn, sql);
        }

        {
            sql="  UPDATE CLW_ITEM_MAPPING IMAP SET (ITEM_PACK) = (SELECT T.ENTERPRISE_CLW_VALUE  FROM "+pTable+"_TEMP T WHERE T.ITEM1_ID = IMAP.ITEM_ID AND T.ENTERPRISE_ITEM_META_ID IS NOT NULL AND T.ENTERPRISE_NAME_VALUE = '"+JanPakLoaderAgent.PACK+"' AND IMAP.ITEM_MAPPING_CD='"+RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR+"'),\n" +
                    " MOD_BY='"+pUser+"',\n" +
                    " MOD_DATE=SYSDATE\n" +
                    " WHERE IMAP.ITEM_MAPPING_ID IN(SELECT IMAP_1.ITEM_MAPPING_ID  FROM "+pTable+"_TEMP T,CLW_ITEM_MAPPING IMAP_1\n" +
                    "  WHERE T.ENTERPRISE_ITEM_META_ID IS NOT NULL \n" +
                    "   AND T.ITEM1_ID=IMAP_1.ITEM_ID\n" +
                    "   AND IMAP_1.ITEM_MAPPING_CD='"+RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR+"'\n" +
                    "   AND T.ENTERPRISE_NAME_VALUE = '"+JanPakLoaderAgent.PACK+"'\n" +
                    "   AND IMAP_1.ITEM_PACK!=T.ENTERPRISE_CLW_VALUE)";
            executeUpdate(conn, sql);
        }

        {
            sql = "DROP TABLE " + pTable + "_TEMP PURGE";
            executeUpdate(conn, sql);
        }

        {

            sql = "CREATE TABLE  " + pTable + "_TEMP AS " +
                    "SELECT * FROM \n" +
                    " \n" +
                    "(SELECT IMAP.ITEM_MAPPING_ID AS ENTERPRISE_ITEM_MAPPING_ID, IA.ITEM1_ID AS ITEM1_ID,IMAP.ITEM_ID AS ENTERPRISE_ITEM1_ID,  IMAP.BUS_ENTITY_ID AS ENTERPRISE_CERT_COMPANY \n" +
                    " FROM CLW_ITEM_MAPPING IMAP,"+pTable+"_ITEM I,CLW_ITEM_ASSOC IA \n" +
                    "  WHERE IMAP.ITEM_ID = IA.ITEM2_ID   \n" +
                    "   AND IA.ITEM1_ID = I.ITEM_ID   \n" +
                    "   AND IA.ITEM_ASSOC_CD='" + RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT + "'\n" +
                    "   AND IMAP.ITEM_MAPPING_CD= '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY + "') I1\n" +
                    "\n" +
                    "FULL JOIN\n" +
                    "\n" +
                    "(SELECT IA.ITEM2_ID AS ENTERPRISE_ITEM2_ID,IMAP.ITEM_MAPPING_ID AS ITEM_MAPPING_ID,IMAP.ITEM_ID AS ITEM2_ID,IMAP.BUS_ENTITY_ID AS CERT_COMPANY \n" +
                    "  FROM CLW_ITEM_MAPPING IMAP,"+pTable+"_ITEM I,CLW_ITEM_ASSOC IA\n" +
                    " WHERE IMAP.ITEM_ID = IA.ITEM1_ID \n" +
                    "  AND IA.ITEM1_ID = I.ITEM_ID  \n" +
                    "  AND IA.ITEM_ASSOC_CD='" + RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT + "'\n" +
                    "  AND IMAP.ITEM_MAPPING_CD= '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY + "') I2 \n" +
                    "\n" +
                    " ON I2.ITEM2_ID = I1.ITEM1_ID AND I2.ENTERPRISE_ITEM2_ID=I1.ENTERPRISE_ITEM1_ID AND I2.CERT_COMPANY=I1.ENTERPRISE_CERT_COMPANY";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX21 ON " + pTable + "_TEMP(ITEM_MAPPING_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX22 ON " + pTable + "_TEMP(ENTERPRISE_ITEM_MAPPING_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql="DELETE FROM CLW_ITEM_MAPPING WHERE ITEM_MAPPING_ID IN (SELECT ITEM_MAPPING_ID FROM "+pTable+"_TEMP WHERE ENTERPRISE_ITEM_MAPPING_ID IS NULL)";
            executeUpdate(conn, sql);
        }

        {
            sql="INSERT INTO CLW_ITEM_MAPPING (ITEM_MAPPING_ID,ITEM_ID,BUS_ENTITY_ID,ITEM_NUM,ITEM_UOM,ITEM_PACK,SHORT_DESC,LONG_DESC,ITEM_MAPPING_CD,EFF_DATE,EXP_DATE,STATUS_CD,ADD_BY,MOD_BY,ADD_DATE,MOD_DATE,UOM_CONV_MULTIPLIER,STANDARD_PRODUCT_LIST) " +
                    "(SELECT CLW_ITEM_MAPPING_SEQ.NEXTVAL,T.ITEM1_ID,T.ENTERPRISE_CERT_COMPANY,NULL,NULL,NULL,NULL,NULL,'"+RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY+"',NULL,NULL,'"+RefCodeNames.SIMPLE_STATUS_CD.ACTIVE+"','"+pUser+"','"+pUser+"',SYSDATE,SYSDATE,NULL,NULL FROM "+pTable+"_TEMP T WHERE ITEM_MAPPING_ID IS NULL)";
            executeUpdate(conn, sql);
        }

        {
            sql = "DROP TABLE " + pTable + "_TEMP PURGE";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE TABLE  " + pTable + "_TEMP AS\n" +
                    "SELECT * FROM (SELECT I1.ITEM_ID AS ENTERPRISE_ITEM1_ID,\n" +
                    " IA.ITEM1_ID AS ITEM1_ID,\n" +
                    "  I1.SHORT_DESC AS ENTERPRISE_SHORT_DESC,\n" +
                    "  I1.LONG_DESC AS ENTERPRISE_LONG_DESC FROM CLW_ITEM I1,\n" +
                    "  " + pTable + "_ITEM I2,\n" +
                    "  CLW_ITEM_ASSOC IA\n" +
                    "WHERE \n" +
                    "  I1.ITEM_ID = IA.ITEM2_ID    AND\n" +
                    "  IA.ITEM1_ID = I2.ITEM_ID AND I1.ITEM_TYPE_CD='" + RefCodeNames.ITEM_TYPE_CD.PRODUCT + "'\n" +
                    "  AND IA.ITEM_ASSOC_CD='" + RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT + "') I1,\n" +
                    "(SELECT I1.ITEM_ID AS ITEM2_ID,\n" +
                    "  IA.ITEM2_ID AS ENTERPRISE_ITEM2_ID,\n" +
                    "  I1.SHORT_DESC AS SHORT_DESC,\n" +
                    "  I1.LONG_DESC AS LONG_DESC FROM CLW_ITEM I1,\n" +
                    "  " + pTable + "_ITEM I2,\n" +
                    "  CLW_ITEM_ASSOC IA\n" +
                    "WHERE \n" +
                    "  I1.ITEM_ID = IA.ITEM1_ID    AND\n" +
                    "  IA.ITEM1_ID = I2.ITEM_ID AND I1.ITEM_TYPE_CD='" + RefCodeNames.ITEM_TYPE_CD.PRODUCT + "' AND\n" +
                    "  IA.ITEM_ASSOC_CD='" + RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT + "') I2 WHERE  I1.ITEM1_ID = I2.ITEM2_ID  AND I1.ENTERPRISE_ITEM1_ID=I2.ENTERPRISE_ITEM2_ID AND (NVL(I1.ENTERPRISE_LONG_DESC,' ') !=NVL(I2.LONG_DESC,' ')  OR NVL(I1.ENTERPRISE_SHORT_DESC,' ') !=NVL(I2.SHORT_DESC,' '))";

            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX14 ON " + pTable + "_TEMP(ITEM1_ID)";
            executeUpdate(conn, sql);
        }

        {


            sql = " UPDATE CLW_ITEM I SET (SHORT_DESC,LONG_DESC) = (SELECT ENTERPRISE_SHORT_DESC,ENTERPRISE_LONG_DESC FROM " + pTable + "_TEMP T WHERE T.ITEM1_ID =  I.ITEM_ID),\n" +
                    "MOD_BY='" + pUser + "'," +
                    "MOD_DATE=SYSDATE" +
                    " WHERE I.ITEM_ID IN (SELECT T.ITEM1_ID FROM " + pTable + "_TEMP T)";

            executeUpdate(conn, sql);
        }

        {
            sql = "DROP TABLE " + pTable + "_TEMP PURGE";
            executeUpdate(conn, sql);
        }
    }

    private void categUpdate(Connection conn, String pTable, String pUser) throws SQLException {
        String sql;
 /*       {
            sql = "UPDATE CLW_ITEM I SET ITEM_STATUS_CD= '" + RefCodeNames.ITEM_STATUS_CD.ACTIVE + "',\n" +
                    "MOD_BY='" + pUser + "',\n" +
                    "MOD_DATE=SYSDATE\n" +
                    "WHERE I.ITEM_ID IN (SELECT C.CATEGORY_ID  FROM " + pTable + "_CATEGORY C WHERE C.CATEGORY_ACTION_CD = 'U')";
            executeUpdate(conn, sql);
        }*/
    }

    private void manufUpdate(Connection conn, String pTable, String pUser) throws SQLException {
        String sql;
/*        {
            sql = "UPDATE CLW_BUS_ENTITY BE SET BUS_ENTITY_STATUS_CD = '" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "'," +
                    "MOD_BY='" + pUser + "'," +
                    "MOD_DATE=SYSDATE" +
                    " WHERE BE.BUS_ENTITY_ID IN (SELECT MANUFACTURER_ID FROM " + pTable + "_MFG " + "WHERE  MANUFACTURER_ACTION_CD='U')";
            executeUpdate(conn, sql);

        }*/
    }

    private void itemUpdate(Connection conn, int pDistributorId, int pStoreCatalogId, String pTable, String pUser) throws SQLException {
        String sql;
        /*{
            sql = "UPDATE CLW_ITEM I SET (SHORT_DESC,LONG_DESC,ITEM_STATUS_CD) = (SELECT ITEM_DESC,ITEM_LONG_DESC,'" + RefCodeNames.ITEM_STATUS_CD.ACTIVE + "' \n" +
                    "FROM " + pTable + "_ITEM I1 WHERE I1.ITEM_ACTION_CD = 'U' AND I1.ITEM_ID = I.ITEM_ID),\n" +
                    "MOD_BY='" + pUser + "',\n" +
                    "MOD_DATE=SYSDATE\n" +
                    "WHERE I.ITEM_ID IN (SELECT I2.ITEM_ID  FROM " + pTable + "_ITEM I2 WHERE I2.ITEM_ACTION_CD = 'U' AND I2.ITEM_ID = I.ITEM_ID)";
            executeUpdate(conn, sql);

        }

        {

            sql = "UPDATE CLW_ITEM_MAPPING IM SET (BUS_ENTITY_ID,ITEM_NUM,SHORT_DESC,ITEM_UOM,ITEM_PACK,LONG_DESC,STATUS_CD) = \n" +
                    "(SELECT I.MANUFACTURER_ID,NVL(I.CATALOG_NUM,'"+JanPakLoaderAgent.DEFAULT_MFG_SKU_VALUE +"') ,NULL,NULL,NULL,NULL,'" + RefCodeNames.SIMPLE_STATUS_CD.ACTIVE + "'  FROM " + pTable + "_ITEM I WHERE I.ITEM_ID = IM.ITEM_ID),\n" +
                    "MOD_BY='" + pUser + "',\n" +
                    "MOD_DATE=SYSDATE\n" +
                    " WHERE IM.ITEM_MAPPING_ID IN (SELECT IM1.ITEM_MAPPING_ID FROM " + pTable + "_ITEM I,CLW_ITEM_MAPPING IM1 WHERE I.ITEM_ID = IM1.ITEM_ID  AND IM1.ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "' AND " +
                    " I.ITEM_MANUFACTURER_ACTION_CD='U')";
            executeUpdate(conn, sql);

        }

        {

            sql = " UPDATE CLW_ITEM_ASSOC IA2 SET (ITEM2_ID)= (SELECT CATEGORY_ID FROM " + pTable + "_ITEM I WHERE I.ITEM_ID = IA2.ITEM1_ID),\n" +
                    " MOD_BY='" + pUser + "',\n" +
                    "MOD_DATE=SYSDATE\n" +
                    "WHERE IA2.ITEM_ASSOC_ID IN (SELECT IA.ITEM_ASSOC_ID FROM " + pTable + "_ITEM I,CLW_ITEM_ASSOC IA " +
                    "WHERE I.ITEM_ID = IA.ITEM1_ID AND I.ITEM_CATEGORY_ACTION_CD = 'U' " +
                    "AND IA.ITEM_ASSOC_CD = '" + RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY + "')";
            executeUpdate(conn, sql);

        }

        {
            sql = "UPDATE CLW_ITEM_MAPPING IM  SET (BUS_ENTITY_ID,ITEM_NUM,SHORT_DESC,ITEM_UOM,ITEM_PACK,LONG_DESC,STATUS_CD) = \n" +
                    "(SELECT " + pDistributorId + ",I.ITEM_NUM ,NULL,I.UOM,I.PACK,NULL,'" + RefCodeNames.SIMPLE_STATUS_CD.ACTIVE + "'  FROM " + pTable + "_ITEM I WHERE I.ITEM_ID = IM.ITEM_ID),\n" +
                    "MOD_BY='" + pUser + "',\n" +
                    "MOD_DATE=SYSDATE\n" +
                    " WHERE IM.ITEM_MAPPING_ID IN (SELECT IM1.ITEM_MAPPING_ID FROM " + pTable + "_ITEM I,CLW_ITEM_MAPPING IM1 " +
                    "WHERE I.ITEM_ID = IM1.ITEM_ID " +
                    " AND IM1.ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "' " +
                    "AND  I.ITEM_DISTRIBUTOR_ACTION_CD='U' AND IM1.BUS_ENTITY_ID = " + pDistributorId + " )";
            executeUpdate(conn, sql);
        }


        {
            sql = " UPDATE CLW_ITEM_META IMETA  SET (CLW_VALUE) = (SELECT UOM FROM " + pTable + "_ITEM I WHERE  I.ITEM_ID = IMETA.ITEM_ID AND IMETA.NAME_VALUE='"+JanPakLoaderAgent.UOM+"')," +
                    " MOD_BY='" + pUser + "',\n" +
                    "  MOD_DATE=SYSDATE\n" +
                    "WHERE IMETA.ITEM_ID IN (SELECT I.ITEM_ID FROM " + pTable + "_ITEM I WHERE ITEM_META_UOM_ACTION_CD = 'U'  AND I.ITEM_ID =  IMETA.ITEM_ID) AND IMETA.NAME_VALUE='"+JanPakLoaderAgent.UOM+"'";
            executeUpdate(conn, sql);
        }

         {
            sql = " UPDATE CLW_ITEM_META IMETA  SET (CLW_VALUE) = (SELECT PACK FROM " + pTable + "_ITEM I WHERE  I.ITEM_ID = IMETA.ITEM_ID AND IMETA.NAME_VALUE='"+JanPakLoaderAgent.PACK+"'),\n" +
                    " MOD_BY='" + pUser + "',\n" +
                    "  MOD_DATE=SYSDATE\n" +
                    "WHERE IMETA.ITEM_ID IN (SELECT I.ITEM_ID FROM " + pTable + "_ITEM I WHERE ITEM_META_PACK_ACTION_CD = 'U'  AND I.ITEM_ID =  IMETA.ITEM_ID) AND IMETA.NAME_VALUE='"+JanPakLoaderAgent.PACK+"'";
            executeUpdate(conn, sql);
        }*/


       /* {
            sql = " UPDATE CLW_CATALOG_STRUCTURE CS  SET (SHORT_DESC,CUSTOMER_SKU_NUM,STATUS_CD) = (SELECT I.ITEM_DESC,(I.ITEM_ID+10000),'" + RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE + "' FROM " + pTable + "_ITEM I WHERE  I.ITEM_ID = CS.ITEM_ID),\n" +
                    " MOD_BY='" + pUser + "',\n" +
                    "  MOD_DATE=SYSDATE\n" +
                    "WHERE CS.CATALOG_STRUCTURE_ID IN (SELECT CS2.CATALOG_STRUCTURE_ID FROM " + pTable + "_ITEM I,CLW_CATALOG_STRUCTURE CS2 WHERE I.ITEM_CATALOG_ACTION_CD = 'U' AND I.ITEM_ID =  CS2.ITEM_ID AND CS2.CATALOG_ID = " + pStoreCatalogId + " AND CS2.CATALOG_STRUCTURE_CD='" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT + "')";
            executeUpdate(conn, sql);
        }  */
    }

    private void match(Connection conn,
                       int pStoreId,
                       int pDistributorId,
                       int pStoreCatalogId,
                       String pTable,
                       String pUser) throws Exception {

        tempDrop(conn, pTable);

        defineManuf(conn, pTable, pStoreId);
        matchManuf(conn, pTable);

        defineCateg(conn, pTable, pStoreCatalogId);
        matchCateg(conn, pTable);

        defineItem(conn, pTable, pDistributorId);
        defineEnterpriseItem(conn,pTable);
        matchItem(conn, pTable, pUser, pStoreId, pDistributorId, pStoreCatalogId);

    }

    private void defineEnterpriseItem(Connection conn, String pTable) throws SQLException {
        String sql;

        {
            sql = "CREATE TABLE "+pTable+"_TEMP AS SELECT I.*,IM_UOM.CLW_VALUE AS UOM,IM_PACK.CLW_VALUE AS PACK FROM \n" +
                    "(SELECT I1.ITEM_ID AS ENTERPRISE_ITEM_ID, IA.ITEM1_ID AS ITEM_ID, I1.SHORT_DESC AS ENTERPRISE_SHORT_DESC,I1.LONG_DESC AS ENTERPRISE_LONG_DESC FROM CLW_ITEM I1,"+pTable+"_ITEM I2, CLW_ITEM_ASSOC IA \n" +
                    "  WHERE  I1.ITEM_ID = IA.ITEM2_ID  \n" +
                    "   AND  IA.ITEM1_ID = I2.ITEM_ID\n" +
                    "   AND I1.ITEM_TYPE_CD='"+ RefCodeNames.ITEM_TYPE_CD.PRODUCT +"'\n" +
                    "   AND IA.ITEM_ASSOC_CD='"+RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT +"' ) I\n" +
                    " LEFT JOIN " +
                    "CLW_ITEM_META IM_UOM ON IM_UOM.ITEM_ID = I.ITEM_ID AND IM_UOM.NAME_VALUE='"+JanPakLoaderAgent.UOM+"'\n"+
                    " LEFT JOIN " +
                    "CLW_ITEM_META IM_PACK ON IM_PACK.ITEM_ID = I.ITEM_ID AND IM_PACK.NAME_VALUE='"+JanPakLoaderAgent.PACK+"'";
            executeUpdate(conn, sql);
        }
        {
            sql = "CREATE INDEX " + pTable + "_INDEX20 ON " + pTable + "_TEMP(ITEM_ID)";
            executeUpdate(conn, sql);
        }
        {

            sql = " UPDATE " + pTable + "_ITEM I SET (ITEM_DESC,ITEM_LONG_DESC,UOM,PACK) = (SELECT NVL(T.ENTERPRISE_SHORT_DESC,I.ITEM_DESC),NVL(T.ENTERPRISE_LONG_DESC,I.ITEM_DESC),NVL(T.UOM,I.UOM),NVL(T.PACK,I.PACK) FROM " + pTable + "_TEMP T WHERE T.ITEM_ID =  I.ITEM_ID)" +
                    " WHERE I.ITEM_ID IN (SELECT T.ITEM_ID FROM " + pTable + "_TEMP T)";
            executeUpdate(conn, sql);
        }
        {

            sql = "DROP TABLE " + pTable + "_TEMP PURGE";
            executeUpdate(conn, sql);
        }

    }

    private void matchItem(Connection conn, String pTable, String pUser, int pStoreId, int pDistributorId, int pStoreCatalogId) throws SQLException {
        String sql;

        {
            sql = "CREATE INDEX " + pTable + "_INDEX4 ON " + pTable + "_MFG(MANUFACTURER_ID)";
            executeUpdate(conn, sql);
        }


        {
            sql = "CREATE INDEX " + pTable + "_INDEX5 ON " + pTable + "_ITEM(MANUFACTURER)";
            executeUpdate(conn, sql);
        }

/*
        {
            sql = "UPDATE " + pTable + "_ITEM I SET (MANUFACTURER_ID,ITEM_MANUFACTURER_ACTION_CD) = (SELECT MFG.MANUFACTURER_ID,MANUFACTURER_ACTION_CD FROM " + pTable + "_MFG  MFG WHERE  MFG.MANUFACTURER=I.MANUFACTURER)";
            executeUpdate(conn, sql);
        }
*/

        {
            sql = "UPDATE " + pTable + "_ITEM I SET (MANUFACTURER_ID) = (SELECT MFG.MANUFACTURER_ID FROM " + pTable + "_MFG  MFG WHERE  MFG.MANUFACTURER=I.MANUFACTURER)";
            executeUpdate(conn, sql);
        }


        {
            sql = "CREATE INDEX " + pTable + "_INDEX9 ON " + pTable + "_ITEM(CATEGORY)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX10 ON " + pTable + "_ITEM(CATEGORY_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX11 ON " + pTable + "_ITEM(ITEM_ID)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX17 ON " + pTable + "_ITEM(ITEM_DESC)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX19 ON " + pTable + "_ITEM(ITEM_LONG_DESC)";
            executeUpdate(conn, sql);
        }

 /*       {
            sql = "UPDATE " + pTable + "_ITEM I SET (CATEGORY_ID,ITEM_CATEGORY_ACTION_CD) = (SELECT CATEG.CATEGORY_ID,CATEGORY_ACTION_CD FROM " + pTable + "_CATEGORY CATEG WHERE  CATEG.CATEGORY=I.CATEGORY)";
            executeUpdate(conn, sql);
        }
*/

        {
            sql = "UPDATE " + pTable + "_ITEM I SET (CATEGORY_ID) = (SELECT CATEG.CATEGORY_ID FROM " + pTable + "_CATEGORY CATEG WHERE  CATEG.CATEGORY=I.CATEGORY)";
            executeUpdate(conn, sql);
        }

        {
            sql = "UPDATE " + pTable + "_ITEM  SET ITEM_ID=CLW_ITEM_SEQ.NEXTVAL,\n" +
                    " MOD_BY ='" + pUser + "',\n" +
                    " MOD_DATE = SYSDATE,\n" +
                    " ITEM_ACTION_CD = 'C',\n" +
                    " ITEM_DISTRIBUTOR_ACTION_CD = 'C',\n" +
                    " ITEM_CATALOG_ACTION_CD= 'C'," +
                    " ITEM_META_UOM_ACTION_CD='C'," +
                    " ITEM_META_PACK_ACTION_CD='C' \n" +
                    "WHERE ITEM_ID IS NULL";
            executeUpdate(conn, sql);

        }


/*        {
            sql = "UPDATE " + pTable + "_ITEM I1 SET ITEM_ACTION_CD = 'U'\n" +
                    " WHERE ITEM_ACTION_CD IS NULL AND EXISTS (SELECT 'U' FROM CLW_ITEM I2 \n" +
                    " WHERE I1.ITEM_ID = I2.ITEM_ID AND (I1.ITEM_DESC!= I2.SHORT_DESC OR I1.ITEM_LONG_DESC != I2.LONG_DESC  OR I2.ITEM_STATUS_CD!='" + RefCodeNames.ITEM_STATUS_CD.ACTIVE + "'))";
            executeUpdate(conn, sql);
        }*/


 /*       {
            sql = " UPDATE " + pTable + "_ITEM I1 SET ITEM_CATEGORY_ACTION_CD = 'U'\n" +
                    " WHERE  EXISTS (SELECT 'U' FROM CLW_ITEM_ASSOC I2 \n" +
                    " WHERE I2.ITEM1_ID = I1.ITEM_ID  \n" +
                    "  AND  I2.ITEM_ASSOC_CD = '" + RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY + "' \n" +
                    "  AND I2.CATALOG_ID = " + pStoreCatalogId + "\n" +
                    "  AND  I2.ITEM2_ID!=I1.CATEGORY_ID \n" +
                    "  AND I1.CATEGORY_ID>0\n" +
                    "  AND I1.ITEM_ID >0)";

            executeUpdate(conn, sql);
        }*/


      /*  {
            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_CATALOG_ACTION_CD = 'U' WHERE  ITEM_CATALOG_ACTION_CD IS NULL AND EXISTS (\n" +
                    "SELECT 'U' FROM CLW_CATALOG_STRUCTURE CS\n" +
                    "WHERE CATALOG_ID= " + pStoreCatalogId +
                    "  AND CS.CATALOG_STRUCTURE_CD = '" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT + "'\n" +
                    "  AND CS.ITEM_ID=I.ITEM_ID AND (NVL(CS.SHORT_DESC,' ')!=I.ITEM_DESC OR NVL(CS.CUSTOMER_SKU_NUM,' ')!=I.ITEM_ID+10000 OR NVL(CS.STATUS_CD,' ')!='" + RefCodeNames.CATALOG_STATUS_CD.ACTIVE + "')" +
                    ")";

            executeUpdate(conn, sql);
        }
        */
/*
        {
            sql = " UPDATE " + pTable + "_ITEM I SET ITEM_MANUFACTURER_ACTION_CD = 'U'\n" +
                    "     WHERE EXISTS (SELECT 'U' FROM CLW_ITEM_MAPPING IM \n" +
                    "       WHERE IM.ITEM_ID = I.ITEM_ID  \n" +
                    "        AND  IM.ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "'\n" +
                    "        AND  IM.BUS_ENTITY_ID!=I.MANUFACTURER_ID)";

            executeUpdate(conn, sql);
        }*/

/*
        {
            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_MANUFACTURER_ACTION_CD = 'U'\n" +
                    " WHERE EXISTS (SELECT 'U' FROM CLW_ITEM_MAPPING IM \n" +
                    " WHERE IM.ITEM_ID = I.ITEM_ID  \n" +
                    "  AND IM.ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "'\n" +
                    "  AND  IM.BUS_ENTITY_ID=I.MANUFACTURER_ID\n" +
                    "  AND  IM.ITEM_NUM!=NVL(I.CATALOG_NUM,'"+JanPakLoaderAgent.DEFAULT_MFG_SKU_VALUE +"')) ";
            executeUpdate(conn, sql);
        }
*/


/*        {
            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_DISTRIBUTOR_ACTION_CD = 'U'\n" +
                    " WHERE I.ITEM_DISTRIBUTOR_ACTION_CD IS NULL AND EXISTS (SELECT 'U' FROM CLW_ITEM_MAPPING IM \n" +
                    " WHERE IM.ITEM_ID = I.ITEM_ID  \n" +
                    "  AND IM.ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "'\n" +
                    "  AND  IM.BUS_ENTITY_ID= " + pDistributorId + "\n" +
                    "  AND IM.ITEM_UOM!=I.UOM)";

            executeUpdate(conn, sql);
        }*/

/*        {
            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_DISTRIBUTOR_ACTION_CD = 'U'\n" +
                    " WHERE I.ITEM_DISTRIBUTOR_ACTION_CD IS NULL AND EXISTS (SELECT 'U' FROM CLW_ITEM_MAPPING IM \n" +
                    " WHERE IM.ITEM_ID = I.ITEM_ID  \n" +
                    "  AND IM.ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "'\n" +
                    "  AND IM.BUS_ENTITY_ID= " + pDistributorId + "\n" +
                    "  AND IM.ITEM_PACK!=I.PACK)";

            executeUpdate(conn, sql);
        }*/

/*        {
            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_META_UOM_ACTION_CD = 'U'\n" +
                    "   WHERE I.ITEM_META_UOM_ACTION_CD IS NULL AND EXISTS (SELECT 'U' FROM CLW_ITEM_META IM \n" +
                    "  WHERE IM.ITEM_ID = I.ITEM_ID  \n" +
                    "  AND IM.NAME_VALUE = '"+JanPakLoaderAgent.UOM+"'\n" +
                    "  AND  IM.CLW_VALUE!=I.UOM)";

            executeUpdate(conn, sql);
        }*/

/*        {
            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_META_PACK_ACTION_CD = 'U'\n" +
                    "   WHERE I.ITEM_META_PACK_ACTION_CD IS NULL AND EXISTS (SELECT 'U' FROM CLW_ITEM_META IM \n" +
                    "  WHERE IM.ITEM_ID = I.ITEM_ID  \n" +
                    "  AND IM.NAME_VALUE = '"+JanPakLoaderAgent.PACK+"'\n" +
                    "  AND IM.CLW_VALUE!=I.PACK)";

            executeUpdate(conn, sql);
        }*/

        /*   {
                    sql = "UPDATE " + pTable + "_ITEM I SET ITEM_META_UOM_ACTION_CD = 'D'\n" +
                            "   WHERE I.ITEM_META_UOM_ACTION_CD IS NULL AND EXISTS (SELECT 'D' FROM CLW_ITEM_META IM \n" +
                            "  WHERE IM.ITEM_ID = I.ITEM_ID  \n" +
                            "  AND IM.NAME_VALUE = 'UOM'\n" +
                            "  AND TRIM(IM.CLW_VALUE)  IS NOT NULL \n" +
                            "  AND TRIM(I.UOM) IS NULL)";

                    executeUpdate(conn, sql);
                }
        */

        /* {
            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_DISTRIBUTOR_ACTION_CD = 'C'\n" +
                    "  WHERE I.ITEM_ID IN (SELECT I.ITEM1_ID FROM " +
                    "(SELECT I1.ITEM_ID AS ITEM1_ID,I2.ITEM_ID AS ITEM2_ID FROM " + pTable + "_ITEM I1 LEFT JOIN \n" +
                    " (SELECT IMAP.ITEM_ID FROM CLW_ITEM_MAPPING IMAP WHERE IMAP.ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "'\n" +
                    " AND IMAP.BUS_ENTITY_ID = " + pDistributorId + ") I2 ON I1.ITEM_ID = I2.ITEM_ID WHERE" +
                    " I1.ITEM_DISTRIBUTOR_ACTION_CD IS NULL ) I " +
                    "WHERE I.ITEM2_ID IS NULL)";

            executeUpdate(conn, sql);
        }*/

/*        {

            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_MANUFACTURER_ACTION_CD = 'C'\n" +
                    "  WHERE ITEM_MANUFACTURER_ACTION_CD IS NULL AND ITEM_ID IN (SELECT I.ITEM_ID FROM " + pTable + "_ITEM  I LEFT JOIN (SELECT IM.ITEM_ID FROM CLW_ITEM_MAPPING IM\n" +
                    " WHERE IM.BUS_ENTITY_ID IN  (SELECT BE.BUS_ENTITY_ID FROM CLW_BUS_ENTITY BE,CLW_BUS_ENTITY_ASSOC BEA\n" +
                    "   WHERE BE.BUS_ENTITY_ID = BEA.BUS_ENTITY1_ID\n" +
                    "     AND BEA.BUS_ENTITY2_ID = " + pStoreId + "\n" +
                    "      AND BE.BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER + "'\n" +
                    "       AND BEA.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE + "')  \n" +
                    "        AND IM.ITEM_MAPPING_CD='" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "') IIM  \n" +
                    "  ON I.ITEM_ID = IIM.ITEM_ID WHERE IIM.ITEM_ID IS NULL)";

            executeUpdate(conn, sql);
        }*/

      {

            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_MANUFACTURER_ACTION_CD = 'C'" +
                    " WHERE ITEM_ACTION_CD='C'" +
                    " AND MANUFACTURER_ID>0";

            executeUpdate(conn, sql);
        }


/*        {
            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_META_UOM_ACTION_CD = 'C'  WHERE I.ITEM_ID IN (\n" +
                    "SELECT I.ITEM1_ID FROM (SELECT I1.ITEM_ID AS ITEM1_ID,I2.ITEM_ID AS ITEM2_ID FROM " + pTable + "_ITEM I1 " +
                    "LEFT JOIN (SELECT IMAP.ITEM_ID FROM CLW_ITEM_MAPPING IMAP,CLW_ITEM_META IMETA \n" +
                    " WHERE IMAP.ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "'\n" +
                    "  AND  IMAP.BUS_ENTITY_ID = " + pDistributorId +
                    "   AND IMETA.ITEM_ID = IMAP.ITEM_ID AND " +
                    "     IMETA.NAME_VALUE = '"+JanPakLoaderAgent.UOM+"' " +
                    "       AND TRIM(IMETA.CLW_VALUE) IS NOT NULL GROUP BY IMAP.ITEM_ID) I2  ON I1.ITEM_ID = I2.ITEM_ID WHERE  " +
                    " I1.ITEM_META_UOM_ACTION_CD IS NULL AND TRIM(I1.UOM) IS NOT NULL ) I " +
                    " WHERE I.ITEM2_ID IS NULL)";

            executeUpdate(conn, sql);
        }

         {
            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_META_PACK_ACTION_CD = 'C'  WHERE I.ITEM_ID IN (\n" +
                    "SELECT I.ITEM1_ID FROM (SELECT I1.ITEM_ID AS ITEM1_ID,I2.ITEM_ID AS ITEM2_ID FROM " + pTable + "_ITEM I1 " +
                    "LEFT JOIN (SELECT IMAP.ITEM_ID FROM CLW_ITEM_MAPPING IMAP,CLW_ITEM_META IMETA \n" +
                    " WHERE IMAP.ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "'\n" +
                    "  AND  IMAP.BUS_ENTITY_ID = " + pDistributorId +
                    "   AND IMETA.ITEM_ID = IMAP.ITEM_ID AND " +
                    "     IMETA.NAME_VALUE = '"+JanPakLoaderAgent.PACK+"' " +
                    "       AND TRIM(IMETA.CLW_VALUE) IS NOT NULL GROUP BY IMAP.ITEM_ID) I2  ON I1.ITEM_ID = I2.ITEM_ID WHERE  " +
                    " I1.ITEM_META_PACK_ACTION_CD IS NULL) I " +
                    " WHERE I.ITEM2_ID IS NULL)";

            executeUpdate(conn, sql);
        }*/

/*
        {
            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_CATEGORY_ACTION_CD = 'C'\n" +
                    "  WHERE I.ITEM_ID IN (SELECT I.ITEM1_ID FROM (SELECT I1.ITEM_ID AS ITEM1_ID,I2.ITEM_ID AS ITEM2_ID FROM " + pTable + "_ITEM I1 LEFT JOIN \n" +
                    " (SELECT IA.ITEM1_ID AS ITEM_ID FROM CLW_ITEM_ASSOC IA \n" +
                    "  WHERE IA.ITEM_ASSOC_CD = '" + RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY + "' \n" +
                    "  AND  CATALOG_ID = " + pStoreCatalogId + ") I2 ON I1.ITEM_ID = I2.ITEM_ID WHERE " +
                    "I1.ITEM_CATEGORY_ACTION_CD IS NULL ) I WHERE I.ITEM2_ID IS NULL)";

            executeUpdate(conn, sql);
        }
*/           {

            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_CATEGORY_ACTION_CD = 'C'" +
                    " WHERE ITEM_ACTION_CD='C'" +
                    " AND CATEGORY_ID>0";

            executeUpdate(conn, sql);
        }



        /*{
            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_CATALOG_ACTION_CD = 'C' \n" +
                    " WHERE I.ITEM_ID IN (SELECT I.ITEM1_ID FROM (SELECT I1.ITEM_ID AS ITEM1_ID,I2.ITEM_ID AS ITEM2_ID FROM " + pTable + "_ITEM I1 LEFT JOIN \n" +
                    "(SELECT CS.ITEM_ID FROM CLW_CATALOG_STRUCTURE CS\n" +
                    "WHERE CATALOG_ID = " + pStoreCatalogId + " AND CS.CATALOG_STRUCTURE_CD = '" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT + "'" +
                    ") I2 ON I1.ITEM_ID = I2.ITEM_ID WHERE  I1.ITEM_CATALOG_ACTION_CD IS NULL ) I " +
                    " WHERE I.ITEM2_ID IS NULL)";

            executeUpdate(conn, sql);
        }*/


    }

    private void matchCateg(Connection conn, String pTable) throws SQLException {
        String sql;

        {
            sql = "CREATE INDEX " + pTable + "_INDEX8 ON " + pTable + "_CATEGORY(CATEGORY_ID)";
            executeUpdate(conn, sql);
        }

/*        {
            sql = "UPDATE " + pTable + "_CATEGORY I1 SET CATEGORY_ACTION_CD = 'U'\n" +
                    " WHERE CATEGORY_ACTION_CD IS NULL \n" +
                    " AND EXISTS (SELECT 'U' FROM CLW_ITEM I2 \n" +
                    " WHERE I1.CATEGORY_ID = I2.ITEM_ID AND  I2.ITEM_STATUS_CD!='" + RefCodeNames.ITEM_STATUS_CD.ACTIVE + "')";

            executeUpdate(conn, sql);
        }*/

        {
            sql = "UPDATE " + pTable + "_CATEGORY  SET CATEGORY_ID=CLW_ITEM_SEQ.NEXTVAL,\n" +
                    " CATEGORY_ACTION_CD = 'C',\n" +
                    " CATEGORY_CATALOG_ACTION_CD ='C'  \n" +
                    "WHERE CATEGORY_ID IS NULL";
            executeUpdate(conn, sql);
        }

/*
        {
            sql = "UPDATE " + pTable + "_CATEGORY CTRY SET CATEGORY_CATALOG_ACTION_CD = NULL WHERE  CATEGORY_CATALOG_ACTION_CD  IS NULL AND EXISTS (\n" +
                    "SELECT 'U' FROM CLW_CATALOG_STRUCTURE CS\n" +
                    "WHERE CATALOG_ID! = " + pStoreCatalogId + "\n" +
                    "  AND CS.CATALOG_STRUCTURE_CD = '" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "'\n" +
                    "  AND CS.ITEM_ID=CTRY.CATEGORY_ID\n" +
                    ")";
            executeUpdate(conn, sql);
        }


        {
            sql = "UPDATE " + pTable + "_CATEGORY CTRY SET CATEGORY_CATALOG_ACTION_CD = 'C' \n" +
                    " WHERE CTRY.CATEGORY_ID IN (SELECT I.ITEM1_ID FROM (SELECT I1.CATEGORY_ID AS ITEM1_ID,I2.ITEM_ID AS ITEM2_ID FROM " + pTable + "_CATEGORY  I1 LEFT JOIN \n" +
                    "(SELECT CS.ITEM_ID FROM CLW_CATALOG_STRUCTURE CS\n" +
                    "WHERE CATALOG_ID =  " + pStoreCatalogId + "\n" +
                    "  AND CS.CATALOG_STRUCTURE_CD = '" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "'\n" +
                    ") I2    ON I1.CATEGORY_ID = I2.ITEM_ID WHERE  I1.CATEGORY_CATALOG_ACTION_CD IS NULL ) I " +
                    "WHERE I.ITEM2_ID IS NULL)";
            executeUpdate(conn, sql);
        }*/

    }

    private void matchManuf(Connection conn, String pTable) throws SQLException {
        String sql;

        {
            sql = "UPDATE " + pTable + "_MFG  SET MANUFACTURER_ID=CLW_BUS_ENTITY_SEQ.NEXTVAL,\n" +
                    " MOD_BY ='" + pTable + "',\n" +
                    " MOD_DATE = SYSDATE,\n" +
                    " MANUFACTURER_ACTION_CD = 'C' \n" +
                    "WHERE MANUFACTURER_ID IS NULL";
            executeUpdate(conn, sql);
        }

      /*  {
            sql = "UPDATE " + pTable + "_MFG  SET MANUFACTURER_ACTION_CD = 'U',\n" +
                    " MOD_BY ='" + pTable + "',\n" +
                    " MOD_DATE = SYSDATE\n" +
                    "WHERE MANUFACTURER_ACTION_CD IS NULL" +
                    " AND EXISTS (SELECT BUS_ENTITY_ID FROM CLW_BUS_ENTITY " +
                    "WHERE BUS_ENTITY_ID = MANUFACTURER_ID " +
                    "AND BUS_ENTITY_STATUS_CD!='" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "' )";
            executeUpdate(conn, sql);
        }
*/
    }

    private void defineCateg(Connection conn, String pTable, int pStoreCatalogId) throws SQLException {
        String sql;

        {
            sql = "CREATE INDEX " + pTable + "_INDEX7 ON " + pTable + "_CATEGORY(CATEGORY)";
            executeUpdate(conn, sql);
        }


        {
            sql = "UPDATE " + pTable + "_CATEGORY CTRY SET CATEGORY_ID = " +
                    " (  SELECT I.ITEM_ID FROM CLW_CATALOG_STRUCTURE CS,CLW_ITEM I WHERE CATALOG_ID =" + pStoreCatalogId +
                    "        AND I.ITEM_ID = CS.ITEM_ID \n" +
                    "         AND I.SHORT_DESC = CTRY.CATEGORY\n" +
                    "          AND CS.CATALOG_STRUCTURE_CD = '" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "')";
            executeUpdate(conn, sql);
        }

    }

    private void defineManuf(Connection conn, String pTable, int pStoreId) throws SQLException {
        String sql;

        {
            sql = "CREATE INDEX " + pTable + "_INDEX3 ON " + pTable + "_MFG(MANUFACTURER)";
            executeUpdate(conn, sql);
        }

        {
            sql = "UPDATE " + pTable + "_MFG MFG SET MANUFACTURER_ID = (SELECT BE.BUS_ENTITY_ID FROM CLW_BUS_ENTITY_ASSOC BEA,CLW_BUS_ENTITY BE\n" +
                    " WHERE BEA.BUS_ENTITY2_ID = " + pStoreId +
                    " AND BEA.BUS_ENTITY1_ID = BE.BUS_ENTITY_ID \n" +
                    " AND BEA.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE + "' \n" +
                    " AND BE.BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER + "'\n" +
                    " AND BE.SHORT_DESC = MFG.MANUFACTURER)";
            executeUpdate(conn, sql);
        }


    }

    private void defineItem(Connection conn, String pTable, int pDistributorId) throws SQLException {
        String sql;

        {
            sql = "CREATE  TABLE " + pTable + "_TEMP(\n" +
                    "     ITEM_ID NUMBER(38),\n" +
                    "     ITEM_NUM VARCHAR2(255)\n" +
                    ")";
            executeUpdate(conn, sql);
        }

        {
            sql = "INSERT /*APPEND*/ INTO " + pTable + "_TEMP (ITEM_ID,ITEM_NUM) (SELECT IM.ITEM_ID,IM.ITEM_NUM" +
                    " FROM CLW_ITEM_MAPPING IM WHERE  IM.BUS_ENTITY_ID = " + pDistributorId + ")";
            executeUpdate(conn, sql);

        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX1 ON " + pTable + "_ITEM(ITEM_NUM)";
            executeUpdate(conn, sql);
        }

        {
            sql = "CREATE INDEX " + pTable + "_INDEX2 ON " + pTable + "_TEMP(ITEM_NUM)";
            executeUpdate(conn, sql);
        }

        {
            sql = "UPDATE " + pTable + "_ITEM I SET ITEM_ID = (SELECT T.ITEM_ID FROM " + pTable + "_TEMP T  WHERE T.ITEM_NUM = I.ITEM_NUM)";
            executeUpdate(conn, sql);
        }

        {
            sql = "DROP TABLE " + pTable + "_TEMP PURGE";
            executeUpdate(conn, sql);
        }
    }

    private void prepare(Connection conn, String pTable,String pFilterTable, String pUser) throws SQLException {

        itemDrop(conn, pTable);
        manufDrop(conn, pTable);
        categDrop(conn, pTable);

        itemCreate(conn, pTable);
        manufCreate(conn, pTable);
        categCreate(conn, pTable);

        itemLoad(conn, pTable, pFilterTable, pUser);
        manufLoad(conn, pTable, pUser);
        categLoad(conn, pTable, pUser);

    }

    private void itemLoad(Connection conn, String pTable, String pFilterTable, String pUser) throws SQLException {

        String sql;

        {
            sql = "INSERT /*APPEND*/ INTO " + pTable + "_ITEM (ITEM_NUM,ITEM_DESC,PRICE_LINE," +
                    "CATALOG_NUM,MANUFACTURER,UOM,JD_UOM,PACK,CATEGORY,ADD_BY, ADD_DATE,MOD_BY,MOD_DATE) " +
                    "(SELECT ITEM_NUM,SUBSTR(ITEM_DESC,0,255),PRICE_LINE,CATALOG_NUM,MANUFACTURER,NVL(UOM,'"+JanPakLoaderAgent.DEFAULT_UOM_VALUE+"'),NVL(UOM,'"+JanPakLoaderAgent.DEFAULT_UOM_VALUE+"'),'"+JanPakLoaderAgent.DEFAULT_PACK_VALUE+"',SELECT_CODE," +
                    "'" + pUser + "',SYSDATE,'" + pUser + "',SYSDATE FROM " + pTable + (Utility.isSet(pFilterTable) ? " WHERE ITEM_NUM IN (SELECT ITEM_NUM FROM " + pFilterTable + ")" : "") + ")" +
                    " ";

            executeUpdate(conn, sql);
        }

        {
            sql = "UPDATE " + pTable + "_ITEM SET JD_UOM = DECODE(JD_UOM,'" + JanPakLoaderAgent.JD_UOM_CD.CA + "','" + JanPakLoaderAgent.JD_UOM_CD.CS + "',JD_UOM) WHERE JD_UOM='" + JanPakLoaderAgent.JD_UOM_CD.CA + "'";
            executeUpdate(conn, sql);
        }

        {
            sql = "UPDATE " + pTable + "_ITEM SET JD_UOM = DECODE(JD_UOM,'" + JanPakLoaderAgent.JD_UOM_CD.CT + "','" + JanPakLoaderAgent.JD_UOM_CD.EA + "',JD_UOM) WHERE JD_UOM='" + JanPakLoaderAgent.JD_UOM_CD.CT + "'";
            executeUpdate(conn, sql);
        }

        {
            sql = "UPDATE " + pTable + "_ITEM SET CATEGORY ='CATEGORY_NOT_ASSIGN' WHERE TRIM(CATEGORY) IS NULL";

            executeUpdate(conn, sql);
        }


    }

    private void manufLoad(Connection conn, String pTable, String pUser) throws SQLException {

        String sql = "INSERT /*APPEND*/ INTO " + pTable + "_MFG (MANUFACTURER,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) " +
                "(SELECT MANUFACTURER,'" + pUser + "',SYSDATE,'" + pUser + "',SYSDATE FROM " + pTable + "_ITEM T1,(\n" +
                "SELECT MAX(ROWID) AS ROW_ID FROM " + pTable + "_ITEM GROUP BY MANUFACTURER) T2\n" +
                "WHERE  T1.ROWID = T2.ROW_ID)";

        executeUpdate(conn, sql);
    }


    private void categLoad(Connection conn, String pTable, String pUser) throws SQLException {

        String sql = "INSERT /*APPEND*/ INTO " + pTable + "_CATEGORY (CATEGORY,ADD_BY, ADD_DATE,MOD_BY,MOD_DATE) " +
                "(SELECT CATEGORY,'" + pUser + "',SYSDATE,'" + pUser + "',SYSDATE FROM " + pTable + "_ITEM T1,(\n" +
                "SELECT MAX(ROWID) AS ROW_ID FROM " + pTable + "_ITEM WHERE TRIM(CATEGORY) IS NOT NULL  GROUP BY  CATEGORY) T2\n" +
                "WHERE  T1.ROWID = T2.ROW_ID)";

        executeUpdate(conn, sql);

    }


    private void categCreate(Connection conn, String pTable) throws SQLException {

        String sql = "CREATE  TABLE " + pTable + "_CATEGORY(\n" +
                "    CATEGORY VARCHAR2(255) NULL,\n" +
                "    CATEGORY_ID NUMBER(38) NULL,\n" +
                "    CATEGORY_ACTION_CD VARCHAR2(2) NULL,\n" +
                "    CATEGORY_CATALOG_ACTION_CD VARCHAR2(2) NULL,\n" +
                "    ADD_DATE DATE NOT NULL,\n" +
                "    ADD_BY   VARCHAR2 (255),\n" +
                "    MOD_DATE DATE NOT NULL,\n" +
                "    MOD_BY   VARCHAR2 (255) \n" +
                ") ";

        executeUpdate(conn, sql);
    }

    private void manufCreate(Connection conn, String pTable) throws SQLException {

        String sql = "CREATE  TABLE " + pTable + "_MFG(\n" +
                "    MANUFACTURER VARCHAR2(255) NULL,\n" +
                "    MANUFACTURER_ID NUMBER(38) NULL,\n" +
                "    MANUFACTURER_ACTION_CD VARCHAR2(2) NULL,\n" +
                "    ADD_DATE DATE NOT NULL,\n" +
                "    ADD_BY   VARCHAR2 (255),\n" +
                "    MOD_DATE DATE NOT NULL,\n" +
                "    MOD_BY   VARCHAR2 (255)\n" +
                ")";

        executeUpdate(conn, sql);
    }


    private void itemCreate(Connection conn, String pTable) throws SQLException {

        String sql = "CREATE  TABLE " + pTable + "_ITEM(\n" +
                "    ITEM_NUM  VARCHAR2(255)  NULL,\n" +
                "    ITEM_DESC VARCHAR2(255) NULL,\n" +
                "    ITEM_ID  NUMBER(38) NULL,\n" +
                "    ITEM_LONG_DESC  VARCHAR2(2000) NULL,\n" +
                "    PRICE_LINE VARCHAR2(255) NULL,\n" +
                "    CATALOG_NUM VARCHAR2(255) NULL,\n" +
                "    MANUFACTURER VARCHAR2(255) NULL,\n" +
                "    MANUFACTURER_ID NUMBER(38) NULL,\n" +
                "    UOM VARCHAR2(30)   NOT NULL,\n" +
                "    PACK VARCHAR2(30)  NOT NULL,\n" +
                "    JD_UOM VARCHAR2(30)  NULL,\n" +
                "    CATEGORY VARCHAR2(255) NULL,\n" +
                "    CATEGORY_ID NUMBER(38) NULL,\n" +
                "    ITEM_ACTION_CD  VARCHAR2(2)  NULL,\n" +
                "    ITEM_CATEGORY_ACTION_CD  VARCHAR2(2)  NULL,\n" +
                "    ITEM_META_UOM_ACTION_CD  VARCHAR2(2)  NULL,\n" +
                "    ITEM_META_PACK_ACTION_CD  VARCHAR2(2)  NULL,\n" +
                "    ITEM_DISTRIBUTOR_ACTION_CD  VARCHAR2(2)  NULL,\n" +
                "    ITEM_MANUFACTURER_ACTION_CD  VARCHAR2(2)  NULL,\n" +
                "    ITEM_CATALOG_ACTION_CD  VARCHAR2(2)  NULL, \n" +
                "    ADD_DATE DATE NOT NULL,\n" +
                "    ADD_BY   VARCHAR2 (255),\n" +
                "    MOD_DATE DATE NOT NULL,\n" +
                "    MOD_BY   VARCHAR2 (255)\n" +
                " )";

        executeUpdate(conn, sql);

    }

    private void executeUpdate(Connection conn, String sql) throws SQLException {
        Statement stmt;
        logInfo("executeUpdate = > SQL:" + sql);
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    private boolean itemDrop(Connection conn, String pTable) throws SQLException {
        if (existTable(conn, pTable + "_ITEM")) {
            dropTable(conn, pTable + "_ITEM");
            return true;
        }
        return false;
    }

    private boolean manufDrop(Connection conn, String pTable) throws SQLException {
        if (existTable(conn, pTable + "_MFG")) {
            dropTable(conn, pTable + "_MFG");
            return true;
        }
        return false;
    }

    private boolean categDrop(Connection conn, String pTable) throws SQLException {
        if (existTable(conn, pTable + "_CATEGORY")) {
            dropTable(conn, pTable + "_CATEGORY");
            return true;
        }
        return false;
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

        JanPakLoaderAgent jplAgent = new JanPakLoaderAgent('|');
        jplAgent.accept(conn, pData, pTable, pFileName, fModDate, current, pUser);

        logInfo("accept => END.");
    }

    private void createMainTable(Connection conn, String pTable) throws SQLException {

        String sql = "CREATE TABLE " + pTable + "(\n" +
                "  FILE_NAME    CHAR(28)     NULL,\n" +
                "  FILE_DATE    DATE         NULL,\n" +
                "  ITEM_NUM  VARCHAR2(255)  NULL,\n" +
                "  ITEM_DESC VARCHAR2(500) NULL,\n" +
                "  PRICE_LINE VARCHAR2(255) NULL,\n" +
                "  CATALOG_NUM VARCHAR2(255) NULL,\n" +
                "  MANUFACTURER VARCHAR2(255) NULL,\n" +
                "  UOM VARCHAR2(255)  NULL,\n" +
                "  SELECT_CODE VARCHAR2(255) NULL,\n" +
                "  ADD_DATE DATE NOT NULL,\n" +
                "  ADD_BY   VARCHAR2 (255),\n" +
                "  MOD_DATE DATE NOT NULL,\n" +
                "  MOD_BY   VARCHAR2 (255)\n" +
                ")";

        logInfo("createMainTable = > sql:" + sql);

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();

    }

    private boolean tempDrop(Connection conn, String pTable) throws SQLException {

        if (existTable(conn, pTable + "_TEMP")) {
            dropTable(conn, pTable + "_TEMP");
            return true;
        }

        return false;
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

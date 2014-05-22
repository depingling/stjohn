
package com.cleanwise.service.api.session;

/**
 * Title:        DataWarehouseBean
 * Description:  Bean implementation for DataWarehouse Stateless Session Bean
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.

 * @author       Alexey Lukovnikov
 */

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.apache.log4j.Category;

import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.utils.Constants;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import com.cleanwise.service.api.util.Utility;
import java.text.*;
import java.util.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import java.io.FileInputStream;
import com.cleanwise.service.api.util.MultipleDataException;
import com.cleanwise.service.api.value.IdVector;

public class DataWarehouseBean extends UtilityServicesAPI {
	private static final Category log = Category.getInstance(DataWarehouseBean.class);
	
    private static final String TBL_CLW_ADDRESS             = "CLW_ADDRESS";
    private static final String TBL_CLW_PROPERTY            = "CLW_PROPERTY";
    private static final String TBL_CLW_BUS_ENTITY          = "CLW_BUS_ENTITY";
    private static final String TBL_CLW_BUS_ENTITY_ASSOC    = "CLW_BUS_ENTITY_ASSOC";
    private static final String TBL_CLW_ITEM                = "CLW_ITEM";
    private static final String TBL_CLW_ITEM_ASSOC          = "CLW_ITEM_ASSOC";
    private static final String TBL_CLW_CATALOG             = "CLW_CATALOG";
    private static final String TBL_CLW_CATALOG_ASSOC       = "CLW_CATALOG_ASSOC";
    private static final String TBL_CLW_CATALOG_STRUCTURE   = "CLW_CATALOG_STRUCTURE";
    private static final String TBL_CLW_ITEM_META           = "CLW_ITEM_META";
    private static final String TBL_CLW_ITEM_MAPPING        = "CLW_ITEM_MAPPING";
    private static final String TBL_CLW_ORDER_ITEM          = "CLW_ORDER_ITEM";
    private static final String TBL_CLW_ORDER               = "CLW_ORDER";
    private static final String TBL_CLW_ORDER_PROPERTY      = "CLW_ORDER_PROPERTY";
    private static final String TBL_CLW_GROUP               = "CLW_GROUP";
    private static final String TBL_CLW_GROUP_ASSOC         = "CLW_GROUP_ASSOC";
    private static final String TBL_CLW_INVOICE_DIST         = "CLW_INVOICE_DIST";
    private static final String TBL_CLW_INVOICE_DIST_DETAIL  = "CLW_INVOICE_DIST_DETAIL";
    private static final String TBL_CLW_INVOICE_CUST         = "CLW_INVOICE_CUST";
    private static final String TBL_CLW_INVOICE_CUST_DETAIL  = "CLW_INVOICE_CUST_DETAIL";


    private static final String TBL_DW_SITE_DIM_TEMP        = "TMP_SITE_DIM";
    private static final String TBL_DW_ACCOUNT_DIM_TEMP     = "TMP_ACC_DIM";
    private static final String TBL_DW_CATEGORY_DIM_TEMP    = "TMP_CATEG_DIM";
    private static final String TBL_DW_ITEM_DIM_TEMP        = "TMP_ITEM_DIM";
    private static final String TBL_DW_ITEM_DISTRIBUTOR_TEMP ="TMP_ITEM_DISTR";
    private static final String TBL_DW_ORDER_FACT_TEMP       ="TMP_ORDER_FACT";
    private static final String TBL_DW_INVOICE_FACT_TEMP     ="TMP_INV_FACT";

    private static final String TBL_DW_SITE_DIM_SEQ         = "DW_SITE_DIM_SEQ";
    private static final String TBL_DW_ACCOUNT_DIM_SEQ      = "DW_ACCOUNT_DIM_SEQ";
    private static final String TBL_DW_CATEGORY_DIM_SEQ     = "DW_CATEGORY_DIM_SEQ";
    private static final String TBL_DW_ITEM_DIM_SEQ         = "DW_ITEM_DIM_SEQ";
    private static final String TBL_DW_ITEM_DISTRIBUTOR_SEQ  = "DW_ITEM_DISTRIBUTOR_SEQ";

    private static final String INITIAL_UPLOAD_DATE          = "01/01/2006 12:00:00 AM";
    private static final String UPLOAD_DATE_FORMAT           = "MM/dd/yyyy HH:mi:ss AM";

    private  String lastOrderFactUploadDate = null;
    private  String lastInvoiceFactUploadDate = null;
    private  String dbLink = null;
    private  String dwTempUser = null;
    private  String tempSuffix = null;
    /**
     * Standard <code>ejbCreate</code> method.
     *
     * @exception CreateException
     *                if an error occurs
     * @exception RemoteException
     *                if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }

   public void updateDimDist(String dbSchemaGeneral,
           String dbSchemaDim, List<Integer> storeIds, Integer jdStoreId)
           throws RemoteException {

      final String tblDwStoreDim = getFullTableName(dbSchemaDim,DataWarehouse.TBL_DW_STORE_DIM);
      Connection  conn = null;
    try {
      conn = getReportConnection();
      int pStoreId = (storeIds.get(0)).intValue();
      int dwStoreDimId = getStoreDimId(conn, pStoreId, dbSchemaGeneral, tblDwStoreDim);
      updateDimDist(dbSchemaGeneral, dbSchemaDim, storeIds, jdStoreId, dwStoreDimId );
    } catch (Exception e) {
         e.printStackTrace();
         throw new RemoteException(e.getMessage());
     } finally {
         closeConnection(conn);
        }
   }
   public void updateDimDist(String dbSchemaGeneral, String dbSchemaDim,
            List<Integer> storeIds, Integer jdStoreId, int dwStoreDimId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            updateDimDist(conn, dbSchemaGeneral, dbSchemaDim, storeIds,
                    jdStoreId, "DistDimJob", dwStoreDimId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    public void updateDimManufacturer(String dbSchemaGeneral,
            String dbSchemaDim, List<Integer> storeIds, Integer jdStoreId)
            throws RemoteException {

       final String tblDwStoreDim = getFullTableName(dbSchemaDim,DataWarehouse.TBL_DW_STORE_DIM);
       Connection  conn = null;
     try {
       conn= getReportConnection();
       int pStoreId = (storeIds.get(0)).intValue();
       int dwStoreDimId = getStoreDimId(conn, pStoreId, dbSchemaGeneral, tblDwStoreDim);
       updateDimManufacturer(dbSchemaGeneral, dbSchemaDim, storeIds, jdStoreId, dwStoreDimId );
     } catch (Exception e) {
         e.printStackTrace();
         throw new RemoteException(e.getMessage());
     } finally {
         closeConnection(conn);
     }

    }

    public void updateDimManufacturer(String dbSchemaGeneral,
            String dbSchemaDim, List<Integer> storeIds, Integer jdStoreId, int dwStoreDimId)
            throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            updateDimManufacturer(conn, dbSchemaGeneral, dbSchemaDim, storeIds,
                    jdStoreId, "ManufacturerDimJob", dwStoreDimId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private void updateDimManufacturer(Connection conn, String dbSchemaGeneral,
            String dbSchemaDim, List<Integer> storeIds, int jdStoreId,
            String user, int dwStoreDimId) throws Exception {
        logDebug("Was got storeIds:[" + storeIds + "] jdStoreId:" + jdStoreId);
        Map<Integer, Integer>  storesCross = getCrossStore2StoreDim(conn, dbSchemaDim);
        checkCrossStores(storesCross, storeIds);
        List<ManufDimUpdateItem> buffer = getExistManufacturers(conn, storeIds, jdStoreId, dbSchemaGeneral);
        logDebug("Was found  " + buffer.size() + " manufacturers.");
        Map<Integer, ManufDimUpdateItem> manufacturerMain = new HashMap<Integer, ManufDimUpdateItem>();
        Map<Integer, ManufDimUpdateItem> manufacturerJD = new HashMap<Integer, ManufDimUpdateItem>();
        Map<Integer, Integer> manufacturerCross = new HashMap<Integer, Integer>();
        for (ManufDimUpdateItem v : buffer) {
            if (v.storeId == jdStoreId) {
                manufacturerJD.put(v.manufId, v);
            } else {
                manufacturerMain.put(v.manufId, v);
            }
            if (v.jdManufId != null) {
                manufacturerCross.put(v.manufId, v.jdManufId);
            }
        }

        List<DwManufDim> allManufacturer = selectAllManufacturers(conn,
                dbSchemaDim, dwStoreDimId);
        Map<Integer, DwManufDim> allManufacturerMap = new HashMap<Integer, DwManufDim>();
        for (int i = 0; allManufacturer != null && i < allManufacturer.size(); i++) {
            DwManufDim item = allManufacturer.get(i);
            DwManufDim prev = allManufacturerMap.put(
                    item.manufId, item);
            if (prev != null) {
                throw new Exception(
                        "Was found more than 1 DwManufacturerDim for "
                                + item.manufId);
            }
        }
        List<DwManufDim> update = new ArrayList<DwManufDim>();
        boolean wasChanged;
        for (ManufDimUpdateItem v : manufacturerMain.values()) {
            wasChanged = false;
            DwManufDim dimData = allManufacturerMap
                    .get(v.manufId);
            ManufDimUpdateItem jdValue = manufacturerJD
                    .get(v.jdManufId);
            if (dimData == null) {
                dimData = new DwManufDim();
                dimData.manufId = v.manufId;
                dimData.manufName = v.manufName;
                if (jdValue == null) {
                    dimData.jdManufName = v.manufName;
                } else {
                    dimData.jdManufId = jdValue.manufId;
                    dimData.jdManufName = jdValue.manufName;
                }
                dimData.addBy = user;
                wasChanged = true;
            } else {
                if (dimData.manufName.equals(v.manufName) == false) {
                    dimData.manufName = v.manufName;
                    if(dimData.jdManufId == 0 && jdValue == null){
                      dimData.jdManufName = v.manufName;
                    }
                    wasChanged = true;
                }
                if (jdValue != null ){
                  if (dimData.jdManufId != jdValue.manufId) {
                    dimData.jdManufId = jdValue.manufId;
                    dimData.jdManufName = jdValue.manufName;
                    wasChanged = true;
                  }
                } else if (dimData.jdManufId != 0) {
                  dimData.jdManufId = 0;
                  dimData.jdManufName = v.manufName;
                  wasChanged = true;
                }
            }
            int storeDimId = storesCross.get(v.storeId);
            if (dimData.storeDimId != storeDimId) {
                dimData.storeDimId = storeDimId;
                wasChanged = true;
            }
            dimData.modBy = user;
            if (wasChanged == true) {
                update.add(dimData);
            }
        }
        for (DwManufDim dimData : update) {
logDebug("[DataWarehouseBean].updateDimManufacturer() ---> dimData.manufDimId= " + dimData.manufDimId);
            if (dimData.manufDimId > 0) {
                updateManufacturer(conn, dimData, dbSchemaDim);
            } else {
                insertManufacturer(conn, dimData, dbSchemaDim);
            }
        }
    }

    public static class ManufDimUpdateItem {
        int manufId;

        String manufName;

        int storeId;

        Integer jdManufId;
    }

    private static class DwManufDim {
        int storeDimId;

        int manufDimId;

        int manufId;

        String manufName;

        int jdManufId;

        String jdManufName;

        String addBy;

        Date addDate;

        String modBy;

        Date modDate;
    }

    private final List<ManufDimUpdateItem> getExistManufacturers(
            Connection conn, List<Integer> storeIds, int jdStoreId,
            String dbSchemaGeneral) throws Exception {
        List<ManufDimUpdateItem> result = new ArrayList<ManufDimUpdateItem>();
        StringBuilder storeIdsString = new StringBuilder("" + jdStoreId);
        for (int i : storeIds) {
            storeIdsString.append("," + i);
        }
        String sql = "SELECT t1.bus_entity_id  AS MANUF_ID, "
                + "       t1.short_desc     AS MANUF_NAME, "
                + "       t2.bus_entity2_id AS STORE_ID, "
                + "       t3.bus_entity2_id AS JD_MANUF_ID "
                + "FROM "
                + dbSchemaGeneral
                + ".CLW_BUS_ENTITY t1 "
                + "INNER JOIN "
                + dbSchemaGeneral
                + ".CLW_BUS_ENTITY_ASSOC t2 ON t1.bus_entity_id = t2.bus_entity1_id AND "
                + "       t2.bus_entity_assoc_cd = '"
                + RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE
                + "' AND "
                + "       t2.bus_entity2_id IN ("
                + storeIdsString.toString()
                + ") "
                + "LEFT OUTER JOIN "
                + dbSchemaGeneral
                + ".CLW_BUS_ENTITY_ASSOC t3 ON t1.bus_entity_id = t3.bus_entity1_id AND "
                + "       t3.bus_entity_assoc_cd = '"
                + RefCodeNames.BUS_ENTITY_ASSOC_CD.CROSS_STORE_MANUF_LINK + "'";
        logDebug("getExistManufacturers SQL:" + sql);
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                ManufDimUpdateItem v = new ManufDimUpdateItem();
                v.manufId = rs.getInt(1);
                v.manufName = rs.getString(2);
                v.storeId = rs.getInt(3);
                if (rs.getString(4) != null) {
                    v.jdManufId = rs.getInt(4);
                }
                result.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }
        return result;
    }

    public  List<DwManufDim> selectAllManufacturers(
            Connection pCon, String dbSchemaDim, int storeDimId) throws SQLException {
        String sql = "SELECT STORE_DIM_ID,MANUFACTURER_DIM_ID,MANUF_ID,MANUF_NAME,JD_MANUF_ID,JD_MANUF_NAME,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM "
                + dbSchemaDim + ".DW_MANUFACTURER_DIM WHERE STORE_DIM_ID =" + storeDimId ;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<DwManufDim> result = new ArrayList<DwManufDim>();
        while (rs.next()) {
            DwManufDim x = new DwManufDim();
            x.storeDimId = rs.getInt(1);
            x.manufDimId = rs.getInt(2);
            x.manufId = rs.getInt(3);
            x.manufName = rs.getString(4);
            x.jdManufId = rs.getInt(5);
            x.jdManufName = rs.getString(6);
            x.addBy = rs.getString(7);
            x.addDate = rs.getTimestamp(8);
            x.modBy = rs.getString(9);
            x.modDate = rs.getTimestamp(10);
            result.add(x);
        }
        rs.close();
        stmt.close();
        return result;
    }

    private static int updateManufacturer(Connection pCon,
            DwManufDim pData, String dbSchemaDim) throws SQLException {
        int n = 0;
        String sql = "UPDATE "
                + dbSchemaDim
                + ".DW_MANUFACTURER_DIM SET STORE_DIM_ID = ?,MANUF_ID = ?,MANUF_NAME = ?,JD_MANUF_ID = ?,JD_MANUF_NAME = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE MANUFACTURER_DIM_ID = ?";
        PreparedStatement pstmt = pCon.prepareStatement(sql);
        pData.modDate = new java.util.Date(System.currentTimeMillis());
        int i = 1;
        pstmt.setInt(i++, pData.storeDimId);
        pstmt.setInt(i++, pData.manufId);
        pstmt.setString(i++, pData.manufName);
//      pstmt.setInt(i++, pData.jdManufId);
        if (pData.jdManufId != 0){
          pstmt.setInt(i++, pData.jdManufId);
        } else {
          pstmt.setString(i++, "");
        }
        pstmt.setString(i++, pData.jdManufName);
        pstmt.setString(i++, pData.addBy);
        pstmt.setTimestamp(i++, DBAccess.toSQLTimestamp(pData.addDate));
        pstmt.setString(i++, pData.modBy);
        pstmt.setTimestamp(i++, DBAccess.toSQLTimestamp(pData.modDate));
        pstmt.setInt(i++, pData.manufDimId);
        n = pstmt.executeUpdate();
        pstmt.close();
        return n;
    }

    public  DwManufDim insertManufacturer(Connection pCon,
            DwManufDim pData, String dbSchemaDim) throws SQLException {
        String exceptionMessage = null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT " + dbSchemaDim
                + ".DW_MANUFACTURER_DIM_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.manufDimId = rs.getInt(1);
        stmt.close();
        String sql = "INSERT INTO "
                + dbSchemaDim
                + ".DW_MANUFACTURER_DIM (STORE_DIM_ID,MANUFACTURER_DIM_ID,MANUF_ID,MANUF_NAME,JD_MANUF_ID,JD_MANUF_NAME,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = pCon.prepareStatement(sql);
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.addDate = current;
        pData.modDate = current;
        pstmt.setInt(1, pData.storeDimId);
        pstmt.setInt(2, pData.manufDimId);
        pstmt.setInt(3, pData.manufId);
        pstmt.setString(4, pData.manufName);
        if (pData.jdManufId != 0){
          pstmt.setInt(5, pData.jdManufId);
        } else {
          pstmt.setString(5, "");
        }
        pstmt.setString(6, pData.jdManufName);
        pstmt.setString(7, pData.addBy);
        pstmt.setTimestamp(8, DBAccess.toSQLTimestamp(pData.addDate));
        pstmt.setString(9, pData.modBy);
        pstmt.setTimestamp(10, DBAccess.toSQLTimestamp(pData.modDate));
        try {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            pData.manufDimId = 0;
            exceptionMessage = e.getMessage();
        } finally {
            pstmt.close();
        }
        if (exceptionMessage != null) {
            throw new SQLException(exceptionMessage);
        }
        return pData;
    }

    /**
     * Update Dim Distributors.
     *
     * @param conn
     * @param dbSchemaGeneral
     * @param dbSchemaDim
     * @param storeIds
     * @param jdStoreId
     * @param user
     * @throws Exception
     */
    private void updateDimDist(Connection conn, String dbSchemaGeneral,
            String dbSchemaDim, List<Integer> storeIds, int jdStoreId,
            String user, int dwStoreDimId) throws Exception {
        logDebug("Was got storeIds:[" + storeIds + "] jdStoreId:" + jdStoreId);
        Map<Integer, Integer>  storesCross = getCrossStore2StoreDim(conn, dbSchemaDim);
        checkCrossStores(storesCross, storeIds);
        List<DistDimUpdateItem> buffer = getExistDistributors(conn, storeIds,
                jdStoreId, dbSchemaGeneral);
        logDebug("Was found  " + buffer.size() + " distributors.");
        Map<Integer, DistDimUpdateItem> distMain = new HashMap<Integer, DistDimUpdateItem>();
        Map<Integer, DistDimUpdateItem> distJD = new HashMap<Integer, DistDimUpdateItem>();
        Map<Integer, Integer> distCross = new HashMap<Integer, Integer>();
        for (DistDimUpdateItem v : buffer) {
            if (v.storeId == jdStoreId) {
                distJD.put(v.distId, v);
            } else {
                distMain.put(v.distId, v);
            }
            if (v.jdDistId != null) {
                distCross.put(v.distId, v.jdDistId);
            }
        }
        List<DwDistributorDim> allDist = selectAllDistributorDim(conn, dbSchemaDim, dwStoreDimId);
        Map<Integer, DwDistributorDim> allDistMap = new HashMap<Integer, DwDistributorDim>();
        for (int i = 0; allDist != null && i < allDist.size(); i++) {
            DwDistributorDim item = (DwDistributorDim) allDist.get(i);
            DwDistributorDim prev = allDistMap.put(item.distId, item);
            if (prev != null) {
                throw new Exception(
                        "Was found more than 1 DwDistributorDim for "
                                + item.distId);
            }
        }
        List<DwDistributorDim> update = new ArrayList<DwDistributorDim>();
        boolean wasChanged;
        for (DistDimUpdateItem v : distMain.values()) {
            wasChanged = false;
            DwDistributorDim dimData = allDistMap.get(v.distId);
            DistDimUpdateItem jdValue = distJD.get(v.jdDistId);
            if (dimData == null) {
                dimData = new DwDistributorDim();
                dimData.distId = v.distId;
                dimData.distName = v.distName;
                if (jdValue == null) {
                    dimData.jdDistName = v.distName;
                } else {
                    dimData.jdDistId = jdValue.distId;
                    dimData.jdDistName = jdValue.distName;
                }
                dimData.addBy = user;
                wasChanged = true;
            } else {
                if (dimData.distName.equals(v.distName) == false) {
                    dimData.distName = v.distName;
                    if(dimData.jdDistId == 0 && jdValue == null){
                      dimData.jdDistName = v.distName;
                    }
                    wasChanged = true;
                }
                if (jdValue != null ){
                   if (dimData.jdDistId != jdValue.distId) {
                     dimData.jdDistId= jdValue.distId;
                     dimData.jdDistName = jdValue.distName;
                     wasChanged = true;
                   }
                 } else if (dimData.jdDistId != 0) {
                   dimData.jdDistId = 0;
                   dimData.jdDistName = v.distName;
                   wasChanged = true;
                 }
            }
            int storeDimId = storesCross.get(v.storeId);
            if (dimData.storeDimId != storeDimId) {
                dimData.storeDimId = storeDimId;
                wasChanged = true;
            }
            dimData.modBy = user;
            if (wasChanged == true) {
                update.add(dimData);
            }
        }
        for (int i = 0; i < update.size(); i++) {
            DwDistributorDim dimData = (DwDistributorDim) update.get(i);
            logDebug("[DataWarehouseBean].updateDimDistributor() ---> dimData.distributorDimId= " + dimData.distributorDimId);
            if (dimData.distributorDimId > 0) {
                updateDistributorDim(conn, dimData, dbSchemaDim);
            } else {
                insertDistributorDim(conn, dimData, dbSchemaDim);
            }
        }
    }

    public static class DistDimUpdateItem {
        int distId;

        String distName;

        int storeId;

        Integer jdDistId;
    }

    private static class DwDistributorDim {
        int storeDimId;

        int distributorDimId;

        int distId;

        String distName;

        int jdDistId;
  //      Integer jdDistId;

        String jdDistName;

        String addBy;

        Date addDate;

        String modBy;

        Date modDate;
    }


    private final List<DistDimUpdateItem> getExistDistributors(Connection conn,
            List<Integer> storeIds, int jdStoreId, String dbSchemaGeneral)
            throws Exception {
        List<DistDimUpdateItem> result = new ArrayList<DistDimUpdateItem>();
        StringBuilder storeIdsString = new StringBuilder("" + jdStoreId);
        for (int i : storeIds) {
            storeIdsString.append("," + i);
        }
        String sql = "SELECT t1.bus_entity_id  AS DIST_ID, "
                + "       t1.short_desc     AS DIST_NAME, "
                + "       t2.bus_entity2_id AS STORE_ID, "
                + "       t3.bus_entity2_id AS JD_DIST_ID "
                + "FROM "
                + dbSchemaGeneral
                + ".CLW_BUS_ENTITY t1 "
                + "INNER JOIN "
                + dbSchemaGeneral
                + ".CLW_BUS_ENTITY_ASSOC t2 ON t1.bus_entity_id = t2.bus_entity1_id AND "
                + "       t2.bus_entity_assoc_cd = '"
                + RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE
                + "' AND "
                + "       t2.bus_entity2_id IN ("
                + storeIdsString.toString()
                + ") "
                + "LEFT OUTER JOIN "
                + dbSchemaGeneral
                + ".CLW_BUS_ENTITY_ASSOC t3 ON t1.bus_entity_id = t3.bus_entity1_id AND "
                + "       t3.bus_entity_assoc_cd = '"
                + RefCodeNames.BUS_ENTITY_ASSOC_CD.CROSS_STORE_DIST_LINK + "'";
        logDebug("getExistDistributors SQL:" + sql);

        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                DistDimUpdateItem v = new DistDimUpdateItem();
                v.distId = rs.getInt(1);
                v.distName = rs.getString(2);
                v.storeId = rs.getInt(3);
                if (rs.getString(4) != null) {
                    v.jdDistId = rs.getInt(4);
                }
                result.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }
        return result;
    }

    public  List<DwDistributorDim> selectAllDistributorDim(Connection pCon,
            String dbSchemaDim, int storeDimId) throws SQLException {
        String sql = "SELECT STORE_DIM_ID,DISTRIBUTOR_DIM_ID,DIST_ID,DIST_NAME,JD_DIST_ID,JD_DIST_NAME,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM "
                + dbSchemaDim + ".DW_DISTRIBUTOR_DIM WHERE STORE_DIM_ID =" + storeDimId;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<DwDistributorDim> result = new ArrayList<DwDistributorDim>();
        while (rs.next()) {
            DwDistributorDim x = new DwDistributorDim();
            x.storeDimId = rs.getInt(1);
            x.distributorDimId = rs.getInt(2);
            x.distId = rs.getInt(3);
            x.distName = rs.getString(4);
            x.jdDistId = rs.getInt(5);
            x.jdDistName = rs.getString(6);
            x.addBy = rs.getString(7);
            x.addDate = rs.getTimestamp(8);
            x.modBy = rs.getString(9);
            x.modDate = rs.getTimestamp(10);
            result.add(x);
        }
        rs.close();
        stmt.close();
        return result;
    }

    private static int updateDistributorDim(Connection pCon, DwDistributorDim pData,
            String dbSchemaDim) throws SQLException {
        int n = 0;
        String sql = "UPDATE "
                + dbSchemaDim
                + ".DW_DISTRIBUTOR_DIM SET STORE_DIM_ID = ?,DIST_ID = ?,DIST_NAME = ?,JD_DIST_ID = ?,JD_DIST_NAME = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE DISTRIBUTOR_DIM_ID = ?";
        PreparedStatement pstmt = pCon.prepareStatement(sql);
        pData.modDate = new java.util.Date(System.currentTimeMillis());
        int i = 1;
        pstmt.setInt(i++, pData.storeDimId);
        pstmt.setInt(i++, pData.distId);
        pstmt.setString(i++, pData.distName);
        if (pData.jdDistId!=0){
          pstmt.setInt(i++, pData.jdDistId);
        } else {
          pstmt.setString(i++, "");
        }
//        pstmt.setInt(i++, pData.jdDistId);

        pstmt.setString(i++, pData.jdDistName);
        pstmt.setString(i++, pData.addBy);
        pstmt.setTimestamp(i++, DBAccess.toSQLTimestamp(pData.addDate));
        pstmt.setString(i++, pData.modBy);
        pstmt.setTimestamp(i++, DBAccess.toSQLTimestamp(pData.modDate));
        pstmt.setInt(i++, pData.distributorDimId);
        n = pstmt.executeUpdate();
        pstmt.close();
        return n;
    }

    public  DwDistributorDim insertDistributorDim(Connection pCon,
            DwDistributorDim pData, String dbSchemaDim) throws SQLException {
        String exceptionMessage = null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT " + dbSchemaDim
                + ".DW_DISTRIBUTOR_DIM_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.distributorDimId = rs.getInt(1);
        stmt.close();
        String sql = "INSERT INTO "
                + dbSchemaDim
                + ".DW_DISTRIBUTOR_DIM (STORE_DIM_ID,DISTRIBUTOR_DIM_ID,DIST_ID,DIST_NAME,JD_DIST_ID,JD_DIST_NAME,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = pCon.prepareStatement(sql);
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.addDate = current;
        pData.modDate = current;
        pstmt.setInt(1, pData.storeDimId);
        pstmt.setInt(2, pData.distributorDimId);
        pstmt.setInt(3, pData.distId);
        pstmt.setString(4, pData.distName);
        if (pData.jdDistId!=0){
          pstmt.setInt(5, pData.jdDistId);
        } else {
          pstmt.setString(5, "");
        }
        pstmt.setString(6, pData.jdDistName);
        pstmt.setString(7, pData.addBy);
        pstmt.setTimestamp(8, DBAccess.toSQLTimestamp(pData.addDate));
        pstmt.setString(9, pData.modBy);
        pstmt.setTimestamp(10, DBAccess.toSQLTimestamp(pData.modDate));
        try {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            pData.distributorDimId = 0;
            exceptionMessage = e.getMessage();
        } finally {
            pstmt.close();
        }
        if (exceptionMessage != null) {
            throw new SQLException(exceptionMessage);
        }
        return pData;
    }

    private void checkCrossStores(Map<Integer, Integer> storesCross,
            List<Integer> storeIds) throws Exception {
        StringBuilder storeCrossError = new StringBuilder();
        for (Integer i : storeIds) {
            if (storesCross.get(i) == null) {
                if (storeCrossError.length() > 0) storeCrossError.append(',');
                storeCrossError.append(i);
            }
        }
        if (storeCrossError.length() > 0) {
            throw new Exception("Doesn't exist Dim Stores for Stores:" + storeCrossError);
        }
    }

    public  Map<Integer, Integer> getCrossStore2StoreDim(Connection pCon,
            String dbSchemaDim) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        StringBuilder duplicateError = new StringBuilder();
        try {
            stmt = pCon.createStatement();
            rs = stmt.executeQuery("SELECT DISTINCT STORE_DIM_ID, STORE_ID FROM " + dbSchemaDim
                    + ".DW_STORE_DIM");
            while (rs.next()) {
                int storeId = rs.getInt(2);
                int dimStoreId = rs.getInt(1);
                Integer oldStoreDim = result.put(storeId, dimStoreId);
                if (oldStoreDim != null) {
                    if (duplicateError.length() > 0) duplicateError.append('\n');
                    duplicateError.append(storeId + ":" + dimStoreId + ","
                            + oldStoreDim);
                }
            }
            if (duplicateError.length() > 0) {
                throw new RuntimeException(
                        "Was found next duplicate stores (format <Store>:"
                                + "<DimStoreOne>,<DimStoreTwo>):\n"
                                + duplicateError);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * Construct full table name with using the namespace and table name.
     */
    private String getFullTableName(String namespace, String table, String pSuffix)  {
         String fullTableName = table.toUpperCase();
         String suffix = (Utility.isSet(pSuffix)) ? pSuffix :"";
         if (Utility.isSet(namespace)) {
           if (Utility.isSet(dbLink) && table.toUpperCase().startsWith("CLW_")){
             fullTableName =  namespace.toUpperCase() + "." + fullTableName + "@" + dbLink.toUpperCase();
           } else {
             fullTableName = namespace.toUpperCase() + "." + fullTableName;
           }
         }
         return fullTableName + suffix;
   }
   private  String getFullTableName(String namespace, String table)  {
        return getFullTableName( namespace, table, null);
   }

    /**
     *  Checking of existence of table in the schema.
     */
    private  boolean checkDbTableOnExistence(Connection connection,
        String namespace, String table) throws SQLException {

        String sql = null;
        PreparedStatement stmt = null;
        ResultSet resSet = null;
        boolean isExistTable = false;

        if (namespace == null || namespace.length() == 0) {
            sql =
                "SELECT COUNT(*) TABLE_COUNT FROM USER_TABLES WHERE upper(TABLE_NAME) = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, table.toUpperCase());
        }
        else {
            sql =
                "SELECT COUNT(*) FROM ALL_TABLES WHERE upper(OWNER) = ? AND upper(TABLE_NAME) = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, namespace.toUpperCase());
            stmt.setString(2, table.toUpperCase());
        }
        resSet = stmt.executeQuery();
        if (resSet.next()) {
            isExistTable = (resSet.getInt(1) > 0);
        }
        resSet.close();
        stmt.close();
        return isExistTable;
    }

    /**
     * Fills the DW_ACCOUNT_DIM and DW_SITE_DIM table in the "Data Warehouse".
     * Use the parameter 'dbClwNamespace' to define the schema for 'CLW'-tables with data.
     * Use the parameter 'dbDwNamespace' to define the schema for the tables from 'Data Warehouse'.
     * The parameter 'storeId' - is the identifier of store (for example 'JanPak') to explore.
     * The parameter 'jdStoreId' - is the identifier of 'JohnsonDiversey' store.
     */
    public void fillDwCustomerDimension(String userName,
        String dbClwNamespace, String dbDwNamespace, int exploredStoreId,
        int linkedStoreId) throws SQLException, RemoteException,Exception {

        Connection connection = null;
        final String tblDwStoreDim = getFullTableName(dbDwNamespace,DataWarehouse.TBL_DW_STORE_DIM);
        try {
            connection = getConnection();
            int dwStoreDimId = getStoreDimId(connection, exploredStoreId, dbClwNamespace, tblDwStoreDim);
            fillDwAccountDimension(connection, userName, dbClwNamespace, dbDwNamespace, exploredStoreId, linkedStoreId, dwStoreDimId);
            fillDwSiteDimension(connection, userName, dbClwNamespace, dbDwNamespace, exploredStoreId,  dwStoreDimId);
        }
        catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        catch (NamingException ex) {
            throw new RemoteException(ex.getMessage());
        }
        catch (DataNotFoundException ex) {
          ex.printStackTrace();
          throw new DataNotFoundException(ex.getMessage());
        }
        finally {
            closeConnection(connection);
        }
    }

    /**
     * Fills the DW_ACCOUNT_DIM table in the "Data Warehouse".
     * Use the parameter 'dbClwNamespace' to define the schema for 'CLW'-tables with data.
     * Use the parameter 'dbDwNamespace' to define the schema for the tables from 'Data Warehouse'.
     * The parameter 'storeId' - is the identifier of store (for example 'JanPak') to explore.
     * The parameter 'jdStoreId' - is the identifier of 'JohnsonDiversey' store.
     */
    private  StringBuffer fillDwAccountDimension(Connection connection,
        String userName, String dbClwNamespace, String dbDwNamespace,
        int exploredStoreId, int linkedStoreId, int dwStoreDimId) throws SQLException, Exception {

        if (connection == null) {
            throw new SQLException("Connection is null!");
        }

        final String tblDwStore = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_STORE_DIM);
        final String tblDwAccount = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_ACCOUNT_DIM);
        final String tblDwAccountTemp = getFullTableName(dwTempUser, TBL_DW_ACCOUNT_DIM_TEMP, tempSuffix);
        final String tblDwAccountSeq = getFullTableName(dbDwNamespace, TBL_DW_ACCOUNT_DIM_SEQ);

        final String tblClwAddress = getFullTableName(dbClwNamespace, TBL_CLW_ADDRESS);
        final String tblClwProperty = getFullTableName(dbClwNamespace, TBL_CLW_PROPERTY);
        final String tblClwBusEntity = getFullTableName(dbClwNamespace, TBL_CLW_BUS_ENTITY);
        final String tblClwBusEntityAssoc = getFullTableName(dbClwNamespace, TBL_CLW_BUS_ENTITY_ASSOC);

        final String action = "< DataWarehouseBean >[" + tblDwAccount + " Creation] ";

        String sql = null;
        PreparedStatement stmt = null;
        boolean isExistTempTable = false;

        ////////////////////////////////////////////////////////////////////////
        /// Creation of temporal table 'TBL_DW_ACCOUNT_DIM_TEMP'.
        /// The table 'TBL_DW_ACCOUNT_DIM_TEMP' contains data
        /// to update table 'TBL_DW_ACCOUNT_DIM' from Data Warehouse.
        ////////////////////////////////////////////////////////////////////////

/*        /// Checking of existence of table 'TBL_DW_ACCOUNT_DIM_TEMP'
        try {
            isExistTempTable = checkDbTableOnExistence(connection,
                dbDwNamespace, TBL_DW_ACCOUNT_DIM_TEMP);
        }
        catch (SQLException ex) {
            String msg = "An error occurred at checking existence of table " +
                tblDwAccountTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
*/
        /// Deleting of already existing table 'TBL_DW_ACCOUNT_DIM_TEMP'
        if (isExistTempTable) {
            try {
                sql = "DROP TABLE " + tblDwAccountTemp + " PURGE";
                stmt = connection.prepareStatement(sql);
                stmt.executeUpdate();
                stmt.close();
            }
            catch (SQLException ex) {
                String msg = "An error occurred at deleting of table " +
                    tblDwAccountTemp + ". " + ex.getMessage();
                throw new SQLException("^clw^"+msg+ "^clw^");
            }
        }

        /// Creation of the table 'TBL_DW_ACCOUNT_DIM_TEMP'
        try {
            sql =
                "CREATE TABLE " + tblDwAccountTemp + " " +
                "( " +
                    "STORE_DIM_ID               NUMBER NULL, " +
                    "ACCOUNT_DIM_ID             NUMBER NULL, " +

                    "STORE_ID                   NUMBER NOT NULL, " +

                    "ACCOUNT_ID                 NUMBER NULL, " +
                    "ACCOUNT_NAME               VARCHAR2(255) NULL, " +
                    "ACCOUNT_TYPE_CD            VARCHAR2(30) NULL, " +
                    "ACCOUNT_STATUS_CD          VARCHAR2(30) NULL, " +

                    "ACCOUNT_NUM                VARCHAR2(30) NULL, " +
                    "ACCOUNT_STREET_ADDRESS     VARCHAR2(255) NULL, " +
                    "ACCOUNT_CITY               VARCHAR2(30) NULL, " +
                    "ACCOUNT_STATE              VARCHAR2(20) NULL, " +
                    "BRANCH_NAME                VARCHAR2(20) NULL, " +
                    "MARKET                     VARCHAR2(50) NULL, " +

                    "JD_STORE_ID                NUMBER NULL, " +

                    "JD_ACCOUNT_ID              NUMBER NULL, " +
                    "JD_ACCOUNT_NAME            VARCHAR2(255) NULL, " +
                    "JD_ACCOUNT_TYPE_CD         VARCHAR2(30) NULL, " +
                    "JD_ACCOUNT_STATUS_CD       VARCHAR2(30) NULL, " +

                    "JD_ACCOUNT_STREET_ADDRESS  VARCHAR2(255) NULL, " +
                    "JD_ACCOUNT_CITY            VARCHAR2(30) NULL, " +
                    "JD_ACCOUNT_STATE           VARCHAR2(20) NULL, " +

                    "JD_REGION_ID               NUMBER NULL, " +
                    "JD_REGION                  VARCHAR2(30) NULL, " +
                    "JD_MARKET                  VARCHAR2(50) NULL, " +
                    "UPDATE_TYPE                VARCHAR2(30) NULL " +
                ")";

            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at creation of table " +
                tblDwAccountTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        ////////////////////////////////////////////////////////////////////////
        /// Filling of temporal 'TBL_DW_ACCOUNT_DIM_TEMP'-table by data.
        ////////////////////////////////////////////////////////////////////////

        /// Insert of data from store 'JanPak' into temporal table.
        try {
            sql =
                "INSERT INTO " + tblDwAccountTemp + " " +
                "SELECT " +
                    dwStoreDimId +", \n" +
                    "NULL, " +

                    "stor.BUS_ENTITY_ID        STORE_ID, " +

                    "acc.BUS_ENTITY_ID         ACCOUNT_ID, " +
                    "acc.SHORT_DESC            ACCOUNT_NAME, " +
                    "acc.BUS_ENTITY_TYPE_CD    ACCOUNT_TYPE_CD, " +
                    "acc.BUS_ENTITY_STATUS_CD  ACCOUNT_STATUS_CD, " +

                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +

                    linkedStoreId +", \n" + //JD_STORE_ID

                    "NULL, " +              //JD_ACCOUNT_ID
                    "NULL, " +              //JD_ACCOUNT_NAME
                    "NULL, " +              //JD_ACCOUNT_TYPE_CD
                    "NULL, " +              //JD_ACCOUNT_STATUS_CD

                    "NULL, " +
                    "NULL, " +
                    "NULL, " +

                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL " +
                "FROM  " +
                    tblClwBusEntity + " stor INNER JOIN " +
                    "( " +
                        tblClwBusEntityAssoc + " r1 INNER JOIN  " +
                        "( " +
                            tblClwBusEntity + " acc " +
                        ") ON r1.BUS_ENTITY1_ID = acc.BUS_ENTITY_ID AND r1.BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' " +
                    ") ON stor.BUS_ENTITY_ID = r1.BUS_ENTITY2_ID AND r1.BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' " +
                "WHERE  " +
                    "stor.BUS_ENTITY_ID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, exploredStoreId);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at insertion of records into table " +
                tblDwAccountTemp + " for store " + exploredStoreId + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

 /*       /// Fill the field 'STORE_DIM_ID' in the table 'TBL_DW_ACCOUNT_DIM_TEMP'.
        try {
            sql =
                "UPDATE " + tblDwAccountTemp + " tmp SET tmp.STORE_DIM_ID = " +
                "( " +
                    "SELECT " +
                        "dw.STORE_DIM_ID " +
                    "FROM " +
                        tblDwStore + " dw " +
                    "WHERE " +
                        "tmp.STORE_ID = dw.STORE_ID " +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of field 'STORE_DIM_ID' " +
                "in the table " + tblDwAccountTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
*/
        /// Fill the field 'ACCOUNT_DIM_ID' in the table 'TBL_DW_ACCOUNT_DIM_TEMP'.
        try {
            sql =
                "UPDATE " + tblDwAccountTemp + " tmp SET tmp.ACCOUNT_DIM_ID = " +
                "( " +
                    "SELECT " +
                        "dw.ACCOUNT_DIM_ID " +
                    "FROM " +
                        tblDwAccount + " dw " +
                    "WHERE " +
                        "tmp.ACCOUNT_ID = dw.ACCOUNT_ID AND " +
                        "dw.STORE_DIM_ID =" + dwStoreDimId +

                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of field 'ACCOUNT_DIM_ID' " +
                "in the table " + tblDwAccountTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Fill the fields with address information for the account in the table 'TBL_DW_ACCOUNT_DIM_TEMP'.
        try {
            sql =
                "UPDATE " + tblDwAccountTemp + " tmp " +
                "SET " +
                "( " +
                    "tmp.ACCOUNT_CITY, " +
                    "tmp.ACCOUNT_STATE, " +
                    "tmp.ACCOUNT_STREET_ADDRESS " +
                ") " +
                "= " +
                "( " +
                    "SELECT " +
                        "addr.CITY              ACCOUNT_CITY, " +
                        "addr.STATE_PROVINCE_CD ACCOUNT_STATE, " +
                        "addr.NAME1             ACCOUNT_STREET_ADDRESS " +
                    "FROM " +
                        tblClwAddress + " addr " +
                    "WHERE " +
                        "tmp.ACCOUNT_ID = addr.BUS_ENTITY_ID " +
                        "AND addr.ADDRESS_TYPE_CD = 'PRIMARY CONTACT' " +
                        "AND addr.PRIMARY_IND = 1 " +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at filling the fields with address information for the account " +
                "in the table " + tblDwAccountTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Fill the field 'ACCOUNT_NUM' in the table 'TBL_DW_ACCOUNT_DIM_TEMP'.
        try {
            sql =
                "UPDATE " + tblDwAccountTemp + " tmp SET tmp.ACCOUNT_NUM = " +
                "( " +
                    "SELECT " +
                        "prop.CLW_VALUE " +
                    "FROM " +
                        tblClwProperty + " prop " +
                    "WHERE " +
                        "prop.BUS_ENTITY_ID = tmp.ACCOUNT_ID AND " +
                        "prop.SHORT_DESC = 'DIST_ACCT_REF_NUM' " +
                        " AND prop.CLW_VALUE is not NULL " +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of field 'ACCOUNT_NUM' " +
                "in the table " + tblDwAccountTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Fill the field 'MARKET' in the table 'TBL_DW_ACCOUNT_DIM_TEMP'.
        try {
            sql =
                "UPDATE " + tblDwAccountTemp + " tmp SET tmp.MARKET = " +
                "( " +
                    "SELECT " +
                        "prop.CLW_VALUE " +
                    "FROM " +
                        tblClwProperty + " prop " +
                    "WHERE " +
                        "prop.BUS_ENTITY_ID = tmp.ACCOUNT_ID AND " +
                        "prop.SHORT_DESC = 'ACCOUNT TYPE' " +
                        " AND prop.CLW_VALUE is not NULL " +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of field 'MARKET' " +
                "in the table " + tblDwAccountTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

/*        /// Filling of data for store 'JohnsonDiversey' in the table 'TBL_DW_ACCOUNT_DIM_TEMP'.
        /// Update the fields which describe the store 'JohnsonDiversey'.
        try {
            sql =
                "UPDATE " + tblDwAccountTemp + " SET JD_STORE_ID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, linkedStoreId);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of 'JD_STORE_ID'-field in the table " +
                tblDwAccountTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
*/
        /// Updating of the 'JD_ACCOUNT'-information in the table 'TBL_DW_ACCOUNT_DIM_TEMP'.
        try {
            sql =
                "UPDATE " + tblDwAccountTemp+ " tmp SET " +
                "( " +
                    "tmp.JD_ACCOUNT_ID, " +
                    "tmp.JD_ACCOUNT_NAME, " +
                    "tmp.JD_ACCOUNT_TYPE_CD, " +
                    "tmp.JD_ACCOUNT_STATUS_CD " +
                ") " +
                "= " +
                "( " +
                    "SELECT " +
                        "acc.BUS_ENTITY_ID, " +
                        "acc.SHORT_DESC, " +
                        "acc.BUS_ENTITY_TYPE_CD, " +
                        "acc.BUS_ENTITY_STATUS_CD " +
                    "FROM " +
                        tblClwBusEntity + " acc, " +
                        tblClwBusEntityAssoc + " r1, "  +
                        tblClwBusEntityAssoc + " r2 " +
                    "WHERE " +
                        "r1.BUS_ENTITY2_ID = ? AND " +
                        "r1.BUS_ENTITY1_ID = acc.BUS_ENTITY_ID AND " +
                        "r1.BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' AND " +
                        "r2.BUS_ENTITY1_ID = acc.BUS_ENTITY_ID AND " +
                        "r2.BUS_ENTITY2_ID = tmp.ACCOUNT_ID AND " +
                        "r2.BUS_ENTITY_ASSOC_CD = 'CROSS_STORE_ACCOUNT_LINK' " +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, linkedStoreId);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of 'JD_ACCOUNT'-information in the table " +
                tblDwAccountTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Fill the fields with address information for the 'JD'-account in the table 'TBL_DW_ACCOUNT_DIM_TEMP'.
        try {
            sql =
                "UPDATE " + tblDwAccountTemp + " tmp " +
                "SET " +
                "( " +
                    "tmp.JD_ACCOUNT_CITY, " +
                    "tmp.JD_ACCOUNT_STATE, " +
                    "tmp.JD_ACCOUNT_STREET_ADDRESS " +
                ") " +
                "= " +
                "( " +
                    "SELECT " +
                        "addr.CITY, " +
                        "addr.STATE_PROVINCE_CD, " +
                        "addr.ADDRESS1 " +
                    "FROM " +
                        tblClwAddress + " addr " +
                    "WHERE " +
                        "tmp.JD_ACCOUNT_ID = addr.BUS_ENTITY_ID "+
                        "AND addr.ADDRESS_TYPE_CD = 'PRIMARY CONTACT' "+
                        "AND addr.PRIMARY_IND = 1 " +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at filling the fields with address information for the 'JD' account " +
                "in the table " + tblDwAccountTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Fill the field 'JD_MARKET' in the table 'TBL_DW_ACCOUNT_DIM_TEMP'.
        try {
            sql =
                "UPDATE " + tblDwAccountTemp + " tmp SET tmp.JD_MARKET = " +
                "( " +
                    "SELECT " +
                        "prop.CLW_VALUE " +
                    "FROM " +
                        tblClwProperty + " prop " +
                    "WHERE " +
                        "prop.BUS_ENTITY_ID = tmp.JD_ACCOUNT_ID AND " +
                        "prop.SHORT_DESC = 'ACCOUNT TYPE' " +
                        " AND prop.CLW_VALUE is not NULL " +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of field 'JD_MARKET' " +
                "in the table " + tblDwAccountTemp + " by values from table " +
                tblClwProperty + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

/*        /// Updating of field 'JD_MARKET' in table 'TBL_DW_ACCOUNT_DIM_TEMP'.
        try {
            sql =
                "UPDATE " + tblDwAccountTemp + " SET JD_MARKET = MARKET WHERE JD_MARKET IS NULL";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of null 'JD_MARKET'-field " +
                "by values from 'MARKET' field in the table " + tblDwAccountTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
*/
        /// Updating of field 'JD_...' in table 'TBL_DW_ACCOUNT_DIM_TEMP' if JD_ACCOUNT_ID is null .
        try {

            sql =
                "UPDATE " + tblDwAccountTemp + " \n"+
                " SET \n" +
                "   JD_ACCOUNT_NAME    = ACCOUNT_NAME, \n" +
                "   JD_ACCOUNT_TYPE_CD = ACCOUNT_TYPE_CD, \n" +
                "   JD_ACCOUNT_STATUS_CD = ACCOUNT_STATUS_CD, \n" +
                "   JD_ACCOUNT_CITY = ACCOUNT_CITY, \n" +
                "   JD_ACCOUNT_STATE = ACCOUNT_STATE, \n" +
                "   JD_ACCOUNT_STREET_ADDRESS = ACCOUNT_STREET_ADDRESS, \n" +
                "   JD_MARKET = MARKET \n" +
//                " WHERE JD_ACCOUNT_NAME IS NULL";
                " WHERE JD_ACCOUNT_ID IS NULL";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of 'JD_...' fields if they are NULL in the table " +
                tblDwAccountTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }


        ////////////////////////////////////////////////////////////////////////
        /// Updating of data in table 'TBL_DW_ACCOUNT_DIM'.
        /// The data from table 'TBL_DW_ACCOUNT_DIM_TEMP' will be used
        /// to update data in the table 'TBL_DW_ACCOUNT_DIM'.
        ////////////////////////////////////////////////////////////////////////

        /// Updating of the field UPDATE_TYPE in table 'TBL_DW_ACCOUNT_DIM_TEMP'.
        /// Set the value of field 'UPDATE_TYPE' in table 'TBL_DW_ACCOUNT_DIM_TEMP' into 'TO_UPDATE' for
        /// records which have the analogues in table 'TBL_DW_ACCOUNT_DIM' and which has been changed.
        try {
            sql =
                "UPDATE " + tblDwAccountTemp + " tmp " +
                "SET tmp.UPDATE_TYPE = " +
                "( " +
                    "SELECT " +
                        "'TO_UPDATE' " +
                    "FROM " +
                        tblDwAccount + " dw " +
                    "WHERE " +
                        "dw.ACCOUNT_DIM_ID = tmp.ACCOUNT_DIM_ID AND " +
                        "( " +
                            "dw.ACCOUNT_NAME <> tmp.ACCOUNT_NAME OR " +
                            "(dw.ACCOUNT_NAME IS NULL AND tmp.ACCOUNT_NAME IS NOT NULL) OR " +
                            "(dw.ACCOUNT_NAME IS NOT NULL AND tmp.ACCOUNT_NAME IS NULL) OR " +

                            "dw.ACCOUNT_NUM <> tmp.ACCOUNT_NUM OR " +
                            "(dw.ACCOUNT_NUM IS NULL AND tmp.ACCOUNT_NUM IS NOT NULL) OR " +
                            "(dw.ACCOUNT_NUM IS NOT NULL AND tmp.ACCOUNT_NUM IS NULL) OR " +

                            "dw.ACCOUNT_STREET_ADDRESS <> tmp.ACCOUNT_STREET_ADDRESS OR " +
                            "(dw.ACCOUNT_STREET_ADDRESS IS NULL AND tmp.ACCOUNT_STREET_ADDRESS IS NOT NULL) OR " +
                            "(dw.ACCOUNT_STREET_ADDRESS IS NOT NULL AND tmp.ACCOUNT_STREET_ADDRESS IS NULL) OR " +

                            "dw.ACCOUNT_CITY <> tmp.ACCOUNT_CITY OR " +
                            "(dw.ACCOUNT_CITY IS NULL AND tmp.ACCOUNT_CITY IS NOT NULL) OR " +
                            "(dw.ACCOUNT_CITY IS NOT NULL AND tmp.ACCOUNT_CITY IS NULL) OR " +

                            "dw.ACCOUNT_STATE <> tmp.ACCOUNT_STATE OR " +
                            "(dw.ACCOUNT_STATE IS NULL AND tmp.ACCOUNT_STATE IS NOT NULL) OR " +
                            "(dw.ACCOUNT_STATE IS NOT NULL AND tmp.ACCOUNT_STATE IS NULL) OR " +

                            "dw.ACCOUNT_STATUS_CD <> tmp.ACCOUNT_STATUS_CD OR " +
                            "(dw.ACCOUNT_STATUS_CD IS NULL AND tmp.ACCOUNT_STATUS_CD IS NOT NULL) OR " +
                            "(dw.ACCOUNT_STATUS_CD IS NOT NULL AND tmp.ACCOUNT_STATUS_CD IS NULL) OR " +

                            "dw.BRANCH_NAME <> tmp.BRANCH_NAME OR " +
                            "(dw.BRANCH_NAME IS NULL AND tmp.BRANCH_NAME IS NOT NULL) OR " +
                            "(dw.BRANCH_NAME IS NOT NULL AND tmp.BRANCH_NAME IS NULL) OR " +

                            "dw.MARKET <> tmp.MARKET OR " +
                            "(dw.MARKET IS NULL AND tmp.MARKET IS NOT NULL) OR " +
                            "(dw.MARKET IS NOT NULL AND tmp.MARKET IS NULL) OR " +

                            "dw.JD_ACCOUNT_NAME <> tmp.JD_ACCOUNT_NAME OR " +
                            "(dw.JD_ACCOUNT_NAME IS NULL AND tmp.JD_ACCOUNT_NAME IS NOT NULL) OR " +
                            "(dw.JD_ACCOUNT_NAME IS NOT NULL AND tmp.JD_ACCOUNT_NAME IS NULL) OR " +

                            "dw.JD_ACCOUNT_STREET_ADDRESS <> tmp.JD_ACCOUNT_STREET_ADDRESS OR " +
                            "(dw.JD_ACCOUNT_STREET_ADDRESS IS NULL AND tmp.JD_ACCOUNT_STREET_ADDRESS IS NOT NULL) OR " +
                            "(dw.JD_ACCOUNT_STREET_ADDRESS IS NOT NULL AND tmp.JD_ACCOUNT_STREET_ADDRESS IS NULL) OR " +

                            "dw.JD_ACCOUNT_CITY <> tmp.JD_ACCOUNT_CITY OR " +
                            "(dw.JD_ACCOUNT_CITY IS NULL AND tmp.JD_ACCOUNT_CITY IS NOT NULL) OR " +
                            "(dw.JD_ACCOUNT_CITY IS NOT NULL AND tmp.JD_ACCOUNT_CITY IS NULL) OR " +

                            "dw.JD_ACCOUNT_STATE <> tmp.JD_ACCOUNT_STATE OR " +
                            "(dw.JD_ACCOUNT_STATE IS NULL AND tmp.JD_ACCOUNT_STATE IS NOT NULL) OR " +
                            "(dw.JD_ACCOUNT_STATE IS NOT NULL AND tmp.JD_ACCOUNT_STATE IS NULL) OR " +

                            "dw.JD_ACCOUNT_STATUS_CD <> tmp.JD_ACCOUNT_STATUS_CD OR " +
                            "(dw.JD_ACCOUNT_STATUS_CD IS NULL AND tmp.JD_ACCOUNT_STATUS_CD IS NOT NULL) OR " +
                            "(dw.JD_ACCOUNT_STATUS_CD IS NOT NULL AND tmp.JD_ACCOUNT_STATUS_CD IS NULL) OR " +

                            "dw.JD_ACCOUNT_ID <> tmp.JD_ACCOUNT_ID OR " +
                            "(dw.JD_ACCOUNT_ID IS NULL AND tmp.JD_ACCOUNT_ID IS NOT NULL) OR " +
                            "(dw.JD_ACCOUNT_ID IS NOT NULL AND tmp.JD_ACCOUNT_ID IS NULL) OR " +

                            "dw.JD_REGION <> tmp.JD_REGION OR " +
                            "(dw.JD_REGION IS NULL AND tmp.JD_REGION IS NOT NULL) OR " +
                            "(dw.JD_REGION IS NOT NULL AND tmp.JD_REGION IS NULL) OR " +

                            "dw.JD_REGION_ID <> tmp.JD_REGION_ID OR " +
                            "(dw.JD_REGION_ID IS NULL AND tmp.JD_REGION_ID IS NOT NULL) OR " +
                            "(dw.JD_REGION_ID IS NOT NULL AND tmp.JD_REGION_ID IS NULL) OR " +

                            "dw.JD_MARKET <> tmp.JD_MARKET OR " +
                            "(dw.JD_MARKET IS NULL AND tmp.JD_MARKET IS NOT NULL) OR " +
                            "(dw.JD_MARKET IS NOT NULL AND tmp.JD_MARKET IS NULL) " +
                        ") " +
                ") " +
               "WHERE tmp.ACCOUNT_DIM_ID IS NOT NULL";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of 'UPDATE_TYPE' field in the table " +
                tblDwAccountTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
        /// log Errors :
        String[] sourceFields = new String[] {"ACCOUNT_ID"};
        String[] foreignKeys = new String[] {"STORE_DIM_ID"};

        List<StringBuffer> errors = verifyConstraints(connection, sourceFields, foreignKeys, tblDwAccountTemp);

        int insertedRowCount = 0;
        int updatedRowCount = 0;
        int errorRowCount = errors.size();
        if (errorRowCount > 0) {
//          String errMess = genErrMess(errors, tblDwAccount, tblDwAccountTemp);
          throw new DataNotFoundException(action + " :: <ERROR> It is impossible to update "+tblDwAccount+". NULL foreign key occured in "+tblDwAccountTemp+". " + errors.toString());
//          throw new DataNotFoundException(action + errMess);
       } else {
        /// Updating of data in table 'DW_ACCOUNT_DIM'
        try {
            sql =
                "UPDATE " + tblDwAccount + " dw " +
                "SET " +
                "( " +
                    "ACCOUNT_NAME, " +
                    "ACCOUNT_NUM, " +
                    "ACCOUNT_STREET_ADDRESS, " +
                    "ACCOUNT_CITY, " +
                    "ACCOUNT_STATE, " +
                    "ACCOUNT_STATUS_CD, " +
                    "BRANCH_NAME, " +
                    "MARKET, " +
                    "JD_ACCOUNT_NAME, " +
                    "JD_ACCOUNT_STREET_ADDRESS, " +
                    "JD_ACCOUNT_CITY, " +
                    "JD_ACCOUNT_STATE, " +
                    "JD_ACCOUNT_STATUS_CD, " +
                    "JD_ACCOUNT_ID, " +
                    "JD_REGION, " +
                    "JD_REGION_ID, " +
                    "JD_MARKET, " +
                    "MOD_BY, " +
                    "MOD_DATE " +
                ") " +
                "= " +
                "( " +
                    "SELECT " +
                        "tmp.ACCOUNT_NAME, " +                     //  VARCHAR2(255) NULL
                        "tmp.ACCOUNT_NUM, " +                      //  VARCHAR2(30) NULL
                        "tmp.ACCOUNT_STREET_ADDRESS, " +           //  VARCHAR2(255) NULL
                        "tmp.ACCOUNT_CITY, " +                     //  VARCHAR2(30) NULL
                        "tmp.ACCOUNT_STATE, " +                    //  VARCHAR2(20) NULL
                        "tmp.ACCOUNT_STATUS_CD, " +                //  VARCHAR2(30) NULL
                        "tmp.BRANCH_NAME, " +                      //  VARCHAR2(20) NULL
                        "tmp.MARKET, " +                           //  VARCHAR2(50) NULL
                        "tmp.JD_ACCOUNT_NAME, " +                  //  VARCHAR2(255) NULL
                        "tmp.JD_ACCOUNT_STREET_ADDRESS, " +        //  VARCHAR2(255) NULL
                        "tmp.JD_ACCOUNT_CITY, " +                  //  VARCHAR2(30) NULL
                        "tmp.JD_ACCOUNT_STATE, " +                 //  VARCHAR2(20) NULL
                        "tmp.JD_ACCOUNT_STATUS_CD, " +             //  VARCHAR2(30) NULL
                        "tmp.JD_ACCOUNT_ID, " +                    //  NUMBER NULL
                        "tmp.JD_REGION, " +                        //  VARCHAR2(30) NULL
                        "tmp.JD_REGION_ID, " +                     //  NUMBER NULL
                        "tmp.JD_MARKET, " +                        //  VARCHAR2(50) NULL
                        "?, " +                                    //  VARCHAR2(30) NULL
                        "SYSDATE " +                              //  DATE NOT NULL
                    "FROM " +
                        tblDwAccountTemp + " tmp " +
                    "WHERE " +
                        "dw.ACCOUNT_DIM_ID = tmp.ACCOUNT_DIM_ID " +
                ") " +
                "WHERE dw.ACCOUNT_DIM_ID IN " +
                    "( " +
                        "SELECT DISTINCT " +
                            "ACCOUNT_DIM_ID " +
                        "FROM " +
                            tblDwAccountTemp + " tmp " +
                        "WHERE " +
                            "UPDATE_TYPE = 'TO_UPDATE' " +
                    ")";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            updatedRowCount = stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of data in the table " +
                tblDwAccount + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Insert of new records into table 'TBL_DW_ACCOUNT_DIM'.
        /// New records (in the table 'TBL_DW_ACCOUNT_DIM_TEMP') have
        /// the NULL value in the field 'ACCOUNT_DIM_ID'.
        try {
            sql =
                "INSERT INTO " + tblDwAccount + " dw " +
                "( " +
                    "STORE_DIM_ID, " +
                    "ACCOUNT_DIM_ID, " +
                    "ACCOUNT_ID, " +
                    "ACCOUNT_NAME, " +
                    "ACCOUNT_NUM, " +
                    "ACCOUNT_STREET_ADDRESS, " +
                    "ACCOUNT_CITY, " +
                    "ACCOUNT_STATE, " +
                    "ACCOUNT_STATUS_CD, " +
                    "BRANCH_NAME, " +
                    "MARKET, " +
                    "JD_ACCOUNT_NAME, " +
                    "JD_ACCOUNT_STREET_ADDRESS, " +
                    "JD_ACCOUNT_CITY, " +
                    "JD_ACCOUNT_STATE, " +
                    "JD_ACCOUNT_STATUS_CD, " +
                    "JD_ACCOUNT_ID, " +
                    "JD_REGION, " +
                    "JD_REGION_ID, " +
                    "JD_MARKET, " +
                    "ADD_BY, " +
                    "ADD_DATE, " +
                    "MOD_BY, " +
                    "MOD_DATE " +
                ") " +
                "SELECT " +
                    "tmp.STORE_DIM_ID, " +                     //  NUMBER NOT NULL
                    tblDwAccountSeq + ".NEXTVAL +100, " +           //  NUMBER NOT NULL
                    "tmp.ACCOUNT_ID, " +                       //  NUMBER NULL
                    "tmp.ACCOUNT_NAME, " +                     //  VARCHAR2(255) NULL
                    "tmp.ACCOUNT_NUM, " +                      //  VARCHAR2(30) NULL
                    "tmp.ACCOUNT_STREET_ADDRESS, " +           //  VARCHAR2(255) NULL
                    "tmp.ACCOUNT_CITY, " +                     //  VARCHAR2(30) NULL
                    "tmp.ACCOUNT_STATE, " +                    //  VARCHAR2(20) NULL
                    "tmp.ACCOUNT_STATUS_CD, " +                //  VARCHAR2(30) NULL
                    "tmp.BRANCH_NAME, " +                      //  VARCHAR2(20) NULL
                    "tmp.MARKET, " +                           //  VARCHAR2(50) NULL
                    "tmp.JD_ACCOUNT_NAME, " +                  //  VARCHAR2(255) NULL
                    "tmp.JD_ACCOUNT_STREET_ADDRESS, " +        //  VARCHAR2(255) NULL
                    "tmp.JD_ACCOUNT_CITY, " +                  //  VARCHAR2(30) NULL
                    "tmp.JD_ACCOUNT_STATE, " +                 //  VARCHAR2(20) NULL
                    "tmp.JD_ACCOUNT_STATUS_CD, " +             //  VARCHAR2(30) NULL
                    "tmp.JD_ACCOUNT_ID, " +                    //  NUMBER NULL
                    "tmp.JD_REGION, " +                        //  VARCHAR2(30) NULL
                    "tmp.JD_REGION_ID, " +                     //  NUMBER NULL
                    "tmp.JD_MARKET, " +                        //  VARCHAR2(50) NULL
                    "?, " +                                    //  VARCHAR2(30) NULL
                    "SYSDATE, " +                              //  DATE NOT NULL
                    "?, " +                                    //  VARCHAR2(30) NULL
                    "SYSDATE " +                               //  DATE NOT NULL
                "FROM " +
                    tblDwAccountTemp + " tmp " +
                "WHERE " +
                    "tmp.ACCOUNT_DIM_ID IS NULL";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, userName);
            insertedRowCount = stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at inserting of records into the table " +
                tblDwAccount + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
       }
       ///
       /// REPORT
       StringBuffer report = logReport(tblDwAccount, insertedRowCount, updatedRowCount, errors  );
       return report;
    }

    /**
     * Fills the DW_SITE_DIM table in the "Data Warehouse".
     * Use the parameter 'dbClwNamespace' to define the schema for 'CLW'-tables with data.
     * Use the parameter 'dbDwNamespace' to define the schema for the tables from 'Data Warehouse'.
     * The parameter 'storeId' - is the identifier of store (for example 'JanPak') to explore.
     * The parameter 'jdStoreId' - is the identifier of 'JohnsonDiversey' store.
     */
    private  StringBuffer fillDwSiteDimension(Connection connection,
        String userName, String dbClwNamespace, String dbDwNamespace,
        int exploredStoreId, int dwStoreDimId) throws SQLException, Exception {

        if (connection == null) {
            throw new SQLException("Connection is null!");
        }

        final String tblDwStore = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_STORE_DIM);
        final String tblDwAccount = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_ACCOUNT_DIM);
        final String tblDwSite = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_SITE_DIM);
        final String tblDwSiteTemp = getFullTableName(dwTempUser, TBL_DW_SITE_DIM_TEMP, tempSuffix);
        final String tblDwSiteSeq = getFullTableName(dbDwNamespace, TBL_DW_SITE_DIM_SEQ);

        final String tblClwAddress = getFullTableName(dbClwNamespace, TBL_CLW_ADDRESS);
        final String tblClwProperty = getFullTableName(dbClwNamespace, TBL_CLW_PROPERTY);
        final String tblClwBusEntity = getFullTableName(dbClwNamespace, TBL_CLW_BUS_ENTITY);
        final String tblClwBusEntityAssoc = getFullTableName(dbClwNamespace, TBL_CLW_BUS_ENTITY_ASSOC);

        final String action = "< DataWarehouseBean >[" + tblDwSite +   " Creation] ";

        String sql = null;
        PreparedStatement stmt = null;
        boolean isExistTempTable = false;

        ////////////////////////////////////////////////////////////////////////
        /// Creation of the temporary table 'TBL_DW_SITE_DIM_TEMP'.
        /// The table 'TBL_DW_SITE_DIM_TEMP' contains data
        /// to update table 'TBL_DW_SITE_DIM' from Data Warehouse.
        ////////////////////////////////////////////////////////////////////////

/*        /// Checking of existence of table 'TBL_DW_SITE_DIM_TEMP'
        try {
            isExistTempTable = checkDbTableOnExistence(connection,
                dbDwNamespace, TBL_DW_SITE_DIM_TEMP);
        }
        catch (SQLException ex) {
            String msg = "An error occurred at checking existence of the table " +
                tblDwSiteTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
*/
        /// Deleting of already existing table 'TBL_DW_SITE_DIM_TEMP'
        if (isExistTempTable) {
            try {
                sql = "DROP TABLE " + tblDwSiteTemp + " PURGE";
                stmt = connection.prepareStatement(sql);
                stmt.executeUpdate();
                stmt.close();
            }
            catch (SQLException ex) {
                String msg = "An error occurred at deleting of table " +
                    tblDwSiteTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
                throw new SQLException("^clw^"+msg+ "^clw^");
            }
        }

        /// Creation of the table 'TBL_DW_SITE_DIM_TEMP'
        try {
            sql =
                "CREATE TABLE " + tblDwSiteTemp + " " +
                "( " +
                    "STORE_DIM_ID               NUMBER NULL, \n" +
                    "SITE_DIM_ID                NUMBER NULL, \n" +
                    "ACCOUNT_DIM_ID             NUMBER NULL, \n" +

                    "STORE_ID                   NUMBER NOT NULL, \n" +

                    "ACCOUNT_ID                 NUMBER NULL, \n" +

                    "SITE_ID                    NUMBER NOT NULL, \n" +
                    "SITE_NAME                  VARCHAR2(255) NOT NULL, \n" +
                    "SITE_TYPE_CD               VARCHAR2(30) NULL, \n" +
                    "SITE_STATUS_CD             VARCHAR2(30) NULL, \n" +

                    "SITE_CITY                  VARCHAR2(255) NULL, \n" +
                    "SITE_STATE                 VARCHAR2(50) NULL, \n" +
                    "SITE_POSTAL_CODE           VARCHAR2(30) NULL, \n" +
                    "SITE_COUNTRY               VARCHAR2(50) NULL, \n" +
                    "SITE_STREET_ADDRESS        VARCHAR2(255) NULL, \n" +
                    "SITE_NUM                   VARCHAR2(30) NULL, \n" +
//                    "SITE_BUDGET_REF_CD         VARCHAR2(30) NULL, \n" +
                    "SITE_DIST_REF_CD           VARCHAR2(30) NULL, \n" +
                    "TYPE                       VARCHAR2(30) NULL, \n" +

                    "UPDATE_TYPE                VARCHAR2(30) NULL, \n" +
                    "SITE_REGION                VARCHAR2(30) NULL, \n"  +
                    "SITE_MARKET                VARCHAR2(30) NULL, \n"  +
                    "SITE_DISTRICT              VARCHAR2(30) NULL, \n"  +
                    "SITE_TYPE                  VARCHAR2(30) NULL, \n"  +
                    "SITE_SQUARE_FOOTAGE        VARCHAR2(30) NULL, \n"  +
                    "SITE_AVERAGE_CUSTOMER_COUNT VARCHAR2(30) NULL, \n"  +
                    "SITE_BSC                   VARCHAR2(255) NULL \n"  +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at creation of table " +
                tblDwSiteTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        ////////////////////////////////////////////////////////////////////////
        /// Filling of temporal 'TBL_DW_SITE_DIM_TEMP'-table by data.
        ////////////////////////////////////////////////////////////////////////

        /// Insert of data from store 'JanPak' into temporal table.
        try {
            sql =
                "INSERT INTO " + tblDwSiteTemp + " \n" +
                "SELECT \n" +
                    dwStoreDimId + ", \n" +
                    "NULL, \n" +
                    "NULL, \n" +

                    "stor.BUS_ENTITY_ID        STORE_ID, \n" +

                    "acc.BUS_ENTITY_ID         ACCOUNT_ID, \n" +

                    "site.BUS_ENTITY_ID        SITE_ID, \n" +
                    "site.SHORT_DESC           SITE_NAME, \n" +
                    "site.BUS_ENTITY_TYPE_CD   SITE_TYPE_CD, \n" +
                    "site.BUS_ENTITY_STATUS_CD SITE_STATUS_CD, \n" +

                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
//                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL, " +
                    "NULL \n" +
                "FROM  \n" +
                    tblClwBusEntity + " stor INNER JOIN \n" +
                    "( " +
                        tblClwBusEntityAssoc + " r1 INNER JOIN  \n" +
                        "( " +
                            tblClwBusEntity + " acc INNER JOIN \n" +
                            "( " +
                                tblClwBusEntityAssoc + " r2 INNER JOIN  \n" +
                                "( " +
                                    tblClwBusEntity + " site \n" +
                                ") ON r2.BUS_ENTITY1_ID = site.BUS_ENTITY_ID AND r2.BUS_ENTITY_ASSOC_CD = 'SITE OF ACCOUNT' \n" +
                            ") ON acc.BUS_ENTITY_ID = r2.BUS_ENTITY2_ID AND r2.BUS_ENTITY_ASSOC_CD = 'SITE OF ACCOUNT' \n" +
                        ") ON r1.BUS_ENTITY1_ID = acc.BUS_ENTITY_ID AND r1.BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' \n" +
                    ") ON stor.BUS_ENTITY_ID = r1.BUS_ENTITY2_ID AND r1.BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' \n" +
                "WHERE \n" +
                    "stor.BUS_ENTITY_ID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, exploredStoreId);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at insertion of records into table " +
                tblDwSiteTemp + " for store " + exploredStoreId + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

/*        /// Fill the field 'STORE_DIM_ID' in the table 'TBL_DW_SITE_DIM_TEMP'.
        try {
            sql =
                "UPDATE " + tblDwSiteTemp + " tmp SET tmp.STORE_DIM_ID = " +
                "( " +
                    "SELECT " +
                        "dw.STORE_DIM_ID " +
                    "FROM " +
                        tblDwStore + " dw " +
                    "WHERE " +
                        "tmp.STORE_ID = dw.STORE_ID " +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of field 'STORE_DIM_ID' " +
                "in the table " + tblDwSiteTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
*/
        /// Fill the field 'ACCOUNT_DIM_ID' in the table 'TBL_DW_SITE_DIM_TEMP'.
        ctaUpdate (connection, dwStoreDimId, tblDwAccount, tblDwSiteTemp,
                   "ACCOUNT_DIM_ID", "ACCOUNT_DIM_ID", "ACCOUNT_ID", "ACCOUNT_ID", null);
/*
        try {
            sql =
                "UPDATE " + tblDwSiteTemp + " tmp SET tmp.ACCOUNT_DIM_ID = \n" +
                "( \n" +
                    "SELECT \n" +
                        "dw.ACCOUNT_DIM_ID \n" +
                    "FROM \n" +
                        tblDwAccount + " dw \n" +
                    "WHERE \n" +
                        "tmp.ACCOUNT_ID = dw.ACCOUNT_ID AND \n" +
                        "dw.STORE_DIM_ID =" + dwStoreDimId +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of field 'ACCOUNT_DIM_ID' " +
                "in the table " + tblDwSiteTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
*/
        createIndex(connection, tblDwSiteTemp, "I0", "SITE_ID");

        /// Fill the field 'SITE_DIM_ID' in the table 'TBL_DW_SITE_DIM_TEMP'.

        ctaUpdate (connection, dwStoreDimId, tblDwSite, tblDwSiteTemp,
                   "SITE_DIM_ID", "SITE_DIM_ID", "SITE_ID", "SITE_ID", null);
/*
        try {
            sql =
                "UPDATE " + tblDwSiteTemp + " tmp SET tmp.SITE_DIM_ID = \n" +
                "( \n" +
                    "SELECT \n" +
                        "dw.SITE_DIM_ID \n" +
                    "FROM \n" +
                        tblDwSite + " dw \n" +
                    "WHERE " +
                        "tmp.SITE_ID = dw.SITE_ID AND \n" +
                        "dw.STORE_DIM_ID =" + dwStoreDimId +

                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of field 'SITE_DIM_ID' " +
                "in the table " + tblDwSiteTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
*/
        /// Fill the fields with address information for the account in the table 'TBL_DW_SITE_DIM_TEMP'.
        try {
            sql =
                "UPDATE " + tblDwSiteTemp + " tmp \n" +
                "SET " +
                "( " +
                    "tmp.SITE_CITY, \n" +
                    "tmp.SITE_STATE, \n" +
                    "tmp.SITE_POSTAL_CODE, \n" +
                    "tmp.SITE_COUNTRY, \n" +
                    "tmp.SITE_STREET_ADDRESS \n" +
                ") \n" +
                "= \n" +
                "( \n" +
                    "SELECT \n" +
                        "addr.CITY                 SITE_CITY, \n" +
                        "addr.STATE_PROVINCE_CD    SITE_STATE, \n" +
                        "addr.POSTAL_CODE          SITE_POSTAL_CODE, \n" +
                        "addr.COUNTRY_CD           SITE_COUNTRY, \n" +
                        "addr.ADDRESS1             SITE_STREET_ADDRESS \n" +
                    "FROM " +
                        tblClwAddress + " addr \n" +
                    "WHERE \n" +
                        "tmp.SITE_ID = addr.BUS_ENTITY_ID AND addr.ADDRESS_TYPE_CD = 'SHIPPING' AND addr.PRIMARY_IND = 1 \n" +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred while filling the fields with address information for the account " +
                "in the table " + tblDwSiteTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Fill the field 'SITE_NUM' in the table 'TBL_DW_SITE_DIM_TEMP'.
        String addCond =" AND SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER +"' \n" +
                        " AND CLW_VALUE is not null \n" ;
        ctaUpdate (connection, 0, tblClwProperty, tblDwSiteTemp,
                   "CLW_VALUE", "SITE_NUM", "BUS_ENTITY_ID", "SITE_ID", addCond);

        addCond =" AND SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER +"' \n" +
                        " AND CLW_VALUE is not null \n" ;
        ctaUpdate (connection, 0, tblClwProperty, tblDwSiteTemp,
                   "CLW_VALUE", "SITE_DIST_REF_CD", "BUS_ENTITY_ID", "SITE_ID", addCond);

        /// Fill the fields SITE_REGION, SITE_MARKET, SITE_DISTRICT, SITE_TYPE, SITE_SQUARE_FOOTAGE,
        /// SITE_AVERAGE_CUSTOMER_COUNT, SITE_BSC from the Site in the table 'TBL_DW_SITE_DIM_TEMP'
            addCond =" AND SHORT_DESC = 'Region:' \n" +
                            " AND CLW_VALUE is not null \n" ;
            ctaUpdate (connection, 0, tblClwProperty, tblDwSiteTemp,
                       "CLW_VALUE", "SITE_REGION", "BUS_ENTITY_ID", "SITE_ID", addCond); //???

            addCond =" AND SHORT_DESC = 'Market:' \n" +
                            " AND CLW_VALUE is not null \n" ;
            ctaUpdate (connection, 0, tblClwProperty, tblDwSiteTemp,
                       "CLW_VALUE", "SITE_MARKET", "BUS_ENTITY_ID", "SITE_ID", addCond); //???

            addCond =" AND SHORT_DESC = 'District:' \n" +
            " AND CLW_VALUE is not null \n" ;
            ctaUpdate (connection, 0, tblClwProperty, tblDwSiteTemp,
                       "CLW_VALUE", "SITE_DISTRICT", "BUS_ENTITY_ID", "SITE_ID", addCond); //???

            addCond =" AND SHORT_DESC = 'Type:' \n" +
            " AND CLW_VALUE is not null \n" ;
            ctaUpdate (connection, 0, tblClwProperty, tblDwSiteTemp,
                       "CLW_VALUE", "SITE_TYPE", "BUS_ENTITY_ID", "SITE_ID", addCond); //???

            addCond =" AND SHORT_DESC = 'Square Footage:' \n" +
            " AND CLW_VALUE is not null \n" ;
            ctaUpdate (connection, 0, tblClwProperty, tblDwSiteTemp,
                       "CLW_VALUE", "SITE_SQUARE_FOOTAGE", "BUS_ENTITY_ID", "SITE_ID", addCond); //???

            addCond =" AND SHORT_DESC = 'Average Customer Count:' \n" +
            " AND CLW_VALUE is not null \n" ;
            ctaUpdate (connection, 0, tblClwProperty, tblDwSiteTemp,
                       "CLW_VALUE", "SITE_AVERAGE_CUSTOMER_COUNT", "BUS_ENTITY_ID", "SITE_ID", addCond); //???

            addCond =" AND SHORT_DESC = 'BSC:' \n" +
            " AND CLW_VALUE is not null \n" ;
            ctaUpdate (connection, 0, tblClwProperty, tblDwSiteTemp,
                       "CLW_VALUE", "SITE_BSC", "BUS_ENTITY_ID", "SITE_ID", addCond); //???
/*
        try {
            sql =
                "UPDATE " + tblDwSiteTemp + " tmp SET tmp.SITE_NUM = \n" +
                "( \n" +
                    "SELECT \n" +
                        "prop.CLW_VALUE \n" +
                    "FROM \n" +
                        tblClwProperty + " prop \n" +
                    "WHERE \n" +
                        "    prop.BUS_ENTITY_ID = tmp.SITE_ID  \n" +
                        " AND prop.SHORT_DESC = 'SITE_REFERENCE_NUMBER' \n" +
                        " AND prop.CLW_VALUE is not null \n" +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of field 'SITE_NUM' " +
                "in the table " + tblDwSiteTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
*/
        ////////////////////////////////////////////////////////////////////////
        /// Updating the data in table 'TBL_DW_SITE_DIM'.
        /// The data from table 'TBL_DW_SITE_DIM_TEMP' will be used
        /// to update the data in the table 'TBL_DW_SITE_DIM'.
        ////////////////////////////////////////////////////////////////////////

        /// Updating the field UPDATE_TYPE in table 'TBL_DW_SITE_DIM_TEMP'.
        /// Set the value of the field 'UPDATE_TYPE' in table 'TBL_DW_SITE_DIM_TEMP' to 'TO_UPDATE' for
        /// records which have the analogues in the table 'TBL_DW_SITE_DIM' and which have been changed.
        try {
            sql =
                "UPDATE " + tblDwSiteTemp + " tmp " +
                "SET tmp.UPDATE_TYPE = " +
                "( " +
                    "SELECT " +
                        "'TO_UPDATE' " +
                    "FROM " +
                        tblDwSite + " dw " +
                    "WHERE " +
                        "dw.SITE_DIM_ID = tmp.SITE_DIM_ID AND " +
                        "( " +
                            "dw.SITE_NAME <> tmp.SITE_NAME OR " +
                            "(dw.SITE_NAME IS NULL AND tmp.SITE_NAME IS NOT NULL) OR " +
                            "(dw.SITE_NAME IS NOT NULL AND tmp.SITE_NAME IS NULL) OR " +

                            "dw.SITE_NUM <> tmp.SITE_NUM OR " +
                            "(dw.SITE_NUM IS NULL AND tmp.SITE_NUM IS NOT NULL) OR " +
                            "(dw.SITE_NUM IS NOT NULL AND tmp.SITE_NUM IS NULL) OR " +

                            "dw.SITE_DIST_REF_CD <> tmp.SITE_DIST_REF_CD OR " +
                            "(dw.SITE_DIST_REF_CD IS NULL AND tmp.SITE_DIST_REF_CD IS NOT NULL) OR " +
                            "(dw.SITE_DIST_REF_CD IS NOT NULL AND tmp.SITE_DIST_REF_CD IS NULL) OR " +

                            "dw.SITE_STREET_ADDRESS <> tmp.SITE_STREET_ADDRESS OR " +
                            "(dw.SITE_STREET_ADDRESS IS NULL AND tmp.SITE_STREET_ADDRESS IS NOT NULL) OR " +
                            "(dw.SITE_STREET_ADDRESS IS NOT NULL AND tmp.SITE_STREET_ADDRESS IS NULL) OR " +

                            "dw.SITE_CITY <> tmp.SITE_CITY OR " +
                            "(dw.SITE_CITY IS NULL AND tmp.SITE_CITY IS NOT NULL) OR " +
                            "(dw.SITE_CITY IS NOT NULL AND tmp.SITE_CITY IS NULL) OR " +

                            "dw.SITE_STATE <> tmp.SITE_STATE OR " +
                            "(dw.SITE_STATE IS NULL AND tmp.SITE_STATE IS NOT NULL) OR " +
                            "(dw.SITE_STATE IS NOT NULL AND tmp.SITE_STATE IS NULL) OR " +

                            "dw.SITE_POSTAL_CODE <> tmp.SITE_POSTAL_CODE OR " +
                            "(dw.SITE_POSTAL_CODE IS NULL AND tmp.SITE_POSTAL_CODE IS NOT NULL) OR " +
                            "(dw.SITE_POSTAL_CODE IS NOT NULL AND tmp.SITE_POSTAL_CODE IS NULL) OR " +

                            "dw.SITE_COUNTRY <> tmp.SITE_COUNTRY OR " +
                            "(dw.SITE_COUNTRY IS NULL AND tmp.SITE_COUNTRY IS NOT NULL) OR " +
                            "(dw.SITE_COUNTRY IS NOT NULL AND tmp.SITE_COUNTRY IS NULL) OR " +

                            "dw.TYPE <> tmp.TYPE OR " +
                            "(dw.TYPE IS NULL AND tmp.TYPE IS NOT NULL) OR " +
                            "(dw.TYPE IS NOT NULL AND tmp.TYPE IS NULL) OR " +

                            "dw.SITE_STATUS_CD <> tmp.SITE_STATUS_CD OR " +
                            "(dw.SITE_STATUS_CD IS NULL AND tmp.SITE_STATUS_CD IS NOT NULL) OR " +
                            "(dw.SITE_STATUS_CD IS NOT NULL AND tmp.SITE_STATUS_CD IS NULL) OR " +

                            "dw.SITE_REGION <> tmp.SITE_REGION OR " +
                            "(dw.SITE_REGION IS NULL AND tmp.SITE_REGION IS NOT NULL) OR " +
                            "(dw.SITE_REGION IS NOT NULL AND tmp.SITE_REGION IS NULL) OR " +

                            "dw.SITE_MARKET <> tmp.SITE_MARKET OR " +
                            "(dw.SITE_MARKET IS NULL AND tmp.SITE_MARKET IS NOT NULL) OR " +
                            "(dw.SITE_MARKET IS NOT NULL AND tmp.SITE_MARKET IS NULL) OR " +

                            "dw.SITE_DISTRICT <> tmp.SITE_DISTRICT OR " +
                            "(dw.SITE_DISTRICT IS NULL AND tmp.SITE_DISTRICT IS NOT NULL) OR " +
                            "(dw.SITE_DISTRICT IS NOT NULL AND tmp.SITE_DISTRICT IS NULL) OR " +

                            "dw.SITE_TYPE <> tmp.SITE_TYPE OR " +
                            "(dw.SITE_TYPE IS NULL AND tmp.SITE_TYPE IS NOT NULL) OR " +
                            "(dw.SITE_TYPE IS NOT NULL AND tmp.SITE_TYPE IS NULL) OR " +

                            "dw.SITE_SQUARE_FOOTAGE <> tmp.SITE_SQUARE_FOOTAGE OR " +
                            "(dw.SITE_SQUARE_FOOTAGE IS NULL AND tmp.SITE_SQUARE_FOOTAGE IS NOT NULL) OR " +
                            "(dw.SITE_SQUARE_FOOTAGE IS NOT NULL AND tmp.SITE_SQUARE_FOOTAGE IS NULL) OR " +

                            "dw.SITE_AVERAGE_CUSTOMER_COUNT <> tmp.SITE_AVERAGE_CUSTOMER_COUNT OR " +
                            "(dw.SITE_AVERAGE_CUSTOMER_COUNT IS NULL AND tmp.SITE_AVERAGE_CUSTOMER_COUNT IS NOT NULL) OR " +
                            "(dw.SITE_AVERAGE_CUSTOMER_COUNT IS NOT NULL AND tmp.SITE_AVERAGE_CUSTOMER_COUNT IS NULL) OR " +

                            "dw.SITE_BSC <> tmp.SITE_BSC OR " +
                            "(dw.SITE_BSC IS NULL AND tmp.SITE_BSC IS NOT NULL) OR " +
                            "(dw.SITE_BSC IS NOT NULL AND tmp.SITE_BSC IS NULL) " +
                        ") " +
                ") " +
               "WHERE tmp.SITE_DIM_ID IS NOT NULL";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred while updating 'UPDATE_TYPE' field in the table " +
                tblDwSiteTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

       /// log Errors :
       String[] sourceFields = new String[]{"SITE_ID"};
       String[] foreignKeys = new String[]{"STORE_DIM_ID", "ACCOUNT_DIM_ID"};
       List<StringBuffer> errors = verifyConstraints(connection, sourceFields,  foreignKeys,  tblDwSiteTemp);

       int insertedRowCount = 0;
       int updatedRowCount = 0;
       int errorRowCount = errors.size();
       if (errorRowCount > 0){
         throw new DataNotFoundException(action + " :: <ERROR> It is impossible to update "+tblDwSite+". NULL foreign key occured in "+tblDwSiteTemp+". " + errors.toString());
       } else {
         /// Updating the data in the table 'DW_SITE_DIM'
         createIndex(connection, tblDwSiteTemp, "I1" , "SITE_DIM_ID" ) ;

         try {
            sql =
                "UPDATE " + tblDwSite + " dw \n" +
                "SET \n" +
                "( \n" +
                    "SITE_NAME, \n" +
                    "SITE_NUM, \n" +
                    "SITE_DIST_REF_CD, \n" +
                    "SITE_STREET_ADDRESS, \n" +
                    "SITE_CITY, \n" +
                    "SITE_STATE, \n" +
                    "SITE_POSTAL_CODE, \n" +
                    "SITE_COUNTRY, \n" +
                    "TYPE, \n" +
                    "SITE_STATUS_CD, \n" +
                    "SITE_REGION, \n" +
                    "SITE_MARKET, \n" +
                    "SITE_DISTRICT, \n" +
                    "SITE_TYPE, \n" +
                    "SITE_SQUARE_FOOTAGE, \n" +
                    "SITE_AVERAGE_CUSTOMER_COUNT, \n" +
                    "SITE_BSC, \n" +
                    "MOD_BY, \n" +
                    "MOD_DATE \n" +
                ") \n" +
                "= \n" +
                "( \n" +
                    "SELECT \n" +
                        "tmp.SITE_NAME, \n" +                        //  VARCHAR2(255) NOT NULL
                        "tmp.SITE_NUM, \n" +                         //  VARCHAR2(30) NULL
                        "tmp.SITE_DIST_REF_CD, \n" +
                        "tmp.SITE_STREET_ADDRESS, \n" +              //  VARCHAR2(255)  NULL
                        "tmp.SITE_CITY, \n" +                        //  VARCHAR2(255) NULL
                        "tmp.SITE_STATE, \n" +                       //  VARCHAR2(50) NULL
                        "tmp.SITE_POSTAL_CODE, \n" +                 //  VARCHAR2(30) NULL
                        "tmp.SITE_COUNTRY, \n" +                     //  VARCHAR2(50) NULL
                        "tmp.TYPE, \n" +                             //  VARCHAR2(30) NULL
                        "tmp.SITE_STATUS_CD, \n" +                   //  VARCHAR2(30) NULL
                        "tmp.SITE_REGION, \n" +
                        "tmp.SITE_MARKET, \n" +
                        "tmp.SITE_DISTRICT, \n" +
                        "tmp.SITE_TYPE, \n" +
                        "tmp.SITE_SQUARE_FOOTAGE, \n" +
                        "tmp.SITE_AVERAGE_CUSTOMER_COUNT, \n" +
                        "tmp.SITE_BSC, \n" +
                        "?, \n" +                                    //  VARCHAR2(30) NULL
                        "SYSDATE \n" +                               //  DATE NOT NULL
                    "FROM \n" +
                        tblDwSiteTemp + " tmp \n" +
                    "WHERE \n" +
                        "dw.SITE_DIM_ID = tmp.SITE_DIM_ID \n" +

                ") \n" +
                "WHERE dw.SITE_DIM_ID IN \n" +
                    "( \n" +
                        "SELECT DISTINCT \n" +
                            "SITE_DIM_ID \n" +
                        "FROM \n" +
                            tblDwSiteTemp + " tmp \n" +
                        "WHERE \n" +
                            "UPDATE_TYPE = 'TO_UPDATE' \n" +
                    ")";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            updatedRowCount = stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of data in the table " +
                tblDwSite + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Insert new records in the table 'TBL_DW_SITE_DIM'.
        /// New records (in the table 'TBL_DW_SITE_DIM_TEMP') have
        /// the NULL value in the field 'ACCOUNT_DIM_ID'.

        try {
            sql =
                "INSERT INTO " + tblDwSite + " dw " +
                "( " +
                    "STORE_DIM_ID, \n" +
                    "SITE_DIM_ID, \n" +
                    "ACCOUNT_DIM_ID, \n" +
                    "ACCOUNT_ID, \n" +
                    "SITE_ID, \n" +
                    "SITE_NAME, \n" +
                    "SITE_NUM, \n" +
                    "SITE_DIST_REF_CD, \n" +
                    "SITE_STREET_ADDRESS, \n" +
                    "SITE_CITY, \n" +
                    "SITE_STATE, \n" +
                    "SITE_POSTAL_CODE, \n" +
                    "SITE_COUNTRY, \n" +
                    "TYPE, \n" +
                    "SITE_STATUS_CD, \n" +
                    "SITE_REGION, \n" +
                    "SITE_MARKET, \n" +
                    "SITE_DISTRICT, \n" +
                    "SITE_TYPE, \n" +
                    "SITE_SQUARE_FOOTAGE, \n" +
                    "SITE_AVERAGE_CUSTOMER_COUNT, \n" +
                    "SITE_BSC, \n" +
                    "ADD_BY, " +
                    "ADD_DATE, " +
                    "MOD_BY, " +
                    "MOD_DATE " +
                ") " +
                "SELECT " +
                    "tmp.STORE_DIM_ID, " +                     //  NUMBER NOT NULL
                    tblDwSiteSeq + ".NEXTVAL+100, " +              //  NUMBER NOT NULL
                    "tmp.ACCOUNT_DIM_ID, " +                   //  NUMBER NOT NULL
                    "tmp.ACCOUNT_ID, " +                       //  NUMBER NULL
                    "tmp.SITE_ID, " +                          //  NUMBER NULL
                    "tmp.SITE_NAME, " +                        //  VARCHAR2(255) NOT NULL
                    "tmp.SITE_NUM, " +                         //  VARCHAR2(30) NULL
                    "tmp.SITE_DIST_REF_CD, " +
                    "tmp.SITE_STREET_ADDRESS, " +              //  VARCHAR2(255)  NULL
                    "tmp.SITE_CITY, " +                        //  VARCHAR2(255) NULL
                    "tmp.SITE_STATE, " +                       //  VARCHAR2(50) NULL
                    "tmp.SITE_POSTAL_CODE, " +                 //  VARCHAR2(30) NULL
                    "tmp.SITE_COUNTRY, " +                     //  VARCHAR2(50) NULL
                    "tmp.TYPE, " +                             //  VARCHAR2(30) NULL
                    "tmp.SITE_STATUS_CD, " +                   //  VARCHAR2(30) NULL
                    "tmp.SITE_REGION, \n" +
                    "tmp.SITE_MARKET, \n" +
                    "tmp.SITE_DISTRICT, \n" +
                    "tmp.SITE_TYPE, \n" +
                    "tmp.SITE_SQUARE_FOOTAGE, \n" +
                    "tmp.SITE_AVERAGE_CUSTOMER_COUNT, \n" +
                    "tmp.SITE_BSC, \n" +
                    "?, " +                                    //  VARCHAR2(30) NULL
                    "SYSDATE, " +                              //  DATE NOT NULL
                    "?, " +                                    //  VARCHAR2(30) NULL
                    "SYSDATE " +                               //  DATE NOT NULL
                "FROM " +
                    tblDwSiteTemp + " tmp " +
                "WHERE " +
                    "tmp.SITE_DIM_ID IS NULL";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, userName);
            insertedRowCount = stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at inserting of records into the table " +
                tblDwSite + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
       }
        ///
        /// REPORT
      StringBuffer report = logReport(tblDwSite, insertedRowCount, updatedRowCount, errors  );
      return report;

      }

    public void fillDwItemDimension(String storeSchemaName, Integer store1Id, Integer store2Id, String dwSchemaName)
            throws RemoteException , Exception{

         String userName = "dwUpdate";
         Connection connection = null;
         final String tblDwStoreDim = getFullTableName(dwSchemaName, DataWarehouse.TBL_DW_STORE_DIM);

        try {
            connection = getConnection();
            int dwStoreDimId = getStoreDimId(connection, store1Id, storeSchemaName, tblDwStoreDim);
            StringBuffer report = new StringBuffer();
            StringBuffer reportCat = fillDwCategoryDimension(connection, userName, storeSchemaName, dwSchemaName, store1Id, store2Id,dwStoreDimId);
            StringBuffer reportItem = fillDwItemDimension(connection, userName, storeSchemaName, dwSchemaName, store1Id, store2Id,dwStoreDimId);
            report.append(reportCat + "\n");
            report.append(reportItem +"\n");
                    }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        }
        catch (NamingException ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        }
        catch (DataNotFoundException ex) {
          ex.printStackTrace();
          throw new DataNotFoundException(ex.getMessage());
        }
       finally {
            closeConnection(connection);
        }
    }

    private  StringBuffer fillDwCategoryDimension(Connection connection,
         String userName, String dbClwNamespace, String dbDwNamespace,
         int exploredStoreId, int linkedStoreId, int dwStoreDimId) throws SQLException, Exception {

         if (connection == null) {
             throw new SQLException("Connection is null!");
         }

         final String tblClwCatalog = getFullTableName(dbClwNamespace, TBL_CLW_CATALOG);
         final String tblClwCatalogAssoc = getFullTableName(dbClwNamespace, TBL_CLW_CATALOG_ASSOC);
         final String tblClwItem = getFullTableName(dbClwNamespace, TBL_CLW_ITEM);
         final String tblClwItemAssoc = getFullTableName(dbClwNamespace, TBL_CLW_ITEM_ASSOC);

         final String tblDwStore = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_STORE_DIM);
         final String tblDwCategory = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_CATEGORY_DIM);
         final String tblDwCategoryTemp = getFullTableName(dwTempUser, TBL_DW_CATEGORY_DIM_TEMP, tempSuffix);
         final String tblDwCategorySeq = getFullTableName(dbDwNamespace, TBL_DW_CATEGORY_DIM_SEQ);
         final String action = "< DataWarehouseBean >[" + tblDwCategory + " Creation] ";

         String sql = null;
         ResultSet resSet = null;
         PreparedStatement stmt = null;
         boolean isExistTempTable = false;
         int catalogId = 0;
         int jdCatalogId = 0;

         ////////////////////////////////////////////////////////////////////////
         /// Creation of temporal table 'TBL_DW_CATEGORY_DIM_TEMP'.
         /// The table 'TBL_DW_CATEGORY_DIM_TEMP' contains data
         /// to update table 'TBL_DW_CATEGORY_DIM' from Data Warehouse.
         ////////////////////////////////////////////////////////////////////////

/*         /// Checking of existence of table 'TBL_DW_CATEGORY_DIM_TEMP'
         try {
             isExistTempTable = checkDbTableOnExistence(connection,
                 dbDwNamespace, TBL_DW_CATEGORY_DIM_TEMP);
         }
         catch (SQLException ex) {
             String msg = "An error occurred at checking existence of table " +
                 tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }
*/
         /// Deleting of already existing table 'TBL_DW_CATEGORY_DIM_TEMP'
         if (isExistTempTable) {
             try {
                 sql = "DROP TABLE " + tblDwCategoryTemp + " PURGE";
                 stmt = connection.prepareStatement(sql);
                 stmt.executeUpdate();
                 stmt.close();
             }
             catch (SQLException ex) {
                 String msg = "An error occurred at deleting of table " +
                     tblDwCategoryTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
                 throw new SQLException("^clw^"+msg+ "^clw^");
             }
         }

         /// Creation of the table 'TBL_DW_CATEGORY_DIM_TEMP'
         try {
             sql =
                 "CREATE TABLE " + tblDwCategoryTemp + " " +
                 "( " +
                     "STORE_DIM_ID        NUMBER NULL, " +
                     "CATEGORY_DIM_ID     NUMBER NULL, " +
                     "CATEGORY1           VARCHAR2(255) NULL, " +
                     "CATEGORY2           VARCHAR2(255) NULL, " +
                     "CATEGORY3           VARCHAR2(255) NULL, " +
                     "JD_CATEGORY1        VARCHAR2(255) NULL, " +
                     "JD_CATEGORY2        VARCHAR2(255) NULL, " +
                     "JD_CATEGORY3        VARCHAR2(255) NULL, " +
                     "CATEG1_ID           NUMBER NULL, " +
                     "CATEG2_ID           NUMBER NULL, " +
                     "CATEG3_ID           NUMBER NULL, " +
                     "JD_CATEG1_ID        NUMBER NULL, " +
                     "JD_CATEG2_ID        NUMBER NULL, " +
                     "JD_CATEG3_ID        NUMBER NULL, " +
                     "STORE_CATALOG_ID    NUMBER NULL, " +
                     "ACCOUNT_CATALOG_ID  NUMBER NULL, " +
                     "STORE_ID            NUMBER NULL, " +
                     "ITEM_ID             NUMBER NULL, " +
                     "JD_STORE_ID         NUMBER NULL, " +
                     "JD_STORE_CATALOG_ID NUMBER NULL, " +
                     "JD_ITEM_ID          NUMBER NULL, " +
                     "UPDATE_TYPE         VARCHAR2(30) NULL " +
                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at creation of table " +
                 tblDwCategoryTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         ////////////////////////////////////////////////////////////////////////
         /// Filling of temporal 'TBL_DW_CATEGORY_DIM_TEMP'-table by data.
         ////////////////////////////////////////////////////////////////////////

         /// Definition of the catalog identifier for main store
         try {
             sql =
                 "SELECT " +
                     "catalogAssoc.CATALOG_ID " +
                 "FROM " +
                     tblClwCatalogAssoc+ " catalogAssoc INNER JOIN " +
                     "( " +
                         tblClwCatalog + " catalog " +
                     ") " +
                     "ON catalogAssoc.CATALOG_ID = catalog.CATALOG_ID AND " +
                         "catalogAssoc.CATALOG_ASSOC_CD = 'CATALOG_STORE' AND " +
                             "catalog.CATALOG_TYPE_CD = 'STORE' " +
                 "WHERE " +
                     "catalogAssoc.BUS_ENTITY_ID = ?";

             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, exploredStoreId);
             resSet = stmt.executeQuery();
             if (resSet.next()) {
                 catalogId = resSet.getInt(1);

             }
             resSet.close();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at definition of the catalog for table " +
                 tblDwCategoryTemp + " for store " + exploredStoreId + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }


        /// Definition of the catalog identifier for linked ("JohnsonDiversey") store
         try {
             sql =
                 "SELECT " +
                     "catalogAssoc.CATALOG_ID " +
                 "FROM " +
                     tblClwCatalogAssoc+ " catalogAssoc INNER JOIN " +
                     "( " +
                         tblClwCatalog + " catalog " +
                     ") " +
                     "ON catalogAssoc.CATALOG_ID = catalog.CATALOG_ID AND " +
                         "catalogAssoc.CATALOG_ASSOC_CD = 'CATALOG_STORE' AND " +
                             "catalog.CATALOG_TYPE_CD = 'STORE' " +
                 "WHERE " +
                     "catalogAssoc.BUS_ENTITY_ID = ?";

             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, linkedStoreId);
             resSet = stmt.executeQuery();
             if (resSet.next()) {
                 jdCatalogId = resSet.getInt(1);
             }
             resSet.close();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at definition of the catalog for table " +
                 tblDwCategoryTemp + " for store " + exploredStoreId + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Insert of data from main store into temporal table.
         try {
             sql =
                 "INSERT INTO " + tblDwCategoryTemp + " \n" +
                 "SELECT DISTINCT \n" +
                     dwStoreDimId + ", \n" +            // STORE_DIM_ID        NUMBER NULL
                     "NULL, \n" +                       // CATEGORY_DIM_ID     NUMBER NULL
                     "i1.SHORT_DESC as CATEGORY1, \n" +        // CATEGORY1           VARCHAR2(255) NULL
                     "NULL, \n" +                       // CATEGORY2           VARCHAR2(255) NULL
                     "NULL, \n" +                       // CATEGORY3           VARCHAR2(255) NULL
                     "NVL((SELECT SHORT_DESC FROM "+ tblClwItem +" WHERE ia2.ITEM2_ID=ITEM_ID), i1.SHORT_DESC) as JD_CATEGORY1, \n" +
 //                    "i2.SHORT_DESC, \n" +        // JD_CATEGORY1        VARCHAR2(255) NULL
                     "NULL, \n" +                       // JD_CATEGORY2        VARCHAR2(255) NULL
                     "NULL, \n" +                       // JD_CATEGORY3        VARCHAR2(255) NULL
                     "ia1.ITEM2_ID, \n" +           // CATEG1_ID           NUMBER NULL
                     "NULL, \n" +                       // CATEG2_ID           NUMBER NULL
                     "NULL, \n" +                       // CATEG3_ID           NUMBER NULL
                     "ia2.ITEM2_ID, \n" +           // JD_CATEG1_ID        NUMBER NULL
                     "NULL, \n" +                       // JD_CATEG2_ID        NUMBER NULL
                     "NULL, \n" +                       // JD_CATEG3_ID        NUMBER NULL
                     "ia1.CATALOG_ID , \n" +       // STORE_CATALOG_ID    NUMBER NULL
                     "NULL, \n" +                       // ACCOUNT_CATALOG_ID  NUMBER NULL
                     exploredStoreId + ", \n" +         // STORE_ID            NUMBER NULL
                     "NULL, \n" +                       // ITEM_ID             NUMBER NULL
                     linkedStoreId + ", \n" +           // JD_STORE_ID         NUMBER NULL
                     "ia2.CATALOG_ID, \n" +      // JD_STORE_CATALOG_ID NUMBER NULL
                     "NULL, \n" +                       // JD_ITEM_ID          NUMBER NULL
                     "NULL \n" +                        // UPDATE_TYPE         VARCHAR2(30) NULL
                     "FROM \n" +
                     "  "+ tblClwItem + "  i1, \n"+
                     "  (SELECT * from " +tblClwItemAssoc + " ia1  \n"+
                     "    WHERE ia1.ITEM_ASSOC_CD = '" + RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY + "' \n"+
                     "      AND ia1.CATALOG_ID = ? \n"+
                     "   ) ia1 \n"+
                     "   LEFT OUTER JOIN "+ tblClwItemAssoc +" iaLink \n" +
                     "    ON  iaLink.ITEM1_ID = ia1.ITEM1_ID \n" +
                     "    AND iaLink.ITEM_ASSOC_CD = '" + RefCodeNames.ITEM_ASSOC_CD.CROSS_STORE_ITEM_LINK + "' \n" +
                     "    AND iaLink.CATALOG_ID = ? \n"+
                     "   LEFT OUTER JOIN "+ tblClwItemAssoc + " ia2 \n" +
                     "    ON  iaLink.ITEM2_ID = ia2.ITEM1_ID \n" +
                     "    AND ia2.CATALOG_ID = iaLink.CATALOG_ID \n" +
                     "    AND ia2.ITEM_ASSOC_CD = '" + RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY + "' \n"+
                     "WHERE \n"+
                     "  ia1.ITEM2_ID = i1.ITEM_ID \n" ;
/*                   tblClwItemAssoc + " ia1,  \n"+
                     tblClwItemAssoc + " iaLink, \n"+
                     tblClwItemAssoc + " ia2, \n"+
                     tblClwItem + "  i1, \n"+
                     tblClwItem + "  i2 \n"+
                     "WHERE \n"+
                     "      ia1.ITEM_ASSOC_CD = 'PRODUCT_PARENT_CATEGORY '  \n"+
                     "  AND ia1.item1_id = iaLink.item1_id \n"+
                     "  AND ia1.item2_id = i1.item_id    \n"+
                     "  AND ia2.ITEM_ASSOC_CD = 'PRODUCT_PARENT_CATEGORY ' \n"+
                     "  AND ia2.item1_id = iaLink.item2_id \n"+
                     "  AND ia2.item2_id = i2.item_id   \n"+
                     "  AND iaLink.ITEM_ASSOC_CD = 'CROSS_STORE_ITEM_LINK '   \n"+
                     "  AND iaLink.catalog_id = ia2.catalog_id  \n"+
                     "  AND ia1.CATALOG_ID = ? \n"+
                     "  AND ia2.CATALOG_ID = ? ";
 */
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, catalogId);
             stmt.setInt(2, jdCatalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at insertion of records into table " +
                 tblDwCategoryTemp + " for store " + exploredStoreId + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }


         /// Fill the fields 'CATEG2_ID' and 'CATEGORY2' in the table 'TBL_DW_CATEGORY_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp \n" +
                 "SET \n" +
                 "( \n" +
                     "tmp.CATEGORY2, \n" +
                     "tmp.CATEG2_ID \n" +
                 ") \n" +
                 "= \n" +
                 "( \n" +
                     "SELECT \n" +
                         "category.SHORT_DESC, \n" +
                         "category.ITEM_ID \n" +
                     "FROM \n" +
                         tblClwItem + " category INNER JOIN \n" +
                         "( \n" +
                             tblClwItemAssoc + " categoryAssoc \n" +
                         ") \n" +
//                         "ON category.ITEM_ID = categoryAssoc.ITEM1_ID AND \n" +
                         "ON category.ITEM_ID = categoryAssoc.ITEM2_ID AND \n" +
                             "categoryAssoc.ITEM_ASSOC_CD = 'CATEGORY_PARENT_CATEGORY' AND \n" +
                                 "categoryAssoc.CATALOG_ID = ? \n" +
                     "WHERE " +
//                         "tmp.CATEG1_ID = categoryAssoc.ITEM2_ID \n" +
                         "tmp.CATEG1_ID = categoryAssoc.ITEM1_ID \n" +
                 ") " +
                 "WHERE tmp.CATEG1_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, catalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of fields 'CATEG2_ID' and 'CATEGORY2' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the fields 'CATEG3_ID' and 'CATEGORY3' in the table 'TBL_DW_CATEGORY_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp \n" +
                 "SET \n" +
                 "( \n" +
                     "tmp.CATEGORY3, \n" +
                     "tmp.CATEG3_ID \n" +
                 ") \n" +
                 "= \n" +
                 "( \n" +
                     "SELECT \n" +
                         "category.SHORT_DESC, \n" +
                         "category.ITEM_ID \n" +
                     "FROM \n" +
                         tblClwItem + " category INNER JOIN \n" +
                         "( \n" +
                             tblClwItemAssoc + " categoryAssoc \n" +
                         ") \n" +
//                         "ON category.ITEM_ID = categoryAssoc.ITEM1_ID AND \n" +
                         "ON category.ITEM_ID = categoryAssoc.ITEM2_ID AND \n" +
                             "categoryAssoc.ITEM_ASSOC_CD = 'CATEGORY_PARENT_CATEGORY' AND \n" +
                                 "categoryAssoc.CATALOG_ID = ? \n" +
                     "WHERE \n" +
//                         "tmp.CATEG2_ID = categoryAssoc.ITEM2_ID \n" +
                         "tmp.CATEG2_ID = categoryAssoc.ITEM1_ID \n" +
                 ") \n" +
                 "WHERE tmp.CATEG2_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, catalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of fields 'CATEG3_ID' and 'CATEGORY3' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }



         /// Fill the fields 'JD_CATEG2_ID' and 'JD_CATEGORY2' in the table 'TBL_DW_CATEGORY_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp \n" +
                 "SET \n" +
                 "( \n" +
                     "tmp.JD_CATEGORY2, \n" +
                     "tmp.JD_CATEG2_ID \n" +
                 ") \n" +
                 "= \n" +
                 "( \n" +
                     "SELECT \n" +
                         "category.SHORT_DESC, \n" +
                         "category.ITEM_ID \n" +
                     "FROM " +
                         tblClwItem + " category INNER JOIN \n" +
                         "( \n" +
                             tblClwItemAssoc + " categoryAssoc \n" +
                         ") \n" +
//NG                         "ON category.ITEM_ID = categoryAssoc.ITEM1_ID AND \n" +
                         "ON category.ITEM_ID = categoryAssoc.ITEM2_ID AND \n" +
                             "categoryAssoc.ITEM_ASSOC_CD = 'CATEGORY_PARENT_CATEGORY' AND \n" +
                                 "categoryAssoc.CATALOG_ID = ? \n" +
                     "WHERE \n" +
//NG                         "tmp.JD_CATEG1_ID = categoryAssoc.ITEM2_ID \n" +
                         "tmp.JD_CATEG1_ID = categoryAssoc.ITEM1_ID \n" +
                 ") \n" +
                 "WHERE tmp.JD_CATEG1_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, jdCatalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of fields 'JD_CATEG2_ID' and 'JD_CATEGORY2' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the fields 'JD_CATEG3_ID' and 'JD_CATEGORY3' in the table 'TBL_DW_CATEGORY_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp \n" +
                 "SET \n" +
                 "( \n" +
                     "tmp.JD_CATEGORY3, \n" +
                     "tmp.JD_CATEG3_ID \n" +
                 ") \n" +
                 "= \n" +
                 "( \n" +
                     "SELECT \n" +
                         "category.SHORT_DESC, \n" +
                         "category.ITEM_ID \n" +
                     "FROM \n" +
                         tblClwItem + " category INNER JOIN \n" +
                         "( " +
                             tblClwItemAssoc + " categoryAssoc " +
                         ") \n" +
//NG                         "ON category.ITEM_ID = categoryAssoc.ITEM1_ID AND \n" +
                         "ON category.ITEM_ID = categoryAssoc.ITEM2_ID AND \n" +
                             "categoryAssoc.ITEM_ASSOC_CD = 'CATEGORY_PARENT_CATEGORY' AND \n" +
                                 "categoryAssoc.CATALOG_ID = ? \n" +
                     "WHERE \n" +
//NG                         "tmp.JD_CATEG2_ID = categoryAssoc.ITEM2_ID \n" +
                         "tmp.JD_CATEG2_ID = categoryAssoc.ITEM1_ID \n" +
                 ") \n" +
                 "WHERE tmp.JD_CATEG2_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, jdCatalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of fields 'JD_CATEG3_ID' and 'JD_CATEGORY3' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }


         /// Fill the field 'CATEGORY_DIM_ID' in the table 'TBL_DW_CATEGORY_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp SET tmp.CATEGORY_DIM_ID = \n" +
                 "( \n" +
                     "SELECT \n" +
                         "dw.CATEGORY_DIM_ID \n" +
                     "FROM \n" +
                         tblDwCategory + " dw \n" +
                     "WHERE \n" +
                         "tmp.CATEG1_ID = dw.CATEG1_ID AND \n" +
                         "NVL(tmp.JD_CATEG1_ID,0) = NVL(dw.JD_CATEG1_ID,0) AND \n" +
                         "dw.STORE_DIM_ID =" + dwStoreDimId +

                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'CATEGORY_DIM_ID' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Updating of the field UPDATE_TYPE in table 'TBL_DW_CATEGORY_DIM_TEMP'.
         /// Set the value of field 'UPDATE_TYPE' in table 'TBL_DW_CATEGORY_DIM_TEMP' into 'TO_UPDATE' for
         /// records which have the similar changed records in table 'TBL_DW_CATEGORY_DIM_DIM'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp " +
                 "SET tmp.UPDATE_TYPE = " +
                 "( " +
                     "SELECT " +
                         "'TO_UPDATE' " +
                     "FROM " +
                         tblDwCategory + " dw " +
                     "WHERE " +
                         "dw.CATEGORY_DIM_ID = tmp.CATEGORY_DIM_ID AND " +
                         "( " +
                             "dw.CATEGORY1 <> tmp.CATEGORY1 OR " +
                             "(dw.CATEGORY1 IS NULL AND tmp.CATEGORY1 IS NOT NULL) OR " +
                             "(dw.CATEGORY1 IS NOT NULL AND tmp.CATEGORY1 IS NULL) OR " +
                             "dw.CATEG2_ID <> tmp.CATEG2_ID OR " +
                             "(dw.CATEG2_ID IS NULL AND tmp.CATEG2_ID IS NOT NULL) OR " +
                             "(dw.CATEG2_ID IS NOT NULL AND tmp.CATEG2_ID IS NULL) OR " +
                             "dw.CATEGORY2 <> tmp.CATEGORY2 OR " +
                             "(dw.CATEGORY2 IS NULL AND tmp.CATEGORY2 IS NOT NULL) OR " +
                             "(dw.CATEGORY2 IS NOT NULL AND tmp.CATEGORY2 IS NULL) OR " +
                             "dw.CATEG3_ID <> tmp.CATEG3_ID OR " +
                             "(dw.CATEG3_ID IS NULL AND tmp.CATEG3_ID IS NOT NULL) OR " +
                             "(dw.CATEG3_ID IS NOT NULL AND tmp.CATEG3_ID IS NULL) OR " +
                             "dw.CATEGORY3 <> tmp.CATEGORY3 OR " +
                             "(dw.CATEGORY3 IS NULL AND tmp.CATEGORY3 IS NOT NULL) OR " +
                             "(dw.CATEGORY3 IS NOT NULL AND tmp.CATEGORY3 IS NULL) OR " +
                             "dw.JD_CATEG1_ID <> tmp.JD_CATEG1_ID OR " +
                             "(dw.JD_CATEG1_ID IS NULL AND tmp.JD_CATEG1_ID IS NOT NULL) OR " +
                             "(dw.JD_CATEG1_ID IS NOT NULL AND tmp.JD_CATEG1_ID IS NULL) OR " +
                             "dw.JD_CATEGORY1 <> tmp.JD_CATEGORY1 OR " +
                             "(dw.JD_CATEGORY1 IS NULL AND tmp.JD_CATEGORY1 IS NOT NULL) OR " +
                             "(dw.JD_CATEGORY1 IS NOT NULL AND tmp.JD_CATEGORY1 IS NULL) OR " +
                             "dw.JD_CATEG2_ID <> tmp.JD_CATEG2_ID OR " +
                             "(dw.JD_CATEG2_ID IS NULL AND tmp.JD_CATEG2_ID IS NOT NULL) OR " +
                             "(dw.JD_CATEG2_ID IS NOT NULL AND tmp.JD_CATEG2_ID IS NULL) OR " +
                             "dw.JD_CATEGORY2 <> tmp.JD_CATEGORY2 OR " +
                             "(dw.JD_CATEGORY2 IS NULL AND tmp.JD_CATEGORY2 IS NOT NULL) OR " +
                             "(dw.JD_CATEGORY2 IS NOT NULL AND tmp.JD_CATEGORY2 IS NULL) OR " +
                             "dw.JD_CATEG3_ID <> tmp.JD_CATEG3_ID OR " +
                             "(dw.JD_CATEG3_ID IS NULL AND tmp.JD_CATEG3_ID IS NOT NULL) OR " +
                             "(dw.JD_CATEG3_ID IS NOT NULL AND tmp.JD_CATEG3_ID IS NULL) OR " +
                             "dw.JD_CATEGORY3 <> tmp.JD_CATEGORY3 OR " +
                             "(dw.JD_CATEGORY3 IS NULL AND tmp.JD_CATEGORY3 IS NOT NULL) OR " +
                             "(dw.JD_CATEGORY3 IS NOT NULL AND tmp.JD_CATEGORY3 IS NULL) OR " +
                             "dw.STORE_CATALOG_ID <> tmp.STORE_CATALOG_ID OR " +
                             "(dw.STORE_CATALOG_ID IS NULL AND tmp.STORE_CATALOG_ID IS NOT NULL) OR " +
                             "(dw.STORE_CATALOG_ID IS NOT NULL AND tmp.STORE_CATALOG_ID IS NULL) " +
                         ") " +
                 ") " +
                "WHERE tmp.CATEGORY_DIM_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of 'UPDATE_TYPE' field in the table " +
                 tblDwCategoryTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         ////////////////////////////////////////////////////////////////////////
         /// Updating of data in table 'TBL_DW_CATEGORY_DIM'.
         /// The data from table 'TBL_DW_CATEGORY_DIM_TEMP' will be used
         /// to update data in the table 'TBL_DW_CATEGORY_DIM_DIM'.
         ////////////////////////////////////////////////////////////////////////
        /// log Errors :
        String[] sourceFields = new String[]{"CATEG1_ID","CATEG2_ID","CATEG3_ID"};
        String[] foreignKeys = new String[]{"STORE_DIM_ID"};

        List<StringBuffer> errors = verifyConstraints(connection, sourceFields,  foreignKeys,  tblDwCategoryTemp);

        int insertedRowCount = 0;
        int updatedRowCount = 0;
        int errorRowCount = errors.size();
        if (errorRowCount > 0){
          throw new DataNotFoundException(action + " :: <ERROR> It is impossible to update "+tblDwCategory+". NULL foreign key occured in "+tblDwCategoryTemp+". " + errors.toString());
        } else {
         /// Updating of data in table 'TBL_DW_CATEGORY_DIM'

         try {
             sql =
                 "UPDATE " + tblDwCategory + " dw " +
                 "SET " +
                 "( " +
                     "CATEGORY1, " +
                     "CATEGORY2, " +
                     "CATEGORY3, " +
                     "JD_CATEGORY1, " +
                     "JD_CATEGORY2, " +
                     "JD_CATEGORY3, " +
                     "CATEG1_ID, " +
                     "CATEG2_ID, " +
                     "CATEG3_ID, " +
                     "JD_CATEG1_ID, " +
                     "JD_CATEG2_ID, " +
                     "JD_CATEG3_ID, " +
                     "STORE_CATALOG_ID, " +
                     "MOD_BY, " +
                     "MOD_DATE " +
                 ") " +
                 "= " +
                 "( " +
                     "SELECT " +
                         "tmp.CATEGORY1, " +
                         "tmp.CATEGORY2, " +
                         "tmp.CATEGORY3, " +
                         "tmp.JD_CATEGORY1, " +
                         "tmp.JD_CATEGORY2, " +
                         "tmp.JD_CATEGORY3, " +
                         "tmp.CATEG1_ID, " +
                         "tmp.CATEG2_ID, " +
                         "tmp.CATEG3_ID, " +
                         "tmp.JD_CATEG1_ID, " +
                         "tmp.JD_CATEG2_ID, " +
                         "tmp.JD_CATEG3_ID, " +
                         "tmp.STORE_CATALOG_ID, " +
                         "?, " +
                         "SYSDATE " +
                     "FROM " +
                         tblDwCategoryTemp + " tmp " +
                     "WHERE " +
                         "dw.CATEGORY_DIM_ID = tmp.CATEGORY_DIM_ID " +

                 ") " +
                 "WHERE dw.CATEGORY_DIM_ID IN " +
                     "( " +
                         "SELECT DISTINCT " +
                             "CATEGORY_DIM_ID " +
                         "FROM " +
                             tblDwCategoryTemp + " tmp " +
                         "WHERE " +
                             "UPDATE_TYPE = 'TO_UPDATE' " +
                     ")";
             stmt = connection.prepareStatement(sql);
             stmt.setString(1, userName);
             updatedRowCount = stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of data in the table " +
                 tblDwCategory + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Insert of new records into table 'TBL_DW_CATEGORY_DIM'.
         /// New records (in the table 'TBL_DW_CATEGORY_DIM_TEMP') have
         /// the NULL value in the field 'CATEGORY_DIM_ID'.

         try {
             sql =
                 "INSERT INTO " + tblDwCategory + " dw " +
                 "( " +
                     "STORE_DIM_ID, " +
                     "CATEGORY_DIM_ID, " +
                     "CATEGORY1, " +
                     "CATEGORY2, " +
                     "CATEGORY3, " +
                     "JD_CATEGORY1, " +
                     "JD_CATEGORY2, " +
                     "JD_CATEGORY3, " +
                     "CATEG1_ID, " +
                     "CATEG2_ID, " +
                     "CATEG3_ID, " +
                     "JD_CATEG1_ID, " +
                     "JD_CATEG2_ID, " +
                     "JD_CATEG3_ID, " +
                     "STORE_CATALOG_ID, " +
                     "ACCOUNT_CATALOG_ID, " +
                     "ADD_BY, " +
                     "ADD_DATE, " +
                     "MOD_BY, " +
                     "MOD_DATE " +
                 ") " +
                 "SELECT " +
                     "tmp.STORE_DIM_ID, " +
                     tblDwCategorySeq + ".NEXTVAL, " +
                     "tmp.CATEGORY1, " +
                     "tmp.CATEGORY2, " +
                     "tmp.CATEGORY3, " +
                     "tmp.JD_CATEGORY1, " +
                     "tmp.JD_CATEGORY2, " +
                     "tmp.JD_CATEGORY3, " +
                     "tmp.CATEG1_ID, " +
                     "tmp.CATEG2_ID, " +
                     "tmp.CATEG3_ID, " +
                     "tmp.JD_CATEG1_ID, " +
                     "tmp.JD_CATEG2_ID, " +
                     "tmp.JD_CATEG3_ID, " +
                     "tmp.STORE_CATALOG_ID, " +
                     "tmp.ACCOUNT_CATALOG_ID, " +
                     "?, " +
                     "SYSDATE, " +
                     "?, " +
                     "SYSDATE " +
                 "FROM " +
                     tblDwCategoryTemp + " tmp " +
                 "WHERE " +
                     "tmp.CATEGORY_DIM_ID IS NULL";
             stmt = connection.prepareStatement(sql);
             stmt.setString(1, userName);
             stmt.setString(2, userName);
             insertedRowCount = stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at inserting of records into the table " +
                 tblDwCategory + ". " + ex.getMessage() +
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }
        }//end if errors
        ///
        /// REPORT
        StringBuffer report = logReport(tblDwCategory, insertedRowCount, updatedRowCount, errors  );
        return report;

       }

 /*    private static StringBuffer fillDwCategoryDimension(Connection connection,
         String userName, String dbClwNamespace, String dbDwNamespace,
         int exploredStoreId, int linkedStoreId, int dwStoreDimId) throws SQLException, Exception {

         if (connection == null) {
             throw new SQLException("Connection is null!");
         }

         final String tblClwCatalog = getFullTableName(dbClwNamespace, TBL_CLW_CATALOG);
         final String tblClwCatalogAssoc = getFullTableName(dbClwNamespace, TBL_CLW_CATALOG_ASSOC);
         final String tblClwItem = getFullTableName(dbClwNamespace, TBL_CLW_ITEM);
         final String tblClwItemAssoc = getFullTableName(dbClwNamespace, TBL_CLW_ITEM_ASSOC);

         final String tblDwStore = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_STORE_DIM);
         final String tblDwCategory = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_CATEGORY_DIM);
         final String tblDwCategoryTemp = getFullTableName(dbDwNamespace, TBL_DW_CATEGORY_DIM_TEMP);
         final String tblDwCategorySeq = getFullTableName(dbDwNamespace, TBL_DW_CATEGORY_DIM_SEQ);
         final String action = "< DataWarehouseBean >[" + tblDwCategory + " Creation] ";

         String sql = null;
         ResultSet resSet = null;
         PreparedStatement stmt = null;
         boolean isExistTempTable = false;
         int catalogId = 0;
         int jdCatalogId = 0;

         ////////////////////////////////////////////////////////////////////////
         /// Creation of temporal table 'TBL_DW_CATEGORY_DIM_TEMP'.
         /// The table 'TBL_DW_CATEGORY_DIM_TEMP' contains data
         /// to update table 'TBL_DW_CATEGORY_DIM' from Data Warehouse.
         ////////////////////////////////////////////////////////////////////////

         /// Checking of existence of table 'TBL_DW_CATEGORY_DIM_TEMP'
         try {
             isExistTempTable = checkDbTableOnExistence(connection,
                 dbDwNamespace, TBL_DW_CATEGORY_DIM_TEMP);
         }
         catch (SQLException ex) {
             String msg = "An error occurred at checking existence of table " +
                 tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Deleting of already existing table 'TBL_DW_CATEGORY_DIM_TEMP'
         if (isExistTempTable) {
             try {
                 sql = "DROP TABLE " + tblDwCategoryTemp + " PURGE";
                 stmt = connection.prepareStatement(sql);
                 stmt.executeUpdate();
                 stmt.close();
             }
             catch (SQLException ex) {
                 String msg = "An error occurred at deleting of table " +
                     tblDwCategoryTemp + ". " + ex.getMessage();
                 throw new SQLException("^clw^"+msg+ "^clw^");
             }
         }

         /// Creation of the table 'TBL_DW_CATEGORY_DIM_TEMP'
         try {
             sql =
                 "CREATE TABLE " + tblDwCategoryTemp + " " +
                 "( " +
                     "STORE_DIM_ID        NUMBER NULL, " +
                     "CATEGORY_DIM_ID     NUMBER NULL, " +
                     "CATEGORY1           VARCHAR2(255) NULL, " +
                     "CATEGORY2           VARCHAR2(255) NULL, " +
                     "CATEGORY3           VARCHAR2(255) NULL, " +
                     "JD_CATEGORY1        VARCHAR2(255) NULL, " +
                     "JD_CATEGORY2        VARCHAR2(255) NULL, " +
                     "JD_CATEGORY3        VARCHAR2(255) NULL, " +
                     "CATEG1_ID           NUMBER NULL, " +
                     "CATEG2_ID           NUMBER NULL, " +
                     "CATEG3_ID           NUMBER NULL, " +
                     "JD_CATEG1_ID        NUMBER NULL, " +
                     "JD_CATEG2_ID        NUMBER NULL, " +
                     "JD_CATEG3_ID        NUMBER NULL, " +
                     "STORE_CATALOG_ID    NUMBER NULL, " +
                     "ACCOUNT_CATALOG_ID  NUMBER NULL, " +
                     "STORE_ID            NUMBER NULL, " +
                     "ITEM_ID             NUMBER NULL, " +
                     "JD_STORE_ID         NUMBER NULL, " +
                     "JD_STORE_CATALOG_ID NUMBER NULL, " +
                     "JD_ITEM_ID          NUMBER NULL, " +
                     "UPDATE_TYPE         VARCHAR2(30) NULL " +
                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at creation of table " +
                 tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         ////////////////////////////////////////////////////////////////////////
         /// Filling of temporal 'TBL_DW_CATEGORY_DIM_TEMP'-table by data.
         ////////////////////////////////////////////////////////////////////////

         /// Definition of the catalog identifier for main store
         try {
             sql =
                 "SELECT " +
                     "catalogAssoc.CATALOG_ID " +
                 "FROM " +
                     tblClwCatalogAssoc+ " catalogAssoc INNER JOIN " +
                     "( " +
                         tblClwCatalog + " catalog " +
                     ") " +
                     "ON catalogAssoc.CATALOG_ID = catalog.CATALOG_ID AND " +
                         "catalogAssoc.CATALOG_ASSOC_CD = 'CATALOG_STORE' AND " +
                             "catalog.CATALOG_TYPE_CD = 'STORE' " +
                 "WHERE " +
                     "catalogAssoc.BUS_ENTITY_ID = ?";

             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, exploredStoreId);
             resSet = stmt.executeQuery();
             if (resSet.next()) {
                 catalogId = resSet.getInt(1);

             }
             resSet.close();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at definition of the catalog for table " +
                 tblDwCategoryTemp + " for store " + exploredStoreId + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }


        /// Definition of the catalog identifier for linked ("JohnsonDiversey") store
         try {
             sql =
                 "SELECT " +
                     "catalogAssoc.CATALOG_ID " +
                 "FROM " +
                     tblClwCatalogAssoc+ " catalogAssoc INNER JOIN " +
                     "( " +
                         tblClwCatalog + " catalog " +
                     ") " +
                     "ON catalogAssoc.CATALOG_ID = catalog.CATALOG_ID AND " +
                         "catalogAssoc.CATALOG_ASSOC_CD = 'CATALOG_STORE' AND " +
                             "catalog.CATALOG_TYPE_CD = 'STORE' " +
                 "WHERE " +
                     "catalogAssoc.BUS_ENTITY_ID = ?";

             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, linkedStoreId);
             resSet = stmt.executeQuery();
             if (resSet.next()) {
                 jdCatalogId = resSet.getInt(1);
                 }
             resSet.close();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at definition of the catalog for table " +
                 tblDwCategoryTemp + " for store " + exploredStoreId + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Insert of data from main store into temporal table.
         try {
             sql =
                 "INSERT INTO " + tblDwCategoryTemp + " \n" +
                 "SELECT DISTINCT \n" +
                     dwStoreDimId + ", \n" +            // STORE_DIM_ID        NUMBER NULL
                     "NULL, \n" +                       // CATEGORY_DIM_ID     NUMBER NULL
                     "category.SHORT_DESC, \n" +        // CATEGORY1           VARCHAR2(255) NULL
                     "NULL, \n" +                       // CATEGORY2           VARCHAR2(255) NULL
                     "NULL, \n" +                       // CATEGORY3           VARCHAR2(255) NULL
                     "category.SHORT_DESC, \n" +        // JD_CATEGORY1        VARCHAR2(255) NULL
                     "NULL, \n" +                       // JD_CATEGORY2        VARCHAR2(255) NULL
                     "NULL, \n" +                       // JD_CATEGORY3        VARCHAR2(255) NULL
                     "category.ITEM_ID, \n" +           // CATEG1_ID           NUMBER NULL
                     "NULL, \n" +                       // CATEG2_ID           NUMBER NULL
                     "NULL, \n" +                       // CATEG3_ID           NUMBER NULL
                     "category.ITEM_ID, \n" +           // JD_CATEG1_ID        NUMBER NULL
                     "NULL, \n" +                       // JD_CATEG2_ID        NUMBER NULL
                     "NULL, \n" +                       // JD_CATEG3_ID        NUMBER NULL
                     catalogId + ", \n" +               // STORE_CATALOG_ID    NUMBER NULL
                     "NULL, \n" +                       // ACCOUNT_CATALOG_ID  NUMBER NULL
                     exploredStoreId + ", \n" +         // STORE_ID            NUMBER NULL
                     "NULL, \n" +                       // ITEM_ID             NUMBER NULL
                     linkedStoreId + ", \n" +           // JD_STORE_ID         NUMBER NULL
                     jdCatalogId + ", \n" +             // JD_STORE_CATALOG_ID NUMBER NULL
                     "NULL, \n" +                       // JD_ITEM_ID          NUMBER NULL
                     "NULL \n" +                        // UPDATE_TYPE         VARCHAR2(30) NULL
                 "FROM  \n" +
                     tblClwItemAssoc + " categoryAssoc INNER JOIN \n" +
                     "( \n" +
                         tblClwItem + " category \n" +
                     ") \n" +
                     "ON categoryAssoc.ITEM2_ID = category.ITEM_ID AND \n" +
                         "categoryAssoc.ITEM_ASSOC_CD = 'PRODUCT_PARENT_CATEGORY' \n" +
                 "WHERE \n" +
                     "categoryAssoc.CATALOG_ID = ?\n";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, catalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at insertion of records into table " +
                 tblDwCategoryTemp + " for store " + exploredStoreId + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the auxiliary fields 'ITEM_ID' and 'JD_ITEM_ID' in the table DW_CATEGORY_DIM_TEMP.
         /// 'ITEM_ID' - is the identifier of product which have the "cross link" with
         /// the proper product from linked ("JohnsonDiversey") store.
         /// 'JD_ITEM_ID' - is the identifier of product linked with 'ITEM_ID'.
         try {
             sql =
                "UPDATE " + tblDwCategoryTemp + " tmp SET tmp.ITEM_ID = \n" +
                 "( \n" +
                     "SELECT \n" +
                         "MIN(itemItemLink.ITEM1_ID) \n" +
                     "FROM \n" +
                         tblClwItemAssoc + " itemCategLink, \n" +
                         tblClwItemAssoc + " itemItemLink \n" +
                     "WHERE \n" +
                         "itemCategLink.ITEM_ASSOC_CD = 'PRODUCT_PARENT_CATEGORY' AND \n" +
                         "itemItemLink.ITEM_ASSOC_CD = 'CROSS_STORE_ITEM_LINK' AND \n" +
                         "itemCategLink.ITEM1_ID = itemItemLink.ITEM1_ID AND \n" +
                         "itemCategLink.ITEM2_ID = tmp.CATEG1_ID AND \n" +
                         "itemCategLink.CATALOG_ID = ? AND \n" +
                         "itemItemLink.CATALOG_ID = ? \n" +
                 ")";

             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, catalogId);
             stmt.setInt(2, jdCatalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of auxiliary field 'ITEM_ID' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the auxiliary field 'JD_ITEM_ID' in the table DW_CATEGORY_DIM_TEMP.
         try {
             sql =
                "UPDATE " + tblDwCategoryTemp + " tmp SET tmp.JD_ITEM_ID = \n" +
                 "( \n" +
                     "SELECT \n" +
                     "MIN(itemItemLink.ITEM2_ID) \n" +
                     "FROM \n" +
                         tblClwItemAssoc + " itemItemLink \n" +
                     "WHERE \n" +
                      "itemItemLink.ITEM_ASSOC_CD = 'CROSS_STORE_ITEM_LINK' AND \n" +
                         "itemItemLink.ITEM1_ID = tmp.ITEM_ID AND \n" +
                         "itemItemLink.CATALOG_ID = ? \n" +
                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, jdCatalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of auxiliary field 'JD_ITEM_ID' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the fields 'CATEG2_ID' and 'CATEGORY2' in the table 'TBL_DW_CATEGORY_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp \n" +
                 "SET \n" +
                 "( \n" +
                     "tmp.CATEGORY2, \n" +
                     "tmp.CATEG2_ID \n" +
                 ") \n" +
                 "= \n" +
                 "( \n" +
                     "SELECT \n" +
                         "category.SHORT_DESC, \n" +
                         "category.ITEM_ID \n" +
                     "FROM \n" +
                         tblClwItem + " category INNER JOIN \n" +
                         "( \n" +
                             tblClwItemAssoc + " categoryAssoc \n" +
                         ") \n" +
//                         "ON category.ITEM_ID = categoryAssoc.ITEM1_ID AND \n" +
                         "ON category.ITEM_ID = categoryAssoc.ITEM2_ID AND \n" +
                             "categoryAssoc.ITEM_ASSOC_CD = 'CATEGORY_PARENT_CATEGORY' AND \n" +
                                 "categoryAssoc.CATALOG_ID = ? \n" +
                     "WHERE " +
//                         "tmp.CATEG1_ID = categoryAssoc.ITEM2_ID \n" +
                         "tmp.CATEG1_ID = categoryAssoc.ITEM1_ID \n" +
                 ") " +
                 "WHERE tmp.CATEG1_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, catalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of fields 'CATEG2_ID' and 'CATEGORY2' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the fields 'CATEG3_ID' and 'CATEGORY3' in the table 'TBL_DW_CATEGORY_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp \n" +
                 "SET \n" +
                 "( \n" +
                     "tmp.CATEGORY3, \n" +
                     "tmp.CATEG3_ID \n" +
                 ") \n" +
                 "= \n" +
                 "( \n" +
                     "SELECT \n" +
                         "category.SHORT_DESC, \n" +
                         "category.ITEM_ID \n" +
                     "FROM \n" +
                         tblClwItem + " category INNER JOIN \n" +
                         "( \n" +
                             tblClwItemAssoc + " categoryAssoc \n" +
                         ") \n" +
//                         "ON category.ITEM_ID = categoryAssoc.ITEM1_ID AND \n" +
                         "ON category.ITEM_ID = categoryAssoc.ITEM2_ID AND \n" +
                             "categoryAssoc.ITEM_ASSOC_CD = 'CATEGORY_PARENT_CATEGORY' AND \n" +
                                 "categoryAssoc.CATALOG_ID = ? \n" +
                     "WHERE \n" +
//                         "tmp.CATEG2_ID = categoryAssoc.ITEM2_ID \n" +
                         "tmp.CATEG2_ID = categoryAssoc.ITEM1_ID \n" +
                 ") \n" +
                 "WHERE tmp.CATEG2_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, catalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of fields 'CATEG3_ID' and 'CATEGORY3' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the field 'JD_CATEG1_ID' in the table 'TBL_DW_CATEGORY_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp SET tmp.JD_CATEG1_ID = \n" +
                 "( \n" +
                     "SELECT \n" +
                         "MIN(itemCategLink.ITEM2_ID) \n" +
                     "FROM \n" +
                         tblClwItemAssoc + " itemCategLink \n" +
                     "WHERE \n" +
                         "itemCategLink.ITEM_ASSOC_CD = 'PRODUCT_PARENT_CATEGORY' AND \n" +
                         "itemCategLink.ITEM1_ID = tmp.JD_ITEM_ID AND \n" +
                         "itemCategLink.CATALOG_ID = ? \n" +
                 ")";

             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, jdCatalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'JD_CATEG1_ID' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the field 'JD_CATEGORY1' in the table 'TBL_DW_CATEGORY_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp SET tmp.JD_CATEGORY1 = \n" +
                 "( \n" +
                     "SELECT \n" +
                         "category.SHORT_DESC \n" +
                     "FROM \n" +
                         tblClwItem + " category \n" +
                     "WHERE \n" +
                         "category.ITEM_ID = tmp.JD_CATEG1_ID \n" +
                 ") \n" +
                 "WHERE \n" +
                     "tmp.JD_CATEG1_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'JD_CATEGORY1' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the fields 'JD_CATEG2_ID' and 'JD_CATEGORY2' in the table 'TBL_DW_CATEGORY_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp \n" +
                 "SET \n" +
                 "( \n" +
                     "tmp.JD_CATEGORY2, \n" +
                     "tmp.JD_CATEG2_ID \n" +
                 ") \n" +
                 "= \n" +
                 "( \n" +
                     "SELECT \n" +
                         "category.SHORT_DESC, \n" +
                         "category.ITEM_ID \n" +
                     "FROM " +
                         tblClwItem + " category INNER JOIN \n" +
                         "( \n" +
                             tblClwItemAssoc + " categoryAssoc \n" +
                         ") \n" +
//NG                         "ON category.ITEM_ID = categoryAssoc.ITEM1_ID AND \n" +
                         "ON category.ITEM_ID = categoryAssoc.ITEM2_ID AND \n" +
                             "categoryAssoc.ITEM_ASSOC_CD = 'CATEGORY_PARENT_CATEGORY' AND \n" +
                                 "categoryAssoc.CATALOG_ID = ? \n" +
                     "WHERE \n" +
//NG                         "tmp.JD_CATEG1_ID = categoryAssoc.ITEM2_ID \n" +
                         "tmp.JD_CATEG1_ID = categoryAssoc.ITEM1_ID \n" +
                 ") \n" +
                 "WHERE tmp.JD_CATEG1_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, jdCatalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of fields 'JD_CATEG2_ID' and 'JD_CATEGORY2' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the fields 'JD_CATEG3_ID' and 'JD_CATEGORY3' in the table 'TBL_DW_CATEGORY_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp \n" +
                 "SET \n" +
                 "( \n" +
                     "tmp.JD_CATEGORY3, \n" +
                     "tmp.JD_CATEG3_ID \n" +
                 ") \n" +
                 "= \n" +
                 "( \n" +
                     "SELECT \n" +
                         "category.SHORT_DESC, \n" +
                         "category.ITEM_ID \n" +
                     "FROM \n" +
                         tblClwItem + " category INNER JOIN \n" +
                         "( " +
                             tblClwItemAssoc + " categoryAssoc " +
                         ") \n" +
//NG                         "ON category.ITEM_ID = categoryAssoc.ITEM1_ID AND \n" +
                         "ON category.ITEM_ID = categoryAssoc.ITEM2_ID AND \n" +
                             "categoryAssoc.ITEM_ASSOC_CD = 'CATEGORY_PARENT_CATEGORY' AND \n" +
                                 "categoryAssoc.CATALOG_ID = ? \n" +
                     "WHERE \n" +
//NG                         "tmp.JD_CATEG2_ID = categoryAssoc.ITEM2_ID \n" +
                         "tmp.JD_CATEG2_ID = categoryAssoc.ITEM1_ID \n" +
                 ") \n" +
                 "WHERE tmp.JD_CATEG2_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, jdCatalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of fields 'JD_CATEG3_ID' and 'JD_CATEGORY3' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }


         /// Fill the field 'CATEGORY_DIM_ID' in the table 'TBL_DW_CATEGORY_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp SET tmp.CATEGORY_DIM_ID = \n" +
                 "( \n" +
                     "SELECT \n" +
                         "dw.CATEGORY_DIM_ID \n" +
                     "FROM \n" +
                         tblDwCategory + " dw \n" +
                     "WHERE \n" +
                         "tmp.CATEG1_ID = dw.CATEG1_ID AND \n" +
                         "NVL(tmp.JD_CATEG1_ID,0) = NVL(dw.JD_CATEG1_ID,0) AND \n" +
                         "dw.STORE_DIM_ID =" + dwStoreDimId +

                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'CATEGORY_DIM_ID' " +
                 "in the table " + tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Updating of the field UPDATE_TYPE in table 'TBL_DW_CATEGORY_DIM_TEMP'.
         /// Set the value of field 'UPDATE_TYPE' in table 'TBL_DW_CATEGORY_DIM_TEMP' into 'TO_UPDATE' for
         /// records which have the similar changed records in table 'TBL_DW_CATEGORY_DIM_DIM'.
         try {
             sql =
                 "UPDATE " + tblDwCategoryTemp + " tmp " +
                 "SET tmp.UPDATE_TYPE = " +
                 "( " +
                     "SELECT " +
                         "'TO_UPDATE' " +
                     "FROM " +
                         tblDwCategory + " dw " +
                     "WHERE " +
                         "dw.CATEGORY_DIM_ID = tmp.CATEGORY_DIM_ID AND " +
                         "( " +
                             "dw.CATEGORY1 <> tmp.CATEGORY1 OR " +
                             "(dw.CATEGORY1 IS NULL AND tmp.CATEGORY1 IS NOT NULL) OR " +
                             "(dw.CATEGORY1 IS NOT NULL AND tmp.CATEGORY1 IS NULL) OR " +
                             "dw.CATEG2_ID <> tmp.CATEG2_ID OR " +
                             "(dw.CATEG2_ID IS NULL AND tmp.CATEG2_ID IS NOT NULL) OR " +
                             "(dw.CATEG2_ID IS NOT NULL AND tmp.CATEG2_ID IS NULL) OR " +
                             "dw.CATEGORY2 <> tmp.CATEGORY2 OR " +
                             "(dw.CATEGORY2 IS NULL AND tmp.CATEGORY2 IS NOT NULL) OR " +
                             "(dw.CATEGORY2 IS NOT NULL AND tmp.CATEGORY2 IS NULL) OR " +
                             "dw.CATEG3_ID <> tmp.CATEG3_ID OR " +
                             "(dw.CATEG3_ID IS NULL AND tmp.CATEG3_ID IS NOT NULL) OR " +
                             "(dw.CATEG3_ID IS NOT NULL AND tmp.CATEG3_ID IS NULL) OR " +
                             "dw.CATEGORY3 <> tmp.CATEGORY3 OR " +
                             "(dw.CATEGORY3 IS NULL AND tmp.CATEGORY3 IS NOT NULL) OR " +
                             "(dw.CATEGORY3 IS NOT NULL AND tmp.CATEGORY3 IS NULL) OR " +
                             "dw.JD_CATEG1_ID <> tmp.JD_CATEG1_ID OR " +
                             "(dw.JD_CATEG1_ID IS NULL AND tmp.JD_CATEG1_ID IS NOT NULL) OR " +
                             "(dw.JD_CATEG1_ID IS NOT NULL AND tmp.JD_CATEG1_ID IS NULL) OR " +
                             "dw.JD_CATEGORY1 <> tmp.JD_CATEGORY1 OR " +
                             "(dw.JD_CATEGORY1 IS NULL AND tmp.JD_CATEGORY1 IS NOT NULL) OR " +
                             "(dw.JD_CATEGORY1 IS NOT NULL AND tmp.JD_CATEGORY1 IS NULL) OR " +
                             "dw.JD_CATEG2_ID <> tmp.JD_CATEG2_ID OR " +
                             "(dw.JD_CATEG2_ID IS NULL AND tmp.JD_CATEG2_ID IS NOT NULL) OR " +
                             "(dw.JD_CATEG2_ID IS NOT NULL AND tmp.JD_CATEG2_ID IS NULL) OR " +
                             "dw.JD_CATEGORY2 <> tmp.JD_CATEGORY2 OR " +
                             "(dw.JD_CATEGORY2 IS NULL AND tmp.JD_CATEGORY2 IS NOT NULL) OR " +
                             "(dw.JD_CATEGORY2 IS NOT NULL AND tmp.JD_CATEGORY2 IS NULL) OR " +
                             "dw.JD_CATEG3_ID <> tmp.JD_CATEG3_ID OR " +
                             "(dw.JD_CATEG3_ID IS NULL AND tmp.JD_CATEG3_ID IS NOT NULL) OR " +
                             "(dw.JD_CATEG3_ID IS NOT NULL AND tmp.JD_CATEG3_ID IS NULL) OR " +
                             "dw.JD_CATEGORY3 <> tmp.JD_CATEGORY3 OR " +
                             "(dw.JD_CATEGORY3 IS NULL AND tmp.JD_CATEGORY3 IS NOT NULL) OR " +
                             "(dw.JD_CATEGORY3 IS NOT NULL AND tmp.JD_CATEGORY3 IS NULL) OR " +
                             "dw.STORE_CATALOG_ID <> tmp.STORE_CATALOG_ID OR " +
                             "(dw.STORE_CATALOG_ID IS NULL AND tmp.STORE_CATALOG_ID IS NOT NULL) OR " +
                             "(dw.STORE_CATALOG_ID IS NOT NULL AND tmp.STORE_CATALOG_ID IS NULL) " +
                         ") " +
                 ") " +
                "WHERE tmp.CATEGORY_DIM_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of 'UPDATE_TYPE' field in the table " +
                 tblDwCategoryTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         ////////////////////////////////////////////////////////////////////////
         /// Updating of data in table 'TBL_DW_CATEGORY_DIM'.
         /// The data from table 'TBL_DW_CATEGORY_DIM_TEMP' will be used
         /// to update data in the table 'TBL_DW_CATEGORY_DIM_DIM'.
         ////////////////////////////////////////////////////////////////////////
        /// log Errors :
        String[] sourceFields = new String[]{"CATEG1_ID","CATEG2_ID","CATEG3_ID"};
        String[] foreignKeys = new String[]{"STORE_DIM_ID"};

        List<StringBuffer> errors = verifyConstraints(connection, sourceFields,  foreignKeys,  tblDwCategoryTemp);

        int insertedRowCount = 0;
        int updatedRowCount = 0;
        int errorRowCount = errors.size();
        if (errorRowCount > 0){
          throw new DataNotFoundException(action + " :: <ERROR> It is impossible to update "+tblDwCategory+". NULL foreign key occured in "+tblDwCategoryTemp+".");
        } else {
         /// Updating of data in table 'TBL_DW_CATEGORY_DIM'

         try {
             sql =
                 "UPDATE " + tblDwCategory + " dw " +
                 "SET " +
                 "( " +
                     "CATEGORY1, " +
                     "CATEGORY2, " +
                     "CATEGORY3, " +
                     "JD_CATEGORY1, " +
                     "JD_CATEGORY2, " +
                     "JD_CATEGORY3, " +
                     "CATEG1_ID, " +
                     "CATEG2_ID, " +
                     "CATEG3_ID, " +
                     "JD_CATEG1_ID, " +
                     "JD_CATEG2_ID, " +
                     "JD_CATEG3_ID, " +
                     "STORE_CATALOG_ID, " +
                     "MOD_BY, " +
                     "MOD_DATE " +
                 ") " +
                 "= " +
                 "( " +
                     "SELECT " +
                         "tmp.CATEGORY1, " +
                         "tmp.CATEGORY2, " +
                         "tmp.CATEGORY3, " +
                         "tmp.JD_CATEGORY1, " +
                         "tmp.JD_CATEGORY2, " +
                         "tmp.JD_CATEGORY3, " +
                         "tmp.CATEG1_ID, " +
                         "tmp.CATEG2_ID, " +
                         "tmp.CATEG3_ID, " +
                         "tmp.JD_CATEG1_ID, " +
                         "tmp.JD_CATEG2_ID, " +
                         "tmp.JD_CATEG3_ID, " +
                         "tmp.STORE_CATALOG_ID, " +
                         "?, " +
                         "SYSDATE " +
                     "FROM " +
                         tblDwCategoryTemp + " tmp " +
                     "WHERE " +
                         "dw.CATEGORY_DIM_ID = tmp.CATEGORY_DIM_ID " +

                 ") " +
                 "WHERE dw.CATEGORY_DIM_ID IN " +
                     "( " +
                         "SELECT DISTINCT " +
                             "CATEGORY_DIM_ID " +
                         "FROM " +
                             tblDwCategoryTemp + " tmp " +
                         "WHERE " +
                             "UPDATE_TYPE = 'TO_UPDATE' " +
                     ")";
             stmt = connection.prepareStatement(sql);
             stmt.setString(1, userName);
             updatedRowCount = stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of data in the table " +
                 tblDwCategory + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Insert of new records into table 'TBL_DW_CATEGORY_DIM'.
         /// New records (in the table 'TBL_DW_CATEGORY_DIM_TEMP') have
         /// the NULL value in the field 'CATEGORY_DIM_ID'.

         try {
             sql =
                 "INSERT INTO " + tblDwCategory + " dw " +
                 "( " +
                     "STORE_DIM_ID, " +
                     "CATEGORY_DIM_ID, " +
                     "CATEGORY1, " +
                     "CATEGORY2, " +
                     "CATEGORY3, " +
                     "JD_CATEGORY1, " +
                     "JD_CATEGORY2, " +
                     "JD_CATEGORY3, " +
                     "CATEG1_ID, " +
                     "CATEG2_ID, " +
                     "CATEG3_ID, " +
                     "JD_CATEG1_ID, " +
                     "JD_CATEG2_ID, " +
                     "JD_CATEG3_ID, " +
                     "STORE_CATALOG_ID, " +
                     "ACCOUNT_CATALOG_ID, " +
                     "ADD_BY, " +
                     "ADD_DATE, " +
                     "MOD_BY, " +
                     "MOD_DATE " +
                 ") " +
                 "SELECT " +
                     "tmp.STORE_DIM_ID, " +
                     tblDwCategorySeq + ".NEXTVAL, " +
                     "tmp.CATEGORY1, " +
                     "tmp.CATEGORY2, " +
                     "tmp.CATEGORY3, " +
                     "tmp.JD_CATEGORY1, " +
                     "tmp.JD_CATEGORY2, " +
                     "tmp.JD_CATEGORY3, " +
                     "tmp.CATEG1_ID, " +
                     "tmp.CATEG2_ID, " +
                     "tmp.CATEG3_ID, " +
                     "tmp.JD_CATEG1_ID, " +
                     "tmp.JD_CATEG2_ID, " +
                     "tmp.JD_CATEG3_ID, " +
                     "tmp.STORE_CATALOG_ID, " +
                     "tmp.ACCOUNT_CATALOG_ID, " +
                     "?, " +
                     "SYSDATE, " +
                     "?, " +
                     "SYSDATE " +
                 "FROM " +
                     tblDwCategoryTemp + " tmp " +
                 "WHERE " +
                     "tmp.CATEGORY_DIM_ID IS NULL";
             stmt = connection.prepareStatement(sql);
             stmt.setString(1, userName);
             stmt.setString(2, userName);
             insertedRowCount = stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at inserting of records into the table " +
                 tblDwCategory + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }
        }//end if errors
        ///
        /// REPORT
        StringBuffer report = logReport(tblDwCategory, insertedRowCount, updatedRowCount, errors  );
        return report;

       }
*/
     private  StringBuffer fillDwItemDimension(Connection connection,
         String userName, String dbClwNamespace, String dbDwNamespace,
         int exploredStoreId, int linkedStoreId, int dwStoreDimId) throws SQLException, MultipleDataException, Exception {

         if (connection == null) {
             throw new SQLException("Connection is null!");
         }

         final String tblClwCatalog = getFullTableName(dbClwNamespace, TBL_CLW_CATALOG);
         final String tblClwCatalogAssoc = getFullTableName(dbClwNamespace, TBL_CLW_CATALOG_ASSOC);
         final String tblClwItem = getFullTableName(dbClwNamespace, TBL_CLW_ITEM);
         final String tblClwItemAssoc = getFullTableName(dbClwNamespace, TBL_CLW_ITEM_ASSOC);
         final String tblClwItemMeta = getFullTableName(dbClwNamespace, TBL_CLW_ITEM_META);
         final String tblClwItemMapping = getFullTableName(dbClwNamespace, TBL_CLW_ITEM_MAPPING);
         final String tblClwGroup = getFullTableName(dbClwNamespace, TBL_CLW_GROUP);
         final String tblClwGroupAssoc = getFullTableName(dbClwNamespace, TBL_CLW_GROUP_ASSOC);

         final String tblDwStore = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_STORE_DIM);
         final String tblDwCategory = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_CATEGORY_DIM);
         final String tblDwItem = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_ITEM_DIM);
         final String tblDwManufDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_MANUFACTURER_DIM);
         final String tblDwItemTemp = getFullTableName(dwTempUser, TBL_DW_ITEM_DIM_TEMP, tempSuffix);
         final String tblDwItemSeq = getFullTableName(dbDwNamespace, TBL_DW_ITEM_DIM_SEQ);
         final String action = "< DataWarehouseBean >[" + tblDwItem + " Creation] ";

         String sql = null;
         ResultSet resSet = null;
         PreparedStatement stmt = null;
         boolean isExistTempTable = false;
         int catalogId = 0;
         int jdCatalogId = 0;
         ArrayList exceptions = new ArrayList();
         ////////////////////////////////////////////////////////////////////////
         /// Creation of temporal table 'TBL_DW_ITEM_DIM_TEMP'.
         /// The table 'TBL_DW_ITEM_DIM_TEMP' contains data
         /// to update table 'TBL_DW_ITEM_DIM' from Data Warehouse.
         ////////////////////////////////////////////////////////////////////////

/*         /// Checking of existence of table 'TBL_DW_ITEM_DIM_TEMP'
         try {
             isExistTempTable = checkDbTableOnExistence(connection,
                 dbDwNamespace, TBL_DW_ITEM_DIM_TEMP);
         }
         catch (SQLException ex) {
             String msg = "An error occurred at checking existence of table " +
                 tblDwItemTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }
*/
         /// Deleting of already existing table 'TBL_DW_ITEM_DIM_TEMP'
         if (isExistTempTable) {
             try {
                 sql = "DROP TABLE " + tblDwItemTemp + " PURGE";
                 stmt = connection.prepareStatement(sql);
                 stmt.executeUpdate();
                 stmt.close();
             }
             catch (SQLException ex) {
                 String msg = "An error occurred at deleting of table " +
                     tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
                 throw new SQLException("^clw^"+msg+ "^clw^");
             }
         }

         /// Creation of the table 'TBL_DW_ITEM_DIM_TEMP'
         try {
             sql =
                 "CREATE TABLE " + tblDwItemTemp + " " +
                 "( " +
                     "STORE_DIM_ID        NUMBER NULL, " +
                     "ITEM_DIM_ID         NUMBER NULL, " +
                     "CATEGORY_DIM_ID     NUMBER NULL, " +
                     "MANUFACTURER_DIM_ID NUMBER NULL, " +
                     "ITEM_ID             NUMBER NULL, " +
                     "ITEM_DESC           VARCHAR2(255) NULL, " +
                     "ITEM_PACK           VARCHAR2(30) NULL, " +
                     "ITEM_UOM            VARCHAR2(30) NULL, " +
                     "ITEM_SIZE           VARCHAR2(50) NULL, " +
                     "CLW_SKU             NUMBER NULL, " +
                     "MANUF_SKU           VARCHAR2(50) NULL, " +
                     "MANUF_ID            NUMBER NULL, " +
                     "JD_ITEM_ID          NUMBER NULL, " +
                     "JD_ITEM_DESC        VARCHAR2(255) NULL, " +
                     "JD_ITEM_PACK        VARCHAR2(30) NULL, " +
                     "JD_ITEM_UOM         VARCHAR2(30) NULL, " +
                     "JD_ITEM_SIZE        VARCHAR2(50) NULL, " +
                     "JD_CLW_SKU          NUMBER NULL, " +
                     "JD_MANUF_SKU        VARCHAR2(50) NULL, " +
                     "JD_MANUF_ID         NUMBER NULL, " +
                     "STORE_CATALOG_ID    NUMBER NULL, " +
                     "ACCOUNT_CATALOG_ID  NUMBER NULL, " +
                     "STORE_ID            NUMBER NULL, " +
                     "CATEGORY_ID         NUMBER NULL, " +
                     "JD_STORE_ID         NUMBER NULL, " +
                     "JD_STORE_CATALOG_ID NUMBER NULL, " +
                     "JD_CATEGORY_ID      NUMBER NULL, " +
                     "JD_ITEM_FL          VARCHAR2(5), " +
                     "UPDATE_TYPE         VARCHAR2(30) NULL " +
                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at creation of table " +
                 tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         ////////////////////////////////////////////////////////////////////////
         /// Filling of temporal 'TBL_DW_ITEM_DIM_TEMP'-table by data.
         ////////////////////////////////////////////////////////////////////////

         /// Definition of the catalog identifier for main store
         try {
             sql =
                 "SELECT " +
                     "catalogAssoc.CATALOG_ID " +
                 "FROM " +
                     tblClwCatalogAssoc +" catalogAssoc INNER JOIN " +
                     "( " +
                         tblClwCatalog + " catalog " +
                     ") " +
                     "ON catalogAssoc.CATALOG_ID = catalog.CATALOG_ID AND " +
                         "catalogAssoc.CATALOG_ASSOC_CD = 'CATALOG_STORE' AND " +
                             "catalog.CATALOG_TYPE_CD = 'STORE' " +
                 "WHERE " +
                     "catalogAssoc.BUS_ENTITY_ID = ?";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, exploredStoreId);
             resSet = stmt.executeQuery();
             if (resSet.next()) {
                 catalogId = resSet.getInt(1);

             }
             resSet.close();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at definition of the catalog for table " +
                 tblDwItemTemp + " for store " + exploredStoreId + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }


         /// Definition of the catalog identifier for linked ("JohnsonDiversey") store
         try {
             sql =
                 "SELECT " +
                     "catalogAssoc.CATALOG_ID " +
                 "FROM " +
                     tblClwCatalogAssoc + " catalogAssoc INNER JOIN " +
                     "( " +
                         tblClwCatalog + " catalog " +
                     ") " +
                     "ON catalogAssoc.CATALOG_ID = catalog.CATALOG_ID AND " +
                         "catalogAssoc.CATALOG_ASSOC_CD = 'CATALOG_STORE' AND " +
                             "catalog.CATALOG_TYPE_CD = 'STORE' " +
                 "WHERE " +
                     "catalogAssoc.BUS_ENTITY_ID = ?";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, linkedStoreId);
             resSet = stmt.executeQuery();
             if (resSet.next()) {
                 jdCatalogId = resSet.getInt(1);

             }
             resSet.close();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at definition of the catalog for table " +
                 tblDwItemTemp + " for store " + exploredStoreId + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Insert of data from store  into temporal table.
         try {
             sql =
                 "INSERT INTO " + tblDwItemTemp + " " +
                 "SELECT " +
                     dwStoreDimId + ", " +            // STORE_DIM_ID       NUMBER NULL
                     "NULL, " +                       // ITEM_DIM_ID        NUMBER NULL
                     "NULL, " +                     // CATEGORY_DIM_ID    NUMBER NULL
                     "NULL, " +                       // MANUFACTORY_DIM_ID    NUMBER NULL
                     "item.ITEM_ID, " +               // ITEM_ID            NUMBER NULL
                     "item.SHORT_DESC, " +            // ITEM_DESC          VARCHAR2(255) NULL
                     "NULL, " +                       // ITEM_PACK          VARCHAR2(30) NULL
                     "NULL, " +                       // ITEM_UOM           VARCHAR2(30) NULL
                     "NULL, " +                       // ITEM_SIZE           VARCHAR2(30) NULL
                     "item.SKU_NUM, " +               // CLW_SKU            NUMBER NULL
                     "NULL, " +                       // MANUF_SKU          VARCHAR2(50) NULL
                     "NULL, " +                       // MANUF_ID           NUMBER NULL
                     "NULL, " +                       // JD_ITEM_ID         NUMBER NULL
                     "NULL, " +                       // JD_ITEM_DESC       VARCHAR2(255) NULL
                     "NULL, " +                       // JD_ITEM_PACK       VARCHAR2(30) NULL
                     "NULL, " +                       // JD_ITEM_UOM        VARCHAR2(30) NULL
                     "NULL, " +                       // JD_ITEM_SIZE        VARCHAR2(30) NULL
                     "NULL, " +                       // JD_CLW_SKU         NUMBER NULL
                     "NULL, " +                       // JD_MANUF_SKU       VARCHAR2(50) NULL
                     "NULL, " +                       // JD_MANUF_ID        NUMBER NULL
                     catalogId + ", " +               // STORE_CATALOG_ID   NUMBER NULL
                     "NULL, " +                       // ACCOUNT_CATALOG_ID NUMBER NULL
                     exploredStoreId + ", " +         // STORE_ID            NUMBER NULL
                     "itemAssoc.ITEM2_ID, " +                       // CATEGORY_ID         NUMBER NULL
                     linkedStoreId + ", " +           // JD_STORE_ID         NUMBER NULL
                     jdCatalogId + ", " +             // JD_STORE_CATALOG_ID NUMBER NULL
                     "NULL, " +                       // JD_CATEGORY_ID      NUMBER NULL
                     "NULL, " +                       // JD_ITEM_FL         VARCHAR2(5) NULL
                     "NULL " +                        // UPDATE_TYPE        VARCHAR2(30) NULL
                 "FROM  " +
                     tblClwItem + " item INNER JOIN " +
                     "( " +
                         tblClwItemAssoc + " itemAssoc " +
                     ") " +
                     "ON item.ITEM_ID = itemAssoc.ITEM1_ID AND " +
                         "itemAssoc.ITEM_ASSOC_CD = 'PRODUCT_PARENT_CATEGORY' " +
                 "WHERE " +
                     "itemAssoc.CATALOG_ID = ?";

             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, catalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at insertion of records into table " +
                 tblDwItemTemp + " for store " + exploredStoreId + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }


         createIndex(connection, tblDwItemTemp, "I0" , "ITEM_ID" ) ;

         // Check for multiple records  per Item
         String err = this.checkMultipleCategPerItem(connection);
         if (Utility.isSet(err)){
           exceptions.add(err);
         }
         err = this.checkMultipleMetaPerItem(connection, "PACK", tblClwItemMeta );
         if (Utility.isSet(err)){
           exceptions.add(err);
         }
         err = this.checkMultipleMetaPerItem(connection, "SIZE", tblClwItemMeta );
         if (Utility.isSet(err)){
           exceptions.add(err);
         }
         err = this.checkMultipleMetaPerItem(connection, "UOM", tblClwItemMeta );
         if (Utility.isSet(err)){
           exceptions.add(err);
         }
         if (exceptions.size()>0){
           throw new MultipleDataException("^clw^"+"ERRORS : "+Utility.toCommaSting(exceptions)+"^clw^");
         }

         /// Fill the field 'ITEM_PACK' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
           String tblDwTemp = dwTempUser+"."+"DW_TEMP"+tempSuffix;
           boolean isExistTemp = checkDbTableOnExistence(connection,dwTempUser, "DW_TEMP"+tempSuffix);
           if (isExistTemp) {
             sql = "DROP TABLE " + tblDwTemp + " PURGE";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
           }

           sql = "CREATE TABLE " + tblDwTemp + " AS \n" +
                 " SELECT ITEM_ID, NAME_VALUE, CLW_VALUE \n" +
                 "   FROM " + tblClwItemMeta + " \n" +
                 "   WHERE ITEM_id IN \n" +
                 "         (SELECT ITEM_ID FROM " + tblDwItemTemp + ") \n" +
                 "     AND NAME_VALUE IN ('PACK','UOM','SIZE') ";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();

             createIndex(connection, tblDwTemp, "I01","ITEM_ID");
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp SET \n" +
                 "  tmp.ITEM_PACK = \n" +
                 "  (SELECT  pack.CLW_VALUE \n" +
                 "     FROM " +  tblDwTemp + " pack \n" +
                 "     WHERE pack.ITEM_ID = tmp.ITEM_ID AND \n" +
                 "           pack.NAME_VALUE = 'PACK'  \n" +
                 "       AND pack.CLW_VALUE is not NULL \n" +
                 " )  ";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
              sql =
                  "UPDATE " + tblDwItemTemp + " tmp SET \n" +
                  "  tmp.ITEM_UOM = \n" +
                  "  (SELECT  uom.CLW_VALUE \n" +
                  "     FROM " +  tblDwTemp + " uom \n" +
                  "     WHERE uom.ITEM_ID = tmp.ITEM_ID AND \n" +
                  "           uom.NAME_VALUE = 'UOM' \n" +
                  "       AND uom.CLW_VALUE is not NULL \n" +
                  " )  ";
              stmt = connection.prepareStatement(sql);
              stmt.executeUpdate();
              stmt.close();

              sql =
                  "UPDATE " + tblDwItemTemp + " tmp SET \n" +
                  "  tmp.ITEM_SIZE = \n" +
                  "  (SELECT  isize.CLW_VALUE \n" +
                  "     FROM " +  tblDwTemp + " isize \n" +
                  "     WHERE isize.ITEM_ID = tmp.ITEM_ID AND \n" +
                  "           isize.NAME_VALUE = 'SIZE' \n" +
                  "       AND isize.CLW_VALUE is not NULL \n" +
                  " )  ";
              stmt = connection.prepareStatement(sql);
              stmt.executeUpdate();
              stmt.close();


         }
         catch (SQLException ex) {
           String msg ="An error " + ex.getMessage() + " found at " +
               " updating  fields 'ITEM_PACK, ITEM_UOM, ITEM_SIZE' " +
               " in the table " + tblDwItemTemp + ". "+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");

         }

 /*        /// Fill the field 'ITEM_PACK' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp SET tmp.ITEM_PACK = " +
                 "( " +
                     "SELECT " +
                         "meta.CLW_VALUE " +
                     "FROM " +
                         tblClwItemMeta + " meta " +
                     "WHERE " +
                         "tmp.ITEM_ID = meta.ITEM_ID AND meta.NAME_VALUE = 'PACK' " +
                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'ITEM_PACK' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the field 'ITEM_UOM' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp SET tmp.ITEM_UOM = " +
                 "( " +
                     "SELECT " +
                         "meta.CLW_VALUE " +
                     "FROM " +
                         tblClwItemMeta + " meta " +
                     "WHERE " +
                         "tmp.ITEM_ID = meta.ITEM_ID AND meta.NAME_VALUE = 'UOM' " +
                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'ITEM_UOM' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }
*/
         /// Fill the fields 'MANUF_SKU' and 'MANUF_ID' in the table 'TBL_DW_ITEM_DIM_TEMP'.

         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp " +
                 "SET " +
                 "( " +
                     "tmp.MANUF_SKU, " +
                     "tmp.MANUF_ID " +
                 ") " +
                 "= " +
                 "( " +
                     "SELECT " +
                         "mapping.ITEM_NUM, " +
                         "mapping.BUS_ENTITY_ID " +
                     "FROM " +
                         tblClwItemMapping + " mapping " +
                     "WHERE " +
                         "tmp.ITEM_ID = mapping.ITEM_ID AND mapping.ITEM_MAPPING_CD = 'ITEM_MANUFACTURER' " +
                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of fields 'MANUF_SKU' and 'MANUF_ID' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage() +
                 "*** Execute the following request to get more information : " +  sql;
//             exceptions.add(msg);
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the field 'JD_ITEM_ID' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp SET tmp.JD_ITEM_ID = " +
                 "( " +
                     "SELECT " +
                         "MIN(itemAssoc.ITEM2_ID) " +
                     "FROM " +
                          tblClwItemAssoc + " itemAssoc " +
                     "WHERE " +
                         "itemAssoc.ITEM_ASSOC_CD = 'CROSS_STORE_ITEM_LINK' AND " +
                         "itemAssoc.ITEM1_ID = tmp.ITEM_ID AND " +
                         "itemAssoc.CATALOG_ID = ? " +
                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, jdCatalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'JD_ITEM_ID' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage() +
                 "*** Execute the following request to get more information : " +  sql;
//             exceptions.add(msg);
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the field 'JD_CATEGORY_ID' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp SET tmp.JD_CATEGORY_ID = " +
                 "( " +
                     "SELECT " +
                         "MIN(categoryAssoc.ITEM2_ID) " +
                     "FROM " +
                         tblClwItemAssoc + " categoryAssoc " +
                     "WHERE " +
                         "categoryAssoc.ITEM_ASSOC_CD = 'PRODUCT_PARENT_CATEGORY' AND " +
                         "categoryAssoc.ITEM1_ID =  tmp.JD_ITEM_ID AND " +
                         "categoryAssoc.CATALOG_ID = ? " +
                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, jdCatalogId);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'JD_CATEGORY_ID' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
//             exceptions.add(msg);
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the field 'JD_ITEM_DESC' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp " +
                 "SET " +
                 "( " +
                     "tmp.JD_ITEM_DESC, " +
                     "tmp.JD_CLW_SKU " +
                 ") " +
                 "= " +
                 "( " +
                     "SELECT " +
                         "item.SHORT_DESC, " +
                         "item.SKU_NUM " +
                     "FROM " +
                         tblClwItem + " item " +
                     "WHERE " +
                         "tmp.JD_ITEM_ID = item.ITEM_ID" +
                 ") " +
                 "WHERE tmp.JD_ITEM_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'JD_ITEM_DESC' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
//             exceptions.add(msg);
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the field 'JD_ITEM_PACK' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp SET tmp.JD_ITEM_PACK = " +
                 "( " +
                     "SELECT " +
                         "meta.CLW_VALUE " +
                     "FROM " +
                         tblClwItemMeta + " meta " +
                     "WHERE " +
                         "tmp.JD_ITEM_ID = meta.ITEM_ID AND meta.NAME_VALUE = 'PACK' " +
                         "AND meta.CLW_VALUE is not NULL " +
                 ") " +
                 "WHERE tmp.JD_ITEM_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'JD_ITEM_PACK' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
//             exceptions.add(msg);
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the field 'JD_ITEM_UOM' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp SET tmp.JD_ITEM_UOM = " +
                 "( " +
                     "SELECT " +
                         "meta.CLW_VALUE " +
                     "FROM " +
                         tblClwItemMeta + " meta " +
                     "WHERE " +
                         "tmp.JD_ITEM_ID = meta.ITEM_ID AND meta.NAME_VALUE = 'UOM' " +
                 ") " +
                 "WHERE tmp.JD_ITEM_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'JD_ITEM_UOM' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
//             exceptions.add(msg);
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the fields 'JD_MANUF_SKU' and 'JD_MANUF_ID' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp " +
                 "SET " +
                 "( " +
                     "tmp.JD_MANUF_SKU, " +
                     "tmp.JD_MANUF_ID " +
                 ") " +
                 "= " +
                 "( " +
                     "SELECT " +
                         "mapping.ITEM_NUM, " +
                         "mapping.BUS_ENTITY_ID " +
                     "FROM " +
                         tblClwItemMapping + " mapping " +
                     "WHERE " +
                         "tmp.JD_ITEM_ID = mapping.ITEM_ID AND mapping.ITEM_MAPPING_CD = 'ITEM_MANUFACTURER' " +
                 ") " +
                 "WHERE tmp.JD_ITEM_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of fields 'JD_MANUF_SKU' and 'JD_MANUF_ID' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
//             exceptions.add(msg);
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Update the not defined 'JD_...'-fields by default value in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp +
                 " SET " +
                 "    JD_ITEM_DESC = ITEM_DESC, " +
                 "    JD_ITEM_PACK = ITEM_PACK, " +
                 "    JD_ITEM_UOM  = ITEM_UOM, " +
                 "    JD_ITEM_SIZE  = ITEM_SIZE, " +
                 "    JD_MANUF_SKU = MANUF_SKU" +
                 " WHERE JD_ITEM_ID IS NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating not defined 'JD_ITEM_DESC'-field by default value in the table " +
                 tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
//             exceptions.add(msg);
             throw new SQLException("^clw^"+msg+ "^clw^");
         }
         /// Filling the fields 'MANUFACTURER_DIM_ID' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         createIndex(connection, tblDwItemTemp, "I1" , "MANUF_ID" ) ;

          try {
              sql =
                  "UPDATE " + tblDwItemTemp + " tmp \n" +
                  "SET \n" +
                 "(  \n" +
                 "    tmp.MANUFACTURER_DIM_ID  \n" +
                 ")  \n" +
                 "=  \n" +
                 "(  \n" +
                 "    SELECT  \n" +
                 "        manuf.MANUFACTURER_DIM_ID \n" +
                 "    FROM  \n" +
                 tblDwManufDim +" manuf  \n" +
                 "    WHERE  \n" +
                 "         tmp.MANUF_ID = manuf.MANUF_ID  \n" +
                 "     and tmp.STORE_DIM_ID =" + dwStoreDimId +
                 ")";

              stmt = connection.prepareStatement(sql);
              stmt.executeUpdate();
              stmt.close();
          }
          catch (SQLException ex) {
              String msg = "An error occurred at updating the columns for Manufactory - dim ids in table " +
                  tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
              throw new SQLException("^clw^"+msg+ "^clw^");
         }
/*
         /// Update the not defined 'JD_ITEM_DESC'-field by default value in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " SET JD_ITEM_DESC = ITEM_DESC WHERE JD_ITEM_DESC IS NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating not defined 'JD_ITEM_DESC'-field by default value in the table " +
                 tblDwItemTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Update the not defined 'JD_ITEM_PACK'-field by default value in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " SET JD_ITEM_PACK = ITEM_PACK WHERE JD_ITEM_PACK IS NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating not defined 'JD_ITEM_PACK'-field by default value in the table " +
                 tblDwItemTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Update the not defined 'JD_ITEM_UOM'-field by default value in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " SET JD_ITEM_UOM = ITEM_UOM WHERE JD_ITEM_UOM IS NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating not defined 'JD_ITEM_UOM'-field by default value in the table " +
                 tblDwItemTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Update the not defined 'JD_MANUF_SKU'-field by default value in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " SET JD_MANUF_SKU = MANUF_SKU WHERE JD_MANUF_SKU IS NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating not defined 'JD_MANUF_SKU'-field by default value in the table " +
                 tblDwItemTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the field 'STORE_DIM_ID' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp SET tmp.STORE_DIM_ID = " +
                 "( " +
                     "SELECT " +
                         "dw.STORE_DIM_ID " +
                     "FROM " +
                         tblDwStore + " dw " +
                     "WHERE " +
                         "tmp.STORE_ID = dw.STORE_ID " +
                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'STORE_DIM_ID' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }
*/
         /// Fill the field 'CATEGORY_DIM_ID' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         createIndex(connection, tblDwItemTemp, "I2" , "CATEGORY_ID" ) ;

         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp SET tmp.CATEGORY_DIM_ID = \n" +
                 "( \n" +
                     "SELECT \n" +
                         "dwCategory.CATEGORY_DIM_ID \n" +
                     "FROM \n" +
                         tblDwCategory + " dwCategory \n" +
                     "WHERE \n" +
                         "tmp.CATEGORY_ID = dwCategory.CATEG1_ID AND \n" +
                         "NVL(tmp.JD_CATEGORY_ID,0) = NVL(dwCategory.JD_CATEG1_ID,0) AND \n" +
                         "dwCategory.STORE_DIM_ID =" + dwStoreDimId +

                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'CATEGORY_DIM_ID' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Fill the field 'ITEM_DIM_ID' in the table 'TBL_DW_ITEM_DIM_TEMP'.

  //       createIndex(connection, tblDwItemTemp, "I1" , "ITEM_ID" ) ;
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp SET tmp.ITEM_DIM_ID = \n" +
                 "( " +
                     "SELECT \n" +
                         "dw.ITEM_DIM_ID \n" +
                     "FROM \n" +
                         tblDwItem + " dw \n" +
                     "WHERE \n" +
                         "tmp.ITEM_ID = dw.ITEM_ID AND \n" +
//                         "tmp.CATEGORY_DIM_ID  = dw.CATEGORY_DIM_ID AND \n" +
                         "dw.STORE_DIM_ID =" + dwStoreDimId +

                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'ITEM_DIM_ID' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }
         /// Fill the field 'JD_ITEM_FL' in the table 'TBL_DW_ITEM_DIM_TEMP'.
         try {
             sql =
                 "UPDATE  " + tblDwItemTemp + " tmp set JD_ITEM_FL = 'TRUE' \n"+
                 "WHERE  \n"+
                 "  tmp.ITEM_ID in  \n"+
                 "  ( \n"+
                 "     SELECT item_id FROM " + tblClwItemMapping +"\n"+
                 "     WHERE bus_entity_id IN ( \n"+
                 "         SELECT distinct BUS_ENTITY_ID  \n"+
                 "         FROM " + tblClwGroupAssoc + " ga, " + tblClwGroup + " g  \n"+
                 "         WHERE ga.GROUP_ID =g.GROUP_ID  \n"+
                 "           and g.SHORT_DESC = 'JD Brands' \n"+
                 "           and g.GROUP_TYPE_CD = 'MANUFACTURER' \n"+
                 "           and g.GROUP_STATUS_CD= 'ACTIVE' \n"+
                 "     )  \n"+
                 "  ) ";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of field 'JD_ITEM_FL' " +
                 "in the table " + tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Updating of the field UPDATE_TYPE in table 'TBL_DW_ITEM_DIM_TEMP'.
         /// Set the value of field 'UPDATE_TYPE' in table 'TBL_DW_ITEM_DIM_TEMP' into 'TO_UPDATE' for
         /// records which have the similar changed records in table 'TBL_DW_ITEM_DIM_DIM'.
         try {
             sql =
                 "UPDATE " + tblDwItemTemp + " tmp " +
                 "SET tmp.UPDATE_TYPE = " +
                 "( " +
                     "SELECT " +
                         "'TO_UPDATE' " +
                     "FROM " +
                         tblDwItem + " dw " +
                     "WHERE " +
                         "dw.ITEM_DIM_ID = tmp.ITEM_DIM_ID AND" +

                         "( " +
                             "dw.CATEGORY_DIM_ID <> tmp.CATEGORY_DIM_ID OR " +
                             "dw.ITEM_DESC <> tmp.ITEM_DESC OR " +
                             "(dw.ITEM_DESC IS NULL AND tmp.ITEM_DESC IS NOT NULL) OR " +
                             "(dw.ITEM_DESC IS NOT NULL AND tmp.ITEM_DESC IS NULL) OR " +
                             "dw.ITEM_PACK <> tmp.ITEM_PACK OR " +
                             "(dw.ITEM_PACK IS NULL AND tmp.ITEM_PACK IS NOT NULL) OR " +
                             "(dw.ITEM_PACK IS NOT NULL AND tmp.ITEM_PACK IS NULL) OR " +
                             "dw.ITEM_UOM <> tmp.ITEM_UOM OR " +
                             "(dw.ITEM_UOM IS NULL AND tmp.ITEM_UOM IS NOT NULL) OR " +
                             "(dw.ITEM_UOM IS NOT NULL AND tmp.ITEM_UOM IS NULL) OR " +
                             "dw.ITEM_SIZE <> tmp.ITEM_SIZE OR " +
                             "(dw.ITEM_SIZE IS NULL AND tmp.ITEM_SIZE IS NOT NULL) OR " +
                             "(dw.ITEM_SIZE IS NOT NULL AND tmp.ITEM_SIZE IS NULL) OR " +
                             "dw.CLW_SKU <> tmp.CLW_SKU OR " +
                             "(dw.CLW_SKU IS NULL AND tmp.CLW_SKU IS NOT NULL) OR " +
                             "(dw.CLW_SKU IS NOT NULL AND tmp.CLW_SKU IS NULL) OR " +
                             "dw.MANUF_SKU <> tmp.MANUF_SKU OR " +
                             "(dw.MANUF_SKU IS NULL AND tmp.MANUF_SKU IS NOT NULL) OR " +
                             "(dw.MANUF_SKU IS NOT NULL AND tmp.MANUF_SKU IS NULL) OR " +
                             "dw.MANUF_ID <> tmp.MANUF_ID OR " +
                             "(dw.MANUF_ID IS NULL AND tmp.MANUF_ID IS NOT NULL) OR " +
                             "(dw.MANUF_ID IS NOT NULL AND tmp.MANUF_ID IS NULL) OR " +
                             "dw.JD_ITEM_ID <> tmp.JD_ITEM_ID OR " +
                             "(dw.JD_ITEM_ID IS NULL AND tmp.JD_ITEM_ID IS NOT NULL) OR " +
                             "(dw.JD_ITEM_ID IS NOT NULL AND tmp.JD_ITEM_ID IS NULL) OR " +
                             "dw.JD_ITEM_DESC <> tmp.JD_ITEM_DESC OR " +
                             "(dw.JD_ITEM_DESC IS NULL AND tmp.JD_ITEM_DESC IS NOT NULL) OR " +
                             "(dw.JD_ITEM_DESC IS NOT NULL AND tmp.JD_ITEM_DESC IS NULL) OR " +
                             "dw.JD_ITEM_PACK <> tmp.JD_ITEM_PACK OR " +
                             "(dw.JD_ITEM_PACK IS NULL AND tmp.JD_ITEM_PACK IS NOT NULL) OR " +
                             "(dw.JD_ITEM_PACK IS NOT NULL AND tmp.JD_ITEM_PACK IS NULL) OR " +
                             "dw.JD_ITEM_UOM <> tmp.JD_ITEM_UOM OR " +
                             "(dw.JD_ITEM_UOM IS NULL AND tmp.JD_ITEM_UOM IS NOT NULL) OR " +
                             "(dw.JD_ITEM_UOM IS NOT NULL AND tmp.JD_ITEM_UOM IS NULL) OR " +
                             "dw.JD_CLW_SKU <> tmp.JD_CLW_SKU OR " +
                             "(dw.JD_CLW_SKU IS NULL AND tmp.JD_CLW_SKU IS NOT NULL) OR " +
                             "(dw.JD_CLW_SKU IS NOT NULL AND tmp.JD_CLW_SKU IS NULL) OR " +
                             "dw.JD_MANUF_SKU <> tmp.JD_MANUF_SKU OR " +
                             "(dw.JD_MANUF_SKU IS NULL AND tmp.JD_MANUF_SKU IS NOT NULL) OR " +
                             "(dw.JD_MANUF_SKU IS NOT NULL AND tmp.JD_MANUF_SKU IS NULL) OR " +
                             "dw.JD_MANUF_ID <> tmp.JD_MANUF_ID OR " +
                             "(dw.JD_MANUF_ID IS NULL AND tmp.JD_MANUF_ID IS NOT NULL) OR " +
                             "(dw.JD_MANUF_ID IS NOT NULL AND tmp.JD_MANUF_ID IS NULL) OR " +
                             "dw.STORE_CATALOG_ID <> tmp.STORE_CATALOG_ID OR " +
                             "(dw.STORE_CATALOG_ID IS NULL AND tmp.STORE_CATALOG_ID IS NOT NULL) OR " +
                             "(dw.STORE_CATALOG_ID IS NOT NULL AND tmp.STORE_CATALOG_ID IS NULL) OR " +
                             "dw.JD_ITEM_FL <> tmp.JD_ITEM_FL OR " +
                             "(dw.JD_ITEM_FL IS NULL AND tmp.JD_ITEM_FL IS NOT NULL) OR " +
                             "(dw.JD_ITEM_FL IS NOT NULL AND tmp.JD_ITEM_FL IS NULL) " +
                         ") " +
                 ") " +
                "WHERE tmp.ITEM_DIM_ID IS NOT NULL";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of 'UPDATE_TYPE' field in the table " +
                 tblDwItemTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         ////////////////////////////////////////////////////////////////////////
         /// Updating of data in table 'TBL_DW_ITEM_DIM'.
         /// The data from table 'TBL_DW_ITEM_DIM_TEMP' will be used
         /// to update data in the table 'TBL_DW_ITEM_DIM_DIM'.
         ////////////////////////////////////////////////////////////////////////
         /// log Errors :
        String[] sourceFields = new String[]{"ITEM_ID"};
        String[] foreignKeys  = new String[]{"STORE_DIM_ID", "CATEGORY_DIM_ID", "MANUFACTURER_DIM_ID"};

        List<StringBuffer> errors = verifyConstraints(connection, sourceFields,  foreignKeys,  tblDwItemTemp);
        int insertedRowCount = 0;
        int updatedRowCount = 0;
        int errorRowCount = errors.size();

        createIndex(connection, tblDwItemTemp, "I3" , "ITEM_DIM_ID" ) ;

        if (errorRowCount > 0){
          throw new DataNotFoundException(action + " :: <ERROR> It is impossible to update "+tblDwItem+". NULL foreign key occured in "+tblDwItemTemp+". "  + errors.toString());
       } else {
        /// Updating of data in table 'TBL_DW_ITEM_DIM'

         try {
             sql =
                 "UPDATE " + tblDwItem + " dw " +
                 "SET " +
                 "( " +
                     "STORE_DIM_ID, " +
                     "CATEGORY_DIM_ID, " +
                     "MANUFACTURER_DIM_ID, " +
                     "ITEM_ID, " +
                     "ITEM_DESC, " +
                     "ITEM_PACK, " +
                     "ITEM_UOM, " +
                     "ITEM_SIZE, " +
                     "CLW_SKU, " +
                     "MANUF_SKU, " +
                     "MANUF_ID, " +
                     "JD_ITEM_ID, " +
                     "JD_ITEM_DESC, " +
                     "JD_ITEM_PACK, " +
                     "JD_ITEM_UOM, " +
                     "JD_ITEM_SIZE, " +
                     "JD_CLW_SKU, " +
                     "JD_MANUF_SKU, " +
                     "JD_MANUF_ID, " +
                     "JD_ITEM_FL, " +
                     "STORE_CATALOG_ID, " +
                     "ACCOUNT_CATALOG_ID, " +
                     "MOD_BY, " +
                     "MOD_DATE " +
                 ") " +
                 "= " +
                 "( " +
                     "SELECT " +
                         "tmp.STORE_DIM_ID, " +
                         "tmp.CATEGORY_DIM_ID, " +
                         "tmp.MANUFACTURER_DIM_ID, " +
                         "tmp.ITEM_ID, " +
                         "tmp.ITEM_DESC, " +
                         "tmp.ITEM_PACK, " +
                         "tmp.ITEM_UOM, " +
                         "tmp.ITEM_SIZE, " +
                         "tmp.CLW_SKU, " +
                         "tmp.MANUF_SKU, " +
                         "tmp.MANUF_ID, " +
                         "tmp.JD_ITEM_ID, " +
                         "tmp.JD_ITEM_DESC, " +
                         "tmp.JD_ITEM_PACK, " +
                         "tmp.JD_ITEM_UOM, " +
                         "tmp.JD_ITEM_SIZE, " +
                         "tmp.JD_CLW_SKU, " +
                         "tmp.JD_MANUF_SKU, " +
                         "tmp.JD_MANUF_ID, " +
                         "tmp.JD_ITEM_FL, " +
                         "tmp.STORE_CATALOG_ID, " +
                         "tmp.ACCOUNT_CATALOG_ID, " +
                         "?, " +
                         "SYSDATE " +
                     "FROM " +
                         tblDwItemTemp + " tmp " +
                     "WHERE " +
                         "dw.ITEM_DIM_ID = tmp.ITEM_DIM_ID " +

                 ") " +
                 "WHERE dw.ITEM_DIM_ID IN " +
                     "( " +
                         "SELECT  " +
                         "    ITEM_DIM_ID " +
                         "FROM " +
                             tblDwItemTemp + " tmp " +
                         "WHERE " +
                             "UPDATE_TYPE = 'TO_UPDATE' " +
                     ")";
             stmt = connection.prepareStatement(sql);
             stmt.setString(1, userName);
             updatedRowCount  = stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating of data in the table " +
                 tblDwItem + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Insert of new records into table 'TBL_DW_ITEM_DIM'.
         /// New records (in the table 'TBL_DW_ITEM_DIM_TEMP') have
         /// the NULL value in the field 'ITEM_DIM_ID'.
         try {
             sql =
                 "INSERT INTO " + tblDwItem + " dw " +
                 "( " +
                     "STORE_DIM_ID, " +
                     "ITEM_DIM_ID, " +
                     "CATEGORY_DIM_ID, " +
                     "MANUFACTURER_DIM_ID, "+
                     "ITEM_ID, " +
                     "ITEM_DESC, " +
                     "ITEM_PACK, " +
                     "ITEM_UOM, " +
                     "ITEM_SIZE, " +
                     "CLW_SKU, " +
                     "MANUF_SKU, " +
                     "MANUF_ID, " +
                     "JD_ITEM_ID, " +
                     "JD_ITEM_DESC, " +
                     "JD_ITEM_PACK, " +
                     "JD_ITEM_UOM, " +
                     "JD_ITEM_SIZE, " +
                     "JD_CLW_SKU, " +
                     "JD_MANUF_SKU, " +
                     "JD_MANUF_ID, " +
                     "JD_ITEM_FL, " +
                     "STORE_CATALOG_ID, " +
                     "ACCOUNT_CATALOG_ID, " +
                     "ADD_BY, " +
                     "ADD_DATE, " +
                     "MOD_BY, " +
                     "MOD_DATE " +
                 ") " +
                 "SELECT " +
                     "tmp.STORE_DIM_ID, " +
                     tblDwItemSeq + ".NEXTVAL, " +
                     "tmp.CATEGORY_DIM_ID, " +
                     "tmp.MANUFACTURER_DIM_ID, " +
                     "tmp.ITEM_ID, " +
                     "tmp.ITEM_DESC, " +
                     "tmp.ITEM_PACK, " +
                     "tmp.ITEM_UOM, " +
                     "tmp.ITEM_SIZE, " +
                     "tmp.CLW_SKU, " +
                     "tmp.MANUF_SKU, " +
                     "tmp.MANUF_ID, " +
                     "tmp.JD_ITEM_ID, " +
                     "tmp.JD_ITEM_DESC, " +
                     "tmp.JD_ITEM_PACK, " +
                     "tmp.JD_ITEM_UOM, " +
                     "tmp.JD_ITEM_SIZE, " +
                     "tmp.JD_CLW_SKU, " +
                     "tmp.JD_MANUF_SKU, " +
                     "tmp.JD_MANUF_ID, " +
                     "tmp.JD_ITEM_FL, " +
                     "tmp.STORE_CATALOG_ID, " +
                     "tmp.ACCOUNT_CATALOG_ID, " +
                     "?, " +
                     "SYSDATE, " +
                     "?, " +
                     "SYSDATE " +
                 "FROM " +
                     tblDwItemTemp + " tmp " +
                 "WHERE " +
                     "tmp.ITEM_DIM_ID IS NULL";
             stmt = connection.prepareStatement(sql);
             stmt.setString(1, userName);
             stmt.setString(2, userName);
             insertedRowCount = stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at inserting of records into the table " +
                 tblDwItem + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }
        } // end if by errors
        ///
        /// REPORT
        StringBuffer report = logReport(tblDwItem, insertedRowCount, updatedRowCount, errors  );
        return report;
    }


    /**
     * Fills the DW_ITEM_DISTRIBUTOR table in the "Data Warehouse".
     * Use the parameter 'storeSchemaName' to define the schema for 'CLW'-tables with data.
     * Use the parameter 'dbSchemaName' to define the schema for the tables from 'Data Warehouse'.
     */
    public void fillDwItemDistributorTable(String userName, String dbClwNamespace,
        String dbDwNamespace) throws RemoteException {

        Connection connection = null;
        try {
            connection = getConnection();
            fillDwItemDistributorTable(connection, userName, dbClwNamespace, dbDwNamespace);
        }
        catch (SQLException ex) {
            throw new RemoteException(ex.getMessage());
        }
        catch (NamingException ex) {
            throw new RemoteException(ex.getMessage());
        }
        catch (Throwable ex) {
                 ex.printStackTrace();
                 throw new RemoteException(ex.getMessage());
        }
        finally {
            closeConnection(connection);
        }
    }
    private  void fillDwItemDistributorTable(Connection connection, String userName,
        String dbClwNamespace, String dbDwNamespace) throws SQLException, RemoteException {
       fillDwItemDistributorTable (connection,userName,dbClwNamespace,dbDwNamespace,-1);
    }

    private  void fillDwItemDistributorTable(Connection connection, String userName,
        String dbClwNamespace, String dbDwNamespace, int dwStoreDimId) throws SQLException, RemoteException {

        if (connection == null) {
            throw new SQLException("Connection is null!");
        }

        final String tblClwItemMapping = getFullTableName(dbClwNamespace, TBL_CLW_ITEM_MAPPING);

        final String tblDwItemDistr = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_ITEM_DISTRIBUTOR);
        final String tblDwItem = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_ITEM_DIM);
        final String tblDwDistr = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_DISTRIBUTOR_DIM);

        final String tblDwItemDistrSeq = getFullTableName(dbDwNamespace, TBL_DW_ITEM_DISTRIBUTOR_SEQ);
        final String tblDwItemDistrTemp = getFullTableName(dwTempUser, TBL_DW_ITEM_DISTRIBUTOR_TEMP, tempSuffix);
        final String action = "< DataWarehouseBean >[" + tblDwItemDistr + " Creation] ";

        String sql = null;
        PreparedStatement stmt = null;
        boolean isExistTempTable = false;
        boolean forStore = (dwStoreDimId > 0);
        ////////////////////////////////////////////////////////////////////////
        /// Creation of temporal table 'TBL_DW_ITEM_DISTRIBUTOR_TEMP'.
        /// The table 'TBL_DW_ITEM_DISTRIBUTOR_TEMP' contains data
        /// to update table 'TBL_DW_ITEM_DISTRIBUTOR' from Data Warehouse.
        ////////////////////////////////////////////////////////////////////////

/*        /// Checking of existence of table 'TBL_DW_ITEM_DISTRIBUTOR_TEMP'
        try {
            isExistTempTable = checkDbTableOnExistence(connection,
                dbDwNamespace, TBL_DW_ITEM_DISTRIBUTOR_TEMP);
        }
        catch (SQLException ex) {
            String msg = "An error occurred at checking existence of table " +
                tblDwItemDistrTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
*/
        /// Deleting of already existing table 'TBL_DW_ITEM_DISTRIBUTOR_TEMP'
        if (isExistTempTable) {
            try {
                sql = "DROP TABLE " + tblDwItemDistrTemp + " PURGE";
                stmt = connection.prepareStatement(sql);
                stmt.executeUpdate();
                stmt.close();
            }
            catch (SQLException ex) {
                String msg = "An error occurred at deleting of table " +
                    tblDwItemDistrTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
                throw new SQLException("^clw^"+msg+ "^clw^");
            }
        }

        /// Creation of the table 'TBL_DW_ITEM_DISTRIBUTOR_TEMP'
        try {
            sql =
                "CREATE TABLE " + tblDwItemDistrTemp + " " +
                "( " +
                    "ITEM_DISTRIBUTOR_ID  NUMBER NULL, " +
                    "ITEM_DIM_ID          NUMBER NULL, " +
                    "DISTRIBUTOR_DIM_ID   NUMBER NULL, " +
                    "DIST_SKU             VARCHAR2(60) NULL, " +
                    "DIST_PACK            VARCHAR2(30) NULL, " +
                    "DIST_UOM             VARCHAR2(30) NULL, " +
                    "DIST_ID              NUMBER NULL, " +
                    "JD_DIST_SKU          VARCHAR2(60) NULL, " +
                    "JD_DIST_PACK         VARCHAR2(30) NULL, " +
                    "JD_DIST_UOM          VARCHAR2(30) NULL, " +
                    "JD_DIST_ID           NUMBER NULL, " +
                    "UPDATE_TYPE          VARCHAR2(30) NULL " +
                 ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at creation of table " +
                tblDwItemDistrTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        ////////////////////////////////////////////////////////////////////////
        /// Filling of temporal 'TBL_DW_ITEM_DISTRIBUTOR_TEMP'-table by data.
        ////////////////////////////////////////////////////////////////////////

        /// Insert of data from store 'JanPak' into temporal table.
        try {
            sql =
                "INSERT INTO " + tblDwItemDistrTemp + " \n" +
                "SELECT  \n" +
                    "NULL,  \n" +                        // ITEM_DISTRIBUTOR_ID  NUMBER NULL
                    "itemDim.ITEM_DIM_ID,  \n" +         // ITEM_DIM_ID          NUMBER NULL
                    "distDim.DISTRIBUTOR_DIM_ID,  \n" +  // DISTRIBUTOR_DIM_ID   NUMBER NULL
                    "itemMapping.ITEM_NUM,  \n" +        // DIST_SKU             VARCHAR2(50) NULL
                    "itemMapping.ITEM_PACK,  \n" +       // DIST_PACK            VARCHAR2(30) NULL
                    "itemMapping.ITEM_UOM,  \n" +        // DIST_UOM             VARCHAR2(30) NULL
                    "distDim.DIST_ID,  \n" +             // DIST_ID              NUMBER NULL
                    "NULL,  \n" +                        // JD_DIST_SKU          VARCHAR2(50) NULL
                    "NULL,  \n" +                        // JD_DIST_PACK         VARCHAR2(30) NULL
                    "NULL,  \n" +                        // JD_DIST_UOM          VARCHAR2(30) NULL
                    "distDim.JD_DIST_ID,  \n" +          // JD_DIST_ID           NUMBER NULL
                    "NULL  \n" +                         // UPDATE_TYPE          VARCHAR2(30) NULL
                "FROM   \n" +
                    tblClwItemMapping + " itemMapping,  \n" +
                    tblDwItem + " itemDim,  \n" +
                    tblDwDistr + " distDim  \n" +
                "WHERE  \n" +
                    "itemMapping.ITEM_ID = itemDim.ITEM_ID AND  \n" +
                    "itemMapping.BUS_ENTITY_ID = distDim.DIST_ID AND  \n" +
                    ((forStore) ? ("itemDim.STORE_DIM_ID = " + dwStoreDimId + " AND  \n") : "" )+
                    ((forStore) ? ("distDim.STORE_DIM_ID = " + dwStoreDimId + " AND  \n") : "" )+
                    "itemMapping.ITEM_MAPPING_CD = 'ITEM_DISTRIBUTOR'";

            stmt = connection.prepareStatement(sql);
            int inserted = stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at insertion of records into table " +
                tblDwItemDistrTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Update the columns for the distributor from "JD" -store.
        try {
            sql =
                "UPDATE " + tblDwItemDistrTemp + " tmp  \n" +
                "SET  \n" +
                "(  \n" +
                    "tmp.JD_DIST_SKU,  \n" +
                    "tmp.JD_DIST_PACK,  \n" +
                    "tmp.JD_DIST_UOM  \n" +
                ")  \n" +
                "=  \n" +
                "(  \n" +
                    "SELECT  \n" +
                        "itemMapping.ITEM_NUM,  \n" +
                        "itemMapping.ITEM_PACK,  \n" +
                        "itemMapping.ITEM_UOM  \n" +
                    "FROM   \n" +
                        tblClwItemMapping + " itemMapping,  \n" +
                        tblDwItem + " itemDim  \n" +
                    "WHERE  \n" +
                        "tmp.ITEM_DIM_ID = itemDim.ITEM_DIM_ID AND  \n" +
                        "tmp.JD_DIST_ID = itemMapping.BUS_ENTITY_ID AND  \n" +
                        "itemMapping.ITEM_ID = itemDim.JD_ITEM_ID AND  \n" +
                        "itemMapping.ITEM_MAPPING_CD = 'ITEM_DISTRIBUTOR'  \n" +
                ")  \n" +
                "WHERE tmp.JD_DIST_ID IS NOT NULL ";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating the columns for the distributor from 'JD'-store in table " +
                tblDwItemDistrTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Update the not defined 'JD_DIST_SKU', 'JD_DIST_PACK', 'JD_DIST_UOM'-field by default values.
        try {
            sql =
                "UPDATE " + tblDwItemDistrTemp + " SET \n" +
                " JD_DIST_SKU = DIST_SKU, \n" +
                " JD_DIST_PACK = DIST_PACK, \n" +
                " JD_DIST_UOM = DIST_UOM  \n" +
                "WHERE JD_DIST_ID IS NULL";
           stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating the not defined 'JD_DIST_SKU'-field by default values in table " +
                tblDwItemDistrTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }


/*
        /// Update the not defined 'JD_DIST_SKU'-field by default values.
        try {
            sql =
                "UPDATE " + tblDwItemDistrTemp + " SET JD_DIST_SKU = DIST_SKU " +
                "WHERE JD_DIST_SKU IS NULL";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating the not defined 'JD_DIST_SKU'-field by default values in table " +
                tblDwItemDistrTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Update the not defined 'JD_DIST_PACK'-field by default values.
        try {
            sql =
                "UPDATE " + tblDwItemDistrTemp + " SET JD_DIST_PACK = DIST_PACK " +
                "WHERE JD_DIST_PACK IS NULL";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating the not defined 'JD_DIST_PACK'-field by default values in table " +
                tblDwItemDistrTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Update the not defined 'JD_DIST_UOM'-field by default values.
        try {
            sql =
                "UPDATE " + tblDwItemDistrTemp + " SET JD_DIST_UOM = DIST_UOM " +
                "WHERE JD_DIST_UOM IS NULL";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating the not defined 'JD_DIST_UOM'-field by default values in table " +
                tblDwItemDistrTemp + ". " + ex.getMessage();
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
*/
        /// Fill the field 'ITEM_DISTRIBUTOR_ID' in the table 'TBL_DW_ITEM_DISTRIBUTOR_TEMP'
        createIndex(connection, tblDwItemDistrTemp, "I1" , "ITEM_DIM_ID" ) ;
        createIndex(connection, tblDwItemDistrTemp, "I2" , "DISTRIBUTOR_DIM_ID" ) ;
        try {
            sql =
                "UPDATE " + tblDwItemDistrTemp + " tmp SET tmp.ITEM_DISTRIBUTOR_ID =  \n" +
                "(  \n" +
                    "SELECT  \n" +
                        "dw.ITEM_DISTRIBUTOR_ID  \n" +
                    "FROM   \n" +
                        tblDwItemDistr + " dw  \n" +
                    "WHERE  \n" +
                        "tmp.ITEM_DIM_ID = dw.ITEM_DIM_ID AND  \n" +
                        "tmp.DISTRIBUTOR_DIM_ID = dw.DISTRIBUTOR_DIM_ID  \n" +
                ") ";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating the field 'ITEM_DISTRIBUTOR_ID' in the table " +
                tblDwItemDistrTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Updating the field 'UPDATE_TYPE' in table 'TBL_DW_ITEM_DISTRIBUTOR_TEMP'
        try {
            sql =
                "UPDATE " + tblDwItemDistrTemp + " tmp SET tmp.UPDATE_TYPE = " +
                "( " +
                    "SELECT " +
                        "'TO_UPDATE' " +
                    "FROM  " +
                        tblDwItemDistr + " dw " +
                    "WHERE " +
                        "dw.ITEM_DISTRIBUTOR_ID = tmp.ITEM_DISTRIBUTOR_ID AND " +
                        "( " +
                            "dw.DIST_SKU <> tmp.DIST_SKU OR " +
                            "(dw.DIST_SKU IS NULL AND tmp.DIST_SKU IS NOT NULL) OR " +
                            "(dw.DIST_SKU IS NOT NULL AND tmp.DIST_SKU IS NULL) OR " +
                            "dw.DIST_PACK <> tmp.DIST_PACK OR " +
                            "(dw.DIST_PACK IS NULL AND tmp.DIST_PACK IS NOT NULL) OR " +
                            "(dw.DIST_PACK IS NOT NULL AND tmp.DIST_PACK IS NULL) OR " +
                            "dw.DIST_UOM <> tmp.DIST_UOM OR " +
                            "(dw.DIST_UOM IS NULL AND tmp.DIST_UOM IS NOT NULL) OR " +
                            "(dw.DIST_UOM IS NOT NULL AND tmp.DIST_UOM IS NULL) OR " +
                            "dw.JD_DIST_SKU <> tmp.JD_DIST_SKU OR " +
                            "(dw.JD_DIST_SKU IS NULL AND tmp.JD_DIST_SKU IS NOT NULL) OR " +
                            "(dw.JD_DIST_SKU IS NOT NULL AND tmp.JD_DIST_SKU IS NULL) OR " +
                            "dw.JD_DIST_PACK <> tmp.JD_DIST_PACK OR " +
                            "(dw.JD_DIST_PACK IS NULL AND tmp.JD_DIST_PACK IS NOT NULL) OR " +
                            "(dw.JD_DIST_PACK IS NOT NULL AND tmp.JD_DIST_PACK IS NULL) OR " +
                            "dw.JD_DIST_UOM <> tmp.JD_DIST_UOM OR " +
                            "(dw.JD_DIST_UOM IS NULL AND tmp.JD_DIST_UOM IS NOT NULL) OR " +
                            "(dw.JD_DIST_UOM IS NOT NULL AND tmp.JD_DIST_UOM IS NULL) OR " +
                            "dw.JD_DIST_ID <> tmp.JD_DIST_ID OR " +
                            "(dw.JD_DIST_ID IS NULL AND tmp.JD_DIST_ID IS NOT NULL) OR " +
                            "(dw.JD_DIST_ID IS NOT NULL AND tmp.JD_DIST_ID IS NULL) " +
                        ") " +
                ") " +
                "WHERE " +
                    "tmp.ITEM_DISTRIBUTOR_ID IS NOT NULL";
            stmt = connection.prepareStatement(sql);
            int updated = stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating the field 'UPDATE_TYPE' in the table " +
                tblDwItemDistrTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        ////////////////////////////////////////////////////////////////////////
        /// Updating of data in table 'TBL_DW_ITEM_DISTRIBUTOR'.
        /// The data from table 'TBL_DW_ITEM_DISTRIBUTOR_TEMP' will be used
        /// to update data in the table 'TBL_DW_ITEM_DISTRIBUTOR'.
        ////////////////////////////////////////////////////////////////////////

        /// Updating of data in table 'TBL_DW_ITEM_DISTRIBUTOR'
        createIndex(connection, tblDwItemDistrTemp, "I3" , "ITEM_DISTRIBUTOR_ID" ) ;

        int updatedRowCount = 0;
        try {
            sql =
                "UPDATE " + tblDwItemDistr + " dw " +
                "SET " +
                "( " +
                    "DIST_SKU, " +
                    "DIST_PACK, " +
                    "DIST_UOM, " +
                    "JD_DIST_SKU, " +
                    "JD_DIST_PACK, " +
                    "JD_DIST_UOM," +
                    "JD_DIST_ID, " +
                    "MOD_BY, " +
                    "MOD_DATE " +
                ") " +
                "= " +
                "( " +
                    "SELECT " +
                        "tmp.DIST_SKU, " +
                        "tmp.DIST_PACK, " +
                        "tmp.DIST_UOM, " +
                        "tmp.JD_DIST_SKU, " +
                        "tmp.JD_DIST_PACK, " +
                        "tmp.JD_DIST_UOM, " +
                        "tmp.JD_DIST_ID, " +
                        "?, " +
                        "SYSDATE " +
                    "FROM  " +
                        tblDwItemDistrTemp + " tmp " +
                    "WHERE " +
                        "dw.ITEM_DISTRIBUTOR_ID = tmp.ITEM_DISTRIBUTOR_ID " +
                ") " +
                "WHERE " +
                    "dw.ITEM_DISTRIBUTOR_ID IN " +
                    "( " +
                        "SELECT " +
                            "ITEM_DISTRIBUTOR_ID " +
                        "FROM " +
                            tblDwItemDistrTemp + " " +
                        "WHERE " +
                            "UPDATE_TYPE = 'TO_UPDATE' " +
                    ")";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            updatedRowCount = stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of data in the table " +
                tblDwItemDistr + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        /// Insert of new records into table 'TBL_DW_ITEM_DISTRIBUTOR'
        int insertedRowCount = 0;
        try {
            sql =
                "INSERT INTO " + tblDwItemDistr + " dw " +
                "( " +
                    "ITEM_DISTRIBUTOR_ID, " +
                    "ITEM_DIM_ID, " +
                    "DISTRIBUTOR_DIM_ID, " +
                    "DIST_SKU, " +
                    "DIST_PACK, " +
                    "DIST_UOM, " +
                    "DIST_ID, " +
                    "JD_DIST_SKU, " +
                    "JD_DIST_PACK, " +
                    "JD_DIST_UOM, " +
                    "JD_DIST_ID, " +
                    "ADD_BY, " +
                    "ADD_DATE, " +
                    "MOD_BY, " +
                    "MOD_DATE " +
                ") " +
                "SELECT " +
                    tblDwItemDistrSeq + ".NEXTVAL, " +
                    "tmp.ITEM_DIM_ID, " +
                    "tmp.DISTRIBUTOR_DIM_ID, " +
                    "tmp.DIST_SKU, " +
                    "tmp.DIST_PACK, " +
                    "tmp.DIST_UOM, " +
                    "tmp.DIST_ID, " +
                    "tmp.JD_DIST_SKU, " +
                    "tmp.JD_DIST_PACK, " +
                    "tmp.JD_DIST_UOM, " +
                    "tmp.JD_DIST_ID, " +
                    "?, " +
                    "SYSDATE, " +
                    "?, " +
                    "SYSDATE " +
                "FROM  " +
                    tblDwItemDistrTemp + " tmp " +
                "WHERE " +
                    "tmp.ITEM_DISTRIBUTOR_ID IS NULL";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, userName);
            insertedRowCount = stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at inserting of records into the table " +
                tblDwItemDistr + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }

        ///
    }

    /**
     * Fills the DW_ORDER_FACT table in the "Data Warehouse".
     * Use the parameter 'storeSchemaName' to define the schema for 'CLW'-tables with data.
     * Use the parameter 'dbSchemaName' to define the schema for the tables from 'Data Warehouse'.
     */
    public StringBuffer fillDwOrderFactTable(int pStoreId, String dbClwNamespace,
                                     String dbDwNamespace, String pDbLink, int pProcessId) throws RemoteException, Exception {

      Connection connection = null;
      dbLink = pDbLink;
      tempSuffix = "_" + String.valueOf(pProcessId);
      dwTempUser = System.getProperty("dw.loader.tempUser");
//      dwTempUser = Utility.getInstallPropertyValue ("dwtempUser");

      try {
         connection = getReportConnection();
         StringBuffer report = fillDwOrderFactTable(connection, pStoreId, dbClwNamespace, dbDwNamespace);
         return report;
       }
       catch (SQLException ex) {
         throw new RemoteException(ex.getMessage());
       }
       catch (NamingException ex) {
         throw new RemoteException(ex.getMessage());
       }
       catch (DataNotFoundException ex) {
         throw new DataNotFoundException(ex.getMessage());
       }
       finally {
         closeConnection(connection);
       }
     }

     public  void fillDwDateDimension(Connection connection,
           String dbClwNamespace, String dbDwNamespace, int pStoreId) throws SQLException, RemoteException {

       final String tblDwStoreDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_STORE_DIM);
       final String tblDwDateDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_DATE_DIM);
       final String tblClwOrder = getFullTableName(dbClwNamespace, TBL_CLW_ORDER);
       final String tblClwInvoice = getFullTableName(dbClwNamespace, TBL_CLW_INVOICE_DIST);

       int storeDimId = getStoreDimId(connection, pStoreId, dbClwNamespace, tblDwStoreDim);
       String lastUploadDateOrder = "TO_DATE('" + lastOrderFactUploadDate +"','" + UPLOAD_DATE_FORMAT +"')";
       String lastUploadDateInvoice = "TO_DATE('" + lastInvoiceFactUploadDate +"','" + UPLOAD_DATE_FORMAT +"')";

       fillDwDateDimension(connection, pStoreId, tblClwOrder, tblDwDateDim, "ORIGINAL_ORDER_DATE", lastUploadDateOrder);
       fillDwDateDimension(connection, pStoreId, tblClwInvoice, tblDwDateDim, "INVOICE_DATE", lastUploadDateInvoice);

     }

    private  void fillDwDateDimension(Connection connection,
            int pStoreId, String tblClwSource, String tblDwDateDim, String clwSourceFldName, String lastUploadDate) throws SQLException, RemoteException {

      if (connection == null) {
        throw new SQLException("Connection is null!");
      }

      final String action = "< DataWarehouseBean >[" + tblDwDateDim + " Creation] ";
      String sql = null;
      PreparedStatement stmt = null;
      try {
         sql =
             "INSERT INTO " + tblDwDateDim + " \n" +
             "   (  \n" +
             "     DATE_DIM_ID,     \n" +
             "     CALENDAR_DATE,   \n" +
             "     YEAR,            \n" +
             "     MONTH,           \n" +
             "     DAY_OF_WEEK ,    \n" +
             "     MONTH_NUMBER,    \n" +
             "     WEEK_NUMBER,     \n" +
             "     QUARTER          \n" +
             "   )    \n" +
             "SELECT  \n" +
             tblDwDateDim +"_SEQ.NEXTVAL, \n" +
             "    sub.* \n" +
             "FROM  \n" +
             "(SELECT distinct \n" +
             clwSourceFldName +" , \n" +
             "    to_number(to_char("+clwSourceFldName+", 'YYYY')) as YEAR, \n" +
             "    to_char("+clwSourceFldName+", 'MONTH')           as MONTH, \n" +
             "    to_char("+clwSourceFldName+",'Day')              as DAY_OF_WEEK, \n" +
             "    to_number(to_char("+clwSourceFldName+", 'mm'))   as MONTH_NUMBER, \n" +
             "    to_number(to_char("+clwSourceFldName+",'W'))     as WEEK_NUMBER, \n" +
             "    to_number(to_char("+clwSourceFldName+",'Q'))     as QUARTER \n" +
             "  FROM  \n" +
             tblClwSource + " \n" +
             "  WHERE   \n" +
             "  MOD_DATE between " + lastUploadDate + " and SYSDATE \n" +
             "     and STORE_ID = " + pStoreId +
             "     and "+clwSourceFldName+" not in (select CALENDAR_DATE from "+ tblDwDateDim + ")" +
             " ) sub ";

         stmt = connection.prepareStatement(sql);
         stmt.executeUpdate();
         stmt.close();
       }
       catch (SQLException ex) {
           String msg = "An error occurred at filling " + clwSourceFldName+ " into " +
               tblDwDateDim + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
       }
    }

    private  void fillDwDSRDimension(Connection connection,
            int pStoreDimId, String tblClwSource, String tblDwDim) throws SQLException, RemoteException {

      if (connection == null) {
        throw new SQLException("Connection is null!");
      }

      final String action = "< DataWarehouseBean >[" + tblDwDim + " Creation] ";
      String sql = null;
      PreparedStatement stmt = null;
      try {
         sql =
             " INSERT INTO " + tblDwDim + " \n"+
            "     (  \n"+
            "       SALES_REP_DIM_ID,   \n"+
            "       STORE_DIM_ID,    \n"+
            "       REP_NAME,     \n"+
            "       REP_CODE,     \n"+
            "       ADD_BY,          \n"+
            "       ADD_DATE ,       \n"+
            "       MOD_BY ,         \n"+
            "       MOD_DATE         \n"+
            "     )  \n"+
            "  SELECT  \n"+
//            "      DW_SALES_REP_DIM_SEQ.NEXTVAL, \n"+
            tblDwDim +"_SEQ.NEXTVAL, \n"+
            pStoreDimId + ", \n" +
            "     sub.* ,  \n"+
            "     'loader'      as add_name , \n"+
            "     SYSDATE       as add_date,  \n"+
            "     'loader'      as mod_name,  \n"+
            "     SYSDATE       as mode_date  \n"+
            "   FROM    \n"+
            "       ( SELECT distinct  Nvl(REP_NAME,'NA'),  REP_NUM  \n"+
            "         FROM " + tblClwSource + " \n"+
            "         WHERE  REP_NUM is not null OR REP_NAME is not null \n"+
            "         MINUS \n" +
            "         SELECT REP_NAME, REP_CODE \n" +
            "         FROM " + tblDwDim + " where STORE_DIM_ID =  " + pStoreDimId +
           "         ) sub ";


         stmt = connection.prepareStatement(sql);
         stmt.executeUpdate();
         stmt.close();
       }
       catch (SQLException ex) {
           String msg = "An error occurred at filling data  into " +
               tblDwDim + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
       }
    }
    private  void fillDwRegionDimension(Connection connection,
            int pStoreDimId, String tblClwSource, String tblDwDim) throws SQLException, RemoteException {

      if (connection == null) {
        throw new SQLException("Connection is null!");
      }

      final String action = "< DataWarehouseBean >[" + tblDwDim + " Creation] ";
      String sql = null;
      PreparedStatement stmt = null;
      try {
         sql =
            " INSERT INTO " + tblDwDim + " \n"+
            "     (  \n"+
            "       REGION_DIM_ID,   \n"+
            "       STORE_DIM_ID,    \n"+
            "       REGION_NAME,     \n"+
            "       REGION_CODE,     \n"+
            "       ADD_BY,          \n"+
            "       ADD_DATE ,       \n"+
            "       MOD_BY ,         \n"+
            "       MOD_DATE         \n"+
            "     )  \n"+
            "  SELECT  \n"+
//            "      DW_REGION_DIM_SEQ.NEXTVAL, \n"+
            tblDwDim +"_SEQ.NEXTVAL, \n"+
            pStoreDimId + ", \n" +
            "     sub.* ,  \n"+
            "     'loader'      as add_name , \n"+
            "     SYSDATE       as add_date,  \n"+
            "     'loader'      as mod_name,  \n"+
            "     SYSDATE       as mode_date  \n"+
            "  FROM    \n"+
            "      (SELECT distinct  'Region '||trim(BRANCH), trim(BRANCH) \n"+
            "        FROM " + tblClwSource + " tmp \n"+
            "        WHERE BRANCH is not null \n"+
            "        MINUS \n"+
            "        SELECT REGION_NAME, REGION_CODE \n" +
            "        FROM " + tblDwDim +
            "        WHERE STORE_DIM_ID = " + pStoreDimId +
            "       ) sub ";


         stmt = connection.prepareStatement(sql);
         stmt.executeUpdate();
         stmt.close();
       }
       catch (SQLException ex) {
           String msg = "An error occurred at filling data into " +
               tblDwDim + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
       }
    }

    private  StringBuffer fillDwOrderFactTable(Connection connection, int pStoreId,
         String dbClwNamespace, String dbDwNamespace) throws SQLException, RemoteException, Exception {


         if (connection == null) {
           throw new SQLException("Connection is null!");
         }

         String DEFAULT_USER_NAME_TO_UPDATE = "'OrderLoader'";
//         pStoreId = 112020; //To do parameter!!!
         String startUploadDate = getCurrentDate();

         final String tblClwOrder = getFullTableName(dbClwNamespace, TBL_CLW_ORDER);
         final String tblClwOrderItem = getFullTableName(dbClwNamespace, TBL_CLW_ORDER_ITEM);
         final String tblClwItemMapping = getFullTableName(dbClwNamespace, TBL_CLW_ITEM_MAPPING);
         final String tblClwBusEntity = getFullTableName(dbClwNamespace, TBL_CLW_BUS_ENTITY);

         final String tblDwStoreDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_STORE_DIM);
         final String tblDwCategoryDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_CATEGORY_DIM);
         final String tblDwItemDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_ITEM_DIM);
         final String tblDwManufDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_MANUFACTURER_DIM);
         final String tblDwDistDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_DISTRIBUTOR_DIM);
         final String tblDwDateDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_DATE_DIM);
         final String tblDwAccountDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_ACCOUNT_DIM);
         final String tblDwSiteDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_SITE_DIM);

         final String tblDwOrderFactTemp = getFullTableName(dwTempUser, TBL_DW_ORDER_FACT_TEMP, tempSuffix);
         final String tblDwOrderFact = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_ORDER_FACT);

         int storeDimId = getStoreDimId(connection, pStoreId, dbClwNamespace, tblDwStoreDim);
         String lastUploadDate = "TO_DATE('" + lastOrderFactUploadDate +"','" + UPLOAD_DATE_FORMAT +"')";
         //filling the ORIGINAL_ORDER_DATEs into DW_DATE_DIM
//         fillDwDateDimension(connection, pStoreId, tblClwOrder, tblDwDateDim, "ORIGINAL_ORDER_DATE", lastUploadDate);

         String ignoreOrderStatusList =getOrderStatusListAsString();
//         final String tblClwItemMapping = getFullTableName(dbClwNamespace, TBL_CLW_ITEM_MAPPING);


         final String action = "< DataWarehouseBean >[" + tblDwOrderFact + " Creation] ";

         String sql = null;
         PreparedStatement stmt = null;
         boolean isExistTempTable = false;

         ////////////////////////////////////////////////////////////////////////
         /// Creation of temporal table 'DW_ORDER_FACT_TEMP'.
         /// The table 'DW_ORDER_FACT_TEMP' contains data
         /// to upload data into the table 'DW_ORDER_FACT' from Data Warehouse.
         ////////////////////////////////////////////////////////////////////////

 /*        /// Checking of existence of table 'DW_ORDER_FACT_TEMP'
         try {
             isExistTempTable = checkDbTableOnExistence(connection,
                            dbDwNamespace, TBL_DW_ORDER_FACT_TEMP);
         }
         catch (SQLException ex) {
             String msg = "An error occurred at checking existence of table " +
                 tblDwOrderFactTemp + ". " + ex.getMessage();
             throw new SQLException("^clw^"+msg+ "^clw^");
         }
*/
         /// Deleting of already existing table 'TBL_DW_ORDER_FACT_TEMP'
         if (isExistTempTable) {
             try {
                 sql = "DROP TABLE " + tblDwOrderFactTemp + " PURGE";
                 stmt = connection.prepareStatement(sql);
                 stmt.executeUpdate();
                 stmt.close();
             }
             catch (SQLException ex) {
                 String msg = "An error occurred at deleting of table " +
                     tblDwOrderFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
                 throw new SQLException("^clw^"+msg+ "^clw^");
             }
         }

         /// Creation of the table 'TBL_DW_ORDER_FACT_TEMP'
         try {
             sql =
                 "CREATE TABLE " + tblDwOrderFactTemp + " " +
                 "( " +
                 "ORDER_FACT_ID         NUMBER, \n" +
                 "ORDER_ID              NUMBER, \n" +
                 "ORDER_ITEM_ID         NUMBER, \n" +
                 "ORDER_MOD_DATE        DATE, \n" +
                 "MANUFACTURER_DIM_ID   NUMBER, \n" +
                 "DATE_DIM_ID           NUMBER, \n" +
                 "DISTRIBUTOR_DIM_ID    NUMBER, \n" +
                 "ITEM_DIM_ID           NUMBER, \n" +
                 "CATEGORY_DIM_ID       NUMBER, \n" +
                 "STORE_DIM_ID          NUMBER, \n" +
                 "SALES_REP_DIM_ID      NUMBER, \n" +
                 "REGION_DIM_ID         NUMBER, \n" +
                 "ACCOUNT_DIM_ID        NUMBER, \n" +
                 "SITE_DIM_ID           NUMBER, \n" +
                 "PRICE                 NUMBER(15,3), \n" +
                 "QUANTITY              NUMBER, \n" +
                 "ERP_PO_NUM            VARCHAR2(30 BYTE), \n" +
                 "ORDER_ITEM_STATUS_CD  VARCHAR2(30 BYTE), \n" +
                 "ORDER_STATUS_CD       VARCHAR2(30 BYTE), \n" +
                 "CUST_ITEM_SKU_NUM     VARCHAR2(30 BYTE), \n" +
                 "DIST_SKU              VARCHAR2(30 BYTE), \n" +
                 "DIST_UOM              VARCHAR2(30 BYTE), \n" +
                 "DIST_PACK             VARCHAR2(30 BYTE), \n" +
                 "DIST_ERP_NUM          VARCHAR2(30 BYTE), \n" +
                 "LOCALE_CD             VARCHAR2(20 BYTE), \n" +
                 "CURRENCY_CD           VARCHAR2(20 BYTE), \n" +
                 "STORE_ID              NUMBER, \n" +
                 "ACCOUNT_ID            NUMBER, \n" +
                 "SITE_ID               NUMBER, \n" +
                 "ITEM_ID               NUMBER, \n" +
                 "DIST_ID               NUMBER, \n" +
                 "ORIGINAL_ORDER_DATE   DATE, \n" +
                 "SALE_TYPE             VARCHAR2(20 BYTE), \n" +
                 "ORDER_NUM             VARCHAR2(50 BYTE), \n" +
                 "REQUEST_PO_NUM        VARCHAR2(50 BYTE), \n" +
                 "TAX_AMOUNT            NUMBER(15,3), \n" +
                 "TOTAL_FREIGHT_COST    NUMBER(15,3), \n" +
                 "TOTAL_MISC_COST       NUMBER(15,3), \n" +
                 "UPDATE_TYPE           VARCHAR2(30 BYTE)  \n" +
                 ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at creation of table " +
                 tblDwOrderFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }


         ////////////////////////////////////////////////////////////////////////
         /// Filling of temporal 'TBL_DW_ORDER_FACT_TEMP'-table by data.
         ////////////////////////////////////////////////////////////////////////

         /// Insert of data from store 'JanPak' into temporal table.
         try {
             sql =
                 "INSERT INTO " + tblDwOrderFactTemp + " " +
                 "SELECT " +
                     "NULL,                     --ORDER_FACT_ID   \n" +
                     "o.ORDER_ID,               --ORDER_ID        \n" +
                     "oi.ORDER_ITEM_ID,         --ORDER_ITEM_ID   \n" +
                     "(select  max( mod_date) mod_date from  \n" +
                     " (  \n" +
                     "   select  distinct order_id, mod_date    \n" +
                     "     from " +tblClwOrder +" where mod_date > " + lastUploadDate + "\n" +
                     "   union all  \n" +
                     "   select  distinct order_id, mod_date  \n" +
                     "     from " + tblClwOrderItem +" where mod_date > " + lastUploadDate + "\n" +
  //                   "   union all  \n" +
  //                   "   select distinct order_id, mod_date    \n" +
  //                   "     from ng.CLW_ORDER_PROPERTY where mod_date >  " + lastUploadDate + "\n" +
  //                   "   union all  \n" +
  //                   "   select distinct order_id, mod_date    \n" +
  //                   "     from ng.CLW_ORDER_META where mod_date >  " + lastUploadDate + "\n" +
                     " ) a  \n" +
                     " where a.order_id = o.order_id    \n" +
                     " group by a.order_id ) mod_date,  --ORDER_MOD_DATE  \n" +
                     "NULL,                     --MANUFACTURER_DIM_ID  \n" +
                     "NULL,                     --DATE_DIM_ID          \n" +
                     "NULL,                     --DISTRIBUTOR_DIM_ID   \n" +
                     "NULL,                     --ITEM_DIM_ID          \n" +
                     "NULL,                     --CATEGORY_DIM_ID      \n" +
                     storeDimId +",             --STORE_DIM_ID         \n" +
                     "NULL,                     --SALES_REP_DIM_ID     \n" +
                     "NULL,                     --ACCOUNT_DIM_ID       \n" +
                     "NULL,                     --SITE_DIM_ID          \n" +
                     "NULL,                     --REGION_DIM_ID        \n" +
                     "oi.CUST_CONTRACT_PRICE ,   --PRICE               \n" +
                     "oi.TOTAL_QUANTITY_ORDERED, --QUANTITY            \n" +
                     "oi.ERP_PO_NUM,            --ERP_PO_NUM           \n" +
                     "oi.ORDER_ITEM_STATUS_CD,  --ORDER_ITEM_STATUS_CD \n" +
                     "o.ORDER_STATUS_CD,        --ORDER_STATUS_CD \n" +
                     "oi.cust_item_sku_num ,    --cust_item_sku_num    \n" +
                     "oi.DIST_ITEM_SKU_NUM,     --DIST_SKU             \n" +
                     "oi.DIST_ITEM_UOM,         --DIST_UOM             \n" +
                     "oi.DIST_ITEM_PACK,        --DIST_PACK            \n" +
                     "oi.DIST_ERP_NUM,          --DIST_ERP_NUM         \n" +
                     "o.LOCALE_CD,              --LOCATE_CD            \n" +
                     "o.CURRENCY_CD,            --CURRENCY_CD          \n" +
                     pStoreId + ",              --STORE_ID             \n" +
                     "o.ACCOUNT_ID ,            --ACCOUNT_ID           \n" +
                     "o.SITE_ID,                --SITE_ID              \n" +
                     "oi.ITEM_ID,               --ITEM_ID              \n" +
                     "NULL,                     --DIST_ID              \n" +
                     "o.ORIGINAL_ORDER_DATE,    --ORIGINAL_ORDER_DATE  \n" +
                     "oi.SALE_TYPE_CD  ,         --SALE_TYPE \n" +
                     "o.ORDER_NUM  ,            --ORDER_NUM  \n" +
                     "o.REQUEST_PO_NUM,         --REQUEST_PO_NUM  \n" +
                     "oi.TAX_AMOUNT,            --TAX_AMOUNT  \n" +
                     "o.TOTAL_FREIGHT_COST,    --TOTAL_FREIGHT_COST  \n" +
                     "o.TOTAL_MISC_COST,       --TOTAL_MISC_COST  \n" +
                     "NULL                      --UPDATE_TYPE          \n" +
                   "FROM    \n" +
                     tblClwOrderItem + " oi,   \n" +
                     tblClwOrder + " o   \n" +
                   "WHERE   \n" +
                     "o.order_id = oi.ORDER_ID  \n" +
                     "AND o.MOD_DATE > " + lastUploadDate + "\n" +
                     "AND o.ORIGINAL_ORDER_DATE >= TO_DATE('" + INITIAL_UPLOAD_DATE +"','" + UPLOAD_DATE_FORMAT + "')  \n" +
                     "AND NVL(oi.ORDER_ITEM_STATUS_CD, '0')  != '"+ RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED +"'   \n" +
                     "AND o.ORDER_STATUS_CD not in ( " +
                     getOrderStatusListAsString() + "\n" +
                     ") \n"+
                     "AND oi.ITEM_ID is not null  \n" +
                     "AND o.STORE_ID = " + pStoreId ;
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at insertion of records into table " +
                 tblDwOrderFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }


         /// Filling the fields 'DIST_ID' ( Distributor_ID ), ITEM_ID, DIST_INTO_STOCK_COST
         String addCond = "  and BUS_ENTITY_TYPE_CD = 'DISTRIBUTOR' " ;
         ctaUpdate (connection, 0,  tblClwBusEntity , tblDwOrderFactTemp,
                    "BUS_ENTITY_ID", "DIST_ID",
                    "ERP_NUM", "DIST_ERP_NUM", addCond);

         /// Filling the fields 'CATEGORY_DIM_ID','ITEM_DIM_ID', 'MANUFACTURER_DIM_ID'
         createIndex(connection, tblDwOrderFactTemp, "I1" , "ITEM_ID" ) ;
         try {
             sql =
                 "UPDATE " + tblDwOrderFactTemp + " tmp \n" +
                 "SET \n" +
                "(  \n" +
                "    tmp.CATEGORY_DIM_ID, \n" +
                "    tmp.ITEM_DIM_ID,   \n" +
                "    tmp.MANUFACTURER_DIM_ID  \n" +
                ")  \n" +
                "=  \n" +
                "(  \n" +
                "    SELECT  \n" +
                "        item.CATEGORY_DIM_ID, \n" +
                "        item.ITEM_DIM_ID,  \n" +
                "        item.MANUFACTURER_DIM_ID \n" +
                "    FROM  \n" +
                tblDwItemDim +" item  \n" +
                "    WHERE  \n" +
                "         tmp.ITEM_ID = item.ITEM_ID  \n" +
                "     and tmp.STORE_DIM_ID = item.STORE_DIM_ID \n" +
                ")";
             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at updating the columns for Category, Item, Manufactory - dim ids in table " +
                 tblDwOrderFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Filling the field 'SITE_DIM_ID'
         createIndex(connection, tblDwOrderFactTemp, "I2" , "SITE_ID" ) ;


         ctaUpdate (connection, storeDimId, tblDwSiteDim, tblDwOrderFactTemp,
                    "SITE_DIM_ID", "SITE_DIM_ID", "SITE_ID", "SITE_ID", null);

         /// Filling the field 'ACCOUNT_DIM_ID'
        createIndex(connection, tblDwOrderFactTemp, "I3" , "ACCOUNT_DIM_ID" ) ;

        ctaUpdate (connection, storeDimId, tblDwAccountDim, tblDwOrderFactTemp,
                  "ACCOUNT_DIM_ID", "ACCOUNT_DIM_ID", "ACCOUNT_ID", "ACCOUNT_ID", null);

        /// Filling the field 'DISTRIBUTOR_DIM_ID'
        createIndex(connection, tblDwOrderFactTemp, "I4" , "DIST_ID" ) ;

        try {
           sql =
               "UPDATE " + tblDwOrderFactTemp + " tmp \n" +
               "SET tmp.DISTRIBUTOR_DIM_ID = \n" +
               "(  \n" +
               "    SELECT  \n" +
               "        DISTRIBUTOR_DIM_ID \n" +
               "    FROM  \n" +
               tblDwDistDim +" dist  \n" +
               "    WHERE  \n" +
               "            tmp.DIST_ID = dist.DIST_ID \n" +
               "       and  tmp.STORE_DIM_ID = dist.STORE_DIM_ID \n" +
               ")";
           stmt = connection.prepareStatement(sql);
           stmt.executeUpdate();
           stmt.close();
         }
         catch (SQLException ex) {
           String msg = "An error occurred at the filling the field 'DISTRIBUTOR_DIM_ID' in table " +
               tblDwOrderFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Filling the field 'DATE_DIM_ID'
         try {
            sql =
                "UPDATE " + tblDwOrderFactTemp + " tmp \n" +
                "SET tmp.DATE_DIM_ID = \n" +
                "(  \n" +
                "    SELECT  \n" +
                "        DATE_DIM_ID \n" +
                "    FROM  \n" +
                tblDwDateDim +" dd  \n" +
                "    WHERE  \n" +
                "        tmp.ORIGINAL_ORDER_DATE  = dd.CALENDAR_DATE \n" +
                ")";

            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
          }
          catch (SQLException ex) {
            String msg = "An error occurred at the filling the field 'DATE_DIM_ID' in table " +
                tblDwOrderFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
          }


         ////////////////////////////////////////////////////////////////////////
         /// Filling the field 'ORDER_FACT_ID' in the table DW_ORDER_FACT_TEMP.
         /// for the orders which where UPLOADed earlier
         ////////////////////////////////////////////////////////////////////////
         try {
            sql =
                "UPDATE " + tblDwOrderFactTemp + " tmp \n" +
                "SET tmp.order_fact_id = \n" +
                "(  \n" +
                "    SELECT  \n" +
                "        dw.order_fact_id \n" +
                "    FROM  \n" +
                tblDwOrderFact +" dw  \n" +
                "    WHERE  \n" +
                "          tmp.order_item_id = dw.order_item_id \n" +
                "      AND tmp.order_id = dw.order_id \n" +
                "      AND dw.order_mod_date <=" + lastUploadDate + " \n" +
                "      AND dw.exp_date IS NULL \n" +
                ")";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
          }
          catch (SQLException ex) {
            String msg = "An error occurred at the filling the field 'ORDER_FACT_ID' in table " +
                tblDwOrderFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
          }
          /// log Errors :
         String[] sourceFields = new String[]{"ORDER_ID", "ORDER_ITEM_ID"};
         String[] foreignKeys = new String[]{"STORE_DIM_ID","DATE_DIM_ID", "ACCOUNT_DIM_ID", "SITE_DIM_ID", "CATEGORY_DIM_ID", "ITEM_DIM_ID", "MANUFACTURER_DIM_ID"};

         List<StringBuffer> errors = verifyConstraints(connection, sourceFields,  foreignKeys,  tblDwOrderFactTemp);

         int insertedRowCount = 0;
         int updatedRowCount = 0;
         int errorRowCount = errors.size();
         int maxRowsWithNullFK = 100;
         if(errorRowCount > maxRowsWithNullFK){
           throw new DataNotFoundException(action + " ::  More then " +  maxRowsWithNullFK + " rows with NULL foreign key occured in "+tblDwOrderFactTemp+ ". " + errors.toString());
         } else {
          /// Insert of new records into table DW_ORDER_FACT from DW_ORDER_FACT_TEMP

         try {
             sql =
                 "INSERT INTO " + tblDwOrderFact + " dw \n" +
                 "( \n" +
                  "ORDER_FACT_ID           ,\n" +
                  "ORDER_ID                ,\n" +
                  "ORDER_ITEM_ID           ,\n" +
                  "ORDER_MOD_DATE          ,\n" +
                  "MANUFACTURER_DIM_ID     ,\n" +
                  "DATE_DIM_ID             ,\n" +
                  "DISTRIBUTOR_DIM_ID      ,\n" +
                  "ITEM_DIM_ID             ,\n" +
 //                 "CATEGORY_DIM_ID         ,\n" +
                  "STORE_DIM_ID            ,\n" +
                  "SALES_REP_DIM_ID        ,\n" +
                  "ACCOUNT_DIM_ID          ,\n" +
                  "SITE_DIM_ID             ,\n" +
                  "PRICE                   ,\n" +
                  "QUANTITY                ,\n" +
                  "ERP_PO_NUM              ,\n" +
                  "ORDER_ITEM_STATUS_CD    ,\n" +
                  "ORDER_STATUS_CD         ,\n" +
                  "CUST_ITEM_SKU_NUM       ,\n" +
                  "DIST_SKU                ,\n" +
                  "DIST_UOM                ,\n" +
                  "DIST_PACK               ,\n" +
                  "LOCALE_CD               ,\n" +
                  "CURRENCY_CD             ,\n" +
                  "ORDER_NUM             , \n" +
                  "REQUEST_PO_NUM        , \n" +
                  "TAX_AMOUNT            , \n" +
                  "TOTAL_FREIGHT_COST    , \n" +
                  "TOTAL_MISC_COST       , \n" +
                  "SALE_TYPE             , \n" +
                  "ADD_DATE                ,\n" +
                  "ADD_BY                  ,\n" +
                  "EXP_DATE                ,\n" +
                  "EXP_BY                  \n" +

            ") \n" +
            "SELECT \n" +
//                  "DW_ORDER_FACT_SEQ.NEXTVAL ,\n" +
                  tblDwOrderFact+"_SEQ.NEXTVAL ,\n" +
                  "tmp.ORDER_ID                ,\n" +
                  "tmp.ORDER_ITEM_ID           ,\n" +
                  "tmp.ORDER_MOD_DATE          ,\n" +
                  "tmp.MANUFACTURER_DIM_ID     ,\n" +
                  "tmp.DATE_DIM_ID             ,\n" +
                  "tmp.DISTRIBUTOR_DIM_ID      ,\n" +
                  "tmp.ITEM_DIM_ID             ,\n" +
//                  "tmp.CATEGORY_DIM_ID         ,\n" +
                  "tmp.STORE_DIM_ID            ,\n" +
                  "tmp.SALES_REP_DIM_ID        ,\n" +
                  "tmp.ACCOUNT_DIM_ID          ,\n" +
                  "tmp.SITE_DIM_ID             ,\n" +
                  "tmp.PRICE                   ,\n" +
                  "tmp.QUANTITY                ,\n" +
                  "tmp.ERP_PO_NUM              ,\n" +
                  "tmp.ORDER_ITEM_STATUS_CD    ,\n" +
                  "tmp.ORDER_STATUS_CD         ,\n" +
                  "tmp.CUST_ITEM_SKU_NUM       ,\n" +
                  "tmp.DIST_SKU                ,\n" +
                  "tmp.DIST_UOM                ,\n" +
                  "tmp.DIST_PACK               ,\n" +
                  "tmp.LOCALE_CD               ,\n" +
                  "tmp.CURRENCY_CD             ,\n" +
                  "tmp.ORDER_NUM             , \n" +
                  "tmp.REQUEST_PO_NUM        , \n" +
                  "tmp.TAX_AMOUNT            , \n" +
                  "tmp.TOTAL_FREIGHT_COST    , \n" +
                  "tmp.TOTAL_MISC_COST       , \n" +
                  "tmp.SALE_TYPE             , \n" +
                  "SYSDATE                     ,\n" +
                  DEFAULT_USER_NAME_TO_UPDATE+",\n" +
                  "NULL                        ,\n" +
                  "NULL                         \n" +
               "FROM \n" +
                     tblDwOrderFactTemp + " tmp "   ;

             stmt = connection.prepareStatement(sql);
//             stmt.setString(1, userName);
             insertedRowCount = stmt.executeUpdate();
             stmt.close();
         }
         catch (SQLException ex) {
             String msg = "An error occurred at inserting of records into the table " +
                 tblDwOrderFact + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
         }
         
         
         log.info("Inserted "+insertedRowCount+" rows");
         /// Updating the field 'EXP_DATE'
        try {
           sql =
               "UPDATE " + tblDwOrderFact + " dw \n" +
               "SET exp_date = SYSDATE, \n" +
               "    exp_by = " + DEFAULT_USER_NAME_TO_UPDATE + " \n" +   //+ lastUploadDate  +"\n"
               "WHERE dw.order_fact_id IN  \n" +
               "(  \n" +
               "    SELECT  \n" +
               "        tmp.order_fact_id \n" +
               "    FROM " + tblDwOrderFactTemp +" tmp  \n" +
               ")";

           stmt = connection.prepareStatement(sql);
           updatedRowCount = stmt.executeUpdate();
           stmt.close();
         }
         catch (SQLException ex) {
           String msg = "An error occurred at the filling the field 'EXP_DATE' in table " +
               tblDwOrderFact + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
         }

         //DON'T continue if nothing was updated.  Possibly database problems lead to this condition
         //and we want it to be self correcting
         if(insertedRowCount > 0){
         // Updating DW_STORE_DIM to set last_upload_order_date
	         try {
	          sql =
	              "UPDATE " + tblDwStoreDim + " dw \n" +
	              "SET LAST_UPLOAD_ORDER_DATE = TO_DATE('" + startUploadDate  +"', '" + UPLOAD_DATE_FORMAT +"') \n" +
	              "WHERE dw.store_dim_id = " + storeDimId  ;

	          stmt = connection.prepareStatement(sql);
	          stmt.executeUpdate();
	          stmt.close();
	        }
	        catch (SQLException ex) {
	          String msg = "An error occurred at the updating DW_STORE_DIM to set last_upload_order_date " +
	              ". " + ex.getMessage()+
	                 "*** Execute the following request to get more information : " +  sql;
	          throw new SQLException("^clw^"+msg+ "^clw^");
	        }
	     }
       }
       /// REPORT
       StringBuffer report = logReport(tblDwOrderFact, insertedRowCount, updatedRowCount, errors  );
       return report;

     }

     private static void createIndex (Connection connection, String tblName, String indexName, String colName) throws SQLException {

       try {
         String sql =
             "CREATE INDEX " + tblName +"_"+ indexName + " ON " +tblName + "(" + colName + ")";
         PreparedStatement stmt = connection.prepareStatement(sql);
         stmt.executeUpdate();
         stmt.close();
       }
       catch (SQLException ex) {
         String msg = "An error occurred at creating index on " + tblName + "(" + colName + ")" +
             ". " + ex.getMessage();
         throw new SQLException("^clw^"+msg+ "^clw^");
       }


     }

     private static String getCurrentDate(){
       SimpleDateFormat df = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN + " " + "hh:mm:ss a");
       Date dt = new Date();
       String dateS = df.format(dt);

       return dateS;
     }

     private  int getStoreDimId(Connection connection, int pStoreId, String dbClwNamespace, String dwTblName) throws SQLException, RemoteException  {
       String sql = null;
       Statement stmt = null;
       ResultSet rs = null;
       int dimStoreId = 0;
       String dbTblName = getFullTableName(dbClwNamespace, "CLW_BUS_ENTITY");
       SimpleDateFormat df = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN + " " + "hh:mm:ss a");

      Date lastUploadDateOrder = null;
      Date lastUploadDateInvoice = null;
 //     Date lastUploadTime = null;
      try {
           Date lastUploadDate  = df.parse(INITIAL_UPLOAD_DATE);
           stmt = connection.createStatement();
           sql = " SELECT DISTINCT STORE_DIM_ID, LAST_UPLOAD_ORDER_DATE, LAST_UPLOAD_INVOICE_DATE \n" +
                 " FROM " + dwTblName + " WHERE STORE_ID = " + pStoreId;
           rs = stmt.executeQuery(sql);
           // Crate new Store Dim if not exists
           if (rs.next()) {
    //         while (rs.next()) {
               dimStoreId = rs.getInt(1);

               lastUploadDateOrder = Utility.getDateTime(rs.getDate(2),rs.getTime(2) );

               lastUploadDateInvoice = Utility.getDateTime(rs.getDate(3),rs.getTime(3) );
    //         }
           } else {
             stmt = connection.createStatement();
             rs = stmt.executeQuery("SELECT " +dwTblName+"_SEQ.NEXTVAL FROM DUAL");
             rs.next();
             dimStoreId = rs.getInt(1);
             stmt.close();

             sql = "INSERT INTO " + dwTblName + " \n" +
                 "SELECT  \n" +
                 dimStoreId + ", \n" +
                 "   SHORT_DESC, \n" +
                 "   BUS_ENTITY_ID, \n" +
                 "   'dwBean', \n" +
                 "   sysdate, \n" +
                 "   'dwBean', \n" +
                 "   sysdate, \n" +
                 "TO_DATE('" + INITIAL_UPLOAD_DATE+"','"+UPLOAD_DATE_FORMAT+"'),   \n" +
                 "TO_DATE('" + INITIAL_UPLOAD_DATE+"','"+UPLOAD_DATE_FORMAT+"')   \n" +
                 "FROM  \n" +
                 dbTblName + " \n" +
                 "WHERE  \n" +
                 "   BUS_ENTITY_ID = " +pStoreId  ;
             stmt = connection.createStatement();
             rs = stmt.executeQuery(sql);
           }

           lastUploadDateOrder = (lastUploadDateOrder == null) ? lastUploadDate : lastUploadDateOrder;
           lastUploadDateInvoice = (lastUploadDateInvoice == null) ? lastUploadDate : lastUploadDateInvoice;

       } catch (SQLException e) {
           throw e;
       } catch (ParseException e) {
           throw new RemoteException(e.getMessage());
       } finally {
           if (rs != null) {
               try {
                   rs.close();
               } catch (SQLException sqle) {
                   sqle.printStackTrace();
               }
           }
           if (stmt != null) {
               try {
                   stmt.close();
               } catch (SQLException sqle) {
                   sqle.printStackTrace();
               }
           }
       }

       lastOrderFactUploadDate = df.format(lastUploadDateOrder);
       lastInvoiceFactUploadDate = df.format(lastUploadDateInvoice);
       return dimStoreId;
     }

     /**
      * Fills the DW_INVOICE_FACT table in the "Data Warehouse".
      * Use the parameter 'storeSchemaName' to define the schema for 'CLW'-tables with data.
      * Use the parameter 'dbSchemaName' to define the schema for the tables from 'Data Warehouse'.
      */
     public StringBuffer fillDwInvoiceFactTable(int pStoreId, String dbClwNamespace,
                                      String dbDwNamespace, String pDbLink, String mandatoryProperties, Integer percentOfErrors, int pProcessId) throws RemoteException, Exception {

       Connection connection = null;
       dbLink = pDbLink;
       tempSuffix = "_" + String.valueOf(pProcessId);
       dwTempUser = System.getProperty("dw.loader.tempUser");

       boolean isExistBranchDSRProperty = Utility.isSet(mandatoryProperties) && mandatoryProperties.toUpperCase().equals("TRUE");
       try {
          connection = getReportConnection();
          StringBuffer report = fillDwInvoiceFactTable(connection, pStoreId, dbClwNamespace, dbDwNamespace, isExistBranchDSRProperty, percentOfErrors);
          return report;
        }
        catch (SQLException ex) {
          ex.printStackTrace();
          throw new RemoteException(ex.getMessage());
        }
        catch (NamingException ex) {
          ex.printStackTrace();
          throw new RemoteException(ex.getMessage());
        }
        catch (DataNotFoundException ex) {
          throw new DataNotFoundException(ex.getMessage());
        }
       finally {
          closeConnection(connection);
        }
      }
      private  StringBuffer fillDwInvoiceFactTable(Connection connection, int pStoreId,
           String dbClwNamespace, String dbDwNamespace, boolean isExistBranchDSRProperty, Integer percentOfErrors) throws SQLException, RemoteException, Exception {


           if (connection == null) {
             throw new SQLException("Connection is null!");
           }

           String DEFAULT_USER_NAME_TO_UPDATE = "'InvoiceLoader'";
//         pStoreId = 112020; //To do parameter!!!
           String startUploadDate = getCurrentDate();

           final String tblClwInvoiceDist = getFullTableName(dbClwNamespace, TBL_CLW_INVOICE_DIST);
           final String tblClwInvoiceDistDetail = getFullTableName(dbClwNamespace, TBL_CLW_INVOICE_DIST_DETAIL);
           final String tblClwInvoiceCust = getFullTableName(dbClwNamespace, TBL_CLW_INVOICE_CUST);
           final String tblClwInvoiceCustDetail = getFullTableName(dbClwNamespace, TBL_CLW_INVOICE_CUST_DETAIL);

           final String tblClwItemMapping = getFullTableName(dbClwNamespace, TBL_CLW_ITEM_MAPPING);
           final String tblClwOrderProperty = getFullTableName(dbClwNamespace, TBL_CLW_ORDER_PROPERTY);

           final String tblDwStoreDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_STORE_DIM);
           final String tblDwCategoryDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_CATEGORY_DIM);
           final String tblDwItemDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_ITEM_DIM);
           final String tblDwManufDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_MANUFACTURER_DIM);
           final String tblDwDistDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_DISTRIBUTOR_DIM);
           final String tblDwDateDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_DATE_DIM);
           final String tblDwAccountDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_ACCOUNT_DIM);
           final String tblDwSiteDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_SITE_DIM);
           final String tblDwRegionDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_REGION_DIM);
           final String tblDwDSRDim = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_SALES_REP_DIM);

           final String tblDwInvoiceFactTemp = getFullTableName(dwTempUser, TBL_DW_INVOICE_FACT_TEMP, tempSuffix);
           final String tblDwInvoiceFact = getFullTableName(dbDwNamespace, DataWarehouse.TBL_DW_INVOICE_FACT);

           final String tblDwTemp = dwTempUser+"."+"DW_TEMP"+tempSuffix;
           // Search for TYPE of Store

          PropertyService propertyBean = null;
          try {
            APIAccess factory = new APIAccess();
            propertyBean = factory.getPropertyServiceAPI();
          }
          catch (Exception ex) {
            ex.getStackTrace();
            throw new RemoteException(ex.getMessage());
          }
           String storeType = propertyBean.checkBusEntityProperty(pStoreId, RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
           // Getting STORE_DIM_ID
           int storeDimId = getStoreDimId(connection, pStoreId, dbClwNamespace, tblDwStoreDim);
           String lastUploadDate = "TO_DATE('" + lastInvoiceFactUploadDate +"','" + UPLOAD_DATE_FORMAT +"')";

           if (storeType.equals(RefCodeNames.STORE_TYPE_CD.MLA)) {
//             fillDwDateDimension(connection, pStoreId, tblClwInvoiceCust, tblDwDateDim, "INVOICE_DATE", lastUploadDate);
           } else {
//             fillDwDateDimension(connection, pStoreId, tblClwInvoiceDist, tblDwDateDim, "INVOICE_DATE", lastUploadDate);
           }
           //filling the INVOICE_DATEs into DW_DATE_DIM
//           fillDwDateDimension(connection, pStoreId, tblClwInvoiceDist, tblDwDateDim, "INVOICE_DATE", lastUploadDate);

//           String ignoreOrderStatusList =getOrderStatusListAsString();


           final String action = "< DataWarehouseBean >[" + tblDwInvoiceFact + " Creation] ";

           String sql = null;
           PreparedStatement stmt = null;
           boolean isExistTempTable = false;

           ////////////////////////////////////////////////////////////////////////
           /// Creation of temporal table 'DW_INVOICE_FACT_TEMP'.
           /// The table 'DW_ORDER_FACT_TEMP' contains data
           /// to upload data into the table 'DW_INVOICE_FACT' from Data Warehouse.
           ////////////////////////////////////////////////////////////////////////

 /*          /// Checking of existence of table 'DW_INVOICE_FACT_TEMP'
           try {
               isExistTempTable = checkDbTableOnExistence(connection,
                              dbDwNamespace, TBL_DW_INVOICE_FACT_TEMP);
           }
           catch (SQLException ex) {
               String msg = "An error occurred at checking existence of table " +
                   tblDwInvoiceFactTemp + ". " + ex.getMessage();
               throw new SQLException("^clw^"+msg+ "^clw^");
           }
*/
           /// Deleting of already existing table 'TBL_DW_INVOICE_FACT_TEMP'
           if (isExistTempTable) {
               try {
                   sql = "DROP TABLE " + tblDwInvoiceFactTemp + " PURGE";

                   stmt = connection.prepareStatement(sql);
                   stmt.executeUpdate();
                   stmt.close();
               }
               catch (SQLException ex) {
                   String msg = "An error occurred at deleting of table " +
                       tblDwInvoiceFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
                   throw new SQLException("^clw^"+msg+ "^clw^");
               }
           }

           /// Creation of the table 'TBL_DW_INVOICE_FACT_TEMP'
           try {
               sql =
                   "CREATE TABLE " + tblDwInvoiceFactTemp + " " +
                   "( " +
                   "INVOICE_FACT_ID       NUMBER, \n" +
                   "MANUFACTURER_DIM_ID   NUMBER, \n" +
                   "DATE_DIM_ID           NUMBER, \n" +
                   "DISTRIBUTOR_DIM_ID    NUMBER, \n" +
                   "ITEM_DIM_ID           NUMBER, \n" +
                   "CATEGORY_DIM_ID       NUMBER, \n" +
                   "STORE_DIM_ID          NUMBER, \n" +
                   "SALES_REP_DIM_ID      NUMBER, \n" +
                   "REGION_DIM_ID         NUMBER, \n" +
                   "ACCOUNT_DIM_ID        NUMBER, \n" +
                   "SITE_DIM_ID           NUMBER, \n" +

                   "INVOICE_DIST_ID         NUMBER(38) ,       \n" +
                   "BUS_ENTITY_ID           NUMBER(38),        \n" +
                   "INVOICE_NUM             VARCHAR2(50 BYTE), \n" +
                   "INVOICE_DATE            DATE,              \n" +
                   "INVOICE_STATUS_CD       VARCHAR2(30 BYTE), \n" +
                   "ADD_DATE                DATE,              \n" +
                   "ADD_BY                  VARCHAR2(30 BYTE), \n" +
                   "STORE_ID                NUMBER(38),        \n" +
                   "SITE_ID                 NUMBER(38),        \n" +
                   "ACCOUNT_ID              NUMBER(38),        \n" +

                   "INVOICE_DIST_DETAIL_ID  NUMBER(38),        \n" +
                   "DIST_LINE_NUMBER        NUMBER(38),        \n" +
                   "DIST_ITEM_SKU_NUM       VARCHAR2(60 BYTE), \n" +
                   "DIST_ITEM_SHORT_DESC    VARCHAR2(255 BYTE), \n" +
                   "DIST_ITEM_UOM           VARCHAR2(30 BYTE), \n" +
                   "DIST_ITEM_PACK          VARCHAR2(30 BYTE), \n" +
                   "DIST_ITEM_QUANTITY      NUMBER(38),        \n" +
                   "ADJUSTED_COST           NUMBER(13,3),      \n" +
                   "ITEM_RECEIVED_COST      NUMBER(13,2),      \n" +
                   "ERP_PO_REF_LINE_NUM     NUMBER(38),        \n" +
                   "INVOICE_DIST_SKU_NUM    VARCHAR2(30 BYTE), \n" +
                   "DIST_INTO_STOCK_COST    NUMBER(15,3),      \n" +

                   "STATUS                  VARCHAR2(20 BYTE), \n" +
                   "JD_CONV_FACTOR          NUMBER(15,3),      \n" +
                   "BRANCH                  VARCHAR2(15 BYTE), \n" +
                   "REP_NUM                 VARCHAR2(15 BYTE), \n" +
                   "REP_NAME                VARCHAR2(50 BYTE), \n" +
                   "MANUF_ID                NUMBER(38),       \n" +
                   "JD_FLAG                 VARCHAR2(5 BYTE), \n" +
                   "ITEM_ID                 NUMBER(38), \n" +
                   "ORDER_ID                NUMBER(38), \n" +
                   "ORDER_ITEM_ID           NUMBER(38), \n" +

                   "CATEG1_ID               NUMBER(38), \n" +
                   "JD_CATEG1_ID            NUMBER(38), \n" +
                   "DISTR_ID                NUMBER(38)  \n" +
                ")";

               stmt = connection.prepareStatement(sql);
               stmt.executeUpdate();
               stmt.close();
           }
           catch (SQLException ex) {
               String msg = "An error occurred at creation of table " +
                   tblDwInvoiceFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
               throw new SQLException("^clw^"+msg+ "^clw^");
           }


           ////////////////////////////////////////////////////////////////////////
           /// Filling of temporal 'TBL_DW_INVOICE_FACT_TEMP'-table by data.
           ////////////////////////////////////////////////////////////////////////

           /// Insert of data from store 'JanPak' into temporal table.

          if (storeType.equals(RefCodeNames.STORE_TYPE_CD.MLA)) {
             initDWInvoiceFactTempCust (connection,  dbClwNamespace, dbDwNamespace, pStoreId, storeDimId, lastUploadDate);
          } else {
             initDWInvoiceFactTempDist (connection,  dbClwNamespace, dbDwNamespace, pStoreId, storeDimId, lastUploadDate);
          }
           /// Filling the fields 'BRANCH','REP_NUM', 'REP_NAME'
          createIndex(connection, tblDwInvoiceFactTemp, "I0" , "INVOICE_DIST_ID" ) ;
          createIndex(connection, tblDwInvoiceFactTemp, "I1" , "INVOICE_DIST_DETAIL_ID" ) ;

          if (isExistBranchDSRProperty) {
            try {

              boolean isExistTemp = checkDbTableOnExistence(connection,dwTempUser, "DW_TEMP"+tempSuffix);
              if (isExistTemp) {
                sql = "DROP TABLE " + tblDwTemp + " PURGE";
                stmt = connection.prepareStatement(sql);
                stmt.executeUpdate();
                stmt.close();
              }

              sql = "CREATE TABLE " + tblDwTemp + " AS \n" +
                  " SELECT INVOICE_DIST_ID, SHORT_DESC, CLW_VALUE \n" +
                  "   FROM " + tblClwOrderProperty + " \n" +
                  "   WHERE INVOICE_DIST_ID IN \n" +
                  "         (SELECT INVOICE_DIST_ID FROM " + tblDwInvoiceFactTemp + ") \n" +
                  "     AND SHORT_DESC IN ('BRANCH','REP_NUM','REP_NAME') ";
              stmt = connection.prepareStatement(sql);
              stmt.executeUpdate();
              stmt.close();

              createIndex(connection, tblDwTemp, "I01","INVOICE_DIST_ID");
              sql =
                  "UPDATE " + tblDwInvoiceFactTemp + " tmp SET \n" +
                  "  tmp.BRANCH = \n" +
                  "  (SELECT  branch.CLW_VALUE \n" +
                  "     FROM " +  tblDwTemp + " branch \n" +
                  "     WHERE branch.INVOICE_DIST_ID = tmp.INVOICE_DIST_ID AND \n" +
                  "           branch.SHORT_DESC = 'BRANCH'  \n" +
                  "       AND branch.CLW_VALUE is not NULL \n" +
                  " )  ";
              stmt = connection.prepareStatement(sql);
              stmt.executeUpdate();
              stmt.close();
              sql =
                   "UPDATE " + tblDwInvoiceFactTemp + " tmp SET \n" +
                   "  tmp.REP_NUM = \n" +
                   "  (SELECT  repNum.CLW_VALUE \n" +
                   "     FROM " +  tblDwTemp + " repNum \n" +
                   "     WHERE repNum.INVOICE_DIST_ID = tmp.INVOICE_DIST_ID AND \n" +
                   "           repNum.SHORT_DESC = 'REP_NUM' \n" +
                   "       AND repNum.CLW_VALUE is not NULL \n" +
                   " )  ";
               stmt = connection.prepareStatement(sql);
               stmt.executeUpdate();
               stmt.close();

               sql =
                   "UPDATE " + tblDwInvoiceFactTemp + " tmp SET \n" +
                   "  tmp.REP_NAME = \n" +
                   "  (SELECT  repName.CLW_VALUE \n" +
                   "     FROM " +  tblDwTemp + " repName \n" +
                   "     WHERE repName.INVOICE_DIST_ID = tmp.INVOICE_DIST_ID AND \n" +
                   "           repName.SHORT_DESC = 'REP_NAME' \n" +
                   "       AND repName.CLW_VALUE is not NULL \n" +
                   " )  ";
               stmt = connection.prepareStatement(sql);
               stmt.executeUpdate();
              stmt.close();
 /*             sql =
                  "UPDATE " + tblDwInvoiceFactTemp + " tmp \n" +
                  "SET \n" +
                 "(  \n" +
                 "    tmp.BRANCH,   \n" +
                 "    tmp.REP_NUM,  \n" +
                 "    tmp.REP_NAME  \n" +
                 ")  \n" +
                 "=  \n" +
                 "(  \n" +
                 " SELECT branch.CLW_VALUE, \n" +
                "         repNum.CLW_VALUE,   \n" +
                "         repName.CLW_VALUE   \n" +
                "   FROM  \n" +
                tblClwOrderProperty + " branch, \n" +
                tblClwOrderProperty + " repNum,   \n" +
                tblClwOrderProperty + " repName   \n" +
                "  WHERE tmp.INVOICE_DIST_ID = branch.INVOICE_DIST_ID(+) AND \n" +
                "        branch.SHORT_DESC = 'BRANCH'  AND \n" +
                "        tmp.INVOICE_DIST_ID = repNum.INVOICE_DIST_ID(+) AND  \n" +
                "        repNum.SHORT_DESC = 'REP_NUM' AND \n" +
                "        tmp.INVOICE_DIST_ID = repName.INVOICE_DIST_ID(+) AND  \n" +
                "        repName.SHORT_DESC = 'REP_NAME'  \n" +
                 ")";

              stmt = connection.prepareStatement(sql);
              stmt.executeUpdate();
              stmt.close();
  */
            }
            catch (SQLException ex) {
              String msg = "An error occurred at updating the columns for 'BRANCH','REP_NUM', 'REP_NAME' - in table " +
                  tblDwInvoiceFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
              throw new SQLException("^clw^"+msg+ "^clw^");
            }
           ///=====================================================================
            /// Population DW_Region_Dim and Dw_Sales_Rep_Dim tables
            fillDwRegionDimension(connection, storeDimId, tblDwInvoiceFactTemp, tblDwRegionDim);

            fillDwDSRDimension(connection, storeDimId, tblDwInvoiceFactTemp, tblDwDSRDim);
           ///=====================================================================
           /// Filling the fields 'REGION_DIM_ID'
           try {
              sql =
                   "UPDATE " + tblDwInvoiceFactTemp+ " tmp \n" +
                   "SET tmp.REGION_DIM_ID= \n" +
                   "(  \n" +
                   "    SELECT  \n" +
                   "        REGION_DIM_ID \n" +
                   "    FROM  \n" +
                   tblDwRegionDim +" dw  \n" +
                   "    WHERE  \n" +
                   "            trim(tmp.BRANCH) = dw.REGION_CODE \n" +
                   "       AND  dw.STORE_DIM_ID= " + storeDimId + " \n" +
                   ")";

               stmt = connection.prepareStatement(sql);
               stmt.executeUpdate();
               stmt.close();
            }
            catch (SQLException ex) {
               String msg = "An error occurred at the filling the field 'REGION_DIM_ID' in table " +
                   tblDwInvoiceFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
               throw new SQLException("^clw^"+msg+ "^clw^");
            }
             /// Filling the fields 'SALES_REP_DIM_ID'
             try {
                sql =
                    "UPDATE " + tblDwInvoiceFactTemp+ " tmp \n" +
                    "SET tmp.SALES_REP_DIM_ID= \n" +
                    "(  \n" +
                    "    SELECT  \n" +
                    "        SALES_REP_DIM_ID \n" +
                    "    FROM  \n" +
                    tblDwDSRDim +" dw  \n" +
                    "    WHERE  \n" +
                    "            tmp.REP_NUM = dw.REP_CODE \n" +
                    "       AND  NVL(tmp.REP_NAME,'NA') = NVL(dw.REP_NAME,'NA') \n" +
                    "       AND  dw.STORE_DIM_ID= " + storeDimId + " \n" +
                    ")";

                stmt = connection.prepareStatement(sql);
                stmt.executeUpdate();
                stmt.close();
             }
             catch (SQLException ex) {
                String msg = "An error occurred at the filling the field 'SALES_REP_DIM_ID' in table " +
                    tblDwInvoiceFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
                throw new SQLException("^clw^"+msg+ "^clw^");
             }
           }

           /// Filling the fields 'CATEGORY_DIM_ID','ITEM_DIM_ID', 'MANUFACTURER_DIM_ID'
           createIndex(connection, tblDwInvoiceFactTemp, "I2" , "ITEM_ID" ) ;
           ctaUpdate (connection, storeDimId, tblDwItemDim, tblDwInvoiceFactTemp,
                       "CATEGORY_DIM_ID,ITEM_DIM_ID,MANUFACTURER_DIM_ID,JD_ITEM_FL",
                       "CATEGORY_DIM_ID,ITEM_DIM_ID,MANUFACTURER_DIM_ID,JD_FLAG",
                       "ITEM_ID", "ITEM_ID", null);


            /// Filling the field 'SITE_DIM_ID'
           createIndex(connection, tblDwInvoiceFactTemp, "I3" , "SITE_ID" ) ;
           ctaUpdate (connection, storeDimId, tblDwSiteDim, tblDwInvoiceFactTemp,
                      "SITE_DIM_ID", "SITE_DIM_ID", "SITE_ID", "SITE_ID", null);



           /// Filling the field 'ACCOUNT_DIM_ID'
          createIndex(connection, tblDwInvoiceFactTemp, "I4" , "ACCOUNT_DIM_ID" ) ;
          ctaUpdate (connection, storeDimId,  tblDwAccountDim, tblDwInvoiceFactTemp,
                     "ACCOUNT_DIM_ID", "ACCOUNT_DIM_ID", "ACCOUNT_ID", "ACCOUNT_ID", null);


          /// Filling the field 'DISTRIBUTOR_DIM_ID'
 //         createIndex(connection, tblDwInvoiceFactTemp, "I4" , "bus_entity_id" ) ; //already created

          try {
             sql =
                 "UPDATE " + tblDwInvoiceFactTemp + " tmp \n" +
                 "SET \n" +
                 " ( \n"+
                 " tmp.DISTRIBUTOR_DIM_ID,  \n" +
                 " tmp.DISTR_ID  \n" +
                 " ) \n" +
                 " = \n" +
                 "(  \n" +
                 "    SELECT  \n" +
                 "        DISTRIBUTOR_DIM_ID, \n" +
                 "        DIST_ID \n" +
                 "    FROM  \n" +
                 tblDwDistDim +" dist  \n" +
                 "    WHERE  \n" +
                 "            tmp.bus_entity_id = dist.DIST_ID \n" +
                 "       AND  dist.STORE_DIM_ID = " + storeDimId + "\n" +
                 ")";

             stmt = connection.prepareStatement(sql);
             stmt.executeUpdate();
             stmt.close();
           }
           catch (SQLException ex) {
             String msg = "An error occurred at the filling the field 'DISTRIBUTOR_DIM_ID' in table " +
                 tblDwInvoiceFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
             throw new SQLException("^clw^"+msg+ "^clw^");
           }

           createIndex(connection, tblDwInvoiceFactTemp, "I5" , "INVOICE_DATE" ) ;
           /// Filling the field 'DATE_DIM_ID'
           try {
              sql =
                  "UPDATE " + tblDwInvoiceFactTemp + " tmp \n" +
                  "SET tmp.DATE_DIM_ID = \n" +
                  "(  \n" +
                  "    SELECT  \n" +
                  "        DATE_DIM_ID \n" +
                  "    FROM  \n" +
                  tblDwDateDim +" dd  \n" +
                  "    WHERE  \n" +
                  "        tmp.INVOICE_DATE  = dd.CALENDAR_DATE \n" +
                  ")";

              stmt = connection.prepareStatement(sql);
              stmt.executeUpdate();
              stmt.close();
            }
            catch (SQLException ex) {
              String msg = "An error occurred at the filling the field 'DATE_DIM_ID' in table " +
                  tblDwInvoiceFactTemp + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
              throw new SQLException("^clw^"+msg+ "^clw^");
            }


           ////////////////////////////////////////////////////////////////////////
           /// Filling the field 'INVOICE_FACT_ID' in the table DW_INVOICE_FACT_TEMP.
           /// for the invoices which where UPLOADed earlier
           ////////////////////////////////////////////////////////////////////////
           String addCond =
               "      AND ADD_DATE <=" + lastUploadDate + " \n" +
               "      AND EXP_DATE IS NULL \n" ;
           ctaUpdate (connection, storeDimId,  tblDwInvoiceFact, tblDwInvoiceFactTemp,
                      "INVOICE_FACT_ID", "INVOICE_FACT_ID",
                      "INVOICE_DIST_DETAIL_ID,INVOICE_DIST_ID", "INVOICE_DIST_DETAIL_ID,INVOICE_DIST_ID",
                      addCond);


            /// log Errors :
           String[] sourceFields = new String[]{"INVOICE_DIST_ID", "INVOICE_DIST_DETAIL_ID"};
           String[] foreignKeys = new String[]{"STORE_DIM_ID", "DATE_DIM_ID", "ACCOUNT_DIM_ID", "SITE_DIM_ID", "CATEGORY_DIM_ID", "ITEM_DIM_ID", "MANUFACTURER_DIM_ID"};
           if (isExistBranchDSRProperty) {
             foreignKeys = new String[]{"STORE_DIM_ID","DATE_DIM_ID", "ACCOUNT_DIM_ID", "SITE_DIM_ID", "CATEGORY_DIM_ID", "ITEM_DIM_ID", "MANUFACTURER_DIM_ID", "REGION_DIM_ID", "SALES_REP_DIM_ID"};
           }
           List<StringBuffer> errors = verifyConstraints(connection, sourceFields,  foreignKeys,  tblDwInvoiceFactTemp);

           int insertedRowCount = 0;
           int updatedRowCount = 0;
           int errorRowCount = errors.size();
           int maxPercentageWithNullFK = (percentOfErrors == null) ? 2 : percentOfErrors.intValue();// 2;
           Statement stmt1 = connection.createStatement();
           ResultSet rs = stmt1.executeQuery("select count(*) from " + tblDwInvoiceFactTemp);
           rs.next();
           int totalRowCount = rs.getInt(1);
           int percentageWithNullFK = (totalRowCount > 0) ? (errorRowCount*100)/totalRowCount : 0;
           if(percentageWithNullFK > maxPercentageWithNullFK){
             throw new DataNotFoundException(action + " ::  More then " +  maxPercentageWithNullFK + "% rows with NULL foreign key occured in "+tblDwInvoiceFactTemp+". " + errors.toString());
           } else {
            /// Insert of new records into table DW_ORDER_FACT from DW_INVOICE_FACT_TEMP


             try {
               sql =
                   "INSERT INTO " + tblDwInvoiceFact + " dw \n" +
                   "( \n" +
                    "INVOICE_FACT_ID,             \n" +
                    "INVOICE_DIST_ID  ,           \n" +
                    "INVOICE_DIST_DETAIL_ID ,     \n" +

                    "MANUFACTURER_DIM_ID ,        \n" +
                    "DATE_DIM_ID ,                \n" +
                    "DISTRIBUTOR_DIM_ID,          \n" +
                    "ITEM_DIM_ID  ,               \n" +
 //                   "CATEGORY_DIM_ID ,            \n" +
                    "STORE_DIM_ID ,               \n" +
                    "SALES_REP_DIM_ID ,           \n" +
                    "ACCOUNT_DIM_ID ,             \n" +
                    "SITE_DIM_ID ,                \n" +

                    "COST  ,                     \n" +
                    "PRICE ,                     \n" +
                    "QUANTITY,                   \n" +
                    "ERP_PO_NUM,                 \n" +
                    "INVOICE_SOURCE_CD,          \n" +
                    "ERP_PO_REF_NUM,             \n" +
                    "FREIGHT,                    \n" +
                    "SALES_TAX,                  \n" +
                    "MISC_CHARGES,               \n" +
                    "DISCOUNTS,                  \n" +
                    "CREDIT,                     \n" +
                    "INVOICE_STATAUS_CD,         \n" +
                    "INVOICE_NUM,                \n" +
                    "LINE_NUM,                   \n" +
                    "DIST_SKU,                   \n" +
                    "DIST_UOM,                   \n" +
                    "DIST_PACK,                  \n" +
                    "LINE_AMOUNT,                \n" +
                    "LINE_COST,                  \n" +
                    "STATUS,                     \n" +
                    "JD_CONV_FACTOR,             \n" +

                    "ITEM_ID,                    \n" +
                    "CATEG1_ID,                  \n" +
                    "JD_CATEG1_ID,               \n" +
                    "MANUF_ID,                   \n" +
                    "ACCOUNT_ID,                 \n" +
                    "SITE_ID,                    \n" +
                    "DISTR_ID,                   \n" +
                    "REGION_NUM,                 \n" +
                    "DSR_NUM,                    \n" +
                    "DSR_NAME,                   \n" +
                    "JD_FLAG,                    \n" +

                    "ADD_DATE                ,\n" +
                    "ADD_BY                  ,\n" +
                    "EXP_DATE                ,\n" +
                    "EXP_BY                  \n" +
              ") \n" +
              "SELECT \n" +
//                    "DW_INVOICE_FACT_SEQ.NEXTVAL ,\n" +
                    tblDwInvoiceFact + "_SEQ.NEXTVAL ,\n" +
                    "tmp.INVOICE_DIST_ID         ,\n" +
                    "tmp.INVOICE_DIST_DETAIL_ID  ,\n" +

                    "tmp.MANUFACTURER_DIM_ID     ,\n" +
                    "tmp.DATE_DIM_ID             ,\n" +
                    "tmp.DISTRIBUTOR_DIM_ID      ,\n" +
                    "tmp.ITEM_DIM_ID             ,\n" +
//                    "tmp.CATEGORY_DIM_ID         ,\n" +
                    "tmp.STORE_DIM_ID            ,\n" +
                    "tmp.SALES_REP_DIM_ID        ,\n" +
                    "tmp.ACCOUNT_DIM_ID          ,\n" +
                    "tmp.SITE_DIM_ID             ,\n" +

                    "(CASE WHEN Dist_Item_Quantity = 0 THEN NULL ELSE \n" +
                    "  Round ((CASE WHEN Item_Received_Cost=0 THEN 1 ELSE Sign(Item_Received_Cost) END) * \n" +
                    "                    (Abs(Dist_Into_Stock_Cost)/ Abs(Dist_Item_Quantity)),2) END ) ,\n" +
                    "ITEM_RECEIVED_COST,          \n" +
                    "DIST_ITEM_QUANTITY ,         \n" +
                    "NULL,             -- AS ERP_PO_NUM,       \n" +
                    "NULL,             -- AS INVOICE_SOURCE_CD,\n" +
                    "NULL,             -- AS ERP_PO_REF_NUM,   \n" +
                    "NULL,             -- AS FREIGHT,          \n" +
                    "NULL,             -- AS SALES_TAX,        \n" +
                    "NULL,             -- AS MISC_CHARGES,     \n" +
                    "NULL,             -- AS DISCOUNTS,        \n" +
                    "NULL,             -- AS CREDIT,           \n" +
                    "INVOICE_STATUS_CD,-- AS INVOICE_STATAUS_CD,\n" +
                    "INVOICE_NUM,      --  AS INVOICE_NUM,\n" +
                    "DIST_LINE_NUMBER, --  AS LINE_NUM,\n" +
                    "INVOICE_DIST_SKU_NUM,--  AS DIST_SKU,\n" +
                    "DIST_ITEM_UOM,    --  AS DIST_UOM,\n" +
                    "DIST_ITEM_PACK,   --  AS DIST_PACK,\n" +
                    "ADJUSTED_COST,    --  AS LINE_AMOUNT,\n" +
                    "DIST_INTO_STOCK_COST,--  AS LINE_COST,\n" +
                    "STATUS,            --  AS STATUS,\n" +
                    "JD_CONV_FACTOR ,     --  AS JD_CONV_FACTOR,\n" +

                    "ITEM_ID,          --  AS ITEM_ID,\n" +
                    "CATEG1_ID,        --  AS CATEG1_ID,\n" +
                    "JD_CATEG1_ID,     --  AS JD_CATEG1_ID,\n" +
                    "MANUF_ID,         --  AS MANUF_ID,\n" +
                    "ACCOUNT_ID,       --  AS ACCOUNT_ID,\n" +
                    "SITE_ID,          --  AS SITE_ID,\n" +
                    "BUS_ENTITY_ID,    --  AS DISTR_ID,\n" +
                    "BRANCH,           --  AS REGION_NUM,\n" +
                    "REP_NUM,          --  AS DSR_NUM,\n" +
                    "REP_NAME,         --  AS DSR_NAME,\n" +
                    "JD_FLAG,          --  AS JD_FLAG   \n" +

                    "SYSDATE                     ,\n" +
                    DEFAULT_USER_NAME_TO_UPDATE+",\n" +
                    "NULL                        ,\n" +
                    "NULL                         \n" +
                 "FROM \n" +
                       tblDwInvoiceFactTemp + " tmp "   ;

               stmt = connection.prepareStatement(sql);
//             stmt.setString(1, userName);
               insertedRowCount = stmt.executeUpdate();
               stmt.close();
           }
           catch (SQLException ex) {
               String msg = "An error occurred at inserting of records into the table " +
                   tblDwInvoiceFact + ". " + ex.getMessage()+
                 "*** Execute the following request to get more information : " +  sql;
               throw new SQLException("^clw^"+msg+ "^clw^");
           }

           createIndex(connection, tblDwInvoiceFactTemp, "I6" , "invoice_fact_id" ) ;

           //DON'T continue if nothing was updated.  Possibly database problems lead to this condition
           //and we want it to be self correcting
           if(insertedRowCount > 0){
	           /// Updating the field 'EXP_DATE'
	          try {
	             sql =
	                 "UPDATE " + tblDwInvoiceFact + " dw \n" +
	                 "SET exp_date = " + lastUploadDate  +"\n" +
	                 "WHERE dw.invoice_fact_id IN  \n" +
	                 "(  \n" +
	                 "    SELECT  \n" +
	                 "        tmp.invoice_fact_id \n" +
	                 "    FROM " + tblDwInvoiceFactTemp +" tmp  \n" +
	                 ")";

	             stmt = connection.prepareStatement(sql);
	             updatedRowCount = stmt.executeUpdate();
	             stmt.close();
	           }
	           catch (SQLException ex) {
	             String msg = "An error occurred at the filling the field 'EXP_DATE' in table " +
	                 tblDwInvoiceFact + ". " + ex.getMessage()+
	                 "*** Execute the following request to get more information : " +  sql;
	             throw new SQLException("^clw^"+msg+ "^clw^");
	           }
	
	           // Updating DW_STORE_DIM to set last_upload_invoice_date
	           try {
	            sql =
	                "UPDATE " + tblDwStoreDim + " dw \n" +
	                "SET LAST_UPLOAD_INVOICE_DATE = TO_DATE('" + startUploadDate  +"', '" + UPLOAD_DATE_FORMAT +"') \n" +
	                "WHERE dw.store_dim_id = " + storeDimId  ;

	            stmt = connection.prepareStatement(sql);
	            stmt.executeUpdate();
	            stmt.close();
	          }
	          catch (SQLException ex) {
	            String msg = "An error occurred at the updating DW_STORE_DIM to set last_upload_invoice_date " +
	                ". " + ex.getMessage()+
	                 "*** Execute the following request to get more information : " +  sql;
	            throw new SQLException("^clw^"+msg+ "^clw^");
	          }
	         }
         }
         /// REPORT
         StringBuffer report = logReport(tblDwInvoiceFact, insertedRowCount, updatedRowCount, errors  );
         return report;

       }

 private  void initDWInvoiceFactTempDist (Connection connection, String dbClwNamespace, String dbDwNamespace,
                                               int pStoreId, int storeDimId, String lastUploadDate ) throws SQLException {
	String tblClwOrderItem = getFullTableName(dbClwNamespace, TBL_CLW_ORDER_ITEM);
    String tblClwItemMapping = getFullTableName(dbClwNamespace, TBL_CLW_ITEM_MAPPING);
    String tblClwInvoice = getFullTableName(dbClwNamespace, TBL_CLW_INVOICE_DIST);
    String tblClwInvoiceDetail = getFullTableName(dbClwNamespace, TBL_CLW_INVOICE_DIST_DETAIL);
    String tblDwInvoiceFactTemp = getFullTableName(dwTempUser, TBL_DW_INVOICE_FACT_TEMP, tempSuffix);

   try {
     String sql =
         "INSERT INTO " + tblDwInvoiceFactTemp + " " +
         "SELECT \n" +
             "NULL,                     --INVOICE_FACT_ID  \n" +
             "NULL,                     --MANUFACTURER_DIM_ID  \n" +
             "NULL,                     --DATE_DIM_ID          \n" +
             "NULL,                     --DISTRIBUTOR_DIM_ID   \n" +
             "NULL,                     --ITEM_DIM_ID          \n" +
             "NULL,                     --CATEGORY_DIM_ID      \n" +
             storeDimId +",             --STORE_DIM_ID         \n" +
             "NULL,                     --SALES_REP_DIM_ID     \n" +
             "NULL,                     --ACCOUNT_DIM_ID       \n" +
             "NULL,                     --SITE_DIM_ID          \n" +
             "NULL,                     --REGION_DIM_ID        \n" +

             "id.INVOICE_DIST_ID,     \n" +
             "BUS_ENTITY_ID,          \n" +
             "INVOICE_NUM,            \n" +
             "INVOICE_DATE,           \n" +
             "INVOICE_STATUS_CD,      \n" +
             "id.ADD_DATE,            \n" +
             "id.ADD_BY,              \n" +
             "STORE_ID,               \n" +
             "site_ID,                \n" +
             "ACCOUNT_ID,             \n" +

             "INVOICE_DIST_DETAIL_ID, \n" +
             "DIST_LINE_NUMBER,       \n" +
             "DIST_ITEM_SKU_NUM,      \n" +
             "DIST_ITEM_SHORT_DESC,   \n" +
             "DIST_ITEM_UOM,          \n" +
             "DIST_ITEM_PACK,         \n" +
             "DIST_ITEM_QUANTITY,     \n" +
             "ADJUSTED_COST,          \n" +
             "ITEM_RECEIVED_COST,     \n" +
             "ERP_PO_REF_LINE_NUM,     \n" +
             "INVOICE_DIST_SKU_NUM,    \n" +
             "DIST_INTO_STOCK_COST,    \n" +
             "'ACTIVE',                 --STATUS \n" +
             "1,                        --JD_CONV_FACTOR \n" +
             "NULL,                     --BRANCH \n" +
             "NULL,                     --REP_NUM  \n" +
             "NULL,                     --REP_NAME  \n" +
             "NULL,                     --MANUF_ID \n" +
             "NULL,                     --JD_FLAG  \n" +
             "NULL,                     --ITEM_ID  \n" +
             "ORDER_ID,                 --ORDER_ID  \n" +
             "ORDER_ITEM_ID,            --ORDER_ITEM_ID  \n" +

             "NULL,                     --CATEG1_ID \n" +
             "NULL,                     --JD_CATEG1_ID  \n" +
             "NULL                      --DISTR_ID \n" +
           "FROM    \n" +
             tblClwInvoice + " id,   \n" +
             tblClwInvoiceDetail + " idd   \n" +
           "WHERE   \n" +
             "id.INVOICE_DIST_ID = idd.INVOICE_DIST_ID  \n" +
             "AND id.INVOICE_STATUS_CD IN ('"+ RefCodeNames.INVOICE_STATUS_CD.INVOICE_HISTORY + "', \n" +
			 "'"+ RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE + "', \n" +
			 "'"+ RefCodeNames.INVOICE_STATUS_CD.PROCESS_ERP + "', \n" +
			 "'"+ RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED + "', \n" +
			 "'"+ RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_PROCESSED + "', \n" +
			 "'"+ RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_RELEASED + "') \n" +
             "AND id.ADD_DATE > " + lastUploadDate + "\n" +
             "AND id.STORE_ID = " + pStoreId ;

         PreparedStatement stmt = connection.prepareStatement(sql);
         int newRows = stmt.executeUpdate();
         stmt.close();
       }
       catch (SQLException ex) {
         String msg = "An error occurred at insertion of records into table " +
             tblDwInvoiceFactTemp + ". " + ex.getMessage();
         throw new SQLException("^clw^"+msg+ "^clw^");
       }

       /// Filling the fields 'ITEM_ID'
       ctaUpdate (connection, 0,  tblClwOrderItem , tblDwInvoiceFactTemp,
              "ITEM_ID", "ITEM_ID",
              "ORDER_ITEM_ID", "ORDER_ITEM_ID",null);

       /// Filling the fields 'ITEM_ID' from clw_item_mapping table in case it could not be filled
       // from clw_order_item table
       ctaUpdateNunUnique (connection, 0,  tblClwItemMapping , tblDwInvoiceFactTemp,
               "ITEM_ID", "ITEM_ID",
               "ITEM_NUM,BUS_ENTITY_ID", "DIST_ITEM_SKU_NUM,BUS_ENTITY_ID",null);

   }

   private  void initDWInvoiceFactTempCust (Connection connection, String dbClwNamespace, String dbDwNamespace,
                                                 int pStoreId, int storeDimId, String lastUploadDate) throws SQLException {

       String tblClwOrderItem = getFullTableName(dbClwNamespace, TBL_CLW_ORDER_ITEM);
       String tblClwBusEntity = getFullTableName(dbClwNamespace, TBL_CLW_BUS_ENTITY);
       String tblClwInvoice = getFullTableName(dbClwNamespace, TBL_CLW_INVOICE_CUST);
       String tblClwInvoiceDetail = getFullTableName(dbClwNamespace, TBL_CLW_INVOICE_CUST_DETAIL);
       String tblDwInvoiceFactTemp = getFullTableName(dwTempUser, TBL_DW_INVOICE_FACT_TEMP, tempSuffix);

       try {
       String sql =
           "INSERT INTO " + tblDwInvoiceFactTemp + " " +
           "SELECT \n" +
               "NULL,                     --INVOICE_FACT_ID  \n" +
               "NULL,                     --MANUFACTURER_DIM_ID  \n" +
               "NULL,                     --DATE_DIM_ID          \n" +
               "NULL,                     --DISTRIBUTOR_DIM_ID   \n" +
               "NULL,                     --ITEM_DIM_ID          \n" +
               "NULL,                     --CATEGORY_DIM_ID      \n" +
               storeDimId +",             --STORE_DIM_ID         \n" +
               "NULL,                     --SALES_REP_DIM_ID     \n" +
               "NULL,                     --ACCOUNT_DIM_ID       \n" +
               "NULL,                     --SITE_DIM_ID          \n" +
               "NULL,                     --REGION_DIM_ID        \n" +

               "id.INVOICE_CUST_ID,     \n" +
               "NULL,                   \n" +  // Dist_id --> bus_entity_id by ERP_NUM
               "INVOICE_NUM,            \n" +
               "INVOICE_DATE,           \n" +
               "INVOICE_STATUS_CD,      \n" +
               "id.ADD_DATE,            \n" +
               "id.ADD_BY,              \n" +
               "STORE_ID,               \n" +
               "site_ID,                \n" +
               "ACCOUNT_ID,             \n" +

               "INVOICE_CUST_DETAIL_ID, \n" +
               "LINE_NUMBER,       \n" +
               "ITEM_SKU_NUM,      \n" +
               "ITEM_SHORT_DESC,   \n" +
               "ITEM_UOM,          \n" +
               "ITEM_PACK,         \n" +
               "ITEM_QUANTITY,     \n" +
               "LINE_TOTAL,          \n" + //ADJUSTED_COST
               "CUST_CONTRACT_PRICE, \n" + //ITEM_RECEIVED_COST
               "NULL,     \n" +
               "NULL,    \n" +      //INVOICE_DIST_SKU_NUM
               "NULL,    \n" +      //DIST_INTO_STOCK_COST==> DIST_ITEM_COST from order_item

               "'ACTIVE',                 --STATUS \n" +
               "1,                        --JD_CONV_FACTOR \n" +
               "NULL,                     --BRANCH \n" +
               "NULL,                     --REP_NUM  \n" +
               "NULL,                     --REP_NAME  \n" +
               "NULL,                     --MANUF_ID \n" +
               "NULL,                     --JD_FLAG  \n" +
               "NULL,                     --ITEM_ID  \n" +
               "ORDER_ID,                 --ORDER_ID  \n" +
               "ORDER_ITEM_ID,            --ORDER_ITEM_ID  \n" +

               "NULL,                     --CATEG1_ID \n" +
               "NULL,                     --JD_CATEG1_ID  \n" +
               "NULL                      --DISTR_ID \n" +
             "FROM    \n" +
               tblClwInvoice + " id,   \n" +
               tblClwInvoiceDetail + " idd   \n" +
             "WHERE   \n" +
               "id.INVOICE_CUST_ID = idd.INVOICE_CUST_ID  \n" +
               "AND id.INVOICE_STATUS_CD IN \n"+
                     "('" + RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED + "', \n" +
                     "'" + RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED_ERROR + "', \n" +
                     "'" + RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED + "', \n" +
                     "'" + RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED_FAILED + "', \n" +
                     "'" + RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR + "', \n" +
                     "'" + RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED + "') \n" +
               "AND id.ADD_DATE > " + lastUploadDate + "\n" +
               "AND id.STORE_ID = " + pStoreId ;

           PreparedStatement stmt = connection.prepareStatement(sql);
           int newRows = stmt.executeUpdate();
           stmt.close();
         }
         catch (SQLException ex) {
           String msg = "An error occurred at insertion of records into table " +
               tblDwInvoiceFactTemp + ". " + ex.getMessage();
           throw new SQLException("^clw^"+msg+ "^clw^");
         }

         /// Filling the fields 'BUS_ENTITY_ID' ( Distributor_ID ), ITEM_ID, DIST_INTO_STOCK_COST
         String addCond = "  and BUS_ENTITY_TYPE_CD = 'DISTRIBUTOR' \n" +
                          "  and DIST_ERP_NUM = ERP_NUM ";
         ctaUpdate (connection, 0,  tblClwOrderItem +","+tblClwBusEntity , tblDwInvoiceFactTemp,
                    "BUS_ENTITY_ID, DIST_ITEM_COST, ITEM_ID", "BUS_ENTITY_ID,DIST_INTO_STOCK_COST,ITEM_ID",
                    "ORDER_ITEM_ID", "ORDER_ITEM_ID", addCond);

   }

   private  void ctaUpdate (Connection connection, int storeDimId,
                                  String tblDwSource, String tblDwDest,
                                  String sourceUpdateColumns, String destUpdateColumns,
                                  String sourceMatchColumns, String destMatchColumns,
                                  String addCond ) throws SQLException {
      String tblDwTemp = dwTempUser+"."+"DW_TEMP"+tempSuffix;
      String[] match = sourceMatchColumns.split(",");
      String[] dest  = destMatchColumns.split(",");
      boolean storeDimFl = (storeDimId!=0);
      boolean addCondFl = Utility.isSet(addCond);
     // Array match = matchColumns.
       try {
         boolean isExistTemp = checkDbTableOnExistence(connection,dwTempUser, "DW_TEMP"+tempSuffix);
         String sql = null;
         PreparedStatement stmt = null;

         if (isExistTemp){
           sql = "DROP TABLE " + tblDwTemp + " PURGE";
           stmt = connection.prepareStatement(sql);
           stmt.executeUpdate();
           stmt.close();
         }
         sql =
             "CREATE TABLE " +  tblDwTemp + " AS \n" +
             "SELECT  " + sourceUpdateColumns + ", " + sourceMatchColumns + " \n" +
             "    FROM \n" +
             tblDwSource + " \n" +
             "    WHERE (" + sourceMatchColumns + ") IN \n" +
             "          (SELECT " + destMatchColumns +" FROM " + tblDwDest + " tmp) \n" +
             (addCondFl ? addCond : "" ) +
             (storeDimFl ? "  AND STORE_DIM_ID = " + storeDimId :"");


         stmt = connection.prepareStatement(sql);
         stmt.executeUpdate();
         stmt.close();

         createIndex(connection, tblDwTemp, "I0" , sourceMatchColumns);
/*         for (int i = 0; i < match.length; i++) {
           createIndex(connection, tblDwTemp, "I0" + i, match[i]);
         }
 */
         String whereCl = "tmp."+ dest[0] + " = " + "dw." + match[0] + " \n"; ;
         String selectCols = "dw." + match[0] + " \n";
         for (int i = 1; i < match.length; i++) {
           whereCl += " AND tmp."+ dest[i] + " = " + "dw." + match[i] + " \n";
           selectCols += ", dw." + match[i] + " \n";
         }

         sql =
             "UPDATE " + tblDwDest + " tmp \n" +
             "SET \n" +
             "(  \n" +
                destUpdateColumns + " \n" +
             ")  \n" +
             "=  \n" +
             "(  \n" +
             "    SELECT  \n" + sourceUpdateColumns + " \n" +
             "    FROM  \n" +
             tblDwTemp + " dw  \n" +
             "    WHERE  \n"+ whereCl + " \n" +
             ")";

         stmt = connection.prepareStatement(sql);
         stmt.executeUpdate();
         stmt.close();


       }
       catch (SQLException ex) {
         String msg = "An error occurred at the filling the field '" +destUpdateColumns+ "' in table " +
             tblDwDest + ". " + ex.getMessage();
         throw new SQLException("^clw^"+msg+ "^clw^");
       }
     }
   /**
    * use this method to update a single field with possible duplicated data in the source table.
    * e.g. clw_item_mapping could have duplicated record for same distributor and sku caused by
    * differnt UOM or bad data
    */
   private  void ctaUpdateNunUnique (Connection connection, int storeDimId,
		   String tblDwSource, String tblDwDest,
		   String sourceUpdateColumn, String destUpdateColumn,
		   String sourceMatchColumns, String destMatchColumns,
		   String addCond ) throws SQLException {

	      String tblDwTemp = dwTempUser+"."+"DW_TEMP"+tempSuffix;
	   String[] match = sourceMatchColumns.split(",");
	   String[] dest  = destMatchColumns.split(",");
	   boolean storeDimFl = (storeDimId!=0);
	   boolean addCondFl = Utility.isSet(addCond);
//	   Array match = matchColumns.
	   try {
		   boolean isExistTemp = checkDbTableOnExistence(connection,dwTempUser, "DW_TEMP"+tempSuffix);
		   String sql = null;
		   PreparedStatement stmt = null;

		   if (isExistTemp){
			   sql = "DROP TABLE " + tblDwTemp + " PURGE";
			   stmt = connection.prepareStatement(sql);
			   stmt.executeUpdate();
			   stmt.close();
		   }
		   sql =
			   "CREATE TABLE " +  tblDwTemp + " AS \n" +
			   "SELECT  " + sourceUpdateColumn + ", " + sourceMatchColumns + " \n" +
			   "    FROM \n" +
			   tblDwSource + " \n" +
			   "    WHERE (" + sourceMatchColumns + ") IN \n" +
			   "          (SELECT " + destMatchColumns +" FROM " + tblDwDest + " tmp WHERE  " + destUpdateColumn + " IS NULL) \n" +
			   (addCondFl ? addCond : "" ) +
			   (storeDimFl ? "  AND STORE_DIM_ID = " + storeDimId :"");


		   stmt = connection.prepareStatement(sql);
		   stmt.executeUpdate();
		   stmt.close();

		   createIndex(connection, tblDwTemp, "I0" , sourceMatchColumns);
		   /*         for (int i = 0; i < match.length; i++) {
createIndex(connection, tblDwTemp, "I0" + i, match[i]);
}
		    */
		   String whereCl = "tmp."+ dest[0] + " = " + "dw." + match[0] + " \n"; ;
		   String selectCols = "dw." + match[0] + " \n";
		   for (int i = 1; i < match.length; i++) {
			   whereCl += " AND tmp."+ dest[i] + " = " + "dw." + match[i] + " \n";
			   selectCols += ", dw." + match[i] + " \n";
		   }

		   sql =
			   "UPDATE " + tblDwDest + " tmp \n" +
			   "SET \n" +
			   "(  \n" +
			   destUpdateColumn + " \n" +
			   ")  \n" +
			   "=  \n" +
			   "(  \n" +
			   "    SELECT  max(" + sourceUpdateColumn + ") \n" +
			   "    FROM  \n" +
			   tblDwTemp + " dw  \n" +
			   "    WHERE  \n"+ whereCl + " \n" +
			   ") \n" +
			   "WHERE  " + destUpdateColumn + " IS NULL";

		   stmt = connection.prepareStatement(sql);
		   stmt.executeUpdate();
		   stmt.close();


	   }
	   catch (SQLException ex) {
		   String msg = "An error occurred at the filling the field '" +destUpdateColumn+ "' in table " +
		   tblDwDest + ". " + ex.getMessage();
		   throw new SQLException("^clw^"+msg+ "^clw^");
	   }
   }
    private static String verifyNotSingleRow (Connection connection, String sql, String pMsg ) throws SQLException {
      try {
       Statement stmt = connection.createStatement();
//       ArrayList errors = new ArrayList();
       StringBuffer error = new StringBuffer();


        ResultSet rs = stmt.executeQuery(sql);
        //
        int k= 0;
//        if (rs.next()){
        IdVector vals = new IdVector();
        while (rs.next() && k<=5) {
            if (k < 3){
              vals.add(rs.getInt("ITEM_ID"));
            }
            k++;
        }
        if (vals.size()  > 0) {
          String extMess = (k > 3)? "... To pick all item ids use the request: " + sql : "";
          error.append(pMsg + " Item ids:" + vals.toString().replace("[","").replace("]","")+extMess);
        }
//          errors.add(error);
        return error.toString();
      }
      catch (SQLException ex) {
        String msg =pMsg + " An error occurred at the filling the Error Log. " + ex.getMessage();
        throw new SQLException("^clw^"+msg+"^clw^");
      }

    }

     private static List<StringBuffer> verifyConstraints(Connection connection, String[] sourceFields,
                                          String[] foreignKeys, String tblName) throws SQLException {
       /// Show log for rows with NULL FOREIGN KEYs
       ArrayList errors = new ArrayList();
       StringBuffer error = new StringBuffer();

       ArrayList sList = new ArrayList();
       ArrayList fList = new ArrayList();
       for (int i = 0; i < sourceFields.length; i++) {
         sList.add(sourceFields[i]);
       }
       for (int i = 0; i < foreignKeys.length; i++) {
         fList.add(foreignKeys[i]);
       }

       String whereStr = ((fList.toString()).replace("[", "")).replace("]", "");
       String whereStrPlus = whereStr.replaceAll(",", "+");
       String colStr = ((sList.toString()).replace("[", "")).replace("]", "");

       try {
        Statement stmt = connection.createStatement();
        String sql = "SELECT DISTINCT " + colStr + " \n" +
                     ", " + whereStr    + " \n" +
                     " FROM " + tblName + " \n" +
                     " WHERE \n" +
                     "( " + whereStrPlus + " ) is NULL \n";

         ResultSet rs = stmt.executeQuery(sql);
         //
         if (rs.next()){
           error.append("*** Execute the following request to get more information : " + sql);
           errors.add(error);
         }
/*          while (rs.next()) {
           ArrayList vals = new ArrayList();
           for (int i = 0; i < sourceFields.length; i++) {
             vals.add(rs.getInt(i+1));
           }
           error = new StringBuffer();
           error.append(colStr + " = " + vals.toString());
           errors.add(error);
          }
*/
          return errors;
       }
       catch (SQLException ex) {
         String msg = "An error occurred at the filling the Error Log for table " +
             tblName + ". " + ex.getMessage();
         throw new SQLException("^clw^"+msg+ "^clw^");
       }
     }

     private String checkMultipleCategPerItem(Connection conn) throws  Exception {
       String message = "Some item(s) has multiple categories.";
       String tblDwItemTemp = getFullTableName(dwTempUser, TBL_DW_ITEM_DIM_TEMP, tempSuffix);
       String sql =
           "SELECT ITEM_ID, num FROM ( \n" +
           "    SELECT ITEM_ID, COUNT(*) num \n" +
           "    FROM " + tblDwItemTemp + " tmp \n" +
           "    GROUP BY ITEM_ID \n" +
           ") WHERE num > 1 \n";
       String err = verifyNotSingleRow(conn, sql, message);
       if (err != null && err.length() > 0) {
//         throw new MultipleDataException("^clw^"+err+"^clw^");
         return "["+err+"]";
       }
       return null;
     }
     private String checkMultipleMetaPerItem(Connection conn, String type, String tblClwItemMeta) throws  Exception {
       String message = "Some item(s) has multiple "+type+" properties.";
       String tblDwItemTemp = getFullTableName(dwTempUser, TBL_DW_ITEM_DIM_TEMP, tempSuffix);
       String sql =
               "SELECT ITEM_ID, NAME_VALUE, num from ( \n" +
               "  SELECT ITEM_ID, NAME_VALUE, count(*) num \n" +
               "  FROM " + tblClwItemMeta + "   WHERE ITEM_id IN \n" +
               "    (SELECT ITEM_ID FROM " + tblDwItemTemp + ") \n" +
               "    AND NAME_VALUE IN ('" + type +"') \n" +
               " GROUP BY ITEM_ID, NAME_VALUE) \n" +
               "WHERE num > 1 \n";
       String err = verifyNotSingleRow(conn, sql, message);
       if (err != null && err.length() > 0) {
 //        throw new MultipleDataException("^clw^"+err+"^clw^");
         return "["+err+"]";
       }
       return null;
     }

     private static StringBuffer logReport (String tblName, int inserted, int updated, List<StringBuffer> errors) {

       StringBuffer report = new StringBuffer();
       int errCount = errors.size();
       report.append("Report(U-UPDATE,I-INSERT,E-ERROR)");
       report.append("[");

       report.append(tblName + "(I): " + inserted + "; ");
       report.append(tblName + "(U): " + updated + "; ");
       report.append(tblName + "(E): " + errCount + " ");
       if (errCount > 0) {
     //         for (int i = 0; i < errors.size(); i++) {
         report.append( (StringBuffer) errors.get(0));
     //         }
       }
       report.append("]");
       return report;
     }

     private static String getOrderStatusListAsString() {
       List list = new ArrayList();
       list.add("\n'"+RefCodeNames.ORDER_STATUS_CD.RECEIVED+"'");
       list.add("\n'"+RefCodeNames.ORDER_STATUS_CD.CANCELLED+"'");
       list.add("\n'"+RefCodeNames.ORDER_STATUS_CD.DUPLICATED+"'");
       list.add("\n'"+RefCodeNames.ORDER_STATUS_CD.REJECTED+"'");
       list.add("\n'"+RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW+"'");
       list.add("\n'"+RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW+"'");
       list.add("\n'"+RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED+"'");
       list.add("\n'"+RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED+"'");
       list.add("\n'"+RefCodeNames.ORDER_STATUS_CD.REFERENCE_ONLY+"'");

       String result = null;
       if (list != null) {
         Iterator it = list.iterator();
         int i = 0;
         result = "";
         while (it.hasNext()) {
           if (i != 0)
             result = result.concat(",");
             result = result.concat(  (String) it.next());
             i++;
         }
       }
       return result;
     }


      /**
        * Fills the DW_..._DIM tables in the "Data Warehouse".
        * Use the parameter 'storeSchemaName' to define the schema for 'CLW'-tables with data.
        * Use the parameter 'dbSchemaName' to define the schema for the tables from 'Data Warehouse'.
        */
       public StringBuffer fillDwAnyDimByStore(int pStoreId, int pLinkedStoreId, String dimName, String dbClwNamespace,
                                 String dbDwNamespace, String pDbLink, int pProcessId) throws RemoteException, Exception{

         final String tblDwStoreDim = getFullTableName(dbDwNamespace,DataWarehouse.TBL_DW_STORE_DIM);


         Connection connection = null;
         dbLink = pDbLink;
         tempSuffix = "_" + String.valueOf(pProcessId);
         dwTempUser = System.getProperty("dw.loader.tempUser");

         ArrayList storeIds = new ArrayList();
         storeIds.add(new Integer(pStoreId));
         String userName = "AnyDimLoader";
         StringBuffer report = null;

         try {
           connection = getReportConnection();
           int dwStoreDimId = getStoreDimId(connection, pStoreId, dbClwNamespace, tblDwStoreDim);
           if (dimName.equals(DataWarehouse.TBL_DW_CATEGORY_DIM)) {
             report = fillDwCategoryDimension(connection, userName, dbClwNamespace, dbDwNamespace, pStoreId, pLinkedStoreId, dwStoreDimId);
           }
           else if (dimName.equals(DataWarehouse.TBL_DW_ITEM_DIM)) {
             report = new StringBuffer();
             StringBuffer report1 = fillDwCategoryDimension(connection, userName, dbClwNamespace, dbDwNamespace, pStoreId, pLinkedStoreId, dwStoreDimId);
             StringBuffer report2 = fillDwItemDimension(connection, userName, dbClwNamespace, dbDwNamespace, pStoreId, pLinkedStoreId, dwStoreDimId);
             report.append(report1 + "\n");
             report.append(report2 + "\n");
           }
           else if (dimName.equals(DataWarehouse.TBL_DW_SITE_DIM)) {
             report = fillDwSiteDimension(connection, userName, dbClwNamespace, dbDwNamespace, pStoreId, dwStoreDimId);
           }
           else if (dimName.equals(DataWarehouse.TBL_DW_ACCOUNT_DIM)) {
             report = fillDwAccountDimension(connection, userName, dbClwNamespace, dbDwNamespace, pStoreId,  pLinkedStoreId, dwStoreDimId);
           }
          else if (dimName.equals(DataWarehouse.TBL_DW_MANUFACTURER_DIM)) {
//             fillDwManufacturerDimension(connection, "Loader", dbClwNamespace, dbDwNamespace, pStoreId,  pLinkedStoreId, dwStoreDimId);
              updateDimManufacturer(connection, dbClwNamespace, dbDwNamespace, storeIds, pLinkedStoreId, userName, dwStoreDimId);
          }
          else if (dimName.equals(DataWarehouse.TBL_DW_DISTRIBUTOR_DIM)) {
//             fillDwDistributorDimension(connection, "AnyDimLoader", dbClwNamespace, dbDwNamespace, pStoreId, pLinkedStoreId, dwStoreDimId);
                updateDimDist(connection, dbClwNamespace, dbDwNamespace, storeIds, pLinkedStoreId, userName, dwStoreDimId);
               fillDwItemDistributorTable(connection, userName, dbClwNamespace, dbDwNamespace, dwStoreDimId );
           }
           else if (dimName.equals(DataWarehouse.TBL_DW_DATE_DIM)) {
//             fillDwDateDimension(connection, dbClwNamespace, dbDwNamespace, pStoreId);
           }
           else if (dimName.equals(DataWarehouse.TBL_DW_REGION_DIM)) {
// todo            fillDwRegionDimension(connection, userName, dbClwNamespace, dbDwNamespace, pStoreId, dwStoreDimId);
           }
           else if (dimName.equals(DataWarehouse.TBL_DW_SALES_REP_DIM)) {
// todo            fillDwSalesRepDimension(connection, userName, dbClwNamespace, dbDwNamespace, pStoreId, dwStoreDimId);
           }
           return report;
         }
         catch (SQLException ex) {
           throw new RemoteException(ex.getMessage(),ex);
         }
         catch (NamingException ex) {
           throw new RemoteException(ex.getMessage(),ex);
         }
         catch (DataNotFoundException ex) {
//           e.printStackTrace();
           throw new DataNotFoundException(ex.getMessage());
         }
         catch (MultipleDataException ex) {
//           e.printStackTrace();
           throw new MultipleDataException(ex.getMessage());
         }
         catch (Exception ex) {
//           e.printStackTrace();
           throw new RemoteException(ex.getMessage(),ex);
         }
         finally {
           closeConnection(connection);
         }
       }
     private  String genErrMess(List errors, String tblDw, String tblDwTemp){
       String errMess = " :: <ERROR> It is impossible to update "+tblDw+". NULL foreign key occured in "+tblDwTemp+". \n";
       int MAX_ERR = 25;
       Iterator iter = errors.iterator();
       int ii = 0;
       while (iter.hasNext() && ii < MAX_ERR ) {
         ii++;
         StringBuffer err = (StringBuffer) iter.next();
         errMess += err.toString()+ "\n";
       }
       return errMess;
     }


}

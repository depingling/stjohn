
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        UploadSkuDataAccess
 * Description:  This class is used to build access methods to the CLW_UPLOAD_SKU table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.UploadSkuData;
import com.cleanwise.service.api.value.UploadSkuDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>UploadSkuDataAccess</code>
 */
public class UploadSkuDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(UploadSkuDataAccess.class.getName());

    /** <code>CLW_UPLOAD_SKU</code> table name */
	/* Primary key: UPLOAD_SKU_ID */
	
    public static final String CLW_UPLOAD_SKU = "CLW_UPLOAD_SKU";
    
    /** <code>UPLOAD_SKU_ID</code> UPLOAD_SKU_ID column of table CLW_UPLOAD_SKU */
    public static final String UPLOAD_SKU_ID = "UPLOAD_SKU_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_UPLOAD_SKU */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>UPLOAD_ID</code> UPLOAD_ID column of table CLW_UPLOAD_SKU */
    public static final String UPLOAD_ID = "UPLOAD_ID";
    /** <code>ROW_NUM</code> ROW_NUM column of table CLW_UPLOAD_SKU */
    public static final String ROW_NUM = "ROW_NUM";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_UPLOAD_SKU */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_UPLOAD_SKU */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>CATEGORY</code> CATEGORY column of table CLW_UPLOAD_SKU */
    public static final String CATEGORY = "CATEGORY";
    /** <code>SKU_SIZE</code> SKU_SIZE column of table CLW_UPLOAD_SKU */
    public static final String SKU_SIZE = "SKU_SIZE";
    /** <code>SKU_PACK</code> SKU_PACK column of table CLW_UPLOAD_SKU */
    public static final String SKU_PACK = "SKU_PACK";
    /** <code>SKU_UOM</code> SKU_UOM column of table CLW_UPLOAD_SKU */
    public static final String SKU_UOM = "SKU_UOM";
    /** <code>SKU_COLOR</code> SKU_COLOR column of table CLW_UPLOAD_SKU */
    public static final String SKU_COLOR = "SKU_COLOR";
    /** <code>MANUF_ID</code> MANUF_ID column of table CLW_UPLOAD_SKU */
    public static final String MANUF_ID = "MANUF_ID";
    /** <code>MANUF_NAME</code> MANUF_NAME column of table CLW_UPLOAD_SKU */
    public static final String MANUF_NAME = "MANUF_NAME";
    /** <code>MANUF_SKU</code> MANUF_SKU column of table CLW_UPLOAD_SKU */
    public static final String MANUF_SKU = "MANUF_SKU";
    /** <code>MANUF_PACK</code> MANUF_PACK column of table CLW_UPLOAD_SKU */
    public static final String MANUF_PACK = "MANUF_PACK";
    /** <code>MANUF_UOM</code> MANUF_UOM column of table CLW_UPLOAD_SKU */
    public static final String MANUF_UOM = "MANUF_UOM";
    /** <code>DIST_ID</code> DIST_ID column of table CLW_UPLOAD_SKU */
    public static final String DIST_ID = "DIST_ID";
    /** <code>DIST_NAME</code> DIST_NAME column of table CLW_UPLOAD_SKU */
    public static final String DIST_NAME = "DIST_NAME";
    /** <code>DIST_SKU</code> DIST_SKU column of table CLW_UPLOAD_SKU */
    public static final String DIST_SKU = "DIST_SKU";
    /** <code>DIST_UOM</code> DIST_UOM column of table CLW_UPLOAD_SKU */
    public static final String DIST_UOM = "DIST_UOM";
    /** <code>DIST_PACK</code> DIST_PACK column of table CLW_UPLOAD_SKU */
    public static final String DIST_PACK = "DIST_PACK";
    /** <code>LIST_PRICE</code> LIST_PRICE column of table CLW_UPLOAD_SKU */
    public static final String LIST_PRICE = "LIST_PRICE";
    /** <code>DIST_COST</code> DIST_COST column of table CLW_UPLOAD_SKU */
    public static final String DIST_COST = "DIST_COST";
    /** <code>SPL</code> SPL column of table CLW_UPLOAD_SKU */
    public static final String SPL = "SPL";
    /** <code>CATALOG_PRICE</code> CATALOG_PRICE column of table CLW_UPLOAD_SKU */
    public static final String CATALOG_PRICE = "CATALOG_PRICE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_UPLOAD_SKU */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_UPLOAD_SKU */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_UPLOAD_SKU */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_UPLOAD_SKU */
    public static final String MOD_BY = "MOD_BY";
    /** <code>SKU_NUM</code> SKU_NUM column of table CLW_UPLOAD_SKU */
    public static final String SKU_NUM = "SKU_NUM";
    /** <code>UNSPSC_CODE</code> UNSPSC_CODE column of table CLW_UPLOAD_SKU */
    public static final String UNSPSC_CODE = "UNSPSC_CODE";
    /** <code>OTHER_DESC</code> OTHER_DESC column of table CLW_UPLOAD_SKU */
    public static final String OTHER_DESC = "OTHER_DESC";
    /** <code>NSN</code> NSN column of table CLW_UPLOAD_SKU */
    public static final String NSN = "NSN";
    /** <code>MFCP</code> MFCP column of table CLW_UPLOAD_SKU */
    public static final String MFCP = "MFCP";
    /** <code>PSN</code> PSN column of table CLW_UPLOAD_SKU */
    public static final String PSN = "PSN";
    /** <code>BASE_COST</code> BASE_COST column of table CLW_UPLOAD_SKU */
    public static final String BASE_COST = "BASE_COST";
    /** <code>GEN_MANUF_ID</code> GEN_MANUF_ID column of table CLW_UPLOAD_SKU */
    public static final String GEN_MANUF_ID = "GEN_MANUF_ID";
    /** <code>GEN_MANUF_NAME</code> GEN_MANUF_NAME column of table CLW_UPLOAD_SKU */
    public static final String GEN_MANUF_NAME = "GEN_MANUF_NAME";
    /** <code>GEN_MANUF_SKU</code> GEN_MANUF_SKU column of table CLW_UPLOAD_SKU */
    public static final String GEN_MANUF_SKU = "GEN_MANUF_SKU";
    /** <code>DIST_UOM_MULT</code> DIST_UOM_MULT column of table CLW_UPLOAD_SKU */
    public static final String DIST_UOM_MULT = "DIST_UOM_MULT";
    /** <code>TAX_EXEMPT</code> TAX_EXEMPT column of table CLW_UPLOAD_SKU */
    public static final String TAX_EXEMPT = "TAX_EXEMPT";
    /** <code>SPECIAL_PERMISSION</code> SPECIAL_PERMISSION column of table CLW_UPLOAD_SKU */
    public static final String SPECIAL_PERMISSION = "SPECIAL_PERMISSION";
    /** <code>IMAGE_URL</code> IMAGE_URL column of table CLW_UPLOAD_SKU */
    public static final String IMAGE_URL = "IMAGE_URL";
    /** <code>MSDS_URL</code> MSDS_URL column of table CLW_UPLOAD_SKU */
    public static final String MSDS_URL = "MSDS_URL";
    /** <code>DED_URL</code> DED_URL column of table CLW_UPLOAD_SKU */
    public static final String DED_URL = "DED_URL";
    /** <code>PROD_SPEC_URL</code> PROD_SPEC_URL column of table CLW_UPLOAD_SKU */
    public static final String PROD_SPEC_URL = "PROD_SPEC_URL";
    /** <code>GREEN_CERTIFIED</code> GREEN_CERTIFIED column of table CLW_UPLOAD_SKU */
    public static final String GREEN_CERTIFIED = "GREEN_CERTIFIED";
    /** <code>CUSTOMER_SKU_NUM</code> CUSTOMER_SKU_NUM column of table CLW_UPLOAD_SKU */
    public static final String CUSTOMER_SKU_NUM = "CUSTOMER_SKU_NUM";
    /** <code>SHIP_WEIGHT</code> SHIP_WEIGHT column of table CLW_UPLOAD_SKU */
    public static final String SHIP_WEIGHT = "SHIP_WEIGHT";
    /** <code>WEIGHT_UNIT</code> WEIGHT_UNIT column of table CLW_UPLOAD_SKU */
    public static final String WEIGHT_UNIT = "WEIGHT_UNIT";
    /** <code>CUSTOMER_DESC</code> CUSTOMER_DESC column of table CLW_UPLOAD_SKU */
    public static final String CUSTOMER_DESC = "CUSTOMER_DESC";
    /** <code>ADMIN_CATEGORY</code> ADMIN_CATEGORY column of table CLW_UPLOAD_SKU */
    public static final String ADMIN_CATEGORY = "ADMIN_CATEGORY";
    /** <code>THUMBNAIL_URL</code> THUMBNAIL_URL column of table CLW_UPLOAD_SKU */
    public static final String THUMBNAIL_URL = "THUMBNAIL_URL";
    /** <code>SERVICE_FEE_CODE</code> SERVICE_FEE_CODE column of table CLW_UPLOAD_SKU */
    public static final String SERVICE_FEE_CODE = "SERVICE_FEE_CODE";

    /**
     * Constructor.
     */
    public UploadSkuDataAccess()
    {
    }

    /**
     * Gets a UploadSkuData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pUploadSkuId The key requested.
     * @return new UploadSkuData()
     * @throws            SQLException
     */
    public static UploadSkuData select(Connection pCon, int pUploadSkuId)
        throws SQLException, DataNotFoundException {
        UploadSkuData x=null;
        String sql="SELECT UPLOAD_SKU_ID,ITEM_ID,UPLOAD_ID,ROW_NUM,SHORT_DESC,LONG_DESC,CATEGORY,SKU_SIZE,SKU_PACK,SKU_UOM,SKU_COLOR,MANUF_ID,MANUF_NAME,MANUF_SKU,MANUF_PACK,MANUF_UOM,DIST_ID,DIST_NAME,DIST_SKU,DIST_UOM,DIST_PACK,LIST_PRICE,DIST_COST,SPL,CATALOG_PRICE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SKU_NUM,UNSPSC_CODE,OTHER_DESC,NSN,MFCP,PSN,BASE_COST,GEN_MANUF_ID,GEN_MANUF_NAME,GEN_MANUF_SKU,DIST_UOM_MULT,TAX_EXEMPT,SPECIAL_PERMISSION,IMAGE_URL,MSDS_URL,DED_URL,PROD_SPEC_URL,GREEN_CERTIFIED,CUSTOMER_SKU_NUM,SHIP_WEIGHT,WEIGHT_UNIT,CUSTOMER_DESC,ADMIN_CATEGORY,THUMBNAIL_URL,SERVICE_FEE_CODE FROM CLW_UPLOAD_SKU WHERE UPLOAD_SKU_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pUploadSkuId=" + pUploadSkuId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pUploadSkuId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=UploadSkuData.createValue();
            
            x.setUploadSkuId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setUploadId(rs.getInt(3));
            x.setRowNum(rs.getInt(4));
            x.setShortDesc(rs.getString(5));
            x.setLongDesc(rs.getString(6));
            x.setCategory(rs.getString(7));
            x.setSkuSize(rs.getString(8));
            x.setSkuPack(rs.getString(9));
            x.setSkuUom(rs.getString(10));
            x.setSkuColor(rs.getString(11));
            x.setManufId(rs.getInt(12));
            x.setManufName(rs.getString(13));
            x.setManufSku(rs.getString(14));
            x.setManufPack(rs.getString(15));
            x.setManufUom(rs.getString(16));
            x.setDistId(rs.getInt(17));
            x.setDistName(rs.getString(18));
            x.setDistSku(rs.getString(19));
            x.setDistUom(rs.getString(20));
            x.setDistPack(rs.getString(21));
            x.setListPrice(rs.getString(22));
            x.setDistCost(rs.getString(23));
            x.setSpl(rs.getString(24));
            x.setCatalogPrice(rs.getString(25));
            x.setAddDate(rs.getTimestamp(26));
            x.setAddBy(rs.getString(27));
            x.setModDate(rs.getTimestamp(28));
            x.setModBy(rs.getString(29));
            x.setSkuNum(rs.getString(30));
            x.setUnspscCode(rs.getString(31));
            x.setOtherDesc(rs.getString(32));
            x.setNsn(rs.getString(33));
            x.setMfcp(rs.getString(34));
            x.setPsn(rs.getString(35));
            x.setBaseCost(rs.getString(36));
            x.setGenManufId(rs.getInt(37));
            x.setGenManufName(rs.getString(38));
            x.setGenManufSku(rs.getString(39));
            x.setDistUomMult(rs.getString(40));
            x.setTaxExempt(rs.getString(41));
            x.setSpecialPermission(rs.getString(42));
            x.setImageUrl(rs.getString(43));
            x.setMsdsUrl(rs.getString(44));
            x.setDedUrl(rs.getString(45));
            x.setProdSpecUrl(rs.getString(46));
            x.setGreenCertified(rs.getString(47));
            x.setCustomerSkuNum(rs.getString(48));
            x.setShipWeight(rs.getString(49));
            x.setWeightUnit(rs.getString(50));
            x.setCustomerDesc(rs.getString(51));
            x.setAdminCategory(rs.getString(52));
            x.setThumbnailUrl(rs.getString(53));
            x.setServiceFeeCode(rs.getString(54));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("UPLOAD_SKU_ID :" + pUploadSkuId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a UploadSkuDataVector object that consists
     * of UploadSkuData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new UploadSkuDataVector()
     * @throws            SQLException
     */
    public static UploadSkuDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a UploadSkuData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_UPLOAD_SKU.UPLOAD_SKU_ID,CLW_UPLOAD_SKU.ITEM_ID,CLW_UPLOAD_SKU.UPLOAD_ID,CLW_UPLOAD_SKU.ROW_NUM,CLW_UPLOAD_SKU.SHORT_DESC,CLW_UPLOAD_SKU.LONG_DESC,CLW_UPLOAD_SKU.CATEGORY,CLW_UPLOAD_SKU.SKU_SIZE,CLW_UPLOAD_SKU.SKU_PACK,CLW_UPLOAD_SKU.SKU_UOM,CLW_UPLOAD_SKU.SKU_COLOR,CLW_UPLOAD_SKU.MANUF_ID,CLW_UPLOAD_SKU.MANUF_NAME,CLW_UPLOAD_SKU.MANUF_SKU,CLW_UPLOAD_SKU.MANUF_PACK,CLW_UPLOAD_SKU.MANUF_UOM,CLW_UPLOAD_SKU.DIST_ID,CLW_UPLOAD_SKU.DIST_NAME,CLW_UPLOAD_SKU.DIST_SKU,CLW_UPLOAD_SKU.DIST_UOM,CLW_UPLOAD_SKU.DIST_PACK,CLW_UPLOAD_SKU.LIST_PRICE,CLW_UPLOAD_SKU.DIST_COST,CLW_UPLOAD_SKU.SPL,CLW_UPLOAD_SKU.CATALOG_PRICE,CLW_UPLOAD_SKU.ADD_DATE,CLW_UPLOAD_SKU.ADD_BY,CLW_UPLOAD_SKU.MOD_DATE,CLW_UPLOAD_SKU.MOD_BY,CLW_UPLOAD_SKU.SKU_NUM,CLW_UPLOAD_SKU.UNSPSC_CODE,CLW_UPLOAD_SKU.OTHER_DESC,CLW_UPLOAD_SKU.NSN,CLW_UPLOAD_SKU.MFCP,CLW_UPLOAD_SKU.PSN,CLW_UPLOAD_SKU.BASE_COST,CLW_UPLOAD_SKU.GEN_MANUF_ID,CLW_UPLOAD_SKU.GEN_MANUF_NAME,CLW_UPLOAD_SKU.GEN_MANUF_SKU,CLW_UPLOAD_SKU.DIST_UOM_MULT,CLW_UPLOAD_SKU.TAX_EXEMPT,CLW_UPLOAD_SKU.SPECIAL_PERMISSION,CLW_UPLOAD_SKU.IMAGE_URL,CLW_UPLOAD_SKU.MSDS_URL,CLW_UPLOAD_SKU.DED_URL,CLW_UPLOAD_SKU.PROD_SPEC_URL,CLW_UPLOAD_SKU.GREEN_CERTIFIED,CLW_UPLOAD_SKU.CUSTOMER_SKU_NUM,CLW_UPLOAD_SKU.SHIP_WEIGHT,CLW_UPLOAD_SKU.WEIGHT_UNIT,CLW_UPLOAD_SKU.CUSTOMER_DESC,CLW_UPLOAD_SKU.ADMIN_CATEGORY,CLW_UPLOAD_SKU.THUMBNAIL_URL,CLW_UPLOAD_SKU.SERVICE_FEE_CODE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated UploadSkuData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs) throws SQLException{
         return parseResultSet(rs,0);
    }

    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@param int the offset to use which is useful when using 1 query to populate multiple objects
    *@returns a populated UploadSkuData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         UploadSkuData x = UploadSkuData.createValue();
         
         x.setUploadSkuId(rs.getInt(1+offset));
         x.setItemId(rs.getInt(2+offset));
         x.setUploadId(rs.getInt(3+offset));
         x.setRowNum(rs.getInt(4+offset));
         x.setShortDesc(rs.getString(5+offset));
         x.setLongDesc(rs.getString(6+offset));
         x.setCategory(rs.getString(7+offset));
         x.setSkuSize(rs.getString(8+offset));
         x.setSkuPack(rs.getString(9+offset));
         x.setSkuUom(rs.getString(10+offset));
         x.setSkuColor(rs.getString(11+offset));
         x.setManufId(rs.getInt(12+offset));
         x.setManufName(rs.getString(13+offset));
         x.setManufSku(rs.getString(14+offset));
         x.setManufPack(rs.getString(15+offset));
         x.setManufUom(rs.getString(16+offset));
         x.setDistId(rs.getInt(17+offset));
         x.setDistName(rs.getString(18+offset));
         x.setDistSku(rs.getString(19+offset));
         x.setDistUom(rs.getString(20+offset));
         x.setDistPack(rs.getString(21+offset));
         x.setListPrice(rs.getString(22+offset));
         x.setDistCost(rs.getString(23+offset));
         x.setSpl(rs.getString(24+offset));
         x.setCatalogPrice(rs.getString(25+offset));
         x.setAddDate(rs.getTimestamp(26+offset));
         x.setAddBy(rs.getString(27+offset));
         x.setModDate(rs.getTimestamp(28+offset));
         x.setModBy(rs.getString(29+offset));
         x.setSkuNum(rs.getString(30+offset));
         x.setUnspscCode(rs.getString(31+offset));
         x.setOtherDesc(rs.getString(32+offset));
         x.setNsn(rs.getString(33+offset));
         x.setMfcp(rs.getString(34+offset));
         x.setPsn(rs.getString(35+offset));
         x.setBaseCost(rs.getString(36+offset));
         x.setGenManufId(rs.getInt(37+offset));
         x.setGenManufName(rs.getString(38+offset));
         x.setGenManufSku(rs.getString(39+offset));
         x.setDistUomMult(rs.getString(40+offset));
         x.setTaxExempt(rs.getString(41+offset));
         x.setSpecialPermission(rs.getString(42+offset));
         x.setImageUrl(rs.getString(43+offset));
         x.setMsdsUrl(rs.getString(44+offset));
         x.setDedUrl(rs.getString(45+offset));
         x.setProdSpecUrl(rs.getString(46+offset));
         x.setGreenCertified(rs.getString(47+offset));
         x.setCustomerSkuNum(rs.getString(48+offset));
         x.setShipWeight(rs.getString(49+offset));
         x.setWeightUnit(rs.getString(50+offset));
         x.setCustomerDesc(rs.getString(51+offset));
         x.setAdminCategory(rs.getString(52+offset));
         x.setThumbnailUrl(rs.getString(53+offset));
         x.setServiceFeeCode(rs.getString(54+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the UploadSkuData Object represents.
    */
    public int getColumnCount(){
        return 54;
    }

    /**
     * Gets a UploadSkuDataVector object that consists
     * of UploadSkuData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new UploadSkuDataVector()
     * @throws            SQLException
     */
    public static UploadSkuDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT UPLOAD_SKU_ID,ITEM_ID,UPLOAD_ID,ROW_NUM,SHORT_DESC,LONG_DESC,CATEGORY,SKU_SIZE,SKU_PACK,SKU_UOM,SKU_COLOR,MANUF_ID,MANUF_NAME,MANUF_SKU,MANUF_PACK,MANUF_UOM,DIST_ID,DIST_NAME,DIST_SKU,DIST_UOM,DIST_PACK,LIST_PRICE,DIST_COST,SPL,CATALOG_PRICE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SKU_NUM,UNSPSC_CODE,OTHER_DESC,NSN,MFCP,PSN,BASE_COST,GEN_MANUF_ID,GEN_MANUF_NAME,GEN_MANUF_SKU,DIST_UOM_MULT,TAX_EXEMPT,SPECIAL_PERMISSION,IMAGE_URL,MSDS_URL,DED_URL,PROD_SPEC_URL,GREEN_CERTIFIED,CUSTOMER_SKU_NUM,SHIP_WEIGHT,WEIGHT_UNIT,CUSTOMER_DESC,ADMIN_CATEGORY,THUMBNAIL_URL,SERVICE_FEE_CODE FROM CLW_UPLOAD_SKU");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_UPLOAD_SKU.UPLOAD_SKU_ID,CLW_UPLOAD_SKU.ITEM_ID,CLW_UPLOAD_SKU.UPLOAD_ID,CLW_UPLOAD_SKU.ROW_NUM,CLW_UPLOAD_SKU.SHORT_DESC,CLW_UPLOAD_SKU.LONG_DESC,CLW_UPLOAD_SKU.CATEGORY,CLW_UPLOAD_SKU.SKU_SIZE,CLW_UPLOAD_SKU.SKU_PACK,CLW_UPLOAD_SKU.SKU_UOM,CLW_UPLOAD_SKU.SKU_COLOR,CLW_UPLOAD_SKU.MANUF_ID,CLW_UPLOAD_SKU.MANUF_NAME,CLW_UPLOAD_SKU.MANUF_SKU,CLW_UPLOAD_SKU.MANUF_PACK,CLW_UPLOAD_SKU.MANUF_UOM,CLW_UPLOAD_SKU.DIST_ID,CLW_UPLOAD_SKU.DIST_NAME,CLW_UPLOAD_SKU.DIST_SKU,CLW_UPLOAD_SKU.DIST_UOM,CLW_UPLOAD_SKU.DIST_PACK,CLW_UPLOAD_SKU.LIST_PRICE,CLW_UPLOAD_SKU.DIST_COST,CLW_UPLOAD_SKU.SPL,CLW_UPLOAD_SKU.CATALOG_PRICE,CLW_UPLOAD_SKU.ADD_DATE,CLW_UPLOAD_SKU.ADD_BY,CLW_UPLOAD_SKU.MOD_DATE,CLW_UPLOAD_SKU.MOD_BY,CLW_UPLOAD_SKU.SKU_NUM,CLW_UPLOAD_SKU.UNSPSC_CODE,CLW_UPLOAD_SKU.OTHER_DESC,CLW_UPLOAD_SKU.NSN,CLW_UPLOAD_SKU.MFCP,CLW_UPLOAD_SKU.PSN,CLW_UPLOAD_SKU.BASE_COST,CLW_UPLOAD_SKU.GEN_MANUF_ID,CLW_UPLOAD_SKU.GEN_MANUF_NAME,CLW_UPLOAD_SKU.GEN_MANUF_SKU,CLW_UPLOAD_SKU.DIST_UOM_MULT,CLW_UPLOAD_SKU.TAX_EXEMPT,CLW_UPLOAD_SKU.SPECIAL_PERMISSION,CLW_UPLOAD_SKU.IMAGE_URL,CLW_UPLOAD_SKU.MSDS_URL,CLW_UPLOAD_SKU.DED_URL,CLW_UPLOAD_SKU.PROD_SPEC_URL,CLW_UPLOAD_SKU.GREEN_CERTIFIED,CLW_UPLOAD_SKU.CUSTOMER_SKU_NUM,CLW_UPLOAD_SKU.SHIP_WEIGHT,CLW_UPLOAD_SKU.WEIGHT_UNIT,CLW_UPLOAD_SKU.CUSTOMER_DESC,CLW_UPLOAD_SKU.ADMIN_CATEGORY,CLW_UPLOAD_SKU.THUMBNAIL_URL,CLW_UPLOAD_SKU.SERVICE_FEE_CODE FROM CLW_UPLOAD_SKU");
                where = pCriteria.getSqlClause("CLW_UPLOAD_SKU");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_UPLOAD_SKU.equals(otherTable)){
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
				}
                }
        }

        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
              stmt.setMaxRows(pMaxRows);
        }
        ResultSet rs=stmt.executeQuery(sql);
        UploadSkuDataVector v = new UploadSkuDataVector();
        while (rs.next()) {
            UploadSkuData x = UploadSkuData.createValue();
            
            x.setUploadSkuId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setUploadId(rs.getInt(3));
            x.setRowNum(rs.getInt(4));
            x.setShortDesc(rs.getString(5));
            x.setLongDesc(rs.getString(6));
            x.setCategory(rs.getString(7));
            x.setSkuSize(rs.getString(8));
            x.setSkuPack(rs.getString(9));
            x.setSkuUom(rs.getString(10));
            x.setSkuColor(rs.getString(11));
            x.setManufId(rs.getInt(12));
            x.setManufName(rs.getString(13));
            x.setManufSku(rs.getString(14));
            x.setManufPack(rs.getString(15));
            x.setManufUom(rs.getString(16));
            x.setDistId(rs.getInt(17));
            x.setDistName(rs.getString(18));
            x.setDistSku(rs.getString(19));
            x.setDistUom(rs.getString(20));
            x.setDistPack(rs.getString(21));
            x.setListPrice(rs.getString(22));
            x.setDistCost(rs.getString(23));
            x.setSpl(rs.getString(24));
            x.setCatalogPrice(rs.getString(25));
            x.setAddDate(rs.getTimestamp(26));
            x.setAddBy(rs.getString(27));
            x.setModDate(rs.getTimestamp(28));
            x.setModBy(rs.getString(29));
            x.setSkuNum(rs.getString(30));
            x.setUnspscCode(rs.getString(31));
            x.setOtherDesc(rs.getString(32));
            x.setNsn(rs.getString(33));
            x.setMfcp(rs.getString(34));
            x.setPsn(rs.getString(35));
            x.setBaseCost(rs.getString(36));
            x.setGenManufId(rs.getInt(37));
            x.setGenManufName(rs.getString(38));
            x.setGenManufSku(rs.getString(39));
            x.setDistUomMult(rs.getString(40));
            x.setTaxExempt(rs.getString(41));
            x.setSpecialPermission(rs.getString(42));
            x.setImageUrl(rs.getString(43));
            x.setMsdsUrl(rs.getString(44));
            x.setDedUrl(rs.getString(45));
            x.setProdSpecUrl(rs.getString(46));
            x.setGreenCertified(rs.getString(47));
            x.setCustomerSkuNum(rs.getString(48));
            x.setShipWeight(rs.getString(49));
            x.setWeightUnit(rs.getString(50));
            x.setCustomerDesc(rs.getString(51));
            x.setAdminCategory(rs.getString(52));
            x.setThumbnailUrl(rs.getString(53));
            x.setServiceFeeCode(rs.getString(54));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a UploadSkuDataVector object that consists
     * of UploadSkuData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for UploadSkuData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new UploadSkuDataVector()
     * @throws            SQLException
     */
    public static UploadSkuDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        UploadSkuDataVector v = new UploadSkuDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT UPLOAD_SKU_ID,ITEM_ID,UPLOAD_ID,ROW_NUM,SHORT_DESC,LONG_DESC,CATEGORY,SKU_SIZE,SKU_PACK,SKU_UOM,SKU_COLOR,MANUF_ID,MANUF_NAME,MANUF_SKU,MANUF_PACK,MANUF_UOM,DIST_ID,DIST_NAME,DIST_SKU,DIST_UOM,DIST_PACK,LIST_PRICE,DIST_COST,SPL,CATALOG_PRICE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SKU_NUM,UNSPSC_CODE,OTHER_DESC,NSN,MFCP,PSN,BASE_COST,GEN_MANUF_ID,GEN_MANUF_NAME,GEN_MANUF_SKU,DIST_UOM_MULT,TAX_EXEMPT,SPECIAL_PERMISSION,IMAGE_URL,MSDS_URL,DED_URL,PROD_SPEC_URL,GREEN_CERTIFIED,CUSTOMER_SKU_NUM,SHIP_WEIGHT,WEIGHT_UNIT,CUSTOMER_DESC,ADMIN_CATEGORY,THUMBNAIL_URL,SERVICE_FEE_CODE FROM CLW_UPLOAD_SKU WHERE UPLOAD_SKU_ID IN (");

        if ( pIdVector.size() > 0 ) {
            sqlBuf.append(pIdVector.get(0).toString());
            int vecsize = pIdVector.size();
            for ( int idx = 1; idx < vecsize; idx++ ) {
                sqlBuf.append("," + pIdVector.get(idx).toString());
            }
            sqlBuf.append(")");


            String sql = sqlBuf.toString();
            if (log.isDebugEnabled()) {
                log.debug("SQL: " + sql);
            }

            Statement stmt = pCon.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            UploadSkuData x=null;
            while (rs.next()) {
                // build the object
                x=UploadSkuData.createValue();
                
                x.setUploadSkuId(rs.getInt(1));
                x.setItemId(rs.getInt(2));
                x.setUploadId(rs.getInt(3));
                x.setRowNum(rs.getInt(4));
                x.setShortDesc(rs.getString(5));
                x.setLongDesc(rs.getString(6));
                x.setCategory(rs.getString(7));
                x.setSkuSize(rs.getString(8));
                x.setSkuPack(rs.getString(9));
                x.setSkuUom(rs.getString(10));
                x.setSkuColor(rs.getString(11));
                x.setManufId(rs.getInt(12));
                x.setManufName(rs.getString(13));
                x.setManufSku(rs.getString(14));
                x.setManufPack(rs.getString(15));
                x.setManufUom(rs.getString(16));
                x.setDistId(rs.getInt(17));
                x.setDistName(rs.getString(18));
                x.setDistSku(rs.getString(19));
                x.setDistUom(rs.getString(20));
                x.setDistPack(rs.getString(21));
                x.setListPrice(rs.getString(22));
                x.setDistCost(rs.getString(23));
                x.setSpl(rs.getString(24));
                x.setCatalogPrice(rs.getString(25));
                x.setAddDate(rs.getTimestamp(26));
                x.setAddBy(rs.getString(27));
                x.setModDate(rs.getTimestamp(28));
                x.setModBy(rs.getString(29));
                x.setSkuNum(rs.getString(30));
                x.setUnspscCode(rs.getString(31));
                x.setOtherDesc(rs.getString(32));
                x.setNsn(rs.getString(33));
                x.setMfcp(rs.getString(34));
                x.setPsn(rs.getString(35));
                x.setBaseCost(rs.getString(36));
                x.setGenManufId(rs.getInt(37));
                x.setGenManufName(rs.getString(38));
                x.setGenManufSku(rs.getString(39));
                x.setDistUomMult(rs.getString(40));
                x.setTaxExempt(rs.getString(41));
                x.setSpecialPermission(rs.getString(42));
                x.setImageUrl(rs.getString(43));
                x.setMsdsUrl(rs.getString(44));
                x.setDedUrl(rs.getString(45));
                x.setProdSpecUrl(rs.getString(46));
                x.setGreenCertified(rs.getString(47));
                x.setCustomerSkuNum(rs.getString(48));
                x.setShipWeight(rs.getString(49));
                x.setWeightUnit(rs.getString(50));
                x.setCustomerDesc(rs.getString(51));
                x.setAdminCategory(rs.getString(52));
                x.setThumbnailUrl(rs.getString(53));
                x.setServiceFeeCode(rs.getString(54));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a UploadSkuDataVector object of all
     * UploadSkuData objects in the database.
     * @param pCon An open database connection.
     * @return new UploadSkuDataVector()
     * @throws            SQLException
     */
    public static UploadSkuDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT UPLOAD_SKU_ID,ITEM_ID,UPLOAD_ID,ROW_NUM,SHORT_DESC,LONG_DESC,CATEGORY,SKU_SIZE,SKU_PACK,SKU_UOM,SKU_COLOR,MANUF_ID,MANUF_NAME,MANUF_SKU,MANUF_PACK,MANUF_UOM,DIST_ID,DIST_NAME,DIST_SKU,DIST_UOM,DIST_PACK,LIST_PRICE,DIST_COST,SPL,CATALOG_PRICE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SKU_NUM,UNSPSC_CODE,OTHER_DESC,NSN,MFCP,PSN,BASE_COST,GEN_MANUF_ID,GEN_MANUF_NAME,GEN_MANUF_SKU,DIST_UOM_MULT,TAX_EXEMPT,SPECIAL_PERMISSION,IMAGE_URL,MSDS_URL,DED_URL,PROD_SPEC_URL,GREEN_CERTIFIED,CUSTOMER_SKU_NUM,SHIP_WEIGHT,WEIGHT_UNIT,CUSTOMER_DESC,ADMIN_CATEGORY,THUMBNAIL_URL,SERVICE_FEE_CODE FROM CLW_UPLOAD_SKU";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        UploadSkuDataVector v = new UploadSkuDataVector();
        UploadSkuData x = null;
        while (rs.next()) {
            // build the object
            x = UploadSkuData.createValue();
            
            x.setUploadSkuId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setUploadId(rs.getInt(3));
            x.setRowNum(rs.getInt(4));
            x.setShortDesc(rs.getString(5));
            x.setLongDesc(rs.getString(6));
            x.setCategory(rs.getString(7));
            x.setSkuSize(rs.getString(8));
            x.setSkuPack(rs.getString(9));
            x.setSkuUom(rs.getString(10));
            x.setSkuColor(rs.getString(11));
            x.setManufId(rs.getInt(12));
            x.setManufName(rs.getString(13));
            x.setManufSku(rs.getString(14));
            x.setManufPack(rs.getString(15));
            x.setManufUom(rs.getString(16));
            x.setDistId(rs.getInt(17));
            x.setDistName(rs.getString(18));
            x.setDistSku(rs.getString(19));
            x.setDistUom(rs.getString(20));
            x.setDistPack(rs.getString(21));
            x.setListPrice(rs.getString(22));
            x.setDistCost(rs.getString(23));
            x.setSpl(rs.getString(24));
            x.setCatalogPrice(rs.getString(25));
            x.setAddDate(rs.getTimestamp(26));
            x.setAddBy(rs.getString(27));
            x.setModDate(rs.getTimestamp(28));
            x.setModBy(rs.getString(29));
            x.setSkuNum(rs.getString(30));
            x.setUnspscCode(rs.getString(31));
            x.setOtherDesc(rs.getString(32));
            x.setNsn(rs.getString(33));
            x.setMfcp(rs.getString(34));
            x.setPsn(rs.getString(35));
            x.setBaseCost(rs.getString(36));
            x.setGenManufId(rs.getInt(37));
            x.setGenManufName(rs.getString(38));
            x.setGenManufSku(rs.getString(39));
            x.setDistUomMult(rs.getString(40));
            x.setTaxExempt(rs.getString(41));
            x.setSpecialPermission(rs.getString(42));
            x.setImageUrl(rs.getString(43));
            x.setMsdsUrl(rs.getString(44));
            x.setDedUrl(rs.getString(45));
            x.setProdSpecUrl(rs.getString(46));
            x.setGreenCertified(rs.getString(47));
            x.setCustomerSkuNum(rs.getString(48));
            x.setShipWeight(rs.getString(49));
            x.setWeightUnit(rs.getString(50));
            x.setCustomerDesc(rs.getString(51));
            x.setAdminCategory(rs.getString(52));
            x.setThumbnailUrl(rs.getString(53));
            x.setServiceFeeCode(rs.getString(54));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * UploadSkuData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT UPLOAD_SKU_ID FROM CLW_UPLOAD_SKU");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        IdVector v = new IdVector();
        while (rs.next()) {
            Integer x = new Integer(rs.getInt(1));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of requested
     * objects in the database.
     * @param pCon An open database connection.
     * @param pIdName A column name
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, String pIdName, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_UPLOAD_SKU");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        IdVector v = new IdVector();
        while (rs.next()) {
            Integer x = new Integer(rs.getInt(1));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }


    /**
     * Gets an sql statement to request ids of
     * objects in the database.
     * @param pIdName A column name
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return String
     */
    public static String getSqlSelectIdOnly(String pIdName, DBCriteria pCriteria)
    {
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_UPLOAD_SKU");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL text: " + sql);
        }

        return sql;
    }

    /**
     * Inserts a UploadSkuData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadSkuData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new UploadSkuData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UploadSkuData insert(Connection pCon, UploadSkuData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_UPLOAD_SKU_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_UPLOAD_SKU_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setUploadSkuId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_UPLOAD_SKU (UPLOAD_SKU_ID,ITEM_ID,UPLOAD_ID,ROW_NUM,SHORT_DESC,LONG_DESC,CATEGORY,SKU_SIZE,SKU_PACK,SKU_UOM,SKU_COLOR,MANUF_ID,MANUF_NAME,MANUF_SKU,MANUF_PACK,MANUF_UOM,DIST_ID,DIST_NAME,DIST_SKU,DIST_UOM,DIST_PACK,LIST_PRICE,DIST_COST,SPL,CATALOG_PRICE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SKU_NUM,UNSPSC_CODE,OTHER_DESC,NSN,MFCP,PSN,BASE_COST,GEN_MANUF_ID,GEN_MANUF_NAME,GEN_MANUF_SKU,DIST_UOM_MULT,TAX_EXEMPT,SPECIAL_PERMISSION,IMAGE_URL,MSDS_URL,DED_URL,PROD_SPEC_URL,GREEN_CERTIFIED,CUSTOMER_SKU_NUM,SHIP_WEIGHT,WEIGHT_UNIT,CUSTOMER_DESC,ADMIN_CATEGORY,THUMBNAIL_URL,SERVICE_FEE_CODE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getUploadSkuId());
        pstmt.setInt(2,pData.getItemId());
        pstmt.setInt(3,pData.getUploadId());
        pstmt.setInt(4,pData.getRowNum());
        pstmt.setString(5,pData.getShortDesc());
        pstmt.setString(6,pData.getLongDesc());
        pstmt.setString(7,pData.getCategory());
        pstmt.setString(8,pData.getSkuSize());
        pstmt.setString(9,pData.getSkuPack());
        pstmt.setString(10,pData.getSkuUom());
        pstmt.setString(11,pData.getSkuColor());
        pstmt.setInt(12,pData.getManufId());
        pstmt.setString(13,pData.getManufName());
        pstmt.setString(14,pData.getManufSku());
        pstmt.setString(15,pData.getManufPack());
        pstmt.setString(16,pData.getManufUom());
        pstmt.setInt(17,pData.getDistId());
        pstmt.setString(18,pData.getDistName());
        pstmt.setString(19,pData.getDistSku());
        pstmt.setString(20,pData.getDistUom());
        pstmt.setString(21,pData.getDistPack());
        pstmt.setString(22,pData.getListPrice());
        pstmt.setString(23,pData.getDistCost());
        pstmt.setString(24,pData.getSpl());
        pstmt.setString(25,pData.getCatalogPrice());
        pstmt.setTimestamp(26,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(27,pData.getAddBy());
        pstmt.setTimestamp(28,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(29,pData.getModBy());
        pstmt.setString(30,pData.getSkuNum());
        pstmt.setString(31,pData.getUnspscCode());
        pstmt.setString(32,pData.getOtherDesc());
        pstmt.setString(33,pData.getNsn());
        pstmt.setString(34,pData.getMfcp());
        pstmt.setString(35,pData.getPsn());
        pstmt.setString(36,pData.getBaseCost());
        pstmt.setInt(37,pData.getGenManufId());
        pstmt.setString(38,pData.getGenManufName());
        pstmt.setString(39,pData.getGenManufSku());
        pstmt.setString(40,pData.getDistUomMult());
        pstmt.setString(41,pData.getTaxExempt());
        pstmt.setString(42,pData.getSpecialPermission());
        pstmt.setString(43,pData.getImageUrl());
        pstmt.setString(44,pData.getMsdsUrl());
        pstmt.setString(45,pData.getDedUrl());
        pstmt.setString(46,pData.getProdSpecUrl());
        pstmt.setString(47,pData.getGreenCertified());
        pstmt.setString(48,pData.getCustomerSkuNum());
        pstmt.setString(49,pData.getShipWeight());
        pstmt.setString(50,pData.getWeightUnit());
        pstmt.setString(51,pData.getCustomerDesc());
        pstmt.setString(52,pData.getAdminCategory());
        pstmt.setString(53,pData.getThumbnailUrl());
        pstmt.setString(54,pData.getServiceFeeCode());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   UPLOAD_SKU_ID="+pData.getUploadSkuId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   UPLOAD_ID="+pData.getUploadId());
            log.debug("SQL:   ROW_NUM="+pData.getRowNum());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   CATEGORY="+pData.getCategory());
            log.debug("SQL:   SKU_SIZE="+pData.getSkuSize());
            log.debug("SQL:   SKU_PACK="+pData.getSkuPack());
            log.debug("SQL:   SKU_UOM="+pData.getSkuUom());
            log.debug("SQL:   SKU_COLOR="+pData.getSkuColor());
            log.debug("SQL:   MANUF_ID="+pData.getManufId());
            log.debug("SQL:   MANUF_NAME="+pData.getManufName());
            log.debug("SQL:   MANUF_SKU="+pData.getManufSku());
            log.debug("SQL:   MANUF_PACK="+pData.getManufPack());
            log.debug("SQL:   MANUF_UOM="+pData.getManufUom());
            log.debug("SQL:   DIST_ID="+pData.getDistId());
            log.debug("SQL:   DIST_NAME="+pData.getDistName());
            log.debug("SQL:   DIST_SKU="+pData.getDistSku());
            log.debug("SQL:   DIST_UOM="+pData.getDistUom());
            log.debug("SQL:   DIST_PACK="+pData.getDistPack());
            log.debug("SQL:   LIST_PRICE="+pData.getListPrice());
            log.debug("SQL:   DIST_COST="+pData.getDistCost());
            log.debug("SQL:   SPL="+pData.getSpl());
            log.debug("SQL:   CATALOG_PRICE="+pData.getCatalogPrice());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   SKU_NUM="+pData.getSkuNum());
            log.debug("SQL:   UNSPSC_CODE="+pData.getUnspscCode());
            log.debug("SQL:   OTHER_DESC="+pData.getOtherDesc());
            log.debug("SQL:   NSN="+pData.getNsn());
            log.debug("SQL:   MFCP="+pData.getMfcp());
            log.debug("SQL:   PSN="+pData.getPsn());
            log.debug("SQL:   BASE_COST="+pData.getBaseCost());
            log.debug("SQL:   GEN_MANUF_ID="+pData.getGenManufId());
            log.debug("SQL:   GEN_MANUF_NAME="+pData.getGenManufName());
            log.debug("SQL:   GEN_MANUF_SKU="+pData.getGenManufSku());
            log.debug("SQL:   DIST_UOM_MULT="+pData.getDistUomMult());
            log.debug("SQL:   TAX_EXEMPT="+pData.getTaxExempt());
            log.debug("SQL:   SPECIAL_PERMISSION="+pData.getSpecialPermission());
            log.debug("SQL:   IMAGE_URL="+pData.getImageUrl());
            log.debug("SQL:   MSDS_URL="+pData.getMsdsUrl());
            log.debug("SQL:   DED_URL="+pData.getDedUrl());
            log.debug("SQL:   PROD_SPEC_URL="+pData.getProdSpecUrl());
            log.debug("SQL:   GREEN_CERTIFIED="+pData.getGreenCertified());
            log.debug("SQL:   CUSTOMER_SKU_NUM="+pData.getCustomerSkuNum());
            log.debug("SQL:   SHIP_WEIGHT="+pData.getShipWeight());
            log.debug("SQL:   WEIGHT_UNIT="+pData.getWeightUnit());
            log.debug("SQL:   CUSTOMER_DESC="+pData.getCustomerDesc());
            log.debug("SQL:   ADMIN_CATEGORY="+pData.getAdminCategory());
            log.debug("SQL:   THUMBNAIL_URL="+pData.getThumbnailUrl());
            log.debug("SQL:   SERVICE_FEE_CODE="+pData.getServiceFeeCode());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setUploadSkuId(0);
        exceptionMessage=e.getMessage();
        }
        finally{
        pstmt.close();
        }

        if(exceptionMessage!=null) {
                 throw new SQLException(exceptionMessage);
        }

        return pData;
    }

    /**
     * Updates a UploadSkuData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadSkuData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UploadSkuData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_UPLOAD_SKU SET ITEM_ID = ?,UPLOAD_ID = ?,ROW_NUM = ?,SHORT_DESC = ?,LONG_DESC = ?,CATEGORY = ?,SKU_SIZE = ?,SKU_PACK = ?,SKU_UOM = ?,SKU_COLOR = ?,MANUF_ID = ?,MANUF_NAME = ?,MANUF_SKU = ?,MANUF_PACK = ?,MANUF_UOM = ?,DIST_ID = ?,DIST_NAME = ?,DIST_SKU = ?,DIST_UOM = ?,DIST_PACK = ?,LIST_PRICE = ?,DIST_COST = ?,SPL = ?,CATALOG_PRICE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,SKU_NUM = ?,UNSPSC_CODE = ?,OTHER_DESC = ?,NSN = ?,MFCP = ?,PSN = ?,BASE_COST = ?,GEN_MANUF_ID = ?,GEN_MANUF_NAME = ?,GEN_MANUF_SKU = ?,DIST_UOM_MULT = ?,TAX_EXEMPT = ?,SPECIAL_PERMISSION = ?,IMAGE_URL = ?,MSDS_URL = ?,DED_URL = ?,PROD_SPEC_URL = ?,GREEN_CERTIFIED = ?,CUSTOMER_SKU_NUM = ?,SHIP_WEIGHT = ?,WEIGHT_UNIT = ?,CUSTOMER_DESC = ?,ADMIN_CATEGORY = ?,THUMBNAIL_URL = ?,SERVICE_FEE_CODE = ? WHERE UPLOAD_SKU_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setInt(i++,pData.getUploadId());
        pstmt.setInt(i++,pData.getRowNum());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setString(i++,pData.getCategory());
        pstmt.setString(i++,pData.getSkuSize());
        pstmt.setString(i++,pData.getSkuPack());
        pstmt.setString(i++,pData.getSkuUom());
        pstmt.setString(i++,pData.getSkuColor());
        pstmt.setInt(i++,pData.getManufId());
        pstmt.setString(i++,pData.getManufName());
        pstmt.setString(i++,pData.getManufSku());
        pstmt.setString(i++,pData.getManufPack());
        pstmt.setString(i++,pData.getManufUom());
        pstmt.setInt(i++,pData.getDistId());
        pstmt.setString(i++,pData.getDistName());
        pstmt.setString(i++,pData.getDistSku());
        pstmt.setString(i++,pData.getDistUom());
        pstmt.setString(i++,pData.getDistPack());
        pstmt.setString(i++,pData.getListPrice());
        pstmt.setString(i++,pData.getDistCost());
        pstmt.setString(i++,pData.getSpl());
        pstmt.setString(i++,pData.getCatalogPrice());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getSkuNum());
        pstmt.setString(i++,pData.getUnspscCode());
        pstmt.setString(i++,pData.getOtherDesc());
        pstmt.setString(i++,pData.getNsn());
        pstmt.setString(i++,pData.getMfcp());
        pstmt.setString(i++,pData.getPsn());
        pstmt.setString(i++,pData.getBaseCost());
        pstmt.setInt(i++,pData.getGenManufId());
        pstmt.setString(i++,pData.getGenManufName());
        pstmt.setString(i++,pData.getGenManufSku());
        pstmt.setString(i++,pData.getDistUomMult());
        pstmt.setString(i++,pData.getTaxExempt());
        pstmt.setString(i++,pData.getSpecialPermission());
        pstmt.setString(i++,pData.getImageUrl());
        pstmt.setString(i++,pData.getMsdsUrl());
        pstmt.setString(i++,pData.getDedUrl());
        pstmt.setString(i++,pData.getProdSpecUrl());
        pstmt.setString(i++,pData.getGreenCertified());
        pstmt.setString(i++,pData.getCustomerSkuNum());
        pstmt.setString(i++,pData.getShipWeight());
        pstmt.setString(i++,pData.getWeightUnit());
        pstmt.setString(i++,pData.getCustomerDesc());
        pstmt.setString(i++,pData.getAdminCategory());
        pstmt.setString(i++,pData.getThumbnailUrl());
        pstmt.setString(i++,pData.getServiceFeeCode());
        pstmt.setInt(i++,pData.getUploadSkuId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   UPLOAD_ID="+pData.getUploadId());
            log.debug("SQL:   ROW_NUM="+pData.getRowNum());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   CATEGORY="+pData.getCategory());
            log.debug("SQL:   SKU_SIZE="+pData.getSkuSize());
            log.debug("SQL:   SKU_PACK="+pData.getSkuPack());
            log.debug("SQL:   SKU_UOM="+pData.getSkuUom());
            log.debug("SQL:   SKU_COLOR="+pData.getSkuColor());
            log.debug("SQL:   MANUF_ID="+pData.getManufId());
            log.debug("SQL:   MANUF_NAME="+pData.getManufName());
            log.debug("SQL:   MANUF_SKU="+pData.getManufSku());
            log.debug("SQL:   MANUF_PACK="+pData.getManufPack());
            log.debug("SQL:   MANUF_UOM="+pData.getManufUom());
            log.debug("SQL:   DIST_ID="+pData.getDistId());
            log.debug("SQL:   DIST_NAME="+pData.getDistName());
            log.debug("SQL:   DIST_SKU="+pData.getDistSku());
            log.debug("SQL:   DIST_UOM="+pData.getDistUom());
            log.debug("SQL:   DIST_PACK="+pData.getDistPack());
            log.debug("SQL:   LIST_PRICE="+pData.getListPrice());
            log.debug("SQL:   DIST_COST="+pData.getDistCost());
            log.debug("SQL:   SPL="+pData.getSpl());
            log.debug("SQL:   CATALOG_PRICE="+pData.getCatalogPrice());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   SKU_NUM="+pData.getSkuNum());
            log.debug("SQL:   UNSPSC_CODE="+pData.getUnspscCode());
            log.debug("SQL:   OTHER_DESC="+pData.getOtherDesc());
            log.debug("SQL:   NSN="+pData.getNsn());
            log.debug("SQL:   MFCP="+pData.getMfcp());
            log.debug("SQL:   PSN="+pData.getPsn());
            log.debug("SQL:   BASE_COST="+pData.getBaseCost());
            log.debug("SQL:   GEN_MANUF_ID="+pData.getGenManufId());
            log.debug("SQL:   GEN_MANUF_NAME="+pData.getGenManufName());
            log.debug("SQL:   GEN_MANUF_SKU="+pData.getGenManufSku());
            log.debug("SQL:   DIST_UOM_MULT="+pData.getDistUomMult());
            log.debug("SQL:   TAX_EXEMPT="+pData.getTaxExempt());
            log.debug("SQL:   SPECIAL_PERMISSION="+pData.getSpecialPermission());
            log.debug("SQL:   IMAGE_URL="+pData.getImageUrl());
            log.debug("SQL:   MSDS_URL="+pData.getMsdsUrl());
            log.debug("SQL:   DED_URL="+pData.getDedUrl());
            log.debug("SQL:   PROD_SPEC_URL="+pData.getProdSpecUrl());
            log.debug("SQL:   GREEN_CERTIFIED="+pData.getGreenCertified());
            log.debug("SQL:   CUSTOMER_SKU_NUM="+pData.getCustomerSkuNum());
            log.debug("SQL:   SHIP_WEIGHT="+pData.getShipWeight());
            log.debug("SQL:   WEIGHT_UNIT="+pData.getWeightUnit());
            log.debug("SQL:   CUSTOMER_DESC="+pData.getCustomerDesc());
            log.debug("SQL:   ADMIN_CATEGORY="+pData.getAdminCategory());
            log.debug("SQL:   THUMBNAIL_URL="+pData.getThumbnailUrl());
            log.debug("SQL:   SERVICE_FEE_CODE="+pData.getServiceFeeCode());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a UploadSkuData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUploadSkuId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUploadSkuId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_UPLOAD_SKU WHERE UPLOAD_SKU_ID = " + pUploadSkuId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes UploadSkuData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_UPLOAD_SKU");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
    }

    /**
     * Inserts a UploadSkuData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadSkuData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, UploadSkuData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_UPLOAD_SKU (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "UPLOAD_SKU_ID,ITEM_ID,UPLOAD_ID,ROW_NUM,SHORT_DESC,LONG_DESC,CATEGORY,SKU_SIZE,SKU_PACK,SKU_UOM,SKU_COLOR,MANUF_ID,MANUF_NAME,MANUF_SKU,MANUF_PACK,MANUF_UOM,DIST_ID,DIST_NAME,DIST_SKU,DIST_UOM,DIST_PACK,LIST_PRICE,DIST_COST,SPL,CATALOG_PRICE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SKU_NUM,UNSPSC_CODE,OTHER_DESC,NSN,MFCP,PSN,BASE_COST,GEN_MANUF_ID,GEN_MANUF_NAME,GEN_MANUF_SKU,DIST_UOM_MULT,TAX_EXEMPT,SPECIAL_PERMISSION,IMAGE_URL,MSDS_URL,DED_URL,PROD_SPEC_URL,GREEN_CERTIFIED,CUSTOMER_SKU_NUM,SHIP_WEIGHT,WEIGHT_UNIT,CUSTOMER_DESC,ADMIN_CATEGORY,THUMBNAIL_URL,SERVICE_FEE_CODE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getUploadSkuId());
        pstmt.setInt(2+4,pData.getItemId());
        pstmt.setInt(3+4,pData.getUploadId());
        pstmt.setInt(4+4,pData.getRowNum());
        pstmt.setString(5+4,pData.getShortDesc());
        pstmt.setString(6+4,pData.getLongDesc());
        pstmt.setString(7+4,pData.getCategory());
        pstmt.setString(8+4,pData.getSkuSize());
        pstmt.setString(9+4,pData.getSkuPack());
        pstmt.setString(10+4,pData.getSkuUom());
        pstmt.setString(11+4,pData.getSkuColor());
        pstmt.setInt(12+4,pData.getManufId());
        pstmt.setString(13+4,pData.getManufName());
        pstmt.setString(14+4,pData.getManufSku());
        pstmt.setString(15+4,pData.getManufPack());
        pstmt.setString(16+4,pData.getManufUom());
        pstmt.setInt(17+4,pData.getDistId());
        pstmt.setString(18+4,pData.getDistName());
        pstmt.setString(19+4,pData.getDistSku());
        pstmt.setString(20+4,pData.getDistUom());
        pstmt.setString(21+4,pData.getDistPack());
        pstmt.setString(22+4,pData.getListPrice());
        pstmt.setString(23+4,pData.getDistCost());
        pstmt.setString(24+4,pData.getSpl());
        pstmt.setString(25+4,pData.getCatalogPrice());
        pstmt.setTimestamp(26+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(27+4,pData.getAddBy());
        pstmt.setTimestamp(28+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(29+4,pData.getModBy());
        pstmt.setString(30+4,pData.getSkuNum());
        pstmt.setString(31+4,pData.getUnspscCode());
        pstmt.setString(32+4,pData.getOtherDesc());
        pstmt.setString(33+4,pData.getNsn());
        pstmt.setString(34+4,pData.getMfcp());
        pstmt.setString(35+4,pData.getPsn());
        pstmt.setString(36+4,pData.getBaseCost());
        pstmt.setInt(37+4,pData.getGenManufId());
        pstmt.setString(38+4,pData.getGenManufName());
        pstmt.setString(39+4,pData.getGenManufSku());
        pstmt.setString(40+4,pData.getDistUomMult());
        pstmt.setString(41+4,pData.getTaxExempt());
        pstmt.setString(42+4,pData.getSpecialPermission());
        pstmt.setString(43+4,pData.getImageUrl());
        pstmt.setString(44+4,pData.getMsdsUrl());
        pstmt.setString(45+4,pData.getDedUrl());
        pstmt.setString(46+4,pData.getProdSpecUrl());
        pstmt.setString(47+4,pData.getGreenCertified());
        pstmt.setString(48+4,pData.getCustomerSkuNum());
        pstmt.setString(49+4,pData.getShipWeight());
        pstmt.setString(50+4,pData.getWeightUnit());
        pstmt.setString(51+4,pData.getCustomerDesc());
        pstmt.setString(52+4,pData.getAdminCategory());
        pstmt.setString(53+4,pData.getThumbnailUrl());
        pstmt.setString(54+4,pData.getServiceFeeCode());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a UploadSkuData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadSkuData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new UploadSkuData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UploadSkuData insert(Connection pCon, UploadSkuData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a UploadSkuData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadSkuData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UploadSkuData pData, boolean pLogFl)
        throws SQLException {
        UploadSkuData oldData = null;
        if(pLogFl) {
          int id = pData.getUploadSkuId();
          try {
          oldData = UploadSkuDataAccess.select(pCon,id);
          } catch(DataNotFoundException exc) {}
        }
        int n = update(pCon,pData);
        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, oldData, millis, "U", "O");
          insertLog(pCon, pData, millis, "U", "N");
        }
        return n;
    }

    /**
     * Deletes a UploadSkuData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUploadSkuId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUploadSkuId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_UPLOAD_SKU SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_UPLOAD_SKU d WHERE UPLOAD_SKU_ID = " + pUploadSkuId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pUploadSkuId);
        return n;
     }

    /**
     * Deletes UploadSkuData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria, boolean pLogFl)
        throws SQLException {
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          StringBuffer sqlBuf =
             new StringBuffer("INSERT INTO LCLW_UPLOAD_SKU SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_UPLOAD_SKU d ");
          String where = pCriteria.getSqlClause();
          sqlBuf.append(" WHERE ");
          sqlBuf.append(where);

          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlBuf.toString());
          stmt.close();
        }
        int n = remove(pCon,pCriteria);
        return n;
    }
///////////////////////////////////////////////
}


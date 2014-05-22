
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        StagedAssetDataAccess
 * Description:  This class is used to build access methods to the CLW_STAGED_ASSET table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.StagedAssetData;
import com.cleanwise.service.api.value.StagedAssetDataVector;
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
 * <code>StagedAssetDataAccess</code>
 */
public class StagedAssetDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(StagedAssetDataAccess.class.getName());

    /** <code>CLW_STAGED_ASSET</code> table name */
	/* Primary key: STAGED_ASSET_ID */
	
    public static final String CLW_STAGED_ASSET = "CLW_STAGED_ASSET";
    
    /** <code>STAGED_ASSET_ID</code> STAGED_ASSET_ID column of table CLW_STAGED_ASSET */
    public static final String STAGED_ASSET_ID = "STAGED_ASSET_ID";
    /** <code>VERSION_NUMBER</code> VERSION_NUMBER column of table CLW_STAGED_ASSET */
    public static final String VERSION_NUMBER = "VERSION_NUMBER";
    /** <code>ACTION</code> ACTION column of table CLW_STAGED_ASSET */
    public static final String ACTION = "ACTION";
    /** <code>ASSET</code> ASSET column of table CLW_STAGED_ASSET */
    public static final String ASSET = "ASSET";
    /** <code>STORE_ID</code> STORE_ID column of table CLW_STAGED_ASSET */
    public static final String STORE_ID = "STORE_ID";
    /** <code>STORE_NAME</code> STORE_NAME column of table CLW_STAGED_ASSET */
    public static final String STORE_NAME = "STORE_NAME";
    /** <code>DIST_SKU</code> DIST_SKU column of table CLW_STAGED_ASSET */
    public static final String DIST_SKU = "DIST_SKU";
    /** <code>MFG_SKU</code> MFG_SKU column of table CLW_STAGED_ASSET */
    public static final String MFG_SKU = "MFG_SKU";
    /** <code>MANUFACTURER</code> MANUFACTURER column of table CLW_STAGED_ASSET */
    public static final String MANUFACTURER = "MANUFACTURER";
    /** <code>DISTRIBUTOR</code> DISTRIBUTOR column of table CLW_STAGED_ASSET */
    public static final String DISTRIBUTOR = "DISTRIBUTOR";
    /** <code>PACK</code> PACK column of table CLW_STAGED_ASSET */
    public static final String PACK = "PACK";
    /** <code>UOM</code> UOM column of table CLW_STAGED_ASSET */
    public static final String UOM = "UOM";
    /** <code>CATEGORY_NAME</code> CATEGORY_NAME column of table CLW_STAGED_ASSET */
    public static final String CATEGORY_NAME = "CATEGORY_NAME";
    /** <code>SUB_CAT_1</code> SUB_CAT_1 column of table CLW_STAGED_ASSET */
    public static final String SUB_CAT_1 = "SUB_CAT_1";
    /** <code>SUB_CAT_2</code> SUB_CAT_2 column of table CLW_STAGED_ASSET */
    public static final String SUB_CAT_2 = "SUB_CAT_2";
    /** <code>SUB_CAT_3</code> SUB_CAT_3 column of table CLW_STAGED_ASSET */
    public static final String SUB_CAT_3 = "SUB_CAT_3";
    /** <code>MULTI_PRODUCT_NAME</code> MULTI_PRODUCT_NAME column of table CLW_STAGED_ASSET */
    public static final String MULTI_PRODUCT_NAME = "MULTI_PRODUCT_NAME";
    /** <code>ITEM_SIZE</code> ITEM_SIZE column of table CLW_STAGED_ASSET */
    public static final String ITEM_SIZE = "ITEM_SIZE";
    /** <code>LONG_DESCRIPTION</code> LONG_DESCRIPTION column of table CLW_STAGED_ASSET */
    public static final String LONG_DESCRIPTION = "LONG_DESCRIPTION";
    /** <code>SHORT_DESCRIPTION</code> SHORT_DESCRIPTION column of table CLW_STAGED_ASSET */
    public static final String SHORT_DESCRIPTION = "SHORT_DESCRIPTION";
    /** <code>PRODUCT_UPC</code> PRODUCT_UPC column of table CLW_STAGED_ASSET */
    public static final String PRODUCT_UPC = "PRODUCT_UPC";
    /** <code>PACK_UPC</code> PACK_UPC column of table CLW_STAGED_ASSET */
    public static final String PACK_UPC = "PACK_UPC";
    /** <code>UNSPSC_CODE</code> UNSPSC_CODE column of table CLW_STAGED_ASSET */
    public static final String UNSPSC_CODE = "UNSPSC_CODE";
    /** <code>COLOR</code> COLOR column of table CLW_STAGED_ASSET */
    public static final String COLOR = "COLOR";
    /** <code>SHIPPING_WEIGHT</code> SHIPPING_WEIGHT column of table CLW_STAGED_ASSET */
    public static final String SHIPPING_WEIGHT = "SHIPPING_WEIGHT";
    /** <code>WEIGHT_UNIT</code> WEIGHT_UNIT column of table CLW_STAGED_ASSET */
    public static final String WEIGHT_UNIT = "WEIGHT_UNIT";
    /** <code>NSN</code> NSN column of table CLW_STAGED_ASSET */
    public static final String NSN = "NSN";
    /** <code>SHIPPING_CUBIC_SIZE</code> SHIPPING_CUBIC_SIZE column of table CLW_STAGED_ASSET */
    public static final String SHIPPING_CUBIC_SIZE = "SHIPPING_CUBIC_SIZE";
    /** <code>HAZMAT</code> HAZMAT column of table CLW_STAGED_ASSET */
    public static final String HAZMAT = "HAZMAT";
    /** <code>CERTIFIED_COMPANIES</code> CERTIFIED_COMPANIES column of table CLW_STAGED_ASSET */
    public static final String CERTIFIED_COMPANIES = "CERTIFIED_COMPANIES";
    /** <code>IMAGE</code> IMAGE column of table CLW_STAGED_ASSET */
    public static final String IMAGE = "IMAGE";
    /** <code>IMAGE_BLOB</code> IMAGE_BLOB column of table CLW_STAGED_ASSET */
    public static final String IMAGE_BLOB = "IMAGE_BLOB";
    /** <code>MSDS</code> MSDS column of table CLW_STAGED_ASSET */
    public static final String MSDS = "MSDS";
    /** <code>MSDS_BLOB</code> MSDS_BLOB column of table CLW_STAGED_ASSET */
    public static final String MSDS_BLOB = "MSDS_BLOB";
    /** <code>SPECIFICATION</code> SPECIFICATION column of table CLW_STAGED_ASSET */
    public static final String SPECIFICATION = "SPECIFICATION";
    /** <code>SPECIFICATION_BLOB</code> SPECIFICATION_BLOB column of table CLW_STAGED_ASSET */
    public static final String SPECIFICATION_BLOB = "SPECIFICATION_BLOB";
    /** <code>ASSET_NAME</code> ASSET_NAME column of table CLW_STAGED_ASSET */
    public static final String ASSET_NAME = "ASSET_NAME";
    /** <code>MODEL_NUMBER</code> MODEL_NUMBER column of table CLW_STAGED_ASSET */
    public static final String MODEL_NUMBER = "MODEL_NUMBER";
    /** <code>ASSOC_DOC_1</code> ASSOC_DOC_1 column of table CLW_STAGED_ASSET */
    public static final String ASSOC_DOC_1 = "ASSOC_DOC_1";
    /** <code>ASSOC_DOC_1_BLOB</code> ASSOC_DOC_1_BLOB column of table CLW_STAGED_ASSET */
    public static final String ASSOC_DOC_1_BLOB = "ASSOC_DOC_1_BLOB";
    /** <code>ASSOC_DOC_2</code> ASSOC_DOC_2 column of table CLW_STAGED_ASSET */
    public static final String ASSOC_DOC_2 = "ASSOC_DOC_2";
    /** <code>ASSOC_DOC_2_BLOB</code> ASSOC_DOC_2_BLOB column of table CLW_STAGED_ASSET */
    public static final String ASSOC_DOC_2_BLOB = "ASSOC_DOC_2_BLOB";
    /** <code>ASSOC_DOC_3</code> ASSOC_DOC_3 column of table CLW_STAGED_ASSET */
    public static final String ASSOC_DOC_3 = "ASSOC_DOC_3";
    /** <code>ASSOC_DOC_3_BLOB</code> ASSOC_DOC_3_BLOB column of table CLW_STAGED_ASSET */
    public static final String ASSOC_DOC_3_BLOB = "ASSOC_DOC_3_BLOB";
    /** <code>MATCHED_ASSET_ID</code> MATCHED_ASSET_ID column of table CLW_STAGED_ASSET */
    public static final String MATCHED_ASSET_ID = "MATCHED_ASSET_ID";

    /**
     * Constructor.
     */
    public StagedAssetDataAccess()
    {
    }

    /**
     * Gets a StagedAssetData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pStagedAssetId The key requested.
     * @return new StagedAssetData()
     * @throws            SQLException
     */
    public static StagedAssetData select(Connection pCon, int pStagedAssetId)
        throws SQLException, DataNotFoundException {
        StagedAssetData x=null;
        String sql="SELECT STAGED_ASSET_ID,VERSION_NUMBER,ACTION,ASSET,STORE_ID,STORE_NAME,DIST_SKU,MFG_SKU,MANUFACTURER,DISTRIBUTOR,PACK,UOM,CATEGORY_NAME,SUB_CAT_1,SUB_CAT_2,SUB_CAT_3,MULTI_PRODUCT_NAME,ITEM_SIZE,LONG_DESCRIPTION,SHORT_DESCRIPTION,PRODUCT_UPC,PACK_UPC,UNSPSC_CODE,COLOR,SHIPPING_WEIGHT,WEIGHT_UNIT,NSN,SHIPPING_CUBIC_SIZE,HAZMAT,CERTIFIED_COMPANIES,IMAGE,IMAGE_BLOB,MSDS,MSDS_BLOB,SPECIFICATION,SPECIFICATION_BLOB,ASSET_NAME,MODEL_NUMBER,ASSOC_DOC_1,ASSOC_DOC_1_BLOB,ASSOC_DOC_2,ASSOC_DOC_2_BLOB,ASSOC_DOC_3,ASSOC_DOC_3_BLOB,MATCHED_ASSET_ID FROM CLW_STAGED_ASSET WHERE STAGED_ASSET_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pStagedAssetId=" + pStagedAssetId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pStagedAssetId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=StagedAssetData.createValue();
            
            x.setStagedAssetId(rs.getInt(1));
            x.setVersionNumber(rs.getInt(2));
            x.setAction(rs.getString(3));
            x.setAsset(rs.getString(4));
            x.setStoreId(rs.getInt(5));
            x.setStoreName(rs.getString(6));
            x.setDistSku(rs.getString(7));
            x.setMfgSku(rs.getString(8));
            x.setManufacturer(rs.getString(9));
            x.setDistributor(rs.getString(10));
            x.setPack(rs.getString(11));
            x.setUom(rs.getString(12));
            x.setCategoryName(rs.getString(13));
            x.setSubCat1(rs.getString(14));
            x.setSubCat2(rs.getString(15));
            x.setSubCat3(rs.getString(16));
            x.setMultiProductName(rs.getString(17));
            x.setItemSize(rs.getString(18));
            x.setLongDescription(rs.getString(19));
            x.setShortDescription(rs.getString(20));
            x.setProductUpc(rs.getString(21));
            x.setPackUpc(rs.getString(22));
            x.setUnspscCode(rs.getString(23));
            x.setColor(rs.getString(24));
            x.setShippingWeight(rs.getInt(25));
            x.setWeightUnit(rs.getString(26));
            x.setNsn(rs.getInt(27));
            x.setShippingCubicSize(rs.getInt(28));
            x.setHazmat(rs.getString(29));
            x.setCertifiedCompanies(rs.getString(30));
            x.setImage(rs.getString(31));
            x.setImageBlob(rs.getBytes(32));
            x.setMsds(rs.getString(33));
            x.setMsdsBlob(rs.getBytes(34));
            x.setSpecification(rs.getString(35));
            x.setSpecificationBlob(rs.getBytes(36));
            x.setAssetName(rs.getString(37));
            x.setModelNumber(rs.getString(38));
            x.setAssocDoc1(rs.getString(39));
            x.setAssocDoc1Blob(rs.getBytes(40));
            x.setAssocDoc2(rs.getString(41));
            x.setAssocDoc2Blob(rs.getBytes(42));
            x.setAssocDoc3(rs.getString(43));
            x.setAssocDoc3Blob(rs.getBytes(44));
            x.setMatchedAssetId(rs.getInt(45));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("STAGED_ASSET_ID :" + pStagedAssetId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a StagedAssetDataVector object that consists
     * of StagedAssetData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new StagedAssetDataVector()
     * @throws            SQLException
     */
    public static StagedAssetDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a StagedAssetData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_STAGED_ASSET.STAGED_ASSET_ID,CLW_STAGED_ASSET.VERSION_NUMBER,CLW_STAGED_ASSET.ACTION,CLW_STAGED_ASSET.ASSET,CLW_STAGED_ASSET.STORE_ID,CLW_STAGED_ASSET.STORE_NAME,CLW_STAGED_ASSET.DIST_SKU,CLW_STAGED_ASSET.MFG_SKU,CLW_STAGED_ASSET.MANUFACTURER,CLW_STAGED_ASSET.DISTRIBUTOR,CLW_STAGED_ASSET.PACK,CLW_STAGED_ASSET.UOM,CLW_STAGED_ASSET.CATEGORY_NAME,CLW_STAGED_ASSET.SUB_CAT_1,CLW_STAGED_ASSET.SUB_CAT_2,CLW_STAGED_ASSET.SUB_CAT_3,CLW_STAGED_ASSET.MULTI_PRODUCT_NAME,CLW_STAGED_ASSET.ITEM_SIZE,CLW_STAGED_ASSET.LONG_DESCRIPTION,CLW_STAGED_ASSET.SHORT_DESCRIPTION,CLW_STAGED_ASSET.PRODUCT_UPC,CLW_STAGED_ASSET.PACK_UPC,CLW_STAGED_ASSET.UNSPSC_CODE,CLW_STAGED_ASSET.COLOR,CLW_STAGED_ASSET.SHIPPING_WEIGHT,CLW_STAGED_ASSET.WEIGHT_UNIT,CLW_STAGED_ASSET.NSN,CLW_STAGED_ASSET.SHIPPING_CUBIC_SIZE,CLW_STAGED_ASSET.HAZMAT,CLW_STAGED_ASSET.CERTIFIED_COMPANIES,CLW_STAGED_ASSET.IMAGE,CLW_STAGED_ASSET.IMAGE_BLOB,CLW_STAGED_ASSET.MSDS,CLW_STAGED_ASSET.MSDS_BLOB,CLW_STAGED_ASSET.SPECIFICATION,CLW_STAGED_ASSET.SPECIFICATION_BLOB,CLW_STAGED_ASSET.ASSET_NAME,CLW_STAGED_ASSET.MODEL_NUMBER,CLW_STAGED_ASSET.ASSOC_DOC_1,CLW_STAGED_ASSET.ASSOC_DOC_1_BLOB,CLW_STAGED_ASSET.ASSOC_DOC_2,CLW_STAGED_ASSET.ASSOC_DOC_2_BLOB,CLW_STAGED_ASSET.ASSOC_DOC_3,CLW_STAGED_ASSET.ASSOC_DOC_3_BLOB,CLW_STAGED_ASSET.MATCHED_ASSET_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated StagedAssetData Object.
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
    *@returns a populated StagedAssetData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         StagedAssetData x = StagedAssetData.createValue();
         
         x.setStagedAssetId(rs.getInt(1+offset));
         x.setVersionNumber(rs.getInt(2+offset));
         x.setAction(rs.getString(3+offset));
         x.setAsset(rs.getString(4+offset));
         x.setStoreId(rs.getInt(5+offset));
         x.setStoreName(rs.getString(6+offset));
         x.setDistSku(rs.getString(7+offset));
         x.setMfgSku(rs.getString(8+offset));
         x.setManufacturer(rs.getString(9+offset));
         x.setDistributor(rs.getString(10+offset));
         x.setPack(rs.getString(11+offset));
         x.setUom(rs.getString(12+offset));
         x.setCategoryName(rs.getString(13+offset));
         x.setSubCat1(rs.getString(14+offset));
         x.setSubCat2(rs.getString(15+offset));
         x.setSubCat3(rs.getString(16+offset));
         x.setMultiProductName(rs.getString(17+offset));
         x.setItemSize(rs.getString(18+offset));
         x.setLongDescription(rs.getString(19+offset));
         x.setShortDescription(rs.getString(20+offset));
         x.setProductUpc(rs.getString(21+offset));
         x.setPackUpc(rs.getString(22+offset));
         x.setUnspscCode(rs.getString(23+offset));
         x.setColor(rs.getString(24+offset));
         x.setShippingWeight(rs.getInt(25+offset));
         x.setWeightUnit(rs.getString(26+offset));
         x.setNsn(rs.getInt(27+offset));
         x.setShippingCubicSize(rs.getInt(28+offset));
         x.setHazmat(rs.getString(29+offset));
         x.setCertifiedCompanies(rs.getString(30+offset));
         x.setImage(rs.getString(31+offset));
         x.setImageBlob(rs.getBytes(32+offset));
         x.setMsds(rs.getString(33+offset));
         x.setMsdsBlob(rs.getBytes(34+offset));
         x.setSpecification(rs.getString(35+offset));
         x.setSpecificationBlob(rs.getBytes(36+offset));
         x.setAssetName(rs.getString(37+offset));
         x.setModelNumber(rs.getString(38+offset));
         x.setAssocDoc1(rs.getString(39+offset));
         x.setAssocDoc1Blob(rs.getBytes(40+offset));
         x.setAssocDoc2(rs.getString(41+offset));
         x.setAssocDoc2Blob(rs.getBytes(42+offset));
         x.setAssocDoc3(rs.getString(43+offset));
         x.setAssocDoc3Blob(rs.getBytes(44+offset));
         x.setMatchedAssetId(rs.getInt(45+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the StagedAssetData Object represents.
    */
    public int getColumnCount(){
        return 45;
    }

    /**
     * Gets a StagedAssetDataVector object that consists
     * of StagedAssetData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new StagedAssetDataVector()
     * @throws            SQLException
     */
    public static StagedAssetDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT STAGED_ASSET_ID,VERSION_NUMBER,ACTION,ASSET,STORE_ID,STORE_NAME,DIST_SKU,MFG_SKU,MANUFACTURER,DISTRIBUTOR,PACK,UOM,CATEGORY_NAME,SUB_CAT_1,SUB_CAT_2,SUB_CAT_3,MULTI_PRODUCT_NAME,ITEM_SIZE,LONG_DESCRIPTION,SHORT_DESCRIPTION,PRODUCT_UPC,PACK_UPC,UNSPSC_CODE,COLOR,SHIPPING_WEIGHT,WEIGHT_UNIT,NSN,SHIPPING_CUBIC_SIZE,HAZMAT,CERTIFIED_COMPANIES,IMAGE,IMAGE_BLOB,MSDS,MSDS_BLOB,SPECIFICATION,SPECIFICATION_BLOB,ASSET_NAME,MODEL_NUMBER,ASSOC_DOC_1,ASSOC_DOC_1_BLOB,ASSOC_DOC_2,ASSOC_DOC_2_BLOB,ASSOC_DOC_3,ASSOC_DOC_3_BLOB,MATCHED_ASSET_ID FROM CLW_STAGED_ASSET");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_STAGED_ASSET.STAGED_ASSET_ID,CLW_STAGED_ASSET.VERSION_NUMBER,CLW_STAGED_ASSET.ACTION,CLW_STAGED_ASSET.ASSET,CLW_STAGED_ASSET.STORE_ID,CLW_STAGED_ASSET.STORE_NAME,CLW_STAGED_ASSET.DIST_SKU,CLW_STAGED_ASSET.MFG_SKU,CLW_STAGED_ASSET.MANUFACTURER,CLW_STAGED_ASSET.DISTRIBUTOR,CLW_STAGED_ASSET.PACK,CLW_STAGED_ASSET.UOM,CLW_STAGED_ASSET.CATEGORY_NAME,CLW_STAGED_ASSET.SUB_CAT_1,CLW_STAGED_ASSET.SUB_CAT_2,CLW_STAGED_ASSET.SUB_CAT_3,CLW_STAGED_ASSET.MULTI_PRODUCT_NAME,CLW_STAGED_ASSET.ITEM_SIZE,CLW_STAGED_ASSET.LONG_DESCRIPTION,CLW_STAGED_ASSET.SHORT_DESCRIPTION,CLW_STAGED_ASSET.PRODUCT_UPC,CLW_STAGED_ASSET.PACK_UPC,CLW_STAGED_ASSET.UNSPSC_CODE,CLW_STAGED_ASSET.COLOR,CLW_STAGED_ASSET.SHIPPING_WEIGHT,CLW_STAGED_ASSET.WEIGHT_UNIT,CLW_STAGED_ASSET.NSN,CLW_STAGED_ASSET.SHIPPING_CUBIC_SIZE,CLW_STAGED_ASSET.HAZMAT,CLW_STAGED_ASSET.CERTIFIED_COMPANIES,CLW_STAGED_ASSET.IMAGE,CLW_STAGED_ASSET.IMAGE_BLOB,CLW_STAGED_ASSET.MSDS,CLW_STAGED_ASSET.MSDS_BLOB,CLW_STAGED_ASSET.SPECIFICATION,CLW_STAGED_ASSET.SPECIFICATION_BLOB,CLW_STAGED_ASSET.ASSET_NAME,CLW_STAGED_ASSET.MODEL_NUMBER,CLW_STAGED_ASSET.ASSOC_DOC_1,CLW_STAGED_ASSET.ASSOC_DOC_1_BLOB,CLW_STAGED_ASSET.ASSOC_DOC_2,CLW_STAGED_ASSET.ASSOC_DOC_2_BLOB,CLW_STAGED_ASSET.ASSOC_DOC_3,CLW_STAGED_ASSET.ASSOC_DOC_3_BLOB,CLW_STAGED_ASSET.MATCHED_ASSET_ID FROM CLW_STAGED_ASSET");
                where = pCriteria.getSqlClause("CLW_STAGED_ASSET");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STAGED_ASSET.equals(otherTable)){
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
        StagedAssetDataVector v = new StagedAssetDataVector();
        while (rs.next()) {
            StagedAssetData x = StagedAssetData.createValue();
            
            x.setStagedAssetId(rs.getInt(1));
            x.setVersionNumber(rs.getInt(2));
            x.setAction(rs.getString(3));
            x.setAsset(rs.getString(4));
            x.setStoreId(rs.getInt(5));
            x.setStoreName(rs.getString(6));
            x.setDistSku(rs.getString(7));
            x.setMfgSku(rs.getString(8));
            x.setManufacturer(rs.getString(9));
            x.setDistributor(rs.getString(10));
            x.setPack(rs.getString(11));
            x.setUom(rs.getString(12));
            x.setCategoryName(rs.getString(13));
            x.setSubCat1(rs.getString(14));
            x.setSubCat2(rs.getString(15));
            x.setSubCat3(rs.getString(16));
            x.setMultiProductName(rs.getString(17));
            x.setItemSize(rs.getString(18));
            x.setLongDescription(rs.getString(19));
            x.setShortDescription(rs.getString(20));
            x.setProductUpc(rs.getString(21));
            x.setPackUpc(rs.getString(22));
            x.setUnspscCode(rs.getString(23));
            x.setColor(rs.getString(24));
            x.setShippingWeight(rs.getInt(25));
            x.setWeightUnit(rs.getString(26));
            x.setNsn(rs.getInt(27));
            x.setShippingCubicSize(rs.getInt(28));
            x.setHazmat(rs.getString(29));
            x.setCertifiedCompanies(rs.getString(30));
            x.setImage(rs.getString(31));
            x.setImageBlob(rs.getBytes(32));
            x.setMsds(rs.getString(33));
            x.setMsdsBlob(rs.getBytes(34));
            x.setSpecification(rs.getString(35));
            x.setSpecificationBlob(rs.getBytes(36));
            x.setAssetName(rs.getString(37));
            x.setModelNumber(rs.getString(38));
            x.setAssocDoc1(rs.getString(39));
            x.setAssocDoc1Blob(rs.getBytes(40));
            x.setAssocDoc2(rs.getString(41));
            x.setAssocDoc2Blob(rs.getBytes(42));
            x.setAssocDoc3(rs.getString(43));
            x.setAssocDoc3Blob(rs.getBytes(44));
            x.setMatchedAssetId(rs.getInt(45));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a StagedAssetDataVector object that consists
     * of StagedAssetData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for StagedAssetData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new StagedAssetDataVector()
     * @throws            SQLException
     */
    public static StagedAssetDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        StagedAssetDataVector v = new StagedAssetDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT STAGED_ASSET_ID,VERSION_NUMBER,ACTION,ASSET,STORE_ID,STORE_NAME,DIST_SKU,MFG_SKU,MANUFACTURER,DISTRIBUTOR,PACK,UOM,CATEGORY_NAME,SUB_CAT_1,SUB_CAT_2,SUB_CAT_3,MULTI_PRODUCT_NAME,ITEM_SIZE,LONG_DESCRIPTION,SHORT_DESCRIPTION,PRODUCT_UPC,PACK_UPC,UNSPSC_CODE,COLOR,SHIPPING_WEIGHT,WEIGHT_UNIT,NSN,SHIPPING_CUBIC_SIZE,HAZMAT,CERTIFIED_COMPANIES,IMAGE,IMAGE_BLOB,MSDS,MSDS_BLOB,SPECIFICATION,SPECIFICATION_BLOB,ASSET_NAME,MODEL_NUMBER,ASSOC_DOC_1,ASSOC_DOC_1_BLOB,ASSOC_DOC_2,ASSOC_DOC_2_BLOB,ASSOC_DOC_3,ASSOC_DOC_3_BLOB,MATCHED_ASSET_ID FROM CLW_STAGED_ASSET WHERE STAGED_ASSET_ID IN (");

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
            StagedAssetData x=null;
            while (rs.next()) {
                // build the object
                x=StagedAssetData.createValue();
                
                x.setStagedAssetId(rs.getInt(1));
                x.setVersionNumber(rs.getInt(2));
                x.setAction(rs.getString(3));
                x.setAsset(rs.getString(4));
                x.setStoreId(rs.getInt(5));
                x.setStoreName(rs.getString(6));
                x.setDistSku(rs.getString(7));
                x.setMfgSku(rs.getString(8));
                x.setManufacturer(rs.getString(9));
                x.setDistributor(rs.getString(10));
                x.setPack(rs.getString(11));
                x.setUom(rs.getString(12));
                x.setCategoryName(rs.getString(13));
                x.setSubCat1(rs.getString(14));
                x.setSubCat2(rs.getString(15));
                x.setSubCat3(rs.getString(16));
                x.setMultiProductName(rs.getString(17));
                x.setItemSize(rs.getString(18));
                x.setLongDescription(rs.getString(19));
                x.setShortDescription(rs.getString(20));
                x.setProductUpc(rs.getString(21));
                x.setPackUpc(rs.getString(22));
                x.setUnspscCode(rs.getString(23));
                x.setColor(rs.getString(24));
                x.setShippingWeight(rs.getInt(25));
                x.setWeightUnit(rs.getString(26));
                x.setNsn(rs.getInt(27));
                x.setShippingCubicSize(rs.getInt(28));
                x.setHazmat(rs.getString(29));
                x.setCertifiedCompanies(rs.getString(30));
                x.setImage(rs.getString(31));
                x.setImageBlob(rs.getBytes(32));
                x.setMsds(rs.getString(33));
                x.setMsdsBlob(rs.getBytes(34));
                x.setSpecification(rs.getString(35));
                x.setSpecificationBlob(rs.getBytes(36));
                x.setAssetName(rs.getString(37));
                x.setModelNumber(rs.getString(38));
                x.setAssocDoc1(rs.getString(39));
                x.setAssocDoc1Blob(rs.getBytes(40));
                x.setAssocDoc2(rs.getString(41));
                x.setAssocDoc2Blob(rs.getBytes(42));
                x.setAssocDoc3(rs.getString(43));
                x.setAssocDoc3Blob(rs.getBytes(44));
                x.setMatchedAssetId(rs.getInt(45));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a StagedAssetDataVector object of all
     * StagedAssetData objects in the database.
     * @param pCon An open database connection.
     * @return new StagedAssetDataVector()
     * @throws            SQLException
     */
    public static StagedAssetDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT STAGED_ASSET_ID,VERSION_NUMBER,ACTION,ASSET,STORE_ID,STORE_NAME,DIST_SKU,MFG_SKU,MANUFACTURER,DISTRIBUTOR,PACK,UOM,CATEGORY_NAME,SUB_CAT_1,SUB_CAT_2,SUB_CAT_3,MULTI_PRODUCT_NAME,ITEM_SIZE,LONG_DESCRIPTION,SHORT_DESCRIPTION,PRODUCT_UPC,PACK_UPC,UNSPSC_CODE,COLOR,SHIPPING_WEIGHT,WEIGHT_UNIT,NSN,SHIPPING_CUBIC_SIZE,HAZMAT,CERTIFIED_COMPANIES,IMAGE,IMAGE_BLOB,MSDS,MSDS_BLOB,SPECIFICATION,SPECIFICATION_BLOB,ASSET_NAME,MODEL_NUMBER,ASSOC_DOC_1,ASSOC_DOC_1_BLOB,ASSOC_DOC_2,ASSOC_DOC_2_BLOB,ASSOC_DOC_3,ASSOC_DOC_3_BLOB,MATCHED_ASSET_ID FROM CLW_STAGED_ASSET";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        StagedAssetDataVector v = new StagedAssetDataVector();
        StagedAssetData x = null;
        while (rs.next()) {
            // build the object
            x = StagedAssetData.createValue();
            
            x.setStagedAssetId(rs.getInt(1));
            x.setVersionNumber(rs.getInt(2));
            x.setAction(rs.getString(3));
            x.setAsset(rs.getString(4));
            x.setStoreId(rs.getInt(5));
            x.setStoreName(rs.getString(6));
            x.setDistSku(rs.getString(7));
            x.setMfgSku(rs.getString(8));
            x.setManufacturer(rs.getString(9));
            x.setDistributor(rs.getString(10));
            x.setPack(rs.getString(11));
            x.setUom(rs.getString(12));
            x.setCategoryName(rs.getString(13));
            x.setSubCat1(rs.getString(14));
            x.setSubCat2(rs.getString(15));
            x.setSubCat3(rs.getString(16));
            x.setMultiProductName(rs.getString(17));
            x.setItemSize(rs.getString(18));
            x.setLongDescription(rs.getString(19));
            x.setShortDescription(rs.getString(20));
            x.setProductUpc(rs.getString(21));
            x.setPackUpc(rs.getString(22));
            x.setUnspscCode(rs.getString(23));
            x.setColor(rs.getString(24));
            x.setShippingWeight(rs.getInt(25));
            x.setWeightUnit(rs.getString(26));
            x.setNsn(rs.getInt(27));
            x.setShippingCubicSize(rs.getInt(28));
            x.setHazmat(rs.getString(29));
            x.setCertifiedCompanies(rs.getString(30));
            x.setImage(rs.getString(31));
            x.setImageBlob(rs.getBytes(32));
            x.setMsds(rs.getString(33));
            x.setMsdsBlob(rs.getBytes(34));
            x.setSpecification(rs.getString(35));
            x.setSpecificationBlob(rs.getBytes(36));
            x.setAssetName(rs.getString(37));
            x.setModelNumber(rs.getString(38));
            x.setAssocDoc1(rs.getString(39));
            x.setAssocDoc1Blob(rs.getBytes(40));
            x.setAssocDoc2(rs.getString(41));
            x.setAssocDoc2Blob(rs.getBytes(42));
            x.setAssocDoc3(rs.getString(43));
            x.setAssocDoc3Blob(rs.getBytes(44));
            x.setMatchedAssetId(rs.getInt(45));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * StagedAssetData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT STAGED_ASSET_ID FROM CLW_STAGED_ASSET");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STAGED_ASSET");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STAGED_ASSET");
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
     * Inserts a StagedAssetData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StagedAssetData object to insert.
     * @return new StagedAssetData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static StagedAssetData insert(Connection pCon, StagedAssetData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_STAGED_ASSET_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_STAGED_ASSET_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setStagedAssetId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_STAGED_ASSET (STAGED_ASSET_ID,VERSION_NUMBER,ACTION,ASSET,STORE_ID,STORE_NAME,DIST_SKU,MFG_SKU,MANUFACTURER,DISTRIBUTOR,PACK,UOM,CATEGORY_NAME,SUB_CAT_1,SUB_CAT_2,SUB_CAT_3,MULTI_PRODUCT_NAME,ITEM_SIZE,LONG_DESCRIPTION,SHORT_DESCRIPTION,PRODUCT_UPC,PACK_UPC,UNSPSC_CODE,COLOR,SHIPPING_WEIGHT,WEIGHT_UNIT,NSN,SHIPPING_CUBIC_SIZE,HAZMAT,CERTIFIED_COMPANIES,IMAGE,IMAGE_BLOB,MSDS,MSDS_BLOB,SPECIFICATION,SPECIFICATION_BLOB,ASSET_NAME,MODEL_NUMBER,ASSOC_DOC_1,ASSOC_DOC_1_BLOB,ASSOC_DOC_2,ASSOC_DOC_2_BLOB,ASSOC_DOC_3,ASSOC_DOC_3_BLOB,MATCHED_ASSET_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pstmt.setInt(1,pData.getStagedAssetId());
        pstmt.setInt(2,pData.getVersionNumber());
        pstmt.setString(3,pData.getAction());
        pstmt.setString(4,pData.getAsset());
        pstmt.setInt(5,pData.getStoreId());
        pstmt.setString(6,pData.getStoreName());
        pstmt.setString(7,pData.getDistSku());
        pstmt.setString(8,pData.getMfgSku());
        pstmt.setString(9,pData.getManufacturer());
        pstmt.setString(10,pData.getDistributor());
        pstmt.setString(11,pData.getPack());
        pstmt.setString(12,pData.getUom());
        pstmt.setString(13,pData.getCategoryName());
        pstmt.setString(14,pData.getSubCat1());
        pstmt.setString(15,pData.getSubCat2());
        pstmt.setString(16,pData.getSubCat3());
        pstmt.setString(17,pData.getMultiProductName());
        pstmt.setString(18,pData.getItemSize());
        pstmt.setString(19,pData.getLongDescription());
        pstmt.setString(20,pData.getShortDescription());
        pstmt.setString(21,pData.getProductUpc());
        pstmt.setString(22,pData.getPackUpc());
        pstmt.setString(23,pData.getUnspscCode());
        pstmt.setString(24,pData.getColor());
        pstmt.setInt(25,pData.getShippingWeight());
        pstmt.setString(26,pData.getWeightUnit());
        pstmt.setInt(27,pData.getNsn());
        pstmt.setInt(28,pData.getShippingCubicSize());
        pstmt.setString(29,pData.getHazmat());
        pstmt.setString(30,pData.getCertifiedCompanies());
        pstmt.setString(31,pData.getImage());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(32, toBlob(pCon,pData.getImageBlob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getImageBlob());
                pstmt.setBinaryStream(32, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(33,pData.getMsds());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(34, toBlob(pCon,pData.getMsdsBlob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getMsdsBlob());
                pstmt.setBinaryStream(34, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(35,pData.getSpecification());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(36, toBlob(pCon,pData.getSpecificationBlob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getSpecificationBlob());
                pstmt.setBinaryStream(36, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(37,pData.getAssetName());
        pstmt.setString(38,pData.getModelNumber());
        pstmt.setString(39,pData.getAssocDoc1());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(40, toBlob(pCon,pData.getAssocDoc1Blob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getAssocDoc1Blob());
                pstmt.setBinaryStream(40, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(41,pData.getAssocDoc2());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(42, toBlob(pCon,pData.getAssocDoc2Blob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getAssocDoc2Blob());
                pstmt.setBinaryStream(42, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(43,pData.getAssocDoc3());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(44, toBlob(pCon,pData.getAssocDoc3Blob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getAssocDoc3Blob());
                pstmt.setBinaryStream(44, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setInt(45,pData.getMatchedAssetId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STAGED_ASSET_ID="+pData.getStagedAssetId());
            log.debug("SQL:   VERSION_NUMBER="+pData.getVersionNumber());
            log.debug("SQL:   ACTION="+pData.getAction());
            log.debug("SQL:   ASSET="+pData.getAsset());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   STORE_NAME="+pData.getStoreName());
            log.debug("SQL:   DIST_SKU="+pData.getDistSku());
            log.debug("SQL:   MFG_SKU="+pData.getMfgSku());
            log.debug("SQL:   MANUFACTURER="+pData.getManufacturer());
            log.debug("SQL:   DISTRIBUTOR="+pData.getDistributor());
            log.debug("SQL:   PACK="+pData.getPack());
            log.debug("SQL:   UOM="+pData.getUom());
            log.debug("SQL:   CATEGORY_NAME="+pData.getCategoryName());
            log.debug("SQL:   SUB_CAT_1="+pData.getSubCat1());
            log.debug("SQL:   SUB_CAT_2="+pData.getSubCat2());
            log.debug("SQL:   SUB_CAT_3="+pData.getSubCat3());
            log.debug("SQL:   MULTI_PRODUCT_NAME="+pData.getMultiProductName());
            log.debug("SQL:   ITEM_SIZE="+pData.getItemSize());
            log.debug("SQL:   LONG_DESCRIPTION="+pData.getLongDescription());
            log.debug("SQL:   SHORT_DESCRIPTION="+pData.getShortDescription());
            log.debug("SQL:   PRODUCT_UPC="+pData.getProductUpc());
            log.debug("SQL:   PACK_UPC="+pData.getPackUpc());
            log.debug("SQL:   UNSPSC_CODE="+pData.getUnspscCode());
            log.debug("SQL:   COLOR="+pData.getColor());
            log.debug("SQL:   SHIPPING_WEIGHT="+pData.getShippingWeight());
            log.debug("SQL:   WEIGHT_UNIT="+pData.getWeightUnit());
            log.debug("SQL:   NSN="+pData.getNsn());
            log.debug("SQL:   SHIPPING_CUBIC_SIZE="+pData.getShippingCubicSize());
            log.debug("SQL:   HAZMAT="+pData.getHazmat());
            log.debug("SQL:   CERTIFIED_COMPANIES="+pData.getCertifiedCompanies());
            log.debug("SQL:   IMAGE="+pData.getImage());
            log.debug("SQL:   IMAGE_BLOB="+pData.getImageBlob());
            log.debug("SQL:   MSDS="+pData.getMsds());
            log.debug("SQL:   MSDS_BLOB="+pData.getMsdsBlob());
            log.debug("SQL:   SPECIFICATION="+pData.getSpecification());
            log.debug("SQL:   SPECIFICATION_BLOB="+pData.getSpecificationBlob());
            log.debug("SQL:   ASSET_NAME="+pData.getAssetName());
            log.debug("SQL:   MODEL_NUMBER="+pData.getModelNumber());
            log.debug("SQL:   ASSOC_DOC_1="+pData.getAssocDoc1());
            log.debug("SQL:   ASSOC_DOC_1_BLOB="+pData.getAssocDoc1Blob());
            log.debug("SQL:   ASSOC_DOC_2="+pData.getAssocDoc2());
            log.debug("SQL:   ASSOC_DOC_2_BLOB="+pData.getAssocDoc2Blob());
            log.debug("SQL:   ASSOC_DOC_3="+pData.getAssocDoc3());
            log.debug("SQL:   ASSOC_DOC_3_BLOB="+pData.getAssocDoc3Blob());
            log.debug("SQL:   MATCHED_ASSET_ID="+pData.getMatchedAssetId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setStagedAssetId(0);
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
     * Updates a StagedAssetData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A StagedAssetData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, StagedAssetData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_STAGED_ASSET SET VERSION_NUMBER = ?,ACTION = ?,ASSET = ?,STORE_ID = ?,STORE_NAME = ?,DIST_SKU = ?,MFG_SKU = ?,MANUFACTURER = ?,DISTRIBUTOR = ?,PACK = ?,UOM = ?,CATEGORY_NAME = ?,SUB_CAT_1 = ?,SUB_CAT_2 = ?,SUB_CAT_3 = ?,MULTI_PRODUCT_NAME = ?,ITEM_SIZE = ?,LONG_DESCRIPTION = ?,SHORT_DESCRIPTION = ?,PRODUCT_UPC = ?,PACK_UPC = ?,UNSPSC_CODE = ?,COLOR = ?,SHIPPING_WEIGHT = ?,WEIGHT_UNIT = ?,NSN = ?,SHIPPING_CUBIC_SIZE = ?,HAZMAT = ?,CERTIFIED_COMPANIES = ?,IMAGE = ?,IMAGE_BLOB = ?,MSDS = ?,MSDS_BLOB = ?,SPECIFICATION = ?,SPECIFICATION_BLOB = ?,ASSET_NAME = ?,MODEL_NUMBER = ?,ASSOC_DOC_1 = ?,ASSOC_DOC_1_BLOB = ?,ASSOC_DOC_2 = ?,ASSOC_DOC_2_BLOB = ?,ASSOC_DOC_3 = ?,ASSOC_DOC_3_BLOB = ?,MATCHED_ASSET_ID = ? WHERE STAGED_ASSET_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setInt(i++,pData.getVersionNumber());
        pstmt.setString(i++,pData.getAction());
        pstmt.setString(i++,pData.getAsset());
        pstmt.setInt(i++,pData.getStoreId());
        pstmt.setString(i++,pData.getStoreName());
        pstmt.setString(i++,pData.getDistSku());
        pstmt.setString(i++,pData.getMfgSku());
        pstmt.setString(i++,pData.getManufacturer());
        pstmt.setString(i++,pData.getDistributor());
        pstmt.setString(i++,pData.getPack());
        pstmt.setString(i++,pData.getUom());
        pstmt.setString(i++,pData.getCategoryName());
        pstmt.setString(i++,pData.getSubCat1());
        pstmt.setString(i++,pData.getSubCat2());
        pstmt.setString(i++,pData.getSubCat3());
        pstmt.setString(i++,pData.getMultiProductName());
        pstmt.setString(i++,pData.getItemSize());
        pstmt.setString(i++,pData.getLongDescription());
        pstmt.setString(i++,pData.getShortDescription());
        pstmt.setString(i++,pData.getProductUpc());
        pstmt.setString(i++,pData.getPackUpc());
        pstmt.setString(i++,pData.getUnspscCode());
        pstmt.setString(i++,pData.getColor());
        pstmt.setInt(i++,pData.getShippingWeight());
        pstmt.setString(i++,pData.getWeightUnit());
        pstmt.setInt(i++,pData.getNsn());
        pstmt.setInt(i++,pData.getShippingCubicSize());
        pstmt.setString(i++,pData.getHazmat());
        pstmt.setString(i++,pData.getCertifiedCompanies());
        pstmt.setString(i++,pData.getImage());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getImageBlob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getImageBlob());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(i++,pData.getMsds());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getMsdsBlob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getMsdsBlob());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(i++,pData.getSpecification());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getSpecificationBlob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getSpecificationBlob());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(i++,pData.getAssetName());
        pstmt.setString(i++,pData.getModelNumber());
        pstmt.setString(i++,pData.getAssocDoc1());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getAssocDoc1Blob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getAssocDoc1Blob());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(i++,pData.getAssocDoc2());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getAssocDoc2Blob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getAssocDoc2Blob());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(i++,pData.getAssocDoc3());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getAssocDoc3Blob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getAssocDoc3Blob());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setInt(i++,pData.getMatchedAssetId());
        pstmt.setInt(i++,pData.getStagedAssetId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   VERSION_NUMBER="+pData.getVersionNumber());
            log.debug("SQL:   ACTION="+pData.getAction());
            log.debug("SQL:   ASSET="+pData.getAsset());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   STORE_NAME="+pData.getStoreName());
            log.debug("SQL:   DIST_SKU="+pData.getDistSku());
            log.debug("SQL:   MFG_SKU="+pData.getMfgSku());
            log.debug("SQL:   MANUFACTURER="+pData.getManufacturer());
            log.debug("SQL:   DISTRIBUTOR="+pData.getDistributor());
            log.debug("SQL:   PACK="+pData.getPack());
            log.debug("SQL:   UOM="+pData.getUom());
            log.debug("SQL:   CATEGORY_NAME="+pData.getCategoryName());
            log.debug("SQL:   SUB_CAT_1="+pData.getSubCat1());
            log.debug("SQL:   SUB_CAT_2="+pData.getSubCat2());
            log.debug("SQL:   SUB_CAT_3="+pData.getSubCat3());
            log.debug("SQL:   MULTI_PRODUCT_NAME="+pData.getMultiProductName());
            log.debug("SQL:   ITEM_SIZE="+pData.getItemSize());
            log.debug("SQL:   LONG_DESCRIPTION="+pData.getLongDescription());
            log.debug("SQL:   SHORT_DESCRIPTION="+pData.getShortDescription());
            log.debug("SQL:   PRODUCT_UPC="+pData.getProductUpc());
            log.debug("SQL:   PACK_UPC="+pData.getPackUpc());
            log.debug("SQL:   UNSPSC_CODE="+pData.getUnspscCode());
            log.debug("SQL:   COLOR="+pData.getColor());
            log.debug("SQL:   SHIPPING_WEIGHT="+pData.getShippingWeight());
            log.debug("SQL:   WEIGHT_UNIT="+pData.getWeightUnit());
            log.debug("SQL:   NSN="+pData.getNsn());
            log.debug("SQL:   SHIPPING_CUBIC_SIZE="+pData.getShippingCubicSize());
            log.debug("SQL:   HAZMAT="+pData.getHazmat());
            log.debug("SQL:   CERTIFIED_COMPANIES="+pData.getCertifiedCompanies());
            log.debug("SQL:   IMAGE="+pData.getImage());
            log.debug("SQL:   IMAGE_BLOB="+pData.getImageBlob());
            log.debug("SQL:   MSDS="+pData.getMsds());
            log.debug("SQL:   MSDS_BLOB="+pData.getMsdsBlob());
            log.debug("SQL:   SPECIFICATION="+pData.getSpecification());
            log.debug("SQL:   SPECIFICATION_BLOB="+pData.getSpecificationBlob());
            log.debug("SQL:   ASSET_NAME="+pData.getAssetName());
            log.debug("SQL:   MODEL_NUMBER="+pData.getModelNumber());
            log.debug("SQL:   ASSOC_DOC_1="+pData.getAssocDoc1());
            log.debug("SQL:   ASSOC_DOC_1_BLOB="+pData.getAssocDoc1Blob());
            log.debug("SQL:   ASSOC_DOC_2="+pData.getAssocDoc2());
            log.debug("SQL:   ASSOC_DOC_2_BLOB="+pData.getAssocDoc2Blob());
            log.debug("SQL:   ASSOC_DOC_3="+pData.getAssocDoc3());
            log.debug("SQL:   ASSOC_DOC_3_BLOB="+pData.getAssocDoc3Blob());
            log.debug("SQL:   MATCHED_ASSET_ID="+pData.getMatchedAssetId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a StagedAssetData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pStagedAssetId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pStagedAssetId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_STAGED_ASSET WHERE STAGED_ASSET_ID = " + pStagedAssetId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes StagedAssetData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_STAGED_ASSET");
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
     * Inserts a StagedAssetData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StagedAssetData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, StagedAssetData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_STAGED_ASSET (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "STAGED_ASSET_ID,VERSION_NUMBER,ACTION,ASSET,STORE_ID,STORE_NAME,DIST_SKU,MFG_SKU,MANUFACTURER,DISTRIBUTOR,PACK,UOM,CATEGORY_NAME,SUB_CAT_1,SUB_CAT_2,SUB_CAT_3,MULTI_PRODUCT_NAME,ITEM_SIZE,LONG_DESCRIPTION,SHORT_DESCRIPTION,PRODUCT_UPC,PACK_UPC,UNSPSC_CODE,COLOR,SHIPPING_WEIGHT,WEIGHT_UNIT,NSN,SHIPPING_CUBIC_SIZE,HAZMAT,CERTIFIED_COMPANIES,IMAGE,IMAGE_BLOB,MSDS,MSDS_BLOB,SPECIFICATION,SPECIFICATION_BLOB,ASSET_NAME,MODEL_NUMBER,ASSOC_DOC_1,ASSOC_DOC_1_BLOB,ASSOC_DOC_2,ASSOC_DOC_2_BLOB,ASSOC_DOC_3,ASSOC_DOC_3_BLOB,MATCHED_ASSET_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getStagedAssetId());
        pstmt.setInt(2+4,pData.getVersionNumber());
        pstmt.setString(3+4,pData.getAction());
        pstmt.setString(4+4,pData.getAsset());
        pstmt.setInt(5+4,pData.getStoreId());
        pstmt.setString(6+4,pData.getStoreName());
        pstmt.setString(7+4,pData.getDistSku());
        pstmt.setString(8+4,pData.getMfgSku());
        pstmt.setString(9+4,pData.getManufacturer());
        pstmt.setString(10+4,pData.getDistributor());
        pstmt.setString(11+4,pData.getPack());
        pstmt.setString(12+4,pData.getUom());
        pstmt.setString(13+4,pData.getCategoryName());
        pstmt.setString(14+4,pData.getSubCat1());
        pstmt.setString(15+4,pData.getSubCat2());
        pstmt.setString(16+4,pData.getSubCat3());
        pstmt.setString(17+4,pData.getMultiProductName());
        pstmt.setString(18+4,pData.getItemSize());
        pstmt.setString(19+4,pData.getLongDescription());
        pstmt.setString(20+4,pData.getShortDescription());
        pstmt.setString(21+4,pData.getProductUpc());
        pstmt.setString(22+4,pData.getPackUpc());
        pstmt.setString(23+4,pData.getUnspscCode());
        pstmt.setString(24+4,pData.getColor());
        pstmt.setInt(25+4,pData.getShippingWeight());
        pstmt.setString(26+4,pData.getWeightUnit());
        pstmt.setInt(27+4,pData.getNsn());
        pstmt.setInt(28+4,pData.getShippingCubicSize());
        pstmt.setString(29+4,pData.getHazmat());
        pstmt.setString(30+4,pData.getCertifiedCompanies());
        pstmt.setString(31+4,pData.getImage());
        pstmt.setBytes(32+4,pData.getImageBlob());
        pstmt.setString(33+4,pData.getMsds());
        pstmt.setBytes(34+4,pData.getMsdsBlob());
        pstmt.setString(35+4,pData.getSpecification());
        pstmt.setBytes(36+4,pData.getSpecificationBlob());
        pstmt.setString(37+4,pData.getAssetName());
        pstmt.setString(38+4,pData.getModelNumber());
        pstmt.setString(39+4,pData.getAssocDoc1());
        pstmt.setBytes(40+4,pData.getAssocDoc1Blob());
        pstmt.setString(41+4,pData.getAssocDoc2());
        pstmt.setBytes(42+4,pData.getAssocDoc2Blob());
        pstmt.setString(43+4,pData.getAssocDoc3());
        pstmt.setBytes(44+4,pData.getAssocDoc3Blob());
        pstmt.setInt(45+4,pData.getMatchedAssetId());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a StagedAssetData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StagedAssetData object to insert.
     * @param pLogFl  Creates record in log table if true
     * @return new StagedAssetData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static StagedAssetData insert(Connection pCon, StagedAssetData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a StagedAssetData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A StagedAssetData object to update. 
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, StagedAssetData pData, boolean pLogFl)
        throws SQLException {
        StagedAssetData oldData = null;
        if(pLogFl) {
          int id = pData.getStagedAssetId();
          try {
          oldData = StagedAssetDataAccess.select(pCon,id);
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
     * Deletes a StagedAssetData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pStagedAssetId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pStagedAssetId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_STAGED_ASSET SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_STAGED_ASSET d WHERE STAGED_ASSET_ID = " + pStagedAssetId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pStagedAssetId);
        return n;
     }

    /**
     * Deletes StagedAssetData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_STAGED_ASSET SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_STAGED_ASSET d ");
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


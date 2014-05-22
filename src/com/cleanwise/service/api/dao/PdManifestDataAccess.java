
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PdManifestDataAccess
 * Description:  This class is used to build access methods to the CLW_PD_MANIFEST table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PdManifestData;
import com.cleanwise.service.api.value.PdManifestDataVector;
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
 * <code>PdManifestDataAccess</code>
 */
public class PdManifestDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PdManifestDataAccess.class.getName());

    /** <code>CLW_PD_MANIFEST</code> table name */
	/* Primary key: PD_MANIFEST_ID */
	
    public static final String CLW_PD_MANIFEST = "CLW_PD_MANIFEST";
    
    /** <code>PD_MANIFEST_ID</code> PD_MANIFEST_ID column of table CLW_PD_MANIFEST */
    public static final String PD_MANIFEST_ID = "PD_MANIFEST_ID";
    /** <code>ACTUAL_WEIGHT</code> ACTUAL_WEIGHT column of table CLW_PD_MANIFEST */
    public static final String ACTUAL_WEIGHT = "ACTUAL_WEIGHT";
    /** <code>CUST_MANIFEST_ID</code> CUST_MANIFEST_ID column of table CLW_PD_MANIFEST */
    public static final String CUST_MANIFEST_ID = "CUST_MANIFEST_ID";
    /** <code>CUST_PRCL_ID</code> CUST_PRCL_ID column of table CLW_PD_MANIFEST */
    public static final String CUST_PRCL_ID = "CUST_PRCL_ID";
    /** <code>DEST_SRC_CD</code> DEST_SRC_CD column of table CLW_PD_MANIFEST */
    public static final String DEST_SRC_CD = "DEST_SRC_CD";
    /** <code>EST_WEIGHT</code> EST_WEIGHT column of table CLW_PD_MANIFEST */
    public static final String EST_WEIGHT = "EST_WEIGHT";
    /** <code>EXPECTED_FLAG</code> EXPECTED_FLAG column of table CLW_PD_MANIFEST */
    public static final String EXPECTED_FLAG = "EXPECTED_FLAG";
    /** <code>MANIFEST_GRP_TXT</code> MANIFEST_GRP_TXT column of table CLW_PD_MANIFEST */
    public static final String MANIFEST_GRP_TXT = "MANIFEST_GRP_TXT";
    /** <code>PHYSICAL_FLAG</code> PHYSICAL_FLAG column of table CLW_PD_MANIFEST */
    public static final String PHYSICAL_FLAG = "PHYSICAL_FLAG";
    /** <code>PRCL_CHRG</code> PRCL_CHRG column of table CLW_PD_MANIFEST */
    public static final String PRCL_CHRG = "PRCL_CHRG";
    /** <code>PRCS_CAT_CD</code> PRCS_CAT_CD column of table CLW_PD_MANIFEST */
    public static final String PRCS_CAT_CD = "PRCS_CAT_CD";
    /** <code>SBL_CD</code> SBL_CD column of table CLW_PD_MANIFEST */
    public static final String SBL_CD = "SBL_CD";
    /** <code>ZONE_CD</code> ZONE_CD column of table CLW_PD_MANIFEST */
    public static final String ZONE_CD = "ZONE_CD";
    /** <code>EST_SHIP_DATE</code> EST_SHIP_DATE column of table CLW_PD_MANIFEST */
    public static final String EST_SHIP_DATE = "EST_SHIP_DATE";
    /** <code>EST_SHIP_DATE_TYPE</code> EST_SHIP_DATE_TYPE column of table CLW_PD_MANIFEST */
    public static final String EST_SHIP_DATE_TYPE = "EST_SHIP_DATE_TYPE";
    /** <code>SRC_DOC</code> SRC_DOC column of table CLW_PD_MANIFEST */
    public static final String SRC_DOC = "SRC_DOC";
    /** <code>PURCHASE_ORDER_ID</code> PURCHASE_ORDER_ID column of table CLW_PD_MANIFEST */
    public static final String PURCHASE_ORDER_ID = "PURCHASE_ORDER_ID";
    /** <code>MATCHING_RECORDS</code> MATCHING_RECORDS column of table CLW_PD_MANIFEST */
    public static final String MATCHING_RECORDS = "MATCHING_RECORDS";
    /** <code>MATCH_TYPE</code> MATCH_TYPE column of table CLW_PD_MANIFEST */
    public static final String MATCH_TYPE = "MATCH_TYPE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PD_MANIFEST */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PD_MANIFEST */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PD_MANIFEST */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PD_MANIFEST */
    public static final String MOD_BY = "MOD_BY";
    /** <code>DISTRIBUTOR_ID</code> DISTRIBUTOR_ID column of table CLW_PD_MANIFEST */
    public static final String DISTRIBUTOR_ID = "DISTRIBUTOR_ID";
    /** <code>PURCHASE_ORDER_ID_NON_PD</code> PURCHASE_ORDER_ID_NON_PD column of table CLW_PD_MANIFEST */
    public static final String PURCHASE_ORDER_ID_NON_PD = "PURCHASE_ORDER_ID_NON_PD";
    /** <code>MATCHING_RECORDS_NON_PD</code> MATCHING_RECORDS_NON_PD column of table CLW_PD_MANIFEST */
    public static final String MATCHING_RECORDS_NON_PD = "MATCHING_RECORDS_NON_PD";

    /**
     * Constructor.
     */
    public PdManifestDataAccess()
    {
    }

    /**
     * Gets a PdManifestData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPdManifestId The key requested.
     * @return new PdManifestData()
     * @throws            SQLException
     */
    public static PdManifestData select(Connection pCon, int pPdManifestId)
        throws SQLException, DataNotFoundException {
        PdManifestData x=null;
        String sql="SELECT PD_MANIFEST_ID,ACTUAL_WEIGHT,CUST_MANIFEST_ID,CUST_PRCL_ID,DEST_SRC_CD,EST_WEIGHT,EXPECTED_FLAG,MANIFEST_GRP_TXT,PHYSICAL_FLAG,PRCL_CHRG,PRCS_CAT_CD,SBL_CD,ZONE_CD,EST_SHIP_DATE,EST_SHIP_DATE_TYPE,SRC_DOC,PURCHASE_ORDER_ID,MATCHING_RECORDS,MATCH_TYPE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISTRIBUTOR_ID,PURCHASE_ORDER_ID_NON_PD,MATCHING_RECORDS_NON_PD FROM CLW_PD_MANIFEST WHERE PD_MANIFEST_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPdManifestId=" + pPdManifestId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPdManifestId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PdManifestData.createValue();
            
            x.setPdManifestId(rs.getInt(1));
            x.setActualWeight(rs.getString(2));
            x.setCustManifestId(rs.getString(3));
            x.setCustPrclId(rs.getString(4));
            x.setDestSrcCd(rs.getString(5));
            x.setEstWeight(rs.getString(6));
            x.setExpectedFlag(rs.getString(7));
            x.setManifestGrpTxt(rs.getString(8));
            x.setPhysicalFlag(rs.getString(9));
            x.setPrclChrg(rs.getString(10));
            x.setPrcsCatCd(rs.getString(11));
            x.setSblCd(rs.getString(12));
            x.setZoneCd(rs.getString(13));
            x.setEstShipDate(rs.getDate(14));
            x.setEstShipDateType(rs.getString(15));
            x.setSrcDoc(rs.getString(16));
            x.setPurchaseOrderId(rs.getInt(17));
            x.setMatchingRecords(rs.getInt(18));
            x.setMatchType(rs.getString(19));
            x.setAddDate(rs.getTimestamp(20));
            x.setAddBy(rs.getString(21));
            x.setModDate(rs.getTimestamp(22));
            x.setModBy(rs.getString(23));
            x.setDistributorId(rs.getInt(24));
            x.setPurchaseOrderIdNonPd(rs.getInt(25));
            x.setMatchingRecordsNonPd(rs.getInt(26));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PD_MANIFEST_ID :" + pPdManifestId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PdManifestDataVector object that consists
     * of PdManifestData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PdManifestDataVector()
     * @throws            SQLException
     */
    public static PdManifestDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PdManifestData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PD_MANIFEST.PD_MANIFEST_ID,CLW_PD_MANIFEST.ACTUAL_WEIGHT,CLW_PD_MANIFEST.CUST_MANIFEST_ID,CLW_PD_MANIFEST.CUST_PRCL_ID,CLW_PD_MANIFEST.DEST_SRC_CD,CLW_PD_MANIFEST.EST_WEIGHT,CLW_PD_MANIFEST.EXPECTED_FLAG,CLW_PD_MANIFEST.MANIFEST_GRP_TXT,CLW_PD_MANIFEST.PHYSICAL_FLAG,CLW_PD_MANIFEST.PRCL_CHRG,CLW_PD_MANIFEST.PRCS_CAT_CD,CLW_PD_MANIFEST.SBL_CD,CLW_PD_MANIFEST.ZONE_CD,CLW_PD_MANIFEST.EST_SHIP_DATE,CLW_PD_MANIFEST.EST_SHIP_DATE_TYPE,CLW_PD_MANIFEST.SRC_DOC,CLW_PD_MANIFEST.PURCHASE_ORDER_ID,CLW_PD_MANIFEST.MATCHING_RECORDS,CLW_PD_MANIFEST.MATCH_TYPE,CLW_PD_MANIFEST.ADD_DATE,CLW_PD_MANIFEST.ADD_BY,CLW_PD_MANIFEST.MOD_DATE,CLW_PD_MANIFEST.MOD_BY,CLW_PD_MANIFEST.DISTRIBUTOR_ID,CLW_PD_MANIFEST.PURCHASE_ORDER_ID_NON_PD,CLW_PD_MANIFEST.MATCHING_RECORDS_NON_PD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PdManifestData Object.
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
    *@returns a populated PdManifestData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PdManifestData x = PdManifestData.createValue();
         
         x.setPdManifestId(rs.getInt(1+offset));
         x.setActualWeight(rs.getString(2+offset));
         x.setCustManifestId(rs.getString(3+offset));
         x.setCustPrclId(rs.getString(4+offset));
         x.setDestSrcCd(rs.getString(5+offset));
         x.setEstWeight(rs.getString(6+offset));
         x.setExpectedFlag(rs.getString(7+offset));
         x.setManifestGrpTxt(rs.getString(8+offset));
         x.setPhysicalFlag(rs.getString(9+offset));
         x.setPrclChrg(rs.getString(10+offset));
         x.setPrcsCatCd(rs.getString(11+offset));
         x.setSblCd(rs.getString(12+offset));
         x.setZoneCd(rs.getString(13+offset));
         x.setEstShipDate(rs.getDate(14+offset));
         x.setEstShipDateType(rs.getString(15+offset));
         x.setSrcDoc(rs.getString(16+offset));
         x.setPurchaseOrderId(rs.getInt(17+offset));
         x.setMatchingRecords(rs.getInt(18+offset));
         x.setMatchType(rs.getString(19+offset));
         x.setAddDate(rs.getTimestamp(20+offset));
         x.setAddBy(rs.getString(21+offset));
         x.setModDate(rs.getTimestamp(22+offset));
         x.setModBy(rs.getString(23+offset));
         x.setDistributorId(rs.getInt(24+offset));
         x.setPurchaseOrderIdNonPd(rs.getInt(25+offset));
         x.setMatchingRecordsNonPd(rs.getInt(26+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PdManifestData Object represents.
    */
    public int getColumnCount(){
        return 26;
    }

    /**
     * Gets a PdManifestDataVector object that consists
     * of PdManifestData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PdManifestDataVector()
     * @throws            SQLException
     */
    public static PdManifestDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PD_MANIFEST_ID,ACTUAL_WEIGHT,CUST_MANIFEST_ID,CUST_PRCL_ID,DEST_SRC_CD,EST_WEIGHT,EXPECTED_FLAG,MANIFEST_GRP_TXT,PHYSICAL_FLAG,PRCL_CHRG,PRCS_CAT_CD,SBL_CD,ZONE_CD,EST_SHIP_DATE,EST_SHIP_DATE_TYPE,SRC_DOC,PURCHASE_ORDER_ID,MATCHING_RECORDS,MATCH_TYPE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISTRIBUTOR_ID,PURCHASE_ORDER_ID_NON_PD,MATCHING_RECORDS_NON_PD FROM CLW_PD_MANIFEST");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PD_MANIFEST.PD_MANIFEST_ID,CLW_PD_MANIFEST.ACTUAL_WEIGHT,CLW_PD_MANIFEST.CUST_MANIFEST_ID,CLW_PD_MANIFEST.CUST_PRCL_ID,CLW_PD_MANIFEST.DEST_SRC_CD,CLW_PD_MANIFEST.EST_WEIGHT,CLW_PD_MANIFEST.EXPECTED_FLAG,CLW_PD_MANIFEST.MANIFEST_GRP_TXT,CLW_PD_MANIFEST.PHYSICAL_FLAG,CLW_PD_MANIFEST.PRCL_CHRG,CLW_PD_MANIFEST.PRCS_CAT_CD,CLW_PD_MANIFEST.SBL_CD,CLW_PD_MANIFEST.ZONE_CD,CLW_PD_MANIFEST.EST_SHIP_DATE,CLW_PD_MANIFEST.EST_SHIP_DATE_TYPE,CLW_PD_MANIFEST.SRC_DOC,CLW_PD_MANIFEST.PURCHASE_ORDER_ID,CLW_PD_MANIFEST.MATCHING_RECORDS,CLW_PD_MANIFEST.MATCH_TYPE,CLW_PD_MANIFEST.ADD_DATE,CLW_PD_MANIFEST.ADD_BY,CLW_PD_MANIFEST.MOD_DATE,CLW_PD_MANIFEST.MOD_BY,CLW_PD_MANIFEST.DISTRIBUTOR_ID,CLW_PD_MANIFEST.PURCHASE_ORDER_ID_NON_PD,CLW_PD_MANIFEST.MATCHING_RECORDS_NON_PD FROM CLW_PD_MANIFEST");
                where = pCriteria.getSqlClause("CLW_PD_MANIFEST");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PD_MANIFEST.equals(otherTable)){
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
        PdManifestDataVector v = new PdManifestDataVector();
        while (rs.next()) {
            PdManifestData x = PdManifestData.createValue();
            
            x.setPdManifestId(rs.getInt(1));
            x.setActualWeight(rs.getString(2));
            x.setCustManifestId(rs.getString(3));
            x.setCustPrclId(rs.getString(4));
            x.setDestSrcCd(rs.getString(5));
            x.setEstWeight(rs.getString(6));
            x.setExpectedFlag(rs.getString(7));
            x.setManifestGrpTxt(rs.getString(8));
            x.setPhysicalFlag(rs.getString(9));
            x.setPrclChrg(rs.getString(10));
            x.setPrcsCatCd(rs.getString(11));
            x.setSblCd(rs.getString(12));
            x.setZoneCd(rs.getString(13));
            x.setEstShipDate(rs.getDate(14));
            x.setEstShipDateType(rs.getString(15));
            x.setSrcDoc(rs.getString(16));
            x.setPurchaseOrderId(rs.getInt(17));
            x.setMatchingRecords(rs.getInt(18));
            x.setMatchType(rs.getString(19));
            x.setAddDate(rs.getTimestamp(20));
            x.setAddBy(rs.getString(21));
            x.setModDate(rs.getTimestamp(22));
            x.setModBy(rs.getString(23));
            x.setDistributorId(rs.getInt(24));
            x.setPurchaseOrderIdNonPd(rs.getInt(25));
            x.setMatchingRecordsNonPd(rs.getInt(26));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a PdManifestDataVector object that consists
     * of PdManifestData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PdManifestData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PdManifestDataVector()
     * @throws            SQLException
     */
    public static PdManifestDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PdManifestDataVector v = new PdManifestDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PD_MANIFEST_ID,ACTUAL_WEIGHT,CUST_MANIFEST_ID,CUST_PRCL_ID,DEST_SRC_CD,EST_WEIGHT,EXPECTED_FLAG,MANIFEST_GRP_TXT,PHYSICAL_FLAG,PRCL_CHRG,PRCS_CAT_CD,SBL_CD,ZONE_CD,EST_SHIP_DATE,EST_SHIP_DATE_TYPE,SRC_DOC,PURCHASE_ORDER_ID,MATCHING_RECORDS,MATCH_TYPE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISTRIBUTOR_ID,PURCHASE_ORDER_ID_NON_PD,MATCHING_RECORDS_NON_PD FROM CLW_PD_MANIFEST WHERE PD_MANIFEST_ID IN (");

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
            PdManifestData x=null;
            while (rs.next()) {
                // build the object
                x=PdManifestData.createValue();
                
                x.setPdManifestId(rs.getInt(1));
                x.setActualWeight(rs.getString(2));
                x.setCustManifestId(rs.getString(3));
                x.setCustPrclId(rs.getString(4));
                x.setDestSrcCd(rs.getString(5));
                x.setEstWeight(rs.getString(6));
                x.setExpectedFlag(rs.getString(7));
                x.setManifestGrpTxt(rs.getString(8));
                x.setPhysicalFlag(rs.getString(9));
                x.setPrclChrg(rs.getString(10));
                x.setPrcsCatCd(rs.getString(11));
                x.setSblCd(rs.getString(12));
                x.setZoneCd(rs.getString(13));
                x.setEstShipDate(rs.getDate(14));
                x.setEstShipDateType(rs.getString(15));
                x.setSrcDoc(rs.getString(16));
                x.setPurchaseOrderId(rs.getInt(17));
                x.setMatchingRecords(rs.getInt(18));
                x.setMatchType(rs.getString(19));
                x.setAddDate(rs.getTimestamp(20));
                x.setAddBy(rs.getString(21));
                x.setModDate(rs.getTimestamp(22));
                x.setModBy(rs.getString(23));
                x.setDistributorId(rs.getInt(24));
                x.setPurchaseOrderIdNonPd(rs.getInt(25));
                x.setMatchingRecordsNonPd(rs.getInt(26));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a PdManifestDataVector object of all
     * PdManifestData objects in the database.
     * @param pCon An open database connection.
     * @return new PdManifestDataVector()
     * @throws            SQLException
     */
    public static PdManifestDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PD_MANIFEST_ID,ACTUAL_WEIGHT,CUST_MANIFEST_ID,CUST_PRCL_ID,DEST_SRC_CD,EST_WEIGHT,EXPECTED_FLAG,MANIFEST_GRP_TXT,PHYSICAL_FLAG,PRCL_CHRG,PRCS_CAT_CD,SBL_CD,ZONE_CD,EST_SHIP_DATE,EST_SHIP_DATE_TYPE,SRC_DOC,PURCHASE_ORDER_ID,MATCHING_RECORDS,MATCH_TYPE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISTRIBUTOR_ID,PURCHASE_ORDER_ID_NON_PD,MATCHING_RECORDS_NON_PD FROM CLW_PD_MANIFEST";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PdManifestDataVector v = new PdManifestDataVector();
        PdManifestData x = null;
        while (rs.next()) {
            // build the object
            x = PdManifestData.createValue();
            
            x.setPdManifestId(rs.getInt(1));
            x.setActualWeight(rs.getString(2));
            x.setCustManifestId(rs.getString(3));
            x.setCustPrclId(rs.getString(4));
            x.setDestSrcCd(rs.getString(5));
            x.setEstWeight(rs.getString(6));
            x.setExpectedFlag(rs.getString(7));
            x.setManifestGrpTxt(rs.getString(8));
            x.setPhysicalFlag(rs.getString(9));
            x.setPrclChrg(rs.getString(10));
            x.setPrcsCatCd(rs.getString(11));
            x.setSblCd(rs.getString(12));
            x.setZoneCd(rs.getString(13));
            x.setEstShipDate(rs.getDate(14));
            x.setEstShipDateType(rs.getString(15));
            x.setSrcDoc(rs.getString(16));
            x.setPurchaseOrderId(rs.getInt(17));
            x.setMatchingRecords(rs.getInt(18));
            x.setMatchType(rs.getString(19));
            x.setAddDate(rs.getTimestamp(20));
            x.setAddBy(rs.getString(21));
            x.setModDate(rs.getTimestamp(22));
            x.setModBy(rs.getString(23));
            x.setDistributorId(rs.getInt(24));
            x.setPurchaseOrderIdNonPd(rs.getInt(25));
            x.setMatchingRecordsNonPd(rs.getInt(26));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * PdManifestData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PD_MANIFEST_ID FROM CLW_PD_MANIFEST");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PD_MANIFEST");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PD_MANIFEST");
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
     * Inserts a PdManifestData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PdManifestData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new PdManifestData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PdManifestData insert(Connection pCon, PdManifestData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PD_MANIFEST_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PD_MANIFEST_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPdManifestId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PD_MANIFEST (PD_MANIFEST_ID,ACTUAL_WEIGHT,CUST_MANIFEST_ID,CUST_PRCL_ID,DEST_SRC_CD,EST_WEIGHT,EXPECTED_FLAG,MANIFEST_GRP_TXT,PHYSICAL_FLAG,PRCL_CHRG,PRCS_CAT_CD,SBL_CD,ZONE_CD,EST_SHIP_DATE,EST_SHIP_DATE_TYPE,SRC_DOC,PURCHASE_ORDER_ID,MATCHING_RECORDS,MATCH_TYPE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISTRIBUTOR_ID,PURCHASE_ORDER_ID_NON_PD,MATCHING_RECORDS_NON_PD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getPdManifestId());
        pstmt.setString(2,pData.getActualWeight());
        pstmt.setString(3,pData.getCustManifestId());
        pstmt.setString(4,pData.getCustPrclId());
        pstmt.setString(5,pData.getDestSrcCd());
        pstmt.setString(6,pData.getEstWeight());
        pstmt.setString(7,pData.getExpectedFlag());
        pstmt.setString(8,pData.getManifestGrpTxt());
        pstmt.setString(9,pData.getPhysicalFlag());
        pstmt.setString(10,pData.getPrclChrg());
        pstmt.setString(11,pData.getPrcsCatCd());
        pstmt.setString(12,pData.getSblCd());
        pstmt.setString(13,pData.getZoneCd());
        pstmt.setDate(14,DBAccess.toSQLDate(pData.getEstShipDate()));
        pstmt.setString(15,pData.getEstShipDateType());
        pstmt.setString(16,pData.getSrcDoc());
        pstmt.setInt(17,pData.getPurchaseOrderId());
        pstmt.setInt(18,pData.getMatchingRecords());
        pstmt.setString(19,pData.getMatchType());
        pstmt.setTimestamp(20,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(21,pData.getAddBy());
        pstmt.setTimestamp(22,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(23,pData.getModBy());
        pstmt.setInt(24,pData.getDistributorId());
        pstmt.setInt(25,pData.getPurchaseOrderIdNonPd());
        pstmt.setInt(26,pData.getMatchingRecordsNonPd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PD_MANIFEST_ID="+pData.getPdManifestId());
            log.debug("SQL:   ACTUAL_WEIGHT="+pData.getActualWeight());
            log.debug("SQL:   CUST_MANIFEST_ID="+pData.getCustManifestId());
            log.debug("SQL:   CUST_PRCL_ID="+pData.getCustPrclId());
            log.debug("SQL:   DEST_SRC_CD="+pData.getDestSrcCd());
            log.debug("SQL:   EST_WEIGHT="+pData.getEstWeight());
            log.debug("SQL:   EXPECTED_FLAG="+pData.getExpectedFlag());
            log.debug("SQL:   MANIFEST_GRP_TXT="+pData.getManifestGrpTxt());
            log.debug("SQL:   PHYSICAL_FLAG="+pData.getPhysicalFlag());
            log.debug("SQL:   PRCL_CHRG="+pData.getPrclChrg());
            log.debug("SQL:   PRCS_CAT_CD="+pData.getPrcsCatCd());
            log.debug("SQL:   SBL_CD="+pData.getSblCd());
            log.debug("SQL:   ZONE_CD="+pData.getZoneCd());
            log.debug("SQL:   EST_SHIP_DATE="+pData.getEstShipDate());
            log.debug("SQL:   EST_SHIP_DATE_TYPE="+pData.getEstShipDateType());
            log.debug("SQL:   SRC_DOC="+pData.getSrcDoc());
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL:   MATCHING_RECORDS="+pData.getMatchingRecords());
            log.debug("SQL:   MATCH_TYPE="+pData.getMatchType());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   DISTRIBUTOR_ID="+pData.getDistributorId());
            log.debug("SQL:   PURCHASE_ORDER_ID_NON_PD="+pData.getPurchaseOrderIdNonPd());
            log.debug("SQL:   MATCHING_RECORDS_NON_PD="+pData.getMatchingRecordsNonPd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setPdManifestId(0);
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
     * Updates a PdManifestData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PdManifestData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PdManifestData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PD_MANIFEST SET ACTUAL_WEIGHT = ?,CUST_MANIFEST_ID = ?,CUST_PRCL_ID = ?,DEST_SRC_CD = ?,EST_WEIGHT = ?,EXPECTED_FLAG = ?,MANIFEST_GRP_TXT = ?,PHYSICAL_FLAG = ?,PRCL_CHRG = ?,PRCS_CAT_CD = ?,SBL_CD = ?,ZONE_CD = ?,EST_SHIP_DATE = ?,EST_SHIP_DATE_TYPE = ?,SRC_DOC = ?,PURCHASE_ORDER_ID = ?,MATCHING_RECORDS = ?,MATCH_TYPE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,DISTRIBUTOR_ID = ?,PURCHASE_ORDER_ID_NON_PD = ?,MATCHING_RECORDS_NON_PD = ? WHERE PD_MANIFEST_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getActualWeight());
        pstmt.setString(i++,pData.getCustManifestId());
        pstmt.setString(i++,pData.getCustPrclId());
        pstmt.setString(i++,pData.getDestSrcCd());
        pstmt.setString(i++,pData.getEstWeight());
        pstmt.setString(i++,pData.getExpectedFlag());
        pstmt.setString(i++,pData.getManifestGrpTxt());
        pstmt.setString(i++,pData.getPhysicalFlag());
        pstmt.setString(i++,pData.getPrclChrg());
        pstmt.setString(i++,pData.getPrcsCatCd());
        pstmt.setString(i++,pData.getSblCd());
        pstmt.setString(i++,pData.getZoneCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEstShipDate()));
        pstmt.setString(i++,pData.getEstShipDateType());
        pstmt.setString(i++,pData.getSrcDoc());
        pstmt.setInt(i++,pData.getPurchaseOrderId());
        pstmt.setInt(i++,pData.getMatchingRecords());
        pstmt.setString(i++,pData.getMatchType());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getDistributorId());
        pstmt.setInt(i++,pData.getPurchaseOrderIdNonPd());
        pstmt.setInt(i++,pData.getMatchingRecordsNonPd());
        pstmt.setInt(i++,pData.getPdManifestId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ACTUAL_WEIGHT="+pData.getActualWeight());
            log.debug("SQL:   CUST_MANIFEST_ID="+pData.getCustManifestId());
            log.debug("SQL:   CUST_PRCL_ID="+pData.getCustPrclId());
            log.debug("SQL:   DEST_SRC_CD="+pData.getDestSrcCd());
            log.debug("SQL:   EST_WEIGHT="+pData.getEstWeight());
            log.debug("SQL:   EXPECTED_FLAG="+pData.getExpectedFlag());
            log.debug("SQL:   MANIFEST_GRP_TXT="+pData.getManifestGrpTxt());
            log.debug("SQL:   PHYSICAL_FLAG="+pData.getPhysicalFlag());
            log.debug("SQL:   PRCL_CHRG="+pData.getPrclChrg());
            log.debug("SQL:   PRCS_CAT_CD="+pData.getPrcsCatCd());
            log.debug("SQL:   SBL_CD="+pData.getSblCd());
            log.debug("SQL:   ZONE_CD="+pData.getZoneCd());
            log.debug("SQL:   EST_SHIP_DATE="+pData.getEstShipDate());
            log.debug("SQL:   EST_SHIP_DATE_TYPE="+pData.getEstShipDateType());
            log.debug("SQL:   SRC_DOC="+pData.getSrcDoc());
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL:   MATCHING_RECORDS="+pData.getMatchingRecords());
            log.debug("SQL:   MATCH_TYPE="+pData.getMatchType());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   DISTRIBUTOR_ID="+pData.getDistributorId());
            log.debug("SQL:   PURCHASE_ORDER_ID_NON_PD="+pData.getPurchaseOrderIdNonPd());
            log.debug("SQL:   MATCHING_RECORDS_NON_PD="+pData.getMatchingRecordsNonPd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a PdManifestData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPdManifestId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPdManifestId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PD_MANIFEST WHERE PD_MANIFEST_ID = " + pPdManifestId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PdManifestData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PD_MANIFEST");
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
     * Inserts a PdManifestData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PdManifestData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PdManifestData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PD_MANIFEST (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PD_MANIFEST_ID,ACTUAL_WEIGHT,CUST_MANIFEST_ID,CUST_PRCL_ID,DEST_SRC_CD,EST_WEIGHT,EXPECTED_FLAG,MANIFEST_GRP_TXT,PHYSICAL_FLAG,PRCL_CHRG,PRCS_CAT_CD,SBL_CD,ZONE_CD,EST_SHIP_DATE,EST_SHIP_DATE_TYPE,SRC_DOC,PURCHASE_ORDER_ID,MATCHING_RECORDS,MATCH_TYPE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISTRIBUTOR_ID,PURCHASE_ORDER_ID_NON_PD,MATCHING_RECORDS_NON_PD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPdManifestId());
        pstmt.setString(2+4,pData.getActualWeight());
        pstmt.setString(3+4,pData.getCustManifestId());
        pstmt.setString(4+4,pData.getCustPrclId());
        pstmt.setString(5+4,pData.getDestSrcCd());
        pstmt.setString(6+4,pData.getEstWeight());
        pstmt.setString(7+4,pData.getExpectedFlag());
        pstmt.setString(8+4,pData.getManifestGrpTxt());
        pstmt.setString(9+4,pData.getPhysicalFlag());
        pstmt.setString(10+4,pData.getPrclChrg());
        pstmt.setString(11+4,pData.getPrcsCatCd());
        pstmt.setString(12+4,pData.getSblCd());
        pstmt.setString(13+4,pData.getZoneCd());
        pstmt.setDate(14+4,DBAccess.toSQLDate(pData.getEstShipDate()));
        pstmt.setString(15+4,pData.getEstShipDateType());
        pstmt.setString(16+4,pData.getSrcDoc());
        pstmt.setInt(17+4,pData.getPurchaseOrderId());
        pstmt.setInt(18+4,pData.getMatchingRecords());
        pstmt.setString(19+4,pData.getMatchType());
        pstmt.setTimestamp(20+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(21+4,pData.getAddBy());
        pstmt.setTimestamp(22+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(23+4,pData.getModBy());
        pstmt.setInt(24+4,pData.getDistributorId());
        pstmt.setInt(25+4,pData.getPurchaseOrderIdNonPd());
        pstmt.setInt(26+4,pData.getMatchingRecordsNonPd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PdManifestData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PdManifestData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new PdManifestData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PdManifestData insert(Connection pCon, PdManifestData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PdManifestData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PdManifestData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PdManifestData pData, boolean pLogFl)
        throws SQLException {
        PdManifestData oldData = null;
        if(pLogFl) {
          int id = pData.getPdManifestId();
          try {
          oldData = PdManifestDataAccess.select(pCon,id);
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
     * Deletes a PdManifestData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPdManifestId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPdManifestId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PD_MANIFEST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PD_MANIFEST d WHERE PD_MANIFEST_ID = " + pPdManifestId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPdManifestId);
        return n;
     }

    /**
     * Deletes PdManifestData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PD_MANIFEST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PD_MANIFEST d ");
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



/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        EstimatorFacilityDataAccess
 * Description:  This class is used to build access methods to the CLW_ESTIMATOR_FACILITY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.EstimatorFacilityData;
import com.cleanwise.service.api.value.EstimatorFacilityDataVector;
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
 * <code>EstimatorFacilityDataAccess</code>
 */
public class EstimatorFacilityDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(EstimatorFacilityDataAccess.class.getName());

    /** <code>CLW_ESTIMATOR_FACILITY</code> table name */
	/* Primary key: ESTIMATOR_FACILITY_ID */
	
    public static final String CLW_ESTIMATOR_FACILITY = "CLW_ESTIMATOR_FACILITY";
    
    /** <code>ESTIMATOR_FACILITY_ID</code> ESTIMATOR_FACILITY_ID column of table CLW_ESTIMATOR_FACILITY */
    public static final String ESTIMATOR_FACILITY_ID = "ESTIMATOR_FACILITY_ID";
    /** <code>CATALOG_ID</code> CATALOG_ID column of table CLW_ESTIMATOR_FACILITY */
    public static final String CATALOG_ID = "CATALOG_ID";
    /** <code>NAME</code> NAME column of table CLW_ESTIMATOR_FACILITY */
    public static final String NAME = "NAME";
    /** <code>FACILITY_QTY</code> FACILITY_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String FACILITY_QTY = "FACILITY_QTY";
    /** <code>WORKING_DAY_YEAR_QTY</code> WORKING_DAY_YEAR_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String WORKING_DAY_YEAR_QTY = "WORKING_DAY_YEAR_QTY";
    /** <code>FACILITY_TYPE_CD</code> FACILITY_TYPE_CD column of table CLW_ESTIMATOR_FACILITY */
    public static final String FACILITY_TYPE_CD = "FACILITY_TYPE_CD";
    /** <code>STATION_QTY</code> STATION_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String STATION_QTY = "STATION_QTY";
    /** <code>APPEARANCE_STANDARD_CD</code> APPEARANCE_STANDARD_CD column of table CLW_ESTIMATOR_FACILITY */
    public static final String APPEARANCE_STANDARD_CD = "APPEARANCE_STANDARD_CD";
    /** <code>PERSONNEL_QTY</code> PERSONNEL_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String PERSONNEL_QTY = "PERSONNEL_QTY";
    /** <code>VISITOR_QTY</code> VISITOR_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String VISITOR_QTY = "VISITOR_QTY";
    /** <code>BATHROOM_QTY</code> BATHROOM_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String BATHROOM_QTY = "BATHROOM_QTY";
    /** <code>TOILET_BATHROOM_QTY</code> TOILET_BATHROOM_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String TOILET_BATHROOM_QTY = "TOILET_BATHROOM_QTY";
    /** <code>SHOWER_QTY</code> SHOWER_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String SHOWER_QTY = "SHOWER_QTY";
    /** <code>VISITOR_BATHROOM_PERCENT</code> VISITOR_BATHROOM_PERCENT column of table CLW_ESTIMATOR_FACILITY */
    public static final String VISITOR_BATHROOM_PERCENT = "VISITOR_BATHROOM_PERCENT";
    /** <code>WASH_HAND_QTY</code> WASH_HAND_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String WASH_HAND_QTY = "WASH_HAND_QTY";
    /** <code>TISSUE_USAGE_QTY</code> TISSUE_USAGE_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String TISSUE_USAGE_QTY = "TISSUE_USAGE_QTY";
    /** <code>RECEPTACLE_LINER_RATIO</code> RECEPTACLE_LINER_RATIO column of table CLW_ESTIMATOR_FACILITY */
    public static final String RECEPTACLE_LINER_RATIO = "RECEPTACLE_LINER_RATIO";
    /** <code>LINER_RATIO_BASE_CD</code> LINER_RATIO_BASE_CD column of table CLW_ESTIMATOR_FACILITY */
    public static final String LINER_RATIO_BASE_CD = "LINER_RATIO_BASE_CD";
    /** <code>ADDITIONAL_LINER_RATIO</code> ADDITIONAL_LINER_RATIO column of table CLW_ESTIMATOR_FACILITY */
    public static final String ADDITIONAL_LINER_RATIO = "ADDITIONAL_LINER_RATIO";
    /** <code>TOILET_LINER_RATIO</code> TOILET_LINER_RATIO column of table CLW_ESTIMATOR_FACILITY */
    public static final String TOILET_LINER_RATIO = "TOILET_LINER_RATIO";
    /** <code>LARGE_LINER_RATIO</code> LARGE_LINER_RATIO column of table CLW_ESTIMATOR_FACILITY */
    public static final String LARGE_LINER_RATIO = "LARGE_LINER_RATIO";
    /** <code>GROSS_FOOTAGE</code> GROSS_FOOTAGE column of table CLW_ESTIMATOR_FACILITY */
    public static final String GROSS_FOOTAGE = "GROSS_FOOTAGE";
    /** <code>CLEANABLE_FOOTAGE_PERCENT</code> CLEANABLE_FOOTAGE_PERCENT column of table CLW_ESTIMATOR_FACILITY */
    public static final String CLEANABLE_FOOTAGE_PERCENT = "CLEANABLE_FOOTAGE_PERCENT";
    /** <code>ESTIMATED_ITEMS_FACTOR</code> ESTIMATED_ITEMS_FACTOR column of table CLW_ESTIMATOR_FACILITY */
    public static final String ESTIMATED_ITEMS_FACTOR = "ESTIMATED_ITEMS_FACTOR";
    /** <code>BASEBOARD_PERCENT</code> BASEBOARD_PERCENT column of table CLW_ESTIMATOR_FACILITY */
    public static final String BASEBOARD_PERCENT = "BASEBOARD_PERCENT";
    /** <code>FACILITY_STATUS_CD</code> FACILITY_STATUS_CD column of table CLW_ESTIMATOR_FACILITY */
    public static final String FACILITY_STATUS_CD = "FACILITY_STATUS_CD";
    /** <code>PROCESS_STEP</code> PROCESS_STEP column of table CLW_ESTIMATOR_FACILITY */
    public static final String PROCESS_STEP = "PROCESS_STEP";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ESTIMATOR_FACILITY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ESTIMATOR_FACILITY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ESTIMATOR_FACILITY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ESTIMATOR_FACILITY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>ORDER_GUIDE_ID</code> ORDER_GUIDE_ID column of table CLW_ESTIMATOR_FACILITY */
    public static final String ORDER_GUIDE_ID = "ORDER_GUIDE_ID";
    /** <code>TEMPLATE_FL</code> TEMPLATE_FL column of table CLW_ESTIMATOR_FACILITY */
    public static final String TEMPLATE_FL = "TEMPLATE_FL";
    /** <code>NET_CLEANABLE_FOOTAGE</code> NET_CLEANABLE_FOOTAGE column of table CLW_ESTIMATOR_FACILITY */
    public static final String NET_CLEANABLE_FOOTAGE = "NET_CLEANABLE_FOOTAGE";
    /** <code>TEMPLATE_FACILITY_ID</code> TEMPLATE_FACILITY_ID column of table CLW_ESTIMATOR_FACILITY */
    public static final String TEMPLATE_FACILITY_ID = "TEMPLATE_FACILITY_ID";
    /** <code>COMMON_AREA_RECEPTACLE_QTY</code> COMMON_AREA_RECEPTACLE_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String COMMON_AREA_RECEPTACLE_QTY = "COMMON_AREA_RECEPTACLE_QTY";
    /** <code>VISITOR_TOILET_TISSUE_PERCENT</code> VISITOR_TOILET_TISSUE_PERCENT column of table CLW_ESTIMATOR_FACILITY */
    public static final String VISITOR_TOILET_TISSUE_PERCENT = "VISITOR_TOILET_TISSUE_PERCENT";
    /** <code>LARGE_LINER_CA_LINER_QTY</code> LARGE_LINER_CA_LINER_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String LARGE_LINER_CA_LINER_QTY = "LARGE_LINER_CA_LINER_QTY";
    /** <code>SINK_BATHROOM_QTY</code> SINK_BATHROOM_QTY column of table CLW_ESTIMATOR_FACILITY */
    public static final String SINK_BATHROOM_QTY = "SINK_BATHROOM_QTY";
    /** <code>FACILITY_GROUP</code> FACILITY_GROUP column of table CLW_ESTIMATOR_FACILITY */
    public static final String FACILITY_GROUP = "FACILITY_GROUP";
    /** <code>FLOOR_MACHINE</code> FLOOR_MACHINE column of table CLW_ESTIMATOR_FACILITY */
    public static final String FLOOR_MACHINE = "FLOOR_MACHINE";

    /**
     * Constructor.
     */
    public EstimatorFacilityDataAccess()
    {
    }

    /**
     * Gets a EstimatorFacilityData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pEstimatorFacilityId The key requested.
     * @return new EstimatorFacilityData()
     * @throws            SQLException
     */
    public static EstimatorFacilityData select(Connection pCon, int pEstimatorFacilityId)
        throws SQLException, DataNotFoundException {
        EstimatorFacilityData x=null;
        String sql="SELECT ESTIMATOR_FACILITY_ID,CATALOG_ID,NAME,FACILITY_QTY,WORKING_DAY_YEAR_QTY,FACILITY_TYPE_CD,STATION_QTY,APPEARANCE_STANDARD_CD,PERSONNEL_QTY,VISITOR_QTY,BATHROOM_QTY,TOILET_BATHROOM_QTY,SHOWER_QTY,VISITOR_BATHROOM_PERCENT,WASH_HAND_QTY,TISSUE_USAGE_QTY,RECEPTACLE_LINER_RATIO,LINER_RATIO_BASE_CD,ADDITIONAL_LINER_RATIO,TOILET_LINER_RATIO,LARGE_LINER_RATIO,GROSS_FOOTAGE,CLEANABLE_FOOTAGE_PERCENT,ESTIMATED_ITEMS_FACTOR,BASEBOARD_PERCENT,FACILITY_STATUS_CD,PROCESS_STEP,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_GUIDE_ID,TEMPLATE_FL,NET_CLEANABLE_FOOTAGE,TEMPLATE_FACILITY_ID,COMMON_AREA_RECEPTACLE_QTY,VISITOR_TOILET_TISSUE_PERCENT,LARGE_LINER_CA_LINER_QTY,SINK_BATHROOM_QTY,FACILITY_GROUP,FLOOR_MACHINE FROM CLW_ESTIMATOR_FACILITY WHERE ESTIMATOR_FACILITY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pEstimatorFacilityId=" + pEstimatorFacilityId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pEstimatorFacilityId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=EstimatorFacilityData.createValue();
            
            x.setEstimatorFacilityId(rs.getInt(1));
            x.setCatalogId(rs.getInt(2));
            x.setName(rs.getString(3));
            x.setFacilityQty(rs.getInt(4));
            x.setWorkingDayYearQty(rs.getInt(5));
            x.setFacilityTypeCd(rs.getString(6));
            x.setStationQty(rs.getInt(7));
            x.setAppearanceStandardCd(rs.getString(8));
            x.setPersonnelQty(rs.getInt(9));
            x.setVisitorQty(rs.getInt(10));
            x.setBathroomQty(rs.getInt(11));
            x.setToiletBathroomQty(rs.getBigDecimal(12));
            x.setShowerQty(rs.getInt(13));
            x.setVisitorBathroomPercent(rs.getBigDecimal(14));
            x.setWashHandQty(rs.getBigDecimal(15));
            x.setTissueUsageQty(rs.getBigDecimal(16));
            x.setReceptacleLinerRatio(rs.getBigDecimal(17));
            x.setLinerRatioBaseCd(rs.getString(18));
            x.setAdditionalLinerRatio(rs.getBigDecimal(19));
            x.setToiletLinerRatio(rs.getBigDecimal(20));
            x.setLargeLinerRatio(rs.getBigDecimal(21));
            x.setGrossFootage(rs.getInt(22));
            x.setCleanableFootagePercent(rs.getBigDecimal(23));
            x.setEstimatedItemsFactor(rs.getBigDecimal(24));
            x.setBaseboardPercent(rs.getBigDecimal(25));
            x.setFacilityStatusCd(rs.getString(26));
            x.setProcessStep(rs.getInt(27));
            x.setAddDate(rs.getTimestamp(28));
            x.setAddBy(rs.getString(29));
            x.setModDate(rs.getTimestamp(30));
            x.setModBy(rs.getString(31));
            x.setOrderGuideId(rs.getInt(32));
            x.setTemplateFl(rs.getString(33));
            x.setNetCleanableFootage(rs.getInt(34));
            x.setTemplateFacilityId(rs.getInt(35));
            x.setCommonAreaReceptacleQty(rs.getInt(36));
            x.setVisitorToiletTissuePercent(rs.getBigDecimal(37));
            x.setLargeLinerCaLinerQty(rs.getBigDecimal(38));
            x.setSinkBathroomQty(rs.getBigDecimal(39));
            x.setFacilityGroup(rs.getString(40));
            x.setFloorMachine(rs.getString(41));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ESTIMATOR_FACILITY_ID :" + pEstimatorFacilityId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a EstimatorFacilityDataVector object that consists
     * of EstimatorFacilityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new EstimatorFacilityDataVector()
     * @throws            SQLException
     */
    public static EstimatorFacilityDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a EstimatorFacilityData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ESTIMATOR_FACILITY.ESTIMATOR_FACILITY_ID,CLW_ESTIMATOR_FACILITY.CATALOG_ID,CLW_ESTIMATOR_FACILITY.NAME,CLW_ESTIMATOR_FACILITY.FACILITY_QTY,CLW_ESTIMATOR_FACILITY.WORKING_DAY_YEAR_QTY,CLW_ESTIMATOR_FACILITY.FACILITY_TYPE_CD,CLW_ESTIMATOR_FACILITY.STATION_QTY,CLW_ESTIMATOR_FACILITY.APPEARANCE_STANDARD_CD,CLW_ESTIMATOR_FACILITY.PERSONNEL_QTY,CLW_ESTIMATOR_FACILITY.VISITOR_QTY,CLW_ESTIMATOR_FACILITY.BATHROOM_QTY,CLW_ESTIMATOR_FACILITY.TOILET_BATHROOM_QTY,CLW_ESTIMATOR_FACILITY.SHOWER_QTY,CLW_ESTIMATOR_FACILITY.VISITOR_BATHROOM_PERCENT,CLW_ESTIMATOR_FACILITY.WASH_HAND_QTY,CLW_ESTIMATOR_FACILITY.TISSUE_USAGE_QTY,CLW_ESTIMATOR_FACILITY.RECEPTACLE_LINER_RATIO,CLW_ESTIMATOR_FACILITY.LINER_RATIO_BASE_CD,CLW_ESTIMATOR_FACILITY.ADDITIONAL_LINER_RATIO,CLW_ESTIMATOR_FACILITY.TOILET_LINER_RATIO,CLW_ESTIMATOR_FACILITY.LARGE_LINER_RATIO,CLW_ESTIMATOR_FACILITY.GROSS_FOOTAGE,CLW_ESTIMATOR_FACILITY.CLEANABLE_FOOTAGE_PERCENT,CLW_ESTIMATOR_FACILITY.ESTIMATED_ITEMS_FACTOR,CLW_ESTIMATOR_FACILITY.BASEBOARD_PERCENT,CLW_ESTIMATOR_FACILITY.FACILITY_STATUS_CD,CLW_ESTIMATOR_FACILITY.PROCESS_STEP,CLW_ESTIMATOR_FACILITY.ADD_DATE,CLW_ESTIMATOR_FACILITY.ADD_BY,CLW_ESTIMATOR_FACILITY.MOD_DATE,CLW_ESTIMATOR_FACILITY.MOD_BY,CLW_ESTIMATOR_FACILITY.ORDER_GUIDE_ID,CLW_ESTIMATOR_FACILITY.TEMPLATE_FL,CLW_ESTIMATOR_FACILITY.NET_CLEANABLE_FOOTAGE,CLW_ESTIMATOR_FACILITY.TEMPLATE_FACILITY_ID,CLW_ESTIMATOR_FACILITY.COMMON_AREA_RECEPTACLE_QTY,CLW_ESTIMATOR_FACILITY.VISITOR_TOILET_TISSUE_PERCENT,CLW_ESTIMATOR_FACILITY.LARGE_LINER_CA_LINER_QTY,CLW_ESTIMATOR_FACILITY.SINK_BATHROOM_QTY,CLW_ESTIMATOR_FACILITY.FACILITY_GROUP,CLW_ESTIMATOR_FACILITY.FLOOR_MACHINE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated EstimatorFacilityData Object.
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
    *@returns a populated EstimatorFacilityData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         EstimatorFacilityData x = EstimatorFacilityData.createValue();
         
         x.setEstimatorFacilityId(rs.getInt(1+offset));
         x.setCatalogId(rs.getInt(2+offset));
         x.setName(rs.getString(3+offset));
         x.setFacilityQty(rs.getInt(4+offset));
         x.setWorkingDayYearQty(rs.getInt(5+offset));
         x.setFacilityTypeCd(rs.getString(6+offset));
         x.setStationQty(rs.getInt(7+offset));
         x.setAppearanceStandardCd(rs.getString(8+offset));
         x.setPersonnelQty(rs.getInt(9+offset));
         x.setVisitorQty(rs.getInt(10+offset));
         x.setBathroomQty(rs.getInt(11+offset));
         x.setToiletBathroomQty(rs.getBigDecimal(12+offset));
         x.setShowerQty(rs.getInt(13+offset));
         x.setVisitorBathroomPercent(rs.getBigDecimal(14+offset));
         x.setWashHandQty(rs.getBigDecimal(15+offset));
         x.setTissueUsageQty(rs.getBigDecimal(16+offset));
         x.setReceptacleLinerRatio(rs.getBigDecimal(17+offset));
         x.setLinerRatioBaseCd(rs.getString(18+offset));
         x.setAdditionalLinerRatio(rs.getBigDecimal(19+offset));
         x.setToiletLinerRatio(rs.getBigDecimal(20+offset));
         x.setLargeLinerRatio(rs.getBigDecimal(21+offset));
         x.setGrossFootage(rs.getInt(22+offset));
         x.setCleanableFootagePercent(rs.getBigDecimal(23+offset));
         x.setEstimatedItemsFactor(rs.getBigDecimal(24+offset));
         x.setBaseboardPercent(rs.getBigDecimal(25+offset));
         x.setFacilityStatusCd(rs.getString(26+offset));
         x.setProcessStep(rs.getInt(27+offset));
         x.setAddDate(rs.getTimestamp(28+offset));
         x.setAddBy(rs.getString(29+offset));
         x.setModDate(rs.getTimestamp(30+offset));
         x.setModBy(rs.getString(31+offset));
         x.setOrderGuideId(rs.getInt(32+offset));
         x.setTemplateFl(rs.getString(33+offset));
         x.setNetCleanableFootage(rs.getInt(34+offset));
         x.setTemplateFacilityId(rs.getInt(35+offset));
         x.setCommonAreaReceptacleQty(rs.getInt(36+offset));
         x.setVisitorToiletTissuePercent(rs.getBigDecimal(37+offset));
         x.setLargeLinerCaLinerQty(rs.getBigDecimal(38+offset));
         x.setSinkBathroomQty(rs.getBigDecimal(39+offset));
         x.setFacilityGroup(rs.getString(40+offset));
         x.setFloorMachine(rs.getString(41+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the EstimatorFacilityData Object represents.
    */
    public int getColumnCount(){
        return 41;
    }

    /**
     * Gets a EstimatorFacilityDataVector object that consists
     * of EstimatorFacilityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new EstimatorFacilityDataVector()
     * @throws            SQLException
     */
    public static EstimatorFacilityDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ESTIMATOR_FACILITY_ID,CATALOG_ID,NAME,FACILITY_QTY,WORKING_DAY_YEAR_QTY,FACILITY_TYPE_CD,STATION_QTY,APPEARANCE_STANDARD_CD,PERSONNEL_QTY,VISITOR_QTY,BATHROOM_QTY,TOILET_BATHROOM_QTY,SHOWER_QTY,VISITOR_BATHROOM_PERCENT,WASH_HAND_QTY,TISSUE_USAGE_QTY,RECEPTACLE_LINER_RATIO,LINER_RATIO_BASE_CD,ADDITIONAL_LINER_RATIO,TOILET_LINER_RATIO,LARGE_LINER_RATIO,GROSS_FOOTAGE,CLEANABLE_FOOTAGE_PERCENT,ESTIMATED_ITEMS_FACTOR,BASEBOARD_PERCENT,FACILITY_STATUS_CD,PROCESS_STEP,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_GUIDE_ID,TEMPLATE_FL,NET_CLEANABLE_FOOTAGE,TEMPLATE_FACILITY_ID,COMMON_AREA_RECEPTACLE_QTY,VISITOR_TOILET_TISSUE_PERCENT,LARGE_LINER_CA_LINER_QTY,SINK_BATHROOM_QTY,FACILITY_GROUP,FLOOR_MACHINE FROM CLW_ESTIMATOR_FACILITY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ESTIMATOR_FACILITY.ESTIMATOR_FACILITY_ID,CLW_ESTIMATOR_FACILITY.CATALOG_ID,CLW_ESTIMATOR_FACILITY.NAME,CLW_ESTIMATOR_FACILITY.FACILITY_QTY,CLW_ESTIMATOR_FACILITY.WORKING_DAY_YEAR_QTY,CLW_ESTIMATOR_FACILITY.FACILITY_TYPE_CD,CLW_ESTIMATOR_FACILITY.STATION_QTY,CLW_ESTIMATOR_FACILITY.APPEARANCE_STANDARD_CD,CLW_ESTIMATOR_FACILITY.PERSONNEL_QTY,CLW_ESTIMATOR_FACILITY.VISITOR_QTY,CLW_ESTIMATOR_FACILITY.BATHROOM_QTY,CLW_ESTIMATOR_FACILITY.TOILET_BATHROOM_QTY,CLW_ESTIMATOR_FACILITY.SHOWER_QTY,CLW_ESTIMATOR_FACILITY.VISITOR_BATHROOM_PERCENT,CLW_ESTIMATOR_FACILITY.WASH_HAND_QTY,CLW_ESTIMATOR_FACILITY.TISSUE_USAGE_QTY,CLW_ESTIMATOR_FACILITY.RECEPTACLE_LINER_RATIO,CLW_ESTIMATOR_FACILITY.LINER_RATIO_BASE_CD,CLW_ESTIMATOR_FACILITY.ADDITIONAL_LINER_RATIO,CLW_ESTIMATOR_FACILITY.TOILET_LINER_RATIO,CLW_ESTIMATOR_FACILITY.LARGE_LINER_RATIO,CLW_ESTIMATOR_FACILITY.GROSS_FOOTAGE,CLW_ESTIMATOR_FACILITY.CLEANABLE_FOOTAGE_PERCENT,CLW_ESTIMATOR_FACILITY.ESTIMATED_ITEMS_FACTOR,CLW_ESTIMATOR_FACILITY.BASEBOARD_PERCENT,CLW_ESTIMATOR_FACILITY.FACILITY_STATUS_CD,CLW_ESTIMATOR_FACILITY.PROCESS_STEP,CLW_ESTIMATOR_FACILITY.ADD_DATE,CLW_ESTIMATOR_FACILITY.ADD_BY,CLW_ESTIMATOR_FACILITY.MOD_DATE,CLW_ESTIMATOR_FACILITY.MOD_BY,CLW_ESTIMATOR_FACILITY.ORDER_GUIDE_ID,CLW_ESTIMATOR_FACILITY.TEMPLATE_FL,CLW_ESTIMATOR_FACILITY.NET_CLEANABLE_FOOTAGE,CLW_ESTIMATOR_FACILITY.TEMPLATE_FACILITY_ID,CLW_ESTIMATOR_FACILITY.COMMON_AREA_RECEPTACLE_QTY,CLW_ESTIMATOR_FACILITY.VISITOR_TOILET_TISSUE_PERCENT,CLW_ESTIMATOR_FACILITY.LARGE_LINER_CA_LINER_QTY,CLW_ESTIMATOR_FACILITY.SINK_BATHROOM_QTY,CLW_ESTIMATOR_FACILITY.FACILITY_GROUP,CLW_ESTIMATOR_FACILITY.FLOOR_MACHINE FROM CLW_ESTIMATOR_FACILITY");
                where = pCriteria.getSqlClause("CLW_ESTIMATOR_FACILITY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ESTIMATOR_FACILITY.equals(otherTable)){
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
        EstimatorFacilityDataVector v = new EstimatorFacilityDataVector();
        while (rs.next()) {
            EstimatorFacilityData x = EstimatorFacilityData.createValue();
            
            x.setEstimatorFacilityId(rs.getInt(1));
            x.setCatalogId(rs.getInt(2));
            x.setName(rs.getString(3));
            x.setFacilityQty(rs.getInt(4));
            x.setWorkingDayYearQty(rs.getInt(5));
            x.setFacilityTypeCd(rs.getString(6));
            x.setStationQty(rs.getInt(7));
            x.setAppearanceStandardCd(rs.getString(8));
            x.setPersonnelQty(rs.getInt(9));
            x.setVisitorQty(rs.getInt(10));
            x.setBathroomQty(rs.getInt(11));
            x.setToiletBathroomQty(rs.getBigDecimal(12));
            x.setShowerQty(rs.getInt(13));
            x.setVisitorBathroomPercent(rs.getBigDecimal(14));
            x.setWashHandQty(rs.getBigDecimal(15));
            x.setTissueUsageQty(rs.getBigDecimal(16));
            x.setReceptacleLinerRatio(rs.getBigDecimal(17));
            x.setLinerRatioBaseCd(rs.getString(18));
            x.setAdditionalLinerRatio(rs.getBigDecimal(19));
            x.setToiletLinerRatio(rs.getBigDecimal(20));
            x.setLargeLinerRatio(rs.getBigDecimal(21));
            x.setGrossFootage(rs.getInt(22));
            x.setCleanableFootagePercent(rs.getBigDecimal(23));
            x.setEstimatedItemsFactor(rs.getBigDecimal(24));
            x.setBaseboardPercent(rs.getBigDecimal(25));
            x.setFacilityStatusCd(rs.getString(26));
            x.setProcessStep(rs.getInt(27));
            x.setAddDate(rs.getTimestamp(28));
            x.setAddBy(rs.getString(29));
            x.setModDate(rs.getTimestamp(30));
            x.setModBy(rs.getString(31));
            x.setOrderGuideId(rs.getInt(32));
            x.setTemplateFl(rs.getString(33));
            x.setNetCleanableFootage(rs.getInt(34));
            x.setTemplateFacilityId(rs.getInt(35));
            x.setCommonAreaReceptacleQty(rs.getInt(36));
            x.setVisitorToiletTissuePercent(rs.getBigDecimal(37));
            x.setLargeLinerCaLinerQty(rs.getBigDecimal(38));
            x.setSinkBathroomQty(rs.getBigDecimal(39));
            x.setFacilityGroup(rs.getString(40));
            x.setFloorMachine(rs.getString(41));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a EstimatorFacilityDataVector object that consists
     * of EstimatorFacilityData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for EstimatorFacilityData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new EstimatorFacilityDataVector()
     * @throws            SQLException
     */
    public static EstimatorFacilityDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        EstimatorFacilityDataVector v = new EstimatorFacilityDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ESTIMATOR_FACILITY_ID,CATALOG_ID,NAME,FACILITY_QTY,WORKING_DAY_YEAR_QTY,FACILITY_TYPE_CD,STATION_QTY,APPEARANCE_STANDARD_CD,PERSONNEL_QTY,VISITOR_QTY,BATHROOM_QTY,TOILET_BATHROOM_QTY,SHOWER_QTY,VISITOR_BATHROOM_PERCENT,WASH_HAND_QTY,TISSUE_USAGE_QTY,RECEPTACLE_LINER_RATIO,LINER_RATIO_BASE_CD,ADDITIONAL_LINER_RATIO,TOILET_LINER_RATIO,LARGE_LINER_RATIO,GROSS_FOOTAGE,CLEANABLE_FOOTAGE_PERCENT,ESTIMATED_ITEMS_FACTOR,BASEBOARD_PERCENT,FACILITY_STATUS_CD,PROCESS_STEP,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_GUIDE_ID,TEMPLATE_FL,NET_CLEANABLE_FOOTAGE,TEMPLATE_FACILITY_ID,COMMON_AREA_RECEPTACLE_QTY,VISITOR_TOILET_TISSUE_PERCENT,LARGE_LINER_CA_LINER_QTY,SINK_BATHROOM_QTY,FACILITY_GROUP,FLOOR_MACHINE FROM CLW_ESTIMATOR_FACILITY WHERE ESTIMATOR_FACILITY_ID IN (");

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
            EstimatorFacilityData x=null;
            while (rs.next()) {
                // build the object
                x=EstimatorFacilityData.createValue();
                
                x.setEstimatorFacilityId(rs.getInt(1));
                x.setCatalogId(rs.getInt(2));
                x.setName(rs.getString(3));
                x.setFacilityQty(rs.getInt(4));
                x.setWorkingDayYearQty(rs.getInt(5));
                x.setFacilityTypeCd(rs.getString(6));
                x.setStationQty(rs.getInt(7));
                x.setAppearanceStandardCd(rs.getString(8));
                x.setPersonnelQty(rs.getInt(9));
                x.setVisitorQty(rs.getInt(10));
                x.setBathroomQty(rs.getInt(11));
                x.setToiletBathroomQty(rs.getBigDecimal(12));
                x.setShowerQty(rs.getInt(13));
                x.setVisitorBathroomPercent(rs.getBigDecimal(14));
                x.setWashHandQty(rs.getBigDecimal(15));
                x.setTissueUsageQty(rs.getBigDecimal(16));
                x.setReceptacleLinerRatio(rs.getBigDecimal(17));
                x.setLinerRatioBaseCd(rs.getString(18));
                x.setAdditionalLinerRatio(rs.getBigDecimal(19));
                x.setToiletLinerRatio(rs.getBigDecimal(20));
                x.setLargeLinerRatio(rs.getBigDecimal(21));
                x.setGrossFootage(rs.getInt(22));
                x.setCleanableFootagePercent(rs.getBigDecimal(23));
                x.setEstimatedItemsFactor(rs.getBigDecimal(24));
                x.setBaseboardPercent(rs.getBigDecimal(25));
                x.setFacilityStatusCd(rs.getString(26));
                x.setProcessStep(rs.getInt(27));
                x.setAddDate(rs.getTimestamp(28));
                x.setAddBy(rs.getString(29));
                x.setModDate(rs.getTimestamp(30));
                x.setModBy(rs.getString(31));
                x.setOrderGuideId(rs.getInt(32));
                x.setTemplateFl(rs.getString(33));
                x.setNetCleanableFootage(rs.getInt(34));
                x.setTemplateFacilityId(rs.getInt(35));
                x.setCommonAreaReceptacleQty(rs.getInt(36));
                x.setVisitorToiletTissuePercent(rs.getBigDecimal(37));
                x.setLargeLinerCaLinerQty(rs.getBigDecimal(38));
                x.setSinkBathroomQty(rs.getBigDecimal(39));
                x.setFacilityGroup(rs.getString(40));
                x.setFloorMachine(rs.getString(41));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a EstimatorFacilityDataVector object of all
     * EstimatorFacilityData objects in the database.
     * @param pCon An open database connection.
     * @return new EstimatorFacilityDataVector()
     * @throws            SQLException
     */
    public static EstimatorFacilityDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ESTIMATOR_FACILITY_ID,CATALOG_ID,NAME,FACILITY_QTY,WORKING_DAY_YEAR_QTY,FACILITY_TYPE_CD,STATION_QTY,APPEARANCE_STANDARD_CD,PERSONNEL_QTY,VISITOR_QTY,BATHROOM_QTY,TOILET_BATHROOM_QTY,SHOWER_QTY,VISITOR_BATHROOM_PERCENT,WASH_HAND_QTY,TISSUE_USAGE_QTY,RECEPTACLE_LINER_RATIO,LINER_RATIO_BASE_CD,ADDITIONAL_LINER_RATIO,TOILET_LINER_RATIO,LARGE_LINER_RATIO,GROSS_FOOTAGE,CLEANABLE_FOOTAGE_PERCENT,ESTIMATED_ITEMS_FACTOR,BASEBOARD_PERCENT,FACILITY_STATUS_CD,PROCESS_STEP,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_GUIDE_ID,TEMPLATE_FL,NET_CLEANABLE_FOOTAGE,TEMPLATE_FACILITY_ID,COMMON_AREA_RECEPTACLE_QTY,VISITOR_TOILET_TISSUE_PERCENT,LARGE_LINER_CA_LINER_QTY,SINK_BATHROOM_QTY,FACILITY_GROUP,FLOOR_MACHINE FROM CLW_ESTIMATOR_FACILITY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        EstimatorFacilityDataVector v = new EstimatorFacilityDataVector();
        EstimatorFacilityData x = null;
        while (rs.next()) {
            // build the object
            x = EstimatorFacilityData.createValue();
            
            x.setEstimatorFacilityId(rs.getInt(1));
            x.setCatalogId(rs.getInt(2));
            x.setName(rs.getString(3));
            x.setFacilityQty(rs.getInt(4));
            x.setWorkingDayYearQty(rs.getInt(5));
            x.setFacilityTypeCd(rs.getString(6));
            x.setStationQty(rs.getInt(7));
            x.setAppearanceStandardCd(rs.getString(8));
            x.setPersonnelQty(rs.getInt(9));
            x.setVisitorQty(rs.getInt(10));
            x.setBathroomQty(rs.getInt(11));
            x.setToiletBathroomQty(rs.getBigDecimal(12));
            x.setShowerQty(rs.getInt(13));
            x.setVisitorBathroomPercent(rs.getBigDecimal(14));
            x.setWashHandQty(rs.getBigDecimal(15));
            x.setTissueUsageQty(rs.getBigDecimal(16));
            x.setReceptacleLinerRatio(rs.getBigDecimal(17));
            x.setLinerRatioBaseCd(rs.getString(18));
            x.setAdditionalLinerRatio(rs.getBigDecimal(19));
            x.setToiletLinerRatio(rs.getBigDecimal(20));
            x.setLargeLinerRatio(rs.getBigDecimal(21));
            x.setGrossFootage(rs.getInt(22));
            x.setCleanableFootagePercent(rs.getBigDecimal(23));
            x.setEstimatedItemsFactor(rs.getBigDecimal(24));
            x.setBaseboardPercent(rs.getBigDecimal(25));
            x.setFacilityStatusCd(rs.getString(26));
            x.setProcessStep(rs.getInt(27));
            x.setAddDate(rs.getTimestamp(28));
            x.setAddBy(rs.getString(29));
            x.setModDate(rs.getTimestamp(30));
            x.setModBy(rs.getString(31));
            x.setOrderGuideId(rs.getInt(32));
            x.setTemplateFl(rs.getString(33));
            x.setNetCleanableFootage(rs.getInt(34));
            x.setTemplateFacilityId(rs.getInt(35));
            x.setCommonAreaReceptacleQty(rs.getInt(36));
            x.setVisitorToiletTissuePercent(rs.getBigDecimal(37));
            x.setLargeLinerCaLinerQty(rs.getBigDecimal(38));
            x.setSinkBathroomQty(rs.getBigDecimal(39));
            x.setFacilityGroup(rs.getString(40));
            x.setFloorMachine(rs.getString(41));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * EstimatorFacilityData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ESTIMATOR_FACILITY_ID FROM CLW_ESTIMATOR_FACILITY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ESTIMATOR_FACILITY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ESTIMATOR_FACILITY");
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
     * Inserts a EstimatorFacilityData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EstimatorFacilityData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new EstimatorFacilityData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static EstimatorFacilityData insert(Connection pCon, EstimatorFacilityData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ESTIMATOR_FACILITY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ESTIMATOR_FACILITY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setEstimatorFacilityId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ESTIMATOR_FACILITY (ESTIMATOR_FACILITY_ID,CATALOG_ID,NAME,FACILITY_QTY,WORKING_DAY_YEAR_QTY,FACILITY_TYPE_CD,STATION_QTY,APPEARANCE_STANDARD_CD,PERSONNEL_QTY,VISITOR_QTY,BATHROOM_QTY,TOILET_BATHROOM_QTY,SHOWER_QTY,VISITOR_BATHROOM_PERCENT,WASH_HAND_QTY,TISSUE_USAGE_QTY,RECEPTACLE_LINER_RATIO,LINER_RATIO_BASE_CD,ADDITIONAL_LINER_RATIO,TOILET_LINER_RATIO,LARGE_LINER_RATIO,GROSS_FOOTAGE,CLEANABLE_FOOTAGE_PERCENT,ESTIMATED_ITEMS_FACTOR,BASEBOARD_PERCENT,FACILITY_STATUS_CD,PROCESS_STEP,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_GUIDE_ID,TEMPLATE_FL,NET_CLEANABLE_FOOTAGE,TEMPLATE_FACILITY_ID,COMMON_AREA_RECEPTACLE_QTY,VISITOR_TOILET_TISSUE_PERCENT,LARGE_LINER_CA_LINER_QTY,SINK_BATHROOM_QTY,FACILITY_GROUP,FLOOR_MACHINE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getEstimatorFacilityId());
        pstmt.setInt(2,pData.getCatalogId());
        pstmt.setString(3,pData.getName());
        pstmt.setInt(4,pData.getFacilityQty());
        pstmt.setInt(5,pData.getWorkingDayYearQty());
        pstmt.setString(6,pData.getFacilityTypeCd());
        pstmt.setInt(7,pData.getStationQty());
        pstmt.setString(8,pData.getAppearanceStandardCd());
        pstmt.setInt(9,pData.getPersonnelQty());
        pstmt.setInt(10,pData.getVisitorQty());
        pstmt.setInt(11,pData.getBathroomQty());
        pstmt.setBigDecimal(12,pData.getToiletBathroomQty());
        pstmt.setInt(13,pData.getShowerQty());
        pstmt.setBigDecimal(14,pData.getVisitorBathroomPercent());
        pstmt.setBigDecimal(15,pData.getWashHandQty());
        pstmt.setBigDecimal(16,pData.getTissueUsageQty());
        pstmt.setBigDecimal(17,pData.getReceptacleLinerRatio());
        pstmt.setString(18,pData.getLinerRatioBaseCd());
        pstmt.setBigDecimal(19,pData.getAdditionalLinerRatio());
        pstmt.setBigDecimal(20,pData.getToiletLinerRatio());
        pstmt.setBigDecimal(21,pData.getLargeLinerRatio());
        pstmt.setInt(22,pData.getGrossFootage());
        pstmt.setBigDecimal(23,pData.getCleanableFootagePercent());
        pstmt.setBigDecimal(24,pData.getEstimatedItemsFactor());
        pstmt.setBigDecimal(25,pData.getBaseboardPercent());
        pstmt.setString(26,pData.getFacilityStatusCd());
        pstmt.setInt(27,pData.getProcessStep());
        pstmt.setTimestamp(28,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(29,pData.getAddBy());
        pstmt.setTimestamp(30,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(31,pData.getModBy());
        pstmt.setInt(32,pData.getOrderGuideId());
        pstmt.setString(33,pData.getTemplateFl());
        pstmt.setInt(34,pData.getNetCleanableFootage());
        pstmt.setInt(35,pData.getTemplateFacilityId());
        pstmt.setInt(36,pData.getCommonAreaReceptacleQty());
        pstmt.setBigDecimal(37,pData.getVisitorToiletTissuePercent());
        pstmt.setBigDecimal(38,pData.getLargeLinerCaLinerQty());
        pstmt.setBigDecimal(39,pData.getSinkBathroomQty());
        pstmt.setString(40,pData.getFacilityGroup());
        pstmt.setString(41,pData.getFloorMachine());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ESTIMATOR_FACILITY_ID="+pData.getEstimatorFacilityId());
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   FACILITY_QTY="+pData.getFacilityQty());
            log.debug("SQL:   WORKING_DAY_YEAR_QTY="+pData.getWorkingDayYearQty());
            log.debug("SQL:   FACILITY_TYPE_CD="+pData.getFacilityTypeCd());
            log.debug("SQL:   STATION_QTY="+pData.getStationQty());
            log.debug("SQL:   APPEARANCE_STANDARD_CD="+pData.getAppearanceStandardCd());
            log.debug("SQL:   PERSONNEL_QTY="+pData.getPersonnelQty());
            log.debug("SQL:   VISITOR_QTY="+pData.getVisitorQty());
            log.debug("SQL:   BATHROOM_QTY="+pData.getBathroomQty());
            log.debug("SQL:   TOILET_BATHROOM_QTY="+pData.getToiletBathroomQty());
            log.debug("SQL:   SHOWER_QTY="+pData.getShowerQty());
            log.debug("SQL:   VISITOR_BATHROOM_PERCENT="+pData.getVisitorBathroomPercent());
            log.debug("SQL:   WASH_HAND_QTY="+pData.getWashHandQty());
            log.debug("SQL:   TISSUE_USAGE_QTY="+pData.getTissueUsageQty());
            log.debug("SQL:   RECEPTACLE_LINER_RATIO="+pData.getReceptacleLinerRatio());
            log.debug("SQL:   LINER_RATIO_BASE_CD="+pData.getLinerRatioBaseCd());
            log.debug("SQL:   ADDITIONAL_LINER_RATIO="+pData.getAdditionalLinerRatio());
            log.debug("SQL:   TOILET_LINER_RATIO="+pData.getToiletLinerRatio());
            log.debug("SQL:   LARGE_LINER_RATIO="+pData.getLargeLinerRatio());
            log.debug("SQL:   GROSS_FOOTAGE="+pData.getGrossFootage());
            log.debug("SQL:   CLEANABLE_FOOTAGE_PERCENT="+pData.getCleanableFootagePercent());
            log.debug("SQL:   ESTIMATED_ITEMS_FACTOR="+pData.getEstimatedItemsFactor());
            log.debug("SQL:   BASEBOARD_PERCENT="+pData.getBaseboardPercent());
            log.debug("SQL:   FACILITY_STATUS_CD="+pData.getFacilityStatusCd());
            log.debug("SQL:   PROCESS_STEP="+pData.getProcessStep());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ORDER_GUIDE_ID="+pData.getOrderGuideId());
            log.debug("SQL:   TEMPLATE_FL="+pData.getTemplateFl());
            log.debug("SQL:   NET_CLEANABLE_FOOTAGE="+pData.getNetCleanableFootage());
            log.debug("SQL:   TEMPLATE_FACILITY_ID="+pData.getTemplateFacilityId());
            log.debug("SQL:   COMMON_AREA_RECEPTACLE_QTY="+pData.getCommonAreaReceptacleQty());
            log.debug("SQL:   VISITOR_TOILET_TISSUE_PERCENT="+pData.getVisitorToiletTissuePercent());
            log.debug("SQL:   LARGE_LINER_CA_LINER_QTY="+pData.getLargeLinerCaLinerQty());
            log.debug("SQL:   SINK_BATHROOM_QTY="+pData.getSinkBathroomQty());
            log.debug("SQL:   FACILITY_GROUP="+pData.getFacilityGroup());
            log.debug("SQL:   FLOOR_MACHINE="+pData.getFloorMachine());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setEstimatorFacilityId(0);
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
     * Updates a EstimatorFacilityData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A EstimatorFacilityData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, EstimatorFacilityData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ESTIMATOR_FACILITY SET CATALOG_ID = ?,NAME = ?,FACILITY_QTY = ?,WORKING_DAY_YEAR_QTY = ?,FACILITY_TYPE_CD = ?,STATION_QTY = ?,APPEARANCE_STANDARD_CD = ?,PERSONNEL_QTY = ?,VISITOR_QTY = ?,BATHROOM_QTY = ?,TOILET_BATHROOM_QTY = ?,SHOWER_QTY = ?,VISITOR_BATHROOM_PERCENT = ?,WASH_HAND_QTY = ?,TISSUE_USAGE_QTY = ?,RECEPTACLE_LINER_RATIO = ?,LINER_RATIO_BASE_CD = ?,ADDITIONAL_LINER_RATIO = ?,TOILET_LINER_RATIO = ?,LARGE_LINER_RATIO = ?,GROSS_FOOTAGE = ?,CLEANABLE_FOOTAGE_PERCENT = ?,ESTIMATED_ITEMS_FACTOR = ?,BASEBOARD_PERCENT = ?,FACILITY_STATUS_CD = ?,PROCESS_STEP = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,ORDER_GUIDE_ID = ?,TEMPLATE_FL = ?,NET_CLEANABLE_FOOTAGE = ?,TEMPLATE_FACILITY_ID = ?,COMMON_AREA_RECEPTACLE_QTY = ?,VISITOR_TOILET_TISSUE_PERCENT = ?,LARGE_LINER_CA_LINER_QTY = ?,SINK_BATHROOM_QTY = ?,FACILITY_GROUP = ?,FLOOR_MACHINE = ? WHERE ESTIMATOR_FACILITY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getCatalogId());
        pstmt.setString(i++,pData.getName());
        pstmt.setInt(i++,pData.getFacilityQty());
        pstmt.setInt(i++,pData.getWorkingDayYearQty());
        pstmt.setString(i++,pData.getFacilityTypeCd());
        pstmt.setInt(i++,pData.getStationQty());
        pstmt.setString(i++,pData.getAppearanceStandardCd());
        pstmt.setInt(i++,pData.getPersonnelQty());
        pstmt.setInt(i++,pData.getVisitorQty());
        pstmt.setInt(i++,pData.getBathroomQty());
        pstmt.setBigDecimal(i++,pData.getToiletBathroomQty());
        pstmt.setInt(i++,pData.getShowerQty());
        pstmt.setBigDecimal(i++,pData.getVisitorBathroomPercent());
        pstmt.setBigDecimal(i++,pData.getWashHandQty());
        pstmt.setBigDecimal(i++,pData.getTissueUsageQty());
        pstmt.setBigDecimal(i++,pData.getReceptacleLinerRatio());
        pstmt.setString(i++,pData.getLinerRatioBaseCd());
        pstmt.setBigDecimal(i++,pData.getAdditionalLinerRatio());
        pstmt.setBigDecimal(i++,pData.getToiletLinerRatio());
        pstmt.setBigDecimal(i++,pData.getLargeLinerRatio());
        pstmt.setInt(i++,pData.getGrossFootage());
        pstmt.setBigDecimal(i++,pData.getCleanableFootagePercent());
        pstmt.setBigDecimal(i++,pData.getEstimatedItemsFactor());
        pstmt.setBigDecimal(i++,pData.getBaseboardPercent());
        pstmt.setString(i++,pData.getFacilityStatusCd());
        pstmt.setInt(i++,pData.getProcessStep());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getOrderGuideId());
        pstmt.setString(i++,pData.getTemplateFl());
        pstmt.setInt(i++,pData.getNetCleanableFootage());
        pstmt.setInt(i++,pData.getTemplateFacilityId());
        pstmt.setInt(i++,pData.getCommonAreaReceptacleQty());
        pstmt.setBigDecimal(i++,pData.getVisitorToiletTissuePercent());
        pstmt.setBigDecimal(i++,pData.getLargeLinerCaLinerQty());
        pstmt.setBigDecimal(i++,pData.getSinkBathroomQty());
        pstmt.setString(i++,pData.getFacilityGroup());
        pstmt.setString(i++,pData.getFloorMachine());
        pstmt.setInt(i++,pData.getEstimatorFacilityId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   FACILITY_QTY="+pData.getFacilityQty());
            log.debug("SQL:   WORKING_DAY_YEAR_QTY="+pData.getWorkingDayYearQty());
            log.debug("SQL:   FACILITY_TYPE_CD="+pData.getFacilityTypeCd());
            log.debug("SQL:   STATION_QTY="+pData.getStationQty());
            log.debug("SQL:   APPEARANCE_STANDARD_CD="+pData.getAppearanceStandardCd());
            log.debug("SQL:   PERSONNEL_QTY="+pData.getPersonnelQty());
            log.debug("SQL:   VISITOR_QTY="+pData.getVisitorQty());
            log.debug("SQL:   BATHROOM_QTY="+pData.getBathroomQty());
            log.debug("SQL:   TOILET_BATHROOM_QTY="+pData.getToiletBathroomQty());
            log.debug("SQL:   SHOWER_QTY="+pData.getShowerQty());
            log.debug("SQL:   VISITOR_BATHROOM_PERCENT="+pData.getVisitorBathroomPercent());
            log.debug("SQL:   WASH_HAND_QTY="+pData.getWashHandQty());
            log.debug("SQL:   TISSUE_USAGE_QTY="+pData.getTissueUsageQty());
            log.debug("SQL:   RECEPTACLE_LINER_RATIO="+pData.getReceptacleLinerRatio());
            log.debug("SQL:   LINER_RATIO_BASE_CD="+pData.getLinerRatioBaseCd());
            log.debug("SQL:   ADDITIONAL_LINER_RATIO="+pData.getAdditionalLinerRatio());
            log.debug("SQL:   TOILET_LINER_RATIO="+pData.getToiletLinerRatio());
            log.debug("SQL:   LARGE_LINER_RATIO="+pData.getLargeLinerRatio());
            log.debug("SQL:   GROSS_FOOTAGE="+pData.getGrossFootage());
            log.debug("SQL:   CLEANABLE_FOOTAGE_PERCENT="+pData.getCleanableFootagePercent());
            log.debug("SQL:   ESTIMATED_ITEMS_FACTOR="+pData.getEstimatedItemsFactor());
            log.debug("SQL:   BASEBOARD_PERCENT="+pData.getBaseboardPercent());
            log.debug("SQL:   FACILITY_STATUS_CD="+pData.getFacilityStatusCd());
            log.debug("SQL:   PROCESS_STEP="+pData.getProcessStep());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ORDER_GUIDE_ID="+pData.getOrderGuideId());
            log.debug("SQL:   TEMPLATE_FL="+pData.getTemplateFl());
            log.debug("SQL:   NET_CLEANABLE_FOOTAGE="+pData.getNetCleanableFootage());
            log.debug("SQL:   TEMPLATE_FACILITY_ID="+pData.getTemplateFacilityId());
            log.debug("SQL:   COMMON_AREA_RECEPTACLE_QTY="+pData.getCommonAreaReceptacleQty());
            log.debug("SQL:   VISITOR_TOILET_TISSUE_PERCENT="+pData.getVisitorToiletTissuePercent());
            log.debug("SQL:   LARGE_LINER_CA_LINER_QTY="+pData.getLargeLinerCaLinerQty());
            log.debug("SQL:   SINK_BATHROOM_QTY="+pData.getSinkBathroomQty());
            log.debug("SQL:   FACILITY_GROUP="+pData.getFacilityGroup());
            log.debug("SQL:   FLOOR_MACHINE="+pData.getFloorMachine());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a EstimatorFacilityData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pEstimatorFacilityId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pEstimatorFacilityId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ESTIMATOR_FACILITY WHERE ESTIMATOR_FACILITY_ID = " + pEstimatorFacilityId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes EstimatorFacilityData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ESTIMATOR_FACILITY");
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
     * Inserts a EstimatorFacilityData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EstimatorFacilityData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, EstimatorFacilityData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ESTIMATOR_FACILITY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ESTIMATOR_FACILITY_ID,CATALOG_ID,NAME,FACILITY_QTY,WORKING_DAY_YEAR_QTY,FACILITY_TYPE_CD,STATION_QTY,APPEARANCE_STANDARD_CD,PERSONNEL_QTY,VISITOR_QTY,BATHROOM_QTY,TOILET_BATHROOM_QTY,SHOWER_QTY,VISITOR_BATHROOM_PERCENT,WASH_HAND_QTY,TISSUE_USAGE_QTY,RECEPTACLE_LINER_RATIO,LINER_RATIO_BASE_CD,ADDITIONAL_LINER_RATIO,TOILET_LINER_RATIO,LARGE_LINER_RATIO,GROSS_FOOTAGE,CLEANABLE_FOOTAGE_PERCENT,ESTIMATED_ITEMS_FACTOR,BASEBOARD_PERCENT,FACILITY_STATUS_CD,PROCESS_STEP,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_GUIDE_ID,TEMPLATE_FL,NET_CLEANABLE_FOOTAGE,TEMPLATE_FACILITY_ID,COMMON_AREA_RECEPTACLE_QTY,VISITOR_TOILET_TISSUE_PERCENT,LARGE_LINER_CA_LINER_QTY,SINK_BATHROOM_QTY,FACILITY_GROUP,FLOOR_MACHINE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getEstimatorFacilityId());
        pstmt.setInt(2+4,pData.getCatalogId());
        pstmt.setString(3+4,pData.getName());
        pstmt.setInt(4+4,pData.getFacilityQty());
        pstmt.setInt(5+4,pData.getWorkingDayYearQty());
        pstmt.setString(6+4,pData.getFacilityTypeCd());
        pstmt.setInt(7+4,pData.getStationQty());
        pstmt.setString(8+4,pData.getAppearanceStandardCd());
        pstmt.setInt(9+4,pData.getPersonnelQty());
        pstmt.setInt(10+4,pData.getVisitorQty());
        pstmt.setInt(11+4,pData.getBathroomQty());
        pstmt.setBigDecimal(12+4,pData.getToiletBathroomQty());
        pstmt.setInt(13+4,pData.getShowerQty());
        pstmt.setBigDecimal(14+4,pData.getVisitorBathroomPercent());
        pstmt.setBigDecimal(15+4,pData.getWashHandQty());
        pstmt.setBigDecimal(16+4,pData.getTissueUsageQty());
        pstmt.setBigDecimal(17+4,pData.getReceptacleLinerRatio());
        pstmt.setString(18+4,pData.getLinerRatioBaseCd());
        pstmt.setBigDecimal(19+4,pData.getAdditionalLinerRatio());
        pstmt.setBigDecimal(20+4,pData.getToiletLinerRatio());
        pstmt.setBigDecimal(21+4,pData.getLargeLinerRatio());
        pstmt.setInt(22+4,pData.getGrossFootage());
        pstmt.setBigDecimal(23+4,pData.getCleanableFootagePercent());
        pstmt.setBigDecimal(24+4,pData.getEstimatedItemsFactor());
        pstmt.setBigDecimal(25+4,pData.getBaseboardPercent());
        pstmt.setString(26+4,pData.getFacilityStatusCd());
        pstmt.setInt(27+4,pData.getProcessStep());
        pstmt.setTimestamp(28+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(29+4,pData.getAddBy());
        pstmt.setTimestamp(30+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(31+4,pData.getModBy());
        pstmt.setInt(32+4,pData.getOrderGuideId());
        pstmt.setString(33+4,pData.getTemplateFl());
        pstmt.setInt(34+4,pData.getNetCleanableFootage());
        pstmt.setInt(35+4,pData.getTemplateFacilityId());
        pstmt.setInt(36+4,pData.getCommonAreaReceptacleQty());
        pstmt.setBigDecimal(37+4,pData.getVisitorToiletTissuePercent());
        pstmt.setBigDecimal(38+4,pData.getLargeLinerCaLinerQty());
        pstmt.setBigDecimal(39+4,pData.getSinkBathroomQty());
        pstmt.setString(40+4,pData.getFacilityGroup());
        pstmt.setString(41+4,pData.getFloorMachine());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a EstimatorFacilityData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EstimatorFacilityData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new EstimatorFacilityData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static EstimatorFacilityData insert(Connection pCon, EstimatorFacilityData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a EstimatorFacilityData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A EstimatorFacilityData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, EstimatorFacilityData pData, boolean pLogFl)
        throws SQLException {
        EstimatorFacilityData oldData = null;
        if(pLogFl) {
          int id = pData.getEstimatorFacilityId();
          try {
          oldData = EstimatorFacilityDataAccess.select(pCon,id);
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
     * Deletes a EstimatorFacilityData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pEstimatorFacilityId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pEstimatorFacilityId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ESTIMATOR_FACILITY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ESTIMATOR_FACILITY d WHERE ESTIMATOR_FACILITY_ID = " + pEstimatorFacilityId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pEstimatorFacilityId);
        return n;
     }

    /**
     * Deletes EstimatorFacilityData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ESTIMATOR_FACILITY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ESTIMATOR_FACILITY d ");
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


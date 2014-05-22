package com.cleanwise.service.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Category;

import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.WorkOrderData;
import com.cleanwise.service.api.value.WorkOrderDataVector;
import com.cleanwise.service.api.value.WorkOrderSearchResultViewVector;
import com.cleanwise.service.api.value.WorkOrderSearchResultView;

public class WorkOrderDAO {
    
	private static final String CLW_WORK_ORDER = "CLW_WORK_ORDER";
	private static Category log = Category.getInstance(WorkOrderDAO.class.getName());

	public static HashMap selectWorkOrderPriorities(Connection connection, DBCriteria dbCriteria) throws SQLException{

        StringBuffer sqlBuf = new StringBuffer(
        		"SELECT clw_work_order.priority, COUNT(*) AS cnt FROM clw_work_order");
        String where = dbCriteria.getSqlClause(CLW_WORK_ORDER);

        Iterator it = dbCriteria.getJoinTables().iterator();
        while(it.hasNext()){
	        String otherTable = (String) it.next();
	        if(!CLW_WORK_ORDER.equalsIgnoreCase(otherTable)){
	        	sqlBuf.append("," + otherTable);
	        }
		}

        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        sqlBuf.append("GROUP BY clw_work_order.priority"); 
        
        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = connection.createStatement();

        ResultSet resultSer = stmt.executeQuery(sql);
		HashMap hashMap = new HashMap();
        while (resultSer.next()) {
            hashMap.put(resultSer.getString(1), new Integer(resultSer.getInt(2)));
        }

        resultSer.close();
        stmt.close();

        return hashMap;

	}

	public static WorkOrderSearchResultViewVector selectWorkOrderSearchResult(Connection conn, DBCriteria dbCriteria) 
		throws SQLException 
	{
		return selectWorkOrderSearchResult(conn, dbCriteria, 0);
	}

	private static WorkOrderSearchResultViewVector selectWorkOrderSearchResult(
			Connection conn, DBCriteria dbCriteria, int pMaxRows) throws SQLException {
        StringBuffer sqlBuf;
        String where;
        

        dbCriteria.addJoinTable(BusEntityDataAccess.CLW_BUS_ENTITY + " SITE");
        dbCriteria.addCondition(WorkOrderDataAccess.BUS_ENTITY_ID + " = " + "SITE." + BusEntityDataAccess.BUS_ENTITY_ID);
        
        dbCriteria.addJoinTable(
        		"(select bus_entity_id, clw_value from clw_property where short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER + "') site_property");
        DBCriteria isolCrit = new DBCriteria();
        isolCrit.addCondition(WorkOrderDataAccess.CLW_WORK_ORDER + "." +  WorkOrderDataAccess.BUS_ENTITY_ID + " = " + 
        		"SITE_PROPERTY." + PropertyDataAccess.BUS_ENTITY_ID + "(+)");
        dbCriteria.addIsolatedCriterita(isolCrit);

        sqlBuf = new StringBuffer(
        		"SELECT " +
        			"SITE." + BusEntityDataAccess.SHORT_DESC + " AS SITE_NAME, " +
        			"SITE_PROPERTY." + PropertyDataAccess.CLW_VALUE + " AS DIST_SITE_REF_NO, " +
	        		"CLW_WORK_ORDER.WORK_ORDER_ID," +
	        		"CLW_WORK_ORDER.WORK_ORDER_NUM," +
	        		"CLW_WORK_ORDER.PO_NUMBER, " +
	        		"CLW_WORK_ORDER.SHORT_DESC," +
	        		"CLW_WORK_ORDER.TYPE_CD," +
	        		"CLW_WORK_ORDER.PRIORITY," +
	        		"CLW_WORK_ORDER.STATUS_CD," +
	        		"CLW_WORK_ORDER.ACTUAL_START_DATE," +
	        		"CLW_WORK_ORDER.ACTUAL_FINISH_DATE " +
        		"FROM CLW_WORK_ORDER");

        Collection otherTables = dbCriteria.getJoinTables();
        where = dbCriteria.getSqlClause(CLW_WORK_ORDER);

        Iterator it = otherTables.iterator();
        while(it.hasNext()){
                String otherTable = (String) it.next();
                if(!CLW_WORK_ORDER.equalsIgnoreCase(otherTable)){
                	sqlBuf.append(",");
                	sqlBuf.append(otherTable);
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

        Statement stmt = conn.createStatement();
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
              stmt.setMaxRows(pMaxRows);
        }
        ResultSet rs=stmt.executeQuery(sql);
        WorkOrderSearchResultViewVector v = new WorkOrderSearchResultViewVector();
        while (rs.next()) {
        	WorkOrderSearchResultView view = WorkOrderSearchResultView.createValue();

            view.setSiteName(rs.getString(1));
            view.setDistributorShipToNumber(rs.getString(2));
            view.setWorkOrderId(rs.getInt(3));
            view.setWorkOrderNum(rs.getString(4));
            view.setPoNumber(rs.getString(5));
            view.setShortDesc(rs.getString(6));
            view.setTypeCd(rs.getString(7));
            view.setPriority(rs.getString(8));
            view.setStatusCd(rs.getString(9));
            view.setActualStartDate(rs.getDate(10));
            view.setActualFinishDate(rs.getDate(11));
            v.add(view);
        }

        rs.close();
        stmt.close();

        return v;
	}

}

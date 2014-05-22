package com.cleanwise.service.api.process.operations;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import java.rmi.RemoteException;
import java.sql.*; //import java.rmi.*;
import javax.naming.*;
import com.cleanwise.service.api.framework.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class PhysicalInventoryMaintenance extends ApplicationServicesAPI {

	public static final String USER_NAME = "PhysicalInvMaintenance";

	private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	public void startMaintenance(String command) throws Exception {
		Connection conn = null;
		try {
			conn = getConnection();
			Date runForDate = new Date();
			runForDate = sdf.parse(sdf.format(runForDate));
			LinkedList<DistSchedPhysInt> activeSchedules =
				getActiveSchedules(conn,runForDate);				

			if ("OnHand".equalsIgnoreCase(command)) {
				initPhysicalPeriod(conn,activeSchedules);
				//setZeroQtyOnHand(conn,activeSchedules);				
			}
		} catch (Exception e) {
			throw processException(e);
		} finally {
			closeConnection(conn);
		}
	}

	private void initPhysicalPeriod(Connection conn,
			LinkedList<DistSchedPhysInt> pActiveSchedules) 
	throws Exception {

		logInfo("CleanPhysicalInventoryCart => Start. Schedules :" + ((pActiveSchedules != null) ? pActiveSchedules.size() : 0));

		APIAccess factory = new APIAccess();
		Site siteBean = factory.getSiteAPI();
		Distributor distrBean = factory.getDistributorAPI();
		CorporateOrder corpOrderBean = factory.getCorporateOrderAPI();

		// Get Sites
		for (Iterator iter = pActiveSchedules.iterator(); iter.hasNext();) {
			DistSchedPhysInt dspi = (DistSchedPhysInt) iter.next();
/*   Old Request for Inventory
 * ================================
 			IdVector scheduleSiteIdV = distrBean.getSiteIdsForSchedule(
					dspi.distId, dspi.scheduleId);
			
			DBCriteria dbc = new DBCriteria();
			dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,
					RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY);
			dbc.addEqualToIgnoreCase(PropertyDataAccess.CLW_VALUE, "true");
			String physInvReq = PropertyDataAccess.getSqlSelectIdOnly(
					PropertyDataAccess.BUS_ENTITY_ID, dbc);

			dbc = new DBCriteria();
			dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, physInvReq);
			dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
					RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
			String sitePhysInvReq = BusEntityAssocDataAccess
					.getSqlSelectIdOnly(
							BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

			dbc = new DBCriteria();		
			dbc.addOneOf(InventoryLevelDataAccess.BUS_ENTITY_ID,
					scheduleSiteIdV);
			dbc.addOneOf(InventoryLevelDataAccess.BUS_ENTITY_ID,
							sitePhysInvReq);
			dbc.addIsNotNull(InventoryLevelDataAccess.QTY_ON_HAND);
			dbc.addLessThan(InventoryLevelDataAccess.MOD_DATE,
							dspi.periodStart);
			dbc.addOrderBy(InventoryLevelDataAccess.BUS_ENTITY_ID);

*/
			List<Integer> scheduleSiteIdV = corpOrderBean.getScheduleSites(dspi.scheduleId) ;
			logInfo("CleanPhysicalInventoryCart => Scheduled sites : " + ((scheduleSiteIdV != null) ? scheduleSiteIdV.size() : 0));
			DBCriteria dbc = new DBCriteria();
			
			dbc = new DBCriteria();		
			dbc.addOneOf(InventoryLevelDataAccess.BUS_ENTITY_ID,
					scheduleSiteIdV);
			dbc.addIsNotNull(InventoryLevelDataAccess.QTY_ON_HAND);
			dbc.addLessThan(InventoryLevelDataAccess.MOD_DATE,
							dspi.periodStart);
			dbc.addOrderBy(InventoryLevelDataAccess.BUS_ENTITY_ID);

			InventoryLevelDataVector inventoryLevelDV = 
				InventoryLevelDataAccess.select(conn, dbc);
			logInfo("CleanPhysicalInventoryCart => Items to update : " + ((inventoryLevelDV != null) ? inventoryLevelDV.size() : 0));
			for (Iterator iter1 = inventoryLevelDV.iterator(); iter1.hasNext();) {
				InventoryLevelData ilD = (InventoryLevelData) iter1.next();
				ilD.setQtyOnHand("");
				ilD.setInitialQtyOnHand(null);
//				ilD.setModBy("CleanPhysicalInventoryCart");
				ilD.setModBy("CleanCorporateOrderCart");
				InventoryLevelDataAccess.update(conn,ilD);

			}
		}

	}

	private void setZeroQtyOnHand(Connection conn, 
			LinkedList<DistSchedPhysInt> pActiveSchedules) 
	throws Exception {
 
		//Get Physical Inventory sites with null on hand 
		APIAccess factory = new APIAccess();
		Site siteBean = factory.getSiteAPI();
		Distributor distrBean = factory.getDistributorAPI();

		DBCriteria dbc = new DBCriteria();
		dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,
				RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY);
		dbc.addEqualToIgnoreCase(PropertyDataAccess.CLW_VALUE, "true");
		String physInvReq = PropertyDataAccess.getSqlSelectIdOnly(
				PropertyDataAccess.BUS_ENTITY_ID, dbc);

		dbc = new DBCriteria();
		dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, physInvReq);
		dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
				RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
		String sitePhysInvReq = BusEntityAssocDataAccess
				.getSqlSelectIdOnly(
						BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

		dbc = new DBCriteria();
		dbc.addOneOf(InventoryLevelDataAccess.BUS_ENTITY_ID,
						sitePhysInvReq);
		dbc.addIsNull(InventoryLevelDataAccess.QTY_ON_HAND);
		dbc.addOrderBy(InventoryLevelDataAccess.BUS_ENTITY_ID);

		InventoryLevelDataVector inventoryLevelDV =
			InventoryLevelDataAccess.select(conn, dbc);
		if(inventoryLevelDV.size()==0) {
			return; //nothing to change
		}
    	
		//Get active period sites
		HashSet<Integer> activeSchedulSiteIdV = new HashSet<Integer>();
		for(DistSchedPhysInt dspi : pActiveSchedules) {
			IdVector scheduleSiteIdV = distrBean.getSiteIdsForSchedule(
					dspi.distId, dspi.scheduleId);
			for(Iterator iter = scheduleSiteIdV.iterator(); iter.hasNext();) {
				Integer sIdI = (Integer) iter.next();
				activeSchedulSiteIdV.add(sIdI);
			}			
		}
		
		//Replace null on hand values with 0
		for(Iterator iter = inventoryLevelDV.iterator(); iter.hasNext();) {
			InventoryLevelData ilD = (InventoryLevelData) iter.next();
			int sId = ilD.getBusEntityId();
			if(!activeSchedulSiteIdV.contains(sId)) {
				ilD.setQtyOnHand("0");
				ilD.setModBy(USER_NAME);
				InventoryLevelDataAccess.update(conn, ilD);
			}			
		}
		return;
		
    }

	private LinkedList<DistSchedPhysInt> getActiveSchedules(Connection conn,
			Date runForDate) throws Exception {
		// find active scheudles
		String dateStr = sdf.format(runForDate);
		String sql = "SELECT sch.schedule_id, bus_entity_id, schedule_detail_cd, value \n"
				+ " FROM clw_schedule sch join clw_schedule_detail schd \n"
				+ " ON sch.schedule_id = schd.schedule_id \n"
				+ " WHERE SCHEDULE_DETAIL_CD IN \n"
				+ " ('PHYSICAL_INV_FINAL_DATE','PHYSICAL_INV_START_DATE') \n"
				+ " ORDER BY sch.schedule_id, To_Date (value,'mm/dd/yyyy')";

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		Date periodStart = null;
		Date periodEnd = null;
		int prevSchId = 0;
		LinkedList<DistSchedPhysInt> scheduleLL = new LinkedList<DistSchedPhysInt>();
		while (rs.next()) {
			int schId = rs.getInt("schedule_id");
			if (schId != prevSchId) {
				periodStart = null;
				periodEnd = null;
				prevSchId = schId;
			} else {
				if (periodStart != null && periodEnd != null) {
					continue;
				}
			}
			int distId = rs.getInt("bus_entity_id");
			String valType = rs.getString("schedule_detail_cd");
			String dtS = rs.getString("value");
			Date dt = sdf.parse(dtS);
			if (RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE
					.equals(valType)) {
				if (dt.compareTo(runForDate) <= 0) {
					periodStart = dt;
				} else {
					periodStart = null;
				}
			} else {
				if (dt.compareTo(runForDate) < 0) {
					periodStart = null;
				}
				if (periodStart != null && periodStart.compareTo(dt) <= 0
						&& dt.compareTo(runForDate) >= 0) {
					periodEnd = dt;
					DistSchedPhysInt dspi = new DistSchedPhysInt();
					dspi.distId = distId;
					dspi.scheduleId = schId;
					dspi.periodStart = periodStart;
					dspi.periodEnd = periodEnd;
					scheduleLL.add(dspi);
				}
			}
		}
		stmt.close();
		return scheduleLL;
	}

	private class DistSchedPhysInt {
		int distId;
		int scheduleId;
		Date periodStart;
		Date periodEnd;

		public String toString() {
			return "distId: " + distId + " schId: " + scheduleId + " start: "
					+ periodStart + " end: " + periodEnd;
		}
	}

}

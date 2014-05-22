package com.cleanwise.service.api.process.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.ScheduleDataAccess;
import com.cleanwise.service.api.dao.ScheduleDetailDataAccess;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.Schedule;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ScheduleDetailData;
import com.cleanwise.service.api.value.ScheduleDetailDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.utils.Constants;

/**
 * Sends out 3 type emails:
 *  1. Send Cutoff Time Reminder for user that has right - USER_GETS_EMAIL_CUTOFF_TIME_REMINDER 
 *  2. Send Inventory Counts Due Reminder email to user who has right of USER_GETS_EMAIL_PHYSICAL_INV_COUNTS_PAST_DUE
 *  3. Send NonCompliant Sites Reminder email to user who has right of USER_GETS_EMAIL_PHYSICAL_INV_NON_COMPL_SITE_LISTING
 * Requiers that the account has the physical inventory setup.
 */
public class SendCorporateSchedOrderReminder extends ApplicationServicesAPI{
	private static final Logger log = Logger.getLogger(SendCorporateSchedOrderReminder.class);
	public static final int THRESHOLD_TIME = 3; //hour
	public static final String TIME_FORMAT = "HH:mm";
	public static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	public static final String MESSAGE_CONTENTS = "MESSAGE_CONTENTS";
	public static final String messageTemplate = getMessageTemplate();
	
	Map storeNameMap = new HashMap();


	public void sendReminder(String someValue) throws Exception {

		Date runForDate = new Date();
		log.info("sendReminder()=> Start of the Corporate Order Remider. Date: "+runForDate);
		Connection conn = null;
		try {
			conn = getConnection();
			/**
			 * Send Cutoff Time Reminder email
			 */
			sendCutoffTimeReminder(conn, runForDate);
			
			/**
			 * Get list of sites that QTY_ON_HAND in table CLW_INVENTORY_LEVEL is not set(=null) 
			 * while Corporate schedule is active for inventory shopping
			 */
			List<NonCompliantSiteInfo> nonCompliantSites = getNonCompliantSites(conn, runForDate);
			if (nonCompliantSites.isEmpty())
				return;
			
			/**
			 * Send Inventory Counts Due Reminder email to user who has right of 
			 * USER_GETS_EMAIL_PHYSICAL_INV_COUNTS_PAST_DUE
			 */
			String sql = "SELECT U.USER_ID, U.USER_ROLE_CD, E.EMAIL_ADDRESS \n" +
					"FROM CLW_USER U, CLW_USER_ASSOC UA, CLW_EMAIL E \n" +
				"WHERE U.USER_ID = UA.USER_ID \n" +
				"AND U.USER_ID = E.USER_ID \n" +
				"AND U.USER_STATUS_CD = 'ACTIVE' \n" +
				"AND U.USER_ROLE_CD LIKE ? \n" +
				"AND UA.BUS_ENTITY_ID = ?";
			log.info(sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + UserInfoData.USER_GETS_EMAIL_PHYSICAL_INV_COUNTS_PAST_DUE + "%");			
			sendInventoryCountsDueReminder(conn, pstmt, runForDate, nonCompliantSites);
			
			/**
			 * Send NonCompliant Sites Reminder email to user who has right of 
			 * USER_GETS_EMAIL_PHYSICAL_INV_NON_COMPL_SITE_LISTING
			 */
			pstmt.setString(1, "%" + UserInfoData.USER_GETS_EMAIL_PHYSICAL_INV_NON_COMPL_SITE_LISTING + "%");
			sendInventoryNonCompliantSitesReminder(pstmt, runForDate, nonCompliantSites);
			pstmt.close();
		} catch (Exception e) {
			throw processException(e);
		} finally {
			closeConnection(conn);
		}
	}
	
	private static String getMessageTemplate() {
		StringBuffer automatedMessage = new StringBuffer();
		automatedMessage.append("*************************************************************************************")
		.append(LINE_SEPARATOR)
		.append(LINE_SEPARATOR)
		.append(MESSAGE_CONTENTS)
		.append(LINE_SEPARATOR)
		.append(LINE_SEPARATOR)
		.append("Thank you.")
		.append(LINE_SEPARATOR)
		.append(LINE_SEPARATOR)		
		.append("*************************************************************************************");
		return automatedMessage.toString();
	}

	/**
	 * Send Cutoff Time Reminder email for user with following condition satisfied:
	 * 1. User right USER_GETS_EMAIL_CUTOFF_TIME_REMINDER set to true 
	 * 2. CUTOFF_TIME_EMAIL_REMINDER_CNT is greater than 0
	 * 2. User associated sites ALLOW_CORPORATE_SCHED_ORDER set to true. 
	 * 3. Site has corporate schedule and next cutoff time is approcaching. 
	 * 		If CUTOFF_TIME_EMAIL_REMINDER_CNT=1, will send reminder 24 hours before cutoff. 
	 * 		If CUTOFF_TIME_EMAIL_REMINDER_CNT=2, will send reminder 48 and 24 hours before cutoff. 
	 * @param conn
	 * @param runDate
	 * @throws Exception
	 */
	private void sendCutoffTimeReminder(Connection conn, Date runDate) throws Exception {
		log.info("Starting sendCutoffTimeReminder()");
		APIAccess factory = new APIAccess();
		Schedule scheduleEjb = factory.getScheduleAPI();
		EmailClient emailClientEjb = factory.getEmailClientAPI();
		String sql = "SELECT DISTINCT S.SHORT_DESC site_name, U.USER_ID, P.CLW_VALUE reminder_cnt, E.EMAIL_ADDRESS, BA.BUS_ENTITY2_ID ACCOUNT_ID \n" +
				"FROM CLW_BUS_ENTITY S, CLW_USER U, CLW_USER_ASSOC UA, CLW_PROPERTY P, CLW_EMAIL E, CLW_BUS_ENTITY_ASSOC BA \n" +
				"WHERE EXISTS (SELECT P.BUS_ENTITY_ID FROM CLW_PROPERTY P \n" +
				"  WHERE P.BUS_ENTITY_ID = S.BUS_ENTITY_ID \n" +
				"  AND P.SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER + "' \n" +
				"  AND UPPER(P.CLW_VALUE) = 'TRUE') \n" +
				"AND S.BUS_ENTITY_STATUS_CD = '" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "' \n" +
				"AND S.BUS_ENTITY_ID = ? \n" +
				"AND U.USER_ID = UA.USER_ID \n" +
				"AND U.USER_ID = P.USER_ID \n" +
				"AND U.USER_ID = E.USER_ID \n" +
				"AND U.USER_STATUS_CD = 'ACTIVE' \n" +
				"AND U.USER_ROLE_CD LIKE '%" + UserInfoData.USER_GETS_EMAIL_CUTOFF_TIME_REMINDER + "%' \n" +
				"AND P.SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.CUTOFF_TIME_EMAIL_REMINDER_CNT + "' \n" +
				"AND UA.BUS_ENTITY_ID = S.BUS_ENTITY_ID \n" +
				"AND S.BUS_ENTITY_ID = BA.BUS_ENTITY1_ID \n" +
				"AND BA.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "'";
		log.info(sql);
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		DBCriteria dbc = new DBCriteria();
		dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_TYPE_CD, RefCodeNames.SCHEDULE_TYPE_CD.CORPORATE);    	
		dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_STATUS_CD, RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE);
		IdVector scheduleDV = ScheduleDataAccess.selectIdOnly(conn,ScheduleDataAccess.SCHEDULE_ID,dbc);
		for (int i = 0; i < scheduleDV.size(); i++) {
			int scheduleId = ((Integer)scheduleDV.get(i)).intValue();
			log.info("Process Schedule Id=" + scheduleId);
			Date nextScheduledDate= scheduleEjb.getNextDeliveryDate(scheduleId);
			if (nextScheduledDate == null){
				continue;
			}
			String strNextSchedDate = sdf.format(nextScheduledDate);
			log.info("Next scheduled date: " + nextScheduledDate);
			IdVector siteIds =scheduleEjb.getScheduleSiteIds(scheduleId, null);
			log.info("Found " +siteIds.size() + " sites for scheduleId: " + scheduleId);
			if (siteIds.isEmpty()){
				continue;
			}

			dbc = new DBCriteria();
			dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleId);
			dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME);
			ScheduleDetailDataVector scheduleDetailDV = ScheduleDetailDataAccess.select(conn, dbc);
			if (scheduleDetailDV.isEmpty()){
				continue;
			}
			ScheduleDetailData schDetailD = (ScheduleDetailData) scheduleDetailDV.get(0);
			String strCutoffTime = schDetailD.getValue();
			if (Utility.isSet(strCutoffTime)) {
				Date nextOrderCutoffTime = Utility.parseDate(strCutoffTime, TIME_FORMAT, true);
				GregorianCalendar cutoffCal = new GregorianCalendar();
				GregorianCalendar cutoffTimeCal = new GregorianCalendar();
				cutoffTimeCal.setTime(nextOrderCutoffTime);
				cutoffCal.setTime(nextScheduledDate);
				cutoffCal.set(Calendar.HOUR_OF_DAY, cutoffTimeCal.get(Calendar.HOUR_OF_DAY));
				cutoffCal.set(Calendar.MINUTE, cutoffTimeCal.get(Calendar.MINUTE));

				for (int j = 0; j < siteIds.size(); j++) {
					int siteId = ((Integer)siteIds.get(j)).intValue();
					pstmt.setInt(1, siteId);
					ResultSet rs = pstmt.executeQuery();
					String siteName = null;
					
					int userCnt = 0;
					while (rs.next()){
						userCnt++;						
						siteName = rs.getString("site_name");
						int userId = rs.getInt("USER_ID");
						int cutoffReminderCnt = rs.getInt("reminder_cnt");
						log.info("User (" + userId + ") allow cutoffReminderCnt=" + cutoffReminderCnt + " for Site(" + siteName + ")");

						GregorianCalendar remCutoffCal = new GregorianCalendar();
						remCutoffCal.setTime(cutoffCal.getTime());					
						remCutoffCal.add(Calendar.DAY_OF_YEAR, -cutoffReminderCnt);

						if (remCutoffCal.getTime().before(runDate) && cutoffCal.getTime().after(runDate)) {
							String toEmailAddress = rs.getString("EMAIL_ADDRESS");
							int accountId = rs.getInt("ACCOUNT_ID");
							GregorianCalendar tempCal = new GregorianCalendar();
							tempCal.setTime(remCutoffCal.getTime());
							int cnt = 0;
							while (tempCal.getTime().before(runDate)){
								cnt++;
								tempCal.add(Calendar.DAY_OF_YEAR, +1);
							}
							String subject = siteName + " - Corporate Scheduled Order for " + strNextSchedDate + " Cutoff Time is Approaching – Reminder " + cnt;

							if (!emailClientEjb.wasThisEmailSent(subject, toEmailAddress)) {
								StringBuffer automatedMessage = new StringBuffer();
								automatedMessage.append("This is an automated email.  Do not reply to this email.")
								.append(LINE_SEPARATOR)
								.append(LINE_SEPARATOR)
								.append("This is a reminder that your corporate scheduled order cutoff time of " + strCutoffTime + " for " +
										strNextSchedDate + " for " + siteName + " is approaching.  To ensure that you receive the " +
										"correct amount of product, enter your on-hand values today.");
								String message = new String(messageTemplate);
								message = message.replaceFirst(MESSAGE_CONTENTS, automatedMessage.toString());								
								
								log.info("Send email to " +toEmailAddress + ": " + subject);
										
								emailClientEjb.send(toEmailAddress,
										emailClientEjb.getDefaultFromAddress(accountId),
										subject, message,
										Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, userId);
							}else{
								log.info("Email has previously sent to " +toEmailAddress + ": " + subject);
							}
						}
					}
					log.info(userCnt + " users have right to receiver Cutoff Time Reminder for site: " + siteId);
				}
			}
		}
	}
	
	/**
	 * Get list of sites that satisfy following condition
	 * 	1. Has active CORPORATE schedule
	 *  2. ALLOW_CORPORATE_SCHED_ORDER is set to true
	 *  3. USE_PHYSICAL_INVENTORY on its account is set to true
	 * 	4. QTY_ON_HAND in table CLW_INVENTORY_LEVEL is not set(=null) 
	 * 
	 * @param conn
	 * @param runDate
	 * @return list of site information related to corporate schedule
	 * @throws Exception
	 */
	private List<NonCompliantSiteInfo> getNonCompliantSites(Connection conn, Date runDate) throws Exception {
		log.info("Starting getNonCompliantSites()");
		APIAccess factory = new APIAccess();
		Schedule scheduleEjb = factory.getScheduleAPI();
		List<NonCompliantSiteInfo> nonCompliantSites = new ArrayList<NonCompliantSiteInfo>();

		//find active corporate schedules
		String sql =
			"SELECT sch.schedule_id, bus_entity_id, schedule_detail_cd, value \n"+
			" FROM clw_schedule sch join clw_schedule_detail schd \n"+
			" ON sch.schedule_id = schd.schedule_id \n"+
			" WHERE SCHEDULE_DETAIL_CD IN \n"+
			" ('" + RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE + "','" + 
				RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE + "','" + 
				RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE + "') \n" +
			" and sch.schedule_status_cd = 'ACTIVE' \n" +
			" and sch.SCHEDULE_TYPE_CD='" + RefCodeNames.SCHEDULE_TYPE_CD.CORPORATE + "'\n" +
			" ORDER BY sch.schedule_id, To_Date (value,'mm/dd/yyyy')";

		log.info("SQL: "+sql);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		Date fdate = sdf.parse(sdf.format(runDate));
		Date periodBegin = null;
		Date periodStart = null;
		Date periodEnd = null;
		int prevSchId = 0;
		LinkedList scheduleLL = new LinkedList();
		while (rs.next()) {

			int schId = rs.getInt("schedule_id");
			if(schId != prevSchId) {
				periodBegin = null;
				periodStart = null;
				periodEnd = null;
				prevSchId = schId;
			}
			else {
				if(periodStart!=null && periodEnd!=null) {
					continue;
				}
			}
			int storeId = rs.getInt("bus_entity_id");
			String valType = rs.getString("schedule_detail_cd");
			String dtS = rs.getString("value");
			Date dt = sdf.parse(dtS);
			if(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE.equals(valType)) {
				periodBegin = dt;
			}else if(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE.equals(valType)) {
				periodStart = dt;
			} else {
				if(periodStart.compareTo(fdate)>0)
					periodStart = null;
				if(dt.compareTo(fdate)<0) {
					periodStart = null;
				}
				if (periodStart == null || periodBegin == null)
					continue;
				if(periodBegin.compareTo(periodStart)==0 && periodStart.compareTo(fdate)==0){
					log.info("Start date and End date are same. No email will be sent.");
					continue;
				} else if (periodStart.compareTo(fdate) < 0 && dt.compareTo(fdate) >= 0) {
					periodEnd = dt;
					CorpSchedPhysInt dspi = new CorpSchedPhysInt();
					dspi.storeId = storeId;
					dspi.scheduleId = schId;
					dspi.periodStart = periodStart;
					dspi.periodEnd = periodEnd;

					GregorianCalendar currentTimeThr = new GregorianCalendar();
					currentTimeThr.setTime(dspi.periodEnd);
					currentTimeThr.add(Calendar.DAY_OF_YEAR, 1);
					currentTimeThr.add(Calendar.HOUR, -1 * THRESHOLD_TIME);

					dspi.periodEndThreshold = currentTimeThr.getTime();

					scheduleLL.add(dspi);

				}
			}
		}
		stmt.close();

		//Get Sites
		log.info(scheduleLL.size() + " corporate schedules are active currently");
		for(Iterator iter=scheduleLL.iterator(); iter.hasNext();) {
			CorpSchedPhysInt cspi = (CorpSchedPhysInt) iter.next();
						
			log.info("Processing schedule: "+cspi.scheduleId);
			IdVector scheduleSiteIdV = scheduleEjb.getScheduleSiteIds(cspi.scheduleId, null);
			log.info("Found "+scheduleSiteIdV.size()+" sites associated to corporate schedule");
			LinkedList siteLists = new LinkedList();
			if(scheduleSiteIdV.size()<1000) {
				siteLists.add(scheduleSiteIdV);
			} else {
				int cnt = 0;
				IdVector wrkIdV = new IdVector();
				siteLists.add(wrkIdV);
				for(Iterator iterSite=scheduleSiteIdV.iterator(); iterSite.hasNext();) {
					cnt++;
					if(cnt>999) {
						cnt = 1;
						wrkIdV = new IdVector();
						siteLists.add(wrkIdV);
					}
					wrkIdV.add(iterSite.next());
				}
			}
			
			String specPermReq = " and Il.ITEM_ID not in (select item_id "+
			" from clw_catalog_structure cs, clw_catalog_assoc ca " +
			" where CS.CATALOG_ID = CA.CATALOG_ID " +
			" and CA.BUS_ENTITY_ID = invi.BUS_ENTITY_ID " +
			" and CA.CATALOG_ASSOC_CD ='" +RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' " +
			" and Nvl(CS.STATUS_CD,'ACTIVE') = 'ACTIVE'" +
			" and upper(CS.SPECIAL_PERMISSION) ='TRUE') ";

			for(Iterator iterSiteGr=siteLists.iterator();iterSiteGr.hasNext();) {
				IdVector wrkIdV = (IdVector) iterSiteGr.next();
				if(wrkIdV.size()>0) {
					sql =
						"SELECT DISTINCT il.bus_entity_id as site_id, be.short_desc site_name, bea.bus_entity2_id account_id \n"+
						" FROM clw_inventory_level  il \n"+
						" join clw_bus_entity be \n"+
						"   ON il.bus_entity_id = be.bus_entity_id \n"+
						"   AND be.bus_entity_status_cd = '" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "' \n"+
						" join clw_catalog_assoc ca \n"+
						" 	ON il.bus_entity_id = ca.bus_entity_id \n"+
						"   AND catalog_assoc_cd = '"+
						RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE+"' \n"+
						" join clw_catalog_structure cs \n"+
						"   ON cs.catalog_id = ca.catalog_id \n"+
						"   AND cs.item_id = il.item_id \n"+
						"   AND Nvl(cs.STATUS_CD,'ACTIVE') = 'ACTIVE' \n"+
						" join CLW_INVENTORY_ITEMS invi \n"+
						"   ON invi.BUS_ENTITY_ID = (select BUS_ENTITY2_ID from CLW_BUS_ENTITY_ASSOC bea where bea.bus_entity1_id = il.bus_entity_id) \n"+
						"   AND invi.item_id = il.item_id \n"+
						"   AND invi.status_cd = '"+RefCodeNames.SIMPLE_STATUS_CD.ACTIVE+"' \n" +
						" join clw_bus_entity_assoc bea \n"+
						"   ON il.bus_entity_id = bea.bus_entity1_id \n"+
						"   AND be.bus_entity_status_cd = 'ACTIVE' \n"+
						"   AND bea.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "' \n" +
						" WHERE EXISTS (select p.clw_value from clw_property p, clw_bus_entity_assoc ba \n" +
						"   where p.bus_entity_id = ba.bus_entity2_id \n" +
						"   and ba.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "' \n" +
						"   and short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY + "' \n" +
						"   and ba.bus_entity1_id = il.bus_entity_id \n" +
						"   and upper(p.clw_value) = 'TRUE' \n" +
						"   ) \n" +
						" AND EXISTS (select p.clw_value FROM CLW_PROPERTY p \n" +
						"   WHERE p.bus_entity_id = il.bus_entity_id \n" +
						"   AND p.SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER + "' \n" +
						"   and upper(p.clw_value) = 'TRUE' \n" +
						"   )\n" +
						" AND QTY_ON_HAND IS NULL \n"+
						" AND il.bus_entity_id IN ("+IdVector.toCommaString(wrkIdV)+" ) \n"+
						specPermReq +
						" ORDER BY il.bus_entity_id \n";
					
					

					log.info(getClass().getName()+" SQL: "+sql);
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int siteCnt = 0;
					while (rs.next()) {						
						int siteId = rs.getInt("site_id");
						NonCompliantSiteInfo sitInfo = new NonCompliantSiteInfo();
						sitInfo.siteId = siteId;
						sitInfo.siteName = rs.getString("site_name");
						sitInfo.accountId = rs.getInt("account_id");
						sitInfo.corpSchedPhysInt = cspi;
						nonCompliantSites.add(sitInfo);
						siteCnt++;						
					}
					log.info(siteCnt + " NonCompliant sites found for scheduleId:" + cspi.scheduleId);
					rs.close();
					stmt.close();
				}
			}
		}
		log.info("Total " + nonCompliantSites.size() + " NonCompliant sites found for active Corporate schedule");
		return nonCompliantSites;
	}

	/**
	 * Send Inventory Counts Due Reminder email to user who have right of 
	 * USER_GETS_EMAIL_PHYSICAL_INV_COUNTS_PAST_DUE
	 * @param conn
	 * @param pstmt
	 * @param runDate
	 * @param nonCompliantSites
	 * @throws Exception
	 */
	private void sendInventoryCountsDueReminder(Connection conn, PreparedStatement pstmt, Date runDate, 
			List<NonCompliantSiteInfo> nonCompliantSites) throws Exception {
		log.info("Starting sendInventoryCountsDueReminder()");
		for (NonCompliantSiteInfo siteInfo : nonCompliantSites) {
			boolean shouldBeSent = siteInfo.corpSchedPhysInt.periodEndThreshold.compareTo(runDate) > 0;
			if (shouldBeSent) {
				pstmt.setInt(2, siteInfo.siteId);
				ResultSet rs = pstmt.executeQuery();
				List<String> emailAddrs = new ArrayList<String>();
				while(rs.next()){
					String toEmailAddress = rs.getString("EMAIL_ADDRESS");
					if (!emailAddrs.contains(toEmailAddress))
						emailAddrs.add(toEmailAddress);
				}
				log.info("InventoryCountsDueReminder email for scheduleId " + siteInfo.corpSchedPhysInt.scheduleId + " will be sent to following e-mail addresses:\n" + emailAddrs);
				if (!emailAddrs.isEmpty())
					sendInventoryMissingEmail(conn, runDate, siteInfo, emailAddrs);
			}
		}
	}


	/**
	 * Send NonCompliant Sites Reminder email to user who has right of 
	 * USER_GETS_EMAIL_PHYSICAL_INV_NON_COMPL_SITE_LISTING
	 * @param pstmt
	 * @param runDate
	 * @param nonCompliantSites
	 * @throws Exception
	 */
	private void sendInventoryNonCompliantSitesReminder(PreparedStatement pstmt, Date runDate, 
			List<NonCompliantSiteInfo> nonCompliantSites) throws Exception {
		log.info("Starting sendInventoryNonCompliantSitesReminder()");
		Map<String, List<String>> nonCompliantEmailSites = new HashMap(); // map of toEmailAddr to siteNames (non-compliant site)
		Map<String, Date> siteEndDateMap = new HashMap<String, Date>();
		Map<String, Integer> emailAddrAcctIdMap = new HashMap<String, Integer>();
		for (NonCompliantSiteInfo siteInfo : nonCompliantSites) {
			siteEndDateMap.put(siteInfo.siteName, siteInfo.corpSchedPhysInt.periodEnd);
			pstmt.setInt(2, siteInfo.siteId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String toEmailAddress = rs.getString("EMAIL_ADDRESS");
				List sites = nonCompliantEmailSites.get(toEmailAddress);
            	if(sites == null){
            		sites = new ArrayList<String>();
            		nonCompliantEmailSites.put(toEmailAddress, sites);
            		emailAddrAcctIdMap.put(toEmailAddress, siteInfo.accountId);
            	}
            	if(!sites.contains(siteInfo.siteName)){
            		sites.add(siteInfo.siteName);
            	}
			}			
		}
			
		log.info("Inventory NonCompliant Sites Email will be sent to following e-mail addresses: " +  nonCompliantEmailSites.keySet().toString());
		if (nonCompliantEmailSites.isEmpty())
			return;
		
		APIAccess factory = new APIAccess();
		EmailClient emailClientEjb = factory.getEmailClientAPI();
		Iterator<String> it = nonCompliantEmailSites.keySet().iterator();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");

		String dateS = sdf.format(runDate);
		String subject ="";

		while(it.hasNext()){
			String toEmailAddress = it.next();
			List<String> sites = nonCompliantEmailSites.get(toEmailAddress);
			StringBuffer automatedMessage = new StringBuffer();
			automatedMessage.append("The following sites are non compliant for their physical inventory:")
			.append(LINE_SEPARATOR)
			.append(LINE_SEPARATOR);
			
			Iterator<String> siteIt = sites.iterator();
			while(siteIt.hasNext()){
				String siteName = siteIt.next();
				automatedMessage.append(siteName)
				.append(LINE_SEPARATOR);
			}
			
			String message = new String(messageTemplate);
			message = message.replaceFirst(MESSAGE_CONTENTS, automatedMessage.toString());
			
			//get period end date for site
			String site = (String)sites.get(0);
			Date intervalEnd = siteEndDateMap.get(site);
			String intervalEndS = sdf.format(intervalEnd);
			if(intervalEndS.compareTo(dateS)==0){
				//today is end date, send 6:00 pm e-mail
				GregorianCalendar runDataGC = new GregorianCalendar();
				runDataGC.setTime(runDate);
				if (runDataGC.get(Calendar.HOUR_OF_DAY) >= 18){
					runDataGC.set(Calendar.HOUR_OF_DAY, 18);
					runDataGC.set(Calendar.MINUTE, 0);
					runDataGC.set(Calendar.SECOND, 0);
					dateS = sdf1.format(runDataGC.getTime());
				}
			}
			subject = "Physical inventory non compliant sites for: "+dateS;

			int accountId = emailAddrAcctIdMap.get(toEmailAddress);
			if (emailClientEjb.wasThisEmailSent
					(subject, toEmailAddress) == false) {
				emailClientEjb.send(toEmailAddress,
						emailClientEjb.getDefaultFromAddress(accountId),
						subject, message,
						Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, 0);
				log.info("Send Inventory NonCompliant Sites Email to: " + toEmailAddress+" for "+runDate);
			}else{
				log.info("Inventory NonCompliant Sites Email was already sent to: "+toEmailAddress+" for "+runDate);
			}
		}
	}

	private void sendInventoryMissingEmail(Connection conn, Date runDate, 
			NonCompliantSiteInfo siteInfo, List<String> emailAddrs) throws Exception {
		log.info("Starting sendInventoryMissingEmail()");
		APIAccess factory = new APIAccess();
		EmailClient emailClientEjb = factory.getEmailClientAPI();

		String subject = siteInfo.siteName+ " - Urgent Physical Inventory Counts Past Due " + sdf.format(runDate);
		int storeId = siteInfo.corpSchedPhysInt.storeId;
		String storeName = (String) storeNameMap.get(storeId);
		if (storeName == null){
			BusEntityData storeBusEnt = BusEntityDataAccess.select(conn, storeId);
			storeName = storeBusEnt.getShortDesc();
			storeNameMap.put(storeId, storeName);
		}
		StringBuffer automatedMessage = new StringBuffer();
		automatedMessage.append("Your store has not completed the physical inventory process in " +
				"the " + storeName + " system. It is imperative that your store load its counts today.");
		String message = new String(messageTemplate);
		message = message.replaceFirst(MESSAGE_CONTENTS, automatedMessage.toString());
			
		for (String toEmailAddress : emailAddrs) {
			// Send an email to this person since they
			// are responsible for ordering for the site.
			if (!emailClientEjb.wasThisEmailSent(subject, toEmailAddress)) {					
				emailClientEjb.send(toEmailAddress,
						emailClientEjb.getDefaultFromAddress(siteInfo.accountId),
						subject, message,
						Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, 0);
			}
		}
	}

	private class NonCompliantSiteInfo {
		protected int siteId;
		protected String siteName;
		protected int accountId;
		protected CorpSchedPhysInt corpSchedPhysInt;
		
		public String toString() {
			return "siteId: " + siteId + " siteName: " + siteName + " CorpSchedPhysInt: " + corpSchedPhysInt.toString();
		}
	}

	private class CorpSchedPhysInt {
		protected int storeId;
		protected int scheduleId;
		protected Date periodStart;
		protected Date periodEnd;
		protected Date periodEndThreshold;

		public String toString() {
			return "storeId: " + storeId + " schId: " + scheduleId + " start: " + periodStart + " end: " + periodEnd + " threshold: " + periodEndThreshold;
		}
	}	    
}

package com.cleanwise.service.api.process.operations;

import java.sql.Connection;
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

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityAssocData;
import com.cleanwise.service.api.value.BusEntityAssocDataVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.utils.Constants;

/**
 * Sends out the "Whine" email that gets sent between the end date and the absolute end date for physical inventory.
 * Requiers that the account has the physical inventory setup.
 */
public class SendPhysicalInventoryReminder extends ApplicationServicesAPI{

	public static final String USER_NAME = "PhysicalInvReminder";

    public static final int THRESHOLD_TIME = 3; //hour

	private static final Logger log = Logger.getLogger(SendPhysicalInventoryReminder.class);

    public void sendReminder(String someValue) throws Exception {

        Date runForDate = new Date();
        log.info("sendReminder()=> Start of the Physical Inventory Remider. Date: "+runForDate);
        sitesForPhysicalInvetoryReminder(runForDate);

    }

    private void sitesForPhysicalInvetoryReminder(Date pDate)
         throws Exception {
       Connection conn = null;
       try {
			conn = getConnection();

            APIAccess factory = new APIAccess();
            Site siteBean = factory.getSiteAPI();
            Distributor distrBean = factory.getDistributorAPI();

            //find active scheudles
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String sql =
        "SELECT sch.schedule_id, bus_entity_id, schedule_detail_cd, value \n"+
        " FROM clw_schedule sch join clw_schedule_detail schd \n"+
        " ON sch.schedule_id = schd.schedule_id \n"+
        " WHERE SCHEDULE_DETAIL_CD IN \n"+
        " ('PHYSICAL_INV_FINAL_DATE','PHYSICAL_INV_END_DATE','PHYSICAL_INV_START_DATE') \n"+
        " ORDER BY sch.schedule_id, To_Date (value,'mm/dd/yyyy')";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Date fdate = sdf.parse(sdf.format(pDate));
            Date periodBegin = null;
			Date periodStart = null;
            Date periodEnd = null;
			int prevSchId = 0;
            LinkedList scheduleLL = new LinkedList();
            while (rs.next()) {

			    int schId = rs.getInt("schedule_id");
			    log.info("Looking at "+schId);
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
				int distId = rs.getInt("bus_entity_id");
			    String valType = rs.getString("schedule_detail_cd");
				String dtS = rs.getString("value");
				Date dt = sdf.parse(dtS);
				if(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE.equals(valType)) {
					log.info("Begin date :"+dt);
					periodBegin = dt;
				}else if(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE.equals(valType)) {
					log.info("Line 93 ");
					if(dt.compareTo(fdate)<=0) {
						log.info("Line 95 ");
						periodStart = dt;
					} else {
						log.info("Line 98 ");
						periodStart = null;
					}
				} else {
					log.info("Line 102 ");
				    if(dt.compareTo(fdate)<0) {
						periodStart = null;
					}
					//if(periodStart!=null && periodStart.compareTo(dt)<=0 && dt.compareTo(pDate)>=0) {
				    if(periodStart!=null && periodBegin!=null
				    		&& periodBegin.compareTo(periodStart)==0 && periodStart.compareTo(fdate)==0){
				    	log.info("Start date and End date are same. No email will be sent.");
				    	continue;
				    } else if (periodStart != null && periodStart.compareTo(dt) <= 0 && dt.compareTo(fdate) >= 0) {

                        periodEnd = dt;

                        DistSchedPhysInt dspi = new DistSchedPhysInt();
                        dspi.distId = distId;
                        dspi.scheduleId = schId;
                        dspi.periodStart = periodStart;
                        dspi.periodEnd = periodEnd;

                        GregorianCalendar currentTimeThr = new GregorianCalendar();
                        currentTimeThr.setTime(dspi.periodEnd);
                        currentTimeThr.add(Calendar.DAY_OF_YEAR, 1);
                        currentTimeThr.add(Calendar.HOUR, -1 * THRESHOLD_TIME);

                        dspi.periodEndThreshold = currentTimeThr.getTime();

                        log.info("sitesForPhysicalInvetoryReminder()=> DistSchedPhysInt: " + dspi);

                        scheduleLL.add(dspi);

                    }
				}
            }
            stmt.close();

            HashMap<String, List<String>> nonCompliantEmailSites = new HashMap();
            HashMap<String, Integer> accounts = new HashMap();
            HashMap<String,Date> siteEndDate = new HashMap();

            HashMap subjectHM = new HashMap();
            HashMap textHM = new HashMap();
            HashMap<Integer,Boolean> physicalInvHM = new HashMap<Integer,Boolean>();
            LinkedList ptLL = new LinkedList();
            ptLL.add(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT);
            ptLL.add(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG);
            ptLL.add(RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY);
            //Get Sites
            log.info("scheduleLL size="+scheduleLL.size());
            for(Iterator iter=scheduleLL.iterator(); iter.hasNext();) {
                DistSchedPhysInt dspi = (DistSchedPhysInt) iter.next();
                log.info("Processing schedule: "+dspi.scheduleId);
                IdVector scheduleSiteIdV = distrBean.getSiteIdsForSchedule(dspi.distId,dspi.scheduleId);
                log.info("Found "+scheduleSiteIdV.size()+" sites associated to dist schedule");
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
                log.info("Found "+siteLists.size()+" lists of sites to process");
//////////////////////////
                 String specPermReq = " and Il.ITEM_ID not in (select item_id "+
                      " from clw_catalog_structure cs, clw_catalog_assoc ca " +
                      " where CS.CATALOG_ID = CA.CATALOG_ID " +
                      " and CA.BUS_ENTITY_ID = invi.BUS_ENTITY_ID " +
                      " and CA.CATALOG_ASSOC_CD ='" +RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' " +
                      " and Nvl(CS.STATUS_CD,'ACTIVE') = 'ACTIVE'" +
                      " and upper(CS.SPECIAL_PERMISSION) ='TRUE') ";
                IdVector siteIdV = new IdVector();
                for(Iterator iterSiteGr=siteLists.iterator();iterSiteGr.hasNext();) {
                	IdVector wrkIdV = (IdVector) iterSiteGr.next();
                	if(wrkIdV.size()>0) {
                		sql =
                		    "SELECT DISTINCT il.bus_entity_id as site_id \n"+
                		    " FROM clw_inventory_level  il \n"+
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
     		                "   AND invi.status_cd = '"+RefCodeNames.SIMPLE_STATUS_CD.ACTIVE+"' \n"+
                		    " WHERE QTY_ON_HAND IS NULL \n"+
                		    " AND il.bus_entity_id IN ("+IdVector.toCommaString(wrkIdV)+" ) \n"+
                                    specPermReq +
                		    " ORDER BY il.bus_entity_id \n";

                		log.info(getClass().getName()+" SQL: "+sql);
   		            	stmt = conn.createStatement();
   		            	rs = stmt.executeQuery(sql);
   		            	while (rs.next()) {
    		            	int sId = rs.getInt("site_id");
    		            	siteIdV.add(sId);
   		            	}
   		            	rs.close();
   		            	stmt.close();
                	}
                }

                log.info("After filtering "+siteIdV.size()+" sites to process");
/////////////////////////////////////

                String perEndS = sdf.format(dspi.periodEnd);
                DBCriteria dbc = new DBCriteria();
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteIdV);
                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                        RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
                dbc.addOrderBy(BusEntityAssocDataAccess.BUS_ENTITY1_ID);
                BusEntityAssocDataVector siteAcctDV = BusEntityAssocDataAccess.select(conn, dbc);

                dbc = new DBCriteria();
                dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, siteIdV);
                dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
                BusEntityDataVector siteBeDV = BusEntityDataAccess.select(conn, dbc);

                BusEntityData wrkBeD = null;
                log.info("Looping through "+siteAcctDV.size()+" accounts");
                for(Iterator iter1 = siteAcctDV.iterator(), iterSite=siteBeDV.iterator();
                                                             iter1.hasNext();) {
                    BusEntityAssocData bea = (BusEntityAssocData) iter1.next();
                    int acctId = bea.getBusEntity2Id();
                    int siteId = bea.getBusEntity1Id();
                    String siteName = "";
                    while(iterSite.hasNext() || wrkBeD!=null) {
                        if(wrkBeD==null) wrkBeD = (BusEntityData) iterSite.next();
                        int sId = wrkBeD.getBusEntityId();
                        if(sId==siteId) {
                            siteName = wrkBeD.getShortDesc();
                            break;
                        } else if(sId<siteId) {
                            wrkBeD = null;
                            continue;
                        }
                        break;
                    }
                    if(RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE.equals(wrkBeD.getBusEntityStatusCd())){
                    	continue;
                    }

                    if(!siteEndDate.containsKey(siteName)){
                    	siteEndDate.put(siteName, dspi.periodEnd);
                    }

                    String subject = (String) subjectHM.get(acctId);
                    log.info("Subject = "+subject);
                    String text = null;
                    boolean physicalInvFl = false;
                    if(subject == null) {

                        dbc = new DBCriteria();
                        dbc.addOneOf(PropertyDataAccess.SHORT_DESC, ptLL);
                        dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, acctId);
                        PropertyDataVector pDV =
                                PropertyDataAccess.select(conn, dbc);

                        for(Iterator iter3=pDV.iterator(); iter3.hasNext();) {
                            PropertyData pD = (PropertyData) iter3.next();
                            String shortDesc = pD.getShortDesc();
                            if(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT
                                    .equals(shortDesc)) {
                                subject = pD.getValue();
                            } else
                            if(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG
                                    .equals(shortDesc)) {
                                text = pD.getValue();
                            } else
                            if(RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY
                                    .equals(shortDesc)) {
                                String val = pD.getValue();
                                physicalInvFl = Utility.isTrue(val, false);

                            }

                        }
                        if(true /*!Utility.isSet(subject)*/) {
                            subject = " -- ";
                        }
                        subjectHM.put(acctId, subject);
                        if(text!=null) textHM.put(acctId, text);
                        physicalInvHM.put(acctId, physicalInvFl);
                   }
                   physicalInvFl = physicalInvHM.get(acctId);
                   if(!physicalInvFl) {
                       continue;
                   }
                   log.info("About to send email");
                   sendInventoryMissingEmail(conn, dspi, siteId, acctId, siteName, pDate, nonCompliantEmailSites, accounts);
                }
            }

            sendInventoryMissingEmailSummaries(nonCompliantEmailSites, pDate, siteEndDate,accounts);

       } catch (Exception e) {
         throw processException(e);
       } finally {
         closeConnection(conn);
       }

     }

    /**
     * Sends the summary email to the corporate users.  The nonCompliantEmailSites @see HashMap is built in the @see sendInventoryMissingEmail method
     */
    private void sendInventoryMissingEmailSummaries(HashMap<String, List<String>> nonCompliantEmailSites,
                                                    Date pDate,
                                                    HashMap<String,Date> pSitesEndDates,
                                                    HashMap<String,Integer> accounts) throws Exception{

    	APIAccess factory = APIAccess.getAPIAccess();
    	EmailClient emailClientEjb = factory.getEmailClientAPI();

        Iterator<String> it = nonCompliantEmailSites.keySet().iterator();

    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");

    	String dateS = sdf.format(pDate);
    	String lSubject ="";

    	while(it.hasNext()){
    		String lToEmailAddress = it.next();
    		List<String> sites = nonCompliantEmailSites.get(lToEmailAddress);

    		int acctId = accounts.get(lToEmailAddress).intValue();

    		StringBuffer lMessage = new StringBuffer("The following sites are non compliant for their physical inventory:");
    		lMessage.append('\n');
    		Iterator<String> siteIt = sites.iterator();
    		while(siteIt.hasNext()){
    			String siteName = siteIt.next();
    			lMessage.append(siteName);
    			lMessage.append('\n');
    		}

    		//get period end date for site
    		String site = (String)sites.get(0);
    		Date pIntervalEnd = pSitesEndDates.get(site);
    		String pIntervalEndS = sdf.format(pIntervalEnd);
    		if(pIntervalEndS.compareTo(dateS)==0){
        		//today is end date
        		dateS = sdf1.format(pDate);
        	}
    		log.info("[SendPhysicalInventoryReminder]: Current date: "+dateS);
        	lSubject = "Physical inventory non compliant sites for: "+dateS;

    		if (emailClientEjb.wasThisEmailSent
                    (lSubject, lToEmailAddress) == false) {
                    emailClientEjb.send(lToEmailAddress,
                            emailClientEjb.getDefaultFromAddress(acctId),
                            lSubject, lMessage.toString(),
                            Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, 0);
            }else{
            	log.info("Corporate email was already sent to: "+lToEmailAddress+" for "+pDate);
            }
    	}
    }

    private String sendInventoryMissingEmail(Connection conn,
                                             DistSchedPhysInt pDspi,
                                             int pSiteId,
                                             int pAccountId,
                                             String pSiteName,
                                             Date pDate,
                                             HashMap<String, List<String>> nonCompliantEmailSites,
                                             HashMap<String,Integer> accounts)
    {
        try {

            APIAccess factory = APIAccess.getAPIAccess();

            EmailClient emailClientEjb = factory.getEmailClientAPI();
            User userBean = factory.getUserAPI();

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");


            String lSubject = pSiteName+ " - Urgent Physical Inventory Counts Past Due " + sdf.format(pDate);
            String lMsg= "Your store has not completed the Physical Inventory process in the xpedx Orderline system. It is imperative that your store load it's counts today.";

            String emailsSentMsg = "";

            Date now = new Date();
            boolean shouldBeSent = pDspi.getPeriodEndThreshold().compareTo(now) > 0;
            if (!shouldBeSent) {
                log.info("sendInventoryMissingEmail()=> pDspi: " + pDspi);
                log.info("sendInventoryMissingEmail()=> No email will be sent" +
                        " for SiteID: " + pSiteId + "(" + pSiteName + ")" +
                        " because threshold time has been exceeded." +
                        " Now: " + now + ", Threshold: " + pDspi.getPeriodEndThreshold());
            }

      /*
                 * The check for order approver and order rejected notification should be removed.
                 */
            UserDataVector uv =
                        userBean.getUsersCollectionByBusEntity(pSiteId,null);
            log.info("uv size ="+uv.size());
            for (int idx = 0; idx < uv.size(); idx++) {
                UserData ud = (UserData) uv.get(idx);
                UserInfoData u = userBean.getUserContact(ud.getUserId());
                String lToEmailAddress = u.getEmailData().getEmailAddress();
                if (!RefCodeNames.USER_STATUS_CD.ACTIVE.equals(ud.getUserStatusCd())) {
                    continue;
                }

                PropertyUtil putil = new PropertyUtil(conn);
                boolean isCorp = Utility.isTrue(putil.fetchValueIgnoreMissing(ud.getUserId(), 0, RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER));
                boolean isMissing = Utility.isTrue(putil.fetchValueIgnoreMissing(ud.getUserId(), 0, RefCodeNames.PROPERTY_TYPE_CD.RECEIVE_INV_MISSING_EMAIL));
                if(isCorp && isMissing){
                	//create a mapping of emails to sites that need to be sent an email to.
                	List sites = nonCompliantEmailSites.get(lToEmailAddress);
                	if(sites == null){
                		sites = new ArrayList<String>();
                		nonCompliantEmailSites.put(lToEmailAddress, sites);
                	}
                	if(!sites.contains(pSiteName)){
                		sites.add(pSiteName);
                	}

                	Integer acct = accounts.get(lToEmailAddress);
                	if(acct==null || acct<0){
                		accounts.put(lToEmailAddress, new Integer(pAccountId));
                	}
                }else if(isMissing && shouldBeSent){
	                // Send an email to this person since they
	                // are responsible for ordering for the site.
	                if (emailClientEjb.wasThisEmailSent
	                        (lSubject, lToEmailAddress) == false) {
	                        emailClientEjb.send(lToEmailAddress,
	                                emailClientEjb.getDefaultFromAddress(pAccountId),
	                                lSubject, lMsg,
	                                Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, ud.getUserId());
	                }
	                emailsSentMsg += "\nInventoryMissing "
	                         + "\n[ Sent email to: " + lToEmailAddress +
	                        "\n  Subject:" + lSubject
	                         + "\n Username:" + ud.getUserName() + "]";
                }

            }
            return emailsSentMsg;
        } catch (Exception exc) {
            String mess = "Site.sendInventoryMissingEmail: " +
                    exc.getMessage();
            logError(mess);
        }
        return " No email sent.";
    }

private class DistSchedPhysInt {

    protected int distId;
    protected int scheduleId;
    protected Date periodStart;
    protected Date periodEnd;
    protected Date periodEndThreshold;

    public int getDistId() {
        return distId;
    }

    public void setDistId(int distId) {
        this.distId = distId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(Date periodStart) {
        this.periodStart = periodStart;
    }

    public Date getPeriodEndThreshold() {
        return periodEndThreshold;
    }

    public void setPeriodEndThreshold(Date periodEndThreshold) {
        this.periodEndThreshold = periodEndThreshold;
    }

    public Date getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Date periodEnd) {
        this.periodEnd = periodEnd;
    }

    public String toString() {
        return "distId: " + distId + " schId: " + scheduleId + " start: " + periodStart + " end: " + periodEnd + " threshold: " + periodEndThreshold;
    }

}

}

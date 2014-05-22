package com.cleanwise.service.api.process.operations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.InventoryLevelDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.EmailClient;
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
import com.cleanwise.service.api.value.InventoryLevelData;
import com.cleanwise.service.api.value.InventoryLevelDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.utils.Constants;

/**
 * Send out the reminder email BEFORE the start of the physical inventory period.  This email gets sent once to
 * all users of sites that belong to the delivery schedule that is about to have it's physical inventory start.  Also
 * requiers that the account is setup for physical inventory. 
 */
public class SendPhysicalInventoryPeriodInfo extends ApplicationServicesAPI{

	public static final String USER_NAME = "PhysicalInvReminder";
	
	private static final Logger log = Logger.getLogger(SendPhysicalInventoryReminder.class);

    public void sendReminder(String someValue) throws Exception {

        Date runForDate = new Date();
        sitesForPhysicalInvetoryReminder(runForDate);

    }

    private void sitesForPhysicalInvetoryReminder(Date pDate)
         throws Exception {
       Connection conn = null;
       try {
			conn = getConnection();

            APIAccess factory = new APIAccess();
            Distributor distrBean = factory.getDistributorAPI();

            //find active scheudles
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String dateStr = sdf.format(pDate);
			String sql =
        "SELECT sch.schedule_id, bus_entity_id, schedule_detail_cd, value \n"+
        " FROM clw_schedule sch join clw_schedule_detail schd \n"+
        " ON sch.schedule_id = schd.schedule_id \n"+
        " WHERE SCHEDULE_DETAIL_CD IN \n"+
        " ('PHYSICAL_INV_START_DATE') \n"+
        " AND To_Date (value,'mm/dd/yyyy') > To_Date ('"+dateStr+"','mm/dd/yyyy') \n"+
        " AND To_Date (value,'mm/dd/yyyy') <= (To_Date ('"+dateStr+"','mm/dd/yyyy')+1) \n"+
        " ORDER BY sch.schedule_id, To_Date (value,'mm/dd/yyyy')";
			

			log.info(" SQL: "+sql);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
			Date periodStart = null;
			int prevSchId = 0;
            LinkedList scheduleLL = new LinkedList();
            while (rs.next()) {
			    int schId = rs.getInt("schedule_id");
			    log.info("got schedule id: "+schId);
				if(schId != prevSchId) {
					periodStart = null;
					prevSchId = schId;
				} else {
					if(periodStart!=null) {
						continue;
					}
				}
				int distId = rs.getInt("bus_entity_id");
			    String valType = rs.getString("schedule_detail_cd");
				String dtS = rs.getString("value");
				Date dt = sdf.parse(dtS);
				if(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE.equals(valType)) {
					periodStart = dt;
					DistSchedPhysInt dspi = new DistSchedPhysInt();
					dspi.distId = distId;
					dspi.scheduleId = schId;
					dspi.periodStart = periodStart;
					//dspi.periodEnd = periodEnd;
					scheduleLL.add(dspi);
				}
            }
            stmt.close();
            log.info("found schedules to process: "+scheduleLL.size());
            HashMap subjectHM = new HashMap();
            HashMap textHM = new HashMap();
            HashMap<Integer,Boolean> physicalInvHM = new HashMap<Integer,Boolean>();
            LinkedList ptLL = new LinkedList();
            //ptLL.add(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT);
            //ptLL.add(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG);
            ptLL.add(RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY);
            //Get Sites
            for(Iterator iter=scheduleLL.iterator(); iter.hasNext();) {
                DistSchedPhysInt dspi = (DistSchedPhysInt) iter.next();
                if(dspi != null){
                	log.info("Processing schedule id: "+dspi.scheduleId);
                }
                IdVector scheduleSiteIdV =
                    distrBean.getSiteIdsForSchedule(dspi.distId,dspi.scheduleId);
                if(scheduleSiteIdV == null || scheduleSiteIdV.size() == 0){
                    log.info("No Sites configured for delivery schedule id: "+dspi.scheduleId+" ... no emails will be sent related to this physical inventory schedule");
                }else{
                    log.info("Found "+scheduleSiteIdV.size()+" sites for schedule id: "+dspi.scheduleId);
                }
                DBCriteria dbc = new DBCriteria();
                dbc.addOneOf(InventoryLevelDataAccess.BUS_ENTITY_ID, scheduleSiteIdV);
                //dbc.addIsNull(InventoryLevelDataAccess.QTY_ON_HAND);
                dbc.addOrderBy(InventoryLevelDataAccess.BUS_ENTITY_ID);
                InventoryLevelDataVector invLeveDV = InventoryLevelDataAccess.select(conn, dbc);

                log.info(InventoryLevelDataAccess.getSqlSelectIdOnly("*",dbc));
                if(invLeveDV != null){
                	log.info("Found sites: "+invLeveDV.size());
                }
                IdVector siteIdV = new IdVector();
                int prevSiteId = 0;
                for(Iterator iter1=invLeveDV.iterator(); iter1.hasNext();) {
                    InventoryLevelData ilD = (InventoryLevelData) iter1.next();
                    int sId = ilD.getBusEntityId();
                    if(prevSiteId!=sId) {
                        prevSiteId = sId;
                        siteIdV.add(sId);
                    }
                }
                log.debug("Found unique sites: "+siteIdV.size());

               // String perEndS = sdf.format(dspi.periodEnd);
                dbc = new DBCriteria();
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteIdV);
                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                        RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
                dbc.addOrderBy(BusEntityAssocDataAccess.BUS_ENTITY1_ID);
                BusEntityAssocDataVector siteAcctDV = BusEntityAssocDataAccess.select(conn, dbc);
                log.info("siteAcctDV size="+siteAcctDV.size());

                dbc = new DBCriteria();
                dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, siteIdV);
                dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
                BusEntityDataVector siteBeDV = BusEntityDataAccess.select(conn, dbc);
                BusEntityData wrkBeD = null;
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
                            log.info("Found site, breaking");
                            break;
                        } else if(sId<siteId) {
                            wrkBeD = null;
                            continue;
                        }
                        log.info("Breaking!");
                        break;
                    }
                    if(RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE.equals(wrkBeD.getBusEntityStatusCd())){
                        log.info("Skipping inactive site: "+siteId);
                    	continue;
                    }

                    String subject = (String) subjectHM.get(acctId);
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
                        if(subject==null) {
                            subject = " -- "
                            + siteName
                            + " Physical Inventory Starts On: " + periodStart
                            + " On hand quantities are not set";

                        }
                        subjectHM.put(acctId, subject);
                        if(text!=null) textHM.put(acctId, text);
                        physicalInvHM.put(acctId, physicalInvFl);
                   }
                    
                   physicalInvFl = physicalInvHM.get(acctId);
                   if(!physicalInvFl) {
                	   log.info("Physical inventory not set for account id: "+acctId);
                       continue;
                   }
                   
                   
                   sendInventoryStartsEmail(conn, siteId, acctId, siteName, subject, text, periodStart);
                }
            }

       } catch (Exception e) {
         throw processException(e);
       } finally {
         closeConnection(conn);
       }

     }

    private String sendInventoryStartsEmail(Connection conn,
            int pSiteId,int pAccountId,
            String pSiteName, String pSubject, String pText, Date pIntervalStart)
    {
        try {

        	log.info("in sendInventoryStartsEmail");
            APIAccess factory = APIAccess.getAPIAccess();
            EmailClient emailClientEjb = factory.getEmailClientAPI();
            User userBean = factory.getUserAPI();

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String fD = sdf.format(pIntervalStart);

			String lSubject = "Physical Inventory starts on "+fD+" for site "+pSiteName;
            //String lSubject = pSubject + " Inventory Interval Ends on "+fD;
            String lMsg = "This email is to inform you that your physical inventory period "+
			" starts on " + fD + " for site "+pSiteName+".  You MUST enter your on hand quantities."+
            " Please go to orderline.xpedx.com and click on the link to download your "+
			"Physical Count Sheet.";

            String emailsSentMsg = "";

                /*
                 * The check for order approver and order rejected notification should be removed.
                 */
            UserDataVector uv =
                        userBean.getUsersCollectionByBusEntity(pSiteId,null);
            for (int idx = 0; idx < uv.size(); idx++) {
                UserData ud = (UserData) uv.get(idx);
                UserInfoData u = userBean.getUserContact(ud.getUserId());
                String lToEmailAddress = u.getEmailData().getEmailAddress();
                if (!RefCodeNames.USER_STATUS_CD.ACTIVE.equals(ud.getUserStatusCd())) {
                    continue;
                }
                
                PropertyUtil putil = new PropertyUtil(conn);
                //do not send email if corporate user
                boolean isCorp = Utility.isTrue(putil.fetchValueIgnoreMissing(ud.getUserId(), 0, RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER));
                if(isCorp){
                	log.info("Not sending email since corporate user "+ud.getUserId());
                	continue;
                }
                
                log.info("Sending email to user: "+lToEmailAddress);
                
                boolean isMissing = Utility.isTrue(putil.fetchValueIgnoreMissing(ud.getUserId(), 0, RefCodeNames.PROPERTY_TYPE_CD.RECEIVE_INV_MISSING_EMAIL));
                if(!isMissing){
                	log.info("Not sending email to user as they are not setup for inventory missing emails: "+ud.getUserName());
                	continue;
                }
                // Send an email to this person since they
                // are responsible for ordering for the site.
                if (emailClientEjb.wasThisEmailSent
                        (lSubject, lToEmailAddress) == false) {
                        emailClientEjb.send(lToEmailAddress,
                                emailClientEjb.getDefaultFromAddress(pAccountId),
                                lSubject, lMsg,
                                Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, ud.getUserId());

                }
                emailsSentMsg += "\nPhysicalInventoryStartsInfo "
                         + "\n[ Sent email to: " + lToEmailAddress +
                        "\n  Subject:" + lSubject
                         + "\n Username:" + ud.getUserName() + "]";

            }

            return emailsSentMsg;
        } catch (Exception exc) {
            String mess = "Site.sendInventoryMissingEmail: " +
                    exc.getMessage();
            log.error(mess, exc);
        }
        log.info("No email sent.");
        return " No email sent.";
    }

	private class DistSchedPhysInt {
		int distId;
		int scheduleId;
		Date periodStart;
		Date periodEnd;
	}

}

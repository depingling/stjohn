package com.cleanwise.service.apps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.pipeline.PendingOrderNotification;
import com.cleanwise.service.api.reporting.InventoryMissingEmailReport;
import com.cleanwise.service.api.reporting.InventoryScheduleReport;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.AccountHome;
import com.cleanwise.service.api.session.AutoOrder;
import com.cleanwise.service.api.session.AutoOrderHome;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Content;
import com.cleanwise.service.api.session.ContentHome;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.DistributorHome;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.EmailClientHome;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.IntegrationServicesHome;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.session.OrderGuideHome;
import com.cleanwise.service.api.session.OrderHome;
import com.cleanwise.service.api.session.Pipeline;
import com.cleanwise.service.api.session.PipelineHome;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.PropertyServiceHome;
import com.cleanwise.service.api.session.PurchaseOrder;
import com.cleanwise.service.api.session.Report;
import com.cleanwise.service.api.session.ReportHome;
import com.cleanwise.service.api.session.Request;
import com.cleanwise.service.api.session.RequestHome;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.SiteHome;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.StoreHome;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.session.UserHome;
import com.cleanwise.service.api.session.Workflow;
import com.cleanwise.service.api.session.WorkflowHome;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.JNDINames;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ScheduleProc;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.CatalogStructureDataVector;
import com.cleanwise.service.api.value.ContentData;
import com.cleanwise.service.api.value.ContentDataVector;
import com.cleanwise.service.api.value.CopySiteRequest;
import com.cleanwise.service.api.value.EntitySearchCriteria;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailView;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.GenericReportView;
import com.cleanwise.service.api.value.GenericReportViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemMetaDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderStatusCriteriaData;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.PreparedReportView;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.ReportResultData;
import com.cleanwise.service.api.value.ScheduleJoinView;
import com.cleanwise.service.api.value.ScheduleOrderDates;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.service.api.value.UserInfoDataVector;
import com.cleanwise.view.utils.Constants;


public class AppCmd extends ClientServicesAPI{

    private static final Logger log = Logger.getLogger(AppCmd.class);

    private String currentTask;

    public class SiteReqUtil {
        Site siteBean;

        public SiteReqUtil(Properties props) {
            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                        (JNDINames.SITE_EJBHOME);
                SiteHome sHome = (SiteHome)
                        PortableRemoteObject.narrow (ref, SiteHome.class);
                siteBean = sHome.create();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void checkSite(int sid) throws Exception {
            SiteData sD =
                    siteBean.getSite(sid, 0);
            log.info("sD=" + sD);
        }

        public void cpSite(CopySiteRequest req)
                throws Exception {

            log.info("req=" + req);

            SiteData cpsD = siteBean.copySite(req);

            log.info("cpsD=" + cpsD);
        }

    }

    public static void main(String arg[]) throws Exception {

        AppCmd ac = new AppCmd();
        ac.doCmd();
    }

    public void doCmd() throws Exception {

        String usage = "Usage: AppCmd -Dconf=app.properties -Dcmd=CMD\n" +
                "Where CMD can be: " +
                "\n process_workflow "+
                "\n status "+
                "\n process_auto_orders [-DaccountIds] "+
                "\n process_auto_orders [-DsiteIds] "+
                "\n process_removed_og_items " +
                "\n process_inventory_orders -Dsiteid=optional_id" +
                "\n process_scheduled_orders -DsiteIds=optional_ids -DbegDate=optional <yyyyMMddHHmm> -DendDate=optional <yyyyMMddHHmm> to run for a specific date"+
                "\n process_schedule -DbegDate=optional <yyyyMMddHHmm> -DendDate=optional <yyyyMMddHHmm> to run for a specific date"+
                "\n process_schedule_by_report -DsiteIds=optional_ids -DbegDate=optional <yyyyMMddHHmm> -DendDate=optional <yyyyMMddHHmm> to run for a specific date " +
                "-DreportName=required if siteIds is not set -DinvModernShopping=optional<true|false> inventory shopping type"+
                "\n send_inventory_missing_email -DreportName=<Report Name> -DuserName=<userName> [-DaccountIds=accountId1,accountId2,...] [-DsiteIds=siteId1,siteId2,...] "+ 
                "\n send_order_emails" +
                "\n shopping_cart_reminder"+
                "\n release_lawson_pos"+
                "\n to_lawson_pipeline Sends orders to lawson" +
                "\n orders_to_lawson process orders (does not actually send them to lawson)" +
                "\n orders_from_lawson | invoices_from_lawson" +
                "\n order_through_pipeline -Dorder_id=clw_order.order_id" +
                "\n ssp (set site property) | ssw (set site property)" +
                "\n checksite | cpsite " +
                "\ncopy_order -Dorder_id=<source order id>" +
                "\n remits_to_lawson | invoices_to_lawson" +
                "\n self_store_dist_invoices "
                + "\n add_content -Dhost=hostname -Dcontent=path "
                + "\n synch_content -DrefetchAllContent=<optional, defaults to false true|false if true will refresh all content regardless of date updated> "
                + "\n item_info_to_lawson -Dsku=optional_sku_num"
                + "\n daily_order_reprocess -DDate=optional <MMddyyyy> to run for a specific date -DorderNum=optional order to reprocess -DmaxRows=optional maximum rows to process (default=500,-1 for unlimited rows)"
                + "\n daily_multi_order_reprocess -DbegDate=optional <MMddyyyy> -DendDate=optional <MMddyyyy> to run for a specific date -DorderNum=optional order to reprocess " +
                "-DdbOperation=optional <Y or N> starts operation of a database -DlogFileName=optional -DlogLevel=optional {<1>-Exception,<2>-Report,<3>-Message,<4>-Debug }"

                + "\n cancel_po_item -Derp_order_num=num -Dsku_num=cw.skuNum"
                + "\n invoice_po_item -Derp_order_num=num -Dsku_num=cw.skuNum"
                + "\n reset_cost_centers resets the cost centers for the system"
                + "\n create_fiscal_cal_cache_table (creates the fiscal calendar caching table to be used for reporting)"
                + "\n set_outbound_po_num -DbegDate=<MM/dd/yyyy> -DendDate=<MM/dd/yyyy>"
                + "\n create_item_content -DcatalogId=id"
                + "\n upload_logos"
                ;

        String cmd = System.getProperty("cmd");
        if ( null == cmd )
        {
            log.info( usage );
            return;
        }

        currentTask = cmd.trim().toUpperCase();

        // Check for a properties file command option.
        String propFileName = System.getProperty("conf");
        if ( null == propFileName )
        {
            log.info( usage );
            return;
        }
        Properties props = new Properties();
        props.load(new FileInputStream (propFileName) );

        if ( cmd.equals("process_auto_orders_deprecated") )
        {

            Date startDate = new Date();
            log.info("AutoOrder processing initiated: " +
                               startDate );
            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                    (JNDINames.AUTO_ORDER_EJBHOME);
                AutoOrderHome aoHome = (AutoOrderHome)
                    PortableRemoteObject.narrow (ref, AutoOrderHome.class);
                AutoOrder aoBean = aoHome.create();
                Object emailClientRef = jndiContext.lookup
                    (JNDINames.EMAIL_CLIENT_EJBHOME);
                EmailClientHome emailClientHome = (EmailClientHome)
                    PortableRemoteObject.narrow(emailClientRef,EmailClientHome.class);
                EmailClient emailClientEjb = emailClientHome.create();
                String defaultEmailAddress = emailClientEjb.getDefaultEmailAddress();

                String accounts = System.getProperty("accountIds");
                IdVector accountIdV = null;
                if(accounts==null) {
                    log("process_auto_orders. No account ids provided");
                    return;
                }
                boolean minusFl = false;
                boolean plusFl = false;
                if(!"all".equalsIgnoreCase(accounts.trim())) {
                    accountIdV = new IdVector();
                    String[] accountIdA = Utility.parseStringToArray(accounts,",");
                    for(int ii=0; ii<accountIdA.length;ii++) {
                        try {
                            int accountId = Integer.parseInt(accountIdA[ii]);
                            if(accountId>0) plusFl = true;
                            if(accountId<0) {
                                minusFl = true;
                                accountId = -accountId;
                            }
                            accountIdV.add(new Integer(accountId));

                        }catch(Exception exc) {
                            log("process_auto_orders. Error. Invalid account id format. Account id = "+accountIdA[ii] );
                            return;
                        }
                    }
                    if(minusFl && plusFl) {
                        log("process_auto_orders. Error. Incompatible set of account ids. Both plus and minus present ");
                        return;
                    }
                }

                Date today = new Date();
                IdVector orderSchIdV  =
                            aoBean.getOrderSchedules(today, accountIdV, plusFl);
                for(Iterator iter=orderSchIdV.iterator(); iter.hasNext();){
                   Integer orderSchIdI = (Integer) iter.next();
                   int orderSchId = orderSchIdI.intValue();
                   String resultMess = null;
                   try {
                     String emailMess =
                            aoBean.placeAutoOrder(orderSchId, today, "System");
                     if(emailMess!=null) {
                       try {
                         aoBean.sendOrderNotification(orderSchId, emailMess, "System");
                       } catch (Exception exc1) {}
                     }
                     resultMess = "Order Schedule "+orderSchId+" was processed";
                   } catch (Exception exc) {
                     resultMess = "Order Schedule "+orderSchId+" failed";
                     try {
                        //log error result
                        aoBean.logErrorRecord(orderSchId,
                                            exc.getMessage(), today, "System");
                     } catch (Exception exc1) {}

                     try {
                      //send error email
                       String detailErrorMess = exc.getMessage();
                       if(detailErrorMess==null || detailErrorMess.trim().length()==0) {
                         detailErrorMess = resultMess;
                       }
                       int ind1 = detailErrorMess.indexOf("^clw^");
                       if(ind1>0 && ind1+5<detailErrorMess.length()) {
                         int ind2 = detailErrorMess.indexOf("^clw^",ind1+5);
                         if(ind2>0) detailErrorMess =
                                            detailErrorMess.substring(ind1+5,ind2);
                       }
                       emailClientEjb.send(defaultEmailAddress,
					   emailClientEjb.getDefaultEmailAddress(),
					   resultMess,
					   detailErrorMess, null, 0,0);

                     } catch (Exception exc1) {}
                   }
                   log.info(resultMess);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("AutoOrder processing done:      " +
                               endDate );

            return;
        } else if (cmd.equals("process_auto_work_orders")) {
        		
            Date startDate = new Date();
            Date runDate = null;
            log("AutoOrder processing initiated: " + startDate);
            try {

                String specDate = System.getProperty("sDate");
                String sites = System.getProperty("siteIds");
                if (sites == null) {
                    log("process_auto_work_orders. No site ids provided");
                    return;
                }

                InitialContext jndiContext = new InitialContext(props);
                Object ref = jndiContext.lookup(JNDINames.AUTO_ORDER_EJBHOME);
                AutoOrderHome aoHome = (AutoOrderHome) PortableRemoteObject.narrow(ref, AutoOrderHome.class);
                AutoOrder aoBean = aoHome.create();
                Object emailClientRef = jndiContext.lookup(JNDINames.EMAIL_CLIENT_EJBHOME);
                EmailClientHome emailClientHome = (EmailClientHome) PortableRemoteObject.narrow(emailClientRef, EmailClientHome.class);
                EmailClient emailClientEjb = emailClientHome.create();
                String defaultEmailAddress = emailClientEjb.getDefaultEmailAddress();

                IdVector siteIds = Utility.parseIdStringToVector(sites, ",");
                if (siteIds.isEmpty()) {
                    log.info("it's impossible to parse a site ids," +
                            " the reason is \"Not correct input string :  [" + sites + "]\"" +
                            " Input string must use format <siteId1,siteId2,siteId3...>");
                    return;

                }
                if (Utility.isSet(specDate)) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        runDate = sdf.parse(specDate);
                    } catch (Exception e) {
                        log.info("The data to be converted to date format was incorrect. Date must use format yyyyMMdd");
                        return;
                    }
                }

                if (runDate == null) {
                    runDate = new Date();
                }

                IdVector workOrderSchIds = aoBean.getWorkOrderScheduleIds(siteIds, runDate);
                for (Iterator iter = workOrderSchIds.iterator(); iter.hasNext();) {
                    Integer woSchInt = (Integer) iter.next();
                    int woSchId = woSchInt.intValue();
                    String resultMess = null;
                    try {
                        String emailMess =
                                aoBean.placeAutoWorkOrder(woSchId, runDate, "System");
                        if (emailMess != null) {
                            try {
                                aoBean.sendOrderNotification(woSchId, emailMess, "System");
                            } catch (Exception exc1) {}
                        }
                        resultMess = "Work Order Schedule " + woSchId + " was processed";
                    } catch (Exception exc) {
                        resultMess = "Work Order Schedule " + woSchId + " failed";
                        try {
                            //log error result
                            aoBean.logErrorRecord(woSchId, exc.getMessage(), runDate, "System");
                        } catch (Exception exc1) {
                        }

                        try {
                            //send error email
                            String detailErrorMess = exc.getMessage();
                            if (detailErrorMess == null || detailErrorMess.trim().length() == 0) {
                                detailErrorMess = resultMess;
                            }
                            int ind1 = detailErrorMess.indexOf("^clw^");
                            if (ind1 > 0 && ind1 + 5 < detailErrorMess.length()) {
                                int ind2 = detailErrorMess.indexOf("^clw^", ind1 + 5);
                                if (ind2 > 0) detailErrorMess = detailErrorMess.substring(ind1 + 5, ind2);
                            }
                            emailClientEjb.send(defaultEmailAddress,
                                    emailClientEjb.getDefaultEmailAddress(),
                                    resultMess,
                                    detailErrorMess, null, 0, 0);

                        } catch (Exception exc1) {
                        }
                    }
                    log(resultMess);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log("AutoOrder processing done:      " + endDate);

            return;
        }
        //=================================================
        else if ( cmd.equals("process_inventory_orders") )
        {

            Date startDate = new Date(),
                orderCutOffDate = new Date();
            log.info("Inventory processing initiated: " +
                               startDate );
            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                    (JNDINames.SITE_EJBHOME);
                SiteHome sHome = (SiteHome)
                    PortableRemoteObject.narrow (ref, SiteHome.class);
                Site sBean = sHome.create();

                String siteid = System.getProperty("siteid");

                IdVector siteIds = new IdVector();
                if ( siteid != null )
                {
                    siteIds.add(new Integer(siteid));
                }
                else
                {
                    siteIds =  sBean.getInventorySiteCollection();
                }

                for ( int idx = 0; siteIds != null &&
                          idx<siteIds.size();
                idx++ )
                {
                    Integer s = (Integer)siteIds.get(idx);
                    log.info("\t" + siteIds.size()
                                       + " -> " + (idx + 1)
                                       + " Processing site id: "+s.intValue());
                    //SimpleDateFormat sdt = new SimpleDateFormat("MM/dd/yyyy");
                    //orderCutOffDate = sdt.parse("06/26/2007");
                    String res = sBean.placeInventoryOrder(s.intValue(),
                                                           orderCutOffDate);
                    log.info("\t" + siteIds.size()
                                       + " -> " + (idx + 1)
                                       + " Inventory processing result: "
                                       + res + "\n" );
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("Inventory processing done:      " +
                               endDate );
            return;
        }
        else if (cmd.equals("process_scheduled_orders")) {

            Date startDate = new Date();
            Date runDate = null;
            int errorCount = 0;
            String begDate = System.getProperty("begDate");
            String endDate = System.getProperty("endDate");
            String siteIdsStr = System.getProperty("siteIds");
            IdVector siteIds = new IdVector();
            ArrayList emailSentTo = new ArrayList();
            ArrayList corporateUsers = new ArrayList();
            String canReceiveEmails;
            log.info("@@@@@@@ process scheduled orders @@@@@@@@@ ");

            try {

                InitialContext jndiContext = new InitialContext(props);

                Object emailClientRef = jndiContext.lookup(JNDINames.EMAIL_CLIENT_EJBHOME);
                EmailClientHome emailClientHome = (EmailClientHome) PortableRemoteObject.narrow(emailClientRef, EmailClientHome.class);
                EmailClient emailClientEjb = emailClientHome.create();

                Object propRef = jndiContext.lookup(JNDINames.PROPERTY_SERVICE_EJBHOME);
                PropertyServiceHome propsHome = (PropertyServiceHome) PortableRemoteObject.narrow(propRef, PropertyServiceHome.class);
                PropertyService propertyEjb = propsHome.create();
                
                Object acctRef = jndiContext.lookup(JNDINames.ACCOUNT_EJBHOME);
                AccountHome acctHome = (AccountHome) PortableRemoteObject.narrow(acctRef, AccountHome.class);
                Account acctEjb = acctHome.create();

                try {
                    boolean specOrderCutOffDateFl = false;
                    // gets ids
                    if (siteIdsStr != null) {
                        StringTokenizer st = new StringTokenizer(siteIdsStr, ",");
                        try {
                            while (st.hasMoreElements()) {
                                siteIds.add(new Integer((String) st.nextElement()));
                            }
                        } catch (NumberFormatException e) {
                            log.info("it's impossible to parse a site ids," +
                                    " the reason is \"Not correct input string :  [" + siteIdsStr + "]\"" +
                                    " Input string must use format <siteId1,siteId2,siteId3...>");
                            return;
                        }
                    }
                    //gets date range
                    if (begDate != null || endDate != null) {
                        if (begDate != null && endDate != null) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                                runDate = sdf.parse(begDate);
                                startDate = sdf.parse(endDate);
                                specOrderCutOffDateFl = true;
                            } catch (Exception e) {
                                log.info("The data to be converted to date format was incorrect. Date must use format yyyyMMddHHmm");
                                return;
                            }
                        } else if (begDate != null && endDate == null) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                                startDate = sdf.parse(begDate);
                                log.info("runDate auto setup");
                                GregorianCalendar cal = new GregorianCalendar();
                                cal.setTime(startDate);
                                cal.set(Calendar.HOUR_OF_DAY, 0);
                                cal.set(Calendar.MINUTE, 0);
                                cal.set(Calendar.SECOND, 0);
                                cal.set(Calendar.MILLISECOND, 0);
                                runDate = cal.getTime();
                                specOrderCutOffDateFl = true;
                            } catch (Exception e) {
                                log.info("The data to be converted to date format was incorrect. Date must use format yyyyMMddHHmm");
                                return;
                            }
                        } else {
                            log.info("Not correct input string");
                            return;
                        }
                    } else {

                        log.info("No begin date specified running normal date logic");

                        String lastStartTime = null;
                        try {
                            lastStartTime = propertyEjb.getProperty(Constants.SCHEDULE_PROCESS_START_TIME);
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                            runDate = sdf.parse(lastStartTime);
                        } catch (Exception e) {
                            log.info(e.getMessage());
                            throw new Exception("start time was not found for the scheduled process");
                        }
                        propertyEjb.setProperty(Constants.SCHEDULE_PROCESS_FINISH_TIME, null);

                    }

                    log.info("processing initiated: " + startDate + " runDate: " + runDate);

                    Object ref = jndiContext.lookup(JNDINames.SITE_EJBHOME);
                    SiteHome sHome = (SiteHome) PortableRemoteObject.narrow(ref, SiteHome.class);
                    Site sBean = sHome.create();
                    Object ref1 = jndiContext.lookup(JNDINames.USER_EJBHOME);
                    UserHome uHome = (UserHome) PortableRemoteObject.narrow(ref1, UserHome.class);
                    User uBean = uHome.create();

                    if (siteIds.isEmpty()) {
                        siteIds = sBean.getInventorySiteCollection(true);
                    }

                    int i = 0;
                    PairViewVector failedMessages = new PairViewVector();
                    Iterator it = siteIds.iterator();
                    while (it.hasNext()) {
                        int id = ((Integer) it.next()).intValue();
                        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> "+id);
                        try {

                            String res = "";
                            res = sBean.placeScheduledOrder(id, runDate, startDate,specOrderCutOffDateFl);

                        	log.info(siteIds.size()
                                    + " -> " + (i+1)
                                    + " processing result: "
                                    + res + "\n");

                            i++;
                        } catch (Exception e) {
                          //  e.printStackTrace();
                            errorCount++;
                            failedMessages.add(new PairView(new Integer(id), e.getMessage()));

                        }

                    }

                    /* Get all sites for which property 'SEND_EMAIL_TO_CORPORATE' is set true
                     * 3 days ago
                     * Get all corporate users and match site ids with this list
                     * Send email to matching users
                     * -xpedx
                     */

                    try{
	                    emailSentTo = propertyEjb.getBusEntityVector(
	                    		RefCodeNames.PROPERTY_TYPE_CD.SEND_EMAIL_TO_CORPORATE, "true",3);
	                    log.info("emailSentTo "+emailSentTo.toString());
	                    
	                    corporateUsers = propertyEjb.getUsers(
	                    		RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER,"true");
	                    log.info("corporateUsers "+corporateUsers.toString());
                    }catch(Exception e){
                    	log.info("process_scheduled_orders:: Property not found:: "+e.getMessage());
                    }

                    if(emailSentTo!=null && emailSentTo.size() > 0 && corporateUsers !=null){
	                    for(int c=0; c < corporateUsers.size(); c++){
	                    	IdVector userSites = new IdVector();
	                    	int user =  ((Integer)corporateUsers.get(c)).intValue();
	                    	
	                    	//if receive inventory missing email flag is not set do not send the email
	                    	String receiveInvMissingEmails = propertyEjb.getUserProperty(
	                        		user, RefCodeNames.PROPERTY_TYPE_CD.RECEIVE_INV_MISSING_EMAIL);
	                    	if(receiveInvMissingEmails.equalsIgnoreCase("false")){
	                        	continue;
	                        }
	                    	
	                    	for(int ii=0; ii<emailSentTo.size(); ii++){
	                    		int site = ((Integer)emailSentTo.get(ii)).intValue();
	                    		if(uBean.isSiteOfUser(site, user)){
	                    			userSites.add(new Integer(site));
	                    		}
	                    	}
	                    	//send email
	                    	if(userSites != null && userSites.size() > 0){
	                    		
	                    		int site = ((Integer)userSites.get(0)).intValue();
	                    		AccountData acctD = acctEjb.getAccountForSite(site);
	                    		int acctId = acctD.getAccountId();
	                    		
	                    		String lSubject = "Corporate Notification - Stores with incomplete on-hand "+
	                    							"quantities for Forecasted Items";

	                    		String lMsg = "Following stores have not completed their on-hand quantities"+
	                    						" for this forecasted items period."+"\n"+"\n";
	                    		
	                    		SiteData sitedata = sBean.getSite(((Integer)userSites.get(0)).intValue());
	                    		Date cutOffD = sitedata.getNextOrdercutoffDate();
	                    		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	                            String fD = sdf.format(cutOffD);

	                            lSubject = lSubject + " Cut-off: "+fD;
	                            log.info("lSubject "+lSubject);
	                    		for(int u=0; u<userSites.size(); u++){
	                    			//lMsg = lMsg + ((Integer)userSites.get(u)).intValue()+"\n";
	                    			SiteData siteD = sBean.getSite(((Integer)userSites.get(u)).intValue());
	                    			lMsg = lMsg + siteD.getBusEntity().getShortDesc()+"\n";
	                    		}

//	                    		UserInfoData u = uBean.getUserContact(user);
                                    UserInfoData u = uBean.getUserContactForNotification(user);
	                            String lToEmailAddress = u.getEmailData().getEmailAddress();
	                            if (!RefCodeNames.USER_STATUS_CD.ACTIVE.equals(u.getUserData().getUserStatusCd())) {
	                                continue;
	                            }

	                    		if (emailClientEjb.wasThisEmailSent(lSubject, lToEmailAddress) == false) {

	                    			emailClientEjb.send(lToEmailAddress,
	                    					emailClientEjb.getAcctDefaultEmailAddress(acctId),
	                    					lSubject, lMsg,
	                    					Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, user);

	            	                    for(int s=0; s< userSites.size(); s++){
	                            			int siteId = ((Integer)userSites.get(s)).intValue();
	                            			propertyEjb.setBusEntityProperty(siteId,
	                            			RefCodeNames.PROPERTY_TYPE_CD.SEND_EMAIL_TO_CORPORATE,
	                                		"false");
	                            		}
	                            }
	                    	}

	                    }
                    }
                    log.info("result: processed "
                            + (i) + " of " + siteIds.size() + ".Error count: " + errorCount);

                    if(!specOrderCutOffDateFl) {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                        propertyEjb.setProperty(Constants.SCHEDULE_PROCESS_START_TIME,sdf.format(startDate));
                        propertyEjb.setProperty(Constants.SCHEDULE_PROCESS_FINISH_TIME,sdf.format(new Date()));
                    }

                    if (errorCount > 0) {
                        String mess = "process_scheduled_orders problems.\n";
                        mess += failedMessages.toString();
                        throw new Exception(mess);
                    }
                } catch (Exception e) {
                	// e.printStackTrace();
                	String body = "AppCmd caught error: "+e.getMessage();
                    log.info(e.getMessage());
                    String defaultEmailAddress = emailClientEjb.getDefaultEmailAddress();
                    emailClientEjb.send(defaultEmailAddress,
                            emailClientEjb.getDefaultEmailAddress(),
                            "process_scheduled_orders problems",
                            body,
                            null, 0, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("processing done: " + new Date());
            return;
        }
        else if (cmd.equals("process_schedule")) {
            Date startDate = new Date();
            Date runDate = null;
            int errorCount = 0;
            String begDate = System.getProperty("begDate");
            String endDate = System.getProperty("endDate");

            try {

                InitialContext jndiContext = new InitialContext(props);

                Object emailClientRef = jndiContext.lookup(JNDINames.EMAIL_CLIENT_EJBHOME);
                EmailClientHome emailClientHome = (EmailClientHome) PortableRemoteObject.narrow(emailClientRef, EmailClientHome.class);
                EmailClient emailClientEjb = emailClientHome.create();

                Object propRef = jndiContext.lookup(JNDINames.PROPERTY_SERVICE_EJBHOME);
                PropertyServiceHome propsHome = (PropertyServiceHome) PortableRemoteObject.narrow(propRef, PropertyServiceHome.class);
                PropertyService propertyEjb = propsHome.create();

                try {
                    boolean specOrderCutOffDateFl = false;
                    //gets date range
                    if (begDate != null || endDate != null) {
                        if (begDate != null && endDate != null) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                                runDate = sdf.parse(begDate);
                                startDate = sdf.parse(endDate);
                                specOrderCutOffDateFl = true;
                            } catch (Exception e) {
                                log.info("The data to be converted to date format was incorrect. Date must use format yyyyMMddHHmm");
                                return;
                            }
                        } else if (begDate != null && endDate == null) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                                startDate = sdf.parse(begDate);
                                log.info("runDate auto setup");
                                GregorianCalendar cal = new GregorianCalendar();
                                cal.setTime(startDate);
                                cal.set(Calendar.HOUR_OF_DAY, 0);
                                cal.set(Calendar.MINUTE, 0);
                                cal.set(Calendar.SECOND, 0);
                                cal.set(Calendar.MILLISECOND, 0);
                                runDate = cal.getTime();
                                specOrderCutOffDateFl = true;
                            } catch (Exception e) {
                                log.info("The data to be converted to date format was incorrect. Date must use format yyyyMMddHHmm");
                                return;
                            }
                        } else {
                            log.info("Not correct input string");
                            return;
                        }
                    } else {

                        log.info("No begin date specified running normal date logic");

                        String lastStartTime = null;
                        try {
                            lastStartTime = propertyEjb.getProperty(Constants.SCHEDULE_PROCESS_START_TIME);
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                            runDate = sdf.parse(lastStartTime);
                        } catch (Exception e) {
                            log.info(e.getMessage());
                            throw new Exception("start time was not found for the scheduled process");
                        }
                        propertyEjb.setProperty(Constants.SCHEDULE_PROCESS_FINISH_TIME, null);

                    }

                    log.info("processing initiated: " + startDate + " runDate: " + runDate);

                    Object ref = jndiContext.lookup(JNDINames.SITE_EJBHOME);
                    SiteHome sHome = (SiteHome) PortableRemoteObject.narrow(ref, SiteHome.class);
                    Site sBean = sHome.create();

                    Object distRef  = jndiContext.lookup(JNDINames.DISTRIBUTOR_EJBHOME);
                    DistributorHome dHome = (DistributorHome)PortableRemoteObject.narrow (distRef, DistributorHome.class);
                    Distributor dBean = dHome.create();

                    GregorianCalendar curDate = new GregorianCalendar();
                    curDate.setTime(runDate);
                    curDate.set(Calendar.HOUR_OF_DAY, 0);
                    curDate.set(Calendar.MINUTE, 0);
                    curDate.set(Calendar.SECOND, 0);
                    curDate.set(Calendar.MILLISECOND, 0);

                    IdVector sdv = dBean.getSchedules(runDate);
                    Iterator iter = sdv.iterator();
                    int i = 0;
                    int k = 0;
                    PairViewVector failedMessages = new PairViewVector();
                    while (iter.hasNext()) {
                      ScheduleJoinView sch = dBean.getDeliveryScheduleById(((Integer)iter.next()).intValue());
                      ScheduleProc scheduleProc = new ScheduleProc(sch, null);
                      scheduleProc.initSchedule();
//                      GregorianCalendar nextDate = scheduleProc.getOrderDeliveryDate(curDate.getTime(),curDate.getTime());
                      ScheduleOrderDates orderPair = scheduleProc.getOrderDeliveryDates(curDate.getTime(),curDate.getTime());
                      log.info("nextDay: " + orderPair.getNextOrderCutoffDate());
                      GregorianCalendar nextDate = new GregorianCalendar();
                      nextDate.setTime(orderPair.getNextOrderCutoffDate());
                      if (nextDate != null) {
                        log.info("nextDay is not null. " + nextDate.getTime());
                        nextDate.set(Calendar.HOUR_OF_DAY, 0);
                        nextDate.set(Calendar.MINUTE, 0);
                        nextDate.set(Calendar.SECOND, 0);
                        nextDate.set(Calendar.MILLISECOND, 0);
                        if (curDate.equals(nextDate)) {
                          IdVector distSiteIds = dBean.getSiteIdsForSchedule(
                              sch.getSchedule().getBusEntityId(),
                              sch.getSchedule().getScheduleId());
                          int j = 0;
                          Iterator it = distSiteIds.iterator();
                          while (it.hasNext()) {
                            int id = ( (Integer) it.next()).intValue();
                            try {
                              String res = "";
                              res = sBean.placeScheduledOrder(id, runDate,
                                  startDate, specOrderCutOffDateFl);
                              log.info("schedules: " + sdv.size()
                                      + " -> " + (i + 1)
                                      + "sites: " + distSiteIds.size()
                                      + " -> " + (j + 1)
                                      + " processing result: "
                                      + res + "\n");
                              j++;
                              k++;
                            }
                            catch (Exception e) {
                              //  e.printStackTrace();
                              errorCount++;
                              failedMessages.add(new PairView(new Integer(id),
                                  e.getMessage()));

                            }
                          }
                        }
                      }
                      i++;
                    }

                    log.info("result: processed "
                            + (k) + ".Error count: " + errorCount);

                    if(!specOrderCutOffDateFl) {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                        propertyEjb.setProperty(Constants.SCHEDULE_PROCESS_START_TIME,sdf.format(startDate));
                        propertyEjb.setProperty(Constants.SCHEDULE_PROCESS_FINISH_TIME,sdf.format(new Date()));
                    }

                    if (errorCount > 0) {
                        String mess = "process_scheduled_orders problems.\n";
                        mess += failedMessages.toString();
                        throw new Exception(mess);
                    }
                } catch (Exception e) {
                   // e.printStackTrace();
                    log.info(e.getMessage());
                    String defaultEmailAddress = emailClientEjb.getDefaultEmailAddress();
                    emailClientEjb.send(defaultEmailAddress,
                            emailClientEjb.getDefaultEmailAddress(),
                            e.getMessage(),
                            "process_scheduled_orders problems",
                            null, 0, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("processing done: " + new Date());
            return;
        } else if (cmd.equals("process_schedule_by_report")) {

            long lst = System.currentTimeMillis();

            String begDate = System.getProperty("begDate");
            String endDate = System.getProperty("endDate");
            String siteIdsStr = System.getProperty("siteIds");
            String reportName = System.getProperty("reportName");
            boolean invModernShopping = Utility.isTrue(System.getProperty("invModernShopping"),true);

            HashSet<Integer> siteIdSet = new HashSet<Integer>();
            ArrayList emailSentTo = new ArrayList();
            ArrayList corporateUsers = new ArrayList();
            HashMap<String, String> params = new HashMap<String, String>();
            //boolean genArchReport = false;
            Date startDate = new Date();
            Date runDate;
            int errorCount = 0;

            log.info("@@@@@@@ process scheduled orders by report@@@@@@@@@ ");
            log.info("sch_by_rep => batch started:" + new Date(lst));
            try {

                InitialContext jndiContext = new InitialContext(props);

                Object emailClientRef = jndiContext.lookup(JNDINames.EMAIL_CLIENT_EJBHOME);
                EmailClientHome emailClientHome = (EmailClientHome) PortableRemoteObject.narrow(emailClientRef, EmailClientHome.class);
                EmailClient emailClientEjb = emailClientHome.create();

                Object propRef = jndiContext.lookup(JNDINames.PROPERTY_SERVICE_EJBHOME);
                PropertyServiceHome propsHome = (PropertyServiceHome) PortableRemoteObject.narrow(propRef, PropertyServiceHome.class);
                PropertyService propertyEjb = propsHome.create();

                try {
                    boolean specOrderCutOffDateFl = false;
                    // gets ids
                    if (siteIdsStr != null) {
                        StringTokenizer st = new StringTokenizer(siteIdsStr, ",");
                        try {
                            while (st.hasMoreElements()) {
                                siteIdSet.add(new Integer((String) st.nextElement()));
                            }
                        } catch (NumberFormatException e) {
                            log.info("sch_by_rep => it's impossible to parse a site ids," +
                                    " the reason is \"Not correct input string :  [" + siteIdsStr + "]\"" +
                                    " Input string must use format <siteId1,siteId2,siteId3...>");
                            return;
                        }
                    }
                    //gets date range
                    if (begDate != null || endDate != null) {
                        if (begDate != null && endDate != null) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                                runDate = sdf.parse(begDate);
                                startDate = sdf.parse(endDate);
                                specOrderCutOffDateFl = true;
                            } catch (Exception e) {
                                log.info("sch_by_rep => The data to be converted to date format was incorrect. Date must use format yyyyMMddHHmm");
                                return;
                            }
                        } else if (begDate != null && endDate == null) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                                startDate = sdf.parse(begDate);
                                log.info("sch_by_rep => runDate auto setup");
                                GregorianCalendar cal = new GregorianCalendar();
                                cal.setTime(startDate);
                                cal.set(Calendar.HOUR_OF_DAY, 0);
                                cal.set(Calendar.MINUTE, 0);
                                cal.set(Calendar.SECOND, 0);
                                cal.set(Calendar.MILLISECOND, 0);
                                runDate = cal.getTime();
                                specOrderCutOffDateFl = true;
                            } catch (Exception e) {
                                log.info("sch_by_rep => The data to be converted to date format was incorrect. Date must use format yyyyMMddHHmm");
                                return;
                            }
                        } else {
                            log.info("sch_by_rep => Not correct input string");
                            return;
                        }
                    } else {

                        log.info("sch_by_rep => No begin date specified.Running normal date logic.");

                        String lastStartTime;
                        try {
                            lastStartTime = propertyEjb.getProperty(Constants.SCH_PROC_BY_REP_START_TIME);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                            runDate = sdf.parse(lastStartTime);
                        } catch (Exception e) {
                            log.info(e.getMessage());
                            throw new Exception("start time was not found for the scheduled process");
                        }
                        propertyEjb.setProperty(Constants.SCH_PROC_BY_REP_FINISH_TIME, null);
                    }

                    log.info("sch_by_rep => processing initiated: " + startDate + " runDate: " + runDate);

                    Object ref = jndiContext.lookup(JNDINames.SITE_EJBHOME);
                    SiteHome sHome = (SiteHome) PortableRemoteObject.narrow(ref, SiteHome.class);
                    Site sBean = sHome.create();

                    Object ref1 = jndiContext.lookup(JNDINames.USER_EJBHOME);
                    UserHome uHome = (UserHome) PortableRemoteObject.narrow(ref1, UserHome.class);
                    User uBean = uHome.create();

                    Object ref2 = jndiContext.lookup(JNDINames.REPORT_EJBHOME);
                    ReportHome rHome = (ReportHome) PortableRemoteObject.narrow(ref2, ReportHome.class);
                    Report rBean = rHome.create();

                    if (siteIdSet.isEmpty()) {

                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                        GregorianCalendar begDateParam = new GregorianCalendar();
                        begDateParam.setTime(runDate);
                        begDateParam.set(Calendar.HOUR_OF_DAY, 0);
                        begDateParam.set(Calendar.MINUTE, 0);
                        begDateParam.set(Calendar.SECOND, 0);
                        begDateParam.set(Calendar.MILLISECOND, 0);

                        GregorianCalendar endDateParam = new GregorianCalendar();
                        endDateParam.setTime(startDate);
                        endDateParam.set(Calendar.HOUR_OF_DAY, 0);
                        endDateParam.set(Calendar.MINUTE, 0);
                        endDateParam.set(Calendar.SECOND, 0);
                        endDateParam.set(Calendar.MILLISECOND, 0);

                        params.put("INV_MODERN_SHOPPING", Boolean.toString(invModernShopping));
                        params.put("BEG_DATE", sdf.format(begDateParam.getTime()));
                        if (endDateParam.compareTo(begDateParam)!=0) {
                            params.put("END_DATE_OPT", sdf.format(endDateParam.getTime()));
                        }
                        log.info("sch_by_rep => params:"+params);

                        String kInvUser = "inv_order";

                        UserData invUser = uBean.getUserByName(kInvUser, 0);

                        List<String> reportNameList = new ArrayList<String>();
                        reportNameList.add(reportName);

                        GenericReportViewVector reportList = rBean.getReportList(invUser.getUserId(), invUser.getUserTypeCd(), reportNameList);

                        if (reportList.size() > 1) {
                            String err = "Found multiple reports. User => " + invUser.getUserName() + ", Report Name => " + reportName;
                            throw new Exception(err);
                        }

                        if (reportList.isEmpty()) {
                            String err = "Report not found.User => " + invUser.getUserName() + ", Report Name => " + reportName;
                            throw new Exception(err);
                        }

                        log.info("sch_by_rep => Generic report is found: " + reportList.get(0));
                        log.info("sch_by_rep => Searching archive data...");
                        GenericReportView report = (GenericReportView) reportList.get(0);

                        IdVector archiveIds = rBean.getArchiveReportIds(report.getGenericReportId(),
                                invUser.getUserId(),
                                RefCodeNames.REPORT_RESULT_STATUS_CD.GENERATED);

                        

                        GenericReportResultViewVector result = new GenericReportResultViewVector();
                        {
                            log.info("sch_by_rep => Attempt to generate the report.");
                            ReportResultData genResult = (ReportResultData) rBean.checkAnalyticReport(report.getGenericReportId(), params, invUser.getUserId(), invUser.getUserName(), true);
                            log.info("sch_by_rep => Generated result: " + genResult);
                            log.info("sch_by_rep => Reading archive data ...");
                            result = rBean.readArchiveReport(genResult.getReportResultId());
                        }

                        if (!result.isEmpty()) {

                            GenericReportResultView sheet = (GenericReportResultView) result.get(0);

                            log.info("sch_by_rep => Archive size: " + sheet.getTable().size() + " records.");
                            log.info("sch_by_rep => Archive scan ...");

                            int cutoffDateIdx = InventoryScheduleReport.getColumnIndex(sheet.getHeader(), InventoryScheduleReport.REPORT_HEADER.CUTOFF_DATE);
                            int cutoffTimeIdx = InventoryScheduleReport.getColumnIndex(sheet.getHeader(), InventoryScheduleReport.REPORT_HEADER.CUTOFF_TIME);
                            int siteIdx = InventoryScheduleReport.getColumnIndex(sheet.getHeader(), InventoryScheduleReport.REPORT_HEADER.SITE_ID);

                            //int idx = 0;
                            for (Object oRow : sheet.getTable()) {

                                List row = (List) oRow;
                                //log.info("sch_by_rep => Row(" + (++idx) + "):" + row);

                                String cutoffTimeStr = (String) row.get(cutoffTimeIdx);
                                String cutoffDateStr = (String) row.get(cutoffDateIdx);

                                Date cutoffTime = Utility.parseDate(cutoffTimeStr, "hh:mm a", true);
                                Date cutoffDate = Utility.parseDate(cutoffDateStr, "MM/dd/yyyy", true);

                                GregorianCalendar cutoffTimeCal = new GregorianCalendar();
                                cutoffTimeCal.setTime(cutoffTime);
                                GregorianCalendar cuttofDateCal = new GregorianCalendar();
                                cuttofDateCal.setTime(cutoffDate);

                                GregorianCalendar cuttofCal = (GregorianCalendar) cuttofDateCal.clone();
                                cuttofCal.set(Calendar.HOUR_OF_DAY, cutoffTimeCal.get(Calendar.HOUR_OF_DAY));
                                cuttofCal.set(Calendar.MINUTE, cutoffTimeCal.get(Calendar.MINUTE));
                                cuttofCal.set(Calendar.SECOND, cutoffTimeCal.get(Calendar.SECOND));
                                cuttofCal.set(Calendar.MILLISECOND, cutoffTimeCal.get(Calendar.MILLISECOND));

                                GregorianCalendar runCal = new GregorianCalendar();
                                runCal.setTime(runDate);

                                GregorianCalendar startCal = new GregorianCalendar();
                                startCal.setTime(startDate);

                                //log.info(runCal.getTime() + " < " + cuttofCal.getTime() + " < " + startCal.getTime());
                                if (cuttofCal.getTime().after(runCal.getTime()) && cuttofCal.getTime().before(startCal.getTime())) {
                                    //log.info("sch_by_rep => Site(" + row.get(siteIdx) + ") is added to process.");
                                    siteIdSet.add((Integer) row.get(siteIdx));
                                }
                            }
                        } else {
                            log.info("sch_by_rep => Archive data is empty.");
                        }
                    }
                    log.info("sch_by_rep => Sites to process: " + siteIdSet);
                    int i = 0;
                    PairViewVector failedMessages = new PairViewVector();
                    for (Object siteId1 : siteIdSet) {
                        int id = (Integer) siteId1;
                        try {

                            String res = sBean.placeScheduledOrder(id, runDate, startDate, specOrderCutOffDateFl);
                            log.info("sch_by_rep => " + (i + 1) + " of " + siteIdSet.size() + " => processing result: " + res);
                            i++;

                        } catch (Exception e) {
                            //  e.printStackTrace();
                            errorCount++;
                            failedMessages.add(new PairView(id, e.getMessage()));

                        }
                    }

                    if (errorCount > 0){
                    	log.info("sch_by_rep => result: processed " + (i) + " of " + siteIdSet.size() + ".Error count: " + errorCount);
                    }else {
                    	log.info("sch_by_rep => result: processed " + (i) + " of " + siteIdSet.size());
                    }
                    if (!specOrderCutOffDateFl) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                        propertyEjb.setProperty(Constants.SCH_PROC_BY_REP_START_TIME, sdf.format(startDate));
                        propertyEjb.setProperty(Constants.SCH_PROC_BY_REP_FINISH_TIME, sdf.format(new Date()));
                    }

                    if (errorCount > 0) {
                        String mess = "process_schedule_by_report problems.\n";
                        mess += failedMessages.toString();
                        throw new Exception(mess);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info(e.getMessage());
                    String defaultEmailAddress = emailClientEjb.getDefaultEmailAddress();
                    emailClientEjb.send(defaultEmailAddress,
                            defaultEmailAddress,
                            e.getMessage(),
                            "process_schedule_by_report problems",
                            null, 0, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("sch_by_rep => processing done: " + new Date() + ". Time(s): " + (System.currentTimeMillis() - lst) + " ms.");
            return;
        }
        else if ( cmd.equals("process_workflow_deprecated") )
        {

            Date startDate = new Date();
            log("Workflow processing initiated.");

            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref = jndiContext.lookup(JNDINames.WORKFLOW_EJBHOME);
                WorkflowHome wkflHome = (WorkflowHome)PortableRemoteObject.narrow (ref, WorkflowHome.class);
                Workflow wkflBean = wkflHome.create();

                log("Start processQueueEntries()");
                /*
                String s = wkflBean.processQueueEntries();
                if ( null != s ) {
                    log.info(s);
                }
                */
                //cleanQueue();
                int forwardForApprovalQty = 0;
                int stopOrderQty = 0;
                int sendEmailQty = 0;

                for(int ii=0; ii<100; ii++) {
                    if(ii==0) { //do only once
                        String actionType = RefCodeNames.WORKFLOW_RULE_ACTION.PENDING_REVIEW;
                        log("Processing "+actionType+ " workflow queue. loop: "+ii);
                        int cnt = wkflBean.processQueueEntries(actionType);
                        log("Processed "+actionType+ " workflow queue. loop: "+ii+" count: "+cnt);
                    }

                    //********************************************************************
                    if(ii==0 || forwardForApprovalQty>0) {
                        String actionType = RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL;
                        log("Processing "+actionType+ " workflow queue. loop: "+ii);
                        forwardForApprovalQty = wkflBean.processQueueEntries(actionType);
                        log("Processed "+actionType+ " workflow queue. loop: "+ii+" count: "+forwardForApprovalQty);
                    }
                    //********************************************************************
                    if(ii==0 || stopOrderQty>0) {
                        String actionType = RefCodeNames.WORKFLOW_RULE_ACTION.STOP_ORDER;
                        log("Processing "+actionType+ " workflow queue. loop: "+ii);
                        stopOrderQty = wkflBean.processQueueEntries(actionType);
                        log("Processed "+actionType+ " workflow queue. loop: "+ii+" count: "+stopOrderQty);
                    }

                    //********************************************************************
                    if(ii==0 || sendEmailQty>0) {
                        String actionType = RefCodeNames.WORKFLOW_RULE_ACTION.SEND_EMAIL;
                        log("Processing "+actionType+ " workflow queue. loop: "+ii);
                        sendEmailQty = wkflBean.processQueueEntries(actionType);
                        log("Processed "+actionType+ " workflow queue. loop: "+ii+" count: "+sendEmailQty);
                    }
                    if(forwardForApprovalQty == 0 && stopOrderQty == 0 && sendEmailQty == 0) {
                        break;
                    }

                }

                log("Finish processQueueEntries()");

                Object ref1 = jndiContext.lookup(JNDINames.INTEGRATION_SERVICES_EJBHOME);
                IntegrationServicesHome isrvHome = (IntegrationServicesHome)PortableRemoteObject.narrow(ref1, IntegrationServicesHome.class);
                IntegrationServices isrvEjb = isrvHome.create();

                log("Start processPendingDateOrdersv()");
                isrvEjb.processPendingDateOrders();
                log("Finish processPendingDateOrdersv()");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log("Workflow processing done. (" + startDate + "-" + endDate + ")");
            return;
        }
        else if ( cmd.equals("send_order_emails_deprecated") )
        {

            Date startDate = new Date();
            log("Workflow processing, send emails");

            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup(JNDINames.WORKFLOW_EJBHOME);
                WorkflowHome wkflHome = (WorkflowHome)PortableRemoteObject.narrow(ref, WorkflowHome.class);
                Workflow wkflBean = wkflHome.create();

                log("All objects are initialized.");

                int oid = 0;
                IdVector oids = wkflBean.getOrdersSinceLastCheck();

                log("wkflBean.getOrdersSinceLastCheck() is finished. Count of emails is " + oids.size());

                for ( int i = 0; oids != null && i < oids.size(); i++ )
                {
                    oid = ((Integer)oids.get(i)).intValue();
                    log("Start sendOrderEmails() , oid=" + oid + ",\t\t " + (i+1) + " of: " + oids.size());

                    wkflBean.sendOrderEmails(oid);

                    log("Finish sendOrderEmails() , oid=" + oid + ",\t\t " + (i+1) + " of: " + oids.size());
                }

                log("All email are sended.");

                wkflBean.updateLastCheckStamp(startDate, oid);
                log("wkflBean.updateLastCheckStamp() is finished");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log("Workflow processing, send emails, done (" + startDate + "-" + endDate + ")");
            return;
        }

        else if ( cmd.equals("orders_to_lawson_deprecated" ) )
        {
            Date startDate = new Date();
            log.info("Lawson: " + cmd +
                               " processing initiated:      " +
                               startDate );


            try
            {

                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                    (JNDINames.PIPELINE_EJBHOME);
                PipelineHome pipelineHome = (PipelineHome)
                    PortableRemoteObject.narrow (ref, PipelineHome.class);
                Pipeline pipelineEjb = pipelineHome.create();

                Object orderRef  = jndiContext.lookup
                    (JNDINames.ORDER_EJBHOME);
                OrderHome orderHome = (OrderHome)
                    PortableRemoteObject.narrow (orderRef, OrderHome.class);
                Order orderEjb = orderHome.create();

                Object propertyServiceRef  = jndiContext.lookup
                    (JNDINames.PROPERTY_SERVICE_EJBHOME);
                PropertyServiceHome propertyServiceHome = (PropertyServiceHome)
                    PortableRemoteObject.narrow (propertyServiceRef, PropertyServiceHome.class);
                PropertyService propertyServiceEjb = propertyServiceHome.create();


                Object storeBeanRef  = jndiContext.lookup
                    (JNDINames.STORE_EJBHOME);
                StoreHome storeBeanHome = (StoreHome)
                    PortableRemoteObject.narrow (storeBeanRef, StoreHome.class);
                Store storeBean = storeBeanHome.create();

                String stores = System.getProperty("stores");
                if(stores==null) {
                    log("orders_to_lawson. No store ids provided");
                    return;
                }
                if("all".equalsIgnoreCase(stores.trim())) {
                    stores="0";
                }
                String[] storesA = Utility.parseStringToArray(stores,",");
                int[] storeIds = new int[storesA.length];
                boolean minusFl = false;
                boolean plusFl = false;
                for(int ii=0; ii<storesA.length;ii++) {
                    try {
                        storeIds[ii] = Integer.parseInt(storesA[ii]);
                        if(storeIds[ii]<0) minusFl = true;
                        if(storeIds[ii]>0) plusFl = true;
                    }catch(Exception exc) {
                        log("orders_to_lawson. Error. Invalid store id format. Store id = "+storesA[ii] );
                        return;
                    }
                }
                if(minusFl && plusFl) {
                    log("orders_to_lawson. Error. Incomatible set of stores. Both plus and minus present ");
                    return;
                }
                if(!plusFl) {
                    BusEntityDataVector storeBusEntDV =
                            storeBean.getAllStoresBusEntityData(Store.ORDER_BY_ID);
                    for(Iterator iter=storeBusEntDV.iterator(); iter.hasNext();) {
                        BusEntityData beD = (BusEntityData) iter.next();
                        int id = beD.getBusEntityId();
                        for(int ii=0; ii<storeIds.length; ii++) {
                            if(id==(-storeIds[ii])) {
                                iter.remove();
                            }
                        }
                    }
                    storeIds = new int[storeBusEntDV.size()];
                    int ind = 0;
                    for(Iterator iter=storeBusEntDV.iterator(); iter.hasNext();) {
                        BusEntityData beD = (BusEntityData) iter.next();
                        storeIds[ind++] = beD.getBusEntityId();
                    }
                }

               //Get erp statuses
               PropertyDataVector propDV =
               propertyServiceEjb.getProperties(null, null,RefCodeNames.PROPERTY_TYPE_CD.ERP_STATUS);
               HashMap erpStatusHM = new HashMap();
               for(Iterator iter=propDV.iterator(); iter.hasNext();) {
                 PropertyData pD = (PropertyData) iter.next();
                 String shortDesc = pD.getShortDesc();
                 String value = pD.getValue();
                 if(shortDesc==null || value==null) {
                   continue;
                 }
                 if(!RefCodeNames.PROPERTY_STATUS_CD.ACTIVE.equals(pD.getPropertyStatusCd())) {
                   continue;
                 }
                 String vv = (String) erpStatusHM.get(shortDesc);
                 if(vv==null) {
                   erpStatusHM.put(shortDesc,value);
                 } else {
                   if(!vv.equals(value)) {
                     String errMess = "***** ERROR !!!!!! Erp System "+shortDesc+" has multiple status records";
                     log.info(errMess);
                     erpStatusHM.put(shortDesc,"ERROR");
                   }
                 }
               }

                //Finish interrupted pipelines
                OrderDataVector orders = orderEjb.getOrdersPipelineToResume();
                for(Iterator iter = orders.iterator(); iter.hasNext();){
                   OrderData oD = (OrderData) iter.next();
                   int storeId = oD.getStoreId();
                   if(storeId>0 && !Utility.isInArray(storeId, storeIds)) {
                       continue;
                   }
                   if(!RefCodeNames.WORKFLOW_IND_CD.TO_RESUME.equals(oD.getWorkflowInd())) {
                     continue;
                   }
                   String erpSystem = oD.getErpSystemCd();
                   if(erpSystem==null) {
                     log.info("******* Order doesn't have erp_system. Order id: "+
                             oD.getOrderId());
                     continue;
                   }
                   String erpStatus = (String) erpStatusHM.get(erpSystem);
                   if(!"ACTIVE".equals(erpStatus)) {
                    log.info("******* Warning Erp System "+erpSystem+" has <"+
                            erpStatus+"> status. Pipeline skipped. Order id: "+oD.getOrderId());
                     continue;
                   }
                   try {
                     pipelineEjb.resumePipeline(oD);
                   } catch (Exception exc){
                     String errorMess = "Pipeline exception. Order id: "+
                                  oD.getOrderId() +" Error: "+ exc.getMessage();
                     processPipelineException(orderEjb, oD, errorMess,
                                          RefCodeNames.PIPELINE_CD.ASYNCH);
                   }
                }

                //process RECEIVED pipeline orders
               orders =
                  orderEjb.getOrdersByType(RefCodeNames.ORDER_STATUS_CD.RECEIVED);

                for(Iterator iter = orders.iterator(); iter.hasNext();){
                   OrderData oD = (OrderData) iter.next();
                   int storeId = oD.getStoreId();
                   if(storeId>0 && !Utility.isInArray(storeId, storeIds)) {
                       continue;
                   }
                   if(RefCodeNames.WORKFLOW_IND_CD.INTERRUPTED.equals(oD.getWorkflowInd())||
                      RefCodeNames.WORKFLOW_IND_CD.TO_RESUME.equals(oD.getWorkflowInd())) {
                     continue;
                   }
                   try {
                     pipelineEjb.processPipeline(oD,RefCodeNames.PIPELINE_CD.SYNCH_ASYNCH);
                   } catch (Exception exc){
                     String errorMess = "Pipeline exception. Order id: "+
                                  oD.getOrderId() +" Error: "+ exc.getMessage();
                     processPipelineException(orderEjb, oD, errorMess,
                                          RefCodeNames.PIPELINE_CD.SYNCH_ASYNCH);
                   }
                }
                //process ORDERED pipeline orders

               orders =
                  orderEjb.getOrdersByType(RefCodeNames.ORDER_STATUS_CD.ORDERED);


                for(Iterator iter = orders.iterator(); iter.hasNext();){
                   OrderData oD = (OrderData) iter.next();
                   int storeId = oD.getStoreId();
                   if(storeId>0 && !Utility.isInArray(storeId, storeIds)) {
                       continue;
                   }
                   String erpSystem = oD.getErpSystemCd();
                   if(erpSystem==null) {
                     log.info("******* Order doesn't have erp_system. Order id: "+
                             oD.getOrderId());
                     continue;
                   }
                   String erpStatus = (String) erpStatusHM.get(erpSystem);
                   if(!"ACTIVE".equals(erpStatus)) {
                    log.info("******* Warning Erp System "+erpSystem+" has <"+
                            erpStatus+"> status. Pipeline skipped. Order id: "+oD.getOrderId());
                     continue;
                   }
                   if(RefCodeNames.WORKFLOW_IND_CD.INTERRUPTED.equals(oD.getWorkflowInd())||
                      RefCodeNames.WORKFLOW_IND_CD.TO_RESUME.equals(oD.getWorkflowInd())) {
                     continue;
                   }
                   try {
                     pipelineEjb.processPipeline(oD,RefCodeNames.PIPELINE_CD.ASYNCH);
                   } catch (Exception exc){
                     String errorMess = "Pipeline exception. Order id: "+
                                  oD.getOrderId() +" Error: "+ exc.getMessage();
                     processPipelineException(orderEjb, oD, errorMess,
                                          RefCodeNames.PIPELINE_CD.ASYNCH);
                   }
                }
                //process PRE_PROCESSED pipeline orders
                orders =
                  orderEjb.getOrdersByType(RefCodeNames.ORDER_STATUS_CD.PRE_PROCESSED);

                for(Iterator iter = orders.iterator(); iter.hasNext();){
                   OrderData oD = (OrderData) iter.next();
                   int storeId = oD.getStoreId();
                   if(storeId>0 && !Utility.isInArray(storeId, storeIds)) {
                       continue;
                   }
                   String erpSystem = oD.getErpSystemCd();
                   if(erpSystem==null) {
                     log.info("******* Order doesn't have erp_system. Order id: "+
                             oD.getOrderId());
                     continue;
                   }
                   String erpStatus = (String) erpStatusHM.get(erpSystem);
                   if(!"ACTIVE".equals(erpStatus)) {
                    log.info("******* Warning Erp System "+erpSystem+" has <"+
                            erpStatus+"> status. Pipeline skipped. Order id: "+oD.getOrderId());
                     continue;
                   }
                   if(RefCodeNames.WORKFLOW_IND_CD.INTERRUPTED.equals(oD.getWorkflowInd())||
                      RefCodeNames.WORKFLOW_IND_CD.TO_RESUME.equals(oD.getWorkflowInd())) {
                     continue;
                   }
                   try {
                     pipelineEjb.processPipeline(oD,RefCodeNames.PIPELINE_CD.ASYNCH_PRE_PROCESSED);
                   } catch (Exception exc){
                     log.info("Pipeline exception. Order id: "+
                                  oD.getOrderId() +" Error: "+ exc.getMessage());
                   }
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("Lawson: " + cmd +
                               " processing done:      " +
                               endDate );
            return;

        }

///////<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        else if ( cmd.equals("release_lawson_pos" ) )
        {
            Date startDate = new Date();
            log.info("Function: " + cmd +
                               " initiated:      " +
                               startDate );

            try
            {

                String poListFileName = System.getProperty("po_list_file");
                File poListFile = new File(poListFileName);
                if(!poListFile.exists()|| !poListFile.isFile()) {
                  String errorMess = "@@@@@@@@@ AppCmd Error. File doesn't exist. File name: "+poListFile;
                  log.info(errorMess);
                  return;
                }
                FileInputStream fis = new FileInputStream(poListFile);
                byte[] buffer = new byte[(int) poListFile.length()];
                int lenght = fis.read(buffer);
                int maxWrdLen = 0;
                for(int ii=0,len=0; ii<buffer.length; ii++) {
                  if(buffer[ii]<'0'||buffer[ii]>'9') {
                    buffer[ii]=' ';
                    len = 0;
                  } else {
                    len++;
                  }
                  if(len>maxWrdLen) maxWrdLen = len;
                }
                String posS = new String(buffer);
                String[] posA = Utility.parseStringToArray(posS," ");

                /*
                InitialContext jndiContext = new InitialContext(props);
                Object lawsonRef  = jndiContext.lookup
                    (JNDINames.LAWSON_EJBHOME);
                LawsonHome lawsonHome = (LawsonHome)
                    PortableRemoteObject.narrow (lawsonRef, LawsonHome.class);
                Lawson lawsonEjb = lawsonHome.create();
                for (int ii=0; ii<posA.length; ii++) {
                  String poS = posA[ii].trim();
                  if(poS.length()>0) {
                    log.info("AppCmd po to release: "+poS);
                    lawsonEjb.releasePo(poS,"AppCmd Po Release");
                  }
                }
                */
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("Function: " + cmd +
                               " is done:      " +
                               endDate );
            return;

        }
       /* else if ( cmd.equals("to_lawson_pipeline" ) )
        {

            Date startDate = new Date();
            log.info("Function: " + cmd +
                               " initiated:      " +
                               startDate );

            try
            {

                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                    (JNDINames.PIPELINE_EJBHOME);
                PipelineHome pipelineHome = (PipelineHome)
                    PortableRemoteObject.narrow (ref, PipelineHome.class);
                Pipeline pipelineEjb = pipelineHome.create();

                Object orderRef  = jndiContext.lookup
                    (JNDINames.ORDER_EJBHOME);
                OrderHome orderHome = (OrderHome)
                    PortableRemoteObject.narrow (orderRef, OrderHome.class);
                Order orderEjb = orderHome.create();

                Object propertyServiceRef  = jndiContext.lookup
                    (JNDINames.PROPERTY_SERVICE_EJBHOME);
                PropertyServiceHome propertyServiceHome = (PropertyServiceHome)
                    PortableRemoteObject.narrow (propertyServiceRef, PropertyServiceHome.class);
                PropertyService propertyServiceEjb = propertyServiceHome.create();

                //Get erp statuses
               PropertyDataVector propDV =
               propertyServiceEjb.getProperties(null, null,RefCodeNames.PROPERTY_TYPE_CD.ERP_STATUS);
               HashMap erpStatusHM = new HashMap();
               for(Iterator iter=propDV.iterator(); iter.hasNext();) {
                 PropertyData pD = (PropertyData) iter.next();
                 String shortDesc = pD.getShortDesc();
                 if(!RefCodeNames.ERP_SYSTEM_CD.LAWSON.equals(shortDesc)) {
                   continue;
                 }
                 String value = pD.getValue();
                 if(shortDesc==null || value==null) {
                   continue;
                 }
                 if(!RefCodeNames.PROPERTY_STATUS_CD.ACTIVE.equals(pD.getPropertyStatusCd())) {
                   continue;
                 }
                 String vv = (String) erpStatusHM.get(shortDesc);
                 if(vv==null) {
                   erpStatusHM.put(shortDesc,value);
                 } else {
                   if(!vv.equals(value)) {
                     String errMess = "***** ERROR !!!!!! Erp System "+shortDesc+" has multiple status records";
                     log.info(errMess);
                     erpStatusHM.put(shortDesc,"ERROR");
                   }
                 }
               }

               if("ACTIVE".equals(erpStatusHM.get(RefCodeNames.ERP_SYSTEM_CD.LAWSON))) {

                //send orders to lawson
                 OrderDataVector orders =  orderEjb.getOrdersReadyForLawson();

                 for(Iterator iter = orders.iterator(); iter.hasNext();){
                   OrderData oD = (OrderData) iter.next();
                   if(RefCodeNames.WORKFLOW_IND_CD.INTERRUPTED.equals(oD.getWorkflowInd())||
                      RefCodeNames.WORKFLOW_IND_CD.TO_RESUME.equals(oD.getWorkflowInd())) {
                     continue;
                   }
                   try {
log.info("AppCmd.java AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA got order: "+oD.getOrderId());
                     pipelineEjb.processPipeline(oD,RefCodeNames.PIPELINE_CD.ORDERS_TO_LAWSON);
                   } catch (Exception exc){
                       String errorMess = "Pipeline exception. Order id: "+
                                    oD.getOrderId() +" Error: "+ exc.getMessage();
                       processPipelineException(orderEjb, oD, errorMess,
                                            RefCodeNames.PIPELINE_CD.ORDERS_TO_LAWSON);

                   }
                 }
               }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("Function: " + cmd +
                               " is done:      " +
                               endDate );
            return;

        }*/



//        else if ( cmd.equals("cancel_po_item" ) )
//        {
//            Date startDate = new Date();
//	    String erpnum = System.getProperty("erp_order_num"),
//		sku_num = System.getProperty("sku_num");
//            log.info("\n" + cmd +
//                               " processing initiated:      " +
//			       " erpnum="+erpnum +
//			       " sku_num=" + sku_num +
//                                " " + startDate );
//
//            try            {
//
//                InitialContext jndiContext = new InitialContext(props);
//                Object ref  = jndiContext.lookup
//                    (JNDINames.PIPELINE_EJBHOME);
//
//                Object orderRef  = jndiContext.lookup
//                    (JNDINames.ORDER_EJBHOME);
//                OrderHome orderHome = (OrderHome)
//                    PortableRemoteObject.narrow (orderRef, OrderHome.class);
//                Order orderEjb = orderHome.create();
//		// look up the order by erp order number
//		OrderStatusCriteriaData searchCriteria =
//		    OrderStatusCriteriaData.createValue();
//		searchCriteria.setErpOrderNum(erpnum);
//
//		OrderStatusDescDataVector orderStatus =
//		    new OrderStatusDescDataVector();
//		orderStatus = orderEjb.getOrderStatusDescCollection
//		    (searchCriteria);
//
//		if ( null == orderStatus || orderStatus.size() == 0 ) {
//		    log.info(" --- " + cmd +
//				       " order not found:      " +
//				       " erpnum="+erpnum +
//				       " sku_num=" + sku_num );
//		    return;
//		}
//
//		if ( orderStatus.size() > 1 ) {
//		    log.info(" --- " + cmd +
//				       " multiple orders found:      " +
//				       " erpnum="+erpnum +
//				       " sku_num=" + sku_num );
//		    return;
//		}
//
//		// get the order item id for the sku being cancelled
//		OrderStatusDescData osdd = (OrderStatusDescData)
//		    orderStatus.get(0);
//		OrderItemDataVector oidv =
//		    (OrderItemDataVector)osdd.getOrderItemList();
//
//		if ( null == oidv || oidv.size() == 0 ) {
//		    log.info(" --- " + cmd +
//				       " order items not found:      " +
//				       " erpnum="+erpnum +
//				       " sku_num=" + sku_num );
//		    return;
//		}
//
//		int orderitemId = 0;
//		OrderItemData oid = null;
//		for ( int i = 0; i < oidv.size(); i++ ) {
//		    oid = (OrderItemData)oidv.get(i);
//		    if ( oid.getItemSkuNum() ==
//			 Integer.parseInt(sku_num)) {
//			orderitemId = oid.getOrderItemId();
//			break;
//		    }
//		}
//		log.info(" --- " + cmd +
//				   " order items found:      " +
//				   " erpnum="+erpnum +
//				   " sku_num=" + sku_num +
//				   " orderitemId=" + orderitemId
//				   );
//
//		if ( orderitemId== 0) {
//		    return;
//		}
//
//		if ( oid.getOrderItemStatusCd().equals
//		     ("CANCELLED") ||
//		     oid.getOrderItemStatusCd().equals
//		     ("PO_ACK_SUCCESS") ||
//		     oid.getOrderItemStatusCd().equals
//		     ("SENT_TO_DISTRIBUTOR") ) {
//		} else {
//		    log.info(" --- " + cmd +
//				       " invalid item status:      " +
//				       " erpnum="+erpnum +
//				       " sku_num=" + sku_num +
//				       " orderitemId=" + orderitemId +
//				       " status=" + oid.getOrderItemStatusCd()
//				       );
//		    return;
//		}
//
//		// cancel the order item
//		IdVector itemIdV = new IdVector();
//		itemIdV.add(new Integer(orderitemId));
//		String userName = "appcmd:cancel";
//		orderEjb.cancelOrderItems(osdd.getOrderDetail(),
//					  itemIdV, userName, false);
//
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//
//            Date endDate = new Date();
//            log.info(" --- " + cmd +
//                               " processing done:      " +
//			       " erpnum="+erpnum +
//			       " sku_num=" + sku_num +
//                               " " + endDate );
//            return;
//
//        }
//        else if ( cmd.equals("invoice_po_item" ) )
//        {
//            Date startDate = new Date();
//	    String erpnum = System.getProperty("erp_order_num"),
//		sku_num = System.getProperty("sku_num");
//            log.info("\n" + cmd +
//                               " processing initiated:      " +
//			       " erpnum="+erpnum +
//			       " sku_num=" + sku_num +
//                                " " + startDate );
//
//            try            {
//
//                InitialContext jndiContext = new InitialContext(props);
//                Object ref  = jndiContext.lookup
//                    (JNDINames.PIPELINE_EJBHOME);
//
//                Object orderRef  = jndiContext.lookup
//                    (JNDINames.ORDER_EJBHOME);
//                OrderHome orderHome = (OrderHome)
//                    PortableRemoteObject.narrow (orderRef, OrderHome.class);
//                Order orderEjb = orderHome.create();
//		// look up the order by erp order number
//		OrderStatusCriteriaData searchCriteria =
//		    OrderStatusCriteriaData.createValue();
//		searchCriteria.setErpOrderNum(erpnum);
//
//		OrderStatusDescDataVector orderStatus =
//		    new OrderStatusDescDataVector();
//		orderStatus = orderEjb.getOrderStatusDescCollection
//		    (searchCriteria);
//
//		if ( null == orderStatus || orderStatus.size() == 0 ) {
//		    log.info(" --- " + cmd +
//				       " order not found:      " +
//				       " erpnum="+erpnum +
//				       " sku_num=" + sku_num );
//		    return;
//		}
//
//		if ( orderStatus.size() > 1 ) {
//		    log.info(" --- " + cmd +
//				       " multiple orders found:      " +
//				       " erpnum="+erpnum +
//				       " sku_num=" + sku_num );
//		    return;
//		}
//
//		// get the order item id for the sku being invoiced
//		OrderStatusDescData osdd = (OrderStatusDescData)
//		    orderStatus.get(0);
//		OrderItemDataVector oidv =
//		    (OrderItemDataVector)osdd.getOrderItemList();
//
//		if ( null == oidv || oidv.size() == 0 ) {
//		    log.info(" --- " + cmd +
//				       " order items not found:      " +
//				       " erpnum="+erpnum +
//				       " sku_num=" + sku_num );
//		    return;
//		}
//
//		int orderitemId = 0;
//		OrderItemData oid = null;
//		for ( int i = 0; i < oidv.size(); i++ ) {
//		    oid = (OrderItemData)oidv.get(i);
//		    if ( oid.getItemSkuNum() ==
//			 Integer.parseInt(sku_num)) {
//			orderitemId = oid.getOrderItemId();
//			break;
//		    }
//		}
//		log.info(" --- " + cmd +
//				   " order items found:      " +
//				   " erpnum="+erpnum +
//				   " sku_num=" + sku_num +
//				   " orderitemId=" + orderitemId
//				   );
//
//		if ( orderitemId== 0) {
//		    return;
//		}
//
//		if ( oid.getOrderItemStatusCd().equals("INVOICED")    ) {
//		} else {
//		    log.info(" --- " + cmd +
//				       " invalid item status:      " +
//				       " erpnum="+erpnum +
//				       " sku_num=" + sku_num +
//				       " orderitemId=" + orderitemId +
//				       " status=" + oid.getOrderItemStatusCd()
//				       );
//		    return;
//		}
//
//		// invoice the order item
//		IdVector itemIdV = new IdVector();
//		itemIdV.add(new Integer(orderitemId));
//		String userName = "appcmd:invoice";
//		/*		orderEjb.cancelOrderItems(osdd.getOrderDetail(),					  itemIdV, userName, false);					  */
//
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//
//            Date endDate = new Date();
//            log.info(" --- " + cmd +
//                               " processing done:      " +
//			       " erpnum="+erpnum +
//			       " sku_num=" + sku_num +
//                               " " + endDate );
//            return;
//
//        }
        else if ( cmd.equals("order_through_pipeline" ) )
        {
            Date startDate = new Date();
            log.info("Testing: " + cmd + " processing initiated:      " +  startDate );

            String orderIdString = System.getProperty("order_id");
            if ( null == orderIdString )
            {
                log.info(usage);
                return;
            }

            int iOrderId = Integer.parseInt( orderIdString );
            log.info("Order id: " + iOrderId );

            try
            {

                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup(JNDINames.PIPELINE_EJBHOME);
                PipelineHome pipelineHome = (PipelineHome)PortableRemoteObject.narrow (ref, PipelineHome.class);
                Pipeline pipelineEjb = pipelineHome.create();

                Object orderRef  = jndiContext.lookup(JNDINames.ORDER_EJBHOME);
                OrderHome orderHome = (OrderHome) PortableRemoteObject.narrow (orderRef, OrderHome.class);
                Order orderEjb = orderHome.create();

                OrderData oD = orderEjb.getOrderStatus(iOrderId);

                if ( null == oD ) {
                    log.info("NO ORDER FOUND for, Order id: " + iOrderId );
                    return;
                }

                try {
                    pipelineEjb.processPipeline(oD, RefCodeNames.PIPELINE_CD.REPROCESS_ORDER);
                } catch (Exception exc){
                    String errorMess = "REPROCESS_ORDER Pipeline exception. "
                            + " Order id: "+
                            oD.getOrderId() +" Error: "+ exc.getMessage();
                    log.info(errorMess);
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("Testing: " + cmd + " processing done:      " +  endDate );
            return;

        }

      /*  else if ( cmd.equals("orders_from_lawson" ) )
        {
            Date startDate = new Date();
            log.info("Lawson: " + cmd +
                               " processing initiated:      " +
                               startDate );

            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                    (JNDINames.LAWSON_EJBHOME);
                LawsonHome lHome = (LawsonHome)
                    PortableRemoteObject.narrow (ref, LawsonHome.class);
                Lawson lBean = lHome.create();
                //lBean.poProcessing("system_orders_from_ERP");


				  IdVector releasedOrderIds = lBean.getReleasedOrderIds();
			      if(releasedOrderIds.size()==0) {
			        String mess = "No unprocessed released orders found";
			        log.info(mess);
			      } else {
			        int size = releasedOrderIds.size();
			        log.info("Found "+releasedOrderIds.size()+" Orders to process");
			        for(int ii=0; ii<size; ii++) {
			          Integer orderIdI = (Integer) releasedOrderIds.get(ii);
			          try {
				        log.info("Lawson.poProcessing order:" + ii +" of " + releasedOrderIds.size() );
			            lBean.processReleasedOrderAsynch(orderIdI.intValue(), "system_orders_from_ERP");
			          } catch (Exception exc) {

			          }

			        }
			      }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("Lawson: " + cmd +
                               " processing done:      " +
                               endDate );
            return;

        } */
      /*  else if ( cmd.equals("invoices_from_lawson" ) )
        {
            Date startDate = new Date();
            log.info("Lawson: " + cmd +
                               " processing initiated:      " +
                               startDate );

            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                    (JNDINames.LAWSON_EJBHOME);
                LawsonHome lHome = (LawsonHome)
                    PortableRemoteObject.narrow (ref, LawsonHome.class);
                Lawson lBean = lHome.create();
                lBean.custInvoiceProcessing("system_inv_from_ERP");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("Lawson: " + cmd +
                               " processing done:      " +
                               endDate );
            return;

        }  */
        else if ( cmd.equals("cpsite") )
        {
            String
		sid = System.getProperty("siteid"),
		toacctid = System.getProperty("toacctid"),
		catid = System.getProperty("catalogid"),
		newsiteprefix = System.getProperty("newprefix"),
		newrole = System.getProperty("newrole")
		;

            log.info("copy site id=" + sid);
            if ( sid == null )  {
                return;
            }

	    SiteReqUtil sru = new SiteReqUtil(props);
	    CopySiteRequest req = new
		CopySiteRequest(
			    Integer.parseInt(sid),
			    Integer.parseInt(toacctid)
			    );
	    req.setCatalogIdForSite(Integer.parseInt(catid));
	    req.setCopyUsersFlag(true);
	    req.setUserRoleForAccount(newrole);
	    req.setNewSiteNamePrefix(newsiteprefix);
	    sru.cpSite(req);

            return;

        }
        else if ( cmd.equals("checksite") )
        {
            String
            sid = System.getProperty("siteid");

            log.info("Check site id=" + sid);
            if ( sid == null )  {
                return;
            }

	    SiteReqUtil sru = new SiteReqUtil(props);
	    sru.checkSite(Integer.parseInt(sid));
            return;

        } else if ( cmd.equals("ssp") )  {
            String sn, pn, pv, an;
            sn = System.getProperty("sn");
            pn = System.getProperty("pn");
            pv = System.getProperty("pv");
            an = System.getProperty("accountId");
            int acctid = Integer.parseInt(an);

            log.info("Set site property, " +
                               " site name, sn=" + sn +
                               " account id, accountId=" + acctid +
                               " property, pn=" + pn +
                               " value, pv=" + pv );

            if ( sn == null || pn == null || pn == null )
            {
                return;
            }

            // Get the bus entity for the site name.
            // Set the property.
            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                    (JNDINames.SITE_EJBHOME);
                SiteHome sHome = (SiteHome)
                    PortableRemoteObject.narrow (ref, SiteHome.class);
                Site siteBean = sHome.create();
                SiteDataVector svec =
                    siteBean.getSiteByName(sn, acctid, Site.EXACT_MATCH,
                                           Site.ORDER_BY_ID);
                if ( svec.size() == 0 )
                {
                    log.info
                        ("no site found for site name: " + sn);
                    return;
                }
                if ( svec.size() > 1 )
                {
                    log.info
                        ("more than one site found for site name: " + sn);
                    return;
                }
                SiteData sD = (SiteData)svec.get(0);
                int beid = sD.getBusEntity().getBusEntityId();
                log.info("beid=" + beid);
                PropertyData pD = PropertyData.createValue();
                pD.setBusEntityId(beid);
                pD.setShortDesc(pn);
                pD.setValue(pv);
                PropertyDataVector pdv = new PropertyDataVector();
                pdv.add(pD);
                siteBean.saveSiteFields(beid, pdv);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return;
        }
        else if ( cmd.equals("ssw") )
        {
            String sn, wks, an;
            sn = System.getProperty("sn");
            wks = System.getProperty("workflowId");
            an = System.getProperty("accountId");
            int acctid = Integer.parseInt(an);
            int wkflid = Integer.parseInt(wks);
            log.info("Set site workflow, " +
                               " site name, sn=" + sn +
                               " account id, accountId=" + acctid +
                               " workflow, wkflid=" + wkflid
                               );

            if ( sn == null || an == null || wks == null )
            {
                return;
            }

            // Get the bus entity for the site name.
            // Set the workflow.
            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                    (JNDINames.SITE_EJBHOME);
                SiteHome sHome = (SiteHome)
                    PortableRemoteObject.narrow (ref, SiteHome.class);
                Site siteBean = sHome.create();

                SiteDataVector svec =
                    siteBean.getSiteByName(sn, acctid, Site.EXACT_MATCH,
                                           Site.ORDER_BY_ID);
                if ( svec.size() == 0 )
                {
                    log.info
                        ("no site found for site name: " + sn);
                    return;
                }
                if ( svec.size() > 1 )
                {
                    log.info
                        ("more than one site found for site name: " + sn);
                    return;
                }
                SiteData sD = (SiteData)svec.get(0);
                int beid = sD.getBusEntity().getBusEntityId();
                log.info("beid=" + beid);

                Object ref2  = jndiContext.lookup
                    (JNDINames.WORKFLOW_EJBHOME);
                WorkflowHome wHome = (WorkflowHome)
                    PortableRemoteObject.narrow (ref2, WorkflowHome.class);
                Workflow wkflBean = wHome.create();
                wkflBean.assignWorkflow(wkflid, beid, "system");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return;
        }
      /*  else if ( cmd.equals("remits_to_lawson" ) )
        {
            Date startDate = new Date();
            log.info("Lawson: " + cmd +
                               " processing initiated:      " +
                               startDate );

            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                    (JNDINames.LAWSON_UPLOAD_EJBHOME);
                LawsonUploadHome lHome = (LawsonUploadHome)
                    PortableRemoteObject.narrow (ref, LawsonUploadHome.class);
                LawsonUpload lBean = lHome.create();
                lBean.remittanceProcessing("system_remits_to_ERP");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("Lawson: " + cmd +
                               " processing done:      " +
                               endDate );
            return;

        }   */
        else if ( cmd.equals("copy_order" ) )
        {
            String orderIdString = System.getProperty("order_id");
            if ( null == orderIdString )
            {
                log.info(usage);
                return;
            }
            int iOrderId = Integer.parseInt( orderIdString );
            log.info("Copy order id: " + iOrderId );

            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                    (JNDINames.INTEGRATION_SERVICES_EJBHOME);
                IntegrationServicesHome lHome = (IntegrationServicesHome)
                    PortableRemoteObject.narrow (ref, IntegrationServicesHome.class);
                IntegrationServices lBean = lHome.create();
                int newOrderId = lBean.copyOrder(iOrderId);
                log.info("New order id: " + newOrderId );
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return;

        }
        else if ( cmd.equals("invoices_to_lawson_deprecated" ) )
        {

        	Date startDate = new Date();
            log.info("Lawson: " + cmd +
                               " processing initiated:      " +
                               startDate );

            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                    (JNDINames.PIPELINE_EJBHOME);
                PipelineHome lHome = (PipelineHome)
                    PortableRemoteObject.narrow (ref, PipelineHome.class);
                Pipeline lBean = lHome.create();
                lBean.processDistInvoicePipeline();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("Lawson: " + cmd +
                               " processing done:      " +
                               endDate );
            return;

        }

        else if ( cmd.equals("self_store_dist_invoices" ) )
        {
            Date startDate = new Date();
            log.info("Command: " + cmd +
                               " processing initiated:      " +
                               startDate );

            try
            {
                InitialContext jndiContext = new InitialContext(props);
                Object ref  = jndiContext.lookup
                    (JNDINames.PIPELINE_EJBHOME);
                PipelineHome lHome = (PipelineHome)
                    PortableRemoteObject.narrow (ref, PipelineHome.class);
                Pipeline lBean = lHome.create();
                lBean.processSelfStoreDistInvPipeline();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("Lawson: " + cmd +
                               " processing done:      " +
                               endDate );
            return;

        }
      /*  else if ( cmd.equals("item_info_to_lawson" ) )
        {
            String sku = System.getProperty("sku");
            if ( null == sku ) sku = "";

            Date startDate = new Date();
            log.info("Lawson: " + cmd +
                               " sku=" + sku +
                               " processing initiated:      " +
                               startDate );

            try
            {
                InitialContext jndiContext = new InitialContext(props);

                Object ref4  = jndiContext.lookup
                    (JNDINames.DISTRIBUTOR_EJBHOME);
                DistributorHome distHome = (DistributorHome)
                    PortableRemoteObject.narrow
                    (ref4, DistributorHome.class);
                Distributor distBean = distHome.create();

                Object ref3  = jndiContext.lookup
                    (JNDINames.CATALOG_INFORMATION_EJBHOME);
                CatalogInformationHome catHome = (CatalogInformationHome)
                    PortableRemoteObject.narrow
                    (ref3, CatalogInformationHome.class);
                CatalogInformation catBean = catHome.create();

                Object ref2  = jndiContext.lookup
                    (JNDINames.ITEM_INFORMATION_EJBHOME);
                ItemInformationHome itemHome = (ItemInformationHome)
                    PortableRemoteObject.narrow (ref2, ItemInformationHome.class);
                ItemInformation itemBean = itemHome.create();

                Object ref  = jndiContext.lookup
                    (JNDINames.LAWSON_ADMIN_EJBHOME);
                LawsonAdminHome lHome = (LawsonAdminHome)
                    PortableRemoteObject.narrow (ref, LawsonAdminHome.class);
                LawsonAdmin lBean = lHome.create();

                DistributorDataVector distv =
                    distBean.getAllDistributors(Distributor.ORDER_BY_NAME);

                Date currDate = new Date();
                ItemDataVector iv = itemBean.getItemsCollectionByCatalog(0);
                // Iterate through all the items found.
                for ( int i = 0; iv != null && i < iv.size(); i++ )
                {
                    ItemData idata = (ItemData)iv.get(i);
                    if ( idata.getSkuNum() > 0 )
                    {
                        if ( sku.length() > 0 &&
                             sku.equals(String.valueOf(idata.getSkuNum())) == false)
                        {
                            continue;
                        }
                        log.info("Lawson: " + cmd +
                                           " sku=" + idata.getSkuNum()
                                           + " index=" + i
                                           + " of " + iv.size()
                                           );

                        try
                        {
                            LawsonItemView liv =
                                lBean.getLawsonItem(idata.getSkuNum());
                            IdVector idv = new IdVector();
                            idv.add(new Integer(idata.getItemId()));
                            ProductDataVector pdv =
                                catBean.getProductCollection(idv);
                            for ( int lidx = 0;
                                  null != pdv && lidx < pdv.size();
                                  lidx++ )
                            {
                                ProductData productD = (ProductData)pdv.get(lidx);
                                String uom = productD.getUom();
                                String status = "A";
                                Date effDate = productD.getEffDate();
                                if(effDate==null || effDate.after(currDate)) {
                                    status = "I";
                                }
                                Date expDate = productD.getExpDate();
                                if(expDate!=null && expDate.before(currDate)) {
                                    status = "I";
                                }

                                liv.setStatus(status);
                                String shortDesc = productD.getShortDesc();
                                if(shortDesc == null) shortDesc = "";
                                shortDesc = shortDesc.trim();
                                if(shortDesc.length()<=30) {
                                    liv.setShortDesc1(shortDesc);
                                    liv.setShortDesc2("");
                                } else {
                                    String shortDesc1 = shortDesc.substring(0,30);
                                    int ind = shortDesc1.lastIndexOf(' ');
                                    if(ind<=0) ind = 30;
                                    liv.setShortDesc1(shortDesc1.substring(0,ind));
                                    String shortDesc2 = shortDesc.substring(ind).trim();
                                    if(shortDesc2.length()>30) {
                                        liv.setShortDesc2(shortDesc2.substring(0,30));
                                    } else {
                                        liv.setShortDesc2(shortDesc2);
                                    }
                                }
                                String lawsonUom = liv.getUom();
                                if(lawsonUom!=null && lawsonUom.trim().length()>0) {
                                    if(!lawsonUom.trim().equals(uom)) {
                                        liv.setUom(uom);
                                    }
                                }

                                // Update the Lawson data with product data.
                                lBean.updateLawsonItem(liv);

                                // Update the information for all
                                // distributors which support this item.
                                for (int didx = 0; distv != null && didx < distv.size();
                                     didx++)
                                {
                                    DistributorData distD = (DistributorData)distv.get(didx);
                                    String distNum = distD.getBusEntity().getErpNum();
                                    if(distNum == null ||
                                       distNum.trim().length()==0) {
                                        continue;
                                    }

                                    DistItemViewVector distItems = distBean.getDistItems
                                        (distD.getBusEntity().getBusEntityId(),idv);
                                    for(int ii=0; ii<distItems.size(); ii++) {
                                        DistItemView diVw = (DistItemView) distItems.get(ii);
                                        int skuNum = diVw.getSku();
                                        IdVector skuNums = new IdVector();
                                        skuNums.add(new Integer(skuNum));

                                        LawsonDistItemViewVector lawsonDistItemVV =
                                            lBean.getLawsonDistItems
                                            (skuNums, distNum);

                                        for ( int jj = 0; null != lawsonDistItemVV &&
                                                  jj < lawsonDistItemVV.size(); jj++ )
                                        {
                                            LawsonDistItemView ldiview =
                                                (LawsonDistItemView)lawsonDistItemVV.get(jj);

                                            if ( ldiview.getCompany() ==null ||
                                                 ldiview.getCompany().intValue() <= 0 ||
                                                 ldiview.getSkuNum() ==null ||
                                                 ldiview.getSkuNum().length() == 0
                                                 )
                                            {
                                                continue;
                                            }

                                            String newDistSku = diVw.getDistItemSku(),
                                                newUom = diVw.getDistItemUom();
                                            boolean doUpdate = false;
                                            if ( newDistSku != null &&
                                                 newDistSku.length() > 0 &&
                                                 newDistSku.equals(ldiview.getDistSkuNum() ) == false)
                                            {
                                                doUpdate = true;
                                                ldiview.setDistSkuNum(newDistSku);
                                            }

                                            //  Don't update the UOM.
                                            //  durval, 2/4/2004
                                           // if ( newUom != null &&
                                           //      newUom.length() > 0 &&
                                           / //     newUom.equals(ldiview.getDistUom()) == false )
                                            //{
                                            //    doUpdate = true;
                                            //    ldiview.setDistUom(newUom);
                                            //}

                                            if ( doUpdate )
                                            {
                                                log.info("Update data, ldiview=" + ldiview);
                                                try {
                                                    lBean.updateLawsonDistItem(ldiview);
                                                }
                                                catch(com.cleanwise.service.api.util.LawsonException e) {
                                                    String msg = e.getMessage();
                                                    if (msg.indexOf("Vendor item already exists") > 0 ) {
                                                        log.info(msg);
                                                    }
                                                    else
                                                    {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                        catch (com.cleanwise.service.api.util.DataNotFoundException e)
                        {
                            log.info("Item NOT IN LAWSON: " +
                                               " sku=" + idata.getSkuNum()
                                               + " index=" + i
                                               + " of " + iv.size()
                                               );
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("Lawson: " + cmd +
                               " processing done:      " +
                               endDate );
            return;

        }*/
         else if(cmd.equals("process_removed_og_items")) {
            try
            {
              InitialContext jndiContext = new InitialContext(props);
              Object ref  = jndiContext.lookup(JNDINames.ORDER_GUIDE_EJBHOME);
              OrderGuideHome orderGuideHome = (OrderGuideHome)
                    PortableRemoteObject.narrow (ref, OrderGuideHome.class);
              OrderGuide orderGuideEjb = orderGuideHome.create();
              orderGuideEjb.sendItemDeleteEmail();

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            Date endDate = new Date();
            log.info("AppCmd: " + cmd +
                               " processing done:      " +
                               endDate );
            return;

        } else if(cmd.equals("add_content")) {
            try
            {
              InitialContext jndiContext = new InitialContext(props);
              Object ref  = jndiContext.lookup(JNDINames.CONTENT_EJBHOME);
              ContentHome cHome = (ContentHome)
                    PortableRemoteObject.narrow (ref, ContentHome.class);
              Content cont = cHome.create();
	      String host = System.getProperty("thishost"),
		  path = System.getProperty("path");
	      log.info(cont.addContent(host, path));
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return;


        } else if(cmd.equals("synch_content_deprecated")) {
 
        	log("Running synch content");
        	if(Utility.isTrue(System.getProperty("refetchAllContent"))){
        		log("force refresh called");
        		getAPIAccess().getContentAPI().refreshBinaryData(true);
        	}else{
        		log("force refresh not called, just fetching changes");
        		getAPIAccess().getContentAPI().refreshBinaryData(false);
        	}
                return;
	} else if(cmd.equals("status")) {
            try
            {
              InitialContext jndiContext = new InitialContext(props);
              Object ref  = jndiContext.lookup(JNDINames.REQUEST_EJBHOME);
              RequestHome reqHome = (RequestHome)
                    PortableRemoteObject.narrow (ref, RequestHome.class);
              Request reqEjb = reqHome.create();
	      log.info(reqEjb.appStatus());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return;


        } else if(cmd.equals("daily_order_reprocess_deprecated")) {

        	try{
                log.info("Specify -DDate=<MMddyyyy> to run for a specific date.");
                log.info("Specify -DmaxRows=optional maximum rows to process (defualt=500, -1 for unlimited).");
                int weeksBack = 9;
                int maxRows = 0;
                String aDate = System.getProperty("Date");
                SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
                String onum = System.getProperty("orderNum");
                String sMaxRows = System.getProperty("maxRows");

                Date today = new Date();

		if ( null != onum ) {
		    log.info("reprocess orderNum="+onum);
		}

                if(aDate != null){
                    try{
                        log.info("Runing for specified date: "+aDate);
                        today = sdf.parse(aDate);
                    }catch(Exception e){
                        log.info("Could not parse Date: ["+aDate+"] must use format MMddyyyy");
                        return;
                    }
                }else{
                    log.info("No date specified runing normal date logic");
                }
                
                if (Utility.isSet(sMaxRows )) {
        		    log.info("maxRows="+sMaxRows);
        		    maxRows = new Integer(sMaxRows).intValue();
        		}

                sdf = new SimpleDateFormat("MM/dd/yyyy");
                APIAccess factory = getAPIAccess(loadConfiguration());
                Pipeline pipelineEjb = factory.getPipelineAPI();
                Order orderEjb = factory.getOrderAPI();
                OrderDataVector orders = new OrderDataVector();

                if(aDate == null && null == onum ){
                    int interval = -7;
                    for(int i=0;i<weeksBack;i++){
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(today);
                        Date begDate;
                        Date endDate;
                        if(i == 0){
                            //the first week we will just reprocess everything.
                            cal.add(Calendar.DAY_OF_YEAR, interval);
                            begDate = cal.getTime();
                            endDate = today;
                        }else{
                            log.info("b4: "+sdf.format(cal.getTime()));
                            cal.add(Calendar.DAY_OF_YEAR,interval * i);
                            log.info("after: "+sdf.format(cal.getTime()));
                            Date[] range = getDateRangeForTodayPlusWeekend(cal.getTime());
                            begDate = range[0];
                            endDate = range[1];
                        }

                        log.info("Finding orders between: "+sdf.format(begDate) +" and "+sdf.format(endDate));

                        //go find the orders
                        OrderStatusCriteriaData crit = OrderStatusCriteriaData.createValue();
                        crit.setOrderDateRangeBegin(sdf.format(begDate));
                        crit.setOrderDateRangeEnd(sdf.format(endDate));
                        crit.addOrderStatus(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
                        crit.addOrderStatus(RefCodeNames.ORDER_STATUS_CD.INVOICED);
                        if (maxRows != 0)
                        	crit.setMaxRows(maxRows);
                        orders.addAll(orderEjb.getOrderStatusCollection(crit));
                    }
                }else if ( null != onum ) {
                    //go find the order
                    OrderStatusCriteriaData crit = OrderStatusCriteriaData.createValue();
                    crit.setWebOrderConfirmationNum (onum);
                    orders.addAll(orderEjb.getOrderStatusCollection(crit));
                }else{
                    //go find the orders
                    OrderStatusCriteriaData crit = OrderStatusCriteriaData.createValue();
                    crit.setOrderDateRangeBegin(sdf.format(today));
                    crit.setOrderDateRangeEnd(sdf.format(today));
                    crit.addOrderStatus(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
                    crit.addOrderStatus(RefCodeNames.ORDER_STATUS_CD.INVOICED);
                    if (maxRows != 0)
                    	crit.setMaxRows(maxRows);
                    orders.addAll(orderEjb.getOrderStatusCollection(crit));
                }


                log.info("Found "+orders.size()+" to process");

                //finally send the orders trhough the pipeline
                int ct = 0;
                for(Iterator iter = orders.iterator(); iter.hasNext();){
                   ct++;
                   OrderData oD = (OrderData) iter.next();
				   String orderStatus = oD.getOrderStatusCd();
                   if(ct % 50 == 0){
                       log.info("Processed "+ct+" orders");
                   }
                   try {
                     pipelineEjb.processPipeline(oD,RefCodeNames.PIPELINE_CD.BATCH_REPROCESS_ORDER);
                   } catch (Exception exc){
                     String errorMess = "Pipeline exception. Order id: "+oD.getOrderId() +" Error: "+ exc.getMessage();
                     processPipelineException(orderEjb, oD, errorMess, RefCodeNames.PIPELINE_CD.BATCH_REPROCESS_ORDER,orderStatus);
                   }
                }
            }catch (Exception e)	{
		e.printStackTrace();
	    }

	    // need the return so as to avoid the usage message
	    return;
        }
        else if(cmd.equals("daily_multi_order_reprocess"))
         {
             log.info("************************************************************");
             long startTime = System.currentTimeMillis();
             log.info("daily_multi_order_reprocess started at: " +new Date(startTime));
             log.info("*************************************************************");

             log.info("Specify -DDate=<MMddyyyy> to run for a specific date.");
             String begDate = System.getProperty("begDate");
             String endDate=System.getProperty("endDate");
             SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
             String onum = System.getProperty("orderNum");
             IdVector orderIds=null;
             String logOn =System.getProperty("logOn");
             String logMode=System.getProperty("logMode");
             String logLevel=System.getProperty("logLevel");
             String logOut=System.getProperty("logOut");
             String dbOperation=System.getProperty("dbOperation");
             String logFileName=System.getProperty("logFileName");

             Hashtable parametrs=new Hashtable();
             Date today =null;
             Date preDate=null;

             if ( null != onum ) {
                 //parse
                 StringTokenizer st=new StringTokenizer(onum,",");
                 orderIds=new IdVector();
                 try {
                     while(st.hasMoreElements())
                     {
                         orderIds.add(new Integer((String)st.nextElement()));
                     }
                 } catch (NumberFormatException e) {
                     log.info("it's impossible to parse a orders," +
                             " the reason is \"Not correct input string :  ["+onum+"]\"" +
                             " Input string must use format <orderId1,orderId2,orderId3...>");
                     return;
                 }
              log.info("reprocess orderNum="+orderIds);
             }

             if(begDate != null){
                 try{
                     today = sdf.parse(begDate);
                 }catch(Exception e){
                    log.info("The data to be converted to date format was incorrect." +
                             " Input string :  ["+begDate+"]\"" +
                             " Date must use format MMddyyyy");
                     return;
                 }
             }else{
                 log.info("No begin date specified runing normal date logic");
            }
             if(endDate != null&&today!=null){
                 try{
                     preDate = sdf.parse(endDate);

                 }catch(Exception e){
                      log.info("The data to be converted to date format was incorrect." +
                             " Input string :  ["+endDate+"]\"" +
                             " Date must use format MMddyyyy");;
                     return;
                 }
             }else if(today!=null){
                 Calendar cal = Calendar.getInstance();
                 cal.setTime(today);
                 cal.add(Calendar.DAY_OF_YEAR,-1);
                 preDate=cal.getTime();
              }
             else
             {
                 log.info("No end date specified runing normal date logic");

             }
            if(preDate!=null && today!=null)
            log.info("Date range : ["+today +","+preDate+"]");

            if(logOn!=null) parametrs.put("logOn",logOn);
            if(logMode!=null) parametrs.put("logMode",logMode);
            if(logLevel!=null) parametrs.put("logLevel",logLevel);
            if(logOut!=null) parametrs.put("logOut",logOut);
            if(dbOperation!=null) parametrs.put("dbOperation",dbOperation);
            if(today!=null) parametrs.put("bDate",today);
            if(preDate!=null) parametrs.put("eDate",preDate);
            if(onum!=null) parametrs.put("orderNum",orderIds);
            if(logFileName!=null) parametrs.put("logFileName",logFileName);


            log.info("Params : "+parametrs);
            log.info("load Configuration and get APIAccess");
            APIAccess factory = getAPIAccess(loadConfiguration());
            Pipeline pipelineEjb = factory.getPipelineAPI();
            Order orderEjb = factory.getOrderAPI();
            log.info("load complete.Process time at : "+(-startTime+System.currentTimeMillis()) +"ms" );
            try {

                log.info("Run pipelineEjb.processPipeline");
                pipelineEjb.processPipeline(parametrs,RefCodeNames.PIPELINE_CD.BATCH_REPROCESS_MULTI_ORDER);
                log.info("************************************************************");
                log.info("daily_multi_order_reprocess completed successfully   at : " +new Date(System.currentTimeMillis()));
                log.info("Process time at : "+(System.currentTimeMillis()-startTime)+" ms ");
                log.info("*************************************************************");


            } catch (Exception exc){
                String errorMess = "Pipeline exception."+" Error: "+ exc.getMessage();
                processPipelineException(orderEjb, null, errorMess, RefCodeNames.PIPELINE_CD.BATCH_REPROCESS_MULTI_ORDER, null);
            }
         return;
        }
    else if(cmd.equals("reset_cost_centers_deprecated")) {

    		APIAccess factory = getAPIAccess(loadConfiguration());
            Catalog catalogEjb = factory.getCatalogAPI();
            CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
            EntitySearchCriteria crit = new EntitySearchCriteria();
            ArrayList accountTypeCdList = new ArrayList();
            accountTypeCdList.add(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
            crit.setSearchTypeCds(accountTypeCdList);
            CatalogDataVector cdv = catalogInfEjb.getCatalogsByCrit(crit);
            Iterator it = cdv.iterator();
            while(it.hasNext()){
                CatalogData cat = (CatalogData) it.next();
                if(RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cat.getCatalogStatusCd())){
                    log.info("resetting cost centers for catalog: "+cat.getShortDesc()+" "+cat.getCatalogTypeCd());
                    catalogEjb.resetCostCenters(cat.getCatalogId(),"System");
                }
            }
            return;
        } else if(cmd.equals("create_fiscal_cal_cache_table")) {
        	APIAccess factory = getAPIAccess(loadConfiguration());
        	Account acctEjb = factory.getAccountAPI();
        	acctEjb.createFiscalCalCacheTablesForAll();
        	return;
        } else if(cmd.equals("event_sys_test")) {

            APIAccess factory = getAPIAccess(loadConfiguration());
        	Event eventEjb = factory.getEventAPI();

            String sleepMillisStr = System.getProperty("millis");
            String loopCountStr   = System.getProperty("loop");
            String toAddress      = System.getProperty("toAddress");
            String subject        = System.getProperty("subject");
            String message        = System.getProperty("message");
            String eventEmail     = System.getProperty("eventEmail");

            int sleepMillisInt = 1000;
            int loopCountInt   = 1;

            try {
               sleepMillisInt=Integer.parseInt(sleepMillisStr);
            } catch (NumberFormatException e) {}

            try {
               loopCountInt=Integer.parseInt(loopCountStr);
            } catch (NumberFormatException e) {}

            if (Utility.isTrue(eventEmail)) {

//                EventData eventData = new EventData(0, Event.STATUS_READY, Event.TYPE_EMAIL, null, null, 1);
//                eventData = eventEjb.addEventToDB(eventData);

                EventEmailDataView eventEmailData = new EventEmailDataView();
                eventEmailData.setCcAddress(null);
                eventEmailData.setToAddress(toAddress);
                eventEmailData.setSubject(subject);
                eventEmailData.setText(message);
//                eventEmailData.setEventId(eventData.getEventId());
                eventEmailData.setEmailStatusCd(Event.STATUS_READY);
                eventEmailData.setModBy("event_sys_test");
                eventEmailData.setAddBy("event_sys_test");

//                eventEjb.addEventEmail(eventEmailData);
                EventData eventData = Utility.createEventDataForEmail();
                EventEmailView eev = new EventEmailView(eventData, eventEmailData);
                eventEjb.addEventEmail(eev, "event_sys_test");
            }

            eventEjb.runTest(loopCountInt,sleepMillisInt);

            return;

        } else if(cmd.equals("event_sys_job")) {

            APIAccess factory = getAPIAccess(loadConfiguration());
        	Event eventEjb = factory.getEventAPI();
            eventEjb.runJob();

            return;

        } else if(cmd.equals("process_event")) {
        	String eventIdsString = System.getProperty("eventIds");
        	log.info("eventIds: "+eventIdsString);
        	List eventIds = new ArrayList();
        	
        	if (Utility.isSet(eventIdsString)){
        		StringTokenizer st = new StringTokenizer(eventIdsString, ",");
        		while (st.hasMoreElements()) {
        			String eventIdString = (String) st.nextElement();
        			int eventId = (new Integer(eventIdString.trim())).intValue();
        			eventIds.add(new Integer(eventId).intValue());
        		}
        	}	
        	        	
        	if (eventIds.size() > 0){
        		APIAccess factory = getAPIAccess(loadConfiguration());
	        	Event eventEjb = factory.getEventAPI();
        		for (int i = 0; i < eventIds.size(); i++){
		    		int eventId = ((Integer)eventIds.get(i)).intValue();
		    		log.info("Processing event: "+eventId);
		    		eventEjb.processEvent(eventId);
		        }
	        }
            return;

        } else if(cmd.equals("set_outbound_po_num")) {
            String begDateS = System.getProperty("begDate");
            String endDateS = System.getProperty("endDate");
            if(!Utility.isSet(begDateS) || !Utility.isSet(endDateS)) {
               log.info("No begDate or endDate parameter set");
               return;
            }
            Date begDate = null;
            Date endDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                begDate = sdf.parse(begDateS);
            } catch (Exception exc) {
               log.info("Wrong begDate format (should be MM/dd/yyyy): "+begDateS);
               return;
            }
            try {
                endDate = sdf.parse(endDateS);
            } catch (Exception exc) {
               log.info("Wrong endDate format (should be MM/dd/yyyy): "+endDateS);
               return;
            }
            APIAccess factory = getAPIAccess(loadConfiguration());
            PurchaseOrder poEjb = factory.getPurchaseOrderAPI();
            IdVector poIds = poEjb.getOrdersWithoutOutboundPoNum(sdf.format(begDate), sdf.format(endDate));
            for(Iterator iter = poIds.iterator(); iter.hasNext();) {
                Integer poIdI = (Integer) iter.next();
                poEjb.setOutboundPoNum(poIdI.intValue(),"AppCmd");
            }

            return;
        } else if (cmd.equals("shopping_cart_reminder")) {

            log("begin.");
            log("get api access factory.");
            long startCmd = System.currentTimeMillis();
            APIAccess factory = getAPIAccess(loadConfiguration());

            ShoppingServices servEjb = factory.getShoppingServicesAPI();
            User userEjb = factory.getUserAPI();
            EmailClient emailEjb = factory.getEmailClientAPI();

            log("get carts.");
            //get carts
            OrderGuideDataVector carts = servEjb.getCartsForReminderAction();
            log("cart size: " + carts.size());
            if (!carts.isEmpty()) {
                //get userIds
                IdVector userIds = new IdVector();
                Iterator it = carts.iterator();
                while (it.hasNext()) {
                    OrderGuideData cart = (OrderGuideData) it.next();
                    userIds.add(new Integer(cart.getUserId()));
                }
                //get contacts
                UserInfoDataVector userInfoCollection = userEjb.getUserInfoCollection(userIds);
                log("contacts size:" + userInfoCollection.size());
                //send
                if (!userInfoCollection.isEmpty()) {
                    Iterator userInfoIt = userInfoCollection.iterator();
                    while (userInfoIt.hasNext()) {
                        UserInfoData userInfo = (UserInfoData) userInfoIt.next();
                        Iterator cartsIt = carts.iterator();
                        while (cartsIt.hasNext()) {
                            OrderGuideData cart = (OrderGuideData) cartsIt.next();
                            //if this user cart
                            if (cart.getUserId() == userInfo.getUserData().getUserId()) {
                                log("send ");
                                try {
                                    String subject = "Old Shopping Cart(" + cart.getShortDesc() + " " + cart.getModDate() + ")";
                                    String message = "Old shopping cart reminder. Last update was done on: " + cart.getModDate();
                                    String email = userInfo.getEmailData().getEmailAddress();
                                    log("email: " + email);
                                    log("subject: " + subject);
                                    log("message: " + message);
                                    if (!emailEjb.wasThisEmailSent(subject,email)) {
                                        emailEjb.send(email,
                                                emailEjb.getDefaultEmailAddress(),
                                                subject, message, null, 0, 0);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally{
                                   cartsIt.remove();
                                }
                            }
                        }
                    }
                }
            }
            log("process time: " + (System.currentTimeMillis() - startCmd));
        }else if (cmd.equals("create_item_content")) {
        	String host = "imageloader";
            Date startDate = new Date();
            log.info("Creating item content initiated: " + startDate );
        	String catalogIdStr = System.getProperty("catalogId");
        	APIAccess factory = getAPIAccess();

            CatalogInformation catalogInformationBean = factory.getCatalogInformationAPI();
            ItemInformation itemInformationBean = factory.getItemInformationAPI();
            Content contentBean = factory.getContentAPI();
            int catalogId;
        	if(!Utility.isSet(catalogIdStr)){
        		catalogId = catalogInformationBean.getSystemCatalogId();
        		log.info("catalogId param not set defaulting to system catalog (id "+catalogId+")");
        	}else{
        		catalogId = (new Integer(catalogIdStr)).intValue();
        	}

        	CatalogStructureDataVector catalogStructureDataVector = catalogInformationBean.getCatalogStructuresCollection(catalogId);
            for(Iterator iter=catalogStructureDataVector.iterator(); iter.hasNext();){
            	CatalogStructureData catSD = (CatalogStructureData) iter.next();
                int itemId = catSD.getItemId();
                ItemMetaDataVector iMDataVector = itemInformationBean.getItemMetaValuesCollection(itemId);

                for(Iterator iter2=iMDataVector.iterator(); iter2.hasNext();) {
                	ItemMetaData itemMD = (ItemMetaData) iter2.next();
                    String nValue = itemMD.getNameValue();
                    String imagePath = "";
                    if (ProductData.isContentNameType(nValue)) {
                    	imagePath = itemMD.getValue();
                    	if (imagePath!=null && imagePath.length()>0) {
                    		try {
								contentBean.saveImage(host, imagePath, "ItemImage");
								log.info("Uploaded: "+imagePath);
							} catch (RemoteException e) {
								Throwable nestedException = e.getCause();
								if (nestedException instanceof FileNotFoundException) {
									log.info("AppCmd item image upload error: " + nestedException.getMessage());
								}
							}
                    	}
                    }
                }
            }
            Date endDate = new Date();
            log.info("Creating item content done:      " + endDate );
        }else if (cmd.equals("create_item_content2")) {
        	String host = "imageloader";
        	Content contentBean = getAPIAccess().getContentAPI();
        	//get all content
        	ContentDataVector cdv = contentBean.getContentToSynchronize("dummy","sysdate-1000");
        	Iterator it = cdv.iterator();
        	while(it.hasNext()){
        		ContentData cd = (ContentData) it.next();
        		String imagePath = cd.getPath();
            	if (imagePath!=null && imagePath.length()>0) {
            		try {
						contentBean.saveImage(host, imagePath, "ItemImage");
						log.info("Uploaded: "+imagePath);
					} catch (RemoteException e) {
						Throwable nestedException = e.getCause();
						if (nestedException instanceof FileNotFoundException) {
							log.info("AppCmd item image upload error: " + nestedException.getMessage());
						}
					}
            	}
        	}

        }else if (cmd.equals("upload_logos")) {
        	String host = "imageloader";
            Date startDate = new Date();
            log.info("Uploading logos initiated: " + startDate );
        	APIAccess factory = getAPIAccess();
            InitialContext jndiContext = new InitialContext(props);
            Object propertyServiceRef  = jndiContext.lookup(JNDINames.PROPERTY_SERVICE_EJBHOME);
            PropertyServiceHome propertyServiceHome = (PropertyServiceHome)
            	PortableRemoteObject.narrow (propertyServiceRef, PropertyServiceHome.class);
            PropertyService propertyServiceEjb = propertyServiceHome.create();

            Content contentBean = factory.getContentAPI();

        	PropertyDataVector propertyDataVector1 = propertyServiceEjb.getProperties(null, null, RefCodeNames.PROPERTY_TYPE_CD.UI_LOGO1);
        	PropertyDataVector propertyDataVector2 = propertyServiceEjb.getProperties(null, null, RefCodeNames.PROPERTY_TYPE_CD.UI_LOGO2);
        	Iterator logo1Iterator = propertyDataVector1.iterator();
        	while(logo1Iterator.hasNext()){
        		PropertyData propertyData = (PropertyData) logo1Iterator.next();
        		String imagePath = propertyData.getValue();
            	if (imagePath!=null && imagePath.length()>0) {
            		try {
						contentBean.saveImage(host, imagePath, "LogoImage");
						log.info("Uploaded: "+imagePath);
					} catch (RemoteException e) {
						Throwable nestedException = e.getCause();
						if (nestedException instanceof FileNotFoundException) {
							log.info("AppCmd logo image upload error: " + nestedException.getMessage());
						}
					}
            	}
        	}
        	Iterator logo2Iterator = propertyDataVector2.iterator();
        	while(logo2Iterator.hasNext()){
        		PropertyData propertyData = (PropertyData) logo2Iterator.next();
        		String imagePath = propertyData.getValue();
            	if (imagePath!=null && imagePath.length()>0) {
            		try {
						contentBean.saveImage(host, imagePath, "TipsImage");
						log.info("Uploaded: "+imagePath);
					} catch (RemoteException e) {
						Throwable nestedException = e.getCause();
						if (nestedException instanceof FileNotFoundException) {
							log.info("AppCmd tips image upload error: " + nestedException.getMessage());
						}
					}
            	}
        	}
        }else if (cmd.equals("verify_inbound_orders")) {
        	String dir = System.getProperty("dir");
        	if(!Utility.isSet(dir)){
        		log.info("dir property must be specified (-Ddir=<dir to scan>)");
        		return;
        	}

        	Connection con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("SELECT count(*) FROM clw_pre_order o, clw_pre_order_property p WHERE p.short_desc = 'CUSTOMER_PO_NUM' AND p.clw_value = ? AND p.pre_order_id = o.pre_order_id");
        	PreparedStatement pstmt2 = con.prepareStatement("SELECT count(*) FROM clw_pre_order o WHERE customer_po_number = ?");

        	File f = new File(dir);
        	File[] files = f.listFiles();
        	for(int i=0;i<files.length;i++){
        		File aFile = files[i];
        		if(!aFile.isDirectory()){
        			try{
        				String content = IOUtilities.loadFile(aFile);
        				content = content.trim();
        				if(content.startsWith("ISA")){
        					//EDI file
        					char edidelimiter = content.charAt(3);
        					//figure out if this is an 850:
        					//ST*850* where * = edidelimiter
        					String poStart = "ST"+edidelimiter+"850"+edidelimiter;
        					//then parse out the po text
        					//BEG*00*SA*<SOME PO NUMBER>*  where * = edidelimiter
        					String poDelimiter = "BEG"+edidelimiter+"00"+edidelimiter+"SA"+edidelimiter;

        					int filePos = content.indexOf(poStart);
        					if(filePos >= 0){
        						log.info("Analyizing file: "+aFile.getAbsolutePath());
        					}
        					while(filePos >= 0){

        						int start = content.indexOf(poDelimiter,filePos);
        						int end = content.indexOf(edidelimiter,start+poDelimiter.length()+1);
        						String po = content.substring(start+poDelimiter.length(),end);
        						pstmt.setString(1, po);
        						ResultSet rs = pstmt.executeQuery();
        						rs.next();
        						int ct = rs.getInt(1);
        						rs.close();

        						if(ct == 0){
        							pstmt2.setString(1, po);
            						rs = pstmt2.executeQuery();
            						rs.next();
            						ct = rs.getInt(1);
            						rs.close();
        						}
        						if(ct == 0){
        							log.info("Error missing po: "+po);
        						}else if(ct > 1){
        							log.info("Error multiples found for po: "+po);
        						}else{
        							log.info("Found po: "+po);
        						}
        						filePos = content.indexOf(poStart,filePos+poStart.length());
        					}
        				}
        			}catch(Exception e){
        				e.printStackTrace();
        				log.info("Error skipping file: "+aFile.getName()+" ("+e.getMessage()+")");
        			}
        		}
        	}
        	try{
				pstmt.close();
				pstmt2.close();
				con.close();
        	}catch(Exception e){}
        	return;
        } else if (cmd.equals("send_inventory_missing_email")) {
            log.info("Starting of performing the action: 'send_inventory_missing_email'");

            String reportName = System.getProperty("reportName");
            String userName = System.getProperty("userName");
            //String storeId = System.getProperty("storeId");
            String accountIds = System.getProperty("accountIds");
            IdVector accountIdV = null;
            if(Utility.isSet(accountIds)) {
            	accountIdV = Utility.parseIdStringToVector(accountIds, ",");
            } 
            String siteIds = System.getProperty("siteIds");
            IdVector siteIdV = null;
            if(Utility.isSet(siteIds)) {
            	siteIdV = Utility.parseIdStringToVector(siteIds, ",");
            } 
            ArrayList emailSentTo = new ArrayList();
            ArrayList corporateUsers = new ArrayList();
            EmailClient emailClientBean = null;
            Report reportBean = null;
            User userBean = null;
            Site siteBean = null;
            Account accountBean = null;

            log.info(" reportName: " + reportName);
            //log.info("   userName: " + userName);
            //log.info("    storeId: " + storeId);
            log.info("  accountIds: " + accountIds);
            log.info("  siteIds: " + siteIds);

            try {
                HashMap<String, String> params = new HashMap<String, String>();
                //params.put("STORE_ID", storeId);
                //params.put("ACCOUNT_ID", accountId);
                log.info("sch_by_rep => params: " + params);

                Date startDate = new Date();
                GregorianCalendar runCal = new GregorianCalendar();
                runCal.setTime(startDate);
                /*
                runCal.set(Calendar.HOUR_OF_DAY, 0);
                runCal.set(Calendar.MINUTE, 0);
                runCal.set(Calendar.SECOND, 0);
                runCal.set(Calendar.MILLISECOND, 0);
                */
                Date runDate = runCal.getTime();          
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date today = sdf.parse(sdf.format(startDate));
                
                SimpleDateFormat sdfTime = new SimpleDateFormat("MM/dd/yyyy:HH:mm:ss");

                InitialContext jndiContext = new InitialContext(props);

                Object emailClientRef = jndiContext.lookup(JNDINames.EMAIL_CLIENT_EJBHOME);
                EmailClientHome emailClientHome = (EmailClientHome) PortableRemoteObject.narrow(emailClientRef, EmailClientHome.class);
                emailClientBean = emailClientHome.create();

                Object userRef = jndiContext.lookup(JNDINames.USER_EJBHOME);
                UserHome userHome = (UserHome) PortableRemoteObject.narrow(userRef, UserHome.class);
                userBean = userHome.create();

                Object reportRef = jndiContext.lookup(JNDINames.REPORT_EJBHOME);
                ReportHome reportHome = (ReportHome) PortableRemoteObject.narrow(reportRef, ReportHome.class);
                reportBean = reportHome.create();

                Object siteRef = jndiContext.lookup(JNDINames.SITE_EJBHOME);
                SiteHome siteHome = (SiteHome) PortableRemoteObject.narrow(siteRef, SiteHome.class);
                siteBean = siteHome.create();
                
                Object accountRef = jndiContext.lookup(JNDINames.ACCOUNT_EJBHOME);
                AccountHome accountHome = (AccountHome) PortableRemoteObject.narrow(accountRef, AccountHome.class);
                accountBean = accountHome.create();
                
                Object propRef = jndiContext.lookup(JNDINames.PROPERTY_SERVICE_EJBHOME);
                PropertyServiceHome propsHome = (PropertyServiceHome) PortableRemoteObject.narrow(propRef, PropertyServiceHome.class);
                PropertyService propertyEjb = propsHome.create();

                UserData user = userBean.getUserByName(userName, 0);

                List<String> reportNameList = new ArrayList<String>();
                reportNameList.add(reportName);
                GenericReportViewVector reportList = 
                    reportBean.getReportList(user.getUserId(), user.getUserTypeCd(), reportNameList);

                if (reportList.size() > 1) {
                    String err = "Found multiple reports. User => " + user.getUserName() + ", Report Name => " + reportName;
                    throw new Exception(err);
                }
                if (reportList.isEmpty()) {
                    String err = "Report not found.User => " + user.getUserName() + ", Report Name => " + reportName;
                    throw new Exception(err);
                }

                GenericReportView report = (GenericReportView) reportList.get(0);
                IdVector archiveIds = reportBean.getArchiveReportIds(report.getGenericReportId(),
                    user.getUserId(), RefCodeNames.REPORT_RESULT_STATUS_CD.GENERATED);

                if (archiveIds.size() > 1) {
                    String err = "Found multiple archive reports." +
                            " GenericReportId: " + report.getGenericReportId() + " ," +
                            " UserId: " + user.getUserId() + " ," +
                            " Status: " + RefCodeNames.REPORT_RESULT_STATUS_CD.GENERATED;
                    throw new Exception(err);
                }

                boolean genArchReport = false;
                if (!archiveIds.isEmpty()) {
                	PreparedReportView prepRepRes = reportBean.getPreparedReport((Integer) archiveIds.get(0));
                	Date reportDate = prepRepRes.getReportDate();
                	reportDate = sdf.parse(sdf.format(reportDate));
                	if(!reportDate.equals(today)) {
                		genArchReport = true;
                	}
                	/*
                	ReportResultParamDataVector reportResParams = 
                        reportBean.getReportResultParams((Integer) archiveIds.get(0));
                    
                    for (String paramName : params.keySet()) {
                        boolean found = false;
                        for (Object oReportResParam : reportResParams) {
                            ReportResultParamData reportResultParam = (ReportResultParamData) oReportResParam;
                            if (paramName.equals(reportResultParam.getParamName())) {
                                String paramVale = params.get(paramName);
                                if (paramVale.equals(reportResultParam.getParamValue())) {
                                    found = true;
                                }
                            }
                        }
                        if (!found) {
                            log.info("Archive data is too old. ReportResultId: " + archiveIds.get(0) + ", Params: " + reportResParams);
                            genArchReport = true;
                            break;
                        }
                    }
                    */
                } else {
                    log.info("sch_by_rep => Archive report not found.");
                    genArchReport = true;
                }
                GenericReportResultViewVector result = new GenericReportResultViewVector();
                if (genArchReport) {
                    log.info("sch_by_rep => Attempt to generate the report.");
                    ReportResultData genResult = 
                        (ReportResultData) reportBean.checkAnalyticReport(report.getGenericReportId(), 
                        params, user.getUserId(), user.getUserName(), true);
                    log.info("sch_by_rep => Generated result: " + genResult);
                    log.info("sch_by_rep => Reading archive data ...");
                    result = reportBean.readArchiveReport(genResult.getReportResultId());
                } else if (!archiveIds.isEmpty()) {
                    log.info("sch_by_rep => Archive report is found. ReportResultId: " + archiveIds.get(0));
                    log.info("sch_by_rep => Reading archive data ...");
                    result = reportBean.readArchiveReport((Integer) archiveIds.get(0));
                }

                ///
                if (!result.isEmpty()) {
                    GenericReportResultView sheet = (GenericReportResultView) result.get(0);
                    int siteIdx = InventoryMissingEmailReport.getColumnIndex(sheet.getHeader(), InventoryMissingEmailReport.REPORT_HEADER.SITE_ID);
                    int accountIdx = InventoryMissingEmailReport.getColumnIndex(sheet.getHeader(), InventoryMissingEmailReport.REPORT_HEADER.ACCOUNT_ID);

                    for (Object oRow : sheet.getTable()) {
                        List row = (List) oRow;
                        int accountId = ((Integer) row.get(accountIdx)).intValue();
                        if(accountIdV!=null) {
                        	if(!accountIdV.contains(accountId)) {
                        		continue;
                        	}
                        }
                        int siteId = ((Integer) row.get(siteIdx)).intValue();
                        if(siteIdV!=null) {
                        	if(!siteIdV.contains(siteId)) {
                        		continue;
                        	}
                        }
                        log.info("Processing site id: " + siteId);

                        /// Sending of email
                        try {
                            siteBean.sendInventoryMissingEmail(siteId, runDate, startDate);
                        } catch (Exception ex) {
                            log.info("An error occurred at sending of email: " + ex.getMessage());
                        }    
               
                    
                    }
   
                    
                } else {
                    log.info("sch_by_rep => Archive data is empty.");
                }
                /* Get all sites for which property 'SEND_EMAIL_TO_CORPORATE' is set true
                 * 3 days ago
                 * Get all corporate users and match site ids with this list
                 * Send email to matching users
                 * -xpedx
                 */
                try{
                    emailSentTo = propertyEjb.getBusEntityVector(
                    		RefCodeNames.PROPERTY_TYPE_CD.SEND_EMAIL_TO_CORPORATE, "true",3);
                    log.info("emailSentTo "+emailSentTo.toString());
                    
                    corporateUsers = propertyEjb.getUsers(
                    		RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER,"true");
                    log.info("corporateUsers "+corporateUsers.toString());
                }catch(Exception e){
                	log.info("send_inventory_missing_emails:: Property not found:: "+e.getMessage());
                }

                if (emailSentTo != null && emailSentTo.size() > 0 && corporateUsers != null) {
                    	
                	for(int c=0; c < corporateUsers.size(); c++){
                		IdVector userSites = new IdVector();
                		int u =  ((Integer)corporateUsers.get(c)).intValue();
	                    
                		try{
                			//if receive inventory missing email flag is not set do not send the email
                			String receiveInvMissingEmails = propertyEjb.getUserProperty(
                					u, RefCodeNames.PROPERTY_TYPE_CD.RECEIVE_INV_MISSING_EMAIL);
                			if(receiveInvMissingEmails.equalsIgnoreCase("false")){
                				continue;
                			}
                		}catch(Exception e){
                        	log.info("send_inventory_missing_emails:: Property not found:: "+e.getMessage());
                        }
                		
	                    	
                		for(int ii=0; ii<emailSentTo.size(); ii++){
                			int site = ((Integer)emailSentTo.get(ii)).intValue();
                			if(userBean.isSiteOfUser(site, u)){
                				userSites.add(new Integer(site));
                			}
                		}
                		//send email
                		if(userSites != null && userSites.size() > 0){
	                    		
                			int site = ((Integer)userSites.get(0)).intValue();
                			AccountData acctD = accountBean.getAccountForSite(site);
                			int acctId = acctD.getAccountId();
	                    		
                			String lSubject = "Corporate Notification - Stores with incomplete on-hand "+
                			"quantities for Forecasted Items";

                			String lMsg = "Following stores have not completed their on-hand quantities"+
                			" for this forecasted items period."+"\n"+"\n";
	                    		
                			try{
                			SiteData sitedata = siteBean.getSite(((Integer)userSites.get(0)).intValue());
                			
                			boolean sendCorpEmail = siteBean.getSendCorporateEmail(site,acctId,runDate, startDate);
                			
                			if(sendCorpEmail){
                				Date cutOffD = sitedata.getNextOrdercutoffDate();
                				if(cutOffD != null){
                					String fD = sdf.format(cutOffD);
                					log.info("CutOff Date : "+fD);
				
                					lSubject = lSubject + " Cut-off: "+fD;
                				}else{
                					String fD = sdf.format(new Date());
                					lSubject = lSubject + " Today: "+fD;
                				}
                				log.info("lSubject "+lSubject);
	                            
                				for(int j=0; j<userSites.size(); j++){
                					SiteData siteD = siteBean.getSite(((Integer)userSites.get(j)).intValue());
                					lMsg = lMsg + siteD.getBusEntity().getShortDesc()+"\n";
                				}

                				UserInfoData uinfo = userBean.getUserContactForNotification(u);
                				String lToEmailAddress = uinfo.getEmailData().getEmailAddress();
                				if (!RefCodeNames.USER_STATUS_CD.ACTIVE.equals(uinfo.getUserData().getUserStatusCd())) {
                					continue;
                				}

                				if (emailClientBean.wasThisEmailSent(lSubject, lToEmailAddress) == false) {

                					emailClientBean.send(lToEmailAddress,
                							emailClientBean.getDefaultFromAddress(acctId),
                							lSubject, lMsg,
                							Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, u);

                					for(int s=0; s< userSites.size(); s++){
                						int sId = ((Integer)userSites.get(s)).intValue();
                						propertyEjb.setBusEntityProperty(sId,
                								RefCodeNames.PROPERTY_TYPE_CD.SEND_EMAIL_TO_CORPORATE,
                						"false");
                					}
                				}
                			}
                			}catch(Exception exc){
                				log.info("send_inventory_missing_email=>not sending email because " +
                						"error occured ==>> "+exc.getMessage());
                			}
                		}
                	}


                }
                
                    
                 
            } catch (Exception ex) {
                log.info("An error occurred in the 'send_inventory_missing_email': " + ex.getMessage());
                ex.printStackTrace();
                if (emailClientBean != null) {
                    String defaultEmailAddress = emailClientBean.getDefaultEmailAddress();
                    emailClientBean.send(defaultEmailAddress,
                            defaultEmailAddress,
                            ex.getMessage(),
                            "send_inventory_missing_email problems",
                            null, 
                            0, 
                            0);
                }
            }
            log.info("Finish of performing the action: 'send_inventory_missing_email'");
            return;
        }

        log.info( usage );
    }

    private void processPipelineException
        (Order pOrderEjb, OrderData pOrder, String pErrorMess, String pPipelineType)
    {
       processPipelineException(pOrderEjb, pOrder, pErrorMess, pPipelineType,
	     RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
 	}

    private void processPipelineException(Order pOrderEjb, OrderData pOrder, String pErrorMess, String pPipelineType, String pErrorStatus) {

        log.info(pErrorMess);

        if (pErrorMess.length() > 2000) pErrorMess = pErrorMess.substring(0, 2000);
        if (pErrorStatus != null) {
            pOrder.setOrderStatusCd(pErrorStatus);
        }

        pOrder.setExceptionInd("Y");
        String modBy = "AppCmd. " + pPipelineType;
        if (modBy.length() > 30) modBy = modBy.substring(0, 30);
        pOrder.setModBy(modBy);
        OrderPropertyData opD = OrderPropertyData.createValue();
        opD.setOrderId(pOrder.getOrderId());
        opD.setShortDesc("Pipeline Exception");
        opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        opD.setValue(pErrorMess);
        opD.setModBy(modBy);
        opD.setAddBy(modBy);

        OrderData errOrder = null;
        OrderPropertyDataVector errNotes = new OrderPropertyDataVector();
        try {
            errOrder = pOrderEjb.updateOrder(pOrder);
            errNotes.add(pOrderEjb.addNote(opD));
        } catch (Exception exc1) {
            exc1.printStackTrace();
        }

        if (errOrder != null) {
            createOrderEmailNotifications(errOrder, errNotes, pPipelineType);
        }

    }

    private void createOrderEmailNotifications(OrderData pOrder, OrderPropertyDataVector pNotes, String pPipelineType) {

        log.info("createOrderEmailNotifications()=> BEGIN" +
                ",\n       OrderID: " + pOrder.getOrderId() +
                ",\n OrderStatusCD: " + pOrder.getOrderStatusCd() +
                ",\n   pNotes.Size: " + pNotes.size() +
                ",\n pPipelineType: " + pNotes.size());

        Connection conn = null;
        try {

            APIAccess factory = getAPIAccess();
            conn = getConnection();

            Order orderEjb = factory.getOrderAPI();

            int orderId = pOrder.getOrderId();

            PendingOrderNotification notif = new PendingOrderNotification();
            if (notif.couldBeSent(pOrder.getOrderNum(), pOrder.getOrderStatusCd(), false)) {
                EventEmailView eventEmail = notif.createNotification(
                        conn,
                        factory,
                        pOrder.getOrderStatusCd(),
                        pOrder,
                        orderEjb.getOrderAddress(orderId, RefCodeNames.ADDRESS_TYPE_CD.SHIPPING),
                        pNotes,
                        true,
                        "AppCmd. " + pPipelineType);

                boolean b = eventEmail != null
                        && eventEmail.getEventData() != null
                        && eventEmail.getEmailProperty() != null
                        && eventEmail.getEventData().getEventId() > 0
                        && eventEmail.getEmailProperty().getEventEmailId() > 0;

                if (b) {
                    log.info("createOrderEmailNotifications()=> !V!" +
                            ", EventID:  " + eventEmail.getEventData().getEventId() +
                            ", EventEmailID:  " + eventEmail.getEmailProperty().getEventEmailId());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        log.info("createOrderEmailNotifications()=> END.");

    }

    private void log(String message){
        Date date = new Date();
        log.info(date + "  " + currentTask + "=>"+ message);
    }


}

/*
 * GenericReportRunner.java
 *
 * Created on March 4, 2003, 9:24 AM
 */

package com.cleanwise.service.api.process.operations;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cleanwise.service.api.APIAccess;
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
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.IgnoredException;
import com.cleanwise.service.api.util.JNDINames;
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
import com.cleanwise.service.apps.ApplicationsEmailTool;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.apps.*;
import com.cleanwise.service.apps.AppCmd.SiteReqUtil;
import com.cleanwise.service.api.dao.CatalogDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.pipeline.PendingOrderNotification;
import com.cleanwise.service.api.reporting.InventoryMissingEmailReport;
import com.cleanwise.service.api.reporting.InventoryScheduleReport;
import com.cleanwise.service.api.reporting.ReportWriterUtil;
import javax.transaction.TransactionManager;
import org.apache.log4j.Logger;

/**
 * Process designed to produce a report that can then
 * be transmitted to a user.  This program is desinged to be the scheduled interface for the
 * reporting framework.
 */
public class AppCmdOperations  extends ApplicationServicesAPI {
    private static final Logger log = Logger.getLogger(AppCmdOperations.class);

    private static Map<String, String> semaphore = new HashMap<String, String>();
    private static Map<String, Map> lockedCommandParams = new HashMap<String, Map>();
    public static interface STATE {
        public static final String LOCKED   = "Locked";
        public static final String UNLOCKED = "Unlocked";
    }
    
    public interface FTP_PARAM {
        public static final String FILE_TRANSFER_HOST = "fileTransferHost";
        public static final String FILE_TRANSFER_USER = "fileTransferUser";
        public static final String FILE_TRANSFER_PASS = "fileTransferPass";
        public static final String FILE_TRANSFER_MODE = "fileTransferMode";
        public static final String FILE_TRANSFER_PORT = "fileTransferPort";
        public static final String FILE_TRANSFER_OVERWRITE_FILE  = "fileTransferOverwriteFile";
        public static final String SEND_FILE_NAME = "sendFileName";
        public static final String FILE_FORMAT = "fileFormat";
    }
    public interface COMMON_PARAM {
        public static final String TR_PROTOCOL = "mail.transport.protocol";
        public static final String SMTP_HOST = "mail.smtp.host";
    }
    public interface EMAIL_PARAM {
        public static final String RECIPIENT = "emailrecipient";
        public static final String MESSAGE = "email-message";
        public static final String SEND_ONLY_IF_DATA = "send-only-if-data";
        public static final String IMPORTANCE_HIGH = "importanceHigh";
        public static final String SUBJECT_DATE="SUBJECT_DATE";
        public static final String FROM_ADDR="FROM_ADDR";
    }
    
    private Date now = new Date();
    private String currentTask;   
    public int idx;

     /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public AppCmdOperations() {
    }

    
    private boolean parseBooleanValue(String pValue, String pConfigSetting){
        boolean returnVal = true;
        if (Utility.isSet(pValue)){
	        if(pValue.equalsIgnoreCase("true")){
	            returnVal = true;
	        }else if(pValue.equalsIgnoreCase("false")){
	            returnVal = false;
	        }else{
	            log.info("Warning boolean values are interpreted as 'true' if they do not equal "+
	            "'false', proceeding with value set to 'true' for configuration setting: "+
	            pConfigSetting+"!");
	            returnVal = true;
	        }
        }
        return returnVal;
    }
    private int parseIntValue(String pValueS, String pKey) throws Exception{
        int val = 0;
        if (Utility.isSet(pValueS)) {
	    	try{
	            val = Integer.parseInt(pValueS);
	        }catch(Exception e){
	            throw new Exception(pKey + " passed in was not a number ("+pValueS+")");
	        }
        }
    	return val;
    }
    	

/*    //adds days, subtracts days etc from the specified date
    //we can thus take paramaters like:
    //@TODAY@ - 10
    //meaning 10 days ago
    private Date doDateMods(String pModification, Date pDate){
        pModification = pModification.trim();
        if(pModification.length() == 0){
            return pDate;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(pDate);
        if(pModification.startsWith("-")){
            Integer lNumDays = new Integer(pModification.substring(1));
            cal.add(Calendar.DAY_OF_YEAR, lNumDays.intValue() * -1);
        }else if(pModification.startsWith("+")){
            Integer lNumDays = new Integer(pModification.substring(1));
            cal.add(Calendar.DAY_OF_YEAR, lNumDays.intValue());
        }else{
            throw new RuntimeException("Unknown date modification action: "+pModification);
        }
        return cal.getTime();
    }
*/    
/*    //processes the string for filter data
    private String processParam(String pParam){
        if(pParam.startsWith("@TODAY@")){
            DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date theDate = new Date();
            theDate = doDateMods (pParam.substring(7),theDate);
            return sdf.format(theDate);
        }else if(pParam.startsWith("@LAST_WEEKDAY@")){
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            switch (cal.get(Calendar.DAY_OF_WEEK)){
                case Calendar.SUNDAY:
                    cal.add(Calendar.DATE, -2);
                    break;
                case Calendar.MONDAY:
                    cal.add(Calendar.DATE, -3);
                    break;
                default:
                    cal.add(Calendar.DATE, -1);
                    break;
            }
            Date theDate = cal.getTime();
            theDate = doDateMods (pParam.substring(14),theDate);
            return sdf.format(theDate);
        }else{
            return pParam;
        }
    }
*/    
    
    /**
     *If the email address is set then send an email message
     */
/*    private void sendEmail(ReportCriteria report,File file, boolean sendAttachment) throws Exception{
        if(report.getEmailRecipients() == null || report.getEmailRecipients().size() == 0){
            return;
        }
        Map params = report.getParameters();
        String to = emailListToString(report.getEmailRecipients());
        String subject=null;
        if(params.containsKey(EMAIL_PARAM.SUBJECT_DATE)){
        	subject = report.getName() +" "+ (String)params.get(EMAIL_PARAM.SUBJECT_DATE);
        }else{
        	subject = report.getName() +" "+ now.toString();
        }
        String message;
        String fromAddr=null;
        
        if(params.containsKey(EMAIL_PARAM.FROM_ADDR)){
        	fromAddr = (String)params.get(EMAIL_PARAM.FROM_ADDR);
        }
        File[] rep = {file};
        if(sendAttachment){
          message = report.getMessage();
        }else{
          rep = null;
          //"no data found" message is from xpedx...might need to parameterize it later.
          message = report.getMessage() + "\nno data found";
        }
        if(fromAddr!=null){
        	if(report.isImportanceHigh()){
        		ApplicationsEmailTool.sendEmail(to,null,subject,message,fromAddr,
        				Constants.EMAIL_IMPORTANCE_HIGH,rep);
        	}else{
        		ApplicationsEmailTool.sendEmail(to,null,subject,message,fromAddr,
        				Constants.EMAIL_IMPORTANCE_NORMAL,rep);
        	}
        }else{
        	if(report.isImportanceHigh()){
        		ApplicationsEmailTool.sendEmail(to, subject, message, rep,
        				Constants.EMAIL_IMPORTANCE_HIGH);
        	}else{
        		ApplicationsEmailTool.sendEmail(to, subject, message, rep,
        				Constants.EMAIL_IMPORTANCE_NORMAL);
        	}
        }
    }
    
    private String emailListToString(List emails){
        StringBuffer retVal = new StringBuffer();
        for(int i=0;i<emails.size();i++){
            retVal.append((String) emails.get(i));
            if(i!=emails.size()-1){
                retVal.append(",");
            }
        }
        return retVal.toString();
    }
*/    
    public void execute (String command, Map parameters)  throws Exception {
    	String show = "\n****************************************************************************\n";
        show += "AppCmdOperations : " + " exec at " + new Date() + " \n";
        show += "command: " + command + " \n";
        show += "parameters: \n";
        if (parameters == null )
        {
            show += "****************************************************************************\n";
        	log.info(show);
            throw new Exception("Problems to run process : parameters cannot be null");
        }

        TreeMap<String, String> sortedParams = new TreeMap<String, String>();
    	sortedParams.putAll(parameters);
    	Set keys = sortedParams.keySet();
        for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
          String key = (String) iter.next();
          String param= (String)sortedParams.get(key);
          show += key + " is " + param + "; \n";
        }
        show += "****************************************************************************\n";
        log.info(show);
       
        Properties props;
        java.util.Date now = new java.util.Date();
        
         ExecutorService service = Executors.newFixedThreadPool(1);  	
        try{  
         	initLock(command, sortedParams.toString());
        	//initLock(command, noWaitSemafore, service);
            doCmd(command, sortedParams, service);
        }catch(IgnoredException e){
            log.info( e.getMessage());
            throw e;
            //return;
        }catch(Exception e){
            log.info("Problems to run command: "+ command+". " + Utility.getUiErrorMess(e.getMessage()));
            if (e instanceof RemoteException ){
               throw new RemoteException(e.getMessage());
             } else {
               throw new Exception(e.getMessage());
             }

           // log.info("Problems to run command: " + e.getMessage());
           // e.printStackTrace();
           // throw new Exception("Problems to run command :" + e.getMessage());
           // //return;
        } finally {
        	releaseLock(command, sortedParams.toString());
        	//releaseLock(command, service);
        }
    }
    
    public void doCmd(String command, Map params, ExecutorService service) throws Exception {

        String usage = "Usage: AppCmd -Dconf=app.properties -Dcmd=CMD\n" +
                "Where CMD can be: " +
/* + */                "\n process_workflow "+
                //"\n status "+
/* + */                "\n process_auto_orders [-DaccountIds] "+
                //"\n process_auto_orders [-DsiteIds] "+
                //"\n process_removed_og_items " +
                //"\n process_inventory_orders -Dsiteid=optional_id" +
                //"\n process_scheduled_orders -DsiteIds=optional_ids -DbegDate=optional <yyyyMMddHHmm> -DendDate=optional <yyyyMMddHHmm> to run for a specific date"+
                //"\n process_schedule -DbegDate=optional <yyyyMMddHHmm> -DendDate=optional <yyyyMMddHHmm> to run for a specific date"+
                //"\n process_schedule_by_report -DsiteIds=optional_ids -DbegDate=optional <yyyyMMddHHmm> -DendDate=optional <yyyyMMddHHmm> to run for a specific date " +
                //"-DreportName=required if siteIds is not set -DinvModernShopping=optional<true|false> inventory shopping type"+
                //"\n send_inventory_missing_email -DreportName=<Report Name> -DuserName=<userName> [-DaccountIds=accountId1,accountId2,...] [-DsiteIds=siteId1,siteId2,...] "+ 
 /* + */               "\n send_order_emails" +
                //"\n shopping_cart_reminder"+
                //"\n release_lawson_pos"+
                //"\n to_lawson_pipeline Sends orders to lawson" +
/* + */                "\n orders_to_lawson process orders (does not actually send them to lawson)" +
                //"\n orders_from_lawson | invoices_from_lawson" +
                //"\n order_through_pipeline -Dorder_id=clw_order.order_id" +
                //"\n ssp (set site property) | ssw (set site property)" +
                //"\n checksite | cpsite " +
                //"\ncopy_order -Dorder_id=<source order id>" +
/* + */                "\n remits_to_lawson | invoices_to_lawson" +
                //"\n self_store_dist_invoices "
                //+ "\n add_content -Dhost=hostname -Dcontent=path "
/* + */                 "\n synch_content -DrefetchAllContent=<optional, defaults to false true|false if true will refresh all content regardless of date updated> "
                //+ "\n item_info_to_lawson -Dsku=optional_sku_num"
/* + */                + "\n daily_order_reprocess -DDate=optional <MMddyyyy> to run for a specific date -DorderNum=optional order to reprocess -DmaxRows=optional maximum rows to process (default=500,-1 for unlimited rows)"
                //+ "\n daily_multi_order_reprocess -DbegDate=optional <MMddyyyy> -DendDate=optional <MMddyyyy> to run for a specific date -DorderNum=optional order to reprocess " +
                //"-DdbOperation=optional <Y or N> starts operation of a database -DlogFileName=optional -DlogLevel=optional {<1>-Exception,<2>-Report,<3>-Message,<4>-Debug }"

                //+ "\n cancel_po_item -Derp_order_num=num -Dsku_num=cw.skuNum"
                //+ "\n invoice_po_item -Derp_order_num=num -Dsku_num=cw.skuNum"
/* + */                + "\n reset_cost_centers resets the cost centers for the system"
/* + */                + "\n create_fiscal_cal_cache_table (creates the fiscal calendar caching table to be used for reporting)"
                //+ "\n set_outbound_po_num -DbegDate=<MM/dd/yyyy> -DendDate=<MM/dd/yyyy>"
                //+ "\n create_item_content -DcatalogId=id"
                //+ "\n upload_logos"
                ;

        //String cmd = (String)params.get("command");
        String cmd = command;
        if ( null == cmd )
        {
            log.info( usage );
            return;
        }

        currentTask = cmd.trim().toUpperCase();

        // Check for a properties file command option.
/*        String propFileName = System.getProperty("conf");
        if ( null == propFileName )
        {
            log.info( usage );
            return;
        }
        Properties props = new Properties();
        props.load(new FileInputStream (propFileName) );
*/        //------------------------------------------------//
        if ( cmd.equals("process_auto_orders") )  {
        	fProcessAutoOrders(params, service);
        } else if ( cmd.equals("process_workflow") ) {
        	fProcessWorkflow(params, service);  //Done
        } else if ( cmd.equals("send_order_emails") ) {
        	fSendOrderEmails(params, service);
        } else if ( cmd.equals("orders_to_lawson" ) ) {
        	fOrdersToLawson(params, service);   //DONE
        } else if ( cmd.equals("invoices_to_lawson" ) ) {
        	fInvoicesToLawsonCallable(params, service); //DONE (without callable)
        } else if(cmd.equals("synch_content")) {
        	fSynchContent(params, service);    //TODO -3
        } else if(cmd.equals("daily_order_reprocess")) {
        	fDailyOrderReprocess(params, service);
        } else if(cmd.equals("reset_cost_centers_old")) {
        	fResetCostCentersOld(params, service);
        } else if(cmd.equals("reset_cost_centers")) {
        	fResetCostCenters(params, service);
        } else if(cmd.equals("create_fiscal_cal_cache_table")) {
        	fCreateFiscalCalCasheTable(params, service);
        }	

        service.shutdown();
        log.info( usage );
    }
    
    private String getFutureResult(Future<String> future, String message ){
    	String result = null;
    	if (future != null) {
	    	try {
	    		 result = future.get();
	             //log("Processed =>"+ message + ((result!= null) ? result : ""));
	             log(" result => Processed :"+ message +  " " +((result!= null) ? result : ""));
	    	} catch (Exception ex) {
                //log("ERROR in Processing =>"+message +". Exception: "+ ex.getMessage());
                log(" result => Failed :"+ message +". Exception: "+ ex.getMessage());
                result = "ERROR";
                ex.printStackTrace();
 	    	}
	    }
    	return result;
    }
    
    private int processOrdersPipeline(ExecutorService service, int[] storeIds, HashMap erpStatusHM,  OrderDataVector orders, String pipelineCd, boolean processPipelineExceptionFl ) throws Exception {
        int i= 0;
        int errCount = 0;
        for(Iterator iter = orders.iterator(); iter.hasNext();){
            i++;	
            OrderData oD = (OrderData) iter.next();
            String processingMess = i + " of " + orders.size();
            //log("Processing RECEIVED:: " + i + " of " + orders.size()+ " Order id: "+oD.getOrderId() + " Status: "+oD.getOrderStatusCd());
            int storeId = oD.getStoreId();
            if(storeId>0 && !Utility.isInArray(storeId, storeIds)) {
                log("Pipeline result => Skipped "+ processingMess + ":: Order id: "+ oD.getOrderId()+ " Reason: Store id: "+ storeId);
                continue;
            }
            if (pipelineCd == null){
                if(!RefCodeNames.WORKFLOW_IND_CD.TO_RESUME.equals(oD.getWorkflowInd())) {
                    log("Pipeline result => Skipped "+ processingMess + ":: Order id: "+ oD.getOrderId()+ " Reason: Workflow Ind: "+ oD.getWorkflowInd());
                    continue;
                }
            } else {
                if(RefCodeNames.WORKFLOW_IND_CD.INTERRUPTED.equals(oD.getWorkflowInd())||
                   RefCodeNames.WORKFLOW_IND_CD.TO_RESUME.equals(oD.getWorkflowInd())) {
                   log("Pipeline result => Skipped "+ processingMess + ":: Order id: "+ oD.getOrderId()+ " Reason: Workflow Ind: "+ oD.getWorkflowInd());
                   continue;
                }
            }
            
            if (!RefCodeNames.ORDER_STATUS_CD.RECEIVED.equals(oD.getOrderStatusCd())){
              String erpSystem = oD.getErpSystemCd();
              if(erpSystem==null) {
                log("Pipeline result => Skipped "+ processingMess + ":: Order id: "+ oD.getOrderId() + " Reason: Order doesn't have erp_system");
                continue;
              }
              String erpStatus = (String) erpStatusHM.get(erpSystem);
              if(!"ACTIVE".equals(erpStatus)) {
                log("Pipeline result => Skipped "+ processingMess + ":: Order id: "+ oD.getOrderId()+ " Reason:  Erp System "+erpSystem+" has '"+ erpStatus+"' status");
                continue;
              }
            }

     	   Callable<String> callable = new CallableWrapper.CallableProcessPipline(oD, pipelineCd);					
			   Future<String> future = service.submit(callable);
			   errCount += processFutureException(service, future, oD, RefCodeNames.PIPELINE_CD.ASYNCH, processPipelineExceptionFl,processingMess );
         }
         return errCount;

    }
    
    private int processFutureException(ExecutorService service, Future<String> future, OrderData order , String cd, boolean processPipelineExceptionFl, String processingMess){ 
		int errorCount = 0;
		
			try {		
				String res = future.get();
                log("Pipeline result => Processed "+ processingMess + ":: Order id: "+ order.getOrderId() +" Status: "+ order.getOrderStatusCd());

			} catch (Exception e) {
				errorCount++;
				String errMess = e.getMessage();
				if (processPipelineExceptionFl){
					Callable callable = new CallableWrapper.CallableProcessException(order, errMess, cd);
					Future<OrderPropertyData> errNoteFuture = service.submit(callable);
					try {
					  OrderPropertyData errNote  = errNoteFuture.get();
					  if (errNote != null) {
						OrderData errOrder = APIAccess.getAPIAccess().getOrderAPI().getOrder(errNote.getOrderId()) ; 
				        OrderPropertyDataVector errNotes = new OrderPropertyDataVector();
				        errNotes.add(errNote);
						createOrderEmailNotifications(errOrder, errNotes, cd);
					  }
					  
					} catch (Exception ex) {
						errMess += "; " + ex.getMessage();
					}
				} 
			    log("Pipeline result => Failed "+ processingMess + ":: Order id:" + order.getOrderId()+" Status: "+ order.getOrderStatusCd() + " - "	+ errMess +  "\n");
			}
			
        return errorCount;
    }

    
    private int processFutureExceptions(ExecutorService service, Set<Future<String>> errSet, List<OrderData> orders , String cd, boolean processPipelineExceptionFl){ 
		int errorCount = 0;
		if (orders == null || orders.isEmpty()) {
			return errorCount;
		}
		
    	int i = 0;
		PairViewVector failedMessages = new PairViewVector();
		for (Future<String> future : errSet) {
			OrderData order = (OrderData)orders.get(i);
			i++;
			String processingMess = i+ " of " + orders.size();
			errorCount += processFutureException(service, future, order, cd, processPipelineExceptionFl, processingMess);
	
		}
		
        log.info("result: processed "+ (orders.size()-errorCount) + " of " + orders.size() + ".Error count: " + errorCount);
		//logInfo("result: processed "+ (orders.size()-errorCount) + " of " + orders.size() + ".Error count: " + errorCount);			
        return errorCount;
    }
    
    private void processPipelineException
        (Order pOrderEjb, OrderData pOrder, String pErrorMess, String pPipelineType)  throws Exception
    {
       processPipelineException(pOrderEjb, pOrder, pErrorMess, pPipelineType,
	     RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
 	}

    private void processPipelineException(Order pOrderEjb, OrderData pOrder, String pErrorMess, String pPipelineType, String pErrorStatus)  throws Exception{

        log.info(pErrorMess);
        Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();
        if (pErrorMess.length() > 2000) pErrorMess = pErrorMess.substring(0, 2000);
        if (pErrorStatus != null) {
            pOrder.setOrderStatusCd(pErrorStatus);
        }

        pOrder.setExceptionInd("Y");
        String modBy = "AppCmdOperations. " + pPipelineType;
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

    private void createOrderEmailNotifications(OrderData pOrder, OrderPropertyDataVector pNotes, String pPipelineType)   throws Exception {

        log.info("createOrderEmailNotifications()=> BEGIN" +
                ",\n       OrderID: " + pOrder.getOrderId() +
                ",\n OrderStatusCD: " + pOrder.getOrderStatusCd() +
                ",\n   pNotes.Size: " + pNotes.size() +
                ",\n pPipelineType: " + pNotes.size());

        Connection conn = null;
        try {

//            APIAccess factory = getAPIAccess();
            conn = getConnection();
            APIAccess factory = APIAccess.getAPIAccess();
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
                        "AppCmdOperations. " + pPipelineType);

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
    private void fSendOrderEmails(Map params, ExecutorService service)  throws Exception {

        Date startDate = new Date();
        //log("Workflow processing, send emails");
    	log(" processing initiated." );

        int errCount = 0;
        int oid = 0;
        try
        {
/*          
            InitialContext jndiContext = new InitialContext(props);
            Object ref  = jndiContext.lookup(JNDINames.WORKFLOW_EJBHOME);
            WorkflowHome wkflHome = (WorkflowHome)PortableRemoteObject.narrow(ref, WorkflowHome.class);
            Workflow wkflBean = wkflHome.create();
*/
            Workflow wkflBean = APIAccess.getAPIAccess().getWorkflowAPI();
            //log("All objects are initialized.");

            //int oid = 0;
            IdVector oids = wkflBean.getOrdersSinceLastCheck();

            //log("wkflBean.getOrdersSinceLastCheck() is finished. Count of emails is " + oids.size());
            log("Count of emails is " + oids.size());

            for ( int i = 0; oids != null && i < oids.size(); i++ )
            {
                oid = ((Integer)oids.get(i)).intValue();
              
                //log("Start sendOrderEmails() , oid=" + oid + ",\t\t " + (i+1) + " of: " + oids.size());
            	String processingMess = "oid=" + oid + ",\t\t " + (i+1) + " of: " + oids.size();
                
                //wkflBean.sendOrderEmails(oid);
   
            	Callable<String> callable = new CallableWrapper.CallableProcessSendOrderEmails(oid);					
			    Future<String> future = service.submit(callable);
			    String result= getFutureResult( future, processingMess );
			    if ("ERROR".equals(result)) {
			    	errCount++;
			    }
			    //log("Finish sendOrderEmails() , oid=" + oid + ",\t\t " + (i+1) + " of: " + oids.size());
            }
            if (errCount == 0  && oids.size() > 0) {
            	log("All emails are sended.");
            }	

            // wkflBean.updateLastCheckStamp(startDate, oid);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }  finally {
	        try{
	             //log(" BATCH FINISHED ");
	             if (errCount ==0){
                    String processingMess = "wkflBean.updateLastCheckStamp()";
	             	Callable<String> callable = new CallableWrapper.CallableUpdateLastCheckStamp(startDate, oid);					
	     		    Future<String> future = service.submit(callable);
	     		    String result= getFutureResult( future, processingMess );
	     		    //log("wkflBean.updateLastCheckStamp() is finished");
	             } else {	 
	                	throw new Exception (currentTask+ ":: " + errCount + " error(s) found in the sending order emails processing.");
	             } 
	         }catch(Exception e){
	             throw e;
	         }
       }
        Date endDate = new Date();
        log("Workflow processing, send emails, done (" + startDate + "-" + endDate + ")");
        return;
    }
    
    private void fDailyOrderReprocess(Map params, ExecutorService service)  throws Exception {
        try{
        	log(" processing initiated." );
            log.info("*** Specify parameter Date='MM/dd/yyyy' to run for a specific date.");
            log.info("*** Specify parameter maxRows=optional maximum rows to process (defualt=500, -1 for unlimited).");
            int weeksBack = 9;
            int maxRows = 0;
//            String aDate = System.getProperty("Date");
//            String onum = System.getProperty("orderNum");
//            String sMaxRows = System.getProperty("maxRows");
            String aDate = (String)params.get("Date");
            String onum = (String)params.get("orderNum");
            String sMaxRows = (String)params.get("maxRows");
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
            	//new SimpleDateFormat("MMddyyyy");

            Date today = new Date();

			if ( null != onum ) {
			    log("Runing for orderNum="+onum);
			}

            if(aDate != null){
                try{
                    log("Runing for specified date: "+aDate);
                    today = sdf.parse(aDate);
                }catch(Exception e){
                    log("Error: Could not parse Date: ["+aDate+"] must use format MMddyyyy");
                    return;
                }
            }else{
                log("Info: No date specified runing normal date logic");
            }
            
            if (Utility.isSet(sMaxRows )) {
    		    log("Runing for maxRows="+sMaxRows);
    		    maxRows = new Integer(sMaxRows).intValue();
    		}

            sdf = new SimpleDateFormat("MM/dd/yyyy");
            
            Pipeline pipelineEjb = APIAccess.getAPIAccess().getPipelineAPI();
            Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();
//            ExecutorService service = Executors.newFixedThreadPool(1);
            
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
                        log.info("*** before: "+sdf.format(cal.getTime()));
                        cal.add(Calendar.DAY_OF_YEAR,interval * i);
                        log.info("*** after: "+sdf.format(cal.getTime()));
                        Date[] range = getDateRangeForTodayPlusWeekend(cal.getTime());
                        begDate = range[0];
                        endDate = range[1];
                    }

                    log("Finding orders between: "+sdf.format(begDate) +" and "+sdf.format(endDate));

                    //go find the orders
                    OrderStatusCriteriaData crit = OrderStatusCriteriaData.createValue();
                    crit.setOrderDateRangeBegin(sdf.format(begDate));
                    crit.setOrderDateRangeEnd(sdf.format(endDate));
                    crit.addOrderStatus(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
                    crit.addOrderStatus(RefCodeNames.ORDER_STATUS_CD.INVOICED);
                    if (maxRows != 0) {
                    	crit.setMaxRows(maxRows);
                    }
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
            //===== finally send the orders trhough the pipeline =====/
            //Set<Future<String>> set = new LinkedHashSet<Future<String>>();

            int ct = 0;
            int errCount = 0;
            for(Iterator iter = orders.iterator(); iter.hasNext();){
               ct++;
               String processingMess = ct + " of " + orders.size();
               OrderData oD = (OrderData) iter.next();
			   String orderStatus = oD.getOrderStatusCd();
/*               if(ct % 50 == 0){
                   log.info("fDailyOrderReprocess() ===> Processed "+ct+" orders");
               }
*/
			   Callable<String> callable = new CallableWrapper.CallableProcessPipline(oD, null);					
			   Future<String> future = service.submit(callable);
			   errCount += processFutureException(service, future, oD, RefCodeNames.PIPELINE_CD.ASYNCH, true,processingMess );
			   //set.add(future);
            }
            //processFutureExceptions(service, set, orders, RefCodeNames.PIPELINE_CD.BATCH_REPROCESS_ORDER, true);
            if (errCount > 0 ){
               	throw new Exception (currentTask+ ":: " + errCount + " error(s) found in the orders processing.");
            }
        }catch (Exception e)	{
        	throw e;
//        	e.printStackTrace();
	    }
    log(" processing done." );
    // need the return so as to avoid the usage message
    return;

    }
    
    private void fProcessAutoOrders(Map params,ExecutorService service)  throws Exception {
        Date startDate = new Date();
        log("Processing initiated: " + startDate );
        try
        {
            AutoOrder aoBean = APIAccess.getAPIAccess().getAutoOrderAPI();
            EmailClient emailClientEjb = APIAccess.getAPIAccess().getEmailClientAPI();
//            ExecutorService service = Executors.newFixedThreadPool(1);
            
            String defaultEmailAddress = emailClientEjb.getDefaultEmailAddress();
            

//            String accounts = System.getProperty("accountIds");
            String accounts = (String)params.get("accountIds");
            IdVector accountIdV = null;
            if(accounts==null) {
                log("Error: No account ids provided");
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
                        log("Error: Invalid account id format. Account id = "+accountIdA[ii] );
                        return;
                    }
                }
                if(minusFl && plusFl) {
                    log("Error: Incompatible set of account ids. Both plus and minus present ");
                    return;
                }
            }

            Set<Future<String>> set = new LinkedHashSet<Future<String>>();
            Date today = new Date();
            IdVector orderSchIdV  =
                        aoBean.getOrderSchedules(today, accountIdV, plusFl);
            log("Found "+orderSchIdV.size()+" scheduled orders to process");
 
            for(Iterator iter=orderSchIdV.iterator(); iter.hasNext();){
               Integer orderSchIdI = (Integer) iter.next();
               int orderSchId = orderSchIdI.intValue();
               String resultMess = null;
/* -----------------------------------------------*/
        	   Callable<String> callable = new CallableWrapper.CallablePlaceAutoOrder(orderSchId, today);					
			   Future<String> future = service.submit(callable);
			   set.add(future);
			   if (future != null ) {
				   try {
					   String emailMess = future.get();
					   if(emailMess!=null) {
						   callable = new CallableWrapper.CallableSendOrderNotification(orderSchId, emailMess);					
						   future = service.submit(callable);
						   set.add(future);
					   }
					   resultMess = "Scheduled order "+orderSchId+" was processed";
					   
				   } catch (Exception exc){
					   resultMess = "Scheduled order "+orderSchId+" failed";
					 //log error result
					   callable = new CallableWrapper.CallableLogErrorRecord(orderSchId, exc.getMessage(), today);					
					   future = service.submit(callable);
					   set.add(future);
					 //send error email
					   String detailErrorMess = exc.getMessage();
					   if(detailErrorMess==null || detailErrorMess.trim().length()==0) {
						   detailErrorMess = resultMess; 
					   }
					   int ind1 = detailErrorMess.indexOf("^clw^");
	                   if(ind1>0 && ind1+5<detailErrorMess.length()) {
	                     int ind2 = detailErrorMess.indexOf("^clw^",ind1+5);
	                     if(ind2>0) {
	                    	 detailErrorMess = detailErrorMess.substring(ind1+5,ind2);
	                     }
	                   }
					   
					   callable = new CallableWrapper.CallableEmailSend(defaultEmailAddress, defaultEmailAddress, resultMess, detailErrorMess);					
					   future = service.submit(callable);
					   
					   
				   }
			   }
			   set.add(future);
  
//                try {
//                 String emailMess =
//                        aoBean.placeAutoOrder(orderSchId, today, "System");
//                 if(emailMess!=null) {
//                   try {
//                     aoBean.sendOrderNotification(orderSchId, emailMess, "System");
//                   } catch (Exception exc1) {}
//                 }
//                 resultMess = "Order Schedule "+orderSchId+" was processed";
//               } catch (Exception exc) {
//                 resultMess = "Order Schedule "+orderSchId+" failed";
//                 try {
//                    //log error result
//                    aoBean.logErrorRecord(orderSchId,
//                                        exc.getMessage(), today, "System");
//                 } catch (Exception exc1) {}
//
//                 try {
//                  //send error email
//                   String detailErrorMess = exc.getMessage();
//                   if(detailErrorMess==null || detailErrorMess.trim().length()==0) {
//                     detailErrorMess = resultMess;
//                   }
//                   int ind1 = detailErrorMess.indexOf("^clw^");
//                   if(ind1>0 && ind1+5<detailErrorMess.length()) {
//                     int ind2 = detailErrorMess.indexOf("^clw^",ind1+5);
//                     if(ind2>0) detailErrorMess =
//                                        detailErrorMess.substring(ind1+5,ind2);
//                   }
//                   emailClientEjb.send(defaultEmailAddress,
//				   emailClientEjb.getDefaultEmailAddress(),
//				   resultMess,
//				   detailErrorMess, null, 0,0);
//
//                 } catch (Exception exc1) {}
//               }
/*----------------------------*/
               log("Result :" + resultMess);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Date endDate = new Date();
        log("Processing done." );

        return;

    }
    
/*    private void fOrdersToLawsonTest(Map params)  throws Exception {
        try
        {
           log.info("fOrdersToLawsonTest :" + currentTask + " processing INITIATED:      " +    new Date());
	       PropertyService propertyServiceEjb =APIAccess.getAPIAccess().getPropertyServiceAPI();
           Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();
	        String stores = (String)params.get("stores");
	        String propertyTypes = (String)params.get("propertyTypes");
	        if(stores==null) {
	            log("orders_to_lawson. No store ids provided");
	            return;
	        }
	        if("all".equalsIgnoreCase(stores.trim())) {
	            stores="0";
	        }
	        String[] storesA = Utility.parseStringToArray(stores,",");
	        Map<String, String> map = new HashMap<String, String>();
	        int[] storeIds = new int[storesA.length];
	        for(int ii=0; ii<storesA.length;ii++) {
	            try {
	                storeIds[ii] = Integer.parseInt(storesA[ii]);
	                if (storeIds[ii] != 0) {
	                	map.put(""+storeIds[ii], RefCodeNames.PROPERTY_TYPE_CD.SYSTEM_MESSAGE+storeIds[ii]);
	                }
	            }catch(Exception exc) {
	                log(currentTask+". Error. Invalid store id format. Store id = "+storesA[ii] );
	                return;
	            }
	        }
		    Set<Future<String>> set = new LinkedHashSet<Future<String>>();

      	    ExecutorService service = Executors.newFixedThreadPool(1);
	        for(int ii=0; ii<storeIds.length;ii++) {
 	            try {
 	               //javax.transaction.TransactionManager tm = propertyServiceEjb.getTrasactionManager()	;
 	               //log.info("fOrdersToLawsonTest : " + tm);
 	               log.info("fOrdersToLawsonTest :" + currentTask + "==> processing store Id:  " +   storeIds[ii]);
 	                if (storeIds[ii] > 0) {
 	                	String mess = "Pipeline message. Store id: "+storeIds[ii];
 	                	//PropertyService myEjb = this.getInitialContext().get
 	      	        //  this.getTransactionManager().begin();
 	                	propertyServiceEjb.setProperty((String)map.get(""+storeIds[ii]), mess);
 	 	      	   //   this.getTransactionManager().commit();
 	                } else {
	 	   				Callable<String> callable = new CallableWrapper.CallableProcess(storeIds[ii], (String)map.get(""+storeIds[ii]));					
	 	   			    Future<String> future = service.submit(callable);
	 					set.add(future);
 	                }
 
 	            
 	            } catch (Exception exc){
  	               log.info("fOrdersToLawsonTest :" + currentTask + "==> Exception for store Id:  " +   storeIds[ii]);
                }
           }
	       idx = 0 ;
		   for (Future<String> future : set) {
			   try {
				String res = future.get();
				log.info(storeIds.length	+ " -> " + (idx+1) + " processing result: " + res + "\n");

				idx++;	
			   } catch (Exception ex) {
  	               log.info("fOrdersToLawsonTest :" + currentTask + "==> Exception for future:  " +   ex.getMessage());
  	               service.submit( new Runnable(){
  	            	 public void run() { 
	  	                String errorMess = "Pipeline exception. idx : "+idx;
		                OrderPropertyData opD = OrderPropertyData.createValue();
		                opD.setOrderId(228221);
		                opD.setShortDesc("Pipeline Exception");
		                opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
		                opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
		                opD.setValue(errorMess);
		                opD.setModBy("ng");
		                opD.setAddBy("ng");
		                try {
		                APIAccess.getAPIAccess().getOrderAPI().addNote(opD);
		                } catch (Exception ex){
		                	ex.printStackTrace();
		                }
  	            	 }
	                });
			   }
		   }
	        
           log.info("fOrdersToLawsonTest :" + currentTask + " processing FINISHED:      " +    new Date());
	        
	        
        } catch (Exception e)  {
	            e.printStackTrace();
	    }
    }
*/
    private void fOrdersToLawson(Map params, ExecutorService service)  throws Exception {

        Date startDate = new Date();
        log(" processing initiated:      " );

        int totalErrCount = 0;
        int errCount = 0;
        try
        {

            Pipeline pipelineEjb = APIAccess.getAPIAccess().getPipelineAPI();
            Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();
            PropertyService propertyServiceEjb =APIAccess.getAPIAccess().getPropertyServiceAPI();
            Store storeBean = APIAccess.getAPIAccess().getStoreAPI();
       	    
//            ExecutorService service = Executors.newFixedThreadPool(1);
 
            String stores = (String)params.get("stores");
            if(stores==null) {
                log("Error: No store ids provided");
                //return;
                throw new Exception(currentTask+ ":: Error => No store ids provided");
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
                    log("Error: Invalid store id format. Store id = "+storesA[ii] );
                    //return;
                    throw exc;
                }
            }
            if(minusFl && plusFl) {
                log("Error: Incomatible set of stores. Both plus and minus present ");
                //return;
                throw new Exception(currentTask+ ":: Error => Incomatible set of stores. Both plus and minus present ");
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
                 String errMess = "Error: Erp System "+shortDesc+" has multiple status records";
                 log(errMess);
                 erpStatusHM.put(shortDesc,"ERROR");
               }
             }
           }

           //======== finishing interrupted pipeline orders =============
           OrderDataVector orders = orderEjb.getOrdersPipelineToResume();
           log.info("***** Process OrdersPipelineToResume started:: Size()="+((orders!=null) ? orders.size(): "0"));
           errCount = processOrdersPipeline(service, storeIds, erpStatusHM, orders, null, true);
           totalErrCount += errCount;
           log.info("***** Process OrdersPipelineToResume finished:: processed "+ (orders.size()-errCount) + " of " + orders.size() + ". Error count: " + errCount);
          
           //======== process RECEIVED pipeline orders ==================
           orders = orderEjb.getOrdersByType(RefCodeNames.ORDER_STATUS_CD.RECEIVED);
           log.info("***** Process RECEIVED pipeline orders started. Size()="+((orders!=null) ? orders.size(): "0"));
           errCount = processOrdersPipeline(service, storeIds, erpStatusHM, orders, RefCodeNames.PIPELINE_CD.SYNCH_ASYNCH, true);
           totalErrCount += errCount;
           log.info("***** Process RECEIVED finished:: processed "+ (orders.size()-errCount) + " of " + orders.size() + ".Error count: " + errCount);

           //======== process ORDERED pipeline orders ===================
           orders =  orderEjb.getOrdersByType(RefCodeNames.ORDER_STATUS_CD.ORDERED);
           log.info("***** Process ORDERED pipeline orders started. size()="+((orders!=null) ? orders.size(): "0"));
           errCount = processOrdersPipeline(service, storeIds, erpStatusHM, orders, RefCodeNames.PIPELINE_CD.ASYNCH, true);
           totalErrCount += errCount;
           log.info("***** Process ORDERED finished:: processed "+ (orders.size()-errCount) + " of " + orders.size() + ".Error count: " + errCount);

           //======== process PRE_PROCESSED pipeline orders =============
           orders = orderEjb.getOrdersByType(RefCodeNames.ORDER_STATUS_CD.PRE_PROCESSED);
           log.info("***** Process PRE_PROCESSED pipeline orders started. size()="+((orders!=null) ? orders.size(): "0"));
           errCount = processOrdersPipeline(service, storeIds, erpStatusHM, orders, RefCodeNames.PIPELINE_CD.ASYNCH_PRE_PROCESSED, false);
           totalErrCount += errCount;
           log.info("***** Process PRE_PROCESSED finished:: processed "+ (orders.size()-errCount) + " of " + orders.size() + ".Error count: " + errCount);

           if (totalErrCount > 0 ){
           	throw new Exception (currentTask+ ":: " + totalErrCount + " error(s) found in the orders processing.");
           }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        Date endDate = new Date();
        log(" processing done:      " );
        return;


    }
    private void fInvoicesToLawson(Map params)  throws Exception {
        Date startDate = new Date();
        log.info("Lawson: " + currentTask +
                           " processing initiated:      " +
                           startDate );
        String semaphore = "INVOICE_DIST_PROCESSING";
        
        try
        {
        	Pipeline lBean = APIAccess.getAPIAccess().getPipelineAPI();
        	lBean.processDistInvoicePipeline();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Date endDate = new Date();
        log.info("Lawson: " + currentTask +
                           " processing done:      " +
                           endDate );
        return;


    }
    private void fInvoicesToLawsonCallable(Map params, ExecutorService service)  throws Exception {
        Date startDate = new Date();
        log.info("Lawson: " + currentTask +
                           " processing initiated:      " +
                           startDate );
//        String semaphore = "INVOICE_DIST_PROCESSING";
//        ExecutorService service = Executors.newFixedThreadPool(1);
        int errCount = 0;
        
        try
        {
        	Pipeline lBean = APIAccess.getAPIAccess().getPipelineAPI();
            //lBean.processDistInvoicePipeline();
            log(" BATCH STARTED ");
            Connection conn = null;
            try {
/*            	Callable<String> callable = new CallableWrapper.CallableAppLockUnlock(semaphore, "true");	
            	Future<String> lstat = service.submit(callable);
            	//String lstat = getAppLock(conn, semaphore);
                log.info("lock status is: " + lstat.get());
                if (lstat != null && ! "UNLOCKED".equals(lstat.get()) ) {
                  String errMess = "This operation1, "+semaphore+" is " + lstat.get();	
                  log(errMess );
                  //return;
                  throw new Exception(errMess);
                }
*/
            	conn = getConnection();
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(InvoiceDistDataAccess.INVOICE_STATUS_CD, RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED);
                IdVector toProcess = InvoiceDistDataAccess.selectIdOnly(conn,crit);
                log.info("Found "+toProcess.size()+" invoices to process");
                Iterator it = toProcess.iterator();
                int i = 0;
                 while(it.hasNext()){
                    Integer invoiceId = 	((Integer) it.next()).intValue();
                    i++;
                	String processingMess = i + " of " + toProcess.size() +  ":: Invoice id:"+ invoiceId;
                	//lBean.processDistInvoicePipeline(((Integer) it.next()).intValue());
                	Callable<String> callable = new CallableWrapper.CallableProcessInvoices(invoiceId);					
    			   Future<String> future = service.submit(callable);
    			   String invStatus= getFutureResult( future, processingMess );
    			   //if (RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW.equals(invStatus)){
       			   if ("ERROR".equals(invStatus)){
    			   	   errCount++;
    			   }
                }
            }catch(Exception e){
                throw e;
            } finally {
               try{
//                 if(semaphore!=null) {
//                 	Callable<String> callable = new CallableWrapper.CallableAppLockUnlock(semaphore, "false");	
//                	Future<String> rs = service.submit(callable);
                    log(" BATCH FINISHED ");
                    if (errCount >0){
                       	throw new Exception (currentTask+ ":: " + errCount + " error(s) found in the invoices processing.");
                    }

//                 }
                }catch(Exception e){
//                    throw new RemoteException(e.getMessage());
                    throw e;
                }
              }
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            //throw processException(e);
            throw e;
        }

        Date endDate = new Date();
        log.info("Lawson: " + currentTask +
                           " processing done:      " +
                           endDate );
        return;


    }
    
    private void fProcessWorkflow(Map params, ExecutorService service)  throws Exception {
        Date startDate = new Date();
        log("Workflow processing initiated.");

        try
        {
 /*
            InitialContext jndiContext = new InitialContext(props);
            Object ref = jndiContext.lookup(JNDINames.WORKFLOW_EJBHOME);
            WorkflowHome wkflHome = (WorkflowHome)PortableRemoteObject.narrow (ref, WorkflowHome.class);
            Workflow wkflBean = wkflHome.create();
*/            
 //           Workflow wkflBean = APIAccess.getAPIAccess().getWorkflowAPI();
//            ExecutorService service = Executors.newFixedThreadPool(1);

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
            Set<Future<String>> set = new LinkedHashSet<Future<String>>();

            for(int ii=0; ii<100; ii++) {
            	String cntS="";
            	if(ii==0) { //do only once
                    String actionType = RefCodeNames.WORKFLOW_RULE_ACTION.PENDING_REVIEW;
                    log("Processing "+actionType+ " workflow queue. Loop: "+ii);
             	   
                    Callable<String> callable = new CallableWrapper.CallableProcessQueueEntries(actionType);					
    			    Future<String> future = service.submit(callable);
    			    String result = getFutureResult(future, actionType+ " workflow queue. Loop: "+ii+" count: ");
                    //int cnt = wkflBean.processQueueEntries(actionType);
                }

                //********************************************************************
                if(ii==0 || forwardForApprovalQty>0) {
                    String actionType = RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL;
                    log("Processing "+actionType+ " workflow queue. loop: "+ii);
                    Callable<String> callable = new CallableWrapper.CallableProcessQueueEntries(actionType);					
    			    Future<String> future = service.submit(callable);
    			    String result = getFutureResult(future,  actionType+ " workflow queue. Loop: "+ii+" count: ");
    			    forwardForApprovalQty = (result != null) ? Integer.parseInt(result) : 0;
//                    forwardForApprovalQty = wkflBean.processQueueEntries(actionType);
                }
                //********************************************************************
                if(ii==0 || stopOrderQty>0) {
                    String actionType = RefCodeNames.WORKFLOW_RULE_ACTION.STOP_ORDER;
                    log("Processing "+actionType+ " workflow queue. loop: "+ii);
                    Callable<String> callable = new CallableWrapper.CallableProcessQueueEntries(actionType);					
    			    Future<String> future = service.submit(callable);
    			    String result = getFutureResult(future,  actionType+ " workflow queue. Loop: "+ii+" count: ");
    			    stopOrderQty = (result != null) ? Integer.parseInt(result) : 0;

//                    stopOrderQty = wkflBean.processQueueEntries(actionType);
                }

                //********************************************************************
                if(ii==0 || sendEmailQty>0) {
                    String actionType = RefCodeNames.WORKFLOW_RULE_ACTION.SEND_EMAIL;
                    log("Processing "+actionType+ " workflow queue. loop: "+ii);
                    Callable<String> callable = new CallableWrapper.CallableProcessQueueEntries(actionType);					
    			    Future<String> future = service.submit(callable);
    			    String result = getFutureResult(future,  actionType+ " workflow queue. Loop: "+ii+" count: ");
    			    sendEmailQty = (result != null) ? Integer.parseInt(result) : 0;

//                    sendEmailQty = wkflBean.processQueueEntries(actionType);
                }
                if(forwardForApprovalQty == 0 && stopOrderQty == 0 && sendEmailQty == 0) {
                    break;
                }

            }

            log("Finish processQueueEntries()");

/*            Object ref1 = jndiContext.lookup(JNDINames.INTEGRATION_SERVICES_EJBHOME);
            IntegrationServicesHome isrvHome = (IntegrationServicesHome)PortableRemoteObject.narrow(ref1, IntegrationServicesHome.class);
            IntegrationServices isrvEjb = isrvHome.create();
*/
//            IntegrationServices isrvEjb = APIAccess.getAPIAccess().getIntegrationServicesAPI();
            
            log("Start processPendingDateOrders()");
            Callable<String> callable = new CallableWrapper.CallableProcessPendingDateOrders();					
		    Future<String> future = service.submit(callable);
		    String result = getFutureResult(future,  "Pending Date Orders.");

//            isrvEjb.processPendingDateOrders();
            log("Finish processPendingDateOrders()");
        } catch (Exception e) {
            e.printStackTrace();
            throw e; 
        }

        Date endDate = new Date();
        log("Workflow processing done. (" + startDate + "-" + endDate + ")");
        return;

    }
    private void fSynchContent(Map params, ExecutorService service) throws Exception{
    	log(" processing initiated." );
    	String refetchAll = (String)params.get("refetchAllContent");
    	Content contentEjb = APIAccess.getAPIAccess().getContentAPI();
    	Map<Integer, String> statusMap = null;
    	try {
	    	if(Utility.isTrue(refetchAll)){
	    		log("force refresh called");
	    		statusMap = contentEjb.refreshBinaryData(true);
	    	}else{
	    		log("force refresh not called, just fetching changes");
	    		statusMap = contentEjb.refreshBinaryData(false);
	    	}
	    	int errCount = 0;
	    	if (statusMap== null || statusMap.isEmpty()){
	    		log.info("*** No contents to process");
	    	} else {
		    	Iterator it = statusMap.keySet().iterator();
		    	while(it.hasNext()){
		    		Integer contentId = (Integer)it.next();
		    		String message = (String)statusMap.get(contentId);
		    		if (Utility.isSet(message)) {
		    			log.info("*** Failed :: contentId="+ it.next()+ " => " +message  );
		    			errCount++;
		    		} else {
		    			log.info("*** Processed :: contentId="+ it.next()  );
		    		}
		    	}
	    	}
	    	if (errCount > 0){
	    		throw new Exception (currentTask+ ":: " + errCount + " error(s) found in the process of refreshing content.");
	    	}
    	} catch (Exception e) {
    		throw e;
    	}
        log(" processing done." );
        return;
    }
    
    private void fCreateFiscalCalCasheTable (Map params, ExecutorService service)  throws Exception {
    	//APIAccess factory = getAPIAccess(loadConfiguration());
    	log(" processing initiated." );
    	try {
	    	Account acctEjb = APIAccess.getAPIAccess().getAccountAPI();
	    	acctEjb.createFiscalCalCacheTablesForAll();
	        log(" processing done." );
	    	return;
    	}catch (Exception ex){
        	throw ex;
        }

    }

    private void fResetCostCentersOld(Map params, ExecutorService service)  throws Exception {
        //APIAccess factory = getAPIAccess(loadConfiguration());
    	log(" processing initiated." );
    	try {
	        Catalog catalogEjb = APIAccess.getAPIAccess().getCatalogAPI();
	        CatalogInformation catalogInfEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
//	        ExecutorService service = Executors.newFixedThreadPool(1);
	
//	        EntitySearchCriteria crit = new EntitySearchCriteria();
//	        ArrayList accountTypeCdList = new ArrayList();
//	        accountTypeCdList.add(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
//	        crit.setSearchTypeCds(accountTypeCdList);
//	        CatalogDataVector cdv = catalogInfEjb.getCatalogsByCrit(crit);
	        
	        DBCriteria crt = new DBCriteria();
	        String modifiedCatalogsSql = "select catalog_id from clw_catalog_structure cs where CS.MOD_DATE > sysdate - 10 group by catalog_id";
	        crt.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
	        crt.addOneOf(CatalogDataAccess.CATALOG_ID, modifiedCatalogsSql);
	        CatalogDataVector cdv = catalogEjb.getCatalogCollection(crt);;
	        
	       
	        if (cdv == null){
	        	log.info("No catalogs found to process");
	        } else {
		        log.info("Found "+cdv.size()+" catalogs to process");
		        Iterator it = cdv.iterator();
		        int errCount = 0;
		        int i = 0;
		        while(it.hasNext()){
		            CatalogData cat = (CatalogData) it.next();
	                i++;
	                String processingMess = i + " of " + cdv.size() + ":: Catalog id: "+ cat.getCatalogId();
		            if(RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cat.getCatalogStatusCd())){
		            	//log.info("resetting cost centers for catalog: "+cat.getShortDesc()+" "+cat.getCatalogTypeCd());
		                //catalogEjb.resetCostCenters(cat.getCatalogId(),"System");
		 			   Callable<String> callable = new CallableWrapper.CallableProcessResetCostCentersOld(cat.getCatalogId());					
					   Future<String> future = service.submit(callable);
		   			   String status= getFutureResult( future, processingMess );
		   			   if ("ERROR".equals(status)){
					   	   errCount++;
					   }
		            } else {
		                //log(" result => Skipped "+ processingMess + " Reason:  Catalog has '"+ cat.getCatalogStatusCd()+"' status");
		            }
		        }
		        if (errCount > 0) {
	              	throw new Exception (currentTask+ ":: " + errCount + " error(s) found in the process of resetting cost centers.");
		        }
	        }
    	}catch (Exception ex){
        	throw ex;
        }
        log(" processing done." );
        return;

    }
    private void fResetCostCenters(Map params, ExecutorService service)  throws Exception {
        //APIAccess factory = getAPIAccess(loadConfiguration());
    	log(" processing initiated." );
    	String user = "ProcessResetCostCenters";
    	String tempDbUser =(String)params.get("tempDbUser");
    	String excludeShoppingCatalogs = (String)params.get("excludeShoppingCatalogs");
    	IdVector excludeShoppingCatalogList = null;
    	if (Utility.isSet(excludeShoppingCatalogs)) {
	    	try {
	            excludeShoppingCatalogList = Utility.parseIdStringToVector(excludeShoppingCatalogs,",", true);
	       	}catch (Exception ex){
	        	throw ex;
	        }
    	}
    	int updated =0;
    	try {
	        CatalogInformation catalogInfEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
	        updated = catalogInfEjb.resetCostCenters(user, (Utility.isSet(tempDbUser)? tempDbUser : (String)null), excludeShoppingCatalogList );
    	}catch (Exception ex){
        	throw ex;
        }
        log(" processing done. Updated :"+ updated + " cost centers." );
        return;

    }

    /**
     *Gets the date range inclusive of the weekend for processing.  That is if you ask this method for the date range
     *on wednesday 1/1/2005 it give you 2/10/2005 - 2/10/2005; However if you ask it for the date range on Monday morning
     *2/10/2005 it gives you a date range of 2/7/2005 to 2/10/2005.  For a request of Friday evening on 2/10/2005 you will
     *get back a date range of 2/10/2005 to 2/2005.
     */
    protected static Date[] getDateRangeForTodayPlusWeekend(Date pBaseDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(pBaseDate);
        int forwardModifier = 0;
        int backModifier = 0;
        if(cal.get(Calendar.HOUR_OF_DAY) < 10){
            //we are running in the morning
            if(Calendar.MONDAY == cal.get(Calendar.DAY_OF_WEEK)){
                backModifier = 2;
            }
        }else{
            //we are running at night
            if(Calendar.FRIDAY == cal.get(Calendar.DAY_OF_WEEK)){
                forwardModifier = 2;
            }
        }
        
        
        cal.add(Calendar.DAY_OF_YEAR, -1 * backModifier);
        Date begDate = cal.getTime();
        cal.setTime(pBaseDate);
        cal.add(Calendar.DAY_OF_YEAR, forwardModifier);
        Date endDate = cal.getTime();
        Date[] retVal = new Date[2];
        retVal[0] = begDate;
        retVal[1] = endDate;
        return retVal;
    }

    private void initLock(String command, String paramKey) throws Exception {
		synchronized(AppCmdOperations.class){
			String key = command+":"+ paramKey;
			if (!Utility.isSet(semaphore.get(key)) ||
				STATE.UNLOCKED.equals(semaphore.get(key))){
				semaphore.put(key, STATE.LOCKED);
			}
			else{
				throw new IgnoredException("Cannot start. There is a process with " + command + " function and parameters:"+paramKey+" IN PROGRESS status.");
			}
		}
    }
    private void releaseLock(String command, String paramKey) {
        synchronized(AppCmdOperations.class){
			String key = command+":"+ paramKey;
			if (STATE.LOCKED.equals(semaphore.get(key))){
				semaphore.put(key, STATE.UNLOCKED);
			}
		}
    }
    
    private void log(String message){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN+" "+Constants.SIMPLE_TIME24_PATTERN);
        log.info(sdf.format(date) + "  " + currentTask + "=>"+ message+  "\n");
    }
    
}

/*
 * GenericReportRunner.java
 *
 * Created on March 4, 2003, 9:24 AM
 */

package com.cleanwise.service.api.process.operations;
import java.io.*;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.JNDINames;
import com.cleanwise.service.api.util.ScheduleProc;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.apps.ApplicationsEmailTool;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.apps.*;
import com.cleanwise.service.apps.AppCmd.SiteReqUtil;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.pipeline.PendingOrderNotification;
import com.cleanwise.service.api.reporting.InventoryMissingEmailReport;
import com.cleanwise.service.api.reporting.InventoryScheduleReport;
import com.cleanwise.service.api.reporting.ReportWriterUtil;
import org.apache.log4j.Logger;

/**
 * Process designed to produce a report that can then
 * be transmitted to a user.  This program is desinged to be the scheduled interface for the
 * reporting framework.
 */
public class CallableWrapper  extends ApplicationServicesAPI{
    private static final Logger log = Logger.getLogger(CallableWrapper.class);

 //#############################################################//
	public static class CallableProcess implements Callable<String> {
		int storeId = 0;
		String cd = null;
		String result = "";
		
		CallableProcess(int storeId, String cd){
			this.storeId=storeId;
			this.cd = cd;
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				PropertyService bean = APIAccess.getAPIAccess().getPropertyServiceAPI();
	            String mess = "Pipeline message. Store id: "+storeId;
	            bean.setProperty(cd, mess);
	
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
	            result = ex.getMessage();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}
	
	public static class CallableProcessPipline implements Callable<String> {
		OrderData oD = null;
		String cd = null;
	//	String result = "";
		
		CallableProcessPipline(OrderData oD, String cd){
			this.oD=oD;
			this.cd = cd;
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				Pipeline bean = APIAccess.getAPIAccess().getPipelineAPI();
//	            String mess = "Pipeline message. Order id: "+oD.getOrderId();
	            if (!Utility.isSet(cd)){
	            	bean.resumePipeline(oD);
	            } else {
	            	bean.processPipeline(oD, cd);
	            }
	            result = "Order id: "+ oD.getOrderId()+ ", Status: "+ oD.getOrderStatusCd();
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}
	public static class CallablePlaceAutoOrder implements Callable<String> {
		int orderSchId = 0;
		Date date = null;
		String user = "System";
		
		CallablePlaceAutoOrder(int orderSchId, Date date){
			this.orderSchId=orderSchId;
			this.date = date;
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				AutoOrder bean = APIAccess.getAPIAccess().getAutoOrderAPI();
				result = bean.placeAutoOrder(orderSchId, date, user);
	 
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
//	            result = ex.getMessage();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}

	public static class CallableUpdateLastCheckStamp implements Callable<String> {
  		Integer oid =  null;
  		Date startDate = null; 
		
		CallableUpdateLastCheckStamp(Date startDate, Integer oid){
			this.oid = oid;
			this.startDate = startDate;
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				Workflow bean = APIAccess.getAPIAccess().getWorkflowAPI();
				bean.updateLastCheckStamp(startDate, oid);		 
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}

	public static class CallableSendOrderNotification implements Callable<String> {
		int orderSchId = 0;
		String mess = null;
		String user = "System";
		
		CallableSendOrderNotification(int orderSchId, String mess){
			this.orderSchId=orderSchId;
			this.mess = mess;
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				AutoOrder bean = APIAccess.getAPIAccess().getAutoOrderAPI();
				bean.sendOrderNotification(orderSchId, mess, user);
	 
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
	            result = ex.getMessage();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}

	public static class CallableLogErrorRecord implements Callable<String> {
		int orderSchId = 0;
		String mess = null;
		Date date = null;
		String user = "System";
		
		CallableLogErrorRecord(int orderSchId, String mess, Date date){
			this.orderSchId=orderSchId;
			this.mess = mess;
			this.date = date;
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				AutoOrder bean = APIAccess.getAPIAccess().getAutoOrderAPI();
				bean.logErrorRecord(orderSchId, mess, date, user);
	 
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
	            result = ex.getMessage();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}
	
	public static class CallableEmailSend implements Callable<String> {
		String toEmailAddress = null;
        String fromEmailAddress = null;
        String subject = null;
        String msg = null;
        String msgFormat = null;
        int emailBusEntityId = 0;
        int emailUserId = 0;
  		
		CallableEmailSend(String toEmailAddress, String fromEmailAddress, String subject, String msg){
			this.toEmailAddress=toEmailAddress;
			this.fromEmailAddress = fromEmailAddress;
			this.subject = subject;
			this.msg = msg;
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				EmailClient bean = APIAccess.getAPIAccess().getEmailClientAPI();
				bean.send(toEmailAddress, fromEmailAddress, subject, msg, msgFormat, emailBusEntityId, emailUserId);
	 
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
	            result = ex.getMessage();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}

	public static class CallableProcessQueueEntries implements Callable<String> {
		String actionType = null;
        		
        CallableProcessQueueEntries(String actionType){
			this.actionType=actionType;
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				Workflow bean = APIAccess.getAPIAccess().getWorkflowAPI();
				int cnt = bean.processQueueEntries(actionType);
				result = ""+cnt;
	 
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}

	public static class CallableProcessPendingDateOrders implements Callable<String> {
      		
		CallableProcessPendingDateOrders(){
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				IntegrationServices bean = APIAccess.getAPIAccess().getIntegrationServicesAPI();
				bean.processPendingDateOrders();		 
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}
	public static class CallableProcessSendOrderEmails implements Callable<String> {
  		Integer oid;
		
		CallableProcessSendOrderEmails(Integer oid){
			this.oid = oid;
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				Workflow bean = APIAccess.getAPIAccess().getWorkflowAPI();
				bean.sendOrderEmails(oid);		 
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}
	public static class CallableProcessResetCostCentersOld implements Callable<String> {
		int catalogId = 0;
		String user = "ProcessResetCostCentersOld";//"System";
		
		CallableProcessResetCostCentersOld(int catalogId){
			this.catalogId=catalogId;
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				Catalog bean = APIAccess.getAPIAccess().getCatalogAPI();
				bean.resetCostCenters(catalogId, user);
	 
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
//	            result = ex.getMessage();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}	

	public static class CallableProcessInvoices implements Callable<String> {
		int invoiceId = 0;
		
		CallableProcessInvoices(int invoiceId){
			this.invoiceId=invoiceId;
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				Pipeline bean = APIAccess.getAPIAccess().getPipelineAPI();
				result = bean.processDistInvoicePipeline(invoiceId);
	 
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
//	            result = ex.getMessage();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}
/*	public static class CallableAppLockUnlock implements Callable<String> {
		String semaphor = null;
		String toLock = null;
		
		CallableAppLockUnlock(String semaphor, String toLock){
			this.semaphor=semaphor;
			this.toLock=toLock;
		}
		
	    public String call() throws Exception {
	    	String result = null;
	    	try {
				APIAccess factory = new APIAccess();
	
				Pipeline bean = APIAccess.getAPIAccess().getPipelineAPI();
				if (toLock != null && "true".equals(toLock)) {
				  result = bean.getAppLock(semaphor);
				} else if (toLock != null && "false".equals(toLock)) {
				  bean.releaseAppLock(semaphor);
				}
	 
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
//	            result = ex.getMessage();
	            throw ex;
	        } 
	 
	        return result; 
	    } 
	}
*/	
	
	public static class CallableProcessException implements Callable {
		OrderData oD = null;
		String errMess = null;
		String cd = null;
		
		CallableProcessException(OrderData oD, String errMess, String cd){
			this.oD=oD;
			this.errMess = errMess;
			this.cd = cd;
		}
		
	    public OrderPropertyData call() {
	    	OrderPropertyData result = null;
	    	try {
				Order bean = APIAccess.getAPIAccess().getOrderAPI();
	
				String errorMess = "Pipeline exception. Order id: "+ oD.getOrderId() +" Error: "+ errMess;
				result = processPipelineException( bean, oD, errorMess, (Utility.isSet(cd) ? cd : RefCodeNames.PIPELINE_CD.ASYNCH));			
	
	    	} 
	        catch(final Exception ex) 
	        {
	            ex.printStackTrace();
	         } 
	        return result;
	    } 
	    private OrderPropertyData processPipelineException (Order pOrderEjb, OrderData pOrder, String pErrorMess, String pPipelineType)  throws Exception    {
		   return processPipelineException(pOrderEjb, pOrder, pErrorMess, pPipelineType,
		     RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
		}
	
	    private OrderPropertyData processPipelineException(Order pOrderEjb, OrderData pOrder, String pErrorMess, String pPipelineType, String pErrorStatus)  throws Exception{
	
		   // log.info(pErrorMess);
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
		    //OrderPropertyDataVector errNotes = new OrderPropertyDataVector();
		    OrderPropertyData errNote = null;
		    try {
		        errOrder = pOrderEjb.updateOrder(pOrder);	 
		        errNote = pOrderEjb.addNote(opD);
		        //errNotes.add(pOrderEjb.addNote(opD));
		    } catch (Exception exc1) {
		        exc1.printStackTrace();
		    }
/*		    if (errOrder != null) {
		        createOrderEmailNotifications(errOrder, errNotes, pPipelineType);
		    }
*/	
		    return errNote;
		}
	    

	}
}	
/*
 * GenericReportRunner.java
 *
 * Created on March 4, 2003, 9:24 AM
 */

package com.cleanwise.service.api.process.operations;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.EmailClientHome;
import com.cleanwise.service.api.session.Report;
import com.cleanwise.service.api.session.ReportHome;
import com.cleanwise.service.api.util.JNDINames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.apps.ApplicationsEmailTool;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.apps.*;
import com.cleanwise.service.api.reporting.ReportWriterUtil;

import org.apache.log4j.Logger;

/**
 * Process designed to produce a report that can then
 * be transmitted to a user.  This program is desinged to be the scheduled interface for the
 * reporting framework.
 */
public class GenericReportRunnerOperations {
    private static final Logger log = Logger.getLogger(GenericReportRunnerOperations.class);
    public static String P_REPORT_NAME = "name";
    public static String P_REPORT_CATEGORY = "category";
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
    

     /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public GenericReportRunnerOperations() {
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
    	
    private ArrayList<ReportCriteria> getReportsToRun(Map parameters) throws Exception {
    	if (parameters == null || parameters.isEmpty()) {
    		return null;
    	}
    	ArrayList<ReportCriteria> reportsToRun = new ArrayList<ReportCriteria>();
    	Set<String> reportNames = Utility.commaDelimitedListToSet((String)parameters.get(this.P_REPORT_NAME));
    	log.info("getReportsToRun() ===> reportNames:" + reportNames);
    	String category = (String)parameters.get(this.P_REPORT_CATEGORY);
  
    	String fileTransferHost = (String)parameters.get(FTP_PARAM.FILE_TRANSFER_HOST);
        String fileTransferUser = (String)parameters.get(FTP_PARAM.FILE_TRANSFER_USER);
        String fileTransferPass = (String)parameters.get(FTP_PARAM.FILE_TRANSFER_PASS);
        String fileTransferMode = (String)parameters.get(FTP_PARAM.FILE_TRANSFER_MODE);
        int fileTransferPort = (Utility.isSet(fileTransferHost))? 0 : parseIntValue((String)parameters.get(FTP_PARAM.FILE_TRANSFER_PORT), FTP_PARAM.FILE_TRANSFER_PORT);
        boolean fileTransferOverwriteFile  = Utility.isTrue((String)parameters.get(FTP_PARAM.FILE_TRANSFER_OVERWRITE_FILE), false );
  
        String fileFormat = (String)parameters.get(FTP_PARAM.FILE_FORMAT);
        String sendFileName = (String)parameters.get(FTP_PARAM.SEND_FILE_NAME);
        String message = (String)parameters.get(EMAIL_PARAM.MESSAGE);
   
        boolean importanceHigh = parseBooleanValue((String)parameters.get(EMAIL_PARAM.IMPORTANCE_HIGH),EMAIL_PARAM.IMPORTANCE_HIGH);
        boolean sendOnlyIfData = parseBooleanValue((String)parameters.get(EMAIL_PARAM.SEND_ONLY_IF_DATA),EMAIL_PARAM.SEND_ONLY_IF_DATA);
  
        ArrayList emailRecipients = new ArrayList();
        String recipients = (String)parameters.get(EMAIL_PARAM.RECIPIENT);
        if (Utility.isSet(recipients)) {
            emailRecipients.add((String)parameters.get(EMAIL_PARAM.RECIPIENT));
        }
    	log.info("getReportsToRun() ===> emailRecipients :"+ emailRecipients  );
 
        HashMap reportParams = new HashMap();
       	List<String> nonReportParams = getNonReportParams(); 
    	//log.info("getReportsToRun() ===> nonReportParams :"+ nonReportParams  );
    	Iterator it = parameters.keySet().iterator();
        while (it.hasNext()) {
    		String key = (String)it.next();
    		if (!nonReportParams.contains(key)){
    	    	//log.info("getReportsToRun() ===>Report parameters key="+key + ", value="+ parameters.get(key) );
            	reportParams.put(key,processParam((String)parameters.get(key)));
    		}
           
    	} 	
    	log.info("getReportsToRun() ===> reportParams:" + reportParams);
    	for (String name : reportNames){
	        ReportCriteria crit = new ReportCriteria(category,name, reportParams,message,emailRecipients,importanceHigh,sendOnlyIfData);
	        crit.setFileTransferHost(fileTransferHost);
	        crit.setFileTransferUser(fileTransferUser);
	        crit.setFileTransferPass(fileTransferPass);
	        crit.setFileTransferPort(fileTransferPort);
	        crit.setFileTransferMode(fileTransferMode);
	        crit.setFileFormat(fileFormat);
	        crit.setSendFileName(sendFileName);
	        crit.setFileTransferOverwriteFile(fileTransferOverwriteFile);
	        reportsToRun.add(crit);
    	}
    	return reportsToRun;
    }
    //adds days, subtracts days etc from the specified date
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
    
    //processes the string for filter data
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
    
//    public void runAndDeliverReports()
    public void runAndDeliverReports(ArrayList<ReportCriteria> reportsToRun)
    throws Exception {
    	log.info("runAndDeliverReports() ====> BEGIN." );
    	Report reportEjb = APIAccess.getAPIAccess().getReportAPI();
        EmailClient emailClientEjb = APIAccess.getAPIAccess().getEmailClientAPI();

    	for(int i=0;i<reportsToRun.size();i++){
            ReportCriteria report = (ReportCriteria) reportsToRun.get(i);
        	log.info("runAndDeliverReports() ====> report=" + report.getName() );
            try{
                File tmp;
                if(Utility.isSet(report.getSendFileName())){
                    String tmpDir = System.getProperty("java.io.tmpdir");
                    tmp = new java.io.File(tmpDir,report.getSendFileName());
                    if(tmp.exists()){
                        tmp.delete();
                    }
                    tmp.createNewFile();
                }else{
                    tmp = java.io.File.createTempFile(report.getReportFileName(),report.getFileFormat());
                }
                tmp.deleteOnExit();
                FileOutputStream out = new FileOutputStream(tmp);
              	log.info("runAndDeliverReports() ====> tmp file =" + tmp.getAbsolutePath() );                
//                GenericReportResultViewVector results = ReportWritter.getReportDataMulti(mReportEjb, report.getCategory(), report.getName(), report.getParameters());
        		GenericReportResultViewVector results = reportEjb.processGenericReportMulti(report.getCategory(), report.getName(), report.getParameters());
                log.info("runAndDeliverReports() ====> results.size() =" + results.size() );                
                boolean hasData = false;
                Iterator it = results.iterator();
                while(it.hasNext()){
                    GenericReportResultView result = (GenericReportResultView) it.next();
                    log.info("runAndDeliverReports() ====> result.getTable() =" + result.getTable() );                
                    if ((result.getTable() != null && result.getTable().size() > 0) || (result.getRawOutput() != null && result.getRawOutput().length > 0)){
                        hasData = true;
                    }else if(report.isSendOnlyIfData()){
                        it.remove();
                    }
                }
                boolean sendReport = true;
                if(report.isSendOnlyIfData()&& !hasData){
                    sendReport = false;
                }
            	log.info("runAndDeliverReports() ====> sendReport = " + sendReport);
                if(sendReport){
                    ReportWriterUtil.writeReport(results, out, report.getFileFormat());
                    out.flush();
                    out.close();

                    //First handle sending email
                    sendEmail(report,tmp,hasData);
                            
                    //now look to send fileTransfer informtaion
                    sendFTP(report,tmp);
                }
            	log.info("runAndDeliverReports() ====> END." );
            }catch(Exception e){
            //    e.printStackTrace();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                
                String errEmail;
                try{
                    errEmail = emailClientEjb.getDefaultEmailAddress();
                }catch(Exception e2){
                    errEmail = System.getProperty("user.name");
                }
                if(errEmail != null && errEmail.length() > 0){
                    ApplicationsEmailTool.sendEmail(errEmail, report.getName() + " FAILED TO RUN", "Please Contact Support\n"+sw.toString());
                }else{
                    System.err.println(report.getName() + " FAILED TO RUN");
                }
                throw new RuntimeException(" FAILED TO RUN/DELIVER REPORT :" + report.getName() + " => " + e.getMessage() );
              
            }
        }
    }
    
    /**
     *If the fileTransfer information is set then sends report to fileTransfer server
     */
    private void sendFTP(ReportCriteria report,File file) throws Exception{
        if(!Utility.isSet(report.getFileTransferHost())){
            return;
        }
        String remoteFileName = file.getName();
        if(Utility.isSet(report.getSendFileName())){
            remoteFileName = report.getSendFileName();
        }
        FileTransferClient client;
        String fileTransferMode = report.getFileTransferMode();
        if ( "active".equalsIgnoreCase(fileTransferMode)) {
            client = new FileTransferClient(FileTransferClient.FTP_ACTIVE);
        }else if ( "sfileTransfer".equalsIgnoreCase(fileTransferMode)) {
            client = new FileTransferClient(FileTransferClient.SFTP);
        }else{
            client = new FileTransferClient(FileTransferClient.FTP_PASIVE);
        }
        client.connect(report.getFileTransferHost(), report.getFileTransferPort(), report.getFileTransferUser(), report.getFileTransferPass());
        client.setTransferTypeAsBinaryType();
        client.put(file.getAbsolutePath(), remoteFileName, !report.getFileTransferOverwriteFile() ,null); 
    }
    
    /**
     *If the email address is set then send an email message
     */
    private void sendEmail(ReportCriteria report,File file, boolean sendAttachment) throws Exception{
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
    
    public void execute (Map parameters)  throws Exception {
    	String show = "\n****************************************************************************\n";
        show += "GenericReportRunnerOperations : " + " exec at " + new Date() + " \n";
        show += "parameters: \n";
        Set keys = parameters.keySet();
        for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
          String key = (String) iter.next();
          String param= (String)parameters.get(key);
          show += key + " is " + param + "; \n";
        }
        show += "****************************************************************************\n";
        log.info(show);
       
        Properties props;
        java.util.Date now = new java.util.Date();
   	
        try{
        	
            runAndDeliverReports(getReportsToRun(parameters));
        }catch(Exception e){
            log.info("Problems running reports.");
            e.printStackTrace();
            throw new Exception("Problems to run and delivery report :" + e.getMessage());
            //return;
        }
    }
    
    private List<String> getNonReportParams (){
	      List<String> nonReportParams = new ArrayList<String>();
	      
	      nonReportParams.add(COMMON_PARAM.SMTP_HOST);
	      nonReportParams.add(COMMON_PARAM.TR_PROTOCOL); 
	      nonReportParams.add(FTP_PARAM.FILE_TRANSFER_HOST); 
	      nonReportParams.add(FTP_PARAM.FILE_TRANSFER_USER); 
	      nonReportParams.add(FTP_PARAM.FILE_TRANSFER_PASS); 
	      nonReportParams.add(FTP_PARAM.FILE_TRANSFER_MODE); 
	      nonReportParams.add(FTP_PARAM.FILE_TRANSFER_PORT); 
	      nonReportParams.add(FTP_PARAM.FILE_TRANSFER_OVERWRITE_FILE); 
	      nonReportParams.add(FTP_PARAM.FILE_FORMAT); 
	      nonReportParams.add(FTP_PARAM.SEND_FILE_NAME); 
	      nonReportParams.add(EMAIL_PARAM.RECIPIENT); 
	      nonReportParams.add(EMAIL_PARAM.MESSAGE); 
	      nonReportParams.add(EMAIL_PARAM.SEND_ONLY_IF_DATA); 
	      nonReportParams.add(EMAIL_PARAM.IMPORTANCE_HIGH); 
	      nonReportParams.add(P_REPORT_NAME); 
	      nonReportParams.add(P_REPORT_CATEGORY); 


	      return nonReportParams;
 }

    /**
     *Holds a single report to run, and all the extranious data associated with the report
     */
    private class ReportCriteria {
        ReportCriteria(String pCategory, String pName, Map pParameters,String pMessage,
        List pEmailRecipients,boolean pImportanceHigh,boolean pSendOnlyIfData){
            category = pCategory;
            name = pName;
            parameters = pParameters;
            message = pMessage;
            emailRecipients = pEmailRecipients;
            importanceHigh = pImportanceHigh;
            sendOnlyIfData = pSendOnlyIfData;
            if(category == null){
                throw new NullPointerException("category cannot be null");
            }
            if(name == null){
                throw new NullPointerException("name cannot be null");
            }
            if(parameters == null){
                throw new NullPointerException("parameters cannot be null");
            }
            if(message == null){
                message = getName();
            }
            /*if(emailRecipients == null || emailRecipients.size() == 0){
                throw new NullPointerException("must have at least 1 emailRecipients");
            }*/
        }
        
        private String fileTransferHost, mFileFormat;
        public String getFileTransferHost(){return fileTransferHost;}
        public void setFileTransferHost(String pVal){fileTransferHost=pVal;}
        
        public String getFileFormat() {
			if (null == mFileFormat || mFileFormat.length() == 0 ) {
				// the default format for reports.
				mFileFormat = ".xls";
			}
			if ( !mFileFormat.startsWith(".")) mFileFormat = "." + mFileFormat;
			return mFileFormat;
		}

		public void setFileFormat(String pVal) {
			mFileFormat = pVal;
		}	
        
        private String fileTransferUser;
        public String getFileTransferUser(){return fileTransferUser;}
        public void setFileTransferUser(String pVal){fileTransferUser=pVal;}
        
        private String fileTransferPass;
        public String getFileTransferPass(){return fileTransferPass;}
        public void setFileTransferPass(String pVal){fileTransferPass=pVal;}
        
        private String fileTransferMode;
        public String getFileTransferMode(){return fileTransferMode;}
        public void setFileTransferMode(String pVal){fileTransferMode=pVal;}
        
        private int fileTransferPort;
        public int getFileTransferPort(){return fileTransferPort;}
        public void setFileTransferPort(int pVal){fileTransferPort=pVal;}
        
        private String sendFileName;
        public String getSendFileName(){return sendFileName;}
        
        public void setSendFileName(String pVal){
        	if(pVal == null){
                    sendFileName=pVal;
                    return;
                }
        	GregorianCalendar tnow = new GregorianCalendar();
        	String tstampNow = tnow.get(GregorianCalendar.YEAR) + "-" 
        	+ (tnow.get(GregorianCalendar.MONTH) + 1)
        	+ "-" + tnow.get(GregorianCalendar.DAY_OF_MONTH)
        	+ "-" + tnow.get(GregorianCalendar.HOUR_OF_DAY) 
        	+ tnow.get(GregorianCalendar.MINUTE) 
        	+ tnow.get(GregorianCalendar.SECOND);
        	
        	sendFileName=pVal;
        	sendFileName = sendFileName.replaceAll("timestamp", tstampNow);
        	}
        
        private boolean fileTransferOverwriteFile;
        public boolean getFileTransferOverwriteFile(){return fileTransferOverwriteFile;}
        public void setFileTransferOverwriteFile(boolean pVal){fileTransferOverwriteFile=pVal;}
        
        
        /** Holds value of property name. */
        private String name;
        
        /** Holds value of property category. */
        private String category;
        
        /** Holds value of property parameters. */
        private Map parameters;
        
        /** Holds value of property emailRecipients. */
        private List emailRecipients;
        
        /** Holds value of property attachmentName. */
        private String message;
        
        /** Holds value of property sendOnlyIfData. */
        private boolean sendOnlyIfData;
        
        /** Holds value of property importanceHigh. */
        private boolean importanceHigh;
        
        /** Getter for property name.
         * @return Value of property name.
         *
         */
        public String getName() {
            return this.name;
        }
        public String getReportFileName() {
        	// Strip out spaces.
        	String tmp = this.name.replaceAll(" ", "_");
            return tmp;
        }
        
        /** Getter for property category.
         * @return Value of property category.
         *
         */
        public String getCategory() {
            return this.category;
        }
        
        /** Getter for property parameters.
         * @return Value of property parameters.
         *
         */
        public Map getParameters() {
            return this.parameters;
        }
        
        /** Getter for property emailRecipients.
         * @return Value of property emailRecipients.
         *
         */
        public List getEmailRecipients() {
            return this.emailRecipients;
        }
        
        
        /** Getter for property attachmentName.
         * @return Value of property attachmentName.
         *
         */
        public String getMessage() {
            return this.message;
        }
        
        /** Getter for property sendOnlyIfData.
         * @return Value of property sendOnlyIfData.
         *
         */
        public boolean isSendOnlyIfData() {
            return this.sendOnlyIfData;
        }
        
        /** Getter for property importanceHigh.
         * @return Value of property importanceHigh.
         *
         */
        public boolean isImportanceHigh() {
            return this.importanceHigh;
        }
        
    }
    
}

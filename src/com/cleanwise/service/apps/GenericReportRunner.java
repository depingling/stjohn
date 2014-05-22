/*
 * GenericReportRunner.java
 *
 * Created on March 4, 2003, 9:24 AM
 */

package com.cleanwise.service.apps;
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

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.EmailClientHome;
import com.cleanwise.service.api.session.Report;
import com.cleanwise.service.api.session.ReportHome;
import com.cleanwise.service.api.util.JNDINames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ReportWritter;

import org.apache.log4j.Logger;

/**
 * Main program designed to interface with the ejb container to produce a report that can then
 * be transmitted to a user.  This program is desinged to be the scheduled interface for the
 * reporting framework.
 * @author  bstevens
 */
public class GenericReportRunner {
    private static final Logger log = Logger.getLogger(GenericReportRunner.class);
    Report mReportEjb;
    EmailClient mEmailClientEjb;
    ArrayList reportsToRun = new ArrayList();
    Properties props;
    java.util.Date now = new java.util.Date();
    
    /**
     *Sets up the class as an EJB Client.
     */
    private void setUp() throws Exception {
        // Check for a properties file command option.
        String propFileName = System.getProperty("conf");
        if ( null == propFileName ) {
            propFileName = "installation.properties";
        }
        props = new Properties();
        java.io.File f = new java.io.File(propFileName);
        props.load(new FileInputStream(propFileName) );
        InitialContext jndiContext = new InitialContext(props);
        Object ref;
        
        ReportHome lReportHome;
        // Get a reference to the Integration Services Bean
        ref = jndiContext.lookup(JNDINames.REPORT_EJBHOME);
        lReportHome = (ReportHome)
        PortableRemoteObject.narrow(ref, ReportHome.class);
        mReportEjb = lReportHome.create();
        
        EmailClientHome lEmailHome;
        // Get a reference to the Integration Services Bean
        ref = jndiContext.lookup(JNDINames.EMAIL_CLIENT_EJBHOME);
        lEmailHome = (EmailClientHome)
        PortableRemoteObject.narrow(ref, EmailClientHome.class);
        mEmailClientEjb = lEmailHome.create();
    }
    
    private boolean parseBooleanValue(String pValue, String pConfigSetting){
        boolean returnVal = true;
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
        return returnVal;
    }
    
    /** Creates a new instance of GenericReportRunner */
    public GenericReportRunner(FileInputStream fis)
    throws Exception{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(fis);
        
        //run through the file getting the email config
        NodeList emailList = document.getElementsByTagName("system-properties");
        for(int j=0,len2=emailList.getLength();j<len2;j++){
            Node emailConfigNode = emailList.item(j);
            NodeList children = emailConfigNode.getChildNodes();
            for(int i=0,len=children.getLength();i<len;i++){
                Node n = children.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE){
                    String key = n.getNodeName();
                    String value = n.getChildNodes().item(0).getNodeValue();
                    System.setProperty(key,value);
                }
            }
        }
        //run through all the reports and get their configuration
        NodeList reportList = document.getElementsByTagName("report");
        for(int j=0,len2=reportList.getLength();j<len2;j++){
            Node reportNode = reportList.item(j);
            String name = null;
            String category = null;
            String fileTransferHost = null;
            String fileTransferUser = null;
            String fileTransferPass = null;
            String fileTransferMode = null;
            String fileFormat = null;
            int fileTransferPort = 0;
            String sendFileName = null;
            boolean fileTransferOverwriteFile  = false;
            String message = null;
            boolean importanceHigh = false;
            boolean sendOnlyIfData = false;
            ArrayList emailRecipients = new ArrayList();
            HashMap params = new HashMap();
            NodeList children = reportNode.getChildNodes();
            for(int i=0,len=children.getLength();i<len;i++){
                Node n = children.item(i);
                if (n.getNodeName().equalsIgnoreCase("name")){
                    name = n.getChildNodes().item(0).getNodeValue();
                }else if (n.getNodeName().equalsIgnoreCase("category")){
                    category = n.getChildNodes().item(0).getNodeValue();
                }else if (n.getNodeName().equalsIgnoreCase("fileTransferHost")){
                    fileTransferHost = n.getChildNodes().item(0).getNodeValue();
                }else if (n.getNodeName().equalsIgnoreCase("fileTransferUser")){
                    fileTransferUser = n.getChildNodes().item(0).getNodeValue();
                }else if (n.getNodeName().equalsIgnoreCase("fileFormat")){
                    fileFormat = n.getChildNodes().item(0).getNodeValue();
                }else if (n.getNodeName().equalsIgnoreCase("fileTransferPass")){
                    fileTransferPass = n.getChildNodes().item(0).getNodeValue();
                 }else if (n.getNodeName().equalsIgnoreCase("fileTransferMode")){
                    fileTransferMode = n.getChildNodes().item(0).getNodeValue();
                }else if (n.getNodeName().equalsIgnoreCase("fileTransferPort")){
                    String fileTransferPortS = n.getChildNodes().item(0).getNodeValue();
                    if(fileTransferPortS != null && fileTransferPortS.trim().length() > 0){
                        try{
                            fileTransferPort = Integer.parseInt(fileTransferPortS);
                        }catch(Exception e){
                            throw new Exception("FileTransferPort passed in was not a number ("+fileTransferPortS+")");
                        }
                    }
                }else if (n.getNodeName().equalsIgnoreCase("sendFileName")){
                    sendFileName = n.getChildNodes().item(0).getNodeValue();
                }else if (n.getNodeName().equalsIgnoreCase("fileTransferOverwriteFile")){
                	String value = n.getChildNodes().item(0).getNodeValue();
                	fileTransferOverwriteFile  = Utility.isTrue(value, fileTransferOverwriteFile );
                }else if (n.getNodeName().equalsIgnoreCase("emailRecipient")){
                    emailRecipients.add(n.getChildNodes().item(0).getNodeValue());
                }else if (n.getNodeName().equalsIgnoreCase("email-message")){
                    message = n.getChildNodes().item(0).getNodeValue();
                }else if (n.getNodeName().equalsIgnoreCase("importance-high")){
                    String value = n.getChildNodes().item(0).getNodeValue();
                    importanceHigh = parseBooleanValue(value,n.getNodeName());
                }else if (n.getNodeName().equalsIgnoreCase("send-only-if-data")){
                    String value = n.getChildNodes().item(0).getNodeValue();
                    sendOnlyIfData = parseBooleanValue(value,n.getNodeName());
                }else if (n.getNodeName().equalsIgnoreCase("param")){
                    String lParam = n.getAttributes().getNamedItem("name").getNodeValue();
                    String lValue = "";
                    if(n.getChildNodes().item(0) != null){
                        lValue = processParam(n.getChildNodes().item(0).getNodeValue());
                    }
                    params.put(lParam,lValue);
                }else if (n.getNodeType() == Node.ELEMENT_NODE){
                    throw new Exception("Invalid Element: " + n.getNodeName());
                }
            }
            ReportCriteria crit = new ReportCriteria(category,name, params,message,emailRecipients,importanceHigh,sendOnlyIfData);
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
    
    public void runAndDeliverReports()
    throws Exception {
        for(int i=0;i<reportsToRun.size();i++){
            ReportCriteria report = (ReportCriteria) reportsToRun.get(i);
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
                GenericReportResultViewVector results = ReportWritter.getReportDataMulti(mReportEjb, report.getCategory(), report.getName(), report.getParameters());
                boolean hasData = false;
                Iterator it = results.iterator();
                while(it.hasNext()){
                    GenericReportResultView result = (GenericReportResultView) it.next();
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
                if(sendReport){
                    ReportWritter.writeReport(results, out, report.getFileFormat());
                    out.flush();
                    out.close();

                    //First handle sending email
                    sendEmail(report,tmp,hasData);
                            
                    //now look to send fileTransfer informtaion
                    sendFTP(report,tmp);
                }
            }catch(Exception e){
                e.printStackTrace();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                
                String errEmail;
                try{
                    errEmail = mEmailClientEjb.getDefaultEmailAddress();
                }catch(Exception e2){
                    errEmail = System.getProperty("user.name");
                }
                if(errEmail != null && errEmail.length() > 0){
                    ApplicationsEmailTool.sendEmail(errEmail, report.getName() + " FAILED TO RUN", "Please Contact Support\n"+sw.toString());
                }else{
                    System.err.println(report.getName() + " FAILED TO RUN");
                }
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
        if(params.containsKey("SUBJECT_DATE")){
        	subject = report.getName() +" "+ (String)params.get("SUBJECT_DATE");
        }else{
        	subject = report.getName() +" "+ now.toString();
        }
        String message;
        String fromAddr=null;
        
        if(params.containsKey("FROM_ADDR")){
        	fromAddr = (String)params.get("FROM_ADDR");
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
    
    public static void main(String[] args){
        String fileName = null;
        if (args.length == 1){
            fileName=args[0];
        }else{
            fileName = System.getProperty("ReportDef");
        }
        if(fileName == null){
            log.info("You must specify a report definition file:");
            log.info("java "+GenericReportRunner.class.getName() + " <reportDefFile>");
            log.info("or");
            log.info("java "+GenericReportRunner.class.getName() + " -DReportDef=<reportDefFile>");
            return;
        }
        
        FileInputStream fis = null;
        File file = new File(fileName);
        try{
            fis = new FileInputStream(file);
        }catch(Exception e){
            log.info("Problems reading in specified report definition file: "+file.getAbsolutePath());
            log.info(e.getMessage());
            return;
        }
        
        GenericReportRunner runner;
        try{
            runner = new GenericReportRunner(fis);
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
        
        try{
            runner.setUp();
        }catch(Exception e){
            log.info("Problems communicating with the EJB container.");
            e.printStackTrace();
            return;
        }
        
        try{
            runner.runAndDeliverReports();
        }catch(Exception e){
            log.info("Problems running reports.");
            e.printStackTrace();
            return;
        }
        
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

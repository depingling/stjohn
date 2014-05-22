package com.cleanwise.service.api.process.operations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import org.quartz.JobExecutionException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.Interchange;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.Attachment;
import com.cleanwise.service.api.value.EmailSearchTerm;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventProcessView;
import com.cleanwise.service.api.value.EventPropertyData;
import com.cleanwise.service.api.value.EventPropertyDataVector;
import com.cleanwise.service.api.value.InboundData;
import com.cleanwise.service.api.value.InboundEmailData;
import com.cleanwise.service.api.value.ProcessData;
import com.cleanwise.service.apps.ApplicationsEmailTool;
import com.cleanwise.service.apps.GetFile;
import com.cleanwise.service.apps.dataexchange.Translator;
import com.cleanwise.service.apps.quartz.PGPDecrypt;

public class GetFtpJobOperations {

  private static final Logger log = Logger.getLogger(GetFtpJobOperations.class);

  public static String P_HOSTNAME = "fromhost";
  public static String P_PORT = "port";
  public static String P_FROM_DIR = "fromdir";
  public static String P_TOFILE = "tofile";
  public static String P_TODIR = "todir";
  public static String P_TRANSFER_TYPE = "transfer_type";
  public static String P_FILE_NAME = "filename";
  public static String P_NO_LISTING = "noListing";
  public static String P_NO_SUBDIR = "noSubDirs";
  public static String P_ASKNOW_LEDGE_DIR = "acknowledgedir";
  public static String P_IGNORE_EMPTY_FILE = "ignoreEmptyFile";
  public static String P_EXCEPTION_ON_FILE_EXISTS = "exceptionOnFileExists";
  public static String P_INDIVIDUAL_TRANSACTION = "individualTransaction";
  public static String P_PARTNER_KEY = "partnerKey";
  public static String P_PGP_CREDENTIAL = "pgpcredential";
  public static String P_EMAIL_SUBJECT = "emailsubject";
  public static String P_EMAIL_TEXT = "emailtext";
  public static String P_EMAIL_FROM = "emailfrom";
  public static String P_FTP_MODE = "ftpmode";
  public static String P_REMOVE_FILE = "removefile";
  public static String P_PASSWORD = "password";
  public static String P_USERNAME = "username";
  public static String P_EMAIL_PROTOCOL = "emailprotocol";
  public static String P_CHUNK_SIZE = "ChunkSize";

  public static String P_CHAIN_SCHEME = "chainscheme" ;
  public static String P_CHAIN_SCHEME_FILE_LIST = "chainschemefilelist" ;
  public static String P_PATTERN = "pattern" ;

  public static final String P_INBOUND_EMAIL_SUBJECT = "inbound-email-subject";
  public static final String P_INBOUND_EMAIL_FROM = "inbound-email-from";

  public static String EMAIL_INLINE = "email-inline";
  public static String EMAIL_ATTACHMENT = "email-attachment";

  private static String space = " ";
  private int prevEventId  = 0;


    /**
   * Quartz requires a public empty constructor so that the
   * scheduler can instantiate the class whenever it needs.
   */
  public GetFtpJobOperations() {
  }

  /**
   * <p>
   * Called by the <code>{@link org.quartz.Scheduler}</code> when a
   * <code>{@link org.quartz.Trigger}</code> fires that is associated with
   * the <code>Job</code>.
   * </p>
   *
   * @throws JobExecutionException
   *             if there is an exception while executing the job.
   */


    public void execute (String ftpmode, String fromhost , String port , String fromdir,
                         String username, String password, String todir, String tofile, String transfer_type,
                         String partnerKey, String pgpcredential, String ChunkSize,String removefile,
                         String emailsubject, String emailtext, String emailfrom,String emailprotocol,
                         String filename, String noListing, String noSubDirs,
                         String acknowledgedir, String ignoreEmptyFile, String exceptionOnFileExists, String individualTransaction,
                         String chainScheme, String chainSchemeFileList , String pattern, String waitingOnFailMediatorClass, Integer currentEventId)
        throws Exception {

      String show = "";
      HashMap propertyMap = new HashMap();
      if (Utility.isSet(fromhost)){
        propertyMap.put(P_HOSTNAME, fromhost);
      }
      if (Utility.isSet(port)){
        propertyMap.put(P_PORT, port);
      }
      if (Utility.isSet(fromdir)){
        propertyMap.put(P_FROM_DIR, fromdir);
      }
      if (Utility.isSet(username)){
        propertyMap.put(this.P_USERNAME, username);
      }
      if (Utility.isSet(password)){
        propertyMap.put(this.P_PASSWORD, password);
      }
      if (Utility.isSet(tofile) ) {
        propertyMap.put(P_TOFILE, tofile);
      }
      if (Utility.isSet(todir) ) {
        propertyMap.put(this.P_TODIR, todir);
      }
      if (Utility.isSet(transfer_type) ) {
        propertyMap.put(this.P_TRANSFER_TYPE, transfer_type);
      }
      if (Utility.isSet(filename) ) {
        propertyMap.put(this.P_FILE_NAME, filename);
      }
      if (Utility.isSet(noListing) ) {
        propertyMap.put(this.P_NO_LISTING, noListing);
      }
      if (Utility.isSet(noSubDirs) ) {
        propertyMap.put(this.P_NO_SUBDIR, noSubDirs);
      }
      if (Utility.isSet(removefile) ) {
        propertyMap.put(this.P_REMOVE_FILE, removefile);
      }
      if (Utility.isSet(acknowledgedir) ) {
        propertyMap.put(this.P_ASKNOW_LEDGE_DIR, acknowledgedir);
      }
      if (Utility.isSet(ignoreEmptyFile) ) {
        propertyMap.put(this.P_IGNORE_EMPTY_FILE, ignoreEmptyFile);
      }
      if (Utility.isSet(exceptionOnFileExists) ) {
        propertyMap.put(this.P_EXCEPTION_ON_FILE_EXISTS, exceptionOnFileExists);
      }
      if (Utility.isSet(individualTransaction) ) {
        propertyMap.put(this.P_INDIVIDUAL_TRANSACTION, individualTransaction);
      }

     if (Utility.isSet(partnerKey)){
         propertyMap.put(P_PARTNER_KEY, partnerKey);
       }
       if (Utility.isSet(pgpcredential)){
         propertyMap.put(P_PGP_CREDENTIAL, pgpcredential);
       }
       if (Utility.isSet(ChunkSize)){
         propertyMap.put(P_CHUNK_SIZE, ChunkSize);
       }

       if (Utility.isSet(emailsubject)){
         propertyMap.put(this.P_EMAIL_SUBJECT, emailsubject);
       }
       if (Utility.isSet(emailtext)){
         propertyMap.put(this.P_EMAIL_TEXT, emailtext);
       }
       if (Utility.isSet(emailfrom)){
         propertyMap.put(this.P_EMAIL_FROM, emailfrom);
       }
       if (Utility.isSet(emailprotocol)){
         propertyMap.put(this.P_EMAIL_PROTOCOL, emailprotocol);
       }

       if (Utility.isSet(chainScheme)){
         propertyMap.put(this.P_CHAIN_SCHEME, chainScheme);
       } else {
         propertyMap.put(this.P_CHAIN_SCHEME, "none");
       }
       if (Utility.isSet(chainSchemeFileList)){
         propertyMap.put(this.P_CHAIN_SCHEME_FILE_LIST, chainSchemeFileList);
       }
       if (Utility.isSet(pattern)){
         propertyMap.put(this.P_PATTERN, pattern);
       } else {
         propertyMap.put(this.P_PATTERN, "*");
       }
       if (Utility.isSet(waitingOnFailMediatorClass)){
           propertyMap.put(Event.WATING_ON_FAIL_MEDIATOR_CLASS, waitingOnFailMediatorClass);
         }  
       if (currentEventId != null)
    	   propertyMap.put(Event.EVENT_ID, currentEventId);

       show += "\n****************************************************************************\n";
     show += "GetFtpJobOperations : " + " exec at " + new Date() + " \n";
     show += "parameters: \n";
     Set keys = propertyMap.keySet();
     for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
       String key = (String) iter.next();
       Object param= propertyMap.get(key);
       show += key + " is " + param + "; \n";
     }
     show += "****************************************************************************\n";
     log.info(show);
     execute(ftpmode, propertyMap);
    }

    private void execute(String ftpmode, Map jobDetail) throws Exception {

//           JobDataMap dataMap = jobDetail.getJobDataMap();
//           String ftpmode = dataMap.getString(P_FTP_MODE);

           if (EMAIL_ATTACHMENT.equals(ftpmode) || EMAIL_INLINE.equals(ftpmode)) {
               emailExec(jobDetail,ftpmode);
           } else {
               ftpExec(jobDetail);
           }
       }

   private void emailExec(Map propertyMap, String mode) throws Exception {

      String pHost = (String)propertyMap.get(this.P_HOSTNAME);
      String pUser = (String)propertyMap.get(this.P_USERNAME);
      String pPassword = (String)propertyMap.get(this.P_PASSWORD);
      String pProtocol = (String)propertyMap.get(this.P_EMAIL_PROTOCOL);
      String pRemoveFile = (String)propertyMap.get(this.P_REMOVE_FILE);
      String pEmailText  = (String)propertyMap.get(this.P_EMAIL_TEXT);
      String pSubject    = (String)propertyMap.get(this.P_EMAIL_SUBJECT);
      String pFrom       = (String)propertyMap.get(this.P_EMAIL_FROM);
      String pUrl        = null;
      String pToFile = (String)propertyMap.get(this.P_TOFILE);

      String pPartnerKey =(String) propertyMap.get(this.P_PARTNER_KEY);
      String pPGPCredential = (String)propertyMap.get(this.P_PGP_CREDENTIAL);
      String pChunkSize	  = (String)propertyMap.get(this.P_CHUNK_SIZE);

      String pChainScheme =(String) propertyMap.get(this.P_CHAIN_SCHEME);
      String pChainSchemeFileList = (String)propertyMap.get(this.P_CHAIN_SCHEME_FILE_LIST);
      String pPattern = (String) propertyMap.get(this.P_PATTERN);

      if(Utility.isSet(pChainScheme) && !"none".equalsIgnoreCase(pChainScheme)){
          throw new Exception("Chain schem not supported for email attachments");
      }
//       Map<String, String> propertyMap = new HashMap<String, String>();
        ArrayList<String> errors = new ArrayList<String>();

 //       JobDataMap dataMap = jobDetail.getJobDataMap();

 //       String jobName = jobDetail.getFullName();
  //      String trName = jobDetail.getFullName();

        try {
            if (!Utility.isSet(pHost)) {
                errors.add(P_HOSTNAME + " a required field.");
            }

            if (!Utility.isSet(pUser)) {
                 errors.add(P_USERNAME + " a required field.");
            }

            if (!Utility.isSet(pPassword)) {
                 errors.add(P_PASSWORD + " a required field.");
            }

            if (!Utility.isSet(pToFile) && EMAIL_INLINE.equals(mode)) {
                 errors.add(P_TOFILE + " a required field.");
            }

            if (errors.size() > 0) {
                String errMessage = "";
                for (String error : errors) {
                    errMessage += error + space;
                }
                throw new Exception(errMessage);
            }

            EmailSearchTerm searchTerm = null;

            if (Utility.isSet(pEmailText) || Utility.isSet(pSubject) || Utility.isSet(pFrom)) {

                searchTerm = new EmailSearchTerm();

                if (Utility.isSet(pFrom)) {
                    log.info("Adding Filter from: ["+pFrom+"]");
                    searchTerm.setFrom(Utility.parseStringToArray(pFrom, ","));
                }

                if (Utility.isSet(pSubject)) {
                    log.info("Adding Filter subject: ["+pSubject+"]");
                    searchTerm.setSubject(pSubject);
                }

                if (Utility.isSet(pEmailText)) {
                    log.info("Adding Filter email text: ["+pEmailText+"]");
                    searchTerm.setText(pEmailText);
                }

            }

            ApplicationsEmailTool eTool = new ApplicationsEmailTool();
            ArrayList<InboundEmailData> emails = eTool.readEmails(pProtocol, pHost, pUser, pPassword, searchTerm, Utility.isTrue(pRemoveFile));
            HashMap emailHM = new HashMap();
            HashSet fileNames = new HashSet();
            if (EMAIL_ATTACHMENT.equals(mode)) {
                for (InboundEmailData email : emails) {

                    ArrayList<Attachment> attachments = email.getAttachments();
                    if(attachments != null){
                        log.info("Found "+attachments.size() +" attachments to save.");
                        if(attachments.size() == 0){
                                throw new Exception("Found email with no attachments (0) to save. For email: "+email.getSubject());
                        }
                    }else{
                        throw new Exception("Found email with no attachments null to save. For email: "+email.getSubject());
                    }
                    for (Attachment attachment : attachments) {
                        if (attachment.getData() != null && attachment.getData().length > 0) {
                           String toFile=null;
                           if (!Utility.isSet(pToFile)) {
                                toFile = attachment.getFileName();
                            }
                            fileNames.add(toFile);
                            propertyMap.put(P_INBOUND_EMAIL_SUBJECT, Utility.strNN(email.getSubject()));
                            propertyMap.put(P_INBOUND_EMAIL_FROM, Utility.strNN(email.getFrom()));
                            if(!Utility.isSet(attachment.getFileName())){
                               attachment.setFileName("unknown");
                            }
                            String[] fs = new String[1];
                            fs[0] = attachment.getFileName();
                            fs = filterByPattern(fs, pPattern);
                            if(fs.length != 0){
                              ByteArrayOutputStream baos = new ByteArrayOutputStream();
                              baos.write(attachment.getData(),0,attachment.getData().length);
                              log("Saving email recieved file to database: " +attachment.getFileName());
                              saveToDb(baos, attachment.getFileName(), pUrl, pPartnerKey, pPGPCredential, propertyMap);
                              saveToFile(baos, attachment.getFileName());

                              baos.flush();
                              baos.close();
                            }

                        }

                    }
                }
            }else if (EMAIL_INLINE.equals(mode)) {
                for (InboundEmailData email : emails) {
                    String text = email.getText();
                    if (Utility.isSet(text)) {

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        baos.write(text.getBytes());

                        propertyMap.put(P_INBOUND_EMAIL_SUBJECT, Utility.strNN(email.getSubject()));
                        propertyMap.put(P_INBOUND_EMAIL_FROM, Utility.strNN(email.getFrom()));

                        saveToDb(baos, pToFile, pUrl, pPartnerKey, pPGPCredential, propertyMap);
                        saveToFile(baos, pToFile);

                        baos.flush();
                        baos.close();
                    }
                }
            } else {
                throw new Exception("Mode  " + mode + " Not Supported.");
            }

        } catch (Exception e) {
            log.error("Error reading email.", e);
            throw new Exception("Error reading email.", e);
        }
    }

    public void ftpExec(Map propertyMap) throws Exception {
    String pHost = (String)propertyMap.get(this.P_HOSTNAME);
    String pPort = (String)propertyMap.get(P_PORT);
    String pFromDir = (String)propertyMap.get(this.P_FROM_DIR);
    String pToFile = (String)propertyMap.get(this.P_TOFILE);

    String pPartnerKey =(String) propertyMap.get(this.P_PARTNER_KEY);
    String pPGPCredential = (String)propertyMap.get(this.P_PGP_CREDENTIAL);
    String pChainScheme =(String) propertyMap.get(this.P_CHAIN_SCHEME);
    String pChainSchemeFileList = (String)propertyMap.get(this.P_CHAIN_SCHEME_FILE_LIST);
    String pPattern = (String)propertyMap.get(this.P_PATTERN);
    String pUrl = null;

    GetFile getClient = null;

    try {
    	pUrl = pHost;
    	if (pPort != null)
    		pUrl += ":" + pPort;
    	if (pFromDir != null)
    		pUrl += "/" + pFromDir;

    	pUrl += "/";
      log(" ftpExec -> BEGIN ");

    	getClient = new GetFile();
    	getClient.setProperties(propertyMap);
    	getClient.connect();
      log(" ftpExec -> process : connected ");

    	String fs [] = getClient.getFileNames();
       log(" ftpExec -> process : fs.length = " + fs.length);

       // filtering by patterns
        fs = filterByPattern(fs, pPattern);

        if ( !("none".equalsIgnoreCase(pChainScheme))) {
          fs = sortByChainScheme(fs, pChainSchemeFileList);
          log(" ftpExec -> process : SORTED : fs.length = " + fs.length);
        }
    	for ( int fidx = 0; fidx < fs.length; fidx++) {
    		try{
    			String toFile;
    			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    			if (pToFile != null && !"".equals(pToFile)) {
    				toFile = new String(pToFile);
    				if (fidx > 0){
    					toFile += Integer.toString(fidx);
    				}
    			} else {
    				toFile = fs[fidx];
    			}
        log(" ftpExec -> process : Saving email recieved file to database: " +toFile);
    			if (getClient.getFile(fs[fidx], outputStream)){
                              log(" ftpExec -> process : fs[fidx] = " + fs[fidx] + " / toFile = " + toFile);
	    			saveToDb(outputStream, toFile, pUrl, pPartnerKey, pPGPCredential, propertyMap);
	    			saveToFile(outputStream, toFile);
    			}
    		}catch(Exception e ) {
    			log.error(e.getMessage(),e);
    			if(!isNoFilesError(e)){
                              e.printStackTrace();
    				//if something is really wrong re-throw the error
    				throw e;
    			}
    		}
    	}
    } catch (Exception e) {
    	log.error("Error getting file.", e);
    	throw new Exception("Error getting file.", e);
    }finally{
    	if (getClient != null){
    		try {
    			getClient.closeSession();
    		} catch (IOException e) {
    			e.printStackTrace();
    			throw new Exception("Error getting file.", e);
    		}
    	}
    }

  }

  private static boolean isNoFilesError(Exception e){
      String s = e.getMessage();
          if (s!=null && (s.indexOf("No files found") >= 0 || s.indexOf("No such file") >= 0 )) {
              return true;
          }else{
              return false;
          }
  }

  private void saveToDb(ByteArrayOutputStream outputStream, String toFilename, String pUrl, String pPartnerKey, String pPGPCredential, Map propertyMap) throws Exception{
    byte[] out = outputStream.toByteArray();
    InboundData inbound = InboundData.createValue();
    inbound.setFileName(toFilename);
    inbound.setUrl(pUrl);
    inbound.setPartnerKey(pPartnerKey);
    Interchange interchangeEjb = APIAccess.getAPIAccess().getInterchangeAPI();
    byte[] eventDataContent;
    if (pPGPCredential != null && !"".equals(pPGPCredential)) {
      PGPDecrypt decryptFactory = new PGPDecrypt();
      eventDataContent = decryptFactory.decrypt(out, pPGPCredential);
      interchangeEjb.saveInboundData(inbound, out, eventDataContent);
    } else {
      eventDataContent = out;
      interchangeEjb.saveInboundData(inbound, new byte[0], out);
    }
    Event eventEjb = APIAccess.getAPIAccess().getEventAPI();
    ProcessData process = APIAccess.getAPIAccess().getProcessAPI()
			.getProcessByName(RefCodeNames.PROCESS_NAMES.PROCESS_INBOUND_TRANSACTION);
    
    String sourceURI = "XX";//TODO:
    java.net.URI requestedUri = new java.net.URI(sourceURI);
   boolean chainManagmentFl = doChainManagmentScheme(toFilename, propertyMap);
    EventData eventData = Utility.createEventDataForProcess();
    int currentEventId = propertyMap.get(Event.EVENT_ID) == null ? 0 : ((Integer)propertyMap.get(Event.EVENT_ID)).intValue();
    EventProcessView epv = new EventProcessView(eventData, new EventPropertyDataVector(), currentEventId);
    epv.getProperties().add(Utility.createEventPropertyData("process_id",
             Event.PROPERTY_PROCESS_TEMPLATE_ID,
             new Integer(process.getProcessId()),
             1));
    epv.getProperties().add(Utility.createEventPropertyData("fileName",
            Event.PROCESS_VARIABLE,
            toFilename,
            1));
    epv.getProperties().add(Utility.createEventPropertyData("partnerKey",
            Event.PROCESS_VARIABLE,
            pPartnerKey,
            2));
    epv.getProperties().add(Utility.createEventPropertyData("dataContents",
            Event.PROCESS_VARIABLE,
            eventDataContent,
            3));
    epv.getProperties().add(Utility.createEventPropertyData("sourceURI",
             Event.PROCESS_VARIABLE, requestedUri,
             4));
    epv.getProperties().add(Utility.createEventPropertyData("propertyMap",
             Event.PROCESS_VARIABLE, propertyMap,
             5));
    if (prevEventId != 0 && chainManagmentFl){
      epv.getProperties().add(Utility.createEventPropertyData(Event.WATING_ON,
          Event.PROCESS_VARIABLE, prevEventId,
          6));
      log.info("Looking for WATING_ON_FAIL_MEDIATOR_CLASS property...");
      String pChainSchemeFailClass =(String) propertyMap.get(Event.WATING_ON_FAIL_MEDIATOR_CLASS);
      log.info("WATING_ON_FAIL_MEDIATOR_CLASS now set to: "+pChainSchemeFailClass);
      if(Utility.isSet(pChainSchemeFailClass)){
    	  log.info("Setting in position 7");
    	  epv.getProperties().add(Utility.createEventPropertyData(Event.WATING_ON_FAIL_MEDIATOR_CLASS,
    	          Event.PROCESS_VARIABLE,pChainSchemeFailClass,
    	          7));
      }
      log("Added '"+Event.WATING_ON+"' property :: prevEventId = "+ prevEventId);
    }
    EventProcessView newEvent = eventEjb.addEventProcess(epv, "GetFtpJobOperations");
    if(newEvent == null || newEvent.getEventData() == null){
    	log.fatal("Added event was null for file: "+toFilename);
    }else{
    	log.info("Added event id "+newEvent.getEventData().getEventId());
    }
    if (chainManagmentFl){
      prevEventId = newEvent.getEventData().getEventId();
    } else {
      prevEventId = 0;
    }
  }

  private void saveToFile(ByteArrayOutputStream outputStream,
		String toFilename) {
	File tmpFile = null;
    try{
    	String fileName = "GetFtpJob"+toFilename;
    	String fileExt="txt";
    	int index = toFilename.lastIndexOf('.');
    	if (index > 0)
    		fileExt = toFilename.substring(index);

    	tmpFile = File.createTempFile(fileName, fileExt,Translator.getIntegrationFileLogDirectory(true));
    	log.info("Writing output stream to tempfile: "+tmpFile.getAbsolutePath());
    	FileOutputStream fout = new FileOutputStream(tmpFile);
    	IOUtilities.copyStream(new ByteArrayInputStream(outputStream.toByteArray()), fout);
    	log.info("Writing output stream to tempfile: "+tmpFile.getAbsolutePath()+" success");
    }catch(Exception e){
    	log.fatal("handleBuilderCleanup.Writing output stream to tempfile failed.  " +
    			"This means that there is somthing wrong with the file system.  " +
    			"Processing continued normally as long as database processing is ok, " +
    			"but the secondary logging of files to the hard disk failed. ",e);
    }
  }

  private String[] filterByPattern (String[] files, String pPatternList){
     log(" Filtering BEGIN ");
     if (!Utility.isSet(pPatternList) || "*".equals(pPatternList)){
       return files;
     }
     ArrayList filteredFiles = new ArrayList();
     String[] patterns = pPatternList.split(",");
     for (int i = 0; i < patterns.length; i++) {
       for (int j = 0; j < files.length; j++) {
         log(" Filtering ->  patterns[i] / files[j] = " + patterns[i] + " / " +files[j] );
         if (files[j].contains(patterns[i])){
           filteredFiles.add(files[j]);
         }
       }
     }
     log(" Filtering -> filteredFiles :" + filteredFiles.toString() );
     return (String[]) filteredFiles.toArray(new String[0]) ;
   }

  private String[] sortByChainScheme (String[] files, String pChainSchemaFileList){
    log(" Sorting BEGIN ");
    if (!Utility.isSet(pChainSchemaFileList)){
      return files;
    }
    ArrayList orderedFiles = new ArrayList();
    String[] chainSchemaFilePatterns = pChainSchemaFileList.split(",");
    for (int i = 0; i < chainSchemaFilePatterns.length; i++) {
      for (int j = 0; j < files.length; j++) {
        log(" Sorting ->  chainSchemaFilePatterns[i] / files[j] = " + chainSchemaFilePatterns[i] + " / " +files[j] );
        if (files[j].contains(chainSchemaFilePatterns[i])){
          orderedFiles.add(files[j]);
        }
      }
    }
    log(" Sorting -> orderedFiles :" + orderedFiles.toString() );
    // append other files which are not in chainSchemaFileList
    ArrayList others = new ArrayList();
    for (int i = 0; i < files.length; i++) {
      if (!orderedFiles.contains(files[i])){
        others.add(files[i]);
      }
    }
    log(" Sorting -> others :" + others.toString() );
    orderedFiles.addAll(others);

    log(" Sorting END -> ALL orderedFiles :" + orderedFiles.toString() );
    return (String[]) orderedFiles.toArray(new String[0]) ;
  }
  private boolean doChainManagmentScheme(String filename, Map propertyMap) {
    String pChainScheme =(String) propertyMap.get(this.P_CHAIN_SCHEME);
    String pChainSchemeFileList = (String)propertyMap.get(this.P_CHAIN_SCHEME_FILE_LIST);
    boolean flag = false;
    if ( Utility.isSet(pChainScheme) &&
         Utility.isSet(pChainSchemeFileList) &&
        !("none".equalsIgnoreCase(pChainScheme)) ){
      String[] chainSchemaFilePatterns = pChainSchemeFileList.split(",");
//log(" .doChainManagmentScheme :: process -> chainSchemaFilePatterns.length = " + chainSchemaFilePatterns.length);
      for (int i = 0; i < chainSchemaFilePatterns.length; i++) {
//        log(" .doChainManagmentScheme :: process -> filename / pattern = " + filename.toLowerCase() + " / " + chainSchemaFilePatterns[i].toLowerCase() );
        if (filename.toLowerCase().contains(chainSchemaFilePatterns[i].toLowerCase())){
          flag = true;
          break;
        }
      }
    }
    log(" .doChainManagmentScheme :: process -> flag = " + flag);
    return flag;
  }
  private void log(String logMessage) {
      log.info(logMessage);
  }

}



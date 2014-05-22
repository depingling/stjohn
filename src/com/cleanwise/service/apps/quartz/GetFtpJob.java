package com.cleanwise.service.apps.quartz;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.Interchange;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.Attachment;
import com.cleanwise.service.api.value.EmailSearchTerm;
import com.cleanwise.service.api.value.EventProcessView;
import com.cleanwise.service.api.value.InboundData;
import com.cleanwise.service.api.value.InboundEmailData;
import com.cleanwise.service.api.value.ProcessData;
import com.cleanwise.service.apps.ApplicationsEmailTool;
import com.cleanwise.service.apps.GetFile;
import com.cleanwise.service.apps.dataexchange.Translator;

public class GetFtpJob extends EventJobImpl {

  private static final Logger log = Logger.getLogger(GetFtpJob.class);

  public static String P_HOSTNAME = "fromhost";
  public static String P_PORT = "port";
  public static String P_FROM_DIR = "fromdir";
  public static String P_TOFILE = "tofile";
  public static String P_PARTNER_KEY = "partnerkey";
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

  public static final String P_INBOUND_EMAIL_SUBJECT = "inbound-email-subject";
  public static final String P_INBOUND_EMAIL_FROM = "inbound-email-from";

  public static String EMAIL_INLINE = "email-inline";
  public static String EMAIL_ATTACHMENT = "email-attachment";

  private static String space = " ";


    /**
   * Quartz requires a public empty constructor so that the
   * scheduler can instantiate the class whenever it needs.
   */
  public GetFtpJob() {
  }  

    public void execute(JobDetail jobDetail) throws JobExecutionException {

        JobDataMap dataMap = jobDetail.getJobDataMap();
        String ftpmode = dataMap.getString(P_FTP_MODE);

        if (EMAIL_ATTACHMENT.equals(ftpmode) || EMAIL_INLINE.equals(ftpmode)) {
            emailExec(jobDetail,ftpmode);
        } else {
            ftpExec(jobDetail);
        }
    }

    private void emailExec(JobDetail jobDetail, String mode) throws JobExecutionException {

        String pHost          = null;
        String pUser          = null;
        String pPassword      = null;
        String pProtocol      = null;
        String pRemoveFile    = null;
        String pEmailText     = null;
        String pSubject       = null;
        String pFrom          = null;
        String pUrl           = null;
        String pToFile        = null;
        String pPartnerKey    = null;
        String pPGPCredential = null;
        String pChunkSize	  = null;

        Map<String, String> propertyMap = new HashMap<String, String>();
        ArrayList<String> errors = new ArrayList<String>();

        JobDataMap dataMap = jobDetail.getJobDataMap();

        String show = "";
        String jobName = jobDetail.getFullName();
        String trName = jobDetail.getFullName();

        show += "\n****************************************************************************\n";
        show += "GetFtpJob: " + jobName + "(" + trName + ") exec at " + new Date() + "\n";
        try {
            if (dataMap != null && dataMap.size() != 0) {

                show += "parameters:\n";
                String[] keys = dataMap.getKeys();

                for (String key : keys) {
                    if (P_HOSTNAME.equalsIgnoreCase(key)) {
                        pHost = dataMap.getString(key);
                    } else if (P_PARTNER_KEY.equalsIgnoreCase(key)) {
                        pPartnerKey = dataMap.getString(key);
                    } else if (P_PGP_CREDENTIAL.equalsIgnoreCase(key)) {
                        pPGPCredential = dataMap.getString(key);
                    } else if (P_USERNAME.equalsIgnoreCase(key)) {
                        pUser = dataMap.getString(key);
                    } else if (P_TOFILE.equalsIgnoreCase(key)) {
                        pToFile = dataMap.getString(key);
                    } else if (P_EMAIL_PROTOCOL.equalsIgnoreCase(key)) {
                        pProtocol = dataMap.getString(key);
                    } else if (P_REMOVE_FILE.equalsIgnoreCase(key)) {
                        pRemoveFile = dataMap.getString(key);
                    } else if (P_EMAIL_SUBJECT.equalsIgnoreCase(key)) {
                        pSubject = dataMap.getString(key);
                    } else if (P_EMAIL_TEXT.equalsIgnoreCase(key)) {
                        pEmailText = dataMap.getString(key);
                    } else if (P_EMAIL_FROM.equalsIgnoreCase(key)) {
                        pFrom = dataMap.getString(key);
                    } else if (P_PASSWORD.equalsIgnoreCase(key)) {
                        pPassword = dataMap.getString(key);
                    } else if (P_CHUNK_SIZE.equalsIgnoreCase(key)) {
                        pChunkSize = dataMap.getString(key);
                    }
                    propertyMap.put(key, dataMap.getString(key));
                    show += key + " is " + dataMap.getString(key) + "\n";
                }
            }
            show += "****************************************************************************\n";

            log.info(show);

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
                    searchTerm.setFrom(Utility.parseStringToArray(pFrom, ","));
                }

                if (Utility.isSet(pSubject)) {
                    searchTerm.setSubject(pSubject);
                }

                if (Utility.isSet(pEmailText)) {
                    searchTerm.setText(pEmailText);
                }

            }

            ApplicationsEmailTool eTool = new ApplicationsEmailTool();
            ArrayList<InboundEmailData> emails = eTool.readEmails(pProtocol, pHost, pUser, pPassword, searchTerm, Utility.isTrue(pRemoveFile));

            if (EMAIL_ATTACHMENT.equals(mode)) {
                for (InboundEmailData email : emails) {

                    ArrayList<Attachment> attachments = email.getAttachments();
                    if(attachments != null){
                    	log.info("Found "+attachments.size() +" attachments to save.");
                    	if(attachments.size() == 0){
                    		throw new JobExecutionException("Found email with no attachments (0) to save. For email: "+email.getSubject());
                    	}
                    }else{
                    	throw new JobExecutionException("Found email with no attachments null to save. For email: "+email.getSubject());
                    }
                    for (Attachment attachment : attachments) {
                        if (attachment.getData() != null && attachment.getData().length > 0) {

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            baos.write(attachment.getData());

                            if (!Utility.isSet(pToFile)) {
                                pToFile = attachment.getFileName();
                            }

                            log.info("File name is: "+pToFile);
                            propertyMap.put(P_INBOUND_EMAIL_SUBJECT, Utility.strNN(email.getSubject()));
                            propertyMap.put(P_INBOUND_EMAIL_FROM, Utility.strNN(email.getFrom()));

                            log.info("Saving email recieved file to database: " +pToFile);
                            saveToDb(baos, pToFile, pUrl, pPartnerKey, pPGPCredential, propertyMap);
                            saveToFile(baos, pToFile);

                            baos.flush();
                            baos.close();
                        }

                    }
                }
            } else if (EMAIL_INLINE.equals(mode)) {
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
            throw new JobExecutionException("Error reading email.", e);
        }
    }

    public void ftpExec(JobDetail jobDetail) throws JobExecutionException {
    String pHost = null;
    String pPort = null;
    String pFromDir = null;
    String pUrl = null;
    String pToFile = null;
    String pPartnerKey = null;
    String pPGPCredential = null;
    String pChunkSize = null;

    String show = "";
    String jobName = jobDetail.getFullName();
    String trName = jobDetail.getFullName();
    show += "\n****************************************************************************\n";
    show += "GetFtpJob: " + jobName + "(" + trName + ") exec at " + new Date() + "\n";
    JobDataMap dataMap = jobDetail.getJobDataMap();
    Map<String, String> propertyMap = new HashMap<String, String>();

    if (dataMap != null && dataMap.size() != 0) {
      show += "parameters:\n";
      String[] keys = dataMap.getKeys();

      for ( int i = 0; i < keys.length; i++) {
        if (P_HOSTNAME.equalsIgnoreCase(keys[i])){
        	pHost = dataMap.getString(keys[i]);

        } else if (P_PORT.equalsIgnoreCase(keys[i])){
        	pPort = dataMap.getString(keys[i]);

        } else if (P_FROM_DIR.equalsIgnoreCase(keys[i])){
        	pFromDir = dataMap.getString(keys[i]);

        } else if (P_PARTNER_KEY.equalsIgnoreCase(keys[i])){
          pPartnerKey = dataMap.getString(keys[i]);

        } else if (P_PGP_CREDENTIAL.equalsIgnoreCase(keys[i])){
          pPGPCredential = dataMap.getString(keys[i]);

        } else if (P_TOFILE.equalsIgnoreCase(keys[i])){
          pToFile = dataMap.getString(keys[i]);
          
        } else if (P_CHUNK_SIZE.equalsIgnoreCase(keys[i])){
          pChunkSize = dataMap.getString(keys[i]);
        }
              
        propertyMap.put(keys[i], dataMap.getString(keys[i]));
        show += keys[i] + " is " + dataMap.getString(keys[i]) + "\n";
      }
    }
    show += "****************************************************************************\n";

    log.info(show);

    GetFile getClient = null;

    try {
    	pUrl = pHost;
    	if (pPort != null)
    		pUrl += ":" + pPort;
    	if (pFromDir != null)
    		pUrl += "/" + pFromDir;

    	pUrl += "/";

    	getClient = new GetFile();
    	getClient.setProperties(propertyMap);
    	getClient.connect();

    	String fs [] = getClient.getFileNames();

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

    			if (getClient.getFile(fs[fidx], outputStream)){
	    			saveToDb(outputStream, toFile, pUrl, pPartnerKey, pPGPCredential, propertyMap);
	    			saveToFile(outputStream, toFile);
    			}
    		}catch(Exception e ) {
    			log.error(e.getMessage(),e);
    			if(!isNoFilesError(e)){
    				//if something is really wrong re-throw the error
    				throw e;
    			}
    		}
    	}
    } catch (Exception e) {
    	log.error("Error getting file.", e);
    	throw new JobExecutionException("Error getting file.", e);
    }finally{
    	if (getClient != null){
    		try {
    			getClient.closeSession();
    		} catch (IOException e) {
    			e.printStackTrace();
    			throw new JobExecutionException("Error getting file.", e);
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
    
    EventProcessView epv = Utility.createEventProcessView(process.getProcessId(), priorityOverride, subProcessPriority);
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
    EventProcessView newEvent = eventEjb.addEventProcess(epv, "GetFtpJob");
    if(newEvent == null || newEvent.getEventData() == null){
    	log.fatal("Added event was null for file: "+toFilename);
    }else{
    	log.info("Added event id "+newEvent.getEventData().getEventId());
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
    	outputStream.writeTo(fout);
    	log.info("Writing output stream to tempfile: "+tmpFile.getAbsolutePath()+" success");
    }catch(Exception e){
    	log.fatal("handleBuilderCleanup.Writing output stream to tempfile failed.  " +
    			"This means that there is somthing wrong with the file system.  " +
    			"Processing continued normally as long as database processing is ok, " +
    			"but the secondary logging of files to the hard disk failed. ",e);
    }
  }

}

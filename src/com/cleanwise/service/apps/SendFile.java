package com.cleanwise.service.apps;

import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.Utility;

public class SendFile {
      protected Logger log = Logger.getLogger(this.getClass());
	private static final String className = "SendFile";
    FileTransferClient ftpc=null;

	private String vUsername,vPassword,vFilename,vToFilename,vFtpMode,vXfertype,vTodir,vHostname,vPort,vResponseCheck;
	private boolean vFlatten;
	private boolean vMakeDirs;
	private boolean vIndividualTransaction;
	private boolean vExceptionOnFileExists = true;
	private int optPort = 0;
	private byte[] vDataContents;
	private Map properties;
	private String vEmailRecipients = null;

    public static void main(String args [])
    throws Exception {
        SendFile sender = new SendFile();
        sender.setProperties(System.getProperties());
        //sender.send();
    }

    public void setProperties(Map propertyMap){
    	properties = propertyMap;
    	vUsername = (String)properties.get("username");
    	vPassword = (String)properties.get("password");
    	vFilename = (String)properties.get("filename");
    	vToFilename = (String)properties.get("tofilename");
    	vFtpMode = (String)properties.get("ftpmode");
    	vXfertype = (String)properties.get("transfer_type");
    	vTodir = (String)properties.get("todir");
    	vHostname = (String)properties.get("tohost");
    	vPort = (String)properties.get("port");
    	vResponseCheck = (String)properties.get("responseCheck");

    	vFlatten  = "true".equals((String)properties.get("flatten"));
    	vMakeDirs = "true".equals((String)properties.get("makeDirs"));
    	vIndividualTransaction = "true".equals((String)properties.get("individualTransaction"));
    	if (Utility.isSet((String)properties.get("exceptionOnFileExists")) && !"true".equals((String)properties.get("exceptionOnFileExists")))
    		vExceptionOnFileExists = false;
    	vEmailRecipients = (String)properties.get("emailRecipients");

    	if ( vXfertype == null ) {
    		vXfertype = "ascii";
    	}

    	if ( null != vPort && vPort.length() > 0 ) {
    		optPort = Integer.parseInt(vPort);
    	}
    	if ( vToFilename == null ) {
    		vToFilename = vFilename;
    	}

    	log.info("Username: " + vUsername +
    			" password: " + vPassword +
    			" filename: " + vFilename +
    			" transfer_type: " + vXfertype +
    			" tofilename: " + vToFilename +
    			" todir: " + vTodir +
    			" hostname: " + vHostname +
    			" responseCheck: " + vResponseCheck);

    }

	protected void connect() throws Exception{
		ftpc = new FileTransferClient(FileTransferClient.getConnectionType(vFtpMode));
			if ( "soap".equalsIgnoreCase(vFtpMode)) {
                ftpc.setSoapOperation((String)properties.get("soapoperation"));
                ftpc.setSoapNamespace((String)properties.get("soapnamespace"));
                ftpc.setSoapFilenameParam((String)properties.get("soapfilenameparam"));
                HashMap soapParams = new HashMap();
                int ct = 1;
                while(true){
                    String name = (String)properties.get("soapParam"+ct+"Name");
                    if(name == null || name.length() == 0){
                        break;
                    }
                    String value = (String)properties.get("soapParam"+ct+"Val");

                    soapParams.put(name, value);
                    ct++;
                }
                ftpc.setSoapParams(soapParams);
            }else if("mq".equalsIgnoreCase(vFtpMode)) {
				ftpc.setMQManager((String)properties.get("MQManager"));
				ftpc.setMQLocalname((String)properties.get("MQLocalname"));
				ftpc.setMQChannel((String)properties.get("MQChannel"));
			}

            ftpc.connect(vHostname, optPort, vUsername, vPassword);

			//setting transfer type and dir
            if ( vXfertype.equals("binary") ) {
                ftpc.setTransferTypeAsBinaryType();
            }

            if (vTodir != null && vTodir.length() > 0) {
                if(vMakeDirs){ftpc.mkdirs(vTodir);}
                ftpc.chdir(vTodir);
            }
	}

	public void send(byte[] dataContents) throws Exception{
		vDataContents = dataContents;
		send();
	}

    protected synchronized void send() throws Exception{
        String kUsage = "Usage: ftpsend " +
        " -Dtohost=<destination hostname> " +
	    " -Dport=<21 (default)> " +
        " -Dfilename=<local filename> " +
        " -Dtransfer_type=<binary | ascii (default) > " +
        " -Dtofilename=<remote filename> " +
        " -Dusername=<ftp username> " +
        " -Dpassword=<ftp password> " +
        " -Dftpmode=<active | passive (default) | sftp | soap | http | mq | email-inline (host becomes to email)> " +
        " -Dsoapoperation=<soap operation if using soap> "+
        " -Dsoapnamespace=<soap namespace if using soap> "+
        " -Dsoapfilenameparam=<soap name of parameter to submit the file as (ex: filename, xmldata, etc)> "+
        " -DsoapParamXName=<SOAP PARAM NAME where x is an incremental counter (currently only 2 params are supported> "+
        " -DsoapParamXVal=<SOAP PARAM VALUE where x is an incremental counter (currently only 2 params are supported> "+
        " -Dtodir=<destination directory>" +
        " -DmakeDirs=<true | false (default)" +
        " -Dflatten=<true | false (default) >" +
		" -DindividualTransaction=send files one per connection (send file, disconnect, send file...) <true | false (default)>";

        if ( "email-inline".equalsIgnoreCase(vFtpMode) || "email_inline".equalsIgnoreCase(vFtpMode) || "email-attachment".equalsIgnoreCase(vFtpMode)) {
        	if ( vHostname == null ){
        		throw new Exception(kUsage);
        	}
        }else if ( vUsername == null ||
	        vPassword == null ||
	        vFilename == null ||
	        vHostname == null) {
        	throw new Exception(kUsage);
        }

        try {
            log.info("opening a connection to: " + vHostname);
            log.info("connected to           : " + vHostname );
            log.info("loging in as           : " + vUsername);
            connect();


            log.info("START sending file: " + vFilename +" to directory: " + vTodir );

            if (vDataContents !=null){

            	InputStream response = ftpc.put(vDataContents, vToFilename, vExceptionOnFileExists, vEmailRecipients, vResponseCheck);

            	//InputStream response = ftpc.put(vDataContents, vToFilename, vExceptionOnFileExists, vEmailRecipients);

            	try{
                    handleResponse(response, new FileInputStream(vToFilename));
                }catch(Exception e){}
                log.info("DONE  sending file: " + vToFilename);
            }
            else{
	            File vFile = new File(vFilename);
	            ArrayList vFiles = new ArrayList();
	            boolean isDir = false;
	            if(vFile.isDirectory()){
	                isDir = true;
	                File[] files = vFile.listFiles();
	                for(int i=0;i<files.length;i++){
	                    vFiles.add(files[i]);
	                }
	            }else{
	                vFiles.add(vFile);
	            }

	            //note vFiles size is volitile as files will be added as new dirs are discovered
	            for(int j=0;j<vFiles.size();j++){
	                if(vIndividualTransaction && j>0){ //j>0 as we don't need to disconnect first time
					     log.info("Disconnecting...");
						 ftpc.closeSession();
						 log.info("Re-Connecting...");
						 connect();
					}
	                vFile = (File)vFiles.get(j);
	                log.info(">>>>>>>>> Processing: "+vFile.getAbsolutePath());
	                if(vFile.isDirectory()){
	                    File[] files = vFile.listFiles();
	                    for(int i=0;i<files.length;i++){
	                        vFiles.add(files[i]);
	                        log.info("adding: " + files[i].getAbsolutePath());
	                    }
	                    continue;
	                }
	                String fileName = vFile.getAbsolutePath();

                    String modFile;
                    if(!isDir){
                        if(vFlatten){
                            modFile = new File(vToFilename).getName();
                        }else{
                            modFile = vToFilename;
                        }
                    }else{
                        if(vFlatten){
                            modFile = new File(fileName).getName();
                        }else{
                            modFile = fileName;
                        }
                    }
					log.info("Putting filename: "+fileName);
                    InputStream response = ftpc.put(fileName, modFile, vExceptionOnFileExists, vEmailRecipients);
                    if ( vFtpMode == null || vFtpMode.equals("passive")) {
                        log.info("Checking file: " + modFile);
                        String fs [] = ftpc.dir(modFile, true);
                        for ( int fidx = 0; fidx < fs.length; fidx++) {
                            log.info("-- remote file [" + fidx + "]: " + fs[fidx]);
                        }
                    } else {
                        log.info("Not checking file (not in passive ftp mode): " + fileName);
                    }
                    try{
                    handleResponse(response, new FileInputStream(modFile));
                    }catch(Exception e){}
                    log.info("DONE  sending file: " + fileName);
	            }
            }
        }finally{
        	if (ftpc != null)
        		ftpc.closeSession();
        }
    }


    protected void  handleResponse(InputStream responseStream, InputStream transportedFile) throws Exception{
        if(responseStream != null){
                log.info("Got repsonse:");
                String response = com.cleanwise.service.api.util.IOUtilities.loadStream(responseStream);
                log.info(response);
        }
    }

}

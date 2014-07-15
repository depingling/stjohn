/*

 * FileTransferClient.java

 *

 * Created on March 29, 2005, 1:01 PM

 */

package com.cleanwise.service.apps;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.*;

import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.util.IgnoredException;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPClientInterface;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.ssh.SSHFTPClient;
import com.enterprisedt.net.ftp.ssl.SSLFTPClient;

/**
 * Abstraction between the varying different types of file transfers that we know
 * about.  Currently supported is sFtp, ftp, Soap (simplistic), MQ, Email.  Not all methods are implemented
 * under the different implementations, but no exception is thrown.  The idea is that
 * client code does not need to know about what is supported and what is not.  Currently
 * there is nothing that needs to break this rule.
 * @author bstevens
 */

public class FileTransferClient {
    static Logger log = Logger.getLogger(FileTransferClient.class);
	static int CONNECTED_COUNT = 0;
	static int CONNECTION_COLOSED_COUNT = 0;
    public static final int FTP_PASIVE = 0;
    public static final int FTP_ACTIVE = 1;
    public static final int SFTP = 2;
    public static final int SOAP = 3;
    public static final int SIMPLE_HTTP_POST = 4;
    public static final int MQ = 5;
    public static final int EMAIL_INLINE = 6; //email with text of file as body of email (not an attachment)s
    public static final int FTPS_IMPLICIT = 7;

    private int connectionType;
    private FTPClientInterface ftp = null;
    private String mHost, mUser, mPass;
    private int mCmdport;

    private String soapOperation; //used when dealing with SOAP transfers
    private String soapNamespace; //used when dealing with SOAP transfers
    private String soapFilenameParam; //used when dealing with SOAP transfers and putting files
    private String dirHistory;

    /** Creates a new instance of FileTransferClient 
     * @param pConnectionType the connection type to use.  See the static types for what is supported (e.g. @see FTP_PASSIVE)
     * */
    public FileTransferClient(int pConnectionType) {
        this.connectionType = pConnectionType;

        switch (connectionType){
            case FTP_PASIVE:
            case FTP_ACTIVE:
            case SFTP:
            case SOAP:
            case SIMPLE_HTTP_POST:
            case MQ:
            case EMAIL_INLINE:
            case FTPS_IMPLICIT:
                break;
            default:
                throw new IllegalArgumentException("wrong type specified");
        }
    }

    /**
     *Connects to the remote host.  Must do this first or subsequent calls will fail.
     *@param host host name or ip to connect to
     *@param user the username to log in as (depending on connection type may be null)
     *@param pass the pass to log in as (depending on connection type may be null)
     */
    public void connect(String host, String user, String pass)
    throws IOException{
        connect(host, 0, user, pass);
    }

    /**
     *Connects to the remote host.  Must do this first or subsequent calls will fail.
     *@param host host name or ip to connect to
     *@param cmdport overrides default command port
     *@param user the username to log in as (depending on connection type may be null)
     *@param pass the pass to log in as (depending on connection type may be null)
     */
    public void connect(String host, int cmdport, String user, String pass) throws IOException{
    	int tryCount = 1;
    	Exception lastException = null;
    	boolean connected = false;

    	for(int tryCt = 0; tryCt < 5; tryCt++) {
	        try {
	        	connectWorker(host,cmdport,user,pass);
	        	connected = true;
	        	break;
		    }catch(Exception e){
		    	lastException = e;
	    		log.info(e.getMessage());
	    		log.info("Could not login, retrying");
	    		closeSession();
	    		try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		    }
    	}
    	if(!connected){
    		log.error("Failed to connect to host - " + host + "\n" + lastException.getMessage());
    		throw new IOException("Failed to connect to host - " + host + "\n" + lastException.getMessage());
    	}
    }
    
    /**
     *Connects to the remote host.  Must do this first or subsequent calls will fail.
     *@param host host name or ip to connect to
     *@param cmdport overrides default command port
     *@param user the username to log in as (depending on connection type may be null)
     *@param pass the pass to log in as (depending on connection type may be null)
     */
    public void oneAttemptConnect(String host, int cmdport, String user, String pass) throws IOException {
    	String errorMessage = "";
    	boolean connected = false;

        try {
            connectWorker(host,cmdport,user,pass);
            connected = true;
        } catch(Exception e) {
            errorMessage = e.getMessage();
            log.info(e.getMessage());
        }
    	if (!connected) {
            log.error("Failed to connect to host - " + host + "\n" + errorMessage);
            throw new IOException("Failed to connect to host - " + host + "\n" + errorMessage);
    	}
    }
    
    /**
     * 
     * @return true if the connection persist
     */
    public boolean checkFtpConnection() {
        boolean connected = false;
        log.info("Checking FTP Connection");
        if (ftp != null) {
            connected = ftp.connected();
        }
        return connected;
    }
    
    /**
     *Does the actual work of connecting to the remote host.
     */
    private void connectWorker(String host, int cmdport, String user, String pass) throws IOException, FTPException{
    	log.info("Calling connect");
    	mHost = host;
    	mUser = user;
    	mPass = pass;
    	mCmdport = cmdport;
    	ftp = null;

        log.info("Connecting...");
    	/*if (CONNECTED_COUNT == 0){
	    	Logger.setLevel(Level.DEBUG);
	    	Logger.addAppender(new FileAppender("ftpClient.log"));
    	}*/

        switch (connectionType){
            case FTP_ACTIVE: //note no break statment is intentional!  ACTIVE and PASSIVE log in the same way
            case FTP_PASIVE:
            	FTPClient fTPClient = new FTPClient();
            	ftp = fTPClient;

            	fTPClient.setRemoteHost(host);
                if ( cmdport > 0 ) {
                    fTPClient.setRemotePort(cmdport);
                }
                fTPClient.setTimeout(50000);// set to 50 second
                
            	log.debug("before ftp.connect() - ");
                fTPClient.connect();

                log.debug("before ftp.login() - ");
                fTPClient.login(user,pass);
                    
                if (connectionType==FTP_ACTIVE){
                	fTPClient.setConnectMode(FTPConnectMode.ACTIVE);
                }
                break;
            case FTPS_IMPLICIT:
            	SSLFTPClient ftpsClient = new SSLFTPClient();
            	ftp = ftpsClient;

                ftpsClient.setConfigFlags(SSLFTPClient.ConfigFlags.DISABLE_SSL_CLOSURE);
                // NOTE: The DISABLE_SSL_CLOSURE flag is included in this example
                // for the sake of compatibility with as wide a range of servers as
                // possible. If possible it should be avoided as it opens the
                // possibility of truncation attacks (i.e. attacks where data is
                // compromised through premature disconnection).

                ftpsClient.setRemoteHost(host);

                // set implicit mode
                ftpsClient.setImplicitFTPS(true);
                ftpsClient.setValidateServer(false);

                // connect to the server
                log.debug("Connecting to server " + host);
                ftpsClient.connect();
                // some servers supporting implicit SSL require
                // this to be called. You may need to comment these
                // lines out
                try {
                	ftpsClient.auth(SSLFTPClient.PROT_PRIVATE);
                    log.debug("auth() succeeded");
                } catch (FTPException ex) {
                	log.debug("auth() not supported or failed" + ex);
                }

                // log in
                log.debug("Logging in with username=" + user + " and password=" + pass);
                ftpsClient.login(user, pass);

                ftpsClient.setConnectMode(FTPConnectMode.PASV);
                break;

            case SFTP:
            	SSHFTPClient sftpClient = new SSHFTPClient();
            	ftp = sftpClient;

                sftpClient.setRemoteHost(host);
                sftpClient.setAuthentication(user, pass);
                sftpClient.getValidator().setHostValidationEnabled(false);
                sftpClient.connect();

                //set our base dir as the directory that we are running out of.
                //the sftp system will otherwise assume the users home directory

                String curDir = System.getProperty("user.dir");

                //sftpClient.lcd(curDir);
                break;

            case SOAP:
            	//nothing to do here
            	break;
            case SIMPLE_HTTP_POST:
            	//nothing to do here
            	break;
            case MQ:
            	//nothing to do here
            	break;
            case EMAIL_INLINE:
            	//nothing to do here
            	break;
        }
    }

    /**
     *Sets the transfer type to be binary.
     */
    public void setTransferTypeAsBinaryType() throws IOException{
    	log.info("Calling setTransferTypeAsBinaryType");
        if(ftp != null){
            try{
                ftp.setType(com.enterprisedt.net.ftp.FTPTransferType.BINARY);
            }catch(FTPException e){
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }
        //nothing to do for sftp

    }

    /**
     *Changes the remote working driectory
     *@param hideStackTrace if failes throws the exception but does not print the underlying cuase.
     */
    private void chdir(String directory, boolean hideStackTrace) throws IOException{
    	log.info("Calling chdir ["+directory+"]");
        if(dirHistory==null) {
            dirHistory = directory;
        } else {
            dirHistory += "/"+directory;
        }

    	try{
    		if(ftp != null){
    			ftp.chdir(directory);
    		}
    	}catch(Exception e){
    		if(!hideStackTrace){
    			e.printStackTrace();
    		}
    		throw new IOException(e.getMessage());
    	}
    }

    /**
     *Changes the remote working driectory
     */
    public void chdir(String directory) throws IOException{
    	log.info("Calling chdir ["+directory+"]");
        chdir(directory, false);
    }

    /**
     *Returns the directory listing matching the mask
     *@param mask the mask to filter by
     */
    public String[] dir(String mask) throws IOException{
    	log.info("Calling dir");
        return dir(mask, false);
    }

    /**
     *Scrubs the passed in array of strings for text that is not really part of the file names, removes blanks etc.
     */
    private String[] scrubSimpleFTPDirListing(String[] sourceList){
        LinkedList scrubbedList = new LinkedList();
        for(int i=0;i<sourceList.length;i++){
            String name = sourceList[i];
            if(name.toLowerCase().indexOf("no such file")<0){
                if(name != null && name.trim().length() > 0){
                    name = name.trim();
                    if(name.endsWith(":")){
                        name = name.substring(0, name.length() - 1);
                    }
                    scrubbedList.add(name);
                }
            }
        }
        String[] toRet = new String[scrubbedList.size()];
        return (String[]) scrubbedList.toArray(toRet);
    }

    /**
     *Returns the directory listing matching the mask
     *@param mask the mask to filter by
     *@param fullListing flag to inidicate wheather the full detail should be returned
     */
    public String[] dir(String mask, boolean fullListing) throws IOException{
    	log.info("Calling dir");
        if(mask == null){
            mask = "*";
        }
        if(ftp != null){
            try{
                return scrubSimpleFTPDirListing(ftp.dir(mask,fullListing));
            }catch(FTPException e){
                throw new IOException(e.getMessage());
            }
        }
        throw new IllegalStateException("Something not initialized correctly");
    }

    /**
     *Down loads the remote file.  Will create the local file.
     *@param localFilename the local file name to save it to
     *@param remoteFileName the file on the host to down load
     */
    public void get(String localFilename, String remoteFileName) throws IOException{
    	log.info("Calling get ["+remoteFileName+"]");
        try{
            File f = new File(localFilename);
            f = f.getParentFile();
            if(f != null){
                f.mkdirs();
            }
        }catch(Exception e){
            log.debug("Error creating parent directory for file: "+localFilename+" continuing anyway.");
            e.printStackTrace();
        }

        log.debug("Checking if dir");
        if(isDirectory(remoteFileName) && false){
            log.debug("Skipping file: "+remoteFileName+" as it is a directory");
            return;
        }

        log.debug("not a dir, continuing");

        if(ftp != null){
            try{
            	ftp.get(localFilename, remoteFileName);
            }catch(FTPException e){
                if(e.getMessage() != null && e.getMessage().indexOf("not a plain file.")>=0){
                    log.debug("Skipping file: "+remoteFileName+" as it is a directory");
                }else{
                    e.printStackTrace();
                    log.error("Failed to get file - " + e.getMessage());
                    throw new IOException(e.getMessage());
                }
            }
        }

        if(connectionType == SOAP){
        	String endpoint = this.mHost;
        	if(dirHistory!= null){
        		endpoint = endpoint+"/"+dirHistory;
        	}

        	String data = SOAPTransferClient.invoke(endpoint, soapNamespace, soapOperation, mUser, mPass,mSoapParams);
                log.info("Soap response:");
                log.info(data);
        	//finally write out the soap xml file

            FileWriter fw = new FileWriter(localFilename);
            fw.write(data);
            fw.flush();
        }

        if(connectionType == SIMPLE_HTTP_POST){
        	String endpoint = this.mHost;
        	if(dirHistory!= null){
        		endpoint = endpoint+"/"+dirHistory;
        	}

                log.info("Sending via SIMPLE_HTTP_POST");
        	String data = SOAPTransferClient.invoke(endpoint, null, "", mUser, mPass,null);
                log.info(data);
        	//finally write out the soap xml file
        	log.debug("Response file="+localFilename);
            FileWriter fw = new FileWriter(localFilename);
            fw.write(data);
            fw.flush();
        }

        if(connectionType == MQ){
        	throw new IllegalStateException("Get not supported for MQ");
        }

        if(connectionType == EMAIL_INLINE){
        	throw new IllegalStateException("Get not supported for EMAIL_INLINE");
        }
    }

    /**
     *Write remote file contents to OutputStream
     */
    public void get(OutputStream outputStream, String remoteFileName) throws IOException{
    	log.info("Calling get OutputStream,String ["+remoteFileName+"]");
    	
    	log.debug("Checking if dir");

        if(isDirectory(remoteFileName) && false){
            log.debug("Skipping file: "+remoteFileName+" as it is a directory");
            return;
        }

        log.debug("not a dir, continuing");

        if(ftp != null){
            try{
                byte[] data = ftp.get(remoteFileName);
                outputStream.write(data);
            }catch(FTPException e){
                if(e.getMessage() != null && e.getMessage().indexOf("not a plain file.")>=0){
                    log.debug("Skipping file: "+remoteFileName+" as it is a directory");
                }else{
                    e.printStackTrace();
                    log.error("Failed to get file - " + e.getMessage());
                    throw new IOException(e.getMessage());
                }
            }
        }
        if(connectionType == SOAP){
        	String endpoint = this.mHost;
        	if(dirHistory!= null){
        		endpoint = endpoint+"/"+dirHistory;
        	}
        	String data = SOAPTransferClient.invoke(endpoint, soapNamespace, soapOperation, mUser, mPass,mSoapParams);
                log.info("Soap response:");
                log.info(data);
        	outputStream.write(data.getBytes());
        }
        if(connectionType == SIMPLE_HTTP_POST){
        	log.info("SIMPLE_HTTP_POST");
        	String endpoint = this.mHost;
        	if(dirHistory!= null){
        		endpoint = endpoint+"/"+dirHistory;
        	}
        	String data = SOAPTransferClient.invoke(endpoint, null, "", mUser, mPass,null);
        	outputStream.write(data.getBytes());
        }
        if(connectionType == MQ){
        	throw new IllegalStateException("Get not supported for MQ");
        }
        if(connectionType == EMAIL_INLINE){
        	throw new IllegalStateException("Get not supported for EMAIL_INLINE");
        }
    }

  /**
     *Deletes the remote file name or directory
     */
    public void delete(String remoteFileName) throws IOException{
    	log.info("Calling delete ["+remoteFileName+"]");
        if(ftp != null){
            try{
                ftp.delete(remoteFileName);
            }catch(FTPException e){
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }
    }

    /**
     *Closes the session, and disconnects from the server
     */
    public void closeSession() throws IOException{
    	log.info("Closing connection");
        if(ftp != null){
            try{
            	if(ftp.connected()){
            		ftp.quitImmediately();
            	}
                CONNECTION_COLOSED_COUNT++ ;
                log.info("CONNECTION_COLOSED_COUNT="+CONNECTION_COLOSED_COUNT);
            }catch(FTPException e){
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }
    }

    /**
     *Returns the current remote directory.  If the connection mode is active then null is returned.
     */
    public String pwd() throws IOException{
    	log.info("Calling pwd");
        if(connectionType == FTP_ACTIVE){
            return null;
        }
        if(ftp != null){
            try{
                return ftp.pwd();
            }catch(FTPException e){
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }
        return null;
    }

    /**
     *Makes any requiered remote directorys.
     */
    public void mkdirs(String remoteDirs) throws IOException{
    	log.info("Calling mkdirs ["+remoteDirs+"]");
        if(remoteDirs.startsWith("/") || remoteDirs.startsWith("\\")){
            log.debug("Moving to absulte path: [/]");
            chdir("/");
        }

        File dirs = new File(remoteDirs);
        Stack dirToCreateList = new Stack();
        while(dirs != null){
            dirToCreateList.push(dirs.getName());
            dirs = dirs.getParentFile();
        }

        while(true){
            String dir = (String) dirToCreateList.peek();
            dir = dir.trim();
            if(dir.equals("..")){
                chdir(dir);
                dirToCreateList.pop();
            }else{
                break;
            }
        }

        int dirsCreated = 0;

        while(!dirToCreateList.empty()){
            String dir = (String) dirToCreateList.pop();
            dir = dir.trim();
            if(!"".equals(dir)){
                dirsCreated++;
                mkdir(dir);
                chdir(dir);
            }
        }
        for(int i=0;i<dirsCreated;i++){
            chdir("..");
        }
    }

    public void mkdir(String remoteDir) throws IOException{
    	log.info("Calling mkdir ["+remoteDir+"]");
        try{
            String[] existing = dir(remoteDir);
            log.debug("Creating directory: "+remoteDir);
            if(!(existing == null || existing.length <= 0)){
                log.debug("dir already exists");
                //dir already exists
                return;
            }
            //secondary check
            try{
                chdir(remoteDir,true);
                chdir("..");
                log.debug("dir already exists");
                //If we can change into it then it exists and return.  This seems to be a bug in the ftp system
                //at least where it will only list directories that have files in them.
                return;
            }catch(Exception e){}
        }catch(IOException e){}

        if(ftp != null){
            try{
                ftp.mkdir(remoteDir);
            }catch(FTPException e){
                log.debug("Could not create directory...assuming it exists");
            }

        }
    }

    /**
     *Returns true if the passed in file is a directory
     */
    public boolean isDirectory(String remoteFileName) throws IOException{
    	log.info("Calling isDirectory ["+remoteFileName+"]");
    	if(connectionType == SOAP){
    		return false;
    	}
    	if(connectionType == SIMPLE_HTTP_POST){
    		return false;
    	}

        if(ftp != null){
            if(connectionType == FTP_ACTIVE){
                return false;
            }

            try{
                chdir(remoteFileName, true);
                chdir("..");
                return true;
            }catch(Exception e){
                //best we can do given the FTPClient has no API to determine if something is a directory or not
            }
        }
        return false;
    }

    public InputStream put(byte[] dataContents, String remoteFile) throws Exception{
    	return put(dataContents, remoteFile, false, null, null);
    }

    /*
     * Puts a file onto a remote server.  If the connection mechanism supports it will return a response input stream.  Soap will, ftp and sftp will not.
     * otherwise returns null.
     */

    public InputStream put(byte[] dataContents, String remoteFile, boolean exceptionOnFileExists, String emailRecipients, String responseCheck) throws Exception{
    	log.info("Calling put ["+remoteFile+"]");
    	if(ftp != null){
            try{
            	if (exceptionOnFileExists && ftp.exists(remoteFile)){
            		if (emailRecipients != null && emailRecipients.length() > 0){
            			sendEmails(emailRecipients, "Failed to ftp file - " + remoteFile,
            					"We were unable to ftp the attached file to your ftp site. " +
            					"Please removed the file from your ftp server for future delivery.", dataContents, remoteFile);
            			throw new IgnoredException("File - " + remoteFile + " already exist on client server. File has been emailed to - " + emailRecipients);
            		}else{
            			throw new IOException("File - " + remoteFile + " already exist on client server.");
            		}
            	}
            	ftp.put(dataContents,remoteFile);
            }catch(FTPException e){
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }else if(connectionType == SOAP){
        	String endpoint = this.mHost;
        	if(dirHistory!= null){
        		endpoint = endpoint+"/"+dirHistory;
        	}

        	String uploadData = new String(dataContents);
        	if(mSoapParams == null){
        		mSoapParams = new HashMap();
        	}
        	mSoapParams.put(getSoapFilenameParam(), uploadData);

        	String ret = SOAPTransferClient.invoke(endpoint, soapNamespace, soapOperation, mUser, mPass, mSoapParams);
                log.info("Soap response:");
                log.info(ret);

                String responseCheckText = ret;

                if(responseCheck!=null && responseCheckText.indexOf(responseCheck) < 0){
                	throw new IOException("FileTransferClient - put() -SOAP- Incorrect response \r\n" + ret);
                }

        	ByteArrayInputStream bais = new ByteArrayInputStream(ret.getBytes());
        	return bais;
        }else if(connectionType == SIMPLE_HTTP_POST){
        	String endpoint = this.mHost;
        	if(dirHistory!= null){
        		endpoint = endpoint+"/"+dirHistory;
        	}

        	String uploadData = new String(dataContents, "UTF-8");
        	HttpClient client = new HttpClient();

        	if (mUser!=null && mUser.trim().length()>0) {
				URL endpointURL = new URL(endpoint);// used to parse out the
													// endpoint
				client.getParams().setAuthenticationPreemptive(true);
				Credentials defaultcreds = new UsernamePasswordCredentials(
						mUser, mPass);
				client.getState().setCredentials(
						new AuthScope(endpointURL.getHost(), endpointURL
								.getPort(), AuthScope.ANY_REALM), defaultcreds);
			}

            PostMethod postMethod = new PostMethod(endpoint);
            postMethod.setRequestBody(uploadData);
            postMethod.setRequestHeader("Content-type","text/xml; charset=UTF-8");
            
            try {
	            int statusCode1 = client.executeMethod(postMethod);
	            String responseText = postMethod.getResponseBodyAsString();
	            String responseCheckText = responseText;
	
	            log.debug("statusLine>>>" + postMethod.getStatusLine());	            
	            responseText = responseText.replaceAll("&lt;","<");
	            responseText = responseText.replaceAll("&gt;",">");
	            log.info(responseText);
	            if (statusCode1 != 200){
	            	throw new IOException("FileTransferClient - put() -SIMPLE_HTTP_POST- failed \r\n" + postMethod.getStatusLine() + "\r\n" + responseText);
	            }
	
	            if(responseCheck != null && responseCheckText.indexOf(responseCheck) < 0){
	            	throw new IOException("FileTransferClient - put() -SIMPLE_HTTP_POST- Incorrect response \r\n" + postMethod.getStatusLine() + "\r\n" + responseText);
	            }
	
	        	ByteArrayInputStream bais = new ByteArrayInputStream(responseText.getBytes());
	        	return bais;
            } finally {
                // Release the connection.
            	postMethod.releaseConnection();
             } 
        /*} else if(connectionType == MQ){
        	try {
      	      // Setup the MQSeries client environment
      	      // Hostname can be either the local hostname or a remote hostname
      	      // Insert the environment variables here:
      		  String qManager     = mMQManager; //"ABFQM1";
      		  String qLocalname   = mMQLocalname; //"POQUEUE";
      		  MQQueueManager qMgr;
      	      MQEnvironment.hostname = this.mHost;
      	      MQEnvironment.channel  = mMQChannel; //"ABFCH1";
			  if(mCmdport > 0){
				MQEnvironment.port     = mCmdport; //1420;
			  }else{
			    MQEnvironment.port = 1420; //some default
			  }

      	      // Create a connection to the queue manager
      	      qMgr = new MQQueueManager(qManager);
      	      // Set up the open options on the queue you wish to open...
      	      // Note. All MQ Options are prefixed with MQC in Java.
      	      int openOptions = MQC.MQOO_INPUT_AS_Q_DEF |
      	                        MQC.MQOO_OUTPUT ;

      	      // Specify the queue that you wish to open, and the open options...
      	      MQQueue qLocal = qMgr.accessQueue(qLocalname,
      	                                        openOptions,
      	                                        null,           // default q manager
      	                                        null,           // no dynamic q name
      	                                        null);          // no alternate user id

      	      // Define a simple MQ message, and write some text in UTF format..
      	      MQMessage messagebuffer = new MQMessage();
      	      messagebuffer.writeString(new String(dataContents));

      	      // specify the message options...
      	      // ... here: accept the defaults, same as MQPMO_Default constant
      	      MQPutMessageOptions pmo = new MQPutMessageOptions();

      	      // put the message on the queue
      	      qLocal.put(messagebuffer,pmo);

      	      log.debug("The message is put");      // Close the queue
      	      // Close the local queue
      	      qLocal.close();
      	      // Disconnect from the queue manager
      	      qMgr.disconnect();
      	    }

      	    // If an error has occured in the above, try to identify what went wrong.
      	    // Was it an MQ error?
      	    catch (MQException ex)
      	    {
      	    	throw new IOException("An MQ error occurred : Completion code " +
      	                         ex.completionCode +
      	                         " Reason code " + ex.reasonCode);
      	    }

      	    // Was it a Java buffer space error?
      	    catch (java.io.IOException ex)
      	    {
      	    	throw new IOException("An error occurred whilst writing to the message buffer: " + ex);
      	    }
      	    */
        }  else if(connectionType == EMAIL_INLINE){
        	try{
        		log.debug("Sending file: "+remoteFile);
	        	String emailBody = new String(dataContents);
	        	ApplicationsEmailTool.sendEmail(this.mHost, remoteFile, emailBody);
        	}catch(Exception e){
        		e.printStackTrace();
        		throw new IOException("FileTransferClient - put() -EMAIL_INLINE- failed \r\n" + e);
        	}
        }

        return null;
    }

    public InputStream put(String localPath, String remoteFile) throws Exception{
    	return put(localPath, remoteFile, true, null);
    }

    public InputStream put(String localPath, String remoteFile, boolean exceptionOnFileExists, String emailRecipients) throws Exception{

    	log.info("Calling put ["+remoteFile+"]");
        if(ftp != null){

            try{
            	if (exceptionOnFileExists && ftp.exists(remoteFile)){
            		if (emailRecipients != null && emailRecipients.length() > 0){
            			sendEmails(emailRecipients, "Failed to ftp file - " + remoteFile,
            					"We were unable to ftp the attached file to your ftp site. " +
            					"Please removed the file from your ftp server for future delivery.", localPath, remoteFile);
            			throw new IgnoredException("File - " + remoteFile + " already exist on client server. File has been emailed to - " + emailRecipients);
            		}else{
            			throw new IOException("File - " + remoteFile + " already exist on client server.");
            		}
            	}
            	ftp.put(localPath,remoteFile);
            }catch(FTPException e){
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }else if(connectionType == SOAP){
        	String endpoint = this.mHost;
        	if(dirHistory!= null){
        		endpoint = endpoint+"/"+dirHistory;
        	}

        	String uploadData = loadFile(new File(localPath));
        	if(mSoapParams == null){
        		mSoapParams = new HashMap();
        	}
        	mSoapParams.put(getSoapFilenameParam(), uploadData);

        	String ret = SOAPTransferClient.invoke(endpoint, soapNamespace, soapOperation, mUser, mPass, mSoapParams);
                log.info("Soap response:");
                log.info(ret);
        	ByteArrayInputStream bais = new ByteArrayInputStream(ret.getBytes());
        	return bais;
        }else if(connectionType == SIMPLE_HTTP_POST){
        	String endpoint = this.mHost;
        	if(dirHistory!= null){
        		endpoint = endpoint+"/"+dirHistory;
        	}

        	String uploadData = loadFile(new File(localPath));
        	HttpClient client = new HttpClient();
        	if (mUser!=null && mUser.trim().length()>0) {
				URL endpointURL = new URL(endpoint);// used to parse out the
													// endpoint
				client.getParams().setAuthenticationPreemptive(true);
				Credentials defaultcreds = new UsernamePasswordCredentials(
						mUser, mPass);
				client.getState().setCredentials(
						new AuthScope(endpointURL.getHost(), endpointURL
								.getPort(), AuthScope.ANY_REALM), defaultcreds);
			}

            PostMethod postMethod = new PostMethod(endpoint);

            postMethod.setRequestBody(uploadData);
            postMethod.setRequestHeader("Content-type","text/xml; charset=ISO-8859-1");
            try {
	            int statusCode1 = client.executeMethod(postMethod);
	            String responseText = postMethod.getResponseBodyAsString();
	            log.debug("statusLine>>>" + postMethod.getStatusLine());
	            responseText = responseText.replaceAll("&lt;","<");
	            responseText = responseText.replaceAll("&gt;",">");
	            log.info(responseText);
	        	ByteArrayInputStream bais = new ByteArrayInputStream(responseText.getBytes());
	        	return bais;
	        } finally {
	            // Release the connection.
	        	postMethod.releaseConnection();
	         } 
        /*}else if(connectionType == MQ){
        	try {
      	      // Setup the MQSeries client environment
      	      // Hostname can be either the local hostname or a remote hostname
      	      // Insert the environment variables here:

      		  String qManager     = mMQManager; //"ABFQM1";
      		  String qLocalname   = mMQLocalname; //"POQUEUE";
      		  MQQueueManager qMgr;
      	      MQEnvironment.hostname = this.mHost;
      	      MQEnvironment.channel  = mMQChannel; //"ABFCH1";
			  if(mCmdport > 0){
				MQEnvironment.port     = mCmdport; //1420;
			  }else{
			    MQEnvironment.port = 1420; //some default
			  }

      	      // Create a connection to the queue manager
      	      qMgr = new MQQueueManager(qManager);

      	      // Set up the open options on the queue you wish to open...
      	      // Note. All MQ Options are prefixed with MQC in Java.
      	      int openOptions = MQC.MQOO_INPUT_AS_Q_DEF |
      	                        MQC.MQOO_OUTPUT ;

      	      // Specify the queue that you wish to open, and the open options...
      	      MQQueue qLocal = qMgr.accessQueue(qLocalname,
      	                                        openOptions,
      	                                        null,           // default q manager
      	                                        null,           // no dynamic q name
      	                                        null);          // no alternate user id

      	      // Define a simple MQ message, and write some text in UTF format..
     	      MQMessage messagebuffer = new MQMessage();

      	      messagebuffer.writeString(loadFile(new File(localPath)));

      	      // specify the message options...
      	      // ... here: accept the defaults, same as MQPMO_Default constant
      	      MQPutMessageOptions pmo = new MQPutMessageOptions();

      	      // put the message on the queue
      	      qLocal.put(messagebuffer,pmo);

      	      log.debug("The message is put");      // Close the queue
      	      // Close the local queue
      	      qLocal.close();
      	      // Disconnect from the queue manager
      	      qMgr.disconnect();
      	    }

      	    // If an error has occured in the above, try to identify what went wrong.
      	    // Was it an MQ error?
      	    catch (MQException ex)
      	    {
      	      log.debug("An MQ error occurred : Completion code " +
      	                         ex.completionCode +
      	                         " Reason code " + ex.reasonCode);
      	    }

      	    // Was it a Java buffer space error?
      	    catch (java.io.IOException ex)
      	    {
      	      log.debug("An error occurred whilst writing to the message buffer: " +
      	                         ex);
      	    }*/
        }else if(connectionType == EMAIL_INLINE){
        	try{
        		log.debug("Sending file: "+localPath);
	        	File f = new File(localPath);
	        	String emailBody = loadFile(f);
	        	ApplicationsEmailTool.sendEmail(this.mHost, f.getName(), emailBody);
        	}catch(Exception e){
        		e.printStackTrace();
        		throw new IOException(e.getMessage());
        	}
        }

        return null;
    }

    /**
     *Reads in a file and spits out a string.  Does not do any validation that the
     *file is actually ascii or exists.
     *@param pFile the file to read
     */
    private  static String loadFile(File pFile) throws IOException{
        StringBuffer data = new StringBuffer();
        BufferedReader rdr = new BufferedReader(new InputStreamReader(new FileInputStream(pFile), "UTF-8"));
        String line = rdr.readLine();
        while(line != null){
            data.append(line);
            data.append("\n");
            line = rdr.readLine();
        }
        return data.toString();
    }
	String mMQManager;
	public void setMQManager(String v){
		mMQManager=v;
	}
	String mMQLocalname;
	public void setMQLocalname(String v){
		mMQLocalname=v;
	}
	String mMQChannel;
	public void setMQChannel(String v){
		mMQChannel=v;
	}

    /**
     * Used only when dealing with soap transfers as part of the operational namespace.  This will typically be a domain name.
     * Will be ignored for other transfer types
     */
	public String getSoapNamespace() {
		return soapNamespace;
	}

	/**
     * Used only when dealing with soap transfers as part of the operational namespace.  This will typically be a domain name.
     * Will be ignored for other transfer types
     */
	public void setSoapNamespace(String soapNamespace) {
		this.soapNamespace = soapNamespace;
	}
	/**
     * Used only when dealing with soap transfers as the operation being requested.  This will typically be a function name.
     * Will be ignored for other transfer types
     */
	public String getSoapOperation() {
		return soapOperation;
	}
	/**
     * Used only when dealing with soap transfers as the operation being requested.  This will typically be a function name.
     * Will be ignored for other transfer types
     */
	public void setSoapOperation(String soapOperation) {
		this.soapOperation = soapOperation;
	}

	public String getSoapFilenameParam() {
		return soapFilenameParam;
	}

	public void setSoapFilenameParam(String soapFilenameParam) {
		this.soapFilenameParam = soapFilenameParam;
	}
	HashMap mSoapParams;
	public HashMap getSoapParams() {
		return mSoapParams;
	}

	public void setSoapParams(HashMap pSoapParams) {
		this.mSoapParams = pSoapParams;
	}

	static public int getConnectionType(String connectionMode){
		int retType = 0;
		if ( "active".equalsIgnoreCase(connectionMode)) {
			retType = FileTransferClient.FTP_ACTIVE;
        }else if ( "soap".equalsIgnoreCase(connectionMode)) {
        	retType = FileTransferClient.SOAP;
        }else if("http".equalsIgnoreCase(connectionMode)) {
        	retType = FileTransferClient.SIMPLE_HTTP_POST;
		/*}else if("mq".equalsIgnoreCase(connectionMode)) {
			retType = FileTransferClient.MQ;*/
		}else if ( "email-inline".equalsIgnoreCase(connectionMode) || "email_inline".equalsIgnoreCase(connectionMode)) {
			retType = FileTransferClient.EMAIL_INLINE;
        }else if ( "sftp".equalsIgnoreCase(connectionMode)) {
        	retType = FileTransferClient.SFTP;
        }else if ( "ftps-implicit".equalsIgnoreCase(connectionMode)) {
        	retType = FileTransferClient.FTPS_IMPLICIT;
        }else{
        	retType = FileTransferClient.FTP_PASIVE;
        }
		return retType;
	}

	/**
     * Send email to recipients
     * @throws Exception
     */
    private void sendEmails(String recipients, String subject, String message, byte[] dataContents, String remoteFileName) throws Exception {
        FileAttach attachment = new FileAttach(remoteFileName, dataContents, "", dataContents.length);;
        File attach = attachment.fromAttachToFile();

        ApplicationsEmailTool.sendEmail(recipients, subject, message, attach);

    }
    private void sendEmails(String recipients, String subject, String message, String localFileName, String remoteFileName) throws Exception {
    	File file = new File(localFileName);
        byte[] byteData = FileAttach.fromFileToByte(file);
        sendEmails(recipients, subject, message, byteData, remoteFileName);
    }

    public FTPFile[] dirDetails() throws java.io.IOException   {
        log.info("FileTransferCliet dir: "+dirHistory);
        try {
        String dir = dirHistory;
        if(dir==null) dir = "/";
        return ftp.dirDetails("");
        } catch (FTPException exc) {
            exc.printStackTrace();
            throw new IOException(exc.getMessage());
        } catch (java.text.ParseException exc) {
            exc.printStackTrace();
            throw new IOException(exc.getMessage());
        }
    }
	
  /**
     *Renames the remote file or directory
     */
    public void rename(String fromFileName, String toFileName) throws IOException{
    	log.info("Calling rename from: "+fromFileName+" to "+toFileName);
        if(ftp != null){
            try{
                ftp.rename(fromFileName, toFileName);
            }catch(FTPException e){
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }
    }
	
	
/*	
  985       public void rename(String from, String to)
  986           throws IOException, FTPException {
*/	
}


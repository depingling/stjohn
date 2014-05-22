package com.cleanwise.service.apps;

import java.io.*;

import com.cleanwise.service.apps.FileTransferClient;
import com.cleanwise.service.api.util.Utility;

import java.util.HashMap;
import java.util.Map;
import com.enterprisedt.net.ftp.*;
import java.util.Date;

import org.apache.log4j.Logger;


public class GetFile {

    private static final Logger log = Logger.getLogger(GetFile.class);

    public static String V_YES = "yes";
    public static String V_TRUE = "true";

	private String vUsername,vPassword,vFilename,vTodir,vTofile,vFtpMode,
	vXfertype,vFromdir,vHostname,vPort,vNoListing,vNoSubDirs;
	private boolean vIndividualTransaction;
	private boolean skipSubDirs;
	private boolean removeFile;
	private int optPort = 0;
	FileTransferClient ftpc = null;
	private Map properties;

	public static void main(String args [])
	throws Exception {
		GetFile getter = new GetFile();
		getter.get();
	}

	public void setProperties(Map propertyMap){
		properties = propertyMap;
		vUsername = (String)properties.get("username");
		vPassword = (String)properties.get("password");
		vFilename = (String)properties.get("filename");
		String vrmFile = (String)properties.get("removefile");
		removeFile = (V_YES.equalsIgnoreCase(vrmFile) || V_TRUE.equalsIgnoreCase(vrmFile));
		vTodir = (String)properties.get("todir");
		vTofile = (String)properties.get("tofile");
		vFtpMode = (String)properties.get("ftpmode");
		vXfertype = (String)properties.get("transfer_type");
		vFromdir = (String)properties.get("fromdir");
		vHostname = (String)properties.get("fromhost");
		vPort = (String)properties.get("port");
		if ( null != vPort && vPort.length() > 0 ) {
			optPort = Integer.parseInt(vPort);
		}
		vNoListing = (String)properties.get("noListing");
		vNoSubDirs = (String)properties.get("noSubDirs");

		vIndividualTransaction = "true".equals((String)properties.get("individualTransaction"));
    }

	public void connect() throws Exception{
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
		}

		ftpc.connect(vHostname, optPort, vUsername, vPassword);

		if ( "binary".equalsIgnoreCase(vXfertype)) {
			ftpc.setTransferTypeAsBinaryType();
		}
		String msg = "START getting file(s)";
		if ( null != vFilename ) {
			msg += ": " + vFilename;
		}
		msg += " from directory: " + vFromdir + " to directory: " + vTodir;
		if ( null != vFromdir ) {
			ftpc.chdir(vFromdir);
		}
	}

	public String[] getFileNames() throws Exception{
		String fileNames [] ;
		if("true".equalsIgnoreCase(vNoListing) || "soap".equalsIgnoreCase(vFtpMode)){
			fileNames = new String[1];
			if ( vFilename == null ) {
				throw new Exception("cannot have no listing directive specified and not give a filename");
			}
			fileNames[0]=vFilename;
		}else{
			if ( vFilename == null ) {
				fileNames = ftpc.dir("*");
			} else {
				fileNames = ftpc.dir(vFilename);
			}
		}
		return fileNames;
	}

	protected void get() throws Exception{
		String kUsage = "Usage: ftpget " +
		" -Dfromhost=<source hostname> " +
		" -Dport=<21 (default)> " +
		" -Dfilename=<optional source filename pattern> " +
		" -Drmfile=<optional remove the file(s) when done with transfer (y|n)> " +
		" -Dusername=<ftp username> " +
		" -Dpassword=<ftp password> " +
		" -Dtodir=<destination directory> " +
		" -Dtofile=<optional local destination file, if multiple files found a seq is appended> " +
		" -DnoListing=<true [use filename specified] | false (default)> " +
		" -Dtransfer_type=<binary | ascii (default) > " +
		" -Dftpmode=<active | passive (default) | sftp | soap> " +
		" -Dsoapoperation=<soap operation if using soap> "+
		" -Dsoapnamespace=<soap namespace if using soap> "+
		" -Dfromdir=<source directory>"+
		" -DindividualTransaction=send files one per connection (send file, disconnect, send file...) <true | false (default)>";

		setProperties(System.getProperties());


		skipSubDirs = false;
		if(vNoSubDirs != null){
			vNoSubDirs = vNoSubDirs.toLowerCase().trim();
			if(vNoSubDirs.indexOf("t") >= 0 || vNoSubDirs.indexOf("1") >=0  || vNoSubDirs.indexOf("y") >=0 ){
				skipSubDirs = true;
			}
		}

		if ( vXfertype == null ) { vXfertype = "ascii"; }

		if ( null != vPort && vPort.length() > 0 ) {
			optPort = Integer.parseInt(vPort);
		}


		if ( vUsername == null ||
				vPassword == null ||
				vTodir == null ||
				vHostname == null) {
			log.info(kUsage);
			return;
		}

		try {
			log.info("opening a connection to: " + vHostname);
			log.info("connected to           : " + vHostname );
			log.info("loging in as           : " + vUsername);

			connect();

			String fs [] = getFileNames();

			for ( int fidx = 0; fidx < fs.length; fidx++) {
				try{
					if(vIndividualTransaction){
						log.info("Disconnecting...");
						ftpc.closeSession();
						log.info("Re-Connecting...");
						connect();
					}
					String localFileName = vTodir + "/" +  fs[fidx];
					if(vTofile!=null){
						//if the local filename was specified use that
						localFileName = vTodir + "/" + vTofile;
						//if the local file was specified and we are fetching multiple increment counter
						//and log error, thus we get file, file1, file2 etc with errors written out
						if(fidx > 0){
							localFileName = localFileName + Integer.toString(fidx);
							log.info("ERROR: Local filename specified and multiple files found on remote host");
							log.info("Saving file as [" + localFileName+ "]");
						}
					}

					try{
						getFile(fs[fidx],localFileName);
					}catch(Exception e){
						log.info("Could not get file, retrying");
						try{
							ftpc.closeSession();
							connect();
							getFile(fs[fidx],localFileName);
						}catch(Exception e2){
							log.info("Could not get file, retrying 2nd time");
							ftpc.closeSession();
							connect();
							getFile(fs[fidx],localFileName);
						}
					}
				}catch(Exception e ) {
					if(!isNoFilesError(e)){
						//if something is really wrong re-throw the error
						throw e;
					}
				}
				log.info("DONE " );
			}
		} catch ( Exception e ) {
			if(isNoFilesError(e)){
				log.info("CW: No files found." );
			}else{
				e.printStackTrace();
				throw new Exception("Exception getting a file, exception: " + e );
			}
		}finally{
			ftpc.closeSession();
		}
	}

	private void getFile(String fileName, String localFileName) throws Exception{
		if( fileName == null ||  fileName.length() == 0){
			log.info("Skipping empty file");
			return;
		}
		if(fileName.startsWith("./") || fileName.startsWith(".\"")){
			fileName = fileName.substring(2);
		}
		if(skipSubDirs){
			if( fileName.lastIndexOf("/")>0 ||  fileName.lastIndexOf("\"")>0){ //greater than, not >=, if it starts with that is okay as it would indicate a root file
				log.info("Skipping file in sub dir: "+ fileName);
				return;
			}

		}

		File localFile = new File(localFileName);
		if(localFile.getParentFile() != null){
			localFile.getParentFile().mkdirs();
		}
		ftpc.get(localFileName,  fileName);
		if ( localFile.exists() ) {
			if ( removeFile ) {
				try {
					ftpc.delete( fileName);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("failed to delete file: " +  fileName + "\n" + e.getMessage());
				}
			}
		} else {
			log.info("-- FAILED to copy file: " +
					fileName);
		}
	}

	public boolean getFile(String fileName, ByteArrayOutputStream outputStream) throws Exception{
	    if( fileName == null ||  fileName.length() == 0){
	      return false;
	    }
	    if( fileName.equals(".") ||  fileName.equals("..")){
		      return false ;
		}
	    if(fileName.startsWith("./") || fileName.startsWith(".\"")){
			fileName = fileName.substring(2);
		}
	    if( fileName.indexOf("/")>0 ||  fileName.indexOf("\"")>0){ //greater than, not >=, if it starts with that is okay as it would indicate a root file
	      return false;
	    }

	    ftpc.get(outputStream,  fileName);
	    if ( removeFile ) {
			try {
				ftpc.delete( fileName);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("failed to delete file: " +  fileName + "\n" + e.getMessage());
			}
		}
	    return true;
	  }

    public boolean readIFile(String pFileName, ByteArrayOutputStream pOutputStream) throws Exception {

        log.info("readIFile()=> BEGIN");

        if (!Utility.isSet(pFileName)) {
            log.info("readIFile()=> Skipping empty file");
            return false;
        }

        if (pFileName.equals(".") || pFileName.equals("..")) {
            return false;
        }

        if (pFileName.startsWith("./") || pFileName.startsWith(".\"")) {
            pFileName = pFileName.substring(2);
        }

        if (pFileName.indexOf("/") > 0 || pFileName.indexOf("\"") > 0) { //greater than, not >=, if it starts with that is okay as it would indicate a root file
            log.info("readIFile()=> skipping file in sub dir: " + pFileName);
            return false;
        }

        log.info("readIFile()=> remote file: " + pFileName);

        ftpc.get(pOutputStream, pFileName);


        log.info("readIFile()=> END.");

        return true;

    }

    public boolean remove(String pFileName) {

        log.info("remove()=> BEGIN");

        try {
            ftpc.delete(pFileName);
        } catch (Exception e) {
            log.error("remove()=> END.Error:" + e.getMessage());
            return false;
        }

        log.info("remove()=> END.");

        return true;

    }

    private static boolean isNoFilesError(Exception e){
		String s = e.getMessage();
		if (s!=null && (s.indexOf("No files found") >= 0 || s.indexOf("No such file") >= 0 )) {
			return true;
		}else{
			return false;
		}
	}

	/**
     *Closes the session, and disconnects from the server
     */
    public void closeSession() throws IOException{
    	if (ftpc != null)
    		ftpc.closeSession();
    }

    public Date lastModified(String pFileName) throws java.io.IOException   {
        if(pFileName==null) return null;
        FTPFile[] files = ftpc.dirDetails();
        Date modDate = null;
        if(files!=null) {
            for(int ii=0; ii<files.length; ii++) {
                String fn = files[ii].getName();
                if(pFileName.equals(fn)) {
                    modDate = files[ii].lastModified();
                    if(modDate==null) {
                        modDate = files[ii].created();
                    }
                    break;
                }
            }
        }
        return modDate;
    }

    public boolean rename(String pFromFileName, String pToFileName) {
     log.info("rename()=> BEGIN");
     try {
         ftpc.rename(pFromFileName, pToFileName);
     } catch (Exception e) {
         log.error("rename()=> END.Error:" + e.getMessage());
         return false;
     }
     log.info("rename()=> END.");
     return true;
   }

}

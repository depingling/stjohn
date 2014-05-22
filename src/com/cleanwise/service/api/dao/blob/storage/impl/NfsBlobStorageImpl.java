package com.cleanwise.service.api.dao.blob.storage.impl;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.dao.blob.storage.BlobStorage;
import com.cleanwise.service.api.dao.blob.storage.util.BlobStorageAccess;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import org.apache.log4j.Logger;


public class NfsBlobStorageImpl implements BlobStorage {
    private static final Logger log = Logger.getLogger(NfsBlobStorageImpl.class);
    
    private static String fileSeparator = System.getProperty("file.separator");

    private boolean _initialized = false;
    private String  _storage_root;

    @Override
    public boolean isInitialized() {
        return _initialized;
    }

    @Override
    public void initialize(Map<String, String> parameters) {
	    _initialized = false;
		try {
		   checkStatus();
		   _initialized = true;
		} catch (Exception exc) {
			log.error(exc.getMessage());
		}
	    return;
	}

	private void checkStatus() 
	throws Exception {
        _storage_root = System.getProperty(BlobStorageAccess.NFS_BLOB_ROOT);   
		if(_storage_root==null) {
			throw new Exception ("NFS Operation. System parameter "+ BlobStorageAccess.NFS_BLOB_ROOT+" is not set");
		}
		File canary = new File(_storage_root,"canary.txt");
		if(!canary.exists()) {
			String errorMess = "NFS Operation. NFS ss not accessible 'Canary.txt' is not found. Path: "+ 
				BlobStorageAccess.NFS_BLOB_ROOT;
			throw new Exception (errorMess);
		}		
	}

	private String getFileDir(String filename) 
	throws Exception {
		checkStatus();
		if(!Utility.isSet(filename)) {
			throw new Exception("No file name provided");
		}
		String[] filenameParts = filename.split("_",3);
		if(filenameParts.length<3) {
			throw new Exception("Wrong filename: "+filename);
		}
		int objectId = 0;
		try {
			objectId = Integer.parseInt(filenameParts[1]);
		} catch (Exception exc) {
			throw new Exception("File name has wrong object id: " + filenameParts[1]+". File name: "+filename);
		}
		int lastDigits = objectId % 100;
		int last = lastDigits % 10;
		int first = lastDigits / 10; 

		String fileDir = _storage_root +
					 "/blobs"+
					 fileSeparator+filenameParts[0]+
					 fileSeparator+first+
					 fileSeparator+last;
		log.info("NFS Operation. Directory: "+fileDir+" for file: "+filename);
		return fileDir;
	}

    @Override
    public boolean storeBlob(byte[] blob, String filename) {
        boolean isOK = false;
		String fileDir = null;
        try {
            log.info("NFS Operation. Trying to save file into filesystem: "+filename);
			fileDir = getFileDir(filename);
            File file = new File(fileDir, filename);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(blob);
            bos.flush();
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {}
            }

            isOK = true;
        } catch (Exception e) {
            log.error("NFS Operation. ERROR saving file. Directory: "+fileDir +". File name: " + filename, e);
        }
        
        return isOK;
    }

    @Override
    public byte[] readBlob(String filename) {
        byte[] blob = null;
		String fileDir = null;
		try {
			fileDir = getFileDir(filename);
			
            File file = new File(fileDir, filename);
            if (file.exists()) {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                blob = new byte[(int)file.length()];
                bis.read(blob);
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {}
                }
            } else {
                throw new FileNotFoundException("File not found. Directory: "+fileDir +". File name: " + filename);
            }
        } catch (Exception e) {
			e.printStackTrace();
            log.error("[readBlob] ERROR reading blob", e);
        }

        return blob;
    }

	
    @Override
    public boolean removeBlob(String filename) {
        boolean isOK = false;
		String fileDir = null;
		
        try {
			fileDir = getFileDir(filename);
            File file = new File(fileDir, filename);
            if (file.exists()) {
                file.delete();
            } else {
                throw new FileNotFoundException("File not found. Directory: "+fileDir +". File name: " + filename);
            }
        } catch (Exception e) {
            log.error("[removeBlob] ERROR removing blob", e);
        }
        
        return isOK;
    }

    @Override
    public String getStorageType() {
        return BlobStorageAccess.STORAGE_NFS;
    }
    
    @Override
    public void disconnect() {}

}

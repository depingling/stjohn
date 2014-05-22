package com.cleanwise.service.api.dao.blob.storage;

import java.util.Map;

public interface BlobStorage {
    public static String FTP_HOST = "FTP_HOST";
    public static String FTP_PORT = "FTP_PORT";
    public static String FTP_USER = "FTP_USER";
    public static String FTP_PASSWORD = "FTP_PASSWORD";
    public static String FTP_CONNECTION_TYPE = "FTP_CONNECTION_TYPE";
    public static String FTP_STORAGE_DIR = "FTP_STORAGE_DIR";
    
    public static String FTP_ACTIVE = "FTP_ACTIVE";
    public static String FTP_PASSIVE = "FTP_PASSIVE";
    
    public static String FILE_STORAGE_DIR = "FILE_STORAGE_DIR";
    /*
    public static enum BlobStorageType {
        FTP_STORAGE,
        FILE_STORAGE,
		NFS_STORAGE
    }
    */
    public void initialize(Map<String, String> parameters);
    
    public boolean isInitialized();
    
    public boolean storeBlob(byte[] blob, String filename);
    
    public byte[] readBlob(String filename);
    
    public boolean removeBlob(String filename);
    
    public void disconnect();

    public String getStorageType();
}

package com.cleanwise.service.api.dao.blob.storage.util;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.dao.blob.storage.BlobStorage;
import com.cleanwise.service.api.dao.blob.storage.factory.BlobStorageFactory;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class BlobStorageAccess {
    private static final Logger log = Logger.getLogger(BlobStorageAccess.class);
    
    private static final String TABLE_EVENT_PROPERTY_NAME = "EP";
    private static final String TABLE_PROCESS_PROPERTY_NAME = "PP";
    private static final String TABLE_EVENT_EMAIL_NAME = "EE";
    
    private static final String FIELD_BLOB_VALUE_NAME = "B";
    private static final String FIELD_VAR_VALUE_NAME = "V";
    private static final String FIELD_ATTACHMENTS_NAME = "A";
    private static final String FIELD_LONG_TEXT_NAME = "L";
    
    private static final String UNKNOWN_NAME = "Unknown";
    
    private static final String BLOB_FILE_EXTENSION = "dat";
    
    private static final String FTP_BLOB_HOST = "ftp.blob.host";
    private static final String FTP_BLOB_PORT = "ftp.blob.port";
    private static final String FTP_BLOB_USER = "ftp.blob.user";
    private static final String FTP_BLOB_PASSWORD = "ftp.blob.password";
    private static final String FTP_BLOB_DIR = "ftp.blob.dir";
    private static final String FILE_BLOB_DIR = "file.blob.dir";
    public static final String NFS_BLOB_ROOT = "nfs.blob.root";

    private static String _currentHost = System.getenv("HOSTNAME");
    private static String _ftpBlobHost = System.getProperty(FTP_BLOB_HOST);

    private BlobStorage _ftpStorage = null;
    private BlobStorage _fileStorage = null;
    private BlobStorage _nfsStorage = null;
    
    private String _storedToStorageType = null;
    private String _storedToHostName = null;

    public static final String TABLE_EVENT_PROPERTY = "clw_event_property";
    public static final String TABLE_PROCESS_PROPERTY = "clw_process_property";
    public static final String TABLE_EVENT_EMAIL = "clw_event_email";
    
    public static final String FIELD_BLOB_VALUE = "blob_value";
    public static final String FIELD_VAR_VALUE = "var_value";
    public static final String FIELD_ATTACHMENTS = "attachments";
    public static final String FIELD_LONG_TEXT = "long_text";

    public static final String STORAGE_TYPE = "storageType";
    public static final String STORAGE_FTP = "FTP";
    public static final String STORAGE_FILESYSTEM = "FILESYSTEM";
    public static final String STORAGE_DB = "DB";
    public static final String STORAGE_NFS = "NFS";
    
    public boolean storeBlob(byte [] blob, String fileName) {
		boolean isOK = false;
        _storedToStorageType = System.getProperty(STORAGE_TYPE);
        log.info("Store blob. Bloag storage type: "+ _storedToStorageType+". File: " + fileName);
		if(STORAGE_FTP.equals(_storedToStorageType)) {	 
			if (_ftpStorage == null) {
				initializeFtpStorage();
			}
			log.info("Blob to FTP! initialized: " + _ftpStorage.isInitialized());
			//trying to save via FTP
			if (_ftpStorage.isInitialized()) {
				if (_ftpStorage.storeBlob(blob, fileName)) {
					isOK= true;
					_storedToHostName = _ftpBlobHost;
					log.info("Blob File saved SUCCESSFULLY via FTP: " + fileName);
				} else {
					_storedToHostName= null;
					log.info("Failure by saving blob via FTP. File: " + fileName);
				}
			} else {
				log.error("Failure by FTP Blob Storage initialization.\nProbably some 'ftp.blob.*' system properties have not been set properly.\nFile name to store: " + fileName);
			}
		} else if (STORAGE_NFS.equals(_storedToStorageType)) {	 
			if(_nfsStorage == null) {
				_nfsStorage = BlobStorageFactory.createNfsBlobStorage();
			}
			if(_nfsStorage.storeBlob(blob, fileName)) {
				isOK = true;
				_storedToHostName = ( Utility.isSet(_currentHost) ) ? _currentHost : "localhost";
                log.info("Blob File saved SUCCESSFULLY to NFS. file: " + fileName);				
			} else {
				_storedToHostName = null;
				log.info("Failure by saving blob to NFS. File: " + fileName);
			}
		
		} else {
			_storedToStorageType=null;
			log.error("System propertiy 'STORAGE_TYPE' has not been properly set. File name to store: " + fileName);
		}
		return isOK; 
    }

    public byte[] readBlob(String storageType, String binaryDataServer, String fileName) {
        byte [] blob = null;
        
        if (Utility.isSet(fileName) && Utility.isSet(storageType)) {
            if (STORAGE_FTP.equals(storageType)) {
                if (_ftpStorage == null) {
                    initializeFtpStorage();
                }
                if (_ftpStorage.isInitialized()) {
                    blob = _ftpStorage.readBlob(fileName);
                } else {
                    log.error("Failure by FTP Blob Storage initialization.\nProbably some 'ftp.blob.*' system properties have not been set properly.\nFile name to read: " + fileName);
                }
            } else if (STORAGE_FILESYSTEM.equals(storageType)) {
                if (_fileStorage == null) {
                    initializeFileStorage();
                }
                if (_fileStorage.isInitialized()) {
                    if (Utility.isSet(_currentHost) && _currentHost.equals(binaryDataServer)) {
                        blob = _fileStorage.readBlob(fileName);
                    } else {
                        log.error("Error reading file from File System Blob Storage.\nBinary Data Server reference doesn't match.\n File name to read: " + fileName);
                    }
                } else {
                    log.error("Failure by File System Blob Storage initialization.\nProbably 'file.blob.dir' system property has not been set properly.\n File name to read: " + fileName);
                }
            } else if (STORAGE_NFS.equals(storageType)) {
				if(_nfsStorage == null) {
					_nfsStorage = BlobStorageFactory.createNfsBlobStorage();
				}
                blob = _nfsStorage.readBlob(fileName);
            }
        }
        
        return blob;
    }

    public boolean removeBlob(String storageType, String binaryDataServer, String fileName) {
        boolean isOK = false;
        
        if (Utility.isSet(fileName) && Utility.isSet(storageType)) {
            if (STORAGE_FTP.equals(storageType)) {
                if (_ftpStorage == null) {
                    initializeFtpStorage();
                }
                if (_ftpStorage.isInitialized()) {
                    isOK = _ftpStorage.removeBlob(fileName);
                } else {
                    log.error("Failure by FTP Blob Storage initialization.\nProbably some 'ftp.blob.*' system properties have not been set properly.\n File name to remove: " + fileName);
                }
            } else if (STORAGE_FILESYSTEM.equals(storageType)) {
                if (_fileStorage == null) {
                    initializeFileStorage();
                }
                if (_fileStorage.isInitialized()) {
                    if (Utility.isSet(_currentHost) && _currentHost.equals(binaryDataServer)) {
                        _fileStorage.removeBlob(fileName);
                    } else {
                        log.error("Error removing file from File System Blob Storage.\nBinary Data Server reference doesn't match.\n File name to remove: " + fileName);
                    }
                } else {
                    log.error("Failure by File System Blob Storage initialization.\nProbably 'file.blob.dir' system property has not been set properly.\n File name to remove: " + fileName);
                }
            } else if (STORAGE_NFS.equals(storageType)) {
				if(_nfsStorage == null) {
					_nfsStorage = BlobStorageFactory.createNfsBlobStorage();
				}
                _nfsStorage.removeBlob(fileName);
            }
        }

        return isOK;
    }
    
    public void storeBlobFtpOnly(byte [] blob, String fileName) {
        log.info("[storeBlobFtpOnly] fileName: " + fileName +((blob != null) ? " BLOB" : " NO BLOB"));
        _storedToStorageType = null;
        _storedToHostName = null;

        if (_ftpStorage == null) {
            initializeFtpStorage();
        }
        //trying to save via FTP
        if (_ftpStorage.isInitialized()) {
            if (_ftpStorage.storeBlob(blob, fileName)) {
                _storedToStorageType = STORAGE_FTP;
                _storedToHostName = _ftpBlobHost;
                log.info("Blob File saved SUCCESSFULLY via FTP. file: " + fileName);
            } else {
                log.error("Failure by saving blob via FTP. file: " + fileName);
            }
        } else {
            log.error("Failure by FTP Blob Storage initialization.\nProbably some 'ftp.blob.*' system properties have not been set properly.\n File name to store: " + fileName);
        }
    }
    
    public static String composeFileName(String table, String field, int id) {
        if (Utility.isSet(table) && Utility.isSet(field) && id > 0) {
            String delimiter = "_";
            StringBuilder name = new StringBuilder();
            if (TABLE_EVENT_EMAIL.equalsIgnoreCase(table)) {
                name.append(TABLE_EVENT_EMAIL_NAME);
                name.append(delimiter);
            } else if (TABLE_EVENT_PROPERTY.equalsIgnoreCase(table)) {
                name.append(TABLE_EVENT_PROPERTY_NAME);
                name.append(delimiter);
            } else if (TABLE_PROCESS_PROPERTY.equalsIgnoreCase(table)) {
                name.append(TABLE_PROCESS_PROPERTY_NAME);
                name.append(delimiter);
            } else {
                name.append(UNKNOWN_NAME);
                name.append(delimiter);
            }
            
            name.append(id);
            name.append(delimiter);
            
            if (FIELD_BLOB_VALUE.equalsIgnoreCase(field)) {
                name.append(FIELD_BLOB_VALUE_NAME);
                name.append(delimiter);
            } else if (FIELD_VAR_VALUE.equalsIgnoreCase(field)) {
                name.append(FIELD_VAR_VALUE_NAME);
                name.append(delimiter);
            } else if (FIELD_ATTACHMENTS.equalsIgnoreCase(field)) {
                name.append(FIELD_ATTACHMENTS_NAME);
                name.append(delimiter);
            } else if (FIELD_LONG_TEXT.equalsIgnoreCase(field)) {
                name.append(FIELD_LONG_TEXT_NAME);
                name.append(delimiter);
            } else {
                name.append(UNKNOWN_NAME);
                name.append(delimiter);
            }
            
            name.append(System.nanoTime());
            name.append(".");
            name.append(BLOB_FILE_EXTENSION);
            
            return name.toString();
        } else {
            return null;
        }
    }
    
    private void initializeFtpStorage() {
        _ftpStorage = BlobStorageFactory.createFtpBlobStorage();

        Map ftpParameters = new HashMap();

        ftpParameters.put(BlobStorage.FTP_HOST, _ftpBlobHost);
        ftpParameters.put(BlobStorage.FTP_PORT, System.getProperty(FTP_BLOB_PORT));
        ftpParameters.put(BlobStorage.FTP_CONNECTION_TYPE, BlobStorage.FTP_PASSIVE);
        ftpParameters.put(BlobStorage.FTP_STORAGE_DIR, System.getProperty(FTP_BLOB_DIR));
        ftpParameters.put(BlobStorage.FTP_USER, System.getProperty(FTP_BLOB_USER));
        ftpParameters.put(BlobStorage.FTP_PASSWORD, System.getProperty(FTP_BLOB_PASSWORD));

        _ftpStorage.initialize(ftpParameters);
    }
    
    public boolean initializeFtpStorage(String ftpHost,
                                        String ftpPort,
                                        String ftpDir,
                                        String ftpUser,
                                        String ftpPassword) {
        _ftpStorage = BlobStorageFactory.createFtpBlobStorage();

        Map ftpParameters = new HashMap();
                
        ftpParameters.put(BlobStorage.FTP_HOST, ftpHost);
        ftpParameters.put(BlobStorage.FTP_PORT, ftpPort);
        ftpParameters.put(BlobStorage.FTP_CONNECTION_TYPE, BlobStorage.FTP_PASSIVE);
        ftpParameters.put(BlobStorage.FTP_STORAGE_DIR, ftpDir);
        ftpParameters.put(BlobStorage.FTP_USER, ftpUser);
        ftpParameters.put(BlobStorage.FTP_PASSWORD, ftpPassword);

        _ftpStorage.initialize(ftpParameters);

        return _ftpStorage.isInitialized();
    }
    
    private void initializeFileStorage() {
        _fileStorage = BlobStorageFactory.createFileBlobStorage();

        Map fileParameters = new HashMap();

        fileParameters.put(BlobStorage.FILE_STORAGE_DIR, System.getProperty(FILE_BLOB_DIR));

        _fileStorage.initialize(fileParameters);
    }
    
    private void initializeFileStorage(String dir) {
        _fileStorage = BlobStorageFactory.createFileBlobStorage();

        Map fileParameters = new HashMap();

        fileParameters.put(BlobStorage.FILE_STORAGE_DIR, dir);

        _fileStorage.initialize(fileParameters);
    }

    public String getStoredToHostName() {
        return _storedToHostName;
    }

    public String getStoredToStorageType() {
        return _storedToStorageType;
    }

    public static String getCurrentHost() {
        return _currentHost;
    }

    public static void setCurrentHost(String currentHost) {
        _currentHost = currentHost;
    }

}
package com.cleanwise.service.api.dao.blob.storage.impl;


import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.apps.FileTransferClient;
import com.cleanwise.service.api.dao.blob.storage.BlobStorage;
import com.cleanwise.service.api.dao.blob.storage.util.BlobStorageAccess;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import org.apache.log4j.Logger;

public class FtpBlobStorageImpl implements BlobStorage {
    private static final Logger log = Logger.getLogger(FtpBlobStorageImpl.class);
    
    private static final String FTP_DEFAULT_PORT = "21";
    private static String fileSeparator = System.getProperty("file.separator");
    private static boolean ftpDirChecked = false;

    private boolean _initialized = false;
    private String  _host;
    private String  _port;
    private String  _user;
    private String  _password;
    private String  _ftp_connection_type;
    private String  _ftp_storage_dir;
    private FileTransferClient _ftc = null;
    private long lastConnectTime = 0;

    @Override
    public boolean isInitialized() {
        return _initialized;
    }

    @Override
    public void initialize(Map<String, String> parameters) {

        _host = parameters.get(FTP_HOST);
        _port = parameters.get(FTP_PORT);
        _user = parameters.get(FTP_USER);
        _password = parameters.get(FTP_PASSWORD);
        _ftp_connection_type = parameters.get(FTP_CONNECTION_TYPE);
        _ftp_storage_dir = parameters.get(FTP_STORAGE_DIR);   
        
        StringBuilder buffer = new StringBuilder();
        boolean isOK = checkMandatoryParameters(buffer);
        
        if (isOK) {
            buffer.setLength(0);
            checkOptionalParameters(buffer);
            if (buffer.length() > 0) {
                log.info(buffer.toString());
            }
            
            buffer.setLength(0);
            buffer.append("Initialized with: \r\n");
            buffer.append("               host = ").append(_host).append("\r\n");
            buffer.append("               port = ").append(_port).append("\r\n");
            buffer.append("               user = ").append(_user).append("\r\n");
            buffer.append("           password = ").append(_password).append("\r\n");
            buffer.append("ftp_connection_type = ").append(_ftp_connection_type).append("\r\n");
            buffer.append("    ftp_storage_dir = ").append(_ftp_storage_dir).append("\r\n");
            
            log.info(buffer.toString());
            _initialized = true;
        } else {
            _initialized = false;
        }
    }

    @Override
    public boolean storeBlob(byte[] blob, String filename) {
        boolean isOK = false;
        try {
            if (connect()) {
                log.info("[storeBlob] Trying to put file via ftp");
                _ftc.put(blob, filename);
                isOK = true;
            } else {
                log.error("[storeBlob] ERROR saving blob into file: (" + filename + ") Connection problem");
            }
        } catch (Exception e) {
            log.error("[storeBlob] ERROR saving blob into file: (" + filename + ")", e);
        }
        
        return isOK;
    }

    @Override
    public byte[] readBlob(String filename) {
        byte[] blob = null;

        try {
            if (connect()) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                
                log.info("[readBlob] Trying to get file via ftp");
                _ftc.get(baos, filename);
                blob = baos.toByteArray();
            } else {
                throw new FileNotFoundException("File not found: " + filename);
            }
        } catch (IOException e) {
            log.error("[readBlob] ERROR reading blob", e);
        }

        return blob;
    }

    @Override
    public boolean removeBlob(String filename) {
        boolean isOK = false;
        try {
            if (connect()) {
                log.info("[removeBlob] Trying to remove file via ftp");
                _ftc.delete(filename);
                isOK = true;
            } else {
                throw new FileNotFoundException("File not found: " + filename);
            }
        } catch (Exception e) {
            log.error("[removeBlob] ERROR removing blob", e);
        }
        
        return isOK;
    }
    
    @Override
    public void disconnect() {
        try {
            if (_ftc != null) {
                _ftc.closeSession();
            }
        } catch (IOException e) {}
    }

    @Override
    public String getStorageType() {
        return BlobStorageAccess.STORAGE_FTP;
    }
    
    private boolean connect() {
        boolean connected = false;
        try {
            if (_initialized) {
            	if (_ftc == null) {
                    int ftpConnectionTypeInt = _ftp_connection_type.equals(FTP_ACTIVE) ? FileTransferClient.FTP_ACTIVE : FileTransferClient.FTP_PASIVE;
                    _ftc = new FileTransferClient(ftpConnectionTypeInt);                    
                }
            	
            	// disconnect and reconnect if connection opened more than 5 seconds
            	long currentTime = System.currentTimeMillis();            	
            	boolean  needReConnect = lastConnectTime > 0 && (currentTime-lastConnectTime)/1000 > 5;
            	if (needReConnect){
            		disconnect();
            	}
                if (!_ftc.checkFtpConnection() || needReConnect) {
                	lastConnectTime = System.currentTimeMillis();
                    log.info("[connect] Trying to connect to the host via ftp");
                    log.info("[connect] host: " + _host);
                    log.info("[connect] port: {" + _port + "}");
                    log.info("[connect] port: " + Integer.parseInt(_port));
                    log.info("[connect] user: " + _user);
                    log.info("[connect] password: " + _password);
                    _ftc.oneAttemptConnect(_host, Integer.parseInt(_port), _user, _password);
                    _ftc.setTransferTypeAsBinaryType();
                    if (!ftpDirChecked){
                    	_ftc.mkdirs(_ftp_storage_dir);
                    	ftpDirChecked = true;
                    }
                    _ftc.chdir(_ftp_storage_dir);
                }
                connected = true;
            }
        } catch (IOException e) {
            log.error("ERROR establishing FTP connection to " + _host + ":" + _port, e);
        }
        
        return connected;
    }
            
    private boolean checkMandatoryParameters(StringBuilder messages) {
        boolean isOk = true;
        if (!Utility.isSet(_host)) {
            isOk = false;
            messages.append("Value of 'host' parameter is empty.\r\n");
        } else {
            _host = _host.trim();
        }
        if (Utility.isSet(_port)) {
            if (!isIntValue(_port)) {
                isOk = false;
                messages.append("Invalid numerical value for 'port' parameter.\r\n");
            }
        }
        if (!Utility.isSet(_user)) {
            isOk = false;
            messages.append("Value of 'user' parameter is empty.\r\n");
        } else  {
            _user = _user.trim();
        }

        return isOk;
    }
    
    private void checkOptionalParameters(StringBuilder messages) {
        if (!Utility.isSet(_port)) {
            _port = FTP_DEFAULT_PORT;
            messages.append("Value of 'port' is not defined. Default value will be used: '");
            messages.append(_port);
            messages.append("'.\r\n");
        } else {
            _port = _port.trim();
        }
        if (!Utility.isSet(_ftp_connection_type)) {
            _ftp_connection_type = FTP_ACTIVE;
            messages.append("Value of 'ftp mode' is not defined. 'ftp mode' set to: 'FTP_ACTIVE'.\r\n");
        } else {
            _ftp_connection_type = _ftp_connection_type.trim();
        }
        if (!Utility.isSet(_ftp_storage_dir)) {
            _ftp_storage_dir = ""; //fileSeparator;
            messages.append("Value of 'storage dir' is not defined. 'storage dir' has been set to root.\r\n");
        } else {
            _ftp_storage_dir = _ftp_storage_dir.trim();
			/*
            if (!_ftp_storage_dir.startsWith(fileSeparator)) {
                _ftp_storage_dir = fileSeparator + _ftp_storage_dir;
            }
            if (!_ftp_storage_dir.endsWith(fileSeparator)) {
                _ftp_storage_dir += fileSeparator;
            }
			*/
        }
        if (!Utility.isSet(_password)) {
            messages.append("Value of 'password' is not defined. 'password' has been leaved empty.\r\n");
        } else {
            _password = _password.trim();
        }
    }
    
    private static boolean isIntValue(String src) {
        if (src == null) {
            return false;
        }
        boolean isOk = true;
        try {
            Integer.parseInt(src.trim());
        } catch (Exception ex) {
            isOk = false;
        }
        return isOk;
    }
}

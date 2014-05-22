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


public class FileBlobStorageImpl implements BlobStorage {
    private static final Logger log = Logger.getLogger(FileBlobStorageImpl.class);
    public static final String FTP_DEFAULT_PORT = "21";
    
    private static String fileSeparator = System.getProperty("file.separator");

    private boolean _initialized = false;
    private String  _storage_dir;

    @Override
    public boolean isInitialized() {
        return _initialized;
    }

    @Override
    public void initialize(Map<String, String> parameters) {

        _storage_dir = parameters.get(FILE_STORAGE_DIR);   
        
        StringBuilder buffer = new StringBuilder();
        boolean isOK = checkMandatoryParameters(buffer);
        
        if (isOK) {
            buffer.setLength(0);
            if (buffer.length() > 0) {
                log.info(buffer.toString());
            }
            
            buffer.setLength(0);
            buffer.append("Initialized with: \r\n");
            buffer.append("    storage_dir = ").append(_storage_dir).append("\r\n");
            
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
            log.info("[storeBlob] Trying to save file into filesystem");
            File dir = new File(_storage_dir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, filename);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(blob);
            bos.flush();
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {}
            }

            isOK = true;
        } catch (IOException e) {
            log.error("[storeBlob] ERROR saving blob into file: (" + filename + ")", e);
        }
        
        return isOK;
    }

    @Override
    public byte[] readBlob(String filename) {
        byte[] blob = null;

        try {
            File file = new File(_storage_dir, filename);
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
                throw new FileNotFoundException("File not found: " + filename);
            }
        } catch (IOException e) {
        	e.printStackTrace();
            log.error("[readBlob] ERROR reading blob", e);
        }

        return blob;
    }

    @Override
    public boolean removeBlob(String filename) {
        boolean isOK = false;
        try {
            File file = new File(_storage_dir, filename);
            if (file.exists()) {
                file.delete();
            } else {
                throw new FileNotFoundException("File not found: " + filename);
            }
        } catch (Exception e) {
            log.error("[removeBlob] ERROR removing blob", e);
        }
        
        return isOK;
    }

    @Override
    public String getStorageType() {
        return BlobStorageAccess.STORAGE_FILESYSTEM;
    }
    
    @Override
    public void disconnect() {}

    private boolean checkMandatoryParameters(StringBuilder messages) {
        boolean isOk = true;
        if (!Utility.isSet(_storage_dir)) {
            isOk = false;
            messages.append("Value of 'storage_dir' parameter is empty.\r\n");
        } else {
            _storage_dir = _storage_dir.trim();
            if (!_storage_dir.startsWith(fileSeparator)) {
                _storage_dir = fileSeparator + _storage_dir;
            }
            if (!_storage_dir.endsWith(fileSeparator)) {
                _storage_dir += fileSeparator;
            }
        }

        return isOk;
    }
}

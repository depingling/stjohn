package com.cleanwise.service.api.dao.blob.storage.factory;

import com.cleanwise.service.api.dao.blob.storage.BlobStorage;
import com.cleanwise.service.api.dao.blob.storage.impl.FileBlobStorageImpl;
import com.cleanwise.service.api.dao.blob.storage.impl.FtpBlobStorageImpl;
import com.cleanwise.service.api.dao.blob.storage.impl.NfsBlobStorageImpl;

public class BlobStorageFactory {

    private BlobStorageFactory() {};
    
    public static BlobStorage createFtpBlobStorage() {
        return new FtpBlobStorageImpl();
    }
    
    public static BlobStorage createFileBlobStorage() {
        return new FileBlobStorageImpl();
    }
    public static BlobStorage createNfsBlobStorage() {
        return new NfsBlobStorageImpl();
    }
}

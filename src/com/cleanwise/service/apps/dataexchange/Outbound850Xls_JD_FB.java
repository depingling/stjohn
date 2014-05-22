package com.cleanwise.service.apps.dataexchange;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.process.operations.Outbound850XLSBuilder_JD_FB;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.OutboundEDIRequestDataVector;
import com.cleanwise.service.api.value.StoreData;

public class Outbound850Xls_JD_FB extends Outbound850Xls {
	protected Logger log = Logger.getLogger(this.getClass());
    protected static String className = "Outbound850Xls_JD_FB";

    public File constructOutboundPurchaseOrder(DistributorData pDist,
            OutboundEDIRequestDataVector p850s, StoreData pStore, String logo)
            throws Exception {
        log("constructOutboundPurchaseOrder => begin");
        File tmp;
        BufferedOutputStream out;
        
        tmp = File.createTempFile("Attachment", ".xls");
        out = new BufferedOutputStream(new FileOutputStream(tmp));
        Outbound850XLSBuilder_JD_FB xls = new Outbound850XLSBuilder_JD_FB();
        
        xls.constructXlsPO(pDist, p850s, pStore, out);
        out.flush();
        log("constructOutboundPurchaseOrder => end");
        
        return tmp;
    }
}

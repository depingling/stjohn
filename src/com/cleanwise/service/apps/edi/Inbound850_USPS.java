package com.cleanwise.service.apps.edi;

import org.apache.log4j.Logger;


import com.americancoders.edi.*;

/**
 *<br>class 850 Purchase Order
 *<br>
 *This class is used to extract inbound 850 transaction from USPS.
 *USPS will send N1 segment on item level instead of header level like JC Penny
 *Cleanwise item information(e.g. cleanwise sku, cleanwise product description...)
 *on PO1 loop will be send which is different from JC Penny that send
 *customer item information. Duplcated order is not checked for USPS order
 */
public class Inbound850_USPS extends Inbound850Super {
	protected Logger log = Logger.getLogger(this.getClass());
    /**
     * extract all segments included in ST - SE Segment that we are intersted in
     */
    public void extract() throws OBOEException {
        Table table = ts.getHeaderTable();
        //find me
        Table dtable = ts.getDetailTable();
        if (dtable != null){
            extractDetailPO1(dtable);
        }
        if (table != null) {
            this.extractHeaderPER(table);
            this.extractHeaderBEG(table);
        }
    }

    //************************Constructor******************************
    public Inbound850_USPS()
    {
    }
}

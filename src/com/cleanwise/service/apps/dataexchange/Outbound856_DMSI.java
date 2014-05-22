/**
 *
 */
package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.io.BufferedWriter;

import org.apache.log4j.Logger;



/**
 * @author Ssharma
 *
 */
public class Outbound856_DMSI extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger logger = Logger.getLogger(this.getClass());
	private static final String className = "Outbound856_DMSI";

	BufferedWriter mOutput;
    private String mDoc;
    private StringBuffer log = new StringBuffer();
    private static SimpleDateFormat poDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public void buildTransactionContent() throws Exception {
    	mDoc = "";

    	OrderItemDataVector currItemV = currOutboundReq.getOrderItemDV();
    	OrderItemActionDataVector currItemActionV = currOutboundReq.getOrderItemActionDV();

    	logger.info("Items "+currItemV.size()+", Item actions "+currItemActionV.size());

    	String siteRefNum="";
    	PropertyDataVector sitePropDV = currOutboundReq.getSiteProperties();
    	if(sitePropDV != null) {
            for(Iterator propIt=sitePropDV.iterator(); propIt.hasNext();) {
                PropertyData propD = (PropertyData) propIt.next();
                if(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER
                              .equalsIgnoreCase(propD.getShortDesc())) {
                	siteRefNum = propD.getValue();

                }
            }
    	}

    	PurchaseOrderData purchaseOrderD = currOutboundReq.getPurchaseOrderD();

        Date poDate = purchaseOrderD.getPoDate();
        String poDateS = (poDate!=null)? poDateFormat.format(poDate):"";

        String poNum = Utility.strNN(purchaseOrderD.getOutboundPoNum());


    	int lineNum = 1;

    	for(int i=0; i<currItemV.size(); i++){

    		logger.info("currItemV: "+(currItemV.get(i)).toString());

    		OrderItemData oid = (OrderItemData)currItemV.get(i);
    		// get from property (cust ref code) for dist
    		String vendorNum = currOutboundReq.getDistributorCustomerReferenceCode();

    		String vendorItemName = oid.getDistItemSkuNum();
    		int poLineNum = oid.getErpPoLineNum();

	    	//int qty = oid.getTotalQuantityShipped(); // this qty may be different from qty ordered
	    	String uom = oid.getDistItemUom();

    		//update order_item_action_status
    		OrderItemActionData OIAD = (OrderItemActionData)currItemActionV.get(i);
    		int qty = OIAD.getQuantity();

    		OIAD.setStatus(RefCodeNames.EDI_TYPE_CD.T856_SENT);

    		appendIntegrationRequest(OIAD);

    		if(lineNum>1) {
                mDoc += "\r\n";
            }
            mDoc += formatData(siteRefNum )+
            ',' + formatData(vendorNum) +
            ',' + poNum +
            ',' + poLineNum +
            ',' + formatData(vendorItemName) +
            ',' + qty +
            ',' + uom +
            ',' + poDateS ;

            logger.info(formatData(siteRefNum )+
   	            ',' + formatData(vendorNum) +
	            ',' + poNum +
	            ',' + poLineNum +
	            ',' + formatData(vendorItemName) +
	            ',' + qty +
	            ',' + uom +
	            ',' + poDateS );

            lineNum++;

    	}

    	mDoc += "\r\n";//last line feed

        translator.writeOutputStream(mDoc);
    }


	private String formatData(String val){
		if(val == null || val.equals("")){
			return " ";
		}
		val = val.replaceAll(",","");
		return val;
	}

    public String getTranslationReport(){
    	return log.toString();
    }

    //may be left unimplemented (return null) and the default will be used
    public String getFileName()throws Exception{
    	//String fileName = "PORECEIVE_"+translator.getDateTimeAsString()+".csv";
    	String fileName = "PORECEIVE.csv";
    	logger.info("IN getFileName::"+fileName);
		return fileName;
    }

    //may be left unimplemented (return null) and the default will be used
    public String getFileExtension(OutboundTranslate pHandler)throws Exception{
    	return ".csv";
    }



}

package com.cleanwise.service.apps.dataexchange;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.tree.ReportFactory;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.tree.transformer.SimpleReportTransformer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Report;
import com.cleanwise.service.apps.dataexchange.OutboundTransaction;


public class OutboundPurchaseOrder_FBG extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
	private static final String className = "OutboundPurchaseOrder_FBG";
	private static final String note = "Sent to FBG";
    private Report reportEjb = null;
    
    private final static String REPORT_FACTORY_CLASS = "com.cleanwise.service.api.tree.xml.ReportXmlFactory";
        
    public OutboundPurchaseOrder_FBG(){
    	seperateFileForEachOutboundOrder = true;
    }
    
    public void buildTransactionContent()
    throws Exception {
    	int userId = 0;	 
		String userName = className;
		boolean savePrevVersion = true;
		if (reportEjb == null)
			reportEjb = Translator.getReportEjb();
		
    	currOrder = currOutboundReq.getOrderD();
        log.info("Processing order: "+currOrder.getOrderNum());  
        
        // generate XML order                 
    	Map params = new HashMap();
	    params.put("ORDER_NUM", currOrder.getOrderNum());     		        
	    ReportItem ser = (ReportItem) reportEjb.checkAnalyticReport(802,params,
	                        userId, userName, !savePrevVersion);
	    ReportFactory reportFactory = (ReportFactory) Class.forName(REPORT_FACTORY_CLASS).newInstance();
	    String str = reportFactory.transform(ser, new SimpleReportTransformer());
        getTranslator().writeOutputStream(str);
		
		// insert order notes 
    	OrderPropertyData OPD = OrderPropertyData.createValue();
    	OPD.setOrderId(currOrder.getOrderId());                
        OPD.setShortDesc(note);
        OPD.setValue(note);
        OPD.setOrderPropertyStatusCd("ACTIVE");
        OPD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        this.appendIntegrationRequest(OPD);
    }

    public String getTranslationReport(){
    	String str = "";

		if (this.isFail())
		{
			return "Outbound Purchase Order translation for FBG failed";
		}
		else
		{
			str = "Outbound Purchase Order translation for FBG is success.\r\n";
			str += "Set_Type  Order_Id    Filename\r\n";
			for (int i = 0; i < getInterchanges().size(); i++) {
				InterchangeRequestData interchangeReqD = (InterchangeRequestData)getInterchanges().get(i);
				for (int j = 0; j < interchangeReqD.getTransactionDataVector().size(); j++) {
					ElectronicTransactionData transactionD = (ElectronicTransactionData)interchangeReqD.getTransactionDataVector().get(j);
					str += "850       " + Utility.padRight(transactionD.getOrderId()+"", ' ', 12) 
						+ interchangeReqD.getInterchangeData().getEdiFileName()+ "\r\n";
				}
			}
		}
		return str;
    }

    //may be left unimplemented (return null) and the default will be used
    public String getFileName()throws Exception{
    	//use order number as name of file.  
    	String fileName = "Order_" + currOutboundReq.getOrderD().getOrderNum() + ".xml";
    	log.info("IN getFileName::"+fileName);
    	
    	return fileName;
    }

    //may be left unimplemented (return null) and the default will be used
    public String getFileExtension()throws Exception{
    	return "xml";
    }
}




package com.cleanwise.service.apps.dataexchange;

import java.util.Map;
import java.util.HashMap;


import org.apache.log4j.Logger;





import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.session.Report;
import com.cleanwise.service.api.tree.ReportFactory;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.tree.transformer.SimpleReportTransformer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ElectronicTransactionData;
import com.cleanwise.service.api.value.InterchangeRequestData;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.OrderPropertyData;


public class OutboundInvoice_FBG extends InterchangeOutboundSuper implements OutboundTransaction{

	protected Logger log = Logger.getLogger(this.getClass());
	private static final String className = "OutboundInvoice_FBG";
	
	private static final String note = "Sent to FBG";
    private Report reportEjb = null;
    
    private final static String REPORT_FACTORY_CLASS = "com.cleanwise.service.api.tree.xml.ReportXmlFactory";
    
    public OutboundInvoice_FBG(){
    	seperateFileForEachOutboundOrder = true;    	
    }
    
    public void buildTransactionContent()
    throws Exception {
    	int userId = 0;	 
		String userName = className;
		boolean savePrevVersion = true;
		if (reportEjb == null)
			reportEjb = Translator.getReportEjb();
		
		InvoiceDistData invoiceD = currOutboundReq.getInvoiceDistD();
        if(invoiceD == null){
            invoiceD = currOutboundReq.getInvoiceData().getInvoiceDistData();
        }
		log.info("Processing invoice: "+invoiceD.getInvoiceNum());
        
        // generate XML order                 
    	Map params = new HashMap();
	    params.put("INVOICE_NUM", invoiceD.getInvoiceNum());  
	    params.put("DISTRIBUTOR", invoiceD.getBusEntityId()+"");  	        
	    ReportItem ser = (ReportItem) reportEjb.checkAnalyticReport(742,params,
	                        userId, userName, !savePrevVersion);
	    ReportFactory reportFactory = (ReportFactory) Class.forName(REPORT_FACTORY_CLASS).newInstance();
	    String str = reportFactory.transform(ser, new SimpleReportTransformer());
	    getTranslator().writeOutputStream(str);
		
		// insert order notes 
    	OrderPropertyData OPD = OrderPropertyData.createValue();
    	OPD.setOrderId(invoiceD.getOrderId());  
    	OPD.setInvoiceDistId(invoiceD.getInvoiceDistId());  
        OPD.setShortDesc("Invoice Matching Note");
        OPD.setValue(note);
        OPD.setOrderPropertyStatusCd("ACTIVE");
        OPD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        appendIntegrationRequest(OPD);
        getTransactionObject().setKeyString("InvoiceNum: " + invoiceD.getInvoiceNum());
        getTransactionObject().setReferenceId(invoiceD.getInvoiceDistId());
        getTransactionObject().setReferenceTable(InvoiceDistDataAccess.CLW_INVOICE_DIST);

		if(currOutboundReq.getInvoiceData() != null){
			currOutboundReq.getInvoiceData().markAcknowledged();
            appendIntegrationRequest(currOutboundReq.getInvoiceData());
        }
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
					str += "810       " + Utility.padRight(transactionD.getOrderId()+"", ' ', 12) 
						+ interchangeReqD.getInterchangeData().getEdiFileName()+ "\r\n";
				}
			}
		}
		return str;
    }
    
    public String getFileName()throws Exception{
    	String fileName = null;
    	if(currOutboundReq.getInvoiceDistD() != null && currOutboundReq.getInvoiceDistD().getInvoiceNum() != null){
	    	//use order number as name of file.
	    	fileName = "Invoice_" + currOutboundReq.getInvoiceDistD().getInvoiceNum() + ".xml";
    	}else if(currOutboundReq.getInvoiceData() != null && currOutboundReq.getInvoiceData().getInvoiceNum() != null){
    		fileName = "Invoice_" + currOutboundReq.getInvoiceData().getInvoiceNum() + ".xml";
    	}else{
    		return null;
    	}
    	log.info("IN getFileName::"+fileName);
    	
    	return fileName;
    }

    //may be left unimplemented (return null) and the default will be used
    public String getFileExtension()throws Exception{
    	return "xml";
    }
}

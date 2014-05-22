package com.cleanwise.service.apps.dataexchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cleanwise.service.api.dao.InvoiceCustDataAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ElectronicTransactionData;
import com.cleanwise.service.api.value.InterchangeRequestData;
import com.cleanwise.service.api.value.InvoiceAbstractionView;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OutboundEDIRequestData;
import com.cleanwise.service.api.value.OutboundEDIRequestDataVector;
import com.cleanwise.service.api.value.OutboundEventData;

public class InterchangeOutboundSuper extends InterchangeSuper implements OutboundTransaction{
	protected OutboundEDIRequestDataVector transactionsToProcess;
	protected boolean seperateFileForEachOutboundOrder = false;
	protected OutboundEDIRequestData currOutboundReq;
	protected OrderData currOrder;
	protected OrderItemData currItem;
	protected OrderItemDataVector items = null;	
	protected OutboundEventData eventData = null;
		
	public void buildInterchangeHeader() throws Exception {
		createInterchangeObject();
		eventData = new OutboundEventData();
	}

	public void buildInterchangeTrailer() throws Exception {
		updateOutboundEvent();
	}
	/**
     * Where the data is actually written to the output stream
     */
    public void buildInterchangeContent() throws Exception {
		boolean first = true;	
        for (Iterator it = getTransactionsToProcess().iterator(); it.hasNext();){
        	currOutboundReq = (OutboundEDIRequestData) it.next();
            // create seperate files for each order
            if(seperateFileForEachOutboundOrder && !first){
				buildInterchangeTrailer();
                ((OutboundTranslate)getTranslator()).initializeOutputStream((OutboundTransaction)this);
                this.buildInterchangeHeader();
            }
            first = false;
            eventData.getEventRelatedData().add(currOutboundReq);
            buildTransaction();
        }
    }
	
	public void buildTransaction() throws Exception {
		buildTransactionHeader();
		buildTransactionContent();
		buildTransactionTrailer();		
	}

	public void buildTransactionHeader()
	throws Exception
	{
		transactionD = createTransactionObject();		
	}
	public void buildTransactionContent() throws Exception {}
	public void buildTransactionTrailer() throws Exception {
		if (currOutboundReq == null)
			return;
		currOrder = currOutboundReq.getOrderD();
		if (currOrder != null)
			transactionD.setOrderId(currOrder.getOrderId());
		if (transactionD.getKeyString() != null && !transactionD.getKeyString().equals(""))
			return;
		if (this instanceof InboundTransaction){
			if (getSetType().equals(RefCodeNames.EDI_TYPE_CD.T850)){
			}else if (getSetType().equals(RefCodeNames.EDI_TYPE_CD.T855)){
				
			}else if (getSetType().equals(RefCodeNames.EDI_TYPE_CD.T856)
					|| getSetType().equals(RefCodeNames.EDI_TYPE_CD.T810)){
				
			}
		}else if (this instanceof OutboundTransaction){
			if (getSetType().equals(RefCodeNames.EDI_TYPE_CD.T850) 
					|| getSetType().equals(RefCodeNames.EDI_TYPE_CD.TPUNCH_OUT_ORDER_OUT)){
				transactionD.setKeyString("OrderNum: " + currOrder.getOrderNum() + 
						", ErpOrderNumber: " + currOrder.getErpOrderNum() +
		                ", ErpPoNumber: " + ((OrderItemData)currOutboundReq.getOrderItemDV().get(0)).getErpPoNum() +
		                ", CustomerPoNumber: " + currOrder.getRequestPoNum()); 
			}else if (getSetType().equals(RefCodeNames.EDI_TYPE_CD.T855)){
				transactionD.setKeyString("ErpOrderNumber: " + currOrder.getErpOrderNum()
						+ ", FrontEndOrderNumber: " + currOrder.getOrderNum()
						+ ", CustomerPoNumber: " + currOrder.getRequestPoNum());
			}else if (getSetType().equals(RefCodeNames.EDI_TYPE_CD.T856)
					|| getSetType().equals(RefCodeNames.EDI_TYPE_CD.T810)){
				InvoiceAbstractionView currInvoice = currOutboundReq.getInvoiceData();
				transactionD.setReferenceId(currInvoice.getInvoiceCustId());
				transactionD.setReferenceTable(InvoiceCustDataAccess.CLW_INVOICE_CUST);
				transactionD.setKeyString("ErpOrderNum: " + currOrder.getErpOrderNum()
						+ ", FrontEndOrderNumber: " + currOrder.getOrderNum()
						+ ", CustomerPoNumber: " + currOrder.getRequestPoNum()
						+ ", InvoiceNumber: " + currInvoice.getInvoiceNum());
			}
		}
	}
	
	public void setTransactionsToProcess(OutboundEDIRequestDataVector transactions) {
		transactionsToProcess = transactions;
		if (transactions != null){
    		currOutboundReq = (OutboundEDIRequestData) transactions.get(0);
    	}    	
	}
	
	public OutboundEDIRequestDataVector getTransactionsToProcess() {
		return transactionsToProcess;		
	}	
	
	public String getTranslationReport() {
		String str = "";
		String directionStr;
		if (this instanceof InboundTransaction){
			directionStr = "Inbound";
		}
		else
			directionStr = "Outbound";

		if (this.isFail())
		{
			return (directionStr + " translation failed");
		}
		else
		{
			str = str + directionStr + " translation is success.\r\n";
			if (this instanceof InboundTransaction){
				str += "Inbound filename: " + getTranslator().getInputFilename() + "\r\n\r\n";
			}else{
				str += "Outbound filename: " + getTranslator().getOutputFileName() + "\r\n\r\n";
			}
			str += "Sender          Receiver        Set_Type  Group_Control_Num  ";
			if (currOrder != null)
				str += "OrderId   ";//10
			if (getSetType().equals(RefCodeNames.EDI_TYPE_CD.T810))
				str += "InvoiceNum  ";//12
			str += "\r\n";
						
			for (int i = 0; i < getInterchanges().size(); i++) {
				InterchangeRequestData interchangeReqD = (InterchangeRequestData)getInterchanges().get(i);
				for (int j = 0; j < interchangeReqD.getTransactionDataVector().size(); j++) {
					ElectronicTransactionData transactionD = (ElectronicTransactionData)interchangeReqD.getTransactionDataVector().get(j);
					str += Utility.padRight(transactionD.getGroupSender(), ' ', 16)
					+ Utility.padRight(transactionD.getGroupReceiver(), ' ', 16)
					+ Utility.padRight(transactionD.getSetType(), ' ', 10)
					+ Utility.padRight(""+transactionD.getGroupControlNumber(), ' ', 19);
					if (currOrder != null)
						str += Utility.padRight(transactionD.getOrderId()+"", ' ', 12);
					if (getSetType().equals(RefCodeNames.EDI_TYPE_CD.T810)){
						if (getInterchanges().size() == 1){
							OutboundEDIRequestData req = (OutboundEDIRequestData)getTransactionsToProcess().get(j);						
							str += Utility.padRight(req.getInvoiceData().getInvoiceNum()+"", ' ', 12);
						}
					}
					str += "\r\n";
				}
			}
		}
		str += "\r\n";
		return str;
	}
	
	protected void updateOutboundEvent(){
		eventData.setFileName(translator.getOutputFileName());
		eventData.setPartnerKey(translator.getProfile().getGroupReceiver()+"");
		eventData.setSetType(this.getSetType());
		eventData.setByteArray(translator.getOutputStream().toByteArray());
		if (getSetType().equals("997")){
			// get parameter map from inbound job
			Map parameterMap = ((OutboundTranslate)getTranslator()).getSendParameterMap();
			
			// set outbound parameter map for sending back 997 file
			Map sendParameterMap = new HashMap();
			((OutboundTranslate)getTranslator()).setSendParameterMap(sendParameterMap);
			
			if (parameterMap != null && parameterMap.size() >= 3){
				String tohost = (String)parameterMap.get("fromhost");
				String port = (String)parameterMap.get("port");
				String username = (String)parameterMap.get("username");
				String password = (String)parameterMap.get("password");
				String ftpmode = (String)parameterMap.get("ftpmode");
				String todir = (String)parameterMap.get("acknowledgedir");
				sendParameterMap.put("tohost", tohost);
			    sendParameterMap.put("username", username);
			    sendParameterMap.put("password", password);
			    sendParameterMap.put("exceptionOnFileExists", "false");
			    if (port != null)
			    	sendParameterMap.put("port", port);
			    if (ftpmode != null)
			    	sendParameterMap.put("ftpmode", ftpmode);
			    if (todir != null)
			    	sendParameterMap.put("todir", todir);
			    if ((String)parameterMap.get("transfer_type") != null)
			    	sendParameterMap.put("transfer_type", (String)parameterMap.get("transfer_type"));
			    sendParameterMap.put("pParentEventId", parameterMap.get("pParentEventId"));
			}
		    /*sendParameterMap.put("tohost", "ftps3.easylink.com");
		    sendParameterMap.put("username", "clwftp");
		    sendParameterMap.put("password", "fowahete");
		    sendParameterMap.put("ftpmode", "active");*/
		    ((OutboundTranslate)getTranslator()).setSendParameterMap(sendParameterMap);
		}
        setExtraSendParameters();
        eventData.setSendParameterMap(((OutboundTranslate)getTranslator()).getSendParameterMap());
		appendIntegrationRequest(eventData);
	}


    //default will be used. the subclass can modify this
	public String getFileExtension()throws Exception{
		return null;
	}
	
	// default will be used. the subclass can modify this
	public String getFileName() throws Exception {
		return null;
	}

    // the subclass can add or change send parameters
    public void setExtraSendParameters() {
    }

    public void writeRowToOutputStream(List pRow, char quoteChar) throws IOException{
		translator.writeOutputStream(Utility.toCommaSting(pRow, quoteChar));
	}
	public void writeRowToOutputStream(List pRow) throws IOException{
		translator.writeOutputStream(Utility.toCommaSting(pRow));
	}
	
	// overwrite this if you want certain counter format or make it a empty string.
	// the default is 001, 002..... 999 from OutboundTranslate class
	public String getCounterStr(){
		return null;
	}
}

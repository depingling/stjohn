package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.process.operations.Outbound850XLSTargetBuilder;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.APIAccess;

import java.io.*;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.*;

import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentFactory;


public class Outbound850XlsTarget extends InterchangeOutboundSuper implements OutboundTransaction {
    protected Logger logger = Logger.getLogger(this.getClass());

    private final String FILE_EXTENSION = ".xls";
    private final String EMAIL_SUBJECT_PARAMETER_NAME = "emailsubject";
    private final String EMAIL_SUBJECT_TOKEN = "@today@";
    private final String EMAIL_SUBJECT_PARAMETER_PATTERN = "ORDERLINE SYSTEM:  Orders As of " + EMAIL_SUBJECT_TOKEN + " 5:00 PM EST";


    private Outbound850XLSTargetBuilder xls;
    private Outbound850XLSTargetBuilder.XlsPoResultTarget xlsResult;

    private final static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");
    private static final BigDecimal ZERO = new BigDecimal(0);
    private String customerRefCode = null;

    public Outbound850XlsTarget(){
        seperateFileForEachOutboundOrder = false;
    }
    
    public void buildInterchangeContent() throws Exception {
    	log("build => begin");
        
        OutboundEDIRequestDataVector p850s = getTransactionsToProcess();
        xls = new Outbound850XLSTargetBuilder();
        xlsResult = xls.makeXlsResult(getCustomerReferenceCode(), p850s);
        for (int i = 0; i < p850s.size(); i++){
        	currOutboundReq = (OutboundEDIRequestData) p850s.get(i);
        	eventData.getEventRelatedData().add(currOutboundReq);
        	OrderItemDataVector itemDV = currOutboundReq.getOrderItemDV();
        	for (int j = 0; j < itemDV.size(); j++){
        		currItem = (OrderItemData) itemDV.get(j);
        		currItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
                appendIntegrationRequest(currItem); // for update the status
        	}
        	
        	currOutboundReq.getPurchaseOrderD().setPurchaseOrderStatusCd(
                    RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
            appendIntegrationRequest(currOutboundReq.getPurchaseOrderD());
        }
        xls.outXls(xlsResult, getTranslator().getOutputStream());    	
    }
    
    // add or replace emailsubject parameter of event
    public void setExtraSendParameters() {
        Map parameterMap = ((OutboundTranslate)getTranslator()).getSendParameterMap();
        String subject = (String)parameterMap.get(EMAIL_SUBJECT_PARAMETER_NAME);
        if (!Utility.isSet(subject) || (subject.indexOf(EMAIL_SUBJECT_TOKEN) < 0)) {
            subject = EMAIL_SUBJECT_PARAMETER_PATTERN;
        }
        String orderDate = dateFormatter.format(new Date());
        subject = Utility.replaceString(subject, EMAIL_SUBJECT_TOKEN, orderDate);

        parameterMap.put(EMAIL_SUBJECT_PARAMETER_NAME, subject);
        ((OutboundTranslate)getTranslator()).setSendParameterMap(parameterMap);
    }



    private StoreData getStore(int storeId) throws Exception  {
        Store storeEjb = APIAccess.getAPIAccess().getStoreAPI();
        return storeEjb.getStore(storeId);
    }

    private String getCustomerReferenceCode() throws Exception{
    	if (customerRefCode == null){
    		Distributor distEjb = APIAccess.getAPIAccess().getDistributorAPI();
    		customerRefCode = distEjb.getDistributorByErpNum(getDistErpNum()).getCustomerReferenceCode();
    	}
        return customerRefCode;
    }

    public String getDistErpNum() {
        return currOutboundReq.getPurchaseOrderD().getDistErpNum();
    }

    public String getFileExtension() throws Exception{
        return FILE_EXTENSION;
    }

    public String getFileName() throws Exception {
        SimpleDateFormat frmt = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String now = frmt.format(new java.util.Date());
        String fileName = "po" + getSetType() + "_" + now + getFileExtension();
        return fileName;
    }


    /**
     * Logging
     * @param message - message which will be pasted to log
     */
    protected void log(String message){
        logger.info(message);
    }

}

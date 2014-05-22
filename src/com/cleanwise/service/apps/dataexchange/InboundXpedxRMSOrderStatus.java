package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;

import java.io.IOException;
import java.util.Iterator;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;


/**
 * InboundXML855
 *
 * @author Evgeny Vlasov
 */
public class InboundXpedxRMSOrderStatus extends InboundXML{
	protected Logger log = Logger.getLogger(this.getClass());
    public void translate(org.dom4j.Node nodeToTranslate) throws Exception {
        super.translate(nodeToTranslate);
        InboundXMLData inData = extract(document);
        process(inData);
    }

    private boolean checkTransaction(SOTrans trans) {

        if (!trans.getLine().isEmpty()) {
            //////////////////////////////////
            String shipToName = trans.getShipToName();
            if (!Utility.isSet(shipToName)) {
            	appendErrorMsgs("Missing shipToName in the ShipToName node", false);
            }
            /////////////////////////////////
            String address1 = trans.getAddrtess1();
            if (!Utility.isSet(address1)) {
            	appendErrorMsgs("Missing address1 in the ADDRESS1 node", false);
            }
            //////////////////////////////////
            String city = trans.getCity();
            if (!Utility.isSet(city)) {
            	appendErrorMsgs("Missing city in the CITY node", false);
            }
            /////////////////////////////////
            String state = trans.getState();
            if (!Utility.isSet(state)) {
            	appendErrorMsgs("Missing state in the STATE node", false);
            }
            ////////////////////////////////////
            String zip = trans.getZipCode();
            if (!Utility.isSet(zip)) {
            	appendErrorMsgs("Missingzip in the ZIPCOD node", false);
            }
            //////////////////////////////////////
            String country = trans.getCountry();
            if (!Utility.isSet(country)) {
            	appendErrorMsgs("Missing country in the COUNTRY node", false);
            }
            Iterator it2 = trans.getLine().iterator();
            int lineNumber = 0;

            while (it2.hasNext()) {
                Line line = (Line) it2.next();
                lineNumber++;
                //////////////////////////////////////
                String distItemSku = line.getItemNumber();
                if (!Utility.isSet(distItemSku)) {
                	appendErrorMsgs("Missing distItemSku in the ITEMNMBR node.Line number  " + lineNumber, true);
                }
            }
        }
        return (getErrorMsgs().size() == 0);
    }

    private void process(InboundXMLData inData) throws Exception {
        log.info("process => begin");
        xmlHandler.createInterchangeObject();
        SOTransVector transVector = inData.getEConnectData().getSOTrans();
        Iterator itTrans = transVector.iterator();

        while (itTrans.hasNext()) {
            SOTrans transaction = (SOTrans) itTrans.next();

            if("3".equals(transaction.getSOPType())){
            	//invoice
            	log.info("Not processing, this is an invoice");
            	return;
            }
            if (checkTransaction(transaction)) {
                try {
                    AcknowledgeRequestData reqAcknowledgement=AcknowledgeRequestData.createValue();
                    String orderNumber=null;
                    prepareTransactionData(transaction,reqAcknowledgement,orderNumber);
                    processTransaction(reqAcknowledgement,orderNumber);
                } catch (Exception e) {
                	appendErrorMsgs(e, true);
                	log.error(e.getMessage(),e);
                    e.printStackTrace();
                }
            }
        }
    }

    private void prepareTransactionData(SOTrans trans, AcknowledgeRequestData reqAcknowledgement, String orderNumber) throws Exception {
        log.info("prepareTransactionData trans => "+ trans);
        //SOPNUMBE or ORIGNUMB ?
        log.info("trans.getSOPNumber()="+trans.getSOPNumber());
        String sopNum = trans.getSOPNumber().trim();
        if(sopNum.startsWith("W")) {
            sopNum = sopNum.substring(1);
        }
        int ix = sopNum.lastIndexOf("/");        
        if (ix > 0) {
            reqAcknowledgement.setErpPoNum(sopNum.substring(0, ix));
            //orderNumber = sopNum.substring(ix); no effect - YK
        } else {
            reqAcknowledgement.setErpPoNum(sopNum);
        }
        //SOPNUMBE or ORIGNUMB ?
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        
        reqAcknowledgement.setVendorOrderNum(trans.getSOPNumber());
        reqAcknowledgement.setAckDate(dateFormat.parse(trans.getDocDate()));

        AckItemDataVector reqAckItems = new AckItemDataVector();
        if (!trans.getLine().isEmpty()) {

            Iterator it2 = trans.getLine().iterator();

            while (it2.hasNext()) {
                AckItemData reqAckItem = AckItemData.createValue();
                Line line = (Line) it2.next();

                reqAckItem.setLineNum(-1);
                reqAckItem.setAction("AA"); //Accepted
                reqAckItem.setDistSkuNum(line.getItemNumber());
                reqAckItem.setQuantity((int) Double.parseDouble(line.getQuantity()));
                reqAckItem.setUom(line.getUOFM());
                reqAckItem.setPrice(new BigDecimal(line.getmUnitPrice()));

                reqAckItems.add(reqAckItem);
            }
        }

        reqAcknowledgement.setAckItemDV(reqAckItems);
    }

    private void processTransaction(AcknowledgeRequestData reqAcknowledgement, String orderNumber) 
    throws Exception {

    	ElectronicTransactionData transactionD = xmlHandler.createTransactionObject();  
    	OrderData orderD = getTranslator().getOrderDataByErpPoNum(reqAcknowledgement.getErpPoNum());
        int orderId = 0;
        int storeId = 0;

        if(orderD!=null) {
            orderId = orderD.getOrderId();
            storeId = orderD.getStoreId();
        }

        transactionD.setKeyString("ErpPoNum: " + reqAcknowledgement.getErpPoNum()
                + ", orderNum: " + orderNumber
                + ", custOrderNum: " //? custOrderNum
                + ", vendorOrderNum: " + reqAcknowledgement.getVendorOrderNum());
        reqAcknowledgement.setStoreId(storeId);
        xmlHandler.appendIntegrationRequest(reqAcknowledgement);
    }
}

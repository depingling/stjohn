/*
 * InboundcXMLOrder.java
 * 
 */

package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.value.*;

import org.apache.log4j.Logger;

import org.dom4j.*;

import com.cleanwise.service.api.util.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class InboundcXML856 extends InboundXMLSuper {
	protected Logger log = Logger.getLogger(this.getClass());

	private String dateFormatStr = "yyyy-MM-dd'T'HH:mm:ss";

	// Processing variables
	protected EdiInp856View ediInp856Vw = null;
	protected EdiInp856ItemView ediInp856ItemVw = null;

	/*
	 * This method is called for each <shipNoticeRequest> element from
	 * InboundXMLHandler.doTranslation().
	 */
	public void translate(Node nodeToOperateOn) throws Exception {
		try {

			// check security info
			Node credentialNode = nodeToOperateOn.getDocument().selectSingleNode(
					"//cXML/Header/Sender/Credential");
			if (credentialNode == null) {
				throw new Exception("Could not parse cXML (Credential)");
			}

			Node passNameNode = credentialNode.selectSingleNode("SharedSecret");
			if (passNameNode == null) {
				throw new Exception("Could not parse cXML (SharedSecret)");
			}

			String pass = passNameNode.getText();

			String passval = mPassword;
			if (!pass.equalsIgnoreCase(passval)) {
				throw new Exception("Authorization failed.  Check username and trading profile authorization setup.");
			}

			parse(nodeToOperateOn, "InboundcXML856");
		} catch (Exception e) {
			appendErrorMsgs(e, true);
		}

	}

	/**
	 * parse a <shipNoticeRequest> into a EdiInp856View objects.
	 */
	private void parse(Node pShipNoticeRequestNode, String pUserName)
			throws Exception {
		

		Node shipNoticeHeaderNode = pShipNoticeRequestNode
				.selectSingleNode("ShipNoticeHeader");

		Node shipControlNode = pShipNoticeRequestNode
				.selectSingleNode("ShipControl");

		int i = 1;
		Iterator shipNoticePortionIt = pShipNoticeRequestNode.selectNodes(
				"ShipNoticePortion").iterator();
		while (shipNoticePortionIt.hasNext()) {
			Node shipNoticePortionNode = (Node) shipNoticePortionIt.next();
			extractShipNoticePortion(shipNoticePortionNode);
			extractShipNoticeHeader(shipNoticeHeaderNode);
			xmlHandler.appendIntegrationRequest(ediInp856Vw);
		}

	
	}

	public void extractShipNoticeHeader(Node pShipNoticeHeaderNode) {

		String shipmentDateS = pShipNoticeHeaderNode.valueOf("@shipmentDate");
		if (!Utility.isSet(shipmentDateS)) {
			errorMsgs.add("Shipment Date is null");
			setValid(false);
			return;
		}

		Date shipmentDate = null;
		try {
			shipmentDate = Utility.parseDate(shipmentDateS, dateFormatStr, true, true);
		} catch (Exception exc) {
			errorMsgs.add("Invalid value of Shipment Date: " + shipmentDateS);
			setValid(false);
			return;
		}
		ediInp856Vw.setShipDate(shipmentDate);

		String noticeDateS = pShipNoticeHeaderNode.valueOf("@noticeDate");
		if (!Utility.isSet(noticeDateS)) {
			errorMsgs.add("Notice Date is null");
			setValid(false);
			return;
		}
		Date noticeDate = null;
		try {
			noticeDate = Utility.parseDate(noticeDateS, dateFormatStr,true, true);
		} catch (Exception exc) {
			errorMsgs.add("Invalid value of Notice Date: " + noticeDateS);
			setValid(false);
			return;
		}
		ediInp856Vw.setTransactionDate(noticeDate);

		return;

	}

	public void extractShipNoticePortion(Node pshipNoticePortionNode) {
		ediInp856Vw = EdiInp856View.createValue();
		ediInp856Vw.setItems(new EdiInp856ItemViewVector());
		ediInp856ItemVw = EdiInp856ItemView.createValue();
		ediInp856Vw.getItems().add(ediInp856ItemVw);

		Node deliveryRefNode = pshipNoticePortionNode
				.selectSingleNode("DeliveryReference");
		String deliveryReference = deliveryRefNode.getText();
		//ediInp856Vw.setTrackingNum(deliveryReference);
		//Delete this if deliveryReference is not tracking num.
		ArrayList trackingNums = new ArrayList();
		trackingNums.add(deliveryReference);
		//ediInp856ItemVw.setTrackingNumList(trackingNums);
		
		ediInp856ItemVw.setDeliveryReference(deliveryReference);

		Node orderRefNode = pshipNoticePortionNode
				.selectSingleNode("OrderReference");
		String poNum = orderRefNode.valueOf("@orderID");
		int cIndex = poNum.indexOf(',');
		if(cIndex > -1){
			poNum = poNum.substring(0, cIndex);
		}
		ediInp856Vw.setPurchOrderNum(poNum);

		Node shipNoticeItemNode = pshipNoticePortionNode
				.selectSingleNode("ShipNoticeItem");
		if (shipNoticeItemNode == null) {
			errorMsgs.add("ShipNoticeItem node missing");
			setValid(false);
			return;
		}

		String orderLineNumS = shipNoticeItemNode.valueOf("@lineNumber");
		// if(orderLineNumS==null && !ignoreMissingLineInfo) {
		String msg = "Order Line Number in ShipNoticeItem node is null";

		if (validateAndAppendErrorMsgs(orderLineNumS, msg)) {
			int orderLineNum = 0;
			try {
				orderLineNum = Integer.parseInt(orderLineNumS);
			} catch (Exception exc) {
				errorMsgs
						.add("Invalid value of order line number in ShipNoticeItem node: "
								+ orderLineNumS);
				setValid(false);
				return;

			}
			ediInp856ItemVw.setPurchOrderLineNum(orderLineNum);
		}

		String shippedQtyS = shipNoticeItemNode.valueOf("@quantity");
		msg = "quantity attribute in ShipNoticeItem node is null";

		if (validateAndAppendErrorMsgs(shippedQtyS, msg)) {
			int shippedQty = 0;
			try {
				shippedQty = Integer.parseInt(shippedQtyS);
			} catch (Exception exc) {
				appendErrorMsgs("Invalid value of quantity in ShipNoticeItem node: "
						+ shippedQtyS, true);
			}
			ediInp856ItemVw.setShippedQty(shippedQty);
		}

		Node UnitOfMeasureNode = shipNoticeItemNode
				.selectSingleNode("UnitOfMeasure");

		if (UnitOfMeasureNode == null) {
			appendErrorMsgs("UnitOfMeasureNode is null " + orderLineNumS, true);
		} else {
			String uom = UnitOfMeasureNode.getText();
			if (validateAndAppendErrorMsgs(uom, "UOM is null")) {
				ediInp856ItemVw.setUom(uom);
			}
		}
		return;
	}
	
}

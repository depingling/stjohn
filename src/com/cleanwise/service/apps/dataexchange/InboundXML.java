package com.cleanwise.service.apps.dataexchange;

import org.apache.log4j.Logger;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class InboundXML extends InboundXMLSuper {
	protected Logger log = Logger.getLogger(this.getClass());
    public Document document;
    public InboundXMLData mInboundData;

    public InboundXML() {
        mInboundData = new InboundXMLData();
    }
    
    protected void translate(org.dom4j.Node nodeToTranslate) throws Exception {
    	log.info("tranlsate() => begin");

        try {
        	String s = nodeToTranslate.asXML();
            ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes());
            log.info("tranlsate() => parse stream");
            document = parseStream(new BufferedInputStream(bais));

        } catch (Exception e) {
        	appendErrorMsgs("Could not parse request node", true);
            return;
        }		
	}

    public InboundXMLData extract(Document document) {
    	log.info("extract() => begin" );
        Node rootNode = document.getDocumentElement();

        NodeList kids = rootNode.getChildNodes();
        if(kids == null || kids.getLength() == 0){
        	log.info("empty xml document");
        	return mInboundData;
        }
        Node node = findStartNode(rootNode);
        if(node!=null) {
        	processEConnectNode(node);
        } else {
        	appendErrorMsgs("ERROR: Can't find <econnect> tag in input file", true);
        }
        return mInboundData;
    }

    private Node findStartNode (Node pNode) {
log.info("findStartNode-->pNode.getNodeName(): "+pNode.getNodeName());
        if("econnect".equalsIgnoreCase(pNode.getNodeName())) {
            return pNode;
        }
        Node retNode = null;
        NodeList clildren = pNode.getChildNodes();
        for(int ii=0; ii<clildren.getLength(); ii++) {
            Node node = clildren.item(ii);
            retNode = findStartNode(node);
            if(retNode!=null) {
                break;
            }
        }
        return retNode;
    }




    private void processEConnectNode(Node eConnect){
    	log.info("proce3ssing econnect "+eConnect.getNodeName());
        NamedNodeMap attributeNodes = eConnect.getAttributes();

        log.info("About to loop through attributes!"+attributeNodes.getLength());
        for (int j = 0; j < attributeNodes.getLength(); j++) {

            Attr attribute = (Attr) attributeNodes.item(j);

            log.info(attribute.getNodeName());
            if ("ACTION".equals(attribute.getNodeName())) {
                mInboundData.getEConnectData().setAction(attribute.getNodeValue());
            } else if ("Requester_DOCTYPE".equals(attribute.getNodeName())) {
                mInboundData.getEConnectData().setRequesterDOCTYPE(attribute.getNodeValue());
            } else if ("DBNAME".equals(attribute.getNodeName())) {
                mInboundData.getEConnectData().setDBName(attribute.getNodeValue());
            } else if ("TABLENAME".equals(attribute.getNodeName())) {
                mInboundData.getEConnectData().setTableName(attribute.getNodeValue());
            } else if ("DATE1".equals(attribute.getNodeName())) {
                mInboundData.getEConnectData().setDate1(attribute.getNodeValue());
            } else if ("SOPNUMBE".equals(attribute.getNodeName())) {
                mInboundData.getEConnectData().setSOPNumber(attribute.getNodeValue());
            } else if ("SOPTYPE".equals(attribute.getNodeName())) {
                mInboundData.getEConnectData().setSOPType(attribute.getNodeValue());
            }
        }

        NodeList listSOTrans = eConnect.getChildNodes();
log.info("listSOTrans.getLength(): "+listSOTrans.getLength());
        for (int listSOTransIdx = 0; listSOTransIdx < listSOTrans.getLength(); listSOTransIdx++) {
            Node nodeTrans = listSOTrans.item(listSOTransIdx);
log.info("nodeTrans: "+nodeTrans.getNodeName());
            NodeList detailsTrans = nodeTrans.getChildNodes();
            InboundXML.SOTrans trans = new InboundXML.SOTrans();

            for (int detailsTransIdx = 0; detailsTransIdx < detailsTrans.getLength(); detailsTransIdx++) {

                Node param = detailsTrans.item(detailsTransIdx);

                if ("SOPNUMBE".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setSOPNumber(param.getChildNodes().item(0).getNodeValue());
                        log.info("prder="+trans.getSOPNumber());
                    }
                } else if ("SOPTYPE".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setSOPType(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("SOPTYPE".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setSOPType(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("ORIGNUMB".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setOrigNumber(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("DOCDATE".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setDocDate(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("INVODATE".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setInvoiceDate(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("ACTLSHIP".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setACTLShip(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("ShipToName".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setShipToName(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("ADDRESS1".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setAddrtess1(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("ADDRESS2".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setAddrtess2(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("ADDRESS3".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setAddrtess3(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("CITY".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setCity(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("STATE".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setState(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("ZIPCODE".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setZipCode(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("COUNTRY".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setCountry(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("SHIPMTHD".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setShipMethod(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("SUBTOTAL".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setSubTotal(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("FRTAMNT".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setFrtAmount(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("MISCAMNT".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setMiscAmount(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("TAXAMNT".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setTaxAmount(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("DOCAMNT".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        trans.setDocAmount(param.getChildNodes().item(0).getNodeValue());
                    }
                } else if ("Line".equals(param.getNodeName())) {
                    if (param.hasChildNodes()) {
                        InboundXML.Line line = extractLine(param);
                        trans.getLine().add(line);
                    }
                }
            }

            mInboundData.getEConnectData().getSOTrans().add(trans);
        }
    }



    private Line extractLine(Node lineNode) {

        NodeList lineDetail = lineNode.getChildNodes();
        Line line = new Line();

        for (int i = 0; i < lineDetail.getLength(); i++) {

            Node param = lineDetail.item(i);

            if ("ITEMNMBR".equals(param.getNodeName())) {
                if (param.hasChildNodes()) {
                    line.setItemNumber(param.getChildNodes().item(0).getNodeValue());
                }
            } else if ("ITEMDESC".equals(param.getNodeName())) {
                if (param.hasChildNodes()) {
                    line.setItemDesc(param.getChildNodes().item(0).getNodeValue());
                }
            } else if ("UOFM".equals(param.getNodeName())) {
                if (param.hasChildNodes()) {
                    line.setUOFM(param.getChildNodes().item(0).getNodeValue());
                }
            } else if ("UNITPRCE".equals(param.getNodeName())) {
                if (param.hasChildNodes()) {
                    line.setUnitPrice(param.getChildNodes().item(0).getNodeValue());
                }
            } else if ("XTNDPRCE".equals(param.getNodeName())) {
                if (param.hasChildNodes()) {
                    line.setXTNDPrice(param.getChildNodes().item(0).getNodeValue());
                }
            } else if ("QUANTITY".equals(param.getNodeName())) {
                if (param.hasChildNodes()) {
                    line.setQuantity(param.getChildNodes().item(0).getNodeValue());
                }
            }
        }
        return line;
    }

    public InboundXMLData getInboundData() {
        return mInboundData;
    }

    public void setInboundData(InboundXMLData inboundData) {
        this.mInboundData = inboundData;
    }

    private Document parseStream(BufferedInputStream is) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new XMLErrorHandler());
  log.info("builder: "+builder);
        return builder.parse(is);
    }

    public class InboundXMLData {
        public EConnectData _eConnectData;


        public InboundXMLData() {
            _eConnectData = new EConnectData();
        }

        public EConnectData getEConnectData() {
            return _eConnectData;
        }

        public void setEConnectData(EConnectData eConnectData) {
            this._eConnectData = eConnectData;
        }
    }

    public class SOTrans {

        public String mSOPNumber;
        public String mSOPType;
        public String mOrigNumber;
        public String mInvoiceDate;
        public String mReqShipDate;
        public String mACTLShip;//856
        public String mShipToName;//810
        public String mAddrtess1;
        public String mAddrtess2;
        public String mAddrtess3;
        public String mCity;
        public String mState;
        public String mZipCode;
        public String mCountry;
        public String mShipMethod;
        public String mSubTotal;
        public String mFrtAmount;
        public String mTaxAmount;
        public String mDocAmount;
        public LineVector mLine;
        public String mDocDate;
        public String mMiscAmount;


        public SOTrans() {
            mLine = new LineVector();
        }

        public String getSOPNumber() {
            return mSOPNumber;
        }

        public void setSOPNumber(String pValue) {
            this.mSOPNumber = pValue;
        }

        public String getSOPType() {
            return mSOPType;
        }

        public void setSOPType(String pValue) {
            this.mSOPType = pValue;
        }

        public String getOrigNumber() {
            return mOrigNumber;
        }

        public void setOrigNumber(String pValue) {
            this.mOrigNumber = pValue;
        }

        public String getInvoiceDate() {
            return mInvoiceDate;
        }

        public void setInvoiceDate(String pValue) {
            this.mInvoiceDate = pValue;
        }

        public String getReqShipDate() {
            return mReqShipDate;
        }

        public void setReqShipDate(String pValue) {
            this.mReqShipDate = pValue;
        }

        public String getACTLShip() {
            return mACTLShip;
        }

        public void setACTLShip(String pValue) {
            this.mACTLShip = pValue;
        }

        public String getShipToName() {
            return mShipToName;
        }

        public void setShipToName(String pValue) {
            this.mShipToName = pValue;
        }

        public String getAddrtess1() {
            return mAddrtess1;
        }

        public void setAddrtess1(String pValue) {
            this.mAddrtess1 = pValue;
        }

        public String getAddrtess2() {
            return mAddrtess2;
        }

        public void setAddrtess2(String pValue) {
            this.mAddrtess2 = pValue;
        }

        public String getAddrtess3() {
            return mAddrtess3;
        }

        public void setAddrtess3(String pValue) {
            this.mAddrtess3 = pValue;
        }

        public String getCity() {
            return mCity;
        }

        public void setCity(String pValue) {
            this.mCity = pValue;
        }

        public String getState() {
            return mState;
        }

        public void setState(String pValue) {
            this.mState = pValue;
        }

        public String getZipCode() {
            return mZipCode;
        }

        public void setZipCode(String pValue) {
            this.mZipCode = pValue;
        }

        public String getCountry() {
            return mCountry;
        }

        public void setCountry(String pValue) {
            this.mCountry = pValue;
        }

        public String getSubTotal() {
            return mSubTotal;
        }

        public void setSubTotal(String pValue) {
            this.mSubTotal = pValue;
        }

        public String getShipMethod() {
            return mShipMethod;
        }

        public void setShipMethod(String pValue) {
            this.mShipMethod = pValue;
        }

        public String getFrtAmount() {
            return mFrtAmount;
        }

        public void setFrtAmount(String pValue) {
            this.mFrtAmount = pValue;
        }

        public String getTaxAmount() {
            return mTaxAmount;
        }

        public void setTaxAmount(String pValue) {
            this.mTaxAmount = pValue;
        }

        public String getDocAmount() {
            return mDocAmount;
        }

        public void setDocAmount(String pValue) {
            this.mDocAmount = pValue;
        }

        public LineVector getLine() {
            return mLine;
        }

        public void setLine(LineVector pValue) {
            this.mLine = pValue;
        }


        public void setDocDate(String pValue) {
            this.mDocDate = pValue;
        }

        public String getDocDate() {
            return this.mDocDate;
        }

        public String getMiscAmount() {
            return this.mMiscAmount;
        }

        public void setMiscAmount(String pValue) {
            this.mMiscAmount = pValue;
        }

    }

    public class SOTransVector extends ArrayList {}

    public class EConnectData {
        public String mAction;
        public String mRequesterDOCTYPE;
        public String mDBName;
        public String mTableName;
        public String mDate1;
        public String mSOPNumber;
        public String mSOPType;
        public SOTransVector mSOTrans;


        public String getRequesterDOCTYPE() {
            return mRequesterDOCTYPE;
        }

        public void setRequesterDOCTYPE(String pValue) {
            this.mRequesterDOCTYPE = pValue;
        }

        public String getAction() {
            return mAction;
        }

        public void setAction(String pValue) {
            this.mAction = pValue;
        }

        public String getDBName() {
            return mDBName;
        }

        public void setDBName(String pValue) {
            this.mDBName = pValue;
        }

        public String getTableName() {
            return mTableName;
        }

        public void setTableName(String pValue) {
            this.mTableName = pValue;
        }

        public String getDate1() {
            return mDate1;
        }

        public void setDate1(String pValue) {
            this.mDate1 = pValue;
        }

        public String getSOPNumber() {
            return mSOPNumber;
        }

        public void setSOPNumber(String pValue) {
            this.mSOPNumber = pValue;
        }

        public String getSOPType() {
            return mSOPType;
        }

        public void setSOPType(String pValue) {
            this.mSOPType = pValue;
        }

        public SOTransVector getSOTrans() {
            return mSOTrans;
        }

        public void setSOTrans(SOTransVector pValue) {
            this.mSOTrans = pValue;
        }

        public EConnectData() {

            mSOTrans = new SOTransVector();
        }


    }

    public class LineVector extends ArrayList {}

    public class Line {
        public String mItemNumber;
        public String mItemDesc;
        public String mUOFM;
        public String mUnitPrice;
        public String mXTNDPrice;
        public String mTaxAmount;
        public String mQuantity;
        public String mReqShipDate;
        public String mShipToName;

        public String getItemNumber() {
            return mItemNumber;
        }

        public void setItemNumber(String pValue) {
            this.mItemNumber = pValue;
        }

        public String getUOFM() {
            return mUOFM;
        }

        public void setUOFM(String pValue) {
            this.mUOFM = pValue;
        }

        public String getItemDesc() {
            return mItemDesc;
        }

        public void setItemDesc(String pValue) {
            this.mItemDesc = pValue;
        }

        public String getmUnitPrice() {
            return mUnitPrice;
        }

        public void setUnitPrice(String pValue) {
            this.mUnitPrice = pValue;
        }

        public String getXTNDPrice() {
            return mXTNDPrice;
        }

        public void setXTNDPrice(String pValue) {
            this.mXTNDPrice = pValue;
        }

        public String getTaxAmount() {
            return mTaxAmount;
        }

        public void setTaxAmount(String pValue) {
            this.mTaxAmount = pValue;
        }

        public String getQuantity() {
            return mQuantity;
        }

        public void setQuantity(String pValue) {
            this.mQuantity = pValue;
        }

        public String getReqShipDate() {
            return mReqShipDate;
        }

        public void setReqShipDate(String pValue) {
            this.mReqShipDate = pValue;
        }

        public String getShipToName() {
            return mShipToName;
        }

        public void setShipToName(String pValue) {
            this.mShipToName = pValue;
        }
    }

    public class XMLErrorHandler implements ErrorHandler {

        public void error(SAXParseException exception) throws SAXException {
        }

        public void fatalError(SAXParseException exception) throws SAXException {
        }

        public void warning(SAXParseException exception) throws SAXException {
        }
    }

	
}

/*
 * XMLInboundTranslator.java
 *
 * Created on July 13, 2004, 5:21 PM
 */

package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.XMLUtil;

import org.apache.log4j.Logger;

import org.dom4j.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author bstevens
 */

public class InboundXMLHandler extends InterchangeInboundSuper implements InboundTransaction {
	protected Logger log = Logger.getLogger(this.getClass());
	public static final String DEFAULT_NEGATIVE_REASPONSE_CODE = "500";
	public static final String POSITIVE_REASPONSE_CODE = "200";
	public static final String POSITIVE_REASPONSE_TEXT = "ok";
	public static final String CXML_DTD = "http://xml.cxml.org/schemas/cXML/1.2.008/cXML.dtd";
    
    private InboundXMLSuper parser;
	private String docType;
	private String mStreamName;
	private Document currXMLdocument = null;
	private Node masterNode = null;

	HashMap<String,String> fromDomainIdentities = new HashMap<String,String>();
	HashMap<String,String> toDomainIdentities = new HashMap<String,String>();

	public InboundXMLHandler(String streamName){
		this.mStreamName = streamName;
	}

	public String getTranslationReport() {
		String str = "";
		if (this.isFail())
		{
			str = "Inbound XML translation failed\r\n";
			if (parser != null)
				str += parser.getFormatedErrorMsgs();
		}
		else
		{
			str = "Inbound XML translation is success.\r\n";
			
			str += "Sender          Receiver        Set_Type              Transaction_Status\r\n";
			for (int i = 0; i < getInterchanges().size(); i++) {
				InterchangeRequestData interchangeReqD = (InterchangeRequestData)getInterchanges().get(i);
				for (int j = 0; j < interchangeReqD.getTransactionDataVector().size(); j++) {
					ElectronicTransactionData transactionD = (ElectronicTransactionData)interchangeReqD.getTransactionDataVector().get(j);
					str += Utility.padRight(transactionD.getGroupSender(), ' ', 16)
							+ Utility.padRight(transactionD.getGroupReceiver(), ' ', 16)
							+ Utility.padRight(transactionD.getSetType(), ' ', 22)
							+ status[transactionD.getSetStatus()] + "\r\n";
				}
			}
		}
		return str;
	}


	/**
	 * An empty xml document is one that has 0 or 1 elements in them without any
	 * text or attributes. ex: <?xml version="1.0"?><!DOCTYPE cXML SYSTEM
	 * "cXML.dtd"><cXML></cXML> Theis does not do a real check, this is done
	 * as an empty doc may not have a dtd and thus be unparsable. So this is a
	 * psudo check that is done to avoid an error.
	 *
	 * @param String
	 *            the xml
	 */
	private boolean isEmptyXMLDocument(String xml) {
		try {
			// anything that big is probebly not empty
			if (xml.length() > 100) {
				return false;
			}
			xml = xml.trim();
			int firstEndTag = xml.indexOf(">");
			String firstEle = xml.substring(0, firstEndTag + 1);
			int secondEndTag = xml.indexOf(">", firstEndTag + 1);
			String secondEle = xml.substring(firstEndTag + 1, secondEndTag + 1);
			String theRest = xml.substring(secondEndTag + 1, xml.length());

			firstEle = firstEle.toLowerCase();
			firstEle = firstEle.replaceAll(" ", "");
			secondEle = secondEle.toLowerCase();
			secondEle = secondEle.replaceAll(" ", "");

			// Make sure the first element looks like an xml start doc tag:
			// <?xml version="1.0"?>
			if (!firstEle.startsWith("<?xmlversion=")) {
				log.info("xml begin tag doesn't look right: "
						+ firstEle + " (lower cased and spaces removed)");
				return false;
			}
			// Make sure the second element looks like an xml doc tag:
			// <!DOCTYPE something SYSTEM "url.dtd">
			if (!secondEle.startsWith("<!doctype")) {
				log.info("xml doc tag doesn't look right: "
						+ secondEle + " (lower cased and spaces removed)");
				return false;
			}

			// make sure the last element is an open and a close tag
			theRest = theRest.trim();
			int endClose = theRest.indexOf('>');
			String startTag = theRest.substring(0, endClose + 1);

			int endTagStart = theRest.lastIndexOf('<');
			String endTag = theRest.substring(endTagStart, theRest.length());

			String tagContents = theRest.substring(endClose + 1, endTagStart);

			if (endTag.charAt(1) != '/') {
				log.info("end tag (" + endTag
						+ ") does not start with '</'");
				return false;
			}

			if (tagContents.trim().length() > 0) {
				// no error, there is just content in the xml document
				return false;
			}

			if (!endTag
					.regionMatches(true, 2, startTag, 1, endTag.length() - 2)) {
				log.info("start and end tags do not match");
				return false;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		// return true, maybe the real xml parser can parse it, otherwise it
		// will just
		// error out there.
		return true;
	}

	public void translate(String s) throws Exception {
		try {
			// log the xml document, this should be handled better but the
			// logging
			// for the streamed documents is currently unimplemented, so in
			// order to
			// have it somewhere for debuging, just print it to the default
			// stream.
			log.info("LOGING INBOUND XML DOCUMENT");
			log.info("");
			String toLog = s.replaceAll("EXCEPTIONALDEMAND","");
			log.info(toLog);
			log.info("");
			log.info("END LOGING INBOUND XML DOCUMENT");

			// if document is empty don't attempt to translate it
			if (isEmptyXMLDocument(s)) {
				log.info("Document determined to be empty, not proceeding");
				return;
			}

			// first step is to parse the document into a
			// figure out what kind of xml this is. Currently the only
			// supported type is cXML
			boolean errorFl = false;
			Document XMLdocument = null;
            try{
            	XMLdocument = DocumentHelper.parseText(s);
            }catch(Exception e){
            	errorFl = true;
            }
            if(errorFl){
            	s = XMLUtil.removeDTD(s);
            	XMLdocument = DocumentHelper.parseText(s);
            }
			if (XMLdocument.getDocType() == null) {
				docType = XMLdocument.getRootElement().getName();
			} else {
				docType = XMLdocument.getDocType().getElementName();
			}
			log.info("docType="+docType);
			if ("string".equalsIgnoreCase(docType) || "root".equalsIgnoreCase(docType) || "eConnect".equalsIgnoreCase(docType)) {
				log.info("docType="+docType);
				// soap request...need to find the real data
				List econnectNodes = null;
				if ("string".equalsIgnoreCase(docType)) {
					econnectNodes = XMLdocument.getRootElement().selectNodes("//string/root/eConnect");
				}
				if ("root".equalsIgnoreCase(docType)) {
					econnectNodes = XMLdocument.getRootElement().selectNodes("//root/eConnect");
				}
				if ("eConnect".equalsIgnoreCase(docType)) {
					econnectNodes = new ArrayList();
					econnectNodes.add(XMLdocument.getRootElement());
				}
				if (econnectNodes != null) {
					Iterator it = econnectNodes.iterator();
					while (it.hasNext()) {
						Node eConnectNode = (Node) it.next();
						processOtherNode(eConnectNode);
						return;
					}
				}
			}
			log.info("DOC TYPE IS CXML!!!!!!!!!!!!!!!!!!!!!!!!!");
			if(docType.equalsIgnoreCase("cXML")){
				processCXML(XMLdocument);
			}else{
				processOtherNode(XMLdocument);
				//processFailure("Unsupported document type: "+docType);
			}

		} catch (Exception e) {
			//e.printStackTrace();
			setFail(true);
			log.info("translate");
			processFailure(e.getMessage());
			
			//return;
		}
	}



	private void doTranslation(TradingProfileConfigData profileConfigD, Node nodeToTranslate, String pwd)
	throws Exception{
		Class c = Class.forName(profileConfigD.getClassname());
		log.info("Class "+c.getName());
		parser = (InboundXMLSuper) c.newInstance();
		parser.setParameter(this, nodeToTranslate);
		parser.setValid(true);
		//get security info
		if(pwd!=null && pwd.trim().length()>0){
			parser.setPassword(pwd);
		}
		
		log.info("will call parser.extract ..");
		parser.extract();
		if (parser.getValid()) {
			parser.processTransaction();
		}

		if (!parser.getValid()) {
			transactionD.setSetStatus(RECR_MSG);
			processFailure("/nERROR: SetType " + this.getSetType() + " Failed Translation.\r\n" + parser.getFormatedErrorMsgs());
		}
	}



	private void processOtherNode(Node eConnectNode) throws Exception{

		log.info("********************In processOtherNode");
		TradingPartnerDescView tpdv = ((InboundTranslate)getTranslator()).getTradingPartnerDescViewFromName(mStreamName);
		getTranslator().setTradingPartnerDescView(tpdv);
		Class c = Class.forName(tpdv.getTradingProfileConfigData().getClassname());
		parser = (InboundXMLSuper) c.newInstance();
		parser.setParameter(this, eConnectNode);
		parser.setValid(true);
		parser.extract();
		if (parser.getValid()) {
			parser.processTransaction();
		}

		if (!parser.getValid()) {
			transactionD.setSetStatus(RECR_MSG);
		}
	}

	private void processCXML(Document XMLdocument) throws Exception{
		try {
			currXMLdocument = XMLdocument;			
			translateInterchange();	
			
		} catch (Exception e) {
			//e.printStackTrace();
			//processFailure("Caught exception: " + e.getMessage());
			log.info("processCXML");
			throw new Exception(e.getMessage());
		}
	}
	
	public void translateInterchangeHeaderByHandler() throws Exception {

		// we will just select a single node for the moment. Multiple
		// authentication methods
		// can be used here but we would not support it anyways
		// <Credential domain="NetworkID"> //opt 1
		// <Credential domain="DUNS"> //another
		// <Credential domain="AribaNetworkUserId> //and so on
		// both from and to headers may contain this multiple infomration
		// Node credentialFromNode =
		// XMLdocument.selectSingleNode("//cXML/Header/From/Credential");

		
		//To
		Node toNode = currXMLdocument.selectSingleNode("//cXML/Header/To");
		
		if(toNode!=null){
			List toCredentials = toNode.selectNodes("Credential");
			Iterator it = toCredentials.iterator();
			while(it.hasNext()){
				Node cNode = (Node) it.next();
				String domainName = cNode.valueOf("@domain");
				Node toIdentity = cNode.selectSingleNode("Identity");
				if(!toDomainIdentities.containsKey(domainName)){
					toDomainIdentities.put(domainName, toIdentity.getText());
				}
			}
		}
		
		//From
		Node fromNode = currXMLdocument.selectSingleNode("//cXML/Header/From");
		
		if(fromNode!=null){
			List fromCredentials = fromNode.selectNodes("Credential");
			Iterator it = fromCredentials.iterator();
			while(it.hasNext()){
				Node cNode = (Node) it.next();
				String domainName = cNode.valueOf("@domain");
				Node fromIdentity = cNode.selectSingleNode("Identity");
				if(!fromDomainIdentities.containsKey(domainName)){
					fromDomainIdentities.put(domainName,fromIdentity.getText());
				}
			}
		}
        
		
		// delicate

		/*if (!Utility.isSet(receiver)) {
			setFailed("No values for credential domain in cXML request");
		}*/

		masterNode = currXMLdocument.selectSingleNode("//cXML");
		if (masterNode == null) {
			setFailed("No values for credential domain in cXML request");
		}

		log.info("Extracted data from xml:");
		
	}
	public void translateInterchangeContent() throws Exception {
		
		//loop through all of the various requests and process them
		Iterator it = ((Element) masterNode).elementIterator("Request");

		log.info("requestNode::" + masterNode.asXML());
		// Iterator it = kids.iterator();
		while (it.hasNext()) {
			Element aKid = (Element) it.next();
			log.info("bKid.getName():::" + aKid.getName());
			String depMode = aKid.valueOf("@deploymentMode");
			if (depMode.startsWith("pro")) {
				depMode = "P";
			} else if (depMode.startsWith("t")) {
				depMode = "T";
			} else {
				depMode = "P";// according to spec production is default
				// mode
			}
			profile.setTestIndicator(depMode);

			Iterator subIt = aKid.elementIterator();
			while (subIt.hasNext()) {
				try{
					Element aGrandKid = (Element) subIt.next();
					String setType = aGrandKid.getName();
				
					// do some set type translations here
					if ("OrderRequest".equals(setType)) {
						setType = RefCodeNames.EDI_TYPE_CD.T850;
					}else if ("ShipNoticeRequest".equals(setType)) {
						setType = RefCodeNames.EDI_TYPE_CD.T856;
					}

					setSetType(setType);
					transactionD = createTransactionObject();
					transactionD.setSetStatus(RECA_MSG);// initial to receive ok
				
					// initializes the response output stream
					getTranslator().initOutputResponseStream(setType);
					
					//TradingProfileData profile = getTradingProfile(this.profile.getGroupSender(),this.profile.getGroupReceiver(), "IN", setType);					
					TradingProfileData profile = getTradingProfile(toDomainIdentities,fromDomainIdentities,"IN", setType);
					String password = "";
					//get security info
					if(profile!=null){
						if(profile.getSecurityInfoQualifier().equals("01")){
							password = profile.getSecurityInfo();
						}
					}
			
					TradingPartnerDescView tpDescViewD = Translator.getTradingPartnerDescView(profile.getTradingProfileId(),setType, "IN");					
					TradingProfileConfigData profileConfigD = tpDescViewD.getTradingProfileConfigData();
					getTranslator().setTradingPartnerDescView(tpDescViewD);
					if (profileConfigD == null) {
						throw new NullPointerException(
								"Could not find transaction class for trading profile id: "
								+ profile.getTradingProfileId()
								+ " and set type " + setType);
					}
				
					doTranslation(profileConfigD, aGrandKid, password);
				}catch(Exception e){
					log.info("Something wrong happened!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					//processFailure(e.getMessage());
					throw new Exception(e.getMessage());
				}
			}
		}
		transactionD.setException(parser.getFormatedErrorMsgs());
		
	}
	
	private void setFailed(String message) throws Exception{
		if (parser != null)
			parser.getErrorMsgs().add(message);
		setFail(true);
		throw new Exception(message);		
	}
	
	/**
     *Creates a default response document indicating no error has occured.  The default
     *code of 200 is used with a message of "ok".
     */
    public Document createPositiveResponse(){
        return createResponse(POSITIVE_REASPONSE_CODE,POSITIVE_REASPONSE_TEXT);
    }
    
    /**
     *Creates a default response document indicating an error has occured.  The default
     *code of 500 (internal server error) is used, and the message can be anything that
     *describes the error, but should be suitable for external consumption.
     */
    public Document createNegativeResponse(String message){
        return createResponse(DEFAULT_NEGATIVE_REASPONSE_CODE, message);
    }
    
    
    /**
     *Creates a default response document with the supplied code and value.
     *codes in the 500 range indicate an error, and the value is expected to be
     *the error message ("Server Unavaliable", etc).  A code of 200 is a positive
     *response and the expected value should be "ok".
     */
    private Document createResponse(String code, String value){
        Element ele = createEmptyCXMLDocument();
        ele.addElement("Response")
        .addElement("Status")
        .addAttribute("code", code)
        .addAttribute("text", value);
        
        return ele.getDocument();
    }
    
    /**
     *Returns the element after the header has been written, thus calling:<br>
     *<code>createEmptyCXMLDocument.getDocument.asXML()</code><br>
     *will return:<br>
     *
     *<?xml version="1.0"?>
     *<!DOCTYPE cXML SYSTEM "http://xml.cxml.org/schemas/cXML/1.1.007/cXML.dtd">
     *<cXML xml:lang="en" payloadID="7213656@Supplier.com" timestamp="2002-01-01T08:46:00-07:00">
     */
    public static Element createEmptyCXMLDocument(){
        Document cxmlResponse = DocumentFactory.getInstance().createDocument();
        cxmlResponse.setDocType(DocumentFactory.getInstance().createDocType("cXML", null, CXML_DTD));
        
        //make the timestamp
        Date now = new Date();
        String timestamp = Utility.formatISODate(now);
        
        //create a payload id
        int rand = (int) Math.random() * 10000000;
        String payloadId = Long.toString(now.getTime()) + Integer.toString(rand);
        /*String url = System.getProperty("com.cleanwise.baseurl");
        if(!Utility.isSet(url)){
            url = "stjohn";
        }
        payloadId = payloadId+"@"+url;*/
        
        return cxmlResponse.addElement("cXML")
        .addAttribute("timestamp", timestamp)
        .addAttribute("payloadID",payloadId);
    }
    
    protected void processFailure(String mess)throws IOException{
    	log.error(mess);
		Document negResp = createNegativeResponse(mess);
        writeCXMLToResponse(translator.getOutputResponseStream(null),negResp);
        log.info("*****error****************");
        log.info(negResp.asXML());
        log.info("**************************");
        setFail(true);
        return;
    }
    
    
    protected static void writeCXMLToResponse(OutputStream response,Document cxmlResponse)
    throws IOException{
        String xml = cxmlResponse.asXML();
        response.write(xml.getBytes());
        response.flush();
        response.close();
    }
    
    /**
     *parses out an ldap item data object out of the cXML document
     */
    protected LdapItemData parseLdapItem(Node nodeToOperateOn) throws Exception{
        //credential node is part of the header, only one per doc
        Node credentialNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Header/Sender/Credential");
        if(credentialNode == null){
        	setFailed("Could not parse cXML (Credential)");
        }
        Node userNameNode = credentialNode.selectSingleNode("Identity");
        Node passNameNode = credentialNode.selectSingleNode("SharedSecret");
        
        if(userNameNode == null){
        	setFailed("Could not parse cXML (Identity)");
        }
        if(passNameNode == null){
        	setFailed("Could not parse cXML (SharedSecret)");
        }
        
        String username = userNameNode.getText();
        String pass = passNameNode.getText();
        
        
        LdapItemData ldap = new LdapItemData();
        ldap.setUserName(username);
        ldap.setPassword(pass);
        return ldap;
    }

}

/*
 * cXMLSuper.java
 *
 * Created on July 14, 2004, 6:03 PM
 */

package com.cleanwise.service.apps.dataexchange;
import org.apache.log4j.Logger;

import org.dom4j.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.LdapItemData;
import java.util.Date;
import java.io.IOException;
import java.io.OutputStream;
/**
 *
 * @author  bstevens
 */
public class cXML {
	protected Logger log = Logger.getLogger(this.getClass());
    protected boolean failed = false;
    protected InboundTranslate handler;
    protected StringBuffer translationReport=new StringBuffer();
    protected static final String DEFAULT_NEGATIVE_REASPONSE_CODE = "500";
    protected static final String POSITIVE_REASPONSE_CODE = "200";
    protected static final String POSITIVE_REASPONSE_TEXT = "ok";
    
    private static final String CXML_DTD = "http://xml.cxml.org/schemas/cXML/1.2.008/cXML.dtd";
    
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
    protected Element createEmptyCXMLDocument(){
        Document cxmlResponse = DocumentFactory.getInstance().createDocument();
        cxmlResponse.setDocType(DocumentFactory.getInstance().createDocType("cXML", null, CXML_DTD));
        
        //make the timestamp
        Date now = new Date();
        String timestamp = Utility.formatISODate(now);
        
        //create a payload id
        int rand = (int) Math.random() * 10000000;
        String payloadId = Long.toString(now.getTime()) + Integer.toString(rand);
        String url = System.getProperty("com.cleanwise.baseurl");
        if(!Utility.isSet(url)){
            url = "stjohn";
        }
        payloadId = payloadId+"@"+url;
        
        return cxmlResponse.addElement("cXML")
        .addAttribute("timestamp", timestamp)
        .addAttribute("payloadID",payloadId);
    }
    
    protected void processFailure(String mess)throws IOException{
        log.error(">>>>>>>>>>>>>>>>>>>>>>PROCESSING FALIURE FOR cXML DOC<<<<<<<<<<<<<<<<<");
        log.error(mess);
        log.error(">>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        Document negResp = createNegativeResponse(mess);
        translationReport.append(mess);
        writeCXMLToResponse(handler.getOutputResponseStream(null),negResp);
        failed = true;
        return;
    }
    
    
    protected void writeCXMLToResponse(OutputStream response,Document cxmlResponse)
    throws IOException{
        String xml = cxmlResponse.asXML();
        response.write(xml.getBytes());
        response.flush();
        response.close();
    }
    
    /**
     *parses out an ldap item data object out of the cXML document
     */
    protected LdapItemData parseLdapItem(Node nodeToOperateOn) throws IOException{
        //credential node is part of the header, only one per doc
        Node credentialNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Header/Sender/Credential");
        if(credentialNode == null){
            processFailure("Could not parse cXML (Credential)");
            return null;
        }
        Node userNameNode = credentialNode.selectSingleNode("Identity");
        Node passNameNode = credentialNode.selectSingleNode("SharedSecret");
        
        if(userNameNode == null){
            processFailure("Could not parse cXML (Identity)");
            return null;
        }
        if(passNameNode == null){
            processFailure("Could not parse cXML (SharedSecret)");
            return null;
        }
        
        String username = userNameNode.getText();
        String pass = passNameNode.getText();
        
        
        LdapItemData ldap = new LdapItemData();
        ldap.setUserName(username);
        ldap.setPassword(pass);
        return ldap;
    }
    public boolean isFail() {
        return failed;
    }
}

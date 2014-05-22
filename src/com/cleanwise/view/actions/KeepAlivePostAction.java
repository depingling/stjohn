/*
 * ActionBase.java
 *
 * Created on February 27, 2004, 11:47 AM
 */

package com.cleanwise.view.actions;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;

import com.cleanwise.service.api.util.Utility;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cleanwise.view.logic.CustAcctMgtReportLogic;

/**
 * Overidding classes do not need to worry about checking the status of the user (wheather or not they
 * are logged in) and any uncaught exception should be left for this class to deal with.
 * @author  bstevens
 */
public class KeepAlivePostAction extends ActionSuper{   
	private static final Logger log = Logger.getLogger(KeepAlivePostAction.class);
    /**
     *Overidding classes should not worry about checking wheather the user is logged in or not and 
     *should not catch any un-caught exception (Logic class problem).  They should however set the 
     *failForward property if it should be anything other than <i>failure</i>.
     */
	public ActionForward performSub(ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response)
	throws IOException, ServletException {
    	String action = request.getParameter("action");
    	if (action.equals("updateLoadingMessage") ) {

        	Element rootEl = updateLoadingMessage(request, response); 
            return null;

        } 
    	return null;
    }
    
    public static Element updateLoadingMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	log.info("updateLoadingMessage");
    	String loadingMessage = request.getParameter("loadingMessage");
    	if (!Utility.isSet(loadingMessage))
    		loadingMessage= "Loading";
    	else{
    		int i = loadingMessage.indexOf('.');
    		if (i < 0){
    			loadingMessage += ".";
    		}
    		else {
	    		int count = loadingMessage.substring(i).length();
	    		if (count < 3)
	    			loadingMessage += ".";
	    		else
	    			loadingMessage= "Loading";
	    	}
    	}
    	
    	response.setContentType("application/xml");
        response.setHeader("Cache-Control", "no-cache");
                
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) { }
        
        Document doc = docBuilder.getDOMImplementation().createDocument("", "response", null);
        Element root =  doc.getDocumentElement();
        
        root.setAttribute("loadingMessage", loadingMessage);
        OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
        XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
        serializer.serialize(root);
        return root;
    }
}

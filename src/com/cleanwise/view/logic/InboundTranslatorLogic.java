/*
 * InboundTranslatorLogic.java
 *
 * Created on June 30, 2004, 3:59 PM
 */

package com.cleanwise.view.logic;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import org.dom4j.*;


import java.io.*;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.apps.HttpUtil;
import com.cleanwise.service.apps.dataexchange.*;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
/**
 * The entry point for http requests into the inbound translation/trading partner sub system.
 * @author  bstevens
 */
public class InboundTranslatorLogic {
    
    /**
     *Translates a post request using the inbound translation sub system.
     */
    public static void preformTranslation(HttpServletRequest request, HttpServletResponse response)throws IOException{

    	try{
            
            InboundTranslate translator = new InboundTranslate();
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            
            translator.setOutputStream(out);
            
            java.net.URI requestedUri = new java.net.URI(HttpUtils.getRequestURL(request).toString());
            
            /*String scheme = requestedUri.getScheme();
            
            if(Utility.isSet((String)request.getSession().getAttribute(Constants.ENTRY_SCHEME))){
                scheme = (String)request.getSession().getAttribute(Constants.ENTRY_SCHEME);
            }
            
            java.net.URI updatedRequestUri = new java.net.URI(scheme,requestedUri.getSchemeSpecificPart(),requestedUri.getFragment());*/
            
            /*
            String str = null;
            java.util.Enumeration paramNames = request.getParameterNames();
            String params = "";
            while(paramNames.hasMoreElements()){
            	String pn = (String) paramNames.nextElement();
            	String val = request.getParameter(pn);
            	if(pn.equalsIgnoreCase(Constants.POST_DATA)){
            		str = val;
            	}
            }
	
            InputStream is;
			if(str!=null){
				is = new ByteArrayInputStream(str.getBytes());
			}else{
				is = request.getInputStream();
			}
            */
            translator.translateByInputStream(request.getInputStream(), request.getContentType(),requestedUri);
            
            if(out.size() > 0){
                
                String fileName = translator.getOutputFileName();
                //we assume that the content type sent is the same that we are sending back.
                response.setContentType(request.getContentType());
                response.setContentLength(out.size());
                response.setHeader("content-disposition","inline; filename="+fileName);
                out.writeTo(response.getOutputStream());
            }
            response.flushBuffer();
            response.getOutputStream().close();
        }catch(Exception e){
            //sort of the equivalent of throwing up a server 500 page
            e.printStackTrace();
            //throw new RuntimeException(e.getMessage());
            
            //trying somethign
            Document negResp = createNegativeResponse(e.getMessage());
            String xml = negResp.asXML();
            response.getOutputStream().write(xml.getBytes());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            
            //Document negResp = createNegativeResponse(e.getMessage());
            //writeCXMLToResponse(response,negResp);
        }
        
    }
    
    private static Document createNegativeResponse(String mess){
    	 Element ele = InboundXMLHandler.createEmptyCXMLDocument();
         ele.addElement("Response")
         .addElement("Status")
         .addAttribute("code", InboundXMLHandler.DEFAULT_NEGATIVE_REASPONSE_CODE)
         .addAttribute("text", mess);
         
         return ele.getDocument();
    }
}

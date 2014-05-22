package com.cleanwise.view.actions;


import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ForwardingActionForward;

import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.util.IOUtilities;

import org.apache.log4j.*;

/**
 */


public class EdiInboundAction extends ActionSuper {
   private static final Logger log = Logger.getLogger(EdiInboundAction.class);

    public ActionForward performSub(ActionMapping mapping,
				 ActionForm    form,
				 HttpServletRequest request,
				 HttpServletResponse response) {

	String storageDir = "../xsuite/edi/inbound";
	File sDir = new File(storageDir);
	request.setAttribute("inbound.pending.vector", sDir.listFiles());

	String action = request.getParameter("action");
	if (action == null || action.equals("init") ) {
	    return mapping.findForward("display");
	}

	if (form instanceof EdiInboundForm) {

	    EdiInboundForm theForm = (EdiInboundForm) form;

	    String text = theForm.getTheText();
	    FormFile file = theForm.getTheFile();

	    String fileName= file.getFileName();
	    String contentType = file.getContentType();
	    String size = (file.getFileSize() + " bytes");
        String data = "No file was loaded.";
        if ( fileName == null || fileName.length() == 0 ) {
    		return null;
	    }
            try{
                OutputStream bos = new FileOutputStream
                    (storageDir + "/I." + System.currentTimeMillis() + "." + fileName);
                IOUtilities.loadWebFileToOutputStream(file,bos);
            }catch (FileNotFoundException fnfe) {
                log.info("EdiInboundAction: File " + fileName + "no found.");
                data = fnfe.getMessage();
		return null;
	    }
	    catch (IOException ioe) {
                log.info("EdiInboundAction: File read write error.");
                data = ioe.getMessage();
		return null;
	    }
            
            data = "The file has been loaded.";
            
	    request.setAttribute("text", text);
	    request.setAttribute("fileName", fileName);
	    request.setAttribute("contentType", contentType);
	    request.setAttribute("size", size);
	    request.setAttribute("data", data);

	    file.destroy();

	    File sDir2 = new File(storageDir);
	    request.setAttribute("inbound.pending.vector", 
				 sDir2.listFiles());

	    //return a forward to display.jsp
	    return mapping.findForward("display");
	}

	//this shouldn't happen in this example
	return null;
    }


}



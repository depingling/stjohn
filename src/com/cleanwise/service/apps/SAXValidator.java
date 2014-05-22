package com.cleanwise.service.apps;

import org.xml.sax.*;
import org.apache.log4j.Logger;


  public class SAXValidator implements ErrorHandler {
	   private static final Logger log = Logger.getLogger(SAXValidator.class);
	   
	   // Flag to check whether any errors have been spotted.
	   private boolean valid = true;
	   
	   public boolean isValid() {
	     return valid; 
	   }
	   
	   // If this handler is used to parse more than one document, 
	   // its initial state needs to be reset between parses.
	   public void reset() {
	     // Assume document is valid until proven otherwise
	     valid = true; 
	   }
	   
	   public void warning(SAXParseException exception) {
	     
	     log.info("Warning: " + exception.getMessage());
	     log.info(" at line " + exception.getLineNumber()
	      + ", column " + exception.getColumnNumber());
	     // Well-formedness is a prerequisite for validity
	     valid = false;
	     
	   }
	   
	   public void error(SAXParseException exception) {
	      
	     log.info("Error: " + exception.getMessage());
	     log.info(" at line " + exception.getLineNumber()
	      + ", column " + exception.getColumnNumber());
	     // Unfortunately there's no good way to distinguish between
	     // validity errors and other kinds of non-fatal errors 
	     valid = false;
	     
	   }
	   
	   public void fatalError(SAXParseException exception) {
	      
	     log.info("Fatal Error: " + exception.getMessage());
	     log.info(" at line " + exception.getLineNumber()
	      + ", column " + exception.getColumnNumber()); 
	      
	   }
   }

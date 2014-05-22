package com.cleanwise.service.api.eventsys;

import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collection;
import java.rmi.*;
import java.io.*;

import org.apache.log4j.Logger;

public class ParsingException extends Exception {

	protected Logger log = Logger.getLogger(this.getClass());
	
		  public ParsingException() {
		  }

		  public ParsingException(String msg) {
		    log.info(msg);		    
		  }


}

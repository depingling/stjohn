package com.cleanwise.service.apps.dataexchange;

import com.protus.www.WebServices.Messaging._2005._2.*; 

import org.w3c.dom.*;
import javax.xml.parsers.*; //JAXP
import org.xml.sax.*; 
import java.io.*;

import com.cleanwise.service.apps.SAXValidator;

import javax.xml.transform.*; //JAXP
import javax.xml.transform.dom.DOMSource; //JAXP
import javax.xml.transform.stream.StreamResult; //JAXP

import com.sun.org.apache.xml.internal.serialize.*; // for testing ONLY

import com.sun.org.apache.xerces.internal.dom.*; // for testing ONLY

import org.w3c.dom.bootstrap.DOMImplementationRegistry; // for testing ONLY
import org.w3c.dom.ls.DOMImplementationLS; // for testing ONLY
import org.w3c.dom.ls.LSSerializer; //for testing ONLY
import org.w3c.dom.ls.LSOutput; // for testing ONLY

import java.util.regex.*;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.pipeline.OrderErpUpdater;
import com.cleanwise.service.api.pipeline.SaveWebOrderChangeRequest;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.APIAccess;
import java.sql.Connection;
import java.util.ArrayList;

import com.cleanwise.service.api.session.OrderHome;
import com.cleanwise.service.api.session.Order;

import org.apache.log4j.Logger;


public class FaxCreatorMyFax {
	
   private static final Logger log = Logger.getLogger(OutboundPurchaseOrderFaxJob.class);
	
   public String pPathToXmlFile;	
   public static int nodeCount = 0;
 
   // Constructor: creates new instance of FaxCreatorMyFax() class
   public FaxCreatorMyFax() {
	 
   }
 
   // Constructor: creates new instance of FaxCreatorMyFax() class
   public FaxCreatorMyFax(String pathToXmlFile) {
	 pPathToXmlFile = pathToXmlFile;
   }
   
   /************************************************************************************************************************/
   /*** method createAndSendSingelFax() reads the basic XML file, inserts in this XML file "fax_recipient_number" value, ***/
   /*** "fax_recipient_name" value, and "document" value (in PDF format) and sends the FAX                               ***/
   /************************************************************************************************************************/
   public int createAndSendSingelFax (String pPathToXmlFile
		                               , String faxRecipientNumber
		                               , String faxRecipientName
		                               , String faxDocument
		                               , String faxPo
		                               , ArrayList pOrderIdList
		                               , Order pOrdRef
		                               , String coverNotes) { 
 
	 log.info("BEGIN method FaxCreatorMyFax.createAndSendSingelFax()");  
	 log.info("pPathToXmlFile = " + pPathToXmlFile);
	 log.info("faxRecipientNumber = " + faxRecipientNumber);
	 log.info("faxRecipientName = " + faxRecipientName);
	 //log.info("faxDocument = " + faxDocument);
	 log.info("faxPo = " + faxPo);
	 log.info("pOrderIdList = " + pOrderIdList);
	 log.info("coverNotes = " + coverNotes);
	 
	 // if faxRecipientNumber consists of more than 1 FAX#, create the array of FAX#s
     // split String faxRecipientNumber into elements (they are divided by semicolon) and put them in the faxRecipientNumberArr
     String faxRecipientNumberArr[] = faxRecipientNumber.split(";");
     log.info("faxRecipientNumberArr = " + faxRecipientNumberArr);
     int fax_not_sent = 0;
     
   for (int jj = 0; jj < faxRecipientNumberArr.length; jj++){ // for all FAX#s
	 // if faxRecipientNumber has the following formats: (508)555-1111, 508-555-1111, 1-508-555-1111, 
	 // (508)-555-1111, 508.555.1111, 1.508.555.1111
	 // convert it to the format 15085551111
	 
	 // 1: replace ALL "-" characters by an empty character ("") in the faxRecipientNumber
	 // The pattern matches "-" character
	 String patternStr;
	 Pattern pattern;
	 Matcher matcher;
	 
	 patternStr = "-";
     String replacementStr = "";
     // Compile regular expression
     pattern = Pattern.compile(patternStr);
     // Replace all occurrences of pattern in faxRecipientNumberArr[jj] and put the result in faxRecipientNumber1
     matcher = pattern.matcher(faxRecipientNumberArr[jj].trim());
     String faxRecipientNumber1 = matcher.replaceAll(replacementStr);
     
     log.info("faxRecipientNumber1 = " + faxRecipientNumber1);
     
     // 2: replace ALL "(" characters by an empty character ("") in the faxRecipientNumber1
     patternStr = "\\(";
     // Compile regular expression
     pattern = Pattern.compile(patternStr);
     // Replace all occurrences of pattern in faxRecipientNumber1 and put the result in faxRecipientNumber2
     matcher = pattern.matcher(faxRecipientNumber1);
    
     String faxRecipientNumber2 = matcher.replaceAll(replacementStr);
    
     
     log.info("faxRecipientNumber2 = " + faxRecipientNumber2);
     
     // 3: replace ALL ")" characters by an empty character ("") in the faxRecipientNumber2
     patternStr = "\\)";
     // Compile regular expression
     pattern = Pattern.compile(patternStr);
     // Replace all occurrences of pattern in faxRecipientNumber2 and put the result in faxRecipientNumber3
     matcher = pattern.matcher(faxRecipientNumber2);
     String faxRecipientNumber3 = matcher.replaceAll(replacementStr);     
     log.info("faxRecipientNumber3 = " + faxRecipientNumber3);
     
     //4: delete all white spaces in the FAX number
     patternStr = " ";
     // Compile regular expression
     pattern = Pattern.compile(patternStr);
     // Replace all occurrences of pattern in faxRecipientNumber3 and put the result in faxRecipientNumber4
     matcher = pattern.matcher(faxRecipientNumber3);
     String faxRecipientNumber4 = matcher.replaceAll(replacementStr);     
     log.info("faxRecipientNumber4 = " + faxRecipientNumber4);
     
     //5: replace ALL "." characters by an empty character ("") in the faxRecipientNumber4
     patternStr = "\\.";
     // Compile regular expression
     pattern = Pattern.compile(patternStr);
     // Replace all occurrences of pattern in faxRecipientNumber4 and put the result in faxRecipientNumber5
     matcher = pattern.matcher(faxRecipientNumber4);
     String faxRecipientNumber5 = matcher.replaceAll(replacementStr);     
     log.info("faxRecipientNumber5 = " + faxRecipientNumber5);
     
     
     // if faxRecipientNumber5 has 10 characters, add "1" in the beginning of it 
     // and put the result in faxRecipientNumberFinal
     int length = faxRecipientNumber5.length();
     String faxRecipientNumberFinal = faxRecipientNumber5;
     if (length == 10) {
    	 faxRecipientNumberFinal = "1" + faxRecipientNumberFinal;
     }

     log.info("faxRecipientNumberFinal = " + faxRecipientNumberFinal);

	 	 	 
     Document document=null;
   
     // Use JAXP to find a parser: get Document Builder Factory
     try {
       DocumentBuilderFactory factory = 
       DocumentBuilderFactory.newInstance();

       // Turn off validation, and turn on namespaces
   
       factory.setValidating(false);
       factory.setNamespaceAware(true);
       DocumentBuilder parser = null;
       try {
         parser = factory.newDocumentBuilder();
       } catch (ParserConfigurationException e) {
    	 log.info("Serious configuration error occurred. " + 
         "Exiting without faxing."); 
    	 String errorMessage = "Serious configuration error occurred. Exiting without faxing.";
         saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
         fax_not_sent += 1;
 	     e.printStackTrace(); 
 	     return fax_not_sent;
       }
   
       //Using SAXValidator class
       SAXValidator handler = new SAXValidator();
       parser.setErrorHandler(handler);

       // Read the entire XML document into memory
       document = parser.parse(pPathToXmlFile); 
   
     
       if (handler.isValid()){
    	   log.info(pPathToXmlFile + " is valid");
       }
       else {
         // If the document isn't well-formed, an exception has
         // already been thrown and this has been skipped.
    	 log.info(pPathToXmlFile + " is NOT well-formed.");
       }
     
     
     }  
     catch (SAXParseException e) {
    	 log.info(pPathToXmlFile + " is not well-formed at ");
    	 log.info("Line " + e.getLineNumber() 
	       + ", column " +  e.getColumnNumber() );
 	     e.printStackTrace(); 
    	 String errorMessage = pPathToXmlFile + " is not well-formed at Line " + e.getLineNumber() + ", column " +  e.getColumnNumber();
         try {
    	    saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
         } catch (IOException e1) {
        	 log.info("saveFaxNotes() method produced Exception.");
     	     e1.printStackTrace();  
         }
         fax_not_sent += 1;
 	     return fax_not_sent;
     }
     catch (SAXException e) {
    	 log.info("Could not check document because " 
	       + e.getMessage()); 
    	 e.printStackTrace(); 
    	 String errorMessage = "Could not check document because " + e.getMessage();
    	 try {
            saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
    	 } catch (IOException e1) {
        	 log.info("saveFaxNotes() method produced Exception.");
     	     e1.printStackTrace();  
         }
         fax_not_sent += 1; 
 	     return fax_not_sent;
     }
     catch (IOException e) { 
    	 log.info(
	       "Due to an IOException, the parser could not check " 
	       + pPathToXmlFile
	      ); 
 	     e.printStackTrace(); 
    	 String errorMessage = "Due to an IOException, the parser could not check " + pPathToXmlFile;
    	 try {
            saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
    	 } catch (IOException e1) {
        	 log.info("saveFaxNotes() method produced Exception.");
     	     e1.printStackTrace();  
         }
         fax_not_sent += 1;
 	     return fax_not_sent;
     }

     // Find Node with the name "fax_recepient_number"
     String nodeName1 = "fax_recipient_number";
     NodeList faxRecipients 
      = document.getElementsByTagName(nodeName1);
 
     if (faxRecipients.getLength() != 1) {
    	 String errorMessage = "Missing " + nodeName1 + " Node";
    	 try {
            saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
    	 } catch (IOException e1) {
        	 log.info("saveFaxNotes() method produced Exception.");
     	     e1.printStackTrace();  
         }  
         fax_not_sent += 1;
       throw new 
        IllegalArgumentException("Missing " + nodeName1 + " Node"); 
     }

     Element elementName1 = (Element) faxRecipients.item(0);
   
     try {
        document = setFaxXmlTextNodeValue(document, elementName1, faxRecipientNumberFinal);
     } catch (IOException e) {
    	 log.info("Unexpected IOException while proccessing " + elementName1.toString() +
			  "Exiting without faxing."); 
 	     e.printStackTrace(); 
    	 String errorMessage = "Unexpected IOException while proccessing " + elementName1.toString() + ". Exiting without faxing.";
         try {
    	    saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
    	 } catch (IOException e1) {
        	 log.info("saveFaxNotes() method produced Exception.");
     	     e1.printStackTrace();  
         }
         fax_not_sent += 1;
 	     return fax_not_sent; 
     }
     log.info("faxRecipientNumber value changed");   
   
     // Find Node with the Name "fax_recepient_name"
     String nodeName2 = "fax_recipient_name";
     NodeList faxRecipientNames 
      = document.getElementsByTagName(nodeName2);
     if (faxRecipientNames.getLength() != 1) {
    	 String errorMessage = "Missing " + nodeName2 + " Node";
    	 try {
            saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
    	 } catch (IOException e1) {
        	 log.info("saveFaxNotes() method produced Exception.");
     	     e1.printStackTrace();  
         }
         fax_not_sent += 1; 
         throw new 
          IllegalArgumentException("Missing " + nodeName2 + " Node"); 
     }

     Element elementName2 = (Element) faxRecipientNames.item(0);
     try {
       document = setFaxXmlTextNodeValue(document, elementName2, faxRecipientName);
     } catch (IOException e) {
    	 log.info("Unexpected IOException while proccessing " + elementName2.toString() +
			  "Exiting without faxing."); 
 	     e.printStackTrace();
    	 String errorMessage = "Unexpected IOException while proccessing " + elementName2.toString() + ". Exiting without faxing.";
         try {
    	    saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
    	 } catch (IOException e1) {
        	 log.info("saveFaxNotes() method produced Exception.");
     	     e1.printStackTrace();  
         }
         fax_not_sent += 1;
	     return fax_not_sent; 
     }
     log.info("faxRecipientName value changed");

   
     String nodeName3 = "document";
     NodeList faxDocuments 
     = document.getElementsByTagName(nodeName3);
     int faxDocArrayLength = faxDocuments.getLength();
     log.info("faxDocArrayLength = " + faxDocArrayLength);
     if (faxDocuments.getLength() == 0) {
    	 String errorMessage = "Missing " + nodeName3 + " Node";
    	 try {
            saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 );
    	 } catch (IOException e1) {
        	 log.info("saveFaxNotes() method produced Exception.");
     	     e1.printStackTrace();  
         }
         fax_not_sent += 1; 
       throw new 
        IllegalArgumentException("Missing " + nodeName3 + " Node"); 
     }

     Element elementName3 = (Element) faxDocuments.item(1);

     try {
       document = setFaxXmlTextNodeValue(document, elementName3, faxDocument);
     } catch (IOException e) {
    	 log.info("Unexpected IOException while proccessing " + elementName3.toString() +
				  ". Exiting without faxing.");
		  e.printStackTrace();
    	 String errorMessage = "Unexpected IOException while proccessing " + elementName3.toString() + ". Exiting without faxing.";
         try {
    	    saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
    	 } catch (IOException e1) {
        	 log.info("saveFaxNotes() method produced Exception.");
     	     e1.printStackTrace();  
         }
         fax_not_sent += 1;
		 return fax_not_sent; 
     }  
     log.info("faxDocument value changed");
   
     
     // Find Node with the name "cover_page_subject"
     
     /***
     String nodeName4 = "cover_page_subject";
     NodeList faxPos 
     = document.getElementsByTagName(nodeName4);
     if (faxRecipientNames.getLength() != 1) {
       throw new 
        IllegalArgumentException("Missing " + nodeName4 + " Node"); 
     }

     Element elementName4 = (Element) faxPos.item(0);

     try {
       //log.info("faxPo = " + faxPo);
       document = setFaxXmlTextNodeValue(document, elementName4, faxPo);
     } catch (IOException e) {
    	 log.info("Unexpected IOException. " +
				  "Exiting without faxing."); 
		  e.printStackTrace();
     } 
     ***/

     //faxPo = "123456789101112131415161718192021222324252627282930313233353536373839404142434445464748495051525354555657585960616263646566676869707172737475767778798081828384858687888990919293949596979899100";
     Element elementName4 = (Element) faxDocuments.item(0);

     try {
       document = setFaxXmlTextNodeValue(document, elementName4, coverNotes);
     } catch (IOException e) {
	   log.info("Unexpected IOException while proccessing " + elementName4.toString() +
			  ". Exiting without faxing.");
	   e.printStackTrace();
	   String errorMessage = "Unexpected IOException while proccessing " + elementName4.toString() + ". Exiting without faxing.";
       try {
	      saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
	   } catch (IOException e1) {
   	      log.info("saveFaxNotes() method produced Exception.");
	      e1.printStackTrace();  
       }
       fax_not_sent += 1;
	   return fax_not_sent; 
     }  
     log.info("coverNotes value changed");
   
   
     // Convert changed XML to a file
     // Serialize the document and print it
   
     // Write to the System.out changed XML document 
   
   
     TransformerFactory xformFactory 
      = TransformerFactory.newInstance();

     StringWriter sw = new StringWriter();

     Transformer idTransform = null;
     try {
       idTransform = xformFactory.newTransformer();
     } catch (TransformerConfigurationException e) {
    	  log.info("Unexpected TransformerConfigurationException. " +
				  "Exiting without faxing."); 
		  e.printStackTrace();
	      String errorMessage = "Unexpected TransformerConfigurationException. Exiting without faxing.";
          try {
	         saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
	      } catch (IOException e1) {
   	         log.info("saveFaxNotes() method produced Exception.");
	         e1.printStackTrace();  
          }
          fax_not_sent += 1;
	      return fax_not_sent; 
     }
     Source input = new DOMSource(document);
     StreamResult outputXmlDocument = new StreamResult(sw); //StreamResult is set up to write to a StringWriter
     try {
       idTransform.transform(input, outputXmlDocument); //XML from "document" -> to StringBuffer "sw"
     } catch (TransformerException e) {
    	 log.info("Unexpected TransformerException occurred during the XML transformation process. " +
		  "Exiting without faxing."); 
		  e.printStackTrace();
	      String errorMessage = "Unexpected TransformerException occurred during the XML transformation process. Exiting without faxing.";
          try {
	         saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
	      } catch (IOException e1) {
   	         log.info("saveFaxNotes() method produced Exception.");
	         e1.printStackTrace();  
          }
          fax_not_sent += 1;
	      return fax_not_sent; 
     }
		  

     //log.info("Finally outputXmlDocument = " + sw.toString());
   

     /*** Alternative code to transform (serialize) XML to String: was NOT tested !!!
   
     // Writing XML to file
     Result output = new StreamResult(file); //for testing purposes ONLY: writing to a file on the disk   
     idTransform.transform(input, output);
   
     idTransform.transform(input, outputXmlDocument);
     xmlDocumentToFax.append(outputXmlDocument.toString());
   
     // Try Xerces Serialization
     OutputFormat format = new OutputFormat(document);
     format.setLineWidth(65);
     format.setIndenting(true);
     format.setIndent(2);
     XMLSerializer serializer = new XMLSerializer(System.out, format);
     serializer.serialize(document);

     // Try DOM Level 3 classes and methods (Serialization)
     DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();

     DOMImplementationLS impl = 
         (DOMImplementationLS)registry.getDOMImplementation("LS");

     LSSerializer writer = impl.createLSSerializer();
     LSOutput output1 = impl.createLSOutput();
     output1.setByteStream(System.out);
     writer.write(document, output1);
   
     ***/
   
   
     // Send FAX to a specific destination; XML file is put in the String "sw"
   
     try {
       Messaging_x0020_Web_x0020_ServiceSoap webMessagingWebServices = new Messaging_x0020_Web_x0020_ServiceLocator ().getMessaging_x0020_Web_x0020_ServiceSoap ();
       SendResponse oSendSingleFaxResponse = webMessagingWebServices.sendSingleFax (sw.toString ());

       if (oSendSingleFaxResponse.getHeader ().isErrorFlag () ||
         !oSendSingleFaxResponse.getHeader ().getReturnMessage ().startsWith ("M2005"))
       { // error
         log.error("SendSingleFaxResponse: " + oSendSingleFaxResponse.getHeader ().getReturnMessage ());
         // put the error message into the CLW_ORDER_PROPERTY table;
         // also put the status code "sent_to_distributor_failed" into the "clw_order_item_table" (field
         // "order_item_status_code")
         saveFaxNotes( pOrderIdList, faxPo, oSendSingleFaxResponse, pOrdRef, 1 );
         fax_not_sent += 1;
       }
       else // success
       {
    	   log.info ("Fax Request was successfully sent to MyFax, for " + oSendSingleFaxResponse.getNumberOfDestinations () + " destinations. The TransactionID is: " + oSendSingleFaxResponse.getTransactionID () + ".");
           // FAX is sent successfully by MyFax
    	   // save info. about this FAX in the CLW_ORDER_PROPERTY Database table as "Notes"
           saveFaxNotes( pOrderIdList, faxPo, oSendSingleFaxResponse, pOrdRef, 0 );
       } 
       
     } catch(Exception e) {
    	 log.error("Error occurred while making an attempt to send a fax. Exiting without faxing.");
	     e.printStackTrace();
    	 String errorMessage = "Error occurred while making an attempt to send a fax. Exiting without faxing.";
         try {
    	    saveFaxNotes( pOrderIdList, faxPo, errorMessage, pOrdRef, 1 ); 
	     } catch (IOException e1) {
	   	         log.info("saveFaxNotes() method produced Exception.");
		         e1.printStackTrace();  
	     }
	     fax_not_sent += 1;
     } // try     
   } // for (int jj = 0; jj < faxRecipientNumberArr.size(); jj++){   
   
   return fax_not_sent;
   
   } // method createAndSendSingleFax()
 

   /***
   public static Document setFaxXmlTextNodeValue(Document doc, String tag, String sub_tag, String textValue) throws IOException {
	        Element child = null;
			Document tempDocument = doc;

			NodeList matches = doc.getElementsByTagName(tag); 
			for (int cd = 0; cd < matches.getLength(); cd++) {
			    Element tag1 = (Element)matches.item(cd);
			  	NodeList children = tag1.getChildNodes();
			  	for(int i=0; i< children.getLength(); i++){
			  	    if((children.item(i)).getNodeName().equals(sub_tag)) {
			  	    	child = (Element)children.item(i);
			  			if(child != null) {
			  				child.getFirstChild().setNodeValue(textValue);
			  			}
			  		}
			  	}
		    }

	   return(doc);
   }
   ***/
   
   public static Document setFaxXmlTextNodeValue(Document doc, Node tag, String textValue) throws IOException {
	   
    // tag can be "fax_recipient_number" or "fax_recipient_name"
	/*** get matching tag = Node name ***/  
	    if (tag.hasChildNodes()) {
	        Node firstChild = tag.getFirstChild(); // get the Text Node, which should be replaced by the textValue 
	        firstChild.setNodeValue(textValue);	        
	  	}
	    else {
	    	log.info("Something is wrong with the tag " + tag.toString());
	    }

        return(doc);
    
   }
   
   public static void writeNode(Node node) throws IOException {
     
     if (node == null) {
       throw new NullPointerException("Node must be non-null.");
     }
     if (node.getNodeType() == Node.DOCUMENT_NODE 
      || node.getNodeType() == Node.DOCUMENT_FRAGMENT_NODE) { 
       // starting a new document, reset the node count
       nodeCount = 1; 
     }
     
     String name      = node.getNodeName(); // never null
     //String type      = NodeTyper.getTypeName(node); // never null
     //String localName = node.getLocalName();
     //String uri       = node.getNamespaceURI();
     //String prefix    = node.getPrefix();
     String value     = node.getNodeValue();
     
     StringBuffer result = new StringBuffer();
     result.append("Node " + nodeCount + ":\r\n");
     //result.append("  Type: " + type + "\r\n");
     result.append("  Name: " + name + "\r\n");
     if (value != null) {
       result.append("  Value: " + value + "\r\n");
     }
     
     log.info("Everything is still OK 1");
     
     log.info(result.toString());
     log.info("\r\n");
     //out.flush();
     
     log.info("Everything is still OK 2");
     
     nodeCount++;
     
   } // writeNode() method

   
   
   public static void saveFaxNotes(ArrayList pOrderIds, String pFaxPo, SendResponse oSendSingleFaxResponse, Order pOrdRef, int sent_fax_flag) throws IOException {
	   
	   // write data into the CLW_ORDER_PROPERTY table
	   
	   
       // split String pFaxPo into elements (they are divided by comma) and put them in the faxPoArr
       String pFaxPoArr[] = pFaxPo.split(",");
       //log.info("pFaxPoArr = " + pFaxPoArr);

       OrderPropertyData pData;
       OrderItemData oiData;
       //OrderItemDataVector orderItemDV;
       
	   for (int j = 0; j < pOrderIds.size(); j++){ // for all order IDs
	    	   
		    OrderPropertyData orderProperty = OrderPropertyData.createValue();
		    
		    //setting the values of a single record to be saved in the DB table
	        log.info("order_id for the SQL stmt = " + pOrderIds.get(j));
	        int intOrderId = ((Number)pOrderIds.get(j)).intValue(); // cast Object to Int

	        orderProperty.setOrderId( intOrderId ); // set order_id value
	        orderProperty.setShortDesc("MyFax Id"); // set short_desc value
	        String clwValue;
	        if (sent_fax_flag == 0) { //FAX was sent successfully
	           clwValue = "Po Number: " + pFaxPoArr[j] + " MyFax Id: " + oSendSingleFaxResponse.getTransactionID (); // construct clw_value: put myFax id in it
	        } else { // sent_fax_flag = 1 (FAX failed) => error message
	           clwValue = "FAX WAS NOT SENT! Po Number: " + pFaxPoArr[j] + ": " + oSendSingleFaxResponse.getHeader ().getReturnMessage (); // construct clw_value: put error message in it
	        }
	        orderProperty.setValue(clwValue); // set clw_value value
	        orderProperty.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE); // set order_property_type_cd value
	        orderProperty.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE); //set order_property_status_cd value
	        try { 
	           pData = pOrdRef.addNote(orderProperty);
	        } catch (DataNotFoundException e) {
	           log.error("Note for the Purchase Order = " + pFaxPoArr[j] + " was not inserted in the clw_order_property table.");   
	           e.printStackTrace();
               return;
	        }
	        
	        log.info("pData = " + pData);
	        
	        if (sent_fax_flag == 1) { //FAX failed => update appropriate record in the clw_order_item DB table
                   // one Distributor per Order or multiple Distributors per Order
	        		        	
	               // find the record to update in the clw_order_item table	           
                   // construct the Database statement, based on OrderId, ErpPoNum to select from the clw_order_item table
	        	   // ErpPoNum = pFaxPoArr[j]
	        	
	        	   OrderItemDataVector orderItemDV = pOrdRef.getOrderItemCollection(intOrderId, pFaxPoArr[j]);
	        	   log.info("orderItemDV = " + orderItemDV);
	        	   if ( !orderItemDV.isEmpty() ) {
	        		  OrderItemData orderItem = (OrderItemData) orderItemDV.get(0);
		        	  log.info("orderItem1 = " + orderItem); 	      
	        		  // set ORDER_ITEM_STATUS_CD field to "SENT_TO_DISTRIBUTOR_FAILED",
	        		  // then update clw_order_item DB table	        		       	        		       		      	      	        	     	        	
		              orderItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED);		        		        
		        	  log.info("orderItem2 = " + orderItem); 	
		              pOrdRef.updateOrderItemStatusCd(orderItem);
	        	   }
	        	
	        }	        
	        
	   } // for int j		   
	   
   } // method saveFaxNotes()
   
   public static void saveFaxNotes(ArrayList pOrderIds, String pFaxPo, String errMessage, Order pOrdRef, int sent_fax_flag) throws IOException {
	   
	   // write data into the CLW_ORDER_PROPERTY table
	   
	   
       // split String pFaxPo into elements (they are divided by comma) and put them in the faxPoArr
       String pFaxPoArr[] = pFaxPo.split(",");
       //log.info("pFaxPoArr = " + pFaxPoArr);

       OrderPropertyData pData;
       OrderItemData oiData;
       //OrderItemDataVector orderItemDV;
       
	   for (int j = 0; j < pOrderIds.size(); j++){ // for all order IDs
	    	   
		    OrderPropertyData orderProperty = OrderPropertyData.createValue();
		    
		    //setting the values of a single record to be saved in the DB table
	        log.info("order_id for the SQL stmt = " + pOrderIds.get(j));
	        int intOrderId = ((Number)pOrderIds.get(j)).intValue(); // cast Object to Int

	        orderProperty.setOrderId( intOrderId ); // set order_id value
	        orderProperty.setShortDesc("MyFax Id"); // set short_desc value
	        String clwValue;

	        // parameter sent_fax_flag = 1 (FAX failed) => error message
	        clwValue = "FAX WAS NOT SENT! Po Number: " + pFaxPoArr[j] + ": " + errMessage; // construct clw_value: put error message in it
	        orderProperty.setValue(clwValue); // set clw_value value
	        orderProperty.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE); // set order_property_type_cd value
	        orderProperty.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE); //set order_property_status_cd value
	        try { 
	           pData = pOrdRef.addNote(orderProperty);
	        } catch (DataNotFoundException e) {
	           log.error("Note for the Purchase Order = " + pFaxPoArr[j] + " was not inserted in the clw_order_property table.");   
	           e.printStackTrace();
               return;
	        }
	        
	        log.info("pData = " + pData);
	        
	        if (sent_fax_flag == 1) { //FAX failed => update appropriate record in the clw_order_item DB table
                   // one Distributor per Order or multiple Distributors per Order
	        		        	
	               // find the record to update in the clw_order_item table	           
                   // construct the Database statement, based on OrderId, ErpPoNum to select from the clw_order_item table
	        	   // ErpPoNum = pFaxPoArr[j]
	        	
	        	   OrderItemDataVector orderItemDV = pOrdRef.getOrderItemCollection(intOrderId, pFaxPoArr[j]);
	        	   log.info("orderItemDV = " + orderItemDV);
	        	   if ( !orderItemDV.isEmpty() ) {
	        		  OrderItemData orderItem = (OrderItemData) orderItemDV.get(0);
		        	  log.info("orderItem1 = " + orderItem); 	      
	        		  // set ORDER_ITEM_STATUS_CD field to "SENT_TO_DISTRIBUTOR_FAILED",
	        		  // then update clw_order_item DB table	        		       	        		       		      	      	        	     	        	
		              orderItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED);		        		        
		        	  log.info("orderItem2 = " + orderItem); 	
		              pOrdRef.updateOrderItemStatusCd(orderItem);
	        	   }
	        	
	        }	        
	        
	   } // for int j		   
	   
   } // method saveFaxNotes()
   
} //class FaxCreatorMyFax

   

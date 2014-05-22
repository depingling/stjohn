package com.cleanwise.service.apps;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceLocator;
import com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoap;
import com.protus.www.WebServices.Messaging._2005._2.SendResponse;

public class TestMyFaxFinal
{
  private static final Logger log = Logger.getLogger(TestMyFaxFinal.class);	
 //private Writer out;
 public static int nodeCount = 0;
 //Document document = null;
 
 public static void main (String [] args)
 { 
  try { 
   if (args.length != 1)
   {
    System.err.println ("Usage: TestMyFaxFianl [Path to XML File]");
    System.exit (1);
   }
 
   String sourcedoc = args[0];
   Document document = null;
   
   // Use JAXP to find a parser: get Document Builder Factory
   try {
     DocumentBuilderFactory factory = 
       DocumentBuilderFactory.newInstance();

     // Turn on validation, and turn on namespaces
   
     factory.setValidating(false);
     factory.setNamespaceAware(true);
     DocumentBuilder parser = factory.newDocumentBuilder();
   
     //Using SAXValidator class
     SAXValidator handler = new SAXValidator();
     parser.setErrorHandler(handler);

     // Read the entire document into memory
     //Document document = parser.parse(args[0]); 
     document = parser.parse(sourcedoc); 
   
     
     if (handler.isValid()){
	     log.info(sourcedoc + " is valid");
     }
     else {
       // If the document isn't well-formed, an exception has
       // already been thrown and this has been skipped.
       log.info(sourcedoc + " is NOT well-formed.");
     }
     
     
   }  
   catch (SAXParseException e) {
	      log.info(sourcedoc + " is not well-formed at ");
	      log.info("Line " + e.getLineNumber()
	       + ", column " +  e.getColumnNumber() );
   }
   catch (SAXException e) {
	      log.info("Could not check document because "
	       + e.getMessage());
   }
   catch (IOException e) { 
	      log.info(
	       "Due to an IOException, the parser could not check " 
	       + sourcedoc
	      ); 
   }

   // Find Node with the name "fax_recepient_number"
   String nodeName1 = "fax_recipient_number";
   NodeList faxRecipients 
   = document.getElementsByTagName(nodeName1);
 
   if (faxRecipients.getLength() != 1) {
     throw new 
      IllegalArgumentException("Missing " + nodeName1 + " Node"); 
   }

   Element elementName1 = (Element) faxRecipients.item(0);
   
   String faxRecipientNumber = "15084817861";
   //document = setFaxXmlTextNodeValue(document, elementName, nodeName1, faxRecipientNumber);
   document = setFaxXmlTextNodeValue(document, elementName1, faxRecipientNumber);
   log.info("faxRecipientNumber value changed");
   
   // Find Node with the Name "fax_recepient_name"
   String nodeName2 = "fax_recipient_name";
   NodeList faxRecipientNames 
   = document.getElementsByTagName(nodeName2);
   if (faxRecipientNames.getLength() != 1) {
     throw new 
      IllegalArgumentException("Missing " + nodeName2 + " Node"); 
   }

   Element elementName2 = (Element) faxRecipientNames.item(0);
   
   //String nodeName2 = "fax_recipient_name";
   String faxRecipientName = "SVC";
   //document = setFaxXmlTextNodeValue(document, elementName, nodeName2, faxRecipientName);
   document = setFaxXmlTextNodeValue(document, elementName2, faxRecipientName);
   log.info("faxRecipientName value changed");

   
   String nodeName3 = "document";
   NodeList faxDocuments 
   = document.getElementsByTagName(nodeName3);
   if (faxRecipientNames.getLength() != 1) {
     throw new 
      IllegalArgumentException("Missing " + nodeName3 + " Node"); 
   }

   Element elementName3 = (Element) faxDocuments.item(0);

   String faxDocument = "PPP";
   //document = setFaxXmlTextNodeValue(document, elementName, nodeName1, faxRecipientNumber);
   document = setFaxXmlTextNodeValue(document, elementName3, faxDocument);
   log.info("faxDocument value changed");
   
   //writeNode(faxRecipientName);
   
   
   // Convert changed XML to a file
   // Serialize the document and print it
   
   // Write to the System.out changed xml document 
   
   
   TransformerFactory xformFactory 
    = TransformerFactory.newInstance();

   StringWriter sw = new StringWriter();

   //File file = new File("/apps/xadminStaging/webapp/stjohn/tools/jobs/SendSingleFaxPOTest1.xml");

   Transformer idTransform = xformFactory.newTransformer();
   Source input = new DOMSource(document);
   //Result output = new StreamResult(System.out); // Write to the System.out changed xml document( for testing purposes ONLY): worked correct
   StreamResult outputXmlDocument = new StreamResult(sw); //StreamResult is set up to write to a StringWriter
   idTransform.transform(input, outputXmlDocument); //XML from "document" -> to StringBuffer "sw"

   log.info("Finally outputXmlDocument = " + sw.toString());
   

   /*** Alternative code to transform (serialize) XML to String: was NOT tested !!!
   
   // Writing XML to file
   Result output = new StreamResult(file); //for testing purposes ONLY: writing to a file on the disk   
   idTransform.transform(input, output);
   
   idTransform.transform(input, outputXmlDocument);
   xmlDocumentToFax.append(outputXmlDocument.toString());
   
   log.info(xmlDocumentToFax.toString());

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
   
   
   // Send Fax to a specific destination; XML file is in the String "sw"
   
   /***
   java.lang.StringBuffer xmlDocument = new java.lang.StringBuffer ();
   java.io.BufferedReader xmlBuffer = new java.io.BufferedReader (new java.io.FileReader (args[0]));

   String line = null;
   while (( line = xmlBuffer.readLine ()) != null){
    xmlDocument.append (line);
   }
   xmlBuffer.close ();
   ***/
   
   Messaging_x0020_Web_x0020_ServiceSoap webMessagingWebServices = new Messaging_x0020_Web_x0020_ServiceLocator ().getMessaging_x0020_Web_x0020_ServiceSoap ();
   SendResponse oSendSingleFaxResponse = webMessagingWebServices.sendSingleFax (sw.toString ());
   //SendResponse oSendSingleFaxResponse = webMessagingWebServices.sendSingleFax (xmlDocumentToFax.toString());

   if (oSendSingleFaxResponse.getHeader ().isErrorFlag () ||
       !oSendSingleFaxResponse.getHeader ().getReturnMessage ().startsWith ("M2005"))
   {
    System.err.println("SendSingleFaxResponse: " + oSendSingleFaxResponse.getHeader ().getReturnMessage ());
   }
   else
   {
    log.info ("Fax Request was successfully sent to MyFax, for " + oSendSingleFaxResponse.getNumberOfDestinations () + " destinations. The TransactionID is: " + oSendSingleFaxResponse.getTransactionID () + ".");
   } 
   
  }
  
  catch (Exception e)
  {
    System.err.println(e.toString ());
  }
  
 } //main 
 
   //public int nodeCount = 0;

 /***
   public static Document setFaxXmlTextNodeValue(Document doc, String tag, String sub_tag, String textValue) throws IOException {
	        log.info("tag = " + tag);
	        log.info("sub_tag = " + sub_tag);
			Element child = null;
			Document tempDocument = doc;

			NodeList matches = doc.getElementsByTagName(tag); 
			for (int cd = 0; cd < matches.getLength(); cd++) {
			    Element tag1 = (Element)matches.item(cd);
			  	NodeList children = tag1.getChildNodes();
			  	for(int i=0; i< children.getLength(); i++){
			  	    if((children.item(i)).getNodeName().equals(sub_tag)) {
			  	    	log.info((children.item(i)).getNodeName());
			  			child = (Element)children.item(i);
			  			if(child != null) {
			  				child.getFirstChild().setNodeValue(textValue);
			  				log.info(getText(child));
			  			}
			  		}
			  	}
		    }

	   log.info("destNodeValue = ");
	   return(doc);
   }
   ***/
   
   public static Document setFaxXmlTextNodeValue(Document doc, Node tag, String textValue) throws IOException {
   	log.info("textValue = " + textValue);
    // tag can be "fax_recipient_number" or "fax_recipient_name"
	//Element child = null;
	//Document tempDocument = doc;
	/*** get matching tag = Node name ***/  
	//for (int cd = 0; cd < matches.getLength(); cd++) {
	    //Element tag1 = (Element)matches.item(cd);
	    if (tag.hasChildNodes()) {
	        Node firstChild = tag.getFirstChild(); // get the Text Node, which should be replaced by the textValue 
	        firstChild.setNodeValue(textValue);
	  	    log.info(firstChild.getNodeType());
	  	    log.info(firstChild.getNodeName());
	  	    log.info(firstChild.getNodeValue());
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
     /***
     if (localName != null) {
       result.append("  Local Name: " + localName + "\r\n");
     }
     if (prefix != null) {
       result.append("  Prefix: " + prefix + "\r\n");
     }
     if (uri != null) {
       result.append("  Namespace URI: " + uri + "\r\n");
     }
     ***/
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

} //class TestMyFaxFinal

   

package com.cleanwise.service.apps;

import org.apache.log4j.Logger;

import com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceLocator;
import com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoap;
import com.protus.www.WebServices.Messaging._2005._2.SendResponse;
   
public class TestMyFax
{
	private static final Logger log = Logger.getLogger(TestMyFax.class);
 public static void main (String [] args)
 {
  try
  {
   if (args.length != 1)
   {
    //System.err.println ("Usage: MyFaxSendSingleFax 'Path to XML File'");
    System.err.println ("Usage: TestMyFax 'Path to XML File'");
   }
   else
   {
    java.lang.StringBuffer xmlDocument = new java.lang.StringBuffer ();
    java.io.BufferedReader xmlBuffer = new java.io.BufferedReader (new java.io.FileReader (args[0]));

    String line = null;
    while (( line = xmlBuffer.readLine ()) != null){
     xmlDocument.append (line);
    }
    xmlBuffer.close ();

    Messaging_x0020_Web_x0020_ServiceSoap webMessagingWebServices = new Messaging_x0020_Web_x0020_ServiceLocator ().getMessaging_x0020_Web_x0020_ServiceSoap ();
    SendResponse oSendSingleFaxResponse = webMessagingWebServices.sendSingleFax (xmlDocument.toString ());

    if (oSendSingleFaxResponse.getHeader ().isErrorFlag () ||
        !oSendSingleFaxResponse.getHeader ().getReturnMessage ().startsWith ("M2005"))
    {
     System.err.println("SendSingleFaxResponse: " + oSendSingleFaxResponse.getHeader ().getReturnMessage ());
    }
    else
    {
     log.info("Fax Request was successfully sent to MyFax, for " + oSendSingleFaxResponse.getNumberOfDestinations () + " destinations. The TransactionID is: " + oSendSingleFaxResponse.getTransactionID () + ".");
    }
   }
  }
  catch (Exception e)
  {
   System.err.println(e.toString ());
  }
 }
}

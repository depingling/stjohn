package com.cleanwise.service.api.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import com.johnsondiversey.msds.msdswebservice.jdimsds.JohnsonDiverseyX0020MSDSX0020WebX0020Services;

/**
 *
 * @author scher
 */
public class DiverseyMSDSRetrieve {

	private static final Logger log = Logger.getLogger(DiverseyMSDSRetrieve.class);
	
	public DiverseyMSDSRetrieve() {}
	
	
    public static String retrieveMsdsUrl(String manufSku, String countryCode, String languageCode) 
	              throws ServiceException, SOAPException, MalformedURLException, IOException, Exception {
      log.info("MSDS_test: inside getMsdsUrl() method");
      log.info("MSDS_test: manufSku = " + manufSku);
      log.info("MSDS_test: countryCode = " + countryCode);
      log.info("MSDS_test: languageCode = " + languageCode);
      
      //construct a DateTime stamp for the file name
      Date today = new Date();
      //DateFormat df = DateFormat.getInstance();
      SimpleDateFormat formatter = new SimpleDateFormat("yyy.MM.dd 'at' hh:mm:ss:SSS a zzz");
      String dtStamp = formatter.format(today);
      
 	  // 1: replace ALL "." characters by an empty character ("") in the dtStamp variable
 	  // The pattern matches "." character
 	  String patternStr;
 	  Pattern pattern;
 	  Matcher matcher;
 	 
 	  patternStr = "\\.";
      String replacementStr = "";
      // Compile regular expression
      pattern = Pattern.compile(patternStr);
      // Replace all occurrences of pattern in dtStamp and put the result in dtStamp1
      matcher = pattern.matcher(dtStamp);
      String dtStamp1 = matcher.replaceAll(replacementStr);
      
      log.info("dtStamp1 = " + dtStamp1);
      
      // 2: replace ALL ":" characters by an empty character ("") in the dtStamp1
      patternStr = ":";
      // Compile regular expression
      pattern = Pattern.compile(patternStr);
      // Replace all occurrences of pattern in dtStamp1 and put the result in dtStamp2
      matcher = pattern.matcher(dtStamp1);
     
      String dtStamp2 = matcher.replaceAll(replacementStr);
     
      
      log.info("dtStamp2 = " + dtStamp2);
      
      // 2: replace ALL whitespace by an empty character ("") in the dtStamp2
      patternStr = " ";
      // Compile regular expression
      pattern = Pattern.compile(patternStr);
      // Replace all occurrences of pattern in dtStamp1 and put the result in dtStamp2
      matcher = pattern.matcher(dtStamp2);
     
      String dtStamp3 = matcher.replaceAll(replacementStr);
     
      
      log.info("dtStamp3 = " + dtStamp3);
      
      
	  JohnsonDiverseyX0020MSDSX0020WebX0020Services port = new JohnsonDiverseyX0020MSDSX0020WebX0020Services();
	  String msdsUrlStr = port.getJohnsonDiverseyX0020MSDSX0020WebX0020ServicesSoap().getMSDS(manufSku, countryCode, languageCode); 
	  log.info("MSDS_test: msdsUrlStr = " + msdsUrlStr);
	  String fileName = "";
	  String basepath = "";
	  try{
		URL msdsUrl = new URL(msdsUrlStr);
		InputStream in = msdsUrl.openConnection().getInputStream();
		
		///////////////////////////////// //////////////////////////
		//create file name with .pdf extension and save it on the server
	

		String fileExt = ".pdf";
	    fileName = "";
	    File file = null;
		basepath =
		      "/en/products/msds/jdws_tmp_"
		      + dtStamp3 // <= removed ":" (also ".", whitespace - not absolutely necessary) using regular expressions from the dtStamp to create a valid file name (for MS Windows) 	      
		      + "_"
		      + manufSku
		      + fileExt;

		// this is the absolute path where we will be writing
		fileName = System.getProperty("webdeploy") + basepath;		
	    log.info("MSDS_test: fileName = " + fileName);
		file = new File(fileName);
		///////////////////////////////////////////////////////////
		//FileOutputStream fout = new FileOutputStream("C:/EJBServer/server/defst/en/products/msds/1269641-1.pdf"); <- works correct !!!
		FileOutputStream fout = new FileOutputStream(file);   
		int abyte;	
		while ( ( abyte = in.read() ) != -1 ){
			fout.write(abyte);
		}
		fout.flush();
		log.info("Pdf file created, filled out via Diversey Web Services, and saved on the server.");
	  }catch(Exception e){
		e.printStackTrace();
	  }
	  
	  //return msdsUrlStr; //URL from JonhsonDiversey Web Services
	  return basepath; //relative location of the newly created file on eSpendwise server
	  
    }	  

}
	

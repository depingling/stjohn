/*
 * CurrencyLoader.java
 *
 * Created on July 4, 2006, 3:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cleanwise.service.apps.loaders;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.session.CurrencyHome;
import com.cleanwise.service.api.util.JNDINames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CurrencyData;
import com.cleanwise.service.api.value.CurrencyDataVector;
/**
 *
 * @author Ykupershmidt
 */
public class CurrencyLoader {
  private static final Logger log = Logger.getLogger(CurrencyLoader.class);
  /** Creates a new instance of CurrencyLoader */
  public CurrencyLoader() {
  }
  
  public int uploadCurrencies(String fileName) 
  throws Exception {
    // Check for a properties file command option.
    String propFileName = System.getProperty("conf");
    Properties props = new Properties();
    props.load(new FileInputStream (propFileName) );
	log.info("CurrencyLoader CCCCCCCCC props: "+props);
    InitialContext jndiContext = new InitialContext(props);
    
    Object ref  = jndiContext.lookup(JNDINames.CURRENCY_EJBHOME);
    CurrencyHome currHome = (CurrencyHome)
                    PortableRemoteObject.narrow (ref, CurrencyHome.class);
    com.cleanwise.service.api.session.Currency currBean = currHome.create();
    File currencyFile = new File(fileName);
     if(!currencyFile.exists() || !currencyFile.isFile()) {
       log.info("Error. Can't find file: "+fileName);
       return 2;
     }
     LinkedList currLL = new LinkedList();
     FileInputStream fis = new FileInputStream(currencyFile);
    // BufferedReader rdr = new BufferedReader(new InputStreamReader(fis,"ISO-8859-1"));
     BufferedReader rdr = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
     String line = rdr.readLine();
     while(line!=null) {     
       currLL.add(line);
       line = rdr.readLine();
		log.info("CurrencyLoader CCCCCCCCC line: "+line);
     }
     CurrencyDataVector currencyDV = new CurrencyDataVector();
     CurrencyData currencyD = null;
     for(Iterator iter=currLL.iterator(); iter.hasNext();) {
       String str = (String) iter.next();
       str = str.trim();
       if(str.length()>0) {
         if(!str.startsWith("#")) {
           int ind = str.indexOf("=");
           if(ind>=0) {
             String key = str.substring(0,ind);
             key=key.trim();
             String value = "";
             if(str.length()>ind+1) {
               value = str.substring(ind+1).trim();
             }
             if("locale".equalsIgnoreCase(key)) {
               if(currencyD!=null) {
                 currencyD.setAddBy("CurrencyLoader");
                 currencyD.setModBy("CurrencyLoader");
                 String errorMess = checkCurrency(currencyD);
                 if(Utility.isSet(errorMess)) {
                   log.info(errorMess);
                   return 2;
                 }
                 currencyDV.add(currencyD);
               }
               currencyD = CurrencyData.createValue();
               currencyD.setDecimals(-2);
               currencyD.setLocale(value);
             } else if("name".equalsIgnoreCase(key)) {
               currencyD.setShortDesc(value);
             } else if("symbol".equalsIgnoreCase(key)) {
               currencyD.setLocalCode(value);
             } else if("position".equalsIgnoreCase(key)) {
               currencyD.setCurrencyPositionCd(value);
             } else if("decimals".equalsIgnoreCase(key)) {
               try{
                 if(Utility.isSet(value)) {
                   currencyD.setDecimals(Integer.parseInt(value));
                 }
               } catch (Exception exc) {
                 currencyD.setDecimals(-3);                 
               }
             } else if("abbreviation".equalsIgnoreCase(key)) {
               currencyD.setGlobalCode(value);
             }             
           }
         }
       }       
     }
     if(currencyD!=null) {
       String errorMess = checkCurrency(currencyD);
       if(Utility.isSet(errorMess)) {
         log.info(errorMess);
         return 2;
       }
       currencyDV.add(currencyD);
     }
     for(Iterator iter=currencyDV.iterator(); iter.hasNext();) {
       CurrencyData currD = (CurrencyData) iter.next();
       currBean.saveCurrency(currD);
     }
     return 0;
  }

  private String checkCurrency(CurrencyData currencyD)
  {
    
    String errorMess = null;
    if(!Utility.isSet(currencyD.getShortDesc())) {
      errorMess = addErrorMess(errorMess, "no name property");
    }
    if(!Utility.isSet(currencyD.getLocale())) {
      errorMess = addErrorMess(errorMess, "no locale property");
    }
    if(!Utility.isSet(currencyD.getGlobalCode())) {
      errorMess = addErrorMess(errorMess, "no abbreviation property");
    }
    if(currencyD.getDecimals()==-3) {
      errorMess = addErrorMess(errorMess, "wrong decimals property format");
    }
    if(currencyD.getDecimals()==-2) {
      errorMess = addErrorMess(errorMess, "no decimals property");
    }
    if(Utility.isSet(errorMess)) {
      errorMess += " Currency Object: " + currencyD;
    }
    return errorMess;
  }

  private String addErrorMess(String errorMess, String newError ) {
    if(!Utility.isSet(errorMess)) {
      errorMess = "Currency object has errors: ";
    } else {
      errorMess += ", ";
    }
    errorMess += newError;
    return errorMess;
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws Exception {
    // TODO code application logic here
    if(args.length<1) {
      log.info("Error. Only "+args.length+" parameters found");
      return;
    }
    String fileName = args[0];
    
    CurrencyLoader cl = new CurrencyLoader();
    cl.uploadCurrencies(fileName);
    return;
  }
  
}
  

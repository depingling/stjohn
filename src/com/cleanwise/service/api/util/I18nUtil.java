/*
 * I18Util.java
 *
 * Created on June 30, 2006, 7:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cleanwise.service.api.util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.CurrencyData;
import com.cleanwise.service.api.value.CurrencyDataVector;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.view.utils.Constants;
import com.espendwise.service.api.util.MessageResource;


/**
 *
 * @author Ykupershmidt
 */
public class I18nUtil {
	
	private static final Logger log = Logger.getLogger(I18nUtil.class);

  private static HashMap localeCurrencyHM = null;


  public static HashMap getCurrencyHashMap()
  {
      try {
        if(localeCurrencyHM==null) {
          loadCurrencies();
        }
        return localeCurrencyHM;
      }catch(Exception exc) {
        return null;
      }

  }

  public static CurrencyData getCurrency(String localeCd)
  {
      try {
        if(localeCurrencyHM==null) {
          loadCurrencies();
        }
        //STJ-3115
        String countryCode = Utility.getCountryCodeFromLocale(localeCd);
        //return (CurrencyData)  localeCurrencyHM.get(localeCd);
        return (CurrencyData)  localeCurrencyHM.get(countryCode);
      }catch(Exception exc) {
        return null;
      }

  }
  
  public static CurrencyData getCurrencyByCd(String pCurrencyCd)
  {
      try {
        if(localeCurrencyHM==null) {
          loadCurrencies();
        }
        Set keys = localeCurrencyHM.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext();) {
        	String countryCode = (String) iter.next();
        	CurrencyData currD = (CurrencyData)localeCurrencyHM.get(countryCode);
        	if ( pCurrencyCd.equals(currD.getGlobalCode())) {
        		return currD;
        	}
        }

      }catch(Exception exc) {
        return null;
      }

      return null;

  }


  private static LinkedList mLocales = null;
  public static LinkedList getAllLocales()
  {
      try {

	    if ( mLocales == null) {
		  mLocales = new LinkedList();
		  APIAccess factory = new APIAccess();
		  com.cleanwise.service.api.session.ListService lsvc = factory.getListServiceAPI();
	      RefCdDataVector locales = lsvc.getRefCodesCollection("LOCALE_CD");
	      Iterator it = locales.iterator();
		  while(it.hasNext()){
		     RefCdData l = (RefCdData) it.next();
			 mLocales.add(l.getValue());
		  }
	    }
	    return mLocales;

      } catch (Exception exc) {
        exc.printStackTrace();
        return new LinkedList();
      }
    }

  public static void flushCache() {
    try {
    	mLocales = null;
        loadCurrencies();
      } catch (Exception exc) {
        exc.printStackTrace();
      }
  }

  private static synchronized void  loadCurrencies()
  throws Exception
  {
    APIAccess factory = new APIAccess();
    com.cleanwise.service.api.session.Currency currBean = factory.getCurrencyAPI();
    CurrencyDataVector currencyDV = currBean.getAllCurrencies();
    HashMap currHM = new HashMap();
    for(Iterator iter = currencyDV.iterator(); iter.hasNext();) {
      CurrencyData cD = (CurrencyData) iter.next();
      String locale = cD.getLocale();
      //STJ-3115
      if(locale.length()>2){
    	  String pCountryCode = locale.substring(3,locale.trim().length());
          if(!currHM.containsKey(pCountryCode)){
        	  currHM.put(pCountryCode,cD);
          }
      }
    }
    localeCurrencyHM = currHM;
  }

  public static String getDatePattern(Locale locale) {
     DateFormat dfShort = DateFormat.getDateInstance(DateFormat.SHORT, locale);
     DateFormat dfFull = DateFormat.getDateInstance(DateFormat.FULL, locale);
     GregorianCalendar gc = new GregorianCalendar(3333,10,22);

     String pattern = "";
     pattern = dfShort.format(gc.getTime());
     pattern = pattern.replaceAll("3","yy");
     pattern = pattern.replaceAll("1","M");
     pattern = pattern.replaceAll("2","d");

     return pattern;
  }

  public static String getUtf8Str(String pStr)
  throws UnsupportedEncodingException, IOException
  {
      /*
      Properties prt = System.getProperties();
      java.util.Enumeration enn = prt.propertyNames();
      while(enn.hasMoreElements()) {
         Object el = enn.nextElement();
      }
      */
    String jbossVersion = System.getProperty("jbossVersion","");
    if(!jbossVersion.startsWith("2.")) {
        return pStr;
    }

    if(pStr==null) {
      return null;
    }
//printByteStr(pStr);
    String osName = Utility.strNN(System.getProperty("os.name")).toLowerCase();
    byte[] strB = null;
    if(osName.startsWith("windows")) {
      strB = pStr.getBytes();
    } else {
      strB = getUnicode(pStr);
    }
    ByteArrayInputStream bais = new ByteArrayInputStream(strB);
    InputStreamReader isr = new InputStreamReader(bais,"UTF-8");
    char[] strC = new char[strB.length];
    isr.read(strC);
    String strNew = String.valueOf(strC);
//printByteStr("New: "+strNew);
    return strNew;

  }

  static public void printByteStr(String pStr) {
    if(pStr==null){
      return;
    }
    byte[] strB = pStr.getBytes();
    String printStr = "";
    for(int ii=0; ii<strB.length; ii++) {
    long ll = strB[ii]; //(strB[ii]<<8) | strB[ii+1];
    ll = ll&0xff;
    if(ii>0) printStr += " ";
    printStr += Long.toHexString(ll);
    }
    log.info(printStr);
    return;
  }

  static public byte[] getUnicode(String pStr) {
  //byte[] test = {(byte)0xc3, (byte)0x90, (byte)0xc2, (byte)0xb0, (byte)0xc3, (byte)0x90, (byte)0xc2, (byte)0xb1,
  //(byte)0xc3, (byte)0x90, (byte)0xc2, (byte)0xb2, (byte)0xc3, (byte)0x90, (byte)0xc2, (byte)0xb3, (byte)0xc3, (byte)0x90, (byte)0xc2, (byte)0xb4, (byte)0xc3, (byte)0x90,
  //(byte)0xc2, (byte)0xb5, (byte)0xc3, (byte)0x90, (byte)0xc2, (byte)0xb6};
  byte[] test = pStr.getBytes();
  byte[] result = new byte[test.length];
  int jj = 0;
  for(int ii=0; ii<test.length; ii++) {
    byte bb = test[ii];
    if((bb&0x80)==0) {
      result[jj++] = bb;
      continue;
    } else if((bb&0xF0)==0xF0) {
      break;
    } else if((bb&0xE0)==0xE0) {
      break;
    } else if((bb&0xC0)==0xC0) {
      ii++;
      byte bb1 = test[ii];
      if(ii>=test.length) break;
      int uu = bb<<6 | (bb1&0x3F);
      uu &= 0x7FF;
      if((uu&0x700)!=0) result[jj++] = (byte)((uu>>8)&0x7);
      result[jj++] = (byte)(uu&0xFF);

    }
  }
  byte[] result1 = new byte[jj];
String printStr = " Test String ";
  for(int ii=0; ii<jj; ii++) {
    result1[ii] = result[ii];
    long ll = result1[ii];
    ll = ll&0xff;
    if(ii>0) printStr += " ";
    printStr += Long.toHexString(ll);
  }
  return result1;

  }

  public static String getDecimalStr(String pStr) {
    if (pStr == null || pStr.length() == 0) return pStr;
    pStr = pStr.replace(',', '.');
    return pStr;
  }

  public static String getMessage(Locale pLocale, String pKey) {
      return MessageResource.getMessage(pLocale, pKey);
  }

  public static String getMessage(String pKey) {
      return getMessage(Locale.US, pKey, null);
  }

  public static String getMessage(String pKey, Object[] pArgs) {
      return getMessage(Locale.US, pKey, pArgs);
  }

  public static String getMessage(String pKey, Object pArg) {
      return MessageResource.getMessage(Locale.US, pKey, pArg);
  }

  public static String getMessage(String pKey, Object pArg, Object pArg1) {
      return MessageResource.getMessage(Locale.US, pKey, pArg, pArg1);
  }

  public static String getMessage(String key, Object[] args, boolean pReturnNullIfNotFound, Locale userLocale, String storePrefix) {

      String message;
      if (args == null) {
          args = new Object[0];
      }

      message = I18nUtil.getMessage(userLocale, key, args);
      if (message == null && !userLocale.equals(Constants.DEFAULT_LOCALE)) {
          message = I18nUtil.getMessage(Constants.DEFAULT_LOCALE, key, args);
      }

      if (message == null && pReturnNullIfNotFound) {
          return null;
      }

      if (message == null) {
          message = "???? " + key;
      }
      return message;
  }

  public static String getMessage(String pKey, Object pArg, Object pArg1, Object pArg2, Object pArg3) {
      return MessageResource.getMessage(Locale.US, pKey, pArg, pArg1, pArg2, pArg3);
  }

  public static String getMessage(String pKey, Object pArg, Object pArg1, Object pArg2) {
      return MessageResource.getMessage(Locale.US, pKey, pArg, pArg1, pArg2);
  }

  public static String getMessage(Locale pLocale, String pKey, Object[] pArgs) {
	  //log.info("I18nUtil getMessage loadMessages 1 pKey="+pKey);
	  if(!MessageResource.areMessageResourcesLoaded()) {
		  String messDir = System.getProperty("webdeploy") + "/" + System.getProperty("messageResourceDirectory");  
		 // log.info("I18nUtil getMessage loadMessages messDir="+messDir);
		  MessageResource.loadMessages(messDir);
	  }
	  
	 /* for (int i=0; i<pArgs.length-1; i++) {
		if (pArgs[i] != null) {  
		  log.info("I18nUtil getMessage loadMessages 11 pLocale.getLanguage()="+pLocale.getLanguage()+" pArgs["+String.valueOf(i)+"].toString()="+pArgs[i].toString());
		} else {
			log.info("I18nUtil getMessage loadMessages 11 pLocale.getLanguage()="+pLocale.getLanguage()+" pArgs["+String.valueOf(i)+"].toString()=null");
			//pArgs[i] = ".";
		}  
	  }*/
	  
	  
	  String returnMess = MessageResource.getMessage(pLocale, pKey, pArgs);
	 // log.info("I18nUtil getMessage loadMessages 111 pKey="+pKey+" returnMess="+returnMess);
	  return returnMess;
  }

  
  /**
   * Gets a locale specific message for the store
   */
  public static String getMessage(Locale locale, String key, Object[] args, String[] types) {
		// if the first argument type is MESSAGE_KEY we need to translate the message from the key
		if (args != null && args.length > 0 && types != null && types.length > 0 &&
			types[0] != null && types[0].equals(RefCodeNames.PIPELINE_MESSAGE_ARG_CD.MESSAGE_KEY)) {

			Object[] newArgs = new Object[args.length-1];
			for (int i=0; i<args.length-1; i++) {
				newArgs[i] = args[i+1];
			}
			args[0] = I18nUtil.getMessage(locale, args[0].toString(), newArgs);
			types[0] = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING;

			return getMessage(locale, key, args);
		}
		
		return getMessage(locale, key, args);
	}

}

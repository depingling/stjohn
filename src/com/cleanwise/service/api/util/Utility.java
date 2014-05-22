package com.cleanwise.service.api.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.errors.EncodingException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.CostCenterAssocDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.process.operations.FileGenerator;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.AccountSearchResultView;
import com.cleanwise.service.api.value.AccountSearchResultViewVector;
import com.cleanwise.service.api.value.AccountView;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AllStoreData;
import com.cleanwise.service.api.value.AssetData;
import com.cleanwise.service.api.value.BudgetData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogFiscalPeriodViewVector;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.CostCenterAssocData;
import com.cleanwise.service.api.value.CostCenterAssocDataVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.CountryPropertyData;
import com.cleanwise.service.api.value.CountryPropertyDataVector;
import com.cleanwise.service.api.value.CurrencyData;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventProcessView;
import com.cleanwise.service.api.value.EventPropertyData;
import com.cleanwise.service.api.value.EventPropertyDataVector;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.GenericReportColumnView;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GroupData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.InventoryItemsData;
import com.cleanwise.service.api.value.InventoryLevelData;
import com.cleanwise.service.api.value.InventoryLevelDetailData;
import com.cleanwise.service.api.value.InventoryLevelDetailDataVector;
import com.cleanwise.service.api.value.InvoiceCustData;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.ItemAssocData;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.LoggingData;
import com.cleanwise.service.api.value.LoggingDataVector;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideStructureData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OutboundEDIRequestData;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PreOrderAddressData;
import com.cleanwise.service.api.value.PreOrderPropertyData;
import com.cleanwise.service.api.value.PreOrderPropertyDataVector;
import com.cleanwise.service.api.value.PriceListData;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.ServiceProviderData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteInventoryInfoView;
import com.cleanwise.service.api.value.SiteInventoryInfoViewVector;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;
import com.cleanwise.service.api.value.UiControlData;
import com.cleanwise.service.api.value.UiControlElementData;
import com.cleanwise.service.api.value.UiControlElementDataVector;
import com.cleanwise.service.api.value.UiControlView;
import com.cleanwise.service.api.value.UiControlViewVector;
import com.cleanwise.service.api.value.UiData;
import com.cleanwise.service.api.value.UiPageData;
import com.cleanwise.service.api.value.UiPageView;
import com.cleanwise.service.api.value.UiPageViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.WarrantyData;
import com.cleanwise.service.api.value.WorkOrderData;
import com.cleanwise.service.apps.ApplicationsEmailTool;
import com.cleanwise.service.apps.SOAPTransferClient;
import com.cleanwise.service.crypto.CryptoException;
import com.cleanwise.service.crypto.CryptoPropNames;
import com.cleanwise.service.crypto.CryptoUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.ocean.util.validation.EmailValidation;

/**
 * set of static utility functions
 */
public final class Utility {
  private static final Logger log = Logger.getLogger(Utility.class);

  private static HashMap javaBeanCache = new HashMap();

  public static final Integer TARGET_FACILITY_THRESHOLD=new Integer(10);
  public static final Date MAX_DATE =
        (new GregorianCalendar(3000,GregorianCalendar.JANUARY,1)).getTime();
  public static final Date MIN_DATE =
        (new GregorianCalendar(1900,GregorianCalendar.JANUARY,1)).getTime();
	
  /**
   * Case insensitive comparison of 2 strings, where all non charachters and numbers are
   * ignored in the comparison.
   * @param str1
   * @param str2
   * @param length The length to compare.  If greater than zero, only
   *               specified number of characters will be compared.
   *               Otherwise the entire strings are compared (in
   *               which case a length mismatch -- after white space
   *               stripped -- would cause the comparison to fail).
   * @return true if match, false otherwise
   */
  static public boolean stringMatch(String str1, String str2, int length)
  {
	if (!isSet(str1) && !isSet(str2)) {
	  return true;
	}
    if (!isSet(str1) || !isSet(str2)) {
      return false;
    }
    str1 = trimNonAlphaNumeric(str1);
    str2 = trimNonAlphaNumeric(str2);
    if (length > 0) {
      int minimum = min(str1.length(), str2.length());
      minimum = min(minimum, length);
      str1 = str1.substring(0, minimum);
      str2 = str2.substring(0, minimum);
    }
    if (str1.equalsIgnoreCase(str2))
      return true;
    else
      return false;
  }

  // remove all the non alpha numeric text in the given String.
  static private String trimNonAlphaNumeric(String str)
  {
    StringBuffer outStr = new StringBuffer();
    char[] strChars = str.toCharArray();
    for (int i = 0; i < strChars.length; i++)
    {
      char ch = strChars[i];
      if(Character.isLetter(ch) || Character.isDigit(ch)){
        outStr.append(ch);
      }
    }
    return outStr.toString();
  }

  //check if string contains numbers
  static public boolean isNumeric(String str){

	  char[] strChars = str.toCharArray();
	  for (int i = 0; i < strChars.length; i++)
	  {
		  char ch = strChars[i];
		  if(Character.isDigit(ch)){
	        return true;
		  }
	  }
	  return false;
  }
  
  //check if a string is an integer
  public static boolean isInteger(String str) {
	  boolean returnValue = false;
	  if (Utility.isSet(str)) {
		  try {
			  Integer.parseInt(str);
			  returnValue = true;
		  }
		  catch (NumberFormatException e) {
			  //nothing to do - return value is already false
		  }
	  }
	  return returnValue;
  }

    public static int parsePercentInt(String pVal) throws java.lang.NumberFormatException {
        if (pVal.endsWith("%")) {
            return pVal.startsWith("+") ?
                    Integer.parseInt(pVal.substring(1, pVal.length() - 1)) :
                    Integer.parseInt(pVal.substring(0, pVal.length() - 1));
        } else {
            return pVal.startsWith("+") ?
                    Integer.parseInt(pVal.substring(1)) :
                    Integer.parseInt(pVal);
        }
    }

  //extract out numbers from string
  static public String extractNum(String str){

	  StringBuffer outStr = new StringBuffer();
	  char[] strChars = str.toCharArray();
	  for (int i = 0; i < strChars.length; i++){
		  char ch = strChars[i];
		  if(Character.isDigit(ch)){
			  outStr.append(ch);
		  }else{
			  continue;
		  }
	  }
	  return outStr.toString();
  }

  // comparing with minimum length of str1 and str2
  static public boolean postalCodeMatch(String str1, String str2)
  {
	if (!isSet(str1) && !isSet(str2)) {
	  return true;
	}
    if (!isSet(str1) || !isSet(str2)) {
      return false;
    }
    str1 = trimNonAlphaNumeric(str1);
    str2 = trimNonAlphaNumeric(str2);
    int minimum = min(str1.length(), str2.length());
      str1 = str1.substring(0, minimum);
      str2 = str2.substring(0, minimum);
    if (str1.equalsIgnoreCase(str2))
      return true;
    else
      return false;
  }

  // return smaller number between num1 and num2
  static public int min(int num1, int num2)
  {
    if (num1 <= num2)
      return num1;
    else
      return num2;
  }

  /**
   *Fetches the given property type out of a propertyDataVector
   */
  static public PropertyData getProperty(PropertyDataVector pProperties,String pType){

      Iterator it = pProperties.iterator();

      while(it.hasNext()){
          PropertyData prop = (PropertyData) it.next();
          if(prop.getPropertyTypeCd().equalsIgnoreCase(pType)
          || (prop.getPropertyTypeCd().equals(RefCodeNames.PROPERTY_TYPE_CD.EXTRA) &&
          prop.getShortDesc().equalsIgnoreCase(pType))){
              return prop;
          }
      }
      return null;
  }

  /**
   *Fetches the given properties value out of a propertyDataVector
   */
  static public String getPropertyValue(PropertyDataVector pProperties,String pType){
      PropertyData pd = getProperty(pProperties,pType);
      if(pd == null){
        return null;
      }else{
          return pd.getValue();
      }
  }

  /**
   *Fetches the given property type out of an orderPropertyDataVector
   */
  static public String getOrderPropertyValue(OrderPropertyDataVector pProperties,String pType){

      OrderPropertyData opd = getOrderProperty(pProperties,pType);
      if(opd == null){
          return null;
      }else{
          return opd.getValue();
      }
  }

  /**
   *Fetches the given property type out of an orderPropertyDataVector
   */
  static public OrderPropertyData getOrderProperty(OrderPropertyDataVector pProperties,String pType){

      Iterator it = pProperties.iterator();

      while(it.hasNext()){
          OrderPropertyData prop = (OrderPropertyData) it.next();
          if(prop.getOrderPropertyTypeCd().equalsIgnoreCase(pType)){
              return prop;
          }
      }
      return null;
  }

    /**
   *Fetches the given property type out of an PreOrderPropertyDataVector
   */
  static public PreOrderPropertyData getPreOrderProperty(PreOrderPropertyDataVector pProperties,String pType){

      Iterator it = pProperties.iterator();

      while(it.hasNext()){
          PreOrderPropertyData prop = (PreOrderPropertyData) it.next();
          if(prop.getOrderPropertyTypeCd().equalsIgnoreCase(pType)){
              return prop;
          }
      }
      return null;
  }


  /**
   *Fetches the given property type out of an countryPropertyDataVector
   */
  static public CountryPropertyData getCountryProperty(CountryPropertyDataVector pProperties,String pType){

      Iterator it = pProperties.iterator();

      while(it.hasNext()){
          CountryPropertyData prop = (CountryPropertyData) it.next();
          if(prop.getCountryPropertyCd().equalsIgnoreCase(pType)){
              return prop;
          }
      }
      return null;
  }


  /**
   *Fetches the given property type out of an countryPropertyDataVector
   */
  static public String getCountryPropertyValue(CountryPropertyDataVector pProperties,String pType){

      CountryPropertyData cpd = getCountryProperty(pProperties,pType);
      if(cpd == null){
          return null;
      }else{
          return cpd.getValue();
      }
  }


  /**
   * Check if a string is "set" -- not null and not empty.
   * @returns true if set, false if not
   */
  static public boolean isSet(String string) {
    if (string == null){
      return false;
    }
    else{
      string = string.trim();
      if (!string.equals("")) {
        return true;
      } else {
        return false;
      }
    }
  }
  
  /**
   * Method to determine if a collection is set - not null and not empty
   */
  public static boolean isSet(Collection collection) {
	  return (collection != null && !collection.isEmpty());
  }
  
  /**
   * Method to determine if an array is set - not null and not empty
   */
  public static boolean isSet(String[] stringArray) {
	  return (stringArray != null && stringArray.length != 0);
  }
  
  /**
   * Method to determine if a map is set - not null and not empty
   */
  public static boolean isSet(Map map) {
	  return (map != null && !map.isEmpty());
  }

  /**
   * Check if a string is "set" -- not null and not empty. and not NA or any of the
   * other programmer introduced text strings.
   * @returns true if set, false if not
   */
  static public boolean isSetForDisplay(String string) {
    if(isSet(string)){
        string = string.trim().toUpperCase();
        if(!("NA".equals(string)) && !("N/A".equals(string))){
            return true;
        }
    }
    return false;
  }


  /**
   *Tests if a big decimal is a zero value, that is a bigdecimal which is null or
   *is equal to zero (pVal.compareTo(new java.math.BigDecimal(0)) == 0)
   */
  static public boolean isZeroValue(java.math.BigDecimal pVal) {
    if(!isSet(pVal) || pVal.compareTo(new java.math.BigDecimal(0)) == 0){
        return true;
    }
    return false;
  }
  static public boolean isSet(java.math.BigDecimal pVal) {
    if (pVal == null){
      return false;
    }
    return true;
  }
  static public java.math.BigDecimal checkValue(java.math.BigDecimal pVal) {
      if ( isSet(pVal)) {
      return pVal;
      }
    return new java.math.BigDecimal(0);
  }

  static public String padLeft(int value, char padChar, int desiredLength) {
      return padLeft(Integer.toString(value), padChar, desiredLength);
  }

  static public String padLeft(String value, char padChar, int desiredLength) {
    StringBuffer result = new StringBuffer();
    int length = 0;
    if (value != null) {
      length = value.length();
    }
    if (length < desiredLength) {
      for (int i = 0; i < (desiredLength - length); i++) {
        result.append(padChar);
      }
    }
    if (value != null) {
      result.append(value);
    }
    if (result.length() > 0) {
      return result.toString();
    } else {
      return null;
    }
  }

  static public String padRight(String value, char padChar, int desiredLength) {
    StringBuffer result = new StringBuffer();
    int length = 0;
    if (value != null) {
      length = value.length();
      result.append(value);
    }
    if (length < desiredLength) {
      for (int i = 0; i < (desiredLength - length); i++) {
        result.append(padChar);
      }
    }
    if (result.length() > 0) {
      return result.toString();
    } else {
      return null;
    }
  }

  //trims all of the left most occurences of a given string
  //trimRight("aaabcdefg", "aa") will return abcdefg
  static public String trimLeft(String pVal, String pTrimPattern){
      if(pVal == null || pTrimPattern == null){
        return pVal;
      }
      int trimLen = pTrimPattern.length();
      while(pVal.startsWith(pTrimPattern)){
          pVal = pVal.substring(trimLen);
      }
      return pVal;
  }

  //trims all of the right most occurences of a given string
  //trimRight("abcdefggg", "gg") will return abcdefg
  static public String trimRight(String pVal, String pTrimPattern){
      if(pVal == null || pTrimPattern == null){
        return pVal;
      }
      int trimLen = pTrimPattern.length();
      while(pVal.endsWith(pTrimPattern)){
          pVal = pVal.substring(0,pVal.length() - trimLen);
      }
      return pVal;
  }

  static public boolean matchUOM(String UOM1, String UOM2)
  {
    if((UOM1 == null || UOM2 == null) && (UOM1 != UOM2)){
        return false;
    }
    if (!UOM1.equals(UOM2))
    {
      UOM1 = UOM1.trim();
      UOM2 = UOM2.trim();
      // unit of measure of CS AND CA are same
      if ((UOM1.equalsIgnoreCase("CS") && UOM2.equalsIgnoreCase("CA")) ||
                (UOM1.equalsIgnoreCase("CA") && UOM2.equalsIgnoreCase("CS")))
        return true;
      else
        return false;
    }
    return true;
  }

  /**
   * USE parseLocaleCodeV2 instead.
   */
  static public Locale parseLocaleCode(String pLocaleCode)
  {
        // Split the locale into language and country.
        StringTokenizer st = new StringTokenizer(pLocaleCode, "_");
        String lang = "", country = "";
        for (int i = 0; st.hasMoreTokens() && i < 2; i++ ) {
          if ( i == 0 ) lang = st.nextToken();
          if ( i == 1 ) country = st.nextToken();
        }
        return new Locale(lang,country);
  }

  /**
   * Use instead of parseLocaleCode.  Will take a locale code in and "guess" at the country portion if there is 
   * only one of the two codes provided (IT instead of it).  It uses capitalization to make this guess, if all the
   * Characters are capitol letters than it will be assumed to be a country.  This logic is only invoked if the 
   * underscore is not sent in.  So for example it_it would use "it" as a country as the underscore notation
   * takes priority over the capitalization.
   * @param pLocaleCode the locale code for example en_US
   * @return a populated Locale object based off the String locale code
   */
  static public Locale parseLocaleCodeV2(String pLocaleCode)
  {
	    if(pLocaleCode == null){
		  pLocaleCode = "";
	    }
        // Split the locale into language and country.
        StringTokenizer st = new StringTokenizer(pLocaleCode, "_");
        String lang;// = "", country = "";
        String country;
        String str1 = null, str2 = null;
        for (int i = 0; st.hasMoreTokens() && i < 2; i++ ) {
          if ( i == 0 ) str1 = st.nextToken();
          if ( i == 1 ) str2 = st.nextToken();
        }
        //partial locale code specified; determine if country or language based off case
        if(str2 == null && str1 != null){
        	boolean lowerFound = false;
        	for (char c : str1.toCharArray()) {
        	    if (Character.isLowerCase(c)) {
        	        lowerFound = true;
        	        break;
        	    }
        	}
        	if(!lowerFound){
        		country = str1;
        		lang = "";
        	}else{
        		lang = str1;
            	country = "";
        	}
        }else if(str2 == null && str1 == null){
        	lang = "";
        	country = ""; 
        }else{
        	lang = str1;
        	country = str2;
        }
        return new Locale(lang,country);
  }
  
  // return true if pAddress start with a number or end with a number
  static public boolean isStreetAddress(String pAddress)
  {
    if (!isSet(pAddress)){
      return false;
    }
    if (Character.isDigit(pAddress.charAt(0)) || Character.isDigit(pAddress.charAt(pAddress.length()-1))){
      return true;
    }
    //checks to see if address contains a set of words, and if so returns
    //true
    pAddress = pAddress.toLowerCase();
    //for abbreviations make sure to add space in before so
    //"something st"
    //will match and
    //"strange address"
    //will not
    String[] addressWords = {" dr"," ln"," ave", " st", " street"
                 ," circle","pickup"
                 ," rd", " road" };

    int addrLen = pAddress.length();
    for(int i=0;i<addressWords.length;i++){
    	int ix = pAddress.indexOf(addressWords[i]);
        if(ix>=0){
        	if (addrLen > ix + addressWords[i].length()){
        		char ch = pAddress.charAt(ix + addressWords[i].length());
        		if(Character.isLetter(ch) || Character.isDigit(ch)){
        			continue;
        		}else
        			return true;
        	}
            return true;
        }
    }
    return false;
  }

//return true if pAddress start with a number or end with a number
  static public String getStreetAddress(String pAddress1, String pAddress2, String pAddress3, String pAddress4)
  {
    if (isSet(pAddress1) && Character.isDigit(pAddress1.charAt(0))){
      return pAddress1;
    } else if (isSet(pAddress2) && Character.isDigit(pAddress2.charAt(0))){
      return pAddress2;
    } else if (isSet(pAddress3) && Character.isDigit(pAddress3.charAt(0))){
      return pAddress3;
    } else if (isSet(pAddress4) && Character.isDigit(pAddress4.charAt(0))){
      return pAddress4;
    }
    if (isStreetAddress(pAddress1)){
      return pAddress1;
    } else if (isStreetAddress(pAddress2)){
      return pAddress2;
    } else if (isStreetAddress(pAddress3)){
      return pAddress3;
    } else if (isStreetAddress(pAddress4)){
      return pAddress4;
    } else
    return null;
  }

    public static List<IdVector> createPackages(IdVector ids, int pPackageSize) {
        List<IdVector> packages = new ArrayList<IdVector>();
        if (ids != null && !ids.isEmpty()) {
            if (ids.size() <= pPackageSize) {
                packages.add(ids);
            } else {
                for (int i = 0; i <= (int) (ids.size() / pPackageSize); i++) {
                    IdVector pack = new IdVector();
                    for (int j = 0; j < (i == (int) (ids.size() / pPackageSize) ? (ids.size() % pPackageSize) : pPackageSize); j++) {
                        pack.add(ids.get((i * pPackageSize) + j));
                    }
                    if (!pack.isEmpty()) {
                        packages.add(pack);
                    }
                }
            }
        }
        return packages;
    }

    public static List<List> createPackages(List list, int pPackageSize) {
        List<List> packages = new ArrayList<List>();
        if (list != null && !list.isEmpty()) {
            if (list.size() <= pPackageSize) {
                packages.add(list);
            } else {
                for (int i = 0; i <= (list.size() / pPackageSize); i++) {
                    List<Object> pack = new ArrayList<Object>();
                    for (int j = 0; j < (i == (list.size() / pPackageSize) ? (list.size() % pPackageSize) : pPackageSize); j++) {
                        pack.add(list.get((i * pPackageSize) + j));
                    }
                    if (!pack.isEmpty()) {
                        packages.add(pack);
                    }
                }
            }
        }
        return packages;
    }

  // return a string with only character of digit
  static public String getNumStringByRemoveNoneNumChars(String pStr)
  {
    String temp = "";

    if (!Utility.isSet(pStr)){
      return pStr;
    }

    for (int i = 0; i < pStr.length(); i++){
      if (Character.isDigit(pStr.charAt(i))){
        temp += pStr.charAt(i);
      }
    }
    return temp;
  }
  // return a string with only character of digit
  static public String getPhoneNumberByRemoveSeperator(String pPhoneNum)
  {
    return (getNumStringByRemoveNoneNumChars(pPhoneNum));
  }

  // return a substring of pStr with maximum length of pLength
  static public String subString(String pStr, int pLength)
  {
    String temp = pStr;

    if (temp == null)
      return "";

    if (temp.length() > pLength){
      temp = temp.substring(0, pLength);
    }
    return temp;
  }

  /**
   *@see replaceString(StringBuffer, String, String)
   */
    public static String replaceString(String pSource,String pSubString, String pReplaceString){
        StringBuffer buf = new StringBuffer(pSource);
        replaceString(buf, pSubString, pReplaceString);
        return buf.toString();
    }

     /**
     *Replaces all occurrences of a subString in a given stringBuffer with a specified replace value.
     *@PARAM pBuf - the StringBuffer to operate on
     *@PARAM pSubString - the String to replace
     *@PARAM pReplaceString - the String to substitute for pSubString
     *@RETURN the modified String that was passed in, returns an unmodified version of the
     *        string if no occurrences of pSubString were found
     */
    public static void replaceString(StringBuffer pBuf,
                                     String pSubString, String pReplaceString){
        int lSubStrLen=pSubString.length();
        if ( pSubString.equals(pReplaceString) ||
             pBuf.length() <= 0 )
        {
            return;
        }

        int origLen = pBuf.length();
        for(int i= origLen - 1; i >= 0; i--){
            if ( i+lSubStrLen > origLen ) continue;
            if ( i+lSubStrLen > pBuf.length() ) continue;
            String lSubStr = pBuf.substring(i,i+lSubStrLen);
            if (lSubStr.equals(pSubString)){
                pBuf.delete(i,i+lSubStrLen);
                pBuf.insert(i,pReplaceString);
            }
        }
    }

    /**
     *Returns a string with all of the special XML characters replaced
     *with their escape characters.
     */
    private static String[] xmlSpecailList = {"\"","&","<",">","'"};
    private static String[] xmlReplaceList = {"&quot;","&amp;","&lt;","&gt;",""};
    public static String toXMLString(String pString){
    	if (!isSet(pString))
    		return pString;
        StringBuffer lBuf = new StringBuffer(pString);
        for(int i=0,len=xmlSpecailList.length;i<len;i++){
            replaceString(lBuf,xmlSpecailList[i],xmlReplaceList[i]);
        }
        return lBuf.toString();
    }

  /** Creates new Utility, private so that this class will not be instantiated*/
  private Utility() {
  }

  static public String toAsciiSubString(String pStr, int pLength)
  {
    String temp = pStr;

    if (temp == null)
      return "";

    if (temp.length() > pLength){
      temp = temp.substring(0, pLength);
    }

    // Replace non-ascii characters in the substring with
    // a period (.)
    char mc[] = temp.toCharArray();

    for ( int i = 0; i < mc.length; i++ ) {
        if ( mc[i] < ' ' || mc[i] >  '~' ) {
            mc[i] = '.';
        }
    }

    return new String(mc);
  }

  //Returns a date truncated to the nearest day.  For example:
  //Tue Mar 11 10:53:41 EST 2003
  //returns
  //Tue Mar 11 00:00:00 EST 2003
  public static Date truncateDateByDay(Date date){
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      cal.set(Calendar.MILLISECOND,0);
      cal.set(Calendar.SECOND,0);
      cal.set(Calendar.MINUTE,0);
      cal.set(Calendar.HOUR,0);
      return cal.getTime();
  }


    // Return timespan between the 2 dates, excluding Saturday and Sunday
   static public long calculateBusinessDays( Date pEndDate, Date pStartDate ) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(pStartDate);
        long busDays = 0;
        long kMilliSecsInaDay = 86400000;
        while (  (pEndDate.getTime() - cal.getTime().getTime()) > kMilliSecsInaDay ) {
            if ( cal.get(Calendar.DAY_OF_WEEK) > Calendar.SUNDAY &&
                cal.get(Calendar.DAY_OF_WEEK) < Calendar.SATURDAY) {
                    // This one day span occurred on a business day.
                    busDays++;
                }
                cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        return busDays;
    }

   /**
    * Returns the first "token" out of the buffer.  Anything that is not
    * alpha numeric is considered a separator.
    */
    static public String getFirstToken( String pString ) {
        pString = pString.trim();
        return getFirstToken(new StringBuffer(pString));
    }

    /**
     * Returns the first "token" out of the buffer.  Anything that is not
     * alpha numeric is considered a separator.  This version will modify
     * the underlying the buffer object so that subsequent calls to this
     * method will effectively token through the list.  Useful for quick
     * tokenization where only a couple of tokens are needed.
     */
    static public String getFirstToken( StringBuffer pStringBuf){
        return getFirstToken(pStringBuf,null);
    }
    /**
     * Returns the first "token" out of the buffer.  Anything that is not
     * alpha numeric is considered a separator.  This version will modify
     * the underlying the buffer object so that subsequent calls to this
     * method will effectively token through the list.  Useful for quick
     * tokenization where only a couple of tokens are needed.
     */
    static public String getFirstToken( StringBuffer pStringBuf, char[] optAllowedChars ) {
        StringBuffer buf = new StringBuffer();
        if(pStringBuf.length() == 0){
            return pStringBuf.toString();
        }
        boolean usingOptAllowedChars =false;
        if(optAllowedChars != null){
            usingOptAllowedChars = optAllowedChars.length != 0;
        }
        char c=pStringBuf.charAt(0);
        pStringBuf.deleteCharAt(0);
        while(true){
            if(Character.isDigit(c) || Character.isLetter(c) || (usingOptAllowedChars && contains(optAllowedChars,c))){
                buf.append(c);
            }else{
                break;
            }
            if(pStringBuf.length() > 0){
                c=pStringBuf.charAt(0);
                pStringBuf.deleteCharAt(0);
            }else{
                break;
            }
        }
        return buf.toString();
    }


    /**
     * Mimics the List.contains method but for use on char arrays
     * @param pArray a char array, may be empty or null
     * @param pC a char value
     * @return true if pC is in the pArarray
     */
    static private boolean contains(char[] pArray, char pC){
        if(pArray == null){
            return false;
        }
        for(int i=0,len=pArray.length;i<len;i++){
            if(pC == pArray[i]){
                return true;
            }
        }
        return false;
    }

    static public String getEDIToken( String pSiteName ) {
    String sname = pSiteName;
    int siteNameIdx = sname.indexOf(' ');
    if ( siteNameIdx > 0 ) {
        // Trim the data after the first string.
        // This results in the fedstrip for the USPS account or
        // the store number for JCPenney.
        sname = sname.substring(0, siteNameIdx);
    }
    // Now check to see if this was a drop ship, in
    // which case, leading characters must be stripped.
    if ( sname.length() > 6 && sname.startsWith("DS") ) {
        sname = sname.substring( sname.length() - 6 );
    }

    return sname;
    }
  //---------------------------------------------------------------------------
  public static String getStoreDir()
  throws Exception
  {
     String storeDir = System.getProperty("storeDir");
     return storeDir;
  }

  public static boolean getClwSwitch()
  throws Exception
  {
     if("cleanwise".equalsIgnoreCase(getStoreDir())) return true;
     return false;
  }
  
/*  public static String getAlternatePortal(StoreData storeData) {
  	String returnValue = null;
  	if (storeData != null) {
	  	PropertyDataVector storeProperties = storeData.getMiscProperties();
	      for (int i = 0; i < storeProperties.size(); i++) {
	          PropertyData pD = (PropertyData) storeProperties.get(i);
	          String propType = pD.getPropertyTypeCd();
	          if (RefCodeNames.PROPERTY_TYPE_CD.ALTERNATE_UI.equals(propType)) {
	              String propValue = pD.getValue();
	          	if (Utility.isSet(propValue)) {
	          		returnValue= propValue.trim();
	          	}
	      		break;
	          }
	      }
  	}
  	return returnValue;
  }*/
  
   
  public static String getAlternatePortal(CleanwiseUser appUser) {
	    String storeAlternatePortal = getAlternatePortal(appUser.getUserStore());
	    String accountAlternatePortal = getAlternatePortal(appUser.getUserAccount());
	    
	  	String returnValue = null;
	  	
	  	if (Utility.isSet(storeAlternatePortal)) {
	  		returnValue	= storeAlternatePortal;
	  	} else if (Utility.isSet(accountAlternatePortal)) {
	  		returnValue = accountAlternatePortal;
	  	}
	  	return returnValue;
  }
  
  public static String getAlternatePortal(ValueObject obj) {
	  	String returnValue = null;
	  	if (obj != null) {
	  		PropertyDataVector properties = null;
	  		if (obj instanceof StoreData ) {
	  			properties = ((StoreData)obj).getMiscProperties();
	  		  	log.info("getAlternatePortal()===> BEGIN for Store:"+ ((StoreData)obj).getStoreId() );
	  		} else if (obj instanceof AccountData){
	  			properties = ((AccountData)obj).getMiscProperties();
	  		  	log.info("getAlternatePortal()===> BEGIN for Account:" + ((AccountData)obj).getAccountId());
	  		}
	  		for (int i = 0; i < properties.size(); i++) {
	          PropertyData pD = (PropertyData) properties.get(i);
	          String propType = pD.getPropertyTypeCd();
	          if (RefCodeNames.PROPERTY_TYPE_CD.ALTERNATE_UI.equals(propType)) {
	        	  log.info("getAlternatePortal()===> propType = "+ propType+ ", pD.getValue()= "+ pD.getValue());
	              String propValue = 
	            	  (Constants.TRUE.equalsIgnoreCase(pD.getValue()) ?  Constants.PORTAL_ESW : 
	            	  (Constants.FALSE.equalsIgnoreCase(pD.getValue()) ? "" : pD.getValue()));
	          	if (Utility.isSet(propValue)) {
	          		returnValue= propValue.trim();
	          	}
	      		break;
	          }
	  		}
	  	}
	  	log.info("getAlternatePortal()===> returnValue = "+ returnValue);
	  	return returnValue;
  } 
  
  public static Properties loadJbossProperties()
  throws Exception
  {  
    /* commented YKR25
    String propertiesName = "jboss.properties";
    java.io.InputStream propertiesStream =
         Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName);
    Properties properties = new Properties();
    String errorMess;
    if(propertiesStream==null) {
      errorMess = "loadJbossProperties(). Cannot find properties file: " +propertiesName;
      throw new Exception(errorMess);
    }
    try {
      properties.load(propertiesStream);
    }catch(java.io.IOException exc) {
      errorMess = "loadJbossProperties(). " +exc.getMessage();
      throw new Exception(errorMess);
    }
    return properties;
	*/
	return loadProperties("jboss.properties");
  }
/* replaced YKR25
  public static Properties loadProperties(String pPropertiesName)
  throws Exception
  {
    java.io.InputStream propertiesStream =
         Thread.currentThread().getContextClassLoader().getResourceAsStream(pPropertiesName);
    Properties properties = new Properties();
    String errorMess;
    if(propertiesStream==null) {
      errorMess = "Cannot find properties file: " +pPropertiesName;
      throw new Exception(errorMess);
    }
    try {
      properties.load(propertiesStream);
    }catch(java.io.IOException exc) {
      errorMess = "loadJbossProperties(). " +exc.getMessage();
      throw new Exception(errorMess);
    }
    return properties;
  }
*/
  public static Properties loadProperties(String pPropertiesName) //YKR25
  throws Exception
  {
    java.io.InputStream propertiesStream =
         Thread.currentThread().getContextClassLoader().getResourceAsStream(pPropertiesName);
    Properties properties = new Properties();
    String errorMess;
    if(propertiesStream==null) {
      errorMess = "Cannot find properties file: " +pPropertiesName;
      throw new Exception(errorMess);
    }
    try {
      Properties wrkProperties = new Properties();
      wrkProperties.load(propertiesStream);
	  Enumeration propNamesEnum = wrkProperties.propertyNames();
      while(propNamesEnum.hasMoreElements()){
	     String prName = (String) propNamesEnum.nextElement();
		 String prValue = System.getProperty(prName);
		 if(prValue==null) {
			prValue = wrkProperties.getProperty(prName);
			properties.put(prName,prValue);
		    log.info("File value "+pPropertiesName+"."+prName+" = "+prValue);
		 } else {
			properties.put(prName,prValue);
		    log.info("System value "+pPropertiesName+"."+prName+" = "+prValue);
		 }
	  }
    }catch(java.io.IOException exc) {
	  exc.printStackTrace();
      errorMess = "loadJbossProperties(). " +exc.getMessage();
      throw new Exception(errorMess);
    }
    return properties;
  }

  /**
   *Returns a number normalized to something that is translatable into a numeric value.
   *Null values are not translated into anything, but will not cause exceptions.
   *This method does not guarantee that the resulting String will be numeric, it applies a set of
   *rules against the passed in String, but does not do any checking to verify that the String is
   *a numeric.  Such error checking should be done at the time of parsing, not when calling this method.
   *for example:
   *(asdf) = -asdf
   *(12.02) = -12.02
   *1,200=1200
   *+45 = 45
   *-12 = -12
   *null = null
   *@param s to be normalized
   *@returns normalized String
   */
  public static String normailzeNumberString(String s){
      if(s==null){
        return s;
      }
      s = trimLeft(s, "+");
      if(s.indexOf('(') == 0 && s.indexOf(')') == s.length()-1){
        s = "-" + s.substring(1,s.length()-1);
      }
      s = s.replace(',','\0');
      return s;
  }

  /**
   *Parses out a string and returns 0 if there were any problems
   */
  public static int parseInt(String s){
      try{
        s = normailzeNumberString(s);
        return Integer.parseInt(s);
      }catch(RuntimeException e){
          return 0;
      }
  }

  /**
   *Parses out a string and returns 0 if there were any problems
   */
  public static java.math.BigDecimal parseBigDecimal(String s){
      try{
        s = normailzeNumberString(s);
        return new java.math.BigDecimal(s);
      }catch(RuntimeException e){
        return new java.math.BigDecimal(0);
      }
  }

  /**
     *Parses out the meta data into the column headers and the column count properties of a
     *supplied GenericReportResultView object.
     */
    public static void parseResultSetDataForReport(java.sql.ResultSet rs,GenericReportResultView result)
    throws java.sql.SQLException{
        java.sql.ResultSetMetaData rsmd = rs.getMetaData();
        result.setColumnCount(rsmd.getColumnCount());
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        int colQty = rsmd.getColumnCount();
        for(int ii=1; ii<=colQty; ii++) {
            GenericReportColumnView column = GenericReportColumnView.createValue();
            column.setColumnClass(rsmd.getColumnClassName(ii));
            parseColumnNameAndWidth(column, rsmd.getColumnName(ii));
            column.setColumnType(rsmd.getColumnTypeName(ii));
            column.setColumnPrecision(rsmd.getPrecision(ii));
            column.setColumnScale(rsmd.getScale(ii));
            header.add(column);
        }
        result.setHeader(header);
        ArrayList records = new ArrayList();
        while (rs.next() ) {
            ArrayList record = new ArrayList();
            for(int ii=1; ii<=colQty; ii++) {
                record.add(rs.getObject(ii));
            }
            records.add(record);
        }
        result.setTable(records);
    }

    /**
     *Parses out a payment terms code into the human redable form.  For example:
     *N15 = Net 15
     *Nx = Net x
     *PRE = PREPAID
     *
     *@param pTermsCode to parse
     *@returns the human readable (parsed) translation of this code
     */
    public static String parseTermsCode(String pTermsCode){
        if(pTermsCode == null){
            return "";
        }
        String ret;
        pTermsCode = pTermsCode.toUpperCase();
        if(pTermsCode.startsWith("N")){
            ret = "Net "+pTermsCode.substring(1)+" days";
        }else if (pTermsCode.equalsIgnoreCase(RefCodeNames.PAYMENT_TERMS_CD.PRE)){
            ret = "PREPAID";
        }else{
            ret = pTermsCode;
        }
        return ret;
    }

    /**
     *converts a list into an unescaped comma separated list
     *i.e.:
     *List l;
     *l.add(1);l.add(2);l.add(3);
     *would become 1,2,3
     *alternatively
     *List l;
     *l.add("a,b,c,d,f,g");l.add("abc");l.add("dfg");
     *would become a,b,c,d,f,g,abc,dfg
     */
    public static String toCommaSting(java.util.Collection pList){
        return toCommaSting(pList,(Character) null);
    }

    /**
     *Convenience method for @see toCommaSting(java.util.Collection,Character);
     */
    public static String toCommaSting(java.util.Collection pList, char quoteChar){
        return toCommaSting(pList,new Character(quoteChar));
    }

    /**
     *converts a list into an unescaped comma separated list
     *i.e.:
     *List l;
     *l.add(1);l.add(2);l.add(3);
     *would become 1,2,3
     *alternatively
     *List l;
     *l.add("a,b,c,d,f,g");l.add("abc");l.add("dfg");
     *would become a,b,c,d,f,g,abc,dfg
     *@param quoteChar Character to append around the list
     */
    public static String toCommaSting(java.util.Collection pList, Character quoteChar){
        char c = ' ';
        boolean appendChar = false;
        if(quoteChar != null){
            appendChar = true;
            c = quoteChar.charValue();
        }
        Iterator it = pList.iterator();
        StringBuffer ret = new StringBuffer();
        while (it.hasNext()){
            if(appendChar){
                ret.append(c);
            }
            ret.append(it.next());
            if(appendChar){
                ret.append(c);
            }
            if(it.hasNext()){
                ret.append(",");
            }
        }
        return ret.toString();
    }

    public static int getId(Object obj) {
        if (obj instanceof BusEntityData) {
            return ((BusEntityData) obj).getBusEntityId();
        } else if (obj instanceof AccountData) {
            return ((AccountData) obj).getBusEntity().getBusEntityId();
        } else if (obj instanceof DistributorData) {
            return ((DistributorData) obj).getBusEntity().getBusEntityId();
        } else if (obj instanceof SiteData) {
            return ((SiteData) obj).getBusEntity().getBusEntityId();
        } else if (obj instanceof UserData) {
            return ((UserData) obj).getUserId();
        } else if (obj instanceof GroupData) {
            return ((GroupData) obj).getGroupId();
        } else if (obj instanceof CatalogData) {
            return ((CatalogData) obj).getCatalogId();
        } else if (obj instanceof StoreData) {
            return ((StoreData) obj).getBusEntity().getBusEntityId();
        } else if (obj instanceof CostCenterData) {
            return ((CostCenterData) obj).getCostCenterId();
        } else if (obj instanceof ManufacturerData) {
            return ((ManufacturerData) obj).getBusEntity().getBusEntityId();
        } else if (obj instanceof InvoiceCustData) {
            return ((InvoiceCustData) obj).getInvoiceCustId();
        } else if (obj instanceof SiteView) {
            return ((SiteView) obj).getId();
        } else if (obj instanceof WorkOrderData) {
            return ((WorkOrderData) obj).getWorkOrderId();
        } else if (obj instanceof AssetData) {
            return ((AssetData) obj).getAssetId();
        } else if (obj instanceof ServiceProviderData) {
            return ((ServiceProviderData) obj).getBusEntity().getBusEntityId();
        } else if (obj instanceof WarrantyData) {
            return ((WarrantyData) obj).getWarrantyId();
        } else if (obj instanceof CatalogCategoryData) {
            return ((CatalogCategoryData) obj).getCatalogCategoryId();
        } else if (obj instanceof AccountSearchResultView) {
            return ((AccountSearchResultView) obj).getAccountId();
        } else if (obj instanceof FiscalCalenderData) {
            return ((FiscalCalenderData) obj).getFiscalCalenderId();
        } else if (obj instanceof UiPageData) {
            return ((UiPageData) obj).getUiPageId();
        } else if (obj instanceof UiControlData) {
            return ((UiControlData) obj).getUiControlId();
        } else if (obj instanceof UiControlElementData) {
            return ((UiControlElementData) obj).getUiControlId();
        } else if (obj instanceof BudgetData) {
            return ((BudgetData) obj).getBudgetId();
        } else if (obj instanceof AccountView) {
            return ((AccountView) obj).getAcctId();
        } else if (obj instanceof InventoryLevelData) {
            return ((InventoryLevelData) obj).getInventoryLevelId();
        } else if (obj instanceof SiteInventoryInfoView) {
            return ((SiteInventoryInfoView) obj).getItemId();
        } else if (obj instanceof ShoppingCartItemData) {
            return ((ShoppingCartItemData) obj).getItemId();
        } else if (obj instanceof ItemData) {
            return ((ItemData) obj).getItemId();
        } else if (obj instanceof ItemAssocData) {
            return ((ItemAssocData) obj).getItemAssocId();
        } else if (obj instanceof ItemMappingData) {
            return ((ItemMappingData) obj).getItemMappingId();
        } else if (obj instanceof ContractData) {
            return ((ContractData) obj).getContractId();
        } else if (obj instanceof ShoppingControlData) {
            return ((ShoppingControlData) obj).getShoppingControlId();
        } else if (obj instanceof InventoryItemsData) {
            return ((InventoryItemsData) obj).getInventoryItemsId();
        } else if (obj instanceof OrderGuideData) {
            return ((OrderGuideData) obj).getOrderGuideId();
        } else if (obj instanceof OrderGuideStructureData) {
            return ((OrderGuideStructureData) obj).getOrderGuideStructureId();
        }else if (obj instanceof ProductData) {
            return ((ProductData) obj).getProductId();
        }else if (obj instanceof PriceListData) {
            return ((PriceListData) obj).getPriceListId();
        }else if (obj instanceof InvoiceDistData) {
            return ((InvoiceDistData) obj).getInvoiceDistId();
        }else if (obj instanceof CatalogStructureData) {
        	return ((CatalogStructureData) obj).getCatalogStructureId();
        }else if (obj instanceof AllStoreData) {
        	return ((AllStoreData) obj).getAllStoreId();	
        }else {
            throw new UnsupportedOperationException("getId does not support objects of type: " + obj.getClass().getName());
        }
    }

       /**
     *Converts a known set of DataVectors to an IdVector.  Supports:
     *BusEntityDataVector, AccountDataVector --> BusEntityIds, UserDataVector,
     *SiteDataVector --> BusEntityIds, DistributorDataVector --> BusEntityIds,
     *StoreDataVector --> BusEntityIds, CatalogDataVector 
     *
     */
    public static IdVector toIdVector(java.util.List pList){
        IdVector ids = new IdVector();
        if(pList == null){
            return ids;
        }
        Iterator it = pList.iterator();
        while(it.hasNext()){
            Object obj = it.next();
            if(obj!= null){
                int id = getId(obj);
                ids.add(new Integer(id));
            }
        }
        return ids;
    }

    public static HashMap toMap(List pDataVector) {
        HashMap map = new HashMap();
        if (pDataVector == null) {
            return map;
        }
        for (Object oData : pDataVector) {
            if (oData != null) {
                map.put(getId(oData), oData);
            }
        }
        return map;
    }

    public static HashSet<String> toShortDesc(List pList) {

        HashSet<String> hSet = new HashSet<String>();

        if (pList == null) {
            return hSet;
        }

        for (Object obj : pList) {
            if (obj != null) {
                String shortDesc;
                if (obj instanceof BusEntityData) {
                    shortDesc = ((BusEntityData) obj).getShortDesc();
                } else if (obj instanceof AccountData) {
                    shortDesc = ((AccountData) obj).getBusEntity().getShortDesc();
                } else if (obj instanceof DistributorData) {
                    shortDesc = ((DistributorData) obj).getBusEntity().getShortDesc();
                } else if (obj instanceof SiteData) {
                    shortDesc = ((SiteData) obj).getBusEntity().getShortDesc();
                } else if (obj instanceof UserData) {
                    shortDesc = ((UserData) obj).getUserName();
                } else if (obj instanceof GroupData) {
                    shortDesc = ((GroupData) obj).getShortDesc();
                } else if (obj instanceof CatalogData) {
                    shortDesc = ((CatalogData) obj).getShortDesc();
                } else if (obj instanceof StoreData) {
                    shortDesc = ((StoreData) obj).getBusEntity().getShortDesc();
                } else if (obj instanceof CostCenterData) {
                    shortDesc = ((CostCenterData) obj).getShortDesc();
                } else if (obj instanceof ManufacturerData) {
                    shortDesc = ((ManufacturerData) obj).getBusEntity().getShortDesc();
                } else if (obj instanceof SiteView) {
                    shortDesc = ((SiteView) obj).getName();
                } else if (obj instanceof WorkOrderData) {
                    shortDesc = ((WorkOrderData) obj).getShortDesc();
                } else if (obj instanceof AssetData) {
                    shortDesc = ((AssetData) obj).getShortDesc();
                } else if (obj instanceof ServiceProviderData) {
                    shortDesc = ((ServiceProviderData) obj).getBusEntity().getShortDesc();
                } else if (obj instanceof WarrantyData) {
                    shortDesc = ((WarrantyData) obj).getShortDesc();
                } else if (obj instanceof AccountSearchResultView) {
                    shortDesc = ((AccountSearchResultView) obj).getShortDesc();
                } else if (obj instanceof FiscalCalenderData) {
                    shortDesc = ((FiscalCalenderData) obj).getShortDesc();
                } else if (obj instanceof UiPageData) {
                    shortDesc = ((UiPageData) obj).getShortDesc();
                } else if (obj instanceof UiControlData) {
                    shortDesc = ((UiControlData) obj).getShortDesc();
                } else if (obj instanceof UiControlView) {
                    shortDesc = ((UiControlView) obj).getUiControlData().getShortDesc();
                } else if (obj instanceof UiControlElementData) {
                    shortDesc = ((UiControlElementData) obj).getShortDesc();
                } else if (obj instanceof BudgetData) {
                    shortDesc = ((BudgetData) obj).getShortDesc();
                } else if (obj instanceof AccountView) {
                    shortDesc = ((AccountView) obj).getAcctName();
                } else {
                    throw new UnsupportedOperationException("toShortDesc does not support objects of type: " + obj.getClass().getName());
                }
                hSet.add(shortDesc);
            }
        }

        return hSet;

    }
  /**
  *If there is one active bus entity in list it will be returned, if not and there is one limited
  *bus entity it will be returned, otherwise if the list is only of length==1 then the singular
  *busentity is returned, otherwise null is returned.
  */
 public static BusEntityData getBestbusEntityData(BusEntityDataVector bedv){
     if(bedv.size()==0) {
         return null;
     } else if(bedv.size()>1) { //pick up the active one
         BusEntityData bed = null;
         int foundActive = 0;
         int foundLimited = 0;
         for(int i=0;i<bedv.size();i++){
             BusEntityData tempbed = (BusEntityData) bedv.get(i);
             if(tempbed.getBusEntityStatusCd().equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE)){
                 foundActive++;
                 bed = tempbed;
             }else if(tempbed.getBusEntityStatusCd().equals(RefCodeNames.BUS_ENTITY_STATUS_CD.LIMITED)){
                 foundLimited++;
                 if(bed == null){
                     bed = tempbed;
                 }
             }else{
                 if(bed == null){
                     bed = tempbed;
                 }
             }
         }
         boolean error = false;
         if (foundActive > 1){
             error = true;
         }else if (foundActive == 1){
             error = false;
         }else if (foundLimited == 1){
             error = false;
         }else{
             error = true;
         }
         if(error){
             return null;
         }else{
             return bed;
         }
     } else {
         return (BusEntityData) bedv.get(0);
     }
 }

     /**
      *Converts an orderItemDescViewVector into an OrderItemDataVector
      *@param pOrderItemDescDataVector to convert
      *@returns a converted OrderItemDataVector
      */
     public static OrderItemDataVector toOrderItemDataVector(OrderItemDescDataVector pOrderItemDescDataVector){
         OrderItemDataVector returnVal = new OrderItemDataVector();
         Iterator it = pOrderItemDescDataVector.iterator();
         while(it.hasNext()){
            OrderItemDescData e = (OrderItemDescData) it.next();
            returnVal.add(e.getOrderItem());
         }
         return returnVal;
     }

     /**
      *Converts an AccountDataVector into an AccountSearchResultViewVector
      *@param AccountDataVector to convert
      *@returns a converted AccountSearchResultViewVector
      */
  public static AccountSearchResultViewVector toAccountSearchResultViewVector(AccountDataVector pAccountDataVector){
	AccountSearchResultViewVector returnVal = new AccountSearchResultViewVector();
    Iterator it = pAccountDataVector.iterator();
    while(it.hasNext()){
      AccountData e = (AccountData) it.next();
      AccountSearchResultView accountResult = new AccountSearchResultView();
	  accountResult.setAccountId(e.getBusEntity().getBusEntityId());
	  accountResult.setErpNum(e.getBusEntity().getErpNum());
	  accountResult.setShortDesc(e.getBusEntity().getShortDesc());
	  accountResult.setCity(e.getPrimaryAddress().getCity());
	  accountResult.setStateProvinceCd(e.getPrimaryAddress().getStateProvinceCd());
	  accountResult.setValue(e.getAccountType().getValue());
	  accountResult.setBusEntityStatusCd(e.getBusEntity().getBusEntityStatusCd());
	  accountResult.setAddress1(e.getPrimaryAddress().getAddress1());
	  accountResult.setPostalCode(e.getPrimaryAddress().getPostalCode());
	  accountResult.setCountryCd(e.getPrimaryAddress().getCountryCd());

	  returnVal.add(accountResult);
    }
    return returnVal;
  }

  /**
   *Converts an AccountSearchResultViewVector into an AccountDataVector
   *@param AccountSearchResultViewVector to convert
   *@returns a converted AccountDataVector
   */
  public static AccountDataVector toAccountDataVector(AccountSearchResultViewVector pAccountSearchResultVector){
	AccountDataVector returnVal = new AccountDataVector();
	Iterator it = pAccountSearchResultVector.iterator();
	while(it.hasNext()){
	  AccountSearchResultView accountSearchResult = (AccountSearchResultView) it.next();
	  AccountData accountData = AccountData.createValue();
	  BusEntityData busData = new BusEntityData();
	  AddressData addressData = new AddressData();
	  PropertyData propData = new PropertyData();

	  busData.setBusEntityId(accountSearchResult.getAccountId());
	  busData.setErpNum(accountSearchResult.getErpNum());
	  busData.setShortDesc(accountSearchResult.getShortDesc());
	  busData.setBusEntityStatusCd(accountSearchResult.getBusEntityStatusCd());

	  addressData.setCity(accountSearchResult.getCity());
	  addressData.setStateProvinceCd(accountSearchResult.getStateProvinceCd());
	  addressData.setAddress1(accountSearchResult.getAddress1());
	  addressData.setPostalCode(accountSearchResult.getPostalCode());
	  addressData.setCountryCd(accountSearchResult.getCountryCd());

	  propData.setValue(accountSearchResult.getValue());

	  accountData.setBusEntity(busData);
	  accountData.setPrimaryAddress(addressData);
	  accountData.setAccountType(propData);

	  returnVal.add(accountData);
    }
    return returnVal;
  }



     /**
      *Converts an AddressData object into an OrderAddressData object.  Converts only properties
      *that are shared by both objects.  Caller is responsible for setting relational ids
      *and any other data not present in an AddressData object.
      *@param the AddressData object to convert
      *@returns the new OrderAddressData
      */
     public static OrderAddressData toOrderAddress(AddressData pAddr){
         OrderAddressData oa = OrderAddressData.createValue();
         oa.setAddress1(pAddr.getAddress1());
         oa.setAddress2(pAddr.getAddress2());
         oa.setAddress3(pAddr.getAddress3());
         oa.setAddress4(pAddr.getAddress4());
         oa.setCity(pAddr.getCity());
         oa.setCountryCd(pAddr.getCountryCd());
         oa.setCountyCd(pAddr.getCountyCd());
         oa.setPostalCode(pAddr.getPostalCode());
         oa.setStateProvinceCd(pAddr.getStateProvinceCd());
         oa.setAddressTypeCd(pAddr.getAddressTypeCd());
         return oa;
     }

     /**
      *Converts an OrderAddressData object into an AddressData object.  Converts only properties
      *that are shared by both objects.  Caller is responsible for setting relational ids
      *and any other data not present in an AddressData object.
      *@param the AddressData object to convert
      *@returns the new OrderAddressData
      */
     public static AddressData toAddress(PreOrderAddressData pOa){
         AddressData addr = AddressData.createValue();
         addr.setAddress1(pOa.getAddress1());
         addr.setAddress2(pOa.getAddress2());
         addr.setAddress3(pOa.getAddress3());
         addr.setAddress4(pOa.getAddress4());
         addr.setCity(pOa.getCity());
         addr.setCountryCd(pOa.getCountryCd());
         addr.setCountyCd(pOa.getCountyCd());
         addr.setPostalCode(pOa.getPostalCode());
         addr.setStateProvinceCd(pOa.getStateProvinceCd());
         addr.setAddressTypeCd(pOa.getAddressTypeCd());
         return addr;
     }

     public static AddressData toAddress(OrderAddressData pOa){
         AddressData addr = AddressData.createValue();
         addr.setAddress1(pOa.getAddress1());
         addr.setAddress2(pOa.getAddress2());
         addr.setAddress3(pOa.getAddress3());
         addr.setAddress4(pOa.getAddress4());
         addr.setCity(pOa.getCity());
         addr.setCountryCd(pOa.getCountryCd());
         addr.setCountyCd(pOa.getCountyCd());
         addr.setPostalCode(pOa.getPostalCode());
         addr.setStateProvinceCd(pOa.getStateProvinceCd());
         addr.setAddressTypeCd(pOa.getAddressTypeCd());
         return addr;
     }


     /**
     *Calculates the "mod 10 checksum" digit for the given *NUMERIC* string
     *Manual method MOD 10 checkdigit.  Uses a weight of 3 for the odd digit.
     * 706511227
     *
     * 7 0 6 5 1 1 2 2 7
     *  *3  *3  *3  *3
     * ---------------------------------
     * 7 + 0 + 6 + 15 + 1 + 3 + 2 + 6 + 7 = 37
     *
     * 37 MOD 10 = 7
     *
     * 10 - 7 = 3 -- the check digit
     */
    public static int calculateCheckSumDigit( String pNumericText ) {
        int checksum = 0;
        for ( int place=0,len=pNumericText.length(); place<len; place++ ) {
            int digit = Integer.parseInt(new Character(pNumericText.charAt(place)).toString());
            if ( (place % 2) == 0 ) {
                // even position, just add digit
                checksum += (3*(digit));
            } else {
                // odd position, must double and add
                checksum += digit;
            }
        }
        checksum = checksum % 10;
        //if the checksum = 0 then return 0, not 10 - 0 (10)
        if(checksum == 0){
            return 0;
        }else{
            return 10-(checksum);
        }
    }

    /**
     *Given the package id will calculate the checkdigit for a USPS Delivery Confirmation, or other
     *such service request for the supplied numeric text.  See the @see calculateCheckSumDigit Method.
     */
    static public int calculateUSPSDeliveryServiceCheckDigitFromPackageId(String pNumericText){
        return calculateCheckSumDigit(RefCodeNames.APPLICATION_IDENTIFIER.SERVICE_TYPE_CD + pNumericText);
    }

    /**
     *adds the specified number of weekdays from the supplied Date object and returns a new
     *Date object.  If you want to subtract pNumberOfDays should be negative.
     */
    static public Date addWeekdays(Date pDate, int pNumberOfDays){
        int aDay = -1 ;
        if(pNumberOfDays > 0){
            aDay = 1;
        }else{
            //positivise pNumberOfDays for loop
            pNumberOfDays = pNumberOfDays * -1;
        }
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(pDate);
        for(int i=0;i<pNumberOfDays;i++){
            cal.add(Calendar.DAY_OF_YEAR, aDay);
            int dow = cal.get(Calendar.DAY_OF_WEEK);
            while(dow == Calendar.SATURDAY || dow == Calendar.SUNDAY){
                cal.add(Calendar.DAY_OF_YEAR, aDay);
                dow = cal.get(Calendar.DAY_OF_WEEK);
            }
        }
        return cal.getTime();
    }

    /**
     *adds the specified number of days from the supplied Date object and returns a new
     *Date object.  If you want to subtract pNumberOfDays should be negative.
     */
    static public Date addDays(Date pDate, int pNumberOfDays){
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(pDate);
        cal.add(Calendar.DAY_OF_YEAR, pNumberOfDays);

        return cal.getTime();
    }

    /**
     *Returns the 2 character country code based off the full country name,
     *eg: United States will return US.
     */
    public static String getCountryCodeFromCountry(String pCountry){
        pCountry = pCountry.toLowerCase();
        if(pCountry.startsWith("united states")){
            return "US";
        }else if(pCountry.startsWith("canada")){
            return "CA";
        }
        return "US";
    }

    /**
     *Returns true if pValue = is a string representation of something that is true.  A true value is any integer greater than 0,
     *anything starting with t, or y.
     *If value is not set ("",null,"   ", @see isSet) evaluates to false.
     *@param a String
     *@returns the boolean value as to whether it is true or not
     */
    public static boolean isTrue(String pValue){
        return isTrue(pValue, false);
    }

    /**
     *Returns true if pValue = is a string representation of something that is true.  A true value is any integer greater than 0,
     *anything starting with t, or y.
     *If value is not set ("",null,"   ", @see isSet) evaluates to passed in defaultValue.
     *@param a String
     *@returns the boolean value as to whether it is true or not
     */
    public static boolean isTrue(String pValue, boolean defaultValue){
        if(pValue != null){pValue = pValue.trim();}
        if(pValue != null && pValue.length() > 0){
            try{
                return Integer.parseInt(pValue) > 0;
            }catch(Exception e){
                String first = pValue.substring(0,1);
                return (first.equalsIgnoreCase("t") || first.equalsIgnoreCase("y") || first.equalsIgnoreCase("1"));
            }
        }
        return defaultValue;
    }

    public static boolean isOn(String pValue, boolean defaultValue){
        if(pValue != null){pValue = pValue.trim();}
        if(pValue != null && pValue.length() > 0){
        return pValue.equalsIgnoreCase(Constants.ON);
        }
        return defaultValue;
    }

    public static boolean isOn(String pVal) {
        return isOn(pVal, false);
    }

    public static boolean isOff(String pValue, boolean defaultValue){
        if(pValue != null){pValue = pValue.trim();}
        if(pValue != null && pValue.length() > 0){
        return pValue.equalsIgnoreCase(Constants.OFF);
        }
        return defaultValue;
    }

    public static boolean isSelected(String pVal) {
        return isSelected(pVal, false);
    }

    public static boolean isSelected(String pVal, boolean defaultValue) {
        return Utility.isTrue(pVal, defaultValue) || Utility.isOn(pVal, defaultValue);
    }

    /**
     *Converts a String to alpha numeric with spaces allowed, and normal punctuation (,.? etc):
     *abcd e%$fghi becomes abcd efghi
     *
     */
    public static String removeSpecialCharachters(String pString){
    	return removeSpecialCharachters(pString, null);
    }
    /**
     *Converts a String to alpha numeric with spaces allowed, and normal punctuation (,.? etc):
     *abcd e%$fghi becomes abcd efghi
     *Optionally replaces the special characters with a different char
     *
     */
    public static String removeSpecialCharachters(String pString,Character optReplacementChar){
        StringBuffer pTo = new StringBuffer();
        StringBuffer pFrom = new StringBuffer(pString);
        for(int i=0,len=pFrom.length();i<len;i++){
            char ch = pFrom.charAt(i);
            if(Character.isLetterOrDigit(ch) || ch == ' ' || ch == '.' || ch == ',' || ch == '?' || ch == '!'){
                pTo.append(ch);
            }else if(optReplacementChar != null){
            	pTo.append(optReplacementChar);
            }
        }
        return pTo.toString();
    }

    
    /**
     * Takes in a sql column (with the alias or table name if appropriate) and a list that may be over 1000 and returns a SQL in statement
     * breaking it up into chunks if the list of 10000.  For example:
     * <pre>
     * Utility.toSqlInClause("UA.USER_ID",userIds)
     * </pre>
     * Will return for large lists (greater than 1000):
     * <pre>
     *  (UA.USER_ID IN (1,2,3,4....) or UA.USER_ID IN (1000,1001,1002...)) 
     * </pre>
     * Or may return for small lists (less than 1000):
     * <pre>
     * UA.USER_ID IN (1,2,3,4)
     * </pre>
     * @param inColumn the column name (with alias or table name if necessary) for example CLW_USER.USER_ID or U.USER_ID
     * @param things must be non-empty non null list of items in the list.
     * @return A partial SQL statement suitable for inclusion in a larger SQL statement.
     */
    public static String toSqlInClause(String inColumn, List things){
    	if(things == null){
    		throw new NullPointerException("The list of things to put in the SQL in was null.");
    	}
    	if(things.size() == 0){
    		throw new RuntimeException("The list of things to put in the SQL in was 0 length.");
    	}
    	if(!Utility.isSet(inColumn)){
    		throw new RuntimeException("The inColumn passed in was not set (either null or empty string) ["+inColumn+"]");
    	}
    	int MAX_SQL_ITEMS = 1000;
    	int thingsSize = things.size();
    	StringBuffer sql = new StringBuffer();
    	//if under the 1000 list then just return the simple version.
    	if(thingsSize<= MAX_SQL_ITEMS){
    		sql.append(' ');
        	sql.append(inColumn);
        	sql.append(" IN ("+Utility.toCommaSting(things));
        	sql.append(") ");
        	return sql.toString();
    	}
    	//Deal with case where list is over 1000 items.
    	boolean first = true;
    	sql.append(" (");
        for (int i = 0; i < thingsSize; i+=MAX_SQL_ITEMS) {
            int end = (i + MAX_SQL_ITEMS > thingsSize) ? thingsSize : i + MAX_SQL_ITEMS;
            List subThings = things.subList(i,end);
            if(first){
            	sql.append(inColumn);
            }else{
            	sql.append(" or "+inColumn);
            }
        	sql.append(" IN (");
        	
        	sql.append(Utility.toCommaSting(subThings));
        	sql.append(')');
        	first = false;
        }
    	sql.append(") ");
    	return sql.toString();
    }

    /**
     * Parses date string using "MM/dd/yyyy" or "MM/dd/yyyy" format
     * Return date or null if error
     */
    static public Date parseDate(String pDateS) {
      if(pDateS==null) return null;
      String mmS = "";
      String ddS = "";
      String yyS = "";
      int ind1 = pDateS.indexOf('/');
      if(ind1<=0||ind1>=pDateS.length()-2||ind1>2) return null;
      mmS = pDateS.substring(0,ind1);
      int ind2 = pDateS.indexOf('/',ind1+1);
      if(ind2<0||ind2>=pDateS.length()-1||ind2-ind1>3||ind2-ind1==1) return null;
      ddS = pDateS.substring(ind1+1,ind2);
      yyS = pDateS.substring(ind2+1);
      if(yyS.length()>4) return null;
      int mm = 0;
      int dd = 0;
      int yy = 0;
      try {
        mm = Integer.parseInt(mmS);
      }catch (Exception exc) {
        return null;
      }
      try {
        dd = Integer.parseInt(ddS);
      }catch (Exception exc) {
        return null;
      }
      try {
        yy = Integer.parseInt(yyS);
        if(yyS.length()<=2) {
          yy+=2000;
        }
      }catch (Exception exc) {
        return null;
      }
      GregorianCalendar dateGC = null;
      try {
        dateGC = new GregorianCalendar(yy,mm-1,dd);
      }catch (Exception exc) {
        return null;
      }
      return dateGC.getTime();

    }

    public static Date parseDate(String dateStr, String pattern, 
    		boolean throwExc, boolean lenient) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(lenient);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            if (throwExc) throw new Exception(e.getMessage());
            return null;
        }
    }
    
    public static Date parseDate(String dateStr, String pattern, boolean throwExc) throws Exception {
      return parseDate(dateStr, pattern, throwExc, true);
    }
    
    /*
     * Method to convert any date string into en_US format to store as string in the database
     * pDateString: current date string e.g. 31/2/2011
     * currentPattern: current date pattern e.g. dd/MM/yyyy
     * return: date in en_US format (MM/dd/yyyy) 2/31/2011
     */
    public static String convertToDBString(String pDateString, String currentPattern, boolean lenient) throws Exception{
    	try {
    		SimpleDateFormat sdfCurrent = new SimpleDateFormat(currentPattern);
    		sdfCurrent.setLenient(lenient);
    		
    		Date currentDate = sdfCurrent.parse(pDateString);

            return convertDateToDBString(currentDate, lenient);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /*
     * Method to convert any date into en_US format string to store in the database
     * pDate: current date object
     * return: date in en_US format (MM/dd/yyyy) 
     */
    public static String convertDateToDBString(Date pDate, boolean lenient) throws Exception{
    	try {
    		
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
            sdf.setLenient(lenient);

            return sdf.format(pDate);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /*
     * Method to extract any date string from the database in en_US format
     * pDateString: current date string as stored in database (assume in en_US format)
     * return: date in en_US format
     */
    public static Date convertDBStringToDate(String pDateString, boolean lenient) throws Exception{
    	try {            
            return parseDate(pDateString, Constants.SIMPLE_DATE_PATTERN, false, lenient);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
   /**
     *Uses the supplied map as a cache with the erp num as the key. If the cache is null will just lookup
     *a distributor based off their erp number.  The cache will be updated automatically by this method.
     */
    public static BusEntityData getDistBusEntityByErpNumber(java.sql.Connection pCon,String erpNum,java.util.Map cache)
    throws java.sql.SQLException{
        if(cache!= null && cache.containsKey(erpNum)){
            return (BusEntityData) cache.get(erpNum);
        }
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
        crit.addEqualTo(BusEntityDataAccess.ERP_NUM,erpNum);
        BusEntityDataVector distVec = BusEntityDataAccess.select(pCon,crit);
        if(distVec.size()==0){
            cache.put(erpNum, null);
            return null;
        }
        if(distVec.size() > 1){
            throw new RuntimeException("Found more than 1 distributor for erp number: " + erpNum);
        }
        BusEntityData dist = (BusEntityData) distVec.get(0);
        cache.put(erpNum, dist);
        return dist;
    }

    /**
    *Safely concatenates 2 string taking into account nulls.  Thus:
    * concatonateStrings("a","b",":") will return: "a:b"
    * and
    * concatonateStrings("a",null,":") will return: "a"
    * and
    * concatonateStrings(null,"b",":") will return: "b"
    * That is to say the separator string is only used when both s1 and s2 are not null.
    */
     public static String concatonateStrings(String s1, String s2, String sep){
         if(s1 == null){
             return s2;
         }
         if(s2 == null){
             return s1;
         }
         return s1 + sep + s2;
     }

     public static String formatISODate(java.util.Date theDate){

        Calendar calendar = Calendar.getInstance();
    calendar.setTime(theDate);

    StringBuffer buf = new StringBuffer();
    buf.append(calendar.get(Calendar.YEAR));
    buf.append("-");
    buf.append(padLeft(calendar.get(Calendar.MONTH) + 1,'0',2));
    buf.append("-");
        buf.append(padLeft(calendar.get(Calendar.DAY_OF_MONTH),'0',2));
    buf.append("T");
        buf.append(padLeft(calendar.get(Calendar.HOUR_OF_DAY),'0',2));
    buf.append(":");
        buf.append(padLeft(calendar.get(Calendar.MINUTE),'0',2));
    buf.append(":");
        buf.append(padLeft(calendar.get(Calendar.SECOND),'0',2));
    buf.append(".");
        buf.append(padLeft(calendar.get(Calendar.MILLISECOND)/ 10,'0',2));

        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET)/ 3600000;
        if(zoneOffset > 0){
            buf.append("+");
        }
        buf.append(padLeft(zoneOffset,'0',2));
        buf.append(":");
        buf.append("00");

    return buf.toString();
     }


     private static final String goodOrderItemsCondition=
        "("+
        OrderItemDataAccess.ORDER_ITEM_STATUS_CD + " <> '" +
        RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED + "' OR " +
        OrderItemDataAccess.ORDER_ITEM_STATUS_CD + " IS NULL" +
        ")";
     /**
      *Returns a String suitable for use in a sql query to pickup only those
      *order item records that are in a "good" status, not cancelled etc.
      */
     public static String getGoodOrderItemsCondition(){
         return goodOrderItemsCondition;
     }


  /**
   *Creates a crypto util object using properties stored in the database and
   *initializes it for encryption.
   */
  public static CryptoUtil createCryptoUtil(PropertyService propEJB)throws java.rmi.RemoteException,CryptoException,DataNotFoundException{
      return createCryptoUtil(null,propEJB);
  }

  /**
   *Creates a crypto util object using properties stored in the database and
   *initializes it for encryption.
   */
  public static CryptoUtil createCryptoUtil(java.sql.Connection pCon)throws java.rmi.RemoteException,CryptoException,DataNotFoundException{
     return createCryptoUtil(pCon,null);
  }

  private static CryptoUtil createCryptoUtil(java.sql.Connection pCon,PropertyService propEJB)throws java.rmi.RemoteException,CryptoException,DataNotFoundException{
      String lKeystorefile = null;
      String lEncryptAlgorithm = null;
      String lProviderName = null;
      PropertyUtil pUtil = null;
      if(pCon != null){
          pUtil = new PropertyUtil(pCon);
      }
      if(propEJB!=null){
        lKeystorefile = propEJB.getProperty(CryptoPropNames.kKeyStorefilePropName);
      }else{
        lKeystorefile = pUtil.getProperty(CryptoPropNames.kKeyStorefilePropName);
      }
      if(propEJB!=null){
        lEncryptAlgorithm = propEJB.getProperty(CryptoPropNames.kAsymmetricAlgPropName);
      }else{
        lEncryptAlgorithm = pUtil.getProperty(CryptoPropNames.kAsymmetricAlgPropName);
      }

      try{
          if(propEJB!=null){
            lProviderName = propEJB.getProperty(CryptoPropNames.kProvider);
          }else{
            lProviderName = pUtil.getProperty(CryptoPropNames.kProvider);
          }
      }catch(Exception e){
      }
      if(!Utility.isSet(lKeystorefile)){
          throw new DataNotFoundException("Keystore File Name is not configured ("+CryptoPropNames.kKeyStorefilePropName+")");
      }
      if(!Utility.isSet(lEncryptAlgorithm)){
          throw new DataNotFoundException("Asymetric Encryption Algorithm is not configured ("+CryptoPropNames.kAsymmetricAlgPropName+")");
      }
      CryptoUtil crypto = new CryptoUtil();
      if(Utility.isSet(lProviderName)){
            crypto.initializeProvider(lProviderName);

      }
      crypto.initialize(null, lEncryptAlgorithm, lKeystorefile, null);
      return crypto;
  }

    static private boolean addrcomp(String a1, String a2) {
    if ( a1 == null ) a1 = "";
    if ( a2 == null ) a2 = "";
    a1 = a1.trim();
    a2 = a2.trim();
    if (a2.compareToIgnoreCase(a1)== 0) {
        return true;
    }
    return false;
    }

    static public boolean areAddressesEquivalent
    (AddressData a1, AddressData a2 ) {
    if ( a1 == null && a2 == null ) return true;
    if ( a1 == null ) return false;
    if ( a2 == null ) return false;
    if ( addrcomp(a1.getAddress1(), a2.getAddress1()) == false) {
        return false;
    }
    if ( addrcomp(a1.getAddress2(), a2.getAddress2()) == false) {
        return false;
    }
    if ( addrcomp(a1.getAddress3(), a2.getAddress3()) == false) {
        return false;
    }
    if ( addrcomp(a1.getAddress4(), a2.getAddress4()) == false) {
        return false;
    }
    if ( addrcomp(a1.getCity(), a2.getCity()) == false) {
        return false;
    }
    if ( addrcomp(a1.getPostalCode(), a2.getPostalCode()) == false) {
        return false;
    }
    if ( addrcomp(a1.getStateProvinceCd(), a2.getStateProvinceCd()) == false) {
        return false;
    }
    if ( addrcomp(a1.getCountryCd(), a2.getCountryCd()) == false) {
        return false;
    }
    return true;
    }


    static public boolean areAddressesEquivalent
    (OrderAddressData a1, OrderAddressData a2 ) {
    if ( a1 == null && a2 == null ) return true;
    if ( a1 == null ) return false;
    if ( a2 == null ) return false;
    if ( addrcomp(a1.getAddress1(), a2.getAddress1()) == false) {
        return false;
    }
    if ( addrcomp(a1.getAddress2(), a2.getAddress2()) == false) {
        return false;
    }
    if ( addrcomp(a1.getAddress3(), a2.getAddress3()) == false) {
        return false;
    }
    if ( addrcomp(a1.getAddress4(), a2.getAddress4()) == false) {
        return false;
    }
    if ( addrcomp(a1.getCity(), a2.getCity()) == false) {
        return false;
    }
    if ( addrcomp(a1.getPostalCode(), a2.getPostalCode()) == false) {
        return false;
    }
    if ( addrcomp(a1.getStateProvinceCd(), a2.getStateProvinceCd()) == false) {
        return false;
    }
    if ( addrcomp(a1.getCountryCd(), a2.getCountryCd()) == false) {
        return false;
    }
    return true;
    }

    public static String strNN(String pStr) {
      if(pStr==null) return "";
      return pStr;
    }

    public static Object getMapValue(Map pMap, Object pKey) {
        if (pMap != null) {
            return pMap.get(pKey);
        } else {
            return null;
        }
    }

    public static boolean mapNotEqual(Map<Integer, Integer> pMap1, Map<Integer, Integer> pMap2) {
        return !mapEqual(pMap1, pMap2);
    }

    public static boolean mapEqual(Map<Integer, Integer> pMap1, Map<Integer, Integer> pMap2) {

        if (pMap1 == null && pMap2 == null) {
            return true;
        } else if (pMap1 == null || pMap2 == null) {
            return false;
        } else if (pMap1.size() != pMap2.size()) {
            return false;
        }

        for (Integer oPeriod : pMap1.keySet()) {
            Integer parValue1 = pMap1.get(oPeriod);
            Integer parValue2 = pMap2.get(oPeriod);
            if (!intEqual(parValue1, parValue2)) {
                return false;
            }
        }

        return true;
    }

    public static boolean byteArrayEqual(byte[] pByteArray1, byte[] pByteArray2) {

        if (pByteArray1 == null && pByteArray2 == null) {
            return true;
        } else if (pByteArray1 == null || pByteArray2 == null) {
            return false;
        } else if (pByteArray1.length != pByteArray2.length) {
            return false;
        }

        for (int i = 0; i < pByteArray1.length; i++) {
            if (pByteArray1[i] != pByteArray2[i]) {
                return false;
            }
        }

        return true;
    }

    public static Integer getIntValueNN(Map<Integer, Integer> pMap, int pKey) {
        return intNN((Integer) getMapValue(pMap, pKey));
    }

    public static Integer getIntValue(Map<Integer, Integer> pMap, int pKey) {
        return (Integer) getMapValue(pMap, pKey);
    }

    public static Integer intNN(Integer pInt) {
        if (pInt == null) {
            return 0;
        } else {
            return pInt;
        }
    }

    public static boolean intEqual(Integer pInt1, Integer pInt2) {
        if (pInt1 == null) {
            return pInt2 == null;
        } else {
            return pInt2 != null && pInt1.equals(pInt2);
        }
    }

    public static BigDecimal bdNN(BigDecimal pBD) {
      if(pBD==null) return new BigDecimal(0);
      return pBD;
    }

    public static boolean bdEqual(BigDecimal pBD1, BigDecimal pBD2) {
      if(pBD1==null) {
        if(pBD2==null) {
           return true;
        } else {
           return false;
        }
      }
      if(pBD2==null) return false;
      return pBD1.equals(pBD2);
    }


    private static ArrayList goodOrderItemStatusCodes = new ArrayList();
    static{
        goodOrderItemStatusCodes.add(RefCodeNames.ORDER_ITEM_STATUS_CD.INVOICED);
        goodOrderItemStatusCodes.add(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_ERP_PO);
        goodOrderItemStatusCodes.add(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT);
        goodOrderItemStatusCodes.add(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT_PROCESSING);
        goodOrderItemStatusCodes.add(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
        goodOrderItemStatusCodes.add(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED);
        goodOrderItemStatusCodes.add(RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_SUCCESS);
        goodOrderItemStatusCodes.add(RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_ERROR);
        goodOrderItemStatusCodes.add(RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_REJECT);
        goodOrderItemStatusCodes.add(null);
    }
    public static boolean isGoodOrderItemStatus(String pOrderItemStatusCd){
        if(pOrderItemStatusCd == null){
            return true;
        }
        return goodOrderItemStatusCodes.contains(pOrderItemStatusCd);

    }

    /**
     *Returns a string representation of an exceptions stack trace.
     *java.lang.IllegalStateException: Document has data, but there are interchanges (malformed EDI)!
     *   at com.cleanwise.service.apps.edi.InboundEdiHandler.translate(InboundEdiHandler.java:469)
     *   at com.cleanwise.service.apps.dataexchange.InboundTranslate.translate(InboundTranslate.java:440)
     *   at com.cleanwise.service.apps.dataexchange.InboundTranslate.translateByFileName(InboundTranslate.java:393)
     *   at com.cleanwise.service.apps.dataexchange.InboundTranslate.translateDirectory(InboundTranslate.java:133)
     *   at com.cleanwise.service.apps.dataexchange.InboundTranslate.translateByFileName(InboundTranslate.java:365)
     *   at com.cleanwise.service.apps.dataexchange.InboundTranslate.translateDirectory(InboundTranslate.java:133)
     *   at com.cleanwise.service.apps.dataexchange.InboundTranslate.translateByFileName(InboundTranslate.java:365)
     *   at com.cleanwise.service.apps.dataexchange.InboundTranslate.main(Inbound
     */
    public static String getExceptionStackTrace(Exception e){
        StringWriter err = new StringWriter();
        PrintWriter err2 = new PrintWriter(err);
        e.printStackTrace(err2);
        return err.toString();
    }


    /**
     *Returns a list of the passed in objects properties as defined by the javaBean
     *specification.
     */
    public static List getJavaBeanProperties(Class clazz){
        List cache = (List) javaBeanCache.get(clazz);
        if(cache != null){
            return cache;
        }
        ArrayList props = new ArrayList();
        Method[] methods = clazz.getMethods();
        for(int i = 0, len=methods.length; i< len; i++){
            Method method = methods[i];
            //only interested in the public setters, and not methods that are just set()
            if(Modifier.isPublic(method.getModifiers()) && (method.getName().startsWith("set") || method.getName().startsWith("get"))
            		&& method.getName().length() > 3){
                //get the remainging method name after the get and lower case the first char
                StringBuffer name = new StringBuffer(method.getName().substring(3));
                name.setCharAt(0, Character.toLowerCase(name.charAt(0)));
                if(!props.contains(name.toString())){
                	props.add(name.toString());
                }
            }
        }
        javaBeanCache.put(clazz,props);
        return props;
    }

    /**
     *Creates a new instance of the passed in classname.  Knows about our value objects and how to instantiate them
     *using the createValue method.  Also knows how to instantiate inner classes, in the case of non-static
     *inner classes the createValue paradigm is ignored, and the only supported option is an "empty" constructor,
     *(constructors are not really empty for non-static inner classes, but the caller does not have to worry about this).
     *Inner classes should be denoted in the standard format:
     *com.cleanwise.package.Outer$Inner
     */
    public static Object createInstanceFromString(String className)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException
    {
        if(className == null){
            throw new NullPointerException("Utility.createInstanceFromString Class Name Was null");
        }
        Class lClass = null;
        try{
            lClass = Thread.currentThread().getContextClassLoader().loadClass(className);

        }catch(ClassNotFoundException e){
            log.info("Error loading class: "+className);
            throw e;
        }catch(RuntimeException e){
            log.info("Error loading class: "+className);
            throw e;
        }
        Object lObj=null;
        //if this is one of our value objects, with a createValue
        //method then use that, otherwise try to construct the
        //object useing newInstance
        try{
            Method create = lClass.getMethod("createValue",new Class[0]);
            lObj = create.invoke(null,new Object[0]);
        }catch (NoSuchMethodException e){
          if(lObj == null){
            try{
              lObj = lClass.newInstance();
            }catch(InstantiationException e3){
              if(className.indexOf("$")>0){
                    try{
                      int splitOn = className.lastIndexOf("$");
                      //inner class
                      className = className.substring(0,splitOn);
                      Class outer = Thread.currentThread().getContextClassLoader().loadClass(className);
                      Class[] outerAr = new Class[1];
                      outerAr[0] = outer;

                      Object outerObj = createInstanceFromString(className);
                      Object[] outerObjAr = new Object[1];
                      outerObjAr[0] = outerObj;
                      lObj = lClass.getConstructor(outerAr).newInstance(outerObjAr);
                    }catch (Exception e2){
                      log.info("Caught exception trying to process inner class invocation: "+ e2);
                      log.info("Rethrowing original error");
                      throw e3;
                    }
                  }
            }
          }
        }
        return lObj;
    }


    /**
     *Finds the setter method of the passed in object based off the javaBean property.
     *Thus myProp would find the method setMyProp of the passed in object.
     */
    public static Method getJavaBeanSetterMethod(Object pObj, String javaBeanProperty){
        //convert the javaBeanProperty to as set method
        char firstChar = Character.toUpperCase(javaBeanProperty.charAt(0));
        String theRest = javaBeanProperty.substring(1);
        String methodName = "set" + firstChar + theRest;
        Method[] methods = pObj.getClass().getMethods();
        for(int i=0;i<methods.length;i++){
            Method method = methods[i];
            if(method.getName().equals(methodName)){
                return method;
            }
        }
        return null;
    }
    
    /**
     *Finds the getter method of the passed in object based off the javaBean property.
     *Thus myProp would find the method getMyProp of the passed in object.
     */
    public static Method getJavaBeanGetterMethod(Object pObj, String javaBeanProperty){
        //convert the javaBeanProperty to as set method
        char firstChar = Character.toUpperCase(javaBeanProperty.charAt(0));
        String theRest = javaBeanProperty.substring(1);
        String methodName = "get" + firstChar + theRest;
        Method[] methods = pObj.getClass().getMethods();
        for(int i=0;i<methods.length;i++){
            Method method = methods[i];
            if(method.getName().equals(methodName)){
                return method;
            }
        }
        return null;
    }
    /**
     *Finds the getter method of the passed in object based off the javaBean property, if it is a map 
     *it will return the property that is passed in;
     *Thus myProp would find the method getMyProp invoke it and return the map or null.
     */
    public static Map getJavaBeaMap(Object pObj, String javaBeanProperty) throws
    IllegalAccessException, InstantiationException, ClassNotFoundException,InvocationTargetException{
    	Object maybeMap = (Map<String, Object>) getJavaBeanGetterMethod(pObj,javaBeanProperty).invoke(pObj, (new Object [0]));
		if(maybeMap instanceof Map){
			return (Map) maybeMap;
		}
		return null;
    }

    /**
     *will populate the method object with the propertyValue
     *property from this object.
     *@param pObj the object of the method we are invoking
     *@param pMeth the method we are invoking
     *@param pArgument a @see String representation of the argument we will be using
     *@param pDateFormatPattern the date format to use to convert the String argument
     *  to a @see java.util.Data object
     */
    public static void populateJavaBeanSetterMethod(Object pObj,Method pMeth, Object pArgument,String pDateFormatPattern) throws
    IllegalAccessException,IllegalArgumentException,InvocationTargetException{
      populateJavaBeanSetterMethod(pObj,pMeth,pArgument,pDateFormatPattern,false);
    }

    /**
     *will populate the method object with the propertyValue
     *property from this object.
     *@param pObj the object of the method we are invoking
     *@param pMeth the method we are invoking
     *@param pArgument a @see String representation of the argument we will be using
     *@param pDateFormatPattern the date format to use to convert the String argument
     *  to a @see java.util.Data object
     *@param pExcelDataSource wheather data source is excel so we can parse the date correctly
     */
    public static void populateJavaBeanSetterMethod(Object pObj,Method pMeth, Object pArgument,String pDateFormatPattern, boolean pExcelDataSource) throws
    IllegalAccessException,IllegalArgumentException,InvocationTargetException{
        Class lParams = pMeth.getParameterTypes()[0];
       // log.info("populateJavaBeanSetterMethod() === > lParams.getName()=" + lParams.getName());
        Object [] params = new Object [1];
        if (lParams.getName().equals("java.lang.String")){
            String s;
            if(pArgument!=null){
                s = pArgument.toString();
                s = s.trim();
            }else{
                s = "";
            }
            params[0] = s;
            pMeth.invoke(pObj,params);
        } else if((lParams.getName().equals("int")) || (lParams.getName().equals("java.lang.Integer"))){
            if(pArgument instanceof Integer){
                params[0] = pArgument;
            }else{
              String s = (pArgument !=null) ? pArgument.toString() : "" ;	
              BigDecimal b = new BigDecimal(s);
              b.setScale(0, BigDecimal.ROUND_UNNECESSARY);
              params[0] = new Integer(b.intValue());
            }
            pMeth.invoke(pObj,params);
        } else if(lParams.getName().equals("java.math.BigDecimal")){
            if(pArgument instanceof BigDecimal){
                params[0] = pArgument;
            }else{
                String argS = (pArgument != null ) ? pArgument.toString() : "";
                argS = argS.trim();
                argS = replaceString(argS, ",", ""); //remove any commas
                argS = trimLeft(argS,"0"); //remove any left 0s  i.e. 00012 = 12
                if(argS.equals("-")){
                    params[0] = new BigDecimal(0);
                }else{
                    if(argS != null && argS.length() != 0){
                        params[0] = new java.math.BigDecimal(argS);
                    }else{
                        params[0] = new BigDecimal(0);
                    }
                }
            }
            pMeth.invoke(pObj,params);
        } else if (lParams.getName().equals("java.util.Date")){
            if(pArgument instanceof java.util.Date){
                params[0] = pArgument;
            }else{
              if(pExcelDataSource && pArgument instanceof Double){
                params[0] = HSSFDateUtil.getJavaDate(((Double)pArgument).doubleValue());
              }else{
                  SimpleDateFormat f = new SimpleDateFormat(pDateFormatPattern);
                  try{
                      params[0] = f.parse(pArgument.toString());
                  }catch(ParseException e){
                      e.printStackTrace();
                      throw new IllegalArgumentException("could not parse date: " + pArgument);
                  }
              }
            }
            pMeth.invoke(pObj,params);
        } else if(lParams.getName().equals("boolean") || lParams.getName().equals("java.lang.Boolean")){
        	if( pArgument instanceof Boolean){
                params[0] = pArgument;
            }else{
                String s = (pArgument !=null) ? pArgument.toString() : "" ;	
           		params[0] = (Utility.isSet(s)) ? new Boolean(isTrue(s)) : null;
             }
            pMeth.invoke(pObj,params);
        } else if(lParams.getName().equals("java.util.List")){
        	//log.info("populateJavaBeanSetter() == > LIST begin; pArgument=" +pArgument);
            if(pArgument instanceof List){
                  params[0] = pArgument;
            } else if (pArgument instanceof Integer){
          	  List array = new ArrayList();
          	  array.add(((Integer)pArgument).toString());
          	  params[0] = array;
            } else {
            	if (pArgument instanceof Object){
            	  String s = (pArgument !=null) ? ((pArgument instanceof String) ? (String)pArgument : pArgument.toString()) : "" ;	
            	  String[] els = s.split(",");
            	  //log.info("populateJavaBeanSetter() == > LIST (o):" + els );
            	  List array = new ArrayList();
            	  for (int i=0; i<els.length; i++){
            		 array.add(els[i].trim()); 
            	  }
            	  //array.add(pArgument.toString());
            	  params[0] = array;
            	} else {
            	  throw new IllegalArgumentException("illegal arg for type List: " + pArgument);
            	}
            }	
              pMeth.invoke(pObj,params);
        } else {
            throw new IllegalArgumentException("cannot populate method "+pMeth.getName()+" with arg of type: " + lParams.getName());
        }
    }

    /**
     *Parses out a string that is represented as a token seperated list into
     *an array of string.
     *@param prop string to be parsed
     *@param token token
     *@return String[] parsed string
     */
    public static String[] parseStringToArray(String prop, String token){
        StringTokenizer tok = new StringTokenizer(prop,token);
        String[] returnVal=new String[tok.countTokens()];
        int i=0;
        while(tok.hasMoreTokens()){
            returnVal[i]=tok.nextToken();
            i++;
        }
        return returnVal;
    }



    /**
     *  Adds a 2 BigDecimals together safly accounting for null, i.e. a call to
     *this method of: addAmt(null,null) returns a BigDecimal with a 0 value
     *
     *@param  pAmt1  A BigDecimal Amount to add.
     *@param  pAmt2  A BigDecimal Amount to add.
     *@return        The result of the addition.
     */
    public static BigDecimal addAmt(BigDecimal pAmt1, BigDecimal pAmt2) {
        if (null == pAmt1) {
            pAmt1 = new BigDecimal(0);
        }
        if (null == pAmt2) {
            pAmt2 = new BigDecimal(0);
        }
        return pAmt1.add(pAmt2);
    }

    /**
     *  Adds a 2 BigDecimals together "not safly" accounting for null, i.e. a call to
//     * this method of: addAmt(null,null)/addAmt(null,?)addAmt(?,null) returns null
     *
     *@param  pAmt1  A BigDecimal Amount to add.
     *@param  pAmt2  A BigDecimal Amount to add.
     *@return        The result of the addition.
     */
    public static BigDecimal addAmtNullable(BigDecimal pAmt1, BigDecimal pAmt2) {
        if ((null == pAmt1) || (null == pAmt2)) {
            return null;
        }
        return pAmt1.add(pAmt2);
    }

     /**
     *  Subtracts 2 BigDecimals together safly accounting for null, i.e. a call to
     *this method of: addAmt(null,null) returns a BigDecimal with a 0 value
     *
     *@param  pAmt1  A BigDecimal Amount to add.
     *@param  pAmt2  A BigDecimal Amount to add.
     *@return        The result of the addition.
     */
    public static BigDecimal subtractAmt(BigDecimal pAmt1, BigDecimal pAmt2) {
        if (null == pAmt1) {
            pAmt1 = new BigDecimal(0);
        }
        if (null == pAmt2) {
            pAmt2 = new BigDecimal(0);
        }
        return pAmt1.subtract(pAmt2);
    }

    /**
     *Returns the sku number for display to the customer based off the users store type for a given order item
     */
    public static String getActualSkuNumber(String pStoreTypeCd, OrderItemData pOrderItemData){
        //if customer sku is set then use that
        if(Utility.isSet(pOrderItemData.getCustItemSkuNum())){
            return pOrderItemData.getCustItemSkuNum();
        }
        //add customizeations per store type here
        if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(pStoreTypeCd)){
          if(Utility.isSet(pOrderItemData.getDistItemSkuNum())){
              return pOrderItemData.getDistItemSkuNum();
          }
        }
        //default to the standard system sku
        return Integer.toString(pOrderItemData.getItemSkuNum());
    }
    
    /**
     *Returns the uom number for display to the customer based off the users store type for a given order item
     */
    public static String getActualUom(String pStoreTypeCd, OrderItemData pOrderItemData){
        //if customer uom is set then use that
        if(Utility.isSet(pOrderItemData.getCustItemUom())){
            return pOrderItemData.getCustItemUom();
        }
        //add customizeations per store type here
        if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(pStoreTypeCd)){
          if(Utility.isSet(pOrderItemData.getDistItemUom())){
              return pOrderItemData.getDistItemUom();
          }
        }
        //default to the standard system uom
        return pOrderItemData.getItemUom();
    }


    /**
     *Finds the "Best" cost to use for this invoice dist detail item.  Searches the adjusted cost, then the iten cost, then the recieved cost.
     *finally returns 0 if nohing could be found (never null).
     */
    public static BigDecimal getBestCostFromInvoiceDistDetail(InvoiceDistDetailData item, BigDecimal defaultVal){

        if(item.getAdjustedCost() == null){
            if(item.getItemCost() != null){
                return item.getItemCost();
            }else if(item.getItemReceivedCost() != null){
                return item.getItemReceivedCost();
            }else{
                return defaultVal;
            }
        }else{
            return item.getAdjustedCost();
        }

    }

    /**
     *Finds the "Best" cost to use for this invoice dist detail item.  Searches the adjusted cost, then the iten cost, then the recieved cost.
     *finally returns 0 if nohing could be found (never null).
     */
    public static BigDecimal getBestCostFromInvoiceDistDetail(InvoiceDistDetailData item){
        return getBestCostFromInvoiceDistDetail(item, new BigDecimal(0.00));
    }

    private static InitialContext ctx = null;
    public static InitialContext getEjbNamingContext()
    throws javax.naming.NamingException
    {
      if(ctx!=null) {
        return ctx;
      }
      Hashtable environment = new Hashtable();
      String ncf = System.getProperty("cleanwise.java.naming.factory.initial");
      if(ncf==null) {
      } else {
        environment.put("java.naming.factory.initial", ncf);
      }
      String nfu = System.getProperty("cleanwise.java.naming.factory.url.pkgs");
      if(nfu==null) {
      } else {
        environment.put("java.naming.factory.url.pkgs", nfu);
      }
      String url = System.getProperty("cleanwise.java.naming.provider.url"); //with port number
      if(url==null) {
      } else {
        environment.put("java.naming.provider.url", "jnp://"+url);
      }
        //environment.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        //environment.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        //environment.put(Context.PROVIDER_URL, "jnp://192.168.0.2:1099");

      InitialContext ctx = new InitialContext(environment);
      return ctx;
    }

    /**
     *Nomalizes an entered date acounting for a 2 digit year.  That is if the date entered is 01/01/05 the returned date will be 01/01/2005
     *01/01/99 will returrn 01/01/1999
     */
    public static Date normalizeDate(Date pDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(pDate);
        int year = cal.get(Calendar.YEAR);
        if(year < 100){
            if(year > 70){
                year = year + 1900;
            }else{
                year = year + 2000;
            }
        }
        cal.set(Calendar.YEAR,year);
        return cal.getTime();
    }
    
    public static Date combineDateAndTime(Date pDate, Date pTime){
        Calendar cDate = Calendar.getInstance();
        Calendar cTime = Calendar.getInstance();
        cDate.setTime(pDate);
        cTime.setTime(pTime);

        cDate.set(Calendar.SECOND, cTime.get(Calendar.SECOND));
        cDate.set(Calendar.MINUTE, cTime.get(Calendar.MINUTE));
        cDate.set(Calendar.HOUR_OF_DAY, cTime.get(Calendar.HOUR_OF_DAY));
        return cDate.getTime();
    }

    /**
     * Returns true if the sale type code in this order item record indicates that it is tax exempt.
     */
    public static boolean isTaxExemptOrderItem(OrderItemData pOrderItemData) {
        return pOrderItemData == null || Utility.isTrue(pOrderItemData.getTaxExempt());
    }

    public static boolean isTaxableOrderItem(OrderItemData pOrderItemData) {
        return
                !isTaxExemptOrderItem(pOrderItemData)
                        &&!isTaxExemptItemSaleTypeCd( pOrderItemData);
    }

    /**
     *Returns true if the sale type indicates that it is tax exempt.
     */
    public static boolean isTaxExemptItemSaleTypeCd(String pSaleTypeCd){
         if(RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(pSaleTypeCd)){
             return true;
         }
         return false;
    }

    public static boolean isTaxExemptItemSaleTypeCd(OrderItemData oid) {
      return oid == null || isTaxExemptItemSaleTypeCd(oid.getSaleTypeCd());
    }

    /**
     *For a US postal code this means just returning the first 5 digits
     */
    public static String normalizePostalCode(String pPostalCode){
        if(pPostalCode!= null && pPostalCode.length() > 5){
            pPostalCode = pPostalCode.substring(0,5);
        }
        return pPostalCode;
    }

    public static int pickOutFreightHandler(OrderItemDataVector v) {

    int fhid = -1;
    for ( int fh_i = 0; null != v && fh_i < v.size(); fh_i++ ) {
        fhid = ((OrderItemData)v.get(fh_i)).getFreightHandlerId();
        if (fhid > 0 ) {
        // we found a non-zero value, assume this is the
        // freight handler used.
        return fhid;
        }
    }

    // no freight handler found in this group of items.
    return 0;

    }

    public static String getUiErrorMess(String pErrorMess) {
      if(pErrorMess==null) return null;
      int ind1 = pErrorMess.indexOf("^clw^");
      if(ind1<0) return null;
      ind1 += 5;
      int ind2 = pErrorMess.indexOf("^clw^",ind1);
      if(ind2<0) {
        return pErrorMess.substring(ind1);
      }
      return pErrorMess.substring(ind1,ind2);

    }



    /**
     * Safely retrieves a value from a list checking for nulls on the array and that the
     * array is sized properly.  If the List is null, or an index is requeted outside the
     * size of the List the defRetVal is returned.  This value may of course be null, in which
     * case null is returned.  If the List contains a null value at the index specified
     * null will be returned.
     * @param pList the list to retrieve the specied index from.
     * @param index the index in the list to retieve
     * @param defRetVal the value to return if the index is outside the length of the list
     * @return an object from the list o
     */
    public static Object safeGetListElement(List pList, int index, Object defRetVal){
        if(pList == null){
            return defRetVal;
        }
        if(index < pList.size()){
            return pList.get(index);
        }
        return defRetVal;
    }


    public static String pickOutNumerics(String pInString) {
    String numstrg = new String();

    for (int i = 0; i < pInString.length(); i++) {
            if ( pInString.charAt(i)>='0' &&
             pInString.charAt(i)<='9') {
        numstrg += pInString.substring(i,i+1);
        }
    }
    return numstrg;
    }

 public static String toOracleCommaString(List pList, boolean pLeftPad, char pChar, int pLength) {
    StringBuffer buf = new StringBuffer();
        if(pList==null || pList.size()==0) {
          return "";
        }
    boolean firstFl = true;
    for(Iterator iter=pList.iterator(); iter.hasNext();) {
      Object objVal = iter.next();
      if(objVal==null) continue;
      String val = objVal.toString();
      if(firstFl) {
        firstFl = false;
        buf.append("'");
      } else {
        buf.append("','");
      }
      int len = val.length();
      if(val.length()<pLength) {
        int padLen = pLength-len;
        if(pLeftPad) {
          for(int ii=0; ii<padLen; ii++) {
            buf.append(pChar);
          }
        }
        buf.append(val);
        if(!pLeftPad) {
          for(int ii=0; ii<padLen; ii++) {
            buf.append(pChar);
          }
        }
      } else {
        buf.append(val);
      }
    }
    buf.append("'");
    return buf.toString();
  }

    public static String makeSSCC(String pVal1, String pVal2) {

    String f = pVal1 + pVal2;
    //f = "10614141192837465";
    while ( f.length() < 17 ) { f = "0" + f; }

    int evenTotal = 0;
    int i = 1;
    while ( i < 17 ) {
        evenTotal += Integer.parseInt(f.substring(i,i+1));
        i = i + 2;
    }
    int oddTotal = 0;
    i = 0;
    while ( i <= 16 ) {
        oddTotal += Integer.parseInt(f.substring(i,i+1));
        i = i + 2;
    }
    int finalTotal = evenTotal + (oddTotal*3);
    String cs = String.valueOf(finalTotal);
    int ci = 0;
    if ( cs.length() > 2 ) {
        ci = Integer.parseInt(cs.substring(cs.length() - 1, cs.length() ));
    } else {
        ci = Integer.parseInt(cs);
    }

    while ( ci > 9 ) { ci = ci - 10; }
    if ( ci > 0 ) {
        ci = 10 - ci;
    }

    return f + ci;
    }


    /**
     * Retrieves the proper outbound po number to use based off the store type and trading partner configurations.
     * Possible return values are the customer po number, the erpPoNum, and the erpPoNum/erpOrderNum for example
     * @depricated  was replaced on getDefaultOutboundPONumber
     */
    public static String getOutboundPONumber(String storeTypeCd, OrderData order, TradingPartnerData partner, String erpPoNum){
      return getOutboundPONumber(storeTypeCd,order, partner, erpPoNum,false);
    }

    /**
     * Retrieves the proper outbound po number to use based off the store type and trading partner configurations.
     * Possible return values are the customer po number, the erpPoNum, and the erpPoNum/erpOrderNum for example.
     * Bassed off the trimPound param will optionally trim off the begining system added '#' sign.  Has no affect
     * on customer entered po numbers.
     * @depricated  was replaced on getDefaultOutboundPONumber
     */
    public static String getOutboundPONumber(String storeTypeCd, OrderData order, TradingPartnerData partner, String erpPoNum, boolean trimPound){
      if(trimPound && erpPoNum != null && erpPoNum.startsWith("#")){
        erpPoNum = erpPoNum.substring(1);
      }
      if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeTypeCd)){
            String seg1;
            String po;
            if(!"N/A".equals(order.getRequestPoNum()) && Utility.isSet(order.getRequestPoNum())){
                po = order.getRequestPoNum();
            }else{
                po = erpPoNum;
            }
            if (order.getRefOrderNum() != null && !order.getRefOrderNum().equals("")){
                seg1 = po + "/" + order.getRefOrderNum();
            }else{
                seg1 = po;
            }
            return seg1;

        }else{
            if(RefCodeNames.PO_NUMBER_RENDERING_TYPE_CD.CONCAT_WITH_ERP_ORDER.equals(partner.getPoNumberType())){
                return erpPoNum + "/" + order.getErpOrderNum();
            }else{
                return erpPoNum;
            }
        }
    }

    /**
     * Retrieves the proper outbound po number to use based off the store type and trading partner configurations.
     * Possible return values are the customer po number, the erpPoNum, and the erpPoNum/erpOrderNum for example
     */
    public static String getDefaultOutboundPONumber(String storeTypeCd, OrderData order, TradingPartnerData partner, String erpPoNum){
        return getDefaultOutboundPONumber(storeTypeCd,order, partner, erpPoNum,false);
    }

    /**
     * Retrieves the proper outbound po number to use based off the store type and trading partner configurations.
     * Possible return values are the customer po number, the erpPoNum, and the erpPoNum/erpOrderNum for example.
     * Bassed off the trimPound param will optionally trim off the begining system added '#' sign.  Has no affect
     * on customer entered po numbers.
     */
    public static String getDefaultOutboundPONumber(String storeTypeCd, OrderData order, TradingPartnerData partner, String erpPoNum, boolean trimPound){
        return getDefaultOutboundPONumber(storeTypeCd,
                partner.getPoNumberType(),
                order.getRequestPoNum(),
                order.getRefOrderNum(),
                String.valueOf(order.getErpOrderNum()),
                erpPoNum,trimPound);
    }

    private static String getDefaultOutboundPONumber(String storeTypeCd,
                                                     String poNumberType,
                                                     String requestPoNum,
                                                     String refOrderNum,
                                                     String erpOrderNum,
                                                     String erpPoNum,
                                                     boolean trimPound) {

        if(trimPound && erpPoNum != null && erpPoNum.startsWith("#")){
            erpPoNum = erpPoNum.substring(1);
        }
        if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeTypeCd)){
            String seg1;
            String po;
            if(!"N/A".equals(requestPoNum) && Utility.isSet(requestPoNum)){
                po = requestPoNum;
            }else{
                po = erpPoNum;
            }
            if (refOrderNum != null && !refOrderNum.equals("")){
                seg1 = po + "/" + refOrderNum;
            }else{
                seg1 = po;
            }
            return seg1;

        }else{
            if(RefCodeNames.PO_NUMBER_RENDERING_TYPE_CD.CONCAT_WITH_ERP_ORDER.equals(poNumberType)){
                return erpPoNum + "/" + erpOrderNum;
            }else{
                return erpPoNum;
            }
        }
    }

    public static String createI18nErrorMess (String key, Object[] params ) {
        String retStr = "^clwKey^"+key+"^clwKey^";
        if(params!=null) {
            for(int ii=0; ii<params.length;ii++) {
                retStr+="^clwParam^"+params[ii]+"^clwParam^";
            }
        }
        return retStr;
    }

    public static IdVector parseIdStringToVector(String prop, String token){
      IdVector idsV = new IdVector();
      StringTokenizer tok = new StringTokenizer(prop,token);
      while(tok.hasMoreTokens()){
        String sId =tok.nextToken().trim();
        try{
          int id = Integer.parseInt(sId);
          idsV.add(new Integer(id));
        } catch(Exception exc) {}
      }
      return idsV;
    }

    public static IdVector parseIdStringToVector(String prop, String token, boolean throwExc) throws Exception {
        IdVector ids = new IdVector();
        StringTokenizer tok = new StringTokenizer(prop, token);
        while (tok.hasMoreTokens()) {
            try {
                ids.add(Integer.parseInt(tok.nextToken().trim()));
            } catch (Exception exc) {
                if (throwExc) {
                    throw exc;
                }
            }
        }
        return ids;
    }

    public static List<String> parseStringToList(String prop, String token){
        List<String> result = new ArrayList<String>();
        StringTokenizer tok = new StringTokenizer(prop,token);
        while(tok.hasMoreTokens()){
          String s = tok.nextToken().trim();
          result.add(s);          
        }
        return result;
      }

     public static  String getFormatTime(long mils,int format) {
       SimpleDateFormat sdf=null;
       if      (format==1)  sdf = new SimpleDateFormat("MM/dd/yyyy");
       else if (format==2)  sdf = new SimpleDateFormat("HH:mm:ss");
       else if(format==3||format==4);
       else               sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

       NumberFormat nf= NumberFormat.getInstance();
       //nf.setMaximumIntegerDigits(3);
       nf.setMinimumIntegerDigits(3);

       if(format==4) return nf.format(mils);

       String timeS = "";
       Date time = new Date(mils);
       long n=(long)1E+3;

       if(sdf!=null) timeS=sdf.format(time);
       String milsS = (format!=1) ? (nf.format(mils - ((mils / n) * 1E+3))) : "";

       if(format==3) return milsS;

       String total=timeS+(format!=1?":"+milsS:milsS);
       return total ;
    }

    public static String addCharsToString(String str,char c,int strlen)
    {

        int length=str.length();
        char[] charArray;
        int quantityChar = strlen - length;
        String  resultString=null;
        if(quantityChar>0)
        {
            charArray=new char[strlen];
            str.getChars(0,length,charArray,quantityChar);


            for(int i=0;i<quantityChar;i++)
            {
                charArray[i]=c;
            }
            resultString=String.valueOf(charArray);
        }
        else
        {

            resultString=new String();
            resultString=resultString.concat(str);
        }
        return resultString;
    }

    public static String getConcatonatedIdentifier(String accountIdentifier, String siteIdentifier,
                                                   TradingPropertyMapDataVector tradingPropertyMapDataVector,
                                                   String ediTypeCd, String direction)     throws Exception{

        PairView paddingPair=getIdentifierPaddingPairOfTradingPartner(tradingPropertyMapDataVector,ediTypeCd,direction);

            if(paddingPair== null)  {

                return accountIdentifier+siteIdentifier;
            }
            int paddingTotalLength=((Integer)paddingPair.getObject2()).intValue();

            if(paddingTotalLength>0) {
                if(accountIdentifier.length()>paddingTotalLength)
                {
                    throw  new Exception("Account Identifier length greater  <PADDING_TOTAL_LENGTH> values of trading profile" );
                }
                if(siteIdentifier.length()>paddingTotalLength)
                {
                    throw  new Exception("Site Identifier length greater  <PADDING_TOTAL_LENGTH> values of trading profile" );
                }
                char []arraych=((String)paddingPair.getObject1()).toCharArray();
                if(arraych.length!=1)
                {
                    throw  new Exception("Invalid symbol <PADDING_CHAR> of trading profile" );
                }

                String concatonatedIdentifier=addCharsToString(accountIdentifier,arraych[0],paddingTotalLength)
                                             +addCharsToString(siteIdentifier,arraych[0],paddingTotalLength);

                if(concatonatedIdentifier.length()!=(paddingTotalLength*2))
                {
                    throw  new Exception("Verification of Identifiers concatenation is failed." );
                }

                return concatonatedIdentifier;
            }
            else
            {
                return accountIdentifier+siteIdentifier;
            }
    }

    public static PairView getIdentifierPaddingPairOfTradingPartner
            (TradingPropertyMapDataVector tradingPropertyMapDataVector,
             String ediTypeCd, String direction) throws  Exception {

        String paddingTotalLength=null;
        String paddingChar=null;
        if(tradingPropertyMapDataVector !=null&&tradingPropertyMapDataVector.size()>0)
        {

            Iterator it= tradingPropertyMapDataVector.iterator();
            while(it.hasNext())
            {
                TradingPropertyMapData propertyMap = (TradingPropertyMapData) it.next();

                if(RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY.equals(propertyMap.getTradingPropertyMapCode())
                        &&RefCodeNames.ENTITY_PROPERTY_TYPE.PADDING_TOTAL_LENGTH.equals(propertyMap.getEntityProperty())
                        &&ediTypeCd.equals(propertyMap.getSetType())&&direction.equals(propertyMap.getDirection()))
                {
                    if(paddingTotalLength==null)
                    {
                        paddingTotalLength=propertyMap.getHardValue();
                    }
                    else
                    {
                        throw  new Exception("Multiple properties <PADDING_TOTAL_LENGTH> of trading profile" );
                    }

                }
                if(RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY.equals(propertyMap.getTradingPropertyMapCode())
                        &&RefCodeNames.ENTITY_PROPERTY_TYPE.PADDING_CHAR.equals(propertyMap.getEntityProperty())
                        &&ediTypeCd.equals(propertyMap.getSetType())&&direction.equals(propertyMap.getDirection()))
                {
                    if(paddingChar==null)
                    {
                        paddingChar=propertyMap.getHardValue()!=null?propertyMap.getHardValue():"NULL";
                    }
                    else
                    {
                        throw  new Exception("Multiple properties <PADDING_CHAR> of trading profile" );
                    }
                }
            }

        }

        if(paddingTotalLength!=null && paddingChar!=null)
        {
            try {

                Integer paddingTotalLengthInt = new Integer(paddingTotalLength);
                return new PairView(paddingChar.equals("NULL")?" ":paddingChar,paddingTotalLengthInt);

            } catch (NumberFormatException e) {

                throw  new Exception("Invalid number <PADDING_TOTAL_LENGTH> of trading profile" );

            }
        }
        else if(paddingTotalLength!=null&&paddingChar==null)
        {
            throw new Exception("PADDING_CHAR not found");
        }
        else if(paddingChar!=null&&paddingTotalLength==null)
        {
            throw new Exception("PADDING_TOTAL_LENGTH not found");
        }

      return null;

    }

    public static String getAsString(List list) {
        String result = null;
        if (list != null) {
            Iterator it = list.iterator();
            int i = 0;
            result = "";
            while (it.hasNext()) {
                if (i != 0) result = result.concat(",");
                result = result.concat(it.next().toString());
                i++;
            }
        }
        return result;
    }

    public static String getIdVectorAsString(IdVector ids) {
        return getAsString(ids);
    }

//	public static int getDistInventory(final String locationCode,
//			final String distSkuNum, final String distErpNum) {
//		return -1;
//	}
//
//	public static int getDistInventory(final String locationCode,
//			final String distSkuNum, final int distId) {
//		return -1;
//	}
  public static int getDistInventory(String locationCode, String distSkuNum,
      final DistributorData distributorData) {
    HashMap map = new HashMap();
    String companyCode = "";
    String inventoryURLFull = "https://webservices.xpedx.com/gp/eConnectWS.asmx/getQuantity";
    final String inventoryURLHost = "https://webservices.xpedx.com";
    final String inventoryURLMethod = "getQuantity";
    String user = "xpedxwebservice";
    String password = "Xp3dX!!!";
    if (distributorData != null) {
      companyCode = distributorData.getExchangeCompanyCode();
      inventoryURLFull = distributorData.getExchangeInventoryURL();
      user = distributorData.getExchangeUser();
      password = distributorData.getExchangePassword();
    }
    // distSkuNum = "1138419"; //!!!! debugging !!!!!!!!!!!!!!!!!!!!!!!!!
    map.put("sItem", distSkuNum);
    map.put("Company", companyCode);
    String retXml = "";
    try {
      retXml = SOAPTransferClient.invoke(inventoryURLFull,
          inventoryURLHost, inventoryURLMethod, user,
          password, map);
            Document theXmlDoc = DocumentHelper.parseText(retXml);
            Element stringE = theXmlDoc.getRootElement();
            Element rootE = getChild(stringE,"root");
            Element eConnectE = getChild(rootE,"eConnect");
            Element itemE = getChild(eConnectE,"Item");
            for(Iterator iter = itemE.elementIterator("Quantities"); iter.hasNext();) {
                Element quantitiesE = (Element) iter.next();
                Element itemNumE = getChild(quantitiesE,"ITEMNMBR");
                if(itemNumE!=null && distSkuNum.equals(itemNumE.getText())){
                    Element locCodeE = getChild(quantitiesE,"LOCNCODE");
                    if(locCodeE!=null &&
                        locationCode.equalsIgnoreCase(locCodeE.getText())) {
                        Element qtyE = getChild(quantitiesE,"QTYONHND");
                        if(qtyE!=null) {
                            String qtyS = qtyE.getText();
                            if(qtyS!=null) {
                                try {
                                    double qtyDb = Double.parseDouble(qtyS);
                                    BigDecimal qtyBD = new BigDecimal(qtyDb);
                                    int qty = qtyBD.intValue();
                                    return qty;
                                } catch (Exception exc){}
                            }
                        }
                    }
                }
            }

        } catch(Exception exc) {
            exc.printStackTrace();
        }
        return -1;
    }

    private static Element getChild(Element pElem, String pChildName) {
        for(Iterator iter = pElem.elementIterator(pChildName); iter.hasNext();) {
            Element elem = (Element) iter.next();
            return elem;
        }
        return null;
    }

    public static boolean isFail(boolean b) {
        return !b;
    }

    public static boolean isInventoryOrder(String sourceCd) {
        return RefCodeNames.ORDER_SOURCE_CD.INVENTORY.equals(sourceCd);
    }

    public static boolean isToBeConsolidated(String orderTypeCd) {
        return RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(orderTypeCd);
    }

    private static Map coeffMap = new HashMap();
    static {
      coeffMap.put(RefCodeNames.WEIGHT_UNIT.POUND + RefCodeNames.WEIGHT_UNIT.OUNCE, new Double(16.00018144d));
      coeffMap.put(RefCodeNames.WEIGHT_UNIT.POUND + RefCodeNames.WEIGHT_UNIT.KILOGRAMME, new Double(0.4535970244d));
      coeffMap.put(RefCodeNames.WEIGHT_UNIT.POUND + RefCodeNames.WEIGHT_UNIT.GRAMME, new Double(453.5970244d));

      coeffMap.put(RefCodeNames.WEIGHT_UNIT.OUNCE + RefCodeNames.WEIGHT_UNIT.POUND, new Double(0.06249929d));
      coeffMap.put(RefCodeNames.WEIGHT_UNIT.OUNCE + RefCodeNames.WEIGHT_UNIT.KILOGRAMME, new Double(0.02834949254d));
      coeffMap.put(RefCodeNames.WEIGHT_UNIT.OUNCE + RefCodeNames.WEIGHT_UNIT.GRAMME, new Double(28.34949254));

      coeffMap.put(RefCodeNames.WEIGHT_UNIT.KILOGRAMME + RefCodeNames.WEIGHT_UNIT.POUND, new Double(2.2046d));
      coeffMap.put(RefCodeNames.WEIGHT_UNIT.KILOGRAMME + RefCodeNames.WEIGHT_UNIT.OUNCE, new Double(35.274d));
      coeffMap.put(RefCodeNames.WEIGHT_UNIT.KILOGRAMME + RefCodeNames.WEIGHT_UNIT.GRAMME, new Double(1000d));

      coeffMap.put(RefCodeNames.WEIGHT_UNIT.GRAMME + RefCodeNames.WEIGHT_UNIT.POUND, new Double(0.0022046d));
      coeffMap.put(RefCodeNames.WEIGHT_UNIT.GRAMME + RefCodeNames.WEIGHT_UNIT.OUNCE, new Double(0.035274d));
      coeffMap.put(RefCodeNames.WEIGHT_UNIT.GRAMME + RefCodeNames.WEIGHT_UNIT.KILOGRAMME, new Double(0.001d));
    }

    public static BigDecimal getWeight(BigDecimal pWeight, String pInUnit, String pOutUnit) {
      if (pWeight == null) {
        return new BigDecimal(0);
      }
      if ( pInUnit.equals(pOutUnit) ) {
        return pWeight;
      }
      BigDecimal result = pWeight;
      String key = pInUnit + pOutUnit;
      if (coeffMap.containsKey(key)) {
        try {
          double coeff = ((Double)coeffMap.get(key)).doubleValue();
          result = pWeight.multiply(new BigDecimal(coeff));
        } catch (Exception e) {
        }
      }
      return result;
    }


    public static boolean isInArray(int pValue, int[] arrayToCheck) {
        if(arrayToCheck==null) {
            return false;
        }
        for(int ii=0; ii<arrayToCheck.length; ii++) {
            if(pValue==arrayToCheck[ii]) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInArray(String pValue, String[] arrayToCheck) {
        if(arrayToCheck==null || pValue==null) {
            return false;
        }
        for(int ii=0; ii<arrayToCheck.length; ii++) {
            if(pValue.equals(arrayToCheck[ii])) {
                return true;
            }
        }
        return false;
    }

    public static HashMap<Integer, Integer> getParValueMap(InventoryLevelDetailDataVector pValues) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        if (pValues != null) {
            for (Object oParValueData : pValues) {
                InventoryLevelDetailData parValueData = (InventoryLevelDetailData) oParValueData;
                map.put(parValueData.getPeriod(), parValueData.getValue());
            }
        }
        return map;
    }

    public static HashMap<Integer, SiteInventoryInfoView> toMapByItemId(SiteInventoryInfoViewVector siteInventoryInfoVV) {
        HashMap<Integer, SiteInventoryInfoView> invInfoItemsMap = new HashMap<Integer, SiteInventoryInfoView>();
        if (siteInventoryInfoVV != null) {
            Iterator it = siteInventoryInfoVV.iterator();
            while (it.hasNext()) {
                SiteInventoryInfoView invInfo = (SiteInventoryInfoView) it.next();
                invInfoItemsMap.put(invInfo.getItemId(), invInfo);
            }
        }
        return invInfoItemsMap;
    }

    public static HashMap<Integer, ShoppingCartItemData> toMapByItemId(ShoppingCartItemDataVector itemList) {
        HashMap<Integer, ShoppingCartItemData> map = new HashMap<Integer, ShoppingCartItemData>();
        if (itemList != null) {
            Iterator it = itemList.iterator();
            while (it.hasNext()) {
                ShoppingCartItemData item = (ShoppingCartItemData) it.next();
                map.put(new Integer(item.getItemId()), item);
            }
        }
        return map;
    }

    public static String getOutboundPoNum(String storeType,
                                          String distrPoType,
                                          String partnerPoType,
                                          String erpPoNum,
                                          String erpPoSuffix,
                                          String customerPoNum,
                                          String requestPoNum,
                                          String refOrderNum,
                                          String erpOrderNum) {

        String poOut=null;
        if (RefCodeNames.DISTR_PO_TYPE.REQUEST.equals(distrPoType)) {
            if (isSet(requestPoNum)) {
                poOut = requestPoNum;
            }
        } else if (RefCodeNames.DISTR_PO_TYPE.CUSTOMER.equals(distrPoType)) {
        	if(!isSet(customerPoNum)){
        		customerPoNum = requestPoNum;
        	}
            if (isSet(customerPoNum) && isSet(erpPoSuffix)) {
                poOut = customerPoNum + "." + erpPoSuffix;
            }
        } else {
            poOut = getDefaultOutboundPONumber(storeType,partnerPoType,requestPoNum,refOrderNum,erpOrderNum,erpPoNum,false);
        }

        return  poOut;
    }

    public static String analyseOutboundPONumProblem(String storeType,
                                                     String distrPoType,
                                                     String partnerPoType,
                                                     String erpPoNum,
                                                     String erpPoSuffix,
                                                     String customerPoNum,
                                                     String requestPoNum,
                                                     String refOrderNum,
                                                     String erpOrderNum){

        StringBuffer problems=new StringBuffer();
        if (RefCodeNames.DISTR_PO_TYPE.REQUEST.equals(distrPoType)) {
            if (!isSet(requestPoNum)) {
                String mess="Request PO Num is not set.DISTR_PO_TYPE => "+distrPoType+".";
                problems.append(mess);
            }
        } else if (RefCodeNames.DISTR_PO_TYPE.CUSTOMER.equals(distrPoType)) {
            if (!isSet(customerPoNum)) {
                String mess="Customer PO Num is not set.DISTR_PO_TYPE => "+distrPoType+".";
                problems.append(mess);
            }
            if (!isSet(erpPoSuffix)) {
                String mess="PO Suffix is not set.DISTR_PO_TYPE => "+distrPoType+".";
                problems.append(mess);
            }

        } else {
            if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeType)){
                if(("N/A".equals(requestPoNum) || !isSet(requestPoNum))){
                    if (!isSet(erpPoNum)){
                        String mess="PO Num is not set.DISTR_PO_TYPE => "+distrPoType+".STORE_TYPE_CD => "+storeType;
                        problems.append(mess);
                    }
                }
            }else{
                if(RefCodeNames.PO_NUMBER_RENDERING_TYPE_CD.CONCAT_WITH_ERP_ORDER.equals(partnerPoType)){
                    if (!isSet(erpPoNum)){
                        String mess="PO Num is not set.PO_NUMBER_RENDERING_TYPE_CD => "+partnerPoType+".";
                        problems.append(mess);
                    }
                    if (!isSet(erpOrderNum)){
                        String mess="Order Num is not set.PO_NUMBER_RENDERING_TYPE_CD => "+partnerPoType+".";
                        problems.append(mess);
                    }
                }else{
                    if (!isSet(erpPoNum)){
                        String mess="PO Num is not set.PO_NUMBER_RENDERING_TYPE_CD => "+partnerPoType+".";
                        problems.append(mess);
                    }
                }
            }
        }
        return problems.toString();
    }

    public static String getErpPoSuffix(String erpPoNum){
        int index = (erpPoNum == null) ? -1 : erpPoNum.lastIndexOf("-");
        if(index!=-1) {
            return erpPoNum.substring(index + 1);
        } else{
            return "";
        }
    }

    public static IdVector toIdVector(int id) {
        IdVector idv = new IdVector();
        idv.add(new Integer(id));
        return idv;
    }

    public static boolean refCdDataVectorContains(RefCdDataVector v, String value) {
        if (v == null || v.size() == 0 || !Utility.isSet(value)) {
            return false;
        }
        Iterator i = v.iterator();
        while (i.hasNext()) {
          RefCdData refD = (RefCdData)i.next();
          if (refD.getValue().equals(value)) {
              return true;
          }
        }
        return false;
    }

    public static Date calculateDate(int year, int weekOfYear, int dayOfWeek) {
      Date deliveryDate = new Date();
      DateFormat df  = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
      GregorianCalendar cal = new GregorianCalendar(year, 0 ,1);

      cal.add(Calendar.YEAR, year - cal.get(Calendar.YEAR));
      cal.add(Calendar.WEEK_OF_YEAR, weekOfYear - cal.get(Calendar.WEEK_OF_YEAR) );
      cal.add(Calendar.DAY_OF_WEEK, dayOfWeek - cal.get(Calendar.DAY_OF_WEEK));

      String sDate = (cal.get(Calendar.MONTH) +1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR);
      try
      {
          deliveryDate = df.parse(sDate);
      } catch (ParseException e)
      {
          e.printStackTrace();
      }
      return deliveryDate;
    }

    public static Date getDateTime(Date date, Date time) {
      if (date == null || time == null) {
        return date;
      }

      Date  dt =  new Date();
      DateFormat dtf  = new SimpleDateFormat(Constants.SIMPLE_DATETIME_PATTERN);
      DateFormat df  = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
      DateFormat tf  = new SimpleDateFormat(Constants.SIMPLE_TIME_PATTERN);
      try
      {
         dt = dtf.parse(  df.format(date)+ " " + tf.format(time)) ;
      } catch (ParseException e)
      {
          e.printStackTrace();
      }
      return dt;
    }

    public static String convertFullDate2Short(String pDate) {
      String dateS = "";
      try
      {
        if (pDate != null) {
          DateFormat dfFull = DateFormat.getDateInstance(DateFormat.FULL);

          Date dt = dfFull.parse(pDate);
          SimpleDateFormat dfShort = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
          dateS = dfShort.format(dt);
        }
      } catch (ParseException e)
      {
        e.printStackTrace();
      }
      return dateS;
    }

    public static TimeZone getTimeZoneFromUserData(ValueObject dataObj) {
      String timeZoneID = null;
      BusEntityData busEntity = null;
      BusEntityData accountBusEntity = null;

      if (dataObj != null) {
        if (dataObj instanceof SiteData ) {
          busEntity = ((SiteData)dataObj).getBusEntity();
          accountBusEntity = ((SiteData)dataObj).getAccountBusEntity();
        }
        if (busEntity != null && busEntity.getTimeZoneCd() != null) {
            timeZoneID = busEntity.getTimeZoneCd();

        } else if (accountBusEntity != null && accountBusEntity.getTimeZoneCd() != null){
            timeZoneID = accountBusEntity.getTimeZoneCd();
        } else {
          timeZoneID = TimeZone.getDefault().getID();//"Asia/Shanghai" ;
        }
      } else { //If DataObject is null;
    	  timeZoneID = TimeZone.getDefault().getID();//"Asia/Shanghai" ;
      }

      return TimeZone.getTimeZone(timeZoneID) ;
    }


    public static IdVector getProcessIdOnly(LoggingDataVector eventLogs) {
        IdVector ids = new IdVector();
        if (eventLogs != null) {
            for (Object oEventLog : eventLogs) {
                Integer processId = ((LoggingData) oEventLog).getProcessId();
                if (processId >= 0 && !ids.contains(processId)) {
                    ids.add(processId);
                }
            }
        }
        return ids;
    }


    public static List filter(List pList, IdVector pFilterIds) {

        if (pFilterIds == null || pFilterIds.isEmpty() || pList == null || pList.isEmpty()) {
            return pList;
        }

        HashSet filterSet = new HashSet(pFilterIds);

        Iterator it = pList.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj != null) {
                if (!filterSet.contains(getId(obj))) {
                    it.remove();
                }
            }
        }

        return pList;
    }

    public static IdVector toIdVector(Integer... ids) {
        IdVector idv = new IdVector();
        if (ids != null) {
            for (Integer id : ids)
                if (id != null) {
                    idv.add(id);
                }
        }
        return idv;
    }

    public static IdVector toIdVector(int[] ids) {
        IdVector idVector = new IdVector();
        if (ids != null) {
            for (int i = 0; i < ids.length; i++) {
                idVector.add(new Integer(ids[i]));
            }
        }
        return idVector;
    }

    public static boolean isEqual(String str1, String str2){
		if (!isSet(str1)){
			if (!isSet(str2))
				return true;
			else return false;
		}else{
			return str1.equals(str2);
		}
	}

    public static int compareBigDecimal(BigDecimal a, BigDecimal b){
    	if(a.subtract(b).abs().compareTo(new BigDecimal(.00001))>=0){
    		return 1;
    	}else{
    		return 0;
    	}
    }

    public static int compareTo(String str1, String str2){
		if (str1 == null)
			str1 = "";
		if (str2 == null)
			str2 = "";

		return str1.compareTo(str2);
	}
    public static int compareToIgnoreCase(String str1, String str2){
		if (str1 == null)
			str1 = "";
		if (str2 == null)
			str2 = "";

		return str1.compareToIgnoreCase(str2);
	}

    public static void parseColumnNameAndWidth(GenericReportColumnView column,
            String sqlName) {
        String name = sqlName;
        String width = "";
        int index1 = 0;
        int index2 = 0;
        if (name != null) {
            index1 = name.indexOf('@');
            if (index1 > 0) {
                index2 = name.indexOf('@', index1 + 1);
                if (index1 > 0 && index2 > 0) {
                    width = name.substring(index1 + 1, index2);
                    name = name.substring(0, index1)
                            + name.substring(index2 + 1);
                }
            }
        }
        column.setColumnName(name);
        column.setColumnWidth(width);
    }

    public static FileGenerator getFileGenerator(String className) throws Exception {
          FileGenerator generator = null;
          Class c = null;
          if (Utility.isSet(className)) {
            c = Class.forName(className);
            generator = (FileGenerator) c.newInstance();
          }
           return generator;

     }
     public static FileAttach[] getFileAttachment(File tmp) {
        File[] attachments;
         attachments = new File[]{tmp};

         FileAttach[] fileAttach = null;

        try {
          if (attachments != null) {
            fileAttach = (new ApplicationsEmailTool()).fromFilesToAttachs(attachments);
          }
          else {
            fileAttach = new FileAttach[0];
          }
        }
        catch (IOException e) {
          e.printStackTrace();
        }
         return fileAttach;
     }


     public static File createTempFileAttachment(){
         File tmp = null;

         try {
           tmp = File.createTempFile("Attachment", ".txt");
         }
         catch (IOException e) {
           e.printStackTrace();
         }
         return tmp;
     }

 /*    public static File createTempFileAttachment(String fileExtension){
         File tmp = null;
         try {
           tmp = File.createTempFile("Attachment", fileExtension);
         }
         catch (IOException e) {
           e.printStackTrace();
         }
         return tmp;
     }
*/
     public static File createTempFileAttachment(String fileName){
         File tmp = null;
         int ind = fileName.lastIndexOf(".");
         String prefix = (ind >0 ) ? fileName.substring(0,ind) : "Attachment" ;
         String suffix = (ind >=0 && fileName.length()>1 ) ? fileName.substring(ind) : ".txt";
         try {
           tmp = File.createTempFile(prefix, suffix);
         }
         catch (IOException e) {
           e.printStackTrace();
         }
         return tmp;
     }
     
     public static String priceFormat(Object value, String priceLocale) {
    	 return priceFormat(priceLocale, value, priceLocale, -1, "");
     }
     
     public static String priceFormat(String userLocale, Object value, String priceLocale,
      int decimals,  String rightFiller)
     {
    try {
      HashMap localeCurrencyHM = I18nUtil.getCurrencyHashMap();
      if(value==null) {
        return "";
      }
      
      //STJ-3115
      String countyCode = Utility.getCountryCodeFromLocale(priceLocale);
      //CurrencyData priceCurrD = (CurrencyData) localeCurrencyHM.get(priceLocale);
      CurrencyData priceCurrD = (CurrencyData) localeCurrencyHM.get(countyCode);
      
      String priceCurrency = priceCurrD.getGlobalCode();

      //STJ-3115
      String userCountyCode = Utility.getCountryCodeFromLocale(userLocale);
      //CurrencyData currD = (CurrencyData) localeCurrencyHM.get(userLocale);
      CurrencyData currD = (CurrencyData) localeCurrencyHM.get(userCountyCode);
      

      if(decimals == -1) {
         decimals = priceCurrD.getDecimals();
        if (decimals == -1) {
            Currency curr = Currency.getInstance(priceCurrency);
            decimals = curr.getDefaultFractionDigits();
        }
      }

      NumberFormat format = DecimalFormat.getNumberInstance(new Locale(userLocale));
      format.setMaximumFractionDigits(decimals);
      format.setMinimumFractionDigits(decimals);
      double valueDbl = 0;
      if(value instanceof BigDecimal) {
        valueDbl = ((BigDecimal)value).doubleValue();
      } else if(value instanceof Double) {
        valueDbl = ((Double)value).doubleValue();
      }
      String valueS = format.format(valueDbl);
      if(currD==null || ! currD.getGlobalCode().equalsIgnoreCase(priceCurrency)) {
        valueS = valueS + rightFiller+ priceCurrency;
      } else {
        if(RefCodeNames.CURRENCY_POSITION_CD.LEFT.equals(currD.getCurrencyPositionCd())) {
          valueS = currD.getLocalCode() + valueS;
        } else if(RefCodeNames.CURRENCY_POSITION_CD.RIGHT.equals(currD.getCurrencyPositionCd())) {
          valueS = valueS + currD.getLocalCode();
        } else { //Right with space is default position
          valueS = valueS + rightFiller+ currD.getLocalCode();
        }
      }

      return valueS;

    } catch (Exception exc) {
      exc.printStackTrace();
      return "Error value: "+value;
    }
  }
     
     public static String formatNumber(Object value, Locale locale)
     {
    	 try {
    		 HashMap localeCurrencyHM = I18nUtil.getCurrencyHashMap();
    		 if(value==null) {
    			 return "";
    		 }

    		 String countyCode = Utility.getCountryCodeFromLocale(locale.toString());
    		 CurrencyData currencyD = (CurrencyData) localeCurrencyHM.get(countyCode);

    		 String priceCurrency = currencyD.getGlobalCode();

    		 int decimals = currencyD.getDecimals();
			 if (decimals == -1) {
				 decimals = 2;
			 }
			 
    		 NumberFormat format = DecimalFormat.getNumberInstance(locale);
    		 format.setMaximumFractionDigits(decimals);
    		 format.setMinimumFractionDigits(decimals);
    		 double valueDbl = 0;
    		 if(value instanceof BigDecimal) {
    			 valueDbl = ((BigDecimal)value).doubleValue();
    		 } else if(value instanceof Double) {
    			 valueDbl = ((Double)value).doubleValue();
    		 }
    		 return format.format(valueDbl);
    	 } catch (Exception exc) {
    		 exc.printStackTrace();
    		 return "Error value: "+value;
    	 }
     }
  //adds days, subtracts days etc from the specified date
    //we can thus take paramaters like:
    //@TODAY@ - 10
    //meaning 10 days ago
   public static Date doDateMods(String pModification, Date pDate) throws Exception {
        pModification = pModification.trim();
        if(pModification.length() == 0){
            return pDate;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(pDate);
        int lNumDays = 0;
        try {
          lNumDays = Integer.parseInt(pModification.substring(1));
          if(pModification.startsWith("-")){
              cal.add(Calendar.DAY_OF_YEAR, lNumDays * -1);
          }else if(pModification.startsWith("+")){
              cal.add(Calendar.DAY_OF_YEAR, lNumDays);
          }else{
              throw new RuntimeException("Unknown date modification action: "+pModification);
          }
        }
        catch (Exception ex) {
          throw new RuntimeException("Incorrect date modification value : "+pModification.substring(1));
        }
        return cal.getTime();
    }

   public static Date evaluateLastWeekday() {
     Calendar cal = Calendar.getInstance();
     cal.setTime(new Date());
     switch (cal.get(Calendar.DAY_OF_WEEK)){
       case Calendar.SUNDAY:
          cal.add(Calendar.DATE, -2);
          break;
        case Calendar.MONDAY:
          cal.add(Calendar.DATE, -3);
          break;
        default:
          cal.add(Calendar.DATE, -1);
          break;
      }
       Date theDate = cal.getTime();
       return theDate;
   }

  public static String customizeDateParam(String name, String value)  throws Exception {
    if (value == null) {
      return value;
    }
    String retValue = value;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    String valueLC = value.trim().toLowerCase().replaceAll(" ", "");
    Date theDate = new Date();
    GregorianCalendar gc = new GregorianCalendar();
    if (name.toLowerCase().indexOf("date") >= 0) {
//      if (valueLC.startsWith(Constants.REP_DATE_CONST.TODAY)) {
      if (isValidDateConstant(valueLC,Constants.REP_DATE_CONST.TODAY)) {
          theDate = doDateMods(valueLC.substring(Constants.REP_DATE_CONST.TODAY.length()), theDate);
          retValue = sdf.format(theDate);
        }
        else if (isValidDateConstant(valueLC,Constants.REP_DATE_CONST.LAST_WEEKDAY)) {
          theDate = evaluateLastWeekday();
          theDate = doDateMods(valueLC.substring(Constants.REP_DATE_CONST.LAST_WEEKDAY.length()),
                               theDate);
          retValue = sdf.format(theDate);
        }
        else if (isValidDateConstant(valueLC,Constants.REP_DATE_CONST.LAST_MONTH_YEAR_BEGIN)) {
          gc.add(GregorianCalendar.MONTH, -1);
          theDate = sdf.parse("01/01/" + gc.get(GregorianCalendar.YEAR));
          theDate = doDateMods(valueLC.substring(Constants.REP_DATE_CONST.LAST_MONTH_YEAR_BEGIN.length()),
                               theDate);
          retValue = sdf.format(theDate);
        }
        else if (isValidDateConstant(valueLC,Constants.REP_DATE_CONST.THIS_YEAR_BEGIN)) {
          theDate = sdf.parse("01/01/" + gc.get(GregorianCalendar.YEAR));
          theDate = doDateMods(valueLC.substring(Constants.REP_DATE_CONST.THIS_YEAR_BEGIN.length()),
                               theDate);
          retValue = sdf.format(theDate);
        }
        else if (isValidDateConstant(valueLC,Constants.REP_DATE_CONST.LAST_MONTH_END)) {
          gc.set(GregorianCalendar.DAY_OF_MONTH, 1);
          gc.add(GregorianCalendar.DAY_OF_MONTH, -1);
          theDate = sdf.parse( (gc.get(GregorianCalendar.MONTH) + 1) + "/" +
                                   gc.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
                                   gc.get(GregorianCalendar.YEAR));
          theDate = doDateMods(valueLC.substring(Constants.REP_DATE_CONST.
                                                 LAST_MONTH_END.length()),
                               theDate);
          retValue = sdf.format(theDate);
        }
      }
    return retValue;
  }

  public static boolean isValidDateConstant(String valueLC, String dateConstant){
//      String valueLC = value.trim().toLowerCase().replaceAll(" ", "");
      int operationPos =(  ( valueLC.indexOf("-")<0 ) ?
                           ((valueLC.indexOf("+")<0 ) ? -1  : valueLC.indexOf("+") ) :
                             valueLC.indexOf("-") );

      boolean isValid = valueLC.startsWith(dateConstant) &&
                        (operationPos==dateConstant.length() || operationPos<0);
      return isValid;
   }
  
  	public final static EventProcessView createEventProcessView(int processId, int priorityOverride, int subProcessPriority) throws Exception {
  		EventData eventData = Utility.createEventDataForProcess();
        EventProcessView epv = new EventProcessView(eventData, new EventPropertyDataVector(), 0);
        epv.getProperties().add(createEventPropertyData("process_id",
                Event.PROPERTY_PROCESS_TEMPLATE_ID,
                new Integer(processId),
                1));
        if (priorityOverride > 0)
			epv.getProperties().add(createEventPropertyData(Event.PRIORITY_OVERRIDE,
					Event.PROCESS_PRIORITY, priorityOverride,
					1));
		if (subProcessPriority > 0)
			epv.getProperties().add(createEventPropertyData(Event.SUBPROCESS_PRIORITY,
					Event.PROCESS_SUBPROCESS_PRIORITY, subProcessPriority,
					1));
        return epv;
  	}

     public final static EventPropertyData createEventPropertyData(
            String shortDesc, String eventTypeCd, Object value, int num)
            throws IOException {
        EventPropertyData epd = EventPropertyData.createValue();
        epd.setType(eventTypeCd);
        epd.setShortDesc(shortDesc);
        epd.setVarClass(value.getClass().getName());
        epd.setNum(num);
        if (value instanceof String) {
            epd.setStringVal((String) value);
        } else if (value instanceof Integer) {
            epd.setNumberVal((Integer) value);
        } else if (value instanceof Date) {
            epd.setDateVal(DBAccess.toSQLTimestamp((java.util.Date) value));
        } else {
            epd.setBlobValue(ObjectUtil.objectToBytes(value));
        }
        return epd;
    }

     public static boolean isValidDate(String pDateStr, String pFormat){
    	 try{
    		 SimpleDateFormat sdf = new SimpleDateFormat(pFormat);
    		 return isValidDate(pDateStr, sdf);
    	 }catch(Exception e){
    		 return false;
    	 }
     }
     
     public static boolean isValidDate(String pDateStr, SimpleDateFormat sdf){
    	 try{
    		 Date date = sdf.parse(pDateStr);
    		 GregorianCalendar gc = new GregorianCalendar();
    		 gc.setTime(date);
    		 String yearS = String.valueOf(gc.get(GregorianCalendar.YEAR));
    		 if(pDateStr.indexOf(yearS)<0) return false;
    		 String dayS = String.valueOf(gc.get(GregorianCalendar.DAY_OF_MONTH));
    		 if(pDateStr.indexOf(dayS)<0) return false;
    		 String monthS = String.valueOf(gc.get(GregorianCalendar.MONTH)+1);
    		 if(pDateStr.indexOf(monthS)<0) return false;
    		 return true;
    	 }catch(Exception e){
    		 return false;
    	 }
     }

    public final static EventData createEventDataForProcess() {
         return createEventData(Event.TYPE_PROCESS, Event.STATUS_READY, 1);
    }

    public final static EventData createEventDataForEmail() {
        return createEventData(Event.TYPE_EMAIL, Event.STATUS_READY, 1);
    }

    public final static EventData createEventData(String type, String status, int attempt) {
        EventData eventData = EventData.createValue();
        eventData.setType(type);
        eventData.setStatus(status);
        eventData.setAttempt(attempt);
        return eventData;
    }

    /**
	 * Checking email address for correct format. It will allow multiple email address
	 * 3 part of address seperated by '@' and '.'. Last part of email after '.' is 2 to 4 
	 * character long.
     *
     * @param emailAddress
     *            Email address(es) for checking.
     * @return true - if email address is correct
     */
    public static boolean isValidEmailAddress(String emailAddress) {
        return EmailValidation.isValidEmailAddress(emailAddress);
    }

    public static void main (String args[]) {
    	log.info(isStreetAddress("1 sts")+"=true");
    	log.info(isStreetAddress("sts1")+"=true");
    	log.info(isStreetAddress("sts")+"=false");
    	log.info(isStreetAddress("street")+"=false");
    	log.info(isStreetAddress(" street")+"=true");
    	log.info(isStreetAddress(" street,")+"=true");
    	log.info(isStreetAddress(" streett")+"=false");
    	log.info(isStreetAddress(" street t")+"=true");
    	log.info(getStreetAddress(" street t", "2dddd st", "one add", "1 st")+"=2dddd st");
    	log.info(postalCodeMatch("01532", "01532-1")+"=true");
    	log.info(postalCodeMatch("015321222", "01532-1222")+"=true");
    }


    public static UiControlView getUiControl(UiControlViewVector controls, String name) {
        if (controls != null && isSet(name)) {
            for (Object oControl : controls) {
                UiControlView control = (UiControlView) oControl;
                if (control.getUiControlData() != null && name.equals(control.getUiControlData().getShortDesc())) {
                    return control;
                }
            }
        }
        return null;
    }

    public static UiControlElementData getUiControlElement(UiControlElementDataVector controlElements, String name) {
       if (controlElements != null && isSet(name)) {
            for (Object oControlElement : controlElements) {
                UiControlElementData controlElement = (UiControlElementData) oControlElement;
                if (controlElement != null && name.equals(controlElement.getShortDesc())) {
                    return controlElement;
                }
            }
        }
        return null;
    }

    public static UiPageView getUiPage(UiPageViewVector pages, String name) {
        if (pages != null && isSet(name)) {
            for (Object oPage : pages) {
                UiPageView page = (UiPageView) oPage;
                if (page != null && name.equals(page.getUiPage().getShortDesc())) {
                    return page;
                }
            }
        }
        return null;
    }

    public static UiPageView createNewPage(UiData pUiData, String pShortDesc, String pTypeCd, String pStatus) {

        UiPageView uiPage = UiPageView.createValue();
        uiPage.setUiData(pUiData);
        uiPage.setUiControls(new UiControlViewVector());

        UiPageData uiPageData = UiPageData.createValue();
        uiPageData.setShortDesc(pShortDesc);
        uiPageData.setTypeCd(pTypeCd);
        uiPageData.setStatusCd(pStatus);
        uiPageData.setUiId(pUiData.getUiId());

        uiPage.setUiPage(uiPageData);

        return uiPage;

    }

       public static UiControlView createUiControl(UiPageData pUiPageData,String pShortDesc,String pStatus) {

        UiControlView uiControl = UiControlView.createValue();

        UiControlData uiControlData = UiControlData.createValue();
        uiControlData.setShortDesc(pShortDesc);
        uiControlData.setStatusCd(pStatus);
        uiControlData.setUiPageId(pUiPageData.getUiPageId());

        uiControl.setUiControlData(uiControlData);
        uiControl.setUiControlElements(new UiControlElementDataVector());

        return  uiControl;

    }

       public static boolean isContainsWorld(String pSourceString,
               String pSearchWorld) {
           return isContainsWorld(pSourceString, pSearchWorld, true);
       }

       public static boolean isContainsWorld(String pSourceString,
            String pSearchWorld, boolean pIsIgnoreCase) {
        if (pSourceString != null && pSearchWorld != null) {
            if (pIsIgnoreCase == true) {
                pSourceString = pSourceString.toLowerCase();
                pSearchWorld = pSearchWorld.toLowerCase();
            }
            final int sourceStringLength = pSourceString.length();
            final int searchWorldLength = pSearchWorld.length();
            int index = pSourceString.indexOf(pSearchWorld);
            while (index >= 0) {
                boolean previousOk = true;
                boolean nextOk = true;
                if (index > 0) {
                    char previousChar = pSourceString.charAt(index - 1);
                    if (Character.isLetterOrDigit(previousChar) == true) {
                        previousOk = false;
                    }
                }
                if ((index + searchWorldLength + 1) < sourceStringLength) {
                    char nextChar = pSourceString.charAt(index
                            + searchWorldLength);
                    if (Character.isLetterOrDigit(nextChar) == true) {
                        nextOk = false;
                    }
                }
                if (previousOk && nextOk) {
                    return true;
                }
                index = pSourceString.indexOf(pSourceString, index
                        + searchWorldLength);
            }
        }
        return false;
    }

    public static String getCategoryFullName(ItemData categoryD) {
        String categoryFullName = categoryD.getShortDesc();
        if (categoryD.getLongDesc() != null && categoryD.getLongDesc().length() > 0) {
            categoryFullName = categoryFullName + "(" + categoryD.getLongDesc() + ")";
        }
        return categoryFullName;
    }

    public static int getYearForDate(Date pForDate) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(pForDate);
        return cal.get(Calendar.YEAR);
    }

    public static int getCurrentYear() {
        return getYearForDate(new Date());
    }

    public static List<String> getAsList(String... strs) {
        List<String> list = new ArrayList<String>();
        if (strs != null) {
            for (String str : strs) {
                list.add(str);
            }
        }
        return list;
    }

    public static boolean isUserAutorizedForReport(String pUserTypeCd, String pReportUserTypes){
      boolean b = true;
      if (Utility.isSet(pReportUserTypes)){
        if (pUserTypeCd.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR)||
            pUserTypeCd.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR)){
          b= true;
        } else if (pUserTypeCd.equals(RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR)){
          b= (pReportUserTypes.contains(RefCodeNames.USER_TYPE_CD.CUSTOMER)||
              pReportUserTypes.contains(RefCodeNames.USER_TYPE_CD.MSB)||
              pReportUserTypes.contains(pUserTypeCd));
        } else {
          b= pReportUserTypes.contains(pUserTypeCd);
        }
      }
      return b;
    }

    public static boolean isLocateUserRequired(String pUserTypeCd, String pReportUserTypes){
      boolean b = false;
      if (Utility.isSet(pReportUserTypes)){
        if (pUserTypeCd.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR)||
            pUserTypeCd.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR)||
            pUserTypeCd.equals(RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR)){
          b= (!pReportUserTypes.contains(pUserTypeCd));
        }
      }
      return b;
    }


    public static OrderMetaData getMetaObject(OrderData pOrder,
                                              OrderMetaDataVector pOrderMeta,
                                              String pName) {

        if (pOrder == null || pName == null) {
            return null;
        }

        Date modDate = null;
        OrderMetaData retObject = null;
        for (Object oOrderMeta : pOrderMeta) {
            OrderMetaData omD = (OrderMetaData) oOrderMeta;
            String name = omD.getName();
            if (pName.equals(name)) {
                if (modDate == null) {
                    modDate = pOrder.getModDate();
                    retObject = omD;
                } else {
                    Date mD = pOrder.getModDate();
                    if (mD == null) continue;
                    if (modDate.before(mD)) {
                        modDate = mD;
                        retObject = omD;
                    }
                }
            }
        }

        return retObject;

    }

    public static List<String> truncateEmptyValues(List<String> pList) {
        for (int ii = 0; ii < pList.size(); ii++) {
            String val = (String) pList.get(ii);
            if (val == null || val.trim().length() == 0) {
                pList.remove(ii);
            }
        }
        return pList;
    }
    
    
    /**
     * Returns the appropriate ship to id based off the trading partner configuration
     * @param partner the TradingPartnerData object to use to make the determination.
     * @param outboundReq the OutboundEDIRequest to actually pull the right data from
     * @return A string of either the customer reference, distributor reference, or site id
     */
    public static String getIdForShipTo(TradingPartnerData partner, OutboundEDIRequestData outboundReq) {
        
        if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_SITE_REF_NUMBER.equals(partner.getSiteIdentifierTypeCd())){
        	String value = Utility.getPropertyValue(outboundReq.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
            if(Utility.isSet(value)){
                return value;
            }
        }else if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.DIST_SITE_REFERENCE_NUMBER.equals(partner.getSiteIdentifierTypeCd())){
        	String value = Utility.getPropertyValue(outboundReq.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER);
            if(Utility.isSet(value)){
                return value;
            }
        }
        return outboundReq.getSiteIdentifier();
    }
    
    /*
     * if pValue is positive then returns negative value pValue, else return pValue
     * Example 
     * pValue = 5, then returns -5
     * pValue = -5, then returns -5 
     */
    public static BigDecimal toNegative(BigDecimal pValue){
    	pValue = checkValue(pValue);
    	BigDecimal zeroValue = new BigDecimal(0);
      if ( pValue.compareTo(zeroValue)>0 ) {        
        return  pValue.negate();
      }else{
      	return pValue;
      }
    }
    
    public static String encodeForHTML(String text){
    	String temp = ESAPI.encoder().encodeForHTML(text);
		return temp;
    }
    
    public static String encodeForHTMLAttribute(String text){
    	String temp = ESAPI.encoder().encodeForHTMLAttribute(text);
		return temp;
    }
    
    public static String encodeForURL(String text){
    	String temp = "";
		try {
			temp = ESAPI.encoder().encodeForURL(text);
		} catch (EncodingException e) {
			e.printStackTrace();
		}
		return temp;
    }
    
    public static String encodeForXML(String text){
    	Encoder encoder = ESAPI.encoder();
    	String temp = ESAPI.encoder().encodeForXML(text);
		return temp;
    }
    
    public static String encodeForJavaScript(String text){
        Encoder encoder = ESAPI.encoder();
        String temp = ESAPI.encoder().encodeForJavaScript(text);
        return temp;
    }
    
    /**
     * Return a string of the form "(?,?,?,...)", meant to follow the "IN" keyword in a SQL where clause. 
     * The number of question marks to include is specified by the numParameters argument.
     * @param numParameters the number of question marks, must be greater than zero
     * @return the string fragment
     * 
     * @see #bindParameterList(PreparedStatement, int, List)
     */
    public static String makeParameterList(int numParameters) {
      if (numParameters <= 0) {
        throw new IllegalArgumentException("The numParameters argument must be > 0: " + numParameters);
      }

      StringBuffer sb = new StringBuffer();
      sb.append("(");
      boolean commaNeeded = false;
      for (int i = 0; i < numParameters; i++) {
        if (commaNeeded) {
          sb.append(", ");
        }
        else {
          commaNeeded = true;
        }
        sb.append("?");
      }
      sb.append(")");

      return sb.toString();
    }
    
   /**
     * <code>getCountryCodeFromLocale</code>
     * @param locale, a <code>String</code> value
     * @return countryCd, a <code>String</code> value
     */
    public static String getCountryCodeFromLocale(String locale) {
    	String countryCd="US";
    		try {
    			if(locale==null || locale.length()==0) {
    				locale = "en_US";
    			}
    			countryCd = locale.substring(3,locale.trim().length());
    		}catch(Exception e) { 
    		}
    	return countryCd;
    }
    
    /**
     * returns SessionDataUtil Object
     * @param request - HttpServletRequest
     * @return SessionDataUtil - Returns the <class>SessionDataUtil</class>
     */
    public static SessionDataUtil getSessionDataUtil(HttpServletRequest request) {
    	return getSessionDataUtil(request.getSession(false));
    }
    
    /**
     * returns SessionDataUtil Object
     * @param session - HttpSession
     * @return SessionDataUtil - Returns the <class>SessionDataUtil</class>
     */
    public static SessionDataUtil getSessionDataUtil(HttpSession session) {
    	SessionDataUtil sessionDataUtil = (SessionDataUtil)session.getAttribute(Constants.SESSION_DATA);
    	//If there is no SessionDataUtil is available in the session, 
    	//create a new object and put it in session.
    	if(sessionDataUtil==null) {
    		sessionDataUtil = new SessionDataUtil();
    		session.setAttribute(Constants.SESSION_DATA, sessionDataUtil);
    	}
    	
    	return sessionDataUtil;
    }
    
    
    /**
     * Method to ensure that strings being returned in a JSON object are escaped correctly.
     */
    public static String escapeStringForJSON(String s) {
    	String returnValue = s.replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\r", "\\r")
        .replace("\n", "\\n");
    	return returnValue;
    }

    public static <T> List<T> toListNN(T pObject) {
        List<T> list = new ArrayList<T>();
        if (pObject != null) {
            list.add(pObject);
        }
        return list;
    }

    public static <T> List<T> toList(T pObject) {
        List<T> list = new ArrayList<T>();
        list.add(pObject);
        return list;
    }
    
    //Return a string of javascript calls to initialize date pickers
    public static String getDatePickerInitializationCalls(Locale locale, String dateFormat) {
    	String language = locale.getLanguage();
    	String country = locale.getCountry();
    	dateFormat = dateFormat.replace("yyyy", "yy").toLowerCase();
    	
    	//call the internationalize method with increasing levels of locale specificity, so the
    	//date picker will use the most specific match it can
    	StringBuilder methodCalls = new StringBuilder(100);
    	methodCalls.append("internationalizeDatePickers('', '" + dateFormat + "');");
    	methodCalls.append("internationalizeDatePickers('" + language + "', '" + dateFormat + "');");
    	methodCalls.append("internationalizeDatePickers('" + language + "-" + country + "', '" + dateFormat + "');");
    	return methodCalls.toString();
    }
    
    /**
     * Checks if the passed number is valid or not
     * @param value - any value.
     * @return - true if a number is valid, otherwise it will return false.
     */
    public static boolean isValidNumber(String value) {
    	boolean validNumber = true;
    	
    	if(isSet(value)) {
	    	for (int index = 0; index < value.length(); index++) {
	            if(!Character.isDigit(value.charAt(index))) {
	            	validNumber = false;
	            	break;
	            }
	        }
    	} else {
    		validNumber = false;
    	}
    	
    	return validNumber;
    }
    
    public static String safeTrim(String s) {
    	String returnValue = null;
    	if (s != null) {
    		returnValue = s.trim();
    	}
    	return returnValue;
    }
    
    /**
	 * Gets list of COST CENTER Id's which are configured to ACCOUNT CATALOG.
	 * @param con - the <code>Connection</code> object.
	 * @param accountCatalogId - ACCOUNT CATALOG's Id.
	 * @return - the <code>List</code> of configured COST CENTER ids
	 */
	 public static List<Integer> getConfiguredCostCenterIds(java.sql.Connection con,int accountCatalogId) {
		List<Integer> costCenterIds = new ArrayList<Integer>();	 	
	 	DBCriteria dbc = new DBCriteria();
	 	dbc.addEqualTo(CostCenterAssocDataAccess.CATALOG_ID, accountCatalogId);
	 	try {
			CostCenterAssocDataVector ccaDataVector = CostCenterAssocDataAccess.select(con, dbc);
			if(Utility.isSet(ccaDataVector)) {
				for(int idx=0; idx<ccaDataVector.size();idx++) {
					CostCenterAssocData cCAData = (CostCenterAssocData)ccaDataVector.get(idx);
					costCenterIds.add(cCAData.getCostCenterId());
				}
			}
		} catch (SQLException e1) {
		}
		return costCenterIds;
	 }
	 
	 public static boolean isFalse(String value) {
		 boolean returnValue = true;
		 if(Utility.isSet(value)) {
			 value = value.toUpperCase();
			 if("N".equals(value) || "NO".equals(value) ||"0".equals(value) ||"F".equals(value) ||"FALSE".equals(value)){
				 returnValue = false;
			 }
		 }
		 return returnValue;
	 }
	 
	 /**
	  * Method to ensure that string(category name) that needs to be displayed in pie chart is escaped correctly.
	 */
	 public static String escapeSpecialCharsForPieChart(String category) {
		 String categoryName = "";
		 if( category != null )
			 categoryName = category.replace("\\","\\\\").replace("\"","\\\"").replace("'","\\'");
	    	return categoryName;
	}
	 
	 public static List<String> getStringListFromIdVector(IdVector list){
		 if (list != null){
			 List<String> listStr = new ArrayList<String>();
			 for(Iterator iter=list.iterator(); iter.hasNext();) {
				 listStr.add(iter.next().toString());
	         }
			 return listStr;
		 }
		 return null;
		 
	 }
	 
    public static boolean hasWildCards(String value) {
        if (isSet(value)) {
            return value.indexOf("%") >= 0 || value.indexOf("_") >= 0;
        }
        return false;
    }

	public static String safeWildCards(String value) {
        if (!isSet(value)) {
            return value;
        }
        value = safeWildCards(value, "%");
        value = safeWildCards(value, "_");
        return value;
    }

    private static String safeWildCards(String value, String wildSymbol) {
        if (!isSet(value)) {
            return value;
        }

        int i = value.indexOf(wildSymbol);
        int len = value.length();
        while (i >= 0) {
            if (i == 0) {
                value = Constants.DB_ESCAPE_SYMBOL + wildSymbol + value.substring(1);
            } else if (i < len) {
                value = value.substring(0, i) + Constants.DB_ESCAPE_SYMBOL + wildSymbol + value.substring(i+1);
            } else {
                value = value.substring(0, i) + Constants.DB_ESCAPE_SYMBOL + wildSymbol;
            }
            i = value.indexOf(wildSymbol, i+2);
        }
        return value;
    }
    /**
	  * Method to find whether account has fiscal calendar and site has budgets set.
	 */
	 public static boolean doesAccountHasFiscalCalendar(HttpServletRequest request){
		 try{
			 CleanwiseUser user = (CleanwiseUser) request.getSession(false).getAttribute(Constants.APP_USER);
			 APIAccess factory = new APIAccess();
			 CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
			 //Here we find whether fiscal calendar is set for account.
			 CatalogFiscalPeriodViewVector cfpVwV = catInfEjb.getFiscalInfo(user.getUserAccount().getAccountCatalogId());
			 //Here we find whether site has budgets set.
			 if(cfpVwV != null && cfpVwV.size() > 0) { // check if account has fiscal calendar set
				 CostCenterDataVector ccdv = ShopTool.getAllCostCenters(request);
				 for(int i = 0; ccdv != null && i < ccdv.size();i++){
					 if(((CostCenterData)ccdv.get(i)).getNoBudget().equalsIgnoreCase(Constants.FALSE)){ //check if site has budgets set
						 return true;
					 }
				 }
			 }
			 return false;
		 }
		 catch(Exception e) {
			 return false;
		 }
	 }
	 
	 public static String getInitErrorMsg(Exception ex){
		  Throwable e = getInitialException(ex);
		  String errMsg = e.getMessage() != null ? e.getMessage() : ex.toString();
		  String errMsg1= Utility.getUiErrorMess(ex.getMessage());
		  if (errMsg1 != null)
			  return errMsg1;
		  else
			  return errMsg;
	  }
	  
	  private static Throwable getInitialException(Throwable e){         
		  if (e.getCause() != null)
			  return getInitialException(e.getCause());

		  return e;
	  }

	 //STJ-4689
	 public static String getAddressFormatFor(String pCountryShortDesc) {
		 try{
			 APIAccess factory = new APIAccess();
			 Country countryEjb = factory.getCountryAPI();
			 return countryEjb.getAdddressFormatByShortDesc(pCountryShortDesc);
		 } catch(Exception e) {
			 return null;
		 }
	 }
	 
	 public static List getUniqueValues(Collection values)
	 {
	    return new ArrayList(new HashSet(values));
	 }

	 public static Set<String> anyDelimitedListToSet(String str){
    	Set<String> res = new HashSet<String>();
    	if (Utility.isSet(str)) {
    		Set<String> set = commaDelimitedListToSet(str.replaceAll("[\\W\\s*\\_]", ","));
    		for (String el : set){
    			if (Utility.isSet(el)){
    				res.add(el.trim());
    			}
    		}
    	}
    	return res;
    }
	    public static Set commaDelimitedListToSet(String str) {
	        Set set = new HashSet();
	        String[] tokens = commaDelimitedListToStringArray(str);
	        if (tokens != null) {	        
		        for (int i = 0; i < tokens.length; i++) {
		            set.add(tokens[i]);
		        }
	        }
	        return set;
	    }
		 
		 public static String[] commaDelimitedListToStringArray(String str) {
	        return delimitedListToStringArray(str, ",");
	    }
		    	
	    /**
	     * Convenience method to convert a CSV string list to a set.
	     * Note that this will suppress duplicates.
	     * @param str the input String
	     * @return a Set of String entries in the list
	     */
		    	

		 public static String[] delimitedListToStringArray(String str, String delimiter) {
	        if (str == null) {
	            return new String [0];
	        }
	        if (delimiter == null) {
	            return new String[] {str};
	        }
	        List result = new ArrayList();
	        if ("".equals(delimiter)) {
	            for (int i = 0; i < str.length(); i++) {
	                result.add(str.substring(i, i + 1));
	            }
	        }
	        else {
	            int pos = 0;
	            int delPos = 0;
	            while ((delPos = str.indexOf(delimiter, pos)) != -1) {
	                result.add(str.substring(pos, delPos));
	                pos = delPos + delimiter.length();
	            }
	            if (str.length() > 0 && pos <= str.length()) {
	                // Add rest of String, but not in case of empty input.
	           	 result.add(str.substring(pos));
	            }
	        }
	        return (String[]) result.toArray(new String[result.size()]);
	    }
    //---- STJ-6114: Performance Improvements - Optimize Pollock 
    public static int getItemId(Object obj) {
        if (obj instanceof InventoryLevelData) {
            return ((InventoryLevelData) obj).getItemId();
        } else if (obj instanceof SiteInventoryInfoView) {
            return ((SiteInventoryInfoView) obj).getItemId();
        } else if (obj instanceof ShoppingCartItemData) {
            return ((ShoppingCartItemData) obj).getItemId();
        } else if (obj instanceof ItemData) {
            return ((ItemData) obj).getItemId();
        } else if (obj instanceof ItemAssocData) {
            return ((ItemAssocData) obj).getItem1Id();
        } else if (obj instanceof ItemMappingData) {
            return ((ItemMappingData) obj).getItemId();
        } else if (obj instanceof ShoppingControlData) {
            return ((ShoppingControlData) obj).getItemId();
        } else if (obj instanceof InventoryItemsData) {
            return ((InventoryItemsData) obj).getItemId();
        } else if (obj instanceof OrderGuideStructureData) {
            return ((OrderGuideStructureData) obj).getItemId();
        }else if (obj instanceof CatalogStructureData) {
        	return ((CatalogStructureData) obj).getItemId();
        }else {
            throw new UnsupportedOperationException("getItemId does not support objects of type: " + obj.getClass().getName());
        }
    }
    //------------
    /**
     * Construct full table name with using the namespace and table name.
     */
    public static String getFullTableName(String namespace, String table, String pSuffix, String dbLink)  {
         String fullTableName = table.toUpperCase();
         String suffix = (Utility.isSet(pSuffix)) ? pSuffix :"";
         if (Utility.isSet(namespace)) {
           if (Utility.isSet(dbLink) && table.toUpperCase().startsWith("CLW_")){
             fullTableName =  namespace.toUpperCase() + "." + fullTableName + "@" + dbLink.toUpperCase();
           } else {
             fullTableName = namespace.toUpperCase() + "." + fullTableName;
           }
         }
         return fullTableName + suffix;
   }
    public static  String getFullTableName(String namespace, String table, String dbLink)  {
        return getFullTableName( namespace, table, null, dbLink);
   }    
    public static  String getFullTableName(String namespace, String table)  {
        return getFullTableName( namespace, table, null, null);
   }    

	 
}

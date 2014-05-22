/*
 * ClwI18nUtil.java
 *
 * Created on June 27, 2006, 3:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cleanwise.view.i18n;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.CountryPropertyDataVector;
import com.cleanwise.service.api.value.CurrencyData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderScheduleView;
import com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;

/**
 *
 * @author Ykupershmidt
 */
public class ClwI18nUtil {
  private static final Logger log = Logger.getLogger(ClwI18nUtil.class);
  private static  Date exampleDate = (new GregorianCalendar(2006,1,9)).getTime();

  public static String getPriceShopping(HttpServletRequest request, Object value) {
      return getPriceShopping(request, value, null, null, " ");
  }

    public static String formatCurrencyAmount
  (HttpServletRequest request, Object value, String pCurrencyCd ) {
  try {

      CurrencyData currD =  I18nUtil.getCurrencyByCd(pCurrencyCd);
      String priceCurrency = currD.getGlobalCode();

      Locale locale = getUserLocale(request);
      CurrencyData userCurrD =  I18nUtil.getCurrency(locale.toString());

      int decimals = currD.getDecimals();
      if (decimals == -1) {
          Currency curr = Currency.getInstance(priceCurrency);
          decimals = curr.getDefaultFractionDigits();
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

      String rightFiller = " ";
      String valueS = format.format(valueDbl);
      if(currD==null || ! currD.getGlobalCode().equalsIgnoreCase(userCurrD.getGlobalCode())) {
    valueS = valueS + rightFiller+ priceCurrency;
      } else {
    if(RefCodeNames.CURRENCY_POSITION_CD.LEFT.equals(currD.getCurrencyPositionCd())) {
        valueS = currD.getLocalCode()+ valueS;
    } else if(RefCodeNames.CURRENCY_POSITION_CD.RIGHT.equals(currD.getCurrencyPositionCd())) {
        valueS = valueS + currD.getLocalCode();
    } else { //Right with space is default position
        valueS = valueS + rightFiller + currD.getLocalCode();
    }
      }

      return valueS;

  } catch (Exception exc) {
      exc.printStackTrace();
      return "Error value: "+value;
  }
    }

  public static String getPriceShopping(HttpServletRequest request,
                                       Object value, String rightFiller) {
      return getPriceShopping(request, value, null, null, rightFiller);
  }

  public static String getPriceShopping
      (HttpServletRequest request, Object value,
       String valueLocaleCd, Integer valueDecimals, String rightFiller )
  {
      try {
        HashMap localeCurrencyHM = I18nUtil.getCurrencyHashMap();
        if(value==null) {
          return "";
        }
        HttpSession session = request.getSession();
        int decimals = -1;
        if(valueDecimals==null) {
          Integer catalogDecimalsI = (Integer) session.getAttribute(Constants.CATALOG_DECIMALS);
          if (catalogDecimalsI!=null) decimals = catalogDecimalsI.intValue();
        } else {
          decimals = valueDecimals.intValue();
        }
        CurrencyData valueCurrency = null;
        boolean noLocale = false;
        if("noLocale".equalsIgnoreCase(valueLocaleCd)){
        	valueLocaleCd = "en_US";
        	noLocale = true;

        }

        if(valueLocaleCd==null) {
          valueLocaleCd = (String) session.getAttribute(Constants.CATALOG_LOCALE);
        }

        if(valueLocaleCd==null) {
            valueLocaleCd = "en_US";
        }
        
        //STJ-3115
        String countyCode = Utility.getCountryCodeFromLocale(valueLocaleCd);
        //CurrencyData valueCurrD = (CurrencyData) localeCurrencyHM.get(valueLocaleCd);
        CurrencyData valueCurrD = (CurrencyData) localeCurrencyHM.get(countyCode);
        
        String valueCurrencyCd = valueCurrD.getGlobalCode();

        // user currency
        Locale userLocale = getUserLocale(request);
        
        //STJ-3115
        String userCountyCode = Utility.getCountryCodeFromLocale(userLocale.toString());
        //CurrencyData userCurrD = (CurrencyData) localeCurrencyHM.get(userLocale.toString());
        CurrencyData userCurrD = (CurrencyData) localeCurrencyHM.get(userCountyCode);

        if(decimals==-1) {
            decimals=valueCurrD.getDecimals();
            if(decimals==-1) {
                Currency curr = Currency.getInstance(valueCurrencyCd);
                decimals = curr.getDefaultFractionDigits();
            }
        }

        NumberFormat format = DecimalFormat.getNumberInstance(userLocale);
        format.setMaximumFractionDigits(decimals);
        format.setMinimumFractionDigits(decimals);
        double valueDbl = 0;
        if(value instanceof BigDecimal) {
          valueDbl = ((BigDecimal)value).doubleValue();
        } else if(value instanceof Double) {
          valueDbl = ((Double)value).doubleValue();
        } else if(value instanceof String)
        {
         try{
          valueDbl=Double.parseDouble((String) value);
         }
         catch(NumberFormatException e){
           throw  new Exception(e.getMessage());
         }
        }


        String valueS = format.format(valueDbl);
        if(!noLocale){
        if(userCurrD==null || ! userCurrD.getGlobalCode().equalsIgnoreCase(valueCurrencyCd)) {
          valueS = valueS + rightFiller +valueCurrencyCd;
        } else {
          if(RefCodeNames.CURRENCY_POSITION_CD.LEFT.equals(valueCurrD.getCurrencyPositionCd())) {
            valueS = valueCurrD.getLocalCode()+ valueS;
          } else if(RefCodeNames.CURRENCY_POSITION_CD.RIGHT.equals(valueCurrD.getCurrencyPositionCd())) {
            valueS = valueS + valueCurrD.getLocalCode();
          } else { //Right with space is default position
            valueS = valueS + rightFiller + valueCurrD.getLocalCode();
          }
        }
      }

/*
    StringBuffer buffer = new StringBuffer(100);
    Currency dollars = Currency.getInstance("USD");
    Currency pounds = Currency.getInstance(Locale.UK);
    buffer.append("Dollars: ");
    buffer.append(dollars.getSymbol());
    buffer.append("\n");
    buffer.append("Pound Sterling: ");
    buffer.append(pounds.getSymbol());
    buffer.append("\n-----\n");
    double amount = 5000.25;
    NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.US);
    buffer.append("Symbol: ");
    buffer.append(usFormat.getCurrency().getSymbol());
    buffer.append("\n");
    buffer.append(usFormat.format(amount));
    buffer.append("\n");
    NumberFormat germanFormat =
      NumberFormat.getCurrencyInstance(Locale.GERMANY);
    buffer.append("Symbol: ");
    buffer.append(germanFormat.getCurrency().getSymbol());
    buffer.append("\n");
    buffer.append(germanFormat.format(amount));
*/
        return valueS;

      } catch (Exception exc) {
        exc.printStackTrace();
        String mess=getMessage(request,"shop.errors.priceFormat",new Object[]{value},true);
        return mess==null?("Error value: "+value):mess;
      }
    }
  
  /**
   * Retrieve date pattern that will be visible in the UI
   * @param request
   * @return
   */
  public static String getUIDateFormat(HttpServletRequest request){
	  
	  String dateFormat = getCountryDateFormat(request);
			  
	  dateFormat = dateFormat.replace("MM", getMessage(request,"format.date.abbrev.month"));
	  dateFormat = dateFormat.replace("dd", getMessage(request,"format.date.abbrev.day"));
	  dateFormat = dateFormat.replace("yyyy", getMessage(request,"format.date.abbrev.year"));	  

	  return dateFormat;
  }
  /**
   * Retrieve date pattern based on user's country. This pattern should be passed to java date format
   * @param request
   * @return date pattern
   */
  public static String getCountryDateFormat(HttpServletRequest request){
	  
	  String dateFormat = "MM/dd/yyyy";
	  try{
		  HttpSession session = request.getSession();
		  APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
		  Country countryEjb = factory.getCountryAPI();

		  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		  Locale userLocale = appUser.getPrefLocale();
		  String countryCd = userLocale.getCountry();
		  
		  CountryData countryD = countryEjb.getCountryByCountryCode(countryCd);
		  if(Utility.isSet(countryD.getInputDateFormat())){
			  dateFormat = countryD.getInputDateFormat();
		  }

	  } catch (Exception exc) {
		  exc.printStackTrace();
		  //return null;
	  }
	  return dateFormat;
  }
  
  /**
   * Retrieve time pattern that will be visible in the UI
   * @param request
   * @return
   */
  public static String getUITimeFormat(HttpServletRequest request){
	  
	  String timeFormat = getCountryTimeFormat(request);

	  timeFormat = timeFormat.replace("HH", getMessage(request,"format.date.abbrev.hour"));
	  timeFormat = timeFormat.replace("mm", getMessage(request,"format.date.abbrev.minute"));  

	  return timeFormat;
  }
  /**
   * Retrieve time pattern based on user's country
   * @param request
   * @return time pattern
   */
  public static String getCountryTimeFormat(HttpServletRequest request){
	  
	  String timeFormat = "HH:mm";
	  try{
		  HttpSession session = request.getSession();
		  APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
		  Country countryEjb = factory.getCountryAPI();

		  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		  Locale userLocale = appUser.getPrefLocale();
		  String countryCd = userLocale.getCountry();
		  
		  CountryData countryD = countryEjb.getCountryByCountryCode(countryCd);
		  if(Utility.isSet(countryD.getInputTimeFormat())){
			  timeFormat = countryD.getInputTimeFormat();
		  }

	  } catch (Exception exc) {
		  exc.printStackTrace();
		  //return null;
	  }
	  return timeFormat;
  }
  
  /**
   * Format amount with either catalog locale or order locale
   * @param request
   * @param amount
   * @param contractId
   * @param orderId
   * @param userId
   * @return
   */
  public static String formatAmount(HttpServletRequest request, Object amount, int contractId, int orderId, int userId){
	  
	  String formattedAmount = null;
	  try{
		  HttpSession session = request.getSession();
		  APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
		  User userEjb = factory.getUserAPI();
		  Contract contractEjb = factory.getContractAPI();
		  Order orderEjb = factory.getOrderAPI();
		  
		  String currLocale = null;
		  UserData userD = userEjb.getUser(userId);
		  String userLocale = userD.getPrefLocaleCd();
		  
		  if(contractId > 0){
			  ContractData contractD = contractEjb.getContract(contractId);
			  currLocale = contractD.getLocaleCd();
		  }
		  
		  if(orderId > 0){
			  OrderData orderD = orderEjb.getOrder(orderId);
			  currLocale = orderD.getLocaleCd();
		  }
		  
		  if(currLocale == null){
			  currLocale = (String) session.getAttribute(Constants.CATALOG_LOCALE);
		  }
		  
		  formattedAmount = formatAmount(amount, currLocale, userLocale);
		  
	  } catch (Exception exc) {
		  exc.printStackTrace();
		  String mess=getMessage(request,"shop.errors.priceFormat",new Object[]{amount},true);
		  return mess==null?("Error value: "+amount):mess;
	  }
	  return formattedAmount;
  }
  
  /**
   * Format an amount- currency based on currency locale, number format based on user locale
   * @param amount
   * @param currencyLocale
   * @param userLocale
   * @return
   * @throws Exception
   */
  public static String formatAmount(Object amount, String currencyLocale, String userLocale) throws Exception{
	  
	  if(amount==null){
		  return "";
	  }
	  
	  HashMap localeCurrencyHM = I18nUtil.getCurrencyHashMap();
	  
	  //Currency
	  if(currencyLocale==null) {
		  currencyLocale = "en_US";
      }
	  String currencyCountryCd = Utility.getCountryCodeFromLocale(currencyLocale);
	  String userCountryCode = Utility.getCountryCodeFromLocale(userLocale);
     
      CurrencyData currencyD = (CurrencyData) localeCurrencyHM.get(currencyCountryCd);
      CurrencyData userCurrD = (CurrencyData) localeCurrencyHM.get(userCountryCode);
      
      int decimals= currencyD.getDecimals();
      if(decimals==-1) {
          Currency curr = Currency.getInstance(currencyD.getGlobalCode());
          decimals = curr.getDefaultFractionDigits();
      }
      
	  //NumberFormat

      NumberFormat numFormat = DecimalFormat.getNumberInstance(Utility.parseLocaleCodeV2(userLocale));
      numFormat.setMaximumFractionDigits(decimals);
      numFormat.setMinimumFractionDigits(decimals);
      
      double amountDbl = 0;
      if(amount instanceof BigDecimal) {
    	  amountDbl = ((BigDecimal)amount).doubleValue();
      } else if(amount instanceof Double) {
    	  amountDbl = ((Double)amount).doubleValue();
      } else if(amount instanceof String){
    	  try{
    		  amountDbl=Double.parseDouble((String) amount);
    	  }catch(NumberFormatException e){
    		  throw  new Exception(e.getMessage());
    	  }
      }

      //Final formatting
      String formattedAmount = numFormat.format(amountDbl);
      
      //remove negative sign if present
      if(formattedAmount.startsWith("-")){
    	  formattedAmount = formattedAmount.substring(1);
      }
      
      if(userCurrD==null || !userCurrD.getGlobalCode().equalsIgnoreCase(currencyD.getGlobalCode())) {
    	  formattedAmount = formattedAmount + " " +currencyD.getGlobalCode();
      } else {
    	  if(RefCodeNames.CURRENCY_POSITION_CD.LEFT.equals(currencyD.getCurrencyPositionCd())) {
    		  formattedAmount = currencyD.getLocalCode()+ formattedAmount;
    	  } else if(RefCodeNames.CURRENCY_POSITION_CD.RIGHT.equals(currencyD.getCurrencyPositionCd())) {
    		  formattedAmount = formattedAmount + currencyD.getLocalCode();
    	  } else { //Right with space is default position
    		  formattedAmount = formattedAmount + " " + currencyD.getLocalCode();
    	  }
      }
      
      
	  return formattedAmount;
	  
  }

  private NumberFormat mFormat = null;
  public void setFormat(NumberFormat pVal) {mFormat = pVal;}
  public NumberFormat getFormat(){return mFormat;}

  private String mPrefix = "";
  public void setPrefix(String pVal) {mPrefix = pVal;}
  public String getPrefix(){return mPrefix;}

  private String mSuffix = "";
  public void setSuffix(String pVal) {mSuffix = pVal;}
  public String getSuffix(){return mSuffix;}

  private Locale mUserLocale = null;
  public void setUserLocale(Locale pVal) {mUserLocale = pVal;}
  public Locale getUserLocale(){return mUserLocale;}

  private Locale mPriceLocale = null;
  public void setPriceLocale(Locale pVal) {mPriceLocale = pVal;}
  public Locale getPriceLocale(){return mPriceLocale;}

  public static ClwI18nUtil getInstance(HttpServletRequest request, String priceLocale, int priceDecimals)
  throws Exception {
    ClwI18nUtil clwI18n = new ClwI18nUtil();
    HashMap localeCurrencyHM = I18nUtil.getCurrencyHashMap();
    HttpSession session = request.getSession();
    
    //STJ-3115
    String countyCode = Utility.getCountryCodeFromLocale(priceLocale);
    //CurrencyData priceCurrD = (CurrencyData) localeCurrencyHM.get(priceLocale);
    CurrencyData priceCurrD = (CurrencyData) localeCurrencyHM.get(countyCode);
    
    String priceCurrency = "USD";
    if (priceCurrD != null) {
        priceCurrency = priceCurrD.getGlobalCode();
    }

    Locale locale = getUserLocale(request);
    clwI18n.setUserLocale(locale);
    
    //STJ-3115
    String userCountyCode = Utility.getCountryCodeFromLocale(locale.toString());
    //CurrencyData currD = (CurrencyData) localeCurrencyHM.get(locale.toString());
    CurrencyData currD = (CurrencyData) localeCurrencyHM.get(userCountyCode);
    
    if (priceDecimals == -1) {
        priceDecimals = priceCurrD.getDecimals();
        if (priceDecimals == -1) {
            Currency curr = Currency.getInstance(priceCurrency);
            priceDecimals = curr.getDefaultFractionDigits();
        }
      }
    NumberFormat format = DecimalFormat.getNumberInstance(locale);
    format.setMaximumFractionDigits(priceDecimals);
    format.setMinimumFractionDigits(priceDecimals);
    clwI18n.setFormat(format);
    if(currD==null || ! currD.getGlobalCode().equalsIgnoreCase(priceCurrency)) {
      clwI18n.setPrefix("");
      clwI18n.setSuffix(priceCurrency);
    } else {
      if(RefCodeNames.CURRENCY_POSITION_CD.LEFT.equals(currD.getCurrencyPositionCd())) {
        clwI18n.setPrefix(currD.getLocalCode());
        clwI18n.setSuffix("");
      } else if(RefCodeNames.CURRENCY_POSITION_CD.RIGHT.equals(currD.getCurrencyPositionCd())) {
        clwI18n.setPrefix("");
        clwI18n.setSuffix(currD.getLocalCode());
      } else { //Right with space is default position
        clwI18n.setPrefix("");
        clwI18n.setSuffix(currD.getLocalCode());
      }
    }
    return clwI18n;

  }

  public String priceFormat(Object value, String rightFiller)
  throws Exception
  {
    double valueDbl = 0;
    if(value instanceof BigDecimal) {
      valueDbl = ((BigDecimal)value).doubleValue();
    } else if(value instanceof Double) {
      valueDbl = ((Double)value).doubleValue();
    }
    String valueS = mFormat.format(valueDbl);
    valueS = mPrefix + valueS + rightFiller+ mSuffix;
    return valueS;
  }

  public String priceFormatWithoutCurrency(Object value)
  throws Exception
  {
    double valueDbl = 0;
    if(value instanceof BigDecimal) {
      valueDbl = ((BigDecimal)value).doubleValue();
    } else if(value instanceof Double) {
      valueDbl = ((Double)value).doubleValue();
    }
    String valueS = mFormat.format(valueDbl);
  //  valueS = mPrefix + valueS + rightFiller+ mSuffix;
    return valueS;
  }

  public static String priceFormat(HttpServletRequest request, Object value, String priceLocale,
          String rightFiller)
  {
      try {
        HashMap localeCurrencyHM = I18nUtil.getCurrencyHashMap();
        if(value==null) {
          return "";
        }
        HttpSession session = request.getSession();
        
        //STJ-3115
        String countyCode = Utility.getCountryCodeFromLocale(priceLocale);
        //CurrencyData priceCurrD = (CurrencyData) localeCurrencyHM.get(priceLocale);
        CurrencyData priceCurrD = (CurrencyData) localeCurrencyHM.get(countyCode);
        
        String priceCurrency = priceCurrD.getGlobalCode();

        Integer catalogDecimalsI = (Integer) session.getAttribute(Constants.CATALOG_DECIMALS);
        int catalogDecimals = (catalogDecimalsI==null)? -1 : catalogDecimalsI.intValue();
        Locale locale = getUserLocale(request);
        
        //STJ-3115
        String userCountyCode = Utility.getCountryCodeFromLocale(locale.toString());
        // CurrencyData currD = (CurrencyData) localeCurrencyHM.get(locale.toString());
        CurrencyData currD = (CurrencyData) localeCurrencyHM.get(userCountyCode);
        
        if (catalogDecimals == -1) {
            catalogDecimals = priceCurrD.getDecimals();
            if (catalogDecimals == -1) {
                Currency curr = Currency.getInstance(priceCurrency);
                catalogDecimals = curr.getDefaultFractionDigits();
            }
        }

        NumberFormat format = DecimalFormat.getNumberInstance(locale);
        format.setMaximumFractionDigits(catalogDecimals);
        format.setMinimumFractionDigits(catalogDecimals);
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
            valueS = currD.getLocalCode()+ valueS;
          } else if(RefCodeNames.CURRENCY_POSITION_CD.RIGHT.equals(currD.getCurrencyPositionCd())) {
            valueS = valueS + currD.getLocalCode();
          } else { //Right with space is default position
            valueS = valueS + rightFiller + currD.getLocalCode();
          }
        }

        return valueS;

      } catch (Exception exc) {
        exc.printStackTrace();
        return "Error value: "+value;
      }
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

  public static CurrencyData getCurrency(String localeCd)
  {
     CurrencyData curr = I18nUtil.getCurrency(localeCd);
     return curr;
  }

  public static LinkedList getAllLocales()
  {
     LinkedList allLocales = I18nUtil.getAllLocales();
     return allLocales;
    }

    public static String getShoppingItemsString(HttpServletRequest request,
    ShoppingCartData shoppingCart)
    {
      return getShoppingItemsString( request,  shoppingCart, true);
    }

    public static String getShoppingItemsString(HttpServletRequest request,
    ShoppingCartData shoppingCart, boolean withUnits )
    {
        // The default locale does not work on Unix.
        String cartLocaleCd = shoppingCart.getLocaleCd();
        int qty = shoppingCart.getItemsQty();
        //int uom = shoppingCart.getItemsUom();
        int uom = shoppingCart.getTotalItemQuantity();
        Double cost = new Double(shoppingCart.getItemsCost());
        String localeCost =  getPriceShopping(request,cost,cartLocaleCd,null," ");

        String itemString = getMessage(request, "shoppingCart.text.items",null);
        String unitString = getMessage(request, "shoppingCart.text.units",null);
        //if ( qty == 1 )  itemString = "item";
        //if ( uom == 1 )  unitString = "unit";
        String unitS = (withUnits) ?  uom + " " + unitString + ", " : "";
        String itemsS = qty + " " + itemString + ", " +
            unitS + localeCost;

        return itemsS;
    }

    public static String getNewShoppingItemsString(HttpServletRequest request,
    ShoppingCartData shoppingCart)
    {
        // The default locale does not work on Unix.
        String cartLocaleCd = shoppingCart.getLocaleCd();
        int qty = shoppingCart.getNewItemsQty();
        int uom = shoppingCart.getNewItemsUom();
        Double cost = new Double(shoppingCart.getNewItemsCost());
        String localeCost =  getPriceShopping(request,cost,cartLocaleCd,null," ");
            //NumberFormat.getCurrencyInstance(Locale.US).format(cost);

        String itemString = getMessage(request, "shoppingCart.text.items",null);
        String unitString = getMessage(request, "shoppingCart.text.units",null);
        //if ( qty == 1 )  itemString = "item";
        //if ( uom == 1 )  unitString = "unit";
        String itemsS = qty + " " + itemString + ", " +
            uom + " " + unitString + ", " + localeCost;

        return itemsS;
    }
    public static String formatDate(HttpServletRequest request, Date date) {
        if (request == null || date == null) {
            return "";
        }
        Locale locale = getUserLocale(request);
        String pattern = I18nUtil.getDatePattern(locale);
        return formatDate(pattern, date);
    }
    
    public static String formatDate(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
  public static String formatDate(HttpServletRequest request,
     Date date, int style) {
     Locale locale = getUserLocale(request);
     DateFormat df = DateFormat.getDateInstance(style, locale);
     String dateS = df.format(date);
     return dateS;
  }

  /*
   * Formats date to a string, based on locale and style.
   */
  public static String formatDate(String localeCd, Date date, int style) {
  		Locale locale = Utility.parseLocaleCode(localeCd);
      DateFormat df = DateFormat.getDateInstance(style, locale);
      String dateS = df.format(date);
      return dateS;
   }

  public static Date parseDateInp(HttpServletRequest request,  String dateS)  throws ParseException
  {
     String pattern = getDatePattern(request);
     return parseDateInp(pattern, dateS);
  }

  public static Date parseDateInp(String pattern,  String dateS) throws ParseException
  {
     SimpleDateFormat sdf = new SimpleDateFormat(pattern);
     sdf.setLenient(false);
     Date date = sdf.parse(dateS);
     if (!sdf.format(date).equals(dateS) || pattern.length() != dateS.length()) {
         throw new ParseException("Invalid date value (" + dateS + ") specified", 0);
     }
     return date;
  }

  public static Date parseDateTimeInp(HttpServletRequest request,  String dateS, String pTimeS, String timeZoneCd )
  throws java.text.ParseException
  {
    return parseDateTimeInp(request, dateS, pTimeS, timeZoneCd , Constants.SIMPLE_TIME24_PATTERN);
  }
  public static Date parseDateTimeInp(HttpServletRequest request,  String dateS, String pTimeS, String timeZoneCd , String timePattern)
  throws java.text.ParseException
  {
     String timeS = (pTimeS != null) ? pTimeS : "00:00:00";
     String datePattern = getDatePattern(request);
     SimpleDateFormat sdf = new SimpleDateFormat(datePattern +" " + timePattern);
     sdf.setLenient(false);

     if (timeZoneCd != null){
       sdf.setTimeZone(TimeZone.getTimeZone(timeZoneCd));   // TO : setting format's Time Zone to sever time zone value (base)
     }
     Date date = sdf.parse(dateS+" "+timeS);

     return date;
  }

  public static String formatDateInp(HttpServletRequest request,  Date date) {
    return formatDateInp(request,  date, null);
  }

  public static String formatDateInp(HttpServletRequest request,  Date date, String timeZoneCd) {
	  if (request == null || date == null) {
          return "";
	  }
     String pattern = getDatePattern(request);
     SimpleDateFormat sdf = new SimpleDateFormat(pattern);
     if (timeZoneCd != null) {
       sdf.setTimeZone(TimeZone.getTimeZone(timeZoneCd));
     }
     String dateS = sdf.format(date);

     return dateS;
  }

  public static String getDatePattern(HttpServletRequest request) {
     String pattern = null;
     try {
       pattern = getCountryDateFormat(request);
     } catch (Exception exc){}
     if(!Utility.isSet(pattern)|| pattern.startsWith("@")) {
       Locale locale = getUserLocale(request);
       pattern = I18nUtil.getDatePattern(locale);
     }
     return pattern;
  }

    public static String getDateExampleStr(HttpServletRequest request) {
        Locale locale = getUserLocale(request);
        String pattern = getDatePattern(request);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        DateFormat ldf = DateFormat.getDateInstance(DateFormat.LONG, locale);
        String example = sdf.format(exampleDate) + " (" + ldf.format(exampleDate) + ")";

        return example;
    }

    public static Locale getUserLocale(HttpServletRequest request) {

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        Locale locale = null;

        if (appUser != null) {
            locale = appUser.getPrefLocale();
        }

        if (locale == null) {
            locale = request.getLocale();
        }

        return locale;
    }

	public static String getMessage(HttpServletRequest request, String key) {
		return getMessage(request, key, null);
	}

	public static String getMessage(HttpServletRequest request, String key, Object[] args) {
		return getMessage(request,key,args, false );
	}

	/*
	 * Returns i18n Value for the passed key.
	 */
	public static String getMessage(HttpServletRequest request, String key, Object[] args, boolean pReturnNullIfNotFound ) {
		String message = null;
	
		if(ClwMessageResourcesImpl.isKeySet(request,key)) {
			MessageResources messages = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
			if(messages instanceof ClwMessageResourcesImpl) {
				ClwMessageResourcesImpl clwMessages = (ClwMessageResourcesImpl)messages;
	    		message = clwMessages.getMessage(request,key,args);
			}
    	} 
		
		if(message == null && !pReturnNullIfNotFound) {
			return "???? "+key;
		}
		return message;
	}

    public static String getMessageOrNull
  (HttpServletRequest request, String key ) {
  return getMessage(request,key, null, true );
    }

    public static String formatEjbError(HttpServletRequest request, String ejbError) {
      String retStr = "";
      if(ejbError==null) return retStr;
      int pos1 = ejbError.indexOf("^clwKey^");
      if(pos1>=0) {
        pos1+=8;
        int pos2 = ejbError.indexOf("^clwKey^",pos1);
        if(pos2>0) {
          int paramQty = 0;
          String key = ejbError.substring(pos1,pos2).trim();
          Object[] params = new Object[4];
          pos2 += 8;
          while(paramQty<4) {
            pos1 = ejbError.indexOf("^clwParam^",pos2);
            if(pos1>=0) {
              pos1 += 10;
              pos2 = ejbError.indexOf("^clwParam^",pos1);
              if(pos2>pos1) {
                params[paramQty] = ejbError.substring(pos1,pos2).trim();
                pos2 += 10;
                paramQty++;
                continue;
              }
            }
            break;
          }
          return getMessage(request,key,params);
        }
      } else {
          int ind1 = ejbError.indexOf("^clw^");
          if(ind1>=0) {
              ind1 += 5;
              int ind2 = ejbError.indexOf("^clw^",ind1);
              if(ind2<0) {
                retStr = ejbError.substring(ind1);
              } else {
                retStr = ejbError.substring(ind1,ind2);
              }
          }
      }
      return Utility.encodeForHTML(retStr);
    }

    public static String formatDate(HttpServletRequest request, String value,int format) {
        try {
            Date date=parseDateInp(request,value);
            return formatDate(request,date,format);
        } catch (Exception e) {
            e.printStackTrace();
            String mess=getMessage(request,"shop.errors.wrongDateFormat",new Object[]{value},true);
            return mess==null?("Wrong date format : "+value):mess;
        }
    }

    public static String convertDateToLocale(HttpServletRequest request,
                                             Locale locale,
                                             String dateValue) {
        if (Utility.isSet(dateValue)) {
            try {
                Date date = ClwI18nUtil.parseDateInp(request, dateValue);
                String pattern = I18nUtil.getDatePattern(locale);
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                return sdf.format(date);
            } catch (ParseException e) { }
        }

        return dateValue;
    }

    public static String convertDateToRequest(Locale locale,
                                              HttpServletRequest request,
                                              String dateValue) {
        if (Utility.isSet(dateValue)) {
            try {
                String pattern = I18nUtil.getDatePattern(locale);
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                Date date = sdf.parse(dateValue);
                return ClwI18nUtil.formatDateInp(request, date);
            } catch (ParseException e) {}
        }
        return dateValue;
    }

    public static String getPriceShoppingNegative(HttpServletRequest request, Object value, String rightFiller ) {
      HttpSession session = request.getSession();
      String totWCartS = ClwI18nUtil.getPriceShopping(request,value,"&nbsp;");
      HashMap localeCurrencyHM = I18nUtil.getCurrencyHashMap();
      String valueLocaleCd = (String) session.getAttribute(Constants.CATALOG_LOCALE);
      if (valueLocaleCd == null) {
        valueLocaleCd = "en_US";
      }
      
      //STJ-3115
      String countyCode = Utility.getCountryCodeFromLocale(valueLocaleCd);
      //CurrencyData valueCurrD = (CurrencyData) localeCurrencyHM.get(valueLocaleCd);
      CurrencyData valueCurrD = (CurrencyData) localeCurrencyHM.get(countyCode);
      
      String origS = valueCurrD.getLocalCode() + "-";
      String replS = "-" + valueCurrD.getLocalCode();

      if (RefCodeNames.CURRENCY_POSITION_CD.LEFT.equals(valueCurrD.getCurrencyPositionCd())) {
        if (totWCartS.startsWith(origS)) {
          totWCartS = replS + totWCartS.substring(totWCartS.lastIndexOf(origS) + 2);
        }
      }
      return totWCartS;
    }

    public static String formatInvoiceCurrency(HttpServletRequest request, Object value, Object poObj){
       PurchaseOrderStatusDescDataView po = (PurchaseOrderStatusDescDataView)poObj;
        String locale=null;
        if(po != null && po.getOrderData() != null){
    	   locale = po.getOrderData().getLocaleCd();
        }
    	if(locale == null){
    		locale = "noLocale";
    	}
    	String str = getPriceShopping(request, value, locale, null, " ");
    	return str;
    }

    public static String getCurrencyGlobalCode(HttpServletRequest request){
        HashMap localeCurrencyHM = I18nUtil.getCurrencyHashMap();
        HttpSession session = request.getSession();
        String valueLocaleCd = (String) session.getAttribute(Constants.CATALOG_LOCALE);
        if (valueLocaleCd == null) {
            valueLocaleCd = "en_US";
        }
        //STJ-3115
        String countyCode = Utility.getCountryCodeFromLocale(valueLocaleCd);
        //CurrencyData valueCurrD = (CurrencyData) localeCurrencyHM.get(valueLocaleCd);
        CurrencyData valueCurrD = (CurrencyData) localeCurrencyHM.get(countyCode);
        
        return valueCurrD.getGlobalCode();
    }

    public static String getPriceForPaymetric
        (HttpServletRequest request, BigDecimal value,
         String valueLocaleCd, Integer valueDecimals)
    {
        try {
          HashMap localeCurrencyHM = I18nUtil.getCurrencyHashMap();
          if(value==null) {
            return "";
          }
          HttpSession session = request.getSession();
          int decimals = -1;
          if(valueDecimals==null) {
            Integer catalogDecimalsI = (Integer) session.getAttribute(Constants.CATALOG_DECIMALS);
            if (catalogDecimalsI!=null) decimals = catalogDecimalsI.intValue();
          } else {
            decimals = valueDecimals.intValue();
          }
          if("noLocale".equalsIgnoreCase(valueLocaleCd)){
                  valueLocaleCd = "en_US";
          }

          if(valueLocaleCd==null) {
            valueLocaleCd = (String) session.getAttribute(Constants.CATALOG_LOCALE);
          }

          if(valueLocaleCd==null) {
              valueLocaleCd = "en_US";
          }

          //STJ-3115
          String countyCode = Utility.getCountryCodeFromLocale(valueLocaleCd);
          //CurrencyData valueCurrD = (CurrencyData) localeCurrencyHM.get(valueLocaleCd);
          CurrencyData valueCurrD = (CurrencyData) localeCurrencyHM.get(countyCode);
          
          String valueCurrencyCd = valueCurrD.getGlobalCode();

          // user currency

          if(decimals==-1) {
              decimals=valueCurrD.getDecimals();
              if(decimals==-1) {
                  Currency curr = Currency.getInstance(valueCurrencyCd);
                  decimals = curr.getDefaultFractionDigits();
              }
          }

          NumberFormat format = new DecimalFormat("#0.0#");
          format.setMaximumFractionDigits(decimals);
          format.setMinimumFractionDigits(decimals);
          double valueDbl = 0;
          valueDbl = ((BigDecimal)value).doubleValue();

          String valueS = format.format(valueDbl);

          return valueS;

        } catch (Exception exc) {
          exc.printStackTrace();
          String mess=getMessage(request,"shop.errors.priceFormat",new Object[]{value},true);
          return mess==null?("Error value: "+value):mess;
        }
      }

      public static String formatTimeInp(HttpServletRequest request,  Date date) {
         return formatTimeInp(request,  date, null);
       }

       public static String formatTimeInp(HttpServletRequest request,  Date date, String timeZoneCd) {
           if (request == null || date == null) {
               return "";
           }
          String pattern = getTimePattern(request);
           SimpleDateFormat sdf = new SimpleDateFormat(pattern);

          if (timeZoneCd != null) {
            sdf.setTimeZone(TimeZone.getTimeZone(timeZoneCd));
          }
          String timeS = sdf.format(date);

          return timeS;
       }

       public static String getTimePattern(HttpServletRequest request) {
          String pattern = null;
          try {
            pattern = getCountryTimeFormat(request);
          } catch (Exception exc){}
          if(!Utility.isSet(pattern)){
            pattern = Constants.SIMPLE_TIME24_PATTERN1;
          }
          return pattern;
       }
       public static String formatTime(HttpServletRequest request, String dateValue,String timeValue) {
           try {
               Date date=ClwI18nUtil.parseDateTimeInp(request, dateValue, timeValue, null, Constants.SIMPLE_TIME_PATTERN);;
               return formatTimeInp(request,date);
           } catch (Exception e) {
               e.printStackTrace();
               String mess=getMessage(request,"userWorkOrder.errors.wrongTimeFormat",new Object[]{timeValue},true);
               return mess==null?("Wrong time format : "+timeValue):mess;
           }
       }
       public static boolean isValidTimeString (String timeS, String pattern) {
         boolean valid = false;
         java.util.regex.Pattern timeP =  java.util.regex.Pattern.compile("([0-9]{2}[\\:][0-9]{2})");
         if (pattern != null) {
           if (Constants.SIMPLE_TIME24_PATTERN.equals(pattern)){
             timeP = java.util.regex.Pattern.compile("([0-9]{1,2}[\\:][0-9]{2}[\\:][0-9]{2})");
           } else if (Constants.SIMPLE_TIME24_PATTERN1.equals(pattern)) {
             timeP =  java.util.regex.Pattern.compile("([0-9]{1,2}[\\:][0-9]{2})");
           } else if (Constants.SIMPLE_TIME_PATTERN.equals(pattern)){
             timeP =  java.util.regex.Pattern.compile("([0-9]{1,2}[\\:][0-9]{2}[\\s][ap]m)");
           }
         }
         if (timeP.matcher(timeS).matches()){
           String[] reg = timeS.split(":");
           if (Integer.parseInt(reg[0]) < 24 || Integer.parseInt(reg[1].substring(0,2))<60) {
             valid = true;
           }
         }
         return valid;
      }

    /**
     * Method to return an address in "short" format
     * @param address - an <code>AddressData</code> containing the address information
     * @param countryProperties - a <code>CountryPropertyDataVector</code> containing information about
     * 		what address related properties are required for a given country.
     * @return List<String> - a <code>List</code> containing the formatted address in the order
     * 		in which the information should appear.
     */
    public static List<String> formatAddressShort(AddressData address, CountryPropertyDataVector countryProperties) {
    	return formatAddressShort(address, countryProperties, null);
    }

    /**
     * Method to return an address in "short" format
     * @param address - an <code>AddressData</code> containing the address information
     * @param countryProperties - a <code>CountryPropertyDataVector</code> containing information about
     * 		what address related properties are required for a given country.
     * @param addressDescription - an optional <code>String</code> containing the name of the entity 
     * 		at the specified address.
     * @return List<String> - a <code>List</code> containing the formatted address in the order
     * 		in which the information should appear.
     */
    public static List<String> formatAddressShort(AddressData address, CountryPropertyDataVector countryProperties, String addressDescription) {
    	ArrayList<String> returnValue = new ArrayList<String>();
    	if (address != null) {
			if (Utility.isSet(addressDescription)) {
				returnValue.add(addressDescription);
			}
			//although a location can have up to four address lines, it
			//was decided for short address format to include just the first.
			if (Utility.isSet(address.getAddress1())) {
				returnValue.add(address.getAddress1());
			}
			//return city, state, and zip on one line
			String cszLine = getCityStateZipInfo(address,countryProperties);
	    	returnValue.add(cszLine);
	    	
    	}
    	return returnValue;
    }
    
    /**
     * Gets City, State and Zip info on one line
     * @param address - an <code>AddressData</code> containing the address information
     * @param countryProperties - a <code>CountryPropertyDataVector</code> containing information about
     * 		what address related properties are required for a given country.
     * @return String - city,state,zip 
     */
    public static String getCityStateZipInfo(AddressData address, CountryPropertyDataVector countryProperties){
    	StringBuilder cszLine = new StringBuilder(50);
    	if (Utility.isSet(address.getCity())) {
    		cszLine.append(address.getCity());
    	}
    	if (countryProperties != null) {
    	    if (Utility.isTrue(Utility.getCountryPropertyValue(countryProperties, RefCodeNames.COUNTRY_PROPERTY.USES_STATE))) {
    	    	if (Utility.isSet(address.getStateProvinceCd())) {
    	    		if (cszLine.length() > 0) {
    	    			cszLine.append(", ");
    	    		}
    	    		cszLine.append(address.getStateProvinceCd());
    	    	}
    	    }
    	}
    	if (Utility.isSet(address.getPostalCode())) {
    		if (cszLine.length() > 0) {
    			cszLine.append(" ");
    		}
    		cszLine.append(address.getPostalCode());
    	}
    	return cszLine.toString();
    }
    
    /**
     * Formats and returns address 
     * @param address
     * @param countryProperties - a <code>CountryPropertyDataVector</code> containing information about
     * 		what address related properties are required for a given country.
     * @return List<String> - a <code>List</code> containing the formatted address in the order
     * 		in which the information should appear.
     */
    public static List<String> formatAddress(AddressData address, CountryPropertyDataVector countryProperties) {
    	ArrayList<String> returnValue = new ArrayList<String>();
    	if (address != null) {
    		if (Utility.isSet(address.getAddress1())) {
				returnValue.add(address.getAddress1());
			}
    		if (Utility.isSet(address.getAddress2())) {
				returnValue.add(address.getAddress2());
			}
    		if (Utility.isSet(address.getAddress3())) {
				returnValue.add(address.getAddress3());
			}
    		if (Utility.isSet(address.getAddress4())) {
				returnValue.add(address.getAddress4());
			}
    		
			//return city, state, and zip on one line
			String cszLine = getCityStateZipInfo(address,countryProperties);
	    	returnValue.add(cszLine);
    	}
    	return returnValue;
    }
    
    /**
     * Formats and returns address 
     * @param address
     * @param countryProperties - a <code>CountryPropertyDataVector</code> containing information about
     * 		what address related properties are required for a given country.
     * @return List<String> - a <code>List</code> containing the formatted address in the order
     * 		in which the information should appear.
     */
    public static List<String> formatAddressWithCountryCd(AddressData address, CountryPropertyDataVector countryProperties) {
    	ArrayList<String> returnValue = new ArrayList<String>();
    	if (address != null) {
    		if (Utility.isSet(address.getAddress1())) {
				returnValue.add(address.getAddress1());
			}
    		if (Utility.isSet(address.getAddress2())) {
				returnValue.add(address.getAddress2());
			}
    		if (Utility.isSet(address.getAddress3())) {
				returnValue.add(address.getAddress3());
			}
    		if (Utility.isSet(address.getAddress4())) {
				returnValue.add(address.getAddress4());
			}
    		
			//return city, state, and zip on one line
			String cszLine = getCityStateZipInfo(address,countryProperties);
	    	returnValue.add(cszLine);
    		if (Utility.isSet(address.getCountryCd())) {
				returnValue.add(address.getCountryCd());
			}
    	}
    	return returnValue;
    }
    
    /**
     * Method to return the list of product search field choices
     * @param request - the <code>HttpServletRequest</code> currently being handled
     * @return List<LableValueBean> - a <code>List</code> of <code>LabelValueBean</code>
     * 		objects, each representing a single product search field choice.
     */
    public static List<LabelValueBean> getProductSearchFieldChoices(HttpServletRequest request) {
    	//TODO - the list of search field choices needs to be verified.  The dev spec says that
    	//development will examine the Xpedx list of fields.  Any fields below that are removed should
    	//also have their labels removed from the message resource file and their values removed from
    	//the Constants file (make sure they aren't used elsewhere first).
    	List<LabelValueBean> returnValue = new ArrayList<LabelValueBean>();
    	returnValue.add(new LabelValueBean(getMessage(request, "product.search.choice.all", null), Constants.PRODUCT_SEARCH_VALUE_ALL));
    	returnValue.add(new LabelValueBean(getMessage(request, "product.search.choice.manufacturer", null), Constants.PRODUCT_SEARCH_VALUE_MANUFACTURER));
    	returnValue.add(new LabelValueBean(getMessage(request, "product.search.choice.manufSku", null), Constants.PRODUCT_SEARCH_VALUE_MANUF_SKU));
    	returnValue.add(new LabelValueBean(getMessage(request, "product.search.choice.productName", null), Constants.PRODUCT_SEARCH_VALUE_PRODUCT_NAME));
    	returnValue.add(new LabelValueBean(getMessage(request, "product.search.choice.sku", null), Constants.PRODUCT_SEARCH_VALUE_SKU));
    	
    	return returnValue;
    }
    
    /**
     * Method to return the list of status field choices
     * @param request - the <code>HttpServletRequest</code> currently being handled
     * @return List<LableValueBean> - a <code>List</code> of <code>LabelValueBean</code>
     * 		objects, each representing a single previous order time period choice.
     */
	public static List<LabelValueBean> getOrdersStatusFieldChoices(HttpServletRequest request) {
    	List<LabelValueBean> returnValue = new ArrayList<LabelValueBean>();
    	
    	returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.all", null), Constants.ALL));
    	returnValue.add(new LabelValueBean(getMessage(request,"orders.filterPane.dropdown.label.cancelled", null),RefCodeNames.ORDER_STATUS_CD.CANCELLED));
    	returnValue.add(new LabelValueBean(getMessage(request,"orders.filterPane.dropdown.label.invoiced", null), RefCodeNames.ORDER_STATUS_CD.INVOICED));
    	returnValue.add(new LabelValueBean(getMessage(request,"orders.filterPane.dropdown.label.onHold", null), RefCodeNames.ORDER_STATUS_CD.ON_HOLD));
    	returnValue.add(new LabelValueBean(getMessage(request,"orders.filterPane.dropdown.label.ordered", null), RefCodeNames.ORDER_STATUS_CD.ORDERED));
    	returnValue.add(new LabelValueBean(getMessage(request,"orders.filterPane.dropdown.label.shipped", null), RefCodeNames.ORDER_STATUS_CD.SHIPPED));    	
    	returnValue.add(new LabelValueBean(getMessage(request,"orders.filterPane.dropdown.label.orderedProcessing", null), RefCodeNames.ORDER_STATUS_CD.ORDERED_PROCESSING));
    	returnValue.add(new LabelValueBean(getMessage(request,"orders.filterPane.dropdown.label.pendingApproval", null), RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL));
    	returnValue.add(new LabelValueBean(getMessage(request,"orders.filterPane.dropdown.label.pendingConsolidation", null), RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION));
    	returnValue.add(new LabelValueBean(getMessage(request,"orders.filterPane.dropdown.label.pendingDate", null), RefCodeNames.ORDER_STATUS_CD.PENDING_DATE));
    	returnValue.add(new LabelValueBean(getMessage(request,"orders.filterPane.dropdown.label.rejected", null), RefCodeNames.ORDER_STATUS_CD.REJECTED));    	
    	returnValue.add(new LabelValueBean(getMessage(request,"orders.filterPane.dropdown.label.shipmentReceived", null), RefCodeNames.ORDER_STATUS_CD.SHIPMENT_RECEIVED));
    	
    	return returnValue;
    }
    
    /**
     * Method to return the list of date range choices
     * @param request - the <code>HttpServletRequest</code> currently being handled
     * @return List<LableValueBean> - a <code>List</code> of <code>LabelValueBean</code>
     * 		objects, each representing a single previous order time period choice.
     */
    public static List<LabelValueBean> getOrdersDateRangeFieldChoices(HttpServletRequest request) {
	 	//TODO - the list of search field choices needs to be verified.  
		List<LabelValueBean> returnValue = new ArrayList<LabelValueBean>();
		SiteData currentLocation = ShopTool.getCurrentSite(request);
    	if (ShopTool.doesAnyAccountSupportsBudgets(request) &&
    	    currentLocation != null &&
    	    Utility.isSet(currentLocation.getBudgetPeriods())) {
        	  returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.lastPeriod", null), Constants.DATE_RANGE_LAST_PERIOD));
    		  returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.currentPeriod", null), Constants.DATE_RANGE_CURRENT_PERIOD));
    	}
		
		returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.30Days", null), Constants.DATE_RANGE_30_DAYS));
		returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.60Days", null), Constants.DATE_RANGE_60_DAYS));
		returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.90Days", null), Constants.DATE_RANGE_90_DAYS));
		returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.customRange", null), Constants.DATE_RANGE_CUSTOM_RANGE));
	 	return returnValue;
	 }

    /**
     * Method to return the list of previous order time period choices
     * @param request - the <code>HttpServletRequest</code> currently being handled
     * @return List<LableValueBean> - a <code>List</code> of <code>LabelValueBean</code>
     * 		objects, each representing a single previous order time period choice.
     */
    public static List<LabelValueBean> getPreviousOrderDateRangeChoices(HttpServletRequest request) {
    	List<LabelValueBean> returnValue = new ArrayList<LabelValueBean>();
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	if (ShopTool.doesAnyAccountSupportsBudgets(request) &&
    	    currentLocation != null &&
    	    Utility.isSet(currentLocation.getBudgetPeriods())) {
        	  returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.currentPeriod", null), Constants.PREVIOUS_ORDER_DATE_RANGE_VALUE_THIS_PERIOD));
        	  returnValue.add(new LabelValueBean(getMessage(request, "previousOrder.dateRange.choice.lastPeriod", null), Constants.PREVIOUS_ORDER_DATE_RANGE_VALUE_LAST_PERIOD));
    	}
    	returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.30Days", null), Constants.PREVIOUS_ORDER_DATE_RANGE_VALUE_LAST_THIRTY_DAYS));
    	returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.60Days", null), Constants.PREVIOUS_ORDER_DATE_RANGE_VALUE_LAST_SIXTY_DAYS));
    	returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.90Days", null), Constants.PREVIOUS_ORDER_DATE_RANGE_VALUE_LAST_NINETY_DAYS));
    	return returnValue;
    }
    
 
    /**
     * Method to return the list of future order time period choices
     * @param request - the <code>HttpServletRequest</code> currently being handled
     * @return List<LableValueBean> - a <code>List</code> of <code>LabelValueBean</code>
     * 		objects, each representing a single previous order time period choice.
     */
    public static List<LabelValueBean> getFutureOrdersDateRangeChoices(HttpServletRequest request) {
    	List<LabelValueBean> returnValue = new ArrayList<LabelValueBean>();
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	if (ShopTool.doesAnyAccountSupportsBudgets(request) &&
    	    currentLocation != null &&
    	    Utility.isSet(currentLocation.getBudgetPeriods())) {
        	  returnValue.add(new LabelValueBean(getMessage(request, "futureOrder.dateRange.choice.nextPeriod", null), Constants.FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_PERIOD));
        	  returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.currentPeriod", null), Constants.FUTURE_ORDER_DATE_RANGE_VALUE_CURRENT_PERIOD));
    	}
    	returnValue.add(new LabelValueBean(getMessage(request, "futureOrder.dateRange.choice.nextThirtyDays", null), Constants.FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_THIRTY_DAYS));
    	returnValue.add(new LabelValueBean(getMessage(request, "futureOrder.dateRange.choice.nextSixtyDays", null), Constants.FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_SIXTY_DAYS));
    	returnValue.add(new LabelValueBean(getMessage(request, "futureOrder.dateRange.choice.nextNinetyDays", null), Constants.FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_NINETY_DAYS));
    	return returnValue;
    }
    
    public static List<LabelValueBean> getReportingDateRangeFieldChoices(HttpServletRequest request) {
	 	//TODO - the list of search field choices needs to be verified.  
		List<LabelValueBean> returnValue = new ArrayList<LabelValueBean>();
		SiteData currentLocation = ShopTool.getCurrentSite(request);
    	if (ShopTool.doesAnyAccountSupportsBudgets(request) &&
    	    currentLocation != null &&
    	    Utility.isSet(currentLocation.getBudgetPeriods())) {
    		  returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.currentPeriod", null), Constants.REPORTING_FILTER_THIS_PERIOD));
        	  returnValue.add(new LabelValueBean(getMessage(request, "reporting.filter.lastPeriod", null), Constants.REPORTING_FILTER_LAST_PERIOD));
    	}
		returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.30Days", null), Constants.DATE_RANGE_30_DAYS));
		returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.60Days", null), Constants.DATE_RANGE_60_DAYS));
		returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.90Days", null), Constants.DATE_RANGE_90_DAYS));
		returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.customRange", null), Constants.DATE_RANGE_CUSTOM_RANGE));
	 	return returnValue;
    }
    
    /**
     * Method to return the list of budgets report fiscal period filter choices
     * @param request - the <code>HttpServletRequest</code> currently being handled
     * @return List<LableValueBean> - a <code>List</code> of <code>LabelValueBean</code>
     */
    public static List<LabelValueBean> getFiscalPeriodFilterChoices(HttpServletRequest request) {
    	List<LabelValueBean> returnValue = new ArrayList<LabelValueBean>();
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	if (ShopTool.doesAnyAccountSupportsBudgets(request) &&
    	    currentLocation != null &&
    	    Utility.isSet(currentLocation.getBudgetPeriods())) {
    		  returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.dropdown.label.currentPeriod", null), Constants.REPORTING_FILTER_THIS_PERIOD));
        	  returnValue.add(new LabelValueBean(getMessage(request, "reporting.filter.lastPeriod", null), Constants.REPORTING_FILTER_LAST_PERIOD));
    	}
    	returnValue.add(new LabelValueBean(getMessage(request, "reporting.filter.thisFiscalYear", null), Constants.REPORTING_FILTER_THIS_FISCAL_YEAR));
    	returnValue.add(new LabelValueBean(getMessage(request, "reporting.filter.lastFiscalYear", null), Constants.REPORTING_FILTER_LAST_FISCAL_YEAR));
    	
    	return returnValue;
    }

   
    /**
     * Method to return the currency format based on catalog locale
     * @param request - HttpServletRequest,
     * @param valueLocaleCd- String
     * @param valueDecimals- Integer
     * @param rightFiller- String
     * @return String[]
     */
    public static String[] getCurrencyAlignment(HttpServletRequest request,String valueLocaleCd, 
    		Integer valueDecimals, String rightFiller )
    {
		String[] formats = new String[3];
		try {
			HashMap localeCurrencyHM = I18nUtil.getCurrencyHashMap();
			HttpSession session = request.getSession();
			boolean noLocale = false;
			if ("noLocale".equalsIgnoreCase(valueLocaleCd)) {
				valueLocaleCd = "en_US";
				noLocale = true;
			}
			if (valueLocaleCd == null) {
				valueLocaleCd = (String) session
						.getAttribute(Constants.CATALOG_LOCALE);
			}
			if (valueLocaleCd == null) {
				valueLocaleCd = "en_US";
			}
			// STJ-3115
			String countyCode = Utility.getCountryCodeFromLocale(valueLocaleCd);
			CurrencyData valueCurrD = (CurrencyData) localeCurrencyHM
					.get(countyCode);
			String valueCurrencyCd = valueCurrD.getGlobalCode();
			// user currency
			Locale userLocale = getUserLocale(request);
			// STJ-3115
			String userCountyCode = Utility.getCountryCodeFromLocale(userLocale
					.toString());
			// CurrencyData userCurrD = (CurrencyData)
			// localeCurrencyHM.get(userLocale.toString());
			CurrencyData userCurrD = (CurrencyData) localeCurrencyHM
					.get(userCountyCode);
			if (!noLocale) {
				if (userCurrD == null
						|| !userCurrD.getGlobalCode().equalsIgnoreCase(
								valueCurrencyCd)) {
					formats[0] = "localeCodeOrPriceCurrency";
					formats[1] = rightFiller + valueCurrencyCd;
					// amount currency : 10 GBP
				} else {
					if (RefCodeNames.CURRENCY_POSITION_CD.LEFT
							.equals(valueCurrD.getCurrencyPositionCd())) {
						formats[0] = "left";
						formats[1] = valueCurrD.getLocalCode();
						// return formats; // currencyamount : $10
					} else if (RefCodeNames.CURRENCY_POSITION_CD.RIGHT
							.equals(valueCurrD.getCurrencyPositionCd())) {
						formats[0] = "right";
						formats[1] = valueCurrD.getLocalCode();
						// formats;// 10currency : 10$
					} else { // Right with space is default position
						formats[0] = "localeCodeOrPriceCurrency";
						formats[1] = rightFiller + valueCurrD.getLocalCode();
						// formats;//10 "" currency : 10 $
					}
				}
			}
			formats[2] = valueCurrencyCd;
			return formats;
		} catch (Exception exc) {
			log.error("Unable to retrieve locale : ", exc);
			return formats;
	    }
  }
    
    /**
     * Method to return the NumberFormat which is used in displaying charts using High Charts
     * @param request - HttpServletRequest,
     * @return NumberFormat
     */
    public static NumberFormat getDecimalFormatForHC(HttpServletRequest request){
		String valueLocaleCd = (String) request.getSession().getAttribute(
				Constants.CATALOG_LOCALE);
		 if(valueLocaleCd==null) {
	          valueLocaleCd = "en_US";
	      }
		String countyCode = Utility.getCountryCodeFromLocale(valueLocaleCd);
		HashMap localeCurrencyHM = I18nUtil.getCurrencyHashMap();
		CurrencyData valueCurrD = (CurrencyData) localeCurrencyHM
				.get(countyCode);
		String valueCurrencyCd = valueCurrD.getGlobalCode();
		NumberFormat formatter = null;
		if (valueCurrencyCd.equalsIgnoreCase("USD-4")) {//for en_XXPO
			formatter = new DecimalFormat("#0.0000");
		} else {
			formatter = new DecimalFormat("#0.00");
		}
		formatter.setGroupingUsed(false);
		return formatter;
    }
    
    /**
     * Method to return the data for location drop down choices
     * @param request - the <code>HttpServletRequest</code> currently being handled
     * @return List<LableValueBean> - a <code>List</code> of <code>LabelValueBean</code>
     * 		objects, each representing a single previous order time period choice.
     */
    public static List<LabelValueBean> getLocationChoices(HttpServletRequest request) {
		List<LabelValueBean> returnValue = new ArrayList<LabelValueBean>();
		
		returnValue.add(new LabelValueBean(getMessage(request, "orders.filterPane.label.flyOut.currentLocation", null), Constants.ORDERS_CURRENT_LOCATION));
	 	return returnValue;
	 }
    /**
     * It returns decimal separator and grouping separator based on catalog locale.
     * @param request
     * @param valueLocaleCd
     * @return ArrayList
     */
    public static ArrayList<Character> getDecimalAndGroupingSeparator(HttpServletRequest request, String valueLocaleCd ) {
    	HttpSession session = request.getSession();
		ArrayList<Character> numberSeparators = new ArrayList<Character>();
    try {
		Locale locale = getUserLocale(request);
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(locale);
		numberSeparators.add(decimalFormatSymbols.getDecimalSeparator());
		numberSeparators.add(decimalFormatSymbols.getGroupingSeparator());
    	} catch (Exception exc) {
        
    }
    return numberSeparators;
   }
    
    /**
     * Calculates number of days between given start and end dates.
     * @param request
     * @param startDate - begin Date (Date object)
     * @param endDate - end Date (Date object)
     * @return - number days between give two dates.
     */
    public static long calculateDaysBetween(HttpServletRequest request, Date startDate, Date endDate) {
    	
    	long oneHour = 60 * 60 * 1000L;
 	   
    	Locale locale = getUserLocale(request);
        String pattern = I18nUtil.getDatePattern(locale);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        
        Date sDate;
        Date eDate;
		try {
			sDate = sdf.parse(sdf.format(startDate));
			eDate = sdf.parse(sdf.format(endDate));
		} catch (ParseException e) {
			log.error("Error while calculating days between start date and end date, Details :"+e.getMessage());
			sDate = startDate;
			eDate = endDate;
		}
    	return ((eDate.getTime() - sDate.getTime() + oneHour) / (oneHour * 24));
   }
    
    /**
     * Method to return the list of payment field choices
     * @param request - the <code>HttpServletRequest</code> currently being handled
     * @return List<LableValueBean> - a <code>List</code> of <code>LabelValueBean</code>
     * 		objects, each representing a single previous order time period choice.
     */
    public static List<LabelValueBean> getPaymentTypeChoices(HttpServletRequest request) {
    	//NOTE: This method was written for New UI, the reference for this method is 
    	//baselinestorepages/checkoutTemplate.jsp
    	CleanwiseUser user = ShopTool.getCurrentUser(request);    	
    	List<LabelValueBean> returnValue = new ArrayList<LabelValueBean>();    	
    	boolean payMetricsCreditCard = Boolean.parseBoolean(String.valueOf(request.getSession(false).getAttribute(Constants.PAYMETRICS_CC)));
    	
    	if (user.getCreditCardFlag() ) {
    		returnValue.add(new LabelValueBean(getMessage(request, "shop.checkout.text.creditCard"),Constants.CHECK_OUT_PAYMENT_CREDIT_CARD));
    	}
    	if (user.getOnAccount() && !payMetricsCreditCard) {
    		returnValue.add(new LabelValueBean(getMessage(request, "shop.orderdetail.text.onAccount"),Constants.CHECK_OUT_PAYMENT_TYPE_PO));
    	}
		if (user.getOtherPaymentFlag() && !payMetricsCreditCard) {
			returnValue.add(new LabelValueBean(getMessage(request, "shop.checkout.text.recordOfCall"),Constants.CHECK_OUT_PAYMENT_OTHER));
		}
    	
    	
    	return returnValue;
    }
    
    /**
     * Method to return the list of payment field choices
     * @param request - the <code>HttpServletRequest</code> currently being handled
     * @param osv - the <code>OrderScheduleView</code> for which the name is being formatted
     * @param nameDateSeperator - a <code>String</code> containing the character(s) used to seperate
     * 	the schedule name from its start/end dates
     * @return String - a <code>String</code> containing the order schedule name.
     */
    public static String formatOrderScheduleName(HttpServletRequest request, OrderScheduleView osv, String nameDateSeperator) {
		StringBuilder scheduleName = new StringBuilder(50);
		scheduleName.append(osv.getOrderGuideName());
		Date startDate = osv.getEffDate();
		Date endDate = osv.getExpDate();
		if (startDate != null || endDate != null) {
			scheduleName.append(nameDateSeperator);
			scheduleName.append("(");
			if (startDate != null) {
				scheduleName.append(ClwI18nUtil.formatDateInp(request, startDate));
			}
			else {
				scheduleName.append("...");
			}
			scheduleName.append(" - ");
			if (endDate != null) {
				scheduleName.append(ClwI18nUtil.formatDateInp(request, endDate));
			}
			else {
				scheduleName.append("...");
			}
			scheduleName.append(")");
		}
		return scheduleName.toString();
    }
    
    /**
     * Method to return today's date in the format of the user's locale
     * @param request - the <code>HttpServletRequest</code> currently being handled
     * @return String - a <code>String</code> containing today's date in the format of the user's locale
     */ 
    public static String getTodaysDate(HttpServletRequest request) {
    	 Date dNow = new Date();
		 //log.info("***dNow = " + dNow.toString());
		 String patternAD  = ClwI18nUtil.getDatePattern(request);
		 //log.info("***patternAD = " + patternAD);
		 SimpleDateFormat formatterAD = new SimpleDateFormat(patternAD);
		 String todaysDate = formatterAD.format(dNow);
		 //log.info("***todaysDate = " + todaysDate);
		 
		 return todaysDate;
    }
    
	/**
	 * Formats Date and Time info according to the user locale.
	 * @param request - the <code>HttpServletRequest</code> currently being handled
	 * @param date - the <code>Date</code> that needs to be formatted.
	 * @return dateTime - the Date with Time
	 */
    public static String formatDateTime(HttpServletRequest request, Date date) {
		String dateTime = "";
		if (request != null && date != null) {
			String timePattern = getTimePattern(request);
			String datePattern = getDatePattern(request);
			SimpleDateFormat sdf = new SimpleDateFormat(datePattern+" "+timePattern);
			
			dateTime = sdf.format(date);
		}
		return dateTime;
	}
    

}

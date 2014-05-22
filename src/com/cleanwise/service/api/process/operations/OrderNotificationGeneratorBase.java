package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.value.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import com.cleanwise.service.api.util.Utility;
import java.text.NumberFormat;
import java.math.BigDecimal;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import com.cleanwise.service.api.util.RefCodeNames;


public abstract class OrderNotificationGeneratorBase extends DocBuilder implements FileGenerator {

    private static final String className = "OrderNotificationGenerator";
    public NumberFormat currencyFmt ;
    public String userLocale ;
    protected String fileType = FileGenerator.TXT_FILE;// can be override by extended class
    protected String text= "Order has been placed.";

    public OrderNotificationGeneratorBase(){
    	fileType = FileGenerator.TXT_FILE;
    }
    public String generate(Object data, String fileName) throws Exception {
        int ix = fileName.indexOf(".");
        if (ix < 0){
        	fileName += getFileExtension();
        }

        File file = new File(fileName);
        return generate(data, file);
    }
        
    public String generate(Object data, File file) throws Exception {

        if (data instanceof Integer) {
            return generateTXT(((Integer) data).intValue(), file);
        } else if (data instanceof OrderInfoDataView) {
            return generateTXT(((OrderInfoDataView) data), file);
        } else {
            throw new Exception("generate() => Unknown input data type : " + data.getClass());
        }
    }

    public String getFileExtension() throws Exception {
    	if (TXT_FILE.equals(fileType)) {
    		return ".txt";
    	} else if (XML_FILE.equals(fileType)) {
    		return  ".xml";
    	} else if (HTML_FILE.equals(fileType)) {
    		return  ".html";
    	} else if (PDF_FILE.equals(fileType)) {
    		return  ".pdf";
    	}
        
        return ".txt";
    }

    private String generateTXT(int orderId, File file) throws Exception {
        OrderInfoDataView data = getData(orderId);
        return generateTXT(data, file);
    }

    private String generateTXT(OrderInfoDataView data, File file) throws Exception {
        setUp(NOTEPAD);

        text = genTXT(data);
        writeToFile(text, file);
        return text;
    }


    public String getTextMessage(){
      return text;
    }

    public String genTXT(OrderInfoDataView data) {
        StringBuffer sb = new StringBuffer();
//        writeHeaderTXT(sb,data.getOrderInfo());
        writeHeaderTXT(sb,data);
        writeBodyTXT(sb,data.getItems());
        writeFooterTXT(sb,data);
        return sb.toString();
    }

    public void writeTitle(StringBuffer sb) {}

    public OrderInfoDataView getData(int orderId) throws Exception {
        APIAccess factory = new APIAccess();
        Order orderEjb = factory.getOrderAPI();
        return orderEjb.getOrderInfoData(orderId);
    }

    private void writeToFile(String text, File file) throws Exception {

        BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(file));
        outStream.write(text.getBytes());
        outStream.flush();
        outStream.close();

    }
    public String readFromFile( File file) throws Exception {
        StringBuffer content = new StringBuffer();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

        int b;

        while ( (b = bis.read()) != -1) {
          content.append( (char) b);
        }
        bis.close();

        return  content.toString();

   }

  //  public abstract String genTXT(OrderInfoDataView data) ;

    public void writeHeaderTXT(StringBuffer sb, OrderInfoDataView orderInfo){
      writeHeaderTXT(sb, orderInfo, "", "");
    }

    public abstract void writeHeaderTXT(StringBuffer sb, OrderInfoDataView orderInfo, String subNote, String title) ;
    public abstract void writeBodyTXT(StringBuffer sb, ItemInfoViewVector items) ;
    public abstract void writeFooterTXT(StringBuffer sb, OrderInfoDataView orderInfo);


/*
    public  void defineCurrencyFormat(OrderInfoDataView data){
      Locale loc = new Locale("en_US");
      if (null != data.getOrderInfo().getLocaleCd()) {
        loc = Utility.parseLocaleCode(data.getOrderInfo().getLocaleCd());
      }
       currencyFmt = NumberFormat.getCurrencyInstance(loc);
       log("=================== currencyFmt.format(0) =" + currencyFmt.format(0) + "/"+loc.toString()+"/"+ loc.getLanguage()+ "," + loc.getCountry());

    }
*/
    public  void defineUserLocale(OrderInfoDataView data){
      userLocale = "en_US";
      if (null != data.getOrderInfo().getLocaleCd()){
        userLocale =  data.getOrderInfo().getLocaleCd() ;
      }
    }

    public String getCurrencyValue( BigDecimal pValue) {
      String valueS = Utility.priceFormat(getUserLocale(), pValue, getUserLocale(), 2,  "");
      return valueS;

    }
    public String getUserLocale(){
      return userLocale;
    }
    public String getSubject(Object data){
      return "Order notification";
    }
    public boolean isStoreFromEmail() {
        return false;
    }
    public boolean isUseSiteName() {
        return false;
    }


    protected  static String getOrderStatus (String pStatus) {
      String status = "";
      if (pStatus.equals(RefCodeNames.ORDER_STATUS_CD.REJECTED) ||
          pStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) )  {
        status= pStatus;
      }
      if (pStatus.equals(RefCodeNames.ORDER_STATUS_CD.RECEIVED) ||
          pStatus.equals(RefCodeNames.ORDER_STATUS_CD.ORDERED)||
          pStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW) ||
          pStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW) ||
          pStatus.equals(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED) ||
          pStatus.equals(RefCodeNames.ORDER_STATUS_CD.SENT_TO_CUST_SYSTEM) ||
          pStatus.equals(RefCodeNames.ORDER_STATUS_CD.READY_TO_SEND_TO_CUST_SYS)) {
        status = "In Progress";
      }
      if (pStatus.equals(RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED) ||
          pStatus.equals(RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED)) {
        status = RefCodeNames.ORDER_STATUS_CD.CANCELLED;
      }

      return status;
  }
    protected BigDecimal getActualCost(ItemInfoView pItem) {
        if (pItem.getCustCost() != null) {
            return pItem.getCustCost();
        } else {
            return Utility.bdNN(pItem.getCost());
        }
    }

    protected String getActualSkuNum(ItemInfoView pItem) {
        if (Utility.isSet(pItem.getCustSkuNum())) {
            return pItem.getCustSkuNum();
        } else {
            return Utility.strNN(pItem.getSkuNum());
        }
    }



}


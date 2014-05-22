package com.cleanwise.service.apps.edi;

import com.cleanwise.service.apps.dataexchange.Translator;
import com.cleanwise.service.apps.dataexchange.InboundTranslate;
import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;
import java.util.Vector;
import java.sql.*;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.math.*;
import java.rmi.*;
import java.io.*;
import com.cleanwise.service.api.value.*;

/** Super class for parsing any transaction set
*<br>
*@author Deping
*/
public class InboundSuper //extends Edi
{
  //static final String FREIGHT_SAC = "D500";
  //changed to D240 (freight) per JCPenny request
  protected static final String FREIGHT_SAC = "D240";
  protected static final String MISC_SAC = "D500";
  protected static final String SALES_TAX_SAC = "H740";
    
  protected Vector errorMsgs = new Vector(); // any parsing or validation error
  protected TransactionSet ts;      // current transaction set
  //wheather extraction was valid, in other words, if the order is valid from an
  //edi point of view, not necessarily from a buisness logic point of view.
  private boolean valid = true;
  private boolean validTemp = false;
  
  //protected boolean valid = true;
  protected InboundEdiHandler mHandler;

  protected InboundTranslate getTranslator(){
    return (InboundTranslate) mHandler.translator;
  }
  
  public InboundSuper()
  {
      //direction = INBOUND;
  }

  public void setParameter(InboundEdiHandler handler, TransactionSet set)
  {
    mHandler = handler;
    //translator = handler;
    ts = set;
  }
  public void extract(){}

  // each inbound parser can override this as needed
  public void setValid(boolean pVal) {
      valid = pVal;
  }
  
  public boolean getValid() {
      return valid;
  }

  //public boolean validate()
  //{
  //  return valid;
  //}
  
  public void processTransaction()
  throws Exception
  {
  }

  // return list of error message that store in a vector
  public Vector getErrorMsgs()
  {
    return errorMsgs;
  }
  public static Date stringToDate(String input, String format, String fromZone)
  {
    if (input == null) {
      throw new IllegalArgumentException("Null date argument");
    }

    String inputDate;
    SimpleDateFormat formatter;
    if (fromZone == null || fromZone.equals(""))
    {
      inputDate = input;
      formatter = new SimpleDateFormat(format);
    }
    else
    {
      inputDate = input + fromZone;
      formatter = new SimpleDateFormat(format+"z");
    }
    ParsePosition pos = new ParsePosition(0);
    return formatter.parse(inputDate, pos);
  }

  String mFormatedErrorMsgs = "";
  // return e-mail message that will be send to customer care professional
  // subclass could override this method depend on each case.
  public String getFormatedErrorMsgs ()
  {
    if (errorMsgs.size() == 0 && getTransactionD().getSetStatus() == Edi.RECA_MSG)
      return "";

    String msg = "**** " + Edi.status[getTransactionD().getSetStatus()] + "\r\n";
    for (int i = 0; i < errorMsgs.size(); i++)
      msg = msg + "**** " + errorMsgs.get(i) + "\r\n";
    return msg;
  }
  
  public boolean isTransactionAccepted() {
      boolean val = false;
      ElectronicTransactionData etD = getTransactionD();
      if(etD!=null) {
          if(etD.getSetStatus() == Edi.RECA_MSG) val = true;
      }
      return val;
  }
  
  protected ElectronicTransactionData getTransactionD(){
      return mHandler.transactionD;
  }
  
}


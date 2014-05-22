package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.apps.ClientServicesAPI;
import com.cleanwise.service.apps.dataexchange.InboundTranslate;
import java.util.Vector;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

/** Super class for parsing any transaction set
*<br>
*@author Deping
*/
public abstract class InboundSuper extends ClientServicesAPI
{
    
  protected Vector errorMsgs = new Vector(); // any parsing or validation error
  protected boolean valid = true;
  protected InboundTransaction mHandler;
  
  public InboundSuper()
  {
  }

  protected InboundTranslate getTranslator(){
	  return (InboundTranslate)mHandler.getTranslator();
  }
  public abstract void extract() throws Exception;

  // each inbound parser can override this as needed
  public void setValid(boolean pVal) {
      valid = pVal;
  }
  
  public boolean getValid() {
      return valid;
  }
  
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
    if (errorMsgs.size() == 0 && getTransactionD().getSetStatus() == InterchangeSuper.RECA_MSG)
      return "";

    String msg = "**** " + InterchangeSuper.status[getTransactionD().getSetStatus()] + "\r\n";
    for (int i = 0; i < errorMsgs.size(); i++)
      msg = msg + "**** " + errorMsgs.get(i) + "\r\n";
    return msg;
  }
    
  public void appendErrorMsgs(String errorMessage, boolean setInvalid){
	  errorMsgs.add(errorMessage);
	  if (setInvalid)
		setValid(false);
  }
  
  public void appendErrorMsgs(Exception e, boolean setInvalid){
	  if (e.getMessage() == null)
  		appendErrorMsgs(e.toString(), true);
  	  else
  		appendErrorMsgs(e.getMessage(), true);
  }
  
  protected ElectronicTransactionData getTransactionD(){
      return mHandler.getTransactionObject();
  }
  
  public boolean validateAndAppendErrorMsgs(String val, String errorMessage){
  	if (!Utility.isSet(val)) {
  		errorMsgs.add(errorMessage);
  		setValid(false);
  		return false;
  	}
  	return true;
  }
  
}


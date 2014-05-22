

package com.cleanwise.service.apps.dataexchange;
//com.cleanwise.service.apps.loaders.PipeFileParser
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.apps.loaders.PipeFileParser;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.BusEntityParameterDataAccess;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.PhoneDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.util.Utility;
import java.text.DateFormat;
import com.cleanwise.service.api.session.Country;

public class InboundNSCSapLoader extends InboundFlatFile {
    protected HashMap errorLines =new HashMap();
 //   protected int storeNum = 0;
 //   private Date runDate = new Date();

    protected HashMap<String, RefCdDataVector> refCodeNamesMap = new HashMap<String, RefCdDataVector>();
//    protected HashMap<String, String> countryRequireStateMap = new HashMap<String, String>();
    protected HashSet countryRequireStateSet = new HashSet();
    private final static int ERROR_LIMIT = 500;

    public interface TYPE {
         public static final int
             TEXT = 0,
             INTEGER = 1,
             BIG_DECIMAL = 2,
             BOOLEAN = 3,
             REF_CODE_NAME = 4,
             REF_CODE_NAME_LIST = 5,
             DATE = 6,
             YEAR = 7;
          }



   protected boolean isErrorLimit() {

    boolean b = false;
    Set errLines = errorLines.keySet();
    if (errLines!= null && errLines.size() >= this.ERROR_LIMIT){
      b = true;
      String s = "More then " + ERROR_LIMIT + "errors found in the input file.";
      appendErrorMsgs(s);
    }
    return b;
  }

   protected void addError(String s) {
      appendErrorMsgs(s);
   }
   protected void addError(String s, int line) {
       StringBuffer errMsgs = null;
       Integer key = new Integer(line+1);
       if (errorLines.containsKey(key)){
         errMsgs= (StringBuffer)errorLines.get(key);
       } else {
         errMsgs = new StringBuffer();
         errorLines.put(key, errMsgs);
       }
       errMsgs.append("-> " + s + " ");
    }

   protected void processErrorMsgs() {
     Set lines = errorLines.keySet();
      for (Iterator iter = lines.iterator(); iter.hasNext(); ) {
       Integer lineNum = (Integer) iter.next();
       StringBuffer errMsgs = (StringBuffer)errorLines.get(lineNum);
       addError("Line "+ lineNum + ": " + errMsgs + "\r\n");
     }
   }

  protected boolean checkRequired(String lineValue, String valueName, int line) {
     boolean valid = true;
     if (!Utility.isSet(lineValue)) {
       addError("Empty " + valueName.toUpperCase() + ". " , line);
       valid = false;
     }
     return valid;
  }
   protected boolean checkType (String lineValue, String valueName, int valueType, boolean isRequired, int line){
     StringBuffer newValue = new StringBuffer();
     return checkType ( lineValue,  valueName,  valueType,  isRequired, line, newValue);
   }
   protected boolean checkType (String lineValue, String valueName, int valueType, boolean isRequired, int line, StringBuffer newValue){

     if (isRequired && !checkRequired(lineValue, valueName, line)){
       return false;
     } else if (!isRequired && !Utility.isSet(lineValue)) {
       return true;
     }
     boolean valid =  true;
     StringBuffer newRefValue = null;
     try {
       switch (valueType) {
         case TYPE.TEXT :
           break;
         case TYPE.INTEGER :
           try {
             Integer v1 = new Integer(lineValue);
           } catch (Exception e){
             throw new Exception("Found: '"+lineValue.trim()+ "'. Should be Integer"  );
           }
           break;
         case TYPE.BIG_DECIMAL :
           try {
             BigDecimal v2 = new BigDecimal(lineValue);
           } catch (Exception e){
             throw new Exception("Found: '"+lineValue.trim()+ "'. Should be Decimal"  );
           }
           break;
         case TYPE.BOOLEAN :
           if (!"TRUE".equalsIgnoreCase(lineValue.trim()) &&
               !"FALSE".equalsIgnoreCase(lineValue.trim())){
             throw new Exception("Found: '"+lineValue.trim()+ "'. Should be TRUE/FALSE"  );//Incorrect Boolean Value
           }
           break;
         case TYPE.REF_CODE_NAME :
           newRefValue = new StringBuffer();
           if (!refCdDataVectorContains((RefCdDataVector)(refCodeNamesMap.get(valueName)),lineValue.trim(), newRefValue)){
             throw new Exception("Missing: '" +lineValue.trim() + "'. Should be one of " + getRefCodeNamesValues (valueName) );
           }
           newValue.append(newRefValue.toString());
           break;
         case TYPE.REF_CODE_NAME_LIST :
           String[] list = lineValue.split(",");
           for (int i = 0; i < list.length; i++) {
             newRefValue = new StringBuffer();
             if (!refCdDataVectorContains((RefCdDataVector)(refCodeNamesMap.get(valueName)),list[i].trim(), newRefValue)){
               throw new Exception("Missing : '" +list[i].trim() + "'. ");
             }
             newValue.append((newValue.length()==0)? newRefValue: ","+newRefValue);
           }
           newValue.append(newRefValue.toString());
           break;
         case TYPE.DATE :
             DateFormat df  = new SimpleDateFormat("MM/dd/yyyy");
             df.parse(lineValue);
             Date d = df.parse(lineValue.trim());
             String s = df.format(d);
             if (!lineValue.equals(s)){
               throw new Exception("Found: '"+lineValue.trim()+ "'. Should be MM/dd/yyyy"); //Incorrect Date Value:
             }
             break;
         case TYPE.YEAR :
           Integer v3 = new Integer(lineValue);
           if (v3.intValue() > 0 && (v3.intValue() > Utility.getCurrentYear()+10 || v3.intValue() <  Utility.getCurrentYear()-1)){
              throw new Exception("Found: '"+lineValue.trim()+ "'. Should be integer" ); // Incorrect Year
           }
           break;

       }

     }
     catch (Exception ex) {
       addError("Incorrect " + valueName.toUpperCase() + ". " + ex.getMessage()+". ", line);
       valid = false;
     }
     return valid;
   }

   protected boolean isValidLineSize(int lineSize,  int lineNum) {
     String errMess = "";
     boolean valid = true;
     int FILE_LINE_SIZE = getFileLineSize();
     if (lineSize < FILE_LINE_SIZE) {
       errMess ="There is not enough fields in the line of inbound file.";
     }
     if (Utility.isSet(errMess)) {
       addError(errMess+" Found: " +lineSize + ". Should be " + FILE_LINE_SIZE +". ", lineNum);
       valid = false;
     }
 //    log.info("isValidLineSize() ===>  FILE_LINE_SIZE = " +  FILE_LINE_SIZE + " valid =" + valid);

     return valid;

   }
   protected int getFileLineSize() {
     return 0;
   }

   protected int getStoreIdFromTradingPartner() throws Exception {
            TradingPartner partnerEjb = APIAccess.getAPIAccess().getTradingPartnerAPI();
            TradingPartnerData partner = translator.getPartner();
            IdVector storeIds = null;
            if (partner == null) {
                throw new IllegalArgumentException("Trading Partner ID cann't be determined");
            }
            HashMap assocMap = partnerEjb.getMapTradingPartnerAssocIds(partner.getTradingPartnerId());
            if (assocMap != null) {
                    storeIds = (IdVector) assocMap.get(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
            }
            int storeId =0;
            if (storeIds != null){
             storeId = ((Integer)storeIds.get(0)).intValue();
            }
            return storeId;
       }

   private int getStoreIdFromFile (Connection conn, PreparedStatement pstmt, String tempTable) throws Exception {
         String selectStoreId = "select distinct STORE_ID from " + tempTable ;
         pstmt = conn.prepareStatement(selectStoreId);
         ResultSet rs = pstmt.executeQuery();
         int storeId =0;
         while(rs.next()){
           storeId = Integer.parseInt(rs.getString(1));
           if (rs.next())
             throw new Exception("Error Found Two distinct Store ID's in File");
         }
         if(storeId == 0){
           throw new Exception("Store ID is missing in File");
         }
         return storeId;
       }

   public Set getRefCodeNamesValues (String refCodeKey)  {
     RefCdDataVector v = (RefCdDataVector)refCodeNamesMap.get(refCodeKey);
     Set refCodeValues = new HashSet();
     Iterator i = v.iterator();
     while (i.hasNext()) {
       RefCdData refD = (RefCdData)i.next();
       refCodeValues.add(refD.getValue());
     }
     return refCodeValues;
   }

   protected void initCountriesRequiredStateSet () throws Exception {
     APIAccess factory = new APIAccess();
     Country countryBean = factory.getCountryAPI();
     CountryDataVector countryV = countryBean.getCountriesByPropertyData( RefCodeNames.COUNTRY_PROPERTY.USES_STATE, "true");
     for (int i = 0; countryV != null && i < countryV.size(); i++) {
       CountryData  country = (CountryData)countryV.get(i);
       countryRequireStateSet.add(country.getShortDesc());
       countryRequireStateSet.add(country.getCountryCode());
       countryRequireStateSet.add(country.getUiName());

     }
   }
   protected boolean isStateRequired (String country) {
     if (!Utility.isSet(country)){
       return false;
     }
     String c = country.trim().toUpperCase();
     return (countryRequireStateSet.contains(c));
   }
   public  boolean refCdDataVectorContains(RefCdDataVector v, String value, StringBuffer newValue) {
    if (v == null || v.size() == 0 || !Utility.isSet(value)) {
        return false;
    }
    Iterator i = v.iterator();
    while (i.hasNext()) {
      RefCdData refD = (RefCdData)i.next();
      if (refD.getValue().equalsIgnoreCase(value)) {
          newValue.append(refD.getValue());
          return true;
      }
    }
    return false;
}

}

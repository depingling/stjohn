/* 
 * SalesTaxRateLoader.java
 *
 * Created on September 6, 2005, 10:03 AM
 *
 * Copyright 2005 Cleanwise, Inc.
 */

package com.cleanwise.service.apps.loaders;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Request;
import com.cleanwise.service.apps.ClientServicesAPI;
import com.cleanwise.service.crypto.BASE64Encoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.util.Iterator;

import org.apache.log4j.Logger;


/**
 * This loader is designed to compinstate for the length of time it takes to get a result back from the tax calculation server.
 * It will prioritize zipcodes to load based off their mod date and absence of data and it performs a commit after each record is loaded.
 * @author bstevens
 */
public class SalesTaxRateLoader extends ClientServicesAPI{
	private static final Logger log = Logger.getLogger(SalesTaxRateLoader.class);
	
	public void logInfo(String message){
    	log.info(message);
    }
	public void logError(String message){
    	log.info(message);
    }
    //first process those entries that are empty
    //private static final String SELECT_SQL_1 = "select distinct postal_code from clw_city_postal_code where tax_rate is null";
    //next process those entries that are oldest
    //private static final String SELECT_SQL_2 = "select postal_code from clw_city_postal_code where tax_rate is not null group by postal_code order by max(mod_date)";

    //program recieveing the request will round the amount that is sent (tax rate of .0735 * and amount of 1 == .08) so we give it a different amount
    //and then divide the result to derive the date based off the taxed amount.
    private Request mTaxService;
    private APIAccess factory;
    private String mTaxServerUrl;
    private String mTaxServerUser;
    private String mTaxServerPass;
    private int maxNumberZipsToProcess;
    
    public static void main(String args[]){
        String taxServerUrl=System.getProperty("TaxServerURL");
        String taxServerUser=System.getProperty("TaxServerUser");
        String taxServerPass=System.getProperty("TaxServerPass");
        String maxNumberZipsToProcessS=System.getProperty("NumberZipsToProcess");
        if(taxServerUrl == null || taxServerUser == null || taxServerPass == null){
            log.info("Usage: java -DNumberZipsToProcess=<Number of zips to process> -DTaxServerURL=<TaxServerURL> -DTaxServerUser=<user> -DTaxServerPass=<pass> SalesTaxRateLoader");
            return;
        }
        int tempMaxNumberZipsToProcess;
        try{
            tempMaxNumberZipsToProcess = Integer.parseInt(maxNumberZipsToProcessS);
        }catch(Exception e){
            e.printStackTrace();
            log.info("NumberZipsToProcess specified was not a number: "+maxNumberZipsToProcessS);
            log.info("Usage: java -DNumberZipsToProcess=<Number of zips to process> -DTaxServerURL=<TaxServerURL> -DTaxServerUser=<user> -DTaxServerPass=<pass> SalesTaxRateLoader");
            return;
        }
        SalesTaxRateLoader me = new SalesTaxRateLoader(taxServerUrl, taxServerUser, taxServerPass);
        me.maxNumberZipsToProcess = tempMaxNumberZipsToProcess;
        me.processLoadRequest();
    }
    
    /**
     *Creates a new instance of the SalesTaxRateLoader with the requiered taxserverurl as the parameter
     */
    public SalesTaxRateLoader(String pTaxServerUrl, String pTaxServerUser, String pTaxServerPassword){
        if(pTaxServerUrl == null){
            throw new NullPointerException("pTaxServerUrl was null");
        }
        if(pTaxServerUser == null){
            throw new NullPointerException("pTaxServerUser was null");
        }
        if(pTaxServerPassword == null){
            throw new NullPointerException("pTaxServerPassword was null");
        }
        mTaxServerUrl = pTaxServerUrl;
        mTaxServerUser = pTaxServerUser;
        mTaxServerPass = pTaxServerPassword;
    }
    
    /**
     *processes a loader run.
     */
    public void processLoadRequest(){
        try{
            APIAccess factory = getAPIAccess();
            mTaxService = factory.getRequestAPI();
        }catch(Exception e){
            logError("Error getting APIAccess, exiting no changes made");
            e.printStackTrace();
            return;
        }
        
        //perform a sanity check to make sure everything is working corectly.  Check a known zipcode, and verify the result
        try{
            BigDecimal testTaxRate = getTaxRateForPostalCodeCode("01752");  //.05
        	//BigDecimal testTaxRate = getTaxRateForPostalCodeCode("95350");
            //BigDecimal testTaxRate = getTaxRateForPostalCodeCode("37077"); //.0925
            if(testTaxRate == null || new BigDecimal("0.0625").compareTo(testTaxRate) != 0){
                logError("Failed 01752 sanity check, rate should be .0625, instead was: "+testTaxRate+" exiting no changes made.");
                return;
            }
        }catch(Exception e){
            logError("Error during sanity check");
            e.printStackTrace();
            return;
        }
        
        //retrieve data to process
      //  CityPostalCodeDataVector postalCodes;
      //  try{
      //      postalCodes = getPostalCodeCodesToProcess();
      //  }catch(Exception e){
      //      logError("Error getting postal codes to process, exiting no changes made");
      //      e.printStackTrace();
      //      return;
      //  }
      //  logInfo("Found "+postalCodes.size()+" to process");
        
        
        //iterate through the zipcodes that need processing
      //  Iterator it = postalCodes.iterator();
      //  BigDecimal lastTaxRate=null;
      //  String lastPostalCode=null;
      //  while(it.hasNext()){
      //      try{
      //          CityPostalCodeData pcData = (CityPostalCodeData) it.next();
      //          String postalCode = pcData.getPostalCode();
      //          BigDecimal taxRate;
      //          //there are duplicate records (1 zip for multiple cities, so cache the last tax rate)
      //          if(postalCode.equals(lastPostalCode)){
      //              logInfo("duplicate postal code: "+lastPostalCode+" re-loading tax rate "+lastTaxRate);
      //              taxRate = lastTaxRate;
      //          }else{
      //              logInfo("getting tax rate for postal code: "+lastPostalCode);
      //              taxRate = getTaxRateForPostalCodeCode(postalCode);
      //              logInfo("got tax rate: "+taxRate);
      //          }
      //          logInfo("sending update request");
      //          loadTaxRateForPostalCode(pcData, taxRate);
      //          lastPostalCode = postalCode;
      //          lastTaxRate = taxRate;
      //      }catch(Exception e){ 
      //          logError("Caught exception...continuing");
      //          e.printStackTrace();
      //      }
      //  }
    }
    
    /**
     *Retrieves a list of zipcodes (possibly ordered by priority) to process.
     */
    //private CityPostalCodeDataVector getPostalCodeCodesToProcess() throws RemoteException{
    //    
   //     return mTaxService.getPostalCodesToProcessSalesTaxRates(maxNumberZipsToProcess);
   // }
    
    /**
     *Figures out the tax rate for this postal code
     */
    private BigDecimal getTaxRateForPostalCodeCode(String pPostalCode) throws IOException{
        if(pPostalCode == null){
            return null;
        }
        StringBuffer urlBuf = new StringBuffer(mTaxServerUrl);
        //construct the query
        urlBuf.append("?zip=");
        if(pPostalCode.length() > 5){
            pPostalCode=pPostalCode.substring(0,5);
        }
        urlBuf.append(pPostalCode);
        logInfo("Posting request using url query: "+urlBuf);
        
        
        //now execute the query
        URL url = new URL(urlBuf.toString());
        URLConnection connect = url.openConnection();
        String userPassword = mTaxServerUser + ":" + mTaxServerPass;
        BASE64Encoder encoder = new BASE64Encoder();
        String encoding = encoder.encode(userPassword.getBytes());
        connect.setRequestProperty ("Authorization", "Basic " + encoding);


        InputStream in = connect.getInputStream();
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        StringBuffer resp = new StringBuffer();
        String aLine = rdr.readLine();
        while(aLine != null){
            resp.append(aLine);
            resp.append("\n");
            aLine = rdr.readLine();
        }
        in.close();
        //parse out the result from something like:
/*
GeoCode: 220173870
result:   5.00

Finished.
 */
        
        if(resp.indexOf("GeoCode") < 0){
            logInfo("Could not find rate information for this postal code (no GeoCode)");
            return null;
        }
        String key = "result:";
        int begIdx = resp.indexOf(key)+key.length();
        int endIdx = resp.indexOf("\n",begIdx);
        String amountStr = resp.substring(begIdx, endIdx).trim();
        logInfo("found result: "+amountStr);
        BigDecimal amount = new BigDecimal(amountStr);
        amount = amount.setScale(10,BigDecimal.ROUND_DOWN);
        return amount;
    }
    
    /**
     *Updates the database with the new tax information
     */
   // private void loadTaxRateForPostalCode(CityPostalCodeData pPostalCodeData,BigDecimal pTaxRate)throws RemoteException{
   //     mTaxService.updateTaxRateCityPostalCodeData(pPostalCodeData, pTaxRate);
   // }
}

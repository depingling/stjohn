/*
 * CITAccountsPayableLoader.java
 *
 * Created on June 27, 2002, 11:23 AM
 */

package com.cleanwise.service.apps.loaders;
import com.cleanwise.service.api.util.Utility;

import com.cleanwise.service.api.value.Fedstrip058Data;
import com.cleanwise.service.api.value.Fedstrip058DataVector;

import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.SiteHome;

import java.util.Properties;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileInputStream;
//RMI Stuff
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import com.cleanwise.service.api.util.JNDINames;
import org.apache.log4j.Logger;
/**
 *
 * @author  bstevens
 */
public class FedstripLoader extends FixedWidthParser{
    //Keeps track of when one payment actually references another one.  This happens
    //when CIT will get in a payment and apply it under a different referenece number,
    //they then call this a A/R Transaction code of 300 and stuff the old payment number
    //into the "item" field as if they are applying the new payment to that older payment
    //This is essentially internal to CIT and we don't care about it, so we just have to know
    //about it.
    private static final Logger log = Logger.getLogger(FedstripLoader.class);

    private HashMap mPaymentNumberMapping = new HashMap();
    
    private ArrayList mRemittanceUpdates = new ArrayList();
    
    
    //if true will be more verbose
    static boolean mDebug = false;
    //username that is added to the db for the addby/modby fields
    static final String mUserName = "FedstripLoader";
    //cashed FedstripData that is being used
    Fedstrip058Data fedstripData = Fedstrip058Data.createValue();
    int mCurrLineNbr = 0;
    
    //EJB References
    Site mSiteRef;
    String mFileName;
    
    Fedstrip058DataVector fedstrips = null;
    
    
    /**
     *Sets up the class as an EJB Client.
     */
    private void setUp() throws Exception {
        // Check for a properties file command option.
        mCurrLineNbr = 0;
        String propFileName = System.getProperty("conf");
        if ( null == propFileName ) {
            propFileName = "installation.properties";
        }
        Properties props = new Properties();
        java.io.File f = new java.io.File(propFileName);
        props.load(new FileInputStream(propFileName) );
        InitialContext jndiContext = new InitialContext(props);
        Object ref;
        
        // Get a reference to the Site Bean
        ref = jndiContext.lookup(JNDINames.SITE_EJBHOME);
        SiteHome siteHome = (SiteHome)
           PortableRemoteObject.narrow(ref, SiteHome.class);
        mSiteRef = siteHome.create();
        mSiteRef.cleanFedstrip058(mFileName);        
        fedstrips = new Fedstrip058DataVector();
    }
    
    private void setupFileMapping(){
        FixedWidthFieldDef[] charRanges = new FixedWidthFieldDef[23];
        charRanges[0] = new FixedWidthFieldDef(0,6,"fedstrip",null,null,null); //varchar2(6) not null,                                                                                                                                                                                                                                                                                        
        charRanges[1] = new FixedWidthFieldDef(6,16,"finance",null,null,null); // varchar2(10) null,                                                                                                                                                                                                                                                                                       
        charRanges[2] = new FixedWidthFieldDef(16,18,"baCode",null,null,null); // varchar2(2) null,                                                                                                                                                                                                                                                                                       
        charRanges[3] = new FixedWidthFieldDef(18,21,"districtCode",null,null,null); // varchar2(3) null,                                                                                                                                                                                                                                                                                       
        charRanges[4] = new FixedWidthFieldDef(21,22,"cag",null,null,null); // varchar2(1) null,                                                                                                                                                                                                                                                                                       
        charRanges[5] = new FixedWidthFieldDef(22,32,"addressTypeCode",null,null,null); // varchar2(10) null,                                                                                                                                                                                                                                                                                       
        charRanges[6] = new FixedWidthFieldDef(32,62,"addressLine1",null,null,null); // varchar2(30) null,                                                                                                                                                                                                                                                                                       
        charRanges[7] = new FixedWidthFieldDef(62,92 ,"addressLine2",null,null,null); // varchar2(30) null,                                                                                                                                                                                                                                                                                       
        charRanges[8] = new FixedWidthFieldDef(92,122,"addressLine3",null,null,null); // varchar2(30) null,                                                                                                                                                                                                                                                                                       
        charRanges[9] = new FixedWidthFieldDef(122,149,"city",null,null,null); // varchar2(27) null,
        charRanges[10] = new FixedWidthFieldDef(149,151,"state",null,null,null); // varchar2(2) null,
        charRanges[11] = new FixedWidthFieldDef(151,160,"zip",null,null,null); // varchar2(9) null,
        charRanges[12] = new FixedWidthFieldDef(160,166,"nmicsSiteCode",null,null,null); // varchar2(6) null,
        charRanges[13] = new FixedWidthFieldDef(166,169,"nmicsSubsiteCode",null,null,null); // varchar2(3) null,
        charRanges[14] = new FixedWidthFieldDef(169,170,"partBuyAuthIndicator",null,null,null); // varchar2(1) null,
        charRanges[15] = new FixedWidthFieldDef(170,180,"partsFinanceNumber",null,null,null); // varchar2(10) null,
        charRanges[16] = new FixedWidthFieldDef(180,200,"contactPhone",null,null,null); // varchar2(20) null,
        charRanges[17] = new FixedWidthFieldDef(200,220,"contactFax",null,null,null); // varchar2(20) null,
        charRanges[18] = new FixedWidthFieldDef(220,221,"partsFacilityTypeCode",null,null,null); // varchar2(1) null,
        charRanges[19] = new FixedWidthFieldDef(221,224,"scfCode",null,null,null); // varchar2(3) null,
        charRanges[20] = new FixedWidthFieldDef(224,264,"customerName",null,null,null); // varchar2(40) null,
        charRanges[21] = new FixedWidthFieldDef(264,272,"datedChanged",null,null,null); // varchar2(8) null,
        charRanges[22] = new FixedWidthFieldDef(272,273,"changeCode",null,null,null); // varchar2(1) null
        Filter fil = this.addFilter(charRanges,"com.cleanwise.service.api.value.Fedstrip058Data",0,0,"");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String debug = System.getProperty("debug");
        
        if ( debug != null && debug.equalsIgnoreCase("true") ) {
            mDebug = true;
        }
        FedstripLoader loader = new FedstripLoader();
        loader.mFileName = System.getProperty("ifile");
        if (!Utility.isSet(loader.mFileName)){
            String usage = "Usage: java "+FedstripLoader.class.getName()+" -Difile=in.txt  -Ddebug=false";
            log.info(usage);
            return;
        }
        loader.setupFileMapping();
        try{
            loader.setUp();
        }catch(Exception e){
            e.printStackTrace();
            log.info("setup failure, no database changes");
            return;
        }
        try{
            loader.parse(loader.mFileName);
        }catch (Exception e){
            //this will essentially just add a message to the database that is not
            //attached to a remittance
            loader.processUnfilteredLine("Caught Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        log.info("Fedstrip 058 Loader finished");
    }
    
    
    
    
    protected void processLine(Object pObj) {
      mCurrLineNbr++;
      if(mCurrLineNbr%100==0) {
       log.info("Processing line #"+mCurrLineNbr);
      }
      Fedstrip058Data fedstripD = (Fedstrip058Data) pObj;
      fedstripD.setFileName(mFileName);
      try {
        fedstripD =  mSiteRef.addFedstrip058(fedstripD, mUserName); 
        fedstrips.add(fedstripD);
      } catch(Exception exc) {
         log.info("Fedstrip processing exception. File: "+mFileName+
            " Fedstrip: "+pObj+
            " Exception: "+exc.getMessage());
      }
    }
    
    protected void processUnfilteredLine(String pLine){
    }
    
    
    protected void postProcessFile() {
       log.info("Parsing finished. Lines processed: "+mCurrLineNbr);
/*
        try{
            if(mProcessedRemittances == null || mProcessedRemittances.size()==0 ){
                return;
            }
            //first update all the remittance data that we changed during processing:
            for (int i=0;i<mRemittanceUpdates.size();i++){
                RemittanceData rd = (RemittanceData) mRemittanceUpdates.get(i);
                mRemittanceRef.updateRemittance(rd,mUserName);
            }
            boolean valid = true;
            String errMsg = null;
            if (mTotalsRecord == null || mTotalsRecord.getTotalAmount() == null){
                //XXX big problem, paritally recieved file?
                valid = false;
                errMsg = "total not found in file, possible partial transmission (corrupt file)";
            }else{
                if(!mTotalsRecord.getTotalAmount().equals(mRunningTotal)){
                    valid = false;
                    errMsg = "calculated total: "+mRunningTotal+
                    " != file total: " + mTotalsRecord.getTotalAmount();
                }
            }
            //update all of the states to error
            if(!valid){
                for(int i=0,len=mProcessedRemittances.size();i<len;i++){
                    RemittanceData rd = (RemittanceData) mProcessedRemittances.get(i);
                    addRemitError(rd,errMsg);
                }
            }
            //update the database with any errors/messages
            mIntegrationServicesRef.processIntegrationRequests(mIntegrationRequests,null);
            
            //re-initialize everything, this is here because of the funky stuff
            //CIT does where they will paste 2 files together that are logically
            //seperate.  This is kind've a hack as this method will be called every
            //time a transmission total record is found
            mRunningTotal = new BigDecimal(0.0);
            mRData = RemittanceData.createValue();
            mIntegrationRequests = new IntegrationRequestsVector();
            mProcessedRemittances = new RemittanceDataVector();
            mErrRemits = new HashMap();
            mPaymentNumberMapping = new HashMap();
            mTotalsRecord = null;
        }catch(Exception e){
            //this is a bad problem
            e.printStackTrace();
        }
 */
    }
    
}

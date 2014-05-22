/*
 * CITAccountsPayableLoader.java
 *
 * Created on June 27, 2002, 11:23 AM
 */

package com.cleanwise.service.apps.loaders;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;

import com.cleanwise.service.api.value.*;

import com.cleanwise.service.api.session.Remittance;
import com.cleanwise.service.api.session.*;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.FileInputStream;
//RMI Stuff
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

import com.cleanwise.service.api.util.JNDINames;

import org.apache.log4j.Logger;

/**
 *
 * @author  bstevens
 */
public class CITAccountsPayableLoader extends FixedWidthParser{
    //Keeps track of when one payment actually references another one.  This happens
    //when CIT will get in a payment and apply it under a different referenece number,
    //they then call this a A/R Transaction code of 300 and stuff the old payment number
    //into the "item" field as if they are applying the new payment to that older payment
    //This is essentially internal to CIT and we don't care about it, so we just have to know
    //about it.

    private static final Logger log = Logger.getLogger(CITAccountsPayableLoader.class);

    private HashMap mPaymentNumberMapping = new HashMap();
    
    private HashMap mAccountIDtoErpMapping = new HashMap();
    
    private ArrayList mHiddenRemittanceUpdates = new ArrayList();
    
    private HashMap mAccountPaymentXRef = new HashMap(); //cross refs the accoutns and payments to those 
                                                         //that were flipped to a new account due to invoice 
                                                         //number being with different account
    
    //running total to compare to the transaction total record in the file
    //*note* we will use the undivided values for this calculation
    //i.e. $123.45 will be 12345
    private BigDecimal mRunningTotal = new BigDecimal(0.0);
    
    //every file has one total record which we will use to validate
    //against our calculated totals.
    private CITTransmissionTotal mTotalsRecord;
    
    //if true will be more verbose
    static boolean mDebug = false;
    //username that is added to the db for the addby/modby fields
    static final String mUserName = "CITAccountsPayableLoader";
    //cashed RData that is being used
    RemittanceData mRData = RemittanceData.createValue();
    //store id (1 for cleanwise)
    int mStoreId;
    //list of objects that will need updating
    IntegrationRequestsVector mIntegrationRequests = new IntegrationRequestsVector();
    
    ArrayList RemittanceReferenceNumberGUIDList = new ArrayList();
    
    //EJB References
    Remittance mRemittanceRef;
    IntegrationServices mIntegrationServicesRef;
    Order mOrderRef;
    Account mAccountRef;
    
    
    private void addRemittanceUpdate(RemittanceData rd){
        mHiddenRemittanceUpdates.add(rd);
    }
    private ArrayList getRemittancUpdates(){
        return mHiddenRemittanceUpdates;
    }
    
    
    private Date getCutoffTime(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-2);
        return cal.getTime();
    }
    
    //Returns a unique refernce number for this remittance that is unique per this account
    //for this day. If the payment refernce number is set it will prepend that to the GUID
    private String getGUIDRemittanceReferenceNumber(RemittanceData pRemittanceData){
        Date today = new Date();
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MMddyyyy");
        //always put the "-" to make this identifiable in our erp as a potental
        //problem remittance
        String GUID = "-" + df.format(today);
        if (Utility.isSet(pRemittanceData.getPaymentReferenceNumber())){
            //this to prevent looping guids (10102003-10102003-10102003 etc);
            if(RemittanceReferenceNumberGUIDList.contains(pRemittanceData.getPaymentReferenceNumber())){
                return pRemittanceData.getPaymentReferenceNumber();
            }
            GUID = pRemittanceData.getPaymentReferenceNumber() + GUID;
        }
        RemittanceReferenceNumberGUIDList.add(GUID);
        return GUID;
    }
    
    //error processing stuff
    private RemittanceDataVector mProcessedRemittances = new RemittanceDataVector();
    private HashMap mErrRemits = new HashMap();
    private void addGeneralError(String pMessage) {
        RemittancePropertyData err = RemittancePropertyData.createValue();
        err.setAddBy(mUserName);
        err.setModBy(mUserName);
        err.setAddDate(new Date());
        err.setModDate(new Date());
        err.setRemittanceDetailId(0);
        err.setRemittanceId(0);
        err.setRemittancePropertyId(0);
        err.setRemittancePropertyStatusCd(RefCodeNames.REMITTANCE_PROPERTY_STATUS_CD.ACTIVE);
        err.setRemittancePropertyTypeCd(RefCodeNames.REMITTANCE_PROPERTY_TYPE_CD.ERROR);
        err.setValue(pMessage);
        mIntegrationRequests.add(err);
    }
    
    private void addError(RemittanceDetailData pRdd,String pMessage) {
        RemittancePropertyData err = RemittancePropertyData.createValue();
        err.setAddBy(mUserName);
        err.setModBy(mUserName);
        err.setAddDate(new Date());
        err.setModDate(new Date());
        err.setRemittanceDetailId(pRdd.getRemittanceDetailId());
        err.setRemittanceId(pRdd.getRemittanceId());
        err.setRemittancePropertyId(0);
        err.setRemittancePropertyStatusCd(RefCodeNames.REMITTANCE_PROPERTY_STATUS_CD.ACTIVE);
        err.setRemittancePropertyTypeCd(RefCodeNames.REMITTANCE_PROPERTY_TYPE_CD.ERROR);
        err.setValue(pMessage);
        mIntegrationRequests.add(err);
        if (!pRdd.getRemittanceDetailStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.INFORMATION_ONLY)){
            pRdd.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED_ERROR);
        }
        mIntegrationRequests.add(pRdd);
    }
    
    private void addRemitNote(RemittanceData pRd,String pMessage){
        if (Utility.isSet(pMessage)){
            RemittancePropertyData note = RemittancePropertyData.createValue();
            note.setAddBy(mUserName);
            note.setModBy(mUserName);
            note.setAddDate(new Date());
            note.setModDate(new Date());
            note.setRemittanceId(pRd.getRemittanceId());
            note.setRemittancePropertyId(0);
            note.setRemittancePropertyStatusCd(RefCodeNames.REMITTANCE_PROPERTY_STATUS_CD.ACTIVE);
            note.setRemittancePropertyTypeCd(RefCodeNames.REMITTANCE_PROPERTY_TYPE_CD.NOTE);
            note.setValue(pMessage);
            mIntegrationRequests.add(note);
        }
    }
    
    private void addRemitError(RemittanceData pRd,String pMessage) {
        //mErrRemits.put(new Integer(pRd.getRemittanceId()),pMessage);
        if (Utility.isSet(pMessage)){
            RemittancePropertyData err = RemittancePropertyData.createValue();
            err.setAddBy(mUserName);
            err.setModBy(mUserName);
            err.setAddDate(new Date());
            err.setModDate(new Date());
            err.setRemittanceId(pRd.getRemittanceId());
            err.setRemittancePropertyId(0);
            err.setRemittancePropertyStatusCd(RefCodeNames.REMITTANCE_PROPERTY_STATUS_CD.ACTIVE);
            err.setRemittancePropertyTypeCd(RefCodeNames.REMITTANCE_PROPERTY_TYPE_CD.ERROR);
            err.setValue(pMessage);
            mIntegrationRequests.add(err);
        }
        if (!(pRd.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY)
        || pRd.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.PENDING))){
            pRd.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED_ERROR);
        }
        mIntegrationRequests.add(pRd);
    }
    
    private void processRemitErrors(RemittanceDetailData pRdd){
        String pMessage = (String) mErrRemits.get(new Integer(pRdd.getRemittanceId()));
        if ( pMessage != null ){
            addError(pRdd, pMessage);
        }
    }
    
    /**
     *Sets up the class as an EJB Client.
     */
    private void setUp() throws Exception {
        // Check for a properties file command option.
        String propFileName = System.getProperty("conf");
        if ( null == propFileName ) {
            propFileName = "installation.properties";
        }
        Properties props = new Properties();
        java.io.File f = new java.io.File(propFileName);
        props.load(new FileInputStream(propFileName) );
        InitialContext jndiContext = new InitialContext(props);
        Object ref;
        
        // Get a reference to the Remittance Bean
        ref = jndiContext.lookup(JNDINames.REMITTANCE_EJBHOME);
        RemittanceHome remittanceHome = (RemittanceHome)
        PortableRemoteObject.narrow(ref, RemittanceHome.class);
        mRemittanceRef = remittanceHome.create();
        
        // Get a reference to the Integration Services Bean
        ref = jndiContext.lookup(JNDINames.INTEGRATION_SERVICES_EJBHOME);
        IntegrationServicesHome integrationServicesHome = (IntegrationServicesHome)
        PortableRemoteObject.narrow(ref, IntegrationServicesHome.class);
        mIntegrationServicesRef = integrationServicesHome.create();
        
        // Get a reference to the Order Bean
        ref = jndiContext.lookup(JNDINames.ORDER_EJBHOME);
        OrderHome orderHome = (OrderHome)
        PortableRemoteObject.narrow(ref, OrderHome.class);
        mOrderRef = orderHome.create();
        
        // Get a reference to the Account Bean
        ref = jndiContext.lookup(JNDINames.ACCOUNT_EJBHOME);
        AccountHome accountHome = (AccountHome)
        PortableRemoteObject.narrow(ref, AccountHome.class);
        mAccountRef = accountHome.create();
    }
    
    private void setupCitFileMapping(){
        RemittanceData del = RemittanceData.createValue();
        del.getPaymentReferenceNumber();
        
        //Header records, these are duplicated throught the file, so we will have
        //to ge through and get rid of the duplicates at the end.
        FixedWidthFieldDef[] lHeaderCharRanges = new FixedWidthFieldDef[7];
        lHeaderCharRanges[0] = new FixedWidthFieldDef(6,10,"transactionDate","MMdd",null,null); //date of extract
        lHeaderCharRanges[1] = new FixedWidthFieldDef(24,39,"payeeErpAccount",null,null,"0");  //client customer number
        lHeaderCharRanges[2] = new FixedWidthFieldDef(39,40,"paymentType",null,null,null); //Activity Indicator Code
        lHeaderCharRanges[3] = new FixedWidthFieldDef(40,46,"paymentPostDate","MMddyy",null,null);  //Accounting Date
        lHeaderCharRanges[4] = new FixedWidthFieldDef(46,54,"paymentReferenceNumber",null,"0",null); //Customer Check Number
        lHeaderCharRanges[5] = new FixedWidthFieldDef(54,63,"totalPaymentAmount",null,"0",null); //Customer Check Amount
        lHeaderCharRanges[6] = new FixedWidthFieldDef(94,97,"transactionCd",null,"0",null); //Transaction Code
        this.addFilter(lHeaderCharRanges,"com.cleanwise.service.api.value.RemittanceData",11,13,"25");
        
        //gets the detail records (invoices)
        FixedWidthFieldDef[] lDetailCharRanges = new FixedWidthFieldDef[8];
        lDetailCharRanges[0] = new FixedWidthFieldDef(84,91,"siteReference",null,null,null); //Store Number
        lDetailCharRanges[1] = new FixedWidthFieldDef(97,105,"invoiceNumber",null,"0",null); //item number
        lDetailCharRanges[2] = new FixedWidthFieldDef(120,121,"invoiceType",null,null,null); //Debit/Credit Advice Id
        lDetailCharRanges[3] = new FixedWidthFieldDef(127,136,"discountAmount",null,"0",null);  //Discount Amount
        lDetailCharRanges[4] = new FixedWidthFieldDef(136,145,"netAmount",null,"0",null);  //Net Item Amount
        lDetailCharRanges[5] = new FixedWidthFieldDef(39,40,"paymentTypeCd",null,null,null); //Activity Indicator Code
        lDetailCharRanges[6] = new FixedWidthFieldDef(94,97,"transactionCd",null,"0",null); //Transaction Code
        lDetailCharRanges[7] = new FixedWidthFieldDef(63,78,"referenceInvoiceNumber",null,null,null);
        this.addFilter(lDetailCharRanges,"com.cleanwise.service.api.value.RemittanceDetailData",11,13,"25");
        
        
        //the total record in the file
        FixedWidthFieldDef[] lTotalsCharRanges = new FixedWidthFieldDef[9];
        lTotalsCharRanges[0] = new FixedWidthFieldDef(0,4,"groupClientNumber",null,null,null);
        lTotalsCharRanges[1] = new FixedWidthFieldDef(4,6,"envelopeNumber",null,null,null);
        lTotalsCharRanges[2] = new FixedWidthFieldDef(6,10,"dateOfExtract","MMdd",null,null);
        lTotalsCharRanges[3] = new FixedWidthFieldDef(10,11,"dataFormatIndicator",null,null,null);
        lTotalsCharRanges[4] = new FixedWidthFieldDef(11,13,"formatType",null,null,null);
        lTotalsCharRanges[5] = new FixedWidthFieldDef(13,17,"clientNumber",null,null,null);
        lTotalsCharRanges[6] = new FixedWidthFieldDef(39,46,"numberNameAddrRecords",null,null,null);
        lTotalsCharRanges[7] = new FixedWidthFieldDef(46,53,"numberARDetailRecords",null,null,null);
        lTotalsCharRanges[8] = new FixedWidthFieldDef(64,75,"totalAmount",null,null,null);
        this.addFilter(lTotalsCharRanges,"com.cleanwise.service.apps.loaders.CITTransmissionTotal",11,13,"82");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String debug = System.getProperty("debug");
        
        if ( debug != null && debug.equalsIgnoreCase("true") ) {
            mDebug = true;
        }
        String lFileName = System.getProperty("ifile");
        String lStoreStr = System.getProperty("estore");
        if(!Utility.isSet(lStoreStr)){
            lStoreStr = "1";
        }
        if (!Utility.isSet(lFileName) || !Utility.isSet(lStoreStr)){
            String usage = "Usage: java "+CITAccountsPayableLoader.class.getName()+" -Difile=in.txt -Destore=1 -Ddebug=false";
            log.info(usage);
            return;
        }
        CITAccountsPayableLoader loader = new CITAccountsPayableLoader();
        try{
            loader.mStoreId = Integer.parseInt(lStoreStr);
        }catch (NumberFormatException e){
            log.info("sStore property must be numeric");
            return;
        }
        loader.setupCitFileMapping();
        loader.setDateFormatPattern("MMDDYY");
        try{
            loader.setUp();
        }catch(Exception e){
            e.printStackTrace();
            log.info("setup failure, no database changes");
            return;
        }
        try{
            loader.parse(lFileName);
        }catch (Exception e){
            //this will essentially just add a message to the database that is not
            //attached to a remittance
            loader.processUnfilteredLine("Caught Error: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    //value objects do not have an "equals" method, so this is mimiced here
    //for the properties we care about
    private boolean remittanceDataEqual(RemittanceData val1, RemittanceData val2, boolean simpleEqual){
        if(val1.getPayeeErpAccount() != null){
            if (val2.getPayeeErpAccount() != null){
                if(!val1.getPayeeErpAccount().equals(val2.getPayeeErpAccount())){
                    return false;
                }
            }else{
                return false;
            }
        } else if(val2.getPayeeErpAccount() == null){
            return false;
        }

        
        if(val1.getPaymentReferenceNumber() != null){
            if (val2.getPaymentReferenceNumber() != null){
                if(!val1.getPaymentReferenceNumber().equals(val2.getPaymentReferenceNumber())){
                    return false;
                }
            }else{
                return false;
            }
        } else if(val2.getPaymentReferenceNumber() == null){
            return false;
        }

        if(simpleEqual){
            return true;
        }
        
        if(!(val1.getRemittanceStatusCd().equals(RefCodeNames.REMITTANCE_STATUS_CD.PENDING)
        || val2.getRemittanceStatusCd().equals(RefCodeNames.REMITTANCE_STATUS_CD.PENDING))){
            if(val1.getPaymentPostDate() != null){
                if (val2.getPaymentPostDate() != null){
                    if(!val1.getPaymentPostDate().equals(val2.getPaymentPostDate())){
                        return false;
                    }
                }else{
                    return false;
                }
            } else if(val2.getPaymentPostDate() == null){
                return false;
            }
        }

        
        //Cit will often times send zero referencing anouther invoice, so zero is not really
        //something to call these not equal, as it just means that it is not being sent.  Anything
        //other than zero is significant and should be treated as so.
        BigDecimal zero = new BigDecimal(0.00);
        if(!(zero.equals(val1.getTotalPaymentAmount()) || zero.equals(val2.getTotalPaymentAmount()))){
            if(!(val1.getRemittanceStatusCd().equals(RefCodeNames.REMITTANCE_STATUS_CD.PENDING)
            || val2.getRemittanceStatusCd().equals(RefCodeNames.REMITTANCE_STATUS_CD.PENDING))){
                if(val1.getTotalPaymentAmount() != null){
                    if (val2.getTotalPaymentAmount() != null){
                        if(!val1.getTotalPaymentAmount().equals(val2.getTotalPaymentAmount())){
                            return false;
                        }
                    }else{
                        return false;
                    }
                } else if(val2.getTotalPaymentAmount() == null){
                    return false;
                }
            }
        }
        return true;
    }
    
    
    //value objects do not have an "equals" method, so this is mimiced here
    //for the properties we care about
    private boolean remittanceDetailDataEqual(RemittanceDetailData val1, RemittanceDetailData val2){
        if(val1.getSiteReference() != null){
            if (val2.getSiteReference() != null){
                if(!val1.getSiteReference().equals(val2.getSiteReference())){
                    return false;
                }
            }else{
                return false;
            }
        } else if(val2.getSiteReference() == null){
            return false;
        }
        if(val1.getInvoiceNumber() != null){
            if (val2.getInvoiceNumber() != null){
                if(!val1.getInvoiceNumber().equals(val2.getInvoiceNumber())){
                    return false;
                }
            }else{
                return false;
            }
        } else if(val2.getInvoiceNumber() == null){
            return false;
        }
        if(val1.getInvoiceType() != null){
            if (val2.getInvoiceType() != null){
                if(!val1.getInvoiceType().equals(val2.getInvoiceType())){
                    return false;
                }
            }else{
                return false;
            }
        } else if(val2.getInvoiceType() == null){
            return false;
        }
        if(val1.getDiscountAmount() != null){
            if (val2.getDiscountAmount() != null){
                if(!val1.getDiscountAmount().equals(val2.getDiscountAmount())){
                    return false;
                }
            }else{
                return false;
            }
        } else if(val2.getDiscountAmount() == null){
            return false;
        }
        if(val1.getNetAmount() != null){
            if (val2.getNetAmount() != null){
                if(!val1.getNetAmount().equals(val2.getNetAmount())){
                    return false;
                }
            }else{
                return false;
            }
        } else if(val2.getNetAmount() == null){
            return false;
        }
        return true;
    }
    
    /*
     *Processes a new remittance.  Returns false if all was a success true if there was an error.
     *May also return an in memory remittance
     */
    private boolean processNewRemittance(RemittanceData lRData, boolean simpleEqual) throws Exception{
        boolean foundError = false;
        //check if it is in the db already
        RemittanceDataVector rdv;
        if(!Utility.isSet(lRData.getPaymentReferenceNumber())){
            lRData.setPaymentReferenceNumber(getGUIDRemittanceReferenceNumber(lRData));
            lRData.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED_ERROR);
        }
        RemittanceCriteriaView rCrit = RemittanceCriteriaView.createValue();
        rCrit.setRemittancePayeeErpAccount(lRData.getPayeeErpAccount());
        rCrit.setRemittancePaymentReferenceNumber(lRData.getPaymentReferenceNumber());
        rCrit.setRemittanceStartAddDate(getCutoffTime());
        
        rdv = mRemittanceRef.getRemittanceData(rCrit);
        if (rdv.size() == 0){
            lRData = mRemittanceRef.addRemittance(lRData,mUserName);
            mProcessedRemittances.add(lRData);
        } else if (rdv.size() == 1){
            RemittanceData scratchRdd = (RemittanceData) rdv.get(0);
            if(!simpleEqual){
                if (!remittanceDataEqual(lRData,scratchRdd,simpleEqual)){
                    addRemitError(scratchRdd,"Remittance found in file: "+lRData.toString()
                    + " does not match Remittance in database: " + scratchRdd.toString()
                    + "\nOn Line" + 
                    this.getCurrentLineNumber());
                    foundError = true;

                }
            }
            
            //handle recieving these remittances out of order
            //deal with pending status
            if(scratchRdd.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.PENDING) && !lRData.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.PENDING)){
                scratchRdd.setRemittanceStatusCd(lRData.getRemittanceStatusCd());
                addRemittanceUpdate(scratchRdd);
                if(scratchRdd.getRemittanceId() == 0){
                    Thread.currentThread().dumpStack();
                    System.exit(0);
                }
            }else if(!scratchRdd.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.PENDING) && lRData.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.PENDING)){
                lRData.setRemittanceStatusCd(scratchRdd.getRemittanceStatusCd());
                addRemittanceUpdate(lRData);
            }
            
            //deal with information status
            if(scratchRdd.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY) && !lRData.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY)){
                scratchRdd.setRemittanceStatusCd(lRData.getRemittanceStatusCd());
                addRemittanceUpdate(scratchRdd);
            }else if(!scratchRdd.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY) && lRData.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY)){
                lRData.setRemittanceStatusCd(scratchRdd.getRemittanceStatusCd());
                addRemittanceUpdate(lRData);
            }
            lRData = scratchRdd;
            mProcessedRemittances.add(lRData);
        } else {
            processUnfilteredLine("Found Multiple RemittanceData objects for criteria: " + lRData + "::" + getWorkingLine());
        }
        
        //deal with information status
        if(mRData.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY) && !lRData.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY)){
            lRData.setRemittanceStatusCd(mRData.getRemittanceStatusCd());
            addRemittanceUpdate(lRData);
            
        }else if(!mRData.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY) && lRData.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY)){
            lRData.setRemittanceStatusCd(mRData.getRemittanceStatusCd());
            addRemittanceUpdate(lRData);
        }
        mRData = lRData;
        return foundError;
    }
    
    
    //Use the map when we are doing something due to a cloned remittance, which would happen if we picked
    //up a remittance detail for an account other than the one specified by the erp number in the file. 
    //This occurs when one corporate office pays for many of our "accounts" on one check.
    private void processSpecialTransactionCodes(StringBuffer note,boolean useMap) throws Exception{
        String lineTransactionCode = mRData.getTransactionCd();
        //String lineTransactionCode = this.getWorkingLine().substring(94,97);
        //this means that they may be applying a payment to a previously recieved
        //transaction and using a different reference number...so we have to map it
        //back to the original, which is in the "item" field.
        if(lineTransactionCode.equals("300")){
            String newPaymentReferenceNumber = null;
            if(useMap){
                String key = mRData.getPaymentReferenceNumber();
                newPaymentReferenceNumber = (String) mPaymentNumberMapping.get(key);
                if(newPaymentReferenceNumber == null){
                    return;
                }
            }else{
                newPaymentReferenceNumber = getWorkingLine().substring(97,105);
                
            }
            //trim it and remove and begining zeros
            newPaymentReferenceNumber = Utility.trimLeft(newPaymentReferenceNumber.trim(),"0");
            String oldPaymentReferenceNumber = mRData.getPaymentReferenceNumber();
            if(!(newPaymentReferenceNumber.equals(oldPaymentReferenceNumber.trim()))){
                note.append("CIT Payment Reference: " + mRData.getPaymentReferenceNumber());
                mPaymentNumberMapping.put(mRData.getPaymentReferenceNumber(),newPaymentReferenceNumber);
                mRData.setPaymentReferenceNumber(newPaymentReferenceNumber);
                
                //update the remittance now so that the rest of the remittances records find
                //this one under the new paymentReferenceNumber.  Otherwise they may add a
                //new remittance header record erroniously
                mRemittanceRef.updateRemittance(mRData, mUserName);
                
                String tmp = (String) mPaymentNumberMapping.get(mRData.getPaymentReferenceNumber());
                RemittanceCriteriaView rCrit = RemittanceCriteriaView.createValue();
                rCrit.setRemittancePayeeErpAccount(mRData.getPayeeErpAccount());
                rCrit.setRemittancePaymentReferenceNumber(mRData.getPaymentReferenceNumber());
                rCrit.setRemittanceStartAddDate(getCutoffTime());
                RemittanceDataVector rdv = mRemittanceRef.getRemittanceData(rCrit);
                //what has happened here is our referece was found after anouther was added
                //and there was a pending remittance lying around.
                if(rdv.size() >= 1){
                    for(int i=0;i<rdv.size();i++){
                        RemittanceData rd = (RemittanceData) rdv.get(i);
                        if (rd.getRemittanceId() != mRData.getRemittanceId()){
                            if(rd.getRemittanceStatusCd().equals(RefCodeNames.REMITTANCE_STATUS_CD.PENDING)){
                                //this is a bad one.
                                mRData.setTotalPaymentAmount(rd.getTotalPaymentAmount());
                                addRemittanceUpdate(mRData);
                                try{
                                    mRemittanceRef.deleteRemittance(rd.getRemittanceId());
                                }catch(DataNotFoundException e){
                                    //this is unexpected, but does not seem to me to be anything
                                    //to worry about, the remittance we were about to delete is
                                    //no longer there
                                }
                            }else{
                                //this is an error condition, this payment exists already and has been processed
                                //give it a new payment reference number and flag it for updating.
                                mRData.setPaymentReferenceNumber(getGUIDRemittanceReferenceNumber(mRData));
                                if(mRData.getTotalPaymentAmount()==null || mRData.getTotalPaymentAmount().equals(new BigDecimal(0))){
                                    mRData.setTotalPaymentAmount(rd.getTotalPaymentAmount());
                                }
                                addRemittanceUpdate(mRData);
                            }
                        }
                    }
                }
            }
        }
    }
    
   
    
    protected void processLine(Object pObj) {
        boolean foundError = false;
        try{
            if (pObj instanceof RemittanceData){
                
                //if this is populated then a note will be added attached to this remittance
                StringBuffer note = new StringBuffer();
                RemittanceData lRData = (RemittanceData) pObj;
                
                
                BigDecimal tot;
                if(lRData.getTotalPaymentAmount() == null){
                    tot = new BigDecimal("0").setScale(2).divide(new BigDecimal(100).setScale(2),
                    BigDecimal.ROUND_HALF_UP);
                }else{
                    tot =lRData.getTotalPaymentAmount().setScale(2).divide(new BigDecimal(100).setScale(2),
                    BigDecimal.ROUND_HALF_UP);
                }
                
                try{
                    if(lRData.getPaymentType().equals("3")){
                        lRData.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED);
                    }else if (lRData.getPaymentType().equals("4")){
                        lRData.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.PENDING);
                    }else if(lRData.getPaymentType().equals("5")){
                        lRData.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY);
                    }else if(lRData.getPaymentType().equals("6")){
                        lRData.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY);
                    }else if(lRData.getPaymentType().equals("7")){
                        lRData.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY);
                    }else if(lRData.getPaymentType().equals("8")){
                        lRData.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY);
                    }else if(lRData.getPaymentType().equals("1")){
                        lRData.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED);
                    }else if(lRData.getPaymentType().equals("2")){
                        lRData.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED);
                    }
                    
                    String lineTransactionCode=lRData.getTransactionCd();
                    if(!lineTransactionCode.equals("300")){
                        String oldPaymentReferenceNumber = (String) mPaymentNumberMapping.get(lRData.getPaymentReferenceNumber());
                        if(oldPaymentReferenceNumber != null){
                            lRData.setPaymentReferenceNumber(oldPaymentReferenceNumber);
                        }
                    }
                    
                    
                    lRData.setTotalPaymentAmount(tot);
                    lRData.setStoreId(mStoreId);
                    lRData.setAddBy("System");
                    lRData.setAddDate(new Date());
                    lRData.setModBy("System");
                    lRData.setModDate(new Date());
                    
                    
                    //set the reference to the file name
                    lRData.setTransactionReference(this.getWorkingFile().getAbsolutePath());
                    //check to see if it is the one we are working on now:
                    
                    if (remittanceDataEqual(mRData,lRData,true) && mRData.getRemittanceId() != 0){
                        mRData.setTransactionCd(lRData.getTransactionCd());
                        mRData.setPaymentType(lRData.getPaymentType());
                        lRData = mRData;
                    } else {
                        foundError = processNewRemittance(lRData,false);
                        mRData.setTransactionCd(lRData.getTransactionCd());
                    }
                    processSpecialTransactionCodes(note,false);
                }catch (RuntimeException e){
                    if(remittanceDataEqual(mRData,lRData,false) && mRData.getRemittanceId() != 0){
                        addRemitError(mRData, e.getMessage());
                    }else{
                        addGeneralError("Could not parse line ["+getCurrentLineNumber()+"]: " + getWorkingLine());
                    }
                }
                
                if (note.length() > 0){
                    addRemitNote(mRData,note.toString());
                }
            } else if (pObj instanceof RemittanceDetailData){
                RemittanceDetailData lRDData = (RemittanceDetailData) pObj;
                mRunningTotal = mRunningTotal.add(lRDData.getNetAmount());
                
                if (mRData.getRemittanceId() == 0){
                    processUnfilteredLine("RemittanceDetailData Record found without parent record::" + getWorkingLine());
                }
                
                String invNum = lRDData.getInvoiceNumber();
                BusEntityData account = null;
                if(invNum != null){
                    invNum = invNum.trim();
                
                    InvoiceCustDataVector icdv = mOrderRef.getInvoiceCustCollection(null,0,invNum,null,null);
                    InvoiceCustData invoice;
                    if(icdv.size() > 0){
                        invoice = (InvoiceCustData) icdv.get(0);
                        Integer key = new Integer(invoice.getAccountId());
                        if(mAccountIDtoErpMapping.containsKey(key)){
                            account = (BusEntityData) mAccountIDtoErpMapping.get(key);
                        }else{
                            try{
                                account = mAccountRef.getAccountBusEntity(invoice.getAccountId());
                            }catch(Exception e){}
                            mAccountIDtoErpMapping.put(key, account);
                        }
                    }
                }
                if(account != null && account.getErpNum() != null){
                    String currErp = mRData.getPayeeErpAccount();
                    if(currErp != null){
                        currErp = currErp.trim();
                    }
                    if(!account.getErpNum().equals(currErp)){
                        String origAcct = mRData.getPayeeErpAccount();
                        int origId = mRData.getRemittanceId();
                        mRData.setPayeeErpAccount(account.getErpNum());
                        mRData.setRemittanceId(0);
                        boolean errorDuringAdd = processNewRemittance(mRData,true);
                        String key = mRData.getPaymentReferenceNumber() + ":" + mRData.getPayeeErpAccount();
                        mAccountPaymentXRef.put(key, origAcct);
                        if(!errorDuringAdd){
                            StringBuffer note = new StringBuffer();
                            processSpecialTransactionCodes(note,true);
                            if (note.length() > 0){
                                addRemitNote(mRData,note.toString());
                            }
                        }else{
                            mRData.setPayeeErpAccount(origAcct);
                            mRData.setRemittanceId(origId);
                        }
                    }
                }

                if(lRDData.getTransactionCd().equals("300")){
                    return;
                }
                if(lRDData.getPaymentTypeCd().equals("4")){
                    return;
                }
                
                if(lRDData.getInvoiceType().equalsIgnoreCase("D")){
                    lRDData.setInvoiceType(RefCodeNames.REMITTANCE_INVOICE_TYPE_CD.INVOICE);
                }else if(lRDData.getInvoiceType().equalsIgnoreCase("C")){
                    //A credit to CIT does not mean the same thing as a credit to us.
                    lRDData.setInvoiceType(RefCodeNames.REMITTANCE_INVOICE_TYPE_CD.OTHER);
                }else{
                    addError(lRDData,"Unrecognized Remittance Invoice Type");
                    if(!mRData.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED_ERROR)){
                        addRemitError(mRData,null);
                    }
                    foundError = true;
                }
                
                //type of 3 indicates a regular payment, type of 4 means we should ignore
                //it, but that is a filter so it will never make it here, all other types,
                //log to database for manual attention.
                if(lRDData.getPaymentTypeCd().equals("3")){
                    lRDData.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED);
                }else if (lRDData.getPaymentTypeCd().equals("1")){
                    if(lRDData.getTransactionCd().equals("260")){ //transaction code type of "Charge to your reserve, which means this was a credit"
                        lRDData.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED);
                        lRDData.setInvoiceType(RefCodeNames.REMITTANCE_INVOICE_TYPE_CD.CREDIT);
                    }else{
                        lRDData.setInvoiceType(RefCodeNames.REMITTANCE_INVOICE_TYPE_CD.SHORT_PAY);
                        lRDData.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.INFORMATION_ONLY);
                    }
                    //}else if (lRDData.getPaymentTypeCd().equals("4")){
                    //    if(lRDData.getTransactionCd().equals("450")){
                    //        mRunningTotal = mRunningTotal.add(lRDData.getNetAmount());
                    //    }
                    //    lRDData.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.INFORMATION_ONLY);
                }else{
                    lRDData.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.INFORMATION_ONLY);
                }
                
                
                
                lRDData.setRemittanceId(mRData.getRemittanceId());
                lRDData.setAddBy("System");
                lRDData.setAddDate(new Date());
                lRDData.setModBy("System");
                lRDData.setModDate(new Date());
                BigDecimal net =lRDData.getNetAmount().setScale(2).divide(new BigDecimal(100).setScale(2),
                BigDecimal.ROUND_HALF_UP);
                lRDData.setNetAmount(net);
                
                
                
                RemittanceCriteriaView rCrit = RemittanceCriteriaView.createValue();
                rCrit.setRemittanceDetailInvoiceNumber(lRDData.getInvoiceNumber());
                rCrit.setRemittanceDetailInvoiceType(lRDData.getInvoiceType());
                rCrit.setRemittanceId(lRDData.getRemittanceId());
                //RemittanceDetailData tmpRDD = mRemittanceRef.getRemittanceDetailDataByInvoiceNum(lRDData.getInvoiceNumber(),lRDData.getInvoiceType(),lRDData.getRemittanceId());
                RemittanceDetailDataVector lRDDV = mRemittanceRef.getRemittanceDetailData(rCrit);
                if (lRDDV.size() == 1){
                    RemittanceDetailData tmpRDD = (RemittanceDetailData) lRDDV.get(0);
                    tmpRDD.setDiscountAmount(lRDData.getDiscountAmount());
                    tmpRDD.setModBy(lRDData.getModBy());
                    tmpRDD.setModDate(lRDData.getModDate());
                    tmpRDD.setNetAmount(lRDData.getNetAmount());
                    tmpRDD.setSiteReference(lRDData.getSiteReference());
                    //what is the status of a return-recieved payment application?
                    if (!(remittanceDetailDataEqual(tmpRDD,lRDData))){
                        addError(lRDData,"Updating exsisting remittance.  File Info: " + tmpRDD + " DB Info: " + lRDData);
                        foundError = true;
                        tmpRDD.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED_ERROR);
                        if(!mRData.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED_ERROR)){
                            addRemitError(mRData,null);
                        }
                    }
                    lRDData = tmpRDD;
                }else if (lRDDV.size() > 1){
                    for(int i=0;i<lRDDV.size();i++){
                        RemittanceDetailData lErrRdd = (RemittanceDetailData)lRDDV.get(i);
                        addError(lErrRdd,"Duplicate old state: " + lErrRdd.getRemittanceDetailStatusCd());
                        if(lErrRdd.getRemittanceDetailStatusCd().equals(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED)){
                            lErrRdd.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED_ERROR);
                        }else {
                            lErrRdd.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.PROCESS_ERP_ERROR);
                        }
                        foundError = true;
                        if(!mRData.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED_ERROR)){
                            addRemitError(mRData,null);
                        }
                    }
                }else{
                    lRDData = mRemittanceRef.addRemittanceDetail(lRDData,mUserName);
                    
                    InterchangeRequestData r = InterchangeRequestData.createValue();
                    ElectronicTransactionData t = ElectronicTransactionData.createValue();
                    t.setAddBy("System");
                    t.setModBy("System");
                    t.setModDate(new Date());
                    t.setAddDate(new Date());
                    t.setKeyString("invoiceType: " + lRDData.getInvoiceType() +
                    " invoice: " + lRDData.getInvoiceNumber() +
                    " Payment Ref: " + mRData.getPaymentReferenceNumber() +
                    " Customer: " + mRData.getPayeeErpAccount());
                    t.setGroupType("RE");
                    t.setSetType("820");
                    //t.setSetStatus();
                    //populate the working line data if there were any errrors
                    if(foundError){
                        t.setSetData(this.getWorkingLine());
                    }
                    ElectronicTransactionDataVector tv = new ElectronicTransactionDataVector();
                    tv.add(t);
                    r.setTransactionDataVector(tv);
                    
                    InterchangeData i = r.getInterchangeData();
                    if (i==null){
                        i = InterchangeData.createValue();
                    }
                    i.setAddBy("system");
                    i.setAddDate(new Date());
                    i.setEdiFileName(getWorkingFile().getName());
                    i.setModBy("system");
                    i.setModDate(new Date());
                    i.setTestInd("P");
                    i.setInterchangeType("INBOUND");
                    r.setInterchangeData(i);
                    mIntegrationRequests.add(r);
                    
                }
                //checks for any errors against this remittance and deals with them
                processRemitErrors(lRDData);
            } else if (pObj instanceof CITTransmissionTotal){
                mTotalsRecord = (CITTransmissionTotal) pObj;
                //CIT will paste multiple files together into one file.  They should be processed
                //seperatly.
                this.postProcessFile();
            }else {
                throw new IllegalArgumentException("pObj is not of a known type: " + pObj.getClass().getName());
            }
            
        }catch (Exception e){
            addGeneralError("Could not parse line ["+getCurrentLineNumber()+"]: " + getWorkingLine());
            e.printStackTrace();
            if(!Utility.isSet(mRData.getPaymentReferenceNumber())){
                mRData.setPaymentReferenceNumber(getGUIDRemittanceReferenceNumber(mRData));
                mRData.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED_ERROR);
            }
        }
    }
    
    protected void processUnfilteredLine(String pLine){
        //type 12 records are address records, and we do not care about these
        //type 72 records are subtotal records, we do not operate off of these
        //either, instead we use the file total record.
        if (!pLine.substring(11,13).equals("12") && !pLine.substring(11,13).equals("72")){
            System.err.println("Could not parse line: " + pLine);
            addGeneralError("Could not parse line ["+getCurrentLineNumber()+"]: " + pLine);
        }
    }
    
    
    protected void postProcessFile() {
        try{
            if(mProcessedRemittances == null || mProcessedRemittances.size()==0 ){
                return;
            }
            //first update all the remittance data that we changed during processing:
            Iterator it = getRemittancUpdates().iterator();
            while(it.hasNext()){
                RemittanceData rd = (RemittanceData) it.next();
                if(rd.getRemittanceId() != 0){
                    mRemittanceRef.updateRemittance(rd,mUserName);
                }
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
            mIntegrationServicesRef.processIntegrationRequests(mIntegrationRequests,null,0);
            
            //re-initialize everything, this is here because of the funky stuff
            //CIT does where they will paste 2 files together that are logically
            //seperate.  
            mRunningTotal = new BigDecimal(0.0);
            mRData = RemittanceData.createValue();
            mIntegrationRequests = new IntegrationRequestsVector();
            mProcessedRemittances = new RemittanceDataVector();
            mErrRemits = new HashMap();
            mPaymentNumberMapping = new HashMap();
            mTotalsRecord = null;
            mAccountIDtoErpMapping = new HashMap();
            mAccountPaymentXRef = new HashMap();
            mHiddenRemittanceUpdates = new ArrayList();

        }catch(Exception e){
            //this is a bad problem
            e.printStackTrace();
        }
    }
    
}

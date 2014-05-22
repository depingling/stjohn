/*
 * OutboundInvoice.java
 *
 * Created on March 21, 2003, 1:12 PM
 */

package com.cleanwise.service.apps.dataexchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.AccountHome;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.IntegrationServicesHome;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.StoreHome;
import com.cleanwise.service.api.util.JNDINames;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.IntegrationRequestsVector;
import com.cleanwise.service.api.value.OutboundEDIRequestData;
import com.cleanwise.service.api.value.OutboundEDIRequestDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.pdf.PdfInvoice;
/**
 *
 * @author  bstevens
 */
public class OutboundInvoice {
	protected Logger log = Logger.getLogger(this.getClass());
    IntegrationServices intSvc;
    Store strRef;
    Account accountRef;

    private void setUp() throws Exception {
        // Check for a properties file command option.
        String propFileName = System.getProperty("conf");
        if ( null == propFileName ) {
            propFileName = "installation.properties";
        }
        Properties props = new Properties();
        java.io.File f = new java.io.File(propFileName);
        log.info(f.getAbsolutePath());
        props.load(new FileInputStream(propFileName) );
        InitialContext jndiContext = new InitialContext(props);
        Object ref;

        // Get a reference to the Integration Services Bean
        ref = jndiContext.lookup(JNDINames.INTEGRATION_SERVICES_EJBHOME);
        IntegrationServicesHome lIntegrationServicesHome = (IntegrationServicesHome)
        PortableRemoteObject.narrow(ref, IntegrationServicesHome.class);
        intSvc = lIntegrationServicesHome.create();


        //setup reference to the store bean
        ref = jndiContext.lookup(JNDINames.STORE_EJBHOME);
        StoreHome lStoreHome = (StoreHome)
        PortableRemoteObject.narrow(ref, StoreHome.class);
        strRef = lStoreHome.create();

        //setup reference to the account bean
        ref = jndiContext.lookup(JNDINames.ACCOUNT_EJBHOME);
        AccountHome lAccountHome = (AccountHome)
        PortableRemoteObject.narrow(ref, AccountHome.class);
        accountRef = lAccountHome.create();
    }

    public void generateOutboundInvoices(OutputStream pOut){
        try{
            ArrayList ignoreAccounts = new ArrayList();
            ignoreAccounts.add(new Integer(99));    //JCI - JCPenney
            ignoreAccounts.add(new Integer(100));   //United States Postal Service
            ignoreAccounts.add(new Integer(101));   //J C Penney Company
            ignoreAccounts.add(new Integer(89417)); //US Postal Service (Number 2)
            ignoreAccounts.add(new Integer(168880)); //US Postal Service (Number 3)
            ignoreAccounts.add(new Integer(88727)); //Somers Building Maintenance
            ignoreAccounts.add(new Integer(89648)); //JCI - Detroit Airport
            ignoreAccounts.add(new Integer(91127)); //JCI - Ericsson
            ignoreAccounts.add(new Integer(95500)); //JCI - Ericsson2
            ignoreAccounts.add(new Integer(89929)); //JCI - Agilent
            ignoreAccounts.add(new Integer(91375)); //JCI - Schwab
            ignoreAccounts.add(new Integer(91877)); //JCI - BP Houston
            ignoreAccounts.add(new Integer(93810)); //JCI - BP Naperville
            ignoreAccounts.add(new Integer(93811)); //JCI - HQ (a.k.a. Johnson Controls Inc)
            ignoreAccounts.add(new Integer(94010)); //J C Penney Type 1
            ignoreAccounts.add(new Integer(88089)); //DMSI - Home Depot/Expo
            ignoreAccounts.add(new Integer(89063)); //DMSI - Fry's Food & Drug
            ignoreAccounts.add(new Integer(89747)); //DMSI - Office Max
            ignoreAccounts.add(new Integer(90111)); //DMSI - Food Lion
            ignoreAccounts.add(new Integer(90112)); //DMSI - Michaels
            ignoreAccounts.add(new Integer(90113)); //DMSI - AutoZone
            ignoreAccounts.add(new Integer(91587)); //DMSI - Schneider
            ignoreAccounts.add(new Integer(91697)); //DMSI - Target
            ignoreAccounts.add(new Integer(91967)); //DMSI - Kroger
            ignoreAccounts.add(new Integer(92449)); //DMSI - Kash N' Karry
            ignoreAccounts.add(new Integer(92528)); //DMSI - Giant Food Stores
            ignoreAccounts.add(new Integer(92608)); //DMSI - Bruno's Supermarkets
            ignoreAccounts.add(new Integer(92779)); //DMSI - Bi-Lo Foods
            ignoreAccounts.add(new Integer(93209)); //DMSI - Rite Aid
            ignoreAccounts.add(new Integer(93400)); //DMSI - Family Dollar Stores
            ignoreAccounts.add(new Integer(93490)); //DMSI - Winn-Dixie Stores
            ignoreAccounts.add(new Integer(97391)); //JCI - AK Steel
            ignoreAccounts.add(new Integer(99250)); //JCI - Merck Data Center
            ignoreAccounts.add(new Integer(99230)); //DMSI - Advance Auto Parts
            // JCI accounts added on 4/25/2005, durval
            ignoreAccounts.add(new Integer(102121)); //JCI - St Clare
            ignoreAccounts.add(new Integer(102720)); //JCI - DHL
            ignoreAccounts.add(new Integer(102721)); //JCI - T-Mobile
            //
            // Per Holly, we are now sending manual invoices to JCI-JD.
            // durval Wed Jun 21 09:34:05 EDT 2006
            //
            //ignoreAccounts.add(new Integer(97961)); //JCI - JohnsonDiversey
           // Per Holly this is an EDI account. Vladimir 08/17/06
            ignoreAccounts.add(new Integer(113992)); //USPS - Capital Expenditures
           // Per Kim this is an EDI account. Vladimir 08/17/06
            ignoreAccounts.add(new Integer(161202)); //JCI - Automotive Group
           // Per Holly this is an EDI account. Vladimir 10/03/06
            ignoreAccounts.add(new Integer(161573)); //JCI - Kodak
            //ignoreAccounts.add(new Integer(174827)); //JCI - Weatherford College
            ignoreAccounts.add(new Integer(181168)); //JCI - Unilever
            ignoreAccounts.add(new Integer(196899)); //JCI - York Norman
            ignoreAccounts.add(new Integer(181288)); //JCI - Manpower
			
            OutboundEDIRequestDataVector allInvoice = new OutboundEDIRequestDataVector();
            //XXX pretty slick here if we looked at the store at this point
            //XXX HArdcoded to only print out CW invoices.  Would need to know where
            //other stores are going, so for now this is a CW only function!
            //AccountDataVector adv = accountRef.getAllAccounts(1, Account.ORDER_BY_NAME);
            ArrayList advid = accountRef.getAccountsForStore(1);
            for(int i=0;i<advid.size();i++){
                Integer aid = (Integer) advid.get(i);
                int busEntityId = aid.intValue();

                if(!(ignoreAccounts.contains(new Integer(busEntityId)))){
                    try{
                    	AccountData ad = (AccountData) accountRef.getAccount(aid.intValue(),1);
                        Integer.parseInt(ad.getBusEntity().getErpNum());
                        if(ad.getBusEntity().getBusEntityStatusCd().equalsIgnoreCase(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE)){
                            log.info("processing account: "+ad.getBusEntity().getShortDesc()+".");
                            OutboundEDIRequestDataVector edi = intSvc.getEDIOrdersByErpNumAndSetType(null,"810",ad.getBusEntity().getBusEntityId());
                            log.info("  Found "+edi.size()+" invoices to process.");
                            allInvoice.addAll(edi);
                        }
                    }catch(NumberFormatException e){
                        log.info("skipping account: "+busEntityId);
                    }
                }
            }

            if(allInvoice.size() > 0){
                log.info("Processing: "+allInvoice.size()+" invoices.");
                PdfInvoice pdf = new PdfInvoice();
                StoreData store = strRef.getStore(1);


                String lImageName = ClwCustomizer.getLogoPathForPrinterDisplay(store);

                pdf.constructPdfInvoice(allInvoice, store, pOut, lImageName);

                IntegrationRequestsVector requests = new IntegrationRequestsVector();
                for(int i=0;i<allInvoice.size();i++){
                    OutboundEDIRequestData rd = (OutboundEDIRequestData) allInvoice.get(i);
                    rd.getInvoiceCustD().setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED);
                    requests.add(rd.getInvoiceCustD());
                }
                intSvc.processIntegrationRequests(requests,null,0);
            }else{
                log.info("Nothing to process");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Command line entry point for program.
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        OutboundInvoice out=new OutboundInvoice();
        try {
            out.setUp();
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        String fileName = "invoices.pdf";
        if(args.length > 0){
            fileName = args[0];
        }
        try{
            File output = new File(fileName);
            FileOutputStream fos = new FileOutputStream(output);
            out.generateOutboundInvoices(fos);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}

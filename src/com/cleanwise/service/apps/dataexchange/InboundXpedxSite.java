/*
 * InboundSiteXls.java
 *
 */
package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;

import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Account;

import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AccountDataVector;


import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.naming.NamingException;

public class InboundXpedxSite extends InboundFlatFile{
    private SiteDataVector sites = new SiteDataVector();

    /**
     *Intercept the requests as we will controll what gets added
     * @throws Exception 
     */
    protected void processParsedObject(Object pParsedObject) throws Exception{
      if(pParsedObject == null)
        throw new IllegalArgumentException("No parsed site object present");

      SiteData sd = null;      

      if (pParsedObject instanceof XpedxSiteView){
        XpedxSiteView xlsData = (XpedxSiteView)pParsedObject;
        checkMandatory(xlsData);
        sd = getSiteIfExist(xlsData);
        sd = XlsToSiteData.parseXlsToSiteData(xlsData, sd);
        if (sd == null)
          throw new IllegalArgumentException("Xls row cann't be parsed into SiteData object");

        try {
          //Getting trading partner id
          TradingPartnerData partner = translator.getPartner();
          if(partner == null)
            throw new IllegalArgumentException("Trading Partner ID cann't be determined");

          AddressData address = sd.getSiteAddress();
          if("USA".equals(address.getCountryCd())) {
             address.setCountryCd(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES);
          }
          //if (!APIAccess.getAPIAccess().getAddressValidatorAPI().validateAddress(address)) {
          //  throw new IllegalArgumentException("Address in the parsed SiteData object isn't valid");
          //}
          sites.add(sd);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
      }
    }

    protected void doPostProcessing() {
        try {
            Site site = APIAccess.getAPIAccess().getSiteAPI();
            site.addSites(sites);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        /*Iterator i = sites.iterator();
        try {
            Site site = APIAccess.getAPIAccess().getSiteAPI();
            while (i.hasNext()) {
             SiteData sd = (SiteData)i.next();
             int accountId = sd.getAccountBusEntity().getBusEntityId();
             sd = site.addSite(sd, accountId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } */
    }

    private void checkMandatory(XpedxSiteView xlsData) throws IllegalArgumentException {
        String errMessage = "Error parsing file for account id "+ xlsData.getAccountId() + ": mandatory field ";
        if (xlsData.getAccountId() <= 0 ) {
            throw new IllegalArgumentException("Account ID not found in the parsig file.");
        }
        if (!Utility.isSet(xlsData.getSiteName())) {
            throw new IllegalArgumentException(errMessage + "Site Name");
        }
        if (!Utility.isSet(xlsData.getSiteBudgetRefNumber())) {
            throw new IllegalArgumentException(errMessage + "Site Budget Ref Number");
        }
        if (xlsData.getTaxeble() == null) {
            throw new IllegalArgumentException(errMessage + "Taxable");
        }
        if (xlsData.getShareBuyerOrderGuides() == null) {
            throw new IllegalArgumentException(errMessage + "Share Buyer Order Guides");
        }
        if (!Utility.isSet(xlsData.getAddress1())) {
            throw new IllegalArgumentException(errMessage + "Address 1");
        }
        if (!Utility.isSet(xlsData.getCity())) {
            throw new IllegalArgumentException(errMessage + "City");
        }
        if (!Utility.isSet(xlsData.getState())) {
            throw new IllegalArgumentException(errMessage + "State");
        }
        if (!Utility.isSet(xlsData.getPostalCode())) {
            throw new IllegalArgumentException(errMessage + "Postal Code");
        }
        if (!Utility.isSet(xlsData.getCountry())) {
            throw new IllegalArgumentException(errMessage + "Country");
        }
    }


    private SiteData getSiteIfExist(XpedxSiteView xlsData)
    throws Exception{
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        String siteRefNum = xlsData.getSiteBudgetRefNumber();
        int accountId = xlsData.getAccountId();
        SiteDataVector siteDV =
           siteEjb.getSiteByProperty(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER, siteRefNum, accountId);
        if(siteDV.size() <= 0) {
            return null;
        }
        SiteData siteD = (SiteData) siteDV.get(0);
        return siteD;
    }


}

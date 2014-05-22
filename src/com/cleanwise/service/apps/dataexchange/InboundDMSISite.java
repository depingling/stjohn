package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import com.cleanwise.service.api.APIAccess;

import java.rmi.RemoteException;
import java.util.*;

import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Account;
import java.util.Iterator;

import org.apache.log4j.Logger;


public class InboundDMSISite extends InboundFlatFile {
	protected Logger log = Logger.getLogger(this.getClass());
  private SiteDataVector sites = new SiteDataVector();
  private List siteBudgetRefNumbers = new ArrayList();
  private DMSISiteView siteView;

  /**
   * Called when the object has successfully been parsed
   */
    protected void processParsedObject(Object pParsedObject)  throws Exception{
    	
        if(pParsedObject == null) {
            throw new IllegalArgumentException("No parsed site object present");
        }

        SiteData sd = null;
        int accountId = -1;
        int storeId = -1;

        if (pParsedObject instanceof DMSISiteView){
            siteView = (DMSISiteView)pParsedObject;

            // to avoid duplacate site name found since the file often contain duplicated row
            if (siteBudgetRefNumbers.contains(siteView.getSiteBudgetRefNumber()))
                  return;
            else
            	siteBudgetRefNumbers.add(siteView.getSiteBudgetRefNumber());

            try {
                //Getting trading partner id
                TradingPartnerData partner = translator.getPartner();
                if(partner == null) {
                    throw new IllegalArgumentException("Trading Partner ID cann't be determined");
                }

                int tradingPartnerId = partner.getTradingPartnerId();

                // checking account
                if(partner.getTradingPartnerTypeCd().equals(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT)) {
                //Getting all accounts associated with trading partner, if it has type of ACCOUNT
                    int[] accountIds = translator.getTradingPartnerBusEntityIds(tradingPartnerId,
                                                            RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
                    if(accountIds == null || accountIds.length < 1) {
                      throw new IllegalArgumentException("No accounts present for current trading partner id = " +
                        tradingPartnerId);
                    }
                    if(accountIds.length > 1) {
                      throw new Exception("Multiple accounts present for current trading partner id = " +
                        tradingPartnerId);
                    }
                    accountId = accountIds[0];
                } else if(partner.getTradingPartnerTypeCd().equals(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE)) {
                    /*Getting all accounts from CLW_BUS_ENTITY_ASSOC if trading partner has type STORE
                     At first we get store id*/
                    int storeIds[] = translator.getTradingPartnerBusEntityIds(tradingPartnerId,
                                                                    RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
                    if(storeIds == null || storeIds.length < 1) {
                      throw new IllegalArgumentException("No stores present for current trading partner id = " +
                        tradingPartnerId);
                    }
                    if(storeIds.length > 1) {
                      throw new Exception("Multiple stores present for current trading partner id = " +
                        tradingPartnerId);
                    }
                    Account accountEjb = translator.getAccountBean();
                    storeId = storeIds[0];
                    List accountIdList = accountEjb.getAccountsForStore(storeId);
                    if(accountIdList == null || accountIdList.size() < 1) {
                      throw new Exception(
                        "Store doesn't have accounts. Store id: "+storeIds[0]);
                    }
                    if(accountIdList.size() > 1) {
                      //throw new Exception(
                      //  "Store has multiple accounts but suppose to have only one. Store id: "+storeIds[0]);
                    }else{
                    	accountId = ((Integer)accountIdList.get(0)).intValue();
                    }
                } else {
                  throw new IllegalArgumentException("Trading partner should has type of ACCOUNT or STORE");
                }

                if(accountId > 0){
                	sd = getSiteIfExist(siteView.getSiteBudgetRefNumber(), accountId);
                }else{
                	sd = getSiteIfExistForStore(siteView.getSiteBudgetRefNumber(), storeId);
                }
                sd = parseDMSISiteViewToSiteData(siteView, sd);

                if (sd == null) {
                    throw new IllegalArgumentException("Txt row cann't be parsed into SiteData object");
                }


                sd.getAccountBusEntity().setBusEntityId(accountId);

                AddressData address = sd.getSiteAddress();
                if("USA".equals(address.getCountryCd())) {
                        address.setCountryCd(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES);
                }
                //if (!APIAccess.getAPIAccess().getAddressValidatorAPI().validateAddress(address))
                //    throw new IllegalArgumentException("Address in the parsed SiteData object isn't valid");


                sites.add(sd);

            } catch (RemoteException e) {
            	log.error(e.getMessage(),e);
            }
        }
    }


   protected void doPostProcessing() {
       Iterator i = sites.iterator();
       try {
           Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
           while (i.hasNext()) {
            SiteData site = (SiteData)i.next();
            int accountId = site.getAccountBusEntity().getBusEntityId();
            try {
            	site = siteEjb.addSite(site, accountId);
            }catch (DuplicateNameException e){
            	site.getBusEntity().setShortDesc(site.getBusEntity().getShortDesc() + "-" + siteView.getSiteBudgetRefNumber());
            	site = siteEjb.addSite(site, accountId);
            }
           }
       } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException(e.getMessage());
       }
   }



   private SiteData getSiteIfExistForStore(String siteRefNum, int accountId)
   throws Exception{
       Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
       SiteDataVector siteDV =
          siteEjb.getSiteByProperty(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER, siteRefNum, accountId);
       if(siteDV.size() <= 0) {
           return null;
       }
       SiteData siteD = (SiteData) siteDV.get(0);
       return siteD;
   }

    private SiteData getSiteIfExist(String siteRefNum, int accountId)
    throws Exception{
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        SiteDataVector siteDV =
           siteEjb.getSiteByProperty(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER, siteRefNum, accountId);
        if(siteDV.size() <= 0) {
            return null;
        }
        SiteData siteD = (SiteData) siteDV.get(0);
        return siteD;
    }


   public SiteData parseDMSISiteViewToSiteData(DMSISiteView pSiteView, SiteData pSiteData) {
      if (pSiteView == null) {
        throw new IllegalArgumentException("Site bean was not populated from txt file");
      }

      Date runDate = new Date();
      boolean isNew = false;
      if (pSiteData == null) {
        pSiteData = SiteData.createValue();
        isNew = true;
      }
      String cuser = "siteLoader";

      // getting address info
      AddressData siteAddressD = pSiteData.getSiteAddress();
      siteAddressD.setAddress1(pSiteView.getAddress1());
      siteAddressD.setAddress2(pSiteView.getAddress2());
      siteAddressD.setAddress3(pSiteView.getAddress3());
      siteAddressD.setCity(pSiteView.getCity());
      siteAddressD.setStateProvinceCd(pSiteView.getStateProvinceCd());
      siteAddressD.setPostalCode(pSiteView.getPostalCode());
      siteAddressD.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
      siteAddressD.setModBy(cuser);
      siteAddressD.setModDate(runDate);
      if (isNew) {
        siteAddressD.setAddBy(cuser);
        siteAddressD.setAddDate(runDate);
        siteAddressD.setCountryCd(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES);
        siteAddressD.setName1("N/A");
        siteAddressD.setName2("N/A");
        siteAddressD.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        siteAddressD.setPrimaryInd(true);
      }

      pSiteData.setSiteAddress(siteAddressD);

      // getting short description
      BusEntityData busEntityD = pSiteData.getBusEntity();
      busEntityD.setShortDesc(pSiteView.getSiteDescription());

      busEntityD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
      busEntityD.setShortDesc(pSiteView.getSiteDescription());
      // USA by default
      busEntityD.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);

      busEntityD.setEffDate(runDate);
      busEntityD.setModDate(runDate);
      busEntityD.setModBy(cuser);
      if (isNew) {
        busEntityD.setAddBy(cuser);
        busEntityD.setAddDate(runDate);
        busEntityD.setLocaleCd("unk");
      }


      // set properties
      PropertyDataVector props = pSiteData.getMiscProperties();

      // site reference number
      String budgetPrefNum = pSiteView.getSiteBudgetRefNumber();
      if (isNew) {
      PropertyData nprop = PropertyData.createValue();
      nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
      nprop.setValue(budgetPrefNum);
      props.add(nprop);
      }

      // taxable property
      PropertyData taxableIndicator = pSiteData.getTaxableIndicator();
      taxableIndicator.setValue(pSiteView.getTaxeble());


      // default property share_order_guides
      if (isNew) {
    	  PropertyData nprop = PropertyData.createValue();
          nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
          nprop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
          nprop.setValue("true");
          props.add(nprop);
      }

      return pSiteData;
    }

}


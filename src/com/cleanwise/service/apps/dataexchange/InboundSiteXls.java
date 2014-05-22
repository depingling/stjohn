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

import javax.naming.NamingException;

public class InboundSiteXls extends InboundFlatFile{
    /**
     *Overides to look for a set of text to ignore
     * @throws Exception 
     */
    public void parseLine(List pParsedLine) throws Exception{
      HashMap columnMap = getColumMap();
      if(pParsedLine == null)
        return;

      //Verifing that minimum number of mandatory values are present
      int parsedSize = pParsedLine.size();
      for (int i = 0; i < columnMap.size(); i++){
        TradingPropertyMapData map = (TradingPropertyMapData) columnMap.get(new Integer(i));
        if(map != null && Utility.isTrue(map.getMandatory())){
          if(parsedSize < i)
            return;
          Object value = pParsedLine.get(i);
          if(value == null)
                throw new IllegalArgumentException("Error parsing column: "+ i + " it is mandority property");
        }
      }
        super.parseLine(pParsedLine);
    }


    /**
     *Intercept the requests as we will controll what gets added
     * @throws Exception 
     */
    protected void processParsedObject(Object pParsedObject) throws Exception{
      if(pParsedObject == null)
        throw new IllegalArgumentException("No parsed site object present");

      SiteData sd = null;      
      int accountId = -1;

      if (pParsedObject instanceof SiteXlsData){
        SiteXlsData xlsData = (SiteXlsData)pParsedObject;          
        sd = XlsToSiteData.parseXlsToSiteData(xlsData);
        if (sd == null)
          throw new IllegalArgumentException("Xls row cann't be parsed into SiteData object");

        try {
          //Getting trading partner id
          TradingPartnerData partner = translator.getPartner();
          if(partner == null)
            throw new IllegalArgumentException("Trading Partner ID cann't be determined");

          int tradingPartnerId = partner.getTradingPartnerId();

          boolean found = false;
          String custMaj = xlsData.getCustMaj(); //parameter identify account in CLW_PROPERTY
          if(custMaj != null){
            int pos = custMaj.indexOf(".");
            if (pos > 0)
              custMaj = custMaj.substring(0, pos);
          } else {
            return;
          }

          PropertyService property = translator.getPropertyServiceEjb();
          //Getting all accounts by cust_maj property (CLW_PROPERTY)
          ArrayList accountByCustMaj = null;
          try{
            accountByCustMaj = property.getBusEntityVector(RefCodeNames.PROPERTY_TYPE_CD.CUST_MAJ, custMaj);
          }catch(DataNotFoundException dex){
            log.info("Data Not found for CUST_MAJ");
          }catch(RemoteException rex){
            log.error("Remote Exception occured during getting account ids by CUST_MAJ",rex);
          }
          if(accountByCustMaj==null || accountByCustMaj.size() < 1)
            throw new IllegalArgumentException("No accounts found with CUST_MAJ = " + custMaj);
          int [] accountIds;
          if(partner.getTradingPartnerTypeCd().equals("ACCOUNT")){
            //Getting all accounts associated with trading partner, if it has type ACCOUNT
            accountIds = translator.getTradingPartnerBusEntityIds(tradingPartnerId, "ACCOUNT");
            if(accountIds == null || accountIds.length < 1)
              throw new IllegalArgumentException("No uniq. accound id present for current trading partner id = " + tradingPartnerId);
          }else if(partner.getTradingPartnerTypeCd().equals("STORE")){
            //Getting all accounts from CLW_BUS_ENTITY_ASSOC if trading partner has type STORE
            accountIds = translator.getTradingPartnerBusEntityIds(tradingPartnerId, "STORE");
            if(accountIds == null || accountIds.length != 1)
              throw new IllegalArgumentException("Trading partner with type STORE should be associated with one store");

            Account mAccBean = translator.getAccountBean();
            AccountDataVector accountData = mAccBean.getAllAccounts(accountIds[0], Account.ORDER_BY_ID);
            if(accountData == null || accountData.size() < 0)
              throw new IllegalArgumentException("No account found for store id=" + accountIds[0]);
            AccountData [] account = null;
            accountData.toArray(account);
            log.info("====TP has typy of STORE parsing store's account to Account Data array");
            accountIds = new int[accountData.size()];
            for (int j = 0; j < account.length; j++){
              accountIds[j] = account[j].getBusEntity().getBusEntityId();
            }

          }else{
            throw new IllegalArgumentException("Trading partner should has type of ACCOUNT or STORE");
          }

          for(int i = 0; i < accountByCustMaj.size(); i++){
            for (int j = 0; j < accountIds.length; j++){
              if ( ( (Integer) accountByCustMaj.get(i)).intValue() == accountIds[j]){
                accountId = accountIds[j];
                found = true;
                break;
              }
            }
          }

          if(!found)
            throw new IllegalArgumentException(" Account ID with CUST MAJ=" + custMaj
                                               + "  not found for trading partner id=" + tradingPartnerId);
          //Catalog should exist... Verify this
          if(!isCatalogValid(xlsData, accountId))
            throw new IllegalArgumentException("Catalog doesn't exist");

          AddressData address = sd.getSiteAddress();
          //if (!APIAccess.getAPIAccess().getAddressValidatorAPI().validateAddress(address)) {
          //  throw new IllegalArgumentException("Address in the parsed SiteData object isn't valid");
         // }
          Site site = APIAccess.getAPIAccess().getSiteAPI();
          site.addSite(sd, accountId);
        } catch (RemoteException e) {
        	log.error(e.getMessage(),e);
        } catch (DuplicateNameException e) {
        	log.error(e.getMessage(),e);
        }
      }
    }

    protected void doPostProcessing(){
    }

    /*
    *The function returns true if catalog exist or no information about catalog present in SiteXlsData bean
    */
    private boolean isCatalogValid(SiteXlsData xlsData, int accountId) throws APIServiceAccessException, NamingException{
      if(xlsData == null)
        return false;

      //We should verify catalog only when all of the catalog's ids present
      //overwise return true
      String mbrMajor = xlsData.getMbrMajor();
      String mbrMinor = xlsData.getMbrMinor();
      String memberName = xlsData.getPrimaryMemberName();
      // If properties for catalog identification were not populated assume all fine
      if(mbrMajor == null && mbrMinor == null && memberName == null)
        return true;

      StringBuffer catalogName = new StringBuffer();
      int pos;

      if(mbrMajor == null || mbrMajor.trim().length() == 0)
        return false;
      else {
        pos = mbrMajor.indexOf(".");
        if(pos > 0)
          mbrMajor = mbrMajor.substring(0, pos);

        catalogName.append(mbrMajor);
        catalogName.append("-");
      }

      if(mbrMinor == null || mbrMinor.trim().length() == 0)
        return false;
      else {
        pos = mbrMinor.indexOf(".");
        if(pos > 0)
          mbrMinor = mbrMinor.substring(0, pos);

        catalogName.append(mbrMinor);
        catalogName.append(" ");
      }
      if(memberName == null || memberName.trim().length() == 0)
        return false;
      else
        catalogName.append(memberName);

      CatalogInformation catalogInfo = APIAccess.getAPIAccess().getCatalogInformationAPI();
      EntitySearchCriteria crit = new EntitySearchCriteria();
      CatalogDataVector catalogList = new CatalogDataVector();

      ArrayList types = new ArrayList();
      types.add(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
      crit.setSearchTypeCds(types);

      crit.setSearchNameType(crit.NAME_CONTAINS);
      crit.setSearchName(new String(catalogName));

      IdVector accountIdV = new IdVector();
      accountIdV.add(new Integer(accountId));
      crit.setAccountBusEntityIds(accountIdV);
      try {
        catalogList = catalogInfo.getCatalogsByCrit(crit);
        log.info("Searching catalog with name = " + catalogName);
      }catch (RemoteException e) {
    	  log.error(e.getMessage(),e);
      }

      if(catalogList != null && catalogList.size() > 0)
        return true;
      else
        return false;
    }
}

package com.cleanwise.service.apps.dataexchange;



import com.cleanwise.service.api.util.*;

import com.cleanwise.service.api.value.*;



import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;

import com.cleanwise.service.api.dao.BusEntityDataAccess;

import com.cleanwise.service.api.dao.CatalogAssocDataAccess;

import com.cleanwise.service.api.dao.CatalogDataAccess;

import com.cleanwise.service.api.session.Account;

import com.cleanwise.service.api.session.CatalogInformation;

import com.cleanwise.service.api.session.Distributor;

import com.cleanwise.service.api.session.Site;

import com.cleanwise.service.api.session.Catalog;

import com.cleanwise.service.api.session.TradingPartner;

import com.cleanwise.service.api.session.PropertyService;

import com.cleanwise.service.api.APIAccess;

import com.cleanwise.service.api.APIServiceAccessException;



import com.cleanwise.service.apps.dataexchange.InboundSiteTxt.Report;

import com.cleanwise.service.apps.loaders.TabFileParser;



import java.io.InputStream;

import java.util.*;

import java.rmi.RemoteException;



import javax.naming.NamingException;



import org.apache.log4j.Logger;





public class InboundKranzSiteTxt extends InboundSiteTxt {

    protected Logger log = Logger.getLogger(InboundKranzSiteTxt.class);



    protected boolean isValid(SiteTxtData parsedData, int line) {

        boolean valid = super.isValid(parsedData, line);

        if (!Utility.isSet(parsedData.getCatalogName())) {

            addError("Empty Catalog Name.Line: " + line);

            valid = false;

        }



        if (!Utility.isSet(parsedData.getMemberNumber())) {

            addError("Empty Member Number.Line: " + line);

            valid = false;

        }



        return valid;

    }



    protected void doPostProcessing() throws Exception {

        log.info("doPostProcessing => BEGIN.");

        log.info("doPostProcessing => parsedMap.size:" + parsedMap.size());



        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();

        Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();

        PropertyService propertyEjb = APIAccess.getAPIAccess().getPropertyServiceAPI();

        Catalog catalogEjb = APIAccess.getAPIAccess().getCatalogAPI();



        Report report = getReport();

        Integer storeId = getStoreId();



        HashSet<NscSiteView> siteForUpdates = new HashSet<NscSiteView>();

        HashSet<NscSiteView> siteForRemoves = new HashSet<NscSiteView>();

        HashSet<NscSiteView> siteForAdds = new HashSet<NscSiteView>();



        Set<String> custMajsSet = getAllCustMajs();



        for (String custMaj : custMajsSet) {

        	try {

                siteForUpdates.clear();

                siteForRemoves.clear();

                siteForAdds.clear();

                int accountId = getAccountIdByCustmaj(storeId,accountEjb, custMaj).intValue();

                if (accountId==0){

                	addError("No accounts found with Customer Number: '" + custMaj + "'.");

                	continue;

                }

                NscSiteViewVector allSites = siteEjb.getAllNscSites(accountId, custMaj);

 //               log.info("doPostProcessing => allSites = " + ((allSites != null)?allSites.size():"null" ));

                siteEjb.populateNscSiteProperties(allSites);

 //               log.info("doPostProcessing => allSites WITH PROPERTIES = " + ((allSites != null)?allSites.size():"null" ));

                HashMap<String, SiteTxtData> inboundSiteMap = getInboundSiteMap(custMaj);



                HashSet<String> siteRefNumHS = new HashSet<String>();

                for (Object oSite : allSites) {



                    NscSiteView sysSite = (NscSiteView) oSite;

                    String siteRefNum = sysSite.getSiteReferenceNumber();

                    if (Utility.isSet(siteRefNum)) {

                        if(siteRefNumHS.contains(siteRefNum)) {

                            continue;

                        }

                        siteRefNumHS.add(siteRefNum);



                        SiteTxtData inboundSite = inboundSiteMap.remove(sysSite.getSiteReferenceNumber());

                        if (inboundSite != null) {

                            if (setChanges(inboundSite, sysSite)) {



                                siteForUpdates.add(sysSite);

                                report.setUpdated(report.getUpdated() + 1);



                            } else {

                                report.setNotchanged(report.getNotchanged() + 1);

                            }

                        } else {

                            if (!RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE.equals(sysSite.getStatus())) {

                                siteForRemoves.add(sysSite);

                                report.setRemoved(report.getRemoved() + 1);

                            }

                        }

                    }

                }



                siteForAdds.addAll(createNscSiteCollection(accountId, custMaj, inboundSiteMap.values()));

                report.setAdded(siteForAdds.size());



                removeSites(siteEjb, siteForRemoves);

                updateSites(siteEjb, siteForUpdates);

                addSites(siteEjb, siteForAdds);



                // add or update catalog and catalog assoc data for site

                log.info("doPostProcessing => catalogs updating START" );

                allSites = siteEjb.getAllNscSites(accountId, custMaj);

                log.info("doPostProcessing => allSites = " + allSites.toString());

                CatalogDataVector allCatalogs = getAllCatalogsForAccount(accountId);

                log.info("doPostProcessing => allCatalogs = " + allCatalogs.toString());

                inboundSiteMap = parsedMap.get(custMaj);

                createOrUpdateCatalogs(catalogEjb, inboundSiteMap, allSites, allCatalogs, storeId.intValue() );

                log.info("doPostProcessing => catalogs updating FINISH" );



            } catch (Exception e) {

                e.printStackTrace();

                addError(e.getMessage());

            }

        }



        log.info("doPostProcessing => END.");



    }

    protected boolean setChanges(SiteTxtData pInboundSite, NscSiteView pSysSite) {

  //     log.info("setChanges => BEGIN; ") ;

    	boolean changed = super.setChanges(pInboundSite, pSysSite);

  //      log.info("setChanges => super : changed = " + changed ) ;



    	PropertyDataVector props = null;

    	if (Utility.isSet(pInboundSite.getNscLocationNumber()) ) {

        	props = pSysSite.getProperties();

        	String sysNscLocationNumber = "";

        	if (props != null && props.size()>0){

    	    	// Update existing property

        		for (Object oProp : props){

    	    		PropertyData nscProp = (PropertyData)oProp;

    	    		if ("NSC Location Number".equals(nscProp.getShortDesc())){

    	    			sysNscLocationNumber = nscProp.getValue();

 //   	    	        log.info("setChanges => pInboundSite.getNscLocationNumber()/sysNscLocationNumber =" + pInboundSite.getNscLocationNumber() + "/" + strNN(sysNscLocationNumber)+";") ;

    	    	       	if (!pInboundSite.getNscLocationNumber().equals(strNN(sysNscLocationNumber))){

    	    	       		nscProp.setValue(pInboundSite.getNscLocationNumber());

    	    	       		pSysSite.setProperties(props);

        	    	       	changed = true;

    	            	}

    	    		}

    	    	}

        	} else {

    	    	// add property

        		PropertyData nscProp = createSiteProperty("NSC Location Number", pInboundSite.getNscLocationNumber(),pSysSite.getSiteId() );

        		props = new PropertyDataVector();

        		props.add(nscProp);

        		pSysSite.setProperties(props);

        		changed = true;

        	}

        }

   //     log.info("setChanges => END  changed =  " + changed) ;

        return changed;

    }



    protected NscSiteView createNscSite(int pAccountId, String pCustMaj, SiteTxtData pSiteTxtData) {

    	NscSiteView sysSite = super.createNscSite(pAccountId, pCustMaj, pSiteTxtData);

        if (Utility.isSet(pSiteTxtData.getNscLocationNumber()) ) {

        	PropertyDataVector props = new PropertyDataVector();

        	PropertyData prop = createSiteProperty("NSC Location Number", pSiteTxtData.getNscLocationNumber(), 0);

        	props.add(prop);

        	sysSite.setProperties(props);

        }

    	return sysSite;

    }



    private PropertyData createSiteProperty(String pName , String pValue, int pSiteId){

       	PropertyData p = PropertyData.createValue();

    	p.setShortDesc(pName);

        p.setValue(pValue);

        p.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

        p.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);

        p.setBusEntityId(pSiteId);

        p.setAddBy(getModBy());

        return p;

    }



    private void createOrUpdateCatalogs(Catalog catalogEjb, HashMap<String, SiteTxtData> inboundSiteMap,

    		                              NscSiteViewVector allSites, CatalogDataVector allCatalogs, int pStoreId ) throws Exception {



        log.info("createOrUpdateCatalogs => BEGIN." );



    	if (inboundSiteMap == null || allSites == null || allCatalogs == null){

    		return;

    	}

       	// creating inboundSiteCatalogMap : Location Number -> Catalog Name

    	HashMap<String, String> inboundSiteCatalogMap = new HashMap<String, String>();

        Set<String> locationNumberSet = inboundSiteMap.keySet();

 //       log.info("createOrUpdateCatalogs => locationNumberSet = " + locationNumberSet.size());



        for (String locationNumber : locationNumberSet) {

        	SiteTxtData txtData = (SiteTxtData)inboundSiteMap.get(locationNumber);

 //           log.info("createOrUpdateCatalogs => locationNumber/txtData = " + locationNumber +" / "+ ((txtData != null)?txtData.getCatalogName():"null" ));

        	if (txtData != null){

	        	String catalogName = txtData.getCatalogName();

				inboundSiteCatalogMap.put(locationNumber, txtData.getCatalogName());

        	}

        }

//        log.info("createOrUpdateCatalogs => inboundSiteCatalogMap = " + inboundSiteCatalogMap.toString() );



    	HashMap<String, NscSiteView> siteDataMap = createSiteDataMap(allSites) ;

    	//

    	HashMap<Integer, Integer> siteCatalogAssocMap = new HashMap<Integer, Integer>();



    	// fill up site-catalog associations map for existing pairs

    	for (String locationNumber : locationNumberSet) {

        	SiteTxtData txtData = (SiteTxtData)inboundSiteMap.get(locationNumber);

        	String catalogName = txtData.getCatalogName();

        	for (Object oSite : allSites) {

            	NscSiteView sysSite = (NscSiteView) oSite;

                for (Object oCatalog : allCatalogs) {

                	CatalogData sysCatalog = (CatalogData)oCatalog;

	        		if (locationNumber.equals(sysSite.getSiteReferenceNumber())){

	        			if (catalogName.equals(sysCatalog.getShortDesc())){

	        				siteCatalogAssocMap.put(new Integer(sysSite.getSiteId()),new Integer(sysCatalog.getCatalogId()));

		        			inboundSiteCatalogMap.remove(locationNumber);

	        			}

	        		}

                }

        	}

        }

        log.info("createOrUpdateCatalogs => EXISTING : siteCatalogAssocMap = " + siteCatalogAssocMap.toString() );

        log.info("createOrUpdateCatalogs => TO ADD   : inboundSiteCatalogMap = " + inboundSiteCatalogMap.toString() );



        //--------------------------------------------------

        int distrId = this.getDistributor(pStoreId);

    	// create Catalogs for catalog names from the inboundSiteCatalogMap if such 'SHOPPING' catalog

        // does not exist for account

    	if (inboundSiteCatalogMap != null && inboundSiteCatalogMap.size() > 0){

	    	Set<String> keySet = inboundSiteCatalogMap.keySet();
                HashMap createdCatalogsHM = new HashMap();
	    	for (String key : keySet) {

	    		String catalogNameVal = inboundSiteCatalogMap.get(key);

//	    		CatalogData catalog = createCatalogData(catalogNameVal);

	    		try {

	    			int accountId = siteDataMap.get(key).getAccountId();

		   		int siteId = siteDataMap.get(key).getSiteId();
                                int catalogId = 0;
                                Integer catalogIdObj = (Integer)createdCatalogsHM.get(catalogNameVal);
                                if (catalogIdObj == null) {
                                  catalogId = catalogEjb.createCatalog( catalogNameVal, RefCodeNames.CATALOG_TYPE_CD.SHOPPING, RefCodeNames.CATALOG_STATUS_CD.ACTIVE,  getModBy());
                                  catalogEjb.createCatalogAssoc(catalogId, pStoreId,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE ,getModBy());
                                  catalogEjb.createCatalogAssoc(catalogId, accountId,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT ,getModBy());
                                  catalogEjb.createCatalogAssoc(catalogId, distrId,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR ,getModBy());
                                  catalogEjb.createCatalogAssoc(catalogId, distrId,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR ,getModBy());
                                  catalogEjb.createCatalogContractAndOGAssociation(accountId,catalogId, catalogNameVal,RefCodeNames.CATALOG_STATUS_CD.ACTIVE ,getModBy());
                                  createdCatalogsHM.put(catalogNameVal, new Integer(catalogId)) ;
                                } else {
                                  catalogId = catalogIdObj.intValue();
                                }

	    			log.info("createOrUpdateCatalogs => NEW : catalog Id = " + catalogId );
	   			siteCatalogAssocMap.put(new Integer(siteId), new Integer(catalogId));

	    		} catch (DuplicateNameException ex){

	    			addError(ex.getMessage());

	    		}

	     	}

    	}

    	// create or update Site-Catalog Association for all changed pairs ;

    	if (siteCatalogAssocMap != null && siteCatalogAssocMap.size() > 0 ){

	    	IdVector siteIds = new IdVector();

	    	Set<Integer> siteIdSet = siteCatalogAssocMap.keySet();

	   		siteIds.addAll(siteIdSet);

	   		CatalogAssocDataVector catalogAssocDV = catalogEjb.getCatalogAssocCollection(siteIds, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

	   		HashMap<Integer, CatalogAssocData> caDMap = createSiteCatalogAssocDataMap(catalogAssocDV);

	   		for (Object oSiteId : siteIdSet) {

	   			Integer siteId = (Integer)oSiteId;

   				int updCatalogId = siteCatalogAssocMap.get(siteId).intValue();

	   			if (caDMap == null ) {

	    	        log.info("createOrUpdateCatalogs => NEW ASSOC: site Id / catalog Id = " + siteId + "/" + updCatalogId );

	   				catalogEjb.addCatalogAssoc(updCatalogId, siteId, getModBy());



	   			} else {

	   				CatalogAssocData caD = caDMap.get(siteId);

	   				if (caDMap.get(siteId) != null && caDMap.get(siteId).getCatalogId() != updCatalogId) {

		    	        log.info("createOrUpdateCatalogs => UPDATE ASSOC: site Id / catalog Id = " + siteId + "/" + updCatalogId );

	   					catalogEjb.updateCatalogAssoc(caD, updCatalogId, siteId, caD.getCatalogAssocId());

	   				}

	   			}

	   		}

    	}



    }



    private CatalogDataVector getAllCatalogsForAccount(int pAccountId) throws Exception {



        CatalogDataVector allCatalogsDV = null;

        Catalog catalogEjb = APIAccess.getAPIAccess().getCatalogAPI();

        DBCriteria critSubSub = new DBCriteria();

        critSubSub.addEqualTo( BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

        critSubSub.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pAccountId);

        String subSql1 = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, critSubSub);



        DBCriteria critSub = new DBCriteria();

        critSub.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, subSql1);

        String subSql2 = BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID, critSub);



        DBCriteria crit = new DBCriteria();

        crit.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.SHOPPING);

        crit.addJoinTable(CatalogAssocDataAccess.CLW_CATALOG_ASSOC);

        crit.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

        crit.addJoinCondition(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID, CatalogDataAccess.CLW_CATALOG, CatalogDataAccess.CATALOG_ID);

        DBCriteria dbc = new DBCriteria();

        dbc.addCondition(CatalogAssocDataAccess.CLW_CATALOG_ASSOC+ "."+CatalogAssocDataAccess.BUS_ENTITY_ID + " IN( "+ subSql2 + ")");

        crit.addIsolatedCriterita(dbc);



      	allCatalogsDV = catalogEjb.getCatalogCollection(crit);

		return allCatalogsDV;

    }

    private CatalogData createCatalogData(String catalogName) {

    	CatalogData cd = CatalogData.createValue();

    	cd.setCatalogStatusCd(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

    	cd.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);

    	cd.setShortDesc(catalogName);



    	return cd;

    }



    private HashMap<String, NscSiteView> createSiteDataMap(NscSiteViewVector allSites){

    	HashMap<String, NscSiteView> map = new HashMap<String, NscSiteView>();

    	for (Object oSite : allSites) {

    		NscSiteView sysSite = (NscSiteView) oSite;

    		map.put(sysSite.getSiteReferenceNumber(), sysSite);

    	}

    	return map;

    }

    private HashMap<Integer, CatalogAssocData> createSiteCatalogAssocDataMap(CatalogAssocDataVector catalogAssocDV){

    	HashMap<Integer, CatalogAssocData> map = null;

    	if (catalogAssocDV != null && catalogAssocDV.size() > 0 ) {

//        	log.info("createSiteCatalogAssocDataMap => BEGIN : catalogAssocDV = " + catalogAssocDV.toString());

    		map = new HashMap<Integer, CatalogAssocData>();

	    	for (Object oCatalogAssocD : catalogAssocDV) {

	    		CatalogAssocData caD = (CatalogAssocData) oCatalogAssocD;

	    		map.put(new Integer(caD.getBusEntityId()), caD);

	    	}

    	}

//    	log.info("createSiteCatalogAssocDataMap => END : map = "+ map.toString() );

    	return map;

    }

    private int getDistributor( int storeId) throws Exception{

        //Get distributor

    	Distributor distrEjb = APIAccess.getAPIAccess().getDistributorAPI();

    	BusEntityDataVector beDV = distrEjb.getNscStoreDistributor(storeId);

        if(beDV.size()==0) {

            throw new IllegalArgumentException(

                    "No disributor found for the store. Store id : " + storeId);

        }

        if(beDV.size()>1) {

            throw new IllegalArgumentException(

                    "Multiple disributors found for the store (should be one. Store id : " + storeId);

        }

        BusEntityData beD = (BusEntityData) beDV.get(0);

        int distributorId = beD.getBusEntityId();

        return distributorId;

    }



    protected String getModBy() {

        return "InboundKranzSiteTxt";

    }

 }


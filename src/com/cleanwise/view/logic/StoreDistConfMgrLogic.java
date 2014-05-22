/*
 * StoreCatalogConfMgrLogic.java
 *
 * Created on July 18, 2005, 4:19 PM
 */

package com.cleanwise.view.logic;

import java.math.BigDecimal;
import java.util.*;
import java.rmi.RemoteException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.StoreCatalogMgrForm;
import com.cleanwise.view.forms.StoreDistMgrDetailForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.dao.CatalogAssocDataAccess;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;


/**
 *
 * @author Ykupershmidt
 */
public class StoreDistConfMgrLogic {

    /**
     *  <code>init</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void init(HttpServletRequest request,
                          ActionForm form)
        throws Exception {

      // reset all the saved data
      HttpSession session = request.getSession();
      session.removeAttribute("distributor.catalogs.vector");
      session.removeAttribute("distributor.sites.vector");
    }

    /**
     *  <code>search</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void search(HttpServletRequest request,
                              ActionForm form)
             throws Exception {

       init(request, form);
       StoreDistMgrDetailForm configForm = (StoreDistMgrDetailForm)form;
       String configType = Utility.strNN(configForm.getConfType());

       if (configType.equals("Catalogs")) {
         searchCatalog(request, form);
       } else if (configType.equals("Sites")) {
         searchSite(request, form);
       }
   }


    public static ActionErrors searchCatalog(HttpServletRequest request,
              ActionForm form)
    throws Exception {
      ActionErrors ae = new ActionErrors();
      HttpSession session = request.getSession();
      APIAccess factory = new APIAccess();
      StoreDistMgrDetailForm pForm = (StoreDistMgrDetailForm)form;
      String searchField = pForm.getConfSearchField();
      String searchType = pForm.getConfSearchType();

      int storeId = Integer.parseInt(pForm.getStoreId());
      int distributorId = Integer.parseInt(pForm.getId());

      CatalogInformation cati = factory.getCatalogInformationAPI();

      CatalogDataVector catalogDV =  new CatalogDataVector();
      CatalogDataVector catalogAssocDV;

      if (!Utility.isSet(searchField)) {
          if(!pForm.getConfShowConfguredOnlyFl()) {
            catalogDV = cati.getCatalogsCollectionByBusEntity(storeId);
          }
          catalogAssocDV = cati.getCatalogsCollectionByBusEntity(distributorId);
      } else {
          if(searchType.equals("nameBegins")) {
            if(!pForm.getConfShowConfguredOnlyFl()) {
              catalogDV = cati.getCatalogsCollectionByNameAndBusEntity(searchField, SearchCriteria.BEGINS_WITH_IGNORE_CASE, storeId);
            }
            catalogAssocDV = cati.getCatalogsCollectionByNameAndBusEntity(searchField, SearchCriteria.BEGINS_WITH_IGNORE_CASE, distributorId);
          } else { // "nameContains"
            if(!pForm.getConfShowConfguredOnlyFl()) {
              catalogDV = cati.getCatalogsCollectionByNameAndBusEntity(searchField, SearchCriteria.CONTAINS_IGNORE_CASE, storeId);
            }
            catalogAssocDV = cati.getCatalogsCollectionByNameAndBusEntity(searchField, SearchCriteria.CONTAINS_IGNORE_CASE, distributorId);
          }
      }

      if(!pForm.getConfShowConfguredOnlyFl()) {
        for(Iterator iter=catalogDV.iterator(); iter.hasNext();) {
          CatalogData cD = (CatalogData) iter.next();
          String catalogStatus = cD.getCatalogStatusCd();
          if(!RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(catalogStatus) &&
             !RefCodeNames.CATALOG_STATUS_CD.LIMITED.equals(catalogStatus) ) {
            iter.remove();
            continue;
          }
          for(Iterator iter2=catalogAssocDV.iterator(); iter2.hasNext();) {
            CatalogData acD = (CatalogData) iter2.next();
            if (acD.getCatalogId() == cD.getCatalogId()) {
                iter.remove();
            }
          }
        }
      }

      IdVector assocCatalogIdV = new IdVector();
      if (catalogAssocDV.size() > 0) {
        String[] selected = new String[catalogAssocDV.size()];
        int ind = 0;
        for(Iterator iter = catalogAssocDV.iterator(); iter.hasNext();) {
          CatalogData cata = (CatalogData)iter.next();
          selected[ind++] =  ""+cata.getCatalogId();
          assocCatalogIdV.add(new Integer(cata.getCatalogId()));
        }
        pForm.setSelectIds(selected);
      }

      if (!pForm.getConfShowConfguredOnlyFl()) {
        for(Iterator iter=catalogAssocDV.iterator(); iter.hasNext();) {
          CatalogData cD = (CatalogData) iter.next();
          catalogDV.add(cD);
        }
        session.setAttribute("distributor.catalogs.vector", catalogDV);
      } else {
        session.setAttribute("distributor.catalogs.vector", catalogAssocDV);
      }


      session.setAttribute("distributor.assoccatalog.ids", assocCatalogIdV);
      return ae;
    }



  /**
     *  <code>searchSite</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static void searchSite(HttpServletRequest request,
                  ActionForm form)
    throws Exception {

  HttpSession session = request.getSession();
  StoreDistMgrDetailForm pForm = (StoreDistMgrDetailForm)form;

  String fieldValue = pForm.getConfSearchField();
  String searchType = pForm.getConfSearchType();
  String state = pForm.getConfState();
  String city = pForm.getConfCity();
  String county = pForm.getConfCounty();
  String zip = pForm.getConfZipcode();
  int storeId = Integer.parseInt(pForm.getStoreId());
  int distributorId = Integer.parseInt(pForm.getId());

  APIAccess factory = new APIAccess();
  Site siteBean = factory.getSiteAPI();

  SiteViewVector dv;
  SiteViewVector assocSites;

  QueryRequest qr = new QueryRequest();
  boolean siteByNameFl = true;
  if(Utility.isSet(fieldValue)) {
    if(searchType.equals("id")) {
      siteByNameFl = false;
      int id = 0;
      try {
        id = Integer.parseInt(fieldValue.trim());
      } catch(Exception exc){}
      qr.filterBySiteId(id);
    } else {
      if (searchType.equals("nameBegins")) {
        qr.filterBySiteName(fieldValue, QueryRequest.BEGINS_IGNORE_CASE);
      } else if (searchType.equals("nameContains")) {
        qr.filterBySiteName(fieldValue, QueryRequest.CONTAINS_IGNORE_CASE);
      }
    }
  }
  if (Utility.isSet(city)) {
        siteByNameFl = false;
        qr.filterByCity(city.trim(), QueryRequest.BEGINS_IGNORE_CASE);
  }
  if (Utility.isSet(county)) {
        siteByNameFl = false;
        qr.filterByCounty(county.trim(), QueryRequest.BEGINS_IGNORE_CASE);
  }
  if (Utility.isSet(state)) {
        siteByNameFl = false;
        qr.filterByState(state.trim(), QueryRequest.BEGINS_IGNORE_CASE);
  }
  if (Utility.isSet(zip)) {
        siteByNameFl = false;
        qr.filterByZip(zip.trim(), QueryRequest.BEGINS_IGNORE_CASE);
  }

  ArrayList statusAL = new ArrayList();
  statusAL.add(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
  statusAL.add(RefCodeNames.BUS_ENTITY_STATUS_CD.LIMITED);

  qr.filterBySiteStatusCdList(statusAL);

  IdVector storeIdV = new IdVector();
  storeIdV.add(new Integer(storeId));
  qr.filterByStoreIds(storeIdV);
  qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);

  if(siteByNameFl && Utility.isSet(fieldValue)) {
        int match = (searchType.equals("nameBegins"))?Site.BEGINS_WITH_IGNORE_CASE:Site.CONTAINS_IGNORE_CASE;
        dv = siteBean.getSiteByNameDeprecated(fieldValue.trim(),0,storeId,false, match,Site.ORDER_BY_NAME);
        assocSites = siteBean.getSiteCollectionByDistributor(fieldValue.trim(), match, distributorId);
  } else {
        dv = siteBean.getSiteCollection(qr);
        assocSites = siteBean.getSiteCollectionByDistributor(qr, distributorId);
  }


      if(!pForm.getConfShowConfguredOnlyFl()) {
        if (dv != null && dv.size() > 0 && assocSites != null && assocSites.size() > 0) {
          for(Iterator iter=dv.iterator(); iter.hasNext();) {
            SiteView site = (SiteView) iter.next();
            for(Iterator iter2=assocSites.iterator(); iter2.hasNext();) {
              SiteView aSite = (SiteView) iter2.next();
              if (site.getId() == aSite.getId()) {
                iter.remove();
              }
            }
          }
        }
      }

      IdVector assocSitesIdV = new IdVector();
      if (assocSites != null && assocSites.size() > 0) {
        String[] selected = new String[assocSites.size()];
        int ind = 0;
        for(Iterator iter = assocSites.iterator(); iter.hasNext();) {
          SiteView site = (SiteView)iter.next();
          selected[ind++] =  "" + site.getId();
          assocSitesIdV.add(new Integer(site.getId()));
        }
        pForm.setSelectIds(selected);
      }

      if (!pForm.getConfShowConfguredOnlyFl()) {
        for(Iterator iter=assocSites.iterator(); iter.hasNext();) {
          SiteView site = (SiteView) iter.next();
          dv.add(site);
        }
        session.setAttribute("distributor.sites.vector", dv);
      } else {
        session.setAttribute("distributor.sites.vector", assocSites);
      }


      session.setAttribute("distributor.assocsites.ids", assocSitesIdV);

  }


  /**
     * <code>update</code> method.
     *
     * @param  request        a <code>HttpServletRequest</code> value
     * @param  form           an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception  Exception  if an error occurs
     */
  public static ActionErrors update(HttpServletRequest request,
                      ActionForm form)
    throws Exception {

    ActionErrors ae =  new ActionErrors();
    HttpSession session = request.getSession();

    StoreDistMgrDetailForm pForm = (StoreDistMgrDetailForm)form;
      String configType = pForm.getConfType();

      if (configType.equals("Catalogs")) {
          ae = updateCatalog(request, form);
      } else if (configType.equals("Sites")) {
          ae = updateSite(request, form);
      }
      return ae;
  }

    /**
     *  <code>updateDist</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */

  public static ActionErrors updateCatalog(HttpServletRequest request,
                  ActionForm form) throws Exception {
   ActionErrors ae = new ActionErrors();
   HttpSession session = request.getSession();
   APIAccess factory = new APIAccess();
   StoreDistMgrDetailForm pForm = (StoreDistMgrDetailForm)form;

   //int storeId = pForm.getStoreId();
   String user =(String)session.getAttribute(Constants.USER_NAME);
   int distributorId = Integer.parseInt(pForm.getId());


   Catalog catalogBean = factory.getCatalogAPI();
   CatalogInformation catinfo=factory.getCatalogInformationAPI();

   // get list of catalog ids displayed
   String[] displayed = pForm.getDisplayIds();
   // get list of catalog ids selected
   String[] selected = pForm.getSelectIds();

   // get list of currently associated dist ids
   IdVector assocCatalogIds = (IdVector)session.getAttribute("distributor.assoccatalog.ids");

   // Looking for two cases:
   // 1. catalog is selected, but not currently associated - this
   //    means we need to add the association
   // 2. catalog is not selected, but is currently associated - this
   //    means we need to remove the association

   for (int i = 0; i < displayed.length; i++) {
       String did = displayed[i];
       boolean foundFl = false;
       for (int j = 0; j < selected.length; j++) {
           if (did.equals(selected[j])) {
             foundFl = true;
             break;
           }
       }
       Integer idI = new Integer(did);
       int assocIndex = assocCatalogIds.indexOf(idI);
       if (foundFl) {
           if (assocIndex < 0) {
             // we need to add the association, the selected list
             // has the id, but not on assoc sites list
         try {
                catalogBean.addCatalogAssoc(idI.intValue(), distributorId,
                                 RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR);
         } catch (Exception exc) {
           String errorMess = exc.getMessage();
           int ind1 = -1;
           int ind2 = -1;
           if(errorMess==null) throw exc;
           ind1 = errorMess.indexOf("^clw^");
           if(ind1>=0) {
             ind2 = errorMess.indexOf("^clw^", ind1+3);
             if(ind2>0) {
              errorMess = errorMess.substring(ind1+5,ind2);
              ae.add("error",new ActionError("error.simpleGenericError",errorMess));
              return ae;
             }
           }
           throw exc;
         }
             assocCatalogIds.add(idI);
           }
       } else if (assocIndex >= 0) {
           catalogBean.removeCatalogAssoc(idI.intValue(), distributorId, user);
           assocCatalogIds.remove(assocIndex);
       }
     }

    // update with changes
      session.setAttribute("distributor.assoccatalog.ids", assocCatalogIds);
      selected = new String[assocCatalogIds.size()];

      int ind = 0;
      for(Iterator iter = assocCatalogIds.iterator(); iter.hasNext();) {
        selected[ind++] =  iter.next().toString();
      }
      pForm.setSelectIds(selected);

    return ae;
    }


    /**
     *  <code>updateSite</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static ActionErrors updateSite(HttpServletRequest request,
                  ActionForm form)
    throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    String user =(String)session.getAttribute(Constants.USER_NAME);
    StoreDistMgrDetailForm pForm = (StoreDistMgrDetailForm)form;

    int distributorId = Integer.parseInt(pForm.getId());
    int storeId = Integer.parseInt(pForm.getStoreId());

    Site siteBean = factory.getSiteAPI();

    // get list of site ids displayed
    String[] displayed = pForm.getDisplayIds();
    // get list of site ids selected
    String[] selected = pForm.getSelectIds();

    // get list of currently associated site ids
    IdVector assocSiteIds = (IdVector)session.getAttribute("distributor.assocsites.ids");

    // Looking for two cases:
    // 1. site is selected, but not currently associated - this
    //    means we need to add the association
    // 2. site is not selected, but is currently associated - this
    //    means we need to remove the association
    ErrorHolderViewVector err = new ErrorHolderViewVector();
    for (int ii = 0; ii < displayed.length; ii++) {
        String did = displayed[ii];
        boolean foundId = false;
        for (int jj = 0; jj < selected.length; jj++) {
          if (did.equals(selected[jj])) {
            foundId = true;
            break;
          }
        }
        int id = Integer.parseInt(did);
        Integer idI = new Integer(id);
        int assocIndex = assocSiteIds.indexOf(idI);
        if (foundId) {
            if (assocIndex < 0) {
                if(pForm.getConfMoveSitesFl()) {
                    siteBean.removeDistributorAssoc(id, distributorId, user);
                }
                try {
                    siteBean.addDistributorAssoc(id, distributorId, user, err);
                } catch (Exception exc) {
                    String errorMess = exc.getMessage();
                    int ind1 = -1;
                    int ind2 = -1;
                    if(errorMess==null) throw exc;
                    ind1 = errorMess.indexOf("^clw^");
                    if(ind1>=0) {
                        ind2 = errorMess.indexOf("^clw^", ind1+3);
                        if(ind2>0) {
                            errorMess = errorMess.substring(ind1+5,ind2);
                            ae.add("error",new ActionError("error.simpleGenericError",errorMess));
                            return ae;
                        }
                    }
                    throw exc;
                }
                assocSiteIds.add(idI);
            }
        } else if (assocIndex >= 0) {
            // we need to remove the association, the selected list
            // doesn't have the id, but it is on the assoc catalogs list
            // (means it was 'unselected')
            siteBean.removeDistributorAssoc(id, distributorId, user);
            assocSiteIds.remove(assocIndex);
        }
      }

      // update with changes
      session.setAttribute("distributor.siteassoc.ids", assocSiteIds);
      selected = new String[assocSiteIds.size()];

      int ind = 0;
      for (Iterator iter = assocSiteIds.iterator(); iter.hasNext();) {
        selected[ind++] = new String(iter.next().toString());
      }
      pForm.setSelectIds(selected);

    return ae;
  }

    /**
     *  <code>sort</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
     /*
  public static void sort(HttpServletRequest request,
                ActionForm form)
    throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();

      StoreCatalogMgrForm configForm = (StoreCatalogMgrForm)form;
      String configType = request.getParameter("configType");

      if (configType.equals("Items")) {
        //sortItemDist(request, form);
      } else if (configType.equals("Distributors")) {
        //sortDist(request, form);
      } else if (configType.equals("Sites")) {
          sortSite(request, form);
      }

     return;
  }
  */


  /**
     *  <code>sortSite</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
     /*
  public static void sortSite(HttpServletRequest request,
                ActionForm form)
    throws Exception {

        // Get a reference to the admin facade
    HttpSession session = request.getSession();

      StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;

      String sortField = request.getParameter("sortField");

      SiteDataVector sites =
        (SiteDataVector)session.getAttribute("distributor.sites.vector");
      if (sites == null) {
        return;
      }
      DisplayListSort.sort(sites, sortField);

      // Need to init the selected/checked sites
      IdVector assocDistIds =
        (IdVector)session.getAttribute("distributor.siteassoc.ids");
      String[] selected = new String[assocDistIds.size()];

      int ind = 0;
      for (Iterator iter = assocDistIds.iterator(); iter.hasNext();) {
        selected[ind++] = new String(iter.next().toString());
      }
      pForm.setSelectIds(selected);

      // so that the display correctly shows the type of config being done
      pForm.setConfType("Sites");

    return;
  }    */

/*    private static ActionErrors testCatalogAssoc(int pBusEntityId)
    throws Exception{

    ActionErrors ae = new ActionErrors();

    APIAccess factory = new APIAccess();

    CatalogInformation catalogInfoBean = factory.getCatalogInformationAPI();
    CatalogDataVector catVec = new CatalogDataVector();
    CatalogData cat = null;
    Integer id = new Integer(pBusEntityId);

    catVec = catalogInfoBean.getCatalogsCollectionByBusEntity(pBusEntityId);

    for(int j=0; j<catVec.size(); j++){

        cat = (CatalogData)catVec.get(j);

        String stat = cat.getCatalogStatusCd();
        String type = cat.getCatalogTypeCd();
        //if any attached accounts are active, we can't attach
        if(stat.equals(RefCodeNames.CATALOG_STATUS_CD.ACTIVE) && type.equals("ACCOUNT")){
        Integer catId = new Integer(cat.getCatalogId());
        String s1 = catId.toString();
        String s2 = id.toString();
        ae.add("Distributor Config",
               new ActionError("distributor.config.error", s1, s2));

        }
    }

    return ae;

    }
  */

    private static final String DIST_STORE_MAP_CACHE =  "distributor.store.map.cache";
    /**
     *updates the caching between stores and distributors
     */
    private static void resetStoreDistributorCache(HttpSession pSession,
                                                   int pDistributorId,
                                                   int pStoreId) {
        if (pDistributorId == 0) {
            return;
        }
        Map storeMap = (HashMap) pSession.getAttribute(DIST_STORE_MAP_CACHE);
        if (storeMap != null) {
            Integer key = new Integer(pDistributorId);
            storeMap.remove(key);
        }
    }


    public static ActionErrors updateDistributorConfiguration(HttpServletRequest
            request,
                                                              ActionForm form) throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();

        HttpSession session = request.getSession();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        BigDecimal invoiceAmountPercentAllowanceUpper = null;
        BigDecimal invoiceAmountPercentAllowanceLower = null;
        BigDecimal allowedFreightSurchargeAmount = null;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                APP_USER);
        int storeId = Integer.parseInt(sForm.getStoreId());

        if (sForm != null) {
            if (Utility.isSet(sForm.getInvoiceAmountPercentAllowanceUpper())) {
                try {
                    invoiceAmountPercentAllowanceUpper = new BigDecimal(sForm.
                            getInvoiceAmountPercentAllowanceUpper());
                } catch (NumberFormatException e) {
                    lUpdateErrors.add("invoiceLoadingPriceExceptionThreshold",
                            new ActionError("error.invalidAmount",
                                    "Invoice Loading Price Exception Threshold"));
                }
            }
        }
        if (Utility.isSet(sForm.getInvoiceAmountPercentAllowanceLower())) {
            try {
                invoiceAmountPercentAllowanceLower = new BigDecimal(sForm.
                    getInvoiceAmountPercentAllowanceLower());
            } catch (NumberFormatException e) {
                lUpdateErrors.add("invoiceAmountPercentAllowanceLower",
                        new ActionError("error.invalidAmount",
                            "InvoiceAmount Percent Allowance Lower"));
            }
        }
        if (Utility.isSet(sForm.getAllowedFreightSurchargeAmount())) {
            try {
              allowedFreightSurchargeAmount = new BigDecimal(sForm.
                    getAllowedFreightSurchargeAmount());
            } catch (NumberFormatException e) {
                lUpdateErrors.add("allowedFreightSurchargeAmount",
                        new ActionError("error.invalidAmount",
                            "Allowed Freight Surcharge Amount"));
            }
        }

        int holdInvoiceDays = 0;
        if (Utility.isSet(sForm.getHoldInvoiceDays())) {
            try {
                holdInvoiceDays = Integer.parseInt(sForm.getHoldInvoiceDays());
            } catch (Exception e) {
                lUpdateErrors.add("holdInvoiceDays",
                        new ActionError("error.invalidAmount",
                                "holdInvoiceDays"));
            }
        }

        if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }

        APIAccess factory = new APIAccess();
        Distributor distributorBean = factory.getDistributorAPI();

        int distributorid = 0;
        if (!sForm.getId().equals("")) {
            distributorid = Integer.parseInt(sForm.getId());
        }
        // Get the current information for this Distributor.
        DistributorData dd = distributorBean.getDistributor(distributorid);

        if (Utility.isSet(sForm.getExceptionOnOverchargedFreight())) {
            if (sForm.getExceptionOnOverchargedFreight().trim().equals("Y")) {
                dd.setExceptionOnOverchargedFreight(Boolean.TRUE);
            } else {
                dd.setExceptionOnOverchargedFreight(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getExceptionOnTaxDifference())) {
               dd.setExceptionOnTaxDifference(sForm.getExceptionOnTaxDifference());
        }

        if (Utility.isSet(sForm.getIgnoreOrderMinimumForFreight())) {
            if (sForm.getIgnoreOrderMinimumForFreight().trim().equals("Y")) {
                dd.setIgnoreOrderMinimumForFreight(Boolean.TRUE);
            } else {
                dd.setIgnoreOrderMinimumForFreight(Boolean.FALSE);
            }
        }

        dd.setMaxInvoiceFreightAllowed(sForm.getMaxInvoiceFreightAllowed());

        if (Utility.isSet(sForm.getAllowFreightOnBackorders())) {
            if (sForm.getAllowFreightOnBackorders().trim().equals("Y")) {
                dd.setAllowFreightOnBackorders(Boolean.TRUE);
            } else {
                dd.setAllowFreightOnBackorders(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getCancelBackorderedLines())) {
            if (sForm.getCancelBackorderedLines().trim().equals("Y")) {
                dd.setCancelBackorderedLines(Boolean.TRUE);
            } else {
                dd.setCancelBackorderedLines(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getAllowFreightOnFreightHandlerOrders())) {
            if (sForm.getAllowFreightOnFreightHandlerOrders().trim().equals("Y")) {
                dd.setAllowFreightOnFreightHandlerOrders(Boolean.TRUE);
            } else {
                dd.setAllowFreightOnFreightHandlerOrders(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getPrintCustContactOnPurchaseOrder())) {
            if (sForm.getPrintCustContactOnPurchaseOrder().trim().equals("Y")) {
                dd.setPrintCustContactOnPurchaseOrder(Boolean.TRUE);
            } else {
                dd.setPrintCustContactOnPurchaseOrder(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getManualPOAcknowldgementRequiered())) {
            if (sForm.getManualPOAcknowldgementRequiered().trim().equals("Y")) {
                dd.setManualPOAcknowldgementRequiered(Boolean.TRUE);
            } else {
                dd.setManualPOAcknowldgementRequiered(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getDoNotAllowInvoiceEdits())) {
            if (sForm.getDoNotAllowInvoiceEdits().trim().equals("Y")) {
                dd.setDoNotAllowInvoiceEdits(Boolean.TRUE);
            } else if(dd.getDoNotAllowInvoiceEdits() != null){
                dd.setDoNotAllowInvoiceEdits(Boolean.FALSE);
            }
        }

        if (sForm.getPurchaseOrderComments() != null) {
            dd.setPurchaseOrderComments(sForm.getPurchaseOrderComments());
        }

        if (sForm.getHoldInvoiceDays() != null) {
            dd.setHoldInvoiceDays(holdInvoiceDays);
        }

        if (invoiceAmountPercentAllowanceUpper != null) {
            dd.setInvoiceAmountPercentAllowanceUpper(
                    invoiceAmountPercentAllowanceUpper);
        }
        if (invoiceAmountPercentAllowanceLower != null) {
            dd.setInvoiceAmountPercentAllowanceLower(
                    invoiceAmountPercentAllowanceLower);
        }
        if (allowedFreightSurchargeAmount != null) {
            dd.setAllowedFreightSurchargeAmount(
                    allowedFreightSurchargeAmount);
        }

        dd.setInvoiceLoadingPriceModel(sForm.getInvoiceLoadingPriceModel());
        dd.setReceivingSystemInvoiceCd(sForm.getReceivingSystemInvoiceCd());

        if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().
                getUserTypeCd())) {
            dd.setStoreId(appUser.getUserStore().getStoreId());
            resetStoreDistributorCache(session, dd.getBusEntity().getBusEntityId(),
                    appUser.getUserStore().getStoreId());
        } else {
            dd.setStoreId(storeId);
            resetStoreDistributorCache(session, dd.getBusEntity().getBusEntityId(),
                    storeId);
        }
        
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        EmailData rejectedInvEmailData;
        rejectedInvEmailData = dd.getRejectedInvEmail();
        String rejectedInvEmailStr = sForm.getRejectedInvEmail();
        
        if (Utility.isSet(rejectedInvEmailStr)) {
          String[] rejectedInvEmails = rejectedInvEmailStr.trim().split(",");
          List<String> incorrectEmails = new ArrayList<String>();
          for (String rejectedInvEmail : rejectedInvEmails) {
              if (!Utility.isValidEmailAddress(rejectedInvEmail)) {
                  incorrectEmails.add(rejectedInvEmail);
              }
          }
          if (incorrectEmails.size() > 0) {
              lUpdateErrors.add("",
                      new ActionError("error.simpleGenericError",
                              "Incorrect email address(es):"
                                      + incorrectEmails));
          }
        }
        
        if (lUpdateErrors.size() > 0) {
          // Report the errors to allow for edits.
          return lUpdateErrors;
        }
        rejectedInvEmailData.setEmailAddress(rejectedInvEmailStr);
      	rejectedInvEmailData.setModBy(cuser);
        if (distributorid == 0) {
        	rejectedInvEmailData.setAddBy(cuser);
        }
        dd.setRejectedInvEmail(rejectedInvEmailData);
        dd = distributorBean.updateDistributor(dd);
        distributorid = dd.getBusEntity().getBusEntityId();
        session.setAttribute("STORE_DIST_DETAIL_FORM", sForm);

        return lUpdateErrors;
    }


}








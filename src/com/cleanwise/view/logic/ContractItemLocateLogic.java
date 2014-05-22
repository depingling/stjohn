
package com.cleanwise.view.logic;

import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AssetDetailView;
import com.cleanwise.service.api.value.AssetDetailViewVector;
import com.cleanwise.service.api.value.AssetSearchCriteria;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.ServiceSearchCriteria;
import com.cleanwise.service.api.value.ServiceView;
import com.cleanwise.service.api.value.ServiceViewVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.forms.ContractItemLocateForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SessionTool;

/**
 * <code>CatalogMgrLogic</code> implements the logic needed to
 * manipulate catalog records.
 *
 * @author yuriy
 */
public class ContractItemLocateLogic {
	  private static final Logger log = Logger.getLogger(ContractItemLocateLogic.class);

  /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void init(HttpServletRequest request,
			  ActionForm form)
    throws Exception
  {
    HttpSession session = request.getSession();

    ContractItemLocateForm pForm = (ContractItemLocateForm) form;
    int catalogId = 0;
    String catalogIdS = request.getParameter("catalogid");
    if (null != catalogIdS && ! "".equals(catalogIdS) ) {
        catalogId =  Integer.parseInt(catalogIdS);
    }

    int contractId = 0;
    String contractIdS = request.getParameter("contractid");
    if (null != contractIdS && ! "".equals(contractIdS) ) {
        contractId =  Integer.parseInt(contractIdS);
    }

    pForm.setCatalogId(catalogId);
    pForm.setContractId(contractId);
    pForm.setSelectorBox(new String[0]);
    pForm.setProducts(new ProductDataVector());
    pForm.setListIds(new IdVector());

    return;
  }


  /**
  * Searches for item. Takes conditions form the form
  *
  */
  public static ActionErrors searchForItem (HttpServletRequest request,
			    ActionForm form)
    throws Exception
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();

    ContractItemLocateForm pForm = (ContractItemLocateForm) form;

    int catalogId = 0;
    String catalogIdS = request.getParameter("catalogid");
    if (null != catalogIdS && ! "".equals(catalogIdS) ) {
        catalogId =  Integer.parseInt(catalogIdS);
    }
    int contractId = 0;
    String contractIdS = request.getParameter("contractid");
    if (null != contractIdS && ! "".equals(contractIdS) ) {
        contractId =  Integer.parseInt(contractIdS);
    }
    int siteId = 0;
    String siteIdS = request.getParameter("siteid");
    if (Utility.isSet(siteIdS)) {
        siteId = Integer.parseInt(siteIdS);
    }
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Distributor distributorEjb = factory.getDistributorAPI();
    Manufacturer manufacturerEjb = factory.getManufacturerAPI();
    Contract contractEjb = factory.getContractAPI();

    if (catalogId == 0 && contractId == 0 && siteId > 0) {
        Site siteEjb = factory.getSiteAPI();
        catalogId = siteEjb.getShoppigCatalogIdForSite(siteId);
    }


    //Clear the search results
    pForm.setListIds(new IdVector());
    pForm.setProducts(new ProductDataVector());

    //Create a set of filters
    Vector searchConditions = new Vector();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    SearchCriteria scStore = new
        SearchCriteria(SearchCriteria.STORE_ID,SearchCriteria.EXACT_MATCH, new Integer(appUser.getUserStore().getStoreId()));
    searchConditions.add(scStore);  

    //Category
    String category = pForm.getCategoryTempl();
    if(category!=null && category.trim().length()>0){
      SearchCriteria sc = new
        SearchCriteria(SearchCriteria.CATALOG_CATEGORY,SearchCriteria.CONTAINS_IGNORE_CASE,category);
        searchConditions.add(sc);
    }
    //Short Desc
    String shortDesc = pForm.getShortDescTempl();
    if(shortDesc!=null && shortDesc.trim().length()>0){
      SearchCriteria sc = new
        SearchCriteria(SearchCriteria.PRODUCT_SHORT_DESC,SearchCriteria.CONTAINS_IGNORE_CASE,shortDesc);
        searchConditions.add(sc);
    }

    //Long Desc
    String longDesc = pForm.getLongDescTempl();
    if(longDesc!=null && longDesc.trim().length()>0){
      SearchCriteria sc = new
        SearchCriteria(SearchCriteria.PRODUCT_LONG_DESC,SearchCriteria.CONTAINS_IGNORE_CASE,longDesc);
        searchConditions.add(sc);
    }

    //Size
    String sizeDesc = pForm.getSizeTempl();
    if(sizeDesc!=null && sizeDesc.trim().length()>0){
      SearchCriteria sc = new
        SearchCriteria(SearchCriteria.SKU_SIZE,SearchCriteria.CONTAINS_IGNORE_CASE,sizeDesc);
        searchConditions.add(sc);
    }

    //Manufacturer
    String manuIdS = pForm.getManuId();
    if(manuIdS!=null && manuIdS.trim().length()>0){
      int manuId = 0;
      try{
        manuId = Integer.parseInt(manuIdS);
        ManufacturerData manuD = manufacturerEjb.getManufacturer(manuId);
        pForm.setManuName(manuD.getBusEntity().getShortDesc());
      }catch(NumberFormatException exc) {
        ae.add("error",new ActionError("item.search.wrong_manufacturer"));
        return ae;
      }catch(DataNotFoundException de) {
	  // no such manufacturer - treat not as an error, but as a search with
	  // no results
	  return ae;
      }
      SearchCriteria sc = new
        SearchCriteria(SearchCriteria.MANUFACTURER_ID,SearchCriteria.EXACT_MATCH,new Integer(manuId));
        searchConditions.add(sc);
    }

    //distributor
    String distrIdS = pForm.getDistributorId();
    if(distrIdS!=null && distrIdS.trim().length()>0){
      int distrId = 0;
      try{
        distrId = Integer.parseInt(distrIdS);
        DistributorData distrD = distributorEjb.getDistributor(distrId);
        pForm.setDistributorName(distrD.getBusEntity().getShortDesc());
      }catch(NumberFormatException exc) {
        ae.add("error",new ActionError("item.search.wrong_distributor"));
        return ae;
      }catch(DataNotFoundException de) {
	  // no such distributor - treat not as an error, but as a search with
	  // no results
	  return ae;
      }
      SearchCriteria sc = new
        SearchCriteria(SearchCriteria.DISTRIBUTOR_ID,SearchCriteria.EXACT_MATCH,new Integer(distrId));
        searchConditions.add(sc);
    }
    //sku
    String skuNumber = pForm.getSkuTempl();
    if(skuNumber!=null && skuNumber.trim().length()>0){
      String skuType = pForm.getSkuType();
      if(skuType.equals("System")) {
          if (appUser.getUserStore().getStoreId() == 1) {
              SearchCriteria sc = new SearchCriteria(SearchCriteria.CUST_SKU_NUMBER,
                                                     SearchCriteria.EXACT_MATCH,
                                                     skuNumber);
              searchConditions.add(sc);
          } else {
              SearchCriteria sc = new SearchCriteria(SearchCriteria.CLW_SKU_NUMBER,
                                                     SearchCriteria.EXACT_MATCH,
                                                     skuNumber);
              searchConditions.add(sc);
          }
      }else if(skuType.equals("Manufacturer")){
        SearchCriteria sc = new SearchCriteria(SearchCriteria.MANUFACTURER_SKU_NUMBER,
                                               SearchCriteria.EXACT_MATCH,
                                               skuNumber);
        searchConditions.add(sc);
      }else if(skuType.equals("Distributor")){
        SearchCriteria sc = new SearchCriteria(SearchCriteria.DISTRIBUTOR_SKU_NUMBER,
                                               SearchCriteria.EXACT_MATCH,
                                               skuNumber);
        searchConditions.add(sc);
      }
    }

    //Find
   pForm.setSelectorBox(new String[0]);
   pForm.setResultSource("");
   IdVector itemIds = null;

   String catalogType = RefCodeNames.CATALOG_TYPE_CD.SYSTEM;
   if (appUser.getUserStore().getStoreId() == 1) {
       catalogId =  4;
   }

    boolean systemCatalog;
    if(catalogType.equals(RefCodeNames.CATALOG_TYPE_CD.SYSTEM)) {
        if (appUser.getUserStore().getStoreId() == 1) {
            itemIds = catalogInfEjb.searchProducts(searchConditions, 1);
            systemCatalog = false;
        } else {
            itemIds = catalogInfEjb.searchProducts(searchConditions);
            systemCatalog = true;
        }
    } else {
      //itemIds = catalogInfEjb.searchCatalogProducts(catalogId,searchConditions);

      itemIds = catalogInfEjb.searchCatalogProducts(searchConditions, appUser.getUserStore().getStoreId(),catalogId);
      systemCatalog = false;
    }

    log.info("=======itemIds.size(): " + itemIds.size());
    // filter the itemIdV by contract
    if (0 != contractId) {
        IdVector contractItemIdV = contractEjb.getItemIdCollectionByContract(contractId);
        if (null != contractItemIdV && 0 < contractItemIdV.size()
            && null != itemIds && 0 < itemIds.size()) {
            IdVector newItemIds = new IdVector();
            for (int i = 0; i < itemIds.size(); i++) {
                int catalogItemId = ((Integer) itemIds.get(i)).intValue();
                for (int j = 0 ; j < contractItemIdV.size(); j++) {
                    int contractItemId = ((Integer) contractItemIdV.get(j)).intValue();
                    if (catalogItemId == contractItemId) {
                        newItemIds.add(new Integer(catalogItemId));
                        contractItemIdV.remove(j);
                        break;
                    }
                }
            }
            itemIds = newItemIds;
        }
        else {
            itemIds = new IdVector();
        }
    }
    log.info("=======itemIds.size(): " + itemIds.size());
    pForm.setListIds(itemIds);
    //Create page list
    ProductDataVector productDV = new ProductDataVector();
    for(int ii=0; ii<itemIds.size(); ii++) {
      int itemId = ((Integer) itemIds.get(ii)).intValue();
      ProductData productD;
      if (systemCatalog) {
	  productD = catalogInfEjb.getProduct(itemId);
      } else {
	  productD = catalogInfEjb.getCatalogClwProduct(catalogId,itemId,0,0,
              SessionTool.getCategoryToCostCenterView(session, 0, catalogId ));
      }
      log.info("=======CustomerSkuNum: " + productD.getCustomerSkuNum());
      log.info("=======ManufacturerSku: " + productD.getManufacturerSku());

      productDV.add(productD);
    }
    pForm.setProducts(productDV);
    return ae;
  }

    /**
     * Returns an Service view vector based off the specified search criteria
     *
     * @return Service view vector
     * @throws Exception exception
     */
    static public ActionErrors searchForService(HttpServletRequest request, ActionForm form) throws Exception {

        APIAccess factory = new APIAccess();
        ActionErrors ae = new ActionErrors();
        CatalogInformation catInfoEJB = factory.getCatalogInformationAPI();
        Contract contractEjb=factory.getContractAPI();
        User userEjb = factory.getUserAPI();
        Asset assetEjb = factory.getAssetAPI();
        Site siteEjb = factory.getSiteAPI();
        ContractItemLocateForm pForm = (ContractItemLocateForm) form;
        try {
            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            String searchField = pForm.getShortDescTempl();
            String fieldSearchType = pForm.getSearchType();

            if (fieldSearchType.equals("id") && searchField.length() > 0) {

                Integer.parseInt(searchField);
            }

            int contractId = 0;
            int catalogId = 0;
            String contractIdS = request.getParameter("contractid");
            try {
                contractId = Integer.parseInt(contractIdS);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                ae.add("error", new ActionError("error.simpleGenericError", "Invalid contract id"));
                return ae;
            }

            ContractData contractData = contractEjb.getContract(contractId);
            if (contractData == null) {
                ae.add("error",
                        new ActionError("error.simpleGenericError", "Contract data is null for contractId : " + contractId));
                return ae;
            }

            catalogId = contractData.getCatalogId();

            int userId = 0;
            UserData custUser = null;
            String userIdS = request.getParameter("custuserid");
            try {
                userId = Integer.parseInt(userIdS);
                custUser = userEjb.getUser(userId);
            } catch (Exception e) {
                e.printStackTrace();
                ae.add("error", new ActionError("error.simpleGenericError", "Invalid user"));
                return ae;
            }

            int siteId = 0;
            String siteIdS = request.getParameter("siteid");
            SiteData siteData=null;
            try {
                siteId = Integer.parseInt(siteIdS);
                siteData =siteEjb.getSite(siteId, 0, false, SessionTool.getCategoryToCostCenterView(session, siteId));
            } catch (Exception e) {
                e.printStackTrace();
                ae.add("error", new ActionError("error.simpleGenericError", "Invalid site id"));
                return ae;
            }

            int assetId = 0;
            String assetIdS = request.getParameter("assetid");
            try {
                assetId = Integer.parseInt(assetIdS);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                ae.add("error", new ActionError("error.simpleGenericError", "Invalid asset id"));
                return ae;
            }

            AssetSearchCriteria criteria = new AssetSearchCriteria();

            //asset crit
            criteria.setAssetId(assetId);
            IdVector siteIds = new IdVector();
            siteIds.add(new Integer(siteId));

            criteria.setSiteIds(siteIds);
            criteria.setUserId(custUser.getUserId());

            criteria.setUserTypeCd(custUser.getUserTypeCd());
            criteria.setShowInactive(false);

            AssetDetailViewVector assetDetailVV = assetEjb.getAssetDetailViewVector(criteria);
            if (assetDetailVV.size() > 1) {
                throw new Exception("Multiple asset detail data");
            }
            IdVector serviceIdsInSiteCatalog = new IdVector();
            ServiceSearchCriteria serviceCrit=new ServiceSearchCriteria();
            //service crit
            IdVector catalogIds=new IdVector();
            catalogIds.add(new Integer(catalogId));
            serviceCrit.setCatalogIds(catalogIds);
            serviceCrit.setCategory(pForm.getCategoryTempl());
            serviceCrit.setServiceName(searchField);
            serviceCrit.setSearchNameTypeCd(fieldSearchType);
            serviceCrit.setStoreId(appUser.getUserStore().getStoreId());
            ServiceViewVector services = null;
            try {
                services=catInfoEJB.getServicesViewVector(serviceCrit);
                serviceIdsInSiteCatalog = catInfoEJB.getServicesIdsBySiteCatalog(siteId, catalogId, true);
                services=getServicesFromCollectionByIds(services,serviceIdsInSiteCatalog);

            } catch (DataNotFoundException e) {

            }
          AssetDetailView assetDetailView = ((AssetDetailView) assetDetailVV.get(0));

         ItemDataVector servicesAssoc = UserAssetMgrLogic.getServicesByIds(assetDetailView.getAssetServiceAssoc(), serviceIdsInSiteCatalog);
         IdVector serviceAssocIds=StoreAssetMgrLogic.getOnlyItemIds(servicesAssoc);
         services=getServicesFromCollectionByIds(services,serviceAssocIds);

         pForm.setServices(services);

        }

        catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionMessage("error.simpleGenericError", e.getMessage()));
        }

        return ae;
    }

    private static ServiceViewVector getServicesFromCollectionByIds(ServiceViewVector services, IdVector serviceIdsInSiteCatalog) {
      ServiceViewVector result=new ServiceViewVector();
      if(services==null||serviceIdsInSiteCatalog==null) return result;

        Iterator it= services.iterator();
        while (it.hasNext())
        {
            ServiceView service= (ServiceView) it.next();
            Iterator it2= serviceIdsInSiteCatalog.iterator();
            while(it2.hasNext())
            {
                int id = ((Integer) it2.next()).intValue();
                if(service.getServiceId()==id)
                {
                    result.add(service);
                    break;
                }
            }
        }
        return result;
    }

    /**
  * Refreshes item list
  *
  */
  public static void refreshListOfItems (HttpServletRequest request,
			    ContractItemLocateForm pForm)
    throws Exception
  {
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    pForm.setSelectorBox(new String[0]);
    String productIdS = request.getParameter("itemId");
    if(productIdS!=null) {
      try{
      int productId = Integer.parseInt(productIdS);
      ProductDataVector productDV = pForm.getProducts();
        for(int ii=0; ii<productDV.size(); ii++) {
          ProductData productD = (ProductData) productDV.get(ii);
          if(productD.getProductId()==productId) {
            ProductData refreshedProductD =
                catalogInfEjb.getProduct(productId);
            productDV.remove(ii);
            productDV.add(ii, refreshedProductD);
            break;
          }
        }
      }catch(NumberFormatException exc) {
        //Just do nothing. Theoreticaly should not happen
      }catch(DataNotFoundException exc) {
        //Just do nothing. Theoreticaly should not happen
      }
    }
  }

  /**
  * Prepare to show items on tree
  *
  */
  public static ActionErrors prepareToShow (HttpServletRequest request,
			    ContractItemLocateForm pForm)
    throws Exception
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    String[] selected = pForm.getSelectorBox();
    if(pForm.getProducts()==null) {
      ae.add("error", new ActionError("item.search.nothing_selected"));
      return ae;
    }
    if(selected ==null || selected.length==0) {
      ae.add("error", new ActionError("item.search.nothing_selected"));
      return ae;
    }

    ProductDataVector productDV = pForm.getProducts();
    int size=productDV.size();
    IdVector selectedIdV = new IdVector();
    for(int ii=0; ii<selected.length; ii++){
      int ind = Integer.parseInt(selected[ii]);
      if(ind>=size) {
        continue;
      }
      ProductData pD = (ProductData) productDV.get(ind);
      selectedIdV.add(new Integer(pD.getProductId()));
    }
    if(selectedIdV.size()==0) {
        ae.add("error", new ActionError("item.search.nothing_selected"));
    }
    pForm.setSelectedProductIds(selectedIdV);
    return ae;
  }


    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request,
			    ActionForm aForm)
	throws Exception {

        ContractItemLocateForm form = (ContractItemLocateForm) aForm;

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
	ProductDataVector products = form.getProducts();
	if (products == null) {
	    return;
	}
	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(products, sortField);
    }

}




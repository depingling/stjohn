
package com.cleanwise.view.logic;

import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.ItemMgrLocateForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import java.rmi.RemoteException;


import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * <code>CatalogMgrLogic</code> implements the logic needed to
 * manipulate catalog records.
 *
 * @author yuriy
 */
public class ItemMgrLocateLogic {

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

    ItemMgrLocateForm pForm = (ItemMgrLocateForm) form;
    int catalogId = 0;
    String catalogIdS = request.getParameter("catalogid");
    if (null != catalogIdS && ! "".equals(catalogIdS) ) {
        catalogId =  Integer.parseInt(catalogIdS);
    }

    pForm.setCatalogId(catalogId);
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

    ItemMgrLocateForm pForm = (ItemMgrLocateForm) form;

    int catalogId = 0;
    String catalogIdS = request.getParameter("catalogid");
    if (null != catalogIdS && ! "".equals(catalogIdS) ) {
        catalogId =  Integer.parseInt(catalogIdS);
    }

    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Distributor distributorEjb = factory.getDistributorAPI();
    Manufacturer manufacturerEjb = factory.getManufacturerAPI();

    //Clear the search results
    pForm.setListIds(new IdVector());
    pForm.setProducts(new ProductDataVector());

    //Create a set of filters
    Vector searchConditions = new Vector();
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
        SearchCriteria sc = new SearchCriteria(SearchCriteria.CLW_SKU_NUMBER,
                                               SearchCriteria.EXACT_MATCH,
                                               skuNumber);
        searchConditions.add(sc);
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
    boolean systemCatalog=false;
    /*if(catalogType.equals(RefCodeNames.CATALOG_TYPE_CD.SYSTEM)) {
      itemIds = catalogInfEjb.searchProducts(searchConditions);
      systemCatalog = true;
    } else {
      itemIds = catalogInfEjb.searchCatalogProducts(catalogId,searchConditions);
      systemCatalog = false;
   }*/
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
    	ae.add("error",
    			new ActionError("error.simpleGenericError", "No store info"));
    	return ae;
    }

    int storeId = storeD.getBusEntity().getBusEntityId();
    itemIds = catalogInfEjb.searchProducts(searchConditions, storeId);

    pForm.setListIds(itemIds);
    //Create page list
    ProductDataVector productDV = new ProductDataVector();
    for(int ii=0; ii<itemIds.size(); ii++) {
      int itemId = ((Integer) itemIds.get(ii)).intValue();
      ProductData productD;
      if (systemCatalog) {
	  productD = catalogInfEjb.getProduct(itemId);
      } else {
	  productD = catalogInfEjb.getCatalogClwProduct(catalogId,itemId,0,0,SessionTool.getCategoryToCostCenterView(session, 0, catalogId ));
      }
      productDV.add(productD);
    }
    pForm.setProducts(productDV);
    return ae;
  }
  /**
  * Refreshes item list
  *
  */
  public static void refreshListOfItems (HttpServletRequest request,
			    ItemMgrLocateForm pForm)
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
			    ItemMgrLocateForm pForm)
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

        ItemMgrLocateForm form = (ItemMgrLocateForm) aForm;

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




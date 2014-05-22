
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
import com.cleanwise.view.forms.CatalogMgrSearchForm;
import com.cleanwise.view.forms.CatalogMgrStructureForm;
import com.cleanwise.view.forms.CatalogMgrDetailForm;
import com.cleanwise.view.forms.CatalogMgrItemAddForm;
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
public class CatalogMgrItemAddLogic {

  /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void init(HttpServletRequest request,
			  CatalogMgrItemAddForm pForm)
    throws Exception
  {
    HttpSession session = request.getSession();
    CatalogMgrDetailForm detailForm = (CatalogMgrDetailForm) session.getAttribute("CATALOG_DETAIL_FORM");
    int catalogId=detailForm.getDetail().getCatalogId();
    int superCatalogId = detailForm.getMasterCatalogId();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }

    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    CatalogData superCatalog = catalogInfEjb.getCatalog(superCatalogId);
    pForm.setSuperCatalog(superCatalog);

    pForm.setCatalogId(catalogId);
    pForm.setProducts(new ProductDataVector());
    pForm.setListIds(new IdVector());
    pForm.setFilterIds(new IdVector());
    pForm.setSelectorBox(new String[0]);


    return;
  }


  /**
  * Searches for item. Takes conditions form the form
  *
  */
  public static ActionErrors searchForItem (HttpServletRequest request,
			    CatalogMgrItemAddForm pForm)
    throws Exception
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    CatalogMgrDetailForm catalogDetail = (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
    int catalogId=catalogDetail.getDetail().getCatalogId();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

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
      }catch(NumberFormatException exc) {
        ae.add("error",new ActionError("item.search.wrong_manufacturer"));
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
      }catch(NumberFormatException exc) {
        ae.add("error",new ActionError("item.search.wrong_distributor"));
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

    //Find filter
    IdVector filterIds = null;
    filterIds = catalogInfEjb.searchCatalogProducts(catalogId,searchConditions);
    pForm.setFilterIds(filterIds);

    //Find
    pForm.setSelectorBox(new String[0]);
    pForm.setResultSource("");
    IdVector itemIds = null;
    int superCatalogId = catalogDetail.getMasterCatalogId();
    if(superCatalogId>0) {
      itemIds = catalogInfEjb.searchCatalogProducts(superCatalogId,searchConditions);
    } else {
      ae.add("error", new ActionError("item.search.no_superior_catalog"));
      return ae;
    }

    //Filter out existing items
    for(int ii=0; ii<itemIds.size(); ) {
      Integer idI=(Integer)itemIds.get(ii);
      int jj=0;
      for(;jj<filterIds.size();jj++) {
        if(idI.equals((Integer)filterIds.get(jj))==true){
          itemIds.remove(ii);
          break;
        }
      }
      if(jj==filterIds.size()){
        ii++;
      }
    }

    pForm.setListIds(itemIds);


    //Create page list
    ProductDataVector productDV = new ProductDataVector();
    for(int ii=0; ii < itemIds.size(); ii++) {
      int itemId = ((Integer) itemIds.get(ii)).intValue();
      ProductData productD = catalogInfEjb.getProduct(itemId);
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
			    CatalogMgrItemAddForm pForm)
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
  * Adds items to catalog from superior catalog
  *
  */
  public static ActionErrors addToCatalog (HttpServletRequest request,
			    CatalogMgrItemAddForm pForm)
    throws Exception
  {
    HttpSession session = request.getSession();
    String user =(String)session.getAttribute(Constants.USER_NAME);
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }
//    Catalog catalogEjb = factory.getCatalogAPI();
    String[] selected = pForm.getSelectorBox();
    LinkedList retObject = pForm.getRetObject();
    if(retObject == null) {
      retObject = new LinkedList();
    }

    if(pForm.getProducts()!=null && selected != null && selected.length >0  ) {
      ProductDataVector productDV = pForm.getProducts();
      int size=productDV.size();
      for(int ii=0; ii<selected.length; ii++){
        int ind = Integer.parseInt(selected[ii]);
        if(ind>=size) {
          continue;
        }
        ProductData pD = (ProductData) productDV.get(ind);
        retObject.add(pD);
      }
    }
    session.setAttribute("lookupValue",retObject);
    pForm.setSelectorBox(new String[0]);
    return ae;
  }

    /**
   * <code>Go To Page</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void goPage(HttpServletRequest request,
			    CatalogMgrItemAddForm pForm)
    throws Exception {

    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    String pageNumS = request.getParameter("page");
    int pageNum = Integer.parseInt(pageNumS);
    int pageSize = pForm.getPageSize();
    pForm.setOffset(pageNum*pageSize);

    IdVector itemIds = pForm.getListIds(); 
    ProductDataVector productDV = new ProductDataVector();
    int offset = pForm.getOffset();
    for(int ii=0; ii<pForm.getPageSize()&& ii+offset<itemIds.size(); ii++) {
      int itemId = ((Integer) itemIds.get(ii+offset)).intValue();
      ProductData productD = catalogInfEjb.getProduct(itemId);
      productDV.add(productD);
    }
    pForm.setProducts(productDV);

  }

}




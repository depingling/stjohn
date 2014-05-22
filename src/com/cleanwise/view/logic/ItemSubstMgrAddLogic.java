
package com.cleanwise.view.logic;

import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Collection;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.ItemSubstMgrAddForm;
import com.cleanwise.view.forms.ContractMgrDetailForm;
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
 * <code>ItemSubstMgrAddLogic</code> implements the logic needed to
 * Searches catalog items to select item substitution
 *
 * @author yuriy
 */
public class ItemSubstMgrAddLogic {

  /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void init(HttpServletRequest request,
			  ItemSubstMgrAddForm pForm)
    throws Exception
  {
    HttpSession session = request.getSession();
    String catalogIdS = request.getParameter("catalogId");
    String itemIdS = request.getParameter("itemId");
    int catalogId = 0;
    try {
      catalogId = Integer.parseInt(catalogIdS);
    }catch(NumberFormatException exc) {
      throw new Exception("Wrong catalogId format: "+catalogIdS);
    }
    int itemId = 0;
    try {
      itemId = Integer.parseInt(itemIdS);
    }catch(NumberFormatException exc) {
      throw new Exception("Wrong itemId format: "+itemIdS);
    }

    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }

    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    CatalogData catalog = catalogInfEjb.getCatalog(catalogId);
    if(pForm.getCatalogId()!=catalogId || pForm.getProducts()==null) {
      pForm.setProducts(new ProductDataVector());
    }
    ProductData sourceProd = catalogInfEjb.getCatalogClwProduct(catalogId, itemId,0,0, SessionTool.getCategoryToCostCenterView(session, 0, catalogId ));
    pForm.setSourceProduct(sourceProd);
    pForm.setCatalogId(catalogId);
    pForm.setListIds(new IdVector());
    pForm.setSelectorBox(new String[0]);
    pForm.setSelectedItemIdS(null);

    return;
  }


  /**
  * Searches for item. Takes conditions form the form
  *
  */
  public static ActionErrors searchForItem (HttpServletRequest request,
			    ItemSubstMgrAddForm pForm)
    throws Exception
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    int catalogId=pForm.getCatalogId();
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

    //Find
    pForm.setSelectorBox(new String[0]);
    pForm.setResultSource("");
    IdVector itemIds = null;
    itemIds = catalogInfEjb.searchCatalogProducts(catalogId,searchConditions);
    pForm.setListIds(itemIds);


    //Create page list
    ProductDataVector productDV = new ProductDataVector();
    int offset = pForm.getOffset();
    for(int ii=0; ii<pForm.getPageSize()&& ii+offset<itemIds.size(); ii++) {
      int itemId = ((Integer) itemIds.get(ii+offset)).intValue();
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
			    ItemSubstMgrAddForm pForm)
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
  public static ActionErrors returnSelected (HttpServletRequest request,
			    ItemSubstMgrAddForm pForm)
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
    int itemId = Integer.parseInt(pForm.getSelectedItemIdS());
    pForm.setSelectedItemIdS(null);
    String conversionFactorS = pForm.getConversionFactorS();
    BigDecimal conversionFactor = null;
    try {
     conversionFactor = new BigDecimal(conversionFactorS);
     conversionFactor.setScale(2, BigDecimal.ROUND_HALF_UP);
    }catch(Exception exc) {
      String mess = "Wrong Uom Conversion Factor format: "+conversionFactorS;
      ae.add("error",new ActionError("error.simpleGenericError",mess));
      return ae;
    }
    AccountItemSubstViewVector retObject = null;
    if(retObject == null) {
      retObject = new AccountItemSubstViewVector();
    }
    if(pForm.getProducts()!=null) {
      ProductDataVector productDV = pForm.getProducts();
      int size=productDV.size();
      for(int ii=0; ii<size; ii++){
        ProductData pD = (ProductData) productDV.get(ii);
        if(pD.getProductId()==itemId);
        AccountItemSubstView aisVw = AccountItemSubstView.createValue();
        aisVw.setItemId(pForm.getSourceProduct().getProductId());
        aisVw.setSubstItemId(itemId);
        aisVw.setSubstItemSku(pD.getSkuNum());
        aisVw.setSubstItemDesc(pD.getCatalogProductShortDesc());
        aisVw.setSubstItemUom(pD.getUom());
        aisVw.setSubstItemPack(pD.getPack());
        aisVw.setSubstItemSize(pD.getSize());
        aisVw.setSubstItemMfgId(pD.getManuMapping().getBusEntityId());
        aisVw.setSubstItemMfgName(pD.getManufacturerName());
        aisVw.setSubstItemMfgSku(pD.getManufacturerSku());
        aisVw.setUomConversionFactor(conversionFactor);
        retObject.add(aisVw);
      }
      session.setAttribute("lookupValue",retObject);
    }
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
			    ItemSubstMgrAddForm pForm)
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




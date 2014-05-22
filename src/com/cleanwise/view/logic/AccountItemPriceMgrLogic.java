
package com.cleanwise.view.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.ArrayList;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.AccountItemPriceMgrForm;
import com.cleanwise.view.forms.AccountMgrDetailForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
/**
 * <code>ItemSubstMgrLogic</code> implements the logic needed to
 * manipulate contract item substitutions.
 *
 * @author Yuriy Kupershmidt
 */
public class AccountItemPriceMgrLogic {
  /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors init(HttpServletRequest request,
			  AccountItemPriceMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    AccountMgrDetailForm detailForm = (AccountMgrDetailForm) session.getAttribute("ACCOUNT_DETAIL_FORM");
    String accountIdS=detailForm.getId();
    int accountId = 0;
    try{
      accountId = Integer.parseInt(accountIdS);
    }catch(NumberFormatException exc) {
	  ae.add("error",new ActionError("error.systemError","Wrong account number format: "+accountIdS));
	  return ae;
    }
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
	    ae.add("error",new ActionError("error.systemError","No Ejb access"));
	    return ae;
	}
    CatalogInformation catalogInfEjb = null;
    try {
      catalogInfEjb = factory.getCatalogInformationAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Catalog Information Ejb access"));
	  return ae;
	}
    int catalogId = 0;
    int ind = -1;
    try {
      CatalogDataVector cDV = catalogInfEjb.getCatalogsCollectionByBusEntity(accountId);
      if(cDV.size()==0) {
        ae.add("error",new ActionError("error.simpleGenericError","No account catalog found"));
        return ae;
      }
      if(cDV.size()>0) {
        for(int ii=0; ii<cDV.size(); ii++) {
          CatalogData cD = (CatalogData) cDV.get(ii);
          if(!RefCodeNames.CATALOG_STATUS_CD.INACTIVE.equals(cD.getCatalogStatusCd())&&
             RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(cD.getCatalogTypeCd())){
            if(ind>=0) {
              ae.add("error",new ActionError("error.systemError","Account has more than one catalog"));
              return ae;
            }
            ind = ii;
          }
        }
      }
      if(ind==-1) {
        ae.add("error",new ActionError("error.simpleGenericError","No active account catalog found"));
        return ae;
      }
      CatalogData cD = (CatalogData) cDV.get(ind);
      catalogId = cD.getCatalogId();
    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Error during account catalog request. Message: "+exc.getMessage()));
      return ae;
    }

    pForm.setCatalogId(catalogId);
    //get contracts
    Contract contractEjb = null;
    try {
      contractEjb = factory.getContractAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Contract Ejb access"));
	  return ae;
	}
    ContractDataVector contractDV = null;
    try {
      contractDV = contractEjb.getContractsByAccount(accountId);
    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Error during account contracts request. Message: "+exc.getMessage()));
      return ae;
    }
    if(contractDV.size()==0) {
      ae.add("error",new ActionError("error.simpleGenericError","No account contracts found"));
      return ae;
    }
    pForm.setContracts(contractDV);
    pForm.setSelectorBox(new String[0]);
    pForm.setPriceItems(null);
    initInputArrays(request,pForm);
    return ae;
  }
  /**
  * Searches for item. Takes conditions from the form
  *
  */
  public static ActionErrors searchForItem (HttpServletRequest request,
			    AccountItemPriceMgrForm pForm)
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    int catalogId=pForm.getCatalogId();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
	    ae.add("error",new ActionError("error.systemError","No Ejb access"));
	    return ae;
	}
    CatalogInformation catalogInfEjb = null;
    try {
      catalogInfEjb = factory.getCatalogInformationAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Catalog Information Ejb access"));
	  return ae;
	}
    Distributor distributorEjb = null;
    try {
      distributorEjb = factory.getDistributorAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Distributor Ejb access"));
	  return ae;
	}
    Manufacturer manufacturerEjb = null;
    try {
      manufacturerEjb = factory.getManufacturerAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Manufacturer Ejb access"));
	  return ae;
	}
    Contract contractEjb = null;
    try {
      contractEjb = factory.getContractAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Contract Ejb access"));
	  return ae;
	}

    //Clear the search results
    pForm.setPriceItems(null);

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
      } catch (java.rmi.RemoteException exc) {
        ae.add("error",new ActionError("error.systemError","Error during manufacturer data request. Message: "+exc.getMessage()));
	    return ae;
      } catch(NumberFormatException exc) {
        ae.add("error",new ActionError("error.simpleGenericError","Wrong manufacturer id format: "+manuIdS));
        return ae;
      }catch(DataNotFoundException de) {
        ae.add("error",new ActionError("error.simpleGenericError","No manufacturer with requested id: "+manuId));
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
      } catch (java.rmi.RemoteException exc) {
        ae.add("error",new ActionError("error.systemError","Error during distributor data request. Message: "+exc.getMessage()));
	    return ae;
      } catch(NumberFormatException exc) {
        ae.add("error",new ActionError("error.simpleGenericError","Wrong distributor id format: "+distrIdS));
        return ae;
      }catch(DataNotFoundException de) {
        ae.add("error",new ActionError("error.simpleGenericError","No distributor with requested id: "+distrId));
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
      if(skuType.equals("SystemCustomer")) {
        SearchCriteria sc = new SearchCriteria(SearchCriteria.CLW_CUST_SKU_NUMBER,
                                               SearchCriteria.EXACT_MATCH,
                                               skuNumber);
        searchConditions.add(sc);
      }else if(skuType.equals("System")){
        SearchCriteria sc = new SearchCriteria(SearchCriteria.CLW_SKU_NUMBER,
                                               SearchCriteria.EXACT_MATCH,
                                               skuNumber);
        searchConditions.add(sc);
      }else if(skuType.equals("Customer")){
        SearchCriteria sc = new SearchCriteria(SearchCriteria.CUST_SKU_NUMBER,
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
      }else if(skuType.equals("Id")){
	SearchCriteria sc = new SearchCriteria(SearchCriteria.ITEM_ID,
				               SearchCriteria.EXACT_MATCH,
				               skuNumber);
	searchConditions.add(sc);
      }
    }

    //Find
    pForm.setSelectorBox(new String[0]);
    IdVector itemIds = null;
    try {
      itemIds = catalogInfEjb.searchCatalogProducts(catalogId,searchConditions);
    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Error during item search request. Message: "+exc.getMessage()));
      return ae;
    }
    ContractItemPriceViewVector priceItems = null;
    try {
      priceItems = contractEjb.getPriceItems(itemIds,pForm.getContracts());
      // Filter our the items for the distributor specified.
      if(distrIdS!=null && distrIdS.trim().length()>0){

	  int distid = Integer.parseInt(distrIdS);
	  ContractItemPriceViewVector tItems = 
	      new ContractItemPriceViewVector();
	  for ( int i = 0; i < priceItems.size(); i++ ) {
	      ContractItemPriceView ci =
		  (ContractItemPriceView)priceItems.get(i);
	      if ( ci.getDistId() == distid ) {
		  tItems.add(ci);
	      }
	  }
	  priceItems = tItems;
      }

    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Error during substitutions request. Message: "+exc.getMessage()));
      return ae;
    }
    pForm.setPriceItems(priceItems);
    initInputArrays(request,pForm);
    return ae;
  }
  //---------------------------------------------------------------------------
  public static ActionErrors sortItems (HttpServletRequest request,
			    AccountItemPriceMgrForm pForm)
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
	String sortField = pForm.getSortField();
    ContractItemPriceViewVector priceItems = pForm.getPriceItems();
    priceItems.sort(sortField);
    initInputArrays(request,pForm);
    return ae;
  }
  //---------------------------------------------------------------------------
  public static ActionErrors selectAll (HttpServletRequest request,
			    AccountItemPriceMgrForm pForm)
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    ContractItemPriceViewVector priceItems = pForm.getPriceItems();
    String[] selectorBox = new String[priceItems.size()];
    for(int ii=0; ii<priceItems.size(); ii++) {
      ContractItemPriceView piVw = (ContractItemPriceView) priceItems.get(ii);
      String select = ""+piVw.getItemId()+'#'+piVw.getContractId();
      selectorBox[ii]=select;
    }
    pForm.setSelectorBox(selectorBox);
    return ae;
  }
  //---------------------------------------------------------------------------
  public static ActionErrors clearSelections (HttpServletRequest request,
			    AccountItemPriceMgrForm pForm)
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    pForm.setSelectorBox(new String[0]);
    return ae;
  }
  //----------------------------------------------------------------------------
  private static void initInputArrays (HttpServletRequest request,
			    AccountItemPriceMgrForm pForm)
  {
    ContractItemPriceViewVector priceItems = pForm.getPriceItems();
    if(priceItems==null || priceItems.size()==0) {
      pForm.setInputIds(new String[0]);
      pForm.setDistCosts(new String[0]);
      pForm.setPrices(new String[0]);
      pForm.setCustSku(new String[0]);
      pForm.setCustDesc(new String[0]);
    } else {
      int size = priceItems.size();
      String[] inputIds = new String[size];
      String[] distCosts = new String[size];
      String[] prices =  new String[size];
      String[] custSkus =  new String[size];
      String[] custDescs =  new String[size];
      for(int ii=0; ii<size; ii++) {
        inputIds[ii] = "";
        ContractItemPriceView piVw = (ContractItemPriceView) priceItems.get(ii);
	if ( null == piVw.getDistCost() ) {
	    distCosts[ii] = "0";
	}
	else {
	    distCosts[ii] = piVw.getDistCost().toString();
	}

	if ( null == piVw.getPrice() ) {
	    prices[ii] = "0";
	}
	else {
	    prices[ii] = piVw.getPrice().toString();
	}

	if ( null == piVw.getItemCustDesc() ) {
	    custDescs[ii] = " ";
	}
	else {
	    custDescs[ii] = piVw.getItemCustDesc().toString();
	}

	if ( null == piVw.getItemCustSku() ) {
	    custSkus[ii] = " ";
	}
	else {
	    custSkus[ii] = piVw.getItemCustSku().toString();
	}
      }
      pForm.setInputIds(inputIds);
      pForm.setDistCosts(distCosts);
      pForm.setPrices(prices);
      pForm.setCustSku(custSkus);
      pForm.setCustDesc(custDescs);
   }
  }
  //----------------------------------------------------------------------------
  public static ActionErrors setPriceForSelected
      (HttpServletRequest request,
       AccountItemPriceMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    String priceMultyS = pForm.getPriceMulty();
    if(priceMultyS==null || priceMultyS.trim().length()==0) {
      ae.add("error",new ActionError("error.simpleGenericError","No item price found"));
      return ae;
    }
    BigDecimal priceMulty = null;
    try {
      priceMulty = new BigDecimal(priceMultyS);
    }catch(Exception exc) {}
    if(priceMulty==null) {
      ae.add("error",new ActionError("error.simpleGenericError","Wrong price format: "+priceMultyS));
      return ae;
    }
    String[] selected = pForm.getSelectorBox();
    String[] prices = pForm.getPrices();
    String[] inputIds = pForm.getInputIds();
    ContractItemPriceViewVector priceItems = pForm.getPriceItems();
    if(inputIds.length<priceItems.size()) {
      ae.add("error",new ActionError("error.simpleGenericError","Submited information does not match stored items. Probably old page was used"));
      return ae;
    }
    for(int ii=0; ii<priceItems.size(); ii++) {
      ContractItemPriceView cipVw = (ContractItemPriceView) priceItems.get(ii);
      String inputId = cipVw.getItemId()+"#"+cipVw.getContractId();
      if(!inputId.equals(inputIds[ii])) {
        ae.add("error",new ActionError("error.simpleGenericError","Submited information does not match stored items. Probably old page was used"));
        return ae;
      }
      for(int jj=0; jj<selected.length; jj++) {
        if(inputId.equals(selected[jj])) {
          prices[ii] = priceMulty.toString();
          break;
        }
      }
    }
    pForm.setPrices(prices);
    return ae;
  }
  //----------------------------------------------------------------------------
  public static ActionErrors setPriceForAll(HttpServletRequest request,
			    AccountItemPriceMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    String priceMultyS = pForm.getPriceMulty();
    if(priceMultyS==null || priceMultyS.trim().length()==0) {
      ae.add("error",new ActionError("error.simpleGenericError","No item price found"));
      return ae;
    }
    BigDecimal priceMulty = null;
    try {
      priceMulty = new BigDecimal(priceMultyS);
    }catch(Exception exc) {}
    if(priceMulty==null) {
      ae.add("error",new ActionError("error.simpleGenericError","Wrong price format: "+priceMultyS));
      return ae;
    }
    String[] prices = pForm.getPrices();
    String[] inputIds = pForm.getInputIds();
    ContractItemPriceViewVector priceItems = pForm.getPriceItems();
    if(inputIds.length<priceItems.size()) {
      ae.add("error",new ActionError("error.simpleGenericError","Submited information does not match stored items. Probably old page was used"));
      return ae;
    }
    for(int ii=0; ii<priceItems.size(); ii++) {
      ContractItemPriceView cipVw = (ContractItemPriceView) priceItems.get(ii);
      String inputId = cipVw.getItemId()+"#"+cipVw.getContractId();
      if(!inputId.equals(inputIds[ii])) {
        ae.add("error",new ActionError("error.simpleGenericError","Submited information does not match stored items. Probably old page was used"));
        return ae;
      }
      prices[ii] = priceMulty.toString();
    }
    pForm.setPrices(prices);
    return ae;
  }

  //----------------------------------------------------------------------------
  public static ActionErrors setCostForSelected
      (HttpServletRequest request,
       AccountItemPriceMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    String costMultyS = pForm.getCostMulty();
    if(costMultyS==null || costMultyS.trim().length()==0) {
      ae.add("error",new ActionError("error.simpleGenericError","No item price found"));
      return ae;
    }
    BigDecimal costMulty = null;
    try {
      costMulty = new BigDecimal(costMultyS);
    }catch(Exception exc) {}
    if(costMulty==null) {
      ae.add("error",new ActionError("error.simpleGenericError","Wrong cost format: "+costMultyS));
      return ae;
    }
    String[] selected = pForm.getSelectorBox();
    String[] costs = pForm.getDistCosts();
    String[] inputIds = pForm.getInputIds();
    ContractItemPriceViewVector priceItems = pForm.getPriceItems();
    if(inputIds.length<priceItems.size()) {
      ae.add("error",new ActionError("error.simpleGenericError","Submited information does not match stored items. Probably old page was used"));
      return ae;
    }
    for(int ii=0; ii<priceItems.size(); ii++) {
      ContractItemPriceView cipVw = (ContractItemPriceView) priceItems.get(ii);
      String inputId = cipVw.getItemId()+"#"+cipVw.getContractId();
      if(!inputId.equals(inputIds[ii])) {
        ae.add("error",new ActionError("error.simpleGenericError","Submited information does not match stored items. Probably old page was used"));
        return ae;
      }
      for(int jj=0; jj<selected.length; jj++) {
        if(inputId.equals(selected[jj])) {
          costs[ii] = costMulty.toString();
          break;
        }
      }
    }
    pForm.setDistCosts(costs);
    return ae;
  }
  //----------------------------------------------------------------------------
  public static ActionErrors setCostForAll(HttpServletRequest request,
			    AccountItemPriceMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    String costMultyS = pForm.getCostMulty();
    if(costMultyS==null || costMultyS.trim().length()==0) {
      ae.add("error",new ActionError("error.simpleGenericError","No item price found"));
      return ae;
    }
    BigDecimal costMulty = null;
    try {
      costMulty = new BigDecimal(costMultyS);
    }catch(Exception exc) {}
    if(costMulty==null) {
      ae.add("error",new ActionError("error.simpleGenericError","Wrong cost format: "+costMultyS));
      return ae;
    }
    String[] costs = pForm.getDistCosts();
    String[] inputIds = pForm.getInputIds();
    ContractItemPriceViewVector priceItems = pForm.getPriceItems();
    if(inputIds.length<priceItems.size()) {
      ae.add("error",new ActionError("error.simpleGenericError","Submited information does not match stored items. Probably old page was used"));
      return ae;
    }
    for(int ii=0; ii<priceItems.size(); ii++) {
      ContractItemPriceView cipVw = (ContractItemPriceView) priceItems.get(ii);
      String inputId = cipVw.getItemId()+"#"+cipVw.getContractId();
      if(!inputId.equals(inputIds[ii])) {
        ae.add("error",new ActionError("error.simpleGenericError","Submited information does not match stored items. Probably old page was used"));
        return ae;
      }
      costs[ii] = costMulty.toString();
    }
    pForm.setDistCosts(costs);
    return ae;
  }
  //----------------------------------------------------------------------------
  public static ActionErrors save(HttpServletRequest request,
			    AccountItemPriceMgrForm pForm)
  {
    HttpSession session = request.getSession();
    String user =(String)session.getAttribute(Constants.USER_NAME);
    ActionErrors ae = new ActionErrors();
    int catalogId=pForm.getCatalogId();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
	    ae.add("error",new ActionError("error.systemError","No Ejb access"));
	    return ae;
	}
    Contract contractEjb = null;
    try {
      contractEjb = factory.getContractAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Contract Ejb access"));
	  return ae;
	}
    //
    ContractItemPriceViewVector priceItems = pForm.getPriceItems();
    if(priceItems==null) {
      return ae;
    }

    // Get all the values from the form.
    String[] distCosts = pForm.getDistCosts();
    String[] prices = pForm.getPrices();
    String[] inputIds = pForm.getInputIds();
    String[] inCustSkuArr = pForm.getCustSku();
    String[] inCustDescArr = pForm.getCustDesc();

    ContractItemPriceViewVector itemsToSave = 
	new ContractItemPriceViewVector();
    ContractItemPriceViewVector custItemsToSave = 
	new ContractItemPriceViewVector();
    if(inputIds.length<priceItems.size()) {
      ae.add("error",new ActionError
	     ("error.simpleGenericError",
	      "Submited information does not match stored items."));
      return ae;
    }
    for(int ii=0; ii<priceItems.size(); ii++) {
      ContractItemPriceView cipVw = (ContractItemPriceView) priceItems.get(ii);
      String inputId = cipVw.getItemId()+"#"+cipVw.getContractId();
      if(!inputId.equals(inputIds[ii])) {
        ae.add("error",new ActionError("error.simpleGenericError","Submited information does not match stored items. Probably old page was used"));
        return ae;
      }
      String distCostS = distCosts[ii];
      BigDecimal distCost = null;
      try {
        distCost = new BigDecimal(distCostS);
      } catch(Exception exc) {}
      if(distCost==null) {
        ae.add("error",new ActionError("error.simpleGenericError","Wrong distributor cost format: "+distCostS+" Item id: "+cipVw.getItemId()+" Contract: "+cipVw.getContractName()));
      }
      String priceS = prices[ii];
      BigDecimal price = null;
      try {
        price = new BigDecimal(priceS);
      } catch(Exception exc) {}
      if(price==null) {
        ae.add("error",new ActionError("error.simpleGenericError","Wrong price format: "+priceS+" Item id: "+cipVw.getItemId()+" Contract: "+cipVw.getContractName()));
      }
      if(price!=null && distCost!=null && 
	 (!price.equals(cipVw.getPrice()) || !distCost.equals(cipVw.getDistCost()))) {

        cipVw.setPrice(price);
        cipVw.setDistCost(distCost);

        itemsToSave.add(cipVw);
      }
    }

    for(int ii=0; ii<priceItems.size(); ii++) {
      ContractItemPriceView cipVw = (ContractItemPriceView) priceItems.get(ii);
	cipVw.setItemCustSku(inCustSkuArr[ii]);
	cipVw.setItemCustDesc(inCustDescArr[ii]);
        custItemsToSave.add(cipVw);
      }

    if(ae.size()>0) {
      return ae;
    }
    try {
      contractEjb.updateContractItemCosts(itemsToSave,user);
      contractEjb.updateContractItemCustData(custItemsToSave,user);
    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Error during price and distributor cost saving. Message: "+exc.getMessage()));
      return ae;
    }
    pForm.setPriceItems(priceItems);
    initInputArrays(request,pForm);
    return ae;
  }


  public static ActionErrors setCustSkuForSelected
      (HttpServletRequest request,
       AccountItemPriceMgrForm pForm)
  {

    ActionErrors ae = new ActionErrors();
    String v = pForm.getNewCustSku();
    if(v==null || v.trim().length()==0) {
      ae.add("error",
	     new ActionError("error.simpleGenericError",
			     "No new customer Sku defined."));
      return ae;
    }

    String[] selected = pForm.getSelectorBox();
    String[] skus = pForm.getCustSku();
    String[] inputIds = pForm.getInputIds();
    ContractItemPriceViewVector items = pForm.getPriceItems();
    if(inputIds.length<items.size()) {
      ae.add("error",new ActionError
	     ("error.simpleGenericError",
	      "Submited information does not match stored items."));
      return ae;
    }

    for(int ii=0; ii<items.size(); ii++) {
      ContractItemPriceView cipVw = (ContractItemPriceView) items.get(ii);
      String inputId = cipVw.getItemId()+"#"+cipVw.getContractId();
      if(!inputId.equals(inputIds[ii])) {
        ae.add("error",new ActionError
	       ("error.simpleGenericError",
		"Submited information does not match stored items."));
        return ae;
      }
      for(int jj=0; jj<selected.length; jj++) {
        if(inputId.equals(selected[jj])) {
          skus[ii] = v;
          break;
        }
      }
    }
    pForm.setCustSku(skus);

    return ae;
  }

  public static ActionErrors setCustSkuForAll
      (HttpServletRequest request,
       AccountItemPriceMgrForm pForm)    {

      ActionErrors ae = new ActionErrors();
      String v = pForm.getNewCustSku();
      if(v==null || v.trim().length()==0) {
	  ae.add("error",new ActionError
		 ("error.simpleGenericError","No item Sku found"));
	  return ae;
      }

      String[] skus = pForm.getCustSku();
      String[] inputIds = pForm.getInputIds();
      ContractItemPriceViewVector items = pForm.getPriceItems();
      if(inputIds.length<items.size()) {
	  ae.add("error",new ActionError
		 ("error.simpleGenericError",
		  "Submited information does not match stored items."));
	  return ae;
      }

      for(int ii=0; ii<items.size(); ii++) {
	  ContractItemPriceView cipVw = (ContractItemPriceView) items.get(ii);
	  String inputId = cipVw.getItemId()+"#"+cipVw.getContractId();
	  if(!inputId.equals(inputIds[ii])) {
	      ae.add("error",new ActionError
		     ("error.simpleGenericError",
		      "Submited information does not match stored items."));
	      return ae;
	  }
	  skus[ii] = v;
      }
      pForm.setCustSku(skus);
      return ae;
  }


  public static ActionErrors setCustDescForSelected
      (HttpServletRequest request,
       AccountItemPriceMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    String v = pForm.getNewCustDesc();
    if(v==null || v.trim().length()==0) {
      ae.add("error",new ActionError("error.simpleGenericError",
				     "No new customer description defined."));
      return ae;
    }

    String[] selected = pForm.getSelectorBox();
    String[] descv = pForm.getCustDesc();
    String[] inputIds = pForm.getInputIds();
    ContractItemPriceViewVector items = pForm.getPriceItems();
    if(inputIds.length<items.size()) {
      ae.add("error",new ActionError
	     ("error.simpleGenericError",
	      "Submited information does not match stored items."));
      return ae;
    }

    for(int ii=0; ii<items.size(); ii++) {
      ContractItemPriceView cipVw = (ContractItemPriceView) items.get(ii);
      String inputId = cipVw.getItemId()+"#"+cipVw.getContractId();
      if(!inputId.equals(inputIds[ii])) {
        ae.add("error",new ActionError
	       ("error.simpleGenericError",
		"Submited information does not match stored items."));
        return ae;
      }
      for(int jj=0; jj<selected.length; jj++) {
        if(inputId.equals(selected[jj])) {
          descv[ii] = v;
          break;
        }
      }
    }
    pForm.setCustDesc(descv);
    return ae;
  }

  public static ActionErrors setCustDescForAll
      (HttpServletRequest request,
       AccountItemPriceMgrForm pForm)    {

      ActionErrors ae = new ActionErrors();
      String v = pForm.getNewCustDesc();
      if(v==null || v.trim().length()==0) {
	  ae.add("error",new ActionError
		 ("error.simpleGenericError","No description found"));
	  return ae;
      }

      String[] descv = pForm.getCustDesc();
      String[] inputIds = pForm.getInputIds();
      ContractItemPriceViewVector items = pForm.getPriceItems();
      if(inputIds.length<items.size()) {
	  ae.add("error",new ActionError
		 ("error.simpleGenericError",
		  "Submited information does not match stored items."));
	  return ae;
      }

      for(int ii=0; ii<items.size(); ii++) {
	  ContractItemPriceView cipVw = (ContractItemPriceView) items.get(ii);
	  String inputId = cipVw.getItemId()+"#"+cipVw.getContractId();
	  if(!inputId.equals(inputIds[ii])) {
	      ae.add("error",new ActionError
		     ("error.simpleGenericError",
		      "Submited information does not match stored items."));
	      return ae;
	  }
	  descv[ii] = v;
      }
      pForm.setCustDesc(descv);
      return ae;
  }

}

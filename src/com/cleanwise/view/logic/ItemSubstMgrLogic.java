
package com.cleanwise.view.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.ArrayList;
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
import com.cleanwise.view.forms.ItemSubstMgrForm;
import com.cleanwise.view.forms.ContractMgrDetailForm;
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
public class ItemSubstMgrLogic {
  /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors init(HttpServletRequest request,
			  ItemSubstMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    ContractMgrDetailForm detailForm = (ContractMgrDetailForm) session.getAttribute("CONTRACT_DETAIL_FORM");
    int catalogId=detailForm.getDetail().getCatalogId();
    pForm.setCatalogId(catalogId);
    int contractId=detailForm.getDetail().getContractId();
    pForm.setContractId(contractId);
    pForm.setSelectorBox(new String[0]);
    pForm.setSubstitutions(null);
    pForm.setSubstItems(null);
    int storeId = detailForm.getStoreId();
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
    int storeCatalogId = 0;
    try {
      CatalogDataVector catalogDV = catalogInfEjb.getCatalogsCollectionByBusEntity(storeId);
      for(int ii=0; ii<catalogDV.size(); ii++) {
        CatalogData cD = (CatalogData) catalogDV.get(ii);
        if(!RefCodeNames.CATALOG_STATUS_CD.INACTIVE.equals(cD.getCatalogStatusCd()) &&
           RefCodeNames.CATALOG_TYPE_CD.STORE.equals(cD.getCatalogTypeCd())) {
          if(storeCatalogId!=0) {
            ae.add("error",new ActionError("error.systemError","Store has more than one catalog. Store id: "+storeId));
            return ae;
          }
          storeCatalogId = cD.getCatalogId();
        }
      }
    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Error during store catalog request. Message: "+exc.getMessage()));
      return ae;
    }
    if(storeCatalogId==0) {
       ae.add("error",new ActionError("error.systemError","Store has no catalog. Store id: "+storeId));
       return ae;
    }
    pForm.setStoreCatalogId(storeCatalogId);
    return ae;
  }
  /**
  * Searches for item. Takes conditions form the form
  *
  */
  public static ActionErrors searchForItem (HttpServletRequest request,
			    ItemSubstMgrForm pForm)
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
    pForm.setSubstitutions(null);

    //Create a set of filters
    Vector searchConditions = new Vector();
    //Contract
    {
    SearchCriteria sc = new
      SearchCriteria(SearchCriteria.CONTRACT_ID,SearchCriteria.EXACT_MATCH,new Integer(pForm.getContractId()));
    searchConditions.add(sc);
    }
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
    IdVector itemIds = null;
    try {
      itemIds = catalogInfEjb.searchCatalogProducts(catalogId,searchConditions);
    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Error during item search request. Message: "+exc.getMessage()));
      return ae;
    }
    ContractItemSubstViewVector substitutions = null;
    try {
       substitutions = contractEjb.getSubstitutions(itemIds,pForm.getContractId(),!pForm.isSubstOnlyFlag());
    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Can't create ContractItemSubstView object. Message: "+exc.getMessage()));
      return ae;
    }
    pForm.setSubstitutions(substitutions);
    return ae;
  }
  //---------------------------------------------------------------------------
  public static ActionErrors addFromLookup (HttpServletRequest request,
			    ItemSubstMgrForm pForm)
  {
    HttpSession session = request.getSession();
    String user =(String)session.getAttribute(Constants.USER_NAME);
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
      ae.add("error",new ActionError("No APIAccess."));
      return ae;
    }
    Contract contractEjb = null;
    try {
      contractEjb = factory.getContractAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Contract Ejb access"));
	  return ae;
	}

    ProductDataVector addObjects = (ProductDataVector) session.getAttribute("lookupValue");
    session.setAttribute("lookupValue", new ProductDataVector());
    pForm.setSubstItems(addObjects);
    return ae;
  }
  //---------------------------------------------------------------------------
  public static ActionErrors addSubstitutions (HttpServletRequest request,
			    ItemSubstMgrForm pForm)
  {
    HttpSession session = request.getSession();
    String user =(String)session.getAttribute(Constants.USER_NAME);
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
      ae.add("error",new ActionError("No APIAccess."));
      return ae;
    }
    Contract contractEjb = null;
    try {
      contractEjb = factory.getContractAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Contract Ejb access"));
	  return ae;
	}
    //check selections
    String itemIdsS[] = pForm.getSelectorBox();
    if(itemIdsS.length==0) {
      ae.add("error",new ActionError("error.simpleGenericError","No items selected"));
      return ae;
    }
    //Make itemIds vector
    IdVector itemIds = new IdVector();
    for(int ii=0; ii<itemIdsS.length; ii++) {
      try {
        String ss = itemIdsS[ii];
        int pos = ss.indexOf('#');
        if(pos>=0) ss = ss.substring(0,pos);
        itemIds.add(new Integer(ss));
      } catch (NumberFormatException exc) {
        ae.add("error",new ActionError("error.systemError","Wrong item id format: "+itemIdsS[ii]));
      }
    }
    //Make substId vector
    ProductDataVector productDV = pForm.getSubstItems();
    if(productDV==null || productDV.size()==0) {
      ae.add("error",new ActionError("error.simpleGenericError","No substitute items selected"));
      return ae;
    }
    IdVector substItemIds = new IdVector();
    for(int ii=0; ii<productDV.size(); ii++) {
      ProductData pD = (ProductData) productDV.get(ii);
      substItemIds.add(new Integer(pD.getItemData().getItemId()));
    }
    //Create substitutions
    try {
      contractEjb.addSubstitutions(itemIds, substItemIds, pForm.getContractId(), user);
    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Can't create new substitution. Message: "+exc.getMessage()));
      return ae;
    }
    pForm.setSubstItems(null);
    pForm.setSelectorBox(new String[0]);
    ae = searchForItem(request,pForm);
    if(ae.size()>0) {
      return ae;
    }
    return ae;
  }
  //---------------------------------------------------------------------------
  public static ActionErrors removeSubstitutions (HttpServletRequest request,
			    ItemSubstMgrForm pForm)
  {
    HttpSession session = request.getSession();
    String user =(String)session.getAttribute(Constants.USER_NAME);
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
      ae.add("error",new ActionError("No APIAccess."));
      return ae;
    }
    Contract contractEjb = null;
    try {
      contractEjb = factory.getContractAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Contract Ejb access"));
	  return ae;
	}
    //check selections
    String itemIdsS[] = pForm.getSelectorBox();
    if(itemIdsS.length==0) {
      ae.add("error",new ActionError("error.simpleGenericError","No items selected"));
      return ae;
    }
    //Make subst ids vector
    IdVector contractItemSubstIds = new IdVector();
    for(int ii=0; ii<itemIdsS.length; ii++) {
      try {
        String ss = itemIdsS[ii];
        int pos = ss.indexOf('#');
        if(pos<0||pos>=ss.length()-1){
          continue;
        }
        ss = ss.substring(pos+1);
        int id = Integer.parseInt(ss);
        if(id<=0) {
          continue;
        }
        contractItemSubstIds.add(new Integer(id));
      } catch (NumberFormatException exc) {
        ae.add("error",new ActionError("error.systemError","Wrong item id format: "+itemIdsS[ii]));
      }
    }
    //Remove substitutions
    try {
      contractEjb.removeSubstitutions(contractItemSubstIds, user);
    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Can't remove substitution. Message: "+exc.getMessage()));
      return ae;
    }
    pForm.setSubstItems(null);
    pForm.setSelectorBox(new String[0]);
    ae = searchForItem(request,pForm);
    if(ae.size()>0) {
      return ae;
    }
    return ae;
  }
  //---------------------------------------------------------------------------
  public static ActionErrors sortItems (HttpServletRequest request,
			    ItemSubstMgrForm pForm)
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
	String sortField = request.getParameter("sortField");
    ContractItemSubstViewVector substitutions = pForm.getSubstitutions();
    substitutions.sort(sortField);
    return ae;
  }
}
